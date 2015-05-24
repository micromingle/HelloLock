/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package org.wordpress.passcodelock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BasePatternActivity extends Activity implements LockPatternView.OnPatternListener, View.OnClickListener {

    private static final int CLEAR_PATTERN_DELAY_MILLI = 2000;
    private int type = -1;
    protected TextView messageText;
    protected LockPatternView patternView;
    protected LinearLayout buttonContainer;
    protected Button leftButton;
    protected Button rightButton;
    private String unverifiedPasscode = null;
    //  String leftButtonText="";
    private boolean isDrawn = false;

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

        initEvents();
    }

    public void initEvents() {
        patternView.setOnPatternListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        rightButton.setEnabled(false);
    }

    protected void removeClearPatternRunnable() {
        patternView.removeCallbacks(clearPatternRunnable);
    }

    protected void postClearPatternRunnable() {
        removeClearPatternRunnable();
        patternView.postDelayed(clearPatternRunnable, CLEAR_PATTERN_DELAY_MILLI);
    }

    @Override
    public void onPatternStart() {
        patternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
    }

    @Override
    public void onPatternCleared() {
        patternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        switch (type) {

            case PasscodePreferencesActivity.DISABLE_PASSLOCK:
                if (LockPatternUtils.confirmPassword(this, pattern)) {
                    setResult(RESULT_OK);
                    AppLockManager.getInstance().getCurrentAppLock().setPassword(null);
                    finish();
                } else {
                    showPasswordError();
                }
                break;

            case PasscodePreferencesActivity.ENABLE_PASSLOCK:
                if (unverifiedPasscode == null) {
                    //  ((TextView) findViewById(R.id.top_message)).setText(R.string.passcode_re_enter_passcode);
                    messageText.setText(R.string.pl_confirm_pattern);
                    unverifiedPasscode = LockPatternUtils.patternToSha1String(pattern);
                    isDrawn = true;//
                    rightButton.setEnabled(true);
                } else {
                    if (LockPatternUtils.confirmPassword(this, unverifiedPasscode, pattern)) {
                        setResult(RESULT_OK);
                        // AppLockManager.getInstance().getCurrentAppLock().setPassword(passLock);
                        LockPatternUtils.saveStringToPreference(this, unverifiedPasscode);
                        finish();
                    } else {
                        unverifiedPasscode = null;
                        //  topMessage.setText(R.string.passcode_enter_passcode);
                        messageText.setText(R.string.pl_wrong_pattern);
                        showPasswordError();
                    }
                }
                break;

            case PasscodePreferencesActivity.CHANGE_PASSWORD:
                //verify old password
                if (LockPatternUtils.confirmPassword(this, pattern)) {
                    //  topMessage.setText(R.string.passcode_enter_passcode);
                    type = PasscodePreferencesActivity.ENABLE_PASSLOCK;
                } else {
                    showPasswordError();
                }
                break;

            default:
                break;
        }
    }

    protected void showPasswordError() {
        Toast toast = Toast.makeText(BasePatternActivity.this, getString(R.string.passcode_wrong_passcode), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 30);
        toast.show();
    }

    @Override
    public void onClick(View v) {
        if (v == leftButton) {
            if (isDrawn) {
                unverifiedPasscode = null;
                isDrawn = false;//
                rightButton.setEnabled(false);
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }
        }

       /* if(v==rightButton){

        }*/
    }
}
