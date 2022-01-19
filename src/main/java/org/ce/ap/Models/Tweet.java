package main.java.org.ce.ap.Models;

import java.time.LocalDateTime;

public class Tweet {
    private User sender;
    private User[] likes;
    private String text;
    private LocalDateTime sentTime;
    private Tweet[] replies;
    private Tweet repliedTo;

    public Tweet(User sender, User[] likes, String text, LocalDateTime sentTime, Tweet[] replies, Tweet repliedTo) {
        this.sender = sender;
        this.likes = likes;
        this.text = text;
        this.sentTime = sentTime;
        this.replies = replies;
        this.repliedTo = repliedTo;
    }
}
