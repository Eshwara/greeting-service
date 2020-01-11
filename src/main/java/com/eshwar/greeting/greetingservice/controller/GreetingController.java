package com.eshwar.greeting.greetingservice.controller;

import com.eshwar.greeting.greetingservice.request.Message;
import com.eshwar.greeting.greetingservice.request.MessageRequest;
import com.eshwar.greeting.greetingservice.request.MobileDetails;
import com.eshwar.greeting.greetingservice.response.GreetingResponse;
import com.eshwar.greeting.greetingservice.service.SenderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class GreetingController {

    @Autowired
    private SenderService sender;

    @PostMapping(value = "/send")
    public GreetingResponse sendMessage(@RequestBody Message message, HttpServletRequest httpServletRequest){

        String token = httpServletRequest.getHeader("token");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:9090/account/authenticate/user/"+token, String.class);

        String response = resp.getBody();
        JSONObject jsonObject = new JSONObject(response);
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessage(message.getMessage());
        String[] mobiles = message.getMobiles().split(",");
        MobileDetails[] mobileDetails = new MobileDetails[mobiles.length];
        int i =0;
        for(String m : mobiles){

             MobileDetails mobileD = new MobileDetails();
             mobileD.setMobile(m);
             mobileDetails[i++]= mobileD;
        }

        messageRequest.setMobiles(mobileDetails);
        String status = jsonObject.optString("status");
        if("ok".equals(status)){
            return sender.send(messageRequest);
        }

        GreetingResponse greetingResponse = new GreetingResponse();
        greetingResponse.setStatus("error");
        greetingResponse.setMessage("Message request failed");

        return greetingResponse;

    }

}
