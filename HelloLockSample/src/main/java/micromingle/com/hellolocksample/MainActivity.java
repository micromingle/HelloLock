package micromingle.com.hellolocksample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.micromingle.hellolock.PatternPreferencesActivity;

/**
 * Created by Administrator on 5/24/2015.
 */
public class MainActivity extends Activity implements View.OnClickListener {
    Button activityOne,activityTwo,lockSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        activityOne=(Button)findViewById(R.id.btn1);
        activityTwo=(Button)findViewById(R.id.btn2);
        lockSetting=(Button)findViewById(R.id.btn_setting);
        activityOne.setOnClickListener(this);
        activityTwo.setOnClickListener(this);
        lockSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                startActivity(new Intent(this,ActivityOne.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this,ActivityTwo.class));
                break;
            case R.id.btn_setting:
                startActivity(new Intent(this,PatternPreferencesActivity.class));
                break;
            default:
        }
    }
}
