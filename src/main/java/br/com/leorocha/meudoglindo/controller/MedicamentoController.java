package br.com.leorocha.meudoglindo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leorocha.meudoglindo.dto.MedicamentoDTO;
import br.com.leorocha.meudoglindo.model.Medicamento;
import br.com.leorocha.meudoglindo.service.MedicamentoService;

@RestController
@RequestMapping(path = "/medicamento")
public class MedicamentoController {
    @Autowired
    private MedicamentoService service;

    @PostMapping
    public void salvar(@RequestBody MedicamentoDTO dto) throws AuthenticationException {
        service.salvar(dto);
    }

    @PutMapping
    public void atualizar(MedicamentoDTO dto) throws AuthenticationException {
        service.atualizar(dto);
    }

    @GetMapping("/{id}")
    public MedicamentoDTO buscar(@PathVariable Integer id) {
        Medicamento medicamento = service.buscar(id);
        if (null != medicamento) {
            return new MedicamentoDTO(medicamento.getId(), medicamento.getAnimal().getId(), medicamento.getNome(), medicamento.getTipoMedicamento(), medicamento.getDataMedicamento(), medicamento.getDataProxima(), medicamento.getObservacao());
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @GetMapping("/animal/{idAnimal}")
    public List<MedicamentoDTO> listarAnimal(@PathVariable Integer idAnimal) throws AuthenticationException {
        List<Medicamento> lista = service.listarPorAnimal(idAnimal);
        List<MedicamentoDTO> dtos = new ArrayList<MedicamentoDTO>();
        lista.forEach(medicamento -> dtos.add(new MedicamentoDTO(medicamento.getId(), medicamento.getAnimal().getId(), medicamento.getNome(), medicamento.getTipoMedicamento(), medicamento.getDataMedicamento(), medicamento.getDataProxima(), medicamento.getObservacao())));
        return dtos;
    }

    @GetMapping
    public List<MedicamentoDTO> listar() {
        List<Medicamento> lista = service.listarPorUsuario();
        List<MedicamentoDTO> dtos = new ArrayList<MedicamentoDTO>();
        lista.forEach(medicamento -> dtos.add(new MedicamentoDTO(medicamento.getId(), medicamento.getAnimal().getId(), medicamento.getNome(), medicamento.getTipoMedicamento(), medicamento.getDataMedicamento(), medicamento.getDataProxima(), medicamento.getObservacao())));
        return dtos;
    }
}
