/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
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
@Stateless(mappedName = "AccountEditCloseFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountEditCloseFacade implements AccountEditCloseFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote facadeRemote;
    @EJB
    AccountOpeningFacadeRemote openingFacadeRemote;
    @EJB
    NpciMgmtFacadeRemote npciRemote;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List codeDesc() throws ApplicationException {
        List codeDescn = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select  code, description  from codebook where groupcode=4 and code<>0 order by code");
            codeDescn = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return codeDescn;
    }

    public List occupationCode() throws ApplicationException {
        List occuCode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select code, description from codebook where groupcode=40 and code<>0 order by code");
            occuCode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return occuCode;
    }

    public List accOpenDate(String branCode) throws ApplicationException {
        List brnCode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select date from bankdays where DayEndFlag = 'N' And Brncode='" + branCode + "'");
            brnCode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brnCode;
    }

    public List docName() throws ApplicationException {
        List docDetails = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select  code, description  from codebook where groupcode=14 and code<>0 order by code");
            docDetails = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return docDetails;
    }

    public String custInfoEditTd(String accNo) throws ApplicationException {
        String result = "";
        String msg = " ";

        try {
            List list1 = em.createNativeQuery("select * from td_accountmaster where acno='" + accNo + "'").getResultList();
            if (list1.isEmpty()) {
                return "This Account No. Does Not Exist.";
            } else {
                List list2 = em.createNativeQuery("select acno from td_accountmaster where acno='" + accNo + "' and accstatus=9").getResultList();

                if (!list2.isEmpty()) {
                    msg = "This Account Has Been Closed.";
                    result = getCustTdDetails(accNo, msg);
                } else {
                    result = getCustTdDetails(accNo, msg);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public String getCustTdDetails(String accNo, String msg) throws ApplicationException {
        String result = "";
        String custId = "";
        String perCity = "";
        String perState = "";
        String perCountry = "";
        String mailCity = "";
        String mailState = "";
        String mailCountry = "";
        String a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y;
        try {
            List custIdList = em.createNativeQuery("select custid from customerid where acno = '" + accNo + "'").getResultList();
            if (custIdList.isEmpty()) {
                result = "Customer Id does not exists corresponding to this Account No.";
                return result;
            } else {
                Vector custIdLst = (Vector) custIdList.get(0);
                custId = custIdLst.get(0).toString();
            }

//            List custDetailValuesLst = em.createNativeQuery("select ifnull(fathername,'')as fathername,ifnull(custname,'') as "
//                    + "custname,ifnull(mailaddressline1,'') as mailaddressline1 ,ifnull(mailaddressline2,'') as mailaddressline2,"
//                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,''),ifnull(mobilenumber,'') as mobilenumber,"
//                    + "ifnull(pan_girnumber,'')as pan_girnumber,ifnull(pervillage,'') as pervillage,ifnull(perblock,'') as perblock,"
//                    + "ifnull(percitycode,'') as percitycode,ifnull(perstatecode,'') as perstatecode,ifnull(perpostalcode,'') as "
//                    + "perpostalcode,ifnull(percountrycode,'') as percountrycode,ifnull(mailvillage,'') as mailvillage,"
//                    + "ifnull(mailblock,'') as mailblock,ifnull(mailcitycode,'') as mailcitycode, ifnull(mailstatecode,'') as "
//                    + "statecode,ifnull(mailpostalcode,'') as mailpostalcode,ifnull(mailcountrycode,'') as mailcountrycode,"
//                    + "ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name from cbs_customer_master_detail  "
//                    + "where customerid='" + custId + "'").getResultList();
            List custDetailValuesLst = em.createNativeQuery("select ifnull(fathername,'')as fathername,ifnull(custname,'') as"
                    + "custname,ifnull(mailaddressline1,'') as mailaddressline1 ,ifnull(mailaddressline2,'') as mailaddressline2,"
                    + "ifnull(peraddressline1,'') as peraddressline1,ifnull(peraddressline2,''),ifnull(mobilenumber,'') as mobilenumber,"
                    + "ifnull(pan_girnumber,'')as pan_girnumber,ifnull(pervillage,'') as pervillage,ifnull(perblock,'') as perblock,"
                    + "ifnull(percitycode,'') as percitycode,ifnull(perstatecode,'') as perstatecode,ifnull(perpostalcode,'') as "
                    + "perpostalcode,ifnull(percountrycode,'') as percountrycode,ifnull(mailvillage,'') as mailvillage,"
                    + "ifnull(mailblock,'') as mailblock,ifnull(mailcitycode,'') as mailcitycode, ifnull(mailstatecode,'') as "
                    + "statecode,ifnull(mailpostalcode,'') as mailpostalcode,ifnull(mailcountrycode,'') as mailcountrycode,"
                    + "ifnull(middle_name,'') as middle_name,ifnull(last_name,'') as last_name,"
                    + "ifnull(FatherMiddleName,'') as father_middle_name,ifnull(FatherLastName,'') as father_last_name from cbs_customer_master_detail  "
                    + "where customerid='" + custId + "'").getResultList();
            if (custDetailValuesLst.isEmpty()) {
                return "No data exists for this account number.";
            } else {
                Vector v3 = (Vector) custDetailValuesLst.get(0);
                if (v3.get(0) == null || v3.get(0).toString().equalsIgnoreCase("")) {
                    a = " ";
                } else {
                    a = v3.get(0).toString();
                    String fMiddleName = v3.get(22).toString();
                    String fLastName = v3.get(23).toString();

                    a = a.trim() + " " + fMiddleName.trim();
                    a = a.trim() + " " + fLastName.trim();
                }
                if (v3.get(1) == null || v3.get(1).toString().equalsIgnoreCase("")) {
                    b = " ";
                } else {
                    b = v3.get(1).toString();

                    String middleName = v3.get(20).toString();
                    String lastName = v3.get(21).toString();

                    b = b.trim() + " " + middleName.trim();
                    b = b.trim() + " " + lastName.trim();
                }
                if ((v3.get(2) == null || v3.get(2).toString().equalsIgnoreCase("")) && (v3.get(3) == null || v3.get(3).toString().equalsIgnoreCase(""))) {
                    c = " ";
                } else {
                    c = v3.get(2).toString() + " " + v3.get(3).toString();
                }
                if (!v3.get(14).toString().equalsIgnoreCase("") || v3.get(14).toString().length() != 0) {
                    c = c + "," + v3.get(14).toString();
                }
                if (!v3.get(15).toString().equalsIgnoreCase("") || v3.get(15).toString().length() != 0) {
                    c = c + "," + v3.get(15).toString();
                }
                if ((!v3.get(16).toString().equalsIgnoreCase("0") || v3.get(16).toString().length() != 0)) {
                    perCity = v3.get(16).toString();
                    if (perCity.equalsIgnoreCase("")) {
                        perCity = "0";
                    }
                    if (!perCity.equalsIgnoreCase("0")) {
                        c = c + "," + introducerCity("001", perCity);
                    }
                }
                if (!v3.get(17).toString().equalsIgnoreCase("0") || v3.get(17).toString().length() != 0) {
                    perState = v3.get(17).toString();
                    if (perState.equalsIgnoreCase("")) {
                        perState = "0";
                    }
                    if (!perState.equalsIgnoreCase("0")) {
                        c = c + "," + introducerCity("002", perState);
                    }
                }
                if (!v3.get(18).toString().equalsIgnoreCase("") || v3.get(18).toString().length() != 0) {
                    c = c + "," + v3.get(18).toString();
                }
                if (!v3.get(19).toString().equalsIgnoreCase("0") || v3.get(19).toString().length() != 0) {
                    perCountry = v3.get(19).toString();
                    if (perCountry.equalsIgnoreCase("")) {
                        perCountry = "0";
                    }
                    if (!perCountry.equalsIgnoreCase("0")) {
                        c = c + "," + introducerCity("003", perCountry);
                    }
                }
                if ((v3.get(4) == null || v3.get(4).toString().equalsIgnoreCase("")) && (v3.get(5) == null || v3.get(5).toString().equalsIgnoreCase(""))) {
                    d = " ";
                } else {
                    d = v3.get(4).toString() + " " + v3.get(5).toString();
                }

                if (!v3.get(8).toString().equalsIgnoreCase("") || v3.get(8).toString().length() != 0) {
                    d = d + "," + v3.get(8).toString();
                }

                if (!v3.get(9).toString().equalsIgnoreCase("") || v3.get(9).toString().length() != 0) {
                    d = d + "," + v3.get(9).toString();
                }

                if (!v3.get(10).toString().equalsIgnoreCase("0") || v3.get(10).toString().length() != 0) {
                    mailCity = v3.get(10).toString();
                    if (mailCity.equalsIgnoreCase("")) {
                        mailCity = "0";
                    }
                    if (!mailCity.equalsIgnoreCase("0")) {
                        d = d + "," + introducerCity("001", mailCity);
                    }
                }

                if (!v3.get(11).toString().equalsIgnoreCase("0") || v3.get(11).toString().length() != 0) {
                    mailState = v3.get(11).toString();
                    if (mailState.equalsIgnoreCase("")) {
                        mailState = "0";
                    }
                    if (!mailState.equalsIgnoreCase("0")) {
                        d = d + "," + introducerCity("002", mailState);
                    }
                }

                if (!v3.get(12).toString().equalsIgnoreCase("") || v3.get(12).toString().length() != 0) {
                    d = d + "," + v3.get(12).toString();
                }

                if (!v3.get(13).toString().equalsIgnoreCase("0") || v3.get(13).toString().length() != 0) {
                    mailCountry = v3.get(13).toString();
                    if (mailCountry.equalsIgnoreCase("")) {
                        mailCountry = "0";
                    }
                    if (!mailCountry.equalsIgnoreCase("0")) {
                        d = d + "," + introducerCity("003", mailCountry);
                    }
                }

                if (v3.get(6) == null || v3.get(6).toString().equalsIgnoreCase("")) {
                    e = " ";
                } else {
                    e = v3.get(6).toString();
                }
                if (v3.get(7) == null || v3.get(7).toString().equalsIgnoreCase("")) {
                    f = " ";
                } else {
                    f = v3.get(7).toString();
                }

            }
            List list3 = em.createNativeQuery("select am.nomination,am.relationship,ifnull(am.jtname1,''),ifnull(am.jtname2,''),ifnull(am.jtname3,''),ifnull(am.jtname4,''),"
                    + "ifnull(am.instruction,'')as instruction,date_format(am.openingdt,'%d/%m/%y') ,cm.grdname,am.introaccno,am.opermode,"
                    + "am.orgncode,am.cust_type,am.tdsflag ,ifnull(am.tdsdetails,'')as tdsdetails,ifnull(am.custid1,''),ifnull(am.custid2,''),"
                    + "ifnull(am.custid3,''),ifnull(am.custid4,''),acctCategory from td_accountmaster am, td_customermaster cm "
                    + "where am.accttype=cm.actype and substring(am.acno,5,6)=cm.custno and am.acno='" + accNo + "'").getResultList();

            if (list3.isEmpty()) {
                return "No data exists for this account number.";
            } else {
                Vector v3 = (Vector) list3.get(0);

                if (v3.get(0) == null || v3.get(0).toString().equalsIgnoreCase("")) {
                    g = " ";
                } else {
                    g = v3.get(0).toString();
                }
                if (v3.get(1) == null || v3.get(1).toString().equalsIgnoreCase("")) {
                    h = " ";
                } else {
                    h = v3.get(1).toString();
                }
                if (v3.get(2) == null || v3.get(2).toString().equalsIgnoreCase("")) {
                    i = " ";
                } else {
                    i = v3.get(2).toString();
                }
                if (v3.get(3) == null || v3.get(3).toString().equalsIgnoreCase("")) {
                    j = " ";
                } else {
                    j = v3.get(3).toString();
                }
                if (v3.get(4) == null || v3.get(4).toString().equalsIgnoreCase("")) {
                    k = " ";
                } else {
                    k = v3.get(4).toString();
                }
                if (v3.get(5) == null || v3.get(5).toString().equalsIgnoreCase("")) {
                    l = " ";
                } else {
                    l = v3.get(5).toString();
                }
                if (v3.get(6) == null || v3.get(6).toString().equalsIgnoreCase("")) {
                    m = " ";
                } else {
                    m = v3.get(6).toString();
                }
                if (v3.get(7) == null || v3.get(7).toString().equalsIgnoreCase("")) {
                    n = " ";
                } else {
                    n = v3.get(7).toString();
                }
                if (v3.get(8) == null || v3.get(8).toString().equalsIgnoreCase("")) {
                    o = " ";
                } else {
                    o = v3.get(8).toString();
                }
                if (v3.get(9) == null || v3.get(9).toString().equalsIgnoreCase("")) {
                    p = " ";
                } else {
                    p = v3.get(9).toString();
                }
                if (v3.get(10) == null || v3.get(10).toString().equalsIgnoreCase("")) {
                    q = " ";
                } else {
                    q = v3.get(10).toString();
                }
                if (v3.get(11) == null || v3.get(11).toString().equalsIgnoreCase("")) {
                    r = " ";
                } else {
                    r = v3.get(11).toString();
                }
                if (v3.get(12) == null || v3.get(12).toString().equalsIgnoreCase("")) {
                    s = " ";
                } else {
                    s = v3.get(12).toString();
                }
                if (v3.get(13) == null || v3.get(13).toString().equalsIgnoreCase("")) {
                    t = " ";
                } else {
                    t = v3.get(13).toString();
                }
                if (v3.get(14) == null || v3.get(14).toString().equalsIgnoreCase("")) {
                    u = " ";
                } else {
                    u = v3.get(14).toString();
                }
                if (v3.get(15) == null || v3.get(15).toString().equalsIgnoreCase("")) {
                    v = " ";
                } else {
                    v = v3.get(15).toString();
                }
                if (v3.get(16) == null || v3.get(16).toString().equalsIgnoreCase("")) {
                    w = " ";
                } else {
                    w = v3.get(16).toString();
                }
                if (v3.get(17) == null || v3.get(17).toString().equalsIgnoreCase("")) {
                    x = " ";
                } else {
                    x = v3.get(17).toString();
                }
                if (v3.get(18) == null || v3.get(18).toString().equalsIgnoreCase("")) {
                    y = " ";
                } else {
                    y = v3.get(18).toString();
                }
                List list4 = em.createNativeQuery("select IntToAcno from td_vouchmst where acno='" + accNo + "' AND  intopt in ('M','Q') AND (INTTOACNO)<>'' AND STATUS='A'").getResultList();

                List list5 = em.createNativeQuery("select ifnull(nomname,''),ifnull(nomadd,''),ifnull(relation,''),ifnull(minior,''),ifnull(date_format(nomdob,'%d/%m/%Y'),'') as nomdob,ifnull(nomage,'') from nom_details where acno='" + accNo + "'").getResultList();

                if (list4.isEmpty()) {
                    if (list5.isEmpty()) {
                        result = a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f + ":" + g + ":" + h + ":" + i + ":" + j + ":" + k + ":" + l + ":" + m + ":" + n + ":" + o + ":" + p + ":" + q + ":" + r + ":" + s + ":" + t + ":" + u + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + v + ":" + w + ":" + x + ":" + y + ":" + v3.get(19) + ":" + msg;
                    } else {
                        Vector v5 = (Vector) list5.get(0);
                        result = a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f + ":" + g + ":" + h + ":" + i + ":" + j + ":" + k + ":" + l + ":" + m + ":" + n + ":" + o + ":" + p + ":" + q + ":" + r + ":" + s + ":" + t + ":" + u + ":" + " " + ":" + v5.get(0).toString() + ":" + v5.get(1).toString() + ":" + v5.get(2).toString() + ":" + v5.get(3).toString() + ":" + v5.get(4).toString() + ":" + v5.get(5).toString() + ":" + v + ":" + w + ":" + x + ":" + y + ":" + v3.get(19) + ":" + msg;
                    }
                } else {
                    Vector v4 = (Vector) list4.get(0);
                    if (list5.isEmpty()) {
                        result = a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f + ":" + g + ":" + h + ":" + i + ":" + j + ":" + k + ":" + l + ":" + m + ":" + n + ":" + o + ":" + p + ":" + q + ":" + r + ":" + s + ":" + t + ":" + u + ":" + v4.get(0).toString() + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + " " + ":" + v + ":" + w + ":" + x + ":" + y + ":" + v3.get(19) + ":" + msg;
                    } else {
                        Vector v5 = (Vector) list5.get(0);
                        result = a + ":" + b + ":" + c + ":" + d + ":" + e + ":" + f + ":" + g + ":" + h + ":" + i + ":" + j + ":" + k + ":" + l + ":" + m + ":" + n + ":" + o + ":" + p + ":" + q + ":" + r + ":" + s + ":" + t + ":" + u + ":" + v4.get(0).toString() + ":" + v5.get(0).toString() + ":" + v5.get(1).toString() + ":" + v5.get(2).toString() + ":" + v5.get(3).toString() + ":" + v5.get(4).toString() + ":" + v5.get(5).toString() + ":" + v + ":" + w + ":" + x + ":" + y + ":" + v3.get(19) + ":" + msg;
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public List getDocDtlDocDescDocCode(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select a.DocuDetails, b.Description, b.Code from documentsreceived a,codebook b where a.GroupDocu='14' and b.groupcode='14' and a.docuno=b.code and a.acno='" + accNo + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String acctEditTdUpdate(String acno, String custName, String craddress, String praddress, String phoneno, String occupation,
            String operatingMode, String document, String documentDetails, String panno, String grdname, String fathername,
            String acnoIntro, String JtName1, String JtName2, String nominee, String nomineeRel, String JtName3, String JtName4,
            String acctIns, String tdsflag, String tdsdetails, String custtype, String receiptno, String acnoInt, String DateText,
            String UserText, String orgBrnCode, String nomiName, String nomiAddress, String nomiRelation, String nomiMinor,
            String nomiDob, String nomiAge, String jtName1Code, String jtName2Code, String jtName3Code,
            String jtName4Code, String autoRenew, String autoPay, String paidAcno, String acctCategry) throws ApplicationException {

        String currentBrnCode = facadeRemote.getCurrentBrnCode(acno);

        String acnoIntOld = "";
        DateText = DateText.substring(6) + DateText.substring(3, 5) + DateText.substring(0, 2);
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date currentDate = new Date();
        try {
            ut.begin();

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

            if (!acnoIntro.equalsIgnoreCase("") && !acnoIntro.equalsIgnoreCase("null")) {
                String resultFunAcNat = facadeRemote.getAccountNature(acnoIntro);
                if (resultFunAcNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || resultFunAcNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List selectFromTdAccMast = em.createNativeQuery("select * from td_accountmaster where acno='" + acnoIntro + "'").getResultList();
                    if (selectFromTdAccMast.isEmpty()) {
                        throw new ApplicationException("This Introducer Account no. does not exist");
                    }
                    selectFromTdAccMast = em.createNativeQuery("select acno from td_accountmaster where acno='" + acnoIntro + "' and accstatus=9").getResultList();
                    if (!selectFromTdAccMast.isEmpty()) {
                        throw new ApplicationException("This Introducer Account no. has been closed");
                    }
                } else {
                    List selectFromAccMast = em.createNativeQuery("select * from accountmaster where acno='" + acnoIntro + "'").getResultList();
                    if (selectFromAccMast.isEmpty()) {
                        throw new ApplicationException("This Introducer Account no. does not exist");
                    }
                    selectFromAccMast = em.createNativeQuery("select acno from accountmaster where acno='" + acnoIntro + "' and accstatus=9").getResultList();
                    if (!selectFromAccMast.isEmpty()) {
                        throw new ApplicationException("This Introducer Account no. has been closed");
                    }
                }
            }

            if (!(receiptno == null || receiptno.equalsIgnoreCase(""))) {
//                List selectInttoAccNo = em.createNativeQuery("select inttoacno from td_vouchmst where acno='" + acno + "' and STATUS='A' and receiptno=" + Float.parseFloat(receiptno) + "").getResultList();
//                Vector vecForInttoAccNo = (Vector) selectInttoAccNo.get(0);
//                acnoIntOld = vecForInttoAccNo.get(0).toString();
//                
//                Query updateTdVouchMst = em.createNativeQuery("update td_vouchmst set inttoacno='" + acnoInt + "' where acno='" + acno + "' and status='A'and intopt in ('M','Q') and inttoacno='" + acnoIntOld + "' and receiptno=" + Float.parseFloat(receiptno) + "");
//                int resultUpdateVouchMst = updateTdVouchMst.executeUpdate();
//                if (resultUpdateVouchMst <= 0) {
//                    throw new ApplicationException("Updation problem in td Vouch Mst.");
//                }

                //Modification On 04/12/2015
                Query updateTdVouchMst = em.createNativeQuery("update td_vouchmst set inttoacno='" + acnoInt + "',"
                        + "autorenew='" + autoRenew + "',auto_pay='" + autoPay + "',auto_paid_acno='" + paidAcno + "' where "
                        + "acno='" + acno + "' and voucherno=" + Float.parseFloat(receiptno) + "");
                int resultUpdateVouchMst = updateTdVouchMst.executeUpdate();
                if (resultUpdateVouchMst <= 0) {
                    throw new ApplicationException("Updation problem in td Vouch Mst.");
                }
            }
            if (!documentDetails.equalsIgnoreCase("")) {
                Query insertIntoDocRcvd = em.createNativeQuery("insert  documentsreceived (acno,groupDocu,DocuNo,DocuDetails,receivedDate) values('" + acno + "',14,'" + document + "','" + documentDetails + "','" + DateText + "')");
                int insertIntoDocRcvdResult = insertIntoDocRcvd.executeUpdate();
                if (insertIntoDocRcvdResult <= 0) {
                    throw new ApplicationException("Insertion Problem in Documents Received");
                }
            }
            Query insertIntoAccEditHistory = em.createNativeQuery("insert into acedithistory (Acno,Name,OperMode,OrgnCode,EnteredBy,UpdateDt,Auth,AuthBy,"
                    + "introacno,FName,MAddress, PAddress,PhNo,PanNo,chBook,Nominee,Relationship,MinBalCharge,JtName1,JtName2,GName,AcInst, AppTDS,TDSDocu,"
                    + "IntOpt,JTNAME3,JtName4,IntToAcno,cust_type,acctCategory)select a.acno, c.custname , a.opermode, a.orgncode, '" + UserText + "',now() ,'N', '', a.introaccno, "
                    + "c.fathername, c.craddress, c.praddress,c.phoneno,c.panno,0,a.nomination,a.relationship,'2',a.jtname1, a.jtname2, c.grdname, "
                    + "a.instruction,tdsflag,tdsdetails,'', a.jtname3, a.jtname4,'" + acnoIntOld + "',a.cust_type,a.acctCategory from td_accountmaster a , td_customermaster c "
                    + "where a.acno='" + acno + "'and c.custno='" + acno.substring(4, 10) + "'and c.brncode = '" + currentBrnCode + "' and substring(a.acno,5,6) = c.custno and a.accttype = c.actype");

            int insertIntoAccEditHistoryResult = insertIntoAccEditHistory.executeUpdate();
            if (insertIntoAccEditHistoryResult <= 0) {
                throw new ApplicationException("Insertion Problem into acedithistory");
            }
            // Comment by Manish Kumar
            Query updateTDAccMast = em.createNativeQuery("update td_accountmaster set custname='" + custName + "', introaccno='" + acnoIntro
                    + "',opermode='" + operatingMode + "',tdsflag='" + tdsflag + "',tdsdetails='" + tdsdetails + "',cust_type='" + custtype
                    + "',jtname1='" + JtName1 + "',jtname2='" + JtName2 + "',jtname3='" + JtName3 + "',jtname4='" + JtName4 + "', orgncode='"
                    + occupation + "', nomination='" + nomiName + "',lastupdatedt=now(),relationship='" + nomiRelation
                    + "',instruction='" + acctIns + "',custid1='" + jtName1Code + "',custid2='" + jtName2Code + "',custid3='" + jtName3Code
                    + "',custid4='" + jtName4Code + "',acctCategory='" + acctCategry + "' where acno='" + acno + "'");
//            Query updateTDAccMast = em.createNativeQuery("update td_accountmaster set custname='" + custName + "', introaccno='" + acnoIntro
//                    + "',opermode='" + operatingMode + "',tdsflag='" + tdsflag + "',tdsdetails='" + tdsdetails + "',cust_type='" + custtype
//                    + "',jtname1='" + JtName1 + "',jtname2='" + JtName2 + "',jtname3='" + JtName3 + "',jtname4='" + JtName4 
//                    +  "', nomination='" + nomiName + "',lastupdatedt=now(),relationship='" + nomiRelation
//                    + "',instruction='" + acctIns + "',custid1='" + jtName1Code + "',custid2='" + jtName2Code + "',custid3='" + jtName3Code
//                    + "',custid4='" + jtName4Code + "',acctCategory='" + acctCategry + "' where acno='" + acno + "'");
            int updateTDAccMastResult = updateTDAccMast.executeUpdate();
            if (updateTDAccMastResult <= 0) {
                throw new ApplicationException("Updation Problem into Td Account Master.");
            }

            Query updateTDCustMast = em.createNativeQuery("update td_customermaster set  occupation = '" + occupation + "',enteredby = '" + UserText + "',"
                    + "lastupdatedt = date_format(now(),'%Y%m%d'),grdname='" + grdname + "' where custno = '" + acno.substring(4, 10) + "' and brncode = '" + currentBrnCode + "'"
                    + "and actype = '" + acno.substring(2, 4) + "' and agcode = '" + acno.substring(10, 12) + "'");
//            Query updateTDCustMast = em.createNativeQuery("update td_customermaster set enteredby = '" + UserText + "',"
//                    + "lastupdatedt = date_format(now(),'%Y%m%d'),grdname='" + grdname + "' where custno = '" + acno.substring(4, 10) + "' and brncode = '" + currentBrnCode + "'"
//                    + "and actype = '" + acno.substring(2, 4) + "' and agcode = '" + acno.substring(10, 12) + "'");
            int updateTDCustMastResult = updateTDCustMast.executeUpdate();
            if (updateTDCustMastResult <= 0) {
                throw new ApplicationException("Updation Problem in td customermaster.");
            }

            List selectFromNomDetails = em.createNativeQuery("select acno,nomname,nomadd,relation,minior,ifnull(nomdob,'19000101'),ifnull(nomage,''),enterby, "
                    + "authby,trantime from nom_details where acno='" + acno + "'").getResultList();
            if (!selectFromNomDetails.isEmpty()) {
                if (receiptno.contains(".")) {
                    receiptno = receiptno.substring(0, receiptno.indexOf("."));
                }
                String date = "";
                Vector vec1 = (Vector) selectFromNomDetails.get(0);
                if (vec1.get(5).toString().equalsIgnoreCase("19000101")) {
                    date = vec1.get(5).toString();
                } else {
                    date = vec1.get(5).toString();
                    date = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
                }

                String currDate = vec1.get(9).toString();

                currDate = currDate.substring(0, 4) + currDate.substring(5, 7) + currDate.substring(8, 10);
                if (receiptno.equalsIgnoreCase("")) {
                    receiptno = "0";
                }
                Query insertIntoNomDetails = em.createNativeQuery("insert into nom_details_his (acno, nomname, nomadd, relation, minior, nomdob, nomage, "
                        + "enterby, authby, trantime, recno)  values('" + vec1.get(0).toString() + "','" + vec1.get(1).toString() + "','" 
                        + vec1.get(2).toString() + "','" + vec1.get(3).toString() + "','" + vec1.get(4).toString() + "','" + date + "','" 
                        + Integer.parseInt(vec1.get(6).toString()) + "','" + vec1.get(7).toString() + "','','" + currDate + "','" + receiptno + "')");
                int insertIntoNomDetailsResult = insertIntoNomDetails.executeUpdate();
                if (insertIntoNomDetailsResult <= 0) {
                    throw new ApplicationException("Insertion Problem in Nominee Details History Table.");
                }
                if (nomiMinor.equalsIgnoreCase("Y")) {
                    Query updateNomDetails = em.createNativeQuery("update nom_details set nomname='" + nomiName + "',nomadd='" + nomiAddress
                            + "',relation='" + nomiRelation + "',minior='" + nomiMinor + "',nomdob='" + nomiDob + "',enterby='" + UserText
                            + "',trantime='" + sdf.format(currentDate) + "' where acno ='" + acno + "'");
                    int updateNomDetailsResult = updateNomDetails.executeUpdate();
                    if (updateNomDetailsResult <= 0) {
                        throw new ApplicationException("Updation Problem in Nominee Details Table.");
                    }
                } else {
                    Query updateNomDetails = em.createNativeQuery("update nom_details set nomname='" + nomiName + "',nomadd='" + nomiAddress
                            + "',relation='" + nomiRelation + "',minior='" + nomiMinor + "',nomage='" + nomiAge + "',enterby='" + UserText
                            + "',trantime='" + sdf.format(currentDate) + "'where acno ='" + acno + "'");
                    int updateNomDetailsResult = updateNomDetails.executeUpdate();
                    if (updateNomDetailsResult <= 0) {
                        throw new ApplicationException("Updation Problem in Nominee Details Table.");
                    }
                }
            } else {
                if (nomiMinor.equalsIgnoreCase("Y")) {
                     List nomNoList = em.createNativeQuery("SELECT ifnull(max(nom_reg_no),0)+1 from nom_details").getResultList();
                    Vector nomVect = (Vector) nomNoList.get(0);
                    long nomRegNo = Long.parseLong(nomVect.get(0).toString());
                
                    Query insertIntoNomDetails2 = em.createNativeQuery("insert into nom_details(acno, nomname, nomadd, relation, minior, nomdob, nomage, "
                            + "enterby, authby, trantime,nom_reg_no) values('" + acno + "','" + nomiName + "','" + nomiAddress + "','" + nomiRelation + "','" 
                            + nomiMinor + "','" + nomiDob + "'," + nomiAge + ",'" + UserText + "','','" + sdf.format(currentDate) + "'," + nomRegNo +")");
                    int insertIntoNomDetails2Result = insertIntoNomDetails2.executeUpdate();
                    if (insertIntoNomDetails2Result <= 0) {
                        throw new ApplicationException("Insertion Problem Into Nominee DEtails Table.");
                    }
                } else {
                    List nomNoList = em.createNativeQuery("SELECT ifnull(max(nom_reg_no),0)+1 from nom_details").getResultList();
                    Vector nomVect = (Vector) nomNoList.get(0);
                    long nomRegNo = Long.parseLong(nomVect.get(0).toString());
                    
                    if (nomiAge.equalsIgnoreCase("")) {
                        nomiAge = "0";
                    }
                    Query insertIntoNomDetails2 = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomage,enterby,authby,"
                            + "trantime,nom_reg_no) values('" + acno + "','" + nomiName + "','" + nomiAddress + "','" + nomiRelation + "','" + nomiMinor + "','" 
                            + nomiAge + "','" + UserText + "','','" + sdf.format(currentDate) + "'," + nomRegNo +")");
                    int insertIntoNomDetails2Result = insertIntoNomDetails2.executeUpdate();
                    if (insertIntoNomDetails2Result <= 0) {
                        throw new ApplicationException("Insertion Problem Into Nominee DEtails Table.");
                    }
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }

        }
    }

    public String functionAcNat(String acctType) throws ApplicationException {
        try {
            List select = em.createNativeQuery("Select AcctNature from accounttypemaster where AcctCode ='" + acctType + "'").getResultList();
            Vector v = (Vector) select.get(0);
            return v.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectReceiptNo(String accNo) throws ApplicationException {
        try {
//            List select = em.createNativeQuery("select receiptno from td_vouchmst where acno='" + accNo + "' AND  intopt in ('M','Q') AND STATUS='A'").getResultList();
            //Modified On 03/12/2015
            List select = em.createNativeQuery("select receiptno,voucherno from td_vouchmst where acno='" + accNo + "' AND STATUS='A'").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectJointName1(String jointName1Code, String orgBrnCode) throws ApplicationException {
        try {
            List select1 = em.createNativeQuery("select ifnull(custname,''),ifnull(middle_name,''),ifnull(last_name,'') from cbs_customer_master_detail where customerid='" + jointName1Code + "'").getResultList();
            return select1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List dateDiffNomDob(String nomDob) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + nomDob + "','" + sdf.format(date) + "')").getResultList();
            return dateDiff;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String actNoValidation(String acno) throws ApplicationException {
        String message = "";
        String acNature = "";
        try {
            int acctStatus = 0;
            int postflag = 0;
            int optStatus = 0;
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatureList = em.createNativeQuery("select Acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (acNatureList.isEmpty()) {
                message = "INVALID ACCOUNT NATURE !!!";
                return message;
            } else {
                Vector acNatureVect = (Vector) acNatureList.get(0);
                acNature = (String) acNatureVect.get(0);
            }
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                List statustList = em.createNativeQuery("SELECT ACCSTATUS FROM td_accountmaster WHERE ACNO='" + acno + "'").getResultList();
                if (statustList.isEmpty()) {
                    message = "ACCOUNT NO. DOES NOT EXISTS !!!";
                    return message;
                } else {
                    Vector status = (Vector) statustList.get(0);
                    acctStatus = Integer.parseInt(status.get(0).toString());
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List postFlagList = em.createNativeQuery("SELECT postflag FROM gltable   WHERE ACNO='" + acno + "'").getResultList();
                if (postFlagList.isEmpty()) {
                    message = "ACCOUNT NO DOES NOT EXISTS IN gltable   !!!";
                    return message;
                } else {
                    Vector postFlag = (Vector) postFlagList.get(0);
                    postflag = Integer.parseInt(postFlag.get(0).toString());
                }
            } else {
                List accStatusList = em.createNativeQuery("SELECT ACCSTATUS FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
                if (accStatusList.isEmpty()) {
                    message = "ACCOUNT NO DOES NOT EXISTS !!!";
                    return message;
                } else {
                    List opStatusList = em.createNativeQuery("SELECT ACCSTATUS,OPTSTATUS FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
                    if (opStatusList.isEmpty()) {
                        message = "ACCOUNT NO DOES NOT EXISTS !!!";
                        return message;
                    } else {
                        Vector ele = (Vector) opStatusList.get(0);
                        acctStatus = Integer.parseInt(ele.get(0).toString());
                        optStatus = Integer.parseInt(ele.get(1).toString());
                    }
                }
            }
            if (!(acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                if (acctStatus == 9) {
                    message = "THIS ACCOUNT HAS BEEN CLOSED !!!";
                    return message;
                } else if (acctStatus == 8) {
                    message = "OPERATION STOPPED FOR THIS ACCOUNT !!!";
                    return message;
                } else if (acctStatus == 7) {
                    message = "WITHDRAWL STOPPED FOR THIS ACCOUNT !!!";
                    return message;
                } else if (acctStatus == 4) {
                    message = "ACCOUNT HAS BEEN FROZEN !!!";
                    return message;
                } else if (acctStatus == 2) {
                    message = "ACCOUNT IS MARKED AS INOPERATIVE !!!";
                    return message;
                }
                if (!(acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    if (optStatus == 2) {
                        message = "ACCOUNT IS MARKED AS INOPERATIVE !!!";
                        return message;
                    }
                }

                if (acctStatus == 1 || acctStatus == 3 || acctStatus == 5 || acctStatus == 6 || acctStatus == 10 || acctStatus == 11 || acctStatus == 12 || acctStatus == 13 || acctStatus == 14) {
                    message = "true";
                    return message;
                }
                if (acctStatus != 1 || acctStatus != 2 || acctStatus != 3 || acctStatus != 4 || acctStatus != 5 || acctStatus != 6 || acctStatus != 7 || acctStatus != 8 || acctStatus != 9 || acctStatus != 10 || acctStatus != 11 || acctStatus != 12 || acctStatus != 13 || acctStatus != 14) {
                    message = "SORRY ACCOUNT STATUS IS INVALID !!!";
                    return message;
                }
            }
            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (postflag == 99) {
                    message = "GL HEAD NOT IN USE !!!";
                    return message;
                } else {
                    message = "true";
                    return message;
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String introducerCity(String recCode, String cityCode) throws ApplicationException {
        try {
            List listForIntroCity = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no='" + recCode + "'  and ref_code = '" + cityCode + "'").getResultList();
            if (!listForIntroCity.isEmpty()) {
                Vector v5 = (Vector) listForIntroCity.get(0);
                return v5.get(0).toString();
            }
            return "";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ********************Methods of
     * AccountCloseRegisterBean*****************************
     */
    public List closedActGridDetail(String brCode) throws ApplicationException {
        List acList = new ArrayList();
        try {
            acList = em.createNativeQuery("SELECT ac.ACNO,ac.CLOSEDBY,'N' FROM acctclose_his ac,accountmaster am  WHERE ac.AUTH <>'Y' AND ac.acno=am.acno and am.curBrcode = '" + brCode + "'"
                    + "union SELECT ac.ACNO,ac.CLOSEDBY,'N' FROM acctclose_his ac,td_accountmaster am  WHERE ac.AUTH <>'Y' AND ac.acno=am.acno and am.curBrcode = '" + brCode + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acList;
    }

    public String closeActCustName(String acno) throws ApplicationException {
        String custName = "";
        String acNat = "";
        try {
            List custNameList = null;
            String accountCode = facadeRemote.getAccountCode(acno);
            List chk2 = em.createNativeQuery("SELECT AcctNature from accounttypemaster where AcctCode = '" + accountCode + "'").getResultList();
            if (!chk2.isEmpty()) {
                Vector ele1 = (Vector) chk2.get(0);
                acNat = ele1.get(0).toString();
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                custNameList = em.createNativeQuery("SELECT CUSTNAME FROM td_accountmaster WHERE ACNO='" + acno + "'").getResultList();
            } else {
                custNameList = em.createNativeQuery("SELECT CUSTNAME FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
            }
            if (!custNameList.isEmpty()) {
                Vector custNameListVec = (Vector) custNameList.get(0);
                custName = custNameListVec.get(0).toString();
            } else {
                custName = "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return custName;
    }

    public String deleteRecord(String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0, var1 = 0, var2 = 0, var3 = 0;
            String acNat = "";
            Integer accStatus = null;
            String accountCode = facadeRemote.getAccountCode(acno);
            List chk1 = em.createNativeQuery("SELECT AcctNature from accounttypemaster where AcctCode = '" + accountCode + "'").getResultList();
            if (!chk1.isEmpty()) {
                Vector ele1 = (Vector) chk1.get(0);
                acNat = ele1.get(0).toString();
            }
            List chk2 = em.createNativeQuery("select acstatus from acctclose_his where acno='" + acno + "'").getResultList();
            if (!chk1.isEmpty()) {
                Vector ele2 = (Vector) chk2.get(0);
                accStatus = Integer.parseInt(ele2.get(0).toString());
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Query updateQuery = em.createNativeQuery("update td_accountmaster set accstatus=" + accStatus + " where acno='" + acno + "'");
                var = updateQuery.executeUpdate();
                Query updateQuery1 = em.createNativeQuery("update td_accountmaster set authby='' where acno='" + acno + "' AND AUTHBY='CLOSED'");
                var1 = updateQuery1.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("update accountmaster set accstatus=" + accStatus + " where acno='" + acno + "'");
                var = updateQuery.executeUpdate();
                Query updateQuery1 = em.createNativeQuery("update accountmaster set authby='' where acno='" + acno + "' AND AUTHBY='CLOSED'");
                var1 = updateQuery1.executeUpdate();
//                Query updateQuery2 = em.createNativeQuery("update loan_mis_details set AUTH='' where acno='" + acno + "'");
//                var2 = updateQuery2.executeUpdate();
            }
            Query deleteQuery = em.createNativeQuery("delete from acctclose_his where acno='" + acno + "'");
            var3 = deleteQuery.executeUpdate();
            //System.out.println("var,var1,var2,var3:=======" + var + "-" + var1 + "-" + var2 + "-" + var3);

            if (var > 0 && var3 > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "RECORD NOT DELETED !!!";
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

    public List custChqDetailForFreshChq(String acno) throws ApplicationException {
        List detail = new ArrayList();
        String acNat = "";
        try {
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                detail = em.createNativeQuery("select cast(chqno as unsigned),coalesce(date_format(issuedt,'%d/%m/%Y'),'')"
                        + " from chbook_sb where acno='" + acno + "'"
                        + " and (statusflag='F')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                detail = em.createNativeQuery("select cast(chqno as unsigned),coalesce(date_format(issuedt,'%d/%m/%Y'),'')"
                        + " from chbook_ca where acno='" + acno + "'"
                        + " and (statusflag='F')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                detail = em.createNativeQuery("select cast(chqno as unsigned),coalesce(date_format(issuedt,'%d/%m/%Y'),'')"
                        + " from chbook_po where acno='" + acno + "'"
                        + " and (statusflag='F')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return detail;
    }

    public List getAcBalance(String acno) throws ApplicationException {
        List balanceList = new ArrayList();
        String acNat = "";
        try {
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM ca_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.TD_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM td_recon WHERE ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM rdrecon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM gl_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM of_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM td_recon WHERE ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM td_recon WHERE ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM ddstransaction WHERE ACNO = '" + acno + "' AND DT <=DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return balanceList;
    }

    public List getTodayTxnAmt(String acno) throws ApplicationException {
        List balanceList = new ArrayList();
        String acNat = "";
        try {
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            }
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM ca_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM rdrecon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM gl_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM of_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM loan_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM td_recon WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d') and Auth='Y'").getResultList();
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                balanceList = em.createNativeQuery("SELECT COALESCE(ROUND((SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0))),2),0) FROM ddstransaction WHERE ACNO = '" + acno + "' AND DT =DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return balanceList;
    }

    public String customerAcNoDetail(String acno) throws ApplicationException {
        String message = "";
        String acNat = "";
        String acctNo = "";
        String custName = "";
        String instruction = "";
        String authBy = "";
        Integer accStatus = null;
        String acStatus = "";
        String acTypeDesc = "";
        Float pendingBal = null;
        Float todayTxnAmt = null;
        Float totalBal = null;
        try {
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            } else {
                message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!";
                return message;
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("select acno,coalesce(Instruction,''),coalesce(AUTHBY,'') from td_accountmaster where acno='" + acno + "'").getResultList();
                if (chk1.isEmpty()) {
                    message = "THIS ACCOUNT NO. DOES NOT EXISTS !!!";
                    return message;
                } else {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        acctNo = ele.get(0).toString();
                        if (ele.get(1).toString() == null || ele.get(1).toString().length() == 0 || ele.get(1).toString().equalsIgnoreCase("")) {
                            instruction = " ";
                        } else {
                            instruction = ele.get(1).toString();
                        }
                        authBy = ele.get(2).toString();
                    }
                }
            } else {
                List chk1 = em.createNativeQuery("select acno,coalesce(Instruction,''),coalesce(AUTHBY,'') from accountmaster where acno='" + acno + "'").getResultList();
                if (chk1.isEmpty()) {
                    message = "THIS ACCOUNT NO. DOES NOT EXISTS !!!";
                    return message;
                } else {
                    for (int i = 0; i < chk1.size(); i++) {
                        Vector ele = (Vector) chk1.get(i);
                        acctNo = ele.get(0).toString();
                        if (ele.get(1).toString() == null || ele.get(1).toString().length() == 0 || ele.get(1).toString().equalsIgnoreCase("")) {
                            instruction = " ";
                        } else {
                            instruction = ele.get(1).toString();
                        }
                        authBy = ele.get(2).toString();
                    }
                }
            }

            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("Select acno,custName From td_accountmaster Where acno='" + acno + "' and accstatus=9").getResultList();
                if (!chk1.isEmpty()) {
                    message = "THIS ACCOUNT HAS BEEN CLOSED !!!";
                    return message;
                } else {
                    List custDtList = em.createNativeQuery("Select acno,custName From td_accountmaster Where acno='" + acno + "'").getResultList();
                    for (int i = 0; i < custDtList.size(); i++) {
                        Vector ele = (Vector) custDtList.get(i);
                        acctNo = ele.get(0).toString();
                        custName = ele.get(1).toString();
                    }
                }
                List deafChk1 = em.createNativeQuery("Select acno,custName From td_accountmaster Where acno='" + acno + "' and accstatus=15").getResultList();
                if (!deafChk1.isEmpty()) {
                    message = "THIS ACCOUNT HAS BEEN ALREADY DEAF MARK, SO IT CAN NOT BE CLOSED !!!";
                    return message;
                }
                List unAuthAcnoList = em.createNativeQuery("select acno from td_accountmaster where acno = '" + acno + "' and (Authby Is Null or authby='') and accstatus<>9").getResultList();
                if (!unAuthAcnoList.isEmpty()) {
                    message = "THIS ACCOUNT AUTHORIZATION IS NOT COMPLETED !!!";
                    return message;
                }

            } else {
                List chk1 = em.createNativeQuery("Select acno,custName From accountmaster Where acno='" + acno + "' and accstatus=9").getResultList();
                if (!chk1.isEmpty()) {
                    message = "THIS ACCOUNT HAS BEEN CLOSED !!!";
                    return message;
                } else {
                    List custDtList = em.createNativeQuery("Select acno,custName From accountmaster Where acno='" + acno + "'").getResultList();
                    for (int i = 0; i < custDtList.size(); i++) {
                        Vector ele = (Vector) custDtList.get(i);
                        acctNo = ele.get(0).toString();
                        custName = ele.get(1).toString();
                    }
                }
                List deafChk1 = em.createNativeQuery("Select acno,custName From accountmaster Where acno='" + acno + "' and accstatus=15").getResultList();
                if (!deafChk1.isEmpty()) {
                    message = "THIS ACCOUNT HAS BEEN ALREADY DEAF MARK, SO IT CAN NOT BE CLOSED !!!";
                    return message;
                }

                List unAuthAcnoList = em.createNativeQuery("select acno from accountmaster where acno = '" + acno + "' and (Authby Is Null or authby='') and accstatus<>9").getResultList();
                if (!unAuthAcnoList.isEmpty()) {
                    message = "THIS ACCOUNT AUTHORIZATION IS NOT COMPLETED !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("select ACCSTATUS FROM td_accountmaster  WHERE ACNO='" + acno + "'").getResultList();
                if (!chk1.isEmpty()) {
                    Vector ele = (Vector) chk1.get(0);
                    accStatus = Integer.parseInt(ele.get(0).toString());
                }
                List chk2 = em.createNativeQuery("SELECT description FROM codebook WHERE CODE=" + accStatus + " AND GROUPCODE=3").getResultList();
                if (!chk2.isEmpty()) {
                    Vector ele = (Vector) chk2.get(0);
                    acStatus = ele.get(0).toString();
                }
            } else {
                List chk1 = em.createNativeQuery("select ACCSTATUS FROM accountmaster  WHERE ACNO='" + acno + "'").getResultList();
                if (!chk1.isEmpty()) {
                    Vector ele = (Vector) chk1.get(0);
                    accStatus = Integer.parseInt(ele.get(0).toString());
                }

                if ((accStatus == 11) || (accStatus == 12) || (accStatus == 13) || (accStatus == 14)) {
                    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
                    List memlist = new ArrayList();
                    memlist = em.createNativeQuery("select ifnull(sum(cramt),0)- ifnull(sum(dramt),0) from npa_recon "
                            + "where acno='" + acno + "' and dt<='" + ymd.format(new Date()) + "' and auth='Y' ").getResultList();
                    Vector memLst = (Vector) memlist.get(0);
                    BigDecimal memBal = new BigDecimal(memLst.get(0).toString());
                    if (!(memBal.compareTo(BigDecimal.ZERO) == 0)) {
                        message = "MEMORANDOM INTEREST EXIST, SO IT CAN NOT BE CLOSED !";
                        return message;
                    }
                }

                List chk2 = em.createNativeQuery("SELECT description FROM codebook WHERE CODE=" + accStatus + " AND GROUPCODE=3").getResultList();
                if (!chk2.isEmpty()) {
                    Vector ele = (Vector) chk2.get(0);
                    acStatus = ele.get(0).toString();
                }
            }
            List acTypeList = em.createNativeQuery("SELECT ACCTDESC FROM accounttypemaster WHERE ACCTCODE='" + accountCode + "'").getResultList();
            if (!acTypeList.isEmpty()) {
                Vector ele = (Vector) acTypeList.get(0);
                acTypeDesc = ele.get(0).toString();
            }
            List pendingBalList = em.createNativeQuery("select TxnInstAmt from clg_ow_shadowbal where acno='" + acno + "'").getResultList();
            if (!pendingBalList.isEmpty()) {
                Vector ele = (Vector) pendingBalList.get(0);
                pendingBal = Float.parseFloat(ele.get(0).toString());
            } else {
                pendingBal = 0.0F;
            }
            List availBalList = getAcBalance(acno);
            if (!availBalList.isEmpty()) {
                Vector ele = (Vector) availBalList.get(0);
                totalBal = Float.parseFloat(ele.get(0).toString());
            }

            List todayTxnAmtList = getTodayTxnAmt(acno);
            if (!todayTxnAmtList.isEmpty()) {
                Vector ele = (Vector) todayTxnAmtList.get(0);
                todayTxnAmt = Float.parseFloat(ele.get(0).toString());
            }

            message = acctNo + ":" + custName + ":" + acTypeDesc + ":" + acStatus + ":" + todayTxnAmt.toString() + ":" + totalBal.toString() + ":" + pendingBal.toString() + ":" + instruction + ":" + acNat;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String closeAccountProcedure(String acno, String authBy, String dateText, String brCode) throws ApplicationException {
        String message = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0, var1 = 0, var2 = 0, var3 = 0, var4 = 0;
            String acNat = "";
            Integer accStatus = null;
            String tempbd = "";
            Float instNo = null;
            String date = null;
            String accountCode = facadeRemote.getAccountCode(acno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            if (!acNatList.isEmpty()) {
                Vector recLst = (Vector) acNatList.get(0);
                acNat = recLst.get(0).toString();
            } else {
                ut.rollback();
                message = "ACCOUNT NATURE NOT FOUND FOR THIS ACCOUNT NO. !!!";
                return message;
            }
            List tempBdList = em.createNativeQuery("SELECT DATE_FORMAT(date,'%Y-%m-%d') FROM bankdays WHERE DAYENDFLAG='N' AND brncode = '" + brCode + "'").getResultList();
            if (!tempBdList.isEmpty()) {
                Vector recLst = (Vector) tempBdList.get(0);
                tempbd = recLst.get(0).toString();
            } else {
                ut.rollback();
                message = "SERVER DATE NOT FOUND. !!!";
                return message;
            }
            List diffList = em.createNativeQuery("SELECT TIMESTAMPDIFF(day,'" + tempbd + "',curdate())").getResultList();
            if (!diffList.isEmpty()) {
                Vector recLst = (Vector) diffList.get(0);
                if (Integer.parseInt(recLst.get(0).toString()) != 0) {
                    ut.rollback();
                    message = "SERVER DATE IS NOT CURRENT DATE , PLEASE CHECK SERVER DATE !!!";
                    return message;
                }
            }

            if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.SS_AC) || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List chk1 = em.createNativeQuery("select acno from emidetails where acno='" + acno + "' and status='Unpaid'").getResultList();
                if (!chk1.isEmpty()) {
                    ut.rollback();
                    message = "PLEASE CHECK UNPAID INSTALLMENT !!!";
                    return message;
                }
                List chk2 = em.createNativeQuery("select ACNO from loansecurity where acno='" + acno + "' and status='Active'").getResultList();
                if (!chk2.isEmpty()) {
                    ut.rollback();
                    message = "SECURITIES ARE STILL ACTIVE !!!";
                    return message;
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk1 = em.createNativeQuery("select acno from td_vouchmst where acno='" + acno + "' and status='A'").getResultList();
                if (!chk1.isEmpty()) {
                    ut.rollback();
                    message = "PLEASE CHECK TERM DEPOSITE LIST !!!";
                    return message;
                }
            }
            List statusList = em.createNativeQuery("Select acno From accountstatus Where acno='" + acno + "' And auth = 'N'").getResultList();
            if (!statusList.isEmpty()) {
                ut.rollback();
                message = "Account Status Authorization is pending !!!";
                return message;
            }
            List chk1 = em.createNativeQuery("Select acno,IntToAcno From td_vouchmst Where IntToAcno='" + acno + "' And Status <> 'C'").getResultList();
            if (!chk1.isEmpty()) {
                ut.rollback();
                message = "A/C ASSOCIATED AS (INTEREST TO A/C) WITH TERM DEPOSITE !!!";
                return message;
            }
            List chk2 = em.createNativeQuery("select acno from lockeracmaster where acno='" + acno + "'").getResultList();
            if (!chk2.isEmpty()) {
                ut.rollback();
                message = "PLEASE SURRENDER THE LOCKER FIRST !!!";
                return message;
            }
            List chk3 = em.createNativeQuery("select acno From standins_generalmaster where acno='" + acno + "'").getResultList();
            if (!chk3.isEmpty()) {
                ut.rollback();
                message = "PLEASE CANCEL STANDING INSTRUCTION FIRST !!!";
                return message;
            }

            List chktran = em.createNativeQuery("Select FROMacno From standins_transmaster Where (FromAcno='" + acno + "') or (ToAcno='" + acno + "')").getResultList();
            if (!chktran.isEmpty()) {
                ut.rollback();
                message = "PLEASE CANCEL STANDING INSTRUCTION FIRST !!!";
                return message;
            }

            Query deleteQuery = em.createNativeQuery("delete From standins_transpending Where (FromAcno='" + acno + "') or (ToAcno='" + acno + "')");
            var4 = deleteQuery.executeUpdate();

            List chk4 = em.createNativeQuery("select  TxnInstNo from clg_in_entry where acno='" + acno + "'").getResultList();
            if (!chk4.isEmpty()) {
                Vector recLst = (Vector) chk4.get(0);
                instNo = Float.parseFloat(recLst.get(0).toString());
                ut.rollback();
                message = "INSTRUMENT NO." + instNo + " ALREADY EXISTS IN INWARD CLEARING !!!";
                return message;
            }
            List chk5 = em.createNativeQuery("select  TxnInstNo from clg_ow_2day where acno='" + acno + "'").getResultList();
            if (!chk5.isEmpty()) {
                Vector recLst = (Vector) chk5.get(0);
                instNo = Float.parseFloat(recLst.get(0).toString());
                ut.rollback();
                message = "INSTRUMENT NO." + instNo + " ALREADY EXISTS IN O/W CLEARING !!!";
                return message;
            }
            List chk6 = em.createNativeQuery("select  TxnInstNo from clg_ow_entry where acno='" + acno + "'").getResultList();
            if (!chk6.isEmpty()) {
                Vector recLst = (Vector) chk6.get(0);
                instNo = Float.parseFloat(recLst.get(0).toString());
                ut.rollback();
                message = "INSTRUMENT NO." + instNo + " ALREADY EXISTS IN O/W CLEARING !!!";
                return message;
            }
            List chk7 = em.createNativeQuery("select  TxnInstNo from clg_ow_shadowbal where acno='" + acno + "'").getResultList();
            if (!chk7.isEmpty()) {
                Vector recLst = (Vector) chk7.get(0);
                instNo = Float.parseFloat(recLst.get(0).toString());
                ut.rollback();
                message = "INSTRUMENT NO." + instNo + " ALREADY EXISTS IN O/W CLEARING !!!";
                return message;
            }
            List chk8 = em.createNativeQuery("select  TxnInstNo from clg_localchq where acno='" + acno + "'").getResultList();
            if (!chk8.isEmpty()) {
                Vector recLst = (Vector) chk8.get(0);
                instNo = Float.parseFloat(recLst.get(0).toString());
                ut.rollback();
                message = "INSTRUMENT NO." + instNo + " ALREADY EXISTS IN O/W CLEARING !!!";
                return message;
            }

            List chk91 = em.createNativeQuery("select acno from tokentable_credit where acno='" + acno + "' and auth ='N'").getResultList();
            if (!chk91.isEmpty()) {
                ut.rollback();
                message = "There is some Cash Credit Pending transactions";
                return message;
            }
            
            List chk92 = em.createNativeQuery("select acno from recon_trf_d where acno='" + acno + "' and auth ='N'").getResultList();
            if (!chk92.isEmpty()) {
                ut.rollback();
                message = "There is some Transfer Pending transactions";
                return message;
            }
            //Code Comment As Per Discussion

//            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                List chk9 = em.createNativeQuery("select acno from chbook_sb Where upper(statusflag)='S' and acno='" + acno + "'").getResultList();
//                if (!chk9.isEmpty()) {
//                    ut.rollback();
//                    message = "PLEASE CHECK STOP PAYMENT DETAILS !!!";
//                    return message;
//                }
//            }
//            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                List chk9 = em.createNativeQuery("select acno from chbook_ca Where upper(statusflag)='S' and acno='" + acno + "'").getResultList();
//                if (!chk9.isEmpty()) {
//                    ut.rollback();
//                    message = "PLEASE CHECK STOP PAYMENT DETAILS !!!";
//                    return message;
//                }
//            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk9 = em.createNativeQuery("select Acno from td_recon where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                List chk9 = em.createNativeQuery("select Acno from recon where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List chk9 = em.createNativeQuery("select Acno from ca_recon where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List chk9 = em.createNativeQuery("select Acno from rdrecon where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List chk9 = em.createNativeQuery("select Acno from loan_recon where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                List chk9 = em.createNativeQuery("select Acno from ddstransaction where (auth='N' or auth is null) and acno='" + acno + "'").getResultList();
                if (!chk9.isEmpty()) {
                    ut.rollback();
                    message = "CHECK UNAUTHORIZED TRANSACTION IN ACCOUNT !!!";
                    return message;
                }
            }
            List chk10 = em.createNativeQuery("select acno from studpledger where acno='" + acno + "'").getResultList();
            if (!chk10.isEmpty()) {
                ut.rollback();
                message = "CLOSING NOT ALLOWED !!!";
                return message;
            }
            List chk11 = em.createNativeQuery("select ACNO from bill_obcbooking where acno='" + acno + "'").getResultList();
            if (!chk11.isEmpty()) {
                ut.rollback();
                message = "PLEASE CHECK OBC BOOKED TRANSACTIONS !!!";
                return message;
            }
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk12 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from of_recon where tdacno='" + acno + "'").getResultList();
                Vector chk12Vect = (Vector) chk12.get(0);
                if (Float.parseFloat(chk12Vect.get(0).toString()) != 0) {
                    ut.rollback();
                    message = "BALANCE OUTSTANDING IN ODFDR ACCOUNT !!!";
                    return message;
                }
            }

            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List chk12 = em.createNativeQuery("select accstatus from td_accountmaster where acno='" + acno + "'").getResultList();
                if (!chk12.isEmpty()) {
                    Vector recLst = (Vector) chk12.get(0);
                    accStatus = Integer.parseInt(recLst.get(0).toString());
//                    Query updateQuery = em.createNativeQuery("update td_accountmaster set accstatus=9,AUTHBY='" + authBy + "' where acno='" + acno + "'");
                    Query updateQuery = em.createNativeQuery("update td_accountmaster set accstatus=9 where acno='" + acno + "'");
                    var = updateQuery.executeUpdate();
                }
            } else {
                List chk12 = em.createNativeQuery("select accstatus from accountmaster where acno='" + acno + "'").getResultList();
                if (!chk12.isEmpty()) {
                    Vector recLst = (Vector) chk12.get(0);
                    accStatus = Integer.parseInt(recLst.get(0).toString());
                    //Query updateQuery = em.createNativeQuery("update accountmaster set accstatus=9,AUTHBY='" + authBy + "' where acno='" + acno + "'");
                    Query updateQuery = em.createNativeQuery("update accountmaster set accstatus=9 where acno='" + acno + "'");
                    var = updateQuery.executeUpdate();
                }
            }

            List smsChqList = em.createNativeQuery("select acno,bill_deduction_acno from mb_subscriber_tab where "
                    + "(acno='" + acno + "' or bill_deduction_acno='" + acno + "') and status = '1' and auth = 'Y' "
                    + "and auth_status = 'V'").getResultList();
            if (!smsChqList.isEmpty()) {
                closingSmsRegistration(smsChqList, acno);
            }

            smsChqList = em.createNativeQuery("select cbs_umrn from cbs_mandate_detail where (creditor_acno='" + acno + "' or "
                    + "debtor_acno='" + acno + "') and flag<>'C'").getResultList();
            if (!smsChqList.isEmpty()) {
                closeMandateRegistration(smsChqList);
            }

            String autoPayMsg = chkAutoPaidAcnoInTd(acno);
            if (!autoPayMsg.equals("")) {
                throw new ApplicationException("This a/c is registered as auto payment a/c in td for a/c-->" + autoPayMsg + " which is not close.");
            }

            Query insertQuery = em.createNativeQuery("Insert into acctclose_his(acno,Closedby,Acstatus,Closingdate,auth,authby) values('" + acno + "','" + authBy + "'," + accStatus + ",'" + dateText + "','N','" + authBy + "')");
            var2 = insertQuery.executeUpdate();
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                if (var > 0 && var2 > 0) {
                    ut.commit();
                    message = "true";
                    return message;
                } else {
                    ut.rollback();
                    message = "ACCOUNT NOT CLOSED !!!";
                    return message;
                }
            } else {
                if (var > 0 && var2 > 0) {
                    //Aadhar Deactivation.
                    String acctNature = facadeRemote.getAccountNature(acno);
                    if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List list = em.createNativeQuery("select customerid,ifnull(aadhaar_no,'') as aadharNo,"
                                + "ifnull(aadhaar_lpg_acno,'') as aadharAcno from cbs_customer_master_detail cu,"
                                + "customerid id where cu.customerid = id.custid and id.acno = '" + acno + "'").getResultList();
                        if (list.isEmpty()) {
                            throw new ApplicationException("Account detail is not found");
                        }
                        Vector ele = (Vector) list.get(0);
                        String custid = ele.get(0).toString();
                        String aadharNo = ele.get(1).toString();
                        String aadharMapAcno = ele.get(2).toString();

                        list = em.createNativeQuery("select cust_id from cbs_aadhar_registration where reg_type='AD' and "
                                + "status='R' and cust_id='" + custid + "' and aadhar_no='" + aadharNo + "'").getResultList();
                        if (!list.isEmpty() && aadharMapAcno.equalsIgnoreCase(acno)) {
                            npciRemote.aadharDeactivation(custid, aadharNo, "CA", "I", "AD", authBy);
                        }
                    }
                    //End Here
                    ut.commit();
                    message = "true";
                    return message;
                } else {
                    ut.rollback();
                    message = "ACCOUNT NOT CLOSED !!!";
                    return message;
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

    public String closingSmsRegistration(List smsChqList, String closingAc) throws ApplicationException {
        try {
            int code = facadeRemote.getCodeForReportName("SMS-CLOSE-CHARGE");
            if (smsChqList.size() == 1) {
                Vector ele = (Vector) smsChqList.get(0);
                String registeredAccount = ele.get(0).toString().trim();
                String chargedAccount = ele.get(1).toString().trim();
                if (registeredAccount.equalsIgnoreCase(chargedAccount)) {
                    if (code == 0) {
                        int n = em.createNativeQuery("update mb_subscriber_tab set status='9' where acno = '" + closingAc + "'").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Problem in A/c status updation in sms table.");
                        }
                    } else if (code == 1) {
                        List msgStatusList = em.createNativeQuery("select acno from mb_push_msg_tab where message_status=2 and acno='" + closingAc + "'").getResultList();
                        if (!msgStatusList.isEmpty()) {
                            throw new ApplicationException("SMS Charge Post First !");
                        } else {
                            int n = em.createNativeQuery("update mb_subscriber_tab set status='9' where acno='" + closingAc + "'").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem in A/c status updation in sms table.");
                            }
                        }
                    }
                } else {
                    if (closingAc.equalsIgnoreCase(registeredAccount)) {
                        if (code == 0) {
                            int n = em.createNativeQuery("update mb_subscriber_tab set status='9' where acno = '" + closingAc + "'").executeUpdate();
                            if (n <= 0) {
                                throw new ApplicationException("Problem in A/c status updation in sms table.");
                            }
                        } else if (code == 1) {
                            List msgStatusList = em.createNativeQuery("select acno from mb_push_msg_tab where message_status=2 and acno='" + closingAc + "'").getResultList();
                            if (!msgStatusList.isEmpty()) {
                                throw new ApplicationException("SMS Charge Post First !");
                            } else {
                                int n = em.createNativeQuery("update mb_subscriber_tab set status='9' where acno='" + closingAc + "'").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Problem in A/c status updation in sms table.");
                                }
                            }
                        }
                    } else if (closingAc.equalsIgnoreCase(chargedAccount)) {
                        throw new ApplicationException("This is the billing A/c no in SMS for A/c No " + registeredAccount + ". So please remove it first.");
                    }
                }
            } else if (smsChqList.size() > 1) {
                String accounts = "";
                for (int i = 0; i < smsChqList.size(); i++) {
                    Vector ele = (Vector) smsChqList.get(i);
                    String regAccount = ele.get(0).toString().trim();
                    if (accounts.equals("")) {
                        accounts = regAccount;
                    } else {
                        accounts = accounts.trim() + "," + regAccount;
                    }
                }
                throw new ApplicationException("This is the billing A/c no in SMS for A/c Nos " + accounts + ". So please remove it first.");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String closeMandateRegistration(List smsChqList) throws ApplicationException {
        try {
            String cbsUmrns = "";
            for (int i = 0; i < smsChqList.size(); i++) {
                Vector ele = (Vector) smsChqList.get(i);
                if (cbsUmrns.equals("")) {
                    cbsUmrns = "'" + ele.get(0).toString().trim() + "'";
                } else {
                    cbsUmrns = cbsUmrns + ",'" + ele.get(0).toString().trim() + "'";
                }
            }
            if (!cbsUmrns.equals("")) {
                int n = em.createNativeQuery("update cbs_mandate_detail set flag='C' where "
                        + "cbs_umrn in(" + cbsUmrns + ")").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("There is problem in mandate close updation.");
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public List lockerCkeck(String acno) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select acno from lockeracmaster where acno='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List instructionCkeck(String acno) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select acno From standins_generalmaster where acno='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List instructionCkeck1(String acno) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("Select FROMacno From standins_transmaster Where (FromAcno='" + acno + "') or (ToAcno='" + acno + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List emiCkeck(String acno) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select acno From emidetails where acno='" + acno + "' and STATUS='UNPAID'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List securityCkeck(String acno) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select acno From loansecurity where acno='" + acno + "' and Status='ACTIVE'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public String dayDiffIntPost(String acno) throws ApplicationException {
        try {
            List listForInt = em.createNativeQuery("select TIMESTAMPDIFF(DAY,int_calc_upto_dt,now()) from cbs_loan_acc_mast_sec where acno ='" + acno + "'").getResultList();
            if (!listForInt.isEmpty()) {
                Vector v5 = (Vector) listForInt.get(0);
                return v5.get(0).toString();
            }
            return "0";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String chkAutoPaidAcnoInTd(String acno) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno from td_vouchmst where "
                    + "auto_paid_acno='" + acno + "' and status='A'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                return ele.get(0).toString().trim();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "";
    }

    public List retrieveReceiptDetails(String acno, Double receiptNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(inttoacno,''),intopt,ifnull(autorenew,''),"
                    + "ifnull(auto_pay,''),ifnull(auto_paid_acno,'') from td_vouchmst "
                    + "where acno='" + acno + "' and voucherno=" + receiptNo + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String chargeAmt() throws ApplicationException {
        int servTaxApplyCode = 0, roundUpTo = 0;
        double totalRot = 0, closingCharge = 0, partySerCharge = 0;
        try {
            List chgList = em.createNativeQuery("SELECT charges from parameterinfo_miscincome where purpose ='ACCOUNT-CLOSURE-CHARGES' and effectivedt = (select max(effectivedt) from parameterinfo_miscincome where purpose = 'ACCOUNT-CLOSURE-CHARGES')").getResultList();
            if (!chgList.isEmpty()) {
                Vector v5 = (Vector) chgList.get(0);
                closingCharge = Double.parseDouble(v5.get(0).toString());
                //return v5.get(0).toString();
                servTaxApplyCode = facadeRemote.getCodeForReportName("STAXMODULE_ACTIVE");
                totalRot = 0;
                if (servTaxApplyCode == 1) {
                    Map<String, Double> slabMap = interFts.getTaxComponentSlab(ymd.format(new Date()));
                    Set<Map.Entry<String, Double>> set = slabMap.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        //desp = keyArray[0];
                        roundUpTo = Integer.parseInt(keyArray[3]);
                        totalRot += Double.parseDouble(entry.getValue().toString());
                    }
                }
                partySerCharge = CbsUtil.round(((closingCharge * totalRot) / 100), roundUpTo);
                // totalCharge = closingCharge + partySerCharge;
            }
            return String.valueOf(closingCharge) + ":" + String.valueOf(partySerCharge);
            //return "0";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
