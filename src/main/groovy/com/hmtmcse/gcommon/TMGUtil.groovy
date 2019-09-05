package com.hmtmcse.gcommon

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

}
