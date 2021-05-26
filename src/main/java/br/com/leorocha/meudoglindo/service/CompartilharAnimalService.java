package br.com.leorocha.meudoglindo.service;

import br.com.leorocha.meudoglindo.dto.DadosCompartilhamentoAnimalDTO;
import br.com.leorocha.meudoglindo.enums.StatusCompartilhamento;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.CompartilhamentoAnimal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.CompartilhamentoAnimalRepository;
import br.com.leorocha.meudoglindo.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class CompartilharAnimalService {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private CompartilhamentoAnimalRepository repository;

    public String gerarCodigo(Integer idAnimal) throws AuthenticationException {
        Animal animal = animalService.buscar(idAnimal);
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        if(animal.getUsuario().getId() != usuario.getId()) {
            throw new AuthenticationException("Você não pode excluir esse registro");
        }
        String codigo = SenhaUtil.criptografarMD5(
                "LéoRochaDev17052021"+LocalDateTime.now().toString()+"-"+usuario.getId()+"-"+animal.getId()+"-"+animal.getNome()+"Criptografado");

        CompartilhamentoAnimal compartilhamentoAnimal = new CompartilhamentoAnimal(null,animal,codigo, LocalDateTime.now(), StatusCompartilhamento.AGUARDANDO, null);
        repository.save(compartilhamentoAnimal);

        return codigo;
    }


    public DadosCompartilhamentoAnimalDTO buscarDadosCompartilhamento(String codigo) throws Exception {
        CompartilhamentoAnimal compartilhamentoAnimal = repository.findByCodigo(codigo);

        if(compartilhamentoAnimal != null){
            DadosCompartilhamentoAnimalDTO dto = new DadosCompartilhamentoAnimalDTO(compartilhamentoAnimal);
            return dto;
        } else {
            throw new Exception("Link inválido");
        }

    }

    public CompartilhamentoAnimal confirmarCompartilhamento(String codigo) throws Exception {
        CompartilhamentoAnimal compartilhamentoAnimal = repository.findByCodigo(codigo);
        if (compartilhamentoAnimal == null)
            throw new Exception("Não foi possível confirmar o compartilhamento desse animalzinho!");
        if(compartilhamentoAnimal.getStatus().equals(StatusCompartilhamento.COMPARTILHADO) ||
                compartilhamentoAnimal.getStatus().equals(StatusCompartilhamento.CANCELADO) )
            throw new Exception("Esse link não é mais valido, por favor, peça para compartilharem com você novamente!");
        if(compartilhamentoAnimal.getData().plusDays(1).isBefore(LocalDateTime.now()))
            throw new Exception("O código de compartilhamento venceu, peça para compartilharem com você novamente!");

        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        if (usuario == null)
            throw new AuthenticationException("Você precisa estar logado!");
        if (usuario.getId().equals(compartilhamentoAnimal.getAnimal().getUsuario().getId()))
            throw new Exception("Você é o dono desse animalzinho!");

        List<CompartilhamentoAnimal> listaCompartilhamentoExistente = repository.findByAnimalIdAndUsuarioIdAndStatus(compartilhamentoAnimal.getAnimal().getId(), usuario.getId(), StatusCompartilhamento.COMPARTILHADO);
        if(listaCompartilhamentoExistente != null && !listaCompartilhamentoExistente.isEmpty()){
            throw new Exception("Você já possui acesso as informações desse animalzinho.!");
        }

        compartilhamentoAnimal.setUsuario(usuario);
        compartilhamentoAnimal.setStatus(StatusCompartilhamento.COMPARTILHADO);

        repository.save(compartilhamentoAnimal);
        return compartilhamentoAnimal;

    }

    public List<Animal> listarCompartilhadosPorUsuario() throws AuthenticationException {
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        if(usuario == null)
            throw new AuthenticationException("Você precisa estar logado.");
        List<CompartilhamentoAnimal> listCompartilhamento = repository.findByUsuarioIdAndStatus(usuario.getId(), StatusCompartilhamento.COMPARTILHADO);
        HashMap<Integer , Animal > hashMap = new HashMap<>();
        listCompartilhamento.forEach(compartilhamentoAnimal -> hashMap.put(compartilhamentoAnimal.getAnimal().getId(), compartilhamentoAnimal.getAnimal()));
        List<Animal> listAnimal = new ArrayList<>(hashMap.values());

        return listAnimal;
    }
}
