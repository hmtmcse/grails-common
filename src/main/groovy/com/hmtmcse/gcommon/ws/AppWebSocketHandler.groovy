package com.hmtmcse.gcommon.ws

import grails.util.Holders
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler

import javax.servlet.ServletContext
import javax.servlet.annotation.WebListener
import javax.websocket.*
import javax.websocket.server.ServerContainer
import javax.websocket.server.ServerEndpoint

@WebListener
@ServerEndpoint(value = "/grails-common/websocket", configurator = AppWebSocketConfigurator.class)
class AppWebSocketHandler {
    static final Set<Session> clients = ([] as Set).asSynchronized()
    private static TaskScheduler clientRemoveScheduler = new ConcurrentTaskScheduler()
    private static Boolean isInit = false

    @OnOpen
    public void handleOpen(Session userSession, EndpointConfig endpointConfig) {
        clients.add(userSession)
        println "WE HAVE OPEN SESSION #${userSession?.id} " + "SESSION ${userSession.userProperties.session?.id}"
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

    public static sendMessage(String message){
        clients.findAll { it.isOpen() }.each { it.basicRemote.sendText(message) }
    }


    @OnClose
    public void handeClose(Session userSession) throws SocketException {
        println "ONE CONNECTION CLOSED"
    }


    @OnError
    public void handleError(Throwable throwable) {
        println("HANDLE ERROR")
        throwable.printStackTrace()
    }

   private static void initWebSocket(ServletContext servletContext){
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