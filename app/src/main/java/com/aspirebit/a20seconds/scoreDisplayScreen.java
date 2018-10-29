package com.aspirebit.a20seconds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Integer.valueOf;

public class scoreDisplayScreen extends AppCompatActivity implements View.OnClickListener {

    View decorView;
    TextView scoreTextView,highscoreTextView, mistapsTextView, accuracyTextView, totalTapTextView;
    ImageView bt_retry, bt_home;
    int highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display_screen);

        decorView = getWindow().getDecorView();
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        highscoreTextView = (TextView) findViewById(R.id.text_highscore);
        mistapsTextView = (TextView) findViewById(R.id.text_mistaps);
        accuracyTextView = (TextView) findViewById(R.id.text_accurcy);
        totalTapTextView = (TextView) findViewById(R.id.text_totaltaps);

        bt_retry = (ImageView) findViewById(R.id.img_retry);
        bt_home = (ImageView) findViewById(R.id.img_home);

        bt_retry.setOnClickListener((View.OnClickListener) this);
        bt_home.setOnClickListener((View.OnClickListener) this);

        Bundle ex = getIntent().getExtras();
        int score = ex.getInt("score");
        int totalTap = ex.getInt("totalTape");
        int mistaps = totalTap - score;
        int acc;
        if(totalTap != 0) {
            acc = (score * 100) / totalTap;
        }else {
            acc = 0;
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyScore", 0); // 0 - for private mode
        String pre_highscore = pref.getString("highscore", "0");
        int totalCoin = pref.getInt("totalCoin", 0);
        SharedPreferences.Editor editor = pref.edit();
        if( score > valueOf(pre_highscore)){
            highscore = score;
            editor.putString("highscore" , String.valueOf(highscore));
            editor.putString("highaccuracy" ,String.valueOf(acc) );
        }
        else {
            highscore = valueOf(pre_highscore);
        }

        totalCoin += score;
        editor.putInt("totalCoin", totalCoin);
        editor.commit();

        scoreTextView.setText(String.valueOf(score));
        highscoreTextView.setText(String.valueOf(highscore));
        mistapsTextView.setText(String.valueOf(mistaps));
        accuracyTextView.setText(String.valueOf(acc) +"%");
        totalTapTextView.setText(String.valueOf(totalTap));



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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        switch (id){

            case R.id.img_retry :
                bt_retry.startAnimation(myAnim);
                Intent i = new Intent(this , MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.img_home :
                bt_home.startAnimation(myAnim);
                Intent intent_home = new Intent(this , Home.class);
                startActivity(intent_home);
                finish();
                break;
        }
    }
}
