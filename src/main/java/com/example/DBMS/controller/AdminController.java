package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.AppointmentDAO;
import com.example.DBMS.dao.BookroomDAO;
import com.example.DBMS.dao.DoctorApplicantDAO;
import com.example.DBMS.dao.DoctorDAO;
import com.example.DBMS.dao.FeedbackDAO;
import com.example.DBMS.dao.HomeDAO;
import com.example.DBMS.dao.MedicineDAO;
import com.example.DBMS.dao.PaymentDAO;
import com.example.DBMS.dao.PayorderMedDAO;
import com.example.DBMS.dao.PayoutDAO;
import com.example.DBMS.dao.RoomDAO;
import com.example.DBMS.dao.TestDAO;
import com.example.DBMS.dao.TestbookingDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.dao.WorkDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Bookroom;
import com.example.DBMS.model.ContactUsForm;
import com.example.DBMS.model.Doctor;
import com.example.DBMS.model.DoctorApplicant;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.Medicine;
import com.example.DBMS.model.Payment;
import com.example.DBMS.model.PayorderMed;
import com.example.DBMS.model.Payout;
import com.example.DBMS.model.Room;
import com.example.DBMS.model.Test;
import com.example.DBMS.model.Testbooking;
import com.example.DBMS.model.User;
import com.example.DBMS.model.Work;
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
public class AdminController {

    @Autowired
    private AuthenticateService authenticateService;

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private DoctorApplicantDAO applicantDAO;
    @Autowired
    private PayoutDAO payoutDAO;
    @Autowired
    private WorkDAO workDAO;
    @Autowired
    private PayorderMedDAO payorderMedDAO;
    @Autowired
    private PaymentDAO paymentDAO;
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private FeedbackDAO feedbackDAO;
    @Autowired
    private HomeDAO homeDAO;
    @Autowired
    private MedicineDAO medicineDAO;
    @Autowired
    private DoctorDAO doctorDAO;
    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private BookroomDAO bookroomDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private TestbookingDAO testbookingDAO;
    @Autowired
    private ToastService toastService;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String username = authenticateService.getCurrentUser(session);
        User user = userDAO.findByUsername(username);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
        System.out.println(username);

        
            model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        if(!(username.equals("admin")))
		{
			toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
			return "redirect:/badAuthorize";
		}

        return "dashboard/index";
        
		

