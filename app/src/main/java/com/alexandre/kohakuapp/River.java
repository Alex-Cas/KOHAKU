package com.alexandre.kohakuapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandre on 11/01/2018.
 */

@IgnoreExtraProperties
public class River {

    private String name, continent, country;
    private int _id;

    public River(){

    }

    public River(String name, String continent, String country, int id){
        this.name = name;
        this.continent = continent;
        this.country = country;
        this._id = id;
    }

    public String getName(){return this.name;}
    public String getContinent(){return this.continent;}
    public String getCountry(){return this.country;}
    public int get_id(){return this._id;}
}
