package com.example.workstation.quicknumbers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtTimer;
    private TextView txtPoints;
    private TextView txtQuestion;
    private TextView txtAnswer;
    private int firstNumber;
    private int secondNumber;
    private int level = 1;
    private int timeToCountdown = 30000; // in miliseconds (to achieve seconds divide by 1000)
    private int points;
    private String operator = "";
    private SharedPreferences sharedpreferences;
    private String mypreference = "mypreference";
    private String pointsKeySharedPreference = "pointsKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);

        // full screen on device
        MakeFullscreen();
        // change default font
        SetCustomFonts();

        // get the points from sharedPreferences file
        GetSharedPreferencesData();
        // display actual number of points
        txtPoints.setText(getString(R.string.points) +" "+ Integer.toString(points));

        // getting data passed from previous Menu activity
        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        if (!extrasBundle.isEmpty()) {
            operator = extrasBundle.getString("operator");
        }

        /* Buttons */
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonClear = (Button) findViewById(R.id.buttonclear);
        Button buttonEqual = (Button) findViewById(R.id.buttonequal);
        /* Set OnClickListener for each button */
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);

        // start countdown with specific amount of time
        Countdown(timeToCountdown);

        // draw numbers for calculation
        NewCalculation(level);
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

    private void SetCustomFonts() {
        Typeface comic_andyFont = Typeface.createFromAsset(getAssets(), "fonts/comic_andy.ttf");
        txtPoints.setTypeface(comic_andyFont);
        txtTimer.setTypeface(comic_andyFont);
    }

    private void GetSharedPreferencesData() {
        sharedpreferences  = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device
        if (sharedpreferences.contains(pointsKeySharedPreference)) {
            points  = sharedpreferences.getInt(pointsKeySharedPreference, 0);
        } else points = 0;
    }

    /* Random the digits */
    private void NewCalculation(int level) {
        // chose calculation type
        switch (operator) {
            case "plus":
                // draw random numbers for calculation
                firstNumber = drawNumber(0,level*2);
                secondNumber = drawNumber(0,level*2);
                txtQuestion.setText(firstNumber + " + " + secondNumber + " = ");
                break;
            case "minus":
                // draw random numbers for calculation
                firstNumber = drawNumber(0,level*2);
                secondNumber = drawNumber(0,level*2);
                while (firstNumber < secondNumber) {
                    firstNumber = drawNumber(0,level*2);
                    secondNumber = drawNumber(0,level*2);
                }
                txtQuestion.setText(firstNumber + " - " + secondNumber + " = ");
                break;
            case "multiply":
                // draw random numbers for calculation
                firstNumber = drawNumber(0,1);
                secondNumber = drawNumber(1,1);
                txtQuestion.setText(firstNumber + " * " + secondNumber + " = ");
                break;
            case "divide":
                firstNumber = drawNumber(0,level);
                secondNumber = drawNumber(1,level);
                while ((firstNumber % secondNumber) != 0) {
                    firstNumber = drawNumber(0,level);
                    secondNumber = drawNumber(1,level);
                }
                txtQuestion.setText(firstNumber + " / " + secondNumber + " = ");
                break;
        }
    }

    private int drawNumber(int addToDrawValue, int level) {
        int temporary = (int) (Math.random() * 10 * level);
        // add 1 because draw number 0 is not desired (can't divide by zero)
        return temporary + addToDrawValue;
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) findViewById(v.getId());
        String text = btn.getText().toString();

        switch (v.getId()) {
            case R.id.buttonclear:
                ClearTextResult();
                break;
            case R.id.buttonequal:
                if (!txtAnswer.getText().toString().isEmpty()) CompareResult();
                break;
            default:
                String currentText = txtAnswer.getText().toString();
                if (currentText.length() < 3) txtAnswer.setText(currentText + text);
                break;

        }
    }

    private void CompareResult() {

        switch (operator) {
            case "plus":
                if (firstNumber + secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    Toast.makeText(this, "Bravo", Toast.LENGTH_SHORT).show();
                    points++;
                    NewCalculation(level);
                } else {
                    if (points > 0) points--;
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show(); // Change this for sound
                }
                // reset textview with users result
                ClearTextResult();
                break;
            case "minus":
                if (firstNumber - secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    Toast.makeText(this, "Bravo", Toast.LENGTH_SHORT).show();
                    points++;
                    NewCalculation(level);
                } else {
                    if (points > 0) points--;
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show(); // Change this for sound
                }
                // reset textview with users result
                ClearTextResult();
                break;
            case "multiply":
                if (firstNumber * secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    Toast.makeText(this, "Bravo", Toast.LENGTH_SHORT).show();
                    points++;
                    NewCalculation(level);
                } else {
                    if (points > 0) points--;
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show(); // Change this for sound
                }
                // reset textview with users result
                ClearTextResult();
                break;
            case "divide":
                if (firstNumber / secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
//                    Toast.makeText(this, "Bravo", Toast.LENGTH_SHORT).show();
                    points++;
                    NewCalculation(level);
                } else {
                    if (points > 0) points--;
                    Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show(); // Change this for sound
                }
                // reset textview with users result
                ClearTextResult();
                break;
        }
        txtPoints.setText(getString(R.string.points) +" "+ Integer.toString(points));
    }

    private void ClearTextResult() {
        txtAnswer.setText("");
    }

    /* Countdown Timer */
    private void Countdown(int miliSeconds) {
        new CountDownTimer(miliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) ((millisUntilFinished % 60000) / 1000);
                if (secondsLeft < 10)
                    txtTimer.setText(getString(R.string.timer) +" 0" + secondsLeft);
                else
                    txtTimer.setText(getString(R.string.timer) +" "+ secondsLeft);
            }

            @Override
            public void onFinish() {
                // display the points and store them in shared preferences
               /* new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle)
                        .setTitle(R.string.dialog_tryAgain)
                        .setMessage(R.string.dialog_again)
                        .setIcon(null)
                        .setPositiveButton(R.string.dialog_Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // reset all game variables to default values
                                ResetGameVariables();
                            }
                        })
                        .setNegativeButton(R.string.dialog_No, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .show();*/
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt(pointsKeySharedPreference, points);
                editor.commit();

                Toast.makeText(MainActivity.this, "You have "+ Integer.toString(points) + " points.", Toast.LENGTH_SHORT).show();

                System.exit(0);
            }
        }.start();
    }
}