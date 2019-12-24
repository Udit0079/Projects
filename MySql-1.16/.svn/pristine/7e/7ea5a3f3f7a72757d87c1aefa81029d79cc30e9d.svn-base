/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ankit Verma
 */
@Stateless(mappedName = "AccountMaintenanceFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountMaintenanceFacade implements AccountMaintenanceFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    AccountOpeningFacadeRemote openingFacadeRemote;

    /**
     * **********************AccountMaintenanceRegisterBean's
     * Method******************************
     */
    public List onLoadList1() throws ApplicationException {
        List actypeLst = new ArrayList();
        try {
            actypeLst = em.createNativeQuery("select acctcode From accounttypemaster where acctNature not in ('PO','FD','MS','OF')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actypeLst;
    }

    public List onLoadList3() throws ApplicationException {
        List opermode = new ArrayList();
        try {
            opermode = em.createNativeQuery("select code,description  from codebook where groupcode = 4 and code <> 0 order by code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return opermode;
    }

    public List onLoadList4() throws ApplicationException {
        List orgtype = new ArrayList();
        try {
            orgtype = em.createNativeQuery("Select code,description from codebook where groupcode=6 and code <> 0 order by code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return orgtype;
    }

    public List onLoadList5() throws ApplicationException {
        List docname = new ArrayList();
        try {
            docname = em.createNativeQuery("select code,description from codebook where  groupcode = 14 and code <> 0 order by code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return docname;
    }

    public List grid(String acno) throws ApplicationException {
        List lst = new ArrayList();
        try {
            String brcode = facadeRemote.getCurrentBrnCode(acno);
            lst = em.createNativeQuery("select b.description,a.docudetails from  documentsreceived a, codebook b,accountmaster am  where a.acno= am.acno and am.curBrcode ='" + brcode + "' and a.groupdocu=14 and b.groupcode=14 and a.docuno=b.code and a.acno='" + acno + "' ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return lst;
    }
    /*
     * Changes By Navneet
     * Adding new method for getting account type
     */

    public String custInfoEdit(String acno) throws ApplicationException {
        try {
            String acNat = facadeRemote.getAccountNature(acno);
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                throw new ApplicationException("FD Nature Accounts can not be edited from this form.");
            }

            String brcode = facadeRemote.getCurrentBrnCode(acno);
            List reslist = em.createNativeQuery("select accstatus from accountmaster where acno='" + acno + "'").getResultList();
            if (reslist.isEmpty()) {
                throw new ApplicationException("This Account No Does Not Exist");
            }
            Vector recLst = (Vector) reslist.get(0);
            int acStatus = Integer.parseInt(recLst.get(0).toString());
//            if (acStatus == 9) {
//                throw new ApplicationException("This Account has been closed");
//            }
            List custIdList = em.createNativeQuery("select custid from customerid where acno = '" + acno + "'").getResultList();
            if (custIdList.isEmpty()) {
                throw new ApplicationException("Customer Id does not exists corresponding to this Account No.");
            }
            Vector custIdLst = (Vector) custIdList.get(0);
            String custId = custIdLst.get(0).toString();

//            List custDetailValuesLst = em.createNativeQuery("select ifnull(fathername,'') as fathername,ifnull(custname,'') as custname,"
//                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,'')as peraddressline2,ifnull(mailaddressline1,'') "
//                    + "as mailaddressline1,ifnull(mailaddressline2,'') as mailaddressline2,ifnull(mobilenumber,'') as mobilenumber,"
//                    + "ifnull(pan_girnumber,'')as pan_girnumber,ifnull(perVillage,'') as perVillage,ifnull(perBlock,'') as perBlock,"
//                    + "ifnull(percitycode,'') as percitycode,ifnull(perstatecode,'') as perstatecode,ifnull(perpostalcode,'') as perpostalcode,"
//                    + "ifnull(percountrycode,'') as percountrycode,ifnull(mailvillage,'') as mailvillage,ifnull(mailBlock,'') as mailBlock,"
//                    + "ifnull(mailcitycode,'') as mailcitycode,ifnull(mailstatecode,'') as mailcitycode,ifnull(mailpostalcode,'') as "
//                    + "mailpostalcode,ifnull(mailcountrycode,'') as mailcountrycode,ifnull(middle_name,'') as middle_name,"
//                    + "ifnull(last_name,'') as last_name from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            List custDetailValuesLst = em.createNativeQuery("select ifnull(fathername,'') as fathername,ifnull(custname,'') as custname,"
                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,'')as peraddressline2,ifnull(mailaddressline1,'') "
                    + "as mailaddressline1,ifnull(mailaddressline2,'') as mailaddressline2,ifnull(mobilenumber,'') as mobilenumber,"
                    + "ifnull(pan_girnumber,'')as pan_girnumber,ifnull(perVillage,'') as perVillage,ifnull(perBlock,'') as perBlock,"
                    + "ifnull(percitycode,'') as percitycode,ifnull(perstatecode,'') as perstatecode,ifnull(perpostalcode,'') as perpostalcode,"
                    + "ifnull(percountrycode,'') as percountrycode,ifnull(mailvillage,'') as mailvillage,ifnull(mailBlock,'') as mailBlock,"
                    + "ifnull(mailcitycode,'') as mailcitycode,ifnull(mailstatecode,'') as mailcitycode,ifnull(mailpostalcode,'') as "
                    + "mailpostalcode,ifnull(mailcountrycode,'') as mailcountrycode,ifnull(middle_name,'') as middle_name,"
                    + "ifnull(last_name,'') as last_name,ifnull(FatherMiddleName,'') as f_middle_name,"
                    + "ifnull(FatherLastName,'') as f_last_name from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            if (custDetailValuesLst.isEmpty()) {
                throw new ApplicationException("Data does not exist for this account in Account Master.");
            }
            List custValuesLst = em.createNativeQuery("select coalesce(am.chequebook,0),ifnull(am.nomination,''),ifnull(am.relatioship,''),"
                    + "ifnull(am.instruction,''),ifnull(date_format(am.openingdt, '%d/%m/%Y'),'') ,"
                    + "ifnull(date_format(am.rdmatdate, '%d/%m/%Y'),''),coalesce(am.rdinstal,0),coalesce(am.minbal,0),"
                    + "coalesce(am.intdeposit,0),ifnull(cm.grdname,''),ifnull(am.introaccno,''),coalesce(am.opermode,0),"
                    + "coalesce(am.orgncode,0),ifnull(am.jtname1,''),ifnull(am.jtname2,''),ifnull(am.jtname3,''),"
                    + "ifnull(am.jtname4,''),ifnull(am.custid1,''),ifnull(am.custid2,''),"
                    + "ifnull(am.custid3,''),ifnull(am.custid4,''),ifnull(am.penalty1,0),am.acctCategory,ifnull(am.tdsflag,''),ifnull(nullif(am.huf_family,''),'Blank') from accountmaster am,customermaster cm where substring(am.acno,1,2)=cm.brncode "
                    + "and am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and am.acno='" + acno + "'").getResultList();
            if (custValuesLst.isEmpty()) {
                throw new ApplicationException("Data does not exist for this account in Account Master.");
            }

            List nomineevalue = em.createNativeQuery("select ifnull(relation,''),ifnull(nomadd,''),"
                    + "ifnull(date_format(nomdob, '%d/%m/%Y'),'') from nom_details where acno='" + acno + "'").getResultList();

            String intoption = "";
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List intoptions = em.createNativeQuery("Select coalesce(Product,'') From loan_appparameter where acno='" + acno + "' and BrCode='"
                        + brcode + "' ").getResultList();
                if (intoptions.isEmpty()) {
                    throw new ApplicationException("Data does not exist for this account in Loan AppParameter.");
                }
                Vector res = (Vector) intoptions.get(0);
                intoption = res.get(0).toString();
            }

            Vector values = (Vector) custDetailValuesLst.get(0);
            String l1 = values.get(0).toString();

            String fMiddleName = values.get(22).toString();
            String fLastName = values.get(23).toString();

            l1 = l1.trim() + " " + fMiddleName.trim();
            l1 = l1.trim() + " " + fLastName.trim();

            String l2 = values.get(1).toString();

            String middleName = values.get(20).toString();
            String lastName = values.get(21).toString();

            l2 = l2.trim() + " " + middleName.trim();
            l2 = l2.trim() + " " + lastName.trim();

            String l4 = values.get(4).toString() + "," + values.get(5).toString(); // corresAdd
            if (!values.get(14).toString().equalsIgnoreCase("") || values.get(14).toString().length() != 0) {
                if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                    l4 = l4 + values.get(14).toString();
                } else {
                    l4 = l4 + "," + values.get(14).toString();
                }
            }
            if (!values.get(15).toString().equalsIgnoreCase("") || values.get(15).toString().length() != 0) {
                if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                    l4 = l4 + values.get(15).toString();
                } else {
                    l4 = l4 + "," + values.get(15).toString();
                }

            }
            String mailCity = "0";
            if (!values.get(16).toString().equalsIgnoreCase("0") || values.get(16).toString().length() != 0) {
                mailCity = values.get(16).toString();
                if (!mailCity.equalsIgnoreCase("0")) {
                    if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                        l4 = l4 + introducerCity("001", mailCity);
                    } else {
                        l4 = l4 + "," + introducerCity("001", mailCity);
                    }
                }
            }
            String mailState = "0";
            if (!values.get(17).toString().equalsIgnoreCase("0") || values.get(17).toString().length() != 0) {
                mailState = values.get(17).toString();
                if (!mailState.equalsIgnoreCase("0")) {
                    if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                        l4 = l4 + introducerCity("002", mailState);
                    } else {
                        l4 = l4 + "," + introducerCity("002", mailState);
                    }
                }
            }
            if (!values.get(18).toString().equalsIgnoreCase("") || values.get(18).toString().length() != 0) {
                if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                    l4 = l4 + values.get(18).toString();
                } else {
                    l4 = l4 + "," + values.get(18).toString();
                }
            }
            String mailCountry = "0";
            if (!values.get(19).toString().equalsIgnoreCase("0") || values.get(19).toString().length() != 0) {
                mailCountry = values.get(19).toString();
                if (!mailCountry.equalsIgnoreCase("0")) {
                    if (l4.substring(l4.lastIndexOf(",")).equals(",")) {
                        l4 = l4 + introducerCity("003", mailCountry);
                    } else {
                        l4 = l4 + "," + introducerCity("003", mailCountry);
                    }
                }
            }
            String l5 = values.get(2).toString() + "," + values.get(3).toString(); // permanenadd
            if (!values.get(8).toString().equalsIgnoreCase("") || values.get(8).toString().length() != 0) {
                if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                    l5 = l5 + values.get(8).toString();
                } else {
                    l5 = l5 + "," + values.get(8).toString();
                }
            }
            if (!values.get(9).toString().equalsIgnoreCase("") || values.get(9).toString().length() != 0) {
                if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                    l5 = l5 + values.get(9).toString();
                } else {
                    l5 = l5 + "," + values.get(9).toString();
                }
            }
            String perCity = "0";
            if (!values.get(10).toString().equalsIgnoreCase("0") || values.get(10).toString().length() != 0) {
                perCity = values.get(10).toString();
                if (!perCity.equalsIgnoreCase("0")) {
                    if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                        l5 = l5 + introducerCity("001", perCity);
                    } else {
                        l5 = l5 + "," + introducerCity("001", perCity);
                    }
                }
            }
            String perState = "0";
            if (!values.get(11).toString().equalsIgnoreCase("0") || values.get(11).toString().length() != 0) {
                perState = values.get(11).toString();
                if (!perState.equalsIgnoreCase("0")) {
                    if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                        l5 = l5 + introducerCity("002", perState);
                    } else {
                        l5 = l5 + "," + introducerCity("002", perState);
                    }
                }
            }
            if (!values.get(12).toString().equalsIgnoreCase("") || values.get(12).toString().length() != 0) {
                if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                    l5 = l5 + values.get(12).toString();
                } else {
                    l5 = l5 + "," + values.get(12).toString();
                }
            }
            String perCountry = "0";
            if (!values.get(13).toString().equalsIgnoreCase("0") || values.get(13).toString().length() != 0) {
                perCountry = values.get(13).toString();
                if (!perCountry.equalsIgnoreCase("0")) {
                    if (l5.substring(l5.lastIndexOf(",")).equals(",")) {
                        l5 = l5 + introducerCity("003", perCountry);
                    } else {
                        l5 = l5 + "," + introducerCity("003", perCountry);
                    }
                }
            }
            String l6 = values.get(6).toString(); // phoneNO
            String l7 = values.get(7).toString();

            Vector acctValues = (Vector) custValuesLst.get(0);
            String l3 = acctValues.get(0).toString(); //cheque No
            String l8 = acctValues.get(1).toString();
            String l9 = acctValues.get(2).toString();

            String l10 = acctValues.get(3).toString();
            String l11 = acctValues.get(4).toString();
            String l12 = acctValues.get(5).toString();

            String l13 = acctValues.get(6).toString();
            String l14 = acctValues.get(7).toString();
            String l15 = acctValues.get(8).toString();

            String l16 = acctValues.get(9).toString();
            String l17 = acctValues.get(10).toString();
            String l18 = acctValues.get(11).toString();
            String l19 = acctValues.get(12).toString();

            String l20 = acctValues.get(13).toString();
            String l21 = acctValues.get(14).toString();
            String l22 = acctValues.get(15).toString();
            String l23 = acctValues.get(16).toString();

            String jtCustId1 = acctValues.get(17).toString();
            String jtCustId2 = acctValues.get(18).toString();
            String jtCustId3 = acctValues.get(19).toString();
            String jtCustId4 = acctValues.get(20).toString();
            String dmdFlag = acctValues.get(21).toString();
            String actCategory = acctValues.get(22).toString();
            String tdsApp = acctValues.get(23).toString();
            if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                if (tdsApp.equalsIgnoreCase("")) {
                    tdsApp = "Y";
                }
            }
            String hFamily = acctValues.get(24).toString();

            String l24 = "";
            String l25 = "";
            String l26 = "";
            if (!nomineevalue.isEmpty()) {
                Vector values1 = (Vector) nomineevalue.get(0);
                l24 = values1.get(0).toString();
                l25 = values1.get(1).toString();
                l26 = values1.get(2).toString();
            }
            return acStatus + ":~" + intoption + ":~" + l1 + ":~" + l2 + ":~" + l3 + ":~" + l4 + ":~" + l5 + ":~" + l6 + ":~" + l7 + ":~" + l8 + ":~"
                    + l9 + ":~" + l10 + ":~" + l11 + ":~" + l12 + ":~" + l13 + ":~" + l14 + ":~" + l15 + ":~" + l16 + ":~" + l17 + ":~" + l18 + ":~"
                    + l19 + ":~" + l20 + ":~" + l21 + ":~" + l22 + ":~" + l23 + ":~" + l24 + ":~" + l25 + ":~" + l26 + ":~" + jtCustId1 + ":~"
                    + jtCustId2 + ":~" + jtCustId3 + ":~" + jtCustId4 + ":~" + custId + ":~" + dmdFlag + ":~" + actCategory + ":~" + tdsApp + ":~" + hFamily;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String customerId(String custId) throws ApplicationException {
        try {
            String custName, middleName, lastName;
            List secList = em.createNativeQuery("select ifnull(custname,''),ifnull(middle_name,''),ifnull(last_name,'') from cbs_customer_master_detail where customerid='" + custId + "'").getResultList();
            if (secList.isEmpty()) {
                throw new ApplicationException("Customer Detail does not exist");
            }
            Vector secListLst = (Vector) secList.get(0);

            custName = secListLst.get(0).toString();
            middleName = secListLst.get(1).toString();
            lastName = secListLst.get(2).toString();
            custName = custName.trim() + " " + middleName.trim();
            custName = custName.trim() + " " + lastName.trim();
            return custName;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateAcctOpenEdit(String acno, String custname, String craddress, String praddress, String phoneno, int occupation,
            int operatingMode, int document, String documentDetails, String panno, String grdname, String fathername, String acnoIntro,
            String JtName1, String JtName2, String nominee, String nominee_rel, String JtName3, String JtName4, float rdroi,
            float rdinstall, String openDt, String MatDt, String acctIns, int minBalOpt, int chkBookOpt, String int_option, String DateText,
            String UserText, String brcode, String nomineeAdd, String nominee_relatioship, String nomineeDate, String minor, Integer nomage,
            String jtName1Code, String jtName2Code, String jtName3Code, String jtName4Code, int dmdFlag, String actCateg, String tdsApp, String hufFamily) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String currentBrnCode = facadeRemote.getCurrentBrnCode(acno);
        int var1 = 0;
        try {
            ut.begin();
            String acNat = "";
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (acNatList.isEmpty()) {
                throw new ApplicationException("Account nature does not found for this account. Please fill valid A/C. no.");
            }
            Vector recLst = (Vector) acNatList.get(0);
            acNat = recLst.get(0).toString();

            if (!acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                tdsApp = "";
            }
            if (!((jtName1Code == null) || (jtName1Code.equalsIgnoreCase("")))) {
                String cFlag1 = openingFacadeRemote.custMergedFlag(jtName1Code);
                if (cFlag1.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtName1Code + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder1 Customer id " + jtName1Code + " Verification is not completed.";
                }
            }

            if (!((jtName2Code == null) || (jtName2Code.equalsIgnoreCase("")))) {
                String cFlag2 = openingFacadeRemote.custMergedFlag(jtName2Code);
                if (cFlag2.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtName2Code + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder2 Customer id " + jtName2Code + " Verification is not completed.";
                }
            }

            if (!((jtName3Code == null) || (jtName3Code.equalsIgnoreCase("")))) {
                String cFlag3 = openingFacadeRemote.custMergedFlag(jtName3Code);
                if (cFlag3.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder3 Customer Id Is Merged.";
                }

                List custList3 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtName3Code + "'").getResultList();
                Vector cuLst3 = (Vector) custList3.get(0);
                String cu_id3 = cuLst3.get(0).toString() != null ? cuLst3.get(0).toString() : "";
                if ((cu_id3.equalsIgnoreCase("N")) || (cu_id3 == null) || (cu_id3.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder3 Customer id " + jtName3Code + " Verification is not completed.";
                }
            }

            if (!((jtName4Code == null) || (jtName4Code.equalsIgnoreCase("")))) {
                String cFlag4 = openingFacadeRemote.custMergedFlag(jtName4Code);
                if (cFlag4.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder4 Customer Id Is Merged.";
                }

                List custList4 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + jtName4Code + "'").getResultList();
                Vector cuLst4 = (Vector) custList4.get(0);
                String cu_id4 = cuLst4.get(0).toString() != null ? cuLst4.get(0).toString() : "";
                if ((cu_id4.equalsIgnoreCase("N")) || (cu_id4 == null) || (cu_id4.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder4 Customer id " + jtName4Code + " Verification is not completed.";
                }
            }

            String int_option_old = "";
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.SS_AC)) {

                List int_option_old1 = em.createNativeQuery("Select ifnull(Product,'') From loan_appparameter where BrCode='" + brcode
                        + "' and acno='" + acno + "' ").getResultList();
                if (!int_option_old1.isEmpty()) {
                    Vector recLstt = (Vector) int_option_old1.get(0);
                    int_option_old = recLstt.get(0).toString();
                }
                var1 = em.createNativeQuery("Update loan_appparameter set Product ='" + int_option + "',custname='" + custname
                        + "' where BrCode='" + brcode + "' and  acno='" + acno + "' ").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in Loan App Parameter");
                }
            }
            List reslist = em.createNativeQuery("select acno from accountmaster where acno='" + acno + "' ").getResultList();
            if (reslist.isEmpty()) {
                throw new ApplicationException("Account No. does not exist");
            }
            if (document != 0) {
                var1 = em.createNativeQuery("insert into  documentsreceived (acno,groupDocu,DocuNo,DocuDetails,receivedDate)"
                        + "values ('" + acno + "',14," + document + ",'" + documentDetails + "','" + DateText + "')").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in Document details");
                }
            }
            var1 = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,introacno,FName,MAddress,"
                    + " PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst,AppTDS,TDSDocu,IntOpt,JTNAME3,JtName4,"
                    + " IntToAcno, custid1, custid2, custid3, custid4,acctCategory,huf_family) select a.acno, c.custname , a.opermode, a.orgncode," + "'" + UserText + "',now(),'N','',a.introaccno,c.fathername, "
                    + " c.craddress,c.praddress,c.phoneno,c.panno,a.chequebook,a.nomination,a.relatioship,a.minbal,a.JtName1,a.JtName2"
                    + " ,c.grdname,a.instruction,a.tdsflag,''," + "'" + int_option_old + "',a.JtName3,a.JtName4,'', custid1, custid2, custid3, custid4,a.acctCategory,a.huf_family from accountmaster a , "
                    + " customermaster c where a.acno='" + acno + "' and c.custno=substring('" + acno + "',5,6) and c.brncode = '" + currentBrnCode
                    + "' and c. actype = '" + accountCode + "' and c.agcode=substring('" + acno + "',11,2)").executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Updation problem in Account details");
            }
            var1 = em.createNativeQuery("update accountmaster set custname='" + custname + "',"
                    + "introaccno='" + acnoIntro + "',intdeposit=" + rdroi + ",opermode=" + operatingMode + ",jtname1='" + JtName1 + "',"
                    + "jtname2='" + JtName2 + "',jtname3='" + JtName3 + "',jtname4='" + JtName4 + "',orgncode=" + occupation + ","
                    + "nomination='" + nominee + "',lastupdatedt=date_format(now(),'%Y%m%d'),relatioship='" + nominee_rel + "',instruction='" + acctIns
                    + "',minbal=" + minBalOpt + ",rdmatdate='" + MatDt + "',rdinstal=" + rdinstall + ",chequebook=" + chkBookOpt
                    + ",custid1='" + jtName1Code + "',custid2='" + jtName2Code + "',custid3='" + jtName3Code + "',custid4='"
                    + jtName4Code + "',penalty1=" + dmdFlag + ",acctCategory = '" + actCateg + "',tdsflag = '" + tdsApp + "', huf_family = '" + hufFamily + "' where acno='" + acno + "' ").executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Updation problem in accountmaster");
            }

            var1 = em.createNativeQuery("update customermaster set  occupation = '" + occupation + "',enteredby = '" + UserText + "',lastupdatedt = date_format(now(),'%Y%m%d'),grdname='" + grdname + "' where custno = '" + acno.substring(4, 10) + "' "
                    + "and brncode = '" + currentBrnCode + "'and actype = '" + accountCode + "' "
                    + "and agcode = '" + acno.substring(10, 12) + "'").executeUpdate();
            if (var1 <= 0) {
                throw new ApplicationException("Updation problem in customermaster");
            }

            List nomDetails = em.createNativeQuery("select * from nom_details where acno='" + acno + "' ").getResultList();
            if (!nomDetails.isEmpty()) {
                var1 = em.createNativeQuery("update nom_details set nomname='" + nominee + "',nomadd='" + nomineeAdd + "',"
                        + "relation='" + nominee_relatioship + "',nomdob='" + nomineeDate + "',authby='" + UserText + "',"
                        + "trantime=now(),nomage = " + nomage + " where  acno='" + acno + "' ").executeUpdate();
                if (var1 <= 0) {
                    throw new ApplicationException("Updation problem in Nomini details");
                }
            } else {
                if (!nominee.equalsIgnoreCase("") || nominee.length() != 0) {
                    List nomNoList = em.createNativeQuery("SELECT ifnull(max(nom_reg_no),0)+1 from nom_details").getResultList();
                    Vector nomVect = (Vector) nomNoList.get(0);
                    long nomRegNo = Long.parseLong(nomVect.get(0).toString());
                    
                    var1 = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,nomage,enterby,authby,trantime,nom_reg_no)"
                            + "values('" + acno + "','" + nominee + "','" + nomineeAdd + "','" + nominee_relatioship + "','" + minor + "',"
                            + "'" + nomineeDate + "'," + nomage + ",'" + UserText + "','" + UserText + "',now()," + nomRegNo +")").executeUpdate();
                    if (var1 < 0) {
                        throw new ApplicationException("Updation problem in Nomini details");
                    }
                }
            }
            ut.commit();
            return "Data Updated Successfully";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public String introducerAcDetail(String introAcno) throws ApplicationException {
        try {
            String acNat = facadeRemote.getAccountNature(introAcno);

            List acDetailList;
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                acDetailList = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
            } else {
                acDetailList = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
            }
            if (acDetailList.isEmpty()) {
                throw new ApplicationException("This Account number does not exist");
            }
            Vector ele = (Vector) acDetailList.get(0);
            String tmpAcNo = ele.get(0).toString();
            if (ele.get(2).toString().equals("9")) {
                throw new ApplicationException("Account has been Closed");
            }

            return tmpAcNo;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String introducerCity(String recCode, String cityCode) throws ApplicationException {
        List listForIntroCity = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='" + recCode + "'  and ref_code = '" + cityCode + "'").getResultList();
        if (!listForIntroCity.isEmpty()) {
            Vector v5 = (Vector) listForIntroCity.get(0);
            return v5.get(0).toString();
        }
        return "";
    }

    public String dmdAmtFlag() throws ApplicationException {
        List dmdAmtList = em.createNativeQuery("select CODE from cbs_parameterinfo where name = 'DMD_AMOUNT'").getResultList();
        if (!dmdAmtList.isEmpty()) {
            Vector v5 = (Vector) dmdAmtList.get(0);
            return v5.get(0).toString();
        }
        return "";
    }
}
