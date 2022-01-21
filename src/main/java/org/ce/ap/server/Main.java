package main.java.org.ce.ap.server;

import main.java.org.ce.ap.Models.GlobalParameters;
import main.java.org.ce.ap.Services.Interface.AuthenticationService;
import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.Services.impl.AuthenticationServiceImpl;
import main.java.org.ce.ap.Services.impl.ConnectionServiceImpl;
import main.java.org.ce.ap.Services.impl.SQLService;

public class Main {

    public static void main(String[] args) {
        System.out.println("Server started!!!");
        GlobalParameters.sqlService = new SQLService();
        ConnectionService c = new ConnectionServiceImpl();
        GlobalParameters.authenticationService = new AuthenticationServiceImpl();
        c.setupServer();
    }
}
