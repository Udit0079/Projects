/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.report;

/**
 *
 * @author root
 */
public class MailingDetailsPojo {
    private int srNo;
    private String serviceType;
    private String custId;
    private String stmtTime;
    private String stmtFile;
    private String fileSize;
    private String stmtDueDate;
    private String custName;
    private String acNo;

   
    
    public int getSrNo() {
        return srNo;
    }

    public void setSrNo(int srNo) {
        this.srNo = srNo;
    }
    
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getStmtTime() {
        return stmtTime;
    }

    public void setStmtTime(String stmtTime) {
        this.stmtTime = stmtTime;
    }

    public String getStmtFile() {
        return stmtFile;
    }

    public void setStmtFile(String stmtFile) {
        this.stmtFile = stmtFile;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getStmtDueDate() {
        return stmtDueDate;
    }

    public void setStmtDueDate(String stmtDueDate) {
        this.stmtDueDate = stmtDueDate;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAcNo() {
        return acNo;
    }

    public void setAcNo(String acNo) {
        this.acNo = acNo;
    }
  }
