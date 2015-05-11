package com.android.flickrimagesearch.model;

import java.io.Serializable;
import java.util.List;

/**
 * To Get the sizes of the images
 */
public class GetSizesResponse implements Serializable , Data{

    private String stat;
    private SizeParent sizes;

    public SizeParent getSizes() {
        return sizes;
    }

    public void setSizes(SizeParent sizes) {
        this.sizes = sizes;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
