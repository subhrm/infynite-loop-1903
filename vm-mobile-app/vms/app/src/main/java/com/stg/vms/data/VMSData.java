package com.stg.vms.data;

import java.io.Serializable;

public class VMSData implements Serializable {
    private static final VMSData ourInstance = new VMSData();
    private String qrCodeData = null;

    private VMSData() {
    }

    static VMSData getInstance() {
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
}
