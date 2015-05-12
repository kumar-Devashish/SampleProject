package com.android.flickrimagesearch;

/**
 *
 */
public class FlickrSearchConstants {

    public static final String API_KEY="0f43106ac04befee4f14949eeba90455";
    public static final String BASE_URL ="https://api.flickr.com/services/rest/?";
    public static final String PARAMETER_SERACH = "method=flickr.photos.search";
    public static final String AND_API_KEY="&api_key=";
    public static final String AND_TEXT="&text=";
    public static final String AND_PER_PAGE="&per_page=";
    public static final String AND_PAGE="&page=";
    public static final String AND_FORMAT="&format=";
    public static final String AND_JSONCALLBACK="&nojsoncallback=";

    public static final String PER_PAGE_VALUE="100";
    public static final String PAGE_VALUE="1";
    public static final String FORMAT_VALUE="json";
    public static final String JSON_CALLBACK_VALUE="1";

    public static final String BASE_IMAGE="http://farm";
    public static final String CONTEXT_IMAGE=".staticflickr.com/";
    public static final String IMAGE_EXTENSION_JPEG=".jpg";

    //Keys
    public static final String PHOTOCLICKED="PhotoClicked";
}
