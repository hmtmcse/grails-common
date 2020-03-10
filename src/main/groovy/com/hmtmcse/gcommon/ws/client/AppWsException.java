package com.hmtmcse.gcommon.ws.client;

/**
 * Created by Touhid Mia on 11/09/2014.
 */
public class AppWsException extends Exception {

    public AppWsException() {
        super("App Web Socket Exception");
    }

    public AppWsException(String message) {
        super(message);
    }
}
