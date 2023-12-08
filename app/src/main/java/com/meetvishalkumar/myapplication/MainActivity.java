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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.meetvishalkumar.myapplication.Adapters.RandomRecipeAdapter;
import com.meetvishalkumar.myapplication.Listeners.RandomRecipesResponseListener;
import com.meetvishalkumar.myapplication.Listeners.RecipeClickListener;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.Loading_Animation.RecipeLoading;
import com.meetvishalkumar.myapplication.UserAccount.Profile;
import com.meetvishalkumar.myapplication.UserAccount.Splash_Login;
import com.meetvishalkumar.myapplication.Models.RandomRecipeApiResponse;

import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //
    static final float END_SCALE = 0.7f;
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(MainActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", id));
        }
    };
    private FirebaseAnalytics mFirebaseAnalytics;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RequestManager manager;
    RandomRecipeAdapter randomRecipeAdapter;
    RecyclerView RecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
//    Loading
    private RecipeLoading recipeLoading;
    private final RandomRecipesResponseListener RandomRecipesResponseListener = new RandomRecipesResponseListener() {
        @Override
        public void didFetch(RandomRecipeApiResponse response, String message) {

            RecyclerView = findViewById(R.id.recycler_View);
            RecyclerView.setHasFixedSize(true);
            RecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(MainActivity.this, response.recipes, recipeClickListener);
            RecyclerView.setAdapter(randomRecipeAdapter);
            //to hide loading
            recipeLoading.hide();
            recipeLoading.cancel();
            recipeLoading.dismiss();
            recipeLoading.hide();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
        }
    };
    Spinner spinner;
    List<String> tags = new ArrayList<>();
    private final AdapterView.OnItemSelectedListener spinnerSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            manager.getRandomRecipes(RandomRecipesResponseListener, tags);
            if (checkInternet()) {
                //        TO SHow loading
                recipeLoading.show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    SearchView searchView;
    ImageView menu_opener_image;
    LinearLayout contentView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        //Check is internet connected or not
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(MainActivity.this);
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }

        searchView = findViewById(R.id.SearchView_Home);
        menu_opener_image = findViewById(R.id.menu_opener_image);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        contentView = findViewById(R.id.content);
        spinner = findViewById(R.id.spinner_tags);
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        //          Calling Loading
        recipeLoading = new RecipeLoading(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        navigationView();
        //        Refresh Activity Code



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                manager.getRandomRecipes(RandomRecipesResponseListener, tags);
                //        TO SHow loading
                recipeLoading.show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this, R.array.tags, R.layout.spinner_txt
        );
        arrayAdapter.setDropDownViewResource(R.layout.spinner_innerr_txt);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedListener);

        manager = new RequestManager(this);
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
                StyleableToast.makeText(getApplicationContext(), "You Are Already On this Activity", Toast.LENGTH_SHORT, R.style.OnActivity).show();
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
                intent9.putExtra(Intent.EXTRA_SUBJECT, "Source Code:https://github.com/vishalkumar456/food-recipe-android-app");
                startActivity(Intent.createChooser(intent9, "Share Via:"));

        }
        return true;
    }

    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            // Check if user triggered a refresh:
//            case R.id.menu_refresh:
//                Log.i(LOG_TAG, "Refresh menu item selected");
//
//                // Signal SwipeRefreshLayout to start the progress indicator
//                mySwipeRefreshLayout.setRefreshing(true);
//
//                // Start the refresh background task.
//                // This method calls setRefreshing(false) when it's finished.
//                myUpdateOperation();
//
//                return true;
//        }
//
//        // User didn't trigger a refresh, let the superclass handle this action
//        return super.onOptionsItemSelected(item);
//    }
    /*
     * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
     * performs a swipe-to-refresh gesture.
     */
    /*
     * Listen for option item selections so that we receive a notification
     * when the user requests a refresh by selecting the refresh action bar item.
     */



}