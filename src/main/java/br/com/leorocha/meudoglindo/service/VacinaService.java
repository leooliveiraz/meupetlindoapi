package br.com.leorocha.meudoglindo.service;

import java.time.LocalDate;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import br.com.leorocha.meudoglindo.enums.PermissaoCompartilhamento;
import br.com.leorocha.meudoglindo.model.Inscricao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.leorocha.meudoglindo.dto.VacinaDTO;
import br.com.leorocha.meudoglindo.model.Animal;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.model.Vacina;
import br.com.leorocha.meudoglindo.repository.VacinaRepository;

@Service
public class VacinaService {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private VacinaRepository repository;
    @Autowired
    private CompartilharAnimalService compartilharAnimalService;

    @Autowired
    private InscricaoService inscricaoService;

    public void salvar(VacinaDTO dto) throws AuthenticationException {
        Animal animal = animalService.buscar(dto.getIdAnimal());
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        Boolean permissao = compartilharAnimalService.estaCompartilhado(animal.getId(), usuario.getId(), PermissaoCompartilhamento.EDITAR);
        if (animal.getUsuario().getId() != usuario.getId() && !permissao) {
            throw new AuthenticationException("Você não pode alterar esse registro");
        }
        Vacina vacina = new Vacina(null, dto.getNome(), dto.getDataVacina(), dto.getDataProximaVacina(), dto.getObservacao(), animal);
        repository.save(vacina);
    }

    public void atualizar(VacinaDTO dto) throws AuthenticationException {
        Vacina vacina = buscar(dto.getId());
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        if (vacina.getAnimal().getUsuario().getId() != usuario.getId()) {
            throw new AuthenticationException("Você não pode alterar esse registro");
        }
        vacina.setDataVacina(dto.getDataVacina());
        vacina.setDataProximaVacina(dto.getDataProximaVacina());
        vacina.setNome(vacina.getNome());
        repository.save(vacina);
    }

    public Vacina buscar(Integer id) {
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        return repository.findByIdAndAnimalUsuarioId(id, usuario.getId()).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<Vacina> listarPorAnimal(Integer idAnimal) throws AuthenticationException {
        if (animalService.validarAcessoAoAnimal(idAnimal)) {
            return repository.findByAnimalIdOrderByDataVacinaDesc(idAnimal);
        } else {
            throw new AuthenticationException("Você não pode ver essas informações.");
        }
    }

    public void deletarLista(List<Vacina> listaVacina) {
        this.repository.deleteAll(listaVacina);

    }

    public List<Vacina> listarPorUsuario() {
        Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        return (List<Vacina>) repository.findByAnimalUsuarioIdOrderByDataVacinaDesc(usuario.getId());
    }

    public List<Vacina> emQuantosDias(int qtdDias) {
        LocalDate dataEscolhida = LocalDate.now();
        dataEscolhida = dataEscolhida.plusDays(qtdDias);
        List<Vacina> vacinas = repository.findByDataProximaVacinaAndAnimalDataObitoIsNull(dataEscolhida);
        return vacinas;
    }

    public void notificarVacina(int diferencaDias) {
        List<Vacina> listaVacinas = emQuantosDias(diferencaDias);
        listaVacinas.forEach(vacina -> {
            Integer idUsuario = vacina.getAnimal().getUsuario().getId();
            List<Inscricao> listaInscricao  = inscricaoService.listByUsuarioId(idUsuario);
            listaInscricao.forEach(inscricao -> {
                inscricaoService.prepareSendNotification("MEU PET LINDO - HORA DA VACINA",
                        "Não se esqueça, está na hora de aplicar a vacina em seu pet: %s!"
                                .formatted(vacina.getAnimal().getNome()),
                        "aviso-vacina",
                        "Abrir",
                        vacina.getAnimal().getId(),
                        inscricao);
            });
        });
    }
}
