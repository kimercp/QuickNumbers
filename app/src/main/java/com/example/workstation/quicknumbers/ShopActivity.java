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

import java.lang.reflect.Array;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    // number of user points
    private int points;

    // shared preferences to get and save points
    private SharedPreferences sharedpreferences;
    private String mypreference = "mypreference";
    // user points are saved in shared preferences
    private String pointsKeySharedPreference = "pointsKey";

    // array with toys status, true is bought
    private boolean[] toysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // full screen on device
        makeFullscreen();
        
        // change the font once in all activity text views
        overrideFonts(this, findViewById(R.id.ShopItems));

        // get actual number of user points
        getSharedPreferencesData();

        // display toys only available to buy, if the user have enough points to buy a toy
        setToysNotAvailableToBuyAsNotActive((ViewGroup) findViewById(R.id.ShopItems));
        
        /* Buttons */
        Button toy0 = (Button) findViewById(R.id.btnToy0);
        Button toy1 = (Button) findViewById(R.id.btnToy1);
        Button toy2 = (Button) findViewById(R.id.btnToy2);
        Button toy3 = (Button) findViewById(R.id.btnToy3);
        Button toy4 = (Button) findViewById(R.id.btnToy4);
        Button toy5 = (Button) findViewById(R.id.btnToy5);
        Button toy6 = (Button) findViewById(R.id.btnToy6);
        Button toy7 = (Button) findViewById(R.id.btnToy7);
        Button toy8 = (Button) findViewById(R.id.btnToy8);
        Button toy9 = (Button) findViewById(R.id.btnToy9);
        Button toy10 = (Button) findViewById(R.id.btnToy10);
        Button toy11 = (Button) findViewById(R.id.btnToy11);
        Button toy12 = (Button) findViewById(R.id.btnToy12);
        Button toy13 = (Button) findViewById(R.id.btnToy13);
        Button toy14 = (Button) findViewById(R.id.btnToy14);
        Button toy15 = (Button) findViewById(R.id.btnToy15);
        Button toy16 = (Button) findViewById(R.id.btnToy16);
        Button toy17 = (Button) findViewById(R.id.btnToy17);
        Button toy18 = (Button) findViewById(R.id.btnToy18);
        Button toy19 = (Button) findViewById(R.id.btnToy19);
        Button toy20 = (Button) findViewById(R.id.btnToy20);
        Button toy21 = (Button) findViewById(R.id.btnToy21);
        Button toy22 = (Button) findViewById(R.id.btnToy22);
        Button toy23 = (Button) findViewById(R.id.btnToy23);
        Button toy24 = (Button) findViewById(R.id.btnToy24);
        Button toy25 = (Button) findViewById(R.id.btnToy25);
        Button toy26 = (Button) findViewById(R.id.btnToy26);
        Button toy27 = (Button) findViewById(R.id.btnToy27);
        Button toy28 = (Button) findViewById(R.id.btnToy28);
        Button toy29 = (Button) findViewById(R.id.btnToy29);
        Button toy30 = (Button) findViewById(R.id.btnToy30);
        Button toy31 = (Button) findViewById(R.id.btnToy31);
        Button toy32 = (Button) findViewById(R.id.btnToy32);
        Button toy33 = (Button) findViewById(R.id.btnToy33);
        /* Set OnClickListener for each button */
        toy0.setOnClickListener(this);
        toy1.setOnClickListener(this);
        toy2.setOnClickListener(this);
        toy3.setOnClickListener(this);
        toy4.setOnClickListener(this);
        toy5.setOnClickListener(this);
        toy6.setOnClickListener(this);
        toy7.setOnClickListener(this);
        toy8.setOnClickListener(this);
        toy9.setOnClickListener(this);
        toy10.setOnClickListener(this);
        toy11.setOnClickListener(this);
        toy12.setOnClickListener(this);
        toy13.setOnClickListener(this);
        toy14.setOnClickListener(this);
        toy15.setOnClickListener(this);
        toy16.setOnClickListener(this);
        toy17.setOnClickListener(this);
        toy18.setOnClickListener(this);
        toy19.setOnClickListener(this);
        toy20.setOnClickListener(this);
        toy21.setOnClickListener(this);
        toy22.setOnClickListener(this);
        toy23.setOnClickListener(this);
        toy24.setOnClickListener(this);
        toy25.setOnClickListener(this);
        toy26.setOnClickListener(this);
        toy27.setOnClickListener(this);
        toy28.setOnClickListener(this);
        toy29.setOnClickListener(this);
        toy30.setOnClickListener(this);
        toy31.setOnClickListener(this);
        toy32.setOnClickListener(this);
        toy33.setOnClickListener(this);

        // array of toys true if bought false if not bought
        toysArray = new boolean[34];

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

    /* Change the font once in all activity text views */
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

    /* get actual number of user points from shared preferences saved on android device */
    private void getSharedPreferencesData() {
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device
        if (sharedpreferences.contains(pointsKeySharedPreference)) {
            points = sharedpreferences.getInt(pointsKeySharedPreference, 0);
        } else points = 0;
    }

    /* display toys only available to buy, if the user have enough points to buy a toy */
    public void setToysNotAvailableToBuyAsNotActive(final ViewGroup vg){
        // loop to check every child in ShopItems component
        for (int i=0; i < vg.getChildCount(); i++ ) {
            // set net view group, because linear layout has 4 children
            ViewGroup v = (ViewGroup) vg.getChildAt(i);
            // the view with number of points needed to buy a toy (price for a toy)
            View vToyPrice = v.getChildAt(2);
            // casting view on TextView
            TextView tvToyPrice = (TextView) vToyPrice;
            // get the number from text view
            Integer toyPrice = Integer.parseInt(tvToyPrice.getText().toString());
            // if less hide BUY button
            if (toyPrice > points) v.getChildAt(3).setVisibility(View.INVISIBLE);
        }
    }
    
    // 1. wez nazwe obrazka, usun tekst, od cyfry odjac 1 i zapisz do array jako true

    /* save points when game finish */
    private void savePointsInSharedPreferences(int pointsToSaveInSharedPreferences) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(pointsKeySharedPreference, pointsToSaveInSharedPreferences);

        // add array to shared preferences

        editor.commit();
    }

    @Override
    public void onClick(View viewButton) {
        // find parent of button passed as an argument
        View parentOfButton = (View) viewButton.getParent();
        // find parent of parent of button
        ViewGroup firstParent = (ViewGroup) parentOfButton.getParent();
        // get third child which is text view with price of toy in points
        TextView textPrice = (TextView) firstParent.getChildAt(2);
        Integer toyTextPrice = Integer.parseInt(textPrice.getText().toString());

        // get button tag
        Button btn = (Button) findViewById(viewButton.getId());
        String btnTag = (String) btn.getTag();
        Integer arrayToyPosition = Integer.parseInt(btnTag);

        // set specific toy as true, when toy has been purchased
        toysArray[arrayToyPosition] = true;

        // subtract points
        points = points - toyTextPrice;

        // update shared preferences
        savePointsInSharedPreferences(points);

        // refresh the shop list
        setToysNotAvailableToBuyAsNotActive((ViewGroup) findViewById(R.id.ShopItems));

        Toast.makeText(this, getString(R.string.buyToy)+Integer.toString(toyTextPrice)+getString(R.string.pointsWord), Toast.LENGTH_SHORT).show();
    }
}
