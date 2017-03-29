package me.gurpreetsk.gopetting;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by gurpreet on 29/03/17.
 */

public class PettingApplication extends Application {

    private RequestQueue queue;
    private static PettingApplication sInstance;

    private static final String TAG = PettingApplication.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static synchronized PettingApplication getInstance() {
        return sInstance;
    }

    public <T> void addToQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getQueue().add(req);
    }

    public <T> void addToQueue(Request<T> req) {
        req.setTag(TAG);
        getQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelAll(tag);
        }
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
            return queue;
        }
        return queue;
    }

}
