package com.example.DBMS.controller;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.HostName;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.EmailSenderService;
import com.example.DBMS.service.ToastService;
import com.example.DBMS.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 @Autowired
	 EmailSenderService emailSenderService;

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
			// if(user.getPassword() != user.getPasswordConfirm()) {

			// 	errorMessage = "Password and Confirm Password do not match...";

			// 	model.addAttribute("user", user);
       	 	// 	toastService.displayErrorToast(model, errorMessage);
        	// 	return "/register";
			// }
            if (!userDAO.userExists(username)) {
				user.setRole("Patient");
				user.setActive(0);
				System.out.println(user.getUsername());
				System.out.println("#4#");
				System.out.println(user.getUsername());


				////////////////////////Email-Verification/////////////////////
				String token = UUID.randomUUID().toString();
				user.setToken(token);
				userDAO.save(user);

				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(user.getEmailID());
				mailMessage.setSubject("Complete Registration!");
				mailMessage.setFrom("guptacare18@gmail.com");
				mailMessage.setText("Your account has been registered on Gupta-Care. To confirm your account, please click here : "
						+HostName.getHost()+"confirm-account?token="+token);
				
				///////////////////////Email-Verification/////////////////////
				
				emailSenderService.sendEmail(mailMessage);

                toastService.redirectWithSuccessToast(redirectAttributes, "Successfully Registered...");
                return "redirect:/login";

            }
			
            errorMessage = "This username already exists.";
        } catch (Exception e) {
            errorMessage = "This username can't be taken. Please change it...";
        }
	    
		model.addAttribute("user", user);
        toastService.displayErrorToast(model, errorMessage);
        return "/register";
		
	}

	@GetMapping("/confirm-account")
	 public String confirmAccountRegister(@RequestParam("token")String token,Model model,HttpSession session, RedirectAttributes redirectAttributes) {

		User user = userDAO.findByConfirmationToken(token);

		if(user != null)
	    {
	    	 userDAO.updateActivity(user.getUsername(), 1);
			 toastService.redirectWithSuccessToast(redirectAttributes, "Account Confirmed Successfully...");

	    	 return "redirect:/login";
	    }
	    else
	    {	
			toastService.redirectWithErrorToast(redirectAttributes, "Account Confirmed Not Successfull...");	
	        return "redirect:/welcome";
	    }

	}
    
}
