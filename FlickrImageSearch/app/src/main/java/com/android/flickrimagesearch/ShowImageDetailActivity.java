package com.android.flickrimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.android.flickrimagesearch.model.Photo;
import com.android.volley.toolbox.NetworkImageView;

/**
 * This activity shows the single image with the description of image.
 */
public class ShowImageDetailActivity extends ActionBarActivity {
    private NetworkImageView imageView;
    private TextView photoTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showimage_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (NetworkImageView) findViewById(R.id.detail_imageview);
        photoTitle= (TextView) findViewById(R.id.detail_text);

        if(getIntent()!=null && getIntent().getSerializableExtra(FlickrSearchConstants.PHOTOCLICKED)!=null) {
            Photo photo = (Photo) getIntent().getSerializableExtra(FlickrSearchConstants.PHOTOCLICKED);
            String url = FlickrSearchConstants.BASE_IMAGE + photo.getFarm() + FlickrSearchConstants.CONTEXT_IMAGE + photo.getServer() + "/" +
                    photo.getId() + "_" + photo.getSecret() + FlickrSearchConstants.IMAGE_EXTENSION_JPEG;

            imageView.setAdjustViewBounds(true);
            imageView.setImageUrl(url, FlickrImageSearchApplication.getmInstance().getImageLoader());
            photoTitle.setText(photo.getTitle());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
