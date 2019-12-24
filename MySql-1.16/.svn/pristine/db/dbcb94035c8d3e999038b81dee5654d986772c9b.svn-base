/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.constant.SiplConstant;
import com.cbs.dto.cdci.CustomerIdPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.cdci.IBSWSFacadeRemote;
import com.cbs.facade.ckycr.CkycrCommonMgmtFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.inventory.ChequeMaintinanceRegisterFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.NpaReportFacadeRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.pojo.MsgPojo;
import com.cbs.pojo.NpaStatusReportPojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Sudhir
 */
@Stateless(mappedName = "BankProcessManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class BankProcessManagementFacade implements BankProcessManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    ReconcilationManagementFacadRemote autoReconcileRemote;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPost43Remote;
    @EJB
    CommonReportMethodsRemote commonRemote;
    @EJB
    AutoTermDepositRenewalRemote autoTdRenewal;
    @EJB
    RbiReportFacadeRemote ossBeanRemote;
    @EJB
    StandingInstructionManagementRemote StInstMgmtRemote;
    @EJB
    LockerManagementFacadeRemote locMgmtRemote;
    @EJB
    SmsManagementFacadeRemote smsRemote;
    @EJB
    ChequeMaintinanceRegisterFacadeRemote cmrFacade;
    @EJB
    IBSWSFacadeRemote ibswsRemote;
    @EJB
    MessageDetailBeanRemote messageDetailBeanRemote;
    @EJB
    InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    NpaReportFacadeRemote npaRemote;
    @EJB
    CkycrCommonMgmtFacadeRemote ckycrCommonRemote;
    @EJB
    SavingAccountProductFacadeRemote productRemote;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");

    public List getPath() throws ApplicationException {
        try {
            return em.createNativeQuery("select code, description from codebook where groupcode=97 and code=1").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String execProc(int code) {
        String databasename = "", msg = "", returnMsg = "";
        try {
            List dbExecutionList = em.createNativeQuery("EXEC CBS_DATABASE_BACK_UP " + code + ",'" + databasename + "','" + msg + "'").getResultList();
        } catch (Exception ex) {
            if (ex.getCause() != null) {
                String bcause = "com.microsoft.sqlserver.jdbc.SQLServerException: The statement did not return a result set.";
                if (ex.getCause().toString().equalsIgnoreCase(bcause)) {
                    returnMsg = "Database backup has been created successfully.";
                } else {
                    returnMsg = "There is an error in creating backup";
                }
            } else {
                returnMsg = "There is an error in creating backup";
            }
        }
        return returnMsg;
    }

    /**
     * *************For get information related
     * daybeginflag*************************
     */
    public String getInitialInfo(String todaydate) throws ApplicationException {
        String dayBeginFlag = "";
        try {
            List value = em.createNativeQuery("select DayBeginFlag from cbs_bankdays where Date='" + todaydate + "' ").getResultList();
            if (!value.isEmpty()) {
                Vector value1 = (Vector) value.get(0);
                dayBeginFlag = value1.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dayBeginFlag;
    }

    @Override
    public String maxWorkingDate(String date) throws ApplicationException {
        try {
            String maxDt = "";
            List list = em.createNativeQuery("SELECT COALESCE(MAX(DATE),'') FROM cbs_bankdays WHERE DATE<'" + date + "' AND DAYBEGINFLAG='N' AND DAYENDFLAG='N'").getResultList();
            if (!list.isEmpty()) {
                Vector element = (Vector) list.get(0);
                maxDt = element.get(0).toString();
            }
            return maxDt;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public boolean isHolidayDate(String date) throws ApplicationException {
        try {
            List list = em.createNativeQuery("SELECT DAYBEGINFLAG FROM cbs_bankdays WHERE DATE='" + date + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                String dbFlag = ele.get(0).toString();

                if (dbFlag.equalsIgnoreCase("H")) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     *
     * @param todaydate
     * @param userName
     * @return
     * @throws ApplicationException
     */
    public List<MsgPojo> dayBeginProcess(String todaydate, String userName) throws ApplicationException {
        String msg = "";
        List<MsgPojo> msgList = new ArrayList<MsgPojo>();
        UserTransaction ut = context.getUserTransaction();
        try {
            try {
                ut.begin();
                List statusList = em.createNativeQuery("select date_format(DATE,'%Y%m%d') from cbs_bankdays where dayendflag='N' and date<'" + todaydate + "'").getResultList();
                if (!statusList.isEmpty()) {
                    Vector vect = (Vector) statusList.get(0);
                    String date = vect.get(0).toString();
                    throw new ApplicationException("You have not done your Central Day End of " + dmy.format(ymd.parse(date)) + " and Today Date is " + dmy.format(ymd.parse(todaydate))
                            + " . So Please contact to your Administrator.");
                }

                List value = em.createNativeQuery("select DATE from cbs_bankdays ").getResultList();
                if (value.isEmpty()) {
                    Query insertQuery = em.createNativeQuery("insert into  cbs_bankdays (DATE,BankCode,DayBeginFlag,DayEndFlag,BeginUser,BeginDate)"
                            + "values ('" + todaydate + "','CBS','Y','N','" + userName + "',now())");
                    int val = insertQuery.executeUpdate();
                    if (val <= 0) {
                        throw new ApplicationException("Problem in data insertion in CBS Bank Days ");
                    }
                } else {
                    Query update1 = em.createNativeQuery("update cbs_bankdays set DayBeginFlag='Y',BeginDate=now(),BeginUser='" + userName + "' where DATE='" + todaydate + "' ");
                    int var = update1.executeUpdate();
                    if (var < 0) {
                        throw new ApplicationException("Problem in data updation in CBS Bank Days.");
                    }
                }
                ut.commit();
                msgList.add(getMsgObj("msg", "", "Central Day Begin Process successfully completed"));
            } catch (Exception e) {
                try {
                    ut.rollback();
                    System.out.println("Problem in Central Day Begin Process " + e.getMessage());
                    throw new ApplicationException("Problem in Central Day Begin Process " + e.getMessage());
                } catch (Exception ex) {
                    System.out.println("Problem in Central Day Begin Process " + ex.getMessage());
                    throw new ApplicationException("Problem in Central Day Begin Process " + ex.getMessage());
                }
            }

            //SENIOR CITIZEN AUTO MARK
            try {
                int intSrCtzCode = ftsPost43Remote.getCodeForReportName("SENIOR-CITIZEN-MARK");
                if (intSrCtzCode == 1) {
                    try {
                        ut.begin();
                        msg = autoTdRenewal.autoSeniorCitizenMarking(todaydate, userName);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Senior Cistizen Marking"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Senior Cistizen Marking"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Monthly Interest Posting"));
            }

            //TD AUTO RENEWAL START
            try {
                String ofCode = "N";
                List autoTDOfChkList = em.createNativeQuery("select ifnull(ofdrflag,'N') from td_parameterinfo").getResultList();

                if (!autoTDOfChkList.isEmpty()) {
                    Vector autoTDOfChkVector = (Vector) autoTDOfChkList.get(0);
                    ofCode = autoTDOfChkVector.get(0).toString();
                }
                int tdRenewalCode = ftsPost43Remote.getCodeForReportName("TD-RENEWAL");
                if (ofCode.equalsIgnoreCase("N") && tdRenewalCode == 1) {
                    try {
                        ut.begin();
                        msg = autoTdRenewal.autoRenewTermDeposit();
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Renewal"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Renewal"));
                        }
                    }
                    try {
                        Integer smsCode = ftsPost43Remote.getCodeForReportName("TD-RENEWAL-SMS");
                        if (smsCode == 1) {
                            sendFdAutoRenewalSMS(todaydate);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending TD Renewal SMS." + ex.getMessage());
                        msgList.add(getMsgObj("error", "", "Problem In Sending TD Renewal SMS."));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Renewal"));
            }
            //TD AUTO MONTHLY INT POSTING START
            try {
                int intPostingCode = ftsPost43Remote.getCodeForReportName("AUTO-INT-POSTING");
                if (intPostingCode == 1) {
                    try {
                        ut.begin();
                        msg = autoTdRenewal.autoMonthlyIntPosting();
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Monthly Interest Posting"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Monthly Interest Posting"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Monthly Interest Posting"));
            }
            //TD AUTO SI POSTING START
            try {
                int siCode = ftsPost43Remote.getCodeForReportName("SI-AUTO");
                if (siCode == 1) {
                    List<SmsToBatchTo> smsBatchSiAutoList = siAutoCoverPost(todaydate);
                    try {
                        if (!smsBatchSiAutoList.isEmpty()) {
                            System.out.println("SI Auto Sms Size is--->" + smsBatchSiAutoList.size());
                            smsRemote.sendSmsToBatch(smsBatchSiAutoList);
                        }
                    } catch (Exception e) {
                        System.out.println("Problem In Sending SMS To In SI Posting." + e.getMessage());
                        msgList.add(getMsgObj("error", "", "Problem in sending SMS to in SI Posting."));
                    }
                }
            } catch (Exception e) {
                msgList.add(getMsgObj("error", "", "Problem in executing Auto SI"));
            }
            // TD AUTO LOCKER RENT POSTING START
            try {
                int lockerCode = ftsPost43Remote.getCodeForReportName("LOCKER-AUTO");
                if (lockerCode == 1) {
                    List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
                    try {
                        ut.begin();
                        smsBatchList = autoTdRenewal.autoLockerRentPosting(todaydate);
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Locker Rent Posting"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Locker Rent Posting"));
                        }
                    }
                    try {
                        smsRemote.sendSmsToBatch(smsBatchList);
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending SMS In Auto Locker Rent-->" + ex.getMessage());
                        msgList.add(getMsgObj("error", "Auto Locker Rent SMS", "Problem in sending SMS in Auto Locker Rent Posting"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Locker Rent Posting"));
            }
            //TD AUTO PAYMENT START
            try {
                Integer autoPaymentCode = ftsPost43Remote.getCodeForReportName("TD-PAYMENT");
                if (autoPaymentCode == 1) {
                    try {
                        ut.begin();
                        msg = autoTdRenewal.autoPaymentTermDeposit(todaydate, userName);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Payment"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Payment"));
                        }
                    }
                    try {
                        Integer smsCode = ftsPost43Remote.getCodeForReportName("TD-PAYMENT-SMS");
                        if (smsCode == 1) {
                            sendFdAutoPaymentSMS(todaydate);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending TD Payment SMS." + ex.getMessage());
                        msgList.add(getMsgObj("error", "", "Problem in sending TD Payment SMS."));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Term Deposit Payment"));
            }
            //SENDING SMS FOR BIRTHDAY START
            try {
                Integer codeSms = ftsPost43Remote.getCodeForReportName("BIRTH-DAY-SMS");
                if (codeSms == 1) {
                    sendBirthDaySms(todaydate);
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending Date of Birth SMS." + ex.getMessage());
                msgList.add(getMsgObj("error", "", "Problem in sending Date of Birth SMS."));
            }
            //SENDING SMS FOR KYC-EXP-ALERT
            try {
                Integer codeSms = ftsPost43Remote.getCodeForReportName("KYC-EXP-ALERT");
                if (codeSms == 1) {
                    sendKycExpAlertSms(todaydate);
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in sending Kyc Expiry Alert."));
            }
            //SENDING SMS FOR LOAN-EMI-DUE START
            try {
                Integer codeLSms = ftsPost43Remote.getCodeForReportName("LOAN-EMI-DUE-SMS");
                if (codeLSms == 1) {
                    sendLoanEmiDueSMS(todaydate);
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending Loan EMI Due SMS." + ex.getMessage());
                msgList.add(getMsgObj("error", "", "Problem in sending Loan EMI Due SMS."));
            }
            //SENDING SMS FOR LOAN-EMI-OVER-DUE START
            try {
                Integer codeESms = ftsPost43Remote.getCodeForReportName("LOAN-EMI-OVER-DUE-SMS");
                if (codeESms == 1) {
                    sendLoanEmiOverDueSMS(todaydate);
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending Loan EMI Due SMS." + ex.getMessage());
                msgList.add(getMsgObj("error", "", "Problem in sending Loan EMI Due SMS."));
            }
            //SENDING SMS FOR TD-RENEWAL-NOTICE START
            try {
                Integer smsCode = ftsPost43Remote.getCodeForReportName("TD-RENEWAL-NOTICE-SMS");
                if (smsCode == 1) {
                    sendFDRenewalNoticeSMS(todaydate);
                }
            } catch (Exception ex) {
                System.out.println("Problem In Sending Loan EMI Due SMS." + ex.getMessage());
                msgList.add(getMsgObj("error", "TD Renewal Notice SMS", ex.getMessage()));
            }
            //PENDING CHARGES POSTING AT THE TIME OF CENTRAL DAY BEGIN
            try {
                int intPostingCode = ftsPost43Remote.getCodeForReportName("PENDING-CHG-POSTING");
                if (intPostingCode == 1) {
                    try {
                        ut.begin();
                        msg = autoTdRenewal.autoPendingChargesPosting(todaydate, userName);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Penfing Charges Recovery Posting"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Auto Penfing Charges Recovery Posting"));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Auto Monthly Interest Posting"));
            }
            //Product calculation
            try {
                int savingProductCode = ftsPost43Remote.getCodeForReportName("SAVING-PRODUCT-CALC");
                if (savingProductCode == 1) {
                    try {
                        ut.begin();
                        msg = productRemote.updateDailySavingProductAtCentralDayBegin("C", todaydate);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (Exception e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing saving auto product calculation."));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing saving auto product calculation."));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing saving auto product calculation."));
            }

            //ATM Transaction fee GST reversal with bifurcation
            try {
                int atmGstReversalCode = ftsPost43Remote.getCodeForReportName("ATM-GST-REVERSAL");
                if (atmGstReversalCode == 1) {
                    try {
                        ut.begin();
                        msg = atmGstReversalProcess(todaydate);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (NotSupportedException | SystemException | ApplicationException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Atm Txn Fee GST Reversal."));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Atm Txn Fee GST Reversal."));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing ATM GST Reversal with Bifurcation Process."));
            }
            // deputed users
            try {
                int isMoveDeputedUser = ftsPost43Remote.getCodeForReportName("MOVE-DEPUTED-USERS");
                if (isMoveDeputedUser == 1) {
                    try {
                        ut.begin();
                        msg = moveDeputedUsers(todaydate);
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        ut.commit();
                    } catch (ApplicationException e) {
                        try {
                            ut.rollback();
                            e.printStackTrace();
                            msgList.add(getMsgObj("error", "", "Problem in executing Deputed Users Process."));
                        } catch (Exception ex) {
                            msgList.add(getMsgObj("error", "", "Problem in executing Deputed Users Process."));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msgList.add(getMsgObj("error", "", "Problem in executing Deputed Users Process."));
            }
            // End deputed users
            return msgList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private MsgPojo getMsgObj(String type, String process, String msg) throws ApplicationException {
        MsgPojo msgPojo = new MsgPojo();
        msgPojo.setMsgType(type);

        msgPojo.setProcessName(process);
        msgPojo.setMessage(msg);
        return msgPojo;
    }

    //For Day End Process
    public String dayEndProcess(String todaydate, String userName, String orgnCode, String directory,
            String ckycrTempImageLocation, String ctsArchievalImgLocation) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        NumberFormat formatter = new DecimalFormat("#.#");
        String msg = "";
        try {
//            List dayEndList = em.createNativeQuery("select bm.BranchName from bankdays bd, branchmaster bm where bd.date='" + todaydate
//                    + "' and bd.dayendflag = 'N' and cast(bd.brncode as UNSIGNED) = bm.BrnCode").getResultList();
//            if (!dayEndList.isEmpty()) {
//                Vector value1 = (Vector) dayEndList.get(0);
//                String brName = value1.get(0).toString();
//                return "You can not execute Central Day End Process because " + brName + " branch is still open.";
//            }

            List dayEndList = em.createNativeQuery("select bm.BranchName,bd.daybeginflag,bd.dayendflag from bankdays bd, branchmaster bm where bd.date='" + todaydate + "' "
                    + " and cast(bd.brncode as UNSIGNED) = bm.BrnCode").getResultList();
            if (!dayEndList.isEmpty()) {
                for (int k = 0; k < dayEndList.size(); k++) {
                    Vector value1 = (Vector) dayEndList.get(k);
                    String brName = value1.get(0).toString();
                    String dBegFlag = value1.get(1).toString();
                    String dEndFlag = value1.get(2).toString();
                    if ((dBegFlag.equalsIgnoreCase("N") && (dEndFlag.equalsIgnoreCase("Y")))
                            || (dBegFlag.equalsIgnoreCase("Y") && (dEndFlag.equalsIgnoreCase("N")))) {
                        return "You can not execute Central Day End Process because " + brName + " branch is still open. Or It is going to happen without working";
                    }
                }
            } else {
                return "Entry not exist for " + todaydate + " in bankdays.";
            }

            //Making CBS At Maintenance
            try {
                ut.begin();
                int m = em.createNativeQuery("update cbs_bankdays set dayendflag='M' where "
                        + "date='" + todaydate + "'").executeUpdate();
                if (m <= 0) {
                    throw new ApplicationException("Problem In Making System Maintenance.");
                }
                ut.commit();
            } catch (Exception e) {
                try {
                    ut.rollback();
                    throw new ApplicationException(e.getMessage());
                } catch (Exception ex) {
                    throw new ApplicationException(ex.getMessage());
                }
            }
            //End Here

            ut.begin();

            //Modified on 07/12/2018
            List list35 = em.createNativeQuery("select ifnull(acno,'') from abb_parameter_info where purpose='INTERSOLE ACCOUNT'").getResultList();
            if (list35.isEmpty()) {
                throw new ApplicationException("Intersole account does not exist.");
            }
            Vector v35 = (Vector) list35.get(0);
            String isoAcNo = v35.get(0).toString().trim();

            Map<String, BigDecimal> isoAmountPosition = new HashMap<>();
            List brnList = em.createNativeQuery("select brncode from branchmaster").getResultList();
            for (int b = 0; b < brnList.size(); b++) {
                Vector brnVector = (Vector) brnList.get(b);
                String brnCode = CbsUtil.lPadding(2, Integer.parseInt(brnVector.get(0).toString()));
                BigDecimal isoAmount = BigDecimal.ZERO;
                String branchIsoAcNo = brnCode + isoAcNo;
                List list36 = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) as decimal) "
                        + "from gl_recon where dt='" + todaydate + "' and acno='" + branchIsoAcNo + "' and auth='Y' and "
                        + "org_brnid<>dest_brnid").getResultList();
                if (!list36.isEmpty()) {
                    Vector v36 = (Vector) list36.get(0);
                    isoAmount = new BigDecimal(formatter.format(Double.parseDouble(v36.get(0).toString())));
                }
                isoAmountPosition.put(branchIsoAcNo, isoAmount);
            }

            if (!isIsoReconsiled(isoAmountPosition)) {
                String adviceReconsilationResult = autoReconcileRemote.adviceReconsilation(todaydate); //Intersole affected
                if (!adviceReconsilationResult.equalsIgnoreCase("true")) {
                    throw new ApplicationException(adviceReconsilationResult);
                }

                String intersoleAccResult = autoReconcileRemote.checkForIntersoleAccounts(todaydate); //Intersole affected
                if (!intersoleAccResult.equalsIgnoreCase("Intersole process Completed")) {
                    throw new ApplicationException(intersoleAccResult);
                }
            }
            //End here

            //  String autoReconcileResult = autoReconcileRemote.autoReconcilation(todaydate);
            //   if (!autoReconcileResult.equalsIgnoreCase("Reconcilation process Completed")) {
            //       throw new ApplicationException(autoReconcileResult);
            //   }
            String neftReconsilationResult = autoReconcileRemote.neftInwardOutwardReconsilation(todaydate); //No intersole role
            if (!neftReconsilationResult.equalsIgnoreCase("true")) {
                throw new ApplicationException(neftReconsilationResult);
            }

            //New addition on 10/03/2016 for reconsilation without intersole.
            String result = autoReconcileRemote.isoTxnReconsilationExceptIntersole(todaydate); //No intersole role
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException(result);
            }
            //New Addition for HCBL NEFT inward return Reconsilation Process on dated 24/09/2019;
           Integer neftCode = ftsPost43Remote.getCodeForReportName("INWARD-NEFT-RETURN-RECON");
           if (neftCode == 1) {
               String neftReconMsg = autoReconcileRemote.neftInwardReturnReconsilation("Yes", todaydate);
               if (!neftReconMsg.equalsIgnoreCase("true")) {
                   throw new ApplicationException(neftReconsilationResult);
               }
           }

            //Commented on 07/12/2018
//            List list35 = em.createNativeQuery("select ifnull(acno,'') from abb_parameter_info where purpose='INTERSOLE ACCOUNT'").getResultList();
//            if (list35.isEmpty()) {
//                throw new ApplicationException("InterSole account does not exist.");
//            }
//            Vector v35 = (Vector) list35.get(0);
//            String isoAcNo = v35.get(0).toString();
//            double isoAmt = 0d;
//            List brnList = em.createNativeQuery("select BrnCode from branchmaster").getResultList();
//            for (int b = 0; b < brnList.size(); b++) {
//                Vector brnVector = (Vector) brnList.get(b);
//                String brnCode = CbsUtil.lPadding(2, Integer.parseInt(brnVector.get(0).toString()));
//                isoAmt = 0;
//                isoAcNo = brnCode + isoAcNo;
//                List list36 = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) as decimal) from gl_recon where dt='" + todaydate + "' and acno='" + isoAcNo + "' and auth='Y' and org_brnid<>dest_brnid").getResultList();
//                if (!list36.isEmpty()) {
//                    Vector v36 = (Vector) list36.get(0);
//                    isoAmt = Double.parseDouble(v36.get(0).toString());
//                }
//                if (isoAmt != 0) {
//                    throw new ApplicationException("Intersole Transaction Is Still Pending.");
//                }
//            }
            //Commented on 07/12/2018 End here

            /*
             * Commented By Dhirendra Singh because it does not use.
             */
//            String hoConsolidateEntryResult = autoReconcileRemote.hoConsolidateEntry(userName);
//            if (!hoConsolidateEntryResult.equalsIgnoreCase("Successful")) {
//                throw new ApplicationException(hoConsolidateEntryResult);
//            }
            /**
             * Saving Product Calculation.
             */
//            String productProcessMessage = savingAccProduct.updateDailySavingProductAtCentral("C");
//            if (!productProcessMessage.equalsIgnoreCase("true")) {
//                throw new ApplicationException(productProcessMessage);
//            }
            /**
             * End here
             */
            em.createNativeQuery("insert into counterdetails_his(CounterNo,UserId,Name,StatusOfCounter,EnterBy,Trantime) select CounterNo,UserId,Name,StatusOfCounter,EnterBy,Trantime from counterdetails").executeUpdate();
            em.createNativeQuery("delete from counterdetails").executeUpdate();
            em.createNativeQuery("delete from tokentable_debit").executeUpdate();
            em.createNativeQuery("delete from recon_cash_d").executeUpdate();
            em.createNativeQuery("delete from recon_clg_d").executeUpdate();
            em.createNativeQuery("delete from recon_trf_d").executeUpdate();
            em.createNativeQuery("delete from recon_ext_d").executeUpdate();
            em.createNativeQuery("delete from tokentable").executeUpdate();
            em.createNativeQuery("delete from tokentable_credit").executeUpdate();
            em.createNativeQuery("ALTER TABLE tokentable_credit AUTO_INCREMENT = 0").executeUpdate();
//            em.createNativeQuery("update reconvmast set recno=0").executeUpdate();
//            em.createNativeQuery("update reconvmast_trans set trsno=0").executeUpdate();
            em.createNativeQuery("delete from min_temp").executeUpdate();
            em.createNativeQuery("delete from tran_temp").executeUpdate();

            /**
             **Added by Dinesh For CTS**
             */
//            Query insertCTS = em.createNativeQuery("insert into cts_clg_in_entry_history select * from cts_clg_in_entry where dt = DATE_FORMAT(now(),'%Y%m%d')");
            Query insertCTS = em.createNativeQuery("insert into cts_clg_in_entry_history(txn_id,batch_no,acno,"
                    + "inst_no,inst_amt,inst_dt,favor_of,bank_name,bank_add,remarks,reason_for_cancel,enter_by,"
                    + "auth_by,status,seq_no,orgn_branch,dest_branch,dt,tran_time,pay_by,iy,screen_flag,details,"
                    + "em_flag,vch_no,second_auth_by,auth,circle_type,substatus,img_code,custname,oper_mode,"
                    + "userdetails,prbankcode,rbirefno,schedule_no,fh_creation_date,fh_file_id,fh_settlement_date,"
                    + "item_payor_bank_routno,item_seq_no,item_trans_code,item_san,binary_data_file_name,"
                    + "binary_img_file_name,fh_vno,fh_test_file_indicator,item_prment_date,item_cycleno,"
                    + "addenda_bofdroutno,addenda_bofdbusdate,addenda_depositoracct,addenda_ifsc,modified_flag,doc_type) "
                    + "select txn_id,batch_no,acno,inst_no,inst_amt,inst_dt,favor_of,bank_name,bank_add,remarks,"
                    + "reason_for_cancel,enter_by,auth_by,status,seq_no,orgn_branch,dest_branch,dt,tran_time,pay_by,"
                    + "iy,screen_flag,details,em_flag,vch_no,second_auth_by,auth,circle_type,substatus,img_code,"
                    + "custname,oper_mode,userdetails,prbankcode,rbirefno,schedule_no,fh_creation_date,fh_file_id,"
                    + "fh_settlement_date,item_payor_bank_routno,item_seq_no,item_trans_code,item_san,"
                    + "binary_data_file_name,binary_img_file_name,fh_vno,fh_test_file_indicator,item_prment_date,"
                    + "item_cycleno,addenda_bofdroutno,addenda_bofdbusdate,addenda_depositoracct,addenda_ifsc,"
                    + "modified_flag,doc_type from cts_clg_in_entry where dt = DATE_FORMAT(now(),'%Y%m%d')");
            int insertCTSNo = insertCTS.executeUpdate();
            if (insertCTSNo > 0) {
                Query truncateCTS = em.createNativeQuery("delete from cts_clg_in_entry");
                truncateCTS.executeUpdate();
            }

            Query insertCTSTXT = em.createNativeQuery("INSERT INTO cts_upload_txt_cell_history (DT, PBANKCODE, DBANKCODE, CLGDT, AMOUNT, CHQNO, RBIREFNO, TC, OTHER, BASEACNO, CTS_BRANCHCODE, DETAILS, ENTERBY, BRANCHCODE ) SELECT DT, PBANKCODE, DBANKCODE, CLGDT, AMOUNT, CHQNO, RBIREFNO, TC, OTHER, BASEACNO, CTS_BRANCHCODE, DETAILS, ENTERBY, BRANCHCODE FROM cts_upload_txt_cell  WHERE DT = DATE_FORMAT(now(),'%Y%m%d')");
            int insertCTSTXTNo = insertCTSTXT.executeUpdate();
            if (insertCTSTXTNo > 0) {
                Query truncateCTSTXT = em.createNativeQuery("delete from cts_upload_txt_cell");
                truncateCTSTXT.executeUpdate();
                //em.createNativeQuery("DBCC CHECKIDENT (CTS_UPLOAD_TXT_CELL,RESEED, 0)").executeUpdate();
                em.createNativeQuery("ALTER TABLE cts_upload_txt_cell AUTO_INCREMENT = 0").executeUpdate();
            }

            Query insertCTSPXF = em.createNativeQuery("INSERT INTO cts_upload_pxf_cell_history(DT,ITEMSEQNO,BANKROUTNO,AMT,BASEACNO,SERIALNO,TRANSCODE,PROUTENO,PRDATE,CYCLENO,NOIMAGES,CLGTYPE,DOCTYPE,MICRREPFLAG,SPECIALHANDLING,TRUNCATINGRTNO,USERFIELD,IQA,CURRENCY,SESSION,REMARKS,ACNO,STATUS,DETAILS,ENTERBY,STATUS_RE,BRANCHCODE) SELECT DT,ITEMSEQNO,BANKROUTNO,AMT,BASEACNO,SERIALNO,TRANSCODE,PROUTENO,PRDATE,CYCLENO,NOIMAGES,CLGTYPE,DOCTYPE,MICRREPFLAG,SPECIALHANDLING,TRUNCATINGRTNO,USERFIELD,IQA,CURRENCY,SESSION,REMARKS,ACNO,STATUS,DETAILS,ENTERBY,STATUS_RE,BRANCHCODE FROM cts_upload_pxf_cell WHERE DT = DATE_FORMAT(now(),'%Y%m%d')");
            int insertCTSPXFNo = insertCTSPXF.executeUpdate();
            if (insertCTSPXFNo > 0) {
                Query truncateCTSPXF = em.createNativeQuery("delete from cts_upload_pxf_cell");
                truncateCTSPXF.executeUpdate();
                //em.createNativeQuery("DBCC CHECKIDENT (CTS_UPLOAD_PXF_CELL,RESEED, 0)").executeUpdate();
                em.createNativeQuery("ALTER TABLE cts_upload_pxf_cell AUTO_INCREMENT = 0").executeUpdate();
            }

            /*
             * Deletion of CTS Folder
             */
            List micrBranchCodeList = em.createNativeQuery("select distinct(branchcode) from bnkadd order by branchcode").getResultList();
            if (!micrBranchCodeList.isEmpty()) {
                // ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                // String directory = context.getInitParameter("cts");
                File dirCheck = new File(directory + "/");
                if (dirCheck.exists()) {
                    for (int i = 0; i < micrBranchCodeList.size(); i++) {
                        Vector element = (Vector) micrBranchCodeList.get(i);
                        String micrBranchCode = element.get(0).toString();

                        File zipFile = new File(directory + "/" + micrBranchCode + ".zip");
                        if (zipFile.exists()) {
                            zipFile.delete();
                        }

                        File brFolder = new File(directory + "/" + micrBranchCode);
                        if (brFolder.exists()) {
                            String[] list = brFolder.list();
                            for (int j = 0; j < list.length; j++) {
                                File entry = new File(directory + "/" + micrBranchCode + "/" + list[j]);
                                entry.delete();
                            }
                            brFolder.delete();
                        }
                    }
                }
            }
            //deletion of binaryImg cts images .
            File ctsArchievalDirectory = new File(ctsArchievalImgLocation);
            if (ctsArchievalDirectory.exists()) {
                File[] list = ctsArchievalDirectory.listFiles();
                for (int i = 0; i < list.length; i++) {
                    File file = new File(list[i].getAbsolutePath());
                    if (file.exists()) {
                        file.delete();
                    }

                }
            }

            //Ckycr Images Deletion
            ckycrCommonRemote.delete(new File(ckycrTempImageLocation + File.separator));

            errorFreeze(todaydate, CbsConstant.RECURRING_AC);

            errorFreeze(todaydate, CbsConstant.PAY_ORDER);

            errorFreeze(todaydate, CbsConstant.TERM_LOAN);

            errorFreeze(todaydate, CbsConstant.CURRENT_AC);

            errorFreeze(todaydate, CbsConstant.SAVING_AC);

            errorFreeze(todaydate, CbsConstant.DEPOSIT_SC);
//            insertIntoCheckBalTable();

            //Addition of minimum balance sms
//            List list = em.createNativeQuery("select ifnull(code,'') from parameterinfo_report "
//                    + "where reportname='MIN-BAL-SMS'").getResultList();
//            if (!list.isEmpty()) {
//                Vector ele = (Vector) list.get(0);
//                int code = Integer.parseInt(ele.get(0).toString());
//                if (code == 1) {
//                    minimumBalanceSms(todaydate, userName);
//                    System.out.println("Minimum balance executed successfully.");
//                }
//            }
            Integer code = ftsPost43Remote.getCodeForReportName("MIN-BAL-SMS");
            if (code == 1) {
                minimumBalanceSms(todaydate, userName);
                System.out.println("Minimum balance executed successfully.");
            }
            //Addition of Stop Payment Charges Request From Internet Banking.
            code = ftsPost43Remote.getCodeForReportName("IB-SP-CHARGE");
            if (code == 1) {
                processStopPaymentChargesForIB(todaydate);
                System.out.println("Stop payment charges for internet banking executed successfully.");
            }
            //End Here
            Query update = em.createNativeQuery("update cbs_bankdays set DayEndFlag='Y',EndUser='" + userName + "',EndDate=now() where DATE='" + todaydate + "'  ");
            int updt = update.executeUpdate();
            if (updt <= 0) {
                throw new ApplicationException("Problem in data updatation in CBS Bank Days.");
            }
            em.createNativeQuery("update reconvmast set recno=0").executeUpdate();
            em.createNativeQuery("update reconvmast_trans set trsno=0").executeUpdate();

            ut.commit();
            return "Central Day End Process successfully completed. ";//+ dbBackupMessage;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                ut.rollback();
                try {
                    ut.begin();
                    int m = em.createNativeQuery("update cbs_bankdays set dayendflag='N' where "
                            + "date='" + todaydate + "'").executeUpdate();
                    if (m <= 0) {
                        throw new ApplicationException("Problem In System Maintenance Updation.");
                    }
                    ut.commit();
                } catch (Exception ec) {
                    ut.rollback();
                    throw new ApplicationException(ec.getMessage());
                }
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String entryAsOnDateBalanceList(String brCode, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String query = CbsUtil.getBranchWiseQuery(brCode, date);
            List glResultList = em.createNativeQuery(query).getResultList();
            List existDataAsOnDateList = em.createNativeQuery("select * from ho_crr_asset_liab where date = '" + date + "'").getResultList();
            if (existDataAsOnDateList.size() > 0) {
                Query q1 = em.createNativeQuery("delete from  ho_crr_asset_liab where date = '" + date + "'");
                int int1 = q1.executeUpdate();
                if (int1 <= 0) {
                    ut.rollback();
                    return "False:problem in data deletion in ho_crr_asset_liab";
                }
            }
            for (int i = 0; i < glResultList.size(); i++) {
                Vector vect = (Vector) glResultList.get(i);
                Query q1 = em.createNativeQuery("insert into ho_crr_asset_liab (ACCODE,BAL,DATE,CLASSIFICATION) values ('" + vect.get(0).toString() + "'," + new BigDecimal(vect.get(1).toString()) + ",'" + date + "','" + (new BigDecimal(vect.get(1).toString()).compareTo(new BigDecimal("0")) < 0 ? "A" : "L") + "')");
                int int1 = q1.executeUpdate();
                if (int1 <= 0) {
                    ut.rollback();
                    return "False:problem in inserting data to ho_crr_asset_liab";
                }
            }
            BigDecimal totBal = new BigDecimal(0);
            List<NpaStatusReportPojo> npaList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", date, "", brCode.equalsIgnoreCase("90") ? "0A" : brCode, "Y");
            if (!npaList.isEmpty()) {
                for (int k = 0; k < npaList.size(); k++) {
                    NpaStatusReportPojo vect = npaList.get(k);
                    totBal = totBal.add(new BigDecimal(vect.getBalance().toString()));
                }
                Query q1 = em.createNativeQuery("insert into ho_crr_asset_liab (ACCODE,BAL,DATE,CLASSIFICATION) values ('NPA',cast(" + totBal + " as decimal(15,2)),'" + date + "','A')");
                int int1 = q1.executeUpdate();
                if (int1 <= 0) {
                    ut.rollback();
                    return "False:problem in inserting data to ho_crr_asset_liab";
                }
            }
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String entryBetweenDateBalanceList(String brCode, String fromdate, String Todate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date frdt = formatter.parse(fromdate);
            Date todt = formatter.parse(Todate);
            long dtDiffComp = CbsUtil.dayDiff(frdt, todt) + 1;
            for (int k = 0; k < dtDiffComp; k++) {
                String query = CbsUtil.getBranchWiseQuery(brCode, fromdate);
//                System.out.println(">>>>>>"+query);
                List glResultList = em.createNativeQuery(query).getResultList();
                List existDataAsOnDateList = em.createNativeQuery("select * from ho_crr_asset_liab where date = '" + fromdate + "' ").getResultList();
                if (existDataAsOnDateList.isEmpty()) {
                    for (int i = 0; i < glResultList.size(); i++) {
                        Vector vect = (Vector) glResultList.get(i);
                        Query q1 = em.createNativeQuery("insert into ho_crr_asset_liab (ACCODE,BAL,DATE,CLASSIFICATION) values ('" + vect.get(0).toString() + "'," + new BigDecimal(vect.get(1).toString()) + ",'" + fromdate + "','" + (new BigDecimal(vect.get(1).toString()).compareTo(new BigDecimal("0")) < 0 ? "A" : "L") + "')");
                        int int1 = q1.executeUpdate();
                        if (int1 <= 0) {
                            ut.rollback();
                            return "False:problem in inserting data to ho_crr_asset_liab";
                        }
                    }
                }
                fromdate = CbsUtil.dateAdd(fromdate, 1);
            }
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String cashInHandEntryBetweenDateBalanceList(String brCode, String fromdate, String Todate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
//            Date frdt = formatter.parse(fromdate);
//            Date todt = formatter.parse(Todate);
//            long dtDiffComp = CbsUtil.dayDiff(frdt, todt)+1;
            List brncode = em.createNativeQuery("select distinct brncode from cashinhand").getResultList();
            for (int j = 0; j < brncode.size(); j++) {
                Vector vect1 = (Vector) brncode.get(j);
                String brcode = vect1.get(0).toString();
                List ldate = em.createNativeQuery("select distinct ldate from cashinhand where ldate not in (select ldate from cashinhand where brncode ='" + brcode + "' and ldate between '" + fromdate + "' and '" + Todate + "' ) and ldate between '" + fromdate + "' and '" + Todate + "'").getResultList();
                for (int k = 0; k < ldate.size(); k++) {
                    Vector vect2 = (Vector) ldate.get(k);
                    Date ldat = (Date) vect2.get(0);
                    String lDate = ymd.format(ldat);
                    String query = "select '06200500',cast(sum(a.amt) as decimal(25,2)) from (select ifnull(sum(amt),0) as amt from cashinhand where ldate ='" + CbsUtil.dateAdd(lDate, -1) + "'   and brncode = '" + brcode + "' "
                            + "union all "
                            + "select sum(cramt-dramt) as amt from recon where dt = '" + ldat + "' and trantype =0  and auth ='Y' and substring(acno,1,2) = '" + brcode + "' "
                            + "union all "
                            + "select sum(cramt-dramt) as amt from ca_recon where dt = '" + ldat + "' and trantype =0  and auth ='Y' and substring(acno,1,2) ='" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from of_recon where dt = '" + ldat + "' and trantype =0  and auth ='Y' and substring(acno,1,2) ='" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from rdrecon where dt ='" + ldat + "'  and trantype =0  and auth ='Y' and substring(acno,1,2) ='" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from ddstransaction where dt = '" + ldat + "'  and trantype =0  and auth ='Y' and substring(acno,1,2) ='" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from td_recon where dt = '" + ldat + "' and trantype =0  and auth ='Y' and closeflag is null and substring(acno,1,2) = '" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from loan_recon where dt = '" + ldat + "' and trantype =0  and auth ='Y' and substring(acno,1,2) ='" + brcode + "'"
                            + "union all "
                            + "select sum(cramt-dramt) as amt from gl_recon where dt = '" + ldat + "'  and trantype =0  and auth ='Y' and substring(acno,1,2) = '" + brcode + "') a";
                    System.out.println(">>>>>>" + query);
                    List glResultList = em.createNativeQuery(query).getResultList();
                    for (int i = 0; i < glResultList.size(); i++) {
                        Vector vect = (Vector) glResultList.get(i);
                        Query q1 = em.createNativeQuery("insert into cashinhand(amt,ldate,brncode) values ('" + vect.get(1) + "','" + ldat + "' ,'" + brcode + "')");
                        int int1 = q1.executeUpdate();
                        if (int1 <= 0) {
                            ut.rollback();
                            return "False:problem in inserting data to cashinhand";
                        }
                    }
                }
            }
            ut.commit();
            return "True";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public void errorFreeze(String date, String acNature) throws ApplicationException {
        try {
            String reconTableName = commonRemote.getTableName(acNature);

            String balanceTableName = "reconbalan";
            if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                balanceTableName = "ca_reconbalan";
            }
            List list1 = em.createNativeQuery("SELECT MINDATE  FROM yearend WHERE MINDATE<=DATE_FORMAT(now(),'%Y%m%d') AND "
                    + "MAXDATE>=DATE_FORMAT(now(),'%Y%m%d')").getResultList();
            Vector v1 = (Vector) list1.get(0);
            String finMinDate = v1.get(0).toString();

            List accountList = em.createNativeQuery("select b.acno,round(b.bal,2)  from " + balanceTableName + " a,(select acno,ifnull((sum(cramt) - sum(dramt)),0) as bal"
                    + " from " + reconTableName + " group by acno) b where a.acno = b.acno and round(a.balance,2) != round(b.bal,2) order by acno").getResultList();
            if (!accountList.isEmpty()) {
                for (int j = 0; j < accountList.size(); j++) {
                    Vector acNoVect = (Vector) accountList.get(j);
                    String acNo = acNoVect.get(0).toString();
                    double balance = Double.parseDouble(acNoVect.get(1).toString());

                    if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) && Long.parseLong(acNo.substring(4, 10)) >= new Long(SiplConstant.GL_PL_ST.getValue().substring(0))
                            && Long.parseLong(acNo.substring(4, 10)) <= new Long(SiplConstant.GL_PL_END.getValue().substring(0))) {

                        List balanceList = em.createNativeQuery("SELECT round(ifnull(SUM(CRAMT)-SUM(DRAMT),0),2) FROM  gl_recon WHERE ACNO='" + acNo
                                + "' AND DT>=DATE_FORMAT('" + finMinDate + "','%Y-%m-%d') AND DT<= DATE_FORMAT('" + date + "','%Y-%m-%d') AND AUTH ='Y'").getResultList();
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balance = Double.parseDouble(balanceVect.get(0).toString());
                    }
                    double pendingBal = 0d;
                    List pendingBalList = em.createNativeQuery("SELECT IFNULL(SUM(TXNINSTAMT),0) FROM clg_ow_shadowbal WHERE ACNO = '" + acNo + "'").getResultList();
                    if (!pendingBalList.isEmpty()) {
                        Vector pendingBalVect = (Vector) pendingBalList.get(0);
                        pendingBal = Double.parseDouble(pendingBalVect.get(0).toString());
                    }

                    pendingBalList = em.createNativeQuery("SELECT IFNULL(SUM(TXNINSTAMT),0) FROM stud_clg_ow_shadowbal WHERE ACNO = '" + acNo + "'").getResultList();
                    if (!pendingBalList.isEmpty()) {
                        Vector pendingBalVect = (Vector) pendingBalList.get(0);
                        pendingBal = pendingBal + Double.parseDouble(pendingBalVect.get(0).toString());
                    }

                    List reconBalanceList = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM " + balanceTableName + " WHERE ACNO='" + acNo + "'").getResultList();
                    if (reconBalanceList.isEmpty()) {
                        insertIntoDebugTable(acNo, (balance - pendingBal), (balance - pendingBal));
                        Query insert3 = em.createNativeQuery("INSERT INTO " + balanceTableName + "(ACNO,DT,BALANCE) VALUES('" + acNo + "',now()," + (balance - pendingBal) + ")");
                        int insert3Result = insert3.executeUpdate();
                        if (insert3Result <= 0) {
                            throw new ApplicationException("An error occured, transaction cant not be completed at this time for " + acNature);
                        }
                    } else {
                        Vector reconBalVect = (Vector) reconBalanceList.get(0);
                        double reconBalance = Double.parseDouble(reconBalVect.get(0).toString());
                        if (balance - pendingBal - reconBalance != 0) {
                            insertIntoDebugTable(acNo, (balance - pendingBal), reconBalance);
                            Query update5 = em.createNativeQuery("UPDATE " + balanceTableName + " SET BALANCE=" + (balance - pendingBal) + " WHERE ACNO='" + acNo + "'");
                            int update5Result = update5.executeUpdate();
                            if (update5Result <= 0) {
                                throw new ApplicationException("An error occured, transaction cant not be completed at this time for " + acNature);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private void insertIntoDebugTable(String acNo, double actualBal, double reconBal) throws ApplicationException {
        try {
            Query insert2 = em.createNativeQuery("INSERT INTO debug_table(ACNO,DT,RECON_BAL,RECONBALAN_BAL) VALUES('" + acNo
                    + "',DATE_FORMAT(now(),'%Y%m%d')," + actualBal + "," + reconBal + ")");
            insert2.executeUpdate();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /* There Is No Calling Of This Method, So Commented in MySql Conversion */
//    public void insertIntoCheckBalTable() throws ApplicationException {
//        try {
//            Query delete1 = em.createNativeQuery("delete from checkbaltable");
//            delete1.executeUpdate();
//            int rs = em.createNativeQuery("insert into checkbaltable(acno,balance)(select acno,pwdencrypt(balance) from CA_reconbalan "
//                    + "union select acno,pwdencrypt(balance) from reconbalan "
//                    + "union select acno,pwdencrypt(balance) from td_reconbalan)").executeUpdate();
//            if (rs <= 0) {
//                throw new ApplicationException("Error in inserting data into CheckBalTable.");
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e.getMessage());
//        }
//    }

    /*          Code for central year end           */
    public String yearEndProcess() throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            List dataList1 = em.createNativeQuery("select BrnCode,BranchName from branchmaster").getResultList();
            if (!dataList1.isEmpty()) {
                for (int i = 0; i < dataList1.size(); i++) {
                    Vector ele1 = (Vector) dataList1.get(i);
                    String branch = ele1.get(0).toString();
                    String branchName = (String) ele1.get(1);
                    List dataList2 = em.createNativeQuery("select yearendflag from yearend where cast(brncode as unsigned) = '" + branch + "'").getResultList();
                    if (!dataList2.isEmpty()) {
                        Vector ele2 = (Vector) dataList2.get(0);
                        String yearFlag = (String) ele2.get(0);
                        if (yearFlag.equalsIgnoreCase("N")) {
                            return "Sorry! you can't perform central year end because "
                                    + branchName + " branch has not performed its year end process.";
                        }
                    }
                }
            }

            ut.begin();
            List dataList3 = em.createNativeQuery("select yearendflag from cbs_yearend where yearendflag = 'N'").getResultList();

            if (!dataList3.isEmpty()) {
                List dataList4 = em.createNativeQuery("select F_YEAR from cbs_yearend where mindate in (select min(mindate) from cbs_yearend where YearEndFlag='N') ").getResultList();
                Vector ele3 = (Vector) dataList4.get(0);

                int preYear = Integer.parseInt(ele3.get(0).toString());
                String minDate = preYear + 1 + "0401";
                String maxDate = preYear + 2 + "0331";

                int updateResult = em.createNativeQuery("update cbs_yearend set yearendflag = 'Y' where yearendflag = 'N'").executeUpdate();
                if (updateResult <= 0) {
                    throw new ApplicationException("Error occured during year end process.Please try again.");
                }

                int insertResult = em.createNativeQuery("insert into cbs_yearend values('" + minDate + "','" + maxDate + "','N'," + preYear + "+1)").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Error occured during year end process.Please try again.");
                }
            }

            /**
             * ******** TDS DOC UNMARKED PROCESS START *********
             */
            int updateTdResult = em.createNativeQuery("update td_accountmaster set tdsflag = 'Y' where accstatus <> 9 and tdsflag <>'Y'").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }

            int updateVouchResult = em.createNativeQuery("update td_vouchmst set tdsflag='Y' where status<>'C' and tdsflag <>'Y'").executeUpdate();
            if (updateVouchResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }
            updateTdResult = em.createNativeQuery("update accountmaster a,(select acctcode from accounttypemaster where acctnature "
                    + " in ('RD','DS')) b set a.tdsflag = 'Y' where a.accttype = b.acctcode and a.accstatus <>9 and "
                    + " a.tdsflag <>'Y'").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }

            updateTdResult = em.createNativeQuery("insert into tds_docdetail_his (customerid,acno,seqNo,submission_date,fyear,receiptNo,doc_details,docFlag,orgBrnid,"
                    + " tranTime,enterBy,auth,authBy,uniqueIdentificationNo,Tax_Option) select customerid,acno,seqNo,submission_date,fyear,receiptNo,doc_details,docFlag,orgBrnid,tranTime,enterBy,auth,"
                    + " authBy,uniqueIdentificationNo,Tax_Option from tds_docdetail").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }

            updateTdResult = em.createNativeQuery("truncate table tds_docdetail").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }

            updateTdResult = em.createNativeQuery("insert into tds_docdetail_header_his(custId, formNo15G_15H, aggregateAmt, otherIncome, deductionAmt, estimatedIncome, orgBrnid, uniqueIdentificationNo, submission_date, assessmentYear)"
                    + "SELECT custId, formNo15G_15H, aggregateAmt, otherIncome, deductionAmt, estimatedIncome, orgBrnid, uniqueIdentificationNo, submission_date, assessmentYear FROM tds_docdetail_header").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }
            updateTdResult = em.createNativeQuery("truncate table tds_docdetail_header").executeUpdate();
            if (updateTdResult < 0) {
                throw new ApplicationException("Error occured during year end process.Please try again.");
            }

            /**
             * ******** TDS DOC UNMARKED PROCESS START *********
             */
            ut.commit();
            return "TRUE";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public List getFinYear() throws ApplicationException {
        try {
            List list = new ArrayList();
            list = em.createNativeQuery("select f_year from cbs_yearend where yearendflag='N'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    /*          End of code for central year end           */
    public String createFile(String date, String brCode, String enterBy) throws ApplicationException {
        String message = "";
        NumberFormat formatter = new DecimalFormat("#0.00");
        try {
            String brnAlphaCode;
            String fileName;
            String tmpFileName;
            List resultList = new ArrayList();
            if (date == null || date.equalsIgnoreCase("") || date.length() == 0) {
                message = "Date Cannot Be Blank !!!";
                return message;
            }
            if (brCode == null || brCode.equalsIgnoreCase("") || brCode.length() == 0) {
                message = "Branch Code Cannot Be Blank !!!";
                return message;
            }
            List brnMasterList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE=CAST('" + brCode + "' AS unsigned)").getResultList();
            if (brnMasterList.isEmpty()) {
                message = "Alpha code cannot be blank !!!";
                return message;
            } else {
                Vector brnMasterListVec = (Vector) brnMasterList.get(0);
                brnAlphaCode = brnMasterListVec.get(0).toString();
            }
            String year = date.substring(0, 4);
            String tmpMonth = date.substring(4, 6);
            String day = date.substring(6);
            String month = "";
            if (tmpMonth.equalsIgnoreCase("01")) {
                month = "Jan";
            } else if (tmpMonth.equalsIgnoreCase("02")) {
                month = "Feb";
            } else if (tmpMonth.equalsIgnoreCase("03")) {
                month = "Mar";
            } else if (tmpMonth.equalsIgnoreCase("04")) {
                month = "Apr";
            } else if (tmpMonth.equalsIgnoreCase("05")) {
                month = "May";
            } else if (tmpMonth.equalsIgnoreCase("06")) {
                month = "Jun";
            } else if (tmpMonth.equalsIgnoreCase("07")) {
                month = "Jul";
            } else if (tmpMonth.equalsIgnoreCase("08")) {
                month = "Aug";
            } else if (tmpMonth.equalsIgnoreCase("09")) {
                month = "Sep";
            } else if (tmpMonth.equalsIgnoreCase("10")) {
                month = "Oct";
            } else if (tmpMonth.equalsIgnoreCase("11")) {
                month = "Nov";
            } else if (tmpMonth.equalsIgnoreCase("12")) {
                month = "Dec";
            } else {
                message = "Month name is not found !!!";
                return message;
            }
            String tmpDt = year + month + day;
            fileName = brnAlphaCode + "-G" + tmpDt + ".txt";
            resultList = em.createNativeQuery("SELECT ifnull(ACNAME,''),coalesce(AMOUNT,0),coalesce(GNO,0),ifnull(ACTYPE,''),ifnull(ACNO,''),ifnull(DT,'') FROM cbs_rep_ho_glb('" + date + "','" + brCode + "') where acname<>'SUB TOTAL' AND ACNO<>'' AND AMOUNT<>0 order by actype, gno, acname").getResultList();
            if (resultList.isEmpty()) {
                message = "Date does not exists !!!";
                return message;
            } else {
                String osName = System.getProperty("os.name");
                String fileSource = null;
                File srcFolder;
                if (osName.equalsIgnoreCase("Linux")) {
                    srcFolder = new File("/install/HOGLBFlatFile/");
                    if (!srcFolder.exists()) {
                        srcFolder.mkdir();
                    }
                    fileSource = "/install/HOGLBFlatFile/" + fileName;
                    tmpFileName = "install/HOGLBFlatFile/" + fileName;
                } else {
                    srcFolder = new File("C:/HOGLBFlatFile/");
                    if (!srcFolder.exists()) {
                        srcFolder.mkdir();
                    }
                    fileSource = "C:/HOGLBFlatFile/" + fileName;
                    tmpFileName = "C:/HOGLBFlatFile/" + fileName;
                }
                File f;
                f = new File(fileSource);
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileWriter fstream = new FileWriter(f);
                BufferedWriter out = new BufferedWriter(fstream);

                for (int i = 0; i < resultList.size(); i++) {
                    Vector v2 = (Vector) resultList.get(i);
                    String ty = "";
                    if (v2.get(3).toString().equalsIgnoreCase("A")) {
                        ty = "1";
                    } else if (v2.get(3).toString().equalsIgnoreCase("L")) {
                        ty = "0";
                    }
                    out.write(v2.get(0).toString() + "," + v2.get(1).toString() + "," + v2.get(2).toString() + "," + v2.get(3).toString() + "," + v2.get(4).toString() + "," + v2.get(5).toString() + "," + ty + "," + enterBy + ",");
                    out.newLine();
                }
                out.close();
                message = "Glb file is successfully generated. For downloading this file please click on following link.:- " + tmpFileName;
                return message;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String valAccDetail(String acNo) throws ApplicationException {
        try {
            String strAccNature = ftsPost43Remote.getAccountNature(acNo);
            if (strAccNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List accountResult = em.createNativeQuery("select a.ACNO,a.custname,r.balance from reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selectPassBook = em.createNativeQuery("SELECT ACNO FROM passbook_recon WHERE ACNO='" + acNo + "'").getResultList();
                    if (!selectPassBook.isEmpty()) {
                        return "True";
                    } else {
                        return "There is No Active PassBook For This Account";
                    }
                } else {
                    return "Account Does Not Exist";
                }
            }
            if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accountResult = em.createNativeQuery("select a.ACNO,a.custname,r.balance from ca_reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selectPassBook = em.createNativeQuery("SELECT ACNO FROM passbook_recon WHERE ACNO='" + acNo + "'").getResultList();
                    if (!selectPassBook.isEmpty()) {
                        return "True";
                    } else {
                        return "There is No Active PassBook For This Account";
                    }
                } else {
                    return "Account Does Not Exist";
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "True";
    }

    public List getAccDetail(String acNo) throws ApplicationException {
        List accountResult = null;
        try {
            String strAccNature = ftsPost43Remote.getAccountNature(acNo);
            if (strAccNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                accountResult = em.createNativeQuery("select a.ACNO,a.custname,ROUND(IFNULL(r.balance,0),2) from reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selectPassBook = em.createNativeQuery("SELECT ACNO FROM passbook_recon WHERE ACNO='" + acNo + "'").getResultList();
                    if (!selectPassBook.isEmpty()) {
                        return accountResult;
                    } else {
                        accountResult.add(0, "There is No Active PassBook For This Account");
                        return accountResult;
                    }
                } else {
                    accountResult.add(0, "Account Does Not Exist");
                    return accountResult;
                }
            }
            if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                accountResult = em.createNativeQuery("select a.ACNO,a.custname,ROUND(IFNULL(r.balance,0),2) from ca_reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selectPassBook = em.createNativeQuery("SELECT ACNO FROM passbook_recon WHERE ACNO='" + acNo + "'").getResultList();
                    if (!selectPassBook.isEmpty()) {
                        return accountResult;
                    } else {
                        accountResult.add("There is No Active PassBook For This Account");
                        return accountResult;
                    }
                } else {
                    accountResult.add("Account Does Not Exist");
                    return accountResult;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountResult;
    }

    public String canPassBook(String acNo, String Reason) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query DelQuery = em.createNativeQuery("DELETE FROM passbook_recon where acno='" + acNo + "'");
            Integer var = DelQuery.executeUpdate();
            if (var > 0) {
                Query updateQuery = em.createNativeQuery("UPDATE passbook_his set reasoncancel='" + Reason + "',status='C' where acno= '" + acNo + "'");
                Integer varUpd = updateQuery.executeUpdate();
                if (varUpd > 0) {
                    ut.commit();
                    return "True";
                } else {
                    ut.rollback();
                    return "Error In Cancellation";
                }
            } else {
                ut.rollback();
                return "Error In Cancellation";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String passBookIss(String acNo) throws ApplicationException {
        try {
            List passBookResult = em.createNativeQuery("select acno,dt,lnum from passbook_recon where acno='" + acNo + "'").getResultList();
            if (!passBookResult.isEmpty()) {
                return "True";
            } else {
                List chkAcForPrn = em.createNativeQuery("select acno from passbook_his where acno='" + acNo + "'AND STATUS='A'").getResultList();
                if (!chkAcForPrn.isEmpty()) {
                    return "True";
                } else {
                    return "Please Issue PassBook first";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String valAccFstPrnDtl(String acNo) throws ApplicationException {
        try {
            String strAccNature = ftsPost43Remote.getAccountNature(acNo);
            if (strAccNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List accountResult = em.createNativeQuery("select a.ACNO,a.custname,r.balance from reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selAccFstPrnStat = em.createNativeQuery("select * from accountmaster where accstatus = 9 and acno = '" + acNo + "'").getResultList();
                    if (!selAccFstPrnStat.isEmpty()) {
                        return "Account Has Been Closed";
                    } else {
                        return "True";
                    }
                } else {
                    return "Account Does Not Exist";
                }
            }
            if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List accountResult = em.createNativeQuery("select a.ACNO,a.custname,r.balance from ca_reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    List selAccFstPrnStat = em.createNativeQuery("select * from accountmaster where accstatus = 9 and acno = '" + acNo + "'").getResultList();
                    if (!selAccFstPrnStat.isEmpty()) {
                        return "Account Has Been Closed";
                    } else {
                        return "True";
                    }
                } else {
                    return "Account Does Not Exist";
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "True";
    }

    public List getAccFstPrnDtl(String acNo) throws ApplicationException {
        List accountResult = null;
        try {
            String strAccNature = ftsPost43Remote.getAccountNature(acNo);
            if (strAccNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || strAccNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || strAccNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                accountResult = em.createNativeQuery("select a.ACNO,a.custname,ROUND(IFNULL(r.balance,0),2) from reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    return accountResult;
                } else {
                    accountResult.add(0, "Account Does Not Exist");
                    return accountResult;
                }
            }
            if (strAccNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                accountResult = em.createNativeQuery("select a.ACNO,a.custname,ROUND(IFNULL(r.balance,0),2) from ca_reconbalan r,accountmaster a where a.acno= r.acno and a.ACNO='" + acNo + "'").getResultList();
                if (!accountResult.isEmpty()) {
                    return accountResult;
                } else {
                    accountResult.add("Account Does Not Exist");
                    return accountResult;
                }

            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return accountResult;
    }

    public List getIssuedDetail(String acNo) throws ApplicationException {
        try {
            List issPassBookResult = null;
            issPassBookResult = em.createNativeQuery("select acno,passbookno,issuedt,status from passbook_his where acno='" + acNo + "'").getResultList();
            return issPassBookResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPassBookValues() throws ApplicationException {
        try {
            List valPassBookResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='PASSBOOK'").getResultList();
            return valPassBookResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List setPassBookValues(String accNo) throws ApplicationException {
        try {
            String accountNat;
            accountNat = ftsPost43Remote.getAccountNature(accNo);
            if (accountNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List list1 = em.createNativeQuery("select * from passbook_values_ca").getResultList();
                return list1;
            } else {
                List list1 = em.createNativeQuery("select * from passbook_values").getResultList();
                return list1;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectParameterValue(String accNo) throws ApplicationException {
        try {
            String accountNat;
            accountNat = ftsPost43Remote.getAccountNature(accNo);
            if (accountNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List list1 = em.createNativeQuery("select parameter,value from passbook_parameters_ca").getResultList();
                return list1;
            } else {
                List list1 = em.createNativeQuery("select parameter,value from passbook_parameters").getResultList();
                return list1;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromPassbookRecon(String accNo) throws ApplicationException {
        try {
            List selectFromPassbkRecon = em.createNativeQuery("select recno,dt,ifnull(BAL,0)as bal,ifnull(LNUM,'')as lnum from passbook_recon where acno= '" + accNo + "' and recno=(select max(recno) from passbook_recon where acno='" + accNo + "')").getResultList();//two change in bal and lnum for isnull()
            return selectFromPassbkRecon;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List checkPrint2(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(lnum,'') as lnum from passbook_recon where acno='" + accNo + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String selectAcnoCramtDramtDt(String accNo, String tableName, String dateFrom, double recmax) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        dateFrom = dateFrom.substring(0, 4) + dateFrom.substring(5, 7) + dateFrom.substring(8, 10);
        try {
            ut.begin();
            List list1 = em.createNativeQuery("select acno,dramt,cramt,dt from recon_cash_d where  ( auth='N' or auth is null ) and acno='" + accNo + "' UNION select acno,dramt,cramt,dt from recon_trf_d where  ( auth='N' or auth is null ) and acno='" + accNo + "' UNION select acno,dramt,cramt,dt from recon_clg_d where  ( auth='N' or auth is null ) and acno='" + accNo + "'").getResultList();
            if (list1.isEmpty()) {
                Query insert1 = em.createNativeQuery("insert  into passbook_prt (acno,dt,trantime,dramt,cramt,details,instno,recno,trantype) select acno,dt,trantime,dramt,cramt,details,instno,recno,trantype from " + tableName + " where dt >'" + dateFrom + "' and acno= '" + accNo + "' and auth='Y'");
                insert1.executeUpdate();
            } else {
                Query insert3 = em.createNativeQuery("insert into passbook_prt (acno,dt,trantime,dramt,cramt,details,instno,recno,trantype) (select acno,dt,trantime,dramt,cramt,details,instno,recno,trantype from " + tableName + " where dt >'" + dateFrom + "' and acno= '" + accNo + "' and auth='Y')");
                insert3.executeUpdate();
            }
            Query insert2 = em.createNativeQuery("insert  into passbook_prt (acno,dt,trantime,dramt,cramt,details,instno,recno,trantype) (select acno,dt,trantime,dramt,cramt,details,instno,recno,trantype from " + tableName + " where recno >" + recmax + " and dt='" + dateFrom + "' and acno= '" + accNo + "' and auth='Y')");//dt='" + dateFrom + "'
            insert2.executeUpdate();
            List list2 = em.createNativeQuery("select count(*) from passbook_prt").getResultList();
            Vector v2 = (Vector) list2.get(0);
            ut.commit();
            return result = v2.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return result; commented by sudhir
    }

    public List selectFromPassbookPrt(int prow, String accNo) throws ApplicationException {
        try {
            if (prow > 0) {
                List select2 = em.createNativeQuery("select sno,dt,ifnull(round(dramt,2),0),ifnull(round(cramt,2),0),ifnull(round(bal,2),0),substring(details,1,50),ifnull(instno,'')as instno,date_format(trantime,'%b %d %Y %T:%f'),recno,trantype from passbook_prt p where acno='" + accNo + "' order by dt,recno limit " + prow + "").getResultList();
                return select2;
            } else {
                List select1 = em.createNativeQuery("select sno,dt,ifnull(round(dramt,2),0),ifnull(round(cramt,2),0),ifnull(round(bal,2),0),substring(details,1,50),ifnull(instno,'')as instno,date_format(trantime,'%b %d %Y %T:%f'),recno,trantype from passbook_prt p where acno='" + accNo + "' order by dt,recno").getResultList();
                return select1;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromPassbookValuesCA(String accNo) throws ApplicationException {
        try {
            String accountNat;
            accountNat = ftsPost43Remote.getAccountNature(accNo);
            if (accountNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List select1 = em.createNativeQuery("select name,ord,align,width from passbook_values_ca order by ord").getResultList();
                return select1;
            } else {
                List select2 = em.createNativeQuery("select name,ord,align,width from passbook_values order by ord").getResultList();
                return select2;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromPassbookValues(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select name,ord,align,width from passbook_values order by ord").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromPassbookHis(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(max(passbookno),'') from passbook_his where acno = '" + accNo + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromPassRecon(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acno,dt,lnum,recno,bal from passbook_recon where acno='" + accNo + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromCustmasterAccMasterParameterInfo(String accNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select substring(c.custname,1,40),c.actype,c.craddress,a.acno,substring(d.description,1,15),"
                    + "p.address,substring(a.jtname1,1,20),e.description,ifnull(c.fathername,''),ifnull(a.nomination ,''),"
                    + "ifnull(a.openingdt ,''),c.phoneno,ci.custid,a.jtname2,a.jtname3,a.jtname4,LPAD(ifnull(cast(b.micr as char),'0'),3,'0'),"
                    + " LPAD(ifnull(cast(b.micrcode as char),'0'),3,'0'),LPAD(ifnull(cast(b.branchcode as char),'0'),3,'0'),a.intdeposit,"
                    + " timestampdiff(MONTH,a.openingdt,ifnull(a.rdmatdate,'19000101')),a.rdinstal,date_format(ifnull(a.rdmatdate,'19000101'),'%d/%m/%Y'),p.brphone "
                    + " from customermaster c,accountmaster a,parameterinfo p , codebook d, codebook e, customerid ci,bnkadd b, branchmaster br where "
                    + " c.actype='"
                    + ftsPost43Remote.getAccountCode(accNo) + "' and c.custno='" + accNo.substring(4, 10) + "' and c.custno=substring(a.acno,5,6) "
                    + "and c.brncode = '" + ftsPost43Remote.getCurrentBrnCode(accNo) + "'and a.acno = '" + accNo + "' and p.brncode = "
                    + "cast(c.brncode as unsigned) and d.code =c.occupation and d.groupcode=6 and a.opermode = e.code and e.groupcode=4 and a.acno = ci.acno and br.brncode = cast(c.brncode as unsigned) and br.alphacode = b.alphacode").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectDescription6(String occupation) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(description,'') from codebook where groupcode=6 and code='" + occupation + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectDescription4(String OPERMODE) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(substring(description,1,15),'') from codebook where groupcode=4 and code ='" + OPERMODE + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List selectFromTable(String accNo, String issueDate, String tableName) throws ApplicationException {
        try {
            List select = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)),0) from " + tableName + " where acno='" + accNo + "' and dt < '" + issueDate + "'").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getMaxTxnDate(String accNo) {
        List dateList = new ArrayList();
        try {
            dateList = em.createNativeQuery("select ifnull(DATE_FORMAT(max(dt),'%Y%m%d'),'') from passbook_prt where acno = '" + accNo + "'").getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateList;
    }

    public List getRemainingDataToPrint(String accNo) {
        List dateList = new ArrayList();
        try {
            dateList = em.createNativeQuery("select * from passbook_prt where acno = '" + accNo + "'").getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dateList;
    }

    public String insertIntoPassbookRecon(String accNo, String bal, String lnum, String recNo, String issueDate, String passBookNo, String sNo, String Tempbd) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();

        try {
            ut.begin();
            Query insert1 = em.createNativeQuery("Insert into passbook_recon (acno,bal,lnum,RECNUM,dt,recno) values ('" + accNo + "','" + bal + "','" + lnum + "','" + recNo + "','" + issueDate + "',0)");// Add recno as 0 by Nishant on Date 03/04/2011
            int insertResult1 = insert1.executeUpdate();
            if (insertResult1 > 0) {
                Query insert2 = em.createNativeQuery("Insert into passbook_his (passbookno,srno,acno,issuedt,status,issueflag) values ('" + passBookNo + "'," + (sNo + 1) + ",'" + accNo + "','" + Tempbd + "','A','Y')");
                int insertResult2 = insert2.executeUpdate();
                if (insertResult2 > 0) {
                    ut.commit();
                    return "Passbook Issued";
                } else {
                    ut.rollback();
                    return "Error in insertion in Passbook_His";
                }
            } else {
                ut.rollback();
                return "Error in insertion in Passbook_Recon";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getDetailsOnLoad1() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select * from passbook_values ").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDetailsOnLoad2() throws ApplicationException {
        try {
            List list = em.createNativeQuery("select parameter,value from passbook_parameters ").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String updatePassbookRecon(String accNo) throws ApplicationException {

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query update = em.createNativeQuery("update passbook_recon set lnum=1 where acno= '" + accNo + "'");
            int updateResult = update.executeUpdate();
            if (updateResult > 0) {
                ut.commit();
                return "Success";
            } else {
                ut.rollback();
                return "Failure";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public String deleteFromPassbookPrint(String accNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            Query delete = em.createNativeQuery("delete from passbook_prt where acno='" + accNo + "'");
            int deleteResult = delete.executeUpdate();
            if (deleteResult > 0) {
                ut.commit();
                return result = "Success in deletion.";
            } else {
                ut.rollback();
                return result = "Error in deletion.";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return result; commeneted by sudhir 
    }

    public List selectForPassUpdate(int prow, String accNo) throws ApplicationException {
        try {
            List select = em.createNativeQuery("select dt,ifnull(recno,0)as recno from passbook_prt p where acno='" + accNo + "' order by dt,recno limit " + prow + "").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteInsertPassbookrecon(String tableName, String accNo, double maxrecno, String maxdt1) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            Query delete = em.createNativeQuery("delete from passbook_recon where acno= '" + accNo + "'");
            int deleteResult = delete.executeUpdate();
            if (deleteResult <= 0) {
                ut.rollback();
                return result = "False1";
            }
            Query insert = em.createNativeQuery("insert  into passbook_recon (acno,dt,trantime,dramt,cramt,details,instno,recno)(select acno,dt,trantime,dramt,cramt,details,instno,recno from " + tableName + " where acno='" + accNo + "' and auth='Y' and recno=" + maxrecno + " and dt=DATE_FORMAT('" + maxdt1 + "','%Y%m%d')) ");
            int insertResult = insert.executeUpdate();
            if (insertResult <= 0) {
                ut.rollback();
                return result = "False2";
            }
            ut.commit();
            return result = "True";
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return result; commented by sudhir
    }

    public String updatepassbookRecon2(String balance, long totalPrint, String accNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            Query update = em.createNativeQuery("update passbook_recon set bal='" + balance + "',lnum=" + totalPrint + " where acno= '" + accNo + "'");
            int updateResult = update.executeUpdate();
            if (updateResult <= 0) {
                ut.rollback();
                return result = "Error in Updation Passbook_Recon";
            } else {
                ut.commit();
                return result = "Success";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return result; commenetd by sudhir 
    }

    public List selectWidthPassbookValues(String accNo) throws ApplicationException {
        try {
            String accountNat;
            accountNat = ftsPost43Remote.getAccountNature(accNo);
            if (accountNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List select1 = em.createNativeQuery("select sum(align) + sum(width) from passbook_values_ca").getResultList();
                return select1;
            } else {
                List select2 = em.createNativeQuery("select sum(align) + sum(width) from passbook_values").getResultList();
                return select2;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String getIfscCodeByBrnCode(int brncode) throws ApplicationException {
        try {
            List ifscList = em.createNativeQuery("SELECT IFSC_CODE FROM branchmaster WHERE BRNCODE=" + brncode + "").getResultList();
            if (!ifscList.isEmpty()) {
                Vector element = (Vector) ifscList.get(0);
                if (element.get(0) == null || element.get(0).toString().equalsIgnoreCase("")) {
                    return "";
                } else {
                    return element.get(0).toString();
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getAcctDescByAcctCode(String acctcode) throws ApplicationException {
        String acctDesc = "";
        try {
            List acDescList = em.createNativeQuery("select coalesce(acctdesc,'') from accounttypemaster where acctcode='" + acctcode + "'").getResultList();
            if (!acDescList.isEmpty()) {
                Vector element = (Vector) acDescList.get(0);
                acctDesc = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return acctDesc;
    }

    public String getoldAccountNumber(String newAcno) throws ApplicationException {
        String oldAccountNo = "";
        try {
            List oldAcnoList = em.createNativeQuery("select old_ac_no from cbs_acno_mapping where new_ac_no='" + newAcno + "'").getResultList();
            if (!oldAcnoList.isEmpty()) {
                Vector element = (Vector) oldAcnoList.get(0);
                oldAccountNo = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return oldAccountNo;
    }

    public String getPassCPABalValues() throws ApplicationException {
        String valCPA = "1";
        try {
            List valPassCPABalResult = em.createNativeQuery("select ifnull(code,'1')as code from parameterinfo_report where UPPER(reportname)='PASS_CPA_BAL'").getResultList();
            if (!valPassCPABalResult.isEmpty()) {
                Vector element = (Vector) valPassCPABalResult.get(0);
                valCPA = element.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return valCPA;
    }

    public String insertFortnightBal(String enterBy, String dt, String toDt, String brnCode) throws ApplicationException {
        String result = "true";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List valExist = em.createNativeQuery("select * from fortnight_balance where date_format(date,'%Y%m%d') between '" + dt + "' and '" + toDt + "'").getResultList();
            if (!valExist.isEmpty()) {
                ut.rollback();
                return result = "false";
            }
            List valDt = em.createNativeQuery("select date_format(REPFRIDATE,'%Y%m%d') from ho_reportingfriday where repfridate between '" + dt + "' and '" + toDt + "'").getResultList();
            if (!valDt.isEmpty()) {
                for (int z = 0; z < valDt.size(); z++) {
                    Vector vect = (Vector) valDt.get(z);
                    dt = vect.get(0).toString();
                    List valRes = em.createNativeQuery("select acctcode,acctnature from accounttypemaster where acctnature not in ('PO')").getResultList();
                    if (!valRes.isEmpty()) {
                        for (int i = 0; i < valRes.size(); i++) {
                            Vector ele1 = (Vector) valRes.get(i);
                            String acctCode = ele1.get(0).toString();
                            String acNat = ele1.get(1).toString();

                            String tableName = CbsUtil.getReconTableName(acNat);

                            if (acNat.equalsIgnoreCase("CA")) {
                                Integer insert = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'A','" + enterBy + "',now() "
                                        + " from " + tableName + " r, accountmaster a where substring(r.acno,3,2) = '" + acctCode + "' and r.acno in (select acno from " + tableName + " "
                                        + " WHERE DT <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                                        + " and r.acno = a.acno AND r.DT <= '" + dt + "' and r.auth = 'Y'").executeUpdate();
                                if (insert <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for CA Nature";
                                }

                                Integer insert1 = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'L','" + enterBy + "',now() "
                                        + " from " + tableName + " r, accountmaster a where substring(r.acno,3,2) = '" + acctCode + "' and r.acno in (select acno from " + tableName + " "
                                        + " WHERE DT <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                                        + " and r.acno = a.acno AND r.DT <= '" + dt + "' and r.auth = 'Y'").executeUpdate();
                                if (insert1 <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for CA Nature";
                                }
                            }

                            if ((acNat.equalsIgnoreCase("FD")) || (acNat.equalsIgnoreCase("MS"))) {
                                Integer insert2 = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'L','" + enterBy + "',now() from " + tableName + " "
                                        + " where substring(acno,3,2) = '" + acctCode + "' and DT <= '" + dt + "' and auth = 'Y' and CLOSEFLAG IS NULL").executeUpdate();
                                if (insert2 <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for '" + acNat + "' Nature";
                                }
                            }

                            if ((acNat.equalsIgnoreCase("SB")) || (acNat.equalsIgnoreCase("DS")) || (acNat.equalsIgnoreCase("RD"))) {
                                Integer insert3 = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'L','" + enterBy + "',now() from " + tableName + " r, "
                                        + " accountmaster a where substring(r.acno,3,2) = '" + acctCode + "' and r.acno = a.acno AND r.DT <= '" + dt + "' and r.auth = 'Y'").executeUpdate();
                                if (insert3 <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for '" + acNat + "' Nature";
                                }
                            }

                            if ((acNat.equalsIgnoreCase("DL")) || (acNat.equalsIgnoreCase("TL"))) {
                                Integer insert4 = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'A','" + enterBy + "',now() "
                                        + " from " + tableName + " r, accountmaster a where substring(r.acno,3,2) = '" + acctCode + "' and r.acno in (select acno from " + tableName + " "
                                        + " WHERE DT <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = -1) "
                                        + " and r.acno = a.acno AND r.DT <= '" + dt + "' and r.auth = 'Y'").executeUpdate();
                                if (insert4 <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for '" + acNat + "' Nature";
                                }

                                Integer insert5 = em.createNativeQuery("insert into fortnight_balance select '" + acctCode + "','" + dt + "',ifnull(sum(ifnull(cramt,0)),0)- ifnull(sum(ifnull(dramt,0)),0),'L','" + enterBy + "',now() "
                                        + " from " + tableName + " r, accountmaster a where substring(r.acno,3,2) = '" + acctCode + "' and r.acno in (select acno from " + tableName + " "
                                        + " WHERE DT <= '" + dt + "' group by acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1) "
                                        + " and r.acno = a.acno AND r.DT <= '" + dt + "' and r.auth = 'Y'").executeUpdate();
                                if (insert5 <= 0) {
                                    return result = "Data is not inserted into fortnight_balance for '" + acNat + "' Nature";
                                }
                            }
                        }

                        List valGl = em.createNativeQuery("SELECT DISTINCT substring(ACNO,3,8) FROM gltable order by substring(ACNO,3,8)").getResultList();
                        if (!valGl.isEmpty()) {
                            for (int j = 0; j < valGl.size(); j++) {
                                Vector ele2 = (Vector) valGl.get(j);
                                String glCode = ele2.get(0).toString();

                                double bal = 0d;

                                List sumGl = em.createNativeQuery("SELECT CAST(ifnull(SUM(CRAMT-DRAMT),0) AS decimal(24,2)) FROM gl_recon WHERE SUBSTRING(ACNO,3,8) = '" + glCode + "' and dt <= '" + dt + "' and auth ='Y'").getResultList();
                                if (!sumGl.isEmpty()) {
                                    Vector ele3 = (Vector) sumGl.get(0);
                                    bal = Double.parseDouble(ele3.get(0).toString());
                                }

                                if (bal > 0) {
                                    Integer insert6 = em.createNativeQuery("insert into fortnight_balance values ('" + glCode + "',date_format('" + dt + "','%Y%m%d')," + bal + ",'L','" + enterBy + "',now())").executeUpdate();
                                    if (insert6 <= 0) {
                                        return result = "Data is not inserted into fortnight_balance for GL Nature";
                                    }
                                } else {
                                    Integer insert7 = em.createNativeQuery("insert into fortnight_balance values ('" + glCode + "',date_format('" + dt + "','%Y%m%d')," + bal + ",'A','" + enterBy + "',now())").executeUpdate();
                                    if (insert7 <= 0) {
                                        return result = "Data is not inserted into fortnight_balance for GL Nature";
                                    }
                                }
                            }
                        }
                    }
                }
                ut.commit();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String insertFortnightBalAsPerGLB(String enterBy, String dt, String toDt, String brnCode) throws ApplicationException {
        String result = "true";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List valExist = em.createNativeQuery("select * from fortnight_balance where date_format(date,'%Y%m%d') between '" + dt + "' and '" + toDt + "'").getResultList();
            if (!valExist.isEmpty()) {
                ut.rollback();
                return result = "false";
            }
            List valDt = em.createNativeQuery("select date_format(REPFRIDATE,'%Y%m%d') from ho_reportingfriday where repfridate between '" + dt + "' and '" + toDt + "'").getResultList();
            if (!valDt.isEmpty()) {
                for (int z = 0; z < valDt.size(); z++) {
                    System.out.println("Start Time " + new Date());
                    Vector vect = (Vector) valDt.get(z);
                    dt = vect.get(0).toString();
                    String query = CbsUtil.getBranchWiseQuery(brnCode, dt);
                    List glResultList = em.createNativeQuery(query).getResultList();
                    for (int i = 0; i < glResultList.size(); i++) {
                        Vector vect1 = (Vector) glResultList.get(i);
                        Query q1 = em.createNativeQuery("insert into fortnight_balance (ACCODE,date,bal,classification,enterby,userdate) values ('" + vect1.get(0).toString() + "','" + dt + "', " + new BigDecimal(vect1.get(1).toString()) + ",'" + (new BigDecimal(vect1.get(1).toString()).compareTo(new BigDecimal("0")) < 0 ? "A" : "L") + "' ,'" + enterBy + "',now() )");
                        int int1 = q1.executeUpdate();
                        if (int1 <= 0) {
                            ut.rollback();
                            return "False:problem in inserting data to fortnight_balance";
                        }
                    }
                    BigDecimal totBal = new BigDecimal(0);
                    List<NpaStatusReportPojo> npaList = npaRemote.getNpaStatusReportDataOptimized("0", "ALL", "19000101", dt, "", brnCode.equalsIgnoreCase("90") ? "0A" : brnCode, "Y");
                    if (!npaList.isEmpty()) {
                        for (int k = 0; k < npaList.size(); k++) {
                            NpaStatusReportPojo vect1 = npaList.get(k);
                            totBal = totBal.add(new BigDecimal(vect1.getBalance().toString()));
                        }
                        Query q1 = em.createNativeQuery("insert into fortnight_balance (ACCODE,date,bal,classification,enterby,userdate) values ('NPA','" + dt + "',cast(" + totBal + " as decimal(15,2)),'A','" + enterBy + "',now() )");
                        int int1 = q1.executeUpdate();
                        if (int1 <= 0) {
                            ut.rollback();
                            return "False:problem in inserting data to fortnight_balance";
                        }
                    }
                    System.out.println("loop END Time " + new Date());
                }
                ut.commit();
                System.out.println("END Time " + new Date());
                return "true";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    private String minimumBalanceSms(String todaydate, String userName) throws ApplicationException {
        List<TransferSmsRequestTo> smsList = new ArrayList<TransferSmsRequestTo>();
        try {
            //Remove those a/c which have balance now greater than min balance from cbs_min_bal_acno_detail.
            List list = em.createNativeQuery("select mi.acno,ifnull(am.chequebook,0) as chqallow,cast(ifnull(re.balance,0) "
                    + "as decimal(25,2)) as balance,ifnull(att.minbal,0) as minbal,ifnull(att.minbal_chq,0) as minbal_chq "
                    + "from cbs_min_bal_acno_detail mi,accountmaster am,reconbalan re,accounttypemaster att where "
                    + "mi.acno=am.acno and mi.acno=re.acno and am.accstatus=1 and substring(mi.acno,3,2)=am.accttype "
                    + "and substring(mi.acno,3,2)=att.acctcode "
                    + "union all "
                    + "select mi.acno,ifnull(am.chequebook,0) as chqallow,cast(ifnull(re.balance,0) as decimal(25,2)) "
                    + "as balance,ifnull(att.minbal,0) as minbal,ifnull(att.minbal_chq,0) as minbal_chq from "
                    + "cbs_min_bal_acno_detail mi,accountmaster am,ca_reconbalan re,accounttypemaster att where "
                    + "mi.acno=am.acno and mi.acno=re.acno and am.accstatus=1 and substring(mi.acno,3,2)=am.accttype "
                    + "and substring(mi.acno,3,2)=att.acctcode").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                String acno = ele.get(0).toString();
                int chqAllow = Integer.parseInt(ele.get(1).toString());
                BigDecimal bal = new BigDecimal(ele.get(2).toString());
                BigDecimal minBal = new BigDecimal(ele.get(3).toString());
                BigDecimal minBalChq = new BigDecimal(ele.get(4).toString());
                //Balance checking
                if ((chqAllow == 1 && bal.compareTo(minBalChq) >= 0)
                        || (chqAllow != 1 && bal.compareTo(minBal) >= 0)) {
                    int n = em.createNativeQuery("INSERT INTO cbs_min_bal_acno_detail_his(acno,min_bal,"
                            + "min_bal_dt,enter_by,enter_time,update_by,update_time) select acno,min_bal,"
                            + "min_bal_dt,enter_by,enter_time,'" + userName + "',now() from cbs_min_bal_acno_detail "
                            + "where acno='" + acno + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in minimum balance sms history insertion. A/c is::" + acno);
                    }

                    n = em.createNativeQuery("delete from cbs_min_bal_acno_detail where acno='" + acno + "'").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in minimum balance sms deletion. A/c is::" + acno);
                    }
                }
            }
            //Now a/c which are in minimum balance on current date.
            list = em.createNativeQuery("select a.ano,a.chqallow,a.minbal,a.minbal_chq,a.balance "
                    + "from (select am.acno as ano,ifnull(am.chequebook,0) as chqallow,ifnull(att.minbal,0) "
                    + "as minbal,ifnull(att.minbal_chq,0) as minbal_chq,cast(ifnull(r.balance,0) as decimal(25,2)) "
                    + "as balance,case ifnull(am.chequebook,0) when 1 then cast(ifnull(r.balance,0) as decimal(25,2)) "
                    + "< ifnull(att.minbal_chq,0) else cast(ifnull(r.balance,0) as decimal(25,2)) < ifnull(att.minbal,0) "
                    + "end as flag from accountmaster am,accounttypemaster att,reconbalan r where am.acno=r.acno and "
                    + "am.accstatus=1 and am.accttype=att.acctcode and am.accttype in(select acctcode from accounttypemaster "
                    + "where acctnature='SB')) a where a.flag=1 and a.ano not in(select acno from cbs_min_bal_acno_detail)"
                    + "union all "
                    + "select a.ano,a.chqallow,a.minbal,a.minbal_chq,a.balance from (select am.acno as ano,"
                    + "ifnull(am.chequebook,0) as chqallow,ifnull(att.minbal,0) as minbal,ifnull(att.minbal_chq,0) as "
                    + "minbal_chq,cast(ifnull(r.balance,0) as decimal(25,2)) as balance,case ifnull(am.chequebook,0) "
                    + "when 1 then cast(ifnull(r.balance,0) as decimal(25,2)) < ifnull(att.minbal_chq,0) else "
                    + "cast(ifnull(r.balance,0) as decimal(25,2)) < ifnull(att.minbal,0) end as flag from "
                    + "accountmaster am,accounttypemaster att,ca_reconbalan r where am.acno=r.acno and am.accstatus=1 and "
                    + "am.accttype=att.acctcode and am.accttype in(select acctcode from accounttypemaster where "
                    + "accttype='CA')) a where a.flag=1 and a.ano not in(select acno from cbs_min_bal_acno_detail)").getResultList();
            if (!list.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    String acno = ele.get(0).toString();
                    BigDecimal bal = new BigDecimal(ele.get(4).toString());

                    //Extracting Mobile No
                    String mobileNo = "";
                    List smsData = em.createNativeQuery("select mobile_no from mb_subscriber_tab where acno='" + acno + "' and "
                            + "status=1 and auth='Y' and auth_status='V'").getResultList();
                    if (!smsData.isEmpty()) {
                        Vector smsEle = (Vector) smsData.get(0);
                        mobileNo = smsEle.get(0).toString().trim();
                    } else {
                        smsData = em.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                                + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                                + "and id.acno='" + acno + "'").getResultList();
                        Vector smsEle = (Vector) smsData.get(0);
                        mobileNo = "+91" + smsEle.get(0).toString().trim();
                    }
                    mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();

                    int n = em.createNativeQuery("INSERT INTO cbs_min_bal_acno_detail(acno,min_bal,min_bal_dt,"
                            + "enter_by,enter_time) VALUES('" + acno + "'," + bal + ",'" + todaydate + "',"
                            + "'" + userName + "',now())").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in minimum balance a/c detail insertion. A/c is::" + acno);
                    }

                    n = em.createNativeQuery("INSERT INTO cbs_min_bal_sms_detail(acno,dt,mobile,enter_by,"
                            + "enter_time) VALUES('" + acno + "','" + todaydate + "',"
                            + "'" + mobileNo + "','" + userName + "',now())").executeUpdate();
                    if (n <= 0) {
                        throw new ApplicationException("Problem in minimum balance a/c sms detail insertion. A/c is::" + acno);
                    }
                    if (mobileNo.length() == 13) {
                        TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                        trfSmsTo.setAcno(acno);
                        trfSmsTo.setPromoMobile(mobileNo);
                        trfSmsTo.setBankName(templateBankName);
                        smsList.add(trfSmsTo);
                    }
                }
            }
            //Sms Sending
            try {
                smsRemote.sendMinimumBalanceSms(smsList);
            } catch (Exception ex) {
                System.out.println("Problem in sending the minimum balance sms to a/c:" + ex.getMessage());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    private String processStopPaymentChargesForIB(String todaydate) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code from cbs_parameterinfo where name='IB-STOP-PAY-CHG'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define applied account type for stop payment charges in internet banking.");
            }
            Vector ele = (Vector) list.get(0);
            String[] splittedValue = ele.get(0).toString().trim().split(",");
            String appliedAccountType = "";
            for (int i = 0; i < splittedValue.length; i++) {
                if (appliedAccountType.equals("")) {
                    appliedAccountType = splittedValue[i];
                } else {
                    appliedAccountType = appliedAccountType + "," + splittedValue[i];
                }
            }

            int taxCode = ftsPost43Remote.getCodeForReportName("STAXMODULE_ACTIVE");
            //Fetching all a/c to process the stop payment charges in internet banking.
            List dataList = em.createNativeQuery("select p.request_no,p.acno,p.invt_class,p.invt_type, "
                    + " ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState from "
                    + " ib_request p, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                    + " where p.request_type like'%STOP PAYMENT%' and p.request_status='APPROVED' "
                    + " and p.charge_status='N' and p.cbs_request_dt='" + todaydate + "' "
                    + " and p.Acno = ci.Acno and ci.CustId = cast(cm.customerid as unsigned) "
                    + " and br.BrnCode=cast(substring(p.Acno,1,2) as unsigned) ").getResultList();
            if (!dataList.isEmpty()) {
                Map<String, Double> map = new HashMap<String, Double>();
                String mainDetails = null;
                for (int i = 0; i < dataList.size(); i++) {
                    Vector element = (Vector) dataList.get(i);
                    long requestNo = Long.parseLong(element.get(0).toString().trim());
                    String acno = element.get(1).toString().trim();
                    long chqFrom = Long.parseLong(element.get(2).toString().trim());
                    long chqTo = Long.parseLong(element.get(3).toString().trim());
                    String custState = element.get(4).toString();
                    String branchState = element.get(5).toString();
                    //Fetching a/c details
                    CustomerIdPojo pojo = ibswsRemote.getDetailOfAccountNumber(acno);
                    if (appliedAccountType.trim().toUpperCase().contains(pojo.getAccountType().trim().toUpperCase())) {
                        //Fetching charge gl head details
                        list = cmrFacade.chkGLHead(pojo.getAcType());
                        if (list.isEmpty()) {
                            throw new ApplicationException("Please define head for stop payment charges.");
                        }
                        ele = (Vector) list.get(0);
                        double charges = Double.parseDouble(ele.get(0) == null ? "0" : ele.get(0).toString().trim());
                        String glHead = ele.get(1).toString().trim();
                        double charges1 = Double.parseDouble(ele.get(2).toString().trim());

                        String tmpDetails = "";
                        long noOfLeaves = 0;
                        if (chqFrom == chqTo) {
                            tmpDetails = "IBS stop payment charge for cheque no: " + String.valueOf(chqFrom);
                            noOfLeaves = 1;
                        } else {
                            tmpDetails = "IBS stop payment charge for cheque no: " + String.valueOf(chqFrom)
                                    + " To " + String.valueOf(chqTo);
                            noOfLeaves = (chqTo - chqFrom) + 1;
                        }

                        list = em.createNativeQuery("select odlimit,adhoclimit,coalesce(adhoctilldt,'') "
                                + "as adhoctilldt from accountmaster where acno = '" + acno + "'").getResultList();
                        if (list.isEmpty()) {
                            throw new ApplicationException("Account does not exist:" + acno);
                        }
                        ele = (Vector) list.get(0);
                        double odLimit = Double.parseDouble(ele.get(0).toString().trim());
                        double adhocLimit = Double.parseDouble(ele.get(1).toString().trim());
                        String adhocTillDt = ele.get(2).toString().trim().equals("")
                                ? CbsUtil.dateAdd(todaydate, -2) : ele.get(2).toString().trim();
                        long dayDiff = CbsUtil.dayDiff(ymd.parse(adhocTillDt), ymd.parse(todaydate));

                        double balance = 0;
                        if (dayDiff > 1) {
                            balance = odLimit;
                        } else {
                            balance = odLimit + adhocLimit;
                        }

                        double acBal = Double.parseDouble(pojo.getBalance());
                        double totBal = balance + acBal;
                        double commAmt = 0;

                        if (noOfLeaves > 1) {
                            int cOpt = ftsPost43Remote.getCodeForReportName("STOP_CHG_FLAT");
                            if (cOpt == 1) {
                                commAmt = charges1;
                            } else {
                                commAmt = noOfLeaves * charges;
                            }
                        } else {
                            commAmt = charges;
                        }

                        String sTaxModActive = "";
                        double sTaxAmt = 0;
                        if (taxCode == 1) {
//                        sTaxModActive = "Y";
//                        sTaxAmt = cmrFacade.findTax(commAmt, todaydate);
//                        totAmt = sTaxAmt + commAmt;
                            if (custState.equalsIgnoreCase(branchState)) {
                                map = interBranchFacade.getTaxComponent(commAmt, todaydate);
                                Set<Map.Entry<String, Double>> set = map.entrySet();
                                Iterator<Map.Entry<String, Double>> it = set.iterator();
                                while (it.hasNext()) {
                                    Map.Entry entry = it.next();
                                    sTaxAmt = sTaxAmt + Double.parseDouble(entry.getValue().toString());
                                }
                            } else {
                                map = interBranchFacade.getIgstTaxComponent(commAmt, todaydate);
                                Set<Map.Entry<String, Double>> set = map.entrySet();
                                Iterator<Map.Entry<String, Double>> it = set.iterator();
                                while (it.hasNext()) {
                                    Map.Entry entry = it.next();
                                    sTaxAmt = sTaxAmt + Double.parseDouble(entry.getValue().toString());
                                }
                            }
                        }
//                    else {
//                        sTaxModActive = "N";
//                        totAmt = commAmt;
//                    }

                        if ((commAmt + sTaxAmt) > 0) {
                            if (totBal > (commAmt + sTaxAmt)) {
                                float trsNo = ftsPost43Remote.getTrsNo();
                                String ftsMsg = ftsPost43Remote.ftsPosting43CBS(acno, 2, 1, commAmt, todaydate, todaydate,
                                        "System", pojo.getBrCode(), pojo.getBrCode(), 8, tmpDetails, trsNo,
                                        ftsPost43Remote.getRecNo(), 0, "", "Y", "System", "", 3, "", "", "", 0f,
                                        "", "", "", "", 0f, "N", "", "", "");
                                if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException("Error In Transaction " + ftsMsg);
                                }

                                ftsMsg = ftsPost43Remote.ftsPosting43CBS(pojo.getBrCode() + glHead + "01", 2, 0, commAmt,
                                        todaydate, todaydate, "System", pojo.getBrCode(), pojo.getBrCode(), 8, tmpDetails,
                                        trsNo, ftsPost43Remote.getRecNo(), 0, "", "Y", "System", "", 3, "", "", "", 0f, "",
                                        "", "", "", 0f, "N", "", "", "");
                                if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException(ftsMsg);
                                }

//                            glHead = pojo.getBrCode() + glHead + "01";            
//                            if (map.containsKey(glHead)) { //Present
//                                map.put(glHead, (commAmt+map.get(glHead)));
//                            } else { //Not Present
//                                map.put(glHead, commAmt);
//                            }
                                if ((taxCode == 1) && sTaxAmt > 0) {

                                    ftsMsg = ftsPost43Remote.ftsPosting43CBS(acno, 2, 1, sTaxAmt, todaydate, todaydate,
                                            "System", pojo.getBrCode(), pojo.getBrCode(), 8, tmpDetails, trsNo,
                                            ftsPost43Remote.getRecNo(), 0, "", "Y", "System", "", 3, "", "", "",
                                            0f, "", "", "", "", 0f, "N", "", "", "");
                                    if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                        throw new ApplicationException(ftsMsg);
                                    }

                                    Set<Map.Entry<String, Double>> set = map.entrySet();
                                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                                    while (it.hasNext()) {
                                        Map.Entry entry = it.next();
                                        String[] keyArray = entry.getKey().toString().split(":");
                                        String description = keyArray[0];
                                        String taxHead = pojo.getBrCode() + keyArray[1];
                                        mainDetails = description.toUpperCase() + " for IBS stop payment charge for. " + acno;
                                        double taxAmount = Double.parseDouble(entry.getValue().toString());

                                        ftsMsg = ftsPost43Remote.ftsPosting43CBS(taxHead, 2, 0,
                                                taxAmount, todaydate, todaydate, "System", pojo.getBrCode(), pojo.getBrCode(),
                                                8, mainDetails, trsNo, ftsPost43Remote.getRecNo(), 0, "", "Y", "System",
                                                "", 3, "", "", "", 0f, "", "", "", "", 0f, "N", "", "", "");
                                        if (!ftsMsg.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                            throw new ApplicationException(ftsMsg);
                                        }
//                                tmpDetails = "Stax:" + tmpDetails;
//
//                                List<String> taxDetail = ftsPost43Remote.getTaxDetail(todaydate);
//                                if (taxDetail.isEmpty()) {
//                                    throw new ApplicationException("Please define proper tax master.");
//                                }
//                                String glAcno = taxDetail.get(3);
                                    }
                                }
                            } else {
                                int n = em.createNativeQuery("insert into pendingcharges(acno,ty,dt,Amount,trantype,"
                                        + "details,instno,recno,enterby,trsno,charges,trandesc) values('" + acno + "',"
                                        + "1,'" + todaydate + "'," + (commAmt) + ",2, 'Insufficient Balance','',"
                                        + "'" + ftsPost43Remote.getRecNo() + "','System'," + ftsPost43Remote.getTrsNo() + ","
                                        + "'Stop Payment Charges',24)").executeUpdate();
                                if (n <= 0) {
                                    throw new ApplicationException("Data insertion problem in pendingcharges");
                                }
                            }
                        }
                        //Charge status updation
                        int n = em.createNativeQuery("update ib_request set charge_status='Y' where "
                                + "request_no=" + requestNo + "").executeUpdate();
                        if (n <= 0) {
                            throw new ApplicationException("Data updation problem in ib_request table for A/c:" + acno);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    private void sendLoanEmiDueSMS(String curDt) throws ApplicationException {
        try {
            int emiDueDays = ftsPost43Remote.getCodeForReportName("EMI-DUE-DAYS");
            if (emiDueDays == 0) {
                emiDueDays = 5;
            }
            String lastDayEndDt = "", fromDt = "", toDt = "";
            List list = em.createNativeQuery("select max(date_format(date,'%Y%m%d')) from "
                    + "cbs_bankdays where daybeginflag='Y' and dayendflag='Y' and date<'" + curDt + "'").getResultList();
            if (list.isEmpty()) {
                fromDt = CbsUtil.dateAdd(curDt, emiDueDays);
                toDt = fromDt;
            } else {
                Vector ele = (Vector) list.get(0);
                lastDayEndDt = ele.get(0).toString();

                long dayDiff = CbsUtil.dayDiff(ymd.parse(lastDayEndDt), ymd.parse(curDt));
                if (dayDiff == 1) {
                    fromDt = CbsUtil.dateAdd(curDt, emiDueDays);
                    toDt = fromDt;
                } else if (dayDiff > 1) {
                    fromDt = CbsUtil.dateAdd(lastDayEndDt, 1 + emiDueDays);
                    toDt = CbsUtil.dateAdd(curDt, emiDueDays);
                }
            }
            System.out.println("From Date-->" + fromDt + "::To Date-->" + toDt);

            List emiDueList = em.createNativeQuery("select e.acno,date_format(e.duedt, '%d/%m/%Y'),e.installamt,"
                    + "concat('+91',ltrim(rtrim(c.mobilenumber))) as mobile from emidetails e,"
                    + "cbs_customer_master_detail c,customerid i, accountmaster a where e.status='UNPAID' and "
                    + "e.duedt between '" + fromDt + "' and '" + toDt + "' and (e.acno <> '' or "
                    + "e.acno is not null) and c.customerid=i.custid and i.acno=e.acno and "
                    + "length(ltrim(rtrim(c.mobilenumber)))=10 and a.acno = e.acno and a.accstatus <> 9 group by e.acno").getResultList();
            if (!emiDueList.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                for (int i = 0; i < emiDueList.size(); i++) {
                    Vector emiDueVec = (Vector) emiDueList.get(i);

                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                    tSmsRequestTo.setMsgType("PAT");
                    tSmsRequestTo.setTemplate(SmsType.LOAN_EMI_DUE);
                    tSmsRequestTo.setAcno(emiDueVec.get(0).toString());
                    tSmsRequestTo.setAmount(Double.parseDouble(emiDueVec.get(2).toString()));
                    tSmsRequestTo.setDate(emiDueVec.get(1).toString());
                    tSmsRequestTo.setPromoMobile(emiDueVec.get(3).toString());
                    tSmsRequestTo.setBankName(templateBankName);
                    tSmsRequestTo.setModuleName("NO-CHG");
                    smsRemote.sendSms(tSmsRequestTo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private void sendKycExpAlertSms(String todayDt) throws ApplicationException {
        try {
            Integer codeSms = ftsPost43Remote.getCodeForReportName("KYC-EXP-ALERT-DAYS");
            String date = CbsUtil.dateAdd(todayDt, codeSms);
//         String days= date.substring(6);
//         String month = date.substring(4,6);
            List list = messageDetailBeanRemote.sendKycExpAlertSms(date);
            if (!list.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    String firstName = ele.get(3).toString().trim();
                    String middleName = ele.get(4).toString().trim();
                    String lastName = ele.get(5).toString().trim();
                    String custName = firstName.trim() + " " + middleName.trim();
                    custName = custName.trim() + " " + lastName.trim();
                    String cusUpdationDate = ele.get(6).toString().trim();

                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                    tSmsRequestTo.setMsgType("PAT");
                    tSmsRequestTo.setTemplate(SmsType.KYC_EXP_ALERT);
                    tSmsRequestTo.setAcno(ele.get(1).toString().trim());
                    tSmsRequestTo.setPromoMobile(ele.get(2).toString().trim());
                    tSmsRequestTo.setBankName(templateBankName);
                    tSmsRequestTo.setPromoMsg(dmy.format(ymmd.parse(cusUpdationDate)));
                    tSmsRequestTo.setModuleName("NO-CHG");
                    smsRemote.sendSms(tSmsRequestTo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    private void sendBirthDaySms(String todayDt) throws ApplicationException {
        try {
            List list = messageDetailBeanRemote.sendBirthDaySms(todayDt.substring(6), todayDt.substring(4, 6));
            if (!list.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    String firstName = ele.get(3).toString().trim();
                    String middleName = ele.get(4).toString().trim();
                    String lastName = ele.get(5).toString().trim();
                    String custName = firstName.trim() + " " + middleName.trim();
                    custName = custName.trim() + " " + lastName.trim();

                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                    tSmsRequestTo.setMsgType("PAT");
                    tSmsRequestTo.setTemplate(SmsType.BIRTH_DAY_SMS);
                    tSmsRequestTo.setAcno(ele.get(1).toString().trim());
                    tSmsRequestTo.setPromoMobile(ele.get(2).toString().trim());
                    tSmsRequestTo.setBankName(templateBankName);
                    tSmsRequestTo.setPromoMsg(custName);
                    tSmsRequestTo.setModuleName("NO-CHG");
                    smsRemote.sendSms(tSmsRequestTo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    //It will modify after discussion- We will take minimum date
    private void sendLoanEmiOverDueSMS(String curDt) throws ApplicationException {
        try {
            int emiOverDueFrequency = ftsPost43Remote.getCodeForReportName("LOAN-EMI-OVER-DUE-DAY");
            if (emiOverDueFrequency == 0) {
                emiOverDueFrequency = 7;
            }
            List emiDueList = em.createNativeQuery("select e.acno,date_format(min(e.duedt), '%d/%m/%Y') as duedt,"
                    + "e.installamt,concat('+91',ltrim(rtrim(c.mobilenumber))) as mobile from emidetails e,"
                    + "cbs_customer_master_detail c,customerid i where e.status='UNPAID' and "
                    + "e.duedt < '" + curDt + "' and (e.acno <> '' or e.acno is not null) and c.customerid=i.custid and "
                    + "i.acno=e.acno and length(ltrim(rtrim(c.mobilenumber)))=10 group by e.acno").getResultList();
            if (!emiDueList.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                for (int i = 0; i < emiDueList.size(); i++) {
                    Vector emiDueVec = (Vector) emiDueList.get(i);
                    String dueDt = ymd.format(dmy.parse(emiDueVec.get(1).toString()));
                    String firstSmsSendDt = CbsUtil.dateAdd(dueDt, 1); //First sms sending date
                    long diff = CbsUtil.dayDiff(ymd.parse(firstSmsSendDt), ymd.parse(curDt));
                    if (diff % emiOverDueFrequency == 0) {
                        TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                        tSmsRequestTo.setMsgType("PAT");
                        tSmsRequestTo.setTemplate(SmsType.LOAN_EMI_OVERDUE);

                        tSmsRequestTo.setAcno(emiDueVec.get(0).toString());
                        tSmsRequestTo.setDate(dmy.format(ymd.parse(firstSmsSendDt)));
                        tSmsRequestTo.setPromoMobile(emiDueVec.get(3).toString());
                        tSmsRequestTo.setBankName(templateBankName);

                        tSmsRequestTo.setModuleName("NO-CHG");
                        smsRemote.sendSms(tSmsRequestTo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private void sendFdAutoRenewalSMS(String curDt) throws ApplicationException {
        try {
            List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

            List fdAutoList = em.createNativeQuery("select t.acno,t.voucherno,t.Prinamt,date_format(t.fddt,'%d/%m/%Y'),t.roi,t.years,t.months,t.days "
                    + " from td_vouchmst t, (select Acno as ano,voucherno as vno from td_vouchmst where cldt = '" + curDt + "') "
                    + " a where t.acno = a.ano and t.PrevVoucherNo = a.vno").getResultList();
            List<TransferSmsRequestTo> smsSenderList = new ArrayList<TransferSmsRequestTo>();

            for (int i = 0; i < fdAutoList.size(); i++) {
                Vector fdAutoVec = (Vector) fdAutoList.get(i);
                String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(fdAutoVec.get(0).toString());

                if (mobileNo.length() == 10) {
                    //Sending Sms
                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();

                    String periodNew = "";
                    if (!fdAutoVec.get(5).toString().equalsIgnoreCase("0")) {
                        periodNew = fdAutoVec.get(5).toString() + "Years ";
                    }
                    if (!fdAutoVec.get(6).toString().equalsIgnoreCase("0")) {
                        periodNew = periodNew + fdAutoVec.get(6).toString() + "Months ";
                    }
                    if (!fdAutoVec.get(7).toString().equalsIgnoreCase("0")) {
                        periodNew = periodNew + fdAutoVec.get(7).toString() + "Days ";
                    }

                    tSmsRequestTo.setMsgType("PAT");
                    tSmsRequestTo.setTemplate(SmsType.TD_AUTO_RENEWAL);
                    tSmsRequestTo.setAcno(fdAutoVec.get(0).toString());
                    tSmsRequestTo.setPromoMsg(ftsPost43Remote.getCustName(fdAutoVec.get(0).toString()));
                    tSmsRequestTo.setAmount(Double.parseDouble(fdAutoVec.get(2).toString()));
                    tSmsRequestTo.setDate(fdAutoVec.get(3).toString());
                    tSmsRequestTo.setFirstCheque(periodNew);
                    tSmsRequestTo.setLastCheque(fdAutoVec.get(4).toString());
                    tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                    tSmsRequestTo.setBankName(templateBankName);

                    smsSenderList.add(tSmsRequestTo);
                }
            }
            smsRemote.trfSmsRequestToBatch(smsSenderList);
        } catch (Exception e) {
            System.out.println("Problem in sending AUTO RENEWED FD SMS");
        }
    }

    private void sendFdAutoPaymentSMS(String curDt) throws ApplicationException {
        try {
            List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

            List fdAutoList = em.createNativeQuery("select ta.acno,ta.PrinAmt,date_format(ta.MatDt,'%d/%m/%Y'),ta.voucherno,tv.auto_paid_acno "
                    + "from td_payment_auth ta,td_vouchmst tv where ta.TranTime >='" + curDt + "' and ta.acno = tv.acno "
                    + "and ta.voucherno = tv.voucherno").getResultList();
            List<TransferSmsRequestTo> smsSenderList = new ArrayList<TransferSmsRequestTo>();

            for (int i = 0; i < fdAutoList.size(); i++) {
                Vector fdAutoVec = (Vector) fdAutoList.get(i);
                //Sending Sms
                String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(fdAutoVec.get(0).toString());
                if (mobileNo.length() == 10) {
                    TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();

                    tSmsRequestTo.setMsgType("PAT");
                    tSmsRequestTo.setTemplate(SmsType.TD_AUTO_PAYMENT);
                    tSmsRequestTo.setAcno(fdAutoVec.get(0).toString());
                    tSmsRequestTo.setPromoMsg(ftsPost43Remote.getCustName(fdAutoVec.get(0).toString()));
                    tSmsRequestTo.setAmount(Double.parseDouble(fdAutoVec.get(1).toString()));
                    tSmsRequestTo.setDate(fdAutoVec.get(2).toString());
                    tSmsRequestTo.setFirstCheque(fdAutoVec.get(4).toString());
                    tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                    tSmsRequestTo.setBankName(templateBankName);

                    smsSenderList.add(tSmsRequestTo);
                }
            }
            smsRemote.trfSmsRequestToBatch(smsSenderList);
        } catch (Exception e) {
            System.out.println("Problem in sending AUTO RENEWED FD SMS");
        }
    }

    private void sendFDRenewalNoticeSMS(String curDt) throws ApplicationException {
        try {
            int tdRenewalCode = ftsPost43Remote.getCodeForReportName("TD-RENEWAL");
            int renewalDueDays = ftsPost43Remote.getCodeForReportName("RENEWAL-DUE-DAYS");
            if (renewalDueDays == 0) {
                renewalDueDays = 1;
            }
            String lastDayEndDt = "", fromDt = "", toDt = "";
            List list = em.createNativeQuery("select max(date_format(date,'%Y%m%d')) from "
                    + "cbs_bankdays where daybeginflag='Y' and dayendflag='Y' and date<'" + curDt + "'").getResultList();
            if (list.isEmpty()) {
                fromDt = CbsUtil.dateAdd(curDt, renewalDueDays);
                toDt = fromDt;
            } else {
                Vector ele = (Vector) list.get(0);
                lastDayEndDt = ele.get(0).toString();

                long dayDiff = CbsUtil.dayDiff(ymd.parse(lastDayEndDt), ymd.parse(curDt));
                if (dayDiff == 1) {
                    fromDt = CbsUtil.dateAdd(curDt, renewalDueDays);
                    toDt = fromDt;
                } else if (dayDiff > 1) {
                    fromDt = CbsUtil.dateAdd(lastDayEndDt, 1 + renewalDueDays);
                    toDt = CbsUtil.dateAdd(curDt, renewalDueDays);
                }
            }
            System.out.println("From Date-->" + fromDt + "::To Date-->" + toDt);

            List renewalDueList = em.createNativeQuery("select acno,prinamt,date_format(matdt,'%d/%m/%Y') from td_vouchmst where matdt "
                    + " between '" + fromDt + "' and '" + toDt + "' and status ='A' order by acno").getResultList();
            if (!renewalDueList.isEmpty()) {
                List<MbSmsSenderBankDetailTO> bankTo = smsRemote.getBankAndSenderDetail();
                String templateBankName = bankTo.get(0).getTemplateBankName().trim();

                for (int i = 0; i < renewalDueList.size(); i++) {
                    Vector renewDueVec = (Vector) renewalDueList.get(i);
                    String mobileNo = messageDetailBeanRemote.getMobileNumberByAcno(renewDueVec.get(0).toString());
                    if (mobileNo.length() == 10) {
                        TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                        tSmsRequestTo.setMsgType("PAT");
                        if (tdRenewalCode == 1) {
                            tSmsRequestTo.setTemplate(SmsType.TD_AUTO_RENEWAL_NOTICE);
                        } else {
                            tSmsRequestTo.setTemplate(SmsType.TD_RENEWAL_NOTICE);
                        }

                        tSmsRequestTo.setPromoMsg(ftsPost43Remote.getCustName(renewDueVec.get(0).toString()));
                        tSmsRequestTo.setAcno(renewDueVec.get(0).toString());
                        tSmsRequestTo.setAmount(Double.parseDouble(renewDueVec.get(1).toString()));

                        tSmsRequestTo.setDate(renewDueVec.get(2).toString());
                        tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                        tSmsRequestTo.setBankName(templateBankName);
                        smsRemote.sendSms(tSmsRequestTo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<SmsToBatchTo> siAutoCoverPost(String dt) throws ApplicationException {

        List<SmsToBatchTo> smsBatchSiAutoList;
        List<SmsToBatchTo> smsBatchSiPendingList;
        List<SmsToBatchTo> smsBatchSiList = new ArrayList<SmsToBatchTo>();
        try {
            int thLmtParam = 0;
            List chParamThresh = ftsPost43Remote.getThreshLmtParam();
            if (!(chParamThresh == null || chParamThresh.isEmpty())) {
                Vector verLst = (Vector) chParamThresh.get(0);
                thLmtParam = Integer.parseInt(verLst.get(0).toString());
            }
            List brnList = em.createNativeQuery("select bd.brncode from bankdays bd, branchmaster bm where Date='" + dt + "' and DayBeginFlag <> 'H' "
                    + "and cast(bd.brncode as unsigned) = bm.brncode").getResultList();
            if (brnList.isEmpty()) {
                throw new ApplicationException("Branch code is not defined");
            }
            for (int a = 0; a < brnList.size(); a++) {
                Vector brnListVector = (Vector) brnList.get(a);
                String brncode = brnListVector.get(0).toString();

                smsBatchSiAutoList = siAutoPost("SYSTEM", brncode, dt, thLmtParam);
                smsBatchSiPendingList = siAutoPendingPost("SYSTEM", brncode, dt, thLmtParam);

                smsBatchSiList.addAll(smsBatchSiAutoList);
                smsBatchSiList.addAll(smsBatchSiPendingList);

            }
            return smsBatchSiList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<SmsToBatchTo> siAutoPendingPost(String user, String brCode, String bankDt, int thLmtParam) throws ApplicationException {
        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        UserTransaction ut = context.getUserTransaction();
        try {
            int intBankDt = Integer.parseInt(bankDt);
            List select = em.createNativeQuery("select instrno,sno,fromacno,toacno,amount,effdate,remarks,expirydt from "
                    + "standins_transpending st,accountmaster ac where st.fromacno=ac.acno and ac.curbrcode='" + brCode
                    + "' and processdate <= '" + bankDt + "' order by instrno,sno").getResultList();
            for (int i = 0; i < select.size(); i++) {
                Vector ele = (Vector) select.get(i);

                float instrNo = Float.parseFloat(ele.get(0).toString());
                int sNo = Integer.parseInt(ele.get(1).toString());
                String fromAcNo = ele.get(2).toString();
                String toAcno = ele.get(3).toString();

                double amount = Double.parseDouble(ele.get(4).toString());
                String effDt = ele.get(5).toString();
                String effDate = effDt.substring(0, 4) + effDt.substring(5, 7) + effDt.substring(8, 10);

                String remarks = ele.get(6).toString();
                String expiryDate = ele.get(7).toString();
                String expiryDt = expiryDate.substring(0, 4) + expiryDate.substring(5, 7) + expiryDate.substring(8, 10);
                int expDt = Integer.parseInt(expiryDt);
                try {
                    ut.begin();
                    if (expDt >= intBankDt) {
                        String fromAccNature = ftsPost43Remote.getAccountNature(fromAcNo);
                        String fromBrCode = ftsPost43Remote.getCurrentBrnCode(fromAcNo);

                        String toAcctNature = ftsPost43Remote.getAccountNature(toAcno);
                        String toBrCode = ftsPost43Remote.getCurrentBrnCode(toAcno);

                        List accTypeFrom = getAccountDetails(fromAccNature, fromAcNo);
                        if (accTypeFrom.isEmpty()) {
                            throw new ApplicationException("Data does not exist for " + fromAcNo);
                        }
                        String stnDetails = "";
                        Vector accAllValues = (Vector) accTypeFrom.get(0);
                        int fromAccSts = Integer.parseInt(accAllValues.get(1).toString());

                        String balanceValue = accAllValues.get(2).toString();
                        String limitValue = accAllValues.get(3).toString();

                        if (fromAccSts == 9) {
                            stnDetails = "Account Has been Closed";
                        }
                        if (fromAccSts == 10) {
                            stnDetails = "Lien Marked";
                        }
                        if (fromAccSts == 8) {
                            stnDetails = " Operation Stopped";
                        }
                        if (fromAccSts == 7) {
                            stnDetails = " Withdrawal Stopped";
                        }
                        double fromBalance = Double.parseDouble(balanceValue);
                        double fromOdlimit = Double.parseDouble(limitValue);

                        List accTypeTo = getAccountDetails(toAcctNature, toAcno);
                        if (accTypeFrom.isEmpty()) {
                            throw new ApplicationException("Data does not exist for " + toAcno);
                        }
                        Vector accToValues = (Vector) accTypeTo.get(0);
                        int toAccSts = Integer.parseInt(accToValues.get(1).toString());

                        if ((fromAccSts == 9) && (toAccSts == 9)) {
                            throw new ApplicationException("Both A/C s  -> " + fromAcNo + " And " + toAcno + " Are Closed ");
                        }
                        if ((fromAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 8) || (fromAccSts == 7)) {
                            throw new ApplicationException("Ac No :-> " + fromAcNo + " " + stnDetails);
                        }
                        if (toAccSts == 9) {
                            throw new ApplicationException("Ac No :-> " + toAcno + " is closed");
                        }
//                        if ((fromBalance + fromOdlimit) <= amount) {
//                            throw new ApplicationException("Insufficient Fund In : " + fromAcNo);
//                        }
                        /**
                         * * Code Added For ThreshHold Limit Checking *
                         */
                        String chkThresh = "";
                        if ((toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && thLmtParam == 1) {
                            chkThresh = ftsPost43Remote.isThreshLmtExceed(toAcno, amount, ymmd.format(ymd.parse(bankDt)));
                            if (!chkThresh.equalsIgnoreCase("true")) {
                                throw new ApplicationException(chkThresh);
                            }
                        }
                        float trsNumber = ftsPost43Remote.getTrsNo();
                        float recNo = 0;
                        float tokenNoDr = 0;

                        List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
                        Vector element = (Vector) list.get(0);
                        String fromDetail = "SI trf to " + toAcno + " at branch " + element.get(0).toString();
                        String toDetail = "SI trf from " + fromAcNo + " at branch " + element.get(0).toString();

                        int crTranDesc = 0;
                        if ((toAcctNature.equals(CbsConstant.RECURRING_AC)) || (toAcctNature.equals(CbsConstant.DEMAND_LOAN))
                                || (toAcctNature.equals(CbsConstant.TERM_LOAN))) {
                            crTranDesc = 1;
                        }
                        String result = "";
                        if (brCode.equals(toBrCode)) {
                            result = ftsPost43Remote.ftsPosting43CBS(fromAcNo, 2, 1, amount, bankDt, bankDt, user, fromBrCode, toBrCode, 0, fromDetail,
                                    trsNumber, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr, "N", "", "", "S");

                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                            result = ftsPost43Remote.ftsPosting43CBS(toAcno, 2, 0, amount, bankDt, bankDt, user, fromBrCode, toBrCode, crTranDesc,
                                    toDetail, trsNumber, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null,
                                    tokenNoDr, "N", "", "", "S");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                            result = ftsPost43Remote.npaRecoveryUpdation(trsNumber, toAcctNature, toAcno, bankDt, amount, fromBrCode, toBrCode, user);
                            if (!result.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                        } else {
                            result = interBranchFacade.cbsPostingCx(fromAcNo, 1, bankDt, amount, 0f, 2, fromDetail, 0f, "20", "", null, 3, null, recNo, 0, fromBrCode, brCode, user, "SYSTEM", trsNumber, "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                            result = interBranchFacade.cbsPostingSx(toAcno, 0, bankDt, amount, 0f, 2, toDetail, 0f, "20", "", null, 3, null, recNo, crTranDesc, toBrCode, brCode, user, "SYSTEM", trsNumber, "", "");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                            result = updateBalances(fromAccNature, fromAcNo, amount, trsNumber, brCode);
                            if (!result.equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result.substring(4));
                            }
                        }

                        Query insertQueryp = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,Effdate,"
                                + "processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcNo + "','" + toAcno + "','" + amount + "','"
                                + remarks + "','" + effDate + "','" + bankDt + "','" + user + "')");
                        int rs = insertQueryp.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("Problem in data insertion in the standins_transexecuted");
                        }
                        Query deleteQuery1 = em.createNativeQuery("Delete from standins_transpending where FromAcno='" + fromAcNo + "' and InstrNo='"
                                + instrNo + "' and Sno='" + sNo + "'");
                        rs = deleteQuery1.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("Problem in data deletion from the standins_transpending");
                        }

                        ftsPost43Remote.lastTxnDateUpdation(toAcno);
                        ftsPost43Remote.lastTxnDateUpdation(fromAcNo);

                        //Sms Object Creation--Dr
                        SmsToBatchTo to = new SmsToBatchTo();
                        to.setAcNo(fromAcNo);
                        to.setDrAmt(amount);
                        to.setCrAmt(0d);
                        to.setTranType(2);
                        to.setTy(1);
                        to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                        to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);

                        smsBatchList.add(to);
                        //Credit
                        to = new SmsToBatchTo();
                        to.setAcNo(toAcno);
                        to.setDrAmt(0d);
                        to.setCrAmt(amount);
                        to.setTranType(2);
                        to.setTy(0);
                        to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                        to.setTemplate(SmsType.TRANSFER_DEPOSIT);

                        smsBatchList.add(to);
                        //End here
                    } else {
                        Query insertQuery = em.createNativeQuery("insert into standins_transcancel(instrno,sno,fromacno,toacno,Amount,remarks,"
                                + "Effdate,closingdate,closedby,status,Closed,expirydt)(select instrno,sno,fromacno,toacno,Amount,remarks,"
                                + "Effdate,'" + bankDt + "','" + user + "',status,'Y',expirydt from standins_transpending st,accountmaster ac "
                                + "where st.FROMACNO=ac.acno and ac.curbrcode='" + brCode + "' and instrno='" + instrNo + "' and sno='" + sNo + "')");
                        int rs = insertQuery.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data insertion in standins_transcancel");
                        }
                        Query deleteQuery = em.createNativeQuery("delete from standins_transpending where fromacno='" + fromAcNo + "' and instrno='"
                                + instrNo + "' and sno='" + sNo + "'");
                        rs = deleteQuery.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data deletion from standins_transpending");
                        }
                    }
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    try {
                        ut.begin();
                        Query updateQuery2 = em.createNativeQuery("Update standins_transpending set processdate='" + bankDt + "',errormsg='" + e.getMessage()
                                + "',PROCESSEDBY='" + user + "' where fromacno='" + fromAcNo + "' and INSTRNO='" + instrNo + "' and SNO='" + sNo + "'");
                        int rs = updateQuery2.executeUpdate();
                        if (rs <= 0) {
                            throw new ApplicationException("SI : Problem in data updation from standins_transpending");
                        }
                        ut.commit();
                    } catch (Exception ex) {
                        ut.rollback();
                        System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + ex.getMessage());
                    }
                    System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());

                }
            }
        } catch (Exception e) {
            System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
        }
        return smsBatchList;
    }

    public List<SmsToBatchTo> siAutoPost(String user, String brCode, String bankDt, int thLmtParam) throws ApplicationException {

        List<SmsToBatchTo> smsBatchList = new ArrayList<SmsToBatchTo>();
        UserTransaction utInd = context.getUserTransaction();
        try {
            List select = em.createNativeQuery("select fromacno,toacno,sno,instrno,amount,effdate,status,remarks,enterby,entrydate,chargeflag,"
                    + "expirydt from standins_transmaster ,accountmaster where fromacno=acno and curbrcode='" + brCode + "' and Effdate <='" + bankDt
                    + "' and status='UNEXECUTED' order by INSTRNO,SNO").getResultList();

            for (int i = 0; i < select.size(); i++) {
                Vector ele = (Vector) select.get(i);

                String fromAcNo = ele.get(0).toString();
                String toAcno = ele.get(1).toString();

                int sNo = Integer.parseInt(ele.get(2).toString());
                float instrNo = Float.parseFloat(ele.get(3).toString());
                double amount = Double.parseDouble(ele.get(4).toString());

                String effDt = ele.get(5).toString();
                String effDate = effDt.substring(0, 4) + effDt.substring(5, 7) + effDt.substring(8, 10);

                String remarks = (ele.get(7).toString());
                String expiryDate = (ele.get(11).toString());
                String expiryDt = expiryDate.substring(0, 4) + expiryDate.substring(5, 7) + expiryDate.substring(8, 10);
                try {
                    utInd.begin();
                    String fromAccNature = ftsPost43Remote.getAccountNature(fromAcNo);
                    String fromBrCode = ftsPost43Remote.getCurrentBrnCode(fromAcNo);

                    String toAcctNature = ftsPost43Remote.getAccountNature(toAcno);
                    String toBrCode = ftsPost43Remote.getCurrentBrnCode(toAcno);

                    List accTypeFrom = getAccountDetails(fromAccNature, fromAcNo);
                    if (accTypeFrom.isEmpty()) {
                        throw new ApplicationException("Data does not exist for " + fromAcNo);
                    }
                    Vector accAllValues = (Vector) accTypeFrom.get(0);
                    int fromAccSts = Integer.parseInt(accAllValues.get(1).toString());
                    String balanceValue = accAllValues.get(2).toString();

                    String limitValue = accAllValues.get(3).toString();

                    String stnDetails = "";
                    if (fromAccSts == 9) {
                        stnDetails = "Account Has been Closed";
                    }
                    if (fromAccSts == 10) {
                        stnDetails = "Lien Marked";
                    }
                    if (fromAccSts == 8) {
                        stnDetails = " Operation Stopped";
                    }
                    if (fromAccSts == 7) {
                        stnDetails = " Withdrawal Stopped";
                    }
                    double fromBalance = Double.parseDouble(balanceValue);
                    double fromOdlimit = Double.parseDouble(limitValue);

                    List accTypeTo = getAccountDetails(toAcctNature, toAcno);
                    if (accTypeTo.isEmpty()) {
                        throw new ApplicationException("Data does not exist for " + toAcno);
                    }
                    Vector accToValues = (Vector) accTypeTo.get(0);
                    int toAccSts = Integer.parseInt(accToValues.get(1).toString());

                    if ((fromAccSts == 9) && (toAccSts == 9)) {
                        throw new ApplicationException("Both A/C s  -> " + fromAcNo + " And " + toAcno + " Are Closed ");
                    }
                    if ((fromAccSts == 9) || (fromAccSts == 10) || (fromAccSts == 8) || (fromAccSts == 7)) {
                        throw new ApplicationException("Ac No :-> " + fromAcNo + " " + stnDetails);
                    }
                    if ((toAccSts == 9)) {
                        throw new ApplicationException("Ac No :-> " + toAcno + " is closed");
                    }
//                    if ((fromBalance + fromOdlimit) <= amount) {
//                        throw new ApplicationException("Insuff. Fund In : " + fromAcNo);
//                    }
                    /**
                     * * Code Added For ThreshHold Limit Checking *
                     */
                    String chkThresh = "";
                    if ((toAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || toAcctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) && thLmtParam == 1) {
                        chkThresh = ftsPost43Remote.isThreshLmtExceed(toAcno, amount, ymmd.format(ymd.parse(bankDt)));
                        if (!chkThresh.equalsIgnoreCase("true")) {
                            throw new ApplicationException(chkThresh);
                        }
                    }
                    float trsNo = ftsPost43Remote.getTrsNo();
                    float recNo = 0;
                    float tokenNoDr = 0;
                    String result = "";

                    List list = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(toBrCode)).getResultList();
                    Vector element = (Vector) list.get(0);
                    String fromDetail = "SI trf to " + toAcno + " at branch " + element.get(0).toString();
                    String toDetail = "SI trf from " + fromAcNo + " at branch " + element.get(0).toString();
                    int crTranDesc = 0;
                    if ((toAcctNature.equals(CbsConstant.RECURRING_AC)) || (toAcctNature.equals(CbsConstant.DEMAND_LOAN))
                            || (toAcctNature.equals(CbsConstant.TERM_LOAN))) {
                        crTranDesc = 1;
                    }

                    if (brCode.equals(toBrCode)) {
                        result = ftsPost43Remote.ftsPosting43CBS(fromAcNo, 2, 1, amount, bankDt, bankDt, user, fromBrCode, fromBrCode, 0,
                                fromDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null, tokenNoDr,
                                "N", "", "", "S");
                        if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        result = ftsPost43Remote.ftsPosting43CBS(toAcno, 2, 0, amount, bankDt, bankDt, user, fromBrCode, fromBrCode,
                                crTranDesc, toDetail, trsNo, recNo, 0, null, "Y", "SYSTEM", "20", 3, "", null, null, null, null, null, null, null,
                                tokenNoDr, "N", "", "", "S");
                        if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        result = ftsPost43Remote.npaRecoveryUpdation(trsNo, toAcctNature, toAcno, bankDt, amount, fromBrCode, toBrCode, user);
                        if (!result.equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                    } else {
                        result = interBranchFacade.cbsPostingCx(fromAcNo, 1, bankDt, amount, 0f, 2, fromDetail, 0f, "20", "", null,
                                3, null, recNo, 0, fromBrCode, brCode, user, "SYSTEM", trsNo, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        result = ftsPost43Remote.updateBalance(fromAccNature, fromAcNo, 0, amount, "", "");
                        if (!result.equalsIgnoreCase("True")) {
                            throw new ApplicationException(result);
                        }
                        result = interBranchFacade.cbsPostingSx(toAcno, 0, bankDt, amount, 0f, 2, toDetail, 0f, "20", "", null,
                                3, null, recNo, crTranDesc, toBrCode, brCode, user, "SYSTEM", trsNo, "", "");
                        if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                    }
                    Query insertQuery6 = em.createNativeQuery("insert into standins_transexecuted (instrno,sno,fromacno,toacno,Amount,remarks,"
                            + "Effdate,processdate,processedby) values ('" + instrNo + "','" + sNo + "','" + fromAcNo + "','" + toAcno + "','"
                            + amount + "','" + remarks + "','" + effDate + "','" + bankDt + "','" + user + "')");
                    int rs = insertQuery6.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in insertion in standins_transexecuted");
                    }

                    Query deleteQuery6 = em.createNativeQuery("Delete st from standins_transmaster st inner join accountmaster ac on st.FROMacno=ac.acno "
                            + "and ac.curbrcode='" + brCode + "'  and FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                    deleteQuery6.executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in deletion from standins_transmaster");
                    }
                    ftsPost43Remote.lastTxnDateUpdation(toAcno);
                    ftsPost43Remote.lastTxnDateUpdation(fromAcNo);
                    //Sms Object Creation--Dr
                    SmsToBatchTo to = new SmsToBatchTo();
                    to.setAcNo(fromAcNo);
                    to.setDrAmt(amount);
                    to.setCrAmt(0d);
                    to.setTranType(2);
                    to.setTy(1);
                    to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                    to.setTemplate(SmsType.TRANSFER_WITHDRAWAL);

                    smsBatchList.add(to);
//                    //Credit
                    to = new SmsToBatchTo();
                    to.setAcNo(toAcno);
                    to.setDrAmt(0d);
                    to.setCrAmt(amount);
                    to.setTranType(2);
                    to.setTy(0);
                    to.setTxnDt(dmy.format(ymd.parse(bankDt)));
                    to.setTemplate(SmsType.TRANSFER_DEPOSIT);

                    smsBatchList.add(to);
                    //End here
                    utInd.commit();
                } catch (Exception e) {
                    utInd.rollback();
                    try {
                        utInd.begin();
                        Query insertQuery = em.createNativeQuery("insert into standins_transpending(FromAcno,TOAcno,InstrNo,Sno,remarks, EffDate,Amount,"
                                + "ProcessDate, ProcessedBy, ErrorMsg,expirydt)values ('" + fromAcNo + "','" + toAcno + "'," + instrNo + "," + sNo + ",'"
                                + remarks + "','" + effDate + "'," + amount + ",'" + bankDt + "','" + user + "','" + e.getMessage() + "','" + expiryDt + "')");
                        int result = insertQuery.executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem in insertion in standins_transpending for " + fromAcNo + " and " + toAcno);
                        }

                        Query deleteQuery = em.createNativeQuery("Delete From standins_transmaster where substring(FROMACNO,1,2)='" + brCode + "' and "
                                + "FromAcno='" + fromAcNo + "' and InstrNo='" + instrNo + "' and Sno='" + sNo + "'");
                        result = deleteQuery.executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem in deletion from standins_transmaster for " + fromAcNo + " and " + toAcno);
                        }
                        utInd.commit();
                    } catch (Exception ex) {
                        try {
                            utInd.rollback();
                            System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + ex.getMessage());
                        } catch (Exception exx) {
                            System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + exx.getMessage());
                        }
                    }
                    System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
        }
        return smsBatchList;
    }

    public List getAccountDetails(String acctNature, String acno) throws ApplicationException {
        try {
            List accountDetailList = new ArrayList();
            if ((acctNature.equals(CbsConstant.PAY_ORDER))) {
                accountDetailList = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from gltable a,reconbalan r where  a.acno='"
                        + acno + "' and a.acno=r.acno").getResultList();

            } else if (acctNature.equals(CbsConstant.FIXED_AC) || (acctNature.equals(CbsConstant.MS_AC))) {
                accountDetailList = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from td_accountmaster a,td_reconbalan r "
                        + "where  a.acno='" + acno + "' and a.acno=r.acno").getResultList();

            } else if (acctNature.equals(CbsConstant.CURRENT_AC)) {
                accountDetailList = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,ca_reconbalan r"
                        + " where  a.acno='" + acno + "' and a.acno=r.acno").getResultList();

            } else {
                accountDetailList = em.createNativeQuery("select custName,accStatus,balance,odlimit,optstatus from accountmaster a,reconbalan r where  "
                        + "a.acno='" + acno + "' and a.acno=r.acno").getResultList();
            }
            return accountDetailList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String updateBalances(String acctNature, String acno, double amt, float trsNumber, String brCode) throws ApplicationException {
        try {
            Query deleteQuery6 = em.createNativeQuery("Delete From recon_trf_d where substring(ACNO,1,2)='" + brCode + "' and trsno=" + trsNumber + "");
            int result = deleteQuery6.executeUpdate();
//            if (result <= 0) {
//                throw new ApplicationException("Problem in Data updation in SI");
//            }
            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER) || acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.SS_AC) || acctNature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {

                Query updateQuery1A = em.createNativeQuery("UPDATE reconbalan rc,accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + brCode + "'");
                result = updateQuery1A.executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in Data updation in SI");
                }

            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                Query updateQuery1A = em.createNativeQuery("UPDATE ca_reconbalan rc,accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + brCode + "'");
                result = updateQuery1A.executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in Data updation in SI");
                }
            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                    || acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {

                Query updateQuery1A = em.createNativeQuery("UPDATE td_reconbalan rc,td_accountmaster ac SET rc.Balance =rc.Balance -" + amt + "  where rc.acno='" + acno + "' and rc.acno=ac.acno  and ac.curbrcode='" + brCode + "'");
                result = updateQuery1A.executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in Data updation in SI");
                }
            }
            return "TRUE";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private boolean isTaodayHoliday(String dt, String brCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select DayBeginFlag from bankdays where Date='" + dt + "' and DayBeginFlag = 'H' and brncode='" + brCode + "'").getResultList();
            if (list.isEmpty()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String getBankGstinByBrnCode(int brncode) throws ApplicationException {
        try {
            List ifscList = em.createNativeQuery("SELECT gst_in FROM branchmaster WHERE BRNCODE=" + brncode + "").getResultList();
            if (!ifscList.isEmpty()) {
                Vector element = (Vector) ifscList.get(0);
                if (element.get(0) == null || element.get(0).toString().equalsIgnoreCase("")) {
                    return "";
                } else {
                    return element.get(0).toString();
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    private String atmGstReversalProcess(String curDt) throws ApplicationException {
        try {
            String lastDayEndDt = "";
            Map<String, Double> map = new HashMap<>();
            List list = em.createNativeQuery("select max(date_format(date,'%Y%m%d')) from "
                    + "cbs_bankdays where daybeginflag='Y' and dayendflag='Y' and date<'" + curDt + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                lastDayEndDt = ele.get(0).toString();
            }
            List tempList4 = em.createNativeQuery("select acno from abb_parameter_info where purpose='ATM-TRANSACTION-FEE'").getResultList();
            if (tempList4.isEmpty()) {
                throw new ApplicationException("ATM Transaction Fee head does not define");
            }
            Vector tempVec = (Vector) tempList4.get(0);
            String glhead = tempVec.get(0).toString();
            String txnFeeHead = 90 + glhead;

            if (!lastDayEndDt.equals("")) {
                List gstList = em.createNativeQuery("select ifnull(cast(sum(a.totamt) as decimal(10,2)),0) as totAmt, ifnull(cast(sum(b.amt) as decimal(10,2)),0) as txnFee from "
                        + " (select processing_code, cast(cast(substring(TRANSACTION_FEE,2,9) as decimal(14))/100 as decimal(10,2)) as totAmt "
                        + " from atm_normal_transaction_parameter where TRANSACTION_FEE <> 'C00000000' and IN_PROCESS_FLAG='S' and "
                        + " IN_PROCESS_STATUS='SUCCESS' and tran_date='" + lastDayEndDt + "' and SYSTEM_TRACE_NUMBER not in (select ORIGINAL_SYSTEM_TRACE_NUMBER "
                        + " from atm_reversal_transaction_parameter where tran_date='" + lastDayEndDt + "' and IN_PROCESS_FLAG='S' and IN_PROCESS_STATUS='SUCCESS' "
                        + " and amount=0)) a  left join "
                        + " (select charge_name, amt from cbs_charge_detail where charge_type='ATM-TXN-FEE' ) b on b.charge_name = a.processing_code").getResultList();
                if (!gstList.isEmpty()) {
                    Vector ele = (Vector) gstList.get(0);
                    double totAmt = Double.parseDouble(ele.get(0).toString());
                    double feeAmt = Double.parseDouble(ele.get(1).toString());

                    double gstAmt = 0;
                    if (totAmt != 0 && feeAmt != 0) {
                        map = interBranchFacade.getTaxComponent(feeAmt, lastDayEndDt);
                        Set<Map.Entry<String, Double>> set = map.entrySet();
                        Iterator<Map.Entry<String, Double>> it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = it.next();
                            gstAmt = gstAmt + Double.parseDouble(entry.getValue().toString());
                        }
                        if (totAmt != feeAmt + CbsUtil.round(gstAmt, 2)) {
                            throw new ApplicationException("There is some issue in GST Slab");
                        }

                        float trsNo = ftsPost43Remote.getTrsNo();
                        String mainDetails = "ATM Transaction Fee GST reversal of dated " + dmy.format(ymd.parse(lastDayEndDt));
                        float recNo = ftsPost43Remote.getRecNo();
                        String result = ftsPost43Remote.ftsPosting43CBS(txnFeeHead, 2, 1, gstAmt, curDt, curDt, "System", "90", "90", 71,
                                mainDetails, trsNo, recNo, 0, "", "Y", "System", "", 3, "", "", "", 0f, "", "", "", "", 0f, "N", "", "", "S");
                        if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                            throw new ApplicationException(result.substring(4));
                        }
                        it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry entry = it.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = "90" + keyArray[1];
                            mainDetails = description.toUpperCase() + " for ATM Transaction Fee of dated " + dmy.format(ymd.parse(lastDayEndDt));
                            double taxAmount = Double.parseDouble(entry.getValue().toString());
                            recNo = recNo + 1;
                            result = ftsPost43Remote.ftsPosting43CBS(taxHead, 2, 0, taxAmount, curDt, curDt, "System", "90", "90", 71,
                                    mainDetails, trsNo, recNo, 0, "", "Y", "System", "", 3, "", "", "", 0f, "", "", "", "", 0f, "N", "", "", "S");
                            if (!result.substring(0, 4).equalsIgnoreCase("TRUE")) {
                                throw new ApplicationException(result);
                            }
                        }
                    }
                }
            }
            return "True";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAtmTransactionDataForMarking(String date) throws ApplicationException {
        List resultList = new ArrayList<>();
        try {
            resultList = em.createNativeQuery("select processing_code,0 as revIndicator,system_trace_number,terminal_id,from_account_number,"
                    + "amount,rrn,card_number,'',tran_time,in_coming_date,ifnull(trsno,0) as trsNo ,'atm_normal_transaction_parameter',tran_date from atm_normal_transaction_parameter "
                    + "where in_coming_date = '" + date + "' and in_process_flag='S' "
                    + "union all "
                    + "select processing_code,REVERSAL_INDICATOR as revIndicator,system_trace_number,terminal_id,'',amount,'',card_number,original_system_trace_number,"
                    + "tran_time,in_coming_date,ifnull(trsno,0) as trsNo ,'atm_off_us_transaction_parameter',tran_date from atm_off_us_transaction_parameter where in_coming_date = '" + date + "' "
                    + "and in_process_flag='S' "
                    + "union all "
                    + "select processing_code,1 as revIndicator,system_trace_number,terminal_id,from_account_number,amount,rrn,card_number,"
                    + "original_system_trace_number,tran_time,in_coming_date,ifnull(trsno,0) as trsNo ,'atm_reversal_transaction_parameter',tran_date from atm_reversal_transaction_parameter "
                    + "where in_coming_date = '" + date + "' and in_process_flag='S'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return resultList;
    }

    public String holidayMarkingProcess(List resultList, String user, String todate, String frDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String updatationDate = "";
        String dataUpdateQuery1 = "";
        String dataUpdateQuery2 = "";
        String dataUpdateQuery3 = "";
        String trsnoquery1 = "";
        String trsnoquery2 = "";
        String trsnoquery3 = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select min(Date) from cbs_bankdays where DayBeginFlag = 'N' and DayEndFlag = 'N' and Date > '" + todate + "'").getResultList();
            Vector datevec = (Vector) list.get(0);
            updatationDate = datevec.get(0).toString();

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    String tableName = vec.get(12).toString();
                    String tranDate = vec.get(13).toString();
                    int m = em.createNativeQuery("insert into atm_txn_history(PROCESSING_CODE,REVERSAL_INDICATOR,SYSTEM_TRACE_NUMBER,TERMINAL_ID,"
                            + "FROM_ACCOUNT_NUMBER,AMOUNT,RRN,CARD_NUMBER,ORG_SYS_TRACE_NO,PREV_TRAN_TIME,PREV_IN_COMING_DATE,UPDATED_IN_COMING_DATE,TRSNO,UPDATE_BY,"
                            + "UPDATE_TIME) values ('" + vec.get(0).toString() + "','" + Integer.parseInt(vec.get(1).toString()) + "','" + vec.get(2).toString() + "','" + vec.get(3).toString() + "',"
                            + " '" + vec.get(4).toString() + "'," + new BigDecimal(vec.get(5).toString()) + ",'" + vec.get(6).toString() + "','" + vec.get(7).toString() + "',"
                            + "'" + vec.get(8).toString() + "','" + vec.get(9).toString() + "','" + vec.get(10).toString() + "','" + updatationDate + "'," + Float.parseFloat(vec.get(11).toString()) + ","
                            + "'" + user + "',now())").executeUpdate();
                    if (m < 0) {
                        throw new ApplicationException("Problem In AtmTxnHis Insertion");
                    }

//                    
                    if (tableName.equalsIgnoreCase("atm_reversal_transaction_parameter")) {

                        int s = em.createNativeQuery("Update atm_reversal_transaction_parameter set in_coming_date ='" + updatationDate + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();
                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_reversal_transaction_parameter updation. ");
                        }

                    } else if (tableName.equalsIgnoreCase("atm_off_us_transaction_parameter")) {

                        int s = em.createNativeQuery("Update atm_off_us_transaction_parameter set in_coming_date ='" + updatationDate + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();

                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_off_us_transaction_parameter updation. ");
                        }
                    } else if (tableName.equalsIgnoreCase("atm_normal_transaction_parameter")) {
                        int s = em.createNativeQuery("Update atm_normal_transaction_parameter set in_coming_date ='" + updatationDate + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();
                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_normal_transaction_parameter updation. ");
                        }
                    }
                }

                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    if (!(vec.get(11).toString() == null)) {
                        List list1 = em.createNativeQuery("select * from ca_recon where dt='" + vec.get(10).toString() + "' and trsno='" + Float.parseFloat(vec.get(11).toString()) + "' and  TranDesc='70'").getResultList();
                        if (!list1.isEmpty()) {
                            if (trsnoquery1.equalsIgnoreCase("")) {
                                trsnoquery1 = trsnoquery1 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            } else {
                                trsnoquery1 = trsnoquery1 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            }

                            dataUpdateQuery1 = " update ca_recon set Dt='" + updatationDate + "' where dt='" + vec.get(10).toString() + "' and trsno in(" + trsnoquery1 + ")  and  TranDesc='70'";
                        }
                        List list2 = em.createNativeQuery("select * from recon where dt='" + vec.get(10).toString() + "' and trsno='" + Float.parseFloat(vec.get(11).toString()) + "' and  TranDesc='70'").getResultList();
                        if (!list2.isEmpty()) {
                            if (trsnoquery2.equalsIgnoreCase("")) {
                                trsnoquery2 = trsnoquery2 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            } else {
                                trsnoquery2 = trsnoquery2 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            }
                            dataUpdateQuery2 = " update recon set Dt='" + updatationDate + "' where dt='" + vec.get(10).toString() + "' and trsno in(" + trsnoquery2 + ") and  TranDesc='70'";
                        }
                    }
                    if (!vec.get(11).toString().equals("0.0")) {
                        if (trsnoquery3.equalsIgnoreCase("")) {
                            trsnoquery3 = trsnoquery3 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                        } else {
                            trsnoquery3 = trsnoquery3 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                        }
                        dataUpdateQuery3 = " update gl_recon set Dt='" + updatationDate + "' where dt='" + vec.get(10).toString() + "' and trsno in(" + trsnoquery3 + ") and  TranDesc='70'";
                    }
                }

            }
            String trsnoddsTransaction = "";
            String dataUpdateQuery4 = "";
            List ddsTransactionDataList = em.createNativeQuery("select txn_id,ifnull(batch_no,0) as trsno,ifnull(business_date,'') as incomingDate from online_pigme_request where business_date = '" + frDate + "' and cbs_return_reason = 'Success'").getResultList();
            if (!ddsTransactionDataList.isEmpty()) {
                for (int i = 0; i < ddsTransactionDataList.size(); i++) {
                    Vector ddVec = (Vector) ddsTransactionDataList.get(i);
                    int t = em.createNativeQuery("insert into online_pigme_holiday_history(prev_txn_id,prev_batch_no,prev_business_date,updated_business_date)values("
                            + " '" + ddVec.get(0).toString() + "','" + Long.parseLong(ddVec.get(1).toString()) + "','" + ddVec.get(2).toString() + "','" + updatationDate + "')").executeUpdate();
                    if (t < 0) {
                        throw new ApplicationException("Problem In dds History insertion.");
                    }
                    int n = em.createNativeQuery("update online_pigme_request set business_date= '" + updatationDate + "' where txn_id='" + ddVec.get(0).toString() + "' and business_date='" + ddVec.get(2).toString() + "'").executeUpdate();
                    if (n < 0) {
                        throw new ApplicationException("Problem In online_pigme_request updating.");
                    }
                    List list4 = em.createNativeQuery("select * from ddstransaction where  dt='" + ddVec.get(2).toString() + "' and trsno='" + Float.parseFloat(ddVec.get(1).toString()) + "' and TranDesc='78'").getResultList();
                    if (!list4.isEmpty()) {
                        if (trsnoddsTransaction.equalsIgnoreCase("")) {
                            trsnoddsTransaction = trsnoddsTransaction + "'" + Float.parseFloat(ddVec.get(1).toString()) + "'";
                        } else {
                            trsnoddsTransaction = trsnoddsTransaction + "," + "'" + Float.parseFloat(ddVec.get(1).toString()) + "'";
                        }
                        dataUpdateQuery4 = " update ddstransaction set Dt='" + updatationDate + "' where  dt='" + ddVec.get(2).toString() + "' and trsno in(" + trsnoddsTransaction + ") and  TranDesc='78'";
                    }
                }
            }

            if (!trsnoquery1.equalsIgnoreCase("")) {
                int s = em.createNativeQuery(dataUpdateQuery1).executeUpdate();
                if (s < 0) {
                    throw new ApplicationException("Problem In ca_recon updation");
                }
            }
            if (!trsnoquery2.equalsIgnoreCase("")) {
                int l = em.createNativeQuery(dataUpdateQuery2).executeUpdate();
                if (l < 0) {
                    throw new ApplicationException("Problem In recon updation");
                }
            }
            if (!trsnoquery3.equalsIgnoreCase("")) {
                int m = em.createNativeQuery(dataUpdateQuery3).executeUpdate();
                if (m < 0) {
                    throw new ApplicationException("Problem In recon updation");
                }
            }
            if (!trsnoddsTransaction.equalsIgnoreCase("")) {
                int n = em.createNativeQuery(dataUpdateQuery4).executeUpdate();
                if (n < 0) {
                    throw new ApplicationException("Problem In ddsTransaction updation");
                }
            }
            int n = em.createNativeQuery("UPDATE cbs_bankdays SET daybeginflag='H',dayendflag='Y' WHERE DATE between '" + frDate + "' and '" + todate + "'").executeUpdate();
            if (n < 0) {
                throw new ApplicationException("Problem In cbs_bankdays updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception e) {
                System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String holidayUnmarkingProcess(String user, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String updatationDate = "";
        try {
            ut.begin();
            List list = em.createNativeQuery("select min(Date) from cbs_bankdays where DayBeginFlag = 'N' and DayEndFlag = 'N' and Date > '" + date + "'").getResultList();
            Vector datevec = (Vector) list.get(0);
            updatationDate = datevec.get(0).toString();
            List resultList = getAtmTransactionDataForMarking(updatationDate);
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    String tableName = vec.get(12).toString();
                    String tranDate = vec.get(13).toString();
                    int m = em.createNativeQuery("insert into atm_txn_history(PROCESSING_CODE,REVERSAL_INDICATOR,SYSTEM_TRACE_NUMBER,TERMINAL_ID,"
                            + "FROM_ACCOUNT_NUMBER,AMOUNT,RRN,CARD_NUMBER,ORG_SYS_TRACE_NO,PREV_TRAN_TIME,PREV_IN_COMING_DATE,UPDATED_IN_COMING_DATE,TRSNO,UPDATE_BY,"
                            + "UPDATE_TIME) values ('" + vec.get(0).toString() + "','" + Integer.parseInt(vec.get(1).toString()) + "','" + vec.get(2).toString() + "','" + vec.get(3).toString() + "',"
                            + " '" + vec.get(4).toString() + "'," + new BigDecimal(vec.get(5).toString()) + ",'" + vec.get(6).toString() + "','" + vec.get(7).toString() + "',"
                            + "'" + vec.get(8).toString() + "','" + vec.get(9).toString() + "','" + vec.get(10).toString() + "','" + date + "'," + Float.parseFloat(vec.get(11).toString()) + ","
                            + "'" + user + "',now())").executeUpdate();
                    if (m < 0) {
                        throw new ApplicationException("Problem In AtmTxnHis Insertion");
                    }
                    if (tableName.equalsIgnoreCase("atm_reversal_transaction_parameter")) {

                        int s = em.createNativeQuery("Update atm_reversal_transaction_parameter set in_coming_date ='" + date + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();
                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_reversal_transaction_parameter updation. ");
                        }

                    } else if (tableName.equalsIgnoreCase("atm_off_us_transaction_parameter")) {

                        int s = em.createNativeQuery("Update atm_off_us_transaction_parameter set in_coming_date ='" + date + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();

                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_off_us_transaction_parameter updation. ");
                        }
                    } else if (tableName.equalsIgnoreCase("atm_normal_transaction_parameter")) {
                        int s = em.createNativeQuery("Update atm_normal_transaction_parameter set in_coming_date ='" + date + "' where system_trace_number ='" + vec.get(2).toString() + "' and TRAN_DATE='" + tranDate + "' ").executeUpdate();
                        if (s < 0) {
                            throw new ApplicationException("Problem In atm_normal_transaction_parameter updation. ");
                        }
                    }
                }

                String dataUpdateQuery1 = "";
                String dataUpdateQuery2 = "";
                String dataUpdateQuery3 = "";
                String trsnoquery1 = "";
                String trsnoquery2 = "";
                String trsnoquery3 = "";
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vec = (Vector) resultList.get(i);
                    if (!(vec.get(11).toString() == null)) {
                        List list1 = em.createNativeQuery("select * from ca_recon where trsno='" + Float.parseFloat(vec.get(11).toString()) + "' and dt='" + vec.get(10).toString() + "' and TranDesc='70'").getResultList();
                        if (!list1.isEmpty()) {
                            if (trsnoquery1.equalsIgnoreCase("")) {
                                trsnoquery1 = trsnoquery1 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            } else {
                                trsnoquery1 = trsnoquery1 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            }

                            dataUpdateQuery1 = " update ca_recon set Dt='" + date + "' where trsno in(" + trsnoquery1 + ")  and dt='" + vec.get(10).toString() + "' and TranDesc='70'";
                        }
                        List list2 = em.createNativeQuery("select * from recon where trsno='" + Float.parseFloat(vec.get(11).toString()) + "' and dt='" + vec.get(10).toString() + "' and TranDesc='70'").getResultList();
                        if (!list2.isEmpty()) {
                            if (trsnoquery2.equalsIgnoreCase("")) {
                                trsnoquery2 = trsnoquery2 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            } else {
                                trsnoquery2 = trsnoquery2 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                            }
                            dataUpdateQuery2 = " update recon set Dt='" + date + "' where trsno in(" + trsnoquery2 + ") and dt='" + vec.get(10).toString() + "' and TranDesc='70'";
                        }
                    }
                    if (!vec.get(11).toString().equals("0.0")) {
                        if (trsnoquery3.equalsIgnoreCase("")) {
                            trsnoquery3 = trsnoquery3 + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                        } else {
                            trsnoquery3 = trsnoquery3 + "," + "'" + Float.parseFloat(vec.get(11).toString()) + "'";
                        }
                        dataUpdateQuery3 = " update gl_recon set Dt='" + date + "' where trsno in(" + trsnoquery3 + ") and dt='" + vec.get(10).toString() + "' and TranDesc='70'";
                    }
                }

                String trsnoddsTransaction = "";
                String dataUpdateQuery4 = "";
                List ddsTransactionDataList = em.createNativeQuery("select txn_id,ifnull(batch_no,0) as trsno,ifnull(business_date,'') as incomingDate from online_pigme_request where business_date = '" + updatationDate + "' and cbs_return_reason = 'Success'").getResultList();
                if (!ddsTransactionDataList.isEmpty()) {
                    for (int i = 0; i < ddsTransactionDataList.size(); i++) {
                        Vector ddVec = (Vector) ddsTransactionDataList.get(i);
                        int t = em.createNativeQuery("insert into online_pigme_holiday_history(prev_txn_id,prev_batch_no,prev_business_date,updated_business_date)values("
                                + " '" + Long.parseLong(ddVec.get(0).toString()) + "','" + Long.parseLong(ddVec.get(1).toString()) + "','" + ddVec.get(2).toString() + "','" + updatationDate + "')").executeUpdate();
                        if (t < 0) {
                            throw new ApplicationException("Problem In dds History insertion.");
                        }
                        int n = em.createNativeQuery("update online_pigme_request set business_date= '" + date + "' where txn_id='" + ddVec.get(0).toString() + "' and business_date='" + ddVec.get(2).toString() + "'").executeUpdate();
                        if (n < 0) {
                            throw new ApplicationException("Problem In online_pigme_request updating.");
                        }
                        List list4 = em.createNativeQuery("select * from ddstransaction where  dt='" + ddVec.get(2).toString() + "' and trsno='" + Float.parseFloat(ddVec.get(1).toString()) + "' and TranDesc='78'").getResultList();
                        if (!list4.isEmpty()) {
                            if (trsnoddsTransaction.equalsIgnoreCase("")) {
                                trsnoddsTransaction = trsnoddsTransaction + "'" + Float.parseFloat(ddVec.get(1).toString()) + "'";
                            } else {
                                trsnoddsTransaction = trsnoddsTransaction + "," + "'" + Float.parseFloat(ddVec.get(1).toString()) + "'";
                            }
                            dataUpdateQuery4 = " update ddstransaction set Dt='" + date + "' where  dt='" + ddVec.get(2).toString() + "' and trsno in(" + trsnoddsTransaction + ") and  TranDesc='78'";
                        }
                    }
                }
                if (!trsnoquery1.equalsIgnoreCase("")) {
                    int s = em.createNativeQuery(dataUpdateQuery1).executeUpdate();
                    if (s < 0) {
                        throw new ApplicationException("Problem In ca_recon updation");
                    }
                }
                if (!trsnoquery2.equalsIgnoreCase("")) {
                    int l = em.createNativeQuery(dataUpdateQuery2).executeUpdate();
                    if (l < 0) {
                        throw new ApplicationException("Problem In recon updation");
                    }
                }
                if (!trsnoquery3.equalsIgnoreCase("")) {
                    int m = em.createNativeQuery(dataUpdateQuery3).executeUpdate();
                    if (m < 0) {
                        throw new ApplicationException("Problem In recon updation");
                    }
                }
                if (!trsnoddsTransaction.equalsIgnoreCase("")) {
                    int n = em.createNativeQuery(dataUpdateQuery4).executeUpdate();
                    if (n < 0) {
                        throw new ApplicationException("Problem In ddsTransaction updation");
                    }
                }
            }
            int n = em.createNativeQuery("UPDATE cbs_bankdays SET daybeginflag='N',dayendflag='N' WHERE DATE='" + date + "';").executeUpdate();
            if (n < 0) {
                throw new ApplicationException("Problem In cbs_bankdays updation.");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception e) {
                System.out.println("Error in Auto SI->>>>>>>>>>>>>>" + e.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public boolean isIsoReconsiled(Map<String, BigDecimal> isoAmountPosition) throws Exception {
        try {
            Set<Entry<String, BigDecimal>> set = isoAmountPosition.entrySet();
            Iterator<Entry<String, BigDecimal>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, BigDecimal> entry = it.next();
                if (entry.getValue().compareTo(BigDecimal.ZERO) != 0) {
                    return false;
                }
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return true;
    }

    public String moveDeputedUsers(String date) throws ApplicationException {
        try {
            List list1 = em.createNativeQuery("select userid from securityinfo where deputeorxfer = 'Deputation' and todate<'" + date + "'").getResultList();
            for (int i = 0; i < list1.size(); i++) {
                Vector ddVec = (Vector) list1.get(i);
                List l7 = em.createNativeQuery("select cast(coalesce(max(sno),0) as unsigned) from securityinfohistory where userid=UPPER('" + ddVec.get(0).toString() + "')").getResultList();
                long tmpSno = 1;
                if (!l7.isEmpty()) {
                    Vector v5 = (Vector) l7.get(0);
                    tmpSno = Long.parseLong(v5.get(0).toString());
                    tmpSno = tmpSno + 1;
                }

                List lastBrCode = em.createNativeQuery("select brncode from securityinfohistory where userid='" + ddVec.get(0).toString() + "' and sno = "
                        + "(select max(sno) from securityinfohistory where  userid='" + ddVec.get(0).toString() + "' /*and orgbrncode<>brncode*/ )").getResultList();
                Vector vtr = (Vector) lastBrCode.get(0);

                Query q1 = em.createNativeQuery("insert securityinfohistory(sno,LevelId,UserId,Password,UserName,tocashlimit,lastLoginDate,"
                        + "Address , Status, enterby, pwDate, cashierst, login,clgdebit,trandebit,brncode,orgbrncode,todate,fromdate,deputeorxfer,"
                        + "lastupdatedt,NpciUserName,FailedAttemptCount,LastFailedLoginDate) select " + tmpSno + ",LevelId,UserId,Password,UserName,"
                        + "tocashlimit,lastLoginDate,Address , Status, enterby, pwDate, cashierst, login, clgdebit,trandebit,brncode,orgbrncode,"
                        + "todate,fromdate,deputeorxfer,CURRENT_TIMESTAMP,NpciUserName,FailedAttemptCount,LastFailedLoginDate from  securityinfo  "
                        + "where userid=UPPER('" + ddVec.get(0).toString() + "')");

                int int4 = q1.executeUpdate();
                if (int4 <= 0) {
                    throw new ApplicationException("Problem in securityinfohistory insertion");
                }

                int rs = em.createNativeQuery("update securityinfo set brncode ='" + vtr.get(0).toString() + "', deputeorxfer = null, fromdate = null, todate =null "
                        + "where userid ='" + ddVec.get(0).toString() + "'").executeUpdate();
                if (rs <= 0) {
                    throw new ApplicationException("Problem in securityinfo updation");
                }


            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
