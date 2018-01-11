package com.alexandre.kohakuapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Alexandre on 11/01/2018.
 */
@IgnoreExtraProperties
public class River {

    private String name;

    public River(){

    }

    public River(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
