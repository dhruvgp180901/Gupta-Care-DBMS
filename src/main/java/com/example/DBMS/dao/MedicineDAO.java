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

import com.example.DBMS.model.Medicine;

@Lazy
@Repository
public class MedicineDAO {

	@Autowired
	private JdbcTemplate jt;

	public void save(Medicine medicine) {

		String sql = "insert into medicine(name,purpose,color,company,photo,description,cost,deliveredAmount,deliveredDate) values (?,?,?,?,?,?,?,?,?);";
		jt.update(sql, medicine.getName(), medicine.getPurpose(), medicine.getColor(), medicine.getCompany(),
				medicine.getPhoto(), medicine.getDescription(), medicine.getCost(), medicine.getDeliveredAmount(),
				medicine.getDeliveredDate());
	}

	public void update(String purpose, String description, String deliveredAmount, String deliveredDate,
			int medicineID) {
		String sql = "update medicine set purpose = ?, description = ?, deliveredAmount = ?, deliveredDate = ? where medicineID = ?";
		jt.update(sql, purpose, description, deliveredAmount, deliveredDate, medicineID);
	}

	public void delete(int medicineID) {

		String sql = "delete from medicine where medicineID = ?";
		jt.update(sql, medicineID);
	}

	public Medicine findByID(int medicineID) {
		String sql = "select * from medicine where medicineID = ?";
		return jt.queryForObject(sql, new Object[] { medicineID }, new BeanPropertyRowMapper<>(Medicine.class));

	}

	public void updateProfile(int medicineid, String filename) {
		String query = "update medicine set photo = ? where medicineID = ?";
		jt.update(query, filename, medicineid);
	}

	public List<Medicine> allmedicines() {

		String sql = "select * from medicine;";

		return jt.query(sql, new BeanPropertyRowMapper<>(Medicine.class));
	}

	public int getLastID() {
		String query = "Select LAST_INSERT_ID()";
		return jt.queryForObject(query, new Object[] {}, Integer.class);
	}

}
