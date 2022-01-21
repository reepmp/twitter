package main.java.org.ce.ap.Models;

import java.time.LocalDateTime;

public class Tweet {
    private int id;
    private User sender;
    private User[] likes;
    private String text;
    private LocalDateTime sentTime;
    private Tweet[] replies;
    private Tweet repliedTo;

    public  Tweet(String username, String text, int repliedTo){
        id = 0;
        this.text=text;
        sentTime = LocalDateTime.now();
        this.sender = new User(username);
        this.repliedTo = new Tweet(repliedTo);
    }
    public Tweet(int id){
        this.id = id;
    }
    public Tweet(int id, User sender, User[] likes, String text, LocalDateTime sentTime, Tweet[] replies, Tweet repliedTo) {

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

    public User[] getLikes() {
        return likes;
    }

    public void setLikes(User[] likes) {
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

    public Tweet[] getReplies() {
        return replies;
    }

    public void setReplies(Tweet[] replies) {
        this.replies = replies;
    }

    public Tweet getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(Tweet repliedTo) {
        this.repliedTo = repliedTo;
    }
}
