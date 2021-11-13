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
import com.example.DBMS.model.Test;
import com.example.DBMS.model.Testbooking;
import com.example.DBMS.model.User;

@Lazy
@Repository
public class TestbookingDAO {

	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Testbooking testbooking) {

		String sql = "insert into testbooking(testName,patientName,currDate,bookDate,bookTime,diseaseDescription,status) values (?,?,?,?,?,?,?);";
		jt.update(sql, testbooking.getTestName(), testbooking.getPatientName(), testbooking.getCurrDate(),
				testbooking.getBookDate(), testbooking.getBookTime(), testbooking.getDiseaseDescription(),
				testbooking.getStatus());
	}

	public void delete(int testbookingID) {

		String sql = "delete from testbooking where testbookingID = ?";
		jt.update(sql, testbookingID);
	}

	public Testbooking findByID(int testbookingID) {
		String sql = "select * from testbooking where testbookingID = ?";
		return jt.queryForObject(sql, new Object[] { testbookingID }, new BeanPropertyRowMapper<>(Testbooking.class));

	}

	public List<Testbooking> alltestbookings() {

		String sql = "select * from testbooking;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Testbooking.class));
	}

	public List<Testbooking> findByUsername(String username) {

		String sql = "select * from testbooking where patientName = ?";

		return jt.query(sql, new BeanPropertyRowMapper<>(Testbooking.class), username);
	}

	public int getLastID() {
		String query = "Select LAST_INSERT_ID()";
		return jt.queryForObject(query, new Object[] {}, Integer.class);
	}

}
