/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.report.DatedSecurityPojo;
import com.cbs.dto.report.DepositeMprPojo;
import com.cbs.dto.report.LimitRenewalNoticePojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.LoanMprsPojo;
import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.dto.report.SuretyLetterPojo;
import com.cbs.dto.report.ho.DemandRecoveryDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.other.PostalDetailFacadeRemote;
import com.cbs.pojo.FreshRenewalEnhashmentPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.Spellar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author SAMY
 */
@Stateless(mappedName = "OtherLoanReportFacade")
public class OtherLoanReportFacade implements OtherLoanReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @EJB
    DDSReportFacadeRemote ddsRemote;
    @EJB
    private PostalDetailFacadeRemote postalFacadeRemote;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat y_m_dFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    private NumberFormat nft = new DecimalFormat("0.00");
    Date date = new Date();

    @Override
    public List<LimitRenewalNoticePojo> getlimitRenewalNotice(String acNature, String accountType, String fromDate, String toDate, String orgnbrcode) throws ApplicationException {
        List<LimitRenewalNoticePojo> dataList = new ArrayList<LimitRenewalNoticePojo>();
        List resultList = new ArrayList();
        try {

            List list = em.createNativeQuery("select distinct  bn.bankname,bn.bankaddress,bm.BranchName,bm.Pincode,City from bnkadd bn,branchmaster bm  where bn.alphacode = bm.alphacode \n"
                    + " and bm.BrnCode = " + Integer.parseInt(orgnbrcode) + " group by bn.bankname,bn.bankaddress").getResultList();

            Vector tempVector1 = (Vector) list.get(0);
            String bankname = tempVector1.get(0).toString();
            String bankaddress = tempVector1.get(1).toString();
            String branchName = tempVector1.get(2).toString();
            String brPin = tempVector1.get(3).toString();
            String city = tempVector1.get(4).toString();

            if (accountType.equalsIgnoreCase("All")) {
                resultList = em.createNativeQuery("select a.acc_no,cast(b.ODLimit as decimal(25,2)),date_format(a.DOCUMENT_EXP_DATE,'%d/%m/%Y'),concat(c.title,' ',c.custfullname),ifnull(c.MailBlock,''),concat(MailAddressLine1,' ',MailAddressLine2),e.REF_DESC,ifnull(MailPostalCode,'')"
                        + "from cbs_loan_borrower_details a,loan_appparameter b,cbs_customer_master_detail c, customerid d,cbs_ref_rec_type e "
                        + "where  a.acc_no = b.acno and a.acc_no = d.acno and cast(c.customerid as unsigned) = d.custid and e.REF_REC_NO = '001' and c.MailCityCode = e.REF_CODE and DOCUMENT_EXP_DATE between '" + fromDate + "' and '" + toDate + "' "
                        + "and substring(acc_no,3,2) in(select acctcode from accounttypemaster where AcctNature in('" + acNature + "'))").getResultList();

            } else {
                resultList = em.createNativeQuery("select a.acc_no,cast(b.ODLimit as decimal(25,2)),date_format(a.DOCUMENT_EXP_DATE,'%d/%m/%Y'),concat(c.title,' ',c.custfullname),ifnull(c.MailBlock,''),concat(MailAddressLine1,' ',MailAddressLine2), e.REF_DESC,ifnull(MailPostalCode,'')"
                        + "from cbs_loan_borrower_details a,loan_appparameter b,cbs_customer_master_detail c, customerid d,cbs_ref_rec_type e "
                        + "where  a.acc_no = b.acno and a.acc_no = d.acno and substring(a.acc_no,1,2) = '" + orgnbrcode + "' and cast(c.customerid as unsigned) = d.custid and e.REF_REC_NO = '001' and c.MailCityCode = e.REF_CODE and DOCUMENT_EXP_DATE between '" + fromDate + "' and '" + toDate + "' "
                        + "and substring(acc_no,3,2) ='" + accountType + "'").getResultList();
            }

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {

                    Vector vtr = (Vector) resultList.get(i);
                    LimitRenewalNoticePojo pojo = new LimitRenewalNoticePojo();
                    pojo.setBankName(bankname);
                    pojo.setBankAddress(bankaddress);
                    pojo.setBranchname(branchName);
                    pojo.setBrCity(city);
                    pojo.setBrPinCode(brPin);
                    pojo.setAccno(vtr.get(0).toString());
                    pojo.setOdlimit(new BigDecimal(vtr.get(1).toString()));
                    pojo.setExpirydate(vtr.get(2).toString());
                    pojo.setCustName(vtr.get(3).toString());
                    pojo.setMailblock(vtr.get(4).toString());
                    pojo.setMailaddressline1(vtr.get(5).toString());
                    pojo.setMailcitycode(vtr.get(6).toString());
                    pojo.setMailPostalCode(vtr.get(7).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    @Override
    public List<DatedSecurityPojo> datedSecurity(String dt1, int days, String security_type, String securityStatus, String brnCode, String acctNature, String acType) throws ApplicationException {
        List<DatedSecurityPojo> datedSecurityPojos = new ArrayList<DatedSecurityPojo>();
        Map<String, String> map = new HashMap();
        try {
            String dt2 = null;
            List result = new ArrayList();
            List objBan;
            if (brnCode.equalsIgnoreCase("0A")) {
                objBan = common.getBranchNameandAddress("90");
            } else {
                objBan = common.getBranchNameandAddress(brnCode);
            }
            String bnkName = null, bnkAddress = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctType = "", securityTypeQuery = "";
            if (acType.equalsIgnoreCase("All")) {
                acctType = "and substring(s.acno,3,2) in(select acctcode from accounttypemaster where acctnature = '" + acctNature + "')";
            } else {
                acctType = "and substring(s.acno,3,2)='" + acType + "'";
            }
            String secTypeQuery = "";
            if (security_type.equalsIgnoreCase("NON-DATED")) {
                securityTypeQuery = "";
                secTypeQuery = " and ((securitytype is null and lienacno = '' ) or securitytype='" + security_type + "')";
            } else {
                secTypeQuery = " and ((securitytype is null and lienacno <> '' ) or securitytype='" + security_type + "')";
                if (securityStatus.equalsIgnoreCase("EXPIRED IN")) {
                    dt2 = CbsUtil.dateAdd(dt1, days);
                    securityTypeQuery = " and matdate between '" + dt1 + "' and '" + dt2 + "' ";
                } else {
                    securityTypeQuery = " and matdate >= '" + dt1 + "'";
                }
            }
            if (brnCode.equalsIgnoreCase("0A")) {
                if (securityStatus.equalsIgnoreCase("ALL ACTIVE")) {
//                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
//                            + "DATE_FORMAT(entrydate,'%d/%m/%Y') from loansecurity  s, accountmaster a where status='active' "
//                            + " and (securitytype is null or securitytype='DATED') and a.openingdt<='" + dt1 + "' and (a.closingdate> '" + dt1
//                            + "' or closingdate='' or ifnull(a.closingdate,'')='') and matdate >= '" + dt1 + "' and s.acno=a.acno order by matdate,a.acno").getResultList();
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0),s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity  s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c"
                            + " where ((status='active' and Entrydate<='" + dt1 + "') or (status ='EXPRIRED' and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' ))  " + secTypeQuery + " and a.openingdt<='" + dt1 + "' "
                            + " and (a.closingdate> '" + dt1 + "' or closingdate='' or ifnull(a.closingdate,'')='') "
                            + securityTypeQuery + " and s.acno=a.acno and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + " order by s.SecurityOption,a.acno,matdate").getResultList();
                } else if (securityStatus.equalsIgnoreCase("ALL EXPIRED")) {
//                    result = em.createNativeQuery(" select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
//                            + "DATE_FORMAT(entrydate,'%d/%m/%Y') from loansecurity s, accountmaster a where status='EXPIRED' "
//                            + " and (securitytype is null or securitytype='DATED') and a.openingdt<='" + dt1 + "' and (a.closingdate< '" + dt1
//                            + "' or closingdate='' or ifnull(a.closingdate,'')='') and matdate <= '" + dt1 + "' and s.acno=a.acno order by matdate,a.acno ").getResultList();
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0) ,s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where status='EXPIRED'  and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' "
                            + " " + secTypeQuery + " and a.openingdt<='" + dt1 + "' and (a.closingdate< '" + dt1 + "' "
                            + "or closingdate='' or ifnull(a.closingdate,'')='') " + securityTypeQuery + " and s.acno=a.acno and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + "order by s.SecurityOption,a.acno,matdate").getResultList();
                } else if (securityStatus.equalsIgnoreCase("EXPIRED IN")) {
//                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
//                            + "DATE_FORMAT(entrydate,'%d/%m/%Y') from loansecurity  s, accountmaster a where status='active' "
//                            + " and  (securitytype is null or securitytype='DATED') and a.openingdt<= '" + dt1 + "' and (a.closingdate> '" + dt1
//                            + "' or closingdate='' or ifnull(a.closingdate,'')='') and  matdate between '" + dt1 + "' and '" + dt2 + "' and s.acno=a.acno "
//                            + "order by matdate,a.acno ").getResultList();
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0),s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity  s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where ((status='active' and Entrydate<='" + dt1 + "') or (status ='EXPRIRED' and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' ))  "
                            + " " + secTypeQuery + " and a.openingdt<= '" + dt1 + "' "
                            + "and (a.closingdate> '" + dt1 + "' or closingdate='' or ifnull(a.closingdate,'')='') " + securityTypeQuery + " and s.acno=a.acno  "
                            + "and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + "order by s.SecurityOption,a.acno,matdate").getResultList();
                }
            } else {
                if (securityStatus.equalsIgnoreCase("ALL ACTIVE")) {
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0),s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity  s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c "
                            + "where ((status='active' and Entrydate<='" + dt1 + "') or (status ='EXPRIRED' and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' ))  and a.curbrcode='" + brnCode + "'  " + secTypeQuery + "  and a.openingdt<='" + dt1 + "' "
                            + "and (a.closingdate> '" + dt1 + "' or closingdate='' or ifnull(a.closingdate,'')='') "
                            + securityTypeQuery + " and s.acno=a.acno and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + "order by s.SecurityOption,a.acno,matdate").getResultList();

                } else if (securityStatus.equalsIgnoreCase("ALL EXPIRED")) {
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby, "
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0),s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where status='EXPIRED'  and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' and a.curbrcode='" + brnCode + "' "
                            + " " + secTypeQuery + " and a.openingdt<='" + dt1 + "' and (a.closingdate< '" + dt1 + "' "
                            + "or closingdate='' or ifnull(a.closingdate,'')='') " + securityTypeQuery + " and s.acno=a.acno and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + "order by s.SecurityOption,a.acno,matdate").getResultList();

                } else if (securityStatus.equalsIgnoreCase("EXPIRED IN")) {
                    result = em.createNativeQuery("select a.acno,custname,s.remarks,matvalue,date_format(matdate,'%d/%m/%Y'),securitychg,status,s.enteredby,"
                            + "DATE_FORMAT(entrydate,'%d/%m/%Y'),ifnull(s.SecurityRoi,0),ifnull(s.IntTableCode,''),ifnull(turn_over_detail_flag,'N'),ifnull(s.lienvalue,0),s.SecurityOption, ifnull(s.addRoi,0) as addRoi, ifnull(s.approi,0) as approi from loansecurity  s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c where ((status='active' and Entrydate<='" + dt1 + "') or (status ='EXPRIRED' and Entrydate<='" + dt1 + "' and ExpiryDate >'" + dt1 + "' ))  and a.curbrcode='" + brnCode + "' "
                            + " " + secTypeQuery + " and a.openingdt<= '" + dt1 + "' "
                            + "and (a.closingdate> '" + dt1 + "' or closingdate='' or ifnull(a.closingdate,'')='') " + securityTypeQuery + " and s.acno=a.acno  "
                            + "and s.acno=b.acno and b.scheme_code = c.scheme_code " + acctType + " "
                            + "order by s.SecurityOption,a.acno,matdate").getResultList();
                }
            }

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {

                    Vector record = (Vector) result.get(i);
                    DatedSecurityPojo balCert = new DatedSecurityPojo();
                    double securityRoi = Double.parseDouble(record.get(9).toString());
                    double addRoi = Double.parseDouble(record.get(14).toString());
                    double appRoi = Double.parseDouble(record.get(15).toString());
                    String intTableCode = record.get(10).toString();
                    String flag = record.get(11).toString();
                    double matValue = Double.parseDouble(record.get(3).toString());

                    double roi = 0;
                    double outStandingBalance = Math.abs(common.getBalanceOnDate(record.get(0).toString(), dt1));
                    if (flag.equalsIgnoreCase("Y")) {
                        if (!intTableCode.equalsIgnoreCase("")) {
                            roi = securityRoi + addRoi + Double.parseDouble(loanInterestCalculationBean.getROI(intTableCode, Double.parseDouble(record.get(12).toString()), dt1));
                        } else {
                            roi = Double.parseDouble(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(record.get(12).toString()), dt1, record.get(0).toString()));
                        }
                    } else {
                        roi = Double.parseDouble(loanInterestCalculationBean.getRoiLoanAccount(outStandingBalance, dt1, record.get(0).toString()));
                    }

                    balCert.setAcNo(record.get(0).toString());
                    balCert.setCustName(record.get(1).toString());
                    balCert.setRemarks(record.get(2).toString());
                    balCert.setMatValue(Double.parseDouble(record.get(3).toString()));
                    balCert.setMatDate(record.get(4).toString());
                    balCert.setSecurityChg(record.get(5).toString());
                    balCert.setStatus(record.get(6).toString());
                    balCert.setEnteredBy(record.get(7).toString());
                    balCert.setEntryDate(record.get(8).toString());
                    balCert.setLienValue(Double.parseDouble(record.get(12).toString()));
                    balCert.setBankName(bnkName);
                    balCert.setBankAddress(bnkAddress);
                    balCert.setRoi(roi);
                    if (!map.containsKey(record.get(0).toString())) {
                        balCert.setOutstandingBalance(outStandingBalance);
                        map.put(record.get(0).toString(), record.get(0).toString());
                    } else {
                        balCert.setOutstandingBalance(0.0);
                    }
                    //   balCert.setOutstandingBalance(outStandingBalance);
                    balCert.setSecurityOptions(record.get(13).toString());
                    datedSecurityPojos.add(balCert);
                }
            }
            return datedSecurityPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<LoanPeriodPojo> loanPeriodReport(String acctCode, int term, int frmperod, int toperiod, float rifrm, float rito, double senfrmamt, double sentoamt, String frmdt, String todt, String scemeCode, String brCode, String reportType, String basedReport, String loanPeriod, String frmDtInt, String toDtInt) throws ApplicationException {
        List<LoanPeriodPojo> finalResultList = new ArrayList<LoanPeriodPojo>();
        Map<String, String> accnatureMap = new HashMap<String, String>();
        String subquery = scemeCode.equalsIgnoreCase("ALL") ? "" : "and ag.scheme_code ='" + scemeCode + "'";
        String subquery1 = loanPeriod.equalsIgnoreCase("ALL") ? "" : loanPeriod.equalsIgnoreCase("Active") ? "and (ifnull(aa.closingdate,'')='' or aa.closingdate > '" + todt + "')" : "and aa.accStatus = 9";
        String acType, acctNature, schemeDiscription = null;
        float roi = 0f;
        double overdueamt = 0d, disbamt = 0d, odAmt = 0d, intAmt = 0d;

        try {
            if (acctCode.equalsIgnoreCase("ALL")) {
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctNature from accounttypemaster where acctNature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEMAND_LOAN + "') And CrDbFlag ='D' order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
                }
            } else {
                List acctCodeNatureList = em.createNativeQuery("select acctcode,acctNature from accounttypemaster where acctcode in('" + acctCode + "') order by acctcode").getResultList();
                for (int i = 0; i < acctCodeNatureList.size(); i++) {
                    Vector vtr = (Vector) acctCodeNatureList.get(i);
                    accnatureMap.put(vtr.get(0).toString(), vtr.get(1).toString());
                }
            }
            Set<Map.Entry<String, String>> set = accnatureMap.entrySet();
            for (Map.Entry<String, String> me : set) {
                acType = me.getKey();
                acctNature = me.getValue();

                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (reportType.equals("0") && basedReport.equals("1") || reportType.equals("1") && basedReport.equals("1")) {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance,aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad,accountmaster aa "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + " and ag.loan_pd_month <=" + toperiod + "  " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <='" + todt + "' "
                                    + "and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        } else {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance,aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit  from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad,accountmaster aa "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + " and ag.loan_pd_month <=" + toperiod + " " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <='" + todt + "' "
                                    + "and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        }
                    } else {
                        if (reportType.equals("0") && basedReport.equals("1") || reportType.equals("1") && basedReport.equals("1")) {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance,aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit  from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad,accountmaster aa "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + " and ag.loan_pd_month <=" + toperiod + "  and aa.curbrcode='" + brCode + "' " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <='" + todt + "' "
                                    + "and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        } else {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance,aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit  from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad,accountmaster aa "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + " and ag.loan_pd_month <=" + toperiod + "  and aa.curbrcode='" + brCode + "'   " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <='" + todt + "' "
                                    + "and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        }
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {

                            Vector vtrmain = (Vector) result.get(j);
                            //roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(vtrmain.get(3).toString()), todt, vtrmain.get(0).toString());
                            roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(vtrmain.get(3).toString()), todt, vtrmain.get(0).toString()));
                            if (rifrm <= roi && roi <= rito) {
                                LoanPeriodPojo pojo = new LoanPeriodPojo();
                                //List disamtList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement where acno='" + vtrmain.get(0).toString() + "'").getResultList();
                                List disamtList = em.createNativeQuery("select a.disbAmt,b.intAmt from "
                                        + "(select ifnull(sum(amtdisbursed),0) disbAmt from loandisbursement where acno='" + vtrmain.get(0).toString() + "')a, "
                                        + "(select ifnull(sum(dramt),0) intAmt from ca_recon where acno='" + vtrmain.get(0).toString() + "' and dt between '" + frmDtInt + "' and '" + toDtInt + "' /*and trantype = 8*/ and ty=1 and trandesc in(3,4) )b").getResultList();
                                if (!disamtList.isEmpty()) {
                                    Vector disvtr = (Vector) disamtList.get(0);
                                    disbamt = Double.parseDouble(disvtr.get(0).toString());
                                    intAmt = Double.parseDouble(disvtr.get(1).toString());
                                }
                                overdueamt = Double.parseDouble(vtrmain.get(1).toString()) - Double.parseDouble(vtrmain.get(3).toString()) < 0 ? 0d : Double.parseDouble(vtrmain.get(1).toString()) - Double.parseDouble(vtrmain.get(3).toString());
                                odAmt = Double.parseDouble(vtrmain.get(7).toString());
                                List schemeDiscList = em.createNativeQuery("select scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code = '" + vtrmain.get(6).toString() + "'").getResultList();
                                if (!schemeDiscList.isEmpty()) {
                                    Vector vtrsch = (Vector) schemeDiscList.get(0);
                                    schemeDiscription = vtrsch.get(0).toString();
                                }
                                String npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(vtrmain.get(0).toString(), toDtInt);
                                if (npaStatus.equalsIgnoreCase("STANDARD") || npaStatus.equalsIgnoreCase("Operative")) {
                                    pojo.setStatus("1");
                                } else {
                                    pojo.setStatus("2");
                                }
                                pojo.setAccountNumber(vtrmain.get(0).toString());
                                pojo.setAccountType(acType);
                                pojo.setBalance(Double.parseDouble(vtrmain.get(1).toString()));
                                pojo.setCustName(vtrmain.get(2).toString());
                                pojo.setDisbAmount(disbamt);
                                pojo.setDuration(Integer.parseInt(vtrmain.get(5).toString()));
                                pojo.setOverdueAmt(overdueamt);
                                pojo.setRate(roi);
                                pojo.setSanctionedDate(y_m_dFormat.parse(vtrmain.get(4).toString()));
                                pojo.setSanctionedamt(Double.parseDouble(vtrmain.get(3).toString()));
                                pojo.setSchemeCode(scemeCode);
                                pojo.setSchemeDesc(schemeDiscription);
                                pojo.setOdLimit(odAmt);
                                pojo.setIntAmount(intAmt);
                                finalResultList.add(pojo);
                            }
                        }
                    }
                } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (reportType.equals("0") && basedReport.equals("1") || reportType.equals("1") && basedReport.equals("1")) {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance, aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and substring(ag.acno,3,2)='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + "  and ag.loan_pd_month <=" + toperiod + " " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <= '" + todt + "' "
                                    + " and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " " + subquery + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        } else {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance, aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and substring(ag.acno,3,2)='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + "  and ag.loan_pd_month <=" + toperiod + " " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <= '" + todt + "' "
                                    + " and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " " + subquery + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        }
                    } else {
                        if (reportType.equals("0") && basedReport.equals("1") || reportType.equals("1") && basedReport.equals("1")) {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance, aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and substring(ag.acno,3,2)='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + "  and ag.loan_pd_month <=" + toperiod + "  and aa.curbrcode='" + brCode + "' " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <= '" + todt + "' "
                                    + " and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " " + subquery + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        } else {
                            result = em.createNativeQuery("select distinct ag.acno,ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0) balance, aa.custname,ifnull(ad.sanctionlimit,0),date_format(ifnull(ad.sanctionlimitdt,date_format(aa.OpeningDt,'%Y-%m-%d')),'%Y-%m-%d'), ag.loan_pd_month, ag.scheme_code, ad.ODLimit from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and substring(ag.acno,3,2)='" + acType + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.loan_pd_month >=" + frmperod + "  and ag.loan_pd_month <=" + toperiod + "  and aa.curbrcode='" + brCode + "'  " + subquery1 + " and aa.openingdt <= '" + todt + "' and rb.dt <= '" + todt + "' "
                                    + " and aa.openingdt between '" + frmdt + "' and '" + todt + "' and ad.sanctionlimit between " + senfrmamt + " and " + sentoamt + " " + subquery + " group by ag.acno,aa.custname,ad.sanctionlimit, aa.intdeposit,ag.loan_pd_month,ag.scheme_code,ad.sanctionlimitdt order by ag.acno ").getResultList();
                        }
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector vtrmain = (Vector) result.get(j);
                            //roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(vtrmain.get(3).toString()), todt, vtrmain.get(0).toString());
                            roi = Float.parseFloat(loanInterestCalculationBean.getRoiLoanAccount(Double.parseDouble(vtrmain.get(3).toString()), todt, vtrmain.get(0).toString()));
                            if (rifrm <= roi && roi <= rito) {
                                LoanPeriodPojo pojo = new LoanPeriodPojo();
                                //List disamtList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement where acno='" + vtrmain.get(0).toString() + "'").getResultList();
                                List disamtList = em.createNativeQuery("select a.disbAmt,b.intAmt from "
                                        + "(select ifnull(sum(amtdisbursed),0) disbAmt from loandisbursement where acno='" + vtrmain.get(0).toString() + "')a, "
                                        + "(select ifnull(sum(dramt),0) intAmt from loan_recon where acno='" + vtrmain.get(0).toString() + "' and dt between '" + frmDtInt + "' and '" + toDtInt + "' /*and trantype = 8*/ and ty=1 and trandesc in(3,4) )b").getResultList();

                                if (!disamtList.isEmpty()) {
                                    Vector disvtr = (Vector) disamtList.get(0);
                                    disbamt = Double.parseDouble(disvtr.get(0).toString());
                                    intAmt = Double.parseDouble(disvtr.get(1).toString());
                                }
                                overdueamt = Double.parseDouble(vtrmain.get(1).toString()) - Double.parseDouble(vtrmain.get(3).toString()) < 0 ? 0d : Double.parseDouble(vtrmain.get(1).toString()) - Double.parseDouble(vtrmain.get(3).toString());
                                odAmt = Double.parseDouble(vtrmain.get(7).toString());
                                List schemeDiscList = em.createNativeQuery("select scheme_description from cbs_scheme_general_scheme_parameter_master where scheme_code = '" + vtrmain.get(6).toString() + "'").getResultList();
                                if (!schemeDiscList.isEmpty()) {
                                    Vector vtrsch = (Vector) schemeDiscList.get(0);
                                    schemeDiscription = vtrsch.get(0).toString();
                                }
                                String npaStatus = interBranchFacade.fnGetLoanStatusOfAccountTillDate(vtrmain.get(0).toString(), toDtInt);
                                System.out.println(npaStatus);
                                if (npaStatus.equalsIgnoreCase("STANDARD") || npaStatus.equalsIgnoreCase("Operative")) {
                                    pojo.setStatus("1");
                                } else {
                                    pojo.setStatus("2");
                                }
                                pojo.setAccountNumber(vtrmain.get(0).toString());
                                pojo.setAccountType(acType);
                                pojo.setBalance(Double.parseDouble(vtrmain.get(1).toString()));
                                pojo.setCustName(vtrmain.get(2).toString());
                                pojo.setDisbAmount(disbamt);
                                pojo.setDuration(Integer.parseInt(vtrmain.get(5).toString()));
                                pojo.setOverdueAmt(overdueamt);
                                pojo.setRate(roi);
                                pojo.setSanctionedDate(y_m_dFormat.parse(vtrmain.get(4).toString()));
                                pojo.setSanctionedamt(Double.parseDouble(vtrmain.get(3).toString()));
                                pojo.setSchemeCode(scemeCode);
                                pojo.setSchemeDesc(schemeDiscription);
                                pojo.setOdLimit(odAmt);
                                pojo.setIntAmount(intAmt);
                                finalResultList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return finalResultList;
    }

    @Override
    public List<LoanMisCellaniousPojo> cbsLoanMisLaniousReport(String accountType, String psector, String psubSector, String pschemeCode, String psecure, String pappCategory, String pweekersSection, String pcategoryOption, String prelation, String brncode, String pminCommunity, String frmDt) throws ApplicationException {
        List sectorList = null;
        Vector sectorVect = null, tempVector = null;
        List<LoanMisCellaniousPojo> finalResult = new ArrayList<LoanMisCellaniousPojo>();
        String sectorDescr = null, acNo = null, sectorp = null, acctNature = null, custName = null, subSectorp = null, subSectorpDisc = null, schemeCode = null, schemeCodeDisc = null, secure = null, secureDesic = null, appCategory = null, appCategoryDisc = null, weekersSection = null, weekersSectionDisc = null, categoryOption = null, categoryOptionDesc = null, minCommunity = null, minCommunityDesc = null, relation = null, relationDesc = null;
        double overdueAmt = 0d, balance = 0d, sensiodLimit = 0d, disbAmount = 0d;
        BigDecimal roi = new BigDecimal(0);
        int loanPeriod = 0;
        try {
            int isExceessEmi = common.isExceessEmi(frmDt);
            if (accountType.equalsIgnoreCase("ALL")) {

                if (!psector.equalsIgnoreCase("ALL")) {
                    if (brncode.equalsIgnoreCase("0A")) {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r,accountmaster am  where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.openingdt <= '" + frmDt + "'  and accstatus<>9 ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r,accountmaster am, accounttypemaster tm  where r.ref_code=lb.sector and lb.acc_no=am.acno and am.accttype = tm.acctcode and tm.CrDbFlag ='D' and r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')").getResultList();
                    } else {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r,accountmaster am  where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "'  and accstatus<>9 ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r,accountmaster am, accounttypemaster tm  where r.ref_code=lb.sector and lb.acc_no=am.acno and am.accttype = tm.acctcode and tm.CrDbFlag ='D' and r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')").getResultList();
                    }
                } else {
                    if (brncode.equalsIgnoreCase("0A")) {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r,accountmaster am  where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.openingdt <= '" + frmDt + "' and accstatus<>9 ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r,accountmaster am, accounttypemaster tm  where r.ref_code=lb.sector and lb.acc_no=am.acno and am.accttype = tm.acctcode and tm.CrDbFlag ='D' and r.ref_rec_no='182' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')").getResultList();
                    } else {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r,accountmaster am  where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and accstatus<>9 ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r,accountmaster am, accounttypemaster tm  where r.ref_code=lb.sector and lb.acc_no=am.acno and am.accttype = tm.acctcode and tm.CrDbFlag ='D' and r.ref_rec_no='182' and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')").getResultList();
                    }
                }
                //Starting the mainloop
                for (int i = 0; i < sectorList.size(); i++) {
                    List tempList = null;
                    acNo = null;
                    sectorVect = (Vector) sectorList.get(i);
                    acNo = sectorVect.get(0).toString();
                    sectorp = sectorVect.get(1).toString();
                    if (psector.equalsIgnoreCase("ALL")) {
                        sectorDescr = "ALL";
                    } else {
                        tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + psector + "' and ref_rec_no='182'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            sectorDescr = tempVector.get(0).toString();
                        }
                    }
                    tempList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acNo.substring(2, 4) + "' ").getResultList();

                    if (!tempList.isEmpty()) {
                        tempVector = (Vector) tempList.get(0);
                        acctNature = tempVector.get(0).toString();
                    }

                    if (brncode.equalsIgnoreCase("0A")) {
                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,ad.sanctionlimit,ag.loan_pd_month "
                                    + "from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad, accountmaster aa  where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4)
                                    + "' and ag.acno=rb.acno and ag.acno = ad.acno and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    } else {
                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,ad.sanctionlimit,ag.loan_pd_month "
                                    + "from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad, accountmaster aa  where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4)
                                    + "' and ag.acno=rb.acno and ag.acno = ad.acno   and aa.curbrcode='" + brncode + "' and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    }

                    if (brncode.equalsIgnoreCase("0A")) {
                        if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,ad.sanctionlimit,ag.loan_pd_month "
                                    + "from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4)
                                    + "' and ag.acno=rb.acno and ag.acno = ad.acno  and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    } else {

                        if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,ad.sanctionlimit,ag.loan_pd_month "
                                    + "from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4)
                                    + "' and ag.acno=rb.acno and ag.acno = ad.acno   and aa.curbrcode='" + brncode + "' and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    }
                    if (!tempList.isEmpty()) {
                        for (int j = 0; j < tempList.size(); j++) {
                            tempVector = (Vector) tempList.get(j);
                            overdueAmt = 0;
                            balance = (Double.parseDouble(tempVector.get(0).toString()));
                            custName = tempVector.get(1).toString();
                            sensiodLimit = Double.parseDouble(tempVector.get(2).toString());
                            roi = new BigDecimal(loanInterestCalculationBean.getRoiLoanAccount(i, ymdFormat.format(date), acNo));
                            accountType = acNo.substring(2, 4);
                            loanPeriod = (Integer) tempVector.get(3);
                            List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + acNo + "'").getResultList();
                            if (acNoList1.size() == 0) {
                                List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("A/Cs Whose Plan Has Finished", acNo, 1, 200, frmDt, common.getBrncodeByAcno(acNo), "", 0, isExceessEmi, null, 0);
                                for (OverdueEmiReportPojo pojo : overdueEmiList) {
                                    BigDecimal ovrdueAmt = pojo.getAmountOverdue();
                                    overdueAmt = ovrdueAmt.doubleValue();
                                }
                            } else {
                                List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", acNo, 1, 200, frmDt, common.getBrncodeByAcno(acNo), "", 0, isExceessEmi, null, 0);
                                for (OverdueEmiReportPojo pojo : overdueEmiList) {
                                    BigDecimal ovrdueAmt = pojo.getAmountOverdue();
                                    overdueAmt = ovrdueAmt.doubleValue();
                                }
                            }
                            // overdueAmt = balance - sensiodLimit < 0 ? 0 : balance - sensiodLimit;
                            tempList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement where acno='" + acNo + "'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                disbAmount = Double.parseDouble(tempVector.get(0).toString());
                            }
                        }
                    } else {
                        continue;
                    }
                    if (!psubSector.equalsIgnoreCase("ALL")) {

                        tempList = em.createNativeQuery(" select ref_desc from cbs_ref_rec_type where ref_code='" + psubSector + "' and ref_rec_no='183'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorpDisc = tempVector.get(0).toString();
                        }
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.sub_sector and r.ref_rec_no='183' and lb.acc_no='" + acNo + "' and r.ref_code='" + psubSector + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorp = tempVector.get(0).toString();
                        } else {
                            continue;
                        }

                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.sub_sector and r.ref_rec_no='183' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorp = tempVector.get(0).toString();
                            subSectorpDisc = psubSector;
                        }
                    }
                    if (!pschemeCode.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.scheme_code and r.ref_rec_no='203' and lb.acc_no='" + acNo + "' and r.ref_code= '" + pschemeCode + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            schemeCode = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pschemeCode + "' and ref_rec_no='203'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            schemeCodeDisc = tempVector.get(0).toString();
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.scheme_code and r.ref_rec_no='203' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            schemeCode = tempVector.get(0).toString();
                            schemeCodeDisc = pschemeCode;
                        }
                    }
                    if (!psecure.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.secured and r.ref_rec_no='187' and lb.acc_no='" + acNo + "' and r.ref_code='" + psecure + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            secure = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + psecure + "' and ref_rec_no='187'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                secureDesic = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.secured and r.ref_rec_no='187' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            secure = tempVector.get(0).toString();
                            secureDesic = psecure;
                        }
                    }
                    if (!pappCategory.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.applicant_category and r.ref_rec_no='208' and lb.acc_no='" + acNo + "' and r.ref_code='" + pappCategory + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            appCategory = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pappCategory + "' and ref_rec_no='208'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            appCategoryDisc = tempVector.get(0).toString();
                        }

                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.applicant_category and r.ref_rec_no='208' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            appCategory = tempVector.get(0).toString();
                            appCategoryDisc = pappCategory;
                        }
                    }
                    if (!pweekersSection.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_customer_master_detail a,cbs_loan_borrower_details b,cbs_ref_rec_type r where a.customerid=b.cust_id and a.gender=r.ref_code and r.ref_rec_no='211' and b.acc_no='" + acNo + "' and r.ref_code='" + pweekersSection + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            weekersSection = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pweekersSection + "' and ref_rec_no='211'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            weekersSectionDisc = tempVector.get(0).toString();
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_customer_master_detail a,cbs_loan_borrower_details b,cbs_ref_rec_type r where a.customerid=b.cust_id and a.gender=r.ref_code and r.ref_rec_no='211' and b.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            weekersSection = tempVector.get(0).toString();
                            weekersSectionDisc = pweekersSection;
                        }
                    }
                    if (!pcategoryOption.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.category_opt and r.ref_rec_no='209' and lb.acc_no='" + acNo + "' and r.ref_code='" + pcategoryOption + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            categoryOption = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pcategoryOption + "' and ref_rec_no='209'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            categoryOptionDesc = tempVector.get(0).toString();
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.category_opt and r.ref_rec_no='209' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            categoryOption = tempVector.get(0).toString();
                            categoryOptionDesc = pcategoryOption;
                        }
                    }
                    //pminCommunity
                    if (!pminCommunity.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.minor_community and r.ref_rec_no='204' and lb.acc_no='" + acNo + "' and r.ref_code='" + pminCommunity + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            minCommunity = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pminCommunity + "' and ref_rec_no='204'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            minCommunityDesc = tempVector.get(0).toString();
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.minor_community and r.ref_rec_no='204' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            minCommunity = tempVector.get(0).toString();
                            minCommunityDesc = pminCommunity;
                        }
                    }
                    if (!prelation.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.relation and r.ref_rec_no='210' and lb.acc_no='" + acNo + "' and r.ref_code='" + prelation + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            relation = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + prelation + "' and ref_rec_no='210'").getResultList();
                            tempVector = (Vector) tempList.get(0);
                            relationDesc = tempVector.get(0).toString();
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.relation and r.ref_rec_no='210' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            relation = tempVector.get(0).toString();
                            relationDesc = prelation;
                        }
                    }
                    LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                    pojo.setAcNo(acNo);
                    pojo.setAcType(accountType);
                    pojo.setAcTypeDesc(common.getAcctDecription(accountType));
                    pojo.setApplicantCategory(appCategory);
                    pojo.setOutstanding(new BigDecimal(balance));
                    pojo.setCategoryOpt(categoryOption);
                    pojo.setCustName(custName);
                    pojo.setDisbAmount(new BigDecimal(disbAmount));
                    pojo.setDuration(loanPeriod);
                    pojo.setMinorCommunity(minCommunity);
                    pojo.setOverdueAmt(new BigDecimal(overdueAmt));
                    pojo.setRoi(roi);
                    pojo.setRelation(relation);
                    pojo.setSanctionAmt(new BigDecimal(sensiodLimit));
                    pojo.setSchemeCode(schemeCode);
                    pojo.setSector(sectorp);
                    pojo.setSecured(secure);
                    pojo.setSubSector(subSectorp);
