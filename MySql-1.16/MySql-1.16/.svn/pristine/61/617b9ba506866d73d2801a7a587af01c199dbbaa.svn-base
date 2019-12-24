package com.cbs.facade.report;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.dto.report.ShareDividendPojo;
import com.cbs.dto.report.FolioAccountStatementPojo;
import com.cbs.dto.report.MemberLabelPojo;
import com.cbs.dto.report.ShareEnquiryPojo;
import com.cbs.dto.report.ShareAccountStatementPojo;
import com.cbs.dto.report.ShareCertPojo;
import com.cbs.dto.report.ShareDetailsReportPojo;
import com.cbs.dto.report.ShareDividendDetailPojo;
import com.cbs.dto.report.ShareFinalBalanceReportPojo;
import com.cbs.dto.report.ShareHoldersPojo;
import com.cbs.dto.report.ShareIssuedPojo;
import com.cbs.dto.report.ShareMemberDetailPojo;
import com.cbs.dto.report.SharePaymentPojo;
import com.cbs.dto.report.ShareStatusPojo;
import com.cbs.dto.report.ShareTransferPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.ho.share.ShareTransferFacadeRemote;
import com.cbs.facade.intcal.LoanInterestCalculationFacadeRemote;
import com.cbs.pojo.ShareCertificatePojo;
import com.cbs.pojo.MemberShareLetterPojo;
import com.cbs.utils.CbsUtil;
import com.cbs.utils.Spellar;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author admin
 */
