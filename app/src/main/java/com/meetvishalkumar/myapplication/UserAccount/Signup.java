package com.meetvishalkumar.myapplication.UserAccount;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.MainActivity;
import com.meetvishalkumar.myapplication.R;

import java.util.Calendar;
import java.util.regex.Pattern;

import io.github.muddz.styleabletoast.StyleableToast;

public class Signup extends AppCompatActivity {

    ImageView signup_back_button;
    TextInputLayout signup_fullname, signup_email, signup_password, c_signup_password;
    Button signup_next_button, signup_login_button;
    private FirebaseAuth mAuth;
    private FirebaseAnalytics mFirebaseAnalytics;

    ProgressBar progressBar;

//    Email Validation
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

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
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        signup_back_button = findViewById(R.id.signup_back_button);
        signup_next_button = findViewById(R.id.User_signup_button);
        signup_login_button = findViewById(R.id.signup_login_button);
        signup_fullname = findViewById(R.id.signup_fullname);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        c_signup_password = findViewById(R.id.c_signup_password);
        progressBar = findViewById(R.id.progressBar);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signup_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                startActivity(new Intent(getApplicationContext(), Splash_Login.class));
                finish();
            }
        });
        signup_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                RigesterUser();
            }
        });

    }

    private void RigesterUser() {

//        Get Info
        String FullName = signup_fullname.getEditText().getText().toString().trim();
        String Email = signup_email.getEditText().getText().toString().trim();
        String Password = signup_password.getEditText().getText().toString().trim();
        String C_Password = c_signup_password.getEditText().getText().toString().trim();
//        Validations
        if (TextUtils.isEmpty(FullName)) {
            StyleableToast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }
        if (TextUtils.isEmpty(Email)) {
            StyleableToast.makeText(getApplicationContext(), "Email is Empty", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            StyleableToast.makeText(getApplicationContext(), "Password is Empty", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }
        if (TextUtils.isEmpty(C_Password)) {
            StyleableToast.makeText(getApplicationContext(), "Confirm Password is Empty", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }
        if (Password.length() < 8) {
            StyleableToast.makeText(getApplicationContext(), "Password must be at least 8 chars", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }
        if (!Password.equals(C_Password)) {
            StyleableToast.makeText(getApplicationContext(), "Both Passwords DO Not Match", Toast.LENGTH_SHORT, R.style.OnActivity).show();
            return;
        }

        if (EMAIL_ADDRESS_PATTERN.matcher(Email).matches())
            progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //         Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Signup.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            RigesterUser rigesterUser = new RigesterUser(FullName, Email);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rigesterUser)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(getApplicationContext(), "Account Created PLease Verify Your Email", Toast.LENGTH_SHORT).show();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            user.sendEmailVerification();
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(getApplicationContext(), Login.class));

                                        }
                                    });
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Signup.this, "createUserWithEmail:failure" + task.getException(),Toast.LENGTH_SHORT).show();
                            signup_login_button.setText(task.getException()+""+Email);
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

//        mAuth.createUserWithEmailAndPassword(_email, _password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    RigesterUser rigesterUser = new RigesterUser(_fullName, _email, _password);
//                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(rigesterUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            Toast.makeText(getApplicationContext(), "Account Created PLease Verify Your Email", Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            user.sendEmailVerification();
//                            FirebaseAuth.getInstance().signOut();
//                            startActivity(new Intent(getApplicationContext(), Login.class));
//
//                        }
//                    });
//                } else if (!task.isSuccessful()) {
//                    Toast.makeText(Signup.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });

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



    private boolean validateEmail() {
        String val = signup_email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            signup_email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            signup_email.setError("Invalid Email!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
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
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Splash_Login.class));
        finish();
    }
    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}