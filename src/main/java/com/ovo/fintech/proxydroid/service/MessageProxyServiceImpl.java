package com.ovo.fintech.proxydroid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ovo.fintech.proxydroid.ProxydroidApplication;
import com.ovo.fintech.proxydroid.model.MessageQueue;
import com.ovo.fintech.proxydroid.model.PayloadQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import com.ovo.fintech.proxydroid.util.IdUtil;
import ovo.payment.common.json.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageProxyServiceImpl implements MessageProxyService{
    private static final Logger log = LoggerFactory.getLogger(MessageProxyServiceImpl.class);
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProxyServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private String createQueueMessage(Object body,HttpServletRequest request){

        PayloadQueue payload = new PayloadQueue();
        MessageQueue message = new MessageQueue();
        payload.setType("http");
        message.setUrl(String.valueOf(request.getRequestURL()));
        message.setMethod(request.getMethod());
        message.setHeader(getHeadersInfo(request));
        message.setBody(body);
        payload.setMessage(message);
        return JsonUtil.encode(JsonUtil.getMapper(),payload);
    }

    public void sendQueueMessage(String routingKey,Object body,HttpServletRequest request) {
        String randomId=IdUtil.generateTransactionId();
        log.info(String.format("send id:%s route:%s path:%s %s body:%s", randomId,routingKey,request.getMethod(),request.getRequestURL(),body.toString()));
        Date createdDate = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        //String timeStamp = dateFormat.format(createdDate);

        SimpleMessageConverter aqmpMessage = new SimpleMessageConverter();
        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT).build();

        properties.setHeader("x-transaction-id", randomId);
        properties.setHeader("x-request-id",randomId);
        properties.setHeader("content-type","application/json");
        properties.setHeader("type","notification");
        properties.setHeader("created-at",createdDate);
        properties.setHeader("source","protocoldroid-proxy");
        properties.setHeader("priority","0");
        properties.setHeader("version","1.0");

        log.info(String.format("send id:%s properties:%s ", randomId,properties.getHeaders().toString()));

        rabbitTemplate.convertAndSend(routingKey,
                aqmpMessage.toMessage(createQueueMessage(body,request),properties));
    }
}
