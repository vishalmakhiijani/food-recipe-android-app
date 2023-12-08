package com.meetvishalkumar.myapplication;

import static com.meetvishalkumar.myapplication.R.id.Navigation_bar_item_login;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.meetvishalkumar.myapplication.Adapters.IngredientsAdapter;
import com.meetvishalkumar.myapplication.Adapters.InstructionsAdapter;
import com.meetvishalkumar.myapplication.Adapters.SimilarRecipeAdapter;
import com.meetvishalkumar.myapplication.Listeners.CustomOnClickListener;
import com.meetvishalkumar.myapplication.Listeners.InstructionsListener;
import com.meetvishalkumar.myapplication.Listeners.RecipeClickListener;
import com.meetvishalkumar.myapplication.Listeners.RecipeDetailsListener;
import com.meetvishalkumar.myapplication.Listeners.SimilarRecipesListener;
import com.meetvishalkumar.myapplication.Loading_Animation.NoInternetDiaload;
import com.meetvishalkumar.myapplication.Loading_Animation.RecipeLoading;
import com.meetvishalkumar.myapplication.UserAccount.Profile;
import com.meetvishalkumar.myapplication.UserAccount.Splash_Login;
import com.meetvishalkumar.myapplication.Models.InstructionsResponse;
import com.meetvishalkumar.myapplication.Models.RecipeDetailsResponse;
import com.meetvishalkumar.myapplication.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static final float END_SCALE = 0.7f;
    private final CustomOnClickListener similarOnClickListener = new CustomOnClickListener() {
        @Override
        public void onClick(String text) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
                    .putExtra("id", text));
        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClick(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class).putExtra("id", id));
        }
    };
    int id;
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView TextView_Meal_Name, textView_Meal_Source, textview_meal_Summary, textview_meal_Summary_Expand, textView_meal_servings, textView_meal_ready, textView_meal_price, ready_in, servings, healthy, instructions;
    ImageView ImageView_meal_image, vegeterian;
    RecyclerView recycler_meal_ingrediets, Recycler_meal_similar, Recycler_meal_instructions;
    RequestManager manager;
    IngredientsAdapter ingredientsAdapter;
    private RecipeLoading recipeLoading;
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {

        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            if (!checkInternet()) {
                NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
                noInternetDialoag.setCancelable(false);
                noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                noInternetDialoag.show();
            }
            textView_meal_ready.setText(response.readyInMinutes + " Minutes");
//            textView_meal_price.setText(response.pricePerServing + "$ Per Serving");
            textView_meal_servings.setText(response.servings + " Persons");
            TextView_Meal_Name.setText(response.title);
            textView_Meal_Source.setText(response.sourceName);
            textview_meal_Summary.setText(Html.fromHtml(response.summary));
            Picasso.get().load(response.image).into(ImageView_meal_image);
            recycler_meal_ingrediets.setHasFixedSize(true);
            recycler_meal_ingrediets.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingrediets.setAdapter(ingredientsAdapter);

            //to hide loading
            recipeLoading.hide();
            recipeLoading.cancel();
            recipeLoading.dismiss();
            recipeLoading.hide();
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    SimilarRecipeAdapter similarRecipeAdapter;
    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            Recycler_meal_similar.setHasFixedSize(true);
            Recycler_meal_similar.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            Recycler_meal_similar.setAdapter(similarRecipeAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    LinearLayout Layout_Expand;
    InstructionsAdapter instructionsAdapter;
    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            Recycler_meal_instructions.setHasFixedSize(true);
            Recycler_meal_instructions.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            Recycler_meal_instructions.setAdapter(instructionsAdapter);
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menu_opener_image;
    LinearLayout contentView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        if (!checkInternet()) {
            NoInternetDiaload noInternetDialoag = new NoInternetDiaload(getApplicationContext());
            noInternetDialoag.setCancelable(false);
            noInternetDialoag.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            noInternetDialoag.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            noInternetDialoag.show();
        }
        findViews();
        navigationView();

        Layout_Expand.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetials(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructions(instructionsListener, id);

//        TO SHow loading
        recipeLoading.show();


    }


    private void findViews() {
        textView_meal_ready = findViewById(R.id.textView_meal_ready);
        textView_meal_servings = findViewById(R.id.textView_meal_servings);
        TextView_Meal_Name = findViewById(R.id.TextView_Meal_Name);
        textView_Meal_Source = findViewById(R.id.textView_Meal_Source);
        textview_meal_Summary = findViewById(R.id.textview_meal_Summary);
        ImageView_meal_image = findViewById(R.id.ImageView_meal_image);
        recycler_meal_ingrediets = findViewById(R.id.recycler_meal_ingrediets);
        Recycler_meal_similar = findViewById(R.id.Recycler_meal_similar);
        Layout_Expand = findViewById(R.id.Layout_Expand);
        textview_meal_Summary_Expand = findViewById(R.id.textview_meal_Summary_Expand);
        Recycler_meal_instructions = findViewById(R.id.Recycler_meal_instructions);
        menu_opener_image = findViewById(R.id.menu_opener_image);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        contentView = findViewById(R.id.content);
//          Calling Loading
        recipeLoading = new RecipeLoading(this);
    }

    public void ExpandView(View view) {
        int v = (textview_meal_Summary.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(Layout_Expand, new AutoTransition());
        textview_meal_Summary.setVisibility(v);
    }

    //        Navigation Drawer Setting Start
    private void navigationView() {

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

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
                Intent intent11 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent11);
                break;
            case R.id.Navigation_bar_item_Meal:
                Intent intent = new Intent(getApplicationContext(), meal_planner.class);
                startActivity(intent);
                break;
            case R.id.Navigation_bar_item_Tips:
                Intent intent1 = new Intent(getApplicationContext(), Tips.class);
                startActivity(intent1);
                break;
            case Navigation_bar_item_login:
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
    private boolean checkInternet() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}