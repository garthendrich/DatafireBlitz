package network;

import java.net.Socket;

class ClientHandler extends NetworkNode {
    private Server server;

    ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        start(clientSocket);
    }

    @Override
    void handleData(String data) {
        server.broadcast(data);
    }
}
