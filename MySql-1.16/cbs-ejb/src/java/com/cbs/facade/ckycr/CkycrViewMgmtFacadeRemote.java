/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ckycr;

import com.cbs.dto.ckycr.CKYCRDownloadDetail30;
import com.cbs.dto.ckycr.CKYCRDownloadDetail60;
import com.cbs.exception.ApplicationException;

import com.cbs.pojo.CKYCRDownloadPojo;

import com.cbs.dto.ckycr.CKYCRRequestPojo;
import com.cbs.dto.ckycr.CKYCRFailurePojo;
import com.cbs.dto.ckycr.CkycrRealTimeRequestPojo;
import com.cbs.dto.ckycr.CkycrRealTimeSearchDetailsPojo;
import com.cbs.web.ckyc.pojo.DwndResPidData.IDENTITYDETAILS;
import com.cbs.web.ckyc.pojo.DwndResPidData.LOCALADDRESSDETAILS;
import com.cbs.web.ckyc.pojo.DwndResPidData.PERSONALDETAILS;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CkycrViewMgmtFacadeRemote {

    //********* Added by Manish Kumar
    public List<CKYCRRequestPojo> getCbsCustomerMasterDetailIndividual(String customerIdOrCKYCRNo, String branchCode) throws ApplicationException;// this method rename from getCbsMasterDetailIndividual

    public List<CKYCRRequestPojo> getCbsCustomerMasterDetailBulk(String branchCode) throws ApplicationException;

    public int insertCKYCRRequestDetails(List<CKYCRRequestPojo> customerList, String mode, String type, String requestBy, boolean checkAll, String fetchMode) throws Exception;

    public int downloadCbsCustomerMasterDetail(String mode, String type, String customerIdOrCKYCRNo, String dateOfBirth, String orgnBrCode, String requestBy, String fetchMode) throws ApplicationException;

    public List getBranchList() throws ApplicationException;

    public List<CKYCRFailurePojo> getFailureCustomerDetail(String branchCode, String mode, String fromDate, String toDate) throws ApplicationException;

    public List<CKYCRFailurePojo> getInfoForPrintFailureReport(String customerId, String branchCode, String mode) throws ApplicationException;

    public List<CKYCRRequestPojo> getInfoForPrintCKYCRReport(String branchCode, String mode, String reportType, String fromDate, String toDate) throws ApplicationException;

//    public String generateUploadFile(String fromDate, String toDate)throws Exception;
    public List<CKYCRRequestPojo> generateDownloadFile(String mode, String fromDate, String toDate, String branchCode) throws ApplicationException;

    public int checkPrameterInfoReport(String parameter) throws ApplicationException;
    //*********

    public String updateCKYCRDetailByCustId(String customerId, String updateMode, String requestBy) throws ApplicationException;

    public CKYCRRequestPojo getCKYCRDetailByCustId(String customerId) throws ApplicationException;

    public List getUploadUpdateRequestFromBranches() throws Exception;

    public List getUploadRequestFromScheduler(int limit) throws Exception;

    public String getNextBatchNo(String mode) throws Exception;

    public List<CKYCRRequestPojo> getCKYCRFailureRequestReport(String branchCode) throws ApplicationException;

    public boolean isValidPostalCode(String pinCode, String stateCode) throws ApplicationException;

//    public boolean storeCKYCRData() throws ApplicationException;
    public CKYCRDownloadPojo getCKYCRClientDetails(String ckycrNo, String dob) throws ApplicationException;

    public List<CKYCRRequestPojo> getDownloadCKYCRReport(String branchCode, String mode, String reportType, String fromDate, String toDate) throws ApplicationException;

    public CKYCRDownloadPojo setCKYCRDownload(String[] data) throws Exception;

    public CKYCRDownloadDetail30 setCKYCRDownloadDetail30(String[] data) throws Exception;

    public CKYCRDownloadDetail60 setCKYCRDownloadDetail60(String[] data) throws Exception;

    public CKYCRDownloadPojo setCKYCRDownload20FromRealResponse(PERSONALDETAILS personalDetail) throws Exception;

    public CKYCRDownloadDetail30 setCKYCRDownloadDetail30FromRealResponse(IDENTITYDETAILS.IDENTITY identity) throws Exception;

    public CKYCRDownloadDetail60 setCKYCRDownloadDetail60FromRealResponse(LOCALADDRESSDETAILS.LOCALADDRESS localAdd) throws Exception;

    public String createCustomerIdByCKYCR(String ckycrNo, String dob, String userName, String brncode) throws ApplicationException;

    public boolean isCKYCRCustomerIdExist(String ckycrNo) throws ApplicationException;

    public CkycrRealTimeSearchDetailsPojo getCkycrSearchResponse(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException;

    public String getCkycrDownloadResponse(CkycrRealTimeRequestPojo ckycrRequest) throws ApplicationException;

    public boolean isCKYCRDataExist(String ckycrNo) throws ApplicationException;
}
