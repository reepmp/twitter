package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.ConsoleViewService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * this service can output tweet timeline on console
 * @author rbmoon
 */


public class ConsoleViewServiceImpl implements ConsoleViewService {

    /**
     *
     * @param tweets ArrayList of tweets to display
     * @param offset number of - to be added at the beginning
     */
    @Override
    public void View_Tweets(ArrayList<Tweet> tweets, int offset) {
        tweets.sort(Comparator.comparing(Tweet::getSentTime).reversed());
        for (Tweet t : tweets) {
            PrintTweet(t, offset);
            View_Tweets(t.getReplies(), offset + 1);
        }
    }

    private void PrintTweet(Tweet t, int offset) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = t.getSentTime().format(formatter);


        String o = "";
        o = o.concat("-".repeat(Math.max(0, offset * 4)));
        System.out.println(o + "| Tweet id: " + t.getId() + "; User: " + t.getSender().getUsername() + "; Date: " + formattedDateTime);
        System.out.println(o + "| Tweet: " + t.getText());
        String likedBy = "";
        for (User u : t.getLikes()) {
            likedBy = likedBy.concat(u.getUsername() + ", ");
        }
        System.out.println(o + "| Liked by: " + likedBy);
        System.out.println("---------------------------------------------");
    }
}
