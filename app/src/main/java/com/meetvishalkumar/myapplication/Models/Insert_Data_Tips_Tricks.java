package com.meetvishalkumar.myapplication.Models;

public class Insert_Data_Tips_Tricks {
    String name;
    String content;
    String FullName;

    public Insert_Data_Tips_Tricks(String name, String content,String FullName) {
        this.name = name;
        this.content = content;
        this.FullName=FullName;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
    public String getFullName() {
        return FullName;
    }
}
