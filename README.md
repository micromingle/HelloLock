# HelloLock
an android library for you to lock your app

## Thanks
 Thanks to these two libs
  * https://github.com/wordpress-mobile/PasscodeLock-Android
  * https://github.com/DreaminginCodeZH/PatternLock

##  ScreenShoots

![Sample](./ScreenShots/mainpage.png)
![Sample](./ScreenShots/turnon.png)
![Sample](./ScreenShots/start.png)
![Sample](./ScreenShots/confirm.png)
![Sample](./ScreenShots/error.png)
![Sample](./ScreenShots/error2.png)

## SAMPLE¡¡¡¡APPLICATION

[Sample Application](./apk/HelloLockSample.apk)


## USAGE:

```xml

1  Add your module's dependency on hellolock library;

2  Add the following line in onCreate of your App file :

   AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);

   And in your AndroidManifest, replace the default application with you custom Application

   within  application tag.

   As shown in  HelloLockSample, I create my own application :  BaseApplication ,then declare it as follows


  <application android:allowBackup="true" android:label="@string/app_name"
             android:icon="@mipmap/ic_launcher" android:theme="@style/AppTheme.Light"
             android:name=".BaseApplication">



3  Open the file AndroidManifest and declare the following activities:


  <activity android:name="com.micromingle.hellolock.PatternPreferencesActivity"></activity>
  <activity android:name="com.micromingle.hellolock.PatternUnlockActivity"></activity>
  <activity android:name="com.micromingle.hellolock.ManagerPatternActivity"></activity>


4  add the following items to you app theme:


  <item name="android:windowDisablePreview">true</item>
  <item name="patternViewStyle">@style/PatternView.Holo.Light</item>
   or
  <item name="android:windowDisablePreview">true</item>
  <item name="patternViewStyle">@style/PatternView.Holo</item>


5  call PatternPreferencesActivity to configure your pattern lock.


6   you can also custom your path color and circle color in pl_base_pattern_activity.xml as follows

    * Firstly   declare   xmlns:myview="http://schemas.android.com/apk/res-auto"  within root layout

    as shown in pl_base_pattern_activity.xml:

    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:myview="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:divider="?android:attr/dividerHorizontal"
        android:orientation="vertical"
        android:id="@+id/AppUnlockLinearLayout1"
        android:showDividers="middle">

    * Secondly , define your custom color within com.micromingle.hellolock.LockPatternView as follows

      <com.micromingle.hellolock.LockPatternView
                android:id="@+id/pl_pattern"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                myview:aspect="lock_height"
                myview:successColor="@android:color/holo_orange_light"
                myview:regularColor="@android:color/holo_purple"
                myview:errorColor="@android:color/holo_blue_dark"/>