//                    pojo.setWeakerSect(weekersSection);
                    pojo.setInputvalues("Sector:" + sectorDescr + ",Sub Sector:" + subSectorpDisc + ",Scheme Code:" + schemeCodeDisc + ", Security Type: " + secureDesic + ", Applicant Category: " + appCategoryDisc + "  Customer Type:" + weekersSectionDisc + " , Customer Category: " + categoryOptionDesc == null ? "ALL" : categoryOptionDesc + " Community:" + minCommunityDesc + ", Relation:" + relationDesc + ",Account Type:" + accountType + "");
                    finalResult.add(pojo);

                }//end of the main loop

            } else {

                if (!psector.equalsIgnoreCase("ALL")) {
                    if (brncode.equalsIgnoreCase("0A")) {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r, accountmaster am "
                        //      + "where r.ref_code=lb.sector and lb.acc_no=am.acno and  r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.accttype= '" + accountType + "' and am.openingdt <= '" + frmDt + "' and accstatus<>9  and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc	").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r, accountmaster am "
                                + "where r.ref_code=lb.sector and lb.acc_no=am.acno and  r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.accttype= '" + accountType + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')  and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc").getResultList();
                    } else {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r, accountmaster am "
                        //      + "where r.ref_code=lb.sector and lb.acc_no=am.acno and  r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.accttype= '" + accountType + "'   and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and accstatus<>9  and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc	").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r, accountmaster am "
                                + "where r.ref_code=lb.sector and lb.acc_no=am.acno and  r.ref_rec_no='182' and r.ref_code='" + psector + "' and am.accttype= '" + accountType + "'   and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "')  and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc	").getResultList();
                        if (sectorList.isEmpty()) {
                            throw new ApplicationException("Data does not exit!");
                        }
                    }
                } else {
                    if (brncode.equalsIgnoreCase("0A")) {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r, accountmaster am "
                        //        + "where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.accttype= '" + accountType + "' and am.openingdt <= '" + frmDt + "'  and accstatus<>9 and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r, accountmaster am "
                                + "where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.accttype= '" + accountType + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "') and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc ").getResultList();
                    } else {
                        //sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from CBS_LOAN_BORROWER_DETAILS lb,cbs_ref_rec_type r, accountmaster am "
                        //       + "where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.accttype= '" + accountType + "'  and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "'  and accstatus<>9 and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc ").getResultList();
                        sectorList = em.createNativeQuery("select lb.acc_no,r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r, accountmaster am "
                                + "where r.ref_code=lb.sector and lb.acc_no=am.acno and r.ref_rec_no='182' and am.accttype= '" + accountType + "'  and am.curbrcode='" + brncode + "' and am.openingdt <= '" + frmDt + "' and (ifnull(closingdate,'') = ''or closingdate = '' or closingdate > '" + frmDt + "') and substring(lb.acc_no,3,2)<>'" + CbsConstant.CURRENT_AC + "' group by r.ref_desc,lb.acc_no order by r.ref_desc desc ").getResultList();
                    }
                }
                for (int i = 0; i < sectorList.size(); i++) {
                    List tempList = null;
                    acNo = null;
                    sectorVect = (Vector) sectorList.get(i);
                    acNo = sectorVect.get(0).toString();
                    sectorp = sectorVect.get(1).toString();
                    if (psector.equalsIgnoreCase("ALL")) {
                        sectorDescr = "ALL";
                    } else {
                        tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + psector + "' and ref_rec_no='182'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            sectorDescr = tempVector.get(0).toString();
                        }
                    }
                    tempList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + acNo.substring(2, 4) + "' ").getResultList();
                    if (!tempList.isEmpty()) {
                        tempVector = (Vector) tempList.get(0);
                        acctNature = tempVector.get(0).toString();
                    }

                    if (brncode.equalsIgnoreCase("0A")) {
                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,"
                                    + "ad.sanctionlimit,ag.loan_pd_month from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4) + "' and ag.acno=rb.acno and ag.acno = ad.acno   "
                                    + " and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    } else {
                        if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,"
                                    + "ad.sanctionlimit,ag.loan_pd_month from cbs_loan_acc_mast_sec ag,ca_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4) + "' and ag.acno=rb.acno and ag.acno = ad.acno   "
                                    + "and aa.curbrcode='" + brncode + "' and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    }

                    if (brncode.equalsIgnoreCase("0A")) {
                        if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,"
                                    + "ad.sanctionlimit,ag.loan_pd_month from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4) + "' and ag.acno=rb.acno and ag.acno = ad.acno   "
                                    + " and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    } else {
                        if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            tempList = em.createNativeQuery("select ifnull(sum(ifnull(rb.cramt,0)),0)-ifnull(sum(ifnull(rb.dramt,0)),0),aa.custname,"
                                    + "ad.sanctionlimit,ag.loan_pd_month from cbs_loan_acc_mast_sec ag,loan_recon rb,loan_appparameter ad, accountmaster aa  "
                                    + "where aa.acno=ad.acno and aa.accttype='" + acNo.substring(2, 4) + "' and ag.acno=rb.acno and ag.acno = ad.acno   "
                                    + "and aa.curbrcode='" + brncode + "' and ag.acno='" + acNo + "' and rb.dt <='" + frmDt
                                    + "' group by ag.acno,aa.custname,sanctionlimit, intdeposit,ag.loan_pd_month ").getResultList();
                        }
                    }
                    if (!tempList.isEmpty()) {
                        for (int j = 0; j < tempList.size(); j++) {
                            tempVector = (Vector) tempList.get(j);
                            overdueAmt = 0;
                            balance = Double.parseDouble(tempVector.get(0).toString());
                            custName = tempVector.get(1).toString();
                            sensiodLimit = Double.parseDouble(tempVector.get(2).toString());
                            roi = new BigDecimal(loanInterestCalculationBean.getRoiLoanAccount(i, ymdFormat.format(date), acNo));
                            accountType = acNo.substring(2, 4);
                            loanPeriod = (Integer) tempVector.get(3);
                            List acNoList1 = em.createNativeQuery("select distinct (acno) from emidetails where status = 'unpaid' and acno ='" + acNo + "'").getResultList();
                            if (acNoList1.size() == 0) {
                                List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("A/Cs Whose Plan Has Finished", acNo, 1, 200, frmDt, common.getBrncodeByAcno(acNo), "", 0, isExceessEmi, null, 0);
                                for (OverdueEmiReportPojo pojo : overdueEmiList) {
                                    BigDecimal ovrdueAmt = pojo.getAmountOverdue();
                                    overdueAmt = ovrdueAmt.doubleValue();
                                }
                            } else {
                                List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", acNo, 1, 200, frmDt, common.getBrncodeByAcno(acNo), "", 0, isExceessEmi, null, 0);
                                for (OverdueEmiReportPojo pojo : overdueEmiList) {
                                    BigDecimal ovrdueAmt = pojo.getAmountOverdue();
                                    overdueAmt = ovrdueAmt.doubleValue();
                                }
                            }
                            // overdueAmt = balance - sensiodLimit < 0 ? 0 : balance - sensiodLimit;
                            tempList = em.createNativeQuery("select ifnull(sum(amtdisbursed),0) from loandisbursement where acno='" + acNo + "'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                disbAmount = Double.parseDouble(tempVector.get(0).toString());
                            }
                        }
                    } else {
                        continue;
                    }
                    if (!psubSector.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery(" select ref_desc from cbs_ref_rec_type where ref_code='" + psubSector + "' and ref_rec_no='183'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorpDisc = tempVector.get(0).toString();
                        }
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.sub_sector and r.ref_rec_no='183' and lb.acc_no='" + acNo + "' and r.ref_code='" + psubSector + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorp = tempVector.get(0).toString();
                        } else {
                            continue;
                        }

                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.sub_sector and r.ref_rec_no='183' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            subSectorp = tempVector.get(0).toString();
                            subSectorpDisc = psubSector;
                        }
                    }
                    if (!pschemeCode.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.scheme_code and r.ref_rec_no='203' and lb.acc_no='" + acNo + "' and r.ref_code= '" + pschemeCode + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            schemeCode = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pschemeCode + "' and ref_rec_no='203'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                schemeCodeDisc = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.scheme_code and r.ref_rec_no='203' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            schemeCode = tempVector.get(0).toString();
                            schemeCodeDisc = pschemeCode;
                        }
                    }
                    if (!psecure.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.secured and r.ref_rec_no='187' and lb.acc_no='" + acNo + "' and r.ref_code='" + psecure + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            secure = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + psecure + "' and ref_rec_no='187'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                secureDesic = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.secured and r.ref_rec_no='187' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            secure = tempVector.get(0).toString();
                            secureDesic = psecure;
                        }
                    }
                    if (!pappCategory.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.applicant_category and r.ref_rec_no='208' and lb.acc_no='" + acNo + "' and r.ref_code='" + pappCategory + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            appCategory = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pappCategory + "' and ref_rec_no='208'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                appCategoryDisc = tempVector.get(0).toString();
                            }
                        }

                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.applicant_category and r.ref_rec_no='208' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            appCategory = tempVector.get(0).toString();
                            appCategoryDisc = pappCategory;
                        }
                    }
                    if (!pweekersSection.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_customer_master_detail a,cbs_loan_borrower_details b,cbs_ref_rec_type r where a.customerid=b.cust_id and a.gender=r.ref_code and r.ref_rec_no='211' and b.acc_no='" + acNo + "' and r.ref_code='" + pweekersSection + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            weekersSection = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pweekersSection + "' and ref_rec_no='211'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                weekersSectionDisc = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_customer_master_detail a,cbs_loan_borrower_details b,cbs_ref_rec_type r where a.customerid=b.cust_id and a.gender=r.ref_code and r.ref_rec_no='211' and b.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            weekersSection = tempVector.get(0).toString();
                            weekersSectionDisc = pweekersSection;
                        }
                    }
                    if (!pcategoryOption.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.category_opt and r.ref_rec_no='209' and lb.acc_no='" + acNo + "' and r.ref_code='" + pcategoryOption + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            categoryOption = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pcategoryOption + "' and ref_rec_no='209'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                categoryOptionDesc = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.category_opt and r.ref_rec_no='209' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            categoryOption = tempVector.get(0).toString();
                            categoryOptionDesc = pcategoryOption;
                        }
                    }
                    if (!pminCommunity.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.minor_community and r.ref_rec_no='204' and lb.acc_no='" + acNo + "' and r.ref_code='" + pminCommunity + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            minCommunity = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + pminCommunity + "' and ref_rec_no='209'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                minCommunityDesc = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.minor_community and r.ref_rec_no='204' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            minCommunity = tempVector.get(0).toString();
                            minCommunityDesc = pminCommunity;
                        }
                    }
                    if (!prelation.equalsIgnoreCase("ALL")) {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.relation and r.ref_rec_no='210' and lb.acc_no='" + acNo + "' and r.ref_code='" + prelation + "'").getResultList();
                        if (tempList.isEmpty()) {
                            continue;
                        } else {
                            tempVector = (Vector) tempList.get(0);
                            relation = tempVector.get(0).toString();
                            tempList = em.createNativeQuery("select ref_desc from cbs_ref_rec_type where ref_code='" + prelation + "' and ref_rec_no='210'").getResultList();
                            if (!tempList.isEmpty()) {
                                tempVector = (Vector) tempList.get(0);
                                relationDesc = tempVector.get(0).toString();
                            }
                        }
                    } else {
                        tempList = em.createNativeQuery("select r.ref_desc from cbs_loan_borrower_details lb,cbs_ref_rec_type r where r.ref_code=lb.relation and r.ref_rec_no='210' and lb.acc_no='" + acNo + "'").getResultList();
                        if (!tempList.isEmpty()) {
                            tempVector = (Vector) tempList.get(0);
                            relation = tempVector.get(0).toString();
                            relationDesc = prelation;
                        }
                    }
                    LoanMisCellaniousPojo pojo = new LoanMisCellaniousPojo();
                    pojo.setAcNo(acNo);
                    pojo.setAcType(accountType);
                    pojo.setAcType(common.getAcctDecription(accountType));
                    pojo.setApplicantCategory(appCategory);
                    pojo.setOutstanding(new BigDecimal(balance));
                    pojo.setCategoryOpt(categoryOption);
                    pojo.setCustName(custName);
                    pojo.setDisbAmount(new BigDecimal(disbAmount));
                    pojo.setDuration(loanPeriod);
                    pojo.setMinorCommunity(minCommunity);
                    pojo.setOverdueAmt(new BigDecimal(overdueAmt));
                    pojo.setRoi(roi);
                    pojo.setRelation(relation);
                    pojo.setSanctionAmt(new BigDecimal(sensiodLimit));
                    pojo.setSchemeCode(schemeCode);
                    pojo.setSector(sectorp);
                    pojo.setSecured(secure);
                    pojo.setSubSector(subSectorp);
                    //pojo.setWeakerSect(weekersSection);
                    pojo.setInputvalues("Sector:" + sectorDescr + ",Sub Sector:" + subSectorpDisc + ",Scheme Code:" + schemeCodeDisc + ", Security Type: " + secureDesic + ", Applicant Category: " + appCategoryDisc + "  Customer Type:" + weekersSectionDisc + " , Customer Category: " + categoryOptionDesc == null ? "ALL" : categoryOptionDesc + " Community:" + minCommunityDesc + ", Relation:" + relationDesc + ",Account Type:" + accountType + "");
                    finalResult.add(pojo);

                }//end of the main loop

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return finalResult;
    }

    public List<FreshRenewalEnhashmentPojo> getFreshRenewalEnhshmentLetter(String branchCode, String frDt, String toDt, String reportOption, String acNature, String acType) throws ApplicationException {
        List<FreshRenewalEnhashmentPojo> dataList = new ArrayList<FreshRenewalEnhashmentPojo>();
        try {
            double rateOfInt = 0, outSt = 0;
            String address = "", custFullName = "", mailAddressLine1 = "", mailAddressLine2 = "", mailDistrict = "", mailPostalCode = "", mailVillage = "", mailStateCode = "", mailblock = "";
            String jtName1 = "", jtName2 = "", jtName3 = "", jtName4 = "", nomineeName = "", crAdd = "", roi = "", prAdd = "", openingDt = "", acctDesc = "", TXNID = "", PrevTXNID, jointAddress = "";
            List result = new ArrayList();
            String acctType = "";

            if (reportOption.equalsIgnoreCase("Ex")) {
                if (acType.equalsIgnoreCase("All")) {
                    acctType = "and accttype in(select acctcode from accounttypemaster where crdbflag in('D','B') and acctnature='" + acNature + "')";
                } else {
                    acctType = "and accttype = '" + acType + "' ";
                }
            }

            if (branchCode.equalsIgnoreCase("0A")) {
                if (reportOption.equalsIgnoreCase("Ex")) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.Sanctionlimit from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c "
                            + "where  a.acno = b.acno and b.acno = c.ACC_NO  " + acctType + " and (ifnull(closingDate,'') ='' or closingDate > '" + toDt + "') order by a.acno").getResultList();
                } else if (reportOption.equalsIgnoreCase("F")) {
                    result = em.createNativeQuery("select a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),Sanctionlimit from accountmaster a,loan_appparameter b "
                            + "where a.acno = b.acno and a.acno not in(select distinct acno from loan_oldinterest where substring(acno,3,2) = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                            + "and a.accttype = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and OpeningDt between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),b.Sanctionlimit from accountmaster a,loan_appparameter b,loan_oldinterest c "
                            + "where a.acno = b.acno and a.acno = c.acno "
                            + "and a.accttype = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and enterdate between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }
            } else {
                if (reportOption.equalsIgnoreCase("Ex")) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.Sanctionlimit from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c "
                            + "where curbrcode = '" + branchCode + "' and a.acno = b.acno and b.acno = c.ACC_NO  " + acctType + " and (ifnull(closingDate,'') ='' or closingDate > '" + toDt + "') order by a.acno").getResultList();
                } else if (reportOption.equalsIgnoreCase("F")) {
                    result = em.createNativeQuery("select a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),Sanctionlimit from accountmaster a,loan_appparameter b "
                            + "where a.acno = b.acno and a.acno not in(select distinct acno from loan_oldinterest where substring(acno,1,2) = '" + branchCode + "' and substring(acno,3,2) = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                            + "and a.curbrcode = '" + branchCode + "' and a.accttype = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and OpeningDt between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),b.Sanctionlimit from accountmaster a,loan_appparameter b,loan_oldinterest c "
                            + "where a.acno = b.acno and a.acno = c.acno and a.curbrcode = '" + branchCode + "' "
                            + "and a.accttype = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and enterdate between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {

                    Vector vtr = (Vector) result.get(i);
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();

                    double odLimit = 0, sanctionsLimit = 0, minLimit = 0, odLimit1 = 0, prevodLimit1 = 0;
                    String effDt = "", expiryDt = "", odSanctionChangeDt = "", prevodSanctionChangeDt = "";

                    if (reportOption.equalsIgnoreCase("Ex")) {
                        sanctionsLimit = Double.parseDouble(vtr.get(2).toString());
                    } else {
                        odLimit = Double.parseDouble(vtr.get(2).toString());
                        effDt = vtr.get(3).toString();
                        expiryDt = vtr.get(4).toString();
                        sanctionsLimit = Double.parseDouble(vtr.get(5).toString());
                    }

                    double maxLimit = 0d, enhancedLimit = 0d;
                    if (reportOption.equalsIgnoreCase("R") || reportOption.equalsIgnoreCase("E")) {
                        List list = em.createNativeQuery("select date_format(b.effDt,'%d/%m/%Y'),date_format(b.expiryDt,'%d/%m/%Y'),c.MaxLimit from ("
                                + "(select max(mo.acno) accountNo, max(mo.modi) as modifi from ("
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + "union all "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + " )mo)a, "
                                + "(select ve.acNo,ve.effDt,ve.expiryDt,ve.version,ve.updatedDate from ("
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + "union all "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details_history where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + ")ve)b,(select MaxLimit from loan_oldinterest where acno ='" + acNo + "' and TXNID =(select max(TXNID) from loan_oldinterest where acno ='" + acNo + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                                + ")c "
                                + ") where a.accountNo = b.acno and a.modifi= b.version").getResultList();
                        if (!list.isEmpty()) {
                            List sanctionLimitDtList = em.createNativeQuery("select TXNID,SanctionLimitDt,acLimit from loan_oldinterest where acno =  '" + acNo + "' and txnid = (select max(txnid) from loan_oldinterest where acno =  '" + acNo + "' and txnid<txnid)").getResultList();
                            if (!sanctionLimitDtList.isEmpty()) {
                                Vector vist = (Vector) sanctionLimitDtList.get(0);
                                TXNID = vist.get(0).toString();
                                odSanctionChangeDt = vist.get(1).toString();
                                odLimit1 = Double.parseDouble(vist.get(2).toString());
                            } else {
                                odLimit1 = odLimit;
                                sanctionLimitDtList = em.createNativeQuery("select TXNID,SanctionLimitDt,acLimit from loan_oldinterest where acno =  '" + acNo + "' and txnid = (select max(TXNID) from loan_oldinterest where acno =  '" + acNo + "' )").getResultList();
                                if (!sanctionLimitDtList.isEmpty()) {
                                    Vector vist = (Vector) sanctionLimitDtList.get(0);
                                    TXNID = vist.get(0).toString();
                                    odSanctionChangeDt = vist.get(1).toString();
                                    odLimit1 = Double.parseDouble(vist.get(2).toString());
                                }
                                List sanctionPrevList = em.createNativeQuery("select TXNID,SanctionLimitDt,acLimit from loan_oldinterest where acno =  '" + acNo + "'and txnid =(select max(TXNID) from loan_oldinterest where acno = '" + acNo + "' and txnid<(select max(TXNID) from loan_oldinterest where acno='" + acNo + "' ))").getResultList();
                                if (!sanctionPrevList.isEmpty()) {
                                    Vector vi = (Vector) sanctionPrevList.get(0);
                                    PrevTXNID = vi.get(0).toString();
                                    prevodSanctionChangeDt = vi.get(1).toString();
                                    prevodLimit1 = Double.parseDouble(vi.get(2).toString());
                                }
                            }
                            Vector limitVector = (Vector) list.get(0);
                            effDt = limitVector.get(0).toString();
                            expiryDt = limitVector.get(1).toString();
                            maxLimit = Double.parseDouble(limitVector.get(2).toString());
                            enhancedLimit = odLimit1 - maxLimit;
                            minLimit = sanctionsLimit - odLimit;
                        }
                        outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acNo, frDt));
                        roi = loanInterestCalculationBean.getRoiLoanAccount(outSt, ymdFormat.format(ymdFormat.parse(frDt)), acNo);
                        rateOfInt = Double.parseDouble(roi);
