package com.example.soo.project;

/**
 * Created by leesangmu1 on 2017-11-24.
 */

public class Photo {
//    private final int name;
//    private final int author;
//    private final int imageResource;
    private boolean isFavorite = false;
    private final String imageUrl;
    private final String email;
    private final String title;

//    public Photo(int name, int author, int imageResource, String imageUrl) {
//        this.name = name;
//        this.author = author;
//        this.imageResource = imageResource;
//        this.imageUrl = imageUrl;
//    }

    public Photo(String email, String title, String imageUrl){
        this.email = email;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }
    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
