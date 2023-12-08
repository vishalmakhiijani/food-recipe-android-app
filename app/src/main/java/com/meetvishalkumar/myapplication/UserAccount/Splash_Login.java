package com.meetvishalkumar.myapplication.UserAccount;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.R;

public class Splash_Login extends AppCompatActivity {
    Button Login_Button, Signup_Button, How_We_Work_Button;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }
        setContentView(R.layout.activity_splash_login);
        Signup_Button = findViewById(R.id.Signup_Button);
        Login_Button = findViewById(R.id.Login_Button);
        How_We_Work_Button = findViewById(R.id.How_We_Work_Button);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}