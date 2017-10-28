package com.example.workstation.quicknumbers;

import android.os.Build;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtTimer;
    private TextView txtPoints;
    private TextView txtQuestion;
    private int firstNumber;
    private int secondNumber;
    private char operator = '+';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtPoints = (TextView) findViewById(R.id.txtTimer);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);

        MakeFullscreen();

        // Draw numbers to calculate level 1
        DrawNumbers(1);
        // start countdown
        Countdown(30000);
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

    /* Random the digits */
    private void DrawNumbers(int level){
        firstNumber = (int) (Math.random()*10*level);
        secondNumber = (int) (Math.random()*10*level);
        txtQuestion.setText(firstNumber + " + " + secondNumber + " = ");
    }

    /* Countdown Timer */
    private void Countdown(int miliSeconds) {
        // countdown timer for alarms this is only for test (pozniej to wykasuj jak zrobisz do godzin
        new CountDownTimer(miliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutesLeft = (int) millisUntilFinished / 60000;
                int secondsLeft = (int) (millisUntilFinished % 60000) / 1000;
                if (secondsLeft < 10)
                    txtTimer.setText(Integer.toString(minutesLeft) + ":0" + secondsLeft);
                else
                    txtTimer.setText(Integer.toString(minutesLeft) + ":" + secondsLeft);
            }

            @Override
            public void onFinish() {
                // call alarm on mobile device
                txtTimer.setText("Alarm");
            }
        }.start();
    }

    /* Compare the results */
    private void Compare(int result){
//        txtQuestion.setText(Integer.toString(firstNumber) + " + " + Integer.toString(secondNumber) + " = " + Integer.toString(result));
//        if (firstNumber+secondNumber == result) Toast.makeText(this,"Bravo",Toast.LENGTH_SHORT).show();

        1. klik na cyfre, dodaj to textview . |Dodaj rownosc try {
        wtedy po kliknieciu na rownosc urucho compare( results from textview)
    }
}