/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.constant.CbsConstant;
import com.cbs.exception.ApplicationException;
import com.cbs.utils.CbsUtil;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.Query;
//import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
@Stateless(mappedName = "SavingAccountProductFacade")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
public class SavingAccountProductFacade implements SavingAccountProductFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;
    SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    /**
     * monthEndFlag will be: M- Month End, C-Central Day End
     */
    public String updateDailySavingProductAtCentral(String processFlag) throws ApplicationException {
        String message = "";
//        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        try {
            List accountCodeList = em.createNativeQuery("SELECT DISTINCT(ACCTCODE) FROM accounttypemaster WHERE ACCTNATURE IN('" + CbsConstant.SAVING_AC + "') ORDER BY ACCTCODE").getResultList();
            List branchList = em.createNativeQuery("SELECT BRNCODE FROM branchmaster WHERE ALPHACODE IN(SELECT DISTINCT ALPHACODE FROM bnkadd WHERE ALPHACODE<>'HO')").getResultList();
            for (int i = 0; i < branchList.size(); i++) {
                Vector brElement = (Vector) branchList.get(i);
                int brncode = Integer.parseInt(brElement.get(0).toString());

                for (int j = 0; j < accountCodeList.size(); j++) {
                    Vector accodeElement = (Vector) accountCodeList.get(j);
                    String accode = accodeElement.get(0).toString();

                    /**
                     * *Evaluation of business date**
                     */
                    List businessDtList = em.createNativeQuery("SELECT DATE_FORMAT(DATE,'%Y%m%d') FROM cbs_bankdays WHERE DAYBEGINFLAG='Y' AND DAYENDFLAG='N'").getResultList();
                    Vector businessElement = (Vector) businessDtList.get(0);
                    String businessDt = businessElement.get(0).toString();
                    String currentDtMonth = businessDt.substring(4, 6);

                    /**
                     * *Checking of interest posting flag for branch and
                     * account code wise**
                     */
                    String postFlag = "";
                    List intPostList = em.createNativeQuery("SELECT DISTINCT(POST_FLAG) FROM cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND DATE_FORMAT(TO_DT,'%Y%m%d')='" + businessDt + "'").getResultList();
                    if (!intPostList.isEmpty()) {
                        Vector intPostElement = (Vector) intPostList.get(0);
                        postFlag = intPostElement.get(0).toString();
                    }

                    /**
                     * *Month-end date of interest posting month calculation**
                     */
                    String dayBeginFlag = "", intPostMendDt = "", intPostMonth = "";
                    List intPostMendDtList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(TO_DT,'%Y%m%d')) FROM cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND POST_FLAG='N'").getResultList();
                    if (!intPostMendDtList.isEmpty()) {
                        Vector intPostMendDtElement = (Vector) intPostMendDtList.get(0);
                        intPostMendDt = intPostMendDtElement.get(0).toString();
                        intPostMonth = intPostMendDt.substring(4, 6);
                    } else {
                        List intPostMendDateList = em.createNativeQuery("SELECT MAX(DATE_FORMAT(TO_DT,'%Y%m%d')) FROM cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND POST_FLAG='Y' AND TO_DT<>''").getResultList();
                        Vector intPostMendDtElement = (Vector) intPostMendDateList.get(0);
                        intPostMendDt = intPostMendDtElement.get(0).toString();
                        intPostMonth = intPostMendDt.substring(4, 6);
                    }

                    /**
                     * *Daybeginflag checking on month end date**
                     */
                    List dayFlagList = em.createNativeQuery("SELECT DAYBEGINFLAG FROM bankdays WHERE DATE='" + intPostMendDt + "' AND CAST(BRNCODE AS unsigned)=" + brncode + "").getResultList();
                    if (!dayFlagList.isEmpty()) {
                        Vector mElement = (Vector) dayFlagList.get(0);
                        dayBeginFlag = mElement.get(0).toString();
                    }


                    /**
                     * *Either it is not a day for interest posting or interest
                     * is not posted on that day**
                     */
                    if (intPostList.isEmpty()) {
                        List dataInProductTableList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM cbs_saving_account_product WHERE DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + businessDt + "' AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                        if (dataInProductTableList.isEmpty()) {

                            String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                            if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                                return message = everyDayProcessMsg;
                            }

                            if (yyyyMMdd.parse(businessDt).before(yyyyMMdd.parse(intPostMendDt)) && dayBeginFlag.equalsIgnoreCase("H") && processFlag.equalsIgnoreCase("M") && currentDtMonth.equalsIgnoreCase(intPostMonth)) {
                                List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product WHERE ACNO_STATUS<>9 AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                                if (!allAcnoList.isEmpty()) {
                                    for (int p = 0; p < allAcnoList.size(); p++) {
                                        Vector allAcnoElement = (Vector) allAcnoList.get(p);
                                        String singleAcno = allAcnoElement.get(0).toString();

                                        String[] data = getMaxTransaction(singleAcno);
                                        String maxTransactionFrDt = data[0];

                                        BigDecimal outStAmount = getOutStandingAmount(singleAcno, intPostMendDt);
                                        Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStAmount + ",TO_DATE='" + intPostMendDt + "' WHERE ACNO='" + singleAcno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                                        int updateValue = updateProductTable.executeUpdate();
                                        if (updateValue <= 0) {
                                            return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                                        }
                                    }
                                }
                            }
                        }
                    } /**
                     * *Interest has been posted on that day**
                     */
                    else if ((!intPostList.isEmpty()) && (postFlag.equalsIgnoreCase("Y"))) {
                        /**
                         * *Calculation of initialzing date**
                         */
                        String initializingDate = CbsUtil.dateAdd(intPostMendDt, 1);
                        /**
                         * *Data insertion in product history table**
                         */
                        Query insertProduct = em.createNativeQuery("INSERT INTO cbs_saving_account_product_history SELECT C.ACNO,C.PRODUCT,C.FROM_DATE,C.TO_DATE,C.INT_CALC_START_DATE,C.ACNO_STATUSC,C.ROI "
                                + "FROM cbs_saving_account_product C,accountmaster A WHERE A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "'");
                        int insertNo = insertProduct.executeUpdate();
                        if (insertNo <= 0) {
                            return message = "Insertion problem in CBS SAVING ACCOUNT PRODUCT HISTORY for account code " + accode + " and branch " + brncode;
                        }
                        /**
                         * *Deletion of data from product table**
                         */
                        Query deleteProduct = em.createNativeQuery("DELETE C FROM cbs_saving_account_product C INNER JOIN accountmaster A ON A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "'");
                        int deleteProductNo = deleteProduct.executeUpdate();
                        if (deleteProductNo <= 0) {
                            return message = "Deletion problem from CBS SAVING ACCOUNT PRODUCT for account code " + accode + " and branch " + brncode;
                        }
                        /**
                         * *Insertion of initialization data into product
                         * table**
                         */
                        List acnoList = em.createNativeQuery("SELECT A.ACNO,A.ACCSTATUS,C.INTEREST_TABLE_CODE FROM accountmaster A,cbs_loan_acc_mast_sec C WHERE A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCSTATUS<>9 AND A.ACCTTYPE='" + accode + "'").getResultList();
                        if (!acnoList.isEmpty()) {
                            for (int u = 0; u < acnoList.size(); u++) {
                                Vector acnoElement = (Vector) acnoList.get(u);
                                String activeAcno = acnoElement.get(0).toString();
                                String activeStatus = acnoElement.get(1).toString();
                                String intTableCode = acnoElement.get(2).toString();
                                /**
                                 * *Outstanding amount**
                                 */
                                BigDecimal amount = getOutStandingAmount(activeAcno, intPostMendDt);
                                double roi = getROI(intTableCode, amount.doubleValue(), intPostMendDt);
                                Query initialInsert = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + activeAcno + "'," + amount + ",'" + initializingDate + "','" + initializingDate + "','" + initializingDate + "'," + activeStatus + "," + roi + ")");
                                int initialNo = initialInsert.executeUpdate();
                                if (initialNo <= 0) {
                                    return message = "Insertion problem in CBS SAVING ACCOUNT PRODUCT for account code " + accode + " and branch " + brncode;
                                }
                            }
                        }
                    } else if ((!intPostList.isEmpty()) && (postFlag.equalsIgnoreCase("N"))) {
                        String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                        if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                            return message = everyDayProcessMsg;
                        }

                        List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product WHERE ACNO_STATUS<>9 AND DATE_FORMAT(FROM_DATE,'%Y%m%d')<>'" + businessDt + "' AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                        if (!allAcnoList.isEmpty()) {
                            for (int p = 0; p < allAcnoList.size(); p++) {
                                Vector allAcnoElement = (Vector) allAcnoList.get(p);
                                String singleAcno = allAcnoElement.get(0).toString();

                                String[] data = getMaxTransaction(singleAcno);
                                String maxTransactionFrDt = data[0];

                                BigDecimal outStAmount = getOutStandingAmount(singleAcno, businessDt);
                                Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStAmount + ",TO_DATE='" + businessDt + "' WHERE ACNO='" + singleAcno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                                int updateValue = updateProductTable.executeUpdate();
                                if (updateValue <= 0) {
                                    return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                                }
                            }
                        }
                    }
                }
            }
            message = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String updateDailySavingProductAtMonthEnd(int brncode, String processFlag) throws ApplicationException {
        String message = "";
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        try {
            List accountCodeList = em.createNativeQuery("SELECT DISTINCT(ACCTCODE) FROM accounttypemaster WHERE ACCTNATURE IN('" + CbsConstant.SAVING_AC + "') ORDER BY ACCTCODE").getResultList();
            for (int j = 0; j < accountCodeList.size(); j++) {
                Vector accodeElement = (Vector) accountCodeList.get(j);
                String accode = accodeElement.get(0).toString();

                /**
                 * *Evaluation of business date**
                 */
                List businessDtList = em.createNativeQuery("SELECT DATE_FORMAT(DATE,'%Y%m%d') FROM cbs_bankdays WHERE DAYBEGINFLAG='Y' AND DAYENDFLAG='N'").getResultList();
                Vector businessElement = (Vector) businessDtList.get(0);
                String businessDt = businessElement.get(0).toString();
                String currentDtMonth = businessDt.substring(4, 6);

                /**
                 * *Checking of interest posting flag for branch and account
                 * code wise**
                 */
                String postFlag = "";
                List intPostList = em.createNativeQuery("SELECT DISTINCT(POST_FLAG) FROM cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND DATE_FORMAT(TO_DT,'%Y%m%d')='" + businessDt + "'").getResultList();
                if (!intPostList.isEmpty()) {
                    Vector intPostElement = (Vector) intPostList.get(0);
                    postFlag = intPostElement.get(0).toString();
                }

                /**
                 * *Month-end date of interest posting month calculation**
                 */
                List intPostMendDtList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(TO_DT,'%Y%m%d')) FROM cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND POST_FLAG='N'").getResultList();
                Vector intPostMendDtElement = (Vector) intPostMendDtList.get(0);
                String intPostMendDt = intPostMendDtElement.get(0).toString();
                String intPostMonth = intPostMendDt.substring(4, 6);

                /**
                 * *Daybeginflag checking on month end date**
                 */
                String dayBeginFlag = "";
                List dayFlagList = em.createNativeQuery("SELECT DAYBEGINFLAG FROM bankdays WHERE DATE='" + intPostMendDt + "' AND CAST(BRNCODE AS unsigned)=" + brncode + "").getResultList();
                if (!dayFlagList.isEmpty()) {
                    Vector mElement = (Vector) dayFlagList.get(0);
                    dayBeginFlag = mElement.get(0).toString();
                }

                /**
                 * *Either it is not a day for interest posting or interest is
                 * not posted on that day**
                 */
                if (intPostList.isEmpty()) {
                    List dataInProductTableList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM cbs_saving_account_product WHERE DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + businessDt + "' AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                    if (dataInProductTableList.isEmpty()) {

                        String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                        if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                            return message = everyDayProcessMsg;
                        }

                        if (yyyyMMdd.parse(businessDt).before(yyyyMMdd.parse(intPostMendDt)) && dayBeginFlag.equalsIgnoreCase("H") && processFlag.equalsIgnoreCase("M") && currentDtMonth.equalsIgnoreCase(intPostMonth)) {
                            List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product WHERE ACNO_STATUS<>9 AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                            if (!allAcnoList.isEmpty()) {
                                for (int p = 0; p < allAcnoList.size(); p++) {
                                    Vector allAcnoElement = (Vector) allAcnoList.get(p);
                                    String singleAcno = allAcnoElement.get(0).toString();

                                    String[] data = getMaxTransaction(singleAcno);
                                    String maxTransactionFrDt = data[0];

                                    BigDecimal outStAmount = getOutStandingAmount(singleAcno, intPostMendDt);
                                    Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStAmount + ",TO_DATE='" + intPostMendDt + "' WHERE ACNO='" + singleAcno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                                    int updateValue = updateProductTable.executeUpdate();
                                    if (updateValue <= 0) {
                                        return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                                    }
                                }
                            }
                        }
                    }
                } else if ((!intPostList.isEmpty()) && (postFlag.equalsIgnoreCase("N"))) {
                    String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                    if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                        return message = everyDayProcessMsg;
                    }

                    List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product WHERE ACNO_STATUS<>9 AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "' AND ACNO NOT IN(SELECT DISTINCT(R.ACNO) FROM recon R ,accountmaster A WHERE A.ACNO=R.ACNO AND A.ACCSTATUS<>9 AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "' AND DATE_FORMAT(R.DT,'%Y%m%d')='" + businessDt + "')").getResultList();
                    if (!allAcnoList.isEmpty()) {
                        for (int p = 0; p < allAcnoList.size(); p++) {
                            Vector allAcnoElement = (Vector) allAcnoList.get(p);
                            String singleAcno = allAcnoElement.get(0).toString();

                            String[] data = getMaxTransaction(singleAcno);
                            String maxTransactionFrDt = data[0];

                            BigDecimal outStAmount = getOutStandingAmount(singleAcno, businessDt);
                            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStAmount + ",TO_DATE='" + businessDt + "' WHERE ACNO='" + singleAcno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                            int updateValue = updateProductTable.executeUpdate();
                            if (updateValue <= 0) {
                                return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                            }
                        }
                    }
                }
            }
            message = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    public String everyDayProductProcess(int brncode, String accode, String businessDt) {
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            /**
             * *All account having transaction on that particular day**
             */
            List accountList = em.createNativeQuery("SELECT DISTINCT(A.ACNO),A.OPENINGDT,A.ACCSTATUS,C.INTEREST_TABLE_CODE FROM recon R,accountmaster A,cbs_loan_acc_mast_sec C WHERE A.ACNO=R.ACNO AND A.ACNO=C.ACNO "
                    + "AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "' AND A.ACCSTATUS<>9 AND DATE_FORMAT(R.DT,'%Y%m%d')='" + businessDt + "'").getResultList();
            if (!accountList.isEmpty()) {
                for (int k = 0; k < accountList.size(); k++) {
                    Vector accountElement = (Vector) accountList.get(k);
                    String acno = accountElement.get(0).toString();
                    String openingDt = accountElement.get(1).toString();
                    String acnoStatus = accountElement.get(2).toString();
                    String intTableCode = accountElement.get(3).toString();

                    /**
                     * *Comparison of opening date from businessDt date**
                     */
                    int openDtCompare = yyyyMMdd.parse(openingDt).compareTo(yyyyMMdd.parse(businessDt));
                    /**
                     * *Account is open on current day**
                     */
                    if (openDtCompare == 0) {
                        /**
                         * *Processing of product calculation either on per day
                         * at central level or at month end day if month end day
                         * is not holiday**
                         */
                        BigDecimal outStandAmount = getOutStandingAmount(acno, businessDt);
                        double roi = getROI(intTableCode, outStandAmount.doubleValue(), businessDt);
                        Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + outStandAmount + ",'" + businessDt + "','" + businessDt + "','" + businessDt + "'," + acnoStatus + "," + roi + ")");
                        int insertValue = insertProductTable.executeUpdate();
                        if (insertValue <= 0) {
                            return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                        }

                    }/**
                     * *Account is open in previous days**
                     */
                    else if (openDtCompare < 0) {
                        /**
                         * *To check whether a particular account has value
                         * date transaction or not**
                         */
                        String msg = checkvalueDtTransaction(acno, businessDt);
                        /**
                         * *There is no value date case**
                         */
                        if (msg.equalsIgnoreCase("FALSE")) {
                            List prevMaxTranDtList = em.createNativeQuery("SELECT DATE_FORMAT(FROM_DATE,'%Y%m%d'),DATE_FORMAT(INT_CALC_START_DATE,'%Y%m%d') FROM cbs_saving_account_product "
                                    + "WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d') = (SELECT MAX(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM cbs_saving_account_product WHERE ACNO='" + acno + "')").getResultList();
                            Vector prevMaxTranDtElement = (Vector) prevMaxTranDtList.get(0);
                            String maxFromTranDt = prevMaxTranDtElement.get(0).toString();
                            String fromIntPostDt = prevMaxTranDtElement.get(1).toString();

                            BigDecimal outStandAmount = getOutStandingAmount(acno, businessDt);

                            /**
                             * *Data insertion on business date**
                             */
                            double roi = getROI(intTableCode, outStandAmount.doubleValue(), businessDt);
                            Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + outStandAmount + ",'" + businessDt + "','" + businessDt + "','" + fromIntPostDt + "'," + acnoStatus + "," + roi + ")");
                            int insertValue = insertProductTable.executeUpdate();
                            if (insertValue <= 0) {
                                return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                            }
                            /**
                             * *To date updation on previous max transaction
                             * date for that particular account**
                             */
                            String preTranToDt = CbsUtil.dateAdd(businessDt, -1);
                            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + preTranToDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxFromTranDt + "'");
                            int updateValue = updateProductTable.executeUpdate();
                            if (updateValue <= 0) {
                                return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                            }
                        } /**
                         * *Value date case**
                         */
                        else {
                            /**
                             * *Calculation of min value for that particular
                             * account on that particular business date**
                             */
                            List minValueDtList = em.createNativeQuery("SELECT MIN(DATE_FORMAT(R.VALUEDT,'%Y%m%d')) FROM recon R WHERE ACNO='" + acno + "' AND DATE_FORMAT(R.DT,'%Y%m%d') = '" + businessDt + "'").getResultList();
                            Vector minValueDtElement = (Vector) minValueDtList.get(0);
                            String minValueDt = minValueDtElement.get(0).toString();

                            /**
                             * *Next interest posted date checking after min
                             * value date**
                             */
                            List nextIntPostList = em.createNativeQuery("SELECT DATE_FORMAT(VALUEDT,'%Y-%m-%d') FROM recon WHERE TRANDESC IN (3,4) AND ACNO ='" + acno + "' AND DT >= '" + minValueDt + "'").getResultList();
                            if (nextIntPostList.isEmpty()) {
                                String[] data = getMaxTranBeforeValueDt(acno, minValueDt);
                                String maxTranFrDt = data[0];
                                String maxTranIntCalcDt = data[1];

                                List valueDtTranList = em.createNativeQuery("SELECT ACNO FROM cbs_saving_account_product WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + minValueDt + "'").getResultList();
                                if (valueDtTranList.isEmpty()) {
                                    Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + CbsUtil.dateAdd(minValueDt, -1) + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTranFrDt + "'");
                                    int updateValue = updateProductTable.executeUpdate();
                                    if (updateValue <= 0) {
                                        return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                    }

                                    BigDecimal outStandAmount = getOutStandingAmount(acno, minValueDt);
                                    double roi = getROI(intTableCode, outStandAmount.doubleValue(), minValueDt);

                                    Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + outStandAmount + ",'" + minValueDt + "','" + minValueDt + "','" + maxTranIntCalcDt + "'," + acnoStatus + "," + roi + ")");
                                    int insertValue = insertProductTable.executeUpdate();
                                    if (insertValue <= 0) {
                                        return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                    }
                                } else {
                                    BigDecimal outStandAmount = getOutStandingAmount(acno, minValueDt);

                                    Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStandAmount + ",TO_DATE='" + minValueDt + "',INT_CALC_START_DATE='" + maxTranIntCalcDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + minValueDt + "'");
                                    int updateValue = updateProductTable.executeUpdate();
                                    if (updateValue <= 0) {
                                        return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                    }
                                }
                                List intervalFrList = em.createNativeQuery("SELECT DATE_FORMAT(FROM_DATE,'%Y%m%d') FROM cbs_saving_account_product WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')>'" + minValueDt + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')<'" + businessDt + "'").getResultList();
                                if (!intervalFrList.isEmpty()) {
                                    for (int m = 0; m < intervalFrList.size(); m++) {
                                        Vector intervalFrElement = (Vector) intervalFrList.get(m);
                                        String intervalFrDt = intervalFrElement.get(0).toString();

                                        String[] intervalData = getMaxTranBeforeValueDt(acno, intervalFrDt);
                                        String intervalTranFrDt = intervalData[0];
                                        String intervalTranIntCalcDt = intervalData[1];

                                        String updateMsg = updateProduct(acno, intervalTranFrDt, intervalTranIntCalcDt, intervalFrDt);
                                        if (!updateMsg.equalsIgnoreCase("true")) {
                                            return updateMsg;
                                        }
                                    }
                                }
                                /**
                                 * *Product at business date**
                                 */
                                String[] intervalData = getMaxTranBeforeValueDt(acno, businessDt);
                                String intervalTranFrDt = intervalData[0];
                                String intervalTranIntCalcDt = intervalData[1];

                                Query updateProductTableForPrevTran = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + CbsUtil.dateAdd(businessDt, -1) + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + intervalTranFrDt + "'");
                                int updatePreValue = updateProductTableForPrevTran.executeUpdate();
                                if (updatePreValue <= 0) {
                                    return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                }

                                BigDecimal outStandAmountAtBusinessDt = getOutStandingAmount(acno, businessDt);
                                double roi = getROI(intTableCode, outStandAmountAtBusinessDt.doubleValue(), businessDt);
                                Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + outStandAmountAtBusinessDt + ",'" + businessDt + "','" + businessDt + "','" + intervalTranIntCalcDt + "'," + acnoStatus + "," + roi + ")");
                                int insertValue = insertProductTable.executeUpdate();
                                if (insertValue <= 0) {
                                    return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                }

                            } /**
                             * *If interest has been posted after value date
                             * transaction**
                             */
                            else {
//                                List<String> forwardintPostList = new ArrayList<String>();
//                                for (int s = 0; s < nextIntPostList.size(); s++) {
//                                    Vector nextIntPostElement = (Vector) nextIntPostList.get(s);
//                                    String nextIntPostDate = nextIntPostElement.get(0).toString();
//                                    forwardintPostList.add(nextIntPostDate);
//                                }
                                /**
                                 * *Checking of last posting date before value
                                 * date**
                                 */
                                List lastPostingDtList = em.createNativeQuery("SELECT DATE_FORMAT(VALUEDT,'%Y%m%d') FROM recon WHERE TRANDESC IN (3,4) AND ACNO ='" + acno + "' "
                                        + "AND DT=(SELECT MAX(DT) FROM recon WHERE TRANDESC IN (3,4) AND ACNO ='" + acno + "' AND DT < '" + minValueDt + "')").getResultList();

                                Vector lastPostingDtElement = (Vector) lastPostingDtList.get(0);
                                String lastPostingDt = lastPostingDtElement.get(0).toString();
                                String lastFromDt = CbsUtil.dateAdd(lastPostingDt, 1);

//                                List intTableCodeList = em.createNativeQuery("SELECT A.INTEREST_TABLE_CODE FROM cbs_loan_acc_mast_sec A, accountmaster B "
//                                        + "WHERE A.ACNO = B.ACNO AND B.ACCSTATUS NOT IN (9) AND  B.ACNO = '" + acno + "' AND B.OPENINGDT <= '" + businessDt + "' AND CAST(B.CURBRCODE AS unsigned)= " + brncode + " ORDER BY B.ACNO").getResultList();
//                                Vector intTableCodeElement = (Vector) intTableCodeList.get(0);
//                                String intTableCode = intTableCodeElement.get(0).toString();

//                                double difference = 0d;

                                String[][] b1 = createFromDtArray(acno, lastFromDt, businessDt, intTableCode);
                                for (int n = 0; n < b1.length - 1; n++) {
                                    String fDate = b1[n][0].toString();
//                                    String tDate = b1[n][1].toString();

                                    BigDecimal outStandAmount = getOutStandingAmount(acno, fDate);
//                                    double standAmount = outStandAmount.doubleValue() + difference;
                                    double standAmount = outStandAmount.doubleValue();
                                    double roi = getROI(intTableCode, standAmount, fDate);

//                                    double rateOfInt = getRoiLoanAccount(standAmount, fDate, acno);

                                    /*3. No. of days between From Date and To Date*/
//                                    Long dDiff = CbsUtil.dayDiff(ymd.parse(fDate), ymd.parse(tDate));
                                    /* In each slab, No. of days is increasing by 1  */
//                                    double dayDiff = dDiff.doubleValue() + 1;

                                    /*4. Interest Calculation*/
//                                    double interest = rateOfInt * dayDiff * standAmount / 36500;
//                                    double totalInt = 0.0f;
//                                    totalInt = totalInt + interest;
//                                    double postedInt = 0d;
//                                    if (isPostingDt(fDate, forwardintPostList)) {
//                                        postedInt = getPostedInterest(acno, fDate);
//                                        difference = postedInt - totalInt;
//                                    }

                                    String convertFDt = fDate.substring(0, 4) + fDate.substring(5, 7) + fDate.substring(8);
                                    int lastFrDtCompare = yyyyMMdd.parse(lastFromDt).compareTo(yyyyMMdd.parse(convertFDt));
                                    if (lastFrDtCompare == 0) {
                                        List fDtDataList = em.createNativeQuery("SELECT ACNO FROM cbs_saving_account_product WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + convertFDt + "'").getResultList();
                                        if (fDtDataList.isEmpty()) {
                                            Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + new BigDecimal(standAmount) + ",'" + convertFDt + "','" + convertFDt + "','" + convertFDt + "'," + acnoStatus + "," + roi + ")");
                                            int insertValue = insertProductTable.executeUpdate();
                                            if (insertValue <= 0) {
                                                return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                            }
                                        } else {
                                            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + new BigDecimal(standAmount) + ",TO_DATE='" + convertFDt + "',INT_CALC_START_DATE='" + convertFDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + convertFDt + "'");
                                            int updateValue = updateProductTable.executeUpdate();
                                            if (updateValue <= 0) {
                                                return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
                                            }
                                        }
                                    } else {
                                        String valuedtMsg = updateValueDtProduct(acno, convertFDt, standAmount, acnoStatus, roi);
                                        if (!valuedtMsg.equalsIgnoreCase("true")) {
                                            return valuedtMsg;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return "true";
    }

    public BigDecimal getOutStandingAmount(String acno, String tillDt) {
        BigDecimal outStandAmount = new BigDecimal("0.00");

        List outStandList = em.createNativeQuery("SELECT IFNULL(SUM(IFNULL(R.CRAMT,0))-SUM(IFNULL(R.DRAMT,0)),0) FROM recon R WHERE R.ACNO='" + acno + "' AND DATE_FORMAT(R.VALUEDT,'%Y%m%d')<='" + tillDt + "' AND AUTH='Y'").getResultList();
        Vector outStandElement = (Vector) outStandList.get(0);
        outStandAmount = new BigDecimal(String.valueOf(outStandElement.get(0)));

        return outStandAmount;
    }

    public String checkvalueDtTransaction(String acno, String businessDt) {
        String msg = "";
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        try {
            List valueDtList = em.createNativeQuery("SELECT DATE_FORMAT(R.DT,'%Y%m%d'),DATE_FORMAT(R.VALUEDT,'%Y%m%d') FROM recon R WHERE ACNO='" + acno + "' AND DATE_FORMAT(R.DT,'%Y%m%d')='" + businessDt + "'").getResultList();
            for (int l = 0; l < valueDtList.size(); l++) {
                Vector valueDtElement = (Vector) valueDtList.get(l);
                String txnDt = valueDtElement.get(0).toString();
                String valueDt = valueDtElement.get(1).toString();

                /**
                 * *Comparison of value date and business date**
                 */
                int valueDtCompare = yyyyMMdd.parse(valueDt).compareTo(yyyyMMdd.parse(txnDt));
                if (valueDtCompare < 0) {
                    msg = "TRUE";
                    return msg;
                } else {
                    msg = "FALSE";
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return msg;
    }

    public String[] getMaxTransaction(String acno) {
        String[] data = new String[1];
        List maxTransactionList = em.createNativeQuery("SELECT DATE_FORMAT(FROM_DATE,'%Y%m%d') FROM cbs_saving_account_product WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')=(SELECT MAX(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM cbs_saving_account_product WHERE ACNO='" + acno + "')").getResultList();

        Vector maxTransactionElement = (Vector) maxTransactionList.get(0);
        String maxTranFrDt = maxTransactionElement.get(0).toString();

        data[0] = maxTranFrDt;
        return data;
    }

    public String[] getMaxTranBeforeValueDt(String acno, String valueDt) {
        String[] data = new String[2];
        List maxTranBeforeValueDtList = em.createNativeQuery("SELECT DATE_FORMAT(FROM_DATE,'%Y%m%d'),DATE_FORMAT(INT_CALC_START_DATE,'%Y%m%d') FROM cbs_saving_account_product "
                + "WHERE DATE_FORMAT(FROM_DATE,'%Y%m%d')=(SELECT MAX(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM cbs_saving_account_product WHERE DATE_FORMAT(FROM_DATE,'%Y%m%d')<'" + valueDt + "' AND ACNO='" + acno + "') AND ACNO='" + acno + "'").getResultList();

        Vector maxTranBeforeValueDtElement = (Vector) maxTranBeforeValueDtList.get(0);
        String maxTranFrDt = maxTranBeforeValueDtElement.get(0).toString();
        String maxTranIntCalcDt = maxTranBeforeValueDtElement.get(1).toString();

        data[0] = maxTranFrDt;
        data[1] = maxTranIntCalcDt;
        return data;
    }

    public String updateProduct(String acno, String intervalTranFrDt, String intervalTranIntCalcDt, String intervalFrDt) {
        Query updateProductTableForPrevTran = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + CbsUtil.dateAdd(intervalFrDt, -1) + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + intervalTranFrDt + "'");
        int updatePreValue = updateProductTableForPrevTran.executeUpdate();
        if (updatePreValue <= 0) {
            return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
        }

        BigDecimal outStandAmount = getOutStandingAmount(acno, intervalFrDt);

        Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStandAmount + ",TO_DATE='" + intervalFrDt + "',INT_CALC_START_DATE='" + intervalTranIntCalcDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + intervalFrDt + "'");
        int updateValue = updateProductTable.executeUpdate();
        if (updateValue <= 0) {
            return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
        }
        return "true";
    }

    public String updateValueDtProduct(String acno, String frDt, double outStandAmt, String acnoStatus, double roi) {

        String[] data = getMaxTranBeforeValueDt(acno, frDt);
        String maxTranFrDt = data[0];
        String maxTranIntCalcDt = data[1];

        String updateToDt = CbsUtil.dateAdd(frDt, -1);
        BigDecimal outStandAmount = BigDecimal.valueOf(outStandAmt);

        List fDtDataList = em.createNativeQuery("SELECT ACNO FROM cbs_saving_account_product WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + frDt + "'").getResultList();
        if (fDtDataList.isEmpty()) {
            Query insertProductTable = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + acno + "'," + outStandAmount + ",'" + frDt + "','" + frDt + "','" + maxTranIntCalcDt + "'," + acnoStatus + "," + roi + ")");
            int insertValue = insertProductTable.executeUpdate();
            if (insertValue <= 0) {
                return "Insertion problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
            }

            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + updateToDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTranFrDt + "'");
            int updateValue = updateProductTable.executeUpdate();
            if (updateValue <= 0) {
                return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
            }
        } else {
            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET TO_DATE='" + updateToDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTranFrDt + "'");
            int updateValue = updateProductTable.executeUpdate();
            if (updateValue <= 0) {
                return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
            }

            Query updateFrProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStandAmount + ",TO_DATE='" + frDt + "',INT_CALC_START_DATE='" + maxTranIntCalcDt + "' WHERE ACNO='" + acno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + frDt + "'");
            int updateFrValue = updateFrProductTable.executeUpdate();
            if (updateFrValue <= 0) {
                return "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + acno;
            }
        }
        return "true";
    }

    private String[][] createFromDtArray(String acNo, String fromDt, String toDt, String intTableCode) throws ApplicationException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
        try {
            ArrayList datesFrom = new ArrayList();
            List unionAllTableList = em.createNativeQuery("SELECT date_format(VALUEDT,'%Y-%m-%d') FROM recon WHERE  ACNO = '" + acNo + "' and Dt  BETWEEN '"
                    + fromDt + "' and '" + toDt + "' AND AUTH = 'Y' GROUP BY VALUEDT "
                    + " UNION ALL "
                    + " select date_format(EFF_FRM_DT,'%Y-%m-%d') from cbs_acc_int_rate_details where acno = '" + acNo + "' and EFF_FRM_DT between '"
                    + fromDt + "' and '" + toDt + "' group by EFF_FRM_DT ").getResultList();

            String a[][] = new String[unionAllTableList.size()][7];
            datesFrom.add(ymd.format(ymmd.parse(fromDt)));
            if (!unionAllTableList.isEmpty()) {
                for (int i = 0; i < unionAllTableList.size(); i++) {
                    Vector unionAllTableVect = (Vector) unionAllTableList.get(i);
                    a[i][0] = unionAllTableVect.get(0).toString();
                    if (i == 0) {
                        /*=======Getting the ROI Change Date Between FromDt and  i Position Date=======================*/
                        if (ymmd.parse(fromDt).equals(ymd.parse(unionAllTableVect.get(0).toString()))) {
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                datesFrom.add(a[i][1]);
                            }
                        } else {
                            getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), a[i][0], datesFrom);
                            datesFrom.add(a[i][0]);
                            if (i == unionAllTableList.size() - 1) {
                                a[i][1] = ymd.format(ymmd.parse(toDt));
                                /*=======Getting the ROI Change Date in Previous i Position=======================*/
                                getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                                datesFrom.add(a[i][1]);
                            }
                        }
                    } else if (i > 0 && i < unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                    } else if (i == unionAllTableList.size() - 1) {
                        a[i - 1][1] = a[i][0];
                        a[i][1] = ymd.format(ymmd.parse(toDt));
                        /*=======Getting the ROI Change Date in Previous i Position=======================*/
                        getRoiChangeSlab(intTableCode, a[i - 1][0], a[i - 1][1], datesFrom);
                        datesFrom.add(a[i][0]);
                        if (!ymmd.parse(toDt).equals(ymd.parse(a[i][0]))) {
                            /*=======Getting the ROI Change Date in Current i Position=======================*/
                            getRoiChangeSlab(intTableCode, a[i][0], a[i][1], datesFrom);
                            datesFrom.add(a[i][1]);
                        } else {
                            datesFrom.add(a[i][1]);
                        }
                    }
                }
                Collections.sort(datesFrom);
            } else {
                getRoiChangeSlab(intTableCode, ymd.format(ymmd.parse(fromDt)), ymd.format(ymmd.parse(toDt)), datesFrom);
                datesFrom.add(ymd.format(ymmd.parse(toDt)));
                Collections.sort(datesFrom);
            }
            String b[][] = new String[datesFrom.size()][7];
            if (!datesFrom.isEmpty()) {
                for (int i = 0; i < datesFrom.size(); i++) {
                    if (i == 0) {
                        b[i][0] = datesFrom.get(i).toString();
                        // firstDisbDt = datesFrom.get(i).toString();
                    } else if (i > 0 && i < datesFrom.size() - 1) {
                        b[i][0] = datesFrom.get(i).toString();
                        b[i - 1][1] = ymd.format(ymmd.parse(CbsUtil.dateAdd(ymmd.format(ymd.parse(b[i][0])), -1)));
                    } else if (i == datesFrom.size() - 1) {
                        b[i - 1][1] = datesFrom.get(i).toString();
                    }
                }
            }
            return b;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public ArrayList getRoiChangeSlab(String intTableCode, String fromDt, String toDate, ArrayList datesFrom) throws ApplicationException {
        try {
            /*=======Getting the ROI Change Date in Previous i Position=======================*/
            List laIntCodeMastList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master where "
                    + "INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            List laIntCodeMastHistList = em.createNativeQuery("select START_DATE,INTEREST_MASTER_TABLE_CODE from cbs_loan_interest_code_master_history "
                    + "where INTEREST_CODE='" + intTableCode + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();

            if (!laIntCodeMastList.isEmpty()) {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                String stDt = laIntCodeMastVect.get(0).toString();
                String intMastTblCod = laIntCodeMastVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod
                        + "' and  START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '"
                        + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }

            }
            if (!laIntCodeMastHistList.isEmpty()) {
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                String stDt = laIntCodeMastHistVect.get(0).toString();
                String intMastTblCod = laIntCodeMastHistVect.get(1).toString();
                datesFrom.add(stDt);

                List laIntMastList = em.createNativeQuery("select start_date from cbs_loan_interest_master where code = '" + intMastTblCod
                        + "' and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                List laIntMastHistList = em.createNativeQuery("select start_date from cbs_loan_interest_master_history where code = '"
                        + intMastTblCod + "'  and START_DATE BETWEEN  '" + fromDt + "' and '" + toDate + "'").getResultList();
                if (!laIntMastList.isEmpty()) {
                    Vector laIntMastVect = (Vector) laIntMastList.get(0);
                    String stDtIntMast = laIntMastVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
                if (!laIntMastHistList.isEmpty()) {
                    Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                    String stDtIntMast = laIntMastHistVect.get(2).toString();
                    datesFrom.add(stDtIntMast);
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
        return datesFrom;
    }

    public double getRoiLoanAccount(double amt, String date, String acno) throws ApplicationException {
        SimpleDateFormat ymmd = new SimpleDateFormat("yyyyMMdd");
        double roi = 0;
        double rateOfInt = 0;
        try {
            List intTableCodeEditList = em.createNativeQuery("SELECT A.INT_TABLE_CODE,A.AC_PREF_DR,A.ACC_PREF_CR, B.RATE_CODE, "
                    + "IFNULL(B.PEGG_FREQ,0) FROM cbs_acc_int_rate_details A, cbs_loan_acc_mast_sec B "
                    + " WHERE  A.ACNO = B.ACNO AND A.ACNO = '" + acno + "' AND A.EFF_FRM_DT <= '" + date + "' AND  "
                    + " A.AC_INT_VER_NO=(SELECT MAX(C.AC_INT_VER_NO) FROM cbs_acc_int_rate_details C WHERE C.ACNO='" + acno + "' "
                    + " AND C.EFF_FRM_DT <= '" + date + "')").getResultList();

            if (intTableCodeEditList.isEmpty()) {
                throw new ApplicationException("Data does not exist in CBS_ACC_INT_RATE_DETAILS for account " + acno);
            }
            Vector intTableCodeVect = (Vector) intTableCodeEditList.get(0);
            String intTableCode = intTableCodeVect.get(0).toString();
            double acPrefDr = Double.parseDouble(intTableCodeVect.get(1).toString());

            double acPrefCr = Double.parseDouble(intTableCodeVect.get(2).toString());
            String rateCode = intTableCodeVect.get(3).toString();
            int peggFreq = Integer.parseInt(intTableCodeVect.get(4).toString());

            if (rateCode.equalsIgnoreCase("Ab")) {                     //=====Absolute Fixed
                roi = rateOfInt;
            } else if (rateCode.equalsIgnoreCase("Fi")) {              //=====Fixed
                Calendar now = Calendar.getInstance();
                now.setTime(ymmd.parse(date));
                now.add(Calendar.MONTH, peggFreq);
                Date peggDt = now.getTime();

                if (peggDt.after(ymmd.parse(date)) || peggDt.equals(ymmd.parse(date))) {
                    roi = getROI(intTableCode, amt, date);
                } else {
                    roi = rateOfInt;
                }
            } else if (rateCode.equalsIgnoreCase("Fl")) {              //=====Floating
                roi = getROI(intTableCode, amt, date);
            }
            return roi + acPrefDr - acPrefCr;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public double getROI(String intTableCode, double amt, String date) throws ApplicationException {
        double roi;
        if (amt < 0) {
            amt = amt * -1;
        }
        try {
            List laIntCodeMastList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                    + " from cbs_loan_interest_code_master where INTEREST_CODE='" + intTableCode + "' and '" + date + "' "
                    + "BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();
            int intVerNo;
            String intMastTblCod;
            if (laIntCodeMastList.isEmpty()) {
                List laIntCodeMastHistList = em.createNativeQuery("select INTEREST_VERSION_NO, INTEREST_MASTER_TABLE_CODE"
                        + " from cbs_loan_interest_code_master_history where INTEREST_CODE='" + intTableCode
                        + "' and '" + date + "' BETWEEN  start_date and end_date  and Record_Status = 'A'").getResultList();
                if (laIntCodeMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in CBS_LOAN_INTEREST_CODE_MASTER for interest Table Code " + intTableCode);
                }
                Vector laIntCodeMastHistVect = (Vector) laIntCodeMastHistList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastHistVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastHistVect.get(1);

            } else {
                Vector laIntCodeMastVect = (Vector) laIntCodeMastList.get(0);
                intVerNo = Integer.parseInt(laIntCodeMastVect.get(0).toString());
                intMastTblCod = (String) laIntCodeMastVect.get(1);
            }
            double intPerDr;
            double intPerCr;
            List laIntMastList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit from "
                    + "cbs_loan_interest_master where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and end_date  "
                    + "and Record_Status = 'A'").getResultList();

            if (laIntMastList.isEmpty()) {
                List laIntMastHistList = em.createNativeQuery("select interest_percentage_debit,interest_percentage_credit "
                        + "from cbs_loan_interest_master_history where code = '" + intMastTblCod + "' and '" + date + "' BETWEEN  start_date and "
                        + "end_date  and Record_Status = 'A'").getResultList();
                if (laIntMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in CBS_LOAN_INTEREST_MASTER for interest Table Code " + intTableCode);
                }
                Vector laIntMastHistVect = (Vector) laIntMastHistList.get(0);
                intPerDr = Double.parseDouble(laIntMastHistVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastHistVect.get(1).toString());
            } else {
                Vector laIntMastVect = (Vector) laIntMastList.get(0);
                intPerDr = Double.parseDouble(laIntMastVect.get(0).toString());
                intPerCr = Double.parseDouble(laIntMastVect.get(1).toString());
            }
            String nrIntIndi;
            double nrIntPer;
            List laIntSlabMastList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                    + "cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and " + amt
                    + " between  BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();

            if (laIntSlabMastList.isEmpty()) {
                List laIntSlabMastHistList = em.createNativeQuery("SELECT NORMAL_INTEREST_INDICATOR, NORMAL_INTEREST_PERCENTAGE from "
                        + "cbs_loan_interest_slab_master_history where INTEREST_CODE = '" + intTableCode + "' and " + amt + " between  "
                        + "BEGIN_SLAB_AMOUNT and END_SLAB_AMOUNT  and Record_Status = 'A' AND INTEREST_VERSION_NO =" + intVerNo).getResultList();
                if (laIntSlabMastHistList.isEmpty()) {
                    throw new ApplicationException("Data does not exists in CBS_LOAN_INTEREST_SLAB_MASTER interest Table Code " + intTableCode);
                }
                Vector laIntSlabMastHistVect = (Vector) laIntSlabMastHistList.get(0);
                nrIntIndi = (String) laIntSlabMastHistVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastHistVect.get(1).toString());
            } else {
                Vector laIntSlabMastVect = (Vector) laIntSlabMastList.get(0);
                nrIntIndi = (String) laIntSlabMastVect.get(0);
                nrIntPer = Double.parseDouble(laIntSlabMastVect.get(1).toString());
            }

            if (nrIntIndi.equalsIgnoreCase("F")) {
//                roi = nrIntPer + intPerDr - intPerCr;
                roi = nrIntPer + intPerCr - intPerDr;
                return roi;
            } else if (nrIntIndi.equalsIgnoreCase("D")) {
                roi = 0d;
                return roi;
            } else if (nrIntIndi.equalsIgnoreCase("N")) {
                roi = nrIntPer;
                return roi;
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private boolean isPostingDt(String dtStr, List<String> list) throws ApplicationException {
        SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt = ymd.parse(dtStr);
            for (String obj : list) {
                if (dt.getTime() == ymd.parse(obj).getTime()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private double getPostedInterest(String acNo, String dt) throws ApplicationException {
        try {
            String query = "select cramt from recon where acno  = '" + acNo + "' and dt = '" + dt + "' and trandesc in (3,4) and ty = 0";
            List intList = em.createNativeQuery(query).getResultList();
            double postedInt = 0d;
            if (!intList.isEmpty()) {
                Vector intVect = (Vector) intList.get(0);
                postedInt = Double.parseDouble(intVect.elementAt(0).toString());
            } else {
                postedInt = 0;
            }
            return postedInt;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String updateDailySavingProductAtCentralDayBegin(String processFlag, String todayDt) throws ApplicationException {
        String message = "";
        try {
            List<String> allBusinessDtList = businessDtEvalutation(todayDt);
            List accountCodeList = em.createNativeQuery("SELECT DISTINCT(ACCTCODE) FROM accounttypemaster "
                    + "WHERE ACCTNATURE IN('" + CbsConstant.SAVING_AC + "') AND PRODUCTCODE='D' ORDER BY ACCTCODE").getResultList();
            List branchList = em.createNativeQuery("SELECT BRNCODE FROM branchmaster WHERE ALPHACODE IN(SELECT "
                    + "DISTINCT ALPHACODE FROM bnkadd WHERE ALPHACODE<>'HO')").getResultList();
            for (int i = 0; i < branchList.size(); i++) {
                Vector brElement = (Vector) branchList.get(i);
                int brncode = Integer.parseInt(brElement.get(0).toString());

                for (int j = 0; j < accountCodeList.size(); j++) {
                    Vector accodeElement = (Vector) accountCodeList.get(j);
                    String accode = accodeElement.get(0).toString();

                    /**
                     * *Evaluation of business date**
                     */
                    for (int k = 0; k < allBusinessDtList.size(); k++) {
                        String businessDt = allBusinessDtList.get(k);
                        String currentDtMonth = businessDt.substring(4, 6);

                        /**
                         * *Checking of interest posting flag for branch and
                         * account code wise**
                         */
                        String postFlag = "";
                        List intPostList = em.createNativeQuery("SELECT DISTINCT(POST_FLAG) FROM cbs_loan_acctype_interest_parameter "
                                + "WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND AC_TYPE='" + accode + "' AND "
                                + "DATE_FORMAT(TO_DT,'%Y%m%d')='" + businessDt + "'").getResultList();
                        if (!intPostList.isEmpty()) {
                            Vector intPostElement = (Vector) intPostList.get(0);
                            postFlag = intPostElement.get(0).toString();
                        }

                        /**
                         * *Month-end date of interest posting month
                         * calculation**
                         */
                        String dayBeginFlag = "", intPostMendDt = "", intPostMonth = "";
                        List intPostMendDtList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(TO_DT,'%Y%m%d')) FROM "
                                + "cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND "
                                + "AC_TYPE='" + accode + "' AND POST_FLAG='N'").getResultList();
                        if (!intPostMendDtList.isEmpty()) {
                            Vector intPostMendDtElement = (Vector) intPostMendDtList.get(0);
                            intPostMendDt = intPostMendDtElement.get(0).toString();
                            intPostMonth = intPostMendDt.substring(4, 6);
                        } else {
                            List intPostMendDateList = em.createNativeQuery("SELECT MAX(DATE_FORMAT(TO_DT,'%Y%m%d')) FROM "
                                    + "cbs_loan_acctype_interest_parameter WHERE CAST(BRNCODE AS unsigned)=" + brncode + " AND "
                                    + "AC_TYPE='" + accode + "' AND POST_FLAG='Y' AND TO_DT<>''").getResultList();
                            Vector intPostMendDtElement = (Vector) intPostMendDateList.get(0);
                            intPostMendDt = intPostMendDtElement.get(0).toString();
                            intPostMonth = intPostMendDt.substring(4, 6);
                        }

                        /**
                         * *Daybeginflag checking on month end date**
                         */
                        List dayFlagList = em.createNativeQuery("SELECT DAYBEGINFLAG FROM bankdays WHERE DATE='" + intPostMendDt + "' "
                                + "AND CAST(BRNCODE AS unsigned)=" + brncode + "").getResultList();
                        if (!dayFlagList.isEmpty()) {
                            Vector mElement = (Vector) dayFlagList.get(0);
                            dayBeginFlag = mElement.get(0).toString();
                        }


                        /**
                         * *Either it is not a day for interest posting or
                         * interest is not posted on that day**
                         */
                        if (intPostList.isEmpty()) {
                            List dataInProductTableList = em.createNativeQuery("SELECT DISTINCT(DATE_FORMAT(FROM_DATE,'%Y%m%d')) FROM "
                                    + "cbs_saving_account_product WHERE DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + businessDt + "' AND "
                                    + "CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND "
                                    + "SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                            if (dataInProductTableList.isEmpty()) {

                                String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                                if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                                    return message = everyDayProcessMsg;
                                }

                                if (yyyyMMdd.parse(businessDt).before(yyyyMMdd.parse(intPostMendDt)) && dayBeginFlag.equalsIgnoreCase("H") && processFlag.equalsIgnoreCase("M") && currentDtMonth.equalsIgnoreCase(intPostMonth)) {
                                    List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product "
                                            + "WHERE ACNO_STATUS<>9 AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND "
                                            + "SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                                    if (!allAcnoList.isEmpty()) {
                                        for (int p = 0; p < allAcnoList.size(); p++) {
                                            Vector allAcnoElement = (Vector) allAcnoList.get(p);
                                            String singleAcno = allAcnoElement.get(0).toString();

                                            String[] data = getMaxTransaction(singleAcno);
                                            String maxTransactionFrDt = data[0];

                                            BigDecimal outStAmount = getOutStandingAmount(singleAcno, intPostMendDt);
                                            Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET "
                                                    + "PRODUCT=" + outStAmount + ",TO_DATE='" + intPostMendDt + "' WHERE "
                                                    + "ACNO='" + singleAcno + "' AND "
                                                    + "DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                                            int updateValue = updateProductTable.executeUpdate();
                                            if (updateValue <= 0) {
                                                return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                                            }
                                        }
                                    }
                                }
                            }
                        } /**
                         * *Interest has been posted on that day**
                         */
                        else if ((!intPostList.isEmpty()) && (postFlag.equalsIgnoreCase("Y"))) {
                            /**
                             * *Calculation of initialzing date**
                             */
                            String initializingDate = CbsUtil.dateAdd(intPostMendDt, 1);
                            /**
                             * *Data insertion in product history table**
                             */
                            Query insertProduct = em.createNativeQuery("INSERT INTO cbs_saving_account_product_history SELECT C.ACNO,C.PRODUCT,C.FROM_DATE,C.TO_DATE,C.INT_CALC_START_DATE,C.ACNO_STATUS,C.ROI "
                                    + "FROM cbs_saving_account_product C,accountmaster A WHERE A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "'");
                            int insertNo = insertProduct.executeUpdate();
                            if (insertNo <= 0) {
                                return message = "Insertion problem in CBS SAVING ACCOUNT PRODUCT HISTORY for account code " + accode + " and branch " + brncode;
                            }
                            /**
                             * *Deletion of data from product table**
                             */
                            Query deleteProduct = em.createNativeQuery("DELETE C FROM cbs_saving_account_product C INNER JOIN accountmaster A ON A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCTTYPE='" + accode + "'");
                            int deleteProductNo = deleteProduct.executeUpdate();
                            if (deleteProductNo <= 0) {
                                return message = "Deletion problem from CBS SAVING ACCOUNT PRODUCT for account code " + accode + " and branch " + brncode;
                            }
                            /**
                             * *Insertion of initialization data into product
                             * table**
                             */
                            List acnoList = em.createNativeQuery("SELECT A.ACNO,A.ACCSTATUS,C.INTEREST_TABLE_CODE FROM accountmaster A,cbs_loan_acc_mast_sec C WHERE A.ACNO=C.ACNO AND CAST(A.CURBRCODE AS unsigned)=" + brncode + " AND A.ACCSTATUS<>9 AND A.ACCTTYPE='" + accode + "'").getResultList();
                            if (!acnoList.isEmpty()) {
                                for (int u = 0; u < acnoList.size(); u++) {
                                    Vector acnoElement = (Vector) acnoList.get(u);
                                    String activeAcno = acnoElement.get(0).toString();
                                    String activeStatus = acnoElement.get(1).toString();
                                    String intTableCode = acnoElement.get(2).toString();
                                    /**
                                     * *Outstanding amount**
                                     */
                                    BigDecimal amount = getOutStandingAmount(activeAcno, intPostMendDt);
                                    double roi = getROI(intTableCode, amount.doubleValue(), intPostMendDt);
                                    Query initialInsert = em.createNativeQuery("INSERT INTO cbs_saving_account_product VALUES('" + activeAcno + "'," + amount + ",'" + initializingDate + "','" + initializingDate + "','" + initializingDate + "'," + activeStatus + "," + roi + ")");
                                    int initialNo = initialInsert.executeUpdate();
                                    if (initialNo <= 0) {
                                        return message = "Insertion problem in CBS SAVING ACCOUNT PRODUCT for account code " + accode + " and branch " + brncode;
                                    }
                                }
                            }
                        } else if ((!intPostList.isEmpty()) && (postFlag.equalsIgnoreCase("N"))) {
                            String everyDayProcessMsg = everyDayProductProcess(brncode, accode, businessDt);
                            if (!everyDayProcessMsg.equalsIgnoreCase("true")) {
                                return message = everyDayProcessMsg;
                            }

                            List allAcnoList = em.createNativeQuery("SELECT DISTINCT(ACNO) FROM cbs_saving_account_product WHERE ACNO_STATUS<>9 AND DATE_FORMAT(FROM_DATE,'%Y%m%d')<>'" + businessDt + "' AND CAST(SUBSTRING(ACNO,1,2) AS unsigned)=" + brncode + " AND SUBSTRING(ACNO,3,2)='" + accode + "'").getResultList();
                            if (!allAcnoList.isEmpty()) {
                                for (int p = 0; p < allAcnoList.size(); p++) {
                                    Vector allAcnoElement = (Vector) allAcnoList.get(p);
                                    String singleAcno = allAcnoElement.get(0).toString();

                                    String[] data = getMaxTransaction(singleAcno);
                                    String maxTransactionFrDt = data[0];

                                    BigDecimal outStAmount = getOutStandingAmount(singleAcno, businessDt);
                                    Query updateProductTable = em.createNativeQuery("UPDATE cbs_saving_account_product SET PRODUCT=" + outStAmount + ",TO_DATE='" + businessDt + "' WHERE ACNO='" + singleAcno + "' AND DATE_FORMAT(FROM_DATE,'%Y%m%d')='" + maxTransactionFrDt + "'");
                                    int updateValue = updateProductTable.executeUpdate();
                                    if (updateValue <= 0) {
                                        return message = "Updation problem in CBS SAVING ACCOUNT PRODUCT for " + singleAcno;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            message = "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return message;
    }

    /*
     * It evaluates all the dates
     * todayDt in yyyyMMdd format
     */
    public List<String> businessDtEvalutation(String todayDt) throws ApplicationException {
        List<String> allBusinessDtList = new ArrayList<>();
        try {
            List maxPrevBusinessList = em.createNativeQuery("SELECT DATE_FORMAT(MAX(DATE),'%Y%m%d') FROM cbs_bankdays "
                    + "WHERE DAYBEGINFLAG='Y' AND DAYENDFLAG='Y'").getResultList();
            Vector maxPrevBusinessElement = (Vector) maxPrevBusinessList.get(0);
            String maxPrevBusinessDt = maxPrevBusinessElement.get(0).toString();

            long dayDiff = CbsUtil.dayDiff(yyyyMMdd.parse(maxPrevBusinessDt), yyyyMMdd.parse(todayDt));
            for (int i = 0; i < dayDiff; i++) {
                allBusinessDtList.add(CbsUtil.dateAdd(maxPrevBusinessDt, i));
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return allBusinessDtList;
    }
}