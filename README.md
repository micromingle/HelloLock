# HelloLock
a library for app lock
it will renew as library proceeds


# USAGE:

1 Add the following line in onCreate of your App file AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);

2 Open the file android.manifest and declare the following activities:

   <activity android:name="com.micromingle.hellolock.PatternPreferencesActivity"></activity>
    <activity android:name="com.micromingle.hellolock.PatternUnlockActivity"></activity>
   <activity android:name="com.micromingle.hellolock.ManagerPatternActivity"></activity>
