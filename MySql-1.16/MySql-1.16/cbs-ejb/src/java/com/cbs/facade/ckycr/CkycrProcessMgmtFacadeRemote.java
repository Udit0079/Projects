/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import java.io.File;
import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CkycrProcessMgmtFacadeRemote {

    public boolean isModuleOn(String moduleName) throws Exception;

    public void ckycrProcess();

    public String[] getParameterInfoReportCodes() throws Exception;

    public String[] getCbsParameterInfoCodes() throws Exception;

    public String generateManualUploadFile(List<CKYCRRequestPojo> requestList, String alphaCode) throws Exception;

    public void processDownloadRequest() throws Exception;

    public String parseManualUploadResponse(File responseFile, String responseBy) throws Exception;

    public String parseManualDownloadResponse() throws Exception;

    public List<CKYCRRequestPojo> getGenUploadData(String branchCode, String fromDate, String toDate) throws Exception;

    public List<CKYCRRequestPojo> getGenDownloadManualData(String branchCode, String fromDate, String toDate) throws Exception;

    public String generateManualDownloadFile(List<CKYCRRequestPojo> requestList, String alphaCode) throws Exception;
    
    public String updateModuleConfigProperty(String key, String value) throws ApplicationException;
}
