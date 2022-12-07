package com.security.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.dto.UsuarioDto;
import com.security.enums.ERole;
import com.security.jwt.Token;
import com.security.jwt.TokenManager;
import com.security.model.Funcao;
import com.security.model.Usuario;
import com.security.repositories.FuncaoRepository;
import com.security.repositories.UsuarioRepository;

@Service
public class UsuarioService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	public UsuarioDto CadastrarUsuario(UsuarioDto usuarioDto) {
		usuarioDto.setSenha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()));
		Optional<Funcao> roleOptional = funcaoRepository.findByRole(ERole.ROLE_USER);

		List<Funcao> funcoes = new ArrayList<>();
		if (roleOptional.isPresent()) {
			funcoes.add(roleOptional.get());
			Usuario usuario = new Usuario(funcoes);
			usuario.setNome(usuarioDto.getNome());
			usuario.setSenha(usuarioDto.getSenha());
			roleOptional.get().getUsuarios().add(usuario);
			usuario = usuarioRepository.save(usuario);
			usuarioDto = new UsuarioDto(usuario);

		} else {
			throw new UsernameNotFoundException("Funcao não encontrada");
		}

		return usuarioDto;
	}

	public Token FazerLogin(UsuarioDto usuarioDto) throws Exception {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usuarioDto.getNome(), usuarioDto.getSenha()));
		Token token = tokenManager.CriarToken(usuarioDto);
		return token;
	}

	public List<UsuarioDto> BuscarTodosUsuario(org.springframework.data.domain.Sort sort) {

		List<Usuario> usuarios = usuarioRepository.findAll(sort);
		List<UsuarioDto> usuarioDtos = usuarios.stream().map(usuario -> new UsuarioDto(usuario))
				.collect(Collectors.toList());
		return usuarioDtos;

	}

	public UsuarioDto AdicionarFuncao(Long idAdmin, Long idFuncao, Long idUsuario) throws Exception {

		// idAdmin, usuario autorizado a fazer esse tipo de alteracao
		Optional<Usuario> usuarioAdminOptional = usuarioRepository.findById(idAdmin);
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
		Optional<Funcao> funcaoOptional = funcaoRepository.findById(idFuncao);
		UsuarioDto usuarioDto = new UsuarioDto();

		if (usuarioAdminOptional.isPresent() && usuarioOptional.isPresent() && funcaoOptional.isPresent()) {
			List<Funcao> listFuncoes = new ArrayList<>();
			for (Funcao funcao : usuarioOptional.get().getFuncoes()) {
				listFuncoes.add(funcao);
			}
			if (listFuncoes.contains(funcaoOptional.get())) {
				throw new Exception("Função já existe");
			} else {
				listFuncoes.add(funcaoOptional.get());
			}
			// list.add(funcaoOptional.get());
			Usuario usuario = new Usuario(listFuncoes);
			usuario.setId(usuarioOptional.get().getId());
			usuario.setNome(usuarioOptional.get().getNome());
			usuario.setSenha(usuarioOptional.get().getSenha());
			usuario = usuarioRepository.save(usuario);
			usuarioDto = new UsuarioDto(usuario);

		} else {
			throw new Exception("Usuario não existe");
		}

		return usuarioDto;
	}

	public UsuarioDto RemoverFuncaoDeUmUsuario(Long idAdmin, Long idFuncao, Long idUsuario) throws Exception {
		Optional<Usuario> usuarioAdminOptional = usuarioRepository.findById(idAdmin);
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
		Optional<Funcao> funcaoOptional = funcaoRepository.findById(idFuncao);
		Usuario usuario = new Usuario();
		if (usuarioAdminOptional.isPresent() && usuarioOptional.isPresent() && funcaoOptional.isPresent()) {
			if (funcaoOptional.get().getRole().equals(ERole.ROLE_USER)) {
				throw new Exception("Não pode excluir essa Função");
			} else {
				usuarioOptional.get().getFuncoes().remove(funcaoOptional.get());
				funcaoOptional.get().getUsuarios().remove(usuarioOptional.get());
				usuario = usuarioRepository.save(usuarioOptional.get());
			}
		} else {
			throw new Exception("Usuario não existe ou Função não existe");
		}

		return new UsuarioDto(usuario);

	}

	public UsuarioDto RemoverUmUsuarioPorId(Long idUsuario) throws Exception {
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
		if (usuarioOptional.isPresent()) {
			usuarioOptional.get().getFuncoes().removeAll(usuarioOptional.get().getFuncoes());
			usuarioRepository.save(usuarioOptional.get());
			usuarioRepository.deleteById(idUsuario);
			return new UsuarioDto(usuarioOptional.get());

		} else {
			throw new Exception("Este Usuario não existe");
		}
	}

}
