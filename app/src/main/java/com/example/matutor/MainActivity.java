package com.example.matutor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.matutor.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 2500; //milliseconds
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ActivityMainBinding binding;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // removes status bar
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        anim = AnimationUtils.loadAnimation(this, R.anim.slide_top);
        binding.matutorLogo.setAnimation(anim);

        new Handler().postDelayed(() -> {
            if (auth.getCurrentUser() != null) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}