/*
 * CREATED BY     :   ROHIT KRISHNA GUPTA
 * CREATION DATE  :   14 DECEMBER 2010
 */
package com.cbs.facade.intcal;

import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author Admin
 */
@Remote
public interface TdInterestCalFacadeRemote {

    /**
     *
     * @param fromdt
     * @param todt
     * @param intopt
     * @param acctype
     * @param brCode
     * @return
     */
    List<TdIntDetail> interestCalculation(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException;

    public List<TdIntDetail> interestCalculationWithTds(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException ;
    /**
     *
     * @param tdIntDetailsList
     * @param toDt
     * @param date
     * @param acctType
     * @param tmpAcno
     * @param intopt
     * @param user
     * @param brCode
     * @return
     */
    Map<String, Object> tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDt, String date, String acctType, String tmpAcno,
            String intopt, String user, String brCode) throws ApplicationException;
//    String tdInterestPosting(List<TdIntDetail> tdIntDetailsList, String toDt, String date, String acctType, String tmpAcno,
//            String intopt, String user, String brCode) throws ApplicationException;
    //String tdIntPost(String acctype, String intopt, String authby, String td_inthead, String todate,String orgnBrCode);
    // String tdMQIntPost(String acctype, String intopt, String authby, String td_inthead, String todate, String gl_head,String orgnBrCode);
    
    public Map<String, Object> tdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String toDt, String fromDt, String acctType, String tmpAcno,
            String intopt, String user, String brCode) throws ApplicationException ;
    
    public String rdInterestPostingWithTds(List<TdIntDetail> tdIntDetailsList, String acType, String AuthBy, String fromdt, String todt, String brcode, String intOption) throws ApplicationException;
    
    public String tdIntPostProvWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException;
     
     public Map<String, Object> tdMQIntPostWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String td_inthead,
            String todate, String glHead, String orgnBrCode) throws ApplicationException ;
     
     public String tdIntPostWithTds(List<TdIntDetail> tdIntDetailsList, String acctype, String intopt, String enterBy, String tdIntHead,
            String todate, String orgnBrCode) throws ApplicationException ;
     
     public String ddsInterestPostingWithTds(List<TdIntDetail> list, String type, String fromDate, String toDate, String brncode, String userName) throws ApplicationException ;
     
    List<TdIntDetail> provInterestCalculation(String fromdt, String todt, String intopt, String acctype, String brCode) throws ApplicationException;
}
