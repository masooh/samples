package echoserver

class EchoClient {
    private Socket echoSocket
    private PrintWriter out
    private BufferedReader input

    void startConnection(String hostName, int portNumber) throws IOException {
        try {
            echoSocket = new Socket(hostName, portNumber)
            out = new PrintWriter(echoSocket.outputStream, true)
            input = new BufferedReader(new InputStreamReader(echoSocket.inputStream))
        } catch (UnknownHostException e) {
            System.err.printf("Don't know about host %s%n", hostName)
            System.exit(1)
        } catch (IOException e) {
            System.err.printf("Couldn't get I/O for the connection to %s%n", hostName)
            System.exit(1)
        }
    }

    String sendMessage(String msg) throws IOException {
        out.println(msg)
        return input.readLine()
    }
}
