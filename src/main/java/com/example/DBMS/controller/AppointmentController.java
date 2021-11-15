package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.AppointmentDAO;
import com.example.DBMS.dao.DoctorDAO;
import com.example.DBMS.dao.FeedbackDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Doctor;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
import com.example.DBMS.validator.UserValidator;

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

@Controller
public class AppointmentController {

    @Autowired
	 private UserDAO userDAO;
	 @Autowired
	 private FeedbackDAO feedbackDAO;
	 @Autowired
	 private DoctorDAO doctorDAO;
	 @Autowired
	 private AppointmentDAO appointmentDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;

    @GetMapping("/{patientName}/{doctorName}/appointment")
	public String appointment(@PathVariable("patientName") String username,@PathVariable("doctorName") String doctorName, Model model,
	HttpSession session,RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}
		
		// String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(authenticateService.getCurrentUser(session) != username)
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

		// authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(doctorDAO.findByDoctorname(doctorName) == null)
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }
		
		System.out.println(username);
		Appointment appointment=new Appointment();

		appointment.setPatientName(username);
		appointment.setDoctorName(doctorName);
		appointment.setCurrDate(new Date().toString());

		model.addAttribute("appointment", appointment);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		return "appointment";
	}

	@PostMapping("/{username}/{doctorName}/appointment")
	public String appointmentPost(@ModelAttribute("appointment") Appointment appointment, Model model) {

		// System.out.println(username);

		// System.out.println("#####");
		// System.out.println(appointment.getPatientName());
		// System.out.println(appointment.getBookDate());

		String bookDate = appointment.getBookDate();
		String startTime = appointment.getBookStime();
		String endTime = appointment.getBookEtime();

		if(appointmentDAO.appointmentExists(bookDate, startTime, endTime)) {
			// toast

			String patientName = appointment.getPatientName();
			String doctorName = appointment.getDoctorName();
			model.addAttribute("appointment", appointment);

			return "redirect:/" + patientName + "/" + doctorName + "/appointment";

		} 
		else{

			appointment.setStatus("pending");
			appointmentDAO.save(appointment);
			int id = appointmentDAO.getLastID();
			return "redirect:/confirmappointment/" + id ;
		}
		
		// appointment.setName(user.getUsername());

	}

	@GetMapping("/confirmappointment/{id}")
	public String confirmappointment(@PathVariable("id") int id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}
		
		Appointment appointment = appointmentDAO.findByID(id);

		String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(authenticateService.getCurrentUser(session) != appointment.getPatientName())
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

		System.out.println(appointment);


		String doctorName = appointment.getDoctorName();
		Doctor doctor = doctorDAO.findByDoctorname(doctorName); 

		User user = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("user", user);
		model.addAttribute("cost", doctor.getAppointmentCost());
		model.addAttribute("appointment", appointment);
		model.addAttribute("id", id);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		return "confirmappointment";
	}

	@PostMapping("/confirmappointment/{id}")
	public String confirmAppointmentPost(@PathVariable("id") int id, Model model, HttpSession session) {

		// appointmentDAO.save(appointment);
		// int appointmentID = appointmentDAO.getLastID();
		System.out.println(id);

		return "redirect:/payment/" + id;	
	}

	@GetMapping("/myappointments")
	public String myappointment(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String username = authenticateService.getCurrentUser(session);

		List<Appointment> appointments = appointmentDAO.findByUsername(username);

		List<Integer> costs = new ArrayList<Integer>();

		List<Integer> feedbacks = new ArrayList<Integer>();

		for(int i=0; i<appointments.size();i++)
		{
			System.out.println(appointments.get(i));
			String doctorName = appointments.get(i).getDoctorName();
			Doctor doctor = doctorDAO.findByDoctorname(doctorName);
			System.out.println(doctor);
			costs.add(doctor.getAppointmentCost());
			
			int rating = feedbackDAO.isAppointmentFeedback(appointments.get(i).getAppointmentID());
			System.out.println(rating);
			if(rating == 0)
				feedbacks.add(0);
			else
			{
				feedbacks.add(feedbackDAO.appointmentRating(appointments.get(i).getAppointmentID()));
			}
		}

		Feedback feedback = new Feedback();

		model.addAttribute("feedback", feedback);
		model.addAttribute("feedbacks", feedbacks);
		model.addAttribute("costs", costs);
		model.addAttribute("appointments", appointments);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		return "myappointments";
	} 

	@PostMapping("/appointments/{id}/feedback")
	public String feedbackAppointment(@ModelAttribute("feedback") Feedback feedback,@PathVariable("id") int appointmentID) {

		feedback.setDate(new Date().toString());

		feedback.setPurpose("Appointment");

		feedback.setPurposeID(appointmentID);

		feedbackDAO.save(feedback);

		return "redirect:/myappointments";
	}

    
}
