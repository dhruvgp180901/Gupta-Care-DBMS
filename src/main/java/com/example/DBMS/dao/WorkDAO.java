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
import com.example.DBMS.model.Work;

@Lazy
@Repository
public class WorkDAO {

	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	public void save(Work work) {

        String sql="insert into work(username,hospital,workRole,workJoiningDate,workLeavingDate,workDescription) values (?,?,?,?,?,?)";
        jt.update(sql,work.getUsername(),work.getHospital(),work.getWorkRole(),work.getWorkJoiningDate(),work.getWorkLeavingDate(),work.getWorkDescription());
	}

	public void delete(String username) {

		String sql = "delete from work where username = ?";
		jt.update(sql, username);
	}

	public void deleteByID(int workid) {

		String sql = "delete from work where workID = ?";
		jt.update(sql, workid);
	}

	public List<Work> findByUsername(String username) {
		String sql = "select * from work where username=?";
		return jt.query(sql, new BeanPropertyRowMapper<>(Work.class),username);
	}

	public Work findByID(int workid) {
		String sql = "select * from work where workid=?";
		return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Work.class),workid);
	}

	public List<Work> allWorks() {

		String sql = "select * from work;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Work.class));
	}

}
