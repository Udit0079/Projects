package com.hrms.common.to;

import java.io.Serializable;

public class HrMstBusPKTO implements Serializable {

    private int compCode;
    private String busNo;

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }
}
