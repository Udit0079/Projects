/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.dds;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.DDSDenominationGrid;
import com.cbs.dto.DateByComparator;
import com.cbs.dto.DdsTable;
import com.cbs.dto.InterestProvisionTable;
import com.cbs.dto.InterestSlabMasterTable;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.RdIntDetail;
import com.cbs.dto.ReceiptMasterTable;
import com.cbs.dto.TdIntDetail;
import com.cbs.dto.master.MultiAcCodeTo;
import com.cbs.dto.master.MultiGLTo;
import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.RbiReportFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.pojo.OtherUnclaimedDepositPojo;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
@Stateless(mappedName = "DDSManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DDSManagementFacade implements DDSManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote fts;
    @EJB
    private SbIntCalcFacadeRemote intRemote;
    @EJB
    private CommonReportMethodsRemote commonRemote;
    @EJB
    private LoanInterestCalculationFacadeRemote intLoanRemote;
    @EJB
    private TermDepositeCalculationManagementFacadeRemote tdRemote;
    @EJB
    AutoTermDepositRenewalRemote autoRenewRemote;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    RbiReportFacadeRemote rbiReportFacade;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    //SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat ymmd = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.##");

    public String getCashMode(String brncode) throws ApplicationException {
        String cashMode = "";
        try {
            List cashModeList = em.createNativeQuery("select cashmod from parameterinfo where brncode='" + brncode + "'").getResultList();
            if (!cashModeList.isEmpty()) {
                Vector element = (Vector) cashModeList.get(0);
                return element.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return cashMode;
    }

    public List getAcctType() throws ApplicationException {
        List list = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select acctcode,AcctDesc from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "'");
            list = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public List getAgents(String brCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct agcode,name from ddsagent where brncode = '" + brCode + "' AND status='A'");
            list = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    public String getAgentName(String agentCode, String brCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct name from ddsagent where "
                    + "brncode='" + brCode + "' and agcode='" + agentCode + "'");
            list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                return ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return "";
    }

    public List getAgentTransactions(String acTy, String agent, String orgnBrCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            String DsActNature = CbsConstant.DEPOSIT_SC;
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            Date d = new Date();
            String date = ymdFormat.format(d);
            Query selectQuery = em.createNativeQuery("SELECT * from dds_auth_d WHERE ifnull(auth,'N') = 'N' AND ifNull(tokenpaid,'N') = 'N' "
                    + "AND substring(acno,3,2) = '" + acTy + "' AND acno in (select acno from accountmaster where curbrcode ='" + orgnBrCode + "' and "
                    + "substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + DsActNature + "')) "
                    + "AND substring(DATE_FORMAT(dt,'%Y%m%d'),1,10)='" + date + "' AND "
                    + "substring(acno,11,2) = '" + agent + "' ORDER BY receiptno");
            list = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

//    public String updateAgentTransactions(String agentCode, String brCode, String authBy, DenominitionTable denominitionObj) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            Query updateQuery = em.createNativeQuery("UPDATE dds_auth_d "
//                    + "SET tokenpaid = 'Y', TOKENPAIDBY = '" + authBy + "' "
//                    + "WHERE acno in (select acno from accountmaster where curbrcode ='" + brCode + "' and "
//                    + "substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) AND "
//                    + "substring(acno,11,2) = '" + agentCode + "' AND "
//                    + "ifNull(tokenpaid,'N') = 'N'");
//            int updateResult = updateQuery.executeUpdate();
//            if (updateResult > 0) {
//                ut.commit();
//                return "Marked As Received";
//            } else {
//                ut.rollback();
//                return "Problem In Receiving";
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//    }
    public String updateAgentTransactions(String agentCode, String brCode, String authBy, List<DDSDenominationGrid> denominationTable, String gCashMode, String enterDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            if (gCashMode.equalsIgnoreCase("Y")) {
                if (denominationTable.isEmpty()) {
                    ut.rollback();
                    return "Please enter denomination correctly !!!";
                }

                float recNo = fts.getRecNo();

                for (DDSDenominationGrid olObj : denominationTable) {
                    double denVal = olObj.getDenoValue();
                    int denNoCnt = olObj.getDenoNo();
                    String oNFlg = olObj.getFlag();
                    String denoMsg = interBranchFacade.insertDenominationDetail(agentCode, recNo, enterDt, new BigDecimal(denVal),
                            denNoCnt, Integer.parseInt(olObj.getTy()), brCode, authBy, oNFlg);
                    if (!denoMsg.equalsIgnoreCase("true")) {
                        ut.rollback();
                        return "Insertion Problem in denomination_details !";
                    } else {
                        int cnVal = 0;
                        if (olObj.getTy().equalsIgnoreCase("0") || olObj.getTy().equalsIgnoreCase("4")) {
                            cnVal = denNoCnt;
                        } else if (olObj.getTy().equalsIgnoreCase("1") || olObj.getTy().equalsIgnoreCase("3")) {
                            cnVal = denNoCnt * -1;
                        }

                        String denUpdateMsg = interBranchFacade.updateOpeningDenomination(brCode, new BigDecimal(denVal), cnVal, enterDt, oNFlg);
                        if (!denUpdateMsg.equalsIgnoreCase("true")) {
                            ut.rollback();
                            return "Updation Problem in denomination_details !";
                        }
                    }
                }
            }

            Query updateQuery = em.createNativeQuery("UPDATE dds_auth_d "
                    + "SET tokenpaid = 'Y', TOKENPAIDBY = '" + authBy + "' "
                    + "WHERE acno in (select acno from accountmaster where curbrcode ='" + brCode + "' and "
                    + "substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) AND "
                    + "substring(acno,11,2) = '" + agentCode + "' AND "
                    + "ifNull(tokenpaid,'N') = 'N'");
            int updateResult = updateQuery.executeUpdate();
            if (updateResult > 0) {
                ut.commit();
                return "Marked As Received";
            } else {
                ut.rollback();
                return "Problem In Receiving";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPendingAgents(String orgnBrCode, Boolean mode) throws ApplicationException {
        List list = new ArrayList();
        try {
            //String cashMode = getCashMode(orgnBrCode);
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            Date d = new Date();
            String date = ymdFormat.format(d);
            if (mode == true) {
                Query selectQuery = em.createNativeQuery("select distinct substring(acno,11,2) from dds_auth_d WHERE ifnull(AUTH,'N') in ('N','') "
                        + "and acno in (select acno from accountmaster where curbrcode ='" + orgnBrCode + "' and "
                        + "substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) AND "
                        + "ifnull(tokenpaid,'N') = 'N' AND substring(DATE_FORMAT(dt,'%Y%m%d'),1,10) = '" + date + "'");
                list = selectQuery.getResultList();
            } else {
                Query selectQuery = em.createNativeQuery("select distinct substring(acno,11,2) from dds_auth_d WHERE ifnull(AUTH,'N') in ('N','') and "
                        + "acno in (select acno from accountmaster where curbrcode ='" + orgnBrCode + "' and substring(acno,3,2) "
                        + "in (select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) AND ifnull(tokenpaid,'N') = 'Y' AND "
                        + "substring(DATE_FORMAT(dt,'%Y%m%d'),1,10) = '" + date + "'");
                list = selectQuery.getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return list;
    }

    /**
     * ****************************DDSAgentMasterBean's Methods
     *
     ******************************************
     * @param agentCode
     * @param name
     * @param address
     * @param phone
     * @param opdate
     * @param status
     * @param brncode
     * @param remarks
     * @param enteredBy
     * @param password
     * @param acno
     * @return
     * @throws com.cbs.exception.ApplicationException
     */
    public String saveData(String agentCode, String name, String address, String phone, String opdate,
            String status, String remarks, String brncode, String enteredBy, String password, String acno) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int count = em.createNativeQuery("insert into ddsagent values('" + agentCode + "','" + name + "',"
                    + "'" + address + "','" + phone + "','" + opdate + "','" + status + "','" + remarks + "',"
                    + "'" + brncode + "','" + enteredBy + "',curdate(),'',now(),'" + password + "','" + acno + "')").executeUpdate();
            if (count > 0) {
                msg = "Data has been Saved successfully !!";
                ut.commit();
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (Exception ex) {
                throw new ApplicationException(ex);
            }
        }
        return msg;
    }

    public String getMaxCode(String brCode) throws ApplicationException {
        try {
            List code = em.createNativeQuery("select ifnull(max(Agcode),0) from ddsagent where brncode='" + brCode + "'").getResultList();
            if (code.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Vector vtr = (Vector) code.get(0);
            return vtr.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<DdsTable> viewData(String brncode) throws ApplicationException {
        List<DdsTable> returnList = new ArrayList<DdsTable>();
        try {
            List resultList = em.createNativeQuery("select agcode,name,address,phno,remarks,status,agent_password,agent_acno from ddsagent where brncode='"
                    + brncode + "' order by agcode").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    DdsTable grid = new DdsTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (ele.get(0) != null) {
                        grid.setAgentCode(ele.get(0).toString());
                    }
                    if (ele.get(1) != null) {
                        grid.setName(ele.get(1).toString());
                    }
                    if (ele.get(2) != null) {
                        grid.setAddress(ele.get(2).toString());
                    }
                    if (ele.get(3) != null) {
                        grid.setPhone(ele.get(3).toString());
                    }
                    if (ele.get(4) != null) {
                        grid.setRemarks(ele.get(4).toString());
                    }
                    if (ele.get(5) != null) {
                        String st = ele.get(5).toString();
                        if (st.equalsIgnoreCase("A")) {
                            grid.setStatus("Active");
                        } else if (st.equalsIgnoreCase("I")) {
                            grid.setStatus("Inactive");
                        } else if (st.equalsIgnoreCase("D")) {
                            grid.setStatus("Deleted");
                        }
                    }
                    grid.setPassword(ele.get(6).toString());
                    grid.setAgAcno(ele.get(7).toString());
                    returnList.add(grid);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public String updateData(String agentCode, String name, String address, String phone, String remarks,
            String status, String modBy, String brncode, String password, String acno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int res = em.createNativeQuery("update ddsagent set name='" + name + "',address='" + address + "',"
                    + "phno='" + phone + "',remarks='" + remarks + "', status='" + status + "', "
                    + "updateby='" + modBy + "', updatedt=now(), agent_password='" + password + "', agent_acno = '" + acno + "' where "
                    + "agcode='" + agentCode + "' and brncode='" + brncode + "'").executeUpdate();
            if (res > 0) {
                ut.commit();
                return "Data has been updated successfully !!";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SecurityException ex) {
                throw new ApplicationException(ex.getMessage());
            } catch (SystemException ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
        return null;
    }

    /**
     * ****************************InterestCalculation's
     * Methods******************************************
     */
    public List getCustomerDetails(String acno, String orgBrCode) throws ApplicationException {
        List resultList = null;
        try {
            resultList = em.createNativeQuery("select am.acno,am.custname,am.openingdt,DATE_FORMAT(rdmatdate,'%Y%m%d'),DATE_FORMAT(c.next_int_calc_dt,'%Y%m%d') from  accountmaster am, cbs_loan_acc_mast_sec c where am.acno='" + acno + "' and accStatus<>9 and am.acno = c.acno and curbrcode ='" + orgBrCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;

    }

//    public List getacopendate(String acno) throws ApplicationException {
//        List resultList = null;
//        try {
//            resultList = em.createNativeQuery("select openingDT from accountmaster where acno = '" + acno + "'").getResultList();
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return resultList;
//    }
    public String getBalance(String acNo) throws ApplicationException {
        NumberFormat nft = new DecimalFormat("0.00");
        String amount = "0.00";
        try {
            List totinstall = em.createNativeQuery("select balance from reconbalan where acno='" + acNo + "'").getResultList();
            if (totinstall.size() > 0) {
                Vector vtr1 = (Vector) totinstall.get(0);
                amount = nft.format(Double.parseDouble(vtr1.get(0).toString()));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return amount;
    }
//
//    public double projectedInttCalcultaion(String acno, Date to, Date from, double roi, double installment, int code) throws ApplicationException {
//        try {
//            
//            double interest = 0;
//            if (code == 0) {
//                interest = CbsUtil.round((product * roi / 1200), 0);
//            } else {
//                interest = CbsUtil.round((product * roi / 36500), 0);
//            }
//        } catch (Exception e) {
//        }
//
//    }

    public List<Double> interestCalcultaion(String acno, Date to, Date from, double panelty) throws ApplicationException {
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

            String brCode = fts.getCurrentBrnCode(acno);
            String bussnesDt = "";
            List tmpseries = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode ='" + brCode + "'").getResultList();

            if (!tmpseries.isEmpty()) {
                Vector elebal = (Vector) tmpseries.get(0);
                bussnesDt = elebal.get(0).toString();
            }

            String finYr = autoRenewRemote.getFinYear(brCode);
            String frmDT = finYr + "0401";
            String toDT = (Integer.parseInt(finYr) + 1) + "0331";

            double dsRoi = 0.0;
            Date lDate = to;
            //  UserTransaction ut = context.getUserTransaction();

//            List resultList = em.createNativeQuery("select openingdt,DATE_FORMAT(rdmatdate,'%Y%m%d'),intdeposit from accountmaster where acno ='" + acno + "'").getResultList();
            List resultList = em.createNativeQuery("select a.openingdt,DATE_FORMAT(a.rdmatdate,'%Y%m%d'),a.intdeposit,"
                    + " c.next_int_calc_dt from accountmaster a, cbs_loan_acc_mast_sec c where a.acno ='" + acno + "' "
                    + " and a.acno = c.acno").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Opening Date does not exist");
            }
            Vector vtr2 = (Vector) resultList.get(0);
            String opDate = vtr2.get(3).toString();
            String matDate = vtr2.get(1).toString();
            String opDt = vtr2.get(0).toString();

            boolean a = ymd.parse(matDate).after(ymd.parse(ymd.format(to)));
            int diff = 0;
            if (a) {
                diff = CbsUtil.monthDiff(ymd.parse(opDt), to);
            } else {
                diff = CbsUtil.monthDiff(ymd.parse(opDt), ymd.parse(matDate));
                lDate = ymd.parse(matDate);
            }

            resultList = em.createNativeQuery("select min(roi),ifnull(roi,0) from dds_slab where (" + diff + " between from_mon and to_mon) "
                    + " and actype ='" + fts.getAccountCode(acno) + "' and applicable_date = (select max(applicable_date) from dds_slab "
                    + " where actype ='" + fts.getAccountCode(acno) + "' and applicable_date <='" + opDt + "')").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Maturity Date does not exist");
            }
            vtr2 = (Vector) resultList.get(0);
            dsRoi = Double.parseDouble(vtr2.get(1).toString());

            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='DDS_PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }

            double balance = Double.parseDouble(getBalance(acno));
            double product = getProduct(opDt, ymd.format(lDate), acno, code);

            double roi = dsRoi - panelty;
            double interest = 0;
            if ((code == 0) || (code == 2)) {
                interest = CbsUtil.round((product * roi / 1200), 0);
            } else {
                interest = CbsUtil.round((product * roi / 36500), 0);
            }

            String strResult = autoRenewRemote.getTdsRateAndApplicableAmt(acno, bussnesDt);
            String[] strRsArr = strResult.split(":");
            String tdsFlag = strRsArr[0];

            double tdsRate = Double.parseDouble(strRsArr[1]);

            double tdsApplicableAmt = Double.parseDouble(strRsArr[2]);

            List selectQueryInttPaid = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from dds_interesthistory where acno='" + acno + "'").getResultList();
            double totalIntPaid = 0;

            if (!selectQueryInttPaid.isEmpty()) {
                Vector Intpaid = (Vector) selectQueryInttPaid.get(0);
                totalIntPaid = Double.parseDouble(Intpaid.get(0).toString());
            }

            interest = interest - totalIntPaid;

            List selectQueryFInttPaid = em.createNativeQuery("select ifnull(sum(ifnull(interest,0)),0) from dds_interesthistory where acno='" + acno + "' and dt between '" + frmDT + "' and '" + toDT + "'").getResultList();
            double finYearTotalIntPaid = 0;

            if (!selectQueryFInttPaid.isEmpty()) {
                Vector finIntpaid = (Vector) selectQueryFInttPaid.get(0);
                finYearTotalIntPaid = Double.parseDouble(finIntpaid.get(0).toString());
            }

            List selectQuerytdsLast = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory where acno='" + acno + "' AND dt < '" + frmDT + "'").getResultList();
            double lastYearTdsDeducted = 0;
            if (selectQuerytdsLast.size() > 0) {
                Vector last = (Vector) selectQuerytdsLast.get(0);
                lastYearTdsDeducted = Double.parseDouble(last.get(0).toString());
            }

            List selectQuerytdsDeducted = em.createNativeQuery("SELECT ifnull(SUM(ifnull(TDS,0)),0) FROM tdshistory WHERE acno='" + acno + "' AND dt between '" + frmDT + "' and '" + toDT + "'").getResultList();
            double finYearTdsDeducted = 0;
            if (selectQuerytdsDeducted.size() > 0) {
                Vector tdsDeduct = (Vector) selectQuerytdsDeducted.get(0);
                finYearTdsDeducted = Double.parseDouble(tdsDeduct.get(0).toString());
            }

            int ddsPymntFlag = fts.getCodeForReportName("DDS-PAYMENT-SBINT");
            if (ddsPymntFlag == 1) {
                String sbIntTabCode = "";
                List setIntTableCode = em.createNativeQuery("SELECT ifnull(Code,'') FROM cbs_parameterinfo where name ='SAVING_INTTABLE_CODE'").getResultList();
                if (setIntTableCode.size() > 0) {
                    Vector intTabCodeVec = (Vector) setIntTableCode.get(0);
                    sbIntTabCode = intTabCodeVec.get(0).toString();
                }
                List<SavingIntRateChangePojo> dataList = intRemote.getSavingRoiChangeDetail(sbIntTabCode, matDate, ymd.format(new Date()));
                if (dataList.isEmpty()) {
                    throw new ApplicationException("There is no slab for saving interest calculation.");
                }
                double fnlTmpInt = balance + interest;
                double ddsSavingIntTot = 0;
                for (int k = 0; k < dataList.size(); k++) {
                    double rateOfInt = 0;
                    Long dDiff = null;
                    SavingIntRateChangePojo obj = dataList.get(k);
                    String slabFrDt = obj.getFrDt();
                    String slabToDt = obj.getToDt();
                    rateOfInt = obj.getRoi();
                    dDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
                    double sInt = (fnlTmpInt * rateOfInt * dDiff / 36500);
                    ddsSavingIntTot = ddsSavingIntTot + sInt;
                }
                interest = interest + ddsSavingIntTot;
            }

            double tdsToBeDeducted = 0;

            //double customerFinYearInt = autoRenewRemote.getFinYearIntOfCustomer(acno, ymd.format(from), ymd.format(to), "N") + interest;
            double customerFinYearInt = autoRenewRemote.getFinYearIntOfCustomer(acno, frmDT, toDT, "N") + interest;
            double closeFinYearCalculatedTds = 0;

            double closeFinYearTdsRecovered = 0;

            double closeFinYearIntOfCustomer = 0;

            double closeAcctTdsToBeDeduted = 0;

            if (tdsFlag.equalsIgnoreCase("Y") && (customerFinYearInt > tdsApplicableAmt)) {
                double tdsCal = Math.round((finYearTotalIntPaid + interest) * tdsRate / 100);
                tdsToBeDeducted = tdsCal - finYearTdsDeducted;

                //closeFinYearIntOfCustomer = autoRenewRemote.getFinYearIntOfCustomer(acno, ymd.format(from), ymd.format(to), "Y");
                closeFinYearIntOfCustomer = autoRenewRemote.getFinYearIntOfCustomer(acno, frmDT, toDT, "Y");
                closeFinYearCalculatedTds = Math.round(closeFinYearIntOfCustomer * tdsRate / 100);

                //closeFinYearTdsRecovered = autoRenewRemote.getCustomerFinYearTds(acno, ymd.format(from), ymd.format(to), "R", "Y");
                closeFinYearTdsRecovered = autoRenewRemote.getCustomerFinYearTds(acno, frmDT, toDT, "R", "Y");

                closeAcctTdsToBeDeduted = closeFinYearCalculatedTds - closeFinYearTdsRecovered;
                if (closeAcctTdsToBeDeduted < 0) {
                    closeAcctTdsToBeDeduted = 0;
                }
            }

            List<Double> result = new ArrayList<Double>();

            result.add(roi);
            result.add(balance);

            result.add(finYearTdsDeducted);
            result.add(lastYearTdsDeducted);
            result.add(tdsToBeDeducted);

            result.add(totalIntPaid);
            result.add(interest);

            result.add(closeAcctTdsToBeDeduted);
            result.add(closeFinYearTdsRecovered);
            result.add(closeFinYearIntOfCustomer);
            return result;

            //return roi + "#~" + interest + "#~" + balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getProduct(String fromDt, String toDt, String acno, int code) throws ApplicationException {

        try {
            double totProd = 0f;
            List<RdIntDetail> ddsIntDetailList = new ArrayList<RdIntDetail>();
            List ddsReconList = em.createNativeQuery("Select Ifnull(Cramt,0)- Ifnull(Dramt,0), date_format(dt,'%Y%m%d') from ddstransaction "
                    + "where acno = '" + acno + "' and dt >= '" + fromDt + "' and dt <= '" + toDt + "' and trantype <>8 and trandesc not in (10,33) group by dt order by dt").getResultList();
            if (ddsReconList.isEmpty()) {
                return CbsUtil.round((totProd), 0);
                //throw new ApplicationException("Data does not exist for interest calculation");
            }
            for (int i = 0; i < ddsReconList.size(); i++) {
                RdIntDetail ddsIntDetail = new RdIntDetail();
                Vector vect = (Vector) ddsReconList.get(i);
                ddsIntDetail.setDate(ymd.parse(vect.elementAt(1).toString()));
                ddsIntDetail.setBalance(Double.parseDouble(vect.elementAt(0).toString()));
                ddsIntDetailList.add(ddsIntDetail);
            }

            Collections.sort(ddsIntDetailList, new DateByComparator());
            int ctr1 = 1;
            int ctr2 = 0;
            String frDt = "";
            if (code == 0) {
                ctr2 = CbsUtil.monthDiff(ymd.parse(fromDt), ymd.parse(toDt));
                if (CbsUtil.datePart("D", fromDt) > 10) {
                    fromDt = CbsUtil.monthAdd(fromDt, 1) + "10";
                }
                frDt = fromDt.substring(0, 4) + fromDt.substring(4, 6) + "10";
            } else if (code == 2) {
                ctr2 = CbsUtil.monthDiff(ymd.parse(fromDt), ymd.parse(toDt));
                if (CbsUtil.datePart("D", fromDt) > 15) {
                    fromDt = CbsUtil.monthAdd(fromDt, 1) + "15";
                }
                frDt = fromDt.substring(0, 4) + fromDt.substring(4, 6) + "15";
            } else {
                frDt = fromDt;
                ctr2 = (int) CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(toDt)) + 1;
            }

            List balList;
            double sbal = 0d;
            String fDt = frDt;
            while (ctr1 <= ctr2) {
                String lDt = ymd.format(CbsUtil.getLastDateOfMonth(ymd.parse(frDt)));
                balList = em.createNativeQuery("Select Ifnull(Sum(Cramt),0) - Ifnull(Sum(Dramt),0) from ddstransaction "
                        + "where acno = '" + acno + "' and dt <= '" + frDt + "' and trantype <>8 ").getResultList();
                Vector vect = (Vector) balList.get(0);
                sbal = Double.parseDouble(vect.get(0).toString());

                double dbal1 = sbal;
                double dbal = 0d;

                if ((code == 0) || (code == 2)) {
                    int startIndex = getStartIndex(ddsIntDetailList, ymd.parse(frDt));
                    if (startIndex == -1) {
                        startIndex = 0;
                    }
                    int endIndex = getEndIndex(ddsIntDetailList, ymd.parse(lDt));
                    if (endIndex == -1) {
                        endIndex = ddsIntDetailList.size();
                    }

                    List<RdIntDetail> rdIntSubList = ddsIntDetailList.subList(startIndex, endIndex);
                    for (RdIntDetail rdIntDetail : rdIntSubList) {
                        dbal = rdIntDetail.getBalance();
                        dbal1 = dbal + dbal1;
                        if (dbal1 < sbal) {
                            sbal = CbsUtil.round(dbal, 2);
                        }
                    }
                }

                double prod;
                if (sbal < 0) {
                    prod = 0d;
                } else {
                    prod = sbal;
                }
                totProd = totProd + prod;
                if ((code == 0) || (code == 2)) {
                    frDt = CbsUtil.monthAdd(frDt, 1);
                } else {
                    frDt = CbsUtil.dateAdd(frDt, 1);
                }
                ctr1 = ctr1 + 1;
            }
            return CbsUtil.round((totProd), 0);
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public int getStartIndex(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            for (int i = 0; i < list.size(); i++) {
                RdIntDetail obj = list.get(i);
                if (obj.getDate().getTime() >= dt.getTime()) {
                    return i;
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public int getEndIndex(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            if (isExist(list, dt)) {
                for (int i = 0; i < list.size(); i++) {
                    RdIntDetail obj = list.get(i);
                    if (obj.getDate().getTime() == dt.getTime()) {
                        return ++i;
                    }
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    RdIntDetail obj = list.get(i);
                    if (obj.getDate().getTime() > dt.getTime()) {
                        return i;
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public boolean isExist(List<RdIntDetail> list, Date dt) throws ApplicationException {
        try {
            for (RdIntDetail obj : list) {
                if (dt.getTime() == obj.getDate().getTime()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

//    public String postInterest(String acno, String custname, String user, double intAmt) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            String dateString = sdf.format(new Date());
//            ut.begin();
//            List check = em.createNativeQuery("select * from recon_trf_d where acno='" + acno + "' and trantype=8 and dt='" + dateString + "'").getResultList();
//            if (check.size() > 0) {
//                throw new ApplicationException("Interest Already Posted to this Account");
//            }
//            check = em.createNativeQuery("select accstatus from accountmaster where acno='" + acno + "' and accstatus <> 1 ").getResultList();
//            if (check.size() > 0) {
//                Vector checkVect = (Vector) check.get(0);
//                String accStatus = checkVect.get(0).toString();
//                if(accStatus.equalsIgnoreCase("10")){
//                    throw new ApplicationException("Account is already Lien Marked");
//                } else {
//                    throw new ApplicationException("Please check the status of this account");
//                }                
//            }
//            boolean ddsProvision = false;
//            List list = em.createNativeQuery("SELECT code from parameterinfo_report where reportname='DDS PROVISION'").getResultList();
//            if (list.isEmpty()) {
//                ddsProvision = false;
//            } else {
//                Vector element = (Vector) list.get(0);
//                if (element.get(0) != null) {
//                    if (element.get(0).toString().equals("1")) {
//                        ddsProvision = true;
//                    } else {
//                        ddsProvision = false;
//                    }
//                } else {
//                    ddsProvision = false;
//                }
//            }
//            List glac = em.createNativeQuery("Select glheadint,glheadprov from accounttypemaster where acctcode='" + acno.substring(2, 4) + "'").getResultList();
//            if (glac.size() <= 0) {
//                throw new ApplicationException("No GL Head for Interest specified for DDS accounts");
//            }
//            List brnList = em.createNativeQuery("select curbrcode from accountmaster where acno ='" + acno + "'").getResultList();
//            if (brnList.isEmpty()) {
//                throw new ApplicationException("Error!! Account no does not exist");
//            }
//            Vector vec = (Vector) brnList.get(0);
//            String brncode = vec.get(0).toString();
//
//            Vector vtr1 = (Vector) glac.get(0);
//
//            String glIntHead = brncode + vtr1.get(0).toString() + "01";
//            String intHeadName = "N/A";
//            List acNameList = em.createNativeQuery("Select acname from gltable where acno='" + glIntHead + "'").getResultList();
//            if (!acNameList.isEmpty()) {
//                Vector nameVect = (Vector) acNameList.get(0);
//                intHeadName = nameVect.get(0).toString();
//            }
//
//            String glIntProv = "";
//            String intProvName = "N/A";
//            if (ddsProvision) {
//                if ((vtr1.get(0) == null || vtr1.get(1).toString().equals(""))) {
//                    throw new ApplicationException("Provision Head does not defined");
//                }
//                glIntProv = brncode + vtr1.get(1).toString() + "01";
//                List provNameList = em.createNativeQuery("Select acname from gltable where acno='" + glIntProv + "'").getResultList();
//                if (!provNameList.isEmpty()) {
//                    Vector nameVect = (Vector) provNameList.get(0);
//                    intProvName = nameVect.get(0).toString();
//                }
//            }
//            float trsNo = fts.getTrsNo();
//            float recNo = 0;
//
//            String drIntHead = glIntHead;
//            String drIntName = intHeadName;
//
//            String crIntHead = acno;
//            String crIntName = custname;
//
//            if (ddsProvision) {
//                double provInt = 0;
//                List intList = em.createNativeQuery("Select ifnull(sum(interest),0) from dds_interesthistory where acno='" + acno + "'").getResultList();
//                if (!intList.isEmpty()) {
//                    Vector vect = (Vector) intList.get(0);
//                    if (vect.get(0) != null || !vect.get(0).toString().equals("")) {
//                        provInt = Double.parseDouble(vect.get(0).toString());
//                    }
//                }
//                double remInt = intAmt - provInt;
//                if (remInt > 0) {
//                    drIntHead = glIntHead;
//                    drIntName = intHeadName;
//
//                    crIntHead = glIntProv;
//                    crIntName = intProvName;
//                }
//                if (remInt < 0) {
//                    crIntHead = glIntHead;
//                    crIntName = intHeadName;
//
//                    drIntHead = glIntProv;
//                    drIntName = intProvName;
//                }
//                if (remInt != 0) {
//                    recNo = fts.getRecNo();
//                    int j = em.createNativeQuery("Insert into recon_trf_d (acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,"
//                            + "enterby,auth,recno,payby,authby,trsno,trantime,org_brnid,dest_brnid,custname,adviceNo,adviceBrnCode) values "
//                            + "('" + drIntHead + "','1','" + dateString + "','" + dateString + "','" + Math.abs(remInt) + "',0,8,3,'Int Posting For DDS',0,'',"
//                            + "'" + user + "','N'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','" + drIntName + "','','')").executeUpdate();
//                    if (j == 0) {
//                        throw new ApplicationException("Problem in insertion in recon_trf_d");
//                    }
//                    String rs = fts.updateBalance(CbsConstant.PAY_ORDER, drIntHead, 0, Math.abs(remInt), "", "");
//                    if (!rs.equalsIgnoreCase("True")) {
//                        throw new ApplicationException("Problem in Balance updation");
//                    }
//                    recNo = fts.getRecNo();
//                    int i = em.createNativeQuery("Insert into recon_trf_d (acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,"
//                            + "enterby,auth,recno,payby,authby,trsno,trantime,org_brnid,dest_brnid,custname,adviceNo,adviceBrnCode) values"
//                            + " ('" + crIntHead + "','0','" + dateString + "','" + dateString + "',0," + Math.abs(remInt) + ",8,3,'Int Posting For DDS',0,"
//                            + " '','" + user + "','N'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','" + crIntName + "','','')").executeUpdate();
//                    if (i == 0) {
//                        throw new ApplicationException("Problem in insertion in recon_trf_d");
//                    }
//                }
//                drIntHead = glIntProv;
//                drIntName = intProvName;
//
//                crIntHead = acno;
//                crIntName = custname;
//            }
//
//            if (intAmt != 0) {
//                recNo = fts.getRecNo();
//                int j = em.createNativeQuery("Insert into recon_trf_d (acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,"
//                        + "enterby,auth,recno,payby,authby,trsno,trantime,org_brnid,dest_brnid,custname,adviceNo,adviceBrnCode) values ('" + drIntHead + "','1','" + dateString + "','" + dateString + "','" + intAmt + "',0,8,3,'Int Posting For DS',0,'',"
//                        + "'" + user + "','N'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','" + drIntName + "','','')").executeUpdate();
//                if (j == 0) {
//                    throw new ApplicationException("Problem in insertion in recon_trf_d");
//                }
//                String rs = fts.updateBalance(CbsConstant.PAY_ORDER, drIntHead, 0, intAmt, "", "");
//                if (!rs.equalsIgnoreCase("True")) {
//                    throw new ApplicationException("Problem in Balance updation");
//                }
//                recNo = fts.getRecNo();
//                int i = em.createNativeQuery("Insert into recon_trf_d (acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,"
//                        + "enterby,auth,recno,payby,authby,trsno,trantime,org_brnid,dest_brnid,custname,adviceNo,adviceBrnCode) values"
//                        + " ('" + crIntHead + "','0','" + dateString + "','" + dateString + "','0'," + intAmt + ",8,3,'Int Posting For DS',0,"
//                        + " '','" + user + "','N'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','" + crIntName + "','','')").executeUpdate();
//                if (i == 0) {
//                    throw new ApplicationException("Problem in insertion in recon_trf_d");
//                }
//            }
//            ut.commit();
//            return "Interest Posted Successfully! Generated Batch No. Is " + trsNo;
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//            throw new ApplicationException(e.getMessage());
//        }
//    }
    public String postInterest(String acno, String custname, String user, double intAmt, double tTds, double cTds, String brnCode, String fromDt, String toDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String dateString = sdf.format(new Date());
            ut.begin();
            List check = em.createNativeQuery("select accstatus from accountmaster where acno='" + acno + "' and accstatus <> 1 ").getResultList();
            if (check.size() > 0) {
                Vector checkVect = (Vector) check.get(0);
                String accStatus = checkVect.get(0).toString();
                if (accStatus.equalsIgnoreCase("10")) {
                    throw new ApplicationException("Account is already Lien Marked");
                } else {
                    throw new ApplicationException("Please check the status of this account");
                }
            }

            List glac = em.createNativeQuery("Select glheadint,glheadprov from accounttypemaster where acctcode='" + acno.substring(2, 4) + "'").getResultList();
            if (glac.size() <= 0) {
                throw new ApplicationException("No GL Head for Interest specified for DDS accounts");
            }

            Vector vtr1 = (Vector) glac.get(0);
            String glIntHead = brnCode + vtr1.get(0).toString() + "01";

            float recNo = 0;
            float trsNo = fts.getTrsNo();
            String acNat = fts.getAccountNature(acno);
            String sysUser = "System";
            if (intAmt != 0) {
                if (intAmt < 0) {
                    recNo = fts.getRecNo();
                    Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                            + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                            + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acno + "', 1,'" + dateString + "','" + dateString + "'," + intAmt * -1 + ""
                            + ",0,0,8,'Int Reversed for " + acno + "',1,'" + user + "','Y', " + recNo + ", 3,'" + sysUser + "'," + trsNo + ","
                            + "'" + brnCode + "','" + brnCode + "',3,'','','','')").executeUpdate();
                    if (ddsTran <= 0) {
                        return "Error in tdRecon Insertion";
                    }

                    fts.updateBalance(acNat, acno, 0, intAmt * -1, "", "");
                    recNo = fts.getRecNo();

                    Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
                            + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glIntHead + "','" + dateString + "'," + "'" + dateString + "',"
                            + intAmt * -1 + ",0,8,'Int Posted for " + fromDt + " To " + toDt + "','Y','" + sysUser + "','" + user + "'," + recNo + "," + trsNo + ",3,3,'" + brnCode
                            + "','" + brnCode + "')").executeUpdate();
                    if (reconBalanL <= 0) {
                        return "Error in reconbalan Insertion !!!";
                    }

                    Integer interestHistory = em.createNativeQuery("Insert into dds_interesthistory values('" + acno + "','" + intAmt + "','" + dateString + "','" + fromDt
                            + "','" + toDt + "', now(),'" + user + "')").executeUpdate();
                    if (interestHistory <= 0) {
                        return "Error in TD_InterestHistory Insertion";
                    }

                    String rs = fts.updateBalance(CbsConstant.PAY_ORDER, glIntHead, intAmt * -1, 0, "", "");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in Balance updation");
                    }
                } else {
                    recNo = fts.getRecNo();
                    Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                            + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                            + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acno + "', 0,'" + dateString + "','" + dateString + "',0,"
                            + "" + intAmt + ",0,8,'Int Posted for " + fromDt + " To " + toDt + "',1,'" + user + "','Y', "
                            + "" + recNo + ", 3,'" + sysUser + "'," + trsNo + ","
                            + "'" + brnCode + "','" + brnCode + "',3,'','','','')").executeUpdate();
                    if (ddsTran <= 0) {
                        return "Error in tdRecon Insertion";
                    }

                    fts.updateBalance(acNat, acno, intAmt, 0, "", "");
                    recNo = fts.getRecNo();

                    Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                            + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glIntHead + "','" + dateString + "'," + "'" + dateString + "',"
                            + intAmt + ",1,8,'Int Posted for " + fromDt + " To " + toDt + "','Y','" + sysUser + "','" + user + "'," + recNo + "," + trsNo + ",3,3,'" + brnCode
                            + "','" + brnCode + "')").executeUpdate();
                    if (reconBalanL <= 0) {
                        return "Error in reconbalan Insertion !!!";
                    }

                    Integer interestHistory = em.createNativeQuery("Insert into dds_interesthistory values('" + acno + "','" + intAmt + "','" + dateString + "','" + fromDt
                            + "','" + toDt + "', now(),'" + user + "')").executeUpdate();
                    if (interestHistory <= 0) {
                        return "Error in TD_InterestHistory Insertion";
                    }

                    String rs = fts.updateBalance(CbsConstant.PAY_ORDER, glIntHead, 0, intAmt, "", "");
                    if (!rs.equalsIgnoreCase("True")) {
                        throw new ApplicationException("Problem in Balance updation");
                    }
                }
            }

            String glAccNo = null;
            List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                    + "where TDS_Applicabledate<='" + new Date() + "')").getResultList();
            if (!chk3.isEmpty()) {
                Vector chk3V = (Vector) chk3.get(0);
                glAccNo = brnCode + chk3V.get(0).toString();
            }

            if (tTds > 0) {

                recNo = fts.getRecNo();
                Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                        + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acno + "', 1,'" + dateString + "','" + dateString + "','" + tTds + "',"
                        + "0,0,8,'Tds Deducted for " + fromDt + " To " + toDt + "',0,'" + user + "','Y', " + recNo + ", 3,'" + sysUser + "'," + trsNo + ","
                        + "'" + brnCode + "','" + brnCode + "',3,'','','','')").executeUpdate();
                if (ddsTran <= 0) {
                    return "Error in tdRecon Insertion";
                }

                fts.updateBalance(acNat, acno, 0, tTds, "", "");

                Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
                        + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glAccNo + "','" + dateString + "'," + "'" + dateString + "',"
                        + tTds + ",0,8,'Tds Credited For " + acno + "','Y','" + sysUser + "','" + user + "'," + recNo + "," + trsNo + ",3,3,'" + brnCode
                        + "','" + brnCode + "')").executeUpdate();
                if (reconBalanL <= 0) {
                    return "Error in reconbalan Insertion !!!";
                }

                String rs = fts.updateBalance(CbsConstant.PAY_ORDER, glAccNo, tTds, 0, "", "");
                if (!rs.equalsIgnoreCase("True")) {
                    throw new ApplicationException("Problem in Balance updation");
                }

                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                        + "VALUES('" + acno + "',0," + tTds + ",'" + dateString + "','" + toDt + "','" + fromDt + "','')").executeUpdate();
                if (TDSHistory <= 0) {
                    throw new ApplicationException("Data Not Saved For " + acno);
                }

                Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                        + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                        + "VALUES('" + acno + "',0," + tTds + ",'" + dateString + "','" + toDt + "','" + fromDt + "','',"
                        + "'R'," + trsNo + "," + recNo + ",0,date_format(now(),'%Y%m%d'))");
                int result = insertTdsQuery.executeUpdate();
                if (result < 0) {
                    throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                }
            }

            if (cTds > 0) {

                recNo = fts.getRecNo();
                Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                        + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acno + "', 1,'" + dateString + "','" + dateString + "','" + cTds + "',"
                        + "0,0,8,'Tds Deducted for" + fromDt + " To " + toDt + "',0,'" + user + "','Y', " + recNo + ", 3,'" + sysUser + "'," + trsNo + ","
                        + "'" + brnCode + "','" + brnCode + "',3,'','','','')").executeUpdate();
                if (ddsTran <= 0) {
                    return "Error in tdRecon Insertion";
                }

                fts.updateBalance(acNat, acno, 0, tTds, "", "");

                Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,cramt,ty,trantype,details,auth,authby,enterBy,"
                        + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + glAccNo + "','" + dateString + "'," + "'" + dateString + "',"
                        + cTds + ",0,8,'Tds Credited For " + acno + "','Y','" + sysUser + "','" + user + "'," + recNo + "," + trsNo + ",3,3,'" + brnCode
                        + "','" + brnCode + "')").executeUpdate();
                if (reconBalanL <= 0) {
                    return "Error in reconbalan Insertion !!!";
                }

                Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                        + "VALUES('" + acno + "',0," + cTds + ",'" + dateString + "','" + toDt + "','" + fromDt + "','')").executeUpdate();
                if (TDSHistory <= 0) {
                    throw new ApplicationException("Data Not Saved For " + acno);
                }

                String rs = fts.updateBalance(CbsConstant.PAY_ORDER, glAccNo, cTds, 0, "", "");
                if (!rs.equalsIgnoreCase("True")) {
                    throw new ApplicationException("Problem in Balance updation");
                }

                Query insertTdsQuery = em.createNativeQuery("INSERT INTO tds_reserve_history(acno,voucherno,tds,dt,Todt,fromdt,intOpt,"
                        + "recovered, trsno, recno, recoveredVch, tdsRecoveredDt)"
                        + "VALUES('" + acno + "',0," + cTds + ",'" + dateString + "','" + toDt + "','" + fromDt + "','',"
                        + "'R'," + trsNo + "," + recNo + ",0,date_format(now(),'%Y%m%d'))");
                int result = insertTdsQuery.executeUpdate();
                if (result < 0) {
                    throw new ApplicationException("Error in updating tds_reserve_history for tds ");
                }
            }

            Query updateQuery1 = em.createNativeQuery("update cbs_loan_acc_mast_sec tv inner join accountmaster tm on tv.acno = tm.acno "
                    + "set int_calc_upto_dt='" + dateString + "', next_int_calc_dt='" + CbsUtil.dateAdd(dateString, 1) + "' where tm.acno ='" + acno + "'");
            int result = updateQuery1.executeUpdate();
            if (result < 0) {
                return "Error in updating Next Interest Payable Date";
            }

            ut.commit();
            return "Interest Posted Successfully! Generated Batch No. Is " + trsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ******************************TransactionAuthorizationBean's
     * Method*******************************************
     */
    public List getAgentsForAuth(String brCode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select distinct agcode,name from ddsagent where brncode = '" + brCode + "' AND status='A'").getResultList();
            return resultList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List getPendingAgentsForAuth(String brCode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select distinct substring(acno,11,2),dt from dds_auth_d WHERE ifnull(AUTH,'N') = 'N' and"
                    + " acno in (select acno from accountmaster where curbrcode ='" + brCode + "' and substring(acno,3,2) in "
                    + "(select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "'))  AND ifnull(tokenpaid,'N') = 'Y'").getResultList();
            return resultList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List getAgentTransactionsBy(String agentCode, String brCode, String ordBy) throws ApplicationException {
        List list = new ArrayList();
        try {
            SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
            String date = ymdFormat.format(new Date());
            Query selectQuery = em.createNativeQuery("SELECT * from dds_auth_d WHERE ifnull(auth,'N') = 'N' AND ifNull(tokenpaid,'N') = 'Y' AND acno "
                    + "in (select acno from accountmaster where curbrcode ='" + brCode + "' and substring(acno,3,2) in "
                    + "(select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) "
                    + "AND substring(DATE_FORMAT(dt,'%Y%m%d'),1,10)='" + date + "' AND substring(acno,11,2) = '" + agentCode + "' order by '" + ordBy + "'");
            list = selectQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public String authorizeAgentTransactions(String agentName, double amount, String agentCode, String brCode, String authBy, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query selectTransactions = em.createNativeQuery("select acno,cramt,receiptno,enterby,TokenPaidBy FROM dds_auth_d where acno in "
                    + "(select acno from accountmaster where curbrcode ='" + brCode + "' and accttype in (select acctcode from accounttypemaster "
                    + "where acctnature ='" + CbsConstant.DEPOSIT_SC + "'))  and substring(acno,11,2) = '" + agentCode + "' and "
                    + "substring(DATE_FORMAT(dt,'%Y%m%d'),1,10) = '" + date + "' and TokenPaid = 'Y'");
            List transactions = selectTransactions.getResultList();
            if (!transactions.isEmpty()) {
                for (Object ob : transactions) {
                    String sacno = "", sreceiptNo = "", senteredBy = "", stokenPaidBy = "", customerName = "";
                    Double scrAmt = 0.0;
                    Vector object = (Vector) ob;
                    if (object.get(0) != null) {
                        sacno = object.get(0).toString();
                    }
                    if (object.get(1) != null) {
                        scrAmt = Double.parseDouble(object.get(1).toString());
                    }
                    if (object.get(2) != null) {
                        sreceiptNo = object.get(2).toString();
                    }
                    if (object.get(3) != null) {
                        senteredBy = object.get(3).toString();
                    }
                    if (object.get(4) != null) {
                        stokenPaidBy = object.get(4).toString();
                    }
                    Query selectCustomerNameQuery = em.createNativeQuery("SELECT acno,custname FROM customerid AS CI,"
                            + "cbs_customer_master_detail AS CCMD WHERE CI.CUSTID=CCMD.customerid AND CI.ACNO='" + sacno + "'");
                    List resultListselectCustomerNameQuery = selectCustomerNameQuery.getResultList();
                    if (!resultListselectCustomerNameQuery.isEmpty()) {
                        Vector ele = (Vector) resultListselectCustomerNameQuery.get(0);
                        customerName = ele.get(1).toString();
                    }
                    float recNo = fts.getRecNo();
                    Query insertTokentable_creditQuery = em.createNativeQuery("Insert into tokentable_credit (acno, custname, dt, cramt, drAmt, "
                            + "payby, enterby, auth, trandesc, tokenpaid, tokenpaidby, authby, RECNO, TRANTYPE, TY, org_brnid, dest_brnid, ValueDt) "
                            + "values ('" + sacno + "','" + customerName + "','" + date + "'," + scrAmt + ",0,3,'" + senteredBy + "','Y',0,'Y','"
                            + stokenPaidBy + "','" + authBy + "','" + recNo + "',0,0,'" + brCode + "','" + brCode + "', now())");
                    int insertQueryTokentable_creditResult = insertTokentable_creditQuery.executeUpdate();
                    if (insertQueryTokentable_creditResult < 0) {
                        throw new ApplicationException("Can not be authorized !!");
                    }
                    int trsNo = fts.getTrsNo().intValue();
                    Query insertQuery1 = em.createNativeQuery("INSERT INTO recon_cash_d(ACNO ,CUSTNAME, TY, DT,VALUEDT, DRAMT, CRAMT,TRANTYPE, "
                            + "DETAILS, IY,ENTERBY, AUTH, RECNO,PAYBY,AUTHBY, TRSNO, TRANTIME, TRANDESC, TOKENNO,TOKENPAIDBY, "
                            + "SUBTOKENNO,TERM_ID,ORG_BRNID,DEST_BRNID,TRAN_ID) values ('" + sacno + "','" + customerName + "',0,'" + date
                            + "',now(),0," + scrAmt + ",0,'',0,'" + senteredBy + "','Y'," + recNo + ",3,'" + authBy + "'," + trsNo
                            + ",CURRENT_TIMESTAMP,0,0,'" + stokenPaidBy + "','A','','" + brCode + "','" + brCode + "',0)");
                    int rs = insertQuery1.executeUpdate();
                    if (rs < 0) {
                        throw new ApplicationException("Can not be authorized !!");
                    }

                    Query insertDDSTransactionQuery = em.createNativeQuery("insert into ddstransaction (acno , ty, dt, valuedt, dramt, cramt, "
                            + "trantype, recno, details, iy, receiptno, enterby, auth, payby, authby, trandesc, trsno, tokenno, subtokenno, "
                            + "tokenpaid, Balance, favorof, trantime, INSTNO, CheckBy, org_brnid, dest_brnid, tran_id, term_id, TOKENPAIDBY) "
                            + "values('" + sacno + "',0,'" + date + "','" + date + "',0," + scrAmt + ",0," + recNo + ",'VCH Ag Coll.',1,'"
                            + sreceiptNo + "','" + senteredBy + "','Y',3,'" + authBy + "',0," + trsNo + ",0,'','Y',0,'',now(),'','','" + brCode
                            + "','" + brCode + "',0,'','" + stokenPaidBy + "')");
                    int resultInsertDDSTransactionQuery = insertDDSTransactionQuery.executeUpdate();
                    if (resultInsertDDSTransactionQuery <= 0) {
                        throw new ApplicationException("Can not be authorized !!");
                    }
                    Query checkReconBalan = em.createNativeQuery("select acno from reconbalan where acno = '" + sacno + "'");
                    List reconBalanList = checkReconBalan.getResultList();
                    if (!reconBalanList.isEmpty()) {
                        Query updateReconBalanQuery = em.createNativeQuery("Update reconbalan set balance = balance +" + scrAmt + " where acno = '" + sacno + "'");
                        int resultUpdateReconBalanQuery = updateReconBalanQuery.executeUpdate();
                        if (resultUpdateReconBalanQuery <= 0) {
                            throw new ApplicationException("Can not be authorized !!");
                        }
                    } else if (reconBalanList.isEmpty()) {
                        Query insertReconBalanQuery = em.createNativeQuery("insert into reconbalan (acno,dt,balance) values ('" + sacno + "',CURRENT_TIMESTAMP," + scrAmt + ")");
                        int resultInsertReconBalanQuery = insertReconBalanQuery.executeUpdate();
                        if (resultInsertReconBalanQuery <= 0) {
                            throw new ApplicationException("Can not be authorized !!");
                        }
                    }
                    fts.lastTxnDateUpdation(sacno);
                }
            }
            Query deleteQuery = em.createNativeQuery("delete from dds_auth_d where substring(acno,11,2) = '" + agentCode + "' and acno in "
                    + "(select acno from accountmaster where curbrcode ='" + brCode + "' and accttype in (select acctcode from accounttypemaster "
                    + "where acctnature ='" + CbsConstant.DEPOSIT_SC + "')) and substring(DATE_FORMAT(dt,'%Y%m%d'),1,10)='" + date + "' and TokenPaid = 'Y'");
            int deleteResult = deleteQuery.executeUpdate();
            if (deleteResult <= 0) {
                throw new ApplicationException("Can not be authorized !!");
            }

            ut.commit();
            //Sending Sms
            try {
                List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();
                for (int s = 0; s < transactions.size(); s++) {
                    SmsToBatchTo to = new SmsToBatchTo();

                    Vector getDateVect = (Vector) transactions.get(s);
                    to.setAcNo(getDateVect.get(0).toString());
                    to.setCrAmt(Double.parseDouble(getDateVect.get(1).toString()));
                    to.setDrAmt(0d);
                    to.setTranType(0);
                    to.setTy(0);
                    to.setTxnDt(dmy.format(ymd.parse(date)));
                    to.setTemplate(SmsType.CASH_DEPOSIT);

                    smsList.add(to);
                }
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending To Account Holders from DDS Authorization." + e.getMessage());
            }
            //End here.
            return "Authorization over !!";
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

    /**
     * **********************InterestSlabMasterBean's
     * Methods************************************
     */
    public List<InterestSlabMasterTable> datalist() throws ApplicationException {
        List<InterestSlabMasterTable> returnList = new ArrayList<InterestSlabMasterTable>();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        NumberFormat format = new DecimalFormat("0.00");
        try {
            //List result = em.createNativeQuery("select * from ddsint_slab WHERE applicable_Date =(select max(Applicable_Date) from ddsint_slab) Order by Applicable_Date").getResultList();
            List result = em.createNativeQuery("select FROM_MON,TO_MON,ROI,FROM_AMT,cast(TO_AMT as decimal(14,2)),COMM_PERC,APPLICABLE_DATE,SURCHARGE,"
                    + " AGITAX,AGSECDEP,DAYBAL,ENTERBY from dds_slab WHERE applicable_Date = (select max(Applicable_Date) from dds_slab) "
                    + " Order by Applicable_Date").getResultList();
            int i = 0;
            while (i < result.size()) {
                InterestSlabMasterTable grid = new InterestSlabMasterTable();
                Vector data = (Vector) result.get(i++);
                grid.setFromPeriod(data.get(0) != null ? data.get(0).toString() : "");
                grid.setToPeriod(data.get(1) != null ? data.get(1).toString() : "");
                grid.setInterestrate(data.get(2) != null ? format.format(data.get(2)) : "");
                grid.setFrmAmt(data.get(3) != null ? data.get(3).toString() : "");
                grid.setToAmt(data.get(4) != null ? data.get(4).toString() : "");
                grid.setCommPerc(data.get(5) != null ? data.get(5).toString() : "");
                grid.setApplicableDate(data.get(6) != null ? dmyFormat.format(data.get(6)) : "");
                grid.setSurChg(data.get(7) != null ? data.get(7).toString() : "");
                grid.setAgiTax(data.get(8) != null ? data.get(8).toString() : "");
                grid.setAgDecDep(data.get(9) != null ? data.get(9).toString() : "");
                grid.setDayBal(data.get(10) != null ? data.get(10).toString() : "");
                grid.setEnteredby(data.get(11) != null ? data.get(11).toString() : "");
                returnList.add(grid);
            }
            Collections.sort(returnList);
        } catch (Exception e) {

            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

//    public String insertData(Date date, double intrt, String fromprd, String toprd, String enterdby, Date update) throws ApplicationException {
//        String returnData = "";
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            List no = em.createNativeQuery("select max(sno) from ddsint_slab").getResultList();
//            Vector vtr = (Vector) no.get(0);
//            int sno = vtr.get(0) == null ? 0 : Integer.parseInt(vtr.get(0).toString());
//            sno++;
//            ut.begin();
//            int save = em.createNativeQuery("insert ddsint_slab values(" + sno + ",'" + ymd.format(date) + "'," + intrt + "," + fromprd + "," + toprd + ",'" + enterdby + "','" + ymd.format(update) + "')").executeUpdate();
//            if (save > 0) {
//                returnData = "Data has been saved sucsessfully !!";
//                ut.commit();
//            } else {
//                ut.rollback();
//            }
//        } catch (Exception e) {
//            returnData = "Slab already exists";
//            throw new ApplicationException(e.getMessage());
//
//        }
//        return returnData;
//    }
    public String insertData(Date date, double intrt, String fromprd, String toprd, String enterdby, String frAmt, String toAmt, String comm, String sChg, String agTax, String agSDep, String dBal) throws ApplicationException {
        String returnData = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int save = em.createNativeQuery("insert dds_slab(FROM_MON,TO_MON,ROI,FROM_AMT,TO_AMT,COMM_PERC,APPLICABLE_DATE,"
                    + " SURCHARGE,AGITAX,AGSECDEP,DAYBAL,ENTERBY) values(" + fromprd + "," + toprd + "," + intrt + "," + frAmt + "," + toAmt + "," + comm + ",'" + ymd.format(date) + "'," + sChg + "," + agTax + "," + agSDep + "," + dBal + ",'" + enterdby + "')").executeUpdate();
            if (save > 0) {
                returnData = "Data has been saved sucsessfully !!";
                ut.commit();
            } else {
                ut.rollback();
            }
        } catch (Exception e) {
            returnData = "Slab already exists";
            throw new ApplicationException(e.getMessage());

        }
        return returnData;
    }

    public boolean checkData(float period, Date AppDate) throws ApplicationException {
        try {
            //List l = em.createNativeQuery("select FROMPERIOD from ddsint_slab WHERE " + period + " BETWEEN  FROMPERIOD AND TOPERIOD  AND APPLICABLE_DATE= '" + ymd.format(AppDate) + "'").getResultList();
            List l = em.createNativeQuery("select FROM_mon from dds_slab WHERE " + period + " BETWEEN  FROM_mon AND TO_mon  AND APPLICABLE_DATE= '" + ymd.format(AppDate) + "'").getResultList();
            if (l.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ***********************ReceiptBean's
     * Methods***********************************
     */
    public List getBranchList() throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select distinct brncode,branchname from branchmaster where brncode not in (90,99) order by branchname").getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getAgentList(String brnCode) throws ApplicationException {
        try {
            String brCode = CbsUtil.lPadding(2, Integer.parseInt(brnCode));
            List resultList = em.createNativeQuery("select agcode,name from ddsagent where status='A' and brncode='" + brCode + "' order by name").getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    private String getAgentNameByAgentCode(String agentCode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select name from ddsagent where agcode='" + agentCode + "'").getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                Vector ele = (Vector) resultList.get(i);
                if (null != ele.get(0)) {
                    return ele.get(0).toString();
                }
            }
            return "";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<ReceiptMasterTable> getAvailableReceipts() throws ApplicationException {
        List<ReceiptMasterTable> returnList = new ArrayList<ReceiptMasterTable>();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List resultList = em.createNativeQuery("select * from ddsreceiptsmast where agcode is null and issuedate is null order by receiptfrom").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ReceiptMasterTable table = new ReceiptMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (null != ele.get(0)) {
                        table.setAgCode(ele.get(0).toString());
                    }
                    if (null != ele.get(1)) {
                        table.setIssueDate(dmyFormat.format(ele.get(1)));
                    }
                    if (null != ele.get(2)) {
                        table.setReceiptFrom(Float.parseFloat(ele.get(2).toString()));
                    }
                    if (null != ele.get(3)) {
                        table.setReceiptTo(Float.parseFloat(ele.get(3).toString()));
                    }
                    if (null != ele.get(4)) {
                        table.setIssuedBy(ele.get(4).toString());
                    }
                    if (null != ele.get(5)) {
                        table.setLeafs(Integer.parseInt(ele.get(5).toString()));
                    }
                    returnList.add(table);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ReceiptMasterTable> getAllocatedReceipts() throws ApplicationException {
        List<ReceiptMasterTable> returnList = new ArrayList<ReceiptMasterTable>();
        SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List resultList = em.createNativeQuery("select * from ddsreceiptsmast where agcode is not null and issuedate is not null order by agcode").getResultList();
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    ReceiptMasterTable table = new ReceiptMasterTable();
                    Vector ele = (Vector) resultList.get(i);
                    if (null != ele.get(0)) {
                        table.setAgCode(ele.get(0).toString());
                    }
                    if (null != ele.get(1)) {
                        table.setIssueDate(dmyFormat.format(ele.get(1)));
                    }
                    if (null != ele.get(2)) {
                        table.setReceiptFrom(Float.parseFloat(ele.get(2).toString()));
                    }
                    if (null != ele.get(3)) {
                        table.setReceiptTo(Float.parseFloat(ele.get(3).toString()));
                    }
                    if (null != ele.get(4)) {
                        table.setIssuedBy(ele.get(4).toString());
                    }
                    if (null != ele.get(5)) {
                        table.setLeafs(Integer.parseInt(ele.get(5).toString()));
                    }
                    table.setAgName(getAgentNameByAgentCode(table.getAgCode()));
                    returnList.add(table);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public String deleteSeries(float receiptFrom, float receiptTo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query insertQuery = em.createNativeQuery("delete from ddsreceiptsmast where receiptfrom=" + receiptFrom + " and receiptto=" + receiptTo + "");
            Integer insertTrsonvMast = insertQuery.executeUpdate();
            if (insertTrsonvMast > 0) {
                ut.commit();
                return "Row deleted successfully";
            } else {
                ut.rollback();
                return "System error occurred";
            }
        } catch (Exception e) {
            return "System error occurred";
        }
    }

    public String revertSeries(float receiptFrom, float receiptTo) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select * from dds_auth_d where CAST(receiptno AS decimal) >= " + receiptFrom + " and CAST(receiptno AS decimal) <= " + receiptTo + "").getResultList();
            if (resultList.isEmpty()) {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                Query insertQuery = em.createNativeQuery("update ddsreceiptsmast set agcode = null, issuedate=null,issuedBy=null where receiptfrom = " + receiptFrom + " and receiptto = " + receiptTo + "");
                Integer insertTrsonvMast = insertQuery.executeUpdate();
                if (insertTrsonvMast > 0) {
                    ut.commit();
                    return "Row deleted successfully";
                } else {
                    ut.rollback();
                    return "System error occurred";
                }
            } else {
                return "This book series has been already used";
            }
        } catch (Exception e) {
            return "System error occurred";
        }
    }

    private String checkReceipts(float receiptFrom, float receiptTo) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select * from ddsreceiptsmast where receiptfrom between " + receiptFrom + " and " + receiptTo + " or receiptto between " + receiptFrom + " and " + receiptTo + "").getResultList();
            if (resultList.isEmpty()) {
                return "OK";
            } else {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0) != null) {
                    String agCode = ele.get(0).toString();
                    String issueDate = ele.get(1).toString();
                    return "Series is already issued to agent code " + agCode + " on date " + new SimpleDateFormat("dd/MM/yyyy").format(issueDate);
                } else {
                    return "Series is already exists";
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String insertSeries(float receiptFrom, float receiptTo) throws ApplicationException {
        try {
            int noOfLeaves = 0;
            if (receiptTo <= 0 || receiptFrom <= 0) {
                return "Receipt cannot be negative";
            }
            if (receiptTo > receiptFrom) {
                noOfLeaves = (int) (receiptTo - receiptFrom) + 1;
            } else {
                return "Receipt To must be greater than Receipt From";
            }
            String checkReceipts = checkReceipts(receiptFrom, receiptTo);
            if (checkReceipts.equalsIgnoreCase("OK")) {
                UserTransaction ut = context.getUserTransaction();
                ut.begin();
                Query insertQuery = em.createNativeQuery("insert into ddsreceiptsmast(receiptfrom, receiptto, leafs) values(" + receiptFrom + "," + receiptTo + "," + noOfLeaves + ")");
                Integer insertTrsonvMast = insertQuery.executeUpdate();
                if (insertTrsonvMast > 0) {
                    ut.commit();
                    return "Data saved successfully !!";
                } else {
                    ut.rollback();
                    return "System error occurred";
                }
            } else {
                return checkReceipts;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String issueSeries(String agCode, String issueBy, float receiptFrom, float receiptTo) throws ApplicationException {
        try {
            String checkReceipts = checkReceipts(receiptFrom, receiptTo);
            if (checkReceipts.contains("Series is already issued")) {
                return checkReceipts;
            }
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            Query insertQuery = em.createNativeQuery("update ddsreceiptsmast set agcode = '" + agCode + "', issuedate = now(),issuedBy = '" + issueBy + "' where receiptfrom = " + receiptFrom + " and receiptto = " + receiptTo + "");
            Integer insertTrsonvMast = insertQuery.executeUpdate();
            if (insertTrsonvMast > 0) {
                ut.commit();
                return "Data saved successfully !!";
            } else {
                ut.rollback();
                return "System error occurred";
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * *****************TransactionBean's
     * Methods*********************************
     */
    public List getagentCodeNameAndAccountNo(String acType, String agCode, String brnCode) throws ApplicationException {
        try {
            Query q = em.createNativeQuery("select name from ddsagent where status='A' and Agcode='" + agCode + "' and brncode='" + brnCode + "'");
            List agentNameAndAcnoList = q.getResultList();
            agentNameAndAcnoList.add(":");
            Query q1 = em.createNativeQuery("select acno from accountmaster where accstatus<>9 and substring(acno,3,2)='" + acType + "' and substring(acno,11,2)='" + agCode + "' and curbrcode='" + brnCode + "' order by acno");
            agentNameAndAcnoList.add(q1.getResultList());
            return agentNameAndAcnoList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getActiveAgentName(String agentCode, String brnCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select name from ddsagent where status='A' and Agcode='" + agentCode + "' and brncode='" + brnCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustNamebasedOnAcNo(String acno) throws ApplicationException {
        try {
            String acNature = fts.getAccountNature(acno);
            if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                return em.createNativeQuery("select custname,accstatus from accountmaster where acno='" + acno + "'").getResultList();
            } else {
                throw new ApplicationException("Only DDS Account is allowed.");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String saveDdsTransactions(String acNo, String dt, double cramt, String receiptNo, String enterBy, float recno, String tokenPaid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = em.createNativeQuery("select accstatus from accountmaster where acno='" + acNo + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("This account number does not exists");
            }
            Vector custNameVector = (Vector) list.get(0);
            int accStatus = Integer.parseInt(custNameVector.get(0).toString());
            if (accStatus == 9) {
                throw new ApplicationException("This account has been closed");
            }
            list = em.createNativeQuery("select ACNO from dds_auth_d where SUBSTRING(acno,1,2)='" + acNo.substring(0, 2) + "' and dt='" + dt
                    + "' and receiptno = '" + receiptNo + "' and SUBSTRING(acno,11,2)='" + acNo.substring(10) + "'").getResultList();
            if (!list.isEmpty()) {
                throw new ApplicationException("This Receipt already exists for this agent. Please fill another Receipt No");
            }
            Query q = em.createNativeQuery("insert into dds_auth_d(ACNo,Ty,Dt,dramt,CrAmt,TranType,ReceiptNo,EnterBy,Recno,TokenPaid) "
                    + "values ('" + acNo + "',0,'" + dt + "',0," + cramt + ",0,'" + receiptNo + "','" + enterBy + "'," + recno + ",'" + tokenPaid + "')");
            int rowAffected = q.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in data insertion");
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

    public String getReceiptNo(String agentCode, String orgBrCode) throws ApplicationException {
//        List receiptNoList = em.createNativeQuery("select isnull(max(receiptno),0) from dds_auth_d where substring(acno,11,2)='" + agentCode + "'").getResultList();
        // Here orgBrCode additional.
        List receiptNoList = em.createNativeQuery("select ifnull(max(cast(receiptno as decimal)),0)+1 from dds_auth_d where substring(acno,11,2)='" + agentCode + "' and substring(acno,1,2)='" + orgBrCode + "'").getResultList();
        Vector element = (Vector) receiptNoList.get(0);
        return (element.get(0).toString());
    }

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException {
        Vector ele = null;
        try {
            Query selectQuery = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=cast('" + orgnbrcode + "' AS unsigned)");
            List bankList = selectQuery.getResultList();
            if (!bankList.isEmpty()) {
                ele = (Vector) bankList.get(0);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return ele;
    }
    /**
     * ***************************InterestProvisionBean's
     * Methods****************************************
     */
    List list;
    float total = 0.0f;

//    public List calculate(String fromDate, Date toDate, String type, String interestFrom, String brncode) throws ApplicationException {
//        String secondMonthStart = "";
//        String firstMonthEnd = "";
//        String thirdMonthStart = "";
//        String secondMonthEnd = "";
//        float firstMonthAmt = 0.0f;
//        float secondMonthAmt = 0.0f;
//        float thirdMonthAmt = 0.0f;
//        float totalAmount = 0.0f;
//        float totalAmount1 = 0.0f;
//        float totalAmount2 = 0.0f;
//        // float totalAmount3 = 0.0f;
//        List tempData = null;
//        float roi = 0.0f;
//        String acno = "";
//        float total1 = 0.0f;
//        float total2 = 0.0f;
//        float total3 = 0.0f;
//        int monthDiff = 0;
//        float interestAmt = 0.0f;
//        Vector vc = null;
//
//        String names = "";
//        //float trsNo = 0.0f;
//        list = new ArrayList();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {    
//            List tmpnew = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD('" + fromDate + "', INTERVAL 1 MONTH),'%Y%m%d')").getResultList();
//            Vector elenewDt = (Vector) tmpnew.get(0);
//            String tempBdv = elenewDt.get(0).toString();
//            String tempBdVYr = tempBdv.substring(0, 4);
//            String tempBdVMon = tempBdv.substring(4, 6);
//            String tempBdVDD = tempBdv.substring(6, 8);
//            String tempSecondMonthStart = tempBdVYr + tempBdVMon + tempBdVDD;
//            List selectQueryFrmDT = em.createNativeQuery("SELECT DATE_FORMAT('" + tempSecondMonthStart + "','%Y-%m-%d')").getResultList();
//            if (!selectQueryFrmDT.isEmpty()) {
//                Vector frmdate = (Vector) selectQueryFrmDT.get(0);
//                secondMonthStart = frmdate.get(0).toString();
//            }            
//            List tmpnew1 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD('" + secondMonthStart + "', INTERVAL -1 DAY),'%Y%m%d')").getResultList();
//            Vector elenewDt1 = (Vector) tmpnew1.get(0);
//            tempBdv = elenewDt1.get(0).toString();
//            tempBdVYr = tempBdv.substring(0, 4);
//            tempBdVMon = tempBdv.substring(4, 6);
//            tempBdVDD = tempBdv.substring(6, 8);
//            tempSecondMonthStart = tempBdVYr + tempBdVMon + tempBdVDD;
//            List selectQueryFrmDT1 = em.createNativeQuery("SELECT DATE_FORMAT('" + tempSecondMonthStart + "','%Y-%m-%d')").getResultList();
//            if (!selectQueryFrmDT1.isEmpty()) {
//                Vector frmdate = (Vector) selectQueryFrmDT1.get(0);
//                firstMonthEnd = frmdate.get(0).toString();
//            }            
//            List tmpnew2 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD('" + secondMonthStart + "', INTERVAL 1 MONTH),'%Y%m%d')").getResultList();
//            Vector elenewDt2 = (Vector) tmpnew2.get(0);
//            tempBdv = elenewDt2.get(0).toString();
//            tempBdVYr = tempBdv.substring(0, 4);
//            tempBdVMon = tempBdv.substring(4, 6);
//            tempBdVDD = tempBdv.substring(6, 8);
//            tempSecondMonthStart = tempBdVYr + tempBdVMon + tempBdVDD;
//            List selectQueryFrmDT2 = em.createNativeQuery("SELECT DATE_FORMAT('" + tempSecondMonthStart + "','%Y-%m-%d')").getResultList();
//            if (!selectQueryFrmDT2.isEmpty()) {
//                Vector frmdate = (Vector) selectQueryFrmDT2.get(0);
//                thirdMonthStart = frmdate.get(0).toString();
//            }            
//            List tmpnew3 = em.createNativeQuery("SELECT DATE_FORMAT(DATE_ADD('" + thirdMonthStart + "', INTERVAL -1 DAY),'%Y%m%d')").getResultList();
//            Vector elenewDt3 = (Vector) tmpnew3.get(0);
//            tempBdv = elenewDt3.get(0).toString();
//            tempBdVYr = tempBdv.substring(0, 4);
//            tempBdVMon = tempBdv.substring(4, 6);
//            tempBdVDD = tempBdv.substring(6, 8);
//            tempSecondMonthStart = tempBdVYr + tempBdVMon + tempBdVDD;
//            List selectQueryFrmDT3 = em.createNativeQuery("SELECT DATE_FORMAT('" + tempSecondMonthStart + "','%Y-%m-%d')").getResultList();
//            if (!selectQueryFrmDT3.isEmpty()) {
//                Vector frmdate = (Vector) selectQueryFrmDT3.get(0);
//                secondMonthEnd = frmdate.get(0).toString();
//            }
//            if (interestFrom.equalsIgnoreCase("M")) {
//                List tmpnew4 = em.createNativeQuery("select MinInt from accounttypemaster where AcctCode ='" + type + "'").getResultList();
//                Vector elenewDt4 = (Vector) tmpnew4.get(0);
//                String roi1 = elenewDt4.get(0).toString();
//                if (roi1 == null || roi1.equalsIgnoreCase("")) {
//                    roi = 0.0f;
//                } else {
//                    roi = Float.parseFloat(roi1);
//                }
//
//            }
//            tempData = em.createNativeQuery("Select Acno,TIMESTAMPDIFF(MONTH,DATE_FORMAT(Openingdt,'%Y-%m-%d'),RdMatDate) from accountmaster where "
//                    + " curbrcode ='" + brncode + "' and substring(acno,3,2) = '" + type + "' and accstatus <> 9 and rdmatdate >= '" + sdf.format(toDate) + "'").getResultList();
//            if (!tempData.isEmpty()) {
//                int i = 0;
//                while (tempData.size() > i) {
//                    Vector elenewDt5 = (Vector) tempData.get(i);
//                    acno = elenewDt5.get(0).toString();
//                    monthDiff = Integer.parseInt(elenewDt5.get(1).toString());
//
//                    List tmpnew6 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno ='" + acno + "'  and dt <='" + fromDate + "'").getResultList();
//                    if (!tmpnew6.isEmpty()) {
//                        Vector ele1 = (Vector) tmpnew6.get(0);
//                        firstMonthAmt = Float.parseFloat(ele1.get(0).toString());
//                    }
//                    List tmpnew7 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno ='" + acno + "'  and dt<='" + secondMonthStart + "'").getResultList();
//                    if (!tmpnew7.isEmpty()) {
//
//                        Vector ele2 = (Vector) tmpnew7.get(0);
//                        secondMonthAmt = Float.parseFloat(ele2.get(0).toString());
//                    }
//                    List tmpnew8 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno = '" + acno + "' and dt <='" + thirdMonthStart + "'").getResultList();
//                    if (!tmpnew8.isEmpty()) {
//                        Vector ele3 = (Vector) tmpnew8.get(0);
//                        thirdMonthAmt = Float.parseFloat(ele3.get(0).toString());
//                    }
//                    totalAmount1 = firstMonthAmt + secondMonthAmt + thirdMonthAmt;
//                    List selectNames = em.createNativeQuery("select custname from accountmaster where acno like '" + acno + "'").getResultList();
//                    if (!selectNames.isEmpty()) {
//                        Vector vect = (Vector) selectNames.get(0);
//                        names = vect.get(0).toString();
//                    }
//                    if (!interestFrom.equalsIgnoreCase("M")) {
//                        List tmp = em.createNativeQuery("select Interest_Rate from ddsint_slab where " + monthDiff + " >= FromPeriod and " + monthDiff + " <ToPeriod").getResultList();
//                        Vector ele5 = (Vector) tmp.get(0);
//                        roi = Float.parseFloat(ele5.get(0).toString());
//                    }
//                    interestAmt = Math.round((totalAmount1 * roi) / 1200);
//                    vc = new Vector();
//                    vc.add(0, acno);
//                    total1 += interestAmt;
//                    vc.add(1, interestAmt);
//                    vc.add(2, names);
//                    if (interestAmt > 0) {
//                        list.add(0, vc);
//                    }
//                    i++;
//                }
//            }
//            //part 2
//            List tempData1 = em.createNativeQuery("Select Acno,TIMESTAMPDIFF(MONTH,DATE_FORMAT(Openingdt,'%Y-%m-%d'),RdMatDate) from accountmaster where curbrcode ='" + brncode + "' and "
//                    + "substring(acno,3,2)='" + type + "' and accstatus <> 9 and rdmatdate >= '" + secondMonthEnd + "'and rdmatdate<'" + sdf.format(toDate) + "'").getResultList();
//            int i = 0;
//            if (!tempData1.isEmpty()) {
//                while (tempData1.size() > i) {
//                    Vector elenew1 = (Vector) tempData.get(i);
//                    acno = elenew1.get(0).toString();
//                    monthDiff = Integer.parseInt(elenew1.get(1).toString());
//                    List tmpnew6 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno ='" + acno + "'  and dt<='" + fromDate + "' ").getResultList();
//                    if (!tmpnew6.isEmpty()) {
//
//                        Vector ele1 = (Vector) tmpnew6.get(0);
//                        firstMonthAmt = Float.parseFloat(ele1.get(0).toString());
//                    }
//                    List tmpnew7 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno ='" + acno + "'  and dt <='" + secondMonthStart + "'").getResultList();
//                    if (!tmpnew7.isEmpty()) {
//                        Vector ele2 = (Vector) tmpnew7.get(0);
//                        secondMonthAmt = Float.parseFloat(ele2.get(0).toString());
//                    }
//                    List selectNames = em.createNativeQuery("select custname from accountmaster where acno like '" + acno + "'").getResultList();
//                    if (!selectNames.isEmpty()) {
//                        Vector vect = (Vector) selectNames.get(0);
//                        names = vect.get(0).toString();
//
//                    }
//                    totalAmount2 = firstMonthAmt + secondMonthAmt;
//                    if (!interestFrom.equalsIgnoreCase("M")) {
//                        List tmp = em.createNativeQuery("select Interest_Rate from ddsint_slab where " + monthDiff + " >= FromPeriod and " + monthDiff + " <ToPeriod").getResultList();
//                        if (!tmp.isEmpty()) {
//                            Vector ele5 = (Vector) tmp.get(0);
//                            roi = Float.parseFloat(ele5.get(0).toString());
//
//                        }
//                    }
//                    interestAmt = Math.round((totalAmount * roi) / 1200);
//                    vc = new Vector();
//                    vc.add(0, acno);
//                    total2 += interestAmt;
//                    vc.add(1, interestAmt);
//                    vc.add(2, names);
//                    if (interestAmt > 0) {
//                        list.add(0, vc);
//                    }
//                    i++;
//                }
//            }
//            List tempData2 = em.createNativeQuery("Select Acno,TIMESTAMPDIFF(MONTH,DATE_FORMAT(Openingdt,'%Y-%m-%d'),RdMatDate) from accountmaster where "
//                    + "curbrcode ='" + brncode + "' and substring(acno,3,2)='" + type + "' and accstatus <> 9 and rdmatdate >='" + firstMonthEnd + "' and "
//                    + "rdmatdate<'" + secondMonthEnd + "'").getResultList();
//            i = 0;
//            if (!tempData2.isEmpty()) {
//                while (tempData2.size() > i) {
//                    Vector elenew2 = (Vector) tempData2.get(i);
//                    acno = elenew2.get(0).toString();
//                    monthDiff = elenew2.get(0).toString().equalsIgnoreCase("null") ? 0 : Integer.parseInt(elenew2.get(1).toString());
//                    List tmpnew6 = em.createNativeQuery("Select IFNULL((Sum(CrAmt) - Sum(DrAmt)),0) from ddstransaction where acno ='" + acno + "'  and dt <='" + fromDate + "'").getResultList();
//                    if (!tmpnew6.isEmpty()) {
//                        Vector ele1 = (Vector) tmpnew6.get(0);
//                        firstMonthAmt = Float.parseFloat(ele1.get(0).toString());
//                    }
//                    //totalAmount3 = firstMonthAmt;
//                    List selectNames = em.createNativeQuery("select custname from accountmaster where acno like '" + acno + "'").getResultList();
//                    if (!selectNames.isEmpty()) {
//                        Vector vect = (Vector) selectNames.get(0);
//                        names = vect.get(0).toString();
//                    }
//                    if (!interestFrom.equalsIgnoreCase("M")) {
//                        List tmp = em.createNativeQuery("select Interest_Rate from ddsint_slab where " + monthDiff + " >= FromPeriod and " + monthDiff + " <ToPeriod").getResultList();
//                        if (!tmp.isEmpty()) {
//                            Vector ele5 = (Vector) tmp.get(0);
//                            roi = Float.parseFloat(ele5.get(0).toString());
//                        }
//                    }
//                    interestAmt = Math.round((totalAmount * roi) / 1200);
//                    vc = new Vector();
//                    vc.add(0, acno);
//                    total3 += interestAmt;
//                    vc.add(1, interestAmt);
//                    vc.add(2, names);
//                    if (interestAmt > 0) {
//                        list.add(0, vc);
//                    }
//                    i++;
//                }
//            }
//
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        total = total1 + total2 + total3;
//        Vector vec = new Vector();
//        vec.add(total);
//        if (!list.isEmpty()) {
//            list.add(1, vec);
//        }
//        return list;
//    }    
    public List<InterestProvisionTable> calculate(Date fromDate, Date toDate, String type, String brncode) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");

//            List tempData = em.createNativeQuery("Select Acno,custname,openingdt,DATE_FORMAT(rdmatdate,'%Y%m%d'),intdeposit from accountmaster where "
//                    + "curbrcode ='" + brncode + "' and accttype = '" + type + "' and accstatus <> 9 and openingdt <= '" + ymd.format(toDate)
//                    + "' and rdmatdate >= '" + sdf.format(toDate) + "'").getResultList();
//            List tempData = em.createNativeQuery("Select a.Acno,a.custname,a.openingdt,DATE_FORMAT(a.rdmatdate,'%Y%m%d'),a.intdeposit from accountmaster a,"
//                    + " (select distinct acno from ddstransaction) d where a.acno = d.acno and a.curbrcode ='" + brncode + "' "
//                    + " and a.accttype = '" + type + "' and a.accstatus <> 9 and a.openingdt <= '" + ymd.format(toDate) + "' "
//                    + " and a.rdmatdate >= '" + sdf.format(toDate) + "'").getResultList();
            List tempData = em.createNativeQuery("Select a.Acno,a.custname,a.openingdt,DATE_FORMAT(a.rdmatdate,'%Y%m%d'),a.intdeposit,DATE_FORMAT(c.next_int_calc_dt,'%Y%m%d') from accountmaster a,"
                    + " (select distinct acno from ddstransaction) d, cbs_loan_acc_mast_sec c where a.acno = d.acno and a.acno = c.acno and a.curbrcode ='" + brncode + "' "
                    + " and a.accttype = '" + type + "' and a.accstatus <> 9 and a.openingdt <= '" + ymd.format(toDate) + "' "
                    + " and a.rdmatdate >= '" + sdf.format(toDate) + "'").getResultList();
            if (tempData.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            List<InterestProvisionTable> intCalList = new ArrayList<InterestProvisionTable>();
            InterestProvisionTable intObj;
            //   UserTransaction ut = context.getUserTransaction();

            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='DDS_PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }

            for (int i = 0; i < tempData.size(); i++) {
                Vector vect = (Vector) tempData.get(i);
                intObj = new InterestProvisionTable();

                intObj.setSno(i + 1);
                intObj.setAcno(vect.get(0).toString());
                intObj.setName(vect.get(1).toString());
                String opDt = vect.get(2).toString();
                String openingDt = vect.get(5).toString();
                if (vect.get(3) == null) {
                    throw new ApplicationException("Maturity Date does not exist In Accountmaster");
                }
                Date fromDt = fromDate;
                if (ymd.parse(openingDt).getTime() > fromDate.getTime()) {
                    fromDt = ymd.parse(openingDt);
                }
                String matDt = vect.get(3).toString();
                int pd = CbsUtil.monthDiff(ymd.parse(opDt), ymd.parse(matDt));
                System.out.println("Acno->" + intObj.getAcno() + "::PD->" + pd + "::OpDT->" + fromDt + "::Accode->" + fts.getAccountCode(intObj.getAcno()));

                List resultList = em.createNativeQuery("select min(roi),ifnull(roi,0) from dds_slab where (" + pd + " between from_mon and to_mon) "
                        + " and actype ='" + fts.getAccountCode(intObj.getAcno()) + "' and applicable_date = (select max(applicable_date) from dds_slab "
                        + " where actype ='" + fts.getAccountCode(intObj.getAcno()) + "' and applicable_date <='" + opDt + "')").getResultList();
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Maturity Date does not exist");
                }
                Vector vtr2 = (Vector) resultList.get(0);
                double roi = Double.parseDouble(vtr2.get(0).toString());

                double balance = Double.parseDouble(getBalance(intObj.getAcno()));
                double product = getProduct(ymd.format(fromDt), ymd.format(toDate), intObj.getAcno(), code);
                double interest = 0;
                if ((code == 0) || (code == 2)) {
                    interest = CbsUtil.round((product * roi / 1200), 0);
                } else {
                    interest = CbsUtil.round((product * roi / 36500), 0);
                }

                intObj.setFromDt(dmy.format(fromDt));
                intObj.setToDt(dmy.format(toDate));

                intObj.setRoi(roi);
                intObj.setIamt(interest);

                intObj.setProduct(product);
                intCalList.add(intObj);
            }
            return intCalList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

//    public String post(List<InterestProvisionTable> list, String type, String fromDate, String toDate, String brncode, String userName) throws ApplicationException {
//        String todayDate = "";
//        List postingQuery = em.createNativeQuery("Select actype from int_post_history where actype='" + type + "' and fromdate='" + fromDate + "' and todate='" + toDate + "' and brncode='" + brncode + "'").getResultList();
//        if (postingQuery.size() > 0) {
//            return "Interest already posted For the this PERIOD.";
//        }
//        List query = em.createNativeQuery("Select glheadint,glheadprov from accounttypemaster where acctcode='" + type + "'").getResultList();
//        if (query.isEmpty()) {
//            throw new ApplicationException("Provision and interest Head does not defined");
//        }
//        Vector vec1 = (Vector) query.get(0);
//        String drglhead = brncode + (vec1.get(0).toString()) + "01";
//        String crglhead = brncode + (vec1.get(1).toString()) + "01";
//
//        UserTransaction ut = context.getUserTransaction();
//        try {
//            ut.begin();
//            float trsNo = fts.getTrsNo();
//            float recNo = fts.getRecNo();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            todayDate = (sdf.format(new Date()));
//            Integer var = 0, var1 = 0, var2 = 0, var3 = 0;
//            Double total = 0d;
//            for (InterestProvisionTable pojo : list) {
//                String acNo = pojo.getAcno();
//                Double interest = pojo.getIamt();
//                total = total + interest;
//                var = em.createNativeQuery("Insert into dds_interesthistory values('" + acNo + "','" + interest + "','" + todayDate + "','" + fromDate
//                        + "','" + toDate + "', now(),'" + userName + "')").executeUpdate();
//            }
//            var1 = em.createNativeQuery("Insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,enterby,auth,recno,"
//                    + "payby,authby,trsno,trantime,org_brnid,dest_brnid,adviceNo,adviceBrnCode) "
//                    + "values ('" + crglhead + "',0,'" + todayDate + "','" + todayDate + "',0," + total + ",8,3,'Int Prov Posting For DS',0,'','"
//                    + userName + "','Y'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','','')").executeUpdate();
//            recNo = fts.getRecNo();
//            var2 = em.createNativeQuery("Insert into gl_recon(acno,ty,dt,valuedt,dramt,cramt,trantype,trandesc,details,iy,instno,enterby,auth,recno,"
//                    + "payby,authby,trsno,trantime,org_brnid,dest_brnid,adviceNo,adviceBrnCode) "
//                    + "values ('" + drglhead + "',1,'" + todayDate + "','" + todayDate + "'," + total + "," + 0 + ",8,3,'Int Prov Posting For DS',0,''"
//                    + ",'" + userName + "','Y'," + recNo + ",3,''," + trsNo + ",now(),'" + brncode + "','" + brncode + "','','')").executeUpdate();
//            var3 = em.createNativeQuery("insert into int_post_history (actype,todate,fromdate,enterby,auth,trantime,authby,brncode) values"
//                    + "('" + type + "','" + toDate + "','" + fromDate + "','" + userName + "','Y','" + todayDate + "','','" + brncode + "')").executeUpdate();
//
//            if (var <= 0 || var1 <= 0 || var2 <= 0 || var3 <= 0) {
//                throw new ApplicationException("Problem in interest Posting");
//            }
//            ut.commit();
//            return "Interest Provisioning completed successfully ! Generated Batch No. is " + trsNo;
//        } catch (Exception e) {
//            try {
//                ut.rollback();
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//            throw new ApplicationException(e.getMessage());
//        }
//
//    }
    /**
     * ************* Added Code Start For DDS Int Posting After TDS Deduction
     * *****************************
     */
    public String post(List<InterestProvisionTable> list, String type, Date fromDate, Date toDate, String brncode, String userName) throws ApplicationException {
        String todayDate = "";

        List query = em.createNativeQuery("Select glheadint,glheadprov from accounttypemaster where acctcode='" + type + "'").getResultList();
        if (query.isEmpty()) {
            throw new ApplicationException("Provision and interest Head does not defined");
        }
        Vector vec1 = (Vector) query.get(0);
        String drglhead = brncode + (vec1.get(0).toString()) + "01";
        String crglhead = brncode + (vec1.get(1).toString()) + "01";

        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            float trsNo = fts.getTrsNo();
            float recNo = fts.getRecNo();
            double totalTds = 0d;
            String glAccNo = null, tdsPostEnable = "N";
            double totalInterest = 0d;
            for (InterestProvisionTable ddsIntDetail : list) {
                totalInterest = totalInterest + ddsIntDetail.getIamt();
            }

            fts.updateBalance("PO", drglhead, 0, totalInterest, "", "");

            Integer reconBalanL = em.createNativeQuery("Insert Into gl_recon (acno,dt,ValueDt,dramt,ty,trantype,details,auth,authby,enterBy,"
                    + "recno,trsno,payby,trandesc,org_brnid,dest_brnid) values('" + drglhead + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                    + totalInterest + ",1,8,'Interest Amount','Y','" + userName + "','" + userName + "'," + recNo + "," + trsNo + ",3,3,'" + brncode
                    + "','" + brncode + "')").executeUpdate();
            if (reconBalanL <= 0) {
                return "Error in reconbalan Insertion !!!";
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            todayDate = (sdf.format(new Date()));
            for (InterestProvisionTable pojo : list) {
                double interest = pojo.getIamt();
                String acNo = pojo.getAcno();
                String fromDt = pojo.getFromDt();
                String toDt = pojo.getToDt();

                recNo = fts.getRecNo();

                Integer ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                        + " details, iy, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                        + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 0,'" + todayDate + "','" + todayDate + "',0,"
                        + "" + interest + ",0,8,'Int Posted for " + fromDt + "To " + toDt + "',1,'" + userName + "','Y', "
                        + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                        + "'" + brncode + "','" + brncode + "',3,'','','','')").executeUpdate();
                if (ddsTran <= 0) {
                    return "Error in tdRecon Insertion";
                }

                /**
                 * ***TDS Deduction from account **************
                 */
                List tdsPostEnableList = em.createNativeQuery("select code from cbs_parameterinfo where name = 'TDS_POST_BEFORE_INT_CALC'  ").getResultList();
                if (!tdsPostEnableList.isEmpty()) {
                    Vector tdsPostEnableVect = (Vector) tdsPostEnableList.get(0);
                    tdsPostEnable = tdsPostEnableVect.get(0).toString();
                }
                double tdsAmt = 0;
                if (tdsPostEnable.equalsIgnoreCase("Y")) {
                    List acWiseTdsList = em.createNativeQuery("select ifnull(sum(ifnull(tds,0)),0) from tds_reserve_history where acno = '" + acNo + "' and recovered ='NR' and todt<='" + toDt + "'").getResultList();
                    if (acWiseTdsList.size() > 0) {
                        Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
                        tdsAmt = Double.parseDouble(acWiseTdsVect.get(0).toString());
                        totalTds = totalTds + Double.parseDouble(acWiseTdsVect.get(0).toString());
                        if (tdsAmt != 0) {
                            recNo = fts.getRecNo();

                            /**
                             * Code Add On 20150624 *
                             */
                            List tdsCloseList = em.createNativeQuery("select tds from tds_reserve_history a, accountmaster b where a.acno = b.acno "
                                    + " and a.recovered ='NR' and b.accstatus =9 "
                                    + " and a.acno = '" + acNo + "' order by txnid").getResultList();
                            for (int cnt = 0; cnt < tdsCloseList.size(); cnt++) {
                                Vector tdsCloseVect = (Vector) tdsCloseList.get(cnt);
                                double cltds = Double.parseDouble(tdsCloseVect.get(0).toString());
                                tdsAmt = tdsAmt + cltds;
                                if (tdsAmt < interest) {
                                    totalTds = totalTds + cltds;
                                    Query updateTdsQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsNo + ", recno = " + recNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                            + " where acno = '" + acNo + "' and recovered ='NR' and todt<='" + toDt + "'");
                                    int result = updateTdsQuery.executeUpdate();
                                    if (result < 0) {
                                        totalTds = totalTds - tdsAmt;
                                        return "Error in updating tdshistory for tds ";
                                    }
                                } else {
                                    tdsAmt = tdsAmt - cltds;
                                    break;
                                }
                            }

                            /**
                             * End Of Code Add On 20150624 *
                             */
                            ddsTran = em.createNativeQuery("insert into ddstransaction( acno , ty, dt, valueDt, Dramt, CrAmt, Balance, TranType,"
                                    + " details, iy, InstNo, instDt, EnterBy,Auth, recno, payby, Authby, trsno, org_brnid,dest_brnid,"
                                    + "trandesc,tokenpaid,favorof,CheckBy,RECEIPTNO)values('" + acNo + "', 1,'" + todayDate + "','" + todayDate + "'," + tdsAmt + ","
                                    + "0,0,8,'Tds Decucted for " + fromDt + " To " + toDt + "',1,'',null,'" + userName + "','Y', "
                                    + "" + recNo + ", 3,'" + userName + "'," + trsNo + ","
                                    + "'" + brncode + "','" + brncode + "',3,'','','','')").executeUpdate();
                            if (ddsTran <= 0) {
                                return "Error in tdRecon Insertion";
                            }

                            Query updateQuery = em.createNativeQuery("Update tds_reserve_history Set  recovered='R', trsno = " + trsNo + ", recno = " + recNo + ", tdsRecoveredDt = date_format(now(),'%Y%m%d') "
                                    + " where acno = '" + acNo + "' and recovered ='NR' and todt<='" + toDt + "'");
                            int result = updateQuery.executeUpdate();
                            if (result < 0) {
                                return "Error in updating tdshistory for tds ";
                            }

                            Integer TDSHistory = em.createNativeQuery("INSERT INTO tdshistory(acno,voucherno,tds,dt,Todt,fromdt,intOpt)"
                                    + "VALUES('" + acNo + "',0," + tdsAmt + ",DATE_FORMAT(NOW(),'%Y%m%d'),'" + ymd.format(dmy.parse(toDt)) + "','" + ymd.format(dmy.parse(fromDt)) + "','')").executeUpdate();
                            if (TDSHistory <= 0) {
                                throw new ApplicationException("Data Not Saved For " + acNo);
                            }
                        }
                    }
                }
                /**
                 * ***END of TDS Deduction from account **************
                 */
                Integer interestHistory = em.createNativeQuery("Insert into dds_interesthistory values('" + acNo + "','" + interest + "','" + todayDate + "','" + ymd.format(fromDate)
                        + "','" + ymd.format(toDate) + "', now(),'" + userName + "')").executeUpdate();
                if (interestHistory <= 0) {
                    return "Error in TD_InterestHistory Insertion";
                }

                Query updateQuery1 = em.createNativeQuery("update cbs_loan_acc_mast_sec set int_calc_upto_dt='" + ymd.format(toDate) + "', "
                        + " next_int_calc_dt='" + CbsUtil.dateAdd(ymd.format(toDate), 1) + "' where acno = '" + acNo + "'");
                int result = updateQuery1.executeUpdate();
                if (result < 0) {
                    return "Error in updating Next Interest Payable Date";
                }
            }

            /**
             * *************Total TDS posting in GL Recon**************
             */
            if (tdsPostEnable.equalsIgnoreCase("Y")) {
                List chk3 = em.createNativeQuery("Select TDS_GLHead From tdsslab Where TDS_Applicabledate In (select max(TDS_Applicabledate) from tdsslab "
                        + "where TDS_Applicabledate<='" + toDate + "')").getResultList();
                if (!chk3.isEmpty()) {
                    Vector chk3V = (Vector) chk3.get(0);
                    glAccNo = brncode + chk3V.get(0).toString();
                }
                Integer reconBalan = em.createNativeQuery("UPDATE reconbalan SET BALANCE=BALANCE+" + totalTds + ",DT=date_format(now(),'%Y%m%d')  WHERE ACNO='" + glAccNo + "'").executeUpdate();
                if (reconBalan <= 0) {
                    return "Error in GL Balance Updation !!!";
                }

                if (totalTds > 0) {
                    recNo = fts.getRecNo();
                    Integer glrecon = em.createNativeQuery("INSERT INTO gl_recon (ACNO,Dt,ValueDt,CrAmt,Ty,TranType,Details,EnterBy,"
                            + "Auth,AuthBy,TranTime,RecNo,trsno,payby,TRANDESC,IY,org_brnid,dest_brnid) VALUES('" + glAccNo + "',date_format(now(),'%Y%m%d')," + "date_format(now(),'%Y%m%d'),"
                            + "" + totalTds + ",0,2, CONCAT('TDS POSTING TILL DT ' , date_format('" + ymd.format(toDate) + "','%Y%m%d')),'" + userName + "','Y','" + userName + "',now(),'" + recNo + "',"
                            + "'" + trsNo + "',3,33,1,'" + brncode + "','" + brncode + "')").executeUpdate();
                    if (glrecon <= 0) {
                        return "Error in TDS Posting in GL !!!";
                    }
                }
            }

            /**
             * *************END of Total TDS posting in GL Recon**************
             */
            /* Updation For NextIntPayDt For All Accounts */
//            Query updateQuery1 = em.createNativeQuery("update cbs_loan_acc_mast_sec tv inner join accountmaster tm on tv.acno = tm.acno "
//                    + "set int_calc_upto_dt='" + ymd.format(toDate) + "', next_int_calc_dt='" + CbsUtil.dateAdd(ymd.format(toDate), 1) + "' where tm.accttype = '" + type + "' and tm.curbrcode = '" + brncode
//                    + "' and next_int_calc_dt <='" + ymd.format(toDate) + "'");
//            int result = updateQuery1.executeUpdate();
//            if (result < 0) {
//                return "Error in updating Next Interest Payable Date";
//            }
            Query updateAccTypeIntParaQuery = em.createNativeQuery("UPDATE cbs_loan_acctype_interest_parameter  SET POST_FLAG = 'Y', POST_DT = NOW(), ENTER_BY = '" + userName + "'  "
                    + "WHERE AC_TYPE = '" + type + "' AND FROM_DT = '" + ymd.format(fromDate) + "' and TO_DT = '" + ymd.format(toDate) + "' and flag = 'I' and brncode = '" + brncode + "'");
            Integer updateAccTypeIntPara = updateAccTypeIntParaQuery.executeUpdate();
            if (updateAccTypeIntPara < 0) {
                return "Error in updating cbs_loan_acctype_interest_parameter";
            }

            ut.commit();
            return "Interest Posting completed successfully ! Generated Batch No. is " + trsNo;
        } catch (Exception e) {
            try {
                ut.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
            throw new ApplicationException(e.getMessage());
        }

    }

    /**
     * ************* Added Code End For DDS Int Posting After TDS Deduction
     * *****************************
     */
    /**
     * ***************************TransactionDeleteBean's
     * Methods*************************
     */
    public List getAgCode(String brncode) throws ApplicationException {
        try {
            List agCodeList = em.createNativeQuery("select agcode,name from ddsagent where brncode='" + brncode + "'").getResultList();
            return agCodeList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getCodeFromParameterInfoReportTable() throws ApplicationException {
        try {
            return em.createNativeQuery("select code from parameterinfo_report where reportname='CASH DENOMINITION MODULE'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getUnauthorizedDDSEntries(String agCode, String brnCode) throws ApplicationException {
        List acTypeList = new ArrayList();
        try {
            //String DsActNature = CbsConstant.DEPOSIT_SC;

            //Boolean[] arr =  fts.ddsCashReceivedFlag(brnCode);
            // if (vec.get(0).toString().equalsIgnoreCase("Y")) {
            acTypeList = em.createNativeQuery("select * from dds_auth_d where ifnull(auth,'N') = 'N' and ifnull(tokenpaid,'N') = 'N' and substring(acno,11,2)='" + agCode + "' and "
                    + "acno in ( select acno from accountmaster where curbrcode ='" + brnCode + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + CbsConstant.DEPOSIT_SC + "') ) order by receiptno ").getResultList();
            return acTypeList;
            //   } else {
//                acTypeList = em.createNativeQuery("select * from dds_auth_d where ifnull(auth,'N') = 'N' and tokenpaid = 'Y' and substring(acno,11,2)='" + agCode + "' and acno"
//                        + " in (select acno from accountmaster where curbrcode ='" + brnCode + "' and substring(acno,3,2) in (select acctcode from accounttypemaster where acctnature ='" + DsActNature + "') ) order by receiptno ").getResultList();
//                return acTypeList;
            //   }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String deleteDDSEntries(String acno, float recno, String receiptno, String tokenPaid, float newAmount, int tokenNo, float cramt, String dt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            Query q = em.createNativeQuery("delete from dds_auth_d where acno='" + acno + "' and recno=" + recno + " and receiptno='" + receiptno + "'");
            int rowAffected = q.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in transaction deletion");
            }
            if (tokenPaid.equalsIgnoreCase("Y")) {
                Query q1 = em.createNativeQuery("update tokentable_credit set cramt=" + newAmount + " where tokenno=" + tokenNo + " and dt='" + dt + "' and cramt=" + cramt + "");
                q1.executeUpdate();

            }
//            //Denomination deletion 12/01/2018
//            String denoMessage = interBranchFacade.deleteDenomination(recno, dt);
//            if (!denoMessage.equalsIgnoreCase("true")) {
//                throw new ApplicationException(denoMessage);
//            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    public String deleteAllDdsEntries(String agCode, String tokenPaid, int tokenNo, String dt, float crAmt, String brCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //List recNoList = em.createNativeQuery("select recno from dds_auth_d where substring(acno,11,2)='" + agCode + "' and tokenpaid='" + tokenPaid + "'").getResultList();

            Query q = em.createNativeQuery("delete from dds_auth_d where substring(acno,11,2)='" + agCode + "' and tokenpaid='" + tokenPaid + "' and substring(acno,1,2) = '" + brCode + "'");
            int rowAffected = q.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in transaction deletion");
            }

            if (tokenPaid.equalsIgnoreCase("Y")) {
                Query q1 = em.createNativeQuery("delete from tokentable_credit where tokenno=" + tokenNo + " and dt='" + dt + "' and cramt=" + crAmt + "");
                q1.executeUpdate();
            }
//            for (int i = 0; i < recNoList.size(); i++) {
//
//                Vector recNoVect = (Vector) recNoList.get(i);
//                float recNo = Float.parseFloat(recNoVect.get(0).toString());
//                //Denomination deletion 12/01/2018
//                String denoMessage = interBranchFacade.deleteDenomination(recNo, dt);
//                if (!denoMessage.equalsIgnoreCase("true")) {
//                    throw new ApplicationException(denoMessage);
//                }
//            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
    }

    /**
     *
     * @param acNature
     * @return
     * @throws ApplicationException
     */
    public String getAccountCode(String acNature) throws ApplicationException {
        String accode = "";
        try {
            List codeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature = '" + acNature + "'").getResultList();
            if (!codeList.isEmpty()) {
                Vector element = (Vector) codeList.get(0);
                accode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return accode;
    }

    /**
     *
     * @param acNo
     * @param dt
     * @param cramt
     * @param receiptNo
     * @param enterBy
     * @param recno
     * @param tokenPaid
     * @return
     * @throws ApplicationException
     */
    public String pushPcrxData(String acNo, String dt, double cramt, String receiptNo, String enterBy, float recno, String tokenPaid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        try {
            ut.begin();
            Query q = em.createNativeQuery("insert into dds_auth_d(ACNo,Ty,Dt,dramt,CrAmt,TranType,ReceiptNo,EnterBy,Recno,TokenPaid) "
                    + "values ('" + acNo + "',0,'" + dt + "',0," + cramt + ",0,'" + receiptNo + "','" + enterBy + "'," + recno + ",'" + tokenPaid + "')");
            int rowAffected = q.executeUpdate();
            if (rowAffected <= 0) {
                throw new ApplicationException("Problem in data insertion");
            }
            ut.commit();
            result = "true";
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

    public List<String> generateDDSOutwardFile(String accode, String branchCode, String agentCode, String days,
            String bankCode, String password) throws ApplicationException {
        SimpleDateFormat dmy = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        List<String> outList = new ArrayList<String>();
        String outStr = "";
        try {
            /**
             * *Preparation of List to generate the file**
             */
            List accountList = em.createNativeQuery("select acno,custname,openingdt from accountmaster "
                    + "where accttype='" + accode + "' and curbrcode='" + branchCode + "' and "
                    + "substring(acno,11,2)='" + agentCode + "' and rdmatdate>DATE_FORMAT(now(),'%Y%m%d') "
                    + "and accstatus<>9 and (closingdate is null or closingdate = '' or closingdate='null')").getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file");
            }

            /**
             * *Creation of first line**
             */
            String lastAccountNo = "";
            Integer listSize = accountList.size();
            for (int i = 0; i < listSize; i++) {
                Vector element = (Vector) accountList.get(i);
                if (i == (listSize - 1)) {
                    lastAccountNo = element.get(0).toString().trim();
                }
            }

            if (bankCode.equalsIgnoreCase("eucb")
                    || bankCode.equalsIgnoreCase("sric")
                    || bankCode.equalsIgnoreCase("nabu")
                    || bankCode.equalsIgnoreCase("hisr")) {//Jamshedpur-Balaji
                String tempAgCode = "0" + agentCode.trim();
                outStr = lastAccountNo.substring(4, 10) + ",000000,0000000000000000," + tempAgCode + tempAgCode + "," + dmy.format(new Date()) + "," + "12341234";
            } else {
                outStr = ParseFileUtil.addTrailingZeros(branchCode, 4) + "," + ParseFileUtil.addTrailingZeros(agentCode, 3) + ","
                        + lastAccountNo.substring(4, 10) + "," + dmyy.format(new Date()) + ",0000,00000000,"
                        + password + "," + ParseFileUtil.addTrailingZeros(listSize.toString(), 3)
                        + "," + days + ",00000000000000000000000000000000000";
            }
            outList.add(outStr);

            /**
             * *For rest of line**
             */
            for (int i = 0; i < listSize; i++) {
                Vector element = (Vector) accountList.get(i);
                String acno = element.get(0).toString().trim();
                String custname = element.get(1).toString().trim();
                String openingDt = element.get(2).toString().trim();

                //Custname
                custname = ParseFileUtil.addSuffixSpaces(custname, 16);
                //Account Balance
                String accBal = fts.ftsGetBal(acno).trim();
                if (accBal.contains(".")) {
                    accBal = accBal.substring(0, accBal.indexOf("."));
                }
                if (bankCode.equalsIgnoreCase("eucb")
                        || bankCode.equalsIgnoreCase("sric")
                        || bankCode.equalsIgnoreCase("nabu")
                        || bankCode.equalsIgnoreCase("hisr")) {//Jamshedpur-Balaji
                    accBal = ParseFileUtil.addTrailingZeros(accBal, 6);
//                    outStr = acno.substring(4, 10) + ",000000," + custname.toUpperCase() + "," + accBal + ","
//                            + dmy.format(new Date()) + ",00000000";
                    outStr = acno.substring(4, 10) + ",000000," + custname.toUpperCase() + "," + accBal + ","
                            + dmy.format(ymd.parse(openingDt)) + ",00000000";
                } else {
                    accBal = ParseFileUtil.addTrailingZeros(accBal, 7);
                    outStr = acno.substring(4, 10) + ",000000,000000,000000,000000,000000,000000,000000,"
                            + custname.toUpperCase() + "," + accBal + "," + dmyy.format(ymd.parse(openingDt));
                }
                outList.add(outStr);
            }
            return outList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());

        }
    }

    // New prati nidhi for mode 2 
    public List<String> pratiNidhiNewDDSOutwardFileForMode2(String accode, String branchCode, String agentCode,
            String days, String bankCode, String password) throws ApplicationException {
        SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yy");
        List<String> outList = new ArrayList<String>();
        String outStr = "";
        try {
            List agentList = em.createNativeQuery("select agent_acno from ddsagent where Agcode='" + agentCode + "' and brncode='" + branchCode + "'").getResultList();
            if (agentList.isEmpty()) {
                throw new ApplicationException("There are no agent account exist in this branch.");
            }
            Vector vc = (Vector) agentList.get(0);
            String agentAcNo = vc.get(0).toString();
            if (agentAcNo.equalsIgnoreCase("")) {
                throw new ApplicationException("There is no account exist for this agent.");
            }
            // Prepeation of list to generate the file
            List accountList = em.createNativeQuery("select acno,custname,openingdt,ifnull(RdInstal,'') from accountmaster "
                    + "where accttype='" + accode + "' and curbrcode='" + branchCode + "' and "
                    + "substring(acno,11,2)='" + agentCode + "' and rdmatdate>DATE_FORMAT(now(),'%Y%m%d') "
                    + "and accstatus<>9 and (closingdate is null or closingdate = '' or closingdate='null')").getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file");
            }
            String lastAccountNo = "";
            Integer listSize = accountList.size();
            for (int i = 0; i < listSize; i++) {
                Vector element = (Vector) accountList.get(i);
                if (i == (listSize - 1)) {
                    lastAccountNo = element.get(0).toString().trim();
                }
            }

            double regularAmt = 0d;
            double odlimit = 0d;
            double acBalance = 0d;
            String acNature = fts.getAccountNature(agentAcNo);

            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                List list = em.createNativeQuery("select rb.Balance,am.ODLimit from reconbalan rb,accountmaster am where "
                        + "rb.acno = am.ACNo and rb.acno ='" + agentAcNo + "'").getResultList();
                Vector v = (Vector) list.get(0);
                acBalance = Double.parseDouble(v.get(0).toString());
                odlimit = Double.parseDouble(v.get(1).toString());
                regularAmt = acBalance + odlimit;
            } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List list2 = em.createNativeQuery("select rb.Balance,am.ODLimit from ca_reconbalan rb,accountmaster am where "
                        + "rb.acno = am.ACNo and rb.acno ='" + agentAcNo + "'").getResultList();
                Vector v1 = (Vector) list2.get(0);
                acBalance = Double.parseDouble(v1.get(0).toString());
                odlimit = Double.parseDouble(v1.get(1).toString());
                regularAmt = acBalance + odlimit;
            } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List list2 = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) as balance "
                        + "from gl_recon where auth= 'Y' and acno ='" + agentAcNo + "'").getResultList();
                Vector v1 = (Vector) list2.get(0);
                regularAmt = Double.parseDouble(v1.get(0).toString());
            }

            String finalAgentLimit = "";
            finalAgentLimit = String.valueOf(regularAmt);
            if (finalAgentLimit.contains(".")) {
                finalAgentLimit = finalAgentLimit.substring(0, finalAgentLimit.indexOf("."));
            }
            finalAgentLimit = ParseFileUtil.addTrailingZerosWithoutDecimal(finalAgentLimit, 8);

            //Creation of first line
            outStr = ParseFileUtil.addTrailingZeros(branchCode, 4) + "," + ParseFileUtil.addTrailingZeros(agentCode, 3) + ","
                    + lastAccountNo.substring(4, 10) + "," + dmyy.format(new Date()) + ",0000,00000000," + password + ","
                    + ParseFileUtil.addTrailingZeros(String.valueOf(accountList.size()), 3) + "," + days + ","
                    + "" + finalAgentLimit + "";

            outList.add(outStr);

            //Agent dds limit calculation
            double bankDdsLmit = 0d;
            List bankDdsLimit = em.createNativeQuery("select CODE from cbs_parameterinfo where name='BANK-DDS-LIMIT'").getResultList();
            if (!bankDdsLimit.isEmpty()) {
                Vector vr = (Vector) bankDdsLimit.get(0);
                bankDdsLmit = Double.parseDouble(vr.get(0).toString());
            } else {
                throw new ApplicationException("Please define the Bank dds Limit .");
            }

            //Calculating first and last date of previous month.
            String[] arr = CbsUtil.calculateIstAndLastDateOfPreviousMonth();
            for (int i = 0; i < accountList.size(); i++) {
                Vector element = (Vector) accountList.get(i);
                String acno = element.get(0).toString().trim();
                String custname = element.get(1).toString().trim();
                String openingDt = element.get(2).toString().trim();
                String rdInstall = element.get(3).toString().trim();
                if (rdInstall.contains(".")) {
                    rdInstall = rdInstall.substring(0, rdInstall.indexOf("."));
                }
                rdInstall = ParseFileUtil.addTrailingZeros(rdInstall, 6);
                String maxInstallment = "";
                double maxInstlmnt = 0d;
                if (!(bankDdsLmit == 0.0)) {
                    maxInstlmnt = bankDdsLmit / Double.parseDouble(rdInstall);
                    if (maxInstlmnt > 99) {
                        maxInstallment = "99";
                    } else {
                        String maxib = String.valueOf(maxInstlmnt);
                        if (String.valueOf(maxInstlmnt).contains(".")) {
                            String[] arrAmt = maxib.split("\\.");
                            maxInstallment = arrAmt[0];
                        }
                    }
                }
                maxInstallment = ParseFileUtil.addTrailingZeros(maxInstallment, 2);
                //Last month balance
                String lastMonthBal = commonRemote.getBalanceOnDate(acno, arr[1]).toString();
                if (lastMonthBal.contains(".")) {
                    lastMonthBal = lastMonthBal.substring(0, lastMonthBal.indexOf("."));
                }
                lastMonthBal = ParseFileUtil.addTrailingZeros(lastMonthBal, 8);
                //Custname
                custname = custname.replaceAll("[\\W_]", " ");
                custname = custname.length() > 16 ? custname.substring(0, 16) : custname;
                custname = ParseFileUtil.addSuffixSpaces(custname, 16);
                //Current month accumulated balance
                String currentDt = ymd.format(new Date());
                String curMonthAccumulatedAmount = commonRemote.getBalanceInBetweenDate(acno, currentDt.substring(0, 6) + "01", currentDt).toString();
                if (curMonthAccumulatedAmount.contains(".")) {
                    curMonthAccumulatedAmount = curMonthAccumulatedAmount.substring(0, curMonthAccumulatedAmount.indexOf("."));
                }
                curMonthAccumulatedAmount = ParseFileUtil.addTrailingZeros(curMonthAccumulatedAmount, 7);

                outStr = acno.substring(4, 10) + ",000000,000000,000000,000000,000000," + rdInstall + "," + maxInstallment + "," + lastMonthBal + ","
                        + custname.toUpperCase() + "," + curMonthAccumulatedAmount + "," + dmyy.format(ymd.parse(openingDt));

                outList.add(outStr);

            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return outList;
    }

    //New Prati Nidhi Pune Machine
    public List<String> pratiNidhiNewDDSOutwardFile(String accode, String branchCode, String agentCode,
            String days, String bankCode, String password) throws ApplicationException {
        SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yy");
        List<String> outList = new ArrayList<String>();
        String outStr = "";
        try {
            //Preparation of List to generate the file
            List accountList = em.createNativeQuery("select acno,custname,openingdt from accountmaster "
                    + "where accttype='" + accode + "' and curbrcode='" + branchCode + "' and "
                    + "substring(acno,11,2)='" + agentCode + "' and rdmatdate>DATE_FORMAT(now(),'%Y%m%d') "
                    + "and accstatus<>9 and (closingdate is null or closingdate = '' or closingdate='null')").getResultList();
            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file");
            }

            String lastAccountNo = "";
            Integer listSize = accountList.size();
            for (int i = 0; i < listSize; i++) {
                Vector element = (Vector) accountList.get(i);
                if (i == (listSize - 1)) {
                    lastAccountNo = element.get(0).toString().trim();
                }
            }
            //Creation of first line
            outStr = ParseFileUtil.addTrailingZeros(branchCode, 4) + "," + ParseFileUtil.addTrailingZeros(agentCode, 3) + ","
                    + lastAccountNo.substring(4, 10) + "," + dmyy.format(new Date()) + ",0000,00000000," + password + ","
                    + ParseFileUtil.addTrailingZeros(String.valueOf(accountList.size()), 3) + "," + days + ",00000000";

            outList.add(outStr);
            //Calculating first and last date of previous month.
            String[] arr = CbsUtil.calculateIstAndLastDateOfPreviousMonth();
            //For rest of line
            for (int i = 0; i < accountList.size(); i++) {
                Vector element = (Vector) accountList.get(i);
                String acno = element.get(0).toString().trim();
                String custname = element.get(1).toString().trim();
                String openingDt = element.get(2).toString().trim();
                //Last month balance
                String lastMonthBal = commonRemote.getBalanceOnDate(acno, arr[1]).toString();
                if (lastMonthBal.contains(".")) {
                    lastMonthBal = lastMonthBal.substring(0, lastMonthBal.indexOf("."));
                }
                lastMonthBal = ParseFileUtil.addTrailingZeros(lastMonthBal, 8);
                //Custname
                custname = custname.replaceAll("[\\W_]", " ");
                custname = custname.length() > 16 ? custname.substring(0, 16) : custname;
                custname = ParseFileUtil.addSuffixSpaces(custname, 16);
                //Current month accumulated balance
                String currentDt = ymd.format(new Date());
                String curMonthAccumulatedAmount = commonRemote.getBalanceInBetweenDate(acno, currentDt.substring(0, 6) + "01", currentDt).toString();
                if (curMonthAccumulatedAmount.contains(".")) {
                    curMonthAccumulatedAmount = curMonthAccumulatedAmount.substring(0, curMonthAccumulatedAmount.indexOf("."));
                }
                curMonthAccumulatedAmount = ParseFileUtil.addTrailingZeros(curMonthAccumulatedAmount, 7);

                outStr = acno.substring(4, 10) + ",000000,000000,000000,000000,000000,000000,00," + lastMonthBal + ","
                        + custname.toUpperCase() + "," + curMonthAccumulatedAmount + "," + dmyy.format(ymd.parse(openingDt));

                outList.add(outStr);
            }
            return outList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());

        }
    }

    public List<DdsTransactionGrid> getAgentUnAuthorizedTransaction(String orgBrCode, String agentCode, String acType, String agentName) throws ApplicationException {
        List<DdsTransactionGrid> dataList = new ArrayList<DdsTransactionGrid>();
        try {
            List ddsAccountList = em.createNativeQuery("select acno,cramt,receiptno,date_format(dt,'%d/%m/%Y') from dds_auth_d where substring(acno,1,2)='" + orgBrCode + "' and "
                    + "substring(acno,3,2)='" + acType + "' and substring(acno,11,2)='" + agentCode + "' and (auth is null or auth='') "
                    + "and (tokenpaidby is null or tokenpaidby='')").getResultList();

            if (!ddsAccountList.isEmpty()) {
                for (int i = 0; i < ddsAccountList.size(); i++) {
                    Vector element = (Vector) ddsAccountList.get(i);
                    String acno = element.get(0).toString();
                    String cramt = element.get(1).toString();
                    String receiptNo = element.get(2).toString();
                    String dt = element.get(3).toString();

                    String accountName = "";
                    List accountNameList = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
                    if (!accountNameList.isEmpty()) {
                        element = (Vector) accountNameList.get(0);
                        accountName = element.get(0).toString();
                    }

                    DdsTransactionGrid pojo = new DdsTransactionGrid();

                    pojo.setAccountNo(acno);
                    pojo.setAgentCode(agentCode);
                    pojo.setAmount(cramt);
                    pojo.setDate(dt);
                    pojo.setReceiptNo(receiptNo);
                    pojo.setAgentName(agentName);
                    pojo.setName(accountName);

                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String deleteDDSAccountTransaction(String acno, String date, String receiptNo) throws ApplicationException {
        String result = "false";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int deleteResult = em.createNativeQuery("delete from dds_auth_d where acno='" + acno + "' and dt='" + date + "' and receiptno = '" + receiptNo + "'").executeUpdate();
            if (deleteResult <= 0) {
                throw new ApplicationException("Account is not deleted properly.");
            }
            result = "true";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public String updateDDSAccountTransaction(String acno, String date, String receiptNo, double amount) throws ApplicationException {
        String result = "false";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int updateResult = em.createNativeQuery("update dds_auth_d set cramt = " + amount + " where acno='" + acno + "' and dt='" + date + "' and receiptno = '" + receiptNo + "'").executeUpdate();
            if (updateResult <= 0) {
                throw new ApplicationException("Account is not updated properly.");
            }
            result = "true";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public String claimDeafAccount(String deafAcno, String userName, String curDt,
            String orgnBrCode, double deafAmount, String deafDt, double vchNo, double savingRoi, String fyear, String instCode) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String acType = fts.getAccountCode(deafAcno);

            if (instCode == null) {
                instCode = "";
            }

//            List list = em.createNativeQuery("select ifnull(glheadint,'') as int_head,ifnull(unclaimed_head,'') as "
//                    + "unclaimed_head from accounttypemaster where acctcode='" + acType + "'").getResultList();
            List list = em.createNativeQuery("select ifnull(glheadint,'') as int_head,ifnull(claimed_head,'') as "
                    + "claimed_head from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Please define interest head for A/c code-->" + acType);
            }
            Vector element = (Vector) list.get(0);
            String intGlHead = orgnBrCode + element.get(0).toString() + "01";
            String claimedHead = orgnBrCode + element.get(1).toString();
            if (intGlHead.equals("") || intGlHead.length() != 12 || claimedHead.equals("") || claimedHead.length() != 12) {
                throw new ApplicationException("Please define proper Int and Unclaimed Gl head.");
            }

            Float trsno = fts.getTrsNo();
            String partyAcNature = fts.getAccountNature(deafAcno);
            String glNature = fts.getAccountNature(claimedHead);
            String closeFlag = null;

            if (deafAmount > 0) {
                msg = fts.insertRecons(partyAcNature, deafAcno, 0, deafAmount, curDt, curDt, 2,
                        "Deaf Amt Trf In Claiming", userName, trsno, "", fts.getRecNo(), "Y",
                        userName, 0, 3, "", curDt, 0f, "", "", 0, "", 0f, "", closeFlag, orgnBrCode,
                        orgnBrCode, 0, "", "", "");

                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.updateBalance(partyAcNature, deafAcno, deafAmount, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.insertRecons(glNature, claimedHead, 1, deafAmount, curDt, curDt, 2,
                        "Deaf Amt Trf In Claiming", userName, trsno, "", fts.getRecNo(), "Y",
                        userName, 0, 3, "", curDt, 0f, "", "", 0, "", 0f, "", "", orgnBrCode,
                        orgnBrCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.updateBalance(glNature, claimedHead, 0, deafAmount, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }
            //Interest Posting If Any.
            double totalInterest = 0;
            String fromDt = CbsUtil.dateAdd(deafDt, 1);
            Long dateDiff = CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(curDt));

            if (!(acType.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode()))) {

                if (dateDiff > 0) {
                    if (partyAcNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                        List<LoanIntCalcList> resultList = intRemote.cbsSbIntCalc("I", "", acType, deafAcno,
//                                dmy.format(ymd.parse(fromDt)), dmy.format(ymd.parse(curDt)), orgnBrCode, deafAmount);

                        List<LoanIntCalcList> resultList = intRemote.cbsSbIntCalcForDeafClaim("I", "", acType, deafAcno,
                                dmy.format(ymd.parse(fromDt)), dmy.format(ymd.parse(curDt)), orgnBrCode, deafAmount, savingRoi);
                        if (!resultList.isEmpty()) {
                            for (LoanIntCalcList lict : resultList) {
                                totalInterest = lict.getTotalInt();
                            }
                        }
                    } else if (partyAcNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                            || partyAcNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(fromDt), ymd.parse(curDt));
                        if (savingDiff > 0) {
                            totalInterest = savingRoi * savingDiff.doubleValue() * deafAmount / 36500;
                        }
                    } else if (partyAcNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        if (acType.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                            String glHead = intLoanRemote.getGlHeads(acType);
                            List<LoanIntCalcList> resultList = intLoanRemote.cbsLoanIntCalc("I", acType, deafAcno, dmy.format(ymd.parse(fromDt)), dmy.format(ymd.parse(curDt)), glHead, "", orgnBrCode);
                            if (!resultList.isEmpty()) {
                                for (LoanIntCalcList lict : resultList) {
                                    totalInterest = lict.getTotalInt();
                                }
                            }
                        }
                    }
                }
            }

            if (totalInterest > 0) {
                msg = fts.insertRecons(partyAcNature, deafAcno, 0, Double.parseDouble(formatter.format(totalInterest)), curDt, curDt, 2,
                        "Int from " + dmy.format(ymd.parse(fromDt)) + " to" + dmy.format(ymd.parse(curDt)) + " in claimed",
                        userName, trsno, "", fts.getRecNo(), "Y", userName, 8, 3, "", curDt,
                        0f, "", "", 0, "", 0f, "", closeFlag, orgnBrCode, orgnBrCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.updateBalance(partyAcNature, deafAcno, Double.parseDouble(formatter.format(totalInterest)), 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.insertRecons(glNature, claimedHead, 1, Double.parseDouble(formatter.format(totalInterest)), curDt, curDt, 2,
                        "Int from " + dmy.format(ymd.parse(fromDt)) + " to" + dmy.format(ymd.parse(curDt)) + " in claimed",
                        userName, trsno, "", fts.getRecNo(), "Y", userName, 8, 3, "", curDt,
                        0f, "", "", 0, "", 0f, "", "", orgnBrCode, orgnBrCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.updateBalance(glNature, claimedHead, 0, Double.parseDouble(formatter.format(totalInterest)), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }
            //Table Insertion And Updation.
            int result = 0;
            if (partyAcNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                        + "enterby,auth,authby,trantime,effdt,baseacno,Seq_No,F_Year,InstCode) values ('" + deafAcno + "','To Be Claimed',"
                        + "'1','" + curDt + "',0,'" + userName + "','Y','" + userName + "',now(),'" + curDt + "',"
                        + "'" + deafAcno + "'," + vchNo + ",'" + fyear + "','" + instCode + "')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else {
                result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                        + "enterby,auth,authby,trantime,effdt,baseacno,voucher_no) values ('" + deafAcno + "','To Be Claimed',"
                        + "'1','" + curDt + "',0,'" + userName + "','Y','" + userName + "',now(),'" + curDt + "',"
                        + "'" + deafAcno + "'," + vchNo + ")").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            }

            if (!acType.equalsIgnoreCase(CbsAcCodeConstant.GL_ACCNO.getAcctCode())) {
                String accountTable = fts.getAccountTable(partyAcNature);
                if (partyAcNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || partyAcNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("update " + accountTable + " set accstatus=1 where "
                            + "acno='" + deafAcno + "'").executeUpdate();
                } else {
                    result = em.createNativeQuery("update " + accountTable + " set accstatus=1,optstatus = 1,last_txn_date=now() where "
                            + "acno='" + deafAcno + "'").executeUpdate();
                }
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Table Updation. " + deafAcno);
                }
            }
            double totalDeafAmount = deafAmount + totalInterest;

            result = em.createNativeQuery("insert into cbs_claimed_details(acno,claimed_amount,deaf_dt,claimed_dt,"
                    + "paid_dt,claimed_time,enter_by,F_Year,Seq_No,Deaf_Roi) VALUES('" + deafAcno + "'," + totalDeafAmount + ",'" + deafDt + "',"
                    + "'" + curDt + "','" + curDt + "',now(),'" + userName + "','" + fyear + "','" + vchNo + "'," + savingRoi + ")").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem In Cbs Claimed Table Insertion. " + deafAcno);
            }
            String acNature = "";
            if (partyAcNature.equalsIgnoreCase("PO")) {
                List acList = em.createNativeQuery("select InstNature from billtypemaster where glhead = '" + deafAcno.substring(2, 10) + "' and InstCode = '" + instCode + "'").getResultList();
                if (!acList.isEmpty()) {
                    Vector acVector = (Vector) acList.get(0);
                    acNature = acVector.get(0).toString();
                }
            }
            if (acNature.equalsIgnoreCase("PO")) {
                int result1 = em.createNativeQuery("update bill_po set STATUS = 'Issued',origindt = '" + curDt + "',validationdt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + "  ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else if (acNature.equalsIgnoreCase("DD")) {
                int result1 = em.createNativeQuery("update bill_dd set STATUS = 'Issued',origindt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + " ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else if (acNature.equalsIgnoreCase("AD")) {
                int result1 = em.createNativeQuery("update bill_ad set STATUS = 'Issued',origindt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + "  ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else if (acNature.equalsIgnoreCase("TPO")) {
                int result1 = em.createNativeQuery("update bill_tpo set STATUS = 'Issued',origindt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + " ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else if (acNature.equalsIgnoreCase("SUN")) {
                int result1 = em.createNativeQuery("update bill_sundry set STATUS = 'Issued',origindt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + " ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            } else if (acNature.equalsIgnoreCase("SUP")) {
                int result1 = em.createNativeQuery("update bill_suspense set STATUS = 'Issued',origindt = '" + curDt + "' where acno = '" + deafAcno + "' and status = 'Deaf' and seqno = " + vchNo + " and fyear = " + fyear + "  ").executeUpdate();
                if (result1 <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + deafAcno);
                }
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg = "true";
    }

    public List getDeafAccountDetail(String deafAcno, String year, String seqNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            String acNature = fts.getAccountNature(deafAcno);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                list = em.createNativeQuery("select amount,date_format(effdt,'%Y%m%d') as DEAF_DT,ifnull(voucher_no,0) as "
                        + "VCHNO from accountstatus a,td_accountmaster b where a.acno='" + deafAcno + "' and a.acno=b.acno and a.spflag = b.accstatus and a.spflag='15'").getResultList();
            } else if (acNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                list = em.createNativeQuery("select amount,date_format(dt,'%Y%m%d') as deaf_dt,ifnull(Seq_No,0) as seqNo,F_Year,InstCode from accountstatus "
                        + "where acno='" + deafAcno + "' and F_Year = " + year + " and Seq_No = " + seqNo + " and spflag='15'and spno in(select max(spno) from accountstatus where acno='" + deafAcno + "'and F_Year = " + year + " and Seq_No = " + seqNo + ")").getResultList();
            } else {
                list = em.createNativeQuery("select amount,date_format(effdt,'%Y%m%d') as DEAF_DT,"
                        + "ifnull(voucher_no,0) as VCHNO from accountstatus a,accountmaster b where a.acno='" + deafAcno + "' and a.acno=b.acno and a.spflag = b.accstatus and "
                        + "a.spflag='15' and a.spno in(select max(spno) from accountstatus where acno='" + deafAcno + "')").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public List getOtherUnclaimedAccountDetail(String orginBrCode, String acctNature, String acCode, String glAcno, String finalDeafDt, String todayDt) throws ApplicationException {
        List list = new ArrayList();
        try {

//            List dateList = commonRemote.getUnclaimedDate(frYear, toYear, asOnDt);
//            String fDt = dateList.get(0).toString();
//            String tDt = dateList.get(1).toString();
            // String glAccount = orginBrCode + glAcno + "01";
            String frDt = CbsUtil.yearAdd(finalDeafDt, -10);
            //String poddType = commonRemote.getPoddType(glAcno.substring(2, 10));

            if (acctNature.equalsIgnoreCase("PO")) {
                list = em.createNativeQuery("select fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') from bill_po where acno = '" + glAcno + "' and status = 'Issued' and ORIGINDT <= '" + frDt + "' order by origindt").getResultList();
            } else if (acctNature.equalsIgnoreCase("DD")) {
                list = em.createNativeQuery("select fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') from bill_dd where acno = '" + glAcno + "' and status = 'Issued' and ORIGINDT <= '" + frDt + "' order by origindt").getResultList();
            } else if (acctNature.equalsIgnoreCase("AD")) {
                list = em.createNativeQuery("SELECT fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') FROM bill_ad where acno = '" + glAcno + "' and status = 'ISSUED' and origindt <= '" + frDt + "' order by origindt").getResultList();
            } else if (acctNature.equalsIgnoreCase("TPO")) {
                list = em.createNativeQuery("SELECT fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') FROM bill_tpo where acno = '" + glAcno + "' and status = 'ISSUED' and origindt <= '" + frDt + "' order by origindt").getResultList();
            } else if (acctNature.equalsIgnoreCase("SUN")) {
                list = em.createNativeQuery("SELECT fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') FROM bill_sundry where acno = '" + glAcno + "' and status = 'ISSUED' and origindt <= '" + frDt + "' order by origindt").getResultList();
            } else if (acctNature.equalsIgnoreCase("SUP")) {
                list = em.createNativeQuery("SELECT fyear,acno,seqno,amount,status,date_format(origindt,'%d/%m/%Y'),ifnull(instno,'') FROM bill_suspense where acno = '" + glAcno + "' and status = 'ISSUED' and origindt <= '" + frDt + "' order by origindt").getResultList();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

        return list;
    }

    public String otherMarkUnClaimed(String reportBranchCode, String acctNature, String acCode,
            List<OtherUnclaimedDepositPojo> unClaimedList, String finalDeafEffDt, String intCalcDt,
            String today, String userName) throws ApplicationException {

        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String acType = fts.getAccountCode(unClaimedList.get(0).getGlAcno());

            List list = em.createNativeQuery("select ifnull(unclaimed_head,'') as "
                    + "unclaimed_head from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Please define interest head for A/c code-->" + acType);
            }

            Vector element = (Vector) list.get(0);
            // String intGlHead = reportBranchCode + element.get(0).toString() + "01";
            String unClaimedHead = reportBranchCode + element.get(0).toString();
            if (unClaimedHead.equals("") || unClaimedHead.length() != 12) {
                throw new ApplicationException("Please define proper Int and Unclaimed Gl head.");
            }

            Float trsno = fts.getTrsNo();
            // String partyAcNature = fts.getAccountNature(glAcno);
            String glNature = fts.getAccountNature(unClaimedHead);

            for (OtherUnclaimedDepositPojo pojo : unClaimedList) {
                String acNature = fts.getAccountNature(pojo.getGlAcno());

//                double updateBal = 0d;
//                list = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from gl_recon "
//                        + "where acno = '" + pojo.getGlAcno() + "'").getResultList();
//                element = (Vector) list.get(0);
//                double partyBal = Double.parseDouble(element.get(0).toString());
//                updateBal = partyBal;
                //Other Un-Claimed Marking Dr amount.
                msg = fts.insertRecons(glNature, pojo.getGlAcno(), 1, pojo.getAmount().doubleValue(), today, today, 2,
                        "Other Unclaimed Marking Dr Amount for instno:" + pojo.getInstNo(),
                        userName, trsno, "", fts.getRecNo(), "Y", userName, 0, 3, "", "",
                        0f, "", "", 0, "", 0f, "", "", reportBranchCode, reportBranchCode, 0, "", "", "");

                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = fts.updateBalance(acNature, pojo.getGlAcno(), 0, pojo.getAmount().doubleValue(), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                //Unclaimed Head Gl Cr amount.
                msg = fts.insertRecons(glNature, unClaimedHead, 0, pojo.getAmount().doubleValue(), today, today, 2,
                        "Other Unclaimed Marking Cr Amount for instno:" + pojo.getInstNo(),
                        userName, trsno, "", fts.getRecNo(), "Y", userName, 0, 3, "", "",
                        0f, "", "", 0, "", 0f, "", "", reportBranchCode, reportBranchCode, 0, "", "", "");

                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                msg = fts.updateBalance(acNature, unClaimedHead, 0, pojo.getAmount().doubleValue(), "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }

                int result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,enterby,"
                        + "auth,effdt,baseacno,F_Year,Seq_No,authby,trantime,InstCode) values ('" + pojo.getGlAcno() + "',"
                        + "'DEAF','15','" + today + "'," + pojo.getAmount() + ",'" + userName + "','Y',"
                        + "'" + intCalcDt + "','" + pojo.getGlAcno() + "','" + pojo.getfYear() + "',"
                        + "" + Double.parseDouble(pojo.getSeqNo()) + ",'System',now(),'" + acCode + "')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                }

                //below code in here
                // String ppdd = commonRemote.getPoddType(unClaimedList.get(0).getGlAcno().substring(2, 10));
                if (acctNature.equalsIgnoreCase("PO")) {
                    int result1 = em.createNativeQuery("update bill_po set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "' ").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                } else if (acctNature.equalsIgnoreCase("DD")) {
                    int result1 = em.createNativeQuery("update bill_dd set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "'").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                } else if (acctNature.equalsIgnoreCase("AD")) {
                    int result1 = em.createNativeQuery("update bill_ad set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "' ").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                } else if (acctNature.equalsIgnoreCase("TPO")) {
                    int result1 = em.createNativeQuery("update bill_tpo set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "' ").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                } else if (acctNature.equalsIgnoreCase("SUN")) {
                    int result1 = em.createNativeQuery("update bill_sundry set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "'").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                } else if (acctNature.equalsIgnoreCase("SUP")) {
                    int result1 = em.createNativeQuery("update bill_suspense set STATUS = 'Deaf' where acno = '" + pojo.getGlAcno() + "' and status = 'Issued' and seqno = " + pojo.getSeqNo() + " and fyear = " + pojo.getfYear() + " and ORIGINDT = '" + ymd.format(dmy.parse(pojo.getOriginDt())) + "' ").executeUpdate();
                    if (result1 <= 0) {
                        throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getGlAcno());
                    }
                }
            }

            //Insertion of posting details.
            list = em.createNativeQuery("select ifnull(max(sno),0)+1 as sno from cbs_loan_acctype_interest_parameter").getResultList();
            element = (Vector) list.get(0);
            int sno = Integer.parseInt(element.get(0).toString());

            int result = em.createNativeQuery("INSERT INTO cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,"
                    + "POST_FLAG,DT,POST_DT,BRNCODE,ENTER_BY,FLAG) VALUES(" + sno + ",'06','" + finalDeafEffDt + "',"
                    + "'" + intCalcDt + "','Y','" + today + "','" + today + "','" + reportBranchCode + "','System',"
                    + "'UC')").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem In Cbs Loan Acctype Interest Parameter "
                        + "Table Insertion. ");
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    public List<String> generateMultiGlDDSOwFile(List<MultiAcCodeTo> glList, String fileType, String accode, String agentCode,
            String agentName, String days, Integer password, String orgnBrCode, String todayDt,
            String generatedBy) throws ApplicationException {
        SimpleDateFormat dmyy = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat dmyyyy = new SimpleDateFormat("dd/MM/yyyy");
        List<MultiGLTo> multiList = new ArrayList<MultiGLTo>();
        List<String> outList = new ArrayList<String>();
        String outStr = "", ddsGlDetails = "", ddsLastAcno = "";
        String firstGlDetails = "", firstLastAcno = "", secondGlDetails = "", secondLastAcno = "";
        String thirdGlDetails = "", thirdLastAcno = "", fourthGlDetails = "", fourthLastAcno = "";
        String fifthGlDetails = "", fifthLastAcno = "", sixthGlDetails = "", sixthLastAcno = "";
        String sevenGlDetails = "", sevenLastAcno = "", eightGlDetails = "", eightLastAcno = "";
        String nineGlDetails = "", nineLastAcno = "", tenGlDetails = "", tenLastAcno = "";
        String headerTotalEntry = "", headerLastAcno = "", sign = "+", acNature = "", acctType = "";
        Integer stGlIndex = null;
        try {
            //For Agent Code.
            agentCode = fileType.equals("2") ? "01" : agentCode;
            stGlIndex = fileType.equals("2") ? 0 : 1;
            //Default Gl Master Setting
            if (fileType.equals("1")) {
                ddsGlDetails = sign + "0" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                firstGlDetails = sign + "1" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                secondGlDetails = sign + "2" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                thirdGlDetails = sign + "3" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                fourthGlDetails = sign + "4" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                fifthGlDetails = sign + "5" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                sixthGlDetails = sign + "6" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                sevenGlDetails = sign + "7" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                eightGlDetails = sign + "8" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                nineGlDetails = sign + "9" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
            } else if (fileType.equals("2")) {
                firstGlDetails = sign + "0" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                secondGlDetails = sign + "1" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                thirdGlDetails = sign + "2" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                fourthGlDetails = sign + "3" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                fifthGlDetails = sign + "4" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                sixthGlDetails = sign + "5" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                sevenGlDetails = sign + "6" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                eightGlDetails = sign + "7" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                nineGlDetails = sign + "8" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
                tenGlDetails = sign + "9" + "," + ParseFileUtil.addSuffixSpaces("", 6) + "," + "00000000";
            }
            //Data Processing.
            if (fileType.equals("1")) {   //With DDS.
                //ddsGlDetails = "+0," + ParseFileUtil.addSuffixSpaces(accode + "dds", 6) + "," + "00000000";
                List ddsList = em.createNativeQuery("select am.acno,am.custname,ifnull(am.openingdt,''),"
                        + "cast(ifnull(r.balance,0) as decimal(7)) as balance from accountmaster am,"
                        + "reconbalan r where am.accttype='" + accode + "' and am.curbrcode='" + orgnBrCode + "' and "
                        + "substring(am.acno,11,2)='" + agentCode + "' and am.rdmatdate>date_format(now(),'%Y%m%d') and "
                        + "am.accstatus<>9 and (am.closingdate is null or am.closingdate = '' or am.closingdate='null') "
                        + "and am.acno=r.acno").getResultList();
                if (!ddsList.isEmpty()) {
                    for (int i = 0; i < ddsList.size(); i++) {
                        Vector element = (Vector) ddsList.get(i);
                        MultiGLTo to = new MultiGLTo();
                        to.setAcno("0" + ParseFileUtil.addTrailingZeros(element.get(0).toString().substring(4, 10), 7));
                        to.setName(ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 16));
                        to.setOpeningDt(element.get(2).toString().equals("") ? dmyy.format(new Date())
                                : dmyy.format(ymd.parse(element.get(2).toString().trim())));
                        to.setBalance(ParseFileUtil.addTrailingZeros(element.get(3).toString(), 7));

                        multiList.add(to);
                        //Evaluation of last a/c no.
                        if (i == (ddsList.size() - 1)) {
                            ddsLastAcno = element.get(0).toString().trim();
                        }
                    }
                    ddsGlDetails = "+0," + ParseFileUtil.addSuffixSpaces(accode, 6) + ","
                            + "0" + ParseFileUtil.addTrailingZeros(ddsLastAcno.substring(4, 10), 7);
                }
            }
            //Other A/c Code Processing.
            Integer tempIndex = stGlIndex;
            for (int j = 0; j < glList.size(); j++) {
                MultiAcCodeTo to = glList.get(j);
                String toAcCode = to.getAcCode();
                acNature = fts.getAcNatureByCode(toAcCode);
                acctType = fts.getAcctTypeByCode(toAcCode);
                if (acNature.equals(CbsConstant.TERM_LOAN)
                        || acNature.equals(CbsConstant.DEMAND_LOAN)
                        || (acNature.equals(CbsConstant.CURRENT_AC) && acctType.equals(CbsConstant.CC_AC))
                        || (acNature.equals(CbsConstant.CURRENT_AC) && acctType.equals(CbsConstant.OD_AC))) {
                    sign = "-";
                } else {
                    sign = "+";
                }
                //Data Processing In Loop.
                List dataList = new ArrayList();
                if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                        || acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)
                        || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && acctType.equalsIgnoreCase(CbsConstant.CC_AC))
                        || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && acctType.equalsIgnoreCase(CbsConstant.OD_AC))
                        || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && acctType.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                    dataList = em.createNativeQuery("select am.acno,am.custname,ifnull(am.openingdt,''),0 as balance "
                            + "from accountmaster am where am.accttype ='" + toAcCode + "' and "
                            + "am.curbrcode='" + orgnBrCode + "' and am.accstatus<>9 and (am.closingdate is "
                            + "null or am.closingdate = '' or am.closingdate='null') ").getResultList();
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    dataList = em.createNativeQuery("select am.acno,am.custname,ifnull(am.openingdt,''),"
                            + "cast(ifnull(r.balance,0) as decimal(7)) as balance from accountmaster am,"
                            + "reconbalan r where am.accttype ='" + toAcCode + "' and am.curbrcode='" + orgnBrCode + "' and "
                            + "am.accstatus<>9 and (am.closingdate is null or am.closingdate = '' or am.closingdate='null') "
                            + "and am.acno=r.acno").getResultList();
                }
                if (!dataList.isEmpty()) {
                    for (int k = 0; k < dataList.size(); k++) {
                        Vector element = (Vector) dataList.get(k);
                        MultiGLTo dataTo = new MultiGLTo();
                        dataTo.setAcno(tempIndex + ParseFileUtil.addTrailingZeros(element.get(0).toString().substring(4, 10), 7));
                        dataTo.setName(ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 16));
                        dataTo.setOpeningDt(element.get(2).toString().equals("") ? dmyy.format(new Date())
                                : dmyy.format(ymd.parse(element.get(2).toString().trim())));
                        dataTo.setBalance(ParseFileUtil.addTrailingZeros(element.get(3).toString(), 7));

                        multiList.add(dataTo);
                        //Evaluation of last a/c no.
                        if (j == 0) {
                            if (k == (dataList.size() - 1)) {
                                firstLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 1) {
                            if (k == (dataList.size() - 1)) {
                                secondLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 2) {
                            if (k == (dataList.size() - 1)) {
                                thirdLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 3) {
                            if (k == (dataList.size() - 1)) {
                                fourthLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 4) {
                            if (k == (dataList.size() - 1)) {
                                fifthLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 5) {
                            if (k == (dataList.size() - 1)) {
                                sixthLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 6) {
                            if (k == (dataList.size() - 1)) {
                                sevenLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 7) {
                            if (k == (dataList.size() - 1)) {
                                eightLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 8) {
                            if (k == (dataList.size() - 1)) {
                                nineLastAcno = element.get(0).toString().trim();
                            }
                        } else if (j == 9) {
                            if (k == (dataList.size() - 1)) {
                                tenLastAcno = element.get(0).toString().trim();
                            }
                        }
                    }
                    //Setting of Gl Details.
                    if (j == 0) {
                        firstGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(firstLastAcno.substring(4, 10), 7);
                    } else if (j == 1) {
                        secondGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(secondLastAcno.substring(4, 10), 7);
                    } else if (j == 2) {
                        thirdGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(thirdLastAcno.substring(4, 10), 7);
                    } else if (j == 3) {
                        fourthGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(fourthLastAcno.substring(4, 10), 7);
                    } else if (j == 4) {
                        fifthGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(fifthLastAcno.substring(4, 10), 7);
                    } else if (j == 5) {
                        sixthGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(sixthLastAcno.substring(4, 10), 7);
                    } else if (j == 6) {
                        sevenGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(sevenLastAcno.substring(4, 10), 7);
                    } else if (j == 7) {
                        eightGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(eightLastAcno.substring(4, 10), 7);
                    } else if (j == 8) {
                        nineGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(nineLastAcno.substring(4, 10), 7);
                    } else if (j == 9) {
                        tenGlDetails = sign + tempIndex + "," + ParseFileUtil.addSuffixSpaces(toAcCode, 6) + "," + tempIndex + ParseFileUtil.addTrailingZeros(tenLastAcno.substring(4, 10), 7);
                    }
                }
                //Incrementing the glIndex.
                tempIndex = tempIndex + 1;
            }
            //Processing.
            if (multiList.isEmpty()) {
                throw new ApplicationException("There is no data to generate the file.");
            }
            System.out.println("Multi List Size is-->" + multiList.size());
            List macList = em.createNativeQuery("select code from parameterinfo_report where "
                    + "reportname='MULTI-GL-TOTAL-AC'").getResultList();
            if (macList.isEmpty()) {
                throw new ApplicationException("Please define parameter MULTI-GL-TOTAL-AC.");
            }
            Vector macVec = (Vector) macList.get(0);
            Integer macCapacity = Integer.parseInt(macVec.get(0).toString());
            if (multiList.size() > macCapacity) {
                throw new ApplicationException("Generated file will be too large from "
                        + "machine capacity.");    //We can modify here to cut the list.
            }
            //Evaluation of last a/c no and list size for header.
            for (int l = 0; l < multiList.size(); l++) {
                if (l == (multiList.size() - 1)) {
                    headerLastAcno = multiList.get(l).getAcno();
                }
            }
            headerTotalEntry = String.valueOf(multiList.size());
            //Header Preparation.
            outStr = orgnBrCode + "," + ParseFileUtil.addTrailingZeros(agentCode, 3) + "," + headerLastAcno + ","
                    + dmyy.format(dmyyyy.parse(todayDt)) + "," + "0000,00000000," + password + ","
                    + ParseFileUtil.addTrailingZeros(headerTotalEntry, 4) + "," + days + ",3,"
                    + "................................," + ParseFileUtil.addSuffixSpaces("", 6)
                    + "," + ParseFileUtil.addSuffixSpaces("", 16) + ",000000";
            outList.add(outStr);
            //1st Line GL Master.
            if (fileType.equals("1")) {
                outStr = ddsGlDetails + "," + firstGlDetails + "," + secondGlDetails + "," + thirdGlDetails + "," + fourthGlDetails;
            } else if (fileType.equals("2")) {
                outStr = firstGlDetails + "," + secondGlDetails + "," + thirdGlDetails + "," + fourthGlDetails + "," + fifthGlDetails;
            }
            outList.add(outStr);
            //2nd Line GL Master.
            if (fileType.equals("1")) {
                outStr = fifthGlDetails + "," + sixthGlDetails + "," + sevenGlDetails + "," + eightGlDetails + "," + nineGlDetails;
            } else if (fileType.equals("2")) {
                outStr = sixthGlDetails + "," + sevenGlDetails + "," + eightGlDetails + "," + nineGlDetails + "," + tenGlDetails;
            }
            outList.add(outStr);
            //Data Lines.
            for (int m = 0; m < multiList.size(); m++) {
                MultiGLTo to = multiList.get(m);
                outStr = to.getAcno() + ",000000,000000,000000,000000,000000," + to.getName() + "," + to.getBalance()
                        + "," + to.getOpeningDt() + ",000000,0000";
                outList.add(outStr);
            }
            return outList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());

        }
    }

    public List getMultiGlDdsAcCode() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctnature,acctcode,acctdesc from accounttypemaster where "
                    + "acctnature not in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','"
                    + CbsConstant.PAY_ORDER + "','" + CbsConstant.DEPOSIT_SC + "') order by acctcode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String pushMultiGLData(List<MultiGLTo> dataList, String dt, String enterBy,
            String tokenPaid, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", tokenPaidBy = "";
        Integer tranDesc = 0;
        try {
            ut.begin();
            if (tokenPaid.equalsIgnoreCase("Y")) {
                tokenPaidBy = enterBy;
            }
            for (int i = 0; i < dataList.size(); i++) {
                MultiGLTo to = dataList.get(i);
                String acno = to.getAcno();
                String acType = acno.substring(2, 4);
                String acNature = fts.getAcNatureByCode(acType);
                Double cramt = Double.parseDouble(to.getBalance());
                Query query = null;
                if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    query = em.createNativeQuery("insert into dds_auth_d(acno,ty,dt,dramt,cramt,trantype,receiptno,"
                            + "enterby,recno,tokenpaid) values ('" + acno + "',0,'" + dt + "',0," + cramt + ",0,"
                            + "'" + String.valueOf(i) + "','" + enterBy + "'," + fts.getRecNo() + ",'" + tokenPaid + "')");
                } else {
                    tranDesc = 0;
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                            || acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        tranDesc = 1;
                    }
                    query = em.createNativeQuery("insert into tokentable_credit(acno,custname,dt,trantime,cramt,dramt,"
                            + "payby,enterby,auth,trandesc,tokenpaid,tokenpaidby,recno,trantype,ty,org_brnid,"
                            + "dest_brnid,valuedt,details) values ('" + acno + "','" + fts.getCustName(acno) + "',"
                            + "'" + dt + "',now()," + cramt + ",0,3,'" + enterBy + "','N'," + tranDesc + ","
                            + "'" + tokenPaid + "','" + tokenPaidBy + "'," + fts.getRecNo() + ",0,0,'" + orgnBrCode + "',"
                            + "'" + orgnBrCode + "','" + dt + "','Entry From DDS Machine')");
                }
                int rowAffected = query.executeUpdate();
                if (rowAffected <= 0) {
                    throw new ApplicationException("Problem in data insertion");
                }
            }
            ut.commit();
            result = "true";
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

    public List<TdIntDetail> intCalcForTds(String fromDate, String toDate, String type, String brncode) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List<TdIntDetail> rdIntList = new ArrayList<TdIntDetail>();

            String finStartDt = rbiReportFacade.getMinFinYear(toDate);
            String acQuery = "select  aa.custid, aa.Acno as acno, aa.custname, aa.openingdt, aa.rdmatdate, aa.roi, aa.next_int_calc_dt,"
                    + " aa.TDSFLAG, aa.pan,  aa.minorflag,  "
                    + " ifnull(gr.CustomerId,0)  as minorCustIdfrom,  "
                    + " ifnull(pro.CustId,'') as propCustId, ifnull(nrTds.nrTds,0), ifnull(vo.totalAcInt,0), "
                    + " ifnull(tdsVouch.totalTdsAcWise,0),  ifnull(bb.totalIntOnCustId,0), ifnull(tds.totalTdsCustId,0), "
                    + " ifnull(tdsVouch.closeAcTds,0), ifnull(nrTdsCustId.nrTdsCustIdWise,0), "
                    + " ifnull(gur.majorCustId,'') as majorCustId, ifnull(gur.pan,'') as majPan, aa.dayWise, aa.custType, aa.DateOfBirth, aa.CustEntityType, if(minorflag='N',aa.DateOfBirth,gur.majDob) as majDob  "
                    + " from  "
                    + " (Select ci.custid,a.Acno,a.custname,DATE_FORMAT(a.openingdt,'%Y%m%d') as openingdt,"
                    + " DATE_FORMAT(a.rdmatdate,'%Y%m%d') as rdmatdate,"
                    + " a.intdeposit as roi, DATE_FORMAT(c.next_int_calc_dt,'%Y%m%d') as next_int_calc_dt, ifnull(a.TDSFLAG,'Y') as TDSFLAG,"
                    + " ifnull(cd.PAN_GIRNumber,'') as pan,  ifnull(cd.minorflag,'N') as minorflag, 'N' as dayWise, "
                    + " ifnull(a.CUST_TYPE,'OT') as custType, date_format(cd.DateOfBirth,'%Y%m%d') as DateOfBirth,"
                    + " ifnull(cd.CustEntityType,'03') as CustEntityType   "
                    + " from accountmaster a, (select distinct acno from ddstransaction) d, "
                    + " cbs_loan_acc_mast_sec c,customerid ci, cbs_customer_master_detail cd  "
                    + " where a.acno = ci.acno and a.acno = d.acno and a.acno = c.acno "
                    + " and ci.CustId = cast(cd.customerid as unsigned) and a.curbrcode ='" + brncode + "' and a.accttype = '" + type + "' "
                    + " and a.accstatus <> 9 and a.openingdt <= '" + toDate + "' and a.rdmatdate >= '" + toDate + "' /*and a.tdsflag='Y'*/"
                    + " ) aa  "
                    + " left join  "
                    + " (select a.CustId as custId,a.acno,cast(ifnull(sum(a.interest),0) as decimal(25,2)) as totalAcInt from  "
                    + " (select ci.CustId,ti.acno, ifnull(sum(interest),0) as interest from customerid ci,rd_interesthistory ti where ti.acno=ci.acno and  "
                    + " ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId, ti.acno  "
                    + " ) a group by a.CustId, a.acno "
                    + " ) vo on aa.CustId = vo.CustId and aa.acno = vo.acno   "
                    + " left join   "
                    + " (select a.CustId as custId, ifnull(cast(sum(a.interest) as decimal(25,2)),0) as totalIntOnCustId from   "
                    + " (select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno   "
                    + " and (tv.cldt >='" + finStartDt + "')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all  "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'  group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,td_vouchmst tv,td_interesthistory ti where tv.acno=ci.acno   "
                    + " and (tv.cldt is null or tv.cldt ='')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,rd_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "' group by ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(interest),0) as interest from customerid ci,accountmaster ac,dds_interesthistory ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'  group by ci.CustId ) a group by a.CustId  "
                    + " ) bb on aa.CustId = bb.CustId   "
                    + " left join   "
                    + " (select a.CustId, ifnull(cast(sum(a.tds) as decimal(25,2)),0) as totalTdsCustId from   "
                    + " (select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno   "
                    + "  and (tv.cldt >='" + finStartDt + "')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all    "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate >='" + finStartDt + "')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,td_vouchmst tv,tds_reserve_history ti where tv.acno=ci.acno   "
                    + " and (tv.cldt is null or tv.cldt ='')  "
                    + " and tv.acno=ti.acno and tv.VoucherNo = ti.voucherno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " union all   "
                    + " select ci.CustId, ifnull(sum(tds),0) as tds from customerid ci,accountmaster ac,tds_reserve_history ri where ac.acno=ci.acno   "
                    + " and (ac.closingdate is null or ac.closingdate ='')  "
                    + " and ac.acno=ri.acno and ri.dt between  '" + finStartDt + "' and '" + toDate + "'   "
                    + " group by  ci.CustId   "
                    + " ) a group by  a.CustId ) tds on aa.CustId = tds.CustId  "
                    + " left join  "
                    + " (select a.CustId,a.acno, sum(a.tds) as totalTdsAcWise, sum(a.closeTds) as closeAcTds from  "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds, ifnull(sum(ti.closeAcTds),0) as closeTds from customerid ci,tds_reserve_history ti where ti.acno=ci.acno and ti.dt between  '" + finStartDt + "' and '" + toDate + "'  "
                    + " group by  ci.CustId, ti.acno  "
                    + " ) a group by  a.CustId, a.acno ) tdsVouch on aa.CustId = tdsVouch.CustId  and aa.acno = tdsVouch.acno  "
                    + " left join  "
                    + " (select count(gr.customerid) as customerid, gr.guardiancode from  cbs_cust_minorinfo gr  "
                    + " where (gr.guardiancode is not null and gr.guardiancode<> '')  group by gr.guardiancode) gr  "
                    + " on gr.guardiancode = aa.CustId   "
                    + " left join  "
                    + " (select a.CustId,a.acno,sum(a.tds) as nrTds from   "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds from customerid ci, tds_reserve_history ti where ti.acno=ci.acno    "
                    + " and ti.recovered ='NR' and ti.dt <='" + toDate + "'   "
                    + " group by  ci.CustId, ti.acno  ) a group by  a.CustId, a.acno) nrTds on aa.CustId = nrTds.CustId  and aa.acno = nrTds.acno  "
                    + " left join  "
                    + " (select a.CustId,a.acno,sum(a.tds) as nrTdsCustIdWise from   "
                    + " (select ci.CustId,ti.acno, ifnull(sum(tds),0) as tds from customerid ci, tds_reserve_history ti where ti.acno=ci.acno    "
                    + " and ti.recovered ='NR' and ti.dt <='" + toDate + "'   "
                    + " group by  ci.CustId ) a group by  a.CustId) nrTdsCustId on aa.CustId = nrTdsCustId.CustId   "
                    + " left join  "
                    + " (select distinct tm.acno, ci.CustId from customerid ci,td_accountmaster tm where tm.opermode = 8 and  "
                    + " ci.custid = tm.custid1  "
                    + " union all  "
                    + " select distinct tm.acno, ci.CustId from customerid ci,accountmaster tm where tm.opermode = 8 and  "
                    + " ci.custid = tm.custid1 and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))  "
                    + " ) pro on  pro.acno = aa.acno  "
                    + " left join "
                    + " (select  mn.CustomerId, ifnull(mn.guardiancode,'0')  as majorCustId, cm.PAN_GIRNumber as pan, date_format(cm.DateOfBirth,'%Y%m%d') as majDob from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                    + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '')) gur on gur.customerid = aa.custId "
                    + " order by aa.CustId,aa.acno";
            List tempData = em.createNativeQuery(acQuery).getResultList();
            int code = 0;
            List hourList = em.createNativeQuery("select ifnull(code,0) from parameterinfo_report where upper(reportname)='DDS_PRODUCT'").getResultList();
            if (!hourList.isEmpty()) {
                Vector v1 = (Vector) hourList.get(0);
                code = Integer.parseInt(v1.get(0).toString());
            }

            for (int i = 0; i < tempData.size(); i++) {
                Vector vect = (Vector) tempData.get(i);
                TdIntDetail intDetail = new TdIntDetail();
                intDetail.setMsg("TRUE");
                String custId = vect.get(0).toString();
                intDetail.setCustId(custId);
                String accno = vect.get(1).toString();
                intDetail.setAcno(accno);
                System.out.println("Acno = " + vect.get(1).toString());
                intDetail.setCustName(vect.get(2).toString());
                intDetail.setRoi(Float.parseFloat(vect.get(5).toString()));

                intDetail.setVoucherNo(0);
                intDetail.setIntOpt("Q");

                intDetail.setFromDt(fromDate);
                if (vect.get(3) == null) {
                    throw new ApplicationException("Maturity Date does not exist In Accountmaster");
                }
                intDetail.setMatDt(vect.get(4).toString());

                intDetail.setIntToAcno("");

                intDetail.setToDt(toDate);
                intDetail.setSeqno(0);

                intDetail.setStatus("A");

                String opDt = vect.get(3).toString();
                String openingDt = vect.get(6).toString();

                String fromDt = fromDate;
                if (ymd.parse(openingDt).getTime() > ymd.parse(fromDate).getTime()) {
                    fromDt = openingDt;
                }
                String matDt = vect.get(4).toString();
                int pd = CbsUtil.monthDiff(ymd.parse(opDt), ymd.parse(matDt));

                List resultList = em.createNativeQuery("select min(roi),ifnull(roi,0) from dds_slab where (" + pd + " between from_mon and to_mon) "
                        + " and actype ='" + type + "' and applicable_date = (select max(applicable_date) from dds_slab "
                        + " where actype ='" + type + "' and applicable_date <='" + opDt + "')").getResultList();
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Maturity Date does not exist");
                }
                Vector vtr2 = (Vector) resultList.get(0);
                double roi = Double.parseDouble(vtr2.get(0).toString());

                double balance = Double.parseDouble(getBalance(intDetail.getAcno()));
                intDetail.setpAmt(balance);
                double product = getProduct(fromDt, toDate, intDetail.getAcno(), code);
                double interest = 0;
                if ((code == 0) || (code == 2)) {
                    interest = CbsUtil.round((product * roi / 1200), 0);
                } else {
                    interest = CbsUtil.round((product * roi / 36500), 0);
                }
                if (interest > 0) {
                    intDetail.setInterest(interest);
//                intDetail.setCustType(vect.get(7).toString());
//                intDetail.setDob(vect.get(8).toString());
                    intDetail.setPropCustId("");
                    intDetail.setTdsFlag(vect.get(7).toString());
                    intDetail.setMinorInterest(0);
                    intDetail.setMajorInterest(0);
                    String pan = vect.get(8).toString();
                    if (ParseFileUtil.isValidPAN(pan) == true) {
                        pan = "Y";
                    } else {
                        pan = "N";
                    }
                    intDetail.setPan(pan);
                    String minorFlag = vect.get(9).toString();
                    intDetail.setMinorFlag(minorFlag);
                    int noOfMinorInMajor = Integer.parseInt(vect.get(10).toString());
                    String propCustId = vect.get(11).toString();
                    double unRecTds = Double.parseDouble(vect.get(12).toString());
                    double intAcWise = Double.parseDouble(vect.get(13).toString());
                    double tdsAcWise = Double.parseDouble(vect.get(14).toString());
                    double totalIntPaid = Double.parseDouble(vect.get(15).toString());
                    double totalTds = Double.parseDouble(vect.get(16).toString());
                    double closeAcTds = Double.parseDouble(vect.get(17).toString());
                    double unRecTdsCustIdWise = Double.parseDouble(vect.get(18).toString());
                    String majorCustId = vect.get(19).toString();
                    String majorPan = vect.get(20).toString();
                    String dayWiseCalFlag = vect.get(21).toString();
                    String custType = vect.get(22).toString();
                    String dob = vect.get(23).toString();
                    String custEntityType = vect.get(24).toString();
//                    if (vect.get(25) == null) {
//                        System.out.println("Please check the DOB of customer " + custId + " Or Major DOB " + majorCustId);
//                        throw new ApplicationException("Please check the DOB of customer " + custId + " Or Major DOB " + majorCustId+" Or Minor Condition");
//                    }
                    String majDob = (vect.get(25) == null || vect.get(25).toString().equalsIgnoreCase("19000101")) ? "" : vect.get(25).toString();
                    double /*tds = 0d,*/ minorInt = 0d, majorInt = 0d;
//                            if (acWiseTdsList.size() > 0) {
//                                Vector acWiseTdsVect = (Vector) acWiseTdsList.get(0);
//                                tds = Double.parseDouble(acWiseTdsVect.get(0).toString());
//                            }
                    String gurCustId = custId;
                    if (minorFlag.equalsIgnoreCase("Y")) {
                        List mjOMinFlagLst = em.createNativeQuery("select  ifnull(guardiancode,'0') from cbs_cust_minorinfo where customerid ='" + custId + "'").getResultList();
                        if (!mjOMinFlagLst.isEmpty()) {
                            for (int e = 0; e < mjOMinFlagLst.size(); e++) {
                                Vector mjOMinVec = (Vector) mjOMinFlagLst.get(e);
                                gurCustId = mjOMinVec.get(0).toString();
                                majorInt = majorInt + autoRenewRemote.getMajorOrMinorInt(accno, finStartDt, toDate);
                            }
                            if (pan.equalsIgnoreCase("N")) {
                                if (ParseFileUtil.isValidPAN(majorPan) == true) {
                                    pan = "Y";
                                } else {
                                    pan = "N";
                                }
                            }
                        }
                    } else if (!majorCustId.equalsIgnoreCase("")) {
                        System.out.println("MinorCustID==>" + custId + ": MajorCustId==>" + majorCustId);
                        List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid =" + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                + "union all "
                                + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                + "union all "
                                + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                + " (select ci.acno from customerid ci where ci.custid = " + majorCustId + " and substring(ci.acno,3,2) in "
                                + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                + " and dt between '" + finStartDt + "' and '" + toDate + "') aa").getResultList();
                        majorInt = majorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                        minorFlag = "Y";
                        if (ParseFileUtil.isValidPAN(majorPan) == true) {
                            pan = "Y";
                        } else {
                            pan = "N";
                        }
                    }
                    if (noOfMinorInMajor > 0) {
                        List majorFlagLst = em.createNativeQuery("select  ifnull(customerid,'0') from cbs_cust_minorinfo where guardiancode ='" + custId + "'").getResultList();
                        if (!majorFlagLst.isEmpty()) {
                            for (int e = 0; e < majorFlagLst.size(); e++) {
                                Vector mjOMinVec = (Vector) majorFlagLst.get(e);
                                String minorCustId = mjOMinVec.get(0).toString();
                                List minorIntList = em.createNativeQuery("select sum(aa.interest) from "
                                        + "(select ifnull(sum(interest),0) as interest from td_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('FD','MS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from rd_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('RD'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "' "
                                        + "union all "
                                        + "select ifnull(sum(interest),0) as interest from dds_interesthistory where acno in "
                                        + " (select ci.acno from customerid ci where ci.custid in (" + minorCustId + ") and substring(ci.acno,3,2) in "
                                        + " (select acctcode from accounttypemaster where acctnature in ('DS'))) "
                                        + " and dt between '" + finStartDt + "' and '" + toDate + "') aa").getResultList();

                                minorInt = minorInt + Double.parseDouble(((Vector) minorIntList.get(0)).get(0).toString());
                            }
                        }
                    }
                    intDetail.setTotalInt(totalIntPaid);
                    intDetail.setTotalTds(totalTds);
                    intDetail.setTdsDeducted(tdsAcWise);
                    intDetail.setTotalIntPaidVouchWise(intAcWise);
                    intDetail.setMajorInterest(majorInt);
                    intDetail.setUnRecoverTds(unRecTds);
                    intDetail.setUnRecoverTdsCustId(unRecTdsCustIdWise);
                    intDetail.setInterestWithMinMaj(totalIntPaid + minorInt + majorInt);
                    intDetail.setMinorInterest(minorInt);
                    intDetail.setMinorFlag(minorFlag.equalsIgnoreCase("Y") ? minorFlag : "");
                    intDetail.setPan(pan);
                    intDetail.setMajorCustId(gurCustId);
                    intDetail.setPropCustId(propCustId);
                    intDetail.setCloseAcTds(closeAcTds);
                    intDetail.setCustType(custType);
                    intDetail.setDob(dob);
                    intDetail.setMajDob(majDob);
                    intDetail.setCustEntityType(custEntityType);
                    rdIntList.add(intDetail);
                }
            }
            return rdIntList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String singleAcnoDeafAmtPost(String function, String txnId, String acno, String deafAmt, String voucherNo, String effectdate, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String accountTable = fts.getAccountTable(fts.getAccountNature(acno));
            int result;
            String actNature = fts.getAccountNature(acno);
            if (function.equalsIgnoreCase("A")) {
                if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                            + "enterby,auth,effdt,baseacno,voucher_no,authby,trantime) values ('" + acno + "','DEAF',"
                            + "'15','" + effectdate + "'," + Double.parseDouble(deafAmt) + ",'" + userName + "','Y','" + effectdate + "',"
                            + "'" + acno + "'," + Double.parseDouble(voucherNo) + ",'System',now())").executeUpdate();
                } else {
                    result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                            + "enterby,auth,effdt,baseacno,authby,trantime) values ('" + acno + "','DEAF',"
                            + "'15','" + effectdate + "'," + Double.parseDouble(deafAmt) + ",'" + userName + "','Y','" + effectdate + "',"
                            + "'" + acno + "','System',now())").executeUpdate();
                }
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Status Table Insertion. " + acno);
                }
                result = em.createNativeQuery("update " + accountTable + " set accstatus=15 where "
                        + "acno='" + acno + "'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Table Updation. " + acno);
                }
                //Interest Date Updation.
                List checkList = em.createNativeQuery("select * from cbs_loan_acc_mast_sec where "
                        + "acno='" + acno + "'").getResultList();
                if (!checkList.isEmpty()) {
                    result = em.createNativeQuery("update cbs_loan_acc_mast_sec set "
                            + "int_calc_upto_dt='" + effectdate + "',next_int_calc_dt='" + CbsUtil.dateAdd(effectdate, 1) + "' "
                            + "where acno='" + acno + "'").executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem In A/c Table Updation In Cbs Loan "
                                + "Secondary Table. " + acno);
                    }
                }
                //End Here
            } else {
                if (voucherNo.equalsIgnoreCase("")) {
                    voucherNo = "0";
                }
                result = em.createNativeQuery("INSERT INTO accountstatus_his(ACNO, REMARK, SPFLAG, DT, EFFDT, AMOUNT, ENTERBY, AUTH, AUTHBY, TRANTIME, SUBSTATUS, BaseAcno, Voucher_No, Seq_No, F_Year)SELECT ACNO, REMARK, SPFLAG, DT, EFFDT, AMOUNT, ENTERBY, AUTH, AUTHBY, TRANTIME, SUBSTATUS, BaseAcno, Voucher_No, Seq_No, F_Year FROM accountstatus where SPNO = '" + txnId + "' and spflag = '15'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Table Insertion. " + acno);
                }
                result = em.createNativeQuery("UPDATE accountstatus SET ACNO = '" + acno + "', TRANTIME= now(),EFFDT = '" + effectdate + "', AMOUNT = " + Double.parseDouble(deafAmt) + ", Voucher_No = " + Double.parseDouble(voucherNo) + ",AUTHBY = '" + userName + "' WHERE SPNO = '" + txnId + "' and spflag = '15'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In A/c Table Updation. " + acno);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    public String getLastTxnDt(String acno, String voucherNo) throws ApplicationException {
        try {
            String acNat = fts.getAccountNature(acno);
            List result1 = new ArrayList();
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result1 = em.createNativeQuery("select date_format(MatDt,'%d/%m/%Y') MatDt from td_vouchmst where acno = '" + acno + "' and "
                        + "Status = 'A' and voucherno = " + Double.parseDouble(voucherNo) + "").getResultList();
            } else {
                // result1 = em.createNativeQuery("select ifnull(max(dt),'') from " + table2 + " where acno = '" + acNo + "' and trandesc not in(" + tranDescUnd + ")").getResultList();
                result1 = em.createNativeQuery("select date_format(ifnull(Last_Txn_Date,''),'%d/%m/%Y') from accountmaster where acno = '" + acno + "'").getResultList();
            }
            Vector dtvtr = (Vector) result1.get(0);
            return dtvtr.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String getAccStatus(String acno) throws ApplicationException {
        try {
            String acNat = fts.getAccountNature(acno);
            List result1 = new ArrayList();
            if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result1 = em.createNativeQuery("select AccStatus from td_accountmaster where acno = '" + acno + "'").getResultList();
            } else {
                result1 = em.createNativeQuery("select AccStatus from accountmaster where acno = '" + acno + "'").getResultList();
            }
            Vector dtvtr = (Vector) result1.get(0);
            return dtvtr.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getGlAccounNature() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(InstNature) from billtypemaster").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getGlCodeByGlNature(String nature) throws ApplicationException {
        try {
            //return em.createNativeQuery("select InstCode,concat(InstCode,'  ',InstDesc) from billtypemaster where InstNature = '" + nature + "'").getResultList();
            return em.createNativeQuery("select InstCode,InstDesc from billtypemaster where InstNature = '" + nature + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public String getGlAcnoByGlCodeBGlNature(String nature, String code) throws ApplicationException {
        try {
            List glList = em.createNativeQuery("select glhead from billtypemaster where InstNature = '" + nature + "' and InstCode = '" + code + "'").getResultList();
            if (!glList.isEmpty()) {
                Vector glv = (Vector) glList.get(0);
                return glv.get(0).toString();
            } else {
                throw new ApplicationException("Gl Head does not exits in table billtypemaster !");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getSingleAcnoData(String brCode, String frDt, String toDt) throws ApplicationException {
        try {
            return em.createNativeQuery("select SPNO,ACNO,date_format(EFFDT,'%d/%m/%Y'),AMOUNT,date_format(DT,'%d/%m/%Y'),ifnull(Voucher_No,0) from accountstatus where substring(acno,1,2)='" + brCode + "' and EFFDT between '" + frDt + "' and '" + toDt + "' and SPFLAG = '15'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String badPersonDataPost(String buttonVal, String sections, String personId, String title, String name, String dob, String pob, String designation, String address, String goodQuality, String lowQuality, String nationality, String passportNo, String nationalIdNo, String listedNo, String otherInfo, String enterBy, String enterDate, String updateBy, String updateDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            int result;
            ut.begin();

            if (buttonVal.equalsIgnoreCase("A")) {
                List chkList = em.createNativeQuery("select *  FROM bad_person_info WHERE sections = '" + sections + "' and bad_person_id = '" + personId + "' ").getResultList();
                if (!chkList.isEmpty()) {
                    throw new ApplicationException("This Id:" + personId + ",data already exits !");
                }
                result = em.createNativeQuery("INSERT INTO bad_person_info (sections, bad_person_id, title, name, dob, pob, designation, address, "
                        + "listed_on, good_quality, flag_good_quality, low_quality, flag_low_quality, nationality, passport_no, "
                        + "national_identification_no, other_information, enter_by, enter_date ) VALUES "
                        + "('" + sections + "', '" + personId + "', '" + title + "', '" + name + "', '" + dob + "', '" + pob + "', '" + designation + "', '" + address + "', '" + listedNo + "',"
                        + " '" + goodQuality + "', '', '" + lowQuality + "', '', '" + nationality + "', '" + passportNo + "','" + nationalIdNo + "', "
                        + "'" + otherInfo + "', '" + enterBy + "', now())").executeUpdate();

                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }
            } else if (buttonVal.equalsIgnoreCase("U")) {

                result = em.createNativeQuery("INSERT INTO bad_person_info_his (sections, bad_person_id, title, name, dob, pob, designation, address, "
                        + "listed_on, good_quality, flag_good_quality, low_quality, flag_low_quality, nationality, passport_no, national_identification_no, "
                        + "other_information, enter_by, enter_date, update_by, update_date)"
                        + "SELECT sections, bad_person_id, title, name, dob, pob, designation, address, listed_on, good_quality, flag_good_quality, "
                        + "low_quality, flag_low_quality, nationality, passport_no, national_identification_no, other_information, enter_by, enter_date, "
                        + "update_by, update_date FROM bad_person_info where sections = '" + sections + "' and bad_person_id = '" + personId + "'").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Insertion");
                }

                result = em.createNativeQuery("UPDATE bad_person_info SET title = '" + title + "', name = '" + name + "', dob = '" + dob + "', pob = '" + pob + "', "
                        + "designation = '" + designation + "', address = '" + address + "', listed_on = '" + listedNo + "', good_quality = '" + goodQuality + "', flag_good_quality = '', low_quality = '" + lowQuality + "', "
                        + "flag_low_quality = '', nationality = '" + nationality + "', passport_no = '" + passportNo + "', national_identification_no = '" + nationalIdNo + "', other_information = '" + otherInfo + "', "
                        + "update_by = '" + updateBy + "', update_date = now() WHERE sections = '" + sections + "' and bad_person_id = '" + personId + "'").executeUpdate();

                if (result <= 0) {
                    throw new ApplicationException("Problem In Table Updation");
                }
            } else if (buttonVal.equalsIgnoreCase("D")) {
                result = em.createNativeQuery("DELETE FROM bad_person_info WHERE sections = '" + sections + "' and bad_person_id = '" + personId + "' ").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In  Table Deletion");
                }
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
        return "true";
    }

    public List getBadPersonData(String section, String id) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT title, name, dob, pob,designation,address,listed_on,good_quality, low_quality,passport_no,nationality,national_identification_no, other_information FROM bad_person_info where sections = '" + section + "' and bad_person_id = '" + id + "'").getResultList();

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getBadPersonSecWiseData(String section) throws ApplicationException {
        List result = new ArrayList();
        try {
            result = em.createNativeQuery("select sections,bad_person_id,title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,"
                    + "nationality,enter_by,date_format(enter_date,'%d/%m/%Y') from bad_person_info where sections='" + section + "'"
                    + " /*union "
                    + "select sections,bad_person_id,title,name,dob,pob,designation,address,good_quality,low_quality,passport_no,"
                    + "nationality,enter_by,date_format(enter_date,'%d/%m/%Y') from bad_person_info_his where sections='" + section + "'*/").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String getnatioality(String nationalCode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where REF_REC_NO = '302' and REF_CODE = '" + nationalCode + "'").getResultList();
            Vector vtr = (Vector) list.get(0);
            String nation = vtr.get(0).toString();
            return nation;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAgentPassword(String agentCode, String brnCode) throws ApplicationException {
        String password = "";
        try {
            List list = em.createNativeQuery("select agent_password from ddsagent where "
                    + "agcode='" + agentCode + "' and brncode='" + brnCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                password = ele.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return password;
    }
}
