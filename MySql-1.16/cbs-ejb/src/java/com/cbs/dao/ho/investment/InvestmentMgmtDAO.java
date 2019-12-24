/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.ho.investment;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.ho.investment.FdrRecon;
import com.cbs.entity.ho.investment.GoiRecon;
import com.cbs.entity.ho.investment.IntPostHistory;
import com.cbs.entity.ho.investment.InvestmentAmortPostHistory;
import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.ho.investment.InvestmentFdrIntHis;
import com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail;
import com.cbs.entity.ho.investment.InvestmentSecurityMaster;
import com.cbs.entity.ho.investment.InvestmentShare;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.ho.investment.ShareRecon;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.transaction.GlRecon;
import com.cbs.exception.ApplicationException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author root
 */
public class InvestmentMgmtDAO extends GenericDAO {

    @Resource
    private UserTransaction utx;

    public InvestmentMgmtDAO(EntityManager entityManager) {
        super(entityManager);
    }

    public List<InvestmentSecurityMaster> getInvestmentSecurityToModify(String maturityYear) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_SECURITY_TO_MODIFY);
            createNamedQuery.setParameter("maturityYear", maturityYear);
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getInvestmentSecurityToDelete(String maturityYear) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_SECURITY_TO_DELETE);
            createNamedQuery.setParameter("maturityYear", maturityYear);
            createNamedQuery.setParameter("delFlag", "Y");
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> checkDuplicate(String maturityYear, double roi, String securityName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CHECK_DUPLICATE_SECURITY_MASTER);
            createNamedQuery.setParameter("maturityYear", maturityYear);
            createNamedQuery.setParameter("roi", roi);
            createNamedQuery.setParameter("securityName", securityName);
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String deleteInvestmentSecurityMaster(Long sno, String lastUpdateBy, Date curDt) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.DELETE_INVESTMENT_SECURITY_MASTER);
            createNamedQuery.setParameter("delFlag", "Y");
            createNamedQuery.setParameter("sno", sno);
            createNamedQuery.setParameter("trantime", curDt);
            createNamedQuery.setParameter("lastUpdate", lastUpdateBy);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String modifyInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<BranchMaster> getAlphaCode() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findAll");
            List<BranchMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<GlDescRange> getGlRange(String glName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("GlDescRange.findBySecType");
            createNamedQuery.setParameter("secType", glName);
            List<GlDescRange> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public List<InvestmentFdrDetails> getCtrlNoList(String status) throws ApplicationException {
        try{
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CONTROLLIST_AS_PER_STATUS);
            createNamedQuery.setParameter("stat",status);
            List<InvestmentFdrDetails> entityList = createNamedQuery.getResultList();
            return entityList;
            }catch (Exception e){
              throw new ApplicationException(e);
           }
       }
    
    public List<String> getSecurityType(String fromNo, String toNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_GOVERNMENT_SECURITY_LIST);
            createNamedQuery.setParameter("fromNo", fromNo);
            createNamedQuery.setParameter("toNo", toNo);
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentGoidates> getControllNo(String secType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_FOR_MASTER_CLOSE);
            createNamedQuery.setParameter("status", "active");
            createNamedQuery.setParameter("secType", secType);
            List<InvestmentGoidates> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object blurOnControllNo(int ctrlNo, String securityType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_DETAILS);
            createNamedQuery.setParameter("controllNo", ctrlNo);
            createNamedQuery.setParameter("secType", securityType);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortizationDetails> getAmortMode(Long ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentAmortizationDetails.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            List<InvestmentAmortizationDetails> entiry = createNamedQuery.getResultList();
            return entiry;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getTotalSumOfDays() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TOTAL_SUM_OF_DAYS);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getTotalSumOfAmortAmt(Date years) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TOTAL_SUM_OF_AMORT_AMT);
            createNamedQuery.setParameter("years", years);
            createNamedQuery.setParameter("status", "P");
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentSecurityMaster getInvestmentSecurityMaster(String securityName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentSecurityMaster.findBySecurityName");
            createNamedQuery.setParameter("securityName", securityName);
            InvestmentSecurityMaster entiry = (InvestmentSecurityMaster) createNamedQuery.getSingleResult();
            return entiry;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getMaxCtrlNoByInvestmentMaster() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_CTRLNO_INVESTMENT_MASTER);
            createNamedQuery.setParameter("sectype", "TERM DEPOSIT");
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            }
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestMasterByCtrlNo(Date curDt, int ctrlNo) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INVEST_MASTER_BY_CTRLNO);
            createNamedQuery.setParameter("curDt", curDt);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String updateInvestGoiDatesByCtrlNo(Date closedt, int ctrlNo) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INVEST_GOIDATES_BY_CTRLNO);
            createNamedQuery.setParameter("closeDt", closedt);
            createNamedQuery.setParameter("status", "Close");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public List<Gltable> getGltable(String acname) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Gltable.findByAcName");
            createNamedQuery.setParameter("acName", acname);
            return (createNamedQuery.getResultList());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGlRecon(GlRecon entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGoiRecon(GoiRecon entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateAmortDetailsByCtrlNo(BigInteger oldCtrlNo, Long ctrlNo, Date closedt) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_AMORT_DETAILS_BY_CTRLNO);
            createNamedQuery.setParameter("oldCtrlNo", oldCtrlNo);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            createNamedQuery.setParameter("status", "A");
            createNamedQuery.setParameter("closeDt", closedt);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public InvestmentMaster getInvestMaster(int ctrlNo, String secType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_MASTER_BY_CTRLNO_AND_SECTYPE);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            createNamedQuery.setParameter("secType", secType);
            InvestmentMaster entity = (InvestmentMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentMaster(InvestmentMaster entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentGoidates getInvestmentGoidates(Integer ctrlno) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_GOIDATES_BY_CTRLNO);
            createNamedQuery.setParameter("ctrlNo", ctrlno);
            InvestmentGoidates entity = (InvestmentGoidates) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentGoidates(InvestmentGoidates entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentGoidates> getControllNoForAmortCalc(String status, String type) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_FOR_AMORT_CALC);
            createNamedQuery.setParameter("stat", status);
            createNamedQuery.setParameter("secType", type);
            List<InvestmentGoidates> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {            
            throw new ApplicationException(ex);
        }
    }

    public Object getCtrlNoDetailsAmortcalc(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_DETAILS_AMORT_CALC);
            createNamedQuery.setParameter("controllNo", ctrlNo);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentAmortizationDetails(InvestmentAmortizationDetails entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    public BranchMaster getBranchMasterByAlphacode(String alphacode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findByAlphaCode");
            createNamedQuery.setParameter("alphaCode", alphacode);
            BranchMaster entity = (BranchMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentCallMaster> getUnAuhorizeCallMaster() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_UNAUTHORIZE_CALL_MASTER);
            createNamedQuery.setParameter("auth", "N");
            List<InvestmentCallMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNo() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_CTRL_NO);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            }
            return (new Long((Long) obj).intValue() + 1);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentCallMaster getInvestCallMasterByCtrl(Long ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentCallMaster.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            InvestmentCallMaster entity = (InvestmentCallMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String deleteInvestmentCallMaster(Long ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.DELETE_INVESTMENT_CALL_MASTER_BY_CTRL);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            int no = createNamedQuery.executeUpdate();
            if (no > 0) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentCallMasterByCtrl(Long ctrlNo, String authBy) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INVESTMENT_CALL_MASTER_BY_CTRL);
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("authBy", authBy);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            int no = createNamedQuery.executeUpdate();
            if (no > 0) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentCallHead getInvestCallHeadByCode(String code) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentCallHead.findByCode");
            createNamedQuery.setParameter("code", code);
            InvestmentCallHead entity = null;
            List<InvestmentCallHead> list = createNamedQuery.getResultList();
            if (!list.isEmpty()) {
                entity = (InvestmentCallHead) list.get(0);
            }
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Gltable getGltableByAcno(String acNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("Gltable.findByAcNo");
            createNamedQuery.setParameter("acNo", acNo);
            Gltable entity = (Gltable) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNoFromInvestmentFdrDetails() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_CTRL_NO_INVESTMENT_FDR_DETAILS);
            Integer obj = (Integer) createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            }
            return obj + 1;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentFdrDates(InvestmentFdrDates entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentFdrDetails(InvestmentFdrDetails entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveFdrRecon(FdrRecon entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public ParameterinfoReport getParameterinfoReportByName(String reportName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("ParameterinfoReport.findByReportName");
            createNamedQuery.setParameter("reportName", reportName);
            ParameterinfoReport entity = (ParameterinfoReport) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getInvestmentDetailsAndDates(String status, Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentFdrDates(InvestmentFdrDates entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentFdrDetails(InvestmentFdrDetails entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getInvestmentDetailAndDatesForIntRecPostFdr(String status, Date purDt, String iOpt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_AND_DATES_FOR_INT_REC_POST_FDR);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("purDt", purDt);
            createNamedQuery.setParameter("iOpt", iOpt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Date getMaxToDateFromIntPostHistory(String param) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INT_POST_HISTORY);
            createNamedQuery.setParameter("actype", param);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                throw new ApplicationException("To date is null in int Post History table !");
            }
            return (Date) obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentFdrDetails getInvestmentFdrDetailsByCtrlNo(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentFdrDetails.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            InvestmentFdrDetails entity = (InvestmentFdrDetails) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentFdrDates getInvestmentFdrDatesByCtrlNo(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentFdrDates.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            InvestmentFdrDates entity = (InvestmentFdrDates) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveIntPostHistory(IntPostHistory entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getmaxCtrlNoInvestmentShare() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_CTRL_NO_INVESTMENT_SHARE);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            } else {
                return (Integer) obj + 1;
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentShare(InvestmentShare entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveShareRecon(ShareRecon entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentShare> getUnAuthorizeControlNo() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CTRL_NO_INVESTMENT_SHARE);
            createNamedQuery.setParameter("status", "ACTIVE");
            List<InvestmentShare> list = createNamedQuery.getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no control no to update !");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentShare getControlNoDetails(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentShare.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            InvestmentShare entity = (InvestmentShare) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentShare(InvestmentShare entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public BranchMaster getAlphaCodeByBrnCode(String orgncode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findByBrnCode");
            createNamedQuery.setParameter("brnCode", orgncode);
            BranchMaster entity = (BranchMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getSecRoi(String securityName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentSecurityMaster.findBySecurityName");
            createNamedQuery.setParameter("securityName", securityName);
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getAllSecName() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentSecurityMaster.findAll");
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public GlDescRange getCodeFrmCodeBook(String sec) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("GlDescRange.findByGlname");
            createNamedQuery.setParameter("glname", sec);
            GlDescRange entity = (GlDescRange) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<ParameterinfoReport> getGoiPurchaseCode(String reportName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("ParameterinfoReport.findByReportName");
            createNamedQuery.setParameter("reportName", reportName);
            List<ParameterinfoReport> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /*public InvestmentCallHead getInvestmentCallHead(String code) throws ApplicationException {
    try {
    Query createNamedQuery = entityManager.createNamedQuery("InvestmentCallhead.findByCode");
    createNamedQuery.setParameter("code", code);
    InvestmentCallHead entity = null;
    List<InvestmentCallHead> list = createNamedQuery.getResultList();
    if (!list.isEmpty()) {
    entity = (InvestmentCallHead) list.get(0);
    }
    return entity;
    } catch (Exception ex) {
    throw new ApplicationException(ex);
    }
    }*/
    public List<BranchMaster> getbrnCode(String alphaCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("BranchMaster.findByAlphaCode");
            createNamedQuery.setParameter("alphaCode", alphaCode);
            List<BranchMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getAllSecDtlBySecName(String securityName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentSecurityMaster.findBySecurityName");
            createNamedQuery.setParameter("securityName", securityName);
            List<InvestmentSecurityMaster> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestAmortPostHis(InvestmentAmortPostHistory entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentAmortDtl(String lastupdateby, Date lastupdatedt, BigInteger slno, Long ctrlno, Date years) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_AMORT_DETAILS);
            createNamedQuery.setParameter("status", "P");
            createNamedQuery.setParameter("lastUpdate", lastupdateby);
            createNamedQuery.setParameter("lastUpDt", lastupdatedt);
            createNamedQuery.setParameter("sno", slno);
            createNamedQuery.setParameter("ctrNo", ctrlno);
            createNamedQuery.setParameter("year", years);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public List<InvestmentAmortizationDetails> getAmortCtrlNo(Date yearAmort, BigInteger ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_FOR_SINGLE_POST);
            createNamedQuery.setParameter("ctrNo", ctrlNo);
            createNamedQuery.setParameter("dtTo", yearAmort);
            createNamedQuery.setParameter("status", "A");

            List<InvestmentAmortizationDetails> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Long> getCtrlNo() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_FOR_AMORT_POST);
            createNamedQuery.setParameter("status", "Active");
            createNamedQuery.setParameter("val", "A");
            return createNamedQuery.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortPostHistory> getPostStatusCtrl(BigInteger yearAmort, String monthAmort, BigInteger ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_AMORT_HIST_CTRL);
            createNamedQuery.setParameter("yearAmort", yearAmort);
            createNamedQuery.setParameter("monthAmort", monthAmort);
            createNamedQuery.setParameter("ctrlno", ctrlNo);
            List<InvestmentAmortPostHistory> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortPostHistory> getPostStatusAll(BigInteger yearAmort, String monthAmort) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_AMORT_HIST_ALL);
            createNamedQuery.setParameter("yearAmort", yearAmort);
            createNamedQuery.setParameter("monthAmort", monthAmort);
            List<InvestmentAmortPostHistory> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getTotalAmortAmt(Date years, BigInteger ctrNo,String stat) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TOTAL_AMORT_AMOUNT);
            createNamedQuery.setParameter("dtTo", years);
            createNamedQuery.setParameter("status", stat);
            createNamedQuery.setParameter("ctrNo", ctrNo);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getRepData(BigInteger ctrNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_AMORT_REPORT_LIST);
            createNamedQuery.setParameter("ctrNo", ctrNo);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentFdrDatesByCtrlNo(double amount, Integer ctrlno) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INVESTMENT_FDR_DATES);
            createNamedQuery.setParameter("intRec", 0);
            createNamedQuery.setParameter("totInt", amount);
            createNamedQuery.setParameter("ctrNo", ctrlno);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public Object getDetailDataOfCtrlNo(Integer ctrNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_FDR_CTRL_DETAIL_DATA);
            createNamedQuery.setParameter("ctrNo", ctrNo);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Integer> getCtrlNoFromInvestmentFdrDetails() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CTRL_NO_INVESTMENT_FDR_DETAILS);
            createNamedQuery.setParameter("status", "Active");
            return createNamedQuery.getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveFDRHis(InvestmentFdrIntHis entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateFDRDetails(Integer ctrlNo, String status) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_FDR_DETAILS_BY_CTRL_NO);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            createNamedQuery.setParameter("status", status);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public String updateFDRDatesBySno(Integer ctrlNo, Date dt) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_FDR_DATES_BY_CTRL_NO);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            createNamedQuery.setParameter("dt", dt);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }

    public List<String> getAllSellerName(String secTp) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_SELLER_NAME);
            createNamedQuery.setParameter("secTp", secTp);
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object getMaxCtrlNoByInvestmentMasterSec() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_CTRLNO_INVESTMENT_MASTER_SEC);
            createNamedQuery.setParameter("sectype", "TERM DEPOSIT");
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            }
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<GlDescRange> getGlRangeBySec(String glName) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("GlDescRange.findByGlname");
            createNamedQuery.setParameter("glname", glName);
            List<GlDescRange> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentFdrDatesAndDetailsHistory(InvestmentFrdDatesAndDetailsHistory entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public InvestmentGoidates getInvestmentGoiDatesByCtrlNo(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentGoidates.findByCtrlno");
            createNamedQuery.setParameter("ctrlno", ctrlNo);
            InvestmentGoidates entity = (InvestmentGoidates) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public Date getMaxToDateFromIntPostHistorySec() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INT_POST_HISTORY);
            createNamedQuery.setParameter("actype", "SEC");
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                throw new ApplicationException("To date is null in int Post History table !");
            }
            return (Date) obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String updateIntInvestGoiDatesByCtrlNo(int ctrlNo, Date tillDt, double amt, String userName ,double amtIntAccr,double totAmtIntAccr) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INT_INVEST_GOIDATES_BY_CTRLNO);
            createNamedQuery.setParameter("lastintpaiddt", tillDt);
            createNamedQuery.setParameter("amtinttrec", amt);
            createNamedQuery.setParameter("ctrlno", ctrlNo);
            createNamedQuery.setParameter("hyIntBy", userName);
            createNamedQuery.setParameter("amtIntAccr", amtIntAccr);
            createNamedQuery.setParameter("totAmtIntAccr", totAmtIntAccr);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }
    
    public Object getInvestmentDetailAndDatesForIntRecPostSec(String status, Date purDt) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_INVESTMENT_DETAIL_FOR_INT_REC_POST_SEC);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("purDt", purDt);
            Object obj = createNamedQuery.getResultList();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }
    
    public String updateInvestmentSecDates(InvestmentGoidates entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String getGLByCtrlNo(Integer ctrlNo) throws ApplicationException {
        String entity ="";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_GL_BY_CTRL_NO);
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            entity = createNamedQuery.getSingleResult().toString();                        
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return entity;
    }
    
    public Object getTotalAmortAmtBal(Integer ctrlNo,String status) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TOTAL_AMORT_AMT_BAL);
            createNamedQuery.setParameter("ctrNo", ctrlNo);
            createNamedQuery.setParameter("status", status);
            Object obj = createNamedQuery.getSingleResult();
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String updateAmortDetailsAllBalByCtrlNo(String lastupdateby, Date lastupdatedt, BigInteger ctrlno) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_AMORT_ALL_BAL_DETAILS);
            createNamedQuery.setParameter("status", "P");
            createNamedQuery.setParameter("lastUpdate", lastupdateby);
            createNamedQuery.setParameter("lastUpDt", lastupdatedt);
            createNamedQuery.setParameter("ctrNo", ctrlno);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }
    
    public InvestmentMaster getInvestmentMasterByCtrlNo(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentMaster.findByControlno");
            createNamedQuery.setParameter("controlno", ctrlNo);
            InvestmentMaster entity = (InvestmentMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String updateInvestMasterAccdIntByCtrlNo(double acdint, int ctrlNo) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_INVEST_MASTER_ACCDINT_BY_CTRLNO);
            createNamedQuery.setParameter("acdint", acdint);
            createNamedQuery.setParameter("ctrlno", ctrlNo);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }
    
    public List<InvestmentCallMaster> getUnAuthorizeCallCtrlNo() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CTRL_NO_INVESTMENT_CALL);
            createNamedQuery.setParameter("status", "ACTIVE");
            List<InvestmentCallMaster> list = createNamedQuery.getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no control no to update !");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public InvestmentCallMaster getCallCtrlNoDetails(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentCallMaster.findByCtrlNo");
            createNamedQuery.setParameter("ctrlNo", ctrlNo);
            InvestmentCallMaster entity = (InvestmentCallMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String updateCallMoney(InvestmentCallMaster entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public List<InvestmentSecurityAuthDetail> getUnAuthSecCtrlNo(String brnCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_SEQ_NO_INVESTMENT_SECURITY);
            createNamedQuery.setParameter("brnCode", brnCode);
            List<InvestmentSecurityAuthDetail> list = createNamedQuery.getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no control no to update !");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public List<InvestmentSecurityAuthDetail> getUnAuthCtrlNoDtl(int seqNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_SEQ_NO_INVESTMENT_SECURITY_DETAIL);
            createNamedQuery.setParameter("seqNo", seqNo);
            List<InvestmentSecurityAuthDetail> list = createNamedQuery.getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("There is no control no to update !");
            }
            return list;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String saveInvestmentSecurityAuthDetail(InvestmentSecurityAuthDetail entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }
    
    public String deleteInvestmentSecurityAuth(int seqNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.DELETE_SEQ_NO_INVESTMENT_SECURITY_AUTH);
            createNamedQuery.setParameter("seqNo", seqNo);
            int no = createNamedQuery.executeUpdate();
            if (no > 0) {
                return "true";
            } else {
                return "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public Object getMaxSeqNoInvestmentSecAuth() throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_MAX_SEQ_INVESTMENT_SECURITY_AUTH);
            Object obj = createNamedQuery.getSingleResult();
            if (obj == null) {
                return 1;
            }
            return obj;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public InvestmentSecurityAuthDetail getSecDetailToAuth(Integer seqNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentSecurityAuthDetail.findByMaxSec");
            createNamedQuery.setParameter("maxSec", seqNo);
            InvestmentSecurityAuthDetail entity = (InvestmentSecurityAuthDetail) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public String updateSecAuthDetail(InvestmentSecurityAuthDetail entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            return "false";
        }
    }
    
    public InvestmentGoidates getSecAccrByCtrlNo(Integer ctrlNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("InvestmentGoidates.findByCtrlno");
            createNamedQuery.setParameter("ctrlno", ctrlNo);
            InvestmentGoidates entity = (InvestmentGoidates) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    } 
    
    public List<InvestmentGoidates> getControllNoSecWise(String secType, String secDesc) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CONTROL_NO_SECURITY_DESC_WISE);
            createNamedQuery.setParameter("status", "active");
            createNamedQuery.setParameter("secType", secType);
            createNamedQuery.setParameter("secDesc", secDesc);
            List<InvestmentGoidates> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ApplicationException(ex);
        }
    }
    
    public List<String> getSecurityDesc(String secType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_SECURITY_DESC);
            createNamedQuery.setParameter("secType", secType);
            List<String> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }
    
    public List<InvestmentFdrDates> onblurCntrlNumber(int ctrlNo) throws ApplicationException{    
        try{
            Query createNamedQuery= entityManager.createQuery(NamedQueryConstant.DETAILS_AS_PER_CTRLNO);
            createNamedQuery.setParameter("cno",ctrlNo);
            List<InvestmentFdrDates>  obj = createNamedQuery.getResultList();
            return obj;
        }catch(Exception ex){
            throw new ApplicationException(ex);
        }
         
    }
    
    public List<InvestmentFdrDetails> controlNumberDeatils(int ctrlNo)throws ApplicationException{
        try{
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.CONTROL_NO_DETAILS);
            createNamedQuery.setParameter("cno",ctrlNo);
             List<InvestmentFdrDetails>  obj = createNamedQuery.getResultList();
               return obj;
        }catch(Exception ex){
            throw new ApplicationException(ex);
        }
    }
    
//    public String saveInvestmentMasterMerge(InvestmentMaster entity) throws ApplicationException {
//        try {
//            merge(entity);
//            return "true";
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//    }
//    
//    public String saveInvestmentGoidatesMerge(InvestmentGoidates entity) throws ApplicationException {
//        try {
//            merge(entity);
//            return "true";
//        } catch (Exception ex) {
//            throw new ApplicationException(ex);
//        }
//    }
}