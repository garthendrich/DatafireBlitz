package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

abstract class NetworkNode implements Runnable {
    private Thread dataManagerThread;

    private BufferedReader clientReader;
    private PrintWriter clientWriter;

    void start(Socket clientSocket) {
        try {
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error initializing input and output handlers: " + exception.getMessage());
        }

        dataManagerThread = new Thread(this);
        dataManagerThread.start();
    }

    @Override
    public void run() {
        try {
            String data = clientReader.readLine();
            while (data != null) {
                handleData(data);
                data = clientReader.readLine();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error reading data from server: " + exception.getMessage());
        }
    }

    abstract void handleData(String data);

    public void send(String data) {
        clientWriter.println(data);
    }
}