//                        if (maxLimit > odLimit) {
//                            enhancedLimit = maxLimit - odLimit;
//                        }
                        List tempList = null;
                        Vector tempVector = null;

                        tempList = em.createNativeQuery("select a.acno,a.custname,b.acctdesc,a.jtname1,a.nomination,c.craddress,c.praddress,a.jtname2,"
                                + "a.jtname3,a.jtname4, a.OpeningDt from accountmaster a,accounttypemaster b ,customermaster c where a.acno='" + acNo
                                + "' and substring(a.acno,3,2)=b.acctcode and substring(a.acno,5,6)=c.custno and c.brncode = substring(a.acno,1,2) "
                                + "and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2) = c.agcode").getResultList();
                        tempVector = (Vector) tempList.get(0);
                        custName = tempVector.get(1).toString();
                        acctDesc = tempVector.get(2).toString();
                        if (tempVector.get(3) != null && !tempVector.get(3).toString().trim().equalsIgnoreCase("")) {
                            jtName1 = tempVector.get(3).toString();
                        }
                        if (tempVector.get(4) != null) {
                            nomineeName = tempVector.get(4).toString();
                        }
                        if (tempVector.get(5) != null) {
                            crAdd = tempVector.get(5).toString();
                        }
                        if (tempVector.get(6) != null) {
                            prAdd = tempVector.get(6).toString();
                        }
                        if (tempVector.get(7) != null && !tempVector.get(7).toString().trim().equalsIgnoreCase("")) {
                            jtName2 = tempVector.get(7).toString();
                        }
                        if (tempVector.get(8) != null && !tempVector.get(8).toString().trim().equalsIgnoreCase("")) {
                            jtName3 = tempVector.get(8).toString();
                        }
                        if (tempVector.get(9) != null && !tempVector.get(9).toString().trim().equalsIgnoreCase("")) {
                            jtName4 = tempVector.get(9).toString();
                        }
                        if (tempVector.get(10) != null && !tempVector.get(10).toString().trim().equalsIgnoreCase("")) {
                            openingDt = dmyFormat.format(ymdFormat.parse(tempVector.get(10).toString()));
                        }
                        List AddressList = new ArrayList<>();
                        if (jtName1 != null && !jtName1.equalsIgnoreCase("")) {
                            AddressList.add(jtName1);
                        }
                        if (jtName2 != null && !jtName2.equalsIgnoreCase("")) {
                            AddressList.add(jtName2);
                        }
                        if (jtName3 != null && !jtName3.equalsIgnoreCase("")) {
                            AddressList.add(jtName3);
                        }
                        if (jtName4 != null && !jtName4.equalsIgnoreCase("")) {
                            AddressList.add(jtName4);
                        }
                        for (int m = 0; m <= AddressList.size(); m++) {
                            String jAddress = AddressList.get(0).toString();
                            jointAddress = jAddress + "\n" + nomineeName + "\n" + crAdd;
                        }

                    } else {
                        List list = em.createNativeQuery("select date_format(b.effDt,'%d/%m/%Y'),date_format(b.expiryDt,'%d/%m/%Y') from ( "
                                + "(select max(mo.acno) accountNo, max(mo.modi) as modifi from ( "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + "union all "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + " )mo)a, "
                                + "(select ve.acNo,ve.effDt,ve.expiryDt,ve.version,ve.updatedDate from ( "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + "union all "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details_history where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + ")ve)b "
                                + ") where a.accountNo = b.acno and a.modifi= b.version").getResultList();
                        if (!list.isEmpty()) {
                            Vector dtVector = (Vector) list.get(0);
                            effDt = dtVector.get(0).toString();
                            expiryDt = dtVector.get(1).toString();
                        }
                    }

                    List l = em.createNativeQuery("select  trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),trim(MailAddressLine2)) as custAdd,"
                            + "trim(ccmd.MailDistrict) as dist, trim(ccmd.MailPostalCode), trim(ccmd.MailVillage),trim(ccmd.MailStateCode),"
                            + "trim(ccmd.mailblock) from cbs_customer_master_detail ccmd where ccmd.customerid = (select custId from customerid where acno ='" + acNo + "')").getResultList();
                    if (!l.isEmpty()) {
                        Vector vector1 = (Vector) l.get(0);
                        custFullName = vector1.get(0).toString();
                        mailAddressLine1 = vector1.get(1).toString();
                        mailAddressLine2 = vector1.get(2).toString();
                        mailDistrict = vector1.get(3).toString();
                        mailPostalCode = vector1.get(4).toString();
                        mailVillage = vector1.get(5).toString();
                        mailStateCode = vector1.get(6).toString();
                    }
                    if (reportOption.equalsIgnoreCase("F")) {
                        FreshRenewalEnhashmentPojo pojo = new FreshRenewalEnhashmentPojo();
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setMinLimit(new BigDecimal(minLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(BigDecimal.ZERO);
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit));
                        pojo.setEnhancedLimit(BigDecimal.ZERO);
                        pojo.setLablePrint("Y");
                        pojo.setCustFullName(custFullName);
                        pojo.setMailAddressLine1(mailAddressLine1);
                        pojo.setMailAddressLine2(mailAddressLine2);
                        pojo.setMailDistrict(mailDistrict);
                        pojo.setMailPostalCode(mailPostalCode);
                        pojo.setMailStateCode(mailStateCode);
                        pojo.setMailVillage(mailVillage);
                        pojo.setJointAddress(jointAddress);
                        pojo.setRateOfInt(rateOfInt);
//                        pojo.setMailblock(mailblock);

                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("R") && enhancedLimit == 0) {
                        FreshRenewalEnhashmentPojo pojo = new FreshRenewalEnhashmentPojo();
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setMinLimit(new BigDecimal(minLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(new BigDecimal(maxLimit));
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit1));
                        pojo.setEnhancedLimit(BigDecimal.ZERO);
                        pojo.setLablePrint("Y");
                        pojo.setCustFullName(custFullName);
                        pojo.setMailAddressLine1(mailAddressLine1);
                        pojo.setMailAddressLine2(mailAddressLine2);
                        pojo.setMailDistrict(mailDistrict);
                        pojo.setMailPostalCode(mailPostalCode);
                        pojo.setMailStateCode(mailStateCode);
                        pojo.setMailVillage(mailVillage);
                        pojo.setJointAddress(jointAddress);
                        pojo.setRateOfInt(rateOfInt);
                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("E") && enhancedLimit != 0) {
                        FreshRenewalEnhashmentPojo pojo = new FreshRenewalEnhashmentPojo();
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setMinLimit(new BigDecimal(minLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(new BigDecimal(maxLimit));
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit1));
                        pojo.setEnhancedLimit(new BigDecimal(enhancedLimit));
                        pojo.setLablePrint("Y");
                        pojo.setCustFullName(custFullName);
                        pojo.setMailAddressLine1(mailAddressLine1);
                        pojo.setMailAddressLine2(mailAddressLine2);
                        pojo.setMailDistrict(mailDistrict);
                        pojo.setMailPostalCode(mailPostalCode);
                        pojo.setMailStateCode(mailStateCode);
                        pojo.setMailVillage(mailVillage);
                        pojo.setPrevodLimit1(new BigDecimal(prevodLimit1));
                        pojo.setRateOfInt(rateOfInt);
                        pojo.setJointAddress(jointAddress);
                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("Ex")) {
                        if (dmyFormat.parse(expiryDt).getTime() >= ymdFormat.parse(frDt).getTime()
                                && dmyFormat.parse(expiryDt).getTime() <= ymdFormat.parse(toDt).getTime()) {
                            FreshRenewalEnhashmentPojo pojo = new FreshRenewalEnhashmentPojo();
                            pojo.setAcNo(acNo);
                            pojo.setCustName(custName);
                            pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                            pojo.setEffDate(effDt);
                            pojo.setExpiryDate(expiryDt);
                            pojo.setPreviousValue(new BigDecimal(Math.abs(common.getBalanceOnDate(acNo, toDt))));
                            pojo.setLablePrint("N");
                            pojo.setCustFullName(custFullName);
                            pojo.setMailAddressLine1(mailAddressLine1);
                            pojo.setMailAddressLine2(mailAddressLine2);
                            pojo.setMailDistrict(mailDistrict);
                            pojo.setMailPostalCode(mailPostalCode);
                            pojo.setMailStateCode(mailStateCode);
                            pojo.setMailVillage(mailVillage);
                            pojo.setJointAddress(jointAddress);
                            pojo.setRateOfInt(rateOfInt);
                            dataList.add(pojo);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<FreshRenewalEnhashmentPojo> getFreshRenewalEnhshmentData(String branchCode, String frDt, String toDt, String reportOption, String acNature, String acType) throws ApplicationException {
        List<FreshRenewalEnhashmentPojo> dataList = new ArrayList<FreshRenewalEnhashmentPojo>();
        try {
            List result = new ArrayList();
            String acctType = "";

            if (reportOption.equalsIgnoreCase("Ex")) {
                if (acType.equalsIgnoreCase("All")) {
                    acctType = "and a.accttype in(select acctcode from accounttypemaster where crdbflag in('D','B') and acctnature='" + acNature + "')";
                } else {
                    acctType = "and a.accttype = '" + acType + "' ";
                }
            } else {
                acctType = "and a.accttype in(select acctcode from accounttypemaster where crdbflag in('D','B') and acctnature='" + CbsConstant.CURRENT_AC + "')";
            }

            if (branchCode.equalsIgnoreCase("0A")) {
                if (reportOption.equalsIgnoreCase("Ex")) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.Sanctionlimit from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c "
                            + "where  a.acno = b.acno and b.acno = c.ACC_NO  " + acctType + " and (ifnull(closingDate,'') ='' or closingDate > '" + toDt + "') order by a.acno").getResultList();
                } else if (reportOption.equalsIgnoreCase("F")) {
                    result = em.createNativeQuery("select a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),Sanctionlimit from accountmaster a,loan_appparameter b "
                            + "where a.acno = b.acno and a.acno not in(select distinct acno from loan_oldinterest where substring(acno,3,2) = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                            + "" + acctType + " and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and OpeningDt between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),b.Sanctionlimit from accountmaster a,loan_appparameter b,loan_oldinterest c "
                            + "where a.acno = b.acno and a.acno = c.acno "
                            + "" + acctType + " and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and enterdate between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }
            } else {
                if (reportOption.equalsIgnoreCase("Ex")) {
                    result = em.createNativeQuery("select a.acno,a.custname,b.Sanctionlimit from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c "
                            + "where curbrcode = '" + branchCode + "' and a.acno = b.acno and b.acno = c.ACC_NO  " + acctType + " and (ifnull(closingDate,'') ='' or closingDate > '" + toDt + "') order by a.acno").getResultList();
                } else if (reportOption.equalsIgnoreCase("F")) {
                    result = em.createNativeQuery("select a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),Sanctionlimit from accountmaster a,loan_appparameter b "
                            + "where a.acno = b.acno and a.acno not in(select distinct acno from loan_oldinterest where substring(acno,1,2) = '" + branchCode + "' and substring(acno,3,2) = '" + CbsAcCodeConstant.OVER_DRAFT.getAcctCode() + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                            + "and a.curbrcode = '" + branchCode + "' " + acctType + " and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and OpeningDt between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct a.acno,a.custname,a.ODLimit,ifnull(date_format(b.AdhocApplicableDt,'%d/%m/%Y'),''),ifnull(date_format(b.AdhocExpiry,'%d/%m/%Y'),''),b.Sanctionlimit from accountmaster a,loan_appparameter b,loan_oldinterest c "
                            + "where a.acno = b.acno and a.acno = c.acno and a.curbrcode = '" + branchCode + "' "
                            + "" + acctType + " and (ifnull(a.closingDate,'') = '' or a.closingDate > '" + toDt + "') and enterdate between '" + frDt + "' and '" + toDt + "' order by a.acno").getResultList();
                }
            }
            double odLimit = 0, sanctionsLimit = 0;
            String effDt = "", expiryDt = "";
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    FreshRenewalEnhashmentPojo pojo = new FreshRenewalEnhashmentPojo();
                    Vector vtr = (Vector) result.get(i);
                    String acNo = vtr.get(0).toString();
                    String custName = vtr.get(1).toString();
                    if (reportOption.equalsIgnoreCase("Ex")) {
                        sanctionsLimit = Double.parseDouble(vtr.get(2).toString());
                    } else {
                        odLimit = Double.parseDouble(vtr.get(2).toString());
                        effDt = vtr.get(3).toString();
                        expiryDt = vtr.get(4).toString();
                        sanctionsLimit = Double.parseDouble(vtr.get(5).toString());
                    }

                    double maxLimit = 0d, enhancedLimit = 0d;
                    if (reportOption.equalsIgnoreCase("R") || reportOption.equalsIgnoreCase("E")) {
                        List list = em.createNativeQuery("select date_format(b.effDt,'%d/%m/%Y'),date_format(b.expiryDt,'%d/%m/%Y'),c.MaxLimit from ("
                                + "(select max(mo.acno) accountNo, max(mo.modi) as modifi from ("
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + "union all "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + " )mo)a, "
                                + "(select ve.acNo,ve.effDt,ve.expiryDt,ve.version,ve.updatedDate from ("
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + "union all "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details_history where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + ")ve)b,(select MaxLimit from loan_oldinterest where acno ='" + acNo + "' and TXNID =(select max(TXNID) from loan_oldinterest where acno ='" + acNo + "' and enterdate between '" + frDt + "' and '" + toDt + "') "
                                + ")c "
                                + ") where a.accountNo = b.acno and a.modifi= b.version").getResultList();


                        Vector limitVector = (Vector) list.get(0);
                        effDt = limitVector.get(0).toString();
                        expiryDt = limitVector.get(1).toString();
                        maxLimit = Double.parseDouble(limitVector.get(2).toString());
                        enhancedLimit = odLimit - maxLimit;
//                        if (maxLimit > odLimit) {
//                            enhancedLimit = maxLimit - odLimit;
//                        }
                    } else {
                        List list = em.createNativeQuery("select date_format(b.effDt,'%d/%m/%Y'),date_format(b.expiryDt,'%d/%m/%Y') from ( "
                                + "(select max(mo.acno) accountNo, max(mo.modi) as modifi from ( "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details_history g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + "union all "
                                + "select g.ACC_NO as acno, max(g.TOTAL_MODIFICATIONS) as modi from cbs_loan_borrower_details g where date_format(ifnull(g.LAST_UPDATED_DATE,g.CREATION_DATE),'%Y%m%d')  <='" + toDt + "' and g.ACC_NO = '" + acNo + "' "
                                + " )mo)a, "
                                + "(select ve.acNo,ve.effDt,ve.expiryDt,ve.version,ve.updatedDate from ( "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + "union all "
                                + "select ACC_NO acNo,RENEWAL_DATE effDt,DOCUMENT_EXP_DATE expiryDt,TOTAL_MODIFICATIONS version,date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d') updatedDate from cbs_loan_borrower_details_history where ACC_NO = '" + acNo + "' and date_format(ifnull(LAST_UPDATED_DATE,CREATION_DATE),'%Y%m%d')  <='" + toDt + "' "
                                + ")ve)b "
                                + ") where a.accountNo = b.acno and a.modifi= b.version").getResultList();
                        if (!list.isEmpty()) {
                            Vector dtVector = (Vector) list.get(0);
                            effDt = dtVector.get(0).toString();
                            expiryDt = dtVector.get(1).toString();
                        }
                    }

                    if (reportOption.equalsIgnoreCase("F")) {
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(BigDecimal.ZERO);
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit));
                        pojo.setEnhancedLimit(BigDecimal.ZERO);
                        pojo.setLablePrint("Y");
                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("R") && enhancedLimit == 0) {
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(new BigDecimal(maxLimit));
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit));
                        pojo.setEnhancedLimit(BigDecimal.ZERO);
                        pojo.setLablePrint("Y");
                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("E") && enhancedLimit != 0) {
                        pojo.setAcNo(acNo);
                        pojo.setCustName(custName);
                        pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                        pojo.setEffDate(effDt);
                        pojo.setExpiryDate(expiryDt);
                        pojo.setPreviousValue(new BigDecimal(maxLimit));
                        pojo.setCurrentValueLimit(new BigDecimal(odLimit));
                        pojo.setEnhancedLimit(new BigDecimal(enhancedLimit));
                        pojo.setLablePrint("Y");
                        dataList.add(pojo);
                    } else if (reportOption.equalsIgnoreCase("Ex")) {
                        if (dmyFormat.parse(expiryDt).getTime() >= ymdFormat.parse(frDt).getTime()
                                && dmyFormat.parse(expiryDt).getTime() <= ymdFormat.parse(toDt).getTime()) {
                            pojo.setAcNo(acNo);
                            pojo.setCustName(custName);
                            pojo.setSanctionLimit(new BigDecimal(sanctionsLimit));
                            pojo.setEffDate(effDt);
                            pojo.setExpiryDate(expiryDt);
                            pojo.setPreviousValue(new BigDecimal(Math.abs(common.getBalanceOnDate(acNo, toDt))));
                            pojo.setLablePrint("N");
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<SuretyLetterPojo> getloanSuretyLetter(String frDt, String toDt, String branchCode) throws ApplicationException {
        List<SuretyLetterPojo> dataList = new ArrayList<SuretyLetterPojo>();
        List arrayList = new ArrayList();
        List list = new ArrayList();
        try {
            if (branchCode.equalsIgnoreCase("0A")) {
                branchCode = "90";
            }
            list = em.createNativeQuery("select distinct  bn.bankname,bn.bankaddress,bm.BranchName,pi.BrPhone,bm.Pincode from bnkadd bn,branchmaster bm, parameterinfo pi where bn.alphacode = bm.alphacode and"
                    + " bm.BrnCode = " + Integer.parseInt(branchCode)
                    + " group by bn.bankname,bn.bankaddress ").getResultList();

            Vector tempVector1 = (Vector) list.get(0);
            String bankname = tempVector1.get(0).toString();
            String bankaddress = tempVector1.get(1).toString();
            String branchName = tempVector1.get(2).toString();
            String brPhone = tempVector1.get(3).toString();
            String brPin = tempVector1.get(4).toString();

            if (branchCode.equalsIgnoreCase("0A")) {
                arrayList = em.createNativeQuery("select gar_custid,concat(ifnull(cc.title,''),' ',ifnull(cc.custname,''),' ',ifnull(cc.middle_name,''),' ',ifnull(cc.last_name,'')) as custname, "
                        + "lg.address,cc.pervillage,cast(la.sanctionlimit as decimal(25,2)),a.acno,date_format(lg.trantime,'%d/%m/%Y'),cc.PerPostalCode "
                        + "from loan_guarantordetails lg,cbs_customer_master_detail cc ,loan_appparameter la, accountmaster a "
                        + "where cc.customerid =lg.gar_custid and a.acno=lg.acno and a.acno = la.acno  "
                        + "and date_format(lg.trantime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
            } else {
                arrayList = em.createNativeQuery("select gar_custid,concat(ifnull(cc.title,''),' ',ifnull(cc.custname,''),' ',ifnull(cc.middle_name,''),' ',ifnull(cc.last_name,'')) as custname, "
                        + "lg.address,cc.pervillage,cast(la.sanctionlimit as decimal(25,2)),a.acno,date_format(lg.trantime,'%d/%m/%Y'),cc.PerPostalCode "
                        + "from loan_guarantordetails lg,cbs_customer_master_detail cc ,loan_appparameter la, accountmaster a "
                        + "where cc.customerid =lg.gar_custid and a.acno=lg.acno and a.acno = la.acno and substring(lg.acno,1,2)='" + branchCode + "' "
                        + "and date_format(lg.trantime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "'").getResultList();
            }
            Spellar word = new Spellar();
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Vector vtr = (Vector) arrayList.get(i);
                    SuretyLetterPojo pojo = new SuretyLetterPojo();
                    String acNo = vtr.get(5).toString();
                    List list1 = em.createNativeQuery("select customerid,concat(ifnull(a.title,''),' ',ifnull(a.custname,''),' ',ifnull(a.middle_name,''),' ',ifnull(a.last_name,'')) as custname  "
                            + "from cbs_customer_master_detail a,customerid b where cast(a.customerid as unsigned) = b.custid and b.acno = '" + acNo + "'").getResultList();
                    Vector acVector = (Vector) list1.get(0);

                    pojo.setBankName(bankname);
                    pojo.setBankAddress(bankaddress);
                    pojo.setBranchName(branchName);
                    pojo.setPhoneNo(brPhone);
                    pojo.setBrPinCode(brPin);

                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setSuretyCustName(acVector.get(1).toString());
                    pojo.setAddress(vtr.get(2).toString());
                    pojo.setCityVillage(vtr.get(3).toString());
                    pojo.setSectionLimit(new BigDecimal(vtr.get(4).toString()));
                    pojo.setInWord(word.spellAmount(Double.parseDouble(vtr.get(4).toString())));
                    pojo.setAcNo(acNo);
                    pojo.setTrantime(vtr.get(6).toString());
                    pojo.setPinCode(vtr.get(7).toString());

                    dataList.add(pojo);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List<StatemenrtOfRecoveriesPojo> getDemandRecoveriesData(String area, String tillDate, String brCode, String repType, String optType, String reportType) throws ApplicationException {
        List<StatemenrtOfRecoveriesPojo> dataList = new ArrayList<StatemenrtOfRecoveriesPojo>();
        try {

            String bnkIndentify = fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            String bnkCode = fts.getBankCode();
            String acctType = postalFacadeRemote.armyInterestPostParameter();
            if (acctType.equalsIgnoreCase("0")) {
                throw new ApplicationException("Please fill Acct Code in cbs_parameterinfo!");
            }
            //List custIdList = em.createNativeQuery("select distinct(CustId) from customerid  where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)order by custid").getResultList();
            List custIdList = em.createNativeQuery("select cast(customerid as unsigned),date_format(DateOfBirth,'%Y%m%d') as dob from cbs_customer_master_detail  where customerid in "
                    + "(select custid from share_holder where area = '" + area + "' and customerid is not null)order by cast(customerid as unsigned)").getResultList();
            int smSno = 0;
            double grandTotalAmt = 0;
            String cuId = "";
            int cnt = 0;
            double totalOutstandbal = 0d, totalPrinAmt = 0d, totalIntAmt = 0d, totalLoanEmi = 0d, totalLipAmt = 0d, totalTheftFund = 0d, totalRdInstallment = 0d, totalTotalAmt = 0d, totalOverDue = 0d;
            double gpOutstandbal = 0d, gpPrinAmt = 0d, gpIntAmt = 0d, gpLoanEmi = 0d, gpLipAmt = 0d, gpTheftFund = 0d, gpRdInstallment = 0d, gpTotalAmt = 0d, gpOverDue = 0d;
            if (!custIdList.isEmpty()) {
                for (int i = 0; i < custIdList.size(); i++) {
                    Vector cvr = (Vector) custIdList.get(i);
                    String custId = cvr.get(0).toString();
                    String dob = cvr.get(1).toString();
                    String srzDob = CbsUtil.yearAdd(dob, 60);
                    if ((bnkCode.equalsIgnoreCase("krbi") && ymdFormat.parse(srzDob).after(new Date())) || bnkCode.equalsIgnoreCase("army")) {

                        smSno = smSno + 1;
                        List result = new ArrayList();
                        if (bnkIndentify.equalsIgnoreCase("RP")) {
                            result = em.createNativeQuery("select acno,custname from accountmaster  where acno in(select acno from customerid where "
                                    + "custid = '" + custId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
                                    + "and curbrcode = '" + brCode + "' and accttype in(" + acctType + ") ").getResultList();
                        } else {
                            result = em.createNativeQuery("select acno,custname from accountmaster  where acno in(select acno from customerid where "
                                    + "custid = '" + custId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
                                    + "and curbrcode = '" + brCode + "' and accttype in(select acctcode from accounttypemaster where acctnature in "
                                    + "('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.RECURRING_AC + "')and acctcode <> '31')"
                                    + "union "
                                    + "select acno,custname from accountmaster  where acno in(select acno from customerid where custid = '" + custId + "')"
                                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')and curbrcode = '" + brCode + "' "
                                    + "and accttype = '" + CbsAcCodeConstant.CL_AC.getAcctCode() + "'").getResultList();
                        }

                        int sno = 0;
                        String cId = "";
                        if (!result.isEmpty()) {
                            for (int j = 0; j < result.size(); j++) {
                                Vector element = (Vector) result.get(j);
                                StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                                String acno = element.get(0).toString();
                                String custName = element.get(1).toString();
                                double outstandbal = 0.0;
                                sno = sno + 1;
                                String acctCode = acno.substring(2, 4);
                                String acNat = fts.getAcNatureByCode(acctCode);
                                if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate));
                                }
//                            else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
//                                outstandbal = common.getBalanceOnDate(acno, tillDate);
//                            }

                                // For Personal Token Number
//                            List ListPerContNoList = em.createNativeQuery("select ifnull(employeeNo,'') from cbs_customer_master_detail where customerid = '" + custId + "'").getResultList();
//                            Vector ele = (Vector) ListPerContNoList.get(0);
//                            String pTokenNo = ele.get(0).toString();
                                String trade = "", flioNo = "", order = "", pTokenNo = "";
                                // For Trade (Designation) and Flio Number
                                //List tradeList = em.createNativeQuery("select ifnull(buss_desig,''),Regfoliono from share_holder where custid = '" + custId + "'").getResultList();
                                List tradeList = em.createNativeQuery("select ifnull(a.buss_desig,''),a.Regfoliono, ifnull((select order_by from cbs_ref_rec_type where REF_REC_NO = '020'  "
                                        + "and REF_CODE = a.buss_desig ),'') as order_by ,ifnull(employeeNo,'') from share_holder a, cbs_customer_master_detail c where "
                                        + "a.custid = '" + custId + "' and a.custid = c.customerid").getResultList();

                                if (!tradeList.isEmpty()) {
                                    Vector ele1 = (Vector) tradeList.get(0);
                                    trade = ele1.get(0).toString();
                                    flioNo = ele1.get(1).toString();
                                    order = ele1.get(2).toString();
                                    pTokenNo = ele1.get(3).toString();
                                    if (trade.equalsIgnoreCase("null")) {
                                        trade = "";
                                    }
                                }

                                double theftFund = 0d, rdInstallment = 0d;

                                if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                    List rdInstallmentList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                    Vector rdvtr = (Vector) rdInstallmentList.get(0);
                                    rdInstallment = Double.parseDouble(rdvtr.get(0).toString());
                                    if (rdInstallment == 0) {
                                        rdInstallmentList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0),date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                                                + "where acno='" + acno + "' and sno=(select min(SNO) from rd_installment where acno = '" + acno + "' and STATUS = 'Unpaid')").getResultList();
                                    }
                                    rdvtr = (Vector) rdInstallmentList.get(0);
                                    rdInstallment = Double.parseDouble(rdvtr.get(0).toString());
                                    pojo.setInstallmentAmt(rdInstallment);
                                }

                                if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                    List theftCustIdList = em.createNativeQuery("select * from share_holder where custid in(select custid from customerid where acno = '" + acno + "')").getResultList();
                                    if (!theftCustIdList.isEmpty()) {
                                        //List theftFundList = em.createNativeQuery("select code from cbs_parameterinfo where  name= 'tf'").getResultList();
                                        List theftFundList = em.createNativeQuery("select RdInstal as overDueAmt from accountmaster where acno  = '" + acno + "'").getResultList();
                                        Vector vtr = (Vector) theftFundList.get(0);
                                        theftFund = Double.parseDouble(vtr.get(0).toString());
                                    }
                                }

                                //********************* For Interest ********************
                                double prinAmt = 0, intAmt = 0;
                                double overDueAmt = 0d;
                                if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                    List result1;
                                    double outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate));
                                    if (acctCode.equalsIgnoreCase("11") && bnkCode.equalsIgnoreCase("army")) {
                                        result1 = em.createNativeQuery("select ifnull(INSTALLAMT,0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                        if (!result1.isEmpty()) {
                                            Vector ele2 = (Vector) result1.get(0);
                                            prinAmt = Double.parseDouble(ele2.get(0).toString());
                                        }
                                        if (prinAmt == 0) {
                                            String fromDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "f");
                                            String toDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "t");
                                            LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, toDt, acno, brCode);
                                            intAmt = it.getTotalInt();
                                            if (!(intAmt == 0)) {
                                                intAmt = intAmt * -1;
                                            }
                                        }
                                    } else {
                                        String fromDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "f");
                                        String toDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "t");
                                        LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, toDt, acno, brCode);
                                        intAmt = it.getTotalInt();
                                        if (!(intAmt == 0)) {
                                            intAmt = intAmt * -1;
                                        }
                                        result1 = em.createNativeQuery("select ifnull(installamt,0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                        if (result1.isEmpty()) {
                                            result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' /*and duedt<='" + tillDate + "'*/").getResultList();
                                        }
                                        Vector ele2 = (Vector) result1.get(0);
                                        prinAmt = Double.parseDouble(ele2.get(0).toString());
                                        if (prinAmt == 0) {
                                            if (Math.abs(outSt) > 0) {
                                                result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                                        + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                                                        + "where acno='" + acno + "' and status='Unpaid' and "
                                                        + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                                        + "where acno='" + acno + "' and status='Unpaid') ").getResultList();
                                            }
                                            ele2 = (Vector) result1.get(0);
                                            prinAmt = Double.parseDouble(ele2.get(0).toString());
                                            if (prinAmt == 0) {
                                                prinAmt = Math.abs(outSt);
                                            } else {
                                                if (prinAmt > Math.abs(outSt)) {
                                                    prinAmt = Math.abs(outSt);
                                                } else {
                                                    prinAmt = prinAmt;
                                                }
                                            }
                                        } else {
                                            if (prinAmt > Math.abs(outSt)) {
                                                prinAmt = Math.abs(outSt);
                                            } else {
                                                prinAmt = prinAmt;
                                            }
                                        }
                                        //For Over Due Amount new code
                                        if (bnkCode.equalsIgnoreCase("army")) {
                                            if (Math.abs(outSt) > 0) {
                                                List<OverDueListPojo> list = ddsRemote.getOverDueListData(acno.substring(0, 2), acno.substring(2, 4), tillDate, acno);
                                                if (!list.isEmpty()) {
                                                    overDueAmt = list.get(0).getOveDue().doubleValue();
                                                }
                                            }
                                        }
                                        //END For Over Due Amount new code  
                                    }
                                }
                                List result2 = em.createNativeQuery("select ifnull(sum(premium_amount),0) from insurance_details where acno = '" + acno + "' and due_dt <= '" + tillDate + "'").getResultList();
                                Vector ele3 = (Vector) result2.get(0);
                                double lipAmt = Double.parseDouble(ele3.get(0).toString());
                                double totalAmt = prinAmt + intAmt + lipAmt + theftFund + rdInstallment + overDueAmt;
                                double loanEmi = prinAmt + intAmt + theftFund + rdInstallment;

