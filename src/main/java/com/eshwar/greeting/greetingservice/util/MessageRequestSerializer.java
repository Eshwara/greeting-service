package com.eshwar.greeting.greetingservice.util;

import com.eshwar.greeting.greetingservice.request.MessageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

public class MessageRequestSerializer implements Serializer<MessageRequest> {

    @Override
    public byte[] serialize(String s, MessageRequest messageRequest) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(s).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
}
