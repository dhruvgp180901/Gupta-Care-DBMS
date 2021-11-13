package com.example.DBMS.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.example.DBMS.dao.UserDAO;
import com.example.DBMS.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Lazy
	@Autowired
	UserDAO userDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		User user = userDAO.findByUsername(username);
		
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		if (user == null) throw new UsernameNotFoundException(username);
		
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
		grantList.add(authority);
		
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), grantList);
		
		
		
		return userDetails;
		
	}

}