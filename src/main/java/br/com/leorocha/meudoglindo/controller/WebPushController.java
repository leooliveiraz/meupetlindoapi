package br.com.leorocha.meudoglindo.controller;

import br.com.leorocha.meudoglindo.service.InscricaoService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/web-push")
public class WebPushController {

    @Autowired
    private InscricaoService service;

    @PostMapping
    public void subscribe(@RequestBody Subscription subscription){
        service.subscribe(subscription);
    }

    @PostMapping("/unscribe")
    public void unscribe(@RequestBody Subscription subscription){
        this.service.unsubscribe(subscription);
    }
}
