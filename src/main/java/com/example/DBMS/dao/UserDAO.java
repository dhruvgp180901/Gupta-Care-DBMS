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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.DBMS.model.User;

@Lazy
@Repository
public class UserDAO {

	@Autowired
	private JdbcTemplate jt;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void save(User user) {

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		// System.out.println(user.getUsername());
		String sql = "insert into user(username,password,role,photo,birthDate,gender,adhaarNumber,emailID,firstName,middleName,lastName,street,city,state,country,phone,token,active) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		jt.update(sql, user.getUsername(), user.getPassword(), user.getRole(), user.getPhoto(), user.getBirthDate(),
				user.getGender(), user.getAdhaarNumber(), user.getEmailID(), user.getFirstName(), user.getMiddleName(),
				user.getLastName(), user.getStreet(), user.getCity(), user.getState(), user.getCountry(),
				user.getPhone(),user.getToken(),user.getActive());
		// System.out.println(user.getUsername());

	}

	public void update(String aadharNumber, String street, String city, String state, String country, String phone,
			String username) {

		String sql = "update user set adhaarNumber = ?,street = ?,city = ?,state = ?,country = ?,phone = ? where username = ?";
		jt.update(sql, aadharNumber, street, city, state, country, phone, username);
	}


	public void updateActivity(String username,int active) {

		String sql = "update user set active = ? where username = ?";
		jt.update(sql, active, username);
	}

	public void delete(String username) {

		String sql = "delete from user where username = ?";
		jt.update(sql, username);
	}

	public User findByUsername(String username) {
		String sql = "select * from user where username='" + username + "'";
		try {
			return jt.queryForObject(sql, new RowMapper<User>() {
				public User mapRow(ResultSet row, int rowNum) throws SQLException {
					User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row, rowNum);
					return user;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public User findByID(int userID) {
		String sql = "select * from user where userID = ?";
		return jt.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userID);

	}

	public void updateProfile(String username, String filename) {
		String query = "update user set photo = ? where username = ?";
		jt.update(query, filename, username);
	}

	public List<User> allusers() {

		String sql = "select * from user;";

		return jt.query(sql, new BeanPropertyRowMapper<>(User.class));
	}

	public boolean userExists(String username) {

		String sql = "select count(*) from user where username='" + username + "'";

		int found = jt.queryForObject(sql, Integer.class);

		if (found == 1)
			return true;
		else
			return false;
	}

	public boolean updatePassword(String username,String oldPassword,String oldPasswordEntered, String newPassword) {
		
		if(bCryptPasswordEncoder.matches(oldPasswordEntered,oldPassword)) {
			String sql="update user set password=? where username=?;";
			String encodedNewPassword=bCryptPasswordEncoder.encode(newPassword);
			jt.update(sql,encodedNewPassword,username);
			return true;
		}else {
			return false;
		}	
	}

	public User findByConfirmationToken(String token) {
        String sql = "select * from user where token='" + token + "'";
        try{
        	return jt.queryForObject(sql, new RowMapper<User>() {
                public User mapRow(ResultSet row, int rowNum) throws SQLException {                	
                	User user = (new BeanPropertyRowMapper<>(User.class)).mapRow(row,rowNum);
                	return user;
                }
            });        
        }catch(EmptyResultDataAccessException e){
        	return null;
        }         
    }
	
}
