package com.example.viikko9uusiyritys;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private int age;

    //constructor if age is not submitted
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.age = 12;
    }
    //and if it is
    public User(String username, String password, int age){
        this.username = username;
        this.password = password;
        this.age = age;
    }


    public boolean isSameUsername(String username){
        if(this.username.equals(username)) {
            return true;
        } else {
            return false;
        }

    }

    public String getUsername(){
        return this.username;
    }

    //not secure way, but better than getter :)
    public boolean checkPassword(String password){
        if(this.password.equals(password)){
            return true;
        } else {
             return false;
        }
    }


}
