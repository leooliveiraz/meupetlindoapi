package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class VacinaScheduler {
@Autowired
    private VacinaService vacinaService;
    @Scheduled(cron = "0 0 10/16 * * *", zone = "America/Sao_Paulo")
    private void notificarVacinas() {
        vacinaService.notificarVacina(0);
        vacinaService.notificarVacina(3);
        vacinaService.notificarVacina(7);
    }
}
