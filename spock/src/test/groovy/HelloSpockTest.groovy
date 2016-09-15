import spock.lang.Specification
import spock.lang.Unroll

class HelloSpockTest extends Specification {

    @Unroll
    def "length of Spock's and his friends' names: #name"() {
        expect:
        name.size() == length

        where:
        name     | length
        'Spock'  | 5
        'Kirk'   | 4
        'Scotty' | 6
    }

}