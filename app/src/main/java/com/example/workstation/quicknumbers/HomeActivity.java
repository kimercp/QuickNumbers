package com.example.workstation.quicknumbers;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    // number of points
    private Integer points;

    // variables to use shared preferences
    private SharedPreferences sharedpreferences;
    private String mypreference = "mypreference";
    // user points are saved in shared preferences
    private String pointsKeySharedPreference = "pointsKey";

    // array with toys status, true is bought
    private boolean[] toysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // array of toys true if bought false if not bought
        toysArray = new boolean[34];

        // full screen on device
        makeFullscreen();

        // load the bought toys array and display those with true value
        getSharedPreferencesData();
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

    private void getSharedPreferencesData() {
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device if not exist then return 0
        points = sharedpreferences.getInt(pointsKeySharedPreference, 0);

        // load data to toys array if not exist return false
        for (int positionInToysArray = 0; positionInToysArray < toysArray.length; positionInToysArray++) {
            toysArray[positionInToysArray] = sharedpreferences.getBoolean(Integer.toString(positionInToysArray), false);
        }
    }
}
