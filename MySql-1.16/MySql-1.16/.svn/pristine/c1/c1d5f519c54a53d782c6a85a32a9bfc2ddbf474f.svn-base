/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.LoanIntCalcList;
import com.cbs.dto.RdInterestDTO;
import com.cbs.dto.report.AgentCommissionPojo;
import com.cbs.dto.report.DDSIntReportPojo;
import com.cbs.dto.report.DdsAccountExpiryPojo;
import com.cbs.dto.report.DdsTransactionGrid;
import com.cbs.dto.report.NpaAccStmPojo;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.dto.report.TdActiveReportPojo;
import com.cbs.dto.report.TdPeriodMaturityPojo;
import com.cbs.dto.report.UnclaimedAccountStatementPojo;
import com.cbs.dto.report.ho.DepositProvisionCalPojo;
import com.cbs.dto.report.ho.CashInAtmPojo;
import com.cbs.dto.report.ho.DepositDrLoanCrBalPojo;
import com.cbs.dto.report.ho.ReportProfilePojo;
import com.cbs.dto.report.ho.UserHistoryDto;
import javax.ejb.Stateless;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.dds.DDSManagementFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.facade.intcal.RDIntCalFacadeRemote;
import com.cbs.facade.intcal.SbIntCalcFacadeRemote;
import com.cbs.facade.other.AutoTermDepositRenewalRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.facade.td.TermDepositeCalculationManagementFacadeRemote;
import com.cbs.pojo.AadharDetailSummaryPojo;
import com.cbs.pojo.FolioStatement;
import com.cbs.pojo.GuaranteeOnLoanPojo;
import com.cbs.pojo.OverDueListPojo;
import com.cbs.pojo.PostDatedChequePojo;
import com.cbs.pojo.SavingIntRateChangePojo;
import com.cbs.pojo.VoucherPrintingPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.Spellar;
import java.math.BigDecimal;
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
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "DDSReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DDSReportFacade implements DDSReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    FtsPostingMgmtFacadeRemote fts;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    DDSManagementFacadeRemote dds;
    @EJB
    LoanInterestCalculationFacadeRemote loanRemote;
    @EJB
    SbIntCalcFacadeRemote sbRemote;
    @EJB
    TermDepositeCalculationManagementFacadeRemote tdCalcRemote;
    @EJB
    AutoTermDepositRenewalRemote autoRenewRemote;
    @EJB
    DDSManagementFacadeRemote beanRemote;
    @EJB
    LoanReportFacadeRemote loanRemoteFacade;
    @EJB
    RDIntCalFacadeRemote rdRemoteFacade;
    @EJB
    private AccountOpeningFacadeRemote acOpenFacadeRemote;
    @EJB
    private TdReceiptManagementFacadeRemote tdReceiptmgtRemote;
    @EJB
    OverDueReportFacadeRemote overDueReportFacade;
    @EJB
    private InterBranchTxnFacadeRemote ibtFacade;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd_1 = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat amtFormatter = new DecimalFormat("0.00");
    NumberFormat formatter = new DecimalFormat("#.##");

    @Override
    public List getAccountType() throws ApplicationException {
        List returnList = new ArrayList();
        try {
            returnList = em.createNativeQuery("select acctcode,acctType from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public List getAgentCode(String orgBrCode) throws ApplicationException {
        List returnList = new ArrayList();
        try {
            returnList = em.createNativeQuery("Select agCode,name From ddsagent where brncode = '" + orgBrCode + "' Order By agCode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    @Override
    public String getAgentName(String agCode, String orgBrCode) throws ApplicationException {
        String name = "";
        try {
            List nameLst = em.createNativeQuery("Select name From ddsagent where agcode = '" + agCode + "' and brncode='" + orgBrCode + "'").getResultList();
            Vector ele = (Vector) nameLst.get(0);
            name = ele.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return name;
    }

    @Override
    public List<DdsTransactionGrid> getDailyCollData(String userDate, String orgBrnCode, String agCode, String acType, String Ord) throws ApplicationException {
        List<DdsTransactionGrid> repDataList = new ArrayList<DdsTransactionGrid>();
        List dataList = new ArrayList();
        try {
            if (Ord.equalsIgnoreCase("receiptno")) {
                dataList = em.createNativeQuery("Select a.acno,b.custname,ifnull(a.cramt,0) 'cramt',a.dt,ifnull(a.ReceiptNo,'') 'receiptno' From "
                        + "ddstransaction a , accountmaster b Where b.acno = a.acno and a.dt = '" + userDate + "' and trantype=0 AND AUTH='Y' "
                        + "and substring(a.acno,11,2) = '" + agCode + "' and substring(a.acno,3,2) = '" + acType + "' and a.ty=0 and b.curbrcode = '"
                        + orgBrnCode + "' order by a.receiptno").getResultList();
            } else {
                dataList = em.createNativeQuery("Select a.acno,b.custname,ifnull(a.cramt,0) 'cramt',a.dt,ifnull(a.ReceiptNo,'') 'receiptno' From "
                        + "ddstransaction a , accountmaster b Where b.acno = a.acno and a.dt = '" + userDate + "' and trantype=0 AND AUTH='Y' "
                        + "and substring(a.acno,11,2) = '" + agCode + "' and substring(a.acno,3,2) = '" + acType + "' and a.ty=0 and b.curbrcode = '"
                        + orgBrnCode + "' order by a.acno").getResultList();
            }
            if (dataList.size() > 0) {
                for (int i = 0; i < dataList.size(); i++) {
                    DdsTransactionGrid ddsObject = new DdsTransactionGrid();
                    Vector element1 = (Vector) dataList.get(i);
                    ddsObject.setAccountNo(element1.get(0).toString());
                    ddsObject.setName(element1.get(1).toString());
                    ddsObject.setAmount(element1.get(2).toString());
                    ddsObject.setDate(element1.get(3).toString());
                    ddsObject.setReceiptNo(element1.get(4).toString());
                    repDataList.add(ddsObject);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return repDataList;
    }

    public List<AgentCommissionPojo> getAgentCommission(String frmdt, String todt, String branchCode) throws ApplicationException {
        List<AgentCommissionPojo> returnlist = new ArrayList<AgentCommissionPojo>();
        double agComm = 0d, agITax = 0d;
        double agSecDep = 0d, surCharge = 0d;
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            List rsList = new ArrayList();
            List dBalList = new ArrayList();
            List ComList = new ArrayList();
            String tranTypeList = fts.getCodeFromCbsParameterInfo("DDS-AGENT-COMM");

            if (branchCode.equalsIgnoreCase("0A")) {
                rsList = em.createNativeQuery("SELECT d.Agcode,d.name,d.brncode,d.agent_acno From ddsagent d where status='a' order by brncode").getResultList();
            } else {
                rsList = em.createNativeQuery("SELECT d.Agcode,d.name,d.brncode,d.agent_acno From ddsagent d where status='a' and brncode='" + branchCode + "'").getResultList();
            }
            if (rsList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }

            dBalList = em.createNativeQuery("select distinct ifnull(daybal,500) from dds_slab where applicable_date = (select max(applicable_date) from dds_slab where applicable_date <='" + frmdt + "')").getResultList();
            if (dBalList.isEmpty()) {
                throw new ApplicationException("Data does not exist in dds_slab table");
            }

            Vector agVec = (Vector) dBalList.get(0);
            double dBal = Double.parseDouble(agVec.get(0).toString()) * CbsUtil.dayDiff(ymd.parse(frmdt), ymd.parse(todt));

            for (int i = 0; i < rsList.size(); i++) {
                Vector agV = (Vector) rsList.get(i);
                String agCode = agV.get(0).toString();
                String agName = agV.get(1).toString();
                String brnCode = agV.get(2).toString();
                String branchName = common.getBranchNameByBrncode(brnCode);
                String acno = agV.get(3).toString();
                double agBal = 0, comAmt = 0, secAmt = 0, iTax1 = 0, iTax2 = 0, taxAmt = 0, netAmt = 0;
                double totalNetAmt = 0, totalCommision = 0, totalTaxAmt = 0;
//                List list = em.createNativeQuery("SELECT r.ACNO,SUM(CRAMT) FROM ddstransaction r,accountmaster a WHERE SUBSTRING(r.ACNO,11,2) = '" + agCode + "' and trantype in(" + tranTypeList + ") "
//                        + "and r.acno = a.acno and (ifnull(a.closingdate,'')=''or a.closingdate>'" + todt + "') "
//                        + "AND TY = 0 AND DT BETWEEN '" + frmdt + "' AND '" + todt + "' AND AUTH ='Y' AND SUBSTRING(r.ACNO,1,2) = '" + brnCode + "' GROUP BY r.ACNO ORDER BY r.ACNO").getResultList();
                List list = em.createNativeQuery("SELECT r.ACNO,SUM(CRAMT) FROM ddstransaction r,accountmaster a WHERE SUBSTRING(r.ACNO,11,2) = '" + agCode + "' and trantype in(" + tranTypeList + ") "
                        + "and r.acno = a.acno "
                        + "AND TY = 0 AND DT BETWEEN '" + frmdt + "' AND '" + todt + "' AND AUTH ='Y' AND SUBSTRING(r.ACNO,1,2) = '" + brnCode + "' GROUP BY r.ACNO ORDER BY r.ACNO").getResultList();
                if (!list.isEmpty()) {
                    for (int j = 0; j < list.size(); j++) {
                        Vector agVect = (Vector) list.get(j);
                        double crAmt = Double.parseDouble(agVect.get(1).toString());
                        // Change a/c to raghib
                        //                    if (crAmt > dBal) {
                        //                        crAmt = dBal;
                        //                    }
                        agBal = agBal + crAmt;
                    }

                    ComList = em.createNativeQuery("SELECT COMM_PERC,AGSECDEP,AGITAX,SURCHARGE FROM dds_slab where applicable_date = (select max(applicable_date) from dds_slab where applicable_date <='" + frmdt + "') and '" + agBal + "' between from_amt and to_amt").getResultList();
                    if (ComList.isEmpty()) {
                        throw new ApplicationException("Data does not exist in dds_slab table");
                    }
                    for (int k = 0; k < ComList.size(); k++) {
                        Vector agVd = (Vector) ComList.get(k);
                        if (agVd.get(0) != null) {
                            agComm = Double.parseDouble(agVd.get(0).toString());
                        } else {
                            agComm = 2.25;
                        }
                        if (agVd.get(1) != null) {
                            agSecDep = Double.parseDouble(agVd.get(1).toString());
                        } else {
                            agSecDep = 10;
                        }
                        if (agVd.get(2) != null) {
                            agITax = Double.parseDouble(agVd.get(2).toString());
                        } else {
                            agITax = 10.5;
                        }
                        if (agVd.get(3) != null) {
                            surCharge = Double.parseDouble(agVd.get(3).toString());
                        } else {
                            surCharge = 0.5;
                        }
                    }
                }
                comAmt = agBal * agComm;
                comAmt = comAmt / 100;
                comAmt = Math.round(comAmt);

                secAmt = comAmt * agSecDep;
                secAmt = secAmt / 100;
                secAmt = Math.round(secAmt);

                iTax1 = comAmt * agITax;
                iTax1 = iTax1 / 100;
                iTax1 = Math.round(iTax1);

                iTax2 = comAmt * surCharge;
                iTax2 = iTax2 / 100;
                iTax2 = Math.round(iTax2);

                taxAmt = iTax1 + iTax2;
                netAmt = comAmt - (secAmt + taxAmt);

                totalNetAmt = totalNetAmt + netAmt;
                totalCommision = totalCommision + comAmt;
                totalTaxAmt = totalTaxAmt + taxAmt;

                AgentCommissionPojo pojo = new AgentCommissionPojo();
                pojo.setAgBal(agBal);
                pojo.setAgCode(agCode);
                pojo.setAgName(agName);
                pojo.setNetAmt(netAmt);
                pojo.setSecAmt(secAmt);
                pojo.setSurCharge(surCharge);
                pojo.setTaxAmt(taxAmt);
                pojo.setComAmt(comAmt);
                pojo.setiTax(iTax2);
                pojo.setPerc(agComm);
                pojo.setBranch(brnCode);
                pojo.setAcno(acno);
                pojo.setBranchName(branchName);
                returnlist.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
        return returnlist;

    }

    public List<DdsAccountExpiryPojo> getDdsAccountExpiryDate(String branchCode, String agCode, String tillDate) throws ApplicationException {
        List<DdsAccountExpiryPojo> returnlist = new ArrayList<DdsAccountExpiryPojo>();
        String expiryDate = "";
        List result = null;
        String name = null;
        try {
            if (branchCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.acno,a.custName,date_format(a.rdmatdate,'%d/%m/%Y') as rdmatdate from accountmaster a,ddsagent d "
                        + "where substring(a.acno,3,2) in ( select acctcode from accounttypemaster where acctnature = '"
                        + CbsConstant.DEPOSIT_SC + "') and a.accstatus <> 9 and a.rdmatdate <= '" + tillDate + "'group by  a.acno,a.custName,a.rdmatdate").getResultList();

            } else if (branchCode.equalsIgnoreCase(branchCode) && agCode.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select a.acno,a.custName,date_format(a.rdmatdate,'%d/%m/%Y') as rdmatdate from accountmaster a,ddsagent d "
                        + "where substring(acno,1,2)='" + branchCode + "'and substring(a.acno,3,2) in ( select acctcode from accounttypemaster "
                        + "where acctnature = '" + CbsConstant.DEPOSIT_SC + "') and a.accstatus <> 9 and a.rdmatdate <= '" + tillDate + "'group by  a.acno,a.custName,a.rdmatdate").getResultList();

            } else {
                List nameLst = em.createNativeQuery("Select name From ddsagent where agcode = '" + agCode + "' and brncode='" + branchCode + "'").getResultList();
                if (nameLst.isEmpty()) {
                    throw new ApplicationException("Data does not exit in DDS AGENT.");
                }
                Vector ele = (Vector) nameLst.get(0);
                name = ele.get(0).toString();
                result = em.createNativeQuery("select a.acno,a.custName,date_format(a.rdmatdate,'%d/%m/%Y') as rdmatdate from accountmaster a,ddsagent d "
                        + "where substring(acno,1,2)='" + branchCode + "' and substring(a.acno,11,2)='" + agCode + "' and substring(a.acno,3,2) in "
                        + "( select acctcode from accounttypemaster where acctnature = '" + CbsConstant.DEPOSIT_SC + "') and a.accstatus <> 9 "
                        + "and a.rdmatdate <= '" + tillDate + "'group by  a.acno,a.custName,a.rdmatdate").getResultList();

            }
            if (result.isEmpty()) {
                throw new ApplicationException("Data does not exit");
            }
            for (int i = 0; i < result.size(); i++) {
                Vector dv = (Vector) result.get(i);
                DdsAccountExpiryPojo pojo = new DdsAccountExpiryPojo();
                String acNo = dv.get(0).toString();
                String custName = dv.get(1).toString();
                if (dv.get(2) != null) {
                    expiryDate = dv.get(2).toString();
                } else {
                    expiryDate = "";
                }
                pojo.setAcNo(acNo);
                pojo.setAgCode(agCode);
                pojo.setCustName(custName);
                pojo.setDateOfExp(expiryDate);
                pojo.setAgName(name);
                returnlist.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getLocalizedMessage());
        }
        return returnlist;
    }

    public List getAgentCodeHo() throws ApplicationException {
        try {
            return em.createNativeQuery("select agcode,name from ddsagent group by agcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<DDSIntReportPojo> getDDSIntData(String acType, String frmdt, String todt, String branchCode) throws ApplicationException {
        List<DDSIntReportPojo> dataList = new ArrayList<DDSIntReportPojo>();
        List result1 = new ArrayList();
        try {
            String acctnature = fts.getAcNatureByCode(acType);
            if (acctnature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                result1 = em.createNativeQuery("select a.acno,a.custname,d.interest,date_format(d.dt,'%d/%m/%Y') as date from dds_interesthistory d,accountmaster a  "
                        + "where a.curbrcode='" + branchCode + "' and dt between '" + frmdt + "' and '" + todt + "'and a.accttype = '" + acType + "'"
                        + "and d.acno = a.acno order by acno,dt").getResultList();
            } else {
                result1 = em.createNativeQuery("select a.acno,a.custname,r.interest,date_format(dt,'%d/%m/%Y') as date from rd_interesthistory r,accountmaster a  "
                        + "where a.curbrcode='" + branchCode + "' and r.dt between '" + frmdt + "' and '" + todt + "'and a.accttype = '" + acType + "'"
                        + "and r.acno = a.acno order by a.acno,r.dt").getResultList();
            }

            if (!result1.isEmpty()) {
                for (int i = 0; i < result1.size(); i++) {
                    Vector ele = (Vector) result1.get(i);
                    DDSIntReportPojo pojo = new DDSIntReportPojo();
                    pojo.setAcNo(ele.get(0).toString());
                    pojo.setCustName(ele.get(1).toString());
                    pojo.setInterest(Double.parseDouble(ele.get(2).toString()));
                    pojo.setDate(ele.get(3).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;

    }

    public List getAcodeRdds() throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature in('" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.RECURRING_AC + "')").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getLoanAndDepositNatures() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctnature from accounttypemaster "
                    + "where acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "',"
                    + "'" + CbsConstant.FIXED_AC + "','" + CbsConstant.MS_AC + "',"
                    + "'" + CbsConstant.RECURRING_AC + "','" + CbsConstant.CURRENT_AC + "') "
                    + "order by acctnature").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getAccountCodeByNature(String nature) throws ApplicationException {
        try {
            if (nature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                return em.createNativeQuery("select acctcode from accounttypemaster where "
                        + "acctnature='" + nature + "' and accttype not "
                        + "in('" + CbsConstant.CURRENT_AC + "') order by acctcode").getResultList();
            } else {
                return em.createNativeQuery("select acctcode from accounttypemaster where "
                        + "acctnature='" + nature + "' order by acctcode").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<ReportProfilePojo> getSummaryReportProfile(String brCode, String classification, String actNature, String acctcode,
            String reportBasedOn, double from, double to, double noOfSlab, String asOnDt, String reportBase) throws ApplicationException {
        List<ReportProfilePojo> dataList = new ArrayList<ReportProfilePojo>();
        List intTblList = new ArrayList();
        List accNatureList;
        //Integer repCode = 0;
        try {
            if (actNature.equalsIgnoreCase("ALL")) {
                accNatureList = em.createNativeQuery("select distinct(acctnature) from accounttypemaster "
                        + "where crdbflag in(" + classification + ") and acctnature "
                        + "not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
            } else {
                accNatureList = em.createNativeQuery("select distinct(acctnature) from accounttypemaster "
                        + "where acctnature='" + actNature + "' and crdbflag in(" + classification + ") and acctnature "
                        + "not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
            }
            for (int l = 0; l < accNatureList.size(); l++) {
                Vector element = (Vector) accNatureList.get(l);
                actNature = element.get(0).toString();
                String subQuery = "";
                if (brCode.equalsIgnoreCase("0A")) {
                    subQuery = "";
                } else {
                    subQuery = "curbrcode = '" + brCode + "' and";
                }

                String subFdQuery = "";
                if (brCode.equalsIgnoreCase("0A")) {
                    subFdQuery = "";
                } else {
                    subFdQuery = "substring(acno,1,2) = '" + brCode + "' and";
                }

                String acCodeQuery = "";
                if (acctcode.equalsIgnoreCase("All")) {
                    acCodeQuery = "select acctcode from accounttypemaster where "
                            + "crdbflag in(" + classification + ") and acctnature='" + actNature + "' order by acctcode";
                } else {
                    acCodeQuery = "select acctcode from accounttypemaster where acctcode in('" + acctcode + "')";
                }
                List acctCodeList = em.createNativeQuery(acCodeQuery).getResultList();
                if (acctCodeList.isEmpty()) {
                    throw new ApplicationException("There is no data in accounttype master.");
                }
                Map<Integer, String> slabMap = getSlabMap(from, to, noOfSlab);
                for (int i = 1; i <= noOfSlab; i++) {
                    String value = slabMap.get(i);
                    String[] t = value.split("-");
                    double frRange = Double.parseDouble(t[0]);
                    double toRange = Double.parseDouble(t[1]);

                    for (int k = 0; k < acctCodeList.size(); k++) {
                        Vector vtrAccode = (Vector) acctCodeList.get(k);
                        String acType = vtrAccode.get(0).toString();

                        List result = null;
                        if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                            result = em.createNativeQuery("select acno,intdeposit,frperiod,toperiod,balance from "
                                    + "(select a.acno,a.intdeposit,a.openingdt as frperiod,date_format(a.rdmatdate,'%Y%m%d') as "
                                    + "toperiod,cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance "
                                    + "from accountmaster a,rdrecon r where " + subQuery + " a.acno=r.acno and "
                                    + "a.accttype = '" + acType + "' and (a.closingdate is null or a.closingdate='' or "
                                    + "a.closingdate > date_format('" + asOnDt + "','%Y%m%d')) and r.dt<='" + asOnDt + "' and r.auth='Y' "
                                    + "group by a.acno) aa group by aa.acno").getResultList();
                        } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                            String drCrQuery = "";
                            if (classification.contains("C")) {
                                drCrQuery = "having cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2))>=0";
                            } else {
                                drCrQuery = "having cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2))<0";
                            }
                            result = em.createNativeQuery("select acno,roi,frperiod,toperiod,balance from "
                                    + "(select a.acno,la.roi,a.openingdt as frperiod,date_format(date_add(openingdt, "
                                    + "interval ifnull(loan_pd_month,0) month),'%Y%m%d') as toperiod,"
                                    + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as "
                                    + "balance from accountmaster a,loan_appparameter la,cbs_loan_acc_mast_sec ls,ca_recon r "
                                    + "where " + subQuery + " accttype = '" + acType + "' and a.acno = r.acno and "
                                    + "r.dt<='" + asOnDt + "' and r.auth='Y' and (closingdate is null or closingdate='' or "
                                    + "closingdate > date_format('" + asOnDt + "','%Y%m%d'))and a.acno = la.acno and a.acno = ls.acno "
                                    + "group by a.acno " + drCrQuery + ") aa group by aa.acno").getResultList();
                        } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                            result = em.createNativeQuery("select acno,roi,frperiod,toperiod,balance from "
                                    + "(select a.acno,la.roi,a.openingdt as frperiod,date_format(date_add(openingdt, "
                                    + "interval ifnull(loan_pd_month,0) month),'%Y%m%d') as toperiod,"
                                    + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance "
                                    + "from accountmaster a,loan_appparameter la,cbs_loan_acc_mast_sec ls,loan_recon r "
                                    + "where " + subQuery + " accttype = '" + acType + "' and a.acno = r.acno and "
                                    + "r.dt<='" + asOnDt + "' and r.auth='Y' and (closingdate is null or closingdate='' or "
                                    + "closingdate > date_format('" + asOnDt + "','%Y%m%d'))and a.acno = la.acno "
                                    + "and a.acno = ls.acno group by a.acno) aa group by aa.acno").getResultList();
                        } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            //                        result = em.createNativeQuery("select acno,roi,frperiod,toperiod,balance from "
                            //                                + " (SELECT t.ACNO as acno, a.CUSTNAME as custname, v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as "
                            //                                + " toperiod,cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) as balance  FROM td_recon t, td_accountmaster a,  td_vouchmst v"
                            //                                + " where " + subQuery + " a.acno = t.acno  and a.acno = v.acno and a.accttype = '" + acType + "' "
                            //                                + " AND t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') and t.auth='Y'  and (v.ClDt is null or v.ClDt='' or "
                            //                                + " v.ClDt > date_format('" + asOnDt + "','%Y-%m-%d'))  GROUP BY a.ACNO "
                            //                                + " HAVING SUM(t.CRAMT)-SUM(t.DRAMT) <> 0 ORDER BY t.ACNO) aa "
                            //                                + " group by acno,roi,frperiod,toperiod "
                            //                                + " union "
                            //                                + " select b.acno,0,b.mdt,b.tdt,b.amt from (select r.acno as acno,0,date_format(max(dt),'%Y%m%d') as mdt,date_format('" + asOnDt + "','%Y%m%d') as tdt,cast(sum(cramt-dramt) "
                            //                                + " as decimal(25,2)) as amt from td_recon r, (select distinct acno from td_vouchmst where " + subFdQuery + " "
                            //                                + " substring(acno,3,2) = '" + acType + "' and acno not in ( select distinct acno from td_vouchmst where "
                            //                                + " " + subFdQuery + " substring(acno,3,2) = '" + acType + "' and status ='A') and cldt<=date_format('" + asOnDt + "','%Y-%m-%d')) t where t.acno = r.acno and "
                            //                                + " r.closeflag is null and r.trantype <>27 group by r.acno having sum(cramt-dramt) <>0) b where b.mdt <= date_format('" + asOnDt + "','%Y%m%d')").getResultList();
                            //
                            result = em.createNativeQuery("select aa.acno,v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as toperiod,aa.balance from "
                                    + "(SELECT t.ACNO as acno, a.CUSTNAME as custname, cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) as balance  FROM "
                                    + "td_recon t, td_accountmaster a "
                                    + "where " + subQuery + " a.acno = t.acno  and a.accttype  in(" + acType + ") and (closingdate is null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) "
                                    + "AND t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL and t.TranType <> 27   GROUP BY a.ACNO "
                                    + "/*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/ ORDER BY t.ACNO) aa left join td_vouchmst v on aa.acno = v.acno "
                                    + "where (v.ClDt is null or v.ClDt='' or v.ClDt > date_format('" + asOnDt + "','%Y-%m-%d'))"
                                    + "group by aa.acno ").getResultList();
//                            result = em.createNativeQuery("select acno,roi,frperiod,toperiod,balance from "
//                                    + " (SELECT t.ACNO as acno, a.CUSTNAME as custname, v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as "
//                                    + " toperiod,cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) as balance  FROM td_recon t, td_accountmaster a,  td_vouchmst v"
//                                    + " where " + subQuery + " a.acno = t.acno  and a.acno = v.acno and a.accttype  in(" + acType + ") "
//                                    + " AND t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL and t.TranType <> 27 and (v.ClDt is null or v.ClDt='' or "
//                                    + " v.ClDt > date_format('" + asOnDt + "','%Y-%m-%d'))  GROUP BY a.ACNO "
//                                    + " /*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/ ORDER BY t.ACNO) aa "
//                                    + " group by acno,roi,frperiod,toperiod").getResultList();

                            //                        result = em.createNativeQuery("select acno,roi,frperiod,toperiod,balance from "
                            //                                + "(select a.acno,v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,"
                            //                                + "date_format(v.matdt,'%Y%m%d') as toperiod,"
                            //                                + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as "
                            //                                + "balance from td_accountmaster a,td_vouchmst v,td_recon r where " + subQuery + " v.status<>'C' and a.accttype = '" + acType + "' and a.acno=r.acno and "
                            //                                + "r.dt<='" + asOnDt + "' and r.auth='Y' and closeflag is null and (a.closingdate is null or a.closingdate='' or "
                            //                                + "a.closingdate > date_format('" + asOnDt + "','%Y%m%d'))and a.acno = v.acno group "
                            //                                + "by a.acno,v.roi,v.fddt,v.matdt having sum(r.cramt)-sum(r.dramt) <> 0) aa group by aa.acno,aa.roi,"
                            //                                + "aa.frperiod,aa.toperiod").getResultList();

                        } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            result = em.createNativeQuery("select acno,intdeposit,frperiod,frperiod,balance,orgncode from "
                                    + "(select a.acno,a.intdeposit,a.openingdt as frperiod,"
                                    + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance,orgncode "
                                    + "from accountmaster a,recon r where " + subQuery + " a.acno = r.acno and "
                                    + "r.dt<='" + asOnDt + "' and r.auth='Y' and accttype = '" + acType + "' and (closingdate is "
                                    + "null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) group by a.acno) "
                                    + "aa group by acno").getResultList();

                            intTblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                                    + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(" + acType + ")").getResultList();

                        } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                            result = em.createNativeQuery("select acno,intdeposit,frperiod,frperiod,balance from "
                                    + "(select a.acno,a.intdeposit,a.openingdt as frperiod,date_format(a.rdmatdate,'%Y%m%d') as "
                                    + "toperiod,cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance from "
                                    + "accountmaster a,ddstransaction r where " + subQuery + " a.acno = r.acno and "
                                    + "r.dt<='" + asOnDt + "' and r.auth='Y' and accttype = '" + acType + "' and (closingdate is null or "
                                    + "closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) group by a.acno) "
                                    + "aa group by acno").getResultList();

                            //                        intTblList = em.createNativeQuery("Select code from parameterinfo_report where "
                            //                                + "reportname='DDSINTFLAG'").getResultList();
                            //                        if (intTblList.size() > 0) {
                            //                            Vector rep = (Vector) intTblList.get(0);
                            //                            repCode = Integer.parseInt(rep.get(0).toString());
                            //                        }
                        }

                        Double savingCommonRoi = 0.0;
                        Map<String, Double> savingIntMap = new HashMap<String, Double>();
                        if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                            for (int z = 0; z < intTblList.size(); z++) {
                                Vector intVec = (Vector) intTblList.get(z);
                                String intAcType = intVec.get(0).toString();
                                String intTblCode = intVec.get(1).toString();

                                List<SavingIntRateChangePojo> intSlabList = sbRemote.getSavingRoiChangeDetail(intTblCode, asOnDt, asOnDt);
                                SavingIntRateChangePojo obj = intSlabList.get(0);
                                savingCommonRoi = obj.getRoi();

                                savingIntMap.put(intAcType, savingCommonRoi);
                            }
                        }
                        //                    else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) && repCode != 1) {
                        //                        for (int z = 0; z < acctCodeList.size(); z++) {
                        //                            Vector intVec = (Vector) acctCodeList.get(z);
                        //                            String intAcType = intVec.get(0).toString();
                        //                            
                        //                            List dsRoiList = em.createNativeQuery("select roi from ddsconditions "
                        //                                    + "where actype ='" + intAcType + "'").getResultList();
                        //                            if (dsRoiList.isEmpty()) {
                        //                                throw new ApplicationException("Roi Date does not exist for A/c Type:" + intAcType);
                        //                            }
                        //                            Vector dsRoiVec = (Vector) dsRoiList.get(0);
                        //                            savingCommonRoi = Double.parseDouble(dsRoiVec.get(0).toString());
                        //                            
                        //                            savingIntMap.put(intAcType, savingCommonRoi);
                        //                        }
                        //                    }

                        ReportProfilePojo pojo = new ReportProfilePojo();
                        Integer totalNoOfAc = 0;
                        BigDecimal totalOutstand = new BigDecimal("0");
                        long period = 0;
                        for (int j = 0; j < result.size(); j++) {
                            Vector vtr = (Vector) result.get(j);
                            String acno = vtr.get(0).toString();
                            String froPeriod = vtr.get(2).toString();
                            String tooPeriod = vtr.get(3).toString();
                            //double balance = Double.parseDouble(amtFormatter.format(Double.parseDouble(vtr.get(4).toString())));
                            double balance = 0d, roi = 0.0;
                            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                List roiList = em.createNativeQuery("SELECT  v.acno, v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as toperiod FROM td_vouchmst v "
                                        + " WHERE v.ACNO = '" + acno + "' and Matdt = (SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + asOnDt + "') FROM td_vouchmst "
                                        + " WHERE ACNO = '" + acno + "' /*and (cldt > '" + asOnDt + "' or cldt is null)*/ and td_madedt <='" + asOnDt + "')").getResultList();
                                if (!roiList.isEmpty()) {
                                    Vector roiVect = (Vector) roiList.get(0);
                                    roi = Double.parseDouble(roiVect.get(1).toString());
                                    froPeriod = roiVect.get(2).toString();
                                    tooPeriod = roiVect.get(3).toString();
                                } else {
                                    roi = Double.parseDouble(vtr.get(2).toString());
                                }
//                                List balList = em.createNativeQuery("SELECT cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2))  FROM td_recon t "
//                                        + " where t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') "
//                                        + " and t.auth='Y' and t.acno = '" + acno + "' AND t.CLOSEFLAG IS NULL "
//                                        + " GROUP BY t.ACNO /*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/").getResultList();
//                                Vector balv = (Vector) balList.get(0);
//                                balance = Double.parseDouble(balv.get(0).toString());
                            }
                            balance = Double.parseDouble(amtFormatter.format(Double.parseDouble(vtr.get(4).toString())));

                            List sancAmtList = null;
                            double sancAmt = 0d;
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                sancAmtList = em.createNativeQuery("select ifnull(ODLimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
                            } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                sancAmtList = em.createNativeQuery("select ifnull(Sanctionlimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
                            }
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                                    || actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                    || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {

                                Vector ele = (Vector) sancAmtList.get(0);
                                sancAmt = Double.parseDouble(ele.get(0).toString());
                            }
                            if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                    || actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                roi = Double.parseDouble(loanRemote.getRoiLoanAccount(balance, asOnDt, acno));
                            } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                double acPrefDr = 0.0, acPrefCr = 0.0;
                                //List orgnList = em.createNativeQuery("select orgncode from accountmaster where "
                                //        + "acno='" + acno + "'").getResultList();
                                // Vector orgnVec = (Vector) orgnList.get(0);
                                int orgnCode = Integer.parseInt(vtr.get(5).toString());
                                if (reportBasedOn.equalsIgnoreCase("R") && orgnCode == 16) {
                                    List orgnList = em.createNativeQuery("select a.ac_pref_dr,a.acc_pref_cr from "
                                            + "cbs_acc_int_rate_details a, cbs_loan_acc_mast_sec b where  a.acno = b.acno "
                                            + "and a.acno = '" + acno + "' and a.eff_frm_dt <= '" + asOnDt + "' and "
                                            + "a.ac_int_ver_no=(select max(c.ac_int_ver_no) from cbs_acc_int_rate_details c "
                                            + "where c.acno='" + acno + "' and c.eff_frm_dt <= '" + asOnDt + "')").getResultList();

                                    if (orgnList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acno);
                                    }
                                    Vector intTableCodeVect = (Vector) orgnList.get(0);
                                    acPrefDr = Double.parseDouble(intTableCodeVect.get(0).toString());
                                    acPrefCr = Double.parseDouble(intTableCodeVect.get(1).toString());
                                    roi = savingIntMap.get(acno.substring(2, 4)) + acPrefCr - acPrefDr;
                                } else {
                                    roi = savingIntMap.get(acno.substring(2, 4));
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                //                            if (repCode != 1) {
                                //                                roi = savingIntMap.get(acno.substring(2, 4));
                                //                            } else {
                                roi = Double.parseDouble(vtr.get(1).toString());
                                //                            }
                            } else {
                                roi = Double.parseDouble(vtr.get(1).toString());
                            }
                            period = CbsUtil.dayDiff(ymd.parse(froPeriod), ymd.parse(tooPeriod));

                            if (reportBasedOn.equalsIgnoreCase("R")) {
                                if (roi >= frRange && roi < toRange) {
                                    totalNoOfAc += 1;
                                    totalOutstand = totalOutstand.add(new BigDecimal(balance));
                                }
                            } else if (reportBasedOn.equalsIgnoreCase("A")) {

                                if (reportBase.equalsIgnoreCase("Ob")) {
                                    if (Math.abs(balance) >= frRange && Math.abs(balance) < toRange) {
//                                        System.out.println("Account number is :" + acno);
                                        totalNoOfAc += 1;
                                        totalOutstand = totalOutstand.add(new BigDecimal(balance));
                                    }
                                } else {
                                    if (Math.abs(sancAmt) >= frRange && Math.abs(sancAmt) < toRange) {
                                        totalNoOfAc += 1;
                                        totalOutstand = totalOutstand.add(new BigDecimal(Math.abs(sancAmt)));
                                    }
                                }
                            } else if (reportBasedOn.equalsIgnoreCase("P")) {
                                if (period >= frRange && period < toRange) {
                                    totalNoOfAc += 1;
                                    totalOutstand = totalOutstand.add(new BigDecimal(balance));
                                }
                            }
                        }
                        if (totalNoOfAc != 0) {
                            if (reportBasedOn.equalsIgnoreCase("R")) {
                                pojo.setSlabType("%Roi");
                            } else if (reportBasedOn.equalsIgnoreCase("A")) {
                                pojo.setSlabType("Rs");
                            } else if (reportBasedOn.equalsIgnoreCase("P")) {
                                pojo.setSlabType("Days");
                            }
                            //pojo.setSlabDescription(value);
                            pojo.setPeriod(String.valueOf(period));
                            pojo.setSlabDescription(amtFormatter.format(frRange) + "-" + amtFormatter.format(toRange - 0.01));
                            pojo.setAcType(acType);
                            pojo.setAcCodeDesc(common.getAcctDecription(acType));
                            pojo.setNoOfAc(totalNoOfAc);
                            pojo.setOutstanding(new BigDecimal(amtFormatter.format(totalOutstand.doubleValue())));
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ReportProfilePojo> getDetailReportProfile(String brCode, String classification, String actNature, String acctcode,
            String reportBasedOn, double from, double to, double noOfSlab, String asOnDt, String reportBase) throws ApplicationException {
        List<ReportProfilePojo> dataList = new ArrayList<ReportProfilePojo>();
        List result = new ArrayList();
        List accNatureList;
        List intTblList = new ArrayList();
        //Integer repCode = 0;
        try {
            if (actNature.equalsIgnoreCase("ALL")) {
                accNatureList = em.createNativeQuery("select distinct(acctnature) from accounttypemaster "
                        + "where crdbflag in(" + classification + ") and acctnature "
                        + "not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
            } else {
                accNatureList = em.createNativeQuery("select distinct(acctnature) from accounttypemaster "
                        + "where acctnature='" + actNature + "' and crdbflag in(" + classification + ") and acctnature "
                        + "not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
            }
            for (int l = 0; l < accNatureList.size(); l++) {
                Vector element = (Vector) accNatureList.get(l);
                actNature = element.get(0).toString();
                String acCodeQuery = "";
                if (acctcode.equalsIgnoreCase("All")) {
                    acCodeQuery = "select acctcode from accounttypemaster where "
                            + "crdbflag in(" + classification + ") and acctnature='" + actNature + "' order by acctcode";
                } else {
                    acCodeQuery = "select acctcode from accounttypemaster where acctcode in('" + acctcode + "')";
                }
                List acctCodeList = em.createNativeQuery(acCodeQuery).getResultList();
                if (acctCodeList.isEmpty()) {
                    throw new ApplicationException("There is no data in accounttype master.");
                }
                String subQuery = "";
                if (brCode.equalsIgnoreCase("0A")) {
                    subQuery = "";
                } else {
                    subQuery = "curbrcode = '" + brCode + "' and";
                }

                if (actNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    result = em.createNativeQuery("select acno,custname,intdeposit,frperiod,toperiod,balance from "
                            + "(select a.acno,a.custname,a.intdeposit,a.openingdt as frperiod,date_format(a.rdmatdate,'%Y%m%d') as "
                            + "toperiod,cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance from "
                            + "accountmaster a,rdrecon r where " + subQuery + " a.acno = r.acno and a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and "
                            + "r.dt<='" + asOnDt + "' and r.auth='Y' and accttype in(" + acCodeQuery + ") and "
                            + "(closingdate is null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) "
                            + "group by a.acno) aa group by acno").getResultList();
                } else if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    String drCrQuery = "";
                    if (classification.contains("C")) {
                        drCrQuery = "having cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2))>=0";
                    } else {
                        drCrQuery = "having cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2))<0";
                    }
                    result = em.createNativeQuery("select acno,custname,roi,frperiod,toperiod,balance from "
                            + "(select a.acno,a.custname,la.roi,a.openingdt as frperiod,date_format(date_add(openingdt, "
                            + "interval ifnull(loan_pd_month,0) month),'%Y%m%d') as toperiod,"
                            + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance "
                            + "from accountmaster a, loan_appparameter la,cbs_loan_acc_mast_sec ls,ca_recon r "
                            + "where " + subQuery + " accttype in(" + acCodeQuery + ") and  a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and (closingdate is null or "
                            + "closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d'))and a.acno = la.acno and "
                            + "a.acno = ls.acno and a.acno=r.acno and r.dt<='" + asOnDt + "' and r.auth='Y' group by a.acno " + drCrQuery
                            + " ) aa group by acno ").getResultList();

                } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    result = em.createNativeQuery("select acno,custname,roi,frperiod,toperiod,balance from "
                            + "(select a.acno,a.custname,la.roi,a.openingdt as frperiod,date_format(date_add(openingdt, "
                            + "interval ifnull(loan_pd_month,0) month),'%Y%m%d') as toperiod,"
                            + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance "
                            + "from accountmaster a, loan_appparameter la,cbs_loan_acc_mast_sec ls,loan_recon r "
                            + "where " + subQuery + " accttype in(" + acCodeQuery + ") and  a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and (closingdate is "
                            + "null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d'))and a.acno = la.acno and a.acno = ls.acno "
                            + "and a.acno=r.acno and r.dt<='" + asOnDt + "' and r.auth='Y' group by a.acno) aa "
                            + "group by acno").getResultList();
                } else if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                    result = em.createNativeQuery("select acno,custname,roi,frperiod,toperiod,balance from "
//                            + " (SELECT t.ACNO as acno, a.CUSTNAME as custname, v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as "
//                            + " toperiod,cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) as balance  FROM td_recon t, td_accountmaster a,  td_vouchmst v"
//                            + " where " + subQuery + " a.acno = t.acno  and a.acno = v.acno and a.accttype  in(" + acCodeQuery + ") "
//                            + " AND t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL and t.TranType <> 27 and (v.ClDt is null or v.ClDt='' or "
//                            + " v.ClDt > date_format('" + asOnDt + "','%Y-%m-%d'))  GROUP BY a.ACNO "
//                            + " /*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/ ORDER BY t.ACNO) aa "
//                            + " group by acno,roi,frperiod,toperiod").getResultList();

                    result = em.createNativeQuery("select aa.acno,aa.custname,v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as toperiod,aa.balance from "
                            + "(SELECT t.ACNO as acno, a.CUSTNAME as custname, cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) as balance  FROM "
                            + "td_recon t, td_accountmaster a "
                            + "where " + subQuery + " a.acno = t.acno  and a.accttype  in(" + acCodeQuery + ") and  a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and (closingdate is null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) "
                            + "AND t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') and t.auth='Y' AND t.CLOSEFLAG IS NULL and t.TranType <> 27   GROUP BY a.ACNO "
                            + "/*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/ ORDER BY t.ACNO) aa left join td_vouchmst v on aa.acno = v.acno "
                            + " where (v.ClDt is null or v.ClDt='' or v.ClDt > date_format('" + asOnDt + "','%Y-%m-%d'))"
                            + "group by aa.acno ").getResultList();
                    //                result = em.createNativeQuery("select acno,custname,roi,frperiod,toperiod,balance from "
                    //                        + "(select a.acno,a.custname,v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as "
                    //                        + "toperiod,cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance from "
                    //                        + "td_accountmaster a, td_vouchmst v,td_recon r where " + subQuery + " a.accttype "
                    //                        + "in(" + acCodeQuery + ") and (a.closingdate is null or a.closingdate='' or "
                    //                        + "a.closingdate > date_format('" + asOnDt + "','%Y%m%d')) and a.acno = v.acno and v.status<>'C' and "
                    //                        + "a.acno=r.acno and r.dt<='" + asOnDt + "' and r.auth='Y'and closeflag is null group by a.acno,v.roi,v.fddt,v.matdt having sum(r.cramt)-sum(r.dramt) <> 0) aa "
                    //                        + "group by acno,roi,frperiod,toperiod").getResultList();
                } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    result = em.createNativeQuery("select acno,custname,intdeposit,frperiod,frperiod,balance,orgncode,interest_table_code from "
                            + "(select a.acno,a.custname,a.intdeposit,a.openingdt as frperiod,"
                            + "cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance,orgncode,l.interest_table_code "
                            + "from accountmaster a,recon r,cbs_loan_acc_mast_sec l where " + subQuery + " a.acno = r.acno and a.acno = l.acno "
                            + "and r.acno = l.acno and r.dt<='" + asOnDt + "' and r.auth='Y' and accttype in(" + acCodeQuery + ") and  a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and (closingdate is "
                            + "null or closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) group by a.acno) "
                            + "aa group by acno").getResultList();

                    intTblList = em.createNativeQuery("select distinct substring(acno,3,2),interest_table_code from "
                            + "cbs_loan_acc_mast_sec where substring(acno,3,2) in(" + acCodeQuery + ")").getResultList();

                } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    result = em.createNativeQuery("select acno,custname,intdeposit,frperiod,toperiod,balance from "
                            + "(select a.acno,a.custname,a.intdeposit,a.openingdt as frperiod,date_format(a.rdmatdate,'%Y%m%d') as "
                            + "toperiod,cast(ifnull(sum(r.cramt),0)-ifnull(sum(r.dramt),0) as decimal(25,2)) as balance from "
                            + "accountmaster a,ddstransaction r where " + subQuery + " a.acno = r.acno and "
                            + "r.dt<='" + asOnDt + "' and r.auth='Y' and accttype in(" + acCodeQuery + ") and  a.OpeningDt <= date_format('" + asOnDt + "','%Y%m%d') and (closingdate is null or "
                            + "closingdate='' or closingdate > date_format('" + asOnDt + "','%Y%m%d')) group by a.acno) "
                            + "aa group by acno").getResultList();

                    //                intTblList = em.createNativeQuery("Select code from parameterinfo_report where "
                    //                        + "reportname='DDSINTFLAG'").getResultList();
                    //                if (intTblList.size() > 0) {
                    //                    Vector rep = (Vector) intTblList.get(0);
                    //                    repCode = Integer.parseInt(rep.get(0).toString());
                    //                }
                }
                if (!result.isEmpty()) {
                    Double savingCommonRoi = 0.0;
                    Map<String, Double> savingIntMap = new HashMap<String, Double>();
                    if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                        for (int z = 0; z < intTblList.size(); z++) {
                            Vector intVec = (Vector) intTblList.get(z);
                            String intAcType = intVec.get(0).toString();
                            String intTblCode = intVec.get(1).toString();

                            List<SavingIntRateChangePojo> intSlabList = sbRemote.getSavingRoiChangeDetail(intTblCode, asOnDt, asOnDt);
                            SavingIntRateChangePojo obj = intSlabList.get(0);
                            savingCommonRoi = obj.getRoi();

                            savingIntMap.put(intTblCode, savingCommonRoi);
                        }

                    }
                    //                else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC) && repCode != 1) {
                    //                    for (int z = 0; z < acctCodeList.size(); z++) {
                    //                        Vector intVec = (Vector) acctCodeList.get(z);
                    //                        String intAcType = intVec.get(0).toString();
                    //                        
                    //                        List dsRoiList = em.createNativeQuery("select roi from ddsconditions "
                    //                                + "where actype ='" + intAcType + "'").getResultList();
                    //                        if (dsRoiList.isEmpty()) {
                    //                            throw new ApplicationException("Roi Date does not exist for A/c Type:" + intAcType);
                    //                        }
                    //                        Vector dsRoiVec = (Vector) dsRoiList.get(0);
                    //                        savingCommonRoi = Double.parseDouble(dsRoiVec.get(0).toString());
                    //                        
                    //                        savingIntMap.put(intAcType, savingCommonRoi);
                    //                    }
                    //                }
                    Map<Integer, String> slabMap = getSlabMap(from, to, noOfSlab);
                    for (int i = 1; i <= noOfSlab; i++) {
                        String value = slabMap.get(i);
                        String[] t = value.split("-");
                        double frRange = Double.parseDouble(t[0]);
                        double toRange = Double.parseDouble(t[1]);

                        for (int j = 0; j < result.size(); j++) {
                            Vector vtr = (Vector) result.get(j);
                            ReportProfilePojo pojo = new ReportProfilePojo();
                            String acno = vtr.get(0).toString();
                            String custname = vtr.get(1).toString();
                            String froPeriod = vtr.get(3).toString();
                            String tooPeriod = vtr.get(4).toString();
                            String interestCode = "";
                            if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                interestCode = vtr.get(7).toString();
                            }

                            double balance = 0d, roi = 0.0;
                            //balance = Double.parseDouble(amtFormatter.format(Double.parseDouble(vtr.get(5).toString())));
                            if (actNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || actNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                                List roiList = em.createNativeQuery("SELECT  v.acno, v.roi,date_format(v.fddt,'%Y%m%d') as frperiod,date_format(v.matdt,'%Y%m%d') as toperiod FROM td_vouchmst v "
                                        + " WHERE v.ACNO = '" + acno + "' and Matdt = (SELECT  coalesce(date_format(MAX(Matdt),'%Y%m%d'),'" + asOnDt + "') FROM td_vouchmst "
                                        + " WHERE ACNO = '" + acno + "' /*and (cldt > '" + asOnDt + "' or cldt is null)*/ and td_madedt <='" + asOnDt + "')").getResultList();
                                if (!roiList.isEmpty()) {
                                    Vector roiVect = (Vector) roiList.get(0);
                                    roi = Double.parseDouble(roiVect.get(1).toString());
                                    froPeriod = roiVect.get(2).toString();
                                    tooPeriod = roiVect.get(3).toString();
                                } else {
                                    roi = Double.parseDouble(vtr.get(2).toString());
                                }
//                                List balList = em.createNativeQuery("SELECT cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2))  FROM td_recon t "
//                                        + " where t.dt<=DATE_FORMAT('" + asOnDt + "','%Y-%m-%d') "
//                                        + " and t.auth='Y' and t.acno = '" + acno + "' AND t.CLOSEFLAG IS NULL "
//                                        + " GROUP BY t.ACNO /*HAVING cast(ifnull(sum(t.cramt),0)-ifnull(sum(t.dramt),0) as decimal(25,2)) <> 0*/").getResultList();
//                                Vector balv = (Vector) balList.get(0);
//                                balance = Double.parseDouble(balv.get(0).toString());
                            }
                            balance = Double.parseDouble(amtFormatter.format(Double.parseDouble(vtr.get(5).toString())));

                            List sancAmtList = null;
                            double sancAmt = 0d;
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                sancAmtList = em.createNativeQuery("select ifnull(ODLimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
                            } else if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN) || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                                sancAmtList = em.createNativeQuery("select ifnull(Sanctionlimit,0) from loan_appparameter where acno = '" + acno + "'").getResultList();
                            }
                            if (actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)
                                    || actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                    || actNature.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {

                                Vector ele = (Vector) sancAmtList.get(0);
                                sancAmt = Double.parseDouble(ele.get(0).toString());
                            }
                            if (actNature.equalsIgnoreCase(CbsConstant.TERM_LOAN)
                                    || actNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                                roi = Double.parseDouble(loanRemote.getRoiLoanAccount(balance, asOnDt, acno));
                            } else if (actNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                double acPrefDr = 0.0, acPrefCr = 0.0;
                                //List orgnList = em.createNativeQuery("select orgncode from accountmaster where "
                                //        + "acno='" + acno + "'").getResultList();
                                //Vector orgnVec = (Vector) orgnList.get(0);
                                int orgnCode = Integer.parseInt(vtr.get(6).toString());
                                if (reportBasedOn.equalsIgnoreCase("R") && orgnCode == 16) {
                                    List orgnList = em.createNativeQuery("select a.ac_pref_dr,a.acc_pref_cr from "
                                            + "cbs_acc_int_rate_details a, cbs_loan_acc_mast_sec b where  a.acno = b.acno "
                                            + "and a.acno = '" + acno + "' and a.eff_frm_dt <= '" + asOnDt + "' and "
                                            + "a.ac_int_ver_no=(select max(c.ac_int_ver_no) from cbs_acc_int_rate_details c "
                                            + "where c.acno='" + acno + "' and c.eff_frm_dt <= '" + asOnDt + "')").getResultList();

                                    if (orgnList.isEmpty()) {
                                        throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acno);
                                    }
                                    Vector intTableCodeVect = (Vector) orgnList.get(0);
                                    acPrefDr = Double.parseDouble(intTableCodeVect.get(0).toString());
                                    acPrefCr = Double.parseDouble(intTableCodeVect.get(1).toString());
                                    roi = savingIntMap.get(interestCode) + acPrefCr - acPrefDr;
                                } else {
                                    roi = savingIntMap.get(interestCode);
                                }
                            } else if (actNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                                //                            if (repCode != 1) {
                                //                                roi = savingIntMap.get(acno.substring(2, 4));
                                //                            } else {
                                roi = Double.parseDouble(vtr.get(2).toString());
                                //                            }
                            } else {
                                roi = Double.parseDouble(vtr.get(2).toString());
                            }

                            long period = CbsUtil.dayDiff(ymd.parse(froPeriod), ymd.parse(tooPeriod));

                            if (reportBasedOn.equalsIgnoreCase("R")) {
                                if (roi >= frRange && roi < toRange) {
                                    pojo.setAcno(acno);
                                    pojo.setName(custname);
                                    pojo.setRoi(roi);
                                    pojo.setOutstanding(new BigDecimal(balance));
                                    pojo.setPeriod(String.valueOf(period));
                                    //pojo.setSlabDescription(value);
                                    pojo.setSlabDescription(amtFormatter.format(frRange) + "-" + amtFormatter.format(toRange - 0.01));
                                    pojo.setSlabType("%Roi");
                                    dataList.add(pojo);
                                }
                            } else if (reportBasedOn.equalsIgnoreCase("A")) {
                                if (reportBase.equalsIgnoreCase("Ob")) {
                                    if (Math.abs(balance) >= frRange && Math.abs(balance) < toRange) {
                                        pojo.setAcno(acno);
                                        pojo.setName(custname);
                                        pojo.setRoi(roi);
                                        pojo.setOutstanding(new BigDecimal(balance));
                                        pojo.setPeriod(String.valueOf(period));
                                        //pojo.setSlabDescription(value);
                                        pojo.setSlabDescription(amtFormatter.format(frRange) + "-" + amtFormatter.format(toRange - 0.01));
                                        pojo.setSlabType("Rs");
                                        dataList.add(pojo);
                                    }
                                } else {
                                    if (Math.abs(sancAmt) >= frRange && Math.abs(sancAmt) < toRange) {
                                        pojo.setAcno(acno);
                                        pojo.setName(custname);
                                        pojo.setRoi(roi);
                                        pojo.setOutstanding(new BigDecimal(Math.abs(sancAmt)));
                                        pojo.setPeriod(String.valueOf(period));
                                        //pojo.setSlabDescription(value);
                                        pojo.setSlabDescription(amtFormatter.format(frRange) + "-" + amtFormatter.format(toRange - 0.01));
                                        pojo.setSlabType("Rs");
                                        dataList.add(pojo);
                                    }
                                }

                            } else if (reportBasedOn.equalsIgnoreCase("P")) {
                                if (period >= frRange && period < toRange) {
                                    pojo.setAcno(acno);
                                    pojo.setName(custname);
                                    pojo.setRoi(roi);
                                    pojo.setOutstanding(new BigDecimal(balance));
                                    pojo.setPeriod(String.valueOf(period));
                                    //pojo.setSlabDescription(value);
                                    pojo.setSlabDescription(frRange + "-" + (toRange - 1));
                                    pojo.setSlabType("Days");
                                    dataList.add(pojo);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public Map<Integer, String> getSlabMap(double from, double to, double noOfSlab) throws ApplicationException {
        Map<Integer, String> slab = new HashMap<Integer, String>();
        try {
            double slabRange = (to - from) / noOfSlab;
            slab.put(1, from + "-" + (from + slabRange));

            double frRange = from;
            double toRange = (from + slabRange);
            for (int i = 2; i <= noOfSlab; i++) {
                frRange = toRange;
                toRange = frRange + slabRange;

                slab.put(i, frRange + "-" + toRange);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return slab;
    }

    public List getDepositNatures() throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct acctnature from accounttypemaster "
                    + "where acctnature in('" + CbsConstant.FIXED_AC + "','" + CbsConstant.RECURRING_AC + "',"
                    + "'" + CbsConstant.DEPOSIT_SC + "','" + CbsConstant.MS_AC + "') order by acctnature").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<DepositProvisionCalPojo> getSummaryProvisionCalculation(String brCode, String acNature,
            String acType, String asOnDt) throws ApplicationException {
        List<DepositProvisionCalPojo> returnList = new ArrayList<DepositProvisionCalPojo>();
        try {
            String acCodeQuery = "";
            if (acType.equalsIgnoreCase("All")) {
                acCodeQuery = "select acctcode,ifnull(glheadprov,'') as glheadprov from accounttypemaster "
                        + "where acctnature in('" + acNature + "') order by acctcode";
            } else {
                acCodeQuery = "select acctcode,ifnull(glheadprov,'') as glheadprov from accounttypemaster "
                        + "where acctcode in('" + acType + "')";
            }
            List acctCodeList = em.createNativeQuery(acCodeQuery).getResultList();
            if (acctCodeList.isEmpty()) {
                throw new ApplicationException("There is no data in accounttype master.");
            }
            for (int i = 0; i < acctCodeList.size(); i++) {
                Vector ele = (Vector) acctCodeList.get(i);
                String acctCode = ele.get(0).toString();
                String glHeadProv = ele.get(1).toString().trim();
                if (glHeadProv.equals("") || glHeadProv.length() != 8) {
                    continue;
                }
                glHeadProv = brCode + glHeadProv + "01";
                String reportMonthLastDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(asOnDt.substring(4, 6)),
                        Integer.parseInt(asOnDt.substring(0, 4))));

                /*If coming AsOn Dt from page is not equal to it's month last date.*/
                if (!(ymd.parse(asOnDt).getTime() == ymd.parse(reportMonthLastDt).getTime())) {
                    String prevMonthDt = CbsUtil.monthAdd(reportMonthLastDt, -1);
                    asOnDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(prevMonthDt.substring(4, 6)),
                            Integer.parseInt(prevMonthDt.substring(0, 4))));
                }
                /*Calculation of total provision.*/
                List dataList = em.createNativeQuery("select cast(ifnull(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0),0) as decimal(25,2)) as "
                        + "total_provision from gl_recon where acno='" + glHeadProv + "' and auth='Y' and dt<='" + asOnDt + "'").getResultList();
                Vector dataVec = (Vector) dataList.get(0);
                BigDecimal provision = new BigDecimal(dataVec.get(0).toString());

                /*Calculation of total outstanding of a particular a/c code.*/
                String reconTableName = common.getTableName(fts.getAcNatureByCode(acctCode));
                dataList = em.createNativeQuery("select cast(ifnull(ifnull(sum(ifnull(cramt,0)),0)-ifnull(sum(ifnull(dramt,0)),0),0) as decimal(25,2)) as "
                        + "total_outstanding from " + reconTableName + " where substring(acno,1,4)='" + (brCode + acctCode) + "' and auth='Y' and dt<='" + asOnDt + "'").getResultList();
                dataVec = (Vector) dataList.get(0);
                BigDecimal outstand = new BigDecimal(dataVec.get(0).toString());
                /*Creation of returning object*/
                DepositProvisionCalPojo obj = new DepositProvisionCalPojo();
                obj.setAcType(acctCode);
                obj.setAcTypeDesc(common.getAcctDecription(acctCode));
                obj.setOutstanding(outstand);
                obj.setProvision(provision);
                obj.setRptAsOnDt(dmy.format(ymd.parse(asOnDt)));

                returnList.add(obj);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public List<DepositProvisionCalPojo> getDetailProvisionCalculation(String brCode, String acNature,
            String acType, String asOnDt) throws ApplicationException {
        List<DepositProvisionCalPojo> returnList = new ArrayList<DepositProvisionCalPojo>();
        try {
            String acCodeQuery = "";
            int repCode = 0;
            if (acType.equalsIgnoreCase("All")) {
                acCodeQuery = "select acctcode from accounttypemaster where acctnature in('" + acNature + "') order by acctcode";
            } else {
                acCodeQuery = "select acctcode from accounttypemaster where acctcode in('" + acType + "')";
            }
            List acctCodeList = em.createNativeQuery(acCodeQuery).getResultList();
            if (acctCodeList.isEmpty()) {
                throw new ApplicationException("There is no data in accounttype master.");
            }
            for (int i = 0; i < acctCodeList.size(); i++) {
                Vector ele = (Vector) acctCodeList.get(i);
                String acctCode = ele.get(0).toString();

                String reportMonthLastDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(asOnDt.substring(4, 6)),
                        Integer.parseInt(asOnDt.substring(0, 4))));

                /*If coming AsOn Dt from page is not equal to it's month last date.*/
                if (!(ymd.parse(asOnDt).getTime() == ymd.parse(reportMonthLastDt).getTime())) {
                    String prevMonthDt = CbsUtil.monthAdd(reportMonthLastDt, -1);
                    asOnDt = ymd.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(prevMonthDt.substring(4, 6)),
                            Integer.parseInt(prevMonthDt.substring(0, 4))));
                }
                String detailQuery = "";
                if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    detailQuery = "select ddh.acno,date_format(am.openingdt,'%d/%m/%Y') as Opening_Dt,am.intdeposit,0.0,"
                            + "sum(ddh.interest) as Provision,date_format(rdmatdate,'%Y%m%d') as matdt from dds_interesthistory ddh,accountmaster am "
                            + "where substring(ddh.acno,1,4)='" + (brCode + acctCode) + "' and ddh.todt<='" + asOnDt + "' and "
                            + "am.acno=ddh.acno group by ddh.acno,Opening_Dt,am.intdeposit";

                    List repcode = em.createNativeQuery("select code from parameterinfo_report where "
                            + "reportname = 'DDSINTFLAG'").getResultList();
                    if (repcode.size() > 0) {
                        Vector rep = (Vector) repcode.get(0);
                        repCode = Integer.parseInt(rep.get(0).toString());
                    }
                } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    detailQuery = "select rdh.acno,date_format(am.openingdt,'%d/%m/%Y') as Opening_Dt,am.intdeposit,"
                            + "am.rdinstal,sum(rdh.interest) as Provision,'' from rd_interesthistory rdh,accountmaster am "
                            + "where substring(rdh.acno,1,4)='" + (brCode + acctCode) + "' and rdh.todt<='" + asOnDt + "' and "
                            + "am.acno=rdh.acno group by rdh.acno,Opening_Dt,am.intdeposit,am.rdinstal";
                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    detailQuery = "select tdh.acno,date_format(tdv.fddt,'%d/%m/%Y') as Fd_Dt,tdv.roi,tdv.prinamt,"
                            + "sum(tdh.interest) as Interest,'',tdh.voucherno from td_interesthistory tdh,td_vouchmst tdv where "
                            + "substring(tdh.acno,1,4)='" + (brCode + acctCode) + "' and tdh.todt<='" + asOnDt + "' and "
                            + "tdv.acno = tdh.acno and tdh.voucherno=tdv.voucherno group by tdh.acno,Fd_Dt,tdv.roi,tdv.prinamt,"
                            + "tdh.voucherno";
                }

                List dataList = em.createNativeQuery(detailQuery).getResultList();
                if (dataList.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < dataList.size(); j++) {
                    Vector element = (Vector) dataList.get(j);
                    String acno = element.get(0).toString();
                    String custName = fts.getCustName(acno);
                    String openingDt = element.get(1) == null ? "" : element.get(1).toString();
                    BigDecimal roi = new BigDecimal(element.get(2).toString());
                    BigDecimal provision = element.get(4) == null ? new BigDecimal("0") : new BigDecimal(element.get(4).toString());
                    BigDecimal commonAmt = null;
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        commonAmt = element.get(3) == null ? new BigDecimal("0") : new BigDecimal(element.get(3).toString());
                    } else {
                        commonAmt = new BigDecimal(common.getBalanceOnDate(acno, asOnDt));
                    }
                    if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                        String matDt = element.get(5) == null ? "19000101" : element.get(5).toString();
                        if (repCode != 1) {
                            int pd = CbsUtil.monthDiff(dmy.parse(openingDt), ymd.parse(matDt));
//                            List resultList = em.createNativeQuery("select roi from ddsconditions "
//                                    + "where actype ='" + fts.getAccountCode(acctCode) + "' and "
//                                    + "maturity = " + pd + "").getResultList();
                            List resultList = em.createNativeQuery("select min(roi),ifnull(roi,0) from dds_slab where (" + pd + " between from_mon and to_mon) "
                                    + " and actype ='" + fts.getAccountCode(acno) + "' and applicable_date = (select max(applicable_date) from dds_slab "
                                    + " where actype ='" + fts.getAccountCode(acno) + "' and applicable_date <='" + openingDt + "')").getResultList();
                            if (resultList.isEmpty()) {
                                throw new ApplicationException("Maturity date does not exist.");
                            }
                            Vector vtr2 = (Vector) resultList.get(0);
                            roi = new BigDecimal(vtr2.get(1).toString());
                        }
                    }
                    DepositProvisionCalPojo obj = new DepositProvisionCalPojo();
                    obj.setAcno(acno);
                    obj.setName(custName);
                    obj.setOpeningDt(openingDt);
                    obj.setRoi(roi);
                    obj.setCommonAmt(commonAmt);
                    obj.setProvision(provision);
                    obj.setAcType(acctCode);
                    obj.setAcTypeDesc(common.getAcctDecription(acctCode));
                    obj.setRptAsOnDt(dmy.format(ymd.parse(asOnDt)));

                    returnList.add(obj);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return returnList;
    }

    public List<CashInAtmPojo> getGlHeadData(String atmBrcode, String glhead, String frDt, String toDt) throws ApplicationException {
        List<CashInAtmPojo> dataList = new ArrayList<CashInAtmPojo>();
        String d1 = "", d2 = "";
        try {

            List result1 = em.createNativeQuery("select ATM_LOCATION from atm_master where ATM_BRANCH = '" + atmBrcode + "' and ATM_CASH_GENERAL_HEAD = '" + glhead + "'").getResultList();
            Vector ele = (Vector) result1.get(0);
            String atmLocation = ele.get(0).toString();

            List result2 = em.createNativeQuery("select acName from gltable where acno = '" + glhead + "'").getResultList();
            Vector ele1 = (Vector) result2.get(0);
            String acName = ele1.get(0).toString();

            List list = em.createNativeQuery("select date_format(dt,'%d/%m/%Y') as date,details,dramt,cramt,balance,trantype,ty from gl_recon where acno = '" + glhead + "' and ( dt <= '" + toDt + "' and dt >= '" + frDt + "' ) and auth='y' order by dt,trantime,recno").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    CashInAtmPojo pojo = new CashInAtmPojo();
                    String dt = vtr.get(0).toString();
                    String detail = vtr.get(1).toString();
                    double drAmt = Double.parseDouble(vtr.get(2).toString());
                    double crAmt = Double.parseDouble(vtr.get(3).toString());
                    double bal = Double.parseDouble(vtr.get(4).toString());
                    if (vtr.get(5) != null || !vtr.get(5).toString().equalsIgnoreCase("")) {
                        int tanType = Integer.parseInt(vtr.get(5).toString());
                        if (tanType == 0) {
                            d2 = "CASH";
                        } else if (tanType == 1) {
                            d2 = "CLR";
                        } else if (tanType == 8) {
                            d2 = "INT";
                        }
                    }
                    if (vtr.get(6) != null || !vtr.get(6).toString().equalsIgnoreCase("")) {
                        int ty = Integer.parseInt(vtr.get(6).toString());
                        if (ty == 0) {
                            d1 = "By";
                        } else if (ty == 1) {
                            d1 = "To";
                        }
                    }

                    double openBal = common.getBalanceOnDate(glhead, toDt);
                    double balance = openBal + crAmt - drAmt;

                    pojo.setOpenBal(openBal);
                    pojo.setDeposit(crAmt);
                    pojo.setWithdrawl(drAmt);
                    pojo.setBalance(balance);
                    pojo.setDt(dt);
                    pojo.setDaetail(d1 + d2 + " " + detail);
                    pojo.setAcName(acName);
                    pojo.setAtmLocation(atmLocation);
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<DepositDrLoanCrBalPojo> getDepositDrLoanCrData(String brCode, String acNature, String acType, String asOnDt) throws ApplicationException {
        List<DepositDrLoanCrBalPojo> dataList = new ArrayList<DepositDrLoanCrBalPojo>();
        List result = new ArrayList();
        List acCodeList = new ArrayList();

        String acNat = "", table1 = "", table2 = "";
        try {
            if (acType.equalsIgnoreCase("ALL")) {
                acCodeList = em.createNativeQuery("select acctcode from accounttypemaster where acctnature = '" + acNature + "' order by acctcode").getResultList();
            } else {
                acCodeList = em.createNativeQuery("select acctcode from accounttypemaster where acctcode = '" + acType + "' order by acctcode").getResultList();
            }
            if (!acCodeList.isEmpty()) {
                for (int i = 0; i < acCodeList.size(); i++) {
                    Vector vtr = (Vector) acCodeList.get(i);
                    String acCode = vtr.get(0).toString();
                    acNat = fts.getAcNatureByCode(acCode);
                    if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        table1 = "td_accountmaster";
                    } else {
                        table1 = "accountmaster";
                    }
                    table2 = common.getTableName(acNat);
                    String header = "";
                    String condition = "";
                    if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN) || acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN) || acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC) && !acCode.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                        condition = "having sum(cramt)-sum(dramt) > 0";
                        header = "Loan(Cr Balance)";
                    } else {
                        condition = "having sum(cramt)-sum(dramt) < 0";
                        header = "Deposit(Dr Balance)";
                    }

                    if (brCode.equalsIgnoreCase("0A")) {
                        result = em.createNativeQuery("select acno,custName from " + table1 + " where acno in(select acno from " + table2 + " where substring(acno,3,2) = '" + acCode + "' group by acno  " + condition + " ) and openingDt < '" + asOnDt + "' and accstatus <> '9' order by acno").getResultList();
                    } else {
                        result = em.createNativeQuery("select acno,custName from " + table1 + " where acno in(select acno from " + table2 + " where substring(acno,1,2) = '" + brCode + "' and substring(acno,3,2) = '" + acCode + "' group by acno " + condition + " ) and openingDt < '" + asOnDt + "' and accstatus <> '9' order by acno").getResultList();
                    }
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            Vector vtr1 = (Vector) result.get(j);
                            DepositDrLoanCrBalPojo pojo = new DepositDrLoanCrBalPojo();
                            String acno = vtr1.get(0).toString();
                            String name = vtr1.get(1).toString();
                            List oList1 = em.createNativeQuery("select concat(PerAddressLine1,' ',PerAddressLine2) from cbs_customer_master_detail where customerid in(select CustId from customerid where acno = '" + acno + "')").getResultList();
                            String add = "";
                            if (!oList1.isEmpty()) {
                                Vector vtr2 = (Vector) oList1.get(0);
                                add = vtr2.get(0).toString();
                            }
                            double bal = common.getBalanceOnDate(acno, asOnDt);
                            if (Math.abs(bal) != 0) {
                                pojo.setAcNo(acno);
                                pojo.setAddress(add);
                                pojo.setName(name);
                                pojo.setBalance(new BigDecimal(Math.abs(bal)));
                                pojo.setAcType(acCode);
                                pojo.setAcTypeCodeDesc(common.getAcctDecription(acCode));
                                pojo.setHeader(header);
                                dataList.add(pojo);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getAccountNatureClassification(String classification) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctnature) from accounttypemaster "
                    + "where crdbflag in(" + classification + ") and acctnature "
                    + "not in('" + CbsConstant.PAY_ORDER + "')").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAccountNatureClassificationWithGl(String classification) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct(acctnature) from accounttypemaster where crdbflag in(" + classification + ") "
                    + "and acctnature not in('PO')"
                    + "union "
                    + "select distinct(InstNature) from billtypemaster").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAccountCodeByClassificationAndNature(String classification, String nature) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where "
                    + "crdbflag in(" + classification + ") and acctnature='" + nature + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<UserHistoryDto> printUserHistory(String brnCode, String frDt, String toDt,
            String enterBy) throws ApplicationException {
        List<UserHistoryDto> dataList = new ArrayList<UserHistoryDto>();
        try {
            List list = em.createNativeQuery("select userid,levelid,username,status,cast(ifnull(tocashlimit,0) as decimal(25,2)) "
                    + "as cash_dr_limit,cast(ifnull(trandebit,0) as decimal(25,2)) as tran_debit_limit,cast(ifnull(clgdebit,0) "
                    + "as decimal(25,2)) as clg_debit_limit,ifnull(address,'') as address,lastupdatedt,password,enterby,txnid from "
                    + "securityinfohistory where brncode='" + brnCode + "' and lastupdatedt between '" + frDt + "' and '" + toDt + "' and "
                    + "levelid not in(5,6,7) and userid<> '" + enterBy + "' order by userid,lastupdatedt ").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector ele = (Vector) list.get(i);
                    if (!(ele.get(0) == null || ele.get(0).toString().equals(""))) {
                        String userid = ele.get(0).toString();

                        UserHistoryDto obj = new UserHistoryDto();
                        obj.setUserid(userid);
                        obj.setRole(CbsUtil.getRoleMap().get(ele.get(1).toString()));
                        obj.setUsername(ele.get(2).toString());
                        String status = ele.get(3).toString();
                        if (status.equalsIgnoreCase("A")) {
                            obj.setStatus("Active");
                        } else if (status.equalsIgnoreCase("C")) {
                            obj.setStatus("Cancelled");
                        } else if (status.equalsIgnoreCase("D")) {
                            obj.setStatus("Deleted");
                        }
                        obj.setCashDrLimit(new BigDecimal(ele.get(4).toString()));
                        obj.setTrfDrLimit(new BigDecimal(ele.get(5).toString()));
                        obj.setClgDrLimit(new BigDecimal(ele.get(6).toString()));
                        obj.setAddress(ele.get(7).toString());
                        String lastUpdateDt = ele.get(8).toString();
                        String pPasswd = ele.get(9).toString();
                        Integer txnid = Integer.parseInt(ele.get(11).toString());
                        String cPasswd = "";
                        List reSetList = em.createNativeQuery("select password from securityinfohistory where userid = '" + userid + "' and "
                                + "brncode = '" + brnCode + "' and txnid in(select max(txnid) from securityinfohistory where userid = '" + userid + "' and "
                                + "brncode = '" + brnCode + "' and txnid<" + txnid + ")").getResultList();
                        if (!reSetList.isEmpty()) {
                            Vector vtr = (Vector) reSetList.get(0);
                            cPasswd = vtr.get(0).toString();
                        }

                        if (pPasswd.equalsIgnoreCase(cPasswd)) {
                            obj.setPwdReSet("");
                        } else {
                            obj.setPwdReSet("Password Reset");
                        }
                        obj.setEnterBy(ele.get(10).toString());
                        obj.setUpdateDt(lastUpdateDt.substring(8, 10) + "/" + lastUpdateDt.substring(5, 7) + "/" + lastUpdateDt.substring(0, 4));
                        obj.setOperatingBranch("");
                        obj.setFlag("H");

                        dataList.add(obj);
                    }
                }
                Set<String> userSet = new TreeSet<String>();
                for (UserHistoryDto obj : dataList) {
                    userSet.add(obj.getUserid());
                }
                Iterator<String> it = userSet.iterator();
                while (it.hasNext()) {
                    String curUserId = it.next();

                    //Current detail of user.
                    List currentList = em.createNativeQuery("select userid,levelid,username,status,"
                            + "cast(ifnull(tocashlimit,0) as decimal(25,2)) as cash_dr_limit,cast(ifnull(trandebit,0) "
                            + "as decimal(25,2)) as tran_debit_limit,cast(ifnull(clgdebit,0) as decimal(25,2)) as "
                            + "clg_debit_limit,ifnull(address,'') as address,brncode from securityinfo where "
                            + "userid='" + curUserId + "' ").getResultList();
                    if (!currentList.isEmpty()) {
                        Vector element = (Vector) currentList.get(0);

                        UserHistoryDto pojo = new UserHistoryDto();
                        pojo.setUserid(curUserId);
                        pojo.setRole(CbsUtil.getRoleMap().get(element.get(1).toString()));
                        pojo.setUsername(element.get(2).toString());
                        String status = element.get(3).toString();
                        if (status.equalsIgnoreCase("A")) {
                            pojo.setStatus("Active");
                        } else if (status.equalsIgnoreCase("C")) {
                            pojo.setStatus("Cancelled");
                        } else if (status.equalsIgnoreCase("D")) {
                            pojo.setStatus("Deleted");
                        }
                        pojo.setCashDrLimit(new BigDecimal(element.get(4).toString()));
                        pojo.setTrfDrLimit(new BigDecimal(element.get(5).toString()));
                        pojo.setClgDrLimit(new BigDecimal(element.get(6).toString()));
                        pojo.setAddress(element.get(7).toString());
                        pojo.setUpdateDt("");
                        pojo.setOperatingBranch(common.getAlphacodeByBrncode(element.get(8).toString()));
                        pojo.setFlag("C");

                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List getAcctCodeByNature(String nature) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature='" + nature + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getAcctCodeByNatureFlag(String nature, String flag) throws ApplicationException {
        try {
            return em.createNativeQuery("select acctcode from accounttypemaster where acctnature='" + nature + "' and CrDbFlag in(" + flag + ")").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getDeafEfectiveDate() throws ApplicationException {
        String deafEffDate = "";
        try {
            List list = em.createNativeQuery("select ifnull(code,0) as deafEffDate from "
                    + "parameterinfo_report where reportname in('DEAF-CUT-OFF-DATE')").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define Deaf Effective Date");
            }
            Vector ele = (Vector) list.get(0);
            deafEffDate = ele.get(0).toString().trim();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return deafEffDate;
    }

    public List<UnclaimedAccountStatementPojo> unClaimedMarking(String brnCode, String acNature, String acType,
            String finalDeafDt, String intTillDate, String curDt, double savingRoi) throws ApplicationException {
        List<UnclaimedAccountStatementPojo> dataList = new ArrayList<UnclaimedAccountStatementPojo>();
        try {
            String frDt = CbsUtil.yearAdd(finalDeafDt, -10);
            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                query = "select a.acno,a.custname,date_format(matdt,'%Y%m%d'),voucherno, intopt,cast(roi as decimal(14,2)),"
                        + "date_format(fddt,'%Y%m%d') as fddt from td_vouchmst v,td_accountmaster a  where a.curbrcode = '" + brnCode + "' and  "
                        + "matdt<= '" + frDt + "' and status = 'A' and v.acno = a.acno and a.accttype = '" + acType + "' and a.accstatus<>15";
            } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                query = "select a.acno,a.custname,date_format(ifnull(last_txn_date,''),'%Y%m%d') as lastTxnDt,date_format(Rdmatdate,'%Y%m%d') from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype ='" + acType + "' and a.curbrcode ='" + brnCode + "' and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + finalDeafDt + "') and date_format(Rdmatdate,'%Y%m%d')<='" + frDt + "' ";
            } else {
                query = "select a.acno,a.custname,date_format(ifnull(last_txn_date,''),'%Y%m%d') as lastTxnDt from accountmaster a "
                        + "where a.accstatus<>15 and a.accttype ='" + acType + "' and a.curbrcode ='" + brnCode + "' and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + finalDeafDt + "') group by a.acno,a.custname "
                        + "having max(date_format(a.last_txn_date,'%Y%m%d'))<='" + frDt + "'";
            }
            dataList = getUnclaimedDetails(query, brnCode, acNature, acType, finalDeafDt, intTillDate, curDt, savingRoi);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<UnclaimedAccountStatementPojo> unClaimedMarkingSingle(String brnCode, String acNo, String finalDeafDt, String intTillDate, String curDt, double savingRoi) throws ApplicationException {
        List<UnclaimedAccountStatementPojo> dataList = new ArrayList<UnclaimedAccountStatementPojo>();
        try {
            String acNature = fts.getAccountNature(acNo);
            String acType = fts.getAccountCode(acNo);
            String frDt = CbsUtil.yearAdd(finalDeafDt, -10);
            String query = "";
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                query = "select a.acno,a.custname,date_format(matdt,'%Y%m%d'),voucherno, intopt,cast(roi as decimal(14,2)),"
                        + "date_format(fddt,'%Y%m%d') as fddt from td_vouchmst v,td_accountmaster a where "
                        + "a.curbrcode = '" + brnCode + "' and  matdt<= '" + frDt + "' and status = 'A'  and a.acno = '" + acNo
                        + "' and v.acno = a.acno and a.accttype = '" + acType + "' and a.accstatus<>15";
            } else {
                query = "select a.acno,a.custname,date_format(ifnull(last_txn_date,''),'%Y%m%d') as lastTxnDt from accountmaster a where a.accstatus<>15 "
                        + "and a.acno = '" + acNo + "' and a.accttype ='" + acType + "' and a.curbrcode ='" + brnCode + "' and (a.closingdate is null "
                        + "or a.closingdate='' or a.closingdate > '" + finalDeafDt + "') group by a.acno,a.custname having "
                        + "max(date_format(a.last_txn_date,'%Y%m%d'))<='" + frDt + "'";
            }
            dataList = getUnclaimedDetails(query, brnCode, acNature, acType, finalDeafDt, intTillDate, curDt, savingRoi);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<UnclaimedAccountStatementPojo> getUnclaimedDetails(String query, String brnCode, String acNature, String acType,
            String finalDeafDt, String intTillDate, String curDt, double savingRoi) throws ApplicationException {
        List<UnclaimedAccountStatementPojo> dataList = new ArrayList<UnclaimedAccountStatementPojo>();

        try {
            String reconTable = common.getTableName(acNature);
            String provFlag = "";
            int postFlag = 0;
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                List simple = em.createNativeQuery("Select ifnull(SimplePostFlag,0) From td_parameterinfo").getResultList();
                if (simple.isEmpty()) {
                    throw new ApplicationException("Data does not exist in td_parameterinfo");
                }
                Vector simples = (Vector) simple.get(0);
                postFlag = Integer.parseInt(simples.get(0).toString());

                List provFlagList = em.createNativeQuery("Select provAppOn from accounttypemaster where acctcode = '" + acType + "'").getResultList();
                if (provFlagList.isEmpty()) {
                    throw new ApplicationException("Data does not exist in accounttypemaster");
                }
                Vector provFlagVect = (Vector) provFlagList.get(0);
                provFlag = provFlagVect.get(0).toString();
            }
            List result = em.createNativeQuery(query).getResultList();

//            System.out.println("Size of List is-->" + result.size());
            Integer sno = 0;
            for (int i = 0; i < result.size(); i++) {
                UnclaimedAccountStatementPojo pojo = new UnclaimedAccountStatementPojo();
                Vector ele = (Vector) result.get(i);
                String acNo = ele.get(0).toString();
                String custName = ele.get(1).toString();
                String lastTrnDt = ele.get(2).toString();
                String provision = "N";
                String receiptNo = "0";
                double totalInterest = 0;
                double prinAmt = 0;
                List list = null;
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {

                    receiptNo = ele.get(3).toString();
                    String intOpt = ele.get(4).toString();
                    float roi = Float.parseFloat(ele.get(5).toString());
                    String fddt = ele.get(6).toString();

                    if (!provFlag.equals("") && provFlag.toUpperCase().contains(intOpt.toUpperCase())) {
                        provision = "Y";
                    } else if (intOpt.equalsIgnoreCase("S") && (postFlag == 0 || postFlag == 2)) {
                        provision = "Y";
                    }
                    // New code as Raghib
                    List prinList = em.createNativeQuery("select IntOpt,PrinAmt,CumuPrinAmt from td_vouchmst where acno = '" + acNo + "' and VoucherNo = " + receiptNo + "").getResultList();
                    Vector pVector = (Vector) prinList.get(0);
                    if (intOpt.equalsIgnoreCase("S")) {
                        prinAmt = Double.parseDouble(pVector.get(1).toString());
                    } else {
                        prinAmt = Double.parseDouble(pVector.get(2).toString());
                    }
                    list = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0) as decimal(14,2)) from "
                            + reconTable + " where acno = '" + acNo + "' and closeflag is null and trantype<>27 and "
                            + "dt < '" + ymd.format(dmy.parse(intTillDate)) + "'").getResultList();

                    long fullDt = CbsUtil.dayDiff(ymd.parse(fddt), ymd.parse(lastTrnDt));
                    String year = String.valueOf(fullDt / 365);
                    fullDt = fullDt % 365;
                    String mon = String.valueOf(fullDt / 30);
                    String day = String.valueOf(fullDt % 30);

                    List<Double> tdGlobalValue = autoRenewRemote.tdGlobal(acNo, Float.parseFloat(receiptNo), "True", roi, 0, year, mon, day, "");

                    double fdInterest = Double.parseDouble(formatter.format(tdGlobalValue.get(3) - tdGlobalValue.get(4)));
                    if (provFlag.equalsIgnoreCase("Y")) {
                        fdInterest = Double.parseDouble(formatter.format(tdGlobalValue.get(3)));
                    }

//                    double outSt = Double.parseDouble(formatter.format(pojo.getAmount() + fdInterest));
//                    String savingFrDt = CbsUtil.dateAdd(lastTrnDt, 1);

                    // String intCode = fts.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");

//                    List<SavingIntRateChangePojo> resultList = sbRemote.getSavingRoiChangeDetail(intCode, savingFrDt, ymd.format(dmy.parse(intTillDate)));
//                    if (resultList.isEmpty()) {
//                        throw new ApplicationException("There is no slab for saving interest calculation.");
//                    }
                    totalInterest = fdInterest;
//                    for (int k = 0; k < resultList.size(); k++) {
//                        SavingIntRateChangePojo obj = resultList.get(k);
//
//                        String slabFrDt = obj.getFrDt();
//                        String slabToDt = obj.getToDt();
//                        double sbRoi = obj.getRoi();
//
//                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
//                        double savingInterest = 0;
//                        if (savingDiff > 0) {
//                            savingInterest = sbRoi * savingDiff.doubleValue() * outSt / 36500;
//                        }
//                        totalInterest = totalInterest + savingInterest;
//                    }
                } else {
                    if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                        list = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from ca_reconbalan where acno = '"
                                + acNo + "'").getResultList();

                        if (acType.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
                            List dateList = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
                                    + "cbs_loan_acc_mast_sec where acno='" + pojo.getAcNo() + "'").getResultList();
                            if (dateList == null || dateList.isEmpty()) {
                                throw new ApplicationException("Please define next interest calculation date for A/c-->" + pojo.getAcNo());
                            }
                            Vector element = (Vector) dateList.get(0);
                            String fromDt = element.get(0).toString();

                            String glHead = loanRemote.getGlHeads(acType);
                            List<LoanIntCalcList> resultList = loanRemote.cbsLoanIntCalc("I", acType, acNo, fromDt, intTillDate, glHead, "", brnCode);
                            if (!resultList.isEmpty()) {
                                for (LoanIntCalcList lict : resultList) {
                                    totalInterest = lict.getTotalInt();
                                }
                            }
                        }
                    } else if (acNature.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {

                        list = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from reconbalan where acno = '"
                                + acNo + "'").getResultList();
                        Vector balVec = (Vector) list.get(0);
                        double bal = Double.parseDouble(balVec.get(0).toString());
                        List dateList = em.createNativeQuery("select a.OpeningDt, DATE_FORMAT(a.Rdmatdate,'%Y%m%d') from accountmaster a where ACNo='" + acNo + "'").getResultList();
                        Vector ele1 = (Vector) dateList.get(0);
                        Date acOpenDate = ymd.parse(ele1.get(0).toString());
                        Date maturityDate = ymd.parse(ele1.get(1).toString());
                        List<Double> result1 = beanRemote.interestCalcultaion(acNo, maturityDate, acOpenDate, 0);
                        totalInterest = result1.get(6);
                        List deafDays = em.createNativeQuery("SELECT DATEDIFF('" + ymd.format(dmy.parse(curDt)) + "','" + ele1.get(1).toString() + "')").getResultList();// Current date and maturity date
                        Vector daysVec = (Vector) deafDays.get(0);
                        int days = Integer.parseInt(daysVec.get(0).toString());
                        double amount = bal + totalInterest;
                        double deafDateInterest = (amount * savingRoi * (days - 1)) / (100 * 365);
                        totalInterest = deafDateInterest;

                    } else if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {

                        list = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from reconbalan where acno = '"
                                + acNo + "'").getResultList();
                        List dateList = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
                                + "cbs_loan_acc_mast_sec where acno='" + acNo + "'").getResultList();
                        if (dateList == null || dateList.isEmpty()) {
                            throw new ApplicationException("Please define next interest calculation date for A/c-->" + pojo.getAcNo());
                        }
                        Vector element = (Vector) dateList.get(0);
                        String fromDt = element.get(0).toString();

                        List<LoanIntCalcList> resultList = sbRemote.cbsSbIntCalc("I", "", acType, acNo, fromDt, intTillDate, brnCode);
                        if (!resultList.isEmpty()) {
                            for (LoanIntCalcList lict : resultList) {
                                totalInterest = lict.getTotalInt();
                            }
                        }

                    } else if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {

                        list = em.createNativeQuery("select cast(ifnull(balance,0) as decimal (14,2)) as balance from reconbalan where acno = '"
                                + acNo + "'").getResultList();

                        Vector balVec = (Vector) list.get(0);
                        double bal = Double.parseDouble(balVec.get(0).toString());

                        List rdList = em.createNativeQuery("select date_format(Rdmatdate,'%Y%m%d') from accountmaster where acno = '" + acNo + "'").getResultList();
                        Vector rdVector = (Vector) rdList.get(0);
                        String rdMatDt = rdVector.get(0).toString();

                        List<RdInterestDTO> rdIntList = individualAcnoRdIntCal(acNo, rdMatDt);
                        double rdRoi = rdIntList.get(0).getRoi();
                        double rdBal = rdIntList.get(0).getBalance();
                        double intAmt = rdIntList.get(0).getInterest();
                        double rdInstallment = rdIntList.get(0).getInstallment();
                        String rdOpeningDt = rdIntList.get(0).getOpeningDt();
                        //String rdMatDt = rdIntList.get(0).getRdMatDt();
                        // double intAmt = CbsUtil.round(rdBal * rdRoi / 1200, 0);
                        double intPaid = RdInterestPaid(acNo);
                        double remainingInt = intAmt - intPaid;
                        double rdTotalBal = bal + remainingInt;
                        String intCode = fts.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");
//                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(rdMatDt), dmy.parse(intTillDate));
//                        String sbRoi = acOpenFacadeRemote.getROI(intCode, rdTotalBal, ymd.format(dmy.parse(intTillDate)));
//                        totalInterest = Double.parseDouble(sbRoi) * savingDiff.doubleValue() * rdTotalBal / 36500;
//                       
                        List<SavingIntRateChangePojo> resultList = sbRemote.getSavingRoiChangeDetail(intCode, rdMatDt, ymd.format(dmy.parse(intTillDate)));
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
                                savingInterest = sbRoi * savingDiff.doubleValue() * rdTotalBal / 36500;
                            }
                            totalInterest = totalInterest + savingInterest;
                        }
                    }
                }
                Vector balVec = (Vector) list.get(0);
                double bal = Double.parseDouble(balVec.get(0).toString());
                if (bal != 0.00) {
                    pojo.setAcNo(acNo);
                    pojo.setCustName(custName);
                    pojo.setLastTrnDt(dmy.format(ymd.parse(lastTrnDt)));
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        pojo.setAmount(prinAmt);
                    } else {
                        pojo.setAmount(bal);
                    }
                    //pojo.setInterest(Double.parseDouble(amtFormatter.format(totalInterest)));
                    pojo.setInterest(CbsUtil.round(totalInterest, 0));

                    pojo.setReceiptNo(receiptNo);
                    pojo.setProvFlag(provision);
                    sno = sno + 1;
                    pojo.setSno(sno.toString());

                    dataList.add(pojo);
                }
            }
            return dataList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<RdInterestDTO> individualAcnoRdIntCal(String acNo, String toDate) throws ApplicationException {
        try {
            List<RdInterestDTO> rdIntList = new ArrayList<RdInterestDTO>();
            List accountList = em.createNativeQuery("Select AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt, AM.RdInstal, sum(ifnull(RP.Balance,0)) as Balance,date_format(Rdmatdate,'%Y%m%d') rdMatDt \n"
                    + "From accountmaster AM, rd_product RP Where AM.Acno = '" + acNo + "' \n"
                    + "And AM.AccStatus<>9 and AM.Acno = RP.acno and AM.OpeningDt <='" + toDate + "' and RP.dt <='" + toDate + "' \n"
                    + "group by AM.Acno, AM.CustName, AM.IntDeposit,AM.OpeningDt,AM.RdInstal order by AM.acno").getResultList();

            if (accountList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                RdInterestDTO rdIntDetail;
                for (int i = 0; i < accountList.size(); i++) {
                    Vector vect = (Vector) accountList.get(i);
                    rdIntDetail = new RdInterestDTO();

                    rdIntDetail.setAcNo(vect.get(0).toString());
                    rdIntDetail.setCustName(vect.get(1).toString());
                    rdIntDetail.setRoi(Double.parseDouble(vect.get(2).toString()));

                    rdIntDetail.setOpeningDt(vect.get(3).toString());
                    rdIntDetail.setInstallment(Double.parseDouble(vect.get(4).toString()));
                    rdIntDetail.setBalance(CbsUtil.round(Double.parseDouble(vect.get(5).toString()), 2));
                    rdIntDetail.setRdMatDt(vect.get(6).toString());

                    double intAmt = CbsUtil.round(rdIntDetail.getBalance() * rdIntDetail.getRoi() / 1200, 0);
                    if (intAmt > 0) {
                        rdIntDetail.setInterest(intAmt);
                        rdIntList.add(rdIntDetail);
                    }
                }
            }
            if (rdIntList.isEmpty()) {
                throw new ApplicationException("There is no account for interest calculation");
            } else {
                return rdIntList;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double RdInterestPaid(String acNo) throws ApplicationException {
        try {
            List rdList = em.createNativeQuery("select ifnull(sum(interest),0.0) from rd_interesthistory where acno = '" + acNo + "'").getResultList();
            double interest = 0;
            if (rdList.size() > 0) {
                Vector rdVect = (Vector) rdList.get(0);
                interest = Float.parseFloat(rdVect.get(0).toString());
            } else {
                interest = 0;
            }
            return interest;
        } catch (Exception e) {
            throw new ApplicationException("aaaa");
        }
    }

    public String getAllBranchCodefromddsAgentListforCheckPostHistory(String frDt, String toDt) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select distinct brncode from ddsagent").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vec = (Vector) list.get(i);
                    List lis1 = em.createNativeQuery("select PostFlag from post_history where PostFlag='CP' and "
                            + "fromdate='" + ymd.format(dmy.parse(frDt)) + "' and todate='" + ymd.format(dmy.parse(toDt)) + "' and brnCode='" + vec.get(0).toString() + "'").getResultList();
                    if (!lis1.isEmpty()) {
                        return "You have already posted agent commision for some branch , So you need to post agent commision individually every branch.";
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    //added by rahul
    public String postAgentCommisionTransaction(List<AgentCommissionPojo> itemList, String txndate, String user, String orgnBrCode, String frDt, String toDt, String branchCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            String entryBrnCode = "";
            String msg = "";
            String consoleGlHead = "";
            float trsno = 0f;
            ut.begin();

            List<AgentCommissionPojo> checkList = new ArrayList<>();
            trsno = fts.getTrsNo();
            for (AgentCommissionPojo check : itemList) {
                entryBrnCode = check.getBranch();
                if (!(check.getAcno().substring(0, 2).equals(entryBrnCode))) {
                    checkList.add(check);
                }

            }
            if (!branchCode.equalsIgnoreCase("0A")) {
                List lis1 = em.createNativeQuery("select PostFlag from post_history where PostFlag='CP' and "
                        + "fromdate='" + ymd.format(dmy.parse(frDt)) + "' and todate='" + ymd.format(dmy.parse(toDt)) + "' and brnCode='" + branchCode + "'").getResultList();
                if (!lis1.isEmpty()) {
                    ut.rollback();
                    return "Agent commision already posted for this branch on this month.";
                }
            }

            for (AgentCommissionPojo obj : itemList) {
                if (!checkList.isEmpty()) {
                    if (obj.getNetAmt() > 0.0) {
                        entryBrnCode = obj.getBranch();
                        List consoleGlHeadList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'DDS_COMMISION_HEAD'").getResultList();
                        if (consoleGlHeadList.size() > 0) {
                            Vector consoleGlHeadVect = (Vector) consoleGlHeadList.get(0);
                            consoleGlHead = fts.getCurrentBrnCode(obj.getAcno()).concat(consoleGlHeadVect.get(0).toString());
                        }
                        float recno = fts.getRecNo();

                        msg = ibtFacade.cbsPostingSx(obj.getAcno(), 0, ymd.format(new Date()), obj.getNetAmt(), 0.0, 2,
                                "Agent Commision from " + frDt + " to " + toDt, 0f, "", "", "", 3, 0f, recno, 131, obj.getAcno().substring(0, 2),
                                entryBrnCode, user, "SYSTEM", trsno, "0", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            throw new Exception(msg);
                        }

                        msg = ibtFacade.cbsPostingCx(consoleGlHead, 1, ymd.format(new Date()), obj.getNetAmt(), 0.0, 2,
                                "Agent Commision from " + frDt + " to " + toDt, 0f, "", "", "", 3, 0f, recno, 131, entryBrnCode,
                                entryBrnCode, user, "SYSTEM", trsno, "0", "");
                        if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                            ut.rollback();
                            throw new Exception(msg);
                        }
//                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
//                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
//                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
//                                + "tran_id,adviceno,advicebrncode) values('" + obj.getAcno() + "',"
//                                + "'" + obj.getAgName() + "','" + ymd.format(new Date()) + "',"
//                                + "'" + ymd.format(new Date()) + "',0," + obj.getNetAmt() + ",1,2,"
//                                + "" + recno + ",'',NULL,3,0,'Y','" + user + "',"
//                                + "131 ,0,'','Agent Commision posted from " + frDt + " to " + toDt + "','SYSTEM'," + trsno + ",CURRENT_TIMESTAMP,"
//                                + "'','" + entryBrnCode + "',"
//                                + "'" + entryBrnCode + "',0,'','')").executeUpdate();
//
//                        if (insertResult <= 0) {
//                            ut.rollback();
//                            throw new Exception("Data insertion problem in transfer scroll for account number: " + obj.getAcno());
//                        }
                    }
                } else {
                    if (obj.getNetAmt() > 0.0) {
                        entryBrnCode = obj.getBranch();
                        float recno = fts.getRecNo();
                        String acctNature = fts.getAccountNature(obj.getAcno());
                        List consoleGlHeadList = em.createNativeQuery("SELECT ACNO FROM abb_parameter_info WHERE PURPOSE = 'DDS_COMMISION_HEAD'").getResultList();
                        if (consoleGlHeadList.size() > 0) {
                            Vector consoleGlHeadVect = (Vector) consoleGlHeadList.get(0);
                            consoleGlHead = fts.getCurrentBrnCode(obj.getAcno()).concat(consoleGlHeadVect.get(0).toString());
                        }

                        msg = fts.insertRecons(acctNature, obj.getAcno(), 0, obj.getNetAmt(), ymd.format(new Date()), ymd.format(new Date()),
                                2, "Agent Commision from " + frDt + " to " + toDt, user, trsno, null, recno, "Y", "SYSTEM",
                                131, 3, "", "", 0.0f, "", "", 1, "", 0.0f, "", "", entryBrnCode, entryBrnCode, 0, "", "", "");

                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                        Integer drrecon = em.createNativeQuery("insert into gl_recon (acno,dt,valueDt,dramt,ty,trantype,details,auth,enterBy,"
                                + "authby,trsno,payby,recno,TRANDESC,IY,org_brnid,dest_brnid) values('" + consoleGlHead + "',date_format(now(),'%Y%m%d')"
                                + ",date_format(now(),'%Y%m%d')," + obj.getNetAmt() + ",1,2, 'Agent Commision posted from " + frDt + " to " + toDt + " for " + obj.getAcno() + "','Y','" + user + "','System'," + trsno + ",3,"
                                + "" + recno + ",131,1,'" + entryBrnCode + "','" + entryBrnCode + "')").executeUpdate();
                        if (drrecon <= 0) {
                            return "Error in GL_Entry !!!";
                        }

                        int insertResult1 = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                + "tran_id,adviceno,advicebrncode) values('" + obj.getAcno() + "',"
                                + "'" + obj.getAgName() + "','" + ymd.format(new Date()) + "',"
                                + "'" + ymd.format(new Date()) + "'," + obj.getNetAmt() + ",0,0,2,"
                                + "" + recno + ",'',NULL,3,0,'Y','" + user + "',"
                                + "131 ,0,'','Agent Commision posted from " + frDt + " to " + toDt + "','SYSTEM'," + trsno + ",CURRENT_TIMESTAMP,"
                                + "'','" + entryBrnCode + "',"
                                + "'" + obj.getAcno().substring(0, 2) + "',0,'','')").executeUpdate();

                        if (insertResult1 <= 0) {
                            ut.rollback();
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + obj.getAcno());
                        }

                        int insertResult = em.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                                + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                                + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                                + "tran_id,adviceno,advicebrncode) values('" + consoleGlHead + "',"
                                + "'DDS_COMMISION_HEAD','" + ymd.format(new Date()) + "',"
                                + "'" + ymd.format(new Date()) + "',0," + obj.getNetAmt() + ",1,2,"
                                + "" + recno + ",'',NULL,3,0,'Y','" + user + "',"
                                + "131 ,0,'','Agent Commision posted from " + frDt + " to " + toDt + "','SYSTEM'," + trsno + ",CURRENT_TIMESTAMP,"
                                + "'','" + entryBrnCode + "',"
                                + "'" + obj.getAcno().substring(0, 2) + "',0,'','')").executeUpdate();

                        if (insertResult <= 0) {
                            ut.rollback();
                            throw new Exception("Data insertion problem in transfer scroll for account number: " + obj.getAcno());
                        }


                        msg = fts.updateBalance(acctNature, obj.getAcno(), obj.getNetAmt(), 0, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            throw new ApplicationException(msg);
                        }
                    }
                }
            }
            String acType = "";
            List list2 = em.createNativeQuery("select AcctCode from accounttypemaster where acctnature='DS'").getResultList();
            if (!list2.isEmpty()) {
                Vector vt = (Vector) list2.get(0);
                acType = vt.get(0).toString();
            } else {
                ut.rollback();
                return "You need to define daily deposit scheme nature.";
            }

            int m = em.createNativeQuery("insert into post_history(postFlag,actype,todate,fromdate,enterby,auth,trantime,authby,brnCode) "
                    + "values('CP','" + acType + "','" + ymd.format(dmy.parse(toDt)) + "','" + ymd.format(dmy.parse(frDt)) + "','" + user + "','Y',now(),'" + user + "','" + entryBrnCode + "')").executeUpdate();
            if (m < 0) {
                throw new Exception("Problem In post_history Insertion");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
            throw new ApplicationException(ex.getMessage());
        }
        return "true";
    }

    public String insertDeafTransaction(String reportBranchCode, String acNature, List<UnclaimedAccountStatementPojo> unClaimedList,
            String tillIntDt, String userName, String flag, String acType, String savingRoi, String finalDeafDt, String curDt) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            double totalUnClaimedCrAmount = 0;
            String accountTable = fts.getAccountTable(acNature);
            Float unClaimTrsno = fts.getTrsNo();
            String todayDt = ymd.format(dmy.parse(curDt));
            String intTillDt = ymd.format(dmy.parse(tillIntDt));

            List list = em.createNativeQuery("select ifnull(glheadint,'') as int_head,ifnull(unclaimed_head,'') as "
                    + "unclaimed_head from accounttypemaster where acctcode='" + acType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Please define interest head for A/c code-->" + acType);
            }
            Vector element = (Vector) list.get(0);
            String intGlHead = reportBranchCode + element.get(0).toString() + "01";
            String unClaimedHead = reportBranchCode + element.get(1).toString();
            if (intGlHead.equals("") || intGlHead.length() != 12 || unClaimedHead.equals("") || unClaimedHead.length() != 12) {
                throw new ApplicationException("Please define proper Int and Unclaimed Gl head.");
            }

            for (UnclaimedAccountStatementPojo pojo : unClaimedList) {
//                String detailDt = "";
                //   double totalInterest = 0;
                //Interest Posting If Any.
//                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                    list = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
//                            + "cbs_loan_acc_mast_sec where acno='" + pojo.getAcNo() + "'").getResultList();
//                    if (list == null || list.isEmpty()) {
//                        throw new ApplicationException("Please define next interest calculation "
//                                + "date for A/c-->" + pojo.getAcNo());
//                    }
//                    element = (Vector) list.get(0);
//                    String fromDt = element.get(0).toString();
////                    detailDt = fromDt;
//
//                    List<LoanIntCalcList> resultList = sbRemote.cbsSbIntCalc("I", "", acType, pojo.getAcNo(),
//                            fromDt, tillIntDt, reportBranchCode);
//                    if (!resultList.isEmpty()) {
//                        for (LoanIntCalcList lict : resultList) {
//                            totalInterest = lict.getTotalInt();
//                        }
//                    }
//                } else if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
//                    list = em.createNativeQuery("select cast(roi as decimal(14,2)) as roi,date_format(fddt,'%Y%m%d') as fddt "
//                            + "from td_vouchmst where acno='" + pojo.getAcNo() + "' and "
//                            + "voucherno=" + pojo.getReceiptNo() + "").getResultList();
//                    if (list == null || list.isEmpty()) {
//                        throw new ApplicationException("There is no entry in td vouchmst for a/c: " + pojo.getAcNo() + " and "
//                                + "voucher no: " + pojo.getReceiptNo());
//                    }
//                    element = (Vector) list.get(0);
//                    float roi = Float.parseFloat(element.get(0).toString());
//                    String fddt = element.get(1).toString();
////                    detailDt = dmy.format(ymd.parse(fddt));
//
//                    long fullDt = CbsUtil.dayDiff(ymd.parse(fddt), dmy.parse(pojo.getLastTrnDt()));
//                    String year = String.valueOf(fullDt / 365);
//                    fullDt = fullDt % 365;
//                    String mon = String.valueOf(fullDt / 30);
//                    String day = String.valueOf(fullDt % 30);
//
//                    List<Double> tdGlobalValue = autoRenewRemote.tdGlobal(pojo.getAcNo(), Float.parseFloat(pojo.getReceiptNo()), "True", roi, 0, year, mon, day);
//
//                    double fdInterest = Double.parseDouble(formatter.format(tdGlobalValue.get(3) - tdGlobalValue.get(4)));
//                    if (pojo.getProvFlag().equalsIgnoreCase("Y")) {
//                        fdInterest = Double.parseDouble(formatter.format(tdGlobalValue.get(3)));
//                    }
//
//                    double outSt = Double.parseDouble(formatter.format(pojo.getAmount() + fdInterest));
//                    String savingFrDt = CbsUtil.dateAdd(ymd.format(dmy.parse(pojo.getLastTrnDt())), 1);
//
//                    String intCode = fts.getCodeFromCbsParameterInfo("UND-INTEREST-CODE");
//
//                    List<SavingIntRateChangePojo> dataList = sbRemote.getSavingRoiChangeDetail(intCode, savingFrDt, intTillDt);
//                    if (dataList.isEmpty()) {
//                        throw new ApplicationException("There is no slab for saving interest calculation.");
//                    }
//                    totalInterest = fdInterest;
//                    for (int k = 0; k < dataList.size(); k++) {
//                        SavingIntRateChangePojo obj = dataList.get(k);
//
//                        String slabFrDt = obj.getFrDt();
//                        String slabToDt = obj.getToDt();
//                        double sbRoi = obj.getRoi();
//
//                        Long savingDiff = CbsUtil.dayDiff(ymd.parse(slabFrDt), ymd.parse(slabToDt));
//                        double savingInterest = 0;
//                        if (savingDiff > 0) {
//                            savingInterest = sbRoi * savingDiff.doubleValue() * outSt / 36500;
//                        }
//                        totalInterest = totalInterest + savingInterest;
//                    }
//                    //totalInterest = Double.parseDouble(formatter.format(CbsUtil.round(totalInterest,0)));
//                } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
//                    if (acType.equalsIgnoreCase(CbsAcCodeConstant.CASH_CREDIT.getAcctCode()) || acType.equalsIgnoreCase(CbsAcCodeConstant.OVER_DRAFT.getAcctCode())) {
//                        list = em.createNativeQuery("select date_format(next_int_calc_dt,'%d/%m/%Y') as fromDt from "
//                                + "cbs_loan_acc_mast_sec where acno='" + pojo.getAcNo() + "'").getResultList();
//                        if (list == null || list.isEmpty()) {
//                            throw new ApplicationException("Please define next interest calculation "
//                                    + "date for A/c-->" + pojo.getAcNo());
//                        }
//                        element = (Vector) list.get(0);
//                        String fromDt = element.get(0).toString();
////                        detailDt = fromDt;
//                        String glHead = loanRemote.getGlHeads(acType);
//                        List<LoanIntCalcList> resultList = loanRemote.cbsLoanIntCalc("I", acType, pojo.getAcNo(), fromDt, tillIntDt, glHead, "", reportBranchCode);
//                        if (!resultList.isEmpty()) {
//                            for (LoanIntCalcList lict : resultList) {
//                                totalInterest = lict.getTotalInt();
//                            }
//                        }
//                    }
//                }
                //Interest Posting If any.
                // totalInterest = pojo.getInterest();
                if (pojo.getInterest() > 0) {
                    Float trsno = fts.getTrsNo();
                    msg = fts.insertRecons(acNature, pojo.getAcNo(), 0, pojo.getInterest(), todayDt, todayDt, 2,
                            "Int Till " + tillIntDt + " in unclaimed marking", userName, trsno,
                            "", fts.getRecNo(), "Y", userName, 8, 3, "", todayDt, 0f, "", "", 0, "",
                            0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = fts.updateBalance(acNature, pojo.getAcNo(), pojo.getInterest(), 0, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = fts.insertRecons(fts.getAccountNature(intGlHead), intGlHead, 1, pojo.getInterest(),
                            todayDt, todayDt, 2, "Int Till " + tillIntDt + " in unclaimed marking",
                            userName, trsno, "", fts.getRecNo(), "Y", userName, 8, 3, "", todayDt,
                            0f, "", "", 0, "", 0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                    msg = fts.updateBalance(fts.getAccountNature(intGlHead), intGlHead, 0,
                            pojo.getInterest(), "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }
                }
                //Un-Claimed Marking.
                double partyBal = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                        || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    list = em.createNativeQuery("select cast(ifnull(sum(cramt-dramt),0)as decimal(14,2)) from td_recon "
                            + "where acno = '" + pojo.getAcNo() + "' and closeflag is null and trantype<>27 ").getResultList();
                    element = (Vector) list.get(0);
                    partyBal = Double.parseDouble(element.get(0).toString());
                } else {
                    partyBal = Double.parseDouble(formatter.format(Double.parseDouble(fts.ftsGetBal(pojo.getAcNo()))));
                }
                if (partyBal > 0) {
                    totalUnClaimedCrAmount += partyBal;

                    msg = fts.insertRecons(acNature, pojo.getAcNo(), 1, partyBal, todayDt, todayDt, 2,
                            "Unclaimed Marking Dr Amount", userName, unClaimTrsno,
                            "", fts.getRecNo(), "Y", userName, 0, 3, "", todayDt, 0f, "", "", 0, "",
                            0f, "", null, reportBranchCode, reportBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    double updateBal = partyBal;
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                            || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        updateBal = pojo.getInterest();
                    }
                    msg = fts.updateBalance(acNature, pojo.getAcNo(), 0, updateBal, "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new ApplicationException(msg);
                    }

                    if (flag.equalsIgnoreCase("R")) {   //For Bulk Marking.
                        int result = em.createNativeQuery("insert into accountstatus(acno,remark,spflag,dt,amount,"
                                + "enterby,auth,effdt,baseacno,voucher_no,authby,trantime) values ('" + pojo.getAcNo() + "','DEAF',"
                                + "'15','" + todayDt + "'," + partyBal + ",'" + userName + "','Y','" + intTillDt + "',"
                                + "'" + pojo.getAcNo() + "'," + Double.parseDouble(pojo.getReceiptNo()) + ",'System',now())").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In A/c Status Table Insertion. " + pojo.getAcNo());
                        }
                        result = em.createNativeQuery("update " + accountTable + " set accstatus=15 where "
                                + "acno='" + pojo.getAcNo() + "'").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In A/c Table Updation. " + pojo.getAcNo());
                        }
                        if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                            Query updateQ1 = em.createNativeQuery("UPDATE td_vouchmst SET Status='C',cldt='" + todayDt + "' WHERE ACNO='" + pojo.getAcNo()
                                    + "' and voucherno=" + Float.parseFloat(pojo.getReceiptNo()));
                            int varQ1 = updateQ1.executeUpdate();
                            if (varQ1 <= 0) {
                                throw new ApplicationException("Problem in data updation");
                            }
                        }
                        //Interest Date Updation.
                        List checkList = em.createNativeQuery("select * from cbs_loan_acc_mast_sec where "
                                + "acno='" + pojo.getAcNo() + "'").getResultList();
                        if (!checkList.isEmpty()) {
                            result = em.createNativeQuery("update cbs_loan_acc_mast_sec set "
                                    + "int_calc_upto_dt='" + intTillDt + "',next_int_calc_dt='" + CbsUtil.dateAdd(intTillDt, 1) + "' where "
                                    + "acno='" + pojo.getAcNo() + "'").executeUpdate();
                            if (result <= 0) {
                                throw new ApplicationException("Problem In A/c Table Updation In Cbs Loan "
                                        + "Secondary Table. " + pojo.getAcNo());
                            }
                        }
                    } else {    //Not In Use
                        int result = em.createNativeQuery("update accountstatus a inner join(select max(spno) as spno from "
                                + "accountstatus where acno='" + pojo.getAcNo() + "' and spflag=15 and "
                                + "effdt='" + todayDt + "') b on a.spno=b.spno set amount = " + partyBal + " where "
                                + "acno='" + pojo.getAcNo() + "' and spflag=15 and effdt='" + todayDt + "'").executeUpdate();
                        if (result <= 0) {
                            throw new ApplicationException("Problem In Account Status Table Updation. " + pojo.getAcNo());
                        }
                    }
                }
            }
            //Unclaimed Gl Cr amount.
            if (totalUnClaimedCrAmount > 0) {
                msg = fts.insertRecons(fts.getAccountNature(unClaimedHead), unClaimedHead, 0, Double.parseDouble(formatter.format(totalUnClaimedCrAmount)),
                        todayDt, todayDt, 2, "Unclaimed Marking Cr Amount", userName, unClaimTrsno, "", fts.getRecNo(),
                        "Y", userName, 0, 3, "", todayDt, 0f, "", "", 0, "", 0f, "", null, reportBranchCode, reportBranchCode,
                        0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                msg = fts.updateBalance(fts.getAccountNature(unClaimedHead), unClaimedHead,
                        totalUnClaimedCrAmount, 0, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
                //Insertion of posting details.
                list = em.createNativeQuery("select ifnull(max(sno),0)+1 as sno from cbs_loan_acctype_interest_parameter").getResultList();
                element = (Vector) list.get(0);
                int sno = Integer.parseInt(element.get(0).toString());

                int result = em.createNativeQuery("INSERT INTO cbs_loan_acctype_interest_parameter(SNO,AC_TYPE,FROM_DT,TO_DT,"
                        + "POST_FLAG,DT,POST_DT,BRNCODE,ENTER_BY,FLAG) VALUES(" + sno + ",'" + acType + "','" + finalDeafDt + "',"
                        + "'" + intTillDt + "','Y','" + ymd.format(new Date()) + "',now(),'" + reportBranchCode + "','" + userName + "',"
                        + "'UC')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem In Cbs Loan Acctype Interest Parameter "
                            + "Table Insertion. ");
                }
            }
            ut.commit();
            return msg = "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public List<TdActiveReportPojo> principalWiseFdDetailData(String brnCode, String frDt, String toDt, String frAmt, String toAmt, String repType, String dateType, String actCategory) throws ApplicationException {
        List<TdActiveReportPojo> dataList = new ArrayList<TdActiveReportPojo>();
        List result = new ArrayList();
        try {
            String table = "", condition = "";
            if (actCategory.equalsIgnoreCase("A")) {
                table = "";
                condition = "";
            } else if (actCategory.equalsIgnoreCase("M")) {
                table = ",share_holder sh";
                condition = "and cd.customerid= sh.custid and sh.regdate <='" + toDt + "'";
            } else if (actCategory.equalsIgnoreCase("N")) {
                table = "";
                condition = "and cd.customerid not in(select custid from share_holder)";
            }

            if (brnCode.equalsIgnoreCase("0A")) {
                if (repType.equalsIgnoreCase("Su")) {
                    if (dateType.equalsIgnoreCase("B")) {
                        result = em.createNativeQuery("select ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 ,cast(sum(vm.prinamt) as decimal(14,2))"
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " where vm.acno=ac.acno and vm.acno = "
                                + "ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and fddt between '" + frDt + "' and '" + toDt + "' and "
                                + "(cldt is null or cldt > '" + toDt + "') and ci.custid in (select ci.custid from td_vouchmst tv,"
                                + "customerid ci where tv.acno = ci.acno and fddt between '" + frDt + "' and '" + toDt + "' and (cldt is null or cldt > '" + toDt + "') group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "group by ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 order by cast(sum(vm.prinamt) as decimal(14,2))").getResultList();
                    } else {
                        result = em.createNativeQuery("select ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 ,cast(sum(vm.prinamt) as decimal(14,2))"
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " where vm.acno=ac.acno and vm.acno = "
                                + "ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and "
                                + "(cldt is null or cldt > '" + toDt + "') and ci.custid in (select ci.custid from td_vouchmst tv,"
                                + "customerid ci where tv.acno = ci.acno and (cldt is null or cldt > '" + toDt + "') "
                                + "group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "group by ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 order by cast(sum(vm.prinamt) as decimal(14,2))").getResultList();
                    }
                } else {
                    if (dateType.equalsIgnoreCase("B")) {
                        result = em.createNativeQuery("select ci.custid,vm.acno,cd.custname,cd.fathername,vm.voucherno,cast(vm.prinamt as decimal(14,2)),"
                                + "vm.receiptno,vm.fddt,vm.roi,vm.matdt,cd.pan_girnumber,cd.PerAddressLine1,ifnull(date_format(vm.cldt,'%d/%m/%Y'),''),vm.status "
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " "
                                + "where vm.acno=ac.acno and vm.acno=ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and fddt between '" + frDt + "' and '"
                                + toDt + "' and (cldt is null or cldt > '" + toDt + "') and ci.custid in (select ci.custid from td_vouchmst tv,"
                                + "customerid ci where tv.acno = ci.acno and fddt between '" + frDt + "' and '" + toDt + "' and (cldt is null or cldt > '" + toDt + "') group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "order by cast(cd.customerid as unsigned)").getResultList();
                    } else {
                        result = em.createNativeQuery("select ci.custid,vm.acno,cd.custname,cd.fathername,vm.voucherno,cast(vm.prinamt as decimal(14,2)),"
                                + "vm.receiptno,vm.fddt,vm.roi,vm.matdt,cd.pan_girnumber,cd.PerAddressLine1,ifnull(date_format(vm.cldt,'%d/%m/%Y'),''),vm.status "
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " "
                                + "where vm.acno=ac.acno and vm.acno=ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and (cldt is null or cldt >'" + toDt + "') "
                                + "and ci.custid in (select ci.custid from td_vouchmst tv,"
                                + "customerid ci where tv.acno = ci.acno and (cldt is null or cldt > '" + toDt + "') "
                                + "group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "order by cast(cd.customerid as unsigned)").getResultList();
                    }
                }
            } else {
                if (repType.equalsIgnoreCase("Su")) {
                    if (dateType.equalsIgnoreCase("B")) {
                        result = em.createNativeQuery("select ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 ,cast(sum(vm.prinamt) as decimal(14,2))"
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " where vm.acno=ac.acno and vm.acno = "
                                + "ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and fddt between '" + frDt + "' and '" + toDt + "' and "
                                + "(cldt is null or cldt > '" + toDt + "') and substring(vm.acno,1,2)='" + brnCode + "' and "
                                + "ci.custid in (select ci.custid from td_vouchmst tv,customerid ci where tv.acno = ci.acno and fddt between '" + frDt
                                + "' and '" + toDt + "' and (cldt is null or cldt > '" + toDt + "') group by ci.custid having "
                                + "sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') group by ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,"
                                + "cd.PerAddressLine1 order by cast(sum(vm.prinamt) as decimal(14,2))").getResultList();
                    } else {
                        result = em.createNativeQuery("select ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,cd.PerAddressLine1 ,cast(sum(vm.prinamt) as decimal(14,2))"
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " where vm.acno=ac.acno and vm.acno = "
                                + "ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and "
                                + "(cldt is null or cldt > '" + toDt + "') and substring(vm.acno,1,2)='" + brnCode + "' and "
                                + "ci.custid in (select ci.custid from td_vouchmst tv,customerid ci where tv.acno = ci.acno and (cldt is null or cldt > '" + toDt + "') group by ci.custid having "
                                + "sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') group by ci.custid,cd.custname,cd.fathername,cd.pan_girnumber,"
                                + "cd.PerAddressLine1 order by cast(sum(vm.prinamt) as decimal(14,2))").getResultList();
                    }

                } else {
                    if (dateType.equalsIgnoreCase("B")) {
                        result = em.createNativeQuery("select ci.custid,vm.acno,cd.custname,cd.fathername,vm.voucherno,cast(vm.prinamt as decimal(14,2)),"
                                + "vm.receiptno,vm.fddt,vm.roi,vm.matdt,cd.pan_girnumber,cd.PerAddressLine1,ifnull(date_format(vm.cldt,'%d/%m/%Y'),''),vm.status "
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " "
                                + "where vm.acno=ac.acno and vm.acno=ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and substring(vm.acno,1,2)='"
                                + brnCode + "' and fddt between '" + frDt + "' and '" + toDt + "' and (cldt is null or cldt > '" + toDt + "') and ci.custid in (select ci.custid from td_vouchmst tv,customerid ci where tv.acno = ci.acno and fddt "
                                + "between '" + frDt + "' and '" + toDt + "' and (cldt is null or cldt > '" + toDt + "') "
                                + "group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "order by cast(cd.customerid as unsigned)").getResultList();
                    } else {
                        result = em.createNativeQuery("select ci.custid,vm.acno,cd.custname,cd.fathername,vm.voucherno,cast(vm.prinamt as decimal(14,2)),"
                                + "vm.receiptno,vm.fddt,vm.roi,vm.matdt,cd.pan_girnumber,cd.PerAddressLine1,ifnull(date_format(vm.cldt,'%d/%m/%Y'),''),vm.status "
                                + "from td_vouchmst vm,td_accountmaster ac,customerid ci,cbs_customer_master_detail cd " + table + " "
                                + "where vm.acno=ac.acno and vm.acno=ci.acno and ci.custid= cast(cd.customerid as unsigned) " + condition + " and substring(vm.acno,1,2)='"
                                + brnCode + "' and (cldt is null or cldt > '" + toDt + "') and ci.custid in (select ci.custid from td_vouchmst tv,customerid ci where tv.acno = ci.acno and (cldt is null or cldt > '" + toDt + "') "
                                + "group by ci.custid having sum(prinamt) between '" + frAmt + "' and '" + toAmt + "') "
                                + "order by cast(cd.customerid as unsigned)").getResultList();
                    }
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector element = (Vector) result.get(i);
                    TdActiveReportPojo pojo = new TdActiveReportPojo();
                    if (repType.equalsIgnoreCase("SU")) {

                        pojo.setCustId(element.get(0).toString());
                        pojo.setCustName(element.get(1).toString());
                        pojo.setFathername(element.get(2).toString());
                        pojo.setAddress(element.get(4).toString());
                        pojo.setPanNo(element.get(3).toString());
                        pojo.setIntAmt(Double.parseDouble(element.get(5).toString()));
                    } else {

                        pojo.setCustId(element.get(0).toString());
                        pojo.setAcNo(element.get(1).toString());
                        pojo.setCustName(element.get(2).toString());
                        pojo.setFathername(element.get(3).toString());
                        pojo.setVouchNo(Float.parseFloat(element.get(4).toString()));
                        pojo.setIntAmt(Double.parseDouble(element.get(5).toString()));
                        pojo.setReceiptNo(Float.parseFloat(element.get(6).toString()));
                        pojo.setFdDt(ymd_1.parse(element.get(7).toString()));
                        pojo.setRoi(Float.parseFloat(element.get(8).toString()));
                        pojo.setMatDt(ymd_1.parse(element.get(9).toString()));
                        pojo.setPanNo(element.get(10).toString());
                        pojo.setAddress(element.get(11).toString());
                        pojo.setCloseDt(element.get(12).toString());
                        pojo.setStatus(element.get(13).toString().equalsIgnoreCase("C") ? "CLOSE" : "ACTIVE");
                    }
                    dataList.add(pojo);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<GuaranteeOnLoanPojo> getGauranteeOnLoanData(String brCode, String tillDate, String reportType, String acno) throws ApplicationException {
        List<GuaranteeOnLoanPojo> dataList = new ArrayList<GuaranteeOnLoanPojo>();
        List result = new ArrayList();
        try {
            String acWise = "";
            if (reportType.equalsIgnoreCase("AC")) {
                acWise = "and a.acno = '" + acno + "'";
            } else {
                acWise = "";
            }
            int isExceessEmi = common.isExceessEmi(tillDate);
            String bnkIdenty = fts.getCodeFromCbsParameterInfo("GRACE_PD_APP_IN_OVERDUE");
            if (brCode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select acno,name,Regfoliono from loan_guarantordetails a,share_holder b where date_format(a.trantime,'%Y%m%d') <= '" + tillDate + "' and a.gar_custid = b.custId " + acWise + " order by acno").getResultList();
            } else {
                result = em.createNativeQuery("select acno,name,Regfoliono from loan_guarantordetails a,share_holder b where date_format(a.trantime,'%Y%m%d') <= '" + tillDate + "' and a.gar_custid = b.custId and substring(acno,1,2)='" + brCode + "' " + acWise + " order by acno").getResultList();
            }
            int sno = 0;
            String prevLoanAcno = "";
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    GuaranteeOnLoanPojo pojo = new GuaranteeOnLoanPojo();
                    String loanAcno = vtr.get(0).toString();
                    String gaurantorName = vtr.get(1).toString();
                    String folioNo = vtr.get(2).toString();
                    if (prevLoanAcno.equalsIgnoreCase(loanAcno) || prevLoanAcno.equalsIgnoreCase("")) {
                        sno = sno + 1;
                        prevLoanAcno = loanAcno;
                    } else {
                        sno = 0;
                        sno = sno + 1;
                        prevLoanAcno = loanAcno;
                    }
                    List<OverdueEmiReportPojo> overdueEmiList = overDueReportFacade.getOverdueEmiList("", loanAcno, 1, 200, tillDate, common.getBrncodeByAcno(loanAcno), bnkIdenty, 0, isExceessEmi, null, 0);
                    if (!overdueEmiList.isEmpty()) {
                        double disbAmt = 0d;
                        String disbDate = "";
                        List disbList = em.createNativeQuery("select amtdisbursed,date_format(disbursementdt,'%d/%m/%Y') from loandisbursement where acno = '" + loanAcno + "' and sno in(select max(sno) from loandisbursement where acno = '" + loanAcno + "' and date_format(disbursementdt,'%Y%m%d') <='" + tillDate + "')and date_format(disbursementdt,'%Y%m%d') <='" + tillDate + "'").getResultList();
                        if (!disbList.isEmpty()) {
                            Vector disbVector = (Vector) disbList.get(0);
                            disbAmt = Double.parseDouble(disbVector.get(0).toString());
                            disbDate = disbVector.get(1).toString();
                        }
                        pojo.setGuaranteeName(gaurantorName);
                        pojo.setFolioNo(folioNo);
                        pojo.setLoanAcno(loanAcno);
                        pojo.setDisburseAmount(new BigDecimal(disbAmt));
                        pojo.setDisburseDate(disbDate);
                        pojo.setName(fts.getCustName(loanAcno));
                        pojo.setSno(sno);
                        pojo.setLoanBalance(new BigDecimal(common.getBalanceOnDate(loanAcno, tillDate)).abs());
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

    public List<FolioStatement> getFolioStatementData(String folio, String frDt, String toDt) throws ApplicationException {
        List<FolioStatement> dataList = new ArrayList<FolioStatement>();
        double openingbalance = 0d, disbAmt = 0d, totalDemand = 0d;;
        String disbDate = "";
        try {
            Map<String, Double> MemorandumMap = new HashMap<>();
            double MemorandumBal = 0d;
            List memList = em.createNativeQuery("select a.acno,ifnull(sum(cramt-dramt),0) from npa_recon a,customerid b,share_holder c where dt <='" + toDt + "' "
                    + "and b.custid = cast(c.custid as unsigned)and a.acno = b.acno and Regfoliono = '" + folio + "'group by acno").getResultList();
            if (!memList.isEmpty()) {
                for (int i = 0; i < memList.size(); i++) {
                    Vector vtr = (Vector) memList.get(0);
                    MemorandumMap.put(vtr.get(0).toString(), Double.parseDouble(vtr.get(1).toString()));
                }
            }
            List acList = common.getAcnoByFolio(folio);
            if (!acList.isEmpty()) {
                for (int i = 0; i < acList.size(); i++) {
                    Vector acVector = (Vector) acList.get(i);
                    String acNo = acVector.get(0).toString();

                    String acNature = fts.getAcNatureByCode(acNo.substring(2, 4));
                    String table = common.getTableName(acNature);
                    double npaOpbal = 0;
                    List npaopList = em.createNativeQuery("select ifnull(sum(ifnull(cramt,0)) - sum(ifnull(dramt,0)),0) from npa_recon where auth= 'y' and acno = '" + acNo + "' and dt <'" + frDt + "'").getResultList();
                    Vector element = (Vector) npaopList.get(0);
                    npaOpbal = Double.parseDouble(element.get(0).toString());


                    List disbList = em.createNativeQuery("select amtdisbursed,date_format(disbursementdt,'%d/%m/%Y') from loandisbursement where acno = '" + acNo + "' and sno in(select max(sno) from loandisbursement where acno = '" + acNo + "' and date_format(disbursementdt,'%Y%m%d') <='" + toDt + "')and date_format(disbursementdt,'%Y%m%d') <='" + toDt + "'").getResultList();
                    if (!disbList.isEmpty()) {
                        Vector disbVector = (Vector) disbList.get(0);
                        disbAmt = Double.parseDouble(disbVector.get(0).toString());
                        disbDate = disbVector.get(1).toString();
                    }
                    double opOverdue = 0d, overdue = 0d;
                    List opList = em.createNativeQuery("select cast(ifnull(sum(dmd_amt),0) as decimal(25,2)) from cbs_loan_dmd_table where acno = '" + acNo + "' and dmd_srl_num in(select max(dmd_srl_num) from cbs_loan_dmd_table where acno = '" + acNo + "' and DMD_DATE < '" + frDt + "')and DMD_DATE < '" + frDt + "'").getResultList();
                    // List opList = em.createNativeQuery("SELECT cast(ifnull(sum(DMD_AMT),0) - ifnull(sum(TOT_ADJ_AMT),0) as decimal(25,2)) FROM cbs_loan_dmd_table where acno = '" + acNo + "' and date_format(DMD_DATE,'%Y%m%d')<'" + frDt + "'").getResultList();
                    if (!opList.isEmpty()) {
                        Vector opverdueVector = (Vector) opList.get(0);
                        opOverdue = Double.parseDouble(opverdueVector.get(0).toString());
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(ymd.parse(frDt));
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    openingbalance = loanRemoteFacade.openingBalance(acNo, ymd.format(calendar.getTime()));
                    double bal1 = 0d, bal2 = 0d;
                    bal1 = openingbalance;

                    //Total Dmand Amount
                    List demadList = em.createNativeQuery("select cast(ifnull(sum(dmd_amt),0) as decimal(25,2)) from cbs_loan_dmd_table where acno = '" + acNo + "' "
                            + "and dmd_srl_num in(select max(dmd_srl_num) from cbs_loan_dmd_table where acno = '" + acNo + "' and DMD_DATE <= '" + toDt + "')"
                            + "and DMD_DATE <= '" + toDt + "'").getResultList();
                    if (!demadList.isEmpty()) {
                        Vector demadVector = (Vector) demadList.get(0);
                        double demand = Double.parseDouble(demadVector.get(0).toString());
                        totalDemand = totalDemand + demand;
                    }

                    List result = em.createNativeQuery("select txnDt from("
                            + "SELECT date_format(DMD_DATE,'%Y%m%d') txnDt FROM cbs_loan_dmd_table where acno = '" + acNo + "' and DMD_FLOW_ID <> 'PLOAN' and date_format(DMD_DATE,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' "
                            + "union  "
                            + "SELECT date_format(dt,'%Y%m%d') txnDt FROM " + table + " where acno = '" + acNo + "' and dt between '" + frDt + "' and '" + toDt + "' "
                            + "union "
                            + "select date_format(RECOVERYDT,'%Y%m%d') txnDt from cbs_loan_dmd_info where OFFICEID = (select area from share_holder where Regfoliono = '" + folio + "') and RECOVERYDT between '" + frDt + "' and '" + toDt + "')a  order by  txnDt").getResultList();
                    if (!result.isEmpty()) {
                        for (int j = 0; j < result.size(); j++) {
                            // FolioStatement pojo = new FolioStatement();
                            Vector txnVector = (Vector) result.get(j);
                            String txnDt = txnVector.get(0).toString();
                            double balance = common.getBalanceOnDate(acNo, txnDt);
                            String rDt = "", rFlag = "";
                            List txnList = new ArrayList();
                            if (balance < 0) {
                                List rdtList = em.createNativeQuery("select date_format(RECOVERYDT,'%Y%m%d') txnDt,flag from cbs_loan_dmd_info where OFFICEID = (select area from share_holder where Regfoliono = '" + folio + "') and RECOVERYDT = '" + txnDt + "'").getResultList();
                                if (!rdtList.isEmpty()) {
                                    Vector rVector = (Vector) rdtList.get(0);
                                    rDt = rVector.get(0).toString();
                                    rFlag = rVector.get(1).toString();
                                }

                                if (rFlag.equalsIgnoreCase("R")) {
                                    txnList = em.createNativeQuery("SELECT ifnull(dramt,0) DrAmt,ifnull(cramt,0) CrAmt,ifnull(details,'') particular,ifnull(ty,'') Ty,ifnull(trantype,'') FROM " + table + " where acno = '" + acNo + "' and dt = '" + txnDt + "'").getResultList();
                                }
                            }
                            if (txnList.isEmpty()) {
                                txnList = em.createNativeQuery("SELECT ifnull(dramt,0) DrAmt,ifnull(cramt,0) CrAmt,ifnull(details,'') particular,ifnull(ty,'') Ty,ifnull(trantype,'') FROM " + table + " where acno = '" + acNo + "' and dt = '" + txnDt + "' "
                                        + "union ALL "
                                        + "SELECT cast(ifnull(sum(DMD_AMT),0) as decimal(25,2)) DemandAmt,'0' CrAmt,'','Demand'Ty,''trantype FROM cbs_loan_dmd_table where acno = '" + acNo + "' and date_format(DMD_DATE,'%Y%m%d') = '" + txnDt + "'").getResultList();
                            }
//                            List txnList = em.createNativeQuery("select DrAmt,CrAmt,particular,DemandAmt,Ty from ((SELECT ifnull(dramt,0) DrAmt,ifnull(cramt,0) CrAmt,ifnull(details,'') particular,ifnull(ty,'') Ty FROM " + table + " where acno = '" + acNo + "' and dt = '" + txnDt + "')a,"
//                                    + "(SELECT cast(ifnull(sum(DMD_AMT),0) as decimal(25,2)) DemandAmt FROM cbs_loan_dmd_table where acno = '" + acNo + "' and date_format(DMD_DATE,'%Y%m%d') = '" + txnDt + "')b)").getResultList();
                            if (!txnList.isEmpty()) {
                                for (int k = 0; k < txnList.size(); k++) {
                                    FolioStatement pojo = new FolioStatement();
                                    Vector vtr = (Vector) txnList.get(k);
                                    String ty = vtr.get(3).toString();
                                    String tranType = vtr.get(4).toString();
                                    double withd = 0d;
                                    if (!ty.equalsIgnoreCase("Demand")) {
                                        withd = Double.parseDouble(vtr.get(0).toString());
                                    }
                                    double depositAmt = Double.parseDouble(vtr.get(1).toString());
                                    String detail = vtr.get(2).toString();

                                    double demandAmt = 0d;
                                    if (ty.equalsIgnoreCase("Demand")) {
                                        demandAmt = Double.parseDouble(vtr.get(0).toString());
                                    }

                                    bal1 = bal1 + depositAmt - withd;

                                    String txnType = "", txnyyyy = "", txnmm = "", demDt1 = "", demDt2 = "", pvDemandDt = "";
                                    double pvDemandAmt = 0d, creditAmt = 0d, overDueAmt = 0d;
//                                    if (!acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
//                                        if (detail.contains("Bulk Recovery") || rFlag.equalsIgnoreCase("R")) {
//                                            txnyyyy = txnDt.substring(0, 4);
//                                            txnmm = txnDt.substring(4, 6);
//                                            demDt2 = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(txnmm) - 1, Integer.parseInt(txnyyyy)));
//                                            demDt1 = ymd.format(dmy.parse(demDt2)).substring(0, 6) + "01";
//                                            List dmdList = em.createNativeQuery("select cast(ifnull(sum(DMD_AMT),0) as decimal(25,2)),ifnull(date_format(DMD_DATE,'%Y%m%d'),'') from cbs_loan_dmd_table where acno ='" + acNo + "' and DMD_DATE between '" + demDt1 + "' and '" + ymd.format(dmy.parse(demDt2)) + "'").getResultList();
//                                            Vector dmdVector = (Vector) dmdList.get(0);
//                                            pvDemandAmt = Double.parseDouble(dmdVector.get(0).toString());
//                                            pvDemandDt = dmdVector.get(1).toString();
//                                            List cashClearingList = em.createNativeQuery("select sum(cramt) from loan_recon where acno = '" + acNo + "' and dt between '" + pvDemandDt + "' and '" + txnDt + "'").getResultList();
//                                            Vector crVector = (Vector) cashClearingList.get(0);
//                                            creditAmt = Double.parseDouble(crVector.get(0).toString());
//                                            overDueAmt = pvDemandAmt - creditAmt;
//                                        }
//                                    }
                                    if (ty.equalsIgnoreCase("")) {
                                        txnType = "";
                                    } else if (ty.equalsIgnoreCase("0")) {
                                        txnType = "By ";
                                    } else if (ty.equalsIgnoreCase("1")) {
                                        txnType = "To ";
                                    } else if (ty.equalsIgnoreCase("demand")) {
                                        txnType = "Demand ";
                                    }
                                    String p2 = "";
                                    if (tranType.equalsIgnoreCase("0")) {
                                        p2 = " CSH ";
                                    } else if (tranType.equalsIgnoreCase("1")) {
                                        p2 = " CLR ";
                                    } else if (tranType.equalsIgnoreCase("8")) {
                                        p2 = " INT ";
                                    } else if (tranType.equalsIgnoreCase("2")) {
                                        p2 = " TRF ";
                                    }

                                    //new code as per over due list
                                    if (!acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                        List<OverDueListPojo> list = getOverDueListData(acNo.substring(0, 2), acNo.substring(2, 4), txnDt, acNo);
                                        if (!list.isEmpty()) {
                                            overDueAmt = list.get(0).getOveDue().doubleValue();
                                        }
                                    }

                                    //END new code as per over due list
                                    if (!ty.equalsIgnoreCase("demand")) {
                                        // if (balance != 0) {
                                        pojo.setFolioNo(folio);
                                        pojo.setAcNo(acNo);
                                        String period = "";
                                        String endDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(txnDt.substring(4, 6)), Integer.parseInt(txnDt.substring(0, 4))));
                                        String firstDt = endDt.substring(6, 10) + endDt.substring(3, 5) + "01";
                                        period = period = dmy.format(ymd.parse(firstDt)) + " to " + endDt;
                                        pojo.setTxnDt(dmy.format(ymd.parse(txnDt)));
                                        pojo.setCreditAmt(new BigDecimal(depositAmt));
                                        pojo.setDebitAmt(new BigDecimal(withd));
                                        if (overDueAmt > 0) {
                                            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                pojo.setParticular("");
                                            } else {
                                                pojo.setParticular("(Over Due)" + txnType + p2 + detail);
                                            }
                                        } else {
                                            if (ty.equalsIgnoreCase("demand")) {
                                                pojo.setParticular(txnType + p2 + detail + " [" + period + "]");
                                            } else {
                                                pojo.setParticular(txnType + p2 + detail);
                                            }
                                        }
                                        pojo.setDemandAmt(new BigDecimal(demandAmt));
                                        pojo.setLastDisburseDt(disbDate);
                                        pojo.setLastDisburseAmt(new BigDecimal(disbAmt));
                                        pojo.setOpeningBalance(new BigDecimal(openingbalance));
                                        //pojo.setBalance(new BigDecimal(balance));
                                        pojo.setBalance(new BigDecimal(bal1));
                                        //pojo.setOverdueAmt(new BigDecimal(demandAmt));
                                        if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            pojo.setOpningOverdueAmt(new BigDecimal("0"));
                                            pojo.setOverdueAmt(new BigDecimal("0"));
                                        } else {
                                            pojo.setOpningOverdueAmt(new BigDecimal(opOverdue));
                                            if (overDueAmt > 0) {
                                                pojo.setOverdueAmt(new BigDecimal(overDueAmt));
                                            } else {
                                                pojo.setOverdueAmt(new BigDecimal("0"));
                                            }
                                        }
                                        pojo.setTotalDemand(new BigDecimal(totalDemand));
                                        pojo.setAcNature(acNature);
                                        pojo.setMemorandumBal(Math.abs(MemorandumMap.get(acNo) == null ? 0 : MemorandumMap.get(acNo)));
                                        pojo.setTypeFlag("");
                                        dataList.add(pojo);
                                        //}
                                    } else if (rFlag.equalsIgnoreCase("R") && overDueAmt > 0) {
                                        pojo.setFolioNo(folio);
                                        pojo.setAcNo(acNo);
                                        String period = "";
                                        String endDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(txnDt.substring(4, 6)), Integer.parseInt(txnDt.substring(0, 4))));
                                        String firstDt = endDt.substring(6, 10) + endDt.substring(3, 5) + "01";
                                        period = period = dmy.format(ymd.parse(firstDt)) + " to " + endDt;
                                        pojo.setTxnDt(dmy.format(ymd.parse(txnDt)));
                                        pojo.setCreditAmt(new BigDecimal(depositAmt));
                                        pojo.setDebitAmt(new BigDecimal(withd));
                                        if (overDueAmt > 0 && rFlag.equalsIgnoreCase("R")) {
                                            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                pojo.setParticular("");
                                            } else {
                                                pojo.setParticular("(Over Due) " + txnType + p2 + detail);
                                            }
                                        } else {
                                            if (ty.equalsIgnoreCase("demand")) {
                                                pojo.setParticular(txnType + p2 + detail + " [" + period + "]");
                                            } else {
                                                pojo.setParticular(txnType + p2 + detail);
                                            }
                                        }
                                        pojo.setDemandAmt(new BigDecimal(demandAmt));
                                        pojo.setLastDisburseDt(disbDate);
                                        pojo.setLastDisburseAmt(new BigDecimal(disbAmt));
                                        pojo.setOpeningBalance(new BigDecimal(openingbalance));
                                        //pojo.setBalance(new BigDecimal(balance));
                                        pojo.setBalance(new BigDecimal(bal1));
                                        //pojo.setOverdueAmt(new BigDecimal(demandAmt));
                                        if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                            pojo.setOpningOverdueAmt(new BigDecimal("0"));
                                            pojo.setOverdueAmt(new BigDecimal("0"));
                                        } else {
                                            pojo.setOpningOverdueAmt(new BigDecimal(opOverdue));
                                            if (detail.contains("Bulk Recovery") || rFlag.equalsIgnoreCase("R")) {
                                                pojo.setOverdueAmt(new BigDecimal(overDueAmt));
                                            } else {
                                                pojo.setOverdueAmt(new BigDecimal("0"));
                                            }
                                        }
                                        pojo.setTotalDemand(new BigDecimal(totalDemand));
                                        pojo.setAcNature(acNature);
                                        pojo.setMemorandumBal(Math.abs(MemorandumMap.get(acNo) == null ? 0 : MemorandumMap.get(acNo)));
                                        pojo.setTypeFlag("");
                                        dataList.add(pojo);

                                    } else {
                                        if (balance != 0) {
                                            if (ty.equalsIgnoreCase("demand") && demandAmt != 0) {
                                                pojo.setFolioNo(folio);
                                                pojo.setAcNo(acNo);
                                                String period = "";
                                                String endDt = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(txnDt.substring(4, 6)), Integer.parseInt(txnDt.substring(0, 4))));
                                                String firstDt = endDt.substring(6, 10) + endDt.substring(3, 5) + "01";
                                                period = dmy.format(ymd.parse(firstDt)) + " to " + endDt;
                                                pojo.setTxnDt(dmy.format(ymd.parse(txnDt)));
                                                pojo.setCreditAmt(new BigDecimal(depositAmt));
                                                pojo.setDebitAmt(new BigDecimal(withd));
                                                if (ty.equalsIgnoreCase("demand")) {
                                                    pojo.setParticular(txnType + p2 + detail + " [" + period + "]");
                                                } else {
                                                    pojo.setParticular(txnType + p2 + detail);
                                                }
                                                pojo.setDemandAmt(new BigDecimal(demandAmt));
                                                pojo.setLastDisburseDt(disbDate);
                                                pojo.setLastDisburseAmt(new BigDecimal(disbAmt));
                                                pojo.setOpeningBalance(new BigDecimal(openingbalance));
                                                //pojo.setBalance(new BigDecimal(balance));
                                                pojo.setBalance(new BigDecimal(bal1));
                                                // pojo.setOverdueAmt(new BigDecimal(demandAmt));
                                                if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                                                    pojo.setOpningOverdueAmt(new BigDecimal("0"));
                                                    pojo.setOverdueAmt(new BigDecimal("0"));
                                                } else {
                                                    pojo.setOpningOverdueAmt(new BigDecimal(opOverdue));
                                                    if (detail.contains("Bulk Recovery")) {
                                                        pojo.setOverdueAmt(new BigDecimal(overDueAmt));
                                                    } else {
                                                        pojo.setOverdueAmt(new BigDecimal(overDueAmt));
                                                    }
                                                }
                                                pojo.setAcNature(acNature);
                                                pojo.setTotalDemand(new BigDecimal(totalDemand));
                                                pojo.setMemorandumBal(Math.abs(MemorandumMap.get(acNo) == null ? 0 : MemorandumMap.get(acNo)));
                                                pojo.setTypeFlag("");
                                                dataList.add(pojo);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        FolioStatement pojo = new FolioStatement();
                        pojo.setFolioNo(folio);
                        pojo.setAcNo(acNo);
                        pojo.setTxnDt("");
                        pojo.setCreditAmt(new BigDecimal(0));
                        pojo.setDebitAmt(new BigDecimal(0));
                        pojo.setParticular("");
                        pojo.setDemandAmt(new BigDecimal(0));
                        pojo.setLastDisburseDt(disbDate);
                        pojo.setLastDisburseAmt(new BigDecimal(disbAmt));
                        pojo.setOpeningBalance(new BigDecimal(openingbalance));
                        pojo.setBalance(new BigDecimal(bal1));
                        pojo.setOverdueAmt(new BigDecimal(0));
                        pojo.setAcNature(acNature);
                        pojo.setTotalDemand(new BigDecimal(totalDemand));
                        pojo.setMemorandumBal(Math.abs(MemorandumMap.get(acNo) == null ? 0 : MemorandumMap.get(acNo)));
                        pojo.setTypeFlag("");
                        dataList.add(pojo);


                    }
                    /*Add on Npa Transaction */
                    double bal = 0;
                    double balance = common.getBalanceOnDate(acNo, toDt);
                    balance = balance + npaOpbal;
                    List<NpaAccStmPojo> npaList = loanRemoteFacade.npaAccountStatement(acNo, frDt, toDt);
                    for (int j = 0; j < npaList.size(); j++) {
                        FolioStatement pojo = new FolioStatement();
                        NpaAccStmPojo npaPojo = npaList.get(j);
                        double overDueAmt = 0d;
                        List<OverDueListPojo> list = getOverDueListData(acNo.substring(0, 2), acNo.substring(2, 4), ymd.format(npaPojo.getNpaDate()), acNo);
                        if (!list.isEmpty()) {
                            overDueAmt = list.get(0).getOveDue().doubleValue();
                        }
                        if (j == 0) {
                            bal = bal + balance + npaPojo.getNpaDeposit().doubleValue() - npaPojo.getNpaWithDrawl().doubleValue();
                        } else {
                            bal = bal + npaPojo.getNpaDeposit().doubleValue() - npaPojo.getNpaWithDrawl().doubleValue();
                        }
                        pojo.setFolioNo(folio);
                        pojo.setAcNo(acNo);
                        pojo.setTxnDt(dmy.format(npaPojo.getNpaDate()));
                        pojo.setCreditAmt(npaPojo.getNpaDeposit());
                        pojo.setDebitAmt(npaPojo.getNpaWithDrawl());
                        pojo.setParticular(npaPojo.getNpaParticulars());
                        pojo.setTypeFlag("1");
                        pojo.setOpeningBalance(npaPojo.getNpaOpenBal());
                        pojo.setNpaOpeningBalance(new BigDecimal(npaOpbal));
                        // pojo.setBalance(npaPojo.getNpaBalance());
                        pojo.setBalance(new BigDecimal(bal));
                        pojo.setDemandAmt(BigDecimal.ZERO);
                        pojo.setOverdueAmt(new BigDecimal(overDueAmt));
                        dataList.add(pojo);
                    }
                    /*End of Add on Npa Transaction */
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<VoucherPrintingPojo> getVoucherPrintingData(String branch, String txnMode, String date, String voucherNo) throws ApplicationException {
        List<VoucherPrintingPojo> dataList = new ArrayList<VoucherPrintingPojo>();
        List result = new ArrayList();
        try {
//            String txnType = "";
//            if (txnMode.equalsIgnoreCase("2")) {
//                txnType = "and trsno = " + voucherNo;
//            } else if (txnMode.equalsIgnoreCase("00")) {
//                txnType = "and tokenno = " + voucherNo;
//            } else if (txnMode.equalsIgnoreCase("01")) {
//                txnType = "and recno = " + voucherNo;
//            } else {
//                txnType = "and recno = " + voucherNo;
//            }
            if (txnMode.equalsIgnoreCase("0")) {
                //result = em.createNativeQuery("select acno,custname,dramt,cramt from tokentable_credit where auth = 'N' and dt = '" + date + "' and trantype = 0 and org_brnid = '" + branch + "' " + txnType).getResultList();
                result = em.createNativeQuery("select acno,custname,dramt,cramt from tokentable_credit where auth = 'N' and dt = '" + date + "' and trantype = 0 and acno = '" + voucherNo + "' and org_brnid = '" + branch + "' "
                        + "union All "
                        + "select acno,custname,dramt,cramt from tokentable_debit where auth = 'N' and dt = '" + date + "' and trantype = 0 and acno = '" + voucherNo + "' and org_brnid = '" + branch + "'").getResultList();
            } //            else if (txnMode.equalsIgnoreCase("01")) {
            //                result = em.createNativeQuery("select acno,custname,dramt,cramt from tokentable_debit where auth = 'N' and dt = '" + date + "' and trantype = 0 and org_brnid = '" + branch + "'" + txnType).getResultList();
            //            } 
            else if (txnMode.equalsIgnoreCase("1")) {
                result = em.createNativeQuery("select acno,custname,dramt,cramt from  recon_clg_d where auth = 'N' and dt = '" + date + "' and trantype = 1 and acno = '" + voucherNo + "' and org_brnid = '" + branch + "'").getResultList();
            } else if (txnMode.equalsIgnoreCase("2")) {
                result = em.createNativeQuery("select acno,custname,dramt,cramt from recon_trf_d where auth = 'N' and dt = '" + date + "' and trantype = 2 and org_brnid = '" + branch + "' and trsno = " + voucherNo).getResultList();
            }

            double totalCrAmt = 0d, totalDrAmt = 0d, netPaid = 0d;
            Spellar word = new Spellar();
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    VoucherPrintingPojo pojo = new VoucherPrintingPojo();
                    double crAmt = Double.parseDouble(vtr.get(3).toString());
                    totalCrAmt = totalCrAmt + crAmt;
                    double drAmt = Double.parseDouble(vtr.get(2).toString());
                    totalDrAmt = totalDrAmt + drAmt;
                    netPaid = totalCrAmt - totalDrAmt;
                    pojo.setAcNo(vtr.get(0).toString());
                    pojo.setAcName(vtr.get(1).toString());
                    pojo.setDrAmt(drAmt);
                    pojo.setCrAmt(crAmt);
                    pojo.setNetPaid(Math.abs(netPaid));
                    pojo.setAmtWord(word.spellAmount(Math.abs(netPaid)));
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ChallanDeducteePojo> getChallanDeducteeData(String reportType, String frDt, String toDt) throws ApplicationException {
        List<ChallanDeducteePojo> dataList = new ArrayList<ChallanDeducteePojo>();

        List result = new ArrayList();
        try {
            double tds = 0d, tdsRateWithPan = 0d, tdsRateNoPan = 0d;
            List tdsList = em.createNativeQuery("SELECT ifnull(TDS_AMOUNT,-1),ifnull(TDS_RATE *(1 + TDS_SURCHARGE /100.0),-1),tdsRate_pan "
                    + "FROM tdsslab WHERE TYPE=1 AND TDS_APPLICABLEDATE IN(SELECT MAX(TDS_APPLICABLEDATE) FROM tdsslab WHERE TDS_APPLICABLEDATE<='" + toDt + "' AND TYPE=1)").getResultList();
            if (!tdsList.isEmpty()) {
                Vector tdVector = (Vector) tdsList.get(0);
                tds = Double.parseDouble(tdVector.get(0).toString());
                tdsRateWithPan = Double.parseDouble(tdVector.get(1).toString());
                tdsRateNoPan = Double.parseDouble(tdVector.get(2).toString());
            }
            result = em.createNativeQuery("select cast(cmd.customerid as unsigned),ina.intAmt,ifnull(ina.tdsAmt,0),ina.maxdt,cmd.CustFullName,ifnull(if (exists(select ifnull(IdentityNo,'') "
                    + "from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),"
                    + "(select ifnull(IdentityNo,'') from  cbs_customer_master_detail where customerid = cmd.customerid and legal_document = 'C' and CustEntityType = '01'),"
                    + "(select ifnull(aa.IdentityNo,'') from  cbs_cust_identity_details aa,cbs_customer_master_detail bb where aa.CustomerId =bb.customerid "
                    + "and aa.customerid = cmd.customerid and aa.IdentificationType = 'C'  and bb.CustEntityType = '01')), ifnull(cmd.PAN_GIRNumber,'')) as Pan,"
                    + "cmd.PerAddressLine1,ifnull(cmd.PerBlock,''), "
                    + "ifnull(cmd.PerDistrict,''),ifnull(cmd.PerPostalCode,''),r.REF_DESC StateCode,cmd.primarybrcode "
                    + "from cbs_customer_master_detail cmd,"
                    + "(select CustId,tdsAmt,maxDt,ifnull(interestAmt,0) intAmt from "
                    + "(select c.CustId, cast(ifnull(sum(a.TDS),0) as decimal(25,2)) as tdsAmt,ifnull(date_format(max(dt),'%d/%m/%Y'),'')maxDt "
                    + "from tds_reserve_history a, customerid c where a.Acno = c.Acno  and a.recovered = 'R' "
                    + "and a.tdsRecoveredDt between  '" + frDt + "' and '" + toDt + "' group by c.CustId)a "
                    + "left join "
                    + "(select cid,sum(intAmt)interestAmt from ("
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from td_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno  and a.dt between  '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from rd_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + "union all "
                    + "select c.CustId cid, cast(ifnull(sum(a.interest),0) as decimal(25,2)) as intAmt from dds_interesthistory a, customerid c "
                    + "where a.Acno = c.Acno and a.dt between '" + frDt + "' and '" + toDt + "' group by c.CustId "
                    + ")ff group by cid order by cid)b on a.CustId = b.cid order by CustId)ina,cbs_ref_rec_type r "
                    + "where cast(cmd.customerid as unsigned) = ina.CustId "
                    + "and cmd.PerStateCode = r.REF_CODE and REF_REC_NO = '002' order by cast(cmd.customerid as unsigned)"
                    + "").getResultList();

            if (reportType.equalsIgnoreCase("C")) {
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector ele = (Vector) result.get(i);
                        String custId = ele.get(0).toString();
                        double tdsAmt = Double.parseDouble(ele.get(2).toString());
                        ChallanDeducteePojo pojo = new ChallanDeducteePojo();

                        pojo.setChallanSerial(i + 1);                   //A
                        pojo.setIncomeTax(tdsAmt);                      //B
                        pojo.setInterest(0);                            //C
                        pojo.setFees(0);                                //D
                        pojo.setOtherPenalty(0);                        //E
                        pojo.setTotalTax(new BigDecimal(tdsAmt));       //F
                        pojo.setDepositBook("No");                      //G
                        pojo.setBsrCode("");                            //H
                        pojo.setTaxDeposit("");                         //I
                        pojo.setChallanDDoNo("");                       //J
                        pojo.setReceiptNo("Nill");                      //K
                        pojo.setMinorNo("200");                         //L
                        pojo.setChequeDDNo("Nill");                     //M
                        pojo.setSectionCode("Nill");                    //N
                        pojo.setBrCode(common.getAlphacodeByBrncode(ele.get(11).toString()));
                        dataList.add(pojo);
                    }
                }
            } else {
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector element = (Vector) result.get(i);
                        ChallanDeducteePojo pojo = new ChallanDeducteePojo();
                        String custId = element.get(0).toString();
                        //String acNo = element.get(1).toString();
                        double intAmt = Double.parseDouble(element.get(1).toString());
                        double tdsAmt = Double.parseDouble(element.get(2).toString());
                        String tdsDate = element.get(3).toString();
                        String custName = element.get(4).toString();
                        String panNo = element.get(5).toString();
                        String address = element.get(6).toString();
                        String blockBuildingName = element.get(7).toString();
                        String distict = element.get(8).toString();
                        String postalCode = element.get(9).toString();
                        String stateCode = element.get(10).toString();

                        pojo.setChallanSerial(i + 1);                   //A
                        pojo.setSectionCode("");                        //B
                        pojo.setTypeRent("Nill");                       //C
                        pojo.setDeducteeIdentificationNo(custId);       //D
                        pojo.setDeducteeCode(custId);                   //E
                        pojo.setDeducteePan(panNo);                     //F
                        pojo.setDeducteeRefNo(custId);                  //G
                        pojo.setNameOfDeductee(custName);               //H
                        pojo.setCreditAmtDate(tdsDate);                 //I
                        pojo.setTaxDeducteeDate(tdsDate);               //J
                        pojo.setPaymentAmt(intAmt); // dought           //K
                        if (panNo.trim().length() == 10) {
                            pojo.setTaxDeducteeRate(tdsRateWithPan);    //L
                        } else {
                            pojo.setTaxDeducteeRate(tdsRateNoPan);      //L 
                        }
                        pojo.setTaxDeducteeAmt(tdsAmt);                 //M
                        pojo.setTotalTaxDeposit(new BigDecimal(tdsAmt));//N
                        pojo.setReasonForDeduction("Nill");             //O
                        pojo.setCertificateNo("Nill");                  //P
                        pojo.setFlatNo("Nill");                         //Q
                        pojo.setDeducteeBuildingName(blockBuildingName);//R
                        pojo.setDeducteeStreetName(address);            //S
                        pojo.setDeducteeArea(address);                  //T
                        pojo.setDeducteeCityTown(distict);              //U
                        pojo.setDeducteePin(postalCode);                //V
                        pojo.setDeducteeState(stateCode);               //W
                        pojo.setBrCode(common.getAlphacodeByBrncode(element.get(11).toString()));
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<PostDatedChequePojo> getPostDatedChequieData(String brncode, String frDt, String toDt, String reportType) throws ApplicationException {
        List<PostDatedChequePojo> dataList = new ArrayList<PostDatedChequePojo>();
        List result = new ArrayList();
        try {
            String status = "";
            if (reportType.equalsIgnoreCase("A")) {
                status = "";
            } else {
                status = "and chqStatus = '" + reportType + "'";
            }
            if (brncode.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select acno_credit, cust_name, acno_debit, bank_name, branch_name, ifsc_code, chequeNo, "
                        + "date_format(ChequeDt,'%d/%m/%Y'), amount, chqStatus from pdc_details "
                        + "where chequedt between '" + frDt + "' and '" + toDt + "' " + status + " order by acno_credit,chqStatus").getResultList();
            } else {
                result = em.createNativeQuery("select acno_credit, cust_name, acno_debit, bank_name, branch_name, ifsc_code, chequeNo, "
                        + "date_format(ChequeDt,'%d/%m/%Y'), amount, chqStatus from pdc_details where org_brnid = '" + brncode + "' "
                        + "and chequedt between '" + frDt + "' and '" + toDt + "' " + status + " order by acno_credit,chqStatus").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    PostDatedChequePojo pojo = new PostDatedChequePojo();
                    pojo.setAcnoCredit(vtr.get(0).toString());
                    pojo.setCustName(vtr.get(1).toString());
                    pojo.setAcnoDebit(vtr.get(2).toString());
                    pojo.setBankName(vtr.get(3).toString());
                    pojo.setBranchName(vtr.get(4).toString());
                    pojo.setIfscCode(vtr.get(5).toString());
                    pojo.setChequeNo(vtr.get(6).toString());
                    pojo.setChequeDt(vtr.get(7).toString());
                    pojo.setAmount(Double.parseDouble(vtr.get(8).toString()));
                    if (vtr.get(9).toString().equalsIgnoreCase("F")) {
                        pojo.setChqStatus("Fresh");
                    } else {
                        pojo.setChqStatus("Used");
                    }

                    dataList.add(pojo);
                }
            }

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<OverDueListPojo> getOverDueListData(String branch, String acType, String toDt, String AccountNo) throws ApplicationException {

        List<OverDueListPojo> dataList = new ArrayList<OverDueListPojo>();
        List result = new ArrayList();
        List list2 = new ArrayList();
        double demand = 0d, intAmt = 0d, recovery = 0d, overDue = 0d;
        int month = 0;
        try {
            String acQuery = "";
            if (!AccountNo.equalsIgnoreCase("")) {
                acQuery = "and a.acno = '" + AccountNo + "'";
                acType = AccountNo.substring(2, 4);
            } else {
                acQuery = "";
            }
            if (branch.equalsIgnoreCase("0A")) {
                result = em.createNativeQuery("select a.acno,cast(sum(cramt-dramt) as decimal(25,2)) from accountmaster a,loan_recon r "
                        + "where  a.acno=r.acno and accttype = '" + acType + "' and dt<='" + toDt + "' " + acQuery + ""
                        + "group by r.acno having(cast(sum(cramt-dramt) as decimal(25,2)) < 0)").getResultList();
            } else {
                result = em.createNativeQuery("select a.acno,cast(sum(cramt-dramt) as decimal(25,2)) from accountmaster a,loan_recon r "
                        + "where  a.acno=r.acno and accttype = '" + acType + "' and dt<='" + toDt + "' and a.curbrcode = '" + branch + "' " + acQuery + " "
                        + "group by r.acno having(cast(sum(cramt-dramt) as decimal(25,2)) < 0)").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    OverDueListPojo pojo = new OverDueListPojo();
                    String acNo = vtr.get(0).toString();
                    double outBal = Math.abs(getBalanceOnDateForOverDue(vtr.get(0).toString(), toDt));
                    List list = em.createNativeQuery("select ifnull(max(date_format(DISBURSEMENTDT,'%Y%m%d')),'') from loandisbursement where acno = '" + acNo + "' and DISBURSEMENTDT <='" + toDt + "'").getResultList();
                    Vector dtVector = (Vector) list.get(0);
                    String disbursementDt = dtVector.get(0).toString();
                    String srlNo = "", dueDt = "", installAmt = "";
                    String tillDate = dmy.format(CbsUtil.calculateMonthEndDate(Integer.parseInt(toDt.substring(4, 6)) - 1, Integer.parseInt(toDt.substring(0, 4))));
                    if (toDt.substring(4, 6).equalsIgnoreCase("01")) {
                        String prevEndDt = CbsUtil.dateAdd(ymd.format(dmy.parse(tillDate)), 2);
                        tillDate = dmy.format(ymd.parse(prevEndDt));
                    }
                    if (!disbursementDt.equalsIgnoreCase("")) {
                        //List list3 = em.createNativeQuery("select max(SNO),max(duedt),max(INSTALLAMT) from(select SNO,min(date_format(DUEDT,'%Y%m%d')) duedt,INSTALLAMT from emidetails where acno = '" + acNo + "' group by INSTALLAMT )a").getResultList();
                        List list3 = em.createNativeQuery("select SNO,date_format(DUEDT,'%Y%m%d'),INSTALLAMT from emidetails where sno =(select ifnull(max(SNO),1) from "
                                + "(select SNO,min(date_format(DUEDT,'%Y%m%d')) duedt,INSTALLAMT from emidetails where acno = '" + acNo + "' and duedt <='" + toDt + "' group by INSTALLAMT )a "
                                + ") and acno = '" + acNo + "'").getResultList();
                        Vector ele = (Vector) list3.get(0);
                        srlNo = ele.get(0).toString();
                        dueDt = ele.get(1).toString();
                        installAmt = ele.get(2).toString();
                        if (ymd.parse(dueDt).before(ymd.parse(disbursementDt))) {
                            list3 = em.createNativeQuery("select SNO,date_format(min(duedt),'%Y%m%d'),INSTALLAMT from emidetails where acno = '" + acNo + "' and DUEDT >=(select max(DISBURSEMENTDT) from loandisbursement where acno = '" + acNo + "') and STATUS = 'Unpaid'").getResultList();
                            Vector ele1 = (Vector) list3.get(0);
                            if (ele1.get(0) == null) {
                                srlNo = srlNo;
                            } else {
                                srlNo = ele1.get(0).toString();
                            }
                            if (ele1.get(1) == null) {
                                List emiList = em.createNativeQuery("select dueDt,disbDt from "
                                        + "(select date_format(min(DUEDT),'%Y%m%d') as dueDt from emidetails  where acno = '" + acNo + "')a, "
                                        + "(select date_format(min(DISBURSEMENTDT),'%Y%m%d') as disbDt from loandisbursement where acno = '" + acNo + "')b").getResultList();
                                Vector emiVector = (Vector) emiList.get(0);
                                String emiMindueDt = emiVector.get(0).toString();
                                String disbDt = emiVector.get(1).toString();
                                if (ymd.parse(emiMindueDt).before(ymd.parse(disbDt))) {
                                    dueDt = disbursementDt;
                                } else {
                                    dueDt = dueDt;
                                }
                            } else {
                                dueDt = ele1.get(1).toString();
                            }
                            if (ele1.get(2) == null) {
                                installAmt = installAmt;
                            } else {
                                installAmt = ele1.get(2).toString();
                            }
                        }
                        //list3 = em.createNativeQuery("SELECT TIMESTAMPDIFF(MONTH, '" + dueDt + "', '" + toDt + "')").getResultList();
                        //Vector ele1 = (Vector) list3.get(0);
                        // month = Integer.parseInt(ele1.get(0).toString());
                        month = CbsUtil.monthDiff(ymd.parse(dueDt), ymd.parse(toDt));
                        if (ymd.parse(dueDt).after(ymd.parse(toDt))) {
                            month = 0;
                        }
                    }
                    if (!disbursementDt.equalsIgnoreCase("")) {
                        demand = month * Double.parseDouble(installAmt);
//                        list2 = em.createNativeQuery("select IntAmt,RecoverAmt from ("
//                                + "(select ifnull(sum(dramt),0) IntAmt from loan_recon where acno = '" + acNo + "' and trantype = 8 and dt between '" + dueDt + "' and '" + ymd.format(dmy.parse(tillDate)) + "')a, "
//                                + "(select ifnull(sum(cramt),0) RecoverAmt from loan_recon where acno = '" + acNo + "' and dt> '" + dueDt + "' and  dt<= '" + toDt + "' and details not like '%Remaining balance%' and details not like '%INTT. TRF FOR MEM%')b "
//                                + ")").getResultList();
                        //Change Npa Recovery
                        list2 = em.createNativeQuery("select IntAmt,RecoverAmt from ("
                                + "(select sum(IntAmt)IntAmt from"
                                + "(select ifnull(sum(dramt),0) IntAmt from loan_recon where acno = '" + acNo + "' and trantype = 8 and dt between '" + dueDt + "' and '" + ymd.format(dmy.parse(tillDate)) + "' "
                                + "union all "
                                + "select ifnull(sum(dramt),0) IntAmt from npa_recon where acno = '" + acNo + "' and trantype = 8 and dt between '" + dueDt + "' and '" + ymd.format(dmy.parse(tillDate)) + "'and details not like '%INTT. TRF FOR MEM%'"
                                + ")a)a, "
                                + "(select RecoverAmount-checkReturnAmt RecoverAmt from "
                                + "(select ifnull(sum(cramt),0) RecoverAmount from loan_recon where acno = '" + acNo + "' and dt> '" + dueDt + "' and  dt<= '" + toDt + "' and details not like '%Remaining balance%' and details not like '%INTT. TRF FOR MEM%')c, "
                                + "(select ifnull(sum(dramt),0) checkReturnAmt from loan_recon where acno = '" + acNo + "' and dt> '" + dueDt + "' and  dt<= '" + toDt + "' and details like '%Cheque Return. Inst No :%' )d)b "
                                + ")").getResultList();
                        Vector amtVector = (Vector) list2.get(0);
                        intAmt = Double.parseDouble(amtVector.get(0).toString());
                        recovery = Double.parseDouble(amtVector.get(1).toString());
                        overDue = (demand + intAmt) - recovery;
//                        list2 = em.createNativeQuery("select a.acno,demand,recovery,demand-recovery as overDue from( "
//                                + "(select acno,ifnull(cast(sum(DMD_AMT) as decimal(25,2)),0) demand from cbs_loan_dmd_table where dmd_date between '" + disbursementDt + "' and '" + toDt + "' and DMD_FLOW_ID <> 'PLOAN' and acno = '" + acNo + "' group by acno)a, "
//                                + "(select acno,sum(cramt) recovery from loan_recon where acno='" + acNo + "' and dt between '" + txnDt + "' and '" + toDt + "')b  "
//                                + ") where a.acno=b.acno group by a.acno having(demand-recovery) > 0").getResultList();
                    } else {
                        list2 = em.createNativeQuery("select demand,IntAmt,RecoverAmt from( "
                                + "(select ifnull(sum(INSTALLAMT),0) demand from emidetails where acno = '" + acNo + "' and duedt <='" + ymd.format(dmy.parse(tillDate)) + "')a, "
                                + "(select sum(IntAmt)IntAmt from("
                                + "select ifnull(sum(dramt),0) IntAmt from loan_recon where acno = '" + acNo + "' and trantype = 8 and dt <= '" + ymd.format(dmy.parse(tillDate)) + "' "
                                + "union all "
                                + "select ifnull(sum(dramt),0) IntAmt from npa_recon where acno = '" + acNo + "' and trantype = 8 and dt <= '" + ymd.format(dmy.parse(tillDate)) + "'and details not like '%INTT. TRF FOR MEM%' "
                                + ")a)b, "
                                + "(select ifnull(sum(cramt),0) RecoverAmt from loan_recon where acno = '" + acNo + "' and dt <='" + toDt + "' and details not like '%Remaining balance%' and details not like '%INTT. TRF FOR MEM%')c "
                                + ")").getResultList();
                        Vector amtVector = (Vector) list2.get(0);

                        demand = Double.parseDouble(amtVector.get(0).toString());
                        intAmt = Double.parseDouble(amtVector.get(1).toString());
                        recovery = Double.parseDouble(amtVector.get(2).toString());
                        overDue = (demand + intAmt) - recovery;
                    }
                    if (!list2.isEmpty()) {
                        if (overDue > 0) {
                            if (disbursementDt.equalsIgnoreCase("")) {
                                pojo.setTotalDemand(new BigDecimal(demand + intAmt));
                                pojo.setTotalRecover(new BigDecimal(recovery));
                                if (overDue > outBal) {
                                    pojo.setOveDue(new BigDecimal(outBal));
                                } else {
                                    pojo.setOveDue(new BigDecimal(overDue));
                                }
                            } else {
                                pojo.setTotalDemand(new BigDecimal(demand + intAmt));
                                pojo.setTotalRecover(new BigDecimal(recovery));
                                if (overDue > outBal) {
                                    pojo.setOveDue(new BigDecimal(outBal));
                                } else {
                                    pojo.setOveDue(new BigDecimal(overDue));
                                }
//                            pojo.setTotalDemand(new BigDecimal(amtVector.get(1).toString()));
//                            pojo.setTotalRecover(new BigDecimal(amtVector.get(2).toString()));
//                            pojo.setOveDue(new BigDecimal(amtVector.get(3).toString()));
                            }
                            pojo.setAcName(acNo);
                            pojo.setCustName(fts.getCustName(vtr.get(0).toString()));
                            pojo.setBalance(new BigDecimal(outBal));
                            dataList.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return dataList;
    }

    public Double getBalanceOnDateForOverDue(String acNo, String date) throws ApplicationException {
        double bal = 0d;
        try {
            List balanceList = em.createNativeQuery("select ifnull(round((sum(ifnull(cramt,0)) - sum(ifnull(dramt,0))),2),0) from loan_recon where auth= 'Y' "
                    + "and acno = '" + acNo + "' and dt <= '" + date + "'and details not like '%INTT. TRF FOR MEM%'").getResultList();
            if (!balanceList.isEmpty()) {
                Vector vtr = (Vector) balanceList.get(0);
                bal = Double.parseDouble(vtr.get(0).toString());
            }
        } catch (Exception ex) {
            throw new ApplicationException();
        }
        return bal;
    }

    @Override
    public List<TdPeriodMaturityPojo> getCustWiseRecieptDetail(String custId, String toDt) throws ApplicationException {
        List<TdPeriodMaturityPojo> dataList = new ArrayList<>();
        List intTdsList = new ArrayList();
        try {
            List list = em.createNativeQuery("select vm.acno,vm.voucherno,vm.receiptno,date_format(vm.fddt,'%d/%m/%Y'),vm.prinamt,vm.roi,date_format(matdt,'%d/%m/%Y'),vm.status,"
                    + "intopt,period,'FD'acctNature  \n"
                    + "from td_vouchmst vm,td_accountmaster ac,\n"
                    + "customerid ci where vm.acno=ac.acno and vm.acno=ci.acno \n"
                    + "and(vm.ClDt>='" + toDt + "' or ifnull(vm.ClDt,'')='' or vm.ClDt='') and FDDT <='" + toDt + "' \n"
                    + "and ci.custid = '" + custId + "' union All\n"
                    + "select a.acno,'0'voucherno,'0'Receipt,date_format(a.OpeningDt,'%d/%m/%Y'),a.RdInstal,a.intdeposit,date_format(a.Rdmatdate,'%d/%m/%Y'),accstatus,''intopt,''period,c.acctNature "
                    + "from accountmaster a,customerid b,accounttypemaster c \n"
                    + "where a.acno=b.acno and b.custid = '" + custId + "' and c.acctNature = '" + CbsConstant.RECURRING_AC + "' and a.accttype = c.acctcode \n"
                    + "and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "') and OpeningDt <='" + toDt + "' ").getResultList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    TdPeriodMaturityPojo pojo = new TdPeriodMaturityPojo();
                    String acNo = vtr.get(0).toString();
                    String fdDt = vtr.get(3).toString();
                    double prin = Double.parseDouble(vtr.get(4).toString());
                    float roi = Float.parseFloat(vtr.get(5).toString());
                    String matDt = vtr.get(6).toString();
                    String intOpt = vtr.get(8).toString();
                    String period = vtr.get(9).toString();
                    String acctnature = vtr.get(10).toString();
                    pojo.setAccountNumber(acNo);
                    pojo.setVoucherNo(Float.parseFloat(vtr.get(1).toString()));
                    pojo.setReceiptNo(vtr.get(2).toString());
                    pojo.setFdDt(fdDt);
                    pojo.setPrinAmt(prin);
                    pojo.setRoi(roi);
                    pojo.setMatDt(matDt);
                    double matAmt = 0, voucherWiseInterest = 0, voucherWiseTds = 0, rdPrin = 0;
                    if (!acctnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        matAmt = Double.parseDouble(tdReceiptmgtRemote.orgFDInterest(intOpt, roi, ymd.format(dmy.parse(fdDt)), ymd.format(dmy.parse(matDt)), prin, period, acNo.substring(0, 2)));
                        if (intOpt.equalsIgnoreCase("S") || intOpt.equalsIgnoreCase("Y") || intOpt.equalsIgnoreCase("H")
                                || intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("M")) {
                            pojo.setIntAtMat(prin);
                        } else {
                            pojo.setIntAtMat(prin + matAmt);
                        }
                    } else {
                        int monDiff = CbsUtil.monthDiff(dmy.parse(fdDt), dmy.parse(matDt));
                        String matAmts = acOpenFacadeRemote.cbsRdCalculation(Float.parseFloat(vtr.get(4).toString()), monDiff, roi);
                        pojo.setIntAtMat(Double.parseDouble(matAmts));
                        List rdList = em.createNativeQuery("select count(*) from rd_installment where acno ='" + acNo + "' and status = 'PAID' and duedt <='" + toDt + "'").getResultList();
                        Vector rdVector = (Vector) rdList.get(0);
                        int rdNo = Integer.parseInt(rdVector.get(0).toString());
                        if (rdNo == 0) {
                            rdPrin = prin;
                            pojo.setPrinAmt(rdPrin);
                        } else {
                            rdPrin = prin * rdNo;
                            pojo.setPrinAmt(rdPrin);
                        }
                    }
                    if (acctnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        intTdsList = em.createNativeQuery("select IntAmt,tdsAmt from (\n"
                                + "(select ifnull(sum(interest),0) IntAmt from rd_interesthistory where acno = '" + acNo + "' and dt<='" + toDt + "')a,\n"
                                + "(select ifnull(sum(tds),0) tdsAmt from tdshistory where acno = '" + acNo + "' and dt<='" + toDt + "')b\n"
                                + ")").getResultList();
                    } else {
                        intTdsList = em.createNativeQuery("select IntAmt,tdsAmt from (\n"
                                + "(select ifnull(sum(interest),0) IntAmt from td_interesthistory where acno = '" + acNo + "' and VoucherNo = " + Float.parseFloat(vtr.get(1).toString()) + " and dt<='" + toDt + "')a,\n"
                                + "(select ifnull(sum(tds),0) tdsAmt from tdshistory where acno = '" + acNo + "' and VoucherNo = " + Float.parseFloat(vtr.get(1).toString()) + " and dt<='" + toDt + "')b\n"
                                + ")").getResultList();
                    }
                    Vector ele = (Vector) intTdsList.get(0);
                    voucherWiseInterest = Double.parseDouble(ele.get(0).toString());
                    voucherWiseTds = Double.parseDouble(ele.get(1).toString());
                    if (acctnature.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                        pojo.setTotTdAmt(common.getBalanceOnDate(acNo, toDt));
                    } else {
                        if (intOpt.equalsIgnoreCase("S") || intOpt.equalsIgnoreCase("Y") || intOpt.equalsIgnoreCase("H")
                                || intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("M")) {
                            pojo.setTotTdAmt(prin);
                        } else {
                            pojo.setTotTdAmt(prin + (voucherWiseInterest - voucherWiseTds));
                        }
                    }
                    dataList.add(pojo);
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public String getCustNameByCustId(String custId) throws ApplicationException {
        String custName = "";
        try {
            List list = em.createNativeQuery("select custfullName from cbs_customer_master_detail where customerid = '" + custId + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                custName = vtr.get(0).toString();
            }
            return custName;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List<AadharDetailSummaryPojo> getAadharDetailSummaryData(String branch, String frDt, String toDt, String reportOpion) throws ApplicationException {
        List<AadharDetailSummaryPojo> dataList = new ArrayList<>();
        List result = new ArrayList();
        try {
            if (reportOpion.equalsIgnoreCase("S")) {
                List bnkList = em.createNativeQuery("select distinct bankname from bnkadd").getResultList();
                Vector bnkVector = (Vector) bnkList.get(0);
                result = em.createNativeQuery("select count(acno) from(\n"
                        + "select custid,a.acno from accountmaster a,customerid b\n"
                        + "where a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "')\n"
                        + "Union\n"
                        + "select custid,a.acno from td_accountmaster a,customerid b\n"
                        + "where a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "') order by 1\n"
                        + ")d").getResultList();
                Vector vtr = (Vector) result.get(0);
                String totalAcno = vtr.get(0).toString();
//                result = em.createNativeQuery("select count(acno) from(\n"
//                        + "select cast(c.customerid as unsigned),a.acno from accountmaster a,customerid b,(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') <'" + frDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
//                        + "union \n"
//                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail a where a.legal_document = 'E' \n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') <'" + frDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
//                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + frDt + "')\n"
//                        + "Union\n"
//                        + "select cast(c.customerid as unsigned),a.acno from td_accountmaster a,customerid b,(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') <'" + frDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
//                        + "union \n"
//                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail a where a.legal_document = 'E' \n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') <'" + frDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
//                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + frDt + "') order by 1\n"
//                        + ")d").getResultList();
//                Vector beforVector = (Vector) result.get(0);
//                String beforeAcno = beforVector.get(0).toString();
                result = em.createNativeQuery("select count(acno) from(\n"
                        + "select cast(c.customerid as unsigned),a.acno from accountmaster a,customerid b,(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
                        + "union \n"
                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail a where a.legal_document = 'E' \n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "')\n"
                        + "Union\n"
                        + "select cast(c.customerid as unsigned),a.acno from td_accountmaster a,customerid b,(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
                        + "union \n"
                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail a where a.legal_document = 'E' \n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "') order by 1\n"
                        + ")d").getResultList();

                Vector ele = (Vector) result.get(0);
                String aadharLinkAcno = ele.get(0).toString();
                long pendingAcno = Long.parseLong(totalAcno) - Long.parseLong(aadharLinkAcno);
                double percentAadharLink = CbsUtil.round((Double.parseDouble(aadharLinkAcno) / Double.parseDouble(totalAcno)) * 100, 2);
                AadharDetailSummaryPojo pojo = new AadharDetailSummaryPojo();
                pojo.setBnkName(bnkVector.get(0).toString());
                pojo.setTotalNoOfacNo(totalAcno);
                pojo.setNoOfAcNoAlready(aadharLinkAcno);
                pojo.setPending(String.valueOf(pendingAcno));
                pojo.setPercentOfAadhar(String.valueOf(percentAadharLink) + " %");
                dataList.add(pojo);
            } else {
                // For Checking multiple aadhar number.
//                List duplicateAadharList = em.createNativeQuery("select CustomerId,IdentityNo,count(CustomerId) from(\n"
//                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
//                        + "union \n"
//                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt from cbs_customer_master_detail a where a.legal_document = 'E' \n"
//                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
//                        + ")a group by customerId having(count(CustomerId) >1)").getResultList();
//                if (!duplicateAadharList.isEmpty()) {
//                }
                result = em.createNativeQuery("select cast(c.customerid as unsigned),a.acno,IdentityNo,custfullName from accountmaster a,customerid b,"
                        + "(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt,custfullName from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
                        + "union \n"
                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt,custfullName from cbs_customer_master_detail a where a.legal_document = 'E' \n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "') group by a.acno\n"
                        + "Union\n"
                        + "select cast(c.customerid as unsigned),a.acno,IdentityNo,custfullName from td_accountmaster a,customerid b,"
                        + "(select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt,custfullName from cbs_cust_identity_details a,cbs_customer_master_detail b where a.IdentificationType= 'E' and a.customerid= b.customerid\n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}')\n"
                        + "union \n"
                        + "select a.CustomerId,a.IdentityNo,date_format(LastChangeTime,'%Y%m%d') updateDt,custfullName from cbs_customer_master_detail a where a.legal_document = 'E' \n"
                        + "and date_format(LastChangeTime,'%Y%m%d') between '" + frDt + "' and '" + toDt + "' and length(a.IdentityNo) =12 and (a.IdentityNo REGEXP '[0-9]{12}'))c\n"
                        + "where b.custid = c.customerid and a.acno = b.acno and (ifnull(closingDate,'')='' OR closingDate > '" + toDt + "') group by a.acno order by 1").getResultList();
                if (!result.isEmpty()) {
                    for (int i = 0; i < result.size(); i++) {
                        Vector vtr = (Vector) result.get(i);
                        AadharDetailSummaryPojo pojo = new AadharDetailSummaryPojo();
                        String custId = vtr.get(0).toString();
                        String acNo = vtr.get(1).toString();
                        String aadharNo = vtr.get(2).toString();
                        String custName = vtr.get(3).toString();
                        pojo.setCustId(custId);
                        pojo.setAcNo(acNo);
                        pojo.setAadharNo(aadharNo);
                        pojo.setCustName(custName);
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;

    }
}
