package main.java.org.ce.ap.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author rbmoon
 * the model for Tweet object
 */
public class Tweet {
    private int id;
    private User sender;
    private ArrayList<User> likes;
    private String text;
    private LocalDateTime sentTime;
    private ArrayList<Tweet> replies;
    private Tweet repliedTo;

    public Tweet(String username, String text, int repliedTo) {
        id = 0;
        this.text = text;
        sentTime = LocalDateTime.now();
        this.sender = new User(username);
        this.repliedTo = new Tweet(repliedTo);
        replies = new ArrayList<>();
        likes = new ArrayList<>();
    }

    public Tweet(int id) {
        this.id = id;
        replies = new ArrayList<>();
        likes = new ArrayList<>();
    }

    public Tweet(int id, User sender, ArrayList<User> likes, String text, LocalDateTime sentTime, ArrayList<Tweet> replies, Tweet repliedTo) {

        this.id = id;
        this.sender = sender;
        this.likes = likes;
        this.text = text;
        this.sentTime = sentTime;
        this.replies = replies;
        this.repliedTo = repliedTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public ArrayList<Tweet> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Tweet> replies) {
        this.replies = replies;
    }

    public Tweet getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Tweet repliedTo) {
        this.repliedTo = repliedTo;
    }
}
