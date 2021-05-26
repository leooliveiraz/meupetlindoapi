package br.com.leorocha.meudoglindo.controller;

import br.com.leorocha.meudoglindo.dto.AnimalDTO;
import br.com.leorocha.meudoglindo.dto.CodigoCompartilhamentoAnimalDTO;
import br.com.leorocha.meudoglindo.dto.DadosCompartilhamentoAnimalDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.CompartilhamentoAnimal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.service.AnimalService;
import br.com.leorocha.meudoglindo.service.CompartilharAnimalService;
import br.com.leorocha.meudoglindo.service.RequestService;
import br.com.leorocha.meudoglindo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path="/compartilhar-animal")
public class CompartilharAnimalController {
    @Autowired
    private CompartilharAnimalService service;

    @PostMapping("/{idAnimal}")
    public CodigoCompartilhamentoAnimalDTO buscar(@PathVariable Integer idAnimal) throws AuthenticationException {
        String codigo = service.gerarCodigo(idAnimal);
        CodigoCompartilhamentoAnimalDTO dto = new CodigoCompartilhamentoAnimalDTO(codigo);
        return dto;
    }

    @GetMapping("/{codigo}")
    public DadosCompartilhamentoAnimalDTO buscarDadosCompartilhamento(@PathVariable String codigo) throws Exception {
        DadosCompartilhamentoAnimalDTO dto = service.buscarDadosCompartilhamento(codigo);
        if(dto.getDataMaximaCompartilhar().isBefore(LocalDateTime.now())){
            throw new Exception("Link inválido");
        }

        return dto;
    }

    @PostMapping("/confirmar/{codigo}")
    public AnimalDTO confirmarCompartilhamento(@PathVariable String codigo) throws Exception {
        CompartilhamentoAnimal compartilhamento = service.confirmarCompartilhamento(codigo);
        AnimalDTO dto = new AnimalDTO();
        dto.setId(compartilhamento.getAnimal().getId());
        return dto;
    }

}
