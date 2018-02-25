package com.kimersoft.workstation.quicknumbers;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // constants
    private final int timeToCountdown = 59000; // in miliseconds (to achieve seconds divide by 1000)
    private final int maxLevel = 10;

    // variables
    private TextView txtTimer;
    private TextView txtPoints;
    private TextView txtQuestion;
    private TextView txtAnswer;
    private TextView txtLevel;

    private MediaPlayer correctSoundMP;
    private MediaPlayer incorrectSoundMP;
    private MediaPlayer buttonSoundMP;

    // first random generated number displayed to user
    private int firstNumber;

    // second random generated number displayed to user
    private int secondNumber;

    // number of levels increase
    private int level = 1;

    // number of user points
    private int points;

    // operator from previous activity chosen by user, it says what type of math operation has been chosen
    private String operator = "";

    // variables to use shared preferences
    private SharedPreferences sharedpreferences;
    // user points are saved in shared preferences
    private String pointsKeySharedPreference = "pointsKey";

    private CountDownTimer timerSeconds;

    // keep the miliSeconds left in countdown timer, it useful when user pause the game
    private int miliSecondsLeftTimer = timeToCountdown;

    // AlertDialog status, true if is awaiting the user decision
    private boolean isUserToQuestionActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTimer = (TextView) findViewById(R.id.txtTimer);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);
        txtLevel = (TextView) findViewById(R.id.txtLevel);

        // change default font
        setCustomFonts();

        // get the points from sharedPreferences file
        getSharedPreferencesData();

        // display actual number of points
        txtPoints.setText(getString(R.string.points) + " " + Integer.toString(points));
        // display actual number of level
        txtLevel.setText(getString(R.string.levels) + " " + Integer.toString(level));

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
        ImageButton btnBack = (ImageButton) findViewById(R.id.imgbBack);
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
        btnBack.setOnClickListener(this);

        // sounds when user give good or wrong answer
        correctSoundMP = MediaPlayer.create(this, R.raw.correct_sound);
        incorrectSoundMP = MediaPlayer.create(this, R.raw.incorrect_sound);
        buttonSoundMP = MediaPlayer.create(this, R.raw.button_sound);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // when  the user leaving  activity cancel the timer
        timerSeconds.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start game when activity is active (run first time and every time when back to game)
        if (!isUserToQuestionActive) startGame();
    }

    private void startGame() {
        // full screen on device
        makeFullscreen();
        // start countdown with specific amount of time
        countdown(miliSecondsLeftTimer);
        // draw numbers for calculation
        newCalculation(level);
        // clear text field where user input the answer
        clearTextResult();
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
        txtTimer.setTypeface(comic_andyFont);
        txtLevel.setTypeface(comic_andyFont);
    }

    private void getSharedPreferencesData() {
        String mypreference = "mypreference";
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, MODE_PRIVATE); // 0 - for private mode
        // get the number of points form shared preferences file on device
        points = sharedpreferences.getInt(pointsKeySharedPreference, 0);
    }

    private void newCalculation(int level) {
        // zero and one variables are beginning number of range
        int zero = 0;
        int one = 1; // because divide by zero is not allowed
        // chose calculation type
        switch (operator) {
            case "plus":
                // draw random numbers for calculation
                firstNumber = drawNumber(zero, level);
                secondNumber = drawNumber(zero, level);
                txtQuestion.setText(firstNumber + " + " + secondNumber + " = ");
                break;
            case "minus":
                // draw random numbers for calculation
                do {
                    firstNumber = drawNumber(zero, level);
                    secondNumber = drawNumber(zero, level);
                } while (firstNumber < secondNumber);
                txtQuestion.setText(firstNumber + " - " + secondNumber + " = ");
                break;
            case "multiply":
                // draw random numbers for calculation
                do {
                    // check if a multiply result is in range 0 - 10*level
                    // example for level 3, the range is 0 - 3
                    firstNumber = (int) (Math.random() * 10);
                    secondNumber = (int) (Math.random() * 10 + 1);
                    // random new digits until multiply result is less than level*10
                } while (firstNumber * secondNumber > level * 10);
                txtQuestion.setText(firstNumber + " * " + secondNumber + " = ");
                break;
            case "divide":
                // draw the numbers while result is integer number
                do {
                    firstNumber = drawNumber(zero, level);
                    secondNumber = drawNumber(one, level);
                } while ((firstNumber % secondNumber) != 0);
                txtQuestion.setText(firstNumber + " / " + secondNumber + " = ");
                break;
        }
    }

    private int drawNumber(int lowerbound, int level) {
        int upperbound = level * 10;
        // with every level the range of random numbers increase by 10
        // (int)(Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
        // (where lowerbound is inclusive and upperbound exclusive).
        int temporary = (int) (Math.random() * ((upperbound - lowerbound) + 1) + lowerbound);
        // add 1 because draw number 0 is not desired (can't divide by zero)
        return temporary;
    }

    @Override
    public void onClick(View v) {
        String text = "";
        if (v instanceof Button) {
            Button btn = (Button) findViewById(v.getId());
            text = btn.getText().toString();
        }

        switch (v.getId()) {
            case R.id.buttonclear:
                clearTextResult();
                buttonSoundMP.start();
                break;
            case R.id.buttonequal:
                if (!txtAnswer.getText().toString().isEmpty()) compareResult();
                break;
            case R.id.imgbBack:
                new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle)
                        .setTitle(R.string.confirm_back)
                        .setIcon(R.drawable.back)
                        .setPositiveButton(R.string.dialog_Yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearTextResult();
                                // set as false because user answer and wants to continue game
                                isUserToQuestionActive = true;
                                // when  the user leaving  activity cancel the timer
                                timerSeconds.cancel();
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.dialog_No, null)
                        .setCancelable(false)
                        .show();
                break;
            default:
                // after click on number button, this method add the number to answer text field
                String currentText = txtAnswer.getText().toString();
                if (currentText.length() < 4){
                    buttonSoundMP.start();
                    txtAnswer.setText(currentText + text); // ((Button) v).getText().toString();
                }
                break;
        }
    }

    private void compareResult() {
        /* points for calculations are varies
            If level = 1 then points+=level; for adding 1 points, points+=2*level; for subtract 2 points,
            points+=3*level; for multiply 3 points, points+=4*level; for divide 4 points.
            If level = 2 then adding = 2 points, subtract = 4 points, multiply = 6 points, divide = 8 points
         */
        switch (operator) {
            case "plus":
                if (firstNumber + secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    correctSoundMP.start();
                    points += level;
                    newCalculation(level);
                } else {
                    if (points > 0) {
                        if (level > 1)
                        points -= level / 2;
                        else points--;
                    }
                    incorrectSoundMP.start();
                }
                // reset textview with users result
                clearTextResult();
                break;
            case "minus":
                if (firstNumber - secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    correctSoundMP.start();
                    points += 2 * level;
                    newCalculation(level);
                } else {
                    if (points > 0) {
                        if (level > 1)
                            points -= 2 * level / 2;
                        else points--;
                    }
                    incorrectSoundMP.start();
                }
                // reset textview with users result
                clearTextResult();
                break;
            case "multiply":
                if (firstNumber * secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    // Change this for sound
                    correctSoundMP.start();
                    points += 3 * level;
                    newCalculation(level);
                } else {
                    if (points > 0) {
                        if (level > 1)
                            points -= 3 * level / 2;
                        else points--;
                    }
                    incorrectSoundMP.start();
                }
                // reset textview with users result
                clearTextResult();
                break;
            case "divide":
                if (firstNumber / secondNumber == Integer.parseInt(txtAnswer.getText().toString())) {
                    correctSoundMP.start();
                    // more points because divide is the hardest calculation
                    points += 4 * level;
                    newCalculation(level);
                } else {
                    if (points > 0) {
                        if (level > 1)
                            points -= 4 * level / 2;
                        else points--;
                    }
                    incorrectSoundMP.start();
                }
                // reset textview with users result
                clearTextResult();
                break;
        }
        txtPoints.setText(getString(R.string.points) + " " + Integer.toString(points));
        savePointsInSharedPreferences(points);
    }

    private void clearTextResult() {
        txtAnswer.setText("");
    }

    /* countdown Timer */
    private void countdown(int miliSeconds) {
        timerSeconds = new CountDownTimer(miliSeconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                // need this variable to keep miliseconds in case of pause by user
                miliSecondsLeftTimer = (int) millisUntilFinished;
                // add " 0" as text to text field if remaining seconds are less than 10
                if (millisUntilFinished < 10000)
                    // divide by 1000 to display number of seconds
                    txtTimer.setText(getString(R.string.timer) + " 0" + millisUntilFinished / 1000);
                else
                    txtTimer.setText(getString(R.string.timer) + " " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                // increase level or quit if max level has been reached
                level++;
                // reset miliSecondsLeftTimer to beginning value of game when timer has finished
                miliSecondsLeftTimer = timeToCountdown;

                isUserToQuestionActive = true;

                if (level > maxLevel) {
                    // display the points and store them in shared preferences
                    new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle)
                            .setTitle(R.string.dialog_Congrats)
                            .setMessage(R.string.noMoreLevels)
                            .setIcon(null)
                            .setNeutralButton(R.string.dialog_OK, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .show();
                } else {
                    // ask user if wants to go for harder level
                    new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle)
                            .setTitle(R.string.dialog_tryHarder)
                            .setMessage(R.string.dialog_HarderLevel)
                            .setIcon(null)
                            .setPositiveButton(R.string.dialog_Yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    clearTextResult();
                                    // set as false because user answer and wants to continue game
                                    isUserToQuestionActive = false;
                                    // display actual number of level
                                    txtLevel.setText(getString(R.string.levels) + " " + Integer.toString(level));
                                    startGame();
                                }
                            })
                            .setNegativeButton(R.string.dialog_No, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        }.start();
    }

    // save points when game finish
    private void savePointsInSharedPreferences(int pointsToSaveInSharedPreferences) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(pointsKeySharedPreference, pointsToSaveInSharedPreferences);
        editor.commit();
    }
}