package com.stg.vms.model;

public class SearchByPhotoRequest {
    private String image;

    public SearchByPhotoRequest() {
    }

    public SearchByPhotoRequest(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
