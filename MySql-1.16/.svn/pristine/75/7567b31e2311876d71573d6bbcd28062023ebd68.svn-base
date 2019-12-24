package com.cbs.facade.master;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LedgerMasterFacadeRemote {

    public List getGlTable(String fromCode, String toCode) throws ApplicationException;

    public String cbsSaveGeneralLedger(String acno, String acnoName, String acType, String BrnCode, String Status) throws ApplicationException;

    public List getGlDesc() throws ApplicationException;

    public List getBranch() throws ApplicationException;

    public List getRange(int pCode) throws ApplicationException;

    List getGlDescData() throws ApplicationException;

    String SaveData(String glDesc, int fromNo, int toNo, String Enterby) throws ApplicationException;

    String updateData(int mCode, String glDesc, int fromNo, int toNo, String UpdateBy) throws ApplicationException;

    List loadGrdTransaction1(String fromAcno, float instNo, String brCode) throws ApplicationException;

    List loadGrdGeneralData1(String fromAcno, float instNo, String brCode) throws ApplicationException;

    List loadGrdTransaction(String fromAcno, String brCode) throws ApplicationException;

    List loadGrdGeneralData(String fromAcno, String brCode) throws ApplicationException;

    String deleteTransactionData(String fromAcno, float instrNo, String brCode) throws ApplicationException;

    String deleteGeneralTableData(String acno, float instrNo, String brCode) throws ApplicationException;

    String acnoLostFocus(String acno, String instType) throws ApplicationException;

    String acctoLostFocus(String fromAcno, String toAcno, String instType) throws ApplicationException;

    String saveData(String insType, String effPeriod, String remarks, float amount, String fromAcno, String toAcno, String periodicity, String effectiveDate, int validityDays, String userName, String deductCharges, String brCode, String sNo) throws ApplicationException;

    public String cbsUpdateGeneralLedger(String glHead, String BrnCode, String Status) throws ApplicationException;

    public List getAllDataFromGlDescRange() throws ApplicationException;

    public String createGlRange(String glname, int fromNo, int toNo, String username) throws ApplicationException;
    
    public String saveUnAuthSIData(String insType,String fromAcno,String effectiveDate,String effPeriod,String toAcno,String periodicity,int validityDays,float amount,String remarks,String deductCharges,String userName,String brCode, String debitAcTrname, String debitAcGenname, String creditAcTrnName) throws ApplicationException;
    
    public String deleteUnAuthSIData(String insType,String brCode, String sNo) throws ApplicationException;
        
    public List getUnAuthSI(String InstType, String orgnBrCode) throws ApplicationException;
    
    public List getUnAuthSILst(String sno, String orgnBrCode) throws ApplicationException;
}
