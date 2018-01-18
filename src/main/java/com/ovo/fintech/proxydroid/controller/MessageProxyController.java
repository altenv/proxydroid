package com.ovo.fintech.proxydroid.controller;

import com.ovo.fintech.proxydroid.ProxydroidApplication;
import com.ovo.fintech.proxydroid.model.PayloadQueue;
import com.ovo.fintech.proxydroid.service.MessageProxyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by aenriko on 17/01/18.
 */
@RestController
@RequestMapping("/v1.0/notification")
public class MessageProxyController {

    @Autowired
    private MessageProxyService proxyService;

    @PutMapping("sms")
    public ResponseEntity smsQueue(@RequestBody Object body,HttpServletRequest request) {
        proxyService.sendQueueMessage(ProxydroidApplication.SMS_MESSAGE_QUEUE,body,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("email")
    public ResponseEntity emailQueue(@RequestBody Object body,HttpServletRequest request) {
        proxyService.sendQueueMessage(ProxydroidApplication.EMAIL_MESSAGE_QUEUE,body,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("emailWithAttachment")
    public ResponseEntity emailWithAttachmentQueue(@RequestBody Object body,HttpServletRequest request) {
        proxyService.sendQueueMessage(ProxydroidApplication.EMAIL_MESSAGE_QUEUE,body,request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
