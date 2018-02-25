package com.kimersoft.workstation.quicknumbers;

import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    // array with toys status, true is bought
    private boolean[] toysArray;

    // advertisement
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton btnBack = (ImageButton) findViewById(R.id.imgbBack);
        btnBack.setOnClickListener(this);

        // array of toys true if bought false if not bought
        toysArray = new boolean[34];

        // full screen on device
        makeFullscreen();

        // load the bought toys array and display those with true value
        getSharedPreferencesData();

        // draw bought toys, check array, true means bought, false replace image with red cross
        drawToysInHome();

        // advertisement AdMob
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().tagForChildDirectedTreatment(true).build();
        mAdView.loadAd(adRequest);
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
        // variables to use shared preferences
        SharedPreferences sharedpreferences;
        String mypreference = "mypreference";
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // load data to toys array if not exist return false
        for (int positionInToysArray = 0; positionInToysArray < toysArray.length; positionInToysArray++) {
            toysArray[positionInToysArray] = sharedpreferences.getBoolean(Integer.toString(positionInToysArray), false);
        }
    }

    private void drawToysInHome() {
        ViewGroup homeItemsToysViewGroup = (ViewGroup) findViewById(R.id.HomeItems);
        // loop to check if toy has been already bought and saved in array as true value
        for (int positionInToysArray = 0; positionInToysArray < toysArray.length; positionInToysArray++) {
            // check if array has false value and replace the toy's image
            if (toysArray[positionInToysArray] == false) {
                // check every child in home items group
                for (int i = 0; i < homeItemsToysViewGroup.getChildCount(); i++) {
                    ViewGroup toyLineViewGroup = (ViewGroup) homeItemsToysViewGroup.getChildAt(i);
                    // check every child's tag if equal with array's position
                    // If Yes replace the image on red cross, otherwise leave original image
                    for (int k = 0; k < toyLineViewGroup.getChildCount(); k++) {
                        View specificToyView = toyLineViewGroup.getChildAt(k);
                        String toyTag = specificToyView.getTag().toString();
                        String positionArray = Integer.toString(positionInToysArray);
                        if (positionArray.equals(toyTag)) {
                            // casting view in purpose to replace image src
                            ImageView imageOfToy = (ImageView) specificToyView;
                            imageOfToy.setImageResource(R.drawable.cross);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgbBack:
                finish();
                break;
        }
    }
}
