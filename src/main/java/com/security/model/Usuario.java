/**
 * 
 */
package com.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */

@Entity
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false, unique = true)
	private String senha;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "USUARIO_FUNCAO", joinColumns = { @JoinColumn(name = "USUARIO_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "FUNCAO_ID") })
	private List<Funcao> funcoes;

	/**
	 * 
	 */
	public Usuario() {

	}

	public Usuario(Long id, String nome, String senha, List<Funcao> funcoes) {

		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.funcoes = funcoes;
	}

	public Usuario(List<Funcao> func) {

		this.funcoes = new ArrayList<Funcao>();
		for (Funcao funcao : func) {
			this.funcoes.add(funcao);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", senha=" + senha + ", funcoes=" + funcoes.get(0) + "]";
	}

}
