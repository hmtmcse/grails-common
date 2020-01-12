package com.hmtmcse.gcommon


import grails.plugins.Plugin


class GrailsCommonGrailsPlugin extends Plugin {
    def grailsVersion = "3.3.4 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]
    def version = "1.0.0"
    def title = "Plugin"
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    def profiles = ['web']
    def documentation = "http://grails.org/plugin/plugin"
}
