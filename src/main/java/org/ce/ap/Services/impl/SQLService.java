package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.SQLServiceInterface;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * this service is used for connecting the program to SQL server
 * and handling data storage and data recovery
 * @author rbmoon
 */
public class SQLService implements SQLServiceInterface {

    private Connection conn;
    private String sql;
    private Statement statement;
    private ResultSet res;
    private final String dbURL = "jdbc:sqlserver://localhost;integratedSecurity=true;databaseName=twitter";

    public SQLService() {
        try {
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Connected to SQL. SQL version: " + dm.getDatabaseProductVersion());
                //conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * this method gets a single user from database using username of the user
     * @param username username of the user
     * @return the User
     */
    public User getUser(String username) throws SQLException {
        String sql = "SELECT * from Users where Username = '" + username + "'";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<User> users = new ArrayList<User>();
        while (res.next()) {
            String name = res.getString("Username");
            String firstname = res.getString("FirstName");
            String password = res.getString("Password");
            String lastname = res.getString("LastName");
            String bio = res.getString("Bio");
            User u = new User(firstname, lastname, username, bio, LocalDateTime.now(), LocalDateTime.now(), password);
            users.add(u);
        }
        if (users.size() != 1) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * this method saves a tweet in database
     * @param t the tweet to be saved
     * @return the tweet, with the id parameter included
     */
    public Tweet newTweet(Tweet t) {
        try {
            String sql = "SELECT MAX(Id) as m from Tweets ";
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            int id = 0;
            while (res.next()) {
                id = res.getInt("m") + 1;
            }
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(t.getSentTime());
            String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
            sql = "INSERT INTO Tweets (Id,Text,SentTime,Sender,RepliedTo)" +
                    "VALUES ('" + id + "', '" + t.getText() + "', '" + s + "', '"
                    + t.getSender().getUsername() + "', '" + t.getRepliedTo().getId() + "')";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                t.setId(id);
            } else {
                return null;
            }
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * this method trys to create a new user
     * @param username username of the user
     * @param password password of the user
     * @return whether it was successful
     */
    public boolean Signup(String username,String password) {
        try {
            User u2 = getUser(username);
            if (u2!=null){
                return false;
            }
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
            String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
            String hashed = new AuthenticationServiceImpl().hash_password(password);
            sql = "INSERT INTO Users (Registerdate,Password,Username)" +
                    "VALUES ('" + s + "', '" + hashed + "', '" + username + "')";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            return rows > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * this method creates a tweet tree, relating all tweets and their replies
     * @param t the root tweet
     * @return the  Arraylist of replied tweets to the root tweet
     */
    public ArrayList<Tweet> createTweetTree(Tweet t) throws SQLException {
        String sql = "SELECT * from Tweets where RepliedTo = '" + t.getId() + "'";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        while (res.next()) {
            int id = Integer.parseInt(res.getString("Id"));
            String text = res.getString("Text");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String str = res.getString("SentTime");
            LocalDateTime sentTime = null;
            try {
                str = str.substring(0, 19);
                sentTime = LocalDateTime.parse(str, formatter);
            } catch (Exception ignored) {
            }
            User sender = new User(res.getString("Sender"));
            Tweet repliedTo = new Tweet(Integer.parseInt(res.getString("RepliedTo")));

            ArrayList<String> likes_usernames = getLikes(id);
            ArrayList<User> likes = new ArrayList<>();
            for (String lu: likes_usernames) {
                likes.add(new User(lu));
            }

            Tweet t2 = new Tweet(id, sender, likes, text, sentTime, null, repliedTo);
            ArrayList<Tweet> t2Replies = createTweetTree(t2);
            t2.setReplies(t2Replies);
            tweets.add(t2);
        }
        return tweets;
    }

    /**
     * this method returns all the users in database
     * @return all the users in database
     */
    public ArrayList<User> getAllUsers() throws SQLException {

        String sql = "SELECT * from Users ";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<User> users = new ArrayList<User>();
        while (res.next()) {
            String username = res.getString("Username");
            String firstname = res.getString("FirstName");
            String password = res.getString("Password");
            String lastname = res.getString("LastName");
            String bio = res.getString("Bio");
            User u = new User(firstname, lastname, username, bio, LocalDateTime.now(), LocalDateTime.now(), password);
            users.add(u);
        }
        return users;
    }

    /**
     * this method makes a user follow another user
     * @param user the requesting user
     * @param username target user's username
     * @return whether it was successful
     */
    public boolean Follow(User user, String username) throws SQLException {
        User u2 = getUser(username);
        if (u2 != null){
            String sql = "SELECT * from User_Follow where Username = '" + user.getUsername() + "' AND Follows ='"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
                return false;
            }
            sql = "INSERT INTO User_Follow (Username,Follows)" +
                    "VALUES ('" + user.getUsername() + "', '" + username + "')";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * this method makes a user unfollow another user
     * @param user the requesting user
     * @param username target user's username
     * @return whether it was successful
     */
    public boolean UnFollow(User user, String username) throws SQLException {
        User u2 = getUser(username);
        if (u2 != null){
            String sql = "SELECT * from User_Follow where Username = '" + user.getUsername() + "' AND Follows ='"+username+"'";
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            if (!res.next()) {
                return false;
            }
            sql = "DELETE FROM User_Follow WHERE Username = '" + user.getUsername() + "' AND Follows ='"+username+"'";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            return rows > 0;
        }
        return false;
    }

    /**
     * this method makes a user like another user
     * @param username the requesting user
     * @param tweetId target tweet
     * @return whether it was successful
     */
    public boolean Like(String username, int tweetId) throws SQLException {
        User u2 = getUser(username);
        if (u2 != null){
            String sql = "SELECT * from Likes where Username = '" + u2.getUsername() + "' AND TweetId ='"+tweetId+"'";
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);
            if (res.next()) {
                return false;
            }
            sql = "INSERT INTO Likes (Username,TweetId)" +
                    "VALUES ('" + u2.getUsername() + "', '" + tweetId + "')";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * this method returns the users who liked this tweet
     * @param tweetId the tweet to be analyzed
     * @return the list of usernames of users who liked this tweet
     */
    public ArrayList<String> getLikes(int tweetId) throws SQLException {

        String sql = "SELECT * from Likes WHERE TweetId ='"+tweetId+"' ";
        Statement statement = conn.createStatement();
        ResultSet res = statement.executeQuery(sql);
        ArrayList<String > usernames = new ArrayList<String>();
        while (res.next()) {
            String username = res.getString("Username");
            usernames.add(username);
        }
        return usernames;
    }

    /**
     * this method logs any action done by the server
     * @param body the action to be loged
     */
    public void Log(String body){
        try {
            java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(LocalDateTime.now());
            String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(timestamp);
            sql = "INSERT INTO Log (Body,Time)" +
                    "VALUES ('" + body + "', '" + s + "')";
            statement = conn.createStatement();
            int rows = statement.executeUpdate(sql);
            return;
        } catch (Exception e) {
            return;
        }
    }
}
