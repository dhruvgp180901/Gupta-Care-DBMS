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

import com.example.DBMS.model.Bookroom;
// import com.example.DBMS.model.Room;

@Lazy
@Repository
public class BookroomDAO {

    @Autowired
    private JdbcTemplate jt;

    public void save(Bookroom bookroom) {

        String sql = "insert into bookroom(username,roomID,currDate,startDate,endDate,description,status) values (?,?,?,?,?,?,?);";
        jt.update(sql, bookroom.getUsername(), bookroom.getRoomID(), bookroom.getCurrDate(), bookroom.getStartDate(), bookroom.getEndDate(),
                bookroom.getDescription(),bookroom.getStatus());
    }

    public void delete(int bookroomID) {

        String sql = "delete from bookroom where bookroomID = ?";
        jt.update(sql, bookroomID);
    }

    public Bookroom findByID(int bookroomID) {
        String sql = "select * from bookroom where bookroomID = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Bookroom.class), bookroomID);

    }

    public Integer findByWard(String ward) {
        String sql = "select count(*) from bookroom where ward = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class), ward);

    }

    public List<Bookroom> findByUsername(String username) {
        String sql = "select * from bookroom where username = ?";
        // System.out.print(username);
        return jt.query(sql, new BeanPropertyRowMapper<>(Bookroom.class), username);

    }

    public List<Bookroom> allbookrooms() {

        String sql = "select * from bookroom;";

        return jt.query(sql, new BeanPropertyRowMapper<>(Bookroom.class));
    }

    public int getLastID() {
        String sql = "Select LAST_INSERT_ID()";
        return jt.queryForObject(sql, Integer.class);
    }

    public void updateStatus(int id, String status) {

		String sql = "update bookroom set status = ? where bookroomID = ?";
	    jt.update(sql, status, id);
	}

    // public List<Integer> bookroomExist(String ward, String type, int numofbeds, String startDate, String endDate) {
    //     String sql = "Select room.roomID from room where ((room.ward = ? and room.type = ? and room.numberofbeds = ?) and ((Select count(*) from bookroom where room.roomID = bookroom.roomID) = 0 or (Select count(*) from bookroom where room.roomID = bookroom.roomID and NOT(bookroom.startDate > ? and bookroom.endDate < ?)) = 0 ));";
    //     return jt.query(sql, new BeanPropertyRowMapper<>(Integer.class),ward,type,numofbeds,endDate,startDate);
    // }

}
