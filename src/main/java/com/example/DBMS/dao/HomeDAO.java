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
import com.example.DBMS.model.Payment;

@Lazy
@Repository
public class HomeDAO {
	
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void save(ContactUsForm contactus) {
	
		String sql="insert into contactUsForm(query, emailID, phoneNumber,name,subject,date) values (?,?,?,?,?,?);";
		jt.update(sql,
        contactus.getQuery(),
        contactus.getEmailID(),
        contactus.getPhoneNumber(),
        contactus.getName(),
        contactus.getSubject(),
		contactus.getDate()
		);		
	}

	public void delete(int queryID) {

		String sql = "delete from contactUsForm where queryID = ?";
	    jt.update(sql, queryID);
	}	
	
	public ContactUsForm findByID(int queryID) {
        String sql = "select * from contactUsForm where queryID = ?";        
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(ContactUsForm.class), queryID);
       
    }

	public Integer isReply(int queryID) {
        String sql = "select count(*) from contactUsForm where queryID = ? and replyGiven IS NULL";        
        return jt.queryForObject(sql, Integer.class, queryID);
       
    }

	public void updateReply(int queryID, String reply) {

		String sql = "update contactUsForm set replyGiven = ? where queryID = ?";
		jt.update(sql,reply,queryID);

	}

	public List<ContactUsForm> allqueries() {
		
		String sql="select * from contactUsForm;";

		return jt.query(sql, new BeanPropertyRowMapper<>(ContactUsForm.class));
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, Integer.class);
    }
	 
}

