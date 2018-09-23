package org.afrikcode.pes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.afrikcode.pes.R;
import org.afrikcode.pes.impl.AuthImp;

public class SplashActivity extends AppCompatActivity {

    AuthImp mAuthImp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuthImp = new AuthImp();

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                if (mAuthImp.isAuthenticated()) {
                    // start home activity
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    // start authActivityforResult
                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                    startActivityForResult(intent, 11);
                }

            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                startHome();
            } else {
                finish();
            }
        }
    }

    private void startHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
