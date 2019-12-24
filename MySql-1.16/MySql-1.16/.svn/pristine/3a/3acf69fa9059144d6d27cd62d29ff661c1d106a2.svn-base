/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.admin;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ankit Verma
 */
@Stateless(mappedName = "FdDdsAccountOpeningFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class FdDdsAccountOpeningFacade implements FdDdsAccountOpeningFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    AccountOpeningFacadeRemote openingFacadeRemote;

    public String saveAccountOpenFd(String custType, String custIdPage, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, String remark, String cust_nature, String tds_flag, String tds_details,
            String nomname, String nomadd, String relation, String minor, String nomdob, Integer nomage, String custId1, String custId2, String custId3, String custId4, String actCategory) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String alphacode = null;
        String date = null;
        Integer custIdNew = null;
        try {
            ut.begin();

            List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custIdPage + "'").getResultList();
            Vector cuLst = (Vector) custList.get(0);
            String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
            if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Customer Verification is not completed.";
            }

            String cFlag = openingFacadeRemote.custMergedFlag(custIdPage);
            if (cFlag.equalsIgnoreCase("false")) {
                ut.rollback();
                return "Customer Id Is Merged.";
            }

            if (!((custId1 == null) || (custId1.equalsIgnoreCase("")))) {
                String cFlag1 = openingFacadeRemote.custMergedFlag(custId1);
                if (cFlag1.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId1 + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder1 Customer id " + custId1 + " Verification is not completed.";
                }
            }

            if (!((custId2 == null) || (custId2.equalsIgnoreCase("")))) {
                String cFlag2 = openingFacadeRemote.custMergedFlag(custId2);
                if (cFlag2.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId2 + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder2 Customer id " + custId2 + " Verification is not completed.";
                }
            }

            if (!((custId3 == null) || (custId3.equalsIgnoreCase("")))) {
                String cFlag3 = openingFacadeRemote.custMergedFlag(custId3);
                if (cFlag3.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder3 Customer Id Is Merged.";
                }

                List custList3 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId3 + "'").getResultList();
                Vector cuLst3 = (Vector) custList3.get(0);
                String cu_id3 = cuLst3.get(0).toString() != null ? cuLst3.get(0).toString() : "";
                if ((cu_id3.equalsIgnoreCase("N")) || (cu_id3 == null) || (cu_id3.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder3 Customer id " + custId3 + " Verification is not completed.";
                }
            }

            if (!((custId4 == null) || (custId4.equalsIgnoreCase("")))) {
                String cFlag4 = openingFacadeRemote.custMergedFlag(custId4);
                if (cFlag4.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder4 Customer Id Is Merged.";
                }

                List custList4 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId4 + "'").getResultList();
                Vector cuLst4 = (Vector) custList4.get(0);
                String cu_id4 = cuLst4.get(0).toString() != null ? cuLst4.get(0).toString() : "";
                if ((cu_id4.equalsIgnoreCase("N")) || (cu_id4 == null) || (cu_id4.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder4 Customer id " + custId4 + " Verification is not completed.";
                }
            }

            List dateList = em.createNativeQuery("select date_format('" + DateText + "', '%Y-%m-%d')").getResultList();
            if (dateList.size() > 0) {
                Vector descVect = (Vector) dateList.get(0);
                date = descVect.get(0).toString();
            }
            List branchmasterList = em.createNativeQuery("select  alphacode from branchmaster where brncode='" + orgncode + "'").getResultList();
            if (branchmasterList.size() > 0) {
                Vector descVect = (Vector) branchmasterList.get(0);
                alphacode = descVect.get(0).toString();
            }
            String custId = openingFacadeRemote.cbsCustId(actype, orgncode);
            if (custId.length() == 1) {
                custId = "00000" + custId;
            }
            if (custId.length() == 2) {
                custId = "0000" + custId;
            }
            if (custId.length() == 3) {
                custId = "000" + custId;
            }
            if (custId.length() == 4) {
                custId = "00" + custId;
            }
            if (custId.length() == 5) {
                custId = "0" + custId;
            }

            String acno = orgncode + actype + custId + "01";

            Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO) values ('" + acno + "','" + acno + "')");
            insertMapping.executeUpdate();

            if (!nomname.equalsIgnoreCase("") || nomname.length() != 0) {
                
                List nomNoList = em.createNativeQuery("SELECT ifnull(max(nom_reg_no),0)+1 from nom_details").getResultList();
                Vector nomVect = (Vector) nomNoList.get(0);
                long nomRegNo = Long.parseLong(nomVect.get(0).toString());
                
                Integer nomDetail = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,nomage,enterby,authby,"
                        + "trantime,nom_reg_no) values('" + acno + "','" + nomname + "','" + nomadd + "','" + relation + "','" + minor + "','" 
                        + nomdob + "'," + nomage + ",'" + UserText + "','" + UserText + "','" + DateText + "'," + nomRegNo +")").executeUpdate();
                if (nomDetail < 0) {
                    ut.rollback();
                    return "Data is not inserted into Nom Detail";
                }
            }
            Integer customerMaster = em.createNativeQuery("insert into td_customermaster(custno,actype,title,custname,"
                    + "craddress,praddress,phoneno,dob,occupation,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,"
                    + "fathername,remarks,brncode)values('" + custId + "','" + actype + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
                    + "'" + phoneno + "','" + dob + "','" + occupation + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
                    + "'" + DateText + "','" + agcode + "','" + UserText + "','" + fathername + "','" + remark + "',"
                    + "'" + orgncode + "')").executeUpdate();
//            Integer customerMaster = em.createNativeQuery("insert into td_customermaster(custno,actype,title,custname,"
//                    + "craddress,praddress,phoneno,dob,status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,"
//                    + "fathername,remarks,brncode)values('" + custId + "','" + actype + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
//                    + "'" + phoneno + "','" + dob + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
//                    + "'" + DateText + "','" + agcode + "','" + UserText + "','" + fathername + "','" + remark + "',"
//                    + "'" + orgncode + "')").executeUpdate();
            if (customerMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Master";
            }

            Integer accountMaster = em.createNativeQuery("insert td_accountmaster(acno,openingdt,introaccno,opermode,JtName1,"
                    + "JtName2,Relationship,remarks,accstatus,orgncode,nomination,enteredby,lastupdatedt,accttype,JtName3,JtName4,"
                    + "tdsDetails,tdsFlag,cust_Type,custName,closingdate,custid1,custid2,custid3,custid4,CurBrCode,acctCategory)values('" + acno + "','" + DateText + "','" + acnoIntro + "',"
                    + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "','" + nomineerelatioship + "','" + remark + "',1,"
                    + "'" + occupation + "','" + nominee + "','" + UserText + "','" + date + "','" + actype + "' ,'" + JtName3 + "',"
                    + "'" + JtName4 + "','" + tds_details + "','" + tds_flag + "','" + cust_nature + "','" + custname + "',null,"
                    + "'" + custId1 + "','" + custId2 + "','" + custId3 + "','" + custId4 + "','" + orgncode + "','" + actCategory + "') ").executeUpdate();
//            Integer accountMaster = em.createNativeQuery("insert td_accountmaster(acno,openingdt,introaccno,opermode,JtName1,"
//                    + "JtName2,Relationship,remarks,accstatus,nomination,enteredby,lastupdatedt,accttype,JtName3,JtName4,"
//                    + "tdsDetails,tdsFlag,cust_Type,custName,closingdate,custid1,custid2,custid3,custid4,CurBrCode,acctCategory)values('" + acno + "','" + DateText + "','" + acnoIntro + "',"
//                    + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "','" + nomineerelatioship + "','" + remark + "',1,"
//                    + "'" + nominee + "','" + UserText + "','" + date + "','" + actype + "' ,'" + JtName3 + "',"
//                    + "'" + JtName4 + "','" + tds_details + "','" + tds_flag + "','" + cust_nature + "','" + custname + "',null,"
//                    + "'" + custId1 + "','" + custId2 + "','" + custId3 + "','" + custId4 + "','" + orgncode + "','" + actCategory + "') ").executeUpdate();
            if (accountMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Account Master";
            }

            Integer reconbalan = em.createNativeQuery("insert into td_reconbalan(acno,balance,dt)values('" + acno + "',0,'" + date + "')").executeUpdate();
            if (reconbalan < 0) {
                ut.rollback();
                return "Data is not inserted into Reconbalan";
            }
            Integer documentsReceived = em.createNativeQuery("insert documentsreceived (acno,groupdocu,docuno,docudetails,receiveddate) values('" + acno + "',14," + docuno + ",'" + docudetails + "','" + DateText + "')").executeUpdate();
            if (documentsReceived < 0) {
                ut.rollback();
                return "Data is not inserted into Documents Received";
            }

            /**
             * ********* Commented By Shipra **********
             */
