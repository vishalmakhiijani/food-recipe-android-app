package com.meetvishalkumar.myapplication.Listeners;

import com.meetvishalkumar.myapplication.Models.RandomRecipeApiResponse;

public interface RandomRecipesResponseListener {
    void didFetch(RandomRecipeApiResponse response, String message);

    void didError(String message);
}
