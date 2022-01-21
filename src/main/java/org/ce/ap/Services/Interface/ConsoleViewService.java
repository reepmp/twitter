package main.java.org.ce.ap.Services.Interface;

import main.java.org.ce.ap.Models.Tweet;

import java.util.ArrayList;

public interface ConsoleViewService {
    void View_Tweets(ArrayList<Tweet> tweets, int offset);
}
