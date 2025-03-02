package com.rishi.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rishi.model.AppUser;
import com.rishi.model.UserPrincipal;
import com.rishi.service.SecurityService;

import jakarta.transaction.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private SecurityService securityService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user=securityService.getByUsername(username);
		if(user==null)
			throw new UsernameNotFoundException(username+"->THIS USERNAME DOSENT EXIST");
	
		return new UserPrincipal(user);
	}

}
