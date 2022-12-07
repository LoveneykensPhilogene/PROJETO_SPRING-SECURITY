/**
 * 
 */
package com.security.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.security.dto.UsuarioDto;
import com.security.jwt.Token;
import com.security.services.UsuarioService;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@RestController
@RequestMapping("api/v1")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("Login")
	public ResponseEntity<Token> FazerLogin(@Validated @RequestBody UsuarioDto usuarioDto) throws Exception {
		Token token = usuarioService.FazerLogin(usuarioDto);
		return ResponseEntity.status(HttpStatus.OK).body(token);
	}

	@PostMapping("CadastrarUsuario")
	public ResponseEntity<UsuarioDto> Cadastrar(@RequestBody UsuarioDto usuarioDto) {

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.CadastrarUsuario(usuarioDto));
	}

	@GetMapping("Usuarios")
	public ResponseEntity<List<UsuarioDto>> TodosOsUsuarios(@SortDefault Sort sort) {
		List<UsuarioDto> usuarioDtos = usuarioService.BuscarTodosUsuario(sort);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioDtos);
	}

	@PutMapping("AdicionarFuncao")
	public ResponseEntity<UsuarioDto> AdicionarFuncaoParaUmUsuario(@Validated @RequestParam("idAdmin") Long idAdmin,
			@Validated @RequestParam("idFuncao") Long idFuncao, @Validated @RequestParam("idUsuario") Long idUsuario)
			throws Exception {
		UsuarioDto usuarioDto = usuarioService.AdicionarFuncao(idAdmin, idFuncao, idUsuario);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
	}

	@PutMapping("RemoverFuncaoDeUmUsuario")
	public ResponseEntity<UsuarioDto> RemoverFuncaoParaUmUsuario(@Validated @RequestParam("idAdmin") Long idAdmin,
			@Validated @RequestParam("idFuncao") Long idFuncao, @Validated @RequestParam("idUsuario") Long idUsuario)
			throws Exception {
		UsuarioDto usuarioDto = usuarioService.RemoverFuncaoDeUmUsuario(idAdmin, idFuncao, idUsuario);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
	}

	@DeleteMapping("DeletarUmUsuarioPorId/{id}")
	public ResponseEntity<UsuarioDto> DeletarUsuarioPorId(@Validated @PathVariable(name = "id") Long id)
			throws Exception {
		return ResponseEntity.status(HttpStatus.GONE).body(usuarioService.RemoverUmUsuarioPorId(id));
	}

}
