package com.cbs.facade.ho.share;

import com.cbs.constant.AccountStatusEnum;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.constant.CbsConstant;
import com.cbs.dao.neftrtgs.ElectronicPaymentSystemDAO;
import com.cbs.dto.DividendReconTable;
import com.cbs.dto.DividendReconTempTable;
import com.cbs.dto.DividendTable;
import com.cbs.dto.sms.SmsToBatchTo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.neftrtgs.UploadNeftRtgsMgmtFacadeRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.sms.service.SmsType;
import com.cbs.utils.CbsUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author Navneet Goyal
 */
@Stateless(mappedName = "DividendCalculationFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class DividendCalculationFacade implements DividendCalculationFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    private SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat dmyFormat = new SimpleDateFormat("dd/MM/yyyy");
    SharePara sharePara = new SharePara();
    String gCode = "", gAuthFlag = "";
    String alphaCode, bname;
    @EJB
    InterBranchTxnFacadeRemote ibtFacade;
    @EJB
    FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    UploadNeftRtgsMgmtFacadeRemote neftFacade;
    @EJB
    private SmsManagementFacadeRemote smsFacade;

    public Double getShareValue() throws ApplicationException {
        Double shareValue = null;
        try {
            Query selectQuery = em.createNativeQuery("select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <='" + ymdFormat.format(new Date()) + "')");
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                if (ele != null) {
                    shareValue = Double.parseDouble(ele.get(0).toString());
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return shareValue;
    }

    public List getAlphaCodeList() throws ApplicationException {
        List list = null;
        try {
            Query selectQuery = em.createNativeQuery("Select distinct(alphacode) from branchmaster order by alphacode");
            list = selectQuery.getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return list;
    }

    public List getFinancialYear() throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("Select F_Year,CONCAT(substring(mindate,1,4),'-',substring(maxdate,1,4) ) from cbs_yearend");
            List list = selectQuery.getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Data does not exist in year end table");
            }
            return list;

        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public Vector getBranchNameandAddress(String orgnbrcode) throws ApplicationException {
        Vector ele = null;
        try {
            Query selectQuery = em.createNativeQuery("SELECT b.bankname,b.bankaddress FROM bnkadd b,branchmaster br WHERE b.alphacode=br.alphacode and br.brncode=cast('" + orgnbrcode + "' AS unsigned)");
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                ele = (Vector) list.get(0);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return ele;
    }

    public String getHOAddress() throws ApplicationException {
        try {
            Query selectQuery = em.createNativeQuery("select address from branchmaster where AlphaCode='HO'");
            List list = selectQuery.getResultList();
            if (!list.isEmpty()) {
                Vector ele = (Vector) list.get(0);
                if (ele.get(0) != null) {
                    return ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return null;
    }

    private String globalData() throws ApplicationException {
        try {
            gAuthFlag = "Y";
            List shareParameterList = em.createNativeQuery("Select cal_type,full_int,half_int From share_parameter").getResultList();
            if (!shareParameterList.isEmpty()) {
                Vector ele = (Vector) shareParameterList.get(0);
                if (ele.get(0) != null) {
                    sharePara.CalType = ele.get(0).toString();
                }
                if (ele.get(1) != null) {
                    sharePara.fullInt = ele.get(1).toString();
                }
                if (ele.get(2) != null) {
                    sharePara.halfInt = ele.get(2).toString();
                }
            } else {
                return "Please Fill The Values In Table Share_Parameter";
            }
            List lockDateParameterList = em.createNativeQuery("Select code FROM parameterinfo_report where reportname ='LOCKDATE'").getResultList();
            if (!lockDateParameterList.isEmpty()) {
                Vector ele = (Vector) lockDateParameterList.get(0);
                if (ele.get(0) != null) {
                    gCode = ele.get(0).toString();
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "True";
    }

    public List<DividendTable> calculateDividend(String alphaCode, Double shareAmount, Double dividend, Integer fYear, Date postDate, String Auth1, String brCode, String custType) throws ApplicationException {
        this.alphaCode = alphaCode;
        List<DividendTable> resultList = new ArrayList<DividendTable>();
        try {
            String msg = globalData();
            if (!msg.equalsIgnoreCase("True")) {
                throw new ApplicationException(msg);
            }
            if (sharePara.CalType.equalsIgnoreCase("CCBL") && fYear <= 2013) {
                resultList = dividendCalculationCCBL(alphaCode, shareAmount, dividend, fYear, postDate, brCode);
            } else {
                resultList = generalDividendCalculation(alphaCode, dividend, fYear, postDate, brCode, custType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return resultList;
    }

    private List generalDividendCalculation(String alphaCode, Double dividend, Integer fyear, Date postDate, String brCode, String custType) throws ApplicationException {
        List<DividendTable> DividendTableresultList = new ArrayList<DividendTable>();
        try {
            Double amount = 0.0, divTotal = 0.0;
            String details = "", status = "", dataQuery = "";
            Integer nextYear = fyear + 1;
            String toDt = nextYear + "0331", fromDt = fyear + sharePara.fullInt.trim();
            
            String custTypeQuery = "";
            if(!custType.equals("00")) custTypeQuery=" and custEntityType ='" + custType + "' ";
            
            if (alphaCode.equalsIgnoreCase("All")) {
                dataQuery = "Select count(sc.shareNo),sc.FolioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,sh.purpose,cc.CustFullName,"
                        + "sh.alphacode,cs.status,DATE_FORMAT(cs.paymentdt,'%Y%m%d'),ifnull(cc.PerAddressLine1,''),IFNULL(sh.beneficiaryAccNo,''),"
                        + "IFNULL(sh.beneficiaryName,''),IFNULL(sh.ifsccode,''),IFNULL(sh.BankName,''), "
                        + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                        + "From share_capital_issue sc, share_holder sh, "
                        + "certificate_share cs,cbs_customer_master_detail cc  Where sh.regfolioNo=sc.FolioNo and Dt<='" + toDt + "' and sc.ShareCertNo <> 0 "
                        + "AND sc.FolioNo <> '" + brCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001' and sc.shareCertNo = cs.certificateno and "
                      //  + "sc.FolioNo = '901401440001' AND " 
                        + "(paymentdt > '" + fromDt + "' or paymentdt is null or paymentdt ='19000101') and cc.customerid = sh.custid "+custTypeQuery
                        + "Group by sc.folioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,"
                        + "cc.PerAddressLine1,sh.purpose,cc.custname,sh.alphacode,cs.paymentdt,cs.status,sc.dt order by sc.FolioNo,sc.shareCertNo";
            } else {
                dataQuery = "Select count(sc.shareNo),sc.FolioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,sh.purpose,cc.CustFullName,"
                        + "sh.alphacode,cs.status,DATE_FORMAT(cs.paymentdt,'%Y%m%d'),ifnull(cc.PerAddressLine1,''),IFNULL(sh.beneficiaryAccNo,''),"
                        + "IFNULL(sh.beneficiaryName,''),IFNULL(sh.ifsccode,''),IFNULL(sh.BankName,''), "
                        + "(select shareAmt from share_value where effectivedt = (select max(effectivedt) from share_value where effectivedt <= cs.CertIssueDt)) as shVal "
                        + "From share_capital_issue sc, share_holder sh, "
                        + "certificate_share cs ,cbs_customer_master_detail cc Where sh.regfolioNo=sc.FolioNo and sh.alphacode='" + alphaCode + "' and Dt<='" + toDt
                        + "' and sc.ShareCertNo <> 0 AND sc.FolioNo <> '" + brCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001' and sc.shareCertNo = cs.certificateno and "
                        + "(paymentdt > '" + fromDt + "' or paymentdt is null or paymentdt ='19000101') and cc.customerid = sh.custid "+custTypeQuery
                        + "Group by sc.folioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno, sh.purpose,cc.custname,sh.alphacode,"
                        + "cs.paymentdt,cc.PerAddressLine1,cs.status,sc.dt order by sc.FolioNo,sc.shareCertNo";
            }

            System.out.println("Query Execution Started");
            Query selectData = em.createNativeQuery(dataQuery);
            List result = selectData.getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("Share folio data does not exist");
            }
            Date preLastDt = ymdFormat.parse(fyear + "0331");
            Date halfDt = ymdFormat.parse(fyear + sharePara.halfInt.trim());
            for (int i = 0; i < result.size(); i++) {
                DividendTable currentRow = new DividendTable();
                Vector vec = (Vector) result.get(i);
                ShareCapitalIssueShareHolder shareHolderDetails = getShareCapitalIssueShareHolder(vec);
                String certStatus = "";
                if (vec.get(8) != null) {
                    certStatus = vec.get(8).toString();
                }
                amount = shareHolderDetails.countScShareNo * shareHolderDetails.shareValue;
                details = shareHolderDetails.scDt + " Dividend Calculated for the - " + fyear + "-" + nextYear;
                String paymentDt = "";
                if (sharePara.CalType.equalsIgnoreCase("KUCB")) {
                    int months = 0;
                    if (certStatus.equals("A")) {
                        months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), toDt);
                    } else {
                        if (vec.get(9) != null) {
                            paymentDt = vec.get(9).toString();
                        }
                        if (ymdFormat.parse(paymentDt).getTime() > ymdFormat.parse(toDt).getTime()) {
                            months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), toDt);

                        } 
                        //else {
                         //   months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), paymentDt);
                        //}
                        paymentDt = dmyFormat.format(ymdFormat.parse(paymentDt));
                    }
                    divTotal = amount * (dividend / 1200.0) * months;
                    if (months == 12) {
                        status = "FULL";
                    } else if (months == 0) {
                        status = "NO DIVIDEND";
                    } else {
                        status = "PARTIAL";
                    }
                } else if (sharePara.CalType.equalsIgnoreCase("KHAT")) {
                    int months = 0;
                    if (certStatus.equals("A")) {
                        months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), toDt);
                    } else {
                        if (vec.get(9) != null) {
                            paymentDt = vec.get(9).toString();
                        }
                        if (ymdFormat.parse(paymentDt).getTime() > ymdFormat.parse(toDt).getTime()) {
                            months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), toDt);

                        }
//                        else {
//                            months = getDivdendMonths(ymdFormat.format(shareHolderDetails.scDt), paymentDt);
//                        }
                        paymentDt = dmyFormat.format(ymdFormat.parse(paymentDt));
                    }
                    divTotal = amount * (dividend / 1200.0) * months;
                    if (months == 12) {
                        status = "FULL";
                    } else if (months == 0) {
                        status = "NO DIVIDEND";
                    } else {
                        status = "PARTIAL";
                    }
                } else if (sharePara.CalType.equalsIgnoreCase("INDR")) {
                    int months = 0;
                    if (certStatus.equals("A")) {
                        months = CbsUtil.monthDiff(ymdFormat.parse(ymdFormat.format(shareHolderDetails.scDt)), ymdFormat.parse(toDt)) + 1;
                    } else {
                        if (vec.get(9) != null) {
                            paymentDt = vec.get(9).toString();
                        }
                        if (ymdFormat.parse(paymentDt).getTime() > ymdFormat.parse(toDt).getTime()) {
                            months = CbsUtil.monthDiff(ymdFormat.parse(ymdFormat.format(shareHolderDetails.scDt)), ymdFormat.parse(toDt)) + 1;

                        }
//                        else {
//                            months = CbsUtil.monthDiff(ymdFormat.parse(fromDt), ymdFormat.parse(paymentDt)) ;
//                        }
                        paymentDt = dmyFormat.format(ymdFormat.parse(paymentDt));
                    }
                    if(months > 12) months = 12;
                    divTotal = amount * (dividend / 1200.0) * months;
                    if (months == 12) {
                        status = "FULL";
                    } else if (months == 0) {
                        status = "NO DIVIDEND";
                    } else {
                        status = "PARTIAL";
                    }
                }else {
                    if (certStatus.equals("A")) {
                        if (shareHolderDetails.scDt.getTime() <= preLastDt.getTime()) {
                            divTotal = amount * (dividend / 100.0);
                            status = "FULL";
                        } else if (shareHolderDetails.scDt.getTime() > preLastDt.getTime() && shareHolderDetails.scDt.getTime() < halfDt.getTime()) {
                            divTotal = amount * (dividend / 200.0);
                            status = "PARTIAL";
                        } else {
                            divTotal = 0d;
                            status = "NO DIVIDEND";
                        }
                    } else {
                        if (vec.get(9) != null) {
                            paymentDt = vec.get(9).toString();
                        }
                        if (ymdFormat.parse(paymentDt).getTime() > ymdFormat.parse(toDt).getTime()) {
                            if (shareHolderDetails.scDt.getTime() <= preLastDt.getTime()) {
                                divTotal = amount * (dividend / 100.0);
                                status = "FULL";
                            } else if (shareHolderDetails.scDt.getTime() > preLastDt.getTime() && shareHolderDetails.scDt.getTime() < halfDt.getTime()) {
                                divTotal = amount * (dividend / 200.0);
                                status = "PARTIAL";
                            } else {
                                divTotal = 0d;
                                status = "NO DIVIDEND";
                            }
                        } else if (ymdFormat.parse(paymentDt).getTime() > halfDt.getTime()) {
                            divTotal = amount * (dividend / 200.0);
                            status = "PARTIAL";
                        } else {
                            divTotal = 0d;
                            status = "NO DIVIDEND";
                        }
                        paymentDt = dmyFormat.format(ymdFormat.parse(paymentDt));
                    }
                }
                if (divTotal != 0) {
                    currentRow.setAmt(CbsUtil.round(amount, 2));
                    currentRow.setAuth(gAuthFlag);
                    if (gAuthFlag.equalsIgnoreCase("N")) {
                        currentRow.setAuthBy("");
                    } else {
                        currentRow.setAuthBy("SYSTEM");
                    }
                    currentRow.setBrcode(bname);
                    currentRow.setCertNo(shareHolderDetails.scShareCertNo);
                    currentRow.setDetails(details.trim());
                    currentRow.setDisbDate(ymdFormat.format(postDate));
                    currentRow.setDivamt(CbsUtil.round(divTotal, 0));
                    currentRow.setFolioNo(shareHolderDetails.scFolioNo);
                    currentRow.setIssueDt(dmyFormat.format(shareHolderDetails.scDt));
                    currentRow.setName(shareHolderDetails.shName);
                    currentRow.setNos(shareHolderDetails.countScShareNo);
                    currentRow.setPaid("N");
                    currentRow.setPurpose(shareHolderDetails.shPurpose);
                    currentRow.setRelatedAcno(shareHolderDetails.shRelatedAcno);
                    currentRow.setSno(i + 1);
                    currentRow.setStatus(status);
                    currentRow.setfYear(String.valueOf(fyear));
                    currentRow.setAddress(shareHolderDetails.addr);
                    currentRow.setPaymentdt(paymentDt);
                    currentRow.setBeneAcno(shareHolderDetails.beneAcno);
                    currentRow.setBeneName(shareHolderDetails.beneName);
                    currentRow.setBankName(shareHolderDetails.bankName);
                    currentRow.setIfscCode(shareHolderDetails.ifscCode);
                    DividendTableresultList.add(currentRow);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return DividendTableresultList;
    }

    private int getDivdendMonths(String shareDt, String toDt) throws ApplicationException {
        try {
            int months = CbsUtil.monthDiff(ymdFormat.parse(shareDt), ymdFormat.parse(toDt));
            int date = CbsUtil.datePart("D", shareDt);
            if (date == 1) {
                months = months + 1;
            }
            if (!CbsUtil.isLastDay(toDt)) {
                months = months - 1;
            }
            if (months >= 12) {
                months = 12;
            }
            return months;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String postDividend(List<DividendTable> dividendTable, String alphaCode, Integer fyear, Date postDate, String authBy, String brCode,String custType) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ElectronicPaymentSystemDAO dataManagementDAO = new ElectronicPaymentSystemDAO(em);
            SimpleDateFormat ymdms = new SimpleDateFormat("yyMMddHHmmssSSS");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println(new Date() + " Dividend Posting Started");
            ut.begin();
            List divGlList = em.createNativeQuery("Select Acno From gltable Where Msgflag=40").getResultList();
            if (divGlList.isEmpty()) {
                throw new ApplicationException("Message flag does not set for Dividend Payable GLHead");
            }
            if (divGlList.size() > 1) {
                throw new ApplicationException("Message Flag 40 set more then one GLHead");
            }

            Vector ele = (Vector) divGlList.get(0);
            String divAcno = "";
            if (ele.get(0) != null) {
                divAcno = ele.get(0).toString();
            }
             List queryResultList;
            if(custType.equalsIgnoreCase("00")){
                 queryResultList = em.createNativeQuery("Select ifnull(acno,0) From dividend_recon Where FYear='" + fyear + "' and cramt <> 0 and trandesc=110").getResultList();
            }else{
               queryResultList = em.createNativeQuery("select distinct CustEntityType from cbs_customer_master_detail where customerid in"
                       + "(select custId from share_holder where Regfoliono in"
                       + "(Select distinct acno From dividend_recon Where FYear='" + fyear + "' and cramt <> 0 and trandesc=110))and CustEntityType = '"+custType+"'").getResultList();  
            }
            if (!queryResultList.isEmpty()) {
                throw new ApplicationException("The Dividend for the year " + fyear + " for the Branch " + alphaCode + " is already Posted");
            }
            float trsNo = ftsPosting.getTrsNo();
            String acctDetail = "";
            List<DividendTable> toBeProcessList = new ArrayList<DividendTable>();
            float recNo = ftsPosting.getRecNo();
            for (DividendTable dt : dividendTable) {
                if (dt.getDivamt() > 0) {
                    Query insertDivRecon = em.createNativeQuery("Insert Into dividend_recon (acno,dramt,cramt,ty,trantype,iy,enterby,authby,details,bcode,dest_brnid, "
                            + "org_brnid,auth,disdate,trandesc,fyear,payby,trantime,recno,trsno) Values ('" + dt.getFolioNo() + "',0," + dt.getDivamt() + ",0,2,0,'SYSTEM','"
                            + authBy + "','Dividend for acno  " + dt.getFolioNo() + "','HO','" + brCode + "','" + brCode + "','Y','" + ymdFormat.format(postDate) + "',110,'"
                            + fyear + "',3,now()," + recNo + "," + trsNo + ")");
                    int result = insertDivRecon.executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Dividend Posting Failed");
                    }
                    if (acctDetail.equals("")) {
                        if (!dt.getBeneAcno().equals("")) {
                            acctDetail = "\'" + dt.getBeneAcno() + "\'";
                            toBeProcessList.add(dt);
                        }
                    } else {
                        if (!dt.getBeneAcno().equals("")) {
                            acctDetail = acctDetail + ",\'" + dt.getBeneAcno() + "\'";
                            toBeProcessList.add(dt);
                        }
                    }
                    recNo = recNo + 1;
                }
            }

            List accountExistDetailList = neftFacade.getAccountDetails(acctDetail);

            List dataList = em.createNativeQuery("select acno from abb_parameter_info "
                    + "where purpose = 'INTERSOLE ACCOUNT'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define Intersole A/c in abb parameter info");
            }
            Vector dataVec = (Vector) dataList.get(0);
            String isoAccount = dataVec.get(0).toString();

            dataList = em.createNativeQuery("select alphacode from branchmaster where "
                    + "brncode = '" + Integer.parseInt(brCode) + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define alphacode for-->" + brCode);
            }
            dataVec = (Vector) dataList.get(0);
            String orgnAlphaCode = dataVec.get(0).toString();

            double divPaidAmt = 0d;

            List<SmsToBatchTo> smsList = new ArrayList<SmsToBatchTo>();

            float trsNoNew = ftsPosting.getTrsNo();
            System.out.println("The size is = " + toBeProcessList.size());
            for (DividendTable dt : toBeProcessList) {
                String drDetail = "";
                if (dt.getBankName().equalsIgnoreCase("OWN")) {

                    drDetail = "Dividend paid in the Acno " + dt.getBeneAcno() + " of Bank " + dt.getBankName();

                    String details = "Dividend for Acno " + dt.getFolioNo();

                    String result = validateAcNo(accountExistDetailList, dt);

                    if (result.equals("true")) {
                        recNo = recNo + 1;
                        result = ibtFacade.insertDividendTxn(trsNoNew, recNo, authBy, brCode, dt, isoAccount, orgnAlphaCode, details);

                        if (result.equalsIgnoreCase("true")) {
                            recNo = recNo + 1;
                            Query insertDivReconNew = em.createNativeQuery("Insert Into dividend_recon (acno,cramt,dramt,ty,trantype,iy,enterby,authby,details,bcode,dest_brnid, "
                                    + "org_brnid,auth,disdate,trandesc,fyear,payby,trantime,recno,trsno) Values ('" + dt.getFolioNo() + "',0," + dt.getDivamt() + ",1,2,0,'SYSTEM','"
                                    + authBy + "','" + drDetail + "','HO','" + brCode + "','" + brCode + "','Y','" + ymdFormat.format(postDate) + "',110,'"
                                    + fyear + "',3,now()," + recNo + "," + trsNoNew + ")");
                            int resultNew = insertDivReconNew.executeUpdate();
                            if (resultNew <= 0) {
                                throw new ApplicationException("Dividend Posting Failed");
                            }
                            divPaidAmt = divPaidAmt + dt.getDivamt();
                            //Adding Object For Sms
                            SmsToBatchTo to = new SmsToBatchTo();

                            to.setAcNo(dt.getBeneAcno());
                            to.setCrAmt(dt.getDivamt());
                            to.setDrAmt(0d);
                            to.setTranType(2);
                            to.setTy(0);
                            to.setTxnDt(dmyFormat.format(postDate));
                            to.setTemplate(SmsType.DIV_POSTING);

                            smsList.add(to);
                        }
                    }
                }
            }
            if (divPaidAmt > 0) {
                recNo = recNo + 1;
                int n = em.createNativeQuery("insert into gl_recon(acno,ty, dt, valuedt, dramt, cramt, balance, trantype,details, iy, instno, instdt,"
                        + "enterby, auth, recno,payby,authby, trsno, trandesc, tokenno,tokenpaidby, subtokenno,trantime,org_brnid,dest_brnid,tran_id,"
                        + "term_id,adviceno,advicebrncode) values('" + divAcno + "',1,'" + ymdFormat.format(new Date()) + "','" + ymdFormat.format(new Date())
                        + "'," + divPaidAmt + ",0, 0,2,'Dividend paid for FYear " + fyear + "',0,'','19000101','" + authBy + "'," + "'Y'," + recNo
                        + ",3,'System'," + trsNoNew + ",110,0,'','',now(),'" + brCode + "'," + "'" + brCode + "',0,'','','' )").executeUpdate();
                if (n <= 0) {
                    throw new ApplicationException("Insertion Problem in Recons for A/c No-->" + divAcno);
                }
            }
            ftsPosting.updateRecNo(recNo);
            ut.commit();
            try {
                smsFacade.sendSmsToBatch(smsList);
            } catch (Exception e) {
                System.out.println("Problem In SMS Sending In Upload Neft Rtgs." + e.getMessage());
            }
            return "Dividend Posted Successfully and the Batch No = " + trsNoNew;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e.getMessage());
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }

    }

    private class SharePara {

        private String CalType = "",
                fullInt = "",
                halfInt = "";
    }

    private class ShareCapitalIssueShareHolder {

        private Integer countScShareNo;
        private Date scDt;
        private String scFolioNo,
                shRelatedAcno,
                shPurpose,
                shName,
                addr,
                shAlphaCode,
                beneAcno,
                bankName,
                beneName,
                ifscCode;
        private Long scShareCertNo;
        private double shareValue;
    }

    private ShareCapitalIssueShareHolder getShareCapitalIssueShareHolder(Vector vec) throws ApplicationException {
        ShareCapitalIssueShareHolder capitalIssueShareHolder = new ShareCapitalIssueShareHolder();
        try {
            if (vec.get(10) != null) {
                capitalIssueShareHolder.addr = vec.get(10).toString();
            } else {
                capitalIssueShareHolder.addr = "";
            }
            if (vec.get(0) != null) {
                capitalIssueShareHolder.countScShareNo = Integer.parseInt(vec.get(0).toString());
            } else {
                capitalIssueShareHolder.countScShareNo = 0;
            }
            if (vec.get(1) != null) {
                capitalIssueShareHolder.scFolioNo = vec.get(1).toString();
            } else {
                capitalIssueShareHolder.scFolioNo = "";
            }
            if (vec.get(2) != null) {
                capitalIssueShareHolder.scShareCertNo = Long.parseLong(vec.get(2).toString());
            } else {
                capitalIssueShareHolder.scShareCertNo = 0L;
            }
            if (vec.get(3) != null) {
                capitalIssueShareHolder.scDt = ymdFormat.parse(ymdFormat.format(vec.get(3)));
            } else {
                capitalIssueShareHolder.scDt = ymdFormat.parse("19000101");
            }
            if (vec.get(4) != null) {
                capitalIssueShareHolder.shRelatedAcno = vec.get(4).toString();
            } else {
                capitalIssueShareHolder.shRelatedAcno = "NA";
            }
            if (vec.get(5) != null) {
                capitalIssueShareHolder.shPurpose = vec.get(5).toString();
            } else {
                capitalIssueShareHolder.shPurpose = "";
            }
            if (vec.get(6) != null) {
                capitalIssueShareHolder.shName = vec.get(6).toString();
            } else {
                capitalIssueShareHolder.shName = "";
            }
            if (alphaCode.equalsIgnoreCase("All")) {
                if (vec.get(7) != null) {
                    capitalIssueShareHolder.shAlphaCode = vec.get(7).toString();
                } else {
                    capitalIssueShareHolder.shAlphaCode = "";
                }
                bname = capitalIssueShareHolder.shAlphaCode;
            } else {
                bname = alphaCode;
            }
            capitalIssueShareHolder.beneAcno = vec.get(11).toString();
            capitalIssueShareHolder.beneName = vec.get(12).toString();
            capitalIssueShareHolder.bankName = vec.get(14).toString();
            capitalIssueShareHolder.ifscCode = vec.get(13).toString();
            capitalIssueShareHolder.shareValue = Double.parseDouble(vec.get(15).toString());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return capitalIssueShareHolder;
    }

    class ReconTempComparator implements Comparator<DividendReconTempTable> {

        public int compare(DividendReconTempTable one, DividendReconTempTable two) {
            return one.getAcno().compareTo(two.getAcno());
        }
    }

    class ReconComparator implements Comparator<DividendReconTable> {

        public int compare(DividendReconTable one, DividendReconTable two) {
            return one.getAcno().compareTo(two.getAcno());
        }
    }

    private List dividendCalculationCCBL(String alphaCode, Double shareAmount, Double dividend, Integer fyear, Date postDate, String brCode) throws ApplicationException {
        List<DividendTable> DividendTableresultList = new ArrayList<DividendTable>();
        try {
            Double amount = 0.0, divTotal = 0.0;
            String details = "", status = "", dataQuery = "";
            Integer nextYear = fyear + 1;
            String toDt = nextYear + "0331", fromDt = fyear + sharePara.fullInt.trim();
            if (alphaCode.equalsIgnoreCase("All")) {
                dataQuery = "Select count(sc.shareNo),sc.FolioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,sh.purpose,cc.CustFullName,sh.alphacode,cs.status,"
                        + "DATE_FORMAT(cs.paymentdt,'%Y%m%d'),ifnull(cc.PerAddressLine1,''),IFNULL(sh.beneficiaryAccNo,''),"
                        + "IFNULL(sh.beneficiaryName,''),IFNULL(sh.ifsccode,''),IFNULL(sh.BankName,'')"
                        + "From share_capital_issue sc, share_holder sh, certificate_share cs,cbs_customer_master_detail cc  "
                        + "Where sh.regfolioNo=sc.FolioNo and Dt<'" + toDt + "' and sc.ShareCertNo <> 0 AND sc.FolioNo <> '" + brCode
                        + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001' and sc.shareCertNo = cs.certificateno and (paymentdt between '" + fromDt + "' "
                        + "and '" + toDt + "' or paymentdt is null or paymentdt ='19000101')and cc.customerid = sh.custid  Group by sc.folioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,cc.PerAddressLine1,"
                        + "sh.purpose,cc.custname,sh.alphacode,cs.paymentdt,cs.status,sc.issuedt order by sc.FolioNo,sc.shareCertNo";
            } else {
                dataQuery = "Select count(sc.shareNo),sc.FolioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno,sh.purpose,cc.CustFullName,sh.alphacode,cs.status,"
                        + "DATE_FORMAT(cs.paymentdt,'%Y%m%d'),ifnull(cc.PerAddressLine1,''),IFNULL(sh.beneficiaryAccNo,''),"
                        + "IFNULL(sh.beneficiaryName,''),IFNULL(sh.ifsccode,''),IFNULL(sh.BankName,'') From share_capital_issue sc, share_holder sh, "
                        + "certificate_share cs ,cbs_customer_master_detail cc "
                        + "Where sh.regfolioNo=sc.FolioNo and sh.alphacode='" + alphaCode + "' and Dt<'" + toDt + "' and sc.ShareCertNo <> 0 AND "
                        + "sc.FolioNo <> '" + brCode + CbsAcCodeConstant.SF_AC.getAcctCode() + "00000001' and sc.shareCertNo = cs.certificateno and "
                        + "(paymentdt between '" + fromDt + "' and '" + toDt + "' or paymentdt is null or paymentdt ='19000101')and cc.customerid = sh.custid "
                        + "Group by sc.folioNo,sc.shareCertNo,sc.Dt ,sh.relatedAcno, sh.purpose,cc.custname,sh.alphacode,cs.paymentdt,cc.PerAddressLine1,"
                        + "cs.status,sc.issuedt order by sc.FolioNo,sc.shareCertNo";
            }
            System.out.println("Query Execution Started");
            Query selectData = em.createNativeQuery(dataQuery);
            List result = selectData.getResultList();
            if (result.isEmpty()) {
                throw new ApplicationException("Data does not exist");
            }
            Date preLastDt = ymdFormat.parse(fyear + "0331");
            Date halfDt = ymdFormat.parse(fyear + sharePara.halfInt.trim());
            for (int i = 0; i < result.size(); i++) {
                DividendTable currentRow = new DividendTable();
                Vector vec = (Vector) result.get(i);
                ShareCapitalIssueShareHolder shareHolderDetails = getShareCapitalIssueShareHolder(vec);
                String certStatus = "";
                if (vec.get(8) != null) {
                    certStatus = vec.get(8).toString();
                }
                amount = shareHolderDetails.countScShareNo * shareAmount;
                details = shareHolderDetails.scDt + " Dividend Calculated for the - " + fyear + "-" + nextYear;
                String paymentDt = "";
                if (certStatus.equals("A")) {
                    if (shareHolderDetails.scDt.getTime() <= preLastDt.getTime()) {
                        divTotal = amount * (dividend / 100.0);
                        status = "FULL";
                    } else if (shareHolderDetails.scDt.getTime() > preLastDt.getTime() && shareHolderDetails.scDt.getTime() < halfDt.getTime()) {
                        divTotal = amount * (dividend / 200.0);
                        status = "PARTIAL";
                    } else {
                        divTotal = 0d;
                        status = "NO DIVIDEND";
                    }
                } else {

                    if (vec.get(9) != null) {
                        paymentDt = vec.get(9).toString();
                    }
                    if (ymdFormat.parse(paymentDt).getTime() > halfDt.getTime()) {
                        divTotal = amount * (dividend / 200.0);
                        status = "PARTIAL";
                    } else {
                        divTotal = 0d;
                        status = "NO DIVIDEND";
                    }
                    paymentDt = dmyFormat.format(ymdFormat.parse(paymentDt));
                }
                /*if (CbsUtil.dayDiff(shareHolderDetails.sc_Dt, preLastDt) >= 0) {
                 divTotal = amount * (dividend / 100.0);
                 status = "FULL";
                 } else if (CbsUtil.dayDiff(shareHolderDetails.sc_Dt, ymdFormat.parse(fromDt)) >= 0) {
                 divTotal = amount * (dividend / 100.0);
                 status = "FULL";
                 } else {
                 noMonths = CbsUtil.monthDiff(shareHolderDetails.sc_Dt, ymdFormat.parse(toDt));
                 if (day <= 15) {
                 noMonths = noMonths + 1;
                 }
                 divTotal = (amount * dividend * noMonths) / (100 * 12);
                 status = "PARTIAL";
                 }*/
                currentRow.setAmt(CbsUtil.round(amount, 2));
                currentRow.setAuth(gAuthFlag);
                if (gAuthFlag.equalsIgnoreCase("N")) {
                    currentRow.setAuthBy("");
                } else {
                    currentRow.setAuthBy("SYSTEM");
                }
                currentRow.setBrcode(bname);
                currentRow.setCertNo(shareHolderDetails.scShareCertNo);
                currentRow.setDetails(details.trim());
                currentRow.setDisbDate(ymdFormat.format(postDate));
                currentRow.setDivamt(CbsUtil.round(divTotal, 2));
                currentRow.setFolioNo(shareHolderDetails.scFolioNo);
                currentRow.setIssueDt(dmyFormat.format(shareHolderDetails.scDt));
                currentRow.setName(shareHolderDetails.shName);
                currentRow.setNos(shareHolderDetails.countScShareNo);
                currentRow.setPaid("N");
                currentRow.setPurpose(shareHolderDetails.shPurpose);
                currentRow.setRelatedAcno(shareHolderDetails.shRelatedAcno);
                currentRow.setSno(i + 1);
                currentRow.setStatus(status);
                currentRow.setfYear(String.valueOf(fyear));
                currentRow.setAddress(shareHolderDetails.addr);
                currentRow.setPaymentdt(paymentDt);
                currentRow.setBeneAcno(shareHolderDetails.beneAcno);
                currentRow.setBeneName(shareHolderDetails.beneName);
                currentRow.setBankName(shareHolderDetails.bankName);
                currentRow.setIfscCode(shareHolderDetails.ifscCode);
                DividendTableresultList.add(currentRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        }
        return DividendTableresultList;
    }

    public String validateAcNo(List existAcnoList, DividendTable pojo) throws ApplicationException {
        try {
            if (existAcnoList.isEmpty()) {
                return "No Mapped Account";
            }
            int index = -1;
            Vector ele;
            for (int i = 0; i < existAcnoList.size(); i++) {
                ele = (Vector) existAcnoList.get(i);
                if (pojo.getBeneAcno().equalsIgnoreCase(ele.get(3).toString()) || pojo.getBeneAcno().equalsIgnoreCase(ele.get(5).toString())) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                return "A/c number does not exist";
            }
            ele = (Vector) existAcnoList.get(index);

            String nature = ele.get(1).toString().trim();
            int accPostFlag = Integer.parseInt(ele.get(2).toString().trim());

            if (nature.equalsIgnoreCase(CbsConstant.PAY_ORDER)) {
                if (accPostFlag == 99) {
                    return "GL head not in use";
                }
            } else {
                if (accPostFlag == 9) {
                    return "Account is Closed";
                } else if (accPostFlag == 8) {
                    return "Operation Stopped For This Account";
                } else if (accPostFlag == 4) {
                    return "Account has been Frozen";
                } else if (AccountStatusEnum.getAcStatusValue(String.valueOf(accPostFlag)) == null) {
                    return "Sorry,Invalid Account Status";
                }
            }
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

}
