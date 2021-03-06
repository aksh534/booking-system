package com.demo.assignment.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.assignment.model.ApplicationUser;
import com.demo.assignment.repository.ApplicationUserRepository;
import com.demo.assignment.security.ApplicationUserRole;

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
	
	public ApplicationUser getCurrentUser() {
		String currentUserName = getCurrentUserName();
		ApplicationUser currentUser = (ApplicationUser) loadUserByUsername(currentUserName);
		return currentUser;
	}
	
	private String getCurrentUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	public boolean hasRole(ApplicationUser user, ApplicationUserRole role) {
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		for(GrantedAuthority authority: authorities) {
			if(authority.getAuthority().equals(role.getRole())) {
				return true;
			}
		}
		
		return false;
	}
}
