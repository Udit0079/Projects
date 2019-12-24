/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dto.npci.cts;

import java.io.Serializable;
import java.util.List;

public class FileHeaderDTO implements Serializable {

    private String creationDate;
    private String fileId;
    private String sessionNo;
    private String sessionDate;
    private String settlementDate;
    private String versionNo;
    private String testFileIndicator;
    private FileSummaryDTO fileSummary;
    private List<ItemDTO> items;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSessionNo() {
        return sessionNo;
    }

    public void setSessionNo(String sessionNo) {
        this.sessionNo = sessionNo;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(String sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public FileSummaryDTO getFileSummary() {
        return fileSummary;
    }

    public void setFileSummary(FileSummaryDTO fileSummary) {
        this.fileSummary = fileSummary;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getTestFileIndicator() {
        return testFileIndicator;
    }

    public void setTestFileIndicator(String testFileIndicator) {
        this.testFileIndicator = testFileIndicator;
    }
}
