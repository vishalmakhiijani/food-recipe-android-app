package com.meetvishalkumar.myapplication.Models;

public class Tag {
    private String name;
    private int imageResId;

    public Tag(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}

