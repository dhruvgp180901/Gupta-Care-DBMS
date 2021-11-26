package com.example.DBMS.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.Utils.HostName;
import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.FeedbackDAO;
import com.example.DBMS.dao.MedicineDAO;
import com.example.DBMS.dao.OrderDAO;
import com.example.DBMS.dao.PaymentDAO;
import com.example.DBMS.dao.PayorderMedDAO;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.Medicine;
import com.example.DBMS.model.Order;
import com.example.DBMS.model.Payment;
import com.example.DBMS.model.PayorderMed;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedicineController {

	 @Autowired
	 private AuthenticateService authenticateService;
    @Autowired
	 private ToastService toastService;
     @Autowired
	 private MedicineDAO medicineDAO;
     @Autowired
	 private OrderDAO orderDAO;
    @Autowired
	 private FeedbackDAO feedbackDAO;
    @Autowired
	 private PaymentDAO paymentDAO;
    @Autowired
	 private PayorderMedDAO payorderMedDAO;
    @Autowired
	 private UserDAO userDAO;

    Instamojo api;

     @GetMapping("/medicines")
     public String alltmedicines(Model model,HttpSession session) {
 
         List<Medicine> list=medicineDAO.allmedicines();

        // for(int i=0;i<list.size();i++)
        // {
        //     System.out.println(list.get(i));
        // }
         model.addAttribute("medicines", list);
      if(authenticateService.isAuthenticated(session))
		{
			model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));

		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);
		}

 
         return "medicine";
     }

     @GetMapping("/medicine/{id}")
     public String ordermedicine(@PathVariable("id") int id, Model model,HttpSession session, RedirectAttributes redirectAttributes) {
      
      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

      Order order = new Order();

      Medicine medicine = medicineDAO.findByID(id);

      order.setMedicineID(id);
      order.setUsername(authenticateService.getCurrentUser(session));
      order.setAmount(1);
      order.setCost(medicine.getCost());
      order.setDate(java.time.LocalDate.now().toString());
      orderDAO.save(order);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
      User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);
        toastService.redirectWithSuccessToast(redirectAttributes, "Added to Cart");


      return "redirect:/medicines";
     }

     @PostMapping("/medicine/{id}")
     public String ordermedicinePost(@PathVariable("id") int id, Model model,HttpSession session, RedirectAttributes redirectAttributes) {
 
        Order order = new Order();

        Medicine medicine = medicineDAO.findByID(id);

        order.setMedicineID(id);
        order.setUsername(authenticateService.getCurrentUser(session));
        order.setAmount(1);
        order.setCost(medicine.getCost());
        order.setDate(java.time.LocalDate.now().toString());
        orderDAO.save(order);


        toastService.redirectWithSuccessToast(redirectAttributes, "Added to Cart");

        return "redirect:/medicines";

     }

     @GetMapping("/mycart")
     public String myorderMed(Model model,HttpSession session, RedirectAttributes redirectAttributes) {

      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}
 
        String username = authenticateService.getCurrentUser(session);
        List<Order> list = orderDAO.findByUsername(username);
        model.addAttribute("myorders", list);

        List<Medicine> medicines = new ArrayList<Medicine>();
        List<Integer> amounts = new ArrayList<Integer>();

        int payment = 0;

        for(int i=0;i<list.size();i++) {
           payment += (list.get(i).getAmount() * list.get(i).getCost() );
           amounts.add(list.get(i).getAmount() * list.get(i).getCost());
            System.out.println(list.get(i).getCost());
           int id = list.get(i).getMedicineID();

           Medicine medicine = medicineDAO.findByID(id);

           medicines.add(medicine);
        }
        model.addAttribute("amounts", amounts);
        model.addAttribute("medicines",medicines);
        model.addAttribute("payment",payment);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

        return "myMed";
     }

   //   @PostMapping("/mycart")
   //   public String myorderMedPost(@RequestBody List<Order> orders, Model model,HttpSession session) {
 
   //      System.out.println(orders);

   //      return "welcome";
   //   }
    
   @GetMapping("/mycart/dec/{id}")
   public String orderAmountdec(@PathVariable("id") int id, Model model,HttpSession session) {

      Order order = orderDAO.findByID(id);
      int newAmount = order.getAmount();
      if(newAmount != 1)
      {
         newAmount = newAmount - 1;
         orderDAO.updateAmount(id, newAmount); 

      }

      // System.out.println(id);

      return "redirect:/mycart";
   }

   @GetMapping("/mycart/inc/{id}")
   public String orderAmountinc(@PathVariable("id") int id, Model model,HttpSession session) {

      Order order = orderDAO.findByID(id);
      int newAmount = order.getAmount();
      if(newAmount != 50)
      {
         newAmount = newAmount + 1;
         orderDAO.updateAmount(id, newAmount); 

      }
      return "redirect:/mycart";
   }

   @GetMapping("/mycart/delete/{id}")
   public String orderDelete(@PathVariable("id") int id, Model model,HttpSession session) {

      orderDAO.delete(id);

      return "redirect:/mycart";
   }

   @GetMapping("/confirm/orderMed")
   public String confirmMedicineBill(Model model,HttpSession session, RedirectAttributes redirectAttributes) {
      
      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

      String username = authenticateService.getCurrentUser(session);


         User user = userDAO.findByUsername(username);
         model.addAttribute("user", user);

         model.addAttribute("date", new Date().toString());

        List<Order> list = orderDAO.findByUsername(username);
        model.addAttribute("myorders", list);

        List<Medicine> medicines = new ArrayList<Medicine>();
        List<Integer> amounts = new ArrayList<Integer>();

        int payment = 0;

        for(int i=0;i<list.size();i++) {
           payment += (list.get(i).getAmount() * list.get(i).getCost() );
           amounts.add(list.get(i).getAmount() * list.get(i).getCost());
            System.out.println(list.get(i).getCost());
           int id = list.get(i).getMedicineID();

           Medicine medicine = medicineDAO.findByID(id);

           medicines.add(medicine);
        }
        model.addAttribute("amounts", amounts);
        model.addAttribute("medicines",medicines);
        model.addAttribute("payment",payment);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

      return "confirmmedicineorder";
   }

   @GetMapping("/paymedicine")
   public String payorderMedicine(Model model,HttpSession session, RedirectAttributes redirectAttributes) {
      
      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

      String username = authenticateService.getCurrentUser(session);

      User user = userDAO.findByUsername(username);

      Payment payment = new Payment();

      List<Order> list = orderDAO.findByUsername(username);
      List<Integer> medicines = new ArrayList<Integer>();
      List<Integer> quantities = new ArrayList<Integer>();

      payment.setPurpose("Medicines Order");
      payment.setPayDate(new Date().toString());
      payment.setPurposeID(user.getUserID());
      

      int amount = 0;

      for(int i=0;i<list.size();i++) {
         
         amount += (list.get(i).getAmount() * list.get(i).getCost() );

         medicines.add(list.get(i).getMedicineID());

         quantities.add(list.get(i).getAmount());
         
      }

      payment.setAmount(amount);
      
      paymentDAO.save(payment);

      int payid = paymentDAO.getLastID();

      for(int i=0;i<medicines.size();i++) {

         PayorderMed payorderMed = new PayorderMed();
         payorderMed.setPaymentID(payid);
         payorderMed.setMedicineID(medicines.get(i));
         payorderMed.setQuantity(quantities.get(i));

         payorderMedDAO.save(payorderMed);

      }

      for(int i=0;i<list.size();i++) {

         orderDAO.delete(list.get(i).getOrderID());
      }
      model.addAttribute("payment", payment);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));


      String clientId = "test_BRySN9XGrlTv2X8mI6RUg9JCBeGd8tuU2tK";
		String clientSecret = "test_KjbkFknjnErbAweyrUKGK5GrPUgANWMN6wUxskqNzZg12q4oYeoB43InzGPH9sM5w5QriiOSI7e5F4wqaL0DmgMvQOHPCgzl8nEkBMVcdTCEuqXqYnQh7gSpvPF";

	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
	    api = new InstamojoImpl(context);

		PaymentOrder order = new PaymentOrder();
		order.setName(authenticateService.getCurrentUser(session));
		order.setEmail("guptacare18@gmail.com");
		order.setPhone("7404528473");
		order.setCurrency("INR");
		order.setAmount((double) amount);
		order.setDescription("Test Booking");
		
		order.setRedirectUrl(HostName.getHost()+"paymedicine/paid/" + payid);
		order.setWebhookUrl("http://www.someurl.com/");
		String token= UUID.randomUUID().toString();
		order.setTransactionId(token);
		
		System.out.println(order);


		try {

		    PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
		    
		    String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();		    

			return "redirect:" + paymentOrderResponse.getPaymentOptions().getPaymentUrl();
        	

		} catch (HTTPException e) {
			System.out.println(e);
		} catch (ConnectionException e) {
			System.out.println(e);

		}
		return "redirect:/welcome";

   }

   @GetMapping("/paymedicine/paid/{id}")
	public String paymentMedicinePost(@RequestParam("transaction_id")String transactonid ,@PathVariable("id") int payid,HttpSession session, RedirectAttributes redirectAttributes) {

      paymentDAO.updateTransaction(payid, transactonid);
        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Made Succesfully...");
        return "redirect:/welcome";

	}

   @GetMapping("/mymedicineorders")
    public String orderMedicinesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

      User user = userDAO.findByUsername(authenticateService.getCurrentUser(session));

        List<Payment> payments = paymentDAO.findByUserID(user.getUserID());

        model.addAttribute("payments", payments);


		   List<Integer> feedbacks = new ArrayList<Integer>();

         for(int i=0;i<payments.size();i++) {

            int rating = feedbackDAO.isMedicineorderFeedback(payments.get(i).getPaymentID());
			   System.out.println(rating);
			   if(rating == 0)
				   feedbacks.add(0);
			   else
			   {
				   feedbacks.add(feedbackDAO.medicineorderRating(payments.get(i).getPaymentID()));
			   }
         }
      
		Feedback feedback = new Feedback();

		model.addAttribute("feedback", feedback);
		model.addAttribute("feedbacks", feedbacks);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
      User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


        return "mymedicineorders";
    }

    @GetMapping("/medicineOrder/{id}")
    public String MedordershowDashboard(@PathVariable("id") int paymentid,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

      String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

      String username = authenticateService.getCurrentUser(session);

      User user = userDAO.findByUsername(username);

      model.addAttribute("user", user);


      Payment payment = paymentDAO.findByID(paymentid);

      model.addAttribute("date", payment.getPayDate());


      List<PayorderMed> payorders = payorderMedDAO.findByPaymentID(paymentid);

      List<Medicine> medicines = new ArrayList<Medicine>();

      List<Integer> amounts = new ArrayList<Integer>();

      int total = 0;

      for(int i=0;i<payorders.size();i++) {

         int medicineid = payorders.get(i).getMedicineID();

         medicines.add(medicineDAO.findByID(medicineid));

         amounts.add(medicineDAO.findByID(medicineid).getCost()* payorders.get(i).getQuantity());

         total += medicineDAO.findByID(medicineid).getCost()* payorders.get(i).getQuantity();

      }

      model.addAttribute("total", total);
      model.addAttribute("amounts", amounts);

      model.addAttribute("medicines", medicines);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
      User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

        return "medicineOrder";
    }

   @PostMapping("/payorders/{id}/feedback")
	public String feedbackAppointment(@ModelAttribute("feedback") Feedback feedback,@PathVariable("id") int paymentID) {

		feedback.setDate(new Date().toString());

		feedback.setPurpose("Medicine Order");

		feedback.setPurposeID(paymentID);

		feedbackDAO.save(feedback);

		return "redirect:/mymedicineorders";
	}
}
