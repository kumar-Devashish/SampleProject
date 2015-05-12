package com.android.flickrimagesearch;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Application class
 */

public class FlickrImageSearchApplication extends Application{
    private static final String TAG = FlickrImageSearchApplication.class.getSimpleName();
    private Gson gson;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static FlickrImageSearchApplication mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(), new ImageLoader.ImageCache() {

                private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
                        20);

                @Override
                public void putBitmap(String url, Bitmap bitmap) {
                    // TODO Auto-generated method stub
                    mCache.put(url, bitmap);
                }

                @Override
                public Bitmap getBitmap(String url) {
                    // TODO Auto-generated method stub
                    return mCache.get(url);
                }
            });
        }
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag) {
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static FlickrImageSearchApplication getmInstance(){
        return mInstance;
    }
}
