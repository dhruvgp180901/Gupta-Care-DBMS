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

import com.example.DBMS.model.Room;

@Lazy
@Repository
public class RoomDAO {

    @Autowired
    private JdbcTemplate jt;

    public void save(Room room) {

        String sql = "insert into room(ward,number,type,numberofbeds,cost) values (?,?,?,?,?);";
        jt.update(sql, room.getWard(), room.getNumber(), room.getType(), room.getNumberofbeds(), room.getCost());
    }

    public void delete(int roomID) {

        String sql = "delete from room where roomID = ?";
        jt.update(sql, roomID);
    }

    public Room findByID(int roomID) {
        String sql = "select * from room where roomID = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Room.class), roomID);

    }

    public Integer findByWard(String ward) {
        String sql = "select count(*) from room where ward = ?";
        return jt.queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class), ward);

    }

    public List<Room> findByUsername(String username) {
        String sql = "select * from room where username = ?";
        // System.out.print(username);
        return jt.query(sql, new BeanPropertyRowMapper<>(Room.class), username);

    }

    public List<Room> allrooms() {

        String sql = "select * from room;";

        return jt.query(sql, new BeanPropertyRowMapper<>(Room.class));
    }

    public int getLastID() {
        String query = "Select LAST_INSERT_ID()";
        return jt.queryForObject(query, new Object[] {}, Integer.class);
    }

}
