package com.franshise.franshise.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;

public class SplashActivity extends AppCompatActivity {
    ImageView splash;
    Animation fade0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash= findViewById(R.id.splash);
        fade0 = AnimationUtils.loadAnimation(this, R.anim.fade_in_enter);
        splash.startAnimation(fade0);

        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {

                if(new SharedPrefrenceModel(SplashActivity.this).isFirstTime()){
                    startActivity(new Intent(SplashActivity.this, Main.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);
                }
            else {
                    new SharedPrefrenceModel(SplashActivity.this).setFirstTime(true);
                    startActivity(new Intent(SplashActivity.this, LanguageActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);
                }

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
