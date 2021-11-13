// package com.example.DBMS.controller;

// import java.security.Principal;
// import java.sql.Time;
// import java.util.Date;
// import java.util.List;

// import javax.servlet.http.HttpSession;

// import com.example.DBMS.Utils.FileUploadUtil;
// import com.example.DBMS.dao.AppointmentDAO;
// import com.example.DBMS.dao.BookroomDAO;
// import com.example.DBMS.dao.MyorderDAO;
// import com.example.DBMS.dao.OrderDAO;
// import com.example.DBMS.dao.RoomDAO;
// import com.example.DBMS.dao.PaymentDAO;
// import com.example.DBMS.dao.TestbookingDAO;
// // import com.example.DBMS.Utils.FileUploadUtil;
// import com.example.DBMS.dao.UserDAO;
// import com.example.DBMS.model.Appointment;
// import com.example.DBMS.model.Bookroom;
// import com.example.DBMS.model.Myorder;
// import com.example.DBMS.model.Order;
// import com.example.DBMS.model.Payment;
// import com.example.DBMS.model.Testbooking;
// import com.example.DBMS.model.User;
// import com.example.DBMS.model.Room;
// import com.example.DBMS.service.AuthenticateService;
// import com.example.DBMS.service.ToastService;
// import com.example.DBMS.validator.UserValidator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;

// @Controller
// public class PaymentController {

//     @Autowired
//     private AppointmentDAO appointmentDAO;
//     @Autowired
//     private TestbookingDAO testbookingDAO;
//     @Autowired
//     private OrderDAO orderDAO;
//     @Autowired
//     private BookroomDAO bookroomDAO;
//     @Autowired
//     private MyorderDAO myorderDAO;
//     @Autowired
//     private UserDAO userDAO;
//     @Autowired
//     private RoomDAO roomDAO;
//     @Autowired
//     private PaymentDAO paymentDAO;
//     @Autowired
//     private ToastService toastService;
//     @Autowired
//     private UserValidator userValidator;
//     @Autowired
//     private AuthenticateService authenticateService;


// 	@GetMapping("/payment/{appointmentID}")
// 	public String showbill(@PathVariable("appointmentID") int id, Model model, HttpSession session) {


//         Payment payment = new Payment();
//         payment.setPurposeID(id);

//         Appointment appointment = appointmentDAO.findByID(id);

//         payment.setAmount(appointment.getCost());
//         String date = new Date().toString();
//         payment.setPayDate(date);

//         int amount = appointment.getCost();
//         amount += amount/10;
//         model.addAttribute("payment", payment);
//         model.addAttribute("id", id);
//         model.addAttribute("date", date);
//         model.addAttribute("cost", appointment.getCost());
//         model.addAttribute("amount", amount);



//         return "makepayment";
// 	}

//     @PostMapping("/payment/{appointmentID}")
// 	public String paymentPost(@ModelAttribute("payment") Payment payment, @PathVariable("appointmentID") int id, Model model) {

//         payment.setStatus("Confirmed");
//         payment.setPurpose("Appointment");


//         paymentDAO.save(payment);
//         model.addAttribute("payment", payment);

//         return "payconfirm";
// 	}

//     @GetMapping("/testpayment/{testbookingID}")
// 	public String showtestbill(@PathVariable("testbookingID") int id, Model model, HttpSession session) {


//         Payment payment = new Payment();
//         payment.setPurposeID(id);

//         Testbooking testbooking = testbookingDAO.findByID(id);

//         payment.setAmount(testbooking.getCost());
//         payment.setPayDate(new Date().toString());

//         model.addAttribute("payment", payment);
//         model.addAttribute("id", id);

//         return "makepayment";
// 	}

//     @PostMapping("/testpayment/{testbookingID}")
// 	public String testpaymentPost(@ModelAttribute("payment") Payment payment, @PathVariable("testbookingID") int id, Model model) {

//         payment.setStatus("Confirmed");
//         payment.setPurpose("TestBooking");


//         paymentDAO.save(payment);
//         model.addAttribute("payment", payment);

//         return "payconfirm";
// 	}

//     @GetMapping("/paymedicine")
//      public String paymentMedicine(Model model,HttpSession session) {
 
//         String username = authenticateService.getCurrentUser(session);
//         List<Order> list = orderDAO.findByUsername(username);

//         int total = 0;

//         for(int i=0;i<list.size();i++) {
//            total += (list.get(i).getAmount() * list.get(i).getCost() );
//         }

//         Payment payment = new Payment();

//         User user = userDAO.findByUsername(username);
//         payment.setPurposeID(user.getUserID());

//         payment.setAmount(total);
//         payment.setPayDate(new Date().toString());

//         model.addAttribute("payment", payment);
//         model.addAttribute("id", user.getUserID());

//         model.addAttribute("payment",payment);

//         return "makepayment";
//     }

//     @PostMapping("/paymedicine")
// 	public String paymentMedicinePost(@ModelAttribute("payment") Payment payment, @PathVariable("userID") int id, Model model, HttpSession session) {

//         payment.setStatus("Confirmed");
//         payment.setPurpose("MedicineOrder");


//         paymentDAO.save(payment);
//         model.addAttribute("payment", payment);

//         String username = authenticateService.getCurrentUser(session);
//         List<Order> list = orderDAO.findByUsername(username);

//         int lastpayment = paymentDAO.getLastID();


//         for(int i = 0; i < list.size(); i++) {
    
//             Myorder myorder = new Myorder();

//             myorder.setPaymentID(lastpayment);
//             myorder.setOrderID(list.get(i).getOrderID());
            
//             myorderDAO.save(myorder);
//         }

//         return "payconfirm";
// 	}

//     @GetMapping("/payroom/{bookroomID}")
// 	public String showroombill(@PathVariable("bookroomID") int id, Model model, HttpSession session) {


//         Payment payment = new Payment();
//         payment.setPurposeID(id);

//         Bookroom bookroom = bookroomDAO.findByID(id);

//         Room room = roomDAO.findByID(bookroom.getRoomID());
//         payment.setAmount(room.getCost());
//         payment.setPayDate(new Date().toString());

//         model.addAttribute("payment", payment);
//         model.addAttribute("id", id);

//         return "makepayment";
// 	}

//     @PostMapping("/payment/{bookroomID}")
// 	public String paymentRoomPost(@ModelAttribute("payment") Payment payment, @PathVariable("bookroomID") int id, Model model) {

//         payment.setStatus("Confirmed");
//         payment.setPurpose("Room Booking");
//         paymentDAO.save(payment);
//         model.addAttribute("payment", payment);

//         return "payconfirm";
// 	}
    
// }
