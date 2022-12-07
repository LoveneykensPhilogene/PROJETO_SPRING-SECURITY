package com.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.security.dto.UsuarioDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String SECRET = new UUID(0, 0).toString();
	private static final long EXPIRACAO = 60000 * 1000;

	public TokenManager() {
	}

	public Token CriarToken(UsuarioDto usuarioDto) {
		Token token = new Token();

		String jwt = Jwts.builder().setSubject(usuarioDto.getNome()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
				.signWith(SignatureAlgorithm.HS256, SECRET).compact();

		token.setToken(jwt);
		;

		return token;
	}

	public boolean ValidarToken(Token token, UsuarioDto usuarioDto) {
		String username = getUsernameFromToken(token);
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.getToken()).getBody();
		boolean isTokenExpired = claims.getExpiration().before(new Date());
		// return (username.equals(usuario.getNome()) && !isTokenExpired);
		if (username.equals(usuarioDto.getNome()) && !isTokenExpired) {
			return true;
		} else {
			return false;
		}
	}

	public String getUsernameFromToken(Token token) {
		final Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.getToken()).getBody();
		return claims.getSubject();
	}

}
