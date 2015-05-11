package com.android.flickrimagesearch.model;

import java.io.Serializable;
import java.util.List;

/**
 * Parent object for photo search.
 */
public class Photos implements Serializable , Data{
    private int page;
    private long pages;
    private int perpage;
    private long total;
    private List<Photo> photo;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
