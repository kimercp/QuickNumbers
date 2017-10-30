package com.example.workstation.quicknumbers;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
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
    private int timeToCountdown = 10000;
    private int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);

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
        Button buttonclear = (Button) findViewById(R.id.buttonclear);
        Button buttonequal = (Button) findViewById(R.id.buttonequal);
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
        buttonclear.setOnClickListener(this);
        buttonequal.setOnClickListener(this);

        MakeFullscreen();

        // Draw numbers to calculate level 1 (max 9 levels)
        DrawNumbers(level);
        // start countdown (30 secs)
        Countdown(timeToCountdown);
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) findViewById(v.getId());
        String text = btn.getText().toString();

        switch (v.getId()){
            case R.id.buttonclear:
                ClearTextResult();
                break;
            case R.id.buttonequal:
                if (!txtAnswer.getText().toString().isEmpty()) CompareResult();
                break;
            default:
                String currentText = txtAnswer.getText().toString();
                if (currentText.length() < 3) txtAnswer.setText(currentText+text);
                break;

        }
    }

    private void CompareResult() {
        if (firstNumber+secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
            // Change this for sound
            Toast.makeText(this,"Bravo",Toast.LENGTH_SHORT).show();
            points++;
            DrawNumbers(level);
        }
        else Toast.makeText(this,"Wrong",Toast.LENGTH_SHORT).show(); // Change this for sound
        // reset textview with users result
        ClearTextResult();
    }

    private void ClearTextResult() {
        txtAnswer.setText("");
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

                txtPoints.setText("Points: " + Integer.toString(points));
            }

            @Override
            public void onFinish() {
                // Display the points and ask if user wants restart game

                new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle)
                    .setTitle(R.string.dialog_tryAgain)
                    .setMessage(R.string.dialog_again)
                    .setIcon(null)
                    .setPositiveButton(R.string.dialog_Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//  new activity , splash screen ???????
                        }
                    })
                    .setNegativeButton(R.string.dialog_No, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .show();
                // reset all game variables to default values
                ResetGameVariables();
            }
        }.start();
    }

    /* Reset all game variables to default values */
    private void ResetGameVariables(){
        points = 0;
        level = 1;
        timeToCountdown = 30000;
        firstNumber = 0;
        secondNumber = 0;
        ClearTextResult();
    }
}