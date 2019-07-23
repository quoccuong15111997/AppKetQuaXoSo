package com.example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonKQSX {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("feed")
    @Expose
    private String feed;

    @SerializedName("item")
    @Expose
    private List<KQSX> listItem;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public List<KQSX> getListItem() {
        return listItem;
    }

    public void setListItem(List<KQSX> listItem) {
        this.listItem = listItem;
    }
}
