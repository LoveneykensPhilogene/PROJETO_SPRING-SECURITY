/**
 * 
 */
package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.dto.UsuarioDto;
import com.security.model.Usuario;
import com.security.repositories.UsuarioRepository;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@Service
@Transactional
public class UsuarioCustumerService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepo.findByNome(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario não foi logado : " + username));
		UsuarioCustumer usuarioCustumer = new UsuarioCustumer(new UsuarioDto(usuario));
		return new User(usuarioCustumer.getUsername(), usuarioCustumer.getPassword(), usuarioCustumer.getAuthorities());

	}

}
