package com.hmtmcse.gcommon.ws.data

class WsMessage {

    public String from
    public String to
    public String message
    public Object jsonData
    public String status

    WsMessage() {}

    WsMessage(String message) {
        this.message = message
    }

    WsMessage setFrom(String from) {
        this.from = from
        return this;
    }

    WsMessage setTo(String to) {
        this.to = to
        return this;
    }

    WsMessage setMessage(String message) {
        this.message = message
        return this;
    }

    WsMessage setJsonData(Object jsonData) {
        this.jsonData = jsonData
        return this;
    }

    WsMessage setStatus(String status) {
        this.status = status
        return this;
    }
}
