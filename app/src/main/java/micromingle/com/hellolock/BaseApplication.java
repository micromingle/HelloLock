package micromingle.com.hellolock;

import android.app.Application;

import com.micromingle.hellolock.AppLockManager;


/**
 * Created by Administrator on 5/24/2015.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppLockManager.getInstance().enableDefaultAppLockIfAvailable(this);
    }
}
