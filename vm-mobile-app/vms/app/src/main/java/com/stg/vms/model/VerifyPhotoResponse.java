package com.stg.vms.model;

public class VerifyPhotoResponse {
    private String similarity;

    public VerifyPhotoResponse() {
    }

    public VerifyPhotoResponse(String similarity) {
        this.similarity = similarity;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }
}
