/**
 * 
 */
package com.security.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.enums.ERole;
import com.security.model.Funcao;

/**
 * @author ${L. Philogene}
 * 
 *         02-11-2022 ${Email : loveneykens@gmail.com}
 */
@Repository
public interface FuncaoRepository extends JpaRepository<Funcao,Long>{
	Optional<Funcao> findByRole(ERole role);

}
