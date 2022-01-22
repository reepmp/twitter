package main.java.org.ce.ap.Services.impl;

import java.util.*;

import main.java.org.ce.ap.Models.*;
import main.java.org.ce.ap.Services.Interface.CommandParserService;
import main.java.org.ce.ap.Services.Interface.ConnectionService;

/**
 * the service for parsing the input command
 * @author rbmoon
 */
public class CommandParserServiceImpl implements CommandParserService {

    /**
     *
     * @param c uses the connection service to connect to server
     */
    @Override
    public void Listen(ConnectionService c) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            try {
                switch (parts[0]) {
                    case "-help" -> {
                        System.out.println("list of the commands:\n" +
                                "signup username password\n" +
                                "login username password\n" +
                                "tweet \"your_tweet\" [\"tweetId_to_reply\"]\n" +
                                "timeline\n" +
                                "follow username_of_person\n" +
                                "unfollow username_of_person\n" +
                                "show_user_list\n" +
                                "like tweet_id");
                    }
                    case "login" -> {
                        String username = parts[1];
                        String password = parts[2];
                        String[] params = {username, password};
                        Request req = new Request("login", "user trying to log in", params);
                        Response response = c.sendRequest(req);
                        if (response.getHasError()) {
                            System.out.println("Login attempt failed! please try again:\nfor login, please enter your credentials in this format:\nlogin username password");
                        } else {
                            GlobalParameters.currentUser = new User(username);
                            System.out.println("welcome back " + username + "\n for the list of the commands, type -help");
                        }
                    }
                    case "signup" -> {
                        String username = parts[1];
                        String password = parts[2];
                        String[] params = {username, password};
                        Request req = new Request("signup", "user trying to signup", params);
                        Response response = c.sendRequest(req);
                        if (response.getHasError()) {
                            System.out.println("Signup attempt failed! please try again:\n");
                        } else {
                            GlobalParameters.currentUser = new User(username);
                            System.out.println("welcome " + username + "\n for the list of the commands, type -help");
                        }
                    }
                    case "tweet" -> {
                        if (GlobalParameters.currentUser == null) {
                            System.out.println("to preform this action, you need to login first");
                            break;
                        }
                        String text = "";
                        int replyId = 0;
                        try {
                            text = command.split("\"")[1];
                            replyId = Integer.parseInt(command.split("\"")[3]);
                        } catch (Exception ignored) {

                        }

                        String tweet_username = GlobalParameters.currentUser.getUsername();
                        String[] tweet_params = {tweet_username, text, String.valueOf(replyId)};
                        Request tweet_req = new Request("tweet", "user trying to tweet", tweet_params);
                        Response tweet_response = c.sendRequest(tweet_req);
                        if (tweet_response.getHasError()) {
                            System.out.println("your tweet could not be posted. please try again");
                        } else {
                            GlobalParameters.currentUser = new User(tweet_username);
                            System.out.println("your tweet has been successfully posted!!");
                        }

                    }
                    case "show_user_list" -> {
                        ArrayList<User> users = GlobalParameters.sqlService.getAllUsers();
                        for (User u : users) {
                            System.out.println(u.getUsername());
                        }
                    }
                    case "timeline" -> {
                        ArrayList<Tweet> tweets = GlobalParameters.sqlService.createTweetTree(new Tweet(0));
                        GlobalParameters.consoleViewService.View_Tweets(tweets, 0);
                    }
                    case "follow" -> {
                        if (GlobalParameters.currentUser == null) {
                            System.out.println("to preform this action, you need to login first");
                            break;
                        }
                        String username = "";
                        try {
                            username = command.split(" ")[1];
                        } catch (Exception ignored) {
                        }
                        String[] params = {GlobalParameters.currentUser.getUsername(), username};
                        Request req = new Request("follow", "user trying to follow", params);
                        Response response = c.sendRequest(req);
                        if (!response.getHasError()) {
                            System.out.println("you have successfully followed " + username + "!!");
                        } else {
                            System.out.println("attempt to follow " + username + " was unsuccessful!");
                        }

                    }
                    case "unfollow" -> {
                        if (GlobalParameters.currentUser == null) {
                            System.out.println("to preform this action, you need to login first");
                            break;
                        }
                        String username = "";
                        try {
                            username = command.split(" ")[1];
                        } catch (Exception ignored) {
                        }
                        String[] params = {GlobalParameters.currentUser.getUsername(), username};
                        Request req = new Request("unfollow", "user trying to follow", params);
                        Response response = c.sendRequest(req);
                        if (!response.getHasError()) {
                            System.out.println("you have successfully unfollowed " + username + "!!");
                        } else {
                            System.out.println("attempt to unfollow " + username + " was unsuccessful!");
                        }

                    }
                    case "like" -> {
                        if (GlobalParameters.currentUser == null) {
                            System.out.println("to preform this action, you need to login first");
                            break;
                        }
                        int tweetId = 0;
                        try {
                            tweetId = Integer.parseInt(command.split(" ")[1]);
                        } catch (Exception ignored) {
                            System.out.println("could not read tweet Id. please try again");
                            break;
                        }

                        String username = GlobalParameters.currentUser.getUsername();
                        String[] params = {username, String.valueOf(tweetId)};
                        Request req = new Request("like", "user trying to like a tweet", params);
                        Response tweet_response = c.sendRequest(req);
                        if (tweet_response.getHasError()) {
                            System.out.println("you were unable to like that tweet. please try again");
                        } else {
                            System.out.println("you successfully liked that tweet!!");
                        }

                    }

                    default -> System.out.println("command not found in the command list. type -help for help");
                }
            } catch (Exception e) {
                System.out.println("something went wrong. please try again\n" + e.getMessage());
            }
        }
    }
}
