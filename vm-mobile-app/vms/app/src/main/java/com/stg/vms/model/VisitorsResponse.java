package com.stg.vms.model;

import java.util.List;

public class VisitorsResponse {
    private TodaysVisitors todaysVisitors;
    private List<VisitorsLastDay> visitorLastDays;

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
