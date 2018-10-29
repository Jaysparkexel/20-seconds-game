package com.aspirebit.a20seconds;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    View decorView;
    int widthAndHeight;
    ArrayList<ImageView> imageGrid;
    ArrayList<Integer> colorArray;
    GridLayout gridLayout;
    ImageView qImageView;
    int qIndex;
    ArrayList<Integer> qBoard;
    static int answerNo;
    static int compareAnswer;
    static int score;
    static int totalTap;
    TextView scoreTextView, timerTextView;
    long remainingTime;
    CountDownTimer countDownTimer;
    private View view;
    MediaPlayer mp1, mp2, mp3, mp4;
    float volumeLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        imageGrid = new ArrayList<ImageView>();
        qBoard = new ArrayList<Integer>();
        widthAndHeight = getScreenWidth();
        volumeLevel = 1f;
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        qImageView = (ImageView) findViewById(R.id.qImageView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        mp1 = MediaPlayer.create(this, R.raw.switches2144);
        mp2 = MediaPlayer.create(this, R.raw.beeps21);
        mp3 = MediaPlayer.create(this, R.raw.clicks15);
        mp1.setVolume(volumeLevel, volumeLevel);
        mp2.setVolume(volumeLevel, volumeLevel);
        mp3.setVolume(volumeLevel, volumeLevel);

        getImageView();
        setColorArray();
        startGame();

    }

    public void playRightSound(){
        try {
            if (mp1.isPlaying()) {
                mp1.stop();
                mp1.release();
                mp1 = MediaPlayer.create(getApplicationContext(), R.raw.switches2144);
                mp1.setVolume(volumeLevel, volumeLevel);
            } mp1.start();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public void playWrongSound(){
        try {
            if (mp2.isPlaying()) {
                mp2.stop();
                mp2.release();
                mp2 = MediaPlayer.create(getApplicationContext(), R.raw.beeps21);
                mp2.setVolume(volumeLevel, volumeLevel);

            } mp2.start();
        } catch(Exception e) { e.printStackTrace(); }
    }


    public void playNewBoardSound(){
        try {
            if (mp3.isPlaying()) {
                mp3.stop();
                mp3.release();
                mp3 = MediaPlayer.create(getApplicationContext(), R.raw.clicks15);
                mp3.setVolume(volumeLevel, volumeLevel);

            } mp3.start();
        } catch(Exception e) { e.printStackTrace(); }

    }

    public void playGameEndSound(){
        mp4 = MediaPlayer.create(this, R.raw.switches889);
        mp4.setVolume(volumeLevel, volumeLevel);
        mp4.start();
    }

    public void startGame(){
        setBoard();
        score = 0;
        totalTap = 0;
        remainingTime = 10000;
        startCountDownTimer(remainingTime);

    }

    public void startCountDownTimer(long timerTime){
        countDownTimer = new CountDownTimer(timerTime + 100, 100){
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = millisUntilFinished;
                updateTimer((int)millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00.0");
                playGameEndSound();
                Intent i = new Intent(getApplicationContext(), scoreDisplayScreen.class);
                i.putExtra("score", score);
                i.putExtra("totalTape", totalTap);
                startActivity(i);
                finish();
//                Toast.makeText(getApplicationContext(), Integer.toString(totalTap), Toast.LENGTH_LONG).show();

//                startGame();
            }

        }.start();
    }

    public void updateTimer(int secondsLeft){

        int seconds = (int) secondsLeft/1000;
        int millisecond = (secondsLeft - seconds*1000)/100;
        String secondsString = Integer.toString(seconds);
        if(seconds<=9){
            secondsString = "0"+secondsString;
        }

        timerTextView.setText(secondsString +"." +Integer.toString(millisecond));
    }

    private static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void setBoard(){

        answerNo = 0;
        compareAnswer = 0;
        qBoard.clear();
        Random random = new Random();
        qIndex = random.nextInt(4);
        qImageView.setImageResource(colorArray.get(qIndex));
        for (int i = 0; i < 25; i++){
            int qBoardAnswer = random.nextInt(4);
            qBoard.add(qBoardAnswer);
            if(qBoardAnswer == qIndex){
                answerNo++;
            }
            imageGrid.get(i).setImageResource(colorArray.get(qBoard.get(i)));
            imageGrid.get(i).setVisibility(View.VISIBLE);
        }

    }

    public void setColorArray(){
        colorArray = new ArrayList<Integer>();
        colorArray.add(R.drawable.blue);
        colorArray.add(R.drawable.green);
        colorArray.add(R.drawable.red);
        colorArray.add(R.drawable.yellow);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void getImageView(){
    imageGrid.add((ImageView)findViewById(R.id.imageView0));
    imageGrid.add((ImageView)findViewById(R.id.imageView1));
    imageGrid.add((ImageView)findViewById(R.id.imageView2));
    imageGrid.add((ImageView)findViewById(R.id.imageView3));
    imageGrid.add((ImageView)findViewById(R.id.imageView4));
    imageGrid.add((ImageView)findViewById(R.id.imageView5));
    imageGrid.add((ImageView)findViewById(R.id.imageView6));
    imageGrid.add((ImageView)findViewById(R.id.imageView7));
    imageGrid.add((ImageView)findViewById(R.id.imageView8));
    imageGrid.add((ImageView)findViewById(R.id.imageView9));
    imageGrid.add((ImageView)findViewById(R.id.imageView10));
    imageGrid.add((ImageView)findViewById(R.id.imageView11));
    imageGrid.add((ImageView)findViewById(R.id.imageView12));
    imageGrid.add((ImageView)findViewById(R.id.imageView13));
    imageGrid.add((ImageView)findViewById(R.id.imageView14));
    imageGrid.add((ImageView)findViewById(R.id.imageView15));
    imageGrid.add((ImageView)findViewById(R.id.imageView16));
    imageGrid.add((ImageView)findViewById(R.id.imageView17));
    imageGrid.add((ImageView)findViewById(R.id.imageView18));
    imageGrid.add((ImageView)findViewById(R.id.imageView19));
    imageGrid.add((ImageView)findViewById(R.id.imageView20));
    imageGrid.add((ImageView)findViewById(R.id.imageView21));
    imageGrid.add((ImageView)findViewById(R.id.imageView22));
    imageGrid.add((ImageView)findViewById(R.id.imageView23));
    imageGrid.add((ImageView)findViewById(R.id.imageView24));
    setImageViewLayout();

    }

    public void setImageViewLayout(){
        int imageHeight = (widthAndHeight/7);

        for (int i=0; i<25; i++){
            android.view.ViewGroup.LayoutParams layoutParams = imageGrid.get(i).getLayoutParams();
            layoutParams.width = imageHeight;
            layoutParams.height = imageHeight;
            imageGrid.get(i).setLayoutParams(layoutParams);
//            imageGrid.get(i).setImageResource(colorArray.get(2));

        }

        android.view.ViewGroup.LayoutParams qImageViewParams = qImageView.getLayoutParams();
        qImageViewParams.width = imageHeight;
        qImageViewParams.height = imageHeight;
        qImageView.setLayoutParams(qImageViewParams);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void circleTaped(View view) {
        this.view = view;
        if(qBoard.get(Integer.valueOf(view.getTag().toString())) == qIndex){
            playRightSound();
            view.setVisibility(View.INVISIBLE);
            compareAnswer++;
            score++;
            scoreTextView.setText(Integer.toString(score));
            if(compareAnswer == answerNo){
                playNewBoardSound();
                setBoard();
                countDownTimer.cancel();
                remainingTime += 1000;
                startCountDownTimer(remainingTime);
            }
        } else {
            playWrongSound();
        }
        totalTap++;
    }
}
