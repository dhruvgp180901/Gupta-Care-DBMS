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

import com.example.DBMS.model.Doctor;

@Lazy
@Repository
public class DoctorDAO {

	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(Doctor doctor) {

		String sql = "insert into doctor(username,specialization,designation,parentName,parentOccupation,collegeGrad,percentageGrad,collegePGrad,percentagePGrad,board10th,percentage10th,board12th,percentage12th,appointmentCost,salary) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		jt.update(sql, doctor.getUsername(), doctor.getSpecialization(), doctor.getDesignation(),
				doctor.getParentName(), doctor.getParentOccupation(), doctor.getCollegeGrad(),
				doctor.getPercentageGrad(), doctor.getCollegePGrad(), doctor.getPercentagePGrad(),
				doctor.getBoard10th(), doctor.getPercentage10th(), doctor.getBoard12th(), doctor.getPercentage12th(),
				doctor.getAppointmentCost(),doctor.getSalary());
	}

	public void update(String specialization, String designation, int appointmentCost, int salary, String parentName,
			String parentOccupation, String collegeGrad, int percentageGrad, String collegePGrad, int percentagePGrad,
			String board10th, int percentage10th, String board12th, int percentage12th, String username) {

		String sql = "update doctor set specialization = ?, designation = ?, appointmentCost = ?, salary = ?,parentName = ?, parentOccupation = ?, collegeGrad = ?,percentageGrad = ?, collegePGrad = ?, percentagePGrad = ?,board10th = ?, percentage10th = ?, board12th = ?, percentage12th = ? where username = ?";
		jt.update(sql, specialization, designation, appointmentCost, salary,parentName, parentOccupation, collegeGrad,
				percentageGrad, collegePGrad, percentagePGrad, board10th, percentage10th, board12th, percentage12th,
				username);
	}

	public void delete(String username) {

		String sql = "delete from doctor where username = ?";
		jt.update(sql, username);
	}

	public Doctor findByDoctorname(String username) {
		String sql = "select * from doctor where username='" + username + "'";
		try {
			return jt.queryForObject(sql, new RowMapper<Doctor>() {
				public Doctor mapRow(ResultSet row, int rowNum) throws SQLException {
					Doctor Doctor = (new BeanPropertyRowMapper<>(Doctor.class)).mapRow(row, rowNum);
					return Doctor;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Doctor> allDoctors() {

		String sql = "select * from doctor;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Doctor.class));
	}

	public boolean DoctorExists(String Doctorname) {

		String sql = "select count(*) from Doctor where username='" + Doctorname + "'";

		int found = jt.queryForObject(sql, Integer.class);

		if (found == 1)
			return true;
		else
			return false;

	}

}
