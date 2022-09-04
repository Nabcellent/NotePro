package com.example.notepro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }, 2000);
    }
}

//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