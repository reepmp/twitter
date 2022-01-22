package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Request;
import main.java.org.ce.ap.Models.Response;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.server.ClientHandler;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

/**
 * service to handle connection between client and server
 */
public class ConnectionServiceImpl implements ConnectionService {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;

    /**
     *
     * @param request sends the given request to the server
     * @return returns the response from server
     * @throws IOException in case Json deserialization finds an error
     */
    @Override
    public Response sendRequest(Request request) throws IOException {
        JSONObject jo = new JSONObject(request);
        out.println(jo);
        out.flush();
        String resp = in.readLine();
        JSONObject j2 = new JSONObject(resp);
        return new Response(
                j2.getBoolean("hasError"), j2.getInt("errorCode"),
                j2.getInt("count"), j2);
    }

    @Override
    public void sendResponse(Response response) {

    }

    /**
     * this method will initiate the server side of connection
     */
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


    /**
     * this method will initiate client side of connection
     */
    @Override
    public void setupClient() {
        try {
            s = new Socket("localhost", 1234);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
