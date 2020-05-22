package br.com.leorocha.meudoglindo.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.leorocha.meudoglindo.dto.SubscriptionDTO;
import br.com.leorocha.meudoglindo.service.RestService;


@RestController
@RequestMapping(path="/web-push")
public class WebPushController {
	@Autowired
	private RestService restService;
	
	List<String> inscricoes = new ArrayList<String>();
	
	public final static String AUTH_KEY_FCM ="BLWLn7BOjy11xaWnt9zAUN40I5Xfs7RuReEb3WeoEF9IQqu07Z0uk3XfGNFPT-AUUGWbcwbFdPjaa-B8ro_nPK8";

	public final static String API_URL_FCM ="https://fcm.googleapis.com/fcm/send";

	         // userDeviceIdKey is the device id you will query from your database

    public void pushFCMNotification(String userDeviceIdKey, String title, String message, String endpoint) throws Exception{

	    String authKey = AUTH_KEY_FCM;   // You FCM AUTH key
	    String FMCurl = endpoint;     

	    URL url = new URL(FMCurl);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	    conn.setUseCaches(false);
	    conn.setDoInput(true);
	    conn.setDoOutput(true);

	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Authorization","key="+authKey);
	    conn.setRequestProperty("Content-Type","application/json");

	    JsonObject json = new JsonObject();
	    json.addProperty("to",userDeviceIdKey.trim());
	    JsonObject info = new JsonObject();
	    info.addProperty("title", title); // Notification title
	    info.addProperty("body", message); // Notification body
	    info.addProperty("image", "https://lh6.googleusercontent.com/-sYITU_cFMVg/AAAAAAAAAAI/AAAAAAAAABM/JmQNdKRPSBg/photo.jpg");
	    info.addProperty("type", "message");
	    json.add("data", info);
	    System.out.println(json.toString());

	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(json.toString());
	    wr.flush();
	    conn.getInputStream();
    }
	
	@GetMapping
	public List<String> todasInscricoes() {
		return this.inscricoes;
	}

	@PutMapping
	public void enviarMsg() throws Exception {
		Gson gson = new Gson();
		String obj = "{\"endpoint\":\"https://fcm.googleapis.com/fcm/send/cIssh0m2Wu4:APA91bHR414gHDwyjcozFBdqGvTkSaF7frHt40OFK1pcOX7iCi1opt3o-PLsP3Ib3jti1M9JLZivk-DKy7oTR5Uq8K7eIUryeKCIIe5wDvMZbnKzJ7Umf24arZ_eSDSVQ90dWOsBRXGt\",\"keys\":{\"auth\":\"ppgF5eqszxMkvHpi2gu4Rw\",\"p256dh\":\"BCiwJDw3VQ57iOQ6A5SR2q4v4t4Ee1iQoUMl-AWiR4F8dJBWO2snWspvG9gKOZ1zluhrr3rbvPoe21DNwy-_5AE\"}}";
		SubscriptionDTO sub = gson.fromJson(obj, SubscriptionDTO.class);
		System.out.println(sub);
		pushFCMNotification(sub.getKeys().getAuth(), "Olá", "Hello", sub.getEndpoint());
	}
	
	@PostMapping
	public void inscrever(@RequestBody SubscriptionDTO inscricao) {
		Gson gson = new Gson();
		String dtoJson = gson.toJson(inscricao);
		if(!this.inscricoes.contains(dtoJson)) {
			this.inscricoes.add(dtoJson);
			System.out.println(inscricao.toString());
			
		}
		
	}
	
}
