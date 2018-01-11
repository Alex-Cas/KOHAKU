package com.alexandre.kohakuapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandre on 11/01/2018.
 */

@IgnoreExtraProperties
public class River {

    private String name, continent, country;

    public River(){

    }

    public River(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public String getContinent(){
        return this.continent;
    }
    public String getCountry(){
        return this.country;
    }
}
