package com.hmtmcse.gcommon

import org.junit.jupiter.api.Test
import static org.junit.Assert.assertEquals

class TMGUtilTest {

    @Test
    public void testHumanReadable() {
        [
                [
                        "input" : "this__Only-----Camel-_Case-_-",
                        "result": "This only camel case",
                ],
                [
                        "input" : "This only camel case",
                        "result": "This only camel case",
                ],
                [
                        "input" : "This Only Camel       Case",
                        "result": "This only camel case",
                ],
                [
                        "input" : "- newText - happen",
                        "result": "New text happen",
                ],
                [
                        "input" : "CamelCase_With_underscore",
                        "result": "Camel case with underscore",
                ],
                [
                        "input" : "_newText-happen",
                        "result": "New text happen",
                ],
                [
                        "input" : "-_newText-happen",
                        "result": "New text happen",
                ],
                [
                        "input" : "-newText-happen",
                        "result": "New text happen",
                ],
                [
                        "input" : "ThisOnlyCamelCase",
                        "result": "This only camel case",
                ],
                [
                        "input" : "this only-Camel_Case",
                        "result": "This only camel case",
                ]
        ].each { Map map ->
            String result = TMGUtil.makeHumReadable(map.input)
            assertEquals(map.result, result)
        }

    }
}
