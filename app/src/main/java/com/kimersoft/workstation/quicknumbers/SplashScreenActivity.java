package com.kimersoft.workstation.quicknumbers;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.MobileAds;

public class SplashScreenActivity extends AppCompatActivity {

    private int splashScreenDuration = 3000;
    private int screenDisplayTimeAfterAnimation = 1000;
    private int splashScreenTime = splashScreenDuration + screenDisplayTimeAfterAnimation;
    private TextView txtLogo;
    private TextView txtSecondLineLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // AdMob App ID: ca-app-pub-5901446691645946~9995286049
        MobileAds.initialize(this, "ca-app-pub-5901446691645946~9995286049");

        txtLogo = (TextView) findViewById(R.id.txtLogo);
        txtSecondLineLogo = (TextView) findViewById(R.id.txtSecondLineLogo);

        // full screen on device
        makeFullscreen();
        // change default font
        setCustomFonts();
        // make a nice animation effect from not visible to visible screen (splash screen)
        startSplashScreen();

        /* This will open new activity after a specific amount of time (Splash screen) */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }, splashScreenTime);
    }

    private void setCustomFonts() {
        // set specific font from directory Assets
        Typeface gooddp__Font = Typeface.createFromAsset(getAssets(), "fonts/GOODDP__.TTF");
        Typeface comic_andyFont = Typeface.createFromAsset(getAssets(), "fonts/comic_andy.ttf");
        // set custom fonts
        txtLogo.setTypeface(gooddp__Font);
        txtSecondLineLogo.setTypeface(comic_andyFont);
    }

    private void startSplashScreen() {
        LinearLayout secondLogo = (LinearLayout) findViewById(R.id.secondLayout);
        ImageView imgLogo = (ImageView) findViewById(R.id.imvLogo);
        // Make the objects startSplashScreen
        ObjectAnimator anim = ObjectAnimator.ofFloat(imgLogo, "alpha", 0f, 1f);
        anim.setDuration(splashScreenDuration); // time in seconds
        anim.start();

        ObjectAnimator anim1 = ObjectAnimator.ofFloat(txtSecondLineLogo, "alpha", 0f, 1f);
        anim1.setDuration(splashScreenDuration); // time in seconds
        anim1.start();

        ObjectAnimator anim2 = ObjectAnimator.ofFloat(txtLogo, "alpha", 0f, 1f);
        anim2.setDuration(splashScreenDuration); // time in seconds
        anim2.start();

        ObjectAnimator anim3 = ObjectAnimator.ofFloat(secondLogo, "alpha", 0f, 1f);
        anim3.setDuration(splashScreenDuration); // time in seconds
        anim3.start();
    }

    /* Hide UI action bar and make the app fullscreen */
    private void makeFullscreen() {
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
}
