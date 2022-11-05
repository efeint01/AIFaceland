package com.app.thispersondoesnotexist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.app.thispersondoesnotexist.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSplash();
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.startBtn.setOnClickListener(view1 -> {
            editor.putBoolean("splash",true).apply();
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        });
    }

    private void checkSplash() {
        sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        boolean isSplash = sharedPreferences.getBoolean("splash",false);
        if (isSplash) {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
        }
    }

}