package com.eshwar.greeting.greetingservice.service;

import com.eshwar.greeting.greetingservice.request.MessageRequest;
import com.eshwar.greeting.greetingservice.request.MobileDetails;
import com.eshwar.greeting.greetingservice.response.GreetingResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Service
public class SenderService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public GreetingResponse send(MessageRequest message) {
        System.out.println("sending payload='{}'"+ message);
        GreetingResponse greetingResponse = new GreetingResponse();
        MobileDetails[] mobilesArr =  message.getMobiles();

        for(MobileDetails mobileDetails : mobilesArr){
            mobileDetails.setId(UUID.randomUUID().toString());
        }

        ObjectMapper mapper = new ObjectMapper();

        String messageStrig = null;

        try {
            messageStrig= mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("messageString: " + messageStrig);

        ListenableFuture<SendResult<String, String>>  future = kafkaTemplate.send("test", messageStrig);
        System.out.println("res.isDone() : " + future.isDone());


        /*future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("sent message='{}' with offset={}"+ message+" ,"+
                        result.getRecordMetadata().offset());

                greetingResponse.setStatus("ok");
                greetingResponse.setMessage("Message Request queued");
                int i=0;
                String[] temp = new String[mobilesArr.length];
                for(MobileDetails mobileDetails : mobilesArr){

                    temp[i++]= mobileDetails.getId();
                }

                greetingResponse.setIds(temp);

               // System.arraycopy(mobilesArr,0, greetingResponse.getIds(),0, mobilesArr.length);
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("unable to send message='{}'"+ message + " "+  ex);

                greetingResponse.setStatus("error");
                greetingResponse.setMessage("Message queue request failed");
            }
        });
*/

        // kafkaTemplate.send("test", payload);


        greetingResponse.setStatus("ok");
        greetingResponse.setMessage("Message Request queued");
        int i=0;
        String[] temp = new String[mobilesArr.length];
        for(MobileDetails mobileDetails : mobilesArr){

            temp[i++]= mobileDetails.getId();
        }

        greetingResponse.setIds(temp);


        return greetingResponse;
    }
}
