/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.SalaryProcessPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.AcctEnqueryFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.utils.ParseFileUtil;
import com.cbs.utils.Spellar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Sudhir
 */
@Stateless(mappedName = "PrintManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class PrintManagementFacade implements PrintFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    @EJB
    TdReceiptManagementFacadeRemote tdListingRemoteObj;
    @EJB
    FtsPostingMgmtFacadeRemote ftsRemote;
    @EJB
    CommonReportMethodsRemote common;
    @EJB
    AccountOpeningFacadeRemote acctRemote;
    @EJB
    AcctEnqueryFacadeRemote acctEnqRemote;

    public String save(String billType, String fieldName, int fieldOrder, int x, int y, int width) throws ApplicationException {
        try {
            List duplicateList = em.createNativeQuery("select * from cbs_bill_printing_parameter where bill_type='" + billType + "' and field_order=" + fieldOrder + " and field_name='" + fieldName + "'").getResultList();
            if (!duplicateList.isEmpty()) {
                return "Cannot insert duplicate enntries";
            }
            UserTransaction ut = context.getUserTransaction();

            ut.begin();
            Query q1 = em.createNativeQuery("insert into cbs_printing_parameter values ('" + billType + "','" + fieldName + "'," + fieldOrder + ","
                    + x + "," + y + "," + width + ")");
            int rowAffected = q1.executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "Data saved successfully";
            } else {
                ut.rollback();
                return "Exception occured while saving data";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String update(String billType, String labelValue, int fieldOrder, int x, int y, int width) throws ApplicationException {
        try {
            List duplicateList = em.createNativeQuery("select * from cbs_printing_parameter where bill_type='" + billType
                    + "' and field_order=" + fieldOrder).getResultList();
            if (duplicateList.isEmpty()) {
                return "Entries does not exist";
            }
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            Query q1 = em.createNativeQuery("update cbs_printing_parameter set X_CO_ORD=" + x + ",Y_CO_ORD=" + y
                    + ",COL_WIDTH=" + width + " where bill_type='" + billType + "' and field_order=" + fieldOrder
                    + " and lable_value = '" + labelValue + "'");
            int rowAffected = q1.executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "Data updated successfully";
            } else {
                ut.rollback();
                return "Exception occured while saving data";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String delete(String billType, int fieldOrder) throws ApplicationException {
        try {
            List duplicateList = em.createNativeQuery("select * from cbs_bill_printing_parameter where bill_type='" + billType + "' and field_order="
                    + fieldOrder).getResultList();
            if (duplicateList.isEmpty()) {
                return "Entries does not exist";
            }
            UserTransaction ut = context.getUserTransaction();
            ut.begin();
            Query q1 = em.createNativeQuery("delete from cbs_bill_printing_parameter where bill_type='" + billType + "' and field_order=" + fieldOrder);
            int rowAffected = q1.executeUpdate();
            if (rowAffected > 0) {
                ut.commit();
                return "Data deleted successfully";
            } else {
                ut.rollback();
                return "Exception occured while deleting data";
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getData(String billType, String fieldType) throws ApplicationException {
        try {
            List billPrintingList = new ArrayList();
            List billrintingList = em.createNativeQuery("select * from cbs_printing_parameter where bill_type='" + billType + "' and lable_value='" + fieldType + "' order by field_order").getResultList();
            if (!billrintingList.isEmpty()) {
                return billrintingList;
            }
            return billPrintingList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getOnloadData() throws ApplicationException {
        try {
            List data = null;
            data = em.createNativeQuery("SELECT INSTCODE,INSTNATURE FROM billtypemaster where instnature in ('PO','DD') ORDER BY INSTCODE").getResultList();
            return data;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getAlphaCode(String orgnCode) throws ApplicationException {
        try {
            List alphaCodeList = null;
            alphaCodeList = em.createNativeQuery("SELECT ALPHACODE FROM branchmaster WHERE BRNCODE = CAST('" + orgnCode + "' AS unsigned)").getResultList();
            return alphaCodeList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPODetail(String instNo, String issueDt, String ogrnAlphaCode) throws ApplicationException {
        try {
            List poList = null;

            //poList = em.createNativeQuery("SELECT SEQNO,CUSTNAME,AMOUNT,INFAVOUROF,PAYABLEAT FROM bill_po WHERE INSTNO = '" + instNo + "' AND ORIGINDT = '" + issueDt + "' AND PAYABLEAT = '" + ogrnAlphaCode + "' AND STATUS='ISSUED'").getResultList();
            poList = em.createNativeQuery("SELECT SEQNO,CUSTNAME,AMOUNT,INFAVOUROF,concat(bm.branchname,' ',bm.city) "
                    + "FROM bill_po bo,branchmaster bm WHERE INSTNO = '" + instNo + "' AND ORIGINDT = '" + issueDt + "' AND PAYABLEAT = '"
                    + ogrnAlphaCode + "' AND STATUS='ISSUED' and bo.PAYABLEAT = bm.alphacode").getResultList();
            return poList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPOPaidDetail(String instNo, String issueDt, String ogrnAlphaCode) throws ApplicationException {
        try {
            List poList = null;
            //poList = em.createNativeQuery("SELECT SEQNO,CUSTNAME,AMOUNT,INFAVOUROF,PAYABLEAT FROM bill_po WHERE INSTNO = '" + instNo + "' AND ORIGINDT = '" + issueDt + "' AND PAYABLEAT = '" + ogrnAlphaCode + "' AND STATUS='ISSUED'").getResultList();
            poList = em.createNativeQuery("SELECT SEQNO,CUSTNAME,AMOUNT,INFAVOUROF,concat(bm.branchname,' ',bm.city) "
                    + "FROM bill_po bo,branchmaster bm WHERE INSTNO = '" + instNo + "' AND dt = '" + issueDt + "' AND PAYABLEAT = '"
                    + ogrnAlphaCode + "' AND STATUS='Paid' and bo.PAYABLEAT = bm.alphacode").getResultList();
            return poList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDDDetail(String instNo, String issueDt, String ogrnAlphaCode) throws ApplicationException {
        try {
            List ddList = null;
            //ddList = em.createNativeQuery("SELECT SEQNO,CUSTNAME,AMOUNT,INFAVOUROF,PAYABLEAT FROM bill_dd WHERE INSTNO = '" + instNo + "' AND ORIGINDT = '" + issueDt + "' AND PAYABLEAT = '" + ogrnAlphaCode + "' AND STATUS='ISSUED'").getResultList();
            ddList = em.createNativeQuery("SELECT b.SEQNO,b.CUSTNAME,b.AMOUNT,b.INFAVOUROF,c.ref_desc FROM bill_dd b, cbs_ref_rec_type c WHERE b.INSTNO = '" + instNo + "' AND b.ORIGINDT = '" + issueDt + "' AND b.org_brnid = '" + ogrnAlphaCode + "' AND b.STATUS='ISSUED' and b.payableat = c.ref_code and c.ref_rec_no = '001'").getResultList();
            return ddList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBillParameters(String billType) throws ApplicationException {
        try {
            List billParamsList = null;
            billParamsList = em.createNativeQuery("select field_name,field_order,top_space_in_new_line,left_space_in_space from cbs_bill_printing_parameter where bill_type='" + billType + "' order by field_order").getResultList();
            return billParamsList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List reportOption() throws ApplicationException {
        try {
            List result = new ArrayList();
            Query selectQuery = em.createNativeQuery("SELECT LPAD(CAST(CODE as char),3,'0'),DESCRIPTION FROM codebook WHERE GROUPCODE=98 AND CODE<>0");
            result = selectQuery.getResultList();
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String createFile(Integer reportOption, String date, String brCode) throws ApplicationException {
        String message = "";
        try {
            int batchCount;
            int count;
            Integer tmpBatchNo = null;
            String tmpBrcode;
            String tmpFileName;
            String tmpRepName;
            List resultList = new ArrayList();
            if (reportOption == null) {
                message = "Report Option Cannot Be Blank !!!";
                return message;
            }
            if (date == null || date.equalsIgnoreCase("") || date.length() == 0) {
                message = "Date Cannot Be Blank !!!";
                return message;
            }
            if (brCode == null || brCode.equalsIgnoreCase("") || brCode.length() == 0) {
                message = "Branch Code Cannot Be Blank !!!";
                return message;
            }
            List chk1 = em.createNativeQuery("SELECT Description FROM codebook WHERE GROUPCODE=98 and code=" + reportOption + "").getResultList();
            if (chk1.isEmpty()) {
                message = "Please Select Valid Report Option !!!";
                return message;
            } else {
                Vector chk1Vec = (Vector) chk1.get(0);
                tmpRepName = chk1Vec.get(0).toString();
            }
            List tmpchk = em.createNativeQuery("SELECT * FROM codebook WHERE GROUPCODE=98 and code=" + reportOption + " AND CODE not in (241,0)").getResultList();
            if (reportOption == 242) {
                resultList = em.createNativeQuery("select LPAD(CAST(txninstno as char),6,'0'),'000000000','000','0000000000',"
                        + "LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 13,'0'),date_format(txndate,'%d/%m/%y'),'2',"
                        + "concat(AREACODE,BNKCODE,BRANCHCODE)"
                        + " from clg_localchq where txndate='" + date + "' and orgnbrcode = '" + brCode + "' order by txntype,substring(acno,3,2),vtot").getResultList();
            } else if (reportOption == 243) { /*Code Added For Kanpur */

                List chk2 = em.createNativeQuery("SELECT txnstatus FROM clg_ow_entry WHERE txndate='" + date + "' and orgnbrcode = '" + brCode + "' and txnstatus <>'V'").getResultList();
                if (!chk2.isEmpty()) {
                    message = "Some Pending Entries Exist !!!";
                    return message;
                }

//                resultList = em.createNativeQuery("select concat(LPAD(CAST(b.micr as char),3,'0'),LPAD(CAST(b.micrcode as char),3,'0'),LPAD(CAST(b.branchcode as char),3,'0')),"
//                        + " concat(c.AREACODE,c.BNKCODE,c.BRANCHCODE),"
//                        + " date_format(txndate,'%d%m%Y'),LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 21,'0'),"
//                        + " LPAD(CAST(txninstno as char),6,'0'),LPAD(substring(a.acNo,5,6),7,'0'),RPAD(substring(a.custname,1,25),25,' ')"
//                        + " from clg_ow_entry c, branchmaster br, bnkadd b,accountmaster a where c.txndate='" + date + "' and c.orgnbrcode = '" + brCode + "' and c.txnstatus ='V' "
//                        + " and c.orgnbrcode = LPAD(CAST(br.brncode as char),2,'0') and br.alphacode = b.alphacode and c.acno = a.acno "
//                        + " order by c.txntype,substring(c.acno,3,2),vtot").getResultList();
                resultList = em.createNativeQuery("select concat(LPAD(CAST(b.micr as char),3,'0'),LPAD(CAST(b.micrcode as char),3,'0'),LPAD(CAST(b.branchcode as char),3,'0')),"
                        + " concat(c.AREACODE,c.BNKCODE,c.BRANCHCODE),"
                        + " date_format(txndate,'%d%m%Y'),LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 21,'0'),"
                        + " LPAD(CAST(txninstno as char),6,'0'),LPAD(substring(a.acNo,5,6),7,'0'),RPAD(substring(a.custname,1,25),25,' '),"
                        + " c.txntype,substring(c.acno,3,2),vtot from clg_ow_entry c, branchmaster br, bnkadd b,accountmaster a "
                        + " where c.txndate='" + date + "' and c.orgnbrcode = '" + brCode + "' and c.txnstatus ='V' "
                        + " and c.orgnbrcode = LPAD(CAST(br.brncode as char),2,'0') and br.alphacode = b.alphacode and c.acno = a.acno "
                        + " union "
                        + " select concat(LPAD(CAST(b.micr as char),3,'0'),LPAD(CAST(b.micrcode as char),3,'0'),LPAD(CAST(b.branchcode as char),3,'0')),"
                        + " concat(c.AREACODE,c.BNKCODE,c.BRANCHCODE),date_format(txndate,'%d%m%Y'),LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 21,'0'),"
                        + " LPAD(CAST(txninstno as char),6,'0'),LPAD(substring(a.acNo,5,6),7,'0'),RPAD(substring(a.acname,1,25),25,' '),"
                        + " c.txntype,substring(c.acno,3,2),vtot from clg_ow_entry c, branchmaster br, bnkadd b,gltable a "
                        + " where c.txndate='" + date + "' and c.orgnbrcode = '" + brCode + "' and c.txnstatus ='V' "
                        + " and c.orgnbrcode = LPAD(CAST(br.brncode as char),2,'0') and br.alphacode = b.alphacode and c.acno = a.acno "
                        + " order by 8,9,10").getResultList();
            } else {

                if (!tmpchk.isEmpty()) {
                    resultList = em.createNativeQuery("select LPAD(CAST(txninstno as char),6,'0'),'000000000','000','0000000000',"
                            + "LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 13,'0'),date_format(txndate,'%d/%m/%y'),'2',"
                            + "concat(AREACODE,BNKCODE,BRANCHCODE)"
                            + " from clg_ow_entry where txndate='" + date + "' and orgnbrcode = '" + brCode + "' AND BNKCODE=" + reportOption + " order by txntype,substring(acno,3,2),vtot").getResultList();
                } else {
                    resultList = em.createNativeQuery("select LPAD(CAST(txninstno as char),6,'0'),'000000000','000','0000000000',"
                            + "LPAD(cast(cast((txninstamt * 100) as decimal) as char(100)), 13,'0'),date_format(txndate,'%d/%m/%y'),'2',"
                            + "concat(AREACODE,BNKCODE,BRANCHCODE)"
                            + " from clg_ow_entry where txndate='" + date + "' and orgnbrcode = '" + brCode + "' AND BNKCODE<>240 order by txntype,substring(acno,3,2),vtot").getResultList();
                }
            }

            if (resultList.isEmpty()) {
                message = "No Transaction Exists !!!";
                return message;
            } else {
                /**
                 * *
                 */
                List brnCodeLt = em.createNativeQuery("select BRNCODE FROM parameterinfo WHERE BRNCODE=CAST('" + brCode + "' AS UNSIGNED)").getResultList();
                if (brnCodeLt.isEmpty()) {
                    message = "Branch Code Is Not Present In PARAMETERINFO !!!";
                    return message;
                }
                Vector brnCodeLtVec = (Vector) brnCodeLt.get(0);
                tmpBrcode = brnCodeLtVec.get(0).toString();

                List paramCodeLt = em.createNativeQuery("SELECT CODE FROM parameterinfo_report WHERE REPORTNAME='CLGBATCH'").getResultList();
                if (paramCodeLt.isEmpty()) {
                    batchCount = 10000;
                } else {
                    Vector paramCodeLtVec = (Vector) paramCodeLt.get(0);
                    batchCount = Integer.parseInt(paramCodeLtVec.get(0).toString());
                }
                count = resultList.size();
                double d = Double.valueOf(count) / Double.valueOf(batchCount);
                Double tmpNo = (Math.ceil(d));
                int batchNo = tmpNo.intValue();

                String returnFileName = "";
                for (int i = 0; i < batchNo; i++) {
                    tmpBatchNo = i + 1;
                    List chk = em.createNativeQuery("SELECT CONCAT(LPAD(CAST(('" + tmpBrcode + "') as char),3,'0'),"
                            + " LPAD(CAST((EXTRACT(DAY from '" + date + "')) as char),2,'0'),"
                            + " LPAD(CAST((EXTRACT(MONTH from '" + date + "')) as char),2,'0'),'" + tmpBatchNo + "')").getResultList();

                    if (chk.isEmpty()) {
                        message = "Problem In Report Name Creation !!!";
                        return message;
                    } else {
                        Vector ele = (Vector) chk.get(0);
                        tmpFileName = ele.get(0).toString();
                    }
                    String fileName = "";
                    //if (reportOption == 240) {
                    if (!tmpchk.isEmpty()) {
                        //fileName = tmpFileName + "_HDFC" + ".txt";
                        fileName = tmpFileName + "_" + tmpRepName + ".txt";
                    } else {
                        fileName = tmpFileName + ".txt";
                    }
                    int start = i * batchCount;
                    int end = (i + 1) * batchCount;
                    if (end > resultList.size()) {
                        end = resultList.size();
                    }
                    String temp = "";
                    if (reportOption == 243) {
                        temp = createFlatFileWithoutQuotes(fileName, resultList, start, end);
                    } else {
                        temp = createFlatFile(fileName, resultList, start, end);
                    }
                    if (returnFileName.equals("")) {
                        returnFileName = temp;
                    } else {
                        returnFileName = returnFileName + "," + temp;
                    }
                    //System.out.println("returnFileName:======="+returnFileName);
                }
                message = "Batch Saved Succesfully. File Name :-" + returnFileName.trim();
                return message;
                /**
                 * *
                 */
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        // return message; commented by sudhir
    }

    private String createFlatFile(String fileName, List resultList, int start, int end) throws ApplicationException {
        try {
            String osName = System.getProperty("os.name");
            String fileSource = null;
            File srcFolder;
            if (osName.equalsIgnoreCase("Linux")) {
                srcFolder = new File("/install/ClgTxtFile/");
                if (!srcFolder.exists()) {
                    srcFolder.mkdir();
                }
                fileSource = "/install/ClgTxtFile/" + fileName;
            } else {
                srcFolder = new File("C:/ClgTxtFile/");
                if (!srcFolder.exists()) {
                    srcFolder.mkdir();
                }
                fileSource = "C:/ClgTxtFile/" + fileName;
            }
            File f;
            f = new File(fileSource);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);

            for (int i = start; i < end; i++) {
                Vector v2 = (Vector) resultList.get(i);
                out.write("\"" + v2.get(0).toString() + "\",\"" + v2.get(1).toString() + "\",\"" + v2.get(2).toString() + "\",\"" + v2.get(3).toString() + "\",\"" + v2.get(4).toString() + "\",\"" + v2.get(5).toString() + "\",\"" + v2.get(6).toString() + "\",\"" + v2.get(7).toString() + "\"");
                out.newLine();
            }
            out.close();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fileName;
    }

    private String createFlatFileWithoutQuotes(String fileName, List resultList, int start, int end) throws ApplicationException {
        try {
            String osName = System.getProperty("os.name");
            String fileSource = null;
            File srcFolder;
            if (osName.equalsIgnoreCase("Linux")) {
                srcFolder = new File("/install/ClgTxtFile/");
                if (!srcFolder.exists()) {
                    srcFolder.mkdir();
                }
                fileSource = "/install/ClgTxtFile/" + fileName;
            } else {
                srcFolder = new File("C:/ClgTxtFile/");
                if (!srcFolder.exists()) {
                    srcFolder.mkdir();
                }
                fileSource = "C:/ClgTxtFile/" + fileName;
            }
            File f;
            f = new File(fileSource);
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fstream = new FileWriter(f);
            BufferedWriter out = new BufferedWriter(fstream);

            for (int i = start; i < end; i++) {
                Vector v2 = (Vector) resultList.get(i);
                out.write(v2.get(0).toString() + v2.get(1).toString() + v2.get(2).toString() + v2.get(3).toString() + v2.get(4).toString() + v2.get(5).toString() + v2.get(6).toString());
                out.newLine();
            }
            out.close();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fileName;
    }

    public List getFDDetails(String acno, Float rtNo) throws ApplicationException {
        List fdDetails = null;
        double matAmt = 0.0;
        try {
            fdDetails = em.createNativeQuery("SELECT A.PRINAMT,B.CUSTNAME,DATE_FORMAT(A.TD_MADEDT,'%d/%m/%Y'),DATE_FORMAT(A.FDDT,'%d/%m/%Y'),DATE_FORMAT(A.MATDT,'%d/%m/%Y'),A.Period,A.Years,A.Months,A.Days,A.ROI,A.INTOPT,A.RECEIPTNO,B.JtName1,B.JtName2,B.JtName3,B.JtName4,C.ACCTDESC,D.DESCRIPTION,TC.PRADDRESS,TC.DOB,DATEDIFF(TC.DOB,NOW()),ifnull(A.inttoacno,'') FROM td_vouchmst A,td_accountmaster B,accounttypemaster C,codebook D,td_customermaster TC "
                    + " WHERE A.ACNO='" + acno + "' AND A.STATUS='A' AND VOUCHERNO=" + rtNo + " AND A.ACNO = B.ACNO AND C.ACCTCODE = SUBSTRING('" + acno + "',3,2) AND B.OPERMODE = D.CODE AND D.GROUPCODE = 4 AND TC.CUSTNO = SUBSTRING('" + acno + "',5,6) AND TC.BRNCODE = B.CURBRCODE AND TC.ACTYPE = B.ACCTTYPE").getResultList();
            if (fdDetails.size() > 0) {
                for (int i = 0; i < fdDetails.size(); i++) {
                    Vector element = (Vector) fdDetails.get(i);
                    double prinAmt = Double.parseDouble(element.get(0).toString());
                    String fdDate = element.get(3).toString();
                    String matDate = element.get(4).toString();
                    String period = element.get(5).toString();
                    String periodY = element.get(6).toString();
                    String periodM = element.get(7).toString();
                    String periodD = element.get(8).toString();
                    Float roi = Float.parseFloat(element.get(9).toString());
                    String intOption = element.get(10).toString();
                    String fdDateString = fdDate.substring(6) + fdDate.substring(3, 5) + fdDate.substring(0, 2);
                    String matDateString = matDate.substring(6) + matDate.substring(3, 5) + matDate.substring(0, 2);
                    String fdInterest = tdListingRemoteObj.orgFDInterest(intOption, roi, fdDateString, matDateString, prinAmt, period, acno.substring(0, 2));
                    if (intOption.equalsIgnoreCase("M") || intOption.equalsIgnoreCase("Q")) {
                        if ((element.get(21).toString().equalsIgnoreCase("")) || (acno.equalsIgnoreCase(element.get(21).toString()))) {
                            matAmt = prinAmt + Double.parseDouble(fdInterest);
                        } else {
                            matAmt = prinAmt;
                        }
                    } else {
                        matAmt = prinAmt + Double.parseDouble(fdInterest);
                    }
                    String jtNm1 = element.get(12).toString() != null ? element.get(12).toString() : "";
                    String jtNm2 = element.get(13).toString() != null ? element.get(13).toString() : "";
                    String jtNm3 = element.get(14).toString() != null ? element.get(14).toString() : "";
                    String jtNm4 = element.get(15).toString() != null ? element.get(15).toString() : "";
                    String actDesc = element.get(16).toString() != null ? element.get(16).toString() : "";
                    String opMode = element.get(17).toString();
                    String adr = element.get(18).toString() != null ? element.get(18).toString() : "";
                    String dob = element.get(19).toString() != null ? element.get(19).toString() : "";
                    String dDiff = element.get(20).toString() != null ? element.get(20).toString() : "0";
                }
                fdDetails.add(matAmt);
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return fdDetails;
    }

    public String insertSalaryData(List<SalaryProcessPojo> tableDataList, String dt, String valueDt, String enterBy, String orgBrnCode) throws ApplicationException {
        String result = "false";
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

            Float trsno = ftsRemote.getTrsNo();
            double crAmt = 0, drAmt = 0;
            Integer ty = 0, payBy = 0, iy = 0;
            String auth = "N";
            ut.begin();
            for (int i = 0; i < tableDataList.size(); i++) {
                SalaryProcessPojo pojo = tableDataList.get(i);
                String acNature = ftsRemote.getAccountNature(pojo.getSalaryAccount());
                if (pojo.getSalaryTranType().equalsIgnoreCase("Credit")) {
                    ty = 0;
                    payBy = 3;
                    crAmt = pojo.getSalaryAmount().doubleValue();
                    drAmt = 0.0;
                } else {
                    ty = 1;
                    crAmt = 0.0;
                    drAmt = pojo.getSalaryAmount().doubleValue();
                    if (pojo.getSalaryInstNo().equalsIgnoreCase("")) {
                        payBy = 3;
                    } else {
                        payBy = 1;
                    }
                    String chkBalResult = ftsRemote.chkBal(pojo.getSalaryAccount(), ty, 1, acNature);
                    if (!chkBalResult.equalsIgnoreCase("true")) {
                        String balResult = ftsRemote.checkBalance(pojo.getSalaryAccount(), drAmt, enterBy);
                        if (!balResult.equalsIgnoreCase("true")) {
                            throw new ApplicationException(balResult + " " + pojo.getSalaryAccount());
                        }
                    }
                }
                int tranDesc = 0;
                if (acNature.equalsIgnoreCase(CbsConstant.RECURRING_AC) && ty == 0) {
                    tranDesc = 1;
                }
                float recno = ftsRemote.getRecNo();
                Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                        + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                        + "DEST_BRNID,TRAN_ID,ADVICENO,ADVICEBRNCODE)"
                        + "values ('" + pojo.getSalaryAccount() + "','" + ftsRemote.ftsGetCustName(pojo.getSalaryAccount()) + "','" + dt + "','" + valueDt
                        + "'," + crAmt + "," + drAmt + "," + ty + "," + 2 + "," + recno + ",'" + pojo.getSalaryInstNo() + "','" + ymd.format(dmy.parse(pojo.getSalaryInstDt()))
                        + "'," + payBy + "," + iy + ",'" + auth + "','" + enterBy + "'," + tranDesc + ",0,'A','" + pojo.getSalaryDetails() + "',''," + trsno + "," + "now()" + ",'','"
                        + orgBrnCode + "','" + ftsRemote.getCurrentBrnCode(pojo.getSalaryAccount()) + "',0,'','')");
                int insertNo = insertQuery3.executeUpdate();
                if (insertNo <= 0) {
                    throw new ApplicationException("Problem in data insertion in Recon Trf.");
                }
                if (ty == 1) {
                    result = ftsRemote.updateBalance(acNature, pojo.getSalaryAccount(), crAmt, drAmt, "", "");
                    if (result.equalsIgnoreCase("true")) {
                        if (pojo.getSalaryInstNo() != null && !pojo.getSalaryInstNo().equalsIgnoreCase("")) {
                            result = ftsRemote.updateCheque(pojo.getSalaryAccount(), payBy, ty, Float.parseFloat(pojo.getSalaryInstNo()), enterBy);
                            if (!result.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Problem in cheque updation." + pojo.getSalaryAccount() + " and cheque no: " + pojo.getSalaryInstNo());
                            }
                        }
                    } else {
                        throw new ApplicationException("Problem in balance updation." + pojo.getSalaryAccount());
                    }
                }
            }
            result = trsno.toString();
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

    private boolean isAccountExist(String acno, Map<String, Double> accountMap) {
        for (Map.Entry<String, Double> entry : accountMap.entrySet()) {
            if (entry.getKey().equals(acno)) {
                return true;
            }
        }
        return false;
    }

    public String getMsgFlagByAcno(String acno) throws ApplicationException {
        String message = "false";
        try {
            List acnoList = em.createNativeQuery("select acno from gltable where msgflag in(4,50) and acno='" + acno + "'").getResultList();
            if (!acnoList.isEmpty()) {
                message = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return message;
    }

    public List getFDValues() throws ApplicationException {
        try {
            List valFDResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='TD PRINTING'").getResultList();
            return valFDResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String insertSalaryFromXLS(String acno, String custname, String dt, String valueDt, double cramt, double dramt, Integer ty, Float recno,
            String instNo, String instDt, Integer payby, String enterby, String details,
            Float trsno, String orgBrCode, String destBrCode) throws ApplicationException {

        String result = "false";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Query insertQuery3 = em.createNativeQuery("INSERT INTO recon_trf_d(ACNO,CUSTNAME,DT,VALUEDT,CRAMT,DRAMT,TY,TRANTYPE,RECNO,"
                    + "INSTNO,INSTDT, PAYBY,IY,AUTH,ENTERBY,TRANDESC,TOKENNO,SUBTOKENNO,DETAILS,AUTHBY,TRSNO,TRANTIME,TERM_ID,ORG_BRNID,"
                    + "DEST_BRNID,TRAN_ID,ADVICENO,ADVICEBRNCODE)"
                    + "values ('" + acno + "','" + custname + "','" + dt + "','" + valueDt
                    + "'," + cramt + "," + dramt + "," + ty + "," + 2 + "," + recno + ",'" + instNo + "','" + instDt
                    + "'," + payby + ",0,'N','" + enterby + "',0,0,'A','" + details + "',''," + trsno + "," + "now()" + ",'','"
                    + orgBrCode + "','" + destBrCode + "',0,'','')");
            int insertNo = insertQuery3.executeUpdate();
            if (insertNo <= 0) {
                throw new ApplicationException("Problem in data insertion in Recon Trf.");
            }
            if (ty == 1) {
                result = ftsRemote.updateBalance(ftsRemote.getAccountNature(acno), acno, cramt, dramt, "", "");
                if (result.equalsIgnoreCase("true")) {
                    if (instNo != null && !instNo.equalsIgnoreCase("")) {
                        result = ftsRemote.updateCheque(acno, payby, ty, Float.parseFloat(instNo), enterby);
                        if (!result.equalsIgnoreCase("true")) {
                            throw new ApplicationException("Problem in cheque updation." + acno + " and cheque no: " + instNo);
                        }
                    }
                } else {
                    throw new ApplicationException("Problem in balance updation." + acno);
                }
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

    @Override
    public List<String> generateCbsAtmReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hms = new SimpleDateFormat("HHmmss");
        SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        FileWriter issuerWriter = null, onUsWriter = null, acquirerWriter = null;
        List<String> files = new ArrayList<String>();
        try {
            ut.begin();
            List atmList = em.createNativeQuery("select code from parameterinfo_report where reportname='BANK-ATM-STATUS'").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please fill data into parameterinfo report for BANK-ATM-STATUS");
            }
            Vector element = (Vector) atmList.get(0);
            String atmStatus = element.get(0).toString();

            atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            String sno = "", issuerFile = "", onUsFile = "", acquireFile = "", writeFolderPath = "";

            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }

            if (atmStatus.equals("73") || atmStatus.equals("65")) {
                /**
                 * Isser File generation***
                 */
                sno = getSerialNo(fileGenerationDt, "I").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                issuerFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".RC";
                issuerFile = writeFolderPath + issuerFile;
                issuerWriter = new FileWriter(issuerFile);

                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,rrn as transaction_serial_number,"
                        + "substring(from_account_number,3,2) as account_type,from_account_number as account_number,"
                        + "substring(from_account_number,1,2) as branch_code,amount as transaction_amount,tran_date as transaction_date,"
                        + "tran_time as transaction_time,in_coming_date as value_date,in_coming_date as value_time,terminal_id as atm_id,"
                        + "terminal_location as atm_location from atm_normal_transaction_parameter where processing_code in('01') "
                        + "and tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        String acno = element.get(4).toString().trim();
                        String custname = getCustNameByAcno(acno);
                        String accountType = element.get(3).toString().trim();
                        accountType = getAcctCode(accountType);
                        //accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
//                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                        String tempAmount = element.get(6).toString();
                        BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                        String singleEntry = ParseFileUtil.addTrailingSpaces(element.get(0).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 19)
                                + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 12) + ParseFileUtil.addTrailingSpaces(accountType, 2)
                                + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addTrailingSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(element.get(5).toString(), 4)
                                + ParseFileUtil.addTrailingSpaces("", 25) + "I" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + ymd.format(dataSdf.parse(element.get(7).toString()))
                                + hms.format(dataSdf.parse(element.get(8).toString())) + ymd.format(dataSdf.parse(element.get(9).toString())) + hms.format(dataSdf.parse(element.get(8).toString()))
                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString(), 8) + ParseFileUtil.addTrailingSpaces(element.get(12).toString(), 40) + "00" + "\n";

                        issuerWriter.write(singleEntry);
                    }
                } else {
                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 203) + "\n");
                }

                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,rrn as transaction_serial_number,"
                        + "substring(from_account_number,3,2) as account_type,from_account_number as account_number,"
                        + "substring(from_account_number,1,2) as branch_code,amount as transaction_amount,tran_date as transaction_date,"
                        + "tran_time as transaction_time,in_coming_date as value_date,in_coming_date as value_time,terminal_id as atm_id,"
                        + "terminal_location as atm_location,original_system_trace_number from atm_reversal_transaction_parameter where  processing_code in('01') "
                        + "and tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        String acno = element.get(4).toString().trim();
                        String custname = getCustNameByAcno(acno);
                        String accountType = element.get(3).toString().trim();
                        accountType = getAcctCode(accountType);
                        //accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
//                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                        String tempAmount = element.get(6).toString();

                        List orgAmtList = em.createNativeQuery("select ifnull(amount,0),ifnull(transaction_fee,'C00000000') from atm_normal_transaction_parameter where system_trace_number='" + element.get(13).toString() + "' and tran_date='" + fileGenerationDt + "'").getResultList();
                        Vector amtVector = (Vector) orgAmtList.get(0);
                        BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                        String tranFee = amtVector.get(1).toString();
                        if (!tranFee.equalsIgnoreCase("C00000000")) {
                            orgAmount = orgAmount.add(new BigDecimal(tranFee.substring(1)).divide(new BigDecimal("100")));

                        }
                        BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                        if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                            tranAmount = orgAmount;
                        } else {
                            tranAmount = tranAmount;
                        }
                        tempAmount = tranAmount.toString();

                        BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
                        String singleEntry = ParseFileUtil.addTrailingSpaces(element.get(0).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 19)
                                + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 12) + ParseFileUtil.addTrailingSpaces(accountType, 2)
                                + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addTrailingSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(element.get(5).toString(), 4)
                                + ParseFileUtil.addTrailingSpaces("", 25) + "I" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + ymd.format(dataSdf.parse(element.get(7).toString()))
                                + hms.format(dataSdf.parse(element.get(8).toString())) + ymd.format(dataSdf.parse(element.get(9).toString())) + hms.format(dataSdf.parse(element.get(8).toString()))
                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString(), 8) + ParseFileUtil.addTrailingSpaces(element.get(12).toString(), 40) + "00" + "\n";

                        issuerWriter.write(singleEntry);
                    }
                } else {
                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 203) + "\n");
                }
                String onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "I", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
            }
            if (atmStatus.equals("79") || atmStatus.equals("65")) {
                /**
                 * On-Us File generation***
                 */
                sno = getSerialNo(fileGenerationDt, "O").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                onUsFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "O-" + sno + ".RC";
                onUsFile = writeFolderPath + onUsFile;
                onUsWriter = new FileWriter(onUsFile);

                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,rrn as transaction_serial_number,"
                        + "substring(from_account_number,3,2) as account_type,from_account_number as account_number,"
                        + "substring(from_account_number,1,2) as branch_code,amount as transaction_amount,tran_date as transaction_date,"
                        + "tran_time as transaction_time,in_coming_date as value_date,in_coming_date as value_time,terminal_id as atm_id,"
                        + "terminal_location as atm_location from atm_normal_transaction_parameter where  processing_code in('00') "
                        + "and tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        String acno = element.get(4).toString().trim();
                        String custname = getCustNameByAcno(acno);
                        String accountType = element.get(3).toString().trim();
                        accountType = getAcctCode(accountType);
                        //accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                        String tempAmount = element.get(6).toString();
                        BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                        String singleEntry = ParseFileUtil.addTrailingSpaces(element.get(0).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 19)
                                + ParseFileUtil.addTrailingSpaces(element.get(2).toString(), 12) + ParseFileUtil.addTrailingSpaces(accountType, 2)
                                + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addTrailingSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(result[0], 4)
                                + ParseFileUtil.addTrailingSpaces(result[1], 25) + "O" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + ymd.format(dataSdf.parse(element.get(7).toString()))
                                + hms.format(dataSdf.parse(element.get(8).toString())) + ymd.format(dataSdf.parse(element.get(9).toString())) + hms.format(dataSdf.parse(element.get(8).toString()))
                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString(), 8) + ParseFileUtil.addTrailingSpaces(element.get(12).toString(), 40) + "00" + "\n";

                        onUsWriter.write(singleEntry);
                    }
                } else {
                    onUsWriter.write(ParseFileUtil.addTrailingSpaces("", 203) + "\n");
                }

                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,rrn as transaction_serial_number,"
                        + "substring(from_account_number,3,2) as account_type,from_account_number as account_number,"
                        + "substring(from_account_number,1,2) as branch_code,amount as transaction_amount,tran_date as transaction_date,"
                        + "tran_time as transaction_time,in_coming_date as value_date,in_coming_date as value_time,terminal_id as atm_id,"
                        + "terminal_location as atm_location,original_system_trace_number from atm_reversal_transaction_parameter where  processing_code in('00') "
                        + "and tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        String acno = element.get(4).toString().trim();
                        String custname = getCustNameByAcno(acno);
                        String accountType = element.get(3).toString().trim();
                        accountType = getAcctCode(accountType);
                        // accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                        String tempAmount = element.get(6).toString();

                        List orgAmtList = em.createNativeQuery("select ifnull(amount,0),ifnull(transaction_fee,'C00000000') from atm_normal_transaction_parameter where system_trace_number='" + element.get(13).toString() + "' and tran_date='" + fileGenerationDt + "'").getResultList();
                        Vector amtVector = (Vector) orgAmtList.get(0);
                        BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                        String tranFee = amtVector.get(1).toString();
                        if (!tranFee.equalsIgnoreCase("C00000000")) {
                            orgAmount = orgAmount.add(new BigDecimal(tranFee.substring(1)).divide(new BigDecimal("100")));

                        }
                        BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                        if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                            tranAmount = orgAmount;
                        } else {
                            tranAmount = tranAmount;
                        }
                        tempAmount = tranAmount.toString();

                        BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
                        String singleEntry = ParseFileUtil.addTrailingSpaces(element.get(0).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 19)
                                + ParseFileUtil.addTrailingSpaces(element.get(2).toString(), 12) + ParseFileUtil.addTrailingSpaces(accountType, 2)
                                + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addTrailingSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(result[0], 4)
                                + ParseFileUtil.addTrailingSpaces(result[1], 25) + "O" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + ymd.format(dataSdf.parse(element.get(7).toString()))
                                + hms.format(dataSdf.parse(element.get(8).toString())) + ymd.format(dataSdf.parse(element.get(9).toString())) + hms.format(dataSdf.parse(element.get(8).toString()))
                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString(), 8) + ParseFileUtil.addTrailingSpaces(element.get(12).toString(), 40) + "00" + "\n";

                        onUsWriter.write(singleEntry);
                    }
                } else {
                    onUsWriter.write(ParseFileUtil.addTrailingSpaces("", 203) + "\n");
                }
                String onlyFileName = onUsFile.substring(onUsFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "O", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
            }
            if (atmStatus.equals("65")) {
                /**
                 * Acquirer File generation***
                 */
                sno = getSerialNo(fileGenerationDt, "A").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                acquireFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "A-" + sno + ".RC";
                acquireFile = writeFolderPath + acquireFile;
                acquirerWriter = new FileWriter(acquireFile);

                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,amount as transaction_amount,"
                        + "tran_date as transaction_date,tran_time as transaction_time,in_coming_date as value_date,in_coming_date as value_time,"
                        + "terminal_id as atm_id,reversal_indicator,original_system_trace_number from atm_off_us_transaction_parameter where  processing_code in('19') and reversal_indicator in(0,1) "
                        + "and tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        String tempAmount = element.get(2).toString();
                        String flag = "";
                        if (element.get(8).toString().equals("0")) {
                            flag = "D";
                        } else if (element.get(8).toString().equals("1")) {
                            flag = "C";

                            String orgStanNo = element.get(9).toString();
                            List orgAmtList = em.createNativeQuery("select ifnull(amount,0) from atm_off_us_transaction_parameter where system_trace_number='" + orgStanNo + "' and tran_date='" + fileGenerationDt + "'").getResultList();
                            Vector amtVector = (Vector) orgAmtList.get(0);
                            BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());

                            BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                            if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                                tranAmount = orgAmount;
                            } else {
                                tranAmount = tranAmount;
                            }
                            tempAmount = tranAmount.toString();
                        }

                        BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
                        String atmTerminal = element.get(7).toString();
                        System.out.println("Terminal Id is :" + atmTerminal);
                        String[] result = getBrCodeAndNameByAtmId(atmTerminal);

                        String singleEntry = ParseFileUtil.addTrailingSpaces(element.get(0).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 19)
                                + ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + ParseFileUtil.addTrailingZeros("", 2)
                                + ParseFileUtil.addTrailingZeros("", 18) + ParseFileUtil.addTrailingSpaces("", 26) + ParseFileUtil.addTrailingZeros(result[0], 4)
                                + ParseFileUtil.addTrailingSpaces(result[1], 25) + "A" + flag + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + ymd.format(dataSdf.parse(element.get(3).toString()))
                                + hms.format(dataSdf.parse(element.get(4).toString())) + ymd.format(dataSdf.parse(element.get(5).toString())) + hms.format(dataSdf.parse(element.get(4).toString()))
                                + ParseFileUtil.addTrailingSpaces(atmTerminal, 8) + ParseFileUtil.addTrailingSpaces(result[2], 40) + "00" + "\n";

                        acquirerWriter.write(singleEntry);
                    }
                } else {
                    acquirerWriter.write(ParseFileUtil.addTrailingSpaces("", 203) + "\n");
                }
                String onlyFileName = acquireFile.substring(acquireFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "A", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
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
        } finally {
            try {
                if (issuerWriter != null) {
                    issuerWriter.close();
                }
                if (acquirerWriter != null) {
                    acquirerWriter.close();
                }
                if (onUsWriter != null) {
                    onUsWriter.close();
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }

    @Override
    public List<String> generateSarvatraImpsReconFile(String fileGenerationDt, String generatedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hms = new SimpleDateFormat("HHmmss");
        SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        FileWriter issuerWriter = null, onUsWriter = null, acquirerWriter = null;
        List<String> files = new ArrayList<>();
        try {
            ut.begin();
//            List atmList = em.createNativeQuery("select code from parameterinfo_report where reportname='BANK-ATM-STATUS'").getResultList();
//            if (atmList.isEmpty()) {
//                throw new ApplicationException("Please fill data into parameterinfo report for BANK-ATM-STATUS");
//            }
//            Vector element = (Vector) atmList.get(0);
//            String atmStatus = element.get(0).toString();

            List atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            Vector element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            String sno = "", issuerFile = "", onUsFile = "", acquireFile = "", writeFolderPath = "";

            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/IMPS/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/IMPS/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }

            /**
             * Aquirer File generation***
             */
            sno = getSerialNo(fileGenerationDt, "A").toString();
            if (sno.length() < 2) {
                sno = "0" + sno;
            }
            acquireFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "A-" + sno + ".RC";
            acquireFile = writeFolderPath + acquireFile;
            acquirerWriter = new FileWriter(acquireFile);

            atmList = em.createNativeQuery("select  RRN,substring(from_account_number,3,2) as actcode,from_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as custname,"
                    + "substring(from_account_number,1,2) as brncode,bm.branchname,remitter_nbin,remitter_mob_no,bm.ifsc_code as ifsc,"
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as aadhar, "
                    + "amount,txn_type ,BEN_NBIN,BEN_MOB_NO,BEN_IFSC_CODE,BEN_ACCOUNT,RESERVED_2, "
                    + "date_format(tran_date,'%y%m%d') as tran_date, time_format(cast(TRAN_TIME as time), '%H%i%s') as time,date_format(in_coming_date,'%y%m%d') as value_date, "
                    + "time_format(cast(update_TIME as time), '%H%i%s') as time,deli_channel,'' as atm_id"
                    + " from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm where  REVERSAL_INDICATOR='0' and processing_code='42'"
                    + " and ci.acno = from_account_number and ci.custid = cast(cm.customerid as unsigned) and cast(substring(acno,1,2) as unsigned)= bm.brncode "
                    + " and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);

                    String tempAmount = element.get(10).toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                    String payeeAcno = "", payeeIFSC = "", remark = "";
                    if (!element.get(16).toString().equals("")) {
                        String[] reserved = element.get(16).toString().split("/");
                        payeeAcno = reserved[3];
                        payeeIFSC = reserved[4];
                        remark = reserved[5];
                    }
                    //    0/  1/   2              /        3    /   4      / 5    /  6
                    //IMPS/RRN/Channel Payee Name/Payee Acct No/Payee IFSC/Remark/Payee Bank
                    String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "A" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(12).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 10) + ParseFileUtil.addSuffixSpaces(payeeIFSC, 11)
                            + ParseFileUtil.addTrailingZeros(payeeAcno, 18) + ParseFileUtil.addTrailingZeros("", 12) + element.get(17).toString() + element.get(18).toString() 
                            + element.get(19).toString() + element.get(20).toString() + ParseFileUtil.addSuffixSpaces(element.get(21).toString(), 3) 
                            + ParseFileUtil.addSuffixSpaces(element.get(22).toString(), 8) + ParseFileUtil.addSuffixSpaces(remark, 15) + "00" + "\n";

                    acquirerWriter.write(singleEntry);
                }
            } else {
                acquirerWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }

            atmList = em.createNativeQuery("select  RRN,substring(from_account_number,3,2) as actcode,from_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as custname,"
                    + "substring(from_account_number,1,2) as brncode,bm.branchname,remitter_nbin,remitter_mob_no,bm.ifsc_code as ifsc,"
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as aadhar, "
                    + "amount,txn_type ,BEN_NBIN,BEN_MOB_NO,BEN_IFSC_CODE,BEN_ACCOUNT,RESERVED_2, "
                    + "date_format(tran_date,'%y%m%d') as tran_date, time_format(cast(TRAN_TIME as time), '%H%i%s') as time,date_format(in_coming_date,'%y%m%d') as value_date, "
                    + "time_format(cast(update_TIME as time), '%H%i%s') as time,deli_channel,'' as atm_id"
                    + " from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm where  REVERSAL_INDICATOR='1' and processing_code='42'"
                    + " and ci.acno = from_account_number and ci.custid = cast(cm.customerid as unsigned) and cast(substring(acno,1,2) as unsigned)= bm.brncode "
                    + " and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);

                    String tempAmount = element.get(10).toString();
                    String payeeAcno = "", payeeIFSC = "", remark = "";
                    if (!element.get(16).toString().equals("")) {
                        String[] reserved = element.get(16).toString().split("/");
                        payeeAcno = reserved[3];
                        payeeIFSC = reserved[4];
                        remark = reserved[5];
                    }

                    List orgAmtList = em.createNativeQuery("select ifnull(amount,0) from imps_txn_parameter where rrn='" + element.get(1).toString()
                            + "' and REVERSAL_INDICATOR='0' and tran_date='" + fileGenerationDt + "'").getResultList();
                    Vector amtVector = (Vector) orgAmtList.get(0);
                    BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                    BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                    if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                        tranAmount = orgAmount;
                    }
                    tempAmount = tranAmount.toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                     String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "A" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(12).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 10) + ParseFileUtil.addSuffixSpaces(payeeIFSC, 11)
                            + ParseFileUtil.addTrailingZeros(payeeAcno, 18) + ParseFileUtil.addTrailingZeros("", 12) + element.get(17).toString() + element.get(18).toString() 
                            + element.get(19).toString() + element.get(20).toString() + ParseFileUtil.addSuffixSpaces(element.get(21).toString(), 3) 
                            + ParseFileUtil.addSuffixSpaces(element.get(22).toString(), 8) + ParseFileUtil.addSuffixSpaces(remark, 15) + "00" + "\n";
                    acquirerWriter.write(singleEntry);
                }
            } else {
                acquirerWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }
            String onlyFileName = acquireFile.substring(acquireFile.lastIndexOf("/") + 1);
            if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "A", generatedBy, fileGenerationDt).equals("true")) {
                files.add(onlyFileName);
            }

            /**
             * Issuer File generation***
             */
            sno = getSerialNo(fileGenerationDt, "I").toString();
            if (sno.length() < 2) {
                sno = "0" + sno;
            }
            issuerFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".RC";
            issuerFile = writeFolderPath + issuerFile;
            issuerWriter = new FileWriter(issuerFile);

            atmList = em.createNativeQuery("select  RRN,substring(to_account_number,3,2),to_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as custname,"
                    + "substring(to_account_number,1,2) as brncode,bm.branchname,BEN_NBIN,BEN_MOB_NO,BEN_IFSC_CODE,"
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a "
                    + "where a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as aadhar, "
                    + "amount,txn_type,RESERVED_2, REMITTER_NBIN,date_format(tran_date,'%y%m%d'), time_format(cast(TRAN_TIME as time), '%H%i%s'),"
                    + "date_format(in_coming_date,'%y%m%d'), time_format(cast(update_TIME as time), '%H%i%s') ,deli_channel,'' as atm_id "
                    + "from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm where  REVERSAL_INDICATOR='0' and "
                    + "processing_code='43' and ci.acno = to_account_number and ci.custid = cast(cm.customerid as unsigned) and cast(substring(acno,1,2) "
                    + "as unsigned)= bm.brncode and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);

                    String tempAmount = element.get(10).toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                    String payerAcno = "", payerMobile = "", remark = "";
                    if (!element.get(12).toString().equals("")) {
                        String[] reserved = element.get(12).toString().split("/");
                        payerAcno = reserved[4];
                        payerMobile = reserved[3];
                        remark = reserved[5];
                    }
                    
                    //    0/  1/   2      /        3      /   4         / 5    /  6
                    //IMPS/RRN/Payer Name/Payer Mobile No/Payer Acct No/Remark/Payer Bank
                    String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "I" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(13).toString()
                            + ParseFileUtil.addTrailingZeros(payerMobile, 10) + ParseFileUtil.addSuffixSpaces("", 11)
                            + ParseFileUtil.addTrailingZeros(payerAcno, 18) + ParseFileUtil.addTrailingZeros("", 12) + element.get(14).toString() 
                            + element.get(15).toString() + element.get(16).toString() + element.get(17).toString() + ParseFileUtil.addTrailingSpaces(element.get(18).toString(), 3) 
                            + ParseFileUtil.addTrailingSpaces(element.get(19).toString(), 8) + ParseFileUtil.addTrailingSpaces(remark, 15) + "00" + "\n";

                    issuerWriter.write(singleEntry);
                }
            } else {
                issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }

            atmList = em.createNativeQuery("select  RRN,substring(to_account_number,3,2),to_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as custname,"
                    + "substring(to_account_number,1,2) as brncode,bm.branchname,BEN_NBIN,BEN_MOB_NO,BEN_IFSC_CODE,"
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a "
                    + "where a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as aadhar, "
                    + "amount,txn_type ,RESERVED_2, date_format(tran_date,'%y%m%d'), time_format(cast(TRAN_TIME as time), '%H%i%s'),"
                    + "date_format(in_coming_date,'%y%m%d'), time_format(cast(update_TIME as time), '%H%i%s'),deli_channel,'' as atm_id "
                    + "from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm where REVERSAL_INDICATOR='1' and "
                    + "processing_code='43' and ci.acno = to_account_number and ci.custid = cast(cm.customerid as unsigned) and cast(substring(acno,1,2) "
                    + "as unsigned)= bm.brncode and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);

                    String tempAmount = element.get(11).toString();
                    String payerAcno = "", payerMobile = "", remark = "", payeeIFSC = "";
                    if (!element.get(12).toString().equals("")) {
                        String[] reserved = element.get(12).toString().split("/");
                        payerAcno = reserved[4];
                        payerMobile = reserved[3];
                        remark = reserved[5];
                    }

                    List orgAmtList = em.createNativeQuery("select ifnull(amount,0) from imps_txn_parameter where rrn='" + element.get(1).toString()
                            + "' and REVERSAL_INDICATOR='0' and tran_date='" + fileGenerationDt + "'").getResultList();
                    Vector amtVector = (Vector) orgAmtList.get(0);
                    BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                    BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                    if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                        tranAmount = orgAmount;
                    }
                    tempAmount = tranAmount.toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                    String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "I" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(13).toString()
                            + ParseFileUtil.addTrailingZeros(payerMobile, 10) + ParseFileUtil.addSuffixSpaces(payeeIFSC, 11)
                            + ParseFileUtil.addTrailingZeros(payerAcno, 18) + ParseFileUtil.addTrailingZeros("", 12) + element.get(14).toString() 
                            + element.get(15).toString() + element.get(16).toString() + element.get(17).toString() + ParseFileUtil.addTrailingSpaces(element.get(18).toString(), 3) 
                            + ParseFileUtil.addTrailingSpaces(element.get(19).toString(), 8) + ParseFileUtil.addTrailingSpaces(remark, 15) + "00" + "\n";
                    issuerWriter.write(singleEntry);
                }
            } else {
                issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }
            onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
            if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "I", generatedBy, fileGenerationDt).equals("true")) {
                files.add(onlyFileName);
            }

            /**
             * On-Us File generation***
             */
            sno = getSerialNo(fileGenerationDt, "O").toString();
            if (sno.length() < 2) {
                sno = "0" + sno;
            }
            onUsFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "O-" + sno + ".RC";
            onUsFile = writeFolderPath + onUsFile;
            onUsWriter = new FileWriter(onUsFile);

            atmList = em.createNativeQuery("select  RRN,substring(from_account_number,3,2),from_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as remi_custname, "
                    + "substring(from_account_number,1,2),bm.branchname,remitter_nbin,remitter_mob_no,bm.ifsc_code, "
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as remi_aadhar, "
                    + "amount,txn_type as txn_code, substring(to_account_number,3,2) as ben_actcode,to_account_number as ben_acno, "
                    + "concat(cm1.custname,if(cm1.middle_name = '',' ',concat(' ', cm1.middle_name, ' ')),cm1.last_name) as ben_custname, "
                    + "substring(to_account_number,1,2),bm1.branchname,ben_nbin,ben_mob_no,bm1.ifsc_code, "
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as ben_aadhar, RESERVED_2, date_format(tran_date,'%y%m%d'), "
                    + "time_format(cast(TRAN_TIME as time), '%H%i%s'),date_format(in_coming_date,'%y%m%d'), time_format(cast(update_TIME as time), '%H%i%s'),deli_channel,'' as atm_id,REMARKS "
                    + "from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm,customerid ci1, cbs_customer_master_detail cm1,branchmaster bm1 "
                    + "where  REVERSAL_INDICATOR='0' and processing_code='41' and ci.acno = from_account_number and ci.custid = cast(cm.customerid as unsigned) "
                    + "and cast(substring(ci.acno,1,2) as unsigned)= bm.brncode and ci1.acno = to_account_number and ci1.custid = cast(cm1.customerid as unsigned) "
                    + "and cast(substring(ci1.acno,1,2) as unsigned)= bm1.brncode and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);

                    String tempAmount = element.get(10).toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
  
                    //1. RRN(12)-left padded zero, 2.Account Type(2),3.Account Number(18)-left padded zero,4.Account Holder Name(26)-right side space
                    //5.Branch Code(4)-left padded zero,6.Branch Name(25)-right side space,7.NBIN(4),8.Mobile Number(10)- 0000000000,9.IFSC(11)-right side space
                    //10.Aadhaar Number(12)-000000000000,11.Transaction Type(1)-I/O/A,12.Dr/Cr Flag(1)-C/D,13.Transaction Amount(15)-left padded zero,14.Transaction Code(2)
                    //15.Remitter/Bene NBIN(4),16.Remitter/Bene Mobile(10)-0000000000,17.Remitter/Bene IFSC(11)-right side space,18.Remitter/Bene Account(18)-left padded zero 
                    //19.Remitter/Bene Aadhaar(UID)(12)-000000000000,20.Transaction Date,21.Transaction Time,22.Value Date,23.Value Time,24.Original Channel(3)		
                    //25.ATM ID(8)-right side space,26.Remark(15)-right side space,27.Response Code(00) 

                     String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "O" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(17).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(18).toString(), 10) + ParseFileUtil.addSuffixSpaces(element.get(19).toString(), 11)
                            + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 18) + ParseFileUtil.addTrailingZeros(element.get(20).toString(), 12) 
                            + element.get(22).toString() + element.get(23).toString() + element.get(24).toString() + element.get(25).toString() 
                            + ParseFileUtil.addTrailingSpaces(element.get(26).toString(), 3) + ParseFileUtil.addTrailingSpaces(element.get(27).toString(), 8) 
                            + ParseFileUtil.addTrailingSpaces(element.get(28).toString(), 15) + "00" + "\n";
                    
                    accountType = element.get(12).toString().trim();
                    accountType = getAcctCode(accountType);
                    
                     singleEntry = singleEntry + ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(14).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(15).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(16).toString(), 25) + element.get(17).toString() + ParseFileUtil.addTrailingZeros(element.get(18).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(19).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(20).toString(), 12)
                            + "O" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(6).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10) + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11)
                            + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12) 
                            + element.get(22).toString() + element.get(23).toString() + element.get(24).toString() + element.get(25).toString() 
                            + ParseFileUtil.addTrailingSpaces(element.get(26).toString(), 3) + ParseFileUtil.addTrailingSpaces(element.get(27).toString(), 8) 
                            + ParseFileUtil.addTrailingSpaces(element.get(28).toString(), 15) + "00" + "\n";
                             
                     onUsWriter.write(singleEntry);
                    
                    
                }
            } else {
                onUsWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }

            atmList = em.createNativeQuery("select  RRN,substring(from_account_number,3,2),from_account_number, "
                    + "concat(cm.custname,if(cm.middle_name = '',' ',concat(' ', cm.middle_name, ' ')),cm.last_name) as remi_custname, "
                    + "substring(from_account_number,1,2),bm.branchname,remitter_nbin,remitter_mob_no,bm.ifsc_code, "
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as remi_aadhar, "
                    + "amount,txn_type as txn_code, substring(to_account_number,3,2) as ben_actcode,to_account_number as ben_acno, "
                    + "concat(cm1.custname,if(cm1.middle_name = '',' ',concat(' ', cm1.middle_name, ' ')),cm1.last_name) as ben_custname, "
                    + "substring(to_account_number,1,2),bm1.branchname,ben_nbin,ben_mob_no,bm1.ifsc_code, "
                    + "ifnull((select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = cm.customerid ),'')  as ben_aadhar, RESERVED_2, date_format(tran_date,'%y%m%d'), "
                    + "time_format(cast(TRAN_TIME as time), '%H%i%s'),date_format(in_coming_date,'%y%m%d'), time_format(cast(update_TIME as time), '%H%i%s'),deli_channel,'' as atm_id,REMARKS "
                    + "from imps_txn_parameter, customerid ci, cbs_customer_master_detail cm, branchmaster bm,customerid ci1, cbs_customer_master_detail cm1,branchmaster bm1 "
                    + "where  REVERSAL_INDICATOR='1' and processing_code='41' and ci.acno = from_account_number and ci.custid = cast(cm.customerid as unsigned) "
                    + "and cast(substring(ci.acno,1,2) as unsigned)= bm.brncode and ci1.acno = to_account_number and ci1.custid = cast(cm1.customerid as unsigned) "
                    + "and cast(substring(ci1.acno,1,2) as unsigned)= bm1.brncode and IN_PROCESS_FLAG = 'S' and tran_date='" + fileGenerationDt + "' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String accountType = element.get(1).toString().trim();
                    accountType = getAcctCode(accountType);
                    
                    String tempAmount = element.get(10).toString();

                    List orgAmtList = em.createNativeQuery("select ifnull(amount,0)from atm_normal_transaction_parameter where rrn='" 
                            + element.get(1).toString() + "' and tran_date='" + fileGenerationDt + "'").getResultList();
                    Vector amtVector = (Vector) orgAmtList.get(0);
                    BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                    BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                    if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                        tranAmount = orgAmount;
                    }
                    tempAmount = tranAmount.toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
                    
                  String singleEntry = ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(3).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(4).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(5).toString(), 25) + element.get(6).toString() + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12)
                            + "O" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(17).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(18).toString(), 10) + ParseFileUtil.addSuffixSpaces(element.get(19).toString(), 11)
                            + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 18) + ParseFileUtil.addTrailingZeros(element.get(20).toString(), 12) 
                            + element.get(22).toString() + element.get(23).toString() + element.get(24).toString() + element.get(25).toString() 
                            + ParseFileUtil.addTrailingSpaces(element.get(26).toString(), 3) + ParseFileUtil.addTrailingSpaces(element.get(27).toString(), 8) 
                            + ParseFileUtil.addTrailingSpaces(element.get(28).toString(), 15) + "00" + "\n";
                    
                    accountType = element.get(12).toString().trim();
                    accountType = getAcctCode(accountType);
                    
                     singleEntry = singleEntry + ParseFileUtil.addTrailingZeros(element.get(0).toString(), 12) + accountType + ParseFileUtil.addTrailingZeros(element.get(13).toString(), 18)
                            + ParseFileUtil.addSuffixSpaces(element.get(14).toString(), 26) + ParseFileUtil.addTrailingZeros(element.get(15).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces(element.get(16).toString(), 25) + element.get(17).toString() + ParseFileUtil.addTrailingZeros(element.get(18).toString(), 10)
                            + ParseFileUtil.addSuffixSpaces(element.get(19).toString(), 11) + ParseFileUtil.addTrailingZeros(element.get(20).toString(), 12)
                            + "O" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15) + element.get(11).toString() + element.get(6).toString()
                            + ParseFileUtil.addTrailingZeros(element.get(7).toString(), 10) + ParseFileUtil.addSuffixSpaces(element.get(8).toString(), 11)
                            + ParseFileUtil.addTrailingZeros(element.get(2).toString(), 18) + ParseFileUtil.addTrailingZeros(element.get(9).toString(), 12) 
                            + element.get(22).toString() + element.get(23).toString() + element.get(24).toString() + element.get(25).toString() 
                            + ParseFileUtil.addTrailingSpaces(element.get(26).toString(), 3) + ParseFileUtil.addTrailingSpaces(element.get(27).toString(), 8) 
                            + ParseFileUtil.addTrailingSpaces(element.get(28).toString(), 15) + "00" + "\n";
                    onUsWriter.write(singleEntry);
                }
            } else {
                onUsWriter.write(ParseFileUtil.addTrailingSpaces("", 250) + "\n");
            }
            onlyFileName = onUsFile.substring(onUsFile.lastIndexOf("/") + 1);
            if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "O", generatedBy, fileGenerationDt).equals("true")) {
                files.add(onlyFileName);
            }
           
            ut.commit();
        } catch (ApplicationException | IOException | IllegalStateException | SecurityException | ParseException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (ApplicationException | IllegalStateException | SecurityException | SystemException e) {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            try {
                if (issuerWriter != null) {
                    issuerWriter.close();
                }
                if (acquirerWriter != null) {
                    acquirerWriter.close();
                }
                if (onUsWriter != null) {
                    onUsWriter.close();
                }
            } catch (IOException ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }

    public String getAcctCode(String acctCode) throws ApplicationException {
        List acList = new ArrayList();
        String acCode = "10";
        try {
            acList = em.createNativeQuery("select * from cbs_parameterinfo where NAME in('SRV-ATM-FILE-SAVING-ACCODE') and code like '%" + acctCode + "%'").getResultList();
            if (!acList.isEmpty()) {
                acCode = "10";
            }
            acList = em.createNativeQuery("select * from cbs_parameterinfo where NAME in('SRV-ATM-FILE-CURRENT-ACCODE') and code like '%" + acctCode + "%'").getResultList();
            if (!acList.isEmpty()) {
                acCode = "20";
            }
            acList = em.createNativeQuery("select * from cbs_parameterinfo where NAME in('SRV-ATM-FILE-CC-ACCODE') and code like '%" + acctCode + "%'").getResultList();
            if (!acList.isEmpty()) {
                acCode = "30";
            }
            return acCode;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    // Added by Manish kumar

    public List getReconFileType() throws ApplicationException {
        List reconFileTypeList = new ArrayList();
        try {
            reconFileTypeList = em.createNativeQuery("select REF_CODE, REF_DESC from cbs_ref_rec_type where ref_rec_no ='099'").getResultList();
            if (reconFileTypeList.isEmpty()) {
                throw new ApplicationException("Please fill data in cbs_ref_rec_type table for Recon File Type !");
            }
            return reconFileTypeList;
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<String> generatePosAtmReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat ymd = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat hms = new SimpleDateFormat("HHmmss");
        SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        FileWriter issuerWriter = null;
        List<String> files = new ArrayList<String>();
        try {
            ut.begin();
            List atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            Vector element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            String sno = "", issuerFile = "", writeFolderPath = "";
            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }
            sno = getSerialNo(fileGenerationDt, "R").toString();
            if (sno.length() < 2) {
                sno = "0" + sno;
            }
            issuerFile = "RGCS-" + bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".RC";
            issuerFile = writeFolderPath + issuerFile;
            issuerWriter = new FileWriter(issuerFile);

            atmList = em.createNativeQuery("select processing_code as transaction_type,system_trace_number as stan_number,"
                    + "ifnull(card_number,'') as card_no,ifnull(rrn,'') as rrn,substring(from_account_number,3,2) as account_type,"
                    + "from_account_number as account_number,substring(from_account_number,1,2) as branch_code,ifnull(amount,0) as "
                    + "transaction_amount,tran_date as transaction_date,tran_time as transaction_time,in_coming_date as value_date,"
                    + "ifnull(acquiring_institution_code,'') as acquirer_institution_code,terminal_id as terminal_id,"
                    + "ifnull(terminal_location,'') as terminal_location from atm_normal_transaction_parameter where "
                    + "processing_code in('44','45','46','49') and tran_date='" + fileGenerationDt + "' and "
                    + "in_process_flag='S' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String acno = element.get(5).toString().trim();
                    String custname = getCustNameByAcno(acno);
                    String accountType = element.get(4).toString().trim();
                    accountType = getAcctCode(accountType);
                    //accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
//                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                    String tempAmount = element.get(7).toString();
                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));
                    String singleEntry = element.get(0).toString() + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 6)
                            + ParseFileUtil.addSuffixSpaces(element.get(2).toString(), 19) + ParseFileUtil.addTrailingZeros(element.get(3).toString(), 12) + ParseFileUtil.addTrailingZeros(accountType, 2)
                            + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addSuffixSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(element.get(6).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces("", 25) + "I" + "D" + ParseFileUtil.addTrailingZeros(amount.toString(), 15)
                            + sdf.format(dataSdf.parse(element.get(8).toString())) + hms.format(dataSdf.parse(element.get(9).toString())) + sdf.format(dataSdf.parse(element.get(10).toString())) + hms.format(dataSdf.parse(element.get(9).toString()))
                            + ParseFileUtil.addTrailingZeros(element.get(11).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(12).toString(), 8) + ParseFileUtil.addSuffixSpaces(element.get(13).toString(), 40) + "00" + "\n";

                    issuerWriter.write(singleEntry);
                }
            } else {
                issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 215) + "\n");
            }

            atmList = em.createNativeQuery("select processing_code as transaction_type,system_trace_number as stan_number,"
                    + "ifnull(card_number,'') as card_no,ifnull(rrn,'') as rrn,substring(from_account_number,3,2) as account_type,"
                    + "from_account_number as account_number,substring(from_account_number,1,2) as branch_code,ifnull(amount,0) as "
                    + "transaction_amount,tran_date as transaction_date,tran_time as transaction_time,in_coming_date as value_date,"
                    + "ifnull(acquiring_institution_code,'') as acquirer_institution_code,terminal_id as terminal_id,"
                    + "ifnull(terminal_location,'') as terminal_location,original_system_trace_number from "
                    + "atm_reversal_transaction_parameter where processing_code in('44','45','46','49') and "
                    + "tran_date='" + fileGenerationDt + "' and in_process_flag='S' order by tran_time").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    String acno = element.get(5).toString().trim();
                    String custname = getCustNameByAcno(acno);
                    String accountType = element.get(4).toString().trim();
                    accountType = getAcctCode(accountType);
                    // accountType = (accountType.equals("10") || accountType.equals("20") || accountType.equals("30")) ? accountType : "10"; //Only Saving-10,Current-20,CC-30 allowed
//                        String[] result = getBrCodeAndNameByAtmId(element.get(11).toString());
                    String tempAmount = element.get(7).toString();

                    List orgAmtList = em.createNativeQuery("select ifnull(amount,0),ifnull(transaction_fee,'C00000000') from atm_normal_transaction_parameter where system_trace_number='" + element.get(14).toString() + "' and tran_date='" + fileGenerationDt + "'").getResultList();
                    Vector amtVector = (Vector) orgAmtList.get(0);
                    BigDecimal orgAmount = new BigDecimal(amtVector.get(0).toString());
                    String tranFee = amtVector.get(1).toString();
                    if (!tranFee.equalsIgnoreCase("C00000000")) {
                        orgAmount = orgAmount.add(new BigDecimal(tranFee.substring(1)).divide(new BigDecimal("100")));

                    }
                    BigDecimal tranAmount = orgAmount.subtract(new BigDecimal(tempAmount));
                    if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
                        tranAmount = orgAmount;
                    } else {
                        tranAmount = tranAmount;
                    }
                    tempAmount = tranAmount.toString();

                    BigDecimal amount = new BigDecimal(tempAmount.substring(0, tempAmount.indexOf("."))).multiply(new BigDecimal("100"));

                    String singleEntry = element.get(0).toString() + ParseFileUtil.addSuffixSpaces(element.get(1).toString(), 6)
                            + ParseFileUtil.addSuffixSpaces(element.get(2).toString(), 19) + ParseFileUtil.addTrailingSpaces(element.get(3).toString(), 12) + ParseFileUtil.addTrailingZeros(accountType, 2)
                            + ParseFileUtil.addTrailingZeros(acno, 18) + ParseFileUtil.addSuffixSpaces(custname, 26) + ParseFileUtil.addTrailingZeros(element.get(6).toString(), 4)
                            + ParseFileUtil.addSuffixSpaces("", 25) + "I" + "C" + ParseFileUtil.addTrailingZeros(amount.toString(), 15)
                            + sdf.format(dataSdf.parse(element.get(8).toString())) + hms.format(dataSdf.parse(element.get(9).toString())) + sdf.format(dataSdf.parse(element.get(10).toString())) + hms.format(dataSdf.parse(element.get(9).toString()))
                            + ParseFileUtil.addTrailingZeros(element.get(11).toString(), 6) + ParseFileUtil.addSuffixSpaces(element.get(12).toString(), 8) + ParseFileUtil.addSuffixSpaces(element.get(13).toString(), 40) + "00" + "\n";

                    issuerWriter.write(singleEntry);
                }
            } else {
                issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 215) + "\n");
            }
            String onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
            if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "R", generatedBy, fileGenerationDt).equals("true")) {
                files.add(onlyFileName);
            }

            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            try {
                if (issuerWriter != null) {
                    issuerWriter.close();
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }

    @Override
    public List<String> generatePosAtmFinacusFiles(String fileGenerationDt, String generatedBy) throws ApplicationException {
        List<String> files = new ArrayList<>();
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        FileWriter posWriter = null;
        try {
            ut.begin();
            List atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            Vector element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            atmList = em.createNativeQuery("select code from cbs_parameterinfo where name in('ACQUIRER-CODE');").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define acquirer code");
            }
            element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define acquirer code in cbs parameterinfo.");
            }

            String sno = "", posFile = "", writeFolderPath = "";
            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }

            sno = getSerialNo(fileGenerationDt, "P").toString();
            if (sno.length() < 2) {
                sno = "0" + sno;
            }
            posFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "P-" + sno + ".csv";
            posFile = writeFolderPath + posFile;
            posWriter = new FileWriter(posFile);

            atmList = em.createNativeQuery("select stan_number,card_no,transaction_serial_number,account_type,account_number,"
                    + "branch_code,transaction_amount,transaction_date,transaction_time,value_date,atm_id,atm_location,"
                    + "batch_code,user_id,acquirerId,in_process_flag,remarks,tran_time,txn_fee,rev_br_code,rev_entry_date,"
                    + "rev_posting_date,rev_batch_code,rev_maker_time,rev_amount from "
                    + "(select m.processing_code,m.system_trace_number as stan_number,m.card_number as card_no,m.rrn as "
                    + "transaction_serial_number,substring(m.from_account_number,3,2) as account_type,m.from_account_number "
                    + "as account_number,substring(m.from_account_number,1,2) as branch_code,m.amount as transaction_amount,"
                    + "date_format(m.tran_date,'%Y%m%d') as transaction_date,concat(extract(hour from m.tran_time),"
                    + "extract(minute from m.tran_time),extract(second from m.tran_time)) as transaction_time,"
                    + "date_format(m.in_coming_date,'%Y%m%d') as value_date,m.terminal_id as atm_id,m.terminal_location as "
                    + "atm_location,ifnull(m.trsno,0) as batch_code,ifnull(m.authorization_id,'') as user_id,"
                    + "ifnull(m.acquiring_institution_code,'') as acquirerId,m.in_process_flag,(case when m.in_process_flag='S' "
                    + "then 'POS/ECOM Withdrawal Success' else 'POS/ECOM Withdrawal Fail' end) as remarks,m.tran_time,m.transaction_fee "
                    + "as txn_fee,ifnull(substring(r.from_account_number,1,2),'') as rev_br_code,ifnull(date_format(r.tran_date,'%Y%m%d'),'') "
                    + "as rev_entry_date,ifnull(date_format(r.in_coming_date,'%Y%m%d'),'') as rev_posting_date,ifnull(r.trsno,0) as "
                    + "rev_batch_code,ifnull(concat(extract(hour from r.tran_time),extract(minute from r.tran_time),"
                    + "extract(second from r.tran_time)),'') as rev_maker_time,ifnull(r.amount,0.00) as rev_amount from "
                    + "atm_normal_transaction_parameter m left join atm_reversal_transaction_parameter r on "
                    + "m.system_trace_number=r.original_system_trace_number and m.in_process_flag=r.in_process_flag and "
                    + "m.tran_date=r.tran_date and m.processing_code=r.processing_code and m.processing_code in('44','45','46','49') and "
                    + "m.in_process_flag='S' and m.tran_date='" + fileGenerationDt + "' order by m.tran_time) a "
                    + "where processing_code in('44','45','46','49') and transaction_date='" + fileGenerationDt + "'").getResultList();
            if (!atmList.isEmpty()) {
                for (int i = 0; i < atmList.size(); i++) {
                    element = (Vector) atmList.get(i);
                    BigDecimal amount = new BigDecimal(element.get(6).toString().trim());
                    BigDecimal txnFee = new BigDecimal(element.get(18).toString().trim().substring(1));

                    String batchCode = new BigDecimal(element.get(12).toString().trim()).toString();
                    if (batchCode.contains(".")) {
                        batchCode = batchCode.substring(0, batchCode.indexOf("."));
                    }

                    String revBatchCode = "", revEntryDt = "", revPostingDt = "", revNarration = "", revMaker = "", revAmount = "";
                    String revBrCode = element.get(19).toString().trim();
                    if (!revBrCode.equals("")) {
                        revBatchCode = new BigDecimal(element.get(22).toString().trim()).toString();
                        if (revBatchCode.contains(".")) {
                            revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
                        }
                        BigDecimal revComingAmount = new BigDecimal(element.get(24).toString().trim());
                        revAmount = (amount.add(txnFee)).subtract(revComingAmount).toString();

                        revEntryDt = element.get(20).toString().trim();
                        revPostingDt = element.get(21).toString().trim();
                        revNarration = "POS/ECOM Reversal Success";
//                        revMaker = "System";
                        revMaker = "";
                    }

                    String singleEntry = element.get(14).toString().trim() + ","
                            + element.get(2).toString().trim() + ","
                            + element.get(0).toString().trim() + ","
                            + element.get(5).toString().trim() + ","
                            + element.get(4).toString().trim() + ","
                            + ","
                            + element.get(10).toString().trim() + ","
                            + element.get(11).toString().trim().replaceAll("[\\W_]", " ") + ","
                            + amount.toString().trim() + ","
                            + element.get(1).toString().trim() + ","
                            + ","
                            + element.get(7).toString().trim() + ","
                            + element.get(9).toString().trim() + ","
                            + batchCode + ","
                            + element.get(16).toString().trim() + ","
                            + revBrCode.trim() + ","
                            + revEntryDt + ","
                            + revPostingDt + ","
                            + revNarration + ","
                            + revBatchCode + ","
                            + element.get(7).toString() + ","
                            + element.get(8).toString().trim() + ","
                            + revMaker + ","
                            + element.get(20).toString().trim() + ","
                            + element.get(23).toString().trim() + ","
                            + revAmount.toString().trim() + ","
                            + "4," + "\r\n";

                    posWriter.write(singleEntry);
                }
            } else {
                posWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
            }

            String onlyFileName = posFile.substring(posFile.lastIndexOf("/") + 1);
            if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "P", generatedBy, fileGenerationDt).equals("true")) {
                files.add(onlyFileName);
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            try {
                if (posWriter != null) {
                    posWriter.close();
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }

//    @Override
//    public List<String> generateImpsOwTxnFile(String fileGenerationDt, String fileGenBy) throws ApplicationException {
//        List<String> files = new ArrayList<>();
//        UserTransaction ut = context.getUserTransaction();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        FileWriter impsOwTxnWriter = null;
//        try {
//            List atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
//            if (atmList.isEmpty()) {
//                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
//            }
//            Vector element = (Vector) atmList.get(0);
//            if (element.get(0) == null || element.get(0).toString().equals("")) {
//                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
//            }
//            String bankCode = element.get(0).toString();
//
//            String impsTxnFile = "", writeFolderPath = "";
//            File writeFolder = null;
//            String osName = System.getProperty("os.name");
//            if (osName.equalsIgnoreCase("Linux")) {
//                writeFolderPath = "/install/ATM/";
//                writeFolder = new File(writeFolderPath);
//                if (!writeFolder.exists()) {
//                    writeFolder.mkdir();
//                }
//            } else {
//                writeFolderPath = "C:/ATM/";
//                writeFolder = new File(writeFolderPath);
//                if (!writeFolder.exists()) {
//                    writeFolder.mkdir();
//                }
//            }
//
//            impsTxnFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "IMPSOW" + ".csv";
//            impsTxnFile = writeFolderPath + impsTxnFile;
//            impsOwTxnWriter = new FileWriter(impsTxnFile);
//
//            String header = "EntryDt,MakerTime,PostingDt,TransactionType,RRN,DrBrCd,DrFlag,DrBatchCd,DrSetNo,DrScrollNo,DrAcctNo,"
//                    + "CrBrCd,CrFlag,CrBatchCd,CrSetNo,CrScrollNo,CrAcctNo,TrnAmt,Narration,track,pintrack,Status" + "\r\n";
//
//            impsOwTxnWriter.write(header);
//
//            List impsList = em.createNativeQuery("select date_format(dt,'%d-%b-%y') as dt,time_format(cast(trantime as time), '%H%i%s') "
//                    + "as time,uniquecustomerrefno,debitaccountno,trs_no,creditaccountno,txnamt,status,(case when status='S' "
//                    + "then 'Paid' when status='U' then 'Reversed' else 'Failed' end) as remark  from neft_ow_details "
//                    + "where paymentType='IMPS' and dt='" + fileGenerationDt + "' and auth='Y'").getResultList();
//            if (!impsList.isEmpty()) {
//                for (int i = 0; i < impsList.size(); i++) {
//                    Vector vec = (Vector) impsList.get(i);
//                    String batchCode = vec.get(4).toString().trim();
//                    if (batchCode.contains(".")) {
//                        batchCode = batchCode.substring(0, batchCode.indexOf("."));
//                    }
//
//                    String singleEntry = vec.get(0).toString().trim() + ","
//                            + vec.get(1).toString().trim() + ","
//                            + vec.get(0).toString().trim() + ","
//                            + "INTER" + ","
//                            + vec.get(2).toString().trim() + ","
//                            + vec.get(3).toString().substring(0, 2).trim() + ","
//                            + "D" + ","
//                            + batchCode.trim() + ","
//                            + ","
//                            + ","
//                            + vec.get(3).toString().trim() + ","
//                            + ","
//                            + "C,"
//                            + ","
//                            + ","
//                            + ","
//                            + vec.get(5).toString().trim() + ","
//                            + vec.get(6).toString().trim() + ","
//                            + vec.get(8).toString().trim() + ","
//                            + ","
//                            + ",1" + "\r\n";
//
//                    impsOwTxnWriter.write(singleEntry);
//
//                }
//            } else {
//                impsOwTxnWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
//            }
//            String onlyFileName = impsTxnFile.substring(impsTxnFile.lastIndexOf("/") + 1);
//            files.add(onlyFileName);
//
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception e) {
//                throw new ApplicationException(e.getMessage());
//            }
//        } finally {
//            try {
//                if (impsOwTxnWriter != null) {
//                    impsOwTxnWriter.close();
//                }
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//        return files;
//    }
    @Override
    public List<String> generateImpsOwTxnFile(String fileGenerationDt, String fileGenBy) throws ApplicationException {
        List<String> files = new ArrayList<>();
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        FileWriter impsOwTxnWriter = null;
        try {
            List atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            Vector element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            String impsTxnFile = "", writeFolderPath = "";
            File writeFolder = null;
            String osName = System.getProperty("os.name");
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }
//            List dateList = em.createNativeQuery("select date_format(now(),'%d%b%y') as dt,time_format(cast(now() as time), '%H%i%s') as time").getResultList();
            List dateList = em.createNativeQuery("select date_format('" + fileGenerationDt + "','%d%b%y') as dt").getResultList();
            Vector dv = (Vector) dateList.get(0);
            String date = dv.get(0).toString().trim();
//            String time = dv.get(1).toString().trim();
            impsTxnFile = "Imps_seq_file" + "_" + date + ".csv";
            impsTxnFile = writeFolderPath + impsTxnFile;
            impsOwTxnWriter = new FileWriter(impsTxnFile);

            List impsList = em.createNativeQuery("select date_format(dt,'%d-%b-%y') as dt,time_format(cast(trantime as time), '%H%i%s') "
                    + "as time,ifnull(CPSMS_Batch_No,'') as refNo,debitaccountno,trs_no,creditaccountno,txnamt,status,(case when status='S' "
                    + "then 'Paid' when status='U' then 'Reversed' else 'Failed' end) as remark  from neft_ow_details "
                    + "where paymentType='IMPS' and dt='" + fileGenerationDt + "' and auth='Y'").getResultList();
            if (!impsList.isEmpty()) {
                for (int i = 0; i < impsList.size(); i++) {
                    Vector vec = (Vector) impsList.get(i);
                    String batchCode = vec.get(4).toString().trim();
                    if (batchCode.contains(".")) {
                        batchCode = batchCode.substring(0, batchCode.indexOf("."));
                    }

                    String singleEntry = vec.get(0).toString().trim() + ","
                            + vec.get(1).toString().trim() + ","
                            + vec.get(0).toString().trim() + ","
                            + "INTER" + ","
                            + vec.get(2).toString().trim() + ","
                            + vec.get(3).toString().substring(0, 2).trim() + ","
                            + "D" + ","
                            + batchCode.trim() + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + vec.get(5).toString().trim() + ","
                            + Double.valueOf(vec.get(6).toString().trim()) + ","
                            + vec.get(8).toString().trim() + ","
                            + ","
                            + ",1|" + "\r\n";

                    impsOwTxnWriter.write(singleEntry);

                }
            }

            List mobileImpsList = em.createNativeQuery("select date_format(request_date,'%d-%b-%y') as dt,time_format(cast(request_time as time), '%H%i%s')as time "
                    + ",ifnull(batch_no,'') as refNo, "
                    + "debit_account_no,batch_no trs_no,credit_account_no,amount,ifnull(batch_no,'') status, "
                    + "(case when ifnull(batch_no,'') <> '' then 'Paid' else 'Failed' end) as remark  "
                    + "from ib_txn_request where request_type='IMPS' and request_date='" + fileGenerationDt + "'").getResultList();

            if (!mobileImpsList.isEmpty()) {
                for (int i = 0; i < mobileImpsList.size(); i++) {
                    Vector vec = (Vector) mobileImpsList.get(i);
                    String batchCode = vec.get(4).toString().trim();
                    if (batchCode.contains(".")) {
                        batchCode = batchCode.substring(0, batchCode.indexOf("."));
                    }
                    String singleEntry = vec.get(0).toString().trim() + ","
                            + vec.get(1).toString().trim() + ","
                            + vec.get(0).toString().trim() + ","
                            + "INTER" + ","
                            + vec.get(2).toString().trim() + ","
                            + vec.get(3).toString().substring(0, 2).trim() + ","
                            + "D" + ","
                            + batchCode.trim() + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + ","
                            + vec.get(5).toString().trim() + ","
                            + Double.valueOf(vec.get(6).toString().trim()) + ","
                            + vec.get(8).toString().trim() + ","
                            + ","
                            + ",1|" + "\r\n";

                    impsOwTxnWriter.write(singleEntry);

                }
            }

            List inwardImpsList = em.createNativeQuery("select  date_format(TRAN_DATE,'%d-%b-%y') as dt ,time_format(cast(TRAN_TIME as time), '%H%i%s') as time,"
                    + "RRN,FROM_ACCOUNT_NUMBER,(case when IN_PROCESS_STATUS='SUCCESS' then substring(TO_ACCOUNT_NUMBER,1,2)  else '' end) as CrBrCd,"
                    + "(case when IN_PROCESS_STATUS='SUCCESS' then TRSNO  else '' end) as crBatchcd,TO_ACCOUNT_NUMBER,AMOUNT,REMARKS "
                    + "from imps_txn_parameter where TRAN_DATE='" + fileGenerationDt + "' and PROCESSING_CODE='43' and REVERSAL_INDICATOR='0'").getResultList();

            if (!inwardImpsList.isEmpty()) {
                for (int j = 0; j < inwardImpsList.size(); j++) {
                    Vector vec = (Vector) inwardImpsList.get(j);

                    String singleEntry1 = vec.get(0).toString().trim() + "," + vec.get(1).toString().trim() + ","
                            + vec.get(0).toString().trim() + "," + "INTRA" + "," + vec.get(2).toString().trim() + ","
                            + ",D,,,," + vec.get(3).toString().trim() + ","
                            + vec.get(4).toString().trim() + ","
                            + "C" + "," + vec.get(5).toString().trim() + ",,,"
                            + vec.get(6).toString().trim() + ","
                            + Double.valueOf(vec.get(7).toString().trim()) + ","
                            + vec.get(8).toString().trim() + ",,,1|" + "\r\n";

                    impsOwTxnWriter.write(singleEntry1);

                }
            } else {
                impsOwTxnWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
            }

            String onlyFileName = impsTxnFile.substring(impsTxnFile.lastIndexOf("/") + 1);
            files.add(onlyFileName);

        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            try {
                if (impsOwTxnWriter != null) {
                    impsOwTxnWriter.close();
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }

    //------------------------
    public Long getSerialNo(String fileGenerationDt, String fileType) {
        List snoList = em.createNativeQuery("select max(sno) from atm_recon_file_log where generated_file_date ='" + fileGenerationDt + "' "
                + "and generated_file_type='" + fileType + "'").getResultList();
        Vector element = (Vector) snoList.get(0);
        if (snoList.isEmpty() || element.get(0) == null || element.get(0).toString().equals("")) {
            return 1l;
        }
        return Long.parseLong(element.get(0).toString()) + 1;
    }

    public String logForGeneratedFile(BigInteger sno, String fileName, String fileType, String createdBy, String genDt) throws ApplicationException {
        String logMessage = "false";
        try {
            String curDt = new SimpleDateFormat("yyyyMMdd").format(new Date());
            int result = em.createNativeQuery("insert into atm_recon_file_log(sno,generated_file_date,generated_file_name,generated_file_type,tran_date,tran_time,enter_by) "
                    + "values(" + sno + ",'" + genDt + "','" + fileName + "','" + fileType + "','" + curDt + "',now(),'" + createdBy + "')").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Data insertion problem in Recon Log");
            }
            logMessage = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return logMessage;
    }

    public String getCustNameByAcno(String acno) {
        String custname = "";
        List list = em.createNativeQuery("select custname from accountmaster where acno='" + acno + "'").getResultList();
        if (list == null || list.isEmpty()) {
            return custname;
        }
        Vector element = (Vector) list.get(0);
        return custname = element.get(0).toString();
    }

    public String getBranchNameByBrncode(String brncode) {
        String branchName = "";
        List list = em.createNativeQuery("select branchname from branchmaster where brncode = cast('" + brncode + "' as unsigned)").getResultList();
        if (list == null || list.isEmpty()) {
            return branchName;
        }
        Vector element = (Vector) list.get(0);
        return branchName = element.get(0).toString();
    }

    public String[] getBrCodeAndNameByAtmId(String atmId) throws ApplicationException {
        String[] result = new String[3];
        try {
            System.out.println("atmId is: " + atmId);
            List dataList = em.createNativeQuery("select atm_branch from atm_master where atm_id='" + atmId + "'").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("There is no ATM Id in ATM Master: " + atmId);
            }
            Vector element = (Vector) dataList.get(0);
            String branchCode = element.get(0).toString();

            dataList = em.createNativeQuery("select branchname,address from branchmaster where brncode = cast('" + branchCode + "' as unsigned)").getResultList();
            if (dataList.isEmpty()) {
                throw new ApplicationException("Please define branch in BranchMaster for Branch Code  :" + branchCode);
            }
            element = (Vector) dataList.get(0);

            result[0] = branchCode;
            result[1] = element.get(0).toString();
            result[2] = element.get(1).toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List getPOValues() throws ApplicationException {
        try {
            List valPOResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='PO PRINTING'").getResultList();
            return valPOResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getPODtValues() throws ApplicationException {
        try {
            List valPOResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='PO DATE'").getResultList();
            return valPOResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDDValues() throws ApplicationException {
        try {
            List valPOResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='DD PRINTING'").getResultList();
            return valPOResult;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public boolean isNewPrinting(String repName) throws ApplicationException {
        try {
            List paramResult = em.createNativeQuery("select ifnull(code,'')as code from parameterinfo_report where UPPER(reportname)='" + repName + "'").getResultList();
            if (paramResult.isEmpty()) {
                return false;
            }
            Vector vecParam = (Vector) paramResult.get(0);
            if (Integer.parseInt(vecParam.get(0).toString()) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String getBillNature(String billType) throws ApplicationException {
        try {
            List list = em.createNativeQuery("select instnature from billtypemaster where instcode='" + billType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Data does not exist in BillTypeMaster");
            }
            Vector element = (Vector) list.get(0);
            return element.get(0).toString();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    public List getFieldNameList(String billType, String fieldType) throws ApplicationException {
        try {
            if (fieldType.equals("L")) {
                billType = billType + "L";
            }
            List list = em.createNativeQuery("select field_order, field_name from cbs_printing_parameter where bill_type = '" + billType
                    + "' and lable_value = '" + fieldType + "'").getResultList();
            if (list == null || list.isEmpty()) {
                throw new ApplicationException("Data does not exist in cbs_printing_parameter");
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getPrintingParameters(String type, String opt) throws ApplicationException {
        try {
            List billParamsList = null;
            billParamsList = em.createNativeQuery("select field_name,x_co_ord,y_co_ord,col_width from cbs_printing_parameter "
                    + "where bill_type='" + type + "' and lable_value = '" + opt + "' order by field_order").getResultList();
            return billParamsList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<String> getNewFdDetails(String acNo, Float rtNo) throws ApplicationException {
        try {
            List<String> dataList = new ArrayList<String>();
            DecimalFormat numFormat = new DecimalFormat("#.00");
            Spellar obj = new Spellar();
            List list = em.createNativeQuery("select b.acno,b.custname,b.jtname1,b.jtname2,b.jtname3,b.jtname4,b.nomination,tc.dob,c.acctdesc, "
                    + "DATE_FORMAT(a.td_madedt,'%d/%m/%Y'),DATE_FORMAT(a.fddt,'%d/%m/%Y'),DATE_FORMAT(a.matdt,'%d/%m/%Y'),"
                    + "a.prinamt,a.roi,a.years, a.months,a.days,a.intopt,d.description,cast(a.receiptno as unsigned), datediff(now(),tc.dob),br.branchname,"
                    + "a.period,ifnull(a.inttoacno,''),tc.craddress,tc.fathername ,br.address,ba.bankname,ifnull(a.Lien,'N'),ifnull(b.instruction,''),"
                    + "ifnull(b.cust_type,''),cast(a.voucherno as unsigned),d.code "
                    + "from td_vouchmst a, td_accountmaster b,accounttypemaster c, codebook d,td_customermaster tc, branchmaster br ,bnkadd ba "
                    + "where a.acno='" + acNo + "' and a.status='A' and voucherno=" + rtNo + " and a.acno = b.acno and c.acctcode = b.accttype "
                    + "and b.opermode = d.code and d.groupcode = 4 and tc.custno = substring('" + acNo + "',5,6) and tc.brncode = b.curbrcode "
                    + "and tc.actype = b.accttype and br.brncode = b.curbrcode and br.alphacode = ba.alphacode").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data corressponding your details");
            }
            Vector element = (Vector) list.get(0);
            //acno at 0
            dataList.add(element.get(0).toString());
            //name at 1

            String custName = "";
            if (element.get(32).toString().equalsIgnoreCase("11") || element.get(32).toString().equalsIgnoreCase("13")) {
                custName = element.get(1).toString();
                List guardList = em.createNativeQuery("select custfullname from cbs_customer_master_detail where customerid = "
                        + "(select guardiancode from cbs_cust_minorinfo where customerid = (select custid from customerid "
                        + " where acno ='" + acNo + "'))").getResultList();
                String gCustName = "";
                if (!guardList.isEmpty()) {
                    Vector guardElement = (Vector) guardList.get(0);
                    gCustName = " U/G " + guardElement.get(0).toString();
                }
                custName = custName + gCustName;
            } else {
                custName = element.get(1).toString();
            }

            dataList.add(custName);

            String jtName = element.get(2).toString();
            if (!element.get(3).toString().equals("")) {
                jtName = jtName + " / " + element.get(3).toString();
            }
            if (!element.get(4).toString().equals("")) {
                jtName = jtName + " / " + element.get(4).toString();
            }
            if (!element.get(5).toString().equals("")) {
                jtName = jtName + " / " + element.get(5).toString();
            }
            //jtname at 2 
            dataList.add(jtName);

            //Nomination at 3
            if (element.get(6) != null) {
                dataList.add(element.get(6).toString());
            } else {
                dataList.add("");
            }

            //Minor Dob at 4
            int diff = Integer.parseInt(element.get(20).toString());
            int sts = diff / 365;
            if (sts < 18) {
                dataList.add(element.get(7).toString().substring(6, 8) + "/" + element.get(7).toString().substring(4, 6) + "/" + element.get(7).toString().substring(0, 4));
            } else {
                dataList.add("");
            }
            //Account desc at 5
            dataList.add(element.get(8).toString());

            //fd made date at 6
            dataList.add(element.get(9).toString());

            //fd date at 7
            String fdDt = element.get(10).toString();
            dataList.add(fdDt);

            //mat date at 8
            String matDt = element.get(11).toString();
            dataList.add(matDt);

            //principal Amt at 9
            double printAmt = Double.parseDouble(element.get(12).toString());
            dataList.add(numFormat.format(printAmt));

            // amount in text at 10
            dataList.add(obj.spellAmount(printAmt));

            //Roi at 11
            float roi = Float.parseFloat(element.get(13).toString());
            dataList.add(numFormat.format(roi));

            //period at 12
            dataList.add(element.get(16).toString() + " / " + element.get(15).toString() + " / " + element.get(14).toString());

            //intopt at 13
            String intOpt = element.get(17).toString();
            dataList.add(intOpt);

            //oper mode at 14
            dataList.add(element.get(18).toString());

            //receipt no at 15
            dataList.add(element.get(19).toString());

            String period = element.get(22).toString();

            String fdDate = fdDt.substring(6) + fdDt.substring(3, 5) + fdDt.substring(0, 2);
            String matDate = matDt.substring(6) + matDt.substring(3, 5) + matDt.substring(0, 2);

            String fdInterest = tdListingRemoteObj.orgFDInterest(intOpt, roi, fdDate, matDate, printAmt, period, acNo.substring(0, 2));

            //total interest at 16
            dataList.add(numFormat.format(Double.parseDouble(fdInterest)));

            double matAmt = 0;

            if (intOpt.equalsIgnoreCase("M") || intOpt.equalsIgnoreCase("Q") || intOpt.equalsIgnoreCase("Y")) {
                if ((element.get(23).toString().equalsIgnoreCase("")) || (element.get(0).toString().equalsIgnoreCase(element.get(23).toString()))) {
                    matAmt = Double.parseDouble(element.get(12).toString()) + Double.parseDouble(fdInterest);
                } else {
                    matAmt = Double.parseDouble(element.get(12).toString());
                }
            } else {
                matAmt = Double.parseDouble(element.get(12).toString()) + Double.parseDouble(fdInterest);
            }

            //mat amt at 17
            dataList.add(numFormat.format(matAmt));

            //mat text amt at 18
            dataList.add(obj.spellAmount(matAmt));

            //branch at 19
            dataList.add(element.get(21).toString());

            //Address at 20
            dataList.add(element.get(24).toString());

            //FatherName at 21
            dataList.add(element.get(25).toString());

            //Branch Address at 22
            dataList.add(element.get(26).toString());

            //BankName at 23
            dataList.add(element.get(27).toString());

            //Lien Status at 24
            String lStatus = "";
            if (element.get(28).toString().equals("Y")) {
                lStatus = "Yes";
            } else {
                lStatus = "No";
            }
            dataList.add(lStatus);

            //Nominee DOB 25
            String nomDob = "";
            List nomList = em.createNativeQuery("select ifnull(DATE_FORMAT(a.nomdob,'%d/%m/%Y'),'') from nom_details a where a.acno='" + acNo + "'").getResultList();
            if (!nomList.isEmpty()) {
                Vector element1 = (Vector) nomList.get(0);
                nomDob = element1.get(0).toString();
                dataList.add(nomDob);
                dataList.add("Yes");
            } else {
                dataList.add(nomDob);
                dataList.add("No");
            }

            //Customer ID 26
            String custId = "";
            String panNo = "";
            List cuList = em.createNativeQuery("select ifnull(custid,''), ifnull(cd.PAN_GIRNumber,'') from customerid c, cbs_customer_master_detail cd where c.custid = cd.customerid and c.acno='" + acNo + "'").getResultList();
            if (!cuList.isEmpty()) {
                Vector element1 = (Vector) cuList.get(0);
                custId = element1.get(0).toString();
                panNo = element1.get(1).toString();
                if (panNo.equals("") || panNo.length() != 10) {
                    panNo = "";
                } else {
                    if (!ParseFileUtil.isAlphabet(panNo.substring(0, 5))
                            || !ParseFileUtil.isNumeric(panNo.substring(5, 9))
                            || !ParseFileUtil.isAlphabet(panNo.substring(9))) {
                        panNo = "";
                    } else {
                    }
                }
            }
            dataList.add(custId);

            //Remarks at 27
            dataList.add(element.get(29).toString());

            //Senior Citizen 28
            String sCtz = element.get(30).toString();
            if (sCtz.equalsIgnoreCase("SC")) {
                dataList.add("Senior Citizen");
            } else if(sCtz.equalsIgnoreCase("ST")){
                dataList.add("Staff");
            }else{
                dataList.add("Other");
            }

            String fIntOpt = "";
            if (intOpt.equals("C")) {
                fIntOpt = "Cumulative";
            } else if (intOpt.equals("S")) {
                fIntOpt = "Simple";
            } else if (intOpt.equals("Q")) {
                fIntOpt = "Quarterly";
            } else if (intOpt.equals("M")) {
                fIntOpt = "Monthly";
            }
            //int opt at 29
            dataList.add(fIntOpt);

            //VoucherNo at 30
            dataList.add(element.get(31).toString());

            //PAN No 31
            dataList.add(panNo);

            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List<String> getNewPassbookDetails(String acNo) throws ApplicationException {
        try {
            List<String> dataList = new ArrayList<String>();
            String acNature = ftsRemote.getAccountNature(acNo);
            List list = em.createNativeQuery("select ci.custid,c.custname,a.acno,date_format(ifnull(a.openingdt ,''),'%d/%m/%Y'),ifnull(a.jtname1,''),ifnull(a.jtname2,''), \n"
                    + "ifnull(a.jtname3,''),ifnull(a.jtname4,''), ifnull(cd.MailAddressLine1,''),ifnull(cd.MailAddressLine2,''),ifnull(cd.MailDistrict,''), \n"
                    + "ifnull(cd.MailPostalCode,''),ifnull(cd.MailStateCode,''),ifnull(cd.MailCountryCode,''),ifnull(cd.MailPhoneNumber,''), \n"
                    + "ifnull(cd.mobilenumber,''),ifnull(a.nomination ,''), coalesce(am.acctdesc,''),'INR',if(nullif(cm.occupationCode,'') IS NULL ,'26',cm.occupationCode ), \n"
                    + "ifnull(e.description,''), ifnull(c.fathername,''),ifnull(a.intdeposit,'0'),timestampdiff(MONTH,a.openingdt,ifnull(a.rdmatdate,'19000101')),\n"
                    + "ifnull(a.rdinstal,'0'), date_format(ifnull(a.rdmatdate,'19000101'),'%d/%m/%Y'),b.bankname,LPAD(ifnull(cast(br.brncode as char),'0'),2,'0'), \n"
                    + "br.branchname,ifnull(br.address,''),ifnull(br.City,''),ifnull(br.Pincode,''),ifnull(br.State,''),'INDIA',ifnull(p.brphone,''), \n"
                    + "LPAD(ifnull(cast(b.micr as char),'0'),3,'0'),LPAD(ifnull(cast(b.micrcode as char),'0'),3,'0'),LPAD(ifnull(cast(b.branchcode as char),'0'),3,'0'), \n"
                    + "ifnull(br.ifsc_code,''),concat(ifnull(cd.spouse_name,''), ' ', ifnull(cd.spousemiddlename,''),if(ifnull(cd.spousemiddlename,'')='','',' '),ifnull(cd.spouselastname,'')),\n"
                    + "date_format(ifnull(cd.DateOfBirth,'19000101'),'%d/%m/%Y'), ifnull(cd.AADHAAR_NO,''), ifnull(cd.PAN_GIRNumber,''), ifnull(br.email,''),ifnull(br.gst_in,'') \n"
                    + "from customermaster c,accountmaster a,parameterinfo p,codebook e, customerid ci,bnkadd b, branchmaster br, \n"
                    + "cbs_customer_master_detail cd,accounttypemaster am, cbs_cust_misinfo cm where c.actype=a.accttype and c.custno=substring(a.acno,5,6) and \n"
                    + "c.agcode=substring(a.acno,11,2) and c.brncode = a.curbrcode and a.acno = '" + acNo + "' and p.brncode = cast(c.brncode as unsigned) and \n"
                    + "a.opermode = e.code and e.groupcode=4 and a.acno = ci.acno and br.brncode = cast(c.brncode as unsigned) and br.alphacode = b.alphacode and \n"
                    + "cd.customerid = ci.custid and ci.acno = a.acno and c.actype = am.acctcode and cd.customerid = cm.customerid").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no data corressponding your details");
            }
            Vector element = (Vector) list.get(0);
            //custid at 0
            dataList.add(element.get(0).toString());
            //name at 1
            dataList.add(element.get(1).toString());
            //acno at 2
            dataList.add(element.get(2).toString());
            //opening dt 3
            dataList.add(element.get(3).toString());

            String jtName1 = element.get(4).toString().equals("") ? "None" : element.get(4).toString();

            if (!jtName1.equals("None")) {
                jtName1 = jtName1 + " / " + element.get(5).toString();
            }
            //4
            dataList.add(jtName1);

            String jtName2 = element.get(6).toString().equals("") ? "None" : element.get(6).toString();

            if (!jtName2.equals("None")) {
                jtName2 = jtName2 + " / " + element.get(7).toString();
            }
            //5
            dataList.add(jtName2);
            // Address 6
            dataList.add(element.get(8).toString() + " " + element.get(9).toString());

            //7 city
            //dataList.add(common.getRefRecDesc("001", element.get(10).toString()));
            dataList.add(element.get(10).toString());
            //8 pin code
            dataList.add(element.get(11).toString());
            //9 state
            dataList.add(common.getRefRecDesc("002", element.get(12).toString()));
            //10 country
            dataList.add(common.getRefRecDesc("003", element.get(13).toString()));
            //11 telephone no
            dataList.add(element.get(14).toString());
            //12 mobile no
            dataList.add(element.get(15).toString());
            //Nomination at 13
            dataList.add(element.get(16).toString().equals("") ? "None" : element.get(16).toString());
            //14 ac type
            dataList.add(element.get(17).toString());
            //15 INR
            dataList.add(element.get(18).toString());
            //16 Occupation
            //dataList.add(element.get(19).toString());
            dataList.add(common.getRefRecDesc("021", element.get(19).toString()));
            //17 Operation Mode 
            dataList.add(element.get(20).toString());
            //18 father Name 
            dataList.add(element.get(21).toString());

            if (acNature.equals(CbsConstant.RECURRING_AC) || acNature.equals(CbsConstant.DEPOSIT_SC)) {
                //19 ROI 
                dataList.add(element.get(22).toString());
                //20 Period 
                dataList.add(element.get(23).toString());
                //21 Installment 
                dataList.add(element.get(24).toString());
                //22 Maturity Date 
                dataList.add(element.get(25).toString());
                //23 Maturity Amount 
                if (acNature.equals(CbsConstant.RECURRING_AC)) {
                    dataList.add(acctRemote.cbsRdCalculation(Float.parseFloat(element.get(24).toString()), Integer.parseInt(element.get(23).toString()),
                            Float.parseFloat(element.get(22).toString())));
                } else {
                    List ddsList = acctEnqRemote.ddsInterestCal(Double.parseDouble(element.get(22).toString()), Double.parseDouble(element.get(24).toString()),
                            Float.parseFloat(element.get(23).toString()));

                    dataList.add(ddsList.get(1).toString());
                }
            } else {
                //19 ROI 
                dataList.add("");
                //20 Period 
                dataList.add("");
                //21 Installment 
                dataList.add("");
                //22 Maturity Date 
                dataList.add("");
                //23 Maturity Amount 
                dataList.add("");
            }

            //24 Bank Name 
            dataList.add(element.get(26).toString());
            //25 Branch Code 
            dataList.add(element.get(27).toString());
            //26 Branch Name 
            dataList.add(element.get(28).toString());
            //27 Branch Address 
            dataList.add(element.get(29).toString());
            //28 Branch City 
            dataList.add(element.get(30).toString());
            //29 Branch Pin Code 
            dataList.add(element.get(31).toString());
            //30 Branch State 
            dataList.add(element.get(32).toString());
            //31 Branch Country 
            dataList.add(element.get(33).toString());
            //32 Branch Tel No 
            dataList.add(element.get(34).toString());
            //33 Micr Code 
            dataList.add(element.get(35).toString() + element.get(36).toString() + element.get(37).toString());
            //34 IFSC Code 
            dataList.add(element.get(38).toString());
            //35 Spouse Name
            dataList.add(element.get(39).toString());
            //36 Date of Birth
            if (element.get(40).toString().substring(6).equals("1900")) {
                dataList.add("");
            } else {
                dataList.add(element.get(40).toString());
            }
            //37 Aadhar No

            List aadharList = em.createNativeQuery("select aa.IdentityNo from (select a.CustomerId, a.IdentityNo from cbs_cust_identity_details a,cbs_customer_master_detail b "
                    + "where a.IdentificationType= 'E' and a.customerid= b.customerid union select a.CustomerId, a.IdentityNo from cbs_customer_master_detail a where "
                    + "a.legal_document = 'E') aa where  aa.CustomerId = '" + element.get(0).toString() + "'").getResultList();
            String aadharNo = "";
            if (!aadharList.isEmpty()) {
                Vector ele = (Vector) aadharList.get(0);

                aadharNo = ele.get(0).toString();
            }
            dataList.add(aadharNo);
            //38 PAN 
            if (element.get(42).toString().length() == 10) {
                dataList.add(element.get(42).toString());
            } else {
                dataList.add("");
            }

            //39 Branch Email
            dataList.add(element.get(43).toString());
            //40 branch GSTIN
            dataList.add(element.get(44).toString());
            if (acNature.equals(CbsConstant.DEPOSIT_SC)) {
                List agentList = em.createNativeQuery("select name from ddsagent where agcode = '" + acNo.substring(10) + "' and brncode='" + element.get(27).toString() + "' and status='A'").getResultList();
                if (agentList.isEmpty()) {
                    throw new ApplicationException("Agent does not exist or active");
                }
                Vector vet = (Vector) agentList.get(0);
                //41 Agent Code
                dataList.add(acNo.substring(10));
                //42 Agent Name
                dataList.add(vet.get(0).toString());
            } else {
                //41 Agent Code
                dataList.add("");
                //42 Agent Name
                dataList.add("");
            }
            return dataList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getPrintingHeader(String type) throws ApplicationException {
        try {
            List billParamsList = null;
            billParamsList = em.createNativeQuery("select width,height,line_break,x_co_ord,pdf_width,pdf_height,top_margin,left_margin,font_size from cbs_print_hd_detail "
                    + "where bill_type='" + type + "'").getResultList();
            return billParamsList;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
    // Added by Manis kumar 28-04-2017

    public int uploadClearingFile(File path) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String chequeNo;
        String sortCode;
        String sanNo;
        String transactionCode;
        double amount;
        String returnCode;
        int result = 0, count = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            count = insertOutwardCtsReturn(path);
            fr = new FileReader(path.getAbsolutePath());
            br = new BufferedReader(fr);
            String line = "";
            ut.begin();
            while ((line = br.readLine()) != null) {
                chequeNo = line.substring(10, 16);
                sortCode = line.substring(17, 26);
                sanNo = line.substring(27, 34);
                transactionCode = line.substring(35, 38);
                //String Amt = line.substring(39, 52);
                amount = Double.parseDouble(line.substring(39, line.lastIndexOf(".00")));
                //amount = Double.parseDouble(Amt.substring(0, Amt.length() - 2));
                //returnCode = line.substring(53, 55);
                returnCode = line.substring(line.lastIndexOf("|") + 1);
                String AreaCode = sortCode.substring(0, 3);
                String BnkCode = sortCode.substring(3, 6);
                String BranchCode = sortCode.substring(6, 9);
                String query = "update clg_ow_shadowbal set CtsReturnCode = '" + returnCode + "' where TxnInstNo = '" + Integer.parseInt(chequeNo) + "' and AreaCode = '" + AreaCode + "' and BnkCode = '" + BnkCode + "' and BranchCode = '" + BranchCode + "' and SanNo = '" + sanNo + "'"
                        + " and TransactionCode = '" + transactionCode + "' and TxnInstAmt = '" + amount + "'";
                System.out.println("Query :- " + query);
                int res = em.createNativeQuery(query).executeUpdate();
                if (res < 1) {
                    ut.rollback();
                    throw new ApplicationException("File uploading problem, Data is not appropriate of file in line no. " + (result + 1) + " !");
                } else {
                    result++;
                }
            }
            if (count == result) {
                ut.commit();
                br.close();
                fr.close();
            } else {
                ut.rollback();
                throw new ApplicationException("File uploading problem !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public int insertOutwardCtsReturn(File fileName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String branchMICR, sortCode, chequeNo, sanNo, transactionCode, returnCode;
        double amount;
        int result = 0, count = 0;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(fileName.getAbsolutePath());
            br = new BufferedReader(fr);
            while ((br.readLine()) != null) {
                count++;
            }
            fr = new FileReader(fileName.getAbsolutePath());
            br = new BufferedReader(fr);
            String line = "";
            ut.begin();
            while ((line = br.readLine()) != null) {
                branchMICR = line.substring(0, 9);
                chequeNo = line.substring(10, 16);
                sortCode = line.substring(17, 26);
                sanNo = line.substring(27, 34);
                transactionCode = line.substring(35, 38);
                // String Amt = line.substring(39, 52);
                //String Amt = line.substring(39, line.lastIndexOf(".00"));
                //amount = Double.parseDouble(Amt.substring(0, Amt.length() - 2));
                amount = Double.parseDouble(line.substring(39, line.lastIndexOf(".00")));
                //returnCode = line.substring(53, 55);
                returnCode = line.substring(line.lastIndexOf("|") + 1);
                String query = "insert into outward_cts_return(Branch_Micr, Chq_No, Sort_Code, San_No, TC, Amount, Return_Code, Received_Date, Received_File)"
                        + "values('" + branchMICR + "', '" + chequeNo + "', '" + sortCode + "', '" + sanNo + "', '" + transactionCode + "', '" + amount + "', '" + returnCode + "', now(), '" + fileName.getName() + "')";
                System.out.println("Query :- " + query);
                int res = em.createNativeQuery(query).executeUpdate();
                if (res < 1) {
                    ut.rollback();
                    throw new ApplicationException("Insertion problem in Outward cts return table, Data is not appropriate of file in line no. " + (result + 1) + " !");
                } else {
                    result++;
                }
            }
            if (count == result) {
                ut.commit();
                br.close();
                fr.close();
            } else {
                ut.rollback();
                throw new ApplicationException("Insertion problem in Outward cts return table !");
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }
    //---

//    @Override
//    public List<String> generateFinacusReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException {
//        UserTransaction ut = context.getUserTransaction();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        FileWriter issuerWriter = null;
//        List<String> files = new ArrayList<>();
//        try {
//            ut.begin();
//            List atmList = em.createNativeQuery("select code from parameterinfo_report where reportname='BANK-ATM-STATUS'").getResultList();
//            if (atmList.isEmpty()) {
//                throw new ApplicationException("Please fill data into parameterinfo report for BANK-ATM-STATUS");
//            }
//            Vector element = (Vector) atmList.get(0);
//            String atmStatus = element.get(0).toString();
//
//            atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
//            if (atmList.isEmpty()) {
//                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
//            }
//            element = (Vector) atmList.get(0);
//            if (element.get(0) == null || element.get(0).toString().equals("")) {
//                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
//            }
//            String bankCode = element.get(0).toString();
//
//            atmList = em.createNativeQuery("select code from cbs_parameterinfo where name in('ACQUIRER-CODE');").getResultList();
//            if (atmList.isEmpty()) {
//                throw new ApplicationException("Please define acquirer code");
//            }
//            element = (Vector) atmList.get(0);
//            if (element.get(0) == null || element.get(0).toString().equals("")) {
//                throw new ApplicationException("Please define acquirer code in cbs parameterinfo.");
//            }
//            String acquirerCode = element.get(0).toString().trim();
//
//            String sno = "", issuerFile = "", writeFolderPath = "";
//
//            String osName = System.getProperty("os.name");
//            File writeFolder = null;
//            if (osName.equalsIgnoreCase("Linux")) {
//                writeFolderPath = "/install/ATM/";
//                writeFolder = new File(writeFolderPath);
//                if (!writeFolder.exists()) {
//                    writeFolder.mkdir();
//                }
//            } else {
//                writeFolderPath = "C:/ATM/";
//                writeFolder = new File(writeFolderPath);
//                if (!writeFolder.exists()) {
//                    writeFolder.mkdir();
//                }
//            }
//
//            if (atmStatus.equals("73")) {
//                //Isser File generation
//                sno = getSerialNo(fileGenerationDt, "I").toString();
//                if (sno.length() < 2) {
//                    sno = "0" + sno;
//                }
//                issuerFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".csv";
//                issuerFile = writeFolderPath + issuerFile;
//                issuerWriter = new FileWriter(issuerFile);
//
//                atmList = em.createNativeQuery("select system_trace_number as stan_number,card_number as card_no,rrn as "
//                        + "transaction_serial_number,substring(from_account_number,3,2) as account_type,from_account_number "
//                        + "as account_number,substring(from_account_number,1,2) as branch_code,amount as transaction_amount,"
//                        + "date_format(tran_date,'%Y%m%d') as transaction_date,concat(extract(hour from tran_time),"
//                        + "extract(minute from tran_time),extract(second from tran_time)) as transaction_time,"
//                        + "date_format(in_coming_date,'%Y%m%d') as value_date,terminal_id as atm_id,terminal_location as "
//                        + "atm_location,ifnull(trsno,0) as batch_code,ifnull(authorization_id,'') as user_id from "
//                        + "atm_normal_transaction_parameter where processing_code in('01') and "
//                        + "tran_date='" + fileGenerationDt + "' and in_process_flag='S' and system_trace_number not "
//                        + "in(select original_system_trace_number from atm_reversal_transaction_parameter where "
//                        + "processing_code='01' and tran_date='" + fileGenerationDt + "' and in_process_flag='S') "
//                        + "order by tran_time").getResultList();
//                if (!atmList.isEmpty()) {
//                    for (int i = 0; i < atmList.size(); i++) {
//                        element = (Vector) atmList.get(i);
//                        BigDecimal amount = new BigDecimal(element.get(6).toString().trim());
//                        String batchCode = new BigDecimal(element.get(12).toString().trim()).toString();
//                        if (batchCode.contains(".")) {
//                            batchCode = batchCode.substring(0, batchCode.indexOf("."));
//                        }
//
////                        String singleEntry = ParseFileUtil.addTrailingSpaces(acquirerCode, 11)
////                                + ParseFileUtil.addTrailingSpaces(element.get(2).toString().trim(), 12)
////                                + ParseFileUtil.addTrailingSpaces(element.get(0).toString().trim(), 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(5).toString().trim(), 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(4).toString().trim(), 32)
////                                + ParseFileUtil.addTrailingSpaces("", 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(10).toString().trim(), 10)
////                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString().trim(), 100)
////                                + ParseFileUtil.addTrailingZeros(amount.toString().trim(), 13)
////                                + ParseFileUtil.addTrailingSpaces(element.get(1).toString().trim(), 19)
////                                + ParseFileUtil.addTrailingSpaces("", 6)
////                                + element.get(9).toString()
////                                + element.get(7).toString()
////                                + ParseFileUtil.addTrailingSpaces(batchCode, 32)
////                                + ParseFileUtil.addTrailingSpaces("ISSUER", 100)
////                                + ParseFileUtil.addTrailingSpaces("", 6)
////                                + ParseFileUtil.addTrailingSpaces("", 8)
////                                + ParseFileUtil.addTrailingSpaces("", 8)
////                                + ParseFileUtil.addTrailingSpaces("", 100)
////                                + ParseFileUtil.addTrailingSpaces("", 32)
////                                + element.get(7).toString()
////                                + ParseFileUtil.addTrailingSpaces(element.get(8).toString().trim(), 8)
////                                + ParseFileUtil.addTrailingSpaces("", 8)
////                                + ParseFileUtil.addTrailingSpaces("", 8)
////                                + ParseFileUtil.addTrailingSpaces("", 8)
////                                + ParseFileUtil.addTrailingZeros("", 13) + "\r\n";
//
//                        String singleEntry = acquirerCode + ","
//                                + element.get(2).toString().trim() + ","
//                                + element.get(0).toString().trim() + ","
//                                + element.get(5).toString().trim() + ","
//                                + element.get(4).toString().trim() + ","
//                                + ","
//                                + element.get(10).toString().trim() + ","
//                                + element.get(11).toString().trim() + ","
//                                + amount.toString().trim() + ","
//                                + element.get(1).toString().trim() + ","
//                                + ","
//                                + element.get(9).toString() + ","
//                                + element.get(7).toString() + ","
//                                + batchCode + ","
//                                + "ISSUER" + ","
//                                + ","
//                                + ","
//                                + ","
//                                + ","
//                                + ","
//                                + element.get(7).toString() + ","
//                                + element.get(8).toString().trim() + ","
//                                + ","
//                                + ","
//                                + ","
//                                + "," + "\r\n";
//
//                        issuerWriter.write(singleEntry);
//                    }
//                } else {
//                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
//                }
//
//                atmList = em.createNativeQuery("select r.system_trace_number as rev_stan_number,r.rrn as rev_transaction_serial_number,"
//                        + "r.from_account_number as rev_account_number,substring(r.from_account_number,1,2) as rev_branch_code,"
//                        + "r.amount as rev_transaction_amount,date_format(r.tran_date,'%Y%m%d') as rev_transaction_date,"
//                        + "concat(extract(hour from r.tran_time),extract(minute from r.tran_time),extract(second from r.tran_time)) "
//                        + "as rev_transaction_time,date_format(r.in_coming_date,'%Y%m%d') as rev_value_date,ifnull(r.trsno,0) as "
//                        + "rev_batch_code,ifnull(r.authorization_id,'') as rev_user_id,n.rrn as nor_transaction_serial_number,"
//                        + "n.system_trace_number as nor_stan_number,n.from_account_number as nor_account_number,"
//                        + "substring(n.from_account_number,1,2) as nor_branch_code,n.terminal_id as nor_atm_id,n.terminal_location "
//                        + "as nor_atm_location,n.amount as nor_transaction_amount,n.card_number as nor_card_no,"
//                        + "date_format(n.tran_date,'%Y%m%d') as nor_transaction_date,concat(extract(hour from n.tran_time),"
//                        + "extract(minute from n.tran_time),extract(second from n.tran_time)) as nor_transaction_time,"
//                        + "date_format(n.in_coming_date,'%Y%m%d') as nor_value_date,ifnull(n.trsno,0) as nor_batch_code,"
//                        + "ifnull(n.transaction_fee,'C00000000') as nor_txn_fee from atm_reversal_transaction_parameter r,"
//                        + "atm_normal_transaction_parameter n where r.processing_code=n.processing_code and "
//                        + "r.tran_date=n.tran_date and r.in_process_flag=n.in_process_flag and r.processing_code='01' and "
//                        + "r.tran_date='" + fileGenerationDt + "' and r.in_process_flag='S' and "
//                        + "r.original_system_trace_number=n.system_trace_number order by r.tran_time").getResultList();
//                if (!atmList.isEmpty()) {
//                    for (int i = 0; i < atmList.size(); i++) {
//                        element = (Vector) atmList.get(i);
//                        BigDecimal txnAmount = new BigDecimal(element.get(16).toString());
//                        String txnFee = element.get(22).toString().trim();
//                        String txnBatchCode = new BigDecimal(element.get(21).toString().trim()).toString();
//                        if (txnBatchCode.contains(".")) {
//                            txnBatchCode = txnBatchCode.substring(0, txnBatchCode.indexOf("."));
//                        }
//
//                        String revBatchCode = new BigDecimal(element.get(8).toString()).toString();
//                        if (revBatchCode.contains(".")) {
//                            revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
//                        }
//                        BigDecimal tempRevAmount = new BigDecimal(element.get(4).toString());
//
//                        if (!(txnFee.equalsIgnoreCase("0") || txnFee.equalsIgnoreCase("C00000000"))) {
//                            if (txnFee.toUpperCase().contains("C")) {
//                                txnAmount = txnAmount.add(new BigDecimal(txnFee.substring(1)).divide(new BigDecimal("100")));
//                            } else {
//                                txnAmount = txnAmount.add(new BigDecimal(txnFee));
//                            }
//                        }
//
//                        BigDecimal tranAmount = txnAmount.subtract(tempRevAmount);
//                        if (tranAmount.compareTo(new BigDecimal("0")) == 0) {
//                            tranAmount = txnAmount;
//                        }
//
////                        String singleEntry = ParseFileUtil.addTrailingSpaces(acquirerCode, 11)
////                                + ParseFileUtil.addTrailingSpaces(element.get(10).toString().trim(), 12)
////                                + ParseFileUtil.addTrailingSpaces(element.get(11).toString().trim(), 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(13).toString().trim(), 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(12).toString().trim(), 32)
////                                + ParseFileUtil.addTrailingSpaces("", 6)
////                                + ParseFileUtil.addTrailingSpaces(element.get(14).toString().trim(), 10)
////                                + ParseFileUtil.addTrailingSpaces(element.get(15).toString().trim(), 100)
////                                + ParseFileUtil.addTrailingZeros(txnAmount.toString().trim(), 13)
////                                + ParseFileUtil.addTrailingSpaces(element.get(17).toString().trim(), 19)
////                                + ParseFileUtil.addTrailingSpaces("", 6)
////                                + element.get(20).toString()
////                                + element.get(18).toString()
////                                + ParseFileUtil.addTrailingSpaces(txnBatchCode, 32)
////                                + ParseFileUtil.addTrailingSpaces("", 100)
////                                + ParseFileUtil.addTrailingSpaces(element.get(3).toString().trim(), 6)
////                                + element.get(7).toString()
////                                + element.get(5).toString()
////                                + ParseFileUtil.addTrailingSpaces("Issuer Reversal", 100)
////                                + ParseFileUtil.addTrailingSpaces(revBatchCode, 32)
////                                + element.get(18).toString()
////                                + ParseFileUtil.addTrailingSpaces(element.get(19).toString().trim(), 8)
////                                + ParseFileUtil.addTrailingSpaces(element.get(9).toString().trim(), 8)
////                                + element.get(5).toString()
////                                + ParseFileUtil.addTrailingSpaces(element.get(6).toString().trim(), 8)
////                                + ParseFileUtil.addTrailingZeros(tranAmount.toString().trim(), 13) + "\r\n";
//
//                        String singleEntry = acquirerCode + ","
//                                + element.get(10).toString().trim() + ","
//                                + element.get(11).toString().trim() + ","
//                                + element.get(13).toString().trim() + ","
//                                + element.get(12).toString().trim() + ","
//                                + ","
//                                + element.get(14).toString().trim() + ","
//                                + element.get(15).toString().trim() + ","
//                                + txnAmount.toString().trim() + ","
//                                + element.get(17).toString().trim() + ","
//                                + ","
//                                + element.get(20).toString() + ","
//                                + element.get(18).toString() + ","
//                                + txnBatchCode + ","
//                                + ","
//                                + element.get(3).toString().trim() + ","
//                                + element.get(7).toString() + ","
//                                + element.get(5).toString() + ","
//                                + "Issuer Reversal" + ","
//                                + revBatchCode + ","
//                                + element.get(18).toString() + ","
//                                + element.get(19).toString().trim() + ","
//                                + element.get(9).toString().trim() + ","
//                                + element.get(5).toString() + ","
//                                + element.get(6).toString().trim() + ","
//                                + tranAmount.toString().trim()
//                                + "," + "\r\n";
//
//                        issuerWriter.write(singleEntry);
//                    }
//                } else {
//                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
//                }
//                String onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
//                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "I", generatedBy, fileGenerationDt).equals("true")) {
//                    files.add(onlyFileName);
//                }
//            }
//            ut.commit();
//        } catch (Exception ex) {
//            try {
//                ut.rollback();
//                ex.printStackTrace();
//                throw new ApplicationException(ex.getMessage());
//            } catch (Exception e) {
//                throw new ApplicationException(e.getMessage());
//            }
//        } finally {
//            try {
//                if (issuerWriter != null) {
//                    issuerWriter.close();
//                }
//            } catch (Exception ex) {
//                throw new ApplicationException(ex.getMessage());
//            }
//        }
//        return files;
//    }
    @Override
    public List<String> generateFinacusReconFiles(String fileGenerationDt, String generatedBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        FileWriter issuerWriter = null;
        FileWriter onUsWriter = null;
        FileWriter offUsWriter = null;
        List<String> files = new ArrayList<>();
        try {
            ut.begin();
            List atmList = em.createNativeQuery("select code from parameterinfo_report where reportname='BANK-ATM-STATUS'").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please fill data into parameterinfo report for BANK-ATM-STATUS");
            }
            Vector element = (Vector) atmList.get(0);
            String atmStatus = element.get(0).toString();

            atmList = em.createNativeQuery("select bank_short_name from mb_sms_sender_bank_detail").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define bank short name in mb sms sender bank detail.");
            }
            String bankCode = element.get(0).toString();

            atmList = em.createNativeQuery("select code from cbs_parameterinfo where name in('ACQUIRER-CODE')").getResultList();
            if (atmList.isEmpty()) {
                throw new ApplicationException("Please define acquirer code");
            }
            element = (Vector) atmList.get(0);
            if (element.get(0) == null || element.get(0).toString().equals("")) {
                throw new ApplicationException("Please define acquirer code in cbs parameterinfo.");
            }
            String acquirerCode = element.get(0).toString().trim();

            String sno = "", issuerFile = "", onUsFile = "", offUsFile = "", writeFolderPath = "";

            String osName = System.getProperty("os.name");
            File writeFolder = null;
            if (osName.equalsIgnoreCase("Linux")) {
                writeFolderPath = "/install/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            } else {
                writeFolderPath = "C:/ATM/";
                writeFolder = new File(writeFolderPath);
                if (!writeFolder.exists()) {
                    writeFolder.mkdir();
                }
            }

            if (atmStatus.equals("73")) {
                //Issuer File generation
                sno = getSerialNo(fileGenerationDt, "I").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                issuerFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".csv";
                issuerFile = writeFolderPath + issuerFile;
                issuerWriter = new FileWriter(issuerFile);
                //This will work for issuer, on-us, pos/ecom just after changing the processing code
                atmList = em.createNativeQuery("select stan_number,card_no,transaction_serial_number,account_type,account_number,"
                        + "branch_code,transaction_amount,transaction_date,transaction_time,value_date,atm_id,atm_location,"
                        + "batch_code,user_id,acquirerId,in_process_flag,remarks,tran_time,txn_fee,rev_br_code,rev_entry_date,"
                        + "rev_posting_date,rev_batch_code,rev_maker_time,rev_amount from "
                        + "(select m.processing_code,m.system_trace_number as stan_number,m.card_number as card_no,m.rrn as "
                        + "transaction_serial_number,substring(m.from_account_number,3,2) as account_type,m.from_account_number "
                        + "as account_number,substring(m.from_account_number,1,2) as branch_code,m.amount as transaction_amount,"
                        + "date_format(m.tran_date,'%Y%m%d') as transaction_date,concat(extract(hour from m.tran_time),"
                        + "extract(minute from m.tran_time),extract(second from m.tran_time)) as transaction_time,"
                        + "date_format(m.in_coming_date,'%Y%m%d') as value_date,m.terminal_id as atm_id,m.terminal_location as "
                        + "atm_location,ifnull(m.trsno,0) as batch_code,ifnull(m.authorization_id,'') as user_id,"
                        + "ifnull(m.acquiring_institution_code,'') as acquirerId,m.in_process_flag,(case when m.in_process_flag='S' "
                        + "then 'Issuer Withdrawal Success' else 'Issuer Withdrawal Fail' end) as remarks,m.tran_time,m.transaction_fee "
                        + "as txn_fee,ifnull(substring(r.from_account_number,1,2),'') as rev_br_code,ifnull(date_format(r.tran_date,'%Y%m%d'),'') "
                        + "as rev_entry_date,ifnull(date_format(r.in_coming_date,'%Y%m%d'),'') as rev_posting_date,ifnull(r.trsno,0) as "
                        + "rev_batch_code,ifnull(concat(extract(hour from r.tran_time),extract(minute from r.tran_time),"
                        + "extract(second from r.tran_time)),'') as rev_maker_time,ifnull(r.amount,0.00) as rev_amount from "
                        + "atm_normal_transaction_parameter m left join atm_reversal_transaction_parameter r on "
                        + "m.system_trace_number=r.original_system_trace_number and m.in_process_flag=r.in_process_flag and "
                        + "m.tran_date=r.tran_date and m.processing_code=r.processing_code and m.processing_code in('01') and "
                        + "m.in_process_flag='S' and m.tran_date='" + fileGenerationDt + "' order by m.tran_time) a "
                        + "where processing_code in('01') and transaction_date='" + fileGenerationDt + "'").getResultList();

                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        BigDecimal amount = new BigDecimal(element.get(6).toString().trim());
                        BigDecimal txnFee = new BigDecimal(element.get(18).toString().trim().substring(1));

                        String batchCode = new BigDecimal(element.get(12).toString().trim()).toString();
                        if (batchCode.contains(".")) {
                            batchCode = batchCode.substring(0, batchCode.indexOf("."));
                        }

                        String revBatchCode = "", revEntryDt = "", revPostingDt = "", revNarration = "", revMaker = "", revAmount = "";
                        String revBrCode = element.get(19).toString().trim();
                        if (!revBrCode.equals("")) {
                            revBatchCode = new BigDecimal(element.get(22).toString().trim()).toString();
                            if (revBatchCode.contains(".")) {
                                revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
                            }
                            BigDecimal revComingAmount = new BigDecimal(element.get(24).toString().trim());
                            revAmount = (amount.add(txnFee)).subtract(revComingAmount).toString();

                            revEntryDt = element.get(20).toString().trim();
                            revPostingDt = element.get(21).toString().trim();
                            revNarration = "Issuer Reversal Success";
                            //revMaker = "System";
                            revMaker = "";
                        }

                        String singleEntry = element.get(14).toString().trim() + ","
                                + element.get(2).toString().trim() + ","
                                + element.get(0).toString().trim() + ","
                                + element.get(5).toString().trim() + ","
                                + element.get(4).toString().trim() + ","
                                + ","
                                + element.get(10).toString().trim() + ","
                                + element.get(11).toString().trim().replaceAll("[\\W_]", " ") + ","
                                + amount.toString().trim() + ","
                                + element.get(1).toString().trim() + ","
                                + ","
                                + element.get(7).toString().trim() + ","
                                + element.get(9).toString().trim() + ","
                                + batchCode + ","
                                + element.get(16).toString().trim() + ","
                                + revBrCode.trim() + ","
                                + revEntryDt + ","
                                + revPostingDt + ","
                                + revNarration + ","
                                + revBatchCode + ","
                                + element.get(7).toString() + ","
                                + element.get(8).toString().trim() + ","
                                + revMaker + ","
                                + element.get(20).toString().trim() + ","
                                + element.get(23).toString().trim() + ","
                                + revAmount.toString().trim() + ","
                                + "1," + "\r\n";

                        issuerWriter.write(singleEntry);
                    }
                } else {
                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
                }

                String onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "I", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
            } else if (atmStatus.equals("65")) {
                sno = getSerialNo(fileGenerationDt, "I").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                issuerFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "I-" + sno + ".csv";
                issuerFile = writeFolderPath + issuerFile;
                issuerWriter = new FileWriter(issuerFile);

                atmList = em.createNativeQuery("select stan_number,card_no,transaction_serial_number,account_type,account_number,"
                        + "branch_code,transaction_amount,transaction_date,transaction_time,value_date,atm_id,atm_location,"
                        + "batch_code,user_id,acquirerId,in_process_flag,remarks,tran_time,txn_fee,rev_br_code,rev_entry_date,"
                        + "rev_posting_date,rev_batch_code,rev_maker_time,rev_amount from "
                        + "(select m.processing_code,m.system_trace_number as stan_number,m.card_number as card_no,m.rrn as "
                        + "transaction_serial_number,substring(m.from_account_number,3,2) as account_type,m.from_account_number "
                        + "as account_number,substring(m.from_account_number,1,2) as branch_code,m.amount as transaction_amount,"
                        + "date_format(m.tran_date,'%Y%m%d') as transaction_date,concat(extract(hour from m.tran_time),"
                        + "extract(minute from m.tran_time),extract(second from m.tran_time)) as transaction_time,"
                        + "date_format(m.in_coming_date,'%Y%m%d') as value_date,m.terminal_id as atm_id,m.terminal_location as "
                        + "atm_location,ifnull(m.trsno,0) as batch_code,ifnull(m.authorization_id,'') as user_id,"
                        + "ifnull(m.acquiring_institution_code,'') as acquirerId,m.in_process_flag,(case when m.in_process_flag='S' "
                        + "then 'Issuer Withdrawal Success' else 'Issuer Withdrawal Fail' end) as remarks,m.tran_time,m.transaction_fee "
                        + "as txn_fee,ifnull(substring(r.from_account_number,1,2),'') as rev_br_code,ifnull(date_format(r.tran_date,'%Y%m%d'),'') "
                        + "as rev_entry_date,ifnull(date_format(r.in_coming_date,'%Y%m%d'),'') as rev_posting_date,ifnull(r.trsno,0) as "
                        + "rev_batch_code,ifnull(concat(extract(hour from r.tran_time),extract(minute from r.tran_time),"
                        + "extract(second from r.tran_time)),'') as rev_maker_time,ifnull(r.amount,0.00) as rev_amount from "
                        + "atm_normal_transaction_parameter m left join atm_reversal_transaction_parameter r on "
                        + "m.system_trace_number=r.original_system_trace_number and m.in_process_flag=r.in_process_flag and "
                        + "m.tran_date=r.tran_date and m.processing_code=r.processing_code and m.processing_code in('01') and "
                        + "m.in_process_flag='S' and m.tran_date='" + fileGenerationDt + "' order by m.tran_time) a "
                        + "where processing_code in('01') and transaction_date='" + fileGenerationDt + "'").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        BigDecimal amount = new BigDecimal(element.get(6).toString().trim());
                        BigDecimal txnFee = new BigDecimal(element.get(18).toString().trim().substring(1));

                        String batchCode = new BigDecimal(element.get(12).toString().trim()).toString();
                        if (batchCode.contains(".")) {
                            batchCode = batchCode.substring(0, batchCode.indexOf("."));
                        }

                        String revBatchCode = "", revEntryDt = "", revPostingDt = "", revNarration = "", revMaker = "", revAmount = "";
                        String revBrCode = element.get(19).toString().trim();
                        if (!revBrCode.equals("")) {
                            revBatchCode = new BigDecimal(element.get(22).toString().trim()).toString();
                            if (revBatchCode.contains(".")) {
                                revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
                            }
                            BigDecimal revComingAmount = new BigDecimal(element.get(24).toString().trim());
                            revAmount = (amount.add(txnFee)).subtract(revComingAmount).toString();

                            revEntryDt = element.get(20).toString().trim();
                            revPostingDt = element.get(21).toString().trim();
                            revNarration = "Issuer Reversal Success";
                            //revMaker = "System";
                            revMaker = "";
                        }

                        String singleEntry = element.get(14).toString().trim() + ","
                                + element.get(2).toString().trim() + ","
                                + element.get(0).toString().trim() + ","
                                + element.get(5).toString().trim() + ","
                                + element.get(4).toString().trim() + ","
                                + ","
                                + element.get(10).toString().trim() + ","
                                + element.get(11).toString().trim().replaceAll("[\\W_]", " ") + ","
                                + amount.toString().trim() + ","
                                + element.get(1).toString().trim() + ","
                                + ","
                                + element.get(7).toString().trim() + ","
                                + element.get(9).toString().trim() + ","
                                + batchCode + ","
                                + element.get(16).toString().trim() + ","
                                + revBrCode.trim() + ","
                                + revEntryDt + ","
                                + revPostingDt + ","
                                + revNarration + ","
                                + revBatchCode + ","
                                + element.get(7).toString() + ","
                                + element.get(8).toString().trim() + ","
                                + revMaker + ","
                                + element.get(20).toString().trim() + ","
                                + element.get(23).toString().trim() + ","
                                + revAmount.toString().trim() + ","
                                + "1," + "\r\n";

                        issuerWriter.write(singleEntry);
                    }
                } else {
                    issuerWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
                }

                String onlyFileName = issuerFile.substring(issuerFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "I", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
                //On-Us File Generation
                sno = getSerialNo(fileGenerationDt, "O").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                onUsFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "O-" + sno + ".csv";
                onUsFile = writeFolderPath + onUsFile;
                onUsWriter = new FileWriter(onUsFile);

                atmList = em.createNativeQuery("select stan_number,card_no,transaction_serial_number,account_type,account_number,"
                        + "branch_code,transaction_amount,transaction_date,transaction_time,value_date,atm_id,atm_location,"
                        + "batch_code,user_id,acquirerId,in_process_flag,remarks,tran_time,txn_fee,rev_br_code,rev_entry_date,"
                        + "rev_posting_date,rev_batch_code,rev_maker_time,rev_amount from "
                        + "(select m.processing_code,m.system_trace_number as stan_number,m.card_number as card_no,m.rrn as "
                        + "transaction_serial_number,substring(m.from_account_number,3,2) as account_type,m.from_account_number "
                        + "as account_number,substring(m.from_account_number,1,2) as branch_code,m.amount as transaction_amount,"
                        + "date_format(m.tran_date,'%Y%m%d') as transaction_date,concat(extract(hour from m.tran_time),"
                        + "extract(minute from m.tran_time),extract(second from m.tran_time)) as transaction_time,"
                        + "date_format(m.in_coming_date,'%Y%m%d') as value_date,m.terminal_id as atm_id,m.terminal_location as "
                        + "atm_location,ifnull(m.trsno,0) as batch_code,ifnull(m.authorization_id,'') as user_id,"
                        + "ifnull(m.acquiring_institution_code,'') as acquirerId,m.in_process_flag,(case when m.in_process_flag='S' "
                        + "then 'On-Us Withdrawal Success' else 'On-Us Withdrawal Fail' end) as remarks,m.tran_time,m.transaction_fee "
                        + "as txn_fee,ifnull(substring(r.from_account_number,1,2),'') as rev_br_code,ifnull(date_format(r.tran_date,'%Y%m%d'),'') "
                        + "as rev_entry_date,ifnull(date_format(r.in_coming_date,'%Y%m%d'),'') as rev_posting_date,ifnull(r.trsno,0) as "
                        + "rev_batch_code,ifnull(concat(extract(hour from r.tran_time),extract(minute from r.tran_time),"
                        + "extract(second from r.tran_time)),'') as rev_maker_time,ifnull(r.amount,0.00) as rev_amount from "
                        + "atm_normal_transaction_parameter m left join atm_reversal_transaction_parameter r on "
                        + "m.system_trace_number=r.original_system_trace_number and m.in_process_flag=r.in_process_flag and "
                        + "m.tran_date=r.tran_date and m.processing_code=r.processing_code and m.processing_code in('00') and "
                        + "m.in_process_flag='S' and m.tran_date='" + fileGenerationDt + "' order by m.tran_time) a "
                        + "where processing_code in('00') and transaction_date='" + fileGenerationDt + "'").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        BigDecimal amount = new BigDecimal(element.get(6).toString().trim());
                        BigDecimal txnFee = new BigDecimal(element.get(18).toString().trim().substring(1));

                        String batchCode = new BigDecimal(element.get(12).toString().trim()).toString();
                        if (batchCode.contains(".")) {
                            batchCode = batchCode.substring(0, batchCode.indexOf("."));
                        }

                        String revBatchCode = "", revEntryDt = "", revPostingDt = "", revNarration = "", revMaker = "", revAmount = "";
                        String revBrCode = element.get(19).toString().trim();
                        if (!revBrCode.equals("")) {
                            revBatchCode = new BigDecimal(element.get(22).toString().trim()).toString();
                            if (revBatchCode.contains(".")) {
                                revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
                            }
                            BigDecimal revComingAmount = new BigDecimal(element.get(24).toString().trim());
                            revAmount = (amount.add(txnFee)).subtract(revComingAmount).toString();

                            revEntryDt = element.get(20).toString().trim();
                            revPostingDt = element.get(21).toString().trim();
                            revNarration = "On-Us Reversal Success";
//                            revMaker = "System";
                            revMaker = "";
                        }

                        String singleEntry = element.get(14).toString().trim() + ","
                                + element.get(2).toString().trim() + ","
                                + element.get(0).toString().trim() + ","
                                + element.get(5).toString().trim() + ","
                                + element.get(4).toString().trim() + ","
                                + ","
                                + element.get(10).toString().trim() + ","
                                + element.get(11).toString().trim().replaceAll("[\\W_]", " ") + ","
                                + amount.toString().trim() + ","
                                + element.get(1).toString().trim() + ","
                                + ","
                                + element.get(7).toString().trim() + ","
                                + element.get(9).toString().trim() + ","
                                + batchCode + ","
                                + element.get(16).toString().trim() + ","
                                + revBrCode.trim() + ","
                                + revEntryDt + ","
                                + revPostingDt + ","
                                + revNarration + ","
                                + revBatchCode + ","
                                + element.get(7).toString() + ","
                                + element.get(8).toString().trim() + ","
                                + revMaker + ","
                                + element.get(20).toString().trim() + ","
                                + element.get(23).toString().trim() + ","
                                + revAmount.toString().trim() + ","
                                + "3," + "\r\n";

                        onUsWriter.write(singleEntry);
                    }
                } else {
                    onUsWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
                }

                onlyFileName = onUsFile.substring(onUsFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "O", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
                //Off-Us File Generation
                sno = getSerialNo(fileGenerationDt, "A").toString();
                if (sno.length() < 2) {
                    sno = "0" + sno;
                }
                offUsFile = bankCode.toUpperCase() + new SimpleDateFormat("ddMMyy").format(sdf.parse(fileGenerationDt)) + "A-" + sno + ".csv";
                offUsFile = writeFolderPath + offUsFile;
                offUsWriter = new FileWriter(offUsFile);

                atmList = em.createNativeQuery("select a.processing_code,a.reversal_indicator,a.amount,a.terminal_id,"
                        + "a.system_trace_number,a.approval_code as referenceno,a.card_number,a.reserved,"
                        + "a.original_system_trace_number,date_format(a.tran_date,'%Y%m%d') as tran_dt,"
                        + "time_format(cast(a.tran_time as time), '%H%i%s') as transaction_time,date_format(a.in_coming_date,'%Y%m%d') "
                        + "as post_dt,a.in_process_flag,ifnull(a.trsno,0) as trsno,(case when a.in_process_flag='S' then 'Acquirer Withdrawal Success' "
                        + "else 'Acquirer Withdrawal Fail' end) as remarks,a.tran_time,date_format(b.tran_date,'%Y%m%d') as "
                        + "rev_tran_dt,date_format(b.in_coming_date,'%Y%m%d') as rev_post_dt,ifnull(b.trsno,0) as rev_batch,"
                        + "time_format(cast(b.tran_time as time), '%H%i%s') as rev_transaction_time,b.amount as rev_amount "
                        + "from atm_off_us_transaction_parameter a,atm_off_us_transaction_parameter b where a.processing_code="
                        + "b.processing_code and a.tran_date=b.tran_date and a.system_trace_number=b.original_system_trace_number "
                        + "and a.in_process_flag=b.in_process_flag and a.processing_code=19 and a.tran_date='" + fileGenerationDt + "' "
                        + "and a.in_process_flag='S' "
                        + "union all "
                        + "select processing_code,reversal_indicator,amount,terminal_id,system_trace_number,approval_code as "
                        + "referenceno,card_number,reserved,original_system_trace_number,date_format(tran_date,'%Y%m%d') as tran_dt, "
                        + "time_format(cast(tran_time as time), '%H%i%s') as transaction_time,date_format(in_coming_date,'%Y%m%d') as "
                        + "post_dt,in_process_flag,ifnull(trsno,0) as trsno,(case when in_process_flag='S' then 'Acquirer Withdrawal Success' "
                        + "else 'Acquirer Withdrawal Fail' end) as remarks,tran_time,'' as rev_tran_dt,'' as rev_post_dt, "
                        + "'' as rev_batch,'' as rev_transaction_time,'' as rev_amount from atm_off_us_transaction_parameter where "
                        + "tran_date='" + fileGenerationDt + "' and processing_code='19' and reversal_indicator='0' and system_trace_number "
                        + "not in(select original_system_trace_number from atm_off_us_transaction_parameter where "
                        + "tran_date='" + fileGenerationDt + "' and processing_code='19' and reversal_indicator='1' "
                        + "and in_process_flag='S')").getResultList();
                if (!atmList.isEmpty()) {
                    for (int i = 0; i < atmList.size(); i++) {
                        element = (Vector) atmList.get(i);
                        BigDecimal amount = new BigDecimal(element.get(2).toString().trim());
                        String batchCode = new BigDecimal(element.get(13).toString().trim()).toString();
                        if (batchCode.contains(".")) {
                            batchCode = batchCode.substring(0, batchCode.indexOf("."));
                        }

                        String revBatchCode = "", revPostingDt = "", revNarration = "", revMaker = "", revAmount = "";
                        String revEntryDt = element.get(16).toString().trim();
                        if (!revEntryDt.equals("")) {
                            revBatchCode = new BigDecimal(element.get(18).toString().trim()).toString();
                            if (revBatchCode.contains(".")) {
                                revBatchCode = revBatchCode.substring(0, revBatchCode.indexOf("."));
                            }

                            revPostingDt = element.get(17).toString().trim();
                            revNarration = "Acquirer Reversal Success";
//                            revMaker = "System";
                            revMaker = "";

                            BigDecimal revComingAmount = new BigDecimal(element.get(20).toString().trim());
                            revAmount = amount.subtract(revComingAmount).toString();
                        }

                        String singleEntry = acquirerCode + ","
                                + element.get(5).toString().trim() + ","
                                + element.get(4).toString().trim() + ","
                                + "," + "," + ","
                                + element.get(3).toString().trim() + ","
                                + element.get(3).toString().trim() + ","
                                + amount.toString().trim() + ","
                                + element.get(6).toString().trim() + ","
                                + ","
                                + element.get(9).toString().trim() + ","
                                + element.get(11).toString().trim() + ","
                                + batchCode + ","
                                + element.get(14).toString().trim() + ","
                                + ","
                                + revEntryDt + ","
                                + revPostingDt + ","
                                + revNarration + ","
                                + revBatchCode + ","
                                + element.get(9).toString().trim() + ","
                                + element.get(10).toString().trim() + ","
                                + revMaker + ","
                                + revEntryDt + ","
                                + element.get(19).toString().trim() + ","
                                + revAmount + ","
                                + "2" + "," + "\r\n";

                        offUsWriter.write(singleEntry);
                    }
                } else {
                    offUsWriter.write(ParseFileUtil.addTrailingSpaces("", 576) + "\r\n");
                }
                onlyFileName = offUsFile.substring(offUsFile.lastIndexOf("/") + 1);
                if (logForGeneratedFile(new BigInteger(sno), onlyFileName, "A", generatedBy, fileGenerationDt).equals("true")) {
                    files.add(onlyFileName);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                ex.printStackTrace();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        } finally {
            try {
                if (issuerWriter != null) {
                    issuerWriter.close();
                }
                if (onUsWriter != null) {
                    onUsWriter.close();
                }
                if (offUsWriter != null) {
                    offUsWriter.close();
                }
            } catch (Exception ex) {
                throw new ApplicationException(ex.getMessage());
            }
        }
        return files;
    }
}
