package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.ConsoleViewService;

import java.util.ArrayList;
import java.util.Comparator;

public class ConsoleViewServiceImpl implements ConsoleViewService {

    @Override
    public void View_Tweets(ArrayList<Tweet> tweets, int offset) {
        tweets.sort(Comparator.comparing(Tweet::getSentTime).reversed());
        for (Tweet t : tweets) {
            PrintTweet(t,offset);
            View_Tweets(t.getReplies(),offset+1);
        }
    }

    private void PrintTweet(Tweet t, int offset) {
       String o = "";
        o = o.concat("-".repeat(Math.max(0, offset * 4)));
        System.out.println(o + "| Tweet id: " + t.getId() + "; User: " + t.getSender().getUsername() + "; Date: " + t.getSentTime());
        System.out.println(o +"| Tweet: "+ t.getText());
        String likedBy = "";
        for (User u:t.getLikes()) {
            likedBy = likedBy.concat(u.getUsername()+", ");
        }
        System.out.println(o +"| Liked by: "+ likedBy);
        System.out.println("---------------------------------------------");
    }
}
