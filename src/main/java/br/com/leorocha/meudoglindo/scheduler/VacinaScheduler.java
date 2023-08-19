package br.com.leorocha.meudoglindo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class VacinaScheduler {

    @Scheduled(cron = "*/30 * * * * ?", zone = "America/Sao_Paulo")
    private void notificarVacinasChegando() {
        System.out.println("Vou rodar a cada meio minuto");

    }
}