//                            gpOutstandbal = gpOutstandbal + Math.abs(outstandbal);
//                            gpPrinAmt = gpPrinAmt + prinAmt;
//                            gpIntAmt = gpIntAmt + intAmt;
//                            gpLoanEmi = gpLoanEmi + loanEmi;
//                            gpLipAmt = gpLipAmt + lipAmt;
//                            gpTheftFund = gpTheftFund + theftFund;
//                            gpRdInstallment = gpRdInstallment + rdInstallment;
//                            gpTotalAmt = gpTotalAmt + totalAmt;
                                //For Grand total
                                totalOutstandbal = totalOutstandbal + Math.abs(outstandbal);
                                totalPrinAmt = totalPrinAmt + prinAmt;
                                totalIntAmt = totalIntAmt + intAmt;
                                totalLoanEmi = totalLoanEmi + loanEmi;
                                totalLipAmt = totalLipAmt + lipAmt;
                                totalTheftFund = totalTheftFund + theftFund;
                                totalRdInstallment = totalRdInstallment + rdInstallment;
                                totalTotalAmt = totalTotalAmt + totalAmt;
                                totalOverDue = totalOverDue + overDueAmt;

                                grandTotalAmt = grandTotalAmt + totalAmt;
                                if (reportType.equalsIgnoreCase("")) {
                                    if (optType.equalsIgnoreCase("Al")) {
                                        pojo.setCustId(custId);
                                        pojo.setPrinAmt(prinAmt);
                                        pojo.setIntAmt(intAmt);
                                        pojo.setLoanAcNo(acno);
                                        pojo.setCustName(custName);
                                        pojo.setTheftFund(theftFund);
                                        pojo.setTrade(trade);
                                        pojo.setOrder(order);
                                        pojo.setPersonalTokenNo(pTokenNo);
                                        pojo.setFlioNo(flioNo);
                                        pojo.setLipAmt(lipAmt);
                                        pojo.setLoanEmiAmt(loanEmi);
                                        pojo.setOutStdBal(Math.abs(outstandbal));
                                        pojo.setTotalAmt(totalAmt);
                                        // pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                        Spellar word = new Spellar();
                                        pojo.setGrTotalAmt(grandTotalAmt);
                                        pojo.setGrandAmtInword(word.spellAmount(grandTotalAmt).toUpperCase());
                                        if (repType.equalsIgnoreCase("De")) {
                                            pojo.setsNo(sno);
                                        } else {
                                            pojo.setsNo(smSno);
                                        }

                                        // System.out.println(smSno);
                                        pojo.setOverDue(overDueAmt);
                                        dataList.add(pojo);
                                    } else {
                                        totalAmt = prinAmt + intAmt;
                                        // pojo.setCustId(custId);
                                        pojo.setPrinAmt(prinAmt);
                                        pojo.setIntAmt(intAmt);
                                        pojo.setLoanAcNo(acno);
                                        pojo.setCustName(custName);
                                        pojo.setTheftFund(theftFund);
                                        // pojo.setTrade(trade);
                                        // pojo.setPersonalTokenNo(pTokenNo);
                                        pojo.setFlioNo(flioNo);
                                        //pojo.setLipAmt(lipAmt);
                                        pojo.setOutStdBal(Math.abs(outstandbal));
                                        pojo.setTotalAmt(totalAmt);
                                        pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                        pojo.setAcctCode(acctCode);
                                        if (repType.equalsIgnoreCase("De")) {
                                            pojo.setsNo(sno);
                                        } else {
                                            pojo.setsNo(smSno);
                                        }
                                        pojo.setOverDue(overDueAmt);
                                        dataList.add(pojo);
                                    }
                                } else {

                                    if (optType.equalsIgnoreCase("Al")) {

                                        if (cnt != 0 && !(cId.equalsIgnoreCase(custId))) {
                                            StatemenrtOfRecoveriesPojo pojo1 = new StatemenrtOfRecoveriesPojo();
                                            cnt = 0;
                                            cId = custId;
                                            pojo1.setFlioNo("FOLIO NUMBER WISE TOTAL ");
                                            pojo1.setOutStdBal(Math.abs(gpOutstandbal));
                                            pojo1.setPrinAmt(gpPrinAmt);
                                            pojo1.setIntAmt(gpIntAmt);
                                            pojo1.setLoanEmiAmt(gpLoanEmi);
                                            pojo1.setLipAmt(gpLipAmt);
                                            pojo1.setTheftFund(gpTheftFund);
                                            pojo1.setInstallmentAmt(gpRdInstallment);
                                            pojo1.setTotalAmt(gpTotalAmt);
                                            pojo1.setGrTotalAmt(grandTotalAmt);
                                            pojo1.setLoanAcNo("");
                                            pojo1.setCustName("");
                                            pojo1.setTrade("");
                                            pojo1.setOrder("");
                                            pojo1.setPersonalTokenNo("");
                                            dataList.add(pojo1);
                                            gpOutstandbal = 0d;
                                            gpPrinAmt = 0d;
                                            gpIntAmt = 0d;
                                            gpLoanEmi = 0d;
                                            gpLipAmt = 0d;
                                            gpTheftFund = 0d;
                                            gpRdInstallment = 0d;
                                            gpTotalAmt = 0d;
                                            gpOverDue = 0d;
                                        }
                                        if (cnt == 0) {
                                            cnt = cnt + 1;
                                            gpOutstandbal = gpOutstandbal + Math.abs(outstandbal);
                                            gpPrinAmt = gpPrinAmt + prinAmt;
                                            gpIntAmt = gpIntAmt + intAmt;
                                            gpLoanEmi = gpLoanEmi + loanEmi;
                                            gpLipAmt = gpLipAmt + lipAmt;
                                            gpTheftFund = gpTheftFund + theftFund;
                                            gpRdInstallment = gpRdInstallment + rdInstallment;
                                            gpTotalAmt = gpTotalAmt + totalAmt;
                                            gpOverDue = gpOverDue + overDueAmt;
                                            cId = custId;
                                            pojo.setCustId(custId);
                                            pojo.setPrinAmt(prinAmt);
                                            pojo.setIntAmt(intAmt);
                                            pojo.setLoanAcNo(acno);
                                            pojo.setCustName(custName);
                                            pojo.setTheftFund(theftFund);
                                            pojo.setTrade(trade);
                                            pojo.setOrder(order);
                                            pojo.setPersonalTokenNo(pTokenNo);
                                            pojo.setFlioNo(flioNo);
                                            pojo.setLipAmt(lipAmt);
                                            pojo.setLoanEmiAmt(loanEmi);
                                            pojo.setOutStdBal(Math.abs(outstandbal));
                                            pojo.setTotalAmt(totalAmt);
                                            pojo.setOverDue(overDueAmt);
                                            dataList.add(pojo);
                                        } else {
                                            if (cId.equalsIgnoreCase(custId)) {
                                                gpOutstandbal = gpOutstandbal + Math.abs(outstandbal);
                                                gpPrinAmt = gpPrinAmt + prinAmt;
                                                gpIntAmt = gpIntAmt + intAmt;
                                                gpLoanEmi = gpLoanEmi + loanEmi;
                                                gpLipAmt = gpLipAmt + lipAmt;
                                                gpTheftFund = gpTheftFund + theftFund;
                                                gpRdInstallment = gpRdInstallment + rdInstallment;
                                                gpTotalAmt = gpTotalAmt + totalAmt;
                                                gpOverDue = gpOverDue + overDueAmt;
                                                cId = custId;
                                                cuId = custId;
                                                pojo.setCustId(custId);
                                                pojo.setPrinAmt(prinAmt);
                                                pojo.setIntAmt(intAmt);
                                                pojo.setLoanAcNo(acno);
                                                pojo.setCustName(custName);
                                                pojo.setTheftFund(theftFund);
                                                pojo.setTrade(trade);
                                                pojo.setOrder(order);
                                                pojo.setPersonalTokenNo(pTokenNo);
                                                pojo.setFlioNo(flioNo);
                                                pojo.setLipAmt(lipAmt);
                                                pojo.setLoanEmiAmt(loanEmi);
                                                pojo.setOutStdBal(Math.abs(outstandbal));
                                                pojo.setTotalAmt(totalAmt);
                                                // pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                                Spellar word = new Spellar();
                                                pojo.setGrTotalAmt(grandTotalAmt);
                                                pojo.setGrandAmtInword(word.spellAmount(grandTotalAmt).toUpperCase());
                                                if (repType.equalsIgnoreCase("De")) {
                                                    pojo.setsNo(sno);
                                                } else {
                                                    pojo.setsNo(smSno);
                                                }
                                                // System.out.println(smSno);
                                                pojo.setOverDue(overDueAmt);
                                                dataList.add(pojo);

                                            } else {
                                                gpOutstandbal = gpOutstandbal + Math.abs(outstandbal);
                                                gpPrinAmt = gpPrinAmt + prinAmt;
                                                gpIntAmt = gpIntAmt + intAmt;
                                                gpLoanEmi = gpLoanEmi + loanEmi;
                                                gpLipAmt = gpLipAmt + lipAmt;
                                                gpTheftFund = gpTheftFund + theftFund;
                                                gpRdInstallment = gpRdInstallment + rdInstallment;
                                                gpTotalAmt = gpTotalAmt + totalAmt;
                                                gpOverDue = gpOverDue + overDueAmt;
                                                //****************
                                                cuId = custId;
                                                pojo.setCustId(custId);
                                                pojo.setPrinAmt(prinAmt);
                                                pojo.setIntAmt(intAmt);
                                                pojo.setLoanAcNo(acno);
                                                pojo.setCustName(custName);
                                                pojo.setTheftFund(theftFund);
                                                pojo.setTrade(trade);
                                                pojo.setOrder(order);
                                                pojo.setPersonalTokenNo(pTokenNo);
                                                pojo.setFlioNo(flioNo);
                                                pojo.setLipAmt(lipAmt);
                                                pojo.setLoanEmiAmt(loanEmi);
                                                pojo.setOutStdBal(Math.abs(outstandbal));
                                                pojo.setTotalAmt(totalAmt);
                                                // pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                                Spellar word = new Spellar();
                                                pojo.setGrTotalAmt(grandTotalAmt);
                                                pojo.setGrandAmtInword(word.spellAmount(grandTotalAmt).toUpperCase());
                                                if (repType.equalsIgnoreCase("De")) {
                                                    pojo.setsNo(sno);
                                                } else {
                                                    pojo.setsNo(smSno);
                                                }
                                                // System.out.println(smSno);
                                                pojo.setOverDue(overDueAmt);
                                                dataList.add(pojo);
                                            }
                                        }

                                    } else {
                                        totalAmt = prinAmt + intAmt + overDueAmt + rdInstallment + theftFund;
                                        // pojo.setCustId(custId);
                                        pojo.setPrinAmt(prinAmt);
                                        pojo.setIntAmt(intAmt);
                                        pojo.setLoanAcNo(acno);
                                        pojo.setCustName(custName);
                                        pojo.setTheftFund(theftFund);
                                        // pojo.setTrade(trade);
                                        // pojo.setPersonalTokenNo(pTokenNo);
                                        pojo.setFlioNo(flioNo);
                                        //pojo.setLipAmt(lipAmt);
                                        pojo.setOutStdBal(Math.abs(outstandbal));
                                        pojo.setTotalAmt(totalAmt);
                                        pojo.setAcnoDesc(common.getAcctDecription(acctCode));
                                        pojo.setAcctCode(acctCode);
                                        if (repType.equalsIgnoreCase("De")) {
                                            pojo.setsNo(sno);
                                        } else {
                                            pojo.setsNo(smSno);
                                        }
                                        dataList.add(pojo);
                                    }

                                }
                            }
                        }
                    }
                }
                // For Grouping
                if (reportType.equalsIgnoreCase("Excel")) {
                    StatemenrtOfRecoveriesPojo pojo2 = new StatemenrtOfRecoveriesPojo();
                    pojo2.setFlioNo("FOLIO NUMBER WISE TOTAL ");
                    pojo2.setOutStdBal(Math.abs(gpOutstandbal));
                    pojo2.setPrinAmt(gpPrinAmt);
                    pojo2.setIntAmt(gpIntAmt);
                    pojo2.setOverDue(gpOverDue);
                    pojo2.setLoanEmiAmt(gpLoanEmi);
                    pojo2.setLipAmt(gpLipAmt);
                    pojo2.setTheftFund(gpTheftFund);
                    pojo2.setInstallmentAmt(gpRdInstallment);
                    pojo2.setTotalAmt(gpTotalAmt);
                    pojo2.setGrTotalAmt(grandTotalAmt);

                    pojo2.setLoanAcNo("");
                    pojo2.setCustName("");
                    pojo2.setTrade("");
                    pojo2.setOrder("");
                    pojo2.setPersonalTokenNo("");
                    dataList.add(pojo2);
                }
            }
            // For Grand Total
            if (reportType.equalsIgnoreCase("Excel")) {
                StatemenrtOfRecoveriesPojo pojo2 = new StatemenrtOfRecoveriesPojo();
                pojo2.setFlioNo("");
                pojo2.setLoanAcNo("GRAND TOTAL");
                pojo2.setOutStdBal(Math.abs(totalOutstandbal));
                pojo2.setPrinAmt(totalPrinAmt);
                pojo2.setIntAmt(totalIntAmt);
                pojo2.setOverDue(totalOverDue);
                pojo2.setLoanEmiAmt(totalLoanEmi);
                pojo2.setLipAmt(totalLipAmt);
                pojo2.setTheftFund(totalTheftFund);
                pojo2.setInstallmentAmt(totalRdInstallment);
                pojo2.setTotalAmt(totalTotalAmt);
                pojo2.setGrTotalAmt(grandTotalAmt);
                pojo2.setCustName("");
                pojo2.setTrade("");
                pojo2.setPersonalTokenNo("");
                pojo2.setOrder("");

                dataList.add(pojo2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

        return dataList;
    }

    public List<StatemenrtOfRecoveriesPojo> getDemandRecoveriesSummaryData(String area, String tillDate, String brCode, String repType, String optType, String reportType) throws ApplicationException {
        List<StatemenrtOfRecoveriesPojo> dataList = new ArrayList<StatemenrtOfRecoveriesPojo>();
        try {

            String bnkIndentify = fts.getCodeFromCbsParameterInfo("BNK_IDENTIFIER");
            String acctType = postalFacadeRemote.armyInterestPostParameter();
            if (acctType.equalsIgnoreCase("0")) {
                throw new ApplicationException("Please fill Acct Code in cbs_parameterinfo!");
            }
            List custIdList = em.createNativeQuery("select distinct(CustId) from customerid  where CustId in(select custid from share_holder where area = '" + area + "' and custid is not null)order by custid").getResultList();
            int smSno = 0;
            double grandTotalAmt = 0;
            double totalOutstandbal = 0d, totalPrinAmt = 0d, totalIntAmt = 0d, totalLoanEmi = 0d, totalLipAmt = 0d, totalTheftFund = 0d, totalRdInstallment = 0d, totalTotalAmt = 0d, totalOverDue = 0d;
            if (!custIdList.isEmpty()) {
                for (int i = 0; i < custIdList.size(); i++) {
                    Vector cvr = (Vector) custIdList.get(i);
                    String custId = cvr.get(0).toString();
                    smSno = smSno + 1;
                    List result = new ArrayList();
                    if (bnkIndentify.equalsIgnoreCase("RP")) {
                        result = em.createNativeQuery("select acno,custname from accountmaster  where acno in(select acno from customerid where "
                                + "custid = '" + custId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
                                + "and curbrcode = '" + brCode + "' and accttype in(" + acctType + ") ").getResultList();
                    } else {
                        result = em.createNativeQuery("select acno,custname from accountmaster  where acno in(select acno from customerid where "
                                + "custid = '" + custId + "')and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')"
                                + "and curbrcode = '" + brCode + "' and accttype in(select acctcode from accounttypemaster where acctnature in "
                                + "('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.RECURRING_AC + "') and acctcode <> '31')"
                                + "union "
                                + "select acno,custname from accountmaster  where acno in(select acno from customerid where custid = '" + custId + "')"
                                + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + tillDate + "')and curbrcode = '" + brCode + "' "
                                + "and accttype = '" + CbsAcCodeConstant.CL_AC.getAcctCode() + "'").getResultList();
                    }

                    int sno = 0;
                    String cId = "";
                    String trade = "", flioNo = "", order = "", pTokenNo = "", custName = "";
                    double gpOutstandbal = 0d, gpPrinAmt = 0d, gpIntAmt = 0d, gpLoanEmi = 0d, gpLipAmt = 0d, gpTheftFund = 0d, gpRdInstallment = 0d, gpTotalAmt = 0d, gpOverDue = 0d;
                    StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector element = (Vector) result.get(j);
                            //StatemenrtOfRecoveriesPojo pojo = new StatemenrtOfRecoveriesPojo();
                            String acno = element.get(0).toString();
                            custName = element.get(1).toString();
                            double outstandbal = 0.0;
                            sno = sno + 1;
                            String acctCode = acno.substring(2, 4);
                            String acNat = fts.getAcNatureByCode(acctCode);
                            if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                outstandbal = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate));
                            }

                            //String trade = "", flioNo = "", order = "", pTokenNo = "";
                            List tradeList = em.createNativeQuery("select ifnull(a.buss_desig,''),a.Regfoliono, ifnull((select order_by from cbs_ref_rec_type where REF_REC_NO = '020'  "
                                    + "and REF_CODE = a.buss_desig ),'') as order_by ,ifnull(employeeNo,'') from share_holder a, cbs_customer_master_detail c where "
                                    + "a.custid = '" + custId + "' and a.custid = c.customerid").getResultList();
                            if (!tradeList.isEmpty()) {
                                Vector ele1 = (Vector) tradeList.get(0);
                                trade = ele1.get(0).toString();
                                flioNo = ele1.get(1).toString();
                                order = ele1.get(2).toString();
                                pTokenNo = ele1.get(3).toString();
                                if (trade.equalsIgnoreCase("null")) {
                                    trade = "";
                                }
                            }
                            double theftFund = 0d, rdInstallment = 0d;
                            if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                                List rdInstallmentList = em.createNativeQuery("select ifnull((sum(installamt)),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                Vector rdvtr = (Vector) rdInstallmentList.get(0);
                                rdInstallment = Double.parseDouble(rdvtr.get(0).toString());
                                if (rdInstallment == 0) {
                                    rdInstallmentList = em.createNativeQuery("select ifnull(sum(INSTALLAMT),0),date_format(max(duedt),'%Y%m%d') as maxDueDt from rd_installment "
                                            + "where acno='" + acno + "' and sno=(select min(SNO) from rd_installment where acno = '" + acno + "' and STATUS = 'Unpaid')").getResultList();
                                }
                                rdvtr = (Vector) rdInstallmentList.get(0);
                                rdInstallment = Double.parseDouble(rdvtr.get(0).toString());
                                //pojo.setInstallmentAmt(rdInstallment);
                            }
                            if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                List theftCustIdList = em.createNativeQuery("select * from share_holder where custid in(select custid from customerid where acno = '" + acno + "')").getResultList();
                                if (!theftCustIdList.isEmpty()) {
                                    List theftFundList = em.createNativeQuery("select RdInstal as overDueAmt from accountmaster where acno  = '" + acno + "'").getResultList();
                                    Vector vtr = (Vector) theftFundList.get(0);
                                    theftFund = Double.parseDouble(vtr.get(0).toString());
                                }
                            }
                            //********************* For Interest ********************
                            double prinAmt = 0, intAmt = 0;
                            if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                String fromDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "f");
                                String toDt = loanInterestCalculationBean.allFromDt(acctCode, brCode, "t");
                                LoanIntCalcList it = loanInterestCalculationBean.accWiseLoanIntCalc(fromDt, toDt, acno, brCode);
                                intAmt = it.getTotalInt();
                                if (!(intAmt == 0)) {
                                    intAmt = intAmt * -1;
                                }
                            }
                            double overDueAmt = 0d, lipAmt = 0d;
                            if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                                List result1;
                                double outSt = Double.parseDouble(loanInterestCalculationBean.outStandingAsOnDate(acno, tillDate));

