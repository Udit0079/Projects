/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.dto.report.CtsChequeStatusReportPojo;
import com.cbs.dto.report.CtsReportSortByAcType;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless(mappedName = "CtsReportManagementFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class CtsReportManagementFacade implements CtsReportManagementFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    @Override
    public List<CtsChequeStatusReportPojo> getCtsChequeStatusReportDetails(String destCode, String reportDt, int status, String clgType, int ctsSponsor) throws ApplicationException {
        long begin = System.nanoTime();
        List<CtsChequeStatusReportPojo> resultList = new ArrayList<CtsChequeStatusReportPojo>();
        List<String> bankList = new ArrayList<String>();
        List resultListSecond = new ArrayList();
        try {
            String addQuery = (ctsSponsor == 2) ? " and em_flag='" + clgType + "' " : "";

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String curDate = sdf.format(date);
            if (destCode.equalsIgnoreCase("0A")) {
                bankList = getBankDetails("90");
            } else {
                bankList = getBankDetails(destCode);
            }
            if (bankList.size() > 0) {
                String brName = bankList.get(3);
                if (reportDt.equalsIgnoreCase(curDate)) {
                    if (status == 0) {
                        if (destCode.equalsIgnoreCase("0A")) {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry WHERE date_format(DT,'%Y%m%d') = '" + reportDt + "' " + addQuery + " ORDER "
                                    + "BY TRAN_TIME").getResultList();
                        } else {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry WHERE DEST_BRANCH='" + destCode + "' AND "
                                    + "date_format(DT,'%Y%m%d') = '" + reportDt + "' " + addQuery + " ORDER "
                                    + "BY TRAN_TIME").getResultList();
                        }
                        if (resultListSecond.size() > 0) {
                            for (int i = 0; i < resultListSecond.size(); i++) {
                                CtsChequeStatusReportPojo tableObj = new CtsChequeStatusReportPojo();
                                Vector element = (Vector) resultListSecond.get(i);
                                tableObj.setBnkName(bankList.get(0));
                                tableObj.setBnkAdd(bankList.get(1));
                                tableObj.setBrncode(Integer.parseInt(bankList.get(2)));
                                tableObj.setAcType((element.get(2).toString() == null || element.get(2).toString().trim().equals("") || element.get(2).toString().trim().length() != 12) ? "" : element.get(2).toString().trim().substring(2, 4));
                                tableObj.setDestBranch(element.get(0).toString());
                                tableObj.setBatchNo(new BigInteger(element.get(1).toString()));
                                tableObj.setAcno(element.get(2).toString());
                                tableObj.setCustName(element.get(3).toString());
                                tableObj.setChequeNo(element.get(4).toString());
                                tableObj.setAmount(new BigDecimal(element.get(5).toString()));
                                tableObj.setFavourOf(element.get(6).toString());
                                tableObj.setDetails(element.get(7).toString());
                                tableObj.setInstDt(element.get(8).toString());
                                tableObj.setStatus(Integer.parseInt(element.get(9).toString()));
                                tableObj.setUserDetails(tableObj.getStatus() == 4 ? getRetReasonDesc(element.get(10).toString()) : element.get(10).toString());
                                tableObj.setTranTime(java.sql.Timestamp.valueOf(element.get(11).toString()));
                                tableObj.setPrbankCode(element.get(12).toString());
                                tableObj.setBranchName(element.get(13).toString());
                                resultList.add(tableObj);
                            }
                            Collections.sort(resultList, new CtsReportSortByAcType());
                        }
                    } else {
                        if (destCode.equalsIgnoreCase("0A")) {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry WHERE date_format(DT,'%Y%m%d') = '" + reportDt + "' AND "
                                    + "STATUS=" + status + " " + addQuery + " ORDER BY TRAN_TIME").getResultList();
                        } else {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,"
                                    + "INST_NO,INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,"
                                    + "USERDETAILS,TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),"
                                    + "ifnull(ITEM_SEQ_NO,'') FROM cts_clg_in_entry WHERE DEST_BRANCH='" + destCode + "' AND "
                                    + "date_format(DT,'%Y%m%d') = '" + reportDt + "' AND STATUS=" + status + " " + addQuery + " ORDER "
                                    + "BY TRAN_TIME").getResultList();
                        }
                        if (resultListSecond.size() > 0) {
                            for (int i = 0; i < resultListSecond.size(); i++) {
                                CtsChequeStatusReportPojo tableObj = new CtsChequeStatusReportPojo();
                                Vector element = (Vector) resultListSecond.get(i);
                                tableObj.setBnkName(bankList.get(0));
                                tableObj.setBnkAdd(bankList.get(1));
                                tableObj.setBrncode(Integer.parseInt(bankList.get(2)));
                                tableObj.setAcType((element.get(2).toString() == null || element.get(2).toString().trim().equals("") || element.get(2).toString().trim().length() != 12) ? "" : element.get(2).toString().trim().substring(2, 4));
                                tableObj.setDestBranch(element.get(0).toString());
                                tableObj.setBatchNo(new BigInteger(element.get(1).toString()));
                                tableObj.setAcno(element.get(2).toString());
                                tableObj.setCustName(element.get(3).toString());
                                tableObj.setChequeNo(element.get(4).toString());
                                tableObj.setAmount(new BigDecimal(element.get(5).toString()));
                                tableObj.setFavourOf(element.get(6).toString());
                                tableObj.setDetails(element.get(7).toString());
                                tableObj.setInstDt(element.get(8).toString());
                                tableObj.setStatus(Integer.parseInt(element.get(9).toString()));
                                tableObj.setUserDetails(tableObj.getStatus() == 4 ? getRetReasonDesc(element.get(10).toString()) : element.get(10).toString());
                                tableObj.setTranTime(java.sql.Timestamp.valueOf(element.get(11).toString()));
                                tableObj.setPrbankCode(element.get(12).toString());
                                tableObj.setBranchName(element.get(13).toString());
                                resultList.add(tableObj);
                            }
                            Collections.sort(resultList, new CtsReportSortByAcType());
                        }
                    }
                } else {
                    if (status == 0) {
                        if (destCode.equalsIgnoreCase("0A")) {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry_history WHERE date_format(DT,'%Y%m%d') = '" + reportDt + "' "
                                    + "" + addQuery + " ORDER BY TRAN_TIME").getResultList();
                        } else {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry_history WHERE DEST_BRANCH='" + destCode + "' AND "
                                    + "date_format(DT,'%Y%m%d') = '" + reportDt + "' " + addQuery + " ORDER "
                                    + "BY TRAN_TIME").getResultList();
                        }
                        if (resultListSecond.size() > 0) {
                            for (int i = 0; i < resultListSecond.size(); i++) {
                                CtsChequeStatusReportPojo tableObj = new CtsChequeStatusReportPojo();
                                Vector element = (Vector) resultListSecond.get(i);
                                tableObj.setBnkName(bankList.get(0));
                                tableObj.setBnkAdd(bankList.get(1));
                                tableObj.setBrncode(Integer.parseInt(bankList.get(2)));
                                tableObj.setAcType((element.get(2).toString() == null || element.get(2).toString().trim().equals("") || element.get(2).toString().trim().length() != 12) ? "" : element.get(2).toString().trim().substring(2, 4));
                                tableObj.setDestBranch(element.get(0).toString());
                                tableObj.setBatchNo(new BigInteger(element.get(1).toString()));
                                tableObj.setAcno(element.get(2).toString());
                                tableObj.setCustName(element.get(3).toString());
                                tableObj.setChequeNo(element.get(4).toString());
                                tableObj.setAmount(new BigDecimal(element.get(5).toString()));
                                tableObj.setFavourOf(element.get(6).toString());
                                tableObj.setDetails(element.get(7).toString());
                                tableObj.setInstDt(element.get(8).toString());
                                tableObj.setStatus(Integer.parseInt(element.get(9).toString()));
                                tableObj.setUserDetails(tableObj.getStatus() == 4 ? getRetReasonDesc(element.get(10).toString()) : element.get(10).toString());
                                tableObj.setTranTime(java.sql.Timestamp.valueOf(element.get(11).toString()));
                                tableObj.setPrbankCode(element.get(12).toString());
                                tableObj.setBranchName(element.get(13).toString());
                                resultList.add(tableObj);
                            }
                            Collections.sort(resultList, new CtsReportSortByAcType());
                        }
                    } else {
                        if (destCode.equalsIgnoreCase("0A")) {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry_history WHERE date_format(DT,'%Y%m%d') = '" + reportDt + "' AND "
                                    + "STATUS=" + status + " " + addQuery + " ORDER BY TRAN_TIME").getResultList();
                        } else {
                            resultListSecond = em.createNativeQuery("SELECT DISTINCT DEST_BRANCH,BATCH_NO,ACNO,CUSTNAME,INST_NO,"
                                    + "INST_AMT,FAVOR_OF,DETAILS,date_format(INST_DT,'%d/%m/%Y') AS INST_DT,STATUS,USERDETAILS,"
                                    + "TRAN_TIME,PRBANKCODE,'" + brName + "',ifnull(rbirefno,''),ifnull(ITEM_SEQ_NO,'') FROM "
                                    + "cts_clg_in_entry_history WHERE DEST_BRANCH='" + destCode + "' AND "
                                    + "date_format(DT,'%Y%m%d') = '" + reportDt + "' AND STATUS=" + status + " " + addQuery + " ORDER "
                                    + "BY TRAN_TIME").getResultList();
                        }
                        if (resultListSecond.size() > 0) {
                            for (int i = 0; i < resultListSecond.size(); i++) {
                                CtsChequeStatusReportPojo tableObj = new CtsChequeStatusReportPojo();
                                Vector element = (Vector) resultListSecond.get(i);
                                tableObj.setBnkName(bankList.get(0));
                                tableObj.setBnkAdd(bankList.get(1));
                                tableObj.setBrncode(Integer.parseInt(bankList.get(2)));
                                tableObj.setAcType((element.get(2).toString() == null || element.get(2).toString().trim().equals("") || element.get(2).toString().trim().length() != 12) ? "" : element.get(2).toString().trim().substring(2, 4));
                                tableObj.setDestBranch(element.get(0).toString());
                                tableObj.setBatchNo(new BigInteger(element.get(1).toString()));
                                tableObj.setAcno(element.get(2).toString());
                                tableObj.setCustName(element.get(3).toString());
                                tableObj.setChequeNo(element.get(4).toString());
                                tableObj.setAmount(new BigDecimal(element.get(5).toString()));
                                tableObj.setFavourOf(element.get(6).toString());
                                tableObj.setDetails(element.get(7).toString());
                                tableObj.setInstDt(element.get(8).toString());
                                tableObj.setStatus(Integer.parseInt(element.get(9).toString()));
                                tableObj.setUserDetails(tableObj.getStatus() == 4 ? getRetReasonDesc(element.get(10).toString()) : element.get(10).toString());
                                tableObj.setTranTime(java.sql.Timestamp.valueOf(element.get(11).toString()));
                                tableObj.setPrbankCode(element.get(12).toString());
                                tableObj.setBranchName(element.get(13).toString());
                                resultList.add(tableObj);
                            }
                            Collections.sort(resultList, new CtsReportSortByAcType());
                        }
                    }
                }
            }
            System.out.println("Returned ResultList Size----->" + resultList.size());
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        System.out.println("Execution time for getCtsChequeStatusReportDetails() method is " + (System.nanoTime() - begin) * 0.000000001 + " seconds");
        return resultList;
    }

    public List<String> getBankDetails(String orgCode) throws ApplicationException {
        List<String> bankList = new ArrayList<String>();
        try {
            List resultList = em.createNativeQuery("select a.bankName,a.bankaddress,b.brncode,b.branchname from bnkadd a,branchmaster b "
                    + " where a.alphacode = b.alphacode and b.brncode = cast('" + orgCode + "' as unsigned)").getResultList();
            if (resultList.size() > 0) {
                for (int j = 0; j < resultList.size(); j++) {
                    Vector element = (Vector) resultList.get(j);
                    bankList.add(element.get(0).toString());
                    bankList.add(element.get(1).toString());
                    bankList.add(element.get(2).toString());
                    bankList.add(element.get(3).toString());
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return bankList;
    }

    public String getRetReasonDesc(String returnCode) throws Exception {
        List list = em.createNativeQuery("select ifnull(description,'') as description from codebook where groupcode=13 and code=" + Integer.parseInt(returnCode) + "").getResultList();
        if (list.isEmpty()) {
            throw new Exception("There should be return reason for code :" + returnCode);
        }
        Vector ele = (Vector) list.get(0);
        return ele.get(0).toString();
    }
}
