package com.example.viikko9uusiyritys;

public class UserScore {
    private String comment;
    private int rating;
    private String name_of_user;


    public UserScore(int rating, String name_of_user) {
        this.rating = rating;
        this.name_of_user = name_of_user;
    }

    public UserScore(int rating, String name_of_user, String comment) {
        this.rating = rating;
        this.name_of_user = name_of_user;
        this.comment = comment;
    }


    public int getRating() {
        return this.rating;
    }

    public String getComment() {
        return this.comment;
    }

    public String getName() {
        return this.name_of_user;
    }
}