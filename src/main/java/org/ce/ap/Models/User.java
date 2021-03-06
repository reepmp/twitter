package main.java.org.ce.ap.Models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.*;

/**
 * @author rbmoon
 * the model for User object
 */
public class User {
    //private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String bio;
    private LocalDateTime birthdate;
    private LocalDateTime registerDate;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String firstname, String lastname, String username, String bio, LocalDateTime birthdate, LocalDateTime registerDate, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.bio = bio;
        this.birthdate = birthdate;
        this.registerDate = registerDate;
        this.password = password;
    }

    public User(String username){
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

  //  public int getId() {
  //      return id;
  //  }

  //  public void setId(int id) {
   //     this.id = id;
   // }


    public void t() {
        birthdate = LocalDateTime.now();
        ;
        System.out.println(birthdate);
    }


}
