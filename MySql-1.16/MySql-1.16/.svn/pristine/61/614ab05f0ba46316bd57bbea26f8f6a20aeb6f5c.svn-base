/*
Document   : MinBalanceCharges
Created on : 25 Aug, 2010, 5:28:22 PM
Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]

 */
package com.cbs.facade.misc;

import com.cbs.dto.CashHandlingChargeGridData;
import com.cbs.dto.ChargesObject;
import com.cbs.dto.agentCommPojo;
import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AtmChargePostingPojo;
import com.cbs.pojo.AtmReversalPostingPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface MinBalanceChargesFacadeRemote {

    List accountTypeDropDown() throws ApplicationException;

    List setAcToCredit(String acType) throws ApplicationException;

    public String BDateCheck(String orgnBrCode) throws ApplicationException;

    String serverDateCheck() throws ApplicationException;

    public String exitSoft(String usr) throws ApplicationException;

    public List<ChargesObject> minBalanceChargesCaculate(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException;

    public String minBalanceChargesPost(List<ChargesObject> rList, String acType, String glAcNo, String authBy, String orgnBrCode, double total, String curDate, String fromDt, String toDt) throws ApplicationException;

    public List yearEnd(String brncode) throws ApplicationException;

    List getAccountType() throws ApplicationException;

    List setAcToCreditInspection(String acType) throws ApplicationException;

    public List<ChargesObject> loanInspectionChargesCalculate(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException;

    public String loanInspectionChargesPost(String acType, String glAcNo, String fromDt, String toDt, String authBy,
            String orgnBrCode, String todayDate) throws ApplicationException;

    public java.util.List<com.cbs.dto.report.MinBalanceChargesPostPojo> minBalanceChargesPostReport(float trsno, java.lang.String acType, java.lang.String enterBy, java.lang.String enteryDt, java.lang.String brCode)throws ApplicationException;
    
    public String getAtmChargeCode() throws com.cbs.exception.ApplicationException;
    
    public List getAtmChargeParam() throws com.cbs.exception.ApplicationException;
    
    public List getAllAtmAccount(String brnCode, String frDt, String toDt) throws com.cbs.exception.ApplicationException;
    
    public String getAllFromDt(String brnCode, String want) throws ApplicationException;
    
    public String atmChargesPost(List<AtmChargePostingPojo> rList, String glAcNo, String usrName, String brnCode, String orgnBrCode, double total, String curDate, String fromDt, String toDt) throws ApplicationException;

    public List<MinBalanceChargesPostPojo> atmChargesPostReport(float trsno, String enterBy, String enteryDt, String orgBrCode, String destBrCode) throws ApplicationException;
    
    public List <AtmReversalPostingPojo> getAtmRevsersalData(String frDt,String toDt)throws ApplicationException;
    
    public String getPostReversalData(AtmReversalPostingPojo obj,String userName) throws ApplicationException;
    
    public String getPostAtmMissingData(String atmBranch,String atmId,String atmGlhead,double amount,String valueDt,String userName) throws ApplicationException;
    
    public String postCashChargeData(List<CashHandlingChargeGridData> cashChgDataList,String brcode,String acNature, String acType,String dtpFrom, String dtpTo,String entryby, String entrydate, String crdaccount) throws ApplicationException;
    
    public List<CashHandlingChargeGridData> calculateCashHandlingCharges(String brnCode, String actNature, String acType, String dtFrom, String dtTo) throws ApplicationException;
    
    public String getDtForCommPost(String brnCode, String want) throws ApplicationException;
    
    public List<agentCommPojo> agentCommCalculate(String fromDt, String toDt, String brnCode) throws ApplicationException;
    
    public String agentRdCommPost( List<agentCommPojo> resultList, String acType, String glAcNo, String authBy, String orgnBrCode, double total, String curDate, String fromDt, String toDt) throws ApplicationException;
    
    public List<ChargesObject> minBalanceChargesCaculateAllBranch(String acType, String fromDt, String toDt, String brnCode, String enterBy, String enteryDt) throws ApplicationException;
    
    public String minBalanceChargesPostAllBranch(List<ChargesObject> chargesList, String acType, String glAcNo,String authBy, String orgnBrCode, double total, String curDate, String fromDt,String toDt) throws ApplicationException;
    
    public List setAcToCreditAll(String acType) throws ApplicationException;
}
