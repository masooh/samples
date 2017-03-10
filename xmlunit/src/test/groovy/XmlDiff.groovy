import org.xmlunit.builder.DiffBuilder
import org.xmlunit.builder.Input
import org.xmlunit.diff.Diff

import spock.lang.Specification

class XmlDiff extends Specification {

    def "compare different XMLs"() {
        given:
        String xmlA = "<root>a</root>"
        String xmlB = "<root>b</root>"

        when:
        Diff diff = DiffBuilder.compare(Input.fromString(xmlA).build())
                               .withTest(Input.fromString(xmlB).build())
                    .build()

        then:
        diff.hasDifferences()
    }

    def "compare only whitespace varying XMLs"() {
        given:
        String multiline = '''<root>
                                a
                              </root>'''
        String singleline = "<root>a</root>"

        when:
        Diff diff = DiffBuilder.compare(Input.fromString(multiline).build())
                               .withTest(Input.fromString(singleline).build())
                    .ignoreWhitespace()
                    .build()

        then:
        !diff.hasDifferences()
    }


}
