package com.demo.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.assignment.repository.ApplicationUserRepository;
import com.demo.assignment.security.ApplicationUser;

@Service
public class ApplicationUserService implements UserDetailsService {
	
	@Autowired
	private ApplicationUserRepository applicationUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.selectApplicationUserByUsername(username);
		if(applicationUser == null) {
			throw new UsernameNotFoundException("No user with username " + username + "exists.");
		}
		
		return applicationUser;
	}

}
