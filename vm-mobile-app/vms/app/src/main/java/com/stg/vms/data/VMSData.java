package com.stg.vms.data;

import com.stg.vms.model.TodaysVisitors;
import com.stg.vms.model.UserProfile;
import com.stg.vms.model.VisitorsLastDay;

import java.io.Serializable;
import java.util.List;

public class VMSData implements Serializable {
    private static final VMSData ourInstance = new VMSData();
    private String qrCodeData = null;
    private UserProfile userProfile;
    private TodaysVisitors todaysVisitors;
    private List<VisitorsLastDay> visitorLastDays;

    private VMSData() {
    }

    public static VMSData getInstance() {
        return ourInstance;
    }

    public static VMSData getOurInstance() {
        return ourInstance;
    }

    public String getQrCodeData() {
        return qrCodeData;
    }

    public void setQrCodeData(String qrCodeData) {
        this.qrCodeData = qrCodeData;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public TodaysVisitors getTodaysVisitors() {
        return todaysVisitors;
    }

    public void setTodaysVisitors(TodaysVisitors todaysVisitors) {
        this.todaysVisitors = todaysVisitors;
    }

    public List<VisitorsLastDay> getVisitorLastDays() {
        return visitorLastDays;
    }

    public void setVisitorLastDays(List<VisitorsLastDay> visitorLastDays) {
        this.visitorLastDays = visitorLastDays;
    }
}
