package com.hrms.common.to;

import java.io.Serializable;

public class HrMstDeptSubdeptPKTO implements Serializable {

    private int compCode;
    private String deptCode;
    private String subDeptCode;

    public int getCompCode() {
        return compCode;
    }

    public void setCompCode(int compCode) {
        this.compCode = compCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getSubDeptCode() {
        return subDeptCode;
    }

    public void setSubDeptCode(String subDeptCode) {
        this.subDeptCode = subDeptCode;
    }
}
