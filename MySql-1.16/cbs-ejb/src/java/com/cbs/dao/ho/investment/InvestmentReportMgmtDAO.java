/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.ho.investment;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.exception.ApplicationException;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class InvestmentReportMgmtDAO extends GenericDAO {

    public InvestmentReportMgmtDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<InvestmentMaster> getDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_REPORT_BETWEEN_RANGE);
            createNamedQuery.setParameter("repType", reportType);
            createNamedQuery.setParameter("frmDt", fromDt);
            createNamedQuery.setParameter("toDt", toDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getDueReportTillDate(String reportType, Date tillDate) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_REPORT_TILL_DATE);
            createNamedQuery.setParameter("repType", reportType);
            createNamedQuery.setParameter("tillDate", tillDate);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSellarName() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_SELLAR_NAME);
            createNamedQuery.setParameter("secType", "TERM DEPOSIT");
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllReportTypeAllSellarName(Date frDt, Date asOnDt, String sType, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_ALL_SELLAR_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_ALL_SELLAR);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_ALL_SELLAR_CLOSE);
            }
            createNamedQuery.setParameter("asOnDt", asOnDt);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("sTp", sType);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllReportTypeIndivisualSellarName(String sellar, Date frDt, Date asOnDt, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_INDIVISUAL_SELLAR_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_INDIVISUAL_SELLAR);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_REPORT_INDIVISUAL_SELLAR_CLOSE);
            }

            createNamedQuery.setParameter("sellar", sellar);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("asOnDt", asOnDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarName(String repType, Date asOnDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_ALL_SELLAR);
            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("asOnDt", asOnDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameGoi(String repType, Date frDt, Date asOnDt, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_ALL_SELLAR_GOI_CLOSE);
            }
            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("asOnDt", asOnDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeAllSellarNameAndMArking(String repType, String marking, Date asOnDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_ALL_SELLAR_MARKING);
            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("marking", marking);
            createNamedQuery.setParameter("asOnDt", asOnDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getIndivisualReportTypeReportTypeIndivisualSellarName(String repType, String sellar, Date frDt, Date asOnDt, String status) throws ApplicationException {
        try {

            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INDIVISUAL_REPORT_INDIVISUAL_SELLAR_CLOSE);
            }

            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("sellar", sellar);
            createNamedQuery.setParameter("asOnDt", asOnDt);
            createNamedQuery.setParameter("frDt", frDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSName(String repType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.LOAD_S_NAME);
            createNamedQuery.setParameter("secType", repType);
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getAllInvestmentMasterSecurityMaster(String repType, Date maxTillDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER);
            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getInvestmentMasterSecurityMaster(String repType, String sellerName, Date frDt, Date maxTillDt, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_MASTER_SECURITY_MASTER_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_MASTER_SECURITY_MASTER);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_MASTER_SECURITY_MASTER_C);
            }

            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("sellerName", sellerName);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getAllInvestmentMasterSecurityMasterGoiInt(String repType, Date frDt, Date maxTillDt, String status) throws ApplicationException {
        try {

            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVESTMENT_MASTER_SECURITY_MASTER_GOINT_C);
            }

            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getDatesAndDetailsForInterestRecreport() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT);
            //createNamedQuery.setParameter("status", "ACTIVE");
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportActive(Date frDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_ACTIVE);
            createNamedQuery.setParameter("asOnDt", frDt);
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

     public List<Object[]> getDueDateReportActive1(Date frDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_ACTIVE1);
            createNamedQuery.setParameter("asOnDt", frDt);
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    
    public List<Object[]> getDueDateReportRenewed() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_RENEWED);
            createNamedQuery.setParameter("status", "ACTIVE");
            createNamedQuery.setParameter("renewStatus", "R");
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportClose(Date frDt, Date toDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_CLOSE);
            createNamedQuery.setParameter("status", "CLOSED");
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportActiveSellarName(String sellerName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_ACTIVE_SELLAR);
            createNamedQuery.setParameter("sName", sellerName);
            createNamedQuery.setParameter("status", "ACTIVE");
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllDueReportBetweenRange(String reportType, Date fromDt, Date toDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_DUE_REPORT_BETWEEN_RANGE);
            createNamedQuery.setParameter("repType", reportType);
            createNamedQuery.setParameter("frmDt", fromDt);
            createNamedQuery.setParameter("toDt", toDt);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentMaster> getAllDueReportTillDate(String reportType, Date tillDate) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_DUE_REPORT_TILL_DATE);
            createNamedQuery.setParameter("repType", reportType);
            createNamedQuery.setParameter("tillDate", tillDate);
            List<InvestmentMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getInvestMastSecAll(String sellerName, Date frDt, Date maxTillDt, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("All")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVEST_MAST_SEC_ALL_STATUS_A);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVEST_MAST_SEC_ALL);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVEST_MAST_SEC_ALL_C);
            }

            createNamedQuery.setParameter("sellerName", sellerName);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getAllInvestMastSec(Date frDt, Date maxTillDt, String status) throws ApplicationException {
        try {
            Query createNamedQuery = null;
            if (status.equalsIgnoreCase("ALL")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVEST_MAST_SEC_ALL);
            } else if (status.equalsIgnoreCase("P")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVEST_MAST_SEC);
            } else if (status.equalsIgnoreCase("C")) {
                createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVEST_MAST_SEC_C);
            }
            createNamedQuery.setParameter("repType", "TERM DEPOSIT");
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getAllInvestmentMasterSecurityMasterAccrPost(String repType, Date maxTillDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_INVESTMENT_MASTER_SECURITY_FOR_ACCR_POST);
            createNamedQuery.setParameter("repType", repType);
            createNamedQuery.setParameter("maxTillDt", maxTillDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getBankName() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.LOAD_BANK_NAME);
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getDatesAndDetailsForInterestRecreportBankWise(String bnkName, String status) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_BANK_WISE);
            createNamedQuery.setParameter("sellername", bnkName);
            createNamedQuery.setParameter("status", status);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getDatesAndDetailsForInterestRecreportAll(String status) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_ALL_BANK_STATUS_WISE);
            createNamedQuery.setParameter("status", status);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getDatesAndDetailsForInterestRecreportBankAll(String bnkName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES_INTEREST_REC_REPORT_BANK_ALL_STATUS);
            createNamedQuery.setParameter("sellername", bnkName);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getDueDateReportObtained(Date frDt, Date toDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_DUE_DATE_REPORT_OBTAINED);
            createNamedQuery.setParameter("frDt", frDt);
            createNamedQuery.setParameter("toDt", toDt);
            List<Object[]> obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
}
