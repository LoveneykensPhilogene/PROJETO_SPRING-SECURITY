/**
 * 
 */
package com.security.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.security.dto.FuncaoDto;
import com.security.enums.ERole;
import com.security.model.Funcao;
import com.security.model.Usuario;
import com.security.repositories.FuncaoRepository;
import com.security.repositories.UsuarioRepository;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@RestController
@RequestMapping("Role")
public class FuncaoControler {

	@Autowired
	private FuncaoRepository funcaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/{id}/CadastrarRole")
	public ResponseEntity<FuncaoDto> Cadastrar(@Validated @RequestBody FuncaoDto funcaoDto,
			@Validated @PathVariable Long id) throws Exception {
		Optional<Usuario> admin = usuarioRepository.findById(id);
		if (admin.isPresent()) {
			Funcao funcao = new Funcao();
			funcao.setRole(funcaoDto.getRole());
			funcao = funcaoRepository.save(funcao);
			funcaoDto.setId(funcao.getId());
			funcaoDto.setRole(funcao.getRole());
			return ResponseEntity.status(HttpStatus.CREATED).body(funcaoDto);
		} else {
			throw new Exception("Sem permissão");
		}

	}

	@GetMapping("/BuscarRoles")
	public ResponseEntity<List<FuncaoDto>> Funcoes() {
		List<Funcao> funcoes = funcaoRepository.findAll();
		List<FuncaoDto> funcaoDtos = funcoes.stream().map(role -> new FuncaoDto(role)).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(funcaoDtos);
	}

	@GetMapping("/BuscarPorRole")
	public ResponseEntity<FuncaoDto> BuscarPorRole(@Validated @RequestParam("role") ERole role) {
		Optional<Funcao> roleOptional = funcaoRepository.findByRole(role);
		FuncaoDto funcaoDto = new FuncaoDto(roleOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body(funcaoDto);
	}

	@DeleteMapping("RemoverPorId")
	public FuncaoDto RomoverRoler(@Validated @RequestParam("id") Long id) {

		Optional<Funcao> roleOptional = funcaoRepository.findById(id);
		FuncaoDto funcaoDto = new FuncaoDto(roleOptional.get());
		if (roleOptional.isPresent()) {
			funcaoRepository.deleteById(id);
		}

		return funcaoDto;
	}

//	@PutMapping("AlterarUmRole")
//	public FuncaoDto AlterarRole(@Validated @RequestParam("id") Long id,
//			@RequestBody(required = true) FuncaoDto funcaoDto) {
//
//		Optional<Funcao> funcaoOptional = funcaoRepository.findById(id);
//
//		if (funcaoOptional.isPresent()) {
//			funcaoOptional.get().setRole(funcaoDto.getRole());
//			funcaoRepository.save(funcaoOptional.get());
//		}
//		funcaoDto.setId(funcaoOptional.get().getId());
//		funcaoDto.setRole(funcaoOptional.get().getRole());
//
//		return funcaoDto;
//	}

}
