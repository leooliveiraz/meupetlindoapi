package br.com.leorocha.meudoglindo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.leorocha.meudoglindo.model.Usuario;
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	boolean existsBySub(String sub);

	Usuario findBySub(String sub);
	
}
