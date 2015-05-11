package com.android.flickrimagesearch;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.android.flickrimagesearch.adapter.ImageAdapter;
import com.android.flickrimagesearch.model.Data;
import com.android.flickrimagesearch.model.ImageSearchResponse;
import com.android.flickrimagesearch.network.ResponseListener;


/**
 * This is the entry point (Launcher) activity which allows user to search images from flickr using keywords.
 */
public class ImageSearchActivity extends Activity implements View.OnClickListener , AdapterView.OnItemClickListener{
    private GridView imagesGridView;
    private EditText searchText;
    private int page;
    private boolean loading = false;
    private ImageSearchResponse data;
    private ImageAdapter imageAdapter;
    private LinearLayout loadMoreView;
    private String BASE_URL = "https://api.flickr.com/services/rest/";
    private String PARAMETER_SERACH = "method=flickr.photos.search";


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
                String url ="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=0f43106ac04befee4f14949eeba90455&text="+searchText.getText().toString()+"&per_page=100&page=1&format=json&nojsoncallback=1";
                ResponseListener responseListener = new ResponseListener(this,url);
                loading= true;
                responseListener.makeServiceCall();
                break;
            default:
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void setResponse(Data data){
        Log.i(getClass().getSimpleName(),"Setting response for page  "+page);
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
        new AlertDialog.Builder(this).setTitle("Error!").setMessage("The service is not responding.Please checkBack soon.").show();

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
                Log.i(getClass().getSimpleName(),"The page number is "+page);
                //Call service with new page index.
                loadMoreView.setVisibility(View.VISIBLE);
                String url ="https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=0f43106ac04befee4f14949eeba90455&text="+searchText.getText().toString()+"&per_page=100&page="+page+"&format=json&nojsoncallback=1";
                ResponseListener responseListener = new ResponseListener(ImageSearchActivity.this,url);
                responseListener.makeServiceCall();
            }
        }
    };
}
