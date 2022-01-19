package main.java.org.ce.ap.server;

import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.Services.impl.ConnectionServiceImpl;
import main.java.org.ce.ap.Services.impl.SQLService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world from server");
        SQLService sqlService = new SQLService();
        ConnectionService c = new ConnectionServiceImpl();
        c.setupServer();
    }
}
