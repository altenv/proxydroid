package com.ovo.fintech.proxydroid.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.ovo.fintech.proxydroid.util.IdUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class PreFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        Date createdDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        String timeStamp = dateFormat.format(createdDate);

        ctx.addZuulRequestHeader("x-transaction-id", IdUtil.generateTransactionId());
        ctx.addZuulRequestHeader("x-request-id",IdUtil.generateRequestId());
        ctx.addZuulRequestHeader("content-type","application/json");
        ctx.addZuulRequestHeader("type","notification");
        ctx.addZuulRequestHeader("created-at",timeStamp);
        ctx.addZuulRequestHeader("source","protocoldroid-proxy");
        ctx.addZuulRequestHeader("priority","0");
        ctx.addZuulRequestHeader("version","1.0");

        log.info(String.format("header:%s path:%s %s", ctx.getZuulRequestHeaders().toString(),request.getMethod(),request.getRequestURL().toString()));

        return null;
    }
}