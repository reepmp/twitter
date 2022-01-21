package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
            if (rows > 0){
                t.setId(id);
            }else {
                return null;
            }
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
