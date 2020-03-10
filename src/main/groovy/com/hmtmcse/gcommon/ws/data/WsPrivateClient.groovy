package com.hmtmcse.gcommon.ws.data

import javax.websocket.Session

class WsPrivateClient {
    public Session session
    public String identity

    WsPrivateClient() {}

    WsPrivateClient(Session session, String identity) {
        this.session = session
        this.identity = identity
    }
}
