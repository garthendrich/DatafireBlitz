package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

abstract class NetworkNode implements Runnable {
    protected Thread dataManagerThread;

    protected Socket clientSocket;
    private BufferedReader clientReader;
    protected PrintWriter clientWriter;

    NetworkNode() {
        dataManagerThread = new Thread(this);
    }

    /**
     * This method is important because the subclasses of this class has to finish
     * initializing clientSocket before the thread can start
     */
    void start() {
        dataManagerThread.start();
    }

    @Override
    public void run() {
        initializeCharacterStreamHandlers();
        startReceivingData();
    }

    private void initializeCharacterStreamHandlers() {
        try {
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.out.println("Error initializing input and output handlers: " + exception.getMessage());
        }
    }

    private void startReceivingData() {
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
