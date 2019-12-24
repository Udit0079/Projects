/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Ankit Verma
 */
@Remote
public interface AccountEditCloseFacadeRemote {

    public List codeDesc() throws ApplicationException;

    public List occupationCode() throws ApplicationException;

    public List accOpenDate(String branCode) throws ApplicationException;

    public List docName() throws ApplicationException;

    public String custInfoEditTd(String accNo) throws ApplicationException;

    public List getDocDtlDocDescDocCode(String accNo) throws ApplicationException;

    public String acctEditTdUpdate(String acno, String custName, String craddress, String praddress,
            String phoneno, String occupation, String operatingMode, String document, String documentDetails,
            String panno, String grdname, String fathername, String acnoIntro, String JtName1, String JtName2,
            String nominee, String nomineeRel, String JtName3, String JtName4, String acctIns, String tdsflag,
            String tdsdetails, String custtype, String receiptno, String acnoInt,
            String DateText, String UserText, String orgBrnCode, String nomiName,
            String nomiAddress, String nomiRelation, String nomiMinor, String nomiDob,
            String nomiAge, String jtName1Code, String jtName2Code,
            String jtName3Code, String jtName4Code, String autoRenew, String autoPay, String paidAcno, String acctCategry) throws ApplicationException;

    public String functionAcNat(String acctType) throws ApplicationException;

    public List selectReceiptNo(String accNo) throws ApplicationException;

    public List selectJointName1(String jointName1Code, String orgBrnCode) throws ApplicationException;

    public List dateDiffNomDob(String nomDob) throws ApplicationException;

    public String actNoValidation(String acno) throws ApplicationException;

    public String introducerCity(String recCode, String cityCode) throws ApplicationException;

    public List securityCkeck(String acno) throws ApplicationException;

    public List emiCkeck(String acno) throws ApplicationException;

    public List instructionCkeck1(String acno) throws ApplicationException;

    public List instructionCkeck(String acno) throws ApplicationException;

    public List lockerCkeck(String acno) throws ApplicationException;

    public String closeAccountProcedure(String acno, String authBy, String dateText, String brCode) throws ApplicationException;

    public String customerAcNoDetail(String acno) throws ApplicationException;

    public List getTodayTxnAmt(String acno) throws ApplicationException;

    public List getAcBalance(String acno) throws ApplicationException;

    public List custChqDetailForFreshChq(String acno) throws ApplicationException;

    public String deleteRecord(String acno) throws ApplicationException;

    public String closeActCustName(String acno) throws ApplicationException;

    public List closedActGridDetail(String brCode) throws ApplicationException;

    public String dayDiffIntPost(String acno) throws ApplicationException;

    public String chkAutoPaidAcnoInTd(String acno) throws ApplicationException;

    public List retrieveReceiptDetails(String acno, Double receiptNo) throws ApplicationException;
    
    public String chargeAmt() throws ApplicationException;
}
