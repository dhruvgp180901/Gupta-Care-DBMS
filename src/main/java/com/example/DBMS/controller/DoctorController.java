package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.DoctorDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Doctor;
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
public class DoctorController {

    @Autowired
	 private DoctorDAO doctorDAO;
	 @Autowired
	 private UserDAO userDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;
	
	@GetMapping("/doctors")
	public String alldoctors(Model model,HttpSession session) {

		// System.out.println(authenticateService.getCurrentUser(session));

		List<Doctor> list=doctorDAO.allDoctors();
		System.out.println(list.get(0));

		List<User> users = new ArrayList<User>();

		for(int i=0;i<list.size();i++) {

			User user = userDAO.findByUsername(list.get(i).getUsername());
			users.add(user);
		}
		model.addAttribute("users", users);
		model.addAttribute("doctors", list);

		if(authenticateService.isAuthenticated(session))
		{
			model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
			model.addAttribute("patientName", authenticateService.getCurrentUser(session));
			User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
			model.addAttribute("loggedUser", loggedUser);
		}

		return "doctor";
	}
    
}
