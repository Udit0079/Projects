/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.SavingIntRateChangePojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface SbIntCalcFacadeRemote {

    /**
     *
     * @return @throws ApplicationException
     */
    public List chkGLHead(String acType) throws ApplicationException;

    public List checkkGLHead() throws ApplicationException;

    /**
     *
     * @return @throws ApplicationException
     */
    public List getAcctType() throws ApplicationException;

    /**
     *
     * @param intOpt
     * @param acType
     * @param acNo
     * @param fromDt
     * @param toDt
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public List<LoanIntCalcList> cbsSbIntCalc(String intOpt, String acctStatus, String acType, String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    /**
     *
     * @param intDetailList
     * @param intOpt
     * @param acType
     * @param acNo
     * @param fromDt
     * @param toDt
     * @param glAcNo
     * @param authBy
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public String sbInterestPosting(List<LoanIntCalcList> intDetailList, String intOpt, String status, String acType, String fromDt, String toDt, String glAcNo,
            String authBy, String brnCode) throws ApplicationException;

    /**
     *
     * @param acNo
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException;

    /**
     *
     * @param acType
     * @param brnCode
     * @param want
     * @param acctStatus
     * @return
     * @throws ApplicationException
     */
    public String allFromDt(String acType, String brnCode, String want, String acctStatus) throws ApplicationException;

    /**
     *
     * @param fromDt
     * @param toDt
     * @return
     * @throws ApplicationException
     */
    public String savingProductCalculation(String fromDt, String toDt) throws ApplicationException;

    /**
     *
     * @param want
     * @return
     * @throws ApplicationException
     */
    public String getAllFromDt(String want) throws ApplicationException;

    /**
     *
     * @param fromDt
     * @param toDt
     * @param authBy
     * @param brnCode
     * @return
     * @throws ApplicationException
     */
    public String sbAllIntPosting(String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException;

    /**
     *
     * @param brCode
     * @param acType
     * @param acStatus
     * @return
     * @throws ApplicationException
     */
    public String getIntPostingDt(String brCode, String acType, String acStatus) throws ApplicationException;

    /**
     *
     * @param acType
     * @param brnCode
     * @param postingDt
     * @param acctStatus
     * @return
     * @throws ApplicationException
     */
    public List getIntPostingPd(String acType, String brnCode, String postingDt, String acctStatus) throws ApplicationException;

    public List<SavingIntRateChangePojo> getSavingRoiChangeDetail(String intTableCode, String fromDt, String toDt) throws ApplicationException;

    public List<LoanIntCalcList> cbsSbIntCalcForDeafClaim(String intOpt, String acctStatus, String acType, String acNo,
            String fromDt, String toDt, String brnCode, double deafAmount,double deafSavingRoi) throws ApplicationException;
    
    public String getProductFromDt()throws ApplicationException;
}
