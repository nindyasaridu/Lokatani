package com.example.nindyasaridu.lokataniapps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (authenticate()) {
                    Toast.makeText(SplashScreenActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SplashScreenActivity.this, "Anda belum login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);
    }

    private boolean authenticate() {
        return false;
    }
}
