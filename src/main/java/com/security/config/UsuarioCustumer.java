/**
 * 
 */
package com.security.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.dto.FuncaoDto;
import com.security.dto.UsuarioDto;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */

@Transactional
public class UsuarioCustumer implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome;
	private String senha;
	private List<FuncaoAuth> auths;

	public UsuarioCustumer(UsuarioDto usuarioDto) {
		this.nome = usuarioDto.getNome();
		this.senha = usuarioDto.getSenha();
		this.auths = new ArrayList<FuncaoAuth>();
		for (FuncaoDto funcaoDto : usuarioDto.getFuncaoDtos()) {
			this.auths.add(new FuncaoAuth(funcaoDto));

		}

	}

	/**
	 * 
	 */
	public UsuarioCustumer() {

	}

	public UsuarioCustumer(String nome, String senha, List<FuncaoAuth> auths) {

		this.nome = nome;
		this.senha = senha;
		this.auths = auths;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return getAuths();
	}

	@Override
	public String getPassword() {

		return getSenha();
	}

	@Override
	public String getUsername() {

		return getNome();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<FuncaoAuth> getAuths() {
		return auths;
	}

}
