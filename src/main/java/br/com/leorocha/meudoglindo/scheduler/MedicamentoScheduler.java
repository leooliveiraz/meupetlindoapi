package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class MedicamentoScheduler {
@Autowired
    private MedicamentoService medicamentoService;
    @Scheduled(cron = "0 0 10/16 * * *", zone = "America/Sao_Paulo")
    private void notificarMedicamentos() {
        medicamentoService.notificarMedicamento(0);
        medicamentoService.notificarMedicamento(3);
        medicamentoService.notificarMedicamento(7);
    }
}
