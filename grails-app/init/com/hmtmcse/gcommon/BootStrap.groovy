package com.hmtmcse.gcommon

import com.hmtmcse.gcommon.ws.WebSocketHandler
import grails.util.Holders
import org.apache.catalina.util.ServerInfo

class BootStrap {



    def init = { servletContext ->
       // WebSocketHandler.init(servletContext)
        infoPrinter()
    }

    def destroy = {
    }

    public infoPrinter() {
        println("\n")
        println("Tomcat Information")
        println("----------------------------------------------------------------------")
        println("Server Info: ${ServerInfo.serverInfo}")
        println("Server Number: ${ServerInfo.serverNumber}")
        println("Server Built: ${ServerInfo.serverBuilt}")

        println("\n")
        println("Grails Plugin Information")
        println("----------------------------------------------------------------------")
        Integer i = 1
        Holders.applicationContext.getBean("pluginManager").allPlugins.each{ plugin ->
            println(i + ". " + plugin.name + " " + plugin.version)
            i++
        }
        println("\n")
    }
}
