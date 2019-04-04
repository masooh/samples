package echoserver

class EchoServer {
    void start(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber)
        Socket clientSocket = serverSocket.accept()
        PrintWriter out = new PrintWriter(clientSocket.outputStream, true)
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.inputStream))

        serverSocket.withCloseable {
            try {
                String inputLine
                while ((inputLine = input.readLine()) != null) {
                    out.println(inputLine)
                }
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port "
                                       + portNumber + " or listening for a connection")
                System.out.println(e.message)
            }
        }
    }
}
