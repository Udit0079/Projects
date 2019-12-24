/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.intcal;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface LoanPenalProductCalFacadeRemote {

    public List cbsLoanPenalCalculation(String intOpt, String acType, String acNo, String fromDt, String toDt, String authBy, String brnCode) throws ApplicationException;

    public String acWiseFromDt(String accountNo, String orgnBrCode) throws ApplicationException;
}
