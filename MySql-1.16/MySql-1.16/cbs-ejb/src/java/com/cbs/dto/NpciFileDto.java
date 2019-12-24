/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto;

import java.io.Serializable;
import java.math.BigInteger;

public class NpciFileDto implements Serializable {

    private BigInteger fileNo;
    private String fileGenDt;
    private String fileName;
    private String fileGenBy;
    private String orignalFileName;
    private String partname;

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

    public String getOrignalFileName() {
        return orignalFileName;
    }

    public void setOrignalFileName(String orignalFileName) {
        this.orignalFileName = orignalFileName;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }
 }
