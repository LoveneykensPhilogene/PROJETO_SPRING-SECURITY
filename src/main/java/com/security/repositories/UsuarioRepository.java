/**
 * 
 */
package com.security.repositories;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.model.Usuario;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public Optional<Usuario> findByNome(String nome);

	public Page<Usuario> findAll(Pageable pageable);

}
