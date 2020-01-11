package com.eshwar.greeting.greetingservice.request;

import java.util.Arrays;

public class MessageRequest {
    private String message;
    private MobileDetails[] mobiles;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MobileDetails[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(MobileDetails[] mobiles) {
        this.mobiles = mobiles;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "message='" + message + '\'' +
                ", mobiles=" + Arrays.toString(mobiles) +
                '}';
    }
}
