package com.example.viikko9uusiyritys;

public class Theatres {
    protected final String place;
    protected final String ID;

    public Theatres(String n, String m) {
        ID = n;
        place = m;
    }

    @Override
    public String toString () {
        return place;
    }
}

