package com.meetvishalkumar.myapplication.Models;

public class Show_Data_Tips_Tricks {
    private String name;
    private String content;
    private String fullName;

    public Show_Data_Tips_Tricks(String name, String content
            ,String fullName
    ) {
        this.name = name;
        this.content = content;
        this.fullName=fullName;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }
}


//    public Show_Data_Tips_Tricks(String name, String content,String fullName) {
//        this.name = name;
//        this.content = content;
//        this.fullName = fullName;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getContent() {
//        return content;
//    }
//    public String getfullName() {
//        return fullName;
//    }
//}
