/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.entity.neftrtgs;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "cbs_auto_neft_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CbsAutoNeftDetails.findAll", query = "SELECT c FROM CbsAutoNeftDetails c"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByHostName", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.hostName = :hostName"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByExpInterval", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.expInterval = :expInterval"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByUserName", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.userName = :userName"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByPassword", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.password = :password"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByOwLocalFilePath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.owLocalFilePath = :owLocalFilePath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByOwRemoteFilePath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.owRemoteFilePath = :owRemoteFilePath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByIwLocalFilePath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.iwLocalFilePath = :iwLocalFilePath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByIwRemoteFilePath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.iwRemoteFilePath = :iwRemoteFilePath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByLocalReportPath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.localReportPath = :localReportPath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByRemoteReportPath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.remoteReportPath = :remoteReportPath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByOwLocalFileBackupPath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.owLocalFileBackupPath = :owLocalFileBackupPath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByIwLocalFileBackupPath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.iwLocalFileBackupPath = :iwLocalFileBackupPath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByLocalReportBackupPath", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.localReportBackupPath = :localReportBackupPath"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByFileNamePrefix", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.fileNamePrefix = :fileNamePrefix"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByNeftBankName", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.neftBankName = :neftBankName"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByProcessType", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.processType = :processType"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByIwHead", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.iwHead = :iwHead"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByOwHead", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.owHead = :owHead"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByProcess", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.process = :process"),
    @NamedQuery(name = "CbsAutoNeftDetails.findByIwFileType", query = "SELECT c FROM CbsAutoNeftDetails c WHERE c.iwFileType = :iwFileType")})
public class CbsAutoNeftDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "HOST_NAME")
    private String hostName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EXP_INTERVAL")
    private String expInterval;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USER_NAME")
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OW_LOCAL_FILE_PATH")
    private String owLocalFilePath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OW_REMOTE_FILE_PATH")
    private String owRemoteFilePath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IW_LOCAL_FILE_PATH")
    private String iwLocalFilePath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IW_REMOTE_FILE_PATH")
    private String iwRemoteFilePath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LOCAL_REPORT_PATH")
    private String localReportPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "REMOTE_REPORT_PATH")
    private String remoteReportPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "OW_LOCAL_FILE_BACKUP_PATH")
    private String owLocalFileBackupPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IW_LOCAL_FILE_BACKUP_PATH")
    private String iwLocalFileBackupPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LOCAL_REPORT_BACKUP_PATH")
    private String localReportBackupPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FILE_NAME_PREFIX")
    private String fileNamePrefix;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "NEFT_BANK_NAME")
    private String neftBankName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PROCESS_TYPE")
    private String processType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "IW_HEAD")
    private String iwHead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "OW_HEAD")
    private String owHead;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PROCESS")
    private String process;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "IW_FILE_TYPE")
    private String iwFileType;

    public CbsAutoNeftDetails() {
    }

    public CbsAutoNeftDetails(String hostName) {
        this.hostName = hostName;
    }

    public CbsAutoNeftDetails(String hostName, String expInterval, String userName, String password, String owLocalFilePath, String owRemoteFilePath, String iwLocalFilePath, String iwRemoteFilePath, String localReportPath, String remoteReportPath, String owLocalFileBackupPath, String iwLocalFileBackupPath, String localReportBackupPath, String fileNamePrefix, String neftBankName, String processType, String iwHead, String owHead, String process, String iwFileType) {
        this.hostName = hostName;
        this.expInterval = expInterval;
        this.userName = userName;
        this.password = password;
        this.owLocalFilePath = owLocalFilePath;
        this.owRemoteFilePath = owRemoteFilePath;
        this.iwLocalFilePath = iwLocalFilePath;
        this.iwRemoteFilePath = iwRemoteFilePath;
        this.localReportPath = localReportPath;
        this.remoteReportPath = remoteReportPath;
        this.owLocalFileBackupPath = owLocalFileBackupPath;
        this.iwLocalFileBackupPath = iwLocalFileBackupPath;
        this.localReportBackupPath = localReportBackupPath;
        this.fileNamePrefix = fileNamePrefix;
        this.neftBankName = neftBankName;
        this.processType = processType;
        this.iwHead = iwHead;
        this.owHead = owHead;
        this.process = process;
        this.iwFileType = iwFileType;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getExpInterval() {
        return expInterval;
    }

    public void setExpInterval(String expInterval) {
        this.expInterval = expInterval;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOwLocalFilePath() {
        return owLocalFilePath;
    }

    public void setOwLocalFilePath(String owLocalFilePath) {
        this.owLocalFilePath = owLocalFilePath;
    }

    public String getOwRemoteFilePath() {
        return owRemoteFilePath;
    }

    public void setOwRemoteFilePath(String owRemoteFilePath) {
        this.owRemoteFilePath = owRemoteFilePath;
    }

    public String getIwLocalFilePath() {
        return iwLocalFilePath;
    }

    public void setIwLocalFilePath(String iwLocalFilePath) {
        this.iwLocalFilePath = iwLocalFilePath;
    }

    public String getIwRemoteFilePath() {
        return iwRemoteFilePath;
    }

    public void setIwRemoteFilePath(String iwRemoteFilePath) {
        this.iwRemoteFilePath = iwRemoteFilePath;
    }

    public String getLocalReportPath() {
        return localReportPath;
    }

    public void setLocalReportPath(String localReportPath) {
        this.localReportPath = localReportPath;
    }

    public String getRemoteReportPath() {
        return remoteReportPath;
    }

    public void setRemoteReportPath(String remoteReportPath) {
        this.remoteReportPath = remoteReportPath;
    }

    public String getOwLocalFileBackupPath() {
        return owLocalFileBackupPath;
    }

    public void setOwLocalFileBackupPath(String owLocalFileBackupPath) {
        this.owLocalFileBackupPath = owLocalFileBackupPath;
    }

    public String getIwLocalFileBackupPath() {
        return iwLocalFileBackupPath;
    }

    public void setIwLocalFileBackupPath(String iwLocalFileBackupPath) {
        this.iwLocalFileBackupPath = iwLocalFileBackupPath;
    }

    public String getLocalReportBackupPath() {
        return localReportBackupPath;
    }

    public void setLocalReportBackupPath(String localReportBackupPath) {
        this.localReportBackupPath = localReportBackupPath;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public String getNeftBankName() {
        return neftBankName;
    }

    public void setNeftBankName(String neftBankName) {
        this.neftBankName = neftBankName;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getIwHead() {
        return iwHead;
    }

    public void setIwHead(String iwHead) {
        this.iwHead = iwHead;
    }

    public String getOwHead() {
        return owHead;
    }

    public void setOwHead(String owHead) {
        this.owHead = owHead;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getIwFileType() {
        return iwFileType;
    }

    public void setIwFileType(String iwFileType) {
        this.iwFileType = iwFileType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hostName != null ? hostName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CbsAutoNeftDetails)) {
            return false;
        }
        CbsAutoNeftDetails other = (CbsAutoNeftDetails) object;
        if ((this.hostName == null && other.hostName != null) || (this.hostName != null && !this.hostName.equals(other.hostName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.cbs.entity.neftrtgs.CbsAutoNeftDetails[ hostName=" + hostName + " ]";
    }
    
}
