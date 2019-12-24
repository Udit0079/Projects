/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.npci.cts.InstrumentsForTheSameVoucherGrid;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.txn.OtherTransactionManagementFacadeRemote;
import com.cbs.pojo.ChqGridDetails;
import com.cbs.pojo.OutwardCtsFileGrid;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
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
@Stateless(mappedName = "OutwardClearingManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class OutwardClearingManagementFacade implements OutwardClearingManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPostingRemote;
    @EJB
    OtherTransactionManagementFacadeRemote trfEjb;
    @EJB
    private InterBranchTxnFacadeRemote ibRemote;
    @EJB
    private CommonReportMethodsRemote common;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ddmmyy = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    SimpleDateFormat dmyOne = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    NumberFormat nf = new DecimalFormat("#.##");
    NumberFormat formatter2 = new DecimalFormat("0.00");
    /*Start of Outward Clearing Register Maintenance */

    public String getCurrentDate(String brCode) throws ApplicationException {
        try {
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            String currentDt = Tempbd.substring(6, 8) + "/" + Tempbd.substring(4, 6) + "/" + Tempbd.substring(0, 4);
            return "" + currentDt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getEntryOpenDate(String emFlag, String brCode) throws ApplicationException {
        try {
            String BreakFlag = "False";
            String DtPostingOpen = null;
            String DtClearingOpen = null;
            emFlag = emFlag.toString().trim();
//            if (emFlag.equalsIgnoreCase("Circle A-MICR")) {
//                emFlag = "A";
//            } else if (emFlag.equalsIgnoreCase("Circle A-Non MICR")) {
//                emFlag = "B";
//            } else if (emFlag.equals("Circle B-MICR")) {
//                emFlag = "C";
//            } else if (emFlag.equals("Circle B-Non MICR")) {
//                emFlag = "D";
//            } else if (emFlag.equals("Circle C-MICR")) {
//                emFlag = "E";
//            } else if (emFlag.equals("Circle C-Non MICR")) {
//                emFlag = "F";
//            }
            List tempBd = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG = 'N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            String NRegDt = CbsUtil.dateAdd(Tempbd, 1);
            if (BreakFlag.equalsIgnoreCase("False")) {
                List dayBeginFgList = em.createNativeQuery("select DayBeginflag from bankdays where Date= '" + NRegDt + "' and Brncode='" + brCode + "'").getResultList();
                if (dayBeginFgList.isEmpty()) {
                    NRegDt = Tempbd;
                    BreakFlag = "True";
                } else {
                    Vector dayBeginFg = (Vector) dayBeginFgList.get(0);
                    String dayBeginFlag = dayBeginFg.get(0).toString();
                    if (dayBeginFlag.equalsIgnoreCase("H")) {
                        NRegDt = CbsUtil.dateAdd(NRegDt, 1);
                    } else {
                        BreakFlag = "True";
                    }
                }
            }
            BreakFlag = "False";
            if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                DtPostingOpen = CbsUtil.dateAdd(Tempbd, 1);
                while (BreakFlag.equalsIgnoreCase("False")) {
                    //if (BreakFlag.equalsIgnoreCase("False")) {
                    List dayBeginFgList = em.createNativeQuery("select DayBeginflag from bankdays where Date= '" + DtPostingOpen + "' and Brncode='" + brCode + "'").getResultList();
                    if (dayBeginFgList.isEmpty()) {
                        DtPostingOpen = Tempbd;
                        BreakFlag = "True";
                    } else {
                        Vector dayBeginFg = (Vector) dayBeginFgList.get(0);
                        String dayBeginFlag = dayBeginFg.get(0).toString();
                        if (dayBeginFlag.equalsIgnoreCase("H")) {
//                            List DtPostingOpenLists = em.createNativeQuery("SELECT DateAdd(d, 1, '" + DtPostingOpenA + "')").getResultList();
//                            Vector NRegDts1 = (Vector) DtPostingOpenLists.get(0);
                            DtPostingOpen = CbsUtil.dateAdd(DtPostingOpen, 1);
                        } else {
                            BreakFlag = "True";
                        }
                    }
                }
                BreakFlag = "False";
            } else {
                DtPostingOpen = Tempbd;
            }
            if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                DtClearingOpen = CbsUtil.dateAdd(DtPostingOpen, 1);
                while (BreakFlag.equalsIgnoreCase("False")) {
                    List dayBeginFgList = em.createNativeQuery("select DayBeginflag from bankdays where Date= '" + DtClearingOpen + "' and Brncode='" + brCode + "'").getResultList();
                    if (dayBeginFgList.isEmpty()) {
                        DtClearingOpen = Tempbd;
                        BreakFlag = "True";
                    } else {
                        Vector dayBeginFg = (Vector) dayBeginFgList.get(0);
                        String dayBeginFlag = dayBeginFg.get(0).toString();
                        if (dayBeginFlag.equalsIgnoreCase("H")) {
                            DtClearingOpen = CbsUtil.dateAdd(DtClearingOpen, 1);
                        } else {
                            BreakFlag = "True";
                        }
                    }
                }
                BreakFlag = "False";
            } else {
                DtClearingOpen = Tempbd;
            }
            return Tempbd + ":" + DtPostingOpen + ":" + DtClearingOpen;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveDataRegisterMaintenance(String emFlag, String entryDt, String postingDt,
            String clearingDt, String user, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            emFlag = emFlag.toString().trim();
//            if (emFlag.equalsIgnoreCase("Circle A-MICR")) {
//                emFlag = "A";
//            } else if (emFlag.equalsIgnoreCase("Circle A-Non MICR")) {
//                emFlag = "B";
//            } else if (emFlag.equals("Circle B-MICR")) {
//                emFlag = "C";
//            } else if (emFlag.equals("Circle B-Non MICR")) {
//                emFlag = "D";
//            } else if (emFlag.equals("Circle C-MICR")) {
//                emFlag = "E";
//            } else if (emFlag.equals("Circle C-Non MICR")) {
//                emFlag = "F";
//            }
            List holidayChekingR = em.createNativeQuery("Select DayBeginFlag from bankdays where Date='" + entryDt + "' and Brncode='" + brCode + "'").getResultList();
            if (holidayChekingR.isEmpty()) {
                ut.rollback();
                return "This Register Date is niether marked as Holiday or as a Working Day.";
            } else {
                Vector holidayChekingRs = (Vector) holidayChekingR.get(0);
                String Rectemp = holidayChekingRs.get(0).toString();
                if (Rectemp.equalsIgnoreCase("H")) {
                    ut.rollback();
                    return "Register Date is marked as Holiday.";
                }
            }
            List holidayChekingP = em.createNativeQuery("Select DayBeginFlag from bankdays where Date='" + postingDt + "' and Brncode='" + brCode + "'").getResultList();
            if (holidayChekingP.isEmpty()) {
                ut.rollback();
                return "This Posting Date is niether marked as Holiday or as a Working Day.";
            } else {
                Vector holidayChekingPs = (Vector) holidayChekingP.get(0);
                String Rectemp = holidayChekingPs.get(0).toString();
                if (Rectemp.equalsIgnoreCase("H")) {
                    ut.rollback();
                    return "Posting Date is marked as Holiday.";
                }
            }
            List holidayChekingC = em.createNativeQuery("Select DayBeginFlag from bankdays where Date='" + clearingDt + "' and Brncode='" + brCode + "'").getResultList();
            if (holidayChekingC.isEmpty()) {
                ut.rollback();
                return "This Day is niether marked as Holiday or as a Working Day.";
            } else {
                Vector holidayChekingCs = (Vector) holidayChekingC.get(0);
                String Rectemp = holidayChekingCs.get(0).toString();
                if (Rectemp.equalsIgnoreCase("H")) {
                    ut.rollback();
                    return "Clearing Date is marked as Holiday.";
                }
            }

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            long post = CbsUtil.dayDiff(ymd.parse(entryDt), ymd.parse(postingDt));

            long clear = CbsUtil.dayDiff(ymd.parse(entryDt), ymd.parse(clearingDt));

            long clearPost = CbsUtil.dayDiff(ymd.parse(postingDt), ymd.parse(clearingDt));

            long dateDiffvecDateChecking = CbsUtil.dayDiff(ymd.parse(Tempbd), ymd.parse(entryDt));

            if (dateDiffvecDateChecking < 0) {
                ut.rollback();
                return "ENTRY DATE CAN NOT BE LESS THEN TODAY DATE .";
            } else if (post < 0) {
                ut.rollback();
                return "Posting Date cannot be less then Today's date.";
            } else if (clear < 0) {
                ut.rollback();
                return "Clearing Date cannot be less then Today's date.";
            } else if (clearPost < 0) {
                ut.rollback();
                return "Clearing Date cannot be less then Posting date of the Clearing Register.";
            }
            List entryDate = em.createNativeQuery("Select EntryDate,OpenedBy from clg_ow_register where Emflag='" + emFlag + "' and entryDate='" + entryDt + "' and brncode='" + brCode + "'").getResultList();
            if (!entryDate.isEmpty()) {
                Vector entryDates = (Vector) entryDate.get(0);
                String OpenedBy = entryDates.get(1).toString();
                ut.rollback();
                return "The Register for   " + emFlag + "   Type in   " + entryDt + "   has already been opened by " + OpenedBy;
            }
            Query insertQuery = em.createNativeQuery("insert into clg_ow_register(EmFlag,EntryDate,PostingDate,ClearingDate,OpenedBy,OpeningDt,Status,Summary,brncode)"
                    + "values (" + "'" + emFlag + "'" + "," + "'" + entryDt + "'" + "," + "'" + postingDt + "'" + "," + "'" + clearingDt + "'" + "," + "'" + user + "'" + "," + "'" + entryDt + "'" + "," + "'OPEN'" + "," + "'OPENED:'" + "," + "'" + brCode + "'" + ")");
            int var = insertQuery.executeUpdate();
            if (var > 0) {
                ut.commit();
                return "Register Opened Successfully.";
            } else {
                ut.rollback();
                return "Register could not be Opened";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String otherButton(String emFlag, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String ClearingDates = null;
            String PostingDates = null;
            emFlag = emFlag.toString().trim();
//            if (emFlag.equalsIgnoreCase("Circle A-MICR")) {
//                emFlag = "A";
//            } else if (emFlag.equalsIgnoreCase("Circle A-Non MICR")) {
//                emFlag = "B";
//            } else if (emFlag.equals("Circle B-MICR")) {
//                emFlag = "C";
//            } else if (emFlag.equals("Circle B-Non MICR")) {
//                emFlag = "D";
//            } else if (emFlag.equals("Circle C-MICR")) {
//                emFlag = "E";
//            } else if (emFlag.equals("Circle C-Non MICR")) {
//                emFlag = "F";
//            }
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            List NRegDtss = em.createNativeQuery("Select PostingDate,ClearingDate from clg_ow_register where Emflag='" + emFlag + "' and entrydate='" + Tempbd + "' and brncode='" + brCode + "'").getResultList();
            if (NRegDtss.isEmpty()) {
            } else {
                Vector NRegDts = (Vector) NRegDtss.get(0);
                String PostingDate = NRegDts.get(0).toString();
                String ClearingDate = NRegDts.get(1).toString();
                PostingDates = PostingDate.substring(8, 10) + "/" + PostingDate.substring(5, 7) + "/" + PostingDate.substring(0, 4);
                ClearingDates = ClearingDate.substring(8, 10) + "/" + ClearingDate.substring(5, 7) + "/" + ClearingDate.substring(0, 4);
            }
            ut.commit();
            return PostingDates + ": " + ClearingDates;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String yesButtonRegisterClose(String emFlag, String dtRegisterClose, String user,
            String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int varA = 0;
            int varB = 0;
            int varC = 0;
            int varD = 0;
            List txnStatus;
            List txnStatuss;
            String txnSts = null;
            emFlag = emFlag.toString().trim();
//            if (emFlag.equalsIgnoreCase("Circle A-MICR")) {
//                emFlag = "A";
//            } else if (emFlag.equalsIgnoreCase("Circle A-Non MICR")) {
//                emFlag = "B";
//            } else if (emFlag.equals("Circle B-MICR")) {
//                emFlag = "C";
//            } else if (emFlag.equals("Circle B-Non MICR")) {
//                emFlag = "D";
//            } else if (emFlag.equals("Circle C-MICR")) {
//                emFlag = "E";
//            } else if (emFlag.equals("Circle C-Non MICR")) {
//                emFlag = "F";
//            }

            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            if (!dtRegisterClose.equalsIgnoreCase(Tempbd)) {
                ut.rollback();
                return "Only Today Clearing Register Can be Closed.";
            }

            List NRegDtss = em.createNativeQuery("Select emflag from clg_ow_register Where Emflag='" + emFlag + "' and EntryDate='" + dtRegisterClose + "' AND STATUS='OPEN' and brncode='" + brCode + "'").getResultList();
            if (NRegDtss.isEmpty()) {
                ut.rollback();
                return "No Record Exist for Closing";
            }
            if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                //txnStatus = em.createNativeQuery("Select TxnStatus From Clg_Ow_Entry where txndate='" + dtRegisterClose + "' and emflag ='" + emFlag + "' and substring(acno,1,2)='" + brCode + "' UNION Select TxnStatus From Clg_Ow_2DAY  where txndate='" + dtRegisterClose + "' and emflag ='" + emFlag + "' and substring(acno,1,2)='" + brCode + "' UNION  Select TxnStatus From stud_Clg_Ow_Entry  where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag ='" + emFlag + "' and substring(acno,1,2)='" + brCode + "' UNION Select TxnStatus From stud_Clg_Ow_2DAY  where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag ='" + emFlag + "' and substring(acno,1,2)='" + brCode + "'").getResultList();
                txnStatus = em.createNativeQuery("Select TxnStatus From clg_ow_entry where txndate='" + dtRegisterClose + "' and emflag ='" + emFlag + "' and orgnBrcode='" + brCode + "' UNION Select TxnStatus From clg_ow_2day  where txndate='" + dtRegisterClose + "' and emflag ='" + emFlag + "' and orgnBrcode='" + brCode + "' UNION Select TxnStatus From stud_clg_ow_entry st1,accountmaster am  where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag ='" + emFlag + "' and st1.Acno = am.ACNo and am.CurBrCode = '" + brCode + "' UNION Select TxnStatus From stud_clg_ow_2day st2,accountmaster am  where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag ='" + emFlag + "' and st2.Acno = am.ACNo and am.CurBrCode = '" + brCode + "'").getResultList();
                if (!txnStatus.isEmpty()) {
                    Vector txnStatus1 = (Vector) txnStatus.get(0);
                    txnSts = txnStatus1.get(0).toString();
                }
            } else {
                //  txnStatus = em.createNativeQuery("Select Txnstatus from Clg_LocalChq where txndate='" + dtRegisterClose + "' and substring(acno,1,2)='" + brCode + "' and emflag = '" + emFlag + "' UNION Select Txnstatus from stud_Clg_LocalChq where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag = '" + emFlag + "' and substring(acno,1,2)='" + brCode + "'").getResultList();
                txnStatus = em.createNativeQuery("Select Txnstatus from clg_localchq where txndate='" + dtRegisterClose + "' and orgnBrcode='" + brCode + "' and emflag = '" + emFlag + "' UNION Select Txnstatus from stud_clg_localchq st,accountmaster am where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag = '" + emFlag + "' and st.Acno = am.ACNo and am.CurBrCode = '" + brCode + "'").getResultList();
                if (!txnStatus.isEmpty()) {
                    Vector txnStatus1 = (Vector) txnStatus.get(0);
                    txnSts = txnStatus1.get(0).toString();
                }
            }
            if (txnStatus.isEmpty()) {
                Query updateQuery = em.createNativeQuery("update clg_ow_register set closedby='" + user + "',status='Close',closingdate=now()  where entrydate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and brncode='" + brCode + "'");
                varA = updateQuery.executeUpdate();
                Query insertQuery = em.createNativeQuery("insert into clg_ow_register_hist (EmFlag,EntryDate,PostingDate,ClearingDate,OpenedBy,OpeningDt,ClosedBy,"
                        + " ClosingDate,Status,Summary,AbortFlag,PostedBy,ClearedBy,brncode)select EmFlag,EntryDate,PostingDate,ClearingDate,OpenedBy,OpeningDt,ClosedBy,"
                        + " ClosingDate,Status,Summary,AbortFlag,PostedBy,ClearedBy,brncode from clg_ow_register where entrydate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and brncode='" + brCode + "'");
                varB = insertQuery.executeUpdate();
                Query deleteQuery = em.createNativeQuery("delete from clg_ow_register where entrydate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and brncode='" + brCode + "'");
                varC = deleteQuery.executeUpdate();
                if ((varA > 0) || (varB > 0) || (varC > 0)) {
                    ut.commit();
                    return "Register Closed Successfully.";
                }
            }
            List status = em.createNativeQuery("Select status from clg_ow_register where Emflag='" + emFlag + "' and entrydate='" + dtRegisterClose + "' and  brncode='" + brCode + "'").getResultList();
            if (status.isEmpty()) {
                ut.rollback();
                return "No Register is Active in this CircleType to Close.";
            } else {
                if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                    //txnStatuss = em.createNativeQuery("Select TxnStatus From Clg_Ow_Entry where txndate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and upper(TxnStatus)<>'V' UNION Select TxnStatus From  stud_Clg_Ow_Entry where txndate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and upper(TxnStatus)<>'V'").getResultList();
                    txnStatuss = em.createNativeQuery("Select TxnStatus From clg_ow_entry where txndate='" + dtRegisterClose + "' and orgnBrcode='" + brCode + "' and emflag ='"
                            + emFlag + "' and upper(TxnStatus)<>'V' "
                            + "UNION "
                            + "Select TxnStatus From  stud_clg_ow_entry st,accountmaster am where txndate='" + dtRegisterClose + "' and emflag ='" + emFlag + "' and st.Acno = am.ACNo "
                            + "and am.CurBrCode = '" + brCode + "' and upper(TxnStatus)<>'V'").getResultList();
                    if (!txnStatuss.isEmpty()) {
                        Vector txnStatus1 = (Vector) txnStatuss.get(0);
                        txnSts = txnStatus1.get(0).toString();
                    }
                } else {
                    //txnStatuss = em.createNativeQuery("Select Txnstatus from Clg_LocalChq where txndate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and upper(TxnStatus)<>'V' UNION Select TxnStatus From stud_Clg_LocalChq  where txndate='" + dtRegisterClose + "' and emflag = '" + emFlag + "' and upper(TxnStatus)<>'V'").getResultList();
                    txnStatuss = em.createNativeQuery("Select Txnstatus from clg_localchq where txndate='" + dtRegisterClose + "' and orgnBrcode='" + brCode + "' and emflag ='" + emFlag + "' and upper(TxnStatus)<>'V' UNION Select TxnStatus From stud_clg_localchq st,accountmaster am where txndate='" + dtRegisterClose + "' and st.Acno = am.ACNo and am.CurBrCode = '" + brCode + "' and emflag ='" + emFlag + "' and upper(TxnStatus)<>'V'").getResultList();
                    if (!txnStatuss.isEmpty()) {
                        Vector txnStatus1 = (Vector) txnStatuss.get(0);
                        txnSts = txnStatus1.get(0).toString();
                    }
                }
                if (!txnStatuss.isEmpty()) {
                    ut.rollback();
                    return "This Register contains Unverified Instruments. So Register Can't be closed.";
                }
                if (txnSts.equalsIgnoreCase("CLOSE")) {
                    ut.rollback();
                    return "This Register has already been Closed.";
                } else if (txnSts.equalsIgnoreCase("CLOSING")) {
                    return "This Register is under Closing Process, Wait for few Minutes.";
                } else if (txnSts.equalsIgnoreCase("OPEN")) {
                    Query updateQuery = em.createNativeQuery("Update clg_ow_register set Status = 'Closing',Summary=concat(Summary,'Closing:'),closingdate='" + Tempbd + "' Where Emflag='" + emFlag + "' and EntryDate='" + dtRegisterClose + "' and  brncode='" + brCode + "'");
                    varD = updateQuery.executeUpdate();
                }
            }

            Query updateQuery1 = em.createNativeQuery("Update clg_ow_register Set CLOSEDBY='" + user + "',Status='Close',Summary = concat(Summary,'Closed:'), Abortflag='N' where Emflag='" + emFlag + "' and EntryDate='" + dtRegisterClose + "' and brncode='" + brCode + "'");
            int var = updateQuery1.executeUpdate();

            if ((emFlag.equals("A")) || (emFlag.equals("B")) || (emFlag.equals("G"))) {
                Query insertQuery = em.createNativeQuery("Insert clg_ow_2day select * from clg_ow_entry where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag='" + emFlag + "' and orgnBrcode='" + brCode + "'");
                varA = insertQuery.executeUpdate();
                Query deleteQuery = em.createNativeQuery("Delete from clg_ow_entry where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag='" + emFlag + "' and orgnBrcode='" + brCode + "'");
                varB = deleteQuery.executeUpdate();
                Query insertQuery1 = em.createNativeQuery("Insert stud_clg_ow_2day select st.* from stud_clg_ow_entry st,accountmaster am where txndate=cast('" + dtRegisterClose + "' as datetime) and emflag='" + emFlag + "' and st.Acno = am.ACNo and am.CurBrCode = '" + brCode + "'");
                varC = insertQuery1.executeUpdate();
                Query deleteQuery1 = em.createNativeQuery("delete st from stud_clg_ow_entry st inner join accountmaster am on st.Acno = am.ACNo and st.txndate=cast('" + dtRegisterClose + "' as datetime) and st.emflag='" + emFlag + "' and am.CurBrCode = '" + brCode + "'");
                varD = deleteQuery1.executeUpdate();
            }
            
            if (var > 0) {
                ut.commit();
                return "Register Closed Successfully.";
            } else {
                ut.rollback();
                return "register Can not be closed";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String changeButtonRegisterMaintenance(String emFlag, String othClRegisterDt,
            String TabOther, String DtNewPosting, String DtOldClearing, String DtNewClearing,
            String DtOldPosting, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            int varA = 0;
            int varB = 0;
            String status;
            emFlag = emFlag.toString().trim();
            List NRegDtss = em.createNativeQuery("Select * from clg_ow_register where Emflag='" + emFlag + "' and entrydate='" + othClRegisterDt + "' and brncode='" + brCode + "'").getResultList();
            if (NRegDtss.isEmpty()) {
                ut.rollback();
                return "No Register is Active in this CircleType.";
            } else {
                List statusList = em.createNativeQuery("SELECT STATUS FROM clg_ow_register WHERE Emflag='" + emFlag + "' and entrydate='" + othClRegisterDt + "' and brncode='" + brCode + "'").getResultList();
                Vector statusL = (Vector) statusList.get(0);
                status = statusL.get(0).toString();
                if (TabOther.equals("Posting")) {
                    if ((status.equalsIgnoreCase("POSTED")) || (status.equalsIgnoreCase("CLEARED"))) {
                        ut.rollback();
                        return "Posting Date cannot be changed.";
                    } else {
                        long posting = CbsUtil.dayDiff(ymd.parse(othClRegisterDt), ymd.parse(DtNewPosting));

                        long posting1 = CbsUtil.dayDiff(ymd.parse(DtOldClearing), ymd.parse(DtNewPosting));

                        if (posting < 0) {
                            ut.rollback();
                            return "Posting Date cannot be less then Clearing Register Entry's date.";
                        }
                        if (posting1 > 0) {
                            ut.rollback();
                            return "Posting Date cannot be greater then Clearing date.";
                        }
                        List date = em.createNativeQuery("select date from bankdays where date='" + DtNewPosting + "' and daybeginflag='H' and Brncode='" + brCode + "'").getResultList();
                        if (!date.isEmpty()) {
                            ut.rollback();
                            return "Please Check the New Posting Date";
                        }
                        Query updateQuery = em.createNativeQuery("UPDATE clg_ow_register SET PostingDate='" + DtNewPosting + "' where emflag='" + emFlag + "' and EntryDate='" + othClRegisterDt + "' and brncode='" + brCode + "'");
                        varA = updateQuery.executeUpdate();

                    }
                } else {
                    if (status.equalsIgnoreCase("CLEARED")) {
                        ut.rollback();
                        return "Neither Posting Nor Clearing Date cannot be changed.";
                    }
                    long clear = CbsUtil.dayDiff(ymd.parse(DtOldPosting), ymd.parse(DtNewClearing));

                    if (clear < 0) {
                        ut.rollback();
                        return "Clearing Date cannot be less then Posting Date.";
                    }
                    List date = em.createNativeQuery("select date from bankdays where date='" + DtNewClearing + "' and daybeginflag='H' and Brncode='" + brCode + "'").getResultList();
                    if (!date.isEmpty()) {
                        ut.rollback();
                        return "Please Check the New Clearing Date";
                    }
                    Query updateQuery = em.createNativeQuery("UPDATE clg_ow_register SET ClearingDate='" + DtNewClearing + "' where emflag='" + emFlag + "' and EntryDate='" + othClRegisterDt + "' and brncode='" + brCode + "'");
                    varB = updateQuery.executeUpdate();
                }
            }
            if (varA > 0) {
                ut.commit();
                return "Date Changed Successfully";
            } else if (varB > 0) {
                ut.commit();
                return "Date Changed Successfully";
            } else {
                ut.rollback();
                return "Date Not Changed";
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String onChangeOfOtherDateCalander(String emFlag, String brCode, String otherCalender)
            throws ApplicationException {
        // UserTransaction ut = context.getUserTransaction();
        try {
            // ut.begin();
            String ClearingDates = null;
            String PostingDates = null;
            emFlag = emFlag.toString().trim();
            List NRegDtss = em.createNativeQuery("Select PostingDate,ClearingDate,Status from clg_ow_register where Emflag='" + emFlag
                    + "' and entrydate='" + otherCalender + "' and brncode='" + brCode + "'").getResultList();
            if (NRegDtss.isEmpty()) {
                // ut.rollback();
                return "No Register is Active in this CircleType.";
            } else {
                Vector NRegDts = (Vector) NRegDtss.get(0);
                String PostingDate = NRegDts.get(0).toString();
                String ClearingDate = NRegDts.get(1).toString();
                String status = NRegDts.get(2).toString();
                PostingDates = PostingDate.substring(8, 10) + "/" + PostingDate.substring(5, 7) + "/" + PostingDate.substring(0, 4);
                ClearingDates = ClearingDate.substring(8, 10) + "/" + ClearingDate.substring(5, 7) + "/" + ClearingDate.substring(0, 4);
                if (status.equalsIgnoreCase("POSTED")) {
                    // ut.rollback();
                    return "Posting Date Cannot Be Changed.";
                } else if (status.equalsIgnoreCase("CLEARED")) {
                    // ut.rollback();
                    return "Neither Posting Nor Clearing Date cannot be changed.";
                }
            }
            // ut.commit();
            return PostingDates + ": " + ClearingDates;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String onChangeOfOtherDateCalander1(String emFlag, String brCode, String otherCalender)
            throws ApplicationException {
        //  UserTransaction ut = context.getUserTransaction();
        try {
            // ut.begin();
            String ClearingDates = null;
            String PostingDates = null;
            emFlag = emFlag.toString().trim();
            List NRegDtss = em.createNativeQuery("Select PostingDate,ClearingDate,Status from clg_ow_register where Emflag='" + emFlag + "' and entrydate='" + otherCalender + "' and brncode='" + brCode + "'").getResultList();
            if (NRegDtss.isEmpty()) {
                //ut.rollback();
                return "No Register is Active in this CircleType.";
            } else {
                Vector NRegDts = (Vector) NRegDtss.get(0);
                String PostingDate = NRegDts.get(0).toString();
                String ClearingDate = NRegDts.get(1).toString();
                String status = NRegDts.get(2).toString();
                PostingDates = PostingDate.substring(8, 10) + "/" + PostingDate.substring(5, 7) + "/" + PostingDate.substring(0, 4);
                ClearingDates = ClearingDate.substring(8, 10) + "/" + ClearingDate.substring(5, 7) + "/" + ClearingDate.substring(0, 4);
            }
            //  ut.commit();
            return PostingDates + ": " + ClearingDates;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    /*End of Outward Clearing Register Maintenance */

    /*Start of Outward Clearing Cheque Deposite*/
    public List loadBankNames() {
        List bankNames = em.createNativeQuery("Select distinct(BankName) From clg_bankdirectory Order By Bankname").getResultList();
        return bankNames;
    }

    public List loadAlphaCode(String orgBrnCode) {
        List alphaCode = em.createNativeQuery("select alphacode from branchmaster where BrnCode = cast('" + orgBrnCode + "' as unsigned) order "
                + "by alphacode").getResultList();
        return alphaCode;
    }

    public String instrDate(String orgBrnCode) {
        List selectInstrdate = em.createNativeQuery("select date from bankdays where DayEndFlag = 'N' And Brncode  = '" + orgBrnCode + "'").getResultList();
        Vector vecInstrdate = (Vector) selectInstrdate.get(0);
        return vecInstrdate.get(0).toString();
    }

    public List getRegisterdate(String circleType, String orgBrnCode) {
        List selectRegisdate = em.createNativeQuery("Select distinct EntryDate from clg_ow_register where Upper(status) Not in "
                + "('CLOSE','POSTED','CLEARED','LOCKED','CLOSING','HELD') and EmFlag='" + circleType + "' And brncode = '" + orgBrnCode
                + "' Order By EntryDate").getResultList();
        return selectRegisdate;
    }

    public String cbsOutClearRegAccInfo(String accNo, String orgBrnCode, String acNature) throws ApplicationException {
        String acName = "", jtNameOut1 = "", jtNameOut2 = "", jtNameOut3 = "", jtNameOut4 = "",
                opendate = "", flgOut = "";
        int accStsOut = 0, postFlag = 0;
        try {

            if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List selectPostFlag = em.createNativeQuery("select acname,postflag,msgflag from gltable where acno='" + accNo + "'").getResultList();
                if (selectPostFlag.isEmpty()) {
                    return "Account Does Not Exist.";
                }
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postFlag = Integer.parseInt(vecPostFlag.get(1).toString());
                acName = vecPostFlag.get(0).toString();
                String msgFlag = vecPostFlag.get(2).toString();

                if (msgFlag.equals("4") || msgFlag.equals("50")) {
                    return "Outward Clearing does not allow in this account";
                }
                String checkPostFlagValue = fnCheckPostFlag(accNo, "");
                if (checkPostFlagValue.equalsIgnoreCase("TRUE")) {
                    return "Error Raised When Account Nature is PO.";
                }
            } else {
                String query = "";
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    query = "SELECT accstatus from td_accountmaster where acno='" + accNo + "' and (authby is not null or authby <>'')";
                } else {
                    query = "SELECT accstatus from accountmaster where acno='" + accNo + "' and (authby is not null or authby <>'')";
                }
                List selectAccStatus = em.createNativeQuery(query).getResultList();
                if (selectAccStatus.isEmpty()) {
                    return "Account does not exist or not authorized.";
                }
                Vector vecAccStatus = (Vector) selectAccStatus.get(0);
                accStsOut = Integer.parseInt(vecAccStatus.get(0).toString());

                if (accStsOut == 2) {
                    return "Account is Inoperative. You can not credit the account.";
                }
                if (accStsOut == 9) {
                    return "Account is Closed.";
                }
                if (ftsPostingRemote.getCodeForReportName("NPA-RECOVERY-CLG") == 0) {
                    if (accStsOut == 11) {
                        return "Account is Sub Standard. Use Sundry Head to Credit the Account.";
                    }
                    if (accStsOut == 12) {
                        return "Account is Doubltful. Use Sundry Head to Credit the Account.";
                    }
                    if (accStsOut == 13) {
                        return "Account is Loss. Use Sundry Head to Credit the Account.";
                    }
                }
                if (accStsOut == 15) {
                    return "Account is Deaf";
                }
                List fnAccDetailsResult = fnAccDetails(accNo, "", orgBrnCode, acNature);

                if (fnAccDetailsResult.isEmpty()) {
                    return "Invalid Account No.";
                }
                acName = fnAccDetailsResult.get(0).toString();
                if (acName.equalsIgnoreCase("")) {
                    return "No Details Exists.";
                }
                acName = fnAccDetailsResult.get(0).toString();
                jtNameOut1 = fnAccDetailsResult.get(1).toString();
                jtNameOut2 = fnAccDetailsResult.get(2).toString();
                jtNameOut3 = fnAccDetailsResult.get(3).toString();
                jtNameOut4 = fnAccDetailsResult.get(4).toString();
                opendate = fnAccDetailsResult.get(7).toString();

                List selectDateFromBankDays = em.createNativeQuery("SELECT date FROM bankdays WHERE DAYENDFLAG='N' AND brncode ='" + orgBrnCode + "'").getResultList();
                Vector vecForDate = (Vector) selectDateFromBankDays.get(0);
                String curDt = vecForDate.get(0).toString();

                long dtDiff = CbsUtil.dayDiff(ymd.parse(opendate), ymd.parse(curDt));
                if (dtDiff < 7) {
                    flgOut = "TRUE";
                }
                List bnkCodeList = em.createNativeQuery("select bank_code from mb_sms_sender_bank_detail").getResultList();
                Vector bnkCodeVec = (Vector) bnkCodeList.get(0);
                if (bnkCodeVec.get(0).toString().equalsIgnoreCase("CCBL") && !accNo.substring(0, 2).equals(orgBrnCode)) {
                    String tmpDt = CbsUtil.monthAdd(opendate, 6);
                    if (ymd.parse(tmpDt).getTime() >= ymd.parse(curDt).getTime()) {
                        throw new ApplicationException("Account must be six month old in case of inter-branch Transction");
                    }
                }
            }
            return String.valueOf(postFlag) + ":" + acName + ":" + jtNameOut1 + ":" + jtNameOut2 + ":" + jtNameOut3 + ":"
                    + jtNameOut4 + ":" + flgOut + ":" + acNature + ":" + accStsOut + ":" + opendate;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String fnCheckPostFlag(String accNo, String str) {
        int postflag;
        if (str.equalsIgnoreCase("ENTRY")) {
            List selectPostFlag = em.createNativeQuery("select postflag from gltable where acno='" + accNo + "' and postflag in (1,2,99)").getResultList();
            if (!selectPostFlag.isEmpty()) {
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postflag = Integer.parseInt(vecPostFlag.get(0).toString());
                if (postflag != 0) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            } else {
                return "FALSE";
            }
        } else if (str.equalsIgnoreCase("INCLG")) {
            List selectPostFlag = em.createNativeQuery("select postflag from gltable where acno='" + accNo + "' and postflag in (1,99)").getResultList();
            if (!selectPostFlag.isEmpty()) {
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postflag = Integer.parseInt(vecPostFlag.get(0).toString());
                if (postflag != 0) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            } else {
                return "FALSE";
            }
        } else if (str.equalsIgnoreCase("OWCLG")) {
            List selectPostFlag = em.createNativeQuery("select postflag from gltable where acno='" + accNo + "' and postflag in (1,99)").getResultList();
            if (!selectPostFlag.isEmpty()) {
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postflag = Integer.parseInt(vecPostFlag.get(0).toString());
                if (postflag != 0) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            } else {
                return "FALSE";
            }
        } else if (str.equalsIgnoreCase("REMT")) {
            List selectPostFlag = em.createNativeQuery("select postflag from gltable where acno='" + accNo + "' and postflag in (1,2,99)").getResultList();
            if (!selectPostFlag.isEmpty()) {
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postflag = Integer.parseInt(vecPostFlag.get(0).toString());
                if (postflag != 0) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            } else {
                return "FALSE";
            }
        } else {
            List selectPostFlag = em.createNativeQuery("select postflag from gltable where acno='" + accNo + "' and postflag in (1,2,99)").getResultList();
            if (!selectPostFlag.isEmpty()) {
                Vector vecPostFlag = (Vector) selectPostFlag.get(0);
                postflag = Integer.parseInt(vecPostFlag.get(0).toString());
                if (postflag != 0) {
                    return "TRUE";
                } else {
                    return "FALSE";
                }
            } else {
                return "FALSE";
            }
        }
    }

    public List fnAccDetails(String accNo, String address, String orgBrnCode, String acctNature) throws ApplicationException {
        List accDetails = new ArrayList();
        try {
            String acCode = ftsPostingRemote.getAccountCode(accNo);

            if (acctNature.equalsIgnoreCase("SB") || acctNature.equalsIgnoreCase("CA") || acctNature.equalsIgnoreCase("RD")
                    || acctNature.equalsIgnoreCase("DL") || acctNature.equalsIgnoreCase("TL") || acctNature.equalsIgnoreCase("DS")) {
                List selectForSB = em.createNativeQuery("select custname,ifnull(jtname1,''),ifnull(jtname2,''),ifnull(jtname3,''),ifnull(JTNAME4,''),"
                        + "ifnull(odlimit,0)'ODLIMIT' ,ifnull(adhoclimit,0) 'ADHOCLIMIT',opermode,accstatus,optstatus,openingdt"
                        + "'OPENINGDT',ifnull(rdinstal,'') as rdinstal,ifnull(rdmatdate,'') as rdmatdate,intdeposit,ifnull(Instruction,'') from accountmaster "
                        + "where acno='" + accNo + "' ").getResultList();
                if (!selectForSB.isEmpty()) {
                    Vector vecForSB = (Vector) selectForSB.get(0);
                    accDetails.add(vecForSB.get(0).toString());
                    accDetails.add(vecForSB.get(1).toString());
                    accDetails.add(vecForSB.get(2).toString());
                    accDetails.add(vecForSB.get(3).toString());
                    accDetails.add(vecForSB.get(4).toString());
                    accDetails.add(vecForSB.get(7).toString());
                    accDetails.add(vecForSB.get(8).toString());
                    accDetails.add(vecForSB.get(10).toString());
                    accDetails.add(vecForSB.get(5).toString());
                    accDetails.add(vecForSB.get(6).toString());
                    accDetails.add(vecForSB.get(11).toString());
                    accDetails.add(vecForSB.get(12).toString());
                    accDetails.add(vecForSB.get(13).toString());
                    accDetails.add(vecForSB.get(14).toString());
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add(vecForSB.get(9).toString());
                }
                if (address.equalsIgnoreCase("YES")) {
                    List selectFromCustMaster = em.createNativeQuery("Select CrAddress,PrAddress from customermaster where custno="
                            + "substring('" + accNo + "',5,6) and actype='" + acCode + "' and agcode= substring('" + accNo + "',11,2) "
                            + "and brncode='" + orgBrnCode + "'").getResultList();
                    if (!selectFromCustMaster.isEmpty()) {
                        Vector vecForCustMast = (Vector) selectFromCustMaster.get(0);
                        accDetails.set(14, vecForCustMast.get(0).toString());
                        accDetails.set(15, vecForCustMast.get(1).toString());
                    }
                }
            }
            if (acctNature.equalsIgnoreCase("FD") || acctNature.equalsIgnoreCase("MS")) {
                List selectForFDMS = em.createNativeQuery("select custname,ifnull(jtname1,''),ifnull(jtname2,''),ifnull(jtname3,''),"
                        + "ifnull(JTNAME4,''),opermode,accstatus,date_format(openingdt,'%Y%m%d') from td_accountmaster where "
                        + "acno='" + accNo + "' ").getResultList();
                if (!selectForFDMS.isEmpty()) {
                    Vector vecForFDMS = (Vector) selectForFDMS.get(0);
                    accDetails.add(vecForFDMS.get(0).toString());
                    accDetails.add(vecForFDMS.get(1).toString());
                    accDetails.add(vecForFDMS.get(2).toString());
                    accDetails.add(vecForFDMS.get(3).toString());
                    accDetails.add(vecForFDMS.get(4).toString());
                    accDetails.add(vecForFDMS.get(5).toString());
                    accDetails.add(vecForFDMS.get(6).toString());
                    accDetails.add(vecForFDMS.get(7).toString());
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("01/01/1900");
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                }
                if (address.equalsIgnoreCase("")) {
                    List selectFromTdCustMast = em.createNativeQuery("Select CrAddress,PrAddress from td_customermaster where custno="
                            + "substring('" + accNo + "',5,6) and actype='" + acCode + "' and agcode= substring('" + accNo + "',11,2) "
                            + "and brncode='" + orgBrnCode + "'").getResultList();
                    if (!selectFromTdCustMast.isEmpty()) {
                        Vector vecForTdCustMast = (Vector) selectFromTdCustMast.get(0);
                        accDetails.set(14, vecForTdCustMast.get(0).toString());
                        accDetails.set(15, vecForTdCustMast.get(1).toString());
                    }
                }
            }
            if (acctNature.equalsIgnoreCase("OF")) {
                List selectForOF = em.createNativeQuery("select a.custname,ifnull(a.jtname1,''),ifnull(a.jtname2,''),ifnull(a.jtname3,''),"
                        + "ifnull(JTNAME4,''),a.opermode,a.accstatus,date_format(openingdt,'%Y%m%d'),Instruction from td_accountmaster a, "
                        + "td_vouchmst b where b.ofacno ='" + accNo + "' and a.acno = b.acno").getResultList();
                if (!selectForOF.isEmpty()) {
                    Vector vecForOF = (Vector) selectForOF.get(0);
                    accDetails.add(vecForOF.get(0).toString());
                    accDetails.add(vecForOF.get(1).toString());
                    accDetails.add(vecForOF.get(2).toString());
                    accDetails.add(vecForOF.get(3).toString());
                    accDetails.add(vecForOF.get(4).toString());
                    accDetails.add(vecForOF.get(5).toString());
                    accDetails.add(vecForOF.get(6).toString());
                    accDetails.add(vecForOF.get(7).toString());
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("01/01/1900");
                    accDetails.add("");
                    accDetails.add(vecForOF.get(8).toString());
                    accDetails.add("");
                    accDetails.add("");
                    accDetails.add("");
                }
                if (address.equalsIgnoreCase("YES")) {
                    List selectFromTdCusMas = em.createNativeQuery("Select CrAddress,PrAddress from td_customermaster where custno="
                            + "substring('" + accNo + "',5,6) and actype='" + acCode + "' and agcode= substring('" + accNo + "',11,2) "
                            + "and brncode='" + orgBrnCode + "'").getResultList();
                    if (!selectFromTdCusMas.isEmpty()) {
                        Vector vecForTdCusMas = (Vector) selectFromTdCusMas.get(0);
                        accDetails.set(14, vecForTdCusMas.get(0).toString());
                        accDetails.set(15, vecForTdCusMas.get(1).toString());
                    }
                }
            }
            return accDetails;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List getUnverifyInstrument(String orgBrnCode, String tmpFlag, String regisDate) {
        regisDate = regisDate.substring(6) + regisDate.substring(3, 5) + regisDate.substring(0, 2);
        if (tmpFlag.equalsIgnoreCase("A") || tmpFlag.equalsIgnoreCase("B") || tmpFlag.equalsIgnoreCase("G")) {
            List select1 = em.createNativeQuery("Select Acno, txninstno,txninstamt,emflag,vtot From clg_ow_entry Where txndate="
                    + "cast('" + regisDate + "' as datetime) and ReasonForCancel=0 and TxnStatus in ('E') and Emflag='" + tmpFlag
                    + "' And orgnBrcode = '" + orgBrnCode + "' order by vtot").getResultList();
            return select1;
        } else {
            List select2 = em.createNativeQuery("Select Acno, txninstno,txninstamt,emflag,vtot From clg_localchq Where txndate="
                    + "cast('" + regisDate + "' as datetime) and ReasonForCancel=0 and TxnStatus in ('E') and Emflag='" + tmpFlag
                    + "' And orgnBrcode = '" + orgBrnCode + "' order by vtot").getResultList();
            return select2;
        }
    }

    public String owInstDateValidate(String instDate, String regisDate) throws ApplicationException {
        try {
            if (instDate == null || instDate.equalsIgnoreCase("")) {
                return "Instrument date can not be blank";
            }
            regisDate = regisDate.substring(6) + regisDate.substring(3, 5) + regisDate.substring(0, 2);
            String sixMonthChqDt = "20120331";
            String toDt = "";
            int instDtCompare = ymd.parse(instDate).compareTo(ymd.parse(sixMonthChqDt));
            if (instDtCompare <= 0) {
                toDt = CbsUtil.monthAdd(instDate, 6);
            } else {
                toDt = CbsUtil.monthAdd(instDate, 3);
            }
            long outCompareValue = CbsUtil.dayDiff(ymd.parse(toDt), ymd.parse(regisDate));
            if (outCompareValue > 0) {
                return "This is outdated cheque";
            }
            long postCompareValue = CbsUtil.dayDiff(ymd.parse(instDate), ymd.parse(regisDate));
            if (postCompareValue < -1) {
                return "This is postdated cheque";
            }

            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List instrUpdtDelRegBankDetail(String bankCode1, String bankCode2, String bankCode3) {
        List selectBankBranch = em.createNativeQuery("select bankname,branch from clg_bankdirectory where micr ='" + bankCode1 + "' and code ='" + bankCode2 + "' and codeno ='" + bankCode3 + "'").getResultList();
        return selectBankBranch;
    }

    public List loadBranchNames(String banknames) {
        List selectBranchNames = em.createNativeQuery("SELECT BRANCH,code FROM clg_bankdirectory WHERE BANKNAME = '" + banknames + "'").getResultList();
        return selectBranchNames;
    }

    public List generateVoucherNo(String empFlag, String instrDate, String orgBrnCode) {
        if (empFlag.equalsIgnoreCase("A") || empFlag.equalsIgnoreCase("B") || empFlag.equalsIgnoreCase("G")) {
            List generateVouchNo = em.createNativeQuery("SELECT COALESCE(MAX(vtot)+1,1) as tot FROM clg_ow_entry WHERE emflag='" + empFlag + "' AND txndate='" + instrDate + "' and orgnBrCode ='" + orgBrnCode + "' ").getResultList();
            return generateVouchNo;
        } else {
            List generateVouchNo = em.createNativeQuery("SELECT COALESCE(MAX(vtot)+1,1) as tot FROM clg_localchq WHERE emflag='" + empFlag + "' AND txndate='" + instrDate + "' and orgnBrCode ='" + orgBrnCode + "' ").getResultList();
            return generateVouchNo;
        }
    }

    public String cbsOwChqDeposit(List<InstrumentsForTheSameVoucherGrid> arraylist, String acNo, String EnterBy, String emflag,
            String txndate, String obcflag, String TerminalId, String tot, String brCode)
            throws ApplicationException {

        String instDt;
        int tmpPostFlag = 0;
        UserTransaction ut = context.getUserTransaction();
        txndate = txndate.substring(6) + txndate.substring(3, 5) + txndate.substring(0, 2);
        try {
            ut.begin();
            String instNoStr = "";
            for (InstrumentsForTheSameVoucherGrid pojo : arraylist) {
                if (instNoStr.equals("")) {
                    instNoStr = "Chq No:" + pojo.getInstructionNo();
                } else {
                    instNoStr = instNoStr + "," + pojo.getInstructionNo();
                }
            }
            for (InstrumentsForTheSameVoucherGrid pojo : arraylist) {
                instDt = pojo.getInstructionDate();
                instDt = instDt.substring(6) + instDt.substring(3, 5) + instDt.substring(0, 2);

                if (acNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                    tmpPostFlag = 0;
                    List select1 = em.createNativeQuery("SELECT postflag FROM gltable WHERE acno='" + acNo + "'").getResultList();
                    if (!select1.isEmpty()) {
                        Vector vec1 = (Vector) select1.get(0);
                        tmpPostFlag = Integer.parseInt(vec1.get(0).toString());
                    }
                }
                String rs = ftsPostingRemote.getGlTranInfo(acNo, 0, Double.parseDouble(pojo.getAmount()));
                if (!rs.equalsIgnoreCase("True")) {
                    throw new ApplicationException(rs);
                }
                if (emflag.equalsIgnoreCase("A") || emflag.equalsIgnoreCase("B") || emflag.equalsIgnoreCase("G")) {
                    if ((tmpPostFlag == 11 || tmpPostFlag == 12 || tmpPostFlag == 13 || tmpPostFlag == 14)
                            && (!pojo.getBillType().equalsIgnoreCase("GENERAL") && !pojo.getBillType().equalsIgnoreCase("")) && !obcflag.equalsIgnoreCase("OBC")) {

                        Query insertClgOwEntry1 = em.createNativeQuery("INSERT INTO clg_ow_entry (acno,txndate,txnmode,txntype,txnstatus,reasonforcancel,"
                                + "EnterBy,vtot,txninstdate,txninstno,txninstamt,txnbankname,TxnBankAddress,emflag,billtype,alphacode,bcbpno,fyear,iy,obcflag,"
                                + "remarks,trandesc,TerminalId,DrwName,DrwAcno,AreaCode,BnkCode,BranchCode,dt,orgnBrcode,destBrcode,SanNo,TransactionCode,"
                                + "AccountName) values('" + acNo + "','" + txndate + "',3,0,'E',0,'" + EnterBy + "','" + tot + "','" + instDt + "','"
                                + pojo.getInstructionNo() + "','" + pojo.getAmount() + "','" + pojo.getBnkName() + "','" + pojo.getBnkAddress() + "','"
                                + emflag + "','" + pojo.getBillType() + "','" + pojo.getAlphaCode() + "','" + pojo.getBcbpCode() + "','" + pojo.getFyear() + "','"
                                + tmpPostFlag + "','" + obcflag + "','" + instNoStr + ":" + pojo.getRemarks() + "','" + pojo.getTransdes() + "','"
                                + TerminalId + "','" + pojo.getDrawerName() + "','" + pojo.getDrawerAccNo() + "','" + pojo.getCityCode() + "','"
                                + pojo.getBnkCode() + "','" + pojo.getBrnchCode() + "','" + txndate + "','" + brCode + "','" + acNo.substring(0, 2) + "','"
                                + pojo.getAcNosanNo() + "','" + pojo.getTransactionCode() + "','" + pojo.getAccName() + "')");
                        int insertClgOwEntry1Result = insertClgOwEntry1.executeUpdate();
                        if (insertClgOwEntry1Result <= 0) {
                            throw new ApplicationException("Insertion Problem For Circle Type A or B or G");
                        }
                    } else {
                        Query insertClgOwEntry2 = em.createNativeQuery("INSERT INTO clg_ow_entry (iy,acno,txndate,txnmode,txntype,txnstatus,reasonforcancel,"
                                + "EnterBy,vtot,txninstdate,txninstno,txninstamt,txnbankname,TxnBankAddress,emflag,obcflag,remarks,trandesc,TerminalId,DrwName,"
                                + "DrwAcno,AreaCode,BnkCode,BranchCode,orgnBrcode,destBrcode,dt,SanNo,TransactionCode,AccountName) values('" + tmpPostFlag + "','"
                                + acNo + "','" + txndate + "',3,0,'E',0,'" + EnterBy + "','" + tot + "','" + instDt + "','" + pojo.getInstructionNo() + "','"
                                + pojo.getAmount() + "','" + pojo.getBnkName() + "','" + pojo.getBnkAddress() + "','" + emflag + "','" + obcflag + "','"
                                + instNoStr + ":" + pojo.getRemarks() + "','" + pojo.getTransdes() + "','" + TerminalId + "','" + pojo.getDrawerName() + "','"
                                + pojo.getDrawerAccNo() + "','" + pojo.getCityCode() + "','" + pojo.getBnkCode() + "','" + pojo.getBrnchCode() + "','"
                                + brCode + "','" + acNo.substring(0, 2) + "',date_format(curdate(),'%Y%m%d'),'" + pojo.getAcNosanNo() + "','"
                                + pojo.getTransactionCode() + "','" + pojo.getAccName() + "')");
                        int insertClgOwEntry2Result = insertClgOwEntry2.executeUpdate();
                        if (insertClgOwEntry2Result <= 0) {
                            throw new ApplicationException("Insertion Problem For Circle Type A or B.");
                        }
                    }
                } else {
                    if ((tmpPostFlag == 11 || tmpPostFlag == 12 || tmpPostFlag == 13 || tmpPostFlag == 14)
                            && (!pojo.getBillType().equalsIgnoreCase("GENERAL") && !pojo.getBillType().equalsIgnoreCase("")) && !obcflag.equalsIgnoreCase("OBC")) {

                        Query insertClgLovalChq1 = em.createNativeQuery("INSERT INTO clg_localchq (acno,txndate,txnmode,txntype,txnstatus,reasonforcancel,"
                                + "EnterBy,vtot,txninstdate,txninstno,txninstamt,txnbankname,TxnBankAddress,emflag,billtype,alphacode,bcbpno,fyear,iy,obcflag,"
                                + "remarks,trandesc,TerminalId,DrwName,DrwAcno,AreaCode,BnkCode,BranchCode,dt,orgnBrcode,destBrcode) "
                                + "values('" + acNo + "','" + txndate + "',3,0,'E',0,'" + EnterBy + "','" + tot + "','" + instDt + "','"
                                + pojo.getInstructionNo() + "','" + pojo.getAmount() + "','" + pojo.getBnkName() + "','" + pojo.getBnkAddress() + "','"
                                + emflag + "','" + pojo.getBillType() + "','" + pojo.getAlphaCode() + "','" + pojo.getBcbpCode() + "','" + pojo.getFyear() + "','"
                                + tmpPostFlag + "','" + obcflag + "','" + instNoStr + ":" + pojo.getRemarks() + "','" + pojo.getTransdes() + "','" + TerminalId + "','"
                                + pojo.getDrawerName() + "','" + pojo.getDrawerAccNo() + "','" + pojo.getCityCode() + "','" + pojo.getBnkCode() + "','"
                                + pojo.getBrnchCode() + "','" + txndate + "','" + brCode + "','" + acNo.substring(0, 2) + "')");
                        int insertClgLovalChq1Result = insertClgLovalChq1.executeUpdate();
                        if (insertClgLovalChq1Result <= 0) {
                            throw new ApplicationException("Insertion Problem For Other Circle Types");
                        }
                    } else {
                        Query insertClgLovalChq2 = em.createNativeQuery("INSERT INTO clg_localchq (acno,txndate,txnmode,txntype,txnstatus,reasonforcancel,"
                                + "EnterBy,vtot,txninstdate,txninstno,txninstamt,txnbankname,TxnBankAddress,emflag,obcflag,remarks,trandesc,TerminalId,"
                                + "DrwName,DrwAcno,AreaCode,BnkCode,BranchCode,dt,orgnBrcode,destBrcode) values('" + acNo + "','"
                                + txndate + "',3,0,'E',0,'" + EnterBy + "','" + tot + "','" + instDt + "','" + pojo.getInstructionNo() + "','"
                                + pojo.getAmount() + "','" + pojo.getBnkName() + "','" + pojo.getBnkAddress() + "','" + emflag + "','" + obcflag + "','"
                                + instNoStr + ":" + pojo.getRemarks() + "','" + pojo.getTransdes() + "','" + TerminalId + "','" + pojo.getDrawerName() + "','"
                                + pojo.getDrawerAccNo() + "','" + pojo.getCityCode() + "','" + pojo.getBnkCode() + "','" + pojo.getBrnchCode() + "','"
                                + txndate + "','" + brCode + "','" + acNo.substring(0, 2) + "')");
                        int insertClgLovalChq2Result = insertClgLovalChq2.executeUpdate();
                        if (insertClgLovalChq2Result <= 0) {
                            throw new ApplicationException("Insertion Problem For Other Circle Types");
                        }
                    }
                }
            }
            ut.commit();
            return "Success";
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

    public List getLockInfo(String emFlag, String entryDate, String orgBrnCode) {
        entryDate = entryDate.substring(6) + entryDate.substring(3, 5) + entryDate.substring(0, 2);
        List selectLockInfo = em.createNativeQuery("Select status from clg_ow_register where  emflag='" + emFlag + "' and  EntryDate = '"
                + entryDate + "' and brncode = '" + orgBrnCode + "'").getResultList();
        return selectLockInfo;
    }

    public List getDescription(String emFlag) {
        List descList = em.createNativeQuery("select circleDesc from parameterinfo_clg where  circleType='" + emFlag + "'").getResultList();
        return descList;
    }

    public List getDetailsOnPopup(String instrNo, String accNo, String emFlag, String vtot, String tableName) {

        List list = em.createNativeQuery("select * from " + tableName + " where  txninstno='" + instrNo + "' and acno='" + accNo
                + "' and emflag='" + emFlag + "' and vtot='" + vtot + "'").getResultList();
        return list;
    }

    public String cbsOwEntryUpdate(String instrDate, String flag, String accNo, String txnAmt,
            String txnNo, String txnNoOld, String bnkAddr, String bnkName, String vt,
            String regDate, String oldAccNo, String brCode, String micrCode1Popup, String micrCode2Popup, String micrCode3Popup) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            instrDate = instrDate.substring(6) + instrDate.substring(3, 5) + instrDate.substring(0, 2);
            String result = owInstDateValidate(instrDate, regDate);
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }
            if (flag.equalsIgnoreCase("A") || flag.equalsIgnoreCase("B") || flag.equalsIgnoreCase("G")) {
                Query update1 = em.createNativeQuery("Update clg_ow_entry set txninstamt ='" + txnAmt + "' , txninstno ='"
                        + txnNo + "', txninstdate ='" + instrDate + "' ,txnbankaddress ='" + bnkAddr + "', txnbankname ='" + bnkName
                        + "',AreaCode = '" + micrCode1Popup + "',BnkCode = '" + micrCode2Popup + "',BranchCode = '" + micrCode3Popup + "'  where  txninstno= '"
                        + txnNoOld + "'  and acno= '" + oldAccNo + "' and vtot='" + vt + "' and orgnBrcode='" + brCode + "'");
                int update1Result = update1.executeUpdate();
                if (update1Result <= 0) {
                    throw new ApplicationException("Updation Failed");
                }
            } else {
                Query update2 = em.createNativeQuery("Update clg_localchq set txninstamt ='" + txnAmt + "' , txninstno ='"
                        + txnNo + "', txninstdate ='" + instrDate + "' ,txnbankaddress ='" + bnkAddr + "', txnbankname ='" + bnkName + "',AreaCode = '"
                        + micrCode1Popup + "',BnkCode = '" + micrCode2Popup + "',BranchCode = '" + micrCode3Popup + "' "
                        + "where  txninstno= '" + txnNoOld + "'  and acno= '" + oldAccNo + "' and vtot='" + vt + "' and orgnBrcode='" + brCode + "'");
                int update2Result = update2.executeUpdate();
                if (update2Result <= 0) {
                    throw new ApplicationException("Updation Failed.");
                }
            }
            ut.commit();
            return "Transaction successfully updated.";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } //return result;
            catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String cbsInstrumentDateCheckPopup(String instrDate, String accNo, String oldInstrDate)
            throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        String result = "";
        int DTDIFF;
        try {
            ut.begin();
            instrDate = instrDate.substring(6) + instrDate.substring(3, 5) + instrDate.substring(0, 2);

            oldInstrDate = oldInstrDate.substring(6) + oldInstrDate.substring(3, 5) + oldInstrDate.substring(0, 2);
            List selectDateDiff = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY, '" + instrDate + "', '" + oldInstrDate + "')").getResultList();
            Vector vecDateDiff = (Vector) selectDateDiff.get(0);
            DTDIFF = Integer.parseInt(vecDateDiff.get(0).toString());

            if (DTDIFF > 184) {
                ut.rollback();
                return "Invalid Instrument Date.";
            }
            List selectDateDiffq = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY, '" + oldInstrDate + "', '" + instrDate + "')").getResultList();
            Vector vecDateDiffq = (Vector) selectDateDiffq.get(0);
            int dateDiffq = Integer.parseInt(vecDateDiffq.get(0).toString());
            if (dateDiffq > 1) {
                ut.rollback();
                return "You Can not Fill Details Of Post Dated Cheques.";
            }

            ut.commit();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String cbsOwEntryDelete(String EMPFLAG, String ACNO, String INSTNO, String VTOT,
            String AUTHBY, String brCode) throws ApplicationException {
        String result = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (EMPFLAG.equalsIgnoreCase("A") || EMPFLAG.equalsIgnoreCase("B") || EMPFLAG.equalsIgnoreCase("G")) {
                List select1 = em.createNativeQuery("SELECT TxnStatus FROM clg_ow_entry where acno='" + ACNO + "' and txninstno='" + INSTNO + "' and vtot='"
                        + VTOT + "' and orgnBrcode='" + brCode + "'").getResultList();
                if (select1.isEmpty()) {
                    throw new ApplicationException("Data not found corresponding to this record.");
                }
                Vector vec1 = (Vector) select1.get(0);

                if (!vec1.get(0).toString().equalsIgnoreCase("E")) {
                    throw new ApplicationException("This Instrument is not in Entered State.");
                }
                Query insert1 = em.createNativeQuery("INSERT INTO clg_ow_deleted (Acno, TxnMode, TxnType, TxnDate, TxnInstNo, TxnInstAmt, Txnstatus, "
                        + "TxnInstDate, TxnBankName, TxnBankAddress, AreaCode, BnkCode, BranchCode, remarks, ReasonForCancel, vtot, EMFlag, EnterBy, "
                        + "AuthBy, BillType, AlphaCode, BcBpNo, Fyear, IY, OBCFLAG, TranDesc, dt, DrwAcno, DrwName, TerminalId, VerifiedBy, Sbal, "
                        + "orgnbrcode, destbrcode,SanNo,TransactionCode,AccountName) select * from clg_ow_entry WHERE acno='" + ACNO + "' and "
                        + "txninstno='" + INSTNO + "' and vtot='" + VTOT + "' and orgnBrcode='" + brCode + "'");
                int insert1Result = insert1.executeUpdate();
                if (insert1Result <= 0) {
                    throw new ApplicationException("Error in insertion in Clg_Ow_Deleted");
                }
                Query update1 = em.createNativeQuery("UPDATE clg_ow_deleted SET AuthBy='" + AUTHBY + "', dt=CURRENT_TIMESTAMP");
                int update1Result = update1.executeUpdate();
                if (update1Result <= 0) {
                    throw new ApplicationException("Updation problem in Clg_Ow_Deleted");
                }
                Query delete1 = em.createNativeQuery("DELETE from clg_ow_entry where acno='" + ACNO + "' and txninstno='" + INSTNO + "' and vtot='"
                        + VTOT + "' and orgnBrcode='" + brCode + "'");
                int delete1Result = delete1.executeUpdate();
                if (delete1Result <= 0) {
                    throw new ApplicationException("Deletion Error from Clg_OW_Entry");
                }
            } else {
                List select3 = em.createNativeQuery("SELECT TxnStatus FROM clg_localchq where acno='" + ACNO + "' and txninstno='" + INSTNO + "' and vtot='"
                        + VTOT + "' and orgnBrcode='" + brCode + "'").getResultList();
                if (select3.isEmpty()) {
                    throw new ApplicationException("Data not found corresponding to this record.");
                }
                Vector v4 = (Vector) select3.get(0);
                if (!v4.get(0).toString().equalsIgnoreCase("E")) {
                    throw new ApplicationException("This Instrument is not in Entered State.");
                }
                Query insert2 = em.createNativeQuery("INSERT INTO clg_ow_deleted (Acno, TxnMode, TxnType, TxnDate, TxnInstNo, TxnInstAmt, Txnstatus, "
                        + "TxnInstDate, TxnBankName, TxnBankAddress, AreaCode, BnkCode, BranchCode, remarks, ReasonForCancel, vtot, EMFlag, EnterBy, AuthBy, "
                        + "BillType, AlphaCode, BcBpNo, Fyear, IY, OBCFLAG, TranDesc, dt, DrwAcno, DrwName, TerminalId, VerifiedBy, Sbal, orgnbrcode, "
                        + "destbrcode) select * from clg_localchq WHERE acno='" + ACNO + "' and txninstno='" + INSTNO + "' and vtot='" + VTOT + "' and "
                        + "orgnBrcode='" + brCode + "'");
                int insert2Result = insert2.executeUpdate();
                if (insert2Result <= 0) {
                    throw new ApplicationException("Error in insertion in Clg_Ow_Deleted.");
                }
                Query update2 = em.createNativeQuery("UPDATE clg_ow_deleted SET AuthBy='" + AUTHBY + "', dt=CURRENT_TIMESTAMP");
                int update2Result = update2.executeUpdate();
                if (update2Result <= 0) {
                    throw new ApplicationException("Updation problem in Clg_Ow_Deleted.");
                }
                Query delete2 = em.createNativeQuery("DELETE from clg_localchq where acno='" + ACNO + "' and txninstno='" + INSTNO + "' and vtot='" + VTOT + "' and orgnBrcode='" + brCode + "'");
                int delete2Result = delete2.executeUpdate();
                if (delete2Result <= 0) {
                    throw new ApplicationException("Deletion Error from clg_localchq");
                }
            }
            ut.commit();
            return result = "Transaction successfully deleted.";
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

    public List getDuplicateChqInfo(String accNo, String instrNo, String regisDate, String micr1, String micr2, String micr3, String EMPFLAG, String orgnbrcode) {
        List list = new ArrayList();
        if (EMPFLAG.equalsIgnoreCase("A") || EMPFLAG.equalsIgnoreCase("B") || EMPFLAG.equalsIgnoreCase("G")) {
            list = em.createNativeQuery("select *from clg_ow_entry where Acno='" + accNo + "' and TxnInstNo='" + instrNo + "' and TxnDate='" + regisDate + "' and AreaCode='" + micr1 + "' and BnkCode='" + micr2 + "' and BranchCode='" + micr3 + "'").getResultList();
        } else {
            list = em.createNativeQuery("select *from clg_localchq where Acno='" + accNo + "' and TxnInstNo='" + instrNo + "' and TxnDate='" + regisDate + "' and AreaCode='" + micr1 + "' and BnkCode='" + micr2 + "' and BranchCode='" + micr3 + "'").getResultList();
        }
        return list;
    }

    public String dateDiff(String acBrCode, String accNo, String orgnCode) throws ApplicationException {
        String diff = new String();
        try {
            String acCode = ftsPostingRemote.getAccountCode(accNo);
            List list1 = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acCode + "'").getResultList();
            if (!list1.isEmpty()) {
                Vector vec1 = (Vector) list1.get(0);
                if (!vec1.get(0).toString().equalsIgnoreCase("OF") && !vec1.get(0).toString().equalsIgnoreCase("PO")) {
                    List list2 = em.createNativeQuery("SELECT date_format(date,'%Y%m%d') FROM bankdays WHERE DayEndFlag='N' AND brncode = '" + orgnCode + "'").getResultList();
                    Vector vec2 = (Vector) list2.get(0);
                    if (vec1.get(0).toString().equalsIgnoreCase("FD") || vec1.get(0).toString().equalsIgnoreCase("MS")) {
                        List list3 = em.createNativeQuery("select date_format(openingdt,'%Y%m%d') 'openingdt' from td_accountmaster where acno='" + accNo + "'").getResultList();
                        if (!list3.isEmpty()) {
                            Vector vec3 = (Vector) list3.get(0);
                            List list4 = em.createNativeQuery("select TIMESTAMPDIFF(MONTH,'" + vec3.get(0).toString() + "','" + vec2.get(0).toString() + "')").getResultList();
                            Vector vec4 = (Vector) list4.get(0);
                            diff = vec4.get(0).toString();
                        }

                    } else {
                        List list3 = em.createNativeQuery("select openingdt 'openingdt' from accountmaster where acno='" + accNo + "'").getResultList();
                        if (!list3.isEmpty()) {
                            Vector vec3 = (Vector) list3.get(0);
                            List list4 = em.createNativeQuery("select TIMESTAMPDIFF(MONTH,'" + vec3.get(0).toString() + "','" + vec2.get(0).toString() + "')").getResultList();
                            Vector vec4 = (Vector) list4.get(0);
                            diff = vec4.get(0).toString();
                        }

                    }

                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return diff;
    }
    /*End of Outward Clearing Cheque Deposite*/

    public String accStatus(String acNo, String orgnCode) throws ApplicationException {
        String msg = "", msg1 = "";
        try {
            int accountStatus;
            List statusList;
            Vector statusVect;
            String acNature = ftsPostingRemote.getAccountNature(acNo);
            if (acNature.equalsIgnoreCase(CbsConstant.MS_AC) || acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                statusList = em.createNativeQuery("SELECT ACCSTATUS FROM  td_accountmaster  WHERE ACNO = '" + acNo + "'").getResultList();
                statusVect = (Vector) statusList.get(0);
            } else {
                statusList = em.createNativeQuery("SELECT ACCSTATUS FROM  accountmaster  WHERE ACNO = '" + acNo + "'").getResultList();
                statusVect = (Vector) statusList.get(0);
            }
            accountStatus = Integer.parseInt(statusVect.get(0).toString());
            String dateDiff = dateDiff(acNo.substring(0, 2), acNo, orgnCode);

            if (!dateDiff.equals("")) {
                if (Integer.parseInt(dateDiff) < 7) {
                    msg1 = ("New Account");
                }
            }
            if (accountStatus == 2) {
                msg = ("Account Has been marked Inoperative");
            } else if (accountStatus == 3) {
                msg = ("Account Has been marked Suit Filed");
            } else if (accountStatus == 4) {
                throw new ApplicationException("Account Has been marked Frozen");
            } else if (accountStatus == 5) {
                msg = ("Account Has been marked Recalled");
            } else if (accountStatus == 6) {
                msg = ("Account Has been marked Decreed");
            } else if (accountStatus == 7) {
                msg = ("Withdrawal is not Allowed in this Account");
            } else if (accountStatus == 8) {
                throw new ApplicationException("Account Has been marked Operation Stopped");
            } else if (accountStatus == 9) {
                throw new ApplicationException("Account Has been Closed");
            } else if (accountStatus == 11) {
                msg = ("This Account is SUB STANDARD Account");
            } else if (accountStatus == 12) {
                msg = ("This Account is DOUBT FUL Account");
            } else if (accountStatus == 13) {
                msg = ("This Account is LOSS Account");
            } else if (accountStatus == 14) {
                msg = ("This Account is PROTESTED BILL Account");
            } else {
                msg = ("");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        if (!msg1.equalsIgnoreCase("")) {
            if (!msg.equalsIgnoreCase("")) {
                msg = msg1 + ", " + msg;
            } else {
                msg = msg1;
            }
        } else {
            msg = msg;
        }
        return msg;
    }

    /*Start of Outward Register Verification*/
    public List regDateCombo(String brCode, String emFlag) throws ApplicationException {
        List regDt = null;
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                regDt = em.createNativeQuery("select distinct date_format(txndate,'%d/%m/%Y') from clg_ow_entry where emflag ='" + emFlag + "' and orgnBrcode = '" + brCode + "'"
                        + " and txndate not in (select entrydate from clg_ow_register where status ='CLOSE' and emflag='" + emFlag + "' "
                        + " and brncode = '" + brCode + "')").getResultList();
            } else {
                regDt = em.createNativeQuery("select distinct date_format(txndate,'%d/%m/%Y') from clg_localchq where  emflag ='" + emFlag + "' And orgnBrcode = '" + brCode + "'"
                        + " and txndate not in (select entrydate from clg_ow_register where status ='CLOSE' and emflag='" + emFlag + "' "
                        + " and brncode = '" + brCode + "')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return regDt;
    }

    public List terminalCombo(String brCode) throws ApplicationException {
        List terminal = null;
        try {
            terminal = em.createNativeQuery("Select Distinct TerminalId From clg_ow_entry where TerminalId is not null and orgnBrcode = '" + brCode + "'"
                    + " Union"
                    + " Select Distinct TerminalId From clg_localchq where TerminalId is not null and orgnBrcode = '" + brCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return terminal;
    }

    public List usersCombo(String brCode, String emFlag, String regDt) throws ApplicationException {
        List user = null;
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                user = em.createNativeQuery("Select Distinct EnterBy From clg_ow_entry where emflag='" + emFlag + "' "
                        + " and TxnDate = '" + regDt + "' and orgnBrcode = '" + brCode + "'").getResultList();
            } else {
                user = em.createNativeQuery("Select Distinct EnterBy From clg_localchq where emflag='" + emFlag + "' "
                        + " and TxnDate = '" + regDt + "' and orgnBrcode = '" + brCode + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return user;
    }

    public List loadInstrumentDetails(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException {
        List instDetail = null;
        String query = "";
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                query = "select a.acno,coalesce(b.custname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),coalesce(b.jtname1,''),coalesce(b.jtname2,''),coalesce(b.jtname3,''),coalesce(b.jtname4,''),coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_ow_entry a, accountmaster b"
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno "
                        + " and a.orgnBrcode = '" + brCode + "'";

                if (userFlag == true) {
                    query = query + " and a.EnterBy = '" + user + "'";
                }

                if (terminalFlag == true) {
                    query = query + " and a.TerminalId = '" + terminal + "'";
                }

                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query = query + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query = query + " and a.Txnstatus = 'V'";
                    }
                }

                if (by.equalsIgnoreCase("Acno")) {
                    query = query + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query = query + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query).getResultList();
            } else {
                query = "select a.acno,coalesce(b.custname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),coalesce(b.jtname1,''),coalesce(b.jtname2,''),coalesce(b.jtname3,''),coalesce(b.jtname4,''),coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_localchq a, accountmaster b "
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno"
                        + " and a.orgnBrcode = '" + brCode + "'";
                String query1 = query;
                if (userFlag == true) {
                    query1 = query + " and a.EnterBy = '" + user + "'";
                } else {
                    query1 = query;
                }

                String query2 = query1;
                if (terminalFlag == true) {
                    query2 = query1 + " and a.TerminalId = '" + terminal + "'";
                } else {
                    query2 = query1;
                }

                String query3 = query2;
                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query3 = query2 + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query3 = query2 + " and a.Txnstatus = 'V'";
                    }
                } else {
                    query3 = query2;
                }

                String query4 = query3;
                if (by.equalsIgnoreCase("Acno")) {
                    query4 = query3 + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query4 = query3 + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query4).getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return instDetail;
    }

    public List statusDetail(String brCode, String emFlag, String regDt) throws ApplicationException {
        List statusLt = null;
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                statusLt = em.createNativeQuery("select pcount,ifnull(pamt,0) pamt,vcount,ifnull(vamt,0) vamt,(pcount+vcount) as tcount, ifnull(pamt,0)+ifnull(vamt,0) as tamt from"
                        + "(select pcount as pcount,pamt as pamt,vcount as vcount,vamt as vamt from "
                        + "(select count(acno) as pcount,sum(txninstamt) as pamt from clg_ow_entry where txnstatus <> 'v' and txndate='" + regDt + "' and emflag='" + emFlag + "' "
                        + "and orgnbrcode = '" + brCode + "') as pending, (select count(acno) as vcount,sum(txninstamt) as vamt from clg_ow_entry where txnstatus = 'v' "
                        + "and txndate='" + regDt + "' and emflag='" + emFlag + "' and orgnbrcode = '" + brCode + "') as verified) as total").getResultList();
            } else {
                statusLt = em.createNativeQuery("select pcount,ifnull(pamt,0) pamt,vcount,ifnull(vamt,0) vamt,(pcount+vcount) as tcount,ifnull(pamt,0)+ifnull(vamt,0) as tamt from"
                        + "(select pcount as pcount,pamt as pamt,vcount as vcount,vamt as vamt from "
                        + "(select count(acno) as pcount,sum(txninstamt) as pamt from clg_localchq where txnstatus <> 'v' and txndate='" + regDt + "' "
                        + "and emflag='" + emFlag + "' and orgnbrcode = '" + brCode + "') as pending,(select count(acno) as vcount,sum(txninstamt) as vamt "
                        + "from clg_localchq where txnstatus = 'v' and txndate='" + regDt + "' and emflag='" + emFlag + "' and orgnbrcode = '" + brCode + "') "
                        + "as verified) total").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return statusLt;
    }

    public String verificationOfInstrument(String brCode, String emFlag, String vtot,
            String instNo, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                Query updateQuery = em.createNativeQuery("Update clg_ow_entry Set TxnStatus='V', VerifiedBy = '" + authBy + "' Where TxnInstNo = '" + instNo + "' "
                        + " and vtot =" + vtot + " And ORGNBRCODE = '" + brCode + "'");
                var = updateQuery.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("Update clg_localchq Set TxnStatus='V' Where TxnInstNo = '" + instNo + "' and vtot = " + vtot + " "
                        + " And ORGNBRCODE = '" + brCode + "'");
                var = updateQuery.executeUpdate();
            }

            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "INSTRUMENT NOT VERIFIED.";
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String unverificationOfInstrument(String brCode, String emFlag, String vtot,
            String instNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer var = 0;
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                Query updateQuery = em.createNativeQuery("Update clg_ow_entry Set TxnStatus='E' Where TxnInstNo = '" + instNo + "' "
                        + " and vtot =" + vtot + " And ORGNBRCODE = '" + brCode + "'");
                var = updateQuery.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("Update clg_localchq Set TxnStatus='E' Where TxnInstNo = '" + instNo + "' and vtot = " + vtot + " "
                        + " And ORGNBRCODE = '" + brCode + "'");
                var = updateQuery.executeUpdate();
            }

            if (var > 0) {
                ut.commit();
                return "true";
            } else {
                ut.rollback();
                return "INSTRUMENT NOT UNVERIFIED.";
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List glTableInstLoad(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException {
        List instDetail = null;
        String query = "";
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                query = "select a.acno,coalesce(b.acname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),'','','','',coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_ow_entry a, gltable b"
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno "
                        + " and a.orgnBrcode = '" + brCode + "'";

                if (userFlag == true) {
                    query = query + " and a.EnterBy = '" + user + "'";
                }

                if (terminalFlag == true) {
                    query = query + " and a.TerminalId = '" + terminal + "'";
                }

                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query = query + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query = query + " and a.Txnstatus = 'V'";
                    }
                }

                if (by.equalsIgnoreCase("Acno")) {
                    query = query + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query = query + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query).getResultList();
            } else {
                query = "select a.acno,coalesce(b.acname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),'','','','',coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_localchq a, gltable b "
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno"
                        + " and a.orgnBrcode = '" + brCode + "'";
                String query1 = query;
                if (userFlag == true) {
                    query1 = query + " and a.EnterBy = '" + user + "'";
                } else {
                    query1 = query;
                }
                String query2 = query1;
                if (terminalFlag == true) {
                    query2 = query1 + " and a.TerminalId = '" + terminal + "'";
                } else {
                    query2 = query1;
                }
                String query3 = query2;
                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query3 = query2 + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query3 = query2 + " and a.Txnstatus = 'V'";
                    }
                } else {
                    query3 = query2;
                }
                String query4 = query3;
                if (by.equalsIgnoreCase("Acno")) {
                    query4 = query3 + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query4 = query3 + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query4).getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return instDetail;
    }

    public List loadFDInstrumentDetails(String brCode, String emFlag, String regDt, String user,
            String terminal, String status, String by, boolean userFlag, boolean terminalFlag,
            boolean statusFlag) throws ApplicationException {
        List instDetail = null;
        String query = "";
        try {
            if (emFlag.equalsIgnoreCase("A") || emFlag.equalsIgnoreCase("B") || emFlag.equalsIgnoreCase("G")) {
                query = "select a.acno,coalesce(b.custname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),coalesce(b.jtname1,''),coalesce(b.jtname2,''),coalesce(b.jtname3,''),coalesce(b.jtname4,''),coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_ow_entry a, td_accountmaster b"
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno "
                        + " and a.orgnBrcode = '" + brCode + "'";

                if (userFlag == true) {
                    query = query + " and a.EnterBy = '" + user + "'";
                }

                if (terminalFlag == true) {
                    query = query + " and a.TerminalId = '" + terminal + "'";
                }

                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query = query + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query = query + " and a.Txnstatus = 'V'";
                    }
                }

                if (by.equalsIgnoreCase("Acno")) {
                    query = query + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query = query + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query).getResultList();
            } else {
                query = "select a.acno,coalesce(b.custname,''),coalesce(a.txninstno,''),coalesce(a.txninstamt,''),coalesce(a.txnstatus,''),ifnull(date_format(a.txninstdate,'%d/%m/%Y'),''),coalesce(a.txnbankname,''),coalesce(a.txnbankaddress,''),"
                        + " coalesce(a.drwacno,''),coalesce(a.drwname,''),coalesce(a.enterby,''),coalesce(a.vtot,''),coalesce(b.jtname1,''),coalesce(b.jtname2,''),coalesce(b.jtname3,''),coalesce(b.jtname4,''),coalesce(a.areacode,''),coalesce(a.bnkcode,''),coalesce(a.branchcode,'') from clg_localchq a, td_accountmaster b "
                        + " where emflag='" + emFlag + "' and a.txndate='" + regDt + "' and a.acno=b.acno"
                        + " and a.orgnBrcode = '" + brCode + "'";
                String query1 = query;
                if (userFlag == true) {
                    query1 = query + " and a.EnterBy = '" + user + "'";
                } else {
                    query1 = query;
                }

                String query2 = query1;
                if (terminalFlag == true) {
                    query2 = query1 + " and a.TerminalId = '" + terminal + "'";
                } else {
                    query2 = query1;
                }

                String query3 = query2;
                if (statusFlag == true) {
                    if (status.equalsIgnoreCase("PENDING")) {
                        query3 = query2 + " and a.Txnstatus <> 'V'";
                    } else if (status.equalsIgnoreCase("VERIFIED")) {
                        query3 = query2 + " and a.Txnstatus = 'V'";
                    }
                } else {
                    query3 = query2;
                }

                String query4 = query3;
                if (by.equalsIgnoreCase("Acno")) {
                    query4 = query3 + " Order by substring(a.acno,5,6)";
                } else if (by.equalsIgnoreCase("Vtot")) {
                    query4 = query3 + " Order by a.Vtot";
                }
                instDetail = em.createNativeQuery(query4).getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return instDetail;
    }
    /*End of Outward Register Verification*/

    /*Start of Local Cheque and High Value Clearing*/
    public List loadReasonForReturnInBean() throws ApplicationException {
        List listSelectDescription = null;
        try {
            Query selectDescription = em.createNativeQuery("select code,Description from codebook where groupcode=13 and code <>0");
            listSelectDescription = selectDescription.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return listSelectDescription;
    }

    public List loadPendingDate(String emFlag, String brCode) throws ApplicationException {
        List listForLoadPendingdate = null;
        try {
            Query selectPendingDate = em.createNativeQuery("select distinct date_format(txndate,'%d/%m/%Y') as txndate "
                    + "from clg_localchq where reasonforcancel=0 and emflag='" + emFlag + "' and orgnbrcode='" + brCode + "'");
            listForLoadPendingdate = selectPendingDate.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return listForLoadPendingdate;
    }

    public List loadCkqGridInBean(String emFlag, String pendingdate, String brCode) throws ApplicationException {
        List listForEmflag = null;
        UserTransaction ut = context.getUserTransaction();

        Query selectForEmflagC = em.createNativeQuery("select * from clg_localchq where emflag='" + emFlag + "' and txndate = '" + pendingdate
                + "' and orgnbrcode='" + brCode + "'");
        listForEmflag = selectForEmflagC.getResultList();
        if (listForEmflag.isEmpty()) {
            throw new ApplicationException("Data does not exist.");
        }
        try {
            ut.begin();
            Query updateClgLocalChq = em.createNativeQuery("Update clg_localchq set txnstatus='P' where txnstatus='V' and emflag='" + emFlag
                    + "' and txndate = '" + pendingdate + "' and orgnbrcode='" + brCode + "'");
            int rs = updateClgLocalChq.executeUpdate();
            ut.commit();
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }

        }
        return listForEmflag;
    }

    public List getDetailsFromChqGrid1(String accNo, String instrNo, String brCode) {
        Query selectForDetailsFromChqGrid1 = em.createNativeQuery("select txninstno,txninstdate,txninstamt,txnbankname,txnbankaddress, acno,emflag,"
                + "billtype,bcbpno from clg_localchq where acno ='" + accNo + "'and txninstno = '" + instrNo + "' and orgnbrcode='" + brCode + "'");
        List listForDetailsFromChqGrid1 = selectForDetailsFromChqGrid1.getResultList();
        return listForDetailsFromChqGrid1;
    }

    public List getDetailsFromChqGrid2(String accNo, String instrNo, String brCode) {
        Query selectForDetailsFromChqGrid2 = em.createNativeQuery("select txninstno,txninstdate,txninstamt,txnbankname,txnbankaddress,c.acno,"
                + "custname,emflag,coalesce(billtype,'')as billtype,coalesce(BCBPNO,'')as BCBPNO from clg_localchq c,accountmaster a where "
                + "c.acno = a.acno and c.acno = '" + accNo + "'and txninstno = '" + instrNo + "' and orgnbrcode='" + brCode + "'");
        List listForDetailsFromChqGrid2 = selectForDetailsFromChqGrid2.getResultList();
        return listForDetailsFromChqGrid2;
    }

    public String clickOnClearButtonInBean(String brnCode) {
        String bFlag = "true";
        Query queryForMyDate = em.createNativeQuery("select date_format(curdate(),'%Y%m%d')");
        List listForMyDate = queryForMyDate.getResultList();
        Vector vrForMyDate = (Vector) listForMyDate.get(0);
        String strForMyDate = vrForMyDate.get(0).toString();

        Query queryForTempbd = em.createNativeQuery("select MIN(date) FROM bankdays WHERE dayendflag='N' and brncode='" + brnCode + "'");
        List listForTempbd = queryForTempbd.getResultList();
        Vector vrForTempbd = (Vector) listForTempbd.get(0);
        String strForTempbd = vrForTempbd.get(0).toString();

        Query queryForServerDate = em.createNativeQuery("select date_format(curdate(),'%Y%m%d')");
        List listForServerDate = queryForServerDate.getResultList();
        Vector vrForServerDate = (Vector) listForServerDate.get(0);
        String strForServerDate = vrForServerDate.get(0).toString();

        Calendar calender1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();
        calender1.set(Integer.parseInt(strForMyDate.substring(0, 4)), Integer.parseInt(strForMyDate.substring(4, 6)), Integer.parseInt(strForMyDate.substring(6)));
        calendar2.set(Integer.parseInt(strForTempbd.substring(0, 4)), Integer.parseInt(strForTempbd.substring(4, 6)), Integer.parseInt(strForTempbd.substring(6)));
        calendar3.set(Integer.parseInt(strForServerDate.substring(0, 4)), Integer.parseInt(strForServerDate.substring(4, 6)), Integer.parseInt(strForServerDate.substring(6)));
        if (calendar2.equals(calender1)) {
            bFlag = "false";
            return bFlag;
        }
        return bFlag;
    }

    public String clgLocalTestNew(String emFlag, String txnDate, String authBy, String accNo, String amount,
            String instrNo, String name, String orgBrnCode) throws ApplicationException {
        String result = "System error occured.";
        try {
            txnDate = txnDate.substring(6) + txnDate.substring(3, 5) + txnDate.substring(0, 2);
            Query selectForClg = em.createNativeQuery("select c.acno,c.TxnInstAmt,c.reasonforcancel,c.enterby,ifnull(a.acctnature,''),c.obcflag,c.txninstno,coalesce(c.billtype,'')as billtype,"
                    + "c.vtot,coalesce(c.fyear,'')as fyear,coalesce(c.bcbpno,'')as bcbpno,coalesce(c.alphacode,'')as alphacode,c.txninstdate ,ifnull(ifnull(am.custname,ta.custname),gl.acname) as custname,"
                    + "c.trandesc,c.remarks,c.orgnbrcode,c.destbrcode from clg_localchq c left join accounttypemaster a on substring(c.acno,3,2)=a.acctcode "
                    + "left join accountmaster am on am.acno=c.acno left join td_accountmaster ta on ta.acno=c.acno left join gltable gl on gl.acno=c.acno "
                    + "where c.ACNO='" + accNo + "' AND c.txninstno='" + instrNo + "' AND c.TxnInstAmt='" + amount + "' AND c.txndate='" + txnDate + "' "
                    + "and c.emflag='" + emFlag + "' and c.txnstatus<>'H' and c.orgnbrcode='" + orgBrnCode + "' order by c.billtype,c.vtot");
            List listForClg = selectForClg.getResultList();
            result = clearLocalCheques(listForClg, emFlag, txnDate, authBy, accNo, amount, instrNo, name, "S");
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String coverOfClearButton(String emFlag, String txnDate, String authBy, String accNo,
            String amount, String instrNo, String orgBrnCode, String auth1, String name) throws ApplicationException {
        String result = new String();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = clgLocalTestNew(emFlag, txnDate, authBy, accNo, amount, instrNo, name, orgBrnCode);
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException("Problem in Clearing  :" + msg);
            }

            txnDate = txnDate.substring(6) + txnDate.substring(3, 5) + txnDate.substring(0, 2);
            List listForTxnStatus = em.createNativeQuery("select TXNSTATUS from clg_localchq where txnDate = '" + txnDate + "' and Emflag = '"
                    + emFlag + "' and orgnbrcode='" + orgBrnCode + "'").getResultList();
            if (listForTxnStatus.isEmpty()) {
                Query update = em.createNativeQuery("Update clg_ow_register Set Status = 'Cleared' , PostedBy = '" + authBy + "',clearedBy = '"
                        + auth1 + "',summary='' Where EntryDate='" + txnDate + "'and Emflag='" + emFlag + "' and brncode='" + orgBrnCode + "'");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Problem in Clearing because the status has not Updated.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                smsFacade.sendTransactionalSms(SmsType.CLEARING_DEPOSIT, accNo, 1, 0, Double.parseDouble(amount),
                        dmyOne.format(ymd.parse(txnDate)), "");
            } catch (Exception ex) {
                System.out.println("Error SMS Sending-->A/c is::" + accNo + " And Amount is::" + Double.parseDouble(amount));
            }
            //End Here
            result = "Transaction Inserted Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return result;
    }

    public String coverOfAllClearButton(String emFlag, String txnDate, String authBy, String orgBrnCode,
            String auth1, List<ChqGridDetails> chqGridDetails) throws ApplicationException {
        String result = new String();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = clgLocalTest(emFlag, txnDate, authBy, orgBrnCode);
            if (!msg.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException("Problem in Clearing  :" + msg);
            }

            txnDate = txnDate.substring(6) + txnDate.substring(3, 5) + txnDate.substring(0, 2);
            List listForTxnStatus = em.createNativeQuery("select TXNSTATUS from clg_localchq where txnDate = '" + txnDate + "' and Emflag = '"
                    + emFlag + "' and orgnbrcode='" + orgBrnCode + "'").getResultList();
            if (listForTxnStatus.isEmpty()) {
                Query update = em.createNativeQuery("Update clg_ow_register Set Status = 'Cleared' , PostedBy = '" + authBy + "',clearedBy = '"
                        + auth1 + "',summary='' Where EntryDate='" + txnDate + "'and Emflag='" + emFlag + "' and brncode='" + orgBrnCode + "'");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Problem in Clearing because the status has not Updated.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                if (!chqGridDetails.isEmpty()) {
                    List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                    for (int s = 0; s < chqGridDetails.size(); s++) {
                        SmsToBatchTo to = new SmsToBatchTo();
                        ChqGridDetails obj = chqGridDetails.get(s);

                        to.setAcNo(obj.getAccNo());
                        to.setCrAmt(obj.getAmount().doubleValue());
                        to.setDrAmt(0d);
                        to.setTranType(1);
                        to.setTy(0);
                        to.setTxnDt(dmyOne.format(ymd.parse(txnDate)));
                        to.setTemplate(SmsType.CLEARING_DEPOSIT);

                        smsList.add(to);
                    }
                    smsFacade.sendSmsToBatch(smsList);
                }
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Batch In "
                        + "Transfer Authorization." + e.getMessage());
            }
            //End here
            result = "Transaction Inserted Successfully";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return result;
    }

    public String clgLocalTest(String emFlag, String txnDate, String authBy, String orgBrnCode) throws ApplicationException {
        String result = "System error occured.";
        try {
            txnDate = txnDate.substring(6) + txnDate.substring(3, 5) + txnDate.substring(0, 2);
            Query selectForClg = em.createNativeQuery("select c.acno,c.TxnInstAmt,c.reasonforcancel,c.enterby,ifnull(a.acctnature,''),c.obcflag,"
                    + "c.txninstno,coalesce(c.billtype,'')as billtype,c.vtot,coalesce(c.fyear,'')as fyear,coalesce(c.bcbpno,'')as bcbpno,"
                    + "coalesce(c.alphacode,'')as alphacode,c.txninstdate ,ifnull(ifnull(am.custname,ta.custname),gl.acname) "
                    + "as custname,c.trandesc,c.remarks,c.orgnbrcode,c.destbrcode from clg_localchq c left join accounttypemaster a on substring(c.acno,3,2)=a.acctcode left "
                    + "join accountmaster am on am.acno=c.acno left join td_accountmaster ta on ta.acno=c.acno left join gltable gl on gl.acno=c.acno "
                    + "where c.txndate='" + txnDate + "' and c.emflag='" + emFlag + "' and c.txnstatus<>'H' and orgnbrcode='" + orgBrnCode + "' order "
                    + "by c.billtype,c.vtot");
            List listForClg = selectForClg.getResultList();
            result = clearLocalCheques(listForClg, emFlag, txnDate, authBy, null, null, null, null, "M");
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List selectAccNo(String accNo, String instDate, String instNo, double instAmt, String emFlag, String orgBrnCode) throws ApplicationException {
        try {
            instDate = instDate.substring(6) + instDate.substring(3, 5) + instDate.substring(0, 2);
            Query selectForClg = em.createNativeQuery("select c.acno,c.TxnInstAmt,c.reasonforcancel,c.enterby,ifnull(a.acctnature,''),c.obcflag,"
                    + "c.txninstno,coalesce(c.billtype,'')as billtype,c.vtot,coalesce(c.fyear,'')as fyear,coalesce(c.bcbpno,'')as bcbpno,"
                    + "coalesce(c.alphacode,'')as alphacode,c.txninstdate ,ifnull(ifnull(am.custname,ta.custname),gl.acname) "
                    + "as custname,c.trandesc,c.remarks,c.orgnbrcode,c.destbrcode from clg_localchq c left join accounttypemaster a on substring(c.acno,3,2)=a.acctcode left "
                    + "join accountmaster am on am.acno=c.acno left join td_accountmaster ta on ta.acno=c.acno left join gltable gl on gl.acno=c.acno "
                    + "where c.acno = '" + accNo + "' and c.txninstdate='" + instDate + "' and c.txninstno ='" + instNo + "' and c.txninstamt =" + instAmt
                    + " and c.emflag='" + emFlag + "' and c.txnstatus<>'H' and orgnbrcode='" + orgBrnCode + "' order by c.billtype,c.vtot");
            return selectForClg.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String returnClickResult(String emFlag, String acountNo, String instrDate,
            String instrNo, double instrAmt, String userName, String orgBrnCode,
            String reasonForReturn, String txnDate, String enterBy) throws ApplicationException {
        String result = "TRUE", mainDetails = null;
        String details;

        UserTransaction ut = context.getUserTransaction();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            ut.begin();
            List acDetailsList = selectAccNo(acountNo, instrDate, instrNo, instrAmt, emFlag, orgBrnCode);
            if (acDetailsList.isEmpty()) {
                throw new ApplicationException("Record Does Not Exist");
            }
            Vector vecForClg = (Vector) acDetailsList.get(0);

            String actNature = vecForClg.get(4).toString();

            String custname = vecForClg.get(13).toString();
            String remarks = vecForClg.get(15).toString();

            String orgnBrCode = vecForClg.get(16).toString();
            String destBrCode = vecForClg.get(17).toString();

            String curDt = ymd.format(new Date());
            float trsno = ftsPostingRemote.getTrsNo();

            result = owClgTxn(actNature, acountNo, custname, 0, instrAmt, curDt, remarks, enterBy, trsno, userName, orgnBrCode, destBrCode);
            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }

            List glclgResult = selectGlclg(emFlag);
            if (glclgResult.isEmpty()) {
                throw new ApplicationException("Please define the Clearing GL Head");
            }
            Vector vecForGlclg = (Vector) glclgResult.get(0);

            if (vecForGlclg.get(0).toString().length() != 6) {
                throw new ApplicationException("Please define the 6 digit Clearing GL Head");
            }

            String clgGlHead = orgBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + vecForGlclg.get(0).toString() + "01";

            details = "Local Chq. Clg";
            result = ftsPostingRemote.ftsPosting43CBS(clgGlHead, 1, 1, instrAmt, curDt, curDt, enterBy, orgBrnCode, destBrCode, 0,
                    details, trsno, 0f, 0, null, "Y", userName, "A", 2, "", null, null, 0f, "", "", "C", "", 0f, "N", "", "", "");

            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }

            details = "Return Local Chq   " + instrNo;
            trsno = ftsPostingRemote.getTrsNo();
            result = owClgTxn(actNature, acountNo, custname, 1, instrAmt, curDt, details, enterBy, trsno, userName, orgnBrCode, destBrCode);
            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }

            //Last Txn Date Updation.
            ftsPostingRemote.lastTxnDateUpdation(acountNo);

            List glclgretResult = selectGlclgret(emFlag);
            if (glclgretResult.isEmpty()) {
                throw new ApplicationException("Please define the Clearing Return GL Head");
            }
            Vector vecForGlclgret = (Vector) glclgretResult.get(0);

            if (vecForGlclgret.get(0).toString().length() != 6) {
                throw new ApplicationException("Please define the 6 digit Clearing Return GL Head");
            }
            String clgRetGlHead = orgBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + vecForGlclgret.get(0).toString() + "01";

            details = "Return Local Clg";
            result = ftsPostingRemote.ftsPosting43CBS(clgRetGlHead, 1, 0, instrAmt, curDt, curDt, enterBy, orgBrnCode, destBrCode, 0,
                    details, trsno, 0f, 0, null, "Y", userName, "A", 2, "", null, null, 0f, "", "", "C", "", 0f, "N", "", "", "");

            if (!result.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }

//            double vRetCharge = 0;
//            List selectRetChg = selectRetChg(emFlag);
//            if (!selectRetChg.isEmpty()) {
//                Vector vecRetChg = (Vector) selectRetChg.get(0);
//                vRetCharge = Double.parseDouble(vecRetChg.get(0).toString());
//            }
            //Code Changed To Return Charges Account Type Wise 
            double vRetCharge = 0;
            String tmpGLClgRetChg = "";
            //List selectRetChg = ftsPostingRemote.getCheqRetChgAndHead("OW Cheque Return Charges", ftsPostingRemote.getAccountCode(acountNo));
            List selectRetChg = ftsPostingRemote.getChequeReturnCharge("CHEQUE-RETURN-CHG", "OW-CHEQUE", instrAmt, ftsPostingRemote.getAccountCode(acountNo));
            if (!selectRetChg.isEmpty()) {
                Vector vecRetChg = (Vector) selectRetChg.get(0);
                vRetCharge = Double.parseDouble(vecRetChg.get(1).toString());
                //tmpGLClgRetChg = orgBrnCode + vecRetChg.get(0).toString() + "01";
                tmpGLClgRetChg = orgBrnCode + vecRetChg.get(0).toString();
            }

            /*
             * For Service Tax
             */
            List listCode = em.createNativeQuery("Select code from parameterinfo_report where reportname like 'STAXMODULE_ACTIVE'").getResultList();
            Vector element4 = (Vector) listCode.get(0);
            Integer code = Integer.parseInt(element4.get(0).toString());
            double sTax = 0d;
            if (code == 1) {

                map = ibRemote.getTaxComponent(vRetCharge, curDt);
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    sTax = sTax + Double.parseDouble(entry.getValue().toString());
                }
            }

            if ((vRetCharge + sTax) > 0 && !(actNature.equals("PO") || trfEjb.reasonNotAppForCharge(Integer.parseInt(reasonForReturn)).equalsIgnoreCase("true"))) {

                String balance = ftsPostingRemote.checkBalance(acountNo, (vRetCharge + sTax), enterBy);
                if (balance.equals("True")) {
                    String l_result = ftsPostingRemote.updateBalance(actNature, acountNo, 0, (vRetCharge + sTax), "Y", "N");
                    if (!l_result.equals("TRUE")) {
                        throw new ApplicationException("Problem in Updating balance");
                    }
                    trsno = ftsPostingRemote.getTrsNo();

                    // Code Commented For Account Type Wise Charges 
//                    List selectGlClgRetChg = selectGlClgRetChg(emFlag);
//                    if (selectGlClgRetChg.isEmpty()) {
//                        throw new ApplicationException("Please define the Clearing Return Charges GL Head");
//                    }
//                    Vector vecGlClgRetChg = (Vector) selectGlClgRetChg.get(0);
//                    if (vecGlClgRetChg.get(0).toString().length() != 6) {
//                        throw new ApplicationException("Please define the 6 digit Clearing Return Charges GL Head");
//                    }
                    //String tmpGLClgRetChg = orgBrnCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + vecGlClgRetChg.get(0).toString() + "01";
                    if (tmpGLClgRetChg.equalsIgnoreCase("") || tmpGLClgRetChg.length() != 6) {
                        throw new ApplicationException("Please define the 6 digit Clearing Return Charges GL Head");
                    }

                    //ADDED TRANDESC = 35 FOR CHEQUE RETURN CHARGES
                    l_result = insertTransfer(actNature, acountNo, 1, vRetCharge, curDt, 2, "Cheque Return Chg.", enterBy, trsno,
                            null, 0f, "N", null, 35, 3, "", 0f, "", "A", 1, null, 0f, null, null, null, null, 0, "", orgnBrCode, curDt);

                    if (!l_result.equals("TRUE")) {
                        throw new ApplicationException("Problem in Insertion in return charges :" + l_result);
                    }

                    l_result = insertTransfer("PO", tmpGLClgRetChg, 0, vRetCharge, curDt, 2, "Cheque Return Chg. for " + acountNo, enterBy, trsno,
                            null, 0f, "N", null, 35, 3, "", 0f, "", "A", 1, null, 0f, null, null, null, null, 0, "", orgnBrCode, curDt);
                    if (!l_result.equals("TRUE")) {
                        throw new ApplicationException("Problem in Insertion in return charges :" + l_result);
                    }

                    if (code == 1) {
                        l_result = insertTransfer(actNature, acountNo, 1, sTax, curDt, 2, "GST For Cheque Return Chg.", enterBy, trsno,
                                null, 0f, "N", null, 71, 3, "", 0f, "", "A", 1, null, 0f, null, null, null, null, 0, "", orgnBrCode, curDt);

                        if (!l_result.equals("TRUE")) {
                            throw new ApplicationException("Problem in Insertion in return charges :" + l_result);
                        }

                        //Service Tax Entry As Per New
                        Set<Entry<String, Double>> set = map.entrySet();
                        Iterator<Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = orgnBrCode + keyArray[1];
                            mainDetails = description.toUpperCase() + " for Cheque Return Chg. for. " + acountNo;
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            l_result = insertTransfer("PO", taxHead, 0, taxAmount, curDt, 2, mainDetails, enterBy, trsno,
                                    null, 0f, "N", null, 71, 3, "", 0f, "", "A", 1, null, 0f, null, null, null, null, 0, "", orgnBrCode, curDt);
                            if (!l_result.equals("TRUE")) {
                                throw new ApplicationException("Problem in Insertion in return charges :" + l_result);
                            }
                        }
                    }
                } else {
                    float recNo = ftsPostingRemote.getRecNo();
                    Query insertQuery4 = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,trsno,enterby,auth,"
                            + "recover,TRANDESC) values ('" + acountNo + "','" + curDt + "'," + vRetCharge + ",'" + details
                            + "'," + 1 + "," + 2 + "," + recNo + "," + trsno + ",'" + enterBy + "','N','N'," + 0 + ")");
                    insertQuery4.executeUpdate();
                }
            }
            String resultClgReturnedChq = insertIntoClgReturnedChq(reasonForReturn, acountNo, instrDate, instrNo, instrAmt, emFlag, orgBrnCode);
            if (!resultClgReturnedChq.equalsIgnoreCase("Success in deletion from ClgLocalChq")) {
                throw new ApplicationException(resultClgReturnedChq);
            }

            List selectFromClgLocalChq = selectFromClgLocalChq(txnDate, emFlag, orgBrnCode);
            if (selectFromClgLocalChq.isEmpty()) {

                String pendingDate = txnDate.substring(6) + txnDate.substring(3, 5) + txnDate.substring(0, 2);
                Query update = em.createNativeQuery("Update clg_ow_register Set Status = 'Cleared' , PostedBy = '" + userName + "',clearedBy = '"
                        + userName + "',summary='' Where EntryDate='" + pendingDate + "' and Emflag='" + emFlag + "' and brncode='" + orgBrnCode + "'");
                int updateResult = update.executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Sorry!! Status has not Updated.");
                }
            }
            ut.commit();
            //Sending Sms
            try {
                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                tSmsRequestTo.setMsgType("PAT");
                tSmsRequestTo.setTemplate(SmsType.CHQ_RETURN_CLEARING_DEBIT);
                tSmsRequestTo.setAcno(acountNo);
                tSmsRequestTo.setAmount(instrAmt);
                tSmsRequestTo.setDate(txnDate);
                tSmsRequestTo.setPromoMobile(smsFacade.getSubscriberDetails(acountNo).getMobileNo());
                tSmsRequestTo.setFirstCheque(instrNo);
                tSmsRequestTo.setBalFlag("Y");

                List<MbSmsSenderBankDetailTO> bankTo = smsFacade.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                tSmsRequestTo.setBankName(templateBankName);

                smsFacade.sendSms(tSmsRequestTo);
            } catch (Exception ex) {
                System.out.println("Error SMS Sending-->A/c is::" + acountNo + " And Amount is::" + instrAmt);
            }
            //End here
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public List selectGlclg(String emFlag) {
        List selectGlclg = em.createNativeQuery("select glclg from parameterinfo_clg where circletype='" + emFlag + "'").getResultList();
        return selectGlclg;
    }

    public List selectGlclgret(String emFlag) {
        List selectGlclgret = em.createNativeQuery("select glclgret from parameterinfo_clg where circletype='" + emFlag + "'").getResultList();
        return selectGlclgret;
    }

    public List selectRetChg(String emFlag) {
        List selectRetChg = em.createNativeQuery("select retchg from parameterinfo_clg where circletype='" + emFlag + "'").getResultList();
        return selectRetChg;
    }

    public List selectBalance(String table1, String accNo) {
        List selectBalance = em.createNativeQuery("SELECT BALANCE FROM " + table1 + " WHERE ACNO='" + accNo + "'").getResultList();
        return selectBalance;
    }

    public List selectGlClgRetChg(String emFlag) {
        List selectGlClgRetChg = em.createNativeQuery("select GlClgRetChg from parameterinfo_clg where circletype='" + emFlag + "'").getResultList();
        return selectGlClgRetChg;
    }

    public String insertIntoClgReturnedChq(String reasonForReturn, String accNo, String date,
            String instrNo, double instrAmt, String emFlag, String orgBrnCode) throws ApplicationException {
        date = date.substring(6) + date.substring(3, 5) + date.substring(0, 2);
        try {

            Query insertIntoClgReturnedChq = em.createNativeQuery("insert into clg_returnedchq(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                    + "Txnstatus,txnauthby,TxnInstDate,TxnBankName,TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,reasonforcancel,vtot,"
                    + "orgnbrcode,destbrcode) select Acno,TxnMode,TxnType,txndate,TxnInstNo,TxnInstAmt,Txnstatus,enterby,TxnInstDate,TxnBankName,"
                    + "TxnBankAddress,emflag,BillType,AlphaCode,BcBpNo,Fyear,'" + reasonForReturn + "',vtot,orgnbrcode,destbrcode from clg_localchq "
                    + "where acno = '" + accNo + "' and txninstdate = '" + date + "' and txninstno = '" + instrNo + "' and txninstamt = " + instrAmt
                    + " and Emflag = '" + emFlag + "'");
            int resultClgReturnedChq = insertIntoClgReturnedChq.executeUpdate();
            if (resultClgReturnedChq > 0) {
                Query delete = em.createNativeQuery("delete from clg_localchq where acno = '" + accNo + "' and txninstdate = '" + date + "' and "
                        + "txninstno = '" + instrNo + "' and txninstamt = " + instrAmt + " and Emflag = '" + emFlag + "' and orgnbrcode='" + orgBrnCode + "'");
                int deleteResult = delete.executeUpdate();
                if (deleteResult > 0) {
                    return "Success in deletion from ClgLocalChq";
                } else {
                    return "Problem in deletion from ClgLocalChq";
                }
            } else {
                return "Problem in insertion in CLG_RETURNEDCHQ";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromClgLocalChq(String pendingDate, String emFlag, String orgBrnCode) {
        pendingDate = pendingDate.substring(6) + pendingDate.substring(3, 5) + pendingDate.substring(0, 2);
        List selectFromClgLocalChq = em.createNativeQuery("select * from clg_localchq where txnDate = '" + pendingDate + "' and Emflag = '"
                + emFlag + "' and orgnbrcode='" + orgBrnCode + "'").getResultList();
        return selectFromClgLocalChq;
    }

    public List checkStatus(String entryDate, String emFlag, String brCode) throws ApplicationException {
        String registerDt = entryDate.substring(6) + entryDate.substring(3, 5) + entryDate.substring(0, 2);
        List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
        Vector tempCurrent = (Vector) tempBd.get(0);
        String Tempbd = tempCurrent.get(0).toString();

        List paramInfo = em.createNativeQuery("select Postingdate from clg_ow_register where emflag='" + emFlag
                + "' and EntryDate = '" + registerDt + "' and brncode='" + brCode + "' and postingdate = clearingdate").getResultList();
        if (paramInfo.isEmpty()) {
            throw new ApplicationException("Sorry!Posting Date has not been set for this Register");
        }
        Vector paramInfos = (Vector) paramInfo.get(0);
        String postingDt = paramInfos.get(0).toString();
        String postDt = postingDt.substring(0, 4) + postingDt.substring(5, 7) + postingDt.substring(8, 10);
        if (!postDt.equals(Tempbd)) {
            throw new ApplicationException("Posting is not allowed in this Date");
        }
        List chkStatus = em.createNativeQuery("select Status from clg_ow_register where EntryDate='" + registerDt + "' and Emflag = '" + emFlag
                + "' and brncode='" + brCode + "'").getResultList();
        return chkStatus;
    }

    public String clearLocalCheques(List queryList, String emFlag, String txnDate, String authBy,
            String accNo, String amount, String instrNo, String name, String flag) throws ApplicationException {
        String result = "";

        String acNo, actNature, enterBy = "", obcFlag, instNo, lResult, glclgret = "", glclgretnm = "", glclgretchg = "", glclg = "", dataBillFlagReturned = "",
                dataFlagPassed = "", billtype, billAcno, fYear, bcbpno, alphacode, details, custname, glclgname, remarks = "", reasonForCancel, txnInstDate;
        float amt, retAmt = 0, retchqNo = 0, trsno = 0, glclgTrsno = 0, billTrsno = 0, tempVtot, vtot, recno, clgamt;
        double totalRetchgs, retchg = 0d;
        int tranDesc;
        clgamt = 0;
        String curDt = "", orgnBrCode = "", destBrCode = "";
        Map<String, List<String>> deductMap = new ConcurrentHashMap<String, List<String>>();
        try {
            for (int i = 0; i < queryList.size(); i++) {
                Vector vecForClg = (Vector) queryList.get(i);
                acNo = vecForClg.get(0).toString();
                amt = Float.parseFloat(vecForClg.get(1).toString());
                reasonForCancel = vecForClg.get(2).toString();
                enterBy = vecForClg.get(3).toString();

                actNature = vecForClg.get(4).toString();
                obcFlag = vecForClg.get(5).toString();
                instNo = vecForClg.get(6).toString();
                billtype = vecForClg.get(7).toString();
                vtot = Float.parseFloat(vecForClg.get(8).toString());

                fYear = vecForClg.get(9).toString();
                bcbpno = vecForClg.get(10).toString();
                alphacode = vecForClg.get(11).toString();

                txnInstDate = vecForClg.get(12).toString();
                custname = vecForClg.get(13).toString();
                tranDesc = Integer.parseInt(vecForClg.get(14).toString());
                remarks = vecForClg.get(15).toString();

                orgnBrCode = vecForClg.get(16).toString();
                destBrCode = vecForClg.get(17).toString();

                retAmt = 0;
                retchqNo = 0;
                glclgTrsno = 0;
                dataBillFlagReturned = "FALSE";
                dataFlagPassed = "FALSE";
                tempVtot = -1;
                curDt = ymd.format(new Date());

                trsno = ftsPostingRemote.getTrsNo();
                List listForClg2 = em.createNativeQuery("select distinct lpad(ifnull(glclgret,''),6,'0'),retchg,lpad(ifnull(glclgretchg,''),6,'0'),"
                        + "ifnull(substring(gl1.acname,1,40),''), ifnull(substring(gl2.acname,1,40),'') from parameterinfo_clg p "
                        + "left join gltable gl1 on substring(gl1.acno,5,6)=lpad(p.glclgret,6,'0') left join gltable gl2 on "
                        + "substring(gl2.acno,5,6)=lpad(p.glclgretchg,6,'0') where circletype='" + emFlag + "'").getResultList();
                Vector vecForClg2 = (Vector) listForClg2.get(0);
                glclgret = vecForClg2.get(0).toString();
                //retchg = Float.parseFloat(vecForClg2.get(1).toString());
                //glclgretchg = vecForClg2.get(2).toString();
                glclgretnm = vecForClg2.get(3).toString();
                List selectRetChg = ftsPostingRemote.getChequeReturnCharge("CHEQUE-RETURN-CHG", "OW-CHEQUE", amt, ftsPostingRemote.getAccountCode(acNo));
                //List selectRetChg = ftsPostingRemote.getCheqRetChgAndHead("OW Cheque Return Charges", ftsPostingRemote.getAccountCode(acNo));
                if (!selectRetChg.isEmpty()) {
                    Vector vecRetChg = (Vector) selectRetChg.get(0);
                    retchg = Double.parseDouble(vecRetChg.get(1).toString());
                    //glclgretchg = orgnBrCode + vecRetChg.get(0).toString() + "01";
                    glclgretchg = orgnBrCode + vecRetChg.get(0).toString();
                }

                if (glclgret.equals("") && glclgretchg.equals("")) {
                    result = "Please Check for GL Head of Clearing and Return Charges";
                    return result;
                }
                billAcno = ftsPostingRemote.getGlHeadFromParam(orgnBrCode, SiplConstant.ISO_HEAD.getValue());
                try {
                    if (!queryList.isEmpty()) {

                        result = owClgTxn(actNature, acNo, custname, 0, amt, curDt, remarks, enterBy, trsno, authBy, orgnBrCode, destBrCode);
                        if (!result.equalsIgnoreCase("True")) {
                            return result;
                        }
                        clgamt = clgamt + amt;
                        if (Integer.parseInt(reasonForCancel) == 0) {
                            dataFlagPassed = "TRUE";
                            if (billtype.equals("")) {
                                String resultOfUpdateBal = ftsPostingRemote.updateBalance(actNature, acNo, amt, 0, "Y", "Y");
                                if (!resultOfUpdateBal.equals("TRUE")) {
                                    return resultOfUpdateBal;
                                }
                                if (obcFlag.equals("OBC") || obcFlag.equals("DD") || obcFlag.equals("PO") || obcFlag.equals("CHQ")) {
                                    Query updateBillObcprocessed = em.createNativeQuery("update bill_obcprocessed set dt='" + curDt
                                            + "', OBCFlag = 'Cleared' where Instno = '" + instNo + "'");
                                    int resultUpdateBill = updateBillObcprocessed.executeUpdate();
                                    if (resultUpdateBill <= 0) {
                                        return "Problem in updation of biil_obcprocessed.";
                                    }
                                }
                                if ((actNature.equals("TL") || actNature.equals("DL") || actNature.equals("SS") || actNature.equals("RD")) && (tranDesc == 0 || tranDesc == 1)) {
                                    details = "By Clearing : Inst No:" + instNo;
                                    lResult = ftsPostingRemote.loanDisbursementInstallment(acNo, amt, 0, authBy, curDt, 0, tranDesc, details);
                                    if (!lResult.equals("TRUE")) {
                                        return "Problem in Loan Disbursement/Installment :" + lResult;
                                    }
                                }
                            } else {
                                if (vtot != tempVtot) {
                                    billTrsno = ftsPostingRemote.getTrsNo();
                                }
                                details = alphacode + "" + billtype + "" + bcbpno;
                                lResult = "";
                                lResult = ftsPostingRemote.insertRecons(actNature, acNo, 1, amt, curDt, curDt, 2, remarks, enterBy,
                                        billTrsno, null, 0f, "N", authBy, 0, 3, instNo, null, 0f, null, "A", 1, null, 0f, null, null,
                                        orgnBrCode, destBrCode, 0, null, "", "");
                                if (!lResult.equalsIgnoreCase("True")) {
                                    return result = lResult;
                                }

                                lResult = "";
                                lResult = ftsPostingRemote.insertRecons("PO", billAcno, 0, amt, curDt, curDt, 2, remarks, enterBy,
                                        billTrsno, null, 0f, "N", null, 0, 3, instNo, null, 0f, null, "A", 1, null, 0f, null, null,
                                        orgnBrCode, destBrCode, 0, null, "", "");
                                if (!lResult.equalsIgnoreCase("true")) {
                                    return result = lResult;
                                }
                                recno = ftsPostingRemote.getRecNo();
                                Query insertIntoBillHoOthers = em.createNativeQuery("insert into bill_hoothers(FYEAR,SEQNO,INSTNO,BILLTYPE,ACNO,"
                                        + "PAYABLEAT,AMOUNT,DT,ORIGINDT,STATUS,enterby,ty,trantype,recno,auth) values('" + fYear + "','" + bcbpno
                                        + "','" + instNo + "','" + billtype + "','" + acNo + "','" + alphacode + "'," + amt + ",'" + curDt
                                        + "','" + txnInstDate + "','Issued','" + authBy + "',0,2," + recno + ",'Y')");
                                int resultInsrtIntoBillHoOthers = insertIntoBillHoOthers.executeUpdate();
                                if (resultInsrtIntoBillHoOthers <= 0) {
                                    return result = "Insertion Problem in Bill_HoOthers Table";
                                }
                            }

                        } else {
                            if (billtype.equals("")) {
                                lResult = "";
                                details = "Cheque Return." + "Inst No:-" + instNo;
                                lResult = owClgTxn(actNature, acNo, custname, 1, amt, curDt, details, enterBy, trsno, authBy, orgnBrCode, destBrCode);
                                if (!lResult.equalsIgnoreCase("True")) {
                                    return lResult;
                                }
                                details = "";
                                if (!actNature.equals("PO")) {
                                    if (glclgTrsno == 0) {
                                        glclgTrsno = ftsPostingRemote.getTrsNo();
                                    }
                                    retAmt = retAmt + amt;
                                    String balance = ftsPostingRemote.checkBalance(acNo, retchg, enterBy);
                                    if (balance.equals("True")) {
                                        String l_result = ftsPostingRemote.updateBalance(actNature, acNo, 0, retchg, "Y", "N");
                                        if (!l_result.equals("TRUE")) {
                                            return "Problem in Updating balance :";
                                        }
                                        retchqNo = retchqNo + 1;

                                        //ADDED TRANDESC = 35 FOR CHEQUE RETURN CHARGES
                                        l_result = insertTransfer(actNature, acNo, 1, retchg, curDt, 2, "Cheque Return Chg.", enterBy, glclgTrsno,
                                                null, 0f, "N", null, 35, 3, "", 0f, null, "A", 1, null, 0f, null, null, null, null, 0, null, orgnBrCode, curDt);
                                        if (!l_result.equals("TRUE")) {
                                            return "Problem in Insertion in recons :" + l_result;
                                        }

                                        if (deductMap.containsKey(glclgretchg)) {
                                            for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                                                Map.Entry entry = (Map.Entry) it.next();
                                                String sKey = entry.getKey().toString();
                                                if (glclgretchg.equalsIgnoreCase(sKey)) {
                                                    List kLst = (List) entry.getValue();
                                                    double nVal = Double.parseDouble(kLst.get(1).toString()) + retchg;
                                                    List glVal = new ArrayList();
                                                    glVal.add(sKey);
                                                    glVal.add(nVal);
                                                    deductMap.remove(sKey);
                                                    deductMap.put(sKey, glVal);
                                                }
                                            }
                                        } else {
                                            List glVal = new ArrayList();
                                            glVal.add(glclgretchg);
                                            glVal.add(retchg);
                                            deductMap.put(glclgretchg, glVal);
                                        }
                                    } else {
                                        float recNo = ftsPostingRemote.getRecNo();
                                        Query insertQuery4 = em.createNativeQuery("insert into pendingcharges(Acno,dt,amount,details,ty,trantype,recno,"
                                                + "trsno,enterby,auth,recover,TRANDESC) values (" + "'" + acNo + "'" + "," + "'" + curDt + "'" + "," + retchg
                                                + "," + "'" + details + "'" + "," + 1 + "," + 2 + "," + recNo + "," + glclgTrsno + "," + "'" + enterBy + "'" + ","
                                                + "'N'" + "," + "'N'" + "," + 0 + ")");
                                        insertQuery4.executeUpdate();
                                    }
                                }
                            } else {
                                dataBillFlagReturned = "TRUE";
                                lResult = "";
                                lResult = ftsPostingRemote.insertRecons("PO", glclgret, 0, amt, curDt, curDt, 1,
                                        "Returned BP/BC Amount", authBy, trsno, null, 0f, "Y", "SYSTEM", 0, 3, null, null, 0f, "SYSTEM", "A",
                                        1, null, 0f, null, null, orgnBrCode, destBrCode, 0, null, "", "");
                                if (!lResult.equalsIgnoreCase("TRUE")) {
                                    return result = lResult;
                                }
                                lResult = "";
                                recno = ftsPostingRemote.getRecNo();
                                Query insertForClg3 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,valuedt,cramt,ty,trantype,recno,"
                                        + "trsno,auth,enterby,authby,details,org_brnid,dest_brnid) values('" + glclgret + "','" + glclgretnm + "','"
                                        + curDt + "','" + curDt + "'," + amt + ",0,1," + recno + "," + trsno + ",'Y','"
                                        + authBy + "','SYSTEM','Returned BP/BC Amount','" + orgnBrCode + "','" + destBrCode + "')");
                                int resultForClg3 = insertForClg3.executeUpdate();
                                if (resultForClg3 <= 0) {
                                    return result = "Insert Problem in Recon_Clg_d for Ac No" + glclgret;
                                }
                                lResult = ftsPostingRemote.insertRecons(actNature, acNo, 1, amt, curDt, curDt, 1,
                                        "Returned BP/BC Amount", authBy, trsno, null, 0f, "Y", "SYSTEM", 0, 3, null, null, 0f, "SYSTEM", "A",
                                        1, null, 0f, null, null, orgnBrCode, destBrCode, 0, null, "", "");
                                if (!lResult.equalsIgnoreCase("true")) {
                                    return result = lResult;
                                }

                                recno = ftsPostingRemote.getRecNo();
                                Query insertForClg4 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,valuedt,dramt,ty,trantype,recno,"
                                        + "trsno,auth,enterby,authby,details,org_brnid,dest_brnid) values('" + acNo + "','" + custname + "','"
                                        + curDt + "','" + curDt + "'," + amt + ",1,1," + recno + "," + trsno + ",'Y','"
                                        + authBy + "','SYSTEM','Returned BP/BC Amount','" + orgnBrCode + "','" + destBrCode + "')");
                                int resultForClg4 = insertForClg4.executeUpdate();
                                if (resultForClg4 <= 0) {
                                    return result = "Insert Problem in Recon_Clg_d for Ac No" + acNo;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (retchqNo > 0) {
                lResult = "";
                lResult = ftsPostingRemote.updateBalance("PO", glclgret, retAmt, 0, "Y", "Y");
                if (!lResult.equalsIgnoreCase("true")) {
                    return result = lResult;
                }
                lResult = "";
                lResult = ftsPostingRemote.insertRecons("PO", glclgret, 0, retAmt, curDt, curDt, 1, "Returned Cheque Amount",
                        authBy, glclgTrsno, null, 0f, "Y", "SYSTEM", 0, 3, null, null, 0f, null, "", 1, null, 0f, null, null,
                        orgnBrCode, destBrCode, 0, null, "", "");
                if (!lResult.equalsIgnoreCase("true")) {
                    return result = lResult;
                }
                lResult = "";
                recno = ftsPostingRemote.getRecNo();
                Query insertIntoReconClgD = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,valuedt,cramt,ty,"
                        + "trantype,recno,trsno,auth,enterby,authby,details,org_brnid,dest_brnid) values('" + glclgret + "','"
                        + glclgretnm + "','" + curDt + "','" + curDt + "'," + retAmt + "," + "0,1," + recno + ","
                        + glclgTrsno + ",'Y','" + authBy + "','SYSTEM','Returned Cheque Amount','" + orgnBrCode + "','" + destBrCode + "')");
                int insertResultOfReconClgD = insertIntoReconClgD.executeUpdate();
                if (insertResultOfReconClgD <= 0) {
                    return result = "Insert Problem in Recon_Clg_d for Ac No" + glclgret;
                }
                totalRetchgs = retchg * retchqNo;
                if (totalRetchgs > 0) {
//                    result = trfEjb.insertTransfer("PO", glclgretchg, 0, totalRetchgs, curDt, 2, "Cheque Return Chg.", enterBy, trsno, null, 0, "N",
//                            null, 0, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, orgnBrCode, curDt);
//                    if (!result.equals("TRUE")) {
//                        return "Problem in Insertion in recons :";
//                    }
                    for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry) it.next();
                        List kLst = (List) entry.getValue();
                        String sGl = kLst.get(0).toString();
                        double nVal = Double.parseDouble(kLst.get(1).toString());
                        result = trfEjb.insertTransfer("PO", sGl, 0, nVal, curDt, 2, "Cheque Return Chg.", enterBy, trsno, null, 0, "N",
                                null, 0, 3, "", 0, null, "A", 1, null, null, null, null, null, null, 0, null, orgnBrCode, curDt);
                        if (!result.equals("TRUE")) {
                            return "Problem in Insertion in recons :";
                        }
                    }
                }
//                lResult = ftsPostingRemote.insertRecons("PO", glclgretchg, 0, totalRetchgs, curDt, curDt, 2, "Cheque Return Chg.",
//                        authBy, glclgTrsno, null, 0f, "Y", null, 0, 3, "", null, 0f, null, "", 1, null, 0f, null, null,
//                        orgnBrCode, destBrCode, 0, null, "", "");
//                if (!lResult.equalsIgnoreCase("true")) {
//                    return result = lResult;
//                }
                for (Iterator it = deductMap.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();
                    List kLst = (List) entry.getValue();
                    String sGl = kLst.get(0).toString();
                    double nVal = Double.parseDouble(kLst.get(1).toString());
                    lResult = ftsPostingRemote.insertRecons("PO", sGl, 0, nVal, curDt, curDt, 2, "Cheque Return Chg.",
                            authBy, glclgTrsno, null, 0f, "Y", null, 0, 3, "", null, 0f, null, "", 1, null, 0f, null, null,
                            orgnBrCode, destBrCode, 0, null, "", "");
                    if (!lResult.equalsIgnoreCase("true")) {
                        return result = lResult;
                    }
                }
            }
            if (clgamt != 0) {
                List chkList1 = em.createNativeQuery("select glclg from parameterinfo_clg  where circletype='" + emFlag + "'").getResultList();
                Vector rLst = (Vector) chkList1.get(0);
                String clgHead = rLst.get(0).toString();
                if (clgHead == null) {
                    return "GLHEAD for Clearing Does not Exists";
                }
                if (clgHead.length() == 12) {
                    glclg = clgHead;
                } else {
                    for (int j = clgHead.length(); j < 6; j++) {
                        clgHead = "0" + clgHead;
                    }
                    glclg = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + clgHead + "01";
                }
                Query updateInReconBal = em.createNativeQuery("update reconbalan set balance=balance-ifnull(" + clgamt + ",0),"
                        + "clearedbalance=clearedbalance-ifnull(" + clgamt + ",0) where acno='" + glclg + "'");
                int updateResultReconBal = updateInReconBal.executeUpdate();
                if (updateResultReconBal <= 0) {
                    return result = "Updation problem in reconbalan for GL CLUBBED ENTRY";
                }
                recno = 0;
                recno = ftsPostingRemote.getRecNo();
                if (recno == 0) {
                    return result = "Problem in getting recno";
                }
                List listGlClgName = em.createNativeQuery("select acname from gltable where acno='" + glclg + "'").getResultList();
                Vector vecForGlClgName = (Vector) listGlClgName.get(0);
                glclgname = vecForGlClgName.get(0).toString();
                Query insertRecnClgd = em.createNativeQuery("insert into recon_clg_d (acno,ty,dramt,trantype,recno,dt,valuedt,details,SCREENFLAG,"
                        + "enterby,CUSTNAME,trsno,auth,authby,org_brnid,dest_brnid) values('" + glclg + "',1," + clgamt + ",1," + recno + ",'"
                        + curDt + "','" + curDt + "','VCH Today Clearing','T','SYSTEM','" + glclgname + "'," + trsno + ",'Y','"
                        + authBy + "','" + orgnBrCode + "','" + destBrCode + "')");
                int insertresultRecon = insertRecnClgd.executeUpdate();
                if (insertresultRecon <= 0) {
                    return result = "Insertion problem in Recon_clg_D";
                }
            }

            lResult = ftsPostingRemote.insertRecons("PO", glclg, 1, clgamt, curDt, curDt, 1, remarks, enterBy, trsno,
                    null, 0f, "Y", authBy, 0, 3, null, null, 0f, null, "A", 1, null, 0f, null, null, orgnBrCode, destBrCode, 0, null, "", "");
            if (!lResult.equalsIgnoreCase("true")) {
                return result = "Problem in Insertion in recons :" + lResult;
            }
            if (flag.equalsIgnoreCase("S")) {
                // Handling of orgnbrcode,destbrcode by Nishant Kansal on Date 23/03/2011
                Query insertIntoClgOwHistry = em.createNativeQuery("insert into clg_ow_history(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,"
                        + "TxnInstAmt,Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,"
                        + "Fyear,Sbal,obcflag,vtot,orgnbrcode,destbrcode,AreaCode,BnkCode,BranchCode) 	select Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,'C',"
                        + "TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,"
                        + "Sbal,obcflag,vtot,orgnbrcode,destbrcode,AreaCode,BnkCode,BranchCode from clg_localchq where ACNO='" + accNo + "' AND txninstno='" + instrNo + "' "
                        + "AND TxnInstAmt=" + amount + " AND txndate='" + txnDate + "' and emflag='" + emFlag + "' and ReasonForCancel = 0 "
                        + "and txnstatus<>'H' and orgnbrcode='" + orgnBrCode + "' and destbrcode='" + destBrCode + "'");
                int resultForInsertIntoClgOwHistry = insertIntoClgOwHistry.executeUpdate();
                if (resultForInsertIntoClgOwHistry <= 0 && dataFlagPassed.equalsIgnoreCase("TRUE")) {
                    return result = "Insertion Problem in History Table";
                }
                // Handling of orgnbrcode,destbrcode by Nishant Kansal on Date 23/03/2011
                Query insertClgRetndChq = em.createNativeQuery("insert into clg_returnedchq (Acno,TxnMode,TxnType,TxnDate,TxnInstNo,"
                        + "TxnInstAmt,Txnstatus,TxnAuthBy,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,"
                        + "AlphaCode,BcBpNo,Fyear,Sbal,obcflag,vtot,orgnbrcode,destbrcode)select s.Acno,s.TxnMode,s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,"
                        + "'R','" + authBy + "',s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,ifnull(c.description,''),s.EMFlag ,s.BillType,"
                        + "s.AlphaCode,s.BcBpNo,s.Fyear,s.Sbal,s.obcflag,s.vtot,s.orgnbrcode,s.destbrcode from clg_localchq s left join codebook c on "
                        + "s.reasonforcancel=c.code and c.groupcode=13 where  s.reasonforcancel <> 0 and s.TxnDate='" + txnDate + "' "
                        + "and s.billtype is null and s.orgnbrcode='" + orgnBrCode + "' and s.destbrcode='" + destBrCode + "'");
                int insertresultClgRetnVhq = insertClgRetndChq.executeUpdate();
                if (insertresultClgRetnVhq <= 0 && retchqNo > 0) {
                    return result = "Insertion Problem in Clg_RetunedChq Table";
                }
                Query insertIntoBillBpbcRetrn = em.createNativeQuery("insert into bill_bpbcreturn(Acno,TxnMode,TxnType,TxnDate,"
                        + "TxnInstNo,TxnInstAmt,Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag,BillType,"
                        + "	AlphaCode,BcBpNo,Fyear) select s.Acno,s.TxnMode,s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,'R',"
                        + "s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,c.description,s.EMFlag,s.BillType,s.AlphaCode,s.BcBpNo,"
                        + "s.Fyear from clg_localchq s left join codebook c on s.reasonforcancel=c.code and c.groupcode=13  "
                        + "where  s.reasonforcancel <> 0 AND s.ACNO='" + accNo + "' AND s.txninstno=" + instrNo + " AND s.TxnInstAmt=" + amount + " "
                        + "and billtype is not null and s.TxnDate='" + txnDate + "' and s.orgnbrcode='" + orgnBrCode + "'");
                int insertResultBillBcbpretrn = insertIntoBillBpbcRetrn.executeUpdate();
                if (insertResultBillBcbpretrn <= 0 && dataBillFlagReturned.equalsIgnoreCase("TRUE")) {
                    return result = "Insertion Problem in Bill_bpbcreturn Table";
                }
                Query deleteFromClgLocalChq = em.createNativeQuery("delete from clg_localchq where  ACNO='" + accNo + "' AND txninstno=" + instrNo
                        + " AND TxnInstAmt=" + amount + " AND txndate='" + txnDate + "' and emflag='" + emFlag + "' and txnstatus<>'H' and orgnbrcode='"
                        + orgnBrCode + "'");
                int deleteResult = deleteFromClgLocalChq.executeUpdate();
                if (deleteResult <= 0 && (dataFlagPassed.equalsIgnoreCase("TRUE") || dataBillFlagReturned.equalsIgnoreCase("TRUE") || retchqNo > 0)) {
                    return result = "deletion Problem of Clg_Localchq Table";
                }
                if (dataFlagPassed.equalsIgnoreCase("TRUE") || dataBillFlagReturned.equalsIgnoreCase("TRUE") || retchqNo > 0) {
                    return result = "TRUE";
                } else {
                    return result = "No Data Exists for Clearing";
                }
            } else if (flag.equalsIgnoreCase("M")) {
                Query insertIntoClgOwHistory = em.createNativeQuery("insert into clg_ow_history(Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,"
                        + "Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,Sbal,obcflag,vtot,"
                        + "orgnbrcode,destbrcode,AreaCode,BnkCode,BranchCode)select Acno,TxnMode,TxnType,TxnDate,TxnInstNo,TxnInstAmt,'C',TxnInstDate,TxnBankName,TxnBankAddress,"
                        + "reasonforcancel,EMFlag ,BillType,AlphaCode,BcBpNo,Fyear,Sbal,obcflag,vtot,orgnbrcode,destbrcode,AreaCode,BnkCode,BranchCode from clg_localchq where "
                        + "txndate='" + txnDate + "' and emflag='" + emFlag + "' and ReasonForCancel = 0 and txnstatus<>'H' and orgnbrcode='" + orgnBrCode + "'");
                int resultOfClgOwHostory = insertIntoClgOwHistory.executeUpdate();
                if (resultOfClgOwHostory <= 0 && dataFlagPassed.equalsIgnoreCase("TRUE")) {
                    return result = "Insertion Problem in History Table";
                }

                Query insertClgRetndChq = em.createNativeQuery("insert into clg_returnedchq (Acno,TxnMode,TxnType,TxnDate,TxnInstNo,"
                        + "TxnInstAmt,Txnstatus,TxnAuthBy,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag ,BillType,"
                        + "AlphaCode,BcBpNo,Fyear,Sbal,obcflag,vtot,orgnbrcode,destbrcode)select s.Acno,s.TxnMode,s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,"
                        + "'R','" + authBy + "',s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,ifnull(c.description,''),s.EMFlag ,s.BillType,"
                        + "s.AlphaCode,s.BcBpNo,s.Fyear,s.Sbal,s.obcflag,s.vtot,s.orgnbrcode,s.destbrcode from clg_localchq s left join codebook c on "
                        + "s.reasonforcancel=c.code and c.groupcode=13 where  s.reasonforcancel <> 0 and s.TxnDate='" + txnDate + "' and s.orgnbrcode='" + orgnBrCode + "' "
                        + "and s.billtype is null");
                int insertresultClgRetnVhq = insertClgRetndChq.executeUpdate();
                if (insertresultClgRetnVhq <= 0 && retchqNo > 0) {
                    return result = "Insertion Problem in Clg_RetunedChq Table";
                }
                Query insertIntoBillBpbcRetrn = em.createNativeQuery("insert into bill_bpbcreturn(Acno,TxnMode,TxnType,TxnDate,"
                        + "TxnInstNo,TxnInstAmt,Txnstatus,TxnInstDate,TxnBankName,TxnBankAddress,reasonforcancel,EMFlag,BillType,"
                        + "	AlphaCode,BcBpNo,Fyear) select s.Acno,s.TxnMode,s.TxnType,s.TxnDate,s.TxnInstNo,s.TxnInstAmt,'R',"
                        + "s.TxnInstDate,s.TxnBankName,s.TxnBankAddress,c.description,s.EMFlag,s.BillType,s.AlphaCode,s.BcBpNo,"
                        + "s.Fyear from clg_localchq s left join codebook c	on s.reasonforcancel=c.code and c.groupcode=13  "
                        + "where  s.reasonforcancel <> 0 and billtype is not null and s.TxnDate='" + txnDate + "' and orgnbrcode='" + orgnBrCode + "'");
                int insertResultBillBcbpretrn = insertIntoBillBpbcRetrn.executeUpdate();
                if (insertResultBillBcbpretrn <= 0 && dataBillFlagReturned.equalsIgnoreCase("TRUE")) {
                    return result = "Insertion Problem in Bill_bpbcreturn Table";
                }
                Query deleteFromClgLocalChq = em.createNativeQuery("delete from clg_localchq where  txndate='" + txnDate + "' and emflag='" + emFlag + "' and txnstatus<>'H' and orgnbrcode='" + orgnBrCode + "'");
                int deleteResult = deleteFromClgLocalChq.executeUpdate();
                if (deleteResult <= 0 && (dataFlagPassed.equalsIgnoreCase("TRUE") || dataBillFlagReturned.equalsIgnoreCase("TRUE") || retchqNo > 0)) {
                    return result = "deletion Problem of Clg_Localchq Table";
                }
                if (dataFlagPassed != null) {
                    if (dataFlagPassed.equalsIgnoreCase("TRUE") || dataBillFlagReturned.equalsIgnoreCase("TRUE") || retchqNo > 0) {
                        return result = "TRUE";
                    } else {
                        return result = "No Data Exists for Clearing";
                    }
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }
    /*End of Local Cheque and High Value Clearing*/

    public String instrDateCheck(String intrDate, String accNo) throws ApplicationException {
        try {
            List chkList1 = em.createNativeQuery("select openingdt from accountmaster where acno ='" + accNo + "'").getResultList();
            if (chkList1.isEmpty()) {
                throw new ApplicationException("Account does not exist");
            }

            Vector rLst = (Vector) chkList1.get(0);
            String acOpeningDt = rLst.get(0).toString();

            long dateDiff1 = CbsUtil.dayDiff(ymd.parse(acOpeningDt), ymd.parse(intrDate));

            if (dateDiff1 < 0) {
                return "Instrument Date is less than Opening Date";
            } else {
                return "true";
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String checkClgDt(String emFlag, String registerDts1, String brCode) throws ApplicationException {
        try {
            String registerDts = registerDts1.trim();
            String registerDt = registerDts.substring(6, 10) + registerDts.substring(3, 5) + registerDts.substring(0, 2);
            List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' and Brncode='" + brCode + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();

            List paramInfo = em.createNativeQuery("select Postingdate from clg_ow_register where emflag='" + emFlag
                    + "' and EntryDate = '" + registerDt + "' and brncode='" + brCode + "' and postingdate = clearingdate").getResultList();
            if (paramInfo.isEmpty()) {
                throw new ApplicationException("Sorry!Posting Date has not been set for this Register");
            }
            Vector paramInfos = (Vector) paramInfo.get(0);
            String postingDt = paramInfos.get(0).toString();
            String postDt = postingDt.substring(0, 4) + postingDt.substring(5, 7) + postingDt.substring(8, 10);
            if (!postDt.equals(Tempbd)) {
                throw new ApplicationException("Posting is not allowed in this Date");
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String owClgTxn(String actNature, String acNo, String custname, int ty, double amt, String curDt, String remarks, String enterBy, float trsno, String authBy,
            String orgnBrCode, String destBrCode) throws ApplicationException {
        try {
            int tyRemote = 1;
            if (ty == 1) {
                tyRemote = 0;
            }
            String orgnIntersoleAcNo = ftsPostingRemote.getGlHeadFromParam(orgnBrCode, SiplConstant.ISO_HEAD.getValue());
            String destIntersoleAcNo = ftsPostingRemote.getGlHeadFromParam(destBrCode, SiplConstant.ISO_HEAD.getValue());

            /* Code for handling inter branch transaction Added By Dhirendra Singh*/
            List chkList3 = em.createNativeQuery("select acname from gltable where acno='" + orgnIntersoleAcNo + "' and substring(AcNo,1,2)='"
                    + orgnIntersoleAcNo.substring(0, 2) + "'").getResultList();
            if (chkList3.isEmpty()) {
                return "A/C. Name Not Found For Your Branch Intersole A/C. No.";
            }
            Vector recLst3 = (Vector) chkList3.get(0);
            String acname1 = recLst3.get(0).toString();

            List chkList4 = em.createNativeQuery("select acname from gltable where acno='" + destIntersoleAcNo + "' and substring(AcNo,1,2)='"
                    + destIntersoleAcNo.substring(0, 2) + "'").getResultList();
            if (chkList4.isEmpty()) {
                return "A/C. Name Not Found For Remote Branch Intersole A/C. No.";
            }
            Vector recLst4 = (Vector) chkList4.get(0);
            String acname2 = recLst4.get(0).toString();
            if (orgnBrCode.equalsIgnoreCase(destBrCode)) {
                String resultFromInsertRecons = ftsPostingRemote.insertRecons(actNature, acNo, ty, amt, curDt, curDt, 1,
                        remarks, enterBy, trsno, null, 0f, "Y", authBy, 0, 3, null, null, 0f, null, "A", 1, null, 0f, null,
                        null, orgnBrCode, destBrCode, 0, null, "", "");
                if (!resultFromInsertRecons.equalsIgnoreCase("True")) {
                    return "Problem in Insertion in recons.....";
                }
                //clgamt = clgamt + amt;
                float resultDrecNo = ftsPostingRemote.getRecNo();
                if (ty == 0) {
                    Query insertForClg = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,valuedt,cramt,ty,trantype,recno,trsno,"
                            + "auth,enterby,authby,details,org_brnid,dest_brnid) values('" + acNo + "','" + custname + "','" + curDt
                            + "','" + curDt + "'," + amt + ",0,1," + resultDrecNo + "," + trsno + ",'Y','" + enterBy + "','" + authBy
                            + "','" + remarks + "','" + orgnBrCode + "','" + destBrCode + "')");
                    int resultForClg = insertForClg.executeUpdate();
                    if (resultForClg <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }
                } else {
                    Query insertForClg = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,valuedt,dramt,ty,trantype,recno,trsno,"
                            + "auth,enterby,authby,details,org_brnid,dest_brnid) values('" + acNo + "','" + custname + "','" + curDt
                            + "','" + curDt + "'," + amt + ",1,1," + resultDrecNo + "," + trsno + ",'Y','" + enterBy + "','" + authBy
                            + "','" + remarks + "','" + orgnBrCode + "','" + destBrCode + "')");
                    int resultForClg = insertForClg.executeUpdate();
                    if (resultForClg <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }
                }
            } else {
                String orgnAlphaCode = "";
                List orgnAlphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(orgnBrCode) + "'").getResultList();
                if (orgnAlphaCodeList.size() > 0) {
                    Vector orgnAlphaCodeVect = (Vector) orgnAlphaCodeList.get(0);
                    orgnAlphaCode = orgnAlphaCodeVect.get(0).toString();
                } else {
                    return "ERROR OCCURED: - Please enter ALPHA CODE for Orign branch!";
                }
                remarks = "AT " + orgnAlphaCode + ": " + remarks;

                String recon = ftsPostingRemote.insertRecons(actNature, acNo, ty, amt, curDt, curDt, 1, remarks, enterBy, trsno,
                        null, 0f, "Y", authBy, 0, 3, null, null, 0f, null, "A", 9999, null, null, null, null, orgnBrCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    return "Problem in Insertion in recons :";
                }

                recon = ftsPostingRemote.insertRecons("PO", destIntersoleAcNo, tyRemote, amt, curDt, curDt, 1, remarks, enterBy, trsno,
                        null, 0f, "Y", authBy, 0, 3, null, null, 0f, null, "A", 9999, null, null, null, null, orgnBrCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    return "Problem in Insertion in recons :";
                }

                recon = ftsPostingRemote.insertRecons("PO", orgnIntersoleAcNo, ty, amt, curDt, curDt, 1, remarks, enterBy, trsno,
                        null, 0f, "Y", authBy, 0, 3, null, null, 0f, null, "A", 8888, null, null, null, null, orgnBrCode, destBrCode, 0, null, "", "");
                if (!recon.equals("TRUE")) {
                    return "Problem in Insertion in recons :";
                }

                float RecNo = ftsPostingRemote.getRecNo();
                float recNo1 = ftsPostingRemote.getRecNo();
                float recNo2 = ftsPostingRemote.getRecNo();
                if (ty == 0) {
                    Query insertQuery2 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,cramt,ty,trantype,recno,trsno,auth,enterby,authby,details,"
                            + "org_brnid,dest_brnid, valueDt)"
                            + "values ('" + acNo + "','" + custname + "','" + curDt + "'," + amt + ",0," + 1 + "," + RecNo + "," + trsno
                            + ",'Y','" + enterBy + "','" + authBy + "','" + remarks + "','" + orgnBrCode + "','" + destBrCode + "','" + curDt + "')");
                    int var2 = insertQuery2.executeUpdate();
                    if (var2 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }

                    Query insertQuery20 = em.createNativeQuery("insert into recon_clg_d(acno,ty,dramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values ('" + destIntersoleAcNo + "',1," + amt + ","
                            + 1 + "," + recNo1 + ",'" + curDt + "','VCH: Today Clearing','T','" + enterBy + "','" + acname1 + "'," + trsno + ",'"
                            + orgnBrCode + "','" + destBrCode + "','Y','" + authBy + "','" + curDt + "')");
                    int var20 = insertQuery20.executeUpdate();
                    if (var20 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + destIntersoleAcNo;
                    }

                    Query insertQuery4 = em.createNativeQuery("insert into recon_clg_d(acno,ty,cramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values ('" + orgnIntersoleAcNo + "',0," + amt + ","
                            + 1 + "," + recNo2 + ",'" + curDt + "','VCH: Today Clearing','T','" + enterBy + "','" + acname2 + "'," + trsno + ",'"
                            + orgnBrCode + "','" + destBrCode + "','Y','" + authBy + "','" + curDt + "')");
                    int var4 = insertQuery4.executeUpdate();
                    if (var4 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + orgnIntersoleAcNo;
                    }
                } else {
                    Query insertQuery2 = em.createNativeQuery("insert into recon_clg_d(acno,custname,dt,dramt,ty,trantype,recno,trsno,auth,enterby,authby,details,"
                            + "org_brnid,dest_brnid, valueDt)"
                            + "values ('" + acNo + "','" + custname + "','" + curDt + "'," + amt + ",1," + 1 + "," + RecNo + "," + trsno
                            + ",'Y','" + enterBy + "','" + authBy + "','" + remarks + "','" + orgnBrCode + "','" + destBrCode + "','" + curDt + "')");
                    int var2 = insertQuery2.executeUpdate();
                    if (var2 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + acNo;
                    }

                    Query insertQuery20 = em.createNativeQuery("insert into recon_clg_d(acno,ty,cramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values ('" + destIntersoleAcNo + "',0," + amt + ","
                            + 1 + "," + recNo1 + ",'" + curDt + "','VCH: Today Clearing','T','" + enterBy + "','" + acname1 + "'," + trsno + ",'"
                            + orgnBrCode + "','" + destBrCode + "','Y','" + authBy + "','" + curDt + "')");
                    int var20 = insertQuery20.executeUpdate();
                    if (var20 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + destIntersoleAcNo;
                    }

                    Query insertQuery4 = em.createNativeQuery("insert into recon_clg_d(acno,ty,dramt,trantype,recno,dt,details,SCREENFLAG,enterby,"
                            + "CUSTNAME,trsno,org_brnid,dest_brnid,auth,authby,valueDt) values ('" + orgnIntersoleAcNo + "',1," + amt + ","
                            + 1 + "," + recNo2 + ",'" + curDt + "','VCH: Today Clearing','T','" + enterBy + "','" + acname2 + "'," + trsno + ",'"
                            + orgnBrCode + "','" + destBrCode + "','Y','" + authBy + "','" + curDt + "')");
                    int var4 = insertQuery4.executeUpdate();
                    if (var4 <= 0) {
                        return "Problem in Insertion in Recon_clg_d For Acno" + orgnIntersoleAcNo;
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String insertTransfer(String acctNature, String acno, int ty, Double amt, String dt, int TranType, String details, String enterby,
            float trsNo, String trantime, float recNo, String auth, String authby, int trandesc, int payby, String instrNo, float TokenNo,
            String tokenPaidBy, String SubTokenNo, int iy, String tdacno, Float voucherno, String intflag, String closeflag, String org_brnid,
            String dest_brnid, int tran_id, String term_id, String brCode, String valueDt) throws ApplicationException {

        Double cramt = 0.0d;
        Double dramt = 0.0d;
        int var20 = 0;
        try {
            if (trantime == null) {
                trantime = "19000101";
                List tempBd = em.createNativeQuery("select date FROM bankdays WHERE DAYENDFLAG='N' And Brncode  = '" + brCode + "'").getResultList();
                Vector tempCurrent = (Vector) tempBd.get(0);
                trantime = tempCurrent.get(0).toString();
                String curCode = ftsPostingRemote.getCurrentBrnCode(acno);
                if (ty == 0) {
                    cramt = amt;
                    dramt = 0.0d;
                } else if (ty == 1) {
                    cramt = 0.0d;
                    dramt = amt;
                } else {
                    return "Invalid Transaction Mode for Account No :-" + acno;
                }

                if (recNo == 0) {
                    //recNo = ftsPosting.getRecNo(acno.substring(0,2));
                    recNo = ftsPostingRemote.getRecNo();
                }

                Query insertQuery20 = em.createNativeQuery("insert into recon_trf_d( acno , custname, dt, Dramt, CrAmt, Ty, TranType,"
                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                        + "values ('" + acno + "','" + ftsPostingRemote.getCustName(acno) + "','" + trantime + "'," + dramt + "," + cramt + "," + ty + "," + TranType + "," + recNo + ","
                        + trsNo + ",'" + instrNo + "'," + payby + "," + iy + ",'" + auth + "','" + enterby + "','" + authby + "','" + tokenPaidBy + "',"
                        + trandesc + "," + TokenNo + ",'" + SubTokenNo + "', CURRENT_TIMESTAMP,'" + details + "'," + tran_id + ",'" + term_id + "','" + brCode + "','"
                        + curCode + "','" + valueDt + "','','')");
                var20 = insertQuery20.executeUpdate();

            }

            if (var20 > 0) {
                return "TRUE";
            } else {
                return "Insertion Problem in Recon_Trf_D for A/c No :- " + acno;
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String getCityCodeAsMiccr(String brCode) throws ApplicationException {
        String cityCodeAsMiccr = "";
        try {
            List miccrList = em.createNativeQuery("select ifnull(micr,'') as micr from bnkadd bn,branchmaster bm where bn.alphacode=bm.alphacode and bm.brncode=" + Integer.parseInt(brCode)).getResultList();
            if (!miccrList.isEmpty()) {
                Vector element = (Vector) miccrList.get(0);
                cityCodeAsMiccr = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return cityCodeAsMiccr;
    }
    // Added by Manish Kumar

    public List getBranchList() throws ApplicationException {
        List branchList = new ArrayList();
        try {
            branchList = em.createNativeQuery("select case CHAR_LENGTH(BrnCode) when 1 then concat('0',BrnCode) else BrnCode end as BrnCode, case  BrnCode when '90' then 'Head Office' else BranchName end as BranchName from branchmaster order by BranchName").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return branchList;
    }

    public String generateOutwardCtsFile(String path, String branchCode, String txnDate, String generatedByBrnCode, String fileGeneratedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List clgOwList = new ArrayList();
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean flag = false;
        String branchMICR = "", ChequeNo = "", SortCode = "", SanNo = "", TransactionCode = "", Amount = "", AccountNo = "", AccountName = "";
        Date curDate = new Date();
        int count = 0;
        File file = null;
        String fileName = "";
        try {
            clgOwList = em.createNativeQuery("select CONCAT(b.micr, b.micrcode, case length(b.branchcode) when 1 then CONCAT('00',b.branchcode) when 2 then CONCAT('0',b.branchcode) else b.branchcode end) as BranchMICR, c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode,c.BnkCode,c.BranchCode) as SortCode, c.SanNo,"
                    + "c.TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo, c.AccountName from bnkadd b, clg_ow_entry c, branchmaster bm "
                    + "where bm.BrnCode = c.orgnbrcode and bm.AlphaCode = b.alphacode  and c.Txnstatus = 'V' and c.TxnDate='" + txnDate + "'").getResultList();
// brach wise
//                clgOwList = em.createNativeQuery("select (select CONCAT(micr,micrcode,case length(branchcode) when 1 then CONCAT('00',branchcode) when 2 then CONCAT('0',branchcode) else branchcode end)  from bnkadd "
//                                                 + "where alphacode = (select AlphaCode from branchmaster  where  BrnCode = '"+branchCode+"'))as BranchMICR, TxnInstNo as ChequeNo, CONCAT(AreaCode,BnkCode,BranchCode) as SortCode, SanNo, TransactionCode, TxnInstAmt as Amount, Acno as AccountNo, AccountName from clg_ow_entry where TxnDate='"+txnDate+"'").getResultList();

            if (clgOwList.isEmpty()) {
                throw new ApplicationException("Data does not exist coressponding given input or not verified !");
            } else {
                String fileNumber = "00001";
                List selectList = em.createNativeQuery("select substring(file_name,-9,5) from cbs_npci_mapper_files where file_gen_date ='" + ymd.format(curDate) + "' and file_name like '%CTS-OUTWARD-%' order by file_gen_date,file_name desc  limit 1").getResultList();
                if (!selectList.isEmpty()) {
                    Vector vec = (Vector) selectList.get(0);
                    int n = Integer.parseInt(vec.get(0).toString());
                    if (n >= 0 && n < 10) {
                        fileNumber = "0000" + (++n);
                    } else if (n > 9 && n < 100) {
                        fileNumber = "000" + (++n);
                    } else if (n > 99 && n < 1000) {
                        fileNumber = "00" + (++n);
                    } else if (n > 999 && n < 10000) {
                        fileNumber = "0" + (++n);
                    } else if (n > 99999) {
                        throw new ApplicationException("This Folder can have maximum 99999 file !");
                    } else {
                        fileNumber = "" + (++n);
                    }
                }
                //File file = new File(path + "CTS-OUTWARD-22042017-00001.txt");
                fileName = "CTS-OUTWARD-" + dmy.format(curDate) + "-" + fileNumber + ".txt";
                System.out.println("File path : " + path + "" + fileName);
                file = new File(path + "/" + fileName);
                if (file.exists()) {
                    file.delete();
                }
                ut.begin();
                if (file.createNewFile()) {
                    fw = new FileWriter(file.getCanonicalFile());
                    bw = new BufferedWriter(fw);
                    for (int i = 0; i < clgOwList.size(); i++) {
                        Vector vec = (Vector) clgOwList.get(i);
                        branchMICR = vec.get(0).toString().trim();//alredy padded 0 using query
                        ChequeNo = vec.get(1).toString().trim();
                        SortCode = vec.get(2).toString().trim();
                        SanNo = vec.get(3).toString().trim();
                        TransactionCode = vec.get(4).toString().trim();
                        Amount = vec.get(5).toString().trim();
                        AccountNo = vec.get(6).toString().trim();
                        AccountName = vec.get(7).toString().trim();
                        bw.write(branchMICR + "|" + ParseFileUtil.addTrailingZeros(ChequeNo, 6) + "|" + ParseFileUtil.addTrailingZeros(SortCode, 9) + "|" + ParseFileUtil.addTrailingZeros(SanNo, 7) + "|" + ParseFileUtil.addTrailingZeros(TransactionCode, 3) + "|" + ParseFileUtil.addTrailingZerosWithoutDecimal(Amount, 13) + "|" + ParseFileUtil.addTrailingZeros(AccountNo, 15) + "|" + ParseFileUtil.addSuffixSpaces(AccountName, 25));
                        bw.write("\n");
                        count++;
                    }
                    String query = "INSERT INTO cbs_npci_mapper_files (FILE_NO, FILE_GEN_DATE, FILE_NAME, FILE_GEN_BY, FILE_GEN_TIME, FILE_GEN_BRNCODE, FILE_GEN_TYPE, SEQ_NO, PREMIUM_AMOUNT, UTR, UTR_DATE) VALUES ((select COALESCE(max(file_no),0)+1 from cbs_npci_mapper_files t where t.file_gen_type ='CTS-OW'), '" + ymd.format(curDate) + "', '" + fileName + "', '" + fileGeneratedBy + "', '" + ymdhms.format(curDate) + "', '" + generatedByBrnCode + "', 'CTS-OW', null, null, null, null)";
                    int result = em.createNativeQuery(query).executeUpdate();
                    if (result > 0) {
                    } else {
                        ut.rollback();
                        throw new ApplicationException("Data insertion problem !");
                    }
                } else {
                    throw new ApplicationException("File Creation problem !");
                }
            }
            if (count == clgOwList.size()) {
                ut.commit();
            } else {
                fileName = "";
                ut.rollback();
                throw new ApplicationException("File insertion problem !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public String generateOutwardCtsFileKcbl(String path, String branchCode, String txnDate, String generatedByBrnCode, String fileGeneratedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List clgOwList = new ArrayList();
        BufferedWriter bw = null;
        FileWriter fw = null;
        String filler = "0000000000000", InstrumentNumber = "", SortCode = "", TC = "", ReversalFlag = "", AccountNumber = "",
                AQBFlag = "", CoreBatchNumber = "", AccountBranchCode = "", ProductCode = "", AccountName = "";
        Date curDate = new Date();
        int count = 0;
        File file = null;
        String fileName = "", orgnCond = "";
        double InstrumentAmount = 0d;
        BigDecimal chequeAmt = new BigDecimal("0");
        BigDecimal multiple = new BigDecimal("100");
        String alphaCode = common.getAlphacodeByBrncode(branchCode);
        if (alphaCode.equalsIgnoreCase("HO")) {
            orgnCond = "";
        } else {
            orgnCond = "and orgnbrcode = '" + branchCode + "'";
        }
        try {
            clgOwList = em.createNativeQuery("select c.TxnInstNo,CONCAT(c.AreaCode,c.BnkCode,c.BranchCode) as SortCode,''TC,''ReversalFlag,c.TxnInstAmt,c.Acno,'Y'AQBFlag,'0000000000'CoreBatchNumber,c.AccountName,\n"
                    + "substring(c.Acno,1,2)  BranchCode,substring(c.Acno,3,2) ProductCode\n"
                    + "from bnkadd b, clg_ow_entry c, branchmaster bm \n"
                    + "where bm.BrnCode = c.orgnbrcode and bm.AlphaCode = b.alphacode  and c.Txnstatus = 'V' and c.TxnDate='" + txnDate + "' " + orgnCond + "").getResultList();
            if (clgOwList.isEmpty()) {
                throw new ApplicationException("Data does not exist coressponding given input or not verified !");
            } else {
                fileName = "CTS_OUTWARD_" + alphaCode + "_" + dmy.format(curDate) + ".txt";
                System.out.println("File path : " + path + "" + fileName);
                file = new File(path + "/" + fileName);
                if (file.exists()) {
                    file.delete();
                }
                ut.begin();
                if (file.createNewFile()) {
                    fw = new FileWriter(file.getCanonicalFile());
                    bw = new BufferedWriter(fw);
                    for (int i = 0; i < clgOwList.size(); i++) {
                        Vector vec = (Vector) clgOwList.get(i);

                        InstrumentNumber = vec.get(0).toString().trim();
                        SortCode = vec.get(1).toString().trim();
                        TC = vec.get(2).toString().trim();
                        ReversalFlag = vec.get(3).toString().trim();
                        InstrumentAmount = Double.parseDouble(vec.get(4).toString().trim());
                        chequeAmt = new BigDecimal(formatter2.format(InstrumentAmount));
                        AccountNumber = vec.get(5).toString().trim();
                        AQBFlag = vec.get(6).toString().trim();
                        CoreBatchNumber = vec.get(7).toString().trim();
                        AccountName = vec.get(8).toString().trim();
                        AccountBranchCode = vec.get(9).toString().trim();
                        ProductCode = vec.get(10).toString().trim();
                        bw.write(ParseFileUtil.addSuffixSpaces(filler, 13)
                                + ParseFileUtil.addTrailingZeros(InstrumentNumber, 6)
                                + SortCode
                                + ParseFileUtil.addSuffixSpaces("29", 2)
                                + ParseFileUtil.addSuffixSpaces(ReversalFlag, 1)
                                + ParseFileUtil.addTrailingZerosWithoutDecimal(String.valueOf(Math.round(chequeAmt.multiply(multiple).doubleValue())), 16)
                                + ParseFileUtil.addTrailingZeros("", 16)
                                + AQBFlag
                                + ParseFileUtil.addTrailingZeros("", 10)
                                + ParseFileUtil.addSuffixSpaces(AccountName, 100)
                                + ParseFileUtil.addTrailingZeros("", 5)
                                + ParseFileUtil.addTrailingZeros("", 5));

                        bw.write("\n");
                        count++;
                    }

                } else {
                    throw new ApplicationException("File Creation problem !");
                }
            }
            if (count == clgOwList.size()) {
                ut.commit();
            } else {
                fileName = "";
                ut.rollback();
                throw new ApplicationException("File insertion problem !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

//    
//     public String generateOutwardCtsFileKcbl(String path, String branchCode, String txnDate, String generatedByBrnCode, String fileGeneratedBy) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        List clgOwList = new ArrayList();
//        BufferedWriter bw = null;
//        FileWriter fw = null;
//        String filler = "0000000000000", InstrumentNumber = "", SortCode = "", TC = "", ReversalFlag = "", AccountNumber = "",
//                AQBFlag = "", CoreBatchNumber = "", AccountBranchCode = "", ProductCode = "", AccountName = "";
//        Date curDate = new Date();
//        int count = 0;
//        File file = null;
//        String fileName = "", orgnCond = "";
//        double InstrumentAmount = 0d;
//        BigDecimal chequeAmt = new BigDecimal("0");
//        BigDecimal multiple = new BigDecimal("100");
//        String alphaCode = common.getAlphacodeByBrncode(branchCode);
//        if (alphaCode.equalsIgnoreCase("HO")) {
//            orgnCond = "";
//        } else {
//            orgnCond = "and orgnbrcode = '" + branchCode + "'";
//        }
//        try {
//            clgOwList = em.createNativeQuery("select c.TxnInstNo,CONCAT(c.AreaCode,c.BnkCode,c.BranchCode) as SortCode,''TC,''ReversalFlag,c.TxnInstAmt,c.Acno,'Y'AQBFlag,'0000000000'CoreBatchNumber,c.AccountName,\n"
//                    + "substring(c.Acno,1,2)  BranchCode,substring(c.Acno,3,2) ProductCode\n"
//                    + "from bnkadd b, clg_ow_entry c, branchmaster bm \n"
//                    + "where bm.BrnCode = c.orgnbrcode and bm.AlphaCode = b.alphacode  and c.Txnstatus = 'V' and c.TxnDate='" + txnDate + "' " + orgnCond + "").getResultList();
//            if (clgOwList.isEmpty()) {
//                throw new ApplicationException("Data does not exist coressponding given input or not verified !");
//            } else {
//                fileName = "CTS_OUTWARD_" + alphaCode + "_" + dmy.format(curDate) + ".txt";
//                System.out.println("File path : " + path + "" + fileName);
//                file = new File(path + "/" + fileName);
//                if (file.exists()) {
//                    file.delete();
//                }
//                ut.begin();
//                if (file.createNewFile()) {
//                    fw = new FileWriter(file.getCanonicalFile());
//                    bw = new BufferedWriter(fw);
//                    for (int i = 0; i < clgOwList.size(); i++) {
//                        Vector vec = (Vector) clgOwList.get(i);
//
//                        InstrumentNumber = vec.get(0).toString().trim();
//                        SortCode = vec.get(1).toString().trim();
//                        TC = vec.get(2).toString().trim();
//                        ReversalFlag = vec.get(3).toString().trim();
//                        InstrumentAmount = Double.parseDouble(vec.get(4).toString().trim());
//                        chequeAmt = new BigDecimal(formatter2.format(InstrumentAmount));                                            
//                        AccountNumber = vec.get(5).toString().trim();
//                        AQBFlag = vec.get(6).toString().trim();
//                        CoreBatchNumber = vec.get(7).toString().trim();
//                        AccountName = vec.get(8).toString().trim();
//                        AccountBranchCode = vec.get(9).toString().trim();
//                        ProductCode = vec.get(10).toString().trim();
//                        bw.write(ParseFileUtil.addSuffixSpaces(filler, 13)
//                                + ParseFileUtil.addTrailingZeros(InstrumentNumber, 6)
//                                + SortCode
//                                + ParseFileUtil.addSuffixSpaces("29", 2)
//                                + ParseFileUtil.addSuffixSpaces(ReversalFlag, 1)
//                                + ParseFileUtil.addTrailingZerosWithoutDecimal(String.valueOf(Math.round(chequeAmt.multiply(multiple).doubleValue())), 16)
//                                + ParseFileUtil.addTrailingZeros("", 16)
//                                + AQBFlag
//                                + ParseFileUtil.addTrailingZeros("", 10)
//                                + ParseFileUtil.addSuffixSpaces(AccountName, 100)
//                                + ParseFileUtil.addTrailingZeros("", 5)
//                                + ParseFileUtil.addTrailingZeros("", 5));
//
//                        bw.write("\n");
//                        count++;
//                    }
//                    String query = "INSERT INTO cbs_npci_mapper_files (FILE_NO, FILE_GEN_DATE, FILE_NAME, FILE_GEN_BY, FILE_GEN_TIME, FILE_GEN_BRNCODE, FILE_GEN_TYPE, SEQ_NO, PREMIUM_AMOUNT, UTR, UTR_DATE) VALUES ((select COALESCE(max(file_no),0)+1 from cbs_npci_mapper_files t where t.file_gen_type ='CTS-OW'), '" + ymd.format(curDate) + "', '" + fileName + "', '" + fileGeneratedBy + "', '" + ymdhms.format(curDate) + "', '" + generatedByBrnCode + "', 'CTS-OW', null, null, null, null)";
//                    int result = em.createNativeQuery(query).executeUpdate();
//                    if (result > 0) {
//                    } else {
//                        ut.rollback();
//                        throw new ApplicationException("Data insertion problem !");
//                    }
//                } else {
//                    throw new ApplicationException("File Creation problem !");
//                }
//            }
//            if (count == clgOwList.size()) {
//                ut.commit();
//            } else {
//                fileName = "";
//                ut.rollback();
//                throw new ApplicationException("File insertion problem !");
//            }
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();
//                }
//                if (fw != null) {
//                    fw.close();
//                }
//            } catch (Exception e) {
//                file.delete();
//                throw new ApplicationException(e.getMessage());
//            }
//        }
//        return fileName;
//    }
    public List<OutwardCtsFileGrid> showOutwardCtsFile(String branchCode, String generatedDate, String generatedBy) throws ApplicationException {
        List<OutwardCtsFileGrid> outWardList = new ArrayList<OutwardCtsFileGrid>();
        try {
            if (branchCode.equalsIgnoreCase("all")) {
                branchCode = "90";
            }
            System.out.println("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,file_gen_by from cbs_npci_mapper_files where file_gen_date='" + generatedDate + "' and file_gen_type='CTS-OW' and file_gen_brncode ='" + branchCode + "'");
            List list = em.createNativeQuery("select file_no,date_format(file_gen_date,'%d/%m/%Y'),file_name,file_gen_by from cbs_npci_mapper_files where file_gen_date='" + generatedDate + "' and file_gen_type='CTS-OW' and file_gen_brncode ='" + branchCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data to show.");
            } else {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    OutwardCtsFileGrid pojo = new OutwardCtsFileGrid();
                    pojo.setFileNo(new BigInteger(vec.get(0).toString()));
                    pojo.setFileGenDt(vec.get(1).toString());
                    pojo.setFileName(vec.get(2).toString());
                    pojo.setFileGenBy(vec.get(3).toString());
                    outWardList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return outWardList;
    }

    public String generateclringOutwardTxtFile(String path, String branchCode, String txnDate, String genrateByBrnCode,
            String generatedBy, String emFlag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List clgOwList = new ArrayList();
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean flag = false;
        String fileName = "";
        String branchMICR = "", chequeNo = "", transCode = "", amount = "", accountNo = "",
                accountName = "", openingDt = "", accountStatus = "";
        Date curDate = new Date();
        int count = 0;
        File file = null;
        try {
            String query = "";
            if (branchCode.equalsIgnoreCase("ALL")) {
                query = "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo, "
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,a.openingdt from  clg_ow_entry c, "
                        + "branchmaster bm ,accountmaster a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') "
                        + "and c.Txnstatus='V' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' "
                        + "union "
                        + "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo, "
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,'' from  clg_ow_entry c, branchmaster bm ,"
                        + "gltable a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and c.Txnstatus='V' "
                        + "and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' ";
            } else {
                query = "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo,"
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,a.openingdt from  clg_ow_entry c, "
                        + "branchmaster bm ,accountmaster a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and "
                        + "c.Txnstatus='V' and c.orgnbrcode='" + branchCode + "' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' "
                        + "union "
                        + "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as "
                        + "BranchMICR, ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as "
                        + "AccountNo,ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,'' from  clg_ow_entry c, "
                        + "branchmaster bm ,gltable a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and "
                        + "c.Txnstatus='V' and c.orgnbrcode='" + branchCode + "' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' ";
            }
            clgOwList = em.createNativeQuery(query).getResultList();
            if (clgOwList.isEmpty()) {
                throw new ApplicationException("Data does not exist coressponding given input or not verified !");
            } else {
//               
                fileName = "TB" + ddmmyy.format(curDate) + "." + branchCode + ".txt";
                System.out.println("File path : " + path + "" + fileName);
                file = new File(path + "/" + fileName);
                if (file.exists()) {
                    file.delete();
                }
                ut.begin();
                if (file.createNewFile()) {
                    fw = new FileWriter(file.getCanonicalFile());
                    bw = new BufferedWriter(fw);
                    for (int i = 0; i < clgOwList.size(); i++) {
                        Vector vec = (Vector) clgOwList.get(i);
                        chequeNo = vec.get(0).toString().trim();
                        branchMICR = vec.get(1).toString().trim();
                        transCode = vec.get(2).toString().trim();
                        BigDecimal amtDecVal = new BigDecimal(vec.get(3).toString().trim()).multiply(new BigDecimal(100));
                        amount = String.valueOf(amtDecVal.toBigInteger());
                        accountNo = vec.get(4).toString().trim();
                        accountName = vec.get(5).toString().trim();
                        openingDt = vec.get(6).toString().trim();
                        accountStatus = "5";
                        if (!openingDt.equals("")) {
                            long datediff = CbsUtil.monthDiff(ymd.parse(openingDt), ymd.parse(ymd.format(new Date())));
                            accountStatus = datediff < 7 ? "9" : "5"; //9- For New, 5- For Old
                        }
                        bw.write(ParseFileUtil.addTrailingZeros(chequeNo, 6) + ParseFileUtil.addTrailingZeros(branchMICR, 9)
                                + ParseFileUtil.addTrailingSpaces(transCode, 3) + ParseFileUtil.addTrailingZeros(amount, 13)
                                + accountStatus + ParseFileUtil.addSuffixSpaces(accountName, 25));
                        bw.write("\n");
                        count++;
                    }
                } else {
                    throw new ApplicationException("File Creation problem !");
                }
            }
            if (count == clgOwList.size()) {
                ut.commit();
            } else {
                fileName = "";
                ut.rollback();
                throw new ApplicationException("File insertion problem !");
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }

    public String generateOutwardTxtFile(String path, String branchCode, String txnDate, String generatedByBrnCode,
            String fileGeneratedBy, String emFlag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        List clgOwList = new ArrayList();
        BufferedWriter bw = null;
        FileWriter fw = null;
        boolean flag = false;
        String branchMICR = "", chequeNo = "", transCode = "", amount = "", accountNo = "",
                accountName = "", openingDt = "", accountStatus = "";
        Date curDate = new Date();
        int count = 0;
        File file = null;
        String fileName = "";
        try {
            String query = "";
            if (branchCode.equalsIgnoreCase("ALL")) {
                query = "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo, "
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,a.openingdt from  clg_ow_entry c, "
                        + "branchmaster bm ,accountmaster a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') "
                        + "and c.Txnstatus='V' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' "
                        + "union "
                        + "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo, "
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,'' from  clg_ow_entry c, branchmaster bm ,"
                        + "gltable a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and c.Txnstatus='V' "
                        + "and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' ";
            } else {
                query = "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as BranchMICR, "
                        + "ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as AccountNo,"
                        + "ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,a.openingdt from  clg_ow_entry c, "
                        + "branchmaster bm ,accountmaster a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and "
                        + "c.Txnstatus='V' and c.orgnbrcode='" + branchCode + "' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' "
                        + "union "
                        + "select c.TxnInstNo as ChequeNo, CONCAT(c.AreaCode, c.BnkCode,lpad(c.branchcode,3,'0')) as "
                        + "BranchMICR, ifnull(c.TransactionCode,'')TransactionCode, c.TxnInstAmt as Amount, c.Acno as "
                        + "AccountNo,ifnull(LEFT(c.AccountName , 25),'') as AccountHolderName,'' from  clg_ow_entry c, "
                        + "branchmaster bm ,gltable a where c.Acno=a.acno and c.orgnbrcode=lpad(bm.BrnCode,2,'0') and "
                        + "c.Txnstatus='V' and c.orgnbrcode='" + branchCode + "' and c.dt='" + txnDate + "' and c.emflag='" + emFlag + "' ";
            }
            clgOwList = em.createNativeQuery(query).getResultList();
            if (clgOwList.isEmpty()) {
                throw new ApplicationException("Data does not exist coressponding given input or not verified !");
            } else {
//               
                fileName = "TB" + ddmmyy.format(curDate) + "." + branchCode + ".txt";
                System.out.println("File path : " + path + "" + fileName);
                file = new File(path + "/" + fileName);
                if (file.exists()) {
                    file.delete();
                }
                ut.begin();
                if (file.createNewFile()) {
                    fw = new FileWriter(file.getCanonicalFile());
                    bw = new BufferedWriter(fw);
                    for (int i = 0; i < clgOwList.size(); i++) {
                        Vector vec = (Vector) clgOwList.get(i);
                        chequeNo = vec.get(0).toString().trim();
                        branchMICR = vec.get(1).toString().trim();
                        transCode = vec.get(2).toString().trim();
                        BigDecimal amtDecVal = new BigDecimal(vec.get(3).toString().trim()).multiply(new BigDecimal(100));
                        amount = String.valueOf(amtDecVal.toBigInteger());
                        accountNo = vec.get(4).toString().trim();
                        accountName = vec.get(5).toString().trim();
                        openingDt = vec.get(6).toString().trim();
                        accountStatus = "5";
                        if (!openingDt.equals("")) {
                            long datediff = CbsUtil.monthDiff(ymd.parse(openingDt), ymd.parse(ymd.format(new Date())));
                            accountStatus = datediff < 7 ? "9" : "5"; //9- For New, 5- For Old
                        }
                        bw.write(ParseFileUtil.addTrailingZeros(chequeNo, 6) + ParseFileUtil.addTrailingZeros(branchMICR, 9)
                                + ParseFileUtil.addTrailingSpaces(transCode, 3) + ParseFileUtil.addTrailingZeros(amount, 13)
                                + ParseFileUtil.addTrailingZeros(accountNo, 25) + ParseFileUtil.addSuffixSpaces(accountName, 25)
                                + accountStatus);
                        bw.write("\n");
                        count++;
                    }
                } else {
                    throw new ApplicationException("File Creation problem !");
                }
            }
            if (count == clgOwList.size()) {
                ut.commit();
            } else {
                fileName = "";
                ut.rollback();
                throw new ApplicationException("File insertion problem !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
                file.delete();
                throw new ApplicationException(e.getMessage());
            }
        }
        return fileName;
    }
}
