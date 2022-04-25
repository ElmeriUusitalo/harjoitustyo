package com.example.viikko9uusiyritys;

public class Movie {
    protected final String name;

    public Movie (String n) {
    name = n;
    }

        @Override
        public String toString () {
            return name;
        }

}
