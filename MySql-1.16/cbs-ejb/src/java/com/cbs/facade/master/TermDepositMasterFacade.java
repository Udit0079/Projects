package com.cbs.facade.master;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.TdIntDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.pojo.TdEntry;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

@Stateless(mappedName = "TermDepositMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TermDepositMasterFacade implements TermDepositMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    TdReceiptManagementFacadeRemote tdRcptMgmtRemote;
    @EJB
    RDIntCalFacadeRemote rdIntCalRemote;
    @EJB
    SbIntCalcFacadeRemote sbRemote;
    @Resource
    EJBContext context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List tableDataTdCondition() throws ApplicationException {
        try {
            return em.createNativeQuery("select coalesce(sno,'') as sno,status,date_format(Applicable_Date,'%d/%m/%Y') AS Applicable_Date,TDAmount,TDDayMth,TDDayCum,EnterBy,date_format(LastUpdateDt,'%d/%m/%Y') AS LastUpdateDt From tdcondition where sno is not null Order by Applicable_Date,sno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String save(String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((tdsAmount == null) || (tdsAmount.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Amount";
            }
            if ((tdsRate == null) || (tdsRate.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Monthly in Days";
            }
            if ((tdsSurcharge == null) || (tdsSurcharge.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Cumulative in Days";
            }
            if ((tdsApplicableDt == null) || (tdsApplicableDt.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Applicable Date";
            }
            int tdAmt = Integer.parseInt(tdsAmount);
            int tdRate = Integer.parseInt(tdsRate);
            int tdSurchage = Integer.parseInt(tdsSurcharge);
            int tdApplicableDt = Integer.parseInt(tdsApplicableDt);
            if (custType.equals("NEW")) {
                custType = "N";
            }
            if (custType.equals("RENEWAL")) {
                custType = "R";
            }
            List seqNo = em.createNativeQuery("select ifnull(max(sno),'0') from tdcondition").getResultList();
            Vector Lst = (Vector) seqNo.get(0);
            String sequenNo = Lst.get(0).toString();
            float sNo = Float.parseFloat(sequenNo);
            if (sequenNo.equals("")) {
                sNo = 1;
            } else {
                sNo = sNo + 1;
            }
            String currentDate = sdf.format(date);
            Integer varint = 0;
            Integer varint1 = 0;
            List con = em.createNativeQuery("Select date_format(cast(Applicable_Date as datetime),'%d/%m/%Y') AS Applicable_Date from tdcondition where applicable_Date in (Select max(Applicable_Date) From tdcondition where status ='" + custType + "')").getResultList();
            if (con.isEmpty()) {
                Query insertQuery = em.createNativeQuery("insert into tdcondition(Sno,status,Applicable_Date,TDAmount,TDDayMth,TDDayCum,trantime,enterBy,enterDt,LastUpdateBy,LastUpdateDt)"
                        + "values (" + "'" + sNo + "'" + "," + "'" + custType + "'" + "," + "'" + tdsApplicableDt + "'" + "," + "'" + tdAmt + "'" + "," + "'" + tdRate + "'" + "," + "'" + tdSurchage + "'" + "," + "'" + currentDate + "'" + "," + "'" + (authBy) + "'" + "," + "'" + currentDate + "'" + "," + "'" + (authBy) + "'" + "," + "'" + currentDate + "'" + ")");
                varint = insertQuery.executeUpdate();
            } else {
                Vector application = (Vector) con.get(0);
                String applicatn = application.get(0).toString();
                String dd = applicatn.substring(0, 2);
                String mm = applicatn.substring(3, 5);
                String yy = applicatn.substring(6, 10);
                String app = yy + "" + mm + "" + dd;
                int applications = Integer.parseInt(app);
                if (applications < tdApplicableDt) {
                    Query insertQuery = em.createNativeQuery("insert into tdcondition (Sno,status,Applicable_Date,TDAmount, TDDayMth, TDDayCum,trantime,enterBy, enterDt,LastUpdateBy,LastUpdateDt)"
                            + "values (" + "'" + sNo + "'" + "," + "'" + custType + "'" + "," + "'" + tdsApplicableDt + "'" + "," + "'" + tdAmt + "'" + "," + "'" + tdRate + "'" + "," + "'" + tdSurchage + "'" + "," + "'" + currentDate + "'" + "," + "'" + (authBy) + "'" + "," + "'" + currentDate + "'" + "," + "'" + (authBy) + "'" + "," + "'" + currentDate + "'" + ")");
                    varint1 = insertQuery.executeUpdate();
                } else {
                    String abc = dd + "/" + mm + "/" + yy;
                    ut.rollback();
                    return "You Can Not Enter Record Less Than " + abc;
                }
            }
            if ((varint > 0) || (varint1 > 0)) {
                ut.commit();
                return "Data Saved Successfully";
            } else {
                ut.rollback();
                return "Data could not be Saved";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String gridClick(String custType, float sNum) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((custType == null) || (custType.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Customer Type is not Blank";
            }
            Query updateQuery = em.createNativeQuery("Delete from tdcondition where sno='" + sNum + "'");
            int var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Record Is deleted Successfully";
            } else {
                ut.rollback();
                return "Data could not be deleted";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String upDate(String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, float sNum, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((tdsAmount == null) || (tdsAmount.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Amount";
            }
            if ((tdsRate == null) || (tdsRate.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Monthly in Days";
            }
            if ((tdsSurcharge == null) || (tdsSurcharge.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Cumulative in Days";
            }
            if ((tdsApplicableDt == null) || (tdsApplicableDt.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Applicable Date";
            }
            int tdAmt = Integer.parseInt(tdsAmount);
            int tdRate = Integer.parseInt(tdsRate);
            int tdSurchage = Integer.parseInt(tdsSurcharge);
            if (custType.equals("NEW")) {
                custType = "N";
            }
            if (custType.equals("RENEWAL")) {
                custType = "R";
            }
            String tempBds = sdf.format(date);
            Query updateQuery = em.createNativeQuery("Update tdcondition set status='" + custType + "', Applicable_Date='" + tdsApplicableDt + "' ,TDAmount=" + tdAmt + ", TDDayMth=" + tdRate + ", TDDayCum=" + tdSurchage + ", trantime='" + tempBds + "',LastUpdateBy='" + userName + "', LastUpdateDt='" + tempBds + "' where sno=" + sNum + "");
            int var1 = updateQuery.executeUpdate();
            if (var1 > 0) {
                ut.commit();
                return "Data Update Successfully";
            } else {
                ut.rollback();
                return "Data could not be Updated";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List tableDataTdsSlab() throws ApplicationException {
        try {
            return em.createNativeQuery("select type,TDS_ApplicableDate,Tds_Amount,Tds_Rate,Tds_Surcharge,TDS_GlHead,EnteredBy,date_format(LastUpdateDt,'%d/%m/%Y') AS LastUpdateDt,Sno,ifnull(tdsRate_pan,0),Srctzn_Tds_Amount From tdsslab Order by TDS_ApplicableDate").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String save(String glAcno, String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, String authBy,String tdsRatePan,String srctznTdsAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((tdsAmount == null) || (tdsAmount.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Amount";
            }
            if ((tdsRate == null) || (tdsRate.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Rate";
            }
            if ((tdsSurcharge == null) || (tdsSurcharge.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Surchage";
            }
            if ((tdsApplicableDt == null) || (tdsApplicableDt.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Applicable Date";
            }
            if ((glAcno == null) || (glAcno.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The TDS-GL Head ";
            }
            float tdAmt = Float.parseFloat(tdsAmount);
            float tdRate = Float.parseFloat(tdsRate);
            float tdSurchage = Float.parseFloat(tdsSurcharge);
            if (custType.equals("INDIVIDUAL")) {
                custType = "1";
            }
            if (custType.equals("COMPANY")) {
                custType = "2";
            }
            if (custType.equals("OTHERS")) {
                custType = "3";
            }
            int ctType = Integer.parseInt(custType);
            List seqNo = em.createNativeQuery("select * from tdsslab where type=" + ctType + " and tds_applicabledate='" + tdsApplicableDt + "'").getResultList();
            if (!seqNo.isEmpty()) {
                ut.rollback();
                return "This detail is already exists.";
            }
            seqNo = em.createNativeQuery("select ifnull(max(sno),0)+1 from tdsslab").getResultList();
            Vector Lst = (Vector) seqNo.get(0);
            String sequenNo = Lst.get(0).toString();
            int sNo = Integer.parseInt(sequenNo);
            Query insertQuery = em.createNativeQuery("insert into tdsslab(sno,type,TDS_Applicabledate,Tds_Amount,Tds_Rate,Tds_Surcharge,TDS_GLHead,EnteredBy,LastUpdateDt,tdsRate_pan,Srctzn_Tds_Amount)"
                    + "values (" + sNo + "," + ctType + "," + "'" + tdsApplicableDt + "'" + "," + tdAmt + "," + tdRate + "," + tdSurchage + "," + "'" + glAcno + "'" + "," + "'" + authBy + "'" + "," + "'" + sdf.format(date) + "'" + ","+tdsRatePan+","+srctznTdsAmt+")");
            Integer varint = insertQuery.executeUpdate();
            if (varint > 0) {
                ut.commit();
                return "Data saved successfully.";
            } else {
                ut.rollback();
                return "Data could not be saved.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String upDate(String glAcno, String custType, String tdsApplicableDt, String tdsAmount, String tdsRate, String tdsSurcharge, int sNum,String tdsRatePan,String srctznTdsAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((tdsAmount == null) || (tdsAmount.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Amount";
            }
            if ((tdsRate == null) || (tdsRate.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Rate";
            }
            if ((tdsSurcharge == null) || (tdsSurcharge.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Surchage";
            }
            if ((tdsApplicableDt == null) || (tdsApplicableDt.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The Applicable Date";
            }
            if ((glAcno == null) || (glAcno.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Please Fill The TDS-GL Head ";
            }
            float tdAmt = Float.parseFloat(tdsAmount);
            float tdRate = Float.parseFloat(tdsRate);
            float tdSurchage = Float.parseFloat(tdsSurcharge);

            if (custType.equals("INDIVIDUAL")) {
                custType = "1";
            }
            if (custType.equals("COMPANY")) {
                custType = "2";
            }
            if (custType.equals("OTHERS")) {
                custType = "3";
            }
            int ctType = Integer.parseInt(custType);
            Query updateQuery = em.createNativeQuery("Update tdsslab set type=" + ctType + ",TDS_Applicabledate='" + tdsApplicableDt + "',Tds_Amount=" + tdAmt + ",Tds_Rate=" + tdRate + ",Tds_Surcharge=" + tdSurchage + ",TDS_GLHead='" + glAcno + "',LastUpdateDt='" + sdf.format(date) + "',tdsRate_pan=" + tdsRatePan + ",Srctzn_Tds_Amount = "+srctznTdsAmt+" where sno=" + sNum + "");
            int var1 = updateQuery.executeUpdate();
            if (var1 > 0) {
                ut.commit();
                return "Data update successfully.";
            } else {
                ut.rollback();
                return "Data could not be updated.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String gridClick(String custType, int sNum) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if ((custType == null) || (custType.trim().equalsIgnoreCase(""))) {
                ut.rollback();
                return "Customer Type is not Blank";
            }
            Query updateQuery = em.createNativeQuery("Delete from tdsslab where sno='" + sNum + "'");
            int var = updateQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Record is deleted successfully.";
            } else {
                ut.rollback();
                return "Data could not be deleted.";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String checkGlTds(String glAcno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List secList = em.createNativeQuery("select distinct substring(acno,3,10) from gltable WHERE substring(ACNO,3,10)='" + glAcno + "'").getResultList();
            if ((secList.isEmpty())) {
                ut.rollback();
                return "Please enter valid gl head.";
            } else {
                Vector secLst = (Vector) secList.get(0);
                String secListed = secLst.get(0).toString();
                if ((secListed == null) || (secListed.trim().equalsIgnoreCase(""))) {
                    ut.rollback();
                    return "Please enter valid gl head.";
                }
            }
            ut.commit();
            return "";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List accountDetails(String custid, String opt) throws ApplicationException {
        try {
            List resultList = new ArrayList();
            //return em.createNativeQuery("SELECT a.CUSTNAME,b.DESCRIPTION,concat(a.JtName1,a.JTNAME2) AS 'JT/UG NAME' FROM td_accountmaster a,codebook b WHERE a.ACNO = '" + accountNo + "' AND b.groupcode = 4 AND a.OPERMODE = b.CODE").getResultList();
            if (!opt.equalsIgnoreCase("A")) {
                resultList = em.createNativeQuery("SELECT CustFullName,concat(fathername,' ',FatherMiddleName,' ',FatherLastName),DateOfBirth, PAN_GIRNumber,formNo15G_15H,aggregateAmt,otherIncome,deductionAmt,assessmentYear "
                        + "FROM cbs_customer_master_detail a,tds_docdetail_header b WHERE a.customerid = '" + custid + "' and a.customerid = b.custid ").getResultList();
                if (resultList.isEmpty()) {
                    resultList = em.createNativeQuery("SELECT CustFullName,concat(fathername,' ',FatherMiddleName,' ',FatherLastName),DateOfBirth, PAN_GIRNumber,0 formNo15G_15H,0 aggregateAmt,0 otherIncome,0 deductionAmt,0 assessmentYear \n"
                            + "FROM cbs_customer_master_detail a WHERE a.customerid = '" + custid + "'").getResultList();
                }
            } else {
                resultList = em.createNativeQuery("SELECT CustFullName,concat(fathername,' ',FatherMiddleName,' ',FatherLastName),DateOfBirth, PAN_GIRNumber FROM "
                        + " cbs_customer_master_detail WHERE customerid = '" + custid + "'").getResultList();
            }
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

//    public List tableData(String accountNo, String fYr) throws ApplicationException {
//        try {
//            //return em.createNativeQuery("select Acno,SeqNo,date_format(FormDate,'%d/%m/%Y') AS FormDate,Fyear,Details from td_form15h where acno = '" + accountNo + "'").getResultList();
//            return em.createNativeQuery("select th.Acno,th.SeqNo,date_format(th.FormDate,'%d/%m/%Y') AS FormDate,th.Fyear,th.Details,ta.tdsdetails from td_form15h th, "
//                    + " td_accountmaster ta where th.acno = '"+ accountNo +"' and th.acno = ta.acno and formdate = (select max(formdate) from td_form15h where acno = '"+ accountNo +"' "
//                    + " and fyear ='"+ fYr +"')").getResultList();
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public List tableData(String custId, String brnId, String Opt) throws ApplicationException {
        try {
            if (Opt.equalsIgnoreCase("E")) {
                return em.createNativeQuery("select customerid,acno,seqNo,date_format(submission_date,'%d/%m/%Y'),fyear,ifnull(receiptNo,''),doc_details,docflag,orgbrnid,auth,enterBy,uniqueIdentificationNo from tds_docdetail "
                        + " where customerid = '" + custId + "' order by seqNo").getResultList();
            } else if (Opt.equalsIgnoreCase("V")) {
                return em.createNativeQuery("select customerid,acno,seqNo,date_format(submission_date,'%d/%m/%Y'),fyear,ifnull(receiptNo,''),doc_details,docflag,orgbrnid,auth,enterBy,uniqueIdentificationNo from tds_docdetail "
                        + " where customerid = '" + custId + "' and auth = 'N' and orgbrnid ='" + brnId + "' order by seqNo").getResultList();
            } else {
                return em.createNativeQuery("select customerid,acno,seqNo,date_format(submission_date,'%d/%m/%Y'),fyear,ifnull(receiptNo,''),doc_details,docflag,orgbrnid,auth,enterBy from tds_docdetail "
                        + " where customerid = '" + custId + "' and auth = 'N' and orgbrnid ='" + brnId + "' order by seqNo").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    //public String upDateData(String date, String details, int seqnum, String brcode, String fYears, String tdsOpt, String acno) throws ApplicationException {
    public String upDateData(String date, List<TdEntry> details, String fYears, String custId, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int years = year - 1;
            int month = Integer.parseInt(date.substring(4, 6));
            int fYear = 0;
            if (month < 4) {
                fYear = years;
            } else {
                fYear = year;
            }
            if (!fYears.equalsIgnoreCase(String.valueOf(fYear))) {
                throw new ApplicationException("Please Enter Correct FYear");
            }
            ut.begin();
            for (int i = 0; i < details.size(); i++) {
                int seqNo = details.get(i).getSeqNumber();
                String dFlag = details.get(i).getrFlag().toString();
                String docDtl = details.get(i).getTdDtl().toString();

                Query updateQuery = em.createNativeQuery("UPDATE tds_docdetail SET doc_details ='" + docDtl + "', "
                        + " enterby = '" + user + "', submission_date='" + date + "', docflag = '" + dFlag + "'  "
                        + " WHERE seqNo = " + seqNo + " and customerid='" + custId + "'");
                int var = updateQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Updated");
                }
            }
            ut.commit();
            return "Record Is Updated Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveData(String date, List<TdEntry> details, String custId, String userName, String brcode, String fYears, String tdsOpt, double totalNoForm, double aggregateAmount, double otherIncome, double deductionAmt, double estimatedIncome, String assessmentYear, String taxOption) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {

            int year = Integer.parseInt(date.substring(0, 4));
            int years = year - 1;
            int month = Integer.parseInt(date.substring(4, 6));
            int fYear = 0;
            int secnum = 0;
            if (month < 4) {
                fYear = years;
            } else {
                fYear = year;
            }
            if (!fYears.equalsIgnoreCase(String.valueOf(fYear))) {
                throw new ApplicationException("Please Enter Correct FYear");
            }
            String uin = "";
            if (!(tdsOpt.equalsIgnoreCase("Exemption certificate") || tdsOpt.equalsIgnoreCase("Cooperative Society"))) {

                List uNoList = em.createNativeQuery("select cast(ifnull(max(substring(uniqueIdentificationNo,2,9)),0) as unsigned)+1 from tds_docdetail where substring(uniqueIdentificationNo,1,1)='" + tdsOpt.substring(7) + "'").getResultList();
                Vector uNoVector = (Vector) uNoList.get(0);
                String uNo = uNoVector.get(0).toString();
                CbsUtil.lPadding(9, Integer.parseInt(uNo));
                String tanNo = getTanNo(brcode);
                if (tanNo.equalsIgnoreCase("")) {
                    throw new ApplicationException("Please Fill Tan No !");
                }
                uin = tdsOpt.substring(7) + CbsUtil.lPadding(9, Integer.parseInt(uNo)) + fYear + (Integer.parseInt(fYears.substring(2, 4)) + 1) + tanNo;
                System.out.println(uin);
            }
            ut.begin();

            for (int i = 0; i < details.size(); i++) {
                String acno = details.get(i).getAccountNumber().toString();
                String maxVouchNo = details.get(i).getVchNo().toString();
                String status = details.get(i).getrFlag().toString();
                // if (status.equalsIgnoreCase("Y")) {
                List secList = em.createNativeQuery("select coalesce(max(seqno),0)+1 from tds_docdetail where fyear = " + fYear + " AND customerid='" + custId + "'").getResultList();
                Vector secLst = (Vector) secList.get(0);
                String secListed = secLst.get(0).toString();
                secnum = Integer.parseInt(secListed);

                Query insertQuery = em.createNativeQuery("insert into tds_docdetail(customerid,acno,seqNo,submission_date,"
                        + "fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,uniqueIdentificationNo,Tax_Option)"
                        + "values ('" + custId + "','" + acno + "'," + secnum + ",now()," + fYear + ",'" + maxVouchNo + "','" + tdsOpt + "','" + status + "','" + brcode + "',now(),'" + userName + "','N','" + uin + "','" + taxOption + "')");
                int var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Inserted");
                }
                // }

            }
            if (!tdsOpt.equalsIgnoreCase("Exemption certificate")) {
                Query insertQuery = em.createNativeQuery("INSERT INTO tds_docdetail_header (custId, formNo15G_15H, aggregateAmt, otherIncome, deductionAmt,estimatedIncome,orgBrnid,uniqueIdentificationNo,submission_date,assessmentYear) VALUES "
                        + "('" + custId + "'," + totalNoForm + "," + aggregateAmount + "," + otherIncome + "," + deductionAmt + "," + estimatedIncome + ",'" + brcode + "','" + uin + "',now(),'" + assessmentYear + "')");
                int var = insertQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Updated");
                }
            }
            ut.commit();
            return "Record Save Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List fYearData(String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select min(F_Year) from yearend where yearendflag='N' and brncode='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getTableDetails(String acNat) throws ApplicationException {
        try {
            return em.createNativeQuery("select Sno,date_format(Applicable_Date,'%d/%m/%Y'), Interest_Rate, DetailsFrom, DetailsTo,"
                    + "FromAmount,ToAmount,SC,ST,OT,sno,acctNature,mg from td_slab where acctNature = '" + acNat + "' and Applicable_Date = (select max(Applicable_Date) from td_slab where acctNature = '" + acNat + "') "
                    + "Order by FromDays").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveRecord(String dateOfEffect, String roi, String fromDays, String toDays, String fromAmt, String toAmt, String scn, String stn, String otn, String fromDaysDetails, String toDaysDetails, String enterBy, boolean value, String SeqenceNo, String acNat, String mgn) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (value == false) {
                List selectQuery = em.createNativeQuery("select Applicable_Date from td_slab Where Sno =" + SeqenceNo
                        + " and dt=date_format(curdate(),'%Y%m%d')").getResultList();
                if (selectQuery.size() <= 0) {
                    ut.rollback();
                    return "Past record can not be updated/deleted ";
                }
                Query q1 = em.createNativeQuery("update td_slab set Interest_Rate = '" + roi + "',FromDays = '" + fromDays
                        + "',ToDays='" + toDays + "',fromamount='" + fromAmt + "',toamount='" + toAmt + "',sc='" + scn + "',st='" + stn
                        + "',ot='" + otn + "',mg='" + mgn + "',Detailsfrom ='" + fromDaysDetails + "',enterby ='" + enterBy + "',acctNature ='" + acNat + "',detailsto='" + toDaysDetails
                        + "'  where Applicable_Date = '" + dateOfEffect + "' and Sno=" + SeqenceNo + "");
                int int1 = q1.executeUpdate();
                if (int1 > 0) {
                    ut.commit();
                    return "Data has been successfully updated";
                } else {
                    ut.rollback();
                    return "Data does not updated";
                }
            } else {
                List l1 = em.createNativeQuery("select * from td_slab where fromdays='" + fromDays + "' and Todays='" + toDays
                        + "' and fromAmount <= '" + fromAmt + "'  and toAmount >= '" + toAmt + "' and applicable_Date='" + dateOfEffect
                        + "' and Interest_rate='" + roi + "' and SC ='" + scn + "' and ST = '" + stn + "' and OT='" + otn + "' and acctNature='" + acNat + "'").getResultList();
                if (!l1.isEmpty()) {
                    ut.rollback();
                    return "Amount range already present. Overlapping From/To Amount does not possible for same date";
                } else {
                    Query q = em.createNativeQuery("select (ifnull(max(sno),0)+1) from td_slab");
                    List l = q.getResultList();
                    Vector v = (Vector) l.get(0);
                    int sno = Integer.parseInt(v.get(0).toString());
                    Query insertRecon = em.createNativeQuery("Insert into td_slab(sno,applicable_date,interest_rate, fromdays, todays, fromamount, "
                            + "toamount, sc, st , ot,mg,detailsfrom,detailsto,enterby,dt,acctNature) values(" + sno + ",'" + dateOfEffect + "','" + roi + "','"
                            + fromDays + "','" + toDays + "','" + fromAmt + "','" + toAmt + "','" + scn + "','" + stn + "','" + otn + "','" + mgn + "','"
                            + fromDaysDetails + "','" + toDaysDetails + "','" + enterBy + "','" + ymd.format(new Date()) + "','" + acNat + "')");
                    int result = insertRecon.executeUpdate();
                    if (result > 0) {
                        ut.commit();
                    } else {
                        ut.rollback();
                        return "Transaction has been rollbacked";
                    }
                }
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
        return "Data has been successfully saved";
    }

    public List getTableHistry(String acNat) throws ApplicationException {
        try {
            return em.createNativeQuery("select Sno,date_format(Applicable_Date,'%d/%m/%Y'), Interest_Rate, DetailsFrom, DetailsTo,FromAmount,ToAmount,SC,ST,OT,acctNature,MG from td_slab where acctNature ='" + acNat + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List returnCurrentItem() throws ApplicationException {
        try {
            Query q = em.createNativeQuery("select max(sno) from td_slab");
            List l = q.getResultList();
            Vector v = (Vector) l.get(0);
            return em.createNativeQuery("select Sno,date_format(Applicable_Date,'%d/%m/%Y'), Interest_Rate, DetailsFrom, DetailsTo,"
                    + "FromAmount,ToAmount,SC,ST,OT from td_slab where sno=" + Integer.parseInt(v.get(0).toString()) + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List returnCurrentDateItem(String acNat) throws ApplicationException {
        try {
            List l = em.createNativeQuery("SELECT date_format(curdate(),'%Y%m%d')").getResultList();
            Vector v = (Vector) l.get(0);
            String date1 = v.get(0).toString();
            return em.createNativeQuery("select Sno,date_format(Applicable_Date,'%d/%m/%Y'),Interest_Rate, DetailsFrom, DetailsTo,"
                    + "FromAmount,ToAmount,SC,ST,OT,sno,acctNature,mg from td_slab where Applicable_Date ='" + date1 + "' and acctNature='" + acNat + "' Order by FromDays ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteData(int seqNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            List selectQuery = em.createNativeQuery("select * from td_slab Where Sno =" + seqNo + " and dt=date_format(curdate(),'%Y%m%d')").getResultList();
            if (selectQuery.size() <= 0) {
                ut.rollback();
                return "Past record can not be updated/deleted ";
            }
            Query deleteQuery = em.createNativeQuery("Delete from td_slab Where Sno =" + seqNo + "");
            var = deleteQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Data deleted successfully.";
            } else {
                ut.rollback();
                return "Please fill correct data";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List dateDiff(String ds) throws ApplicationException {
        try {
            return em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + ds + "','" + sdf.format(date) + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List customerData(String custId, String finMinDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno,VoucherNo,date_format(fddt,'%d/%m/%Y'),date_format(matdt,'%d/%m/%Y') from td_vouchmst where acno in ( "
                    + " select acno from td_accountmaster where acno in (select acno from customerid c, (select acctcode from accounttypemaster where "
                    + " acctnature in ('FD','MS')) a where c.custid = '" + custId + "' and substring(c.acno,3,2) = a.acctcode) and (closingdate is null OR closingdate = '' OR closingdate > '" + finMinDt + "')) and(ClDt is null OR ClDt = '' OR ClDt > '" + finMinDt + "') "
                    + " and VoucherNo not in(select receiptNo from tds_docdetail where customerid = '" + custId + "')"
                    + " union "
                    + " select acno,'0'VoucherNo,date_format(openingdt,'%d/%m/%Y'),date_format(ifnull(rdmatdate,'1900-01-01'),'%d/%m/%Y') from accountmaster where "
                    + " acno in (select acno from customerid c, (select acctcode from accounttypemaster where acctnature in ('RD','DS')) a "
                    + " where c.custid = '" + custId + "' and substring(c.acno,3,2) = a.acctcode) and (closingdate is null OR closingdate = '' OR closingdate > '" + finMinDt + "') "
                    + "and acno not in(select acno from tds_docdetail where customerid = '" + custId + "' and substring(acno,3,2) in"
                    + "(select acctcode from accounttypemaster where acctnature in ('RD','DS'))) ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteData(String date, String fYears, String custId) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int years = year - 1;
            int month = Integer.parseInt(date.substring(4, 6));
            int fYear = 0;
            if (month < 4) {
                fYear = years;
            } else {
                fYear = year;
            }
            if (!fYears.equalsIgnoreCase(String.valueOf(fYear))) {
                throw new ApplicationException("Please Enter Correct FYear");
            }

            ut.begin();

            String alreadyAuth = "N";

            List chkList = em.createNativeQuery("select auth from tds_docdetail where customerid = '" + custId + "'").getResultList();
            for (int i = 0; i < chkList.size(); i++) {
                Vector recLst1 = (Vector) chkList.get(i);
                String auth = recLst1.get(0).toString();
                if (auth.equalsIgnoreCase("Y")) {
                    alreadyAuth = "Y";
                }
            }

            if (alreadyAuth.equalsIgnoreCase("Y")) {
                throw new ApplicationException("You Can't Delete Authorized Details");
            }

            Query updateQuery = em.createNativeQuery("delete from tds_docdetail WHERE customerid='" + custId + "'");
            int var = updateQuery.executeUpdate();
            if (var <= 0) {
                throw new ApplicationException("Data could not be Deleted");
            }

            ut.commit();
            return "Record Is Deleted Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String verifyData(List<TdEntry> details, String custId, String userName, String tdsDoc) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {

            ut.begin();

            for (int i = 0; i < details.size(); i++) {
                String acno = details.get(i).getAccountNumber().toString();
                String vouchNo = details.get(i).getVchNo().toString();

                String actnature = ftsPosting.getAccountNature(acno);
                if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    Query updateQuery = em.createNativeQuery("update td_accountmaster set tdsflag = 'N',TDSDETAILS = '" + tdsDoc + "' where acno = '" + acno + "'");
                    int var = updateQuery.executeUpdate();
                    if (var <= 0) {
                        throw new ApplicationException("Data could not be Updated");
                    }

                    Query updateQuery1 = em.createNativeQuery("update td_vouchmst set TdsFlag = 'N' where ACNO = '" + acno + "' and VoucherNo = " + vouchNo + "");
                    int var1 = updateQuery1.executeUpdate();
                    if (var1 <= 0) {
                        throw new ApplicationException("Data could not be Updated");
                    }

                } else {
                    Query updateQuery = em.createNativeQuery("update accountmaster set tdsflag = 'N' where acno = '" + acno + "'");
                    int var1 = updateQuery.executeUpdate();
                    if (var1 <= 0) {
                        throw new ApplicationException("Data could not be Updated");
                    }
                }

                int seqNo = details.get(i).getSeqNumber();
                String dFlag = details.get(i).getrFlag().toString();

                List l = em.createNativeQuery("select docflag from tds_docdetail where seqNo = " + seqNo + " and customerid='" + custId + "'").getResultList();
                Vector v = (Vector) l.get(0);
                String dFlg = v.get(0).toString();
                Query updateQuery = em.createNativeQuery("update tds_docdetail set auth ='Y',authBy ='" + userName + "' WHERE seqNo = " + seqNo + " and customerid='" + custId + "'");
                int var = updateQuery.executeUpdate();
                if (var <= 0) {
                    throw new ApplicationException("Data could not be Updated");
                }

            }

            ut.commit();
            return "Record Verified Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String authFlagChk(String custId) throws ApplicationException {
        String authFlag = "N";
        List chkList = em.createNativeQuery("select auth from tds_docdetail where customerid = '" + custId + "'").getResultList();
        for (int i = 0; i < chkList.size(); i++) {
            Vector recLst1 = (Vector) chkList.get(i);
            String auth = recLst1.get(0).toString();
            if (auth.equalsIgnoreCase("Y")) {
                authFlag = "Y";
            }
        }
        return authFlag;
    }

    public List getDetailsList(String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select Distinct customerid from tds_docdetail where auth = 'N' and orgbrnid='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getTanNo(String brnCode) throws ApplicationException {
        try {
            List chkList = em.createNativeQuery("select TAXAcno from parameterinfo where brncode = '" + brnCode + "'").getResultList();
            if (chkList.isEmpty()) {
                throw new ApplicationException("Please Fill Tan No !");
            }
            Vector recLst1 = (Vector) chkList.get(0);
            String tanNo = recLst1.get(0).toString();
            return tanNo;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List customerValidateData(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(date_format(dateofbirth,'%d/%m/%Y'),''),ifnull(pan_girnumber,''),ifnull(nriflag,''),ifnull(mobilenumber,''),ifnull(peraddressline1,''),ifnull(perblock,''),ifnull(percitycode,''),ifnull(perstatecode,''),ifnull(percountrycode,''),ifnull(pervillage,''),ifnull(perphonenumber,''),ifnull(perpostalcode,''),ifnull(minorflag,''),ifnull(per_email,''),ifnull(PerDistrict,''),CustEntityType  from cbs_customer_master_detail where customerid = '" + custId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List tdsAcno(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno,VoucherNo from td_vouchmst where acno in(select acno from customerid where custid = '" + custId + "') "
                    + "and status = 'A' "
                    + "union "
                    + "select acno,'' from accountmaster where acno in(select acno from customerid where custid = '" + custId + "' "
                    + "and substring(acno,3,2) in(select acctCode from accounttypemaster where acctnature in('" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "'))) and tdsflag in ('Y') and AccStatus <> 9").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getTotalIntAmtDuringFinYear(String acno, String frDt, String toDt, float voucherNo) throws ApplicationException {
        try {
            List selectQueryintopt = em.createNativeQuery("SELECT intopt,date_format(fddt,'%Y%m%d'),date_format(matdt,'%Y%m%d'),roi,prinamt,"
                    + "date_format(NextIntPayDt,'%Y%m%d'), CumuPrinAmt,period,status FROM td_vouchmst WHERE acno='" + acno + "' and voucherno= " + voucherNo).getResultList();
            Vector intoptVec = (Vector) selectQueryintopt.get(0);
            String intopt = intoptVec.get(0).toString();
            String fddt = intoptVec.get(1).toString();
            String matdt = intoptVec.get(2).toString();
            String status = intoptVec.get(8).toString();
            String finMinDt = frDt;
            if (ymd.parse(frDt).before(ymd.parse(fddt))) {
                frDt = fddt;
            } else {
                frDt = frDt;
            }
            if (ymd.parse(toDt).after(ymd.parse(matdt))) {
                toDt = matdt;
            } else {
                toDt = toDt;
            }
            Float roi = Float.parseFloat(intoptVec.get(3).toString());
            double prinAmt = Double.parseDouble(intoptVec.get(4).toString());
            String nextIntPayDT = intoptVec.get(5).toString();

            double cumuPrinAmt = Double.parseDouble(intoptVec.get(6).toString());
            String pr = intoptVec.get(7).toString();
            long dtDiff = CbsUtil.dayDiff(ymd.parse(frDt), ymd.parse(toDt));
            List aRes = CbsUtil.getYrMonDayDiff(frDt, toDt);
            int prdY = Integer.parseInt(aRes.get(0).toString());
            int prdM = Integer.parseInt(aRes.get(1).toString());
            int prdD = Integer.parseInt(aRes.get(2).toString());           
           // String period = prdY + "Years" + prdM + "Months" + prdD + "Days";
            String period = 0 + "Years" + 0 + "Months" + dtDiff + "Days";
            String bAInt = "0";

            if (status.equalsIgnoreCase("C")) {
                List closedIntList = em.createNativeQuery("select concat(a.ACNO,cast(a.VoucherNo as unsigned)) AcnoVouchNo,sum(Interest),dt,status from td_vouchmst a,td_interesthistory b \n"
                        + "where ClDt > '" + finMinDt + "'  and a.acno = b.acno and a.VoucherNo = b.VoucherNo and a.acno = '" + acno + "' and a.VoucherNo = " + voucherNo + "\n"
                        + "and dt > '" + finMinDt + "' group by a.ACNO,a.VoucherNo order by a.ACNO,a.VoucherNo").getResultList();
                if (!closedIntList.isEmpty()) {
                    Vector vtr = (Vector) closedIntList.get(0);
                    bAInt = vtr.get(1).toString();
                }
            } else {
                if ((intopt.equalsIgnoreCase("S")) || (intopt.equalsIgnoreCase("Simple")) || (intopt.equalsIgnoreCase("Y"))) {
                    bAInt = tdRcptMgmtRemote.orgFDInterestSimple15gh(intopt, roi, frDt, toDt, prinAmt, period, acno.substring(0, 2));
                } else {
                    bAInt = tdRcptMgmtRemote.orgFDInterest(intopt, roi, frDt, toDt, cumuPrinAmt, period, acno.substring(0, 2));
                }
            }

            return bAInt;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<TdIntDetail> rdAcWiseIntCalc(String acNo, String fromDate, String toDate, String brCode) throws ApplicationException {
        try {
            List<TdIntDetail> rdIntList = new ArrayList<TdIntDetail>();
            List accountList = em.createNativeQuery("select ci.custid,a.acno,a.custname,date_format(a.RDmatdate,'%Y%m%d'),a.rdinstal,a.intDeposit,a.openingdt, "
                    + "a.accstatus from accountmaster a, customerid ci where  a.acno = ci.acno and a.acno='" + acNo + "' and "
                    + "a.curbrcode = '" + brCode + "' and (closingdate is null or closingdate between '" + fromDate + "' and '" + toDate + "' or closingdate = '')"
                    + "and a.tdsflag in ('Y') ").getResultList();

            TdIntDetail rdIntDetail;
            for (int j = 0; j < accountList.size(); j++) {
                Vector vect = (Vector) accountList.get(j);
                rdIntDetail = new TdIntDetail();

                rdIntDetail.setMsg("TRUE");

                rdIntDetail.setCustId(vect.get(0).toString());
                rdIntDetail.setAcno(vect.get(1).toString());
                rdIntDetail.setCustName(vect.get(2).toString());
                rdIntDetail.setVoucherNo(0);
                rdIntDetail.setIntOpt("Q");

                String rdMatDt = vect.get(3).toString();
                double rdInstall = Float.parseFloat(vect.get(4).toString());
                double roi = Float.parseFloat(vect.get(5).toString());
                String openingDt = vect.get(6).toString();
                int accStatus = Integer.parseInt(vect.get(7).toString());

                double interest = 0;
                if (accStatus == 9) {
                    List sbalList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from rdrecon where acno='" + vect.get(1).toString() + "' and dt "
                            + "between '" + fromDate + "' and '" + toDate + "' and trantype = 8").getResultList();
                    if (sbalList.size() > 0) {
                        Vector sbalVect = (Vector) sbalList.get(0);
                        interest = Float.parseFloat(sbalVect.get(0).toString());
                    }
                    if (interest > 0) {
                        rdIntDetail.setIntPaid(interest);
                        rdIntDetail.setStatus("C");
                        rdIntList.add(rdIntDetail);
                    }
                } else {
                    String frDt = fromDate;
                    String toDt = toDate;
                    if (ymd.parse(openingDt).getTime() > ymd.parse(fromDate).getTime()) {
                        frDt = openingDt;
                    }
                    if (ymd.parse(rdMatDt).getTime() < ymd.parse(toDate).getTime()) {
                        toDt = rdMatDt;
                    }

                    if (ymd.parse(frDt).getTime() > ymd.parse(rdMatDt).getTime()) {
                        frDt = openingDt;
                    }

                    long vDays = CbsUtil.dayDiff(ymd.parse(frDt), ymd.parse(toDt));
                    if (vDays < 91) {
                        interest = 0;
                    }
                    float ip = 4;
                    double i = roi / ip;
                    float c = ip / 12;

                    double f = Math.pow((1 + i / 100), c) - 1;
                    double rdPrin = rdInstall;
                    double a1 = (Math.pow((1 + f), CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt))) - 1) / f;
                    double a2 = a1 * (1 + f);

                    double matAmt = a2 * rdInstall;
                    interest = (matAmt - (rdPrin * CbsUtil.monthDiff(ymd.parse(frDt), ymd.parse(toDt))));
                    interest = CbsUtil.round(interest, 0);
                    if (interest > 0) {
                        rdIntDetail.setInterest(interest);
                        rdIntDetail.setStatus("A");
                        rdIntList.add(rdIntDetail);
                    }
                }
            }

            return rdIntList;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List form15G15HChecking(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from  tds_docdetail where customerid = '" + custId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List minorIdData(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct guardiancode from cbs_cust_minorinfo where guardiancode = '" + custId + "' "
                    + "union "
                    + "select customerid from cbs_cust_minorinfo where guardiancode = '" + custId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getcustEntityType(String custId) throws ApplicationException {
        try {
            return em.createNativeQuery("select ifnull(CustEntityType,'') from cbs_customer_master_detail where customerid = '" + custId + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public double getRdProjectedInt(String acNo, String fromDate, String toDate) throws ApplicationException {

        try {
            double rdInt1 = 0, rdInt2 = 0;
            List accList = em.createNativeQuery("select ci.custid,a.acno,a.custname,a.openingdt,date_format(a.RDmatdate,'%Y%m%d'),a.rdinstal,a.intDeposit, \n"
                    + "a.accstatus from accountmaster a, customerid ci where  a.acno = ci.acno and a.acno='" + acNo + "'and openingdt<='" + toDate + "' \n"
                    + "and (closingdate is null or closingdate = '' or closingdate > '" + fromDate + "') and rdinstal <> 0").getResultList();
            for (int j = 0; j < accList.size(); j++) {
                Vector vect = (Vector) accList.get(j);
                String acno = vect.get(1).toString();
                String rdOpeningDt = vect.get(3).toString();
                String rdMatDt = vect.get(4).toString();
                float rdInstall = Float.parseFloat(vect.get(5).toString());
                float roi = Float.parseFloat(vect.get(6).toString());
                int accStatus = Integer.parseInt(vect.get(7).toString());
                String frDt = fromDate;
                String toDt = toDate;
                double rdBal = 0;
                if (!ymd.parse(rdOpeningDt).after(ymd.parse(fromDate))) {
                    List balList = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from rdrecon where acno = '" + acno + "' and dt<'" + fromDate + "'").getResultList();
                    Vector vtr = (Vector) balList.get(0);
                    rdBal = Double.parseDouble(vtr.get(0).toString());
                }

                double rdBal1 = 0;
                rdBal1 = rdBal + rdInstall;

                if (ymd.parse(rdMatDt).getTime() <= ymd.parse(fromDate).getTime()) {
                    String intCode = ftsPosting.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");
                    List<SavingIntRateChangePojo> resultList = sbRemote.getSavingRoiChangeDetail(intCode, rdMatDt, toDate);
                    if (resultList.isEmpty()) {
                        throw new ApplicationException("There is no slab for saving interest calculation.");
                    }
                    for (int k = 0; k < resultList.size(); k++) {
                        SavingIntRateChangePojo obj = resultList.get(k);

                        String slabFrDt = obj.getFrDt();
                        String slabToDt = obj.getToDt();
                        double sbRoi = obj.getRoi();

                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                        double savingInterest = 0;
                        if (savingDiff > 0) {
                            savingInterest = sbRoi * savingDiff.doubleValue() * rdBal / 36500;
                        }
                        rdInt1 = rdInt1 + savingInterest;
                    }
                } else {
                    if (ymd.parse(rdOpeningDt).getTime() <= ymd.parse(fromDate).getTime()) {
                        if (ymd.parse(rdMatDt).getTime() >= ymd.parse(toDate).getTime()) {
                            Map<String, Integer> map = getAllMonthsn();
                            int v = map.get(CbsUtil.getMonthName(Integer.parseInt(toDate.substring(4, 6))));
                            //v = v - 1;  //if matdt < fin-end-dt then installment 1 month less as Hemant ji khattri, ex.Feb take jan
                            for (int i = 0; i < v; i++) {
                                double rdInt = 0;
                                if (i != 0) {
                                    rdBal1 = rdBal1 + rdInstall;
                                }
                                if (i != 3 && i != 6 && i != 9) {
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt2 + rdInt;
                                }
                                if (i == 3) {
                                    rdBal1 = rdBal1 + rdInt1;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt;
                                } else if (i == 6) {
                                    // rdBal1 = rdBal1 + rdInt1;
                                    rdBal1 = rdBal1 + rdInt2;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt;
                                } else if (i == 9) {
                                    //rdBal1 = rdBal1 + rdInt1;
                                    rdBal1 = rdBal1 + rdInt2;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                }
                                // System.out.println("==" + rdBal1 + "== " + rdInt + "== " + rdInt1);
                            }
                        } else {
                            Map<String, Integer> map = getAllMonthsn();
                            int v = map.get(CbsUtil.getMonthName(Integer.parseInt(rdMatDt.substring(4, 6))));
                            v = v - 1;  //if matdt < fin-end-dt then installment 1 month less as Hemant ji khattri, ex.Feb take jan
                            for (int i = 0; i < v; i++) {
                                double rdInt = 0;
                                if (i != 0) {
                                    rdBal1 = rdBal1 + rdInstall;
                                }
                                if (i != 3 && i != 6 && i != 9) {
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt2 + rdInt;
                                }
                                if (i == 3) {
                                    rdBal1 = rdBal1 + rdInt1;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt;
                                } else if (i == 6) {
                                    rdBal1 = rdBal1 + rdInt2;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                    rdInt2 = rdInt;
                                } else if (i == 9) {
                                    rdBal1 = rdBal1 + rdInt2;
                                    rdInt = CbsUtil.round(((rdBal1 * roi) / 1200), 0);
                                    rdInt1 = rdInt1 + rdInt;
                                }
                                // System.out.println("==" + rdBal1 + "== " + rdInt + "== " + rdInt1);
                            }
                        }
                    } else {
                        if (ymd.parse(rdMatDt).getTime() > ymd.parse(toDate).getTime()) {
                            toDt = toDate;
                        } else {
                            toDt = rdMatDt;
                        }

                        List rdIntList = rdIntCalProjected(rdOpeningDt, toDt, roi, rdInstall);
                        rdInt1 = Double.parseDouble(rdIntList.get(1).toString());
                    }
                }
            }
            return rdInt1;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List rdIntCalProjected(String openingDate, String matDt, Float netConRoi, Float install) throws ApplicationException {
        List resultList = new ArrayList();
        try {
            long vDays = CbsUtil.dayDiff(ymd.parse(openingDate), ymd.parse(matDt));
            long n = vDays / 91;
            double ip = 4d;
            double i = netConRoi / ip;
            double c = ip / 12;

            double f = Math.pow((1 + i / 100), c) - 1;
            double rdPrin = install;
            double a1 = (Math.pow((1 + f), CbsUtil.monthDiff(ymd.parse(openingDate), ymd.parse(matDt))) - 1) / f;
            double a2 = a1 * (1 + f);

            double matAmt = a2 * install;
            double interest = (matAmt - (rdPrin * CbsUtil.monthDiff(ymd.parse(openingDate), ymd.parse(matDt))));

            interest = CbsUtil.round(interest, 4);
            double n5 = (vDays / 30) - (n * 3);

            resultList.add(matAmt);
            resultList.add(interest);
            resultList.add(n5);
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public Map<String, Integer> getAllMonthsn() {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        map.put("April", 1);
        map.put("May", 2);
        map.put("June", 3);
        map.put("July", 4);
        map.put("August", 5);
        map.put("September", 6);
        map.put("October", 7);
        map.put("November", 8);
        map.put("December", 9);
        map.put("January", 10);
        map.put("February", 11);
        map.put("March", 12);
        return map;
    }

    @Override
    public String getAfterHolyDate() throws ApplicationException {
        try {
            List dtList = em.createNativeQuery("SELECT date_format(min(Date),'%Y%m%d') FROM cbs_bankdays where DAYBEGINFLAG = 'N' and DayEndFlag = 'N'").getResultList();
            Vector vtr = (Vector) dtList.get(0);
            return vtr.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List isHolyDays(String date) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT * FROM cbs_bankdays where DAYBEGINFLAG = 'H' and DayEndFlag = 'Y' and date = '" + date + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }
}
