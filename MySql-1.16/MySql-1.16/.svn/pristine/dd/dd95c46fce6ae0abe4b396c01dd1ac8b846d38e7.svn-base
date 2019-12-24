/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.dto.LoanIntCalcList;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.IntCalTable;
import java.util.List;

/**
 *
 * @author root
 */
public interface CentralizedLoanInterestCalFacadeRemote {

    public String dateAdd(String dt, int noOfDays) throws ApplicationException;

    public String centralizedLoanInterestPosting(List<IntCalTable> intCalc, String acType, String acNature, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException;

    public List<LoanIntCalcList> centralizedLoanInterestCalculationCover(String acType, String acNature, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException;
}
