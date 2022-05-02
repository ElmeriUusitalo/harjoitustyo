package com.example.viikko9uusiyritys;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {
    private String title;
    private String time;
    private int hrs;
    private float mean;
    private int amount_of_stars;

    private int lenght;
    private int age_rating;
    private ArrayList<UserScore> ratings;



    public Movie(String title){
        this.title = title;

    }


    //For movies based on theatres
    public Movie(String title, String time){
        String[] parts = time.split("T");
        String[] time_splitted = parts[1].split(":");

        hrs = Integer.parseInt(time_splitted[0] + time_splitted[1]);

        this.title = title;
        this.time = time_splitted[0] + ":" + time_splitted[1];
    }






    public Movie(String title, String lenght, String age_rating){
        this.title = title;
        ratings = new ArrayList<UserScore>();
        try {
            this.age_rating = Integer.parseInt(age_rating);
            this.lenght = Integer.parseInt(lenght);
            this.time = "";
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public String getTitle(){
        return title;
    }

    public String getTime(){
        return time;
    }

    public int getHrs(){
        return hrs;
    }

    public void addRating(UserScore rating){
        ratings.add(rating);
    }

    public void addStar(int star){
        amount_of_stars++;
        this.mean = (this.mean + star) / amount_of_stars;
    }

    public float getStars(){
        float mean;
        mean = 1;
        return mean;
    }

    public ArrayList<UserScore> getRatings(){
        return ratings;
    }

    public ArrayList<String> getComments(){
        ArrayList<String> all_comments = new ArrayList<>();
        for (UserScore i : ratings){
            all_comments.add(i.getComment());
        }
        return all_comments;
    }

    @Override
    public String toString() {
        return title + " " + time;
    }

}
