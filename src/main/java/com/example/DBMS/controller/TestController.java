package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.FeedbackDAO;
import com.example.DBMS.dao.TestDAO;
import com.example.DBMS.dao.TestbookingDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.Test;
import com.example.DBMS.model.Testbooking;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
// import com.example.DBMS.service.ToastService;
import com.example.DBMS.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
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

@Controller
public class TestController {

    @Autowired
	 private TestDAO testDAO;
	 @Autowired
	 private FeedbackDAO feedbackDAO;
	 @Autowired
	 private UserDAO userDAO;
     @Autowired
	 private TestbookingDAO testbookingDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;
	
	@GetMapping("/booktest")
	public String alltests(Model model,HttpSession session, RedirectAttributes redirectAttributes) {


		List<Test> list=testDAO.alltests();
		model.addAttribute("tests", list);
		if(authenticateService.isAuthenticated(session))
		{
			model.addAttribute("patientName", authenticateService.getCurrentUser(session));
			model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
			User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
			model.addAttribute("loggedUser", loggedUser);
		}
		return "test";
	}

    @GetMapping("{username}/booktest/{id}")
	public String testbook(@PathVariable("username") String username, @PathVariable("id") int testid, Model model,HttpSession session, RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        Testbooking testbooking = new Testbooking();

        Test test = testDAO.findByID(testid);
        System.out.println(test);

        testbooking.setPatientName(username);
        testbooking.setCurrDate(new Date().toString());
        testbooking.setTestName(test.getTestName());

        model.addAttribute("testbooking", testbooking);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


		return "bookingtest";
	}
    
    @PostMapping("{username}/booktest/{id}")
	public String testbookPost(@PathVariable("username") String username, @PathVariable("id") int testid,@ModelAttribute("testbooking") Testbooking testbooking, Model model,HttpSession session) {

            testbooking.setStatus("Pending");
            System.out.print(testbooking);
            testbookingDAO.save(testbooking);
            int id = testbookingDAO.getLastID();
			return "redirect:/confirmtestbooking/" + id ;
	}

    @GetMapping("/confirmtestbooking/{id}")
	public String confirmbookingtest(@PathVariable("id") int id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		Testbooking testbooking = testbookingDAO.findByID(id);
		System.out.println(testbooking);

		String testName = testbooking.getTestName();

		Test test = testDAO.findByTestName(testName);

		model.addAttribute("cost", test.getCost());

		User user = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("user", user);
		model.addAttribute("testbooking", testbooking);
		model.addAttribute("id", id);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		return "confirmtestbooking";
	}

	@PostMapping("/confirmtestbooking/{id}")
	public String confirmbookingtestPost(@PathVariable("id") int id, Model model, HttpSession session) {

		System.out.println(id);

		return "redirect:/testpayment/" + id;	
	}

	@GetMapping("/mytestbookings")
	public String mytestbooking(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String username = authenticateService.getCurrentUser(session);

		List<Testbooking> testbookings = testbookingDAO.findByUsername(username);

		model.addAttribute("testbookings", testbookings);

		List<Integer> costs = new ArrayList<Integer>();
		List<Integer> feedbacks = new ArrayList<Integer>();

		for(int i=0; i<testbookings.size(); i++) {

			String testName = (testbookings.get(i)).getTestName();

			Test test = testDAO.findByTestName(testName);

			costs.add(test.getCost());

			int rating = feedbackDAO.isTestbookingFeedback(testbookings.get(i).getTestbookingID());
			System.out.println(rating);
			if(rating == 0)
				feedbacks.add(0);
			else
			{
				feedbacks.add(feedbackDAO.testbookingRating(testbookings.get(i).getTestbookingID()));
			}
		}

		Feedback feedback = new Feedback();

		model.addAttribute("feedback", feedback);
		model.addAttribute("feedbacks", feedbacks);

		model.addAttribute("costs", costs);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


		return "mytestbookings";
	}

	@PostMapping("/testbookings/{id}/feedback")
	public String feedbackAppointment(@ModelAttribute("feedback") Feedback feedback,@PathVariable("id") int testbookingID) {

		feedback.setDate(new Date().toString());

		feedback.setPurpose("Test Booking");

		feedback.setPurposeID(testbookingID);

		feedbackDAO.save(feedback);

		return "redirect:/mytestbookings";
	}
}
