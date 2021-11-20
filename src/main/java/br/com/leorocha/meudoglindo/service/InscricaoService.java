package br.com.leorocha.meudoglindo.service;

import br.com.leorocha.meudoglindo.dto.NotificacaoDTO;
import br.com.leorocha.meudoglindo.model.Inscricao;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.InscricaolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InscricaoService {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private InscricaolRepository inscricaoRepository;
    @Autowired
    private RestService restService;

    @Value("${webpush.server}")
    private String webpushServer;


    public void salvar(String json){
        Usuario usuario = null;
        try{
            usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        boolean inscricaoExistente = inscricaoRepository.existsByInscricao(json);
        if (!inscricaoExistente) {
            Inscricao inscricao = new Inscricao();
            inscricao.setInscricao(json);
            inscricao.setDataInscricao(LocalDateTime.now());
            if(usuario != null){
                inscricao.setUsuario(usuario);
            }

            inscricaoRepository.save(inscricao);
        } else {
            Inscricao inscricao = inscricaoRepository.findByInscricao(json);
            inscricao.setInscricao(json);
            inscricao.setDataInscricao(LocalDateTime.now());
            if(usuario != null){
                inscricao.setUsuario(usuario);
            }

            inscricaoRepository.save(inscricao);
        }
    }

    public void enviarNotificacao(NotificacaoDTO dto){
        restService.post(this.webpushServer,dto);
    }

    public List<Inscricao> listarPorUsuario(Integer id) {
        return inscricaoRepository.findByUsuarioId(id);
    }
}
