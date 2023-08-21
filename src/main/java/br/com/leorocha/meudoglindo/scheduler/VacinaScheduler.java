package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.VacinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class VacinaScheduler {
@Autowired
    private VacinaService vacinaService;
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    private void notificarVacinas() {
        System.out.println("Notificando Vacinas as "+ LocalDateTime.now());
        vacinaService.notificarVacina(0);
        vacinaService.notificarVacina(3);
        vacinaService.notificarVacina(7);
    }
}
