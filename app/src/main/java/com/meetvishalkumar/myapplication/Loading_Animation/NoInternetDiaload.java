package com.meetvishalkumar.myapplication.Loading_Animation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.meetvishalkumar.myapplication.R;
import com.meetvishalkumar.myapplication.SplashScreen;
import com.meetvishalkumar.myapplication.meal_planner;

public class NoInternetDiaload extends Dialog {
    public NoInternetDiaload(@NonNull Context context){
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet_diaload);
//        final AppCompatButton retryButton=findViewById(R.id.retry_button);
//        retryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });
    }
}