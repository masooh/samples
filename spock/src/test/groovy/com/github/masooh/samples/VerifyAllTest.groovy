package com.github.masooh.samples

import spock.lang.Specification

class VerifyAllTest extends Specification {

    def "verify all with map"() {
        given:
        URI anything = new URI("http://anything.com")

        Map map = [
            one : 1,
            'with-hyphen': 2,
            (new URI("http://google.de")) : anything
        ]

        expect:
        verifyAll(map) {
            size() == 3
            one == 1
            delegate.'with-hyphen' == 2
            get('with-hyphen') == 2
            delegate[new URI('http://google.de')] == anything
        }
    }

}