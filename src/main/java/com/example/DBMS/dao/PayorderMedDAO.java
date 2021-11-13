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
import com.example.DBMS.model.PayorderMed;
import com.example.DBMS.model.User;

@Lazy
@Repository
public class PayorderMedDAO {

	@Autowired
	private JdbcTemplate jt;

	public void save(PayorderMed payorderMed) {

		String sql = "insert into payorderMed(paymentID,medicineID,quantity) values (?,?,?);";
		jt.update(sql, payorderMed.getPaymentID(), payorderMed.getMedicineID(),payorderMed.getQuantity());
	}

	public void delete(int payorderMedID) {

		String sql = "delete from payorderMed where payorderMedID = ?";
		jt.update(sql, payorderMedID);
	}

	public PayorderMed findByID(int payorderMedID) {
		String sql = "select * from payorderMed where payorderMedID = ?";
		return jt.queryForObject(sql, new BeanPropertyRowMapper<>(PayorderMed.class), payorderMedID);
	}

	public List<PayorderMed> allPayorderMeds() {

		String sql = "select * from payorderMed;";

		return jt.query(sql, new BeanPropertyRowMapper<>(PayorderMed.class));
	}

	public List<PayorderMed> findByPaymentID(int paymentID) {

		String sql = "select * from payorderMed where paymentID = ?;";

		return jt.query(sql, new BeanPropertyRowMapper<>(PayorderMed.class), paymentID);
	}

	public int getLastID() {
		String query = "Select LAST_INSERT_ID()";
		return jt.queryForObject(query, Integer.class);
	}

}
