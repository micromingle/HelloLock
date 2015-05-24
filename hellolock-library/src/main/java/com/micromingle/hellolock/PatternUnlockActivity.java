package com.micromingle.hellolock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.List;

/**
 * Created by Administrator on 5/24/2015.
 */
public class PatternUnlockActivity extends BasePatternActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonContainer.setVisibility(View.INVISIBLE);
        patternView.setOnPatternListener(this);
    }


    @Override
    public void onPatternCreated(List<LockPatternView.Cell> pattern) {

        Log.d("pattern ", "pattern created");

        if(LockPatternUtils.checkPassword(this,pattern)){
            setResult(RESULT_OK);
            finish();
        }else{
            patternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            postClearPatternRunnable();
            Thread shake = new Thread() {
                public void run() {
                    Animation shake = AnimationUtils.loadAnimation(PatternUnlockActivity.this, R.anim.shake);
                    findViewById(R.id.AppUnlockLinearLayout1).startAnimation(shake);
                    showPasswordError();

                }
            };
            runOnUiThread(shake);
        }
    }

    @Override
    public void onPatternBegin() {

        Log.d("pattern ", "pattern begin");
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        AppLockManager.getInstance().getCurrentAppLock().forcePasswordLock();
        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        this.startActivity(i);
        finish();
    }
}
