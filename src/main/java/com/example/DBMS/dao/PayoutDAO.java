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

import com.example.DBMS.model.Payout;

@Lazy
@Repository
public class PayoutDAO {

	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	public void save(Payout payout) {

        String sql="insert into payout(username,month,year,leavesAllowed,leavesTaken,overdaysWorked,netAmount,status) values (?,?,?,?,?,?,?,?)";
        jt.update(sql,payout.getUsername(),payout.getMonth(),payout.getYear(),payout.getLeavesAllowed(),payout.getLeavesTaken(),payout.getOverdaysWorked(),payout.getNetAmount(),payout.getStatus());
	}

	public void delete(String username) {

		String sql = "delete from payout where username = ?";
		jt.update(sql, username);
	}

	public List<Payout> findByUsername(String username) {
		String sql = "select * from payout where username=?";
		return jt.query(sql,new BeanPropertyRowMapper<>(Payout.class),username);
		
	}

	public void deleteByID(int payoutid) {

		String sql = "delete from payout where payoutID = ?";
		jt.update(sql, payoutid);
	}

	public Payout findByID(int payoutid) {
		String sql = "select * from payout where payoutid=?";
		return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Payout.class),payoutid);
	}

	public List<Payout> allPayouts() {

		String sql = "select * from payout;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Payout.class));
	}

}
