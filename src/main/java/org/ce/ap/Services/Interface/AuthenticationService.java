package main.java.org.ce.ap.Services.Interface;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface AuthenticationService {
    boolean Login(String username, String password) throws NoSuchAlgorithmException, SQLException;
}
