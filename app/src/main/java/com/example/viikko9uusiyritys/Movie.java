package com.example.viikko9uusiyritys;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String time;
    private int hrs;
    int lenght;
    int age_rating;
    UserScore rating;


    public Movie(String title){
        this.title = title;

    }



    public Movie(String title, String time){
        String[] parts = time.split("T");
        String[] time_splitted = parts[1].split(":");

        hrs = Integer.parseInt(time_splitted[0] + time_splitted[1]);

        this.title = title;
        this.time = time_splitted[0] + ":" + time_splitted[1];
    }

    public Movie(String title, int lenght, int age_rating){
        this.title = title;
        this.time = "" + lenght + "min";
        this.age_rating = age_rating;
        this.lenght = lenght;
    }


    public Movie(String title, String lenght, String age_rating){
        this.title = title;
        try {
            this.age_rating = Integer.parseInt(age_rating);
            this.lenght = Integer.parseInt(lenght);
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




    @Override
    public String toString() {
        return title + " " + time;
    }

}