//            List customerIdList = em.createNativeQuery("SELECT * FROM customerid Where CustId = '" + custIdPage + "'").getResultList();
//            if (customerIdList.size() > 0) {
//                custType = "2";
//            } else {
//                custType = "1";
//            }
//            if (custType.equalsIgnoreCase("1")) {
//                List customerId = em.createNativeQuery("select (max(custid)+1) from customerid").getResultList();
//                if (customerId.size() > 0) {
//                    Vector customerIdVect = (Vector) customerId.get(0);
//                    custIdNew = Integer.parseInt(customerIdVect.get(0).toString());
//                }
//            } else {
//                custIdNew = Integer.parseInt(custIdPage);
//            }
//           /*********** End Comment ***********/
            Integer customer = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn) values"
                    + "(" + custIdPage + ",'" + acno + "','" + UserText + "','" + alphacode + "')").executeUpdate();
            if (customer < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Id ";
            }
            ut.commit();
            return "true" + acno + custIdNew;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ***************Methods of
     * DDSAccountOpeningRegisterBean**************************
     */
    public List getAcctTypeIntro(String brcode) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Agcode,name from ddsagent where status = 'A' AND brncode = '" + brcode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String saveAccountOpenDDS(String custType, String custIdPage, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, float rdperiod, float rdInstallment, float roi,
            String nomname, String nomadd, String relation, String minor, String nomdob, Integer nomage, String custId1, String custId2, String custId3, String custId4, String actCateg) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String alphacode = null;
        String date = null;
        //  Integer custIdNew = null;
        //String rdMatDate = "";
        String rdMatDate = "", tdsFlag = "Y";
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ut.begin();

            List custList = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custIdPage + "'").getResultList();
            Vector cuLst = (Vector) custList.get(0);
            String cu_id = cuLst.get(0).toString() != null ? cuLst.get(0).toString() : "";
            if ((cu_id.equalsIgnoreCase("N")) || (cu_id == null) || (cu_id.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Customer Verification is not completed.";
            }

            String cFlag = openingFacadeRemote.custMergedFlag(custIdPage);
            if (cFlag.equalsIgnoreCase("false")) {
                ut.rollback();
                return "Customer Id Is Merged.";
            }

            if (!((custId1 == null) || (custId1.equalsIgnoreCase("")))) {
                String cFlag1 = openingFacadeRemote.custMergedFlag(custId1);
                if (cFlag1.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder1 Customer Id Is Merged.";
                }

                List custList1 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId1 + "'").getResultList();
                Vector cuLst1 = (Vector) custList1.get(0);
                String cu_id1 = cuLst1.get(0).toString() != null ? cuLst1.get(0).toString() : "";
                if ((cu_id1.equalsIgnoreCase("N")) || (cu_id1 == null) || (cu_id1.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder1 Customer id " + custId1 + " Verification is not completed.";
                }
            }

            if (!((custId2 == null) || (custId2.equalsIgnoreCase("")))) {
                String cFlag2 = openingFacadeRemote.custMergedFlag(custId2);
                if (cFlag2.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder2 Customer Id Is Merged.";
                }

                List custList2 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId2 + "'").getResultList();
                Vector cuLst2 = (Vector) custList2.get(0);
                String cu_id2 = cuLst2.get(0).toString() != null ? cuLst2.get(0).toString() : "";
                if ((cu_id2.equalsIgnoreCase("N")) || (cu_id2 == null) || (cu_id2.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder2 Customer id " + custId2 + " Verification is not completed.";
                }
            }

            if (!((custId3 == null) || (custId3.equalsIgnoreCase("")))) {
                String cFlag3 = openingFacadeRemote.custMergedFlag(custId3);
                if (cFlag3.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder3 Customer Id Is Merged.";
                }

                List custList3 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId3 + "'").getResultList();
                Vector cuLst3 = (Vector) custList3.get(0);
                String cu_id3 = cuLst3.get(0).toString() != null ? cuLst3.get(0).toString() : "";
                if ((cu_id3.equalsIgnoreCase("N")) || (cu_id3 == null) || (cu_id3.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder3 Customer id " + custId3 + " Verification is not completed.";
                }
            }

            if (!((custId4 == null) || (custId4.equalsIgnoreCase("")))) {
                String cFlag4 = openingFacadeRemote.custMergedFlag(custId4);
                if (cFlag4.equalsIgnoreCase("false")) {
                    ut.rollback();
                    return "Joint Holder4 Customer Id Is Merged.";
                }

                List custList4 = em.createNativeQuery("select ifnull(auth,'') from cbs_customer_master_detail where customerid ='" + custId4 + "'").getResultList();
                Vector cuLst4 = (Vector) custList4.get(0);
                String cu_id4 = cuLst4.get(0).toString() != null ? cuLst4.get(0).toString() : "";
                if ((cu_id4.equalsIgnoreCase("N")) || (cu_id4 == null) || (cu_id4.equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Joint Holder4 Customer id " + custId4 + " Verification is not completed.";
                }
            }

            List dateList = em.createNativeQuery("select date_format('" + DateText + "','%Y%m%d')").getResultList();
            if (dateList.size() > 0) {
                Vector descVect = (Vector) dateList.get(0);
                date = descVect.get(0).toString();
                Date d = ymd.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int mnth = (int) rdperiod;
                int days = (int) ((rdperiod - mnth) * 30);
                cal.add(Calendar.MONTH, mnth);
                cal.add(Calendar.DATE, days);
                d = cal.getTime();
                rdMatDate = sdf.format(d);
            }

            List branchmasterList = em.createNativeQuery("select  alphacode from branchmaster where brncode='" + orgncode + "'").getResultList();
            if (branchmasterList.size() > 0) {
                Vector descVect = (Vector) branchmasterList.get(0);
                alphacode = descVect.get(0).toString();
            }
            String custId = cbsGetCustIdForDds(actype, orgncode, agcode);
            if (custId.length() == 1) {
                custId = "00000" + custId;
            }
            if (custId.length() == 2) {
                custId = "0000" + custId;
            }
            if (custId.length() == 3) {
                custId = "000" + custId;
            }
            if (custId.length() == 4) {
                custId = "00" + custId;
            }
            if (custId.length() == 5) {
                custId = "0" + custId;
            }


            String acno = orgncode + actype + custId + agcode;

            Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                    + "values ('" + acno + "','" + acno + "')");
            insertMapping.executeUpdate();

            if (!nomname.equalsIgnoreCase("") || nomname.length() != 0) {
                Integer nomDetail = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,"
                        + "nomage,enterby,authby,trantime)values('" + acno + "','" + nomname + "','" + nomadd + "','" + relation + "','" + minor + "',"
                        + "'" + nomdob + "'," + nomage + ",'" + UserText + "','" + UserText + "','" + DateText + "')").executeUpdate();
                if (nomDetail < 0) {
                    ut.rollback();
                    return "Data is not inserted into Nom Detail";
                }
            }
            Integer customerMaster = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,"
                    + "status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                    + "values('" + custId + "','" + actype + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
                    + "'" + phoneno + "','" + dob + "','" + occupation + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
                    + "'" + DateText + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')").executeUpdate();
//            Integer customerMaster = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,"
//                    + "status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
//                    + "values('" + custId + "','" + actype + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
//                    + "'" + phoneno + "','" + dob + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
//                    + "'" + DateText + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')").executeUpdate();
            if (customerMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Master";
            }

            String docMsg = openingFacadeRemote.getCustAcTdsDocDtl(custIdPage, "", "C");
            if (docMsg.equalsIgnoreCase("true")) {
                tdsFlag = "N";

                List cList = em.createNativeQuery("select distinct doc_details,fyear,uniqueIdentificationNo from tds_docdetail where customerid = '" + custIdPage + " ' ").getResultList();
                Vector custLst = (Vector) cList.get(0);
                String docDtl = custLst.get(0).toString();
                String fYear = custLst.get(1).toString();
                String uin = custLst.get(2).toString();

                List sList = em.createNativeQuery("select coalesce(max(seqno),0)+1 from tds_docdetail where customerid = '" + custIdPage + " '").getResultList();
                Vector seqLst = (Vector) sList.get(0);
                String secListed = seqLst.get(0).toString();
                int secnum = Integer.parseInt(secListed);

                Query insertDocQuery = em.createNativeQuery("insert into tds_docdetail(customerid,acno,seqNo,submission_date,"
                        + "fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,uniqueIdentificationNo)"
                        + "values ('" + custIdPage + "','" + acno + "'," + secnum + ",now()," + fYear + ",'','" + docDtl + "','Y','" + orgncode + "',now(),'" + UserText + "','N','" + uin + "')");
                int var = insertDocQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Inserted");
                }
            }

            Integer accountMaster = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,"
                    + "JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,creationdt,lastupdatedt,"
                    + "accttype,relatioship,JtName3,JtName4,Custname,intDeposit,custid1,custid2,custid3,custid4,CurBrCode,tdsFlag,acctCategory)"
                    + "values('" + acno + "','" + date + "','" + date + "','" + acnoIntro + "'," + rdInstallment + ",'" + rdMatDate + "',0,"
                    + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "',1,'" + occupation + "','" + nominee + "',0,1,0,"
                    + "'" + UserText + "','" + date + "','" + date + "','" + actype + "' ,'" + nomineerelatioship + "',"
                    + "'" + JtName3 + "','" + JtName4 + "','" + custname + "'," + roi + ","
                    + "'" + custId1 + "','" + custId2 + "','" + custId3 + "','" + custId4 + "','" + orgncode + "','" + tdsFlag + "','" + actCateg + "') ").executeUpdate();
