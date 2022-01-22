package main.java.org.ce.ap.Services.Interface;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.impl.AuthenticationServiceImpl;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public interface SQLServiceInterface {

    User getUser(String username) throws SQLException;

    Tweet newTweet(Tweet t);

    boolean Signup(String username, String password);

    ArrayList<Tweet> createTweetTree(Tweet t) throws SQLException;

    ArrayList<User> getAllUsers() throws SQLException;

    boolean Follow(User user, String username) throws SQLException;

    boolean UnFollow(User user, String username) throws SQLException;

    boolean Like(String username, int tweetId) throws SQLException;

    ArrayList<String> getLikes(int tweetId) throws SQLException;

    void Log(String body);

}
