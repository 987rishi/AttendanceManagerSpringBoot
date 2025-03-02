package com.rishi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rishi.model.AppUser;
import com.rishi.repo.UserRepo;

@Service
public class SecurityService {

	@Autowired
	private UserRepo userRepo;
	public AppUser getByUsername(String username) {
		return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("USER NAME NOT FOUND IN DB: "+username));
	}

}
