package main.java.org.ce.ap.client;

import main.java.org.ce.ap.Services.Interface.CommandParserService;
import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.Services.impl.CommandParserServiceImpl;
import main.java.org.ce.ap.Services.impl.ConnectionServiceImpl;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Client started!!!");
        ConnectionService c = new ConnectionServiceImpl();
        TimeUnit.SECONDS.sleep(5);
        c.setupClient();
        CommandParserService cps= new CommandParserServiceImpl();
        cps.Listen(c);
        //new User().t();
        /*try {
            System.out.println(new User().hash_password("hh"));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }
}