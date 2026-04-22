package br.com.leorocha.meudoglindo.scheduler;

import br.com.leorocha.meudoglindo.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationScheduler {
    @Autowired
    private InscricaoService inscricaoService;
    public void notificar(){
        inscricaoService.prepareSendNotification("MEU PET LINDO - HORA DA VACINA",
                "Não se esqueça, está na hora de aplicar a vacina em seu pet: %s!"
                        .formatted(""),
                "aviso-vacina",
                "Abrir",
                1,
                "",
                null);
    }
}
