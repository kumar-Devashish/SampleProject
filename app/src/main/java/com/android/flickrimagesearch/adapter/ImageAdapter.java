package com.android.flickrimagesearch.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.flickrimagesearch.FlickrImageSearchApplication;
import com.android.flickrimagesearch.model.ImageSearchResponse;
import com.android.volley.toolbox.NetworkImageView;

/**
 * This is the adapted for the GridView
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private NetworkImageView imageView;
    private ImageSearchResponse imageSearchResponse;

    public ImageAdapter(Context c , ImageSearchResponse response) {
        mContext = c;
        this.imageSearchResponse = response;
    }

    public int getCount() {
        return imageSearchResponse.getPhotos().getPhoto().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new NetworkImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setBackgroundColor(Color.BLACK);
        } else {
            imageView = (NetworkImageView) convertView;
        }

        String url = "http://farm" + imageSearchResponse.getPhotos().getPhoto().get(position).getFarm() + ".staticflickr.com/" + imageSearchResponse.getPhotos().getPhoto().get(position).getServer() + "/" +
                imageSearchResponse.getPhotos().getPhoto().get(position).getId() + "_" + imageSearchResponse.getPhotos().getPhoto().get(position).getSecret() +".jpg";

        imageView.setAdjustViewBounds(true);
        imageView.setImageUrl(url , FlickrImageSearchApplication.getmInstance().getImageLoader());

        return imageView;
    }

}
