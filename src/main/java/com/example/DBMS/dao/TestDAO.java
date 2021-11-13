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
import com.example.DBMS.model.User;

@Lazy
@Repository
public class TestDAO {

	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Test test) {

		String sql = "insert into test(testName,setupDate,description,cost,photo) values (?,?,?,?,?);";
		jt.update(sql, test.getTestName(), test.getSetupDate(), test.getDescription(), test.getCost(), test.getPhoto());
	}

	public void update(String setupDate, String description, int cost, int testid) {

		String sql = "update test set setupDate = ?,description = ?,cost = ? where testid = ?";
		jt.update(sql, setupDate, description, cost, testid);
	}

	public void delete(int testID) {

		String sql = "delete from test where testID = ?";
		jt.update(sql, testID);
	}

	public void updateProfile(int testid, String filename) {
		String query = "update test set photo = ? where testID = ?";
		jt.update(query, filename, testid);
	}

	public Test findByID(int testID) {
		String sql = "select * from test where testID = ?";
		return jt.queryForObject(sql, new Object[] { testID }, new BeanPropertyRowMapper<>(Test.class));
	}

	public Test findByTestName(String testName) {
		String sql = "select * from test where testName = ?";
		return jt.queryForObject(sql, new Object[] { testName }, new BeanPropertyRowMapper<>(Test.class));
	}

	public List<Test> alltests() {

		String sql = "select * from test;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Test.class));
	}

	public int getLastID() {
		String query = "Select LAST_INSERT_ID()";
		return jt.queryForObject(query, new Object[] {}, Integer.class);
	}

}
