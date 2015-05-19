package com.android.flickrimagesearch;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.flickrimagesearch.adapter.ImageAdapter;
import com.android.flickrimagesearch.model.Data;
import com.android.flickrimagesearch.model.ImageSearchResponse;
import com.android.flickrimagesearch.model.Photo;
import com.android.flickrimagesearch.network.ResponseListener;


/**
 * This is the entry point (Launcher) activity which allows user to search images from flickr using keywords.
 */
public class ImageSearchActivity extends ActionBarActivity implements View.OnClickListener , AdapterView.OnItemClickListener , ResponseListener.SetData{
    private GridView imagesGridView;
    private EditText searchText;
    private int page;
    private boolean loading = false;
    private ImageSearchResponse data;
    private ImageAdapter imageAdapter;
    private LinearLayout loadMoreView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_search);
        imagesGridView = (GridView) findViewById(R.id.imagesGridview);

        ((Button) findViewById(R.id.search_button)).setOnClickListener(this);
        searchText= (EditText) findViewById(R.id.search_string);
        imagesGridView.setOnScrollListener(onScrollListener);
        loadMoreView = (LinearLayout) findViewById(R.id.load_more_view);
        imagesGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_button:
                //Hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                String url=FlickrSearchConstants.BASE_URL+FlickrSearchConstants.PARAMETER_SERACH+FlickrSearchConstants.AND_API_KEY+FlickrSearchConstants.API_KEY
                        +FlickrSearchConstants.AND_TEXT+searchText.getText().toString()+FlickrSearchConstants.AND_PER_PAGE+FlickrSearchConstants.PER_PAGE_VALUE
                    + FlickrSearchConstants.AND_PAGE+FlickrSearchConstants.PAGE_VALUE+FlickrSearchConstants.AND_FORMAT+FlickrSearchConstants.FORMAT_VALUE
                    + FlickrSearchConstants.AND_JSONCALLBACK+FlickrSearchConstants.JSON_CALLBACK_VALUE;
                ResponseListener responseListener = new ResponseListener(this,url);
                loading= true;
                responseListener.makeServiceCall();
                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Log.i(getClass().getName(), "onItemClicked");
        Photo photoClicked =
                ((ImageAdapter) adapterView.getAdapter()).getItem(position);
        Intent detailViewIntent = new Intent(this,ShowImageDetailActivity.class);
        detailViewIntent.putExtra(FlickrSearchConstants.PHOTOCLICKED,photoClicked);
        startActivity(detailViewIntent);
    }

    public void setResponse(Data data){
        loading= false;
        if(this.data ==null){
            this.data = (ImageSearchResponse)data;
            imageAdapter = new ImageAdapter(ImageSearchActivity.this,this.data);
            imagesGridView.setAdapter(imageAdapter);
        }else{
            ((ImageSearchResponse) this.data).getPhotos().getPhoto().addAll(((ImageSearchResponse) data).getPhotos().getPhoto());
            imageAdapter.notifyDataSetChanged();
            if(loadMoreView.getVisibility() == View.VISIBLE){
                loadMoreView.setVisibility(View.GONE);
            }
        }


    }

    public void showError(){
        loading = false;
        new AlertDialog.Builder(this).setTitle("Error!").setMessage(getResources().getString(R.string.service_error)).setNeutralButton("OK",null).show();

    }

    @Override
    public void setData(Data data) {
        loading= false;
        if(this.data ==null){
            this.data = (ImageSearchResponse)data;
            imageAdapter = new ImageAdapter(ImageSearchActivity.this,this.data);
            imagesGridView.setAdapter(imageAdapter);
        }else{
            ((ImageSearchResponse) this.data).getPhotos().getPhoto().addAll(((ImageSearchResponse) data).getPhotos().getPhoto());
            imageAdapter.notifyDataSetChanged();
            if(loadMoreView.getVisibility() == View.VISIBLE){
                loadMoreView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Scroll listener for pagination. As soon as you reach end of first data set pur a request for the next page and on service response append the data to the existing
     */
    private AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
            if (!loading && firstVisibleItem == (totalItemCount - visibleItemCount) && totalItemCount >0 && page < ((ImageSearchResponse)data).getPhotos().getPages()) {
                loading = true;
                page = page + 1;
                //Call service with new page index.
                loadMoreView.setVisibility(View.VISIBLE);

                String url=FlickrSearchConstants.BASE_URL+FlickrSearchConstants.PARAMETER_SERACH+FlickrSearchConstants.AND_API_KEY+FlickrSearchConstants.API_KEY
                        +FlickrSearchConstants.AND_TEXT+searchText.getText().toString()+FlickrSearchConstants.AND_PER_PAGE+FlickrSearchConstants.PER_PAGE_VALUE
                        + FlickrSearchConstants.AND_PAGE+page+FlickrSearchConstants.AND_FORMAT+FlickrSearchConstants.FORMAT_VALUE
                        + FlickrSearchConstants.AND_JSONCALLBACK+FlickrSearchConstants.JSON_CALLBACK_VALUE;

                ResponseListener responseListener = new ResponseListener(ImageSearchActivity.this,url);
                responseListener.makeServiceCall();
            }
        }
    };
}
