package main.java.org.ce.ap.Services.impl;

import main.java.org.ce.ap.Models.GlobalParameters;
import main.java.org.ce.ap.Models.User;
import main.java.org.ce.ap.Services.Interface.AuthenticationService;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public boolean Login(String username, String password) throws NoSuchAlgorithmException, SQLException {
        String hashed = hash_password(password);
        User u = GlobalParameters.sqlService.getUser(username);
        if (u==null){
            return false;
        }
        return Objects.equals(u.getPassword(), hashed);
    }

    private String hash_password(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
