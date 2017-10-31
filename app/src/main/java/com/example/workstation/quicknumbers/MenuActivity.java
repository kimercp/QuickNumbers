package com.example.workstation.quicknumbers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private Typeface typeface;
    private TextView txtPoints;
    private TextView txtPointsNumber;
    private TextView txtHome;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtPointsNumber = (TextView) findViewById(R.id.txtPointsNumber);
        txtHome = (TextView) findViewById(R.id.txtHome);


        typeface = Typeface.createFromAsset(getAssets(), "fonts/comic_andy.ttf");
        txtPoints.setTypeface(typeface);
        txtHome.setTypeface(typeface);
        txtPointsNumber.setTypeface(typeface);
        txtPointsNumber.setText(Integer.toString(points));

        MakeFullscreen();
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

    /* This method will open new activity depend from chosen option */
    public void GameActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
