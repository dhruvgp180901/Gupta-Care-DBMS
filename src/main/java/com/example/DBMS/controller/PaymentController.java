package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.AppointmentDAO;
import com.example.DBMS.dao.BookroomDAO;
import com.example.DBMS.dao.DoctorDAO;
// import com.example.DBMS.dao.MyorderDAO;
import com.example.DBMS.dao.OrderDAO;
import com.example.DBMS.dao.RoomDAO;
import com.example.DBMS.dao.TestDAO;
import com.example.DBMS.dao.PaymentDAO;
import com.example.DBMS.dao.TestbookingDAO;
// import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bookroom;
import com.example.DBMS.model.Doctor;
import com.example.DBMS.model.Order;
import com.example.DBMS.model.Payment;
import com.example.DBMS.model.Testbooking;
import com.example.DBMS.model.User;
import com.example.DBMS.model.Room;
import com.example.DBMS.model.Test;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;
import com.example.DBMS.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    @Autowired
    private AppointmentDAO appointmentDAO;
    @Autowired
    private TestbookingDAO testbookingDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private BookroomDAO bookroomDAO;
    // @Autowired
    // private MyorderDAO myorderDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private DoctorDAO doctorDAO;
    @Autowired
    private PaymentDAO paymentDAO;
    @Autowired
    private ToastService toastService;
    @Autowired
    private AuthenticateService authenticateService;


	@GetMapping("/payment/{appointmentID}")
	public String showbill(@PathVariable("appointmentID") int id, Model model, HttpSession session) {


        Payment payment = new Payment();
        payment.setPurposeID(id);

        Appointment appointment = appointmentDAO.findByID(id);

        String doctorname = appointment.getDoctorName();
        Doctor doctor = doctorDAO.findByDoctorname(doctorname);

        payment.setAmount(doctor.getAppointmentCost());
        String date = new Date().toString();
        payment.setPayDate(date);

        model.addAttribute("payment", payment);
        model.addAttribute("id", id);
        model.addAttribute("date", date);

        return "makepayment";
	}

    @PostMapping("/payment/{appointmentID}")
	public String paymentPost(@ModelAttribute("payment") Payment payment, @PathVariable("appointmentID") int id, Model model,RedirectAttributes redirectAttributes) {

        payment.setStatus("Confirmed");
        payment.setPurpose("Appointment");
        String date = new Date().toString();
        payment.setPayDate(date);


        Appointment appointment = appointmentDAO.findByID(id);

        appointment.setStatus("Confirmed");


        paymentDAO.save(payment);
        model.addAttribute("payment", payment);
        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Made Succesfully...");
        return "redirect:/welcome";

	}

    @GetMapping("/testpayment/{testbookingID}")
	public String showtestbill(@PathVariable("testbookingID") int id, Model model, HttpSession session) {


        Payment payment = new Payment();
        payment.setPurposeID(id);

        Testbooking testbooking = testbookingDAO.findByID(id);
        String testname = testbooking.getTestName();
        Test test = testDAO.findByTestName(testname);

        payment.setAmount(test.getCost());
        payment.setPayDate(new Date().toString());

        model.addAttribute("payment", payment);
        model.addAttribute("id", id);

        return "makepayment";
	}

    @PostMapping("/testpayment/{testbookingID}")
	public String testpaymentPost(@ModelAttribute("payment") Payment payment, @PathVariable("testbookingID") int id, Model model,RedirectAttributes redirectAttributes) {

        payment.setStatus("Confirmed");
        payment.setPurpose("TestBooking");

        Testbooking testbooking = testbookingDAO.findByID(id);

        testbooking.setStatus("Confirmed");

        paymentDAO.save(payment);
        
        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Made Succesfully...");
        return "redirect:/welcome";
	}


    @GetMapping("/payroom/{bookroomID}")
	public String showroombill(@PathVariable("bookroomID") int id, Model model, HttpSession session) {

        Payment payment = new Payment();
        payment.setPurposeID(id);

        Bookroom bookroom = bookroomDAO.findByID(id);

        Room room = roomDAO.findByID(bookroom.getRoomID());
        payment.setAmount(room.getCost());
        payment.setPayDate(new Date().toString());

        model.addAttribute("payment", payment);
        model.addAttribute("id", id);

        return "makepayment";
	}

    @PostMapping("/payroom/{bookroomID}")
	public String paymentRoomPost(@ModelAttribute("payment") Payment payment, @PathVariable("bookroomID") int id, Model model, RedirectAttributes redirectAttributes) {

        payment.setStatus("Confirmed");
        payment.setPurpose("Room Booking");
        paymentDAO.save(payment);


        Bookroom bookroom = bookroomDAO.findByID(id);

        bookroom.setStatus("Confirmed");

        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Made Succesfully...");
        return "redirect:/welcome";

	}
    
}
