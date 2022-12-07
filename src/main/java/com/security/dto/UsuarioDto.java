package com.security.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.security.model.Funcao;
import com.security.model.Usuario;

public class UsuarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String senha;

	private List<FuncaoDto> funcoesDtos;

	public UsuarioDto() {
	}

	public UsuarioDto(Long id, String nome, String senha, List<FuncaoDto> funcaoDtos) {

		this.id = id;
		this.nome = nome;
		this.senha = senha;
		this.funcoesDtos = funcaoDtos;
	}

	public UsuarioDto(List<FuncaoDto> func) {

		this.funcoesDtos = new ArrayList<>();
		for (FuncaoDto funcaoDto : func) {
			funcoesDtos.add(funcaoDto);
		}
	}

	public UsuarioDto(Usuario usuario) {
		this.funcoesDtos = new ArrayList<>();

		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.senha = usuario.getSenha();
		for (Funcao f : usuario.getFuncoes()) {
			FuncaoDto funcaoDto = new FuncaoDto();
			funcaoDto.setId(f.getId());
			funcaoDto.setRole(f.getRole());
			funcoesDtos.add(funcaoDto);
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

	public List<FuncaoDto> getFuncaoDtos() {
		return funcoesDtos;
	}

}
