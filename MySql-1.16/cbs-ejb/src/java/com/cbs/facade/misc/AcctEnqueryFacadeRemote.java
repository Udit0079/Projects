/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AadharNonAadharPoJo;
import com.cbs.pojo.ChbookDetailPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface AcctEnqueryFacadeRemote {

    public List tableAccountWise(String accountNo) throws ApplicationException;

    public List ddStatus() throws ApplicationException;

    public List branchList() throws ApplicationException;

    public List CustomerDetail(String acctNum, String acctType, String agCode) throws ApplicationException;

    public String AccountStatus(String acNo) throws ApplicationException;

    public Double AccountWiseBalanceCheck(String acNo) throws ApplicationException;

    public List nameWiseEnquery(String enterBy, String status, String acctType, String nameType, String custName, String brnCode, String branch) throws ApplicationException;

    public List nameWiseEnqueryJointName(String accountNo) throws ApplicationException;

    public List nameWiseOutwordClearing(String accountNo) throws ApplicationException;

    public String acNature(String acNo) throws ApplicationException;

    public String roiSearch(Float period, String date) throws ApplicationException;

    public String rdRoiCal(Float roi, Float amt, Float period) throws ApplicationException;

    public List getData1(String paramValue, String status) throws ApplicationException;

    public List getData2(String paramValue, String status) throws ApplicationException;
    
    public List getData3(String paramValue, String option, String status) throws ApplicationException;

    public List getData4(String paramValue, String status) throws ApplicationException;
    
    public List getData5(String custnameValue,String fatherValue, String status) throws ApplicationException;
    
    List accType() throws ApplicationException;

    String searchData(String acType, String chqno, String brCode) throws ApplicationException;

    public List getGlDetails(String optionSearch, String glCode, String glName) throws ApplicationException;

    public List getIdDetails(String custId) throws ApplicationException;

    public List<ChbookDetailPojo> getAllChqDetails(String acno) throws ApplicationException;

    public List<AadharNonAadharPoJo> getAadharNonAadharDetail(String paramValue, String option) throws ApplicationException;

    public List ddsInterestCal(Double roi, Double amt, Float period) throws ApplicationException;

    public List getOriginalIdMergeId(String custId) throws ApplicationException;
}
