package com.ovo.fintech.proxydroid.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by aenriko on 18/01/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayloadQueue {
    private String type;
    private MessageQueue message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageQueue getMessage() {
        return message;
    }

    public void setMessage(MessageQueue message) {
        this.message = message;
    }
}
