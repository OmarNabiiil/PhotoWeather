package com.robusta.photoweather.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.robusta.photoweather.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                openMainActivity();
            }
        }, 3000);
    }

    private void openMainActivity() {
        Intent intent = new Intent(SplashActivity.this, HistoryActivity.class);

        startActivity(intent);
        finish();
    }
}
