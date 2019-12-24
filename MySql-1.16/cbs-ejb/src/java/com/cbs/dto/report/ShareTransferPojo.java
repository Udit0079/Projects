/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class ShareTransferPojo implements Serializable{
    private Date trfDate;
    private int certNo;
    private String trfrFolioNO;
    private String trfrName;
    private String trfereFolioNO;
    private String trfereName;
    private String remarks;
    private int minShare;
    private int maxShare;
    private int noOfShare;

    public int getCertNo() {
        return certNo;
    }

    public void setCertNo(int certNo) {
        this.certNo = certNo;
    }

    public int getMaxShare() {
        return maxShare;
    }

    public void setMaxShare(int maxShare) {
        this.maxShare = maxShare;
    }

    public int getMinShare() {
        return minShare;
    }

    public void setMinShare(int minShare) {
        this.minShare = minShare;
    }

    public int getNoOfShare() {
        return noOfShare;
    }

    public void setNoOfShare(int noOfShare) {
        this.noOfShare = noOfShare;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getTrfDate() {
        return trfDate;
    }

    public void setTrfDate(Date trfDate) {
        this.trfDate = trfDate;
    }

    public String getTrfereFolioNO() {
        return trfereFolioNO;
    }

    public void setTrfereFolioNO(String trfereFolioNO) {
        this.trfereFolioNO = trfereFolioNO;
    }

    public String getTrfereName() {
        return trfereName;
    }

    public void setTrfereName(String trfereName) {
        this.trfereName = trfereName;
    }

    public String getTrfrFolioNO() {
        return trfrFolioNO;
    }

    public void setTrfrFolioNO(String trfrFolioNO) {
        this.trfrFolioNO = trfrFolioNO;
    }

    public String getTrfrName() {
        return trfrName;
    }

    public void setTrfrName(String trfrName) {
        this.trfrName = trfrName;
    }
     
    
}
