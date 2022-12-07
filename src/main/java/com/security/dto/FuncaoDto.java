package com.security.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import org.springframework.lang.NonNull;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.enums.ERole;
import com.security.model.Funcao;

public class FuncaoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@NonNull
	private Long id;

	@NonNull
	@Enumerated(EnumType.STRING)
	private ERole role;

	@JsonIgnore
	private List<UsuarioDto> usuarioDtos;

	public FuncaoDto() {
	}

	public FuncaoDto(Long id, ERole role, List<UsuarioDto> usuarioDtos) {

		this.id = id;
		this.role = role;
		this.usuarioDtos = usuarioDtos;
	}

	public FuncaoDto(Funcao funcao) {
		this.id = funcao.getId();
		this.role = funcao.getRole();
//		this.usuarioDtos = new ArrayList<>();
//		for (Usuario usuario : funcao.getUsuarios()) {
//			usuarioDtos.add(new UsuarioDto(usuario));
//		}
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

	public List<UsuarioDto> getUsuarioDtos() {
		return usuarioDtos;
	}

}
