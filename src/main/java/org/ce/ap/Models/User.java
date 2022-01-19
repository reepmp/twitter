package main.java.org.ce.ap.Models;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;

public class User {
    private String firstname;
    private String lastname;
    private String username;
    private String bio;
    private LocalDateTime birthdate;
    private LocalDateTime registerDate;


    public User(String firstname, String lastname, String username, String bio, LocalDateTime birthdate, LocalDateTime registerDate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.bio = bio;
        this.birthdate = birthdate;
        this.registerDate = registerDate;
    }

    public void t(){
        birthdate =  LocalDateTime.now();;
        System.out.println(birthdate);
    }


    public String hash_password(String password) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(password.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);
        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }
        return hashtext.toUpperCase();
    }
}
