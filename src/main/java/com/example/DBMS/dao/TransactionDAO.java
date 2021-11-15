package com.example.DBMS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.DBMS.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO {
	
	@Autowired
	private JdbcTemplate jt;	
	
	public void save(Transaction transaction) {
		
		String sql="insert into transaction(transactionDate,transactionTime,amount,mode,verified,token) values (NOW(),NOW(),?,?,?,?);";

	    KeyHolder keyHolder = new GeneratedKeyHolder();
	    jt.update(sql,transaction.getAmount(),
		transaction.getMode(),
		transaction.isVerified(),
		transaction.getToken());
	
	}
	
	public Transaction updateVerified(String token) {
		
		String sql="update transaction set verified=1 where token=?;";
		jt.update(sql,token);
		
		sql="select * from transaction where token=?";
		
		return jt.queryForObject(sql, new Object[] {token},new RowMapper<Transaction>() {
			
			public Transaction mapRow(ResultSet row, int rowNum) throws SQLException {
				
				Transaction transaction = (new BeanPropertyRowMapper<>(Transaction.class)).mapRow(row,rowNum);
				
				return transaction;
				
			}
		});
		
	}
	
	public void delete(int transactionID) {
		
		String sql="delete from transaction where transactionID=?;";
		jt.update(sql,transactionID);
		
	}

}






