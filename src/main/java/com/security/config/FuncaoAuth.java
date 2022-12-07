/**
 * 
 */
package com.security.config;

import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

import com.security.dto.FuncaoDto;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */

public class FuncaoAuth implements Serializable, GrantedAuthority {

	private static final long serialVersionUID = 1L;
	private String role;

	/**
	 * @param role
	 */

	public FuncaoAuth(FuncaoDto funcaoDto) {
		this.role = funcaoDto.getRole().toString();
	}

	/**
	 * 
	 */
	public FuncaoAuth() {
	}

	@Override
	public String getAuthority() {

		return getRole();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
