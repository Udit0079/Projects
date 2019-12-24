/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.TdIntDetail;
import com.cbs.dto.TempProjIntDetail;
import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface TDInterestCalulationFacadeRemote {

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    List acctTypeCombo() throws ApplicationException;

    /**
     * 
     * @param acType
     * @return
     * @throws ApplicationException 
     */
    List setGLAcNoAndInterestOption(String acType) throws ApplicationException;

//    List setFinancialYear(String brCode) throws ApplicationException;
//
//    String tableDtCheck(String brCode) throws ApplicationException;
//
//    String serverDateCheck() throws ApplicationException;
//
//    String exitSoft(String usr, String brCode) throws ApplicationException;

    public List<TdIntDetail> tdInterestCalculationCover(String brnCode, String tmpFDate, String tempDate, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException ;
    /**
     * 
     * @param frDt
     * @param toDt
     * @param acctType
     * @param intOpt
     * @param mode
     * @param user
     * @param tmpAcNo
     * @param date
     * @param brCode
     * @return
     */
    
    public List<TdIntDetail> tdInterestCalculationWithTds(String tmpFDate, String tempDate, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException;
    
    public String tdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String toDate, String fromDt, String acctType, String tmpAcNo,
            String intOpt, String user, String brCode) throws ApplicationException ;
    
    List<TdIntDetail> tdInterestCalculation(String frDt, String toDt, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException;

    /**
     * 
     * @param tdIntDetailsList
     * @param toDate
     * @param date
     * @param acctType
     * @param tmpAcno
     * @param intopt
     * @param user
     * @param brCode
     * @return
     */
    String tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDate, String date, String acctType, String tmpAcno,
            String intopt, String user, String brCode) throws ApplicationException;

    /**
     * 
     * @return
     * @throws ApplicationException 
     */
    List acctTypeLoad() throws ApplicationException;

    /**
     * 
     * @param brCode
     * @return
     * @throws ApplicationException 
     */
    List finYear(String brCode) throws ApplicationException;
    
    /**
     * 
     * @param acDesc
     * @return
     * @throws ApplicationException 
     */
    List acNat(String acDesc) throws ApplicationException;

    /**
     * 
     * @param fromdt
     * @param todt
     * @param intopt
     * @param acctype
     * @param brcode
     * @return
     * @throws ApplicationException 
     */
   // String TdInterestCal(String fromdt, String todt, String intopt, String acctype, String brcode) throws ApplicationException;

    //String projectedIntCalTD(String intopt, String acctype, String brcode) throws ApplicationException;
    
    /**
     * 
     * @param intopt
     * @param acctype
     * @param brcode
     * @return
     * @throws ApplicationException 
     */
    public List<TempProjIntDetail> projectedIntCalTD(String intopt, String acctype, String brcode, String tillDate) throws ApplicationException;

    //List gridLoad(String brCode) throws ApplicationException;
    
    List finYearTD(String brCode) throws ApplicationException;
    
    List<TdIntDetail> tdProvInterestCalculation(String frDt, String toDt, String acctType, String intOpt, String user,
            String tmpAcNo, String date, String brCode) throws ApplicationException;
    
     List<TdIntDetail> tdProvInterestMonthlyData(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException;
    
    //public String postProvInterestTd(String tdIntHead,String tdProvIntHead, double amt,String enterBy, String orgnBrCode) throws ApplicationException;
}
