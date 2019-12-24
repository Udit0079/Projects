/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface CertIssueFacadeRemote {

    public java.util.List accType() throws ApplicationException;

    public java.util.List getRelatedTo() throws ApplicationException;

    public java.util.List getAdviseNo() throws ApplicationException;

    public java.util.List getBranchCode() throws ApplicationException;

    public List getAccountDetailsNormal(java.lang.String acno) throws ApplicationException;

    public List getAccountDetailsGlhead(java.lang.String acno) throws ApplicationException;

    public java.util.List getShareFolioDetails(java.lang.String acno) throws ApplicationException;

    public double getperShareValue() throws ApplicationException;

    public int getAvailableShare(java.lang.String acno) throws ApplicationException;

    public java.util.List getShareDetails(java.lang.String acno) throws ApplicationException;

    public java.lang.String getGlHeadforHeadOffice() throws com.cbs.exception.ApplicationException;

    public java.lang.String saveData(java.lang.String user, java.lang.String orgnBrCode, java.util.List<com.cbs.dto.HoTransactionTable> table) throws ApplicationException;

    public double getBalanceAccountNo(java.lang.String accNo, java.lang.String TmpAcctNature) throws com.cbs.exception.ApplicationException;

    public java.util.List getAllBranchExcludingHoAndCell() throws ApplicationException;

    public List getAllRefRecNoData(String refRecNo) throws ApplicationException;

    public List getBankProfile(Integer brncode) throws ApplicationException;

    public String saveAndUpdateBankProfile(String bankName, String regAdd, String regPin, String hoAdd,
            String hoPin, String status, String category, String rbiRating, String subCategory,
            String bankRegion, String licenceDt, String licenceNo, String lastInsPectionDt, String agmDt,
            BigInteger totRegMembers, BigInteger totNomMembers, String lastIntAutditDt, BigDecimal majorIrr,
            BigDecimal minorIrr, BigDecimal parasFully, BigDecimal parasOutstand, Integer Brncode,
            String userName, String todayDate, String opFlag) throws ApplicationException;
    
    public boolean isLoanOutStanding(String folioNo) throws ApplicationException;
    
    public double getDividendBal(String folioNo,String dt) throws ApplicationException;
    
    public List isUnathorizeFolio(String folioNo,String dt) throws ApplicationException;
}
