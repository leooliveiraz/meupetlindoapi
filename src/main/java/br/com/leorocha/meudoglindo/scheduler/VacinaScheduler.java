package br.com.leorocha.meudoglindo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

public class VacinaScheduler {

    @Scheduled(cron = "0 15 10 15 * ?", zone = "Europe/Paris")
    private void notificarVacinasChegando() {

    }
}
