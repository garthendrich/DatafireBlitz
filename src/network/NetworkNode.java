package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import network.datatypes.Data;

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
        while (true) {
            Data data = awaitData();

            if (data == null) {
                break;
            }

            handleData(data);
        }
    }

    Data awaitData() {
        try {
            String serializedData = clientReader.readLine();
            return Data.fromSerialized(serializedData);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error reading serialized data from stream: " + exception.getMessage());
            return null;
        }
    }

    abstract void handleData(Data data);

    public void send(Data data) {
        clientWriter.println(data.getSerialized());
    }
}
