package com.demo.assignment.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.demo.assignment.model.ApplicationUser;
import com.demo.assignment.security.ApplicationUserRole;

@Repository
public class ApplicationUserRepository {
	
	private final PasswordEncoder passwordEncoder;
	
	private static List<ApplicationUser> applicationUsers;
	
	@Autowired
	public ApplicationUserRepository(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		applicationUsers = getApplicationUsers();
	}
	
	public ApplicationUser selectApplicationUserByUsername(String username) {
		for(ApplicationUser applicationUser: applicationUsers) {
			if(applicationUser.getUsername().equals(username)) {
				return applicationUser;
			}
		}
		return null;
	}
	
	private List<ApplicationUser> getApplicationUsers() {
		ApplicationUser test_user_1 = new ApplicationUser( ApplicationUserRole.CUSTOMER.getAuthorities(), 
														    "test-user-1", 
														    passwordEncoder.encode("password"), 
															true, true, true, true);
		
		
		ApplicationUser admin = new ApplicationUser( ApplicationUserRole.ADMIN.getAuthorities(), 
													 "admin", 
													 passwordEncoder.encode("password"), 
													 true, true, true, true);
		
		ApplicationUser test_user_2 = new ApplicationUser( ApplicationUserRole.CUSTOMER.getAuthorities(), 
															"test-user-2", 
															passwordEncoder.encode("password"), 
															true, true, true, true);

		List<ApplicationUser> applicationUsers = Arrays.asList(test_user_1, admin, test_user_2);
		
		return applicationUsers;											
	}
}
