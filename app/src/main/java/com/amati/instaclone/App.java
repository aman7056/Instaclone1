package com.amati.instaclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("HL2DHTBD3Pg8gy8uHrMBAreyYpyX2DzkiUhPVhfm")
                // if defined
                .clientKey("RkYcVmo7J0JyBNr1mbRJKohX4xkOe0LjEpbDhtWm")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
