package com.meetvishalkumar.myapplication.Listeners;

import com.meetvishalkumar.myapplication.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipesListener {
    void didFetch(List<SimilarRecipeResponse> response, String message);

    void didError(String message);
}
