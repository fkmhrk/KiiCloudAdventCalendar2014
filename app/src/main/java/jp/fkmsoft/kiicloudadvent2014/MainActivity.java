package jp.fkmsoft.kiicloudadvent2014;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import jp.fkmsoft.kiicloudadvent2014.page.signup.SignupFragment;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container, SignupFragment.newInstance(), false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication context = (MyApplication) getApplicationContext();
        context.getQueue().start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication context = (MyApplication) getApplicationContext();
        context.getQueue().stop();
    }
}
