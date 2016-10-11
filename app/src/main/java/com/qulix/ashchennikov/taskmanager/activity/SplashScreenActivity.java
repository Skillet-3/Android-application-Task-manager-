package com.qulix.ashchennikov.taskmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.qulix.ashchennikov.taskmanager.R;

/**
 * Splash screen
 */
public class SplashScreenActivity extends Activity {

    private static long SPLASH_TIME_OUT_MS = 2 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (!isFinishing()){
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT_MS);
    }
}
