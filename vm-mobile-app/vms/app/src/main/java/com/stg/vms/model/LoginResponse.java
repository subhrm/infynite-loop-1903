package com.stg.vms.model;

import java.util.List;

public class LoginResponse {
    private long userId;
    private String userName;
    private String email;
    private String userRole;
    private TodaysVisitors todaysVisitors;
    private List<VisitorsLastDay> visitorLastDays;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
