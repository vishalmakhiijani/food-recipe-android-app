package com.meetvishalkumar.myapplication.UserAccount;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.R;

public class Login extends AppCompatActivity {
    ProgressBar progressBar;
    TextInputLayout login_Email_editText, login_password;
    ImageView login_back_button;
    Button letTheUserLogIn, Button_Forget_Password, Button_Create_Account;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            finish();
        }
        progressBar = findViewById(R.id.progressBar);
        login_Email_editText = findViewById(R.id.login_Email_editText);
        login_password = findViewById(R.id.login_password_editText);
        letTheUserLogIn = findViewById(R.id.letTheUserLogIn);
        login_back_button = findViewById(R.id.login_back_button);
        Button_Forget_Password = findViewById(R.id.Button_Forget_Password);
        Button_Create_Account = findViewById(R.id.Button_Create_Account);
        login_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                startActivity(new Intent(Login.this, Splash_Login.class));
            }
        });
        Button_Forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                startActivity(new Intent(Login.this, ForgetPassword.class));
            }
        });
        Button_Create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
        letTheUserLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                UserLogin();
            }
        });
    }

    private void UserLogin() {
        if (!validateEmail() | !validatePassword()) {
            return;
        }

        String email = login_Email_editText.getEditText().getText().toString().trim();
        String password = login_password.getEditText().getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        login_Email_editText.setEnabled(false);
        login_password.setEnabled(false);
        letTheUserLogIn.setEnabled(false);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Toast.makeText(Login.this,"",Toast.LENGTH_SHORT).show();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(Login.this, Profile.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(Login.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        login_Email_editText.setEnabled(true);
                        login_password.setEnabled(true);
                        letTheUserLogIn.setEnabled(true);
                    }


                } else if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Failed To Login Try Again", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    login_Email_editText.setEnabled(true);
                    login_password.setEnabled(true);
                    letTheUserLogIn.setEnabled(true);
                }
            }
        });

    }

    private boolean validateEmail() {
        String val = login_Email_editText.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            login_Email_editText.setError("Field can not be empty");
            login_Email_editText.requestFocus();
            return false;
        } else if (!val.matches(checkEmail)) {
            login_Email_editText.setError("Invalid Email!");
            login_Email_editText.requestFocus();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            login_Email_editText.setError("Invalid Email!");
            login_Email_editText.requestFocus();
            return false;
        } else {
            login_Email_editText.setError(null);
            login_Email_editText.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = login_password.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            login_password.setError("Field can not be empty");
            return false;
        } else if (val.length() < 6) {
            login_password.setError("Password should contain Minimum of 6 characters!");
            return false;
        } else {
            login_password.setError(null);
            login_password.setErrorEnabled(false);
            return true;
        }
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