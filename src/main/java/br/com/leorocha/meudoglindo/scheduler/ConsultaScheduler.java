package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class ConsultaScheduler {
@Autowired
    private ConsultaService consultaService;
    @Scheduled(cron = "0 0 10/16 * * *", zone = "America/Sao_Paulo")
    private void notificarConsultas() {
        consultaService.notificarMedicamento(0);
        consultaService.notificarMedicamento(3);
        consultaService.notificarMedicamento(7);
    }
}
