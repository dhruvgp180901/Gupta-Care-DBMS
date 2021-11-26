package com.example.DBMS.controller;

import java.security.Principal;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.Utils.HostName;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    Instamojo api;


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
        payment.setPurpose("Appointment");

        paymentDAO.save(payment);

        int payid = paymentDAO.getLastID();

        String clientId = "test_BRySN9XGrlTv2X8mI6RUg9JCBeGd8tuU2tK";
		String clientSecret = "test_KjbkFknjnErbAweyrUKGK5GrPUgANWMN6wUxskqNzZg12q4oYeoB43InzGPH9sM5w5QriiOSI7e5F4wqaL0DmgMvQOHPCgzl8nEkBMVcdTCEuqXqYnQh7gSpvPF";

	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
	    api = new InstamojoImpl(context);

		PaymentOrder order = new PaymentOrder();
		order.setName(appointment.getPatientName());
		order.setEmail("guptacare18@gmail.com");
		order.setPhone("7404528473");
		order.setCurrency("INR");
		order.setAmount((double) doctor.getAppointmentCost());
		order.setDescription("Appointment");
		
		order.setRedirectUrl(HostName.getHost()+"payment/" + id + "/paid/" + payid);
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

    @GetMapping("/payment/{appointmentID}/paid/{payid}")
	public String paymentPost(@RequestParam("transaction_id")String transactionid, @PathVariable("appointmentID") int id,@PathVariable("payid") int payid, Model model,RedirectAttributes redirectAttributes) {

        paymentDAO.updateTransaction(payid, transactionid);
        appointmentDAO.updateStatus(id, "Confirmed");

        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Successfull");

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
        payment.setPurpose("TestBooking");

        paymentDAO.save(payment);

        int payid = paymentDAO.getLastID();

        String clientId = "test_BRySN9XGrlTv2X8mI6RUg9JCBeGd8tuU2tK";
		String clientSecret = "test_KjbkFknjnErbAweyrUKGK5GrPUgANWMN6wUxskqNzZg12q4oYeoB43InzGPH9sM5w5QriiOSI7e5F4wqaL0DmgMvQOHPCgzl8nEkBMVcdTCEuqXqYnQh7gSpvPF";

	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
	    api = new InstamojoImpl(context);

		PaymentOrder order = new PaymentOrder();
		order.setName(testbooking.getPatientName());
		order.setEmail("guptacare18@gmail.com");
		order.setPhone("7404528473");
		order.setCurrency("INR");
		order.setAmount((double) test.getCost());
		order.setDescription("Test Booking");
		
		order.setRedirectUrl(HostName.getHost()+"testpayment/" + id + "/paid/" + payid);
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

    @GetMapping("/testpayment/{testbookingID}/paid/{payid}")
	public String testpaymentPost(@RequestParam("transaction_id") String transactionid,  @PathVariable("payid") int payid, @PathVariable("testbookingID") int id, Model model,RedirectAttributes redirectAttributes) {


        paymentDAO.updateTransaction(payid, transactionid);
        System.out.println(id);
        testbookingDAO.updateStatus(id, "Confirmed");

        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Successfull");

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
        payment.setPurpose("Room Booking");

        paymentDAO.save(payment);

        int payid = paymentDAO.getLastID();


        String clientId = "test_BRySN9XGrlTv2X8mI6RUg9JCBeGd8tuU2tK";
		String clientSecret = "test_KjbkFknjnErbAweyrUKGK5GrPUgANWMN6wUxskqNzZg12q4oYeoB43InzGPH9sM5w5QriiOSI7e5F4wqaL0DmgMvQOHPCgzl8nEkBMVcdTCEuqXqYnQh7gSpvPF";

	 	ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
	    api = new InstamojoImpl(context);

		PaymentOrder order = new PaymentOrder();
		order.setName(bookroom.getUsername());
		order.setEmail("guptacare18@gmail.com");
		order.setPhone("7404528473");
		order.setCurrency("INR");
		order.setAmount((double) room.getCost());
		order.setDescription("Test Booking");
		
		order.setRedirectUrl(HostName.getHost()+"roompayment/" + id + "/paid/" + payid);
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

    @GetMapping("/roompayment/{bookroomID}/paid/{payid}")
	public String paymentRoomPost(@RequestParam("transaction_id") String transactionid, @PathVariable("payid") int payid,@PathVariable("bookroomID") int id, Model model, RedirectAttributes redirectAttributes) {


        paymentDAO.updateTransaction(payid, transactionid);
        System.out.println(id);
        bookroomDAO.updateStatus(id, "Confirmed");

        toastService.redirectWithSuccessToast(redirectAttributes, "Payment Successfull");

        return "redirect:/welcome";

	}
    
}
