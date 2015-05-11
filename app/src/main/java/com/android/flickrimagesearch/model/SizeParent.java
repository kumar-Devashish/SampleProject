package com.android.flickrimagesearch.model;

import java.io.Serializable;
import java.util.List;


public class SizeParent implements Serializable , Data {
    private int canblog;
    private int canprint;
    private int candownload;
    private List<Size> size;

    public int getCanblog() {
        return canblog;
    }

    public void setCanblog(int canblog) {
        this.canblog = canblog;
    }

    public int getCandownload() {
        return candownload;
    }

    public void setCandownload(int candownload) {
        this.candownload = candownload;
    }

    public int getCanprint() {
        return canprint;
    }

    public void setCanprint(int canprint) {
        this.canprint = canprint;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }
}
