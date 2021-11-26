package com.example.DBMS.controller;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.Utils.FileUploadUtil;
import com.example.DBMS.dao.BookroomDAO;
import com.example.DBMS.dao.FeedbackDAO;
import com.example.DBMS.dao.MedicineDAO;
import com.example.DBMS.dao.RoomDAO;
import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.Bill;
import com.example.DBMS.model.Bookroom;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.Medicine;
import com.example.DBMS.model.Order;
import com.example.DBMS.model.Room;
import com.example.DBMS.model.User;
import com.example.DBMS.service.AuthenticateService;
import com.example.DBMS.service.ToastService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoomController {

    @Autowired
    private AuthenticateService authenticateService;
    @Autowired
    private ToastService toastService;
    @Autowired
    private BookroomDAO bookroomDAO;
    @Autowired
    private RoomDAO roomDAO;
    @Autowired
    private FeedbackDAO feedbackDAO;
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/ward")
    public String allwards(Model model, HttpSession session) {

        if(authenticateService.isAuthenticated(session))
		{
			model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
            User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);
		}

        return "ward";

    }

    @GetMapping("/ward/general")
    public String generalward(Model model, HttpSession session, RedirectAttributes redirectAttributes){

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        Bookroom bookroom = new Bookroom();

        bookroom.setUsername(authenticateService.getCurrentUser(session));
        bookroom.setCurrDate(new Date().toString());

        model.addAttribute("bookroom", bookroom);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


        return "roombookingform"; 
    }

    @PostMapping("/ward/general")
    public String generalwardPost(@ModelAttribute("bookroom") Bookroom bookroom, Model model, HttpSession session) {

        // List<Integer> rooms = bookroomDAO.bookroomExist("General", "Non-AC", 1, bookroom.getStartDate(), bookroom.getEndDate());     

        // System.out.println(rooms.size());
                // if(rooms.size() > 0) {

                bookroom.setRoomID(3);

                bookroom.setCurrDate(new Date().toString());
                bookroom.setStatus("Pending");


                bookroomDAO.save(bookroom);

                int id = bookroomDAO.getLastID();

                return "redirect:/confirmroom/" + id;
            // }      

        // room not available;

        // return "redirect:/ward"; 
    }

    @GetMapping("/confirmroom/{id}")
    public String confirmbookroom(@PathVariable("id") int id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        Bookroom bookroom = bookroomDAO.findByID(id);
        
        int roomid = bookroom.getRoomID();

        Room room = roomDAO.findByID(roomid);

        System.out.println(room);

        model.addAttribute("cost", room.getCost());
        
        // if(room.getNumberofbeds() == 1 && room.getType() == "Non-AC" && room.getWard() == "General")
        //     bookroom.setCost(1000);
        // else if(room.getNumberofbeds() == 1 && room.getType() == "Non-AC" && room.getWard() == "Private")
        //     bookroom.setCost(2000);
        // else if(room.getNumberofbeds() == 1 && room.getType() == "AC" && room.getWard() == "Private")
        //     bookroom.setCost(3000);
        // else if(room.getNumberofbeds() == 2 && room.getType() == "Non-AC" && room.getWard() == "Private")
        //     bookroom.setCost(4000);
        // else if(room.getNumberofbeds() == 2 && room.getType() == "AC" && room.getWard() == "Private")
        //     bookroom.setCost(5000);

        model.addAttribute("bookroom", bookroom);
        model.addAttribute("room", room);

        User user = userDAO.findByUsername(authenticateService.getCurrentUser(session));
        model.addAttribute("user", user);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);


        return "confirmroom";

    }

    @PostMapping("/confirmroom/{id}")
	public String confirmbookroomPost(@PathVariable("id") int id, Model model, HttpSession session) {

		System.out.println(id);

		return "redirect:/payroom/" + id;	
	}

    @GetMapping("/ward/private")
    public String privateward(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}
		// model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));

		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

        return "private"; 
    }

    @GetMapping({"/ward/private/single/ac","/ward/private/single/nonac","ward/private/double/ac","ward/private/double/nonac"})
    public String privateroom(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

        Bookroom bookroom = new Bookroom();

        bookroom.setCurrDate(new Date().toString());

        bookroom.setUsername(authenticateService.getCurrentUser(session));

        model.addAttribute("bookroom", bookroom);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

        return "roombookingform"; 
    }

    @PostMapping("/ward/private/single/nonac")
    public String privatesinglenonac(@ModelAttribute("bookroom") Bookroom bookroom, Model model, HttpSession session) {

        // List<Integer> rooms = bookroomDAO.bookroomExist("Private", "Non-AC", 1, bookroom.getStartDate(), bookroom.getEndDate());

        // if(rooms.size() > 0) {

            bookroom.setRoomID(4);

            bookroom.setCurrDate(new Date().toString());
            bookroom.setStatus("Pending");

            bookroomDAO.save(bookroom);

            int id = bookroomDAO.getLastID();

            return "redirect:/confirmroom/" + id;
        // }

        // room not available;

        // return "redirect:/ward/private"; 
    }

    @PostMapping("/ward/private/single/ac")
    public String privatesingleac(@ModelAttribute("bookroom") Bookroom bookroom, Model model, HttpSession session) {

            // List<Integer> rooms = bookroomDAO.bookroomExist("Private", "AC", 1, bookroom.getStartDate(), bookroom.getEndDate());
            // if(rooms.size() > 0) {

                bookroom.setRoomID(5);

                bookroom.setCurrDate(new Date().toString());
                bookroom.setStatus("Pending");

                bookroomDAO.save(bookroom);

                int id = bookroomDAO.getLastID();

                return "redirect:/confirmroom/" + id;
            // }      
        // room not available;

        // return "redirect:/ward/private"; 
    }

    @PostMapping("/ward/private/double/nonac")
    public String privatedoublenonac(@ModelAttribute("bookroom") Bookroom bookroom, Model model, HttpSession session) {

        // List<Integer> rooms = bookroomDAO.bookroomExist("Private", "Non-AC", 1, bookroom.getStartDate(), bookroom.getEndDate());
        
        // if(rooms.size() > 0) {

            bookroom.setRoomID(6);

            bookroom.setCurrDate(new Date().toString());
            bookroom.setStatus("Pending");

            
            bookroomDAO.save(bookroom);

            
            int id = bookroomDAO.getLastID();

            return "redirect:/confirmroom/" + id;
        // }
      
        // room not available;

        // return "redirect:/ward/private"; 
    }

    @PostMapping("/ward/private/double/ac")
    public String privatedoubleac(@ModelAttribute("bookroom") Bookroom bookroom, Model model, HttpSession session) {

        // List<Integer> rooms = bookroomDAO.bookroomExist("Private", "AC", 1, bookroom.getStartDate(), bookroom.getEndDate());
        // System.out.println(rooms.size());

        // if(rooms.size() > 0) {

            bookroom.setRoomID(7);

            bookroom.setCurrDate(new Date().toString());

            bookroom.setStatus("Pending");

            bookroomDAO.save(bookroom);


            int id = bookroomDAO.getLastID();

            return "redirect:/confirmroom/" + id;
        // }

        // room not available;
        // System.out.println("juyyyy");
        // return "redirect:/private"; 
    }

    @GetMapping("/myroombookings")
	public String myroombooks(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        String loginMessage = "Please Sign in to proceed!!!";
		if(!authenticateService.isAuthenticated(session))
		{
			toastService.redirectWithErrorToast(redirectAttributes, loginMessage);
			return "redirect:/login";
		}

		String username = authenticateService.getCurrentUser(session);

		List<Bookroom> roombookings = bookroomDAO.findByUsername(username);

		for(int i=0; i<roombookings.size();i++)
		{
			System.out.println(roombookings.get(i));
		}

        List<Room> rooms = new ArrayList<Room>();
		List<Integer> feedbacks = new ArrayList<Integer>();


        for(int i=0; i<roombookings.size();i++) {

            Room room = roomDAO.findByID(roombookings.get(i).getRoomID());
            rooms.add(room);

            int rating = feedbackDAO.isRoombookingFeedback(roombookings.get(i).getBookroomID());
			System.out.println(rating);
			if(rating == 0)
				feedbacks.add(0);
			else
			{
				feedbacks.add(feedbackDAO.roombookingRating(roombookings.get(i).getBookroomID()));
			}
        }


		Feedback feedback = new Feedback();

		model.addAttribute("feedback", feedback);
		model.addAttribute("feedbacks", feedbacks);

		model.addAttribute("roombookings", roombookings);
        model.addAttribute("rooms", rooms);
		model.addAttribute("loggedinUser", authenticateService.getCurrentUser(session));
        User loggedUser = userDAO.findByUsername(authenticateService.getCurrentUser(session));
		model.addAttribute("loggedUser", loggedUser);

		return "myroombookings";
	} 

    @PostMapping("/roombookings/{id}/feedback")
	public String feedbackAppointment(@ModelAttribute("feedback") Feedback feedback,@PathVariable("id") int roombookingID) {

		feedback.setDate(new Date().toString());

		feedback.setPurpose("Room Booking");

		feedback.setPurposeID(roombookingID);

		feedbackDAO.save(feedback);

		return "redirect:/myroombookings";
	}
}
