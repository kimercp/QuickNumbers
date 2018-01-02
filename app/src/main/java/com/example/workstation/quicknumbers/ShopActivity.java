package com.example.workstation.quicknumbers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    // number of user points
    private int points;

    // shared preferences to get and save points
    private SharedPreferences sharedpreferences;
    private String mypreference = "mypreference";
    // user points are saved in shared preferences
    private String pointsKeySharedPreference = "pointsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // change the font once in all activity text views
        overrideFonts(this, findViewById(R.id.ShopItems));

        // full screen on device
        makeFullscreen();

        // load actual number of user points
        getSharedPreferencesData();

        // display toys only available to buy, if the user have enough points to buy a toy
        setToysNotAvailableToBuyAsNotActive((ViewGroup) findViewById(R.id.ShopItems));

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

    public static void overrideFonts(final Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                // take number of children from parent if view group
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    // run the method again to check the children of actual taken view
                    overrideFonts(context, child);
                }
            }
            // check if view is not button, do nothing in case of button
            if (v instanceof Button) {}
            else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/comic_andy.ttf"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setToysNotAvailableToBuyAsNotActive(final ViewGroup vg){
        // loop to check every child in ShopItems component
        for (int i=0; i < vg.getChildCount(); i++ ) {
            // set net view group, because linear layout has 4 children
            ViewGroup v = (ViewGroup) vg.getChildAt(i);
            // the view with number of points needed to buy a toy (price for a toy)
            View vToyPrice = v.getChildAt(2);
            // casting view on TextView
            TextView tvToyPrice = (TextView) vToyPrice;
            Integer toyPrice = Integer.parseInt(tvToyPrice.getText().toString());
            if (toyPrice > points) v.getChildAt(3).setVisibility(View.INVISIBLE);
        }
    }
    
    // 1. wez nazwe obrazka, usun tekst, od cyfry odjac 1 i zapisz do array jako true

    private void getSharedPreferencesData() {
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device
        if (sharedpreferences.contains(pointsKeySharedPreference)) {
            points = sharedpreferences.getInt(pointsKeySharedPreference, 0);
        } else points = 0;
    }

    // save points when game finish
    private void savePointsInSharedPreferences(int pointsToSaveInSharedPreferences) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(pointsKeySharedPreference, pointsToSaveInSharedPreferences);
        editor.commit();
    }
}