@Stateless(name = "ShareReportFacade")
public class ShareReportFacade implements ShareReportFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private CommonReportMethodsRemote common;
    @EJB
    LoanInterestCalculationFacadeRemote loanInterestCalculationBean;
    @EJB
    private ShareTransferFacadeRemote remoteObject;
    private SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    NumberFormat nft = new DecimalFormat("0.00");

    public List getFolioStatementList(String folioNo, String date) throws ApplicationException {
        List<FolioAccountStatementPojo> returnList = new ArrayList<FolioAccountStatementPojo>();

        try {
            // Vector tempVector1 = (Vector) em.createNativeQuery("select ifnull(shareAmt,0) from share_value").getResultList().get(0);
            //   double shareValue = Double.parseDouble(tempVector1.get(0).toString());

            List result = em.createNativeQuery("select regfoliono,cc.custfullname,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),ifnull(perVillage,''),"
                    + "ifnull(perBlock,''),ifnull(percitycode,''),min(shareNo),max(shareno),count(shareno) ,sharecertno,status,cs.Issuedt, "
                    + "date_format(paymentdt,'%d/%m/%Y'),"
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                    + " from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                    + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo "
                    + "and cs.Issuedt<='" + date + "' group by regfoliono,cc.custfullname,cc.perAddressLine1,cc.perAddressLine2, perVillage,perBlock,percitycode,"
                    + "shareCertno,cs.issueDt,status,paymentdt").getResultList();
            for (int i = 0; i < result.size(); i++) {
                Vector tempVector = (Vector) result.get(i);
                int fromNo = Integer.parseInt(tempVector.get(7).toString());
                FolioAccountStatementPojo pojo = new FolioAccountStatementPojo();
                pojo.setFolioNo(tempVector.get(0).toString());
                pojo.setName(tempVector.get(1).toString());
                String city = "";
                if (!tempVector.get(6).toString().equals("")) {
                    city = common.getRefRecDesc("001", tempVector.get(6).toString());
                }
                pojo.setAddress(tempVector.get(2).toString() + " " + tempVector.get(3).toString() + " " + tempVector.get(4).toString() + " " + tempVector.get(5).toString() + " " + city);
                pojo.setDate((Date) tempVector.get(12));
                pojo.setStatus(tempVector.get(11).toString());
                pojo.setCertficateShareNo(tempVector.get(10).toString());

                if (tempVector.get(13) == null) {
                    pojo.setPaymentDt("");
                } else {
                    if (tempVector.get(13).toString().equalsIgnoreCase("01/01/1900")) {
                        pojo.setPaymentDt("");
                    } else {
                        pojo.setPaymentDt(tempVector.get(13) == null ? "" : tempVector.get(13).toString());
                    }
                }

                pojo.setFromNo(fromNo);

                pojo.setToNo(Integer.parseInt(tempVector.get(8).toString()));
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(9).toString()));
                if (tempVector.get(11).toString().equalsIgnoreCase("A")) {
                    pojo.setIssueAmt(Double.parseDouble(tempVector.get(14).toString()) * Integer.parseInt(tempVector.get(9).toString()));
                    pojo.setIssueShare(Integer.parseInt(tempVector.get(9).toString()));
                } else {
                    pojo.setPaidAmt(Double.parseDouble(tempVector.get(14).toString()) * Integer.parseInt(tempVector.get(9).toString()));
                    pojo.setPaidShare(Integer.parseInt(tempVector.get(9).toString()));
                }

                returnList.add(pojo);
                //           i--;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ShareTransferPojo> getTransfererReport(String frmDt, String toDt) throws ApplicationException {
        List<ShareTransferPojo> returnList = new ArrayList<ShareTransferPojo>();
        try {
            List result = em.createNativeQuery("select certno,trfFno,trfefno,trfrName,trfename,minshare,maxshare,noofshare,remark,trfdt from "
                    + "share_transfer where trfdt between '" + frmDt + "' and '" + toDt + "'").getResultList();
            for (int i = 0; i < result.size(); i++) {
                ShareTransferPojo pojo = new ShareTransferPojo();
                Vector tempVector = (Vector) result.get(i);
                pojo.setCertNo(Integer.parseInt(tempVector.get(0).toString()));
                pojo.setMaxShare(Integer.parseInt(tempVector.get(6).toString()));
                pojo.setMinShare(Integer.parseInt(tempVector.get(5).toString()));
                pojo.setRemarks(tempVector.get(8).toString());
                pojo.setTrfDate((Date) tempVector.get(9));
                pojo.setTrfereFolioNO(tempVector.get(2).toString());
                pojo.setTrfereName(tempVector.get(4).toString());
                pojo.setTrfrFolioNO(tempVector.get(1).toString());
                pojo.setTrfrName(tempVector.get(3).toString());
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(7).toString()));
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ShareFinalBalanceReportPojo> getFinalBalanceReport(String frmfoliono, String tofoliono, String dt, String alphaCode, String actCategory) throws ApplicationException {
        List<ShareFinalBalanceReportPojo> returnList = new ArrayList<ShareFinalBalanceReportPojo>();

        try {

            String accountCatg = "";
            if (actCategory.equalsIgnoreCase("ALL")) {
                accountCatg = "";
            } else {
                accountCatg = " and cc.CustEntityType = '" + actCategory + "'";
            }
            List divList = em.createNativeQuery("select acno, sum(cramt-dramt) from dividend_recon group by acno").getResultList();
            List result = new ArrayList();
            if (alphaCode.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select a.foliono,a.custfullname,sum(a.shval) from"
                        + "(select distinct foliono,cc.custfullname,sc.sharecertno,count(shareno)*"
                        + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                        + " from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where foliono between '"
                        + frmfoliono + "' and '" + tofoliono + "' and cs.issueDt<='" + dt + "' and sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                        + "" + accountCatg + " "
                        + "and cs.certificateno=sc.sharecertno and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + dt + "') "
                        + "group by foliono,custfullname,sc.sharecertno order by foliono) a group by a.foliono,a.custfullname").getResultList();
            } else {
                result = em.createNativeQuery("select a.foliono,a.custfullname,sum(a.shval) from"
                        + "(select distinct foliono,cc.custfullname,sc.sharecertno,count(shareno)*"
                        + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                        + " from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where foliono between '"
                        + frmfoliono + "' and '" + tofoliono + "' and cs.issueDt<='" + dt + "' and sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                        + "and sh.alphacode='" + alphaCode + "' " + accountCatg + " "
                        + "and cs.certificateno=sc.sharecertno and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + dt + "') "
                        + "group by foliono,custfullname,sc.sharecertno order by foliono) a group by a.foliono,a.custfullname").getResultList();
            }


            for (int i = 0; i < result.size(); i++) {
                Vector tempVector = (Vector) result.get(i);

                ShareFinalBalanceReportPojo pojo = new ShareFinalBalanceReportPojo();
                pojo.setAcno(tempVector.get(0).toString());
                pojo.setName(tempVector.get(1).toString());
                pojo.setShareAmount(Double.parseDouble(tempVector.get(2).toString()));
                pojo.setDivOutStn(getDividend(divList, tempVector.get(0).toString()));
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    private double getDividend(List divdendList, String folioNo) throws ApplicationException {
        try {
            System.out.println("Folio number is :" + folioNo);
            double dividend = 0;
            for (int i = 0; i < divdendList.size(); i++) {
                Vector divVect = (Vector) divdendList.get(i);
                if (divVect.get(0).toString().equals(folioNo)) {
                    dividend = Double.parseDouble(divVect.get(1).toString());
                    break;
                }
            }
            return dividend;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<ShareAccountStatementPojo> getAccountStatement(String folioNo, String frmDt, String toDt) throws ApplicationException {
        List<ShareAccountStatementPojo> returnList = new ArrayList<ShareAccountStatementPojo>();
        try {
            //  Vector tempVector = (Vector) em.createNativeQuery("select ifnull(shareAmt,0) from share_value").getResultList().get(0);
            //  double shareValue = Double.parseDouble(tempVector.get(0).toString());

            List result = em.createNativeQuery("select regfoliono,cc.custfullname,cc.perAddressLine1, ifnull(cc.perAddressLine2,''), ifnull(perVillage,''), "
                    + "ifnull(perBlock,''),ifnull(percitycode,''), cc.mailAddressLine1, ifnull(cc.mailAddressLine2,''), ifnull(mailVillage,''), "
                    + "ifnull(mailBlock,''),ifnull(mailcitycode,''),min(shareNo),max(shareno),count(shareno) ,sharecertno,cs.Issuedt,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName, "
                    + "ifnull(nomname,'') ,ifnull(nomrelation,''),ifnull(alphacode,''),status, "
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                    + " from share_capital_issue sc,share_holder sh,certificate_share cs, "
                    + "cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and sh.regfoliono='" + folioNo
                    + "' and cs.certificateNo=sc.sharecertNo and cs.Issuedt<='" + toDt + "' group by regfoliono,cc.custfullname,"
                    + "cc.perAddressLine1,cc.perAddressLine2, perVillage,perBlock,percitycode, mailAddressLine1, mailAddressLine2, mailVillage, "
                    + "mailBlock,mailcitycode,shareCertno,sh.permadd,cs.issueDt,FatherName,nomname,nomrelation,alphacode").getResultList();

            List result1 = em.createNativeQuery("select ifnull(sum(cramt-dramt),0) from dividend_recon where acno='" + folioNo + "' and disdate < '"
                    + frmDt + "'").getResultList();

            List result2 = em.createNativeQuery("select drAmt,crAmt,details,disdate from dividend_recon where acno='" + folioNo + "' and disdate between '"
                    + frmDt + "' and '" + toDt + "'").getResultList();

            Vector tempVector1 = (Vector) result1.get(0);
            double balance = Double.parseDouble(tempVector1.get(0).toString());
            Vector tempVector;
            for (int i = 0; i < result.size(); i++) {
                tempVector = (Vector) result.get(i);
                ShareAccountStatementPojo pojo = new ShareAccountStatementPojo();

                pojo.setAcno(tempVector.get(0).toString());
                pojo.setName(tempVector.get(1).toString());

                String city = "";
                if (!tempVector.get(6).toString().equals("")) {
                    city = common.getRefRecDesc("001", tempVector.get(6).toString());
                }
                pojo.setPermanentAdd(tempVector.get(2).toString() + " " + tempVector.get(3).toString() + " " + tempVector.get(4).toString() + " " + tempVector.get(5).toString() + " " + city);
                String mailCity = "";
                if (!tempVector.get(11).toString().equals("")) {
                    mailCity = common.getRefRecDesc("001", tempVector.get(11).toString());
                }
                pojo.setPresentAdd(tempVector.get(7).toString() + " " + tempVector.get(8).toString() + " " + tempVector.get(9).toString() + " " + tempVector.get(10).toString() + " " + mailCity);
                pojo.setOpenBal(Double.parseDouble(tempVector1.get(0).toString()));
                pojo.setFromNo(tempVector.get(12).toString());
                pojo.setToNo(tempVector.get(13).toString());

                String st = tempVector.get(21).toString();
                if (st.equalsIgnoreCase("A")) {
                    pojo.setShareAmt(Double.parseDouble(tempVector.get(22).toString()) * Integer.parseInt(tempVector.get(14).toString()));
                    balance = balance + pojo.getShareAmt();
                } else {
                    pojo.setShareAmt(0);
                    balance = balance;
                }

                pojo.setType("A");
                pojo.setBalance(balance);
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(14).toString()));
                pojo.setCertNo(tempVector.get(15).toString());

                pojo.setIssueDate((Date) tempVector.get(16));
                pojo.setFatherName(tempVector.get(17).toString());
                pojo.setNomneeName(tempVector.get(18).toString());
                pojo.setRelation(tempVector.get(19).toString());
                pojo.setBranch(tempVector.get(20).toString());

                returnList.add(pojo);
            }

            for (int i = 0; i < result2.size(); i++) {
                tempVector = (Vector) result2.get(i);
                ShareAccountStatementPojo pojo = new ShareAccountStatementPojo();
                pojo.setCrAmt(Double.parseDouble(tempVector.get(1).toString()));
                pojo.setDrAmt(Double.parseDouble(tempVector.get(0).toString()));
                pojo.setDetails(tempVector.get(2) != null ? tempVector.get(2).toString() : "");
                pojo.setDisDate((Date) tempVector.get(3));
                balance = balance + pojo.getCrAmt() - pojo.getDrAmt();
                pojo.setType("B");
                pojo.setBalance(balance);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ShareDetailsReportPojo> getShareDetailsReport(String query, String dt) throws ApplicationException {
        List<ShareDetailsReportPojo> returnList = new ArrayList<ShareDetailsReportPojo>();
        List result;
        Vector tempVector;
        try {

            result = em.createNativeQuery(query).getResultList();
            for (int i = 0; i < result.size(); i++) {
                tempVector = (Vector) result.get(i);

                ShareDetailsReportPojo pojo = new ShareDetailsReportPojo();
                pojo.setFolioNo(tempVector.get(0).toString());
                pojo.setFolioName(tempVector.get(1).toString());
                String city = "";
                if (!tempVector.get(6).toString().equals("")) {
                    city = common.getRefRecDesc("001", tempVector.get(6).toString());
                }
                pojo.setAddress(tempVector.get(2).toString() + " " + tempVector.get(3).toString() + " " + tempVector.get(4).toString() + " " + tempVector.get(5).toString() + " " + city);

                pojo.setFromNo(Integer.parseInt(tempVector.get(7).toString()));
                pojo.setToNO(Integer.parseInt(tempVector.get(8).toString()));
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(9).toString()));
                pojo.setCertNo(Integer.parseInt(tempVector.get(10).toString()));

                pojo.setStaus(tempVector.get(11).toString());
                pojo.setIssueDt((Date) tempVector.get(12));
                pojo.setBranch(tempVector.get(13).toString());

                pojo.setShareAmt(Double.parseDouble(tempVector.get(14).toString()) * Integer.parseInt(tempVector.get(9).toString()));
                pojo.setFatherName(tempVector.get(15).toString());
                pojo.setCustId(tempVector.get(16).toString());

                returnList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());

        }
        return returnList;
    }

    public List listEnterBy() throws ApplicationException {
        List tableResult = new ArrayList();
        try {
            Query selectQuery = em.createNativeQuery("select distinct enterby from share_holder");
            tableResult = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return tableResult;
    }

    public List<ShareEnquiryPojo> shareEnquiryReport(String fromFolio, String toFolio, String firstStarShareHolderName, String firstName,
            String LastStarShareHolderName, String LastName, String startHouseNo,
            String houseNo, String startSector, String sector, String startCity, String city, String enterBy, String fatherName) throws ApplicationException {
        List<ShareEnquiryPojo> returnList = new ArrayList<ShareEnquiryPojo>();
        try {
            List result = new ArrayList();
            Vector tempVector;
            if ((fromFolio != null) && (toFolio != null)) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName ,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where s.foliono between '" + fromFolio + "' and '" + toFolio + "' "
                        + "and s.foliono=sh.regfoliono and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if ((fromFolio != null) && (toFolio == null)) {

                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where s.foliono = '" + fromFolio + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            if (firstStarShareHolderName.equalsIgnoreCase("0") && firstName != null) {

                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where cc.custfullname like  '" + firstName + "%'  and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (firstStarShareHolderName.equalsIgnoreCase("1") && firstName != null) {

                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where cc.custfullname ='" + firstName + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (firstStarShareHolderName.equalsIgnoreCase("2") && firstName != null) {

                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where cc.custfullname like  '%" + firstName + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }

            if (startHouseNo.equalsIgnoreCase("0") && houseNo != null) {

                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.houseno like  '" + houseNo + "%' and s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startHouseNo.equalsIgnoreCase("1") && houseNo != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.houseno ='" + houseNo + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startHouseNo.equalsIgnoreCase("2") && houseNo != null) {
                result = em.createNativeQuery("select distinct s.folioNo, cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.houseno like  '%" + houseNo + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            if (startSector.equalsIgnoreCase("0") && sector != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.sector like  '" + sector + "%'  and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startSector.equalsIgnoreCase("1") && sector != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.sector ='" + sector + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startSector.equalsIgnoreCase("2") && sector != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.sector like  '%" + sector + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            if (startCity.equalsIgnoreCase("0") && city != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.city like  '" + city + "%'  and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startCity.equalsIgnoreCase("1") && city != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.city ='" + city + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            } else if (startCity.equalsIgnoreCase("2") && city != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.city like  '%" + city + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,'',FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            if (enterBy != null) {
                result = em.createNativeQuery("select distinct s.folioNo,cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where sh.enterby =  '" + enterBy + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            if (fatherName != null) {
                result = em.createNativeQuery("select distinct s.folioNo, cc.custfullname,'',concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,cc.PerAddressLine1,sh.enterby,"
                        + "count(distinct s.sharecertno),count(s.shareno),count(s.shareno)*(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= date_format(curdate(),'%Y%m%d'))) from "
                        + "share_capital_issue s,share_holder sh,cbs_customer_master_detail cc where cc.fathername =  '" + fatherName + "' and  s.foliono=sh.regfoliono "
                        + "and cc.customerid = sh.custid group by s.foliono,cc.custfullname,FatherName,cc.PerAddressLine1,sh.enterby").getResultList();
            }
            for (int i = 0; i < result.size(); i++) {
                ShareEnquiryPojo pojo = new ShareEnquiryPojo();
                tempVector = (Vector) result.get(i);
                pojo.setAcno(tempVector.get(0).toString());
                pojo.setfName(tempVector.get(1).toString());
                //pojo.setlName(tempVector.get(2).toString());
                pojo.setFatherName(tempVector.get(3).toString());
                pojo.setAddress(tempVector.get(4).toString());
                pojo.setEnterBy(tempVector.get(5).toString());
                pojo.setTotalCert(Integer.parseInt(tempVector.get(6).toString()));
                pojo.setTotalShares(Integer.parseInt(tempVector.get(7).toString()));
                pojo.setAmount(Double.parseDouble(tempVector.get(8).toString()));
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }

    }

    public List<ShareCertPojo> shareCertificateReport(String fromDt, String toDt) throws ApplicationException {
        List<ShareCertPojo> returnList = new ArrayList<ShareCertPojo>();

        try {
            Vector tempVector;
            List result = em.createNativeQuery("select foliono,cc.custfullname,shareCertno,cs.issueDt,min(shareNo),max(shareno),count(shareno),alphaCode,sh.relatedAcNo,"
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cc.customerId "
                    + "from share_capital_issue sc,certificate_share cs,share_holder sh, cbs_customer_master_detail cc where sh.custid = cc.customerid "
                    + "and sharecertno<>0 and sharecertno =certificateNo and sh.regfoliono=sc.foliono and cs.issuedt between '" + fromDt + "' and '" + toDt + "' "
                    + "group by foliono,cc.custfullname,shareCertno,cs.issueDt,alphaCode,sh.relatedAcNo").getResultList();
            for (int i = 0; i < result.size(); i++) {
                tempVector = (Vector) result.get(i);
                ShareCertPojo pojo = new ShareCertPojo();
                pojo.setFolioNo(tempVector.get(0).toString());
                pojo.setShareHolderName(tempVector.get(1).toString());
                pojo.setCertNo(Integer.parseInt(tempVector.get(2).toString()));

                pojo.setIssueDt((Date) tempVector.get(3));
                pojo.setFromNo(Integer.parseInt(tempVector.get(4).toString()));
                pojo.setToNO(Integer.parseInt(tempVector.get(5).toString()));
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(6).toString()));

                pojo.setBranch(tempVector.get(7).toString());
                pojo.setRelatedAcno(tempVector.get(8) != null ? tempVector.get(8).toString() : "");
                pojo.setShareAmt(Float.parseFloat(tempVector.get(9).toString()) * Integer.parseInt(tempVector.get(6).toString()));
                pojo.setCustomerId(tempVector.get(10).toString());
                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
    }

    public List<SharePaymentPojo> sharePaymentReport(String fromDt, String toDt) throws ApplicationException {
        List<SharePaymentPojo> returnList = new ArrayList<SharePaymentPojo>();

        try {
            Vector tempVector;
            List result = em.createNativeQuery("select foliono,cc.custfullname,shareCertno,ifnull(DATE_FORMAT(cs.paymentDt,'%d/%m/%Y'),''),min(shareNo),max(shareno),"
                    + "count(shareno),ifnull(cs.adviceno,''), ifnull(cs.pono,''),(select shareAmt from share_value where effectivedt = "
                    + "(select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cc.customerId from share_capital_issue_his sc,certificate_share cs,"
                    + "share_holder sh, cbs_customer_master_detail cc where sh.custid = cc.customerid and sharecertno<>0 and "
                    + "sharecertno =certificateNo and sh.regfoliono=sc.foliono and cs.paymentDt between '" + fromDt + "' and '" + toDt
                    + "' group by foliono,cc.custfullname,shareCertno,cs.paymentdt,adviceno,pono").getResultList();
            for (int i = 0; i < result.size(); i++) {
                tempVector = (Vector) result.get(i);
                SharePaymentPojo pojo = new SharePaymentPojo();
                pojo.setFolioNo(tempVector.get(0).toString());
                pojo.setShareHolderName(tempVector.get(1).toString());
                pojo.setCertNo(Integer.parseInt(tempVector.get(2).toString()));
                pojo.setPaymentDt(dmy.parse(tempVector.get(3).toString()));

                pojo.setFromNo(Integer.parseInt(tempVector.get(4).toString()));
                pojo.setToNO(Integer.parseInt(tempVector.get(5).toString()));
                pojo.setNoOfShare(Integer.parseInt(tempVector.get(6).toString()));

                pojo.setAdvice(tempVector.get(7).toString());
                pojo.setPayorder(tempVector.get(8).toString());
                pojo.setShareAmt(Float.parseFloat(tempVector.get(9).toString()) * Integer.parseInt(tempVector.get(6).toString()));
                pojo.setCustomerId(tempVector.get(10).toString());

                returnList.add(pojo);
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<ShareDividendPojo> getDividenDreport(String frmDt, String toDt, String foliono) throws ApplicationException {
        List<ShareDividendPojo> returnList = new ArrayList<ShareDividendPojo>();
        try {
            String name = "";
            double openbalance = 0d, dramt, cramt, balance = 0;
            int noOfShare = 0;
            List result;
            Vector tempvtr;
            result = em.createNativeQuery("select ifnull(sum(crAmt-drAmt),0)  from dividend_recon where acno ='" + foliono + "'  and disdate < '" + frmDt + "'").getResultList();
            tempvtr = (Vector) result.get(0);
            openbalance = Double.parseDouble(tempvtr.get(0).toString());
            result = em.createNativeQuery("select distinct foliono,cc.custfullname,count(shareno) from share_capital_issue sc,share_holder sh,"
                    + "certificate_share cs,cbs_customer_master_detail cc where sh.custid = cc.customerid and foliono ='" + foliono + "' and "
                    + "sc.issueDt<='" + toDt + "' and sc.foliono=sh.regfoliono and cs.certificateno=sc.sharecertno and cs.status='A'"
                    + " group by foliono,custfullname order by foliono").getResultList();
            if (!result.isEmpty()) {
                tempvtr = (Vector) result.get(0);
                name = tempvtr.get(1).toString();
                noOfShare = Integer.parseInt(tempvtr.get(2).toString());
            }
            result = em.createNativeQuery("select DrAmt,CrAmt,disdate from dividend_recon where acno ='" + foliono + "'  and disdate between '"
                    + frmDt + "' and '" + toDt + "' order by disdate").getResultList();
            for (int i = 0; i < result.size(); i++) {
                tempvtr = (Vector) result.get(i);
                ShareDividendPojo pojo = new ShareDividendPojo();
                dramt = Double.parseDouble(tempvtr.get(0) != null ? tempvtr.get(0).toString() : "0.0");
                cramt = Double.parseDouble(tempvtr.get(1) != null ? tempvtr.get(1).toString() : "0.0");
                balance = balance + cramt - dramt;
                pojo.setOpenBalance(openbalance);
                pojo.setCrAmt(cramt);
                pojo.setDrAmt(dramt);
                pojo.setDate((Date) tempvtr.get(2));
                pojo.setBalance(balance);
                pojo.setName(name);
                pojo.setNoOfShare(noOfShare);
                pojo.setFinalBalance(balance);
                returnList.add(pojo);
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ShareStatusPojo> getShareStatus(int frShareNo, int toShareNo, String status) throws ApplicationException {
        List<ShareStatusPojo> returnList = new ArrayList<ShareStatusPojo>();
        try {
            List shareList = null;
            if (status.equalsIgnoreCase("ACTIVE")) {
                shareList = em.createNativeQuery("select foliono,sharecertno,count(sharecertno),cc.custfullname,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),"
                        + " ifnull(perVillage,''), ifnull(perBlock,''),ifnull(percitycode,'') from share_capital_issue sc,share_holder sh, "
                        + "cbs_customer_master_detail cc where sharecertno in (select certificateno from certificate_share where status ='A') "
                        + "and cc.customerid = sh.custid and sh.regfoliono = sc.foliono group by foliono,sharecertno,custfullname,perAddressLine1, perAddressLine2, "
                        + "perVillage,perBlock,percitycode having count(shareno) between '" + frShareNo + "' and '" + toShareNo + "' order by foliono").getResultList();
            } else if (status.equalsIgnoreCase("CLOSE")) {
                shareList = em.createNativeQuery("select foliono,sharecertno,count(sharecertno),cc.custfullname,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),"
                        + " ifnull(perVillage,''), ifnull(perBlock,''),ifnull(percitycode,'') from share_capital_issue sc,share_holder sh, "
                        + "cbs_customer_master_detail cc where sharecertno in (select certificateno from certificate_share where status ='C') "
                        + "and cc.customerid = sh.custid and sh.regfoliono = sc.foliono group by foliono,sharecertno,custfullname,perAddressLine1, perAddressLine2, "
                        + "perVillage,perBlock,percitycode having count(shareno) between '" + frShareNo + "' and '" + toShareNo + "' order by foliono").getResultList();
            } else {
                shareList = em.createNativeQuery("select foliono,sharecertno,count(sharecertno),cc.custfullname,cc.perAddressLine1, ifnull(cc.perAddressLine2,''),"
                        + " ifnull(perVillage,''), ifnull(perBlock,''),ifnull(percitycode,'') from share_capital_issue sc,share_holder sh, "
                        + "cbs_customer_master_detail cc where sharecertno in (select certificateno from certificate_share where status in ('A','C')) "
                        + "and cc.customerid = sh.custid and sh.regfoliono = sc.foliono group by foliono,sharecertno,custfullname,perAddressLine1, perAddressLine2, "
                        + "perVillage,perBlock,percitycode having count(shareno) between '" + frShareNo + "' and '" + toShareNo + "' order by foliono").getResultList();
            }
            if (shareList.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            for (int i = 0; i < shareList.size(); i++) {
                Vector shareV = (Vector) shareList.get(i);
                ShareStatusPojo pojo = new ShareStatusPojo();
                pojo.setFolioNo(shareV.get(0).toString());
                pojo.setShareCertNo(shareV.get(1).toString());

                pojo.setShares(Integer.parseInt(shareV.get(2).toString()));
                pojo.setAcName(shareV.get(3).toString());

                String city = "";
                if (!shareV.get(6).toString().equals("")) {
                    city = common.getRefRecDesc("001", shareV.get(8).toString());
                }
                pojo.setAddress(shareV.get(4).toString() + " " + shareV.get(5).toString() + " " + shareV.get(6).toString() + " " + shareV.get(7).toString() + " " + city);

                returnList.add(pojo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return returnList;
    }

    public List<ShareIssuedPojo> getTransfererReportWise(String frmDt, String toDt) {
        List<ShareIssuedPojo> returnList = new ArrayList<ShareIssuedPojo>();
        try {
            List list3 = em.createNativeQuery("select distinct foliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName, cs.certificateNo,count(shareno),date_format(cs.issuedt,'%d/%m/%Y'),"
                    + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                    + " from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where  cs.issueDt between '" + frmDt + "'and "
                    + "'" + toDt + "' and  sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateno=sc.sharecertno "
                    + "group by foliono,custfullname,FatherName,cs.certificateNo order by cs.issuedt").getResultList();
            for (int n = 0; n < list3.size(); n++) {
                Vector vector = (Vector) list3.get(n);

                ShareIssuedPojo pojo = new ShareIssuedPojo();
                pojo.setCertificateNo(vector.get(3).toString());
                pojo.setIssueDt(vector.get(5).toString());

                pojo.setAmount(Integer.parseInt(vector.get(4).toString()) * Double.parseDouble(vector.get(6).toString()));

                pojo.setFatherName(vector.get(2).toString());
                pojo.setHolderName(vector.get(1).toString());
                pojo.setShareCertNo(vector.get(3).toString());

                pojo.setFolioNo(vector.get(0).toString());
                pojo.setShareNo(vector.get(4).toString());
                returnList.add(pojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public List<ShareHoldersPojo> getShareHolders(String alphaCode, String frAcNo, String toAcNo, String frDt, String toDt, String orderBy, String accountCategory, String status, String repOption) throws ApplicationException {
        List<ShareHoldersPojo> returnList = new ArrayList<ShareHoldersPojo>();
        try {
            String orderBy4 = "";
            if (repOption.equalsIgnoreCase("4")) {
                if (orderBy.equalsIgnoreCase("1")) {
                    orderBy4 = "order by s.Regfoliono";
                } else {
                    orderBy4 = "order by s.Regdate";
                }
            } else {
                if (repOption.equalsIgnoreCase("3")) {
                    if (orderBy.equalsIgnoreCase("1")) {
                        orderBy = "si.FolioNo";
                    } else if (orderBy.equalsIgnoreCase("4")) {
                        orderBy = "cs.issueDt";
                    } else {
                        orderBy = "sh.FName";
                    }
                } else {
                    if (orderBy.equalsIgnoreCase("1")) {
                        orderBy = "si.FolioNo,shVal";
                    } else if (orderBy.equalsIgnoreCase("4")) {
                        orderBy = "cs.issueDt";
                    } else {
                        orderBy = "sh.FName";
                    }
                }
            }
            String accountCatg = "", statusQuery = "";
            if (accountCategory.equalsIgnoreCase("ALL")) {
                accountCatg = "";
            } else {
                accountCatg = " and cc.CustEntityType = '" + accountCategory + "'";
            }

            if (status.equalsIgnoreCase("A")) {
                statusQuery = " and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + toDt + "') ";
            } else if (status.equalsIgnoreCase("C")) {
                statusQuery = " and cs.paymentdt between '" + frDt + "' and '" + toDt + "' ";
            } else {
                statusQuery = "";
            }

            List resultList = new ArrayList();
            if (repOption.equalsIgnoreCase("3")) {
                if (alphaCode.equalsIgnoreCase("ALL")) {
                    resultList = em.createNativeQuery("select si.foliono,cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''),\n"
                            + "concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,upper(PerDistrict),PerPostalCode,cs.issueDt,upper(rf.REF_DESC) \n"
                            + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc,cbs_ref_rec_type rf  \n"
                            + "where sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono \n"
                            + "" + statusQuery + " and rf.REF_REC_NO = '002' and cc.PerStateCode = rf.REF_CODE \n"
                            + "and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + " \n"
                            + "group by custfullname,cc.peraddressline1, cc.peraddressline2,pervillage,perblock,percitycode,si.foliono,FatherName,sh.alphacode,\n"
                            + "sh.purpose,sh.relatedacno Order By " + orderBy).getResultList();
                } else {
                    resultList = em.createNativeQuery("select si.foliono,cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''),\n"
                            + "concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,upper(PerDistrict),PerPostalCode,cs.issueDt,upper(rf.REF_DESC) \n"
                            + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc,branchmaster br,cbs_ref_rec_type rf \n"
                            + "where br.alphacode='" + alphaCode + "' and sh.alphacode=br.alphacode and sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono \n"
                            + "" + statusQuery + " and rf.REF_REC_NO = '002' and cc.PerStateCode = rf.REF_CODE \n"
                            + "and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + "\n"
                            + "group by custfullname,cc.peraddressline1, cc.peraddressline2,pervillage,perblock,percitycode,si.foliono,FatherName,sh.alphacode,\n"
                            + "sh.purpose,sh.relatedacno Order By " + orderBy).getResultList();
                }
                if (resultList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                for (int i = 0; i < resultList.size(); i++) {
                    Vector resV = (Vector) resultList.get(i);
                    ShareHoldersPojo pojo = new ShareHoldersPojo();
                    pojo.setFolioNo(resV.get(0).toString());
                    pojo.setShName(resV.get(1).toString());
                    pojo.setFhName(resV.get(7).toString());
                    pojo.setAddress(resV.get(2).toString().trim() + " " + resV.get(3).toString().trim() + " " + resV.get(4).toString().trim() + " " + resV.get(5).toString().trim());
                    pojo.setCity(resV.get(11).toString());
                    pojo.setPinCode(resV.get(9).toString());
                    returnList.add(pojo);
                }
            } else if (repOption.equalsIgnoreCase("4")) {
                String alphaCodeCond = "";
                if (alphaCode.equalsIgnoreCase("ALL")) {
                    alphaCodeCond = "";
                } else {
                    alphaCodeCond = "and AlphaCode = '" + alphaCode + "'";
                }
                resultList = em.createNativeQuery("select s.Regfoliono,s.custId,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName"
                        + ",date_format(cc.DateOfBirth,'%d/%m/%Y') dob,date_format(s.Regdate,'%d/%m/%Y') from share_holder s,cbs_customer_master_detail cc "
                        + "where s.custid = cc.customerid and s.Regdate between '" + frDt + "' and '" + toDt + "' " + alphaCodeCond + " " + accountCatg + " " + orderBy4 + "").getResultList();
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector vtr = (Vector) resultList.get(i);
                        ShareHoldersPojo pojo = new ShareHoldersPojo();

                        pojo.setFolioNo(vtr.get(0).toString());
                        pojo.setCustId(vtr.get(1).toString());
                        pojo.setShName(vtr.get(2).toString());
                        pojo.setFhName(vtr.get(3).toString());
                        pojo.setDob(vtr.get(4).toString());
                        pojo.setRegistrationDt(vtr.get(5).toString());
                        returnList.add(pojo);
                    }
                }
            } else {
                if (alphaCode.equalsIgnoreCase("ALL")) {
                    if (repOption.equalsIgnoreCase("1")) {
                        resultList = em.createNativeQuery("select cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''),"
                                + " si.foliono,count(si.shareno) total,min(si.shareno) sharemin, max(si.shareno) sharemax,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,sh.alphacode,sh.purpose,sh.relatedacno,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo,upper(PerDistrict),PerPostalCode,cs.issueDt  "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc "
                                + "where sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono " + statusQuery + " and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by custfullname,cc.peraddressline1, cc.peraddressline2,pervillage,perblock,percitycode,si.foliono,FatherName,sh.alphacode,"
                                + "sh.purpose,sh.relatedacno,shVal Order By " + orderBy).getResultList();
                    } else {
                        resultList = em.createNativeQuery("select cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''),"
                                + " si.foliono,count(si.shareno) total,min(si.shareno) sharemin, max(si.shareno) sharemax,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,sh.alphacode,sh.purpose,sh.relatedacno,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo,upper(PerDistrict),PerPostalCode,cs.issueDt  "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc "
                                + "where sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono " + statusQuery + " "
                                + "and si.foliono between '" + frAcNo + "' and '" + toAcNo + "' and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by custfullname,cc.peraddressline1, cc.peraddressline2,pervillage,perblock,percitycode,si.foliono,FatherName,sh.alphacode,sh.purpose,"
                                + "sh.relatedacno,shVal Order By " + orderBy).getResultList();
                    }
                } else {
                    if (repOption.equalsIgnoreCase("1")) {
                        resultList = em.createNativeQuery("select cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''), "
                                + "si.foliono,count(si.shareno) total,min(si.shareno) sharemin, max(si.shareno) sharemax,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,sh.alphacode,sh.purpose,sh.relatedacno,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo,upper(PerDistrict),PerPostalCode,cs.issueDt  "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc, "
                                + "branchmaster br where br.alphacode='" + alphaCode + "' and sh.alphacode=br.alphacode and sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono "
                                + "" + statusQuery + " and cs.issueDt between '" + frDt + "' and '" + toDt + "'" + accountCatg + " group by custname,cc.peraddressline1, cc.peraddressline2,pervillage,perblock,percitycode,si.foliono,FatherName,"
                                + "sh.alphacode,sh.purpose,sh.relatedacno,shVal Order By " + orderBy).getResultList();
                    } else {
                        resultList = em.createNativeQuery("select cc.custfullname,cc.peraddressline1, ifnull(cc.peraddressline2,''), ifnull(pervillage,''), ifnull(perblock,''),ifnull(percitycode,''), "
                                + "si.foliono,count(si.shareno) total,min(si.shareno) sharemin, max(si.shareno) sharemax,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,sh.alphacode,sh.purpose,sh.relatedacno,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo,upper(PerDistrict),PerPostalCode,cs.issueDt  "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc,"
                                + " branchmaster br where br.alphacode='" + alphaCode + "' and sh.alphacode=br.alphacode and sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono "
                                + "" + statusQuery + " and si.foliono between '" + frAcNo + "' and '" + toAcNo + "' and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by custfullname,cc.peraddressline1, cc.peraddressline2,"
                                + "pervillage,perblock,percitycode,si.foliono,FatherName,sh.alphacode,sh.purpose,sh.relatedacno,shVal Order By " + orderBy).getResultList();
                    }
                }

                if (resultList.isEmpty()) {
                    throw new ApplicationException("Data does not exist");
                }
                for (int i = 0; i < resultList.size(); i++) {
                    Vector resV = (Vector) resultList.get(i);
                    ShareHoldersPojo pojo = new ShareHoldersPojo();
                    pojo.setShName(resV.get(0).toString());
                    String city = resV.get(16).toString();
//                if (!resV.get(6).toString().equals("")) {
//                    city = common.getRefRecDesc("001", resV.get(5).toString());
//                }
                    pojo.setAddress(resV.get(1).toString().trim() + " " + resV.get(2).toString().trim() + " " + resV.get(3).toString().trim() + " " + resV.get(4).toString().trim() + " " + city);
                    pojo.setFolioNo(resV.get(6).toString());
                    pojo.setTotalSr(Integer.parseInt(resV.get(7).toString()));
                    pojo.setMinShareNo(Long.parseLong(resV.get(8).toString()));
                    pojo.setMaxShareNo(Long.parseLong(resV.get(9).toString()));
                    pojo.setFhName(resV.get(10).toString());
                    pojo.setBranch(resV.get(11).toString());
                    pojo.setPurpose(resV.get(12) != null ? resV.get(12).toString() : "");
                    pojo.setRelatedacno(resV.get(13) != null ? resV.get(13).toString() : "");
                    pojo.setShareValue(Double.parseDouble(resV.get(14).toString()));
                    pojo.setShareAmt(Double.parseDouble(resV.get(14).toString()) * Integer.parseInt(resV.get(7).toString()));
                    pojo.setCity(resV.get(16).toString());
                    pojo.setPinCode(resV.get(17).toString());
                    pojo.setType("A");
                    returnList.add(pojo);
                }
                if (alphaCode.equalsIgnoreCase("ALL")) {
                    if (repOption.equalsIgnoreCase("1")) {
                        resultList = em.createNativeQuery("select count(si.shareno) total,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc "
                                + "where sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono " + statusQuery + " and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by shVal Order By shVal").getResultList();
                    } else {
                        resultList = em.createNativeQuery("select count(si.shareno) total,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc "
                                + "where sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono " + statusQuery + " "
                                + "and si.foliono between '" + frAcNo + "' and '" + toAcNo + "' and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by shVal Order By shVal").getResultList();
                    }
                } else {
                    if (repOption.equalsIgnoreCase("1")) {
                        resultList = em.createNativeQuery("select count(si.shareno) total,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc, "
                                + "branchmaster br where br.alphacode='" + alphaCode + "' and sh.alphacode=br.alphacode and sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono "
                                + "" + statusQuery + " and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg
                                + " group by shVal Order By shVal").getResultList();
                    } else {
                        resultList = em.createNativeQuery("select count(si.shareno) total,"
                                + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal,cs.certificateNo "
                                + "from share_holder sh,share_capital_issue si,certificate_share cs,cbs_customer_master_detail cc,"
                                + " branchmaster br where br.alphacode='" + alphaCode + "' and sh.alphacode=br.alphacode and sh.custid = cc.customerid and cs.certificateno=si.sharecertno and si.foliono=sh.regfoliono "
                                + "" + statusQuery + " and si.foliono between '" + frAcNo + "' and '" + toAcNo + "' and cs.issueDt between '" + frDt + "' and '" + toDt + "' " + accountCatg + ""
                                + "group by shVal Order By shVal").getResultList();
                    }
                }

                for (int i = 0; i < resultList.size(); i++) {
                    Vector sumVector = (Vector) resultList.get(i);
                    ShareHoldersPojo pojo = new ShareHoldersPojo();
                    pojo.setTotalSr(Integer.parseInt(sumVector.get(0).toString()));
                    pojo.setShareValue(Double.parseDouble(sumVector.get(1).toString()));
                    pojo.setShareAmt(Integer.parseInt(sumVector.get(0).toString()) * Double.parseDouble(sumVector.get(1).toString()));
                    pojo.setType("B");
                    // System.out.println("Share No: "+pojo.getTotalSr()+" share Value : "+pojo.getShareValue()+" Share Amt : "+pojo.getShareAmt());
                    returnList.add(pojo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return returnList;

    }

    public List getBrnNameandBrnAddress(String alphacode) throws ApplicationException {
        List returnList = new ArrayList();
        Vector ele = null;
        try {
            List list = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode "
                    + "and br.alphacode='" + alphacode + "'").getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
                returnList.add(ele.get(0));
                returnList.add(ele.get(1));
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getAlphacodeList() throws ApplicationException {
        List returnList = new ArrayList();
        try {
            List alpahaList = em.createNativeQuery("select distinct(alphacode) from branchmaster order by alphacode").getResultList();
            returnList = alpahaList;

        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List getAreaWiseList(String branch) throws ApplicationException {
        List returnList = new ArrayList();
        try {
            if (branch.equalsIgnoreCase("A")) {
                returnList = em.createNativeQuery("select distinct(area) from share_holder where area is not null order by area").getResultList();
            } else {
                returnList = em.createNativeQuery("select distinct(s.area) from share_holder s, branchmaster b where s.alphacode = b.alphacode and b.brncode = " + Integer.parseInt(branch) + " "
                        + "and s.area is not null order by area").getResultList();
            }
//            returnList = areaList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return returnList;
    }

    public List<AreaWiseSharePojo> getAreaWiseShareData(String brnCode, String area, String date) throws ApplicationException {
        List<AreaWiseSharePojo> dataList = new ArrayList<AreaWiseSharePojo>();
        try {
            // List result = em.createNativeQuery("select sh.Regfoliono,cmd.custname,cmd.fathername,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1 "
            //        + "from share_holder sh,cbs_customer_master_detail cmd where sh.custId = cmd.customerid and sh.area = '" + area + "'").getResultList();
            List result = new ArrayList();
            if (brnCode.equalsIgnoreCase("A")) {
                if (area.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select distinct si.foliono,cmd.CustFullName,concat(ifnull(cmd.fathername,''),' ',ifnull(cmd.FatherMiddleName,''),' ',ifnull(cmd.FatherLastName,'')) as FatherName,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1,sh.area  "
                            + "from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si where sh.custId = cmd.customerid and "
                            + "sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct si.foliono,cmd.CustFullName,concat(ifnull(cmd.fathername,''),' ',ifnull(cmd.FatherMiddleName,''),' ',ifnull(cmd.FatherLastName,'')) as FatherName,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1,sh.area  "
                            + "from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si where sh.area = '" + area + "' and sh.custId = cmd.customerid and "
                            + "sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
                }
            } else {
                if (area.equalsIgnoreCase("All")) {
                    result = em.createNativeQuery("select distinct si.foliono,cmd.CustFullName,concat(ifnull(cmd.fathername,''),' ',ifnull(cmd.FatherMiddleName,''),' ',ifnull(cmd.FatherLastName,'')) as FatherName,cmd.DateOfBirth,cmd.PerAddressLine1,"
                            + "cmd.MailAddressLine1, sh.area from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si,"
                            + "(select distinct(s.area) Area from share_holder s, branchmaster b where s.alphacode = b.alphacode "
                            + "and b.brncode = '" + brnCode + "' and s.area is not null order by area) brn where sh.area = brn.Area and sh.custId = cmd.customerid "
                            + "and sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share "
                            + "where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
                } else {
                    result = em.createNativeQuery("select distinct si.foliono,cmd.CustFullName,concat(ifnull(cmd.fathername,''),' ',ifnull(cmd.FatherMiddleName,''),' ',ifnull(cmd.FatherLastName,'')) as FatherName,cmd.DateOfBirth,cmd.PerAddressLine1,cmd.MailAddressLine1 ,sh.area "
                            + "from share_holder sh,cbs_customer_master_detail cmd,share_capital_issue si, "
                            + "(select distinct(s.area) Area from share_holder s, branchmaster b where s.alphacode = b.alphacode "
                            + "and b.brncode = '" + brnCode + "' and s.area is not null order by area) brn where sh.area = brn.Area and "
                            + "sh.area = '" + area + "' and sh.custId = cmd.customerid and "
                            + "sh.Regfoliono =si.foliono and si.sharecertno in (select certificateNo from  certificate_share where (paymentdt is null or paymentdt >='" + date + "'))").getResultList();
                }
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector ele = (Vector) result.get(i);
                    AreaWiseSharePojo pojo = new AreaWiseSharePojo();
                    pojo.setFolioNo(ele.get(0).toString());
                    pojo.setCustName(ele.get(1).toString());
                    if (ele.get(2) == null || ele.get(2).toString().equalsIgnoreCase("")) {
                        pojo.setFatherName("");
                    } else {
                        pojo.setFatherName(ele.get(2).toString());
                    }
                    String dob = ele.get(3).toString().substring(8, 10) + "/" + ele.get(3).toString().substring(5, 7) + "/" + ele.get(3).toString().substring(0, 4);

                    if (dob == null || dob.equalsIgnoreCase("") || dob.equalsIgnoreCase("01/01/1900")) {
                        pojo.setDob("");
                    } else {
                        pojo.setDob(dob);
                    }
                    pojo.setPermAdd(ele.get(4).toString());
                    pojo.setCorresspndAdd(ele.get(5).toString());
                    pojo.setArea(ele.get(6).toString());
                    dataList.add(pojo);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List<String> getShareCertificateData(String folioNo, String certficateNo, String brCode) throws ApplicationException {
        try {
            List<String> dataList = new ArrayList<String>();
            DecimalFormat numFormat = new DecimalFormat("#.00");
            Spellar obj = new Spellar();
            List list = em.createNativeQuery("select cc.customerid,regfoliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,perAddressLine1,ifnull(cc.PerCityCode,''),ifnull(PerStateCode,''),"
                    + "min(shareNo),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y'),ifnull(JtId1,''),ifnull(JtId2,''),ifnull(JtName1,''),ifnull(JtName2,'') "
                    + "from share_capital_issue sc,share_holder sh,"
                    + "certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                    + "and sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo and sc.ShareCertNo = " + Integer.parseInt(certficateNo)).getResultList();

            if (list.isEmpty()) {
                throw new ApplicationException("There is no data corressponding your details");
            }
            Vector ele = (Vector) list.get(0);
            //At 0th position - Customer id
            dataList.add(ele.get(0).toString());
            //At 1th position - folio no
            dataList.add(ele.get(1).toString());
            //At 2th position - name
            dataList.add(ele.get(2).toString());
            //At 3th position - father name
            dataList.add(ele.get(3).toString());
            //At 4th position - address
            dataList.add(ele.get(4).toString());
            //At 5th position - City
            dataList.add(common.getRefRecDesc("001", ele.get(5).toString()));
            //At 6th position - State
            dataList.add(common.getRefRecDesc("002", ele.get(6).toString()));
            //At 7th position - min share no
            dataList.add(ele.get(7).toString());
            //At 8th position - max share no 
            dataList.add(ele.get(8).toString());
            //At 9th position - total Number of share
            dataList.add(ele.get(9).toString());
            //At 10th position -  certificate Number
            dataList.add(CbsUtil.lPadding(4, Integer.parseInt(ele.get(10).toString())));
            //At 11th position -  issue date
            dataList.add(ele.get(11).toString());
            ///At 12th position Distinctive No.(s)
            dataList.add(CbsUtil.lPadding(4, Integer.parseInt(ele.get(7).toString())) + " to " + CbsUtil.lPadding(4, Integer.parseInt(ele.get(8).toString())));

            List resultList = remoteObject.getshareAmount(ymd.format(dmy.parse(ele.get(11).toString())));
            Vector element = (Vector) resultList.get(0);
            BigDecimal shareVal = new BigDecimal(element.get(0).toString());
            //At 13th position -  share Amount
            dataList.add(numFormat.format(shareVal.multiply(new BigDecimal(ele.get(9).toString()))));
            //At 14th position -  share Amount in word
            dataList.add(obj.spellAmount(shareVal.multiply(new BigDecimal(ele.get(9).toString())).doubleValue()));

            String jtName = ele.get(14).toString();
            if (!jtName.equalsIgnoreCase("")) {
                jtName = jtName + "/" + ele.get(15).toString();
            }

            list = em.createNativeQuery("select code from cbs_parameterinfo where name like 'SC_DF%'").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define the date format in Parameters");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector eleCode = (Vector) list.get(i);
                if (eleCode.get(0).toString().equalsIgnoreCase("ddo")) {
                    //At 14th position -  day of date
                    //String day = new SimpleDateFormat("dd").format(new Date());
                    String day = new SimpleDateFormat("dd").format(dmy.parse(ele.get(11).toString()));
                    //dataList.add(day + CbsUtil.getOrdinalSuffix(Integer.parseInt(day)));
                    String dayOfDate = day + CbsUtil.getOrdinalSuffix(Integer.parseInt(day));

                    String month = new SimpleDateFormat("MM").format(dmy.parse(ele.get(11).toString()));
                    String monthOfDate = CbsUtil.getMonthName(Integer.parseInt(month));
                    String yearOfDate = new SimpleDateFormat("yyyy").format(dmy.parse(ele.get(11).toString()));
                    //At 15th position -  day of date
                    dataList.add(dayOfDate);
                    //At 16th position -  month Year of date 
                    dataList.add(monthOfDate + " " + yearOfDate);
                } else {
                    //At 16th position -  month of date //At 16th position -  year of date
                    dataList.add(new SimpleDateFormat(eleCode.get(0).toString()).format(new Date()));
                }
            }

            list = em.createNativeQuery("select city from branchmaster where brncode = " + Integer.parseInt(brCode)).getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define the City in Branchmaster");
            }
            Vector eleBr = (Vector) list.get(0);
            //At 17th position -  Branch City

            dataList.add(eleBr.get(0).toString());
            //At 18th position -  Joint Name
            dataList.add(jtName);

            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }

    }

    public List<ShareCertificatePojo> getShareCertificateData1(String repType, String fromFolio, String toFolio, String frDt, String toDt, String folioNo, String certficateNo, String brCode, String fromCertNo, String toCertNo) throws ApplicationException {
        List<ShareCertificatePojo> dataList = new ArrayList<ShareCertificatePojo>();
        List list = new ArrayList();
        try {
            DecimalFormat numFormat = new DecimalFormat("#.00");
            Spellar obj = new Spellar();
            if (repType.equalsIgnoreCase("Folio Wise")) {
                list = em.createNativeQuery("select cc.customerid,regfoliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,perAddressLine1,ifnull(cc.PerCityCode,''),"
                        + "ifnull(PerStateCode,''),min(shareNo),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y'),ifnull(JtName1,''),ifnull(JtName2,'')  "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid "
                        + "and sc.foliono=sh.regfoliono and sh.regfoliono between '" + fromFolio + "' and '" + toFolio + "' and cs.certificateNo=sc.sharecertNo "
                        + "group by sc.foliono,sc.sharecertNo,custfullname order by sc.foliono").getResultList();
            } else if (repType.equalsIgnoreCase("Date Wise")) {
                list = em.createNativeQuery("select cc.customerid,regfoliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,perAddressLine1,ifnull(cc.PerCityCode,''),ifnull(PerStateCode,''),"
                        + "min(shareno),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y'),ifnull(JtName1,''),ifnull(JtName2,'')  from share_capital_issue sc,share_holder sh,"
                        + "certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo "
                        + "and cs.Issuedt between '" + frDt + "' and '" + toDt + "' /*and cs.Issuedt = sc.Issuedt*/ "
                        + "group by sc.foliono,sc.sharecertNo,custfullname order by sc.foliono").getResultList();
            } else if (repType.equalsIgnoreCase("Single Folio")) {
                list = em.createNativeQuery("select cc.customerid,regfoliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,perAddressLine1,ifnull(cc.PerCityCode,''),ifnull(PerStateCode,''),"
                        + "min(shareNo),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y'),ifnull(JtName1,''),ifnull(JtName2,'')  from share_capital_issue sc,share_holder sh,"
                        + "certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid and sc.foliono=sh.regfoliono "
                        + "and sh.regfoliono='" + folioNo + "' and cs.certificateNo=sc.sharecertNo and sc.ShareCertNo = " + Integer.parseInt(certficateNo)).getResultList();
            } else if (repType.equalsIgnoreCase("Certificate No. Wise")) {
                list = em.createNativeQuery("select cc.customerid,regfoliono,cc.custfullname,concat(ifnull(cc.fathername,''),' ',ifnull(cc.FatherMiddleName,''),' ',ifnull(cc.FatherLastName,'')) as FatherName,perAddressLine1,ifnull(cc.PerCityCode,''),"
                        + "ifnull(PerStateCode,''),min(shareNo),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y'),ifnull(JtName1,''),ifnull(JtName2,'')  "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc where sh.custid = cc.customerid "
                        + "and sc.foliono=sh.regfoliono and sc.sharecertno between '" + fromCertNo + "' and '" + toCertNo + "' and cs.certificateNo=sc.sharecertNo "
                        + "group by sc.foliono,sc.sharecertNo,custfullname order by sc.foliono").getResultList();
            }

            if (list.isEmpty()) {
                throw new ApplicationException("There is no data corressponding your details");
            }

//            int size = list.size();
//            if (size >= 25) {
//                size = 25;
//            } else {
//                size = size;
//            }
//            Vector tempVector = (Vector) em.createNativeQuery("select ifnull(shareAmt,0) from share_value").getResultList().get(0);
//            double shareValue = Double.parseDouble(tempVector.get(0).toString());
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                ShareCertificatePojo pojo = new ShareCertificatePojo();
                pojo.setRegFolioNo(ele.get(1).toString());
                pojo.setNameoFHolder(ele.get(2).toString());
                pojo.setBothIncluName(ele.get(2).toString());
                pojo.setFatherHusName(ele.get(3).toString());
                pojo.setAddress(ele.get(4).toString());
                pojo.setFromShare(CbsUtil.lPadding(4, Integer.parseInt(ele.get(7).toString())));
                pojo.setToShare(ele.get(8).toString());
                pojo.setNoOfShare(ele.get(9).toString());
                String shareNoInWord = obj.spellAmount(Double.parseDouble(ele.get(9).toString()));
                shareNoInWord = "(" + shareNoInWord.replaceFirst("Rupees ", "").toUpperCase() + ")";
                pojo.setToShareNoInWord(shareNoInWord);

                String jtName = ele.get(12).toString();
                if (!jtName.equalsIgnoreCase("")) {
                    jtName = jtName + "/" + ele.get(13).toString();
                }

                pojo.setCertificateNo(CbsUtil.lPadding(4, Integer.parseInt(ele.get(10).toString())));
                pojo.setDistinctiveNo(CbsUtil.lPadding(4, Integer.parseInt(ele.get(7).toString())) + " to " + ele.get(8).toString());
                List resultList = remoteObject.getshareAmount(ymd.format(dmy.parse(ele.get(11).toString())));
                Vector element = (Vector) resultList.get(0);
                BigDecimal shareVal = new BigDecimal(element.get(0).toString());
                pojo.setShareValue(nft.format(shareVal.doubleValue()));
                //At 12th position -  share Amount
                numFormat.format(shareVal.multiply(new BigDecimal(ele.get(9).toString())));
                //At 13th position -  share Amount in word
                obj.spellAmount(shareVal.multiply(new BigDecimal(ele.get(9).toString())).doubleValue());
                //System.out.print(ele.get(1).toString()+"===issuedt "+ele.get(11).toString());
                pojo.setShareAmt(numFormat.format(shareVal.multiply(new BigDecimal(ele.get(9).toString()))));
                List paramlist = em.createNativeQuery("select code from cbs_parameterinfo where name like 'SC_DF%'").getResultList();
                if (paramlist.isEmpty()) {
                    throw new ApplicationException("Please define the date format in Parameters");
                }
                String dateOfDay = "", monthOfDate = "", yearOfDate = "";
                // for (int j = 0; j < list.size(); j++) {
                Vector eleCode = (Vector) paramlist.get(0);
                if (eleCode.get(0).toString().equalsIgnoreCase("ddo")) {
                    //At 14th position -  day of date
                    String day = new SimpleDateFormat("dd").format(dmy.parse(ele.get(11).toString()));
                    dateOfDay = day + CbsUtil.getOrdinalSuffix(Integer.parseInt(day));
                    String month = new SimpleDateFormat("MM").format(dmy.parse(ele.get(11).toString()));
                    monthOfDate = CbsUtil.getMonthName(Integer.parseInt(month));
                    yearOfDate = new SimpleDateFormat("yyyy").format(dmy.parse(ele.get(11).toString()));
                } else {
                    //At 15th position -  month of date //At 16th position -  year of date
                    new SimpleDateFormat(eleCode.get(0).toString()).format(new Date());
                }
                //  }

                pojo.setDateOfDay(dateOfDay);
                pojo.setDateOfMonth(monthOfDate);
                pojo.setDateOfyy(yearOfDate);
                String occupation = "";
                List occuList = em.createNativeQuery("select description from codebook where groupcode=40 and code in(select OccupationCode from cbs_cust_misinfo where CustomerId = '" + ele.get(0).toString() + "')").getResultList();
                if (!occuList.isEmpty()) {
                    Vector vtr = (Vector) occuList.get(0);
                    occupation = vtr.get(0).toString();
                }
                pojo.setOccupation(occupation);
                pojo.setJoinName(jtName);
                dataList.add(pojo);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    public List<ShareDividendDetailPojo> getShareDividendDetail(String finYear, String repType, String area) throws ApplicationException {
        List<ShareDividendDetailPojo> dataList = new ArrayList<ShareDividendDetailPojo>();
        List result = new ArrayList();
        try {

            if (area.equalsIgnoreCase("ALL")) {
                result = em.createNativeQuery("select distinct acno,sum(Cramt) as dividendAmt,ifnull(sum(crAmt-drAmt),0) as balance from dividend_recon  where FYear = '" + finYear + "' group by acno ").getResultList();
            } else {
                result = em.createNativeQuery("select distinct acno,sum(Cramt) as dividendAmt,ifnull(sum(crAmt-drAmt),0) as balance from dividend_recon dr,share_holder sh"
                        + " where FYear = '" + finYear + "' and dr.ACNo = sh.Regfoliono and sh.area='" + area + "' group by acno").getResultList();
            }

            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);

                    String flioAcno = vtr.get(0).toString();
                    double bal = Double.parseDouble(vtr.get(2).toString());
                    ShareDividendDetailPojo pojo = new ShareDividendDetailPojo();
                    if (repType.equalsIgnoreCase("P") && bal == 0) {
                        pojo.setFolioNo(flioAcno);
                        List custList = em.createNativeQuery("select cc.custfullname from share_holder sh,cbs_customer_master_detail cc where sh.custid = cc.customerid  and regfoliono ='" + flioAcno + "'").getResultList();
                        Vector cvt = (Vector) custList.get(0);
                        pojo.setCustName(cvt.get(0).toString());
                        pojo.setDividendAmt(Double.parseDouble(vtr.get(1).toString()));
                        List paidList = em.createNativeQuery("select ifnull(sum(drAmt),0) from dividend_recon where FYear = '" + finYear + "' and ACNo = '" + flioAcno + "'").getResultList();
                        Vector pvt = (Vector) paidList.get(0);
                        pojo.setBalAmt(Double.parseDouble(pvt.get(0).toString()));
                        dataList.add(pojo);
                    } else if (repType.equalsIgnoreCase("B") && bal != 0) {
                        pojo.setFolioNo(flioAcno);
                        List custList = em.createNativeQuery("select cc.custfullname from share_holder sh,cbs_customer_master_detail cc where sh.custid = cc.customerid  and regfoliono ='" + flioAcno + "'").getResultList();
                        Vector cvt = (Vector) custList.get(0);
                        pojo.setCustName(cvt.get(0).toString());
                        pojo.setDividendAmt(Double.parseDouble(vtr.get(1).toString()));
                        pojo.setBalAmt(bal);
                        dataList.add(pojo);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return dataList;
    }

    public List fYearList() throws ApplicationException {
        List fyearList = new ArrayList();
        try {
            fyearList = em.createNativeQuery("select distinct(FYear) from dividend_recon").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fyearList;
    }

    public List getCertificateNoList(String folioNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select distinct ShareCertNo from share_capital_issue a,certificate_share b where a.FolioNo = '" + folioNo + "' "
                    + "and a.sharecertNo = b.certificateNo and b.Status = 'A' order by a.sharecertno").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAreaTypeList() throws ApplicationException {
        List areaList = new ArrayList();
        try {
            areaList = em.createNativeQuery("select distinct(area) from share_holder where area is not null order by area").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return areaList;

    }

    public List<MemberShareLetterPojo> getAdditionNewShareReport(String frDt, String toDt, String member, String orgnbrcode) throws ApplicationException {
        List<MemberShareLetterPojo> dataList = new ArrayList<MemberShareLetterPojo>();
        List arrayList = new ArrayList();
        try {

            List list = em.createNativeQuery("select distinct  bn.bankname,bn.bankaddress,bm.BranchName,pi.BrPhone,bm.Pincode from bnkadd bn,branchmaster bm, parameterinfo pi where bn.alphacode = bm.alphacode and"
                    + " bm.brncode = pi.brncode and bm.BrnCode = " + Integer.parseInt(orgnbrcode)
                    + " group by bn.bankname,bn.bankaddress ").getResultList();
            Vector tempVector1 = (Vector) list.get(0);
            String bankname = tempVector1.get(0).toString();
            String bankaddress = tempVector1.get(1).toString();
            String branchName = tempVector1.get(2).toString();
            String brPhone = tempVector1.get(3).toString();
            String pinCode = tempVector1.get(4).toString();
            if (member.equalsIgnoreCase("New")) {
                arrayList = em.createNativeQuery("select regfoliono,cc.custfullname,MailAddressLine1,concat(ifnull(cc.MailAddressLine2,''),' ',ifnull(cc.MailVillage,''),' ',ifnull(cc.MailBlock,'')),MailPostalCode, "
                        + "min(shareno),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y') ,sv.shareAmt,ifnull(cc.mailCityCode,'') "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc,share_value sv "
                        + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo "
                        + "and sh.regdate between '" + frDt + "' and '" + toDt + "' and effectivedt = (select max(effectivedt) from share_value where effectivedt <= '" + toDt + "') "
                        + "group by sc.foliono,sc.sharecertNo,custfullname order by sc.foliono").getResultList();
            } else {
                arrayList = em.createNativeQuery("select regfoliono,cc.custfullname,MailAddressLine1,concat(ifnull(cc.MailAddressLine2,''),' ',ifnull(cc.MailVillage,''),' ',ifnull(cc.MailBlock,'')),MailPostalCode, "
                        + "min(shareno),max(shareno),count(shareno) ,sharecertno,date_format(cs.Issuedt,'%d/%m/%Y') ,sv.shareAmt,ifnull(cc.mailCityCode,'') "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc,share_value sv  "
                        + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo  "
                        + "and sc.dt between '" + frDt + "' and '" + toDt + "' and effectivedt = (select max(effectivedt) from share_value where effectivedt <= '" + toDt + "') "
                        + "group by sc.foliono,sc.sharecertNo,custfullname order by sc.foliono").getResultList();
            }
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    Vector vtr = (Vector) arrayList.get(i);

                    MemberShareLetterPojo pojo = new MemberShareLetterPojo();
                    pojo.setBankName(bankname);
                    pojo.setBankAddress(bankaddress);
                    pojo.setBranchName(branchName);
                    pojo.setPhoneNo(brPhone);
                    pojo.setPinCode(pinCode);
                    pojo.setCurDate(dmy.format(new Date()));
                    pojo.setRegFolioNo(vtr.get(0).toString());
                    pojo.setNameoFHolder(vtr.get(1).toString());
                    pojo.setAddress(vtr.get(2).toString());
                    pojo.setCityVillage(vtr.get(3).toString());
                    //pojo.setPerStateCode(vtr.get(4).toString());
                    pojo.setPerStateCode(vtr.get(11).toString().trim().equalsIgnoreCase("") ? "" : refDesc("001", vtr.get(11).toString().trim()) + " : " + vtr.get(4).toString().trim());
                    pojo.setFromNo(CbsUtil.lPadding(4, Integer.parseInt(vtr.get(5).toString())));
                    pojo.setToNO(vtr.get(6).toString());
                    pojo.setNoOfShare((vtr.get(7).toString()));
                    pojo.setIssueDt(vtr.get(9).toString());
                    pojo.setShareAmt(vtr.get(10).toString());
                    dataList.add(pojo);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e);
        }
        return dataList;
    }

    public List<MemberLabelPojo> getMemberLabelReportData(String fromDate, String toDate, String status, String custEntityType, String nomDtl) throws ApplicationException {
        List<MemberLabelPojo> resultData = new ArrayList<MemberLabelPojo>();
        List result = new ArrayList();
        String entityType = "";
        String nomType = "";
        String statusQuery = "";
        try {
            if (custEntityType.equalsIgnoreCase("All")) {
                entityType = "";
            } else {
                entityType = "and  cc.custEntityType= '" + custEntityType + "'";
            }
//            if (nomDtl.equalsIgnoreCase("All")) {
//                nomType = "";
//            } else if (nomDtl.equalsIgnoreCase("W")) {
//                nomType = "and  (sh.nomname !='' and sh.nomname is not null)";
//            } else {
//                nomType = "and (sh.nomname ='' or sh.nomname is null)";
//            }

            if (status.equalsIgnoreCase("A")) {
                statusQuery = "and (cs.paymentdt is null or cs.paymentdt ='' or cs.paymentdt > '" + toDate + "') ";
            } else if (status.equalsIgnoreCase("C")) {
                statusQuery = "and cs.paymentdt between '" + fromDate + "' and '" + toDate + "' ";
            } else {
                statusQuery = "";
            }

            if (status.equalsIgnoreCase("All")) {
                result = em.createNativeQuery("select sh.regfoliono,concat(ifnull(cc.title,''),' ',ifnull(cc.custname,''),' ',ifnull(cc.middle_name,''),' ',ifnull(cc.last_name,'')) as custname,cc.MailAddressLine1,concat(ifnull(cc.MailAddressLine2,''),' ',ifnull(cc.MailVillage,''),' ',ifnull(cc.MailBlock,'')),cc.MailPostalCode,cc.customerid,cc.custEntityType,ifnull(sh.nomname,''),ifnull(cc.mailCityCode,'') "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                        + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo "
                        + "and cs.Issuedt between '" + fromDate + "' and '" + toDate + "' " + statusQuery + "" + entityType + " " + nomType + " "
                        + "group by sc.foliono order by sc.foliono").getResultList();
            } else if (status.equalsIgnoreCase("A")) {
                result = em.createNativeQuery("select sh.regfoliono,concat(ifnull(cc.title,''),' ',ifnull(cc.custname,''),' ',ifnull(cc.middle_name,''),' ',ifnull(cc.last_name,'')) as custname,cc.MailAddressLine1,concat(ifnull(cc.MailAddressLine2,''),' ',ifnull(cc.MailVillage,''),' ',ifnull(cc.MailBlock,'')),cc.MailPostalCode,cc.customerid,cc.custEntityType,ifnull(sh.nomname,''),ifnull(cc.mailCityCode,'') "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                        + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo "
                        + "and cs.Issuedt between '" + fromDate + "' and '" + toDate + "' " + statusQuery + " " + entityType + " " + nomType + " "
                        + "group by sc.foliono order by sc.foliono").getResultList();
            } else if (status.equalsIgnoreCase("C")) {
                result = em.createNativeQuery("select sh.regfoliono,concat(ifnull(cc.title,''),' ',ifnull(cc.custname,''),' ',ifnull(cc.middle_name,''),' ',ifnull(cc.last_name,'')) as custname,cc.MailAddressLine1,concat(ifnull(cc.MailAddressLine2,''),' ',ifnull(cc.MailVillage,''),' ',ifnull(cc.MailBlock,'')),cc.MailPostalCode,cc.customerid,cc.custEntityType,ifnull(sh.nomname,''),ifnull(cc.mailCityCode,'') "
                        + "from share_capital_issue sc,share_holder sh,certificate_share cs, cbs_customer_master_detail cc "
                        + "where sh.custid = cc.customerid and sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo "
                        + " " + statusQuery + " " + entityType + " " + nomType + " "
                        + "group by sc.foliono order by sc.foliono").getResultList();
            }
            if (!result.isEmpty()) {
                for (int i = 0; i < result.size(); i++) {
                    Vector vtr = (Vector) result.get(i);
                    MemberLabelPojo pojo = new MemberLabelPojo();
                    pojo.setRegFoliono(vtr.get(0).toString().trim());
                    pojo.setCustomerName(vtr.get(1).toString().trim());
                    pojo.setPerAddressLine1(vtr.get(2).toString().trim());
                    pojo.setPerAddressLine2(vtr.get(3).toString().trim());
                    pojo.setPerPostalcode(vtr.get(8).toString().trim().equalsIgnoreCase("") ? "" : refDesc("001", vtr.get(8).toString().trim()) + " - " + vtr.get(4).toString().trim());
                    pojo.setNomName(vtr.get(7).toString().trim().equalsIgnoreCase("") ? "" : "Nom : " + vtr.get(7).toString().trim());
                    resultData.add(pojo);
                }
            }
        } catch (Exception ex) {

            throw new ApplicationException(ex.getMessage());
        }
        return resultData;
    }

    public List<ShareMemberDetailPojo> getMemberDetailsReport(String memberType, String memberNo, String accountCatageory, String asOnDate) throws ApplicationException {
        List<ShareMemberDetailPojo> resultData = new ArrayList<ShareMemberDetailPojo>();
        List result = new ArrayList();

        try {
            int isExceessEmi = common.isExceessEmi(asOnDate);
            List shList = em.createNativeQuery("select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= '" + asOnDate + "')").getResultList();
            Vector shVector = (Vector) shList.get(0);
            double shareValue = Double.parseDouble(shVector.get(0).toString());

            if (memberType.equalsIgnoreCase("All")) {
                result = em.createNativeQuery("select bb.custid,aa.custfullname,bb.Regfoliono,cc.TshareNo,cc.memberDt,bb.BeneficiaryAccNo,bb.bal from cbs_customer_master_detail aa,\n"
                        + "(select b.custid,a.Regfoliono,a.BeneficiaryAccNo, (select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon rr where rr.acno = a.BeneficiaryAccNo and rr.dt<='" + asOnDate + "') as bal "
                        + "from share_holder a,customerid b,accounttypemaster c,accountmaster d where cast(a.custid as unsigned) = b.custId and b.acno= d.acno \n"
                        + "and c.acctcode = d.accttype /*and c.acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D')*/ \n"
                        + "/*and (closingdate is null or closingdate ='' or closingdate >= '" + asOnDate + "')*/ group by b.custId) bb,"
                        + "(select sh.custid,sc.foliono,count(shareno) TshareNo,date_format(Regdate,'%d/%m/%Y') memberDt from share_capital_issue sc,share_holder sh,\n"
                        + "certificate_share cs where sc.foliono=sh.regfoliono and cs.certificateNo=sc.sharecertNo and cs.status = 'A' and cs.Issuedt<='" + asOnDate + "' group by sc.foliono)cc\n"
                        + "where cast(aa.customerid as unsigned) = bb.custid and bb.custid = cc.custid\n"
                        + "and aa.CustEntityType = '" + accountCatageory + "'").getResultList();
            } else {
                result = em.createNativeQuery("select bb.custid,aa.custfullname,bb.Regfoliono,cc.TshareNo,cc.memberDt,bb.BeneficiaryAccNo,bb.bal from cbs_customer_master_detail aa,\n"
                        + "(select b.custid,a.Regfoliono,a.BeneficiaryAccNo, (select cast(ifnull(sum(cramt-dramt),0) as decimal(25,2)) from recon rr where rr.acno = a.BeneficiaryAccNo and rr.dt<='" + asOnDate + "') as bal "
                        + "from share_holder a,customerid b,accounttypemaster c,accountmaster d where cast(a.custid as unsigned) = b.custId and b.acno= d.acno \n"
                        + "and c.acctcode = d.accttype /*and c.acctnature in('" + CbsConstant.TERM_LOAN + "','" + CbsConstant.DEMAND_LOAN + "','" + CbsConstant.CURRENT_AC + "') and crdbflag in ('B','D')*/ \n"
                        + "/*and (closingdate is null or closingdate ='' or closingdate >= '" + asOnDate + "')*/ group by b.custId) bb,(select sh.custid,sc.foliono,count(shareno) TshareNo,date_format(Regdate,'%d/%m/%Y') memberDt from share_capital_issue sc,share_holder sh,\n"
                        + "certificate_share cs where sc.foliono=sh.regfoliono \n"
                        + "and cs.certificateNo=sc.sharecertNo and cs.status = 'A' and cs.Issuedt<='" + asOnDate + "' group by sc.foliono)cc\n"
                        + "where cast(aa.customerid as unsigned) = bb.custid and bb.custid = cc.custid\n"
                        + "and bb.Regfoliono = '" + memberNo + "'").getResultList();
            }
            if (!result.isEmpty()) {
                for (int j = 0; j < result.size(); j++) {
                    Vector vtrmain = (Vector) result.get(j);
                    String custId = vtrmain.get(0).toString();
                    String custName = vtrmain.get(1).toString();
                    String foiliNo = vtrmain.get(2).toString();
                    String shareNo = vtrmain.get(3).toString();
                    String memberDt = vtrmain.get(4).toString();
                    String csdBal = vtrmain.get(6).toString();
                    // for staus
                    String status = "";
                    List list1 = em.createNativeQuery("Select c.description,a.acno from accountstatus a,codebook c where acno in(select acno from customerid where custid = '" + custId + "')\n"
                            + "and effdt=(Select max(Effdt) from accountstatus where date_format(EffDt,'%Y%m%d')<='" + asOnDate + "' and acno in(select acno from customerid where custid = '" + custId + "')\n"
                            + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) \n"
                            + "and spno=(Select max(Spno) from accountstatus where acno in(select acno from customerid where custid = '" + custId + "') and date_format(EffDt,'%Y%m%d')<='" + asOnDate + "' \n"
                            + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))) \n"
                            + "and spno=(Select max(Spno) from accountstatus where acno in(select acno from customerid where custid = '" + custId + "') and date_format(EffDt,'%Y%m%d')<='" + asOnDate + "' and (spflag in (11,12,13,14,3,6,7,8,2) or \n"
                            + "(spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')))\n"
                            + "and (spflag in (11,12,13,14,3,6,7,8,2) or (spflag=1 and remark like 'Standard%') or (spflag=1 and remark like 'Operative%')) \n"
                            + "AND AUTH='Y' and a.spflag = c.code and c.groupcode = 3 group by a.acno").getResultList();
                    if (list1.isEmpty()) {
                        status = "STANDARD";
                    } else {
                        Vector element = (Vector) list1.get(0);
                        status = element.get(0).toString();
                        status = status.toUpperCase();
                    }
                    // End for staus

                    List list = em.createNativeQuery("select 'DL'as GRP, aa.custid,aa.acno,cast(bal as decimal(25,2)),cast(aa.sanctionlimit as decimal(25,2)),date_format(aa.sanctionlimitdt,'%d/%m/%Y'),bb.installamt,'',cast(cc.TdlBal as decimal(25,2)) from (\n"
                            + "(select b.custid,a.acno,sum(cramt-dramt) bal,c.sanctionlimit,c.sanctionlimitdt from loan_recon a,customerid b,loan_appparameter c where a.acno = b.acno and a.acno = c.acno \n"
                            + "and b.custid = '" + custId + "' and a.dt<='" + asOnDate + "' group by a.acno having(bal<>0))aa,\n"
                            + "(select a.acno,a.installamt from emidetails a,customerid b where  a.acno = b.acno  and b.custid = '" + custId + "'group by acno)bb,"
                            + "(select cast(sum(cramt-dramt) as decimal(25,2)) TdlBal from loan_recon a,customerid b where a.acno = b.acno and b.custid = '" + custId + "' and a.dt<='" + asOnDate + "')cc\n"
                            + ") where aa.acno = bb.acno\n"
                            + "union \n"
                            + "select 'IL'as GRP,aa.custid,aa.acno,cast(bal as decimal(25,2)),cast(aa.sanctionlimit as decimal(25,2)),date_format(aa.sanctionlimitdt,'%d/%m/%Y'),bb.installamt,aa.custname,cast(cc.TilBal as decimal(25,2)) from (\n"
                            + "(select b.custid,a.acno,sum(cramt-dramt) bal,c.sanctionlimit,c.sanctionlimitdt,d.custname from loan_recon a,customerid b,loan_appparameter c,"
                            + "accountmaster d,loan_guarantordetails e where a.acno = b.acno and a.acno = c.acno and a.acno = d.acno and a.acno = e.acno "
                            + "and e.GAR_CUSTID = '" + custId + "'and a.dt<='" + asOnDate + "' group by a.acno having(bal<>0))aa,\n"
                            + "(select a.acno,a.installamt from emidetails a,customerid b,loan_guarantordetails c "
                            + "where  a.acno = b.acno  and a.acno = c.acno and c.GAR_CUSTID = '" + custId + "' group by acno)bb,(select cast(sum(cramt-dramt) as decimal(25,2)) TilBal from loan_recon a,customerid b,accountmaster d,loan_guarantordetails e \n"
                            + "where a.acno = b.acno and a.acno = d.acno and a.acno = e.acno and e.GAR_CUSTID = '" + custId + "'and a.dt<='" + asOnDate + "')cc) where aa.acno = bb.acno "
                            + "union \n"
                            + "select 'DL'as GRP, aa.custid,aa.acno,cast(bal as decimal(25,2)),cast(aa.sanctionlimit as decimal(25,2)),date_format(aa.sanctionlimitdt,'%d/%m/%Y'),'0' as installment,'',\n"
                            + "cast(cc.TdlBal as decimal(25,2)) from \n"
                            + "((select b.custid,a.acno,sum(cramt-dramt) bal,c.sanctionlimit,c.sanctionlimitdt from ca_recon a,customerid b,loan_appparameter c where a.acno = b.acno and a.acno = c.acno \n"
                            + "and b.custid = '" + custId + "' and a.dt<='" + asOnDate + "' group by a.acno having(bal<>0))aa,\n"
                            + "(select cast(sum(cramt-dramt) as decimal(25,2)) TdlBal from ca_recon a,customerid b where a.acno = b.acno and b.custid = '" + custId + "' and a.dt<='" + asOnDate + "')cc) \n"
                            + "union \n"
                            + "select 'IL'as GRP,aa.custid,aa.acno,cast(bal as decimal(25,2)),cast(aa.sanctionlimit as decimal(25,2)),date_format(aa.sanctionlimitdt,'%d/%m/%Y'),'0' as installment,aa.custname,cast(cc.TilBal as decimal(25,2)) from (\n"
                            + "(select b.custid,a.acno,sum(cramt-dramt) bal,c.sanctionlimit,c.sanctionlimitdt,d.custname from ca_recon a,customerid b,loan_appparameter c,\n"
                            + "accountmaster d,loan_guarantordetails e where a.acno = b.acno and a.acno = c.acno and a.acno = d.acno and a.acno = e.acno \n"
                            + "and e.GAR_CUSTID = '" + custId + "'and a.dt<='" + asOnDate + "' group by a.acno having(bal<>0))aa,\n"
                            + "(select cast(sum(cramt-dramt) as decimal(25,2)) TilBal from ca_recon a,customerid b,accountmaster d,loan_guarantordetails e \n"
                            + "where a.acno = b.acno and a.acno = d.acno and a.acno = e.acno and e.GAR_CUSTID = '" + custId + "'and a.dt<='" + asOnDate + "')cc) ").getResultList();
                    if (!list.isEmpty()) {

                        for (int i = 0; i < list.size(); i++) {
                            Vector vtr = (Vector) list.get(i);
                            String type = vtr.get(0).toString();
                            String acNoCId = vtr.get(1).toString();
                            String acNo = vtr.get(2).toString();
                            String bal = vtr.get(3).toString();
                            String sancAmt = vtr.get(4).toString();
                            String sancDt = vtr.get(5).toString();
                            String installAmt = vtr.get(6).toString();
                            String loanee = vtr.get(7).toString();
                            double dlilBal = Math.abs(Double.parseDouble(vtr.get(8).toString()));

                            ShareMemberDetailPojo pojo = new ShareMemberDetailPojo();
                            pojo.setCustId(custId);
                            pojo.setMemberName(custName);
                            pojo.setMembershipNo(foiliNo);
                            pojo.setMembershipDate(memberDt);
                            double shareBal = Integer.parseInt(shareNo) * shareValue;
                            pojo.setShareAcBalance(String.valueOf(shareBal)); //
                            pojo.setCsdAcBalance(csdBal);
                            pojo.setType(type);
                            pojo.setBranchCode(acNo.substring(0, 2));
                            pojo.setAcNo(acNo);
                            pojo.setAcNoCId(acNoCId);
                            pojo.setSanctionLimit(sancAmt);
                            pojo.setSanctionLimitdt(sancDt);
                            pojo.setInstallmentAmount(installAmt);
                            pojo.setLoaneeName(loanee);
                            pojo.setOsBalance(new BigDecimal(bal).abs());
                            pojo.setDlilBal(new BigDecimal(dlilBal));
                            pojo.setMemberStatus(status);
                            pojo.setOverdueAmount(getOverDueByAcNo(acNo, asOnDate, Double.parseDouble(bal), isExceessEmi, 1, 100));
                            resultData.add(pojo);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());

        }
        return resultData;
    }

    public double getOverDueByAcNo(String acNo, String dt, double balance, int isExceessEmi, int emiPendingfrom, int emiPendingto) throws ApplicationException {
        int IdealInstPaid = 0, ActualInstPaid = 0, installmentoverdue = 0;
        double excess = 0d, amountOverd = 0d, amountOverdue = 0;
        try {
//            if (acNo.equalsIgnoreCase("021201063401")) {
//                System.out.println("");
//            }
            List list = em.createNativeQuery("select a.IdealInstPaid,b.ActualInstPaid from (\n"
                    + "(select count(1)IdealInstPaid from emidetails where acno = '" + acNo + "' and duedt <= '" + dt + "')a,\n"
                    + "(select count(1)ActualInstPaid from emidetails where acno = '" + acNo + "' and paymentdt <= '" + dt + "')b/*,\n"
                    + "(Select ifnull(e.excessamt,0) excess from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = (select max(duedt) from emidetails where paymentdt <= '" + dt + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1)c*/\n"
                    + ")").getResultList();
            Vector ele = (Vector) list.get(0);
            IdealInstPaid = Integer.parseInt(ele.get(0).toString());
            ActualInstPaid = Integer.parseInt(ele.get(1).toString());
            // excess = Double.parseDouble(ele.get(2).toString());
            List tempList = new ArrayList();
            if (isExceessEmi == 0) {
                tempList = em.createNativeQuery("Select ifnull(e.excessamt,0) from emidetails e where e.sno in (select  sno+1 from emidetails where acno = '" + acNo + "' and duedt = ( select  max(duedt) from emidetails where paymentdt <= '" + dt + "'  and acno = '" + acNo + "')) and e.acno ='" + acNo + "' limit 1").getResultList();
            } else {
                tempList = em.createNativeQuery("select ifnull(sum(excessamt),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and txnid in\n"
                        + "(select ifnull(max(txnid),0) from cbs_loan_emi_excess_details where acno = '" + acNo + "' and dt <='" + dt + "')").getResultList();
            }
            if (!tempList.isEmpty()) {
                if (!tempList.isEmpty()) {
                    ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        excess = Double.parseDouble(ele.get(0).toString());
                    }
                }
            }
            if (IdealInstPaid >= ActualInstPaid) {
                tempList = em.createNativeQuery("select ifnull(sum(ifnull(installamt,0)),0) from emidetails where acno = '" + acNo + "' and sno <='" + IdealInstPaid + "' and sno>'" + ActualInstPaid + "'").getResultList();
                if (!tempList.isEmpty()) {
                    ele = (Vector) tempList.get(0);
                    if (ele.get(0) != null || !ele.get(0).toString().equalsIgnoreCase("")) {
                        amountOverd = Double.parseDouble(ele.get(0).toString());
                    } else {
                        amountOverd = 0;
                    }
                }
                installmentoverdue = IdealInstPaid - ActualInstPaid;
            } else {
                installmentoverdue = 0;
            }
            if ((amountOverd - excess) > Math.abs(balance)) {
                amountOverdue = Math.abs(balance);
            } else if (balance > 0) {
                amountOverdue = 0;
            } else if (balance < 0 && amountOverd == 0) {
                amountOverdue = Math.abs(balance);
            } else {
                amountOverdue = amountOverd - excess;
            }
            boolean isTrue = false;
            if (installmentoverdue >= emiPendingfrom && installmentoverdue <= emiPendingto) {
                isTrue = true;
            }
            if (isTrue) {
                amountOverdue = amountOverdue;
            } else {
                amountOverdue = 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex.getMessage());
        }
        return amountOverdue;
    }

    public String refDesc(String refNo, String refCode) throws ApplicationException {
        try {
            String desc = "";
            List list = em.createNativeQuery("select REF_DESC from cbs_ref_rec_type where REF_REC_NO = '" + refNo + "' and REF_CODE = '" + refCode + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                desc = vtr.get(0).toString();
            }
            return desc;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<ShareHoldersPojo> getRetireShareMemberData(String orgnbrcode, int retireAge, int retirePeriod, String asOnDt) throws ApplicationException {
        List<ShareHoldersPojo> dataList = new ArrayList<>();
        try {
            String branch = "";
            if (!orgnbrcode.equalsIgnoreCase("0A")) {
                branch = "and primarybrcode = '" + orgnbrcode + "'";
            }
            List list = em.createNativeQuery("select b.customerid,a.Regfoliono,b.custfullname,concat(ifnull(b.fathername,''),' ',ifnull(b.FatherMiddleName,''),' ',ifnull(b.FatherLastName,'')) as FatherName, "
                    + "trim(concat(trim(ifnull(b.PerAddressLine1,'')),',',trim(ifnull(b.perAddressLine2,'')),',',trim(ifnull(b.PerVillage,'')),',',trim(ifnull(b.PerBlock,'')))) as Address, "
                    + "date_format(DateOfBirth,'%Y%m%d') as dob," + retireAge + " 'sriznage',"
                    + "date_format(DATE_ADD(DateOfBirth, INTERVAL " + retireAge + " year),'%Y%m%d') as retireDate, "
                    + "" + asOnDt + " 'AsOnDate'," + retirePeriod + " 'Days',"
                    + "date_format(DATE_ADD('" + asOnDt + "', INTERVAL " + retirePeriod + " DAY),'%Y%m%d') as asOnDt_day "
                    + "from share_holder a,cbs_customer_master_detail b where a.custid = b.customerid "
                    + "and date_format(DATE_ADD(DateOfBirth, INTERVAL " + retireAge + " year),'%Y%m%d') >= '" + asOnDt + "' "
                    + "and date_format(DATE_ADD(DateOfBirth, INTERVAL " + retireAge + " year),'%Y%m%d')<= date_format(DATE_ADD('" + asOnDt + "', INTERVAL " + retirePeriod + " DAY),'%Y%m%d') "
                    + "" + branch + "").getResultList();

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Vector vtr = (Vector) list.get(i);
                    ShareHoldersPojo pojo = new ShareHoldersPojo();
                    pojo.setCustId(vtr.get(0).toString());
                    pojo.setFolioNo(vtr.get(1).toString());
                    pojo.setShName(vtr.get(2).toString());
                    pojo.setFhName(vtr.get(3).toString());
                    pojo.setAddress(vtr.get(4).toString());
                    pojo.setDob(dmy.format(ymd.parse(vtr.get(5).toString())));
                    pojo.setRetirementAge(Integer.parseInt(vtr.get(6).toString()));
                    pojo.setRetirementdate(dmy.format(ymd.parse(vtr.get(7).toString())));
                    pojo.setRetireInDays(Integer.parseInt(vtr.get(9).toString()));
                    dataList.add(pojo);
                }
            }
            return dataList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }
}
