package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.server.ClientHandler;

import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectionServiceImpl implements ConnectionService {
    @Override
    public void sendRequest() {

    }

    @Override
    public void sendResponse() {

    }

    @Override
    public void setupServer() {

        ServerSocket server = null;

        try {
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            while (true) {
                Socket s = server.accept();
                System.out.println("New client connected");
                ClientHandler clientSock = new ClientHandler(s);
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setupClient() {
        try (Socket s = new Socket("localhost", 1234)) {
            PrintWriter out = new PrintWriter(
                    s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = "nulling";
            out.println(line);
            out.flush();
            System.out.println("Server replied "+ in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
