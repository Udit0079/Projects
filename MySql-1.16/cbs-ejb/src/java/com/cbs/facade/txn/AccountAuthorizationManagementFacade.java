/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.txn;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.LoanAcDetailStatementPojo;
import com.cbs.dto.report.NpaAccountDetailPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.dto.sms.MbSmsSenderBankDetailTO;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.other.NpciMgmtFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.MessageDetailBeanRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
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
 * @author root
 */
@Stateless(mappedName = "AccountAuthorizationManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class AccountAuthorizationManagementFacade implements AccountAuthorizationManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private LoanInterestCalculationFacadeRemote interestCalculationBean;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private SmsManagementFacadeRemote smsMgmtFacade;
    @EJB
    private MessageDetailBeanRemote messageDetailRemote;
    @EJB
    NpciMgmtFacadeRemote npciRemote;
    @EJB
    private InterBranchTxnFacadeRemote interFts;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ddmy = new SimpleDateFormat("ddMMyyyy");
    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat y_m_d = new SimpleDateFormat("yyyy-MM-dd");

    /* Start of Account Closing Authorization*/
    public List closedActGridDetail(String brCode) throws ApplicationException {
        List acList = new ArrayList();
        try {
            acList = em.createNativeQuery("SELECT ACNO,CLOSEDBY,'N' FROM acctclose_his WHERE AUTH <>'Y' AND substring(ACNO,1,2) = '" + brCode + "'").getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acList;
    }

    public String closeActCustName(String acno) throws ApplicationException {
        String custName = "";

        try {
            List custNameList = null;
            String acNat = fts.getAccountNature(acno);
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

    public String authorizeAction(List list, String enterBy, String brcode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "", desp = "";
        int servTaxApplyCode = 0, roundUpTo = 0;
        double  totalRot = 0;

        try {
            ut.begin();
            String acctNature = "";
            if ((list == null) || (enterBy == null) || (list.isEmpty()) || (brcode == null)) {
                ut.rollback();
                return "Please Double Click On The Auth Column To Authorize Entry.";
            }

            Integer closingFlag = fts.getCodeForReportName("ACCOUNT-CLOSING-CHARGES");
            String glAcno = "";
            double closingCharge = 0d;
            if (closingFlag == 1) {
                List closingChargeList = em.createNativeQuery("SELECT glheadMisc,charges from parameterinfo_miscincome where purpose ='ACCOUNT-CLOSURE-CHARGES' "
                        + "and effectivedt = (select max(effectivedt) from parameterinfo_miscincome where purpose = 'ACCOUNT-CLOSURE-CHARGES')").getResultList();
                if (!closingChargeList.isEmpty()) {
                    Vector vtr = (Vector) closingChargeList.get(0);
                    glAcno = vtr.get(0).toString();
                    closingCharge = Double.parseDouble(vtr.get(1).toString());
                }
                servTaxApplyCode = fts.getCodeForReportName("STAXMODULE_ACTIVE");
                totalRot = 0;
                if (servTaxApplyCode == 1) {
                    Map<String, Double> slabMap = interFts.getTaxComponentSlab(ymd.format(new Date()));
                    Set<Map.Entry<String, Double>> set = slabMap.entrySet();
                    Iterator<Map.Entry<String, Double>> it = set.iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = it.next();
                        String[] keyArray = entry.getKey().toString().split(":");
                        desp = keyArray[0];
                        roundUpTo = Integer.parseInt(keyArray[3]);
                        totalRot += Double.parseDouble(entry.getValue().toString());
                    }
                }
            }
            float trSNo = fts.getTrsNo();
            float recno = fts.getRecNo().floatValue();
            for (int a = 0, b = 1, c = 2, d = 3; a < list.size(); a = a + 4, b = b + 4, c = c + 4, d = d + 4) {
                String acNo = list.get(a).toString();
                String brnCode = fts.getCurrentBrnCode(acNo);
                List checkInstNo = em.createNativeQuery("SELECT DATE FROM bankdays WHERE DAYENDFLAG = 'N' AND brncode = '" + brnCode + "'").getResultList();
                Vector ele = (Vector) checkInstNo.get(0);
                String tmpbd = ele.get(0).toString();
                acctNature = fts.getAccountNature(acNo);
                //New Code for Closing charge Txn
                if (closingFlag == 1) {
                    glAcno = acNo.substring(0, 2) + glAcno + "01";


                    double partySerCharge = CbsUtil.round(((closingCharge * totalRot) / 100), roundUpTo);
                    double totalDramt = closingCharge + partySerCharge;
                    recno = recno + 1;
                    msg = fts.insertRecons(acctNature, acNo, 1, closingCharge, tmpbd,
                            tmpbd, 2, "Account Closing Charges Posting", enterBy, trSNo, ymd.format(new Date()),
                            recno, "Y", enterBy, 33, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "",
                            brcode, brcode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    if (closingCharge != 0 && servTaxApplyCode == 1) {
                        recno = recno + 1;
                        msg = fts.insertRecons(acctNature, acNo, 1, partySerCharge, tmpbd,
                                tmpbd, 2, desp + "Account Closing Charges Posting", enterBy, trSNo, ymd.format(new Date()),
                                recno, "Y", enterBy, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "",
                                "", brcode, brcode, 0, "", "", "");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                    msg = fts.updateBalance(acctNature, acNo, 0.0, totalDramt, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    recno = recno + 1;
                    //  Double.parseDouble(formatter.format(totalCharge))
                    msg = fts.insertRecons("PO", glAcno, 0, CbsUtil.round(closingCharge, roundUpTo), tmpbd, tmpbd,
                            2, "Account Closing Charges Posting", enterBy, trSNo, ymd.format(new Date()), recno,
                            "Y", enterBy, 33, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", brcode, brcode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = fts.updateBalance("PO", glAcno, CbsUtil.round(closingCharge, roundUpTo), 0.0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    //Service head transaction 
                    if (servTaxApplyCode == 1) {
                        // double totalChgBasedOnTotalServiceTax = CbsUtil.round(((closingCharge * 100) / totalRot), roundUpTo);
                        Map<String, Double> map = interFts.getTaxComponent(closingCharge, ymd.format(new Date()));
                        Set<Map.Entry<String, Double>> staxSet = map.entrySet();
                        Iterator<Map.Entry<String, Double>> staxIt = staxSet.iterator();
                        while (staxIt.hasNext()) {
                            Map.Entry entry = staxIt.next();
                            String[] keyArray = entry.getKey().toString().split(":");
                            String description = keyArray[0];
                            String taxHead = brcode + keyArray[1];
                            double serAmount = Double.parseDouble(entry.getValue().toString());
                            recno = recno + 1;

                            description = description + "Account Closing Charges Posting";
                            msg = fts.insertRecons("PO", taxHead, 0, serAmount, tmpbd, tmpbd, 2,
                                    description, enterBy, trSNo, ymd.format(new Date()), recno, "Y",
                                    enterBy, 71, 3, "", "", 0.0f, "", "", 0, "", 0.0f, "", "", brcode, brcode, 0,
                                    "", "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                            msg = fts.updateBalance("PO", taxHead, serAmount, 0.0, "", "");
                            if (!msg.equalsIgnoreCase("true")) {
                                throw new ApplicationException(msg);
                            }
                        }
                    }
                }
                // End this transaction Code

                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    List tmpseries = em.createNativeQuery("select accstatus,closingDate,acno from td_accountmaster where acno='" + acNo + "'").getResultList();
                    if (tmpseries.size() > 0) {
                        Integer tdAccountMaster = em.createNativeQuery("UPDATE td_accountmaster set  closingDate = '" + tmpbd + "'  where acno='" + acNo + "'").executeUpdate();
                        if (tdAccountMaster <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }

                        Integer acctCloseHis = em.createNativeQuery("UPDATE acctclose_his SET closingdate='" + tmpbd + "' ,auth='Y',authby='" + enterBy + "' WHERE acno='" + acNo + "'").executeUpdate();
                        if (acctCloseHis <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }
                    }
                } else {
                    List tmpseries = em.createNativeQuery("select accstatus,closingDate,acno from accountmaster where acno='" + acNo + "'").getResultList();
                    if (tmpseries.size() > 0) {
                        Integer tdAccountMaster = em.createNativeQuery("UPDATE accountmaster set  closingDate = '" + tmpbd + "'  where acno='" + acNo + "'").executeUpdate();
                        if (tdAccountMaster <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }
                        System.out.println("UPDATE acctclose_his SET closingdate='" + tmpbd + "' ,auth='Y',authby='" + enterBy + "' WHERE acno='" + acNo + "'");
                        Integer acctCloseHis = em.createNativeQuery("UPDATE acctclose_his SET closingdate='" + tmpbd + "' ,auth='Y',authby='" + enterBy + "' WHERE acno='" + acNo + "'").executeUpdate();
                        System.out.println("acctCloseHis:=======" + acctCloseHis);
                        if (acctCloseHis <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }

                    }

                }

                if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                    List chkAccountMaster = em.createNativeQuery("SELECT * FROM chbook_sb WHERE ACNO='" + acNo + "' AND STATUSFLAG='F'").getResultList();
                    if (!chkAccountMaster.isEmpty()) {
                        Integer tdAccountMaster = em.createNativeQuery("UPDATE chbook_sb SET STATUSFLAG='U' WHERE ACNO='" + acNo + "' AND STATUSFLAG='F'").executeUpdate();
                        if (tdAccountMaster <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }
                    }

                    Integer acctCloseHis = em.createNativeQuery("UPDATE acctclose_his SET closingdate='" + tmpbd + "' ,auth='Y',authby='" + enterBy + "' WHERE acno='" + acNo + "'").executeUpdate();
                    if (acctCloseHis <= 0) {
                        ut.rollback();
                        return "DATA NOT updated";
                    }
                }

                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {

                    List chkAccountMaster = em.createNativeQuery("SELECT * FROM chbook_ca WHERE ACNO='" + acNo + "' AND STATUSFLAG='F'").getResultList();
                    if (!chkAccountMaster.isEmpty()) {
                        Integer tdAccountMaster = em.createNativeQuery("UPDATE chbook_ca SET STATUSFLAG='U' WHERE ACNO='" + acNo + "' AND STATUSFLAG='F'").executeUpdate();
                        if (tdAccountMaster <= 0) {
                            ut.rollback();
                            return "DATA NOT updated";
                        }
                    }

                    Integer acctCloseHis = em.createNativeQuery("UPDATE acctclose_his SET closingdate='" + tmpbd + "' ,auth='Y',authby='" + enterBy + "' WHERE acno='" + acNo + "'").executeUpdate();
                    if (acctCloseHis <= 0) {
                        ut.rollback();
                        return "DATA NOT updated";
                    }

                }

            }
            ut.commit();
            if (closingFlag == 1) {
                return "AUTHORIZATION SUCCESSFUL AND BATCH NO. IS " + trSNo;
            } else {
                return "AUTHORIZATION SUCCESSFUL";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getListDetails(String brncode) {
        List list1 = em.createNativeQuery("SELECT acno,TXNID from loan_oldinterest where substring(acno,1,2)='" + brncode + "' and authby is null or authby='' ORDER BY TXNID").getResultList();
        return list1;
    }

    public List getTableData(String acNo, String txnId) {
        List list1 = em.createNativeQuery("SELECT am.CUSTNAME, am.ACNO, coalesce(ifnull(li.roi,0),'') , coalesce(am.intDeposit,''),round((ifnull(li.penalroi,0)*12)-ifnull(li.roi,0),2),"
                + "round((am.penalty*12)-am.intDeposit,2),coalesce(li.aclimit,''),coalesce(am.odLimit,'') ,coalesce(li.adhocinterest,''),am.AdhocInterest, "
                + "coalesce(li.adhoclimIt,''),coalesce(lp.adhoclimit,'') as adhoclimit,coalesce(replace(date_format(li.AdhocApplicableDt,'%d/%m/%Y'),'1900-01-01',''),''), "
                + "coalesce(replace(date_format(lp.AdhocApplicableDt,'%d/%m/%Y'),'1900-01-01',''),'') as AdhocApplicableDt,"
                + "coalesce(replace(date_format(li.adhoctilldt,'%d/%m/%Y'),'01/01/1900',''),'') as ADHOCTILLDTOLD,"
                + "coalesce(replace(date_format(am.adhoctilldt,'%d/%m/%Y'),'01/01/1900',''),'') as adhoctilldt,"
                + "coalesce (li.SubsidyAmt,''),coalesce(lp.SubsidyAmt,''),coalesce(replace(date_format(li.SubsidyExpDt,'%d/%m/%Y'),'01/01/1900',''),'') as SUBSIDYEXPDTOLD,"
                + "coalesce(replace(date_format(li.SubsidyExpDt,'%d/%m/%Y'),'01/01/1900',''),'') as SubsidyExpDt,li.MaxLimit ,lp.MaxLimit,am.ENTEREDBY,li.ENTERBY  "
                + "FROM accountmaster am, loan_oldinterest li,loan_appparameter lp where am.acno=li.acno AND lp.ACNO=am.ACNO AND "
                + "li.ACNO='" + acNo + "'AND li.TXNID='" + txnId + "' and li.authby is null").getResultList();
        return list1;
    }

    public String btnAuthorize(String acNo, String txnId, String user) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q1 = em.createNativeQuery("Update loan_oldinterest set authby = '" + user + "' where acno = '" + acNo + "' AND TXNID='" + txnId + "'");
            int uu = q1.executeUpdate();
            if (uu > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    /* End of Account Loan Limit Authorization*/
    /* Start of Account Status Authorization*/

    public List tableAuthorize(String orgnCode) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("Select am.acno,remark,date_format(dt,'%d/%m/%Y'),c.description,ast.auth,ast.authby,ast.enterby,"
                    + "BaseAcno,date_format(EffDt,'%d/%m/%Y'),spflag,spno,d.Description, am.custname,am.accstatus from accountstatus ast, "
                    + "accountmaster am, codebook c, codebook d where am.acno= ast.acno and d.groupcode='3' and accstatus= d.code and c.groupcode='3' "
                    + "and spflag= c.code and auth = 'N' and substring(ast.acno,1,2)='" + orgnCode + "'"
                    + "union "
                    + "Select am.acno,remark,date_format(dt,'%d/%m/%Y'),c.description,ast.auth,ast.authby,ast.enterby,BaseAcno,date_format(EffDt,'%d/%m/%Y'),"
                    + "spflag,spno,d.Description,am.custname,am.accstatus from accountstatus ast, td_accountmaster am, codebook c, codebook d where "
                    + "am.acno= ast.acno and d.groupcode='3' and accstatus= d.code and c.groupcode='3' and spflag= c.code and auth = 'N' "
                    + "and substring(ast.acno,1,2)='" + orgnCode + "'");
            tableResult = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return tableResult;
    }

    public String getAccStatusDesc(String newStatus) throws ApplicationException {
        try {
            List varlist = em.createNativeQuery("Select description From codebook where groupcode='3' and code='" + newStatus + "'").getResultList();
            Vector res = (Vector) varlist.get(0);
            return res.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String tansferStatus(String pStatus, String acNo, String spFlag, String auth1, String dt, String genAcno, String orgnBrId)
            throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String result = "";
            String dt1 = "";
            float trfamt = 0;
            float trsNo = 0;
            float recNo;
            String details = "";
            String transferStatus = "";
            String acctNature = "";
            String details1 = "";
            String acCode = fts.getAccountCode(acNo);
            if (pStatus.equalsIgnoreCase("11") || pStatus.equalsIgnoreCase("12") || pStatus.equalsIgnoreCase("13") || pStatus.equalsIgnoreCase("14") || pStatus.equalsIgnoreCase("3")) {
                if (spFlag.equalsIgnoreCase("14") || spFlag.equalsIgnoreCase("3") || spFlag.equalsIgnoreCase("6")) {
                    List baseList = em.createNativeQuery("Select max(dt) from npa_recon where acno='" + acNo + "'").getResultList();
                    if (baseList.size() > 0) {
                        for (int i = 0; i < baseList.size(); i++) {
                            Vector recnoVect = (Vector) baseList.get(i);
                            dt1 = recnoVect.get(0).toString();
                        }
                    }

                    if (dmy.parse(dt1).before(dmy.parse(dt))) {
                        List acctType = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acCode + "'").getResultList();
                        if (acctType.size() > 0) {
                            Vector eleType = (Vector) acctType.get(0);
                            acctNature = eleType.get(0).toString();
                        }
                        double intAmt = 0d;
                        List<LoanIntCalcList> intAmtList = interestCalculationBean.cbsLoanIntCalc("I", acctNature, acNo, result, dt1, dt, "", auth1);
                        if (intAmtList.size() == 1) {
                            for (LoanIntCalcList it : intAmtList) {
                                if (it.getFlag() != null) {
                                    if ((intAmtList.size() == 1 && it.getFlag().substring(0, 5).equalsIgnoreCase("false"))) {
                                        ut.rollback();
                                        return it.getFlag().substring(6);
                                    }
                                } else {
                                    intAmt = it.getTotalInt();
                                }
                            }
                        }
                        if (intAmt > 0f) {
                            details1 = "INT. UP TO" + ddmy.parse(dt);
                            trsNo = fts.getTrsNo();
                            Integer OFF_ReconInsert = em.createNativeQuery("Insert into  npa_recon(ACNo,Ty,Dt,ValueDt,DrAmt,CrAmt,TranType,Details,iy,EnterBy,auth,Payby,Authby,Trantime,TranDesc,IntAmt,recno,org_brnid,dest_brnid)"
                                    + "VALUES('" + acNo + "',1,'" + ymd.parse(dt) + "','" + ymd.parse(dt) + "'," + intAmt + ",0,8,'" + details1 + "',1,'" + auth1 + "','Y',3,'SYSTEM',CURRENT_TIMESTAMP,3," + intAmt + "," + trsNo + ",'" + orgnBrId + "','" + acNo.substring(0, 2) + "')").executeUpdate();
                            if (OFF_ReconInsert <= 0) {
                                ut.rollback();
                                result = "DATA NOT SAVED OF Npa_Recon";
                                return result;
                            }

                        }

                    }

                    List baseList1 = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from npa_recon where acno='" + acNo + "'").getResultList();
                    if (baseList1.size() > 0) {
                        Vector recnoVect = (Vector) baseList.get(0);
                        trfamt = Float.parseFloat(recnoVect.get(0).toString());
                    }
                    if (trfamt > 0) {
                        trsNo = fts.getTrsNo();
                        details = "TRF TO" + genAcno;
                        recNo = fts.getRecNo();
                        Integer OF_ReconInsert = em.createNativeQuery("Insert into  npa_recon(ACNo,Ty,Dt,ValueDt,DrAmt,CrAmt,TranType,Details,iy,EnterBy,auth,Authby,Trantime,TranDesc,IntAmt,recno,trsno,org_brnid,dest_brnid)"
                                + "VALUES('" + acNo + "',0,'" + dt + "','" + dt + "',0," + trfamt + ",2,'" + details + "',1,'" + auth1 + "','Y','SYSTEM',CURRENT_TIMESTAMP,9," + trfamt + "," + recNo + "," + trsNo + ",'" + orgnBrId + "','" + acNo.substring(0, 2) + "')").executeUpdate();
                        if (OF_ReconInsert <= 0) {
                            ut.rollback();
                            result = "DATA NOT SAVED OF OF_Recon";
                            return result;
                        }

                        details = "TRF From" + acNo;
                        Integer ReconInsert = em.createNativeQuery("Insert into  npa_recon(ACNo,Ty,Dt,ValueDt,DrAmt,CrAmt,TranType,Details,iy,EnterBy,auth,Authby,Trantime,TranDesc,IntAmt,recno,trsno,org_brnid,dest_brnid)"
                                + "VALUES('" + genAcno + "',1,'" + dt + "','" + dt + "'," + trfamt + ",0,2,'" + details + "',1,'" + auth1 + "','Y','SYSTEM',CURRENT_TIMESTAMP,3," + trfamt + "," + recNo + "," + trsNo + ",'" + orgnBrId + "','" + acNo.substring(0, 2) + "')").executeUpdate();
                        if (ReconInsert <= 0) {
                            ut.rollback();
                            result = "DATA NOT SAVED OF OF_Recon";
                            return result;
                        }
                    }
                }
            }
            transferStatus = "True";
            result = transferStatus;
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String authorizeActionAccountStatus(Long spNo, String acNo, String effDt, String spFlag,
            String authBy, String brcode, String dt, String oldStatus) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            String brnCode = acNo.substring(0, 2);
            String frDt = ymd.format(dmy.parse(effDt));
            String toDt = ymd.format(dmy.parse(dt));
            String accCode = fts.getAccountCode(acNo);
            String acctNature = fts.getAcNatureByCode(accCode);
            String acTable = fts.getAccountTable(acctNature);

            String statusDesc = "";
            if (spFlag.equalsIgnoreCase("4") || spFlag.equalsIgnoreCase("3") || spFlag.equalsIgnoreCase("6")) {
                statusDesc = "DOUBT FUL";
            } else {
                statusDesc = getAccStatusDesc(spFlag);
            }
            int pCodeNo = 0;
            List pOpt = em.createNativeQuery("Select ifnull(code,0) From parameterinfo_report Where reportname ='PIC_OPT'").getResultList();
            if (!pOpt.isEmpty()) {
                Vector pNo = (Vector) pOpt.get(0);
                pCodeNo = Integer.parseInt(pNo.get(0).toString());
            }
            List statusList = em.createNativeQuery("select accstatus from " + acTable + " where acno='" + acNo + "'").getResultList();
            if (statusList.isEmpty()) {
                throw new ApplicationException("Data does not exist for this AcNo");
            }
            Vector eleType = (Vector) statusList.get(0);
            String presentStatus = eleType.get(0).toString();
            //Deaf processing.
            if (spFlag.equalsIgnoreCase("15")) {
                List<UnclaimedAccountStatementPojo> unClaimedList = new ArrayList<UnclaimedAccountStatementPojo>();
                UnclaimedAccountStatementPojo pojo = new UnclaimedAccountStatementPojo();
                pojo.setAcNo(acNo);
                unClaimedList.add(pojo);

                String msg = fts.markUnClaimed(brnCode, acctNature, unClaimedList, frDt, authBy, "", fts.getAccountCode(acNo), "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Integer entryList = em.createNativeQuery("Update td_accountmaster Set AccStatus = '" + spFlag + "' where acno = '" + acNo + "'").executeUpdate();
                if (entryList <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            } else {
                int optStatus = 1;
                if (spFlag.equals("2")) {
                    optStatus = 2;
                }
                Integer entryList = em.createNativeQuery("Update accountmaster Set AccStatus = '" + spFlag + "',optstatus=" + optStatus + " where acno = '" + acNo + "'").executeUpdate();
                if (entryList <= 0) {
                    throw new ApplicationException("Problem in data updation");
                }
            }
            if (spFlag.equals("10")) {
//                if(!(acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)|| acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)))
//                    throw new ApplicationException("Saving and Current nature only allow for Lien Marking");
//                
                double osBalance = common.getBalanceOnDate(acNo, frDt);
                List lienAmtList = em.createNativeQuery("select amount,baseacno from accountstatus where acno='" + acNo + "' and spno=" + spNo).getResultList();
                if (lienAmtList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                Vector lienAmtVec = (Vector) lienAmtList.get(0);
                double lienAmt = Double.parseDouble(lienAmtVec.get(0).toString());
                String lienAcno = lienAmtVec.get(1).toString();
                if (osBalance < lienAmt) {
                    throw new ApplicationException("Lien Amount can not be greater than o/s Balance.");
                }
                //Code Commented 
//                List sNos = em.createNativeQuery("Select ifnull(max(sno),0)+1 From loansecurity Where acno='" + lienAcno + "'").getResultList();
//                Vector sNoss = (Vector) sNos.get(0);
//                String sNo = sNoss.get(0).toString();
//
//                List dataList = em.createNativeQuery(" Select COALESCE(IntDeposit,0),ifnull(RDMatDate,0),ifnull(OpeningDt,0) From accountmaster "
//                        + "Where acno='" + acNo + "'").getResultList();
//                if (dataList.isEmpty()) {
//                    throw new ApplicationException("Data does not exist in account master.");
//                }
//                Vector threevalues = (Vector) dataList.get(0);
//                String IntDeposits = threevalues.get(0).toString();
//                float IntDeposit = Float.parseFloat(IntDeposits);
//                String RDMatDate = threevalues.get(1).toString();
//                String OpeningDt = threevalues.get(2).toString();
//
//                Query insertQuery = em.createNativeQuery("Insert into loansecurity(Acno,Sno,Security,Particulars,MatDate,LienValue,matValue,IssueDate,"
//                        + "Status,Remarks,EnteredBy,entryDate,SecurityOption,SecurityChg,lienacno) Select '" + lienAcno + "','" + sNo + "','P','"
//                        + accCode + "','" + RDMatDate + "'," + lienAmt + "," + lienAmt + ", date_format('" + OpeningDt + "','%Y-%m-%d'),"
//                        + "'ACTIVE',CONCAT('DATED:SECURED ADVANCES:FIXED AND OTHER DEPOSITS(SPECIFY):', '" + acNo + "', '; ROI:' "
//                        + ", cast(" + IntDeposit + " as char(10)) , '; PresentAmt:' , cast(" + lienAmt + " as char(20))),UPPER('" + authBy + "'),"
//                        + "date_format('" + frDt + "','%Y-%m-%d'),SUBSTRING('" + acNo + "',3,2),'LIEN','" + acNo + "' From accountmaster Where Acno='" + acNo + "'");
//                int var = insertQuery.executeUpdate();
//                if (var <= 0) {
//                    throw new ApplicationException("Problem in data inserting.");
//                }
            }
            //NPCI Aadhar deactivation.
            if (spFlag.equals("4") && (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                String[] arr = getAcnoAadharDetails(acNo);

                List list = em.createNativeQuery("select cust_id from cbs_aadhar_registration where reg_type='AD' and "
                        + "status='R' and cust_id='" + arr[0] + "' and aadhar_no='" + arr[1] + "'").getResultList();
                if (!list.isEmpty() && arr[2].equalsIgnoreCase(acNo)) {
                    npciRemote.aadharDeactivation(arr[0], arr[1], "CA", "I", "AD", authBy);
                }
            }
            //NPCI Aadhar activation.
            if (oldStatus.equals("4") && spFlag.equals("1") && (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                    || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                String[] arr = getAcnoAadharDetails(acNo);

                List list = em.createNativeQuery("select cust_id from cbs_aadhar_registration where reg_type='AD' and "
                        + "(status='E' or status='D') and mapping_status='I' and cust_id='" + arr[0] + "' and "
                        + "aadhar_no='" + arr[1] + "'").getResultList();
                if (!list.isEmpty() && arr[2].equalsIgnoreCase(acNo)) {
                    npciRemote.aadharDeactivation(arr[0], arr[1], "MU", "", "AD", authBy);
                }
            }


            String npaAcNo = "";
            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                if (spFlag.equalsIgnoreCase("1") || spFlag.equalsIgnoreCase("11") || spFlag.equalsIgnoreCase("12") || spFlag.equalsIgnoreCase("13")
                        || spFlag.equalsIgnoreCase("14") || spFlag.equalsIgnoreCase("3") || spFlag.equalsIgnoreCase("6")) {

                    String dtColumn = "";
                    String recover = "CIP";

                    if (pCodeNo == 1 && (spFlag.equalsIgnoreCase("11") || spFlag.equalsIgnoreCase("12") || spFlag.equalsIgnoreCase("13")
                            || spFlag.equalsIgnoreCase("14") || spFlag.equalsIgnoreCase("3") || spFlag.equalsIgnoreCase("6"))) {
                        recover = "PIC";
                    }
                    String autoNpaEnable = "N";
                    List autoNpaList = em.createNativeQuery("select ifnull(code,'N') from cbs_parameterinfo where name = 'AUTO-NPA'").getResultList();
                    if (!autoNpaList.isEmpty()) {
                        Vector autoNpaVect = (Vector) autoNpaList.get(0);
                        autoNpaEnable = autoNpaVect.get(0).toString().equalsIgnoreCase("Y") ? autoNpaVect.get(0).toString() : autoNpaEnable;
                    }
                    /*
                     * Interest going in NPA 
                     */
                    if (autoNpaEnable.equalsIgnoreCase("Y")) {
                        if ((presentStatus.equalsIgnoreCase("1")) && (spFlag.equalsIgnoreCase("11") || spFlag.equalsIgnoreCase("12") || spFlag.equalsIgnoreCase("13"))) {
                            //                        String frDt = null, toDt = null;
                            //                        List operDataExistList = em.createNativeQuery("select date_format(ifnull(a.EFFDT,now()),'%Y%m%d'), date_format(ifnull(a.dt,now()),'%Y%m%d') from accountstatus a, accountmaster am where am.acno = a.acno and am.acno='" + acNo + "' and a.auth = 'N'").getResultList();
                            //                        if (!operDataExistList.isEmpty()) {
                            //                            Vector operDataExistVect = (Vector) operDataExistList.get(0);
                            //                            frDt = operDataExistVect.get(0).toString();
                            //                            toDt = operDataExistVect.get(1).toString();
                            //                        }
                            double balGoingNpa = 0;
                            String npaDuration = "";
                            List<LoanAcDetailStatementPojo> resultList = getLoanGoingToNpa(acNo, frDt, toDt, brnCode);
                            if (resultList.size() > 0) {
                                balGoingNpa = resultList.get(0).getBalance();
                                npaDuration = resultList.get(0).getDate();
                            }

                            String msg = "";
                            List list = em.createNativeQuery("SELECT GLHEADINT,ifnull(GLHEADURI,''),ifnull(glheadname,'') FROM accounttypemaster WHERE ACCTCODE='" + accCode + "'").getResultList();
                            if (list.isEmpty()) {
                                msg = "Please define the Overdue Interest Reserve and Interest receivable Gl head.";
                                throw new ApplicationException(msg);
                            }
                            Vector element = (Vector) list.get(0);
                            String intGl = element.get(0).toString();
                            String uriGl = element.get(1).toString();
                            String oirHead = element.get(2).toString();

                            if (balGoingNpa > 0) {
                                /* Credit the Party Account */
                                float trsNo = fts.getTrsNo();
                                float recNo = fts.getRecNo();

                                if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                    Query q4 = em.createNativeQuery("INSERT INTO loan_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                            + "('" + acNo + "',0.0,'" + toDt + "','" + toDt + "',0," + balGoingNpa + ",0,2," + recNo + "," + trsNo + ","
                                            + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "','" + brnCode + "','" + brnCode + "')");
                                    q4.executeUpdate();

                                    msg = fts.updateBalance(acctNature, acNo, balGoingNpa, 0, "Y", "N");
                                    if (!msg.equalsIgnoreCase("TRUE")) {
                                        throw new ApplicationException(msg);
                                    }

                                } else {
                                    Query q4 = em.createNativeQuery("INSERT INTO ca_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID) VALUES "
                                            + "('" + acNo + "',0.0,'" + toDt + "','" + toDt + "',0," + balGoingNpa + ",0,2," + recNo + "," + trsNo + ","
                                            + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "','" + brnCode + "','" + brnCode + "')");
                                    q4.executeUpdate();

                                    msg = fts.updateBalance(acctNature, acNo, balGoingNpa, 0, "Y", "N");
                                    if (!msg.equalsIgnoreCase("TRUE")) {
                                        throw new ApplicationException(msg);
                                    }
                                }

                                recNo = fts.getRecNo();

                                String interestAcno = brnCode + intGl + "01";
                                Query q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                        + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                        + "('" + interestAcno + "',0.0,'" + toDt + "','" + toDt + "'," + balGoingNpa + ",0,1,2," + recNo + "," + trsNo + ","
                                        + "3,0,3,'INTT. TRF FOR MEM " + acNo + ":" + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "','" + brnCode + "','" + brnCode + "','','')");
                                q4.executeUpdate();
                                msg = fts.updateBalance("PO", brnCode + intGl + "01", 0, balGoingNpa, "Y", "N");
                                if (!msg.equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException(msg);
                                }
                                Query q5 = em.createNativeQuery("INSERT INTO npa_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                        + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY,INTAMT, ORG_BRNID, DEST_BRNID) VALUES "
                                        + "('" + acNo + "',0.0,'" + toDt + "','" + toDt + "'," + balGoingNpa + ",0,1,8," + recNo + "," + trsNo + ","
                                        + "3,0,3,'INTT. TRF FOR MEM " + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "'," + balGoingNpa + ",'" + brnCode + "','" + brnCode + "')");
                                q5.executeUpdate();
                                msg = fts.updateBalance(acctNature, acNo, 0, balGoingNpa, "Y", "N");
                                if (!msg.equalsIgnoreCase("TRUE")) {
                                    throw new ApplicationException(msg);
                                }

                                if (!oirHead.equals("") && !uriGl.equals("")) {
                                    recNo = fts.getRecNo();
                                    String oHead = brnCode + oirHead + "01";
                                    q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                            + "('" + oHead + "',0.0,'" + toDt + "','" + toDt + "',0,ABS(" + balGoingNpa + "),0,2," + recNo + "," + trsNo + ","
                                            + "3,0,3,'Int. Trf to Memo from " + acNo + ":" + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "','" + brnCode + "','" + brnCode + "','','')");
                                    q4.executeUpdate();
                                    msg = fts.updateBalance("PO", brnCode + oirHead + "01", Math.abs(balGoingNpa), 0, "Y", "N");
                                    if (!msg.equalsIgnoreCase("TRUE")) {
                                        throw new ApplicationException(msg);
                                    }

                                    recNo = fts.getRecNo();
                                    oHead = brnCode + uriGl + "01";
                                    q4 = em.createNativeQuery("INSERT INTO gl_recon(ACNO,BALANCE,DT,VALUEDT,DRAMT,CRAMT,TY,TRANTYPE,RECNO,TRSNO,PAYBY,IY,"
                                            + "TRANDESC,DETAILS,TRANTIME,AUTH,ENTERBY,AUTHBY, ORG_BRNID, DEST_BRNID,adviceNo,adviceBrnCode) VALUES "
                                            + "('" + oHead + "',0.0,'" + toDt + "','" + toDt + "',ABS(" + balGoingNpa + "),0,1,2," + recNo + "," + trsNo + ","
                                            + "3,0,3,'Int. Trf to Memo from " + acNo + ":" + npaDuration + "',CURRENT_TIMESTAMP,'Y','SYSTEM','" + authBy + "','" + brnCode + "','" + brnCode + "','','')");
                                    q4.executeUpdate();

                                    msg = fts.updateBalance("PO", brnCode + uriGl + "01", 0, Math.abs(balGoingNpa), "Y", "N");
                                    if (!msg.equalsIgnoreCase("TRUE")) {
                                        throw new ApplicationException(msg);
                                    }
                                }
                            }
                        }
                    }
                    if (spFlag.equalsIgnoreCase("1")) {
                        dtColumn = "stadt";
                    } else if (spFlag.equalsIgnoreCase("11")) {
                        dtColumn = "npadt";
                    } else if (spFlag.equalsIgnoreCase("12")) {
                        dtColumn = "dbtdt";
                    } else if (spFlag.equalsIgnoreCase("13") || spFlag.equalsIgnoreCase("6")) {
                        dtColumn = "dcdt";
                    } else if (spFlag.equalsIgnoreCase("14")) {
                        dtColumn = "pbdt";
                    } else if (spFlag.equalsIgnoreCase("3")) {
                        dtColumn = "sfdt";
                    }
                    String query = "update loan_appparameter set presentstatus = '" + statusDesc + "', npaAcNo='"
                            + npaAcNo + "', " + dtColumn + " ='" + frDt + "',recover ='" + recover + "' where acno = '" + acNo + "'";
                    Integer entryList = em.createNativeQuery(query).executeUpdate();
                    if (entryList <= 0) {
                        throw new ApplicationException("Problem in data updation");
                    }
                }
            }
            if(spFlag.equals("2")){
               if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)
                            || acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List list = em.createNativeQuery("select customerid,ifnull(aadhaar_no,'') as aadharNo,"
                                + "ifnull(aadhaar_lpg_acno,'') as aadharAcno from cbs_customer_master_detail cu,"
                                + "customerid id where cu.customerid = id.custid and id.acno = '" + acNo + "'").getResultList();
                        if (list.isEmpty()) {
                            throw new ApplicationException("Account detail is not found");
                        }
                        Vector ele = (Vector) list.get(0);
                        String custid = ele.get(0).toString();
                        String aadharNo = ele.get(1).toString();
                        String aadharMapAcno = ele.get(2).toString();

                        list = em.createNativeQuery("select cust_id from cbs_aadhar_registration where reg_type='AD' and "
                                + "status='R' and cust_id='" + custid + "' and aadhar_no='" + aadharNo + "'").getResultList();
                        if (!list.isEmpty() && aadharMapAcno.equalsIgnoreCase(acNo)) {
                            npciRemote.aadharDeactivation(custid, aadharNo, "CA", "I", "AD", authBy);
                        }
                    } 
            }
            
            //SMS registered account inoperative /operative Marking
            if (spFlag.equals("1") || spFlag.equals("2")) {
                String status = "";
                if(spFlag.equals("1")) status = "2";
                else status = "1";
                
                List list = em.createNativeQuery("select acno from mb_subscriber_tab where acno='" + acNo + "' and status='"+ status +"' and auth='Y' and "
                        + "auth_status='V'").getResultList();
                if (!list.isEmpty()) {
                    int rs = em.createNativeQuery("insert into mb_subscriber_tab_his select (select ifnull(max(txn_id),0)+1 from mb_subscriber_tab_his where "
                            + "acno='" + acNo + "'), ACNO,ACNO_TYPE,MOBILE_NO,STATUS,BILL_DEDUCTION_ACNO,CASH_CR_LIMIT,CASH_DR_LIMIT,TRF_CR_LIMIT,"
                            + "TRF_DR_LIMIT,CLG_CR_LIMIT,CLG_DR_LIMIT,PIN_NO,CREATED_DATE,ENTER_BY,AUTH,AUTH_BY,AUTH_STATUS,INTEREST_LIMIT,"
                            + "CHARGE_LIMIT,UPDATE_BY,UPDATE_DT from mb_subscriber_tab where acno='" + acNo + "'").executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in data insertion");
                    }

                    rs = em.createNativeQuery("update mb_subscriber_tab set STATUS = '" + spFlag + "', update_by='"+ authBy +"', update_dt=now() "
                            + "where acno='" + acNo + "'and auth='Y' and auth_status='V'").executeUpdate();
                    if (rs <= 0) {
                        throw new ApplicationException("Problem in data updation");
                    }
                }
            }
            
            Integer entryList1 = em.createNativeQuery("Update accountstatus set auth = 'Y',authby = '" + authBy + "', trantime=now() where spno="
                    + spNo + " and acno = '" + acNo + "' and spflag = '" + spFlag + "' and  Effdt = '" + frDt + "'").executeUpdate();
            if (entryList1 <= 0) {
                throw new ApplicationException("Problem in data updation");
            }
            if (!presentStatus.equalsIgnoreCase("1")) {
                String transferStatus = tansferStatus(presentStatus, acNo, spFlag, authBy, frDt, acNo, brcode);
                if (!transferStatus.equalsIgnoreCase("True")) {
                    throw new ApplicationException("Error Occured in tansferStatus ");
                }
            }
            ut.commit();
            return "True";

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

    public String[] getAcnoAadharDetails(String acNo) throws ApplicationException {
        String[] arr = new String[3];
        try {
            List list = em.createNativeQuery("select customerid,ifnull(aadhaar_no,'') as aadharNo,"
                    + "ifnull(aadhaar_lpg_acno,'') as aadharAcno from cbs_customer_master_detail cu,"
                    + "customerid id where cu.customerid = id.custid and id.acno = '" + acNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Account detail is not found");
            }
            Vector ele = (Vector) list.get(0);
            String custid = ele.get(0).toString();
            String aadharNo = ele.get(1).toString();
            String aadharMapAcno = ele.get(2).toString();

            arr[0] = custid;
            arr[1] = aadharNo;
            arr[2] = aadharMapAcno;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return arr;
    }
    /* End of Account Status Authorization*/

    public String acctStatusDeletion(Long spNo, String acNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Integer entryList1 = em.createNativeQuery("delete from accountstatus where spno=" + spNo + " and auth='N' and acno = '" + acNo + "'").executeUpdate();
            if (entryList1 <= 0) {
                throw new ApplicationException("Problem in data deletion");
            }
            ut.commit();
            return "True";
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
    /* Start of Account Edit TD Authorization*/

    public List accountNoList(String brCode) throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acno,TXNID from acedithistory where authby is null or authby='' and substring(acno,1,2) = '" + brCode + "' ORDER BY TXNID");
            acctNo = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acctNo;
    }

    public List accountNature(String acctType) throws ApplicationException {
        List acctNat = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acctType + "'");
            acctNat = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acctNat;
    }

    public List loadGrid(String acctNo, String txnId, String brCode) throws ApplicationException {
        List actDetail = new ArrayList();

        try {
            String acNature = fts.getAccountNature(acctNo);
            String curBrnCode = fts.getCurrentBrnCode(acctNo);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                actDetail = em.createNativeQuery("Select eh.Name 'CUSTOMERNAME',am.custName,d.description as 'OPERATIONMODENEW',c.description 'OPERATIONMODEOLD',e.DESCRIPTION 'ORGANISATION',f.DESCRIPTION,"
//                        + " coalesce(eh.introacno,''),coalesce(am.introAccno,''),coalesce(eh.fname,''),coalesce(cm.FatherName,''),eh.maddress 'MAILINGADDRESS',cm.CrAddress,"
//                        + " eh.paddress 'PERMANENTADDRESS',cm.PrAddress,coalesce(eh.phno,''),coalesce(cm.PhoneNo,''),coalesce(eh.panno,''),coalesce(cm.PanNo,''),eh.AppTDS 'APPLYTDS',"
//                        + " am.TDSFlag,eh.tdsdocu 'TDSDOCUMENTS',coalesce(am.TDSDetails,'') ,coalesce(eh.Nominee,''),coalesce(am.Nomination,''),coalesce(eh.Relationship,'') ,coalesce(am.Relationship,''),"
//                        + " coalesce(eh.jtname1,'') ,coalesce(am.JtName1,'') ,coalesce(eh.jtname2,'') ,coalesce(am.JtName2,'') ,coalesce(eh.jtname3,'') ,coalesce(am.JtName3,''),"
//                        + " coalesce(eh.gname,'') ,coalesce(cm.GrdName,''),coalesce(eh.ACinst,'') ,coalesce(am.Instruction,'') , am.ENTEREDBY 'ACENTEREDBYEDITEDBY',eh.ENTEREDBY,am.cust_type,eh.cust_type"
//                        + " From td_accountmaster am, acedithistory eh,td_customermaster cm,codebook c,codebook d,codebook e,codebook f"
//                        + " Where am.acno=eh.acno and (eh.authby is null or eh.authby='') and eh.acno = '" + acctNo + "' AND eh.TXNID=" + txnId + " and substring(am.acno,3,2)=cm.AcType "
//                        + " and substring(am.acno,11,2)=cm.agCode and substring(am.acno,5,6)=cm.Custno and am.OperMode = c.code and c.groupcode = 4"
//                        + " and eh.OperMode = d.code and d.groupcode = 4 And eh.OrgnCode = e.code and e.groupcode = 40 and am.OrgnCode = f.code and f.groupcode = 40 and cm.brncode='" + curBrnCode + "'").getResultList();
//            

                actDetail = em.createNativeQuery("Select eh.Name 'CUSTOMERNAME',am.custName,d.description as 'OPERATIONMODENEW',c.description 'OPERATIONMODEOLD',e.REF_DESC 'ORGANISATION',f.REF_DESC,"
                        + " coalesce(eh.introacno,''),coalesce(am.introAccno,''),coalesce(eh.fname,''),coalesce(cm.FatherName,''),eh.maddress 'MAILINGADDRESS',cm.CrAddress,"
                        + " eh.paddress 'PERMANENTADDRESS',cm.PrAddress,coalesce(eh.phno,''),coalesce(cm.PhoneNo,''),coalesce(eh.panno,''),coalesce(cm.PanNo,''),eh.AppTDS 'APPLYTDS',"
                        + " am.TDSFlag,eh.tdsdocu 'TDSDOCUMENTS',coalesce(am.TDSDetails,'') ,coalesce(eh.Nominee,''),coalesce(am.Nomination,''),coalesce(eh.Relationship,'') ,coalesce(am.Relationship,''),"
                        + " coalesce(eh.jtname1,'') ,coalesce(am.JtName1,'') ,coalesce(eh.jtname2,'') ,coalesce(am.JtName2,'') ,coalesce(eh.jtname3,'') ,coalesce(am.JtName3,''),"
                        + " coalesce(eh.gname,'') ,coalesce(cm.GrdName,''),coalesce(eh.ACinst,'') ,coalesce(am.Instruction,'') , am.ENTEREDBY 'ACENTEREDBYEDITEDBY',eh.ENTEREDBY,am.cust_type,eh.cust_type"
                        + " From td_accountmaster am, acedithistory eh,td_customermaster cm,codebook c,codebook d,cbs_ref_rec_type e,cbs_ref_rec_type f"
                        + " Where am.acno=eh.acno and (eh.authby is null or eh.authby='') and eh.acno = '" + acctNo + "' AND eh.TXNID=" + txnId + " and substring(am.acno,3,2)=cm.AcType "
                        + " and substring(am.acno,11,2)=cm.agCode and substring(am.acno,5,6)=cm.Custno and am.OperMode = c.code and c.groupcode = 4"
                        + " and eh.OperMode = d.code and d.groupcode = 4 And eh.OrgnCode = e.REF_CODE and e.REF_REC_NO = '021' and am.OrgnCode = f.REF_CODE and f.REF_REC_NO = '021' and cm.brncode='" + curBrnCode + "'").getResultList();




            } else {
//                actDetail = em.createNativeQuery("Select eh.Name 'CUSTOMERNAME',am.custName,d.DESCRIPTION as 'OPERATIONMODENEW',c.DESCRIPTION 'OPERATIONMODEOLD',e.DESCRIPTION 'ORGANISATION',f.DESCRIPTION,"
//                        + " coalesce(eh.introacno,'') ,coalesce(am.introAccno,''),coalesce(eh.fname,''),coalesce(cm.FatherName,''), eh.maddress 'MAILINGADDRESS',cm.CrAddress,"
//                        + " eh.paddress 'PERMANENTADDRESS',cm.PrAddress,coalesce(eh.phno,''),coalesce(cm.PhoneNo,''),coalesce(eh.panno,''),coalesce(cm.PanNo,''),coalesce(eh.Nominee,'') 'NOMINEE',"
//                        + " coalesce(am.Nomination,''),coalesce(eh.Relationship,''),coalesce(am.Relatioship,''),coalesce(eh.jtname1,''),coalesce(am.JtName1,'') ,coalesce(eh.jtname2,'') ,coalesce(am.JtName2,'') ,"
//                        + " coalesce(eh.jtname3,''),coalesce(am.JtName3,'') ,CASE eh.MinBalCharge WHEN 1 THEN 'YES' ELSE 'NO' END, "
//                        + " CASE am.MinBal WHEN 1 THEN 'YES' ELSE 'NO' END,coalesce(eh.gname,''),coalesce(cm.GrdName,'') ,coalesce(eh.ACinst,''),"
//                        + " coalesce(am.Instruction,''), am.ENTEREDBY 'ACCENTEREDBYEDITEDBY', eh.ENTEREDBY From accountmaster am, acedithistory eh,customermaster cm,"
//                        + " codebook c,codebook d,codebook e,codebook f Where am.acno=eh.acno and (eh.authby is null or eh.authby='') and eh.acno = '" + acctNo + "'"
//                        + " AND eh.TXNID=" + txnId + " and substring(am.acno,3,2)=cm.AcType and substring(am.acno,11,2)=cm.agCode and substring(am.acno,5,6)=cm.Custno"
//                        + " AND am.OperMode = c.code and c.groupcode = 4 and eh.OperMode = d.code and d.groupcode = 4 And eh.OrgnCode = e.code and e.groupcode = 6"
//                        + " and am.OrgnCode = f.code and f.groupcode = 6 and cm.brncode='" + curBrnCode + "'").getResultList();
//            
//                
                actDetail = em.createNativeQuery("Select eh.Name 'CUSTOMERNAME',am.custName,d.DESCRIPTION as 'OPERATIONMODENEW',c.DESCRIPTION 'OPERATIONMODEOLD',e.REF_DESC 'ORGANISATION',f.REF_DESC,"
                        + " coalesce(eh.introacno,'') ,coalesce(am.introAccno,''),coalesce(eh.fname,''),coalesce(cm.FatherName,''), eh.maddress 'MAILINGADDRESS',cm.CrAddress,"
                        + " eh.paddress 'PERMANENTADDRESS',cm.PrAddress,coalesce(eh.phno,''),coalesce(cm.PhoneNo,''),coalesce(eh.panno,''),coalesce(cm.PanNo,''),coalesce(eh.Nominee,'') 'NOMINEE',"
                        + " coalesce(am.Nomination,''),coalesce(eh.Relationship,''),coalesce(am.Relatioship,''),coalesce(eh.jtname1,''),coalesce(am.JtName1,'') ,coalesce(eh.jtname2,'') ,coalesce(am.JtName2,'') ,"
                        + " coalesce(eh.jtname3,''),coalesce(am.JtName3,'') ,CASE eh.MinBalCharge WHEN 1 THEN 'YES' ELSE 'NO' END, "
                        + " CASE am.MinBal WHEN 1 THEN 'YES' ELSE 'NO' END,coalesce(eh.gname,''),coalesce(cm.GrdName,'') ,coalesce(eh.ACinst,''),"
                        + " coalesce(am.Instruction,''), am.ENTEREDBY 'ACCENTEREDBYEDITEDBY', eh.ENTEREDBY From accountmaster am, acedithistory eh,customermaster cm,"
                        + " codebook c,codebook d,cbs_ref_rec_type e,cbs_ref_rec_type f Where am.acno=eh.acno and (eh.authby is null or eh.authby='') and eh.acno = '" + acctNo + "'"
                        + " AND eh.TXNID=" + txnId + " and substring(am.acno,3,2)=cm.AcType and substring(am.acno,11,2)=cm.agCode and substring(am.acno,5,6)=cm.Custno"
                        + " AND am.OperMode = c.code and c.groupcode = 4 and eh.OperMode = d.code and d.groupcode = 4 And eh.OrgnCode = e.REF_CODE and e.REF_REC_NO = '021'"
                        + " and am.OrgnCode = f.REF_CODE and f.REF_REC_NO = '021' and cm.brncode='" + curBrnCode + "'").getResultList();


            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actDetail;
    }

    public String authorizeAcEdit(String acno, String txnId, String enterBy, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0;
            if (acno == null || txnId == null || enterBy == null || authBy == null) {
                ut.rollback();
                return "PLEASE SELECT AGAIN FROM PENDING LIST OF A/C. NO.";
            }
            if (enterBy.equalsIgnoreCase(authBy)) {
                ut.rollback();
                return "SORRY,YOU CAN NOT AUTHORIZE YOUR OWN ENTRY !!!";
            }
            Query updateQuery = em.createNativeQuery("Update acedithistory set authby = '" + authBy + "',Auth = 'Y'  where acno = '" + acno + "' AND TXNID=" + txnId + "");
            var = updateQuery.executeUpdate();
            if ((var > 0)) {
                ut.commit();
                return "AUTHORIZATION SUCCESSFUL";
            } else {
                ut.rollback();
                return "AUTHORIZATION NOT COMPLETED.";
            }

        } catch (Exception e) {

            throw new ApplicationException(e);
        }

    }

    public List accountNoOpenList(String brCode) throws ApplicationException {
        List acctNo = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acno from accountmaster where (authby is null or authby ='') and CurBrCode = '" + brCode + "' union select acno from td_accountmaster where (authby is null or authby ='') and CurBrCode = '" + brCode + "'");
            acctNo = selectQuery.getResultList();

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acctNo;
    }

    public List loadGrid(String acctNo, String brCode) throws ApplicationException {
        List actDetail = new ArrayList();
        String acNature = "";
        try {
            List recList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode=substring('" + acctNo + "'" + ",3,2)").getResultList();
            if (!recList.isEmpty()) {
                Vector recLst = (Vector) recList.get(0);
                acNature = recLst.get(0).toString();
            }
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                actDetail = em.createNativeQuery("select am.acno,am.IntroAccno,coalesce((select custname from accountmaster where acno = (select introAccno from td_accountmaster where acno = '" + acctNo + "')"
                        + " union"
                        + " select custname from td_accountmaster where acno = (select introAccno from td_accountmaster where acno = '" + acctNo + "')),'') as 'IntroName',"
                        + " am.custname, am.JtName1,am.JtName2,am.EnteredBy,coalesce(c.grdname,''),coalesce(c.praddress,''),coalesce(c.craddress,''),coalesce(c.phoneno,''),coalesce(c.panno,''),coalesce(c.relation,''),date_format(cast(COALESCE(c.dob,'') as DATETIME),'%d/%m/%Y'),coalesce(b.REF_DESC,''),coalesce(c.fathername,''),d.description as 'opermode',"
                        + " coalesce(am.nomination,''),am.cust_type from td_accountmaster am , td_customermaster c, cbs_ref_rec_type b, codebook d Where (am.Authby Is Null or am.authby='') "
                        + " and (ltrim(rtrim(am.introaccno)) is not null or am.introaccno='') and substring(am.acno,3,2)=c.ACTYPE and substring(am.acno,5,6)=c.custno and "
                        + " substring(am.Acno, 11, 2) = c.agcode and am.acno='" + acctNo + "' and c.occupation = b.REF_CODE and b.REF_REC_NO = '021' and am.OPERMODE = d.code and d.groupcode = 4 and c.brncode='" + brCode + "'").getResultList();
            } else {
                actDetail = em.createNativeQuery("select am.acno,am.IntroAccno, coalesce((select custname from accountmaster where acno = (select introAccno from accountmaster where acno = '" + acctNo + "')),'') as 'IntroName', "
                        + " am.custname, coalesce(am.ODLimit,'0'), coalesce(am.JtName1,''), coalesce(am.JtName2,''), coalesce(am.EnteredBy,''),coalesce(c.grdname,''),coalesce(am.nomination,''),c.praddress,c.craddress,c.phoneno,c.panno,coalesce(c.relation,''),date_format(cast(COALESCE(c.dob,'') as DATETIME),'%d/%m/%Y'),b.REF_DESC as description,"
                        + " coalesce(c.fathername,''),coalesce(am.intdeposit,'0'),d.description as 'opermode',timestampdiff(MONTH,am.openingdt,ifnull(am.rdmatdate,'19000101')),am.rdinstal,date_format(ifnull(am.rdmatdate,'19000101'),'%d/%m/%Y') from accountmaster am, customermaster c , cbs_ref_rec_type b, codebook d Where (am.Authby Is Null or am.authby='')"
                        + " and (ltrim(rtrim(am.introaccno)) is not null or am.introaccno='') and substring(am.acno,3,2)=c.ACTYPE and substring(am.acno,5,6)=c.custno "
                        + " and substring(am.Acno, 11, 2) = c.agcode and am.acno='" + acctNo + "' and c.occupation = b.REF_CODE and b.REF_REC_NO='021' and am.opermode = d.code and d.groupcode = 4 and c.brncode='" + brCode + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return actDetail;
    }

    public String authorizeAcOpen(String acno, String enterBy, String authBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int var = 0, var1 = 0;
            if (acno == null || enterBy == null || authBy == null) {
                ut.rollback();
                return "PLEASE SELECT AGAIN FROM PENDING LIST OF A/C. NO.";
            }
            if (enterBy.equalsIgnoreCase(authBy)) {
                ut.rollback();
                return "SORRY,YOU CAN NOT PASS YOUR OWN ENTRY !!!";
            }

            String acType = fts.getAccountCode(acno);
            String acNat = "";
            List acNatLt = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (!acNatLt.isEmpty()) {
                Vector recLst = (Vector) acNatLt.get(0);
                acNat = recLst.get(0).toString();
            }

            if ((!acType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) && (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                    || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
//                if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
//                    List chkListEmi = em.createNativeQuery("select acno,STATUS from emidetails where acno = '" + acno + "'").getResultList();
//                    if (chkListEmi.isEmpty()) {
//                        ut.rollback();
//                        return "Please Fill Emi Details For This Account From Loan Account Details";
//                    }
//                }

                List chkList1 = em.createNativeQuery("SELECT LoanAuth From accounttypemaster where ACCTCODE = '" + acType + "'").getResultList();
                if (chkList1.isEmpty()) {
                    ut.rollback();
                    return "Invalid Account Type. Please consult System Administrator.";
                }
                Vector recLst1 = (Vector) chkList1.get(0);
                int authLen = recLst1.get(0).toString().length();
                if (authLen != 12) {
                    ut.rollback();
                    return "Invalid AuthCode for this Account Type. Please consult System Administrator.";
                }
                String loanAuth = recLst1.get(0).toString();
                char[] charArr = loanAuth.toCharArray();

                String guarantor = String.valueOf(charArr[1]);
                String emi = String.valueOf(charArr[2]);
                String loanMis = String.valueOf(charArr[3]);
                String legalDoc = String.valueOf(charArr[4]);
                String loanShare = String.valueOf(charArr[5]);
                String security = String.valueOf(charArr[6]);
                String disb = String.valueOf(charArr[7]);
                String insurance = String.valueOf(charArr[8]);
                String employment = String.valueOf(charArr[10]);

                String misDetAcNo = "";
                String misDetAuth = "";
                if (loanMis.equalsIgnoreCase("1")) {
                    List chkList4 = em.createNativeQuery("Select ACC_NO,ifnull(Auth,'') from cbs_loan_borrower_details where ACC_NO = '" + acno + "'").getResultList();
                    if (!chkList4.isEmpty()) {
                        for (int i = 0; i < chkList4.size(); i++) {
                            Vector recLst4 = (Vector) chkList4.get(i);
                            misDetAcNo = recLst4.get(0).toString();
                            misDetAuth = recLst4.get(1).toString();
                        }
                    }
                    if (misDetAcNo.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "MIS Details for this account is Absent.";
                    }
                    if (!misDetAuth.equalsIgnoreCase("Y")) {
                        ut.rollback();
                        return "MIS Details for this Account is not Authorized.";
                    }
                }

                String secDetAcNo = "";
                String secDetAuth = "";
                String secDescAcNo ="";
                if (security.equalsIgnoreCase("1")) {
                    List secListNew = em.createNativeQuery("Select ifnull(SECURED,'') from cbs_loan_borrower_details where ACC_NO ='" + acno + "'").getResultList();
                    if (!secListNew.isEmpty()) {
                        Vector secLstVec = (Vector) secListNew.get(0);
                        secDescAcNo = secLstVec.get(0).toString();
                    }else{
                        ut.rollback();
                        return "MIS Details for this account is Absent.";
                    }
                    
                    if(!secDescAcNo.equalsIgnoreCase("UNSEC")){
                        List chkList5 = em.createNativeQuery("Select Acno,ifnull(Auth,'') from loansecurity where Acno ='" + acno + "'").getResultList();
                        if (!chkList5.isEmpty()) {
                            for (int i = 0; i < chkList5.size(); i++) {
                                Vector recLst5 = (Vector) chkList5.get(i);
                                secDetAcNo = recLst5.get(0).toString();
                                secDetAuth = recLst5.get(1).toString();
                            }
                        }
                        if (secDetAcNo.equalsIgnoreCase("")) {
                            ut.rollback();
                            return "Security Details for this account is Absent.";
                        }
                        if (!secDetAuth.equalsIgnoreCase("Y")) {
                            ut.rollback();
                            return "Security Details for this Account is not Authorized.";
                        }
                    }
                }

                String guarDet = "";
                if (guarantor.equalsIgnoreCase("1")) {
                    List chkList6 = em.createNativeQuery("Select Acno from loan_guarantordetails where Acno ='" + acno + "'").getResultList();
                    if (!chkList6.isEmpty()) {
                        Vector recLst6 = (Vector) chkList6.get(0);
                        guarDet = recLst6.get(0).toString();
                    }
                    if (guarDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Guarantor Details for this account is Absent.";
                    }

                }

                String emiDet = "";
                if (emi.equalsIgnoreCase("1")) {
                    List chkList7 = em.createNativeQuery("Select Acno from emidetails where Acno ='" + acno + "'").getResultList();
                    if (!chkList7.isEmpty()) {
                        Vector recLst7 = (Vector) chkList7.get(0);
                        emiDet = recLst7.get(0).toString();
                    }
                    if (emiDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "EMI Details for this account is Absent.";
                    }
                }

                String legalDocDet = "";
                if (legalDoc.equalsIgnoreCase("1")) {
                    List chkList8 = em.createNativeQuery("Select Acno from loanlegalcode where Acno ='" + acno + "'").getResultList();
                    if (!chkList8.isEmpty()) {
                        Vector recLst8 = (Vector) chkList8.get(0);
                        legalDocDet = recLst8.get(0).toString();
                    }
                    if (legalDocDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Details related to Legal Documents for this account is Absent.";
                    }
                }

                String loanShareDet = "";
                if (loanShare.equalsIgnoreCase("1")) {
                    List chkList9 = em.createNativeQuery("Select Acno from loan_share where Acno ='" + acno + "'").getResultList();
                    if (!chkList9.isEmpty()) {
                        Vector recLst9 = (Vector) chkList9.get(0);
                        loanShareDet = recLst9.get(0).toString();
                    }
                    if (loanShareDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Loan Share Details for this account is Absent.";
                    }
                }

                String disbDet = "";
                if (disb.equalsIgnoreCase("1")) {
                    List chkList10 = em.createNativeQuery("Select Acno from loandisbursement where Acno ='" + acno + "'").getResultList();
                    if (!chkList10.isEmpty()) {
                        Vector recLst10 = (Vector) chkList10.get(0);
                        disbDet = recLst10.get(0).toString();
                    }
                    if (disbDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Loan Disbursement Details for this account is Absent.";
                    }

                }

                String insDet = "";
                if (insurance.equalsIgnoreCase("1")) {
                    List chkList11 = em.createNativeQuery("Select Acno from loan_insurance where Acno ='" + acno + "'").getResultList();
                    if (!chkList11.isEmpty()) {
                        Vector recLst11 = (Vector) chkList11.get(0);
                        insDet = recLst11.get(0).toString();
                    }
                    if (insDet.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Insurance Details for this account is Absent.";
                    }

                }

                String emphis = "";
                if (employment.equalsIgnoreCase("1")) {
                    List chkList12 = em.createNativeQuery("Select Acno from loan_employmenthistory where Acno ='" + acno + "'").getResultList();
                    if (!chkList12.isEmpty()) {
                        Vector recLst12 = (Vector) chkList12.get(0);
                        emphis = recLst12.get(0).toString();
                    }
                    if (emphis.equalsIgnoreCase("")) {
                        ut.rollback();
                        return "Employment Details for this account is Absent.";
                    }
                }
            }
            //   }
            //}
            /**
             * ADDED BY ROHIT FOR CA A/C. SECURITY CHECKING(ACCORDING TO ALOK
             * YADAV).ADDED ON DATE:29 MARCH 2011*
             */
//            if (acType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
//                String secDetAcNo = "";
//                String secDetAuth = "";
//                List chkSec = em.createNativeQuery("SELECT ODLimit FROM accountmaster WHERE ACNO='" + acno + "'").getResultList();
//                Vector chkSecV = (Vector) chkSec.get(0);
//                double tmpOdlimit = Double.parseDouble(chkSecV.get(0).toString());
//                if (tmpOdlimit > 0) {
//                    List chkList5 = em.createNativeQuery("Select Acno,ifnull(Auth,'') from loansecurity where Acno ='" + acno + "'").getResultList();
//                    if (!chkList5.isEmpty()) {
//                        for (int i = 0; i < chkList5.size(); i++) {
//                            Vector recLst5 = (Vector) chkList5.get(i);
//                            secDetAcNo = recLst5.get(0).toString();
//                            secDetAuth = recLst5.get(1).toString();
//                        }
//                    }
//                    if (secDetAcNo.equalsIgnoreCase("")) {
//                        ut.rollback();
//                        return "Security Details for this account is Absent.";
//                    }
//                    if (!secDetAuth.equalsIgnoreCase("Y")) {
//                        ut.rollback();
//                        return "Security Details for this Account is not Authorized.";
//                    }
//                }
//            }
            /**
             * END OF CA A/C. SECURITY CHECKING*
             */
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Query updateQuery = em.createNativeQuery("Update td_accountmaster set AuthBy='" + authBy + "' where acno='" + acno + "'");
                var1 = updateQuery.executeUpdate();
            } else {
                Query updateQuery = em.createNativeQuery("Update accountmaster set AuthBy = '" + authBy + "' where acno='" + acno + "'");
                var1 = updateQuery.executeUpdate();
                Query updateQuery1 = em.createNativeQuery("Update loan_appparameter set authby='" + authBy + "', auth='Y' where acno='" + acno + "'");
                var = updateQuery1.executeUpdate();
            }

            Integer code = fts.getCodeForReportName("AC-OPEN-SMS");

            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                if ((var1 > 0)) {
                    ut.commit();
                    //SMS Sending
                    try {
                        if (code == 1) {
                            sendAcOpenSms(acno);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem in sending sms of a/c opening. A/c-->"
                                + acno + "::Error is-->" + ex.getMessage());
                    }
                    //End Here
                    return "AUTHORIZATION SUCCESSFUL";
                } else {
                    ut.rollback();
                    return "AUTHORIZATION NOT COMPLETED.";
                }
            } else {
                if ((var1 > 0)) {
                    ut.commit();
                    //SMS Sending
                    try {
                        if (code == 1) {
                            sendAcOpenSms(acno);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem in sending sms of a/c opening. A/c-->"
                                + acno + "::Error is-->" + ex.getMessage());
                    }
                    //End Here
                    return "AUTHORIZATION SUCCESSFUL";
                } else {
                    ut.rollback();
                    return "AUTHORIZATION NOT COMPLETED.";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public void sendAcOpenSms(String acno) throws ApplicationException {
        try {
            List<MbSmsSenderBankDetailTO> bankTo = smsMgmtFacade.getBankAndSenderDetail();
            String templateBankName = bankTo.get(0).getTemplateBankName().trim();

            String mobileNo = messageDetailRemote.getMobileNumberByAcno(acno);
            if (mobileNo.length() == 10) {
                TransferSmsRequestTo tSmsRequestTo = new TransferSmsRequestTo();
                tSmsRequestTo.setMsgType("PAT");
                tSmsRequestTo.setTemplate(SmsType.AC_OPEN_SMS);
                tSmsRequestTo.setPromoMobile("+91" + mobileNo);
                tSmsRequestTo.setBankName(templateBankName);
                tSmsRequestTo.setPromoMsg(fts.getCustName(acno));
                tSmsRequestTo.setAcno(acno);
                tSmsRequestTo.setModuleName("NO-CHG");
                smsMgmtFacade.sendSms(tSmsRequestTo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /* End of Account Open Authorization*/
    /**
     * *******VERY VERY IMPORTANT MSG If any changes will be occurred in
     * npaPosting method Then same will be modify in FtsPostingMgmtFacade method
     * name is same *******
     */
    public String npaPosting(List<NpaAccountDetailPojo> npaList, String dt, String enterBy, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String msg = fts.npaPosting(npaList, dt, enterBy, orgnCode);
            if (msg.equalsIgnoreCase("Npa marking is successfull.")) {
                ut.commit();
            } else {
                throw new ApplicationException(msg);
            }
            return msg;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List<LoanAcDetailStatementPojo> getLoanGoingToNpa(String acNo, String fromDt, String toDt, String brCode) {
        List<LoanAcDetailStatementPojo> dataList = new ArrayList<LoanAcDetailStatementPojo>();
        double balance = 0, balCr = 0, balDr = 0;
        try {
            String lastRecdt = fromDt;
            List tempList = null;
            String acctNature = common.getAcNatureByAcNo(acNo);
            String tablename = common.getTableName(acctNature);
            if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {                
                /**
                 * ************Only for TERM
                 * LOAN*********************************** Getting the repayment
                 * due date which payment date is before TODT, Then consider it
                 * as Last Credit Dt
                 * *****************************************************************
                 */
                tempList = em.createNativeQuery("Select date_format(e.DUEDT,'%Y%m%d') from emidetails e where e.SNO = (select max(sno)+1 from emidetails where acno = '" + acNo + "' and paymentdt <='" + toDt + "' and status='PAID') and e.acno = '" + acNo + "'  limit 1").getResultList();
                if (!tempList.isEmpty()) {
                    Vector ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        /*IF due dt is before fromDt (LastCrDt) then LastCrDt will be DueDT*/
                        if (ymd.parse(ele.get(0).toString()).compareTo(ymd.parse(lastRecdt)) < 0) {
                            lastRecdt = ele.get(0).toString();
                        }
                    }
                }
            }
            /*Getting the credit date in recon before TODT,
             Then consider it as Last Credit Dt*/
            tempList = em.createNativeQuery("select date_format(max(dt),'%Y%m%d') from " + tablename + " where acno='" + acNo + "' and  cramt>0 and dt <= '" + toDt + "'").getResultList();
            if (!tempList.isEmpty()) {
                Vector ele = (Vector) tempList.get(0);
                if (ele.get(0) != null) {
                    /*IF creditDt is before LastCrDt then LastCrDt will be creditDt*/
                    if (ymd.parse(ele.get(0).toString()).compareTo(ymd.parse(lastRecdt)) < 0) {
                        lastRecdt = ele.get(0).toString();
                    }
                }
            }

            /* https://www.rbi.org.in/Scripts/BS_ViewMasCirculardetails.aspx?id=9908
             *  2.1.1 An asset, including a leased asset, becomes non­ performing when it ceases to generate income for the bank.
             2.1.2 A non ­performing asset (NPA) is a loan or an advance where;
             * i)    interest and/ or instalment of principal remain overdue for a period of more than 90 days in respect of a term loan, 
             * ii)   the account remains ‘out of order’ as indicated at paragraph 2.2 below, in respect of an Overdraft/Cash Credit (OD/CC), 
             * iii)  the bill remains overdue for a period of more than 90 days in the case of bills purchased and discounted, 
             * iv)   the instalment of principal or interest thereon remains overdue for two crop seasons for short duration crops,
             * v)    the instalment of principal or interest thereon remains overdue for one crop season for long duration crops,
             * vi)   the amount of liquidity facility remains outstanding for more than 90 days, in respect of a securitisation transaction undertaken in terms of guidelines on securitisation dated February 1, 2006.
             * vii)  in respect of derivative transactions, the overdue receivables representing positive mark-to-market value of a derivative contract, if these remain unpaid for a period of 90 days from the specified due date for payment.
             * 
             2.2 ‘Out of Order’ status
             *     An account should be treated as 'out of order' if the outstanding balance remains continuously in excess of the 
             * sanctioned limit/drawing power for 90 days. In cases where the outstanding balance in the principal operating account 
             * is less than the sanctioned limit/drawing power, but there are no credits continuously for 90 days as on the date of 
             * Balance Sheet or credits are not enough to cover the interest debited during the same period, these accounts should be 
             * treated as 'out of order'.
             * 
             4.2.4 Accounts with temporary deficiencies
             * The classification of an asset as NPA should be based on the record of recovery. 
             * Bank should not classify an advance account as NPA merely due to the existence of 
             * some deficiencies which are temporary in nature such as non­-availability of adequate 
             * drawing power based on the latest available stock statement, balance outstanding exceeding 
             * the limit temporarily, non­-submission of stock statements and non­-renewal of the limits on 
             * the due date, etc. In the matter of classification of accounts with such deficiencies banks 
             * may follow the following guidelines:
             * i) Banks should ensure that drawings in the working capital accounts are covered by the adequacy of current 
             * assets, since current assets are first appropriated in times of distress. Drawing power is required to be 
             * arrived at based on the stock statement which is current. However, considering the difficulties of large 
             * borrowers, stock statements relied upon by the banks for determining drawing power should not be older than 
             * three months. The outstanding in the account based on drawing power calculated from stock statements older 
             * than three months, would be deemed as irregular.
             * 
             * A working capital borrowal account will become NPA if such irregular drawings are permitted in the account 
             * for a continuous period of 90 days even though the unit may be working or the borrower's financial position 
             * is satisfactory.
             * 
             * ii) Regular and ad hoc credit limits need to be reviewed/ regularised not later than three months from the 
             * due date/date of ad hoc sanction. In case of constraints such as non-­availability of financial statements 
             * and other data from the borrowers, the branch should furnish evidence to show that renewal/ review of credit 
             * limits is already on and would be completed soon. In any case, delay beyond six months is not considered 
             * desirable as a general discipline. Hence, an account where the regular/ ad hoc credit limits have not been 
             * reviewed/ renewed within 180 days from the due date/ date of ad hoc sanction will be treated as NPA.


             */
//            if(ymd.parse(lastRecdt).compareTo(ymd.parse(toDt))<0) {
//                toDt = ymd.format(ymd.parse(lastRecdt));
//            } else {
//                fromDt = lastRecdt;
//            }

//            List balanceDrTotalList = em.createNativeQuery("select ifnull(sum(dramt),0) from "+tablename+" a,accountmaster b where a.acno='" + acNo + "' and a.dt <='" + toDt + "' and a.auth='Y' and a.acno=b.acno  and a.trandesc <>6").getResultList();
//            Vector balanceDrTotalVect = (Vector) balanceDrTotalList.get(0);
//            double balanceDrTotal = Double.parseDouble(balanceDrTotalVect.get(0).toString());
//            
//            List balancecrTotalList = em.createNativeQuery("select ifnull(sum(cramt),0) from "+tablename+" a,accountmaster b where a.acno='" + acNo + "' and a.dt <='" + toDt + "' and a.auth='Y' and a.acno=b.acno ").getResultList();
//            Vector balanceCrTotalVect = (Vector) balancecrTotalList.get(0);
//            double balanceCrTotal = Double.parseDouble(balanceCrTotalVect.get(0).toString());

//            if (balanceDrTotal>balanceCrTotal) {
            long dayDiff = common.dayDifference(lastRecdt, toDt);
            long stdDays =0l;
            List limitList = em.createNativeQuery("select distinct delinq_cycle,cast(days_past_due as unsigned) from "
                    + " cbs_scheme_delinquency_details where delinq_cycle in (select ref_code from "
                    + " cbs_ref_rec_type where ref_rec_no='207' and ref_code in('STD'))").getResultList();
            if(!limitList.isEmpty()) {
                Vector limitVec = (Vector) limitList.get(0);
                stdDays = Long.parseLong(limitVec.get(1).toString());
            } else {
                stdDays= 90;
            }
            if (dayDiff > stdDays) {
                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    /*Get the transaction list between both dates*/
                    List transList = em.createNativeQuery("select distinct date_format(a.dt,'%Y%m%d'), b.odlimit, ifnull(a.cramt,0) from " + tablename + " a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + lastRecdt + "' and '" + toDt + "' and a.auth='Y' and a.acno=b.acno  and a.trandesc <>6 order by a.dt").getResultList();
                    if (!transList.isEmpty()) {
                        for (int l = 0; l < transList.size(); l++) {
                            double adHocLimit = 0;
                            Vector tranVect = (Vector) transList.get(l);
                            double outStandingBal = Double.parseDouble(interestCalculationBean.outStandingAsOnDate(acNo, transList.get(0).toString())) * -1;
                            double odLimit = Double.parseDouble(tranVect.get(1).toString());
                            double crAmt = Double.parseDouble(tranVect.get(2).toString());
                            List adHocList = em.createNativeQuery("select Adhoclimit from loan_appparameter where acno = '" + acNo + "' and '" + transList.get(0).toString() + "' between Adhocapplicabledt and AdhocExpiry").getResultList();
                            if (!adHocList.isEmpty()) {
                                Vector adHocVect = (Vector) adHocList.get(0);
                                adHocLimit = Double.parseDouble(adHocVect.get(0).toString());
                            }
                            if ((outStandingBal < (odLimit + adHocLimit)) && (crAmt > 0)) {
                                /*If outstanding is less than odLimit in that duration, any Credit on that date then that date should be lastRecDt*/
                                lastRecdt = tranVect.get(0).toString();
                            }
                        }
                    }
                    /*Check the lastCrDt and toDt diff*/
                    dayDiff = common.dayDifference(lastRecdt, toDt);
                    if (dayDiff > stdDays) {
                        /*Get the sum of debit (interests/ changes) from recon between both dt*/
                        List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from " + tablename + " a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + lastRecdt + "' and '" + toDt + "' and a.auth='Y' and a.acno=b.acno  and a.trandesc in (3,4,5,7,8) and details not like '%INTT. REC FOR MEM%'").getResultList();
                        Vector balanceVect = (Vector) balanceList.get(0);
                        balDr = Double.parseDouble(balanceVect.get(0).toString());
                    } else {
                        balDr = 0;
                    }
                } else {
                    List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from " + tablename + " a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + lastRecdt + "' and '" + toDt + "' and a.auth='Y' and a.acno=b.acno  and a.trandesc in (3,4,5,7,8) and details not like '%INTT. REC FOR MEM%'").getResultList();
                    Vector balanceVect = (Vector) balanceList.get(0);
                    balDr = Double.parseDouble(balanceVect.get(0).toString());
                    double outStandingBal = Double.parseDouble(interestCalculationBean.outStandingAsOnDate(acNo, toDt)) * -1;
                    if(outStandingBal < balDr) {
                        balDr = outStandingBal;
                    }
                }
//                List recoverList = em.createNativeQuery("select recover from loan_appparameter where acno='" + acNo + "' ").getResultList();
//                Vector recoverVect = (Vector) recoverList.get(0);
//                String recover = recoverVect.get(0).toString();

//                balanceList = em.createNativeQuery("select ifnull(sum(cramt),0) from " + tablename + " a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + lastRecdt + "' and '" + toDt + "' and a.auth='Y' and a.acno=b.acno  ").getResultList();
//                balanceVect = (Vector) balanceList.get(0);
//                balCr = Double.parseDouble(balanceVect.get(0).toString());
//                if (recover.equalsIgnoreCase("PIC")) {
//                    balance = 0;
//                } else {
                balance = balDr;
//                }
            }
            LoanAcDetailStatementPojo pojo = new LoanAcDetailStatementPojo();
            pojo.setAcNo(acNo);
            pojo.setBalance(balance);
            pojo.setDate(lastRecdt.concat(" to ").concat(toDt));
            dataList.add(pojo);

//            }            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dataList;
    }
    public String borrowerChecking (String acno) {
        String misDetAcNo = "", msg="", misDetAuth = "",soc = "N";
        List borrChq = em.createNativeQuery(" select ifnull(code,'N') from cbs_parameterinfo where name = 'BANK_IS_SOCIETY';").getResultList();
        if (!borrChq.isEmpty()) {
            Vector vect = (Vector) borrChq.get(0);
            soc = vect.get(0).toString();
        }
        if (soc.equalsIgnoreCase("N")) {
            List chkList4 = em.createNativeQuery("Select ACC_NO,ifnull(Auth,'') from cbs_loan_borrower_details where ACC_NO = '" + acno + "'").getResultList();
            if (!chkList4.isEmpty()) {
                for (int i = 0; i < chkList4.size(); i++) {
                    Vector recLst4 = (Vector) chkList4.get(i);
                    misDetAcNo = recLst4.get(0).toString();
                    misDetAuth = recLst4.get(1).toString();
                }
            } else {
                msg = "Borrower Details for this account is Absent.Please fill the Borrower Deatils For account "+acno;
            }
            if (!misDetAuth.equalsIgnoreCase("Y")) {
                msg= "Borrower Details for this Account is not Authorized.Please Authorize Borrower Deatils For account "+acno;
            }
        }
        return msg;
    }
}
