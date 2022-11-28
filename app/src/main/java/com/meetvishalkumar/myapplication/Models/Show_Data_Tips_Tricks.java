package com.meetvishalkumar.myapplication.Models;

public class Show_Data_Tips_Tricks {
    private String name;
    private String content;
    private String FullName;

    public Show_Data_Tips_Tricks(String name, String content
//            ,String FullName
    ) {
        this.name = name;
        this.content = content;
//        this.FullName=FullName;

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

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
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
