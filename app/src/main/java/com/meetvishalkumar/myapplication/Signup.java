package com.meetvishalkumar.myapplication;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Signup extends AppCompatActivity {

    ImageView signup_back_button;
    TextInputLayout signup_fullname, signup_username, signup_email, signup_password;
    Button signup_next_button, signup_login_button;
    TextView signup_title_text, signup_slide_text;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        signup_back_button = findViewById(R.id.signup_back_button);
        signup_next_button = findViewById(R.id.signup_next_button);
        signup_login_button = findViewById(R.id.signup_login_button);
        signup_title_text = findViewById(R.id.signup_title_text);
        signup_slide_text = findViewById(R.id.signup_slide_text);
        signup_fullname = findViewById(R.id.signup_fullname);
        signup_username = findViewById(R.id.signup_username);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);

        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    public void callNextSigupScreen(View view) {

        if (!validateFullName() | !validateUsername() | !validateEmail() | !validatePassword()) {
            return;
        }
        String fullname = signup_fullname.getEditText().getText().toString();
        String username = signup_username.getEditText().getText().toString();
        String email = signup_email.getEditText().getText().toString();
        String password = signup_password.getEditText().getText().toString();


        Intent intent = new Intent(getApplicationContext(), Signup_Second.class);
        intent.putExtra("fullname", fullname);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);

        //Add Shared Animation
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair<View, String>(signup_back_button, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(signup_next_button, "transition_next_btn");
        pairs[2] = new Pair<View, String>(signup_login_button, "transition_login_btn");
        pairs[3] = new Pair<View, String>(signup_title_text, "transition_title_text");
        pairs[4] = new Pair<View, String>(signup_slide_text, "transition_slide_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }

    private boolean validateFullName() {
        String val = signup_fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            signup_fullname.setError("Field can not be empty");
            return false;
        } else {
            signup_fullname.setError(null);
            signup_fullname.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        String val = signup_username.getEditText().getText().toString().trim();
        val.replaceAll("\\s", "");

        if (val.isEmpty()) {
            signup_username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            signup_username.setError("Username is too large!");
            return false;
        } else {
            signup_username.setError(null);
            signup_username.setErrorEnabled(false);

            return true;
        }
    }

    private boolean validateEmail() {
        String val = signup_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            signup_email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            signup_email.setError("Invalid Email!");
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            signup_email.setError("Invalid Email!");
            return false;
        } else {
            signup_email.setError(null);
            signup_email.setErrorEnabled(false);
            return true;
        }


    }
    private boolean validatePassword() {
        String val = signup_password.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            signup_password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 8) {
            signup_password.setError("Password should contain Minimum of 6 characters!");
            return false;
        } else {
            signup_password.setError(null);
            signup_password.setErrorEnabled(false);
            return true;
        }
    }

        public void callLoginFromSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}