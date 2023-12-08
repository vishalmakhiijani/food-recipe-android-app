package com.meetvishalkumar.myapplication.UserAccount;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.R;

import io.github.muddz.styleabletoast.StyleableToast;

public class ForgetPassword extends AppCompatActivity {
    TextInputLayout forget_password_Email;
    TextInputEditText EditInputEditText_Email;
    ProgressBar progressBar;
    Button forget_password_next_btn;
    FirebaseAuth firebaseAuth;
    ImageView forget_password_back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }
        forget_password_Email = findViewById(R.id.forget_password_Email);
        EditInputEditText_Email = findViewById(R.id.EditInputEditText_Email);
        progressBar = findViewById(R.id.progressBar);
        forget_password_back_btn = findViewById(R.id.forget_password_back_btn);
        forget_password_next_btn = findViewById(R.id.forget_password_next_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        forget_password_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                resetPassword();
            }
        });
        forget_password_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInternet()) {
                    NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                    noInternetDialoag.setCancelable(false);
                    noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                    noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    noInternetDialoag.show();
                }
                startActivity(new Intent(ForgetPassword.this, Login.class));
            }
        });
    }

    private void resetPassword() {
        String email = forget_password_Email.getEditText().getText().toString().trim();
        if (!validateEmail()) {
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgetPassword.this, "Email with a link has been send to your account", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                } else {
//                    Toast.makeText(ForgetPassword.this, "Something Went Wrong"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(ForgetPassword.this, "Something Went Wrong" + task.getException().getMessage(), R.style.errorToast).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgetPassword.this, "can't Send Email" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail() {
        String val = forget_password_Email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            forget_password_Email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            forget_password_Email.setError("Invalid Email!");
            return false;
        } else {
            forget_password_Email.setError(null);
            forget_password_Email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}