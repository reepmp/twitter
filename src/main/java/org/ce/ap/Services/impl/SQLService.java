package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SQLService {

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
            Tweet t2 = new Tweet(id, sender, null, text, sentTime, null, repliedTo);
            ArrayList<Tweet> t2Replies = createTweetTree(t2);
            t2.setReplies(t2Replies);
            tweets.add(t2);
        }
        return tweets;
    }

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
}
