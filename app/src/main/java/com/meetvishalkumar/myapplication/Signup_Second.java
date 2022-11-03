package com.meetvishalkumar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.meetvishalkumar.myapplication.LoginOrSignup.RigesterUser;

import java.util.Calendar;

public class Signup_Second extends AppCompatActivity {
    ImageView signup_back_button;
    Button signup_next_button, signup_login_button;
    TextView signup_title_text, signup_slide_text;
    RadioGroup radioGroup;
    RadioButton selectedGender;
    DatePicker datePicker;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup_second);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        signup_back_button = findViewById(R.id.signup_back_button);
        signup_next_button = findViewById(R.id.signup_next_button);
        signup_login_button = findViewById(R.id.signup_login_button);
        signup_title_text = findViewById(R.id.signup_title_text);
        signup_slide_text = findViewById(R.id.signup_slide_text);
        progressBar = findViewById(R.id.progressBar);

        radioGroup = findViewById(R.id.radio_group);
        datePicker = findViewById(R.id.age_picker);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup.class));
                finish();
            }
        });
        signup_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RigesterUser();
            }
        });


    }

    private void RigesterUser() {
        validateAge();
        validateGender();
        if (!validateGender() | !validateAge()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Intent intent1 = getIntent();
        String _fullName = intent1.getStringExtra("fullname");
        String _username = intent1.getStringExtra("username");
        String _email = intent1.getStringExtra("email");
        String _password = intent1.getStringExtra("password");

        selectedGender = findViewById(radioGroup.getCheckedRadioButtonId());
        String _gender = selectedGender.getText().toString();
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int CureentYear = Calendar.getInstance().get(Calendar.YEAR);
        int _age = CureentYear - year;
        String _date = month + "/" + day + "/" + year;
        int _birthyear =year;
        mAuth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RigesterUser rigesterUser = new RigesterUser(_fullName, _email, _username, _password, _date, _gender, _age,_birthyear);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rigesterUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getApplicationContext(), "Account Created PLease Verify Your Email", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(), Login.class));

                        }
                    });
                } else if (!task.isSuccessful()) {
                    Toast.makeText(Signup_Second.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private boolean validateGender() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = datePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 10) {
            Toast.makeText(this, "You are not eligible to apply! Minimum age is 10 ", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }


}