//                                List emeCheckingList = em.createNativeQuery("select ifnull(min(SNO),0),ifnull(max(SNO),0) from emidetails where acno = '" + acno + "' and STATUS = 'UNPAID' ").getResultList();
//                                Vector emiVector = (Vector) emeCheckingList.get(0);
//                                int minSeqNo = Integer.parseInt(emiVector.get(0).toString());
//                                int maxSeqNo = Integer.parseInt(emiVector.get(1).toString());
//
//                                if (minSeqNo != maxSeqNo) {
//                                    List ovedueList = em.createNativeQuery("select count(sno) from emidetails where acno = '" + acno + "' and duedt <='" + tillDate + "' and status = 'unpaid'").getResultList();
//                                    Vector ele = (Vector) ovedueList.get(0);
//                                    int overDuesno = Integer.parseInt(ele.get(0).toString());
//                                    if (overDuesno > 1) {
//                                        result1 = em.createNativeQuery("select ifnull(sum(installamt),0)- ifnull(sum(excessamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
//                                    } else {
//                                        result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
//                                    }
//                                } else {
//                                    result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' /*and duedt<='" + tillDate + "'*/").getResultList();
//                                }

                                result1 = em.createNativeQuery("select ifnull(installamt,0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' and duedt<='" + tillDate + "'").getResultList();
                                if (result1.isEmpty()) {
                                    result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails where acno='" + acno + "' and status='Unpaid' /*and duedt<='" + tillDate + "'*/").getResultList();
                                }

                                Vector ele2 = (Vector) result1.get(0);
                                prinAmt = Double.parseDouble(ele2.get(0).toString());
                                if (prinAmt == 0) {
                                    if (Math.abs(outSt) > 0) {
                                        result1 = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,"
                                                + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails "
                                                + "where acno='" + acno + "' and status='Unpaid' and "
                                                + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails "
                                                + "where acno='" + acno + "' and status='Unpaid') ").getResultList();
                                    }
                                    ele2 = (Vector) result1.get(0);
                                    prinAmt = Double.parseDouble(ele2.get(0).toString());
                                    if (prinAmt == 0) {
                                        prinAmt = Math.abs(outSt);
                                    } else {
                                        if (prinAmt > Math.abs(outSt)) {
                                            prinAmt = Math.abs(outSt);
                                        } else {
                                            prinAmt = prinAmt;
                                        }
                                    }
                                } else {
                                    if (prinAmt > Math.abs(outSt)) {
                                        prinAmt = Math.abs(outSt);
                                    } else {
                                        prinAmt = prinAmt;
                                    }
                                }

                                //For Over Due Amount new code
                                if (Math.abs(outSt) > 0) {
                                    List<OverDueListPojo> list = ddsRemote.getOverDueListData(acno.substring(0, 2), acno.substring(2, 4), tillDate, acno);
                                    if (!list.isEmpty()) {
                                        overDueAmt = list.get(0).getOveDue().doubleValue();
                                    }
                                }
                                //END For Over Due Amount new code
                            }
