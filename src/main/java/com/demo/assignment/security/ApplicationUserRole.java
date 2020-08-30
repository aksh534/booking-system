package com.demo.assignment.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {
	
	ADMIN(Sets.newHashSet(ApplicationUserPermission.BOOKED_SEATS,
						  ApplicationUserPermission.UNBOOKED_SEATS,
						  ApplicationUserPermission.IN_PROGRESS_SEATS
	)),
	
	CUSTOMER(Sets.newHashSet( ApplicationUserPermission.ALL_SEATS,
						  	  ApplicationUserPermission.BOOK_SEATS
	));
	
	private final String role; 
	private final Set<ApplicationUserPermission> permissions;
	
	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.role = "ROLE_" + this.name();
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermission> getPermissions() {
		return this.permissions;
	}
	
	public Set<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for(ApplicationUserPermission permission: this.permissions) {
			GrantedAuthority authority = new SimpleGrantedAuthority(permission.getPermission());
			authorities.add(authority);
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
	
	public String getRole() {
		return this.role;
	}
}
