package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ConsultaScheduler {
@Autowired
    private ConsultaService consultaService;
    @Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")
    private void notificarConsultas() {
        System.out.println("Notificando Consultas as "+ LocalDateTime.now());
        consultaService.notificarConsulta(0);
        consultaService.notificarConsulta(3);
        consultaService.notificarConsulta(7);
    }
}
