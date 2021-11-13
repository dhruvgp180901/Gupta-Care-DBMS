package com.example.DBMS.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.DBMS.model.Appointment;
import com.example.DBMS.model.User;

@Lazy
@Repository
public class AppointmentDAO {
	
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void save(Appointment appointment) {
		
		String sql="insert into appointment(patientName,doctorName,currDate,bookDate,bookStime,bookEtime,description,status) values (?,?,?,?,?,?,?,?);";
		jt.update(sql,
            appointment.getPatientName(),
            appointment.getDoctorName(),
            appointment.getCurrDate(),
            appointment.getBookDate(),
            appointment.getBookStime(),
            appointment.getBookEtime(),
            appointment.getDescription(),
            appointment.getStatus()
				);		
	}

	public void delete(int appointmentID) {

		String sql = "delete from appointment where appointmentID = ?";
	    jt.update(sql, appointmentID);
	}	
	
	public Appointment findByID(int appointmentID) {
        String sql = "select * from appointment where appointmentID = ?";        
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Appointment.class), appointmentID);   
    }

	public List<Appointment> allappointments() {
		
		String sql="select * from appointment;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Appointment.class));
	}

    public List<Appointment> findByUsername(String username) {
		
		String sql="select * from appointment where patientName = ?";

		return jt.query(sql, new BeanPropertyRowMapper<>(Appointment.class), username);
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, Integer.class);
    }

    public Boolean appointmentExists(String bookingDate, String startTime, String endTime) {
        String sql = "Select count(*) from appointment where appointment.bookDate = ? and NOT(appointment.bookStime > ? or appointment.bookEtime < ?);";
        int found = jt.queryForObject(sql, Integer.class, bookingDate, endTime, startTime);
        System.out.println(found);
       
        if(found>=1) return true;
        else return false;
    }
	 
}

