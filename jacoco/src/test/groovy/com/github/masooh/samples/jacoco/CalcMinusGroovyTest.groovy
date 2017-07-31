package com.github.masooh.samples.jacoco

import spock.lang.Specification

class CalcMinusGroovyTest extends Specification {

    def minus() {
        given:
        Calc calc = new Calc()

        expect:
        calc.minus(5,3) == 2
    }
}
