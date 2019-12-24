package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AbbStatementPojo;
import com.cbs.dto.report.LoanDemandRecoveryPojo;
import com.cbs.dto.report.MiniStatementPojo;
import com.cbs.dto.report.PostingReportPojo;
import com.cbs.dto.report.VillageWiseEMIDetailPojo;
import com.cbs.dto.report.ho.BranchAdjPojo;
import com.cbs.dto.report.ho.HoReconPojo;
import com.cbs.entity.sms.MbSmsSenderBankDetail;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName = "CommonReportMethods")
public class CommonReportMethods implements CommonReportMethodsRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private UploadNeftRtgsMgmtFacadeRemote nrRemote;
    @EJB
    private LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat nft = new DecimalFormat("0.00");

    @Override
    public List getBranchNameandAddress(String orgnbrcode) throws ApplicationException {
        List returnList = new ArrayList();
        Vector ele = null;
        try {
            List list = em.createNativeQuery("SELECT b.bankname,b.bankaddress,ifnull(br.pincode,'') FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and "
                    + "br.brncode=" + Integer.parseInt(orgnbrcode)).getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                returnList.add(ele.get(0));
                returnList.add(ele.get(1));
                returnList.add(ele.get(2));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getCode(String reportName) throws ApplicationException {
        List returnList = new ArrayList();
        Vector ele = null;
        try {
            List list = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo where name = '" + reportName + "'").getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                returnList.add(ele.get(0));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public String getBranchNameandAddressString(String orgnbrcode) throws ApplicationException {
        Vector ele = null;
        try {
            List list = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and "
                    + "br.brncode=" + Integer.parseInt(orgnbrcode)).getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                return ele.get(0).toString() + "!" + ele.get(1).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    @Override
    public long dayDifference(String dt1, String dt2) throws ApplicationException {
        try {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(ymdFormat.parse(dt1));
            cal2.setTime(ymdFormat.parse(dt2));
            long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
            long div = 24 * 60 * 60 * 1000;
            long diffDays = (long) Math.round(diff / div);
            return diffDays;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getAllAcTypeList() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctCode from accounttypemaster ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public int getTotalWorkingDays(int quarter, int year, String brCode) throws ApplicationException {
        try {
            Vector ele = (Vector) em.createNativeQuery("select count(*) from opcash where brncode='" + brCode + "' and extract(year from tdate)=" + year
                    + " and extract(month from tdate) in (" + quarter + "," + quarter + "-1," + quarter + "-2)").getSingleResult();
            if (ele != null && ele.get(0) != null) {
                return Integer.parseInt(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return -1;
    }

    @Override
    public String getTableName(String acctNature) throws ApplicationException {
        if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
            return "recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            return "ca_recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
            return "loan_recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
            return "td_recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
            return "gl_recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
            return "ddstransaction";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
            return "rdrecon";
        } else if (acctNature.contains("NPA")) {
            return "npa_recon";
        } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
            return "of_recon";
        }
        return null;
    }

    public String getAcTypeByAcNo(String acNo) throws ApplicationException {
        try {
            List listCurBrnCode = em.createNativeQuery("select accttype from accountmaster where acno = '" + acNo + "'").getResultList();
            if (!listCurBrnCode.isEmpty()) {
                Vector element = (Vector) listCurBrnCode.get(0);
                return element.get(0).toString();
            } else {
                listCurBrnCode = em.createNativeQuery("select accttype from td_accountmaster where acno='" + acNo + "'").getResultList();
                if (!listCurBrnCode.isEmpty()) {
                    Vector element = (Vector) listCurBrnCode.get(0);
                    return element.get(0).toString();
                } else {
                    listCurBrnCode = em.createNativeQuery("select acno from gltable where acno = '" + acNo + "'").getResultList();
                    if (!listCurBrnCode.isEmpty()) {
                        return acNo.substring(2, 4);
                    } else {
                        throw new ApplicationException("Account Code does not exist for " + acNo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getDoubleAndIntegerElementsFromList(List vecList) throws ApplicationException {
        try {
            if (!vecList.isEmpty()) {
                List list = new ArrayList();
                Vector tempEle1 = (Vector) vecList.get(0);
                if (tempEle1.get(0) != null) {
                    list.add(0, Double.parseDouble(tempEle1.get(0).toString()));
                } else {
                    list.add(0, 0.0);
                }
                if (tempEle1.get(1) != null) {
                    list.add(1, Integer.parseInt(tempEle1.get(1).toString()));
                } else {
                    list.add(1, 0);
                }
                return list;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    @Override
    public String getMonthName(int month) throws ApplicationException {
        String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthName[month - 1];
    }

    @Override
    public List getGlNames(String acno) throws ApplicationException {
        List acNoList = new ArrayList();
        try {
            acNoList = em.createNativeQuery("select distinct acname from gltable where substring(acno,3,8)='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acNoList;
    }

    public List getGlHeadName(String acno) throws ApplicationException {
        List glList = new ArrayList();
        try {
            glList = em.createNativeQuery("select distinct acname from gltable where acno='" + acno + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return glList;
    }

    @Override
    public Double getDoubleValueFromVector(Vector ele) throws ApplicationException {
        try {
            if (ele != null && ele.get(0) != null) {
                return Double.parseDouble(ele.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return 0.0D;
    }

    @Override
    public float getLoanRoi(double amt, String date, String acno) throws ApplicationException {
        float roi = 0f;
//        Vector tempvtr = null;
//        List tempList = null;
//        String rateofCode = null, intTableCode = null;
        try {
//            tempList = em.createNativeQuery("select a.int_table_code,a.ac_pref_dr,a.acc_pref_cr,b.rate_code,ifnull(b.pegg_freq,0),ifnull(d.roi,0),e.openingdt "
//                    + "from cbs_acc_int_rate_details a,cbs_loan_acc_mast_sec b, loan_appparameter d, accountmaster e"
//                    + "  where  a.acno = b.acno and a.acno = "
//                    + "d.acno and a.acno = e.acno and a.acno = '" + acno + "'"
//                    + " and a.eff_frm_dt <= '" + date + "' and   a.ac_int_ver_no=(select max(c.ac_int_ver_no) from cbs_acc_int_rate_details c where c.acno='" + acno + "' and c.eff_frm_dt <= '" + date + "')").getResultList();
//            if (!tempList.isEmpty()) {
//                tempvtr = (Vector) tempList.get(0);
//                intTableCode = tempvtr.get(0).toString();
//                rateofCode = tempvtr.get(3).toString();
//
//                if (rateofCode.equalsIgnoreCase("Ab")) {
//
//                    roi = Float.parseFloat(tempvtr.get(5).toString());
//                } else if (rateofCode.equalsIgnoreCase("Fi")) {
//                    Calendar cal = Calendar.getInstance();
//                    String date1 = CbsUtil.monthAdd(date, Integer.parseInt(tempvtr.get(4).toString()));
//                    long daydiff = CbsUtil.dayDiff(ymdFormat.parse(date), ymdFormat.parse(date1));
//                    if (daydiff == 0 || daydiff > 0) {
//                        roi = getCbsRoi(amt, intTableCode, date);
//                    } else {
//                        roi = Float.parseFloat(tempvtr.get(5).toString());
//                    }
//                } else if (rateofCode.equalsIgnoreCase("Fl")) {
//
//                    roi = getCbsRoi(amt, intTableCode, date);
//                }
//                roi = roi + Float.parseFloat(tempvtr.get(1).toString()) - Float.parseFloat(tempvtr.get(2).toString());
//            }
            roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(amt, date, acno));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return roi;
    }

    @Override
    public float getCbsRoi(double amt, String intTableCode, String date) throws ApplicationException {
        List tempList = null;
        Vector tempVector = null;
        String versionno = null, intmasterTableCode = null, normalIntIndicator = null;
        float basePrCr = 0f, basePrDr = 0f, roi = 0f, intprDr = 0f, intPrCr = 0f, normalPr = 0f;
        Date startDt, endDt, stDtMst, endDtMst;
        try {
            tempList = em.createNativeQuery("select interest_version_no,base_percentage_debit,base_percentage_credit,interest_master_table_code,start_date,end_date from cbs_loan_interest_code_master where "
                    + "interest_code='" + intTableCode + "'  and '" + date + "' between  start_date and end_date  and record_status = 'A'").getResultList();
            if (!tempList.isEmpty()) {
                tempVector = (Vector) tempList.get(0);
                versionno = tempVector.get(0).toString();
                basePrDr = Float.parseFloat(tempVector.get(1).toString());
                basePrCr = Float.parseFloat(tempVector.get(2).toString());
                intmasterTableCode = tempVector.get(3).toString();
                startDt = (Date) tempVector.get(4);
                endDt = (Date) tempVector.get(5);
            } else {
                tempList = em.createNativeQuery("select interest_version_no,base_percentage_debit,base_percentage_credit,interest_master_table_code,start_date,end_date"
                        + " from cbs_loan_interest_code_master_history  where interest_code='" + intTableCode + "'  and '" + date + "' between  start_date and end_date  and record_status = 'A'").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    versionno = tempVector.get(0).toString();
                    basePrDr = Float.parseFloat(tempVector.get(1).toString());
                    basePrCr = Float.parseFloat(tempVector.get(2).toString());
                    intmasterTableCode = tempVector.get(3).toString();
                    startDt = (Date) tempVector.get(4);
                    endDt = (Date) tempVector.get(5);
                }
            }
            tempList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master where code = '" + intmasterTableCode + "' and '" + date + "' between  start_date and end_date  and record_status = 'A'").getResultList();
            if (!tempList.isEmpty()) {
                tempVector = (Vector) tempList.get(0);
                intprDr = Float.parseFloat(tempVector.get(0).toString());
                intPrCr = Float.parseFloat(tempVector.get(1).toString());
                stDtMst = (Date) tempVector.get(2);
                endDtMst = (Date) tempVector.get(3);
            } else {
                tempList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit,start_date,end_date from cbs_loan_interest_master_history where code = '" + intmasterTableCode + "' and '" + date + "' between  start_date and end_date  and record_status = 'A'").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    intprDr = Float.parseFloat(tempVector.get(0).toString());
                    intPrCr = Float.parseFloat(tempVector.get(1).toString());
                    stDtMst = (Date) tempVector.get(2);
                    endDtMst = (Date) tempVector.get(3);
                }
            }
            tempList = em.createNativeQuery("select normal_interest_indicator, normal_interest_percentage from cbs_loan_interest_slab_master where interest_code ='" + intTableCode + "'  and " + amt + " between  begin_slab_amount and end_slab_amount  and record_status = 'A' and interest_version_no =" + versionno + "").getResultList();
            if (!tempList.isEmpty()) {
                tempVector = (Vector) tempList.get(0);
                normalIntIndicator = tempVector.get(0).toString();
                normalPr = Float.parseFloat(tempVector.get(1).toString());
            } else {
                tempList = em.createNativeQuery("select normal_interest_indicator, normal_interest_percentage from cbs_loan_interest_slab_master_history where interest_code ='" + intTableCode + "'  and " + amt + " between  begin_slab_amount and end_slab_amount  and record_status = 'a' and interest_version_no =" + versionno + "").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    normalIntIndicator = tempVector.get(0).toString();
                    normalPr = Float.parseFloat(tempVector.get(1).toString());
                }
            }
            if (normalIntIndicator != null) {
                if (normalIntIndicator.equalsIgnoreCase("F")) {
                    roi = normalPr + intprDr - intPrCr;
                }
                if (normalIntIndicator.equalsIgnoreCase("D")) {
                    roi = 0f;
                } else if (normalIntIndicator.equalsIgnoreCase("N")) {
                    roi = normalPr;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return roi;
    }

    @Override
    public List<AbbStatementPojo> getAbbStatement(String acNo, String fromDate, String toDate, String option) throws ApplicationException {
        List<AbbStatementPojo> returnList = new ArrayList<AbbStatementPojo>();
        double bal = 0, cramt = 0, balance1 = 0, recno = 0, dramt = 0, opbal = 0;
        Date dt = null, trantime = null, valdt = null;
        String particulars = "", instno = "", p1 = "", p2 = "", actnature = "";
        int trantype = -1, ty = -1;
        List tempList = null;
        try {
            int code = 0;
            List rDescList = em.createNativeQuery("select code from parameterinfo_report where reportname='ROI-IN-DESC'").getResultList();
            if (!rDescList.isEmpty()) {
                Vector autoVector = (Vector) rDescList.get(0);
                code = Integer.parseInt(autoVector.get(0).toString());
            }

            Calendar calFromDt = Calendar.getInstance();
            calFromDt.setTime(ymdFormat.parse(fromDate));
            calFromDt.add(Calendar.DATE, -1);
            String dtB = ymdFormat.format(calFromDt.getTime());
            opbal = getBalanceOnDate(acNo, dtB);
            balance1 = opbal;
            String vouchNo = "";
            if (option.equalsIgnoreCase("glb")) {
                tempList = em.createNativeQuery("select glhead, description,type,dramt ,cramt,groupcode,subgroupcode from glbtempactypeentry").getResultList();
            } else if (option.equalsIgnoreCase("plr")) {
                tempList = em.createNativeQuery("select glhead, description,amount4,groupcode,subgroupcode from pltempentry").getResultList();
            } else if (option.equalsIgnoreCase("dbr")) {
                tempList = em.createNativeQuery("select acnum, acname,crbal1,crbal0,crbal2,crdittotal,drbal1,drbal0,drbal2,debittotal,groupcode,subgroupcode from daybook").getResultList();
            } else if (option.contains("ASR")) {
                String as[] = option.split(":");
                vouchNo = as[1];
                String status = as[2];
                if (status.equalsIgnoreCase("ALL")) {
                    tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,cramt,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y'),cast(VoucherNo as unsigned) from td_recon where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and closeflag is null and Trantype<> 27 and auth='y' order by VoucherNo,dt,trantime,recno").getResultList();
                } else if ((!status.equalsIgnoreCase("ALL")) && vouchNo.equalsIgnoreCase("ALL")) {
                    tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,cramt,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y'),cast(VoucherNo as unsigned) from td_recon where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and closeflag is null and Trantype<> 27 and auth='y' "
                            + "and VoucherNo in(select distinct cast(b.VoucherNo as unsigned) from td_vouchmst a,td_recon b where a.acno = '" + acNo + "' and a.acno = b.acno and a.VoucherNo = b.VoucherNo and a.status = '" + status + "') order by VoucherNo,dt,trantime,recno").getResultList();
                } else {
                    tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,cramt,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y'),cast(VoucherNo as unsigned) from td_recon where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and closeflag is null and Trantype<> 27 and auth='y' "
                            + "and VoucherNo =" + vouchNo + " order by VoucherNo,dt,trantime,recno").getResultList();
                }

            } else {
                //  actnature = getAcNatureByAcNo(acNo);
                actnature = ftsPosting.getAccountNature(acNo);
                String tableName = getTableName(actnature);
                if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,cramt,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y') from " + tableName + " where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and closeflag is null and Trantype<> 27 and auth='y' order by dt,trantime,recno").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC) || actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,balance,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y') from " + tableName + " where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and auth='y' order by dt,trantime,recno").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    tempList = em.createNativeQuery("select dt,details,receiptno,dramt,cramt,balance,trantype,ty,trantime,recno,ValueDt,trandesc,date_format(instdt,'%d/%m/%Y') from " + tableName + " where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) and auth='y' order by dt,trantime,recno").getResultList();
                } else {
                    if (option.equalsIgnoreCase("gls")) {
                        tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,balance,trantype,ty,trantime,recno,'',0 from temp_glstat where ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) order by dt,trantime,recno").getResultList();
                    } else if (option.equalsIgnoreCase("glr")) {
                        tempList = em.createNativeQuery("select dt,'','',sum(dramt) ,sum(cramt),sum(ifnull(balance,0)),'','','','',dt,0 from temp_glstat group by dt").getResultList();
                    } else if (option.equalsIgnoreCase("glb")) {
                        tempList = em.createNativeQuery("select '',description,'',dramt ,cramt,'',groupcode,subgroupcode,'','','',0 from glbtempactypeentry").getResultList();
                    }
                }
            }
            String bankCode = "";
            List<MbSmsSenderBankDetail> bankList = nrRemote.getBankCode();
            if (!bankList.isEmpty()) {
                MbSmsSenderBankDetail bankEntity = bankList.get(0);
                bankCode = bankEntity.getBankCode();
            }
            if (tempList.size() > 0) {
                for (int i = 0; i < tempList.size(); i++) {
                    Vector ele = (Vector) tempList.get(i);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        dt = (Date) ele.get(0);
                    }
                    /*Details Modification*/
                    if (ele.get(1) != null) {
                        if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            if (code == 1) {
                                if ((ele.get(1).toString().contains("@")) && (ele.get(1).toString().contains("%"))) {
                                    particulars = ele.get(1).toString();
                                } else if ((ele.get(1).toString().contains("UPTO") || ele.get(1).toString().contains("UP TO"))) {
                                    if (code == 0) {
                                        particulars = ele.get(1).toString();
                                    } else {
                                        particulars = ele.get(1).toString().concat(" @").concat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, ymdFormat.format(dt))), ymdFormat.format(dt), acNo)).concat("%");
                                    }
                                } else {
                                    particulars = ele.get(1).toString();
                                }
                            } else {
                                if ((ele.get(1).toString().contains("@")) && (ele.get(1).toString().contains("%"))) {
                                    particulars = ele.get(1).toString().substring(0, ele.get(1).toString().indexOf("@"));
                                } else {
                                    particulars = ele.get(1).toString();
                                }
                            }
                        } else {
                            particulars = ele.get(1).toString();
                        }

                    } else {
                        particulars = "";
                    }

                    if (ele.get(2) != null) {
                        instno = ele.get(2).toString();
                        if (!instno.equals("") && bankCode.equalsIgnoreCase("CCBL")) {
                            long tmpInstNo = Long.parseLong(instno.trim());
                            if (tmpInstNo == 0) {
                                instno = "";
                            } else {
                                instno = String.valueOf(tmpInstNo);
                            }
                        }
                        if (ele.get(12) != null) {
                            if (!ele.get(12).toString().equals("01/01/1900")) {
                                particulars = particulars + " CHQ DT - " + ele.get(12).toString();
                            }
                        }
                    } else {
                        instno = "";
                    }
                    if (bankCode.equalsIgnoreCase("CCBL")) {
                        if (instno.equals("")) {
                            if (Integer.parseInt(ele.get(11).toString()) == 66) {
                                if (particulars.toUpperCase().contains("NPCI")) {
                                    instno = "";
                                } else {
                                    instno = "RTGS/NEFT";
                                }
                            }
                            if (particulars.contains("ECS")) {
                                instno = "ECS";
                            }
                            if (particulars.contains("ChqNo:")) {
                                String[] strArr = particulars.split(":");
                                instno = strArr[1];
                            }
                        }
                    }

                    if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                        dramt = Double.parseDouble(ele.get(3).toString());
                    }
                    if (ele.get(4) != null || !ele.get(4).toString().equalsIgnoreCase("")) {
                        cramt = Double.parseDouble(ele.get(4).toString());
                    }
                    if (ele.get(5) != null || !ele.get(5).toString().equalsIgnoreCase("")) {
                        bal = Double.parseDouble(ele.get(5).toString());
                    }

                    if (ele.get(6) != null || !ele.get(6).toString().equalsIgnoreCase("")) {
                        trantype = Integer.parseInt(ele.get(6).toString());
                        if (trantype == 0) {
                            p2 = "CSH ";
                        } else if (trantype == 1) {
                            p2 = "CLR ";
                        } else if (trantype == 8) {
                            p2 = "INT ";
                        } else {
                            if (Integer.parseInt(ele.get(11).toString()) == 66) {
                                if (particulars.toUpperCase().contains("NPCI")) {
                                    p2 = "";
                                } else {
                                    p2 = "RTGS/NEFT";
                                }
                            } else if (Integer.parseInt(ele.get(11).toString()) == 70) { //For ATM
                                p2 = "";
                            } else {
                                p2 = "TRF ";
                            }
                        }
                    }
                    if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                        ty = Integer.parseInt(ele.get(7).toString());
                        if (Integer.parseInt(ele.get(11).toString()) == 70) {
                            p1 = "";
                        } else {
                            if (ty == 0) {
                                p1 = "By ";
                            } else {
                                p1 = "To ";
                            }
                        }
                    }
                    if (ele.get(8) != null || !ele.get(8).toString().equalsIgnoreCase("")) {
                        trantime = (Date) ele.get(8);
                    }
                    if (ele.get(9) != null || !ele.get(9).toString().equalsIgnoreCase("")) {
                        recno = Double.parseDouble(ele.get(9).toString());
                    }
                    if (ele.get(10) != null || !ele.get(10).toString().equalsIgnoreCase("")) {
                        valdt = (Date) ele.get(10);
                    }
                    String voucherNo = "";
                    if (option.contains("ASR")) {
                        if (ele.get(13) != null || !ele.get(13).toString().equalsIgnoreCase("")) {
                            voucherNo = ele.get(13).toString();
                        }
                    }
                    bal = balance1 + cramt - dramt;
                    balance1 = bal;
                    particulars = p1 + p2 + particulars;
                    AbbStatementPojo pojo = new AbbStatementPojo();
                    pojo.setDate1(dmyFormat.format(dt));
                    pojo.setDt(dt);
                    pojo.setParticulars(particulars);
                    pojo.setChequeNo(instno);
                    pojo.setWithdrawl(dramt);
                    pojo.setDeposit(cramt);
                    pojo.setBalance(bal);
                    pojo.setTranType(trantype);
                    pojo.setTy(ty);
                    pojo.setTranTime(trantime);
                    pojo.setRecNo(recno);
                    pojo.setValueDate(dmyFormat.format(valdt));
                    pojo.setVoucherNo(voucherNo);
                    returnList.add(pojo);
                }
            } else {
                AbbStatementPojo pojo = new AbbStatementPojo();
                pojo.setDate1(dmyFormat.format(ymdFormat.parse(toDate)));
                pojo.setDt(ymdFormat.parse(toDate));
                pojo.setParticulars(particulars);
                pojo.setChequeNo(instno);
                pojo.setWithdrawl(dramt);
                pojo.setDeposit(cramt);
                pojo.setBalance(balance1);
                pojo.setTranType(trantype);
                pojo.setTy(ty);
                pojo.setTranTime(trantime);
                pojo.setRecNo(recno);
                pojo.setValueDate(dmyFormat.format(ymdFormat.parse(toDate)));
                pojo.setVoucherNo(vouchNo);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List getCAAcTypeList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("select ifnull(acctcode,'N.A') as acctcode from accounttypemaster where acctnature='" + CbsConstant.CURRENT_AC + "' order by acctcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public List getCASBAcTypeList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode From accounttypemaster Where AcctNature In ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "') And acctNature Is Not Null").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public List getCASBAcNatureList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("select code from cbs_parameterinfo where name in ('CTR_ACNATURE')").getResultList();
            Vector tableResultVect = (Vector) tableResult.get(0);
            tableResult = em.createNativeQuery("SELECT DISTINCT AcctNature From accounttypemaster Where AcctNature In (" + tableResultVect.get(0) + ") And acctNature Is Not Null").getResultList();
//            tableResult = em.createNativeQuery("SELECT DISTINCT AcctNature From accounttypemaster Where AcctNature In ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "') And acctNature Is Not Null").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public List getCibilCompanyList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("select code from cbs_parameterinfo where name in ('CIBIL')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public String getCibilVerNo() throws ApplicationException {
        List tableResult = new ArrayList();
        String result = "";
        try {
            tableResult = em.createNativeQuery("select code from cbs_parameterinfo where name in ('CIBIL_VER')").getResultList();
            if (tableResult.size() > 0) {
                Vector tableVect = (Vector) tableResult.get(0);
                result = tableVect.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    @Override
    public List getSBRDAcTypeList() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select acctCode,acctdesc from accounttypemaster where acctNature in('" + CbsConstant.SAVING_AC + "','" + CbsConstant.RECURRING_AC + "') order by acctCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    @Override
    public Double getBalanceOnDate(String acNo, String date) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            if (!acNo.equalsIgnoreCase("")) {
                acNat = ftsPosting.getAccountNature(acNo);
                if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TD_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from rdrecon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from gl_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from of_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from nparecon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27 and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27 and dt <= '" + date + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                }
                if (balanceList != null) {
                    Vector val = (Vector) balanceList;
                    if (val.get(0) != null) {
                        return new Double(val.get(0).toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return 0.0;
    }

    @Override
    public Double getBalanceInBetweenDate(String acNo, String frDt, String toDt) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            if (!acNo.equalsIgnoreCase("")) {
                acNat = ftsPosting.getAccountNature(acNo);
                if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TD_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from rdrecon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from gl_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from of_recon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from nparecon where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27 and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27  and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = '" + acNo + "' and dt>='" + frDt + "' and dt <= '" + toDt + "'").getSingleResult();
                }
                if (balanceList != null) {
                    Vector val = (Vector) balanceList;
                    if (val.get(0) != null) {
                        return new Double(val.get(0).toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return 0.0;
    }

    @Override
    public String getAcNatureByAcNo(String acNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select acctnature from accounttypemaster where acctCode = '" + getAcTypeByAcNo(acNo) + "'").getResultList();
            if (list.size() > 0) {
                Vector element = (Vector) list.get(0);
                return (String) element.get(0);
            } else {
                throw new ApplicationException("Account Nature for " + acNo + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getAcNatureByAcNat(String acNat) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct acctnature from accounttypemaster where acctnature = '" + acNat + "'").getResultList();
            if (list.size() > 0) {
                return list;
            } else {
                throw new ApplicationException("Account Nature for " + acNat + " does not exist");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String getAcNatureByAcType(String acType) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select acctNature from accounttypemaster where acctCode in('" + acType + "')").getResultList();
            if (!resultList.isEmpty()) {
                Vector ele = (Vector) resultList.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    public String currentDateByBrnCode(String BRCODE) throws ApplicationException {
        String currentDt = null;
        try {
            List tempBd = em.createNativeQuery("select date from bankdays where dayendflag='N' and Brncode='" + BRCODE + "'").getResultList();
            Vector tempCurrent = (Vector) tempBd.get(0);
            String Tempbd = tempCurrent.get(0).toString();
            String yy = Tempbd.substring(0, 4);
            String mm = Tempbd.substring(4, 6);
            String dd = Tempbd.substring(6, 8);
            currentDt = dd + "/" + mm + "/" + yy;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return "" + currentDt;
    }

    public List getOcupationDetails() throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("Select code,description from codebook where groupcode=6 and code <>0").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List getActCategoryDetails() throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no = '325' order by order_by").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List getOperatingModeDetails() throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select code,description  from codebook where groupcode = 4 and code <>0").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List getDocumentDetails() throws ApplicationException {
        List checkList = new ArrayList();
        try {
            checkList = em.createNativeQuery("select code,description from codebook where  groupcode = 14 and code <>0").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return checkList;
    }

    public List getAcctTypebyDLNature() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select distinct acctCode,acctdesc From accounttypemaster where acctNature in ('" + CbsConstant.DEMAND_LOAN + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getDSRDAcTypeList() throws ApplicationException {
        List varlist = new ArrayList();
        try {
            varlist = em.createNativeQuery("Select acctCode From accounttypemaster Where AcctNature in ('" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return varlist;
    }

    public List getFDMSAcTypeList() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select acctCode,acctdesc from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getAcctTypeForDs() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select acctCode,AcctDesc from accounttypemaster where acctNature = '" + CbsConstant.DEPOSIT_SC + "' order by acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getAccTypeExcludePO() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode,AcctDesc From accounttypemaster Where AcctNature <>'" + CbsConstant.PAY_ORDER + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAgentCode(String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select agcode,name from ddsagent where brncode='" + brCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAgentName(String agCode, String brCode) throws ApplicationException {
        try {
            String agName = "";
            List agNameList = em.createNativeQuery("select name from ddsagent where agcode='" + agCode + "' and brncode = '" + brCode + "'").getResultList();
            if (agNameList.size() > 0) {
                Vector agVect = (Vector) agNameList.get(0);
                agName = agVect.get(0).toString();
            } else {
                return agName;
            }
            return agName;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * ************** added By Zeeshan Waris **************************
     */
    @Override
    public List<MiniStatementPojo> getminiStatement(String acNo, String option) throws ApplicationException {
        List<MiniStatementPojo> returnList = new ArrayList<MiniStatementPojo>();
        double bal = 0, cramt = 0, balance1 = 0, recno = 0, dramt = 0, opbal = 0;
        Date dt = null, trantime = null;
        String particulars = "", instno = "", p1 = "", p2 = "", actnature = "";
        int trantype = -1, ty = -1;
        List tempList = null;
        String fromDate = null, toDate = null, preDt = null;
        try {
            if (option.equalsIgnoreCase("glb")) {
                tempList = em.createNativeQuery("select glhead, description,type,dramt ,cramt,groupcode,subgroupcode from glbtempactypeentry").getResultList();
            } else if (option.equalsIgnoreCase("plr")) {
                tempList = em.createNativeQuery("select glhead, description,amount4,groupcode,subgroupcode from pltempentry").getResultList();
            } else if (option.equalsIgnoreCase("dbr")) {
                tempList = em.createNativeQuery("select acnum, acname,crbal1,crbal0,crbal2,crdittotal,drbal1,drbal0,drbal2,debittotal,groupcode,subgroupcode from daybook").getResultList();
            } else {
                actnature = getAcNatureByAcNo(acNo);
                String tableName = getTableName(actnature);
                List resultList = em.createNativeQuery("select min(dt), max(dt) from (select acno,dt from " + tableName + " where acno='" + acNo + "' order by dt desc limit 10) tmp(acno,dt)").getResultList();
                if (!resultList.isEmpty()) {
                    Vector ele = (Vector) resultList.get(0);
                    fromDate = ele.get(0).toString();
                }
                preDt = CbsUtil.dateAdd(fromDate, -1);
                opbal = getBalanceOnDate(acNo, preDt);
                balance1 = opbal;
                if (actnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    tempList = em.createNativeQuery("select dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from  rdrecon  where acno='" + acNo + "' and auth='y' order by trantime desc limit 10").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || actnature.equalsIgnoreCase(CbsConstant.SS_AC) || actnature.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    tempList = em.createNativeQuery("select dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from  loan_recon where acno='" + acNo + "' and auth='y' order by trantime desc limit 10").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList = em.createNativeQuery("select dt ,details,instno ,dramt ,cramt ,cramt ,trantype ,ty ,trantime,recno from  td_recon where acno='" + acNo + "' and closeflag is null and Trantype<>27and auth='y' order by trantime desc limit 10").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.CURRENT_AC) || actnature.equalsIgnoreCase(CbsConstant.CC_AC)) {
                    tempList = em.createNativeQuery("select dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from  ca_recon where acno='" + acNo + "' and auth='y' order by trantime desc limit 10").getResultList();
                } else if (actnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    tempList = em.createNativeQuery("select dt ,details,instno ,dramt ,cramt ,balance ,trantype ,ty ,trantime,recno from  recon  where acno='" + acNo + "' and auth='y' order by DT  desc limit 10").getResultList();
                } else {
                    if (option.equalsIgnoreCase("gls")) {
                        tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,balance,trantype,ty,trantime,recno from temp_glstat where ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) order by dt,trantime,recno").getResultList();
                    } else if (option.equalsIgnoreCase("glr")) {
                        tempList = em.createNativeQuery("select dt,'','',sum(dramt) ,sum(cramt),sum(ifnull(balance,0)),'','','','',dt from temp_glstat group by dt").getResultList();
                    } else if (option.equalsIgnoreCase("glb")) {
                        tempList = em.createNativeQuery("select '',description,'',dramt ,cramt,'',groupcode,subgroupcode,'','' from glbtempactypeentry").getResultList();
                    }
                }
            }
            for (int i = tempList.size() - 1; i >= 0; i--) {
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    dt = (Date) ele.get(0);
                }
                if (ele.get(1) != null) {
                    particulars = ele.get(1).toString();
                }
                if (ele.get(2) != null) {
                    instno = ele.get(2).toString();
                }
                if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                    dramt = Double.parseDouble(ele.get(3).toString());
                }
                if (ele.get(4) != null || !ele.get(4).toString().equalsIgnoreCase("")) {
                    cramt = Double.parseDouble(ele.get(4).toString());
                }
                if (ele.get(5) != null || !ele.get(5).toString().equalsIgnoreCase("")) {
                    bal = Double.parseDouble(ele.get(5).toString());
                }
                if (ele.get(6) != null || !ele.get(6).toString().equalsIgnoreCase("")) {
                    trantype = Integer.parseInt(ele.get(6).toString());
                    if (trantype == 0) {
                        p2 = "CSH ";
                    } else if (trantype == 1) {
                        p2 = "CLR ";
                    } else if (trantype == 8) {
                        p2 = "INT ";
                    } else {
                        p2 = "TRF ";
                    }
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    ty = Integer.parseInt(ele.get(7).toString());
                    if (ty == 0) {
                        p1 = "By ";
                    } else {
                        p1 = "To ";
                    }
                }
                if (ele.get(8) != null || !ele.get(8).toString().equalsIgnoreCase("")) {
                    trantime = (Date) ele.get(8);
                }
                if (ele.get(9) != null || !ele.get(9).toString().equalsIgnoreCase("")) {
                    recno = Double.parseDouble(ele.get(9).toString());
                }
                bal = balance1 + cramt - dramt;
                balance1 = bal;
                particulars = p1 + p2 + particulars;
                MiniStatementPojo object = new MiniStatementPojo();
                object.setDate1(dmyFormat.format(dt));
                object.setDt(dt);
                object.setParticulars(particulars);
                object.setChequeNo(instno);
                object.setWithdrawl(dramt);
                object.setDeposit(cramt);
                object.setBalance(bal);
                object.setTranType(trantype);
                object.setTy(ty);
                object.setTranTime(trantime);
                object.setRecNo(recno);
                returnList.add(object);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getAllAcctcode() throws ApplicationException {
        List resultList = null;
        try {
            resultList = em.createNativeQuery("select distinct(acctcode) from accounttypemaster").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public String getAcctNature(String acctCode) throws ApplicationException {
        String accNature = "";
        try {
            List resultList = em.createNativeQuery("Select acctnature from accounttypemaster where acctcode='" + acctCode + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                accNature = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accNature;
    }

    public String getAcctDecription(String acctCode) throws ApplicationException {
        String accNature = "";
        try {
            List resultList = em.createNativeQuery("select acctdesc from accounttypemaster where acctcode='" + acctCode + "'").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                accNature = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accNature;
    }

    public String getBranchNameByBrncode(String brcode) throws ApplicationException {
        String branchName = "";
        try {
            List branchNameList = em.createNativeQuery("select branchname from branchmaster where brncode=" + Integer.parseInt(brcode)).getResultList();
            Vector element = (Vector) branchNameList.get(0);
            branchName = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return branchName;
    }

    public String getTransactionType(String tranCode) throws ApplicationException {
        String tranType = "";
        try {
            List tranTypeList = em.createNativeQuery("select description from codebook where groupcode=7 and code=" + Integer.parseInt(tranCode)).getResultList();
            Vector element = (Vector) tranTypeList.get(0);
            tranType = element.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return tranType;
    }

    public List<PostingReportPojo> getPostingReport(float trsno, String acType, String enterBy, String enteryDt, String brCode) throws ApplicationException {
        List<PostingReportPojo> postingpojoList = new ArrayList<PostingReportPojo>();
        try {
            String acctNature = "", table = "";
            String bnkName = null, bnkAddress = null;
            List objBan = getBranchNameandAddress(brCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List result = new ArrayList();
            acctNature = getAcNatureByAcType(acType);
            table = getTableName(acctNature);
            result = em.createNativeQuery("select acno,cramt,details from " + table + " where dt='" + enteryDt + "' and enterby='" + enterBy + "' and trsno=" + trsno + " and org_brnid='" + brCode + "' and dest_brnid='" + brCode + "' order by dramt,ACNO").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    PostingReportPojo balCert = new PostingReportPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    if (record.get(2) != null) {
                        balCert.setDetails(record.get(2).toString());
                    } else {
                        balCert.setDetails("");
                    }
                    postingpojoList.add(balCert);
                }
            }
            result = em.createNativeQuery("select a.acno,a.amount,a.details from pendingcharges a,accountmaster b  where a.dt='" + enteryDt + "' and a.enterby='" + enterBy + "'  and a.trsno=" + trsno + " and a.acno=b.acno  and b.CurBrCode ='" + brCode + "'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    PostingReportPojo balCert = new PostingReportPojo();
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    balCert.setAcno(record.get(0).toString());
                    balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                    balCert.setDetails(record.get(2).toString());
                    postingpojoList.add(balCert);
                }
            }
            return postingpojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAlphacodeExcludingHo() throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select brncode,alphacode from branchmaster where alphacode not in('HO')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    public String getAlphacodeByBrncode(String brncode) throws ApplicationException {
        String alphacode = "";
        try {
            List brncodeList = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(brncode)).getResultList();
            if (!brncodeList.isEmpty()) {
                Vector element = (Vector) brncodeList.get(0);
                alphacode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return alphacode;
    }

    public String getBrnRefNoByBrncode(String brncode) throws ApplicationException {
        String brnRefNo = "";
        try {
            List brncodeList = em.createNativeQuery("select ifnull(BrnRefNo,'') from branchmaster where brncode = cast('" + brncode + "' as unsigned)").getResultList();
            if (!brncodeList.isEmpty()) {
                Vector element = (Vector) brncodeList.get(0);
                brnRefNo = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brnRefNo;
    }

    public String getBrncodeByAlphacode(String alphacode) throws ApplicationException {
        String brncode = "";
        try {
            List alphacodeList = em.createNativeQuery("select brncode from branchmaster where alphacode='" + alphacode + "'").getResultList();
            if (!alphacodeList.isEmpty()) {
                Vector alphacodeVector = (Vector) alphacodeList.get(0);
                brncode = alphacodeVector.get(0).toString();
                brncode = brncode.length() == 1 ? "0" + brncode : brncode;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brncode;
    }

    public String getAcNameByAcno(String acno) throws ApplicationException {
        String acName = "";
        try {
            List acNameList = em.createNativeQuery("select acname from gltable where acno='" + acno + "'").getResultList();
            if (!acNameList.isEmpty()) {
                Vector element = (Vector) acNameList.get(0);
                acName = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return acName;
    }

    /**
     * ****************************************** End *************************
     */
    public Double getBalanceOnDateWise(String acNo, String date) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            acNat = ftsPosting.getAccountNature(acNo);
            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from se_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
            }
            if (balanceList != null) {
                Vector val = (Vector) balanceList;
                if (val.get(0) != null) {
                    return new Double(val.get(0).toString());
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return 0.0;
    }

    public List<AbbStatementPojo> getAbbStatementWies(String acNo, String fromDate, String toDate, String option) throws ApplicationException {
        List<AbbStatementPojo> returnList = new ArrayList<AbbStatementPojo>();
        double bal = 0, cramt = 0, balance1 = 0, recno = 0, dramt = 0, opbal = 0;
        Date dt = null, trantime = null, valdt = null;
        String particulars = "", instno = "", p1 = "", p2 = "", actnature = "";
        int trantype = -1, ty = -1;
        List tempList = new ArrayList();
        try {
            Calendar calFromDt = Calendar.getInstance();
            calFromDt.setTime(ymdFormat.parse(fromDate));
            calFromDt.add(Calendar.DATE, -1);
            String dtB = ymdFormat.format(calFromDt.getTime());
            opbal = getBalanceOnDateWise(acNo, dtB);
            balance1 = opbal;
            tempList = em.createNativeQuery("select dt,details,instno,dramt,cramt,balance,trantype,ty,trantime,recno,valuedt from se_recon where acno = '" + acNo + "' and ( dt <= '" + toDate + "' and dt >= '" + fromDate + "' ) order by dt,trantime,recno").getResultList();
            for (int i = 0; i < tempList.size(); i++) {
                Vector ele = (Vector) tempList.get(i);
                if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                    dt = (Date) ele.get(0);
                }
                if (ele.get(1) != null) {
                    particulars = ele.get(1).toString();
                }

                if (ele.get(2) != null) {
                    instno = ele.get(2).toString();
                } else {
                    instno = "";
                }

                if (ele.get(3) != null || !ele.get(3).toString().equalsIgnoreCase("")) {
                    dramt = Double.parseDouble(ele.get(3).toString());
                }
                if (ele.get(4) != null || !ele.get(4).toString().equalsIgnoreCase("")) {
                    cramt = Double.parseDouble(ele.get(4).toString());
                }
                if (ele.get(5) != null || !ele.get(5).toString().equalsIgnoreCase("")) {
                    bal = Double.parseDouble(ele.get(5).toString());
                }
                if (ele.get(6) != null || !ele.get(6).toString().equalsIgnoreCase("")) {
                    trantype = Integer.parseInt(ele.get(6).toString());
                    if (trantype == 0) {
                        p2 = "CSH ";
                    } else if (trantype == 1) {
                        p2 = "CLR ";
                    } else if (trantype == 8) {
                        p2 = "INT ";
                    } else {
                        p2 = "TRF ";
                    }
                }
                if (ele.get(7) != null || !ele.get(7).toString().equalsIgnoreCase("")) {
                    ty = Integer.parseInt(ele.get(7).toString());
                    if (ty == 0) {
                        p1 = "By ";
                    } else {
                        p1 = "To ";
                    }
                }
                if (ele.get(8) != null || !ele.get(8).toString().equalsIgnoreCase("")) {
                    trantime = (Date) ele.get(8);
                }
                if (ele.get(9) != null || !ele.get(9).toString().equalsIgnoreCase("")) {
                    recno = Double.parseDouble(ele.get(9).toString());
                }
                if (ele.get(10) != null || !ele.get(10).toString().equalsIgnoreCase("")) {
                    valdt = (Date) ele.get(10);
                }
                bal = balance1 + cramt - dramt;
                balance1 = bal;
                particulars = p1 + p2 + particulars;
                AbbStatementPojo pojo = new AbbStatementPojo();
                pojo.setDate1(dmyFormat.format(dt));
                pojo.setDt(dt);
                pojo.setParticulars(particulars);
                pojo.setChequeNo(instno);
                pojo.setWithdrawl(dramt);
                pojo.setDeposit(cramt);
                pojo.setBalance(bal);
                pojo.setTranType(trantype);
                pojo.setTy(ty);
                pojo.setTranTime(trantime);
                pojo.setRecNo(recno);
                pojo.setValueDate(dmyFormat.format(valdt));
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List dividendParameterCode() throws ApplicationException {
        List param = null;
        try {
            param = em.createNativeQuery("select code from parameterinfo_report where reportname='SHOW_DIVIDEND'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return param;
    }

    public String getBrncodeByAcno(String acno) throws ApplicationException {
        String acNat = "", brncode = "";
        List brnList;
        try {
            acNat = ftsPosting.getAccountNature(acno);
            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                brnList = em.createNativeQuery("select CurBrCode from td_accountmaster where acno='" + acno + "'").getResultList();
            } else {
                brnList = em.createNativeQuery("select CurBrCode from accountmaster where acno='" + acno + "'").getResultList();
            }

            if (!brnList.isEmpty()) {
                Vector element = (Vector) brnList.get(0);
                brncode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return brncode;
    }

    public List getAccTypeIncludeSBRDFD() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode,concat(AcctCode,'  ',AcctDesc) From accounttypemaster Where AcctNature in ('" + CbsConstant.MS_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.SAVING_AC + "') order by acctnature").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAcNatureIncludeRdFdMsDs() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctNature From accounttypemaster Where AcctNature in ('" + CbsConstant.MS_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAccType(String acNature) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode From accounttypemaster Where AcctNature in ('" + acNature + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAccTypeOnlyCrDbFlag(String acNature) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode From accounttypemaster Where AcctNature in ('" + acNature + "') and  CrDbFlag in ('B','D')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String getRefRecDesc(String refRecNo, String refCode) throws ApplicationException {
        String refDesc = "";
        try {
            List tableResultList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '" + refRecNo + "' and ref_code = '" + refCode + "'").getResultList();
            if (tableResultList.size() > 0) {
                Vector tableResultVect = (Vector) tableResultList.get(0);
                refDesc = tableResultVect.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return refDesc;
    }

    public List getRefRecList(String refRecNo) throws ApplicationException {
        try {
            List tableResultList = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no = '" + refRecNo + "' order by order_by, ref_desc desc").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getRefRecCode(String refRecNo, String refdesc) throws ApplicationException {
        try {
            String refcode = "";
            List tableResultList = em.createNativeQuery("select ref_code from cbs_ref_rec_type where ref_rec_no = '" + refRecNo + "' and REF_DESC='" + refdesc + "'").getResultList();
            if (tableResultList.size() > 0) {
                Vector tableResultVect = (Vector) tableResultList.get(0);
                refcode = tableResultVect.get(0).toString();
            }
            return refcode;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getRefCardStatuslist(String refrecNo, String cardStatus) throws ApplicationException {
        try {
            List CardStatuslist = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '" + refrecNo + "' and ref_code = '" + cardStatus + "'").getResultList();
            return CardStatuslist;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getChargeCode(String chargeType) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select distinct ss_gno from cbs_ref_rec_mapping  where s_gno = '" + chargeType + "' ").getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getChargeName(String code) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no ='108' and ref_code = '" + code + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBranchCodeList(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            List list1 = em.createNativeQuery("select alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
            Vector v1 = (Vector) list1.get(0);
            String aCode = v1.get(0).toString();
            if (aCode.equalsIgnoreCase("HO")) {
                result = em.createNativeQuery("SELECT 'A','ALL' union select cast(brncode as char(2)),alphacode from branchmaster").getResultList();
            } else {
                result = em.createNativeQuery("select brncode ,alphacode from branchmaster where brncode = " + Integer.parseInt(orgnCode)).getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List getAccountManager() throws ApplicationException {
        List manglist = new ArrayList();
        try {
            //manglist = em.createNativeQuery("select distinct cast(type as UNSIGNED),concat(type,' ',b.ref_desc) from cbs_cust_misinfo a,cbs_ref_rec_type b where Type is not null  and a.type=b.ref_code  and b.REF_REC_NO ='233' order by cast(type as UNSIGNED) ").getResultList();
            //manglist = em.createNativeQuery("select distinct office_id,concat(office_id,' ',office_name) from office_dept_master order by office_id").getResultList();
            manglist = em.createNativeQuery("select distinct a.office_id, concat(a.office_id,' ',b.REF_DESC) from office_dept_master a, cbs_ref_rec_type b where a.office_id = b.REF_CODE and b.REF_REC_NO = '233'order by office_id").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return manglist;
    }

    public List getGroupId(String type) throws ApplicationException {
        List grouplist = new ArrayList();
        try {
            // grouplist = em.createNativeQuery("select cast(REF_CODE as UNSIGNED),CONCAT(REF_CODE,' -> ',REF_DESC) from cbs_ref_rec_type where REF_REC_NO = '234' and cast(REF_CODE as UNSIGNED)in(select cast(GroupID as UNSIGNED) from cbs_cust_misinfo  where type = '" + type + "')order by cast(REF_CODE as UNSIGNED)").getResultList();
            grouplist = em.createNativeQuery("select dept_id,concat(dept_id,' ',dept) from office_dept_master where office_id = " + type + " order by dept_id").getResultList();
//            grouplist = em.createNativeQuery("select a.dept_id,concat(a.dept_id,' ',b.REF_DESC) from office_dept_master a, cbs_ref_rec_type b where a.dept_id=b.REF_CODE and "
//                    + " a.office_id = 3 and b.REF_REC_NO = '234' order by a.dept_id").getResultList();          
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grouplist;
    }

    public List getALLGroupId() throws ApplicationException {
        List allgrouplist = new ArrayList();
        try {
            allgrouplist = em.createNativeQuery("select distinct  ifnull(GroupID,'') from cbs_cust_misinfo where (GroupID <>'')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return allgrouplist;
    }

    public List getAccTypeIncludeRDFDMS() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode,AcctDesc From accounttypemaster Where AcctNature in ('" + CbsConstant.MS_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List getAcctypeNatureconstant() throws ApplicationException {
        List tableresult = new ArrayList();
        try {
            tableresult = em.createNativeQuery("Select distinct AcctNature From accounttypemaster Where AcctNature in ('" + CbsConstant.MS_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableresult;
    }

    /**
     *
     * @param brncode
     * @param dt
     * @param ty
     * @return
     * @throws ApplicationException
     */
    public List getUserId(String brncode, String dt, String ty) throws ApplicationException {
        List userIdList = new ArrayList();
        try {
            if (brncode.equalsIgnoreCase("0A")) {
                if (ty.equalsIgnoreCase("0")) {
                    userIdList = em.createNativeQuery("select distinct enterby from recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ca_recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from loan_recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from rdrecon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ddstransaction where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from td_recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from of_recon  where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from gl_recon  where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'").getResultList();
                } else {
                    userIdList = em.createNativeQuery("select distinct enterby from recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ca_recon where dt='" + dt + "'and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from loan_recon where dt='" + dt + "'and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from rdrecon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ddstransaction where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from td_recon where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from of_recon  where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from gl_recon  where dt='" + dt + "' and tranType= '0'and ty='" + ty + "'").getResultList();
                }
            } else {
                if (ty.equalsIgnoreCase("0")) {
                    userIdList = em.createNativeQuery("select distinct enterby from recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ca_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from loan_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from rdrecon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ddstransaction where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from td_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from of_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from gl_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'").getResultList();
                } else {
                    userIdList = em.createNativeQuery("select distinct enterby from recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ca_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from loan_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from rdrecon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from ddstransaction where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from td_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from of_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                            + "union select distinct enterby from gl_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'").getResultList();
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return userIdList;
    }

    /**
     *
     * @param brncode
     * @param dt
     * @param ty
     * @return
     * @throws ApplicationException
     */
    public List getTokenPaidBy(String brncode, String dt, String ty) throws ApplicationException {
        List userIdList = new ArrayList();
        try {
            userIdList = em.createNativeQuery("select distinct tokenpaidBy from recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from ca_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from loan_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from rdrecon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from ddstransaction where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from td_recon where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from of_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'"
                    + "union select distinct tokenpaidBy from gl_recon  where dt='" + dt + "' and substring(acno,1,2)='" + brncode + "' and tranType= '0'and ty='" + ty + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return userIdList;
    }

    /**
     *
     * @param acNo
     * @return
     * @throws ApplicationException
     */
    public List getReceiptNoByAcNo(String acNo) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("select distinct(VoucherNo) from  td_interesthistory where acno = '" + acNo + "'"
                    + " union select distinct(VoucherNo) from tdshistory where acno = '" + acNo + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List<PostingReportPojo> sbIntPostingReport(String acType, String status, String postingDt, String brCode) throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select distinct trsno,enterby from recon r, accountmaster a where a.acno=r.acno and "
                    + "a.accttype='" + acType + "' and a.accstatus=" + status + " and r.dt='" + postingDt + "' and trantype=8 and org_brnid = '" + brCode
                    + "' and dest_brnid = '" + brCode + "'").getResultList();
            if (resultList.isEmpty()) {
                throw new ApplicationException("Data does not exist. So please check Posting Date.");
            }
            Vector resultVect = (Vector) resultList.get(0);
            float trsNo = Float.parseFloat(resultVect.get(0).toString());
            String postedBy = resultVect.get(1).toString();
            List<PostingReportPojo> intList = getPostingReport(trsNo, acType, postedBy, postingDt, brCode);
            return intList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAccStatusdesc(String accStatusCode) throws ApplicationException {
        String accNature = "";
        try {
            List resultList = em.createNativeQuery("select Description from codebook  where GroupCode = 3 and code = " + accStatusCode + "").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                accNature = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return accNature;
    }

    public List getAcctTypeNpaList() throws ApplicationException {
        List result = null;
        try {
            result = em.createNativeQuery("select Acctcode From accounttypemaster where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "') and acctcode <> '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String getBillTable(String bNature) {
        if (bNature.equals("PO")) {
            return "bill_po";
        } else if (bNature.equals("TPO")) {
            return "bill_tpo";
        } else if (bNature.equals("AD")) {
            return "bill_ad";
        } else if (bNature.equals("DD")) {
            return "bill_dd";
        } else {
            return "bill_hoothers";
        }
    }

    public List getAcctfdRdMsNatList() throws ApplicationException {
        try {
            List tableResultList = em.createNativeQuery("select distinct(acctNature) from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getBucketList(String almBase) throws ApplicationException {
        try {
            List tableResultList = em.createNativeQuery("SELECT bucket_no, BUCKET_DESC FROM cbs_alm_bucket_master WHERE PROFILE_PARAMETER = '" + almBase + "' AND RECORD_STATUS = 'A' ORDER BY BUCKET_NO ").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAllNatureList() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select distinct(acctnature) from accounttypemaster").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public Integer getCodeByReportName(String reportName) throws ApplicationException {
        Integer code = 0;
        try {
            List list = em.createNativeQuery("select ifnull(code,0) as code from parameterinfo_report where "
                    + "reportname='" + reportName + "'").getResultList();
            if (!list.isEmpty()) {
                Vector element = (Vector) list.get(0);
                code = Integer.parseInt(element.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return code;
    }

    public List getTranDtList(String finFrDt, String finToDt) throws ApplicationException {
        try {
            List tableResultList = em.createNativeQuery("select distinct DATE_FORMAT(tran_Dt,'%d/%m/%Y') from  mb_charge_posting_history where TRAN_DT between '" + finFrDt + "' and '" + finToDt + "'").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getTrsNoList(String trsnDt) throws ApplicationException {
        try {
            List tableResultList = em.createNativeQuery("select distinct(TRS_NO) from mb_charge_posting_history where DATE_FORMAT(tran_Dt,'%Y%m%d') = '" + trsnDt + "'").getResultList();
            return tableResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getAlphacodeBasedOnBranch(String orgnBrCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String alphaCode = getAlphacodeByBrncode(orgnBrCode);
            if (alphaCode.equalsIgnoreCase("HO") || alphaCode.equalsIgnoreCase("CELL")) {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where alphacode "
                        + "not in('HO','CELL') order by brncode").getResultList();
            } else {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where "
                        + "brncode = cast('" + orgnBrCode + "' as unsigned)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List getAlphacodeBasedOnOrgnBranch(String orgnBrCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String alphaCode = getAlphacodeByBrncode(orgnBrCode);
            if (alphaCode.equalsIgnoreCase("HO")) {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where alphacode "
                        + "not in('CELL') order by brncode").getResultList();
            } else {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where "
                        + "brncode = cast('" + orgnBrCode + "' as unsigned)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getAlphacodeIncludingHo() throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = em.createNativeQuery("select alphacode,brncode from branchmaster order by brncode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return dataList;
    }

    public List getAcNatureListExcludingTl() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select distinct(acctnature) from accounttypemaster where acctnature not in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.PAY_ORDER + "') order by acctnature").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAtmGlhead(String atmBranch) throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select ATM_CASH_GENERAL_HEAD from atm_master where atm_branch = '" + atmBranch + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAcNaturetldlcaList() throws ApplicationException {
        List list = null;
        try {
            list = em.createNativeQuery("select distinct(acctNature) from accounttypemaster where acctNature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') order by acctNature").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAccTypeCcod(String acNature) throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            tableResult = em.createNativeQuery("Select AcctCode From accounttypemaster Where AcctNature in ('" + acNature + "') and AcctCode <> '" + CbsAcCodeConstant.CURRENT_AC.getAcctCode() + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public String getNoticeToBorrowerCode() throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select code from cbs_parameterinfo where name in ('NOTICE_BORROW')").getResultList();
            if (resultList.size() > 0) {
                Vector element = (Vector) resultList.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Please fill Slab day in table cbs_parameterinfo !");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAlphacodeAllAndBranch(String orgnBrCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String alphaCode = getAlphacodeByBrncode(orgnBrCode);
            if (alphaCode.equalsIgnoreCase("HO")) {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where alphacode "
                        + "not in('CELL') order by brncode").getResultList();
            } else {
                dataList = em.createNativeQuery("select alphacode,brncode from branchmaster where "
                        + "brncode = cast('" + orgnBrCode + "' as unsigned)").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String getSuspenceBalance(String acNo, String fYear, String segNo) throws ApplicationException {
        List balanceList = new ArrayList();
        String result = "";
        try {

            String otherUnclaimed = getPoddType(acNo);
            if (otherUnclaimed.equalsIgnoreCase("SN")) {
                balanceList = em.createNativeQuery("SELECT ifnull(sum(amount),0) FROM bill_sundry_dt where acno = '" + acNo + "' and fyear = '" + fYear + "' and seqno = " + segNo).getResultList();
            } else if (otherUnclaimed.equalsIgnoreCase("SP")) {
                balanceList = em.createNativeQuery("SELECT ifnull(sum(amount),0) FROM bill_suspense_dt where acno = '" + acNo + "' and fyear = '" + fYear + "' and seqno = " + segNo).getResultList();
            }
            if (balanceList != null) {
                Vector val = (Vector) balanceList.get(0);
                if (val.get(0) != null) {
                    result = val.get(0).toString();
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String getPoddType(String glAcno) throws ApplicationException {
        String poddType = "";
        try {
            List poddList = em.createNativeQuery("select InstNature from billtypemaster where GLHEAD = '" + glAcno + "' and InstCode in('po','dd')").getResultList();
            if (!poddList.isEmpty()) {
                Vector vtr = (Vector) poddList.get(0);
                poddType = vtr.get(0).toString();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return poddType;
    }

    public List getUnclaimedDate(String fYear, String tYear, String asOnDt) throws ApplicationException {
        List list = new ArrayList();
        try {
            String fyr = "-" + fYear;
            String tyr = "-" + tYear;
            List frDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', INTERVAL " + tyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr = (Vector) frDtList.get(0);
            String frDt = dtvtr.get(0).toString();
            list.add(frDt);

            List toDtList = em.createNativeQuery("select DATE_FORMAT(date_add('" + asOnDt + "', INTERVAL " + fyr + " YEAR),'%Y%m%d')").getResultList();
            Vector dtvtr1 = (Vector) toDtList.get(0);
            String toDt = dtvtr1.get(0).toString();
            list.add(toDt);

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAtmId(String atmBranch) throws ApplicationException {
        List list = null;
        try {
            if (atmBranch.equalsIgnoreCase("0A")) {
                list = em.createNativeQuery("select atm_id from atm_master").getResultList();
            } else {
                list = em.createNativeQuery("select atm_id from atm_master where atm_branch = '" + atmBranch + "'").getResultList();
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return list;
    }

    public List getAtmBranchList(String orgnCode) throws ApplicationException {
        List result = new ArrayList();
        try {
            if (orgnCode.equalsIgnoreCase("90")) {
                result = em.createNativeQuery("select cast(brncode as char(2)),alphacode from branchmaster where brncode in(select atm_branch from atm_master)").getResultList();
            } else {
                result = em.createNativeQuery("select cast(brncode as char(2)),alphacode from branchmaster where brncode = '" + orgnCode + "'").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public List getacNatureTLDL() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctnature) from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAccountNature() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctNature from accounttypemaster where acctNature not in('" + CbsConstant.PAY_ORDER + "') order by acctNature").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAtmhead(String atmId) throws ApplicationException {
        String atmhead = "";
        try {
            List atmHeadList = em.createNativeQuery("select atm_cash_general_head from atm_master where atm_id = '" + atmId + "'").getResultList();
            Vector vtr = (Vector) atmHeadList.get(0);
            atmhead = vtr.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return atmhead;
    }

    public String getNfsAtmHead() throws ApplicationException {
        try {
            List nfsPaidList = em.createNativeQuery("select acno from abb_parameter_info where purpose = 'NFS PAY/REC HEAD'").getResultList();
            Vector nfsVector = (Vector) nfsPaidList.get(0);
            return "90" + nfsVector.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    @Override
    public List getCustIdCheck(String aadharNo) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT ifnull(primarybrcode,'') as brcode from cbs_customer_master_detail "
                    + "where AADHAAR_NO = '" + aadharNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    @Override
    public List getCustId(String aadharNo) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT customerid from cbs_customer_master_detail where AADHAAR_NO = '" + aadharNo + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List getCustIdData(String functionType, String custId, String aadharNo) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (functionType.equalsIgnoreCase("mo")) {
//                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
//                        + "where reg_type='AD' and status In('R','D','E') and cust_id='" + custId + "' and aadhar_no='" + aadharNo + "'").getResultList();

                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
                        + "where reg_type='AD' and cust_id='" + custId + "' and aadhar_no='" + aadharNo + "' "
                        + "and ((status='D' and mapping_status='') or (status='D' and mapping_status='I') or "
                        + "(status='E' and mapping_status='I') or (status='I' and mapping_status='') or "
                        + "(status='R' and mapping_status=''))").getResultList();

            } else if (functionType.equalsIgnoreCase("mr")) {
                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
                        + "where reg_type='AD' and (status ='U' or status='D') and (mapping_status='I' or "
                        + "mapping_status='M') and cust_id='" + custId + "' and aadhar_no='" + aadharNo + "'").getResultList();
            } else if (functionType.equalsIgnoreCase("mi")) { //make inactive- aadhar is active at npci while inactive at cbs
//                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
//                        + "where reg_type='AD' and (status ='E' or status ='U') and cust_id='" + custId + "' and "
//                        + "aadhar_no='" + aadharNo + "'").getResultList();

                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
                        + "where reg_type='AD' and ((status ='E' and mapping_status ='I') or status ='U' or status ='R') and "
                        + "cust_id='" + custId + "' and aadhar_no='" + aadharNo + "'").getResultList();
            } else {
                list = em.createNativeQuery("select cust_id,aadhar_no,status,mapping_status from cbs_aadhar_registration "
                        + "where reg_type='AD' and status='E' and mapping_status='I' and cust_id='" + custId + "' and "
                        + "aadhar_no='" + aadharNo + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return list;
    }

    public String getAAdharAcno(String custId) throws ApplicationException {
        String aadharAcno = "";
        try {
            List aadharAcnoList = em.createNativeQuery("SELECT AADHAAR_LPG_ACNO from cbs_customer_master_detail   where customerid = '" + custId + "'").getResultList();
            if (!aadharAcnoList.isEmpty()) {
                Vector element = (Vector) aadharAcnoList.get(0);
                aadharAcno = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return aadharAcno;
    }

    public Double getcrrPercent(String dt) throws ApplicationException {
        double crrPer = 0d;
        try {

            List reportList = em.createNativeQuery("select ifnull(crrperc,0) from ho_crr_parameter where wefdate=(select max(wefdate) from ho_crr_parameter where wefdate <='" + dt + "' )").getResultList();
            if (reportList.isEmpty()) {
                throw new ApplicationException("Crr Percentage does not defined.");
            }
            Vector ele = (Vector) reportList.get(0);
            crrPer = Double.parseDouble(ele.get(0).toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }

        return crrPer;
    }

    public List getAcctTypeScheme() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.SAVING_AC + "') and CrDbFlag in('B','D')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcctNatureOnlyDB() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select distinct acctNature From accounttypemaster "
                    + "where acctNature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "') and CrDbFlag in('B','D')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcctTypeAsAcNatureOnlyDB(String acNature) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + acNature + "') and CrDbFlag in('B','D')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcctTypeSchemeWise(String acType) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select distinct SCHEME_CODE,SCHEME_DESCRIPTION From cbs_scheme_general_scheme_parameter_master "
                    + "where SCHEME_TYPE = '" + acType + "' ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public String getSchemeDescAcTypeAndSchemeWise(String acType, String schemeCode) throws ApplicationException {
        List resultlist = null;
        String schemeDesc = "";
        try {
            resultlist = em.createNativeQuery("select  SCHEME_DESCRIPTION From cbs_scheme_general_scheme_parameter_master "
                    + "where SCHEME_TYPE = '" + acType + "' and SCHEME_CODE = '" + schemeCode + "' ").getResultList();
            if (!resultlist.isEmpty()) {
                Vector vect = (Vector) resultlist.get(0);
                schemeDesc = vect.get(0).toString();
            }
        } catch (Exception e) {
        }
        return schemeDesc;
    }

    public List getActypeOnlyForDLProbableNPA() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select ifnull(CODE,'') From cbs_parameterinfo where NAME = 'PROBABLE_DL_AC_TYPE' ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcctTypeAsAcNatureOnlyDB1(String acNature) throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("select Acctcode From accounttypemaster "
                    + "where acctNature in('" + acNature + "') and CrDbFlag ='C'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultlist;
    }

    public List getAcTypeDescByClassificationAndNature(String classification, String nature) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode,concat(acctcode,' ',acctdesc,' ') from accounttypemaster where "
                    + "crdbflag in('" + classification + "') and acctnature='" + nature + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getAccseq() throws ApplicationException {
        try {
            List result = em.createNativeQuery("Select code From cbs_parameterinfo where name = 'MEMBER_AS_ACNO'").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                return (element.get(0).toString());
            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcctCodeDetails(String acctcode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ifnull(acctnature,'') as Nature,accttype from "
                    + "accounttypemaster where acctcode='" + acctcode + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data for A/c Code:" + acctcode);
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcopMode(String acno) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select opermode from accountmaster where AcNo = '" + acno + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data for oprationMode:" + acno);
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String selectForOperationMode(String opMode) throws ApplicationException {
        String opmode = "";
        try {

            List result = em.createNativeQuery("select description from codebook where groupcode=4 and code='" + opMode + "' ").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                opmode = (element.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return opmode;
    }

    public String getTdsOfficeAdd() throws ApplicationException {
        try {
            List result = em.createNativeQuery("select code from cbs_parameterinfo where name ='tds_office_add'").getResultList();
            if (result.size() > 0) {
                Vector element = (Vector) result.get(0);
                return (element.get(0).toString());
            } else {
                throw new ApplicationException("Tds office address does not exist!");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getTheftAcno(String custId) throws ApplicationException {
        String theftAcno = "";
        try {
            List theftAcnoList = em.createNativeQuery("select acno from customerid where custid = '" + custId + "' and Substring(acno,3,2) in(select acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "') and accttype = 'Tf')").getResultList();
            if (!theftAcnoList.isEmpty()) {
                Vector element = (Vector) theftAcnoList.get(0);
                theftAcno = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return theftAcno;
    }

    public String getArea(String folioNo) throws ApplicationException {
        String shHolderArea = "";
        try {
            List theftAreaList = em.createNativeQuery("select ifnull(area,'') from share_holder where custId = (select distinct(CustId) from customerid  where CustId =(select CustId from share_holder where Regfoliono = '" + folioNo + "'))").getResultList();
            if (!theftAreaList.isEmpty()) {
                Vector element = (Vector) theftAreaList.get(0);
                shHolderArea = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return shHolderArea;
    }

    public List getSundryCrAcno() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acno) from bill_sundry ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getOccupationByfolioNo(String folioNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("Select code,description from codebook where groupcode=6 and code in(select distinct(OrgnCode) from accountmaster where acno in(select acno from customerid where custid =(select custid from share_holder where Regfoliono = '" + folioNo + "')))").getResultList();
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getNomDobByfolioNo(String folioNo) throws ApplicationException {
        String nomDob = "01/01/1900";
        try {
            List nomList = em.createNativeQuery("select date_format(nomdob,'%d/%m/%Y') from nom_details where acno in(select acno from customerid where custid =(select custid from share_holder where Regfoliono = '" + folioNo + "'))").getResultList();
            if (!nomList.isEmpty()) {
                Vector element = (Vector) nomList.get(0);
                nomDob = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return nomDob;
    }

    public List getIntroducerAcNo(String folioNo) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct(IntroAccno) from accountmaster where acno in(select acno from customerid where custid =(select custid from share_holder where Regfoliono = '" + folioNo + "')) order by IntroAccno").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Introducer Account does not exist");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getOfficeDecByType(String Type) throws ApplicationException {
        String offoceDesc = "";
        try {
            List officeIdList = em.createNativeQuery("select ref_desc as officeDesc from cbs_ref_rec_type where REF_REC_NO = '233' and REF_CODE in('" + Type + "')").getResultList();
            if (!officeIdList.isEmpty()) {
                Vector element = (Vector) officeIdList.get(0);
                offoceDesc = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return offoceDesc;
    }

    public String getDeptDescByGroupId(String groupId) throws ApplicationException {
        String deptDesc = "";
        try {
            List deptIdList = em.createNativeQuery("select ref_desc as deptDesc from cbs_ref_rec_type where REF_REC_NO = '234' and REF_CODE in('" + groupId + "')").getResultList();
            if (!deptIdList.isEmpty()) {
                Vector element = (Vector) deptIdList.get(0);
                deptDesc = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return deptDesc;
    }

    public String getEmiReportName() throws ApplicationException {
        String repName = "";
        try {
            List repList = em.createNativeQuery("select name from cbs_parameterinfo  where code = 'rep'").getResultList();
            if (!repList.isEmpty()) {
                Vector element = (Vector) repList.get(0);
                repName = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return repName;
    }

    public double getshareMoneyBal(String folioNo, String dt) throws ApplicationException {
        double shareMoneyBal = 0;
        try {
            List shaList = em.createNativeQuery("select ifnull(status,''),count(shareno),ifnull(date_format(cs.Issuedt,'%Y%m%d'),'') from share_capital_issue sc,share_holder sh,"
                    + "certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                    + "and sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo and cs.status = 'A' and cs.Issuedt<='" + dt + "' ").getResultList();
            if (!shaList.isEmpty()) {
                Vector shaVector = (Vector) shaList.get(0);
                String status = shaVector.get(0).toString();
                Integer shareNo = Integer.parseInt(shaVector.get(1).toString());
                String issueDt = shaVector.get(2).toString();
                if (status.equalsIgnoreCase("A")) {
                    Vector tempVector = (Vector) em.createNativeQuery("select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <='" + dt + "')").getResultList().get(0);
                    double shareValue = Double.parseDouble(tempVector.get(0).toString());
                    shareMoneyBal = shareNo * shareValue;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return shareMoneyBal;
    }

    public double gettotalLoanBal(String folioNo, String dt) throws ApplicationException {
        double totalLoanBal = 0;
        try {
            List totalLoanBalList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0)from loan_recon where acno in(select acno from customerid where CustId = (select custId from share_holder where Regfoliono = '" + folioNo + "')and substring(acno,3,2) in(select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "'))) and dt <= '" + dt + "'").getResultList();
            Vector bv = (Vector) totalLoanBalList.get(0);
            totalLoanBal = Double.parseDouble(bv.get(0).toString());

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return totalLoanBal;
    }

    public String getOrderByActCode(String actCode) throws ApplicationException {
        String refCode = "";
        try {
            List repList = em.createNativeQuery("select ORDER_BY from cbs_ref_rec_type WHERE REF_REC_NO = '400' AND REF_CODE = '" + actCode + "'").getResultList();
            if (!repList.isEmpty()) {
                Vector element = (Vector) repList.get(0);
                refCode = element.get(0).toString();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return refCode;
    }

    public double getDisbursementAmt(String acNo, String dt) throws ApplicationException {
        double disbAmt = 0;
        try {
            List disList = em.createNativeQuery("select ifnull(AMTDISBURSED,0) from loandisbursement where acno = '" + acNo + "' and disbursementdt <= '" + dt + "'").getResultList();
            if (!disList.isEmpty()) {
                Vector vtr2 = (Vector) disList.get(0);
                disbAmt = Double.parseDouble(vtr2.get(0).toString());
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return disbAmt;
    }

    public List getUnRecoveredDemand(String type, String brnCode) throws ApplicationException {
        List grouplist = new ArrayList();
        try {
            grouplist = em.createNativeQuery("select distinct sno from cbs_loan_dmd_info where officeid ='" + type + "' and flag ='A' "
                    + "and brncode = '" + brnCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grouplist;
    }

    public List<VillageWiseEMIDetailPojo> getDemandDetails(String demNo) throws ApplicationException {
        List<VillageWiseEMIDetailPojo> demandPojoList = new ArrayList<VillageWiseEMIDetailPojo>();
        try {
            List result = new ArrayList();
            result = em.createNativeQuery("select distinct b.acno,a.custname,b.EI_AMT,b.shdl_num,date_format(b.dmd_date,'%d/%m/%Y'),"
                    + " date_format(b.dmd_eff_date,'%d/%m/%Y'),a.accttype from accountmaster a, cbs_loan_dmd_table b "
                    + " where b.dmd_srl_num = '" + demNo + "' and a.acno = b.acno order by dmd_eff_date,acno,shdl_num").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    VillageWiseEMIDetailPojo pDtl = new VillageWiseEMIDetailPojo();
                    pDtl.setAcNo(record.get(0).toString());
                    pDtl.setName(record.get(1).toString());
                    pDtl.setInstallment(Double.parseDouble(record.get(2).toString()));
                    pDtl.setSchdlNo(record.get(3).toString());
                    pDtl.setDate(record.get(4).toString());
                    pDtl.setDemEffDt(record.get(5).toString());
                    pDtl.setOutStandBal(Double.parseDouble(record.get(2).toString()));
                    pDtl.setDemandAmt(Double.toString(Double.parseDouble(record.get(2).toString())));
                    pDtl.setAcTypeDesc(getAcctDecription(record.get(6).toString()));
                    demandPojoList.add(pDtl);
                }
            }
            return demandPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPhoneNo(String brCode) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT BrPhone from parameterinfo where brncode = '" + brCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getOfficeDeptHead(String officeId, String deptId) throws ApplicationException {
        try {
            if (deptId.equalsIgnoreCase("ALL")) {
                return em.createNativeQuery("select dept_head,office_name,address from office_dept_master where office_id =" + officeId).getResultList();
            } else {
                return em.createNativeQuery("select dept_head,office_name,address from office_dept_master where office_id = " + officeId + " and  dept_id = " + deptId).getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String dmdAmtFlag() throws ApplicationException {
        List dmdAmtList = em.createNativeQuery("select CODE from cbs_parameterinfo where name = 'DMD_AMOUNT'").getResultList();
        if (!dmdAmtList.isEmpty()) {
            Vector v5 = (Vector) dmdAmtList.get(0);
            return v5.get(0).toString();
        }
        return "";
    }

    public List getGroupIdByTypeAndCustId(String custId, String type) throws ApplicationException {
        List grouplist = new ArrayList();
        try {
            grouplist = em.createNativeQuery("select dept_id,concat(dept_id,' ',dept) from office_dept_master where office_id = '" + type + "' and dept_id = (select groupId from cbs_cust_misinfo where CustomerId = '" + custId + "' and type = '" + type + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return grouplist;
    }

    public List<LoanDemandRecoveryPojo> loanDmdRecovery(String acNature, String acCode, String fromDt, String toDt, String orgnBrId) throws ApplicationException {
        List tempList = null, tempList1 = null, tempList4 = null;
        Vector tempVector = null;
        List<LoanDemandRecoveryPojo> finalList = new ArrayList<LoanDemandRecoveryPojo>();
        try {
            String acno = null;
            if (orgnBrId.equalsIgnoreCase("0A")) {
                orgnBrId = "";
            } else {
                orgnBrId = "AND a.curbrcode='" + orgnBrId + "'";
            }
            if (acNature.equalsIgnoreCase("0") && (acCode.equalsIgnoreCase("0") || acCode.equalsIgnoreCase(""))) {
                List acnatList = getAcctNatureOnlyDB();
                for (int k = 0; k < acnatList.size(); k++) {
                    Vector vect = (Vector) acnatList.get(k);
                    acNature = vect.get(0).toString();
                    if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        acCode = " substring(a.ACNO,3,2) in( select acctcode from accounttypemaster where acctnature in('" + acNature + "')) ";
                        String table_name = getTableName(acNature);
                        tempList1 = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE  " + acCode + "  AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype  IN( select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "') and crdbflag in('B','D')) " + orgnBrId + " and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + fromDt + "')").getResultList();
                        if (!tempList1.isEmpty()) {
                            for (int i = 0; i < tempList1.size(); i++) {
                                double preDemand = 0d;
                                double currDemand = 0d;
                                double recovery = 0d;
                                double emi = 0d;
                                double outDemand = 0d;
                                LoanDemandRecoveryPojo pojo = new LoanDemandRecoveryPojo();
                                tempVector = (Vector) tempList1.get(i);
                                acno = tempVector.get(0).toString();
                                pojo.setAcno(tempVector.get(0).toString());
                                pojo.setCustName(tempVector.get(2).toString());
                                tempList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno='" + acno + "' and paymentdt <='" + fromDt + "'").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    preDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "' and paymentdt between '" + fromDt + "' and '" + toDt + "' and status= 'UNPAID' ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    currDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "'  ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    emi = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from   " + table_name + "   where acno = '" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "'").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    recovery = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                outDemand = (preDemand + currDemand) - recovery;
                                pojo.setCurrDemand(currDemand);
                                pojo.setEmi(emi);
                                pojo.setOutsDemand(outDemand);
                                pojo.setPreDemand(preDemand);
                                pojo.setRecoveryamt(recovery);
                                pojo.setAcType(acNature);
                                finalList.add(pojo);
                            }
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                        acCode = " substring(a.ACNO,3,2) in( select acctcode from accounttypemaster where acctnature in('" + acNature + "')) ";
                        String table_name = getTableName(acNature);
                        tempList1 = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE " + acCode + "  AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype  IN( select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in('B','D')) " + orgnBrId + " and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + fromDt + "')").getResultList();
                        if (!tempList1.isEmpty()) {
                            for (int i = 0; i < tempList1.size(); i++) {
                                double preDemand = 0d;
                                double currDemand = 0d;
                                double recovery = 0d;
                                double emi = 0d;
                                double outDemand = 0d;
                                LoanDemandRecoveryPojo pojo = new LoanDemandRecoveryPojo();
                                tempVector = (Vector) tempList1.get(i);
                                acno = tempVector.get(0).toString();
                                pojo.setAcno(tempVector.get(0).toString());
                                pojo.setCustName(tempVector.get(2).toString());
                                tempList = em.createNativeQuery("select ifnull(sum(dramt),0) from  " + table_name + "  where acno='" + acno + "' and dt <='" + fromDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                                tempList4 = em.createNativeQuery("select ifnull(sum(cramt),0) from  " + table_name + "  where acno='" + acno + "' and dt <='" + fromDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    Vector tempVector1 = (Vector) tempList4.get(0);
                                    double drAmt = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                    double crAmt = Double.parseDouble(nft.format(Double.parseDouble(tempVector1.get(0).toString())));
                                    if (crAmt < drAmt) {
                                        preDemand = drAmt - crAmt;
                                    }
                                }
                                tempList = em.createNativeQuery("select ifnull(sum(dramt),0) from  " + table_name + "  where acno='" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    currDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "'  ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    emi = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from  " + table_name + "  where acno='" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                                if (!tempList.isEmpty()) {
                                    tempVector = (Vector) tempList.get(0);
                                    recovery = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                                }
                                outDemand = (preDemand + currDemand) - recovery;
                                pojo.setCurrDemand(currDemand);
                                pojo.setEmi(emi);
                                pojo.setOutsDemand(outDemand);
                                pojo.setPreDemand(preDemand);
                                pojo.setRecoveryamt(recovery);
                                pojo.setAcType(acNature);
                                finalList.add(pojo);
                            }
                        }
                    }
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) && (acCode.equalsIgnoreCase("0") || !acCode.equalsIgnoreCase("")) && (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase("")))) {
                if (acCode.equalsIgnoreCase("0")) {
                    acCode = " substring(a.ACNO,3,2) in( select acctcode from accounttypemaster where acctnature in('" + acNature + "')) ";
                } else {
                    acCode = " substring(a.ACNO,3,2) ='" + acCode + "'";
                }
                String table_name = getTableName(acNature);
                tempList1 = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE  " + acCode + "  AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype  IN( select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "') and crdbflag in('B','D') ) " + orgnBrId + " and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + fromDt + "')").getResultList();
                if (!tempList1.isEmpty()) {
                    for (int i = 0; i < tempList1.size(); i++) {
                        double preDemand = 0d;
                        double currDemand = 0d;
                        double recovery = 0d;
                        double emi = 0d;
                        double outDemand = 0d;
                        LoanDemandRecoveryPojo pojo = new LoanDemandRecoveryPojo();
                        tempVector = (Vector) tempList1.get(i);
                        acno = tempVector.get(0).toString();
                        pojo.setAcno(tempVector.get(0).toString());
                        pojo.setCustName(tempVector.get(2).toString());
                        tempList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0) from emidetails where acno='" + acno + "' and paymentdt <='" + fromDt + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            preDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "' and paymentdt between '" + fromDt + "' and '" + toDt + "' and status= 'UNPAID' ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            currDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "'  ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            emi = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from   " + table_name + "   where acno = '" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            recovery = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        outDemand = (preDemand + currDemand) - recovery;
                        pojo.setCurrDemand(currDemand);
                        pojo.setEmi(emi);
                        pojo.setOutsDemand(outDemand);
                        pojo.setPreDemand(preDemand);
                        pojo.setRecoveryamt(recovery);
                        pojo.setAcType(acNature);
                        finalList.add(pojo);
                    }
                }
            } else if ((acNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) && (acCode.equalsIgnoreCase("0") || !acCode.equalsIgnoreCase("")) && (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase(""))))
                    || (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC) && (acCode.equalsIgnoreCase("0") || !acCode.equalsIgnoreCase("")) && (!(acNature.equalsIgnoreCase("0") || acNature.equalsIgnoreCase(""))))) {
                if (acCode.equalsIgnoreCase("0")) {
                    acCode = " substring(a.ACNO,3,2) in( select acctcode from accounttypemaster where acctnature in('" + acNature + "')) ";
                } else {
                    acCode = " substring(a.ACNO,3,2) ='" + acCode + "'";
                }
                String table_name = getTableName(acNature);
                tempList1 = em.createNativeQuery("SELECT a.acno,ifnull(c.title,''),a.CUSTNAME,c.CrAddress FROM accountmaster a,customermaster c WHERE " + acCode + "  AND SUBSTRING(a.acno,5,6)=c.CustNo AND c.brncode = a.curbrcode AND SUBSTRING(a.acno,3,2)=c.actype  AND a.accttype  IN( select acctcode from accounttypemaster where acctnature in ('" + acNature + "') and crdbflag in('B','D')) " + orgnBrId + " and (a.closingdate is null or a.closingdate ='' or a.closingdate >'" + fromDt + "')").getResultList();
                if (!tempList1.isEmpty()) {
                    for (int i = 0; i < tempList1.size(); i++) {
                        double preDemand = 0d;
                        double currDemand = 0d;
                        double recovery = 0d;
                        double emi = 0d;
                        double outDemand = 0d;
                        LoanDemandRecoveryPojo pojo = new LoanDemandRecoveryPojo();
                        tempVector = (Vector) tempList1.get(i);
                        acno = tempVector.get(0).toString();
                        pojo.setAcno(tempVector.get(0).toString());
                        pojo.setCustName(tempVector.get(2).toString());
                        tempList = em.createNativeQuery("select ifnull(sum(dramt),0) from  " + table_name + "  where acno='" + acno + "' and dt <='" + fromDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                        tempList4 = em.createNativeQuery("select ifnull(sum(cramt),0) from  " + table_name + "  where acno='" + acno + "' and dt <='" + fromDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            Vector tempVector1 = (Vector) tempList4.get(0);
                            double drAmt = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                            double crAmt = Double.parseDouble(nft.format(Double.parseDouble(tempVector1.get(0).toString())));
                            if (crAmt < drAmt) {
                                preDemand = drAmt - crAmt;
                            }
                        }
                        tempList = em.createNativeQuery("select ifnull(sum(dramt),0) from  " + table_name + "  where acno='" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            currDemand = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        tempList = em.createNativeQuery("select ifnull(INSTALLAMT,0) from emidetails where acno='" + acno + "'  ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            emi = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        tempList = em.createNativeQuery("select ifnull(sum(cramt),0) from  " + table_name + "  where acno='" + acno + "' and dt between '" + fromDt + "' and '" + toDt + "' and trandesc in('0','3','4','5','7','8') ").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            recovery = Double.parseDouble(nft.format(Double.parseDouble(tempVector.get(0).toString())));
                        }
                        outDemand = (preDemand + currDemand) - recovery;
                        pojo.setCurrDemand(currDemand);
                        pojo.setEmi(emi);
                        pojo.setOutsDemand(outDemand);
                        pojo.setPreDemand(preDemand);
                        pojo.setRecoveryamt(recovery);
                        pojo.setAcType(acNature);
                        finalList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Print Stack" + e);
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List<BranchAdjPojo> getBranchAdjustment(String orgnId, String asonDate, String reptop) throws ApplicationException {
        List tempList = null, tempList1 = null;
        Vector tempVector = null;
        Vector tempVector2 = null;
        String brnCode;
        List<BranchAdjPojo> finalList = new ArrayList<BranchAdjPojo>();
        try {

            List tempList4 = em.createNativeQuery("select acno from abb_parameter_info where purpose='HEAD OFFICE'").getResultList();
            if (tempList4.isEmpty()) {
                throw new ApplicationException("Head office GL head does not exist");
            }
            Vector tempVec = (Vector) tempList4.get(0);
            String glhead = tempVec.get(0).toString();
            String ho_acno = 90 + glhead;
            List brnList = em.createNativeQuery("Select distinct brncode from branchmaster /*where brncode <> '90'*/  order by brncode ").getResultList();
            for (int i = 0; i < brnList.size(); i++) {
                BranchAdjPojo pojo = new BranchAdjPojo();
                double brAdjAsperGLB = 0d;
                double brAdjAsPerInd = 0d;
                double diff = 0d;
                tempVector = (Vector) brnList.get(i);
                brnCode = tempVector.get(0).toString();
                if (brnCode.length() != 2) {
                    brnCode = "0".concat(brnCode);
                }
                String acNo = brnCode + glhead;
                tempList1 = em.createNativeQuery("Select BranchName from branchmaster Where brncode='" + brnCode + "'").getResultList();
                tempVector = (Vector) tempList1.get(0);
                String branch = tempVector.get(0).toString();
                tempList = em.createNativeQuery("select  cast(ifnull(sum(cramt-dramt),0) as decimal(15,2)) from gl_recon where acno ='" + acNo + "' and dt<='" + asonDate + "'  /*and advicebrncode='" + brnCode + "' */ and trandesc<>999").getResultList();
                tempVector = (Vector) tempList.get(0);
                brAdjAsPerInd = new Double(tempVector.get(0).toString());
                tempList = em.createNativeQuery("select advicebrncode, cast(ifnull(sum(cramt-dramt),0) as decimal(14,2)) from gl_recon where acno ='" + ho_acno + "' and dt<='" + asonDate + "'  and trandesc<>999  and advicebrncode<>'' and advicebrncode='" + brnCode + "' group by advicebrncode").getResultList();
                if (!tempList.isEmpty()) {
                    tempVector = (Vector) tempList.get(0);
                    brAdjAsperGLB = new Double(tempVector.get(1).toString());
                }
                diff = brAdjAsperGLB + brAdjAsPerInd;
                pojo.setBranch(branch);
                pojo.setBalAsPerBranch(new BigDecimal(brAdjAsPerInd).divide(new BigDecimal(reptop)));
                pojo.setBalAsPerGLB(new BigDecimal(brAdjAsperGLB).divide(new BigDecimal(reptop)));
                pojo.setDiff(new BigDecimal(diff).divide(new BigDecimal(reptop)));
                finalList.add(pojo);
            }
        } catch (Exception e) {
            System.out.println("Error Is " + e);
            throw new ApplicationException(e);
        }
        return finalList;
    }

//    public List<MmsReportPojo> getMmsDetails(String reportType, String manDateType, String uploadDate, String zipFileName) throws ApplicationException {
//        List<MmsReportPojo> finalList = new ArrayList<MmsReportPojo>();
//        List templist = null;
//        Vector tempVector = null;
//        try {
//            if (reportType.equalsIgnoreCase("U")) {
//                templist = em.createNativeQuery("select MndtId,dbtrAcct_Tp_Prtry,dbtrAcct_Id_Othr_Id,dbtr_Nm,ocrncs_Frqcy,ocrncs_FrstColltnDt,"
//                        + "ocrncs_FnlColltnDt,colltnAmt,maxAmt,dbtrAgt_FinInstnId_ClrSysMmbId_MmbId,dbtrAgt_FinInstnId_Nm,Accept,reject_code "
//                        + " from mms_upload_xml_detail where mandate_type='" + manDateType + "' and upload_date='" + uploadDate + "'"
//                        + " and zip_file_name='" + zipFileName + "' and mandate_status='V' ").getResultList();
//
//                for (int i = 0; i < templist.size(); i++) {
//                    tempVector = (Vector) templist.get(i);
//                    MmsReportPojo pojo = new MmsReportPojo();
//                    pojo.setUmrn(tempVector.get(0).toString());
//                    pojo.setAcType(tempVector.get(1).toString());
//                    pojo.setAcNo(tempVector.get(2).toString());
//                    pojo.setAcName(tempVector.get(3).toString());
//                    pojo.setFrqncy(tempVector.get(4).toString());
//                    pojo.setFirstCollDt(tempVector.get(5).toString());
//                    pojo.setFinalCollDt(tempVector.get(6).toString());
//                    pojo.setCollectionAmt(new BigDecimal(tempVector.get(7).toString()));
//                    pojo.setMaxAmt(new BigDecimal(tempVector.get(8).toString()));
//                    pojo.setDebtorIFSC(tempVector.get(9).toString());
//                    pojo.setDebtorBnkName(tempVector.get(10).toString());
//                    String status = "";
//                    if (tempVector.get(11).toString().equalsIgnoreCase("A")) {
//                        status = "ACTIVE";
//                    } else if (tempVector.get(11).toString().equalsIgnoreCase("R")) {
//                        status = "REJECT";
//                    }
//                    pojo.setStatus(status);
//                    String mandateDes = "";
//                    String rejectionCode = tempVector.get(12) == null ? "" : tempVector.get(12).toString();
//                    if (tempVector.get(11).toString().equalsIgnoreCase("R")) {
//                        if (manDateType.equalsIgnoreCase("create")) {
//                            mandateDes = getRefRecDesc("320", rejectionCode);
//                        } else if (manDateType.equalsIgnoreCase("amend")) {
//                            mandateDes = getRefRecDesc("321", rejectionCode);
//                        } else if (manDateType.equalsIgnoreCase("cancel")) {
//                            mandateDes = getRefRecDesc("322", rejectionCode);
//                        }
//                    }
//                    pojo.setRejectRsn(mandateDes);
//                    finalList.add(pojo);
//                }
//            } else if (reportType.equalsIgnoreCase("D")) {
//                templist = em.createNativeQuery("select MndtId, dbtrAcct_Tp_Prtry, dbtrAcct_Id_Othr_Id, dbtr_Nm, ocrncs_Frqcy,"
//                        + "ocrncs_FrstColltnDt,ocrncs_FnlColltnDt,colltnAmt,maxAmt,dbtrAgt_FinInstnId_ClrSysMmbId_MmbId,"
//                        + "dbtrAgt_FinInstnId_Nm from mms_detail where mandate_type='" + manDateType + "'").getResultList();
//                for (int i = 0; i < templist.size(); i++) {
//                    tempVector = (Vector) templist.get(i);
//                    MmsReportPojo pojo = new MmsReportPojo();
//                    pojo.setUmrn(tempVector.get(0).toString());
//                    pojo.setAcType(tempVector.get(1).toString());
//                    pojo.setAcNo(tempVector.get(2).toString());
//                    pojo.setAcName(tempVector.get(3).toString());
//                    pojo.setFrqncy(tempVector.get(4).toString());
//                    pojo.setFirstCollDt(tempVector.get(5).toString());
//                    pojo.setFinalCollDt(tempVector.get(6).toString());
//                    pojo.setCollectionAmt(new BigDecimal(tempVector.get(7).toString()));
//                    pojo.setMaxAmt(new BigDecimal(tempVector.get(8).toString()));
//                    pojo.setDebtorIFSC(tempVector.get(9).toString());
//                    pojo.setDebtorBnkName(tempVector.get(10).toString());
//                    pojo.setStatus("");
//                    pojo.setRejectRsn("");
//                    finalList.add(pojo);
//                }
//            }
//        } catch (Exception e) {
//            throw new ApplicationException(e);
//        }
//        return finalList;
//    }
//
//    public List<MmsReportPojo> getZipFileNameList(String manDateType, String uploadDate) throws ApplicationException {
//        List<MmsReportPojo> finalList = new ArrayList<MmsReportPojo>();
//        List templist = null;
//        templist = em.createNativeQuery("select distinct zip_file_name from mms_upload_xml_detail"
//                + " where mandate_type='" + manDateType + "' and upload_date='" + uploadDate + "' and mandate_status='V'").getResultList();
//        return templist;
//    }
    public List getFidiltyTypeList() throws ApplicationException {
        List resultlist = null;
        try {
            resultlist = em.createNativeQuery("Select a.acctCode from accounttypemaster a, cbs_scheme_general_scheme_parameter_master b where a.acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "') and a.acctcode = b.scheme_type order by a.acctCode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultlist;
    }

    public List getLipAcctCodeList(String purpCode) throws ApplicationException {
        List resultList = null;
        try {
            resultList = em.createNativeQuery("Select a.acctCode,a.accttype,a.acctdesc,b.glheadmisc from accounttypemaster a, "
                    + "parameterinfo_miscincome b where b.Purpose ='" + purpCode + "' and a.acctcode = b.acctcode").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return resultList;
    }

    public List getAreaWiseDmdsrno(String area, String frDt, String toDt) throws ApplicationException {
        try {
            if (area.equalsIgnoreCase("ALL")) {
                return em.createNativeQuery("select SNO from cbs_loan_dmd_info where recoverydt between '" + frDt + "' and '" + toDt + "'").getResultList();
            } else {
                return em.createNativeQuery("select sno from cbs_loan_dmd_info where officeid = '" + area + "' and recoverydt between '" + frDt + "' and '" + toDt + "'").getResultList();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getIntTableName(String acNature) throws ApplicationException {
        try {
            if (acNature.equals(CbsConstant.FIXED_AC) || acNature.equals(CbsConstant.MS_AC)) {
                return "td_interesthistory";
            } else if (acNature.equals(CbsConstant.RECURRING_AC)) {
                return "rd_interesthistory";
            } else if (acNature.equals(CbsConstant.DEPOSIT_SC)) {
                return "dds_interesthistory";
            }
            return "";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getDocDetail(String custId, String fYear, String tableName) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct doc_details from " + tableName + " where customerid = '" + custId + "' and fyear= '" + fYear + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getBankName() throws ApplicationException {
        List bankList = em.createNativeQuery("select distinct bankname from bnkadd").getResultList();
        if (!bankList.isEmpty()) {
            Vector v5 = (Vector) bankList.get(0);
            return v5.get(0).toString();
        }
        return "";
    }

    public List getAllAcNature() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctNature) from accounttypemaster where acctnature not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getActCodeByAcNature(String acNature) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(AcctCode) from accounttypemaster where acctnature = '" + acNature + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcnoByFolio(String folio) throws ApplicationException {
        try {
            return em.createNativeQuery("select acno from accountmaster where acno in(select acno from customerid where custid = (select custId from share_holder where Regfoliono = '" + folio + "'))and accttype in(select REF_CODE from cbs_ref_rec_type where REF_REC_NO = '400')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAccountStatus() throws ApplicationException {
        try {
            return em.createNativeQuery("select code,Description from codebook where GroupCode = 3 and code not in(0,11,12,13,9,15) order by code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBatchVoucherNo(String txnMode, String orgBrCode, String dt) throws ApplicationException {

        List result = new ArrayList();
        try {
            if (txnMode.equalsIgnoreCase("0")) {
                // result = em.createNativeQuery("select tokenno from tokentable_credit where auth = 'N' and trantype = 0 and dt = '" + dt + "' order by tokenno ").getResultList();
                result = em.createNativeQuery("select acno from tokentable_credit where auth = 'N' and trantype = 0 and dt = '" + dt + "'and org_brnid = '" + orgBrCode + "' "
                        + "union "
                        + "select acno from tokentable_debit where auth = 'N' and trantype = 0 and dt = '" + dt + "'and org_brnid = '" + orgBrCode + "' ").getResultList();
            } else if (txnMode.equalsIgnoreCase("1")) {
                //result = em.createNativeQuery("select recno from  recon_clg_d where auth = 'N' and trantype = 1 and dt = '" + dt + "' order by recno").getResultList();
                result = em.createNativeQuery("select acno from  recon_clg_d where auth = 'N' and trantype = 1 and dt = '" + dt + "' and org_brnid = '" + orgBrCode + "'").getResultList();
            } else if (txnMode.equalsIgnoreCase("2")) {
                result = em.createNativeQuery("select DISTINCT trsno from recon_trf_d where auth = 'N' and trantype = 2 and dt = '" + dt + "'and org_brnid = '" + orgBrCode + "' order by trsno ").getResultList();
            }
            return result;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBnkNameAdd(String alphaCode) throws ApplicationException {

        try {
            return em.createNativeQuery("select bankname,bankaddress from bnkadd where alphacode = '" + alphaCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getGOIParameter() throws ApplicationException {

        try {
            return em.createNativeQuery("select * from cbs_ho_rbi_stmt_report where report_name in ('OSS3TBILL','OSS3GOIBONDS','OSS3SGS') ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCodeBookDescription(int groupcode, int code) throws ApplicationException {
        String description = "";
        try {
            List list = em.createNativeQuery("select ifnull(description,'') as description from codebook where groupcode=" + groupcode + " and code=" + code + "").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define description for groupcode:" + groupcode + " and code:" + code);
            }
            Vector ele = (Vector) list.get(0);
            description = ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return description;
    }

    public List getTdsDoctype(String refRecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select REF_CODE,REF_DESC from cbs_ref_rec_type where REF_REC_NO = '" + refRecNo + "' order by ORDER_BY").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String validateAccountType(String accNature, String accCode) throws ApplicationException {

        try {
            String ckycrDetaialQuery = "select S_GNO from cbs_ref_rec_mapping where gno='008' and S_GNO='" + accNature + "' and SS_GNO='" + accCode + "'";
            List selectList = em.createNativeQuery(ckycrDetaialQuery).getResultList();
            if (selectList.isEmpty()) {
                return "false";
            } else {
                return "true";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

    }

    public String getBranchCode(String empCode) throws ApplicationException {
        String code = "";
        try {
            List list = em.createNativeQuery("select ifnull(base_branch,'') from hr_personnel_details where emp_id='" + empCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Please define Base Branch for :" + empCode);
            }
            Vector ele = (Vector) list.get(0);
            code = ele.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return code;
    }

    public String getcbsFormatAcno(String noncbsAcno, String micr, String accType) throws ApplicationException {
        String cbsAcno = "";
        String sixDigitAcNo = "";
        String brnCode = "";

        sixDigitAcNo = ParseFileUtil.addTrailingZeros(noncbsAcno, 6);

        String query = "SELECT LPAD(BrnCode, 2, '0') as BrnCode FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and b.alphacode<>'HO' "
                + " and CONCAT (LPAD(micr, 3, '0'), LPAD(micrcode, 3, '0'),LPAD(branchcode, 3, '0')) ='" + micr + "'";
        List branCodeList = em.createNativeQuery(query).getResultList();

        if (!branCodeList.isEmpty()) {
            brnCode = ((Vector) branCodeList.get(0)).get(0).toString();
        }

        cbsAcno = brnCode + accType + sixDigitAcNo + "01";

        return cbsAcno;
    }

    public Map getConvertedAccTypeByMapping() throws ApplicationException {
        Map acctypeMap = new HashMap();
        String query = "select S_GNO,SSSS_GNO from cbs_ref_rec_mapping where GNO='009' ";
        List branCodeList = em.createNativeQuery(query).getResultList();
        if (!branCodeList.isEmpty()) {
            for (Object actypeData : branCodeList) {
                acctypeMap.put(((Vector) actypeData).get(0).toString(), ((Vector) actypeData).get(1).toString());
            }
        }
        return acctypeMap;
    }

    public List<HoReconPojo> getHeadOfficeDetails(String orgnId, String dt, String reptop) throws ApplicationException {

        List<HoReconPojo> finalList = new ArrayList<HoReconPojo>();
        try {

            List tempList4 = em.createNativeQuery("select acno from abb_parameter_info where purpose='HEAD OFFICE'").getResultList();
            if (tempList4.isEmpty()) {
                throw new ApplicationException("Head office GL head does not exist");
            }
            Vector tempVec = (Vector) tempList4.get(0);
            String head = tempVec.get(0).toString();

            HoReconPojo pojo;
            String brQuery = "";
            if (!orgnId.equals("90")) {
                brQuery = " where a.orgn = '" + orgnId + "'";
            }
            Map<String, String> brNameMap = new HashMap<String, String>();

            List brnList = em.createNativeQuery("select brncode,brncode,branchname  from branchmaster ORDER BY brncode").getResultList();
            if (brnList.isEmpty()) {
                throw new ApplicationException("Data does not exist in branch master");
            }

            List<String> orgBrnList = new ArrayList<String>();
            List<String> destBrnList = new ArrayList<String>();
            String reconQuery = "";

            for (int i = 0; i < brnList.size(); i++) {
                Vector vect = (Vector) brnList.get(i);
                orgBrnList.add(CbsUtil.lPadding(2, Integer.parseInt(vect.get(0).toString())));
                destBrnList.add(CbsUtil.lPadding(2, Integer.parseInt(vect.get(1).toString())));
                brNameMap.put(CbsUtil.lPadding(2, Integer.parseInt(vect.get(0).toString())), vect.get(2).toString());
            }

            for (String orgBrCode : orgBrnList) {
                String tmpHead = orgBrCode + head;
                if (orgBrCode.equals("90")) {
                    if (reconQuery.equals("")) {
                        reconQuery = reconQuery + "(select acno, org_brnid as orgn, advicebrncode as dest, 'Manual' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon  "
                                + "where acno='" + tmpHead + "' and org_brnid=dest_brnid and trandesc <>999 and iy<>999  and dt<='" + dt + "' and org_brnid='" + orgBrCode + "' group by advicebrncode having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n";
                    } else {
                        reconQuery = reconQuery + " union all \n "
                                + "(select acno, org_brnid as orgn, advicebrncode as dest, 'Manual' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon "
                                + "where acno='" + tmpHead + "' and org_brnid=dest_brnid and trandesc <>999 and iy<>999  and dt<='" + dt + "' and org_brnid='" + orgBrCode + "' group by advicebrncode having cast(sum(cramt-dramt) as decimal(14,2)) <>0)  \n";
                    }
                } else {
                    if (reconQuery.equals("")) {
                        reconQuery = reconQuery + "(select acno,org_brnid as orgn, dest_brnid as dest,'Manual' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                + "acno='" + tmpHead + "' and dt<='" + dt + "' and org_brnid=dest_brnid group by dest_brnid having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n";
                    } else {
                        reconQuery = reconQuery + " union all \n "
                                + "(select acno,org_brnid as orgn, dest_brnid as dest,'Manual' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                + "acno='" + tmpHead + "' and dt<='" + dt + "' and org_brnid=dest_brnid group by dest_brnid having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n";
                    }
                }

                for (String destBrCode : destBrnList) {
                    if (!orgBrCode.equals(destBrCode)) {
                        if (reconQuery.equals("")) {
                            reconQuery = reconQuery + "(select acno,org_brnid as orgn, dest_brnid as dest,'Auto' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                    + "acno='" + tmpHead + "' and dt<='" + dt + "' and (trandesc=999 or iy=999) and org_brnid='" + orgBrCode + "' and dest_brnid='" + destBrCode + "' having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n"
                                    + " union all \n"
                                    + "(select acno,org_brnid as orgn, dest_brnid  as dest,'Auto' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                    + "acno='" + tmpHead + "' and dt<='" + dt + "' and (trandesc=999 or iy=999) and org_brnid='" + destBrCode + "' and dest_brnid='" + orgBrCode + "' having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n";
                        } else {
                            reconQuery = reconQuery + " union all \n "
                                    + "(select acno,org_brnid as orgn, dest_brnid as dest ,'Auto' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                    + "acno='" + tmpHead + "' and dt<='" + dt + "' and (trandesc=999 or iy=999) and org_brnid='" + orgBrCode + "' and dest_brnid='" + destBrCode + "' having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n"
                                    + " union all \n"
                                    + "(select acno,org_brnid as orgn, dest_brnid as dest,'Auto' as modeOfTran, cast(sum(cramt-dramt) as decimal(14,2)) as amt from gl_recon where "
                                    + "acno='" + tmpHead + "' and dt<='" + dt + "' and (trandesc=999 or iy=999) and org_brnid='" + destBrCode + "' and dest_brnid='" + orgBrCode + "' having cast(sum(cramt-dramt) as decimal(14,2)) <>0) \n";
                        }
                    }
                }
            }
            if (reconQuery.equals("")) {
                throw new ApplicationException("Problem in query generation");
            }

            List reconList = em.createNativeQuery("select  a.orgn, a.modeOfTran, a.dest, a.acno, a.amt  from ( " + reconQuery + ")a " + brQuery
                    + "order by a.orgn, a.modeOfTran desc, a.dest, a.acno").getResultList();
            if (reconList.isEmpty()) {
                throw new ApplicationException("Data does not exist in branch master");
            }
            for (int i = 0; i < reconList.size(); i++) {
                Vector vect = (Vector) reconList.get(i);
                pojo = new HoReconPojo();
                pojo.setOrgn(vect.get(0).toString() + "-" + brNameMap.get(vect.get(0).toString()));
                pojo.setModeOfTran(vect.get(1).toString());

                pojo.setDest(vect.get(2).toString() + "-" + brNameMap.get(vect.get(2).toString()));
                pojo.setAcno(vect.get(3).toString());
                pojo.setAmt(Double.parseDouble(vect.get(4).toString()) / Double.parseDouble(reptop));
                finalList.add(pojo);
            }
            return finalList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public int getMoratoriumpd(String acNo) throws ApplicationException {
        int moratoriumpd = 0;
        try {
            List list = em.createNativeQuery("select MORATORIUM_PD from cbs_loan_acc_mast_sec where acno ='" + acNo + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                moratoriumpd = Integer.parseInt(vtr.get(0).toString());
            }
            return moratoriumpd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public int isExceessEmi(String dt) throws ApplicationException {
        try {
            int excessEmi = 0;
            List list = em.createNativeQuery("select distinct acno  from cbs_loan_emi_excess_details where dt <='" + dt + "'").getResultList();
            if (list.isEmpty()) {
                excessEmi = 0;
            } else {
                excessEmi = 1;
            }
            return excessEmi;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List getfrequencydescription(String refrecNo, String code) throws ApplicationException {
        try {
            List frequencyDescription = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_rec_no = '" + refrecNo + "' and ref_code='" + code + "'").getResultList();
            return frequencyDescription;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getCodeFromCbsParameterInfo(String reportName) throws ApplicationException {
        String code = "";
        try {
            List list = em.createNativeQuery("select ifnull(code,'') from cbs_parameterinfo "
                    + "where name = '" + reportName + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define parameter named as:" + reportName);
            }
            Vector ele = (Vector) list.get(0);
            code = ele.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return code;
    }

    public List checkCustId(String Custid) throws ApplicationException {
        try {
            List custIdList = em.createNativeQuery("select customerid from cbs_customer_master_detail where customerid='" + Custid + "'").getResultList();
            return custIdList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getCustIdfromacNo(String acNo) throws ApplicationException {
        try {
            List custIdlist = em.createNativeQuery("select CustId from customerid where Acno = '" + acNo + "'").getResultList();
            return custIdlist;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBankDetails(String iinNo) throws ApplicationException {
        List list = em.createNativeQuery("select bank_type,ifsc,micr from bank_iin_detail where iin='" + iinNo + "'").getResultList();

        return list;
    }

    @Override
    public List getMonthEndList(String date) throws ApplicationException {
        List list = em.createNativeQuery("select * from bankdays where date ='" + date + "' and (MEndFlag is null OR MEndFlag ='N') and Brncode <> '90'").getResultList();

        return list;
    }

    @Override
    public Double getBalanceOnDateWithoutInt(String acNo, String date) throws ApplicationException {
        Object balanceList = null;
        String acNat = "";
        try {
            if (!acNo.equalsIgnoreCase("")) {
                acNat = ftsPosting.getAccountNature(acNo);
                if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ca_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TD_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from loan_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from rdrecon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from gl_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from of_recon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from nparecon where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27 and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from td_recon where auth= 'Y' and acno = '" + acNo + "' and closeflag is null and trantype<>27 and dt <= '" + date + "' and trantype <>8 and TranDesc <> 3").getSingleResult();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    balanceList = em.createNativeQuery("select round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2) from ddstransaction where auth= 'Y' and acno = '" + acNo + "' and dt <= '" + date + "'").getSingleResult();
                }
                if (balanceList != null) {
                    Vector val = (Vector) balanceList;
                    if (val.get(0) != null) {
                        return new Double(val.get(0).toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return 0.0;
    }

    @Override
    public List getCaAdvanceAccount(String acctNature, String acType) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from accounttypemaster where acctnature = '" + acctNature + "' and CrDbFlag in('B','D') and acctcode = '" + acType + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getBranchInfoByAlphaCode(String alphaCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select BranchName,Address from branchmaster where AlphaCode = '" + alphaCode + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getAmountLimitList() throws ApplicationException {
        try {
            return em.createNativeQuery("select * from cbs_parameterinfo where name in('PURCHASE-LIMIT','WITHDRAWAL-LIMIT','MIN-LIMIT','PURCHASE-COUNT','WITHDRAWAL-COUNT')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public boolean isExCounterExit(String brCode) throws ApplicationException {
        try {
            List rsList = em.createNativeQuery("select brnCode from branchmaster where ex_counter = 'Y' and parent_brncode = "
                    + Integer.parseInt(brCode)).getResultList();
            if (rsList.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getAtmChgDuration() throws ApplicationException {
        try {
            return em.createNativeQuery("select code from cbs_parameterinfo where name='ATM-CHG-DURATION'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public String getStateCodeFromBranchMaster(String brnCode) throws ApplicationException {
        String StateCode = "";
        try {
            List list = em.createNativeQuery("select State from branchmaster where BrnCode ='" + brnCode + "'").getResultList();
            Vector vec = (Vector) list.get(0);
            StateCode = vec.get(0).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return StateCode;
    }

    @Override
    public String getBusinessDate() throws ApplicationException {
        String businessDate = "";
        try {
            Date dt = new Date();
            List dayBeginList = em.createNativeQuery("SELECT daybeginflag,dayendflag  FROM cbs_bankdays WHERE DATE = '" + ymdFormat.format(dt) + "'").getResultList();
            if (dayBeginList.isEmpty()) {
                throw new ApplicationException("Data is not present in Cbs Bankdays.");
            }
            Vector vtr = (Vector) dayBeginList.get(0);
            String cbsDaybeginFlag = vtr.get(0).toString();
            String cbsDayendFlag = vtr.get(1).toString();
            if (cbsDaybeginFlag.equalsIgnoreCase("M") && cbsDayendFlag.equalsIgnoreCase("M")) {
                throw new ApplicationException("Please try again after some time !!!");
            } else if (cbsDaybeginFlag.equalsIgnoreCase("Y") && cbsDayendFlag.equalsIgnoreCase("N")) {
                List dateList = em.createNativeQuery("select  date_format(date,'%Y%m%d') As business_date from cbs_bankdays where daybeginflag='Y' AND dayendflag='N'").getResultList();
                Vector dateVector = (Vector) dateList.get(0);
                businessDate = dateVector.get(0).toString();
            } else {
                List dateList = em.createNativeQuery("select date_format(MIN(date),'%Y%m%d') as business_date from cbs_bankdays where daybeginflag='N' AND dayendflag='N'").getResultList();
                Vector dateVector = (Vector) dateList.get(0);
                businessDate = dateVector.get(0).toString();
            }
        } catch (ApplicationException e) {
            throw new ApplicationException(e.getMessage());
        }
        return businessDate;
    }

    @Override
    public List getUsernameandUserId(String brncode) throws ApplicationException {
        try {
            if (brncode.equalsIgnoreCase("0A")) {
                return em.createNativeQuery("select userid, username  from securityinfo where status='A' group by UserId,UserName").getResultList();
            } else {
                return em.createNativeQuery("select userid, username  from securityinfo where status='A' and brncode='" + brncode + "' group by UserId,UserName").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String getIntAppFreq(String acNo) throws ApplicationException {
        try {
            String intFreq = "";
            List intFreqList = em.createNativeQuery("select ifnull(INT_APP_FREQ,'') from cbs_loan_acc_mast_sec where acno = '" + acNo + "'").getResultList();
            if (!intFreqList.isEmpty()) {
                Vector vtr = (Vector) intFreqList.get(0);
                intFreq = vtr.get(0).toString();
            }
            return intFreq;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String getAccountOpeningDate(String accountNo) throws Exception {
        String openingDate = "";
        try {
            List list = em.createNativeQuery("Select OpeningDt,ACNo from accountmaster where acno='" + accountNo + "'").getResultList();
            if (list.isEmpty()) {
                list = em.createNativeQuery("Select date_format(OpeningDt,'%Y%m%d'),ACNo from td_accountmaster where acno='" + accountNo + "'").getResultList();
                if (list.isEmpty()) {
                    throw new Exception("Account no does not exists");
                } else {
                    Vector v = (Vector) list.get(0);
                    openingDate = v.get(0).toString();
                }
            } else {
                Vector v = (Vector) list.get(0);
                openingDate = v.get(0).toString();
            }
        } catch (Exception e) {
        }
        return openingDate;
    }

    @Override
    public String getRecoverType(String schemeCode) throws Exception {
        String recoverType = "";
        try {
            List recoverList = em.createNativeQuery("select if(ifnull(STMT_MESSAGE,'CIP') = '','CIP',ifnull(STMT_MESSAGE,'CIP')) as recover  from cbs_scheme_general_scheme_parameter_master where SCHEME_CODE = '" + schemeCode + "'").getResultList();
            if (!recoverList.isEmpty()) {
                Vector vtr = (Vector) recoverList.get(0);
                recoverType = vtr.get(0).toString();
            }
            return recoverType;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getAllUsernameandUserId() throws Exception {
        try {
            return em.createNativeQuery("select userid, username  from securityinfo where status='A'").getResultList();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public List getNatureSecurityValueByAcno(String acNo, String asOnDt) throws Exception {
        try {
            return em.createNativeQuery("select security,(case WHEN security = 'p' THEN 'Primary' when security = 'C' THEN 'Collateral'  else ''end) as security_desc, "
                    + "(cast(sum(matvalue) as decimal(25,2))) as secAmt from loansecurity "
                    + "where  (ExpiryDate is null or ExpiryDate ='' or ExpiryDate >'" + asOnDt + "') "
                    + "and Entrydate<='" + asOnDt + "' and acno = '" + acNo + "' group by acno").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List getCustEntityType(String acno) throws ApplicationException {
        try {
            return em.createNativeQuery("select CustEntityType from cbs_customer_master_detail a,customerid b where cast(a.customerid as unsigned) = b.custid and b.acno = '" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String getUserNameByUserId(String userId) throws Exception {
        String userName = "ALL";
        try {
            List userList = em.createNativeQuery("select UserName from securityinfo where UserId = '" + userId + "'").getResultList();
            if (!userList.isEmpty()) {
                Vector vtr = (Vector) userList.get(0);
                userName = vtr.get(0).toString();
            }
            return userName;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}