//                            List result2 = em.createNativeQuery("select ifnull(sum(premium_amount),0) from insurance_details where acno = '" + acno + "' and due_dt <= '" + tillDate + "'").getResultList();
//                            Vector ele3 = (Vector) result2.get(0);
//                            double lipAmt = Double.parseDouble(ele3.get(0).toString());
                            double totalAmt = prinAmt + intAmt + lipAmt + theftFund + rdInstallment + overDueAmt;
                            double loanEmi = prinAmt + intAmt + theftFund + rdInstallment;
                            //For Account wise total
                            gpOutstandbal = gpOutstandbal + Math.abs(outstandbal);
                            gpPrinAmt = gpPrinAmt + prinAmt;
                            gpIntAmt = gpIntAmt + intAmt;
                            gpLoanEmi = gpLoanEmi + loanEmi;
                            gpLipAmt = gpLipAmt + lipAmt;
                            gpTheftFund = gpTheftFund + theftFund;
                            gpRdInstallment = gpRdInstallment + rdInstallment;
                            gpTotalAmt = gpTotalAmt + totalAmt;
                            gpOverDue = gpOverDue + overDueAmt;
                            //For Grand total
                            totalOutstandbal = totalOutstandbal + Math.abs(outstandbal);
                            totalPrinAmt = totalPrinAmt + prinAmt;
                            totalIntAmt = totalIntAmt + intAmt;
                            totalLoanEmi = totalLoanEmi + loanEmi;
                            totalLipAmt = totalLipAmt + lipAmt;
                            totalTheftFund = totalTheftFund + theftFund;
                            totalRdInstallment = totalRdInstallment + rdInstallment;
                            totalTotalAmt = totalTotalAmt + totalAmt;
                            totalOverDue = totalOverDue + overDueAmt;

                            grandTotalAmt = grandTotalAmt + totalAmt;
                        }
                        if (reportType.equalsIgnoreCase("Excel")) {
                            // StatemenrtOfRecoveriesPojo pojo1 = new StatemenrtOfRecoveriesPojo();
                            pojo.setLoanAcNo("FOLIO NUMBER WISE TOTAL ");
                            pojo.setFlioNo(flioNo);
                            pojo.setOutStdBal(Math.abs(gpOutstandbal));
                            pojo.setPrinAmt(gpPrinAmt);
                            pojo.setIntAmt(gpIntAmt);
                            pojo.setOverDue(gpOverDue);
                            pojo.setLoanEmiAmt(gpLoanEmi);
                            pojo.setLipAmt(gpLipAmt);
                            pojo.setTheftFund(gpTheftFund);
                            pojo.setInstallmentAmt(gpRdInstallment);
                            pojo.setTotalAmt(gpTotalAmt);
                            pojo.setGrTotalAmt(grandTotalAmt);

                            pojo.setCustName(custName);
                            pojo.setTrade(trade);
                            pojo.setOrder(order);
                            pojo.setPersonalTokenNo(pTokenNo);
                            dataList.add(pojo);
                        }
                    }
                }
            }
            // For Grand Total
            if (reportType.equalsIgnoreCase("Excel")) {
                StatemenrtOfRecoveriesPojo pojo2 = new StatemenrtOfRecoveriesPojo();
                pojo2.setFlioNo("");
                pojo2.setLoanAcNo("GRAND TOTAL");
                pojo2.setOutStdBal(Math.abs(totalOutstandbal));
                pojo2.setPrinAmt(totalPrinAmt);
                pojo2.setIntAmt(totalIntAmt);
                pojo2.setOverDue(totalOverDue);
                pojo2.setLoanEmiAmt(totalLoanEmi);
                pojo2.setLipAmt(totalLipAmt);
                pojo2.setTheftFund(totalTheftFund);
                pojo2.setInstallmentAmt(totalRdInstallment);
                pojo2.setTotalAmt(totalTotalAmt);
                pojo2.setGrTotalAmt(grandTotalAmt);
                pojo2.setCustName("");
                if (area.contains("P")) {
                    pojo2.setTrade("ZZZ");
                } else {
                    pojo2.setPersonalTokenNo("999999");
                }
                pojo2.setOrder("");

                dataList.add(pojo2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getDepositeMpr(String reportType, String col1, String col2, String col3, String col4, String incDec, String monQuter, String fromDt, String toDt, String brnCode, boolean noOfAcsBox, boolean tarAcsBox, boolean tarAmtBox, boolean osAmtBox) throws ApplicationException {
        List<DepositeMprPojo> returnList = new ArrayList<DepositeMprPojo>();
        String tab_name = "";
        String finDt = "";
        int noOfFinAcno = 0, noOfLmAcno = 0, noOfcmAcno = 0, tarAcno = 0, inc_ac = 0, tot_inc_ac = 0;
        double finBal = 0d, lmBal = 0d, cmBal = 0d, targetAmt = 0d, glAmt = 0d, inc_amt = 0d, tot_inc_amt = 0d;

        try {
            List findtList = em.createNativeQuery("select mindate from yearend where '" + fromDt + "' between mindate and maxdate and brncode='" + brnCode + "'").getResultList();
            if (!findtList.isEmpty()) {
                Vector fV = (Vector) findtList.get(0);
                finDt = fV.get(0).toString();
            }

            if (reportType.equals("A/c Type Wise")) {
                List actNatureList = em.createNativeQuery("select distinct(acctnature) from accounttypemaster where acctnature in ('SB','CA','FD','MS','RD','DS') order by acctnature").getResultList();
                if (!actNatureList.isEmpty()) {
                    for (int i = 0; i < actNatureList.size(); i++) {
                        Vector anV = (Vector) actNatureList.get(i);
                        String acctNature = anV.get(0).toString();
                        String tableName = CbsUtil.getReconTableName(acctNature);

                        List acctCodeList = em.createNativeQuery("select acctcode,acctdesc from accounttypemaster where acctnature='" + acctNature + "' order by acctcode").getResultList();
                        if (!acctCodeList.isEmpty()) {
                            for (int j = 0; j < acctCodeList.size(); j++) {
                                Vector acodeV = (Vector) acctCodeList.get(j);
                                String acctCode = acodeV.get(0).toString();
                                String acctdesc = acodeV.get(1).toString();

                                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    tab_name = "td_accountmaster";
                                } else {
                                    tab_name = "accountmaster";
                                }
                                if (tarAcsBox == true && tarAmtBox == true) {
                                    List tarpList = em.createNativeQuery("select period from target_master limit 1").getResultList();
                                    if (!tarpList.isEmpty()) {
                                        Vector pV = (Vector) tarpList.get(0);
                                        String period = pV.get(0).toString();
                                        if (period.equals("M")) {
                                            List tarList = em.createNativeQuery("select sum(targetacno),sum(targetamt) from target_master where acctnature='" + acctNature + "'and type='D' and dtfrom='" + fromDt + "' and dtto ='" + toDt + "'").getResultList();
                                            if (!tarList.isEmpty()) {
                                                pV = (Vector) tarList.get(0);
                                                if (pV.get(0) != null) {
                                                    tarAcno = Integer.parseInt(pV.get(0).toString());
                                                } else {
                                                    tarAcno = 0;
                                                }
                                                if (pV.get(1) != null) {
                                                    targetAmt = Double.parseDouble(pV.get(1).toString());
                                                } else {
                                                    targetAmt = 0.0;
                                                }
                                            }
                                        } else {
                                            List tarList = em.createNativeQuery("select sum(targetacno),sum(targetamt) from target_master where acctnature='" + acctNature + "'and type='D' and dtfrom='" + finDt + "' and dtto =DATE_FORMAT(DATE_ADD( '" + finDt + "', INTERVAL 1 YEAR), '%Y%m%d')").getResultList();
                                            if (!tarList.isEmpty()) {
                                                pV = (Vector) tarList.get(0);
                                                if (pV.get(0) != null) {
                                                    tarAcno = Integer.parseInt(pV.get(0).toString());
                                                } else {
                                                    tarAcno = 00;
                                                }
                                                if (pV.get(1) != null) {
                                                    targetAmt = Double.parseDouble(pV.get(1).toString());
                                                } else {
                                                    targetAmt = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                                /**
                                 * **********************************************
                                 * For Last Fin Year col1
                                 * *********************************************
                                 */
                                if (col1.equalsIgnoreCase("O/S As On 01.04.") || col1.equalsIgnoreCase("Begining Of The Year")) {
                                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List finYearList = em.createNativeQuery("select count(distinct cc.acno), ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) "
                                                + "from " + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt
                                                + "' and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode
                                                + "'and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')"
                                                + "= '' or am.closingdate='') having sum(cramt)-sum(dramt)>0 ").getResultList();
                                        if (!finYearList.isEmpty()) {
                                            Vector finV = (Vector) finYearList.get(0);
                                            noOfFinAcno = Integer.parseInt(finV.get(0).toString());
                                            finBal = Double.parseDouble(finV.get(1).toString());
                                        }
                                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        List finYearList1 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) "
                                                + "from " + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt
                                                + "' and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode
                                                + "'and cc.closeflag is null and cc.auth = 'Y'and am.openingdt<='" + finDt + "' and (am.closingdate>'"
                                                + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') ").getResultList();
                                        if (!finYearList1.isEmpty()) {
                                            Vector finV1 = (Vector) finYearList1.get(0);
                                            noOfFinAcno = Integer.parseInt(finV1.get(0).toString());
                                            finBal = Double.parseDouble(finV1.get(1).toString());
                                        }
                                    } else {
                                        List finYearList2 = em.createNativeQuery("select count(distinct cc.acno), ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) "
                                                + "from " + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt
                                                + "' and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode
                                                + "'and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')="
                                                + "'' or am.closingdate='')").getResultList();
                                        if (!finYearList2.isEmpty()) {
                                            Vector finV2 = (Vector) finYearList2.get(0);
                                            if (finV2.get(0) != null) {
                                                noOfFinAcno = Integer.parseInt(finV2.get(0).toString());
                                            } else {
                                                noOfFinAcno = 0;
                                            }
                                            if (finV2.get(1) != null) {
                                                finBal = Double.parseDouble(finV2.get(1).toString());
                                            } else {
                                                finBal = 0;
                                            }
                                        }
                                    }
                                }
                                /**
                                 * ******************************************************
                                 * FOR LAST MONTH col2
                                 * *****************************************************
                                 */
                                if (col2.equalsIgnoreCase("Last Month") || col2.equalsIgnoreCase("O/S At The End Of Prev. Mon.")) {
                                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        if (monQuter.equals("M")) {
                                            List lastmonthList1 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) "
                                                    + "from " + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=date_format(date_add('"
                                                    + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                                    + acctCode + "'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') "
                                                    + "and (am.closingdate> DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or "
                                                    + "ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!lastmonthList1.isEmpty()) {
                                                Vector cobV = (Vector) lastmonthList1.get(0);
                                                noOfLmAcno = Integer.parseInt(cobV.get(0).toString());
                                                lmBal = Double.parseDouble(cobV.get(1).toString());
                                            }
                                        } else {
                                            List lastmonthList1 = em.createNativeQuery("select count(distinct cc.acno), ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                    + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('"
                                                    + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode
                                                    + "' and substring(am.acno,3,2)='" + acctCode + "'and am.openingdt<=DATE_FORMAT(DATE_ADD('"
                                                    + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('"
                                                    + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') "
                                                    + "having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!lastmonthList1.isEmpty()) {
                                                Vector cobV = (Vector) lastmonthList1.get(0);
                                                noOfLmAcno = Integer.parseInt(cobV.get(0).toString());
                                                lmBal = Double.parseDouble(cobV.get(1).toString());
                                            }
                                        }
                                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        if (monQuter.equals("M")) {
                                            List lastmonthList2 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                    + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('"
                                                    + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode
                                                    + "' and substring(am.acno,3,2)='" + acctCode + "'and cc.closeflag is null and cc.auth = 'Y' "
                                                    + "and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') and "
                                                    + "(am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or "
                                                    + "ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!lastmonthList2.isEmpty()) {
                                                Vector coV1 = (Vector) lastmonthList2.get(0);
                                                noOfLmAcno = Integer.parseInt(coV1.get(0).toString());
                                                lmBal = Double.parseDouble(coV1.get(1).toString());
                                            }

                                        } else {
                                            List lastmonthList2 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                    + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt
                                                    + "', INTERVAL -3 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                                    + acctCode + "'and cc.closeflag is null and cc.auth = 'Y' and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt
                                                    + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') "
                                                    + "or ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!lastmonthList2.isEmpty()) {
                                                Vector coV1 = (Vector) lastmonthList2.get(0);
                                                noOfLmAcno = Integer.parseInt(coV1.get(0).toString());
                                                lmBal = Double.parseDouble(coV1.get(1).toString());
                                            }
                                        }
                                    } else {
                                        if (monQuter.equals("M")) {
                                            List lastmonthList3 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                    + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt
                                                    + "', INTERVAL -1 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                                    + acctCode + "'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') "
                                                    + "and (am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or "
                                                    + "ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                            if (!lastmonthList3.isEmpty()) {
                                                Vector coV2 = (Vector) lastmonthList3.get(0);
                                                noOfLmAcno = Integer.parseInt(coV2.get(0).toString());
                                                lmBal = Double.parseDouble(coV2.get(1).toString());
                                            }

                                        } else {
                                            List lastmonthList3 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                    + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt
                                                    + "', INTERVAL -3 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                                    + acctCode + "'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') and "
                                                    + "(am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' "
                                                    + "or am.closingdate='')").getResultList();
                                            if (!lastmonthList3.isEmpty()) {
                                                Vector coV2 = (Vector) lastmonthList3.get(0);
                                                noOfLmAcno = Integer.parseInt(coV2.get(0).toString());
                                                lmBal = Double.parseDouble(coV2.get(1).toString());
                                            }
                                        }
                                    }
                                }
                                /**
                                 * ******************************************************
                                 * For Current Month col3
                                 * *****************************************************
                                 */
                                if (col3.equalsIgnoreCase("O/S At The End Of Curr. Mon.") || col3.equalsIgnoreCase("Current Month")) {
                                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List curntmonthList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='"
                                                + brnCode + "' and substring(am.acno,3,2)='" + acctCode + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'"
                                                + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                        if (!curntmonthList.isEmpty()) {
                                            Vector cmV = (Vector) curntmonthList.get(0);
                                            noOfcmAcno = Integer.parseInt(cmV.get(0).toString());
                                            cmBal = Double.parseDouble(cmV.get(1).toString());
                                        }
                                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        List curntmonthList1 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='"
                                                + brnCode + "' and substring(am.acno,3,2)='" + acctCode + "'and cc.closeflag is null and cc.auth = 'Y' and "
                                                + "am.openingdt<='" + toDt + "' and (am.closingdate>'" + toDt + "' or ifnull(am.closingdate,'')='' or "
                                                + "am.closingdate='')").getResultList();
                                        if (!curntmonthList1.isEmpty()) {
                                            Vector cmV1 = (Vector) curntmonthList1.get(0);
                                            noOfcmAcno = Integer.parseInt(cmV1.get(0).toString());
                                            cmBal = Double.parseDouble(cmV1.get(1).toString());
                                        }
                                    } else {
                                        List curntmonthList2 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + "  am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='"
                                                + brnCode + "' and substring(am.acno,3,2)='" + acctCode + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'"
                                                + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                        if (!curntmonthList2.isEmpty()) {
                                            Vector cmV2 = (Vector) curntmonthList2.get(0);
                                            noOfcmAcno = Integer.parseInt(cmV2.get(0).toString());
                                            cmBal = Double.parseDouble(cmV2.get(1).toString());
                                        }
                                    }
                                }
                                /**
                                 * **************************************************************
                                 * col4
                                 * *************************************************************
                                 */
                                if (col4.equalsIgnoreCase("Current Month")) {

                                    if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List ecmList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='"
                                                + brnCode + "' and substring(am.acno,3,2)='" + acctCode + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'"
                                                + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                        if (!ecmList.isEmpty()) {
                                            Vector ecmV = (Vector) ecmList.get(0);
                                            noOfcmAcno = Integer.parseInt(ecmV.get(0).toString());
                                            inc_ac = noOfcmAcno;
                                            cmBal = Double.parseDouble(ecmV.get(1).toString());
                                            inc_amt = cmBal;
                                        }
                                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                        List ecm1List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt
                                                + "' and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                                + acctCode + "'and cc.closeflag is null and cc.auth = 'Y'and am.openingdt<='" + toDt
                                                + "' and (am.closingdate>'" + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                        if (!ecm1List.isEmpty()) {
                                            Vector ecmV1 = (Vector) ecm1List.get(0);
                                            noOfcmAcno = Integer.parseInt(ecmV1.get(0).toString());
                                            inc_ac = noOfcmAcno;
                                            cmBal = Double.parseDouble(ecmV1.get(1).toString());
                                            inc_amt = cmBal;
                                        }
                                    } else {
                                        List ecm2List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                                + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='"
                                                + brnCode + "' and substring(am.acno,3,2)='" + acctCode + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'"
                                                + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                        if (!ecm2List.isEmpty()) {
                                            Vector ecm2 = (Vector) ecm2List.get(0);
                                            noOfcmAcno = Integer.parseInt(ecm2.get(0).toString());
                                            inc_ac = noOfcmAcno;
                                            cmBal = Double.parseDouble(ecm2.get(1).toString());
                                            inc_amt = cmBal;
                                        }
                                    }
                                } else if (col4.equalsIgnoreCase("Increment/Decrement") || incDec.equalsIgnoreCase("Last Year")) {
                                    inc_ac = noOfcmAcno - noOfFinAcno;
                                    inc_amt = cmBal - finBal;
                                } else if (col4.equalsIgnoreCase("Increment/Decrement") || incDec.equalsIgnoreCase("Last Month")) {
                                    inc_ac = noOfcmAcno - noOfLmAcno;
                                    inc_amt = cmBal - lmBal;
                                }
                                /**
                                 * **************************************************************
                                 * Make a acno
                                 * *************************************************************
                                 */
                                if (osAmtBox == true) {
                                    List codeList = em.createNativeQuery("select distinct code from parameterinfo_report where reportname='DMPR'").getResultList();
                                    if (!codeList.isEmpty()) {
                                        Vector cV = (Vector) codeList.get(0);
                                        String code = cV.get(0).toString();
                                        String gLAcno = brnCode + CbsAcCodeConstant.GL_ACCNO + "000" + code + "01";

                                        List glList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cramt)-sum(dramt))/100000,0) from "
                                                + "gl_recon  where acno='" + gLAcno + "'  and dt<='" + finDt + "' having sum(cramt)-sum(dramt)>0").getResultList();
                                        if (!glList.isEmpty()) {
                                            Vector glV = (Vector) glList.get(0);
                                            if (glV.get(0) != null) {
                                                glAmt = Double.parseDouble(glV.get(0).toString());
                                            } else {
                                                glAmt = 0.0;
                                            }
                                        }
                                        /**
                                         * **************************************************************
                                         * FOR CURRENT MONTH
                                         * *************************************************************
                                         */
                                        //if(c)
                                        if (monQuter.equals("M")) {
                                            List glList1 = em.createNativeQuery("select ifnull((sum(cramt)-sum(dramt))/100000,0) from gl_recon  where acno='"
                                                    + gLAcno + "'  and dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') "
                                                    + "having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!glList1.isEmpty()) {
                                                Vector glV1 = (Vector) glList1.get(0);
                                                if (glV1.get(0) != null) {
                                                    glAmt = Double.parseDouble(glV1.get(0).toString());
                                                } else {
                                                    glAmt = 0.0;
                                                }
                                            }
                                        } else {
                                            List glList2 = em.createNativeQuery("select ifnull((sum(cramt)-sum(dramt))/100000,0) from gl_recon  where acno='"
                                                    + gLAcno + "' and dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d')"
                                                    + " having sum(cramt)-sum(dramt)>0").getResultList();
                                            if (!glList2.isEmpty()) {
                                                Vector glV2 = (Vector) glList2.get(0);
                                                if (glV2.get(0) != null) {
                                                    glAmt = Double.parseDouble(glV2.get(0).toString());
                                                } else {
                                                    glAmt = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (col1.equalsIgnoreCase("O/S As On 01.04.") || col1.equalsIgnoreCase("Begining Of The Year") && osAmtBox == true) {
                                    finBal = finBal + glAmt;
                                }
                                if (col2.equalsIgnoreCase("O/S At The End Of Prev. Mon.") || col2.equalsIgnoreCase("Last Month") && osAmtBox == true) {
                                    lmBal = lmBal + glAmt;
                                }
                                if (col3.equalsIgnoreCase("O/S At The End Of Curr. Mon.") || col2.equalsIgnoreCase("Current Month") && osAmtBox == true) {
                                    cmBal = cmBal + glAmt;
                                }
                                if (col4.equalsIgnoreCase("Current Month") || col4.equalsIgnoreCase("Increment/Decrement") && osAmtBox == true) {
                                    cmBal = cmBal + glAmt;
                                }
                                DepositeMprPojo pojo = new DepositeMprPojo();
                                pojo.setAcctDesc(acctdesc);
                                pojo.setNoOfFinAcno(noOfFinAcno);
                                pojo.setFinBal(finBal);
                                pojo.setNoOfLmAcno(noOfLmAcno);
                                pojo.setLmBal(lmBal);
                                pojo.setNoOfcmAcno(noOfcmAcno);
                                pojo.setCmBal(cmBal);
                                pojo.setTarAmt(targetAmt);
                                pojo.setTarAcno(tarAcno);
                                pojo.setInc_ac(inc_ac);
                                pojo.setInc_amt(inc_amt);
                                returnList.add(pojo);
                                /**
                                 * **********************************************************************
                                 * End Final Loop
                                 * *********************************************************************
                                 */
                            }
                        }
                    }
                }
            } else {
                List acctDescList = em.createNativeQuery("select distinct(acctcode),acctdesc,acctnature from accounttypemaster where acctnature in ('SB','CA','FD','MS','RD','DS')  order by acctnature,acctcode").getResultList();
                if (!acctDescList.isEmpty()) {
                    for (int k = 0; k < acctDescList.size(); k++) {
                        Vector acdescV = (Vector) acctDescList.get(k);
                        String acctCode1 = acdescV.get(0).toString();
                        String acctDesc = acdescV.get(1).toString();
                        String actNature = acdescV.get(2).toString();
                        String tableName = CbsUtil.getReconTableName(actNature);

                        if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            tab_name = "td_accountmaster";
                        } else {
                            tab_name = "accountmaster";
                        }
                        if (tarAcsBox == true && tarAmtBox == true) {
                            List tarpList = em.createNativeQuery("select period from target_master limit 1").getResultList();
                            if (!tarpList.isEmpty()) {
                                Vector pV = (Vector) tarpList.get(0);
                                String period = pV.get(0).toString();
                                if (period.equals("M")) {
                                    List tarList = em.createNativeQuery("select sum(targetacno),sum(targetamt) from target_master where acctdesc='" + acctDesc + "'and type='D' and dtfrom='" + fromDt + "' and dtto ='" + toDt + "'").getResultList();
                                    if (!tarList.isEmpty()) {
                                        pV = (Vector) tarList.get(0);
                                        if (pV.get(0) != null) {
                                            tarAcno = Integer.parseInt(pV.get(0).toString());
                                        } else {
                                            tarAcno = 00;
                                        }
                                        if (pV.get(1) != null) {
                                            targetAmt = Double.parseDouble(pV.get(1).toString());
                                        } else {
                                            targetAmt = 0.0;
                                        }
                                    }
                                } else {
                                    List tarList = em.createNativeQuery("select sum(targetacno),sum(targetamt) from target_master where acctdesc='" + acctDesc + "'and type='D' and dtfrom='" + finDt + "' and dtto =DATE_FORMAT(DATE_ADD( '" + finDt + "', INTERVAL 1 YEAR), '%Y%m%d')").getResultList();
                                    if (!tarList.isEmpty()) {
                                        pV = (Vector) tarList.get(0);
                                        if (pV.get(0) != null) {
                                            tarAcno = Integer.parseInt(pV.get(0).toString());
                                        } else {
                                            tarAcno = 0;
                                        }
                                        if (pV.get(1) != null) {
                                            targetAmt = Double.parseDouble(pV.get(1).toString());
                                        } else {
                                            targetAmt = 0.0;
                                        }
                                    }
                                }
                            }
                        }
                        /**
                         * **********************************************************************
                         * For Last Fin Year col 1 , For O/S As On 01.04.
                         * *********************************************************************
                         */
                        if (col1.equalsIgnoreCase("O/S As On 01.04.") || col1.equalsIgnoreCase("Begining Of The Year")) {
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                List lfyList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                        + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt + "' and "
                                        + "substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1
                                        + "'and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' "
                                        + "or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                if (!lfyList.isEmpty()) {
                                    Vector lfyV = (Vector) lfyList.get(0);
                                    noOfFinAcno = Integer.parseInt(lfyV.get(0).toString());
                                    finBal = Double.parseDouble(lfyV.get(1).toString());
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                List lfyList1 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                        + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt + "' and "
                                        + "substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1 + "'and cc.closeflag "
                                        + "is null and cc.auth = 'Y'and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or "
                                        + "ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!lfyList1.isEmpty()) {
                                    Vector lfy1V = (Vector) lfyList1.get(0);
                                    noOfFinAcno = Integer.parseInt(lfy1V.get(0).toString());
                                    finBal = Double.parseDouble(lfy1V.get(1).toString());
                                }
                            } else {
                                List lfyList2 = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + finDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt
                                        + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!lfyList2.isEmpty()) {
                                    Vector lfy2V = (Vector) lfyList2.get(0);
                                    noOfFinAcno = Integer.parseInt(lfy2V.get(0).toString());
                                    finBal = Double.parseDouble(lfy2V.get(1).toString());
                                }
                            }
                        }
                        /**
                         * ******************************************************************************
                         * For O/S At The End Of Prev. Mon. col 2
                         * *****************************************************************************
                         */
                        if (col2.equalsIgnoreCase("O/S At The End Of Prev. Mon.") || col2.equalsIgnoreCase("Last Month")) {
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                if (monQuter.equals("M")) {
                                    List lmList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                            + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), "
                                            + "'%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1 + "'and "
                                            + "am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') and (am.closingdate>"
                                            + "DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' "
                                            + "or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!lmList.isEmpty()) {
                                        Vector lmV = (Vector) lmList.get(0);
                                        noOfLmAcno = Integer.parseInt(lmV.get(0).toString());
                                        lmBal = Double.parseDouble(lmV.get(1).toString());
                                    }
                                } else {
                                    List lmList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                            + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), "
                                            + "'%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1 + "'and "
                                            + "am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate>"
                                            + "DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' "
                                            + "or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!lmList.isEmpty()) {
                                        Vector lmV = (Vector) lmList.get(0);
                                        noOfLmAcno = Integer.parseInt(lmV.get(0).toString());
                                        lmBal = Double.parseDouble(lmV.get(1).toString());
                                    }
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                if (monQuter.equals("M")) {
                                    List lm1List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                            + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), "
                                            + "'%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1 + "'and "
                                            + "cc.closeflag is null and cc.auth = 'Y'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), "
                                            + "'%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or "
                                            + "ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!lm1List.isEmpty()) {
                                        Vector lm1V = (Vector) lm1List.get(0);
                                        noOfLmAcno = Integer.parseInt(lm1V.get(0).toString());
                                        lmBal = Double.parseDouble(lm1V.get(1).toString());
                                    }
                                } else {
                                    List lm1List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                            + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), "
                                            + "'%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1
                                            + "'and cc.closeflag is null and cc.auth = 'Y'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), "
                                            + "'%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') or "
                                            + "ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!lm1List.isEmpty()) {
                                        Vector lm1V = (Vector) lm1List.get(0);
                                        noOfLmAcno = Integer.parseInt(lm1V.get(0).toString());
                                        lmBal = Double.parseDouble(lm1V.get(1).toString());
                                    }
                                }
                            } else {
                                if (monQuter.equals("M")) {
                                    List lm2List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from "
                                            + tableName + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt
                                            + "', INTERVAL -1 MONTH), '%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='"
                                            + acctCode1 + "'and am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') "
                                            + "and (am.closingdate>DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') or "
                                            + "ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                    if (!lm2List.isEmpty()) {
                                        Vector lm2V = (Vector) lm2List.get(0);
                                        noOfLmAcno = Integer.parseInt(lm2V.get(0).toString());
                                        lmBal = Double.parseDouble(lm2V.get(1).toString());
                                    }
                                } else {
                                    List lm2List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                            + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), "
                                            + "'%Y%m%d') and substring(am.acno,1,2)='" + brnCode + "' and substring(am.acno,3,2)='" + acctCode1 + "'and "
                                            + "am.openingdt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate>"
                                            + "DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or "
                                            + "am.closingdate='')").getResultList();
                                    if (!lm2List.isEmpty()) {
                                        Vector lm2V = (Vector) lm2List.get(0);
                                        noOfLmAcno = Integer.parseInt(lm2V.get(0).toString());
                                        lmBal = Double.parseDouble(lm2V.get(1).toString());
                                    }
                                }
                            }
                        }
                        /**
                         * **************************************************************************************
                         * For UP TO End of Current Month col 3
                         * *************************************************************************************
                         */
                        if (col3.equalsIgnoreCase("O/S At The End Of Curr. Mon.") || col3.equalsIgnoreCase("Current Month")) {
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                List ecmList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'" + toDt
                                        + "' or ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                if (!ecmList.isEmpty()) {
                                    Vector ecmV = (Vector) ecmList.get(0);
                                    noOfcmAcno = Integer.parseInt(ecmV.get(0).toString());
                                    cmBal = Double.parseDouble(ecmV.get(1).toString());
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                List ecm1List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and cc.closeflag is null and cc.auth = 'Y'and am.openingdt<='"
                                        + toDt + "' and (am.closingdate>'" + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!ecm1List.isEmpty()) {
                                    Vector ecmV1 = (Vector) ecm1List.get(0);
                                    noOfcmAcno = Integer.parseInt(ecmV1.get(0).toString());
                                    cmBal = Double.parseDouble(ecmV1.get(1).toString());
                                }
                            } else {
                                List ecm2List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'" + toDt
                                        + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!ecm2List.isEmpty()) {
                                    Vector ecm2 = (Vector) ecm2List.get(0);
                                    noOfcmAcno = Integer.parseInt(ecm2.get(0).toString());
                                    cmBal = Double.parseDouble(ecm2.get(1).toString());
                                }
                            }
                        }
                        /**
                         * **************************************************************************************
                         * col4
                         * *************************************************************************************
                         */
                        if (col4.equalsIgnoreCase("Current Month")) {
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                List ecmList = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'" + toDt
                                        + "' or ifnull(am.closingdate,'')='' or am.closingdate='') having sum(cramt)-sum(dramt)>0").getResultList();
                                if (!ecmList.isEmpty()) {
                                    Vector ecmV = (Vector) ecmList.get(0);
                                    noOfcmAcno = Integer.parseInt(ecmV.get(0).toString());
                                    inc_ac = noOfcmAcno;
                                    cmBal = Double.parseDouble(ecmV.get(1).toString());
                                    inc_amt = cmBal;
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                List ecm1List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and cc.closeflag is null and cc.auth = 'Y'and am.openingdt<='"
                                        + toDt + "' and (am.closingdate>'" + toDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!ecm1List.isEmpty()) {
                                    Vector ecmV1 = (Vector) ecm1List.get(0);
                                    noOfcmAcno = Integer.parseInt(ecmV1.get(0).toString());
                                    inc_ac = noOfcmAcno;
                                    cmBal = Double.parseDouble(ecmV1.get(1).toString());
                                    inc_amt = cmBal;
                                }
                            } else {
                                List ecm2List = em.createNativeQuery("select count(distinct cc.acno),ifnull((sum(cc.cramt)-sum(cc.dramt))/100000,0) from " + tableName
                                        + " cc," + tab_name + " am where cc.acno=am.acno and cc.dt<='" + toDt + "' and substring(am.acno,1,2)='" + brnCode
                                        + "' and substring(am.acno,3,2)='" + acctCode1 + "'and am.openingdt<='" + toDt + "' and (am.closingdate>'" + toDt
                                        + "' or ifnull(am.closingdate,'')='' or am.closingdate='')").getResultList();
                                if (!ecm2List.isEmpty()) {
                                    Vector ecm2 = (Vector) ecm2List.get(0);
                                    noOfcmAcno = Integer.parseInt(ecm2.get(0).toString());
                                    inc_ac = noOfcmAcno;
                                    cmBal = Double.parseDouble(ecm2.get(1).toString());
                                    inc_amt = cmBal;
                                }
                            }
                        } else if (col4.equalsIgnoreCase("Increment/Decrement") || incDec.equalsIgnoreCase("Last Year")) {
                            inc_ac = noOfcmAcno - noOfFinAcno;
                            inc_amt = cmBal - finBal;
                        } else if (col4.equalsIgnoreCase("Increment/Decrement") || incDec.equalsIgnoreCase("Last Month")) {
                            inc_ac = noOfcmAcno - noOfLmAcno;
                            inc_amt = cmBal - lmBal;
                        }
                        /**
                         * *************************
                         * Make a acno ************************
                         */
                        if (osAmtBox == true) {
                            List codeList = em.createNativeQuery("select distinct code from parameterinfo_report where reportname='DMPR'").getResultList();
                            if (!codeList.isEmpty()) {
                                Vector cV = (Vector) codeList.get(0);
                                String code = cV.get(0).toString();
                                String gLAcno = brnCode + CbsAcCodeConstant.GL_ACCNO + "000" + code + "01";

                                List glList = em.createNativeQuery("select ifnull((sum(cramt)-sum(dramt))/100000,0) from gl_recon  where acno='" + gLAcno
                                        + "'  and dt<='" + finDt + "'having sum(cramt)-sum(dramt)>0").getResultList();
                                if (!glList.isEmpty()) {
                                    Vector glV = (Vector) glList.get(0);
                                    if (glV.get(0) != null) {
                                        glAmt = Double.parseDouble(glV.get(0).toString());
                                    } else {
                                        glAmt = 0.0;
                                    }
                                }
                                /**
                                 * *************************************************************
                                 * FOR CURRENT MONTH
                                 * ************************************************************
                                 */
                                if (monQuter.equals("M")) {
                                    List glList1 = em.createNativeQuery("select ifnull((sum(cramt)-sum(dramt))/100000,0) from gl_recon  where acno='" + gLAcno
                                            + "'  and dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -1 MONTH), '%Y%m%d') "
                                            + "having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!glList1.isEmpty()) {
                                        Vector glV1 = (Vector) glList1.get(0);
                                        if (glV1.get(0) != null) {
                                            glAmt = Double.parseDouble(glV1.get(0).toString());
                                        } else {
                                            glAmt = 0.0;
                                        }
                                    }
                                } else {
                                    List glList2 = em.createNativeQuery("select ifnull((sum(cramt)-sum(dramt))/100000,0) from gl_recon  where acno='" + gLAcno
                                            + " '  and dt<=DATE_FORMAT(DATE_ADD('" + toDt + "', INTERVAL -3 MONTH), '%Y%m%d')"
                                            + " having sum(cramt)-sum(dramt)>0").getResultList();
                                    if (!glList2.isEmpty()) {
                                        Vector glV2 = (Vector) glList2.get(0);
                                        if (glV2.get(0) != null) {
                                            glAmt = Double.parseDouble(glV2.get(0).toString());
                                        } else {
                                            glAmt = 0.0;
                                        }
                                    }
                                }
                            }
                        }
                        if (col1.equalsIgnoreCase("O/S As On 01.04.") && col1.equalsIgnoreCase("Begining Of The Year") || osAmtBox == true) {
                            finBal = finBal + glAmt;
                        }
                        if (col2.equalsIgnoreCase("O/S At The End Of Prev. Mon.") || col2.equalsIgnoreCase("Last Month") || osAmtBox == true) {
                            lmBal = lmBal + glAmt;
                        }
                        if (col3.equalsIgnoreCase("O/S At The End Of Curr. Mon.") || col2.equalsIgnoreCase("Current Month") || osAmtBox == true) {
                            cmBal = cmBal + glAmt;
                        }
                        if (col4.equalsIgnoreCase("Current Month") || col4.equalsIgnoreCase("Increment/Decrement") || osAmtBox == true) {
                            cmBal = cmBal + glAmt;
                        }
                        DepositeMprPojo pojo = new DepositeMprPojo();
                        pojo.setAcctDesc(acctDesc);
                        pojo.setNoOfFinAcno(noOfFinAcno);
                        pojo.setFinBal(finBal);
                        pojo.setNoOfLmAcno(noOfLmAcno);
                        pojo.setLmBal(lmBal);
                        pojo.setNoOfcmAcno(noOfcmAcno);
                        pojo.setCmBal(cmBal);
                        pojo.setTarAcno(tarAcno);
                        pojo.setTarAmt(targetAmt);
                        pojo.setInc_ac(inc_ac);
                        pojo.setInc_amt(inc_amt);
                        returnList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
        return returnList;
    }

    public List<LoanMprsPojo> getLoanMpr(String fdt, String tdt, String cmbOption, boolean chkTarac, boolean chkTaramt, boolean chkAc1, boolean chkOsbal1, boolean chkRec1, boolean chkDisbAmt1, String cmbReptype, boolean chkDisb2, boolean chkRec2, boolean chkOsbal2, boolean chkOs1, boolean chkOs2, boolean chkLastMon, boolean chkCurMon, boolean chkAc2, boolean chkIncrDcr, String incrdcr, String brnCode, boolean chkOverdue1, boolean chkOverDue2) throws ApplicationException {
        List<LoanMprsPojo> returnList = new ArrayList<LoanMprsPojo>();
        String tableName;
        int tar_ac = 0;
        double tar_amt = 0d;
        int last_ac = 0;
        int curr_ac1 = 0;
        int curr_ac2 = 0;
        double last_amt = 0d;
        double curr_amt1 = 0d;
        double curr_amt2 = 0d;
        double last_disb = 0d;
        double curr_disb1 = 0d;
        double curr_disb2 = 0d;
        double last_OD = 0d;
        double curr_OD1 = 0d;
        double curr_OD2 = 0d;
        double last_Rec = 0d;
        double curr_Rec1 = 0d;
        double curr_Rec2 = 0d;

        int inc_ac = 0;
        double inc_amt = 0d;
        String finDt;
        String dt;
        int dt2;
        String nextDt;
        String acnat;
        try {
            List findtList = em.createNativeQuery("select mindate from yearend where '" + fdt + "' between mindate and maxdate and brncode='" + brnCode + "'").getResultList();
            if (!findtList.isEmpty()) {
                Vector fV = (Vector) findtList.get(0);
                finDt = fV.get(0).toString();
                dt = finDt.substring(0, 4);
                dt2 = Integer.parseInt(dt) + 1;
                nextDt = dt2 + "0331";
                if (cmbOption.equalsIgnoreCase("A/c Type wise")) {
                    List typeWiseList = em.createNativeQuery("select distinct acctcode,acctdesc,acctnature from accounttypemaster where acctnature in ('TL','DL','CA') order by acctnature,acctcode").getResultList();
                    if (!typeWiseList.isEmpty()) {
                        for (int nat = 0; nat < typeWiseList.size(); nat++) {
                            Vector v1 = (Vector) typeWiseList.get(nat);
                            String acctcode = v1.get(0).toString();
                            String description = v1.get(1).toString();
                            acnat = v1.get(2).toString();
                            //tableName = CbsUtil.getReconTableName(acnat);
                            if (acnat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                tableName = "ca_recon";
                            } else {
                                tableName = "loan_recon";
                            }
                            //Query For Last Year-------->OS1
                            if (chkOs1 == true) {
                                if (chkTarac == true || chkTaramt == true) {
                                    List periodlist = em.createNativeQuery("select top 1 period from target_master").getResultList();
                                    if (!periodlist.isEmpty()) {
                                        Vector v = (Vector) periodlist.get(0);
                                        String period = v.get(0).toString();
                                        if (period.equals("M")) {
                                            List tarList = em.createNativeQuery("select sum(targetacno)/100000,sum(targetamt)/100000 from target_master where acctnature='" + acnat + "'and type='L' and dtfrom='" + fdt + "' and dtto ='" + tdt + "'").getResultList();
                                            if (!tarList.isEmpty()) {
                                                v = (Vector) tarList.get(0);
                                                if (v.get(0) != null) {
                                                    tar_ac = Integer.parseInt(v.get(0).toString());
                                                } else {
                                                    tar_ac = 0;
                                                }
                                                if (v.get(1) != null) {
                                                    tar_amt = Double.parseDouble(v.get(1).toString());
                                                } else {
                                                    tar_amt = 0.0;
                                                }
                                            }
                                        } else {
                                            List tarList = em.createNativeQuery("select sum(targetacno)/100000,sum(targetamt)/100000 from target_master where acctnature='" + acnat + "'and type='L' and dtfrom='" + finDt + "' and dtto =DATE_FORMAT(DATE_ADD( '" + finDt + "', INTERVAL 1 YEAR), '%Y%m%d')").getResultList();
                                            if (!tarList.isEmpty()) {
                                                v = (Vector) tarList.get(0);
                                                if (v.get(0) != null) {
                                                    tar_ac = Integer.parseInt(v.get(0).toString());
                                                } else {
                                                    tar_ac = 00;
                                                }
                                                if (v.get(1) != null) {
                                                    tar_amt = Double.parseDouble(v.get(1).toString());
                                                } else {
                                                    tar_amt = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (chkAc1 == true || chkOsbal1 == true) {
                                    if (acnat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                        List os1list = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and rc.dt<='" + finDt + "' group by rc.acno having ((sum(cramt)-sum(dramt)))<0 ) temptab(bal)").getResultList();
                                        if (!os1list.isEmpty()) {
                                            Vector vosbal = (Vector) os1list.get(0);
                                            last_ac = Integer.parseInt(vosbal.get(0).toString());
                                            if (vosbal.get(1) != null) {
                                                last_amt = Double.parseDouble(vosbal.get(1).toString());
                                            } else {
                                                last_amt = 0.0;
                                            }
                                        }
                                    } else {
                                        List oslist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and rc.dt<='" + finDt + "' group by rc.acno) temptab(bal)").getResultList();
                                        if (!oslist.isEmpty()) {
                                            Vector vosbal = (Vector) oslist.get(0);
                                            last_ac = Integer.parseInt(vosbal.get(0).toString());
                                            if (vosbal.get(1) != null) {
                                                last_amt = Double.parseDouble(vosbal.get(1).toString());
                                            } else {
                                                last_amt = 0.0;
                                            }
                                        }
                                    }
                                }
                                if (chkDisbAmt1 == true) {
                                    List disblist = em.createNativeQuery(" SELECT sum(dramt)/100000 from " + tableName + " where acno in (select acno from accountmaster where substring(acno,3,2)='" + acctcode + "' and openingdt<= '" + finDt + "' and (closingdate='' or closingdate>'" + finDt + "' or ifnull(closingdate,'')='') ) and trantype<>8 and trandesc not between 21 and 50 and dt < '" + finDt + "' ").getResultList();
                                    if (!disblist.isEmpty()) {
                                        Vector vdisb1 = (Vector) disblist.get(0);
                                        if (vdisb1.get(0) != null) {
                                            last_disb = Double.parseDouble(vdisb1.get(0).toString());
                                        } else {
                                            last_disb = 0.0;
                                        }
                                    }
                                }
                                if (chkOverdue1 == true) {
                                    List overdue1 = em.createNativeQuery("SELECT [bnkdata].[dbo].[fn_overdue_foraccount](am.acno, '" + finDt + "') from accountmaster am where substring(acno,3,2)='" + acctcode + "' and (am.closingdate='' or am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='')").getResultList();
                                    if (!overdue1.isEmpty()) {
                                        Vector orvde = (Vector) overdue1.get(0);
                                        if (orvde.get(0) != null) {
                                            last_OD = Double.parseDouble(orvde.get(0).toString());
                                        } else {
                                            last_OD = 0.0;
                                        }
                                    }
                                }
                                if (chkRec1 == true) {
                                    List reclist = em.createNativeQuery(" SELECT sum(cramt)/100000 from " + tableName + " where acno in (select acno from accountmaster where substring(acno,3,2)='" + acctcode + "' and openingdt<='" + finDt + "' and (closingdate='' or closingdate>'" + finDt + "' or ifnull(closingdate,'')='') )and dt < '" + finDt + "' ").getResultList();
                                    if (!reclist.isEmpty()) {
                                        Vector vrec = (Vector) reclist.get(0);
                                        if (vrec.get(0) != null) {
                                            last_Rec = Double.parseDouble(vrec.get(0).toString());
                                        } else {
                                            last_Rec = 0.0;
                                        }
                                    }
                                }
                            }
                            //Query For Current Year--------->OS2
                            if (chkOs2 == true) {
                                if (chkLastMon == true) {
                                    if (chkAc2 == true || chkOsbal1 == true) {
                                        if (cmbReptype.equalsIgnoreCase("Monthly")) {
                                            if (acnat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                List oslist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<=DATE_FORMAT(Date_Add('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (closingdate>DATE_FORMAT(Date_Add('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<=DATE_FORMAT(Date_Add('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') group by rc.acno having (sum(cramt)-sum(dramt))<0 ) temptab(bal)").getResultList();
                                                if (!oslist.isEmpty()) {
                                                    Vector vosbal = (Vector) oslist.get(0);
                                                    curr_ac1 = Integer.parseInt(vosbal.get(0).toString());
                                                    if (vosbal.get(1) != null) {
                                                        curr_amt1 = Double.parseDouble(vosbal.get(1).toString());
                                                    } else {
                                                        curr_amt1 = 0.0;
                                                    }
                                                }
                                            } else {
                                                List oslist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<=DATE_FORMAT(DATE_ADD( '" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (closingdate>DATE_FORMAT(DATE_ADD( '" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') group by rc.acno ) temptab(bal)").getResultList();
                                                if (!oslist.isEmpty()) {
                                                    Vector vosbal = (Vector) oslist.get(0);
                                                    curr_ac1 = Integer.parseInt(vosbal.get(0).toString());
                                                    if (vosbal.get(1) != null) {
                                                        curr_amt1 = Double.parseDouble(vosbal.get(1).toString());
                                                    } else {
                                                        curr_amt1 = 0.0;
                                                    }
                                                }
                                            }
                                        } else {
                                            if (acnat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                List oslist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<=DATE_FORMAT(DATE_ADD( '" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') and (closingdate>DATE_FORMAT(DATE_ADD( '" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') group by rc.acno having (sum(cramt)-sum(dramt))<0 ) temptab(bal)").getResultList();
                                                if (!oslist.isEmpty()) {
                                                    Vector vosbal = (Vector) oslist.get(0);
                                                    curr_ac1 = Integer.parseInt(vosbal.get(0).toString());
                                                    if (vosbal.get(1) != null) {
                                                        curr_amt1 = Double.parseDouble(vosbal.get(1).toString());
                                                    } else {
                                                        curr_amt1 = 0.0;
                                                    }
                                                }
                                            } else {
                                                List oslist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') and (closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') group by rc.acno ) temptab(bal)").getResultList();
                                                if (!oslist.isEmpty()) {
                                                    Vector vosbal = (Vector) oslist.get(0);
                                                    curr_ac1 = Integer.parseInt(vosbal.get(0).toString());
                                                    if (vosbal.get(1) != null) {
                                                        curr_amt1 = Double.parseDouble(vosbal.get(1).toString());
                                                    } else {
                                                        curr_amt1 = 0.0;
                                                    }
                                                }
                                            }
                                        }
                                        if (chkDisb2 == true) {
                                            List dislist = em.createNativeQuery(" SELECT sum(dramt)/100000 from " + tableName + " where acno in ( select acno from accountmaster where substring(acno,3,2)='" + acctcode + "' and openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (closingdate='' or closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(closingdate,'')='') ) and trantype<>8 and trandesc not between 21 and 50 and dt < DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') ").getResultList();
                                            if (!dislist.isEmpty()) {
                                                Vector vdisb2 = (Vector) dislist.get(0);
                                                if (vdisb2.get(0) != null) {
                                                    curr_disb1 = Double.parseDouble(vdisb2.get(0).toString());
                                                } else {
                                                    curr_disb1 = 0.0;
                                                }
                                            }
                                        }
                                        /*
                                         If chkoverdue(1).Value = 1 Then
                                         * some code here
                                         */
                                        if (chkOverDue2 == true) {
                                            List ovrdueList = em.createNativeQuery("SELECT [bnkdata].[dbo].[fn_overdue_foraccount](am.acno, DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') ) from accountmaster am where substring(acno,3,2)='" + acctcode + "' and (am.closingdate='' or am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='') ").getResultList();
                                            if (!ovrdueList.isEmpty()) {
                                                Vector odueV = (Vector) ovrdueList.get(0);
                                                if (odueV.get(0) != null) {
                                                    curr_OD1 = Double.parseDouble(odueV.get(0).toString());
                                                } else {
                                                    curr_OD1 = 0.0;
                                                }
                                            }
                                        }
                                        if (chkRec2 == true) {
                                            List reclist = em.createNativeQuery(" SELECT sum(cramt)/100000 from " + tableName + " where acno in ( select acno from accountmaster where substring(acno,3,2)='" + acctcode + "' and openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (closingdate='' or closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(closingdate,'')='') ) and dt < DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')  ").getResultList();
                                            if (!reclist.isEmpty()) {
                                                Vector vrec = (Vector) reclist.get(0);
                                                if (vrec.get(0) != null) {
                                                    curr_Rec1 = Double.parseDouble(vrec.get(0).toString());
                                                } else {
                                                    curr_Rec1 = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                                //For Current Month
                                if (chkCurMon == true) {
                                    if (chkAc2 == true || chkOsbal2 == true) {
                                        if (acnat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            List cmonlist = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<='" + tdt + "' and (closingdate>'" + tdt + "' or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<='" + tdt + "' group by rc.acno having (sum(rc.cramt)-sum(rc.dramt))<0 ) temptab(bal)").getResultList();
                                            if (!cmonlist.isEmpty()) {
                                                Vector vmon = (Vector) cmonlist.get(0);
                                                curr_ac2 = Integer.parseInt(vmon.get(0).toString());
                                                if (vmon.get(1) != null) {
                                                    curr_amt2 = Double.parseDouble(vmon.get(1).toString());
                                                } else {
                                                    curr_amt2 = 0.0;
                                                }
                                            }
                                        } else {
                                            List vmon1 = em.createNativeQuery("select count(*),ifnull(sum(bal),0)/100000 from (select (sum(rc.cramt)-sum(rc.dramt)) from " + tableName + " rc,accountmaster am where rc.acno=am.acno and substring(am.acno,3,2)='" + acctcode + "' and am.openingdt<='" + tdt + "' and (closingdate>'" + tdt + "' or ifnull(am.closingdate,'')='' or closingdate='') and rc.dt<='" + tdt + "' group by rc.acno ) temptab(bal)").getResultList();
                                            if (!vmon1.isEmpty()) {
                                                Vector vmon = (Vector) vmon1.get(0);
                                                curr_ac2 = Integer.parseInt(vmon.get(0).toString());
                                                if (vmon.get(1) != null) {
                                                    curr_amt2 = Double.parseDouble(vmon.get(1).toString());
                                                } else {
                                                    curr_amt2 = 0.0;
                                                }
                                            }
                                        }
                                    }
                                    if (chkDisb2 == true) {
                                        List disblist = em.createNativeQuery(" SELECT sum(dramt)/100000 from " + tableName + " where acno in ( select acno from accountmaster where  substring(acno,3,2)='" + acctcode + "' and openingdt<='" + tdt + "' and (closingdate='' or closingdate>'" + tdt + "' or ifnull(closingdate,'')='') ) and trantype<>8 and trandesc not between 21 and 50 and dt < '" + tdt + "' ").getResultList();
                                        if (!disblist.isEmpty()) {
                                            Vector vdisb1 = (Vector) disblist.get(0);
                                            if (vdisb1.get(0) != null) {
                                                curr_disb2 = Double.parseDouble(vdisb1.get(0).toString());
                                            } else {
                                                curr_disb2 = 0.0;
                                            }
                                        }
                                    }
                                    if (chkOverDue2 = true) {
                                        List ovrdue2List = em.createNativeQuery("SELECT [bnkdata].[dbo].[fn_overdue_foraccount](am.acno, '" + tdt + "') from accountmaster am where substring(acno,3,2)='" + acctcode + "' and (am.closingdate='' or am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='') ").getResultList();
                                        if (!ovrdue2List.isEmpty()) {
                                            Vector odue2 = (Vector) ovrdue2List.get(0);
                                            if (odue2.get(0) != null) {
                                                curr_OD2 = Double.parseDouble(odue2.get(0).toString());
                                            } else {
                                                curr_OD2 = 0.0;
                                            }
                                        }
                                    }
                                    if (chkRec2 == true) {
                                        List reclist = em.createNativeQuery(" SELECT sum(cramt)/100000 from " + tableName + " where acno in ( select acno from accountmaster where substring(acno,3,2)='" + acctcode + "'and openingdt<='" + tdt + "' and (closingdate='' or closingdate>'" + tdt + "' or ifnull(closingdate,'')='') ) and dt < '" + tdt + "'  ").getResultList();
                                        if (!reclist.isEmpty()) {
                                            Vector vrec = (Vector) reclist.get(0);
                                            if (vrec.get(0) != null) {
                                                curr_Rec2 = Double.parseDouble(vrec.get(0).toString());
                                            } else {
                                                curr_Rec2 = 0.0;
                                            }
                                        }
                                    }
                                }
                            }
                            /*
                             code for Increment and decrement
                             * 
                             */
                            if (chkIncrDcr == true) {
                                if (incrdcr.equalsIgnoreCase("1")) {
                                    inc_ac = curr_ac2 - curr_ac1;
                                    inc_amt = curr_amt2 - curr_amt1;
                                } else {
                                    inc_ac = curr_ac2 - last_ac;
                                    inc_amt = curr_amt2 - last_amt;
                                }
                            }
                            LoanMprsPojo pojo = new LoanMprsPojo();
                            pojo.setDescription(description);
                            pojo.setTar_ac(tar_ac);
                            pojo.setTar_amt(tar_amt);
                            pojo.setLast_ac(last_ac);
                            pojo.setLast_amt(last_amt);
                            pojo.setLast_disb(last_disb);
                            pojo.setLast_OD(last_OD);
                            pojo.setLast_Rec(last_Rec);
                            pojo.setCurr_ac1(curr_ac1);
                            pojo.setCurr_amt1(curr_amt1);
                            pojo.setCurr_disb1(curr_disb1);
                            pojo.setCurr_OD1(curr_OD1);
                            pojo.setCurr_Rec1(curr_Rec1);
                            pojo.setCurr_ac2(curr_ac2);
                            pojo.setCurr_amt2(curr_amt2);
                            pojo.setCurr_disb2(curr_disb2);
                            pojo.setCurr_OD2(curr_OD2);
                            pojo.setCurr_Rec2(curr_Rec2);
                            pojo.setInc_ac(inc_ac);
                            pojo.setInc_amt(inc_amt);
                            returnList.add(pojo);
                        }
                    }
                } else if (cmbOption.equalsIgnoreCase("SectorWise")) {
                    List sectorlist = em.createNativeQuery("select distinct sector from vw_forsector where (sector ! = '' and sector not like '%error encountered%' and sector not like '%N/A%') order by sector").getResultList();
                    if (!sectorlist.isEmpty()) {
                        for (int j = 0; j < sectorlist.size(); j++) {
                            Vector v2 = (Vector) sectorlist.get(j);
                            String description = v2.get(0).toString();
                            if (chkOs1 == true) {
                                List vwacnolist = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.sector ='" + description + "'  and vw.acno <> ('CA') ").getResultList();
                                if (!vwacnolist.isEmpty()) {
                                    for (int v = 0; v < vwacnolist.size(); v++) {
                                        Vector vwacno = (Vector) vwacnolist.get(v);
                                        String vacno = vwacno.get(0).toString();
                                        String acctCode = fts.getAccountCode(vacno);
                                        String acNat = fts.getAcNatureByCode(acctCode);
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            tableName = "ca_recon";
                                        } else {
                                            tableName = "loan_recon";
                                        }
                                        if (chkTarac == true || chkTaramt == true) {
                                            List taracList = em.createNativeQuery("select sum(targetamt)/100000 from target_master where acctnature='" + acNat + "'and type='L' and dtfrom='" + finDt + "' and dtto ='" + nextDt + "' AND ACCTDESC='" + description + "' ").getResultList();
                                            if (!taracList.isEmpty()) {
                                                Vector v3 = (Vector) taracList.get(0);
                                                if (v3.get(0) != null) {
                                                    tar_amt = Double.parseDouble(v3.get(1).toString());
                                                } else {
                                                    tar_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkAc1 == true || chkOsbal1 == true) {
                                            List result1 = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 as bal from " + tableName + " where acno='" + vacno + "' and dt<='" + finDt + "' having sum(cramt)-sum(dramt)<0 ").getResultList();
                                            if (!result1.isEmpty()) {
                                                Vector resv = (Vector) result1.get(0);
                                                if (resv.get(0) != null) {
                                                    last_amt = Double.parseDouble(resv.get(0).toString());
                                                } else {
                                                    last_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkDisbAmt1 == true) {
                                            List result2 = em.createNativeQuery(" SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + vacno + "' and dt <='" + finDt + "'").getResultList();
                                            if (!result2.isEmpty()) {
                                                Vector resv2 = (Vector) result2.get(0);
                                                if (resv2.get(0) != null) {
                                                    last_disb = Double.parseDouble(resv2.get(0).toString());
                                                } else {
                                                    last_disb = 0.0;
                                                }
                                            }
                                        }
                                        /*
                                         If chkoverdue(0).Value = 1 Then
                                         some code left here
                                         * 
                                         */
                                        if (chkRec1 == true) {
                                            List result3 = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + vacno + "'and dt < '" + finDt + "'").getResultList();
                                            if (!result3.isEmpty()) {
                                                Vector vrec = (Vector) result3.get(0);
                                                if (vrec.get(0) != null) {
                                                    last_Rec = Double.parseDouble(vrec.get(0).toString());
                                                } else {
                                                    last_Rec = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            //*********************Query For Current Year chkos2 ***********************
                            if (chkOs2 == true) {
                                if (chkLastMon == true) {
                                    if (cmbReptype.equalsIgnoreCase("Monthly")) {
                                        List result4 = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<= DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.sector ='" + description + "'").getResultList();
                                        if (!result4.isEmpty()) {
                                            for (int i = 0; i < result4.size(); i++) {
                                                Vector vAcno = (Vector) result4.get(i);
                                                String cur_acno = vAcno.get(0).toString();
                                                String acctCode = fts.getAccountCode(cur_acno);
                                                String acNat = fts.getAcNatureByCode(acctCode);
                                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    tableName = "ca_recon";
                                                } else {
                                                    tableName = "loan_recon";
                                                }
                                                if (chkAc2 == true || chkOsbal2 == true) {
                                                    List result5 = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                    if (!result5.isEmpty()) {
                                                        Vector acv = (Vector) result5.get(0);
                                                        if (acv.get(0) != null) {
                                                            curr_amt1 = Double.parseDouble(acv.get(0).toString());
                                                        } else {
                                                            curr_amt1 = 0.0;
                                                        }
                                                    }
                                                }
                                                if (chkDisb2 == true) {
                                                    List result6 = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno + "' and trantype<>8 and trandesc not between 21 and 50 and dt <=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!result6.isEmpty()) {
                                                        Vector acv1 = (Vector) result6.get(0);
                                                        if (acv1.get(0) != null) {
                                                            curr_disb1 = Double.parseDouble(acv1.get(0).toString());
                                                        } else {
                                                            curr_disb1 = 0.0;
                                                        }
                                                    }
                                                }
                                                /*
                                                 If chkoverdue(0).Value = 1 Then
                                                 some code here 
                                                 * 
                                                 */
                                                if (chkRec2 == true) {
                                                    List result7 = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!result7.isEmpty()) {
                                                        Vector acv2 = (Vector) result7.get(0);
                                                        if (acv2.get(0) != null) {
                                                            curr_Rec1 = Double.parseDouble(acv2.get(0).toString());
                                                        } else {
                                                            curr_Rec1 = 0.0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        List result4 = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<= DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate> DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.sector ='" + description + "'").getResultList();
                                        if (!result4.isEmpty()) {
                                            for (int k = 0; k < result4.size(); k++) {
                                                Vector vAcno = (Vector) result4.get(k);
                                                String cur_acno1 = vAcno.get(0).toString();
                                                String acctCode = fts.getAccountCode(cur_acno1);
                                                String acNat = fts.getAcNatureByCode(acctCode);
                                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    tableName = "ca_recon";
                                                } else {
                                                    tableName = "loan_recon";
                                                }
                                                if (chkAc1 == true || chkOsbal2 == true) {
                                                    List result5 = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno1 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                    if (!result5.isEmpty()) {
                                                        Vector acv = (Vector) result5.get(0);
                                                        if (acv.get(0) != null) {
                                                            curr_amt1 = Double.parseDouble(acv.get(0).toString());
                                                        } else {
                                                            curr_amt1 = 0.0;
                                                        }
                                                    }
                                                }
                                                if (chkDisb2 == true) {
                                                    List result6 = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d')").getResultList();
                                                    if (!result6.isEmpty()) {
                                                        Vector acv1 = (Vector) result6.get(0);
                                                        if (acv1.get(0) != null) {
                                                            curr_disb1 = Double.parseDouble(acv1.get(0).toString());
                                                        } else {
                                                            curr_disb1 = 0.0;
                                                        }
                                                    }
                                                }
                                                /*
                                                 If chkoverdue(0).Value = 1 Then
                                                 some code here 
                                                 * 
                                                 */
                                                if (chkRec2 == true) {
                                                    List result7 = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!result7.isEmpty()) {
                                                        Vector acv2 = (Vector) result7.get(0);
                                                        if (acv2.get(0) != null) {
                                                            curr_Rec1 = Double.parseDouble(acv2.get(0).toString());
                                                        } else {
                                                            curr_Rec1 = 0.0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (chkCurMon == true) {
                                    List result8 = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<='" + tdt + "'and (am.closingdate>'" + tdt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.sector ='" + description + "' ").getResultList();
                                    if (!result8.isEmpty()) {
                                        for (int i = 0; i < result8.size(); i++) {
                                            Vector vAcno1 = (Vector) result8.get(i);
                                            String cur_acno2 = vAcno1.get(0).toString();
                                            String acctCode = fts.getAccountCode(cur_acno2);
                                            String acNat = fts.getAcNatureByCode(acctCode);
                                            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                tableName = "ca_recon";
                                            } else {
                                                tableName = "loan_recon";
                                            }
                                            if (chkAc2 == true || chkOsbal2 == true) {
                                                List curList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno2 + "' and dt<='" + tdt + "' having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                if (!curList.isEmpty()) {
                                                    Vector crV = (Vector) curList.get(0);
                                                    if (crV.get(0) != null) {
                                                        curr_amt2 = Double.parseDouble(crV.get(0).toString());
                                                    } else {
                                                        curr_amt2 = 0.0;
                                                    }
                                                }
                                            }
                                            if (chkDisb2 == true) {
                                                List chdList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and trantype<>8 and trandesc not between 21 and 50  and dt <='" + tdt + "'").getResultList();
                                                if (!chdList.isEmpty()) {
                                                    Vector vdisb1 = (Vector) chdList.get(0);
                                                    if (vdisb1.get(0) != null) {
                                                        curr_disb2 = Double.parseDouble(vdisb1.get(0).toString());
                                                    } else {
                                                        curr_disb2 = 0.0;
                                                    }
                                                }
                                            }
                                            /*
                                             If chkoverdue(1).Value = 1 Then 
                                            
                                             * 
                                             */
                                            if (chkRec2 == true) {
                                                List chr2List = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and dt<='" + tdt + "'").getResultList();
                                                if (!chr2List.isEmpty()) {
                                                    Vector vrec = (Vector) chr2List.get(0);
                                                    if (vrec.get(0) != null) {
                                                        curr_Rec2 = Double.parseDouble(vrec.get(0).toString());
                                                    } else {
                                                        curr_Rec2 = 0.0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (chkIncrDcr == true) {
                                if (incrdcr.equalsIgnoreCase("1")) {
                                    inc_ac = curr_ac2 - curr_ac1;
                                    inc_amt = curr_amt2 - curr_amt1;
                                } else {
                                    inc_ac = curr_ac2 - last_ac;
                                    inc_amt = curr_amt2 - last_amt;
                                }
                            }
                            LoanMprsPojo pojo = new LoanMprsPojo();
                            pojo.setDescription(description);
                            pojo.setTar_ac(tar_ac);
                            pojo.setTar_amt(tar_amt);
                            pojo.setLast_ac(last_ac);
                            pojo.setLast_amt(last_amt);
                            pojo.setLast_disb(last_disb);
                            pojo.setLast_OD(last_OD);
                            pojo.setLast_Rec(last_Rec);
                            pojo.setCurr_ac1(curr_ac1);
                            pojo.setCurr_amt1(curr_amt1);
                            pojo.setCurr_disb1(curr_disb1);
                            pojo.setCurr_OD1(curr_OD1);
                            pojo.setCurr_Rec1(curr_Rec1);
                            pojo.setCurr_ac2(curr_ac2);
                            pojo.setCurr_amt2(curr_amt2);
                            pojo.setCurr_disb2(curr_disb2);
                            pojo.setCurr_OD2(curr_OD2);
                            pojo.setCurr_Rec2(curr_Rec2);
                            pojo.setInc_ac(inc_ac);
                            pojo.setInc_amt(inc_amt);
                            returnList.add(pojo);
                        }
                    }
                } else if (cmbOption.equalsIgnoreCase("SchemesWise")) {
                    List schmeList = em.createNativeQuery("select distinct yojyna from vw_forsector where (YOJYNA ! = '' and YOJYNA not like '%error encountered%' and YOJYNA not like '%N/A%') order by YOJYNA").getResultList();
                    if (!schmeList.isEmpty()) {
                        for (int j = 0; j < schmeList.size(); j++) {
                            Vector v2 = (Vector) schmeList.get(j);
                            String description = v2.get(0).toString();
                            if (chkOs1 == true) {
                                List acnoList = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.YOJYNA ='" + description + "'  and vw.acno <> ('CA')").getResultList();
                                if (!acnoList.isEmpty()) {
                                    for (int v = 0; v < acnoList.size(); v++) {
                                        Vector vwacno = (Vector) acnoList.get(v);
                                        String vacno = vwacno.get(0).toString();
                                        String acctCode = fts.getAccountCode(vacno);
                                        String acNat = fts.getAcNatureByCode(acctCode);
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            tableName = "ca_recon";
                                        } else {
                                            tableName = "loan_recon";
                                        }
                                        if (chkTarac == true || chkTaramt == true) {
                                            List tarList = em.createNativeQuery("select targetamt from target_master where acctnature='" + acNat + "' and type='l' and dtfrom='" + finDt + "' and dtto ='" + nextDt + "' AND ACCTDESC='" + description + "'").getResultList();
                                            if (!tarList.isEmpty()) {
                                                Vector v3 = (Vector) tarList.get(0);
                                                if (v3.get(0) != null) {
                                                    tar_amt = Double.parseDouble(v3.get(1).toString());
                                                } else {
                                                    tar_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkAc1 == true || chkOsbal1 == true) {
                                            List chaosbList = em.createNativeQuery("SELECT sum(cramt)-sum(dramt)/100000 from " + tableName + " where acno='" + vacno + "' and dt<='" + finDt + "' having sum(cramt)-sum(dramt)<0 ").getResultList();
                                            if (!chaosbList.isEmpty()) {
                                                Vector resv = (Vector) chaosbList.get(0);
                                                if (resv.get(0) != null) {
                                                    last_amt = Double.parseDouble(resv.get(0).toString());
                                                } else {
                                                    last_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkDisbAmt1 == true) {
                                            List dsb1List = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + vacno + "' and dt <='" + finDt + "' and trantype<>8 and trandesc not between 21 and 50").getResultList();
                                            if (!dsb1List.isEmpty()) {
                                                Vector vdisb1 = (Vector) dsb1List.get(0);
                                                if (vdisb1.get(0) != null) {
                                                    last_disb = Double.parseDouble(vdisb1.get(0).toString());
                                                } else {
                                                    last_disb = 0.0;
                                                }
                                            }
                                        }
                                        /*
                                         If chkoverdue(0).Value = 1 Then
                                         * some code here
                                         * 
                                         */
                                        if (chkRec1 == true) {
                                            List rec1List = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + vacno + "' and dt < '" + finDt + "'").getResultList();
                                            if (!rec1List.isEmpty()) {
                                                Vector vrec = (Vector) rec1List.get(0);
                                                if (vrec.get(0) != null) {
                                                    last_Rec = Double.parseDouble(vrec.get(0).toString());
                                                } else {
                                                    last_Rec = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            /*
                             *********************Query For Current Year*********************** 
                             * 
                             */
                            if (chkOs2 == true) {
                                if (chkLastMon == true) {
                                    if (cmbReptype.equalsIgnoreCase("Monthly")) {
                                        List lastmonList = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.YOJYNA ='" + description + "'").getResultList();
                                        if (!lastmonList.isEmpty()) {
                                            for (int i = 0; i < lastmonList.size(); i++) {
                                                Vector vwacno = (Vector) lastmonList.get(i);
                                                String cur_acno2 = vwacno.get(0).toString();
                                                String acctCode = fts.getAccountCode(cur_acno2);
                                                String acNat = fts.getAcNatureByCode(acctCode);
                                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    tableName = "ca_recon";
                                                } else {
                                                    tableName = "loan_recon";
                                                }
                                                if (chkAc1 == true || chkOsbal1 == true) {
                                                    List chacosList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno2 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                    if (!chacosList.isEmpty()) {
                                                        Vector acv = (Vector) chacosList.get(0);
                                                        if (acv.get(0) != null) {
                                                            curr_amt1 = Double.parseDouble(acv.get(0).toString());
                                                        } else {
                                                            curr_amt1 = 0.0;
                                                        }
                                                    }
                                                }
                                                if (chkDisb2 == true) {
                                                    List disbList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!disbList.isEmpty()) {
                                                        Vector acv1 = (Vector) disbList.get(0);
                                                        if (acv1.get(0) != null) {
                                                            curr_disb1 = Double.parseDouble(acv1.get(0).toString());
                                                        } else {
                                                            curr_disb1 = 0.0;
                                                        }
                                                    }
                                                }
                                                /*
                                                 If chkoverdue(1).Value = 1 Then
                                                 * 
                                                 */
                                                if (chkRec2 == true) {
                                                    List rec3List = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!rec3List.isEmpty()) {
                                                        Vector vrec = (Vector) rec3List.get(0);
                                                        if (vrec.get(0) != null) {
                                                            curr_Rec1 = Double.parseDouble(vrec.get(0).toString());
                                                        } else {
                                                            curr_Rec1 = 0.0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        List lastmonList = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.YOJYNA ='" + description + "'").getResultList();
                                        if (!lastmonList.isEmpty()) {
                                            for (int k = 0; k < lastmonList.size(); k++) {
                                                Vector vwacno = (Vector) lastmonList.get(k);
                                                String cur_acno3 = vwacno.get(0).toString();
                                                String acctCode = fts.getAccountCode(cur_acno3);
                                                String acNat = fts.getAcNatureByCode(acctCode);
                                                if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                    tableName = "ca_recon";
                                                } else {
                                                    tableName = "loan_recon";
                                                }
                                                if (chkAc1 == true || chkOsbal1 == true) {
                                                    List chacosList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno3 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -3 MONTH), '%Y%m%d') having (sum(cramt)-sum(dramt))<0").getResultList();
                                                    if (!chacosList.isEmpty()) {
                                                        Vector acv = (Vector) chacosList.get(0);
                                                        if (acv.get(0) != null) {
                                                            curr_amt1 = Double.parseDouble(acv.get(0).toString());
                                                        } else {
                                                            curr_amt1 = 0.0;
                                                        }
                                                    }
                                                }
                                                if (chkDisb2 == true) {
                                                    List disbList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno3 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!disbList.isEmpty()) {
                                                        Vector acv1 = (Vector) disbList.get(0);
                                                        if (acv1.get(0) != null) {
                                                            curr_disb1 = Double.parseDouble(acv1.get(0).toString());
                                                        } else {
                                                            curr_disb1 = 0.0;
                                                        }
                                                    }
                                                }
                                                /*
                                                 If chkoverdue(1).Value = 1 Then
                                                 some code here left 
                                                 * 
                                                 */
                                                if (chkRec2 == true) {
                                                    List rec3List = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno3 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                    if (!rec3List.isEmpty()) {
                                                        Vector vrec = (Vector) rec3List.get(0);
                                                        if (vrec.get(0) != null) {
                                                            curr_Rec1 = Double.parseDouble(vrec.get(0).toString());
                                                        } else {
                                                            curr_Rec1 = 0.0;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (chkCurMon == true) {
                                    List curmonList = em.createNativeQuery("select vw.acno  from vw_forsector vw,accountmaster am where vw.acno=am.acno and am.openingdt<='" + tdt + "' and (am.closingdate>'" + tdt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  vw.YOJYNA ='" + description + "'").getResultList();
                                    if (!curmonList.isEmpty()) {
                                        for (int i = 0; i < curmonList.size(); i++) {
                                            Vector vAcno1 = (Vector) curmonList.get(i);
                                            String cur_acno1 = vAcno1.get(0).toString();
                                            String acctCode = fts.getAccountCode(cur_acno1);
                                            String acNat = fts.getAcNatureByCode(acctCode);
                                            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                tableName = "ca_recon";
                                            } else {
                                                tableName = "loan_recon";
                                            }
                                            if (chkAc2 == true || chkOsbal1 == true) {
                                                List curmonList1 = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno1 + "' and dt<='" + tdt + "' having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                if (!curmonList1.isEmpty()) {
                                                    Vector crV = (Vector) curmonList1.get(0);
                                                    if (crV.get(0) != null) {
                                                        curr_amt2 = Double.parseDouble(crV.get(0).toString());
                                                    } else {
                                                        curr_amt2 = 0.0;
                                                    }
                                                }
                                            }
                                            if (chkDisb2 == true) {
                                                List disbList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <='" + tdt + "'").getResultList();
                                                if (!disbList.isEmpty()) {
                                                    Vector vdisb1 = (Vector) disbList.get(0);
                                                    if (vdisb1.get(0) != null) {
                                                        curr_disb2 = Double.parseDouble(vdisb1.get(0).toString());
                                                    } else {
                                                        curr_disb2 = 0.0;
                                                    }
                                                }
                                            }
                                            /*
                                             If chkoverdue(1).Value = 1 Then 
                                             * some code here left
                                             */
                                            if (chkRec2 == true) {
                                                List chkacList = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and dt<='" + tdt + "'").getResultList();
                                                if (!chkacList.isEmpty()) {
                                                    Vector vrec = (Vector) chkacList.get(0);
                                                    if (vrec.get(0) != null) {
                                                        curr_Rec2 = Double.parseDouble(vrec.get(0).toString());
                                                    } else {
                                                        curr_Rec2 = 0.0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (chkIncrDcr == true) {
                                if (incrdcr.equalsIgnoreCase("1")) {
                                    inc_ac = curr_ac2 - curr_ac1;
                                    inc_amt = curr_amt2 - curr_amt1;
                                } else {
                                    inc_ac = curr_ac2 - last_ac;
                                    inc_amt = curr_amt2 - last_amt;
                                }
                            }
                            LoanMprsPojo pojo = new LoanMprsPojo();
                            pojo.setDescription(description);
                            pojo.setTar_ac(tar_ac);
                            pojo.setTar_amt(tar_amt);
                            pojo.setLast_ac(last_ac);
                            pojo.setLast_amt(last_amt);
                            pojo.setLast_disb(last_disb);
                            pojo.setLast_OD(last_OD);
                            pojo.setLast_Rec(last_Rec);
                            pojo.setCurr_ac1(curr_ac1);
                            pojo.setCurr_amt1(curr_amt1);
                            pojo.setCurr_disb1(curr_disb1);
                            pojo.setCurr_OD1(curr_OD1);
                            pojo.setCurr_Rec1(curr_Rec1);
                            pojo.setCurr_ac2(curr_ac2);
                            pojo.setCurr_amt2(curr_amt2);
                            pojo.setCurr_disb2(curr_disb2);
                            pojo.setCurr_OD2(curr_OD2);
                            pojo.setCurr_Rec2(curr_Rec2);
                            pojo.setInc_ac(inc_ac);
                            pojo.setInc_amt(inc_amt);

                            returnList.add(pojo);
                        }
                    }
                } else if (cmbOption.equalsIgnoreCase("SecurityWise")) {
                    List codeList = em.createNativeQuery("(select description from loan_codebook where groupcode>=910100 and groupcode<910200  ) Union (select description from loan_codebook where groupcode>=910200 and groupcode<910300 )").getResultList();
                    if (!codeList.isEmpty()) {
                        for (int i = 0; i < codeList.size(); i++) {
                            Vector descV = (Vector) codeList.get(i);
                            String description = descV.get(0).toString();
                            if (chkOs1 == true) {
                                List osList = em.createNativeQuery("select am.acno  from loansecurity ls,accountmaster am where ls.acno=am.acno and am.openingdt<='" + finDt + "' and (am.closingdate>'" + finDt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  ls.remarks like'%" + description + "%'").getResultList();
                                if (!osList.isEmpty()) {
                                    for (int l = 0; l < osList.size(); l++) {
                                        Vector acV = (Vector) osList.get(l);
                                        String acno = acV.get(0).toString();
                                        String acctCode = fts.getAccountCode(acno);
                                        String acNat = fts.getAcNatureByCode(acctCode);
                                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                            tableName = "ca_recon";
                                        } else {
                                            tableName = "loan_recon";
                                        }
                                        if (chkTarac == true || chkTaramt == true) {
                                            List tarList = em.createNativeQuery("select targetamt from target_master where acctnature='" + acNat + "' and type='l' and dtfrom='" + finDt + "' and dtto ='" + nextDt + "' AND ACCTDESC='" + description + "'").getResultList();
                                            if (!tarList.isEmpty()) {
                                                Vector v3 = (Vector) tarList.get(0);
                                                if (v3.get(0) != null) {
                                                    tar_amt = Double.parseDouble(v3.get(1).toString());
                                                } else {
                                                    tar_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkAc1 == true || chkOsbal1 == true) {
                                            List chkosList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 from " + tableName + " where acno='" + acno + "' and dt<='" + finDt + "' ").getResultList();
                                            if (!chkosList.isEmpty()) {
                                                Vector resv = (Vector) chkosList.get(0);
                                                if (resv.get(0) != null) {
                                                    last_amt = Double.parseDouble(resv.get(0).toString());
                                                } else {
                                                    last_amt = 0.0;
                                                }
                                            }
                                        }
                                        if (chkDisbAmt1 == true) {
                                            List disbLst = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + acno + "' and dt <='" + finDt + "'").getResultList();
                                            if (!disbLst.isEmpty()) {
                                                Vector vdisb1 = (Vector) disbLst.get(0);
                                                if (vdisb1.get(0) != null) {
                                                    last_disb = Double.parseDouble(vdisb1.get(0).toString());
                                                } else {
                                                    last_disb = 0.0;
                                                }
                                            }
                                        }
                                        /*
                                         If chkoverdue(0).Value = 1 Then
                                         some code here left
                                         * 
                                         */

                                        if (chkRec1 == true) {
                                            List recvList = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + acno + "' and dt < '" + finDt + "'").getResultList();
                                            if (!recvList.isEmpty()) {
                                                Vector vrec = (Vector) recvList.get(0);
                                                if (vrec.get(0) != null) {
                                                    last_Rec = Double.parseDouble(vrec.get(0).toString());
                                                } else {
                                                    last_Rec = 0.0;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            /*
                             *********************Query For Current Year*********************** 
                             * 
                             */
                            if (chkOs2 == true) {
                                if (chkLastMon == true) {
                                    List chmList = em.createNativeQuery("select am.acno  from loansecurity ls,accountmaster am where ls.acno=am.acno and am.openingdt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') and (am.closingdate>DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') or ifnull(am.closingdate,'')='' or am.closingdate='') and  ls.remarks like '%" + description + "%'").getResultList();
                                    if (!chmList.isEmpty()) {
                                        for (int m = 0; m < chmList.size(); m++) {
                                            Vector vwacno = (Vector) chmList.get(m);
                                            String cur_acno2 = vwacno.get(0).toString();
                                            String acctCode = fts.getAccountCode(cur_acno2);
                                            String acNat = fts.getAcNatureByCode(acctCode);
                                            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                tableName = "ca_recon";
                                            } else {
                                                tableName = "loan_recon";
                                            }
                                            if (chkAc2 == true || chkOsbal1 == true) {
                                                List chosList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno2 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d') having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                if (!chosList.isEmpty()) {
                                                    Vector crV = (Vector) chosList.get(0);
                                                    if (crV.get(0) != null) {
                                                        curr_amt1 = Double.parseDouble(crV.get(0).toString());
                                                    } else {
                                                        curr_amt1 = 0.0;
                                                    }
                                                }
                                            }
                                            if (chkDisb2 == true) {
                                                List disbList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                if (!disbList.isEmpty()) {
                                                    Vector vdisb1 = (Vector) disbList.get(0);
                                                    if (vdisb1.get(0) != null) {
                                                        curr_disb1 = Double.parseDouble(vdisb1.get(0).toString());
                                                    } else {
                                                        curr_disb1 = 0.0;
                                                    }
                                                }
                                            }
                                            /*
                                             If chkoverdue(1).Value = 1 Then 
                                             * 
                                             */
                                            if (chkRec2 == true) {
                                                List recList = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno2 + "' and dt<=DATE_FORMAT(DATE_ADD('" + tdt + "', INTERVAL -1 MONTH), '%Y%m%d')").getResultList();
                                                if (!recList.isEmpty()) {
                                                    Vector vrec = (Vector) recList.get(0);
                                                    if (vrec.get(0) != null) {
                                                        curr_Rec1 = Double.parseDouble(vrec.get(0).toString());
                                                    } else {
                                                        curr_Rec1 = 0.0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (chkCurMon == true) {
                                    List monList = em.createNativeQuery("select ls.acno  from loansecurity ls,accountmaster am where ls.acno=am.acno and am.openingdt<='" + tdt + "' and (am.closingdate>'" + tdt + "' or ifnull(am.closingdate,'')='' or am.closingdate='') and  ls.remarks like '%" + description + "%'").getResultList();
                                    if (!monList.isEmpty()) {
                                        for (int n = 0; n < monList.size(); n++) {
                                            Vector vAcno1 = (Vector) monList.get(n);
                                            String cur_acno1 = vAcno1.get(0).toString();
                                            String acctCode = fts.getAccountCode(cur_acno1);
                                            String acNat = fts.getAcNatureByCode(acctCode);
                                            if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                                tableName = "ca_recon";
                                            } else {
                                                tableName = "loan_recon";
                                            }
                                            if (chkAc2 == true || chkOsbal1 == true) {
                                                List chosList = em.createNativeQuery("SELECT (sum(cramt)-sum(dramt))/100000 FROM " + tableName + " where acno ='" + cur_acno1 + "' and dt<='" + tdt + "' having (sum(cramt)-sum(dramt))<0 ").getResultList();
                                                if (!chosList.isEmpty()) {
                                                    Vector crV = (Vector) chosList.get(0);
                                                    if (crV.get(0) != null) {
                                                        curr_amt2 = Double.parseDouble(crV.get(0).toString());
                                                    } else {
                                                        curr_amt2 = 0.0;
                                                    }
                                                }
                                            }
                                            if (chkDisb2 == true) {
                                                List disbList = em.createNativeQuery("SELECT sum(dramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and trantype<>8 and trandesc not between 21 and 50 and dt <='" + tdt + "'").getResultList();
                                                if (!disbList.isEmpty()) {
                                                    Vector vdisb1 = (Vector) disbList.get(0);
                                                    if (vdisb1.get(0) != null) {
                                                        curr_disb2 = Double.parseDouble(vdisb1.get(0).toString());
                                                    } else {
                                                        curr_disb2 = 0.0;

                                                    }
                                                }
                                            }
                                            /*
                                             If chkoverdue(1).Value = 1 Then
                                             * some code here left
                                             * 
                                             */
                                            if (chkRec2 == true) {
                                                List recList = em.createNativeQuery("SELECT sum(cramt)/100000 from " + tableName + " where acno ='" + cur_acno1 + "' and dt<='" + tdt + "'").getResultList();
                                                if (!recList.isEmpty()) {
                                                    Vector vrec = (Vector) recList.get(0);
                                                    if (vrec.get(0) != null) {
                                                        curr_Rec2 = Double.parseDouble(vrec.get(0).toString());
                                                    } else {
                                                        curr_Rec2 = 0.0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (chkIncrDcr == true) {
                                if (incrdcr.equalsIgnoreCase("1")) {
                                    inc_ac = curr_ac2 - curr_ac1;
                                    inc_amt = curr_amt2 - curr_amt1;
                                } else {
                                    inc_ac = curr_ac2 - last_ac;
                                    inc_amt = curr_amt2 - last_amt;
                                }
                            }
                            LoanMprsPojo pojo = new LoanMprsPojo();
                            pojo.setDescription(description);
                            pojo.setTar_ac(tar_ac);
                            pojo.setTar_amt(tar_amt);
                            pojo.setLast_ac(last_ac);
                            pojo.setLast_amt(last_amt);
                            pojo.setLast_disb(last_disb);
                            pojo.setLast_OD(last_OD);
                            pojo.setLast_Rec(last_Rec);
                            pojo.setCurr_ac1(curr_ac1);
                            pojo.setCurr_amt1(curr_amt1);
                            pojo.setCurr_disb1(curr_disb1);
                            pojo.setCurr_OD1(curr_OD1);
                            pojo.setCurr_Rec1(curr_Rec1);
                            pojo.setCurr_ac2(curr_ac2);
                            pojo.setCurr_amt2(curr_amt2);
                            pojo.setCurr_disb2(curr_disb2);
                            pojo.setCurr_OD2(curr_OD2);
                            pojo.setCurr_Rec2(curr_Rec2);
                            pojo.setInc_ac(inc_ac);
                            pojo.setInc_amt(inc_amt);
                            returnList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
        return returnList;
    }

    public List<DemandRecoveryDetailPojo> getDemandRecoveryDetail(String brncode, String acNature, String actype, String fromDt, String toDate) throws ApplicationException {
        List<DemandRecoveryDetailPojo> list = new ArrayList();
        String date = CbsUtil.dateAdd(fromDt, -1);
        List<OverdueEmiReportPojo> overdueemi = new ArrayList();
        String branchQuery = "";
        if (brncode.equalsIgnoreCase("0A")) {
            branchQuery = "";
        } else {
            branchQuery = "a.curbrcode = '" + brncode + "' and";
        }
        String acTypeQuery = "";
        if (actype.equalsIgnoreCase("0")) {
            acTypeQuery = "";
        } else {
            acTypeQuery = "and a.accttype = '" + actype + "'";
        }


        overdueemi = overDueReportFacade.getOverdueEmiReport(1, 0, "", "ALL", 0, 5000, date, brncode, "A", false, "", "N");
        Map<String, BigDecimal> map = new HashMap();
        for (int i = 0; i < overdueemi.size(); i++) {
            OverdueEmiReportPojo pojo = new OverdueEmiReportPojo();
            pojo = overdueemi.get(i);
            map.put(pojo.getAccountNumber(), pojo.getAmountOverdue());
        }
        List l = em.createNativeQuery("select aa.custid,aa.acno,aa.custname,\n"
                + "ifnull(bb.instalment,0) ,\n"
                + "ifnull(cc.bal,0),ifnull(dd.npaBal,0),ifnull(ee.RecoverAmt,0),ifnull(ee.creditDt,''),ifnull(ff.NpaRecoverAmt,0),"
                + "ifnull(ff.npaCreditDt,''),ifnull(gg.npaSpflag,1),case when ifnull(gg.npaSpflag,1)= 1 then 'A' else 'N' end as statusflag from\n"
                + "(select b.custid,a.acno,a.custname from accountmaster a,customerid b,accounttypemaster c where " + branchQuery + "\n"
                + " c.acctnature = '" + acNature + "' \n"
                + "and a.accttype = c.acctcode " + acTypeQuery
                + "and a.acno = b.acno\n"
                + "and a.OpeningDt <='" + toDate + "'\n"
                + "and (a.closingdate='' or ifnull(a.closingdate,'')='' or a.closingdate >'" + date + "')\n"
                + "order by  b.custid,a.acno)aa\n"
                + "left join\n"
                + "(select acno,ifnull(sum(installamt),0) as instalment,date_format(max(duedt),'%Y%m%d') as maxDueDt \n"
                + "from emidetails where status='Unpaid' and duedt<='" + toDate + "' group by acno)bb\n"
                + "on aa.acno = bb.acno\n"
                + "left join\n"
                + "(select acno, ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) as bal from loan_recon where auth= 'Y' and dt <='" + toDate + "' \n"
                + "group by acno)cc\n"
                + "on aa.acno = cc.acno\n"
                + "left join\n"
                + "(select acno, ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) as npaBal from npa_recon where auth= 'Y' and dt <='" + toDate + "' group by acno)dd\n"
                + "on aa.acno = dd.acno\n"
                + "left join\n"
                + "(select acno,cast(ifnull(sum(cramt),0) as decimal(25,2)) RecoverAmt,GROUP_CONCAT(date_format(dt,'%d/%m/%Y')) as creditDt from loan_recon where dt between '" + fromDt + "' and '" + toDate + "' group by acno)ee\n"
                + "on aa.acno = ee.acno\n"
                + "left join\n"
                + "(select acno,cast(ifnull(sum(cramt),0) as decimal(25,2)) NpaRecoverAmt,GROUP_CONCAT(date_format(dt,'%d/%m/%Y')) as npaCreditDt from npa_recon where dt between '" + fromDt + "' and '" + toDate + "' group by acno)ff\n"
                + "on aa.acno = ff.acno\n"
                + "left join\n"
                + "(select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast,\n"
                + "(select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa,  \n"
                + "(select acno as ano, max(effdt) as dt from accountstatus \n"
                + "where effdt  <='" + toDate + "' \n"
                + "and SPFLAG IN ('11','12','13')  group by acno) b  \n"
                + "where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN ('11','12','13')  group by aa.acno,aa.effdt) c \n"
                + "where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno )gg\n"
                + "on aa.acno = gg.npaAcno order by 11,1").getResultList();
        if (!l.isEmpty()) {
            for (int i = 0; i < l.size(); i++) {
                DemandRecoveryDetailPojo pojo = new DemandRecoveryDetailPojo();
                Vector vec = (Vector) l.get(i);
                pojo.setCustId(vec.get(0).toString());
                pojo.setAcno(vec.get(1).toString());
                pojo.setName(vec.get(2).toString());
                if (Double.valueOf(vec.get(3).toString()) == 0) {
                    List emilist = em.createNativeQuery("select ifnull(sum(installamt),0) as overDueAmt,\n"
                            + "date_format(max(duedt),'%Y%m%d') as maxDueDt from emidetails \n"
                            + "where acno='" + vec.get(1).toString() + "' and \n"
                            + "duedt<= (select date_format(min(duedt),'%Y%m%d')  from emidetails \n"
                            + "where acno='" + vec.get(1).toString() + "' )").getResultList();
                    if (!emilist.isEmpty()) {
                        Vector vector = (Vector) emilist.get(0);
                        if (Double.valueOf(vector.get(0).toString()) != 0) {
                            pojo.setEmiAmt(BigDecimal.valueOf(Math.abs(Double.valueOf(vector.get(0).toString()))));
                        } else {
                            pojo.setEmiAmt(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(4).toString()))));
                        }
                    }
                } else {
                    pojo.setEmiAmt(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(3).toString()))));
                }
                if (map.containsKey(vec.get(1).toString())) {
                    pojo.setOverdueAmt(map.get(vec.get(1).toString()));
                } else {
                    pojo.setOverdueAmt(BigDecimal.ZERO);
                }
                pojo.setOutstandingBalance(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(4).toString()))));
                pojo.setTotaldueAmt(pojo.getEmiAmt().add(pojo.getOverdueAmt()));
                pojo.setNpaBal(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(5).toString()))));
                pojo.setCreditDate(vec.get(7).toString());
                pojo.setStatus(vec.get(10).toString());
                pojo.setStatusflag(vec.get(11).toString());
                pojo.setRecoveryAmt(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(6).toString()))));
                BigDecimal remainAmt = pojo.getTotaldueAmt().subtract(pojo.getRecoveryAmt());
                if (remainAmt.doubleValue() >= 0) {
                    pojo.setRemainingBal(BigDecimal.ZERO);
                } else {
                    pojo.setRemainingBal(BigDecimal.valueOf(Math.abs(remainAmt.doubleValue())));
                }
                if (pojo.getStatus().equalsIgnoreCase("1")) {
                    pojo.setNpaRecovery(BigDecimal.ZERO);
                    pojo.setTotalDeposit(BigDecimal.ZERO);
                    pojo.setStdRecovery(BigDecimal.ZERO);
                } else {
                    pojo.setNpaRecovery(BigDecimal.valueOf(Math.abs(Double.valueOf(vec.get(5).toString()))));
                    if (pojo.getNpaBal().doubleValue() >= pojo.getNpaRecovery().doubleValue()) {
                        pojo.setStdRecovery(BigDecimal.ZERO);
                    } else {
                        pojo.setStdRecovery(pojo.getNpaBal().subtract(pojo.getNpaRecovery()));
                    }
                }
                list.add(pojo);
            }
        }
        return list;
    }

    public HashMap<String, String> getThriftAreawiseAcc(String area, String branch) throws ApplicationException {
        HashMap<String, String> map = new HashMap();
        try {
            String areaQuery = "";
            if (!area.equalsIgnoreCase("A")) {
                areaQuery = "and a.area = '" + area + "'";
            }

            List list = em.createNativeQuery("select c.Acno,a.area from share_holder a,customerid b,accountmaster c,branchmaster d "
                    + "where cast(a.CustId as unsigned)=b.custId and b.acno = c.acno and c.accttype = '53' "
                    + "and a.AlphaCode = d.AlphaCode and d.brncode = '" + branch + "' "
                    + "and a.area is not null and  a.area <> '' " + areaQuery + "").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    map.put(vec.get(0).toString(), vec.get(1).toString());
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return map;
    }
}
