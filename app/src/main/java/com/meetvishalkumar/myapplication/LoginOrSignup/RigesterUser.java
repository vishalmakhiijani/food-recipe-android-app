package com.meetvishalkumar.myapplication.LoginOrSignup;


public class RigesterUser {
    public String _fullname, _email, _username, _password, _date, _gender;
    public int _age,_birthyear;


    public RigesterUser() {

    }

    public RigesterUser(String _fullname, String _email, String _username, String _password, String _date, String _gender, int _age,int _birthyear) {
        this._fullname = _fullname;
        this._email = _email;
        this._username = _username;
        this._password = _password;
        this._date = _date;
        this._gender = _gender;
        this._age = _age;
        this._birthyear=_birthyear;

    }

}
