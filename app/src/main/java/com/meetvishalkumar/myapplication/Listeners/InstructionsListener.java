package com.meetvishalkumar.myapplication.Listeners;

import com.meetvishalkumar.myapplication.Models.InstructionsResponse;

import java.util.List;

public interface InstructionsListener {
    void didFetch(List<InstructionsResponse> response, String message);

    void didError(String message);
}
