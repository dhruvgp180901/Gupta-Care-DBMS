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
import com.example.DBMS.model.DoctorApplicant;
import com.example.DBMS.model.User;

@Lazy
@Repository
public class DoctorApplicantDAO {
	
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void save(DoctorApplicant doctorApplicant) {

		String sql="insert into DoctorApplicant(name,phoneNumber,emailID,date,specialization,resume) values (?,?,?,?,?,?);";
		jt.update(sql,
        doctorApplicant.getApplicantName(),
        doctorApplicant.getApplicantPhoneNumber(),
        doctorApplicant.getApplicantEmailID(),
        doctorApplicant.getApplicationDate(),
        doctorApplicant.getSpecialization(),
        doctorApplicant.getResume()
		);		
	}

	public void delete(int DoctorApplicantID) {

		String sql = "delete from DoctorApplicant where DoctorApplicantID = ?";
	    jt.update(sql, DoctorApplicantID);
	}	
	
	public DoctorApplicant findByID(int DoctorApplicantID) {
        String sql = "select * from DoctorApplicant where DoctorApplicantID = ?";        
        return jt.queryForObject(sql, new Object[] {DoctorApplicantID}, new BeanPropertyRowMapper<>(DoctorApplicant.class));
       
    }

    public void updateProfile(int id, String filename) {
		String query = "update DoctorApplicant set resume = ? where DoctorApplicantID = ?";
		jt.update(query, filename, id);
	}

	public List<DoctorApplicant> allDoctorApplicants() {
		
		String sql="select * from DoctorApplicant;";

		return jt.query(sql, new BeanPropertyRowMapper<>(DoctorApplicant.class));
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, new Object[] {}, Integer.class);
    }
	 
}

