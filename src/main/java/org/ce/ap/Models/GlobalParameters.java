package main.java.org.ce.ap.Models;
import main.java.org.ce.ap.Services.Interface.AuthenticationService;
import main.java.org.ce.ap.Services.Interface.ConsoleViewService;
import main.java.org.ce.ap.Services.impl.SQLService;

/**
 * @author rbmoon
 * class that stores one instant of services and other global parameters
 */
public class GlobalParameters {
    static public SQLService sqlService;
    static public AuthenticationService authenticationService;
    static public User currentUser = null;
    static public ConsoleViewService consoleViewService;
}
