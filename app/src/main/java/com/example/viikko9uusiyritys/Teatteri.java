package com.example.viikko9uusiyritys;

public class Teatteri {
    protected final String paikka;
    protected final String ID;

    public Teatteri(String n, String m) {
        ID = n;
        paikka = m;
    }

    @Override
    public String toString () {
        return paikka;
    }
}

