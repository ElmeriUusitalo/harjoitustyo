package com.example.viikko9uusiyritys;

public class Theatres {
    private String place;
    private String id;

    public Theatres(String id, String place) {
        this.id = id;
        this.place = place;
    }

    public String getName(){
        return place;
    }

    public String getId(){
        return id;
    }

    @Override
    public String toString () {
        return place;
    }
}

