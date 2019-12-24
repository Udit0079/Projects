/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.LoanPrequistitsList;
import com.cbs.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.ejb.Remote;

/**
 *
 * @author Sudhir
 */
@Remote
public interface LoanIntProductTestFacadeRemote {

    public List getAcctType() throws ApplicationException;

    public String allFromDt(String acType, String brnCode, String want) throws ApplicationException;

    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException;

    public List accWiseLoanIntProductCalcProvisional(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;
            
    public List accWiseLoanIntProductCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;
        
    public List accWiseLoanProjectedIntCalc(String fromDt, String toDt, String acNo, String brnCode, String roiFromUserFlag, double roiFromUser) throws ApplicationException ;
    
    public List accWiseLoanIntProductCalcAsPerEmiRecovery(String fromDt, String toDt, String acNo, String brnCode, String roiFromUserFlag, double roiFromUser) throws ApplicationException ;
    
    public List accWiseTheftIntProductCalc(String flag,String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;
    
    //public List accWiseLoanIntCalc(String fromDt, String toDt, String acNo, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> cbsIntCalLoan(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;

    public String reportShow(String brCode, String enterBy, List resultList) throws ApplicationException;

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException;
    
    public ArrayList getAdhocDateChangeSlab(String acNo, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException;
    
    public String loanInterestPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNo, String authBy, String brnCode) throws ApplicationException;
    
    public List acWiseFromDtToDt(String year, String brnCode) throws ApplicationException;
    
    public List<LoanPrequistitsList> staffLoanPerquisits(String dt, String schemeCode, String brnCode, double appRoi) throws ApplicationException ;
        
    public List getStaffScheme() throws ApplicationException;

}
