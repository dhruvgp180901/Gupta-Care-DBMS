package com.example.DBMS.dao;
import java.util.List;

// import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
// import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.DBMS.model.Order;

@Lazy
@Repository
public class OrderDAO {
	
	@Autowired
	private JdbcTemplate jt;
	
	public void save(Order order) {
		
		String sql="insert into orderMed(username,medicineID,amount,date,cost) values (?,?,?,?,?);";
		jt.update(sql,
            order.getUsername(),
            order.getMedicineID(),
            order.getAmount(),
            order.getDate(),
            order.getCost()
				);		
	}

	public void delete(int orderID) {

		String sql = "delete from orderMed where orderID = ?";
	    jt.update(sql, orderID);
	}	
	
	public Order findByID(int orderID) {
        String sql = "select * from orderMed where orderID = ?";        
        return jt.queryForObject(sql, new Object[] {orderID}, new BeanPropertyRowMapper<>(Order.class));
       
    }

    public List<Order> findByUsername(String username) {
        String sql = "select * from orderMed where username = ?";    
        System.out.print(username);    
        return jt.query(sql,new BeanPropertyRowMapper<>(Order.class),username);
       
    }

    public void updateAmount(int id, int amount) {
        String query = "update orderMed set amount = ? where orderID = ?";
        jt.update(query, amount,id);
    }

	public List<Order> allorders() {
		
		String sql="select * from orderMed;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Order.class));
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, new Object[] {}, Integer.class);
    }
	 
}

