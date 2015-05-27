package com.micromingle.hellolock;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 5/24/2015.
 */
public class ManagerPatternActivity extends BasePatternActivity {

    private String unverifiedPasscode = null;
    private int type = -1;
    private boolean isDrawn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initEvents();

    }

    public void initEvents() {
        patternView.setOnPatternListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        rightButton.setEnabled(false);
        rightButton.setVisibility(View.GONE);
        messageText.setText(R.string.pl_draw_pattern);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            type = extras.getInt("type", -1);
        }
        Log.d("type", type + "");
        if(type== PatternPreferencesActivity.CHANGE_PASSWORD) {
            messageText.setText(R.string.pl_old_pattern);
        }
        if(type== PatternPreferencesActivity.DISABLE_PASSLOCK) {
            messageText.setText(R.string.pl_previous_pattern);
        }

    }

    @Override
    public void onPatternBegin() {
        removeClearPatternRunnable();
        patternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
        leftButton.setEnabled(true);
        rightButton.setEnabled(false);

        Log.d("begin", "pattern begin");
    }

    @Override
    public void onPatternCreated(List<LockPatternView.Cell> pattern) {
        AbstractAppLock appLock= AppLockManager.getInstance().getCurrentAppLock();
        switch (type) {

            case PatternPreferencesActivity.DISABLE_PASSLOCK:
                if (appLock.verifyPassword(this, pattern)) {
                    setResult(RESULT_OK);
                    appLock.setPassword(null);
                    finish();
                } else {
                    messageText.setText(R.string.pl_wrong_pattern);
                    patternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    postClearPatternRunnable();

                }
                break;

            case PatternPreferencesActivity.ENABLE_PASSLOCK:
                if (unverifiedPasscode == null) {
                    //  ((TextView) findViewById(R.id.top_message)).setText(R.string.passcode_re_enter_passcode);
                    messageText.setText(R.string.pl_confirm_pattern);
                    unverifiedPasscode = LockPatternUtils.patternToSha1String(pattern);
                    Log.d("unverifiedPasscode", unverifiedPasscode);
                    isDrawn = true;
                    leftButton.setText(R.string.pl_redraw);
                    postClearPatternRunnable();
                } else {
                    if (appLock.confirmPassword(this, unverifiedPasscode, pattern)) {
                        setResult(RESULT_OK);
                         AppLockManager.getInstance().getCurrentAppLock().setPassword(unverifiedPasscode);
                       // LockPatternUtils.saveStringToPreference(this, unverifiedPasscode);
                        Log.d("Check", "checking");
                        postClearPatternRunnable();
                        finish();
                    } else {
                        messageText.setText(R.string.pl_wrong_pattern);
                        patternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                        //     showPasswordError();
                        postClearPatternRunnable();

                    }
                }
                break;

            case PatternPreferencesActivity.CHANGE_PASSWORD:
                //verify old password
                messageText.setText(R.string.pl_old_pattern);
                if (appLock.verifyPassword(this, pattern)) {
                    type = PatternPreferencesActivity.ENABLE_PASSLOCK;
                    postClearPatternRunnable();
                    messageText.setText(R.string.pl_draw_pattern);
                } else {
                    messageText.setText(R.string.pl_wrong_pattern);
                    patternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    postClearPatternRunnable();
                    //  showPasswordError();
                    // patternView.clearPattern();
                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == leftButton) {
            if (isDrawn) {
                resetView();
            } else {
                setResult(RESULT_CANCELED);
                finish();
            }
        }

      /*  if (v == rightButton) {
            messageText.setText(R.string.pl_confirm_pattern);
            patternView.clearPattern();
        }*/
    }

    private void resetView() {
        onPatternBegin();
        leftButton.setText(R.string.pl_cancel);
        isDrawn = false;
        patternView.clearPattern();
        messageText.setText(R.string.pl_draw_pattern);
        unverifiedPasscode = null;
    }
}
