package com.stg.vms.data;

import com.stg.vms.model.UserProfile;

import java.io.Serializable;

public class VMSData implements Serializable {
    private static final VMSData ourInstance = new VMSData();
    private String visitorPhoto = null;
    private String newPhoto = null;
    private UserProfile userProfile;
    private String accessToken;
    private String visitorId;
    private boolean encrypted;
    private boolean searchByPhoto;

    private VMSData() {
    }

    public static VMSData getInstance() {
        return ourInstance;
    }

    public void clear() {
        visitorPhoto = null;
        newPhoto = null;
        userProfile = null;
        accessToken = null;
        visitorId = null;
        encrypted = false;
        searchByPhoto = false;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getVisitorPhoto() {
        return visitorPhoto;
    }

    public void setVisitorPhoto(String visitorPhoto) {
        this.visitorPhoto = visitorPhoto;
    }

    public String getNewPhoto() {
        return newPhoto;
    }

    public void setNewPhoto(String newPhoto) {
        this.newPhoto = newPhoto;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean isSearchByPhoto() {
        return searchByPhoto;
    }

    public void setSearchByPhoto(boolean searchByPhoto) {
        this.searchByPhoto = searchByPhoto;
    }
}
