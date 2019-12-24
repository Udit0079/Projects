package com.cbs.facade.master;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface InterestMasterFacadeRemote {

    //InterestCodeMasterRemote Methods
    String saveInterestCodeMaster(List slabTable, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, Float basePerCredit, Float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String createdBy, String createdDate) throws ApplicationException;

    List getCurrencyCodeInterestCodeMaster(Integer refRecNo) throws ApplicationException;

    List getBaseIntTableCodeInterestCodeMaster() throws ApplicationException;

    List getDataInterestCodeMaster(String intTableCode) throws ApplicationException;

    List getDataIntSlabInterestCodeMaster(String intTableCode, int intVerNo, float loanPeriodDays, float loanPeriodMonth, float begSlbAmt, float endSlbAmt, String currencyCode) throws ApplicationException;

    List getSlabInterestCodeMaster() throws ApplicationException;

    public List getDataHelpInterestCodeMaster(String code) throws ApplicationException;

    public String checkRefCodeInterestCodeMaster(String code, String intTableCode) throws ApplicationException;

    String updateMasterSlabInterestCodeMaster(List table, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, float basePerCredit, float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String updatedBy, String updatedDate) throws ApplicationException;

    public String AddNewSlabInterestCodeMaster(List table, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, Float basePerCredit, Float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String createdBy, String createdDate) throws ApplicationException;

    //InterestMasterBean Methods
    public String saveUpdateInterestMaster(String command, String code, String codeDescription, String indicatorFlag, String recordStatus, String startDate, String endDate, String versionNoDesc, String userName, String currentDate, String usrName, Float interestPercentage, Float interestPercentageCredit, String todayDt, String lastUpdateDate, Integer verNo) throws ApplicationException;

    public List tableDataInterestMaster() throws ApplicationException;

    public String deletionInterestMaster(String code, String codeDescription, String indicatorFlag, String recordStatus, String startDate, String endDate, String versionNoDesc, String userName, String currentDate, String usrName, Float interestPercentage, Float interestPercentageCredit, String todayDt, String lastUpdateDate, Integer verNo) throws ApplicationException;

    public java.lang.String daybeginDateInterestMaster(java.lang.String orgnBrCode) throws ApplicationException;

    //InterestParameterBean Methods
    public List acctCodeInterestParameter() throws ApplicationException;

    public String saveFrHalfInterestParameter(String acType, boolean check1, boolean check2) throws ApplicationException;

    public String saveFrQuartInterestParameter(String acType, boolean check1, boolean check2, boolean check3, boolean check4) throws ApplicationException;

    public String saveFrMonthlyInterestParameter(String acType, boolean check1, boolean check2, boolean check3, boolean check4, boolean check5, boolean check6) throws ApplicationException;
    // Added by Manish kumar
    public boolean isIntrestTableCodeExit(String intrestTablecCode)throws ApplicationException;
}
