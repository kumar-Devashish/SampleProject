package com.android.flickrimagesearch.model;

import java.io.Serializable;

/**
 * ImageResponse For any keyword search.
 */
public class ImageSearchResponse implements Serializable , Data{
    private Photos photos;
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
