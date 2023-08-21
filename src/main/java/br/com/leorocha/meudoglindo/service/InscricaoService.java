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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class InscricaoService {
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
    @Value("${app.url}")
    private String appUrl;
    @Value("${api.url}")
    private String apiUrl;

    private PushService pushService;
    private List<Subscription> subscriptions = new ArrayList<>(); // remover depois de implementar o unscribe

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

    }

    public void unsubscribe(Subscription subscription) {
        Gson gson = new Gson();
        String sub = gson.toJson(subscription);
        Inscricao inscricao = inscricaoRepository.findByInscricao(sub);
        inscricao.setAtivo(false);
        inscricao.setDataCancelamento(LocalDateTime.now());
        inscricaoRepository.save(inscricao);
    }

    private void sendNotification(Subscription subscription, String messageJson) {
        try {
            pushService.send(new Notification(subscription, messageJson));
        } catch (GeneralSecurityException | IOException | JoseException | ExecutionException
                 | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sendTestNotifications() {
        List<Subscription> subs = new ArrayList<>();

        Gson gson = new Gson();
        this.inscricaoRepository.findAll().forEach(i -> {
            Subscription sub = gson.fromJson(i.getInscricao(), Subscription.class);
            subs.add(sub);
        });

        var json = """
                  {
                  "notification": { 
                    "title": "MEU PET LINDO", 
                    "body": "Não se esqueça de abrir o APP!", 
                    "vibrate": [100, 50, 100], 
                    "data": { 
                        "dateOfArrival": "2023-08-20",
                        "primaryKey": 15 
                    }, 
                    "actions": [{ 
                        "action": "explore", 
                        "title": "Go to the site" 
                    }] 
                  }
                }
                """;

        if (!subs.isEmpty())
            subs.forEach(subscription -> {
                sendNotification(subscription, String.format(json, subscription));
            });
    }

    public void prepareSendNotification(String titulo, String mensagem, String acao, String botaoAcao, Integer idAnimal, String chaveArquivo, Inscricao inscricao) {
        Gson gson = new Gson();
        Subscription subscription = gson.fromJson(inscricao.getInscricao(), Subscription.class);

        String img = apiUrl + "arquivo/"+ chaveArquivo + ".jpg";
        String icon = appUrl + "assets/icons/icon-512x512.png";
        String badge = appUrl + "assets/icons/icon.svg";


        String json = String.format("""
                  {
                  "notification": { 
                    "title": "%s", 
                    "body": "%s", 
                    "vibrate": [100, 50, 100], 
                    "image": "%s",
                    "icon": "%s",
                    "badge": "%s",
                    "data": { 
                        "dateOfArrival": "%s",
                        "primaryKey": 15 ,
                        "idAnimal": %s
                    }, 
                    "actions": [{ 
                        "action": "%s", 
                        "title": "%s" 
                    }] 
                  }
                }
                """, titulo, mensagem, img, icon,badge, LocalDate.now(), idAnimal, acao, botaoAcao);

        sendNotification(subscription, json);
    }

    public List<Inscricao> listByUsuarioId(Integer idUsuario) {
        return this.inscricaoRepository.findByUsuarioId(idUsuario);
    }
}
