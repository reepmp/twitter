package main.java.org.ce.ap.client;

import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.Services.impl.ConnectionServiceImpl;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world from client");
        ConnectionService c = new ConnectionServiceImpl();
        TimeUnit.SECONDS.sleep(5);
        c.setupClient();
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