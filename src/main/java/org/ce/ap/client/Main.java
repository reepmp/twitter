package main.java.org.ce.ap.client;

import main.java.org.ce.ap.Models.GlobalParameters;
import main.java.org.ce.ap.Models.Tweet;
import main.java.org.ce.ap.Services.Interface.CommandParserService;
import main.java.org.ce.ap.Services.Interface.ConnectionService;
import main.java.org.ce.ap.Services.Interface.ConsoleViewService;
import main.java.org.ce.ap.Services.impl.CommandParserServiceImpl;
import main.java.org.ce.ap.Services.impl.ConnectionServiceImpl;
import main.java.org.ce.ap.Services.impl.ConsoleViewServiceImpl;
import main.java.org.ce.ap.Services.impl.SQLService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        System.out.println("Client started!!!");
        ConnectionService c = new ConnectionServiceImpl();
        GlobalParameters.sqlService = new SQLService();
        TimeUnit.SECONDS.sleep(2);
        c.setupClient();
        CommandParserService cps = new CommandParserServiceImpl();
        GlobalParameters.consoleViewService = new ConsoleViewServiceImpl();



        cps.Listen(c);

    }
}