        // model.addAttribute("username", authenticateService.getCurrentUser(session));
        
    }

    @GetMapping("/dashboard/manage/users")
    public String usersDashboard(Model model, HttpSession session,RedirectAttributes redirectAttributes) {


        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String username = authenticateService.getCurrentUser(session);
        User user = userDAO.findByUsername(username);
        // String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(user.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        model.addAttribute("username", authenticateService.getCurrentUser(session));

        List<User> users = userDAO.allusers();

        model.addAttribute("users", users);

        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(0));
        }
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);
        return "dashboard/users";
    }

    @GetMapping("/dashboard/manage/user/{username}")
    public String userDashboard(@PathVariable("username") String username, Model model, HttpSession session,RedirectAttributes redirectAttributes) {

        // model.addAttribute("username", authenticateService.getCurrentUser(session));

        String loginMessage = "Please Sign in to proceed!!!";
		// if(!authenticateService.isAuthenticated(session))
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
		// 	return "redirect:/login";
		// }

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        User user = userDAO.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/user";
    }

    @PostMapping("/dashboard/manage/user/{username}")
    public String userDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/user/edit/" + username;
    }

    @GetMapping("/dashboard/manage/user/edit/{username}")
    public String userEditDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		// if(!authenticateService.isAuthenticated(session))
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
		// 	return "redirect:/login";
		// }

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        User user = userDAO.findByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/updateUser";
    }

    @PostMapping("/dashboard/manage/user/edit/{username}")
    public String userEditDashboardPost(@ModelAttribute("user") User user, @PathVariable("username") String username,@RequestParam("file") MultipartFile file,Model model, HttpSession session) {

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


        return "redirect:/dashboard/manage/user/" + username;
    }

    @GetMapping("/dashboard/user/delete/{username}")
    public String userDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        // model.addAttribute("username", authenticateService.getCurrentUser(session));

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        User user = userDAO.findByUsername(username);

        // check if possible to delete the user

        userDAO.delete(username);
        System.out.println(user);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/users";
    }

    @GetMapping("/dashboard/user/add")
    public String userAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addUser";
    }

    @PostMapping("/dashboard/user/add")
    public String userAddDashboardPost(@ModelAttribute("user") User user, HttpSession session) {

        user.setPhoto("adminpic");
        userDAO.save(user);

        return "redirect:/dashboard/manage/users";
    }

    @GetMapping("/dashboard/manage/doctors")
    public String doctorsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Doctor> doctors = doctorDAO.allDoctors();

        model.addAttribute("doctors", doctors);

        for (int i = 0; i < doctors.size(); i++) {
            System.out.println(doctors.get(0));
        }
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);
        return "dashboard/doctors";
    }

    @GetMapping("/dashboard/manage/doctor/{username}")
    public String doctorDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Doctor doctor = doctorDAO.findByDoctorname(username);

        List<Work> works = workDAO.findByUsername(username);

        
        model.addAttribute("username", username);
        model.addAttribute("doctor", doctor);
        model.addAttribute("works", works);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/doctor";
    }

    @PostMapping("/dashboard/manage/doctor/{username}")
    public String doctorDashboardPost(@PathVariable("username") String username, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/doctor/edit/" + username;
    }

    @GetMapping("/dashboard/manage/doctor/edit/{username}")
    public String doctorEditDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Doctor doctor = doctorDAO.findByDoctorname(username);

        model.addAttribute("doctor", doctor);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/updateDoctor";
    }

    @PostMapping("/dashboard/manage/doctor/edit/{username}")
    public String doctorEditDashboardPost(@PathVariable("username") String username,
            @ModelAttribute("doctor") Doctor doctor, Model model, HttpSession session) {
        doctorDAO.update(doctor.getSpecialization(), doctor.getDesignation(), doctor.getAppointmentCost(),doctor.getSalary(),
                doctor.getParentName(), doctor.getParentOccupation(), doctor.getCollegeGrad(),
                doctor.getPercentageGrad(), doctor.getCollegePGrad(), doctor.getPercentagePGrad(),
                doctor.getBoard10th(), doctor.getPercentage10th(), doctor.getBoard12th(), doctor.getPercentage12th(),
                username);

        return "redirect:/dashboard/manage/doctor/" + username;
    }

    @GetMapping("/dashboard/doctor/delete/{username}")
    public String doctorDeleteDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        workDAO.delete(username);
        payoutDAO.delete(username);
        
        doctorDAO.delete(username);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);


        return "redirect:/dashboard/manage/doctors";
    }

    @GetMapping("/dashboard/manage/doctor/payouts/{username}")
    public String doctorPayoutDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Payout> payouts = payoutDAO.findByUsername(username);

        model.addAttribute("payouts", payouts);

        model.addAttribute("username", username);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/payouts";
    }

    @GetMapping("/dashboard/doctor/{username}/work/add")
    public String doctorWorkAddDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Work work = new Work();
        model.addAttribute("work", work);
        model.addAttribute("username", username);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addWork";
    }

    @PostMapping("/dashboard/doctor/{username}/work/add")
    public String doctorWorkAddDashboardPost(@PathVariable("username") String username,@ModelAttribute("work") Work work, Model model, HttpSession session) {

        work.setUsername(username);

        workDAO.save(work);

        return "redirect:/dashboard/manage/doctor/" + username;
    }

    @GetMapping("/dashboard/delete/doctor/work/{id}")
    public String doctorWorkDeleteDashboard(@PathVariable("id") int workid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Work work = workDAO.findByID(workid);

        String username = work.getUsername();

        workDAO.deleteByID(workid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/doctor/" + username;
    }

    @GetMapping("/dashboard/delete/payout/{id}")
    public String doctorPayoutDeleteDashboard(@PathVariable("id") int payoutid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        
        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Payout payout = payoutDAO.findByID(payoutid);

        String username = payout.getUsername();

        payoutDAO.deleteByID(payoutid);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/doctor/payouts/" + username;
    }

    @GetMapping("/dashboard/{username}/payout/add")
    public String doctorPayoutAddDashboard(@PathVariable("username") String username, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Payout payout = new Payout();
        model.addAttribute("payout", payout);

        model.addAttribute("username", username);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addPayout";
    }

    @PostMapping("/dashboard/{username}/payout/add")
    public String doctorPayoutAddDashboardPost(@PathVariable("username") String username,@ModelAttribute("payout") Payout payout, Model model, HttpSession session) {

        payout.setUsername(username);

        payoutDAO.save(payout);

        return "redirect:/dashboard/manage/doctor/payouts/" + username;
    }

    @GetMapping("/dashboard/doctor/add")
    public String doctorAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Doctor doctor = new Doctor();

        model.addAttribute("doctor", doctor);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addDoctor";
    }

    @PostMapping("/dashboard/doctor/add")
    public String doctorAddDashboardPost(@ModelAttribute("doctor") Doctor doctor, Model model, HttpSession session) {

        doctorDAO.save(doctor);

        return "redirect:/dashboard/manage/doctors";
    }

    @GetMapping("/dashboard/manage/testBookings")
    public String testBookingsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Testbooking> testbookings = testbookingDAO.alltestbookings();

        model.addAttribute("testbookings", testbookings);

        List<Integer> costs = new ArrayList<Integer>();

        for (int i = 0; i < testbookings.size(); i++) {

            String testName = (testbookings.get(i)).getTestName();

            Test test = testDAO.findByTestName(testName);

            costs.add(test.getCost());
        }

        model.addAttribute("costs", costs);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/testBookings";
    }

    @GetMapping("/dashboard/delete/testbooking/{id}")
    public String testBookingDeleteDashboard(@PathVariable("id") int testbookingID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }
        
        testbookingDAO.delete(testbookingID);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/testBookings";
    }

    @GetMapping("/dashboard/manage/roomBookings")
    public String roomBookingsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Bookroom> roombookings = bookroomDAO.allbookrooms();

        for (int i = 0; i < roombookings.size(); i++) {
            System.out.println(roombookings.get(i));
        }

        List<Room> rooms = new ArrayList<Room>();

        for (int i = 0; i < roombookings.size(); i++) {

            Room room = roomDAO.findByID(roombookings.get(i).getRoomID());
            rooms.add(room);
        }

        model.addAttribute("roombookings", roombookings);
        model.addAttribute("rooms", rooms);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/roomBookings";
    }

    @GetMapping("/dashboard/delete/roombooking/{id}")
    public String roomBookingDeleteDashboard(@PathVariable("id") int roombookingID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        bookroomDAO.delete(roombookingID);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/roomBookings";
    }

    @GetMapping("/dashboard/manage/appointments")
    public String appointmentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Appointment> appointments = appointmentDAO.allappointments();

        List<Integer> costs = new ArrayList<Integer>();

        for (int i = 0; i < appointments.size(); i++) {
            System.out.println(appointments.get(i));
            String doctorName = appointments.get(i).getDoctorName();
            Doctor doctor = doctorDAO.findByDoctorname(doctorName);
            System.out.println(doctor);
            costs.add(doctor.getAppointmentCost());
        }
        model.addAttribute("costs", costs);
        model.addAttribute("appointments", appointments);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/appointments";
    }

    @GetMapping("/dashboard/delete/appointment/{id}")
    public String appointmentDeleteDashboard(@PathVariable("id") int appointmentID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        appointmentDAO.delete(appointmentID);
        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/appointments";
    }

    @GetMapping("/dashboard/manage/medicines")
    public String medicinesDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Medicine> medicines = medicineDAO.allmedicines();

        model.addAttribute("medicines", medicines);

        for (int i = 0; i < medicines.size(); i++) {
            System.out.println(medicines.get(i));
        }

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/medicines";
    }

    @GetMapping("/dashboard/manage/medicine/{id}")
    public String medicineDashboard(@PathVariable("id") Integer medicineID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Medicine medicine = medicineDAO.findByID(medicineID);

        model.addAttribute("medicine", medicine);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/medicine";
    }

    @PostMapping("/dashboard/manage/medicine/{id}")
    public String medicineDashboardPost(@PathVariable("id") Integer medicineID, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/medicine/edit/" + medicineID;
    }

    @GetMapping("/dashboard/manage/medicine/edit/{id}")
    public String medicineUpdateDashboard(@PathVariable("id") Integer medicineID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Medicine medicine = medicineDAO.findByID(medicineID);

        model.addAttribute("medicine", medicine);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/updateMedicine";
    }

    @PostMapping("/dashboard/manage/medicine/edit/{id}")
    public String medicineUpdateDashboardPost(@ModelAttribute("medicine") Medicine medicine,
            @PathVariable("id") Integer medicineID, @RequestParam("file") MultipartFile file ,Model model, HttpSession session) {

            if(!file.isEmpty()) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                medicineDAO.updateProfile(medicineID, filename);
                System.out.println(medicineID);
                String uploadDir = "medicine-photos/"  + medicineID;
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

        medicineDAO.update(medicine.getPurpose(), medicine.getDescription(),
                medicine.getDeliveredAmount(), medicine.getDeliveredDate().toString(), medicineID);

        return "redirect:/dashboard/manage/medicine/" + medicineID;
    }

    @GetMapping("/dashboard/medicine/delete/{id}")
    public String medicineDeleteDashboard(@PathVariable("id") int medicineID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        medicineDAO.delete(medicineID);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/medicines";
    }

    @GetMapping("/dashboard/medicine/add")
    public String medicineAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Medicine medicine = new Medicine();

        model.addAttribute("medicine", medicine);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addMedicine";
    }

    @PostMapping("/dashboard/medicine/add")
    public String medicineAddDashboardPost(@ModelAttribute("medicine") Medicine medicine, HttpSession session) {

        medicineDAO.save(medicine);

        return "redirect:/dashboard/manage/medicines";
    }

    @GetMapping("/dashboard/manage/tests")
    public String testsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Test> tests = testDAO.alltests();

        model.addAttribute("tests", tests);

        for (int i = 0; i < tests.size(); i++) {
            System.out.println(tests.get(0));
        }

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);
        return "dashboard/tests";
    }

    @GetMapping("/dashboard/manage/test/{id}")
    public String testDashboard(@PathVariable("id") Integer testid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Test test = testDAO.findByID(testid);
        model.addAttribute("test", test);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/test";
    }

    @GetMapping("/dashboard/test/delete/{id}")
    public String testDeleteDashboard(@PathVariable("id") int testID, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        testDAO.delete(testID);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/tests";
    }

    @PostMapping("/dashboard/manage/test/{id}")
    public String testDashboardPost(@PathVariable("id") Integer testid, Model model, HttpSession session) {

        return "redirect:/dashboard/manage/test/edit/" + testid;
    }

    @GetMapping("/dashboard/manage/test/edit/{id}")
    public String testEditDashboard(@PathVariable("id") Integer testid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Test test = testDAO.findByID(testid);

        model.addAttribute("test", test);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/updateTest";
    }

    @PostMapping("/dashboard/manage/test/edit/{id}")
    public String testEditDashboardPost(@ModelAttribute("test") Test test, @PathVariable("id") Integer testid,
            @RequestParam("file") MultipartFile file,Model model, HttpSession session) {
        

            if(!file.isEmpty()) {
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                testDAO.updateProfile(testid, filename);
                System.out.println(testid);
                String uploadDir = "test-photos/"  + testid;
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

        testDAO.update(test.getSetupDate(), test.getDescription(), test.getCost(), testid);

        return "redirect:/dashboard/manage/test/" + testid;
    }

    @GetMapping("/dashboard/test/add")
    public String testAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Test test = new Test();

        model.addAttribute("test", test);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addTest";
    }

    @PostMapping("/dashboard/test/add")
    public String testAddDashboardPost(@ModelAttribute("test") Test test, HttpSession session) {

        testDAO.save(test);

        return "redirect:/dashboard/manage/tests";
    }

    @GetMapping("/dashboard/manage/rooms")
    public String roomsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Room> rooms = roomDAO.allrooms();

        model.addAttribute("rooms", rooms);

        for (int i = 0; i < rooms.size(); i++) {
            System.out.println(rooms.get(0));
        }

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);
        return "dashboard/rooms";
    }

    @GetMapping("/dashboard/delete/room/{id}")
    public String roomDeleteDashboard(@PathVariable("id") Integer roomid,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        roomDAO.delete(roomid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/rooms";
    }

    @GetMapping("/dashboard/room/add")
    public String roomAddDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        
        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        Room room = new Room();

        model.addAttribute("room", room);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/addRoom";
    }

    @PostMapping("/dashboard/room/add")
    public String roomAddDashboardPost(@ModelAttribute("room") Room room, HttpSession session) {

        roomDAO.save(room);

        return "redirect:/dashboard/manage/rooms";
    }

    @GetMapping("/dashboard/manage/medicineOrders")
    public String orderMedsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Payment> payments = paymentDAO.allMedicinepayments();
        List<String> usernames =  new ArrayList<String>();

        for(int i=0; i< payments.size(); i++) {

            User user = userDAO.findByID(payments.get(i).getPurposeID());

            usernames.add(user.getUsername());
        }

        model.addAttribute("payments", payments);
        model.addAttribute("usernames", usernames);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);


        return "dashboard/medicineOrders";
    }

    @GetMapping("/dashboard/delete/medicineOrder/{id}")
    public String paymentDeleteDashboard(@PathVariable("id") Integer paymentid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<PayorderMed> payorders = payorderMedDAO.findByPaymentID(paymentid);

        for(int i = 0; i < payorders.size();i++) {

            payorderMedDAO.delete(payorders.get(i).getPaymentID());
        }
        paymentDAO.delete(paymentid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/medicineOrders";
    }

    @GetMapping("/dashboard/manage/medicineOrder/{id}")
    public String payorderMedicineDashboard(@PathVariable("id") Integer paymentid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<PayorderMed> payorders = payorderMedDAO.findByPaymentID(paymentid);

        List<Integer> costs = new ArrayList<Integer>();
        List<Integer> amounts = new ArrayList<Integer>();

        for(int i = 0; i < payorders.size();i++) {

            System.out.println(payorders.get(i));

            int medicineid = payorders.get(i).getMedicineID();
            Medicine medicine = medicineDAO.findByID(medicineid);

            costs.add(medicine.getCost());

            amounts.add(payorders.get(i).getQuantity() * medicine.getCost());
        }

        model.addAttribute("payorders", payorders);
        model.addAttribute("costs", costs);
        model.addAttribute("amounts", amounts);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/payorderMedicines";
    }

    // /dashboard/delete/payorder/' + payorder.payorderMedID

    @GetMapping("/dashboard/delete/payorder/{id}")
    public String payorderDeleteDashboard(@PathVariable("id") Integer payorderid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        PayorderMed payorder = payorderMedDAO.findByID(payorderid);
        int medicineid = payorder.getMedicineID();
        Medicine medicine = medicineDAO.findByID(medicineid);
        int amount = payorder.getQuantity() * medicine.getCost();
        Payment payment = paymentDAO.findByID(payorder.getPaymentID());
        int initialAmount = payment.getAmount();

        initialAmount -= amount;

        paymentDAO.updateAmount(payment.getPaymentID(), initialAmount);

        payorderMedDAO.delete(payorderid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);


        return "redirect:/dashboard/manage/medicineOrder/" + payment.getPaymentID();
    }

    @GetMapping("/dashboard/manage/contactUsForms")
    public String contactUsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<ContactUsForm> forms = homeDAO.allqueries();

        ContactUsForm contactus = new ContactUsForm();

        List<Integer> replies = new ArrayList<Integer>();

        for(int i=0;i<forms.size();i++) {

            int reply = homeDAO.isReply(forms.get(i).getQueryID());
            System.out.println(reply);
            if(reply == 1)
                replies.add(0);
            else
                replies.add(1);
        }

        model.addAttribute("replies", replies);
        model.addAttribute("contactus", contactus);

        model.addAttribute("forms", forms);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/contactUsForms";
    }

    @GetMapping("/dashboard/manage/payments")
    public String paymentsDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		// if(!authenticateService.isAuthenticated(session))
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
		// 	return "redirect:/login";
		// }

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Payment> payments = paymentDAO.allpayments();
        model.addAttribute("payments", payments);


        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/payments";
    }

    @GetMapping("/dashboard/delete/payment/{id}")
    public String paymentsDeleteDashboard(@PathVariable("id") int paymentid,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        paymentDAO.delete(paymentid);

        return "redirect:/dashboard/manage/payments";
    }

    @GetMapping("/dashboard/manage/applicants")
    public String applicantssDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<DoctorApplicant> applicants = applicantDAO.allDoctorApplicants();
        model.addAttribute("applicants", applicants);


        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/applicants";
    }

    @GetMapping("/dashboard/delete/application/{id}")
    public String applicationsDeleteDashboard(@PathVariable("id") int applicationid,Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        applicantDAO.delete(applicationid);

        return "redirect:/dashboard/manage/applicants";
    }

    @GetMapping("/dashboard/delete/contactUsForm/{id}")
    public String contactUsDeleteDashboard(@PathVariable("id") int queryid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        homeDAO.delete(queryid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/contactUsForms";
    }

    @PostMapping("/queries/{id}/reply")
    public String contactUsReplyDashboard(@PathVariable("id") int queryid, @ModelAttribute("contactus") ContactUsForm contactus, HttpSession session) {

        homeDAO.updateReply(queryid, contactus.getReplyGiven());

        return "redirect:/dashboard/manage/contactUsForms";
    }

    @GetMapping("/dashboard/manage/feedbacks")
    public String feedbacksDashboard(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to view this page!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        List<Feedback> feedbacks = feedbackDAO.allfeedbacks();

        model.addAttribute("feedbacks", feedbacks);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "dashboard/feedbacks";
    }

    @GetMapping("/dashboard/delete/feedback/{id}")
    public String feedbackDeleteDashboard(@PathVariable("id") int feedbackid, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        String loggedinUser = authenticateService.getCurrentUser(session);
        User loggeduser = userDAO.findByUsername(loggedinUser);
        String authorizeMessage = "Sorry, You are not authorize to do this operation!!!";
		// if(loggeduser.getRole() != "Admin")
		// {
		// 	toastService.redirectWithErrorToast(redirectAttributes, authorizeMessage);
		// 	return "redirect:/badAuthorize";
		// }

        feedbackDAO.delete(feedbackid);

        model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("loggedUser", loggedUser);

        return "redirect:/dashboard/manage/feedbacks";
    }
}