package com.hmtmcse.gcommon.ws

import javax.servlet.http.HttpSession
import javax.websocket.HandshakeResponse
import javax.websocket.server.HandshakeRequest
import javax.websocket.server.ServerEndpointConfig

class WebSocketConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(config, request, response)
//        config.getUserProperties().put("session", request.httpSession);
    }

}