# HelloLock
an android library for you to lock your app

## Thanks
 Thanks to those two libs
  * https://github.com/wordpress-mobile/PasscodeLock-Android
  * https://github.com/DreaminginCodeZH/PatternLock

##  ScreenShoots

![Sample](./ScreenShots/mainpage.png)
![Sample](./ScreenShots/turnon.png)
![Sample](./ScreenShots/start.png)
![Sample](./ScreenShots/confirm.png)
![Sample](./ScreenShots/error.png)
![Sample](./ScreenShots/error2.png)

## USAGE:

1  Add the following line in onCreate of your App file :

   AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);

   And in your AndroidManifest, replace the default application with you custom Application

   within  application tag.

   For the  HelloLockSample, I create my own application: BaseApplication ,then declare it as follows

 ```xml
  <application android:allowBackup="true" android:label="@string/app_name"
             android:icon="@mipmap/ic_launcher" android:theme="@style/AppTheme.Light"
             android:name=".BaseApplication">
 ...


2  Open the file AndroidManifestt and declare the following activities:

```xml
  <activity android:name="com.micromingle.hellolock.PatternPreferencesActivity"></activity>
  <activity android:name="com.micromingle.hellolock.PatternUnlockActivity"></activity>
  <activity android:name="com.micromingle.hellolock.ManagerPatternActivity"></activity>
...

3  add the following items to you app theme:

```xml
  <item name="android:windowDisablePreview">true</item>
  <item name="patternViewStyle">@style/PatternView.Holo.Light</item>
   or
  <item name="android:windowDisablePreview">true</item>
  <item name="patternViewStyle">@style/PatternView.Holo</item>
...

4  call PatternPreferencesActivity to configure your pattern lock.

```xml
<declare-styleable name="PatternView">
    <!-- Defines the aspect to use when drawing PatternView. -->
    <attr name="aspect">
        <!-- Square; the default value. -->
        <enum name="square" value="0" />
        <enum name="lock_width" value="1" />
        <enum name="lock_height" value="2" />
    </attr>
    <!-- Defines the regular pattern color. -->
    <attr name="regularColor" format="color|reference" />
    <!-- Defines the error color. -->
    <attr name="errorColor" format="color|reference" />
    <!-- Defines the success color. -->
    <attr name="successColor" format="color|reference"/>
    <!-- Defines the color to use when drawing PatternView paths. -->
    <attr name="pathColor" format="color|reference" />
    <attr name="dotDrawableDefault" format="reference" />
    <attr name="dotDrawableTouched" format="reference" />
    <attr name="circleDrawableDefault" format="reference" />
    <attr name="circleDrawable" format="reference" />
    <attr name="arrowUpDrawable" format="reference" />
</declare-styleable>
```