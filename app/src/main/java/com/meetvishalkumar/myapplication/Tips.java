package com.meetvishalkumar.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.meetvishalkumar.myapplication.Adapters.Get_Tips_Tricks;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.Loading_Animation.RecipeLoading;
import com.meetvishalkumar.myapplication.UserAccount.Profile;
import com.meetvishalkumar.myapplication.UserAccount.Splash_Login;
import com.meetvishalkumar.myapplication.Models.Show_Data_Tips_Tricks;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class Tips extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private final List<Show_Data_Tips_Tricks> list = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu_opener_image;
    LinearLayout contentView;
    RecyclerView recyclerView;
    Adapter get_Tips_Tricks;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference referencee;
    private String UserID;
    private FirebaseAnalytics mFirebaseAnalytics;

    private RecipeLoading recipeLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }
        menu_opener_image = findViewById(R.id.menu_opener_image);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        contentView = findViewById(R.id.content);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //          Calling Loading
        recipeLoading = new RecipeLoading(this);
        //        TO SHow loading
        recipeLoading.show();


        final RecyclerView recyclerView = findViewById(R.id.Show_Tips_Tricks_Recycler_View);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Tips.this));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot tip : snapshot.child("Tips And Tricks").getChildren()) {
                    if (tip.hasChild("name") && tip.hasChild("content")) {

                        final String getName = tip.child("name").getValue(String.class);
                        final String getContent = tip.child("content").getValue(String.class);
                        Show_Data_Tips_Tricks show_data_tips_tricks = new Show_Data_Tips_Tricks(getName, getContent);
                        list.add(show_data_tips_tricks);
                        //to hide loading
                        recipeLoading.hide();
                        recipeLoading.cancel();
                        recipeLoading.dismiss();
                        recipeLoading.hide();
                    }

                }
                recyclerView.setAdapter(new Get_Tips_Tricks(list, Tips.this));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navigationView();
    }

    //        Navigation Drawer Setting Start
    private void navigationView() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.Navigation_bar_item_Tips);
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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.Navigation_bar_item_Meal:
                Intent intent1 = new Intent(getApplicationContext(), meal_planner.class);
                startActivity(intent1);
                break;
            case R.id.Navigation_bar_item_Tips:
                StyleableToast.makeText(getApplicationContext(), "You Are Already On this Activity", Toast.LENGTH_SHORT, R.style.OnActivity).show();
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

        }
        return true;
    }
    //        Navigation Drawer Setting End
    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}