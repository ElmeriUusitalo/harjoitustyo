package com.example.viikko9uusiyritys;

public class Theatres {
    private String place;
    private int id;

    public Theatres(String id, String place) {
        this.id = Integer.parseInt(id);
        this.place = place;
    }

    @Override
    public String toString () {
        return place;
    }
}

