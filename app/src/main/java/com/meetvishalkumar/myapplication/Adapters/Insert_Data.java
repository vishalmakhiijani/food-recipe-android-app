package com.meetvishalkumar.myapplication.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.meetvishalkumar.myapplication.MainActivity;
import com.meetvishalkumar.myapplication.Models.Insert_Data_Tips_Tricks;
import com.meetvishalkumar.myapplication.Profile;
import com.meetvishalkumar.myapplication.R;
import com.meetvishalkumar.myapplication.Splash_Login;
import com.meetvishalkumar.myapplication.Tips;
import com.meetvishalkumar.myapplication.meal_planner;

public class Insert_Data extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu_opener_image;
    LinearLayout contentView;
    EditText Edit_Text_Insert_data_Name, Edit_Text_Insert_data_Desp;
    Button Button_Inert_Data;
    DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        Edit_Text_Insert_data_Name = findViewById(R.id.Edit_Text_Insert_data_Name);
        Edit_Text_Insert_data_Desp = findViewById(R.id.Edit_Text_Insert_data_Desp);
        Button_Inert_Data = findViewById(R.id.Button_Inert_Data);

        reference = FirebaseDatabase.getInstance().getReference().child("Tips And Tricks");
        Button_Inert_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertTipsAndTricksData();
            }
        });

    }

    private void InsertTipsAndTricksData() {
        String name = Edit_Text_Insert_data_Name.getText().toString();
        String content = Edit_Text_Insert_data_Desp.getText().toString();
        Insert_Data_Tips_Tricks insert_data_tips_tricks = new Insert_Data_Tips_Tricks(name, content);
        reference.push().setValue(insert_data_tips_tricks);

    }

    //        Navigation Drawer Setting Start
    private void navigationView() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Navigation_bar_item_Home);
        menu_opener_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        animateNavigationDrawer();


    }

    private void animateNavigationDrawer() {
        //Add any color or remove it to use the default one!
        //To make it transparent use Color.Transparent in side setScrimColor();
        drawerLayout.setScrimColor(getResources().getColor(R.color.dark_red));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

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
                break;
            case R.id.Navigation_bar_item_Meal:
                Intent intent = new Intent(getApplicationContext(), meal_planner.class);
                startActivity(intent);
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

        }
        return true;
    }

    //        Navigation Drawer Setting End
}