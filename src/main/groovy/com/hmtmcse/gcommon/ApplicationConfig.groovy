package com.hmtmcse.gcommon

import grails.util.Holders

class ApplicationConfig {


    static getConfiguration(String configKey, String key, Object defaultValue = null) {
        if (Holders.config[configKey][key]) {
            return Holders.config[configKey][key]
        }
        return defaultValue
    }


    static getTMAppConfig(String key, Object defaultValue = null) {
        return getConfiguration("tmApp", key, defaultValue)
    }

    static appBaseUrl(){
        return getTMAppConfig("baseURL", "http://localhost:6601/")
    }

    static getGrailsConfig(String key, Object defaultValue = null) {
        return getConfiguration("grails", key, defaultValue)
    }

}