//            Integer accountMaster = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,"
//                    + "JtName2,accstatus,nomination,ODLimit,minbal,penalty,enteredby,creationdt,lastupdatedt,"
//                    + "accttype,relatioship,JtName3,JtName4,Custname,intDeposit,custid1,custid2,custid3,custid4,CurBrCode,tdsFlag,acctCategory)"
//                    + "values('" + acno + "','" + date + "','" + date + "','" + acnoIntro + "'," + rdInstallment + ",'" + rdMatDate + "',0,"
//                    + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "',1,'" + nominee + "',0,1,0,"
//                    + "'" + UserText + "','" + date + "','" + date + "','" + actype + "' ,'" + nomineerelatioship + "',"
//                    + "'" + JtName3 + "','" + JtName4 + "','" + custname + "'," + roi + ","
//                    + "'" + custId1 + "','" + custId2 + "','" + custId3 + "','" + custId4 + "','" + orgncode + "','" + tdsFlag + "','" + actCateg + "') ").executeUpdate();
            if (accountMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Account Master";
            }

            Integer reconbalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + acno + "',0,'" + date + "')").executeUpdate();
            if (reconbalan < 0) {
                ut.rollback();
                return "Data is not inserted into Reconbalan";
            }
            Integer documentsReceived = em.createNativeQuery("insert documentsreceived (acno,groupdocu,docuno,docudetails,receiveddate) values('" + acno + "',14," + docuno + ",'" + docudetails + "','" + date + "')").executeUpdate();
            if (documentsReceived < 0) {
                ut.rollback();
                return "Data is not inserted into Documents Received";
            }
            Integer customer = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn) values"
                    + "('" + custIdPage + "','" + acno + "','" + UserText + "','" + alphacode + "')").executeUpdate();
            if (customer < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Id ";
            }

            /**
             * **** FOR DDS INTEREST POSTING DATA NEEDED IN
             * cbs_loan_acc_mast_sec TABLE *****
             */
            Integer loanAccMst = em.createNativeQuery("insert into cbs_loan_acc_mast_sec(ACNO,SCHEME_CODE,"
                    + "INTEREST_TABLE_CODE,MORATORIUM_PD,ACC_PREF_DR,ACC_PREF_CR,RATE_CODE,DISB_TYPE,SB_CA_DETAIN_IN_BANK,"
                    + "CALC_METHOD,CALC_ON,INT_APP_FREQ,CALC_LEVEL,COMPOUND_FREQ,PEGG_FREQ,LOAN_PD_MONTH,LOAN_PD_DAY,INT_CALC_UPTO_DT,INT_COMP_TILL_DT,NEXT_INT_CALC_DT) "
                    + "values('" + acno + "','','',0,0,0,'','','','','','','','',0,0,0,'" + date + "','" + date + "','" + date + "')").executeUpdate();
            if (loanAccMst < 0) {
                ut.rollback();
                return "Data is not inserted into cbs_loan_acc_mast_sec";
            }
            ut.commit();
            return "true" + acno;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private String cbsGetCustIdForDds(String acctType, String brcode, String agCode) throws ApplicationException {

        Integer custno = 0;
        Integer custOpt = 0;
        Integer custOpt1 = 0;
        try {
            List branchmasterList = em.createNativeQuery("Select * From parameterinfo where UPPER(Acc_Seq)='I'").getResultList();
            if (branchmasterList.size() > 0) {
                List customerMasterList = em.createNativeQuery("select coalesce(max(custno),'000000') From customermaster where actype='" + acctType + "' AND brncode = '" + brcode + "' and agcode= '" + agCode + "'").getResultList();
                if (customerMasterList.size() > 0) {
                    Vector customerMasterListVect = (Vector) customerMasterList.get(0);
                    custno = Integer.parseInt(customerMasterListVect.get(0).toString()) + 1;
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

    public List getROIForDDS(double months, double amt, String acTp) throws ApplicationException {
        List resultlist = null;
        try {
            //resultlist = em.createNativeQuery("select interest_rate from ddsint_slab WHERE " + months + " >=fromperiod and " + months + " <= toperiod and applicable_Date =(select max(Applicable_Date) from ddsint_slab)").getResultList();
            resultlist = em.createNativeQuery("select roi from dds_slab where (" + months + " between from_mon and to_mon) and (" + amt + " "
                    + " between from_amt and to_amt) and actype ='" + acTp + "' and applicable_date = (select max(applicable_date) from "
                    + " dds_slab where actype ='" + acTp + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public String introducerAcDetailForDDS(String introAcno) throws ApplicationException {
        String message = "";
        String tmpAcNo = "";
        String custName = "";
        String acSt = "";
        String acNat = "";
        try {
            if (introAcno != null && introAcno.length() == 12) {
                List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=(select accttype from accountmaster where acno='" + introAcno + "')").getResultList();
                if (!acNatList.isEmpty()) {
                    Vector ele = (Vector) acNatList.get(0);
                    acNat = ele.get(0).toString();
                    List accountMasterList = new ArrayList();
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        accountMasterList = em.createNativeQuery("select acno, custname, accstatus from td_accountmaster where acno='" + introAcno + "'").getResultList();
                    } else {
                        accountMasterList = em.createNativeQuery("select acno, custname, accstatus from accountmaster where acno='" + introAcno + "'").getResultList();
                    }
                    if (!accountMasterList.isEmpty() && accountMasterList.size() == 1) {
                        Vector accountVector = (Vector) accountMasterList.get(0);
                        tmpAcNo = accountVector.get(0).toString();
                        custName = accountVector.get(1).toString();
                        int acctStatus = Integer.parseInt(accountVector.get(2).toString());
                        List chk3 = em.createNativeQuery("select description from codebook where groupcode=3 and code= " + acctStatus + "").getResultList();
                        if (!chk3.isEmpty()) {
                            Vector ele1 = (Vector) chk3.get(0);
                            acSt = ele1.get(0).toString();
                        }
                        message = "true" + tmpAcNo + ":" + custName + ":" + acSt;
                    } else {
                        message = "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                        return message;
                    }
                } else {
                    return "THIS ACCOUNT NO DOES NOT EXISTS !!!";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return message;
    }

    /**
     * ***********************Methods of
     * KCCAcctOpenBean*************************************
     */
    public List getAccountTypeNatureByKC() throws ApplicationException {
        List accountTypeList = new ArrayList();
        try {
            accountTypeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature='" + CbsConstant.CURRENT_AC + "' AND PRODUCTCODE='KC'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accountTypeList;
    }

    public List getOccupationDetails() throws ApplicationException {
        List occupationList = new ArrayList();
        try {
            occupationList = em.createNativeQuery("select description from codebook where groupcode=6 and code<>0 order by code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return occupationList;
    }

    public List getOperationMode() throws ApplicationException {
        List operationModeList = new ArrayList();
        try {
            operationModeList = em.createNativeQuery("select description  from codebook where groupcode=4 and code<>0 order by groupcode,code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return operationModeList;
    }

    public List getSecurity() throws ApplicationException {
        List securityList = new ArrayList();
        try {
            securityList = em.createNativeQuery("select description from loan_codebook where substring(cast(groupcode as char(8)),1,3)='910' and code<>0 order by groupcode,code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return securityList;
    }

    public List getbsrVillage() throws ApplicationException {
        List bsrVillageList = new ArrayList();
        try {
            bsrVillageList = em.createNativeQuery("select description from bsr_village").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return bsrVillageList;
    }

    public String saveData(String TxtRemarks, String acType, String occupation, String titalName, String TxtName, String txtadd, String village, String state, String phoneNo, String dob, String fathersName, String roi, String sancAmount, String sancDate, String accOpenDate, String education, String TxtRabiLimit, String TxtKharifLimit, String TxtAgriInc, String TxtOthInc, String CmbFarmer, String CmbPriSec1, String CmbPriSec2, String CmbColSec1, String CmbColSec2, List holdingGrid, List MHLiab, List MHMAssets, List MHIMAssets, String user, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String txnbrn = null;
            String custno = null;
            String fullAccNo;
            String OPERMODE = null;
            String Tempbd = null;

            if (titalName.equals("0")) {
                titalName = "Mr.";
            } else if (titalName.equals("1")) {
                titalName = "Mrs.";
            } else if (titalName.equals("2")) {
                titalName = "Master";
            } else if (titalName.equals("3")) {
                titalName = "Kumari";
            } else if (titalName.equals("4")) {
                titalName = "M/s";
            } else {
                titalName = "Miss";
            }

            if (CmbFarmer.equals("0")) {
                CmbFarmer = "Medium";
            } else if (CmbFarmer.equals("1")) {
                CmbFarmer = "Small";
            } else {
                CmbFarmer = "Large";
            }

            if (education.equals("0")) {
                education = "UP TO HSC";
            } else if (education.equals("1")) {
                education = "GRADUATE";
            } else if (education.equals("2")) {
                education = "POST-GRADUATE";
            } else if (education.equals("3")) {
                education = "PROFESSIONAL";
            } else if (education.equals("4")) {
                education = "OTHERS";
            } else {
                education = "N/A";
            }

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            if (!tempBd.isEmpty()) {
                Vector tempCurrent = (Vector) tempBd.get(0);
                Tempbd = tempCurrent.get(0).toString();
            }
            if ((TxtRemarks == null) || (TxtRemarks.equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Enter Remarks";
            }
            List secList = em.createNativeQuery("select alphacode from branchmaster").getResultList();
            if (!secList.isEmpty()) {
                Vector secLists = (Vector) secList.get(0);
                txnbrn = secLists.get(0).toString();
            }
            List custNoList = em.createNativeQuery("select ifnull(max(custno),'')+1 from customermaster where actype='" + acType + "'").getResultList();
            if (!custNoList.isEmpty()) {
                Vector custNoLists = (Vector) custNoList.get(0);
                custno = custNoLists.get(0).toString();
                int length = custno.length();
                int addZero = 6 - length;
                for (int i = 1; i <= addZero; i++) {
                    custno = "0" + custno;
                }
            } else {
                custno = "000001";
            }
            fullAccNo = brCode + acType + custno + "01";
            Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                    + "values ('" + fullAccNo + "','" + fullAccNo + "')");
            insertMapping.executeUpdate();

            List codeList = em.createNativeQuery("select ifnull(code,'1') from codebook where groupcode=6 and description='" + occupation + "'").getResultList();
            if (!codeList.isEmpty()) {
                Vector codeLists = (Vector) codeList.get(0);
                OPERMODE = codeLists.get(0).toString();
            }

            String crAddress = txtadd + "-" + village + "-" + state;
            Query insertQuery = em.createNativeQuery("insert into customermaster(custno,title,actype,custname,craddress,praddress,phoneno,dob,occupation,LastUpdateDt,AgCode,EnteredBy,fathername,brncode)"
                    + "values (" + "'" + custno + "'"
                    + "," + "'" + titalName + "'"
                    + "," + "'" + acType + "'"
                    + "," + "'" + TxtName + "'"
                    + "," + "'" + crAddress + "'"
                    + "," + "'" + txtadd + "'"
                    + "," + "'" + phoneNo + "'"
                    + "," + "'" + dob + "'"
                    + "," + Integer.parseInt(OPERMODE)
                    + "," + "'" + Tempbd + "'"
                    + "," + "'" + 01 + "'"
                    + "," + "'" + user + "'"
                    + "," + "'" + fathersName + "'"
                    + "," + "'" + brCode + "'"
                    + ")");
            int varA = insertQuery.executeUpdate();
            float calRoi = Float.parseFloat(roi) / 12;
            Query insertQuery1 = em.createNativeQuery("insert into accountmaster(Acno,OpeningDt,intdeposit,ClosingBal,opermode,AccStatus,instruction,orgncode,ODLimit,EnteredBy,CreationDt,LastUpdateDt,AcctType,MinBal,CustName,chequebook,optstatus,penalty,IntroAccno,CurBrCode)"
                    + "values (" + "'" + fullAccNo + "'"
                    + "," + "'" + Tempbd + "'"
                    + "," + Float.parseFloat(roi)
                    + "," + 0
                    + "," + Integer.parseInt(OPERMODE)
                    + "," + 1
                    + "," + "'" + TxtRemarks + "'"
                    + "," + Integer.parseInt(OPERMODE)
                    + "," + Float.parseFloat(sancAmount)
                    + "," + "'" + user + "'"
                    + "," + "'" + Tempbd + "'"
                    + "," + "'" + Tempbd + "'"
                    + "," + "'" + acType + "'"
                    + "," + 0
                    + "," + "'" + TxtName + "'"
                    + "," + 1
                    + "," + 1
                    + "," + calRoi
                    + "," + "''"
                    + ",'" + brCode + "')");
            int varB = insertQuery1.executeUpdate();

            Query insertQuery2 = em.createNativeQuery("insert into ca_reconbalan(Acno,dt,Balance)"
                    + "values (" + "'" + fullAccNo + "'" + "," + "'" + Tempbd + "'" + "," + 0 + ")");
            int varC = insertQuery2.executeUpdate();

            String CustID = null;
            List custIdList = em.createNativeQuery("Select (ifnull(max(CustID),0)+1) From customerid").getResultList();
            if (!custIdList.isEmpty()) {
                Vector custIdLists = (Vector) custIdList.get(0);
                CustID = custIdLists.get(0).toString();
            }
            Query insertQuery3 = em.createNativeQuery("insert into customerid(CustId,Acno,enterby,trantime,txnbrn)"
                    + "values (" + custno + ","
                    + "'" + fullAccNo + "'"
                    + "," + "'" + user + "'"
                    + "," + "'" + Tempbd + "'"
                    + "," + "'" + txnbrn + "'"
                    + ")");
            int varD = insertQuery3.executeUpdate();

            Query insertQuery4 = em.createNativeQuery("insert into loan_appparameter(brcode,Acno,CustName,AcctNature,ROI,PenalROI,Sanctionlimit,Sanctionlimitdt, ODLimit,MaxLimit,PresentStatus,EnterBy,closingDt,product,int_opt)"
                    + "values (" + "'" + brCode + "'"
                    + "," + "'" + fullAccNo + "'"
                    + "," + "'" + TxtName + "'"
                    + "," + "'" + acType + "'"
                    + "," + Float.parseFloat(roi)
                    + "," + calRoi
                    + "," + Float.parseFloat(sancAmount)
                    + "," + "'" + sancDate + "'"
                    + "," + Float.parseFloat(sancAmount)
                    + "," + Float.parseFloat(sancAmount)
                    + "," + "'OPERATIVE'"
                    + "," + "'" + user.toUpperCase() + "'"
                    + "," + "''"
                    + "," + "'S'"
                    + "," + "'S'"
                    + ")");
            int varE = insertQuery4.executeUpdate();

//            List loanList = em.createNativeQuery("Select * from loan_mis_details where Acno ='" + fullAccNo + "'").getResultList();
//            if (loanList.isEmpty()) {
//                Query InsertQuery5 = em.createNativeQuery("insert into loan_mis_details(AcNo,Classification,Sector,GovtScheme,IndustrialExp,LoanType,AppCategory,CategoryOpt,NonPrioritySec,Relation,SanctionAuth,SensitiveSector,EducationIn,LastBalConfirmDt,LoanDuration,Margin,NetWorth,DocDt,DocExpDt,SecurityType,EnterBy,Auth,AuthBy,ActivityCode,SpProgCode,VillageCode,DistrictCode,PopulationCode,ActivityCode1,Xtra1,remarks,purpose) (SELECT Max('" + fullAccNo + "') AcNo,Max(CASE WHEN GC BETWEEN 100000 AND 109999 THEN VAL ELSE ''END) CLASSIFICATION,Max(CASE WHEN GC BETWEEN 200000 AND 209999 THEN VAL ELSE ''END) Sectors,Max(CASE WHEN GC BETWEEN 300000 AND 309999 THEN VAL ELSE ''END) Schemes,Max(CASE WHEN GC BETWEEN 800000 AND 809999 THEN VAL ELSE ''END) IndExp,Max(CASE WHEN GC BETWEEN 400000 AND 409999 THEN VAL ELSE ''END) TypeOfLoan,Max('GENERAL') AppCategory,Max(CASE WHEN GC BETWEEN 600000 AND 609999 THEN VAL ELSE ''END) CatOPT,Max(CASE WHEN GC BETWEEN 210000 AND 219999 THEN VAL ELSE ''END) NonPrioritySec,Max(CASE WHEN GC BETWEEN 700000 AND 709999 THEN VAL ELSE ''END) Relation,Max(CASE WHEN GC BETWEEN 900000 AND 909999 THEN VAL ELSE ''END) SanAuth,Max(CASE WHEN GC BETWEEN 500000 AND 509999 THEN VAL ELSE ''END) SSector,MAX('INDIA') EducationIn,Max(CURDATE()) LastBalConfirmDt,Max(12) LoanDuration,Max(0) Margin,Max(0) NetWorth,Max(CURDATE()) DocDt,Max(Date_Add(CURDATE(), INTERVAL 3 YEAR)) DocExpDt,Max('PLEDGE') SecurityType,Max('" + user + "') EnterBy,Max('N') Auth,Max('') AuthBy,Max(0) ActivityCode,Max(0) SpProgCode,Max(0) VillageCode,Max(0) DistrictCode,Max(0) PopCode,Max(0) ActivityCode1,Max('920000/6') Xtra1,Max('')remarks,Max('930000/1')purpose From (SELECT Max(GROUPCODE),CAST(Max(GROUPCODE) AS CHAR(6))+'/'+ CAST(DEF AS CHAR(6)) Val FROM loan_defaults Where AccountType = Substring('" + fullAccNo + "',3,2) GROUP BY SUBSTRING(CAST(GROUPCODE AS CHAR(6)),1,2),DEF)Def(GC,Val))");
//                int varF = InsertQuery5.executeUpdate();
//            }

            Query insertQuery6 = em.createNativeQuery("insert INTO kcc_master(acno,openingdt,closingdt,name,fname, address, village,state,dob,phone,occupation,education,opermode, sancamt,sancdt,rabilimit,khariflimit,roi,agriincome,othincome,farmertype,prisecurity1,prisecurity2,colsecurity1,colsecurity2,enterby,authby,trantime,lastupdateddt)"
                    + "values (" + "'" + fullAccNo + "'"
                    + "," + "'" + accOpenDate + "'"
                    + "," + "''"
                    + "," + "'" + TxtName + "'"
                    + "," + "'" + fathersName + "'"
                    + "," + "'" + txtadd + "'"
                    + "," + "'" + village + "'"
                    + "," + "'" + state + "'"
                    + "," + "'" + dob + "'"
                    + "," + "'" + phoneNo + "'"
                    + "," + Integer.parseInt(OPERMODE)
                    + "," + "'" + education + "'"
                    + "," + Integer.parseInt(OPERMODE)
                    + "," + Float.parseFloat(sancAmount)
                    + "," + "'" + sancDate + "'"
                    + "," + Float.parseFloat(TxtRabiLimit)
                    + "," + Float.parseFloat(TxtKharifLimit)
                    + "," + Float.parseFloat(roi)
                    + "," + Float.parseFloat(TxtAgriInc)
                    + "," + Float.parseFloat(TxtOthInc)
                    + "," + "'" + CmbFarmer + "'"
                    + "," + "'" + CmbPriSec1 + "'"
                    + "," + "'" + CmbPriSec2 + "'"
                    + "," + "'" + CmbColSec1 + "'"
                    + "," + "'" + CmbColSec2 + "'"
                    + "," + "'" + user.toUpperCase() + "'"
                    + "," + "'" + user.toUpperCase() + "'"
                    + "," + "'" + Tempbd + "'"
                    + "," + "'" + Tempbd + "'"
                    + ")");
            int varG = insertQuery6.executeUpdate();
            if (holdingGrid.size() > 20) {
                for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6, h = 7, i = 8, j = 9; a < holdingGrid.size(); a = a + 10, b = b + 10, c = c + 10, d = d + 10, e = e + 10, f = f + 10, g = g + 10, h = h + 10, i = i + 10, j = j + 10) {
                    String seqNo = (holdingGrid.get(a).toString());
                    String villages = (holdingGrid.get(b).toString());
                    float area = Float.parseFloat(holdingGrid.get(c).toString());
                    String blockNo = (holdingGrid.get(d).toString());
                    float owned = Float.parseFloat(holdingGrid.get(e).toString());
                    float leased = Float.parseFloat(holdingGrid.get(f).toString());
                    float cropper = Float.parseFloat(holdingGrid.get(g).toString());
                    float irrigated = Float.parseFloat(holdingGrid.get(h).toString());
                    String sourceIrrigation = holdingGrid.get(i).toString();
                    String encumreance = (holdingGrid.get(j).toString());

                    float nonIrrigated = area - irrigated;
                    Query insertQuery7 = em.createNativeQuery("insert into kcc_landdetails(acno,village,surveyno,owned,leased,sharecropper,area,irrigated,nonirrgated,source,encumb,enterby,authby,trantime,lastupdateddt)"
                            + "values (" + "'" + fullAccNo + "'"
                            + "," + "'" + villages + "'"
                            + "," + "'" + blockNo + "'"
                            + "," + owned
                            + "," + leased
                            + "," + cropper
                            + "," + area
                            + "," + irrigated
                            + "," + nonIrrigated
                            + "," + "'" + sourceIrrigation + "'"
                            + "," + "'" + encumreance + "'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + Tempbd + "'"
                            + "," + "'" + Tempbd + "'"
                            + ")");
                    int varH = insertQuery7.executeUpdate();
                }
            } else {
                ut.rollback();
                return "Please Fill The Land Details";
            }
            if (MHLiab.size() > 14) {
                for (int a = 0, b = 1, c = 2, d = 3, e = 4, f = 5, g = 6; a < MHLiab.size(); a = a + 7, b = b + 7, c = c + 7, d = d + 7, e = e + 7, f = f + 7, g = g + 7) {
                    String seqNo = (MHLiab.get(a).toString());
                    String institutions = MHLiab.get(b).toString();
                    String purpose = MHLiab.get(c).toString();
                    float osAmount = Float.parseFloat(MHLiab.get(d).toString());
                    float overDue = Float.parseFloat(MHLiab.get(e).toString());
                    String security = MHLiab.get(f).toString();
                    float instAmount = Float.parseFloat(MHLiab.get(g).toString());

                    Query insertQuery8 = em.createNativeQuery("insert into kcc_liabdetails(Acno,instituation,purpose,osamt,overdue,security,yearlyinstallamt,enterby,authby,trantime,lastupdateddt)"
                            + "values (" + "'" + fullAccNo + "'"
                            + "," + "'" + institutions + "'"
                            + "," + "'" + purpose + "'"
                            + "," + osAmount
                            + "," + overDue
                            + "," + "'" + security + "'"
                            + "," + instAmount
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + Tempbd + "'"
                            + "," + "'" + Tempbd + "'"
                            + ")");
                    int varI = insertQuery8.executeUpdate();
                }
            } else {
                Query insertQuery9 = em.createNativeQuery("insert into kcc_liabdetails(Acno,instituation,purpose,osamt,overdue,security,yearlyinstallamt,enterby,authby,trantime,lastupdateddt)"
                        + "values (" + "'" + fullAccNo + "'"
                        + "," + "'N/A'"
                        + "," + "'N/A'"
                        + "," + 0
                        + "," + 0
                        + "," + "'N/A'"
                        + "," + 0
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + Tempbd + "'"
                        + "," + "'" + Tempbd + "'"
                        + ")");
                int varJ = insertQuery9.executeUpdate();
            }
            if (MHMAssets.size() > 8) {
                for (int a = 0, b = 1, c = 2, d = 3; a < MHMAssets.size(); a = a + 4, b = b + 4, c = c + 4, d = d + 4) {
                    String seqNo = (MHLiab.get(a).toString());
                    String particular = MHLiab.get(b).toString();
                    int number = Integer.parseInt(MHLiab.get(c).toString());
                    float presentValue = Float.parseFloat(MHLiab.get(d).toString());

                    Query insertQuery10 = em.createNativeQuery("insert into kcc_assetsdetails(acno,particulars,number,presentvalue,assettype,enterby,authby,trantime,lastupdateddt)"
                            + "values (" + "'" + fullAccNo + "'"
                            + "," + "'" + particular + "'"
                            + "," + number
                            + "," + presentValue
                            + "," + "'M'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + Tempbd + "'"
                            + "," + "'" + Tempbd + "'"
                            + ")");
                    int varK = insertQuery10.executeUpdate();
                }
            } else {
                Query insertQuery11 = em.createNativeQuery("insert into kcc_assetsdetails(acno,particulars,number,presentvalue,assettype,enterby,authby,trantime,lastupdateddt)"
                        + "values (" + "'" + fullAccNo + "'"
                        + "," + "'N/A'"
                        + "," + 0
                        + "," + 0
                        + "," + "'M'"
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + Tempbd + "'"
                        + "," + "'" + Tempbd + "'"
                        + ")");
                int varL = insertQuery11.executeUpdate();
            }
            if (MHIMAssets.size() > 8) {
                for (int a = 0, b = 1, c = 2, d = 3; a < MHIMAssets.size(); a = a + 4, b = b + 4, c = c + 4, d = d + 4) {
                    String seqNo = (MHIMAssets.get(a).toString());
                    String particular = MHIMAssets.get(b).toString();
                    int number = Integer.parseInt(MHIMAssets.get(c).toString());
                    float presentValue = Float.parseFloat(MHIMAssets.get(d).toString());

                    Query insertQuery12 = em.createNativeQuery("insert into kcc_assetsdetails(acno,particulars,number,presentvalue,assettype,enterby,authby,trantime,lastupdateddt)"
                            + "values (" + "'" + fullAccNo + "'"
                            + "," + "'" + particular + "'"
                            + "," + number
                            + "," + presentValue
                            + "," + "'IM'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + user.toUpperCase() + "'"
                            + "," + "'" + Tempbd + "'"
                            + "," + "'" + Tempbd + "'"
                            + ")");
                    int varM = insertQuery12.executeUpdate();
                }
            } else {
                Query insertQuery13 = em.createNativeQuery("insert into kcc_assetsdetails(acno,particulars,number,presentvalue,assettype,enterby,authby,trantime,lastupdateddt)"
                        + "values (" + "'" + fullAccNo + "'"
                        + "," + "'N/A'"
                        + "," + 0
                        + "," + 0
                        + "," + "'IM'"
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + user.toUpperCase() + "'"
                        + "," + "'" + Tempbd + "'"
                        + "," + "'" + Tempbd + "'"
                        + ")");
                int varN = insertQuery13.executeUpdate();
            }
            //ut.rollback();
            ut.commit();
            return "Record Save Successfully And Generated AccountNo. " + fullAccNo + "   Customer ID =  " + CustID;

        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(e);
            }
            throw new ApplicationException(e);
        }
    }

    /**
     * *************FinancialInclAccountOpenBean's
     * Methods********************************************
     */
    public List getAcctType() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select acctCode from accounttypemaster where acctNature in('" + CbsConstant.SAVING_AC + "','" + CbsConstant.DEPOSIT_SC + "') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getAgCode(String brnCode) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Agcode from ddsagent where status = 'A' AND brncode = '" + brnCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getROIForFinInclusion(int days, float amount, String date) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Interest_Rate from td_slab WHERE FromDays <= " + days + " And ToDays >= " + days + " AND fromamount <= " + amount + " AND toamount >= " + amount + " AND applicable_Date =(select max(Applicable_Date) from td_slab WHERE applicable_date <=date_format(str_to_date('" + date + "', '%d/%m/%Y'),'%Y%m%d'))").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public String saveAccountOpenDDS(String custType, String actype, String title, String custname, String craddress,
            String praddress, String phoneno, String dob, Integer occupation, Integer operatingMode, String status, String panno,
            String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro,
            String JtName1, String JtName2, String JtName3, String JtName4, String orgncode, String nominee, String nomineerelatioship,
            Integer docuno, String docudetails, int rdperiod, float rdInstallment, float roi,
            String nomname, String nomadd, String relation, String minor, String nomdob, String nomage, String custId1, String custId2, String custId3, String custId4) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String alphacode = null;
        String date = null;
        Integer custIdNew = null;
        String rdMatDate = "";
        int customerId;
        try {
            ut.begin();
            List dateList = em.createNativeQuery("select date_format('" + DateText + "','%Y%m%d')").getResultList();
            if (dateList.size() > 0) {
                Vector descVect = (Vector) dateList.get(0);
                date = descVect.get(0).toString();
            }

            List addList = em.createNativeQuery("select date_format(DATE_ADD('" + DateText + "', INTERVAL 2 MONTH),'%Y%m%d')").getResultList();
            if (addList.size() > 0) {
                Vector descVect = (Vector) dateList.get(0);
                rdMatDate = descVect.get(0).toString();
            }



            List branchmasterList = em.createNativeQuery("select  alphacode from branchmaster where brncode='" + orgncode + "'").getResultList();
            if (branchmasterList.size() > 0) {
                Vector descVect = (Vector) branchmasterList.get(0);
                alphacode = descVect.get(0).toString();
            }


            String custId = openingFacadeRemote.cbsCustId(actype, orgncode);
            if (custId.length() == 1) {
                custId = "00000" + custId;
            }
            if (custId.length() == 2) {
                custId = "0000" + custId;
            }
            if (custId.length() == 3) {
                custId = "000" + custId;
            }
            if (custId.length() == 4) {
                custId = "00" + custId;
            }
            if (custId.length() == 5) {
                custId = "0" + custId;
            }


            String acno = orgncode + actype + custId + "01";
            Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                    + "values ('" + acno + "','" + acno + "')");
            insertMapping.executeUpdate();

            if (!nomname.equalsIgnoreCase("") || nomname.length() != 0) {
                Integer nomDetail = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,"
                        + "nomage,enterby,authby,trantime)values('" + acno + "','" + nomname + "','" + nomadd + "','" + relation + "','" + minor + "',"
                        + "'" + nomdob + "'," + nomage + ",'" + UserText + "','" + UserText + "','" + DateText + "')").executeUpdate();
                if (nomDetail < 0) {
                    ut.rollback();
                    return "Data is not inserted into Nom Detail";
                }
            }

            List selectMaxCustId = em.createNativeQuery("select ifnull(max(cast(customerid as unsigned)),'0')as customerid from cbs_customer_master_detail").getResultList();
            Vector vecToSelectMaxCustid = (Vector) selectMaxCustId.get(0);
            String strForMaxCustid = vecToSelectMaxCustid.get(0).toString();
            customerId = Integer.parseInt(strForMaxCustid) + 1;


            Query insertIntoCustMasterDtl = em.createNativeQuery("insert into cbs_customer_master_detail(customerid,title,custname,fathername,DateOfBirth,mobilenumber,AddressLine1,AddressLine2,PrimaryBrCode,FirstAccountDate,Auth,RecordCreaterID,CreationTime) "
                    + "values(" + customerId + ",'" + title + "','" + custname + "','" + fathername + "','" + dob + "','" + phoneno + "','" + praddress + "','" + craddress + "','" + orgncode + "','" + DateText + "','N','" + UserText + "','" + DateText + "')");
            int insertResultIntoCustMastDtl = insertIntoCustMasterDtl.executeUpdate();
            if (insertResultIntoCustMastDtl < 0) {
                ut.rollback();
                return "Data is not inserted into cbs_customer_master_detail";
            }



            Integer customerMaster = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,"
                    + "status,panno, grdname, relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                    + "values('" + custId + "','" + actype + "','" + title + "','" + custname + "','" + craddress + "','" + praddress + "',"
                    + "'" + phoneno + "','" + dob + "','" + occupation + "','" + status + "','" + panno + "','" + grdname + "','" + grd_relation + "',"
                    + "'" + DateText + "','" + agcode + "','" + UserText + "','" + fathername + "','" + orgncode + "')").executeUpdate();
            if (customerMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Master";
            }

            Integer accountMaster = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,"
                    + "JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,creationdt,lastupdatedt,"
                    + "accttype,relatioship,JtName3,JtName4,Custname,intDeposit,custid1,custid2,custid3,custid4,CurBrCode)"
                    + "values('" + acno + "','" + date + "','" + date + "','" + acnoIntro + "'," + rdInstallment + ",'" + rdMatDate + "',0,"
                    + "'" + operatingMode + "','" + JtName1 + "','" + JtName2 + "',1,'" + occupation + "','" + nominee + "',0,1,0,"
                    + "'" + UserText + "','" + date + "','" + date + "','" + actype + "' ,'" + nomineerelatioship + "',"
                    + "'" + JtName3 + "','" + JtName4 + "','" + custname + "'," + roi + ","
                    + "'" + custId1 + "','" + custId2 + "','" + custId3 + "','" + custId4 + "','" + orgncode + "') ").executeUpdate();
            if (accountMaster < 0) {
                ut.rollback();
                return "Data is not inserted into Account Master";
            }

            Integer reconbalan = em.createNativeQuery("insert into reconbalan(acno,balance,dt)values('" + acno + "',0,'" + date + "')").executeUpdate();
            if (reconbalan < 0) {
                ut.rollback();
                return "Data is not inserted into Reconbalan";
            }
            Integer documentsReceived = em.createNativeQuery("insert documentsreceived (acno,groupdocu,docuno,docudetails,receiveddate) values('" + acno + "',14," + docuno + ",'" + docudetails + "','" + date + "')").executeUpdate();
            if (documentsReceived < 0) {
                ut.rollback();
                return "Data is not inserted into Documents Received";
            }

            Integer customer = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn) values"
                    + "('" + customerId + "','" + acno + "','" + UserText + "','" + alphacode + "')").executeUpdate();
            if (customer < 0) {
                ut.rollback();
                return "Data is not inserted into Customer Id ";
            }
            ut.commit();
            return "true" + acno + customerId;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveAccountOpenSbRd(String cust_type, String actype, String title, String custname, String craddress, String praddress, String phoneno, String dob, int occupation, int operatingMode, String panno, String grdname, String grd_relation, String agcode, String DateText, String UserText, String fathername, String acnoIntro, String JtName1, String JtName2, String orgncode, String nominee, String nominee_relatioship, String JtName3, String JtName4, int rdperiod, float rdinstall, float rdroi, int docuno, String docudetails, String nomineeAdd, String nomineeDate, String custid1, String custid2, String custid3, String custid4) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            int var1 = 0;
            int var3 = 0;
            String cust_no_new1 = null;
            String status = null;
            int customerId;
            List dateDiff = em.createNativeQuery("select TIMESTAMPDIFF(day, '" + dob + "','" + DateText + "')").getResultList();
            Vector dateDiffs = (Vector) dateDiff.get(0);
            String dateDifference = dateDiffs.get(0).toString();
            int stats = Integer.parseInt(dateDifference);
            int sts = stats / 365;
            if (sts >= 18) {
                status = "MJ";
            } else if (sts < 18) {
                status = "MN";
            }
            List secList = em.createNativeQuery("select acctnature from fn_acnat('" + actype + "')").getResultList();
            Vector secLst = (Vector) secList.get(0);
            String acct_nat = secLst.get(0).toString();
            List rdDtList = em.createNativeQuery("Select date_format(DATE_ADD('" + DateText + "', INTERVAL " + rdperiod + " MONTH),'%Y%m%d')").getResultList();
            Vector rdDtLst = (Vector) rdDtList.get(0);
            String rdmatDate = rdDtLst.get(0).toString();

            List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode=" + orgncode + "").getResultList();
            Vector alphaLst = (Vector) alphaList.get(0);
            String alphacode = alphaLst.get(0).toString();

            String custName = openingFacadeRemote.cbsCustId(actype, orgncode);
            int custNames = Integer.parseInt(custName);
            int cust_no_news = custNames + 1;

            //String cust_no_new = String.valueOf(cust_no_news);
            String cust_no_new = custName;// By Nishant Kansal on 27/12/2010
            if (cust_no_new.length() == 1) {
                cust_no_new1 = "00000" + cust_no_new;
            }
            if (cust_no_new.length() == 2) {
                cust_no_new1 = "0000" + cust_no_new;
            }
            if (cust_no_new.length() == 3) {
                cust_no_new1 = "000" + cust_no_new;
            }
            if (cust_no_new.length() == 4) {
                cust_no_new1 = "00" + cust_no_new;
            }
            if (cust_no_new.length() == 5) {
                cust_no_new1 = "0" + cust_no_new;
            }
            if (cust_no_new.length() == 6) {
                cust_no_new1 = cust_no_new;
            }
            String acno = orgncode + actype + cust_no_new1 + "01";
            Query insertMapping = em.createNativeQuery("insert into cbs_acno_mapping(OLD_AC_NO,NEW_AC_NO)"
                    + "values ('" + acno + "','" + acno + "')");
            insertMapping.executeUpdate();

            List selectMaxCustId = em.createNativeQuery("select ifnull(max(cast(customerid as unsigned)),'0')as customerid from cbs_customer_master_detail").getResultList();
            Vector vecToSelectMaxCustid = (Vector) selectMaxCustId.get(0);
            String strForMaxCustid = vecToSelectMaxCustid.get(0).toString();
            customerId = Integer.parseInt(strForMaxCustid) + 1;


            Query insertIntoCustMasterDtl = em.createNativeQuery("insert into cbs_customer_master_detail(customerid,title,custname,fathername,DateOfBirth,mobilenumber,AddressLine1,AddressLine2,PrimaryBrCode,FirstAccountDate,Auth,RecordCreaterID,CreationTime) "
                    + "values(" + customerId + ",'" + title + "','" + custname + "','" + fathername + "','" + dob + "','" + phoneno + "','" + praddress + "','" + craddress + "','" + orgncode + "','" + DateText + "','N','" + UserText + "','" + DateText + "')");
            int insertResultIntoCustMastDtl = insertIntoCustMasterDtl.executeUpdate();
            if (insertResultIntoCustMastDtl < 0) {
                ut.rollback();
                return "Data is not inserted into cbs_customer_master_detail";
            }

            Query insertQuery = em.createNativeQuery("insert into customermaster(custno,actype,title,custname,craddress,praddress,phoneno,dob,occupation,status,panno, grdname,relation,lastupdatedt,AgCode,EnteredBy,fathername,brncode)"
                    + "values (" + "'" + cust_no_new1 + "'" + "," + "'" + actype + "'" + "," + "'" + title + "'" + "," + "'" + custname + "'" + "," + "'" + craddress + "'" + "," + "'" + praddress + "'" + "," + "'" + phoneno + "'" + "," + "'" + dob + "'" + "," + occupation + "," + "'" + status + "'" + "," + "'" + panno + "'" + "," + "'" + grdname + "'" + "," + "'" + grd_relation + "'" + "," + "'" + DateText + "'" + "," + "'" + agcode + "'" + "," + "'" + UserText + "'" + "," + "'" + fathername + "'" + "," + "'" + orgncode + "'" + ")");
            var = insertQuery.executeUpdate();
            if (var < 0) {
                ut.rollback();
                return "Data is not inserted into customermaster";
            }

            if (acct_nat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                int sno = 1;
                int period = 1;
                for (int i = 0; i < rdperiod; i++) {
                    List rdDtList1 = em.createNativeQuery("Select date_format(DATE_ADD('" + DateText + "', INTERVAL " + period + " MONTH),'%Y%m%d')").getResultList();
                    Vector rdDtLst1 = (Vector) rdDtList1.get(0);
                    String rdmatDate1 = rdDtLst1.get(0).toString();
                    Query insertQuery1 = em.createNativeQuery("insert into rd_installment(acno,sno,duedt,installamt,status,enterby)"
                            + "values (" + "'" + acno + "'" + "," + sno + "," + "'" + rdmatDate1 + "'" + "," + rdinstall + "," + "'Unpaid'" + "," + "'" + UserText + "'" + ")");
                    var1 = insertQuery1.executeUpdate();
                    if (var1 < 0) {
                        ut.rollback();
                        return "Data is not inserted into rd_installment";
                    }


                    sno++;
                    period++;
                }
            }
            Query insertQuery2 = em.createNativeQuery("insert into accountmaster(acno,openingdt,LastOpDate,introaccno,RDInstal,RDmatDate,closingbal,opermode,JtName1,JtName2,accstatus,orgncode,nomination,ODLimit,minbal,penalty,enteredby,lastupdatedt,accttype,relatioship,JtName3,JtName4,Custname,intDeposit,ClosingDate,optstatus,custid1,custid2,custid3,custid4,CurBrCode)"
                    + "values (" + "'" + acno + "'" + "," + "'" + DateText + "'" + "," + "'" + DateText + "'" + "," + "'" + acnoIntro + "'" + "," + rdinstall + "," + "'" + rdmatDate + "'" + "," + 0 + "," + "'" + operatingMode + "'" + "," + "'" + JtName1 + "'" + "," + "'" + JtName2 + "'" + "," + 1 + "," + occupation + "," + "'" + nominee + "'" + "," + 0 + "," + 1 + "," + 0 + "," + "'" + UserText + "'" + "," + "'" + DateText + "'" + "," + "'" + actype + "'" + "," + "'" + nominee_relatioship + "'" + "," + "'" + JtName3 + "'" + "," + "'" + JtName4 + "'" + "," + "'" + custname + "'" + "," + rdroi + "," + "'null'" + "," + 1 + "," + "'" + custid1 + "'" + "," + "'" + custid2 + "'" + "," + "'" + custid3 + "'" + "," + "'" + custid4 + "'" + ",'" + orgncode + "')");
            int var2 = insertQuery2.executeUpdate();
            if (var2 < 0) {
                ut.rollback();
                return "Data is not inserted into accountmaster";
            }


            if (acct_nat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Query insertQuery3 = em.createNativeQuery("insert into ca_reconbalan(acno,balance,dt)"
                        + "values (" + "'" + acno + "'" + "," + 0 + "," + "'" + DateText + "'" + ")");
                var3 = insertQuery3.executeUpdate();
                if (var3 < 0) {
                    ut.rollback();
                    return "Data is not inserted into ca_reconbalan";
                }

            } else {
                Query insertQuery4 = em.createNativeQuery("insert into reconbalan(acno,balance,dt)"
                        + "values (" + "'" + acno + "'" + "," + 0 + "," + "'" + DateText + "'" + ")");
                int var4 = insertQuery4.executeUpdate();
                if (var4 < 0) {
                    ut.rollback();
                    return "Data is not inserted into reconbalan";
                }
            }

            Query insertQuery5 = em.createNativeQuery("insert into documentsreceived(acno,groupdocu,docuno,docudetails,receiveddate)"
                    + "values (" + "'" + acno + "'" + "," + 14 + "," + docuno + "," + "'" + docudetails + "'" + "," + "'" + DateText + "'" + ")");
            int var5 = insertQuery5.executeUpdate();
            if (var5 < 0) {
                ut.rollback();
                return "Data is not inserted into documentsreceived";
            }

            Query insertQuery6 = em.createNativeQuery("insert into customerid(custid,acno,enterby,txnbrn)"
                    + "values (" + "'" + customerId + "'" + "," + "'" + acno + "'" + "," + "'" + UserText + "'" + "," + "'" + alphacode + "'" + ")");
            int var6 = insertQuery6.executeUpdate();
            if (var6 < 0) {
                ut.rollback();
                return "Data is not inserted into customerid";
            }

            if ((nominee == null) || (nominee.equalsIgnoreCase(""))) {
            } else {
                if (status.equals("MJ")) {
                    status = "N";
                } else {
                    status = "Y";
                }
                Query insertQuery7 = em.createNativeQuery("insert into nom_details(acno,nomname,nomadd,relation,minior,nomdob,nomage,enterby,authby,trantime)"
                        + "values (" + "'" + acno + "'" + "," + "'" + nominee + "'" + "," + "'" + nomineeAdd + "'" + "," + "'" + nominee_relatioship + "'" + "," + "'" + status + "'" + "," + "'" + nomineeDate + "'" + "," + sts + "," + "'" + UserText + "'" + "," + "'" + UserText + "'" + "," + "'" + DateText + "'" + ")");
                int var7 = insertQuery7.executeUpdate();
                if (var7 < 0) {
                    ut.rollback();
                    return "Data is not inserted into nom_details";
                }
            }
            ut.commit();
            return "true" + acno + customerId;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new ApplicationException(e);
            }
            throw new ApplicationException(e);
        }
    }

    public String getMaxToMonForDDS(String acTp) throws com.cbs.exception.ApplicationException {
        String maxToNo = "0";
        try {
            List resultlist = em.createNativeQuery("select max(to_mon) from dds_slab where actype = '" + acTp + "'"
                    + " and applicable_date = (select max(applicable_date) from dds_slab where actype ='" + acTp + "')").getResultList();
            if (!resultlist.isEmpty()) {
                Vector resultVec = (Vector) resultlist.get(0);
                maxToNo = resultVec.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return maxToNo;
    }
}
