package com.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.security.config.UsuarioCustumerService;
import com.security.dto.UsuarioDto;

@Service
public class AthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UsuarioCustumerService usuarioCustumerService;

	@Autowired
	private TokenManager tokenManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Token tokenHeader = new Token(request.getHeader("AUTHORIZATION"));
		String nomeUsuario = null;
		Token token = new Token();

		if (tokenHeader.getToken() != null && tokenHeader.getToken().startsWith("Bearer")) {
			token.setToken(tokenHeader.getToken().substring(7));
			nomeUsuario = tokenManager.getUsernameFromToken(token);

		} else {

		}

		if (nomeUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = usuarioCustumerService.loadUserByUsername(nomeUsuario);
			UsuarioDto usuarioDto = new UsuarioDto();
			usuarioDto.setNome(userDetails.getUsername());
			usuarioDto.setSenha(userDetails.getPassword());

			if (tokenManager.ValidarToken(token, usuarioDto)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}

		}

		filterChain.doFilter(request, response);

	}

}
