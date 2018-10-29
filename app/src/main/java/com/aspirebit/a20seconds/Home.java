package com.aspirebit.a20seconds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static java.lang.Integer.valueOf;

public class Home extends AppCompatActivity implements View.OnClickListener{

    ImageView bt_play;
    TextView tv_highscore, tv_accuracy, coinTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home);

        bt_play = (ImageView) findViewById(R.id.img_play);
        bt_play.setOnClickListener((View.OnClickListener) this);

        tv_highscore = (TextView) findViewById(R.id.textview_highscore);
        tv_accuracy = (TextView) findViewById(R.id.textview_accuracy);
        coinTextView = (TextView) findViewById(R.id.coinTextView);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyScore", 0); // 0 - for private mode
        String highscore = pref.getString("highscore" , "0");
        String accuracy = pref.getString("highaccuracy" , "0");
        int totalCoin = pref.getInt("totalCoin", 0);

        /*if(valueOf(highscore) < 100 ){

            if(valueOf(highscore) < 10){
                highscore = "00" + highscore;
            }else{
                highscore = "0" + highscore;
            }

        }*/
        tv_highscore.setText(highscore);
        tv_accuracy.setText(accuracy);
        coinTextView.setText(Integer.toString(totalCoin));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){

            case R.id.img_play:

                final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
                bt_play.startAnimation(myAnim);

                Intent i = new Intent(this , MainActivity.class);
                startActivity(i);
                finish();
        }
    }

    class MyBounceInterpolator implements android.view.animation.Interpolator {
        private double mAmplitude = 1;
        private double mFrequency = 10;

        MyBounceInterpolator(double amplitude, double frequency) {
            mAmplitude = amplitude;
            mFrequency = frequency;
        }

        public float getInterpolation(float time) {
            return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                    Math.cos(mFrequency * time) + 1);
        }
    }
}
