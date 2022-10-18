package com.meetvishalkumar.myapplication;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Login extends AppCompatActivity {
    Button Login_Button, Signup_Button, How_We_Work_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_login);
        Signup_Button = findViewById(R.id.Signup_Button);
        Login_Button = findViewById(R.id.Login_Button);
        How_We_Work_Button = findViewById(R.id.How_We_Work_Button);


    }

    public void send_user_login(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.Login_Button), "transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash_Login.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void send_user_signup(View view) {
        Intent intent = new Intent(getApplicationContext(), Signup.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.Signup_Button), "transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash_Login.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void play_audio_meme(View view) {
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.audio_meme);
        mediaPlayer.start();
    }
}