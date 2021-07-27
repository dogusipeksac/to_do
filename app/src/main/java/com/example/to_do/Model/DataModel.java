package com.example.to_do.Model;

import com.google.gson.annotations.SerializedName;

public class DataModel {

    //value
    private int userId;
    private int id;
    private String title;
    /*
    @SerializedName("completed")
    private boolean status;
    */
    private boolean completed;

    //getters
    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
    //no need setters because we dont set data
}
