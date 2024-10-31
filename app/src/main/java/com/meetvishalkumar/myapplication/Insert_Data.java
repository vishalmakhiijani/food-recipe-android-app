package com.meetvishalkumar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.meetvishalkumar.myapplication.UserAccount.Profile;
import com.meetvishalkumar.myapplication.UserAccount.RigesterUser;
import com.meetvishalkumar.myapplication.UserAccount.Splash_Login;
import com.meetvishalkumar.myapplication.Models.Insert_Data_Tips_Tricks;

import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class Insert_Data extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu_opener_image;
    LinearLayout contentView;
    EditText Edit_Text_Insert_data_Name, Edit_Text_Insert_data_Desp;
    Button Button_Inert_Data;
    DatabaseReference reference;
    DatabaseReference reference_User;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UserID;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        Edit_Text_Insert_data_Name = findViewById(R.id.Edit_Text_Insert_data_Name);
        Edit_Text_Insert_data_Desp = findViewById(R.id.Edit_Text_Insert_data_Desp);
        Button_Inert_Data = findViewById(R.id.Button_Inert_Data);
        menu_opener_image = findViewById(R.id.menu_opener_image);
        navigationView = findViewById(R.id.navigation_view);
//        navigationView();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            Toast.makeText(this, "Please Login To Contribute", Toast.LENGTH_SHORT).show();
            Intent intent3 = new Intent(getApplicationContext(), Splash_Login.class);
            startActivity(intent3);
        }else{
            user = FirebaseAuth.getInstance().getCurrentUser();
            reference_User = FirebaseDatabase.getInstance().getReference("Users");
            UserID = user.getUid();
        }
        reference = FirebaseDatabase.getInstance().getReference().child("Tips And Tricks");
        Button_Inert_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertTipsAndTricksData();
            }
        });

    }

    private void InsertTipsAndTricksData() {
        reference_User.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RigesterUser UserProfile = snapshot.getValue(RigesterUser.class);
                if (UserProfile != null) {
                    String FullName = UserProfile.Fullname;
                    String name = Edit_Text_Insert_data_Name.getText().toString();
                    String content = Edit_Text_Insert_data_Desp.getText().toString();
//                    // Add timeStamp
                    Map map = new HashMap();
                    map.put("timestamp", ServerValue.TIMESTAMP);
                    reference_User.child(UserID).updateChildren(map);

                    Insert_Data_Tips_Tricks insert_data_tips_tricks = new Insert_Data_Tips_Tricks(name, content, FullName);
                    reference.push().setValue(insert_data_tips_tricks).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Insert_Data.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                            Edit_Text_Insert_data_Name.setText("");
                            Edit_Text_Insert_data_Desp.setText("");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Insert_Data.this, "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
        reference = FirebaseDatabase.getInstance().getReference().child("Tips And Tricks");
        Button_Inert_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertTipsAndTricksData();
            }
        });



    }


//    private void navigationView() {
//
//        navigationView.bringToFront();
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setCheckedItem(R.id.Navigation_bar_item_Home);
//        menu_opener_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
////        animateNavigationDrawer();
//
//
//    }

//    private void animateNavigationDrawer() {
//        //Add any color or remove it to use the default one!
//        //To make it transparent use Color.Transparent in side setScrimColor();
//        drawerLayout.setScrimColor(getResources().getColor(R.color.dark_red));
//        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//
//                // Scale the View based on current slide offset
//                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
//                final float offsetScale = 1 - diffScaledOffset;
//                contentView.setScaleX(offsetScale);
//                contentView.setScaleY(offsetScale);
//
//                // Translate the View, accounting for the scaled width
//                final float xOffset = drawerView.getWidth() * slideOffset;
//                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
//                final float xTranslation = xOffset - xOffsetDiff;
//                contentView.setTranslationX(xTranslation);
//            }
//        });
//
//    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Navigation_bar_item_Home:
                StyleableToast.makeText(getApplicationContext(), "You Are Already On this Activity", Toast.LENGTH_SHORT, R.style.OnActivity).show();
                break;
            case R.id.Navigation_bar_item_Tips:
                Intent intent1 = new Intent(getApplicationContext(), Tips.class);
                startActivity(intent1);
                break;
            case R.id.Navigation_bar_item_login:
                Intent intent2 = new Intent(getApplicationContext(), Splash_Login.class);
                startActivity(intent2);
                break;
            case R.id.Navigation_bar_item_logout:
                mAuth = FirebaseAuth.getInstance();
                FirebaseAuth.getInstance().signOut();
                StyleableToast.makeText(getApplicationContext(), "You Successfully Logged out", Toast.LENGTH_SHORT, R.style.OnActivity).show();
                Intent intent3 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.Navigation_bar_item_Profile:
                Intent intent4 = new Intent(getApplicationContext(), Profile.class);
                startActivity(intent4);
                break;

            case R.id.Navigation_bar_item_Insert_Data_Screen:
                Intent intent8 = new Intent(getApplicationContext(), Insert_Data.class);
                startActivity(intent8);
                break;
            case R.id.Navigation_bar_item_Share:
                Intent intent9 = new Intent(Intent.ACTION_SEND);
                intent9.setType("text/plain");
                intent9.putExtra(Intent.EXTRA_SUBJECT, "Check Out This New Recipes App");
                intent9.putExtra(Intent.EXTRA_SUBJECT, "Don't Forget to Give Star");
                intent9.putExtra(Intent.EXTRA_SUBJECT, "Source Code:https://github.com/vishalmakhiijani/food-recipe-android-app");
                startActivity(Intent.createChooser(intent9, "Share Via:"));

        }
        return true;
    }
}