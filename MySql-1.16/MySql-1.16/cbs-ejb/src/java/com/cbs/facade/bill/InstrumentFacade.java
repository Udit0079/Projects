/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.bill;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.EJBException;
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
 * @author admin Modify By Dhirendra singh
 */
@Stateless(mappedName = "InstrumentFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InstrumentFacade implements InstrumentFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fTSPosting43CBSBean;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    public List getDropDownValuesInstrumentActive() throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct InstCode From billtypemaster where InstNature <> 'AD'");
            acctNo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acctNo;
    }

    public List tableDataInstrumentActive(String billType, String instNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select sno,bookno,seqno,instno,billtype,amount,"
                    + "date_format(dt,'%d/%m/%Y') as dt,branchcode,infavourof,issuebr,reportingbr,draweebr,date_format(origindt,'%d/%m/%Y') as origindt,"
                    + "date_format(lostdt,'%d/%m/%Y') as lostdt,circularno,date_format(circulardt,'%d/%m/%Y') as circulardt,cautionmark,enterby "
                    + "From bill_lost  WHERE  BILLTYPE='" + billType + "' and instno='" + instNo + "' order by sno");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public String instrumentActivate(String instNo, int srNo, String authBy, String billType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List billLost = em.createNativeQuery("Select * From bill_lost Where instno='" + instNo + "' and billtype='" + billType + "' AND sno=" + srNo).getResultList();
            if ((billLost.size() <= 0)) {
                throw new ApplicationException("Instrument Does Not Exist");
            }
            Query updateBillLost = em.createNativeQuery("Update bill_lost set updatedtrantime = CURRENT_TIMESTAMP,UpdatedBy=UPPER('" + authBy + "') where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varUpdateBillLost = updateBillLost.executeUpdate();
            if (varUpdateBillLost <= 0) {
                throw new ApplicationException("Instrument is not updated in Bill Lost");
            }
            Query insertBillLostHist = em.createNativeQuery("insert into bill_lost_hist select * from bill_lost where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varinsertBillLostHist = insertBillLostHist.executeUpdate();
            if (varinsertBillLostHist <= 0) {
                throw new ApplicationException("Instrument is not inserted in Bill Lost History");
            }
            Query deleteBillLost = em.createNativeQuery("Update bill_lost set status='A', auth='N', authby='' where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varDeleteBillLost = deleteBillLost.executeUpdate();
            if (varDeleteBillLost <= 0) {
                throw new ApplicationException("Instrument is not deleted from Bill Lost");
            }
            ut.commit();
            return "Instrument Activated Successfully";

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
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String instrumentDeletionInstrumentActive(int instrumentNo, int srNo, String dt, String authBy, String billType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertBillLostHist = em.createNativeQuery("insert into bill_lost_hist select * from bill_lost where sno=" + srNo + " and instno=" + instrumentNo + " and billtype='" + billType + "'");
            Integer varinsertBillLostHist = insertBillLostHist.executeUpdate();
            if (varinsertBillLostHist <= 0) {
                throw new ApplicationException("Instrument is not inserted in Bill Lost History");
            }
            Query deleteBillLost = em.createNativeQuery("delete from bill_lost where sno=" + srNo + " and instno=" + instrumentNo + " and billtype='" + billType + "'");
            Integer varDeleteBillLost = deleteBillLost.executeUpdate();
            if (varDeleteBillLost <= 0) {
                throw new ApplicationException("Instrument is not deleted from Bill Lost");
            }
            ut.commit();
            return "Instrument Deleted Successfully";

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
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /**
     * Start Instrument lost register bean
     *
     */
    public List branchCodeDropDown() throws ApplicationException {
        List brcode = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct AlphaCode From branchmaster where BranchName is not null");
            brcode = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return brcode;
    }

    public List billtypeDropDown() throws ApplicationException {
        List bilType = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct InstCode From billtypemaster where InstNature <> 'AD'");
            bilType = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return bilType;
    }

    public List tableDataInstrumentLost(String billType, String status, String orgBrnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            if (status.equals("L")) {
                tableResult = em.createNativeQuery("Select sno,bookno,seqno,instno,billtype, amount,date_format(dt,'%d/%m/%Y') as dt,"
                        + "branchcode,infavourof,issuebr,reportingbr,draweebr,date_format(origindt,'%d/%m/%Y') as origindt,"
                        + "date_format(lostdt,'%d/%m/%Y') as lostdt,circularno, date_format(circulardt,'%d/%m/%Y') as circulardt,enterby,status,auth "
                        + "From bill_lost WHERE BILLTYPE='" + billType + "' and auth='Y' and status='L' and branchcode='" + orgBrnCode + "' order by sno").getResultList();
            } else if (status.equals("A")) {
                tableResult = em.createNativeQuery("Select sno,bookno,seqno,instno,billtype, amount,date_format(dt,'%d/%m/%Y') as dt,"
                        + "branchcode,infavourof,issuebr,reportingbr,draweebr, date_format(origindt,'%d/%m/%Y') as origindt,"
                        + "date_format(lostdt,'%d/%m/%Y') as lostdt,circularno, date_format(circulardt,'%d/%m/%Y') as circulardt,enterby,status,auth "
                        + "From bill_lost WHERE BILLTYPE='" + billType + "' and auth='Y' and status='L' and branchcode='" + orgBrnCode + "' order by sno").getResultList();
            } else if (status.equals("E")) {
                tableResult = em.createNativeQuery("Select sno,bookno,seqno,instno,billtype, amount,date_format(dt,'%d/%m/%Y') as dt,"
                        + "branchcode,infavourof,issuebr,reportingbr,draweebr, date_format(origindt,'%d/%m/%Y') as origindt,"
                        + "date_format(lostdt,'%d/%m/%Y') as lostdt,circularno, date_format(circulardt,'%d/%m/%Y') as circulardt,enterby,status,auth "
                        + "From bill_lost WHERE BILLTYPE='" + billType + "' and auth='N' and branchcode='" + orgBrnCode + "' order by sno").getResultList();
            } else {
                tableResult = em.createNativeQuery("Select sno,bookno,seqno,instno,billtype, amount,date_format(dt,'%d/%m/%Y') as dt,"
                        + "branchcode,infavourof,issuebr,reportingbr,draweebr, date_format(origindt,'%d/%m/%Y') as origindt,"
                        + "date_format(lostdt,'%d/%m/%Y') as lostdt,circularno, date_format(circulardt,'%d/%m/%Y') as circulardt,enterby,status,auth "
                        + "From bill_lost WHERE BILLTYPE='" + billType + "' and auth='N' and branchcode='" + orgBrnCode + "' order by sno").getResultList();
            }


        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public String tmpbdInstrumentLost() throws ApplicationException {
        String tempBd = new String();
        try {
            Query selectQuery = em.createNativeQuery("select min(date) as tbldate from bankdays where dayendflag='N'");
            if (selectQuery.getResultList().size() > 0) {
                Vector qq = (Vector) selectQuery.getSingleResult();
                tempBd = (String) qq.get(0);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tempBd;
    }

    public String instLostDeletion(String instNo, int srNo, String authBy, String billType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List billLost = em.createNativeQuery("Select * From bill_lost Where instno='" + instNo + "' and billtype='" + billType + "' AND sno=" + srNo).getResultList();
            if ((billLost.size() <= 0)) {
                throw new ApplicationException("Instrument Does Not Exist");
            }
            Query updateBillLost = em.createNativeQuery("Update bill_lost set status='D', updatedtrantime = CURRENT_TIMESTAMP,UpdatedBy=UPPER('" + authBy + "') where sno=" + srNo + " and instno=" + instNo + " and billtype='" + billType + "'");
            Integer varUpdateBillLost = updateBillLost.executeUpdate();
            if (varUpdateBillLost <= 0) {
                throw new ApplicationException("Instrument is not updated in Bill Lost");
            }
            Query insertBillLostHist = em.createNativeQuery("insert into bill_lost_hist select * from bill_lost where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varinsertBillLostHist = insertBillLostHist.executeUpdate();
            if (varinsertBillLostHist <= 0) {
                throw new ApplicationException("Instrument is not inserted in Bill Lost History");
            }
            Query deleteBillLost = em.createNativeQuery("delete from bill_lost where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varDeleteBillLost = deleteBillLost.executeUpdate();
            if (varDeleteBillLost <= 0) {
                throw new ApplicationException("Instrument is not deleted from Bill Lost");
            }
            ut.commit();
            return "Data Deleted Successfully";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String instLostSaveUpdate(String command, String billType, String instNo, float seqNo, float amt, String infavourof,
            String issueBrCode, String reportBrCode, String draweeBrCode, String dtOrigin, String dtLoss, String circular, String authBy,
            int srNo, String dt, String dtCircular, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            /**
             * **************CHECKING THE DAYBEGIN DATE ACCORDING ISSUED BRANCH
             * CODE*****************
             */
            String tempBd = daybeginDate(orgnBrCode);

            /**
             * **************COMPAIR THE DAYBEGIN DATE AND TODAY
             * DATE*************************************
             */
            if (ymd.parse(tempBd).after(ymd.parse(dt)) || ymd.parse(tempBd).before(ymd.parse(dt))) {
                throw new ApplicationException("Check the today date you have passed, Day Begin Date is " + dmy.format(ymd.parse(tempBd.toString())));
            }
            String billTable = getBillTable(billType);

            List checkInstNo = em.createNativeQuery("select instno from " + billTable + " WHERE INSTNO='" + instNo + "' AND STATUS='PAID' and seqno="
                    + seqNo + " and substring(acno,1,2)=" + orgnBrCode).getResultList();
            if (checkInstNo.size() > 0) {
                return "Instrument already Paid.";
            }

            if (command.equalsIgnoreCase("save")) {
                List checkInBillLost = em.createNativeQuery("select instno from  bill_lost a, branchmaster b WHERE a.INSTNO='" + instNo
                        + "' AND a.seqno=" + seqNo + " and a.billtype='" + billType + "' and a.issueBr = '"
                        + issueBrCode + "' and b.brnCode = " + orgnBrCode + " and b.alphaCode = a.IssueBr").getResultList();
                if (checkInBillLost.size() > 0) {
                    throw new ApplicationException("Instrument Already Lost Marked.");
                }

                Query insertBillLost = em.createNativeQuery("insert into bill_lost(BookNo,SeqNo,InstNo, BillType,Amount,Dt,BranchCode,Infavourof, "
                        + "IssueBr,ReportingBr,DraweeBr,OriginDt,LostDt,CircularNo,CircularDt,auth,EnterBy,TranTime,STATUS,authby)"
                        + " values('" + 0 + "'," + seqNo + ",'" + instNo + "','" + billType + "'," + amt + ",'" + tempBd + "','" + orgnBrCode + "','"
                        + infavourof + "','" + issueBrCode + "','" + reportBrCode + "','" + draweeBrCode + "','" + dtOrigin + "','" + dtLoss + "','"
                        + circular + "','" + dtCircular + "','N','" + authBy.toUpperCase() + "',CURRENT_TIMESTAMP,'L','')");
                Integer insertBillLostVarient = insertBillLost.executeUpdate();
                if (insertBillLostVarient <= 0) {
                    throw new ApplicationException("Data could not be Inserted !!! Please fill the right instrument no.");
                }
                ut.commit();
                return "Record Save Successfully";
            } else {
                List selectQuery = em.createNativeQuery("Select * From bill_lost Where Sno=" + srNo + " and dt='" + tempBd + "'").getResultList();
                if (selectQuery.size() <= 0) {
                    throw new ApplicationException("Data does not exist");
                }

                Query updateBillLost = em.createNativeQuery("Update bill_lost set Bookno='" + 0 + "',SeqNo=" + seqNo + ",InstNo='" + instNo
                        + "',billtype='" + billType + "', Amount=" + amt + ",InfavourOf='" + infavourof + "',IssueBr='" + issueBrCode + "'"
                        + ",ReportingBr='" + reportBrCode + "',DraweeBr='" + draweeBrCode + "',OriginDt='" + dtOrigin + "'"
                        + ",LostDt='" + dtLoss + "',CircularNo='" + circular + "',CircularDt='" + dtOrigin + "',Enterby='" + authBy + "' where sno="
                        + srNo + " and branchcode = '" + orgnBrCode + "'");
                Integer updateBillLostVarient = updateBillLost.executeUpdate();
                if (updateBillLostVarient <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
                ut.commit();
                return "Data updated Successfully.";
            }

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
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String instVerified(String instNo, int srNo, String authBy, String billType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List billLost = em.createNativeQuery("Select * From bill_lost Where instno='" + instNo + "' and billtype='" + billType + "' AND sno=" + srNo).getResultList();
            if ((billLost.size() <= 0)) {
                throw new ApplicationException("Instrument Does Not Exist");
            }
            Query deleteBillLost = em.createNativeQuery("Update bill_lost set auth='Y', authby='" + authBy + "' where sno=" + srNo + " and instno='" + instNo + "' and billtype='" + billType + "'");
            Integer varDeleteBillLost = deleteBillLost.executeUpdate();
            if (varDeleteBillLost <= 0) {
                throw new ApplicationException("Instrument is not deleted from Bill Lost");
            }
            ut.commit();
            return "Instrument Verified Successfully";

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
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    private String getBillTable(String billType) throws ApplicationException {
        try {
            String billTable = "";
            if (billType.toUpperCase().equals("DD")) {
                billTable = "bill_dd";
            } else if (billType.toUpperCase().equals("PO")) {
                billTable = "bill_po";
            } else if (billType.toUpperCase().equals("TPO")) {
                billTable = "bill_tpo";
            }
            return billTable;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }


    }

    public List getInstDetails(String billType, String instNo, String originDt, String issueBr) throws ApplicationException {
        try {
            List brList = em.createNativeQuery("Select brncode From branchmaster where alphacode ='" + issueBr + "'").getResultList();
            if (brList.isEmpty()) {
                throw new ApplicationException("Data does not exists");
            }
            Vector v = (Vector) brList.get(0);
            String brCode = CbsUtil.lPadding(2, Integer.parseInt(v.get(0).toString()));
            String billTable = getBillTable(billType);
            List list = em.createNativeQuery("select seqno, amount,infavourof,payableat from " + billTable + " WHERE INSTNO='" + instNo + "' AND STATUS='ISSUED' and origindt='"
                    + originDt + "' and substring(acno,1,2)='" + brCode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String daybeginDate(String orgnBrCode) throws ApplicationException {
        try {
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                throw new ApplicationException("Check the Day Begin/Day End");
            }
            Vector dateVect = (Vector) dateList.get(0);
            return dateVect.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ******************************************start the code
     * ObcRealizationBean ***************************************************
     */
    public List fYear(String brCode) throws ApplicationException {
        List yearResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct FYear From bill_ibcbooking where substring(acno,1,2)='" + brCode + "' and FYear is not null ");
            yearResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return yearResult;
    }

    public List alphaCode(String brCode) throws ApplicationException {
        List alphaCodeResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct AlphaCode From branchmaster where BranchName is not null");
            alphaCodeResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return alphaCodeResult;
    }

    public List reasonForCancel() throws ApplicationException {
        List reasonResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select Distinct Description From codebook Where GroupCode=13 and code<>0");
            reasonResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return reasonResult;
    }

    public List LoadGridBookedData(String brCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select BillNo,BillType,RemType,Fyear,subString (Acno,3,8) As Acno,InstNo,InstAmount,Comm,Postage,date_format(InstDt,'%d/%m/%Y') AS InstDt,BankName,BankAddr,EnterBy From bill_ibcbooking where substring(acno,1,2)='" + brCode + "' and billtype='BP' and billno is not null order by billType,billno,fyear");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tableResult;
    }

    public String dishonorClick(String instNo, String instDt, double instAmt, String billType, float billNo, int fYear, String panelReason, double ourCharges, double amtDebited, double retCharges, String resonCancel, String accountNo, String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String s = null;
        try {
            ut.begin();
            Float TrsNo;
            String alphaCode;
            float tmpDpLimit;
            int exec;
            String msg = null;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE brncode='" + BRCODE + "'and DAYENDFLAG='N'  ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);
            if ((instNo == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return s = "Please Check Instruement details";
            }
            List select = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage From bill_ibcbooking Where substring(acno,1,2)='" + BRCODE + "' and BillNo = " + billNo + " And FYear=" + fYear + " and billtype='" + billType + "'").getResultList();
            if (select.isEmpty()) {
                ut.rollback();
                return s = "Please Check Instruement details";
            }
            String fd = fieldCheck(billType, billNo, fYear, panelReason, ourCharges, amtDebited, instAmt, retCharges, resonCancel);
            if (!fd.equals("False")) {
                String[] values = null;
                String spliter = ": ";
                values = fd.split(spliter);
                String b = (values[0]);
                String a = (values[1]);
                if (a.equals("True")) {
                    ut.rollback();
                    return s = "" + b;
                }
            }
            TrsNo = fTSPosting43CBSBean.getTrsNo();

            List alphaCde = em.createNativeQuery("Select AlphaCode From bill_ibcbooking where substring(acno,1,2)='" + BRCODE + "' and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'").getResultList();
            Vector alphaCd = (Vector) alphaCde.get(0);
            String alphaCodes = alphaCd.get(0).toString();

            if (alphaCde.isEmpty()) {
                alphaCode = "0";
            } else {
                alphaCode = alphaCodes;
            }

            tmpDpLimit = 0;
            List odLimit = em.createNativeQuery("Select coalesce(odlimit,0) From loan_appparameter where BrCode='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
            if (!odLimit.isEmpty()) {
                Vector oLimits = (Vector) odLimit.get(0);
                float odLimits = Float.parseFloat(oLimits.get(0).toString());
                if (odLimit.isEmpty()) {
                    tmpDpLimit = odLimits;
                }
            }

            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            float RecNo = fTSPosting43CBSBean.getRecNo();

            String ab = fTSPosting43CBSBean.checkBalance(accountNo, ourCharges, user);
            if (!ab.equals("True")) {
                ut.rollback();
                return s = "" + ab;
            }
            String pro = procDishonor(retCharges, accountNo, instNo, billNo, fYear, BRCODE, user, instDt);
            if (!pro.equals("True")) {
                ut.rollback();
                return s = pro;
            }
            Query insertQuery = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,RetCharges,ReasonforCancel,InFavourOf,InstDt,InstEntryDt,Dt,status,EnterBy,TrsNo,RecNo,refno) Select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + retCharges + ",'" + resonCancel + "',InFavourOf,InstDt,Dt,'" + Tempbd + "','D','" + user + "'," + TrsNo + "," + RecNo + ",refno from bill_ibcbooking where substring(acno,1,2)='" + BRCODE + "' and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
            Integer varint = insertQuery.executeUpdate();

            Query updateQuery = em.createNativeQuery("Delete From bill_ibcbooking Where substring(acno,1,2)='" + BRCODE + "' and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
            int var = updateQuery.executeUpdate();

            if ((varint > 0) || (var > 0)) {
                ut.commit();
                //ut.rollback();
                return s = "Dishonor Is Successfull";
            } else {
                ut.rollback();
                return s = "Dishonor could not be Successfull";
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String procDishonor(double retCharges, String accountNo, String instNo, float billNo, int fYear, String BRCODE, String user, String instDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int exec = 0;
            int exec1 = 0;
            String CHK_MSG = null;
            String MSG = null;
            float trsNumber;
            float TOKENNO = 0.0f;
            float TRSNO1 = 0.0f;
            String detail = "VCH : Charges For BPNo: " + "" + billNo + " / " + " " + fYear;
            String details = "VCH : Charges For BCNo: " + "" + billNo + " / " + " " + fYear + " For Acno: " + accountNo;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);

            trsNumber = fTSPosting43CBSBean.getTrsNo();
            List checkList = null;
            String GLChequeRet = "";

            checkList = em.createNativeQuery("select name from sysobjects where name='bill_ibcobcglmast' and xtype='u'").getResultList();
            if (checkList.isEmpty()) {
                GLChequeRet = BRCODE + "GL00261501";
            } else {
                checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
                if (checkList.isEmpty()) {
                    GLChequeRet = BRCODE + "GL00261501";
                }
            }
            if (retCharges > 0) {
                float RECNO = 0;
                float TOKENNO_Dr = 0;
                String checkListD1 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 1, retCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", 3, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    //ut.rollback();
                    return checkListD1;
                }
                String checkListD2 = fTSPosting43CBSBean.ftsPosting43CBS(GLChequeRet, 2, 0, retCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", 3, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    // ut.rollback();
                    return checkListD2;
                }

            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String accountValidation(String accountNo, float instAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String accountFlag = "False";
            List accStatus;
            Integer optStatus = null;
            Integer accSts;
            Float amounts;
            List balance;
            String a = "False";
            Integer tmpOptStatus = 1;
            String nature = fTSPosting43CBSBean.getAccountCode(accountNo);
            String BRCODE = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNature.equals(CbsConstant.MS_AC))) {
                // accStatus = em.createNativeQuery("select accstatus from TD_AccountMaster where substring(acno,1,2)='" + BRCODE + "' and acno = '" + accountNo + "'").getResultList();
                accStatus = em.createNativeQuery("select accstatus from td_accountmaster where CurBrCode='" + BRCODE + "' and acno = '" + accountNo + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
            } else {
                // accStatus = em.createNativeQuery("select accstatus,coalesce(optstatus,'1') from Accountmaster where substring(acno,1,2)='" + BRCODE + "' and acno ='" + accountNo + "'").getResultList();
                accStatus = em.createNativeQuery("select accstatus,coalesce(optstatus,'1') from accountmaster where CurBrCode='" + BRCODE + "' and acno ='" + accountNo + "'").getResultList();
                Vector acStatus = (Vector) accStatus.get(0);
                accSts = Integer.parseInt(acStatus.get(0).toString());
                String otStatus = acStatus.get(1).toString();
                optStatus = Integer.parseInt(otStatus);
            }
            if (!accStatus.isEmpty()) {
                if (!acNature.equals(CbsConstant.FIXED_AC) && (!acNature.equals(CbsConstant.MS_AC))) {
                    tmpOptStatus = optStatus;
                }

                if ((accSts == 1) && (tmpOptStatus == 2)) {
                    ut.rollback();
                    accountFlag = "True";
                    return a = "Account Is Inoperative And Kindly Dishonor This Inst." + ": " + accountFlag;
                } else if ((accSts != 1) && (accSts != 10)) {
                    List discription = em.createNativeQuery("select description from codebook where groupcode = 3 and code =" + accSts + " ").getResultList();
                    if (!discription.isEmpty()) {
                        ut.rollback();
                        accountFlag = "True";
                        return a = "Account is Marked as And Kindly Dishonor This Inst." + ": " + accountFlag;
                    }
                } else if (accSts == 10) {
                    List amount = em.createNativeQuery("select (ifnull(amount,0)) AS amount from accountstatus where  acno ='" + accountNo + "' and spno = (select max(spno) from accountstatus a where a.acno ='" + accountNo + "' and  auth = 'Y')").getResultList();
                    //  List amount = em.createNativeQuery("select (ifnull(amount,0)) AS amount from accountstatus where substring(acno,1,2)='" + BRCODE + "' and acno ='" + accountNo + "' and spno = (select max(spno) from accountstatus a where substring(a.acno,1,2)='" + BRCODE + "' and  a.acno ='" + accountNo + "' and  auth = 'Y')").getResultList();
                    Vector amt = (Vector) amount.get(0);
                    amounts = Float.parseFloat(amt.get(0).toString());

                    String ABC = "Account Has been Lien Marked for amount of Rs. " + amounts;

                    if ((acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER))) {
                        //  balance = em.createNativeQuery("select balance from RECONBALAN  where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                        balance = em.createNativeQuery("select balance from reconbalan  where acno='" + accountNo + "'").getResultList();
                    } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || (acNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                        balance = em.createNativeQuery("select balance from td_reconbalan where  acno='" + accountNo + "'").getResultList();
                        // balance = em.createNativeQuery("select balance from TD_RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                    } //else if (acNature.equals("CA") || (acNature.equals("CC")) || (acNature.equals("OD"))) {
                    else if (acNature.equals(CbsConstant.CURRENT_AC)) {
                        balance = em.createNativeQuery("select balance from ca_reconbalan where  acno='" + accountNo + "'").getResultList();
                        // balance = em.createNativeQuery("select balance from CA_RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                    } else {
                        // balance = em.createNativeQuery("select balance from RECONBALAN where substring(acno,1,2)='" + BRCODE + "' and acno='" + accountNo + "'").getResultList();
                        balance = em.createNativeQuery("select balance from reconbalan where acno='" + accountNo + "'").getResultList();
                    }

                    Vector bl = (Vector) balance.get(0);
                    Float balances = Float.parseFloat(bl.get(0).toString());

                    if (balance.isEmpty()) {
                        if (amounts >= (balances - instAmt)) {
                            ut.rollback();
                            accountFlag = "True";
                            return a = "Please Dishonor This Instrument " + ": " + ABC + ": " + accountFlag;
                        }
                    }
                    //return a="Account Has been Lien Marked for amount of Rs. "+amounts;
                }
            }

            ut.commit();
            return "" + a;

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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String accountValidationResult(String accountNo, String instNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            // String nature = accountNo.substring(2, 4);
            String BRCODE = fTSPosting43CBSBean.getCurrentBrnCode(accountNo);
//            List accNature = em.createNativeQuery("select acctNature from accountTypeMaster where Acctcode='" + nature + "'").getResultList();
//            Vector accNatures = (Vector) accNature.get(0);
//            String acNature = accNatures.get(0).toString();
            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                //  List allResult = em.createNativeQuery("select Acno,statusflag from chbook_" + acNature + " where substring(acno,1,2)='" + BRCODE + "' and acno = '" + accountNo + "' and Chqno ='" + instNo + "'").getResultList();
                List allResult = em.createNativeQuery("select Acno,statusflag from chbook_" + acNature + " where acno = '" + accountNo + "' and Chqno ='" + instNo + "'").getResultList();
                if (allResult.isEmpty()) {
                    ut.rollback();
                    return "Instrument is Not Issued";
                } else {

                    if (!allResult.isEmpty()) {
                        Vector alR = (Vector) allResult.get(0);
                        String statusFlag = alR.get(1).toString();
                        if (statusFlag.equals("S")) {
                            ut.rollback();
                            return "Cheque is Marked as Stop Payment And Kindly Dishonor This Inst. ";
                        }
                    }
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
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String procPass(String billType, double ourCharges, String accountNo, String instNo, float billNo, int fYear,
            double AmtToDebited, int payBy, double instAmt, String BRCODE, String user, String instDt) throws ApplicationException {
        //UserTransaction ut = context.getUserTransaction();
        try {

            int execX = 0;
            int execY = 0;
            int execZ = 0;
            String CHK_MSG = null;
            String MSG = null;
            float trsNumber;
            float TOKENNO = 0.0f;
            float TRSNO1 = 0.0f;
            String pFlag = "False";


            List checkList = null;
            String GLHO = "";
            String GLBPIntRec = "";
            String GLBCComm = "";
            String GLBillCol = "";
            String GLBillPay = "";
            String GLDraftPay = "";
            String GLCASEPAY = "";
            String GLHOPAY = "";
            String GLBillLodg = "";
            String GLChequeRet = "";
            String GLSundry = "";
            String BnkGuarCr = "";
            String BnkGuarDr = "";
            String GLSundryName = "";
            String GLService = "";
            String GLLcComm = "";
            String GLBgComm = "";

//            checkList = em.createNativeQuery("select name from sysobjects where name='Bill_IBCOBCGLMast' and xtype='u'").getResultList();
//            if (checkList.size() == 0) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } 


            checkList = em.createNativeQuery("select GLHO, GLBPIntRec, GLBCComm, GLBillPay, GLDraftPay, GLCASEPAY, GLHOPAY, GLBillCol, GLBillLodg,GLChequeRet, GLSundry, BnkGuarCr, BnkGuarDr, GLService, GLLcComm, GLBgComm from bill_ibcobcglmast").getResultList();
//            if (checkList.size() == 0) {
//                GLHO = BRCODE + GlConstant.GLHO;
//                GLBPIntRec = BRCODE + GlConstant.GLBPIntRec;
//
//                GLBCComm = BRCODE + GlConstant.GLBCComm;
//                GLBillPay = BRCODE + GlConstant.GLBillPay;
//
//                GLDraftPay = BRCODE + GlConstant.GLBPIntRec;
//                GLCASEPAY = BRCODE + GlConstant.GLDraftPay;
//                GLHOPAY = BRCODE + GlConstant.GLHOPAY;
//                GLBillCol = BRCODE + GlConstant.GLBillCol;
//
//                GLBillLodg = BRCODE + GlConstant.GLBillLodg;
//
//                GLChequeRet = BRCODE + GlConstant.GLChequeRet;
//                GLSundry = BRCODE + GlConstant.GLSundry;
//                BnkGuarCr = BRCODE + GlConstant.BnkGuarCr;
//                BnkGuarDr = BRCODE + GlConstant.BnkGuarDr;
//                GLService = BRCODE + GlConstant.GLService;
//                GLLcComm = BRCODE + GlConstant.GLLcComm;
//                GLBgComm = BRCODE + GlConstant.GLBgComm;
//            } else {
//                Vector secLst1 = (Vector) checkList.get(0);
//                GLBPIntRec = secLst1.get(1).toString();
//                GLBillPay = secLst1.get(3).toString();
//                GLBillCol = secLst1.get(7).toString();
//                GLBillLodg = secLst1.get(8).toString();
//
//            }

            String detail = billType + " No: " + billNo + "/" + fYear + " From Acno: " + accountNo;
            String details = "VCH : Comm For " + billType + "No: " + billNo + "/" + fYear + "For Acno: " + " " + accountNo;
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "'  ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            int Tempbds = Integer.parseInt(Tempbd);
            trsNumber = fTSPosting43CBSBean.getTrsNo();
            float RECNO = 0;
            float TOKENNO_Dr = 0;
            String checkListD1 = fTSPosting43CBSBean.ftsPosting43CBS(GLHO, 2, 0, AmtToDebited, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(checkListD1.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                //ut.rollback();
                return checkListD1;
            }

            if (ourCharges > 0) {
                String checkListD2 = fTSPosting43CBSBean.ftsPosting43CBS(GLBCComm, 2, 0, ourCharges, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
                if (!(checkListD2.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                    //ut.rollback();
                    return checkListD2;
                }

            }
            String detail1 = "VCH : BCNo: " + " " + billNo + " / " + " " + fYear;
            String checkListD3 = fTSPosting43CBSBean.ftsPosting43CBS(accountNo, 2, 1, instAmt, Tempbd, Tempbd, user, BRCODE, BRCODE, 0, detail, trsNumber, RECNO, 0, null, "N", null, "20", payBy, instNo, instDt, null, null, null, null, null, null, TOKENNO_Dr, "N", "", "", "");
            if (!(checkListD3.substring(0, 4).equalsIgnoreCase("TRUE"))) {
                return checkListD3;
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String passClick(String instNo, String instDt, double instAmt, String billType, float billNo, int fYear, String panelReason, double ourCharges, double amtDebited, double retCharges, String resonCancel, String accountNo, String name, int payBy, String user, String BRCODE) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Float TrsNo;
            Integer var4 = null;
            Integer var5 = null;
            Integer var6 = null;
            float recnum = 0.0f;
            List balance;

            if (resonCancel == null) {
                resonCancel = "A/C FROZEN";
            }
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + BRCODE + "' ").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            int Tempbds = Integer.parseInt(Tempbd);
            if ((instNo == null) || (instDt == null) || (instAmt <= 0)) {
                ut.rollback();
                return "Please Check Instruement details";
            }
            List select = em.createNativeQuery("Select InstNo,InstAmount,InstDt,Acno,Comm,Postage From bill_ibcbooking Where substring(acno,1,2)='" + BRCODE + "' and  BillNo = " + billNo + " And FYear=" + fYear + " and billtype='" + billType + "'").getResultList();
            if (select.isEmpty()) {
                ut.rollback();
                return "Please Check Instruement details";
            }

            String fd = fieldCheck(billType, billNo, fYear, panelReason, ourCharges, amtDebited, instAmt, retCharges, resonCancel);
            if (!fd.equals("False")) {
                String[] values = null;
                String spliter = ": ";
                values = fd.split(spliter);
                String b = (values[0]);
                String a = (values[1]);
                if (a.equals("True")) {
                    ut.rollback();
                    return "" + b;
                }
            }

            String acNature = fTSPosting43CBSBean.getAccountNature(accountNo);
            if ((acNature.equalsIgnoreCase(CbsConstant.SAVING_AC))) {
                //  balance = em.createNativeQuery("Select Balance From RECONBALAN Where substring(acno,1,2)='" + BRCODE + "' and Acno='" + accountNo + "' And Balance>=" + amtDebited + "").getResultList();
                balance = em.createNativeQuery("Select Balance From reconbalan Where  Acno='" + accountNo + "' And Balance>=" + amtDebited + "").getResultList();
                if (balance.isEmpty()) {
                    ut.rollback();
                    return "Insufficient Fund in Acno :" + accountNo;
                }
            }
            TrsNo = fTSPosting43CBSBean.getTrsNo();
            recnum = fTSPosting43CBSBean.getRecNo();

            List billBooking = em.createNativeQuery("Select BillNo,Acno,Fyear,Refno,BillType,AlphaCode,PayableAt,Infavourof,BankName,BankAddr,Instno,InstAmount,Comm,Postage,RemType,InstDt,DT,Status,Auth,AuthBy,EnterBy,LastUpdateBy,Trantime From bill_ibcbooking where substring(acno,1,2)='" + BRCODE + "' and Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'").getResultList();
            if (!billBooking.isEmpty()) {
                Vector billBooks = (Vector) billBooking.get(0);
                float billNumber = Float.parseFloat(billBooks.get(0).toString());

                int fYr = Integer.parseInt(billBooks.get(2).toString());
                String refNo = billBooks.get(3).toString();
                //float refNo=Float.parseFloat(refN);
                String alphaCod = billBooks.get(5).toString();

                if (alphaCod.equals("0")) {
                    ut.rollback();
                    return "alpha Code is Zero :";
                } else {
                    String prPass = procPass(billType, ourCharges, accountNo, instNo, billNo, fYear, amtDebited, payBy, instAmt, BRCODE, user, instDt);
                    if (!prPass.equals("True")) {
                        ut.rollback();
                        return prPass;
                    }
                    Query insertQueries = em.createNativeQuery("insert bill_hoothers (fyear,instno,acno,custname,infavourof,payableat,amount,enterby,Auth,AuthBy,dt,status,trantype, seqno,comm,billtype,ty,recno,origindt) select FYear,instno,Acno," + "'" + name + "'" + ",InFavourOf,Alphacode," + amtDebited + "," + "'" + user + "'" + ",'Y'," + "'" + user + "'" + ",'" + Tempbd + "','Issued', 2," + refNo + "," + ourCharges + ",'BT',0," + recnum + ",'" + instDt + "' from bill_ibcbooking where  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
                    var4 = insertQueries.executeUpdate();

                    Query insertQ = em.createNativeQuery("Insert bill_ibcprocessed(billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage,OtherCharges,CreditedAmount,InFavourOf,InstDt,instentrydt,dt,status,EnterBy,TrsNo,RecNo,Refno,PayableAt,RemType) select billNo,Acno,FYear,BillType,Alphacode,BankName,BankAddr,InstNo,InstAmount,Comm,Postage," + ourCharges + "," + amtDebited + ",InFavourOf,'" + instDt + "','" + Tempbd + "','" + Tempbd + "','R','" + user + "'," + TrsNo + "," + recnum + ",refno,PayableAt,RemType from bill_ibcbooking where substring(acno,1,2)='" + BRCODE + "' and  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
                    var5 = insertQ.executeUpdate();

                    Query updateQuery = em.createNativeQuery("Delete From bill_ibcbooking Where substring(acno,1,2)='" + BRCODE + "' and  Billno=" + billNo + " and Fyear=" + fYear + " and billType='" + billType + "'");
                    var6 = updateQuery.executeUpdate();

                }

            }
            if ((var4 > 0) && (var5 > 0) && (var6 > 0)) {
                ut.commit();
                //ut.rollback();
                return "Instruction Pass Successfully";
            } else {
                ut.rollback();
                return "Problem in Instruction Pass";
            }

        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }

    public String fieldCheck(String billType, Float billNo, Integer fYear, String panelReason, double ourCharges, double amtDebited, double instAmt, double retCharges, String resonCancel) {

        String CFlag = "False";
        String a = "False";
        if ((billType == null) || (billType.trim().equalsIgnoreCase(""))) {
            CFlag = "True";
            //ut.rollback();
            return "Bill Type Should Not Be Blank" + ": " + CFlag;
        }
        if (billType.equals("--SELECT--")) {
            CFlag = "True";
            //ut.rollback();
            return "Bill Type Should Not Be Blank" + ": " + CFlag;
        }
        if (billNo < 0) {
            CFlag = "True";
            //ut.rollback();
            return "Please Enter Valid Bill No." + ": " + CFlag;
        }
        if (fYear < 0) {
            CFlag = "True";
            //ut.rollback();
            return "Year Should Not Be Blank" + ": " + CFlag;
        }
        if (panelReason.equals("True")) {
            if (ourCharges < 0) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Our Charges" + ": " + CFlag;
            }
            if ((amtDebited < 0) || (amtDebited > (instAmt - ourCharges))) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Amount" + ": " + CFlag;
            }
        } else {
            if (retCharges < 0) {
                CFlag = "True";
                //ut.rollback();
                return "Please Enter Valid Return Charges" + ": " + CFlag;
            }
            if ((resonCancel == null) || (resonCancel.trim().equalsIgnoreCase(""))) {
                CFlag = "True";
                //ut.rollback();
                return "Please Select Reason For Cancel" + ": " + CFlag;
            }
        }
        return "False";
    }

    public String fieldDisplay(float billNo, Integer fYear, String billType, String BRCODE) {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List custName;
            String custNames = null;
            String instNos = null;
            float instAmount = 0;
            String instDts = null;
            String accNo;
            float comm = 0;
            float postage = 0;
            String alphaCodes = null;
            String panelAlpha = null;
            String lblHo = null;
            String number = null;
            if (billType.equals("--SELECT--")) {
                ut.rollback();
                return "Please Select bill Type";
            }
            if (billNo < 0) {
                ut.rollback();
                return "Please Select big Bill Number";
            }
            List selectAlpha = em.createNativeQuery("Select alphacode,instno,InstAmount,InstDt,Acno,Comm,Postage,RemType From bill_ibcbooking Where substring(acno,1,2)='" + BRCODE + "' and  BillNo =" + billNo + " And FYear=" + fYear + " and billtype='" + billType + "'").getResultList();
            if (!selectAlpha.isEmpty()) {
                Vector selectAlphas = (Vector) selectAlpha.get(0);
                accNo = selectAlphas.get(4).toString();
                String accCode = accNo.substring(2, 4);
                String accNature = fTSPosting43CBSBean.getAccountNature(accNo);
                if ((accNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (accNature.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    custName = em.createNativeQuery("Select CustName from td_accountmaster Where substring(acno,1,2)='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();
                } else if (accNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    // custName = em.createNativeQuery("Select AcName from GLTable Where substring(acno,1,2)='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                    custName = em.createNativeQuery("Select AcName from gltable Where acno='" + accNo + "'").getResultList();
                } else {
                    //  custName = em.createNativeQuery("Select CustName from AccountMaster Where substring(acno,1,2)='" + BRCODE + "' and acno='" + accNo + "'").getResultList();
                    custName = em.createNativeQuery("Select CustName from accountmaster Where acno='" + accNo + "'").getResultList();
                    Vector custumerName = (Vector) custName.get(0);
                    custNames = custumerName.get(0).toString();

                }
                if (!custName.isEmpty()) {
                    String alphaCode = selectAlphas.get(0).toString();
                    instNos = selectAlphas.get(1).toString();
                    String instAmt = selectAlphas.get(2).toString();
                    instAmount = Float.parseFloat(instAmt);
                    String instD = selectAlphas.get(3).toString();
                    String yy = instD.substring(0, 4);
                    String mm = instD.substring(5, 7);
                    String dd = instD.substring(8, 10);
                    instDts = dd + "/" + mm + "/" + yy;
                    String co = selectAlphas.get(5).toString();
                    comm = Float.parseFloat(co);
                    String ptg = selectAlphas.get(6).toString();
                    postage = Float.parseFloat(ptg);
                    String remType = selectAlphas.get(7).toString();
                    if (alphaCode.equals("0")) {
                        panelAlpha = "True";
                        if (remType.equals("PO")) {
                            List alpha = em.createNativeQuery("select alphacode from branchmaster where brncode= cast ('" + BRCODE + "' as unsigned)").getResultList();
                            if (!alpha.isEmpty()) {
                                Vector alphas = (Vector) alpha.get(0);
                                alphaCodes = alphas.get(0).toString();
                            }
                        }
                    } else {
                        panelAlpha = "False";
                        lblHo = "Head Office";
                        List acNumber = em.createNativeQuery("select AcNo from gltable where substring(acno,1,2)='" + BRCODE + "' and AcName='HEAD OFFICE'").getResultList();
                        Vector acNumbers = (Vector) acNumber.get(0);
                        number = acNumbers.get(0).toString();
                    }
                }
            } else {
                ut.rollback();
                return "Bill No. Does Not Exist";
            }
            ut.commit();
            return instNos + ": " + instAmount + ": " + instDts + ": " + accNo + ": " + custNames + ": " + comm + ": " + postage + ": " + alphaCodes + ": " + panelAlpha + ": " + lblHo + ": " + number;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (SystemException syex) {
                throw new EJBException("Rollback failed: " + syex.getMessage());
            }
            throw new EJBException("Transaction failed: " + e.getMessage());
        }
    }
//    public String findTax(Float commAmt) {
//        List result = new ArrayList();
//        String currentDate = "";
//        Integer roundNo = 0;
//        String appType = "";
//        Float appRot;
//        String appOn = "";
//        String glAcct = "";
//        String message = "true";
//        Float ttlTaxAmt = null;
//        try {
//            List resultDate = em.createNativeQuery("SELECT date_format(curdate(),'%Y%m%d')").getResultList();
//            if (resultDate.size() <= 0) {
//            } else {
//                Vector resultVect = (Vector) resultDate.get(0);
//                currentDate = resultVect.get(0).toString();
//            }
//            List resultTaxMaster = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= date_format(curdate(),'%Y%m%d') limit 1").getResultList();
//            if (resultTaxMaster.size() <= 0) {
//                message = "TAX NOT DEFINED!!!";
//            } else {
//                List resultTax = em.createNativeQuery("SELECT RoundUpto FROM taxmaster WHERE ROTApplyOn='C' AND applicableFlag='Y' AND auth='Y' AND applicableDt <= date_format(curdate(),'%Y%m%d') limit 1").getResultList();
//                if (resultTax.size() <= 0) {
//                } else {
//                    Vector resultVect = (Vector) resultTax.get(0);
//                    roundNo = Integer.parseInt(resultVect.get(0).toString());
//                }
//            }
//            List taxApplicableROTList = fnTaxApplicableROT(currentDate);
//            if (taxApplicableROTList.size() <= 0) {
//                message = "Rate Of Tax not Found!!!";
//            } else {
//                Vector resultVect = (Vector) taxApplicableROTList.get(0);
//                appType = resultVect.get(0).toString();
//                appRot = Float.parseFloat(resultVect.get(1).toString());
//                appOn = resultVect.get(2).toString();
//                glAcct = resultVect.get(3).toString();
//            }
//            String taxamount = taxAmount(commAmt, appType);
//            List ttlTax = em.createNativeQuery("SELECT ROUND(" + taxamount + "," + roundNo + ")").getResultList();
//            if (ttlTax.size() <= 0) {
//            } else {
//                Vector resultVect = (Vector) ttlTax.get(0);
//                ttlTaxAmt = Float.parseFloat(resultVect.get(0).toString());
//            }
//            result.add(ttlTaxAmt);
//            //result.add(message);
//            return result.toString();
//        } catch (Exception ex) {
//        }
//        return result.toString();
//
//    }
//    public List fnTaxApplicableROT(String appDT) {
//        List resultList = null;
//        try {
//            resultList = em.createNativeQuery("select TYPE,ROT,ROTApplyOn,glhead from taxmaster where ApplicableDt<='" + appDT + "' and applicableFlag='Y' and Auth='Y'").getResultList();
//            if (resultList.size() <= 0) {
//            }
//            return resultList;
//        } catch (Exception ex) {
//        }
//        return resultList;
//    }
//    public String taxAmount(Float amt, String type) {
//        Float minAmt;
//        Float maxAmt;
//        Float balance = null;
//        try {
//            List resultlist = em.createNativeQuery("select minAmt,maxamt from taxmaster where type='" + type + "' and"
//                    + " ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' "
//                    + "and ApplicableFlag='Y')").getResultList();
//            if (resultlist.size() <= 0) {
//                return "No Data in TaxMaster";
//            } else {
//                Vector resultVect = (Vector) resultlist.get(0);
//                minAmt = Float.parseFloat(resultVect.get(0).toString());
//                maxAmt = Float.parseFloat(resultVect.get(1).toString());
//            }
//            List resultlistTaxMaster = em.createNativeQuery("Select ('" + amt + "'* ROT)/100  from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y' and ApplicableDt in (select max(ApplicableDt) from taxmaster where type='" + type + "' and "
//                    + "ApplicableFlag='Y')").getResultList();
//            if (resultlistTaxMaster.size() <= 0) {
//                return "No Data in TaxMaster";
//            } else {
//                Vector resultVect = (Vector) resultlistTaxMaster.get(0);
//                balance = Float.parseFloat(resultVect.get(0).toString());
//            }
//
//            if (balance < minAmt) {
//                balance = minAmt;
//            } else if (balance > maxAmt) {
//                balance = maxAmt;
//            }
//            return balance.toString();
//        } catch (Exception ex) {
//        }
//        return balance.toString();
//    }
}