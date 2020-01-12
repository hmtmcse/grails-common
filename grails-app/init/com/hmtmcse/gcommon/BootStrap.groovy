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
        println("Tomcat Information")
        println("----------------------------------------------------------------------")
        println("Server Info: ${ServerInfo.serverInfo}")
        println("Server Number: ${ServerInfo.serverNumber}")
        println("Server Built: ${ServerInfo.serverBuilt}")

        println("\n")
        println("Grails Plugin Information")
        println("----------------------------------------------------------------------")
        Holders.applicationContext.getBean("pluginManager").allPlugins.each{ plugin ->
            println(plugin.name + " " + plugin.version)
        }
    }
}
