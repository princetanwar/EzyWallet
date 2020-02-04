package com.loveinshayari.ezywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.loveinshayari.ezywallet.activitys.Main2Activity;
import com.loveinshayari.ezywallet.activitys.MainActivity;

public class SplashScreen extends AppCompatActivity {
    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ProgressBar progressBar = findViewById(R.id.progressBarSplash);

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                a = a+1;
                progressBar.setProgress(a);
            }

            @Override
            public void onFinish() {
                SharedPreferences sharedPreferences = getSharedPreferences("com.loveinshayari.ezywallet_login_status",MODE_PRIVATE);
                String check = sharedPreferences.getString("login_status","off");
                if (check.equalsIgnoreCase("on")){
                    startActivity(new Intent(SplashScreen.this, Main2Activity.class));
                }
                else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                finish();
            }
        }.start();
    }
}
