/**
 * 
 */
package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.exeptions.JwtAuthenticationEntryPoint;
import com.security.jwt.AthenticationFilter;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {// extends WebSecurityConfigurerAdapter {

	@Autowired
	private AthenticationFilter jwtFilter;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
		http

//		.httpBasic()
//		.and()
				.authorizeHttpRequests().antMatchers(HttpMethod.POST, "/api/v1/Login").permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/CadastrarUsuario").permitAll()
				.antMatchers("/api/v1/Usuarios").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/Role/BuscarRoles").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/Role/CadastrarRole").permitAll()
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).cors().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable();

		return http.build();

	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(usuarioCustumerService).passwordEncoder(encoder());
////		auth
////			.inMemoryAuthentication()
////			.withUser("user")  // #1
////			.password(new BCryptPasswordEncoder().encode("1234"))
////			.roles("ADMIN");
//            
//	}
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			
//			.httpBasic()
//			.and()
//			.authorizeHttpRequests()
//			.antMatchers(HttpMethod.POST,"/api/v1/Login").permitAll()
//			.antMatchers(HttpMethod.POST,"/api/v1/CadastrarUsuario").hasRole("ADMIN")
//			.antMatchers("/api/v1/Usuarios").hasRole("ADMIN")
//			.anyRequest().authenticated()
//			.and().csrf().disable()
//			;
//			
//	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
