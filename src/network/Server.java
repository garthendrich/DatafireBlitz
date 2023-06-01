package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clientHandlers = new ArrayList<ClientHandler>();

    private Thread serverThread;
    private boolean isSearchingForConnections = true;

    public Server() {
        initializeServerSocket();

        serverThread = new Thread(this);
        serverThread.start();
    }

    @Override
    public void run() {
        startConnectionSearch();
    }

    private void initializeServerSocket() {
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error creating server socket: " + exception.getMessage());
        }
    }

    public String getIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            System.out.println("Error getting IP address: " + exception.getMessage());
            return null;
        }
    }

    public int getPortNumber() {
        return serverSocket.getLocalPort();
    }

    private void startConnectionSearch() {
        while (isSearchingForConnections) {
            try {
                Socket newClientSocket = serverSocket.accept();
                ClientHandler newClientHandler = new ClientHandler(this, newClientSocket);

                clientHandlers.add(newClientHandler);
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error accepting client connection: " + exception.getMessage());
            }
        }
    }

    public void stopConnectionSearch() {
        isSearchingForConnections = false;
        serverThread.interrupt();
        shutdown();
    }

    void broadcast(String data) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler != null) {
                clientHandler.send(data);
            }
        }
    }

    public void shutdown() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException exception) {
                exception.printStackTrace();
                System.out.println("Error shutting down server socket: " + exception.getMessage());
            }
        }
    }
}
