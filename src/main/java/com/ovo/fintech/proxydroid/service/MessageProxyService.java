package com.ovo.fintech.proxydroid.service;

import com.ovo.fintech.proxydroid.model.PayloadQueue;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface MessageProxyService {
    void sendQueueMessage(String routingkey,Object body,HttpServletRequest request);
}
