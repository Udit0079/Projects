/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.dto.FdRenewalActOldDetail;
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
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "TdAuthorizationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class TdAuthorizationManagementFacade implements TdAuthorizationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    String tmpVouch;
    /* Start of FD Renew Authorization*/

//    public List accountNoList(String brCode) throws ApplicationException {
//        List acctNo = new ArrayList();
//        try {
//            List recList = em.createNativeQuery("select date from bankdays where dayendflag='N' AND brncode='" + brCode + "'").getResultList();
//            Vector recLst = (Vector) recList.get(0);
//            String tempBd = recLst.get(0).toString();
//            Query selectQuery = em.createNativeQuery("SELECT tdr.ACNO,VOUCHERNO,BATCHNO FROM td_renewal_auth tdr,td_accountmaster td WHERE VOUCHERNO IN "
//                    + "(SELECT MIN(VOUCHERNO) FROM td_renewal_auth tdra where auth='N' and ((ifnull(tdra.authby,'')='' or tdra.authby='') AND DATE_FORMAT(trantime,'%Y%m%d')='" 
//                    + tempBd + "') GROUP BY BATCHNO) AND auth='N' and (ifnull(tdr.authby,'')='' or tdr.authby='') AND DATE_FORMAT(trantime,'%Y%m%d')='" + tempBd 
//                    + "' and tdr.acno = td.ACNO and td.CurBrCode = '" + brCode + "' ORDER BY BATCHNO");
//            acctNo = selectQuery.getResultList();
//
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//        return acctNo;
//    }
    public List accountNoList(String brCode) throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select tdr.acno from td_renewal_auth tdr,td_accountmaster td where tdr.auth='N' "
                    + " and tdr.acno = td.ACNO and td.CurBrCode = '" + brCode + "'");
            acctNo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acctNo;
    }

    /*    Code by Nishant Kansal     */
    public List getOldAndNewData(String batNo, String brCode) throws ApplicationException {
        List dataList = new ArrayList();
        FdRenewalActOldDetail oldAndNewAccDetail;
        List<FdRenewalActOldDetail> oldDataList = new ArrayList<FdRenewalActOldDetail>();
        List<FdRenewalActOldDetail> newDataList = new ArrayList<FdRenewalActOldDetail>();
        try {
            List checkList = em.createNativeQuery("select tdr.acno,voucherno,auth,tdr.authby,enterby,balInterest,matAmt from td_renewal_auth tdr,td_accountmaster td where "
                    + "batchno=" + batNo + " and auth='N' and tdr.acno = td.ACNO and td.CurBrCode = '" + brCode + "' order by voucherno").getResultList();
            if (!checkList.isEmpty()) {
                for (int i = 0; i < checkList.size(); i++) {
                    Vector vecCheckList = (Vector) checkList.get(i);
                    String accNo = vecCheckList.get(0).toString();
                    String vochNo = vecCheckList.get(1).toString();
                    List dataList1 = em.createNativeQuery("select voucherno,receiptno,roi,date_format(fddt,'%d/%m/%Y') as fddt,date_format(matdt,'%d/%m/%Y') as matdt,"
                            + "prinamt,intopt,period,status,ifnull(finalamt,0),auth,enterby from td_vouchmst where acno='" + accNo + "' and voucherno='" + vochNo
                            + "' and status='C'").getResultList();
                    if (!dataList1.isEmpty()) {
                        Vector element1 = (Vector) dataList1.get(0);
                        oldAndNewAccDetail = new FdRenewalActOldDetail();
                        oldAndNewAccDetail.setVoucherNo(element1.get(0).toString());
                        oldAndNewAccDetail.setRecieptNo(Float.parseFloat(element1.get(1).toString()));
                        oldAndNewAccDetail.setRoi(Float.parseFloat(element1.get(2).toString()));
                        oldAndNewAccDetail.setFdDate(element1.get(3).toString());
                        oldAndNewAccDetail.setMatDate(element1.get(4).toString());
                        oldAndNewAccDetail.setPrinAmt(Float.parseFloat(element1.get(5).toString()));
                        oldAndNewAccDetail.setIntOpt(element1.get(6).toString());
                        oldAndNewAccDetail.setPeriod(element1.get(7).toString());
                        oldAndNewAccDetail.setStatus("Closed");
                        oldAndNewAccDetail.setTotalAmt(Float.parseFloat(vecCheckList.get(5).toString()));
                        oldAndNewAccDetail.setAuth(element1.get(10).toString());
                        oldAndNewAccDetail.setEnterBy(element1.get(11).toString());
                        oldDataList.add(oldAndNewAccDetail);
                    }
                    List dataList2 = em.createNativeQuery("select voucherno,receiptno,roi,date_format(fddt,'%d/%m/%Y') as fddt,date_format(matdt,'%d/%m/%Y') as matdt,"
                            + "prinamt,intopt,period,status,ifnull(finalamt,0),auth,enterby from td_vouchmst where acno='" + accNo + "' and voucherno='" + vochNo
                            + "' and status='A'").getResultList();
                    if (!dataList2.isEmpty()) {
                        Vector element2 = (Vector) dataList2.get(0);
                        oldAndNewAccDetail = new FdRenewalActOldDetail();
                        oldAndNewAccDetail.setVoucherNo(element2.get(0).toString());
                        oldAndNewAccDetail.setRecieptNo(Float.parseFloat(element2.get(1).toString()));
                        oldAndNewAccDetail.setRoi(Float.parseFloat(element2.get(2).toString()));
                        oldAndNewAccDetail.setFdDate(element2.get(3).toString());
                        oldAndNewAccDetail.setMatDate(element2.get(4).toString());
                        oldAndNewAccDetail.setPrinAmt(Float.parseFloat(element2.get(5).toString()));
                        oldAndNewAccDetail.setIntOpt(element2.get(6).toString());
                        oldAndNewAccDetail.setPeriod(element2.get(7).toString());
                        oldAndNewAccDetail.setStatus("Active");
                        oldAndNewAccDetail.setTotalAmt(Float.parseFloat(vecCheckList.get(6).toString()));
                        oldAndNewAccDetail.setAuth(element2.get(10).toString());
                        oldAndNewAccDetail.setEnterBy(element2.get(11).toString());
                        newDataList.add(oldAndNewAccDetail);
                    }
                }
                dataList.add(oldDataList);
                dataList.add(newDataList);

            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String authorizeAction(String authBy, String batNo, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query updateQuery = em.createNativeQuery("update td_renewal_auth tdr inner join td_accountmaster td on td.acno = tdr.acno set tdr.authby = '"
                    + authBy + "',tdr.auth='Y' where batchno = " + batNo + " and tdr.auth='N' and td.curbrcode = '" + brCode + "'");
            int var = updateQuery.executeUpdate();
            if ((var <= 0)) {
                throw new ApplicationException("Sorry, Authorization Not Completed.");
            }
            ut.commit();
            return "Authorized Successfully!!!";
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
    /* End of FD Renew Authorization*/

    /* Start of FD Duplicate Receipt Authorization*/
    public List gridLoadingDuplicateReceipt(String orgBrnCode) throws ApplicationException {
        List result = new ArrayList();
        try {

            Query q = em.createNativeQuery("select acno,VoucherNo,ROI,date_format(td_madedt,'%d/%m/%Y'),date_format(fddt,'%d/%m/%Y'),"
                    + "date_format(matdt,'%d/%m/%Y'),PrinAmt,IntOpt,receiptno,period,coalesce(ofacno,'') as ofacno,seqno,status,enterby,auth "
                    + "from td_vouchmst_duplicate  where  Auth = 'N'  and substring(acno,1,2)='" + orgBrnCode + "' order by acno");
            result = q.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public String authorizationChange(String acNo, float vchNo, String fddt, float receiptNo, float roi, String userName, String brnCode, String currentDate)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            double totalInt;
            ut.begin();

            String fddtDate = fddt.substring(6, 10) + fddt.substring(3, 5) + fddt.substring(0, 2);

            Float updateReceiptNo = 0f;
            String acNature = fts.getAccountNature(acNo);
            String value1 = "";
            String reVal = "";
            List reSeq = em.createNativeQuery("select ifnull(receiptno_seq,'') from td_parameterinfo ").getResultList();
            if (reSeq.isEmpty()) {
                throw new ApplicationException("Please fill TD Parameterinfo for Receipt Sequence");
            } else {
                Vector v4 = (Vector) reSeq.get(0);
                reVal = v4.get(0).toString();
                if (reVal.equalsIgnoreCase("C")) {
                    List result1 = em.createNativeQuery("select ifnull(min(receiptno),'') from td_receiptissue where status = 'F' and brncode='" + brnCode + "'").getResultList();
                    if (result1.isEmpty()) {
                        throw new ApplicationException("No New Receipt Avaliable For Issue");
                    } else {
                        Vector v3 = (Vector) result1.get(0);
                        value1 = v3.get(0).toString();
                        if (value1.equals("")) {
                            throw new ApplicationException("No New Receipt Avaliable For Issue");
                        }
                    }
                } else {
                    List result1 = em.createNativeQuery("select ifnull(min(receiptno),'') from td_receiptissue where scheme = '" + acNature + "' and status = 'F' and brncode='" + brnCode + "'").getResultList();
                    if (result1.isEmpty()) {
                        throw new ApplicationException("No New Receipt Avaliable For Issue");
                    } else {
                        Vector v3 = (Vector) result1.get(0);
                        value1 = v3.get(0).toString();
                        if (value1.equals("")) {
                            throw new ApplicationException("No New Receipt Avaliable For Issue");
                        }
                    }
                }
            }

            updateReceiptNo = Float.parseFloat(value1);

            Query vouch = em.createNativeQuery("select ifnull(cumuprinamt,0)  from td_vouchmst where ACNO = '" + acNo + "' and  receiptno =" + receiptNo + " and voucherno=" + vchNo + "");
            List result1 = vouch.getResultList();
            if (result1.isEmpty()) {
                throw new ApplicationException("Data does not exist in td_vouchmst");
            }
            Vector v4 = (Vector) result1.get(0);
            value1 = v4.get(0).toString();
            totalInt = Double.parseDouble(value1);

            Integer vouch1 = em.createNativeQuery("update td_vouchmst_duplicate set auth ='Y',authby ='" + userName + "' where  ACNO = '" + acNo
                    + "' and receiptno=" + receiptNo + " and voucherno=" + vchNo + "").executeUpdate();
            if (vouch1 <= 0) {
                throw new ApplicationException("Data does not updated");
            }
            Integer vouch2 = em.createNativeQuery("update td_vouchmst set receiptno=" + updateReceiptNo + ",status='A' where ACNO = '" + acNo
                    + "' and receiptno=" + receiptNo + " and voucherno=" + vchNo + "").executeUpdate();
            if (vouch2 <= 0) {
                throw new ApplicationException("Data does not updated");
            }

            if (reVal.equalsIgnoreCase("C")) {
                int uu = em.createNativeQuery("UPDATE td_receiptissue set STATUS = 'U',LASTUPDATEBY = '" + userName + "' ,LASTUPDATEDT = CURRENT_TIMESTAMP "
                        + "where receiptno = " + updateReceiptNo + " and status = 'F'").executeUpdate();
                if (uu <= 0) {
                    throw new ApplicationException("Data does not updated");
                }
            } else {
                int uu = em.createNativeQuery("UPDATE td_receiptissue set STATUS = 'U',LASTUPDATEBY = '" + userName + "' ,LASTUPDATEDT = CURRENT_TIMESTAMP "
                        + "where receiptno = " + updateReceiptNo + " and  scheme = '" + acNature + "' and status = 'F'").executeUpdate();
                if (uu <= 0) {
                    throw new ApplicationException("Data does not updated");
                }
            }
//
//            float trsNo = fts.getTrsNo();
//            float recNo = fts.getRecNo();
//
//            String details = "Duplicate Receipt" + updateReceiptNo;
//            Integer Insert = em.createNativeQuery("insert into  td_recon (acno,dt,valuedt,dramt,cramt,fddt,voucherno,ty,trantype,details,enterby,auth,"
//                    + "authby,trsno,recno,iy,payby,trandesc,org_brnid,dest_brnid) values('" + acNo + "','" + currentDate + "','" + currentDate + "',"
//                    + totalInt + ",0,'" + fddtDate + "'," + vchNo + ",1,2, '" + details + "','system','Y','" + userName + "'," + trsNo + "," + recNo
//                    + ",1,3,0,'" + brnCode + "','" + brnCode + "')").executeUpdate();
//            if (Insert <= 0) {
//                throw new ApplicationException("Data not updated");
//            }
//            recNo = fts.getRecNo();
//            Integer InsertRecon = em.createNativeQuery("insert into  td_recon (acno,dt,valuedt,cramt,dramt,fddt,voucherno,ty,trantype,details,enterby,"
//                    + "auth,authby,trsno,recno,iy,payby,trandesc,org_brnid,dest_brnid) values('" + acNo + "','" + currentDate + "','" + currentDate + "',"
//                    + totalInt + ",0,'" + fddtDate + "'," + vchNo + ",0,2, '" + details + "','system','Y','" + userName + "'," + trsNo + "," + recNo
//                    + ",1,3,0,'" + brnCode + "','" + brnCode + "')").executeUpdate();
//            if (InsertRecon <= 0) {
//                throw new ApplicationException("Data not updated");
//            }

            ut.commit();
            //return "true" + ":" + updateReceiptNo + ":" + trsNo;
            return "true" + ":" + updateReceiptNo;
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

    public List tableAuthorize(String orgnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Scheme, BookNo,Series,RecFrom,RecTo,Leaf,date_format(issuedt,'%Y%m%d'),enterby, Auth, SNo From td_receiptmaster where (auth='N' or auth is null) And brncode = '" + orgnCode + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public String tdIssueAuthorize(String userName, float stSNo, float stRNfrom, float stRNTo, String stBookNo, String series, String scheme, String orgnBrCode)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            float a = stRNfrom;
            float b = stRNTo;
            float tmpSno = 1;
            ut.begin();
            String result = "true";
            List checkInstNo = em.createNativeQuery("SELECT DATE_FORMAT(curdate(),'%Y%m%d')").getResultList();
            Vector ele = (Vector) checkInstNo.get(0);
            String currentDt = ele.get(0).toString();

            Integer updateparameter = em.createNativeQuery("UPDATE td_receiptmaster SET AuthBy = '" + userName + "' , auth='Y' WHERE sno=" + stSNo + " AND brncode = '" + orgnBrCode + "' and auth='N'").executeUpdate();
            if (updateparameter <= 0) {
                throw new ApplicationException("Either Data does not exit or entry already authorized.");
            }
            while (a <= b) {
                List tmpSnoList = em.createNativeQuery("SELECT MAX(sno) FROM td_receiptissue WHERE brncode = '" + orgnBrCode + "'").getResultList();
                if (!tmpSnoList.isEmpty()) {
                    Vector ele1 = (Vector) tmpSnoList.get(0);
                    if (ele1.get(0) != null) {
                        tmpSno = Float.parseFloat(ele1.get(0).toString());
                        tmpSno = tmpSno + 1;
                    }
                }
                Integer saveData = em.createNativeQuery("INSERT INTO td_receiptissue (Sno,Scheme,BookNo,Series,ReceiptNo,EnterBy,entryDt,trantime,status,lastUpdateBy,lastupdateDt,brncode)"
                        + " values(" + tmpSno + "  ,'" + scheme + "','" + stBookNo + "','" + series + "'," + a + ",'" + userName + "','" + currentDt + "',CURRENT_TIMESTAMP,'F','" + userName + "','" + currentDt + "','" + orgnBrCode + "')").executeUpdate();
                if (saveData <= 0) {
                    throw new ApplicationException("Data does not Save");
                }
                a = a + 1;
            }
            ut.commit();
            return result;
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

    public List getUnAuthRecptNo(String acNo) throws ApplicationException {
        try {
            String query = "select ReceiptNo from td_renewal_auth where auth='N' and acno = '" + acNo + "'";
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRenewedReceptDetails(String acNo, float rtNo) throws ApplicationException {
        try {

            String query = "select ACNO,date_format(renewedTdDt,'%d/%m/%Y'),date_format(renewedMatDt,'%d/%m/%Y'),TdsToBeDed,year,month,days,rtNoHide,IntOpt,tdDayMth,tdDayCum,renewalAmount,ReceiptNo,"
                    + "series,receiptnew,EnterBy,rAmt,balint,renewalAcc,renewedMatAmt,matpre,stYear,endYear,ROI,TDSDeductedLast,totalIntRateCal,TdsDed,"
                    + "ClActTdsToBeDeducted,ClActTdsDeducted,ClActInt,appRoiFrom from td_renewal_auth where auth='N' and acno = '" + acNo + "' "
                    + "and ReceiptNo = " + rtNo;
            List dataList = em.createNativeQuery(query).getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no pending authorization");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteTdRenewalDetails(String acNo, float rtNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String query = "UPDATE td_vouchmst SET Status='A',cldt=null where acno = '" + acNo + "' and voucherno = " + rtNo;
            int rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            query = "delete from td_renewal_auth where auth='N' and acno = '" + acNo + "' and rtNoHide = " + rtNo;
            rs = em.createNativeQuery(query).executeUpdate();
            if (rs <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "Receipt Detail successfully deleted";
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

    public String saveTdRenewalDetails(String acno, String renewedTdDt, String renewedMatDt, double TdsToBeDed, int year, int month, int days,
            double rtNoHide, String IntOpt, int tdDayMth, int tdDayCum, double renewalAmount, double ReceiptNo, String series, String receiptnew,
            String EnterBy, double rAmt, double balint, String renewalAcc, double renewedMatAmt, double matpre, String stYear, String endYear, double ROI,
            double TDSDeductedLast, double totalIntRateCal, double TdsDed, double ClActTdsToBeDeducted, double ClActTdsDeducted,
            double ClActInt, String appRoiFrom, String brCode) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List dtList = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) dtList.get(0);
            String curDt = tempCurrent.get(0).toString();


            List finlamtList = em.createNativeQuery("SELECT DISTINCT acno,prinamt,voucherno,FDDt,status,matdt,IntOpt,roi,cldt,finalAmt,period,"
                    + "IntToAcNo,OFFlag,OFAcno,lien from td_vouchmst where status='A' and voucherNo='" + rtNoHide + "' and acno='" + acno
                    + "' UNION SELECT DISTINCT vm.acno,vm.prinamt,vm.voucherno,vm.FDDt,vm.status,vm.matdt,vm.IntOpt,vm.roi,vm.cldt,"
                    + "vm.finalAmt,vm.period,vm.IntToAcNo,vm.OFFlag,vm.OFAcno,lien from td_vouchmst vm where OFFlag='Y' and vm.status='C' "
                    + "and vm.voucherNo='" + rtNoHide + "' and vm.acno='" + acno + "'").getResultList();

            if (finlamtList.size() <= 0) {
                throw new ApplicationException("Data Not exist in Td vouch master");
            }
            Vector tdVchList = (Vector) finlamtList.get(0);
            String lien = "";
            if (tdVchList.get(14) != null) {
                lien = tdVchList.get(14).toString();
            }

            if (lien.equalsIgnoreCase("Y") && fts.getCodeForReportName("LIEN-FD-RENEW") != 1) {
                throw new ApplicationException("This receipt is Lien Marked");
            }

            Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET Status='C',cldt='" + curDt + "' WHERE ACNO='" + acno + "' "
                    + "and receiptNo=" + ReceiptNo + " and voucherno= " + rtNoHide + "");
            int varQ1 = updateQ1.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            String query = "INSERT INTO td_renewal_auth(ACNO,renewedTdDt,renewedMatDt,TdsToBeDed,year,month,days,rtNoHide,IntOpt,tdDayMth,tdDayCum,"
                    + "renewalAmount,ReceiptNo,series,receiptnew,EnterBy,rAmt,balint,renewalAcc,renewedMatAmt,matpre,stYear,endYear,ROI,TDSDeductedLast,"
                    + "totalIntRateCal,TdsDed,ClActTdsToBeDeducted,ClActTdsDeducted,ClActInt,appRoiFrom,Auth,TranTime) "
                    + " VALUES('" + acno + "','" + renewedTdDt + "','" + renewedMatDt + "'," + TdsToBeDed + "," + year + "," + month + "," + days + "," + rtNoHide + ","
                    + "'" + IntOpt + "'," + tdDayMth + "," + tdDayCum + "," + renewalAmount + "," + ReceiptNo + ",'" + series + "','" + receiptnew + "',"
                    + "'" + EnterBy + "'," + rAmt + "," + balint + ",'" + renewalAcc + "'," + renewedMatAmt + "," + matpre + ",'" + stYear + "','" + endYear + "',"
                    + "" + ROI + "," + TDSDeductedLast + "," + totalIntRateCal + "," + TdsDed + "," + ClActTdsToBeDeducted + "," + ClActTdsDeducted + "," + ClActInt + ","
                    + "'" + appRoiFrom + "','N',now())";
            Query insertQuery = em.createNativeQuery(query);
            varQ1 = insertQuery.executeUpdate();
            if (varQ1 <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            ut.commit();
            return "Data successfully saved";
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
}
