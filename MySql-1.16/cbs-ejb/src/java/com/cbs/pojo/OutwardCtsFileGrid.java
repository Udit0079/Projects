/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.pojo;

import java.math.BigInteger;

/**
 *
 * @author root
 */
public class OutwardCtsFileGrid {
    private BigInteger fileNo;
    private String fileGenDt;
    private String fileName;
    private String fileGenBy;

    public BigInteger getFileNo() {
        return fileNo;
    }

    public void setFileNo(BigInteger fileNo) {
        this.fileNo = fileNo;
    }

    public String getFileGenDt() {
        return fileGenDt;
    }

    public void setFileGenDt(String fileGenDt) {
        this.fileGenDt = fileGenDt;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileGenBy() {
        return fileGenBy;
    }

    public void setFileGenBy(String fileGenBy) {
        this.fileGenBy = fileGenBy;
    }
}
