/*
 * Created By    :   ROHIT KRISHNA GUPTA
 * Creation Date :   12 Oct 2010
 */
package com.cbs.facade.loan;

import com.cbs.exception.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Admin
 */
@Remote
public interface LoanAccountsDetailsRemote {

    List actTypeCombo()throws ApplicationException;

    List accountDetail(String acctNo)throws ApplicationException;

    public List accountDetailForArmy(String acctNo) throws ApplicationException ;

    List gridDetail(String acctNo)throws ApplicationException;

    String saveEmploymentDetail(String brCode, String acNo, String firmName, String firmAdd, String firmPhoneNo, String designation, String monthlyIncome, String fromDt, String toDt, String reasonToQuit, String empId, String enterBy, String lastUpdate)throws ApplicationException;

    List legalDocsNameDetail(String code)throws ApplicationException;

    String deleteRecord(String brCode, String acno, String sNo)throws ApplicationException;

    List docListCombo(String acType)throws ApplicationException;

    List legalDocsGridDetail(String acctNo)throws ApplicationException;

    String saveDocumentDetails(String brCode, String acno, String docName, String dueDt, String expDt, String recDt, String remarks, String delFlag, String scanFlag, String freeText1, String freeText2, String freeText3, String freeText4, String freeText5, String freeText6, String freeText7, String freeText8, String freeText9, String freeText10)throws ApplicationException;

    String deleteRecordFromLoanAcDoctable(String brCode, String acno, String docName)throws ApplicationException;

    String loanShareDtOnLoad(String acno)throws ApplicationException;

    String loanShareDtSave(String acno, String ShareType, Float ShareMoney, String NomineeName, String MembershipDt, String NominalMem, String MemNo)throws ApplicationException;

    List loanGuarantorDtOnLoad(String acno)throws ApplicationException;

    String gurnatorDtSave(String actionFlag, String authby, String custFlag, String custidacno, String acno, String name, String Fathersname, Integer age, String retirementage, String Address, String PhNo, String occupation, String firmname, String firmaddress, String firmphno, String designation, Float networth)throws ApplicationException;

    String loanGuarantorDtDelete(String authby, String acno, String name, String Fathersname, String Address, String PhNo)throws ApplicationException;

    List insuranceStatusCombo()throws ApplicationException;

    List insuranceGridLoad(String acno)throws ApplicationException;

    List insuranceTypeCombo()throws ApplicationException;

    List insuranceCompanyCombo()throws ApplicationException;

    List setInsuranceCompany(String insComName)throws ApplicationException;

    List setInsuranceType(String insType)throws ApplicationException;

    String saveInsuranceDetail(String acno, String assetDesc, Float assetval, String insComName, String insType, String coverNoteNo, String status, String issueDt, String expDt, Float insAmt, String insDetail, String userName,String brCode)throws ApplicationException;

    String updateInsuranceDetail(String acno, int sNo, String assetDesc, Float assetval, String insComName, String insType, String coverNoteNo, String status, String issueDt, String expDt, Float insAmt, String insDetail, String userName)throws ApplicationException;

    List groupCompanyGridLoad(String acno)throws ApplicationException;

    List companyDetailOnLoad(String acno)throws ApplicationException;

    String saveCompanyDetail(String acno, String cmpnyName, String regdoffadd, String location, String incorpdt, String authcapital, String subscapital, String networth, String dir1, String dir2, String dir3, String dir4, String dir5, String dir6, String dir7, String enterBy)throws ApplicationException;

    String updateCompanyDetail(String acno, String cmpnyName, String regdoffadd, String location, String incorpdt, String authcapital, String subscapital, String networth, String dir1, String dir2, String dir3, String dir4, String dir5, String dir6, String dir7, String enterBy, String oldCmpnyname, String oldRegOffice, String oldLocation)throws ApplicationException;

    String saveGroupCompanyDetail(String acno, String grComName, String grCompAdd, String grCompPhNo, String enterBy)throws ApplicationException;

    String deleteFromGroupCompDetailGrid(String acno, String name, String address, String phNo)throws ApplicationException;

    public String acNoCheckForGuar(String acno)throws ApplicationException;
    public List guarontorDetailIfBankCust(String custId)throws ApplicationException;
    
    public String gurnatorCompare(String custId, String acno, float netWordth) throws ApplicationException;
}
