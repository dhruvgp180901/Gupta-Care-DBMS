package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
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
public class RegisterController {

    @Autowired
	 private UserDAO userDAO;
	 @Autowired
	 private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;

    @GetMapping("/register")
	 public String showForm(Model model,HttpSession session) {

		if (authenticateService.isAuthenticated(session)) {
			return "redirect:/welcome";
		}
		User user = new User();		 
	 	model.addAttribute("user", user);
	    return "register";
	 }

	 @PostMapping("/register")
	 public String submitForm(@ModelAttribute("user") User user, Model model, BindingResult bindingResult,HttpSession session, RedirectAttributes redirectAttributes) {
		
		// userValidator.validate(userForm, bindingResult);
	    
		// if (bindingResult.hasErrors()) {
	    	
	    //     return "register";
	    // }

		String username=user.getUsername();
        String errorMessage = null;

		try {
            if (!userDAO.userExists(username)) {

				user.setRole("patient");
				user.setPhoto("mypic");
				System.out.println(user.getUsername());
				userDAO.save(user);
				System.out.println(user.getUsername());

                // toastService.redirectWithSuccessToast(redirectAttributes, "Successfully Registered...");
                return "redirect:/login";

            }
            errorMessage = "This username already exists.";
        } catch (Exception e) {
            errorMessage = "This username can't be taken. Please change it...";
        }
	    
		model.addAttribute("user", user);
        toastService.displayErrorToast(model, errorMessage);
        return "/register";
		
	    // securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

	}
    
}
