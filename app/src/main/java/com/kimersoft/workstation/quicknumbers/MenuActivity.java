package com.kimersoft.workstation.quicknumbers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtPoints;
    private TextView txtPointsNumber;
    private TextView txtHome;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtPointsNumber = (TextView) findViewById(R.id.txtPointsNumber);
        txtHome = (TextView) findViewById(R.id.txtHome);

        // full screen on device
        makeFullscreen();
        // change default font
        setCustomFonts();

        /* Buttons */
        ImageButton btnPlus = (ImageButton) findViewById(R.id.btnPlus);
        ImageButton btnMinus = (ImageButton) findViewById(R.id.btnMinus);
        ImageButton btnMultiply = (ImageButton) findViewById(R.id.btnMultiply);
        ImageButton btnDivide = (ImageButton) findViewById(R.id.btnDivide);
        ImageButton btnHome = (ImageButton) findViewById(R.id.btnHome);
        ImageButton btnShop = (ImageButton) findViewById(R.id.btnShop);
        ImageButton btnExit = (ImageButton) findViewById(R.id.imgbExit);

        /* Set OnClickListener for each button */
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        btnExit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // get the data from sharedPreferences file
        getSharedPreferencesData();
        // display the actual number of points
        txtPointsNumber.setText(Integer.toString(points));
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

    private void setCustomFonts() {
        Typeface comic_andyFont = Typeface.createFromAsset(getAssets(), "fonts/comic_andy.ttf");
        txtPoints.setTypeface(comic_andyFont);
        txtHome.setTypeface(comic_andyFont);
        txtPointsNumber.setTypeface(comic_andyFont);
    }

    private void getSharedPreferencesData() {
        // variables to use shared preferences
        SharedPreferences sharedpreferences;
        String mypreference = "mypreference";
        // user points are saved in shared preferences
        String pointsKeySharedPreference = "pointsKey";

        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device if not exist then return 0
        points = sharedpreferences.getInt(pointsKeySharedPreference, 0);
    }

    /* This will open new activity depend from chosen button with option */
    @Override
    public void onClick(View v) {
        Intent intent;
        Bundle bundle;

        switch (v.getId()) {
            case R.id.btnPlus:
                intent = new Intent(this, MainActivity.class);
                // passing operator to new activity using bubdle class
                bundle = new Bundle();
                bundle.putString("operator","plus");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btnMinus:
                intent = new Intent(this, MainActivity.class);
                // passing operator to new activity
                bundle = new Bundle();
                bundle.putString("operator","minus");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btnMultiply:
                intent = new Intent(this, MainActivity.class);
                // passing operator to new activity
                bundle = new Bundle();
                bundle.putString("operator","multiply");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btnDivide:
                intent = new Intent(this, MainActivity.class);
                // passing operator to new activity
                bundle = new Bundle();
                bundle.putString("operator","divide");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.btnHome:
                intent = new Intent(this, HomeActivity.class);
                // home items keep in database, internal file or sharedpreferences
                startActivity(intent);
                break;
            case R.id.btnShop:
                intent = new Intent(this, ShopActivity.class);
                // passing operator to new activity
                bundle = new Bundle();
                bundle.putInt("points",points);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imgbExit:
                new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                        .setTitle(R.string.dialog_confirmExit)
                        .setIcon(R.drawable.exit)
                        .setNegativeButton(R.string.dialog_No, null)
                        .setPositiveButton(R.string.dialog_Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }
}
