package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.dto.NotificacaoDTO;
import br.com.leorocha.meudoglindo.model.Inscricao;
import br.com.leorocha.meudoglindo.model.Vacina;
import br.com.leorocha.meudoglindo.service.InscricaoService;
import br.com.leorocha.meudoglindo.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VacinaScheduler {
    @Autowired
    private VacinaService vacinaService;
    @Autowired
    private InscricaoService inscricaoService;

    @Scheduled(cron = "0 0 10 * * ?", zone = "America/Sao_Paulo")
    public void vacinasEm20Dias(){
        List<Vacina> vacinas = vacinaService.emQuantosDias(20);

        for (Vacina vacina : vacinas) {
            List<Inscricao> inscricoes = inscricaoService.listarPorUsuario(vacina.getAnimal().getUsuario().getId());
            for (Inscricao inscricao: inscricoes){
                String mensagem ="Faltam 20 dias para a nova dose da vacina "+vacina.getNome()+" para "+vacina.getAnimal().getNome();
                NotificacaoDTO dto = new NotificacaoDTO("Está chegando a data da vacina!",
                        mensagem,
                        "assets/icons/communication.png",
                        null,
                        LocalDateTime.now(),
                        1,
                        "Vacina",
                        "Ir para o site!",
                        inscricao.getInscricao());

                inscricaoService.enviarNotificacao(dto);
            }
        }
    }

    @Scheduled(cron = "0 0 10 * * ?", zone = "America/Sao_Paulo")
    public void vacinasEm10Dias(){
        List<Vacina> vacinas = vacinaService.emQuantosDias(10);

        for (Vacina vacina : vacinas) {
            List<Inscricao> inscricoes = inscricaoService.listarPorUsuario(vacina.getAnimal().getUsuario().getId());
            for (Inscricao inscricao: inscricoes){
                String mensagem ="Faltam 10 dias para a nova dose da vacina "+vacina.getNome()+" para "+vacina.getAnimal().getNome();
                NotificacaoDTO dto = new NotificacaoDTO("Está chegando a data da vacina!",
                        mensagem,
                        "assets/icons/communication.png",
                        null,
                        LocalDateTime.now(),
                        1,
                        "Vacina",
                        "Ir para o site!",
                        inscricao.getInscricao());

                inscricaoService.enviarNotificacao(dto);
            }
        }
    }
    @Scheduled(cron = "0 0 10 * * ?", zone = "America/Sao_Paulo")
    public void vacinasEm0Dias(){
        List<Vacina> vacinas = vacinaService.emQuantosDias(0);

        for (Vacina vacina : vacinas) {
            List<Inscricao> inscricoes = inscricaoService.listarPorUsuario(vacina.getAnimal().getUsuario().getId());
            for (Inscricao inscricao: inscricoes){
                String mensagem ="Não se esqueça, hoje é data limite da vacina "+vacina.getNome()+" para "+vacina.getAnimal().getNome();
                NotificacaoDTO dto = new NotificacaoDTO("Dia de vacina!",
                        mensagem,
                        "assets/icons/communication.png",
                        null,
                        LocalDateTime.now(),
                        1,
                        "Vacina",
                        "Ir para o site!",
                        inscricao.getInscricao());

                inscricaoService.enviarNotificacao(dto);
            }
        }
    }
}
