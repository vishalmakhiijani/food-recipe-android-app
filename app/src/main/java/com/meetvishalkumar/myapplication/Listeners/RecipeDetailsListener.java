package com.meetvishalkumar.myapplication.Listeners;

import com.meetvishalkumar.myapplication.Models.RecipeDetailsResponse;

public interface RecipeDetailsListener {
    void didFetch(RecipeDetailsResponse response, String message);

    void didError(String message);
}
