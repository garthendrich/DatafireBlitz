package network;

import java.net.Socket;

class GameClientHandler extends NetworkDataManager {
    GameServer server;

    GameClientHandler(GameServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;

        start();
    }

    @Override
    void handleData(String data) {
        server.broadcast(data);
    }
}
