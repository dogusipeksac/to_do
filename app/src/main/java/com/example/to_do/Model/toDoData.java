package com.example.to_do.Model;

public class toDoData {
    //value
    private String userId;
    private String id;
    private String title;
    private String imgUrl;

    public toDoData(String userId, String id, String title, String imgUrl) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
