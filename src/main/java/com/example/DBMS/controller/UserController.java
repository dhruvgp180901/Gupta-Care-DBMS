package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
// import com.example.DBMS.service.ToastService;
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
public class UserController {

    @Autowired
	 private UserDAO userDAO;
	//  @Autowired
	//  private ToastService toastService;
	 @Autowired
	 private UserValidator userValidator;
	 @Autowired
	 private AuthenticateService authenticateService;
	 @Autowired
	 private ToastService toastService;

    @GetMapping("/profile/{username}")
	public String profile(@PathVariable("username") String username,HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		
		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(authenticateService.getCurrentUser(session) != username)
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

		User user = userDAO.findByUsername(username);
		
		model.addAttribute("user", user);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		
		return "profile";
		
	}

	@PostMapping("/profile/{username}")
	public String profilePost(@PathVariable("username") String username) {
		
		return "redirect:/profile/edit/" + username;
		
	}

	@GetMapping("/profile/edit/{username}")
	public String profileEdit(@PathVariable("username") String username,HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		
		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(authenticateService.getCurrentUser(session) != username)
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

		User user = userDAO.findByUsername(username);

		System.out.println(user);
		
		model.addAttribute("user", user);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		
		return "profileupdate";	
	}

    @PostMapping("/profile/edit/{username}")
    public String postEditProfile(@ModelAttribute("user") User user, @PathVariable("username") String username, @RequestParam("file") MultipartFile file, HttpSession session) {

		System.out.println("############");
        if(!file.isEmpty()) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            userDAO.updateProfile(username, filename);
			System.out.println(username);
			String uploadDir = "user-photos/"  + username;
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

		userDAO.update(user.getAdhaarNumber(),user.getStreet(),user.getCity(),user.getState(),user.getCountry(),user.getPhone(),username);
        return "redirect:/profile/" + username;
    }

	@GetMapping("/{username}/change/Password")
	public String passwordChange(@PathVariable("username") String username,HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		
		String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		if(!(authenticateService.getCurrentUser(session).equals(username)))
		{
			toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
			return "redirect:/badAuthorize";
		}

		User user = new User();

		user.setUsername(username);

		model.addAttribute("user", user);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
		User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


		return "changePassword";	
	}

	@PostMapping("/{username}/change/Password")
	public String passwordChangePost(@ModelAttribute("user") User user, @PathVariable("username") String username,HttpSession session, Model model,RedirectAttributes redirectAttributes) {
		
		User registeredUser = userDAO.findByUsername(username);

		if(userDAO.updatePassword(username, registeredUser.getPassword(), user.getOldPassword(), user.getPassword()) == true)
		{
			System.out.println("Hogya");
			toastService.redirectWithSuccessToast(redirectAttributes,"Password Changed Successfully!");
			return "redirect:/welcome";
		}
		else
		{
			System.out.println("Niii Hua");
			toastService.redirectWithErrorToast(redirectAttributes,"Incorrect Password");
			return "redirect:/welcome";

		}
		
	}

    
}
