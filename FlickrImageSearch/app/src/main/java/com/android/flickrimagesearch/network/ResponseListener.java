package com.android.flickrimagesearch.network;

import android.content.Context;
import android.util.Log;

import com.android.flickrimagesearch.FlickrImageSearchApplication;
import com.android.flickrimagesearch.ImageSearchActivity;
import com.android.flickrimagesearch.adapter.ImageAdapter;
import com.android.flickrimagesearch.model.Data;
import com.android.flickrimagesearch.model.ImageSearchResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * ResponseListener handles all the responses.
 */
public class ResponseListener {

    private Context context;
    private String url;

    public interface SetData{
        public void setData(Data data);
    }

    public ResponseListener(Context context , String url){
        this.context = context;
        this.url = url;
    }

    public void makeServiceCall(){
        Response.Listener responseListener = new Response.Listener<Data>() {
            @Override
            public void onResponse(Data response) {
                // Do something with the response
                Log.i(getClass().getName(), "The Response is " + response);
                    ((ImageSearchActivity) context).setData(response);

            }
        };

        Response.ErrorListener errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                Log.i(getClass().getName(), "The Response is " + error.toString());
                ((ImageSearchActivity) context).showError();
            }
        };

        GsonRequest gsonRequest = new GsonRequest(url,ImageSearchResponse.class, null ,responseListener, errorListener);
        // Add the request to the RequestQueue.
        FlickrImageSearchApplication.getmInstance().addToRequestQueue(gsonRequest ,"");
    }

}
