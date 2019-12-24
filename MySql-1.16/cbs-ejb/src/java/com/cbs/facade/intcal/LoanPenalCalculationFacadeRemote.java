/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.IntCalTable;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Administrator
 */
@Remote
public interface LoanPenalCalculationFacadeRemote {

    public List getAcctType() throws ApplicationException;

    public String acWiseFromDt(String acNo, String brnCode) throws ApplicationException;

    public String dateAdd(String dt, int noOfDays) throws ApplicationException;

    public String allFromDt(String acType, String brnCode, String want) throws ApplicationException;

    public List<List<LoanIntCalcList>> cbsLoanPenalCalculation(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> repaymentPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> repaymentPenalCalculationOnEMI(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> securityPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> stockPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> odPenalCalculation(String acNo, String fromDt, String toDt, String brnCode) throws ApplicationException;

    public String getPenalROI(String intTableCode, double amt, String date) throws ApplicationException;

    public String cbsLoanPenalPosting(String intOpt, String acType, String acNo, String fromDt, String toDt, String glAcNO, String authBy, String brnCode) throws ApplicationException;

    public List<List<LoanIntCalcList>> cbsCentralizedPenalCalculation(String brnCode, String acNature, String acType, String fromDt, String toDt) throws ApplicationException;

    public String cbsCentralizedPenalPosting(List<IntCalTable> intCalc, String brnCode, String acNature, String acType, String fromDt, String toDt, String authBy) throws ApplicationException;
}
