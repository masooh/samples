package echoserver

import spock.lang.Shared
import spock.lang.Specification


    class EchoClientTest extends Specification {

        @Shared
        Thread echoServerThread

        void setupSpec() {
            echoServerThread = Thread.start {
                EchoServer server = new EchoServer()
                server.start(4444)
            }
        }

        void cleanupSpec() {
            echoServerThread.stop()
        }

        def "Server should echo message from Client"() {
            when:
            EchoClient client = new EchoClient()
            client.startConnection("localhost", 4444)

            then:
            client.sendMessage("echo") == "echo"
        }
    }