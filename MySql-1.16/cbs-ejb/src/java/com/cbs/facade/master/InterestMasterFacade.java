package com.cbs.facade.master;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.InterestCodeMasterTable;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
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
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

@Stateless(mappedName = "InterestMasterFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InterestMasterFacade implements InterestMasterFacadeRemote {

    @PersistenceContext
    private EntityManager em;
    @Resource
    EJBContext context;

    public List getCurrencyCodeInterestCodeMaster(Integer refRecNo) throws ApplicationException {
        try {
            return em.createNativeQuery("select REF_CODE from cbs_ref_rec_type WHERE REF_REC_NO = " + refRecNo + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getBaseIntTableCodeInterestCodeMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("select code,indicator_flag,interest_percentage_debit,interest_percentage_credit from cbs_loan_interest_master").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveInterestCodeMaster(List table, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, Float basePerCredit, Float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String createdBy, String createdDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result;
            int intVerNo = 0;
            int intVerNoSlab = 0;
            int modificationCounter = 0;
            int modificationMasterCounter = 0;
            List<InterestCodeMasterTable> slabTable = table;
            if (intTableDesc == null) {
                intTableDesc = "";
            }
            if (intRateDes == null) {
                intRateDes = "";
            }
            List checkData = em.createNativeQuery("select * from cbs_loan_interest_code_master where INTEREST_CODE = '" + intTableCode + "'").getResultList();
            if (checkData.isEmpty()) {
                modificationMasterCounter = 0;
                intVerNo = 1;
                Integer entry = em.createNativeQuery("insert into cbs_loan_interest_code_master(INTEREST_CODE,INTEREST_CODE_DESCRIPTION,INTEREST_VERSION_NO,INTEREST_VERSION_DESCRIPTION,CURRENCY_CODE,RECORD_STATUS,START_DATE,END_DATE,INDICATOR_FLAG,INTEREST_VERSION_REMARKS,INTEREST_MASTER_TABLE_CODE, RECORD_MODIFICATION_COUNT,BASE_PERCENTAGE_DEBIT,BASE_PERCENTAGE_CREDIT,CREATED_BY_USER_ID,CREATION_DATE)"
                        + "values ('" + intTableCode + "','" + intTableDesc + "'," + intVerNo + ",'" + intRateDes + "','" + currencyCode + "','" + recordStatusMs + "','" + startDate + "'," + "'" + endDate + "','" + baseindFlag + "','" + intVerRemark + "','" + baseIntTableCode + "'," + modificationMasterCounter + "," + basePerDebit + "," + basePerCredit + ",'" + createdBy + "',CURRENT_TIMESTAMP)").executeUpdate();

                if (entry <= 0) {
                    result = "Data is not inserted cbs_loan_interest_code_master";
                    ut.rollback();
                }
            } else {
                List counterList = em.createNativeQuery("select interest_version_no,record_modification_count from cbs_loan_interest_code_master "
                        + "where INTEREST_CODE = '" + intTableCode + "'").getResultList();
                if (counterList.size() <= 0) {

                    ut.rollback();
                    return "No Data in cbs_loan_interest_code_master";

                } else {
                    Vector dateVect = (Vector) counterList.get(0);
                    intVerNo = (Integer) dateVect.get(0);
                    modificationCounter = (Integer) dateVect.get(1);
                }
                Integer upadteMasterHistoryList = em.createNativeQuery("insert into cbs_loan_interest_code_master_history select * from "
                        + "cbs_loan_interest_code_master where INTEREST_CODE = '" + intTableCode + "'").executeUpdate();
                if (upadteMasterHistoryList <= 0) {
                    result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                    ut.rollback();
                    return result;
                }
                Integer upadteMasterDateHistoryList = em.createNativeQuery("update cbs_loan_interest_code_master_history set "
                        + "last_updated_date = CURRENT_TIMESTAMP, END_DATE = DATE_FORMAT(DATE_ADD('" + startDate + "', INTERVAL -1 DAY),'%Y%m%d')  "
                        + "where interest_code = '" + intTableCode + "' and INTEREST_VERSION_NO = " + intVerNo).executeUpdate();
                if (upadteMasterDateHistoryList <= 0) {
                    result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                    ut.rollback();
                    return result;
                }
                intVerNo = intVerNo + 1;
                modificationCounter = modificationCounter + 1;
                Integer updateMasterList = em.createNativeQuery("update cbs_loan_interest_code_master set INTEREST_CODE_DESCRIPTION = '" + intTableDesc + "', "
                        + " INTEREST_VERSION_NO = " + intVerNo + ", INTEREST_VERSION_DESCRIPTION ='" + intRateDes + "', CURRENCY_CODE ='" + currencyCode + "',"
                        + "RECORD_STATUS  ='" + recordStatusMs + "',START_DATE ='" + startDate + "' , END_DATE ='" + endDate + "',"
                        + "INDICATOR_FLAG ='" + baseindFlag + "', INTEREST_VERSION_REMARKS ='" + intVerRemark + "',INTEREST_MASTER_TABLE_CODE= '" + baseIntTableCode + "' , "
                        + "RECORD_MODIFICATION_COUNT= " + modificationCounter + ", BASE_PERCENTAGE_DEBIT =" + basePerDebit + ", BASE_PERCENTAGE_CREDIT =" + basePerCredit + ", "
                        + "LAST_UPDATED_BY_USER_ID ='" + createdBy + "',LAST_UPDATED_DATE =CURRENT_TIMESTAMP where INTEREST_CODE = '" + intTableCode + "'").executeUpdate();
                if (updateMasterList <= 0) {
                    result = "Data is does get not Updated in cbs_loan_interest slab master";
                    ut.rollback();
                    return (result);
                }
            }
            for (int i = 0; i < slabTable.size(); i++) {
                int loanPeriodDays = slabTable.get(i).getLoanPeriodDays();
                int loanPeriodMonths = slabTable.get(i).getLoanPeriodMonths();
                Float bgSlabAmt = Float.parseFloat(slabTable.get(i).getBgSlabAmt());
                Float endSlabAmt = Float.parseFloat(slabTable.get(i).getEndSlabAmt());
                String recordStatus = slabTable.get(i).getRecordStatus();
                String intSlabDcFlag = slabTable.get(i).getIntSlabDcFlag();
                Float normalIntPer = slabTable.get(i).getNormalIntPer();
                String normalIntInd = slabTable.get(i).getNormalIntInd();
                Float peenalIntPer = slabTable.get(i).getPeenalIntPer();
                String peenalIntInd = slabTable.get(i).getPeenalIntInd();
                Float cleanIntPer = slabTable.get(i).getCleanIntPer();
                String cleanPortionInd = slabTable.get(i).getCleanPortionInd();
                Float qisIntPer = slabTable.get(i).getQisIntPer();
                String qisPortionInd = slabTable.get(i).getQisPortionInd();
                Float additionalInt = slabTable.get(i).getAdditionalInt();
                String additionalInd = slabTable.get(i).getAdditionalInd();
                modificationCounter = 0;
                String intSlabDes = slabTable.get(i).getIntSlabDes();
                String intSlabVerRemark = slabTable.get(i).getIntSlabVerRemark();

                /**
                 * ** Add BY Shipra ***
                 */
                if (!checkData.isEmpty()) {
                    List intVerNoList = em.createNativeQuery("select INTEREST_VERSION_NO from cbs_loan_interest_code_master where INTEREST_CODE ='" + intTableCode + "'").getResultList();
                    if (!intVerNoList.isEmpty()) {
                        Vector intVerNoVect = (Vector) intVerNoList.get(0);
                        intVerNo = (Integer) intVerNoVect.get(0);

                    }
                }
                /**
                 * ***** Ended ****
                 */
                intVerNoSlab = 1;
                Integer entryList = em.createNativeQuery("insert into cbs_loan_interest_slab_master(INTEREST_CODE,INTEREST_VERSION_NO,CURRENCY_CODE,INTEREST_SLAB_DEBIT_CREDIT_FLAG,INTEREST_SLAB_VERSION_NO,INTEREST_SLAB_DESCRIPTION,LOAN_PERIOD_MONTHS,LOAN_PERIOD_DAYS,RECORD_STATUS,BEGIN_SLAB_AMOUNT,END_SLAB_AMOUNT,NORMAL_INTEREST_INDICATOR,NORMAL_INTEREST_PERCENTAGE,PEENAL_INTEREST_INDICATOR,PEENAL_INTEREST_PERCENTAGE,CLEAN_PORTION_INDICATOR,CLEAN_INTEREST_PERCENTAGE,QIS_PORTION_INDICATOR,QIS_INTEREST_PERCENTAGE,ADDITIONAL_PORTION_INDICATOR,ADDITIONAL_INTEREST_PERCENTAGES,RECORD_MODIFICATION_COUNT,INTEREST_SLAB_VERSION_REMARKS,CREATED_BY_USER_ID,CREATION_DATE)  "
                        + "values ('" + intTableCode + "'," + intVerNo + ",'" + currencyCode + "','" + intSlabDcFlag + "'," + intVerNoSlab + ",'" + intSlabDes + "'," + loanPeriodMonths + "," + loanPeriodDays + ",'" + recordStatus + "'," + bgSlabAmt + "," + endSlabAmt + ",'" + normalIntInd + "'," + normalIntPer + ",'" + peenalIntInd + "'," + peenalIntPer + ",'" + cleanPortionInd + "'," + cleanIntPer + ",'" + qisPortionInd + "'," + qisIntPer + ",'" + additionalInd + "'," + additionalInt + "," + modificationCounter + ",'" + intSlabVerRemark + "','" + createdBy + "',CURRENT_TIMESTAMP)").executeUpdate();

                if (entryList <= 0) {
                    result = "Data is not inserted cbs_loan_interest slab master";
                    ut.rollback();
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List getDataInterestCodeMaster(String intTableCode) throws ApplicationException {
        try {
            return em.createNativeQuery("select  * from cbs_loan_interest_code_master where interest_code = '" + intTableCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDataHelpInterestCodeMaster(String code) throws ApplicationException {
        try {
            return em.createNativeQuery("select ref_code,ref_desc from cbs_ref_rec_type where REF_REC_NO ='" + code + "' order by ref_code").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getDataIntSlabInterestCodeMaster(String intTableCode, int intVerNo, float loanPeriodDays, float loanPeriodMonth, float begSlbAmt, float endSlbAmt, String currencyCode) throws ApplicationException {
        try {
            return em.createNativeQuery("SELECT * FROM cbs_loan_interest_slab_master WHERE NOT EXISTS(SELECT * FROM cbs_loan_interest_slab_master WHERE INTEREST_CODE = '" + intTableCode + "' AND INTEREST_VERSION_NO = " + intVerNo + " AND LOAN_PERIOD_MONTHS =" + loanPeriodMonth + "  AND LOAN_PERIOD_DAYS = " + loanPeriodDays + "  AND BEGIN_SLAB_AMOUNT = " + begSlbAmt + "  AND END_SLAB_AMOUNT =" + endSlbAmt + " AND UPPER(CURRENCY_CODE)  = UPPER('" + currencyCode + "')) AND INTEREST_CODE = '" + intTableCode + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getSlabInterestCodeMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("select * from cbs_loan_interest_slab_master").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String AddNewSlabInterestCodeMaster(List table, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, Float basePerCredit, Float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String createdBy, String createdDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int intVerNo = 0;
            int intVerNoSlab = 0;
            int modificationCounter = 0;
            List<InterestCodeMasterTable> slabTable = table;
            if (intTableDesc == null) {
                intTableDesc = "";
            }
            if (intRateDes == null) {
                intRateDes = "";
            }
            for (int i = 0; i < slabTable.size(); i++) {
                int loanPeriodDays = slabTable.get(i).getLoanPeriodDays();
                int loanPeriodMonths = slabTable.get(i).getLoanPeriodMonths();
                Float bgSlabAmt = Float.parseFloat(slabTable.get(i).getBgSlabAmt());
                Float endSlabAmt = Float.parseFloat(slabTable.get(i).getEndSlabAmt());
                String recordStatus = slabTable.get(i).getRecordStatus();
                String intSlabDcFlag = slabTable.get(i).getIntSlabDcFlag();
                Float normalIntPer = slabTable.get(i).getNormalIntPer();
                String normalIntInd = slabTable.get(i).getNormalIntInd();
                Float peenalIntPer = slabTable.get(i).getPeenalIntPer();
                String peenalIntInd = slabTable.get(i).getPeenalIntInd();
                Float cleanIntPer = slabTable.get(i).getCleanIntPer();
                String cleanPortionInd = slabTable.get(i).getCleanPortionInd();
                Float qisIntPer = slabTable.get(i).getQisIntPer();
                String qisPortionInd = slabTable.get(i).getQisPortionInd();
                Float additionalInt = slabTable.get(i).getAdditionalInt();
                String additionalInd = slabTable.get(i).getAdditionalInd();
                modificationCounter = 0;
                String intSlabDes = slabTable.get(i).getIntSlabDes();
                String intSlabVerRemark = slabTable.get(i).getIntSlabVerRemark();

                /**
                 * ** Add BY Shipra ***
                 */
                List checkData = em.createNativeQuery("select * from cbs_loan_interest_code_master where INTEREST_CODE = '" + intTableCode + "'").getResultList();
                if (!checkData.isEmpty()) {
                    List intVerNoList = em.createNativeQuery("select INTEREST_VERSION_NO from cbs_loan_interest_code_master where INTEREST_CODE ='" + intTableCode + "'").getResultList();
                    if (!intVerNoList.isEmpty()) {
                        Vector intVerNoVect = (Vector) intVerNoList.get(0);
                        intVerNo = (Integer) intVerNoVect.get(0);

                    }
                }
                /**
                 * *****Ended ****
                 */
                intVerNoSlab = 1;
                Integer entryList = em.createNativeQuery("insert into cbs_loan_interest_slab_master(INTEREST_CODE,INTEREST_VERSION_NO,CURRENCY_CODE,INTEREST_SLAB_DEBIT_CREDIT_FLAG,INTEREST_SLAB_VERSION_NO,INTEREST_SLAB_DESCRIPTION,LOAN_PERIOD_MONTHS,LOAN_PERIOD_DAYS,RECORD_STATUS,BEGIN_SLAB_AMOUNT,END_SLAB_AMOUNT,NORMAL_INTEREST_INDICATOR,NORMAL_INTEREST_PERCENTAGE,PEENAL_INTEREST_INDICATOR,PEENAL_INTEREST_PERCENTAGE,CLEAN_PORTION_INDICATOR,CLEAN_INTEREST_PERCENTAGE,QIS_PORTION_INDICATOR,QIS_INTEREST_PERCENTAGE,ADDITIONAL_PORTION_INDICATOR,ADDITIONAL_INTEREST_PERCENTAGES,RECORD_MODIFICATION_COUNT,INTEREST_SLAB_VERSION_REMARKS,CREATED_BY_USER_ID,CREATION_DATE)  "
                        + "values ('" + intTableCode + "'," + intVerNo + ",'" + currencyCode + "','" + intSlabDcFlag + "'," + intVerNoSlab + ",'" + intSlabDes + "'," + loanPeriodMonths + "," + loanPeriodDays + ",'" + recordStatus + "'," + bgSlabAmt + "," + endSlabAmt + ",'" + normalIntInd + "'," + normalIntPer + ",'" + peenalIntInd + "'," + peenalIntPer + ",'" + cleanPortionInd + "'," + cleanIntPer + ",'" + qisPortionInd + "'," + qisIntPer + ",'" + additionalInd + "'," + additionalInt + "," + modificationCounter + ",'" + intSlabVerRemark + "','" + createdBy + "',CURRENT_TIMESTAMP)").executeUpdate();

                if (entryList <= 0) {
                    ut.rollback();
                    return "Data is not inserted cbs_loan_interest slab master";
                }
            }
            ut.commit();
            return "true";

        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String updateMasterSlabInterestCodeMaster(List table, String intTableCode, String intTableDesc, String currencyCode, String recordStatusMs, String startDate, String endDate, float basePerCredit, float basePerDebit, String intRateDes, String intVerRemark, String baseIntTableCode, String baseindFlag, String updatedBy, String updatedDate) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            Integer modificationCounter;
            int modificationCountSlab;
            int intVerNoMaster;
            int dateDiff = 0;
            String startDateTable = "";
            ut.begin();
            String result = "";
            Integer intVerNo;
            Integer modificationCount = 0;
            List<InterestCodeMasterTable> slabTable = table;
            List versionList = em.createNativeQuery("select * from cbs_loan_interest_code_master where "
                    + "INTEREST_CODE ='" + intTableCode + "' and creation_date = '" + startDate + "'").getResultList();
            if (versionList.size() <= 0) {
                List startDateList = em.createNativeQuery("select date_format(start_Date,'%Y%m%d') from cbs_loan_interest_code_master where "
                        + "INTEREST_CODE ='" + intTableCode + "'").getResultList();
                if (startDateList.size() > 0) {
                    Vector startDateVect = (Vector) startDateList.get(0);
                    startDateTable = startDateVect.get(0).toString();
                }
                List dateDiffList = em.createNativeQuery("select TIMESTAMPDIFF(DAY,'" + startDateTable + "','" + startDate + "')").getResultList();
                if (dateDiffList.size() > 0) {
                    Vector dateDiffVect = (Vector) dateDiffList.get(0);
                    dateDiff = Integer.parseInt(dateDiffVect.get(0).toString());
                }
                if (dateDiff != 0) {
                    List counterList = em.createNativeQuery("select interest_version_no,record_modification_count from cbs_loan_interest_code_master "
                            + "where INTEREST_CODE = '" + intTableCode + "'").getResultList();
                    if (counterList.size() <= 0) {
                        ut.rollback();
                        return "No Data in cbs_loan_interest_code_master";
                    } else {
                        Vector dateVect = (Vector) counterList.get(0);
                        intVerNo = (Integer) dateVect.get(0);
                        modificationCount = (Integer) dateVect.get(1);
                    }
                    Integer upadteMasterHistoryList = em.createNativeQuery("insert into cbs_loan_interest_code_master_history select * from "
                            + "cbs_loan_interest_code_master where INTEREST_CODE = '" + intTableCode + "'").executeUpdate();
                    if (upadteMasterHistoryList <= 0) {
                        result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                        ut.rollback();
                        return result;
                    }
                    Integer upadteMasterDateHistoryList = em.createNativeQuery("update cbs_loan_interest_code_master_history set "
                            + "last_updated_date = CURRENT_TIMESTAMP, END_DATE = DATE_FORMAT(DATE_ADD('" + startDate + "', INTERVAL -1 DAY),'%Y%m%d')  "
                            + "where interest_code = '" + intTableCode + "' and INTEREST_VERSION_NO = " + intVerNo).executeUpdate();
                    if (upadteMasterDateHistoryList <= 0) {
                        result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                        ut.rollback();
                        return result;
                    }
                    intVerNo = intVerNo + 1;
                    modificationCount = modificationCount + 1;
                    Integer updateMasterList = em.createNativeQuery("update cbs_loan_interest_code_master set INTEREST_CODE_DESCRIPTION = '" + intTableDesc + "', "
                            + " INTEREST_VERSION_NO = " + intVerNo + ", INTEREST_VERSION_DESCRIPTION ='" + intRateDes + "', CURRENCY_CODE ='" + currencyCode + "',"
                            + "RECORD_STATUS  ='" + recordStatusMs + "',START_DATE ='" + startDate + "' , END_DATE ='" + endDate + "',"
                            + "INDICATOR_FLAG ='" + baseindFlag + "', INTEREST_VERSION_REMARKS ='" + intVerRemark + "',INTEREST_MASTER_TABLE_CODE= '" + baseIntTableCode + "' , "
                            + "RECORD_MODIFICATION_COUNT= " + modificationCount + ", BASE_PERCENTAGE_DEBIT =" + basePerDebit + ", BASE_PERCENTAGE_CREDIT =" + basePerCredit + ", "
                            + "LAST_UPDATED_BY_USER_ID ='" + updatedBy + "',LAST_UPDATED_DATE = CURRENT_TIMESTAMP where INTEREST_CODE = '" + intTableCode + "'").executeUpdate();
                    if (updateMasterList <= 0) {
                        result = "Data is does get not Updated in cbs_loan_interest slab master";
                        ut.rollback();
                        return (result);
                    }
                }
            }
            for (int i = 0; i < slabTable.size(); i++) {
                BigDecimal loanPeriodDays = new BigDecimal(slabTable.get(i).getLoanPeriodDays());
                BigDecimal loanPeriodMonths = new BigDecimal(slabTable.get(i).getLoanPeriodMonths());
                BigDecimal bgSlabAmt = new BigDecimal(slabTable.get(i).getBgSlabAmt());
                BigDecimal endSlabAmt = new BigDecimal(slabTable.get(i).getEndSlabAmt());
                String recordStatus = slabTable.get(i).getRecordStatus();
                String intSlabDcFlag = slabTable.get(i).getIntSlabDcFlag();
                BigDecimal normalIntPer = new BigDecimal(slabTable.get(i).getNormalIntPer());
                String normalIntInd = slabTable.get(i).getNormalIntInd();
                BigDecimal peenalIntPer = new BigDecimal(slabTable.get(i).getPeenalIntPer());
                String peenalIntInd = slabTable.get(i).getPeenalIntInd();
                BigDecimal cleanIntPer = new BigDecimal(slabTable.get(i).getCleanIntPer());
                String cleanPortionInd = slabTable.get(i).getCleanPortionInd();
                BigDecimal qisIntPer = new BigDecimal(slabTable.get(i).getQisIntPer());
                String qisPortionInd = slabTable.get(i).getQisPortionInd();
                BigDecimal additionalInt = new BigDecimal(slabTable.get(i).getAdditionalInt());
                String additionalInd = slabTable.get(i).getAdditionalInd();
                modificationCounter = slabTable.get(i).getModificationCount();
                String intSlabDes = slabTable.get(i).getIntSlabDes();
                String intSlabVerRemark = slabTable.get(i).getIntSlabVerRemark();
                Integer intSlabVerNo = slabTable.get(i).getIntSlabVerNo();
                //Integer intSlabVerNoTocheck = intSlabVerNo;
                Integer modificationCountSlab1 = null;
                System.out.println("select INTEREST_VERSION_NO,RECORD_MODIFICATION_COUNT from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + " and interest_code = '" + intTableCode + "' and currency_code = '" + currencyCode + "'  ");
                List countSlabList = em.createNativeQuery("select INTEREST_VERSION_NO,RECORD_MODIFICATION_COUNT from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + " and interest_code = '" + intTableCode + "' and currency_code = '" + currencyCode + "'  ").getResultList();
                if (countSlabList.size() < 0) {
                    ut.rollback();
                    return "No Data in cbs_loan_interest_code_master";
                } else {
                    Vector dateVect = (Vector) countSlabList.get(0);
                    intVerNoMaster = (Integer) dateVect.get(0);
                    modificationCountSlab = (Integer) dateVect.get(1);
                }
                if (modificationCountSlab == modificationCounter) {
                    List versionList1 = em.createNativeQuery("select interest_version_no from cbs_loan_interest_code_master where "
                            + "INTEREST_CODE ='" + intTableCode + "'").getResultList();
                    if (versionList1.size() > 0) {
                        Vector versionList1Vect = (Vector) versionList1.get(0);
                        intVerNoMaster = (Integer) versionList1Vect.get(0);
                    }
                } else {
                    List versionList1 = em.createNativeQuery("select interest_version_no from cbs_loan_interest_code_master where "
                            + "INTEREST_CODE ='" + intTableCode + "'").getResultList();
                    if (versionList1.size() > 0) {
                        Vector versionList1Vect = (Vector) versionList1.get(0);
                        intVerNoMaster = (Integer) versionList1Vect.get(0);
                    }
                    if (dateDiff == 0) {
                        intSlabVerNo = intSlabVerNo + 1;
                    }
                }
                List versionHisList = em.createNativeQuery("select RECORD_MODIFICATION_COUNT "
                        + "from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and"
                        + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and "
                        + "BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + "  and currency_code = '" + currencyCode + "'").getResultList();
                if (versionHisList.size() > 0) {
                    Vector versionHisVect = (Vector) versionHisList.get(0);
                    modificationCountSlab1 = (Integer) versionHisVect.get(0);
                }
                Integer upadteSlabHistoryList = 0;
                Integer upadteSlabHistoryDateList = 0;
                if (dateDiff != 0) {
                    upadteSlabHistoryList = em.createNativeQuery("insert into cbs_loan_interest_slab_master_history select * "
                            + "from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and"
                            + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and "
                            + "BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + "  and currency_code = '" + currencyCode + "'").executeUpdate();
                    if (upadteSlabHistoryList <= 0) {
                        result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                        ut.rollback();
                        return (result);
                    }
                    upadteSlabHistoryDateList = em.createNativeQuery("update cbs_loan_interest_slab_master_history "
                            + "set last_updated_date = CURRENT_TIMESTAMP  where INTEREST_CODE = '" + intTableCode + "' and"
                            + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and"
                            + " BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + " and interest_code = '" + intTableCode + "' and currency_code = '" + currencyCode + "'  ").executeUpdate();
                    if (upadteSlabHistoryDateList <= 0) {
                        result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                        ut.rollback();
                        return (result);
                    }
                }
                if (modificationCountSlab1 < modificationCounter) {
                    if (upadteSlabHistoryList == 0 && upadteSlabHistoryDateList == 0) {

                        upadteSlabHistoryList = em.createNativeQuery("insert into cbs_loan_interest_slab_master_history select * "
                                + "from cbs_loan_interest_slab_master where INTEREST_CODE = '" + intTableCode + "' and"
                                + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and "
                                + "BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + "  and currency_code = '" + currencyCode + "'").executeUpdate();
                        if (upadteSlabHistoryList <= 0) {
                            result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                            ut.rollback();
                            return (result);
                        }
                        upadteSlabHistoryDateList = em.createNativeQuery("update cbs_loan_interest_slab_master_history "
                                + "set last_updated_date = CURRENT_TIMESTAMP  where INTEREST_CODE = '" + intTableCode + "' and"
                                + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " and"
                                + " BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + " and interest_code = '" + intTableCode + "' and currency_code = '" + currencyCode + "'  ").executeUpdate();
                        if (upadteSlabHistoryDateList <= 0) {
                            result = "Data is does get not Updated in cbs_loan_interest slab master_history";
                            ut.rollback();
                            return (result);
                        }
                    }
                }
                Integer updateSlabList = em.createNativeQuery("update  cbs_loan_interest_slab_master set"
                        + " INTEREST_VERSION_NO = " + intVerNoMaster + ",CURRENCY_CODE = '" + currencyCode + "',"
                        + "INTEREST_SLAB_DEBIT_CREDIT_FLAG = '" + intSlabDcFlag + "',INTEREST_SLAB_VERSION_NO= " + intSlabVerNo + ","
                        + "INTEREST_SLAB_DESCRIPTION = '" + intSlabDes + "',LOAN_PERIOD_MONTHS = " + loanPeriodMonths + ""
                        + ",LOAN_PERIOD_DAYS = " + loanPeriodDays + ",RECORD_STATUS = '" + recordStatus + "',"
                        + "BEGIN_SLAB_AMOUNT = " + bgSlabAmt + ",END_SLAB_AMOUNT =" + endSlabAmt + ",NORMAL_INTEREST_INDICATOR = '" + normalIntInd + "',"
                        + "NORMAL_INTEREST_PERCENTAGE = " + normalIntPer + ",PEENAL_INTEREST_INDICATOR = '" + peenalIntInd + "',"
                        + "PEENAL_INTEREST_PERCENTAGE =" + peenalIntPer + ",CLEAN_PORTION_INDICATOR = '" + cleanPortionInd + "',"
                        + "CLEAN_INTEREST_PERCENTAGE = " + cleanIntPer + ",QIS_PORTION_INDICATOR = '" + qisPortionInd + "',"
                        + "QIS_INTEREST_PERCENTAGE =" + qisIntPer + ",ADDITIONAL_PORTION_INDICATOR ='" + additionalInd + "',"
                        + "ADDITIONAL_INTEREST_PERCENTAGES = " + additionalInt + ",RECORD_MODIFICATION_COUNT = " + modificationCounter + ","
                        + "INTEREST_SLAB_VERSION_REMARKS ='" + intSlabVerRemark + "',"
                        + "LAST_UPDATED_BY_USER_ID ='" + updatedBy + "',LAST_UPDATED_DATE = CURRENT_TIMESTAMP"
                        + " where INTEREST_CODE = '" + intTableCode + "' and"
                        + " LOAN_PERIOD_MONTHS = " + loanPeriodMonths + " and LOAN_PERIOD_DAYS =" + loanPeriodDays + " "
                        + "and BEGIN_SLAB_AMOUNT = " + bgSlabAmt + " and  END_SLAB_AMOUNT =" + endSlabAmt + ""
                        + " and interest_code = '" + intTableCode + "' and currency_code = '" + currencyCode + "' ").executeUpdate();
                if (updateSlabList <= 0) {
                    result = "Data is not inserted cbs_loan_interest slab master";
                    ut.rollback();
                    return (result);
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String checkRefCodeInterestCodeMaster(String code, String intTableCode) throws ApplicationException {
        List resultlist = null;
        String check = "";
        try {
            resultlist = em.createNativeQuery("select * from cbs_ref_rec_type where REF_REC_NO ='" + code + "' and ref_code = '" + intTableCode + "'").getResultList();
            if (resultlist.size() <= 0) {
                check = "false";
            } else {
                check = "true";
            }
            return check;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String daybeginDateInterestMaster(String orgnBrCode) throws ApplicationException {
        try {
            String tempBd;
            List dateList = em.createNativeQuery("select date from bankdays where dayendflag = 'N' and brncode = '" + orgnBrCode + "'").getResultList();
            if (dateList.size() <= 0) {
                return "Check the Day Begin/Day End";
            } else {
                Vector dateVect = (Vector) dateList.get(0);
                tempBd = (String) dateVect.get(0);
            }
            return tempBd;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveUpdateInterestMaster(String command, String code, String codeDescription, String indicatorFlag, String recordStatus, String startDate, String endDate, String versionNoDesc, String userName, String currentDate, String usrName, Float interestPercentage, Float interestPercentageCredit, String todayDt, String lastUpdateDate, Integer verNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            String codeData = "";
            String indFlag = "";
            String verno = "";
            Integer versionNo = 0;
            Integer recordModificationCount = 0;
            if (command.equalsIgnoreCase("save")) {
                List checkcode = em.createNativeQuery("select code,indicator_flag from cbs_loan_interest_master where code = '" + code + "'").getResultList();//and INDICATOR_FLAG ='" + indicatorFlag + "'
                if (checkcode.size() > 0) {
                    ut.rollback();
                    return "Same code already exist ";
                } else {

                    List codeCheck = em.createNativeQuery("select version_no from cbs_loan_interest_master where code='" + code + "'").getResultList();
                    if (codeCheck.size() <= 0) {
                        versionNo = 1;
                    } else {
                        versionNo += 1;
                    }
                    Query CBS_LOAN_INTEREST_MASTER = em.createNativeQuery("insert into cbs_loan_interest_master(CODE,CODE_DESCRIPTION,INDICATOR_FLAG,"
                            + "VERSION_NO,RECORD_STATUS,START_DATE,END_DATE,VERSION_NO_DESCRIPTION, CREATED_BY_USER_ID,CREATION_DATE,"
                            + "INTEREST_PERCENTAGE_DEBIT,INTEREST_PERCENTAGE_CREDIT,RECORD_MODIFICATION_COUNT)"
                            + " values('" + code + "','" + codeDescription + "','" + indicatorFlag + "'," + versionNo + ",'" + recordStatus + "','"
                            + startDate + "','" + endDate + "','" + versionNoDesc + "','" + userName + "',CURRENT_TIMESTAMP," + interestPercentage + ","
                            + interestPercentageCredit + "," + recordModificationCount + ")");
                    Integer CBS_LOAN_INTEREST_MASTERVarient = CBS_LOAN_INTEREST_MASTER.executeUpdate();
                    if (CBS_LOAN_INTEREST_MASTERVarient > 0) {
                        ut.commit();
                        result = "Record Save Successfully";
                    } else {
                        ut.rollback();
                        result = "Data could not be Inserted.";
                    }
                }
            } else if (command.equalsIgnoreCase("update")) {
                List checkcodehistory = em.createNativeQuery("select code,indicator_flag,version_no from cbs_loan_interest_master_history").getResultList();
                if (checkcodehistory.size() > 0) {
                    for (int i = 0; i < checkcodehistory.size(); i++) {
                        Vector ele = (Vector) checkcodehistory.get(i);
                        for (Object ee : ele) {
                            codeData = (String) ele.get(0);
                            indFlag = (String) ele.get(1);
                            verno = ele.get(2).toString();
                        }
                    }
                    if (codeData.equals(code) && indFlag.equalsIgnoreCase(indicatorFlag) && verno.equals(verNo.toString())) {
                        ut.rollback();
                        return "Duplicate entry is not allowed !";
                    }
                }
                List codeCheck = em.createNativeQuery("select VERSION_NO,RECORD_MODIFICATION_COUNT from cbs_loan_interest_master where "
                        + "code='" + code + "'").getResultList();
                Vector elem = (Vector) codeCheck.get(0);
                versionNo = Integer.parseInt(elem.get(0).toString());
                recordModificationCount = Integer.parseInt(elem.get(1).toString());
                if (codeCheck.size() <= 0) {
                    versionNo = 1;
                    recordModificationCount = 0;
                } else {
                    versionNo += 1;
                    recordModificationCount = recordModificationCount + 1;
                }
//                List checkcode = em.createNativeQuery("select interest_master_table_code,record_status from cbs_loan_interest_code_master "
//                        + " where interest_master_table_code = '" + code + "' and record_status = 'a'").getResultList();
//                if (checkcode.size() > 0) {
//                    ut.rollback();
//                    return "This code can not be update,it is currently running ";
//                } else {
                    Query insertintMasterHist = em.createNativeQuery("insert into cbs_loan_interest_master_history select * from "
                            + "cbs_loan_interest_master where CODE='" + code + "' and INDICATOR_FLAG='" + indicatorFlag + "'");
                    Integer varinsertintMasterHist = insertintMasterHist.executeUpdate();
                    if (varinsertintMasterHist <= 0) {
                        ut.rollback();
                        return "Instrument is not inserted !";
                    }
                    Integer upadteMasterDateHistoryList = em.createNativeQuery("update cbs_loan_interest_master_history set END_DATE = "
                            + "DATE_FORMAT(DATE_ADD('" + startDate + "', INTERVAL -1 DAY),'%Y%m%d')  where CODE='" + code + "' and "
                            + "INDICATOR_FLAG='" + indicatorFlag + "' and VERSION_NO='" + verNo + "'").executeUpdate();
                    if (upadteMasterDateHistoryList <= 0) {
                        result = "Data is not updated.";
                        ut.rollback();
                        return result;
                    }
                    Query updateBillLost = em.createNativeQuery("Update cbs_loan_interest_master set CODE_DESCRIPTION='" + codeDescription
                            + "',VERSION_NO=" + versionNo + ",RECORD_STATUS='" + recordStatus + "',START_DATE = '" + startDate + "',END_DATE='"
                            + endDate + "',VERSION_NO_DESCRIPTION='" + versionNoDesc + "',LAST_UPDATED_BY_USER_ID='" + userName
                            + "',LAST_UPDATED_DATE = CURRENT_TIMESTAMP,INTEREST_PERCENTAGE_DEBIT=" + interestPercentage + ",INTEREST_PERCENTAGE_CREDIT="
                            + interestPercentageCredit + ",RECORD_MODIFICATION_COUNT=" + recordModificationCount + " where CODE='" + code
                            + "' and INDICATOR_FLAG='" + indicatorFlag + "'");
                    Integer updateBillLostVarient = updateBillLost.executeUpdate();
                    if (updateBillLostVarient > 0) {
                        ut.commit();
                        result = "Data updated Successfully.";
                    } else {
                        ut.rollback();
                        result = "Data could not be updated.";
                    }
//                }
            }
            return result;
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List tableDataInterestMaster() throws ApplicationException {
        try {
            return em.createNativeQuery("Select CODE,CODE_DESCRIPTION,INDICATOR_FLAG,RECORD_STATUS,date_format(START_DATE,'%d/%m/%Y') as START_DATE,date_format(END_DATE,'%d/%m/%Y') as END_DATE,VERSION_NO_DESCRIPTION,INTEREST_PERCENTAGE_DEBIT,INTEREST_PERCENTAGE_CREDIT,RECORD_MODIFICATION_COUNT,VERSION_NO,CREATED_BY_USER_ID,CREATION_DATE,LAST_UPDATED_BY_USER_ID,LAST_UPDATED_DATE,RECORD_MODIFICATION_COUNT From cbs_loan_interest_master where RECORD_STATUS='A'  order by CODE").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deletionInterestMaster(String code, String codeDescription, String indicatorFlag, String recordStatus, String startDate, String endDate, String versionNoDesc, String userName, String currentDate, String usrName, Float interestPercentage, Float interestPercentageCredit, String todayDt, String lastUpdateDate, Integer verNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String result = "";
            String codeData = "";
            String indFlag = "";
            String verno = "";
            Integer versionNo = 0;
            Integer recordModificationCount = 0;
            List checkcodehistory = em.createNativeQuery("select code,indicator_flag,version_no from  cbs_loan_interest_master_history").getResultList();
            if (checkcodehistory.size() > 0) {
                for (int i = 0; i < checkcodehistory.size(); i++) {
                    Vector ele = (Vector) checkcodehistory.get(i);
                    for (Object ee : ele) {
                        codeData = (String) ele.get(0);
                        indFlag = (String) ele.get(1);
                        verno = ele.get(2).toString();
                    }
                }
                if (codeData.equals(code) && indFlag.equalsIgnoreCase(indicatorFlag) && verno.equals(verNo.toString())) {
                    ut.rollback();
                    return "Duplicate entry not allowed .";
                }
            }
            List codeCheck = em.createNativeQuery("select VERSION_NO,RECORD_MODIFICATION_COUNT from cbs_loan_interest_master where code='" + code + "'").getResultList();
            Vector elem = (Vector) codeCheck.get(0);
            versionNo = Integer.parseInt(elem.get(0).toString());
            recordModificationCount = Integer.parseInt(elem.get(1).toString());
            if (codeCheck.size() <= 0) {
                versionNo = 1;
                recordModificationCount = 0;
            } else {
                versionNo += 1;
                recordModificationCount = recordModificationCount + 1;
            }
            List checkcode = em.createNativeQuery("select interest_master_table_code,record_status from  cbs_loan_interest_code_master  "
                    + "where interest_master_table_code = '" + code + "' and record_status = 'a'").getResultList();
            if (checkcode.size() > 0) {
                ut.rollback();
                return "Please Check The Record In CBS_LOAN_INTEREST_CODE_MASTER Table,This Code Is Currently Running ";
            } else {
                Query insertintMasterHist = em.createNativeQuery("insert into cbs_loan_interest_master_history select * from "
                        + "cbs_loan_interest_master where CODE='" + code + "' and INDICATOR_FLAG='" + indicatorFlag + "'");

                Integer varinsertintMasterHist = insertintMasterHist.executeUpdate();
                if (varinsertintMasterHist <= 0) {
                    ut.rollback();
                    return "Instrument is not inserted.";
                }
                Integer upadteMasterDateHistoryList = em.createNativeQuery("update cbs_loan_interest_master_history set "
                        + "LAST_UPDATED_BY_USER_ID='" + userName + "', END_DATE = DATE_FORMAT(DATE_ADD('" + startDate + "', INTERVAL -1 DAY),'%Y%m%d')  "
                        + "where CODE='" + code + "' and INDICATOR_FLAG='" + indicatorFlag + "' and VERSION_NO='" + verNo + "'").executeUpdate();
                if (upadteMasterDateHistoryList <= 0) {
                    result = "Data did'nt update !";
                    ut.rollback();
                    return result;
                }
                Query deleteintmaster = em.createNativeQuery("Update cbs_loan_interest_master set CODE_DESCRIPTION='" + codeDescription
                        + "',VERSION_NO=" + versionNo + ",RECORD_STATUS='D',START_DATE = '" + startDate + "',END_DATE='" + endDate
                        + "',VERSION_NO_DESCRIPTION='" + versionNoDesc + "',LAST_UPDATED_BY_USER_ID='" + userName
                        + "',LAST_UPDATED_DATE = CURRENT_TIMESTAMP,INTEREST_PERCENTAGE_DEBIT=" + interestPercentage + ",INTEREST_PERCENTAGE_CREDIT="
                        + interestPercentageCredit + ",RECORD_MODIFICATION_COUNT=" + recordModificationCount + " where CODE='" + code
                        + "' and INDICATOR_FLAG='" + indicatorFlag + "'");
                Integer varDeleteintmaster = deleteintmaster.executeUpdate();
                if (varDeleteintmaster <= 0) {
                    ut.rollback();
                    return "Instrument is not deleted from Bill Lost";
                }
                ut.commit();
                return "Instrument Deleted Successfully";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public List acctCodeInterestParameter() throws ApplicationException {
        try {
            return em.createNativeQuery("Select ifnull(acctcode,'N.A') as acctcode from accounttypemaster where acctnature <> '" + CbsConstant.PAY_ORDER + "' order by acctcode").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String saveFrHalfInterestParameter(String acType, boolean check1, boolean check2) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String checkValue1;
            String checkValue2;
            if (check1 == true) {
                checkValue1 = "Y";
            } else {
                checkValue1 = "N";
            }
            if (check2 == true) {
                checkValue2 = "Y";
            } else {
                checkValue2 = "N";
            }
            Query q = em.createNativeQuery("delete from parameterinfo_int where actype='" + acType + "'");
            q.executeUpdate();
            Query q2 = em.createNativeQuery("insert into parameterinfo_int (actype,fmonth,tmonth,mflag,dt) values('" + acType + "',4,9,'" + checkValue1 + "',CURRENT_TIMESTAMP)");
            q2.executeUpdate();
            Query q3 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',10,3,'" + checkValue2 + "',CURRENT_TIMESTAMP)");
            Integer int1 = q3.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveFrQuartInterestParameter(String acType, boolean check1, boolean check2, boolean check3, boolean check4) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String checkValue1;
            String checkValue2;
            String checkValue3;
            String checkValue4;
            if (check1 == true) {
                checkValue1 = "Y";
            } else {
                checkValue1 = "N";
            }
            if (check2 == true) {
                checkValue2 = "Y";
            } else {
                checkValue2 = "N";
            }
            if (check3 == true) {
                checkValue3 = "Y";
            } else {
                checkValue3 = "N";
            }

            if (check4 == true) {
                checkValue4 = "Y";
            } else {
                checkValue4 = "N";
            }
            Query q = em.createNativeQuery("delete from parameterinfo_int where actype='" + acType + "'");
            q.executeUpdate();
            Query q1 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',4,6,'" + checkValue1 + "',CURRENT_TIMESTAMP)");
            q1.executeUpdate();
            Query q2 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',7,9,'" + checkValue2 + "',CURRENT_TIMESTAMP)");
            q2.executeUpdate();
            Query q3 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',10,12,'" + checkValue3 + "',CURRENT_TIMESTAMP)");
            q3.executeUpdate();
            Query q4 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',1,3,'" + checkValue4 + "',CURRENT_TIMESTAMP)");
            Integer int1 = q4.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    public String saveFrMonthlyInterestParameter(String acType, boolean check1, boolean check2, boolean check3, boolean check4, boolean check5, boolean check6) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String checkValue1;
            String checkValue2;
            String checkValue3;
            String checkValue4;
            String checkValue5;
            String checkValue6;
            if (check1 == true) {
                checkValue1 = "Y";
            } else {
                checkValue1 = "N";
            }
            if (check2 == true) {
                checkValue2 = "Y";
            } else {
                checkValue2 = "N";
            }
            if (check3 == true) {
                checkValue3 = "Y";

            } else {
                checkValue3 = "N";
            }
            if (check4 == true) {
                checkValue4 = "Y";
            } else {
                checkValue4 = "N";
            }
            if (check5 == true) {
                checkValue5 = "Y";
            } else {
                checkValue5 = "N";
            }
            if (check6 == true) {
                checkValue6 = "Y";
            } else {
                checkValue6 = "N";
            }
            Query q = em.createNativeQuery("delete from parameterinfo_int where actype='" + acType + "'");
            q.executeUpdate();
            Query q1 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',4,5,'" + checkValue1 + "',CURRENT_TIMESTAMP)");
            q1.executeUpdate();
            Query q2 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',6,7,'" + checkValue2 + "',CURRENT_TIMESTAMP)");
            q2.executeUpdate();
            Query q3 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',8,9,'" + checkValue3 + "',CURRENT_TIMESTAMP)");
            q3.executeUpdate();
            Query q4 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',10,11,'" + checkValue4 + "',CURRENT_TIMESTAMP)");
            q4.executeUpdate();
            Query q5 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',12,1,'" + checkValue3 + "',CURRENT_TIMESTAMP)");
            q5.executeUpdate();
            Query q6 = em.createNativeQuery("insert into parameterinfo_int(actype,fmonth,tmonth,mflag,dt) values ('" + acType + "',2,3,'" + checkValue4 + "',CURRENT_TIMESTAMP)");
            Integer int1 = q6.executeUpdate();
            if (int1 > 0) {
                ut.commit();
                return "transaction successful";
            } else {
                ut.rollback();
                return "Transaction is not Successful";
            }
        } catch (Exception e) {
            try {
                ut.rollback();
                throw new ApplicationException(e);
            } catch (IllegalStateException ex) {
                throw new ApplicationException(ex);
            } catch (SecurityException ex) {
                throw new ApplicationException(ex);
            } catch (SystemException ex) {
                throw new ApplicationException(ex);
            }
        }
    }
    //Added by Manish kumar
    public boolean isIntrestTableCodeExit(String intrestTablecCode)throws ApplicationException{
        boolean flag = false;
        try{
            String query = "select INTEREST_CODE from cbs_loan_interest_slab_master where INTEREST_CODE = '"+intrestTablecCode+"'";
            List list = em.createNativeQuery(query).getResultList();
            if(!list.isEmpty()){
                flag =true;
            }
        }catch(Exception ex){
            throw new ApplicationException(ex.getMessage());
        }
        return flag;
    }
}
