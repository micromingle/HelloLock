package micromingle.com.hellolock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import micromingle.com.hellolock.HelloLockView.LockPatternView;


public class MainActivity extends Activity implements LockPatternView.OnPatternListener{

    LockPatternView lockPatternView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lockPatternView=(LockPatternView)findViewById(R.id.lockView);
        lockPatternView.setOnPatternListener(this);

    }


    @Override
    public void onPatternStart() {

    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {

    }
}
