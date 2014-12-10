package jp.fkmsoft.kiicloudadvent2014;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import jp.fkmsoft.kiicloudadvent2014.page.login.LoginFragment;
import jp.fkmsoft.kiicloudadvent2014.page.main.MainFragment;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // login check
            SharedPreferences pref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
            String token = pref.getString(Constants.PREF_KEY_TOKEN, null);
            String userId = pref.getString(Constants.PREF_KEY_USER_ID, null);
            if (token == null || userId == null) {
                FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container, LoginFragment.newInstance(), false);
            } else {
                FragmentUtils.toNextFragment(getSupportFragmentManager(), R.id.container, MainFragment.newInstance(token, new KiiUser(userId)), false);
            }
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
