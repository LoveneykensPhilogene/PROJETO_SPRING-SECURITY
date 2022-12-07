/**
 * 
 */
package com.security.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.enums.ERole;

/**
 * @author ${L. Philogene}

  *02-11-2022
 * ${Email : loveneykens@gmail.com}
 */
@Entity
public class Funcao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,unique = true)
	private ERole role;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "funcoes",cascade = CascadeType.ALL)
	private List<Usuario>usuarios;
	/**
	 * @param role
	 */
	public Funcao(ERole role) {
		
		this.role = role;
	}
	/**
	 * 
	 */
	public Funcao() {
		
	}
	
public Funcao(Long id, ERole role, List<Usuario> usuarios) {
		
		this.id = id;
		this.role = role;
		this.usuarios = usuarios;
	}

public Funcao(List<Usuario> users) {
		this.usuarios=users;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ERole getRole() {
		return role;
	}
	public void setRole(ERole role) {
		this.role = role;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
}
