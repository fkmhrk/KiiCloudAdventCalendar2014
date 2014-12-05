package jp.fkmsoft.kiicloudadvent2014;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Application class
 */
public class MyApplication extends Application {

    private RequestQueue mQueue;

    public synchronized RequestQueue getQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(this);
        }
        return mQueue;
    }
}
