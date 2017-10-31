package com.example.workstation.quicknumbers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        MakeFullscreen();
        Countdown(2500);
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
