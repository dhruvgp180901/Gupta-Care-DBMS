package com.example.DBMS.service;

import javax.servlet.http.HttpSession;

import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {
    
    @Autowired
    private UserDAO users;
    private String loggedUser = "AUTH_USER";

    public Boolean checkCredentials(String username, String password) {
        User user = users.findByUsername(username);
        return user.getPassword().equals(password);
    }

    public void loginUser(HttpSession session, String username) {
        session.setAttribute(loggedUser, username);
    }

    public void logoutUser(HttpSession session) {
        session.removeAttribute(loggedUser);
    }

    public String getCurrentUser(HttpSession session) {
        try {
            return session.getAttribute(loggedUser).toString();
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean isAuthenticated(HttpSession session) {
        return getCurrentUser(session) != null;
    }
}