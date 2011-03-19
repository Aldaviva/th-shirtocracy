package org.techhouse.shirts.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.techhouse.shirts.data.enums.Role;

public class RoleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private final Role role;
	
	public RoleGrantedAuthority(Role role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}

}
