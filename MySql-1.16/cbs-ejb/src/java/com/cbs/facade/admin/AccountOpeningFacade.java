/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ankit Verma
 */
@Stateless(mappedName = "AccountOpeningFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountOpeningFacade implements AccountOpeningFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public String cbsIntroInfo(String introCustId, String introAcno, String acNat) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int accStatus = 0;
            String acno = null;
            String custName = null;
            String description = "";
            String msg = "True";
            if (introCustId == null || introCustId.length() == 0 || introCustId.equalsIgnoreCase("0")) {
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List secList = em.createNativeQuery("select * from td_accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!secList.isEmpty()) {
                        List allInfoList = em.createNativeQuery("select acno,custname,accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                        Vector allInfoListLst = (Vector) allInfoList.get(0);
                        acno = allInfoListLst.get(0).toString();
                        custName = allInfoListLst.get(1).toString();
                        accStatus = Integer.parseInt(allInfoListLst.get(2).toString());
                        List desList = em.createNativeQuery("select description from codebook where groupcode=3 and code=" + accStatus + "").getResultList();
                        if (!desList.isEmpty()) {
                            Vector descriptionLst = (Vector) desList.get(0);
                            description = descriptionLst.get(0).toString();
                        }
                    } else {
                        ut.rollback();
                        return "This Account No Does Not Exists";
                    }
                } else {
                    List secList = em.createNativeQuery("select * from accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!secList.isEmpty()) {
                        List allInfoList = em.createNativeQuery("select acno,custname,accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                        Vector allInfoListLst = (Vector) allInfoList.get(0);
                        acno = allInfoListLst.get(0).toString();
                        custName = allInfoListLst.get(1).toString();
                        accStatus = Integer.parseInt(allInfoListLst.get(2).toString());
                        List desList = em.createNativeQuery("select description from codebook where groupcode=3 and code=" + accStatus + "").getResultList();
                        if (!desList.isEmpty()) {
                            Vector descriptionLst = (Vector) desList.get(0);
                            description = descriptionLst.get(0).toString();
                        }
                    } else {
                        ut.rollback();
                        return "This Account No Does Not Exists";
                    }
                }
            } else {
                List chk1 = em.createNativeQuery("SELECT ACNO from customerid WHERE CUSTID=" + introCustId + " AND ACNO='" + introAcno + "'").getResultList();
                if (chk1.isEmpty()) {
                    ut.rollback();
                    return "THIS CUSTOMER ID AND A/C. NO. ARE NOT CORRECT , SO PLEASE ENTER CORRECT ID AND A/C. NO";
                } else if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List secList = em.createNativeQuery("select * from td_accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!secList.isEmpty()) {
                        List allInfoList = em.createNativeQuery("select acno,custname,accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                        Vector allInfoListLst = (Vector) allInfoList.get(0);
                        acno = allInfoListLst.get(0).toString();
                        custName = allInfoListLst.get(1).toString();
                        accStatus = Integer.parseInt(allInfoListLst.get(2).toString());
                        List desList = em.createNativeQuery("select description from codebook where groupcode=3 and code=" + accStatus + "").getResultList();
                        if (!desList.isEmpty()) {
                            Vector descriptionLst = (Vector) desList.get(0);
                            description = descriptionLst.get(0).toString();
                        }
                    } else {
                        ut.rollback();
                        return "This Account No Does Not Exists";
                    }
                } else {
                    List secList = em.createNativeQuery("select * from accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!secList.isEmpty()) {
                        List allInfoList = em.createNativeQuery("select acno,custname,accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                        Vector allInfoListLst = (Vector) allInfoList.get(0);
                        acno = allInfoListLst.get(0).toString();
                        custName = allInfoListLst.get(1).toString();
                        accStatus = Integer.parseInt(allInfoListLst.get(2).toString());
                        List desList = em.createNativeQuery("select description from codebook where groupcode=3 and code=" + accStatus + "").getResultList();
                        if (!desList.isEmpty()) {
                            Vector descriptionLst = (Vector) desList.get(0);
                            description = descriptionLst.get(0).toString();
                        }
                    } else {
                        ut.rollback();
                        return "This Account No Does Not Exists";
                    }
                }
            }
            ut.commit();
            return acno + ": " + custName + ": " + description + ": " + msg;

        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(ex);
        }
    }

    public String custIdInformation(String custId) throws ApplicationException {
        try {
            String msg = "True";
//            List secList = em.createNativeQuery("select title,custname,peraddressline1,peraddressline2,mailaddressline1,mailaddressline2,"
//                    + "fathername,mobilenumber,pan_girnumber,DATE_FORMAT(dateofbirth, '%d/%m/%Y') dob,ifnull(IntroCustomerId,''), PrimaryBrCode,"
//                    + "ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name "
//                    + "from cbs_customer_master_detail where customerid='" + custId + "' and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg = '')").getResultList();

//            //----Added by Manish Kumar----
            List secList = em.createNativeQuery("select title,custname,peraddressline1,peraddressline2,mailaddressline1,mailaddressline2,"
                    + " fathername,mobilenumber,pan_girnumber,DATE_FORMAT(dateofbirth, '%d/%m/%Y') dob,ifnull(IntroCustomerId,''), PrimaryBrCode,"
                    + " ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name, ifnull(FatherMiddleName,'') as f_middle_name, ifnull(FatherLastName,'') as f_last_name, "
                    + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = PerCityCode),'') as PerCityOrTown, "
                    + " if(ifnull(PerStateCode,'')='','',if(ifnull(PerStateCode,'')='0','', ifnull(PerStateCode,''))) as PerStateCode, "
                    + " if(ifnull(PerPostalCode,'')='','',ifnull(PerPostalCode,'')) asPerPostalCode, "
                    + " ifnull((select ref_desc from cbs_ref_rec_type where REF_REC_NO = '001' and REF_CODE = MailCityCode),'') as MailCityOrTown, "
                    + " if(ifnull(MailStateCode,'')='','',if(ifnull(MailStateCode,'')='0','', ifnull(MailStateCode,''))) as MailStateCode, "
                    + " if(ifnull(MailPostalCode,'')='','',ifnull(MailPostalCode,'')) as MailPostalCode "
                    + " from cbs_customer_master_detail where customerid='" + custId + "' and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg = '')").getResultList();
            //------
            if (secList.isEmpty()) {
                throw new ApplicationException("This Customer Id has been suspended or does not exists or unauthorized");
            }
            Vector secListLst = (Vector) secList.get(0);
            String title = secListLst.get(0).toString();
            String custName = secListLst.get(1).toString();
            String permanentAddress = secListLst.get(2).toString() + secListLst.get(3).toString();

            String corrAddress = secListLst.get(4).toString() + secListLst.get(5).toString();

            String fatherName = secListLst.get(6).toString();
            String mobileNo = secListLst.get(7) == null ? "" : secListLst.get(7).toString();
            String penGirNumber = secListLst.get(8).toString();

            String dob = secListLst.get(9).toString();
            String introId = secListLst.get(10).toString();
            String primaryBrCode = secListLst.get(11).toString();

            String middleName = secListLst.get(12).toString();
            String lastName = secListLst.get(13).toString();
            String fMiddleName = secListLst.get(14).toString();
            String fLastName = secListLst.get(15).toString();
            String perCity = secListLst.get(16).toString();
            String perState = secListLst.get(17).toString();
            String perPin = secListLst.get(18).toString();
            String mailCity = secListLst.get(19).toString();
            String mailState = secListLst.get(20).toString();
            String mailPin = secListLst.get(21).toString();
            String perCityWithAll = (perCity.equalsIgnoreCase("") ? "" : ", ".concat(perCity)).concat(perState.equalsIgnoreCase("") ? "" : ", ".concat(perState)).concat(perPin.equalsIgnoreCase("") ? "" : ", ".concat(perPin));
            String mailCityWithAll = (mailCity.equalsIgnoreCase("") ? "" : ", ".concat(mailCity)).concat(mailState.equalsIgnoreCase("") ? "" : ", ".concat(mailState)).concat(mailPin.equalsIgnoreCase("") ? "" : ", ".concat(mailPin));

//            //----- Added By Manish Kumar
//            String OccupationCode = secListLst.get(14).toString();
//            if(!OccupationCode.equals("")){
//                List tempOcc = em.createNativeQuery("Select description from codebook where groupcode=6 and code = '"+OccupationCode+"'").getResultList();
//                Vector vec = (Vector) tempOcc.get(0);
//                OccupationCode = vec.get(0).toString();
//            }else{
//                OccupationCode = "empty";
//            }
//            //----
            custName = custName.trim() + " " + middleName.trim();
            custName = custName.trim() + " " + lastName.trim();
            fatherName = fatherName.trim() + " " + fMiddleName.trim();
            fatherName = fatherName.trim() + " " + fLastName.trim();
            return title + ": " + custName.trim() + ": " + permanentAddress + ": " + corrAddress + ": " + fatherName + ": " + mobileNo + ": " + penGirNumber + ": " + dob + ": " + introId + ": " + msg + ": " + primaryBrCode + ": " + perCityWithAll + ": " + mailCityWithAll;// +": " + OccupationCode;

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getJointHolderDetails(String custId) throws ApplicationException {
        try {
            List secList = em.createNativeQuery("select custname,ifnull(middle_name,''),ifnull(last_name,'') from cbs_customer_master_detail where "
                    + "customerid='" + custId + "' and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg ='')").getResultList();
            if (secList.isEmpty()) {
                throw new ApplicationException("This Customer Id has been suspended or does not exists or unauthorized");
            }
            Vector secListLst = (Vector) secList.get(0);
            String custName = secListLst.get(0).toString();
            String middleName = secListLst.get(1).toString();
            String lastName = secListLst.get(2).toString();
            custName = custName.trim() + " " + middleName.trim();
            custName = custName.trim() + " " + lastName.trim();

            return custName;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveAccountOpenSbRd(String cust_type, String cust_id, String actype, String title, String custname, String craddress, String praddress,
            String phoneno, String dob, int occupation, int operatingMode, String panno, String grdname, String grd_relation, String agcode, String DateText,
            String UserText, String fathername, String acnoIntro, String JtName1, String JtName2, String orgncode, String nominee, String nominee_relatioship,
            String JtName3, String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, String docudetails, String nomineeAdd, String nomineeDate,
            String custid1, String custid2, String custid3, String custid4, String schemeCode, String intCode, String actCateg, String hufFamily, int chqOpt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = facadeRemote.saveAccountOpenSbRdWithOutTranMgmt(cust_type, cust_id, actype, title, custname, craddress, praddress,
                    phoneno, dob, occupation, operatingMode, panno, grdname, grd_relation, agcode, DateText,
                    UserText, fathername, acnoIntro, JtName1, JtName2, orgncode, nominee, nominee_relatioship,
                    JtName3, JtName4, rdperiod, rdinstall, rdroi, docuno, docudetails, nomineeAdd, nomineeDate,
                    custid1, custid2, custid3, custid4, schemeCode, intCode, actCateg, "", hufFamily, chqOpt);

            if (result.contains("Account Open Successfully")) {
                ut.commit();
                return result;
            } else {
                ut.rollback();
                return result;
            }

        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String cbsRdCalculation(float instAmt, int periodInMonth, float rdRoi) throws ApplicationException {
        try {
            float ip = 4;
            float varI = rdRoi / ip;
            float varC = ip / 12;
            double varF = (Math.pow(1 + varI / 100, varC)) - 1;
            double varA1 = ((Math.pow((1 + varF), periodInMonth)) - 1) / varF;
            double varA2 = varA1 * (1 + varF);
            double rd_mat_amt = Math.round((varA2 * instAmt));
            return "" + rd_mat_amt;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List dateDiffStatementFreqDate(String statementFreqDate) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(day,'" + statementFreqDate + "','" + sdf.format(date) + "')").getResultList();
            return dateDiff;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    //Added on 12/07/2011
    public List getRDRoi(double amount, long days) throws ApplicationException {
        List result = null;
        try {
            result = em.createNativeQuery("select interest_rate,SC,ST,OT from td_slab where " + days + " between fromdays and todays and " + amount + " between fromamount and toamount and applicable_date = (select max(applicable_date) from td_slab where applicable_date < curdate())").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    /**
     * *ADDED FOR SCHEME CODE****
     */
    public List SetValueAcctToScheme(String schemeCode) throws ApplicationException {
        List schemeValueList = null;
        try {
            /* Khatri 
             * TURN_OVER_DETAIL_FLAG ==> Y = (Int Calc Based on Security ROI); N= Normal Interest based on Scheme
             * COMMITMENT_EVENT = Margin Value define against Security
             * DRCR_IND ==> Y = Enable; N = Desible
             */
            schemeValueList = em.createNativeQuery("select l.INT_BASE_METHOD,l.INT_PRODUCT_METHOD,l.COMPOUNDING_FREQ,"
                    + " t.INTEREST_TABLE_CODE, sg.commit_calculation_method, sg.TURN_OVER_DETAIL_FLAG, cd.COMMITMENT_EVENT, cd.DRCR_IND "
                    + " from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t, "
                    + " cbs_scheme_general_scheme_parameter_master sg, cbs_scheme_currency_details cd "
                    + " where l.SCHEME_CODE = t.SCHEME_CODE and l.SCHEME_CODE = sg.SCHEME_CODE and l.SCHEME_CODE = cd.SCHEME_CODE "
                    + " and l.SCHEME_CODE='" + schemeCode + "' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return schemeValueList;
    }

    public List schemeCodeCombo(String acctype) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select scheme_code,scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_type = '" + acctype + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public List getAcTypeDescription(String acctCode) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select AcctType, acctnature from accounttypemaster where AcctCode='" + acctCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return scheme;
    }

    public List interestCodeData(String intCode) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select interest_code,interest_code_description from cbs_loan_interest_code_master where interest_code='" + intCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public List interestCodeAdditionalRoi(String intCode, double amt) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select ifnull(ADDITIONAL_INTEREST_PERCENTAGES,0) from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intCode + "' and " + amt + " between BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public List interestCodeCombo() throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select interest_code,interest_code_description from cbs_loan_interest_code_master ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public String getROI(String intTableCode, double amt, String date) throws ApplicationException {
        String result = null;
        int intVerNo;
        String intMastTblCod;
        double roi;
        String intVerDesc;
        if (amt < 0) {
            amt = amt * -1;
        }
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='"
                    + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,"
                    + "INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='"
                    + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
            } else {
                result = "false:Data does not exists in cbs_loan_interest_code_master";
                return result;
            }

            double intPerDr;
            double intPerCr;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from "
                    + "cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date  and "
                    + "Record_Status = 'A'").getResultList();
            List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from "
                    + "cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date "
                    + " and Record_Status = 'A'").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
            } else {
                result = "false:Data does not exists in cbs_loan_interest_master";
                return result;
            }

            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from"
                    + " cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and "
                    + "END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                    + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT "
                    + "and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                result = "false:Data does not exists in cbs_loan_interest_slab_master";
                return result;
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerCr - intPerDr;
                result = Double.toString(roi);
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = 0d;
                result = Double.toString(roi);
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                result = Double.toString(roi);
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return result;
    }

    public List acctTypeCombo() throws ApplicationException {
        List actype = null;
        try {
            actype = em.createNativeQuery("select acctCode,acctdesc from accounttypemaster where acctNature in('TL','CA','SS') and acctCode not in ('" + CbsAcCodeConstant.SF_AC + "','" + CbsAcCodeConstant.DC_AC + "','" + CbsAcCodeConstant.PB_AC + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List IntroducerAcctTypeCombo() throws ApplicationException {
        List actype = null;
        try {
            actype = em.createNativeQuery("select acctCode from accounttypemaster where acctNature not in ('PO','OF','TL','SS','DL') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actype;
    }

    public List parameterCode() throws ApplicationException {
        List param = null;
        try {
            param = em.createNativeQuery("select code from parameterinfo_report where reportname='LoanApplication'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return param;
    }

    public List appSeqNoCombo(String acctType) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("select AppNo, Fyear from loan_receipt where statusflag='Y' and LoanType='" + acctType + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public List acctTypeComboLostFocus(String acType) throws ApplicationException {
        List chk = null;
        try {
            //   chk = em.createNativeQuery("Select AccountType from loan_defaults where AccountType = '" + acType + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return chk;
    }

    public List appSeqNoComboLostFocus(String acctType, String appNo, String fYear) throws ApplicationException {
        List scheme = null;
        try {
            scheme = em.createNativeQuery("Select BorrowerName,Amountsanc,SancDt from loan_receipt where loantype='" + acctType + "' and appno= '" + appNo + "' and fyear = '" + fYear + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return scheme;
    }

    public List customerDetail(String custId) throws ApplicationException {
        List custDet = new ArrayList();
        try {
//            Query selectQuery = em.createNativeQuery("select ifnull(title,''),ifnull(custname,''),ifnull(fathername,''),ifnull(peraddressline1,''),ifnull(peraddressline2,''),ifnull(mailaddressline1,''),ifnull(mailaddressline2,''),"
//                    + " ifnull(mobilenumber,''),ifnull(pan_girnumber,''),ifnull(date_format(dateofbirth,'%d/%m/%Y'),''),"
//                    + "ifnull(IntroCustomerId,''),ifnull(MinorFlag,''),ifnull(middle_name,'') as middle_name,"
//                    + "ifnull(last_name,'') as last_name from cbs_customer_master_detail where customerid='" + custId + "' "
//                    + "and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg = '')");
            Query selectQuery = em.createNativeQuery("select ifnull(title,''),ifnull(custname,''),ifnull(fathername,''),ifnull(peraddressline1,''),ifnull(peraddressline2,''),ifnull(mailaddressline1,''),ifnull(mailaddressline2,''),"
                    + "ifnull(mobilenumber,''),ifnull(pan_girnumber,''),ifnull(date_format(dateofbirth,'%d/%m/%Y'),''),"
                    + "ifnull(IntroCustomerId,''),ifnull(MinorFlag,''),ifnull(middle_name,'') as middle_name,"
                    + "ifnull(last_name,'') as last_name, ifnull(FatherMiddleName,'') as f_middle_name, ifnull(FatherLastName,'') as f_last_name "
                    + " ,ifnull(gender,''),ifnull(MailCityCode,''),ifnull(MailBlock,''),ifnull(MailDistrict,''),ifnull(mail_email,''),ifnull(MailStateCode,''),ifnull(MailPostalCode,''),ifnull(MailVillage,''),ifnull(MailCountryCode,'IN'),ifnull(CustFullName,'')  from cbs_customer_master_detail where customerid='" + custId + "'"
                    + "and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg = '')");
            custDet = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return custDet;
    }

    public String introducerAcDetail(String custid, String introCustId, String introAcno) throws ApplicationException {
        String message = "";
        try {
            String tmpAcNo = "";
            String custName = "";
            String acSt = "";
            String acNat = "";
            String accountCode = facadeRemote.getAccountCode(introAcno);
            if (introCustId == null || introCustId.length() == 0 || introCustId.equalsIgnoreCase("0")) {
                List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
                if (!acNatList.isEmpty()) {
                    Vector ele = (Vector) acNatList.get(0);
                    acNat = ele.get(0).toString();
                } else {
                    message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT !!!";
                    return message;
                }
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List chk2 = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!chk2.isEmpty()) {
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            tmpAcNo = ele.get(0).toString();
                            custName = ele.get(1).toString();
                            List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                            if (!chk3.isEmpty()) {
                                Vector ele1 = (Vector) chk3.get(0);
                                acSt = ele1.get(0).toString();
                            }
                        }
                        List lt1 = em.createNativeQuery("SELECT CUSTID from customerid WHERE ACNO='" + introAcno + "'").getResultList();
                        if (!lt1.isEmpty()) {
                            Vector ltv = (Vector) lt1.get(0);
                            if (ltv.get(0).toString().equalsIgnoreCase(custid)) {
                                message = "SORRY THIS A/C. HOLDER CANNOT INTRODUCE FOR SELF i.e. CUST ID FOR WHICH YOU ARE OPENING ACCOUNT AND INTRODUCER CUST ID ARE SAME.";
                                return message;
                            }
                        }
                    } else {
                        message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                        return message;
                    }
                } else {
                    List chk2 = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                    if (!chk2.isEmpty()) {
                        for (int i = 0; i < chk2.size(); i++) {
                            Vector ele = (Vector) chk2.get(i);
                            tmpAcNo = ele.get(0).toString();
                            custName = ele.get(1).toString();
                            List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                            if (!chk3.isEmpty()) {
                                Vector ele1 = (Vector) chk3.get(0);
                                acSt = ele1.get(0).toString();
                            }
                            List lt1 = em.createNativeQuery("SELECT CUSTID from customerid WHERE ACNO='" + introAcno + "'").getResultList();
                            if (!lt1.isEmpty()) {
                                Vector ltv = (Vector) lt1.get(0);
                                if (ltv.get(0).toString().equalsIgnoreCase(custid)) {
                                    message = "SORRY THIS A/C. HOLDER CANNOT INTRODUCE FOR SELF i.e. CUST ID FOR WHICH YOU ARE OPENING ACCOUNT AND INTRODUCER CUST ID ARE SAME.";
                                    return message;
                                }
                            }
                        }
                    } else {
                        message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                        return message;
                    }
                }
            } else {
                List chk1 = em.createNativeQuery("SELECT ACNO from customerid WHERE CUSTID=" + introCustId + " AND ACNO='" + introAcno + "'").getResultList();
                if (chk1.isEmpty()) {
                    message = "THIS CUSTOMER ID AND A/C. NO. ARE NOT CORRECT , SO PLEASE ENTER CORRECT ID AND A/C. NO.";
                    return message;
                } else {
                    List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
                    if (!acNatList.isEmpty()) {
                        Vector ele = (Vector) acNatList.get(0);
                        acNat = ele.get(0).toString();
                    } else {
                        message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT !!!";
                        return message;
                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        List chk2 = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                        if (!chk2.isEmpty()) {
                            for (int i = 0; i < chk2.size(); i++) {
                                Vector ele = (Vector) chk2.get(i);
                                tmpAcNo = ele.get(0).toString();
                                custName = ele.get(1).toString();
                                List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                if (!chk3.isEmpty()) {
                                    Vector ele1 = (Vector) chk3.get(0);
                                    acSt = ele1.get(0).toString();
                                }
                            }
                            List lt1 = em.createNativeQuery("SELECT CUSTID from customerid WHERE ACNO='" + introAcno + "'").getResultList();
                            if (!lt1.isEmpty()) {
                                Vector ltv = (Vector) lt1.get(0);
                                if (ltv.get(0).toString().equalsIgnoreCase(custid)) {
                                    message = "SORRY THIS A/C. HOLDER CANNOT INTRODUCE FOR SELF i.e. CUST ID FOR WHICH YOU ARE OPENING ACCOUNT AND INTRODUCER CUST ID ARE SAME.";
                                    return message;
                                }
                            }
                        } else {
                            message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                            return message;
                        }
                    } else {
                        List chk2 = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                        if (!chk2.isEmpty()) {
                            for (int i = 0; i < chk2.size(); i++) {
                                Vector ele = (Vector) chk2.get(i);
                                tmpAcNo = ele.get(0).toString();
                                custName = ele.get(1).toString();
                                List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                if (!chk3.isEmpty()) {
                                    Vector ele1 = (Vector) chk3.get(0);
                                    acSt = ele1.get(0).toString();
                                }
                            }
                            List lt1 = em.createNativeQuery("SELECT CUSTID from customerid WHERE ACNO='" + introAcno + "'").getResultList();
                            if (!lt1.isEmpty()) {
                                Vector ltv = (Vector) lt1.get(0);
                                if (ltv.get(0).toString().equalsIgnoreCase(custid)) {
                                    message = "SORRY THIS A/C. HOLDER CANNOT INTRODUCE FOR SELF i.e. CUST ID FOR WHICH YOU ARE OPENING ACCOUNT AND INTRODUCER CUST ID ARE SAME.";
                                    return message;
                                }
                            }
                        } else {
                            message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                            return message;
                        }
                    }
                }
            }
            message = "true" + tmpAcNo + "#" + custName + "*" + acSt;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String GetROIForLoanDLAcOpen(String intTableCode, float amt, String date) throws ApplicationException {
        String result = null;
        int intVerNo;
        float basePerDr;
        float basePerCr;
        String intMastTblCod;
        Date stDt;
        Date toDt;
        float roi;
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                basePerDr = Float.parseFloat(laIntCodeMastVect.get(1).toString());
                basePerCr = Float.parseFloat(laIntCodeMastVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
                stDt = (Date) laIntCodeMastVect.get(4);
                toDt = (Date) laIntCodeMastVect.get(5);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                basePerDr = Float.parseFloat(laIntCodeMastHistVect.get(1).toString());
                basePerCr = Float.parseFloat(laIntCodeMastHistVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
                stDt = (Date) laIntCodeMastHistVect.get(4);
                toDt = (Date) laIntCodeMastHistVect.get(5);
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            float intPerDr;
            float intPerCr;
            Date stDtIntMast;
            Date toDtIntMast;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Float.parseFloat(laIntMastVect.get(0).toString());
                intPerCr = Float.parseFloat(laIntMastVect.get(1).toString());
                stDtIntMast = (Date) laIntMastVect.get(2);
                toDtIntMast = (Date) laIntMastVect.get(3);
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Float.parseFloat(laIntMastHistVect.get(0).toString());
                intPerCr = Float.parseFloat(laIntMastHistVect.get(1).toString());
                stDtIntMast = (Date) laIntMastHistVect.get(2);
                toDtIntMast = (Date) laIntMastHistVect.get(3);
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            String nrIntIndi;
            float nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();
            List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Float.parseFloat(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Float.parseFloat(laIntSlabMastHistVect.get(1).toString());
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerDr - intPerCr;
                result = new Float(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = (float) 0.0;
                result = new Float(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
//                roi = intPerDr - intPerCr;
                roi = nrIntPer;
                result = new Float(roi).toString();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return result;
    }

    public String GetROIAcOpen(String intTableCode, double amt, int period, String date) throws ApplicationException {
        String result = null;
        int intVerNo;
        float basePerDr;
        float basePerCr;
        String intMastTblCod;
        Date stDt;
        Date toDt;
        float roi;
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,INTEREST_MASTER_TABLE_CODE,START_DATE,END_DATE from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                basePerDr = Float.parseFloat(laIntCodeMastVect.get(1).toString());
                basePerCr = Float.parseFloat(laIntCodeMastVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(3);
                stDt = (Date) laIntCodeMastVect.get(4);
                toDt = (Date) laIntCodeMastVect.get(5);
            } else if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                basePerDr = Float.parseFloat(laIntCodeMastHistVect.get(1).toString());
                basePerCr = Float.parseFloat(laIntCodeMastHistVect.get(2).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(3);
                stDt = (Date) laIntCodeMastHistVect.get(4);
                toDt = (Date) laIntCodeMastHistVect.get(5);
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            float intPerDr;
            float intPerCr;
            Date stDtIntMast;
            Date toDtIntMast;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();
            List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date").getResultList();

            if (!laIntMastList.isEmpty()) {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Float.parseFloat(laIntMastVect.get(0).toString());
                intPerCr = Float.parseFloat(laIntMastVect.get(1).toString());
                stDtIntMast = (Date) laIntMastVect.get(2);
                toDtIntMast = (Date) laIntMastVect.get(3);
            } else if (!laIntMastHistList.isEmpty()) {
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Float.parseFloat(laIntMastHistVect.get(0).toString());
                intPerCr = Float.parseFloat(laIntMastHistVect.get(1).toString());
                stDtIntMast = (Date) laIntMastHistVect.get(2);
                toDtIntMast = (Date) laIntMastHistVect.get(3);
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            String nrIntIndi;
            float nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT and loan_period_months>=" + period + " AND INTEREST_VERSION_NO =" + intVerNo).getResultList();
            List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT and loan_period_months>=" + period + "  AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (!laIntSlabMastList.isEmpty()) {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Float.parseFloat(laIntSlabMastVect.get(1).toString());
            } else if (!laIntSlabMastHistList.isEmpty()) {
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Float.parseFloat(laIntSlabMastHistVect.get(1).toString());
            } else {
                result = "ROI DOES NOT EXISTS,PLEASE FILL ROI.";
                return result;
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
                roi = nrIntPer + intPerDr - intPerCr;
                result = new Float(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = (float) 0.0;
                result = new Float(roi).toString();
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
//                roi = intPerDr - intPerCr;
                roi = nrIntPer;
                result = new Float(roi).toString();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return result;
    }

    public List operationModeComboLostFocus(String code) throws ApplicationException {
        List occupation = null;
        try {
            occupation = em.createNativeQuery("SELECT DESCRIPTION FROM codebook WHERE CODE=" + code + " AND groupcode = 4").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return occupation;
    }

    public String saveTLAcOpenDetail(String cust_id, String tmpANat, String appTp, String appSeq,
            String FYear, String actype, String orgncode, String agcode, String acOpDt, String title,
            String custname, String fathername, String praddress, String craddress, String phoneno,
            String panno, String dob, Integer occupation, String grdname, String grd_relation,
            Integer operatingMode, String JtName1, String JtName2, String JtName3, String JtName4,
            String nominee, String nominee_relatioship, String nomineeAdd, String nomineeDob,
            String acnoIntro, Float Odlimit, Float roi, String sancDtV, String IntOpt, String subSidyAmt,
            Integer docuno, String docudetails, String spInst, String UserText, String schemeCode,
            Integer moritoriumPeriod, Float acnoPreDr, Float acnoPreCr, String rateCode, String calMethod, String calOn,
            String intAppFrequency, String calLevel, String compoundFrequency, String disbursmentType, String intCode,
            String paggingFrequency, Integer LoanPeriod1, Integer LoanPeriod2, String jtCustId1, String jtCustId2, String jtCustId3, String jtCustId4, String actCateg, String hufFamily, int chqOpt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = facadeRemote.saveTLAcOpenDetailWithOutTranMgmt(cust_id, tmpANat, appTp, appSeq, FYear, actype, orgncode, agcode, acOpDt, title,
                    custname, fathername, praddress, craddress, phoneno, panno, dob, occupation, grdname, grd_relation,
                    operatingMode, JtName1, JtName2, JtName3, JtName4, nominee, nominee_relatioship, nomineeAdd, nomineeDob,
                    acnoIntro, Odlimit, roi, sancDtV, IntOpt, subSidyAmt, docuno, docudetails, spInst, UserText, schemeCode,
                    moritoriumPeriod, acnoPreDr, acnoPreCr, rateCode, calMethod, calOn,
                    intAppFrequency, calLevel, compoundFrequency, disbursmentType, intCode,
                    paggingFrequency, LoanPeriod1, LoanPeriod2, jtCustId1, jtCustId2, jtCustId3, jtCustId4, actCateg, "", hufFamily, chqOpt);

            if (result == null) {
                ut.rollback();
                return "DATA NOT SAVED , SOME PROBLEM OCCURED !!!";
            } else {
                if (result.contains("FOR CUST ID")) {
                    ut.commit();
                    return result;
//                            .substring(0, 12);
                } else {
                    ut.rollback();
                    return result;
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    private boolean validateString(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '#' || value.charAt(i) == '~') {
                return false;
            }
        }
        return true;
    }

//    public String saveLoanMISDetail(String Acno) {
//        UserTransaction ut;
//        String message = "";
//        try {
//            int var = 0;
//            if (Acno == null || Acno.equalsIgnoreCase("") || Acno.length() == 0 || Acno.length() != 12) {
//                message = "ACCOUNT NO COULD NOT BE BLANK IN LOAN MIS DETAIL !!!";
//                return message;
//            }
//            String accountCode = facadeRemote.getAccountCode(Acno);
//            Query insertQuery = em.createNativeQuery("Insert Into loan_mis_details(AcNo,Classification,Sector,GovtScheme,IndustrialExp,"
//                    + " LoanType,AppCategory,CategoryOpt,NonPrioritySec,Relation,SanctionAuth,SensitiveSector,EducationIn,LastBalConfirmDt,"
//                    + " LoanDuration,Margin,NetWorth,DocDt,DocExpDt,SecurityType,EnterBy,Auth,AuthBy,ActivityCode,SpProgCode,VillageCode,DistrictCode,"
//                    + " PopulationCode,ActivityCode1,Xtra1,remarks,purpose)"
//                    + " SELECT "
//                    + " Max('" + Acno + "') AcNo,"
//                    + " Max(CASE WHEN GC BETWEEN 100000 AND 109999 THEN VAL ELSE ''END) CLASSIFICATION,"
//                    + " Max(CASE WHEN GC BETWEEN 200000 AND 209999 THEN VAL ELSE ''END) Sectors,"
//                    + " Max(CASE WHEN GC BETWEEN 300000 AND 309999 THEN VAL ELSE ''END) Schemes,"
//                    + " Max(CASE WHEN GC BETWEEN 800000 AND 809999 THEN VAL ELSE ''END) IndExp,"
//                    + " Max(CASE WHEN GC BETWEEN 400000 AND 409999 THEN VAL ELSE ''END) TypeOfLoan,"
//                    + " Max('GENERAL') AppCategory,"
//                    + " Max(CASE WHEN GC BETWEEN 600000 AND 609999 THEN VAL ELSE ''END) CatOPT,"
//                    + " Max(CASE WHEN GC BETWEEN 210000 AND 219999 THEN VAL ELSE ''END) NonPrioritySec,"
//                    + " Max(CASE WHEN GC BETWEEN 700000 AND 709999 THEN VAL ELSE ''END) Relation,"
//                    + " Max(CASE WHEN GC BETWEEN 900000 AND 909999 THEN VAL ELSE ''END) SanAuth,"
//                    + " Max(CASE WHEN GC BETWEEN 500000 AND 509999 THEN VAL ELSE ''END) SSector,"
//                    + " MAX('INDIA') EducationIn,"
//                    + " Max(curdate()) LastBalConfirmDt,"
//                    + " Max(12) LoanDuration,"
//                    + " Max(0) Margin,"
//                    + " Max(0) NetWorth,"
//                    + " Max(curdate()) DocDt,"
//                    + " Max(DateAdd(Y,3,curdate())) DocExpDt,"
//                    + " Max('PLEDGE') SecurityType,"
//                    + " Max('SYSTEM') EnterBy,"
//                    + " Max('N') Auth,"
//                    + " Max('') AuthBy,"
//                    + " Max(0) ActivityCode,"
//                    + " Max(0) SpProgCode,"
//                    + " Max(0) VillageCode,"
//                    + " Max(0) DistrictCode,"
//                    + " Max(0) PopCode,"
//                    + " Max(0) ActivityCode1,"
//                    + " Max('920000/6') Xtra1,"
//                    + " Max('')remarks,"
//                    + " Max('930000/1')purpose "
//                    + " From	"
//                    + " (SELECT Max(GROUPCODE),CONCAT(CAST(Max(GROUPCODE) AS CHAR(6)),'/', CAST(DEF AS CHAR(6))) Val "
//                    + " FROM loan_defaults "
//                    + " Where AccountType = '" + accountCode + "'"
//                    + " GROUP BY SUBSTRING(CAST(GROUPCODE AS CHAR(6)),1,2),DEF"
//                    + " )Def(GC,Val)");
//            var = insertQuery.executeUpdate();
//            //System.out.println("VAR:======="+var);
//            if (var <= 0) {
//                message = "ERROR IN INSERTION IN LOAN MIS DETAIL !!!";
//                return message;
//            } else {
//                message = "true";
//                return message;
//            }
//        } catch (Exception e) {
//        }
//        return message;
//    }
    /**
     * **************************************DlAccountOpeningRegisterBean's
     * Method************************
     */
    public List getOrganizationType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select code,description  from codebook where groupcode=40 and code != 0 order by description").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getExistingCustDetail(String custId) throws ApplicationException {
        List resultlist = null;
        try {
//            resultlist = em.createNativeQuery("select ifnull(title,'') as title,ifnull(custname,'') as custname,"
//                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,'') as peraddressline2,"
//                    + "ifnull(mailaddressline1,'') as mailaddressline1 ,ifnull(mailaddressline2,'') as mailaddressline2,"
//                    + "ifnull(fathername,'')as fathername,ifnull(mobilenumber,'') as mobilenumber,ifnull(pan_girnumber,'')as "
//                    + "pan_girnumber,ifnull(DATE_FORMAT(dateofbirth, '%d/%m/%Y'),'') as  dob,ifnull(IntroCustomerId,'') as "
//                    + "IntroCustomerId,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name from "
//                    + "cbs_customer_master_detail where customerid='" + custId + "' and auth='Y' and "
//                    + "(suspensionflg is null or suspensionflg = 'N' or suspensionflg ='')").getResultList();
            resultlist = em.createNativeQuery("select ifnull(title,'') as title,ifnull(custname,'') as custname,"
                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,'') as peraddressline2,"
                    + "ifnull(mailaddressline1,'') as mailaddressline1 ,ifnull(mailaddressline2,'') as mailaddressline2,"
                    + "ifnull(fathername,'')as fathername,ifnull(mobilenumber,'') as mobilenumber,ifnull(pan_girnumber,'')as "
                    + "pan_girnumber,ifnull(DATE_FORMAT(dateofbirth, '%d/%m/%Y'),'') as  dob,ifnull(IntroCustomerId,'') as "
                    + "IntroCustomerId,ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name, ifnull(FatherMiddleName,'') as f_middle_name, ifnull(FatherLastName,'') as f_last_name from "
                    + "cbs_customer_master_detail where customerid='" + custId + "' and auth='Y' and "
                    + "(suspensionflg is null or suspensionflg = 'N' or suspensionflg ='')").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultlist;
    }

    public String introducerAcDetailForDlFDAc(String introCustId, String introAcno) throws ApplicationException {
        String message = "";
        try {
            String tmpAcNo = "";
            String custName = "";
            String acSt = "";
            String acNat = "";
            if (introCustId == null || introCustId.equalsIgnoreCase("") || introCustId.length() == 0 || introCustId.equalsIgnoreCase("0")) {
                if (!introAcno.equalsIgnoreCase("")) {
                    List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acno='" + introAcno + "')").getResultList();
                    if (!acNatList.isEmpty()) {
                        Vector ele = (Vector) acNatList.get(0);
                        acNat = ele.get(0).toString();
                    } else {
                        message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT !!!";
                        return message;
                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        List chk2 = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                        if (!chk2.isEmpty()) {
                            for (int i = 0; i < chk2.size(); i++) {
                                Vector ele = (Vector) chk2.get(i);
                                tmpAcNo = ele.get(0).toString();
                                custName = ele.get(1).toString();
                                List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                if (!chk3.isEmpty()) {
                                    Vector ele1 = (Vector) chk3.get(0);
                                    acSt = ele1.get(0).toString();
                                }
                            }
                        } else {
                            message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                            return message;
                        }
                    } else {
                        List chk2 = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                        if (!chk2.isEmpty()) {
                            for (int i = 0; i < chk2.size(); i++) {
                                Vector ele = (Vector) chk2.get(i);
                                tmpAcNo = ele.get(0).toString();
                                custName = ele.get(1).toString();
                                List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                if (!chk3.isEmpty()) {
                                    Vector ele1 = (Vector) chk3.get(0);
                                    acSt = ele1.get(0).toString();
                                }
                            }
                        } else {
                            message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                            return message;
                        }
                    }
                } else {
                    message = "false";
                    return message;
                }
            } else {
                if (!introAcno.equalsIgnoreCase("")) {
                    List chk1 = em.createNativeQuery("SELECT ACNO from customerid WHERE CUSTID=" + introCustId + " AND ACNO='" + introAcno + "'").getResultList();
                    if (chk1.isEmpty()) {
                        message = "THIS CUSTOMER ID AND A/C. NO. ARE NOT CORRECT , SO PLEASE ENTER CORRECT ID AND A/C. NO.";
                        return message;
                    } else {
                        List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acno='" + introAcno + "')").getResultList();
                        if (!acNatList.isEmpty()) {
                            Vector ele = (Vector) acNatList.get(0);
                            acNat = ele.get(0).toString();
                        } else {
                            message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT !!!";
                            return message;
                        }
                        if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            List chk2 = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                            if (!chk2.isEmpty()) {
                                for (int i = 0; i < chk2.size(); i++) {
                                    Vector ele = (Vector) chk2.get(i);
                                    tmpAcNo = ele.get(0).toString();
                                    custName = ele.get(1).toString();
                                    List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                    if (!chk3.isEmpty()) {
                                        Vector ele1 = (Vector) chk3.get(0);
                                        acSt = ele1.get(0).toString();
                                    }
                                }
                            } else {
                                message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                                return message;
                            }
                        } else {
                            List chk2 = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                            if (!chk2.isEmpty()) {
                                for (int i = 0; i < chk2.size(); i++) {
                                    Vector ele = (Vector) chk2.get(i);
                                    tmpAcNo = ele.get(0).toString();
                                    custName = ele.get(1).toString();
                                    List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + ele.get(2).toString() + "").getResultList();
                                    if (!chk3.isEmpty()) {
                                        Vector ele1 = (Vector) chk3.get(0);
                                        acSt = ele1.get(0).toString();
                                    }
                                }
                            } else {
                                message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                                return message;
                            }
                        }
                    }
                } else {
                    message = "false";
                    return message;
                }
            }
            message = "true" + tmpAcNo + ":" + custName + ":" + acSt;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public List getJtName(Integer custId) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select custname,ifnull(middle_name,''),ifnull(last_name,'') from  cbs_customer_master_detail "
                    + "where customerId = " + custId + " and auth='Y' and (suspensionflg is null or suspensionflg = 'N' or suspensionflg ='')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultlist;
    }

    public String cbsCustId(String acctType, String brcode) throws ApplicationException {

        Integer custno = 0;
        Integer custOpt = 0;
        Integer custOpt1 = 0;
        try {
            String acNature = facadeRemote.getAcNatureByCode(acctType);
            List branchmasterList = em.createNativeQuery("Select * From parameterinfo where UPPER(Acc_Seq)='I'").getResultList();
            if (branchmasterList.size() > 0) {
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List tdcustomerMasterList = em.createNativeQuery("select coalesce(max(custno),'000000') From td_customermaster where actype='" + acctType + "' AND brncode = '" + brcode + "'").getResultList();
                    if (tdcustomerMasterList.size() > 0) {
                        Vector tdcustomerMasterVect = (Vector) tdcustomerMasterList.get(0);
                        custno = Integer.parseInt(tdcustomerMasterVect.get(0).toString()) + 1;
                    }
                } else {
                    List customerMasterList = em.createNativeQuery("select coalesce(max(custno),'000000') From customermaster where actype='" + acctType + "' AND brncode = '" + brcode + "'").getResultList();
                    if (customerMasterList.size() > 0) {
                        Vector customerMasterListVect = (Vector) customerMasterList.get(0);
                        custno = Integer.parseInt(customerMasterListVect.get(0).toString()) + 1;
                    }
                }
            } else {
                List tdcustomerMasterList1 = em.createNativeQuery("select coalesce(max(custno),'000000') From td_customermaster WHERE brncode ='" + brcode + "'").getResultList();
                if (tdcustomerMasterList1.size() > 0) {
                    Vector tdcustomerMasterVect1 = (Vector) tdcustomerMasterList1.get(0);
                    custOpt1 = Integer.parseInt(tdcustomerMasterVect1.get(0).toString()) + 1;
                }
                List customerMasterList = em.createNativeQuery("select coalesce(max(custno),'000000') From customermaster WHERE brncode ='" + brcode + "'").getResultList();
                if (customerMasterList.size() > 0) {
                    Vector customerMasterListVect = (Vector) customerMasterList.get(0);
                    custOpt = Integer.parseInt(customerMasterListVect.get(0).toString()) + 1;
                }
                if ((custOpt1) >= (custOpt)) {

                    custno = custOpt1;
                } else {
                    custno = custOpt;
                }
            }
            return custno.toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveDlAcctOpenRegister(String actype, String Occupation, String OpMode, String orgncode, String agcode,
            Float Sanclimit, Float ROI, String Title, String custname, String CrAddress, String PrAddress, String phone,
            String enterby, String pan, String FrName, String Age, String JtName1, String JtName2, String IntAcct, String catvalue,
            String CustIDExist, String schemeCode, String intTableCode, Integer moratoriunPd, Float accPrefDr,
            Float accPrefCr, String rateCode, String disbType, String calcMethod, String calcOnInt, String calLevel,
            String compFreq, Integer peggFreq, String intAppFreq, Integer loanPdMonth,
            Integer loanPdDay, List table, String dob, String custId1, String custId2, String actCateg, String acOpenFromFlag, String hufFamily) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = facadeRemote.saveDlAcctOpenRegisterWithOutTranMgmt(actype, Occupation, OpMode, orgncode, agcode,
                    Sanclimit, ROI, Title, custname, CrAddress, PrAddress, phone,
                    enterby, pan, FrName, Age, JtName1, JtName2, IntAcct, catvalue,
                    CustIDExist, schemeCode, intTableCode, moratoriunPd, accPrefDr,
                    accPrefCr, rateCode, disbType, calcMethod, calcOnInt, calLevel,
                    compFreq, peggFreq, intAppFreq, loanPdMonth,
                    loanPdDay, table, dob, custId1, custId2, actCateg, acOpenFromFlag, "", hufFamily);

            if (result.substring(0, 4).equalsIgnoreCase("true")) {
                ut.commit();
                return result;
            } else if (result.contains("Customer Verification")) {
                ut.rollback();
                return "Customer Verification is not completed.";
            } else if (result.substring(0, 5).equalsIgnoreCase("false")) {
                ut.rollback();
                return "DATA IS NOT SAVED";
            } else {
                ut.rollback();
                return "DATA IS NOT SAVED";
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List fnAcnat(String acType) throws ApplicationException {
        List accounttypemaster = new ArrayList();
        try {
            accounttypemaster = em.createNativeQuery("Select AcctNature,glhead from accounttypemaster where AcctCode = '" + acType + "'").getResultList();
            return accounttypemaster;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getStatus() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select Code,Description From codebook Where groupCode=33 and Code not in (0,2)").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getSecurityDesc1(String securityType) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select description from loan_codebook where substring(cast(groupcode as char(8)),1,2)='91' and "
                    + "substring(cast(groupcode as char(8)),4,2) in (CONCAT((select cast(code as char(10)) from loan_codebook where description='"
                    + securityType + "' and substring(cast(groupcode as char(8)),1,2)='91' and flag='Y'),'0')) and flag='Y' "
                    + "order by groupcode ,code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getSecurityDesc2(String securityType, String securityDesc1) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select description from loan_codebook where substring"
                    + "(cast(groupcode as char(8)),1,2)='91' and substring(cast(groupcode as char(8)),4,2)="
                    + "CONCAT((select cast(code as char(10)) from loan_codebook where description='" + securityType + "' "
                    + "and substring(cast(groupcode as char(8)),1,2) ='91' and flag='Y'),(select cast(code as char(10))"
                    + "from loan_codebook where description='" + securityDesc1 + "' and "
                    + "substring(cast(groupcode as char(8)),1,2)='91'and flag='Y' "
                    + "AND substring(cast(groupcode as char(8)),4,1)=(select cast(code as char(10)) "
                    + "from loan_codebook where description='" + securityType + "' and substring(cast(groupcode "
                    + "as char(8)),1,2)='91' and flag='Y'))) and  flag='Y' order by groupcode,code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }
    
    public List getSecurityDescAll() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select distinct SecurityOption from loansecurity where SecurityOption not in (select acctcode from accounttypemaster);").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String getSecurityDesc2Code(String securityType, String securityDesc1, String securityDesc2) throws ApplicationException {
        String resultlist = null;
        try {
            List resultlist1 = em.createNativeQuery("select code from loan_codebook where substring"
                    + "(cast(groupcode as char(8)),1,2)='91' and substring(cast(groupcode as char(8)),4,2)="
                    + "CONCAT((select cast(code as char(10)) from loan_codebook where description='" + securityType + "' "
                    + "and substring(cast(groupcode as char(8)),1,2) ='91' and flag='Y'),(select cast(code as char(10))"
                    + "from loan_codebook where description='" + securityDesc1 + "' and "
                    + "substring(cast(groupcode as char(8)),1,2)='91'and flag='Y' "
                    + "AND substring(cast(groupcode as char(8)),4,1)=(select cast(code as char(10)) "
                    + "from loan_codebook where description='" + securityType + "' and substring(cast(groupcode "
                    + "as char(8)),1,2)='91' and flag='Y'))) and  flag='Y' and description = '" + securityDesc2 + "' order by groupcode,code").getResultList();
            if (!resultlist1.isEmpty()) {
                Vector element = (Vector) resultlist1.get(0);
                resultlist = element.get(0).toString();
            } else {
                resultlist = "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getSecurityDesc3(String securityType, String securityDesc1, String securityDesc2) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select description from loan_codebook where substring(cast(groupcode as "
                    + "char(8)),1,2)='91' and substring(cast(groupcode as char(8)),4,3)=CONCAT((select cast(code as char(10)) "
                    + "from loan_codebook where description='" + securityType + "' and substring(cast(groupcode as char(8)),1,2)='91'"
                    + " and flag='Y'),(select cast(code as char(10)) from loan_codebook where description= '" + securityDesc1 + "' and  "
                    + "substring(cast(groupcode as char(8)),4,1)=(select cast(code as char(10)) from loan_codebook "
                    + "where description='" + securityType + "' and substring(cast(groupcode as char(8)),1,2)='91' and flag='Y')"
                    + "and substring(cast(groupcode as char(8)),1,2)='91' and flag='Y'), (select cast(code as char(10)) "
                    + "from loan_codebook where description='" + securityDesc2 + "'and substring(cast(groupcode as char(8)),4,1)="
                    + "(select cast(code as char(10)) from loan_codebook where description='" + securityType + "' "
                    + "and substring(cast(groupcode as char(8)),1,2)='91' and flag='Y')  and substring(cast(groupcode as char(8)),1,2)"
                    + "='91' and flag='Y'))and flag='Y' order by groupcode,code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public int getRdEnableCode() throws ApplicationException {
        try {
            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='RD ROI ENABLE'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }
            return code;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getMemDetails() throws ApplicationException {
        try {
            String msg = "false";
            int code = 0;
            List memList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='MEM_CHECK'").getResultList();
            if (!memList.isEmpty()) {
                Vector v1 = (Vector) memList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
                if (code == 1) {
                    msg = "true";
                } else {
                    msg = "false";
                }
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public int chkMonDetails() throws ApplicationException {
        try {
            int monCode = 0;
            List monList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='MON_CHECK'").getResultList();
            if (!monList.isEmpty()) {
                Vector v2 = (Vector) monList.get(0);
                monCode = Integer.parseInt(v2.get(0).toString());
            }
            return monCode;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List chkSchDetails(String schCode) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select eefc_scheme_flag,status_option,nre_scheme_flag,transaction_reference_number_flag,min_commit_utilisation from cbs_scheme_general_scheme_parameter_master "
                    + " where scheme_code = '" + schCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String shareCompare(String custId, Float Odlimit, String schCode, String acNo) throws ApplicationException {
        double odAmt = 0;
        double totAmt = 0;
        try {
            String acNoExist = "";
            String msg = "false";
            List sList = em.createNativeQuery("select ifnull(sum(odlimit),0), a.acno from accountmaster a, customerid cu where a.acno in ("
                    + " select acno from cbs_loan_acc_mast_sec where scheme_code in ("
                    + " select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag = 'Y' and scheme_code = '" + schCode + "')) and a.acno = cu.acno "
                    + " and cu.custid = '" + custId + "' and accstatus<>9 " + acNoExist + " "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','DL') and CrDbFlag in('B','D')) group by a.acno").getResultList();
            if (!sList.isEmpty()) {
                for (int l = 0; l < sList.size(); l++) {
                    Vector v1 = (Vector) sList.get(l);
                    double outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(v1.get(1).toString(), ymd.format(new Date())));
                    odAmt = odAmt + (outstanding < 0 ? Math.abs(outstanding) : (outstanding * -1));
                }
            }

            totAmt = odAmt + Odlimit;

            Vector tempVector = (Vector) em.createNativeQuery("select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <='" + ymd.format(new Date()) + "')").getResultList().get(0);
            double shareValue = Double.parseDouble(tempVector.get(0).toString());

            Vector shVector = (Vector) em.createNativeQuery("select ifnull(count(*),0) from share_capital_issue where FolioNo in (select "
                    + " regfoliono from share_holder where custid ='" + custId + "')").getResultList().get(0);
            double totShare = Double.parseDouble(shVector.get(0).toString());
            double totShVal = shareValue * totShare;

            List sharePer = em.createNativeQuery("select ifnull(commitment_event,0) from cbs_scheme_currency_details where scheme_Code ='" + schCode + "'").getResultList();
            double shPer = 0.0;
            if (!sharePer.isEmpty()) {
                Vector sharePerLst = (Vector) sharePer.get(0);
                shPer = Double.parseDouble(sharePerLst.get(0).toString());
            }

            //double perVal = (totAmt * shPer) / 100.0;
            double perVal = (totAmt / shPer);

            int chkVal = Double.compare(perVal, totShVal);

            if (chkVal <= 0) {
                msg = "true";
            } else {
                msg = "false";
            }
            return msg;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String loanAmtCompare(String custId, Float Odlimit, String schCode, String returnParameter, String acNo) throws ApplicationException {
        double odAmt = 0, totAmt = 0, salValue = 0, maxValue = 0, basicSal = 0, totalSalValue = 0;
        try {
            String msg = "false", acNoExist = "";
            List acNoAuthPendingExist = em.createNativeQuery("select custid, acno from customerid where ACNo in (SELECT acno from loan_oldinterest where authby is null or authby='' ORDER BY TXNID)  and custid = " + custId).getResultList();
            if (!acNoAuthPendingExist.isEmpty()) {
                return "Loan Limit Authorization is pending for the same or another account of this Customer Id " + custId;
            }
            List sList = em.createNativeQuery("select ifnull(sum(odlimit),0), a.acno from accountmaster a, customerid cu where a.acno in ("
                    + " select acno from cbs_loan_acc_mast_sec where scheme_code in ("
                    + " select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag = 'Y' and scheme_code = '" + schCode + "')) and a.acno = cu.acno "
                    + " and cu.custid = '" + custId + "' and accstatus<>9 " + acNoExist + " "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in('TL','CA','DL') and CrDbFlag in('B','D')) group by a.acno").getResultList();
            if (!sList.isEmpty()) {
                for (int l = 0; l < sList.size(); l++) {
                    Vector v1 = (Vector) sList.get(l);
                    double outstanding = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(v1.get(1).toString(), ymd.format(new Date())));
                    odAmt = odAmt + (outstanding < 0 ? Math.abs(outstanding) : (outstanding * -1));
                }
            }
            totAmt = odAmt + Odlimit;
            List salList = em.createNativeQuery("select ifnull(salary,0),ifnull(gradepay,0) from share_holder where custid = '" + custId + "'").getResultList();
            if (!salList.isEmpty()) {
                Vector tempVector = (Vector) salList.get(0);
                salValue = Double.parseDouble(tempVector.get(0).toString());
                basicSal = Double.parseDouble(tempVector.get(1).toString());
                totalSalValue = salValue + basicSal;
            }

            List maxSchValueList = em.createNativeQuery("select max(loan_amount_max) from cbs_scheme_loan_scheme_details where scheme_code "
                    + " in (select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag='Y') and scheme_code = '" + schCode + "'").getResultList();
            if (!maxSchValueList.isEmpty()) {
                Vector tempVector1 = (Vector) em.createNativeQuery("select max(loan_amount_max) from cbs_scheme_loan_scheme_details where scheme_code "
                        + " in (select scheme_code from cbs_scheme_general_scheme_parameter_master where eefc_scheme_flag='Y') and scheme_code = '" + schCode + "'").getResultList().get(0);
                maxValue = Double.parseDouble(tempVector1.get(0).toString());
            }
            int chkVal = Double.compare(totAmt, maxValue);
            if (chkVal <= 0) {
                Vector tempVector2 = (Vector) em.createNativeQuery("select loan_amount_max from cbs_scheme_loan_scheme_details where scheme_code = '" + schCode + "'").getResultList().get(0);
                double mVal = Double.parseDouble(tempVector2.get(0).toString());

                double chVal = 0;

                List prd = em.createNativeQuery("select ifnull(include_floor_limit_for_tax,0) from cbs_scheme_currency_details where scheme_Code ='" + schCode + "'").getResultList();
                /**
                 * ***Flag include_floor_limit_for_tax will be multiplied with
                 * Salary***
                 */
                double sPrd = 0.0;
                if (!prd.isEmpty()) {
                    Vector prdLst = (Vector) prd.get(0);
                    sPrd = Double.parseDouble(prdLst.get(0).toString());
                }

                int cHk = Double.compare(mVal, (totalSalValue * sPrd));
                if (cHk <= 0) {//Scheme max Limit
                    chVal = mVal;
                } else {//Limit = Salary*N Times
                    chVal = totalSalValue * sPrd;
                }

                int cHk1 = Double.compare(totAmt, chVal);
                if (returnParameter.equalsIgnoreCase("msg")) {//Only return the Message
                    if (cHk1 <= 0) {
                        return msg = "true";
                    } else {
                        return msg = "Sanction Amount Greater Than Salary Limit Defined";
                    }
                } else {
                    if (cHk1 <= 0) {
                        if (Odlimit != 0) {
                            return String.valueOf(Odlimit);
                        } else {
                            return String.valueOf(0);
                        }
                    } else {
                        return String.valueOf(0);
                    }
                }
            } else {
                if (returnParameter.equalsIgnoreCase("msg")) {//Only return the Message
                    return msg = "Total Amount is Greater Than Limit Defined";
                } else {
                    return String.valueOf(0);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    public String saveFidilityAccountOpen(String mode, String custId, String accNo, String openDt, String businessDesig, String cuName, String actype, String userName, String orgnBrCode, String schemeCode, double bAmt, double prAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (mode.equalsIgnoreCase("1")) {
                String custNoNew1 = "";

                List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId + "'").getResultList();
                Vector cuLst = (Vector) custList.get(0);
                String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
                if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Customer Verification is not completed.";
                }

                String cFlag = custMergedFlag(custId);
                if (cFlag.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Customer Id Is Merged.";
                }

                List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + orgnBrCode + "").getResultList();
                Vector alphaLst = (Vector) alphaList.get(0);
                String alphacode = alphaLst.get(0).toString();

                String custName = "";
                List fidilityMasterList = em.createNativeQuery("select coalesce(max(substring(acno,5,6)),'000000') From fidility_accountmaster where ACCTTYPE='" + actype + "' AND brncode = '" + orgnBrCode + "'").getResultList();
                if (fidilityMasterList.size() > 0) {
                    Vector fidilityMasterVect = (Vector) fidilityMasterList.get(0);
                    custName = Integer.toString(Integer.parseInt(fidilityMasterVect.get(0).toString()) + 1);
                }

                String custNoNew = custName;
                if (custNoNew.length() == 1) {
                    custNoNew1 = "00000" + custNoNew;
                }
                if (custNoNew.length() == 2) {
                    custNoNew1 = "0000" + custNoNew;
                }
                if (custNoNew.length() == 3) {
                    custNoNew1 = "000" + custNoNew;
                }
                if (custNoNew.length() == 4) {
                    custNoNew1 = "00" + custNoNew;
                }
                if (custNoNew.length() == 5) {
                    custNoNew1 = "0" + custNoNew;
                }
                if (custNoNew.length() == 6) {
                    custNoNew1 = custNoNew;
                }
                String acno = orgnBrCode + actype + custNoNew1 + "01";

                Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                        + "values ('" + acno + "','" + acno + "')");
                insertMapping.executeUpdate();

                Query insertQuery2 = em.createNativeQuery("insert into fidility_accountmaster(ACNO,Desig_Code,CUSTNAME,OPENINGDT,ACCTTYPE,AccStatus,Closingdate,EnterBy,Auth,AuthBy,CustId,Brncode,BondAmt,PremiumAmt)"
                        + "values (" + "'" + acno + "'" + "," + "'" + businessDesig + "'" + "," + "'" + cuName + "'" + "," + "'" + openDt + "'" + "," + actype + "," + '1' + "," + "NULL" + "," + "'" + userName + "'" + ",'N',''," + "'" + custId + "'" + "," + "'" + orgnBrCode + "'," + bAmt + "," + prAmt + ")");
                insertQuery2.executeUpdate();

                Query insertQuery6 = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn)"
                        + "values (" + "'" + custId + "'" + "," + "'" + acno + "'" + "," + "'" + userName + "'" + "," + "'" + alphacode + "'" + ")");

                //Addition to save in the table on the basis of Scheme Code.
                String rateCode = "";
                String calMethod = "";
                Date dt = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String date = sdf.format(dt);
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                String oneBackDay = sdf.format(cal.getTime());

                /**
                 * *Addition end here***
                 */
                String intCode = "";
                List schemeValueList = em.createNativeQuery("select l.INT_BASE_METHOD,l.INT_PRODUCT_METHOD,l.COMPOUNDING_FREQ,t.INTEREST_TABLE_CODE"
                        + " from cbs_scheme_loan_scheme_details l,cbs_scheme_tod_reference_details t where l.SCHEME_CODE='" + schemeCode + "' AND t.SCHEME_CODE='" + schemeCode + "'").getResultList();
                if (!schemeValueList.isEmpty()) {
                    Vector schemeValMasterVect = (Vector) schemeValueList.get(0);
                    intCode = schemeValMasterVect.get(3).toString();
                } else {
                    ut.rollback();
                    return "Interest Table Code Not Defined For this Scheme";
                }

                Query insertQuery8 = em.createNativeQuery("INSERT INTO cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                        + " VALUES ('" + acno + "','" + schemeCode + "','" + intCode + "'," + 0 + ",0.00,0.00 ,'" + rateCode + "','','','" + calMethod + "','','','','" + calMethod + "','0',0,0,'" + oneBackDay + "','" + oneBackDay + "','" + date + "')");
                insertQuery8.executeUpdate();

                ut.commit();
                return "Account Open Successfully Account No Is   " + acno + "  And Id Is   " + custId;

            } else {
                Integer certificate = em.createNativeQuery("update fidility_accountmaster set auth='Y', authby='" + userName + "'  where acno='" + accNo + "'").executeUpdate();
                if (certificate > 0) {
                    ut.commit();
                    return "Account Details successfully verified.";
                } else {
                    throw new ApplicationException("Problem in verifing fidility_accountmaster");
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(ex);
            }
            throw new ApplicationException(ex);
        }
    }

    public List getDetail(String acNo) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select fa.CUSTNAME,cd.fathername,cd.MailAddressLine1,cd.MailAddressLine2,cd.PerAddressLine1,cd.PerAddressLine2,fa.Desig_Code,fa.BondAmt,fa.PremiumAmt,"
                    + " DATE_FORMAT(fa.OPENINGDT,'%d/%m/%Y'),cd.PAN_GIRNumber,DATE_FORMAT(cd.dateofbirth,'%d/%m/%Y'),fa.EnterBy from fidility_accountmaster fa, cbs_customer_master_detail cd where fa.CustId = cd.customerid and acno = '" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getUnAuthAccountList(String brncode) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from fidility_accountmaster where auth='N' and brncode = '" + brncode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List memInfo(String folioNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select * from share_holder where regfoliono= '" + folioNo + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List loanForMemInfo(String acNo) throws ApplicationException {
        List loanCustIdList = null;
        try {
            loanCustIdList = em.createNativeQuery("select regfoliono from share_holder where custid = (select custid from customerid where acno = '" + acNo + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return loanCustIdList;
    }

    public List memMirDue(String acNo) throws ApplicationException {
        List result = null;
        try {
            result = em.createNativeQuery("select ifnull(sum(dramt - cramt),0) from mem_mir_recon where acno = '" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List getRDRate(double amount, int days, String IntCode) throws ApplicationException {
        List result = null;
        try {
//            result = em.createNativeQuery("select interest_rate,SC,ST,OT from td_slab where " + days + " between fromdays and todays and " + amount + " between fromamount and toamount and applicable_date = (select max(applicable_date) from td_slab where applicable_date < curdate())").getResultList();
            result = em.createNativeQuery("select normal_interest_indicator, normal_interest_percentage,min(loan_period_months) from "
                    + " cbs_loan_interest_slab_master where interest_code = '" + IntCode + "' and " + amount + " between begin_slab_amount and end_slab_amount "
                    + " and record_status = 'A' and interest_version_no = 1 and loan_period_months>= " + days + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public List getRDDaysLst(String IntCode) throws ApplicationException {
        List result = null;
        try {
            result = em.createNativeQuery("select cast(min(loan_period_months) as unsigned),cast(max(loan_period_months) as unsigned) from cbs_loan_interest_slab_master where interest_code = '" + IntCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public String custMergedFlag(String custId) throws ApplicationException {
        String msg = "true";
        try {
            List result = em.createNativeQuery("select * from cbs_id_merge_history where (oldcustid = '" + custId + "' or jtid1 = '" + custId + "' "
                    + " or jtid2 = '" + custId + "' or jtid3 = '" + custId + "' or jtid4 = '" + custId + "')").getResultList();
            if (!result.isEmpty()) {
                msg = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String getCustAcTdsDocDtl(String customerId, String acno, String flag) throws ApplicationException {
        String msg = "false";
        try {
            if (flag.equalsIgnoreCase("C")) {
                List result = em.createNativeQuery("select * from tds_docdetail where customerid = '" + customerId + "'").getResultList();
                if (!result.isEmpty()) {
                    msg = "true";
                }else{
                   result = em.createNativeQuery("select * from tds_docdetail where customerid in(select ifnull(guardiancode,'') from cbs_cust_minorinfo where customerid = '"+customerId+"' and ifnull(guardiancode,'') <> '')").getResultList();
                   if(!result.isEmpty()){
                       msg = "true";
                   }
                }
            } else {
                List result = em.createNativeQuery("select * from tds_docdetail where acno = '" + acno + "'").getResultList();
                if (!result.isEmpty()) {
                    msg = "true";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }
    //---Added by Manish Kumar

    public List getOccupation(String custId) throws ApplicationException {
        List selectList = new ArrayList();
        try {
            //selectList = em.createNativeQuery("select c.Code, c.description from cbs_cust_misinfo ccm, codebook c where c.groupcode=6  and c.code=ccm.OccupationCode and customerid='"+custId+"'").getResultList();
            System.out.println("select ifnull(m.OccupationCode,26),r.ref_desc from cbs_cust_misinfo m,cbs_ref_rec_type r where customerid='" + custId + "' and r.ref_rec_no='021' and r.ref_code=ifnull(m.OccupationCode,26)");
            selectList = em.createNativeQuery("select ifnull(m.OccupationCode,26),r.ref_desc from cbs_cust_misinfo m,cbs_ref_rec_type r where customerid='" + custId + "' and r.ref_rec_no='021' and r.ref_code=ifnull(m.OccupationCode,26)").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return selectList;
    }

    public String customerId(String acountNumber) throws ApplicationException {
        String customerId = "";
        try {
            List selectList = em.createNativeQuery("select CustId from customerid where Acno ='" + acountNumber + "'").getResultList();
            if (selectList.isEmpty()) {
                throw new ApplicationException("Customer Id does not exist according given Acount Number!");
            } else {
                Vector vec = (Vector) selectList.get(0);
                customerId = vec.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return customerId;
    }

    public String removeSomeSpecialChar(String str) throws ApplicationException {
        String result = "";
        try {
            String charToDel = "><#?%^!@*";
            String pat = "[" + Pattern.quote(charToDel) + "]";
            result = str.replaceAll(pat, "");
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String getRecRefDiscription(String RecRefNo, String RecRefCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where"
                    + " REF_REC_NO='" + RecRefNo + "' and REF_CODE='" + RecRefCode + "'").getResultList();
            if (list.size() > 0) {
                return ((Vector) list.get(0)).get(0).toString();
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
    //---

    public String getChqFacilityTrue(String acctType) throws ApplicationException {
        String chqVal = "false", flag;
        try {
            List selectList = em.createNativeQuery("select cheque_allowed_flag from cbs_scheme_interest_or_service_charges_details "
                    + " where scheme_type ='" + acctType + "'").getResultList();
            if (!selectList.isEmpty()) {
                Vector vec = (Vector) selectList.get(0);
                flag = vec.get(0).toString();
                if (flag.equalsIgnoreCase("Y")) {
                    chqVal = "true";
                } else {
                    chqVal = "false";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return chqVal;
    }

    public List getChbookStatusList(String acno, String acnature) throws ApplicationException {
        List acList = new ArrayList<>();
        try {
            String acTableQuery = "";
            if (acnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                acTableQuery = "chbook_ca";
            } else if (acnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                acTableQuery = "chbook_sb";
            }
            List list = em.createNativeQuery("select StatusFlag from  " + acTableQuery + " where Acno = '" + acno + "'").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    acList.add(vec.get(0).toString());
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acList;
    }

    @Override
    public List getCustIdForRenewal(String acNo, String receoptNo, String voucherNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select custid,a.ACNO,a.ReceiptNo,VoucherNo from td_vouchmst a,customerid b where a.acno = '" + acNo + "'and a.acno = b.acno and ReceiptNo = " + receoptNo + " and VoucherNo = " + voucherNo + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List isCustIdForRenewalAuth(String custId, String acNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select custid,a.ACNO,a.ReceiptNo,rtNoHide VoucherNo from td_renewal_auth a,customerid b where a.acno = '" + acNo + "'and a.acno = b.acno  and custid = " + custId + " and Auth ='N'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
