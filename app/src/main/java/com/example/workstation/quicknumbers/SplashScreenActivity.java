package com.example.workstation.quicknumbers;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private int splashscreentime = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TextView txtLogo = (TextView) findViewById(R.id.txtLogo);
        TextView txtSecondLineLogo = (TextView) findViewById(R.id.txtSecondLineLogo);
        Typeface gooddp__Font = Typeface.createFromAsset(getAssets(), "fonts/GOODDP__.TTF");
        Typeface comic_andyFont = Typeface.createFromAsset(getAssets(), "fonts/comic_andy.ttf");
        txtLogo.setTypeface(gooddp__Font);
        txtSecondLineLogo.setTypeface(comic_andyFont);
        LinearLayout secondLogo = (LinearLayout) findViewById(R.id.secondLayout);

        MakeFullscreen();

        ImageView imgLogo = (ImageView) findViewById(R.id.imvLogo);
        // Make the object 50% transparent
        ObjectAnimator anim = ObjectAnimator.ofFloat(imgLogo, "alpha", 0f, 1f);
        anim.setDuration(3000); // duration 3 seconds
        anim.start();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(txtSecondLineLogo, "alpha", 0f, 1f);
        anim1.setDuration(3000); // duration 3 seconds
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(txtLogo, "alpha", 0f, 1f);
        anim2.setDuration(3000); // duration 3 seconds
        anim2.start();

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(secondLogo, "alpha", 0f, 1f);
        anim3.setDuration(3000); // duration 3 seconds
        anim3.start();

        //Countdown(2500);
        /* This will open new activity after a specific amount of time (Splash screen) */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }, splashscreentime);
    }

    private void ColorLoop() {

    }

    /* Timer with animation */
    private void Countdown(int miliSeconds) {
        // countdown timer for alarms this is only for test (pozniej to wykasuj jak zrobisz do godzin
        new CountDownTimer(miliSeconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {
                int liczba = (int) millisUntilFinished;
                LinearLayout secondLayout = (LinearLayout) findViewById(R.id.secondLayout);
                secondLayout.setBackgroundColor(Color.argb(liczba/10,00,00,00));
            }

            @Override
            public void onFinish() {
                LinearLayout secondLayout = (LinearLayout) findViewById(R.id.secondLayout);
                secondLayout.setBackgroundColor(Color.argb(00,00,00,00));
            }
        }.start();
    }

    /* Hide UI action bar and make the app fullscreen */
    private void MakeFullscreen() {
        getSupportActionBar().hide();
        // API 19 (Kit Kat)
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        } else {
            if (Build.VERSION.SDK_INT > 10) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    // use this if you want to create login managment system
    /* This method will open new activity after
     clicking on button "Start" */
    public void OpenMainActivity(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
