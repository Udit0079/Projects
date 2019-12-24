/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface TdAuthorizationManagementFacadeRemote {

    public List accountNoList(String brCode) throws ApplicationException;

    public String authorizeAction(String authBy, String batNo, String brCode) throws ApplicationException;

    public List getOldAndNewData(String batNo, String brCode) throws ApplicationException;

    public List gridLoadingDuplicateReceipt(String orgBrnCode) throws ApplicationException;

    public String authorizationChange(String accountNumber, float vchNo, String fddt, float receiptNo, float roi, String userName, String brnCode, String currentDate)
            throws ApplicationException;

    public List tableAuthorize(String orgnCode) throws ApplicationException;

    public String tdIssueAuthorize(String userName, float stSNo, float stRNfrom, float stRNTo, String stBookNo, String series, String scheme, String orgnBrCode)
            throws ApplicationException;
    
    public List getUnAuthRecptNo(String acNo) throws ApplicationException;
    
    public List getRenewedReceptDetails(String acNo, float rtNo) throws ApplicationException;
    
    public String deleteTdRenewalDetails(String acNo, float rtNo) throws ApplicationException;
    
    public String saveTdRenewalDetails(String acno, String renewedTdDt, String renewedMatDt, double TdsToBeDed, int year ,int month, int days,
            double rtNoHide, String IntOpt, int tdDayMth, int tdDayCum, double renewalAmount, double ReceiptNo, String series, String receiptnew,
            String EnterBy, double rAmt, double balint, String renewalAcc, double renewedMatAmt, double matpre, String stYear, String endYear, double ROI,
            double TDSDeductedLast, double totalIntRateCal, double TdsDed, double ClActTdsToBeDeducted, double ClActTdsDeducted, 
            double ClActInt, String appRoiFrom, String brCode) throws ApplicationException;
}
