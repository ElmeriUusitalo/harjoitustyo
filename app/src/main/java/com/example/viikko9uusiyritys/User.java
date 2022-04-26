package com.example.viikko9uusiyritys;

public class User {
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

    public String getUsername(){
        return this.username;
    }

    //not secure way, but better than getter :)
    public int checkPassword(String password){
        if(this.password == password){
            return 1;
        } else {
             return 0;
        }
    }
}
