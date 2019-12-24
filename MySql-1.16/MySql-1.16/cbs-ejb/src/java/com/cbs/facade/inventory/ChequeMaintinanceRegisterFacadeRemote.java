/*
 * CREATED BY    :  ROHIT KRISHNA GUPTA
 * CREATION DATE :  30 OCT 2010
 */
package com.cbs.facade.inventory;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface ChequeMaintinanceRegisterFacadeRemote {

    List chkGLHead(String acctType) throws ApplicationException;

    List custChqDetailForFreshChq(String acno) throws ApplicationException;

    List custChqDetailForStopChq(String acno) throws ApplicationException;

    String custStatus(String acno) throws ApplicationException;

    List custDetail(String acno) throws ApplicationException;

    List getAcBalance(String acno) throws ApplicationException;

//    double findTax(double commAmt, String dt) throws ApplicationException;
//
//    String taxAmount(double amt, String type) throws ApplicationException;
//
//    List fnTaxApplicableROT(String appDT) throws ApplicationException;

    String saveChqMaintinanceDetail(String acno, long chqFrom, String option, long chqTo, String status, String enterBy, String favoring,
            String chqDt, Float tAmt, String chrgOpt, Float partyBalance, Float tmpCharges, String glHead,
            String orgBrnCode, String destBrnCode, String saveUpdateFlag) throws ApplicationException;
    
    public String getChequeBookTable(String acNature);
}