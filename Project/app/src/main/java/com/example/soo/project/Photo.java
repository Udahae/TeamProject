package com.example.soo.project;

/**
 * Created by leesangmu1 on 2017-11-24.
 */

public class Photo {

    private final String imageUrl;
    private final String email;
    private final String title;
    private final int number;


    public Photo(int number,String email, String title, String imageUrl){
        this.number = number;
        this.email = email;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public int getNumber(){ return number; }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }


    public String getImageUrl() {
        return imageUrl;
    }
}
