package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.MprPojo;
import com.cbs.dto.UserReportTable;
import com.cbs.dto.cdci.NomineeDetailsPojo;
import com.cbs.dto.loanInsurancePojo;
import com.cbs.dto.other.BranchPerformancePojo;
import com.cbs.dto.report.AmortizationReportPojo;
import com.cbs.dto.report.AtmTransactionStatusPojo;
import com.cbs.dto.report.ExceptionReportPojo;
import com.cbs.dto.report.MinorAccountPojo;
import com.cbs.dto.report.StandingErrorPojo;
import com.cbs.dto.report.TransactionAggregatePojo;
import com.cbs.dto.report.TransactionAggregateTotalPojo;
import com.cbs.dto.report.AccountOpenIntroducerPojo;
import com.cbs.dto.report.AccountReportPojo;
import com.cbs.dto.report.AcntCloseDetailsTable;
import com.cbs.dto.report.ActiveRdReportPojo;
import com.cbs.dto.report.AgentLedgerReportPojo;
import com.cbs.dto.report.ChqBookStopTable;
import com.cbs.dto.report.OnUsReportTable;
import com.cbs.dto.report.BalanceCertificatePojo;
import com.cbs.dto.report.CashDepositAggregateAnyOneDayPojo;
import com.cbs.dto.report.ExcessTransactionPojo;
import com.cbs.dto.report.InterestCertificatePojo;
import com.cbs.dto.report.IssueChequeBookRegisterPojo;
import com.cbs.dto.report.PayOrderPojo;
import com.cbs.dto.report.StandingInstructionTodayExecutedReportPojo;
import com.cbs.dto.report.TransferBatchPojo;
import com.cbs.dto.report.CashTranRepPojo;
import com.cbs.dto.report.CheqHonouredCertificate;
import com.cbs.dto.report.CustomerEnquiryPojo;
import com.cbs.dto.report.DICGCDetailReportPojo;
import com.cbs.dto.report.DepositWithdrawlPojo;
import com.cbs.dto.report.DepositesReportPojo;
import com.cbs.dto.report.InoperativeReportPojo;
import com.cbs.dto.report.InterestFdReportPojo;
import com.cbs.dto.report.InterestReportPojo;
import com.cbs.dto.report.KycReportWriterPojo;
import com.cbs.dto.report.LoanIntCertPojo;
import com.cbs.dto.report.LongBookPojo;
import com.cbs.dto.report.MinBalanceChargesPostPojo;
import com.cbs.dto.report.PayOrderCompareByFlag;
import com.cbs.dto.report.RbiSossPojo;
import com.cbs.dto.report.RdInstallmentPojo;
import com.cbs.dto.report.ReportWriterPojo;
import com.cbs.dto.report.SignatureReportPojo;
import com.cbs.dto.report.SundrySuspencePojo;
import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.dto.report.TdNewAndReNewPojo;
import com.cbs.dto.report.TdPeriodReportPojo;
import com.cbs.dto.report.TdReceiptIntDetailPojo;
import com.cbs.dto.report.UltilityReportPojo;
import com.cbs.dto.report.payOrderCompareByDate;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.pojo.AtmCardIssueDetailPojo;
import com.cbs.dto.report.Closing_CashPojo;
import com.cbs.dto.report.IMPSTxnParameterPojo;
import com.cbs.dto.report.NominationRegisterPojo;
import com.cbs.dto.report.TransactionDetailsPojo;
import com.cbs.pojo.DDSAgentNaturePojo;
import com.cbs.pojo.FolioLedgerPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.ParseFileUtil;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;

@Stateless(mappedName = "OtherReportFacade")
public class OtherReportFacade implements OtherReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    private RbiReportFacadeRemote rbiReportFacade;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private LoanReportFacadeRemote bal;
    @EJB
    private MiscReportFacadeRemote miscRemote;
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd_1 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("ddMMyyyy");
    NumberFormat nft = new DecimalFormat("0.00");
    NumberFormat amtFormatter = new DecimalFormat("0.00");

    public List<DICGCDetailReportPojo> getDICGCReport(String date, String acType, String instCode, String brCode, String type, String accBalType, String deafAcType) throws ApplicationException {
        List<DICGCDetailReportPojo> returnList = new ArrayList<DICGCDetailReportPojo>();
        try {
            String acctCode = "";
            if (acType.equalsIgnoreCase("All")) {
                List resultList1 = em.createNativeQuery("select acctCode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "') order by acctnature,acctcode").getResultList();
                int count = 1;
                for (int i = 0; i < resultList1.size(); i++) {
                    Vector ele1 = (Vector) resultList1.get(i);
                    acctCode = ele1.get(0).toString();
                    if (type.equalsIgnoreCase("DetailExcel")) {
                        List datalist = getDICGCDetailExcelReportPojos(acctCode, date, brCode, count, accBalType, deafAcType);
                        returnList.addAll(datalist);
                        count = count + datalist.size();
                    } else {
                        returnList.addAll(getDICGCDetailReportPojos(acctCode, date, brCode));
                    }
                }
            } else {
                if (type.equalsIgnoreCase("DetailExcel")) {
                    int count = 1;
                    returnList.addAll(getDICGCDetailExcelReportPojos(acType, date, brCode, count, accBalType, deafAcType));
                } else {
                    returnList.addAll(getDICGCDetailReportPojos(acType, date, brCode));
                }
            }
            if (type.equalsIgnoreCase("Detail")) {
                return returnList;
            } else if (type.equalsIgnoreCase("DetailExcel")) {
                return returnList;
            } else if (type.equalsIgnoreCase("Summary")) {
                List<DICGCDetailReportPojo> returnListSummary = new ArrayList<DICGCDetailReportPojo>();
                int ac1 = 0, ac2 = 0, ac3 = 0, ac4 = 0, ac5 = 0, ac6 = 0, ac7 = 0, ac8 = 0, ac9 = 0, ac10 = 0, ac11 = 0, ac12 = 0;
                double bal1 = 0.0, bal2 = 0.0, bal3 = 0.0, bal4 = 0.0, bal5 = 0.0, bal6 = 0.0, bal7 = 0.0, bal8 = 0.0, bal9 = 0.0, bal10 = 0.0, bal11 = 0.0, bal12 = 0.0;
                for (DICGCDetailReportPojo pojo : returnList) {
                    String det = pojo.getDetails();
                    String cat = pojo.getCategory();
                    if (det.equalsIgnoreCase("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT")) {
                        if (cat.equalsIgnoreCase("A")) {
                            ++ac1;
                            bal1 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("B")) {
                            ++ac2;
                            bal2 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("C")) {
                            ++ac3;
                            bal3 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("D")) {
                            ++ac4;
                            bal4 += pojo.getBalance();
                        }
                    } else if (pojo.getDetails().equalsIgnoreCase("FOR STATE GOVT ACCOUNTS")) {
                        if (cat.equalsIgnoreCase("A")) {
                            ++ac5;
                            bal5 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("B")) {
                            ++ac6;
                            bal6 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("C")) {
                            ++ac7;
                            bal7 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("D")) {
                            ++ac8;
                            bal8 += pojo.getBalance();
                        }
                    } else if (pojo.getDetails().equalsIgnoreCase("FOR CENTRAL GOVT ACCOUNTS")) {
                        if (cat.equalsIgnoreCase("A")) {
                            ++ac9;
                            bal9 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("B")) {
                            ++ac10;
                            bal10 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("C")) {
                            ++ac11;
                            bal11 += pojo.getBalance();
                        } else if (cat.equalsIgnoreCase("D")) {
                            ++ac12;
                            bal12 += pojo.getBalance();
                        }
                    }
                }
                if (ac1 != 0) {
                    DICGCDetailReportPojo pojo1 = new DICGCDetailReportPojo();
                    pojo1.setAcNo(String.valueOf(ac1));
                    pojo1.setBalance(bal1);
                    pojo1.setCategory("A");
                    pojo1.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo1);
                } else {
                    DICGCDetailReportPojo pojo1 = new DICGCDetailReportPojo();
                    pojo1.setAcNo(String.valueOf(ac1));
                    pojo1.setBalance(0.0);
                    pojo1.setCategory("A");
                    pojo1.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo1);
                }
                if (ac2 != 0) {
                    DICGCDetailReportPojo pojo2 = new DICGCDetailReportPojo();
                    pojo2.setAcNo(String.valueOf(ac2));
                    pojo2.setBalance(bal2);
                    pojo2.setCategory("B");
                    pojo2.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo2);
                } else {
                    DICGCDetailReportPojo pojo2 = new DICGCDetailReportPojo();
                    pojo2.setAcNo(String.valueOf(ac2));
                    pojo2.setBalance(0.0);
                    pojo2.setCategory("B");
                    pojo2.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo2);
                }
                if (ac3 != 0) {
                    DICGCDetailReportPojo pojo3 = new DICGCDetailReportPojo();
                    pojo3.setAcNo(String.valueOf(ac3));
                    pojo3.setBalance(bal3);
                    pojo3.setCategory("C");
                    pojo3.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo3);
                } else {
                    DICGCDetailReportPojo pojo3 = new DICGCDetailReportPojo();
                    pojo3.setAcNo(String.valueOf(ac3));
                    pojo3.setBalance(0.0);
                    pojo3.setCategory("C");
                    pojo3.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo3);
                }
                if (ac4 != 0) {
                    DICGCDetailReportPojo pojo4 = new DICGCDetailReportPojo();
                    pojo4.setAcNo(String.valueOf(ac4));
                    pojo4.setBalance(bal4);
                    pojo4.setCategory("D");
                    pojo4.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo4);
                } else {
                    DICGCDetailReportPojo pojo4 = new DICGCDetailReportPojo();
                    pojo4.setAcNo(String.valueOf(ac4));
                    pojo4.setBalance(0.0);
                    pojo4.setCategory("D");
                    pojo4.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                    returnListSummary.add(pojo4);
                }
                if (ac5 != 0) {
                    DICGCDetailReportPojo pojo5 = new DICGCDetailReportPojo();
                    pojo5.setAcNo(String.valueOf(ac5));
                    pojo5.setBalance(bal5);
                    pojo5.setCategory("A");
                    pojo5.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo5);
                } else {
                    DICGCDetailReportPojo pojo5 = new DICGCDetailReportPojo();
                    pojo5.setAcNo(String.valueOf(ac5));
                    pojo5.setBalance(0.0);
                    pojo5.setCategory("A");
                    pojo5.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo5);
                }
                if (ac6 != 0) {
                    DICGCDetailReportPojo pojo6 = new DICGCDetailReportPojo();
                    pojo6.setAcNo(String.valueOf(ac6));
                    pojo6.setBalance(bal6);
                    pojo6.setCategory("B");
                    pojo6.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo6);
                } else {
                    DICGCDetailReportPojo pojo6 = new DICGCDetailReportPojo();
                    pojo6.setAcNo(String.valueOf(ac6));
                    pojo6.setBalance(0.0);
                    pojo6.setCategory("B");
                    pojo6.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo6);
                }
                if (ac7 != 0) {
                    DICGCDetailReportPojo pojo7 = new DICGCDetailReportPojo();
                    pojo7.setAcNo(String.valueOf(ac7));
                    pojo7.setBalance(bal7);
                    pojo7.setCategory("C");
                    pojo7.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo7);
                } else {
                    DICGCDetailReportPojo pojo7 = new DICGCDetailReportPojo();
                    pojo7.setAcNo(String.valueOf(ac7));
                    pojo7.setBalance(0.0);
                    pojo7.setCategory("C");
                    pojo7.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo7);
                }
                if (ac8 != 0) {
                    DICGCDetailReportPojo pojo8 = new DICGCDetailReportPojo();
                    pojo8.setAcNo(String.valueOf(ac8));
                    pojo8.setBalance(bal8);
                    pojo8.setCategory("D");
                    pojo8.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo8);
                } else {
                    DICGCDetailReportPojo pojo8 = new DICGCDetailReportPojo();
                    pojo8.setAcNo(String.valueOf(ac8));
                    pojo8.setBalance(0.0);
                    pojo8.setCategory("D");
                    pojo8.setDetails("FOR STATE GOVT ACCOUNTS");
                    returnListSummary.add(pojo8);
                }
                if (ac9 != 0) {
                    DICGCDetailReportPojo pojo9 = new DICGCDetailReportPojo();
                    pojo9.setAcNo(String.valueOf(ac9));
                    pojo9.setBalance(bal9);
                    pojo9.setCategory("A");
                    pojo9.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo9);
                } else {
                    DICGCDetailReportPojo pojo9 = new DICGCDetailReportPojo();
                    pojo9.setAcNo(String.valueOf(ac9));
                    pojo9.setBalance(0.0);
                    pojo9.setCategory("A");
                    pojo9.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo9);
                }
                if (ac10 != 0) {
                    DICGCDetailReportPojo pojo10 = new DICGCDetailReportPojo();
                    pojo10.setAcNo(String.valueOf(ac10));
                    pojo10.setBalance(bal10);
                    pojo10.setCategory("B");
                    pojo10.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo10);
                } else {
                    DICGCDetailReportPojo pojo10 = new DICGCDetailReportPojo();
                    pojo10.setAcNo(String.valueOf(ac10));
                    pojo10.setBalance(0.0);
                    pojo10.setCategory("B");
                    pojo10.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo10);
                }
                if (ac11 != 0) {
                    DICGCDetailReportPojo pojo11 = new DICGCDetailReportPojo();
                    pojo11.setAcNo(String.valueOf(ac11));
                    pojo11.setBalance(bal11);
                    pojo11.setCategory("C");
                    pojo11.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo11);
                } else {
                    DICGCDetailReportPojo pojo11 = new DICGCDetailReportPojo();
                    pojo11.setAcNo(String.valueOf(ac11));
                    pojo11.setBalance(0.0);
                    pojo11.setCategory("C");
                    pojo11.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo11);
                }
                if (ac12 != 0) {
                    DICGCDetailReportPojo pojo12 = new DICGCDetailReportPojo();
                    pojo12.setAcNo(String.valueOf(ac12));
                    pojo12.setBalance(bal12);
                    pojo12.setCategory("D");
                    pojo12.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo12);
                } else {
                    DICGCDetailReportPojo pojo12 = new DICGCDetailReportPojo();
                    pojo12.setAcNo(String.valueOf(ac12));
                    pojo12.setBalance(0.0);
                    pojo12.setCategory("D");
                    pojo12.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                    returnListSummary.add(pojo12);
                }
                return returnListSummary;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    private List<DICGCDetailReportPojo> getDICGCDetailExcelReportPojos(String acType, String date, String brCode, int count, String accBalType, String deafAcType) throws ApplicationException {
        List<DICGCDetailReportPojo> returnList = new ArrayList<DICGCDetailReportPojo>();
        String str_l;
        String Tab1;
        try {
            List tempList = new ArrayList();
            String acctNature = common.getAcNatureByAcType(acType);
            String tablename = common.getTableName(acctNature);
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Tab1 = "td_accountmaster";
                str_l = " and trantype<>27 and closeflag is null ";
            } else {
                Tab1 = "accountmaster";
                str_l = "";
            }
            String balanceCond = "";
            if (accBalType.equalsIgnoreCase("ALL")) {
                balanceCond = " >=0 ";
            } else if (accBalType.equalsIgnoreCase("WOTZERO")) {
                balanceCond = " >0 ";
            }
            String deafAcCondition = "";
            if (deafAcType.equalsIgnoreCase("WTDEAF")) {
                deafAcCondition = "  ";
            } else if (deafAcType.equalsIgnoreCase("WOTDEAF")) {
                deafAcCondition = " and a.AccStatus<>15 ";
            }

            if (brCode.equalsIgnoreCase("0A")) {
                String query = query = "select cid.custid, a.acno,date_format(a.OpeningDt,'%d-%m-%Y') as OpeningDt,a.Accttype,ccmd.custFullName,ccmd.title,\n"
                        + "ccmd.custname, ifnull(ccmd.middle_name,''), ifnull(ccmd.last_name,''), if(ccmd.CustEntityType='01',ifnull(date_format(ccmd.DateOfBirth,'%d-%m-%Y'),'01-01-1900'),'') as DateOfBirth, if(ifnull(ccmd.nationality,'')='IN','INDIAN',ifnull(ccmd.nationality,'')),\n"
                        + "ifnull(ccmd.mobilenumber,''), ifnull(ccmd.per_email,''), ifnull(ccmd.PerAddressLine1,''), ifnull(ccmd.peraddressline2,''), ifnull(ccmd.PerVillage,''), ifnull(ccmd.PerPostalCode,''), "
                        + "ifnull((select REF_DESC from cbs_ref_rec_type where REF_REC_NO='002' and REF_CODE=ccmd.PerStateCode),'') as state, ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = ccmd.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = ccmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')),\n"
                        + "ifnull(ccmd.PAN_GIRNumber,'')  ) as Pan,\n"
                        + "ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from \n"
                        + "cbs_customer_master_detail where customerid = ccmd.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = ccmd.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(ccmd.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'D'\n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail\n"
                        + "where customerid = ccmd.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, \n"
                        + "date_format(CustUpdationDate,'%Y%m%d'),ifnull(CustImage,''), ifnull(date_format(CustUpdationDate,'%Y%m%d'),'19000101'),\n"
                        + "ifnull(operationalRiskrating,'03') from customerid cid,cbs_customer_master_detail ccmd ,(\n"
                        + "select a.acno,a.OpeningDt,a.Accttype from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t\n"
                        + "where substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno   and c.dt <= '" + date + "' and \n"
                        + "a.OpeningDt <= '" + date + "' AND (a.closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  \n"
                        + " /*and a.orgncode <>18*/ " + deafAcCondition + " and substring(c.acno,3,2)='" + acType + "' " + str_l + "  \n"
                        + "group by c.acno having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2)" + balanceCond
                        + "union\n"
                        + "select a.acno,a.OpeningDt,a.Accttype from  " + Tab1 + " a,accounttypemaster t where \n"
                        + " substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.OpeningDt <= '" + date + "' AND (a.closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  \n"
                        + " /*and a.orgncode <>18 */ " + deafAcCondition + " and substring(a.acno,3,2)='" + acType + "' and  a.acno not in(select distinct acno from " + tablename + ")   \n"
                        + "group by a.acno ) as a where  cid.acno=a.acno and ccmd.customerid=cid.custid order by a.acno";
                tempList = em.createNativeQuery(query).getResultList();
            } else {
                String query = "select cid.custid, a.acno,date_format(a.OpeningDt,'%d-%m-%Y') as OpeningDt,a.Accttype,ccmd.custFullName,ccmd.title,\n"
                        + "ccmd.custname, ifnull(ccmd.middle_name,''), ifnull(ccmd.last_name,''), if(ccmd.CustEntityType='01',ifnull(date_format(ccmd.DateOfBirth,'%d-%m-%Y'),'01-01-1900'),'') as DateOfBirth, if(ifnull(ccmd.nationality,'')='IN','INDIAN',ifnull(ccmd.nationality,'')),\n"
                        + "ifnull(ccmd.mobilenumber,''), ifnull(ccmd.per_email,''), ifnull(ccmd.PerAddressLine1,''), ifnull(ccmd.peraddressline2,''), ifnull(ccmd.PerVillage,''), ifnull(ccmd.PerPostalCode,''), "
                        + "ifnull((select REF_DESC from cbs_ref_rec_type where REF_REC_NO='002' and REF_CODE=ccmd.PerStateCode),'') as state, ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail  where customerid = ccmd.customerid and legal_document = 'C' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = ccmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')),\n"
                        + "ifnull(ccmd.PAN_GIRNumber,'')  ) as Pan,\n"
                        + "ifnull(if (exists(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'E' and CustEntityType = '01'),  (select ifnull(IdentityNo,'') from \n"
                        + "cbs_customer_master_detail where customerid = ccmd.customerid and\n"
                        + "legal_document = 'E' and CustEntityType = '01'),\n"
                        + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid and aa.customerid = ccmd.customerid and aa.IdentificationType = 'E'  and bb.CustEntityType = '01')  ),\n"
                        + "ifnull(ccmd.AADHAAR_NO,'')) as AadhaarNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'B' and CustEntityType = '01'),'') as VoterIDNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = ccmd.customerid and legal_document = 'D'\n"
                        + "and CustEntityType = '01'),'') as DrivingLicenseNo,\n"
                        + "ifnull((select ifnull(IdentityNo,'') from  cbs_customer_master_detail\n"
                        + "where customerid = ccmd.customerid and legal_document = 'A' and CustEntityType = '01'),'') as PassportNo, \n"
                        + "date_format(CustUpdationDate,'%Y%m%d'),ifnull(CustImage,''), ifnull(date_format(CustUpdationDate,'%Y%m%d'),'19000101'),\n"
                        + "ifnull(operationalRiskrating,'03') from customerid cid,cbs_customer_master_detail ccmd ,(\n"
                        + "select a.acno,a.OpeningDt,a.Accttype from " + tablename + " c, " + Tab1 + " a,accounttypemaster t\n"
                        + "where substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and   a.acno=c.acno   and c.dt <= '" + date + "' and \n"
                        + "a.OpeningDt <= '" + date + "' AND (a.closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  \n"
                        + " /*and a.orgncode <>18 */ " + deafAcCondition + " and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + "  \n"
                        + "group by c.acno having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) " + balanceCond
                        + "union select a.acno,a.OpeningDt,a.Accttype from  " + Tab1 + " a,accounttypemaster t where \n"
                        + " substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.OpeningDt <= '" + date + "' AND (a.closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  \n"
                        + " /*and a.orgncode <>18 */" + deafAcCondition + "  and substring(a.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "'  \n"
                        + " and  a.acno not in(select distinct acno from " + tablename + ") group by a.acno ) as a where  cid.acno=a.acno and ccmd.customerid=cid.custid  order by a.acno";

                tempList = em.createNativeQuery(query).getResultList();
            }
            //Document List which consider fro address proof
            List<String> docMentCodeList = new ArrayList<String>();
            docMentCodeList.add("04");
            docMentCodeList.add("05");
            docMentCodeList.add("06");
            docMentCodeList.add("07");
            docMentCodeList.add("08");
            docMentCodeList.add("12");
            docMentCodeList.add("15");
            docMentCodeList.add("16");
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                pojo.setCustomerId(ele.get(0).toString().trim());
                pojo.setAcNo(ele.get(1).toString().trim());
                pojo.setAccountNo(ele.get(1).toString().trim());
                pojo.setDateOfAccopen(ele.get(2).toString().trim());
                pojo.setAccountType(acctNature/*ele.get(3).toString().trim()*/);
                pojo.setCustName(ele.get(4).toString().trim());
                pojo.setSalutation(ele.get(5).toString().trim());
                pojo.setFirstName(ele.get(6).toString().trim());
                pojo.setMiddleName(ele.get(7).toString().trim());
                pojo.setLastName(ele.get(8).toString().trim());
                pojo.setDob(ele.get(9).toString().trim());
                pojo.setNationality(ele.get(10).toString().trim());
                pojo.setMobileNo(ele.get(11).toString().trim());
                pojo.setEmailId(ele.get(12).toString().trim());
                pojo.setAdd1(ele.get(13).toString().trim());
                pojo.setAdd2(ele.get(14).toString().trim());
                pojo.setAdd3("");
                pojo.setCityVillage(ele.get(15).toString().trim());
                pojo.setPincode(ele.get(16).toString().trim());
                pojo.setState(ele.get(17).toString().trim());
                pojo.setPanNo(ele.get(18).toString().trim().toUpperCase());
                if (pojo.getPanNo().equals("") || (pojo.getPanNo().equals("NA"))
                        || !(pojo.getPanNo().length() == 10 && (ParseFileUtil.isAlphabet(pojo.getPanNo().substring(0, 5))
                        && ParseFileUtil.isNumeric(pojo.getPanNo().substring(5, 9))
                        && ParseFileUtil.isAlphabet(pojo.getPanNo().substring(9))))) {
                    pojo.setPanNo("");
                }

                pojo.setUidAadharNo(ele.get(19).toString().trim());
                pojo.setVoterIdNo(ele.get(20).toString().trim());
                pojo.setDrivingLicenNo(ele.get(21).toString().trim());
                pojo.setPassportNo(ele.get(22).toString().trim());
                pojo.setSlNo(count);
                pojo.setClaimNo("");
                count = count + 1;
                if (!ele.get(24).toString().trim().equals("")) {
                    String[] capturedDoclist = ele.get(24).toString().trim().split("\\|");
                    int docMatchCount = 0;
                    for (String docCode : capturedDoclist) {
                        docMatchCount = docMentCodeList.contains(docCode) ? docMatchCount++ : docMatchCount;
                    }
                    if (docMatchCount > 0) {
                        pojo.setAddProffVerified("YES");
                        pojo.setBankHasSupportingDocForAddrProof("YES");
                    } else {
                        pojo.setAddProffVerified("NO");
                        pojo.setBankHasSupportingDocForAddrProof("NO");
                    }
                } else {
                    pojo.setAddProffVerified("NO");
                    pojo.setBankHasSupportingDocForAddrProof("NO");
                }

                String kycUpdationDt = ele.get(25).toString();
                String riskCategory = ele.get(26).toString();

                if (kycUpdationDt.equals("19000101")) {
                    pojo.setWhetherKYCComplied("NO");
                }
                long effNoOfDays = CbsUtil.dayDiff(ymdFormat.parse(kycUpdationDt), ymdFormat.parse(date));
                long year = effNoOfDays / 365;
                if (riskCategory.equals("03") && year >= 10) {
                    pojo.setWhetherKYCComplied("NO");
                } else if (riskCategory.equals("02") && year >= 8) {
                    pojo.setWhetherKYCComplied("NO");
                } else if ((riskCategory.equals("01")) && year >= 2) {
                    pojo.setWhetherKYCComplied("NO");
                } else {
                    pojo.setWhetherKYCComplied("YES");
                }

                returnList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }

        return returnList;
    }

    private List<DICGCDetailReportPojo> getDICGCDetailReportPojos(String acType, String date, String brCode) throws ApplicationException {
        List<DICGCDetailReportPojo> returnList = new ArrayList<DICGCDetailReportPojo>();
        String str_l;
        String Tab1;
        try {
            List tempList = new ArrayList();
            String acNo;
            double balance;
            String custName;
            String acctNature = common.getAcNatureByAcType(acType);
            String tablename = common.getTableName(acctNature);
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                Tab1 = "td_accountmaster";
                str_l = " and trantype<>27 and closeflag is null ";
            } else {
                Tab1 = "accountmaster";
                str_l = "";
            }

            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from "
                        + "(select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and a.orgncode not in(18,19) "
                        + " AND (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + " AND a.OpeningDt <= '" + date + "' and substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') "
                        + "and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000 ) as x "
                        + "union ( select acno,0 as bal,custname from " + Tab1 + " ,accounttypemaster t where "
                        + "substring(acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) ='" + acType + "' and orgncode not in(18,19) "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and acno not in(select distinct acno from " + tablename + ")) ").getResultList();
            } else {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from "
                        + "(select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.acno=c.acno and c.dt <= '" + date + "' "
                        + " and a.orgncode not in(18,19) AND (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + " and a.OpeningDt <= '" + date + "' and substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') "
                        + "and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000 ) as x  "
                        + "union( select acno,0 as bal,custname from " + Tab1 + " ,accounttypemaster t where "
                        + "substring(acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) in ('" + acType + "') and orgncode not in(18,19)  "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and curbrcode='" + brCode + "' and acno not in(select distinct acno from " + tablename + ") ) ").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("A");
                pojo.setCustName(custName);
                pojo.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a, accounttypemaster t where substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and "
                        + " a.acno=c.acno and c.dt <= '" + date + "' and a.orgncode not in(18,19) and  "
                        + " a.OpeningDt <= '" + date + "' and "
                        + "(closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "'" + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.OpeningDt <= '" + date + "' and "
                        + " a.acno=c.acno and c.dt <= '" + date + "' and a.orgncode not in(18,19) and substring(c.acno,3,2)='" + acType + "' "
                        + "and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("B");
                pojo.setCustName(custName);
                pojo.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and a.OpeningDt <= '" + date + "' "
                        + "and a.orgncode not in(18,19) and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "'" + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode not in(18,19) and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("C");
                pojo.setCustName(custName);
                pojo.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and a.OpeningDt <= '" + date + "' and a.orgncode not in(18,19) "
                        + "and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and a.OpeningDt <= '" + date + "' and a.orgncode not in(18,19) and  (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='')  and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("D");
                pojo.setCustName(custName);
                pojo.setDetails("FOR ACCOUNTS EXCLUDING STATE GOVT AND CENTRAL GOVT");
                returnList.add(pojo);
            }

            //FOR STATE GOVT ACCOUNTS
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from("
                        + "select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') "
                        + "and  a.acno=c.acno and c.dt <= '" + date + "' and a.OpeningDt <= '" + date + "' and a.orgncode=19 "
                        + "and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000 ) as x "
                        + "union ("
                        + "select acno,0 as bal, custname from " + Tab1 + " ,accounttypemaster t where substring(acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) in ('" + acType + "') and orgncode=19 "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and acno not in(select distinct acno from " + tablename + "))  ").getResultList();
            } else {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from("
                        + "select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "'and  a.OpeningDt <= '" + date + "' "
                        + "and a.orgncode=19  and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000  ) as x "
                        + "union ("
                        + "select acno,0 as bal, custname from " + Tab1 + " ,accounttypemaster t where "
                        + "substring(acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) in ('" + acType + "') and orgncode=19 "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and curbrcode=" + brCode + "  and acno not in(select distinct acno from " + tablename + ") )  ").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("A");
                pojo.setCustName(custName);
                pojo.setDetails("FOR STATE GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=19 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=19  and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("B");
                pojo.setCustName(custName);
                pojo.setDetails("FOR STATE GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "' "
                        + "and a.orgncode=19 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=19 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("C");
                pojo.setCustName(custName);
                pojo.setDetails("FOR STATE GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=19 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=19  and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("D");
                pojo.setCustName(custName);
                pojo.setDetails("FOR STATE GOVT ACCOUNTS");
                returnList.add(pojo);
            }

            //FOR CENTRAL GOVT ACCOUNTS
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from("
                        + "select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') "
                        + "and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "' and a.orgncode=18 "
                        + "and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000 ) as x "
                        + "union ( "
                        + "select acno,0 as bal, custname from " + Tab1 + " ,accounttypemaster t where "
                        + "substring(acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) in ('" + acType + "') and orgncode=18 "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and acno not in(select distinct acno from " + tablename + "))  ").getResultList();
            } else {
                tempList = em.createNativeQuery("select acno,ifnull(bal,0.0),custname from( select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2) as bal, a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno "
                        + "and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "' and a.orgncode=18 "
                        + "and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') "
                        + "and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname "
                        + "having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) >= 0 "
                        + "and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 100000 ) as x "
                        + "union ( "
                        + "select acno,0 as bal, custname from " + Tab1 + " ,accounttypemaster t where "
                        + "substring(acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  OpeningDt <= '" + date + "' "
                        + "and substring(acno,3,2) in ('" + acType + "') and orgncode=18  "
                        + "and (closingdate is null or closingdate ='' or closingdate >'" + date + "') and curbrcode='" + brCode + "' and acno not in(select distinct acno from " + tablename + "))  ").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("A");
                pojo.setCustName(custName);
                pojo.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where "
                        + "substring(a.acno,3,2)=t.AcctCode and (t.CrDbFlag ='C' or t.CrDbFlag ='B') "
                        + "and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "' and a.orgncode=18 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=18 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 100000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 200000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("B");
                pojo.setCustName(custName);
                pojo.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "' "
                        + "and a.orgncode=18  and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=18 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 200000 and round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) <= 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("C");
                pojo.setCustName(custName);
                pojo.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                returnList.add(pojo);
            }
            if (brCode.equalsIgnoreCase("0A")) {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=18 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            } else {
                tempList = em.createNativeQuery("select c.acno,round(ifnull(sum(ifnull(c.cramt,0))-sum(ifnull(c.dramt,0)),0),2), a.custname "
                        + "from " + tablename + " c, " + Tab1 + " a ,accounttypemaster t where substring(a.acno,3,2)=t.AcctCode "
                        + "and (t.CrDbFlag ='C' or t.CrDbFlag ='B') and  a.acno=c.acno and c.dt <= '" + date + "' and  a.OpeningDt <= '" + date + "'"
                        + " and a.orgncode=18 and (closingdate is null or a.closingdate > '" + date + "' OR a.closingdate='') and substring(c.acno,3,2)='" + acType + "' and a.curbrcode = '" + brCode + "' " + str_l + " group by c.acno, a.custname having round(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),2) > 300000 order by c.acno").getResultList();
            }
            for (int i = 0; i < tempList.size(); i++) {
                DICGCDetailReportPojo pojo = new DICGCDetailReportPojo();
                Vector ele = (Vector) (tempList.get(i));
                acNo = ele.get(0).toString();
                balance = Double.parseDouble(ele.get(1).toString());
                custName = ele.get(2).toString();
                pojo.setAcNo(acNo);
                pojo.setBalance(balance);
                pojo.setCategory("D");
                pojo.setCustName(custName);
                pojo.setDetails("FOR CENTRAL GOVT ACCOUNTS");
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    /**
     * CONVERSION OF FUNCTION CBS_REP_ACCT_CLOSE_REG GET THE DETAILS OF CLOSE
     * ACCOUNT FOR THE PARAMETERS PASSED
     *
     * @param accountNature
     * @param fromDate
     * @param toDate
     * @param brnCode
     * @return
     */
    public List<AcntCloseDetailsTable> getAcctCloseDetails(String accountCode, String acctNature, String fromDate, String toDate, String brnCode) throws ApplicationException {
        List<AcntCloseDetailsTable> acntCloseDetails = new ArrayList<AcntCloseDetailsTable>();
        List accountDetailsList = new ArrayList();
        List bankNameAddressList = new ArrayList();
        try {
            if (brnCode.equalsIgnoreCase("0A")) {
                bankNameAddressList = em.createNativeQuery("select c.bankname,c.bankaddress from bnkadd c,branchmaster d where c.alphacode = d.alphacode and d.brncode = cast('90' as unsigned)").getResultList();
            } else {
                bankNameAddressList = em.createNativeQuery("select c.bankname,c.bankaddress from bnkadd c,branchmaster d where c.alphacode = d.alphacode and d.brncode = cast('" + brnCode + "' as unsigned)").getResultList();
                if (bankNameAddressList.isEmpty()) {
                    return acntCloseDetails;
                }
            }
            Vector bnkVec = (Vector) bankNameAddressList.get(0);
            String bnkName = bnkVec.get(0).toString();
            String bnkAddress = bnkVec.get(1).toString();
            if (accountCode.equalsIgnoreCase("ALL")) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from td_accountmaster a,acctclose_his b where a.closingdate "
                            + "between  '" + fromDate + "' and '" + toDate + "' and accstatus=9 and accttype in(select AcctCode from accounttypemaster where acctNature ='" + acctNature + "')and a.acno = b.acno order by substring(a.acno,3,2),curbrcode ").getResultList();
                } else {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from td_accountmaster a,acctclose_his b where a.closingdate "
                            + "between  '" + fromDate + "' and '" + toDate + "' and accstatus=9 and accttype in(select AcctCode from accounttypemaster where acctNature ='" + acctNature + "') and curbrcode ='" + brnCode + "' and a.acno = b.acno order by substring(a.acno,3,2),curbrcode ").getResultList();
                }
                if (!accountDetailsList.isEmpty()) {
                    for (int i = 0; i < accountDetailsList.size(); i++) {
                        AcntCloseDetailsTable acntCloseDetailsTable = new AcntCloseDetailsTable();
                        Vector vec = (Vector) accountDetailsList.get(i);
                        acntCloseDetailsTable.setAcno(vec.get(0).toString());
                        acntCloseDetailsTable.setCustName(vec.get(1).toString());
                        acntCloseDetailsTable.setEnterBy(vec.get(3).toString());
                        acntCloseDetailsTable.setAuthBy(vec.get(4).toString());
                        acntCloseDetailsTable.setDate(dmyFormat.format(ymdFormat.parse(vec.get(2).toString())));
                        acntCloseDetailsTable.setBankAddress(bnkAddress);
                        acntCloseDetailsTable.setBankName(bnkName);
                        acntCloseDetails.add(acntCloseDetailsTable);
                    }
                }
                if (brnCode.equalsIgnoreCase("0A")) {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from accountmaster a,acctclose_his b where "
                            + "a.closingdate between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and accttype in(select AcctCode from accounttypemaster where acctNature ='" + acctNature + "') and a.acno = b.acno order by substring(a.acno,3,2),curbrcode ").getResultList();
                } else {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from accountmaster a,acctclose_his b where "
                            + "a.closingdate between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and accttype in(select AcctCode from accounttypemaster where acctNature ='" + acctNature + "') and curbrcode ='" + brnCode + "' and a.acno = b.acno order by substring(a.acno,3,2),curbrcode ").getResultList();
                }

                if (!accountDetailsList.isEmpty()) {
                    for (int i = 0; i < accountDetailsList.size(); i++) {
                        AcntCloseDetailsTable acntCloseDetailsTable = new AcntCloseDetailsTable();
                        Vector vec = (Vector) accountDetailsList.get(i);
                        acntCloseDetailsTable.setAcno(vec.get(0).toString());
                        acntCloseDetailsTable.setCustName(vec.get(1).toString());
                        acntCloseDetailsTable.setEnterBy(vec.get(3).toString());
                        acntCloseDetailsTable.setAuthBy(vec.get(4).toString());
                        acntCloseDetailsTable.setDate(dmyFormat.format(ymdFormat.parse(vec.get(2).toString())));
                        acntCloseDetailsTable.setBankAddress(bnkAddress);
                        acntCloseDetailsTable.setBankName(bnkName);
                        acntCloseDetails.add(acntCloseDetailsTable);
                    }
                }
                return acntCloseDetails;
            } else if (common.getAcctNature(accountCode).equalsIgnoreCase(CbsConstant.FIXED_AC) || common.getAcctNature(accountCode).equalsIgnoreCase(CbsConstant.MS_AC)) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from td_accountmaster a,acctclose_his b where a.closingdate "
                            + "between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and substring(a.acno,3,2) ='" + accountCode + "' and a.acno = b.acno").getResultList();
                } else {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from td_accountmaster a,acctclose_his b where a.closingdate "
                            + "between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and curbrcode ='" + brnCode + "'and substring(a.acno,3,2) ='" + accountCode + "' and a.acno = b.acno").getResultList();
                }
            } else {
                if (brnCode.equalsIgnoreCase("0A")) {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from accountmaster a,acctclose_his b where "
                            + "a.closingdate between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and substring(a.acno,3,2) ='" + accountCode + "' and a.acno = b.acno").getResultList();
                } else {
                    accountDetailsList = em.createNativeQuery("select distinct a.acno,custname,a.closingdate,b.Closedby,b.AuthBy from accountmaster a,acctclose_his b where "
                            + "a.closingdate between '" + fromDate + "' and '" + toDate + "' and accstatus=9 and curbrcode ='" + brnCode + "' and substring(a.acno,3,2) ='" + accountCode + "' and a.acno = b.acno").getResultList();
                }
            }
            if (!accountDetailsList.isEmpty()) {
                for (int i = 0; i < accountDetailsList.size(); i++) {
                    AcntCloseDetailsTable acntCloseDetailsTable = new AcntCloseDetailsTable();
                    Vector vec = (Vector) accountDetailsList.get(i);
                    acntCloseDetailsTable.setAcno(vec.get(0).toString());
                    acntCloseDetailsTable.setCustName(vec.get(1).toString());
                    acntCloseDetailsTable.setEnterBy(vec.get(3).toString());
                    acntCloseDetailsTable.setAuthBy(vec.get(4).toString());
                    acntCloseDetailsTable.setDate(dmyFormat.format(ymdFormat.parse(vec.get(2).toString())));
                    acntCloseDetailsTable.setBankAddress(bnkAddress);
                    acntCloseDetailsTable.setBankName(bnkName);
                    acntCloseDetails.add(acntCloseDetailsTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acntCloseDetails;
    }

    /**
     * CBS_REP_CHEQUE_BOOK_STOP_PAYMENT_REGISTER one Test
     * parameter(0,'','20110401','20111001','06')
     *
     * @param reportType
     * @param accountWise
     * @param fromDate
     * @param toDate
     * @param brCode
     * @return
     */
    public List<ChqBookStopTable> chequeStopDetails(String reportType, String accountWise, String fromDate, String toDate, String brCode) throws ApplicationException {
        List<ChqBookStopTable> chqBookstopList = new ArrayList<ChqBookStopTable>();
        List resultSet = new ArrayList();
        String bankName = null, bankAddress = null;
        String stopPayDt, chequeDate, acNo, custName, chequeNo, amount, favouring, enterBy, authBy;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List bankDetails = new ArrayList();
            if (brCode.equalsIgnoreCase("0A")) {
                bankDetails = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode=cast('90' as unsigned) ").getResultList();
            } else {
                bankDetails = em.createNativeQuery("select b.bankname,b.bankaddress from bnkadd b,branchmaster br where b.alphacode=br.alphacode and br.brncode=cast('" + brCode + "' as unsigned) ").getResultList();
            }

            if (!bankDetails.isEmpty()) {
                Vector vec = (Vector) bankDetails.get(0);
                bankName = vec.get(0).toString();
                bankAddress = vec.get(1).toString();
            }
            String branch = "";
            if (brCode.equalsIgnoreCase("0A")) {
                branch = "";
            } else {
                branch = "and c.curbrcode='" + brCode + "' and b.brncode ='" + brCode + "'";
            }

            if (reportType.equalsIgnoreCase("0") || reportType.equalsIgnoreCase("1") || reportType.equalsIgnoreCase("2") || reportType.equalsIgnoreCase("3")) {
                if (reportType.equalsIgnoreCase("0")) {
                    resultSet = em.createNativeQuery("select date_format(a.enterydate,'%d/%m/%Y'),date_format(a.chequedt,'%d/%m/%Y'),a.acno,b.custname,a.chequeno,a.amount,a.favoring from "
                            + "chbookdetail a,customermaster b,accountmaster c where a.enterydate between date_format('" + fromDate + "','%Y%m%d') and "
                            + "date_format('" + toDate + "','%Y%m%d') and substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)=b.actype and"
                            + " a.status=9 and a.acno=c.acno " + branch + " order by a.enterydate,a.acno").getResultList();
                } else if (reportType.equalsIgnoreCase("1")) {
                    resultSet = em.createNativeQuery("select date_format(a.enterydate,'%d/%m/%Y'),date_format(a.chequedt,'%d/%m/%Y'),a.acno,b.custname,"
                            + "a.chequeno,a.amount,a.favoring from chbookdetail a,customermaster b,accountmaster c where a.enterydate between "
                            + "date_format('" + fromDate + "','%Y%m%d') and date_format('" + toDate + "','%Y%m%d') and substring(a.acno,5,6)=b.custno and "
                            + "substring(a.acno,3,2)=b.actype and a.status=9 and a.acno=c.acno " + branch + "  "
                            + "and substring(a.acno,3,2)='" + accountWise + "' order by a.enterydate,a.acno").getResultList();

                } else if (reportType.equalsIgnoreCase("2")) {
                    resultSet = em.createNativeQuery("select date_format(a.enterydate,'%d/%m/%Y'),date_format(a.chequedt,'%d/%m/%Y'),a.acno,b.custname,a.chequeno,a.amount,"
                            + "a.favoring from chbookdetail a,customermaster b,accountmaster c where a.enterydate between date_format('" + fromDate + "','%Y%m%d') "
                            + "and date_format('" + toDate + "','%Y%m%d') and substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)=b.actype  "
                            + "and a.acno=c.acno " + branch + " and a.status=1 and a.authby is not null order by "
                            + "a.enterydate,a.acno").getResultList();
                } else if (reportType.equalsIgnoreCase("3")) {
                    resultSet = em.createNativeQuery("select date_format(a.enterydate,'%d/%m/%Y'),date_format(a.chequedt,'%d/%m/%Y'),a.acno,b.custname,a.chequeno,"
                            + "a.amount,a.favoring from chbookdetail a,customermaster b,accountmaster c  where a.enterydate between "
                            + "date_format('" + fromDate + "','%Y%m%d') and date_format('" + toDate + "','%Y%m%d') and "
                            + "substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)=b.actype  and a.acno=c.acno " + branch + " and "
                            + "a.status=1 and a.authby is not null and substring(a.acno,3,2)= '" + accountWise + "' order by a.enterydate,a.acno").getResultList();
                }
                if (!resultSet.isEmpty()) {
                    for (int i = 0; i < resultSet.size(); i++) {
                        Vector vec = (Vector) resultSet.get(i);
                        stopPayDt = vec.get(0).toString();
                        chequeDate = vec.get(1).toString();
                        acNo = vec.get(2).toString();
                        custName = vec.get(3).toString();
                        chequeNo = vec.get(4).toString();
                        amount = vec.get(5).toString();
                        favouring = vec.get(6).toString();
                        ChqBookStopTable chqBookStopTable = new ChqBookStopTable();
                        chqBookStopTable.setBnkName(bankName);
                        chqBookStopTable.setBnkAddress(bankAddress);
                        chqBookStopTable.setStopPayDt(stopPayDt);
                        chqBookStopTable.setAcNo(acNo);
                        chqBookStopTable.setCustName(custName);
                        chqBookStopTable.setChequeNo(Integer.parseInt(chequeNo));
                        chqBookStopTable.setAmount(Double.parseDouble(amount));
                        chqBookStopTable.setFavouring(favouring);
                        chqBookStopTable.setChequeDate(chequeDate);
                        chqBookstopList.add(chqBookStopTable);
                    }
                }
                return chqBookstopList;
            } else if (reportType.equalsIgnoreCase("4")) {
                resultSet = em.createNativeQuery("select a.acno,b.custname,a.chequeno,date_format(a.enterydate,'%d/%m/%Y'),a.enteredby,a.authby  "
                        + "from chbookdetail a, customermaster b,accountmaster c where substring(a.acno,5,6)=b.custno and  a.status=1 "
                        + "and a.authby is not null and a.enterydate between date_format('" + fromDate + "','%Y%m%d') and "
                        + "date_format('" + toDate + "','%Y%m%d') and a.acno in(select acno from chbookdetail where status=9) and "
                        + "a.acno=c.acno " + branch + " and b.brncode = c.curbrcode and b.actype = c.accttype order by a.chequeno,a.enterydate,a.acno").getResultList();
            } else if (reportType.equalsIgnoreCase("5")) {
                resultSet = em.createNativeQuery("select a.acno,b.custname,a.chequeno,date_format(a.enterydate,'%d/%m/%Y'),"
                        + "a.enteredby,a.authby from chbookdetail a, customermaster b,accountmaster c where substring(a.acno,5,6)=b.custno and"
                        + " a.status=1 and a.authby is not null and a.enterydate between date_format('" + fromDate + "','%Y%m%d') and "
                        + "date_format('" + toDate + "','%Y%m%d') and a.acno in(select acno from chbookdetail where status=9) and "
                        + "a.acno=c.acno " + branch + " and substring(a.acno,3,2)='" + accountWise + "' and b.brncode = c.curbrcode and b.actype = c.accttype order"
                        + " by a.chequeno,a.enterydate,a.acno").getResultList();
            }
            if (!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    acNo = vec.get(0).toString();
                    custName = vec.get(1).toString();
                    chequeNo = vec.get(2).toString();
                    stopPayDt = vec.get(3).toString();
                    enterBy = vec.get(4).toString();
                    authBy = vec.get(5).toString();

                    ChqBookStopTable chqBookStopTable = new ChqBookStopTable();
                    chqBookStopTable.setBnkName(bankName);
                    chqBookStopTable.setBnkAddress(bankAddress);
                    chqBookStopTable.setStopPayDt(stopPayDt);
                    chqBookStopTable.setAcNo(acNo);
                    chqBookStopTable.setCustName(custName);
                    chqBookStopTable.setChequeNo(Integer.parseInt(chequeNo));
                    chqBookStopTable.setEnterBy(enterBy);
                    chqBookStopTable.setAuthBy(authBy);

                    chqBookstopList.add(chqBookStopTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chqBookstopList;
    }

    public List<OnUsReportTable> getOnUsReport(String date, String scrollType, String brCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException {
        List<OnUsReportTable> onUsComReportlist = new ArrayList<OnUsReportTable>();
        List<OnUsReportTable> onUsReportlist = new ArrayList<OnUsReportTable>();
        String acno, custName;
        double dramt, cramt;
        String auth, details, orgBrnId, destBrnId, branchAdd = null, tranTime;
        double trsno;
        try {
            List resultSet = em.createNativeQuery("select b.bankname from branchmaster a,bnkadd b where a.alphacode = b.alphacode and a.brncode = cast('" + brCode + "' as unsigned)").getResultList();
            if (!resultSet.isEmpty()) {
                Vector vec = (Vector) resultSet.get(0);
                branchAdd = vec.get(0).toString();
            }
            /**
             * ************** ON US REPORT FUNCTION FOR CASH CASE AND
             * TIMEALLOWED Y ****************************
             */
            if (scrollType.equalsIgnoreCase("CASH") && timeAllowed.equalsIgnoreCase("Y")) {
//                resultSet = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID FROM TOKENTABLE_CREDIT  "
//                        + "WHERE AUTH='N' AND SUBSTRING(ACNO,1,2) NOT IN ('" + brCode + "') AND TRANTYPE=0 AND DT='" + date + "' AND ORG_BRNID='" + brCode + "' AND "
//                        + "DEST_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_credit  "
                        + "where auth='n' and trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and "
                        + "dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "", onUsComReportlist, "", "");
                }
//                resultSet = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID FROM TOKENTABLE_DEBIT  WHERE AUTH='N' AND "
//                        + "SUBSTRING(ACNO,1,2) NOT IN ('" + brCode + "') AND TRANTYPE=0 AND DT='" + date + "' AND ORG_BRNID='" + brCode + "' AND DEST_BRNID NOT IN ('" + brCode + "') AND TRANTIME "
//                        + "BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_debit  where auth='n' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime "
                        + "between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon  where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "ca_recon", onUsComReportlist, "", "");
                }

                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where auth='y' and trantype=0 "
                        + "and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "loan_recon", onUsComReportlist, "", "");
                }

                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "')  and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "ddstransaction", onUsComReportlist, "", "");
                }

                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "td_recon", onUsComReportlist, "", "");
                }

                resultSet = em.createNativeQuery(" select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon "
                        + " where auth='y' and trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and "
                        + "trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "rdrecon", onUsComReportlist, "", "");
                }

                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime "
                        + "between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "recon", onUsComReportlist, "", "");
                }
            }

            /**
             * ************** ON US REPORT FUNCTION FOR CASH CASE AND
             * TIMEALLOWED N ****************************
             */
            if (scrollType.equalsIgnoreCase("CASH") && timeAllowed.equalsIgnoreCase("N")) {
//                resultSet = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID FROM TOKENTABLE_CREDIT  "
//                        + "WHERE AUTH='N' AND SUBSTRING(ACNO,1,2) NOT IN ('" + brCode + "') AND TRANTYPE=0 AND DT='" + date + "' AND ORG_BRNID='" + brCode + "' AND "
//                        + "DEST_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();

                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_credit  "
                        + "where auth='n' and trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and "
                        + "dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_debit  where auth='n' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon  where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "ca_recon", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "loan_recon", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "')  order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "ddstransaction", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "td_recon", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery(" select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon "
                        + " where auth='y' and trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "rdrecon", onUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    onUsComReportlist = dataAddition(resultSet, "recon", onUsComReportlist, "", "");
                }
            }

            /**
             * ************** ON US REPORT FUNCTION FOR TRANSFER CASE AND
             * TIMEALLOWED N ****************************
             */
            if (scrollType.equalsIgnoreCase("TRANSFER") && timeAllowed.equalsIgnoreCase("N")) {
                List trsnoList = em.createNativeQuery("select distinct(trsno) from recon_trf_d where auth='n' and trantype=2 and dt='" + date + "' and "
                        + "dest_brnid<>org_brnid and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from recon_trf_d where "
                            + "trsno=" + trsno + " and auth='n' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ca_recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "ca_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from loan_recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where "
                            + "trsno=" + trsno + " and auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "loan_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ddstransaction where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where "
                            + "trsno=" + trsno + " and  auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "ddstransaction", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from td_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where "
                            + "trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "td_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from rdrecon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon where trsno=" + trsno + " and auth='y' and "
                            + "trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "rdrecon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where "
                            + "trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
            }

            /**
             * ************** ON US REPORT FUNCTION FOR TRANSFER CASE AND
             * TIMEALLOWED Y ****************************
             */
            if (scrollType.equalsIgnoreCase("TRANSFER") && timeAllowed.equalsIgnoreCase("Y")) {
                List trsnoList = em.createNativeQuery("select distinct(trsno) from recon_trf_d where auth='n' and trantype=2 and dt='" + date + "' and "
                        + "dest_brnid<>org_brnid and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from recon_trf_d where "
                            + "trsno=" + trsno + " and auth='n' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ca_recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "ca_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from loan_recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where "
                            + "trsno=" + trsno + " and auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "loan_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ddstransaction where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where "
                            + "trsno=" + trsno + " and  auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "ddstransaction", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from td_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where "
                            + "trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "td_recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from rdrecon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid "
                        + "and org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon where trsno=" + trsno + " and auth='y' and "
                            + "trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "rdrecon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "org_brnid='" + brCode + "' and dest_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trsno").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where "
                            + "trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        onUsComReportlist = dataAddition(l1, "recon", onUsComReportlist, String.valueOf(trsno), "");
                    }
                }
            }
            for (int i = 0; i < onUsComReportlist.size(); i++) {
                onUsComReportlist.get(i).setBranchAddress(branchAdd);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return onUsComReportlist;
    }

    public List<OnUsReportTable> dataAddition(List resultSet, String table, List<OnUsReportTable> appendedList, String trsno, String chqTrsno) throws ApplicationException {
        List<OnUsReportTable> onUsReportTableList = new ArrayList<OnUsReportTable>();
        OnUsReportTable onUsReportTable = new OnUsReportTable();
        String acno, custName = null;
        double dramt, cramt;
        String auth, details, orgBrnId, destBrnId, branchAdd = null, tranTime;
        try {
            if (!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    String tempTrsnoNo = "";
                    Vector vec = (Vector) resultSet.get(i);
                    acno = vec.get(0).toString();
                    if (table.equalsIgnoreCase("ca_recon") || table.equalsIgnoreCase("loan_recon") || table.equalsIgnoreCase("ddstransaction") || table.equalsIgnoreCase("rdrecon") || table.equalsIgnoreCase("td_recon") || table.equalsIgnoreCase("recon")) {
                        dramt = Double.parseDouble(vec.get(1).toString());
                        cramt = Double.parseDouble(vec.get(2).toString());
                        auth = vec.get(3).toString();
                        details = vec.get(4).toString();
                        orgBrnId = vec.get(5).toString();
                        destBrnId = vec.get(6).toString();
                        if (!chqTrsno.equals("")) {
                            tempTrsnoNo = vec.get(7) == null ? "" : vec.get(7).toString();
                        }
                    } else {
                        custName = vec.get(1).toString();
                        dramt = Double.parseDouble(vec.get(2).toString());
                        cramt = Double.parseDouble(vec.get(3).toString());
                        auth = vec.get(4).toString();
                        details = vec.get(5).toString();
                        orgBrnId = vec.get(6).toString();
                        destBrnId = vec.get(7).toString();
                        if (!chqTrsno.equals("")) {
                            tempTrsnoNo = vec.get(8) == null ? "" : vec.get(8).toString();
                        }
                    }
                    List alphacodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + destBrnId + "'").getResultList();
                    Vector vec1 = (Vector) alphacodeList.get(0);
                    destBrnId = vec1.get(0).toString();

                    alphacodeList = em.createNativeQuery("select alphacode from branchmaster where brncode='" + orgBrnId + "'").getResultList();
                    vec1 = (Vector) alphacodeList.get(0);
                    orgBrnId = vec1.get(0).toString();

                    if (table.equalsIgnoreCase("ca_recon") || table.equalsIgnoreCase("loan_recon") || table.equalsIgnoreCase("ddstransaction") || table.equalsIgnoreCase("rdrecon") || table.equalsIgnoreCase("td_recon") || table.equalsIgnoreCase("recon")) {
                        alphacodeList = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
                        vec1 = (Vector) alphacodeList.get(0);
                        custName = vec1.get(0).toString();
                    }
                    onUsReportTable = new OnUsReportTable();
                    onUsReportTable.setAcNo(acno);
                    onUsReportTable.setCustName(custName);
                    onUsReportTable.setDrAmt(dramt);
                    onUsReportTable.setCrAmt(cramt);
                    onUsReportTable.setAuth(auth);
                    if (tempTrsnoNo.equals("")) {
                        if (trsno.equalsIgnoreCase("")) {
                            onUsReportTable.setBatchNo(0.0);
                        } else {
                            onUsReportTable.setBatchNo(Double.parseDouble(trsno));
                        }
                    } else {
                        onUsReportTable.setBatchNo(Double.parseDouble(tempTrsnoNo));
                    }
                    onUsReportTable.setDetails(details);
                    onUsReportTable.setOriginBran(orgBrnId);
                    onUsReportTable.setDestBran(destBrnId);
                    onUsReportTable.setBranchAddress(branchAdd);
                    onUsReportTable.setBranch(destBrnId);

                    appendedList.add(onUsReportTable);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return appendedList;
    }

    public List<OnUsReportTable> getOffUsReport(String date, String scrollType, String brCode, String fromTime, String toTime, String timeAllowed) throws ApplicationException {
        List<OnUsReportTable> offUsComReportlist = new ArrayList<OnUsReportTable>();
        List<OnUsReportTable> onUsReportlist = new ArrayList<OnUsReportTable>();
        String acno, custName;
        double dramt, cramt;
        String auth, details, orgBrnId, destBrnId, branchAdd = null, tranTime;
        double trsno;
        try {
            List resultSet = em.createNativeQuery("select b.bankname from branchmaster a,bnkadd b where a.alphacode = b.alphacode and a.brncode = cast('" + brCode + "' as unsigned)").getResultList();
            if (!resultSet.isEmpty()) {
                Vector vec = (Vector) resultSet.get(0);
                branchAdd = vec.get(0).toString();
            }
            /**
             * ************** OFF US REPORT FUNCTION FOR CASH CASE AND
             * TIMEALLOWED Y ****************************
             */
            if (scrollType.equalsIgnoreCase("CASH") && timeAllowed.equalsIgnoreCase("Y")) {
//                resultSet = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID FROM TOKENTABLE_CREDIT  "
//                        + "WHERE AUTH='N' AND SUBSTRING(ACNO,1,2) IN ('" + brCode + "') AND TRANTYPE=0 AND DT='" + date + "' AND DEST_BRNID='" + brCode + "' AND "
//                        + "ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();

                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_credit  "
                        + "where auth='n' and trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_debit  where auth='n' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime "
                        + "between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon  where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "ca_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "loan_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "')  and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "ddstransaction", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "td_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery(" select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon "
                        + " where auth='y' and trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and "
                        + "trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "rdrecon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime "
                        + "between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "recon", offUsComReportlist, "", "");
                }
            }

            /**
             * ************** OFF US REPORT FUNCTION FOR CASH CASE AND
             * TIMEALLOWED N ****************************
             */
            if (scrollType.equalsIgnoreCase("CASH") && timeAllowed.equalsIgnoreCase("N")) {
//                resultSet = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID FROM TOKENTABLE_CREDIT  "
//                        + "WHERE AUTH='N' AND SUBSTRING(ACNO,1,2) IN ('" + brCode + "') AND TRANTYPE=0 AND DT='" + date + "' AND DEST_BRNID='" + brCode + "' AND "
//                        + "ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();

                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_credit  "
                        + "where auth='n' and trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from tokentable_debit  where auth='n' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("Select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon  where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "ca_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "loan_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction where auth='y' and "
                        + "trantype=0 and dt='" + date + "'  and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "')  order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "ddstransaction", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "td_recon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery(" select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon "
                        + " where auth='y' and trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "rdrecon", offUsComReportlist, "", "");
                }
                resultSet = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where auth='y' and "
                        + "trantype=0 and dt='" + date + "' and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                if (!resultSet.isEmpty()) {
                    offUsComReportlist = dataAddition(resultSet, "recon", offUsComReportlist, "", "");
                }
            }

            /**
             * ************** OFF US REPORT FUNCTION FOR TRANSFER CASE AND
             * TIMEALLOWED N ****************************
             */
            if (scrollType.equalsIgnoreCase("TRANSFER") && timeAllowed.equalsIgnoreCase("Y")) {
                List trsnoList = em.createNativeQuery("select distinct(trsno) from recon_trf_d where auth='n' and  trantype=2 and dt='" + date + "' "
                        + "and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' "
                        + "order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from recon_trf_d "
                            + "where trsno=" + trsno + " and auth='n' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ca_recon where auth='y' and  "
                        + "trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime "
                        + "between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "ca_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from loan_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "loan_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ddstransaction where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction "
                            + "where trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "ddstransaction", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from td_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon"
                            + " where trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "td_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from rdrecon where auth='y' and  "
                        + "trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon where trsno=" + trsno + " and auth='y' "
                            + "and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "rdrecon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') and trantime between '" + fromTime + "' and '" + toTime + "' order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where trsno=" + trsno + " and "
                            + "auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
            }
            /**
             * ************** OFF US REPORT FUNCTION FOR TRANSFER CASE AND
             * TIMEALLOWED Y ****************************
             */
            if (scrollType.equalsIgnoreCase("TRANSFER") && timeAllowed.equalsIgnoreCase("N")) {
                List trsnoList = em.createNativeQuery("select distinct(trsno) from recon_trf_d where auth='n' and  trantype=2 and dt='" + date + "' "
                        + "and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,custname,dramt,cramt,auth,details,org_brnid,dest_brnid from recon_trf_d "
                            + "where trsno=" + trsno + " and auth='n' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ca_recon where auth='y' and  "
                        + "trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ca_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "ca_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from loan_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from loan_recon where trsno=" + trsno + " and "
                            + "auth='y' and trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "loan_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from ddstransaction where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from ddstransaction "
                            + "where trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "ddstransaction", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from td_recon where auth='y' and  trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from td_recon"
                            + " where trsno=" + trsno + " and auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "td_recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from rdrecon where auth='y' and  "
                        + "trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and dest_brnid='" + brCode + "' and "
                        + "org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from rdrecon where trsno=" + trsno + " and auth='y' "
                            + "and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "rdrecon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
                trsnoList = em.createNativeQuery("select distinct(trsno) from recon where auth='y' and trantype=2 and dt='" + date + "' and dest_brnid<>org_brnid and "
                        + "dest_brnid='" + brCode + "' and org_brnid not in ('" + brCode + "') order by trantime").getResultList();
                for (int i = 0; i < trsnoList.size(); i++) {
                    Vector vec = (Vector) trsnoList.get(i);
                    trsno = Double.parseDouble(vec.get(0).toString());
                    List l1 = em.createNativeQuery("select acno,dramt,cramt,auth,details,org_brnid,dest_brnid from recon where trsno=" + trsno + " and "
                            + "auth='y' and  trantype=2 and dt='" + date + "'").getResultList();
                    if (!l1.isEmpty()) {
                        offUsComReportlist = dataAddition(l1, "recon", offUsComReportlist, String.valueOf(trsno), "");
                    }
                }
            }
            /**
             * ************** OFF US REPORT FUNCTION FOR CLEARING CASE AND
             * TIMEALLOWED Y ****************************
             */
            if (scrollType.equalsIgnoreCase("CLEARING") && timeAllowed.equalsIgnoreCase("Y")) {
                List l1 = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM recon_clg_d WHERE AUTH='N' AND  TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM ca_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "ca_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM loan_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "loan_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM ddstransaction WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "ddstransaction", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM td_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "td_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM rdrecon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "rdrecon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') AND TRANTIME BETWEEN '" + fromTime + "' AND '" + toTime + "' ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "recon", offUsComReportlist, String.valueOf(0), "T");
                }
            }
            /**
             * ************** OFF US REPORT FUNCTION FOR CLEARING CASE AND
             * TIMEALLOWED N ****************************
             */
            if (scrollType.equalsIgnoreCase("CLEARING") && timeAllowed.equalsIgnoreCase("N")) {
                List l1 = em.createNativeQuery("SELECT ACNO,CUSTNAME,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM recon_clg_d WHERE AUTH='N' AND  TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM ca_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "ca_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM loan_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "loan_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM ddstransaction WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "ddstransaction", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM td_recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "td_recon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM rdrecon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "rdrecon", offUsComReportlist, String.valueOf(0), "T");
                }
                l1 = em.createNativeQuery("SELECT ACNO,DRAMT,CRAMT,AUTH,DETAILS,ORG_BRNID,DEST_BRNID,TRSNO FROM recon WHERE AUTH='Y' AND TRANTYPE=1 AND DT='" + date + "' AND DEST_BRNID<>ORG_BRNID AND DEST_BRNID='" + brCode + "' AND ORG_BRNID NOT IN ('" + brCode + "') ORDER BY TRANTIME").getResultList();
                if (!l1.isEmpty()) {
                    offUsComReportlist = dataAddition(l1, "recon", offUsComReportlist, String.valueOf(0), "T");
                }
            }
            for (int i = 0; i < offUsComReportlist.size(); i++) {
                offUsComReportlist.get(i).setBranchAddress(branchAdd);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return offUsComReportlist;
    }

    /**
     * ***************** cheque Honoured Certificate ************
     */
    public List<CheqHonouredCertificate> chequeHonouredCertificate(String accountNo, int chequeNo) throws ApplicationException {
        List<CheqHonouredCertificate> cheqHonouredCertificate = new ArrayList<CheqHonouredCertificate>();
        try {
            String trnTable = "", custname = "", TranType = "";
            List result = new ArrayList();
            String currentBrnCode = ftsPosting.getCurrentBrnCode(accountNo);
            List objBan = common.getBranchNameandAddress(currentBrnCode);
            String bnkName = null, bnkAddress = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctNature = common.getAcNatureByAcNo(accountNo);
            if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                trnTable = "ca_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                trnTable = "recon";
            }
            result = em.createNativeQuery("select custname from accountmaster where acno='" + accountNo + "'").getResultList();
            if (result.size() > 0) {
                Vector record = (Vector) result.get(0);
                custname = record.get(0).toString();
            }
            result = em.createNativeQuery("select ifnull(ifnull(cramt,0)- ifnull(dramt,0),0),dt,trantype,ifnull(details,' ') from " + trnTable + " where acno= '" + accountNo + "' and instno=" + chequeNo + " and auth='Y'").getResultList();

            if (result.size() > 0) {
                Vector record = (Vector) result.get(0);
                CheqHonouredCertificate balCert = new CheqHonouredCertificate();
                balCert.setAmount(Double.parseDouble(record.get(0).toString()));
                balCert.setDt(dmyFormat.format(ymd_1.parse(record.get(1).toString())));
                if (record.get(2).toString().equalsIgnoreCase("0")) {
                    TranType = "cash";
                } else if (record.get(2).toString().equalsIgnoreCase("1")) {
                    TranType = "clearing";
                } else {
                    TranType = "transfer";
                }
                balCert.setTrantype(TranType);
                balCert.setDetails(record.get(3).toString());
                balCert.setName(custname);
                balCert.setAccountNo(accountNo);
                balCert.setChqNo(chequeNo);
                balCert.setBankName(bnkName);
                balCert.setBankAdd(bnkAddress);
                cheqHonouredCertificate.add(balCert);
            }
            return cheqHonouredCertificate;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************** End
     * ******************************
     */
    /**
     * ********************** Balance Certificate ***********************
     */
    /**
     *
     * @param accountNo
     * @param dt
     * @param brnCode
     * @return
     */
    public List<BalanceCertificatePojo> balanceCertificate(String accountNo, String dt, String brnCode) throws ApplicationException {
        List<BalanceCertificatePojo> balCertificateList = new ArrayList<BalanceCertificatePojo>();
        try {
            List result = new ArrayList();
            List objBan = common.getBranchNameandAddress(brnCode);
            String bnkName = null, bnkAddress = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctNature = common.getAcNatureByAcNo(accountNo);
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result = em.createNativeQuery("select c.custno,c.title,c.custname,c.craddress,c.fathername,c.actype,a.jtname1,a.jtname2,a.jtname3,a.jtname4 from td_accountmaster a, td_customermaster c where a.acno = '" + accountNo + "' and c.custno=substring('" + accountNo + "',5,6) and c.actype=substring('" + accountNo + "',3,2) and c.agcode=substring('" + accountNo + "',11,2) and c.brncode='" + brnCode + "'").getResultList();
            } else {
                result = em.createNativeQuery("select c.custno,c.title,c.custname,c.craddress,c.fathername,c.actype,a.jtname1,a.jtname2,a.jtname3,a.jtname4 from accountmaster a, customermaster c where a.acno = '" + accountNo + "' and c.custno=substring('" + accountNo + "',5,6) and c.actype=substring('" + accountNo + "',3,2) and c.agcode=substring('" + accountNo + "',11,2) and c.brncode='" + brnCode + "'").getResultList();
            }
            if (result.size() > 0) {
                Vector record = (Vector) result.get(0);
                String custName;
                BalanceCertificatePojo balCert = new BalanceCertificatePojo();
//                if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    custName = record.get(2).toString();
//                    if (!record.get(6).toString().equalsIgnoreCase("")) {
//                        custName = custName.concat(", " + record.get(6).toString());
//                    }
//                    if (!record.get(7).toString().equalsIgnoreCase("")) {
//                        custName = custName.concat(", " + record.get(7).toString());
//                    }
//                    if (!record.get(8).toString().equalsIgnoreCase("")) {
//                        custName = custName.concat(", " + record.get(8).toString());
//                    }
//                    if (!record.get(9).toString().equalsIgnoreCase("")) {
//                        custName = custName.concat(", " + record.get(9).toString());
//                    }
//
//                } else {
//                    custName = record.get(2).toString();
//                }
                custName = record.get(2).toString();

                balCert.setCUSTNO(accountNo);
                balCert.setCUSTNAME(custName);
                balCert.setCRADDRESS(record.get(3).toString());
                balCert.setFATHERNAME(record.get(4).toString());
                balCert.setACTYPE(record.get(5).toString());

                balCert.setAMOUNT(new BigDecimal(nft.format(common.getBalanceOnDate(accountNo, dt))));
                balCert.setBNKNAME(bnkName);
                balCert.setBNKADDRESS(bnkAddress);
                balCertificateList.add(balCert);
                return balCertificateList;
            }
            return balCertificateList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************** End
     * **********************************
     */
    /**
     * ************* ISSUE CHECK BOOK REGISTER ****************
     */
    /**
     *
     * @param reportType
     * @param accountWise
     * @param fromDate
     * @param ToDate
     * @param brnCode
     * @return
     */
    public List<IssueChequeBookRegisterPojo> issueChequeBookRegister(int reportType, String accountWise, String fromDate, String ToDate, String brnCode) throws ApplicationException {
        List<IssueChequeBookRegisterPojo> issueChequeKookRegisterPojos = new ArrayList<IssueChequeBookRegisterPojo>();
        try {
            List result = new ArrayList();
            List objBan = new ArrayList();
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
            String branch = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                branch = "";
            } else {
                branch = "and a.curbrcode='" + brnCode + "'";
            }
            if (reportType == 0) {
                result = em.createNativeQuery("select ch.acno,cm.custname,ch.chbookno,ch.chnofrom,ch.chnoto,ch.issuedby,date_format(ch.issuedt,'%d/%m/%Y') "
                        + "from chbookmaster ch,customermaster cm,accountmaster a where ch.issuedt between date_format('" + fromDate + "','%Y%m%d') and date_format('" + ToDate + "','%Y%m%d') "
                        + "and substring(ch.acno,5,6)=cm.custno and substring(ch.acno,3,2)=cm.actype  and ch.acno=a.acno " + branch + " and a.curbrcode=cm.brncode order by ch.issuedt,ch.acno").getResultList();
            } else if (reportType == 1) {
                result = em.createNativeQuery("select ch.acno,cm.custname,ch.chbookno,ch.chnofrom,ch.chnoto,ch.issuedby, date_format(ch.issuedt,'%d/%m/%Y') "
                        + "from chbookmaster ch,customermaster cm,accountmaster a where ch.issuedt between date_format('" + fromDate + "','%Y%m%d') and date_format('" + ToDate + "','%Y%m%d') "
                        + "and substring(ch.acno,5,6)=cm.custno and substring(ch.acno,3,2)=cm.actype  and substring(ch.acno,3,2)='" + accountWise + "' and ch.acno=a.acno " + branch + " and a.curbrcode=cm.brncode order by ch.issuedt,ch.acno").getResultList();
            }
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector record = (Vector) result.get(i);
                    IssueChequeBookRegisterPojo balCert = new IssueChequeBookRegisterPojo();
                    balCert.setAcNo(record.get(0).toString());
                    balCert.setCustName(record.get(1).toString());
                    balCert.setChBookNo(Integer.parseInt(record.get(2).toString()));
                    balCert.setChNoFrom(Integer.parseInt(record.get(3).toString()));
                    balCert.setChNoTo(Integer.parseInt(record.get(4).toString()));
                    balCert.setIssuedBy(record.get(5).toString());
                    balCert.setIssueDt(record.get(6).toString());
                    balCert.setBnkName(bnkName);
                    balCert.setBnkAddress(bnkAddress);
                    issueChequeKookRegisterPojos.add(balCert);
                    Collections.sort(issueChequeKookRegisterPojos);
                }
            }
            return issueChequeKookRegisterPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************** End
     * **********************************
     */
    /**
     * ******************************* EXCESS TRANSACTION
     * ***************************
     */
    /**
     *
     * @param amount
     * @param trDate
     * @param brCode
     * @return
     */
    public List<ExcessTransactionPojo> excessTransaction(double amount, String trDate, String brCode) throws ApplicationException {
        List<ExcessTransactionPojo> excessTransactionPojos = new ArrayList<ExcessTransactionPojo>();
        try {
            List result = new ArrayList();
            List value = new ArrayList();
            List objBan = common.getBranchNameandAddress(brCode);
            String bnkName = null, bnkAddress = null, acType = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            value = em.createNativeQuery("select acctcode from accounttypemaster where acctcode is not null order by acctnature,acctcode").getResultList();
            if (value.size() > 0) {
                for (int i = 0; i < value.size(); i++) {
                    Vector values = (Vector) value.get(i);
                    acType = values.get(0).toString();
                    String acctNature = common.getAcNatureByAcType(acType);
                    if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from recon a,customermaster b,accountmaster c  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and c.curbrcode = b.brncode and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and a.acno=c.acno and c.curbrcode='" + brCode + "'").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from rdrecon a,customermaster b,accountmaster c  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and c.curbrcode = b.brncode and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and a.acno=c.acno and c.curbrcode='" + brCode + "'").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from ca_recon a,customermaster b,accountmaster c  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and c.curbrcode = b.brncode and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and a.acno=c.acno and c.curbrcode='" + brCode + "'").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from loan_recon a,customermaster b,accountmaster c  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and c.curbrcode = b.brncode and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and a.acno=c.acno and c.curbrcode='" + brCode + "'").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.FS_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from td_recon a,customermaster b,td_accountmaster c  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and c.curbrcode = b.brncode and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and a.acno=c.acno and c.curbrcode='" + brCode + "'").getResultList();
                    } else if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        result = em.createNativeQuery("select a.acno,b.custname,ifnull(a.cramt,0),ifnull(a.dramt,0),a.enterby,a.authby from gl_recon a,customermaster b  where substring(a.acno,5,6)=b.custno and substring(a.acno,3,2)='" + acType + "' and substring(a.acno,3,2)=b.actype and (ifnull(cramt,0) >= " + amount + "  or  ifnull(dramt,0) >= " + amount + ") and a.dt = date_format('" + trDate + "','%Y%m%d') and a.auth='y' and substring(a.acno,1,2)='" + brCode + "'").getResultList();
                    }
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            ExcessTransactionPojo balCert = new ExcessTransactionPojo();
                            balCert.setAcNo(record.get(0).toString());
                            balCert.setPartyName(record.get(1).toString());
                            balCert.setCrAmount(Double.parseDouble(record.get(2).toString()));
                            balCert.setDrAmount(Double.parseDouble(record.get(3).toString()));
                            balCert.setEnterBy(record.get(4).toString());
                            balCert.setAuthBy(record.get(5).toString());
                            balCert.setBnkName(bnkName);
                            balCert.setBnkAddress(bnkAddress);
                            excessTransactionPojos.add(balCert);
                        }
                    }
                }
            }
            return excessTransactionPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************** End
     * **********************************
     */
    /**
     * ************************* INTEREST CERTIFICATE
     * **************************
     */
    /**
     *
     * @param acType
     * @param acno
     * @param fromDt
     * @param toDt
     * @param brCode
     * @param agCode
     * @return
     */
    public List<InterestCertificatePojo> interestCertificate(String acType, String acno, String fromDt, String toDt, String brCode, String agCode) throws ApplicationException {
        List<InterestCertificatePojo> interestCertificatePojos = new ArrayList<InterestCertificatePojo>();
        try {
            String fullAcno = brCode + acType + acno + agCode;
            List result = new ArrayList();
            List objBan = common.getBranchNameandAddress(brCode);
            String bnkName = null, bnkAddress = null;
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String acctNature = common.getAcNatureByAcType(acType);
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                result = em.createNativeQuery("select ifnull(title,''),custname,ifnull(fathername,''),craddress,custno from td_customermaster where custno='" + acno + "' and actype='" + acType + "' and brncode='" + brCode + "'").getResultList();
            } else {
                result = em.createNativeQuery("select ifnull(title,''),custname,ifnull(fathername,''),craddress,custno from customermaster where custno='" + acno + "' and actype='" + acType + "' and brncode='" + brCode + "'").getResultList();
            }
            double intAmt = 0d;
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InterestCertificatePojo balCert = new InterestCertificatePojo();
                    balCert.setCUSTNAME(record.get(0).toString() + " " + record.get(1).toString());
                    balCert.setFATHERNAME(record.get(2).toString());
                    balCert.setCRADDRESS(record.get(3).toString());
                    balCert.setACCTTYPE(acType);
                    balCert.setCUSTNO(fullAcno);
//                    if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                        List intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + fullAcno + "' and dt between '" + fromDt + "' and '" + toDt + "'").getResultList();
//                        Vector ele = (Vector) intAmtList.get(0);
//                        intAmt = Double.parseDouble(ele.get(0).toString());
//                        balCert.setSUMAMOUNT(intAmt);
//                    } else {
                    //balCert.setSUMAMOUNT(getSum(acctNature, fullAcno, fromDt, toDt, brCode));
                    balCert.setSUMAMOUNT(new BigDecimal(nft.format(getSum(acctNature, fullAcno, fromDt, toDt, brCode))));
                    //}
                    balCert.setFROMDATE(dmyFormat.format(ymdFormat.parse(fromDt)));
                    balCert.setTODATE(dmyFormat.format(ymdFormat.parse(toDt)));
                    balCert.setBANKNAME(bnkName);
                    balCert.setBANKADDRESS(bnkAddress);
                    balCert.setFromYear(fromDt.substring(0, 4));
                    balCert.setToYear(toDt.substring(0, 4));
                    if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        balCert.setStatement("recieved");
                    } else {
                        balCert.setStatement("paid");
                    }
                    interestCertificatePojos.add(balCert);
                }
            }
            return interestCertificatePojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private double getSum(String acctNature, String fullAcno, String fromDt, String toDt, String brCode) {
        double balance = 0;
        try {
            if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(cramt),0) from recon a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(cramt),0) from ddstransaction a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from td_recon a,td_accountmaster b where a.acno='" + fullAcno + "'  and a.dt between '" + fromDt + "' and '" + toDt + "' and (a.intflag='i' or a.trantype='8') and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(cramt),0) from rdrecon a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from gl_recon where acno='" + fullAcno + "' and dt between '" + fromDt + "' and '" + toDt + "' and trantype='8' and auth='y' and substring(acno,1,2) = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from of_recon a,td_accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                List balanceList = em.createNativeQuery("select ifnull(sum(dramt),0) from ca_recon a,accountmaster b where a.acno='" + fullAcno + "' and a.dt between '" + fromDt + "' and '" + toDt + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + brCode + "'").getResultList();
                Vector balanceVect = (Vector) balanceList.get(0);
                balance = Double.parseDouble(balanceVect.get(0).toString());
                return balance;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return balance;
    }

    /**
     * ****************************************** End
     * *************************************
     */
    /**
     * *********************** ACCOUNT OPEN INTRODUCER
     * *************************
     */
    /**
     *
     * @param checkBox
     * @param acountOwner
     * @param acType
     * @param acnoText
     * @param fromDt
     * @param toDt
     * @param brCode
     * @param agCode
     * @return
     */
    public List<AccountOpenIntroducerPojo> accountOpenIntroducer(String checkBox, String acountOwner, String acType, String acnoText, String fromDt, String toDt, String brCode, String agCode) throws ApplicationException {
        List<AccountOpenIntroducerPojo> accountOpenIntroducerPojos = new ArrayList<AccountOpenIntroducerPojo>();
        try {
            checkBox = checkBox == null ? "" : checkBox;

            List result = new ArrayList();
            List introducerList = new ArrayList();
            List objBan = common.getBranchNameandAddress(brCode);
            String bnkName = null, acctNature = "", bnkAddress = null, introducerName = "", introducerAddress = "", custName = "", accountNum = "", acctType = "", custNo = "", customerAddress = "", introaccno = "";
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString() + " - " + objBan.get(2).toString();
            }
            String custAdd = "";
            if (checkBox.equalsIgnoreCase("AC")) {
                String fullAcno = brCode + acType + acnoText + agCode;
                String cityState = "", postalCode = "", mob = "";
                acctNature = common.getAcNatureByAcType(acType);
                if (acountOwner.equalsIgnoreCase("Owner of Account")) {
                    if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        result = em.createNativeQuery("select  actype,custname,craddress from td_customermaster"
                                + " where custno= '" + acnoText + "' and actype= '" + acType + "' and brncode = '" + brCode + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select  actype,custname,craddress from customermaster "
                                + "where custno= '" + acnoText + "'  and actype='" + acType + "' and brncode = '" + brCode + "'").getResultList();
                    }
                    List custList = em.createNativeQuery("select peraddressline1,peraddressline2,upper(concat(pervillage,' ,',PerDistrict,' ,',rf2.ref_desc)),"
                            + "ifnull(perpostalcode,''),ifnull(mobilenumber,'') from cbs_customer_master_detail cb,cbs_ref_rec_type rf2 "
                            + "where customerid in(select custid from customerid where acno = '" + fullAcno + "') "
                            + "and cb.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'").getResultList();
                    if (!custList.isEmpty()) {
                        Vector custVector = (Vector) custList.get(0);
                        custAdd = custVector.get(0).toString().trim() + " " + custVector.get(1).toString().trim();
                        cityState = custVector.get(2).toString();
                        postalCode = custVector.get(3).toString();
                        mob = custVector.get(4).toString();
                    }
                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            AccountOpenIntroducerPojo balCert = new AccountOpenIntroducerPojo();
                            balCert.setACNO(fullAcno);
                            balCert.setACCTTYPE(record.get(0).toString());
                            balCert.setCUSTNAME(record.get(1).toString());
                            balCert.setCRADDRESS(custAdd);
                            balCert.setCityState(cityState);
                            balCert.setPostalCode(postalCode);
                            balCert.setMobile(mob);
                            balCert.setINTRONAME("");
                            balCert.setINTROADD("");
                            balCert.setCUSTNO("");
                            balCert.setINTRODUCERNUM("");
                            balCert.setBANKNAME(bnkName);
                            balCert.setBANKADDRESS(bnkAddress);
                            accountOpenIntroducerPojos.add(balCert);
                        }
                    }
                } else if (acountOwner.equalsIgnoreCase("Introducer Of Account")) {
                    String introAcno = "";
                    if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        //String introAcno = "";
                        List introList = em.createNativeQuery("select introaccno from accountmaster where acno='" + fullAcno + "' union select introaccno from td_accountmaster where acno='" + fullAcno + "'").getResultList();
                        if (!introList.isEmpty()) {
                            Vector introVector = (Vector) introList.get(0);
                            introAcno = introVector.get(0).toString();
                        }
                        result = em.createNativeQuery("select Actype,custname,craddress from td_customermaster "
                                + "where custno= '" + acnoText + "' and actype= '" + acType + "' and brncode = '" + brCode + "'").getResultList();
                        introducerList = em.createNativeQuery("select custname,craddress from td_customermaster "
                                + "where custno=(select substring(introaccno,5,6) from td_accountmaster "
                                + "where acno='" + fullAcno + "')  and brncode = '" + introAcno.substring(0, 2) + "' and actype= '" + introAcno.substring(2, 4) + "'").getResultList();
                        if (introducerList.size() > 0) {
                            Vector intro = (Vector) introducerList.get(0);
                            introducerName = intro.get(0).toString();
                            introducerAddress = intro.get(1).toString();
                        }
                    } else {
                        // String introAcno = "";
                        List introList = em.createNativeQuery("select introaccno from accountmaster where acno='" + fullAcno + "' union select introaccno from td_accountmaster where acno='" + fullAcno + "'").getResultList();
                        if (!introList.isEmpty()) {
                            Vector introVector = (Vector) introList.get(0);
                            introAcno = introVector.get(0).toString();
                        }
                        result = em.createNativeQuery("select Actype,custname,craddress from customermaster "
                                + "where custno= '" + acnoText + "'  and actype='" + acType + "' and brncode = '" + brCode + "'").getResultList();
                        introducerList = em.createNativeQuery("select custname,craddress from customermaster "
                                + "where custno=(select substring(introaccno,5,6) from accountmaster "
                                + "where acno='" + fullAcno + "')  and brncode = '" + introAcno.substring(0, 2) + "' and actype='" + introAcno.substring(2, 4) + "'").getResultList();
                        if (introducerList.size() > 0) {
                            Vector intro = (Vector) introducerList.get(0);
                            introducerName = intro.get(0).toString();
                            introducerAddress = intro.get(1).toString();
                        }
                    }
                    List custList = em.createNativeQuery("select peraddressline1,peraddressline2,upper(concat(pervillage,' ,',PerDistrict,' ,',rf2.ref_desc)),"
                            + "ifnull(perpostalcode,''),ifnull(mobilenumber,'') from cbs_customer_master_detail cb,cbs_ref_rec_type rf2 "
                            + "where customerid in(select custid from customerid where acno = '" + introAcno + "') "
                            + "and cb.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'").getResultList();
                    if (!custList.isEmpty()) {
                        Vector custVector = (Vector) custList.get(0);
                        custAdd = custVector.get(0).toString().trim() + " " + custVector.get(1).toString().trim();
                        cityState = custVector.get(2).toString();
                        postalCode = custVector.get(3).toString();
                        mob = custVector.get(4).toString();
                    }

                    if (result.size() > 0) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector record = (Vector) result.get(j);
                            AccountOpenIntroducerPojo balCert = new AccountOpenIntroducerPojo();
                            balCert.setACNO(fullAcno);
                            balCert.setACCTTYPE(record.get(0).toString());
                            balCert.setCUSTNAME(record.get(1).toString());
                            balCert.setCRADDRESS(custAdd);
                            balCert.setINTRONAME(introducerName);
                            balCert.setINTROADD(introducerAddress);
                            balCert.setCityState(cityState);
                            balCert.setPostalCode(postalCode);
                            balCert.setMobile(mob);
                            balCert.setCUSTNO("");
                            balCert.setINTRODUCERNUM("");
                            balCert.setBANKNAME(bnkName);
                            balCert.setBANKADDRESS(bnkAddress);
                            accountOpenIntroducerPojos.add(balCert);
                        }
                    }
                }

            } else {
                String cityState = "", postalCode = "", mob = "";
                if (acountOwner.equalsIgnoreCase("Owner of Account")) {
                    if (acType.equalsIgnoreCase("A")) {
                        result = em.createNativeQuery("select custname,acno,accttype,substring(acno,5,6) from accountmaster "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and curbrcode = '" + brCode + "'  union "
                                + "select custname,acno,accttype,substring(acno,5,6) from td_accountmaster "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and curbrcode = '" + brCode + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select custname,acno,accttype,substring(acno,5,6) from accountmaster "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and curbrcode = '" + brCode + "'  AND accttype = '" + acType + "' union "
                                + "select custname,acno,accttype,substring(acno,5,6) from td_accountmaster "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and curbrcode = '" + brCode + "' AND accttype = '" + acType + "'").getResultList();
                    }

                    if (result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector values = (Vector) result.get(i);
                            custName = values.get(0).toString();
                            accountNum = values.get(1).toString();
                            acctType = values.get(2).toString();
                            custNo = values.get(3).toString();
                            if (accountNum != null && accountNum.length() == 12) {
                                acctNature = common.getAcNatureByAcNo(accountNum);
                                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    introducerList = em.createNativeQuery("select craddress from td_customermaster "
                                            + "where custno=substring('" + accountNum + "',5,6) and actype=substring('" + accountNum + "',3,2)  and brncode = '" + brCode + "'").getResultList();
                                    if (introducerList.size() > 0) {
                                        Vector intro = (Vector) introducerList.get(0);
                                        customerAddress = intro.get(0).toString();
                                    }
                                } else {
                                    introducerList = em.createNativeQuery("select craddress from customermaster "
                                            + "where custno=substring('" + accountNum + "',5,6) and actype=substring('" + accountNum + "',3,2) and brncode = '" + brCode + "'").getResultList();
                                    if (introducerList.size() > 0) {
                                        Vector intro = (Vector) introducerList.get(0);
                                        customerAddress = intro.get(0).toString();
                                    }
                                }
                            }
                            List custList = em.createNativeQuery("select peraddressline1,peraddressline2,upper(concat(pervillage,' ,',PerDistrict,' ,',rf2.ref_desc)),"
                                    + "ifnull(perpostalcode,''),ifnull(mobilenumber,'') from cbs_customer_master_detail cb,cbs_ref_rec_type rf2 "
                                    + "where customerid in(select custid from customerid where acno = '" + accountNum + "') "
                                    + "and cb.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'").getResultList();
                            if (!custList.isEmpty()) {
                                Vector custVector = (Vector) custList.get(0);
                                custAdd = custVector.get(0).toString().trim() + " " + custVector.get(1).toString().trim();
                                cityState = custVector.get(2).toString();
                                postalCode = custVector.get(3).toString();
                                mob = custVector.get(4).toString();
                            }
                            AccountOpenIntroducerPojo balCert = new AccountOpenIntroducerPojo();
                            balCert.setACNO(accountNum);
                            balCert.setACCTTYPE(acctType);
                            balCert.setCUSTNAME(custName);
                            balCert.setCRADDRESS(custAdd);
                            balCert.setCityState(cityState);
                            balCert.setPostalCode(postalCode);
                            balCert.setMobile(mob);
                            balCert.setINTRONAME("");
                            balCert.setINTROADD("");
                            balCert.setCUSTNO(custNo);
                            balCert.setINTRODUCERNUM("");
                            balCert.setBANKNAME(bnkName);
                            balCert.setBANKADDRESS(bnkAddress);
                            accountOpenIntroducerPojos.add(balCert);
                        }
                    }
                } else if (acountOwner.equalsIgnoreCase("Introducer Of Account")) {
                    if (acType.equalsIgnoreCase("A")) {
                        result = em.createNativeQuery("select custname,acno,introaccno,accttype,substring(acno,5,6) from accountmaster  "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and introaccno<>'' and curbrcode = '" + brCode + "'  union "
                                + "select custname,acno,introaccno,accttype,substring(acno,5,6) from td_accountmaster where openingdt between '" + fromDt + "' and '" + toDt + "' and "
                                + "introaccno<>'' and curbrcode = '" + brCode + "'").getResultList();
                    } else {
                        result = em.createNativeQuery("select custname,acno,introaccno,accttype,substring(acno,5,6) from accountmaster  "
                                + "where openingdt between '" + fromDt + "' and '" + toDt + "' and introaccno<>'' and curbrcode = '" + brCode + "'  AND accttype = '" + acType + "' union "
                                + "select custname,acno,introaccno,accttype,substring(acno,5,6) from td_accountmaster where openingdt between '" + fromDt + "' and '" + toDt + "' and "
                                + "introaccno<>'' and curbrcode = '" + brCode + "' AND accttype = '" + acType + "' ").getResultList();
                    }

                    if (result.size() > 0) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector values = (Vector) result.get(i);
                            custName = values.get(0).toString();
                            accountNum = values.get(1).toString();
                            introaccno = values.get(2).toString();
                            acctType = values.get(3).toString();
                            custNo = values.get(4).toString();
                            if (introaccno != null && introaccno.length() == 12) {
                                acctNature = common.getAcNatureByAcNo(introaccno);
                                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    //introducerList = em.createNativeQuery("select  custname,craddress from td_customermaster where "
                                    //        + "custno=substring('" + introaccno + "',5,6) and actype=substring('" + introaccno + "',3,2) and brncode = '" + brCode + "'").getResultList();
                                    introducerList = em.createNativeQuery("select  custname,craddress from td_customermaster where "
                                            + "custno=substring('" + introaccno + "',5,6) and actype=substring('" + introaccno + "',3,2) and brncode = substring('" + introaccno + "',1,2)").getResultList();
                                    if (introducerList.size() > 0) {
                                        Vector intro = (Vector) introducerList.get(0);
                                        introducerName = intro.get(0).toString();
                                        introducerAddress = intro.get(1).toString();
                                    }
                                } else {
                                    //introducerList = em.createNativeQuery("select custname,craddress from customermaster "
                                    //        + "where custno=substring('" + introaccno + "',5,6) and actype=substring('" + introaccno + "',3,2) and brncode = '" + brCode + "'").getResultList();
                                    introducerList = em.createNativeQuery("select custname,craddress from customermaster "
                                            + "where custno=substring('" + introaccno + "',5,6) and actype=substring('" + introaccno + "',3,2) and brncode = substring('" + introaccno + "',1,2)").getResultList();
                                    if (introducerList.size() > 0) {
                                        Vector intro = (Vector) introducerList.get(0);
                                        introducerName = intro.get(0).toString();
                                        introducerAddress = intro.get(1).toString();
                                    }
                                }
                            }
                            List custList = em.createNativeQuery("select peraddressline1,peraddressline2,upper(concat(pervillage,' ,',PerDistrict,' ,',rf2.ref_desc)),"
                                    + "ifnull(perpostalcode,''),ifnull(mobilenumber,'') from cbs_customer_master_detail cb,cbs_ref_rec_type rf2 "
                                    + "where customerid in(select custid from customerid where acno = '" + introaccno + "') "
                                    + "and cb.perstatecode = rf2.ref_code and rf2.ref_rec_no = '002'").getResultList();
                            if (!custList.isEmpty()) {
                                Vector custVector = (Vector) custList.get(0);
                                custAdd = custVector.get(0).toString().trim() + " " + custVector.get(1).toString().trim();
                                cityState = custVector.get(2).toString();
                                postalCode = custVector.get(3).toString();
                                mob = custVector.get(4).toString();
                            }
                            AccountOpenIntroducerPojo balCert = new AccountOpenIntroducerPojo();
                            balCert.setACNO(accountNum);
                            balCert.setACCTTYPE(acctType);
                            balCert.setCUSTNAME(custName);
                            balCert.setCRADDRESS(custAdd);
                            balCert.setINTRONAME(introducerName);
                            balCert.setINTROADD(introducerAddress);
                            balCert.setCityState(cityState);
                            balCert.setPostalCode(postalCode);
                            balCert.setMobile(mob);
                            balCert.setCUSTNO(custNo);
                            balCert.setINTRODUCERNUM(introaccno);
                            balCert.setBANKNAME(bnkName);
                            balCert.setBANKADDRESS(bnkAddress);
                            accountOpenIntroducerPojos.add(balCert);
                        }
                    }
                }
            }
            return accountOpenIntroducerPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * *************************************** End
     * ******************************************
     */
    /**
     * ***************************** TRANSFER BATCH
     * *********************************
     */
    /**
     *
     * @param voucherNo
     * @param date
     * @param brnCode
     * @return
     */
    public List<TransferBatchPojo> transferBatch(String voucherNo, String date, String brnCode) throws ApplicationException {
        List<TransferBatchPojo> transferBatchPojos = new ArrayList<TransferBatchPojo>();
        try {
            String bnkName = null, bnkAddress = null;
            List objBan;
            if (brnCode.equalsIgnoreCase("0A")) {
                objBan = common.getBranchNameandAddress("90");
            } else {
                objBan = common.getBranchNameandAddress(brnCode);
            }
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            List<String> tableName = new ArrayList<String>();
            tableName.add("recon");
            tableName.add("ca_recon");
            tableName.add("loan_recon");
            tableName.add("rdrecon");
            tableName.add("ddstransaction");
            tableName.add("of_recon");
            List result = new ArrayList();
            for (String table : tableName) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select a.acno,a.dramt,a.cramt,b.custName,a.Details,a.enterby,a.authby from " + table + " a,accountmaster b where a.dt = '" + date + "'  and a.trsno = '" + voucherNo + "'  and a.trantype in (2,8,6) and a.acno=b.acno ").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,a.dramt,a.cramt,b.custName,a.Details,a.enterby,a.authby from " + table + " a,accountmaster b where a.dt = '" + date + "'  and a.trsno = '" + voucherNo + "'  and a.trantype in (2,8,6) and a.acno=b.acno and a.org_brnid = '" + brnCode + "'").getResultList();
                }
                if (result.size() > 0) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector record = (Vector) result.get(j);
                        TransferBatchPojo balCert = new TransferBatchPojo();
                        balCert.setAcNo(record.get(0).toString());
                        balCert.setDrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(1).toString()))));
                        balCert.setCrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(2).toString()))));
                        balCert.setCustName(record.get(3).toString());
                        balCert.setDetail(record.get(4).toString());
                        balCert.setEnterBy(record.get(5).toString());
                        balCert.setAuthBy(record.get(6).toString());
                        balCert.setBankname(bnkName);
                        balCert.setBankaddress(bnkAddress);
                        transferBatchPojos.add(balCert);
                    }
                }
            }
            if (brnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.acno,a.dramt,a.cramt,b.custName,a.Details,a.enterby,a.authby from td_recon a,td_accountmaster b where a.dt = '" + date + "'  and a.trsno = '" + voucherNo + "'  and a.trantype in (2,8,6) and a.acno=b.acno and closeflag is null ").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,a.dramt,a.cramt,b.custName,a.Details,a.enterby,a.authby from td_recon a,td_accountmaster b where a.dt = '" + date + "'  and a.trsno = '" + voucherNo + "'  and a.trantype in (2,8,6) and a.acno=b.acno and closeflag is null and a.org_brnid = '" + brnCode + "'").getResultList();
            }

            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    TransferBatchPojo balCert = new TransferBatchPojo();
                    balCert.setAcNo(record.get(0).toString());
                    balCert.setDrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(1).toString()))));
                    balCert.setCrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(2).toString()))));
                    balCert.setCustName(record.get(3).toString());
                    balCert.setDetail(record.get(4).toString());
                    balCert.setEnterBy(record.get(5).toString());
                    balCert.setAuthBy(record.get(6).toString());
                    balCert.setBankname(bnkName);
                    balCert.setBankaddress(bnkAddress);
                    transferBatchPojos.add(balCert);
                }
            }
//            List result1 = em.createNativeQuery("select r.acno,r.dramt,r.cramt,g.acname,r.details,r.enterby,r.authby from gl_recon r,gltable g where r.dt = '" + date + "' and r.trsno = '" + voucherNo + "'and r.trantype in (2,8,6) and substring(r.acno,1,2) = '" + brnCode + "' and r.acno = g.acno").getResultList();
            List result1;

            if (brnCode.equalsIgnoreCase("0A")) {
                result1 = em.createNativeQuery("select r.acno,r.dramt,r.cramt,g.acname,r.details,r.enterby,r.authby from gl_recon r,gltable g "
                        + " where r.acno = g.acno and r.dt = '" + date + "' and r.trantype in (2,8,6) and auth='Y' "
                        + " AND r.ACNO NOT IN (select distinct acno from gltable where acname like '%CASH IN HAND%') "
                        + " AND r.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                        + " AND r.TranDesc <> 999 and r.trsno = '" + voucherNo + "'").getResultList();
            } else {
                result1 = em.createNativeQuery("select r.acno,r.dramt,r.cramt,g.acname,r.details,r.enterby,r.authby from gl_recon r,gltable g "
                        + " where r.acno = g.acno and r.dt = '" + date + "' and r.trantype in (2,8,6) and auth='Y' "
                        + " AND r.ACNO NOT IN (select distinct acno from gltable where acname like '%CASH IN HAND%') "
                        + " AND r.ACNO NOT IN (SELECT DISTINCT ACNO FROM gltable WHERE ACNAME LIKE '%INTERSOLE%') "
                        + " AND r.TranDesc <> 999 and r.trsno = '" + voucherNo + "' and r.org_brnid = '" + brnCode + "'").getResultList();
            }

            if (result1.size() > 0) {
                for (int j = 0; j < result1.size(); j++) {
                    Vector record = (Vector) result1.get(j);
                    TransferBatchPojo balCert = new TransferBatchPojo();
                    balCert.setAcNo(record.get(0).toString());
                    balCert.setDrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(1).toString()))));
                    balCert.setCrAmount(new BigDecimal(nft.format(Double.parseDouble(record.get(2).toString()))));
                    balCert.setCustName(record.get(3).toString());
                    balCert.setDetail(record.get(4).toString());
                    balCert.setEnterBy(record.get(5).toString());
                    balCert.setAuthBy(record.get(6).toString());
                    balCert.setBankname(bnkName);
                    balCert.setBankaddress(bnkAddress);
                    transferBatchPojos.add(balCert);
                }
            }
            return transferBatchPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ************************************** End
     * *************************************
     */
    /**
     * **************************
     * CBS_REP_STND_EXE_TODAY(StandingInstructionTodayExecutedReport)**************
     */
    public List<StandingInstructionTodayExecutedReportPojo> StandingInstructionTodayExecutedReport(String date, String tranType, String brnCode) throws ApplicationException {
        List<StandingInstructionTodayExecutedReportPojo> standingInstructionPojos = new ArrayList<StandingInstructionTodayExecutedReportPojo>();
        List result = new ArrayList();
        try {
            String fromName = "", toName = "";
            double amount = 0d;
            String bnkName = null, bnkAddress = null;
            List objBan;
            if (brnCode.equalsIgnoreCase("0A")) {
                objBan = common.getBranchNameandAddress("90");
            } else {
                objBan = common.getBranchNameandAddress(brnCode);
            }

            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            if (tranType.equalsIgnoreCase("TRAN")) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select fromacno,toacno,amount,remarks,substring(fromacno,3,2),0,'','' from standins_transexecuted a,accountmaster b where a.processdate ='" + date + "' and a.fromacno=b.acno order by fromacno").getResultList();
                } else {
                    result = em.createNativeQuery("select fromacno,toacno,amount,remarks,substring(fromacno,3,2),0,'','' from standins_transexecuted a,accountmaster b where a.processdate ='" + date + "' and a.fromacno=b.acno and b.curbrcode = '" + brnCode + "'  order by fromacno").getResultList();
                }
                if (result.size() > 0) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector record = (Vector) result.get(j);
                        StandingInstructionTodayExecutedReportPojo balCert = new StandingInstructionTodayExecutedReportPojo();
                        balCert.setBnkName(bnkName);
                        balCert.setBnkAdd(bnkAddress);
                        balCert.setFROMAC(record.get(0).toString());
                        balCert.setTOAC(record.get(1).toString());
                        balCert.setAMOUNT(new BigDecimal(Double.parseDouble(record.get(2).toString())));
                        balCert.setDETAILS(record.get(3).toString());
                        balCert.setACCTYPE(record.get(4).toString());
                        if (record.get(0) != null && record.get(0).toString().length() == 12) {
                            List frNameList = em.createNativeQuery("select custname from accountmaster where acno = '" + record.get(0).toString() + "'").getResultList();
                            if (!frNameList.isEmpty()) {
                                Vector balanceVect = (Vector) frNameList.get(0);
                                fromName = balanceVect.get(0).toString();
                            }
                        }
                        if (record.get(1) != null && record.get(1).toString().length() == 12) {
                            List toNameList = em.createNativeQuery("select custname from accountmaster where acno = '" + record.get(1).toString() + "'").getResultList();
                            if (!toNameList.isEmpty()) {
                                Vector balanceVect = (Vector) toNameList.get(0);
                                toName = balanceVect.get(0).toString();
                            }
                        }
                        List amountList = em.createNativeQuery("select sum(amount),substring(fromacno,3,2) from standins_transexecuted  where processdate ='" + date + "' and substring(fromacno,3,2)='" + record.get(4).toString() + "' group by substring(fromacno,3,2)").getResultList();
                        if (!amountList.isEmpty()) {
                            for (int k = 0; k < amountList.size(); k++) {
                                Vector balanceVect = (Vector) amountList.get(k);
                                amount = Double.parseDouble(balanceVect.get(0).toString());
                            }
                        }
                        balCert.setTOTALAMOUNT(new BigDecimal(amount));
                        balCert.setFROMNAME(fromName);
                        balCert.setTONAME(toName);
                        standingInstructionPojos.add(balCert);
                    }
                }
            }
            return standingInstructionPojos;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * ***************************** End **************************** /
     * /********************************* PAY ORDER
     * ********************************************
     */
    public List billTypeLoad() throws ApplicationException {
        List billType = new ArrayList();
        try {
            billType = em.createNativeQuery("select INSTCODE,instdesc from billtypemaster where instnature in ('PO','DD') ORDER BY INSTNATURE").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return billType;
    }

    public List<PayOrderPojo> getPoDdData(String billType, String mode, String dateValue, String fromdttmp, String todttmp, String bcode) throws ApplicationException {
        List<PayOrderPojo> payOrderPojos = new ArrayList<PayOrderPojo>();
        try {
            dateValue = dateValue == null ? "" : dateValue;
            fromdttmp = fromdttmp == null ? "" : fromdttmp;
            todttmp = todttmp == null ? "" : todttmp;
            String alpha = "", tmpRepDate = null, statusTEMP = "", trantypechar = "";
            List result = new ArrayList();
            int i = 0, temp = 0;
            int j = 0;
            double sum = 0d;
            String bnkName = null, bnkAddress = null;
            List objBan;
            if (bcode.equalsIgnoreCase("0A")) {
                objBan = common.getBranchNameandAddress("90");
            } else {
                objBan = common.getBranchNameandAddress(bcode);
            }

            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }
            String billHead = "";
            if (bcode.equalsIgnoreCase("0A")) {
                billHead = ftsPosting.getPoDdGlhead(billType) + "01";
            } else {
                billHead = bcode + ftsPosting.getPoDdGlhead(billType) + "01";
            }

            Date fromdt = null, todt = null;
            if (mode.equalsIgnoreCase("ISSUED")) {
                fromdt = ymdFormat.parse(fromdttmp);
                todt = ymdFormat.parse(todttmp);
            }

            if (mode.equalsIgnoreCase("PAID")) {
                fromdt = ymdFormat.parse(fromdttmp);
                todt = ymdFormat.parse(todttmp);
            }
            List toDtList;
            if (bcode.equalsIgnoreCase("0A")) {
                toDtList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '90'").getResultList();
            } else {
                toDtList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = '" + Integer.parseInt(bcode) + "'").getResultList();
            }
            if (!toDtList.isEmpty()) {
                Vector balanceVect = (Vector) toDtList.get(0);
                alpha = balanceVect.get(0).toString();
            }
            List rsList = em.createNativeQuery("select instnature from billtypemaster where instcode = '" + billType + "'").getResultList();
            if (rsList.isEmpty()) {
                throw new ApplicationException("GL Head does not exist in Bill Type Master");
            }
            Vector ele = (Vector) rsList.get(0);
            if (ele.get(0) == null) {
                throw new ApplicationException("GL Head does not exist in Bill Type Master");
            }
            String bNature = ele.get(0).toString();
            String billTable = common.getBillTable(bNature);
            if (bcode.equalsIgnoreCase("0A")) {
                if (mode.equalsIgnoreCase("SEQNO")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y'"
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt > '" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo and a.status like '%Deaf%' "
                            + "order by seqno").getResultList();

                } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y'"
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled')  "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt > '" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo  and a.status like '%Deaf%' "
                            + "order by origindt").getResultList();

                } else if (mode.equalsIgnoreCase("INSTNO")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' "
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled')  "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt >'" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo and a.status like '%Deaf%' "
                            + "order by instno").getResultList();
                }
            } else {
                if (mode.equalsIgnoreCase("SEQNO")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt > '" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo and substring(a.acno,1,2) = '" + bcode + "'  and a.status like '%Deaf%' "
                            + "order by seqno").getResultList();

                } else if (mode.equalsIgnoreCase("ISSUEDDATE")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + " "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled')  "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt > '" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo and substring(a.acno,1,2) = '" + bcode + "'  and a.status like '%Deaf%' "
                            + "order by origindt").getResultList();

                } else if (mode.equalsIgnoreCase("INSTNO")) {
                    result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from  " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt = dt) or (status in ('paid','cancelled') "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select instno,seqno,origindt,infavourof,trantype,amount,FYEAR from " + billTable + "  "
                            + "where ((status = 'issued' and dt <= '" + dateValue + "' and origindt <> dt) or (status in ('paid','cancelled')  "
                            + "and origindt <= '" + dateValue + "' and dt > '" + dateValue + "')) and auth = 'y' and substring(acno,1,2) = '" + bcode + "' "
                            + "union "
                            + "select a.instno,a.seqno,a.origindt,a.infavourof,a.trantype,a.amount,a.FYEAR from  bill_po a, "
                            + "(select aa.acno as aano ,aa.dt as aadt,max(spno)  as sno,aa.SPFLAG,aa.F_Year,cast(aa.Seq_No as unsigned) as SeqNo from accountstatus aa, "
                            + "(select acno as ano, max(dt) as dt,F_Year,Seq_No from accountstatus where dt >'" + dateValue + "' and substring(acno,3,2)='06' group by acno,F_Year,Seq_No) b "
                            + "where aa.acno = b.ano and aa.dt = b.dt "
                            + "and  aa.F_Year = b.F_Year "
                            + "and  aa.Seq_No = b.Seq_No "
                            + "group by aa.acno,aa.F_Year,aa.Seq_No)b "
                            + "where a.FYEAR = b.F_Year and a.seqno = b.SeqNo and substring(a.acno,1,2) = '" + bcode + "'  and a.status like '%Deaf%' "
                            + "order by instno").getResultList();
                }
            }
            if (result.size() > 0) {
                for (int k = 0; k < result.size(); k++) {
                    Vector record = (Vector) result.get(k);
                    if (Integer.parseInt(record.get(4).toString()) == 0) {
                        trantypechar = "BY CASH";
                    } else if (Integer.parseInt(record.get(4).toString()) == 1) {
                        trantypechar = "BY CLEARING";
                    } else {
                        trantypechar = "BY TRANSFER";
                    }
                    PayOrderPojo balCert = new PayOrderPojo();
                    balCert.setInstno(record.get(0).toString());
                    balCert.setSeqno(Double.parseDouble(record.get(1).toString()));
                    balCert.setOrigindt(ymd_1.parse(record.get(2).toString()));
                    balCert.setInfavourof(record.get(3).toString());
                    balCert.setTrantype(Integer.parseInt(record.get(4).toString()));
                    balCert.setTrantypechar(trantypechar);
                    balCert.setAmount(new BigDecimal(Double.parseDouble(record.get(5).toString())));
                    balCert.setAlphaCode(alpha);
                    balCert.setBankname(bnkName);
                    balCert.setBankaddress(bnkAddress);
                    payOrderPojos.add(balCert);
                }
            }
            if (mode.equalsIgnoreCase("ISSUED")) {
                List<PayOrderPojo> tempList = new ArrayList<PayOrderPojo>();
                double sumBal1 = 0, sumBal2 = 0;
                temp = (int) CbsUtil.dayDiff(fromdt, todt);
                while (i <= temp) {
                    sumBal1 = 0;
                    sumBal2 = 0;
                    tmpRepDate = CbsUtil.dateAdd(ymdFormat.format(fromdt), i);
                    if (bcode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select instno,seqno,infavourof,trantype,amount,comm,enterby,custname,origindt from " + billTable + " b where "
                                + "(dt = '" + tmpRepDate + "' or origindt = '" + tmpRepDate + "') and ((status = 'Issued' and ty = 0) or "
                                + "(status = 'Paid' and ty = 1) OR (status = 'CANCELLED' and ty = 1)) and auth = 'Y' and (ty =0 or b.dt = b.origindt "
                                + "or origindt =  '" + tmpRepDate + "') and substring(acno,3,10) = '" + billHead + "'order by origindt,seqno").getResultList();
                    } else {
                        result = em.createNativeQuery("select instno,seqno,infavourof,trantype,amount,comm,enterby,custname,origindt from " + billTable + " b where "
                                + "(dt = '" + tmpRepDate + "' or origindt = '" + tmpRepDate + "') and ((status = 'Issued' and ty = 0) or "
                                + "(status = 'Paid' and ty = 1) OR (status = 'CANCELLED' and ty = 1)) and auth = 'Y' and (ty =0 or b.dt = b.origindt "
                                + "or origindt =  '" + tmpRepDate + "') and substring(acno,1,2) = '" + bcode + "' and acno = '" + billHead + "'order by origindt,seqno").getResultList();
                    }
                    if (result.size() > 0) {
                        for (int k = 0; k < result.size(); k++) {
                            Vector record = (Vector) result.get(k);
                            PayOrderPojo balCert = new PayOrderPojo();
                            balCert.setAlphaCode(alpha);
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            balCert.setInstno(record.get(0).toString());
                            balCert.setSeqno(Double.parseDouble(record.get(1).toString()));
                            balCert.setInfavourof(record.get(2).toString());
                            balCert.setTrantype(Integer.parseInt(record.get(3).toString()));
                            if (Integer.parseInt(record.get(3).toString()) == 0) {
                                trantypechar = "BY CASH";
                            } else if (Integer.parseInt(record.get(3).toString()) == 1) {
                                trantypechar = "BY CLEARING";
                            } else {
                                trantypechar = "BY TRANSFER";
                            }
                            balCert.setTrantypechar(trantypechar);
                            balCert.setAmount(new BigDecimal(Double.parseDouble(record.get(4).toString())));
                            balCert.setComm(record.get(5).toString());
                            balCert.setEnterby(record.get(6).toString());
                            balCert.setPurchaser(record.get(7).toString());

                            balCert.setDt(ymd_1.parse(record.get(8).toString()));
                            List sumList;
                            if (bcode.equalsIgnoreCase("0A")) {
                                sumList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0)as decimal(25,2)) glBal from gl_recon where "
                                        + "substring(acno,3,10) = '" + billHead + "' and dt < '" + tmpRepDate + "'").getResultList();
                            } else {
                                sumList = em.createNativeQuery("select cast(ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0)as decimal(25,2)) glBal from gl_recon where acno = '"
                                        + billHead + "' and dt < '" + tmpRepDate + "'").getResultList();
                            }
                            if (!sumList.isEmpty()) {
                                Vector balanceVect = (Vector) sumList.get(0);
                                sum = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            if (record.get(7) != null) {
                                balCert.setFlag("I");
                                balCert.setOpenbal(new BigDecimal(sum));
                            }

                            balCert.setSumbal(new BigDecimal(sumBal1));
                            tempList.add(balCert);
                            sumBal2 += Double.parseDouble(balCert.getAmount().toString());
                        }

                    }
                    for (PayOrderPojo op : tempList) {
                        if (op.getFlag().equalsIgnoreCase("P") && op.getDt().equals(ymdFormat.parse(tmpRepDate))) {
                            op.setSumbal(new BigDecimal(sumBal2));
                        }
                    }
                    i = i + 1;
                }

                for (PayOrderPojo op : tempList) {
                    payOrderPojos.add(op);
                }
                ComparatorChain chain = new ComparatorChain();
                chain.addComparator(new payOrderCompareByDate());
                chain.addComparator(new PayOrderCompareByFlag());
                Collections.sort(payOrderPojos, chain);

            }

            if (mode.equalsIgnoreCase("PAID")) {
                List<PayOrderPojo> tempList = new ArrayList<PayOrderPojo>();
                double sumBal1 = 0, sumBal2 = 0;
                temp = (int) CbsUtil.dayDiff(fromdt, todt);
                while (i <= temp) {
                    sumBal1 = 0;
                    sumBal2 = 0;
                    tmpRepDate = CbsUtil.dateAdd(ymdFormat.format(fromdt), i);
                    if (bcode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,comm,enterby,dt from " + billTable + " "
                                + "Where (dt = '" + tmpRepDate + "' or origindt = '" + tmpRepDate + "') and ((status = 'Issued' and ty = 0) or "
                                + "(status = 'Paid' and ty = 1) OR (status = 'CANCELLED' and ty = 1)) and auth = 'Y' and (dt =  '" + tmpRepDate
                                + "' and ty =1 ) and substring(acno,3,10) = '" + billHead + "' order by dt,seqno").getResultList();
                    } else {
                        result = em.createNativeQuery("select instno,seqno,origindt,infavourof,trantype,amount,comm,enterby,dt from " + billTable + " "
                                + "Where (dt = '" + tmpRepDate + "' or origindt = '" + tmpRepDate + "') and ((status = 'Issued' and ty = 0) or "
                                + "(status = 'Paid' and ty = 1) OR (status = 'CANCELLED' and ty = 1)) and auth = 'Y' and (dt =  '" + tmpRepDate
                                + "' and ty =1 ) and substring(acno,1,2) = '" + bcode + "' and acno = '" + billHead + "' order by dt,seqno").getResultList();
                    }
                    if (result.size() > 0) {
                        for (int k = 0; k < result.size(); k++) {
                            Vector record = (Vector) result.get(k);
                            PayOrderPojo balCert = new PayOrderPojo();
                            balCert.setAlphaCode(alpha);
                            balCert.setBankname(bnkName);
                            balCert.setBankaddress(bnkAddress);
                            balCert.setInstno(record.get(0).toString());
                            balCert.setSeqno(Double.parseDouble(record.get(1).toString()));
                            balCert.setOrigindt(ymd_1.parse(record.get(8).toString()));
                            balCert.setInfavourof(record.get(3).toString());
                            balCert.setTrantype(Integer.parseInt(record.get(4).toString()));
                            if (Integer.parseInt(record.get(4).toString()) == 0) {
                                trantypechar = "BY CASH";
                            } else if (Integer.parseInt(record.get(4).toString()) == 1) {
                                trantypechar = "BY CLEARING";
                            } else {
                                trantypechar = "BY TRANSFER";
                            }
                            balCert.setTrantypechar(trantypechar);
                            balCert.setAmount(new BigDecimal(Double.parseDouble(record.get(5).toString())));
                            balCert.setComm(record.get(6).toString());
                            balCert.setEnterby(record.get(7).toString());
                            balCert.setDt(ymd_1.parse(record.get(2).toString()));
                            balCert.setPurchaser(dmyFormat.format(ymd_1.parse(record.get(8).toString())));
                            List sumList;
                            if (bcode.equalsIgnoreCase("0A")) {
                                sumList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon "
                                        + "where substring(acno,3,10) = '" + billHead + "' and dt < '" + tmpRepDate + "'").getResultList();
                            } else {
                                sumList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0))-sum(ifnull(dramt,0)),0) from gl_recon "
                                        + "where acno = '" + billHead + "' and dt < '" + tmpRepDate + "'").getResultList();
                            }
                            if (!sumList.isEmpty()) {
                                Vector balanceVect = (Vector) sumList.get(0);
                                sum = Double.parseDouble(balanceVect.get(0).toString());
                            }
                            balCert.setOpenbal(new BigDecimal(sum));
                            balCert.setFlag("P");
                            tempList.add(balCert);
                            sumBal1 += Double.parseDouble(balCert.getAmount().toString());
                        }
                    }

                    for (PayOrderPojo op : tempList) {
                        if (op.getFlag().equalsIgnoreCase("P") && op.getDt().equals(ymdFormat.parse(tmpRepDate))) {
                            op.setSumbal(new BigDecimal(sumBal2));
                        }
                    }
                    i = i + 1;

                }
                for (PayOrderPojo op : tempList) {
                    payOrderPojos.add(op);
                }
                ComparatorChain chain = new ComparatorChain();
                chain.addComparator(new payOrderCompareByDate());
                chain.addComparator(new PayOrderCompareByFlag());
                Collections.sort(payOrderPojos, chain);

            }

            if (mode.equalsIgnoreCase("STATUS")) {
                if (bcode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select ty,lastupdateby,instno,seqno,origindt,infavourof,trantype,amount,comm,status,enterby "
                            + "from " + billTable + " where origindt between '" + fromdttmp + "' and '" + todttmp + "'  and auth = 'Y' "
                            + "order by seqno ").getResultList();
                } else {
                    result = em.createNativeQuery("select ty,lastupdateby,instno,seqno,origindt,infavourof,trantype,amount,comm,status,enterby "
                            + "from " + billTable + " where origindt between '" + fromdttmp + "' and '" + todttmp + "'  and auth = 'Y' "
                            + "and substring(acno,1,2) = '" + bcode + "' order by seqno ").getResultList();
                }
                if (result.size() > 0) {
                    for (int k = 0; k < result.size(); k++) {
                        Vector record = (Vector) result.get(k);
                        if (Integer.parseInt(record.get(6).toString()) == 0) {
                            trantypechar = "BY CASH";
                        } else if (Integer.parseInt(record.get(6).toString()) == 1) {
                            trantypechar = "BY CLEARING";
                        } else {
                            trantypechar = "BY TRANSFER";
                        }
                        if (Integer.parseInt(record.get(0).toString()) == 0) {
                            statusTEMP = "UNCLEARED";
                        } else {
                            statusTEMP = record.get(9).toString();
                        }
                        statusTEMP = "UNCLEARED";
                        PayOrderPojo balCert = new PayOrderPojo();
                        balCert.setInstno(record.get(2).toString());
                        balCert.setSeqno(Double.parseDouble(record.get(3).toString()));
                        balCert.setOrigindt(ymd_1.parse(record.get(4).toString()));
                        balCert.setInfavourof(record.get(5).toString());
                        balCert.setTrantype(Integer.parseInt(record.get(6).toString()));
                        balCert.setAmount(new BigDecimal(Double.parseDouble(record.get(7).toString())));
                        balCert.setComm(record.get(8).toString());
                        balCert.setTrantypechar(trantypechar);
                        balCert.setStatus(statusTEMP);
                        balCert.setEnterby(record.get(10).toString());
                        balCert.setAlphaCode(alpha);
                        balCert.setBankname(bnkName);
                        balCert.setBankaddress(bnkAddress);
                        payOrderPojos.add(balCert);
                    }
                }
            }
            return payOrderPojos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    /**
     * ******************************* End
     * *****************************************
     */
    @Override
    public List<CashTranRepPojo> cashTransferReport(int reporttype, String actype1, double amt, String frmdt, String todt, String brCode, String ty) {
        String accnature = "";
        List result = new ArrayList();
        List<String> acctypeList = new ArrayList<String>();
        List<CashTranRepPojo> resultList = new ArrayList<CashTranRepPojo>();
        int trantype = 0, trantype1 = 0;
        switch (reporttype) {
            case 0:
                trantype = 0;
                trantype1 = 0;
                break;
            case 1:
                trantype = 1;
                trantype1 = 1;
                break;
            case 2:
                trantype = 2;
                trantype1 = 8;
                break;
        }

        if (actype1.equalsIgnoreCase("ALL")) {
            acctypeList.clear();
            result = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "') ").getResultList();
            for (int i = 0; i < result.size(); i++) {
                Vector vtr = (Vector) result.get(i);
                acctypeList.add(vtr.get(0).toString());
            }
        } else {
            acctypeList.clear();
            acctypeList.add(actype1);
        }
        for (String actype : acctypeList) {
            List acnatureList = em.createNativeQuery("select  distinct(acctnature) from accounttypemaster where acctcode='" + actype + "'").getResultList();
            if (!acnatureList.isEmpty()) {
                for (int k = 0; k < acnatureList.size(); k++) {
                    Vector vtr1 = (Vector) acnatureList.get(k);
                    accnature = vtr1.get(0).toString();
                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            // row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select ifnull (sum(cramt),0) from recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select ifnull(sum(dramt),0) from recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            // row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }
                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from ca_recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and  "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from ca_recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and  "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();

                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            // row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select ifnull (sum(cramt),0) from ca_recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select ifnull(sum(dramt),0) from ca_recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            // row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }
                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from rdrecon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }

                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from rdrecon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();

                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            //  row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select ifnull (sum(cramt),0) from rdrecon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select ifnull(sum(dramt),0) from rdrecon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            // row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }
                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from td_recon a ,td_accountmaster b,td_customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                    + " and a.closeflag is null and a.Trantype<> 27 and a.auth='y' order by a.acno,a.dt  ").getResultList();
                        }
                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.FIXED_AC) || accnature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from td_recon a ,td_accountmaster b,td_customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                    + " and a.closeflag is null and a.Trantype<> 27 and a.auth='y' order by a.acno,a.dt ").getResultList();
                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            //row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select cast(ifnull (sum(cramt),0)as decimal(25,2)) from td_recon where dt between '" + frmdt + "' and '" + todt + "' and closeflag is null and Trantype<> 27 and auth='y'and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select cast(ifnull(sum(dramt),0)as decimal(25,2)) from td_recon where dt between '" + frmdt + "' and '" + todt + "' and closeflag is null and Trantype<> 27 and auth='y' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            //  row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }
                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from ddstransaction a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from ddstransaction a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            //row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select ifnull(sum(cramt),0) from td_recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select ifnull(sum(dramt),0) from rdrecon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            // row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }

                    result = new ArrayList();
                    if (brCode.equalsIgnoreCase("0A")) {
                        if (accnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from loan_recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + " and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    } else {
                        if (accnature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || accnature.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                            result = em.createNativeQuery("select a.dt,b.custname,a.acno,a.dramt,a.cramt,a.ty,ifnull(c.panno,''),c.praddress,ifnull(c.fathername,'') from loan_recon a ,accountmaster b,customermaster c "
                                    + "where a.dt between '" + frmdt + "' and '" + todt + "' and (a.dramt>=" + amt + " or a.cramt >=" + amt + ") "
                                    + "and b.curbrcode = '" + brCode + "' and b.openingdt<='" + todt + "' and substring(a.acno,3,2)='" + actype + "' and "
                                    + "(b.closingdate>='" + frmdt + "' or ifnull(b.closingdate,'')='' or b.closingdate='') and c.brncode = b.curbrcode and "
                                    + "a.acno = b.acno and a.trantype in (" + trantype + "," + trantype1 + ") and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) order by a.ty, a.acno,a.dt ").getResultList();
                        }
                    }

                    if (!result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Vector vtr = (Vector) result.get(i);
                            CashTranRepPojo row = new CashTranRepPojo();
                            row.setACCNAME(vtr.get(1).toString());
                            row.setACCNO(vtr.get(2).toString());
                            //row.setAMOUNT(Float.parseFloat(vtr.get(3).toString()) - Float.parseFloat(vtr.get(4).toString()));
                            row.setAMOUNT(new BigDecimal(vtr.get(3).toString()).subtract(new BigDecimal(vtr.get(4).toString())));
                            row.setDATE((Date) vtr.get(0));
                            row.setFLAG(vtr.get(5).toString().equalsIgnoreCase("0") ? "CR" : "DR");
                            row.setPANNO(vtr.get(6).toString());
                            row.setPrAddr(vtr.get(7).toString());
                            row.setFatherName(vtr.get(8).toString());
                            List s = vtr.get(5).toString().equalsIgnoreCase("0") ? em.createNativeQuery("select ifnull(sum(cramt),0) from td_recon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList() : em.createNativeQuery("select ifnull(sum(dramt),0) from rdrecon where dt between '" + frmdt + "' and '" + todt + "' and acno = '" + vtr.get(2).toString() + "'").getResultList();
                            Vector vtr3 = (Vector) s.get(0);
                            // row.setTOTDEP(Float.parseFloat(vtr3.get(0).toString()));
                            row.setTOTDEP(new BigDecimal(vtr3.get(0).toString()));
                            if (ty.equals("0") && vtr.get(5).toString().equals("0")) {
                                resultList.add(row);
                            } else if (ty.equals("1") && vtr.get(5).toString().equals("1")) {
                                resultList.add(row);
                            } else if (ty.equals("2")) {
                                resultList.add(row);
                            }
                        }
                    }
                }
            }
        }
        return resultList;
    }

    @Override
    public List<DepositWithdrawlPojo> depositWithdrawlReport(int reporttype, String actype1, String frmDate, String toDate, String brcode, double amt) throws ApplicationException {
        try {
            String accnature = "";
            List resultList = new ArrayList();
            List<DepositWithdrawlPojo> finalResultList = new ArrayList<DepositWithdrawlPojo>();
            List<String> acctypeList = new ArrayList<String>();
            int trantype = 0, trantype1 = 0;
            switch (reporttype) {
                case 0:
                    trantype = 0;
                    trantype1 = 0;
                    break;
                case 1:
                    trantype = 1;
                    trantype1 = 1;
                    break;
                case 2:
                    trantype = 2;
                    trantype1 = 8;
                    break;

            }
            if (actype1.equalsIgnoreCase("ALL")) {
                acctypeList.clear();
                List result = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in ('" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "') ").getResultList();
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    acctypeList.add(vtr.get(0).toString());
                }
            } else {
                acctypeList.clear();
                acctypeList.add(actype1);
            }
            for (String actype : acctypeList) {
                List acnatureList = em.createNativeQuery("select  distinct(acctnature) from accounttypemaster where acctcode='" + actype + "'").getResultList();
                if (!acnatureList.isEmpty()) {
                    for (int k = 0; k < acnatureList.size(); k++) {
                        Vector vtr1 = (Vector) acnatureList.get(k);
                        accnature = vtr1.get(0).toString();
                        resultList.clear();
                        if (accnature.equals(CbsConstant.SAVING_AC)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' and  r.trantype in(" + trantype + "," + trantype1 + ") "
//                                        + "and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,recon r,customermaster cu,(select distinct (t.acno) as acn from recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' and  r.trantype in(" + trantype + "," + trantype1 + ") "
                                        + "and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno,r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,recon r,customermaster cu,(select distinct (t.acno) as acn from recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            }

                        } else if (accnature.equals(CbsConstant.FIXED_AC) || accnature.equals(CbsConstant.MS_AC)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from td_accountmaster a,td_recon r,td_customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from td_recon t,td_accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' and  r.trantype in(" + trantype + "," + trantype1 + ") "
//                                        + "and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from td_accountmaster a,td_recon r,td_customermaster cu,(select distinct (t.acno) as acn from td_recon t,td_accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno  "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' and r.trantype in(" + trantype + "," + trantype1 + ") "
                                        + "and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno, r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from td_accountmaster a,td_recon r,td_customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from td_recon t,td_accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' and  r.trantype in(" + trantype + "," + trantype1 + ") "
//                                        + "and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from td_accountmaster a,td_recon r,td_customermaster cu,(select distinct (t.acno) as acn from td_recon t,td_accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' and  r.trantype in(" + trantype + "," + trantype1 + ") "
                                        + "and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno order by a.acno, r.dt").getResultList();
                            }

                        } else if (accnature.equals(CbsConstant.RECURRING_AC)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,rdrecon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from rdrecon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between'" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,rdrecon r,customermaster cu,(select distinct (t.acno) as acn from rdrecon t,accountmaster z where t.dt between '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between'" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,rdrecon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from rdrecon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "' "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between'" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,rdrecon r,customermaster cu,(select distinct (t.acno) as acn from rdrecon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "' "
                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between'" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            }

                        } else if (accnature.equals(CbsConstant.TERM_LOAN) || accnature.equals(CbsConstant.DEMAND_LOAN)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,loan_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from loan_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + "))"
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,loan_recon r,customermaster cu,(select distinct (t.acno) as acn from loan_recon t,accountmaster z where t.dt between '" + frmDate + "' and '" + toDate + "' and t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,loan_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from loan_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "' "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + "))"
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,loan_recon r,customermaster cu,(select distinct (t.acno) as acn from loan_recon t,accountmaster z where t.dt between '" + frmDate + "' and '" + toDate + "' and t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "' "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            }

                        } else if (accnature.equals(CbsConstant.CURRENT_AC)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,ca_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from ca_recon t, accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,ca_recon r,customermaster cu,(select distinct (t.acno) as acn from ca_recon t, accountmaster z where t.dt between '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from accountmaster a,ca_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from ca_recon t, accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from accountmaster a,ca_recon r,customermaster cu,(select distinct (t.acno) as acn from ca_recon t, accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            }

                        } else if (accnature.equals(CbsConstant.OF_AC)) {
                            if (brcode.equalsIgnoreCase("90")) {
//                                resultList = em.createNativeQuery("Select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from td_accountmaster a,of_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from of_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("Select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt,r.dt from td_accountmaster a,of_recon r,customermaster cu,(select distinct (t.acno) as acn from of_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno "
                                        + "group by t.acno having (sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            } else {
//                                resultList = em.createNativeQuery("Select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from td_accountmaster a,of_recon r,customermaster cu,codebook co  where a.acno "
//                                        + "in(select distinct (t.acno) from of_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
//                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) "
//                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + toDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
//                                        + "and  r.dt between '" + frmDate + "' and '" + toDate + "' "
//                                        + "and  r.trantype in(" + trantype + "," + trantype1 + ") and concat(cu.brncode,cu.actype,cu.custno)=substring(a.acno,1,10) "
//                                        + "and cu.occupation=co.code and co.groupcode=6 "
//                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
//                                        + "order by a.acno, r.dt").getResultList();
                                resultList = em.createNativeQuery("Select a.acno,cu.custname,cu.panno,sum(r.cramt)as cramt,sum(r.dramt) as dramt ,r.dt from td_accountmaster a,of_recon r,customermaster cu,(select distinct (t.acno) as acn from of_recon t,accountmaster z  where  t.dt between  '" + frmDate + "' and '" + toDate + "' and  t.trantype in(" + trantype + "," + trantype1 + ") and t.acno=z.acno and z.curbrcode='" + brcode + "'"
                                        + "group by t.acno  having ( sum(t.dramt)>= " + amt + " or sum(t.cramt)>= " + amt + ")) d where a.acno = d.acn "
                                        + "and a.openingdt<='" + toDate + "' and substring(a.acno,3,2)='" + actype + "' and (a.closingdate>='" + frmDate + "' or ifnull(a.closingdate,'')='' or a.closingdate='') and r.acno=a.acno "
                                        + "and r.dt between '" + frmDate + "' and '" + toDate + "' "
                                        + "and r.trantype in(" + trantype + "," + trantype1 + ") and cu.brncode = substring(a.acno,1,2) and cu.actype = substring(a.acno,3,2) and cu.custno = substring(a.acno,5,6) "
                                        + "group by a.acno,r.dt,cu.custname,cu.panno "
                                        + "order by a.acno, r.dt").getResultList();
                            }

                        }
                        if (!resultList.isEmpty()) {
                            for (int j = 0; j < resultList.size(); j++) {
                                Vector vtr = (Vector) resultList.get(j);
                                DepositWithdrawlPojo row = new DepositWithdrawlPojo();
                                row.setAccNo(vtr.get(0).toString());
                                row.setCustName(vtr.get(1).toString());
                                row.setCrAmt(Double.parseDouble(vtr.get(3).toString()));
                                row.setCustAdd(vtr.get(2).toString());
                                row.setDrAmt(Double.parseDouble(vtr.get(4).toString()));
                                row.setDate((Date) vtr.get(5));
                                finalResultList.add(row);
                            }
                        }
                    }
                }
            }
            return finalResultList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getDicgcAcTypeList() throws ApplicationException {
        try {
            List resultList = em.createNativeQuery("select 'ALL' as acctcode union select acctcode from accounttypemaster where acctnature in('" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.CURRENT_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.MS_AC + "') and acctNature Is Not Null").getResultList();
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<TdActiveReportPojo> getTdActiveReport(int option, String actype, int month, int yyyy, String brncode, String frmdt, String todt, int tempDays, float frmroi, float toroi, String orderby) throws ApplicationException {
        List result = null;
        Vector vtr = null;
        List<TdActiveReportPojo> resultList = new ArrayList<TdActiveReportPojo>();
        try {
            String acTypeQuery = "";
            if (!actype.equals("All")) {
                acTypeQuery = " and substring(vm.acno,3,2)='" + actype + "'";
            }
            if (brncode.equalsIgnoreCase("0A")) {
                if (option == 1) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt <= DATE_ADD(vm.td_madedt, INTERVAL " + tempDays + " DAY) "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + "  and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                            + "from td_vouchmst vm,td_customermaster cm,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt <= DATE_ADD(vm.td_madedt, INTERVAL " + tempDays + " DAY) \n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " \n"
                            + "and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo)b\n"
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 2) {
                    if (month == 13) {
//                        result = em.createNativeQuery("Select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<=" + yyyy + "  "
//                                + "and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                                + " and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                        result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                                + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                                + "(Select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<= " + yyyy + " \n"
                                + "and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + "\n"
                                + "and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where extract(year from dt) <=" + yyyy + " group by acno,VoucherNo)b\n"
                                + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where extract(year from dt) <=" + yyyy + " and recovered = 'R'  group by acno,VoucherNo\n"
                                + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                    } else {
//                        result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='A' and extract(year from vm.matdt)<=" + yyyy + " "
//                                + "and extract(month from vm.matdt)<=" + month + " and cm.custno=substring(vm.acno,5,6) "
//                                + " and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                        result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                                + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                                + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='A' and extract(year from vm.matdt)<= " + yyyy + "\n"
                                + "and extract(month from vm.matdt)<= " + month + " and cm.custno=substring(vm.acno,5,6) \n"
                                + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where extract(year from dt) <=" + yyyy + " and extract(month from dt)<= " + month + " group by acno,VoucherNo)b\n"
                                + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where extract(year from dt) <=" + yyyy + " and extract(month from dt)<= " + month + " and recovered = 'R'  group by acno,VoucherNo\n"
                                + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                    }
                }
                if (option == 3) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt >= '" + frmdt + "' and vm.matdt <= '" + todt + "' "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + " and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt >= '" + frmdt + "' and vm.matdt <= '" + todt + "'\n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2)" + acTypeQuery + "\n"
                            + "and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo)b\n"
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where  dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 4) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.roi between " + frmroi + " and " + toroi + " and vm.status='a' "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + " and ac.curbrcode = cm.brncode order by " + orderby).getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.roi between " + frmroi + " and " + toroi + " and vm.status='a'\n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + "\n"
                            + "and ac.curbrcode = cm.brncode order by " + orderby + ") a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo)b\n"
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 5) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,"
//                            + "vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  from td_vouchmst vm,td_customermaster cm ,"
//                            + "td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and(ac.closingdate>='" + todt + "' "
//                            + "or ifnull(ac.closingdate,'')='' or ac.closingdate='') and vm.fddt <= '" + todt + "'and cm.custno=substring(vm.acno,5,6) "
//                            + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,\n"
                            + "vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt  from td_vouchmst vm,td_customermaster cm ,\n"
                            + "td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and (vm.cldt is null or vm.cldt>='" + todt + "') and(ac.closingdate>='" + todt + "'\n"
                            + "or ifnull(ac.closingdate,'')='' or ac.closingdate='') and vm.fddt <= '" + todt + "'and cm.custno=substring(vm.acno,5,6) \n"
                            + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt <= '" + todt + "' group by acno,VoucherNo)b\n"
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 0) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and cm.custno=substring(vm.acno,5,6) "
//                            + " and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = cm.brncode order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and (vm.cldt is null or vm.cldt>='" + ymd.format(new Date()) + "') and cm.custno=substring(vm.acno,5,6) " + acTypeQuery + " \n"
                            + "and cm.actype=substring(vm.acno,3,2) and ac.curbrcode = cm.brncode order by vm.acno) a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo)b\n"
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt >= '" + frmdt + "' and dt <= '" + todt + "'and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
            } else {
                if (option == 1) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt <= DATE_ADD(vm.td_madedt, INTERVAL " + tempDays + " DAY) "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' "
//                            + " and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + " and ac.curbrcode = '" + brncode + "' order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y')NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                            + "from td_vouchmst vm,td_customermaster cm,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt <= DATE_ADD(vm.td_madedt, INTERVAL " + tempDays + " DAY) \n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' and cm.actype=substring(vm.acno,3,2)" + acTypeQuery + " \n"
                            + "and ac.curbrcode = '" + brncode + "' order by vm.acno)a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo\n"
                            + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo\n"
                            + "").getResultList();
                }
                if (option == 2) {
                    if (month == 13) {
//                        result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<=" + yyyy + "  "
//                                + "and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' and cm.actype=substring(vm.acno,3,2) "
//                                + acTypeQuery + " and ac.curbrcode = '" + brncode + "' order by vm.acno").getResultList();
                        result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                                + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                                + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt\n"
                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<= " + yyyy + "\n"
                                + "and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' and cm.actype=substring(vm.acno,3,2)  " + acTypeQuery + "\n"
                                + "and ac.curbrcode = '" + brncode + "' order by vm.acno)a\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where extract(year from dt) <=" + yyyy + " group by acno,VoucherNo\n"
                                + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where extract(year from dt) <=" + yyyy + " and recovered = 'R'  group by acno,VoucherNo\n"
                                + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                    } else {
//                        result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno ,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid "
//                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<=" + yyyy + " "
//                                + "and extract(month from vm.matdt)<=" + month + " and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' "
//                                + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = '" + brncode + "' order by vm.acno").getResultList();

                        result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                                + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                                + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno ,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt  \n"
                                + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and extract(year from vm.matdt)<= " + yyyy + " \n"
                                + "and extract(month from vm.matdt)<= " + month + " and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' \n"
                                + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = '" + brncode + "' order by vm.acno)a\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where extract(year from dt) <=" + yyyy + " and extract(month from dt)<= " + month + " group by acno,VoucherNo\n"
                                + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                                + "left join\n"
                                + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where extract(year from dt) <=" + yyyy + " and extract(month from dt)<= " + month + " and recovered = 'R'  group by acno,VoucherNo\n"
                                + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();

                    }
                }
                if (option == 3) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt >= '" + frmdt + "' and vm.matdt <= '" + todt + "' "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.brncode ='" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + " and ac.curbrcode = '" + brncode + " ' order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt   \n"
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and vm.matdt >= '" + frmdt + "' and vm.matdt <= '" + todt + "' \n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.brncode ='" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + "\n"
                            + "and ac.curbrcode = '" + brncode + "' order by vm.acno)a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo\n"
                            + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 4) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.roi between " + frmroi + " and " + toroi + " and vm.status='a' "
//                            + " and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + " and ac.curbrcode = '" + brncode + "' order by " + orderby).getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from \n"
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from\n"
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt \n"
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.roi between " + frmroi + " and " + toroi + " and vm.status='a' \n"
                            + "and cm.custno=substring(vm.acno,5,6) and cm.brncode = '" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + "\n"
                            + "and ac.curbrcode = '" + brncode + "' order by " + orderby + ")a\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where  dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo\n"
                            + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa\n"
                            + "left join\n"
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where  dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo\n"
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 5) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,"
//                            + "vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,"
//                            + "customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and(ac.closingdate>='" + todt + "' or "
//                            + "ifnull(ac.closingdate,'')='' or ac.closingdate='')and vm.fddt <= '" + todt + "'and cm.custno=substring(vm.acno,5,6) "
//                            + "and cm.brncode ='" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery
//                            + "and ac.curbrcode = '" + brncode + "' order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from "
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from "
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,matdt,intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt "
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,"
                            + "customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and (vm.cldt is null or vm.cldt>='" + todt + "') and(ac.closingdate>='" + todt + "' or "
                            + "ifnull(ac.closingdate,'')='' or ac.closingdate='')and vm.fddt <= '" + todt + "'and cm.custno=substring(vm.acno,5,6) "
                            + "and cm.brncode ='" + brncode + "' and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + ""
                            + "and ac.curbrcode = '" + brncode + "' order by vm.acno)a "
                            + "left join "
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where dt <= '" + todt + "' group by acno,VoucherNo "
                            + ")b on a.acno=b.acno and a.voucherno = b.VoucherNo)aa "
                            + "left join "
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo "
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();
                }
                if (option == 0) {
//                    result = em.createNativeQuery("select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y'),ci.custid  "
//                            + " from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and vm.status='a' and cm.custno=substring(vm.acno,5,6) "
//                            + " and cm.brncode = '" + brncode + "' "
//                            + " and cm.actype=substring(vm.acno,3,2)" + acTypeQuery + " and ac.curbrcode = '" + brncode + "' order by vm.acno").getResultList();
                    result = em.createNativeQuery("select  aa.acno,aa.voucherno,aa.prinamt,aa.fddt,aa.roi,aa.matdt,aa.intopt,aa.custname,aa.seqno,aa.receiptno,aa.autorenew,aa.NextIntDt,aa.custid,IntAmt,ifnull(bb.TdsAmt,0),aa.CumuPrinAmt from "
                            + "(select  a.acno,a.voucherno,a.prinamt,a.fddt,a.roi,a.matdt,a.intopt,a.custname,a.seqno,a.receiptno,a.autorenew,a.NextIntDt,a.custid,ifnull(b.IntAmt,0) IntAmt,a.CumuPrinAmt from "
                            + "(select vm.acno,vm.voucherno,vm.prinamt,vm.fddt,vm.roi,vm.matdt,vm.intopt,cm.custname,vm.seqno,vm.receiptno,vm.autorenew,date_format(vm.NextIntPayDt,'%d/%m/%Y') NextIntDt,ci.custid,vm.CumuPrinAmt "
                            + "from td_vouchmst vm,td_customermaster cm ,td_accountmaster ac,customerid ci where vm.acno=ac.acno and vm.acno=ci.acno and (vm.cldt is null or vm.cldt>='" + ymd.format(new Date()) + "') and cm.custno=substring(vm.acno,5,6) "
                            + "and cm.brncode = '" + brncode + "' "
                            + "and cm.actype=substring(vm.acno,3,2) " + acTypeQuery + " and ac.curbrcode = '" + brncode + "' order by vm.acno) a "
                            + "left join "
                            + "(select acno,VoucherNo,ifnull(sum(Interest),0) IntAmt from td_interesthistory where  dt >= '" + frmdt + "' and dt <= '" + todt + "' group by acno,VoucherNo)b "
                            + "on a.acno=b.acno and a.voucherno = b.VoucherNo)aa "
                            + "left join "
                            + "(select acno,VoucherNo,ifnull(sum(TDS),0) TdsAmt from tds_reserve_history where  dt >= '" + frmdt + "' and dt <= '" + todt + "' and recovered = 'R'  group by acno,VoucherNo "
                            + ")bb on aa.acno=bb.acno and aa.voucherno = bb.VoucherNo").getResultList();

                }
            }
            List intAmtList = null;
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    vtr = (Vector) result.get(i);
                    TdActiveReportPojo row = new TdActiveReportPojo();
                    row.setAcNo(vtr.get(0).toString());
//                    //For Interest Amount
//                    if (option == 1) {
//                        // intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and dt<= DATE_ADD('20150101',INTERVAL " + tempDays + " DAY)").getResultList();
//                        intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and dt >= '" + frmdt + "' and dt <= '" + todt + "'").getResultList();
//                    } else if (option == 2) {
//                        if (month == 13) {
//                            intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and extract(year from dt) <=" + yyyy + " ").getResultList();
//                        } else {
//                            intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and extract(year from dt) <=" + yyyy + " and extract(month from dt)<= " + month + "").getResultList();
//                        }
//                    } else if (option == 3) {
//                        intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and dt >= '" + frmdt + "' and dt <= '" + todt + "'").getResultList();
//                    } else if (option == 4) {
//                        intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and dt >= '" + frmdt + "' and dt <= '" + todt + "'").getResultList();
//                    } else if (option == 5) {
//                        intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and dt <= '" + todt + "' ").getResultList();
//                    } else if (option == 0) {
//                        intAmtList = em.createNativeQuery("select ifnull(sum(Interest),0) from td_interesthistory where acno = '" + vtr.get(0).toString() + "' and VoucherNo = '" + vtr.get(1).toString() + "' and dt >= '" + frmdt + "' and dt <= '" + todt + "'").getResultList();
//                    }
//                    Vector intAmtVector = (Vector) intAmtList.get(0);

                    row.setVouchNo(Float.parseFloat(vtr.get(1).toString()));
                    row.setPrinAmt(Float.parseFloat(vtr.get(2).toString()));
                    row.setFdDt(ymd_1.parse(vtr.get(3).toString()));
                    row.setFddtexcel(dmyFormat.format(ymd_1.parse(vtr.get(3).toString())));
                    row.setRoi(Float.parseFloat(vtr.get(4).toString()));
                    row.setMatDt(ymd_1.parse(vtr.get(5).toString()));
                    row.setMatDtexcel(dmyFormat.format(ymd_1.parse(vtr.get(5).toString())));
                    if (vtr.get(6).toString().equalsIgnoreCase("Q")) {
                        row.setIntOpt("Quarterly");
                    } else if (vtr.get(6).toString().equalsIgnoreCase("S")) {
                        row.setIntOpt("Simple");
                    } else if (vtr.get(6).toString().equalsIgnoreCase("C")) {
                        row.setIntOpt("Cumulative");
                    } else if (vtr.get(6).toString().equalsIgnoreCase("M")) {
                        row.setIntOpt("Monthly");
                    }
                    row.setCustName(vtr.get(7).toString());
                    row.setSeqno(Float.parseFloat(vtr.get(8).toString()));
                    row.setReceiptNo(Float.parseFloat(vtr.get(9).toString()));
                    row.setAutoRenew(vtr.get(10).toString());
                    row.setNextIntpDt(vtr.get(11).toString());
                    row.setIntAmt(Double.parseDouble(vtr.get(13).toString()));
                    row.setTds(Double.parseDouble(vtr.get(14).toString()));
                    row.setCumPrinAmt(Double.parseDouble(vtr.get(15).toString()));
                    row.setCustId(vtr.get(12).toString());
                    resultList.add(row);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return resultList;
    }

    @Override
    public List<TransactionAggregatePojo> transactionAggregateReport(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode) throws ApplicationException {
        List tempList = null, acnoList = null;
        Vector tempVector = null, acnoVector = null;
        double sumDrAmt = 0d, sumCrAmt = 0d, crAmt = 0d, drAmt = 0d;
        String acctNature, accountmaster = null, customermaster = null, recon = null;
        List<TransactionAggregatePojo> finalResultList = new ArrayList<TransactionAggregatePojo>();
        try {
            tempList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode ='" + acType + "'").getResultList();
            tempVector = (Vector) tempList.get(0);
            acctNature = tempVector.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                accountmaster = "td_accountmaster";
                customermaster = "td_customermaster";
                recon = "td_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "ca_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "rdrecon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "loan_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                accountmaster = "td_accountmaster";
                customermaster = "td_customermaster";
                recon = "of_recon";
            }
            if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC) || acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                acnoList = em.createNativeQuery("select td.acno,openingdt,tc.custname,tc.praddress ,tc.dob,description,ifnull(panno,'') from " + accountmaster + " td, " + customermaster + " tc,codebook cb  "
                        + " where td.acno  in( select distinct a.acno from " + recon + " a,td_accountmaster b where  a.dt between '" + frmDt + "' and '" + toDt + "'  and  a.trantype = " + reportType + " and a.acno=b.acno and b.curbrcode='" + brnCode + "' group by a.acno  having (sum(a.dramt)>=" + amount + " or sum(a.cramt)>=" + amount + ")) "
                        + " and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' and td.curbrcode='" + brnCode + "' and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and concat(actype,custno)=substring(td.acno,3,8) "
                        + " and occupation=code and groupcode=6 and brncode='" + brnCode + "' order by acno ").getResultList();
            } else {
                acnoList = em.createNativeQuery("select td.acno,openingdt,tc.custname,tc.praddress ,tc.dob,description,ifnull(panno,'') from " + accountmaster + " td, " + customermaster + " tc,codebook cb  "
                        + " where td.acno  in( select distinct a.acno from " + recon + " a,accountmaster b where  a.dt between '" + frmDt + "' and '" + toDt + "'  and  a.trantype = " + reportType + " and a.acno=b.acno and b.curbrcode='" + brnCode + "' group by a.acno  having (sum(a.dramt)>=" + amount + " or sum(a.cramt)>=" + amount + ")) "
                        + " and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' and td.curbrcode='" + brnCode + "' and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and concat(actype,custno)=substring(td.acno,3,8) "
                        + " and occupation=code and groupcode=6 and brncode='" + brnCode + "' order by acno ").getResultList();
            }
            if (!acnoList.isEmpty()) {
                for (int i = 0; i < acnoList.size(); i++) {
                    acnoVector = (Vector) acnoList.get(i);
                    tempList = em.createNativeQuery("select ifnull(sum(cramt),0),ifnull(sum(dramt),0) from " + recon + " where acno='" + acnoVector.get(0).toString() + "' and  dt between '" + frmDt + "' and '" + toDt + "'"
                            + " and  trantype = " + reportType + " group by acno having (sum(dramt)>=" + amount + " or sum(cramt)>=" + amount + ") ").getResultList();
                    tempVector = (Vector) tempList.get(0);
                    sumCrAmt = (Double) tempVector.get(0);
                    sumDrAmt = (Double) tempVector.get(1);
                    if ((sumCrAmt >= amount && sumCrAmt != 0) || (sumDrAmt >= amount && sumDrAmt != 0)) {
                        tempList = em.createNativeQuery("select ifnull((cramt),0),ifnull((dramt),0) from " + recon + " where acno='" + acnoVector.get(0).toString() + "' and  dt between '" + frmDt + "' and '" + toDt + "' and trantype =" + reportType + " ").getResultList();
                        if (!tempList.isEmpty()) {
                            for (int j = 0; j < tempList.size(); j++) {
                                tempVector = (Vector) tempList.get(j);
                                crAmt = (Double) tempVector.get(0);
                                drAmt = (Double) tempVector.get(1);
                                TransactionAggregatePojo pojo = new TransactionAggregatePojo();
                                pojo.setAcNo(acnoVector.get(0).toString());
                                pojo.setCustName(acnoVector.get(2).toString());
                                pojo.setAddress(acnoVector.get(3).toString());
                                pojo.setCrAmt(crAmt);
                                pojo.setDob(ymdFormat.parse(acnoVector.get(4).toString()));
                                pojo.setDrAmt(drAmt);
                                pojo.setOccupation(acnoVector.get(5).toString());
                                pojo.setOpendt(ymdFormat.parse(acnoVector.get(1).toString()));
                                pojo.setPanNo(acnoVector.get(6).toString());
                                finalResultList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return finalResultList;
    }

    @Override
    public List<TransactionAggregateTotalPojo> transactionAggregateReportTotal(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode) throws ApplicationException {
        List<TransactionAggregateTotalPojo> finalResultList = new ArrayList<TransactionAggregateTotalPojo>();
        List tempList = null, acnoList = null;
        Vector tempVector = null, acnoVector = null;
        double sumCrDrAmt = 0d;
        String acctNature, accountmaster = null, customermaster = null, recon = null;
        try {
            tempList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode ='" + acType + "'").getResultList();
            tempVector = (Vector) tempList.get(0);
            acctNature = tempVector.get(0).toString();
            if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                accountmaster = "td_accountmaster";
                customermaster = "td_customermaster";
                recon = "td_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "ca_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "rdrecon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acctNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                accountmaster = "accountmaster";
                customermaster = "customermaster";
                recon = "loan_recon";
            } else if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
                accountmaster = "td_accountmaster";
                customermaster = "td_customermaster";
                recon = "of_recon";
            }
            if (acctNature.equalsIgnoreCase(CbsConstant.OF_AC) || acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                acnoList = em.createNativeQuery("select td.acno,openingdt,tc.custname,tc.praddress ,tc.dob,description,ifnull(panno,'') from " + accountmaster + " td, " + customermaster + " tc,codebook cb  "
                        + " where td.acno  in( select distinct a.acno from " + recon + " a,td_accountmaster b where  a.dt between '" + frmDt + "' and '" + toDt + "'  and  a.trantype = " + reportType + " and a.acno=b.acno and b.curbrcode='" + brnCode + "' group by a.acno  having (abs(sum(a.dramt))+abs(sum(a.cramt))>=" + amount + ") ) "
                        + " and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' and td.curbrcode='" + brnCode + "' and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and CONCAT(actype,custno)=substring(td.acno,3,8) "
                        + " and occupation=code and groupcode=6 and brncode='" + brnCode + "' order by acno ").getResultList();
            } else {
                acnoList = em.createNativeQuery("select td.acno,openingdt,tc.custname,tc.praddress ,tc.dob,description,ifnull(panno,'') from " + accountmaster + " td, " + customermaster + " tc,codebook cb  "
                        + " where td.acno  in( select distinct a.acno from " + recon + " a,accountmaster b where  a.dt between '" + frmDt + "' and '" + toDt + "'  and  a.trantype = " + reportType + " and a.acno=b.acno and b.curbrcode='" + brnCode + "' group by a.acno  having (abs(sum(a.dramt))+abs(sum(a.cramt))>=" + amount + ") ) "
                        + " and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' and td.curbrcode='" + brnCode + "' and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and CONCAT(actype,custno)=substring(td.acno,3,8) "
                        + " and occupation=code and groupcode=6 and brncode='" + brnCode + "' order by acno ").getResultList();
            }
            if (!acnoList.isEmpty()) {
                for (int i = 0; i < acnoList.size(); i++) {
                    acnoVector = (Vector) acnoList.get(i);
                    tempList = em.createNativeQuery("select abs(ifnull(sum(dramt),0))+abs(ifnull(sum(cramt),0)) from " + recon + " where acno='" + acnoVector.get(0).toString() + "' and  dt between '" + frmDt + "' and '" + toDt + "'"
                            + " and  trantype = " + reportType + " ").getResultList();
                    if (!tempList.isEmpty()) {
                        tempVector = (Vector) tempList.get(0);
                        sumCrDrAmt = (Double) tempVector.get(0);
                        TransactionAggregateTotalPojo pojo = new TransactionAggregateTotalPojo();
                        pojo.setAcNo(acnoVector.get(0).toString());
                        pojo.setCustName(acnoVector.get(2).toString());
                        pojo.setAddress(acnoVector.get(3).toString());
                        pojo.setDob(ymdFormat.parse(acnoVector.get(4).toString()));
                        pojo.setOccupation(acnoVector.get(5).toString());
                        pojo.setOpendt(ymdFormat.parse(acnoVector.get(1).toString()));
                        pojo.setPanNo(acnoVector.get(6).toString());
                        pojo.setCrDrAmt(sumCrDrAmt);
                        finalResultList.add(pojo);
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
    public List<ExceptionReportPojo> exceptionTrnasctionReport(String todt, String brncode) throws ApplicationException {
        List<ExceptionReportPojo> finalList = new ArrayList<ExceptionReportPojo>();
        try {
//            List resultca = em.createNativeQuery("select a.acno,a.custname,a.odlimit,(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))*(-1)  from ca_recon r,accountmaster a "
//                    + " where a.acno in (select acno from ca_recon  where date_format(dt,'%Y%m%d') = date_format('" + todt + "','%Y%m%d')  and ty = 1) and a.acno=r.acno "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
//                    + " group by a.acno,a.custname,a.odlimit having (sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0))) < 0 and a.odlimit<(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))*(-1)").getResultList();
//            if (!resultca.isEmpty()) {
//                for (int i = 0; i < resultca.size(); i++) {
//                    Vector vtr1 = (Vector) resultca.get(i);
//                    List result2 = em.createNativeQuery("select dramt,enterby,authby from ca_recon where date_format(dt,'%Y%m%d') ='" + todt + "' and acno ='" + vtr1.get(0).toString() + "' and ty = 1   ").getResultList();
//                    if (!result2.isEmpty()) {
//                        for (int j = 0; j < result2.size(); j++) {
//                            Vector vtr2 = (Vector) result2.get(j);
//                            ExceptionReportPojo row = new ExceptionReportPojo();
//                            row.setAcNo(vtr1.get(0).toString());
//                            row.setCustName(vtr1.get(1).toString());
//                            row.setOdLimit(Double.parseDouble(vtr1.get(2).toString()));
//                            row.setBalance(Double.parseDouble(vtr1.get(3).toString()));
//                            row.setDebitAmt(Double.parseDouble(vtr2.get(0).toString()));
//                            row.setEnterBy(vtr2.get(1).toString());
//                            row.setAuthBy(vtr2.get(2).toString());
//                            finalList.add(row);
//                        }
//                    }
//
//                }
//            }
//            List resultla = em.createNativeQuery("select la.acno,la.custname,ifnull((sum(ifnull(cramt,0))-sum(ifnull(dramt,0))),0)*(-1),sanctionlimit "
//                    + " from loan_recon lr,loan_appparameter la,accountmaster a where ty = 1 and la.acno = lr.acno "
//                    + "and la.acno=a.acno "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
//                    + "group by la.acno,la.sanctionlimit,la.custname having date_format(min(dt),'%Y%m%d') ='" + todt + "'").getResultList();
//            if (!resultla.isEmpty()) {
//                for (int i = 0; i < resultla.size(); i++) {
//                    Vector vtr1 = (Vector) resultla.get(i);
//                    List result2 = em.createNativeQuery("select ifnull(sum(dramt),0),enterby,authby from loan_recon where dt ='" + todt + "' and acno ='" + vtr1.get(0).toString() + "' and ty = 1 group by enterby,authby").getResultList();
//                    if (!result2.isEmpty()) {
//                        for (int j = 0; j < result2.size(); j++) {
//                            Vector vtr2 = (Vector) result2.get(j);
//                            ExceptionReportPojo row = new ExceptionReportPojo();
//                            row.setAcNo(vtr1.get(0).toString());
//                            row.setCustName(vtr1.get(1).toString());
//                            row.setOdLimit(Double.parseDouble(vtr1.get(2).toString()));
//                            row.setBalance(Double.parseDouble(vtr1.get(3).toString()));
//                            row.setDebitAmt(Double.parseDouble(vtr2.get(0).toString()));
//                            row.setEnterBy(vtr2.get(1).toString());
//                            row.setAuthBy(vtr2.get(2).toString());
//                            finalList.add(row);
//                        }
//                    }
//                }
//            }
//            List lacust = em.createNativeQuery("select a.acno,a.custname,a.odlimit,(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))*(-1)"
//                    + "from loan_recon r,accountmaster a where a.acno in (select acno from loan_recon  where date_format(dt,'%Y%m%d') ='" + todt + "' and ty = 1) and a.acno=r.acno "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
//                    + "group by a.acno,a.custname,a.odlimit having (sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0))) < 0 and a.odlimit<(sum(ifnull(r.cramt,0))-sum(ifnull(r.dramt,0)))*(-1)").getResultList();
//            if (!lacust.isEmpty()) {
//                for (int i = 0; i < lacust.size(); i++) {
//                    Vector vtr1 = (Vector) lacust.get(i);
//                    List result2 = em.createNativeQuery("select dramt,enterby,authby from loan_recon where date_format(dt,'%Y%m%d')='" + todt + "' and acno='" + vtr1.get(0).toString() + "' and ty = 1").getResultList();
//                    if (!result2.isEmpty()) {
//                        for (int j = 0; j < result2.size(); j++) {
//                            Vector vtr2 = (Vector) result2.get(j);
//                            ExceptionReportPojo row = new ExceptionReportPojo();
//                            row.setAcNo(vtr1.get(0).toString());
//                            row.setCustName(vtr1.get(1).toString());
//                            row.setOdLimit(Double.parseDouble(vtr1.get(2).toString()));
//                            row.setBalance(Double.parseDouble(vtr1.get(3).toString()));
//                            row.setDebitAmt(Double.parseDouble(vtr2.get(0).toString()));
//                            row.setEnterBy(vtr2.get(1).toString());
//                            row.setAuthBy(vtr2.get(2).toString());
//                            finalList.add(row);
//                        }
//                    }
//                }
//            }
            //
            List purchaseList = em.createNativeQuery("select acno,billtype,instno,instamount,instentrydt,enterby,authby from bill_obcprocessed where date_format(instentrydt,'%Y%m%d')='" + todt + "' and billtype in('CHQ','PO','DD')").getResultList();
            if (!purchaseList.isEmpty()) {
                for (int i = 0; i < purchaseList.size(); i++) {
                    Vector vtr1 = (Vector) purchaseList.get(i);
                    String accnature = vtr1.get(0).toString().substring(3, 5);
                    if (accnature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        List result2 = em.createNativeQuery("select acname from gltable where acno='" + vtr1.get(0).toString() + "'").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr1.get(0).toString());
                                row.setCustName(vtr2.get(0).toString());
                                row.setBillType(vtr1.get(1).toString());
                                row.setInstNo(vtr1.get(2).toString());
                                row.setInstAmount(Double.parseDouble(vtr1.get(3).toString()));
                                row.setInstEntryDt(ymdFormat.parse(vtr1.get(4).toString()));
                                row.setEnterBy(vtr1.get(5).toString());
                                row.setAuthBy(vtr1.get(6).toString());
                                row.setHeader("TODAYS PURCHASED BILLS");
                                finalList.add(row);
                            }
                        }
                    } else {
                        List result2 = em.createNativeQuery("select custname from accountmaster where acno='" + vtr1.get(0).toString() + "'").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr1.get(0).toString());
                                row.setCustName(vtr2.get(0).toString());
                                row.setBillType(vtr1.get(1).toString());
                                row.setInstNo(vtr1.get(2).toString());
                                row.setInstAmount(Double.parseDouble(vtr1.get(3).toString()));
                                row.setInstEntryDt(ymdFormat.parse(vtr1.get(4).toString()));
                                row.setEnterBy(vtr1.get(5).toString());
                                row.setAuthBy(vtr1.get(6).toString());
                                row.setHeader("TODAYS PURCHASED BILLS");
                                finalList.add(row);
                            }
                        }
                    }
                }
            } /**
             * ********************************** Added by Shipra(missing code)
             * ******************************************
             */
            else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("TODAYS PURCHASED BILLS");
                finalList.add(row);
            }

            /**
             * *******************************************************************************
             */
            List chequeList = em.createNativeQuery("select distinct(acctnature),acctcode  from accounttypemaster where acctcode is not null order by acctnature").getResultList();
            if (!chequeList.isEmpty()) {
                for (int i = 0; i < chequeList.size(); i++) {
                    Vector vtr1 = (Vector) chequeList.get(i);
                    String accnatue = vtr1.get(0).toString();
                    String actype = vtr1.get(1).toString();
                    if (accnatue.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from recon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from ca_recon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from rdrecon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from gl_recon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.FIXED_AC) || accnatue.equalsIgnoreCase(CbsConstant.MS_AC) || accnatue.equalsIgnoreCase(CbsConstant.OF_AC)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from td_recon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.TERM_LOAN) || accnatue.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from loan_recon r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else if (accnatue.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        List result2 = em.createNativeQuery("select a.acno,a.custname,r.cramt,r.dramt,r.instno,r.details, "
                                + " r.enterby,r.authby  from ddstransaction r, accountmaster a where date_format(dt,'%Y%m%d') ='" + todt + "' and payby = 4 "
                                + "and auth = 'y' and r.acno = a.acno "
                                + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ")
                                + "and substring(a.acno,3,2)= '" + actype + "' ").getResultList();
                        if (!result2.isEmpty()) {
                            for (int j = 0; j < result2.size(); j++) {
                                Vector vtr2 = (Vector) result2.get(j);
                                ExceptionReportPojo row = new ExceptionReportPojo();
                                row.setAcNo(vtr2.get(0).toString());
                                row.setCustName(vtr2.get(1).toString());
                                row.setOdLimit(Double.parseDouble(vtr2.get(2).toString()));
                                row.setDebitAmt(Double.parseDouble(vtr2.get(3).toString()));
                                row.setInstNo(vtr2.get(4).toString());
                                row.setDetails(vtr2.get(5).toString());
                                row.setEnterBy(vtr2.get(6).toString());
                                row.setAuthBy(vtr2.get(7).toString());
                                row.setHeader("TODAYS USED LOOSE CHEQUES");
                                finalList.add(row);
                            }
                        }
                    } else {
                        ExceptionReportPojo row = new ExceptionReportPojo();
                        row.setHeader("TODAYS USED LOOSE CHEQUES");
                        finalList.add(row);
                    }
                }
            }//end of loose cheque detail
            ExceptionReportPojo row1 = new ExceptionReportPojo();
            row1.setHeader("TODAYS USED LOOSE CHEQUES");
            finalList.add(row1);

//            List security = em.createNativeQuery("select a.acno,b.custname ,a.cramt,a.dramt,a.instno,a.details,a.enterby,a.authby "
//                    + "from ca_recon a,accountmaster b where a.acno=b.acno and date_format(a.dt,'%Y%m%d')='" + todt + "' and iy=95 " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and b.curbrcode in ('" + brncode + "') ") + " order by a.acno").getResultList();

            List security = em.createNativeQuery("select a.acno,custname,date_format(max(matdate),'%d/%m/%Y') matDt,securitychg,status,aa.balance \n"
                    + "from loansecurity s, accountmaster a,cbs_loan_acc_mast_sec b,cbs_scheme_general_scheme_parameter_master c ,(\n"
                    + "select acNo,balance from (\n"
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from ca_recon where dt<='" + todt + "' group by acno\n"
                    + "union \n"
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from loan_recon where dt<='" + todt + "' group by acno\n"
                    + ")a\n"
                    + ")aa\n"
                    + "where status='EXPIRED' " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ") + " "
                    + "and ((securitytype is null and lienacno <> '' ) or securitytype='DATED') and a.openingdt<='" + todt + "' \n"
                    + "and (a.closingdate< '" + todt + "' or closingdate='' or ifnull(a.closingdate,'')='')  and matdate = '" + todt + "' \n"
                    + "and s.acno=a.acno and s.acno=b.acno\n"
                    + "and a.acno = aa.acNo \n"
                    + "and b.scheme_code = c.scheme_code \n"
                    + "and substring(s.acno,3,2) in(select acctcode from accounttypemaster where acctnature in"
                    + "('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "')) \n"
                    + " group by a.acno order by s.SecurityOption,a.acno").getResultList();

            if (!security.isEmpty()) {
                for (int i = 0; i < security.size(); i++) {
                    Vector vtr = (Vector) security.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy 
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setBalance(Math.abs(Double.parseDouble(vtr.get(5).toString())));
//                    row.setOdLimit(Double.parseDouble(vtr.get(2).toString()));
//                    row.setDebitAmt(Double.parseDouble(vtr.get(3).toString()));
//                    row.setInstNo(vtr.get(4).toString());
//                    row.setDetails(vtr.get(5).toString());
                    row.setDetails("EXPIRED DATE : " + vtr.get(2).toString());
//                    row.setAuthBy(vtr.get(7).toString());
                    row.setHeader("SECURITY EXPIRED");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("SECURITY EXPIRED");
                finalList.add(row);
            }
            //select a.acno,b.custname ,a.cramt,a.dramt,a.instno,a.details,a.enterby,a.authby from ca_recon a,accountmaster b where a.acno=b.acno and date_format(a.dt,'%Y%m%d')=@tDate  and iy=96 and substring(a.acno,1,2)=@brCode ORDER BY A.ACNO
//            List insurance = em.createNativeQuery("select a.acno,b.custname ,a.cramt,a.dramt,a.instno,a.details,a.enterby,a.authby "
//                    + "from ca_recon a,accountmaster b where a.acno=b.acno and date_format(a.dt,'%Y%m%d')='" + todt + "'  and iy=96 "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and b.curbrcode in ('" + brncode + "') ")
//                    + " order by a.acno").getResultList();

            List insurance = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),lp.custname,InsComName,CoverNoteNo  \n"
                    + "from loan_insurance l,loan_appparameter lp \n"
                    + "where l.acno=lp.acno and upper(status)='EXPIRED'  " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and substring(l.acno ,1,2) in ('" + brncode + "') ") + " "
                    + "and todt= '" + todt + "' order by l.todt,lp.acno").getResultList();

            if (!insurance.isEmpty()) {
                for (int i = 0; i < insurance.size(); i++) {
                    Vector vtr = (Vector) insurance.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(6).toString());

//                    row.setOdLimit(Double.parseDouble(vtr.get(2).toString()));
//                    row.setDebitAmt(Double.parseDouble(vtr.get(3).toString()));
//                    row.setInstNo(vtr.get(4).toString());
                    row.setDetails(vtr.get(4).toString() + " : Premium Amt : " + vtr.get(2).toString() + ": Date: " + vtr.get(3).toString());
//                    row.setEnterBy(vtr.get(6).toString());
//                    row.setAuthBy(vtr.get(7).toString());
                    row.setHeader("LOAN INSURANCE EXPIRED");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("LOAN INSURANCE EXPIRED");
                finalList.add(row);
            }
            //select a.acno,b.custname ,a.cramt,a.dramt,a.instno,a.details,a.enterby,a.authby from ca_recon a,accountmaster b where a.acno=b.acno and date_format(a.dt,'%Y%m%d')=@tDate and iy=97 and substring(a.acno,1,2)=@brCode ORDER BY A.ACNO
//            List sancion = em.createNativeQuery("select a.acno,b.custname ,a.cramt,a.dramt,a.instno,a.details,a.enterby,a.authby "
//                    + "from ca_recon a,accountmaster b where a.acno=b.acno and date_format(a.dt,'%Y%m%d')='" + todt + "'  and iy=97 "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and b.curbrcode in ('" + brncode + "') ")
//                    + "order by a.acno").getResultList();
            List sancion = em.createNativeQuery("select a.ACNo,a.custname,date_format(Sanctionlimitdt,'%d/%m/%Y'),"
                    + "LOAN_DURATION,date_format(DATE_ADD(Sanctionlimitdt, INTERVAL LOAN_DURATION month),'%d/%m/%Y') ExpiryDate,"
                    + "cast(Sanctionlimit as decimal(25,2)),balance \n"
                    + "from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c,(select acNo,balance from (\n"
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from ca_recon where dt<='" + todt + "' group by acno\n"
                    + "union "
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from loan_recon where dt<='" + todt + "' group by acno\n"
                    + ")a)d, accounttypemaster e "
                    + "where a.acno = b.acno and a.acno = c.ACC_NO and a.acno = d.acNo "
                    + "and substring(a.acno,3,2) = e.AcctCode and e.acctnature in ('TL','DL') " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ") + ""
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + todt + "') "
                    + "and DATE_ADD(Sanctionlimitdt, INTERVAL LOAN_DURATION month)= '" + todt + "'"
                    + " union all "
                    + "select a.ACNo,a.custname,date_format(Sanctionlimitdt,'%d/%m/%Y'),"
                    + "LOAN_DURATION,date_format(c.DOCUMENT_EXP_DATE,'%d/%m/%Y') ExpiryDate,"
                    + "cast(Sanctionlimit as decimal(25,2)),balance "
                    + "from accountmaster a,loan_appparameter b,cbs_loan_borrower_details c,(select acNo,balance from (\n"
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from ca_recon where dt<='" + todt + "' group by acno "
                    + "union  "
                    + "select acno acNo,cast(sum(cramt-dramt) as decimal (25,2)) balance from loan_recon where dt<='" + todt + "' group by acno "
                    + ")a)d, accounttypemaster e "
                    + "where a.acno = b.acno and a.acno = c.ACC_NO and a.acno = d.acNo "
                    + "and substring(a.acno,3,2) = e.AcctCode and e.acctnature in ('CA') and e.crdbflag in('D','B') " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and a.curbrcode in ('" + brncode + "') ") + ""
                    + "and (closingdate='' or ifnull(closingdate,'')='' or closingdate >'" + todt + "') "
                    + "and c.DOCUMENT_EXP_DATE = '" + todt + "' "
                    + "").getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setBalance(Math.abs(Double.parseDouble(vtr.get(6).toString())));

//                    row.setOdLimit(Double.parseDouble(vtr.get(2).toString()));
//                    row.setDebitAmt(Double.parseDouble(vtr.get(3).toString()));
//                    row.setInstNo(vtr.get(4).toString());
                    row.setDetails("EXPIRED DATE :" + vtr.get(4).toString());
//                    row.setEnterBy(vtr.get(6).toString());
//                    row.setAuthBy(vtr.get(7).toString());
                    row.setHeader("SANCTION DATE EXPIRED");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("SANCTION DATE EXPIRED");
                finalList.add(row);
            }
            /*Point 6*/
            String acQuery = "select a.acno, ac.custname, ac.odlimit, atm.acctnature, cb.Description, a.REMARK,"
                    + " concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']') as enterBy, "
                    + " concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']') as authBy, a.EFFDT "
                    + " from accountstatus a, accountmaster ac, accounttypemaster atm, codebook cb  "
                    + " where  a.ACNO = ac.ACNo and a.SPFLAG = cb.Code "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and cb.groupcode = 3 and  a.AUTH = 'Y' and "
                    + " substring(a.acno,3,2) = atm.acctcode and  date_format(a.effdt,'%Y%m%d')='" + todt + "'"
                    + " and  (a.ENTERBY<>'SYSTEM' and a.AUTHBY<> 'SYSTEM' and a.ENTERBY<>a.AUTHBY) and "
                    + " substring(ac.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','SB','TL','DL','FD','MS','DS')) "
                    + " order by a.acno, a.SPNO";
//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo(vtr.get(4).toString());
                    row.setDetails(vtr.get(5).toString());
                    row.setEnterBy(vtr.get(6) == null ? "" : vtr.get(6).toString());
                    row.setAuthBy(vtr.get(7) == null ? "" : vtr.get(7).toString());
                    row.setHeader("ACCOUNT STATUS CHANGE");
                    List crDrList = em.createNativeQuery("select ifnull(sum(cramt),0) , ifnull(sum(dramt),0) from " + common.getTableName(vtr.get(3).toString()) + " where acno ='" + vtr.get(0).toString() + "' and dt ='" + todt + "'").getResultList();
                    if (!crDrList.isEmpty()) {
                        Vector crDrVect = (Vector) crDrList.get(0);
                        row.setOdLimit(Double.parseDouble(crDrVect.get(0).toString()));
                        row.setDebitAmt(Double.parseDouble(crDrVect.get(1).toString()));
                    }
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("ACCOUNT STATUS CHANGE");
                finalList.add(row);
            }

            /*Point 7*/
            acQuery = " select a.Acno, b.custname as name, a.TranTime as TranTime, cast(IF(cramt<>0, cramt, dramt) as decimal(25,2)) as amount, IF(cramt<>0, 'Cr', 'Dr') as ty ,ifnull(a.Details,'') as details, "
                    + " concat(a.Enterby,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.Deletedby,' [', (select si.UserName from securityinfo si where si.UserId = a.Deletedby) ,']') as authBy, ifnull(a.TrsNo,'') as batchNo, ifnull(a.IDno,'') as recno "
                    + " from deletetrans a, accountmaster b where a.acno = b.acno and  date_format(a.deletedt,'%Y%m%d')='" + todt + "' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " union all "
                    + " select a.Acno,b.custname as name, a.TranTime as TranTime, cast(IF(cramt<>0, cramt, dramt) as decimal(25,2)) as amount, IF(cramt<>0, 'Cr', 'Dr') as ty ,ifnull(a.Details,'') as details, "
                    + " concat(a.Enterby,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.Deletedby,' [', (select si.UserName from securityinfo si where si.UserId = a.Deletedby) ,']') as authBy, ifnull(a.TrsNo,'') as batchNo, ifnull(a.IDno,'') as recno "
                    + " from deletetrans a, td_accountmaster b where a.acno = b.acno and   date_format(a.deletedt,'%Y%m%d')='" + todt + "' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " union all "
                    + " select a.Acno, b.acname as name, a.TranTime as TranTime, cast(IF(cramt<>0, cramt, dramt) as decimal(25,2)) as amount, IF(cramt<>0, 'Cr', 'Dr') as ty ,ifnull(a.Details,'') as details, "
                    + " concat(a.Enterby,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.Deletedby,' [', (select si.UserName from securityinfo si where si.UserId = a.Deletedby) ,']') as authBy, ifnull(a.TrsNo,'') as batchNo, ifnull(a.IDno,'') as recno "
                    + " from deletetrans a, gltable b where a.acno = b.acno and   date_format(a.deletedt,'%Y%m%d')='" + todt + "' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " order by TranTime, batchNo, recno, acno ";
//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setOdLimit(vtr.get(4).toString().equalsIgnoreCase("Cr") ? Double.parseDouble(vtr.get(3).toString()) : 0d);
                    row.setDebitAmt(vtr.get(4).toString().equalsIgnoreCase("Dr") ? Double.parseDouble(vtr.get(3).toString()) : 0d);
                    row.setInstNo("");
                    row.setDetails(vtr.get(5).toString().concat(vtr.get(5).toString().equalsIgnoreCase("") ? (" Batch No: " + vtr.get(5).toString()) : ""));
                    row.setEnterBy(vtr.get(6).toString());
                    row.setAuthBy(vtr.get(7).toString());
                    row.setHeader("REJECTED/DELETED TRANSACTIONS");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("REJECTED/DELETED TRANSACTIONS");
                finalList.add(row);
            }

            /*Point 9*/
            acQuery = "select  aa.acno,aa.CustId,aa.custname, aa.pan,aa.dt,   aa.bal,  aa.description, aa.details, aa.enterBy, aa.authBy  from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,mm.dt,   mm.bal,  'Deposit between 49000-49999' as description, mm.details, mm.enterBy, mm.authBy  from "
                    + " (select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  0 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) and a.TranType = 0    "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  1 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and a.TranType = 0 "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  2  as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  3 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  4 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  5 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  6 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 49000 and 49999 ) mm "
                    + " ) aa group by aa.acno order by aa.description, aa.acno";
//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(2).toString());
                    row.setOdLimit(Double.parseDouble(vtr.get(5).toString()));
                    row.setInstNo(vtr.get(3).toString());
                    row.setDetails(vtr.get(7).toString());
                    row.setEnterBy(vtr.get(8).toString());
                    row.setAuthBy(vtr.get(9).toString());
                    row.setHeader("DEPOSIT BETWEEN 49000 TO 49999");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("DEPOSIT BETWEEN 49000 TO 49999");
                finalList.add(row);
            }
            /*Point 9*/
            acQuery = "select  aa.acno,aa.CustId,aa.custname, aa.pan,aa.dt,   aa.bal,  aa.description, aa.details, aa.enterBy, aa.authBy  from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,mm.dt,   mm.bal,  'Deposit between 900000-999999' as description, mm.details, mm.enterBy, mm.authBy  from "
                    + " (select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  0 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) and a.TranType = 0    "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  1 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and a.TranType = 0 "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  2  as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  3 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  4 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  5 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cramt as bal, c.PAN_GIRNumber as pan, c.custname,  6 as tab, a.dt, a.details,"
                    + " concat(a.Enterby  ,' [', (select si.UserName from securityinfo si where si.UserId = a.Enterby),   ']') as enterBy, "
                    + " concat(a.authBy,' [', (select si.UserName from securityinfo si where si.UserId = a.authBy) ,']') as authBy  "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and a.TranType = 0   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cramt between 900000 and 999999 ) mm "
                    + " ) aa group by aa.acno order by aa.description, aa.acno";
//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(2).toString());
                    row.setOdLimit(Double.parseDouble(vtr.get(5).toString()));
                    row.setInstNo(vtr.get(3).toString());
                    row.setDetails(vtr.get(7).toString());
                    row.setEnterBy(vtr.get(8).toString());
                    row.setAuthBy(vtr.get(9).toString());
                    row.setHeader("DEPOSIT BETWEEN 900000 TO 999999");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("DEPOSIT BETWEEN 900000 TO 999999");
                finalList.add(row);
            }

            /*Point 14*/
            acQuery = "select  aa.acno,aa.CustId,aa.custname, aa.pan,  aa.bal,  aa.description, aa.enterBy, aa.authBy from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,  mm.bal,  '(i) balance of rupees fifty thousand or more but donot have PAN or FORM 60' as description, mm.enterBy, mm.authBy  from "
                    + " (select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  0 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' AND c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all  "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  1 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  2  as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') and c.PAN_GIRNumber <> 'Form 61'")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  3 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  4 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  5 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  6 as tab ,"
                    + " ifnull(concat(c.RecordCreaterID  ,' [', (select si.UserName from securityinfo si where si.UserId = c.RecordCreaterID),   ']'),'') as enterBy, "
                    + " ifnull(concat(c.LastChangeUserID,' [', (select si.UserName from securityinfo si where si.UserId = c.LastChangeUserID) ,']'),'') as authBy "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c where a.ACNo = b.Acno and b.CustId = c.customerid and date_format(a.dt,'%Y%m%d')='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA')) and "
                    + " c.PAN_GIRNumber NOT REGEXP '[A-Z][A-Z][A-Z][A-Z][A-Z][0-9][0-9][0-9][0-9][A-Z]' and c.PAN_GIRNumber <> 'Form 60' and c.PAN_GIRNumber <> 'Form 61'"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 ) mm "
                    + " ) aa group by aa.acno order by aa.description, aa.acno";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(2).toString());
                    row.setInstNo(vtr.get(3).toString());
                    row.setDetails("Cust ID: " + vtr.get(1).toString());
                    row.setEnterBy(vtr.get(6).toString());
                    row.setAuthBy(vtr.get(7).toString());
                    row.setHeader("ACCOUNT DONOT HAVE PAN/FORM60/FORM61");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("ACCOUNT DONOT HAVE PAN/FORM60/FORM61");
                finalList.add(row);
            }
            /*Point 15*/
            acQuery = " select  aa.acno,aa.CustId,aa.custname, aa.pan,  aa.bal,  aa.description from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,  mm.bal,  mm.description from "
                    + " (select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed CA account have balance' as description "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA') and crdbflag = 'C')  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed SB account have balance' as description "
                    + " from recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed RD account have balance' as description "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))   "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed FD/MS account have balance' as description "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c, td_accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' and  a.CloseFlag is null and a.TranType <>27"
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed Loan/Advance account have balance' as description "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed DDS account have balance' as description "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Closed OF account have balance' as description "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "'  and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.accstatus = 9 and date_format(STR_TO_DATE(d.ClosingDate,'%Y%m%d'),'%Y%m%d') <='" + todt + "' ) and a.auth = 'Y' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<>0 ) mm "
                    + " ) aa group by aa.acno order by aa.description, aa.acno ";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(2).toString());
                    row.setBalance(Double.parseDouble(vtr.get(4).toString()));
                    row.setInstNo("");
                    row.setDetails("Cust ID: " + vtr.get(1).toString());
                    row.setEnterBy("");
                    row.setAuthBy("");
                    row.setHeader("CLOSED ACCOUNT HAVE BALANCE");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("CLOSED ACCOUNT HAVE BALANCE");
                finalList.add(row);
            }
            /*Point 15*/
            acQuery = "select a.acno, a.acname, b.Details,cast(b.DrAmt as decimal(25,2)),"
                    + " ifnull(concat(b.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = b.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(b.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = b.AUTHBY) ,']'),'') as authBy "
                    + " from gltable a, gl_recon b "
                    + " where a.AcNo = b.ACNo and substring(a.acno,5,1) = '3' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and b.Ty = 1 and date_format(b.dt,'%Y%m%d')='" + todt + "'  and "
                    + " enterby<> 'SYSTEM' and  authby <> 'SYSTEM' group by a.acno";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setDebitAmt(Double.parseDouble(vtr.get(3).toString()));
                    row.setInstNo("");
                    row.setDetails(vtr.get(2).toString());
                    row.setEnterBy(vtr.get(4).toString());
                    row.setAuthBy(vtr.get(5).toString());
                    row.setHeader("INCOME GL HEAD ARE MANUALLY DEBITED BY BANKERS");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("INCOME GL HEAD ARE MANUALLY DEBITED BY BANKERS");
                finalList.add(row);
            }
            /*Point 17*/
            acQuery = "select  aa.acno,aa.CustId,aa.custname, aa.pan,  aa.cramt, aa.dramt, aa.bal, aa.trDetails,  aa.description, aa.details, aa.enterBy, aa.authBy from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,  mm.bal,mm.cramt, mm.dramt, mm.trDetails,  mm.description, mm.details, mm.enterBy, mm.authBy from "
                    + " (select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,   'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c, td_accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' and  a.CloseFlag is null and a.TranType <>27 "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y'  "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' "
                    + " group by a.acno  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal,a.CrAmt, a.DrAmt, c.PAN_GIRNumber as pan, c.custname, a.details as trDetails,  'Value Dated Transactions' as description, concat('Date of Transaction:',date_format(a.Dt,'%d/%m/%Y'),'; Value Dt:',date_format(a.ValueDt,'%d/%m/%Y')) as details "
                    + ",ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and a.Dt<>a.ValueDt and  a.enterby<> 'SYSTEM' and  a.authby <> 'SYSTEM' and a.auth = 'Y' "
                    + " group by a.acno  ) mm "
                    + ") aa group by aa.acno order by aa.description, aa.acno";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(2).toString());
                    row.setOdLimit(Double.parseDouble(vtr.get(4).toString()));
                    row.setDebitAmt(Double.parseDouble(vtr.get(5).toString()));
                    row.setInstNo("");
                    row.setDetails(vtr.get(7).toString().concat(" ").concat(vtr.get(9).toString()));
                    row.setEnterBy(vtr.get(10).toString());
                    row.setAuthBy(vtr.get(11).toString());
                    row.setHeader("VALUE DATED TRANSACTIONS DONE BY BANKERS");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("VALUE DATED TRANSACTIONS DONE BY BANKERS");
                finalList.add(row);
            }

            /*Point 19*/
            acQuery = "select  aa.acno,aa.CustId,aa.custname, aa.pan,  aa.bal,  aa.description from ("
                    + " select mm.CustId, mm.acno,mm.custname, mm.pan,  mm.bal,  mm.description from "
                    + " (select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'CA account have debit balance' as description "
                    + " from ca_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA') and crdbflag = 'C')  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'SB account have debit balance' as description "
                    + " from recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '')   "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'RD account have debit balance' as description "
                    + " from rdrecon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))   "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'FD/MS account have debit balance' as description "
                    + " from td_recon a, customerid b, cbs_customer_master_detail c, td_accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' "
                    + " and substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'Loan/Advance account have Credit balance' as description "
                    + " from loan_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))>0  "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'DDS account have debit balance' as description "
                    + " from ddstransaction a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 "
                    + " union all "
                    + " select b.CustId, a.acno as acno, cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2)) as bal, c.PAN_GIRNumber as pan, c.custname,  'OF account have debit balance' as description "
                    + " from of_recon a, customerid b, cbs_customer_master_detail c, accountmaster d where a.acno = d.acno and  a.ACNo = b.Acno and b.CustId = c.customerid and  date_format(a.dt,'%Y%m%d')<='" + todt + "' and "
                    + " substring(a.acno,3,2) in (select acctcode from accounttypemaster where acctnature not in ('CA'))  "
                    + " and (d.ClosingDate >'" + todt + "' or d.ClosingDate is null or d.ClosingDate = '') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno having cast(sum(cramt) as decimal(25,2))-cast(sum(dramt) as decimal(25,2))<0 ) mm "
                    + " ) aa group by aa.acno order by aa.description, aa.acno";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setBalance(Double.parseDouble(vtr.get(4).toString()));
                    row.setInstNo("");
                    row.setDetails(vtr.get(5).toString());
                    row.setEnterBy("");
                    row.setAuthBy("");
                    row.setHeader("ACCOUNT HAVE BALANCE NOT AS PER THEIR NATURE");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("ACCOUNT HAVE BALANCE NOT AS PER THEIR NATURE");
                finalList.add(row);
            }

            /*Point 20*/
            acQuery = "select a.acno, c.custname, cast(a.aclimit as decimal(25,2)), cast(b.ODLimit as decimal(25,2)),a.enterdate, "
                    + " cast(a.adhoclimit as decimal(25,2)), a.AdhocApplicableDt, cast(a.MaxLimit as decimal(25,2)),"
                    + " ifnull(concat(a.ENTERBY,' [', (select si.UserName from securityinfo si where si.UserId = a.ENTERBY),']'),'') as enterBy, "
                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy "
                    + " from loan_oldinterest a, loan_appparameter b, accountmaster c where a.acno = c.ACNo and a.acno = b.Acno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " and date_format(a.enterdate,'%Y%m%d') ='" + todt + "'  order by a.acno, a.enterdate, a.TXNID ";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo("");
                    row.setDetails("Previous:" + vtr.get(2).toString() + "; Current:" + vtr.get(3).toString());
                    row.setEnterBy(vtr.get(8).toString());
                    row.setAuthBy(vtr.get(9).toString());
                    row.setHeader("CC/OD LIMIT MODIFIED");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("CC/OD LIMIT MODIFIED");
                finalList.add(row);
            }

            /*Point 21*/
            acQuery = "select distinct a.ACNO, b.custname, count(a.acno) ,"
                    + " ifnull(concat(a.CREATED_BY,' [', (select si.UserName from securityinfo si where si.UserId = a.CREATED_BY),']'),'') as enterBy, "
                    + " ifnull(concat(a.UPDATED_BY,' [', (select si.UserName from securityinfo si where si.UserId = a.UPDATED_BY) ,']'),'') as authBy  "
                    + " from cbs_acc_int_rate_details a, accountmaster b where  a.ACNO = b.ACNo and substring(a.ACNO,3,2) in "
                    + " (select acctcode from accounttypemaster where acctnature in ('CA','TL','DL') and crdbflag in ('B','D')) "
                    + " and MOD_CNT >0 and date_format(a.UPDATED_DT,'%Y%m%d') = '" + todt + "' "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
                    + " group by a.acno";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo("");
                    row.setDetails("No. of modification:" + vtr.get(2).toString());
                    row.setEnterBy(vtr.get(3).toString());
                    row.setAuthBy(vtr.get(4).toString());
                    row.setHeader("LOAN ROI MODIFIED");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("LOAN ROI MODIFIED");
                finalList.add(row);
            }

            /*Point 23*/
            acQuery = "select aa.acno, aa.custname, ci.CustId  from accountmaster aa, customerid ci  where  aa.acno = ci.acno and substring(aa.acno,3,2) in "
                    + " (select acctcode from accounttypemaster where acctnature in ('CA','TL','DL') and crdbflag in ('B','D')) "
                    + " and aa.accstatus not in (11,12,13) and (aa.closingdate is null or aa.closingdate = '' or aa.closingdate > '" + todt + "') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(aa.acno,1,2) in ('" + brncode + "') ")
                    + " and ci.CustId in ("
                    + " select ci.custid from accountstatus a, "
                    + " (select ast.acno as npaAcno,ast.effdt as npaEffDt,ast.spflag as npaSpflag  from accountstatus ast, "
                    + " (select aa.acno as aano ,aa.effdt as aadt,max(spno)  as sno from accountstatus aa, "
                    + " (select acno as ano, max(effdt) as dt from accountstatus where effdt  between '19000101' and '" + todt + "' and SPFLAG IN (11,12,13)  group by acno) b "
                    + "  where aa.acno = b.ano and aa.effdt = b.dt and aa.SPFLAG IN (11,12,13) "
                    + " group by aa.acno,aa.effdt) c where ast.acno = c.aano and ast.effdt = c.aadt and ast.spno = c.sno) npa, "
                    + " (select acno,max(spno) as sno from accountstatus where effdt <='" + todt + "' group by acno) c , "
                    + " accountmaster ac, accounttypemaster atm, codebook cb, customerid ci  where  ac.acno = ci.acno and  a.acno = c.acno and a.acno = ac.acno  and a.acno = npa.npaAcno  "
                    + " and npa.npaSpFlag = cb.code and  cb.groupcode = 3 and  a.spflag in (11,12,13) and  "
                    + " substring(a.acno,3,2) = atm.acctcode and a.effdt = npa.npaEffDt and a.spno = c.sno  and a.effdt <='" + todt + "' and  "
                    + " (ac.closingdate is null or ac.closingdate = '' or ac.closingdate > '" + todt + "')  AND substring(ac.acno,3,2) in "
                    + " (select acctcode from accounttypemaster where acctnature in ('CA','TL','DL') and crdbflag in ('B','D')))";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo("");
                    row.setDetails("Cust Id:" + vtr.get(2).toString());
                    row.setEnterBy("");
                    row.setAuthBy("");
                    row.setHeader("ACCOUNTS WHICH ARE NOT MARKED AS NPA, BECAUSE THAT CUSTOMER HAVE ALREADY NPA ACCOUNTS");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("ACCOUNTS WHICH ARE NOT MARKED AS NPA, BECAUSE THAT CUSTOMER HAVE ALREADY NPA ACCOUNTS");
                finalList.add(row);
            }

            /*Point 24. (1)*/
            acQuery = "select ja.acno,ja.custname, ja.ODLimit, ja.bal, ja.acLimit,ifnull(jb.Adhoclimit,0) from "
                    + "(select aa.acno,aa.custname, aa.ODLimit, aa.bal, aa.acLimit from "
                    + "(select am.acno,am.custname, am.ODLimit, mm.bal, "
                    + "(select ifnull(acLimit,0) from loan_oldinterest where acno =  am.acno and enterdate>'" + todt + "' and txnid = "
                    + "(select min(TXNID) from loan_oldinterest where acno =  am.acno and enterdate>'" + todt + "')) as acLimit "
                    + "from accountmaster am, "
                    + " (select ca.acno as acno, sum(ca.bal) as bal from "
                    + " (select acno, cast(sum(dramt-cramt) as decimal(25,2)) as bal from ca_recon  where dt <='" + todt + "' group by acno "
                    + " union all "
                    + " select acno, cast(sum(dramt-cramt) as decimal(25,2)) as bal from loan_recon where dt <='" + todt + "' group by acno "
                    + " union all "
                    + " select acno, cast(sum(dramt-cramt) as decimal(25,2))  as bal from npa_recon  where dt <='" + todt + "' group by acno) ca "
                    + " group by ca.acno having sum(ca.bal)>0) mm "
                    + " where am.ACNo = mm.ACNo and (am.closingdate is null or am.closingdate = '' or am.closingdate > '" + todt + "') "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(am.acno,1,2) in ('" + brncode + "') ")
                    + " and substring(am.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','TL','DL') "
                    + "and crdbflag in ('B','D'))) aa "
                    + " where aa.bal> ifnull(aa.acLimit,aa.ODLimit)) ja "
                    + "left join"
                    + "(select Acno,Adhoclimit,AdhocExpiry from loan_appparameter where AdhocExpiry >='" + todt + "' group by acno) jb "
                    + "on ja.acno = jb.acno ";

//                    + "select am.acno,am.custname, am.ODLimit, mm.bal from accountmaster am, "
//                    + " (select ca.acno as acno, sum(ca.bal) as bal from "
//                    + " (select acno, cast(sum(dramt-cramt) as decimal(25,2)) as bal from ca_recon  where dt <='" + todt + "' group by acno"
//                    + " union all "
//                    + " select acno, cast(sum(dramt-cramt) as decimal(25,2)) as bal from loan_recon where dt <='" + todt + "' group by acno"
//                    + " union all "
//                    + " select acno, cast(sum(dramt-cramt) as decimal(25,2))  as bal from npa_recon  where dt ='" + todt + "' group by acno) ca "
//                    + " group by ca.acno having sum(ca.bal)>0) mm "
//                    + " where am.ACNo = mm.ACNo and (am.closingdate is null or am.closingdate = '' or am.closingdate > '" + todt + "') "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(am.acno,1,2) in ('" + brncode + "') ")
//                    + " and substring(am.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('CA','TL','DL') and crdbflag in ('B','D'))"
//                    + " and mm.bal>am.ODLimit";

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    String limitAmt = vtr.get(4) == null ? vtr.get(2).toString() : vtr.get(4).toString();
                    double cmpLimitAmt = Double.parseDouble(limitAmt) + Double.parseDouble(vtr.get(5).toString());
                    if (Double.parseDouble(vtr.get(3).toString()) > cmpLimitAmt) {
                        row.setAcNo(vtr.get(0).toString());
                        row.setCustName(vtr.get(1).toString());
                        row.setBalance(Double.parseDouble(vtr.get(3).toString()));
                        row.setInstNo("");
                        row.setDetails("Limit: " + (vtr.get(4) == null ? nft.format(Double.parseDouble(vtr.get(2).toString())) : nft.format(Double.parseDouble(vtr.get(4).toString()))));
                        row.setEnterBy("");
                        row.setAuthBy("");
                        row.setHeader("ACCOUNTS HAVE BALANCE MORE THAN THEIR LIMIT");
                        finalList.add(row);
                    }
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("ACCOUNTS HAVE BALANCE MORE THAN THEIR LIMIT");
                finalList.add(row);
            }

            /*Point 24. (2)*/
//            acQuery = "select distinct a.acno, a.custname , a.AccStatus, "
//                    + " ifnull(concat(a.EnteredBy,' [', (select si.UserName from securityinfo si where si.UserId = a.EnteredBy),']'),'') as enterBy,  "
//                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy, "
//                    + " b.dno, b.ddoc, b.ddocdtl "
//                    + " from accountmaster a left join (select d.acno as dno,d.DocuNo as ddoc, d.DocuDetails as ddocdtl, d.ReceivedDate as drecdt "
//                    + " from documentsreceived d, (select distinct acno as acc,max(txnid) as tid from documentsreceived group by acno) b "
//                    + " where b.acc = d.acno and b.tid = d.txnid) b on a.ACNo = b.dno "
//                    + " where a.acno in ("
//                    + " select acno from accountmaster a where (a.IntroAccno is null or a.IntroAccno = '') and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + todt + "')"
//                    + " )  and (b.dno is null or b.dno = '' ) "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ")
//                    + " union all "
//                    + " select distinct a.acno, a.custname , a.AccStatus, "
//                    + " ifnull(concat(a.EnteredBy,' [', (select si.UserName from securityinfo si where si.UserId = a.EnteredBy),']'),'') as enterBy,  "
//                    + " ifnull(concat(a.AUTHBY ,' [', (select si.UserName from securityinfo si where si.UserId = a.AUTHBY) ,']'),'') as authBy,  "
//                    + " b.dno, b.ddoc, b.ddocdtl "
//                    + " from td_accountmaster a left join (select d.acno as dno,d.DocuNo as ddoc, d.DocuDetails as ddocdtl, d.ReceivedDate as drecdt "
//                    + " from documentsreceived d, (select distinct acno as acc,max(txnid) as tid from documentsreceived group by acno) b "
//                    + " where b.acc = d.acno and b.tid = d.txnid) b on a.ACNo = b.dno "
//                    + " where a.acno in ( "
//                    + " select acno from td_accountmaster a where (a.IntroAccno is null or a.IntroAccno = '') and (a.closingdate is null or a.closingdate = '' or a.closingdate > '" + todt + "') "
//                    + " )  and (b.dno is null or b.dno = '' ) "
//                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  substring(a.acno,1,2) in ('" + brncode + "') ");
//
////            System.out.println("acQuery:"+acQuery);
//            sancion = em.createNativeQuery(acQuery).getResultList();
//            if (!sancion.isEmpty()) {
//                for (int i = 0; i < sancion.size(); i++) {
//                    Vector vtr = (Vector) sancion.get(i);
//                    ExceptionReportPojo row = new ExceptionReportPojo();
//                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
//                    row.setAcNo(vtr.get(0).toString());
//                    row.setCustName(vtr.get(1).toString());
//                    row.setInstNo("");
//                    row.setDetails("");
//                    row.setEnterBy(vtr.get(3).toString());
//                    row.setAuthBy(vtr.get(4).toString());
//                    row.setHeader("ACCOUNTS DONOT HAVE INTRODUCER");
//                    finalList.add(row);
//                }
//            } else {
//                ExceptionReportPojo row = new ExceptionReportPojo();
//                row.setHeader("ACCOUNTS DONOT HAVE INTRODUCER");
//                finalList.add(row);
//            }
            /*STR NOT REPORTED*/
            acQuery = "select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, ca_recon c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + "select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, loan_recon c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + " select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, recon c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + " select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, ddstransaction c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + " select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, rdrecon c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + " select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, accountmaster b, of_recon c where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ")
                    + " union all "
                    + " select a.acno, b.custname,  a.recno, a.alert_code, a.enter_by, c.authby, c.DrAmt, c.CrAmt  from cbs_str_detail a, td_accountmaster b, td_recon c  where a.acno = b.acno and a.flag = 'EXP' and a.dt = '" + todt + "'  and b.acno = c.acno and a.dt = c.dt and a.recno = c.recno "
                    + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : " and  a.orgnbrncode in ('" + brncode + "') ");

//            System.out.println("acQuery:"+acQuery);
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo("");
                    row.setDetails("Alert Code:" + vtr.get(3).toString() + "==>Trn No:" + vtr.get(2).toString());
                    row.setEnterBy(vtr.get(4).toString());
                    row.setAuthBy(vtr.get(5).toString());
                    row.setDebitAmt(Double.parseDouble(vtr.get(6).toString()));
                    row.setOdLimit(Double.parseDouble(vtr.get(7).toString()));
                    row.setHeader("STR REPORTED BY SYSTEM, BUT USER DID NOT MARK THIS ACCOUNT IN STR");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("STR REPORTED BY SYSTEM, BUT USER DID NOT MARK THIS ACCOUNT IN STR");
                finalList.add(row);
            }
            /*Minor Customer to Major Customer*/
            acQuery = "select b.CustomerId,b.custfullname,concat(ifnull(b.fathername,''),' ',ifnull(b.FatherMiddleName,''),' ',ifnull(b.FatherLastName,'')) as FatherName,"
                    + "concat(b.perAddressLine1,' ',ifnull(b.perAddressLine2,'')),date_format(MajorityDate,'%d/%m/%Y') MajorDate,primarybrcode,a.LastChangeUserID  "
                    + "from cbs_cust_minorinfo a,cbs_customer_master_detail b "
                    + "where date_format(MajorityDate,'%Y%m%d')='" + todt + "' and guardiancode='' "
                    + "and a.CustomerId = b.CustomerId " + ((brncode.equalsIgnoreCase("90") || brncode.equalsIgnoreCase("0A")) ? "" : "and  primarybrcode in ('" + brncode + "')") + " order by MajorityDate";
            sancion = em.createNativeQuery(acQuery).getResultList();
            if (!sancion.isEmpty()) {
                for (int i = 0; i < sancion.size(); i++) {
                    Vector vtr = (Vector) sancion.get(i);
                    ExceptionReportPojo row = new ExceptionReportPojo();
                    //@acNo,@custName,@odLimit,@debitAmt,@instNo,@details,@enterBy,@authBy
                    row.setAcNo(vtr.get(0).toString());
                    row.setCustName(vtr.get(1).toString());
                    row.setInstNo("");
                    row.setDetails("Father's Name:" + vtr.get(2).toString() + "==>Major Date:" + vtr.get(4).toString());
                    row.setEnterBy(vtr.get(6).toString());
                    row.setAuthBy("");
                    row.setDebitAmt(0);
                    row.setOdLimit(0);
                    row.setHeader("MINOR CUSTOMER TO MAJOR CUSTOMER");
                    finalList.add(row);
                }
            } else {
                ExceptionReportPojo row = new ExceptionReportPojo();
                row.setHeader("MINOR CUSTOMER TO MAJOR CUSTOMER");
                finalList.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return finalList;
    }

    public List<StandingErrorPojo> stanIntReport(String frmdt, String todt, String brncode) throws ApplicationException {
        List<StandingErrorPojo> resultArray = new ArrayList<StandingErrorPojo>();
        List result = new ArrayList();
        try {

            if (brncode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select fromacno,toacno,amount,date_format(processdate,'%Y%m%d'),errormsg from standins_transpending a ,accountmaster b where  a.fromacno=b.acno and a.processdate between '" + frmdt + "' and '" + todt + "'").getResultList();
            } else {
                result = em.createNativeQuery("select fromacno,toacno,amount,date_format(processdate,'%Y%m%d'),errormsg from standins_transpending a ,accountmaster b where  a.fromacno=b.acno and  b.curbrcode='" + brncode + "' and a.processdate between '" + frmdt + "' and '" + todt + "'").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr1 = (Vector) result.get(i);
                    Vector o1 = (Vector) em.createNativeQuery("select custname from accountmaster where acno='" + vtr1.get(0).toString() + "' ").getResultList();
                    Vector o2 = (Vector) em.createNativeQuery("select custname from accountmaster where acno='" + vtr1.get(1).toString() + "'").getResultList();
                    StandingErrorPojo row = new StandingErrorPojo();
                    row.setAcCr(vtr1.get(1).toString());
                    row.setAcCrCustname(o2.get(0).toString());
                    row.setAcDr(vtr1.get(0).toString());
                    row.setAcDrCustname(o1.get(0).toString());
                    row.setAmount(Float.parseFloat(vtr1.get(2).toString()));
                    row.setErrorDate(ymdFormat.parse(vtr1.get(3).toString()));
                    row.setTypeOfError(vtr1.get(4).toString());
                    resultArray.add(row);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return resultArray;
    }

    public List getAcctTypeList() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctCode) from accounttypemaster ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAcctsbcaTypeList() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAcctTypeListCashTrf() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in ('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.TERM_LOAN + "') ").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getLongBook(String accType, int status, String date, String brnCode, String fromTime, String tottime, int timeAllow, String orderby) throws ApplicationException {
        List tempList = null;
        Vector tempVector = null;
        Date dt = null;
        int trantype, ty;
        List<LongBookPojo> resultList = new ArrayList<LongBookPojo>();
        String accNatureString = null, condition = null, tableName = null, accountmasterTable = null, acNo, custName, auth, optStatus, IY, orgBrnid;
        double crAmt, drAmt;
        String orderBy = "";
        if (orderby.equalsIgnoreCase("recno")) {
            orderBy = " order by r.recno ";
        } else if (orderby.equalsIgnoreCase("a.acno")) {
            orderBy = " order by r.acno";
        }
        try {
            tempList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode = '" + accType + "'").getResultList();
            tempVector = (Vector) tempList.get(0);
            accNatureString = tempVector.get(0).toString();
            if (accNatureString.equalsIgnoreCase(CbsConstant.TD_AC) || accNatureString.equalsIgnoreCase(CbsConstant.MS_AC) || accNatureString.equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                accountmasterTable = "td_accountmaster";

            } else {
                accountmasterTable = "accountmaster";
            }
            if (timeAllow == 1) {
                condition = " and r.trantime between '" + fromTime + "' and '" + tottime + "' ";
            } else if (timeAllow == 0) {
                condition = "";
            }
            if (accNatureString.equalsIgnoreCase(CbsConstant.SAVING_AC) || accNatureString.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                if (status == 1) {
                    condition = condition + " and dt ='" + date + "' and accstatus <> 2 ";
                } else {
                    condition = condition + " and dt ='" + date + "' and accstatus = 2 ";
                }
            } else {
                condition = condition + " and dt ='" + date + "'  ";
            }
            tableName = tableName(accNatureString);

            if (!accNatureString.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) && !accNatureString.equalsIgnoreCase(CbsConstant.TD_AC) && !accNatureString.equalsIgnoreCase(CbsConstant.MS_AC) && !accNatureString.equalsIgnoreCase(CbsConstant.FIXED_AC)) {

                tempList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,recno,optstatus,iy,org_brnid  from " + tableName + " r," + accountmasterTable + " a "
                        + "where r.acno=a.acno and r.org_brnid = '" + brnCode + "' and substring(r.acno,3,2)='" + accType + "'" + condition + " and auth='y'  " + orderBy + " ").getResultList();

            } else if (accNatureString.equalsIgnoreCase(CbsConstant.TD_AC) || accNatureString.equalsIgnoreCase(CbsConstant.MS_AC) || accNatureString.equalsIgnoreCase(CbsConstant.FIXED_AC)) {

                tempList = em.createNativeQuery("select r.acno,dt,custname,cramt,dramt,trantype,auth,ty,recno,opermode,iy,org_brnid  from " + tableName + " r," + accountmasterTable + " a "
                        + "where r.acno=a.acno and r.org_brnid = '" + brnCode + "' and substring(r.acno,3,2)='" + accType + "'" + condition + " and auth='y'  and trantype <> 27 and closeflag is null " + orderBy + "").getResultList();
            } else {
                if (timeAllow == 1) {
                    tempList = em.createNativeQuery("select d.acno,d.dt,a.custname,cramt,dramt,trantype,auth,ty,recno,optstatus,iy,org_brnid from ddstransaction d, "
                            + "accountmaster a where a.acno=d.acno and d.trantime between '" + fromTime + "' and '" + tottime + "' and d.dt='" + date
                            + "' and auth='y' and a.curbrcode = '" + brnCode + "' " + orderBy + " ").getResultList();
                } else if (timeAllow == 0) {
                    tempList = em.createNativeQuery("select d.acno,d.dt,a.custname,cramt,dramt,trantype,auth,ty,recno,optstatus,iy,org_brnid  from ddstransaction d, "
                            + "accountmaster a where a.acno=d.acno and d.dt='" + date + "' and auth='y' and a.curbrcode = '" + brnCode + "' " + orderBy + " ").getResultList();
                }
            }
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNo = tempVector.get(0).toString();
                    dt = ymdFormat.parse(tempVector.get(1).toString());
                    custName = tempVector.get(2).toString();
                    crAmt = Double.parseDouble(tempVector.get(3).toString());
                    drAmt = Double.parseDouble(tempVector.get(4).toString());
                    trantype = Integer.parseInt(tempVector.get(5).toString());
                    ty = Integer.parseInt(tempVector.get(7).toString());
                    double recno = Double.parseDouble(tempVector.get(8).toString());
                    LongBookPojo pojo = new LongBookPojo();
                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    pojo.setRecno(recno);
                    if (trantype == 0) {
                        if (ty == 0) {
                            pojo.setCrCash(crAmt);
                        } else {
                            pojo.setDrCash(drAmt);
                        }
                    } else if (trantype == 1) {
                        if (ty == 0) {
                            pojo.setCrClg(crAmt);
                        } else {
                            pojo.setDrClg(drAmt);
                        }
                    } else {
                        if (ty == 0) {
                            pojo.setCrTrans(crAmt);
                        } else {
                            pojo.setDrTrans(drAmt);
                        }
                    }
                    resultList.add(pojo);
                }
            }
            if (orderby.equalsIgnoreCase("recno")) {
                Collections.sort(resultList, new OrderByRecnoComparator());
            } else if (orderby.equalsIgnoreCase("a.acno")) {
                Collections.sort(resultList, new AcNoByComparator());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return resultList;
    }

    private class AcNoByComparator implements Comparator<LongBookPojo> {

        public int compare(LongBookPojo tdIntDetailObj1, LongBookPojo tdIntDetailObj2) {
            return tdIntDetailObj1.getAcNo().compareTo(tdIntDetailObj2.getAcNo());
        }
    }

    private class OrderByRecnoComparator implements Comparator<LongBookPojo> {

        public int compare(LongBookPojo tdIntDetailObj1, LongBookPojo tdIntDetailObj2) {
            return Double.compare(tdIntDetailObj1.getRecno(), tdIntDetailObj2.getRecno());
        }
    }

    public List<SundrySuspencePojo> getSundryData(String brncode, int reporttype, String todate, String fromdt) {
        List<SundrySuspencePojo> listPojo = new ArrayList<SundrySuspencePojo>();
        if (reporttype == 0) {
            try {
                List sundry = em.createNativeQuery("select bs.fyear,bs.seqno,bs.acno,bs.amount,bs.origindt,bs.status,bs.dt,bs.trantype from bill_sundry bs  where bs.fyear in(select f_year from yearend y where y.yearendflag='n' and y.brncode='" + brncode + "' and  now() between y.mindate and y.maxdate ) and bs.acno in(select acno from gltable where acname='sundry' and substring(acno,1,2)= '" + brncode + "')and bs.dt between '" + fromdt + "' and '" + todate + "'").getResultList();
                if (!sundry.isEmpty()) {
                    for (int i = 0; i < sundry.size(); i++) {
                        Vector vtr = (Vector) sundry.get(i);
                        List sundrydt = em.createNativeQuery("select acno,amount,dt,trantype from bill_sundry_dt where fyear='" + vtr.get(0).toString() + "' and seqno=" + vtr.get(1) + "").getResultList();
                        if (!sundrydt.isEmpty()) {
                            for (int j = 0; j < sundrydt.size(); j++) {
                                Vector vtr1 = (Vector) sundrydt.get(j);
                                SundrySuspencePojo row = new SundrySuspencePojo();
                                row.setFyear(vtr.get(0).toString());
                                row.setSeqno((Double) vtr.get(1));
                                row.setAcno(vtr.get(2).toString());
                                row.setAmount((Double) vtr.get(3));
                                row.setDt((Date) vtr.get(4));
                                row.setStatus(vtr.get(5).toString());
                                row.setType(vtr.get(6).toString().equalsIgnoreCase("0") ? "Cash" : "Transfer");
                                row.setAcnodt(vtr1.get(0).toString());
                                row.setAmountdt((Double) vtr1.get(1));
                                row.setDtdt((Date) vtr1.get(2));
                                row.setTypedt(vtr1.get(3).toString().equalsIgnoreCase("0") ? "Cash" : "Transfer");
                                listPojo.add(row);
                            }

                        } else {
                            SundrySuspencePojo row = new SundrySuspencePojo();
                            row.setFyear(vtr.get(0).toString());
                            row.setSeqno((Double) vtr.get(1));
                            row.setAcno(vtr.get(2).toString());
                            row.setAmount((Double) vtr.get(3));
                            row.setDt((Date) vtr.get(4));
                            row.setStatus(vtr.get(5).toString());
                            row.setType(vtr.get(6).toString().equalsIgnoreCase("0") ? "Cash" : "Transfer");
                            if (vtr.get(5).toString().equalsIgnoreCase("PAID")) {
                                row.setAcnodt("Cash");
                                row.setAmountdt((Double) vtr.get(3));
                                row.setDtdt((Date) vtr.get(6));
                                row.setTypedt("Cash");
                            }

                            listPojo.add(row);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                List sundry = em.createNativeQuery("select bs.fyear,bs.seqno,bs.acno,bs.amount,bs.dt,bs.status from bill_suspense bs  where bs.fyear in(select f_year from yearend y where y.yearendflag='n' and y.brncode='" + brncode + "' and  now() between y.mindate and y.maxdate ) and bs.acno in(select acno from gltable where acname='suspense payment' and substring(acno,1,2)= '" + brncode + "')and bs.dt between '" + fromdt + "' and '" + todate + "'").getResultList();

                if (!sundry.isEmpty()) {
                    for (int i = 0; i < sundry.size(); i++) {
                        Vector vtr = (Vector) sundry.get(i);
                        List sundrydt = em.createNativeQuery("select acno,amount,dt from bill_suspense_dt where fyear='" + vtr.get(0).toString() + "' and seqno=" + vtr.get(1) + "").getResultList();
                        if (!sundrydt.isEmpty()) {
                            for (int j = 0; j < sundrydt.size(); j++) {
                                Vector vtr1 = (Vector) sundrydt.get(j);
                                SundrySuspencePojo row = new SundrySuspencePojo();
                                row.setFyear(vtr.get(0).toString());
                                row.setSeqno((Double) vtr.get(1));
                                row.setAcno(vtr.get(2).toString());
                                row.setAmount((Double) vtr.get(3));
                                row.setDt((Date) vtr.get(4));
                                row.setStatus(vtr.get(5).toString());
                                row.setAcnodt(vtr1.get(0).toString());
                                row.setAmountdt((Double) vtr1.get(1));
                                row.setDtdt((Date) vtr1.get(2));
                                listPojo.add(row);
                            }

                        } else {
                            SundrySuspencePojo row = new SundrySuspencePojo();
                            row.setFyear(vtr.get(0).toString());
                            row.setSeqno((Double) vtr.get(1));
                            row.setAcno(vtr.get(2).toString());
                            row.setAmount((Double) vtr.get(3));
                            row.setDt((Date) vtr.get(4));
                            row.setStatus(vtr.get(5).toString());
                            row.setAcnodt(null);
                            row.setAmountdt(null);
                            row.setDtdt(null);
                            listPojo.add(row);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listPojo;
    }

    private String tableName(String acctNature) {
        if (acctNature.equalsIgnoreCase("SB")) {
            return "recon";
        } else if (acctNature.equalsIgnoreCase("CA")) {
            return "ca_recon";
        } else if (acctNature.equalsIgnoreCase("TL") || acctNature.equalsIgnoreCase("DL") || acctNature.equalsIgnoreCase("SS") || acctNature.equalsIgnoreCase("NP")) {
            return "loan_recon";
        } else if (acctNature.equalsIgnoreCase("FD") || acctNature.equalsIgnoreCase("MS") || acctNature.equalsIgnoreCase("TD")) {
            return "td_recon";
        } else if (acctNature.equalsIgnoreCase("PO")) {
            return "gl_recon";
        } else if (acctNature.equalsIgnoreCase("DS")) {
            return "ddstransaction";
        } else if (acctNature.equalsIgnoreCase("RD")) {
            return "rdrecon";
        }
        return null;
    }

    public List getAcctType() throws ApplicationException {
        try {
            List select = em.createNativeQuery("select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<UserReportTable> getUserInfo(String brncode, String status) throws ApplicationException {
        List<UserReportTable> userResultList = new ArrayList();
        try {
            List resultSet = new ArrayList();
            if (brncode.equalsIgnoreCase("0A")) {
                if (status.equalsIgnoreCase("ALL")) {
                    resultSet = em.createNativeQuery("select userid,username,status,login,tocashlimit,clgdebit,trandebit,LevelId,ref_desc from securityinfo a,cbs_ref_rec_type b where ref_rec_no = '403' and a.LevelId = b.ref_code").getResultList();
                } else {
                    resultSet = em.createNativeQuery("select userid,username,status,login,tocashlimit,clgdebit,trandebit,LevelId,ref_desc from securityinfo a,cbs_ref_rec_type b where status = '" + status + "' and ref_rec_no = '403' and a.LevelId = b.ref_code").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("ALL")) {
                    resultSet = em.createNativeQuery("select userid,username,status,login,tocashlimit,clgdebit,trandebit,LevelId,ref_desc from securityinfo a,cbs_ref_rec_type b where brncode='" + brncode + "' and ref_rec_no = '403' and a.LevelId = b.ref_code").getResultList();
                } else {
                    resultSet = em.createNativeQuery("select userid,username,status,login,tocashlimit,clgdebit,trandebit,LevelId,ref_desc from securityinfo a,cbs_ref_rec_type b where brncode='" + brncode + "' and status = '" + status + "' and ref_rec_no = '403' and a.LevelId = b.ref_code").getResultList();
                }
            }
            if (!resultSet.isEmpty()) {
                for (int i = 0; i < resultSet.size(); i++) {
                    Vector vec = (Vector) resultSet.get(i);
                    UserReportTable user = new UserReportTable();
                    user.setClgDebit(Double.parseDouble(vec.get(5).toString()));
                    user.setLogin(vec.get(3).toString());
                    user.setStatus(vec.get(2).toString());
                    user.setToCashLimit(Double.parseDouble(vec.get(4).toString()));
                    user.setTranDebit(Double.parseDouble(vec.get(6).toString()));
                    user.setUserId(vec.get(0).toString());
                    user.setUserName(vec.get(1).toString());
                    user.setRollName(vec.get(8).toString());
                    userResultList.add(user);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return userResultList;
    }

    public List getRefcodeDesc(String refcode) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where ref_rec_no='" + refcode + "'").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getCodeBook(String st) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select code,description from codebook where groupcode='" + st + "' ").getResultList();
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<CustomerEnquiryPojo> getCustomerEnquiry(String query, String query1, boolean balanceOption) throws ApplicationException {
        List<CustomerEnquiryPojo> resultList = new ArrayList<CustomerEnquiryPojo>();
        try {
            int size;
            Vector vtr, vtr1;
            List list1;
            double balance;
            String acnature = "", tablename, acno, category, ocupation;
            List list = em.createNativeQuery(query).getResultList();

            if (!list.isEmpty()) {
                list1 = getRefcodeDesc("21");
                HashMap<String, String> ocupationMap = new HashMap<String, String>();
                size = list1.size();
                for (int i = 0; i < size; i++) {
                    vtr1 = (Vector) list1.get(i);
                    ocupationMap.put(vtr1.get(0).toString(), vtr1.get(1).toString());
                }
                HashMap<String, String> categoryMap = new HashMap<String, String>();
                list1 = getRefcodeDesc("209");
                size = list1.size();
                for (int i = 0; i < size; i++) {
                    vtr1 = (Vector) list1.get(i);
                    categoryMap.put(vtr1.get(0).toString(), vtr1.get(1).toString());
                }
                size = list.size();
                for (int i = 0; i < size; i++) {
                    vtr = (Vector) list.get(i);
                    CustomerEnquiryPojo pojo = new CustomerEnquiryPojo();
                    if (vtr.get(1) == null) {
                        if (!balanceOption) {
                            category = vtr.get(9) == null ? "" : categoryMap.get(vtr.get(9).toString());
                            ocupation = vtr.get(8) == null ? "" : ocupationMap.get(vtr.get(8).toString());
                            pojo.setAcno(vtr.get(1) != null ? vtr.get(1).toString() : "");
                            pojo.setCustname(vtr.get(2) != null ? vtr.get(2).toString() : "");
                            pojo.setCustomerid(vtr.get(0) != null ? vtr.get(0).toString() : "");
                            pojo.setDateofbirth(vtr.get(6) != null ? (Date) vtr.get(6) : null);
                            pojo.setFathername(vtr.get(5) != null ? vtr.get(5).toString() : "");

                            pojo.setGender(vtr.get(3) != null ? vtr.get(3).toString() : "");
                            pojo.setMaritalstatus(vtr.get(4) != null ? vtr.get(4).toString() : "");
                            pojo.setAddress(vtr.get(7) != null ? vtr.get(7).toString() : "");
                            pojo.setCategory(category);
                            pojo.setOccupation(ocupation);
                            resultList.add(pojo);
                        }
                    } else {
                        acnature = ftsPosting.getAccountNature(vtr.get(1) != null ? vtr.get(1).toString() : "");
                        tablename = CbsUtil.getReconBalanTableName(acnature);
                        acno = vtr.get(1) != null ? vtr.get(1).toString() : "";
                        String query2 = "select balance from " + tablename + " where acno='" + acno + "' " + query1;
                        list1 = em.createNativeQuery(query2).getResultList();
                        if (!list1.isEmpty()) {
                            vtr1 = (Vector) list1.get(0);
                            balance = Double.parseDouble(vtr1.get(0).toString());
                            balance = balance < 0 ? balance * (-1) : balance;
                            pojo.setBalance(balance);
                            category = vtr.get(9) == null ? "" : categoryMap.get(vtr.get(9).toString());
                            ocupation = vtr.get(8) == null ? "" : ocupationMap.get(vtr.get(8).toString());
                            pojo.setAcno(vtr.get(1) != null ? vtr.get(1).toString() : "");

                            pojo.setCustname(vtr.get(2) != null ? vtr.get(2).toString() : "");
                            pojo.setCustomerid(vtr.get(0) != null ? vtr.get(0).toString() : "");
                            pojo.setDateofbirth(vtr.get(6) != null ? (Date) vtr.get(6) : null);
                            pojo.setFathername(vtr.get(5) != null ? vtr.get(5).toString() : "");

                            pojo.setGender(vtr.get(3) != null ? vtr.get(3).toString() : "");
                            pojo.setMaritalstatus(vtr.get(4) != null ? vtr.get(4).toString() : "");
                            pojo.setAddress(vtr.get(7) != null ? vtr.get(7).toString() : "");

                            pojo.setCategory(category);
                            pojo.setOccupation(ocupation);
                            resultList.add(pojo);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return resultList;
    }

    /**
     * **********************Methods for Active RD
     * Report***************************
     */
    public List getRdAccountCode() throws ApplicationException {
        try {
            List acCodeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature='" + CbsConstant.RECURRING_AC + "'").getResultList();
            return acCodeList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<ActiveRdReportPojo> getRdActiveReportDetails(String acCode, String date, String brncode) throws ApplicationException {
        List<ActiveRdReportPojo> rdActiveReportList = new ArrayList<ActiveRdReportPojo>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat ymd = new SimpleDateFormat("dd/MM/yyyy");
        try {
            List resultList = new ArrayList();
            if (brncode.equalsIgnoreCase("0A")) {
//                resultList = em.createNativeQuery("select acno,custname,ifnull(rdinstal,0),ifnull(intdeposit,0),openingdt,date_format(rdmatdate,'%d/%m/%Y') as rdmatdate,accstatus,closingdate,ifnull(timestampdiff(month,openingdt,rdmatdate),0) "
//                        + " from accountmaster where accttype='" + acCode + "'   and openingdt<='" + date + "'  and (ifnull(closingdate,'')='' or closingdate>'" + date + "' ) order by acno").getResultList();
                resultList = em.createNativeQuery("select a.acno,a.custname,ifnull(a.rdinstal,0),ifnull(a.intdeposit,0),a.openingdt,date_format(a.rdmatdate,'%d/%m/%Y') "
                        + "as rdmatdate,a.accstatus,a.closingdate,ifnull(timestampdiff(month,a.openingdt,a.rdmatdate),0),ifnull(sum(b.cramt-b.dramt),0) "
                        + "as balance,c.custid from accountmaster a,rdrecon b,customerid c "
                        + "where accttype='" + acCode + "' and a.acno=b.acno and a.acno = c.acno and a.openingdt<='" + date + "'  and (ifnull(a.closingdate,'')='' or "
                        + "a.closingdate>'" + date + "' ) and b.dt<= '" + date + "' group by  b.acno order by a.acno").getResultList();
            } else {
//                resultList = em.createNativeQuery("select acno,custname,ifnull(rdinstal,0),ifnull(intdeposit,0),openingdt,date_format(rdmatdate,'%d/%m/%Y') as rdmatdate,accstatus,closingdate,ifnull(timestampdiff(month,openingdt,rdmatdate),0) "
//                        + " from accountmaster where accttype='" + acCode + "'   and openingdt<='" + date + "'  and (ifnull(closingdate,'')='' or closingdate>'" + date + "' ) and curbrcode='" + brncode + "' order by acno").getResultList();
                resultList = em.createNativeQuery("select a.acno,a.custname,ifnull(a.rdinstal,0),ifnull(a.intdeposit,0),a.openingdt,date_format(a.rdmatdate,'%d/%m/%Y') "
                        + "as rdmatdate,a.accstatus,a.closingdate,ifnull(timestampdiff(month,a.openingdt,a.rdmatdate),0),ifnull(sum(b.cramt-b.dramt),0) "
                        + "as balance,c.custid from accountmaster a,rdrecon b,customerid c where accttype='" + acCode + "' and a.acno=b.acno and a.acno = c.acno "
                        + "and a.openingdt<='" + date + "'  and (ifnull(a.closingdate,'')='' or a.closingdate>'" + date + "' ) and b.dt<= '" + date + "' "
                        + "and a.curbrcode='" + brncode + "' group by  b.acno order by a.acno").getResultList();
            }
            if (!resultList.isEmpty()) {
                String acctStatus = "";
                String openingDt = "";
                String closingDt = "19000101";
                for (int i = 0, listSize = resultList.size(); i < listSize; i++) {
                    Vector vec = (Vector) resultList.get(i);
                    ActiveRdReportPojo pojo = new ActiveRdReportPojo();
                    if (vec.get(4) != null) {
                        openingDt = vec.get(4).toString();

                    }
                    if (vec.get(6) != null) {
                        acctStatus = vec.get(6).toString();
                    }

                    if (vec.get(7) != null) {
                        closingDt = vec.get(7).toString();
                        closingDt = closingDt.equalsIgnoreCase("") ? "19000101" : closingDt;
                    }

                    if ((!acctStatus.equalsIgnoreCase("9"))) {

                        List rdIntAmtList = em.createNativeQuery("select ifnull(sum(interest),0) from rd_interesthistory where acno = '" + vec.get(0).toString() + "' and dt<= '" + date + "'").getResultList();
                        Vector intAmtVector = (Vector) rdIntAmtList.get(0);
                        double rdIntAmt = Double.parseDouble(intAmtVector.get(0).toString());

                        if (vec.get(0) != null) {
                            pojo.setAcno(vec.get(0).toString());
                        }
                        if (vec.get(1) != null) {
                            pojo.setCustName(vec.get(1).toString());
                        }
                        if (vec.get(2) != null) {
                            pojo.setRdInstal(Double.parseDouble(vec.get(2).toString()));
                        }
                        if (vec.get(3) != null) {
                            pojo.setIntDeposit(Float.parseFloat(vec.get(3).toString()));
                        }
                        if (vec.get(5) != null) {
                            pojo.setRdMatDate(vec.get(5).toString());
                        }
                        //pojo.setPeriod(Integer.parseInt(vec.get(8).toString()));  //>= 0 ? period : 0
                        pojo.setPeriod(CbsUtil.monthDiff(sdf.parse(openingDt), ymd.parse(vec.get(5).toString())));  //>= 0 ? period : 0
                        pojo.setOpeningDt(sdf.parse(openingDt));
                        // pojo.setBalance(Double.parseDouble(vec.get(9).toString()));
                        pojo.setBalance(Double.parseDouble(amtFormatter.format(common.getBalanceOnDate(vec.get(0).toString(), date))));
                        pojo.setCustId(vec.get(10).toString());
                        pojo.setIntAmt(rdIntAmt);
                        rdActiveReportList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);

        }
        return rdActiveReportList;
    }

    public List<MprPojo> getMprDetails(String fromDt, String toDt, String brncode) throws ApplicationException {
        List<MprPojo> mprList = new ArrayList<MprPojo>();
        try {
            List resList = em.createNativeQuery("select * from cbs_mpr('" + fromDt + "','" + toDt + "','" + brncode + "')").getResultList();
            if (!resList.isEmpty()) {
                Vector vec = (Vector) resList.get(0);
                MprPojo pojo = new MprPojo();
                pojo.setDepositAmtBeg(Double.parseDouble(vec.get(0).toString()));
                pojo.setDepositAmountEnd(Double.parseDouble(vec.get(1).toString()));
                pojo.setOutstandingPrev(Double.parseDouble(vec.get(2).toString()));
                pojo.setOverdue(Double.parseDouble(vec.get(3).toString()));
                pojo.setDemand(Double.parseDouble(vec.get(4).toString()));
                pojo.setRecovery(Double.parseDouble(vec.get(5).toString()));
                pojo.setOutstanding(Double.parseDouble(vec.get(6).toString()));
                pojo.setOverdueInOutsatnding(Double.parseDouble(vec.get(7).toString()));
                mprList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return mprList;
    }

    public List getBranchCode(int orgncode) throws ApplicationException {
        try {
            List list;
            if (orgncode == 90) {
                list = em.createNativeQuery("select a.alphacode,a.brncode from branchmaster a,bnkadd b where a.alphacode = b.alphacode and brncode != 90").getResultList();
            } else {
                list = em.createNativeQuery("select a.alphacode,a.brncode from branchmaster a,bnkadd b where a.alphacode = b.alphacode and brncode = '" + orgncode + "'").getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<CustomerEnquiryPojo> getCustomerEnquiry(String query, String query1, double balanceV, int fromAge, int toAge, Date currentdate,
            double avgBalance) throws ApplicationException {

        List<CustomerEnquiryPojo> resultList = new ArrayList<CustomerEnquiryPojo>();
        try {
            int size;
            Vector vtr, vtr1;
            List list1;
            double balance;

            BigDecimal avgBal = new BigDecimal("0.0");

            String acnature = "", tablename, acno, category, ocupation;
            List list = em.createNativeQuery(query).getResultList();

            if (!list.isEmpty()) {
                list1 = getRefcodeDesc("21");
                HashMap<String, String> ocupationMap = new HashMap<String, String>();
                size = list1.size();
                for (int i = 0; i < size; i++) {
                    vtr1 = (Vector) list1.get(i);
                    ocupationMap.put(vtr1.get(0).toString(), vtr1.get(1).toString());
                }
                HashMap<String, String> categoryMap = new HashMap<String, String>();
                list1 = getRefcodeDesc("209");
                size = list1.size();
                for (int i = 0; i < size; i++) {
                    vtr1 = (Vector) list1.get(i);
                    categoryMap.put(vtr1.get(0).toString(), vtr1.get(1).toString());
                }
                size = list.size();
                for (int i = 0; i < size; i++) {
                    vtr = (Vector) list.get(i);
                    CustomerEnquiryPojo pojo = new CustomerEnquiryPojo();
                    if (vtr.get(1) == null) {
                        if (balanceV != 0d) {
                            category = vtr.get(9) == null ? "" : categoryMap.get(vtr.get(9).toString());
                            ocupation = vtr.get(8) == null ? "" : ocupationMap.get(vtr.get(8).toString());
                            pojo.setAcno(vtr.get(1) != null ? vtr.get(1).toString() : "");
                            pojo.setCustname(vtr.get(2) != null ? vtr.get(2).toString() : "");
                            pojo.setCustomerid(vtr.get(0) != null ? vtr.get(0).toString() : "");
                            pojo.setDateofbirth(vtr.get(6) != null ? (Date) vtr.get(6) : null);

                            pojo.setFathername(vtr.get(5) != null ? vtr.get(5).toString() : "");

                            pojo.setGender(vtr.get(3) != null ? vtr.get(3).toString() : "");
                            pojo.setMaritalstatus(vtr.get(4) != null ? vtr.get(4).toString() : "");
                            pojo.setAddress(vtr.get(7) != null ? vtr.get(7).toString() : "");
                            pojo.setCategory(category);
                            pojo.setOccupation(ocupation);
                            if (fromAge != 0 && toAge != 0) {
                                int age = CbsUtil.yearDiff((Date) vtr.get(6), currentdate);
                                if (age >= fromAge && age <= toAge) {
                                    resultList.add(pojo);
                                }
                            } else {
                                resultList.add(pojo);
                            }
                        }
                    } else {
                        acnature = ftsPosting.getAccountNature(vtr.get(1) != null ? vtr.get(1).toString() : "");
                        tablename = CbsUtil.getReconBalanTableName(acnature);
                        acno = vtr.get(1) != null ? vtr.get(1).toString() : "";
                        if (avgBalance != 0) {
                            avgBal = getAverageBalance(vtr.get(1).toString(), ymd.format(currentdate));
                            if (avgBal.compareTo(BigDecimal.valueOf(avgBalance)) == 1 || avgBal.compareTo(BigDecimal.valueOf(avgBalance)) == 0) {
                                pojo.setAvgbalance(avgBalance);
                            }
                        }

                        String query2 = "select balance from " + tablename + " where acno='" + acno + "' " + query1;
                        list1 = em.createNativeQuery(query2).getResultList();
                        if (!list1.isEmpty()) {
                            vtr1 = (Vector) list1.get(0);
                            balance = Double.parseDouble(vtr1.get(0).toString());
                            balance = balance < 0 ? balance * (-1) : balance;
                            pojo.setBalance(balance);
                            category = vtr.get(9) == null ? "" : categoryMap.get(vtr.get(9).toString());
                            ocupation = vtr.get(8) == null ? "" : ocupationMap.get(vtr.get(8).toString());
                            pojo.setAcno(vtr.get(1) != null ? vtr.get(1).toString() : "");

                            pojo.setCustname(vtr.get(2) != null ? vtr.get(2).toString() : "");
                            pojo.setCustomerid(vtr.get(0) != null ? vtr.get(0).toString() : "");
                            pojo.setDateofbirth(vtr.get(6) != null ? (Date) vtr.get(6) : null);
                            pojo.setFathername(vtr.get(5) != null ? vtr.get(5).toString() : "");

                            pojo.setGender(vtr.get(3) != null ? vtr.get(3).toString() : "");
                            pojo.setMaritalstatus(vtr.get(4) != null ? vtr.get(4).toString() : "");
                            pojo.setAddress(vtr.get(7) != null ? vtr.get(7).toString() : "");

                            pojo.setCategory(category);
                            pojo.setOccupation(ocupation);
                            if (fromAge != 0 && toAge != 0) {
                                int age = CbsUtil.yearDiff((Date) vtr.get(6), currentdate);
                                if (age >= fromAge && age <= toAge) {
                                    resultList.add(pojo);
                                }
                            } else {
                                resultList.add(pojo);
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return resultList;
    }

    public BigDecimal getAverageBalance(String acno, String asOnDt) throws ApplicationException {
        String acnature = ftsPosting.getAccountNature(acno);
        String tablename = CbsUtil.getReconTableName(acnature);
        BigDecimal balance = new BigDecimal("0.0");
        //Date dt = new Date();
        String todate = asOnDt;
        String frDate = todate.substring(0, 6) + "01";
        String frDt = frDate;
        try {
            int nodays = (int) CbsUtil.dayDiff(ymd.parse(frDate), ymd.parse(todate));
            for (int j = 0; j <= nodays; j++) {
                frDate = frDt;
                frDate = CbsUtil.dateAdd(frDate, j);
                List listAvg = em.createNativeQuery("select ifnull(sum(cramt),0) - ifnull(sum(dramt),0) from " + tablename + " "
                        + "where dt <= '" + frDate + "' and acno = '" + acno + "'").getResultList();
                if (!listAvg.isEmpty()) {
                    Vector vtr1 = (Vector) listAvg.get(0);
                    BigDecimal amt = new BigDecimal(vtr1.get(0).toString());
                    balance = amt.add(balance);
                }
            }
            return new BigDecimal(balance.doubleValue() / (nodays == 0 ? nodays + 1 : nodays + 1));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public BigDecimal getAverageBalanceOnQuery(String acno, String asOnDt) throws ApplicationException {
        String acnature = ftsPosting.getAccountNature(acno);
        String tablename = CbsUtil.getReconTableName(acnature);
        BigDecimal balance = new BigDecimal("0.0");
        String frDate = asOnDt.substring(0, 6) + "01";
        try {
            List listAvg = em.createNativeQuery("select avg(a.bal) as bal from "
                    + "(select v.selected_date, (select cast(ifnull(sum(cramt-dramt),0) as decimal(15,2))  from " + tablename + " where acno = '" + acno + "' "
                    + "and dt<=v.selected_date ) as bal from (select date as selected_date from cbs_bankdays where date between "
                    + "'" + frDate + "' and '" + asOnDt + "') v where v.selected_date between '" + frDate + "' and '" + asOnDt + "' )a").getResultList();
            Vector avgVector = (Vector) listAvg.get(0);
            balance = new BigDecimal(avgVector.get(0).toString());
            return balance;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getReportWriter(String frmDt, String toDt, String acctype, int orderBy, boolean flag,
            String branch, String status, String custStatus, boolean avgBalFlag, String annualIncomeFrom,
            String annualIncomeTo, String areaSector, String occupation, String riskCategorization,
            String fromAge, String toAge, String fromAcno, String toAcno, String reportOption) throws ApplicationException {
        List<ReportWriterPojo> returnList = new ArrayList<ReportWriterPojo>();
        try {
            List tempList = null, tempList1 = null;
            Vector tempVector, tempVector1;
            String acno, custName, openDt, jtName, custid, dateofBrth, perPhoneNo, perAdd, mailAdd,
                    operationMode, gender = "", fatherName, nomineName;
            String branchQuery, acTypeQuery, statusCondition = "", annualIncomeQuery = "", areaSectorQuery = "",
                    occupationQuery = "", riskCategorizationQuery = "", acRangeQuery = "", spFlag = "", acStatusBranch = "", acStatusType = "", atmCondition;
            double openBal = 0d, closeBal = 0d;

            tempList = getCodeBook("4");
            HashMap<String, String> operMap = new HashMap<String, String>();
            for (int i = 0; i < tempList.size(); i++) {
                tempVector = (Vector) tempList.get(i);
                operMap.put(tempVector.get(0).toString(), tempVector.get(1).toString());
            }
            if (branch.equalsIgnoreCase("0A")) {
                branchQuery = "";
                acStatusBranch = "";
            } else {
                branchQuery = " and am.curbrcode = '" + branch + "'";
                acStatusBranch = "and substring(a.acno,1,2)='" + branch + "'";
            }
            /*A/c Level Condition*/
            if (acctype == null || acctype.equalsIgnoreCase("All")) {
                acTypeQuery = "";
                acRangeQuery = "";
                acStatusType = "";
            } else {
                acTypeQuery = " and am.accttype = '" + acctype + "' ";
                acStatusType = "and substring(a.acno,3,2)='" + acctype + "'";

                if (!(fromAcno == null || toAcno == null || fromAcno.equals("") || toAcno.equals(""))) {
                    acRangeQuery = " and substring(am.acno,5,6) >= '" + CbsUtil.lPadding(6, Integer.parseInt(fromAcno)) + "' and "
                            + " substring(am.acno,5,6) <= '" + CbsUtil.lPadding(6, Integer.parseInt(toAcno)) + "'";
                }
            }
//            if (status.equalsIgnoreCase("O")) {
//                statusCondition = " and am.accstatus=1";
//            } else if (status.equalsIgnoreCase("I")) {
//                statusCondition = " and am.accstatus=2";
//            } else {
//                //statusCondition = " and am.accstatus<>9";
//                statusCondition = " and (am.closingdate is null or am.closingdate ='' or am.closingdate >='" + toDt + "')";
//            }

            if (status.equalsIgnoreCase("ALL")) {
                statusCondition = "";
                spFlag = "";
            } else {
                statusCondition = " and (am.accstatus= " + status + " or ac.SPFLAG= " + status + ")";
                spFlag = "and a.SPFLAG = " + status + "";
            }

            /*Customer Level Condition*/
            if (!(annualIncomeFrom == null || annualIncomeFrom.equals(""))) {
                annualIncomeQuery = " and cb.networth between " + Double.parseDouble(annualIncomeFrom) + " and " + Double.parseDouble(annualIncomeTo) + "";
            }
            if (!(areaSector == null || areaSector.equals(""))) {
                areaSectorQuery = " and cb.pervillage in('" + areaSector + "')";
            }
            if (!(occupation == null || occupation.equalsIgnoreCase("--Select--"))) {
                if (!occupation.equalsIgnoreCase("All")) {
                    occupationQuery = " and cm.occupationcode in('" + occupation + "')";
                }
            }
            if (!(riskCategorization == null || riskCategorization.equalsIgnoreCase("--Select--"))) {
                if (!riskCategorization.equalsIgnoreCase("All")) {
                    riskCategorizationQuery = " and cb.operationalriskrating in('" + riskCategorization + "')";
                }
            }
            if (reportOption.equalsIgnoreCase("ALL")) {
                atmCondition = "";
            } else {
                atmCondition = "and atm.card_no  is not null";
            }
            List occuList = common.getRefRecList("21");
            List riskCatList = common.getRefRecList("024");
            List actCatList = common.getRefRecList("325");
            if (acctype.equalsIgnoreCase("All")) {
                tempList = em.createNativeQuery("select cb.customerid,trim(concat(trim(concat(trim(ifnull(cb.custname,'')),' ',trim(ifnull(cb.middle_name,'')))),' ',trim(ifnull(cb.last_name,'')))) as custname,"
                        + "date_format(cb.dateofbirth,'%d/%m/%Y'),cb.peraddressline1,cb.peraddressline2,cb.mailaddressline1,cb.mailaddressline2,cm.occupationcode,cb.mobilenumber,"
                        + "cb.gender,am.jtname1,am.openingdt,am.opermode,am.acno,cb.fathername,am.nomination,cb.operationalriskrating,cb.networth,cb.pervillage,cb.pan_girnumber, "
                        + " ifnull( aa.IdentityNo,'') as aadhar_no,am.acctCategory,atm.card_no  "
                        + "from cbs_customer_master_detail cb "
                        + "left outer join customerid ci on cast(cb.customerid as unsigned) = ci.custid "
                        + "left outer join cbs_cust_misinfo cm on cb.customerid = cm.customerid "
                        + "left outer join accountmaster am on ci.acno = am.acno "
                        + "left outer join (select a.acno,a.SPFLAG from accountstatus a,(select acno,max(SPNO) spNo from accountstatus where effdt <= '" + toDt + "' group by acno)b "
                        + "where a.acno=b.acno and a.SPNO = b.spNo and a.effdt <= '" + toDt + "' " + acStatusBranch + " " + spFlag + ")ac on ci.acno = ac.acno "
                        + "left outer join "
                        + "(select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,"
                        + "cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid "
                        + "union "
                        + "select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E') aa on  aa.CustomerId = cb.customerid "
                        + "left outer join "
                        + "(select acno,card_no,registration_dt,del_flag from atm_card_master where del_flag <>'I'and date_format(registration_dt,'%Y%m%d') <='" + toDt + "')atm "
                        + "on am.acno = atm.acno "
                        + "where am.openingdt between '" + frmDt + "' and '" + toDt + "' and (closingdate is null or closingdate ='' or closingdate >'" + toDt + "')" + statusCondition
                        + branchQuery + annualIncomeQuery + areaSectorQuery + occupationQuery + riskCategorizationQuery + acRangeQuery + atmCondition).getResultList();

                System.out.println("Size of tempList: " + tempList.size());

                tempList1 = em.createNativeQuery("select cb.customerid,trim(concat(trim(concat(trim(ifnull(cb.custname,'')),' ',trim(ifnull(cb.middle_name,'')))),' ',trim(ifnull(cb.last_name,'')))) as custname,"
                        + "date_format(cb.dateofbirth,'%d/%m/%Y'),cb.peraddressline1,cb.peraddressline2,cb.mailaddressline1,cb.mailaddressline2,cm.occupationcode,cb.mobilenumber,"
                        + "cb.gender,am.jtname1,am.openingdt,am.opermode,am.acno,cb.fathername,am.nomination,cb.operationalriskrating,cb.networth,cb.pervillage,cb.pan_girnumber, "
                        + "ifnull( aa.IdentityNo,'') as aadhar_no,am.acctCategory,atm.card_no "
                        + "from cbs_customer_master_detail cb "
                        + "left outer join customerid ci on cast(cb.customerid as unsigned) = ci.custid "
                        + "left outer join cbs_cust_misinfo cm on cb.customerid = cm.customerid "
                        + "left outer join td_accountmaster am on ci.acno = am.acno "
                        + "left outer join (select a.acno,a.SPFLAG from accountstatus a,(select acno,max(SPNO) spNo from accountstatus where effdt <= '" + toDt + "' group by acno)b "
                        + "where a.acno=b.acno and a.SPNO = b.spNo and a.effdt <= '" + toDt + "' " + acStatusBranch + " " + spFlag + ")ac on ci.acno = ac.acno "
                        + "left outer join "
                        + "(select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,"
                        + "cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid "
                        + "union "
                        + "select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E') aa on  aa.CustomerId = cb.customerid "
                        + "left outer join "
                        + "(select acno,card_no,registration_dt,del_flag from atm_card_master where del_flag <>'I'and date_format(registration_dt,'%Y%m%d') <='" + toDt + "')atm "
                        + "on am.acno = atm.acno "
                        + "where am.openingdt between '" + frmDt + "' and '" + toDt + "' and (closingdate is null or closingdate ='' or closingdate >'" + toDt + "')" + statusCondition
                        + branchQuery + annualIncomeQuery + areaSectorQuery + occupationQuery + riskCategorizationQuery + acRangeQuery + atmCondition).getResultList();

                System.out.println("Size of tempList1: " + tempList1.size());
                tempList1.addAll(tempList);
                System.out.println("After Addition Size of tempList1: " + tempList1.size());
            } else {
                String acNat = ftsPosting.getAcNatureByCode(acctype);
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    tempList1 = em.createNativeQuery("select cb.customerid,trim(concat(trim(concat(trim(ifnull(cb.custname,'')),' ',trim(ifnull(cb.middle_name,'')))),' ',trim(ifnull(cb.last_name,'')))) as custname,"
                            + "date_format(cb.dateofbirth,'%d/%m/%Y'),cb.peraddressline1,cb.peraddressline2,cb.mailaddressline1,cb.mailaddressline2,cm.occupationcode,cb.mobilenumber,"
                            + "cb.gender,am.jtname1,am.openingdt,am.opermode,am.acno,cb.fathername,am.nomination,cb.operationalriskrating,cb.networth,cb.pervillage,cb.pan_girnumber, "
                            + "ifnull( aa.IdentityNo,'') as aadhar_no,am.acctCategory,atm.card_no  "
                            + "from cbs_customer_master_detail cb "
                            + "left outer join customerid ci on cast(cb.customerid as unsigned) = ci.custid "
                            + "left outer join cbs_cust_misinfo cm on cb.customerid = cm.customerid "
                            + "left outer join td_accountmaster am on ci.acno = am.acno "
                            + "left outer join (select a.acno,a.SPFLAG from accountstatus a,(select acno,max(SPNO) spNo from accountstatus where effdt <= '" + toDt + "' group by acno)b "
                            + "where a.acno=b.acno and a.SPNO = b.spNo and a.effdt <= '" + toDt + "' " + acStatusBranch + " " + acStatusType + " " + spFlag + ")ac on ci.acno = ac.acno "
                            + "left outer join "
                            + "(select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,"
                            + "cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid "
                            + "union "
                            + "select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E') aa on  aa.CustomerId = cb.customerid "
                            + "left outer join "
                            + "(select acno,card_no,registration_dt,del_flag from atm_card_master where del_flag <>'I'and date_format(registration_dt,'%Y%m%d') <='" + toDt + "')atm "
                            + "on am.acno = atm.acno "
                            + "where am.openingdt between '" + frmDt + "' and '" + toDt + "' and (closingdate is null or closingdate ='' or closingdate >'" + toDt + "')" + statusCondition
                            + branchQuery + acTypeQuery + annualIncomeQuery + areaSectorQuery + occupationQuery + riskCategorizationQuery + acRangeQuery + atmCondition).getResultList();

                } else {
                    tempList1 = em.createNativeQuery("select cb.customerid,trim(concat(trim(concat(trim(ifnull(cb.custname,'')),' ',trim(ifnull(cb.middle_name,'')))),' ',trim(ifnull(cb.last_name,'')))) as custname,"
                            + "date_format(cb.dateofbirth,'%d/%m/%Y'),cb.peraddressline1,cb.peraddressline2,cb.mailaddressline1,cb.mailaddressline2,cm.occupationcode,cb.mobilenumber,"
                            + "cb.gender,am.jtname1,am.openingdt,am.opermode,am.acno,cb.fathername,am.nomination,cb.operationalriskrating,cb.networth,cb.pervillage,cb.pan_girnumber, "
                            + " ifnull( aa.IdentityNo,'') as aadhar_no,am.acctCategory,atm.card_no  "
                            + "from cbs_customer_master_detail cb "
                            + "left outer join customerid ci on cast(cb.customerid as unsigned) = ci.custid "
                            + "left outer join cbs_cust_misinfo cm on cb.customerid = cm.customerid "
                            + "left outer join accountmaster am on ci.acno = am.acno "
                            + "left outer join (select a.acno,a.SPFLAG from accountstatus a,(select acno,max(SPNO) spNo from accountstatus where effdt <= '" + toDt + "' group by acno)b "
                            + "where a.acno=b.acno and a.SPNO = b.spNo and a.effdt <= '" + toDt + "' " + acStatusBranch + " " + acStatusType + " " + spFlag + ")ac on ci.acno = ac.acno "
                            + "left outer join "
                            + "(select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,"
                            + "cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid "
                            + "union "
                            + "select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E') aa on  aa.CustomerId = cb.customerid "
                            + "left outer join "
                            + "(select acno,card_no,registration_dt,del_flag from atm_card_master where del_flag <>'I'and date_format(registration_dt,'%Y%m%d') <='" + toDt + "')atm "
                            + "on am.acno = atm.acno "
                            + "where am.openingdt between '" + frmDt + "' and '" + toDt + "' and (closingdate is null or closingdate ='' or closingdate >'" + toDt + "')" + statusCondition
                            + branchQuery + acTypeQuery + annualIncomeQuery + areaSectorQuery + occupationQuery + riskCategorizationQuery + acRangeQuery + atmCondition).getResultList();
                }
            }
            if (!tempList1.isEmpty()) {
                for (int i = 0; i < tempList1.size(); i++) {
                    ReportWriterPojo pojo = new ReportWriterPojo();
                    tempVector1 = (Vector) tempList1.get(i);
                    acno = tempVector1.get(13).toString();
                    jtName = tempVector1.get(10) != null ? tempVector1.get(10).toString() : "";
                    openDt = tempVector1.get(11).toString();
                    operationMode = tempVector1.get(12).toString();
                    fatherName = tempVector1.get(14) != null ? tempVector1.get(14).toString() : "";
                    nomineName = tempVector1.get(15) != null ? tempVector1.get(15).toString() : "";
                    custid = tempVector1.get(0).toString();
                    custName = tempVector1.get(1).toString();

                    dateofBrth = tempVector1.get(2) != null ? tempVector1.get(2).toString() : "";
                    if (!dateofBrth.equals("")) {
                        if (!(fromAge == null || fromAge.equals(""))) {
                            int yearDiff = CbsUtil.yearDiff(dmyFormat.parse(dateofBrth), dmyFormat.parse(dmyFormat.format(new Date())));
                            if (!(yearDiff >= Integer.parseInt(fromAge) && yearDiff <= Integer.parseInt(toAge))) {
                                continue;
                            }
                            pojo.setAge(yearDiff);
                        }
                        pojo.setDateofBirth(dateofBrth);
                    }

                    perAdd = (tempVector1.get(3) != null ? tempVector1.get(3).toString() : "") + " " + (tempVector1.get(4) != null ? tempVector1.get(4).toString() : "");
                    perPhoneNo = tempVector1.get(8) != null ? tempVector1.get(8).toString() : "";
                    mailAdd = (tempVector1.get(5) != null ? tempVector1.get(5).toString() : "") + " " + (tempVector1.get(6) != null ? tempVector1.get(6).toString() : "");
                    occupation = tempVector1.get(7) != null ? getRefDesc(occuList, tempVector1.get(7).toString()) : "";
                    gender = tempVector1.get(9) != null ? tempVector1.get(9).toString() : "";
                    if (flag) {
                        openBal = Double.parseDouble(amtFormatter.format(common.getBalanceOnDate(acno, frmDt)));
                        closeBal = Double.parseDouble(amtFormatter.format(common.getBalanceOnDate(acno, toDt)));
                    }
                    if (avgBalFlag) {
                        pojo.setAverageBalance(new BigDecimal(amtFormatter.format(getAverageBalanceOnQuery(acno, toDt).doubleValue())));
                    }

                    pojo.setAcNo(acno);
                    pojo.setAccopenDt(dmyFormat.format(ymdFormat.parse(openDt)));
                    pojo.setAcctype(acctype);
                    pojo.setCloseBal(closeBal);
                    pojo.setCrrAdd(mailAdd);
                    pojo.setCustId(custid);
                    pojo.setName(custName);
                    pojo.setFatherName(fatherName);
                    pojo.setNomineName(nomineName);
                    pojo.setOccupation(occupation);
                    pojo.setRiskCategorization(tempVector1.get(16) != null ? getRefDesc(riskCatList, tempVector1.get(16).toString()) : "");
                    pojo.setOperationMode(operMap.get(operationMode));
                    pojo.setOpenBal(openBal);
                    pojo.setPerAdd(perAdd);
                    pojo.setPhoneNo(perPhoneNo);
                    pojo.setJtName(jtName);
                    pojo.setAnnualIncome(tempVector1.get(17) != null ? new BigDecimal(tempVector1.get(17).toString()) : new BigDecimal("0"));
                    pojo.setArea(tempVector1.get(18) != null ? tempVector1.get(18).toString().toUpperCase() : "");
                    pojo.setPanNo(tempVector1.get(19) != null ? tempVector1.get(19).toString() : "");
                    pojo.setAadharCard(tempVector1.get(20) != null ? tempVector1.get(20).toString() : "");
                    pojo.setI(orderBy);
                    pojo.setActCategory(tempVector1.get(21) != null ? getRefDesc(actCatList, tempVector1.get(21).toString()) : "");
                    if (gender.equalsIgnoreCase("M")) {
                        pojo.setGender("Male");
                    } else if (gender.equalsIgnoreCase("F")) {
                        pojo.setGender("Female");
                    } else {
                        pojo.setGender("");
                    }
                    returnList.add(pojo);
                }
            }
            if (returnList.isEmpty()) {
                throw new ApplicationException("Data does not exist.");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        Collections.sort(returnList);
        return returnList;
    }

    public List getKycReport(String branch) throws ApplicationException {
        List<KycReportWriterPojo> returnList = new ArrayList<KycReportWriterPojo>();
        try {
            String branchQuery = "";
            if (!branch.equalsIgnoreCase("All")) {
                branchQuery = " and cb.primarybrCode = '" + branch + "'";
            }
            List kycList = em.createNativeQuery("select ky.customerid,ky.dependents,ky.no_child,ky.no_males_10,"
                    + "ky.no_males_20,ky.no_males_45,ky.no_males_60,ky.no_males_above_61,ky.no_fem_10,ky.no_fem_20,"
                    + "ky.no_fem_45,ky.no_fem_60,ky.no_fem_above_61 from cbs_customer_master_detail cb,cbs_kyc_details ky "
                    + "where cb.customerid=ky.customerid" + branchQuery).getResultList();
            if (kycList.isEmpty()) {
                throw new ApplicationException("There is no data for Kyc details.");
            }
            for (int i = 0; i < kycList.size(); i++) {
                KycReportWriterPojo pojo = new KycReportWriterPojo();
                Vector element = (Vector) kycList.get(i);

                pojo.setCustId(element.get(0).toString());
                pojo.setDependents(element.get(1) == null ? "" : element.get(1).toString());
                pojo.setNoChild(element.get(2) == null ? 0 : Integer.parseInt(element.get(2).toString()));
                pojo.setNoMales10(element.get(3) == null ? 0 : Integer.parseInt(element.get(3).toString()));
                pojo.setNoMales20(element.get(4) == null ? 0 : Integer.parseInt(element.get(4).toString()));
                pojo.setNoMales45(element.get(5) == null ? 0 : Integer.parseInt(element.get(5).toString()));
                pojo.setNoMales60(element.get(6) == null ? 0 : Integer.parseInt(element.get(6).toString()));
                pojo.setNoMalesAbove61(element.get(7) == null ? 0 : Integer.parseInt(element.get(7).toString()));
                pojo.setNoFem10(element.get(8) == null ? 0 : Integer.parseInt(element.get(8).toString()));
                pojo.setNoFem20(element.get(9) == null ? 0 : Integer.parseInt(element.get(9).toString()));
                pojo.setNoFem45(element.get(10) == null ? 0 : Integer.parseInt(element.get(10).toString()));
                pojo.setNoFem60(element.get(11) == null ? 0 : Integer.parseInt(element.get(11).toString()));
                pojo.setNoFemAbove61(element.get(12) == null ? 0 : Integer.parseInt(element.get(12).toString()));

                returnList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public List getCustomersData(String branch, int orderBy, String custStatus, String annualIncomeFrom, String annualIncomeTo,
            String areaSector, String occupation, String riskCategorization, String fromAge,
            String toAge, boolean shareQuery) throws ApplicationException {
        List<ReportWriterPojo> returnList = new ArrayList<ReportWriterPojo>();
        try {
            List tempList;
            Vector tempVector1;
            String dateofBrth, gender = "";
            double openBal = 0d, closeBal = 0d;

            String consQuery = "select distinct cb.customerid,trim(concat(trim(concat(trim(ifnull(cb.custname,'')),' ',trim(ifnull(cb.middle_name,'')))),' ',trim(ifnull(cb.last_name,'')))) as custname,"
                    + "date_format(cb.dateofbirth,'%d/%m/%Y') as dob, "
                    + "cb.peraddressline1,cb.peraddressline2,cb.mailaddressline1,cb.mailaddressline2,cm.occupationcode, "
                    + "cb.mobilenumber,cb.gender,cb.operationalriskrating,cb.networth,cb.pervillage,cb.fathername,cb.pan_girnumber,0,0, "
                    + " ifnull( aa.IdentityNo,'') "
                    + "from cbs_customer_master_detail cb "
                    //    + "left outer join customerid ci on cb.customerid = ci.custid "
                    + " left outer join cbs_cust_misinfo cm on cb.customerid = cm.customerid "
                    + " left join "
                    + "(select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a, "
                    + "cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid "
                    + "union "
                    + "select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where a.legal_document = 'E') aa on  aa.CustomerId = cb.customerid ";
            //   + "left outer join share_holder sh on cb.customerid = sh.custId";

            if (branch.equalsIgnoreCase("0A")) {
                consQuery = consQuery + " where cb.suspensionflg<>'Y'";
            } else {
                consQuery = consQuery + " where cb.primarybrCode = '" + branch + "' and cb.suspensionflg<>'Y'";
            }
            if (!(annualIncomeFrom == null || annualIncomeFrom.equals(""))) {
                if (consQuery.contains("where")) {
                    consQuery = consQuery + " and cb.networth between " + Double.parseDouble(annualIncomeFrom) + " and " + Double.parseDouble(annualIncomeTo) + "";
                } else {
                    consQuery = consQuery + " where cb.networth between " + Double.parseDouble(annualIncomeFrom) + " and " + Double.parseDouble(annualIncomeTo) + "";
                }
            }
            if (!(areaSector == null || areaSector.equals(""))) {
                if (consQuery.contains("where")) {
                    consQuery = consQuery + " and cb.pervillage in('" + areaSector + "')";
                } else {
                    consQuery = consQuery + " where cb.pervillage in('" + areaSector + "')";
                }
            }
            if (!(occupation == null || occupation.equalsIgnoreCase("--Select--"))) {
                if (!occupation.equalsIgnoreCase("All")) {
                    if (consQuery.contains("where")) {
                        consQuery = consQuery + " and cm.occupationcode in('" + occupation + "')";
                    } else {
                        consQuery = consQuery + " where cm.occupationcode in('" + occupation + "')";
                    }
                }
            }
            if (!(riskCategorization == null || riskCategorization.equalsIgnoreCase("--Select--"))) {
                if (!riskCategorization.equalsIgnoreCase("All")) {
                    if (consQuery.contains("where")) {
                        consQuery = consQuery + " and cb.operationalriskrating in('" + riskCategorization + "')";
                    } else {
                        consQuery = consQuery + " where cb.operationalriskrating in('" + riskCategorization + "')";
                    }
                }
            }

            System.out.println(new Date() + " : Execution Query is:--> " + consQuery);
            tempList = em.createNativeQuery(consQuery).getResultList();
            System.out.println(new Date() + " : Query Executed successfully");
            List shareList = new ArrayList();
            if (shareQuery) {
                shareList = em.createNativeQuery("select custid,ifnull(Salary,0),ifnull(gradepay,0) from share_holder").getResultList();
            }
            List occuList = common.getRefRecList("21");
            List riskCatList = common.getRefRecList("024");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    ReportWriterPojo pojo = new ReportWriterPojo();
                    tempVector1 = (Vector) tempList.get(i);
                    dateofBrth = tempVector1.get(2) != null ? tempVector1.get(2).toString() : "";
                    if (!dateofBrth.equals("")) {
                        if (!(fromAge == null || fromAge.equals(""))) {
                            int yearDiff = CbsUtil.yearDiff(dmyFormat.parse(dateofBrth), dmyFormat.parse(dmyFormat.format(new Date())));
                            if (!(yearDiff >= Integer.parseInt(fromAge) && yearDiff <= Integer.parseInt(toAge))) {
                                continue;
                            }
                            pojo.setAge(yearDiff);
                        }
                        pojo.setDateofBirth(dateofBrth);
                    }
                    pojo.setCustId(tempVector1.get(0).toString());
                    pojo.setName(tempVector1.get(1).toString());
                    gender = tempVector1.get(9) != null ? tempVector1.get(9).toString() : "";
                    pojo.setPhoneNo(tempVector1.get(8) != null ? tempVector1.get(8).toString() : "");
                    pojo.setOccupation(tempVector1.get(7) != null ? getRefDesc(occuList, tempVector1.get(7).toString()) : "");
                    pojo.setCrrAdd((tempVector1.get(5) != null ? tempVector1.get(5).toString() : "") + " " + (tempVector1.get(6) != null ? tempVector1.get(6).toString() : ""));
                    pojo.setPerAdd((tempVector1.get(3) != null ? tempVector1.get(3).toString() : "") + " " + (tempVector1.get(4) != null ? tempVector1.get(4).toString() : ""));
                    pojo.setRiskCategorization(tempVector1.get(10) != null ? getRefDesc(riskCatList, tempVector1.get(10).toString()) : "");
                    pojo.setAnnualIncome(tempVector1.get(11) != null ? new BigDecimal(tempVector1.get(11).toString()) : new BigDecimal("0"));
                    pojo.setArea(tempVector1.get(12) != null ? tempVector1.get(12).toString() : "");
                    pojo.setFatherName(tempVector1.get(13) != null ? tempVector1.get(13).toString() : "");
                    pojo.setPanNo(tempVector1.get(14) != null ? tempVector1.get(14).toString() : "");
                    pojo.setAcNo("");
                    pojo.setAccopenDt("");
                    pojo.setAcctype("");
                    pojo.setOperationMode("");
                    pojo.setJtName("");
                    pojo.setOpenBal(openBal);
                    pojo.setCloseBal(closeBal);
                    pojo.setI(orderBy);

                    pojo.setBasic(Double.parseDouble(tempVector1.get(15).toString()));
                    pojo.setDa(Double.parseDouble(tempVector1.get(16).toString()));
                    pojo.setTotal(Double.parseDouble(tempVector1.get(15).toString()) + Double.parseDouble(tempVector1.get(16).toString()));
                    if (shareQuery) {
                        Vector vect = getSalaryAndDA(shareList, tempVector1.get(0).toString());
                        if (null != vect) {
                            pojo.setBasic(Double.parseDouble(vect.get(1).toString()));
                            pojo.setDa(Double.parseDouble(vect.get(2).toString()));
                            pojo.setTotal(Double.parseDouble(vect.get(1).toString()) + Double.parseDouble(vect.get(2).toString()));
                        }
                    }

                    pojo.setAadharCard(tempVector1.get(17) != null ? tempVector1.get(17).toString() : "");

                    if (gender.equalsIgnoreCase("M")) {
                        pojo.setGender("Male");
                    } else if (gender.equalsIgnoreCase("F")) {
                        pojo.setGender("Female");
                    } else {
                        pojo.setGender("");
                    }
                    returnList.add(pojo);
                }
            }
            if (returnList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        Collections.sort(returnList);
        return returnList;
    }

    private String getRefDesc(List refDescList, String refCode) throws ApplicationException {
        try {
            for (int i = 0; i < refDescList.size(); i++) {
                Vector vect = (Vector) refDescList.get(i);
                if (vect.get(0).toString().equals(refCode)) {
                    return vect.get(1).toString();
                }
            }
            return "";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private Vector getSalaryAndDA(List shareDataList, String custId) throws ApplicationException {
        try {
            Vector returnVect = null;
            for (int i = 0; i < shareDataList.size(); i++) {
                Vector vect = (Vector) shareDataList.get(i);
                if (vect.get(0).toString().equals(custId)) {
                    returnVect = vect;
                    break;
                }
            }
            return returnVect;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<LoanIntCertPojo> getBranchWiseBal(String brCode, String dt) throws ApplicationException {
        List<LoanIntCertPojo> returnList = new ArrayList<LoanIntCertPojo>();
        try {
            List tempList = em.createNativeQuery("select acno from abb_parameter_info where purpose='HEAD OFFICE'").getResultList();
            if (tempList.isEmpty()) {
                throw new ApplicationException("Head office GL head does not exist");
            }
            Vector tempVec = (Vector) tempList.get(0);
            String glhead = tempVec.get(0).toString();

            tempList = em.createNativeQuery("select brncode, alphacode from branchmaster order by brncode").getResultList();
            if (tempList.isEmpty()) {
                throw new ApplicationException("Data does no exit in branch master");
            }
            for (int i = 0; i < tempList.size(); i++) {
                Vector tempVector = (Vector) tempList.get(i);
                String brnCode = tempVector.get(0).toString();
                brnCode = CbsUtil.lPadding(2, Integer.parseInt(brnCode));
                String alphaCode = tempVector.get(1).toString();
                String acNo = brnCode + glhead;
                BigDecimal amount = new BigDecimal(0);

                List rsList = new ArrayList();

                rsList = em.createNativeQuery("select ifnull(sum(g.cramt),0)-ifnull(sum(g.dramt),0) from gl_recon g where g.acno ='" + acNo
                        + "' and g.dt<='" + dt + "' and g.auth='y' group by g.acno").getResultList();
                if (!rsList.isEmpty()) {
                    Vector vect = (Vector) rsList.get(0);
                    amount = new BigDecimal(vect.get(0).toString());
                }
                LoanIntCertPojo pojo = new LoanIntCertPojo();
                pojo.setAcNo(acNo);
                pojo.setPrinAmt(amount);
                pojo.setCustName(alphaCode);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<AccountReportPojo> getPrintAction(String codeBr, String calFromDate, String caltoDate, String orderBy, String brCode, String tranType) throws ApplicationException {
        List<AccountReportPojo> returnList = new ArrayList<AccountReportPojo>();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        String orderBySeq = "";
        try {
            List list = em.createNativeQuery("select acno,acname from gltable where acno ='" + codeBr + "'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("GL Head does not exist. Please enter correct GL Head");
            }
            BigDecimal balance = new BigDecimal("0");

            Vector vector1 = (Vector) list.get(0);
            String acno = vector1.get(0).toString();
            String acname = vector1.get(1).toString();
            BigDecimal opBal = new BigDecimal("0");
            BigDecimal closeBal = new BigDecimal("0");
            opBal = new BigDecimal(0);
            if (orderBy.equals("A")) {
                orderBySeq = "advicebrncode,dt";
            } else {
                orderBySeq = "dt,advicebrncode";
            }
            int cnt = 0;
            if (brCode.equalsIgnoreCase("0A")) {
                if (tranType.equalsIgnoreCase("ALL")) {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "'").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "'").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'),adviceBrnCode from gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' order by " + orderBySeq + "").getResultList();
                } else if (tranType.equalsIgnoreCase("AT")) {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "'and (TranDesc = 999 or iy = 999) ").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "'and (TranDesc = 999 or iy = 999) ").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'),adviceBrnCode from gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' and (TranDesc = 999 or iy = 999) order by " + orderBySeq + "").getResultList();

                } else {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "' and org_brnid=dest_brnid and (TranDesc <> 999 and iy <> 999) ").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "' and org_brnid=dest_brnid and (TranDesc <> 999 and iy <> 999) ").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'), adviceBrnCode from gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' and org_brnid=dest_brnid and (TranDesc <> 999 and iy <> 999) order by " + orderBySeq + "").getResultList();
                }

            } else {
                if (tranType.equalsIgnoreCase("ALL")) {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "' and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "')").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "' and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "')").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'),adviceBrnCode FROM gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "') order by " + orderBySeq + "").getResultList();
                } else if (tranType.equalsIgnoreCase("AT")) {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "' and (TranDesc = 999 or iy = 999) and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "')").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "' and (TranDesc = 999 or iy = 999) and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "')").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'),adviceBrnCode FROM gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' and (TranDesc = 999 or iy = 999) and (adviceBrnCode='" + brCode + "' or dest_brnid='" + brCode + "') order by " + orderBySeq + "").getResultList();
                } else {
                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt < '" + calFromDate + "' and  Acno ='" + acno + "' and org_brnid=dest_brnid and  (TranDesc <> 999 and iy <>999) and (adviceBrnCode='" + brCode + "')").getResultList();
                    Vector vector2 = (Vector) list1.get(0);
                    opBal = new BigDecimal(vector2.get(0).toString());

                    list1 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from  gl_recon  where dt <= '" + caltoDate + "' and  Acno ='" + acno + "' and org_brnid=dest_brnid and (TranDesc <> 999 and iy <>999) and (adviceBrnCode='" + brCode + "')").getResultList();
                    Vector vect2 = (Vector) list1.get(0);
                    closeBal = new BigDecimal(vect2.get(0).toString());

                    list2 = em.createNativeQuery(" select ifnull(cramt,0) 'crbal' ,ifnull(dramt,0) 'drbal' ,ty,trantype,details,adviceno,date_format(dt,'%d/%m/%Y'),adviceBrnCode FROM gl_recon "
                            + " where acno='" + acno + "' and dt between'" + calFromDate + "' and '" + caltoDate + "' and auth='Y' and org_brnid=dest_brnid and  (TranDesc <> 999 and iy <>999) and (adviceBrnCode='" + brCode + "') order by " + orderBySeq + "").getResultList();
                }
            }
            if (!list2.isEmpty()) {
                for (int j = 0; j < list2.size(); j++) {
                    BigDecimal crBal = new BigDecimal("0");
                    BigDecimal drBal = new BigDecimal("0");
                    Vector vector3 = (Vector) list2.get(j);
                    AccountReportPojo pojo = new AccountReportPojo();

                    crBal = new BigDecimal(vector3.get(0).toString());
                    drBal = new BigDecimal(vector3.get(1).toString());
                    String details = vector3.get(4) != null ? vector3.get(4).toString() : "";
                    String adviceno = vector3.get(5) != null ? vector3.get(5).toString() : "";
                    String dt = vector3.get(6).toString();
                    String dest_brnid = vector3.get(7) != null ? vector3.get(7).toString() : "";
                    if (cnt == 0) {
                        balance = (opBal.add(crBal)).subtract(drBal);
                        cnt = 1;
                    } else {
                        balance = (balance.add(crBal)).subtract(drBal);
                    }
                    String destAlCode = "";
                    if (!dest_brnid.equals("")) {
                        List list3 = em.createNativeQuery(" select alphacode from branchmaster where brncode='" + dest_brnid + "'").getResultList();
                        Vector vect = (Vector) list3.get(0);
                        destAlCode = vect.get(0).toString();
                    }

                    pojo.setAcno(acno);
                    pojo.setAcname(acname);
                    pojo.setAdviceno(adviceno);
                    pojo.setBalance(balance);
                    pojo.setCloseBal(closeBal);
                    pojo.setCrBal(crBal);
                    pojo.setDest_brnid(destAlCode);
                    pojo.setDetails(details);
                    pojo.setDrBal(drBal);
                    pojo.setDt(dt);
                    pojo.setOpBal(opBal);
                    returnList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List<AgentLedgerReportPojo> getAgentLedgerAction(String agentCode, String acctType, String calFromDate, String caltoDate, String OrgnBrCode) throws ApplicationException {
        List<AgentLedgerReportPojo> returnList = new ArrayList<AgentLedgerReportPojo>();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List list3 = new ArrayList();
        String address = "";
        String agentName = "";
        try {
            List alist = em.createNativeQuery("select name, address from ddsagent where agcode='" + agentCode + "'and brncode='" + OrgnBrCode + "'").getResultList();
            if (!alist.isEmpty()) {
                for (int i = 0; i < alist.size(); i++) {
                    Vector vector1 = (Vector) alist.get(i);
                    agentName = vector1.get(0).toString();
                    address = vector1.get(1).toString();
                }
            }
            double closeBal = 0.0, balance = 0.0;
            if (OrgnBrCode.equalsIgnoreCase("0A")) {
                list1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ddstransaction where substring(acno,3,2)='" + acctType + "' and substring(acno,11,2)='" + agentCode + "'and dt< '" + calFromDate + "' /*and trantype <>8*/").getResultList();
            } else {
                list1 = em.createNativeQuery("select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ddstransaction where substring(acno,3,2)='" + acctType + "' and substring(acno,11,2)='" + agentCode + "'and substring(acno,1,2)='" + OrgnBrCode + "' and dt< '" + calFromDate + "' /*and trantype <>8*/").getResultList();
            }
            Vector vector2 = (Vector) list1.get(0);
            String opBal = vector2.get(0).toString();

            int bal = 0;
            if (OrgnBrCode.equalsIgnoreCase("0A")) {
                list2 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ddstransaction where substring(acno,3,2)='" + acctType + "' and substring(acno,11,2)='" + agentCode + "' and dt<='" + caltoDate + "' /*and trantype <>8*/").getResultList();
            } else {
                list2 = em.createNativeQuery(" select ifnull(sum(cramt),0)-ifnull(sum(dramt),0) from ddstransaction where substring(acno,3,2)='" + acctType + "' and substring(acno,11,2)='" + agentCode + "'and substring(acno,1,2)='" + OrgnBrCode + "' and dt<='" + caltoDate + "' /*and trantype <>8*/").getResultList();
            }
            Vector vector4 = (Vector) list2.get(0);
            closeBal = Double.parseDouble(vector4.get(0).toString());
            if (OrgnBrCode.equalsIgnoreCase("0A")) {
                list3 = em.createNativeQuery("select sum(ifnull(cramt,0)),sum(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y') from ddstransaction where substring(acno,3,2)='" + acctType + "' and  substring(acno,11,2)='" + agentCode + "' and  dt between '" + calFromDate + "' and '" + caltoDate + "'  /*and trantype <>8*/ group by dt,ty").getResultList();
            } else {
                list3 = em.createNativeQuery("select sum(ifnull(cramt,0)),sum(ifnull(dramt,0)),date_format(dt,'%d/%m/%Y') from ddstransaction where substring(acno,3,2)='" + acctType + "' and  substring(acno,11,2)='" + agentCode + "'and substring(acno,1,2)='" + OrgnBrCode + "' and  dt between '" + calFromDate + "' and '" + caltoDate + "'  /*and trantype <>8 */ group by dt,ty").getResultList();
            }

            if (!list3.isEmpty()) {
                for (int j = 0; j < list3.size(); j++) {
                    Vector vector3 = (Vector) list3.get(j);
                    AgentLedgerReportPojo pojo1 = new AgentLedgerReportPojo();
                    String crBal = vector3.get(0).toString();
                    String drBal = vector3.get(1).toString();
                    String dt = vector3.get(2).toString();
                    if (bal == 0) {
                        balance = Double.parseDouble(opBal) + Double.parseDouble(crBal) - Double.parseDouble(drBal);
                        bal = 1;
                    } else {
                        balance = balance + Double.parseDouble(crBal) - Double.parseDouble(drBal);
                    }
                    pojo1.setCrBal(Double.parseDouble(vector3.get(0).toString()));
                    pojo1.setDrBal(Double.parseDouble(vector3.get(1).toString()));
                    pojo1.setOpBal(Double.parseDouble(vector2.get(0).toString()));
                    pojo1.setDt(dt);
                    pojo1.setBalance(balance);
                    pojo1.setCloseBal(closeBal);
                    pojo1.setAgentCode(agentCode);
                    pojo1.setAddress(address);
                    pojo1.setAgentName(agentName);
                    returnList.add(pojo1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getActTypeList() throws ApplicationException {
        List returnList = new ArrayList();
        List actlist = em.createNativeQuery("select acctcode,accttype from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "'").getResultList();
        if (!actlist.isEmpty()) {
            for (int k = 0; k < actlist.size(); k++) {
                Vector vector1 = (Vector) actlist.get(k);
                String acType = vector1.get(0).toString();
                returnList = actlist;
            }
        }
        return returnList;
    }

    public List getagentWiseMonthlyReport(String orgnBrCode, String acType, String Frdt, String Todt) {
        List<DDSAgentNaturePojo> repLoansList = new ArrayList<DDSAgentNaturePojo>();
        List list1 = new ArrayList();
        List list2 = new ArrayList();
        List agCodelist = new ArrayList();
        String brCode = "";
        try {
            if (orgnBrCode.equalsIgnoreCase("0A")) {
                orgnBrCode = "90";
            }

            List brList = common.getAlphacodeAllAndBranch(orgnBrCode);
            for (int j = 0; j < brList.size(); j++) {
                Vector vtr = (Vector) brList.get(j);
                brCode = vtr.get(1).toString().length() < 2 ? "0" + vtr.get(1).toString() : vtr.get(1).toString();
                agCodelist = em.createNativeQuery(" select agcode,name from ddsagent where brncode='" + brCode + "'").getResultList();

                if (!agCodelist.isEmpty()) {
                    for (int i = 0; i < agCodelist.size(); i++) {
                        Vector vector1 = (Vector) agCodelist.get(i);
                        String agcode = vector1.get(0).toString();
                        String name = vector1.get(1).toString();

                        list1 = em.createNativeQuery("select ifnull(sum(cramt),0) from ddstransaction where substring(acno,11,2)='" + agcode
                                + "' and substring(acno,1,2)='" + brCode + "' and auth='y' and trantype= 0 AND TY = 0 and dt between '" + Frdt + "' and '"
                                + Todt + "'and substring(acno,3,2)='" + acType + "'").getResultList();

                        Vector vector2 = (Vector) list1.get(0);
                        BigDecimal cashCr = new BigDecimal("0");
                        cashCr = new BigDecimal(vector2.get(0).toString());

                        list2 = em.createNativeQuery("select ifnull(sum(cramt),0) from ddstransaction where substring(acno,11,2)='" + agcode
                                + "' and substring(acno,1,2)='" + brCode + "' and auth='y' and trantype=2 and dt between '" + Frdt + "' and '"
                                + Todt + "' and substring(acno,3,2)='" + acType + "'").getResultList();

                        Vector vector3 = (Vector) list2.get(0);
                        BigDecimal transfercash = new BigDecimal("0");
                        transfercash = new BigDecimal(vector3.get(0).toString());

                        DDSAgentNaturePojo pojo = new DDSAgentNaturePojo();
                        pojo.setBranch(common.getBranchNameByBrncode(brCode));
                        pojo.setAgcode(agcode);
                        pojo.setName(name);
                        pojo.setCashCr(cashCr);
                        pojo.setTransfercash(transfercash);
                        repLoansList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repLoansList;
    }

    public List<RdInstallmentPojo> getRdInstallmentDetails(String opt, String acno, String date, String brncode) throws ApplicationException {
        List<RdInstallmentPojo> rdInstallmentList = new ArrayList<RdInstallmentPojo>();
        List resultList = new ArrayList();
        List list1 = new ArrayList();
        try {
            if (opt.equalsIgnoreCase("AW")) {
                resultList = em.createNativeQuery("select acno,custname,ifnull(rdinstal,0),ifnull(intdeposit,0),date_format(openingdt,'%d/%m/%Y'),date_format(rdmatdate,'%d/%m/%Y') as rdmatdate,timestampdiff(month,openingdt,rdmatdate) "
                        + " from accountmaster where acno='" + acno + "'").getResultList();
            } else {
                resultList = em.createNativeQuery("select acno,custname,ifnull(rdinstal,0),ifnull(intdeposit,0),date_format(openingdt,'%d/%m/%Y'),date_format(rdmatdate,'%d/%m/%Y') as rdmatdate,timestampdiff(month,openingdt,rdmatdate) "
                        + " from accountmaster where accttype in (select acctcode from accounttypemaster where acctnature = '" + CbsConstant.RECURRING_AC + "') "
                        + "and openingdt<='" + date + "'  and (ifnull(closingdate,'')='' or closingdate>'" + date + "' ) and curbrcode='" + brncode + "' order by acno").getResultList();
            }
            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vector1 = (Vector) resultList.get(i);
                    String rdMatDt = "";
                    String accno = vector1.get(0).toString();
                    String name = vector1.get(1).toString();
                    String rdInstal = vector1.get(2).toString();
                    String intDeposit = vector1.get(3).toString();
                    String openDt = vector1.get(4).toString();
                    if (vector1.get(5) != null) {
                        rdMatDt = vector1.get(5).toString();
                    } else {
                        rdMatDt = "";
                    }

                    int tot = CbsUtil.monthDiff(dmyFormat.parse(openDt), dmyFormat.parse(rdMatDt));
                    list1 = em.createNativeQuery("select date_format(duedt,'%d/%m/%Y'),status,date_format(paymentdt,'%d/%m/%Y'),installamt from rd_installment where acno='" + accno + "'").getResultList();
                    for (int j = 0; j < list1.size(); j++) {
                        Vector vector2 = (Vector) list1.get(j);
                        String pymtDate = "";
                        String dueDate = vector2.get(0).toString();
                        String stat = vector2.get(1).toString();
                        if (vector2.get(2) != null) {
                            pymtDate = vector2.get(2).toString();
                        } else {
                            pymtDate = "";
                        }

                        double instalAmt = Double.parseDouble(vector2.get(3).toString());

                        RdInstallmentPojo pojo = new RdInstallmentPojo();

                        pojo.setAccountNumber(accno);
                        pojo.setCustName(name);
                        pojo.setRdDueDate(dueDate);
                        pojo.setRdInstalAmt(instalAmt);
                        pojo.setRdMadeDate(openDt);
                        pojo.setRdMatDate(rdMatDt);
                        pojo.setRdPymtDate(pymtDate);
                        pojo.setStatus(stat);
                        pojo.setTotInstallment(tot);
                        pojo.setRoi(Double.parseDouble(intDeposit));

                        rdInstallmentList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return rdInstallmentList;
    }

    public List getAcctTypeList1() throws ApplicationException {
        List returnList = new ArrayList();
        List actlist = em.createNativeQuery("select acctcode,accttype from accounttypemaster where acctnature  in('CA','TL','DL') and CrDbFlag in('B','D') order by acctcode").getResultList();
        if (!actlist.isEmpty()) {
            for (int k = 0; k < actlist.size(); k++) {
                Vector vector1 = (Vector) actlist.get(k);
                String acType = vector1.get(0).toString();
                returnList = actlist;
            }
        }
        return returnList;
    }

    public List<loanInsurancePojo> getInsuranceReport(String insurancePolicy, String acctType, String caltoDate, int day, String branchCode) throws ApplicationException {
        List<loanInsurancePojo> repDataList = new ArrayList<loanInsurancePojo>();
        List resultList = new ArrayList();
        try {
            String brncode = "";
            if (branchCode.equalsIgnoreCase("90") || branchCode.equalsIgnoreCase("0A")) {
                brncode = "";
            } else {
                brncode = " lp.brcode ='" + branchCode + "' and ";
            }

            if (acctType.equalsIgnoreCase("ALL")) {
                if (insurancePolicy.equalsIgnoreCase("ALL")) {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),lp.custname,InsComName,CoverNoteNo from loan_insurance l, loan_appparameter lp "
                            + "where " + brncode + "  l.acno=lp.acno /*and upper(status)='ACTIVE'*/ and fromdt <= '" + caltoDate + "' order by l.todt,lp.acno ").getResultList();
                } else if (insurancePolicy.equalsIgnoreCase("ACTIVE")) {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),"
                            + " lp.custname,InsComName,CoverNoteNo  from loan_insurance l, loan_appparameter lp, accountmaster am "
                            + " where " + brncode + " l.acno=lp.acno  and am.acno =l.acno and am.acno= lp.acno "
                            + " and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + caltoDate + "')  and /*upper(status)='ACTIVE' and */ "
                            + " fromDt <= '" + caltoDate + "'  and ((todt>'" + caltoDate + "' and status ='EXPIRED') or (status ='ACTIVE')) order by l.todt,lp.acno ").getResultList();
                } else {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),lp.custname,InsComName,CoverNoteNo  from loan_insurance l, loan_appparameter lp "
                            + "where " + brncode + "  l.acno=lp.acno and upper(status)='ACTIVE' and todt>= '" + caltoDate + "' and todt<= date_format( DATE_ADD('" + caltoDate + "', INTERVAL " + day + " DAY),'%Y%m%d')order by l.todt,lp.acno ").getResultList();
                }
            } else {
                if (insurancePolicy.equalsIgnoreCase("ALL")) {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),lp.custname,InsComName,CoverNoteNo from loan_insurance l, "
                            + "loan_appparameter lp where substring(lp.acno,3,2)='" + acctType + "' and " + brncode + "  l.acno=lp.acno /*and upper(status)='ACTIVE' */ "
                            + "and fromdt <= '" + caltoDate + "' order by l.todt,lp.acno").getResultList();
                } else if (insurancePolicy.equalsIgnoreCase("ACTIVE")) {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),"
                            + " lp.custname,InsComName,CoverNoteNo  from loan_insurance l,loan_appparameter lp , accountmaster am "
                            + " where substring(lp.acno,3,2)='" + acctType + "' and  " + brncode + " l.acno=lp.acno  and am.acno =l.acno and am.acno= lp.acno "
                            + " and (am.closingdate is null or am.closingdate ='' or am.closingdate >'" + caltoDate + "') /*and upper(status)='ACTIVE'*/ "
                            + " and fromDt <= '" + caltoDate + "' and ((todt>'" + caltoDate + "' and status ='EXPIRED') or (status ='ACTIVE'))  order by l.todt,lp.acno ").getResultList();
                } else {
                    resultList = em.createNativeQuery("select lp.acno,particulars,premiumpaid,date_format(todt,'%d/%m/%Y'),status,date_format(dt,'%d/%m/%Y'),lp.custname,InsComName,CoverNoteNo  from loan_insurance l,"
                            + " loan_appparameter lp where substring(lp.acno,3,2)='" + acctType + "' and " + brncode + "  l.acno=lp.acno and upper(status)='ACTIVE'  "
                            + "and ToDt>= '" + caltoDate + "' and todt<= date_format( DATE_ADD('" + caltoDate + "', INTERVAL " + day + " DAY),'%Y%m%d') order by l.todt,lp.acno").getResultList();
                }
            }

            if (!resultList.isEmpty()) {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector vector1 = (Vector) resultList.get(i);
                    String acno = vector1.get(0).toString();
                    List phoneNoList = em.createNativeQuery("select  ifnull(mobilenumber,'') from cbs_customer_master_detail where customerid in(select custid from customerid where acno = '" + acno + "')").getResultList();
                    String phoneNo = "";

                    if (!phoneNoList.isEmpty()) {
                        Vector vtr = (Vector) phoneNoList.get(0);
                        phoneNo = vtr.get(0).toString();
                    }

                    String particulars = vector1.get(1).toString();
                    String premiumpaid = vector1.get(2).toString();
                    String todt = vector1.get(3).toString();
                    String status = vector1.get(4).toString();
                    String dt = vector1.get(5).toString();
                    String custname = vector1.get(6).toString();
                    String companyName = vector1.get(7) == null ? "" : vector1.get(7).toString();
                    String policyNo = vector1.get(8).toString();

                    loanInsurancePojo pojo = new loanInsurancePojo();
                    pojo.setAcno(acno);
                    pojo.setParticulars(particulars);
                    pojo.setPremiumpaid(premiumpaid);
                    pojo.setTodt(todt);
                    pojo.setStatus(status);
                    pojo.setDt(dt);
                    pojo.setCustname(custname);
                    pojo.setCompanyName(companyName);
                    pojo.setPolicyNo(policyNo);
                    if (phoneNo.length() > 9) {
                        pojo.setPhoneNo(phoneNo.substring(0, 10));
                    } else {
                        pojo.setPhoneNo("");
                    }
                    repDataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return repDataList;
    }

    public List<InterestReportPojo> interestReport(String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode, String tdsApp, String repType) throws ApplicationException {
        List<InterestReportPojo> InterestReportPojos = new ArrayList<InterestReportPojo>();
        try {
            List result = new ArrayList();
            // String acctNature = common.getAcNatureByAcType(acCode);
            // List fyearList = em.createNativeQuery("select yearendflag from cbs_yearend where mindate between '" + fDt + "' and '" + tDt + "'").getResultList();
//            List fyearList = em.createNativeQuery("select yearendflag from cbs_yearend where mindate<= '" + tDt + "' and maxdate >= '" + tDt + "'").getResultList();
//            Vector fVector = (Vector) fyearList.get(0);
//            String currentFyear = fVector.get(0).toString();

            Map<String, String> tdsMap = new HashMap<>();
            List finList = em.createNativeQuery("select MINDATE,MAXDATE from yearend where YEARENDFLAG = 'N' group by F_YEAR").getResultList();
            Vector finVector = (Vector) finList.get(0);
            String findt = finVector.get(0).toString();
            if (ymd.parse(fDt).getTime() < ymd.parse(findt).getTime()) {
                List tdsList = em.createNativeQuery("select customerid,'N'tdsFlag from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + fDt + "' and '" + tDt + "' group by customerid").getResultList();
                for (int i = 0; i < tdsList.size(); i++) {
                    Vector tdsVector = (Vector) tdsList.get(i);
                    tdsMap.put(tdsVector.get(0).toString(), tdsVector.get(1).toString());
                }
            }

            List tableResult = new ArrayList();
            if (acCode.equalsIgnoreCase("ALL")) {
                tableResult = em.createNativeQuery("Select AcctCode,AcctDesc From accounttypemaster Where AcctNature in ('" + CbsConstant.MS_AC + "','" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "') order by acctnature").getResultList();
            } else {
                tableResult = em.createNativeQuery("Select AcctCode,AcctDesc From accounttypemaster Where AcctCode = '" + acCode + "' ").getResultList();
            }

            for (int i = 0; i < tableResult.size(); i++) {
                Vector acVector = (Vector) tableResult.get(i);
                String actCode = acVector.get(0).toString();
                String actDesc = acVector.get(1).toString();
                String acctNature = common.getAcNatureByAcType(actCode);
                String branchQuery = "";
                if (!(brCode.equalsIgnoreCase("0A")) || (brCode.equalsIgnoreCase("All"))) {
                    branchQuery = "and a.curbrcode ='" + brCode + "'";
                }
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {

                    result = em.createNativeQuery("select aa.acno,aa.custName,aa.fatherName,aa.dob,panNo,aa.address,aa.thIntAmt,aa.custid,ifnull(bb.tdsAmt,0),aa.tdsflag from "
                            + "(select a.acno,ifnull(a.custname,'') custName,ifnull(c.fathername,'') fatherName,ifnull(c.dob,'19000101') dob, "
                            + "ifnull(c.panno,'') panNo,ifnull(c.praddress,'') address,sum(th.interest) thIntAmt, d.custid,tdsflag  from td_accountmaster a, "
                            + "td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno and a.acno = th.acno "
                            + "and a.accttype = '" + actCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode " + branchQuery + " and custid in "
                            + "( select CustId from "
                            + "( select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno  and a.dt between  '" + fDt + "' and '" + tDt + "' group by c.CustId "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId  "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId )ff "
                            + "group by CustId having sum(intAmt) between " + fAmt + " and " + tAmt + ") "
                            + "and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype "
                            + "and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
                            + "order by d.custid,acno)aa "
                            + "left join "
                            + "(select acno,0 as intAmt,ifnull(sum(TDS),0) as tdsAmt from tds_reserve_history where Dt between '" + fDt + "' and '" + tDt + "' group by acno)bb "
                            + "on aa.acno = bb.acno "
                            + " group by aa.custid,aa.acno ").getResultList();

                } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

                    result = em.createNativeQuery(" select aa.acno,custName,fatherName,dob,panNo,address,aa.intAmt,custid,ifnull(bb.tdsAmt,0),aa.tdsflag from "
                            + "(select a.acno,ifnull(a.custname,'') custName,ifnull(c.fathername,'') fatherName,ifnull(c.dob,'19000101') dob,ifnull(c.panno,'') panNo,ifnull(c.praddress,'') address,sum(r.interest) intAmt, d.custid,tdsflag "
                            + "from rd_interesthistory r,accountmaster a,"
                            + "customermaster c , customerid d where a.acno = d.acno and  a.acno = r.acno and a.accttype = '" + actCode + "' and substring(a.acno,5,6)=c.custno and "
                            + "a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype " + branchQuery + " "
                            + "and d.custid in(select CustId from ("
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno  and a.dt between  '" + fDt + "' and '" + tDt + "' group by c.CustId "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId )ff "
                            + "group by CustId having sum(intAmt) between " + fAmt + " and " + tAmt + " ) "
                            + "and r.dt between '" + fDt + "' and '" + tDt + "' "
                            + "group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername  "
                            + "order by d.custid,acno)aa "
                            + "left join "
                            + "(select acno,0 as intAmt,ifnull(sum(TDS),0) as tdsAmt from tds_reserve_history where Dt between '" + fDt + "' and '" + tDt + "' group by acno)bb "
                            + "on aa.acno = bb.acno group by aa.custid,aa.acno").getResultList();

                } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {

                    result = em.createNativeQuery("select aa.acno,custName,fatherName,dob,panNo,address,aa.intAmt,custid,ifnull(bb.tdsAmt,0),aa.tdsflag from "
                            + "(select a.acno,ifnull(a.custname,'') custName,ifnull(c.fathername,'') fatherName,ifnull(c.dob,'19000101') dob,ifnull(c.panno,'') panNo,ifnull(c.praddress,'') address,sum(r.interest) intAmt, d.custid,tdsflag "
                            + "from rd_interesthistory r,accountmaster a, "
                            + "customermaster c , customerid d where a.acno = d.acno and  a.acno = r.acno and a.accttype = '" + actCode + "' and substring(a.acno,5,6)=c.custno and "
                            + "a.curbrcode = c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype " + branchQuery + " "
                            + "and d.custid in(select CustId from ( "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno  and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId "
                            + "union all "
                            + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
                            + "where a.Acno = c.Acno and a.dt between '" + fDt + "' and '" + tDt + "' group by c.CustId )ff "
                            + " group by CustId having sum(intAmt) between " + fAmt + " and " + tAmt + ") "
                            + "and r.dt between '" + fDt + "' and '" + tDt + "' "
                            + "group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
                            + "order by d.custid,acno)aa "
                            + "left join"
                            + "(select acno,0 as intAmt,ifnull(sum(TDS),0) as tdsAmt from tds_reserve_history where Dt between '" + fDt + "' and '" + tDt + "' group by acno)bb "
                            + " on aa.acno = bb.acno group by aa.custid,aa.acno ").getResultList();

                } else {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),ifnull(c.panno,''),ifnull(c.praddress,''),sum(r.cramt), d.custid,0 as tdsAmt, ifnull(tdsflag,'ALL') from recon r,accountmaster a,"
                            + " customermaster c , customerid d where a.acno = d.acno and  a.acno = r.acno and a.accttype = '" + actCode + "' and substring(a.acno,5,6)=c.custno and "
                            + " a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype " + branchQuery + " and r.trantype = 8 and r.dt between '" + fDt + "' and '" + tDt + "' "
                            + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername having sum(r.cramt) between '" + fAmt + "' and '" + tAmt + "' "
                            + " order by d.custid,acno ").getResultList();
                }
                double intAmt = 0d;
                String tdsFlag = "";
                List tdsFlaList = new ArrayList();
                List intList = new ArrayList();

                if (result.size() > 0) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector record = (Vector) result.get(j);
                        InterestReportPojo intRep = new InterestReportPojo();

                        if (!acctNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            if (ymd.parse(fDt).getTime() < ymd.parse(findt).getTime()) {
                                tdsFlag = tdsMap.get(record.get(7).toString()) == null ? "Y" : tdsMap.get(record.get(7).toString());
                            }
                        }
                        String interest = record.get(6).toString();
                        String Acno = record.get(0).toString();
                        intRep.setAcNo(record.get(0).toString());

                        double tdsAmt = Double.parseDouble(record.get(8).toString());

                        intRep.setCustName(record.get(1).toString());
                        intRep.setFatherName(record.get(2).toString() != null ? record.get(2).toString() : "");
                        String dob = "";
                        if (record.get(3).toString().equalsIgnoreCase("")) {
                            dob = "19000101";
                        } else {
                            dob = record.get(3).toString().substring(6, 8) + "/" + record.get(3).toString().substring(4, 6) + "/" + record.get(3).toString().substring(0, 4);
                        }

                        if (dob.equalsIgnoreCase("01/01/1900")) {
                            dob = "";
                        }
                        intRep.setDob(dob);
                        intRep.setPanNo(record.get(4).toString() != null ? record.get(4).toString() : "");
                        intRep.setPerAddr(record.get(5).toString() != null ? record.get(5).toString() : "");
                        intRep.setIntAmt(new BigDecimal(interest));
                        intRep.setTdsAmt(new BigDecimal(tdsAmt));
                        intRep.setCustId(record.get(7).toString());
                        intRep.setActCode(actCode);
                        intRep.setActDesc(actDesc);

                        if (tdsApp.equalsIgnoreCase("ALL")) {
                            InterestReportPojos.add(intRep);
                        } else if (tdsApp.equalsIgnoreCase(tdsFlag)) {
                            InterestReportPojos.add(intRep);
                        }
                    }
                }
            }
            return InterestReportPojos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List<RbiSossPojo> getTdsIntSummary(String brnCode, String acType, String fromDt, String toDt) throws ApplicationException {
        try {
            List<RbiSossPojo> rbiPojoTable = new ArrayList<RbiSossPojo>();
            RbiSossPojo rbiSossPojo = new RbiSossPojo();
            BigDecimal bal = new BigDecimal("0");
            Vector ele = null;
            String acTypeQuery = "", brnQuery = "", glHeadQuery = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = "select acctcode from accounttypemaster where acctnature in ('FD','MS','RD','DS')";
                    glHeadQuery = "select distinct(GLHeadInt) from accounttypemaster where acctnature in ('FD','MS','RD','DS')";
                } else {
                    acTypeQuery = "'" + acType + "'";
                    glHeadQuery = "select distinct(GLHeadInt) from accounttypemaster where acctcode in ('" + acType + "')";
                }
            } else {
                brnQuery = " and substring(z.acno,1,2) ='" + brnCode + "' ";
                if (acType.equalsIgnoreCase("ALL")) {
                    acTypeQuery = "select acctcode from accounttypemaster where acctnature in ('FD','MS','RD','DS')";
                    glHeadQuery = "select distinct(GLHeadInt) from accounttypemaster where acctnature in ('FD','MS','RD','DS')";
                } else {
                    acTypeQuery = "'" + acType + "'";
                    glHeadQuery = "select distinct(GLHeadInt) from accounttypemaster where acctcode in ('" + acType + "')";
                }
            }
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(0);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setCountApplicable("No");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setDescription("Total interest paid on FD + RD + DS during " + dmyFormat.format(ymd.parse(fromDt)) + " to " + dmyFormat.format(ymd.parse(toDt)));
            rbiPojoTable.add(rbiSossPojo);


            /*Details */
            /*(1) Total interest paid on FD+RD during F.Y.*/
            String query = "select substring(z.acno,3,8) as acctCode,b.AcName as AcctDesc, cast(ifnull(sum(z.cramt-z.dramt),0)as decimal(25,2)) as amt from gl_recon z,gltable b "
                    + " where z.acno = b.acno and z.dt between  '" + fromDt + "' and '" + toDt + "'  and z.auth ='Y' and z.trandesc<>13 and  "
                    + " substring(z.acno,3,8) in (" + glHeadQuery + ") " + brnQuery
                    + " group by substring(z.acno,3,8) ";
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(1);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("PL Head Interest");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            rbiPojoTable.add(rbiSossPojo);
            List reportList = em.createNativeQuery(query).getResultList();
            if (!reportList.isEmpty()) {
                for (int i = 0; i < reportList.size(); i++) {
                    rbiSossPojo = new RbiSossPojo();
                    ele = (Vector) reportList.get(i);
                    String acctDesc = ele.get(1).toString();
                    bal = new BigDecimal(ele.get(2).toString());
                    rbiSossPojo.setgNo(1);
                    rbiSossPojo.setsGNo(1);
                    rbiSossPojo.setSsGNo(i + 1);
                    rbiSossPojo.setSssGNo(0);
                    rbiSossPojo.setSsssGNo(0);
                    rbiSossPojo.setDescription("    " + acctDesc);
                    rbiSossPojo.setCountApplicable("");
                    rbiSossPojo.setNpaClassification("");
                    rbiSossPojo.setAmt(bal.abs());
                    rbiPojoTable.add(rbiSossPojo);
                }
            }
            /*(2) Total interest paid on FD+RD during F.Y.*/
            query = "select substring(z.acno,3,2) as acctCode, b.AcctDesc , cast(ifnull(sum(z.interest),0) as decimal(25,2)) as intAmt from td_interesthistory z, accounttypemaster b "
                    + " where substring(z.acno,3,2) in (" + acTypeQuery + ") " + brnQuery
                    + " and substring(z.acno,3,2) = b.AcctCode and z.dt between '" + fromDt + "' and '" + toDt + "' group by substring(z.acno,3,2) "
                    + " union all "
                    + " select substring(z.acno,3,2) as acctCode, b.AcctDesc, cast(ifnull(sum(z.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory z, accounttypemaster b "
                    + " where substring(z.acno,3,2) in (" + acTypeQuery + ") " + brnQuery
                    + " and substring(z.acno,3,2) = b.AcctCode and z.dt between '" + fromDt + "' and '" + toDt + "' group by substring(z.acno,3,2) "
                    + " union all "
                    + " select substring(z.acno,3,2) as acctCode, b.AcctDesc, cast(ifnull(sum(z.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory z, accounttypemaster b "
                    + " where substring(z.acno,3,2) in (" + acTypeQuery + ") " + brnQuery
                    + " and substring(z.acno,3,2) = b.AcctCode and z.dt between '" + fromDt + "' and '" + toDt + "' group by substring(z.acno,3,2) ";
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(2);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("Total Interest History ");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            rbiPojoTable.add(rbiSossPojo);
            reportList = em.createNativeQuery(query).getResultList();
            if (!reportList.isEmpty()) {
                for (int i = 0; i < reportList.size(); i++) {
                    rbiSossPojo = new RbiSossPojo();
                    ele = (Vector) reportList.get(i);
                    String acctDesc = ele.get(1).toString();
                    bal = new BigDecimal(ele.get(2).toString());
                    rbiSossPojo.setgNo(1);
                    rbiSossPojo.setsGNo(2);
                    rbiSossPojo.setSsGNo(i + 1);
                    rbiSossPojo.setSssGNo(0);
                    rbiSossPojo.setSsssGNo(0);
                    rbiSossPojo.setDescription("    " + acctDesc);
                    rbiSossPojo.setCountApplicable("");
                    rbiSossPojo.setNpaClassification("");
                    rbiSossPojo.setAmt(bal.abs());
                    rbiPojoTable.add(rbiSossPojo);
                }
            }

            /*(2) (-) Less interest on FD + RD paid less than Rs. 10000*/
            query = "select ifnull(sum(z.intamt),0) from "
                    + "(select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from dds_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno) z, "
                    + "(select custid from ( "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + fromDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c  "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId) j group by custid "
                    + "having sum(intAmt) < 10000) m where z.cid = m.custid " + brnQuery;

            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(3);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(-) Less interest on FD + RD + DS paid less than Rs. 10000");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            reportList = em.createNativeQuery(query).getResultList();
            if (!reportList.isEmpty()) {
                ele = (Vector) reportList.get(0);
                bal = new BigDecimal(ele.get(0).toString());
                rbiSossPojo.setAmt(bal.abs());
                rbiPojoTable.add(rbiSossPojo);
            } else {
                rbiPojoTable.add(rbiSossPojo);
            }

            /*(3) (+) Plus Interest paid Rs.10000/- and above but shown in above statement of Interest below Rs.10000*/
            query = "select ifnull(sum(z.intamt),0) from "
                    + "(select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno "
                    + "union all "
                    + "select c.CustId as cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt,c.acno as acno from dds_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId,c.acno) z, "
                    + "(select custid from ( "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + fromDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c  "
                    + "where a.Acno = c.Acno and a.dt between '" + fromDt + "' and '" + toDt + "' group by c.CustId) j group by custid "
                    + "having sum(intAmt) >= 10000) m where z.cid = m.custid " + brnQuery;

            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(4);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(+) Plus Interest paid Rs.10000/- and above but shown in above statement of Interest below Rs.10000");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            BigDecimal thirdAmt = new BigDecimal("0");
            reportList = em.createNativeQuery(query).getResultList();
            if (!reportList.isEmpty()) {
                ele = (Vector) reportList.get(0);
                thirdAmt = new BigDecimal(ele.get(0).toString());
                rbiSossPojo.setAmt(thirdAmt.abs());
                rbiPojoTable.add(rbiSossPojo);
            } else {
                rbiPojoTable.add(rbiSossPojo);
            }
            //Common interest, 15G/15H has been submitted but TDS also teducted 
            BigDecimal commmonIntt = miscRemote.getVoucherWiseInterest(fromDt, toDt, brnCode);
            /*(4) (-) Amount of 15G/15H i.e , actual amount of interest exempted on A/c of submission of these forms during the F.Y.*/
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(5);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(-) Amount of 15G/15H i.e , actual amount of interest exempted on A/c of submission of these forms during the F.Y.");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            BigDecimal fourthAmt = miscRemote.getExemptedInterest(brnCode, fromDt, toDt);
            fourthAmt = fourthAmt.subtract(commmonIntt);
            rbiSossPojo.setAmt(fourthAmt.abs());
            rbiPojoTable.add(rbiSossPojo);

            /*(5) The amount of interest on which TDS should have been deducted*/
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(6);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("The amount of interest on which TDS should have been deducted");
            rbiSossPojo.setAmt(new BigDecimal(thirdAmt.doubleValue() - fourthAmt.doubleValue()));
            rbiPojoTable.add(rbiSossPojo);


            /*(6) (-) Less Amount of interest on which TDS has actually been deducted*/
            // List<TdReceiptIntDetailPojo> tdsBookList = getTdReceiptIntData("C", "", fromDt, toDt, "", brnCode, "S", "RS", "S");
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(7);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(-) Less Amount of interest on which TDS has actually been deducted");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            BigDecimal amt = miscRemote.getTdsDeductedInterest(brnCode, fromDt, toDt);
            amt = amt.add(commmonIntt);
            rbiSossPojo.setAmt(amt);
            rbiPojoTable.add(rbiSossPojo);

            /*(7) (-) Less Amount of interest of those Customer which are merged after the To Date*/
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(7);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(-) Less Amount of interest of those Customer which are merged after the To Date");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            BigDecimal intAmt = miscRemote.getInterestOfMergeIdAfterToDate(brnCode, fromDt, toDt);
            rbiSossPojo.setAmt(intAmt);
            rbiPojoTable.add(rbiSossPojo);

            /*(8) (-) Amount left on which TDS not deducted*/
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(8);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("(-) Amount left on which TDS not deducted");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            rbiSossPojo.setAmt(new BigDecimal(thirdAmt.doubleValue() - fourthAmt.doubleValue() - amt.doubleValue() - intAmt.doubleValue()));
            rbiPojoTable.add(rbiSossPojo);

            /*(9) (+) Tds Reserved but not deducted from Account*/
            query = "select ifnull(sum(tds),0) from tds_reserve_history z where (recovered = 'NR' or tdsrecovereddt > '" + toDt + "') and dt between '"
                    + fromDt + "' and '" + toDt + "'" + brnQuery;
            rbiSossPojo = new RbiSossPojo();
            rbiSossPojo.setgNo(1);
            rbiSossPojo.setsGNo(9);
            rbiSossPojo.setSsGNo(0);
            rbiSossPojo.setSssGNo(0);
            rbiSossPojo.setSsssGNo(0);
            rbiSossPojo.setCountApplicable("");
            rbiSossPojo.setNpaClassification("");
            rbiSossPojo.setDescription("Tds Reserved but not deducted from Account");
            rbiSossPojo.setAmt(new BigDecimal("0"));
            BigDecimal unRecoverdTds = new BigDecimal("0");
            reportList = em.createNativeQuery(query).getResultList();
            if (!reportList.isEmpty()) {
                ele = (Vector) reportList.get(0);
                unRecoverdTds = new BigDecimal(ele.get(0).toString());
                rbiSossPojo.setAmt(unRecoverdTds.abs());
                rbiPojoTable.add(rbiSossPojo);
            } else {
                rbiPojoTable.add(rbiSossPojo);
            }

            return rbiPojoTable;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List<MinorAccountPojo> getMinorAccount(String tillDate, String acctCode, String branchCode) throws ApplicationException {
        List<MinorAccountPojo> returnList = new ArrayList<MinorAccountPojo>();
        double balance = 0d;
        List result = null;
        try {
            String acNatureQuery = "";
            if (acctCode.equalsIgnoreCase("ALL")) {
                acNatureQuery = "";
            } else {
                acNatureQuery = "and accttype='" + acctCode + "'";
            }
            if (branchCode.equalsIgnoreCase("0A")) {
//                result = em.createNativeQuery("select a.acno,a.custname,b.fathername,b.praddress,b.dob, crr.CustId from accountmaster a,customermaster b, customerid crr where "
//                        + "substring(a.acno,1,2) = b.brncode and  substring(a.acno,3,2) = b.actype and substring(a.acno,11,2) = b.agcode "
//                        + "and substring(a.acno,5,6) = b.custno " + acNatureQuery + " and a.acno IN (select acno from customerid "
//                        + "where custid in (select customerid from cbs_customer_master_detail where minorflag='Y')) and crr.Acno = a.acno"
//                        + " union "
//                        + "select a.acno,a.custname,b.fathername,b.praddress,b.dob, crr.CustId from td_accountmaster a,td_customermaster b, customerid crr where "
//                        + "substring(a.acno,1,2) = b.brncode and  substring(a.acno,3,2) = b.actype and substring(a.acno,11,2) = b.agcode "
//                        + "and substring(a.acno,5,6) = b.custno " + acNatureQuery + " and a.acno IN (select acno from customerid "
//                        + "where custid in (select customerid from cbs_customer_master_detail where minorflag='Y')) and crr.Acno = a.acno order by 5").getResultList();
                result = em.createNativeQuery("select c.acno,a.custfullname,concat(ifnull(a.fathername,''),' ',ifnull(a.FatherMiddleName,''),' ',ifnull(a.FatherLastName,'')) as FatherName, "
                        + "concat(a.PerAddressLine1,'',ifnull(a.peraddressline2,'')) Adress,a.DateOfBirth,b.custid "
                        + "from cbs_customer_master_detail a,customerid b,accountmaster c "
                        + "where a.minorflag='Y' and cast(a.customerid as unsigned) = b.custid and b.acno = c.acno " + acNatureQuery + ""
                        + "union "
                        + "select c.acno,a.custfullname,concat(ifnull(a.fathername,''),' ',ifnull(a.FatherMiddleName,''),' ',ifnull(a.FatherLastName,'')) as FatherName,"
                        + "concat(a.PerAddressLine1,'',ifnull(a.peraddressline2,'')) Adress,a.DateOfBirth,b.custid "
                        + "from cbs_customer_master_detail a,customerid b,td_accountmaster c "
                        + "where a.minorflag='Y' and cast(a.customerid as unsigned) = b.custid and b.acno = c.acno " + acNatureQuery + " "
                        + "order by 5").getResultList();
            } else {
//                result = em.createNativeQuery("select a.acno,a.custname,b.fathername,b.praddress,b.dob , crr.CustId from accountmaster a, customermaster b, customerid crr where "
//                        + "substring(a.acno,1,2) = b.brncode and  substring(a.acno,3,2) = b.actype and substring(a.acno,11,2) = b.agcode "
//                        + "and substring(a.acno,5,6) = b.custno and substring(a.acno,1,2) = '" + branchCode + "' " + acNatureQuery + " "
//                        + "and a.acno in (select acno from customerid where custid in (select customerid from cbs_customer_master_detail where minorflag='Y')) and crr.Acno = a.acno "
//                        + " union  "
//                        + "select a.acno,a.custname,b.fathername,b.praddress,b.dob , crr.CustId from td_accountmaster a,td_customermaster b, customerid crr where "
//                        + "substring(a.acno,1,2) = b.brncode and  substring(a.acno,3,2) = b.actype and substring(a.acno,11,2) = b.agcode "
//                        + "and substring(a.acno,5,6) = b.custno and substring(a.acno,1,2) = '" + branchCode + "' " + acNatureQuery + " "
//                        + "and a.acno in (select acno from customerid where custid in (select customerid from cbs_customer_master_detail where minorflag='Y')) and crr.Acno = a.acno order by 5").getResultList();
                result = em.createNativeQuery("select c.acno,a.custfullname,concat(ifnull(a.fathername,''),' ',ifnull(a.FatherMiddleName,''),' ',ifnull(a.FatherLastName,'')) as FatherName, "
                        + "concat(a.PerAddressLine1,'',ifnull(a.peraddressline2,'')) Adress,a.DateOfBirth,b.custid "
                        + "from cbs_customer_master_detail a,customerid b,accountmaster c "
                        + "where a.minorflag='Y' and cast(a.customerid as unsigned) = b.custid and b.acno = c.acno and curbrcode = '" + branchCode + "' " + acNatureQuery + ""
                        + "union "
                        + "select c.acno,a.custfullname,concat(ifnull(a.fathername,''),' ',ifnull(a.FatherMiddleName,''),' ',ifnull(a.FatherLastName,'')) as FatherName,"
                        + "concat(a.PerAddressLine1,'',ifnull(a.peraddressline2,'')) Adress,a.DateOfBirth,b.custid "
                        + "from cbs_customer_master_detail a,customerid b,td_accountmaster c "
                        + "where a.minorflag='Y' and cast(a.customerid as unsigned) = b.custid and b.acno = c.acno and curbrcode = '" + branchCode + "' " + acNatureQuery + " "
                        + "order by 5").getResultList();
            }

            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector resultSet = (Vector) result.get(i);
                    MinorAccountPojo pojo = new MinorAccountPojo();
                    pojo.setAcNo(resultSet.get(0).toString() != null ? resultSet.get(0).toString() : "");
                    pojo.setCustName(resultSet.get(1).toString() != null ? resultSet.get(1).toString() : "");
                    pojo.setFatherName(resultSet.get(2).toString() != null ? resultSet.get(2).toString() : "");
                    pojo.setAddress(resultSet.get(3).toString() != null ? resultSet.get(3).toString() : "");
                    pojo.setDob(dmyFormat.format(ymd_1.parse(resultSet.get(4).toString())));
                    pojo.setCustId(resultSet.get(5).toString() != null ? resultSet.get(5).toString() : "");
                    balance = bal.fnBalosForAccountAsOn(resultSet.get(0).toString(), tillDate);
                    pojo.setBalance(BigDecimal.valueOf(balance));
                    returnList.add(pojo);
                }
            }
        } catch (Exception e) {

            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List<InterestFdReportPojo> interestFdReport(String acNature, String acCode, String fAmt, String tAmt, String fDt, String tDt, String brCode, String reportType) throws ApplicationException {
        List<InterestFdReportPojo> InterestFdReportPojos = new ArrayList<InterestFdReportPojo>();
        try {
            List result = new ArrayList();
            List resultDtl = new ArrayList();
            String acctNature = "";
            if (acCode.equalsIgnoreCase("ALL")) {
                acctNature = "FD";
            } else {
                acctNature = common.getAcNatureByAcType(acCode);
            }
            Map<String, String> tdsMap = new HashMap<>();
            List finList = em.createNativeQuery("select MINDATE,MAXDATE from yearend where YEARENDFLAG = 'N' group by F_YEAR").getResultList();
            Vector finVector = (Vector) finList.get(0);
            String findt = finVector.get(0).toString();
            if (ymd.parse(fDt).getTime() < ymd.parse(findt).getTime()) {
                List tdsList = em.createNativeQuery("select customerid,'N'tdsFlag from tds_docdetail_his where date_format(submission_date,'%Y%m%d') between '" + fDt + "' and '" + tDt + "' group by customerid").getResultList();
                for (int i = 0; i < tdsList.size(); i++) {
                    Vector tdsVector = (Vector) tdsList.get(i);
                    tdsMap.put(tdsVector.get(0).toString(), tdsVector.get(1).toString());
                }
            }
            if (brCode.equalsIgnoreCase("0A")) {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (acCode.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),"
//                                + " ifnull(c.panno,''),c.praddress,sum(th.interest), d.custid from td_vouchmst r,td_accountmaster a,"
//                                + " td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno and a.acno = r.acno "
//                                + " and r.acno = th.acno and a.accttype in(select AcctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
//                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.voucherno = th.voucherno "
//                                + " and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
//                                + " having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno  ").getResultList();

                        result = em.createNativeQuery("select ga.gacno,ga.gcname,ga.gfname,ga.gdob,ga.gpan,ga.gpadd,ga.gint, ga.cid1,TDSFLAG from "
                                + " (select a.acno as gacno,ifnull(a.custname,'') as gcname,ifnull(c.fathername,'') as gfname,"
                                + " ifnull(c.dob,'19000101') as gdob,ifnull(c.panno,'') as gpan,c.praddress as gpadd,sum(th.interest) as gint, "
                                + " d.custid as cid1,a.TDSFLAG from td_vouchmst r,td_accountmaster a, td_customermaster c,customerid d,td_interesthistory th "
                                + " where a.acno = d.acno and a.acno = r.acno and r.acno = th.acno and a.accttype in(select AcctCode from "
                                + " accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                                + " and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode "
                                + " and a.accttype = c.actype and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername) ga,"
                                + " (select d.custid as cid2 from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and a.acno = r.acno and r.acno = th.acno and a.accttype in"
                                + " (select AcctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                                + " and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode "
                                + " and a.accttype = c.actype and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' "
                                + " order by d.custid) gb where ga.cid1 = gb.cid2 order by ga.cid1").getResultList();
                    } else {
//                        result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),"
//                                + " ifnull(c.panno,''),c.praddress,sum(th.interest), d.custid from td_vouchmst r,td_accountmaster a,"
//                                + " td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno and a.acno = r.acno "
//                                + " and r.acno = th.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
//                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.voucherno = th.voucherno "
//                                + " and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername "
//                                + " having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno  ").getResultList();

                        result = em.createNativeQuery("select ga.gacno,ga.gcname,ga.gfname,ga.gdob,ga.gpan,ga.gpadd,ga.gint,ga.cid1,TDSFLAG from "
                                + " (select a.acno as gacno,ifnull(a.custname,'') as gcname,ifnull(c.fathername,'') as gfname,"
                                + " ifnull(c.dob,'19000101') as gdob,ifnull(c.panno,'') as gpan,c.praddress as gpadd,sum(th.interest) as gint,"
                                + " d.custid as cid1,a.TDSFLAG from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,td_interesthistory th "
                                + " where a.acno = d.acno and a.acno = r.acno and r.acno = th.acno and a.accttype = '" + acCode + "' "
                                + " and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode "
                                + " and a.accttype = c.actype and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername) ga,"
                                + " (select d.custid as cid2 from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and a.acno = r.acno and r.acno = th.acno "
                                + " and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.voucherno = th.voucherno "
                                + " and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid "
                                + " having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid) "
                                + " gb where ga.cid1 = gb.cid2 order by ga.cid1").getResultList();
                    }

                }
            } else {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (acCode.equalsIgnoreCase("ALL")) {
//                        result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),"
//                                + " ifnull(c.panno,''),c.praddress,sum(th.interest), d.custid  from td_vouchmst r,td_accountmaster a,"
//                                + " td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno and  a.acno = r.acno "
//                                + " and r.acno = th.acno and a.accttype in(select AcctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
//                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and a.curbrcode ='" + brCode + "' "
//                                + " and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
//                                + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername having sum(th.interest) "
//                                + " between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno  ").getResultList();

                        result = em.createNativeQuery("select ga.gacno,ga.gcname,ga.gfname,ga.gdob,ga.gpan,ga.gpadd,ga.gint,ga.cid1,TDSFLAG from "
                                + " (select a.acno as gacno,ifnull(a.custname,'') as gcname,ifnull(c.fathername,'') as gfname,"
                                + " ifnull(c.dob,'19000101') as gdob,ifnull(c.panno,'') as gpan,c.praddress as gpadd,sum(th.interest) as gint, "
                                + " d.custid as cid1,a.TDSFLAG from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and  a.acno = r.acno and r.acno = th.acno "
                                + " and a.accttype in(select AcctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                                + " and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode "
                                + " and a.accttype = c.actype and a.curbrcode ='" + brCode + "' and r.voucherno = th.voucherno "
                                + " and th.dt between '" + fDt + "' and '" + tDt + "' group by d.custid,a.acno,a.custname,c.panno,"
                                + " c.praddress,c.dob,c.fathername) ga,"
                                + " (select d.custid as cid2  from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and a.acno = r.acno and r.acno = th.acno and a.accttype "
                                + " in(select AcctCode from accounttypemaster where acctNature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "')) "
                                + " and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype "
                                + " and a.curbrcode ='" + brCode + "' and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' order by d.custid) gb "
                                + " where ga.cid1 = gb.cid2 order by ga.cid1").getResultList();
                    } else {
//                        result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),"
//                                + " ifnull(c.panno,''),c.praddress,sum(th.interest), d.custid  from td_vouchmst r,td_accountmaster a,"
//                                + " td_customermaster c,customerid d,td_interesthistory th where a.acno = d.acno and  a.acno = r.acno "
//                                + " and r.acno = th.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
//                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and a.curbrcode ='" + brCode + "' "
//                                + " and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
//                                + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername having sum(th.interest) "
//                                + " between '" + fAmt + "' and '" + tAmt + "' order by d.custid,acno  ").getResultList();

                        result = em.createNativeQuery("select ga.gacno,ga.gcname,ga.gfname,ga.gdob,ga.gpan,ga.gpadd,ga.gint,ga.cid1,TDSFLAG from "
                                + " (select a.acno as gacno,ifnull(a.custname,'') as gcname,ifnull(c.fathername,'') as gfname,"
                                + " ifnull(c.dob,'19000101') gdob,ifnull(c.panno,'') as gpan,c.praddress as gpadd,sum(th.interest) as gint, "
                                + " d.custid as cid1,a.TDSFLAG from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and  a.acno = r.acno and r.acno = th.acno "
                                + " and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and a.curbrcode ='" + brCode + "' "
                                + " and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername) ga, "
                                + " (select d.custid as cid2 from td_vouchmst r,td_accountmaster a, td_customermaster c,customerid d,"
                                + " td_interesthistory th where a.acno = d.acno and  a.acno = r.acno and r.acno = th.acno "
                                + " and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
                                + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and a.curbrcode ='" + brCode + "' "
                                + " and r.voucherno = th.voucherno and th.dt between '" + fDt + "' and '" + tDt + "' "
                                + " group by d.custid having sum(th.interest) between '" + fAmt + "' and '" + tAmt + "' "
                                + " order by d.custid) gb where ga.cid1 = gb.cid2 order by ga.cid1 ").getResultList();
                    }
                }
            }

            int cnt = 0;
            String acN = "";
            String custI = "";
            BigDecimal custAmt = new BigDecimal("0");
            BigDecimal custPAmt = new BigDecimal("0");
            BigDecimal sumGrIntAmt = new BigDecimal("0");
            BigDecimal sumGrPrinAmt = new BigDecimal("0");
            BigDecimal sumGrTdsAmt = new BigDecimal("0");
            String tdsFlag = "";
            if (result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    Vector resultSet = (Vector) result.get(i);
                    String acno = resultSet.get(0).toString();
                    String custN = resultSet.get(1).toString();
                    String fName = resultSet.get(2).toString();
                    String dob = resultSet.get(3).toString();
                    if (dob.equals("") || dob.length() != 10) {
                        dob = "01/01/1900";
                    } else {
                        dob = dmyFormat.format(ymd.parse(dob));
                    }
//                    String dob = resultSet.get(3).toString().substring(6, 8) + "/" + resultSet.get(3).toString().substring(4, 6) + "/" + resultSet.get(3).toString().substring(0, 4);
                    String pNo = resultSet.get(4).toString();
                    String addr = resultSet.get(5).toString();
                    String custId = resultSet.get(7).toString();
                    tdsFlag = resultSet.get(8).toString();

//                    List tdsFlaList = em.createNativeQuery("select * from tds_docdetail_his where customerid = '" + custId + "' and date_format(submission_date,'%Y%m%d') between '" + fDt + "' and '" + tDt + "'").getResultList();
//                    if (tdsFlaList.isEmpty()) {
//                        tdsFlag = "Y";
//                    } else {
//                        tdsFlag = "N";
//                    }
                    if (ymd.parse(fDt).getTime() < ymd.parse(findt).getTime()) {
                        tdsFlag = tdsMap.get(custId) == null ? "Y" : tdsMap.get(custId);
                    }

                    if ((!custI.equalsIgnoreCase(custId)) && (cnt != 0)) {
                        InterestFdReportPojo intRep2 = new InterestFdReportPojo();
                        intRep2.setCustId("");
                        intRep2.setCustName("");
                        intRep2.setAcNo("");
                        intRep2.setFatherName("");
                        intRep2.setDob("");
                        intRep2.setPanNo("");
                        intRep2.setPerAddr("CUSTID WISE TOTAL ");
                        intRep2.setPrnAmt(custPAmt);
                        intRep2.setDepDate("");
                        intRep2.setIntAmt(custAmt);
                        intRep2.setIntPayDate("");
                        intRep2.setRecptNo("");
                        if (reportType.equalsIgnoreCase("ALL")) {
                            InterestFdReportPojos.add(intRep2);
                        } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                            InterestFdReportPojos.add(intRep2);
                        }
                        custAmt = new BigDecimal("0");
                        custPAmt = new BigDecimal("0");
                    }

                    resultDtl = em.createNativeQuery("select t.receiptno,t.voucherno,th.interest,date_format(th.dt,'%d/%m/%Y'),cast(t.prinamt as decimal (25,2)),date_format(t.fddt,'%d/%m/%Y') from td_vouchmst t,"
                            + " td_interesthistory th where t.acno = '" + acno + "' and t.acno = th.acno and t.voucherno = th.voucherno "
                            + " and th.dt between '" + fDt + "' and '" + tDt + "' order by 2,4").getResultList();

                    InterestFdReportPojo intRep1 = new InterestFdReportPojo();
                    BigDecimal sumAmt = new BigDecimal("0");
                    BigDecimal sumPrn = new BigDecimal("0");
                    String oldVch = "";
                    if (resultDtl.size() > 0) {
                        for (int j = 0; j < resultDtl.size(); j++) {
                            Vector record = (Vector) resultDtl.get(j);
                            InterestFdReportPojo intRep = new InterestFdReportPojo();
                            String newVch = record.get(1).toString();
//                            sumGrIntAmt = sumGrIntAmt.add(new BigDecimal(record.get(2).toString()));
//                            sumGrPrinAmt = sumGrPrinAmt.add(new BigDecimal(record.get(4).toString()));
                            if (reportType.equalsIgnoreCase("ALL")) {
                                sumGrIntAmt = sumGrIntAmt.add(new BigDecimal(record.get(2).toString()));
                                sumGrPrinAmt = sumGrPrinAmt.add(new BigDecimal(record.get(4).toString()));
                            } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                sumGrIntAmt = sumGrIntAmt.add(new BigDecimal(record.get(2).toString()));
                                sumGrPrinAmt = sumGrPrinAmt.add(new BigDecimal(record.get(4).toString()));
                            }

                            if (cnt == 0) {
                                intRep.setCustId(custId);
                                intRep.setCustName(custN);
                                intRep.setAcNo(acno);
                                intRep.setFatherName(fName != null ? fName : "");
                                if (dob.equalsIgnoreCase("01/01/1900")) {
                                    dob = "";
                                }
                                intRep.setDob(dob);
                                intRep.setPanNo(pNo != null ? pNo : "");
                                intRep.setPerAddr(addr != null ? addr : "");
                                intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                intRep.setDepDate(record.get(5).toString());
                                intRep.setIntAmt(new BigDecimal(record.get(2).toString()));
                                intRep.setIntPayDate(record.get(3).toString());
                                intRep.setRecptNo(record.get(1).toString());
                                cnt = 1;
                                acN = acno;
                                custI = custId;
                                oldVch = newVch;

                                if (reportType.equalsIgnoreCase("ALL")) {
                                    sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                    sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                    InterestFdReportPojos.add(intRep);
                                } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                    sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                    sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                    InterestFdReportPojos.add(intRep);
                                }

                            } else {
                                if (custI.equalsIgnoreCase(custId)) {
                                    if (acN.equalsIgnoreCase(acno)) {
                                        intRep.setCustId("");
                                        intRep.setCustName("");
                                        intRep.setAcNo("");
                                        intRep.setFatherName("");
                                        intRep.setDob("");
                                        intRep.setPanNo("");
                                        intRep.setPerAddr("");
                                        if (oldVch.equalsIgnoreCase(newVch)) {
                                            intRep.setPrnAmt(new BigDecimal("0"));
                                            sumPrn = sumPrn.add(new BigDecimal("0"));
                                            intRep.setRecptNo("");
                                            intRep.setDepDate("");
                                        } else {
                                            intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                            intRep.setRecptNo(record.get(1).toString());
                                            intRep.setDepDate(record.get(5).toString());
                                        }
                                        intRep.setIntAmt(new BigDecimal(record.get(2).toString()));
                                        intRep.setIntPayDate(record.get(3).toString());
                                        acN = acno;
                                        custI = custId;
                                        oldVch = newVch;
                                        if (reportType.equalsIgnoreCase("ALL")) {
                                            sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                            InterestFdReportPojos.add(intRep);
                                        } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                            sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                            InterestFdReportPojos.add(intRep);
                                        }
                                    } else {
                                        intRep.setCustId("");
                                        intRep.setCustName(custN);
                                        intRep.setAcNo(acno);
                                        intRep.setFatherName(fName != null ? fName : "");
                                        if (dob.equalsIgnoreCase("01/01/1900")) {
                                            dob = "";
                                        }
                                        intRep.setDob(dob);
                                        intRep.setPanNo(pNo != null ? pNo : "");
                                        intRep.setPerAddr(addr != null ? addr : "");
                                        intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                        intRep.setDepDate(record.get(5).toString());
                                        intRep.setIntAmt(new BigDecimal(record.get(2).toString()));
                                        intRep.setIntPayDate(record.get(3).toString());
                                        intRep.setRecptNo(record.get(1).toString());
                                        acN = acno;
                                        custI = custId;
                                        oldVch = newVch;
                                        sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                        if (reportType.equalsIgnoreCase("ALL")) {
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                            InterestFdReportPojos.add(intRep);
                                        } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                            InterestFdReportPojos.add(intRep);
                                        }
                                    }
                                } else {
                                    intRep.setCustId(custId);
                                    intRep.setCustName(custN);
                                    intRep.setAcNo(acno);
                                    intRep.setFatherName(fName != null ? fName : "");
                                    if (dob.equalsIgnoreCase("01/01/1900")) {
                                        dob = "";
                                    }
                                    intRep.setDob(dob);
                                    intRep.setPanNo(pNo != null ? pNo : "");
                                    intRep.setPerAddr(addr != null ? addr : "");
                                    intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                    intRep.setDepDate(record.get(5).toString());
                                    intRep.setIntAmt(new BigDecimal(record.get(2).toString()));
                                    intRep.setIntPayDate(record.get(3).toString());
                                    intRep.setRecptNo(record.get(1).toString());
                                    acN = acno;
                                    custI = custId;
                                    oldVch = newVch;

                                    if (reportType.equalsIgnoreCase("ALL")) {
                                        InterestFdReportPojos.add(intRep);
                                        sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                        sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                    } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                        InterestFdReportPojos.add(intRep);
                                        sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                        sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                    }
                                }
                            }
                        }
                        custAmt = custAmt.add(sumAmt);
                        custPAmt = custPAmt.add(sumPrn);
                    }
                    double tdsTotalAmt = 0d;
                    List tdsList = em.createNativeQuery("select ifnull(sum(TDS),0),date_format(dt,'%d/%m/%Y') from tds_reserve_history where acno = '" + acno + "' and Dt between '" + fDt + "' and '" + tDt + "' group by dt").getResultList();
                    if (!tdsList.isEmpty()) {
                        for (int k = 0; k < tdsList.size(); k++) {
                            Vector tdsVector = (Vector) tdsList.get(k);
                            double tdsAmt = Double.parseDouble(tdsVector.get(0).toString());
                            String tdsDt = tdsVector.get(1).toString();

                            InterestFdReportPojo tdsInpRep2 = new InterestFdReportPojo();
                            tdsInpRep2.setCustId("");
                            tdsInpRep2.setCustName("");
                            tdsInpRep2.setAcNo("");
                            tdsInpRep2.setFatherName("");
                            tdsInpRep2.setDob("");
                            tdsInpRep2.setPanNo("");
                            tdsInpRep2.setPerAddr("TDS ");
                            tdsInpRep2.setPrnAmt(new BigDecimal("0"));
                            tdsInpRep2.setDepDate("");
                            tdsInpRep2.setIntAmt(new BigDecimal("0"));
                            tdsInpRep2.setIntPayDate("");
                            tdsInpRep2.setRecptNo("");
                            tdsInpRep2.setTdsAmt(new BigDecimal(tdsAmt));
                            tdsInpRep2.setTdsDate(tdsDt);
                            if (reportType.equalsIgnoreCase("ALL")) {
                                tdsTotalAmt = tdsTotalAmt + tdsAmt;
                                sumGrTdsAmt = sumGrTdsAmt.add(new BigDecimal(tdsAmt));
                                InterestFdReportPojos.add(tdsInpRep2);
                            } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                                tdsTotalAmt = tdsTotalAmt + tdsAmt;
                                sumGrTdsAmt = sumGrTdsAmt.add(new BigDecimal(tdsAmt));
                                InterestFdReportPojos.add(tdsInpRep2);
                            }
                        }
                    }
                    intRep1.setCustId("");
                    intRep1.setCustName("");
                    intRep1.setAcNo("");
                    intRep1.setFatherName("");
                    intRep1.setDob("");
                    intRep1.setPanNo("");
                    intRep1.setPerAddr("A/C WISE TOTAL");
                    intRep1.setPrnAmt(sumPrn);
                    intRep1.setDepDate("");
                    intRep1.setIntAmt(sumAmt);
                    intRep1.setTdsAmt(new BigDecimal(tdsTotalAmt));
                    intRep1.setIntPayDate("");
                    intRep1.setRecptNo("");
                    if (reportType.equalsIgnoreCase("ALL")) {
                        InterestFdReportPojos.add(intRep1);
                    } else if (reportType.equalsIgnoreCase(tdsFlag)) {
                        InterestFdReportPojos.add(intRep1);
                    }
                }
                InterestFdReportPojo intRep3 = new InterestFdReportPojo();
                intRep3.setCustId("");
                intRep3.setCustName("");
                intRep3.setAcNo("");
                intRep3.setFatherName("");
                intRep3.setDob("");
                intRep3.setPanNo("");
                intRep3.setPerAddr("GRAND TOTAL");
                intRep3.setPrnAmt(sumGrPrinAmt);
                intRep3.setDepDate("");
                intRep3.setIntAmt(sumGrIntAmt);
                intRep3.setIntPayDate("");
                intRep3.setRecptNo("");
                intRep3.setTdsAmt(sumGrTdsAmt);
//                if (reportType.equalsIgnoreCase("ALL")) {
                InterestFdReportPojos.add(intRep3);
//                } else if (reportType.equalsIgnoreCase(tdsFlag)) {
//                    InterestFdReportPojos.add(intRep3);
//                }
            }
            return InterestFdReportPojos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public String getCodeFromCbsParameterInfo(String name) throws ApplicationException {
        String ctrParameter;
        List ctrExist = em.createNativeQuery("select code from cbs_parameterinfo where name='" + name + "'").getResultList();
        if (!ctrExist.isEmpty()) {
            Vector ctrVect = (Vector) ctrExist.get(0);
            ctrParameter = ctrVect.get(0).toString();
        } else {
            throw new ApplicationException("Data does not exit in table cbs_parameterinfo");
        }
        return ctrParameter;
    }

    public List<AmortizationReportPojo> getAmortizationReport(String asOnDate) throws ApplicationException {
        List<AmortizationReportPojo> amortList = new ArrayList<AmortizationReportPojo>();
        try {
            List amortResult = em.createNativeQuery("select im.secdesc,cast(im.facevalue as decimal(25,2)),cast(im.bookvalue as decimal(25,2)),date_format(im.settledt,'%d/%m/%Y'),date_format(im.matdt,'%d/%m/%Y'),iad.days,iad.amort_amt,date_format(iad.years,'%d/%m/%Y'),date_format(im.transdt,'%d/%m/%Y'),iad.ctrl_no,iad.status from investment_master im,investment_amortization_details iad where im.controlno = iad.ctrl_no and dt <'" + asOnDate + "' order by iad.ctrl_no,iad.years").getResultList();
            if (amortResult.isEmpty()) {
                throw new ApplicationException("Data does not exit table in investment_master and investment_amortization_details");
            }
            String pCtr = "";
            double balance = 0;
            for (int i = 0; i < amortResult.size(); i++) {
                Vector amortVect = (Vector) amortResult.get(i);
                AmortizationReportPojo pojo = new AmortizationReportPojo();

                String ctrNo = amortVect.get(9).toString() != null ? amortVect.get(9).toString() : "";
                String particular = amortVect.get(0).toString() != null ? amortVect.get(0).toString() : "";
                String faceValue = amortVect.get(1).toString();
                String bookValue = amortVect.get(2).toString();
                String dateOfPurchase = amortVect.get(3).toString();
                String dateOfMaturity = amortVect.get(4).toString();
                String noOfDays = amortVect.get(5).toString();
                double amortAmt = Double.parseDouble(amortVect.get(6).toString());
                double bookVal = Double.parseDouble(bookValue);
                String excessDate = amortVect.get(7).toString();
                String commencDt = amortVect.get(8).toString();
                double totalDays = CbsUtil.dayDiff(dmyFormat.parse(excessDate), dmyFormat.parse(dateOfMaturity));
                String stat = amortVect.get(10).toString() != null ? amortVect.get(10).toString() : "";
                if (!pCtr.equalsIgnoreCase(ctrNo)) {
                    balance = bookVal - amortAmt;
                    pojo.setAmortAson(amortAmt);
                    pojo.setBookVal(bookValue);
                    pojo.setDateOfComm(commencDt);
                    pojo.setDateOfMat(dateOfMaturity);
                    pojo.setDateOfPur(dateOfPurchase);
                    pojo.setExcessDate(excessDate);
                    pojo.setFaceVal(faceValue);
                    pojo.setNoOfdaysAson(noOfDays);
                    pojo.setParticular(particular);
                    pojo.setTotalDays(totalDays);
                    pojo.setStatus(stat);
                    pojo.setCtrNo(ctrNo);
                    pojo.setBalance(balance);
                    amortList.add(pojo);
                } else {
                    balance = balance - amortAmt;
                    pojo.setAmortAson(amortAmt);
                    pojo.setBookVal("");
                    pojo.setDateOfComm("");
                    pojo.setDateOfMat("");
                    pojo.setDateOfPur("");
                    pojo.setExcessDate(excessDate);
                    pojo.setFaceVal("");
                    pojo.setNoOfdaysAson(noOfDays);
                    pojo.setParticular("");
                    pojo.setTotalDays(totalDays);
                    pojo.setStatus(stat);
                    pojo.setCtrNo(ctrNo);
                    pojo.setBalance(balance);
                    amortList.add(pojo);
                }
                pCtr = ctrNo;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return amortList;

    }

//    public List<AtmTransactionStatusPojo> getAtmTransactionStatus(String type, String transaction, String brnCode, String frDate, String toDate, String atmId, String status) throws ApplicationException {
//        List<AtmTransactionStatusPojo> atmList = new ArrayList<AtmTransactionStatusPojo>();
//        List result1 = new ArrayList();
//        List result2 = new ArrayList();
//        List result3 = new ArrayList();
//        List allList = new ArrayList();
//        List allList1 = new ArrayList();
//        String nrev = "";
//        String nfrev = "";
//        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("0")) {
//            nrev = transaction.substring(0, 2);
//            nfrev = transaction.substring(0, 2);
//        }
//        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("1")) {
//            nrev = transaction.substring(0, 2);
//            nfrev = transaction.substring(0, 2);
//        }
//        String traceNo, acNo, location, cardNo, tranDate, businessDate, Status, orgTraceNo,rrn;
//        double amount;
//        String terminal_Id, branch = null;
//        try {
//            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00")) {
//                result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status ,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01")) {
//                result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Withdrawal")) {
//                result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code in(44,45,46,49) and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            }
//
//            if (!result1.isEmpty()) {
//                for (int i = 0; i < result1.size(); i++) {
//                    Vector rv = (Vector) result1.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    amount = Double.parseDouble(rv.get(2).toString());
//                    location = rv.get(3).toString();
//                    cardNo = rv.get(4).toString();
//                    tranDate = rv.get(5).toString();
//                    businessDate = rv.get(6).toString();
//                    Status = rv.get(7).toString();
//                    rrn= rv.get(8).toString();
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setAmount(BigDecimal.valueOf(amount));
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardNo);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setRrn(rrn);
//                    atmList.add(pojo);
//                }
//            }
//
//            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("30,31")) {
//                result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code in('30','31') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("35,36")) {
//                result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code in('35','36') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("32") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("37")) {
//                result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//            }
//
//            if (!result2.isEmpty()) {
//                for (int i = 0; i < result2.size(); i++) {
//                    Vector rv = (Vector) result2.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    location = rv.get(2).toString();
//                    cardNo = rv.get(3).toString();
//                    tranDate = rv.get(4).toString();
//                    businessDate = rv.get(5).toString();
//                    Status = rv.get(6).toString();
//                    rrn = rv.get(7).toString();
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardNo);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setRrn(rrn);
//
//                    atmList.add(pojo);
//
//                }
//            }
//            //if(nReversal.equals("N00") || nFReversal.equals("NF00 
//
//            if (atmId.equalsIgnoreCase("--Select--")) {
//                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
//                    result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Reversal")) {
//                    result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where processing_code in(44,45,46,49) and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                }
//            } else {
//                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
//                    result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                }
//            }
//            if (!result3.isEmpty()) {
//                for (int i = 0; i < result3.size(); i++) {
//                    Vector rv = (Vector) result3.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    amount = Double.parseDouble(rv.get(2).toString());
//                    location = rv.get(3).toString();
//                    cardNo = rv.get(4).toString();
//                    tranDate = rv.get(5).toString();
//                    businessDate = rv.get(6).toString();
//                    Status = rv.get(7).toString();
//                    orgTraceNo = rv.get(8).toString();
//                    rrn = rv.get(9).toString();
//                    
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setAmount(BigDecimal.valueOf(amount));
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardNo);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setOrgSysTraceNo(orgTraceNo);
//                    pojo.setRrn(rrn);
//                    atmList.add(pojo);
//                }
//            }
//
//            /*     
//             *  compare between atm_branch from table atm_master and Terminal_Id from table 
//             */
//            if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("0")) {
//                allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status from  atm_off_us_transaction_parameter  where processing_code = '19'  and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                // allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status from  atm_off_us_transaction_parameter  where processing_code = '19' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' and terminal_id = '" + atmId + "'").getResultList();
//                if (!allList.isEmpty()) {
//                    for (int m = 0; m < allList.size(); m++) {
//                        Vector mv = (Vector) allList.get(m);
//                        AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                        traceNo = mv.get(0).toString();
//                        terminal_Id = mv.get(1).toString();
//                        amount = Double.parseDouble(mv.get(2).toString());
//                        cardNo = mv.get(3).toString();
//                        tranDate = mv.get(4).toString();
//                        businessDate = mv.get(5).toString();
//                        Status = mv.get(6).toString();
//
//                        List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
//                        if (!alphaList.isEmpty()) {
//                            Vector vec1 = (Vector) alphaList.get(0);
//                            branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
//                        }
//                        pojo.setSysTraceNo(traceNo);
//                        pojo.setTerminalId(terminal_Id);
//                        pojo.setAmount(BigDecimal.valueOf(amount));
//                        pojo.setCardNo(cardNo);
//                        pojo.setTranDt(tranDate);
//                        pojo.setBusinessDt(businessDate);
//                        pojo.setStatus(Status);
//                        pojo.setBranch(branch);
//                        atmList.add(pojo);
//                    }
//                }
//
//            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("1")) {
//                allList1 = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number from  atm_off_us_transaction_parameter where processing_code = '19' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '1' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                //allList1 = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,tran_date,in_coming_date,in_process_status,original_system_trace_number from  atm_off_us_transaction_parameter where processing_code = '19' and terminal_id='" + atmId + "' and reversal_indicator = '1' and tran_date between '" + frDate + "' and '" + toDate + "'").getResultList();
//                if (!allList1.isEmpty()) {
//                    for (int n = 0; n < allList1.size(); n++) {
//                        Vector nv = (Vector) allList1.get(n);
//                        AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                        traceNo = nv.get(0).toString();
//                        terminal_Id = nv.get(1).toString();
//                        amount = Double.parseDouble(nv.get(2).toString());
//                        cardNo = nv.get(3).toString();
//                        tranDate = nv.get(4).toString();
//                        businessDate = nv.get(5).toString();
//                        Status = nv.get(6).toString();
//                        orgTraceNo = nv.get(7).toString();
//
//                        List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
//                        if (!alphaList.isEmpty()) {
//                            Vector vec1 = (Vector) alphaList.get(0);
//                            branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
//                        }
//                        pojo.setSysTraceNo(traceNo);
//                        pojo.setTerminalId(terminal_Id);
//                        pojo.setAmount(BigDecimal.valueOf(amount));
//                        pojo.setCardNo(cardNo);
//                        pojo.setTranDt(tranDate);
//                        pojo.setBusinessDt(businessDate);
//                        pojo.setStatus(Status);
//                        pojo.setOrgSysTraceNo(orgTraceNo);
//                        pojo.setBranch(branch);
//                        atmList.add(pojo);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e);
//        }
//        return atmList;
//    }
    ///-----                                      -final changes------------------------------>
//    public List<AtmTransactionStatusPojo> getAtmTransactionStatus(String type, String transaction, String brnCode, String frDate, String toDate, String atmId, String status, String mode, String AccountNo) throws ApplicationException {
//        List<AtmTransactionStatusPojo> atmList = new ArrayList<AtmTransactionStatusPojo>();
//        List result1 = new ArrayList();
//        List result2 = new ArrayList();
//        List result3 = new ArrayList();
//        List allList = new ArrayList();
//        List allList1 = new ArrayList();
//        String nrev = "";
//        String nfrev = "";
//        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("0")) {
//            nrev = transaction.substring(0, 2);
//            nfrev = transaction.substring(0, 2);
//        }
//        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("1")) {
//            nrev = transaction.substring(0, 2);
//            nfrev = transaction.substring(0, 2);
//        }
//        String traceNo, acNo, location, cardNo, tranDate, businessDate, Status, orgTraceNo, rrn;
//        double amount;
//        String cardMask = "";
//        String terminal_Id, branch = null;
//        try {
//            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00")) {
//                if (mode.equals("All")) {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status ,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status ,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "'  and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                }
//
//            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01")) {
//                if (mode.equals("All")) {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Withdrawal")) {
//                if (mode.equals("All")) {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code in(44,45,46) and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code in(44,45,46) and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//            } else if (type.equalsIgnoreCase("ECOM") && transaction.equalsIgnoreCase("49")) {
//                if (mode.equals("All")) {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='49' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='49' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//
//            }
//
//            if (!result1.isEmpty()) {
//                for (int i = 0; i < result1.size(); i++) {
//                    Vector rv = (Vector) result1.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    amount = Double.parseDouble(rv.get(2).toString());
//                    location = rv.get(3).toString();
//                    cardNo = rv.get(4).toString();
//                    if (!cardNo.equalsIgnoreCase("")) {
//                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
//                    }
//                    tranDate = rv.get(5).toString();
//                    businessDate = rv.get(6).toString();
//                    Status = rv.get(7).toString();
//                    rrn = rv.get(8).toString();
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setAmount(BigDecimal.valueOf(amount));
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardMask);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setRrn(rrn);
//                    atmList.add(pojo);
//                }
//            }
//
//            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("30")) {
//                if (mode.equals("All")) {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='30' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                } else {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='30' and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("36")) {
//                if (mode.equals("All")) {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='36' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where processing_code ='36' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//
//            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("32") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("37")) {
//                if (mode.equals("All")) {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("31") || type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("35")) {
//                if (mode.equals("All")) {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                } else {
//                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                }
//            }
//
//
//
//            if (!result2.isEmpty()) {
//                for (int i = 0; i < result2.size(); i++) {
//                    Vector rv = (Vector) result2.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    location = rv.get(2).toString();
//                    cardNo = rv.get(3).toString();
//                    if (!cardNo.equalsIgnoreCase("")) {
//                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
//                    }
//                    tranDate = rv.get(4).toString();
//                    businessDate = rv.get(5).toString();
//                    Status = rv.get(6).toString();
//                    rrn = rv.get(7).toString();
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardMask);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setRrn(rrn);
//
//                    atmList.add(pojo);
//
//                }
//            }
//            //if(nReversal.equals("N00") || nFReversal.equals("NF00 
//
//            if (atmId.equalsIgnoreCase("--Select--")) {
//                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
//                    if (mode.equals("All")) {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//                    } else {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//
//                    }
//                } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Reversal")) {
//                    if (mode.equals("All")) {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where processing_code in(44,45,46,49) and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                    } else {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where processing_code in(44,45,46,49) and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//
//                    }
//                }
//            } else {
//                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
//                    if (mode.equals("All")) {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                    } else {
//                        result3 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number,RRN from atm_reversal_transaction_parameter where (processing_code = '" + nrev + "' or processing_code = '" + nfrev + "') and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//
//
//                    }
//                }
//            }
//            if (!result3.isEmpty()) {
//                for (int i = 0; i < result3.size(); i++) {
//                    Vector rv = (Vector) result3.get(i);
//                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                    traceNo = rv.get(0).toString();
//                    acNo = rv.get(1).toString();
//                    amount = Double.parseDouble(rv.get(2).toString());
//                    location = rv.get(3).toString();
//                    cardNo = rv.get(4).toString();
//                    if (!cardNo.equalsIgnoreCase("")) {
//                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
//                    }
//                    tranDate = rv.get(5).toString();
//                    businessDate = rv.get(6).toString();
//                    Status = rv.get(7).toString();
//                    orgTraceNo = rv.get(8).toString();
//                    rrn = rv.get(9).toString();
//
//                    pojo.setSysTraceNo(traceNo);
//                    pojo.setAcNo(acNo);
//                    pojo.setAmount(BigDecimal.valueOf(amount));
//                    pojo.setLocation(location);
//                    pojo.setCardNo(cardMask);
//                    pojo.setTranDt(tranDate);
//                    pojo.setBusinessDt(businessDate);
//                    pojo.setStatus(Status);
//                    pojo.setOrgSysTraceNo(orgTraceNo);
//                    pojo.setRrn(rrn);
//                    atmList.add(pojo);
//                }
//            }
//
//            /*     
//             *  compare between atm_branch from table atm_master and Terminal_Id from table 
//             */
//            if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("0")) {
//                allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status from  atm_off_us_transaction_parameter  where processing_code = '19'  and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                // allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status from  atm_off_us_transaction_parameter  where processing_code = '19' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' and terminal_id = '" + atmId + "'").getResultList();
//                if (!allList.isEmpty()) {
//                    for (int m = 0; m < allList.size(); m++) {
//                        Vector mv = (Vector) allList.get(m);
//                        AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                        traceNo = mv.get(0).toString();
//                        terminal_Id = mv.get(1).toString();
//                        amount = Double.parseDouble(mv.get(2).toString());
//                        cardNo = mv.get(3).toString();
//                        if (!cardNo.equalsIgnoreCase("")) {
//                            cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
//                        }
//                        tranDate = mv.get(4).toString();
//                        businessDate = mv.get(5).toString();
//                        Status = mv.get(6).toString();
//
//                        List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
//                        if (!alphaList.isEmpty()) {
//                            Vector vec1 = (Vector) alphaList.get(0);
//                            branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
//                        }
//                        pojo.setSysTraceNo(traceNo);
//                        pojo.setTerminalId(terminal_Id);
//                        pojo.setAmount(BigDecimal.valueOf(amount));
//                        pojo.setCardNo(cardMask);
//                        pojo.setTranDt(tranDate);
//                        pojo.setBusinessDt(businessDate);
//                        pojo.setStatus(Status);
//                        pojo.setBranch(branch);
//                        atmList.add(pojo);
//                    }
//                }
//
//            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("1")) {
//                allList1 = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number from  atm_off_us_transaction_parameter where processing_code = '19' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '1' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
//                //allList1 = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,tran_date,in_coming_date,in_process_status,original_system_trace_number from  atm_off_us_transaction_parameter where processing_code = '19' and terminal_id='" + atmId + "' and reversal_indicator = '1' and tran_date between '" + frDate + "' and '" + toDate + "'").getResultList();
//                if (!allList1.isEmpty()) {
//                    for (int n = 0; n < allList1.size(); n++) {
//                        Vector nv = (Vector) allList1.get(n);
//                        AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
//                        traceNo = nv.get(0).toString();
//                        terminal_Id = nv.get(1).toString();
//                        amount = Double.parseDouble(nv.get(2).toString());
//                        cardNo = nv.get(3).toString();
//                        if (!cardNo.equalsIgnoreCase("")) {
//                            cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
//                        }
//                        tranDate = nv.get(4).toString();
//                        businessDate = nv.get(5).toString();
//                        Status = nv.get(6).toString();
//                        orgTraceNo = nv.get(7).toString();
//
//                        List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
//                        if (!alphaList.isEmpty()) {
//                            Vector vec1 = (Vector) alphaList.get(0);
//                            branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
//                        }
//                        pojo.setSysTraceNo(traceNo);
//                        pojo.setTerminalId(terminal_Id);
//                        pojo.setAmount(BigDecimal.valueOf(amount));
//                        pojo.setCardNo(cardMask);
//                        pojo.setTranDt(tranDate);
//                        pojo.setBusinessDt(businessDate);
//                        pojo.setStatus(Status);
//                        pojo.setOrgSysTraceNo(orgTraceNo);
//                        pojo.setBranch(branch);
//                        atmList.add(pojo);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApplicationException(e);
//        }
//        return atmList;
//    }
    public List<AtmTransactionStatusPojo> getAtmTransactionStatus(String type, String transaction, String brnCode, String frDate, String toDate, String atmId, String status, String mode, String AccountNo) throws ApplicationException {
        List<AtmTransactionStatusPojo> atmList = new ArrayList<AtmTransactionStatusPojo>();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        List result3 = new ArrayList();
        List allList = new ArrayList();
        List allList1 = new ArrayList();
        String nrev = "";
        String nfrev = "";
        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("0")) {
            nrev = transaction.substring(0, 2);
            nfrev = transaction.substring(0, 2);
        }
        if (!type.equalsIgnoreCase("NFS Acquirer") && !transaction.equalsIgnoreCase("1")) {
            nrev = transaction.substring(0, 2);
            nfrev = transaction.substring(0, 2);
        }
        String traceNo, acNo, location, cardNo, tranDate, businessDate, Status, orgTraceNo, rrn;
        double amount, reversal_amount;
        String cardMask = "";
        String terminal_Id, branch = null;
        try {
            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00")) {
                if (mode.equals("All")) {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status ,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status ,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "'  and terminal_id='" + atmId + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                }

            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01")) {
                if (mode.equals("All")) {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code = '" + transaction + "' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }
            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Withdrawal")) {
                if (mode.equals("All")) {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code in(44,45,46) and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code in(44,45,46) and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }
            } else if (type.equalsIgnoreCase("ECOM") && transaction.equalsIgnoreCase("49")) {
                if (mode.equals("All")) {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='49' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result1 = em.createNativeQuery("select system_trace_number,from_account_number ,amount,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='49' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }

            }
            if (!result1.isEmpty()) {
                for (int i = 0; i < result1.size(); i++) {
                    Vector rv = (Vector) result1.get(i);
                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
                    traceNo = rv.get(0).toString();
                    acNo = rv.get(1).toString();
                    amount = Double.parseDouble(rv.get(2).toString());
                    location = rv.get(3).toString().trim();
                    cardNo = rv.get(4).toString();
                    if (!cardNo.equalsIgnoreCase("")) {
                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
                    }

                    tranDate = rv.get(9).toString();
                    businessDate = rv.get(6).toString();
                    Status = rv.get(7).toString();
                    rrn = rv.get(8).toString();
                    pojo.setSysTraceNo(traceNo);
                    pojo.setAcNo(acNo);
                    pojo.setAmount(BigDecimal.valueOf(amount));
                    pojo.setLocation(location);
                    pojo.setCardNo(cardMask);
                    pojo.setTranDt(tranDate);
                    pojo.setBusinessDt(businessDate);
                    pojo.setStatus(Status);
                    pojo.setRrn(rrn);
                    atmList.add(pojo);
                }
            }

            if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("30")) {
                if (mode.equals("All")) {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='30' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                } else {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='30' and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }
            } else if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("36")) {
                if (mode.equals("All")) {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='36' and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where processing_code ='36' and in_process_flag = '" + status + "' and  from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }

            } else if (type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("32") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("37")) {
                if (mode.equals("All")) {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }
            } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("31") || type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("35")) {
                if (mode.equals("All")) {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                } else {
                    result2 = em.createNativeQuery("select system_trace_number,from_account_number ,terminal_location,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,rrn,date_format(tran_time,'%d/%m/%Y %H:%i:%S') from atm_normal_transaction_parameter where (processing_code = '" + transaction + "' or  processing_code = '" + transaction + "') and in_process_flag = '" + status + "' and from_account_number = '" + AccountNo + "' and  tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                }
            }
            if (!result2.isEmpty()) {
                for (int i = 0; i < result2.size(); i++) {
                    Vector rv = (Vector) result2.get(i);
                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
                    traceNo = rv.get(0).toString();
                    acNo = rv.get(1).toString();
                    location = rv.get(2).toString();
                    cardNo = rv.get(3).toString();
                    if (!cardNo.equalsIgnoreCase("")) {
                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
                    }
                    tranDate = rv.get(8).toString();
                    businessDate = rv.get(5).toString();
                    Status = rv.get(6).toString();
                    rrn = rv.get(7).toString();
                    pojo.setSysTraceNo(traceNo);
                    pojo.setAcNo(acNo);
                    pojo.setLocation(location);
                    pojo.setCardNo(cardMask);
                    pojo.setTranDt(tranDate);
                    pojo.setBusinessDt(businessDate);
                    pojo.setStatus(Status);
                    pojo.setRrn(rrn);

                    atmList.add(pojo);

                }
            }
            //if(nReversal.equals("N00") || nFReversal.equals("NF00 

            if (atmId.equalsIgnoreCase("--Select--")) {
                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
                    if (mode.equals("All")) {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal where (reversal.processing_code = '" + nrev + "' or reversal.processing_code = '" + nfrev + "') and reversal.in_process_flag = '" + status + "' and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();

                    } else {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal ,atm_normal_transaction_parameter normal where (reversal.processing_code = '" + nrev + "' or reversal.processing_code = '" + nfrev + "') and reversal.in_process_flag = '" + status + "' and normal.system_trace_number=reversal.original_system_trace_number and  reversal.from_account_number = '" + AccountNo + "' and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date order by reversal.tran_time").getResultList();


                    }
                } else if (type.equalsIgnoreCase("POS") && transaction.equalsIgnoreCase("Reversal")) {
                    if (mode.equals("All")) {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal  where reversal.processing_code in(44,45,46,49) and reversal.in_process_flag = '" + status + "' and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "'and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();
                    } else {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number ,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal where reversal.processing_code in(44,45,46,49) and reversal.in_process_flag = '" + status + "' and reversal.from_account_number = '" + AccountNo + "' and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();
                    }
                } else if (type.equalsIgnoreCase("ECOM") && transaction.equalsIgnoreCase("Reversal")) {
                    if (mode.equals("All")) {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal  where reversal.processing_code ='49' and reversal.in_process_flag = '" + status + "' and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date order by reversal.tran_time").getResultList();
                    } else {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number ,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal where reversal.processing_code ='49' and reversal.in_process_flag = '" + status + "' and reversal.from_account_number = '" + AccountNo + "' and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();
                    }
                }
            } else {
                if (type.equalsIgnoreCase("Normal") && transaction.equalsIgnoreCase("00N") || type.equalsIgnoreCase("NFS Issuer") && transaction.equalsIgnoreCase("01NF")) {
                    if (mode.equals("All")) {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal where (reversal.processing_code = '" + nrev + "' or reversal.processing_code = '" + nfrev + "') and reversal.in_process_flag = '" + status + "' and reversal.terminal_id='" + atmId + "'and normal.system_trace_number=reversal.original_system_trace_number and reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();
                    } else {
                        result3 = em.createNativeQuery("select reversal.system_trace_number,reversal.from_account_number ,reversal.amount,reversal.terminal_location,reversal.card_number,date_format(reversal.tran_date,'%d/%m/%Y'),date_format(reversal.in_coming_date,'%d/%m/%Y'),reversal.in_process_status,reversal.original_system_trace_number,reversal.rrn,date_format(reversal.tran_time,'%d/%m/%Y %H:%i:%S'),normal.amount,normal.system_trace_number,date_format(normal.tran_date,'%d/%m/%Y') from atm_reversal_transaction_parameter reversal,atm_normal_transaction_parameter normal where (reversal.processing_code = '" + nrev + "' or reversal.processing_code = '" + nfrev + "') and reversal.in_process_flag = '" + status + "' and reversal.terminal_id='" + atmId + "' and reversal.from_account_number = '" + AccountNo + "' and normal.system_trace_number=reversal.original_system_trace_number and  reversal.tran_date between '" + frDate + "' and '" + toDate + "' and normal.tran_date=reversal.tran_date  order by reversal.tran_time").getResultList();


                    }
                }
            }
            if (!result3.isEmpty()) {
                for (int i = 0; i < result3.size(); i++) {
                    Vector rv = (Vector) result3.get(i);
                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
                    traceNo = rv.get(0).toString();
                    acNo = rv.get(1).toString();
                    amount = Double.parseDouble(rv.get(2).toString());
                    location = rv.get(3).toString();
                    cardNo = rv.get(4).toString();
                    if (!cardNo.equalsIgnoreCase("")) {
                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
                    }
                    tranDate = rv.get(10).toString();
                    businessDate = rv.get(6).toString();
                    Status = rv.get(7).toString();
                    orgTraceNo = rv.get(8).toString();
                    rrn = rv.get(9).toString();
                    reversal_amount = Double.parseDouble(rv.get(11).toString()) - amount;
                    if (reversal_amount == 0.0) {
                        reversal_amount = Double.parseDouble(rv.get(11).toString());
                    }

                    pojo.setSysTraceNo(traceNo);
                    pojo.setAcNo(acNo);
                    pojo.setAmount(BigDecimal.valueOf(amount));
                    pojo.setLocation(location);
                    pojo.setCardNo(cardMask);
                    pojo.setTranDt(tranDate);
                    pojo.setBusinessDt(businessDate);
                    pojo.setStatus(Status);
                    pojo.setOrgSysTraceNo(orgTraceNo);
                    pojo.setRrn(rrn);
                    pojo.setReversalAmount(BigDecimal.valueOf(reversal_amount));
                    atmList.add(pojo);
                }
            }

            /*     
             *  compare between atm_branch from table atm_master and Terminal_Id from table 
             */
            if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("0")) {
                allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,date_format(tran_time,'%d/%m/%Y %H:%i:%S'),approval_code from"
                        + "  atm_off_us_transaction_parameter  where processing_code = '19'  and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();
                // allList = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status from  atm_off_us_transaction_parameter  where processing_code = '19' and terminal_id='" + atmId + "' and reversal_indicator = '0' and tran_date between '" + frDate + "' and '" + toDate + "' and terminal_id = '" + atmId + "'").getResultList();
                if (!allList.isEmpty()) {
                    for (int m = 0; m < allList.size(); m++) {
                        Vector mv = (Vector) allList.get(m);
                        AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
                        traceNo = mv.get(0).toString();
                        terminal_Id = mv.get(1).toString();
                        amount = Double.parseDouble(mv.get(2).toString());
                        cardNo = mv.get(3).toString();
                        if (!cardNo.equalsIgnoreCase("")) {
                            cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
                        }
                        tranDate = mv.get(7).toString();
                        businessDate = mv.get(5).toString();
                        Status = mv.get(6).toString();
                        List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
                        if (!alphaList.isEmpty()) {
                            Vector vec1 = (Vector) alphaList.get(0);
                            branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
                        }
                        rrn = mv.get(8).toString();
                        pojo.setSysTraceNo(traceNo);
                        pojo.setTerminalId(terminal_Id);
                        pojo.setAmount(BigDecimal.valueOf(amount));
                        pojo.setCardNo(cardMask);
                        pojo.setTranDt(tranDate);
                        pojo.setBusinessDt(businessDate);
                        pojo.setStatus(Status);
                        pojo.setBranch(branch);
                        pojo.setRrn(rrn);
                        atmList.add(pojo);
                    }
                }
            } else if (type.equalsIgnoreCase("NFS Acquirer") && transaction.equalsIgnoreCase("1")) {
                // allList1 = em.createNativeQuery("select system_trace_number,terminal_id,amount,card_number,date_format(tran_date,'%d/%m/%Y'),date_format(in_coming_date,'%d/%m/%Y'),in_process_status,original_system_trace_number from  atm_off_us_transaction_parameter where processing_code = '19' and in_process_flag = '" + status + "' and terminal_id='" + atmId + "' and reversal_indicator = '1' and tran_date between '" + frDate + "' and '" + toDate + "' order by tran_time").getResultList();

                if (status.equalsIgnoreCase("S")) {   //Success
                    allList1 = em.createNativeQuery("select atm_rev.system_trace_number as rev_trace_no,atm_rev.terminal_id "
                            + "as rev_terminal,atm_rev.amount as rev_amount,atm_rev.approval_code as rev_rrn,atm_rev.card_number "
                            + "as rev_card_no,date_format(atm_rev.tran_date,'%d/%m/%Y') as rev_tran_date,date_format("
                            + "atm_rev.tran_time,'%d/%m/%Y %H:%i:%S') as rev_tran_time,date_format(atm_rev.in_coming_date,'%d/%m/%Y') "
                            + "as rev_in_coming_date,atm_rev.in_process_status as rev_status,atm_org.system_trace_number as org_trace_no,atm_org.amount as org_amount "
                            + "from atm_off_us_transaction_parameter atm_rev, atm_off_us_transaction_parameter atm_org where "
                            + "atm_rev.processing_code=atm_org.processing_code and atm_rev.original_system_trace_number="
                            + "atm_org.system_trace_number and atm_rev.tran_date = atm_org.tran_date and atm_rev.in_process_flag='" + status + "' and "
                            + "atm_rev.terminal_id='" + atmId + "' and atm_rev.processing_code='19' and "
                            + "atm_rev.reversal_indicator=" + transaction + " and atm_rev.tran_date between '" + frDate + "' "
                            + "and '" + toDate + "' order by atm_rev.tran_time").getResultList();
                } else { //Un-Success,Un-Process
                    allList1 = em.createNativeQuery("select atm_rev.system_trace_number as rev_trace_no,atm_rev.terminal_id as "
                            + "rev_terminal,atm_rev.amount as rev_amount,atm_rev.approval_code as rev_rrn,atm_rev.card_number "
                            + "as rev_card_no,date_format(atm_rev.tran_date,'%d/%m/%Y') as rev_tran_date,date_format("
                            + "atm_rev.tran_time,'%d/%m/%Y %H:%i:%S') as rev_tran_time,date_format(atm_rev.in_coming_date,'%d/%m/%Y') as "
                            + "rev_in_coming_date,atm_rev.in_process_status as rev_status,ifnull(atm_org.system_trace_number,'') as org_trace_no,ifnull(atm_org.amount,0) "
                            + "as org_amount from atm_off_us_transaction_parameter atm_rev left join atm_off_us_transaction_parameter "
                            + "atm_org on atm_rev.processing_code=atm_org.processing_code and atm_rev.original_system_trace_number="
                            + "atm_org.system_trace_number where atm_rev.in_process_flag='" + status + "' and "
                            + "atm_rev.terminal_id='" + atmId + "' and atm_rev.processing_code='19' and "
                            + "atm_rev.reversal_indicator=" + transaction + " and atm_rev.tran_date between '" + frDate + "' "
                            + "and '" + toDate + "' order by atm_rev.tran_time").getResultList();
                }


                for (int n = 0; n < allList1.size(); n++) {
                    Vector nv = (Vector) allList1.get(n);
                    AtmTransactionStatusPojo pojo = new AtmTransactionStatusPojo();
                    traceNo = nv.get(0).toString();
                    terminal_Id = nv.get(1).toString();
                    amount = Double.parseDouble(nv.get(2).toString());
                    cardNo = nv.get(4).toString();
                    if (!cardNo.equalsIgnoreCase("")) {
                        cardMask = StringUtils.overlay(cardNo, StringUtils.repeat("X", cardNo.length() - 4), 0, cardNo.length() - 4);
                    }
                    tranDate = nv.get(5).toString();
                    businessDate = nv.get(7).toString();
                    Status = nv.get(8).toString();
                    orgTraceNo = nv.get(9).toString();

                    double orgAmount = Double.parseDouble(nv.get(10).toString());
                    if (orgAmount == 0.0) {
                        reversal_amount = 0.00;
                    } else {
                        reversal_amount = orgAmount - amount;
                        if (reversal_amount == 0.0) {
                            reversal_amount = orgAmount;
                        }
                    }

                    List alphaList = em.createNativeQuery("select alphacode from branchmaster where brncode = (select cast(atm_branch as unsigned)from atm_master where atm_id = '" + terminal_Id + "')").getResultList();
                    if (!alphaList.isEmpty()) {
                        Vector vec1 = (Vector) alphaList.get(0);
                        branch = vec1.get(0).toString() != null ? vec1.get(0).toString() : "";
                    }
                    rrn = nv.get(3).toString();
                    pojo.setSysTraceNo(traceNo);
                    pojo.setTerminalId(terminal_Id);
                    pojo.setAmount(BigDecimal.valueOf(amount));
                    pojo.setCardNo(cardMask);
                    pojo.setTranDt(tranDate);
                    pojo.setBusinessDt(businessDate);
                    pojo.setStatus(Status);
                    pojo.setOrgSysTraceNo(orgTraceNo);
                    pojo.setBranch(branch);
                    pojo.setReversalAmount(BigDecimal.valueOf(reversal_amount));
                    pojo.setRrn(rrn);
                    atmList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return atmList;
    }

    public List<SignatureReportPojo> getSignatureList(String sign, String acctCode, String branchCode) throws ApplicationException {
        List<SignatureReportPojo> signList = new ArrayList<SignatureReportPojo>();
        List result = new ArrayList();
        try {
            if (sign.equalsIgnoreCase("Signature Scanned")) {
                result = em.createNativeQuery("select newacno,enterby,authby from cbs_cust_image_detail where auth='Y' and substring(newacno,1,2)='" + branchCode + "' and substring(newacno,3,2)='" + acctCode + "' order by newacno").getResultList();
                if (result.isEmpty()) {
                    throw new ApplicationException("Data does not exit.");
                }
            } else {
                result = em.createNativeQuery("select newacno,enterby,authby from cbs_cust_image_detail_his where auth='y' and substring(newacno,1,2)='" + branchCode + "' and substring(newacno,3,2)='" + acctCode + "' order by newacno").getResultList();
                if (result.isEmpty()) {
                    throw new ApplicationException("Data does not exit.");
                }
            }
            for (int i = 0; i < result.size(); i++) {
                Vector sigv = (Vector) result.get(i);
                SignatureReportPojo pojo = new SignatureReportPojo();
                pojo.setAcno(sigv.get(0).toString());
                pojo.setEnterby(sigv.get(1).toString());
                pojo.setAuthby(sigv.get(2).toString());
                signList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return signList;
    }

    public List<DepositesReportPojo> getDepositesReport(String report, String actype, String frmDate, String toDate, String brcode, double amt, String reportFormatIn) throws ApplicationException {
        List<DepositesReportPojo> resultlist = new ArrayList<DepositesReportPojo>();
        String acNo = "", oldAcNo = "";
        String acNat = "", paymtDt = "";
        List result = new ArrayList();
        List acnoList = new ArrayList(), acTypeList = new ArrayList();
        try {
            if (actype.equalsIgnoreCase("ALL")) {
                acTypeList = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctnature in('" + CbsConstant.CURRENT_AC + "','" + CbsConstant.SAVING_AC + "','" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.RECURRING_AC + "','" + CbsConstant.DEPOSIT_SC + "')").getResultList();
            } else {
                acTypeList = em.createNativeQuery("select distinct(acctcode) from accounttypemaster where acctcode in('" + actype + "')").getResultList();
            }
            if (!acTypeList.isEmpty()) {
                for (int z = 0; z < acTypeList.size(); z++) {
                    Vector vtrAcType = (Vector) acTypeList.get(z);
                    actype = vtrAcType.get(0).toString();
                    List acctnature = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + actype + "'").getResultList();
                    Vector vtrn = (Vector) acctnature.get(0);
                    acNat = vtrn.get(0).toString();
                    if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from ca_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from ca_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from ca_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid ='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }
                    }

                    if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }

                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from loan_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from loan_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from loan_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }
                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from td_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from td_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from td_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }
                    }

                    if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from rdrecon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from td_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from rdrecon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }
                    }

                    if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        if (brcode.equalsIgnoreCase("0A")) {
                            acnoList = em.createNativeQuery("select acno from ddstransaction where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        } else {
//                            acnoList = em.createNativeQuery("select acno from td_recon where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
//                                    + "and substring(acno,1,2)='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                            acnoList = em.createNativeQuery("select acno from ddstransaction where dt between '" + frmDate + "' and '" + toDate + "' and trantype = 0 "
                                    + "and org_brnid='" + brcode + "' and substring(acno,3,2)='" + actype + "' group by acno having sum(cramt)>=" + amt + " order by acno").getResultList();
                        }
                    }

//                    if (acnoList.isEmpty()) {
//                        throw new ApplicationException("Data does not exit!");
//                    }
                    int print = 0;

                    for (int j = 0; j < acnoList.size(); j++) {
                        Vector vtr = (Vector) acnoList.get(j);
                        acNo = vtr.get(0).toString();
                        if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0) Interest_Amout from recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0 and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0) Interest_Amout from recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo
//                                        + "' and substring(a.acno,1,2)='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0) Interest_Amout from recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "') d where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid = '" + brcode + "' order by a.dt ").getResultList();
                            }
                        }

                        if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ca_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ca_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d where a.acno='" + acNo + "'"
                                        + "and substring(a.acno,3,2)='" + actype + "'and trantype =0 and substring(a.acno,1,2)= c.brncode and a.acno = b.acno and custno = "
                                        + "substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ca_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ca_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d  where a.acno='" + acNo
//                                        + "' and substring(a.acno,1,2)='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ca_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ca_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "')d  where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid = '" + brcode + "' order by a.dt ").getResultList();
                            }
                        }

                        if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from loan_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from loan_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo + "'"
                                        + "and substring(a.acno,3,2)='" + actype + "'and trantype =0 and substring(a.acno,1,2)= c.brncode and a.acno = b.acno and custno = "
                                        + "substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from loan_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from loan_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo
//                                        + "' and substring(a.acno,1,2)='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from loan_recon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from loan_recon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "') d where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid ='" + brcode + "' order by a.dt ").getResultList();
                            }
                        }

                        if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from td_recon a ,td_accountmaster b,td_customermaster c, (select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0)-ifnull(sum(dramt),0) Interest_Amout from td_recon a,td_accountmaster b where a.acno='" + acNo + "'  and a.dt between '" + frmDate + "' and '" + toDate + "' and (a.intflag='i' or a.trantype='8') and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo + "'"
                                        + "and substring(a.acno,3,2)='" + actype + "'and trantype =0 and substring(a.acno,1,2)= c.brncode and a.acno = b.acno and custno = "
                                        + "substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from td_recon a ,td_accountmaster b,td_customermaster c, (select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0)-ifnull(sum(dramt),0) Interest_Amout from td_recon a,td_accountmaster b where a.acno='" + acNo + "'  and a.dt between '" + frmDate + "' and '" + toDate + "' and (a.intflag='i' or a.trantype='8') and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "') d where a.acno='" + acNo
//                                        + "' and substring(a.acno,1,2)='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from td_recon a ,td_accountmaster b,td_customermaster c, (select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(cramt),0)-ifnull(sum(dramt),0) Interest_Amout from td_recon a,td_accountmaster b where a.acno='" + acNo + "'  and a.dt between '" + frmDate + "' and '" + toDate + "' and (a.intflag='i' or a.trantype='8') and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "') d where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid ='" + brcode + "' order by a.dt ").getResultList();
                            }

                        }

                        if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from rdrecon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from rdrecon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d where a.acno='" + acNo + "'"
                                        + "and substring(a.acno,3,2)='" + actype + "'and trantype =0 and substring(a.acno,1,2)= c.brncode and a.acno = b.acno and custno = "
                                        + "substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from rdrecon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from rdrecon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d  where a.acno='" + acNo
//                                        + "' and a.org_brnid ='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from rdrecon a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from rdrecon a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "')d  where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid ='" + brcode + "' order by a.dt ").getResultList();
                            }
                        }

                        if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            if (brcode.equalsIgnoreCase("0A")) {
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ddstransaction a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ddstransaction a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d where a.acno='" + acNo + "'"
                                        + "and substring(a.acno,3,2)='" + actype + "'and trantype =0 and substring(a.acno,1,2)= c.brncode and a.acno = b.acno and custno = "
                                        + "substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                            } else {
//                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
//                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ddstransaction a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ddstransaction a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and b.curbrcode = '" + acNo.substring(0, 2) + "')d  where a.acno='" + acNo
//                                        + "' and a.org_brnid='" + brcode + "'and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
//                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
//                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0  order by a.dt ").getResultList();
                                result = em.createNativeQuery("select date_format(a.dt,'%d/%m/%Y'),b.custname,cast(a.cramt as decimal(15,2)),ifnull(c.panno,''),c.praddress,"
                                        + "ifnull(c.fathername,''),c.dob,a.trantype,d.Payment_Date,d.Interest_Amout from ddstransaction a ,accountmaster b,customermaster c,(select ifnull(date_format(max(dt),'%d/%m/%Y'),'') Payment_Date,ifnull(sum(dramt),0) Interest_Amout from ddstransaction a,accountmaster b where a.acno='" + acNo + "' and a.dt between '" + frmDate + "' and '" + toDate + "' and a.trantype='8' and a.auth='y' and a.acno=b.acno and a.org_brnid = '" + brcode + "')d  where a.acno='" + acNo
                                        + "' and substring(a.acno,3,2)='" + actype + "'  and trantype = 0  and substring(a.acno,1,2)= c.brncode "
                                        + "and a.acno = b.acno and custno = substring(a.acno,5,6) and b.accttype=c.actype and c.agcode=substring(b.acno,11,2) "
                                        + "and a.dt  between '" + frmDate + "' and '" + toDate + "' and a.cramt>0 and a.org_brnid ='" + brcode + "' order by a.dt ").getResultList();
                            }
                        }

                        DepositesReportPojo pojo1 = new DepositesReportPojo();
                        BigDecimal sumAmt = new BigDecimal("0");
                        String dob = "";
                        String fName = "";
                        String pan = "";
                        String accNo = "";
                        String custName = "";
                        String CustAdd = "";
                        double intAmt = 0d;
                        if (result.size() > 0) {
                            for (int i = 0; i < result.size(); i++) {
                                Vector vtr1 = (Vector) result.get(i);
                                paymtDt = vtr1.get(8).toString();
                                intAmt = Double.parseDouble(vtr1.get(9).toString());

                                DepositesReportPojo pojo = new DepositesReportPojo();
                                if (report.equalsIgnoreCase("JRXML")) {

                                    pojo.setDepositeDt(vtr1.get(0).toString());
                                    pojo.setCustName(vtr1.get(1).toString());
                                    pojo.setAccNo(acNo);
                                    pojo.setCrAmt(new BigDecimal(vtr1.get(2).toString()));
                                    pojo.setPan(vtr1.get(3).toString());
                                    pojo.setCustAdd(vtr1.get(4).toString());
                                    pojo.setfName(vtr1.get(5).toString());
                                    if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                        pojo.setDob("");
                                    } else {
                                        pojo.setDob(vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4));
                                    }
                                    //                            if (vtr1.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                                    //                                pojo.setDob("");
                                    //                            }
                                    pojo.setIntAmt(new BigDecimal(intAmt));
                                    pojo.setPaymtDt(paymtDt);
                                    pojo.setFlag(reportFormatIn);
                                    resultlist.add(pojo);

                                } else if (report.equalsIgnoreCase("EXCEL")) {

                                    if (print == 0) {

                                        pojo.setDepositeDt(vtr1.get(0).toString());
                                        pojo.setCustName(vtr1.get(1).toString());
                                        pojo.setAccNo(acNo);
                                        pojo.setCrAmt(new BigDecimal(vtr1.get(2).toString()));
                                        pojo.setPan(vtr1.get(3).toString());
                                        pojo.setCustAdd(vtr1.get(4).toString());
                                        pojo.setfName(vtr1.get(5).toString());
                                        if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                            pojo.setDob("");
                                        } else {
                                            pojo.setDob(vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4));
                                        }
                                        //                                if (vtr1.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                                        //                                    pojo.setDob("");
                                        //                                }
                                        pojo.setIntAmt(new BigDecimal("0"));
                                        pojo.setPaymtDt("");
                                        pojo.setFlag(reportFormatIn);
                                        oldAcNo = acNo;
                                        print = 1;
                                        sumAmt = sumAmt.add(new BigDecimal(vtr1.get(2).toString()));
                                        if (reportFormatIn.equalsIgnoreCase("D")) {
                                            resultlist.add(pojo);
                                        } else {
                                            if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                                dob = "";
                                            } else {
                                                dob = vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4);
                                            }
                                            fName = vtr1.get(5).toString();
                                            pan = vtr1.get(3).toString();
                                            accNo = acNo;
                                            custName = vtr1.get(1).toString();
                                            CustAdd = vtr1.get(4).toString();
                                        }

                                    } else {

                                        if (oldAcNo.equalsIgnoreCase(acNo)) {

                                            pojo.setDepositeDt(vtr1.get(0).toString());
                                            pojo.setCustName(vtr1.get(1).toString());
                                            pojo.setAccNo(acNo);
                                            pojo.setCrAmt(new BigDecimal(vtr1.get(2).toString()));
                                            pojo.setPan(vtr1.get(3).toString());
                                            pojo.setCustAdd(vtr1.get(4).toString());
                                            pojo.setfName(vtr1.get(5).toString());
                                            if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                                pojo.setDob("");
                                            } else {
                                                pojo.setDob(vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4));
                                            }
                                            //                                    if (vtr1.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                                            //                                        pojo.setDob("");
                                            //                                    }
                                            pojo.setIntAmt(new BigDecimal("0.00"));
                                            pojo.setPaymtDt("");
                                            pojo.setFlag(reportFormatIn);
                                            oldAcNo = acNo;
                                            sumAmt = sumAmt.add(new BigDecimal(vtr1.get(2).toString()));
                                            if (reportFormatIn.equalsIgnoreCase("D")) {
                                                resultlist.add(pojo);
                                            } else {
                                                if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                                    dob = "";
                                                } else {
                                                    dob = vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4);
                                                }
                                                fName = vtr1.get(5).toString();
                                                pan = vtr1.get(3).toString();
                                                accNo = acNo;
                                                custName = vtr1.get(1).toString();
                                                CustAdd = vtr1.get(4).toString();
                                            }

                                        } else {

                                            pojo.setDepositeDt(vtr1.get(0).toString());
                                            pojo.setCustName(vtr1.get(1).toString());
                                            pojo.setAccNo(acNo);
                                            pojo.setCrAmt(new BigDecimal(vtr1.get(2).toString()));
                                            pojo.setPan(vtr1.get(3).toString());
                                            pojo.setCustAdd(vtr1.get(4).toString());
                                            pojo.setfName(vtr1.get(5).toString());
                                            if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                                pojo.setDob("");
                                            } else {
                                                pojo.setDob(vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4));
                                            }
                                            //                                    if (vtr1.get(6).toString().equalsIgnoreCase("01/01/1900")) {
                                            //                                        pojo.setDob("");
                                            //                                    }
                                            pojo.setIntAmt(new BigDecimal("0.00"));
                                            pojo.setPaymtDt("");
                                            pojo.setFlag(reportFormatIn);
                                            oldAcNo = acNo;
                                            sumAmt = sumAmt.add(new BigDecimal(vtr1.get(2).toString()));
                                            if (reportFormatIn.equalsIgnoreCase("D")) {
                                                resultlist.add(pojo);
                                            } else {
                                                if ((vtr1.get(6).toString().equalsIgnoreCase("")) || (vtr1.get(6).toString().equalsIgnoreCase("19000101"))) {
                                                    dob = "";
                                                } else {
                                                    dob = vtr1.get(6).toString().substring(6, 8) + "/" + vtr1.get(6).toString().substring(4, 6) + "/" + vtr1.get(6).toString().substring(0, 4);
                                                }
                                                fName = vtr1.get(5).toString();
                                                pan = vtr1.get(3).toString();
                                                accNo = acNo;
                                                custName = vtr1.get(1).toString();
                                                CustAdd = vtr1.get(4).toString();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (report.equalsIgnoreCase("EXCEL")) {
                            pojo1.setIntAmt(new BigDecimal(intAmt));
                            pojo1.setPaymtDt(paymtDt);
                            pojo1.setDob(dob);
                            pojo1.setfName(fName);
                            pojo1.setPan(pan);
                            pojo1.setAccNo(reportFormatIn.equalsIgnoreCase("D") ? "" : acNo);
                            pojo1.setCustName(custName);
                            pojo1.setDepositeDt("");
                            pojo1.setCustAdd(reportFormatIn.equalsIgnoreCase("D") ? "Total Deposit Amt of this A/c.No :" + acNo : CustAdd);
                            pojo1.setCrAmt(sumAmt);
                            resultlist.add(pojo1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return resultlist;
    }

    public List getInoperativeReport(String orgnBrCode, String Frdt, String Todt) throws ApplicationException {
        List<InoperativeReportPojo> iList = new ArrayList<InoperativeReportPojo>();
        List result, result1, result2, result3 = new ArrayList();
        try {
            if (orgnBrCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select distinct a.acno,a.dt,ac.custname from accountstatus a,accountmaster ac where a.spflag = 2 and a.dt between '" + Frdt + "' "
                        + " and '" + Todt + "' and a.acno = ac.acno order by a.acno,a.dt").getResultList();
            } else {
                result = em.createNativeQuery("select distinct a.acno,a.dt,ac.custname from accountstatus a, accountmaster ac where a.spflag = 2 and a.dt between '" + Frdt + "' "
                        + " and '" + Todt + "' and a.acno = ac.acno and ac.curbrcode ='" + orgnBrCode + "' order by a.acno,a.dt").getResultList();
            }

            for (int i = 0; i < result.size(); i++) {
                InoperativeReportPojo pojo = new InoperativeReportPojo();
                Vector iv = (Vector) result.get(i);
                String acNo = iv.get(0).toString();
                String tranDate = iv.get(1).toString();

                result3 = em.createNativeQuery("select count(*) from accountstatus where acno ='" + acNo + "' and dt ='" + tranDate + "' and spflag = 1").getResultList();
                Vector cntLst = (Vector) result3.get(0);
                int cnt = Integer.parseInt(cntLst.get(0).toString());

                if (cnt == 0) {
                    String custN = iv.get(2).toString();

                    String acctCode1 = ftsPosting.getAccountCode(acNo);
                    String acNat = ftsPosting.getAcNatureByCode(acctCode1);
                    String tablename = common.getTableName(acNat);

                    result1 = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from " + tablename + " where acno ='" + acNo + "' and dt <='" + tranDate + "'").getResultList();
                    Vector ivBal = (Vector) result1.get(0);
                    String Bal = ivBal.get(0).toString();

                    result2 = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from " + tablename + " where acno ='" + acNo + "' and dt <='" + Todt + "'").getResultList();
                    Vector ivBal1 = (Vector) result2.get(0);
                    String Bal1 = ivBal1.get(0).toString();

                    pojo.setAccNo(acNo);
                    pojo.setCustName(custN);
                    pojo.setDate(tranDate.substring(8, 10) + "/" + tranDate.substring(5, 7) + "/" + tranDate.substring(0, 4));
                    pojo.setCloseBal(new BigDecimal(Bal));
                    pojo.setCloseBalAsOn(new BigDecimal(Bal1));
                    iList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return iList;
    }

    public List<InterestFdDepositesRepPojo> interestFdDepositesReport(String acCode, String amt, String fDt, String tDt, String brCode) throws ApplicationException {
        List<InterestFdDepositesRepPojo> InterestFdDepositesRepPojos = new ArrayList<InterestFdDepositesRepPojo>();
        try {
            List result = new ArrayList();
            List resultDtl = new ArrayList();
            String acctNature = common.getAcNatureByAcType(acCode);
            if (brCode.equalsIgnoreCase("0A")) {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),ifnull(c.panno,''),"
                            + " c.praddress,sum(r.prinamt),d.custid from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d "
                            + " where a.acno = d.acno and a.acno = r.acno and r.acno = a.acno and a.accttype = '" + acCode + "'  and substring(a.acno,5,6)=c.custno "
                            + "and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.fddt between '" + fDt + "' and '" + tDt + "' "
                            + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername having sum(r.prinamt) > " + amt + " "
                            + " order by acno,d.custid").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),ifnull(c.panno,''),"
                            + " c.praddress,sum(r.cramt),d.custid,date_format(a.rdmatdate,'%d/%m/%Y'),a.intdeposit from rdrecon r,accountmaster a,customermaster c,customerid d "
                            + " where a.acno = d.acno and a.acno = r.acno and r.acno = a.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode "
                            + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.dt between '" + fDt + "' and '" + tDt + "' "
                            + " group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername,a.rdmatdate,a.intdeposit having sum(r.cramt) > " + amt + " "
                            + " order by acno,d.custid").getResultList();
                }
            } else {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),ifnull(c.panno,''),"
                            + " c.praddress,sum(r.prinamt),d.custid from td_vouchmst r,td_accountmaster a,td_customermaster c,customerid d "
                            + " where a.acno = d.acno and a.acno = r.acno and r.acno = a.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno "
                            + "and a.curbrcode=c.brncode and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.fddt between '" + fDt + "' and '" + tDt + "'"
                            + " and a.curbrcode = '" + brCode + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername having"
                            + " sum(r.prinamt) > " + amt + " order by acno,d.custid").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,ifnull(a.custname,''),ifnull(c.fathername,''),ifnull(c.dob,'19000101'),ifnull(c.panno,''),"
                            + " c.praddress,sum(r.cramt),d.custid,date_format(a.rdmatdate,'%d/%m%Y'),a.intdeposit from rdrecon r,accountmaster a,customermaster c,customerid d "
                            + " where a.acno = d.acno and a.acno = r.acno and r.acno = a.acno and a.accttype = '" + acCode + "' and substring(a.acno,5,6)=c.custno and a.curbrcode=c.brncode"
                            + " and substring(a.acno,11,2)= c.agcode and a.accttype = c.actype and r.dt between '" + fDt + "' and '" + tDt + "'"
                            + " and a.curbrcode = '" + brCode + "' group by d.custid,a.acno,a.custname,c.panno,c.praddress,c.dob,c.fathername,a.rdmatdate,a.intdeposit having"
                            + " sum(r.cramt) > " + amt + " order by acno,d.custid").getResultList();
                }
            }

            int cnt = 0;
            String acN = "";
            String custI = "";

            if (result.size() > 0) {
                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector resultSet = (Vector) result.get(i);
                        String acno = resultSet.get(0).toString();
                        String custN = resultSet.get(1).toString();
                        String fName = resultSet.get(2).toString();
                        String dob = resultSet.get(3).toString();
                        if (dob.equals("") || dob.length() != 10) {
                            dob = "01/01/1900";
                        } else {
                            dob = dmyFormat.format(ymd.parse(dob));
                        }
                        String pNo = resultSet.get(4).toString();
                        String addr = resultSet.get(5).toString();
                        String custId = resultSet.get(7).toString();

                        resultDtl = em.createNativeQuery("select t.receiptno,t.voucherno,t.cumuprinamt,date_format(t.matdt,date_format(),cast(t.prinamt as decimal (25,2)),"
                                + "date_format(t.fddt,date_format(),roi from td_vouchmst t where t.acno = '" + acno + "' and t.fddt between '" + fDt + "' and '"
                                + tDt + "' order by t.fddt").getResultList();

                        InterestFdDepositesRepPojo intRep1 = new InterestFdDepositesRepPojo();
                        BigDecimal sumAmt = new BigDecimal("0");
                        BigDecimal sumPrn = new BigDecimal("0");
                        if (resultDtl.size() > 0) {
                            for (int j = 0; j < resultDtl.size(); j++) {
                                Vector record = (Vector) resultDtl.get(j);
                                InterestFdDepositesRepPojo intRep = new InterestFdDepositesRepPojo();

                                if (cnt == 0) {
                                    intRep.setjCnt("");
                                    intRep.setCustName(custN);
                                    intRep.setpType("");
                                    intRep.setpStatus("");
                                    intRep.setPanNo(pNo != null ? pNo : "");
                                    if (dob.equalsIgnoreCase("01/01/1900")) {
                                        dob = "";
                                    }
                                    intRep.setDob(dob);
                                    intRep.setFatherName(fName != null ? fName : "");
                                    intRep.setPerAddr(addr != null ? addr : "");
                                    intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                    intRep.setpMode("");
                                    intRep.setDepDate(record.get(5).toString());
                                    intRep.setMatDate(record.get(3).toString());
                                    intRep.setRoi(record.get(6).toString());
                                    intRep.setAcNo(acno);
                                    intRep.setCustId(custId);
                                    cnt = 1;
                                    acN = acno;
                                    custI = custId;
                                    sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                    sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                    InterestFdDepositesRepPojos.add(intRep);
                                } else {
                                    if (custI.equalsIgnoreCase(custId)) {
                                        if (acN.equalsIgnoreCase(acno)) {
                                            intRep.setjCnt("");
                                            intRep.setCustName("");
                                            intRep.setpType("");
                                            intRep.setpStatus("");
                                            intRep.setPanNo("");
                                            intRep.setDob("");
                                            intRep.setFatherName("");
                                            intRep.setPerAddr("");
                                            intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                            intRep.setpMode("");
                                            intRep.setDepDate(record.get(5).toString());
                                            intRep.setMatDate(record.get(3).toString());
                                            intRep.setRoi(record.get(6).toString());
                                            intRep.setCustId("");
                                            intRep.setAcNo("");
                                            acN = acno;
                                            custI = custId;
                                            sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                            InterestFdDepositesRepPojos.add(intRep);
                                        } else {
                                            intRep.setjCnt("");
                                            intRep.setCustName(custN);
                                            intRep.setpType("");
                                            intRep.setpStatus("");
                                            intRep.setPanNo(pNo != null ? pNo : "");
                                            if (dob.equalsIgnoreCase("01/01/1900")) {
                                                dob = "";
                                            }
                                            intRep.setDob(dob);
                                            intRep.setFatherName(fName != null ? fName : "");
                                            intRep.setPerAddr(addr != null ? addr : "");
                                            intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                            intRep.setpMode("");
                                            intRep.setDepDate(record.get(5).toString());
                                            intRep.setMatDate(record.get(3).toString());
                                            intRep.setRoi(record.get(6).toString());
                                            intRep.setCustId("");
                                            intRep.setAcNo(acno);
                                            acN = acno;
                                            custI = custId;
                                            sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                            InterestFdDepositesRepPojos.add(intRep);
                                        }
                                    } else {
                                        intRep.setjCnt("");
                                        intRep.setCustName(custN);
                                        intRep.setpType("");
                                        intRep.setpStatus("");
                                        intRep.setPanNo(pNo != null ? pNo : "");
                                        if (dob.equalsIgnoreCase("01/01/1900")) {
                                            dob = "";
                                        }
                                        intRep.setDob(dob);
                                        intRep.setFatherName(fName != null ? fName : "");
                                        intRep.setPerAddr(addr != null ? addr : "");
                                        intRep.setPrnAmt(new BigDecimal(record.get(4).toString()));
                                        intRep.setpMode("");
                                        intRep.setDepDate(record.get(5).toString());
                                        intRep.setMatDate(record.get(3).toString());
                                        intRep.setRoi(record.get(6).toString());
                                        intRep.setCustId(custId);
                                        intRep.setAcNo(acno);
                                        acN = acno;
                                        custI = custId;
                                        sumAmt = sumAmt.add(new BigDecimal(record.get(2).toString()));
                                        sumPrn = sumPrn.add(new BigDecimal(record.get(4).toString()));
                                        InterestFdDepositesRepPojos.add(intRep);
                                    }
                                }
                            }
                        }
                        intRep1.setjCnt("");
                        intRep1.setCustName("");
                        intRep1.setpType("");
                        intRep1.setpStatus("");
                        intRep1.setPanNo("");
                        intRep1.setDob("");
                        intRep1.setFatherName("");
                        intRep1.setPerAddr("TOTAL");
                        intRep1.setPrnAmt(sumPrn);
                        intRep1.setpMode("");
                        intRep1.setDepDate("");
                        intRep1.setMatDate("");
                        intRep1.setRoi("");
                        intRep1.setCustId("");
                        intRep1.setAcNo("");
                        InterestFdDepositesRepPojos.add(intRep1);
                    }
                } else {
                    for (int i = 0; i < result.size(); i++) {
                        Vector resultSet = (Vector) result.get(i);
                        String acno = resultSet.get(0).toString();
                        String custN = resultSet.get(1).toString();
                        String fName = resultSet.get(2).toString();
                        String dob = resultSet.get(3).toString();
                        if (dob.equals("") || dob.length() != 10) {
                            dob = "01/01/1900";
                        } else {
                            dob = dmyFormat.format(ymd.parse(dob));
                        }
                        String pNo = resultSet.get(4).toString();
                        String addr = resultSet.get(5).toString();
                        String custId = resultSet.get(7).toString();
                        String mDt = resultSet.get(8).toString();
                        String roi = resultSet.get(9).toString();

                        resultDtl = em.createNativeQuery("select decimal(t.dt,'%d/%m/%Y'),cast(t.cramt as decimal (25,2)) from rdrecon t"
                                + " where t.acno = '" + acno + "' and t.dt between '" + fDt + "' and '" + tDt + "' and cast(t.cramt as decimal (25,2)) <>0 order by t.dt").getResultList();

                        InterestFdDepositesRepPojo intRep1 = new InterestFdDepositesRepPojo();
                        BigDecimal sumAmt = new BigDecimal("0");
                        BigDecimal sumPrn = new BigDecimal("0");
                        if (resultDtl.size() > 0) {
                            for (int j = 0; j < resultDtl.size(); j++) {
                                Vector record = (Vector) resultDtl.get(j);
                                InterestFdDepositesRepPojo intRep = new InterestFdDepositesRepPojo();

                                if (cnt == 0) {
                                    intRep.setjCnt("");
                                    intRep.setCustName(custN);
                                    intRep.setpType("");
                                    intRep.setpStatus("");
                                    intRep.setPanNo(pNo != null ? pNo : "");
                                    if (dob.equalsIgnoreCase("01/01/1900")) {
                                        dob = "";
                                    }
                                    intRep.setDob(dob);
                                    intRep.setFatherName(fName != null ? fName : "");
                                    intRep.setPerAddr(addr != null ? addr : "");
                                    intRep.setPrnAmt(new BigDecimal(record.get(1).toString()));
                                    intRep.setpMode("");
                                    intRep.setDepDate(record.get(0).toString());
                                    intRep.setMatDate(mDt);
                                    intRep.setRoi(roi);
                                    intRep.setAcNo(acno);
                                    intRep.setCustId(custId);
                                    cnt = 1;
                                    acN = acno;
                                    custI = custId;
                                    sumPrn = sumPrn.add(new BigDecimal(record.get(1).toString()));
                                    InterestFdDepositesRepPojos.add(intRep);
                                } else {
                                    if (custI.equalsIgnoreCase(custId)) {
                                        if (acN.equalsIgnoreCase(acno)) {
                                            intRep.setjCnt("");
                                            intRep.setCustName("");
                                            intRep.setpType("");
                                            intRep.setpStatus("");
                                            intRep.setPanNo("");
                                            intRep.setDob("");
                                            intRep.setFatherName("");
                                            intRep.setPerAddr("");
                                            intRep.setPrnAmt(new BigDecimal(record.get(1).toString()));
                                            intRep.setpMode("");
                                            intRep.setDepDate(record.get(0).toString());
                                            intRep.setMatDate("");
                                            intRep.setRoi("");
                                            intRep.setCustId("");
                                            intRep.setAcNo("");
                                            acN = acno;
                                            custI = custId;
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(1).toString()));
                                            InterestFdDepositesRepPojos.add(intRep);
                                        } else {
                                            intRep.setjCnt("");
                                            intRep.setCustName(custN);
                                            intRep.setpType("");
                                            intRep.setpStatus("");
                                            intRep.setPanNo(pNo != null ? pNo : "");
                                            if (dob.equalsIgnoreCase("01/01/1900")) {
                                                dob = "";
                                            }
                                            intRep.setDob(dob);
                                            intRep.setFatherName(fName != null ? fName : "");
                                            intRep.setPerAddr(addr != null ? addr : "");
                                            intRep.setPrnAmt(new BigDecimal(record.get(1).toString()));
                                            intRep.setpMode("");
                                            intRep.setDepDate(record.get(0).toString());
                                            intRep.setMatDate(mDt);
                                            intRep.setRoi(roi);
                                            intRep.setCustId("");
                                            intRep.setAcNo(acno);
                                            acN = acno;
                                            custI = custId;
                                            sumPrn = sumPrn.add(new BigDecimal(record.get(1).toString()));
                                            InterestFdDepositesRepPojos.add(intRep);
                                        }
                                    } else {
                                        intRep.setjCnt("");
                                        intRep.setCustName(custN);
                                        intRep.setpType("");
                                        intRep.setpStatus("");
                                        intRep.setPanNo(pNo != null ? pNo : "");
                                        if (dob.equalsIgnoreCase("01/01/1900")) {
                                            dob = "";
                                        }
                                        intRep.setDob(dob);
                                        intRep.setFatherName(fName != null ? fName : "");
                                        intRep.setPerAddr(addr != null ? addr : "");
                                        intRep.setPrnAmt(new BigDecimal(record.get(1).toString()));
                                        intRep.setpMode("");
                                        intRep.setDepDate(record.get(0).toString());
                                        intRep.setMatDate(mDt);
                                        intRep.setRoi(roi);
                                        intRep.setCustId(custId);
                                        intRep.setAcNo(acno);
                                        acN = acno;
                                        custI = custId;
                                        sumPrn = sumPrn.add(new BigDecimal(record.get(1).toString()));
                                        InterestFdDepositesRepPojos.add(intRep);
                                    }
                                }
                            }
                        }
                        intRep1.setjCnt("");
                        intRep1.setCustName("");
                        intRep1.setpType("");
                        intRep1.setpStatus("");
                        intRep1.setPanNo("");
                        intRep1.setDob("");
                        intRep1.setFatherName("");
                        intRep1.setPerAddr("TOTAL");
                        intRep1.setPrnAmt(sumPrn);
                        intRep1.setpMode("");
                        intRep1.setDepDate("");
                        intRep1.setMatDate("");
                        intRep1.setRoi("");
                        intRep1.setCustId("");
                        intRep1.setAcNo("");
                        InterestFdDepositesRepPojos.add(intRep1);
                    }
                }
            }
            return InterestFdDepositesRepPojos;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List<TdNewAndReNewPojo> getTdNewAndRenewData(String brCode, String status, String frDt, String toDt) throws ApplicationException {

        List<TdNewAndReNewPojo> dataList = new ArrayList<TdNewAndReNewPojo>();
        List result1 = new ArrayList();

        try {

            if (brCode.equalsIgnoreCase("0A")) {
                if (status.equalsIgnoreCase("New")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,a.fddt,a.matdt,substring(a.Acno, 3, 2) ,"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo from td_vouchmst a, td_customermaster cm, td_recon re where re.acno=a.acno "
                            + "and re.voucherno=a.voucherno and a.fddt>='" + frDt + "' and a.fddt<='" + toDt + "' and re.IntFlag is null and a.status='A'"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) "
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) and prevvoucherno is null order by a.acno").getResultList();
                } else if (status.equalsIgnoreCase("Renew")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,a.fddt,a.matdt,period ,a.prevVoucherNo,"
                            + "a.years,a.months,a.days,a.td_madedt,a.seqno,a.Receiptno from td_vouchmst a, td_customermaster cm where a.td_MadeDt between '" + frDt + "' "
                            + "and '" + toDt + "' and a.prevVoucherno is not null and cm.custno = substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) "
                            + "and cm.brncode = substring(a.acno,1,2) and prevvoucherno is not null order by a.acno").getResultList();
                } else if (status.equalsIgnoreCase("Mature")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,date_format(a.fddt,'%d/%m/%Y'),date_format(a.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,\n"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo,date_format(a.ClDt,'%d/%m/%Y'),DATEDIFF(a.ClDt,a.matdt) dayDiff,prevvoucherno\n"
                            + "from td_vouchmst a, td_customermaster cm, td_recon re \n"
                            + "where re.acno=a.acno \n"
                            + "and re.voucherno=a.voucherno and a.matdt between '" + frDt + "' and '" + toDt + "' and a.status='C'and a.matdt <= a.ClDt\n"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) \n"
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) \n"
                            + "and (prevvoucherno = 0 OR prevvoucherno = NULL) order by a.matdt,a.acno").getResultList();
                } else if (status.equalsIgnoreCase("Premature")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,date_format(a.fddt,'%d/%m/%Y'),date_format(a.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,\n"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo,date_format(a.ClDt,'%d/%m/%Y'),DATEDIFF(a.ClDt,a.matdt) dayDiff,prevvoucherno\n"
                            + "from td_vouchmst a, td_customermaster cm, td_recon re \n"
                            + "where re.acno=a.acno \n"
                            + "and re.voucherno=a.voucherno and a.ClDt between '" + frDt + "' and '" + toDt + "'and a.status='C'and a.matdt > a.ClDt\n"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) \n"
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) \n"
                            + "and (prevvoucherno = 0 OR prevvoucherno = NULL) order by a.matdt,a.acno").getResultList();
                }
            } else {
                if (status.equalsIgnoreCase("New")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,a.fddt,a.matdt,substring(a.Acno, 3, 2) ,"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo from td_vouchmst a, td_customermaster cm, td_recon re where re.acno=a.acno "
                            + "and re.voucherno=a.voucherno and a.fddt>='" + frDt + "' and a.fddt<='" + toDt + "' and re.IntFlag is null and a.status='A'"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) "
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) and cm.brncode ='" + brCode + "' and prevvoucherno is null").getResultList();
                } else if (status.equalsIgnoreCase("Renew")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,a.fddt,a.matdt,period ,a.prevVoucherNo,"
                            + "a.years,a.months,a.days,a.td_madedt,a.seqno,a.Receiptno from td_vouchmst a, td_customermaster cm where a.td_MadeDt between '" + frDt + "' "
                            + "and '" + toDt + "' and a.prevVoucherno is not null and cm.custno = substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) "
                            + "and cm.brncode = substring(a.acno,1,2) and cm.brncode ='" + brCode + "' and prevvoucherno is not null").getResultList();
                } else if (status.equalsIgnoreCase("Mature")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,date_format(a.fddt,'%d/%m/%Y'),date_format(a.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,\n"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo,date_format(a.ClDt,'%d/%m/%Y'),DATEDIFF(a.ClDt,a.matdt) dayDiff,prevvoucherno\n"
                            + "from td_vouchmst a, td_customermaster cm, td_recon re \n"
                            + "where re.acno=a.acno \n"
                            + "and re.voucherno=a.voucherno and a.matdt between '" + frDt + "' and '" + toDt + "' and a.status='C'and a.matdt <= a.ClDt\n"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) \n"
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) and cm.brncode ='" + brCode + "' \n"
                            + "and (prevvoucherno = 0 OR prevvoucherno = NULL) order by a.matdt,a.acno").getResultList();
                } else if (status.equalsIgnoreCase("Premature")) {
                    result1 = em.createNativeQuery("select distinct a.acno,a.voucherno,upper(cm.custname),a.prinamt,a.roi,a.intopt,date_format(a.fddt,'%d/%m/%Y'),date_format(a.matdt,'%d/%m/%Y'),substring(a.Acno, 3, 2) ,\n"
                            + "a.years,a.months,a.days,a.td_madedt,seqNo,a.receiptNo,date_format(a.ClDt,'%d/%m/%Y'),DATEDIFF(a.ClDt,a.matdt) dayDiff,prevvoucherno\n"
                            + "from td_vouchmst a, td_customermaster cm, td_recon re \n"
                            + "where re.acno=a.acno \n"
                            + "and re.voucherno=a.voucherno and a.matdt between '" + frDt + "' and '" + toDt + "'and a.status='C'and a.matdt > a.ClDt\n"
                            + "and cm.custno=substring(a.acno,5,6) and cm.actype=substring(a.acno,3,2) and cm.brncode = substring(a.acno,1,2) \n"
                            + "and substring(a.acno,1,2) = substring(re.acno,1,2) and cm.brncode ='" + brCode + "' \n"
                            + "and (prevvoucherno = 0 OR prevvoucherno = NULL) order by a.matdt,a.acno").getResultList();
                }
            }

            if (!result1.isEmpty()) {
                if (status.equalsIgnoreCase("New")) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele = (Vector) result1.get(i);
                        TdNewAndReNewPojo pojo = new TdNewAndReNewPojo();

                        String year = ele.get(9).toString().length() < 2 ? "0" + ele.get(9).toString() : ele.get(9).toString();
                        String month = ele.get(10).toString().length() < 2 ? "0" + ele.get(10).toString() : ele.get(10).toString();
                        String day = ele.get(11).toString().length() < 2 ? "0" + ele.get(11).toString() : ele.get(11).toString();

                        pojo.setAcNo(ele.get(0).toString());
                        pojo.setRtNo(ele.get(1).toString());
                        pojo.setCustName(ele.get(2).toString());
                        pojo.setPrinAmt(Double.parseDouble(ele.get(3).toString()));
                        pojo.setRoi(Double.parseDouble(ele.get(4).toString()));
                        pojo.setIntOpt(ele.get(5).toString());
                        pojo.setTdDtWef(ele.get(6).toString().substring(8, 10) + "/" + ele.get(6).toString().substring(5, 7) + "/" + ele.get(6).toString().substring(0, 4));
                        pojo.setMatDt(ele.get(7).toString().substring(8, 10) + "/" + ele.get(7).toString().substring(5, 7) + "/" + ele.get(7).toString().substring(0, 4));
                        pojo.setYear(ele.get(9).toString());
                        pojo.setMonth(ele.get(10).toString());
                        pojo.setDay(ele.get(11).toString());

                        pojo.setControlNo(ele.get(13).toString());
                        pojo.setReceiptNo(ele.get(14).toString());

                        pojo.setPeriod(year + "Y " + month + "M " + day + "D");
                        dataList.add(pojo);
                    }

                } else if (status.equalsIgnoreCase("Renew")) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele = (Vector) result1.get(i);
                        TdNewAndReNewPojo pojo = new TdNewAndReNewPojo();
                        double prePrinAmt = 0d;
                        String acNo = ele.get(0).toString();
                        String preVouchNo = ele.get(9).toString();

                        List prPrinAmtList = em.createNativeQuery("select distinct prinAmt From td_vouchmst where acno= '" + acNo + "' and voucherno ='" + preVouchNo + "'").getResultList();
                        if (!prPrinAmtList.isEmpty()) {
                            Vector ele1 = (Vector) prPrinAmtList.get(0);
                            prePrinAmt = Double.parseDouble(ele1.get(0).toString());
                        }
                        pojo.setAcNo(ele.get(0).toString());
                        pojo.setRtNo(ele.get(1).toString());
                        pojo.setCustName(ele.get(2).toString());
                        pojo.setPrinAmt(Double.parseDouble(ele.get(3).toString()));
                        pojo.setRoi(Double.parseDouble(ele.get(4).toString()));
                        pojo.setIntOpt(ele.get(5).toString());
                        pojo.setTdDtWef(ele.get(6).toString().substring(8, 10) + "/" + ele.get(6).toString().substring(5, 7) + "/" + ele.get(6).toString().substring(0, 4));
                        pojo.setPrevRtNo(ele.get(9).toString());
                        pojo.setPrevPrinAmt(prePrinAmt);
                        pojo.setControlNo(ele.get(14).toString());
                        pojo.setReceiptNo(ele.get(15).toString());
                        pojo.setPeriod(ele.get(8).toString());
                        dataList.add(pojo);
                    }
                } else if (status.equalsIgnoreCase("Mature")) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele = (Vector) result1.get(i);
                        TdNewAndReNewPojo pojo = new TdNewAndReNewPojo();

                        String year = ele.get(9).toString().length() < 2 ? "0" + ele.get(9).toString() : ele.get(9).toString();
                        String month = ele.get(10).toString().length() < 2 ? "0" + ele.get(10).toString() : ele.get(10).toString();
                        String day = ele.get(11).toString().length() < 2 ? "0" + ele.get(11).toString() : ele.get(11).toString();

                        pojo.setAcNo(ele.get(0).toString());
                        pojo.setRtNo(ele.get(1).toString());
                        pojo.setCustName(ele.get(2).toString());
                        pojo.setPrinAmt(Double.parseDouble(ele.get(3).toString()));
                        pojo.setRoi(Double.parseDouble(ele.get(4).toString()));
                        pojo.setIntOpt(ele.get(5).toString());
                        pojo.setTdDtWef(ele.get(6).toString());
                        pojo.setMatDt(ele.get(7).toString());
                        pojo.setYear(ele.get(9).toString());
                        pojo.setMonth(ele.get(10).toString());
                        pojo.setDay(ele.get(11).toString());
                        pojo.setControlNo(ele.get(13).toString());
                        pojo.setReceiptNo(ele.get(14).toString());
                        pojo.setPeriod(year + "Y " + month + "M " + day + "D");
                        pojo.setPaymentDt(ele.get(15).toString());
                        pojo.setPrintLabel("Y");
                        dataList.add(pojo);
                    }

                } else if (status.equalsIgnoreCase("Premature")) {
                    for (int i = 0; i < result1.size(); i++) {
                        Vector ele = (Vector) result1.get(i);
                        TdNewAndReNewPojo pojo = new TdNewAndReNewPojo();

                        String year = ele.get(9).toString().length() < 2 ? "0" + ele.get(9).toString() : ele.get(9).toString();
                        String month = ele.get(10).toString().length() < 2 ? "0" + ele.get(10).toString() : ele.get(10).toString();
                        String day = ele.get(11).toString().length() < 2 ? "0" + ele.get(11).toString() : ele.get(11).toString();

                        pojo.setAcNo(ele.get(0).toString());
                        pojo.setRtNo(ele.get(1).toString());
                        pojo.setCustName(ele.get(2).toString());
                        pojo.setPrinAmt(Double.parseDouble(ele.get(3).toString()));
                        pojo.setRoi(Double.parseDouble(ele.get(4).toString()));
                        pojo.setIntOpt(ele.get(5).toString());
                        pojo.setTdDtWef(ele.get(6).toString());
                        pojo.setMatDt(ele.get(7).toString());
                        pojo.setYear(ele.get(9).toString());
                        pojo.setMonth(ele.get(10).toString());
                        pojo.setDay(ele.get(11).toString());
                        pojo.setControlNo(ele.get(13).toString());
                        pojo.setReceiptNo(ele.get(14).toString());
                        pojo.setPeriod(year + "Y " + month + "M " + day + "D");
                        pojo.setPaymentDt(ele.get(15).toString());
                        pojo.setPrintLabel("Y");
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<TdPeriodReportPojo> getTdPeriodReportData(String brCode, String date) throws ApplicationException {
        List<TdPeriodReportPojo> dataList = new ArrayList<TdPeriodReportPojo>();
        List result = new ArrayList();
        try {
            if (brCode.equalsIgnoreCase("90")) {
                result = em.createNativeQuery("select from_days ,to_days ,count(ifnull(tdvouchmst.acno, 0)), cast(ifnull(sum(tdvouchmst.prinamt), 0)as decimal(25,2))"
                        + "from td_peroid_temp left join(select acno,voucherno,fddt,matdt,prinamt from td_vouchmst where (cldt is null or cldt>='" + date + "')"
                        + "and acno in (select acno from td_accountmaster where (closingdate is null or closingdate='' or closingdate>='" + date + "') "
                        + "and openingdt <='" + date + "')and auth='y' group by acno,voucherno,fddt,matdt,prinamt) as tdvouchmst on "
                        + "datediff(tdvouchmst.matdt,tdvouchmst.fddt) between td_peroid_temp.from_days and td_peroid_temp.to_days group by "
                        + "td_peroid_temp.from_days,td_peroid_temp.to_days order by td_peroid_temp.from_days asc").getResultList();
            } else {
                result = em.createNativeQuery("select from_days ,to_days ,count(ifnull(tdvouchmst.acno, 0)), cast(ifnull(sum(tdvouchmst.prinamt), 0)as decimal(25,2))"
                        + "from td_peroid_temp left join (select acno,voucherno,fddt,matdt,prinamt from td_vouchmst where (cldt is null or cldt>='" + date + "')"
                        + "and acno in (select acno from td_accountmaster where (closingdate is null or closingdate='' or closingdate>='" + date + "') "
                        + "and openingdt <='" + date + "' and curbrcode ='" + brCode + "')and auth='y' group by "
                        + "acno,voucherno,fddt,matdt,prinamt) as tdvouchmst on datediff(tdvouchmst.matdt,tdvouchmst.fddt) between td_peroid_temp.from_days "
                        + "and td_peroid_temp.to_days group by td_peroid_temp.from_days,td_peroid_temp.to_days order by td_peroid_temp.from_days asc").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    TdPeriodReportPojo pojo = new TdPeriodReportPojo();
                    pojo.setFromDays(ele.get(0).toString());
                    pojo.setToDays(ele.get(1).toString());
                    pojo.setReceiptNo(Double.parseDouble(ele.get(2).toString()));
                    pojo.setAmount(Double.parseDouble(ele.get(3).toString()));
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<CashDepositAggregateAnyOneDayPojo> getCashDepositAnyOneDay(int reportType, String acType, double amount, String frmDt, String toDt, String brnCode, String option) throws ApplicationException {
        List<CashDepositAggregateAnyOneDayPojo> dataList = new ArrayList<CashDepositAggregateAnyOneDayPojo>();
        List result1 = new ArrayList();
        List result2 = new ArrayList();
        int nodays;
        int dt = 0;
        String date = frmDt;
        try {
            String acNat = ftsPosting.getAcNatureByCode(acType);
            String tableName = common.getTableName(acNat);
            String brCode1 = "", brCode2 = "", brCode3 = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                brCode1 = "";
                brCode2 = "";
                brCode3 = "";
            } else {
                brCode1 = "and b.curbrcode='" + brnCode + "'";
                brCode2 = "and td.curbrcode='" + brnCode + "'";
                brCode3 = "and brncode='" + brnCode + "'";
            }
            nodays = (int) CbsUtil.dayDiff(ymdFormat.parse(frmDt), ymdFormat.parse(toDt));
            for (int i = 0; i <= nodays; i++) {
                dt = dt + 1;
                if (dt == 1) {
                    frmDt = date;
                } else {
                    frmDt = CbsUtil.dateAdd(frmDt, 1);
                }

                if (acNat.equalsIgnoreCase(CbsConstant.TD_AC) || acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    if (option.equalsIgnoreCase("Form 60")) {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from td_accountmaster  td, td_customermaster tc "
                                + "where td.acno in(select distinct a.acno from " + tableName + " a,td_accountmaster b where  a.dt = '" + frmDt + "' and a.acno=b.acno " + brCode1 + " and a.closeflag is null and a.trantype <> 27 "
                                + "group by a.acno having (sum(a.cramt)>=" + amount + "))and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " /*and "
                                + " (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='')*/ and concat(actype,custno)=substring(td.acno,3,8) and (panno ='form 60' or panno ='') "
                                + "" + brCode3 + " order by acno").getResultList();
                    } else if (option.equalsIgnoreCase("Pan No")) {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from td_accountmaster  td, td_customermaster tc "
                                + "where td.acno in(select distinct a.acno from " + tableName + " a,td_accountmaster b where  a.dt = '" + frmDt + "'  and a.acno=b.acno " + brCode1 + " and a.closeflag is null and a.trantype <> 27 "
                                + "group by a.acno having (sum(a.cramt)>=" + amount + "))and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " /*and "
                                + " (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') */and concat(actype,custno)=substring(td.acno,3,8) and (panno <>'form 60' and panno <>'') "
                                + "" + brCode3 + " order by acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from td_accountmaster  td, td_customermaster tc "
                                + "where td.acno in(select distinct a.acno from " + tableName + " a,td_accountmaster b where  a.dt = '" + frmDt + "' and a.acno=b.acno " + brCode1 + "  and a.closeflag is null and a.trantype <> 27 "
                                + "group by a.acno having (sum(a.cramt)>=" + amount + ")) and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " /*and "
                                + " (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') */and concat(actype,custno)=substring(td.acno,3,8)"
                                + "" + brCode3 + " order by acno").getResultList();
                    }
                } else {
                    if (option.equalsIgnoreCase("Form 60")) {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from accountmaster  td, customermaster tc "
                                + "where td.acno  in(select distinct a.acno from " + tableName + " a,accountmaster b where  a.dt = '" + frmDt + "' and  a.trantype = 0 and a.acno=b.acno "
                                + "" + brCode1 + " group by a.acno having (sum(a.cramt)>=" + amount + "))and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " "
                                + "and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and concat(actype,custno)=substring(td.acno,3,8)"
                                + "and (panno ='form 60' or panno ='')" + brCode3 + " order by acno").getResultList();
                    } else if (option.equalsIgnoreCase("Pan No")) {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from accountmaster  td, customermaster tc "
                                + "where td.acno  in(select distinct a.acno from " + tableName + " a,accountmaster b where  a.dt = '" + frmDt + "' and  a.trantype = 0 and a.acno=b.acno "
                                + "" + brCode1 + " group by a.acno having (sum(a.cramt)>=" + amount + "))and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " "
                                + "and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and concat(actype,custno)=substring(td.acno,3,8)"
                                + "and (panno <>'form 60' and panno <>'')" + brCode3 + " order by acno").getResultList();
                    } else {
                        result1 = em.createNativeQuery("select td.acno,tc.custname,tc.praddress ,ifnull(panno,''),ifnull(tc.fathername,''),td.JtName1,date_format(tc.dob,'%d/%m/%Y') from accountmaster  td, customermaster tc "
                                + "where td.acno  in(select distinct a.acno from " + tableName + " a,accountmaster b where  a.dt = '" + frmDt + "' and  a.trantype = 0 and a.acno=b.acno "
                                + "" + brCode1 + " group by a.acno having (sum(a.cramt)>=" + amount + "))and td.openingdt<= '" + toDt + "' and td.accttype='" + acType + "' " + brCode2 + " "
                                + "and  (td.closingdate>='" + toDt + "' or ifnull(td.closingdate,'')='' or td.closingdate='') and concat(actype,custno) = substring(td.acno,3,8)"
                                + "" + brCode3 + " order by acno").getResultList();
                    }
                }
                List roiMatDtList = new ArrayList();
                String roi = "";
                String matDt = "";
                if (!result1.isEmpty()) {
                    for (int j = 0; j < result1.size(); j++) {
                        Vector ele = (Vector) result1.get(j);
                        if (acNat.equalsIgnoreCase(CbsConstant.TD_AC) || acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            roiMatDtList = em.createNativeQuery("select cast(ROI as decimal(14,2)),date_format(MatDt,'%d/%m/%Y') from td_vouchmst where acno = '" + ele.get(0).toString() + "' and Status = 'A'").getResultList();
                            result2 = em.createNativeQuery("select cast(ifnull(sum(cramt),0)as decimal(14,2)),trantype from " + tableName + " where acno='" + ele.get(0).toString() + "' and  dt ='" + frmDt + "' and trantype in(0,1,2) and closeflag is null and cramt > 0 group by trantype order by trantype").getResultList();
                        } else {
                            result2 = em.createNativeQuery("select cast(ifnull(sum(cramt),0)as decimal(14,2)) from " + tableName + " where acno='" + ele.get(0).toString() + "' and  dt ='" + frmDt + "' and trantype =0 and cramt > 0").getResultList();
                        }
                        Vector ele3 = null;
                        if (!roiMatDtList.isEmpty()) {
                            ele3 = (Vector) roiMatDtList.get(0);
                            roi = ele3.get(0).toString();
                            matDt = ele3.get(1).toString();
                        }

                        List acPerStatusList = em.createNativeQuery("select accstatus,Description from accountmaster a, codebook c where acno = '" + ele.get(0).toString() + "' "
                                + "and c.groupcode = '3' and a.accstatus = c.code"
                                + " union "
                                + "select accstatus,Description from td_accountmaster a, codebook c where acno = '" + ele.get(0).toString() + "' "
                                + "and c.groupcode = '3' and a.accstatus = c.code").getResultList();
                        Vector acStatusVect1 = (Vector) acPerStatusList.get(0);
                        String PerStatus = acStatusVect1.get(0).toString();
                        String acStatusDesc = acStatusVect1.get(1).toString();

                        if (!result2.isEmpty()) {
                            for (int k = 0; k < result2.size(); k++) {
                                Vector ele2 = (Vector) result2.get(k);
                                CashDepositAggregateAnyOneDayPojo pojo = new CashDepositAggregateAnyOneDayPojo();
                                if (acNat.equalsIgnoreCase(CbsConstant.TD_AC) || acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                    if (ele2.get(1).toString().equalsIgnoreCase("0")) {
                                        pojo.setTranType("Cash");
                                    } else if (ele2.get(1).toString().equalsIgnoreCase("1")) {
                                        pojo.setTranType("Clearing");
                                    } else if (ele2.get(1).toString().equalsIgnoreCase("2")) {
                                        pojo.setTranType("Transfer");
                                    }
                                    pojo.setRoi(roi);
                                    pojo.setMatDate(matDt);
                                }
                                pojo.setAcNo(ele.get(0).toString());
                                pojo.setCustName(ele.get(1).toString());
                                pojo.setAddress(ele.get(2).toString());
                                if (ele.get(3).toString().equalsIgnoreCase("")) {
                                    pojo.setPanNo("Form 60");
                                } else {
                                    pojo.setPanNo(ele.get(3).toString());
                                }
                                pojo.setFatherName(ele.get(4).toString());
                                pojo.setJointName(ele.get(5).toString());
                                pojo.setCustDob(ele.get(6).toString());
                                pojo.setAcType("");
                                pojo.setAcStatus(acStatusDesc);
                                pojo.setAmount(Double.parseDouble(ele2.get(0).toString()));
                                pojo.setTxnDate(frmDt.substring(6, 8) + "/" + frmDt.substring(4, 6) + "/" + frmDt.substring(0, 4));
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<TdReceiptIntDetailPojo> getTdReceiptIntData(String allAcNo, String custId, String frDt, String toDt, String receiptNo, String brCode, String acType, String tdsOption, String summaryOpt, String recFrDt, String recToDt, String actCatgory, double fromAmt, double toAmt) throws ApplicationException {
        List<TdReceiptIntDetailPojo> dataList = new ArrayList<TdReceiptIntDetailPojo>();
        //List result = new ArrayList();
        try {
            String brQuery = "", acTypeQuery = "", receiptQuery = "", acQuery = "", tdsDtQuery = "", tdsRecDtQuery = "", actCatgTable = "", actCatgQuery = "";
            if (actCatgory.equalsIgnoreCase("A")) {
                actCatgTable = "";
                actCatgQuery = "";
            } else {
                actCatgTable = ",cbs_customer_master_detail d";
                actCatgQuery = "and b.custid = cast(d.customerid as unsigned) and d.CustEntityType = '" + actCatgory + "'";
            }
            if (brCode.equalsIgnoreCase("0A") || brCode.equalsIgnoreCase("90")) {
                brQuery = " ";
            } else {
                brQuery = " and Substring(a.acno,1,2) = '" + brCode + "' ";
            }

            if (tdsOption.equals("RS")) {
                tdsDtQuery = " and a.Dt between '" + frDt + "' and '" + toDt + "' ";
            } else if (tdsOption.equals("RC")) {
                tdsDtQuery = " and a.dt  between '" + frDt + "' and '" + toDt + "' ";
                tdsRecDtQuery = " and a.tdsrecovereddt between '" + recFrDt + "' and '" + recToDt + "'  ";
            } else if (tdsOption.equals("UR")) {
                tdsDtQuery = " and (a.recovered='NR' or a.tdsrecovereddt > '" + recToDt + "' ) and a.Dt  between '" + frDt + "' and '" + toDt + "' ";
            }
            if (allAcNo.equalsIgnoreCase("A")) {
                String q1 = "", q2 = "", column = "", groupVch = "";
                if (common.getAcctNature(acType).equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    q1 = " customermaster ";
                    q2 = " dds_interesthistory ";
                    column = " distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno ";
                    groupVch = "";
                } else if (common.getAcctNature(acType).equalsIgnoreCase(CbsConstant.MS_AC)) {
                    q1 = " td_customermaster ";
                    q2 = " td_interesthistory ";
                    column = " distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno ";
                    groupVch = " , a.voucherno ";
                } else if (common.getAcctNature(acType).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                    q1 = " td_customermaster ";
                    q2 = " td_interesthistory ";
                    column = " distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno ";
                    groupVch = " , a.voucherno ";
                } else if (common.getAcctNature(acType).equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    q1 = " customermaster ";
                    q2 = " rd_interesthistory ";
                    column = " distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno ";
                    groupVch = "";
                }
                receiptQuery = "";
                acTypeQuery = " and substring(a.acno,3,2) = '" + acType + "' ";
                acQuery = "select distinct " + column + " from " + q2 + " a,customerid b, " + q1 + " c "
                        + " where a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                        + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + acTypeQuery + " group by a.acno " + groupVch + ", a.dt"
                        + " union "
                        + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b, " + q1 + "  c  "
                        + " where  a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                        + tdsDtQuery + tdsRecDtQuery + brQuery + acTypeQuery + "  group by a.acno, a.voucherno, a.recovered, a.tdsRecoveredDt   "
                        + " order by 1,2,3,4,6 ";
            } else if (allAcNo.equalsIgnoreCase("I")) {
//                acQuery = "select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno from td_interesthistory a,customerid b, td_customermaster c "
//                        + " where a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
//                        + " and a.dt between '" + frDt + "' and '" + toDt + "' and b.custid='" + custId + "' " + brQuery + " group by a.acno, a.voucherno, a.dt "
//                        + " union"
//                        + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from dds_interesthistory a,customerid b, customermaster c "
//                        + " where a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
//                        + " and a.dt between '" + frDt + "' and '" + toDt + "' and b.custid='" + custId + "' " + brQuery + " group by a.acno, a.dt "
//                        + " union "
//                        + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from rd_interesthistory a,customerid b, customermaster c "
//                        + " where a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
//                        + " and a.dt between '" + frDt + "' and '" + toDt + "' and b.custid='" + custId + "' " + brQuery + " group by a.acno, a.dt "
//                        + " union "
//                        + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b,td_customermaster c  "
//                        + " where  a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
//                        + tdsDtQuery + tdsRecDtQuery + " and b.custid='" + custId + "' " + brQuery + " group by a.acno, a.voucherno, a.recovered, a.tdsRecoveredDt  "
//                        + " union "
//                        + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b, customermaster c "
//                        + " where  a.acno = b.acno and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
//                        + tdsDtQuery + tdsRecDtQuery + "  and b.custid='" + custId + "'  " + brQuery + " group by a.acno, a.voucherno, a.recovered, a.tdsRecoveredDt  "
//                        + " order by 1,2,3,4,6 ";
                acQuery = "select distinct ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt,ee.recoveredVch,ee.custname,ee.panno, ee.minorFlag, ee.minorCustId, "
                        + " ee.majorPropCustID, ee.gurPanCard, ee.proCustID, amt.majorPropCustID as majId, amt.totalInt from "
                        + " (select distinct dd.custid,dd.acno, dd.voucherno,dd.dt,ifnull(sum(dd.interest),0) as interest,dd.flag,dd.recovered,dd.tdsRecoveredDt,dd.recoveredVch,dd.custname,dd.panno, dd.minorFlag "
                        + " ,gur.minorCustId,  cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.custid)) as UNSIGNED) as majorPropCustID, gur.gurPanCard as gurPanCard, pro.custid as proCustID "
                        + " from "
                        + " (select distinct aa.custid,aa.acno, aa.voucherno,aa.dt,ifnull(sum(aa.interest),0) as interest,aa.flag,aa.recovered,aa.tdsRecoveredDt,aa.recoveredVch,aa.custname,aa.panno, bb.minorFlag from "
                        + " (select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno from td_interesthistory a,customerid b, td_customermaster c " + actCatgTable
                        + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                        + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + " group by a.acno, a.voucherno, a.dt "
                        + " union all "
                        + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from dds_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                        + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                        + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                        + " union all "
                        + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from rd_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                        + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                        + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                        + " union all "
                        + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b,td_customermaster c " + actCatgTable + " "
                        + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                        + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered , a.tdsRecoveredDt  "
                        + " union all "
                        + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b, customermaster c " + actCatgTable + " "
                        + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                        + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered,  a.tdsRecoveredDt   "
                        + " order by 1,2,3,4,6 ) aa, cbs_customer_master_detail bb where aa.custid = cast(bb.customerid as unsigned)  "
                        + " group by aa.custid,aa.acno, aa.voucherno,aa.dt,aa.interest,aa.flag,aa.recovered,aa.tdsRecoveredDt   order by 1,2,3,4,6 ) dd "
                        + " left join "
                        + " (select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                        + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                        + " union "
                        + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard "
                        + " from cbs_cust_minorinfo_his mn, "
                        + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                        + " (select cast(CustomerId as UNSIGNED) as minorCustId, mAX(TXNID) as txnId from cbs_cust_minorinfo_his where "
                        + " ifnull(date_format(LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "' "
                        + " and (MajorityDate is null or MajorityDate = '' or date_format(MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                        + " group by cast(CustomerId as UNSIGNED) "
                        + " ) aa  "
                        + " where mn.guardiancode = cm.customerid "
                        + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                        + " and mn.customerid = cc.customerid "
                        + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                        + " "
                        + " left join  "
                        + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                        + " union all  "
                        + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                        + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                        + " group by dd.custid,dd.acno, dd.voucherno,dd.dt,dd.interest,dd.flag,dd.recovered,dd.tdsRecoveredDt ) ee"
                        + " right join "
                        + " ( select cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.customerid)) as UNSIGNED) as majorPropCustID, "
                        + " sum(dd.interest) as totalInt "
                        + " from "
                        + " (select cast(aa.customerid as unsigned) as customerid, bb.custid, bb.acno, bb.voucherno, sum(bb.interest) as interest, ifnull(aa.PAN_GIRNumber,'') as pan, aa.minorflag  from "
                        + " (select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch from td_interesthistory a,customerid b   where a.acno = b.acno   and a.dt between '" + frDt + "' and '" + toDt + "' group by a.acno, a.voucherno, a.dt  "
                        + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from dds_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'    group by a.acno, a.dt  "
                        + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from rd_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'  group by a.acno, a.dt  "
                        + " ) bb, cbs_customer_master_detail aa where bb.custid = cast(aa.customerid as unsigned) group by bb.custid,  bb.acno, bb.voucherno order by 1,2,3 ) dd "
                        + " left join "
                        + " (select  cast(mn.CustomerId as unsigned) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED) as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                        + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                        + " union "
                        + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag "
                        + " from cbs_cust_minorinfo_his mn, "
                        + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                        + " (select cast(CustomerId as UNSIGNED) as minorCustId, mAX(TXNID) as txnId from cbs_cust_minorinfo_his where "
                        + " ifnull(date_format(LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "' "
                        + " and (MajorityDate is null or MajorityDate = '' or date_format(MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                        + " group by cast(CustomerId as UNSIGNED) "
                        + " ) aa  "
                        + " where mn.guardiancode = cm.customerid "
                        + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                        + " and mn.customerid = cc.customerid "
                        + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                        + " left join  "
                        + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                        + " union all  "
                        + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                        + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                        + " group by majorPropCustID having sum(dd.interest) between " + fromAmt + " and " + toAmt + " order by cast(majorPropCustID as UNSIGNED), "
                        + " dd.customerid ) amt "
                        + " on ee.majorPropCustID = amt.majorPropCustID "
                        + " where (ee.majorPropCustID = " + custId + " or ee.custid = " + custId + ") and ifnull(ee.custid,'') <>''"
                        + " group by ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt "
                        + " order by ee.majorPropCustID,ee.custid,ee.acno, cast(ee.voucherno as decimal),ee.dt,ee.flag";
            } else if (allAcNo.equalsIgnoreCase("C")) {
                if (tdsOption.equals("UR")) {
                    acQuery = "select distinct ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt,ee.recoveredVch,ee.custname,ee.panno, ee.minorFlag, ee.minorCustId, "
                            + " ee.majorPropCustID, ee.gurPanCard, ee.proCustID, amt.majorPropCustID as majId, amt.totalInt from "
                            + " (select distinct dd.custid,dd.acno, dd.voucherno,dd.dt,ifnull(sum(dd.interest),0) as interest,dd.flag,dd.recovered,dd.tdsRecoveredDt,dd.recoveredVch,dd.custname,dd.panno, dd.minorFlag "
                            + " ,gur.minorCustId,  cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.custid)) as UNSIGNED) as majorPropCustID, gur.gurPanCard as gurPanCard, pro.custid as proCustID "
                            + " from "
                            + " (select  aa.custid,aa.acno, aa.voucherno,aa.dt,aa.interest,aa.flag,aa.recovered,aa.tdsRecoveredDt,aa.recoveredVch,aa.custname,aa.panno, bb.minorFlag   from  "
                            + "(select  aa.custid,aa.acno, aa.voucherno,aa.dt,aa.interest,aa.flag,aa.recovered,aa.tdsRecoveredDt,aa.recoveredVch,aa.custname,aa.panno  from  "
                            + "(select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno from td_interesthistory a,customerid b, td_customermaster c " + actCatgTable + " "
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + " group by a.acno, a.voucherno, a.dt "
                            + " union all "
                            + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from dds_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                            + " union all "
                            + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from rd_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                            + " union all "
                            + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b,td_customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered , a.dt, a.tdsRecoveredDt  "
                            + " union all "
                            + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b, customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered, a.dt, a.tdsRecoveredDt   "
                            + " order by 1,2,3,4,6) aa right join  "
                            + " (select b.custid,a.acno, a.voucherno from tds_reserve_history a, customerid b,td_customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered , a.dt, a.tdsRecoveredDt  "
                            + " union all "
                            + " select b.custid,a.acno, a.voucherno from tds_reserve_history a, customerid b, customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered, a.dt, a.tdsRecoveredDt   "
                            + ") bb  on aa.custid = bb.custid and aa.acno = bb.acno and aa.voucherno = bb.voucherno group by aa.custid,aa.acno, aa.voucherno,aa.dt,aa.flag order by 1,2,3,4,6) aa , cbs_customer_master_detail bb where aa.custid = cast(bb.customerid as unsigned)  "
                            + " group by aa.custid,aa.acno, aa.voucherno,aa.dt,aa.interest,aa.flag,aa.recovered,aa.tdsRecoveredDt   order by 1,2,3,4,6 ) dd "
                            + " left join "
                            + " (select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                            + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                            + " union "
                            + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard "
                            + " from cbs_cust_minorinfo_his mn, "
                            + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                            + " (select cast(CustomerId as UNSIGNED) as minorCustId, mAX(TXNID) as txnId from cbs_cust_minorinfo_his where "
                            + " ifnull(date_format(LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "' "
                            + " and (MajorityDate is null or MajorityDate = '' or date_format(MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                            + " group by cast(CustomerId as UNSIGNED) "
                            + " ) aa  "
                            + " where mn.guardiancode = cm.customerid "
                            + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                            + " and mn.customerid = cc.customerid "
                            + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                            + " "
                            + " left join  "
                            + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                            + " union all  "
                            + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                            + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                            + " group by dd.custid,dd.acno, dd.voucherno,dd.dt,dd.interest,dd.flag,dd.recovered,dd.tdsRecoveredDt ) ee"
                            + " right join "
                            + " ( select cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.customerid)) as UNSIGNED) as majorPropCustID, "
                            + " sum(dd.interest) as totalInt "
                            + " from "
                            + " (select cast(aa.customerid as unsigned) as customerid, bb.custid, bb.acno, bb.voucherno, sum(bb.interest) as interest, ifnull(aa.PAN_GIRNumber,'') as pan, aa.minorflag  from "
                            + " (select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch from td_interesthistory a,customerid b   where a.acno = b.acno   and a.dt between '" + frDt + "' and '" + toDt + "' group by a.acno, a.voucherno, a.dt  "
                            + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from dds_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'    group by a.acno, a.dt  "
                            + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from rd_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'  group by a.acno, a.dt  "
                            + " ) bb, cbs_customer_master_detail aa where bb.custid = cast(aa.customerid as unsigned) group by bb.custid,  bb.acno, bb.voucherno order by 1,2,3 ) dd "
                            + " left join "
                            + " (select  cast(mn.CustomerId as unsigned) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED) as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag "
                            + " from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                            + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                            + " union "
                            + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag "
                            + " from cbs_cust_minorinfo_his mn, "
                            + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                            + " (select cast(CustomerId as UNSIGNED) as minorCustId, mAX(TXNID) as txnId from cbs_cust_minorinfo_his where "
                            + " ifnull(date_format(LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "' "
                            + " and (MajorityDate is null or MajorityDate = '' or date_format(MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                            + " group by cast(CustomerId as UNSIGNED) "
                            + " ) aa  "
                            + " where mn.guardiancode = cm.customerid "
                            + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                            + " and mn.customerid = cc.customerid "
                            + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                            + " left join  "
                            + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                            + " union all  "
                            + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                            + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                            + " group by majorPropCustID having sum(dd.interest) between " + fromAmt + " and " + toAmt + " order by cast(majorPropCustID as UNSIGNED), "
                            + " dd.customerid ) amt "
                            + " on ee.majorPropCustID = amt.majorPropCustID  where ifnull(ee.custid,'') <>''  and (ee.recovered='NR' or ee.tdsrecovereddt > '" + toDt + "' ) "
                            + " group by ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt "
                            + " order by ee.majorPropCustID,ee.custid,ee.acno, cast(ee.voucherno as decimal),ee.dt,ee.flag";
                } else {
                    acQuery = "select distinct ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt,ee.recoveredVch,ee.custname,ee.panno, ee.minorFlag, ee.minorCustId, "
                            + " ee.majorPropCustID, ee.gurPanCard, ee.proCustID, amt.majorPropCustID as majId, amt.totalInt from "
                            + " (select distinct dd.custid,dd.acno, dd.voucherno,dd.dt,ifnull(sum(dd.interest),0) as interest,dd.flag,dd.recovered,dd.tdsRecoveredDt,dd.recoveredVch,dd.custname,dd.panno, dd.minorFlag "
                            + " ,gur.minorCustId,  cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.custid)) as UNSIGNED) as majorPropCustID, gur.gurPanCard as gurPanCard, pro.custid as proCustID "
                            + " from "
                            + " (select distinct aa.custid,aa.acno, aa.voucherno,aa.dt,ifnull(sum(aa.interest),0) as interest,aa.flag,aa.recovered,aa.tdsRecoveredDt,aa.recoveredVch,aa.custname,aa.panno, bb.minorFlag from "
                            + " (select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch,c.custname,c.panno from td_interesthistory a,customerid b, td_customermaster c " + actCatgTable
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + " group by a.acno, a.voucherno, a.dt "
                            + " union all "
                            + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from dds_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                            + " union all "
                            + " select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','',c.custname,c.panno from rd_interesthistory a,customerid b, customermaster c " + actCatgTable + " "
                            + " where a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                            + " and a.dt between '" + frDt + "' and '" + toDt + "' " + brQuery + "  group by a.acno, a.dt "
                            + " union all "
                            + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b,td_customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode  "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered , a.tdsRecoveredDt  "
                            + " union all "
                            + " select b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.TDS),0) as ttds,'T' as flag, a.recovered, ifnull(a.tdsRecoveredDt,''), ifnull(a.recoveredVch,''),c.custname,c.panno from tds_reserve_history a, customerid b, customermaster c " + actCatgTable + " "
                            + " where  a.acno = b.acno " + actCatgQuery + " and substring(a.acno,1,2)=c.brncode and substring(a.acno,5,6)=c.custno and substring(a.acno,3,2)=c.actype and substring(a.acno,11,2)=c.agcode "
                            + tdsDtQuery + tdsRecDtQuery + brQuery + "  group by a.acno, a.voucherno, a.recovered,  a.tdsRecoveredDt   "
                            + " order by 1,2,3,4,6 ) aa, cbs_customer_master_detail bb where aa.custid = cast(bb.customerid as unsigned)  "
                            + " group by aa.custid,aa.acno, aa.voucherno,aa.dt,aa.interest,aa.flag,aa.recovered,aa.tdsRecoveredDt   order by 1,2,3,4,6 ) dd "
                            + " left join "
                            + " (select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                            + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                            + " union "
                            + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as gurPanCard "
                            + " from cbs_cust_minorinfo_his mn, "
                            + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                            + " (select cast(CustomerId as UNSIGNED) as minorCustId, mAX(TXNID) as txnId from cbs_cust_minorinfo_his where "
                            + " ifnull(date_format(LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "' "
                            + " and (MajorityDate is null or MajorityDate = '' or date_format(MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                            + " group by cast(CustomerId as UNSIGNED) "
                            + " ) aa  "
                            + " where mn.guardiancode = cm.customerid "
                            + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                            + " and mn.customerid = cc.customerid "
                            + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                            + " "
                            + " left join  "
                            + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                            + " union all  "
                            + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                            + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                            + " group by dd.custid,dd.acno, dd.voucherno,dd.dt,dd.interest,dd.flag,dd.recovered,dd.tdsRecoveredDt ) ee"
                            + " right join "
                            + " ( select cast(if(ifnull(pro.custid,'')<>'',ifnull(pro.custid,''),ifnull(gur.majorCustId,dd.customerid)) as UNSIGNED) as majorPropCustID, "
                            + " sum(dd.interest) as totalInt "
                            + " from "
                            + " (select cast(aa.customerid as unsigned) as customerid, bb.custid, bb.acno, bb.voucherno, sum(bb.interest) as interest, ifnull(aa.PAN_GIRNumber,'') as pan, aa.minorflag  from "
                            + " (select distinct b.custid,a.acno, a.voucherno,a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'' as recovered,'' as tdsRecoveredDt,'' as recoveredVch from td_interesthistory a,customerid b   where a.acno = b.acno   and a.dt between '" + frDt + "' and '" + toDt + "' group by a.acno, a.voucherno, a.dt  "
                            + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from dds_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'    group by a.acno, a.dt  "
                            + " union all  select distinct b.custid,a.acno, '',a.dt,ifnull(sum(a.interest),0) as interest,'I' as flag,'','','' from rd_interesthistory a,customerid b   where a.acno = b.acno and a.dt between '" + frDt + "' and '" + toDt + "'  group by a.acno, a.dt  "
                            + " ) bb, cbs_customer_master_detail aa where bb.custid = cast(aa.customerid as unsigned) group by bb.custid,  bb.acno, bb.voucherno order by 1,2,3 ) dd "
                            + " left join "
                            + " (select  cast(mn.CustomerId as unsigned) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED) as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag "
                            + " from cbs_cust_minorinfo mn, cbs_customer_master_detail cm "
                            + " where mn.guardiancode = cm.customerid and (mn.guardiancode is not null and mn.guardiancode<> '') "
                            + " union "
                            + " select  cast(mn.CustomerId as UNSIGNED) as minorCustId, cast(ifnull(mn.guardiancode,'0') as UNSIGNED)  as majorCustId, cm.PAN_GIRNumber as pan, cm.minorflag "
                            + " from cbs_cust_minorinfo_his mn, "
                            + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc, "
                            + " (select  cast(mn.CustomerId as UNSIGNED) as minorCustId, max(mn.TXNID) as txnId from cbs_cust_minorinfo_his mn, "
                            + " cbs_customer_master_detail cm, cbs_cust_minorinfo cc  "
                            + " where mn.guardiancode = cm.customerid  and  mn.customerid = cc.customerid "
                            + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') "
                            + " and ( ifnull(date_format(mn.LastChangeTime,'%Y%m%d' ),'19000101')<='" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) aa  "
                            + " where mn.guardiancode = cm.customerid "
                            + " and cast(mn.CustomerId as UNSIGNED) = aa.minorCustId and mn.TXNID = aa.txnId "
                            + " and mn.customerid = cc.customerid "
                            + " and (cc.MajorityDate is null or cc.MajorityDate = '' or date_format(cc.MajorityDate,'%Y%m%d' )>'" + toDt + "') group by cast(mn.CustomerId as UNSIGNED)) gur on gur.minorCustId = dd.custId   "
                            + " left join  "
                            + " (select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,td_accountmaster tm where opermode = 8 and  ci.custid = tm.custid1  "
                            + " union all  "
                            + " select distinct tm.acno, ci.CustId, tm.custid1 from customerid ci,accountmaster tm where tm.opermode = 8 and  ci.custid = tm.custid1 "
                            + " and substring(tm.acno,3,2) in (select acctcode from accounttypemaster where acctnature in ('DS','RD'))) pro on  pro.acno = dd.acno "
                            + " group by majorPropCustID having sum(dd.interest) between " + fromAmt + " and " + toAmt + " order by cast(majorPropCustID as UNSIGNED), "
                            + " dd.customerid ) amt "
                            + " on ee.majorPropCustID = amt.majorPropCustID  where ifnull(ee.custid,'') <>'' "
                            + " group by ee.custid,ee.acno, ee.voucherno,ee.dt,ee.interest,ee.flag,ee.recovered,ee.tdsRecoveredDt "
                            + " order by ee.majorPropCustID,ee.custid,ee.acno, cast(ee.voucherno as decimal),ee.dt,ee.flag";
                }
            }
//            System.out.println("Query:>>>" + acQuery);
            List distinctAcNoList = em.createNativeQuery(acQuery).getResultList();
            if (!distinctAcNoList.isEmpty()) {
                String acNo = "", preAcNo = "", preCustId = "", preName = "", prePanNo = "", preReceiptNo = "", preDate = "",
                        preTdsPostingDt = "", preRecDt = "", preRecVchNo = "", preRecFlag = "";
                double intAmt = 0d, tdsAmt = 0d, recTdsAmt = 0d;
                for (int i = 0; i < distinctAcNoList.size(); i++) {
                    Vector distinctAcNoVect = (Vector) distinctAcNoList.get(i);
//                    System.out.println("Acno ="+ distinctAcNoVect.get(0).toString() +" and voucher no is ="+distinctAcNoVect.get(1).toString());
                    /*
                     *Only Interest Posting Details
                     */
                    custId = distinctAcNoVect.get(0).toString();
                    if (i == 0) {
                        preCustId = custId;
                    }
                    TdReceiptIntDetailPojo pojo = new TdReceiptIntDetailPojo();
                    pojo.setCustId(distinctAcNoVect.get(0).toString());
                    pojo.setName(distinctAcNoVect.get(9).toString());
                    pojo.setPanNo(distinctAcNoVect.get(10).toString());
                    pojo.setAcNo(distinctAcNoVect.get(1).toString());
                    pojo.setReceiptNo(distinctAcNoVect.get(2).toString());
                    pojo.setDate(dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(3).toString())));
                    if (allAcNo.equalsIgnoreCase("C") || allAcNo.equalsIgnoreCase("I")) {
                        pojo.setMajorCustId(distinctAcNoVect.get(13).toString());
                        if (!(distinctAcNoVect.get(13).toString().equalsIgnoreCase("") || distinctAcNoVect.get(13).toString().equalsIgnoreCase(null)) && !(distinctAcNoVect.get(13).toString().equalsIgnoreCase(distinctAcNoVect.get(0).toString()))) {
                            pojo.setMinorFlag("Yes");
                        } else {
                            pojo.setMinorFlag("");
                        }
                    }

                    if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("I")) { //I=Interest
                        pojo.setInterest(Double.parseDouble(distinctAcNoVect.get(4).toString()));
                        pojo.setTds(0.00);
                        pojo.setTdsPostingDt("");
                        pojo.setRecoveredTdsAmt(0.00);
                        pojo.setRecoveredDt("");
                        pojo.setRecoveredFlag("Interest ");
                        pojo.setRecoveredVchNo("");
                    } else if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("T")) { //T=TDS 
                        pojo.setInterest(0.00);
                        pojo.setTds(Double.parseDouble(distinctAcNoVect.get(4).toString()));
                        pojo.setTdsPostingDt(dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(3).toString())));
                        if (distinctAcNoVect.get(6).toString().equalsIgnoreCase("R")) {
                            if (tdsOption.equals("RC")) {
                                if ((ymd_1.parse(distinctAcNoVect.get(7).toString()).compareTo(ymd.parse(recFrDt)) >= 0) && (ymd_1.parse(distinctAcNoVect.get(7).toString()).compareTo(ymd.parse(recToDt)) <= 0)) {
                                    pojo.setRecoveredTdsAmt(Double.parseDouble(distinctAcNoVect.get(4).toString()));
                                    pojo.setRecoveredFlag("Recover");
                                    pojo.setRecoveredDt(dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString())));
                                    pojo.setRecoveredVchNo(distinctAcNoVect.get(8).toString());
                                } else {
                                    pojo.setRecoveredTdsAmt(0.00);
                                    pojo.setRecoveredFlag("UnRecover");
                                    pojo.setRecoveredDt("");
                                    pojo.setRecoveredVchNo("");
                                }
                            } else {
                                pojo.setRecoveredTdsAmt(Double.parseDouble(distinctAcNoVect.get(4).toString()));
                                pojo.setRecoveredFlag("Recover");
                                pojo.setRecoveredDt(dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString())));
                                pojo.setRecoveredVchNo(distinctAcNoVect.get(8).toString());
                            }
                        } else if (distinctAcNoVect.get(6).toString().equalsIgnoreCase("NR")) {
                            pojo.setRecoveredTdsAmt(0.00);
                            pojo.setRecoveredFlag("UnRecover");
                            pojo.setRecoveredDt("");
                            pojo.setRecoveredVchNo("");
                        } else {
                            pojo.setRecoveredTdsAmt(0.00);
                            pojo.setRecoveredFlag("");
                            pojo.setRecoveredDt("");
                            pojo.setRecoveredVchNo("");
                        }
                    }
                    if (allAcNo.equalsIgnoreCase("C") && summaryOpt.equalsIgnoreCase("S")) {
                        if (custId.equalsIgnoreCase(preCustId)) {
                            if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("I")) { //I=Interest
                                preRecFlag = "Interest ";
                                preRecVchNo = "";
                                intAmt = intAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                            } else if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("T")) { //T=TDS 
                                tdsAmt = tdsAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                                if (distinctAcNoVect.get(6).toString().equalsIgnoreCase("R")) {
                                    if (tdsOption.equals("RC")) {
                                        if ((ymd_1.parse(distinctAcNoVect.get(7).toString()).compareTo(ymd.parse(recFrDt)) >= 0) && (ymd_1.parse(distinctAcNoVect.get(7).toString()).compareTo(ymd.parse(recToDt)) <= 0)) {
                                            recTdsAmt = recTdsAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                                            preTdsPostingDt = "";
                                            preRecDt = distinctAcNoVect.get(7).toString().equalsIgnoreCase("") ? preRecDt : dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString()));
                                            preRecFlag = "Recover ";
                                            preRecVchNo = distinctAcNoVect.get(8).toString();
                                        } else {
                                            recTdsAmt = recTdsAmt;
                                            preTdsPostingDt = "";
                                            preRecDt = distinctAcNoVect.get(7).toString().equalsIgnoreCase("") ? preRecDt : dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString()));
                                            preRecFlag = "Recover ";
                                            preRecVchNo = distinctAcNoVect.get(8).toString();
                                        }
                                    } else {
                                        recTdsAmt = recTdsAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                                        preTdsPostingDt = "";
                                        preRecDt = distinctAcNoVect.get(7).toString().equalsIgnoreCase("") ? preRecDt : dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString()));
                                        preRecFlag = "Recover ";
                                        preRecVchNo = distinctAcNoVect.get(8).toString();
                                    }
                                }
                            }
                            preAcNo = acNo;
                            preCustId = distinctAcNoVect.get(0).toString();
                            preName = distinctAcNoVect.get(9).toString();
                            prePanNo = distinctAcNoVect.get(10).toString();
                            preAcNo = distinctAcNoVect.get(1).toString();
                            preReceiptNo = distinctAcNoVect.get(2).toString();
                            preDate = dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(3).toString()));
                        } else {
                            TdReceiptIntDetailPojo prePojo = new TdReceiptIntDetailPojo();
                            prePojo.setCustId(preCustId);
                            prePojo.setName(preName);
                            prePojo.setPanNo(prePanNo);
                            prePojo.setAcNo(preAcNo);
                            prePojo.setReceiptNo(preReceiptNo);
                            prePojo.setDate(preDate);
                            prePojo.setInterest(intAmt);
                            prePojo.setTds(tdsAmt);
                            prePojo.setTdsPostingDt(preTdsPostingDt);
                            prePojo.setRecoveredTdsAmt(recTdsAmt);
                            prePojo.setRecoveredDt(preRecDt);
                            prePojo.setRecoveredFlag(preRecFlag);
                            prePojo.setRecoveredVchNo(preRecVchNo);
                            if (tdsAmt > 0) {
                                dataList.add(prePojo);
                            }

                            acNo = "";
                            preAcNo = "";
                            preCustId = "";
                            preName = "";
                            prePanNo = "";
                            preReceiptNo = "";
                            preDate = "";
                            preTdsPostingDt = "";
                            preRecDt = "";
                            preRecVchNo = "";
                            preRecFlag = "";
                            intAmt = 0d;
                            tdsAmt = 0d;
                            recTdsAmt = 0d;

                            if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("I")) { //I=Interest
                                preTdsPostingDt = "";
                                preRecDt = "";
                                preRecFlag = "Interest ";
                                preRecVchNo = "";
                                intAmt = intAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                            } else if (distinctAcNoVect.get(5).toString().equalsIgnoreCase("T")) { //T=TDS 
                                tdsAmt = tdsAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                                if (distinctAcNoVect.get(6).toString().equalsIgnoreCase("R")) {
                                    recTdsAmt = recTdsAmt + Double.parseDouble(distinctAcNoVect.get(4).toString());
                                    preTdsPostingDt = "";
                                    preRecDt = distinctAcNoVect.get(7).toString().equalsIgnoreCase("") ? preRecDt : dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(7).toString()));
                                    preRecFlag = "Recover ";
                                    preRecVchNo = distinctAcNoVect.get(8).toString();
                                }
                            }
                            preCustId = distinctAcNoVect.get(0).toString();
                            preName = distinctAcNoVect.get(9).toString();
                            prePanNo = distinctAcNoVect.get(10).toString();
                            preAcNo = distinctAcNoVect.get(1).toString();
                            preReceiptNo = distinctAcNoVect.get(2).toString();
                            preDate = dmyFormat.format(ymd_1.parse(distinctAcNoVect.get(3).toString()));
                            preCustId = custId;
                        }
                    } else {
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List getAcctTypeExceptGL() throws ApplicationException {
        try {
            List select = em.createNativeQuery("select AcctCode,concat(acctcode,' -- ',acctdesc) from accounttypemaster where acctNature not in ('" + CbsConstant.PAY_ORDER + "')").getResultList();
            return select;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<MinBalanceChargesPostPojo> lockerExeErrReport(String repType, String repDt, String orgBrCode) throws ApplicationException {
        List<MinBalanceChargesPostPojo> lockerErrPojo = new ArrayList<MinBalanceChargesPostPojo>();
        try {
            String bnkName = null, bnkAddress = null;
            List objBan = common.getBranchNameandAddress(orgBrCode);
            if (objBan != null) {
                bnkName = objBan.get(0).toString();
                bnkAddress = objBan.get(1).toString();
            }

            if (repType.equalsIgnoreCase("E")) {
                List result = em.createNativeQuery("select a.acno,a.amount,a.details,b.CUSTNAME from pendingcharges a,accountmaster b  where a.dt='" + repDt + "' and a.charges='VCH. Of Locker Rent' and a.acno=b.acno and b.CurBrCode ='" + orgBrCode + "'").getResultList();
                if (result.size() > 0) {
                    for (int j = 0; j < result.size(); j++) {
                        Vector record = (Vector) result.get(j);
                        MinBalanceChargesPostPojo balCert = new MinBalanceChargesPostPojo();
                        balCert.setBnkName(bnkName);
                        balCert.setBnkAddress(bnkAddress);
                        balCert.setAcno(record.get(0).toString());
                        balCert.setDramt(Double.parseDouble(record.get(1).toString()));
                        balCert.setDetails("INSUFFICIENT FUND");
                        balCert.setCustName(record.get(3).toString());
                        lockerErrPojo.add(balCert);
                    }
                }
            } else if (repType.equalsIgnoreCase("P")) {
                List posResult = em.createNativeQuery("SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l, recon r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' "
                        + " UNION SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l,ca_recon r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' "
                        + " UNION SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l,rdrecon r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' "
                        + " UNION SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l,td_recon r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' "
                        + " UNION SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l, ddstransaction r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' "
                        + " UNION SELECT r.acno,a.custname,r.dramt,case when r.details like '%VCH. OF Locker Rent:%' then 'SERIVICE TAX' ELSE 'POSTED' END FROM lockerrecon l, loan_recon r,accountmaster a WHERE l.acno = r.acno AND l.acno = a.acno AND date_format(l.paydt,'%Y%m%d') = '" + repDt + "' AND r.details LIKE '%Locker%' AND DATE_FORMAT(l.paydt,'%Y%m%d') = r.dt AND a.CurBrCode = '" + orgBrCode + "' ORDER BY 4 desc,1 asc").getResultList();
                if (!posResult.isEmpty()) {
                    for (int i = 0; i < posResult.size(); i++) {
                        Vector vtr1 = (Vector) posResult.get(i);
                        MinBalanceChargesPostPojo pojo1 = new MinBalanceChargesPostPojo();
                        pojo1.setBnkName(bnkName);
                        pojo1.setBnkAddress(bnkAddress);
                        pojo1.setAcno(vtr1.get(0).toString());
                        pojo1.setDramt(Double.parseDouble(vtr1.get(2).toString()));
                        pojo1.setDetails(vtr1.get(3).toString());
                        pojo1.setCustName(vtr1.get(1).toString());
                        lockerErrPojo.add(pojo1);
                    }
                }
            }
            return lockerErrPojo;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<AtmCardIssueDetailPojo> getAtmCardIssueDetail(String branch, String statusType, String frDt, String toDt) throws ApplicationException {
        List<AtmCardIssueDetailPojo> dataList = new ArrayList<AtmCardIssueDetailPojo>();
        List result = new ArrayList();
        try {
            if (branch.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select acno,min_limit,date_format(enter_time,'%d/%m/%Y') from prizm_card_master where date_format(enter_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and cbs_status = '" + statusType + "'").getResultList();
            } else {
                result = em.createNativeQuery("select acno,min_limit,date_format(enter_time,'%d/%m/%Y') from prizm_card_master where substring(acno,1,2) = '" + branch + "' and date_format(enter_time,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and cbs_status = '" + statusType + "'").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    AtmCardIssueDetailPojo pojo = new AtmCardIssueDetailPojo();
                    pojo.setAcno(vtr.get(0).toString());
                    List custList = em.createNativeQuery("select CustId from customerid where acno = '" + vtr.get(0).toString() + "'").getResultList();
                    Vector ele = (Vector) custList.get(0);
                    pojo.setLimitAmt(Double.parseDouble(vtr.get(1).toString()));
                    pojo.setDate(vtr.get(2).toString());
                    pojo.setCustName(ftsPosting.getCustName(vtr.get(0).toString()));
                    pojo.setCustId(ele.get(0).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List<TdReceiptIntDetailPojo> getInterestCertificateDate(String custId, List<TdReceiptIntDetailPojo> reportList, String ftDate, String toDate) throws ApplicationException {
        List<TdReceiptIntDetailPojo> dataList = new ArrayList<TdReceiptIntDetailPojo>();
        List result = new ArrayList();
        String receiptNo, acNo;
        String matAmts = "0";
        int count = 0, j, k;
        try {
            for (j = 0; j < reportList.size(); j++) {
                double interestTmp = 0, tds = 0;
                receiptNo = reportList.get(j).getReceiptNo();
                acNo = reportList.get(j).getAcNo();
                String acctNature = ftsPosting.getAcNatureByCode(acNo.substring(2, 4));
                for (k = 0; k < reportList.size(); k++) {
                    if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        if (acNo.equals(reportList.get(k).getAcNo())) {
                            interestTmp += reportList.get(k).getInterest();
                            tds += reportList.get(k).getTds();
                            count++;
                        }
                    } else {
                        if (receiptNo.equals(reportList.get(k).getReceiptNo()) && (acNo.equalsIgnoreCase(reportList.get(k).getAcNo()))) {
                            interestTmp += reportList.get(k).getInterest();
                            tds += reportList.get(k).getTds();
                            count++;
                        }
                    }
                }
                j = count - 1;

                if (acctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acctNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    result = em.createNativeQuery("select t.PrinAmt, DATE_FORMAT(t.FDDT,'%d/%m/%Y'), "
                            + "DATE_FORMAT(t.MatDt,'%d/%m/%Y'), concat_ws(',', nullif(trim(PerAddressLine1),''),nullif(trim(concat_ws(',', nullif(trim(peraddressline2),'') ,nullif(trim(PerVillage),''),nullif(trim(PerBlock),'') ,nullif((select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = PerCityCode),''),nullif((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = PerStateCode),''),nullif(PerPostalCode,''))),'')) "
                            + "from td_vouchmst t, cbs_customer_master_detail where customerid = '" + custId + "' and acno = '" + acNo + "' and voucherNo =  '" + receiptNo + "'").getResultList();
                } else if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    result = em.createNativeQuery("select ifnull(sum(cramt),0)depositAmt,DATE_FORMAT(a.openingDT,'%d/%m/%Y') openingDT,"
                            + "DATE_FORMAT(a.RDmatdate,'%d/%m/%Y') RDmatdate,concat_ws(',', nullif(trim(PerAddressLine1),''),nullif(trim(concat_ws(',', nullif(trim(peraddressline2),'') ,nullif(trim(PerVillage),''),nullif(trim(PerBlock),'') ,nullif((select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = PerCityCode),''),nullif((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = PerStateCode),''),nullif(PerPostalCode,''))),'')),a.RDinstal,a.intDeposit "
                            + "from accountmaster a,rdrecon r,cbs_customer_master_detail where customerid = '" + custId + "' and a.acno = '" + acNo + "' "
                            + "and a.acno = r.acno and TranType <>8 and r.dt between '" + ftDate + "' and '" + toDate + "'").getResultList();
                } else if (acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    result = em.createNativeQuery("select ifnull(sum(cramt),0)depositAmt,DATE_FORMAT(a.openingDT,'%d/%m/%Y') openingDT,"
                            + "DATE_FORMAT(a.RDmatdate,'%d/%m/%Y') RDmatdate,concat_ws(',', nullif(trim(PerAddressLine1),''),nullif(trim(concat_ws(',', nullif(trim(peraddressline2),'') ,nullif(trim(PerVillage),''),nullif(trim(PerBlock),'') ,nullif((select ref_desc from cbs_ref_rec_type where ref_rec_no = 001 and ref_code = PerCityCode),''),nullif((select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no = 002 and ref_code = PerStateCode),''),nullif(PerPostalCode,''))),'')),"
                            + "a.RDinstal,a.intDeposit from accountmaster a,ddstransaction r,cbs_customer_master_detail where customerid = '" + custId + "' "
                            + "and a.acno = '" + acNo + "' and a.acno = r.acno and TranType <>8 and r.dt between '" + ftDate + "' and '" + toDate + "'").getResultList();
                }

                if (!result.isEmpty()) {
                    TdReceiptIntDetailPojo pojo = new TdReceiptIntDetailPojo();
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        double prinAmt = Double.parseDouble(vtr.get(0).toString());

                        if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            String installment = vtr.get(4).toString();
                            String roi = vtr.get(5).toString();
                            int monDiff = CbsUtil.monthDiff(dmyFormat.parse(vtr.get(1).toString()), dmyFormat.parse(vtr.get(2).toString()));
                            matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(installment), monDiff, Float.parseFloat(roi));
                        }
                        pojo.setTds(prinAmt); // PrinAmt --> Deposite Amount
                        pojo.setDate(vtr.get(1).toString());// FDDT --> Opening Date
                        pojo.setRecoveredDt(vtr.get(2).toString());// MatDt --> Maturity Date
                        pojo.setCustAdd(vtr.get(3).toString());
                        pojo.setAcNo(acNo);
                        pojo.setReceiptNo(receiptNo);
                        pojo.setInterest(interestTmp);
                        pojo.setTdsAmount(tds);
                        if (acctNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) || acctNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            pojo.setRecoveredTdsAmt(Double.parseDouble(matAmts));// Maturity Amount     
                        } else {
                            pojo.setRecoveredTdsAmt((prinAmt + interestTmp) - tds);// Maturity Amount     
                        }
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List<FolioLedgerPojo> lockerOperationDetail(String frmdt, String todt, String brncode, String reportOption, String lockerNumber, String reportType) throws ApplicationException {
        List<FolioLedgerPojo> resultArray = new ArrayList<FolioLedgerPojo>();
        String reserveBankOfIndia = "Reserve Bank Of India";
        String query = "", query1 = "";
        String brCode1 = "", brCode2 = "", bankNameReq = "", bankAddressReq = "", city = "", pinCode = "";
        String custFullName = "", mailAddressLine1 = "", mailAddressLine2 = "", mailDistrict = "", mailPostalCode = "", mailVillage = "", mailStateCode = "", mailBlock = "";
        List result = new ArrayList();
        if (reportType.equalsIgnoreCase("I")) {
            query = "lockerno ='" + lockerNumber + "' and";
            query1 = " and a.lockerno ='" + lockerNumber + "'";
        }
        Map<String, Integer> visitCountMap = new HashMap<>();
        try {
            if (reportOption.equalsIgnoreCase("O")) {
                if (brncode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select acno,count(acno) from locker_operation "
                            + " where " + query + "  date_format(opdate,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' group by acno ").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,count(acno) from locker_operation "
                            + " where " + query + " brncode = '" + brncode + "' and date_format(opdate,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' group by acno ").getResultList();
                }
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    visitCountMap.put(vtr.get(0).toString(), Integer.parseInt(vtr.get(1).toString()));
                }
                if (brncode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select acno,cast(lockerno as unsigned),cast(cabno as unsigned),lockertype,cast(keyno as unsigned),custname,inpresenceof,date_format(intime,'%d/%m/%Y %k:%i:%s'),date_format(outtime,'%d/%m/%Y %k:%i:%s') from locker_operation "
                            + " where " + query + "  date_format(opdate,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' order by acno,intime").getResultList();
                } else {
                    result = em.createNativeQuery("select acno,cast(lockerno as unsigned),cast(cabno as unsigned),lockertype,cast(keyno as unsigned),custname,inpresenceof,date_format(intime,'%d/%m/%Y %k:%i:%s'),date_format(outtime,'%d/%m/%Y %k:%i:%s') from locker_operation "
                            + " where " + query + " brncode = '" + brncode + "' and date_format(opdate,'%Y%m%d') between '" + frmdt + "' and '" + todt + "' order by acno,intime").getResultList();
                }
            } else {
                if (brncode.equalsIgnoreCase("0A")) {
                    result = em.createNativeQuery("select a.acno,cast(lockerno as unsigned) as lockerNo,cast(cabno as unsigned) as cabNo,lockertype,cast(keyno as unsigned) keyNo,b.custname ,date_format(issuedt,'%d/%m/%Y') as IssueDt,date_format(issuedt,'%Y%m%d') as opdate, "
                            + "DATEDIFF('" + todt + "',date_format(issuedt,'%Y%m%d')) days "
                            + "from lockeracmaster a, accountmaster b where a.acno not in "
                            + "(select distinct acno from locker_operation) "
                            + "and a.acno=b.acno "
                            + "union "
                            + "select a.acno,a.lockerNo,a.cabNo,a.lockertype,a.keyNo,a.custname,a.IssueDt,date_format(max(b.opdate),'%Y%m%d'),DATEDIFF('" + todt + "',date_format(max(b.opdate),'%Y%m%d')) days  from "
                            + "(select a.acno,cast(lockerno as unsigned) as lockerNo,cast(cabno as unsigned) as cabNo,lockertype,cast(keyno as unsigned) keyNo,b.custname ,date_format(issuedt,'%d/%m/%Y') as IssueDt,brncode "
                            + "from lockeracmaster a, accountmaster b where a.acno not in "
                            + "(select distinct acno from locker_operation where opdate between '" + frmdt + "' and '" + todt + "') "
                            + "and a.acno=b.acno order by issuedt,a.acno)a,locker_operation b where a.acno = b.acno and a.lockerNo = b.lockerno and a.cabNo = b.cabno and a.lockertype = b.lockertype "
                            + "and a.keyNo = b.keyno and a.brncode = b.brncode group by a.acno,a.lockerNo,a.cabNo,a.lockertype,a.keyNo").getResultList();

//                    result = em.createNativeQuery("select a.acno,cast(lockerno as unsigned),cast(cabno as unsigned),lockertype,cast(keyno as unsigned),b.custname ,date_format(issuedt,'%d/%m/%Y') "
//                            + "from lockeracmaster a, accountmaster b where a.acno not in(select distinct acno from locker_operation where opdate between '" + frmdt + "' and '" + todt + "') and a.acno=b.acno "
//                            + "/*and issuedt between '" + frmdt + "' and '" + todt + "' */" + query1 + " order by a.acno,issuedt ").getResultList();
                } else {
                    result = em.createNativeQuery("select a.acno,cast(lockerno as unsigned) as lockerNo,cast(cabno as unsigned) as cabNo,lockertype,cast(keyno as unsigned) keyNo,b.custname ,date_format(issuedt,'%d/%m/%Y') as IssueDt,date_format(issuedt,'%Y%m%d') as opdate, "
                            + "DATEDIFF('" + todt + "',date_format(issuedt,'%Y%m%d')) days "
                            + "from lockeracmaster a, accountmaster b where a.acno not in "
                            + "(select distinct acno from locker_operation) "
                            + "and a.acno=b.acno and brncode = '" + brncode + "' " + query1 + " "
                            + "union "
                            + "select a.acno,a.lockerNo,a.cabNo,a.lockertype,a.keyNo,a.custname,a.IssueDt,date_format(max(b.opdate),'%Y%m%d'),DATEDIFF('" + todt + "',date_format(max(b.opdate),'%Y%m%d')) days  from "
                            + "(select a.acno,cast(lockerno as unsigned) as lockerNo,cast(cabno as unsigned) as cabNo,lockertype,cast(keyno as unsigned) keyNo,b.custname ,date_format(issuedt,'%d/%m/%Y') as IssueDt,brncode "
                            + "from lockeracmaster a, accountmaster b where a.acno not in "
                            + "(select distinct acno from locker_operation where opdate between '" + frmdt + "' and '" + todt + "') "
                            + "and a.acno=b.acno order by issuedt,a.acno)a,locker_operation b where a.acno = b.acno and a.lockerNo = b.lockerno and a.cabNo = b.cabno and a.lockertype = b.lockertype "
                            + "and a.keyNo = b.keyno and a.brncode = b.brncode and a.brncode = '" + brncode + "' " + query1 + " group by a.acno,a.lockerNo,a.cabNo,a.lockertype,a.keyNo").getResultList();

//                    result = em.createNativeQuery("select a.acno,cast(lockerno as unsigned),cast(cabno as unsigned),lockertype,cast(keyno as unsigned),b.custname ,date_format(issuedt,'%d/%m/%Y') "
//                            + "from lockeracmaster a, accountmaster b where a.acno not in(select distinct acno from locker_operation where opdate between '" + frmdt + "' and '" + todt + "') and a.acno=b.acno  "
//                            + "and brncode = '" + brncode + "' /*and issuedt between '" + frmdt + "' and '" + todt + "' */" + query1 + " order by issuedt ").getResultList();
                }
            }
            String tmpAcno = "";
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr1 = (Vector) result.get(i);
                    FolioLedgerPojo row = new FolioLedgerPojo();
                    String acNo = vtr1.get(0).toString();
                    String lockerNo = vtr1.get(1).toString();
                    String keyNo = vtr1.get(4).toString();
                    if (!reportOption.equalsIgnoreCase("O")) {
                        List lm = em.createNativeQuery(" select c.brncode, b.bankname, b.bankaddress, c.keyno, a.City, a.Pincode from lockeracmaster c , bnkadd b, branchmaster a where c.lockerno = '" + lockerNo + "'"
                                + "and c.brncode = b.branchcode and b.alphacode = a.AlphaCode and c.keyno = '" + keyNo + "' and c.acno='" + acNo + "'").getResultList();
                        if (!lm.isEmpty()) {
                            Vector vec1 = (Vector) lm.get(0);
                            bankNameReq = vec1.get(1).toString();
                            bankAddressReq = vec1.get(2).toString();
                            city = vec1.get(4).toString();
                            pinCode = vec1.get(5).toString();
                        }
                        List l = em.createNativeQuery("select  trim(ccmd.CustFullName), concat(trim(ccmd.MailAddressLine1),' ',ifnull(trim(MailAddressLine2),'')) as custAdd,"
                                + "ifnull(trim(ccmd.MailDistrict),'') as dist, ifnull(trim(ccmd.MailPostalCode),'') pin, ifnull(trim(ccmd.MailVillage),'') village,re.REF_DESC stateCode,"
                                + "ifnull(trim(ccmd.mailblock),'')block from cbs_customer_master_detail ccmd,cbs_ref_rec_type re "
                                + "where ccmd.customerid =(select custId from customerid where acno ='" + acNo + "') and re.REF_REC_NO = '002' and re.REF_CODE = ccmd.MailStateCode").getResultList();
                        if (!l.isEmpty()) {
                            Vector vector1 = (Vector) l.get(0);
                            custFullName = vector1.get(0).toString();
                            mailAddressLine1 = vector1.get(1).toString();
                            mailDistrict = vector1.get(2).toString();
                            mailPostalCode = vector1.get(3).toString();
                            mailVillage = vector1.get(4).toString();
                            mailStateCode = vector1.get(5).toString();
                            mailBlock = vector1.get(6).toString();

                        }
                    }
                    if (reportOption.equalsIgnoreCase("O")) {
                        if (acNo.equalsIgnoreCase(tmpAcno)) {
                            row.setNoOfvisitCustInLocker(0);
                            tmpAcno = acNo;
                        } else {
                            row.setNoOfvisitCustInLocker(visitCountMap.get(acNo));
                            tmpAcno = acNo;
                        }
                    }
                    row.setFolioNo(vtr1.get(0).toString());
                    row.setThrDemand(vtr1.get(1).toString());
                    row.setThrPay(vtr1.get(2).toString());
                    row.setThrOpen(vtr1.get(3).toString());
                    row.setThrBal(vtr1.get(4).toString());
                    row.setCustName(vtr1.get(5).toString().toUpperCase());
                    row.setFatherName(vtr1.get(6).toString());
                    row.setMailAddressLine1(mailAddressLine1.toUpperCase());
                    // row.setMailAddressLine2(mailAddressLine2);
                    row.setMailDistrict(mailDistrict.toUpperCase());
                    row.setMailPostalCode(mailPostalCode.toUpperCase());
                    row.setMailStateCode(mailStateCode.toUpperCase());
                    row.setMailVillage(mailVillage.toUpperCase());
                    row.setBankNameReq(bankNameReq.toUpperCase());
                    row.setBankAddressReq(bankAddressReq.toUpperCase());
                    row.setCity(city.toUpperCase());
                    row.setPinCode(pinCode.toUpperCase());
                    if (reportOption.equalsIgnoreCase("O")) {
                        row.setDob(vtr1.get(7).toString());
                        row.setTxnDt(vtr1.get(8).toString());
                    }
                    if (reportOption.equalsIgnoreCase("U")) {
                        row.setLastOperationDt(dmyFormat.format(ymdFormat.parse(vtr1.get(7).toString())));
                        row.setDays(Long.parseLong(vtr1.get(8).toString()));
                    }
                    row.setReserveBankOfIndia(reserveBankOfIndia);
                    resultArray.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return resultArray;
    }

    public List<UltilityReportPojo> getUtilityReportDetails(String brnCode, String remarks,
            String frDate, String toDate, String mode, String acno, String searchBy, String serchByOption) throws ApplicationException {
        List<UltilityReportPojo> detailList = new ArrayList<>();
        String acNo, businessDate, name, mobileno, details;
        double amount;
        String subQuery = " and (r.details like '%" + remarks + "%' ";
        if (!serchByOption.equalsIgnoreCase("")) {
            if (serchByOption.contains(",")) {
                String[] splitArray = serchByOption.split(",");
                for (int i = 0; i < splitArray.length; i++) {
                    String subQuery1 = "  or r.details like '%" + splitArray[i] + "%' ";
                    subQuery = subQuery + subQuery1;

                }
            } else {
                String subQuery1 = "  or r.details like '%" + serchByOption + "%' ";
                subQuery = subQuery + subQuery1;
            }
        }


        try {
            List list = new ArrayList();
            if (mode.equalsIgnoreCase("A")) {
                if (brnCode.equalsIgnoreCase("0A")) {
                    list = em.createNativeQuery("select acno,custfullname,mobileNo,amount,details,dt from (select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'   " + subQuery + " )"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,ca_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,loan_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,ddstransaction r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,rdrecon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from td_accountmaster a,cbs_customer_master_detail c,customerid i,td_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )) a order by acno").getResultList();
                } else {
                    list = em.createNativeQuery("select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,ca_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,loan_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,ddstransaction r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from accountmaster a,cbs_customer_master_detail c,customerid i,rdrecon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'"
                            + " union all"
                            + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                            + " from td_accountmaster a,cbs_customer_master_detail c,customerid i,td_recon r where c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                            + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                            + " and substring(a.acno,1,2)='" + brnCode + "'").getResultList();
                }
            } else if (mode.equalsIgnoreCase("I")) {
                list = em.createNativeQuery("select acno,custfullname,mobileNo,amount,details,dt from (select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from accountmaster a,cbs_customer_master_detail c,customerid i,recon r where  a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                        + " union all"
                        + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from accountmaster a,cbs_customer_master_detail c,customerid i,ca_recon r where a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                        + " union all"
                        + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from accountmaster a,cbs_customer_master_detail c,customerid i,loan_recon r where a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                        + " union all"
                        + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from accountmaster a,cbs_customer_master_detail c,customerid i,ddstransaction r where a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                        + " union all"
                        + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from accountmaster a,cbs_customer_master_detail c,customerid i,rdrecon r where a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )"
                        + " union all"
                        + " select a.acno,c.custfullname,ifnull(c.mobilenumber,'') as mobileNo,cast(dramt as decimal(14,2)) as amount,details,date_format(r.dt,'%d/%m/%Y') as dt"
                        + " from td_accountmaster a,cbs_customer_master_detail c,customerid i,td_recon r where a.acno ='" + acno + "' and c.customerid=i.custid and i.acno=a.acno and a.acno=r.acno "
                        + " and r.ty=1 and r.trandesc not in(3,4) and r.dt between '" + frDate + "' and '" + toDate + "'  " + subQuery + " )) a order by acno").getResultList();
            }
            int srno = 1;
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                UltilityReportPojo obj = new UltilityReportPojo();
                acNo = ele.get(0).toString();
                name = ele.get(1).toString();
                mobileno = ele.get(2).toString();
                amount = Double.parseDouble(ele.get(3).toString());
                details = ele.get(4).toString();
                businessDate = ele.get(5).toString();
                obj.setSrno(srno++);
                obj.setAcNo(acNo);
                obj.setName(name);
                obj.setAmount(BigDecimal.valueOf(amount));
                obj.setMobileNo(mobileno);
                obj.setDetails(details);
                obj.setSetBusinessDt(businessDate);

                detailList.add(obj);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailList;
    }

    public List<Closing_CashPojo> get_ClosingCashDetails(String branch, String frmDt,
            String toDt, String limit) throws ApplicationException {
        List<Closing_CashPojo> detailslist = new ArrayList<>();
        double amount, limit1, DiffernceAmount;

        String businessDate;
        try {
            List list = new ArrayList();
            if (branch.equalsIgnoreCase("0A")) {
                list = em.createNativeQuery("select amt,date_format(ldate,'%d/%m/%Y'),brncode from cashinhand where ldate between date_format('" + frmDt + "','%Y/%m/%d') and " + "date_format('" + toDt + "','%Y%m%d')").getResultList();
            } else {
                list = em.createNativeQuery("select amt,date_format(ldate,'%d/%m/%Y'),brncode from cashinhand where ldate between date_format('" + frmDt + "','%Y/%m/%d') and " + "date_format('" + toDt + "','%Y%m%d') and  brncode='" + branch + "'").getResultList();
            }
            int srno = 1;
            for (int i = 0; i < list.size(); i++) {
                Vector vc = (Vector) list.get(i);
                Closing_CashPojo obj = new Closing_CashPojo();
                amount = Double.parseDouble(vc.get(0).toString());
                businessDate = vc.get(1).toString();
                limit1 = Double.parseDouble(limit);
                DiffernceAmount = amount - limit1;
                obj.setSrno(srno++);
                obj.setLimit(BigDecimal.valueOf(limit1));
                obj.setCash_Closing(BigDecimal.valueOf(amount));
                obj.setSetBusinessDt(businessDate);
                obj.setDifference_Cash(BigDecimal.valueOf(DiffernceAmount));
                detailslist.add(obj);
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return detailslist;
    }

    public List<BranchPerformancePojo> getDetailBranchConversion(String toDt, String branchCode) throws ApplicationException {
        List<BranchPerformancePojo> detailList = new ArrayList<BranchPerformancePojo>();
        try {
            List branchList = new ArrayList();
            String womanSchAcOpen = "";
            String RDLockerMinorAcopn = "";
            if (branchCode.equals("0A")) {
                branchList = em.createNativeQuery("select distinct BrnCode,BranchName ,AlphaCode from branchmaster"
                        + "where BrnCode not in(99,10,9,13,90,12)order by BrnCode").getResultList();
            } else {
                branchList = em.createNativeQuery("select distinct BrnCode,BranchName,AlphaCode from branchmaster where BrnCode = '" + branchCode + "'").getResultList();
            }
            int srNo = 0;
            for (int i = 0; i < branchList.size(); i++) {
                BranchPerformancePojo pojo = new BranchPerformancePojo();
                Vector v = (Vector) branchList.get(i);
                String BrnCode = (Integer.parseInt(v.get(0).toString()) < 10 ? "0" : "") + v.get(0).toString();
                String BranchName = v.get(1).toString();

                int serNo = srNo++;
                List womenSchAcOpenList = em.createNativeQuery("select count(ci.Acno) from cbs_customer_master_detail cm ,"
                        + "customerid ci,accountmaster am where cm.customerid = ci.CustId and ci.Acno = am.ACNo and cm.gender = 'F' and "
                        + "cm.PrimaryBrCode='" + BrnCode + "' and am.OpeningDt = '" + toDt + "'").getResultList();

                if (womenSchAcOpenList.isEmpty()) {
                    womanSchAcOpen = "0";
                } else {
                    Vector vec1 = (Vector) womenSchAcOpenList.get(0);
                    womanSchAcOpen = vec1.get(0).toString();
                }
                List rdLockerMinorList = em.createNativeQuery("select sum(openNo) from ("
                        + "select count(ci.Acno) openNo from cbs_customer_master_detail cm ,customerid ci,accountmaster am  "
                        + "where cm.customerid = ci.CustId and ci.Acno = am.ACNo and cm.gender = 'F' and cm.PrimaryBrCode='06' "
                        + "and am.OpeningDt = '20181005' and am.Accttype in(select AcctCode from accounttypemaster where acctNature='RD') "
                        + "union all "
                        + "select count(ci.Acno) openNo from cbs_customer_master_detail cm ,customerid ci,accountmaster am where cm.customerid = ci.CustId "
                        + "and ci.Acno = am.ACNo and cm.gender = 'F' and cm.PrimaryBrCode='06'  and am.OpeningDt = '20181005' and cm.minorflag='Y' "
                        + "union all "
                        + "select count(lm.acno) openNo from cbs_customer_master_detail cm ,customerid ci,accountmaster am,lockeracmaster lm "
                        + "where cm.customerid = ci.CustId and ci.Acno = am.ACNo and lm.acno = am.ACNo and ci.Acno=lm.acno and cm.gender = 'F' "
                        + "and cm.PrimaryBrCode='06'  and am.OpeningDt = '20181005' ) b").getResultList();
                if (rdLockerMinorList.isEmpty()) {
                    RDLockerMinorAcopn = "0";
                } else {
                    Vector vec2 = (Vector) rdLockerMinorList.get(0);
                    RDLockerMinorAcopn = vec2.get(0).toString();
                }



            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailList;
    }

    public List<BranchPerformancePojo> getDetailBranchPerformance(String fromDt, String toDt, String branchCode) throws ApplicationException {
        List<BranchPerformancePojo> detailList = new ArrayList<BranchPerformancePojo>();
        try {
            List branchList = new ArrayList();
            String acOpen = "";
            String acClosed = "";
            String loacacOpen = "";
            String lokerIssue = "";
            String lokerSurrender = "";
            if (branchCode.equalsIgnoreCase("0A")) {
                branchList = em.createNativeQuery("select distinct BrnCode,BranchName,AlphaCode from branchmaster where (closedate is null or closedate='' or closedate>'" + ymdFormat.format(dmyFormat.parse(toDt)) + "' ) and BrnCode not in('90','99') order by BrnCode").getResultList();
            } else {
                branchList = em.createNativeQuery("select distinct BrnCode,BranchName,AlphaCode from branchmaster where BrnCode = '" + branchCode + "'").getResultList();
            }

            int srNo = 0;
            for (int i = 0; i < branchList.size(); i++) {
                BranchPerformancePojo pojo = new BranchPerformancePojo();
                Vector v = (Vector) branchList.get(i);
                String BrnCode = (Integer.parseInt(v.get(0).toString()) < 10 ? "0" : "") + v.get(0).toString();
                String BranchName = v.get(1).toString();

                int serNo = srNo++;
                List acOpenList = em.createNativeQuery("select sum(openNo) from ("
                        + "select count(Acno) openNo from accountmaster  where  OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'  and curbrcode ='" + BrnCode + "' /*AND (closingdate is null or closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR closingdate='')*/"
                        + "union all "
                        + "select count(Acno) openNo from td_accountmaster  where  OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'  and curbrcode ='" + BrnCode + "' /*AND (closingdate is null or closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR closingdate='')*/ "
                        + ")b").getResultList();
                if (acOpenList.isEmpty()) {
                    acOpen = "0";
                } else {
                    Vector vec1 = (Vector) acOpenList.get(0);
                    acOpen = vec1.get(0).toString();
                }
                List acClosedList = em.createNativeQuery("select sum(closedNo) from ("
                        + "select count(Acno) closedNo from accountmaster  where  ClosingDate between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and curbrcode='" + BrnCode + "' "
                        + "union All "
                        + "select count(Acno) closedNo from td_accountmaster  where  ClosingDate between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and  curbrcode='" + BrnCode + "' "
                        + ")a").getResultList();
                if (acClosedList.isEmpty()) {
                    acClosed = "0";
                } else {
                    Vector vec2 = (Vector) acClosedList.get(0);
                    acClosed = vec2.get(0).toString();
                }
                List loanAcOpenList = em.createNativeQuery("select count(Acno) as noOfAcOpen from accountmaster where OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'  "
                        + "and substring(acno,3,2) in (select AcctCode from accounttypemaster where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and CrDbFlag ='D') and  substring(acno,1,2)='" + BrnCode + "'").getResultList();
                if (loanAcOpenList.isEmpty()) {
                    loacacOpen = "0";
                } else {
                    Vector vec3 = (Vector) loanAcOpenList.get(0);
                    loacacOpen = vec3.get(0).toString();
                }
//                List lokerisuueList = em.createNativeQuery("select count(acno) from lockeracmaster where issuedt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' "
//                        + " and substring(acno,1,2) ='" + BrnCode + "'").getResultList();
                List lokerisuueList = em.createNativeQuery("select count(a.acno),a.lockerno,a.issueDt from"
                        + " (Select distinct am.acno,lm.lockerno,lr.issueDt From lockermaster lm,accountmaster am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and "
                        + "lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode and  lr.brncode = '" + BrnCode + "' and lm.ocflag='Y' "
                        + "and lr.issueDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "') a,"
                        + "(Select c.acno ,cm.customerid,cm.PerAddressLine1 from customerid c ,cbs_customer_master_detail cm where c.custid = cast(cm.customerid as unsigned)) b "
                        + "where a.acno = b.acno  "
                        + "union all "
                        + "select a.acno,a.lockerno,a.issueDt from "
                        + "(Select distinct am.acno, lm.lockerno,lr.issueDt From lockermaster lm,gltable am, lockerrent lr where lr.acno=am.acno and lm.cabNo=lr.cabNo and "
                        + "lm.LockerType=lr.LockerType and lm.LockerNo=lr.LockerNo and lm.brncode = lr.brncode and  lr.brncode = '" + BrnCode + "' and lm.ocflag='Y' "
                        + "and lr.issueDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "') a ").getResultList();

                if (lokerisuueList.isEmpty()) {
                    lokerIssue = "0";
                } else {
                    Vector vec4 = (Vector) lokerisuueList.get(0);
                    lokerIssue = vec4.get(0).toString();
                }
                List lokerSurrenderList = em.createNativeQuery("select count(acno) from lockersurrender where surrenderdt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'  "
                        + "and brncode ='" + BrnCode + "'").getResultList();
                if (lokerSurrenderList.isEmpty()) {
                    lokerSurrender = "0";
                } else {
                    Vector vec5 = (Vector) lokerSurrenderList.get(0);
                    lokerSurrender = vec5.get(0).toString();
                }
                int netOffAc = Integer.parseInt(acOpen) - Integer.parseInt(acClosed);
                int lockerNetOff = Integer.parseInt(lokerIssue) - Integer.parseInt(lokerSurrender);

                //Deposit Amount

                List depositList = em.createNativeQuery("select cast(sum(balance)/100000 as decimal(14,2)) Balance from(\n"
                        + "select ifnull(cast(sum(cramt-dramt) as decimal(25,2)),0) balance  from recon r,accountmaster a where r.dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "'and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'\n"
                        + "and a.curbrcode='" + BrnCode + "' and r.acno = a.acno and a.OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' /*AND (a.closingdate is null or a.closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR a.closingdate='')*/ \n"
                        + "union all \n"
                        + "select ifnull(cast(sum(cramt-dramt) as decimal(25,2)),0) balance from ca_recon where acno in\n"
                        + "(select r.acno  from ca_recon r,accountmaster a where r.dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "'and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and a.curbrcode ='" + BrnCode + "'and r.acno = a.acno "
                        + "and a.OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' /*AND (a.closingdate is null or a.closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR a.closingdate='')*/ \n"
                        + "group by r.acno having sign(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0)) = 1)and dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "'and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'   and auth ='Y'\n"
                        + "union all\n"
                        + "select ifnull(cast(sum(cramt-dramt) as decimal(25,2)),0) balance  from td_recon r,td_accountmaster a where r.dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "'and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'\n"
                        + "and a.curbrcode ='" + BrnCode + "'and closeflag is null and Trantype<> 27 and r.acno = a.acno and a.OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' /*AND (a.closingdate is null or a.closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR a.closingdate='')*/\n"
                        + "union all \n"
                        + "select ifnull(cast(sum(cramt-dramt) as decimal(25,2)),0) balance  from rdrecon r,accountmaster a where r.dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "'and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "'\n"
                        + "and a.curbrcode ='" + BrnCode + "' and r.acno = a.acno and a.OpeningDt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' /*AND (a.closingdate is null or a.closingdate > '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' OR a.closingdate='')*/\n"
                        + ")a").getResultList();
                Vector vtr = (Vector) depositList.get(0);
                double depositAmt = Double.parseDouble(vtr.get(0).toString());

                // Code Add Atm Issue
                List atmList = em.createNativeQuery("select count(*) from atm_card_master where del_flag <>'I'and date_format(registration_dt,'%Y%m%d') between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and substring(acno,1,2)='" + BrnCode + "'").getResultList();
                Vector amtVector = (Vector) atmList.get(0);
                String atmIssue = amtVector.get(0).toString();

                pojo.setBranchCode(BrnCode);
                pojo.setBranchName(BranchName);
                pojo.setNoOfACOpen(Long.valueOf(acOpen));
                pojo.setNoOfACClosed(Long.valueOf(acClosed));
                pojo.setNoOfLoanAcOpen(Long.valueOf(loacacOpen));
                pojo.setNoOfLockerIssue(Long.valueOf(lokerIssue));
                pojo.setNoOfLockerSurrender(Long.valueOf(lokerSurrender));
                pojo.setSrNo(srNo);
                pojo.setAcOpenNetOff(Long.valueOf(netOffAc));
                pojo.setLockerIssueNetOff(Long.valueOf(lockerNetOff));
                pojo.setDepositAmt(depositAmt);
                pojo.setAtmIssue(atmIssue);

                String sod = "", eod = "";
                List eodList = em.createNativeQuery("select date_format(SodTime, '%d/%m/%Y %r'), date_format(EodTime, '%d/%m/%Y %r') from bankdays where  date between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and Brncode = '" + BrnCode + "' order by date").getResultList();
                if (!eodList.isEmpty()) {
                    for (int z = 0; z < eodList.size(); z++) {
                        Vector vec3 = (Vector) eodList.get(z);
                        sod = vec3.get(0) == null ? sod : (z == 0 ? vec3.get(0).toString() : sod.concat("\n").concat(vec3.get(0).toString()));
                        eod = vec3.get(1) == null ? eod : (z == 0 ? vec3.get(1).toString() : eod.concat("\n").concat(vec3.get(1).toString()));
                        pojo.setDayBegin(sod);
                        pojo.setDayEnd(eod);
                    }
                } else {
                    eodList = em.createNativeQuery("select date_format(Dbegn_DT, '%d/%m/%Y %r'), date_format(Dend1_DT, '%d/%m/%Y %r') from bankdays_hist where  dt between '" + ymdFormat.format(dmyFormat.parse(fromDt)) + "' and '" + ymdFormat.format(dmyFormat.parse(toDt)) + "' and Brncode = '" + BrnCode + "' order by dt").getResultList();
                    if (!eodList.isEmpty()) {
                        for (int z = 0; z < eodList.size(); z++) {
                            Vector vec3 = (Vector) eodList.get(z);
                            sod = vec3.get(0) == null ? sod : (z == 0 ? vec3.get(0).toString() : sod.concat("\n").concat(vec3.get(0).toString()));
                            eod = vec3.get(1) == null ? eod : (z == 0 ? vec3.get(1).toString() : eod.concat("\n").concat(vec3.get(1).toString()));
                            pojo.setDayBegin(sod);
                            pojo.setDayEnd(eod);
                        }
                    } else {
                        pojo.setDayBegin(sod);
                        pojo.setDayEnd(eod);
                    }
                }
                detailList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailList;
    }

    public List<TransactionDetailsPojo> getDetailImpsOutward(String process, String frDt, String toDt, String user, String brnCode, String mode) throws ApplicationException {
        List<TransactionDetailsPojo> deatilList = new ArrayList<TransactionDetailsPojo>();
        List result = new ArrayList<>();
        try {
            String statusQuery = "";
            if (mode.equalsIgnoreCase("S")) {
                statusQuery = " and Status ='S'";
            } else {
                statusQuery = " and Status<>'S'";
            }

            if (brnCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.ref_desc,b.beneficiaryname,b.txnamt,b.paymentdate,b.debitaccountno,b.creditaccountno,b.details, "
                        + "b.cmsbankrefno,b.utrno,b.reason,b.OutsidebankIfscCode,b.Status,b.UniqueCustomerRefNo,ifnull(CPSMS_Batch_No,'') RRN  from cbs_ref_rec_type a, neft_ow_details b "
                        + "where  a.ref_rec_no='009' and a.ref_code =  b.paymentType  and PaymentType='IMPS' and (CPSMS_MessageId is null or CPSMS_MessageId='') "
                        + "and dt between '" + frDt + "' and '" + toDt + "' " + statusQuery + " order by PaymentDate").getResultList();
            } else {
                result = em.createNativeQuery("select a.ref_desc,b.beneficiaryname,b.txnamt,b.paymentdate,b.debitaccountno,b.creditaccountno,b.details, "
                        + "b.cmsbankrefno,b.utrno,b.reason,b.OutsidebankIfscCode,b.Status,b.UniqueCustomerRefNo,ifnull(CPSMS_Batch_No,'') RRN  from cbs_ref_rec_type a, neft_ow_details b "
                        + "where  a.ref_rec_no='009' and a.ref_code =  b.paymentType  and PaymentType='IMPS' and (CPSMS_MessageId is null or CPSMS_MessageId='') "
                        + "and dt between '" + frDt + "' and '" + toDt + "' and OrgBrnid='" + brnCode + "' " + statusQuery + " order by PaymentDate").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    TransactionDetailsPojo pojo = new TransactionDetailsPojo();
                    Vector vector = (Vector) result.get(i);
                    pojo.setBeneficiaryName(vector.get(1).toString());
                    pojo.setTxnAmt(new BigDecimal(vector.get(2).toString()));
                    pojo.setPaymentDate(vector.get(3).toString());
                    pojo.setDebitAccountNo(vector.get(4).toString());
                    pojo.setCreditAccountNo(vector.get(5).toString());
                    pojo.setDetails(vector.get(6) != null ? vector.get(6).toString() : "");
                    pojo.setCmsBankRefNo(vector.get(7) != null ? vector.get(7).toString() : "");
                    pojo.setUtrNo(vector.get(8) != null ? vector.get(8).toString() : "");
                    pojo.setIfscCode(vector.get(10) != null ? vector.get(10).toString() : "");
                    pojo.setUniqueCustRefNo(vector.get(0).toString());
                    String status = vector.get(11).toString();
                    if (status.equalsIgnoreCase("S")) {
                        pojo.setReason("");
                    } else {
                        pojo.setReason(vector.get(9) != null ? vector.get(9).toString() : "");
                    }
                    pojo.setRrno(vector.get(13).toString());
                    deatilList.add(pojo);
                }
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return deatilList;
    }

    public List<IMPSTxnParameterPojo> getDetailImpsTxnParameter(String process, String mode, String frDt, String toDt, String user, String brcode) throws ApplicationException {
        List<IMPSTxnParameterPojo> detailList = new ArrayList<IMPSTxnParameterPojo>();
        try {
            String brncodeQuery = "";
            if (brcode.equalsIgnoreCase("0A")) {
                brncodeQuery = "";
            } else {
                if (process.equalsIgnoreCase("I")) {
                    brncodeQuery = "and substring(TO_ACCOUNT_NUMBER,1,2)='" + brcode + "'";
                } else {
                    brncodeQuery = "and substring(FROM_ACCOUNT_NUMBER,1,2)='" + brcode + "'";
                }
            }

            String procesStatusQuery = "";
            if (mode.equalsIgnoreCase("S")) {
                procesStatusQuery = "and IN_PROCESS_FLAG='S'";
            } else {
                procesStatusQuery = "and IN_PROCESS_FLAG<>'S'";
            }
            List result = new ArrayList<>();
            if (process.equalsIgnoreCase("I")) {
                result = em.createNativeQuery("select PROCESSING_CODE,REVERSAL_INDICATOR,AMOUNT,FROM_ACCOUNT_NUMBER,"
                        + "TO_ACCOUNT_NUMBER,REMITTER_NBIN,RRN,date_format(TRAN_DATE,'%d/%m/%Y'),date_format(IN_COMING_DATE,'%d/%m/%Y'),cast(TRSNO as unsigned) batchNo,IN_PROCESS_STATUS from "
                        + "imps_txn_parameter where PROCESSING_CODE = '43' and TXN_TYPE='48' and REVERSAL_INDICATOR='0'  "
                        + " " + procesStatusQuery + "   and cast(TRAN_DATE as date) between '" + frDt + "' and '" + toDt + "' " + brncodeQuery + "").getResultList();
            } else if (process.equalsIgnoreCase("O")) {
                result = em.createNativeQuery("select PROCESSING_CODE,REVERSAL_INDICATOR,AMOUNT,FROM_ACCOUNT_NUMBER,"
                        + "TO_ACCOUNT_NUMBER,REMITTER_NBIN,RRN,date_format(TRAN_DATE,'%d/%m/%Y'),date_format(IN_COMING_DATE,'%d/%m/%Y'),cast(TRSNO as unsigned) batchNo,IN_PROCESS_STATUS from "
                        + "imps_txn_parameter where PROCESSING_CODE = '42' and TXN_TYPE='48' and REVERSAL_INDICATOR='0'  "
                        + " " + procesStatusQuery + "   and cast(TRAN_DATE as date) between '" + frDt + "' and '" + toDt + "' " + brncodeQuery + "").getResultList();
            } else if (process.equalsIgnoreCase("R")) {
                result = em.createNativeQuery("select PROCESSING_CODE,REVERSAL_INDICATOR,AMOUNT,FROM_ACCOUNT_NUMBER,"
                        + "TO_ACCOUNT_NUMBER,REMITTER_NBIN,RRN,date_format(TRAN_DATE,'%d/%m/%Y'),date_format(IN_COMING_DATE,'%d/%m/%Y'),cast(TRSNO as unsigned) batchNo,IN_PROCESS_STATUS from "
                        + "imps_txn_parameter where PROCESSING_CODE = '42' and TXN_TYPE='48' and REVERSAL_INDICATOR='1'  "
                        + " " + procesStatusQuery + "   and cast(TRAN_DATE as date) between '" + frDt + "' and '" + toDt + "' " + brncodeQuery + "").getResultList();
            }
            if (result.isEmpty()) {
                throw new ApplicationException("Data does not exist. ");
            }
            int srno = 1;
            for (int i = 0; i < result.size(); i++) {
                Vector vec = (Vector) result.get(i);
                IMPSTxnParameterPojo pojo = new IMPSTxnParameterPojo();
                pojo.setSrNo(srno++);
                pojo.setReversalIndicator(vec.get(1).toString().trim());
                pojo.setAmount(new BigDecimal(vec.get(2).toString().trim()));
                pojo.setFromAcNo(vec.get(3).toString().trim());
                pojo.setToAcno(vec.get(4).toString().trim());
                pojo.setRemitterNbin(vec.get(5).toString().trim());
                pojo.setRrn(vec.get(6).toString().trim());
                String tranDate = vec.get(7).toString().trim() + "(" + (vec.get(8).toString().trim()) + ")";
                pojo.setTranDate(tranDate);
                pojo.setTrsBatch(vec.get(9).toString().trim());
                pojo.setStatus(vec.get(10).toString().trim());

                detailList.add(pojo);
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailList;
    }

    public List<TdActiveReportPojo> getDuplicateReceiptDetails(String brnCode, String frmDt, String toDt) throws ApplicationException {
        List<TdActiveReportPojo> detailList = new ArrayList<TdActiveReportPojo>();
        try {
            String brncodeQuery = "";
            if (brnCode.equalsIgnoreCase("0A")) {
                brncodeQuery = "";
            } else {
                brncodeQuery = " and c.curbrcode = '" + brnCode + "' ";
            }
            List result = new ArrayList<>();
            result = em.createNativeQuery(" select a.ACNO,c.CustName,a.VoucherNo,a.PrinAmt,a.ROI,date_format(a.FDDT,'%d/%m/%Y') FDdate,date_format(a.MatDt,'%d/%m/%Y') matDate, "
                    + " b.ReceiptNo BeforRtNo,a.ReceiptNo currentRtNo,ifnull(date_format(b.TranTime,'%d/%m/%Y'),'') DuplicateRtNoIssueDate "
                    + " from td_vouchmst a , td_vouchmst_duplicate b , td_accountmaster c "
                    + " where a.acno = b.acno and a.acno = c.acno and a.VoucherNo = b.VoucherNo " + brncodeQuery + ""
                    + " and ((date_format(b.TranTime,'%Y%m%d') between '" + frmDt + "' and '" + toDt + "') OR b.TranTime is null)").getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("Data Does Not Exist.");
            } else {
                for (int i = 0; i < result.size(); i++) {
                    Vector vec = (Vector) result.get(i);
                    TdActiveReportPojo pojo = new TdActiveReportPojo();
                    pojo.setAcNo(vec.get(0).toString());
                    pojo.setCustName(vec.get(1).toString());
                    pojo.setVouchNo(Float.parseFloat(vec.get(2).toString()));
                    pojo.setPrinAmt(Float.parseFloat(vec.get(3).toString()));
                    pojo.setRoi(Float.parseFloat(vec.get(4).toString()));
                    pojo.setFdDate(vec.get(5).toString());
                    pojo.setMatDate(vec.get(6).toString());
                    pojo.setReceiptNo(Float.parseFloat(vec.get(7).toString()));
                    pojo.setSeqno(Float.parseFloat(vec.get(8).toString()));
                    pojo.setCloseDt(vec.get(9).toString());
                    detailList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return detailList;
    }

    @Override
    public List<IMPSTxnParameterPojo> getImpsTxnData(String frDt, String toDt, String brcode) throws ApplicationException {
        List<IMPSTxnParameterPojo> dataList = new ArrayList<>();
        try {
            String brnCode = "";
            if (brcode.equalsIgnoreCase("0A")) {
                brnCode = "";
            } else {
                brnCode = "and curbrcode = '" + brcode + "'";
            }
            List list = em.createNativeQuery("select ib_request_id,user_id,debit_account_no,credit_account_no,amount,beneficiary_ifsc,beneficiary_name,"
                    + "beneficiary_mobile,custname,ifnull(batch_no,'') "
                    + "from ib_txn_request a,accountmaster b where a.debit_account_no = b.acno "
                    + "and date_format(request_date,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' " + brnCode + "").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    IMPSTxnParameterPojo pojo = new IMPSTxnParameterPojo();
                    pojo.setRequest_id(vtr.get(0).toString());
                    pojo.setUser_id(vtr.get(1).toString());
                    pojo.setDebit_account_no(vtr.get(2).toString());
                    pojo.setCredit_account_no(vtr.get(3).toString());
                    pojo.setAmount(new BigDecimal(vtr.get(4).toString()));
                    pojo.setBeneficiary_ifsc(vtr.get(5).toString());
                    pojo.setBeneficiary_name(vtr.get(6).toString());
                    pojo.setBeneficiary_mobile(vtr.get(7).toString());
                    pojo.setCustName(vtr.get(8).toString());
                    if (vtr.get(9).toString().equalsIgnoreCase("")) {
                        pojo.setStatus("Fail");
                    } else {
                        pojo.setStatus("Pass");
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

        return dataList;
    }

    @Override
    public List<NominationRegisterPojo> getNomineeDetails(String brnCode, String fromDate, String toDate, String reportType, String nomStatus) throws ApplicationException {
        List<NominationRegisterPojo> dataList = new ArrayList();
        List list = null;
        String brnCodeQuery = "";
        if (brnCode.equalsIgnoreCase("0A")) {
            brnCodeQuery = "";
        } else {
            if (nomStatus.equalsIgnoreCase("Y")) {
                brnCodeQuery = "and substring(b.acno,1,2)='" + brnCode + "'";
            } else {
                brnCodeQuery = "and substring(c.acno,1,2)='" + brnCode + "'";
            }

        }
        if (reportType.equalsIgnoreCase("1")) {
            if (nomStatus.equalsIgnoreCase("Y")) {
                list = em.createNativeQuery("select d.AcctType , a.customerid,b.acno,a.CustFullName ,ifnull(b.nom_reg_no,'') as nom_reg_no ,b.nomname,ifnull(b.nomadd,'') as nomadd, "
                        + " Date_format(b.nomdob,'%d/%m/%Y') as nomdob,ifnull(b.relation,'') as relation from cbs_customer_master_detail a,nom_details b, "
                        + " customerid c, accounttypemaster d,accountmaster e  where b.ACNo = c.acno and b.ACNo = e.acno and   a.customerid = c.CustId  "
                        + " and substring(c.acno,3,2)= d.AcctCode  and e.OpeningDt <='" + toDate + "' and (e.closingdate='' or ifnull(e.closingdate,'')='' or "
                        + " e.closingdate >'" + toDate + "')  and substring(b.acno,1,2) = e.curbrcode " + brnCodeQuery + " and Date_Format(b.trantime,'%Y%m%d') "
                        + " between '" + fromDate + "' and '" + toDate + "'").getResultList();
            } else {

                list = em.createNativeQuery("select d.AcctType , a.customerid,e.acno,a.CustFullName "
                        + "from cbs_customer_master_detail a, customerid c, accounttypemaster d,accountmaster e  "
                        + "where c.ACNo = e.acno "
                        + "and   a.customerid = c.CustId  "
                        + "and substring(c.acno,3,2)= d.AcctCode  and e.OpeningDt <='" + toDate + "' "
                        + "and (e.closingdate='' or ifnull(e.closingdate,'')='' or e.closingdate >'" + toDate + "')  "
                        + "and substring(c.acno,1,2) = e.curbrcode " + brnCodeQuery + " "
                        + "and e.acno not in(select acno from nom_details)").getResultList();
            }
        } else if (reportType.equalsIgnoreCase("2")) {

            list = em.createNativeQuery("select d.AcctType,e.customerid,b.acno,e.CustFullName ,b.nomination ,b.cabno,b.lockerno from lockeracmaster b,customerid c, "
                    + "accounttypemaster d,cbs_customer_master_detail e, accountmaster a where  c.acno=b.acno and c.acno=a.acno  "
                    + "and substring(c.acno,3,2)= d.AcctCode and c.CustId =cast(e.customerid as unsigned) and b.nomination <> '' "
                    + "and substring(c.acno,1,2) = b.brncode and substring(c.acno,1,2) = a.curbrcode " + brnCodeQuery + " and  a.OpeningDt <='" + toDate + "'  "
                    + "and (a.closingdate='' or ifnull(a.closingdate,'')='' or a.closingdate >'" + toDate + "')   "
                    + "and Date_Format(b.issuedt,'%Y%m%d') between '" + fromDate + "' and '" + toDate + "';").getResultList();
        }

        try {
            if (!list.isEmpty()) {

                if (reportType.equalsIgnoreCase("1")) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vec = (Vector) list.get(i);
                        NominationRegisterPojo nrp = new NominationRegisterPojo();
                        if (nomStatus.equalsIgnoreCase("Y")) {
                            nrp.setActype(vec.get(0).toString());
                            nrp.setCustomerId(vec.get(1).toString());
                            nrp.setAcno(vec.get(2).toString());
                            nrp.setAcnoHolderName(vec.get(3).toString());
                            nrp.setNomineeNo(vec.get(4).toString());
                            nrp.setNomName(vec.get(5).toString());
                            nrp.setNomAdd(vec.get(6).toString());
                            nrp.setNomDob(vec.get(7).toString());
                            nrp.setRelation(vec.get(8).toString());
                            dataList.add(nrp);
                        } else {
                            nrp.setActype(vec.get(0).toString());
                            nrp.setCustomerId(vec.get(1).toString());
                            nrp.setAcno(vec.get(2).toString());
                            nrp.setAcnoHolderName(vec.get(3).toString());
//                            nrp.setNomineeNo(vec.get(4).toString());
//                            nrp.setNomName(vec.get(5).toString());
//                            nrp.setNomAdd(vec.get(6).toString());
//                            nrp.setNomDob(vec.get(7).toString());
//                            nrp.setRelation(vec.get(8).toString());
                            dataList.add(nrp);
                        }
                    }
                } else if (reportType.equalsIgnoreCase("2")) {
                    for (int i = 0; i < list.size(); i++) {
                        Vector vec = (Vector) list.get(i);
                        NominationRegisterPojo nrp = new NominationRegisterPojo();
                        nrp.setActype(vec.get(0).toString());
                        nrp.setCustomerId(vec.get(1).toString());
                        nrp.setAcno(vec.get(2).toString());
                        nrp.setAcnoHolderName(vec.get(3).toString());
                        nrp.setNomName(vec.get(4).toString());
                        nrp.setNomineeNo(vec.get(5).toString());
                        nrp.setRelation(vec.get(6).toString());
                        dataList.add(nrp);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException();
        }
        return dataList;
    }
}
