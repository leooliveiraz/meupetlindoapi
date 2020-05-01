package br.com.leorocha.meudoglindo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repository; 
	
	public void salvar(Usuario usuario) {
		repository.save(usuario);
	}
	public void atualizar(Usuario usuario) {
		repository.save(usuario);
	}
	public Usuario buscar(Integer id) {
		return repository.findById(id).orElse(null);
	}
	public List<Usuario> listar() {
		return (List<Usuario>) repository.findAll();
	}
	public void delete(Integer id) {
		repository.deleteById(id);
	}
	public boolean usuarioExiste(String sub) {
		boolean existe = repository.existsBySub(sub);
		return existe;
	}
	public Usuario buscarPorSub(String sub) {
		Usuario usuario = repository.findBySub(sub);
		return usuario;
	}
}
