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

import com.example.DBMS.model.Payment;

@Lazy
@Repository
public class PaymentDAO {
	
	@Autowired
	private JdbcTemplate jt;
	// @Autowired
	// private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void save(Payment payment) {
	
		String sql="insert into payment(purpose,purposeID,amount,payDate,status,cardNumber,expirationDate,cvv) values (?,?,?,?,?,?,?,?);";
		jt.update(sql,
		payment.getPurpose(),
        payment.getPurposeID(),
        payment.getAmount(),
        payment.getPayDate(),
        payment.getStatus(),
		payment.getCardNumber(),
		payment.getExpirationDate(),
		payment.getCvv()
		);		
	}

	public void delete(int paymentID) {

		String sql = "delete from payment where paymentID = ?";
	    jt.update(sql, paymentID);
	}
	
	public void updateAmount(int paymentID, int amount) {

		String sql = "update payment set amount = ? where paymentID = ?";
	    jt.update(sql, amount, paymentID);
	}
	
	public Payment findByID(int paymentID) {
        String sql = "select * from payment where paymentID = ?";        
        return jt.queryForObject(sql, new Object[] {paymentID}, new BeanPropertyRowMapper<>(Payment.class));
       
    }

	public List<Payment> findByUserID(int userid) {
        String sql = "select * from payment where purposeID = ?";        
        return jt.query(sql, new BeanPropertyRowMapper<>(Payment.class),userid);
       
    }

	public List<Payment> allpayments() {
		
		String sql="select * from payment;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Payment.class));
	}

	public List<Payment> allMedicinepayments() {
		
		String sql="select * from payment where purpose = 'Medicines Order';";

		return jt.query(sql, new BeanPropertyRowMapper<>(Payment.class));
	}

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, new Object[] {}, Integer.class);
    }
	 
}

