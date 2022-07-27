package br.com.leorocha.meudoglindo.service;

import br.com.leorocha.meudoglindo.model.Inscricao;
import br.com.leorocha.meudoglindo.model.Usuario;
import br.com.leorocha.meudoglindo.repository.InscricaoRepository;
import com.google.gson.Gson;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class WebPushNotificationService {
    @Autowired
    private RequestService requestService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Value("${vapid.public.key}")
    private String publicKey;
    @Value("${vapid.private.key}")
    private String privateKey;

    private PushService pushService;
    private List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    private void init() throws GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        pushService = new PushService(publicKey, privateKey);
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void subscribe(Subscription subscription) {
        Gson gson = new Gson();
        //todo verificar se já existe inscricao antes de criar outra
        Inscricao inscricao = null;
        if (inscricaoRepository.existsByInscricao(gson.toJson(subscription))) {
            inscricao = inscricaoRepository.findByInscricao(gson.toJson(subscription));
        } else {
            inscricao = new Inscricao();
        }
        inscricao.setInscricao(gson.toJson(subscription));
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setAtivo(true);
        inscricao.setUserAgent(requestService.getProp("user-agent"));

        if (requestService.getUserDTO() != null) {
            Usuario usuario = usuarioService.buscarPorSub(requestService.getUserDTO().getSub());
            inscricao.setUsuario(usuario);
        } else {
            inscricao.setUsuario(null);
        }
        inscricaoRepository.save(inscricao);

        this.subscriptions.add(subscription);
    }

    public void unsubscribe(Subscription subscription) {
        Gson gson = new Gson();
        String sub = gson.toJson(subscription);
        Inscricao inscricao = inscricaoRepository.findByInscricao(sub);
        inscricao.setAtivo(false);
        inscricao.setDataCancelamento(LocalDateTime.now());
        inscricaoRepository.save(inscricao);
    }

    public void sendNotification(Subscription subscription, String messageJson) {
        try {
            pushService.send(new Notification(subscription, messageJson));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException
                | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 30000)
    private void sendNotifications() {
        System.out.println("Sending notifications to all subscribers");

        var json = """
                  {
                  "notification": { 
                    "title": "PWA-PUSH-ANGULAR", 
                    "body": "Uma nova notificação chegou!!", 
                    "vibrate": [100, 50, 100], 
                    "data": { 
                        "dateOfArrival": "2018-08-31",
                        "primaryKey": 1 
                    }, 
                    "actions": [{ 
                        "action": "explore", 
                        "title": "Go to the site" 
                    }] 
                  }
                }
                """;

//        if(!subscriptions.isEmpty())
//            subscriptions.forEach(subscription -> {
//                sendNotification(subscription, String.format(json, LocalTime.now()));
//            });
    }
}
