package com.hmtmcse.gcommon

import org.junit.jupiter.api.Test
import static org.junit.Assert.assertEquals

class TMGUtilTest {

    @Test
    public void testHumanReadable() {
        [
                [
                        "input": "CamelCase_With_underscore",
                        "result": "Camel case with underscore",
                ],
                [
                        "input": "_newText-happen",
                        "result": "New text happen",
                ],
                [
                        "input": "ThisOnlyCamelCase",
                        "result": "This only camel case",
                ]
        ].each { Map map ->
            String result = TMGUtil.makeHumReadable(map.input)
            assertEquals(map.result, result)
        }

    }
}
