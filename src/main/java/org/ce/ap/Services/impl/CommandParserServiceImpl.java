package main.java.org.ce.ap.Services.impl;

import java.util.*;

import main.java.org.ce.ap.Models.GlobalParameters;
import main.java.org.ce.ap.Models.Request;
import main.java.org.ce.ap.Models.Response;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.CommandParserService;
import main.java.org.ce.ap.Services.Interface.ConnectionService;

public class CommandParserServiceImpl implements CommandParserService {
    @Override
    public void Listen(ConnectionService c) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            try {
            switch (parts[0]){
                case "-help":
                    System.out.println("list of the commands:\n" +
                            "login username password\n" +
                            "tweet \"your_tweet\" [\"tweetId_to_reply\"]\n" +
                            "follow username_of_person\n" +
                            "unfollow username_of_person\n" +
                            "show_user_list\n" +
                            "like tweet_id");
                    break;
                case "login":
                    String username = parts[1];
                    String password = parts[2];
                    String[] params = {username,password};
                    Request req = new Request("login","user trying to log in",params);
                    Response response = c.sendRequest(req);
                    if (response.getHasError()){
                        System.out.println("Login attempt failed! please try again:\nfor login, please enter your credentials in this format:\nlogin username password");
                    }else {
                        GlobalParameters.currentUser = new User(username);
                        System.out.println("welcome back "+username+"\n for the list of the commands, type -help");
                    }
                    break;
                case "tweet":
                    if (GlobalParameters.currentUser==null){
                        System.out.println("to preform this action, you need to login first");
                        break;
                    }
                    String text ="";
                    int replyId = 0;
                    try {
                        text = command.split("\"")[1];
                        replyId = Integer.parseInt(command.split("\"")[3]);
                    }catch (Exception ignored){

                    }

                    String tweet_username = GlobalParameters.currentUser.getUsername();
                    String[] tweet_params = {tweet_username, text, String.valueOf(replyId)};
                    Request tweet_req = new Request("tweet","user trying to tweet",tweet_params);
                    Response tweet_response = c.sendRequest(tweet_req);
                    if (tweet_response.getHasError()){
                        System.out.println("your tweet could not be posted. please try again");
                    }else {
                        GlobalParameters.currentUser = new User(tweet_username);
                        System.out.println("your tweet has been successfully posted!!");
                    }

                    break;
                default:
                    System.out.println("command not found in the command list. type -help for help");
            }
            }catch (Exception e){
                System.out.println("something went wrong. please try again\n"+e.getMessage());
            }
        }
    }
}
