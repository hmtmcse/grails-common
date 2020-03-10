package com.hmtmcse.gcommon.ws

import com.hmtmcse.gcommon.ws.data.WsMessage
import com.hmtmcse.gcommon.ws.data.WsPrivateClient
import com.hmtmcse.parser4java.JsonProcessor
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import javax.servlet.ServletContext
import javax.servlet.annotation.WebListener
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerContainer
import javax.websocket.server.ServerEndpoint

@WebListener
@ServerEndpoint(value = "/grails-common/websocket/{identity}", configurator = AppWebSocketConfigurator.class)
class AppWebSocketHandler {
    static final Set<Session> clients = ([] as Set).asSynchronized()
    static final Set<WsPrivateClient> privateClients = ([] as Set).asSynchronized()
    private static TaskScheduler clientRemoveScheduler = new ConcurrentTaskScheduler()
    private static Boolean isInit = false

    @OnOpen
    public void handleOpen(Session userSession, @PathParam("identity") String identity) {
        if (identity) {
            privateClients.add(new WsPrivateClient(userSession, identity))
        } else {
            clients.add(userSession)
        }
    }

    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException {
        if (message) {
            println("SENDING TO ${clients.findAll { it.isOpen() }.size()}/${clients.size()} CLIENTS")
            message = "${userSession?.id}: ${message}".toString()
//            clients.findAll { it.isOpen() }.each { it.basicRemote.sendText(message) }
            sendMessage(message)
        }
    }

    public static sendMessage(String message) {
        sendMessage(new WsMessage(message))
    }

    public static sendMessage(WsMessage message, String identity = "all-message") {
        if (identity) {
            String json = "{}"
            try {
                JsonProcessor jsonProcessor = new JsonProcessor()
                json = jsonProcessor.klassToString(message)
            } catch (Exception i) {
                println(i.getMessage())
            }
            privateClients.findAll {
                it.identity && it.identity.equals(identity) && it.session.isOpen()
            }.each { it.session.basicRemote.sendText(json) }
        } else {
            clients.findAll { it.isOpen() }.each { it.basicRemote.sendText(message) }
        }
    }


    @OnClose
    public void handeClose(Session userSession) throws SocketException {
        println "ONE CONNECTION CLOSED"
    }


    @OnError
    public void handleError(Throwable throwable) {
        println("HANDLE ERROR")
    }


    private static void initWebSocket(ServletContext servletContext) {
        final ServerContainer serverContainer = servletContext.getAttribute("javax.websocket.server.ServerContainer")
        serverContainer.addEndpoint(AppWebSocketHandler)
        serverContainer.defaultMaxSessionIdleTimeout = 0
        isInit = true
    }


    static void init(final ServletContext servletContext) {
        try {
            clientRemoveScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (!isInit) {
                            println("WebSocket Not Init...")
                            initWebSocket(servletContext)
                        }
                        clients.removeAll { !it.isOpen() }
                        privateClients.removeAll { !it.session.isOpen() }
                    }
                    catch (Exception ex) {
                        System.err.println("WebSocket Initilization Exception: ${ex.getMessage()}")
                    }
                }
            }, 1000L * 10)
        } catch (Exception e) {
            System.err.println("WebSocket Initilization Exception: ${e.getMessage()}")
        }
    }
}