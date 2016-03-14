package com.example.kevinjiang.week4project;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Kevin Jiang.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(getApplicationContext(), "IU2D1ej4N8QcYVLhzYLU5Kpmbz7j0N9KBauQ8a1A", "w6j6KHFyEi1LLCymib2XcKXr9D2LmsZ7qFVUhE6P");
    }
}