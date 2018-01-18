package com.ovo.fintech.proxydroid.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by aenriko on 18/01/17.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageQueue {
    private String method;
    private String url;
    private Object header;
    private Object body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getHeader() {
        return header;
    }

    public void setHeader(Object header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
