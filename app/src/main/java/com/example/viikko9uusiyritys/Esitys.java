package com.example.viikko9uusiyritys;

public class Esitys {
    protected final String nimi;

    public Esitys (String n) {
    nimi = n;
    }

        @Override
        public String toString () {
            return nimi;
        }

}
