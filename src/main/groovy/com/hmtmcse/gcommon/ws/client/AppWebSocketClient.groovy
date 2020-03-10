package com.hmtmcse.gcommon.ws.client

import javax.websocket.CloseReason
import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.ClientEndpoint
import javax.websocket.ContainerProvider
import javax.websocket.WebSocketContainer

@ClientEndpoint
class AppWebSocketClient {

    private Session wsSession
    private Object waitLock = new Object();
    private WsMessage wsMessage
    private String wsUrl
    public static interface WsMessage {
        public void update(String message, AppWebSocketClient appWebSocketClient);
        public void close();
    }

    private AppWebSocketClient(String wsUrl, WsMessage wsMessage) {
        this.wsUrl = wsUrl
        this.wsMessage = wsMessage
    }

    @OnOpen
    public void onOpen(Session userSession) {
        this.wsSession = userSession;
    }


    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.wsSession = null;
        if (wsMessage) {
            wsMessage.close()
        }
    }

    @OnMessage
    public void onMessage(String message) {
        if (wsMessage) {
            wsMessage.update(message, this)
        }
    }

    private void initConnection(URI endpointURI) throws AppWsException {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new AppWsException(e.getMessage())
        }
    }

    private void sendMessage(String message) {
        if (wsSession) {
            wsSession.getAsyncRemote().sendText(message);
        }
    }

    public void close() {
        if (wsSession) {
            wsSession.close()
        }
        waitLock.notifyAll()
    }

    public static AppWebSocketClient instance(String wsUrl, WsMessage wsMessage) throws AppWsException {
        if (!wsUrl || !wsMessage) {
            throw new AppWsException("Invalid Input");
        }
        return new AppWebSocketClient(wsUrl, wsMessage)
    }

    private void waitUntilFinish() {
        synchronized (waitLock) {
            try {
                waitLock.wait();
            } catch (InterruptedException e) {
            }
        }
    }

    public void start() throws AppWsException {
        Thread.start {
            initConnection(new URI(wsUrl))
        }
        waitUntilFinish()
    }

}
