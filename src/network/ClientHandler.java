package network;

import java.net.Socket;

class ClientHandler extends NetworkNode {
    Server server;

    ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;

        start();
    }

    @Override
    void handleData(String data) {
        server.broadcast(data);
    }
}
