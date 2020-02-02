package com.zachrawi.todo;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

public class MyApplication extends Application {
    private static final String TAG = "###";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("I7pPikXDup2kkY3AaYMhKAbgLjTXhXCcVoaKvGwk")
            .clientKey("1j3tGBbZABH4AV60bhVwSERksngouW8JE2Unu7fo")
            .server("https://parseapi.back4app.com/")
            .build());

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
