/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.share;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dto.HoTransactionTable;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author admin
 */
@Stateless(mappedName = "CertIssueFacade")
@TransactionManagement(TransactionManagementType.BEAN)
public class CertIssueFacade implements CertIssueFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPost43CBSRemote;

    public List accType() throws ApplicationException {
        try {
            List actype = em.createNativeQuery("select acctcode from accounttypemaster").getResultList();
            return actype;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getRelatedTo() throws ApplicationException {
        try {
            List actype = em.createNativeQuery("select code,description from codebook where groupcode = 42 and code <> 0 order by code").getResultList();
            return actype;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAdviseNo() throws ApplicationException {
        try {
            List actype = em.createNativeQuery("Select Distinct InstCode From billtypemaster").getResultList();
            return actype;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBranchCode() throws ApplicationException {
        try {
            List actype = em.createNativeQuery("SELECT ALPHACODE,BrnCode FROM branchmaster ").getResultList();
            return actype;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getAccountDetailsNormal(String acno) throws ApplicationException {
        try {
            Vector vtr;
            List erdetail = em.createNativeQuery("select ifnull(productcode,0) from accounttypemaster where acctcode='" + acno.substring(2, 4) + "'").getResultList();
            vtr = (Vector) erdetail.get(0);
            if (vtr.get(0).toString().equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {
                List rgfolio = em.createNativeQuery("SELECT cd.custname,sh.regdate from share_holder sh, cbs_customer_master_detail cd where sh.regfoliono='" + acno + "' and sh.custid = cd.customerid ").getResultList();
                return rgfolio;
            } else {
                List acctdt = em.createNativeQuery("select custname,openingdt,accstatus from accountmaster where acno='" + acno + "'").getResultList();
                return acctdt;
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAccountDetailsGlhead(String acno) throws ApplicationException {
        try {
            List acctdt = em.createNativeQuery("Select acname,acno,postflag,MSGFLAG from gltable where acno='" + acno + "'").getResultList();
            return acctdt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getShareFolioDetails(String acno) throws ApplicationException {
        try {
            List details = em.createNativeQuery("select cd.custname,cd.fathername,sh.alphacode,sh.authflag from share_holder sh, cbs_customer_master_detail cd  Where sh.regfoliono ='" + acno + "' and sh.custid = cd.customerid ").getResultList();
            return details;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public double getperShareValue() throws ApplicationException {
        try {
            List sharevalue = em.createNativeQuery("select ifnull(shareamt,0) from share_value where effectivedt=(select max(effectivedt) from share_value where effectivedt<='" + ymd.format(new java.util.Date()) + "')").getResultList();
            if (!sharevalue.isEmpty()) {
                Vector vtr = (Vector) sharevalue.get(0);
                return Double.parseDouble(vtr.get(0).toString());
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return 0;
    }

    public int getAvailableShare(String acno) throws ApplicationException {
        try {
            List noofshare = em.createNativeQuery("select count(shareno) from share_capital_issue where foliono = '" + acno + "'").getResultList();
            Vector vtr = (Vector) noofshare.get(0);
            return Integer.parseInt(vtr.get(0).toString());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List getShareDetails(String acno) throws ApplicationException {
        try {
            List details = em.createNativeQuery("Select r.shareCertNo as certNo,r.total as noofShares,r.shareMin as Fromno, r.shareMax as tono, "
                    + "c.issueDt as IssueDate,c.issuedBy as enterBy,c.AdviceNo as AdviceNo,c.PoNo as PoNo,c.PaymentDt as PaymentDt,c.CertIssueDt as "
                    + "CertIssueDt,ifnull(c.remark,0) as Remark,case c.status When 'A' then 'ACTIVE' when 'C' then 'INACTIVE' else 'OTHERS' end, "
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= c.CertIssueDt)) as shVal "
                    + "From (Select shareCertNo as shareCertNo,count(shareno) as Total,min(shareno) as shareMin,max(shareno) as shareMax, "
                    + "lastUpdateTime as issueDt,lastUpdateBy as updateBy From share_capital_issue Where foliono='" + acno + "' Group By shareCertNo) "
                    + "as r join certificate_share c on r.shareCertno=c.certificateNo order by issuedate").getResultList();
            return details;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getGlHeadforHeadOffice() throws ApplicationException {
        String acno = "";
        try {
            List selectList = em.createNativeQuery("select acno from gltable where acname like 'HEAD OFFICE'").getResultList();
            Vector acnoList = (Vector) selectList.get(0);
            acno = acnoList.get(0).toString().substring(2);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return acno;
    }

    public String saveData(String user, String orgnBrCode, List<HoTransactionTable> table) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        boolean result = true;
        try {
            ut.begin();
            float trsno = ftsPost43CBSRemote.getTrsNo();
            if (result) {
                for (int i = 0; i < table.size(); i++) {
                    HoTransactionTable obj = table.get(i);
                    String a = table.get(i).getAuthBy().toString();
                    msg = ftsPost43CBSRemote.ftsPosting43CBS(obj.getAcno(), Integer.parseInt(obj.getTranType()), Integer.parseInt(obj.getTy()),
                            obj.getAmount(), ymd.format(obj.getDt()), ymd.format(obj.getValDt()), obj.getEnteredby(), obj.getOrgBrId(),
                            obj.getDestBrId(), Integer.parseInt(obj.getTranDesc()), obj.getDetails(), trsno, obj.getRecNo(), obj.getTranId(),
                            obj.getTermId(), obj.getAuth(), obj.getAuthBy(), obj.getSubTokeNo(), Integer.parseInt(obj.getPayBy()), obj.getInstNo(),
                            obj.getInstDt(), obj.getTdAcnoDt(), obj.getVoucherNo(), obj.getIntFlag(), obj.getCloseFlag(), obj.getScreenFlag(),
                            obj.getTxnStatus(), obj.getTokenNoDr(), obj.getCxsxFlag(), obj.getAdviseNo(), obj.getAdviseBranch(), "");

                    if (msg.contains("True")) {
                        msg = "Data is successfully Saved batch No = " + trsno;
                    } else if (msg.contains("TRUE0.0")) {
                        msg = "Data is successfully Saved batch No = " + trsno;
                    } else {
                        ut.rollback();
                        return msg;
                    }
                }
                ut.commit();
            } else {
                msg = "Some problem Occur";
                ut.rollback();
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
        return msg;
    }

    public double getBalanceAccountNo(String accNo, String TmpAcctNature) throws ApplicationException {
        List list;
        Vector vtr;
        if (TmpAcctNature.equalsIgnoreCase(CbsConstant.FIXED_AC) || TmpAcctNature.equalsIgnoreCase(CbsConstant.MS_AC)
                || TmpAcctNature.equalsIgnoreCase(CbsConstant.OF_AC)) {
            list = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM td_reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        } else if (TmpAcctNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
            list = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM ca_reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        } else {
            list = em.createNativeQuery("SELECT ROUND(IFNULL(BALANCE,0),2) FROM reconbalan WHERE ACNO ='" + accNo + "'").getResultList();
        }
        if (!list.isEmpty()) {
            vtr = (Vector) list.get(0);
            return Double.parseDouble(vtr.get(0).toString());
        }
        return 0;

    }

    public List getAllBranchExcludingHoAndCell() throws ApplicationException {
        try {
            return em.createNativeQuery("select alphacode,brncode from branchmaster where alphacode "
                    + "not in('HO','CELL') order by  brncode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAllRefRecNoData(String refRecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type "
                    + "where ref_rec_no='" + refRecNo + "' order by ref_code").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List getBankProfile(Integer brncode) throws ApplicationException {
        try {
            return em.createNativeQuery("select company_name as bankname,address as regofficeadd,pin as regofficepin ,"
                    + "ho_address,ho_pin,company_reg as status,cstno as bankcategory,rbi_rating,ist_no as subcategory,"
                    + "bank_region,date_format(license_date,'%d/%m/%Y'),lst_no as licenceno,fin_year_from as lastinspectiondt,"
                    + "books_begining_from as agmdt,total_regular_members,total_nominal_members,date_format(last_int_audit_date,'%d/%m/%Y'),"
                    + "major_irregularities_no,minor_irregularities_no,paras_fully_no,paras_outstanding_no "
                    + "from company_master where company_code = " + brncode + "").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveAndUpdateBankProfile(String bankName, String regAdd, String regPin, String hoAdd,
            String hoPin, String status, String category, String rbiRating, String subCategory,
            String bankRegion, String licenceDt, String licenceNo, String lastInsPectionDt, String agmDt,
            BigInteger totRegMembers, BigInteger totNomMembers, String lastIntAutditDt, BigDecimal majorIrr,
            BigDecimal minorIrr, BigDecimal parasFully, BigDecimal parasOutstand, Integer brncode,
            String userName, String todayDate, String opFlag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            if (opFlag.equalsIgnoreCase("A")) {
                List list = em.createNativeQuery("select company_name from company_master where "
                        + "company_code = " + brncode + "").getResultList();
                if (!list.isEmpty()) {
                    throw new ApplicationException("There is already an entry for :" + brncode);
                }
            } else if (opFlag.equalsIgnoreCase("M")) {
                int insertResult = em.createNativeQuery("insert into company_master_history(company_name,company_code,mailing_name,"
                        + "address,state,city,pin,country_code,parent_company,parent_comp_code,default_company,def_comp_code,"
                        + "active_flag,income_tax_no,lst_no,cstno,ist_no,service_tax,company_reg,apply_vat,vat_tin_no,fin_year_from,"
                        + "books_begining_from,base_currrency,base_currency_notation,floating_points,auth_flag,auth_status,auth_by,"
                        + "tran_time,effect_dt,entered_by,ho_address,ho_pin,license_date,last_int_audit_date,total_regular_members,"
                        + "total_nominal_members,major_irregularities_no,minor_irregularities_no,paras_fully_no,paras_outstanding_no,"
                        + "rbi_rating,bank_region) select company_name,company_code,mailing_name,address,state,city,pin,country_code,"
                        + "parent_company,parent_comp_code,default_company,def_comp_code,active_flag,income_tax_no,lst_no,cstno,ist_no,"
                        + "service_tax,company_reg,apply_vat,vat_tin_no,fin_year_from,books_begining_from,base_currrency,"
                        + "base_currency_notation,floating_points,auth_flag,auth_status,auth_by,tran_time,effect_dt,entered_by,"
                        + "ho_address,ho_pin,license_date,last_int_audit_date,total_regular_members,total_nominal_members,"
                        + "major_irregularities_no,minor_irregularities_no,paras_fully_no,paras_outstanding_no,rbi_rating,"
                        + "bank_region from company_master where company_code = " + brncode + "").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Insertion Problem In Company Master History.");
                }
                insertResult = em.createNativeQuery("delete from company_master where company_code = " + brncode + "").executeUpdate();
                if (insertResult <= 0) {
                    throw new ApplicationException("Deletion Problem In Company Master.");
                }
            }
            int insertResult = em.createNativeQuery("insert into company_master(company_name,company_code,address,pin,country_code,"
                    + "lst_no,cstno,ist_no,company_reg,fin_year_from,books_begining_from,auth_status,auth_by,tran_time,"
                    + "effect_dt,entered_by,ho_address,ho_pin,license_date,last_int_audit_date,total_regular_members,"
                    + "total_nominal_members,major_irregularities_no,minor_irregularities_no,paras_fully_no,"
                    + "paras_outstanding_no,rbi_rating,bank_region,active_flag,income_tax_no,apply_vat,base_currrency,"
                    + "base_currency_notation,floating_points,auth_flag) values('" + bankName + "'," + brncode + ","
                    + "'" + regAdd + "','" + regPin + "',0,'" + licenceNo + "','" + category + "','" + subCategory + "',"
                    + "'" + status + "','" + lastInsPectionDt + "','" + agmDt + "','Y','" + userName + "',now(),"
                    + "'" + ymd.format(dmy.parse(todayDate)) + "','" + userName + "','" + hoAdd + "','" + hoPin + "',"
                    + "'" + licenceDt + "','" + lastIntAutditDt + "'," + totRegMembers + "," + totNomMembers + ","
                    + "" + majorIrr + "," + minorIrr + "," + parasFully + "," + parasOutstand + ",'" + rbiRating + "',"
                    + "'" + bankRegion + "','Y','','N','','',0,'Y')").executeUpdate();
            if (insertResult <= 0) {
                throw new ApplicationException("Insertion Problem In Company Master.");
            }
            ut.commit();
            return "true";
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    public boolean isLoanOutStanding(String folioNo) throws ApplicationException {
        try {
            List acNoList = em.createNativeQuery("select acno from loan_recon where acno in (select acno from customerid where custid in "
                    + "(select custid from share_holder where regfoliono = '" + folioNo + "') and substring(acno,3,2) in "
                    + "(select acctcode from accounttypemaster where acctnature in ('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "'))) "
                    + "group by acno having cast(sum(cramt - dramt) as decimal(14,2)) < 0").getResultList();
            return !acNoList.isEmpty();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public double getDividendBal(String folioNo, String dt) throws ApplicationException {
        double dividendBal = 0;
        try {
            List list = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from dividend_recon where acno = '" + folioNo + "' and disdate <= '" + dt + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                dividendBal = Double.parseDouble(vtr.get(0).toString());
            }
            return dividendBal;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public List isUnathorizeFolio(String folioNo, String dt) throws ApplicationException {
        try {
            return em.createNativeQuery("select * from recon_trf_d where dt = '" + dt + "' and tdacno = '" + folioNo + "' and auth = 'N';").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
