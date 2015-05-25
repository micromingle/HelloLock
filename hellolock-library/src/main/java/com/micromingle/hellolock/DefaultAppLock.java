package com.micromingle.hellolock;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DefaultAppLock extends AbstractAppLock {

    private Application currentApp; //Keep a reference to the app that invoked the locker
    private SharedPreferences settings;
    public Date lostFocusDate;
    public static String password__key = "passcode_lock_prefs_password_key";

    //Add back-compatibility
    private static final String OLD_PASSWORD_SALT = "sadasauidhsuyeuihdahdiauhs";
    private static final String OLD_APP_LOCK_PASSWORD_PREF_KEY = "wp_app_lock_password_key";

    public DefaultAppLock(Application currentApp) {
        super();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(currentApp);
        this.settings = settings;
        this.currentApp = currentApp;
    }

    public void enable() {
        if (android.os.Build.VERSION.SDK_INT < 14)
            return;

        if (isPasswordLocked()) {
            //  currentApp.unregisterActivityLifecycleCallbacks(this);
            currentApp.registerActivityLifecycleCallbacks(this);
        }
    }

    public void disable() {
        if (android.os.Build.VERSION.SDK_INT < 14)
            return;

        currentApp.unregisterActivityLifecycleCallbacks(this);
    }

    public void forcePasswordLock() {
        lostFocusDate = null;
    }

    @Override
    public boolean verifyPassword(Context context, List<LockPatternView.Cell> pattern) {
        String newpassword = bytesToString(patternToSha1(pattern));
        String oldpassword = settings.getString(password__key, "");
        if (newpassword.equals(oldpassword)) {
            lostFocusDate = new Date();
            return true;
        }
        return false;
    }

    public boolean verifyPassword(String password) {
        String storedPassword = "";
        if (password.equalsIgnoreCase(storedPassword)) {
            lostFocusDate = new Date();
            return true;
        } else {
            return false;
        }
    }


    public boolean setPassword(String password) {
        SharedPreferences.Editor editor = settings.edit();

        if (password == null) {
            editor.remove(password__key);
            editor.commit();
            this.disable();
        } else {
            editor.putString(password__key, password);
            editor.commit();
            this.enable();
        }

        return true;
    }

    @Override
    public boolean confirmPassword(Context context, String firstPassword, List<LockPatternView.Cell> confirmPattern) {

        String confromPassword = bytesToString(patternToSha1(confirmPattern));
        if (confromPassword.equals(firstPassword)) {
            return true;
        }
        return false;

    }

    //Check if we need to show the lock screen at startup
    public boolean isPasswordLocked() {

        if (settings.contains(password__key)) //Check if the old value is available
            return true;
        return false;
    }

    private boolean mustShowUnlockSceen() {

        if (isPasswordLocked() == false)
            return false;

        if (lostFocusDate == null)
            return true; //first startup or when we forced to show the password

        int currentTimeOut = lockTimeOut; //get a reference to the current password timeout and reset it to default
        lockTimeOut = DEFAULT_TIMEOUT;
        Date now = new Date();
        long now_ms = now.getTime();
        long lost_focus_ms = lostFocusDate.getTime();
        int secondsPassed = (int) (now_ms - lost_focus_ms) / (1000);
        secondsPassed = Math.abs(secondsPassed); //Make sure changing the clock on the device to a time in the past doesn't by-pass PIN Lock
        if (secondsPassed >= currentTimeOut) {
            lostFocusDate = null;
            return true;
        }

        return false;
    }

    @Override
    public void onActivityPaused(Activity arg0) {

        if (arg0.getClass() == PatternUnlockActivity.class)
            return;

        if ((this.appLockDisabledActivities != null) && Arrays.asList(this.appLockDisabledActivities).contains(arg0.getClass().getName()))
            return;

        lostFocusDate = new Date();

    }

    @Override
    public void onActivityResumed(Activity arg0) {

        if (arg0.getClass() == PatternUnlockActivity.class)
            return;

        if ((this.appLockDisabledActivities != null) && Arrays.asList(this.appLockDisabledActivities).contains(arg0.getClass().getName()))
            return;

        if (mustShowUnlockSceen()) {
            //uhhh ohhh!
            Intent i = new Intent(arg0.getApplicationContext(), PatternUnlockActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            arg0.getApplication().startActivity(i);
            return;
        }

    }

    private static String bytesToString(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private static byte[] stringToBytes(String string) {
        return Base64.decode(string, Base64.DEFAULT);
    }

    public static byte[] patternToBytes(List<LockPatternView.Cell> pattern) {
        int patternSize = pattern.size();
        byte[] bytes = new byte[patternSize];
        for (int i = 0; i < patternSize; ++i) {
            LockPatternView.Cell cell = pattern.get(i);
            bytes[i] = (byte) (cell.getRow() * 3 + cell.getColumn());
        }
        return bytes;
    }

    public static List<LockPatternView.Cell> bytesToPattern(byte[] bytes) {
        List<LockPatternView.Cell> pattern = new ArrayList<LockPatternView.Cell>();
        for (byte b : bytes) {
            pattern.add(LockPatternView.Cell.of(b / 3, b % 3));
        }
        return pattern;
    }

    @Deprecated
    public static String patternToString(List<LockPatternView.Cell> pattern) {
        return bytesToString(patternToBytes(pattern));
    }

    @Deprecated
    public static List<LockPatternView.Cell> stringToPattern(String string) {
        return bytesToPattern(stringToBytes(string));
    }

    private static byte[] sha1(byte[] input) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] patternToSha1(List<LockPatternView.Cell> pattern) {
        return sha1(patternToBytes(pattern));
    }

    public static String patternToSha1String(List<LockPatternView.Cell> pattern) {
        return bytesToString(patternToSha1(pattern));
    }

    @Override
    public void onActivityCreated(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityDestroyed(Activity arg0) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity arg0, Bundle arg1) {
    }

    @Override
    public void onActivityStarted(Activity arg0) {
    }

    @Override
    public void onActivityStopped(Activity arg0) {
    }
}
