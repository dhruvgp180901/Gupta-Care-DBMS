package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.Utils.HostName;
import com.example.DBMS.dao.DoctorApplicantDAO;
import com.example.DBMS.dao.HomeDAO;
import com.example.DBMS.dao.TransactionDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.dao.WorkDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.ContactUsForm;
import com.example.DBMS.model.DoctorApplicant;
import com.example.DBMS.model.Transaction;
import com.example.DBMS.model.User;
import com.example.DBMS.model.Work;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
import com.example.DBMS.validator.UserValidator;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.classic.Logger;

@Controller
public class HomeController {

    @Autowired
	private DoctorApplicantDAO doctorApplicantDAO;
	@Autowired
	private HomeDAO homeDAO;
	@Autowired
	private TransactionDAO transactionDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WorkDAO workDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;

	 Instamojo api;
	// private static final Logger LOGGER = Logger.getLogger(HomeController.class.getName());

	

	@GetMapping({"/","/welcome"})
	public String welcome(Model model, HttpSession session) {
		
		ContactUsForm contactUs = new ContactUsForm();

		model.addAttribute("contactUs", contactUs);

		// System.out.println(session.getAttribute("loggedUser").toString());
		if(authenticateService.isAuthenticated(session))
		{
			model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
			User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
			model.addAttribute("loggedUser", loggedUser);
		}
		
		return "welcome";
	}

	@PostMapping({"/","/welcome"})
	public String welcomePost(@ModelAttribute("contactUs") ContactUsForm contactUs, HttpSession session) {

		contactUs.setDate(new Date().toString());
		homeDAO.save(contactUs);

		return "welcome";
	}

	@GetMapping({"/doctor/application"})
	public String applyDoctor(Model model, HttpSession session, RedirectAttributes redirectAttributes) {


		DoctorApplicant application = new DoctorApplicant();

		application.setApplicationDate(java.time.LocalDate.now().toString());

		model.addAttribute("application", application);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));

		if(authenticateService.isAuthenticated(session))
		{
			User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
			model.addAttribute("loggedUser", loggedUser);

		}
		
		
		return "doctorapplication";
	}

	@PostMapping({"/doctor/application"})
	public String applyDoctorPost(@ModelAttribute("application") DoctorApplicant application,@RequestParam("file") MultipartFile file, Model model, HttpSession session) {

		doctorApplicantDAO.save(application);
		int id = doctorApplicantDAO.getLastID();
		if(!file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            doctorApplicantDAO.updateProfile(id, filename);
			String uploadDir = "applicant-photos/resume/"  + application.getApplicantName();
			System.out.println(uploadDir);

			// if(bindingResult.hasErrors())
			// {
			// 	return "profile/edit/" + username;
			// }

            try {
                FileUploadUtil.saveFile(filename, uploadDir, file);
            }
            catch(Exception e) {
                // unable to upload the file
				System.out.println(e);
            }
        }
		return "/welcome";
	}


	@GetMapping("/badAuthorize")
	public String errorPage(Model model,HttpSession session) {

		return "badAuthorize";
	}
}



// 	@GetMapping(value = "/payment/paid")
// 	public String success(@RequestParam("transaction_id")String transaction_id, Model model, HttpSession session)
// 	{	

// 		try {
			
// 		    PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(transaction_id);
		    
		    
// 		    Transaction transaction=transactionDAO.updateVerified(transaction_id);
// 			System.out.println("Pay");

// 			System.out.println(paymentOrder);
// 			System.out.println("Pay");

// 			System.out.println(transaction);
	    	
// 	    	return "redirect:/login";
		    

// 		} catch (Exception e) {
		    
// 		    return "redirect:/";
		    

// 		}
		
// 	}
	
// 	@GetMapping("/payment")
// 	public String pay(Model model, HttpSession session) {
		

// 		// api = InstamojoImpl.getApi(clientId, clientSecret, )

// 		// System.out.println(api);

// 		float fees = 20;
// 		String clientId = "test_23S7qHPCPl85W3iriMorlc15Unt4a8Akg40";
// 		String clientSecret = "test_R78aFel2JNDEcsOlir6WnU3AAs2sOIsAxc9cQOnh0D6KJMtufzNvpl58oXqfmBpHDZcivBNHeVeo1wzebpnETXrQnOPb69Ao9AC6k6ImoFQZlRe49QNBuuOJzro";
// 		// String clientId = "test_23S7qHPCPl85W3iriMorlc15Unt4a8Akg40";
// 		// String clientSecret = "test_R78aFel2JNDEcsOlir6WnU3AAs2sOIsAxc9cQOnh0D6KJMtufzNvpl58oXqfmBpHDZcivBNHeVeo1wzebpnETXrQnOPb69Ao9AC6k6ImoFQZlRe49QNBuuOJzro";

// 	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
// 	    api = new InstamojoImpl(context);

// 		PaymentOrder order = new PaymentOrder();
// 		order.setName("student1");
// 		order.setEmail("guptacare18@gmail.com");
// 		order.setPhone("9204040100");
// 		order.setCurrency("INR");
// 		order.setAmount((double) fees);
// 		order.setDescription("Enrollment");
		
// 		order.setRedirectUrl(HostName.getHost()+"payment/paid");
// 		order.setWebhookUrl("http://www.someurl.com/");
// 		String token= UUID.randomUUID().toString();
// 		order.setTransactionId(token);
		
// 		System.out.println(order);


// 		try {

// 		    PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
		    
// 		    String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();		    
//         	// System.out.println(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
			
// 		// System.out.println(paymentOrderId);
//         	// System.out.println(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
        	
//         	// Transaction transaction = new Transaction();
//     	    // transaction.setAmount(20);  
//     	    // transaction.setVerified(false);
//     	    // transaction.setTransactionDate(new Date());
//    	     	// transaction.setTransactionTime(new Date());
// 	    	// transaction.setMode("online");
// 	    	// transaction.setToken(token);
// 	    	// transactionDAO.save(transaction);

// 			return "redirect:/" + paymentOrderResponse.getPaymentOptions().getPaymentUrl();
        	

// 		} catch (HTTPException e) {
// 			System.out.println(e);
// 		} catch (ConnectionException e) {
// 			System.out.println(e);

// 		}
// 		return "redirect:/welcome";
// 	}
// }
