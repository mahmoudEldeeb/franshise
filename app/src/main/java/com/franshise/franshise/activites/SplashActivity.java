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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashActivity extends AppCompatActivity {
    ImageView splash;
    Animation fade0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash= findViewById(R.id.splash);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(
                SplashActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                      String  refreshedToken = instanceIdResult.getToken();

                        Log.v("tttt",refreshedToken);
                    }
                });
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
