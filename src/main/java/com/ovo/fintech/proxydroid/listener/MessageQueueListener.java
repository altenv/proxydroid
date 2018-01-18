package com.ovo.fintech.proxydroid.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * This is the queue listener class, its receiveMessage() method ios invoked with the
 * message as the parameter.
 */
@Component
public class MessageQueueListener {

    private static final Logger log = LogManager.getLogger(MessageQueueListener.class);

    public MessageQueueListener() {

    }

    /**
     * This method is invoked whenever any new message is put in the queue.
     * @param json
     */
    public void receiveMessage(Object json) {
        log.info("Received <" + json.toString() + ">");
        log.info("Message processed...");
    }
}
