package com.hmtmcse.gcommon

trait DomainTask {

    def beforeValidate () {
        if (this.hasProperty("uuid") && this.uuid == null){
            this.uuid = TMGUtil.uuid()
        }
    }
}
