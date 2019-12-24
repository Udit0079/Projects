/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.misc;

import com.cbs.constant.CbsAcCodeConstant;
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
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(mappedName = "CustomerDetailsReportFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CustomerDetailsReportFacade implements CustomerDetailsReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;

    public String accNoPass(String accno, String acType, String date) throws ApplicationException {
        String msg = "";
        try {
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + acType + "'").getResultList();
            Vector recLst = (Vector) acNatList.get(0);
            String acNat = recLst.get(0).toString();
            String tabname = "";
            String list1 = "";
            String list2 = "";
            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                    || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                tabname = "td_accountmaster";
            } else {
                tabname = "accountmaster";
            }
            List resList1 = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                    || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                resList1 = em.createNativeQuery("select acno from td_accountmaster where acno ='" + accno + "' ").getResultList();
            } else {
                resList1 = em.createNativeQuery("select acno from accountmaster where acno ='" + accno + "' ").getResultList();
            }
            List resList2 = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                    || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                resList2 = em.createNativeQuery("select acno from td_accountmaster where acno ='" + accno + "' and (closingdate>'" + date + "' or closingdate='' or ifnull(closingdate,'')='' ) ").getResultList();
            } else {
                resList2 = em.createNativeQuery("select acno from accountmaster where acno ='" + accno + "' and (closingdate>'" + date + "' or closingdate='' or ifnull(closingdate,'')='' ) ").getResultList();
            }

            if (resList1.isEmpty()) {
                msg = "A/c No Does Not Exist";
                return msg;
            }
            String rdroi = "";
            String nooffd = "null";
            if (resList2.isEmpty()) {
                msg = "A/c Has Been Closed";
                return msg;
            } else {
                list1 = loadGrid1Data(accno, acNat, date, tabname);
//                List reslist = new ArrayList();
//                reslist = em.createNativeQuery("SELECT lastbalconfirmdt,DATE_ADD(lastbalconfirmdt, INTERVAL 12 MONTH) FROM loan_mis_details WHERE ACNO='" + accno + "'").getResultList();
//                if (!reslist.isEmpty()) {
//                    for (int i = 0; i < reslist.size(); i++) {
//                        Vector recLst1 = (Vector) reslist.get(i);
//                        rdroi = recLst1.get(1).toString().substring(0, 10);
//                    }
//                } else {
//                    rdroi = "";
//                }
                if (((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                        || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                        || (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)))
                        && !(acType.equalsIgnoreCase("01"))) {
                    list2 = loadGrid2Data(accno, acNat, date);
                } else if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                        || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    List resList3 = em.createNativeQuery("SELECT count(*) FROM td_vouchmst WHERE ACNO='" + accno + "' AND STATUS='A' and fddt<='" + date + "'").getResultList();
                    Vector recLst2 = (Vector) resList3.get(0);
                    nooffd = recLst2.get(0).toString();
                }
            }
            msg = msg + "###" + rdroi + "###" + acNat + "###" + tabname + "###" + list1 + "###" + list2 + "###" + nooffd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return msg;
    }
    String OSBal;

    public String loadGrid1Data(String accno, String acNat, String date, String tabname) throws ApplicationException {

        String interestOption = "";
        String balance = "";
        String ROI = "";
        String RDmatdate = "";
        String RDinstall = "";
        int nooffdvalue = 0;
        String openingdate = "";
        String operationmode = "";
        String organisationtype = "";
        String custname = "";
        String fathername = "";
        String craddress = "";
        String phoneno = "";
        String panno = "";
        String documentDetails = "";
        String documentname = "";
        String rdMatvalue = "";
        String acInstrn = "";
        int OrgnCode = 0;
        int OPERMODE = 0;
        String dateOfBirth = "";
        String orgBrnCode = ftsRemote.getCurrentBrnCode(accno);
        //  String accountCode = ftsRemote.getAccountCode(accno);
        try {
            List reslist1 = new ArrayList();
            if (tabname.equalsIgnoreCase("accountmaster")) {

                reslist1 = em.createNativeQuery("select ifnull(openingdt,''),coalesce(opermode,0),ifnull(jtname1,''),coalesce(orgncode,0),"
                        + "ifnull(nomination,''),ifnull(instruction,''),coalesce(optstatus,0),coalesce(chequebook,0) from accountmaster where acno='" + accno + "' and (closingdate='' or"
                        + " ifnull(closingdate,'')='' or closingdate>'" + date + "')").getResultList();
                if (!reslist1.isEmpty()) {
                    Vector recLst = (Vector) reslist1.get(0);
                    openingdate = recLst.get(0).toString();
                    String OPER = recLst.get(1).toString();
                    String OrgnCod = recLst.get(3).toString();
                    acInstrn = recLst.get(5).toString();
                    OrgnCode = Integer.parseInt(OrgnCod);
                    OPERMODE = Integer.parseInt(OPER);
                }
            } else {
                List reslist11 = em.createNativeQuery("select ifnull(date_format(openingdt,'%Y%m%d'),''),coalesce(opermode,0),ifnull(jtname1,''),coalesce(orgncode,0),ifnull(nomination,''),ifnull(instruction,'') from "
                        + "td_accountmaster where acno='" + accno + "' and (closingdate='' or ifnull(closingdate,'')='' or closingdate>'"
                        + date + "')").getResultList();
                if (!reslist11.isEmpty()) {
                    Vector recLst = (Vector) reslist11.get(0);
                    openingdate = recLst.get(0).toString();
                    String OPER = recLst.get(1).toString();
                    String OrgnCod = recLst.get(3).toString();
                    acInstrn = "";
                    OrgnCode = Integer.parseInt(OrgnCod);
                    OPERMODE = Integer.parseInt(OPER);
                }
            }

            List reslist2 = new ArrayList();
            reslist2 = em.createNativeQuery("select ifnull(description,'') from codebook where groupcode=4 and code= " + OPERMODE + " ").getResultList();
            if (!reslist2.isEmpty()) {
                Vector recLst1 = (Vector) reslist2.get(0);
                operationmode = recLst1.get(0).toString();
            }
            List reslist3 = new ArrayList();
            reslist3 = em.createNativeQuery("select ifnull(description,'') from codebook where groupcode=6 and code= " + OrgnCode + " ").getResultList();
            if (!reslist3.isEmpty()) {
                Vector recLst2 = (Vector) reslist3.get(0);
                organisationtype = recLst2.get(0).toString();
            }
            List reslist4 = new ArrayList();
            reslist4 = em.createNativeQuery("select ifnull(custname,''),ifnull(fathername,''), concat(ifnull(MailAddressLine1,''),ifnull(MailAddressLine2,'')),"
                    + "ifnull(mobilenumber,''),ifnull(pan_girnumber,''),date_format(DateOfBirth,'%d/%m/%Y')"
                    + ",ifnull(mailPhoneNumber,''),ifnull(middle_name,''),ifnull(last_name,'') from cbs_customer_master_detail cc, customerid c where c.custid=cc.customerid and c.acno='" + accno + "'").getResultList();
            if (!reslist4.isEmpty()) {
                Vector recLst3 = (Vector) reslist4.get(0);
                custname = recLst3.get(0).toString() + " " + recLst3.get(7).toString() + " " + recLst3.get(8).toString();
                fathername = recLst3.get(1).toString();
                craddress = recLst3.get(2).toString();
                if (recLst3.get(6).toString().equalsIgnoreCase("")) {
                    phoneno = recLst3.get(3).toString();
                } else {
                    if (recLst3.get(3).toString().equalsIgnoreCase("")) {
                        phoneno = recLst3.get(6).toString();
                    } else {
                        phoneno = recLst3.get(3).toString() + " / " + recLst3.get(6).toString();
                    }
                }
                panno = recLst3.get(4).toString();
                dateOfBirth = recLst3.get(5).toString();
            }

            List reslist5 = new ArrayList();
            reslist5 = em.createNativeQuery("select ifnull(docuno,''),ifnull(docudetails,'') from documentsreceived where acno='" + accno + "'").getResultList();
            if (!reslist5.isEmpty()) {
                Vector res = (Vector) reslist5.get(0);
                String doc = res.get(0).toString();
                documentDetails = res.get(1).toString();
                int docuno = Integer.parseInt(doc);
                List reslist6 = new ArrayList();
                reslist6 = em.createNativeQuery("select ifnull(description,'') from codebook where groupcode=10 and code=" + docuno + " ").getResultList();
                if (!reslist6.isEmpty()) {
                    Vector res1 = (Vector) reslist6.get(0);
                    documentname = res1.get(0).toString();
                }
            }

            List reslist7 = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                    || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                reslist7 = em.createNativeQuery("select ifnull(int_opt,'') from loan_appparameter where acno='" + accno + "' ").getResultList();
            }
            if (!reslist7.isEmpty()) {
                Vector res2 = (Vector) reslist7.get(0);
                interestOption = res2.get(0).toString();
            }
            List reslist8 = new ArrayList();
            reslist8 = em.createNativeQuery("select date from bankdays where dayendflag='N'and brncode='" + orgBrnCode + "' ").getResultList();
            if (!reslist8.isEmpty()) {
                Vector res2 = (Vector) reslist8.get(0);
                String Tempbd = res2.get(0).toString();
                List reslist13 = new ArrayList();
                if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                        || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from loan_recon where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from recon  where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from rdrecon  where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from ca_recon  where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                } else if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                        || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from td_recon  where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' AND CLOSEFLAG IS NULL ").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from ddstransaction  where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                } else if (acNat.equalsIgnoreCase(CbsConstant.OF_AC)) {
                    reslist13 = em.createNativeQuery("select abs(ifnull(sum(cramt),0)- ifnull(sum(dramt),0)) from of_recon   where acno='" + accno + "' and dt<='" + Tempbd + "' and auth='Y' ").getResultList();
                }
                if (!reslist13.isEmpty()) {
                    Vector res3 = (Vector) reslist13.get(0);
                    balance = res3.get(0).toString();
                    OSBal = balance;
                } else {
                    OSBal = "0";
                    balance = "0";
                }
            }
            List reslist9 = new ArrayList();

            if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                reslist9 = em.createNativeQuery("select ifnull(intdeposit,''),ifnull(date_format(rdmatdate,'%Y%m%d'),''),coalesce(rdinstal,0) from accountmaster where acno='" + accno + "' ").getResultList();
                if (!reslist9.isEmpty()) {
                    Vector recLst4 = (Vector) reslist9.get(0);
                    ROI = recLst4.get(0).toString();
                    RDmatdate = recLst4.get(1).toString();
                    RDinstall = recLst4.get(2).toString();

                    int RdPeriod = CbsUtil.monthDiff(new SimpleDateFormat("yyyyMMdd").parse(openingdate), new SimpleDateFormat("yyyyMMdd").parse(RDmatdate));
                    float ip = 4;
                    float j = Float.parseFloat(ROI) / ip;
                    float c = ip / 12;
                    double F = (Math.pow((1 + j / 100), c)) - 1;
                    double a1 = ((Math.pow((1 + F), RdPeriod)) - 1) / F;
                    double a2 = a1 * (1 + F);
                    double RDMatvalue = Math.round(a2 * Double.parseDouble(RDinstall));
                    rdMatvalue = String.valueOf(RDMatvalue);
                    nooffdvalue = RdPeriod;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        String list1 = openingdate + "###" + operationmode + "###" + organisationtype + "###" + custname + "###" + fathername + "###" + craddress + "###"
                + acInstrn + "###" + phoneno + "###" + panno + "###" + dateOfBirth + "###" + documentDetails + "###" + documentname + "###" + interestOption + "###"
                + balance + "###" + ROI + "###" + RDmatdate + "###" + RDinstall + "###" + rdMatvalue + "###"
                + nooffdvalue;
        return list1;
    }

    /**
     * Code added by Nishant Kansal*
     */
    public String getDateOfBirth(String accno) throws ApplicationException {
        List list = new ArrayList();
        String result = "";
        String tabnam;
        try {
            String orgBrnCode = ftsRemote.getCurrentBrnCode(accno);
            String accountCode = ftsRemote.getAccountCode(accno);
            List acNatList = em.createNativeQuery("select acctnature from accounttypemaster where acctcode='" + accountCode + "'").getResultList();
            Vector recLst = (Vector) acNatList.get(0);
            String acNat = recLst.get(0).toString();
            if ((acNat.equalsIgnoreCase(CbsConstant.FIXED_AC))
                    || (acNat.equalsIgnoreCase(CbsConstant.MS_AC))) {
                tabnam = "td_accountmaster";
            } else {
                tabnam = "accountmaster";
            }
            if (tabnam.equalsIgnoreCase("accountmaster")) {
                list = em.createNativeQuery("select ifnull(dob,'') from customermaster where custno=substring('" + accno + "',5,6) "
                        + "and actype='" + accountCode + "' and brncode='" + orgBrnCode + "'").getResultList();
            } else {
                list = em.createNativeQuery("select ifnull(dob,'') from td_customermaster where custno=substring('" + accno + "',5,6) "
                        + "and actype='" + accountCode + "' and brncode='" + orgBrnCode + "'").getResultList();
            }
            if (list.size() > 0) {
                Vector vector = (Vector) list.get(0);
                result = (String) vector.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return result;
    }

    public String recoveryData(String accno, String date, String acNat) throws ApplicationException {
        String cramt = "";
        double recovery = 0;
        try {
            List reslist = new ArrayList();
            List reslist1 = new ArrayList();
            List reslist2 = new ArrayList();

            if ((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                    || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))) {
                //tab_name = "LOAN_RECON";
                reslist = em.createNativeQuery("SELECT ifnull(CRAMT,0) FROM loan_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' ").getResultList();
                if (!reslist.isEmpty()) {
                    for (int i = 0; i < reslist.size(); i++) {
                        Vector recLst = (Vector) reslist.get(i);
                        cramt = recLst.get(0).toString();
                        reslist1 = em.createNativeQuery("SELECT ifnull(DRAMT,0) FROM loan_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' AND CRAMT=" + cramt + "").getResultList();
                        if (!reslist1.isEmpty()) {
                            Vector recLst1 = (Vector) reslist1.get(0);
                            String value = recLst1.get(0).toString();
                            recovery = recovery + Double.parseDouble(value);
                        }
                    }
                }

                reslist2 = em.createNativeQuery("SELECT ifnull(sum(CRAMT),0) FROM loan_recon WHERE ACNO='" + accno + "'  AND DT <='" + date + "' ").getResultList();
                if (!reslist2.isEmpty()) {
                    Vector recLst2 = (Vector) reslist2.get(0);
                    String value1 = recLst2.get(0).toString();
                    if (!value1.isEmpty()) {
                        recovery = Double.parseDouble(value1) - recovery;
                    } else {
                        recovery = 0;
                    }
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                //tab_name = "CA_RECON";
                reslist = em.createNativeQuery("SELECT ifnull(CRAMT,0) FROM ca_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' ").getResultList();
                if (!reslist.isEmpty()) {

                    for (int i = 0; i < reslist.size(); i++) {
                        Vector recLst = (Vector) reslist.get(i);
                        cramt = recLst.get(0).toString();
                        reslist1 = em.createNativeQuery("SELECT ifnull(DRAMT,0) FROM ca_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' AND CRAMT='" + cramt + "' ").getResultList();
                        if (!reslist1.isEmpty()) {
                            Vector recLst1 = (Vector) reslist1.get(0);
                            String value = recLst1.get(0).toString();
                            recovery = recovery + Double.parseDouble(value);
                        }
                    }
                }
                reslist2 = em.createNativeQuery("SELECT ifnull(sum(CRAMT),0) FROM ca_recon WHERE ACNO='" + accno + "'  AND DT <='" + date + "' ").getResultList();
                if (!reslist2.isEmpty()) {
                    Vector recLst2 = (Vector) reslist2.get(0);
                    String value1 = recLst2.get(0).toString();
                    if (!value1.isEmpty()) {
                        recovery = Double.parseDouble(value1) - recovery;
                    } else {
                        recovery = 0;
                    }
                }
            } else {
                //tab_name = "gl_RECON";
                reslist = em.createNativeQuery("SELECT ifnull(CRAMT,0) FROM gl_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' ").getResultList();
                if (!reslist.isEmpty()) {
                    for (int i = 0; i < reslist.size(); i++) {
                        Vector recLst = (Vector) reslist.get(i);
                        cramt = recLst.get(0).toString();
                        reslist1 = em.createNativeQuery("SELECT ifnull(DRAMT,0) FROM gl_recon WHERE ACNO='" + accno + "' AND TRANTYPE=1 AND DT <='" + date + "' AND CRAMT='" + cramt + "' ").getResultList();
                        if (!reslist1.isEmpty()) {
                            Vector recLst1 = (Vector) reslist1.get(0);
                            String value = recLst1.get(0).toString();
                            recovery = recovery + Double.parseDouble(value);
                        }
                    }
                }

                reslist2 = em.createNativeQuery("SELECT ifnull(sum(CRAMT),0) FROM gl_recon WHERE ACNO='" + accno + "'  AND DT <='" + date + "' ").getResultList();
                if (!reslist2.isEmpty()) {
                    Vector recLst2 = (Vector) reslist2.get(0);
                    String value1 = recLst2.get(0).toString();
                    if (!value1.isEmpty()) {
                        recovery = Double.parseDouble(value1) - recovery;
                    } else {
                        recovery = 0;
                    }
                }
            }
            return "" + recovery;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }
    // String list2;

    public String loadGrid2Data(String accno, String acNat, String date) throws ApplicationException {

        String lbl9 = "";
        String lbl1 = "";
        String lbl10 = "";
        String lbl2 = "";
        String lbl11 = "";
        String lbl3 = "";
        String lbl4 = "";
        String lbl5 = "";
        String NPA_DrAmt = "";
        String lbl7 = "";
        String lbl8 = "";
        String lbl12 = "";
        String lbl13 = "";
        String lbl14 = "";
        String lbl15 = "";
        String lbl16 = "";
        String lbl17 = "";
        String lbl18 = "";
        String lbl19 = "";
        String lbl20 = "";
        String lbl21 = "";
        String lbl22 = "";
        String lbl23 = "";
        String lbl24 = "";
        String lbl25 = "";
        String lbl26 = "";
        String lbl27 = "";
        String lbl28 = "";
        String lbl29 = "";
        String lbl30 = "";
        String lbl31 = "";
        String lbl32 = "";
        String lbl33 = "";
        String intopt = "";
        double recovery = 0;
        try {
            String Str = recoveryData(accno, date, acNat);
            if (!Str.isEmpty()) {
                recovery = Double.parseDouble(Str);
            } else {
                recovery = 0;
            }

            List reslist = new ArrayList();
            if ((acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC))) {
                String accountCode = ftsRemote.getAccountCode(accno);
                if ((accountCode.equalsIgnoreCase("07"))) {
                    reslist = em.createNativeQuery("select ifnull(sanctionlimit,0),ifnull(date_format(sanctionlimitdt,'%d/%m/%Y'),'') AS sanctionlimitdt,"
                            + "coalesce(odlimit,0),ifnull(roi,0),ifnull(penalroi,0),coalesce(adhoclimit,0),coalesce(adhocroi,0),"
                            + "ifnull(date_format(adhocapplicabledt,'%Y%m%d'),''),ifnull(date_format(adhocexpiry,'%Y%m%d'),''),"
                            + "coalesce(subsidyamt,0),ifnull(date_format(subsidyexpdt,'%Y%m%d'),''),ifnull(int_opt,'') from loan_appparameter "
                            + "where acno= '" + accno + "'").getResultList();
                } else if ((accountCode.equalsIgnoreCase("02"))) {
                    reslist = em.createNativeQuery("select ifnull(sanctionlimit,0),ifnull(date_format(sanctionlimitdt,'%d/%m/%Y'),'') AS sanctionlimitdt,coalesce(dplimit,0),"
                            + "ifnull(roi,0),ifnull(penalroi,0),coalesce(adhoclimit,0),coalesce(adhocroi,0),ifnull(date_format(adhocapplicabledt,'%Y%m%d'),''),"
                            + "ifnull(date_format(adhocexpiry,'%Y%m%d'),''),coalesce(subsidyamt,0),ifnull(date_format(subsidyexpdt,'%Y%m%d'),''),"
                            + "ifnull(int_opt,'') from loan_appparameter where acno= '" + accno + "'").getResultList();
                } else {
                    reslist = em.createNativeQuery("select ifnull(sanctionlimit,0),ifnull(date_format(sanctionlimitdt,'%d/%m/%Y'),'') AS sanctionlimitdt,coalesce(dplimit,0),"
                            + "ifnull(roi,0),ifnull(penalroi,0),coalesce(adhoclimit,0),coalesce(adhocroi,0),ifnull(date_format(adhocapplicabledt,'%Y%m%d'),''),"
                            + "ifnull(date_format(adhocexpiry,'%Y%m%d'),''),coalesce(subsidyamt,0),ifnull(date_format(subsidyexpdt,'%Y%m%d'),''),"
                            + "ifnull(int_opt,'') from loan_appparameter where acno= '" + accno + "'").getResultList();
                }
                if (!reslist.isEmpty()) {
                    for (int i = 0; i < reslist.size(); i++) {
                        Vector recLst = (Vector) reslist.get(i);
                        lbl1 = recLst.get(0).toString();
                        lbl2 = recLst.get(1).toString();
                        lbl3 = recLst.get(3).toString();
                        lbl26 = recLst.get(2).toString();
                        lbl27 = recLst.get(4).toString();
                        lbl28 = recLst.get(5).toString();
                        lbl29 = recLst.get(6).toString();
                        lbl30 = recLst.get(7).toString();
                        lbl31 = recLst.get(8).toString();
                        lbl32 = recLst.get(9).toString();
                        lbl33 = recLst.get(10).toString();
                        intopt = recLst.get(11).toString();
                    }
                }
            } else if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                lbl23 = fnGetLoanStatus(accno, date);
                List reslist2 = em.createNativeQuery("select ifnull(sanctionlimit,0),ifnull(date_format(sanctionlimitdt,'%d/%m/%Y'),'') "
                        + "AS sanctionlimitdt,ifnull(roi,0) from loan_appparameter where acno='" + accno + "' ").getResultList();
                if (!reslist2.isEmpty()) {
                    Vector recLst1 = (Vector) reslist2.get(0);
                    lbl1 = recLst1.get(0).toString();
                    lbl2 = recLst1.get(1).toString();
                    lbl3 = recLst1.get(2).toString();
                }
                List reslist3 = new ArrayList();
                reslist3 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno='" + accno + "' and dt<='" + date + "' "
                        + "and trantype<>8 and (trandesc not between 21 and 50 or trandesc is null ) and trandesc not in('3','4')").getResultList();
                if (!reslist3.isEmpty()) {
                    Vector recLst2 = (Vector) reslist3.get(0);
                    lbl4 = recLst2.get(0).toString();
                }
                lbl5 = FnOverDueForaccount(accno, date).toString();
                List reslist5 = new ArrayList();
                String status = lbl23;
                if (status.equals("STANDARD")) {
                    reslist5 = em.createNativeQuery("select ifnull(date_format(effdt,'%d/%m/%Y'),'') AS effdt from accountstatus where "
                            + "acno='" + accno + "'").getResultList();
                    if (!reslist5.isEmpty()) {
                        Vector recLst4 = (Vector) reslist5.get(0);
                        lbl24 = recLst4.get(0).toString();
                    }

                    List reslist6 = new ArrayList();
                    reslist6 = em.createNativeQuery("select ifnull(sum(dramt),0) from npa_recon where acno='" + accno + "' and trantype=8 and "
                            + "dt<='" + date + "'").getResultList();
                    if (!reslist6.isEmpty()) {
                        Vector recLst5 = (Vector) reslist6.get(0);
                        NPA_DrAmt = recLst5.get(0).toString();
                        lbl25 = NPA_DrAmt;
                    }
                }
                List reslist7 = new ArrayList();
                reslist7 = em.createNativeQuery("SELECT ifnull(sum(dramt),0) FROM loan_recon where acno='" + accno + "' AND DT<='" + date
                        + "' and trantype=8 ").getResultList();
                if (!reslist7.isEmpty()) {
                    Vector recLst6 = (Vector) reslist7.get(0);
                    String INT_AMOUNT = recLst6.get(0).toString();
                    double INT_AMTDUE = recovery - Double.parseDouble(INT_AMOUNT);
                    if (INT_AMTDUE < 0) {
                        lbl7 = String.valueOf(INT_AMTDUE + Double.parseDouble(NPA_DrAmt));
                    } else {
                        lbl7 = "0";
                    }

//                    double PRN_AMOUNT = Double.parseDouble(OSBal) - Double.parseDouble(INT_AMOUNT);
//                    lbl22 = String.valueOf(PRN_AMOUNT);
                }
                //New Code  Software Bug #12214 for Resolved
                reslist7 = em.createNativeQuery("select "
                        + "(SELECT ifnull(sum(cramt),0) FROM loan_recon where acno='" + accno + "' AND DT<='" + date + "') as Cr,"
                        + "(SELECT ifnull(sum(dramt),0) FROM loan_recon where acno='" + accno + "' AND DT<='" + date + "' and trandesc not in('3','4','8')) as Disb,"
                        + "(SELECT ifnull(sum(dramt),0) FROM loan_recon where acno='" + accno + "' AND DT<='" + date + "' and trandesc in('3','4','8')) as IntChg ").getResultList();
                if (!reslist7.isEmpty()) {
                    Vector vtr = (Vector) reslist7.get(0);
                    double crAmt = Double.parseDouble(vtr.get(0).toString());
                    double Disb = Double.parseDouble(vtr.get(1).toString());
                    double intChg = Double.parseDouble(vtr.get(2).toString());
                    double PRN_AMOUNT = 0;
                    if (intChg > crAmt) {
                        PRN_AMOUNT = Disb;
                    } else {
                        PRN_AMOUNT = Disb - (crAmt - intChg);
                    }
                    lbl22 = String.valueOf(PRN_AMOUNT);
                }
                lbl8 = CategoryOptForAcct(accno);
                List reslist10 = new ArrayList();
//                reslist10 = em.createNativeQuery("select ifnull(sector,'') from vw_applicantgeneral where acno='" + accno + "'").getResultList();
//                if (!reslist10.isEmpty()) {
//                    Vector recLst9 = (Vector) reslist10.get(0);
//                    lbl10 = recLst9.get(0).toString();
//                }
                reslist10 = em.createNativeQuery("select ifnull(sub_sector,'') from cbs_loan_borrower_details where acc_no='" + accno + "'").getResultList();
                if (!reslist10.isEmpty()) {
                    Vector recLst9 = (Vector) reslist10.get(0);
                    lbl10 = recLst9.get(0).toString();
                }

//                List reslist11 = new ArrayList();
//                reslist11 = em.createNativeQuery("select ifnull(YOJYNA,'') from vw_applicantgeneral where acno='" + accno + "'").getResultList();
//                if (!reslist11.isEmpty()) {
//                    Vector recLst10 = (Vector) reslist11.get(0);
//                    lbl11 = recLst10.get(0).toString();
//                }
                List reslist11 = new ArrayList();
                reslist11 = em.createNativeQuery("select ifnull(type_of_advance,'') from cbs_loan_borrower_details where acc_no='" + accno + "'").getResultList();
                if (!reslist11.isEmpty()) {
                    Vector recLst10 = (Vector) reslist11.get(0);
                    lbl11 = recLst10.get(0).toString();
                }

                List reslist12 = new ArrayList();
                reslist12 = em.createNativeQuery("select ifnull(particulars,'') from loansecurity where acno='" + accno + "' limit 1").getResultList();
                if (!reslist12.isEmpty()) {
                    Vector recLst11 = (Vector) reslist12.get(0);
                    lbl13 = recLst11.get(0).toString();
                }

                List reslist13 = new ArrayList();
                reslist13 = em.createNativeQuery("select ifnull(sum(lienvalue),0) from loansecurity where acno='" + accno + "'").getResultList();
                if (!reslist13.isEmpty()) {
                    Vector recLst12 = (Vector) reslist13.get(0);
                    lbl14 = recLst12.get(0).toString();
                }

                List reslist14 = new ArrayList();
                reslist14 = em.createNativeQuery("select ifnull(installamt,'') from emidetails where acno='" + accno + "' limit 1").getResultList();
                if (!reslist14.isEmpty()) {
                    Vector recLst13 = (Vector) reslist14.get(0);
                    lbl15 = recLst13.get(0).toString();
                }

                List reslist15 = new ArrayList();
                reslist15 = em.createNativeQuery("select ifnull(max(sno),'') from emidetails where  acno='" + accno + "'  and status='PAID' AND duedt<='" + date + "' ").getResultList();
                if (!reslist15.isEmpty()) {
                    Vector recLst14 = (Vector) reslist15.get(0);
                    lbl16 = recLst14.get(0).toString();
                }

                List reslist16 = new ArrayList();
                reslist16 = em.createNativeQuery("SELECT (select ifnull(max(sno),0) from emidetails where duedt<='" + date + "' AND  acno='" + accno + "' )-( select ifnull(max(sno),0) from emidetails where duedt<='" + date + "' AND  acno='" + accno + "'  and status='PAID')").getResultList();
                if (!reslist16.isEmpty()) {
                    Vector recLst15 = (Vector) reslist16.get(0);
                    lbl17 = recLst15.get(0).toString();
                }

                List reslist17 = new ArrayList();
                reslist17 = em.createNativeQuery("SELECT ifnull(max(sno),'') from emidetails where acno='" + accno + "'").getResultList();
                if (!reslist17.isEmpty()) {
                    Vector recLst16 = (Vector) reslist17.get(0);
                    lbl18 = recLst16.get(0).toString();
                }

                List reslist18 = new ArrayList();
                reslist18 = em.createNativeQuery("select ifnull(loan_pd_month,''),ifnull(loan_pd_day,'') from cbs_loan_acc_mast_sec where acno = '" + accno + "'").getResultList();
                if (!reslist18.isEmpty()) {
                    Vector recLst17 = (Vector) reslist18.get(0);
                    String month = recLst17.get(0).toString();
                    String days = recLst17.get(1).toString();
                    lbl12 = month + " Months " + days + " Days ";
                }
                /**
                 * ** Not Used ***
                 */
                List reslist19 = new ArrayList();
                reslist19 = em.createNativeQuery("select ifnull(villagecode,''),ifnull(actcode,'') from bsr_details where acno='" + accno + "'").getResultList();
                if (!reslist19.isEmpty()) {
                    Vector recLst18 = (Vector) reslist19.get(0);
                    lbl19 = recLst18.get(0).toString();
                    lbl20 = recLst18.get(1).toString();
                    lbl21 = "0";
                }
                /**
                 * ** Not Used ***
                 */
            } else if (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                List reslist20 = new ArrayList();
                reslist20 = em.createNativeQuery("select ifnull(sanctionlimit,0),ifnull(date_format(sanctionlimitdt,'%d/%m/%Y'),'') AS sanctionlimitdt,ifnull(roi,0) from loan_appparameter where acno='" + accno + "'").getResultList();
                if (!reslist20.isEmpty()) {
                    Vector recLst1 = (Vector) reslist20.get(0);
                    lbl1 = recLst1.get(0).toString();
                    lbl2 = recLst1.get(1).toString();
                    lbl3 = recLst1.get(2).toString();
                }
                List reslist21 = new ArrayList();
                reslist21 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno='" + accno + "' and dt<='" + date + "' and trantype<>8  ").getResultList();
                if (!reslist21.isEmpty()) {
                    Vector recLst2 = (Vector) reslist21.get(0);
                    lbl4 = recLst2.get(0).toString();
                }
                lbl5 = FnOverDueForaccount(accno, date).toString();

                List reslist23 = new ArrayList();
                reslist23 = em.createNativeQuery("select ifnull(sum(dramt),0) from loan_recon where acno='" + accno + "' and dt<='" + date + "' and trantype=8").getResultList();
                if (!reslist23.isEmpty()) {
                    Vector recLst4 = (Vector) reslist23.get(0);
                    lbl7 = recLst4.get(0).toString();
                }
                lbl8 = CategoryOptForAcct(accno);

                List reslist25 = new ArrayList();
                reslist25 = em.createNativeQuery("select ifnull(sector,'') from cbs_loan_borrower_details where acc_no='" + accno + "'").getResultList();
                if (!reslist25.isEmpty()) {
                    Vector recLst6 = (Vector) reslist25.get(0);
                    lbl9 = recLst6.get(0).toString();
                }

                List reslist26 = new ArrayList();
                reslist26 = em.createNativeQuery("select ifnull(sub_sector,'') from cbs_loan_borrower_details where acc_no='" + accno + "'").getResultList();
                if (!reslist26.isEmpty()) {
                    Vector recLst7 = (Vector) reslist26.get(0);
                    lbl10 = recLst7.get(0).toString();
                }

//                List reslist27 = new ArrayList();
//                reslist27 = em.createNativeQuery("select ifnull(YOJYNA,'') from vw_applicantgeneral where acno='" + accno + "'").getResultList();
//                if (!reslist27.isEmpty()) {
//                    Vector recLst8 = (Vector) reslist27.get(0);
//                    lbl11 = recLst8.get(0).toString();
//                }
                List reslist27 = new ArrayList();
                reslist27 = em.createNativeQuery("select ifnull(type_of_advance,'') from cbs_loan_borrower_details where acc_no='" + accno + "'").getResultList();
                if (!reslist27.isEmpty()) {
                    Vector recLst8 = (Vector) reslist27.get(0);
                    lbl11 = recLst8.get(0).toString();
                }

                List reslist28 = new ArrayList();
                reslist28 = em.createNativeQuery("select ifnull(particulars,'') from loansecurity where acno='" + accno + "' limit 1").getResultList();
                if (!reslist28.isEmpty()) {
                    Vector recLst9 = (Vector) reslist28.get(0);
                    lbl13 = recLst9.get(0).toString();
                }

                List reslist29 = new ArrayList();
                reslist29 = em.createNativeQuery("select ifnull(sum(lienvalue),0) from loansecurity where acno='" + accno + "'  ").getResultList();
                if (!reslist29.isEmpty()) {
                    Vector recLst10 = (Vector) reslist29.get(0);
                    lbl14 = recLst10.get(0).toString();
                }

                List reslist30 = new ArrayList();
                reslist30 = em.createNativeQuery("select ifnull(loan_pd_month,0),ifnull(loan_pd_day,0) from cbs_loan_acc_mast_sec where acno = '" + accno + "' ").getResultList();
                if (!reslist30.isEmpty()) {
                    Vector recLst11 = (Vector) reslist30.get(0);
                    String month = recLst11.get(0).toString();
                    String days = recLst11.get(1).toString();
                    lbl12 = month + " Months " + days + " Days ";
                }
                /**
                 * ** Not Used ***
                 */
                List reslist31 = new ArrayList();
                reslist31 = em.createNativeQuery("select ifnull(villagecode,''),ifnull(actcode,'') from bsr_details where acno='" + accno + "' ").getResultList();
                if (!reslist31.isEmpty()) {
                    Vector recLst12 = (Vector) reslist31.get(0);
                    lbl19 = recLst12.get(0).toString();
                    lbl20 = recLst12.get(1).toString();
                    lbl21 = "0";
                }
                /**
                 * ** Not Used ***
                 */
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        String list2 = lbl1 + "###" + lbl2 + "###" + lbl3 + "###" + lbl4 + "###" + lbl5 + "###" + recovery + "###" + lbl7 + "###" + lbl8 + "###" + lbl9 + "###" + lbl10 + "###" + lbl11 + "###" + lbl12 + "###" + lbl13 + "###" + lbl14 + "###" + lbl15 + "###" + lbl16 + "###" + lbl17 + "###" + lbl18
                + "###" + lbl19 + "###" + lbl20 + "###" + lbl21 + "###" + lbl22 + "###" + lbl23 + "###" + lbl24 + "###" + lbl25 + "###" + lbl26 + "###" + lbl27 + "###" + lbl28 + "###" + lbl29 + "###" + lbl30 + "###" + lbl31 + "###" + lbl32 + "###" + lbl33 + "###" + intopt;

        return list2;
    }

    public List securityDetails(String acno) throws ApplicationException {
        List varlist = new ArrayList();
        try {

            Query selectQuery = em.createNativeQuery("select particulars,matvalue,date_format(coalesce(matdate,0),'%d/%m/%Y') "
                    + "as matdate,lienvalue,status,date_format(coalesce(issuedate,0),'%d/%m/%Y') as issuedate from loansecurity"
                    + " where acno='" + acno + "'");
            varlist = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

        return varlist;
    }

    public String acctNature(String acno) throws ApplicationException {
        List varlist = new ArrayList();
        String nature = null;
        try {
            varlist = em.createNativeQuery("select acctNature from accounttypemaster  where acctCode = '" + acno + "'").getResultList();
            if (varlist.size() > 0) {
                Vector varlistVect = (Vector) varlist.get(0);
                nature = varlistVect.get(0).toString();
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return nature;
    }

    public List otherAcnoDetails(String acno) throws ApplicationException {
        String custId = null;
        String accountNo;
        String name = null;
        String acNature;
        List custnamelist1 = null;
        List custnamelist2 = null;
        List resultList = new ArrayList();
        try {
            List varlist = em.createNativeQuery("select custid from customerid where acno='" + acno + "'").getResultList();
            if (!varlist.isEmpty()) {
                Vector custIdVec = (Vector) varlist.get(0);
                custId = custIdVec.get(0).toString();
            }
            List custIdlist = em.createNativeQuery("select acno from customerid where custid= " + custId + "").getResultList();

            if (!custIdlist.isEmpty()) {
                for (int i = 0; i < custIdlist.size(); i++) {
                    Vector custIdVec = (Vector) custIdlist.get(i);
                    accountNo = custIdVec.get(0).toString();
                    String accountCode = ftsRemote.getAccountCode(accountNo);
                    acNature = acctNature(accountCode);
                    String orgnCode = ftsRemote.getCurrentBrnCode(accountNo);
                    if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                            || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                        custnamelist1 = em.createNativeQuery("select ifnull(custname,'') from td_customermaster where "
                                + "custno='" + accountNo.substring(4, 10) + "' and actype='" + accountCode + "' and brncode = '" + orgnCode + "'").
                                getResultList();
                        if (!custnamelist1.isEmpty()) {
                            Vector custnamelist1Vec = (Vector) custnamelist1.get(0);
                            name = custnamelist1Vec.get(0).toString();
                        }
                    } else {
                        custnamelist2 = em.createNativeQuery("select ifnull(custname,'') from customermaster where "
                                + "custno='" + accountNo.substring(4, 10) + "' and actype='" + accountCode + "' and brncode = '" + orgnCode + "'").getResultList();
                        if (!custnamelist2.isEmpty()) {
                            Vector custnamelist2Vec = (Vector) custnamelist2.get(0);
                            name = custnamelist2Vec.get(0).toString();
                        }
                    }
                    resultList.add(name);
                    resultList.add(accountNo);
                }
            }
            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List jtDetails(String acno, String orgnBrCode) throws ApplicationException {
        String JtcustId = "";
        String acNature;
        List accountmasterlist = new ArrayList();
        List JtcustId1List = new ArrayList();
        String occupationCode;
        List resultList = new ArrayList();
        try {
            String accountCode = ftsRemote.getAccountCode(acno);
            acNature = acctNature(accountCode);
            if (acNature.equalsIgnoreCase(CbsConstant.FIXED_AC)
                    || acNature.equalsIgnoreCase(CbsConstant.MS_AC)) {
                accountmasterlist = em.createNativeQuery("select ifnull(custid1,''),ifnull(custid2,''),ifnull(custid3,''),"
                        + "ifnull(custid4,'') from td_accountmaster where acno ='" + acno + "'").getResultList();
                if (accountmasterlist.isEmpty()) {
                    resultList.add("Sorry Joint Holder Details Are Not Present");
                    return resultList;
                } else {
                    Vector custIdVec1 = (Vector) accountmasterlist.get(0);
                    JtcustId1List.add(custIdVec1.get(0).toString());
                    JtcustId1List.add(custIdVec1.get(1).toString());
                    JtcustId1List.add(custIdVec1.get(2).toString());
                    JtcustId1List.add(custIdVec1.get(3).toString());
                }
            } else {
                accountmasterlist = em.createNativeQuery("select ifnull(custid1,''),ifnull(custid2,''),ifnull(custid3,''),"
                        + "ifnull(custid4,'') from accountmaster where acno ='" + acno + "'").getResultList();
                if (accountmasterlist.isEmpty()) {
                    resultList.add("Sorry Joint Holder Details Are Not Present");
                    return resultList;
                } else {
                    Vector custIdVec1 = (Vector) accountmasterlist.get(0);
                    JtcustId1List.add(custIdVec1.get(0).toString());
                    JtcustId1List.add(custIdVec1.get(1).toString());
                    JtcustId1List.add(custIdVec1.get(2).toString());
                    JtcustId1List.add(custIdVec1.get(3).toString());
                }
            }

            List nomDetailslist = em.createNativeQuery("select ifnull(nomname,''),ifnull(nomadd,''),ifnull(relation,'') from nom_details where acno = '" + acno + "'").getResultList();
            if (!nomDetailslist.isEmpty()) {
                Vector nomDetailsVec = (Vector) nomDetailslist.get(0);
                resultList.add(nomDetailsVec.get(0).toString());
                resultList.add(nomDetailsVec.get(1).toString());
                resultList.add(nomDetailsVec.get(2).toString());
            } else {
                resultList.add("");
                resultList.add("");
                resultList.add("");
            }
            if (!JtcustId1List.isEmpty()) {
                for (int i = 0; i < JtcustId1List.size(); i++) {
                    JtcustId = JtcustId1List.get(i).toString();

                    List jtlist = em.createNativeQuery("select ifnull(concat(title,' ',custname),'') jt1name,ifnull(date_format(coalesce(DateOfBirth,0),'%d/%m/%Y'),'') as DateOfBirth,"
                            + "ifnull(concat(peraddressline1,' ',peraddressline2),''),ifnull(fathername,''),ifnull(PAN_GIRNumber,'') from cbs_customer_master_detail where "
                            + "customerid = '" + JtcustId + "'").getResultList();

                    if (!jtlist.isEmpty()) {
                        Vector jtlistVec = (Vector) jtlist.get(0);
                        resultList.add(jtlistVec.get(0).toString());
                        resultList.add(jtlistVec.get(1).toString());
                        resultList.add(jtlistVec.get(2).toString());
                        resultList.add(jtlistVec.get(3).toString());
                        resultList.add(jtlistVec.get(4).toString());
                    } else {
                        resultList.add("");
                        resultList.add("");
                        resultList.add("");
                        resultList.add("");
                        resultList.add("");
                    }

                    List occupationCodelist = em.createNativeQuery("select occupationCode from cbs_cust_misinfo where customerid= '" + JtcustId + "'").getResultList();
                    if (!occupationCodelist.isEmpty()) {
                        Vector occupationCodelistVec = (Vector) occupationCodelist.get(0);
                        occupationCode = occupationCodelistVec.get(0).toString();
                        List reflist = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_rec_no='021' and ref_code = '" + occupationCode + "'").getResultList();
                        if (!reflist.isEmpty()) {
                            Vector refVec = (Vector) reflist.get(0);
                            resultList.add(refVec.get(0).toString());
                        } else {
                            resultList.add("");
                        }

                    } else {
                        resultList.add("");
                    }
                }
            }

            return resultList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List misDetails(String acno) throws ApplicationException {
        List varlist = new ArrayList();
        List misList = new ArrayList();
        String sector;
        String SubSector;
        String purposeAdvance;
        String modeAdvance;
        String typeAdvance;
        String secured;
        String guaranteeCover;
        String industryType;
        String sickness;
        String exposure;
        String restructuring;
        String sanctionLevel;
        String sanAuth;
        String intTable;
        String interestType;
        String schemeCode;
        String npaCal;
        String court;
        String modeOfSet;
        String debtWaiverReason;
        String assetClassReason;
        String populationGroup;

        try {
            varlist = em.createNativeQuery("select ifnull(SECTOR,''),ifnull(SUB_SECTOR,''),ifnull(PURPOSE_OF_ADVANCE,''),"
                    + "ifnull(MODE_OF_ADVANCE,''),ifnull(TYPE_OF_ADVANCE,''),ifnull(SECURED,''), ifnull(GUARANTEE_COVER,''),"
                    + "ifnull(INDUSTRY_TYPE,''),ifnull(SICKNESS,''),ifnull(EXPOSURE,''),ifnull(RESTRUCTURING,''),ifnull(SANCTION_LEVEL,''),"
                    + "ifnull(SANCTIONING_AUTHORITY,''),ifnull(INTEREST_TABLE,''),ifnull(INTEREST_TYPE,''),ifnull(SCHEME_CODE,''),"
                    + "ifnull(NPA_CLASSIFICATION,''),ifnull(COURTS,''),ifnull(MODE_OF_SETTLEMENT,''),ifnull(DEBT_WAIVER_REASON,'') ,"
                    + "ifnull(ASSET_CLASS_REASON,''),ifnull(POPULATION_GROUP,'') from cbs_loan_borrower_details where acc_no = '" + acno + "'").getResultList();
            if (varlist.size() > 0) {
                Vector refVec = (Vector) varlist.get(0);
                sector = getReferenceCode(refVec.get(0).toString(), "182");
                misList.add(sector);

                SubSector = getReferenceCode(refVec.get(1).toString(), "183");
                misList.add(SubSector);

                purposeAdvance = getReferenceCode(refVec.get(2).toString(), "184");
                misList.add(purposeAdvance);

                modeAdvance = getReferenceCode(refVec.get(3).toString(), "185");
                misList.add(modeAdvance);

                typeAdvance = getReferenceCode(refVec.get(4).toString(), "186");
                misList.add(typeAdvance);
                secured = getReferenceCode(refVec.get(5).toString(), "187");
                misList.add(secured);

                guaranteeCover = getReferenceCode(refVec.get(6).toString(), "188");
                misList.add(guaranteeCover);

                industryType = getReferenceCode(refVec.get(7).toString(), "189");
                misList.add(industryType);

                sickness = getReferenceCode(refVec.get(8).toString(), "190");
                misList.add(sickness);

                exposure = getReferenceCode(refVec.get(9).toString(), "191");
                misList.add(exposure);

                restructuring = getReferenceCode(refVec.get(10).toString(), "192");
                misList.add(restructuring);

                sanctionLevel = getReferenceCode(refVec.get(11).toString(), "193");
                misList.add(sanctionLevel);

                sanAuth = getReferenceCode(refVec.get(12).toString(), "194");
                misList.add(sanAuth);

                intTable = getReferenceCode(refVec.get(13).toString(), "201");
                misList.add(intTable);

                interestType = getReferenceCode(refVec.get(14).toString(), "202");
                misList.add(interestType);

                schemeCode = getReferenceCode(refVec.get(15).toString(), "203");
                misList.add(schemeCode);

                npaCal = getReferenceCode(refVec.get(16).toString(), "195");
                misList.add(npaCal);

                court = getReferenceCode(refVec.get(17).toString(), "196");
                misList.add(court);

                modeOfSet = getReferenceCode(refVec.get(18).toString(), "197");
                misList.add(modeOfSet);

                debtWaiverReason = getReferenceCode(refVec.get(19).toString(), "198");
                misList.add(debtWaiverReason);

                assetClassReason = getReferenceCode(refVec.get(20).toString(), "199");
                misList.add(assetClassReason);

                populationGroup = getReferenceCode(refVec.get(21).toString(), "200");
                misList.add(populationGroup);

                return misList;
            }

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return misList;
    }

    public String getReferenceCode(String refcode, String refRecNo) throws ApplicationException {
        String desc = null;
        try {
            List listForSector = em.createNativeQuery("select ifnull(ref_desc,'') from cbs_ref_rec_type where ref_code='" + refcode + "' and ref_rec_no='" + refRecNo + "'").getResultList();
            if (listForSector.size() > 0) {
                Vector refVec = (Vector) listForSector.get(0);
                desc = refVec.get(0).toString();
            } else {
                desc = "";
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getCustDetails(String acno) throws ApplicationException {
        List custList = new ArrayList();
        String custId = null;
        String occupCode;
        String constCode;
        String casteCode;
        String communityCode;
        String businessSeg;
        String salesTurnover;
        String custStatus;
        String stateCode;
        String village;
        String block;
        String tehsil;
        String externalRatingLongTerm;
        String externalRatingShortTerm;
        String OperRiskRating;
        String CreditRiskRatingInternal;
        String stateCodeMinor;

        try {
            List acnoList = em.createNativeQuery("select custId from customerid where acno = '" + acno + "'").getResultList();
            if (acnoList.size() > 0) {
                Vector acnoVec = (Vector) acnoList.get(0);
                custId = acnoVec.get(0).toString();
            }

            List cbsCustMisInfoList = em.createNativeQuery("select ifnull(OccupationCode,''),ifnull(ConstitutionCode,''),"
                    + "ifnull(CasteCode,''),ifnull(CommunityCode,''),ifnull(BusinessSegment,''),ifnull(SalesTurnover,'') "
                    + "from cbs_cust_misinfo where customerid = " + custId + "").getResultList();
            if (cbsCustMisInfoList.size() > 0) {
                Vector cbsCustMisInfoVec = (Vector) cbsCustMisInfoList.get(0);
                occupCode = getReferenceCode(cbsCustMisInfoVec.get(0).toString(), "021");
                custList.add(occupCode);

                constCode = getReferenceCode(cbsCustMisInfoVec.get(1).toString(), "044");
                custList.add(constCode);

                casteCode = getReferenceCode(cbsCustMisInfoVec.get(2).toString(), "054");
                custList.add(casteCode);

                communityCode = getReferenceCode(cbsCustMisInfoVec.get(3).toString(), "013");
                custList.add(communityCode);

                businessSeg = cbsCustMisInfoVec.get(4).toString();
                custList.add(businessSeg);

                salesTurnover = cbsCustMisInfoVec.get(5).toString();
                custList.add(salesTurnover);

            } else {
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
            }

            List cbsCustMasterInfoList = em.createNativeQuery("select ifnull(CustStatus,''),ifnull(PerStateCode,''),ifnull(PerVillage,''),ifnull(ExternalRatingLongTerm,''),"
                    + "ifnull(ExternalRatingShortTerm,''),ifnull(PerBlock,''),ifnull(MailBlock,''),ifnull(OperationalRiskRating,''),ifnull(CreditRiskRatingInternal,'') from "
                    + "cbs_customer_master_detail   where customerid = " + custId + "").getResultList();
            if (cbsCustMasterInfoList.size() > 0) {
                Vector cbsCustMisInfoVec = (Vector) cbsCustMasterInfoList.get(0);
                custStatus = getReferenceCode(cbsCustMisInfoVec.get(0).toString(), "025");
                custList.add(custStatus);

                stateCode = getReferenceCode(cbsCustMisInfoVec.get(1).toString(), "002");
                custList.add(stateCode);

                village = cbsCustMisInfoVec.get(2).toString();
                custList.add(village);

                externalRatingLongTerm = cbsCustMisInfoVec.get(3).toString();
                //System.out.println("externalRatingLongTerm -->"+externalRatingLongTerm);
                custList.add(externalRatingLongTerm);
                //     System.out.println("custList -->2" + custList);
                externalRatingShortTerm = cbsCustMisInfoVec.get(4).toString();
                custList.add(externalRatingShortTerm);

                tehsil = cbsCustMisInfoVec.get(5).toString();
                custList.add(tehsil);

                block = cbsCustMisInfoVec.get(6).toString();
                custList.add(block);

                OperRiskRating = cbsCustMisInfoVec.get(7).toString();
                custList.add(OperRiskRating);

                CreditRiskRatingInternal = cbsCustMisInfoVec.get(8).toString();
                custList.add(CreditRiskRatingInternal);

            } else {
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
                custList.add("");
            }
            List cbsCustMinorInfoList = em.createNativeQuery("select ifnull(StateCode,'') from cbs_cust_minorinfo where customerid = " + custId + "").getResultList();
            if (cbsCustMinorInfoList.size() > 0) {
                Vector cbsCustMinorInfoVec = (Vector) cbsCustMinorInfoList.get(0);
                stateCodeMinor = getReferenceCode(cbsCustMinorInfoVec.get(0).toString(), "002");
                custList.add(stateCodeMinor);
            } else {
                custList.add("");
            }
            return custList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String fnGetLoanStatus(String acno, String date) throws ApplicationException {
        String status = null;
        try {
            List loanAppParameter = em.createNativeQuery("Select acno from loan_appparameter where acno='" + acno + "'").getResultList();
            if (loanAppParameter.size() < 0) {
                return "Record not found";
            }

            List accountStatus = em.createNativeQuery("Select ifnull(Remark,'') from accountstatus where acno='" + acno + "' and "
                    + "effdt=(Select max(Effdt) from accountstatus where EffDt<='" + date + "' and acno='" + acno + "' and (spflag in (11,12,13,14,3,6) "
                    + "or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) and spno=(Select max(Spno) "
                    + "from accountstatus where acno='" + acno + "' and EffDt<='" + date + "' and (spflag in (11,12,13,14,3,6) or (spflag=1 and remark like "
                    + "'Standard%') or (spflag=1 and remark like 'Operative%')))) and (spflag in (11,12,13,14,3,6) or (spflag=1 and remark"
                    + " like 'Standard%') or (spflag=1 and remark like 'Operative%'))  AND AUTH='Y'").getResultList();
            if (accountStatus.size() > 0) {
                Vector accountStatusVec = (Vector) accountStatus.get(0);
                status = accountStatusVec.get(0).toString();
                if (status.indexOf("###") >= 0) {
                    List statusList = em.createNativeQuery("Select left('" + status + "',locate('###','" + status + "')-1)").getResultList();
                    Vector StatusVec = (Vector) statusList.get(0);
                    return StatusVec.get(0).toString();
                } else {
                    if (status.equalsIgnoreCase("") || status == null) {
                        status = "STANDARD";
                        return status;
                    }
                }
            } else {
                status = "STANDARD";
                return status;
            }
            return status;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String CategoryOptForAcct(String acno) throws ApplicationException {
        String categoryOpt = "";
        try {

            List loanBorrower = em.createNativeQuery("Select  ifnull(CATEGORY_OPT,'') from cbs_loan_borrower_details where acc_no = '" + acno + "'").getResultList();
            if (loanBorrower.size() > 0) {
                Vector loanBorrowerVec = (Vector) loanBorrower.get(0);
                categoryOpt = getReferenceCode(loanBorrowerVec.get(0).toString(), "209");
            } else {
                categoryOpt = "";
            }
            return categoryOpt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Float FnBALOSForAccountAsOn(String acno, String date) throws ApplicationException {

        Float BALANCE = 0.0f;
        List balanceList = null;
        try {
            String accountCode = ftsRemote.getAccountCode(acno);
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM ca_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.TD_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM td_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM loan_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM loan_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM rdrecon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM gl_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.OF_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM of_recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.NON_PERFORM_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM nparecon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND  DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.MS_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM "
                        + "td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND "
                        + "DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.FIXED_AC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) "
                        + "FROM td_recon WHERE AUTH= 'Y' AND ACNO = '" + acno + "' AND CLOSEFLAG IS NULL AND TRANTYPE<>27 AND "
                        + "DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.DEPOSIT_SC)) {
                balanceList = em.createNativeQuery("SELECT SUM(ifnull(CRAMT,0)) - SUM(ifnull(DRAMT,0)) FROM recon "
                        + "WHERE AUTH= 'Y' AND  ACNO = '" + acno + "' AND   DT <=date_format('" + date + "','%Y%m%d')").getResultList();
            }
            if (balanceList.size() > 0) {
                Vector balanceVec = (Vector) balanceList.get(0);
                BALANCE = Float.parseFloat(balanceVec.get(0).toString());
            }
            return BALANCE;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public Float FnOverDueForaccount(String acno, String Dt) throws ApplicationException {

        Float TEMP_OVERDUE_AMT = 0.0f;
        Integer CODE = null;
        Float BALANCE = 0.0f;
        Float BALANCE1 = 0.0f;
        Float BALANCE2 = 0.0f;
        Float BALANCE3 = 0.0f;
        Float BALANCE4 = 0.0f;
        Float BALANCE5 = 0.0f;
        Float BALANCE6;
        Float BALANCE7;
        Float IDEALINSTPAID = 0.0f;
        Float MAXINSTALLAMT = 0.0f;
        Float ACTUALINSTPAID = 0.0f;
        Float INSTALLAMTOVERDUE = 0.0f;
        Float EXCESSAMT = 0.0f;
        Float FN_OVERDUE = 0.0f;
        String DATE1 = null;
        Float INST_AMT = 1f;
        Integer NO_INST = 0;
        Float REC_AMT = 0.0f;
        Integer OVERDUE_EMI = 0;
        String DUEDT = null;
        Float OVERDUE_AMT = 0.0f;
        Float OS_AMT = 0.0f;
        Integer dueDiff = null;
        Integer dueDiff2 = null;
        Integer TempDiff = null;
        try {

            if (Dt == null || Dt.equalsIgnoreCase("")) {
                List dateList = em.createNativeQuery("SELECT CURRENT_TIMESTAMP").getResultList();
                if (!dateList.isEmpty()) {
                    Vector dtVec = (Vector) dateList.get(0);
                    Dt = dtVec.get(0).toString();
                }
            }
            List codeList = em.createNativeQuery("SELECT ifnull(CODE,0) FROM parameterinfo_report WHERE REPORTNAME='OVERDUE_EMI_CAL'").getResultList();

            if (!codeList.isEmpty()) {
                Vector codeVec = (Vector) codeList.get(0);
                CODE = Integer.parseInt(codeVec.get(0).toString());

            }
            String accountCode = ftsRemote.getAccountCode(acno);
            if (CODE == 1) {
                if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    List osList = em.createNativeQuery("SELECT ifnull((SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0))),0)  FROM loan_recon "
                            + "WHERE DT<=date_format('" + Dt + "','%Y%m%d') AND  ACNO ='" + acno + "' AND AUTH='Y' ").getResultList();
                    if (!osList.isEmpty()) {
                        Vector osVec = (Vector) osList.get(0);
                        OS_AMT = Float.parseFloat(osVec.get(0).toString());

                    }
                    if (!accountCode.equalsIgnoreCase(CbsAcCodeConstant.NL_AC.getAcctCode())) {
                        List instAmtList = em.createNativeQuery("SELECT ifnull(INSTALLAMT,1),COUNT(ifnull(INSTALLAMT,0))  FROM "
                                + "emidetails WHERE ACNO='" + acno + "' AND DUEDT<=date_format('" + Dt + "','%Y%m%d') GROUP BY INSTALLAMT").getResultList();

                        if (!instAmtList.isEmpty()) {
                            Vector instAmtVec = (Vector) instAmtList.get(0);
                            INST_AMT = Float.parseFloat(instAmtVec.get(0).toString());
                            NO_INST = Integer.parseInt(instAmtVec.get(1).toString());
                        }

                        List recAmtList = em.createNativeQuery("SELECT ifnull(SUM(ifnull(CRAMT,0)),0)  FROM loan_recon WHERE"
                                + " DT<=date_format('" + Dt + "','%Y%m%d') AND  ACNO ='" + acno + "' AND AUTH='Y'").getResultList();
                        if (!recAmtList.isEmpty()) {
                            Vector recAmtVec = (Vector) recAmtList.get(0);
                            REC_AMT = Float.parseFloat(recAmtVec.get(0).toString());

                        }

                        Float OVERVALUE = NO_INST.floatValue() - (REC_AMT / INST_AMT);
                        OVERDUE_EMI = OVERVALUE.intValue();

                        List dueDtList = em.createNativeQuery("SELECT MAX(DUEDT) FROM emidetails WHERE ACNO='" + acno + "'").getResultList();
                        if (!dueDtList.isEmpty()) {
                            Vector dueDtVec = (Vector) dueDtList.get(0);
                            DUEDT = dueDtVec.get(0).toString();

                        }
                        List dueDtList1 = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + DUEDT + "','" + Dt + "')").getResultList();
                        if (!dueDtList1.isEmpty()) {
                            Vector dueDt1Vec = (Vector) dueDtList1.get(0);
                            TempDiff = Integer.parseInt(dueDt1Vec.get(0).toString());

                        }

                        if (TempDiff >= 0) {
                            if (OVERDUE_EMI < 0) {
                                OVERDUE_EMI = 0;
                                OVERDUE_AMT = OS_AMT;
                            } else if (OVERDUE_EMI > 0) {
                                List overDueAmtList = em.createNativeQuery("SELECT " + NO_INST + "*ifnull(" + INST_AMT + ",0) - ifnull(" + REC_AMT + ",0)").getResultList();
                                if (!overDueAmtList.isEmpty()) {
                                    Vector overDueAmtVec = (Vector) overDueAmtList.get(0);
                                    OVERDUE_AMT = Float.parseFloat(overDueAmtVec.get(0).toString());
                                }
                            }

                        } else {
                            OVERDUE_AMT = 0.0f;
                        }

                    }
                    if (accountCode.equalsIgnoreCase(CbsAcCodeConstant.NL_AC.getAcctCode())) {
                        OVERDUE_AMT = 0.0f;
                    }

                }
                if (accountCode.equalsIgnoreCase(CbsAcCodeConstant.CURRENT_AC.getAcctCode())) {
                    List CARECONList = em.createNativeQuery("SELECT ifnull((SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0))),0) "
                            + " FROM ca_recon WHERE DT<=date_format('" + Dt + "','%Y%m%d') AND  ACNO ='" + acno + "' AND AUTH='Y'").getResultList();
                    if (!CARECONList.isEmpty()) {
                        Vector CARECONVec = (Vector) CARECONList.get(0);
                        OS_AMT = Float.parseFloat(CARECONVec.get(0).toString());

                    }
                    List APPPARAMETERList = em.createNativeQuery("SELECT  ifnull((ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))-L.ODLIMIT),"
                            + "0)  FROM loan_appparameter L,ca_recon C WHERE L.ACNO=C.ACNO AND  C.DT<=date_format('" + Dt + "','%Y%m%d') AND "
                            + "C.ACNO='" + acno + "'  GROUP BY L.ODLIMIT  HAVING ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))>L.ODLIMIT ").getResultList();
                    if (!APPPARAMETERList.isEmpty()) {
                        Vector APPPARAMETERVec = (Vector) APPPARAMETERList.get(0);
                        OVERDUE_AMT = Float.parseFloat(APPPARAMETERVec.get(0).toString());

                    }
                }
                if (accountCode.equalsIgnoreCase(CbsAcCodeConstant.DEMAND_LOAN.getAcctCode())) {
                    List CARECONList = em.createNativeQuery("SELECT ifnull((SUM(ifnull(CRAMT,0))-SUM(ifnull(DRAMT,0))),0) "
                            + " FROM loan_recon WHERE DT<=date_format('" + Dt + "','%Y%m%d') AND  ACNO ='" + acno + "' AND AUTH='Y'").getResultList();
                    if (!CARECONList.isEmpty()) {
                        Vector CARECONVec = (Vector) CARECONList.get(0);
                        OS_AMT = Float.parseFloat(CARECONVec.get(0).toString());
                    }
                    List APPPARAMETERList = em.createNativeQuery("SELECT  ifnull((ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))-L.SANCTIONLIMIT),0) "
                            + " FROM loan_appparameter L,loan_recon C WHERE L.ACNO=C.ACNO AND  C.DT<=date_format('" + Dt + "','%Y%m%d') AND"
                            + " C.ACNO='" + acno + "' GROUP BY L.SANCTIONLIMIT  HAVING ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))>L.SANCTIONLIMIT ").getResultList();
                    if (!APPPARAMETERList.isEmpty()) {
                        Vector APPPARAMETERVec = (Vector) APPPARAMETERList.get(0);
                        OVERDUE_AMT = Float.parseFloat(APPPARAMETERVec.get(0).toString());

                    }
                }

                if (Math.abs(OVERDUE_AMT) > Math.abs(OS_AMT)) {
                    OVERDUE_AMT = Math.abs(OS_AMT);
                } else {
                    OVERDUE_AMT = 0f;
                }

            } else {
                if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                    if (!accountCode.equalsIgnoreCase(CbsAcCodeConstant.NL_AC.getAcctCode())) {
                        List dueDtList = em.createNativeQuery("SELECT ifnull(MAX(DUEDT),'') FROM emidetails WHERE ACNO='" + acno + "'").getResultList();
                        if (!dueDtList.isEmpty()) {
                            Vector dueDtVec = (Vector) dueDtList.get(0);
                            DATE1 = dueDtVec.get(0).toString();

                        }
                        List dueDiffList = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,date_format('" + Dt + "','%Y%m%d'),"
                                + "date_format('" + DATE1 + "','%Y%m%d'))").getResultList();
                        if (!dueDiffList.isEmpty()) {
                            Vector dueDiffVec = (Vector) dueDiffList.get(0);
                            dueDiff = Integer.parseInt(dueDiffVec.get(0).toString());

                        }
                        if (dueDiff >= 0) {
                            List emiDetailList = em.createNativeQuery("SELECT COUNT(1) FROM emidetails WHERE ACNO= '" + acno + "' AND "
                                    + "DUEDT<=date_format('" + Dt + "','%Y%m%d')").getResultList();
                            if (!emiDetailList.isEmpty()) {
                                Vector emiDetailVec = (Vector) emiDetailList.get(0);
                                IDEALINSTPAID = Float.parseFloat(emiDetailVec.get(0).toString());

                            }

                            List actualInstPaidList = em.createNativeQuery("SELECT COUNT(1) FROM emidetails WHERE ACNO='" + acno + "'"
                                    + " AND PAYMENTDT<=date_format('" + Dt + "','%Y%m%d')").getResultList();
                            if (!actualInstPaidList.isEmpty()) {
                                Vector actualInstPaidVec = (Vector) actualInstPaidList.get(0);
                                ACTUALINSTPAID = Float.parseFloat(actualInstPaidVec.get(0).toString());

                            }
                            if (IDEALINSTPAID >= ACTUALINSTPAID) {
                                INSTALLAMTOVERDUE = IDEALINSTPAID - ACTUALINSTPAID;

                            }
                            if (IDEALINSTPAID < ACTUALINSTPAID) {
                                INSTALLAMTOVERDUE = 0f;

                            }
                            List emiList = em.createNativeQuery("SELECT MAX(INSTALLAMT) FROM emidetails WHERE ACNO= '" + acno + "'").getResultList();
                            if (!emiList.isEmpty()) {
                                Vector emiVec = (Vector) emiList.get(0);
                                MAXINSTALLAMT = Float.parseFloat(emiVec.get(0).toString());

                            }

                            List emiDList = em.createNativeQuery("SELECT ifnull(E.EXCESSAMT,0) FROM emidetails E WHERE E.DUEDT "
                                    + "IN (SELECT MAX(DUEDT) FROM emidetails WHERE PAYMENTDT <= date_format('" + Dt + "','%Y%m%d') AND ACNO = "
                                    + "'" + acno + "') AND E.ACNO = '" + acno + "'").getResultList();
                            if (!emiDList.isEmpty()) {
                                for (int i = 0; i < emiDList.size(); i++) {
                                    Vector emiDVec = (Vector) emiDList.get(i);
                                    EXCESSAMT = Float.parseFloat(emiDVec.get(0).toString());

                                }
                            }
                            if (IDEALINSTPAID >= ACTUALINSTPAID) {
                                if (((MAXINSTALLAMT) * (INSTALLAMTOVERDUE)) > 0) {
                                    OVERDUE_AMT = ((MAXINSTALLAMT) * (INSTALLAMTOVERDUE)) - EXCESSAMT;
                                }
                                if (((MAXINSTALLAMT) * (INSTALLAMTOVERDUE)) <= 0) {
                                    OVERDUE_AMT = ((MAXINSTALLAMT) * (INSTALLAMTOVERDUE));
                                }

                            }
                            List dateConvList = em.createNativeQuery("SELECT date_format('" + Dt + "','%Y%m%d')").getResultList();
                            if (!dateConvList.isEmpty()) {
                                Vector dateConvVec = (Vector) dateConvList.get(0);
                                Dt = dateConvVec.get(0).toString();
                            }
                            BALANCE5 = FnBALOSForAccountAsOn(acno, Dt);
                            if (OVERDUE_AMT > Math.abs(BALANCE5) && Math.abs(BALANCE5) < 0) {
                                OVERDUE_AMT = Math.abs(BALANCE5);
                            }
                            if (BALANCE5 > 0) {
                                OVERDUE_AMT = 0f;
                            }

                        }
                        List dateDList = em.createNativeQuery("SELECT TIMESTAMPDIFF(DAY,date_format('" + Dt + "','%Y%m%d'),date_format('" + DATE1 + "','%Y%m%d'))").getResultList();
                        if (!dateDList.isEmpty()) {
                            Vector dateDVec = (Vector) dateDList.get(0);
                            dueDiff2 = Integer.parseInt(dateDVec.get(0).toString());

                        }
                        if (dueDiff2 < 0) {
                            BALANCE5 = FnBALOSForAccountAsOn(acno, Dt);
                            if (BALANCE5 < 0) {
                                OVERDUE_AMT = Math.abs(BALANCE5);
                            } else {
                                OVERDUE_AMT = 0f;
                            }

                        }
                    }
                    if (accountCode.equalsIgnoreCase(CbsAcCodeConstant.NL_AC.getAcctCode())) {
                        OVERDUE_AMT = 0.0f;
                    }
                } // NATURE END
                if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                    List overDList = em.createNativeQuery("SELECT ifnull((ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))-L.ODLIMIT),0) "
                            + " FROM  loan_appparameter L,ca_recon C WHERE L.ACNO=C.ACNO AND  C.DT<=date_format('" + Dt + "','%Y%m%d') AND"
                            + " C.ACNO='" + acno + "' GROUP BY L.ODLIMIT  HAVING ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))>L.ODLIMIT").getResultList();
                    if (!overDList.isEmpty()) {
                        Vector overDVec = (Vector) overDList.get(0);
                        OVERDUE_AMT = Float.parseFloat(overDVec.get(0).toString());
                    }
                }
                if (acctNature(accountCode).equalsIgnoreCase(CbsConstant.DEMAND_LOAN)) {
                    List overDList = em.createNativeQuery("SELECT  ifnull((ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))-L.SANCTIONLIMIT),"
                            + "0)  FROM loan_appparameter L,loan_recon C WHERE L.ACNO=C.ACNO AND  C.DT<=date_format('" + Dt + "','%Y%m%d') AND "
                            + "C.ACNO='" + acno + "' GROUP BY L.SANCTIONLIMIT  HAVING ABS((SUM(C.CRAMT)-SUM(C.DRAMT)))>L.SANCTIONLIMIT").getResultList();
                    if (!overDList.isEmpty()) {
                        Vector overDVec = (Vector) overDList.get(0);
                        OVERDUE_AMT = Float.parseFloat(overDVec.get(0).toString());
                    }
                }
            }

            return OVERDUE_AMT;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getChequeFacility(String acno) throws ApplicationException {
        List chequeFacilityList = null;
        try {
            chequeFacilityList = em.createNativeQuery("select coalesce(chequebook,0) from accountmaster where acno='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return chequeFacilityList;
    }

    public List proprietorDetails(String acno) throws ApplicationException {
        List proprietorList = new ArrayList();
        try {
            List proprietorData = em.createNativeQuery("select ifnull(JtName1,'') , ifnull(JtName2,''),ifnull(JtName3,''),ifnull(JtName4,'')from accountmaster where OperMode='8' and ACNo='" + acno + "'").getResultList();
            if (!proprietorData.isEmpty()) {
                Vector vec = (Vector) proprietorData.get(0);
                proprietorList.add(vec.get(0).toString());
                proprietorList.add(vec.get(1).toString());
                proprietorList.add(vec.get(2).toString());
                proprietorList.add(vec.get(3).toString());
            } else {
                proprietorList.add("");
                proprietorList.add("");
                proprietorList.add("");
                proprietorList.add("");
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return proprietorList;
    }
}
