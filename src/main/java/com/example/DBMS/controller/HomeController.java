package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.DoctorApplicantDAO;
import com.example.DBMS.dao.HomeDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.ContactUsForm;
import com.example.DBMS.model.DoctorApplicant;
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
public class HomeController {

    @Autowired
	private DoctorApplicantDAO doctorApplicantDAO;
	@Autowired
	private HomeDAO homeDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;


	@GetMapping({"/","/welcome"})
	public String welcome(Model model, HttpSession session) {

		System.out.println("Chl ja");

		ContactUsForm contactUs = new ContactUsForm();

		model.addAttribute("contactUs", contactUs);

		// System.out.println(session.getAttribute("loggedUser").toString());
		// model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		
		return "welcome";
	}

	@PostMapping({"/","/welcome"})
	public String welcomePost(@ModelAttribute("contactUs") ContactUsForm contactUs, HttpSession session) {

		contactUs.setDate(new Date().toString());
		homeDAO.save(contactUs);

		return "welcome";
	}

	@GetMapping({"/doctor/application"})
	public String applyDoctor(Model model, HttpSession session) {

		DoctorApplicant application = new DoctorApplicant();

		application.setApplicationDate(java.time.LocalDate.now().toString());

		model.addAttribute("application", application);
		
		return "doctorapplication";
	}

	@PostMapping({"/doctor/application"})
	public String applyDoctorPost(@ModelAttribute("application") DoctorApplicant application,@RequestParam("file") MultipartFile file, Model model, HttpSession session) {

		doctorApplicantDAO.save(application);
		int id = doctorApplicantDAO.getLastID();
		if(!file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            doctorApplicantDAO.updateProfile(id, filename);
			String uploadDir = "user-photos/resume/"  + application.getApplicantName();
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
            }
        }
		return "/welcome";
	}
    
}
