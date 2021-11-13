package com.example.DBMS.dao;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.DBMS.model.ContactUsForm;
import com.example.DBMS.model.Feedback;
import com.example.DBMS.model.Payment;

@Lazy
@Repository
public class FeedbackDAO {
	
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Feedback feedback) {
	
		String sql="insert into feedback(purpose,purposeID,rating,complaint,suggestion,date) values (?,?,?,?,?,?);";
		jt.update(sql,
        feedback.getPurpose(),
        feedback.getPurposeID(),
		feedback.getRating(),
        feedback.getComplaint(),
        feedback.getSuggestion(),
        feedback.getDate()
		);		
	}

	public void delete(int feedbackID) {

		String sql = "delete from feedback where feedbackID = ?";
	    jt.update(sql, feedbackID);
	}	
	
	public Feedback findByID(int feedbackID) {
        String sql = "select * from feedback where feedbackID = ?";        
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Feedback.class), feedbackID);
       
    }

	public int isAppointmentFeedback(int id) {

		String sql = "Select count(*) from feedback where purpose = 'Appointment' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int appointmentRating(int id) {

		String sql = "Select rating from feedback where purpose = 'Appointment' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int isTestbookingFeedback(int id) {

		String sql = "Select count(*) from feedback where purpose = 'Test Booking' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int testbookingRating(int id) {

		String sql = "Select rating from feedback where purpose = 'Test Booking' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int isRoombookingFeedback(int id) {

		String sql = "Select count(*) from feedback where purpose = 'Room Booking' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int roombookingRating(int id) {

		String sql = "Select rating from feedback where purpose = 'Room Booking' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int isMedicineorderFeedback(int id) {

		String sql = "Select count(*) from feedback where purpose = 'Medicine Order' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public int medicineorderRating(int id) {

		String sql = "Select rating from feedback where purpose = 'Medicine Order' and purposeID = ?";
        return jt.queryForObject(sql, Integer.class,id);

	}

	public List<Feedback> allfeedbacks() {
		
		String sql="select * from feedback;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Feedback.class));
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, Integer.class);
    }
	 
}

