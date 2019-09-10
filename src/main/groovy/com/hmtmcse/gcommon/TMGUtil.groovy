package com.hmtmcse.gcommon

import grails.util.Holders
import org.grails.web.util.WebUtils

class TMGUtil {

    public static String makeHumReadable(String text) {
        String underscoreToSpace = text.replaceAll("(_+)([A-Za-z0-9_])", {
            Object[] it -> " " + it[2]?.trim()
        })

        String camelCaseToSpace =  underscoreToSpace.replaceAll("(\\s*[A-Z])", {
            Object[] it -> " " + it[0]?.trim()
        })

        String hyphenToSpace = camelCaseToSpace.replaceAll("\\s*[\\-_]*\\s*", {
            Object[] it -> it[0].equals("")?"":" "
        })

        if (hyphenToSpace){
            return hyphenToSpace.trim().toLowerCase().capitalize()
        }
        return text
    }

    public static String toCamelCase( String text, boolean capitalized = false ) {
        text = text.replaceAll( "(_)([A-Za-z0-9])", { Object[] it -> it[2].toUpperCase() } )
        return capitalized ? text?.capitalize() : text
    }


    public static String toSnakeCase( String text ) {
        text.replaceAll( /([A-Z])/, /_$1/ ).toLowerCase().replaceAll( /^_/, '' )
    }

    static uuid(){
        return UUID.randomUUID().toString().toUpperCase()
    }

    static getAppSession() {
        return WebUtils.retrieveGrailsWebRequest().session
    }

    static invalidateSession() {
        return appSession.invalidate()
    }

    static appBaseUrlHostWithPort(){
        String urlString = ApplicationConfig.appBaseUrl()
        URL url = new URL(urlString)
        String port = ""
        if (url.getPort() > 0){
            port = ":${url.getPort()}"
        }
        return "${url.getHost()}${port}"
    }


    static def getBean(String beanIdentifier) {
        try {
            return Holders.grailsApplication.mainContext.getBean(beanIdentifier)
        } catch (Exception e) {
            return null
        }
    }
}
