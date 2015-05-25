/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.micromingle.hellolock;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public abstract class BasePatternActivity extends Activity implements LockPatternView.OnPatternListener, View.OnClickListener {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 1200;

    protected TextView messageText;
    protected LockPatternView patternView;
    protected LinearLayout buttonContainer;
    protected Button leftButton;
    protected Button rightButton;

    //  String leftButtonText="";


    private final Runnable clearPatternRunnable = new Runnable() {
        public void run() {
            // clearPattern() resets display mode to DisplayMode.Correct.
            patternView.clearPattern();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pl_base_pattern_activity);
        messageText = (TextView) findViewById(R.id.pl_message_text);
        patternView = (LockPatternView) findViewById(R.id.pl_pattern);
        buttonContainer = (LinearLayout) findViewById(R.id.pl_button_container);
        leftButton = (Button) findViewById(R.id.pl_left_button);
        rightButton = (Button) findViewById(R.id.pl_right_button);

    }



    public abstract void onPatternCreated(List<LockPatternView.Cell> pattern);

    public abstract void onPatternBegin();

    protected void removeClearPatternRunnable() {
        patternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        patternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }

    @Override
    public void onPatternStart() {
      //  patternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
        onPatternBegin();
    }

    @Override
    public void onPatternCleared() {
        patternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        Log.d("Added", "pattern added");
    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {

        onPatternCreated(pattern);
        Log.d("detected", "pattern detected");
    }

    protected void showPasswordError() {
        Toast toast = Toast.makeText(BasePatternActivity.this, getString(R.string.passcode_wrong_passcode), Toast.LENGTH_SHORT);
     //   toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
        toast.show();
    }



}
