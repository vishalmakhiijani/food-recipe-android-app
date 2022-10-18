package com.meetvishalkumar.myapplication;

import android.content.Context;

import com.meetvishalkumar.myapplication.Listeners.InstructionsListener;
import com.meetvishalkumar.myapplication.Listeners.RandomRecipesResponseListener;
import com.meetvishalkumar.myapplication.Listeners.RecipeDetailsListener;
import com.meetvishalkumar.myapplication.Listeners.SimilarRecipesListener;
import com.meetvishalkumar.myapplication.Models.InstructionsResponse;
import com.meetvishalkumar.myapplication.Models.RandomRecipeApiResponse;
import com.meetvishalkumar.myapplication.Models.RecipeDetailsResponse;
import com.meetvishalkumar.myapplication.Models.SimilarRecipeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipes(RandomRecipesResponseListener listener, List<String> tags) {
        CallRandomRecipes callRandomRecipes = retrofit.create(CallRandomRecipes.class);
        Call<RandomRecipeApiResponse> Call = callRandomRecipes.callRandomRecipe(context.getString(R.string.api_key), "50", tags);
        Call.enqueue(new Callback<RandomRecipeApiResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResponse> call, Response<RandomRecipeApiResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getRecipeDetials(RecipeDetailsListener listener, int id) {
        CallRecipeDetails callRecipeDetails = retrofit.create(CallRecipeDetails.class);
        Call<RecipeDetailsResponse> call = callRecipeDetails.CallRecipeDetails(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<RecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RecipeDetailsResponse> call, Response<RecipeDetailsResponse> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());

            }
        });
    }

    public void getSimilarRecipes(SimilarRecipesListener listener, int id) {
        CallSimilarRecipes callSimilarRecipes = retrofit.create(CallSimilarRecipes.class);
        Call<List<SimilarRecipeResponse>> call = callSimilarRecipes.CallSimilarRecipe(id, "5", context.getString(R.string.api_key));
        call.enqueue(new Callback<List<SimilarRecipeResponse>>() {
            @Override
            public void onResponse(Call<List<SimilarRecipeResponse>> call, Response<List<SimilarRecipeResponse>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<SimilarRecipeResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getInstructions(InstructionsListener listener, int id) {
        CallInstrutions callInstrutions = retrofit.create(CallInstrutions.class);
        Call<List<InstructionsResponse>> call = callInstrutions.callInstructions(id, context.getString(R.string.api_key));
        call.enqueue(new Callback<List<InstructionsResponse>>() {
            @Override
            public void onResponse(Call<List<InstructionsResponse>> call, Response<List<InstructionsResponse>> response) {
                if (!response.isSuccessful()) {
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<List<InstructionsResponse>> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    private interface CallRandomRecipes {
        @GET("recipes/random")
        Call<RandomRecipeApiResponse> callRandomRecipe(
                @Query("apiKey") String apiKey,
                @Query("number") String number,
                @Query("tags") List<String> tags
        );
    }

    private interface CallRecipeDetails {
        @GET("recipes/{id}/information")
        Call<RecipeDetailsResponse> CallRecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallSimilarRecipes {
        @GET("recipes/{id}/similar")
        Call<List<SimilarRecipeResponse>> CallSimilarRecipe(
                @Path("id") int id,
                @Query("number") String number,
                @Query("apiKey") String apiKey
        );
    }

    private interface CallInstrutions {
        @GET("recipes/{id}/analyzedInstructions")
        Call<List<InstructionsResponse>> callInstructions(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
