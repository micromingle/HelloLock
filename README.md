# HelloLock
an android library for you to lock your app


# USAGE:

1  Add the following line in onCreate of your App file

   AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);

   And in your AndroidManifest, replace the default application within application tag by android:name="yourApplication"

   like        <application android:allowBackup="true" android:label="@string/app_name"
                android:icon="@mipmap/ic_launcher" android:theme="@style/AppTheme.Light"
                android:name="yourApplication">

2 Open the file android.manifest and declare the following activities:

   <activity android:name="com.micromingle.hellolock.PatternPreferencesActivity"></activity>

    <activity android:name="com.micromingle.hellolock.PatternUnlockActivity"></activity>

   <activity android:name="com.micromingle.hellolock.ManagerPatternActivity"></activity>

3  add the following items to you app theme

        <item name="android:windowDisablePreview">true</item>
        <item name="patternViewStyle">@style/PatternView.Holo.Light</item>
        # or
        <item name="android:windowDisablePreview">true</item>
         <item name="patternViewStyle">@style/PatternView.Holo</item>

 4  call PatternPreferencesActivity configure your patter lock