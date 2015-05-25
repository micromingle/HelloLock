# HelloLock
an android library for you to lock your app


# USAGE:

1  Add the following line in onCreate of your App file

   AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);

   And in your AndroidManifest, replace the default application within application tag  like

```xml
<application android:allowBackup="true" android:label="@string/app_name"
               android:icon="@mipmap/ic_launcher" android:theme="@style/AppTheme.Light"
               android:name="yourApplication">
</application>
...

2 Open the file android.manifest and declare the following activities:
```xml
<activity android:name="com.micromingle.hellolock.PatternPreferencesActivity"></activity>

      <activity android:name="com.micromingle.hellolock.PatternUnlockActivity"></activity>

      <activity android:name="com.micromingle.hellolock.ManagerPatternActivity">
</activity>
...
3  add the following items to you app theme
```xml
<item name="android:windowDisablePreview">true</item>
        <item name="patternViewStyle">@style/PatternView.Holo.Light</item>
        # or
        <item name="android:windowDisablePreview">true</item>
<item name="patternViewStyle">@style/PatternView.Holo</item>
...

 4  call PatternPreferencesActivity configure your pattern lock

 ```xml
 <item name="patternViewStyle">@style/PatternView.Holo</item>
 <!-- Or PatternView.Holo.Light, or your own style extending these two or not. -->
 ```