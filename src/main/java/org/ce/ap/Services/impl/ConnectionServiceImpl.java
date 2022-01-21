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

public class ConnectionServiceImpl implements ConnectionService {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;

    @Override
    public Response sendRequest(Request request) throws IOException {
        JSONObject jo = new JSONObject(request);
        System.out.print(jo + "\n");
        out.println(jo);
        out.flush();
        String resp = in.readLine();
        System.out.println("Server replied " + resp);
        JSONObject j2 = new JSONObject(resp);
        return new Response(
                j2.getBoolean("hasError"), j2.getInt("errorCode"),
                j2.getInt("count"), j2);
    }

    @Override
    public void sendResponse(Response response) {

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
        try {
            s = new Socket("localhost", 1234);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
 /*
            String line = "nulling";
            User u = new User(1,"a","b","asd","", LocalDateTime.now(),LocalDateTime.now());
            JSONObject jo = new JSONObject(u);
            System.out.print(jo+"\n");
            out.println(jo);
            out.flush();
            System.out.println("Server replied "+ in.readLine());
            */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
