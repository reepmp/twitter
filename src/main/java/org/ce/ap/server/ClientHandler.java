package main.java.org.ce.ap.server;

import main.java.org.ce.ap.Models.GlobalParameters;
import main.java.org.ce.ap.Models.Response;
import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;
import org.json.*;

import java.io.*;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;

    // Constructor
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("client:" + line);
                JSONObject jo = new JSONObject(line);
                String command = jo.getString("method");
                switch (command) {
                    case "login" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        String password = params.getString(1);
                        boolean res = GlobalParameters.authenticationService.Login(username, password);
                        Response response;
                        if (res) {
                            response = new Response(false, 0, 1, true);
                        } else {
                            response = new Response(true, 98, 0, false);
                        }
                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    case "signup" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        String password = params.getString(1);
                        boolean res = GlobalParameters.sqlService.Signup(username, password);
                        Response response;
                        if (res) {
                            response = new Response(false, 0, 1, true);
                        } else {
                            response = new Response(true, 228, 0, false);
                        }
                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    case "tweet" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        String text = params.getString(1);
                        int repliedTo = Integer.parseInt(params.getString(2));
                        Tweet tweet = new Tweet(username, text, repliedTo);
                        tweet = GlobalParameters.sqlService.newTweet(tweet);
                        Response response;
                        if (tweet != null) {
                            response = new Response(false, 0, 1, tweet);
                        } else {
                            response = new Response(true, 98, 0, false);
                        }

                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    case "like" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        int tweetId = Integer.parseInt(params.getString(1));
                        boolean res = GlobalParameters.sqlService.Like(username, tweetId);
                        Response response;
                        if (res) {
                            response = new Response(false, 0, 1, true);
                        } else {
                            response = new Response(true, 18, 0, false);
                        }

                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    case "follow" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        String target = params.getString(1);
                        boolean res = GlobalParameters.sqlService.Follow(new User(username), target);
                        Response response;
                        if (res) {
                            response = new Response(false, 0, 1, true);
                        } else {
                            response = new Response(true, 18, 0, false);
                        }

                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    case "unfollow" -> {
                        JSONArray params = jo.getJSONArray("parameterValues");
                        String username = params.getString(0);
                        String target = params.getString(1);
                        boolean res = GlobalParameters.sqlService.UnFollow(new User(username), target);
                        Response response;
                        if (res) {
                            response = new Response(false, 0, 1, true);
                        } else {
                            response = new Response(true, 18, 0, false);
                        }

                        JSONObject jj = new JSONObject(response);
                        System.out.print(jj + "\n");
                        out.println(jj);
                        out.flush();
                    }
                    default -> {
                    }
                }

                //out.println("yes, nulled");
            }
        } catch (IOException | SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
