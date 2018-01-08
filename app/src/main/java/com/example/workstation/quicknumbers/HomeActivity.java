package com.example.workstation.quicknumbers;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private String operator = "";

    private SharedPreferences sharedpreferences;
    private String mypreference = "mypreference";

    // user points are saved in shared preferences
    private String pointsKeySharedPreference = "pointsKey";

    private Integer points;

    // array with toys status, true is bought
    private boolean[] toysArray;
    private Integer arrayToyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        // get the number of points form shared preferences file on device
        if (sharedpreferences.contains(pointsKeySharedPreference)) {
            points = sharedpreferences.getInt(pointsKeySharedPreference, 0);
        } else points = 0;
        for (int positionInToysArray = 0; positionInToysArray < toysArray.length; positionInToysArray++) {
            toysArray[positionInToysArray] = sharedpreferences.getBoolean(Integer.toString(positionInToysArray), false);
            Toast.makeText(this, Boolean.toString(toysArray[positionInToysArray]), Toast.LENGTH_SHORT).show();
        }
    }
}
