/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.ho.investment;

import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dao.ho.investment.InvestmentMgmtDAO;
import com.cbs.entity.ho.investment.FdrRecon;
import com.cbs.entity.ho.investment.FdrReconPK;
import com.cbs.entity.ho.investment.GoiRecon;
import com.cbs.entity.ho.investment.GoiReconPK;
import com.cbs.entity.ho.investment.IntPostHistory;
import com.cbs.entity.ho.investment.IntPostHistoryPK;
import com.cbs.entity.ho.investment.InvestmentAmortPostHistory;
import com.cbs.entity.ho.investment.InvestmentAmortizationDetails;
import com.cbs.entity.ho.investment.InvestmentCallHead;
import com.cbs.entity.ho.investment.InvestmentCallMaster;
import com.cbs.entity.ho.investment.InvestmentFdrDates;
import com.cbs.entity.ho.investment.InvestmentFdrDetails;
import com.cbs.entity.ho.investment.InvestmentFdrIntHis;
import com.cbs.entity.ho.investment.InvestmentFdrIntHisPK;
import com.cbs.entity.ho.investment.InvestmentFrdDatesAndDetailsHistory;
import com.cbs.entity.ho.investment.InvestmentGoidates;
import com.cbs.entity.ho.investment.InvestmentMaster;
import com.cbs.entity.ho.investment.InvestmentMasterPK;
import com.cbs.entity.ho.investment.InvestmentSecurityAuthDetail;
import com.cbs.entity.ho.investment.InvestmentSecurityMaster;
import com.cbs.entity.ho.investment.InvestmentShare;
import com.cbs.entity.ho.investment.ParameterinfoReport;
import com.cbs.entity.ho.investment.ShareRecon;
import com.cbs.entity.ho.investment.ShareReconPK;
import com.cbs.entity.master.GlDescRange;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.master.BranchMaster;
import com.cbs.entity.transaction.GlRecon;
import com.cbs.entity.transaction.GlReconPK;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.td.TdReceiptManagementFacadeRemote;
import com.cbs.pojo.FdrIntPostPojo;
import com.cbs.pojo.GoiIntReportPojo;
import com.cbs.pojo.InvestmentFDRCloseRenewAuthPojo;
import com.cbs.pojo.InvestmentFDRInterestPojo;
import com.cbs.pojo.SecurityAuthClosePojo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author root
 */
@Stateless(mappedName = "InvestmentMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class InvestmentMgmtFacade implements InvestmentMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    EJBContext context;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsObj;
    @EJB
    private TdReceiptManagementFacadeRemote obj;
    InvestmentMgmtDAO dao = null;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd_Format = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat formatter = new DecimalFormat("#.##");
    Date dt = new Date();

    /**
     *
     * @param maturityYear
     * @return
     * @throws ApplicationException
     */
    public List<InvestmentSecurityMaster> getInvestmentSecurityToModify(String maturityYear) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentSecurityToModify(maturityYear));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getInvestmentSecurityToDelete(String maturityYear) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentSecurityToDelete(maturityYear));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> checkDuplicate(String maturityYear, double roi, String secrityName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.checkDuplicate(maturityYear, roi, secrityName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = dao.saveInvestmentSecurityMaster(entity);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String deleteInvestmentSecurityMaster(Long sno, String lastUpdateBy, Date curDt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = dao.deleteInvestmentSecurityMaster(sno, lastUpdateBy, curDt);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String modifyInvestmentSecurityMaster(InvestmentSecurityMaster entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = dao.modifyInvestmentSecurityMaster(entity);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<BranchMaster> getAllAlphaCode() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAlphaCode());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<GlDescRange> getGlRange(String glName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getGlRange(glName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSecurityType(String fromNo, String toNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getSecurityType(fromNo, toNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentGoidates> getControllNo(String secType) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getControllNo(secType));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object[] blurOnControllNo(int ctrlNo, String securityType) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            Object[] obj = (Object[]) (dao.blurOnControllNo(ctrlNo, securityType));
            return obj;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortizationDetails> getAmortMode(Long ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAmortMode(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Long getTotalSumOfDays() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (Long) dao.getTotalSumOfDays();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public BigDecimal getTotalSumOfAmortAmt(Date years) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (BigDecimal) (dao.getTotalSumOfAmortAmt(years));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentSecurityMaster getInvestmentSecurityMaster(String securityName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentSecurityMaster(securityName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNoByInvestmentMaster() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (Integer) (dao.getMaxCtrlNoByInvestmentMaster());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestMasterByCtrlNo(Date curDt, int ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.updateInvestMasterByCtrlNo(curDt, ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestGoiDatesByCtrlNo(Date closedt, int ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.updateInvestGoiDatesByCtrlNo(closedt, ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Gltable> getGltable(String acname) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getGltable(acname));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGlRecon(GlRecon entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveGlRecon(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGoiRecon(GoiRecon entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveGoiRecon(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateAmortDetailsByCtrlNo(BigInteger oldCtrlNo, Long ctrlNo, Date closedt) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.updateAmortDetailsByCtrlNo(oldCtrlNo, ctrlNo, closedt));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentMaster getInvestMaster(int ctrlNo, String secType) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestMaster(ctrlNo, secType));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentMaster(InvestmentMaster entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveInvestmentMaster(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentGoidates getInvestmentGoidates(Integer ctrlno) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentGoidates(ctrlno));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentGoidates(InvestmentGoidates entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveInvestmentGoidates(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentGoidates> getControllNoForAmortCalc(String type) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getControllNo(type));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object[] getCtrlNoDetailsAmortcalc(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (Object[]) (dao.getCtrlNoDetailsAmortcalc(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentAmortizationDetails(InvestmentAmortizationDetails entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = (dao.saveInvestmentAmortizationDetails(entity));
            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public BranchMaster getBranchMasterByAlphacode(String alphacode) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getBranchMasterByAlphacode(alphacode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveFaceEqualSellAmtPart(Date todayDt, int ctrlNo, String securityType, double sellingAmt, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, ctrlNo);
            updateInvestGoiDatesByCtrlNo(todayDt, ctrlNo);

            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");

            trsno = ftsObj.getTrsNo();
            recno = ftsObj.getRecNo();

            String insertMsg = insertDataOnSave(entity.getIntGlhead(), new BigInteger(String.valueOf(ctrlNo)), trsno, recno, balIntAmt, 0, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();

            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, balIntAmt, 1, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(String.valueOf(ctrlNo)), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();

            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, sellingAmt, 0, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, sellingAmt, 1, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<String> getInsertAcnos(String creditedGlHead, String securityType, String ogrnBrCode) {
        List<String> acnoList = new ArrayList<String>();
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");
            if (entity != null) {
                acnoList.add(entity.getCrGlhead());
                acnoList.add(entity.getDrGlhead());
            }
            acnoList.add(creditedGlHead);


            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    acnoList.add(ogrnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01");
                }
            }
        } catch (Exception e) {
            return acnoList = new ArrayList<String>();
        }
        return acnoList;
    }

    public String insertDataOnSave(String acno, BigInteger ctrNo, Float trsno, Float recno, double amount, Integer ty, Integer trantype, double payby, String details, String auth, String orgncode, String destcode, String enterby, String alphacode) {
        String msg = "";
        try {
            /**
             * GL RECON INSERTION
             */
            GlRecon glReconEntity = new GlRecon();
            GlReconPK glReconPK = new GlReconPK(acno, ymd.parse(ymd.format(dt)), recno);
            glReconEntity.setGlReconPK(glReconPK);
            glReconEntity.setBalance(0d);

            glReconEntity.setTy(ty);
            if (ty == 0) {
                glReconEntity.setDrAmt(0);
                glReconEntity.setCrAmt(Double.parseDouble(formatter.format(amount)));
            } else if (ty == 1) {
                glReconEntity.setDrAmt(Double.parseDouble(formatter.format(amount)));
                glReconEntity.setCrAmt(0);
            }

            glReconEntity.setTranType(trantype);
            glReconEntity.setTrsno(trsno.intValue());
            glReconEntity.setInstno("");
            glReconEntity.setPayby(payby);
            glReconEntity.setIy(0);

            glReconEntity.setTranDesc(0);
            glReconEntity.setAuth(auth);
            glReconEntity.setEnterBy(enterby);
            glReconEntity.setAuthby("System");
            glReconEntity.setTrantime(dt);

            glReconEntity.setDetails(details);
            glReconEntity.setTranid(new BigInteger("0"));
            glReconEntity.setOrgBrnid(orgncode);
            glReconEntity.setDestBrnid(destcode);
            BranchMaster brMasterEntity = getBranchMasterByAlphacode(alphacode);
            String adviceBrnCode = String.format("%02d", brMasterEntity.getBrnCode());
            glReconEntity.setAdviceBrnCode(adviceBrnCode);
            glReconEntity.setValueDt(dt);

            saveGlRecon(glReconEntity);

            /**
             * GOI RECON INSERTION
             */
            GoiRecon goiReconEntity = new GoiRecon();

            GoiReconPK goiReconPK = new GoiReconPK(ctrNo.longValue(), acno, ymd.parse(ymd.format(dt)), recno);

            goiReconEntity.setGoiReconPK(goiReconPK);
            goiReconEntity.setBalance(0d);

            goiReconEntity.setTy(ty);
            if (ty == 0) {
                goiReconEntity.setDramt(0);
                goiReconEntity.setCramt(Double.parseDouble(formatter.format(amount)));
            } else if (ty == 1) {
                goiReconEntity.setDramt(Double.parseDouble(formatter.format(amount)));
                goiReconEntity.setCramt(0);
            }

            goiReconEntity.setTrantype(trantype);
            goiReconEntity.setTrsno(trsno.intValue());
            goiReconEntity.setInstno("");
            goiReconEntity.setPayby(payby);
            goiReconEntity.setIy(0);

            goiReconEntity.setTrandesc(0);
            goiReconEntity.setDetails(details);
            goiReconEntity.setTranId(new BigInteger("0"));
            goiReconEntity.setOrgBrnid(orgncode);

            goiReconEntity.setDestBrnid(destcode);
            goiReconEntity.setTrantime(dt);
            goiReconEntity.setAuth(auth);
            goiReconEntity.setEnterby(enterby);
            goiReconEntity.setAuthby("System");

            msg = saveGoiRecon(goiReconEntity);
            msg = "true";
        } catch (Exception ex) {
            return msg = "false";
        }
        return msg;
    }

    public String saveFaceGreaterSellAmtPart(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateInvestGoiDatesByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateAmortDetailsByCtrlNo(new BigInteger(maxSec), Long.parseLong(ctrlNo), todayDt);

            /**
             * ************* No Need For This Code Open After Discussion
             * ************
             */
//            InvestmentMaster investmentMaster = getInvestMaster(Integer.parseInt(ctrlNo), securityType);
//
//            InvestmentMasterPK investmentMasterPK = new InvestmentMasterPK(Integer.parseInt(maxSec), investmentMaster.getInvestmentMasterPK().getSectype());
//            InvestmentMaster investMasterEntity = new InvestmentMaster(investmentMasterPK, investmentMaster.getSecdesc(), investmentMaster.getInvstcode(), investmentMaster.getRoi(), investmentMaster.getTransdt(), investmentMaster.getSettledt(), dt, investmentMaster.getSellername(), investmentMaster.getBuyeracno(), investmentMaster.getBrokeracno(), (investmentMaster.getFacevalue() - sellingAmt), investmentMaster.getBrokerage(), 0d, investmentMaster.getEnterby(), investmentMaster.getMatdt(), ((investmentMaster.getFacevalue() - sellingAmt) * issuingPrice) / 100, investmentMaster.getBrokername(), investmentMaster.getAuth(), investmentMaster.getAuthby(), investmentMaster.getPricegsec(), investmentMaster.getYtm());
//
//            dao = new InvestmentMgmtDAO(entityManager);
//            dao.saveInvestmentMasterMerge(investMasterEntity);
//            
//            InvestmentGoidates investmentGoidates = getInvestmentGoidates(Integer.parseInt(ctrlNo));
//
//            InvestmentGoidates investmentGoidatesEntity = new InvestmentGoidates(Integer.parseInt(maxSec), dt, investmentGoidates.getPrintedIssuedt(), investmentGoidates.getMatdt(), investmentGoidates.getCloseddt(), investmentGoidates.getIntpdt1(), investmentGoidates.getIntpdt2(), investmentGoidates.getLastintpaiddt(), investmentGoidates.getFlag(), investmentGoidates.getCode(), investmentGoidates.getAmtinttrec(), investmentGoidates.getRecflag(), investmentGoidates.getIssueUnits(), investmentGoidates.getStatus(), investmentGoidates.getAmtIntAccr(), investmentGoidates.getTotAmtIntAccr(), investmentGoidates.getTotIntRec(), investmentGoidates.getLastIntPaidDtAccr());
//            dao.saveInvestmentGoidatesMerge(investmentGoidatesEntity);            
            /**
             * ************* No Need For This Code Open After Discussion
             * ************
             */
            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");
            trsno = ftsObj.getTrsNo();
            String insertMsg = "";
            if (balIntAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getIntGlhead(), new BigInteger(maxSec), trsno, recno, balIntAmt, 0, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, balIntAmt, 1, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            if (intAccAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(maxSec), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, issuingPrice, 0, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, issuingPrice, 1, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            InvestmentCallHead entity1 = dao.getInvestCallHeadByCode("13");

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(entity1.getCrGlhead(), new BigInteger(maxSec), trsno, recno, (sellingAmt - issuingPrice), 0, 2, 3d, "Profit On Redm. Of Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, (sellingAmt - issuingPrice), 1, 2, 3d, "Profit On Redm. Of Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<InvestmentCallMaster> getUnAuhorizeCallMaster() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getUnAuhorizeCallMaster());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNo() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getMaxCtrlNo());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            ut.begin();
            result = dao.saveInvestmentCallMaster(entity);
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String updateInvestmentCallMaster(InvestmentCallMaster entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            ut.begin();
            result = (dao.updateInvestmentCallMaster(entity));
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public InvestmentCallMaster getInvestCallMasterByCtrl(Long ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestCallMasterByCtrl(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String deleteInvestmentCallMaster(Long ctrlNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            ut.begin();
            result = (dao.deleteInvestmentCallMaster(ctrlNo));
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String authorizeEntry(Long ctrlNo, String authBy, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", crHead = "", drHead = "", intHead = "";
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            ut.begin();

            InvestmentCallMaster entity = getInvestCallMasterByCtrl(ctrlNo);
            if (entity == null) {
                throw new ApplicationException("There is no data Investment Call Master for control no :" + ctrlNo);
            }

            if (entity.getEnterBy().equalsIgnoreCase(authBy)) {
                throw new ApplicationException("You can not authorize your own entry !");
            }

            int compareNo = entity.getDealDt().compareTo(ymd.parse(ymd.format(dt)));
            if (compareNo != 0) {
                throw new ApplicationException("You can authorize only today entry !");
            }

            String msg = dao.updateInvestmentCallMasterByCtrl(ctrlNo, authBy);
            if (msg.equalsIgnoreCase("true")) {
                InvestmentCallHead callEntity = dao.getInvestCallHeadByCode("1");
                if (callEntity != null) {
                    if (entity.getDrGlHead() == null || entity.getDrGlHead().equalsIgnoreCase("") || entity.getDrGlHead().length() != 12) {
                        throw new ApplicationException("Credit GL Haed is not defined in Investment Call Head.");
                    } else {
                        crHead = entity.getDrGlHead();
                    }
                    if (callEntity.getDrGlhead() == null || callEntity.getDrGlhead().equalsIgnoreCase("") || callEntity.getDrGlhead().length() != 12) {
                        throw new ApplicationException("Debit GL Haed is not defined");
                    } else {
                        drHead = callEntity.getDrGlhead();
                    }
                    if (callEntity.getIntGlhead() == null || callEntity.getIntGlhead().equalsIgnoreCase("") || callEntity.getIntGlhead().length() != 12) {
                        throw new ApplicationException("Interest GL Haed is not defined in Investment Call Head.");
                    } else {
                        intHead = callEntity.getIntGlhead();
                    }

                    Float trsno = ftsObj.getTrsNo();
                    Float recno = ftsObj.getRecNo();

                    String insertMsg = insertDataIntoGlRecon(drHead, trsno, recno, entity.getAmt(), 1, 2, 3d, entity.getDetails(), "Y", orgnCode, orgnCode, authBy, 0, 3, authBy);
                    if (insertMsg.equalsIgnoreCase("false")) {
                        throw new ApplicationException();
                    }

                    recno = ftsObj.getRecNo();

                    insertMsg = insertDataIntoGlRecon(crHead, trsno, recno, entity.getAmt(), 0, 2, 3d, entity.getDetails(), "Y", orgnCode, orgnCode, authBy, 0, 3, authBy);
                    if (insertMsg.equalsIgnoreCase("false")) {
                        throw new ApplicationException();
                    }
                    result = "true";
                    ut.commit();
                } else {
                    throw new ApplicationException("Please insert data in Investment Call Head.");
                }
            } else {
                throw new ApplicationException("Updation problem In Investment Call Master.");
            }
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

    public String insertDataIntoGlRecon(String acno, Float trsno, Float recno, double amount, Integer ty, Integer trantype, double payby, String details, String auth, String orgncode, String destcode, String enterby, Integer iy, Integer trandesc, String authBy) {
        String msg = "";
        try {
            GlRecon glReconEntity = new GlRecon();
            GlReconPK glReconPK = new GlReconPK(acno, ymd.parse(ymd.format(dt)), recno);
            glReconEntity.setGlReconPK(glReconPK);
            glReconEntity.setBalance(0d);

            glReconEntity.setTy(ty);
            if (ty == 0) {
                glReconEntity.setDrAmt(0);
                glReconEntity.setCrAmt(Double.parseDouble(formatter.format(amount)));
            } else if (ty == 1) {
                glReconEntity.setDrAmt(Double.parseDouble(formatter.format(amount)));
                glReconEntity.setCrAmt(0);
            }

            glReconEntity.setTranType(trantype);
            glReconEntity.setTrsno(trsno.intValue());
            glReconEntity.setInstno("");
            glReconEntity.setPayby(payby);

            glReconEntity.setIy(iy);
            glReconEntity.setTranDesc(trandesc);
            glReconEntity.setDetails(details);
            glReconEntity.setTranid(new BigInteger("0"));

            glReconEntity.setOrgBrnid(orgncode);
            glReconEntity.setDestBrnid(destcode);
            glReconEntity.setTrantime(dt);
            glReconEntity.setAuth(auth);
            glReconEntity.setEnterBy(enterby);
            glReconEntity.setAuthby(authBy);
            glReconEntity.setValueDt(dt);

            saveGlRecon(glReconEntity);
            return msg = "true";
        } catch (Exception ex) {
            return msg = "false";
        }
    }

    public Gltable getGltableByAcno(String acNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getGltableByAcno(acNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNoFromInvestmentFdrDetails() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getMaxCtrlNoFromInvestmentFdrDetails());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String fdrSaveProcess(Integer maxCtrlNo, Date purDate, Date matDate, String intOpt, String days,
            String months, String years, String details, String roi, String bank, double facevalue, double bookValue,
            String fdrNo, String branch, String user, String crHead, String orgnCode, String secTp, int txnid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        try {
            ut.begin();
            /**
             * save process in Investment FDR Dates
             */
            InvestmentFdrDates invFdrDates = new InvestmentFdrDates();
            invFdrDates.setCtrlNo(maxCtrlNo);
            invFdrDates.setPurchaseDt(purDate);
            invFdrDates.setMatDt(matDate);
            invFdrDates.setIntPdt(purDate);
            invFdrDates.setLastIntPaidDt(purDate);
            invFdrDates.setFlag("P");
            invFdrDates.setIntOpt(intOpt);
            invFdrDates.setAmtIntTrec(0.0);

            invFdrDates.setTotAmtIntRec(0.0);
            invFdrDates.setDays(new BigInteger(days));
            invFdrDates.setMonths(new BigInteger(months));
            invFdrDates.setYears(new BigInteger(years));

            String msg = dao.saveInvestmentFdrDates(invFdrDates);
            if (msg.equals("true")) {
                /**
                 * save process in Investment FDR Details
                 */
                InvestmentFdrDetails invFdrDetails = new InvestmentFdrDetails();
                invFdrDetails.setCtrlNo(maxCtrlNo);
                invFdrDetails.setSecType(secTp);
                invFdrDetails.setSecDesc(details);
                invFdrDetails.setRoi(Double.parseDouble(roi));
                invFdrDetails.setSellerName(bank);
                invFdrDetails.setFaceValue(facevalue);
                invFdrDetails.setBookValue(bookValue);
                invFdrDetails.setIntOpt(intOpt);
                invFdrDetails.setEnterBy(user);
                invFdrDetails.setAuth("Y");
                invFdrDetails.setAuthBy(user);
                invFdrDetails.setStatus("ACTIVE");
                invFdrDetails.setTranTime(dt);
                invFdrDetails.setBranch(branch);
                invFdrDetails.setFdrNo(fdrNo);

                String detailMsg = dao.saveInvestmentFdrDetails(invFdrDetails);
                if (detailMsg.equals("true")) {

                    String drAcno = "";
                    List<Gltable> entityList = dao.getGltable(bank);
                    for (Gltable var : entityList) {
                        drAcno = var.getAcNo();
                    }
                    /**
                     * save process in FDR Recon
                     */
                    trsno = ftsObj.getTrsNo();
                    recno = ftsObj.getRecNo();

                    FdrRecon fdrRecon = new FdrRecon();
                    FdrReconPK fdrReconPK = new FdrReconPK();

                    fdrReconPK.setAcno(drAcno);
                    fdrReconPK.setDt(purDate);
                    fdrReconPK.setRecno(recno);

                    fdrRecon.setFdrReconPK(fdrReconPK);
                    fdrRecon.setCtrlNo(maxCtrlNo);
                    fdrRecon.setBalance(0.0);
                    fdrRecon.setDrAmt(facevalue);

                    fdrRecon.setCrAmt(0.0);
                    fdrRecon.setTy(0);
                    fdrRecon.setTrantype(0);
                    fdrRecon.setTrsno(trsno.intValue());

                    fdrRecon.setPayBy(3);
                    fdrRecon.setIy(0);
                    fdrRecon.setTranDesc(3);
                    fdrRecon.setDetails("One Time Entry of FDR");

                    fdrRecon.setOrgBrnid(orgnCode);
                    fdrRecon.setDestBrnid(crHead.substring(0, 2));
                    fdrRecon.setTranTime(dt);
                    fdrRecon.setAuth("Y");
                    fdrRecon.setEnterBy(user);
                    fdrRecon.setAuthBy(user);

                    String fdrMsg = dao.saveFdrRecon(fdrRecon);
                    if (fdrMsg.equals("true")) {
                        ParameterinfoReport entity = dao.getParameterinfoReportByName("FDPURCHASE");
                        if (entity != null) {
                            if (entity.getCode() == 1) {
                                if (drAcno == null || drAcno.equalsIgnoreCase("")) {
                                    throw new ApplicationException("There should be entry for FDPURCHASE in ParameterInfo Report table !");
                                } else {
                                    recno = ftsObj.getRecNo();
                                    String glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, facevalue, 1, 2, 3, "PURCHASE ENTRY OF FDR", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                                    if (glMsg.equals("true")) {
                                        recno = ftsObj.getRecNo();
                                        insertDataIntoGlRecon(crHead, trsno, recno, facevalue, 0, 2, 3, "PURCHASE ENTRY OF FDR", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_fdr_creation_auth set auth ='Y', "
                    + " auth_by='" + user + "' where TXNID = " + txnid + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }
            result = "true" + trsno.toString();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public Object[] getInvestmentDetailsAndDates(String status, Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            Object[] obj = (Object[]) (dao.getInvestmentDetailsAndDates(status, ctrlNo));
            return obj;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String fdrUpdation(InvestmentFdrDates invFdrDates, InvestmentFdrDetails invFdrDetails) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            String msg = dao.updateInvestmentFdrDates(invFdrDates);
            if (msg.equals("true")) {
                dao.updateInvestmentFdrDetails(invFdrDetails);
            }
            result = "true";
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<Object[]> getInvestmentDetailAndDatesForIntRecPostFdr(String status, Date purDt, String iOpt) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (List<Object[]>) dao.getInvestmentDetailAndDatesForIntRecPostFdr(status, purDt, iOpt);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String postIntRecFdr(List<InvestmentFDRInterestPojo> tableList, double totAmt, Date tillDt, String authBy, String orgncode, String intOpt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        try {
            ut.begin();
            InvestmentCallHead entity = dao.getInvestCallHeadByCode("6");
            if (entity == null) {
                throw new ApplicationException("Data does not exist in Investment Call Head !");
            } else {
                if (entity.getDrGlhead() == null || entity.getDrGlhead().equals("") || entity.getCrGlhead() == null || entity.getCrGlhead().equals("")) {
                    throw new ApplicationException("GL Head does not present in Investment Call Head !");
                }
            }

            Date maxIntPostDt = getMaxToDateFromIntPostHistory("FDR" + "_" + intOpt);
            if (tillDt.compareTo(maxIntPostDt) <= 0) {
                throw new ApplicationException("Interest already posted upto " + dmy.format(maxIntPostDt));
            }

            if (tableList.isEmpty()) {
                throw new ApplicationException("There is no data to post !");
            } else {
                Double dblInterest = 0.0;
                String glMsg = "";
                trsno = ftsObj.getTrsNo();
                for (int i = 0; i < tableList.size(); i++) {
                    dblInterest = Double.parseDouble(tableList.get(i).getAmtIntRec().toString());
                    if (dblInterest > 0) {
                        String branch = "";
                        InvestmentFdrDetails fdrEntity = dao.getInvestmentFdrDetailsByCtrlNo(tableList.get(i).getCtrlNo());
                        if (fdrEntity == null) {
                            throw new ApplicationException("Data not found in Fdr Details !");
                        } else {
                            branch = fdrEntity.getBranch();
                        }
                        String amount = formatter.format(dblInterest);
                        String AcnoFdr = dao.getGLByCtrlNo(tableList.get(i).getCtrlNo());

                        if (Double.parseDouble(amount) > 0) {
                            recno = ftsObj.getRecNo();

                            FdrRecon fdrRecon = new FdrRecon();
                            FdrReconPK fdrReconPK = new FdrReconPK();

                            fdrReconPK.setAcno(AcnoFdr);
                            fdrReconPK.setDt(ymd.parse(ymd.format(dt)));
                            fdrReconPK.setRecno(recno);

                            fdrRecon.setFdrReconPK(fdrReconPK);
                            fdrRecon.setCtrlNo(tableList.get(i).getCtrlNo());
                            fdrRecon.setBalance(0.0);
                            fdrRecon.setDrAmt(Double.parseDouble(formatter.format(dblInterest)));

                            fdrRecon.setCrAmt(0.0);
                            fdrRecon.setTy(0);
                            fdrRecon.setTrantype(8);
                            fdrRecon.setTrsno(trsno.intValue());

                            fdrRecon.setPayBy(3);
                            fdrRecon.setIy(0);
                            fdrRecon.setTranDesc(3);
                            fdrRecon.setDetails("Entry of Intt. Rece. for FDR");

                            fdrRecon.setOrgBrnid(branch);
                            fdrRecon.setDestBrnid(orgncode);
                            fdrRecon.setTranTime(dt);
                            fdrRecon.setAuth("Y");
                            fdrRecon.setEnterBy(authBy);
                            fdrRecon.setAuthBy(authBy);

                            glMsg = dao.saveFdrRecon(fdrRecon);
                            if (glMsg.equals("true")) {
                                InvestmentFdrDates dateEntity = dao.getInvestmentFdrDatesByCtrlNo(tableList.get(i).getCtrlNo());
                                if (dateEntity == null) {
                                    throw new ApplicationException("Data not found in Investment Frd Dates !");
                                }

                                InvestmentFdrDates fdrDateEntity = new InvestmentFdrDates();
                                fdrDateEntity.setCtrlNo(tableList.get(i).getCtrlNo());
                                fdrDateEntity.setPurchaseDt(dateEntity.getPurchaseDt());
                                fdrDateEntity.setMatDt(dateEntity.getMatDt());
                                fdrDateEntity.setClosedDt(dateEntity.getClosedDt());
                                fdrDateEntity.setIntPdt(dateEntity.getIntPdt());
                                fdrDateEntity.setLastIntPaidDt(tillDt);
                                fdrDateEntity.setFlag("R");
                                fdrDateEntity.setIntOpt(dateEntity.getIntOpt());
                                fdrDateEntity.setAmtIntTrec(Double.parseDouble(formatter.format(dblInterest)));
                                fdrDateEntity.setTotAmtIntRec(dateEntity.getTotAmtIntRec() + Double.parseDouble(formatter.format(dblInterest)));
                                fdrDateEntity.setDays(dateEntity.getDays());
                                fdrDateEntity.setMonths(dateEntity.getMonths());
                                fdrDateEntity.setYears(dateEntity.getYears());
                                dao.updateInvestmentFdrDates(fdrDateEntity);
                                //}
                            }
                        }
                    }
                }
                if (glMsg.equals("true")) {
                    recno = ftsObj.getRecNo();
                    glMsg = insertDataIntoGlRecon(entity.getDrGlhead(), trsno, recno, totAmt, 0, 2, 3, "Int Rec. OF FDRs Quarter" + dmy.format(tillDt), "Y", orgncode, orgncode, authBy, 0, 0, "System");
                    if (glMsg.equalsIgnoreCase("True")) {
                        glMsg = ftsObj.updateBalance("PO", entity.getDrGlhead(), totAmt, 0, "Y", "Y");
                        if (glMsg.equalsIgnoreCase("true")) {
                            recno = ftsObj.getRecNo();
                            glMsg = insertDataIntoGlRecon(entity.getIntGlhead(), trsno, recno, totAmt, 1, 2, 3, "Int Rec. OF FDRs Quarter " + dmy.format(tillDt), "Y", orgncode, orgncode, authBy, 0, 0, "System");
                            if (glMsg.equalsIgnoreCase("True")) {
                                glMsg = ftsObj.updateBalance("PO", entity.getIntGlhead(), 0, totAmt, "Y", "Y");
                                if (glMsg.equalsIgnoreCase("True")) {
                                    if (glMsg.equals("true")) {
                                        IntPostHistory intPostEntity = new IntPostHistory();
                                        IntPostHistoryPK intPostEntityPk = new IntPostHistoryPK();

                                        intPostEntityPk.setActype("FDR" + "_" + intOpt);
                                        intPostEntityPk.setFromdate(tillDt);
                                        intPostEntityPk.setBrncode(orgncode);

                                        intPostEntity.setIntPostHistoryPK(intPostEntityPk);
                                        intPostEntity.setTodate(tillDt);
                                        intPostEntity.setEnterby(authBy);
                                        intPostEntity.setAuth("Y");
                                        intPostEntity.setTrantime(tillDt);
                                        intPostEntity.setAuthby(authBy);

                                        dao.saveIntPostHistory(intPostEntity);
                                    }
                                } else {
                                    throw new ApplicationException("Updation Problem In " + entity.getIntGlhead());
                                }
                            } else {
                                throw new ApplicationException("Insertion Problem In " + entity.getIntGlhead());
                            }
                        } else {
                            throw new ApplicationException("Updation Problem in " + entity.getDrGlhead());
                        }
                    } else {
                        throw new ApplicationException("Insertion Problem In " + entity.getDrGlhead());
                    }
                } else {
                    throw new ApplicationException("Problem!");
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

    public Date getMaxToDateFromIntPostHistory(String param) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getMaxToDateFromIntPostHistory(param);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getmaxCtrlNoInvestmentShare() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getmaxCtrlNoInvestmentShare();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveShareCreation(Integer maxCtrlNo, String secType, Date purDate, String details, String bank, double facevalue, String shareCertificate,
            String branch, String user, String crHead, String orgnCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "", cracno = "", dracno = "";
        Float trsno = 0.0f, recno = 0.0f;
        try {
            /**
             * Creation of entity of Investment share
             */
            ut.begin();
            InvestmentShare invshare = new InvestmentShare();
            invshare.setCtrlNo(maxCtrlNo);
            invshare.setSecType(secType);
            invshare.setSecDesc(details);
            invshare.setRoi(0);
            invshare.setSellerName(bank);
            invshare.setFaceValue(facevalue);
            invshare.setBookValue(0);
            invshare.setIntOpt("");
            invshare.setEnterBy(user);
            invshare.setAuth("Y");
            invshare.setAuthBy(user);
            invshare.setStatus("ACTIVE");
            invshare.setTranTime(dt);
            invshare.setBranch(branch);
            invshare.setFdrNo(shareCertificate);
            invshare.setPurchaseDt(purDate);

            String msg = dao.saveInvestmentShare(invshare);
            if (msg.equals("true")) {
                List<Gltable> dataList = dao.getGltable(bank);
                if (!dataList.isEmpty()) {
                    for (Gltable entity : dataList) {
                        dracno = entity.getAcNo();
                    }
                } else {
                    throw new ApplicationException("GL Head not found in gltable for " + bank);
                }

                /**
                 * Creation of entity of Share recon
                 */
                trsno = ftsObj.getTrsNo();
                recno = ftsObj.getRecNo();

                ShareRecon shareRecon = new ShareRecon();
                ShareReconPK shareReconPK = new ShareReconPK();

                shareReconPK.setAcno(dracno);
                shareReconPK.setDt(purDate);
                shareReconPK.setRecno(recno);

                shareRecon.setShareReconPK(shareReconPK);
                shareRecon.setCtrlno(maxCtrlNo);
                shareRecon.setBalance(0.0);
                shareRecon.setCrAmt(0.0);
                shareRecon.setDrAmt(facevalue);
                shareRecon.setTy(0);
                shareRecon.setTranType(0);
                shareRecon.setTrsno(trsno.intValue());
                shareRecon.setPayby(3);
                shareRecon.setIy(0);
                shareRecon.setTranDesc(3);
                shareRecon.setDetails("Entry of Share with Bank");
                shareRecon.setOrgBrnid(orgnCode);
                shareRecon.setDestBrnid(orgnCode);
                shareRecon.setTranTime(dt);
                shareRecon.setAuth("Y");
                shareRecon.setEnterBy(user);
                shareRecon.setAuthBy(user);

                msg = dao.saveShareRecon(shareRecon);
                if (msg.equals("true")) {
                    ParameterinfoReport entity = dao.getParameterinfoReportByName("FDPURCHASE");
                    if (entity != null) {
                        if (entity.getCode() == 1) {
                            String drAcno = "";
                            List<Gltable> entityList = dao.getGltable(bank);
                            for (Gltable var : entityList) {
                                drAcno = var.getAcNo();
                            }
                            if (drAcno == null || drAcno.equalsIgnoreCase("")) {
                                throw new ApplicationException("There should be entry for FDPURCHASE in ParameterInfo Report table !");
                            } else {
                                recno = ftsObj.getRecNo();
                                String glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, facevalue, 1, 2, 3, "PURCHASE ENTRY OF SHARE", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                                if (glMsg.equals("true")) {
                                    recno = ftsObj.getRecNo();
                                    insertDataIntoGlRecon(crHead, trsno, recno, facevalue, 0, 2, 3, "PURCHASE ENTRY OF SHARE", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                                }
                            }
                        }
                    }
                }
            }
            result = "true" + trsno.toString();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<InvestmentShare> getUnAuthorizeControlNo() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getUnAuthorizeControlNo();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentShare getControlNoDetails(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getControlNoDetails(ctrlNo);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentShare(InvestmentShare entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String msg = "";
        try {
            ut.begin();
            msg = dao.updateInvestmentShare(entity);
            if (msg.equals("true")) {
                msg = "true";
                ut.commit();
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg;
    }

    public BranchMaster getAlphaCodeByBrnCode(String orgncode) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getAlphaCodeByBrnCode(orgncode);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentCallHead getInvestCallHeadByCode(String code) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getInvestCallHeadByCode(code);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String closeShare(Integer ctrlNo, double faceValue, String user, String bank, String drHead, String orgncode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f, recno = 0.0f;
        try {
            /**
             * Creation of Investment share Entity
             */
            ut.begin();
            InvestmentShare invShare = getInvestmentShare(ctrlNo);
            invShare.setStatus("CLOSED");
            invShare.setCloseDt(dt);
            String msg = dao.updateInvestmentShare(invShare);
            if (msg.equals("true")) {
                ParameterinfoReport entity = dao.getParameterinfoReportByName("FDPURCHASE");
                if (entity != null) {
                    if (entity.getCode() == 1) {
                        String drAcno = "";
                        List<Gltable> entityList = dao.getGltable(bank);
                        for (Gltable var : entityList) {
                            drAcno = var.getAcNo();
                        }
                        if (drAcno == null || drAcno.equalsIgnoreCase("")) {
                            throw new ApplicationException("There should be entry for FDPURCHASE in ParameterInfo Report table !");
                        } else {
                            trsno = ftsObj.getTrsNo();
                            recno = ftsObj.getRecNo();
                            String glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, faceValue, 0, 2, 3, "PURCHASE ENTRY OF SHARE", "Y", orgncode, drHead.substring(0, 2), user, 0, 0, "System");
                            if (glMsg.equals("true")) {
                                recno = ftsObj.getRecNo();
                                insertDataIntoGlRecon(drHead, trsno, recno, faceValue, 1, 2, 3, "PURCHASE ENTRY OF SHARE", "Y", orgncode, drHead.substring(0, 2), user, 0, 0, "System");
                            }
                        }
                    }
                }
            }

            result = "true" + trsno.toString();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<InvestmentSecurityMaster> getSecRoi(String securityName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getSecRoi(securityName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityMaster> getAllSecName() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAllSecName());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public GlDescRange getCodeFrmCodeBook(String sec) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getCodeFrmCodeBook(sec));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGoiSecurity(int maxSec, String getCrGlVal, String getSecTpDd, String getFaceValue, String getOrgnBrCode, String getCrBranchDd, String getUserName, String getBookValue, String getAccrIntVal, String getIssueFrDd, String getCodeNoVal, double getRoiVal, Date getTransactionDate, String setFlag, Date getSettlementDate, String getSellerNmVal, String getConAcNoVal, String getBrokerAcVal, double getBrokerageAmtVal, int getNoOfShrVal, Date getMatDate, String getConSgAccountDd, double getPriceVal, String getPurchaseDate, String getPrnIssueDate, String getfInttPayDate, String getsInttPayDate, String getIssuePrVal, String dtl, double ytm, String enterBy, int seqNo, String marking) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "", dracno = "", rsCrHead = "", rsDrHead = "", msg = "", codeGoi = "";
        Float trsno = 0f, recno = 0f;
        double prem_balance = 0d;
        Date pDt = null, nDt = null;
        try {
            ut.begin();

            if (setFlag.equals("false")) {
                pDt = getSettlementDate;
            } else {
                pDt = getTransactionDate;
            }

            if (getPurchaseDate.equals("")) {
                nDt = ymd.parse(getPurchaseDate);
            } else {
                nDt = ymd.parse(ymd.format(new Date()));
            }

            msg = saveInvestmentData(maxSec, getSecTpDd, getIssueFrDd, getCodeNoVal, getRoiVal, getTransactionDate, pDt, getMatDate, getSellerNmVal, getConAcNoVal, getConSgAccountDd, getBrokerAcVal, Double.valueOf(formatter.format(Double.valueOf(getFaceValue))), Double.valueOf(formatter.format(Double.valueOf(getBookValue))), getBrokerageAmtVal, Double.valueOf(formatter.format(Double.valueOf(getAccrIntVal))), getUserName, nDt, getNoOfShrVal, getPriceVal, ytm, enterBy, marking);

            if (msg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            } else {
                msg = saveGoiDates(maxSec, getPurchaseDate, getPrnIssueDate, getMatDate, getfInttPayDate, getsInttPayDate, getIssuePrVal, nDt, Double.valueOf(formatter.format(Double.valueOf(getAccrIntVal))));
                if (msg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                } else {
                    InvestmentSecurityAuthDetail invCall = getSecDetailToAuth(seqNo);
                    invCall.setAuth("Y");
                    invCall.setAuthBy(getUserName);
                    String msg1 = dao.updateSecAuthDetail(invCall);
                    if (msg1.equals("false")) {
                        throw new ApplicationException();
                    } else {
                        List<ParameterinfoReport> resultList = new ArrayList<ParameterinfoReport>();
                        resultList = getGoiPurchaseCode("GOIPURCHASE");
                        for (int i = 0; i < resultList.size(); i++) {
                            ParameterinfoReport entity = resultList.get(i);
                            codeGoi = entity.getCode().toString();
                        }

                        if (codeGoi.equalsIgnoreCase("1")) {
                            InvestmentCallHead entity = getInvestCallHeadByCode("11");
                            if (entity != null) {
                                cracno = getCrGlVal;

                                List<GlDescRange> gltableList = getGlRangeBySec(getSecTpDd);
                                if (!gltableList.isEmpty()) {
                                    for (GlDescRange glEntity : gltableList) {
                                        dracno = getOrgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                                    }

                                    trsno = ftsObj.getTrsNo();
                                    recno = ftsObj.getRecNo();

                                    msg = insertDataOnSave(dracno, maxSec, trsno, recno, Double.valueOf(formatter.format(Double.parseDouble(getBookValue))), 1, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                    if (msg.equalsIgnoreCase("false")) {
                                        throw new ApplicationException();
                                    } else {
                                        recno = ftsObj.getRecNo();

                                        msg = insertDataOnSave(cracno, maxSec, trsno, recno, Double.valueOf(formatter.format(Double.parseDouble(getBookValue))), 0, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                        if (msg.equalsIgnoreCase("false")) {
                                            throw new ApplicationException();
                                        } else {
                                            ParameterinfoReport entitySec = dao.getParameterinfoReportByName("PREMIUMBALANCE");
                                            if (entitySec != null) {
                                                if (entitySec.getCode() == 1) {
                                                    recno = ftsObj.getRecNo();
                                                    if (Double.valueOf(formatter.format(Double.parseDouble(getFaceValue))) < Double.valueOf(formatter.format(Double.parseDouble(getBookValue)))) {
                                                        prem_balance = 0d;
                                                        prem_balance = (Double.valueOf(formatter.format(Double.parseDouble(getBookValue))) - Double.valueOf(formatter.format(Double.parseDouble(getFaceValue))));
                                                        List<String> insertAcnoList = getInsertAcnos("12");
                                                        if (!insertAcnoList.isEmpty()) {
                                                            rsCrHead = insertAcnoList.get(0);
                                                            rsDrHead = insertAcnoList.get(1);
                                                        } else {
                                                            throw new ApplicationException();
                                                        }
                                                        msg = insertDataOnSave(rsDrHead, maxSec, trsno, recno, prem_balance, 1, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                        if (msg.equalsIgnoreCase("true")) {
                                                            recno = ftsObj.getRecNo();
                                                            msg = insertDataOnSave(rsCrHead, maxSec, trsno, recno, prem_balance, 0, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                            if (!msg.equalsIgnoreCase("true")) {
                                                                throw new ApplicationException();
                                                            }
                                                        } else {
                                                            throw new ApplicationException();
                                                        }
                                                    } else if (Double.valueOf(formatter.format(Double.parseDouble(getFaceValue))) > Double.valueOf(formatter.format(Double.parseDouble(getBookValue)))) {
                                                        prem_balance = 0d;
                                                        prem_balance = Math.abs(Double.valueOf(formatter.format(Double.parseDouble(getBookValue))) - Double.valueOf(formatter.format(Double.parseDouble(getFaceValue))));
                                                        List<String> insertAcnoList = getInsertAcnos("13");
                                                        if (!insertAcnoList.isEmpty()) {
                                                            rsCrHead = insertAcnoList.get(0);
                                                            rsDrHead = insertAcnoList.get(1);
                                                        } else {
                                                            throw new ApplicationException();
                                                        }
                                                        msg = insertDataOnSave(rsDrHead, maxSec, trsno, recno, prem_balance, 1, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                        if (msg.equalsIgnoreCase("true")) {
                                                            recno = ftsObj.getRecNo();
                                                            msg = insertDataOnSave(rsCrHead, maxSec, trsno, recno, prem_balance, 0, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                            if (!msg.equalsIgnoreCase("true")) {
                                                                throw new ApplicationException();
                                                            }
                                                        } else {
                                                            throw new ApplicationException();
                                                        }
                                                    }
                                                }
                                            }

                                            if (!(getAccrIntVal.equalsIgnoreCase("0.0")) || (getAccrIntVal.equalsIgnoreCase("0"))) {
                                                List<String> insertAcnoList = getInsertAcnos("11");
                                                if (!insertAcnoList.isEmpty()) {
                                                    rsCrHead = insertAcnoList.get(0);
                                                    rsDrHead = insertAcnoList.get(1);
                                                } else {
                                                    throw new ApplicationException();
                                                }
                                                recno = ftsObj.getRecNo();
                                                msg = insertDataOnGoiRecon(rsDrHead, maxSec, trsno, recno, Double.valueOf(formatter.format(Double.parseDouble(getAccrIntVal))), 0, 8, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                if (msg.equalsIgnoreCase("true")) {
                                                    recno = ftsObj.getRecNo();
                                                    msg = insertDataOnSave(rsDrHead, maxSec, trsno, recno, Double.valueOf(formatter.format(Double.parseDouble(getAccrIntVal))), 1, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                    if (msg.equalsIgnoreCase("true")) {
                                                        recno = ftsObj.getRecNo();
                                                        msg = insertDataOnSave(rsCrHead, maxSec, trsno, recno, Double.valueOf(formatter.format(Double.parseDouble(getAccrIntVal))), 0, 2, 3d, dtl, "Y", getOrgnBrCode, getOrgnBrCode, getCrBranchDd, getUserName);
                                                        if (!msg.equalsIgnoreCase("true")) {
                                                            throw new ApplicationException();
                                                        }
                                                    } else {
                                                        throw new ApplicationException();
                                                    }
                                                } else {
                                                    throw new ApplicationException();
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    result = "Please Set The GL Head Of Seller";
                                    ut.rollback();
                                }

                                result = "true" + trsno;
                                ut.commit();
                            } else {
                                result = "Gl Head For Accrued Intt. Does Not Exists ,Posting Aborted";
                                ut.rollback();
                            }
                        } else {
                            result = "true Parameter GOIPURCHASE Not Defined";
                            ut.commit();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String saveInvestmentData(int maxSec, String getSecTpDd, String getIssueFrDd, String getCodeNoVal, double getRoiVal, Date getTransactionDate, Date pDt, Date getMatDate, String getSellerNmVal, String getConAcNoVal, String getConSgAccountDd, String getBrokerAcVal, double getFaceValue, double getBookValue, double getBrokerageAmtVal, double getAccrIntVal, String getUserName, Date nDt, int getNoOfShrVal, double getPriceVal, double ytm, String enterBy, String marking) {
        String msg = "";
        try {
            InvestmentMasterPK investMastPk = new InvestmentMasterPK();

            investMastPk.setControlno(maxSec);
            investMastPk.setSectype(getSecTpDd);

            InvestmentMaster investMast = new InvestmentMaster();

            investMast.setInvestmentMasterPK(investMastPk);
            investMast.setSecdesc(getIssueFrDd);
            investMast.setInvstcode(getCodeNoVal);
            investMast.setRoi(getRoiVal);
            investMast.setTransdt(getTransactionDate);
            investMast.setSettledt(pDt);
            investMast.setDt(nDt);
            investMast.setSellername(getSellerNmVal);
            investMast.setBuyeracno(getConAcNoVal);
            investMast.setBrokeracno(getBrokerAcVal);
            investMast.setFacevalue(getFaceValue);
            investMast.setBrokerage(getBrokerageAmtVal);
            investMast.setAccdint(getAccrIntVal);
            investMast.setEnterby(enterBy);
            investMast.setNoofshares(getNoOfShrVal);
            investMast.setBookvalue(getBookValue);
            investMast.setAuth("Y");
            investMast.setAuthby(getUserName);
            investMast.setMatdt(getMatDate);
            investMast.setBrokername(getConSgAccountDd);
            investMast.setPricegsec(getPriceVal);
            investMast.setTransno("");
            investMast.setPdDay(0);
            investMast.setPdMon(0);
            investMast.setPdYear(0);
            investMast.setIntopt("");
            investMast.setLastupdateby("");
            investMast.setLastupdatedt(ymd.parse(ymd.format(new Date())));
            //investMast.setClosedt(ymd.parse(ymd.format(new Date())));
            investMast.setVouchno("");
            investMast.setClosepricegsec(0.0);
            investMast.setContpersonnm("");
            investMast.setContpersphone("");
            investMast.setYtm(ytm);
            investMast.setMarking(marking);

            msg = saveInvestmentMaster(investMast);
            msg = "true";

        } catch (Exception ex) {
            ex.printStackTrace();
            msg = "false";
        }
        return msg;
    }

    public String saveGoiDates(int maxSec, String getPurchaseDate, String getPrnIssueDate, Date getMatDate, String getfInttPayDate, String getsInttPayDate, String getIssuePrVal, Date accDt, double intAccr) {
        String msg = "";
        try {
            if (getfInttPayDate.equalsIgnoreCase("") || getfInttPayDate == null) {
                getfInttPayDate = "01/01/1900";
            }
            if (getsInttPayDate.equalsIgnoreCase("") || getsInttPayDate == null) {
                getsInttPayDate = "01/01/1900";
            }
            InvestmentGoidates investGoiDates = new InvestmentGoidates();

            investGoiDates.setCtrlno(maxSec);
            investGoiDates.setPurchasedt(dmy.parse(getPurchaseDate));
            investGoiDates.setPrintedIssuedt(dmy.parse(getPrnIssueDate));
            investGoiDates.setMatdt(getMatDate);
            //investGoiDates.setCloseddt(ymd.parse("19000101"));
            investGoiDates.setIntpdt1(dmy.parse(getfInttPayDate));
            investGoiDates.setIntpdt2(dmy.parse(getsInttPayDate));
            investGoiDates.setLastintpaiddt(dmy.parse(getPurchaseDate));
            investGoiDates.setFlag('P');
            investGoiDates.setCode('1');
            investGoiDates.setAmtinttrec(0);
            investGoiDates.setRecflag('Y');
            investGoiDates.setIssueUnits(Double.valueOf(getIssuePrVal));
            investGoiDates.setStatus("Active");
            investGoiDates.setAmtIntAccr(intAccr);
            investGoiDates.setTotAmtIntAccr(intAccr);
            investGoiDates.setTotIntRec(0.0);
            investGoiDates.setLastIntPaidDtAccr(accDt);

            msg = saveInvestmentGoidates(investGoiDates);
            msg = "true";
        } catch (Exception ex) {
            msg = "false";
        }
        return msg;
    }

    public List<ParameterinfoReport> getGoiPurchaseCode(String reportName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getGoiPurchaseCode(reportName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String insertDataOnSave(String acno, Integer ctrNo, Float trsno, Float recno, double amount, Integer ty, Integer trantype, double payby, String details, String auth, String orgncode, String destcode, String brAlpha, String enterby) {
        String msg = "", advBrn = "";
        try {
            GlRecon glReconEntity = new GlRecon();
            GlReconPK glReconPK = new GlReconPK(acno, ymd.parse(ymd.format(dt)), recno);
            glReconEntity.setGlReconPK(glReconPK);

            glReconEntity.setBalance(0d);
            if (ty == 0) {
                glReconEntity.setDrAmt(0);
                glReconEntity.setCrAmt(amount);
            } else if (ty == 1) {
                glReconEntity.setDrAmt(amount);
                glReconEntity.setCrAmt(0);
            }

            glReconEntity.setTy(ty);
            glReconEntity.setTranType(trantype);
            glReconEntity.setTrsno(trsno.intValue());
            glReconEntity.setInstno("0");
            glReconEntity.setPayby(payby);
            glReconEntity.setIy(0);
            glReconEntity.setTranDesc(0);
            glReconEntity.setDetails(details);
            glReconEntity.setTranid(new BigDecimal(recno.toString()).toBigInteger());
            glReconEntity.setOrgBrnid(orgncode);
            glReconEntity.setDestBrnid(destcode);
            glReconEntity.setTrantime(dt);
            glReconEntity.setAuth(auth);
            glReconEntity.setEnterBy(enterby);
            glReconEntity.setAuthby("System");
            glReconEntity.setValueDt(ymd.parse(ymd.format(dt)));

            List<BranchMaster> resultList = new ArrayList<BranchMaster>();
            resultList = getbrnCode(brAlpha);
            for (int i = 0; i < resultList.size(); i++) {
                advBrn = String.format("%02d", Integer.parseInt(resultList.get(i).getBrnCode().toString()));
            }

            glReconEntity.setAdviceBrnCode(advBrn);

            msg = saveGlRecon(glReconEntity);
            if (msg.equalsIgnoreCase("true")) {
                GoiRecon goiReconEntity = new GoiRecon();
                GoiReconPK goiReconPK = new GoiReconPK(ctrNo.longValue(), acno, ymd.parse(ymd.format(dt)), recno);
                goiReconEntity.setGoiReconPK(goiReconPK);
                goiReconEntity.setBalance(0d);
                if (ty == 0) {
                    goiReconEntity.setDramt(0);
                    goiReconEntity.setCramt(amount);
                } else if (ty == 1) {
                    goiReconEntity.setDramt(amount);
                    goiReconEntity.setCramt(0);
                }
                goiReconEntity.setTy(ty);
                goiReconEntity.setTrantype(trantype);
                goiReconEntity.setTrsno(trsno.intValue());
                goiReconEntity.setInstno("0");
                goiReconEntity.setPayby(payby);
                goiReconEntity.setIy(0);
                goiReconEntity.setTrandesc(0);
                goiReconEntity.setDetails(details);
                goiReconEntity.setTranId(new BigDecimal(recno.toString()).toBigInteger());
                goiReconEntity.setOrgBrnid(orgncode);
                goiReconEntity.setDestBrnid(destcode);
                goiReconEntity.setTrantime(dt);
                goiReconEntity.setAuth(auth);
                goiReconEntity.setEnterby(enterby);
                goiReconEntity.setAuthby("System");

                msg = saveGoiRecon(goiReconEntity);
                msg = "true";
            }
        } catch (Exception ex) {
            return msg = "false";
        }
        return msg;
    }

    public List<BranchMaster> getbrnCode(String alphaCode) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getbrnCode(alphaCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getInsertAcnos(String value) {
        List<String> acnoList = new ArrayList<String>();
        String rsCrHead = "", rsDrHead = "";
        try {
            InvestmentCallHead entity = getInvestCallHeadByCode(value);
            if (entity != null) {
                rsCrHead = entity.getCrGlhead();
                rsDrHead = entity.getDrGlhead();
                acnoList.add(rsCrHead);
                acnoList.add(rsDrHead);
            }
        } catch (Exception ex) {
            return acnoList = new ArrayList<String>();
        }
        return acnoList;
    }

    public List<InvestmentSecurityMaster> getAllSecDtlBySecName(String securityName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAllSecDtlBySecName(securityName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveGlDataForAmort(String CrGlHead, String DrGlHead, String orgncode, double amount, String enterby, BigInteger ctrlNo, String monAmort, BigInteger yrAmort, String flag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", msg = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            if (!(flag.equalsIgnoreCase("A") && ctrlNo.compareTo(new BigInteger("999999")) == 0)) {
                trsno = ftsObj.getTrsNo();
                recno = ftsObj.getRecNo();
                msg = insertGLDataForAmort(orgncode, DrGlHead, 1, amount, 2, 1, enterby, trsno, recno, 1, "Amortrised Amt Posted For Month" + monAmort + " of the Year " + yrAmort, "Y");
                if (msg.equalsIgnoreCase("True")) {
                    msg = ftsObj.updateBalance("PO", DrGlHead, 0, amount, "Y", "Y");
                    if (msg.equalsIgnoreCase("True")) {
                        recno = ftsObj.getRecNo();
                        msg = insertGLDataForAmort(orgncode, CrGlHead, 0, amount, 2, 1, enterby, trsno, recno, 1, "Amortrised Amt Posted For Month" + monAmort + " of the Year " + yrAmort, "Y");
                        if (msg.equalsIgnoreCase("True")) {
                            msg = ftsObj.updateBalance("PO", CrGlHead, amount, 0, "Y", "Y");
                            if (!msg.equalsIgnoreCase("true")) {
                                result = "Data Not Updated In GL Table " + CrGlHead;
                                ut.rollback();
                            }
                        } else {
                            result = "Data Not Saved In GL Table " + CrGlHead;
                            ut.rollback();
                        }
                    } else {
                        result = "Data Not Updated In GL Table " + DrGlHead;
                        ut.rollback();
                    }
                } else {
                    result = "Data Not Saved In GL Table " + DrGlHead;
                    ut.rollback();
                }
            }

            if ((flag.equalsIgnoreCase("A") && ctrlNo.compareTo(new BigInteger("999999")) == 0) || (flag.equalsIgnoreCase("I"))) {
                msg = saveAmortHistory(ctrlNo, monAmort, yrAmort, enterby, new Date());
                if (!msg.equalsIgnoreCase("true")) {
                    result = "Data Not Saved In Amort Post History";
                    ut.rollback();
                } else {
                    result = "true" + trsno;
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String insertGLDataForAmort(String orgncode, String acno, Integer ty, double amount, Integer trantype, double payby, String enterby, Float trsno, Float recno, Integer iy, String details, String auth) {
        String msg = "", advBrn = "";
        try {
            GlRecon glReconEntity = new GlRecon();
            GlReconPK glReconPK = new GlReconPK(acno, ymd.parse(ymd.format(dt)), recno);
            glReconEntity.setGlReconPK(glReconPK);

            glReconEntity.setBalance(0d);
            if (ty == 0) {
                glReconEntity.setDrAmt(0);
                glReconEntity.setCrAmt(amount);
            } else if (ty == 1) {
                glReconEntity.setDrAmt(amount);
                glReconEntity.setCrAmt(0);
            }

            glReconEntity.setTy(ty);
            glReconEntity.setTranType(trantype);
            glReconEntity.setTrsno(trsno.intValue());
            glReconEntity.setInstno("0");
            glReconEntity.setPayby(payby);
            glReconEntity.setIy(0);
            glReconEntity.setTranDesc(0);
            glReconEntity.setDetails(details);
            glReconEntity.setTranid(new BigDecimal(recno.toString()).toBigInteger());
            glReconEntity.setOrgBrnid(orgncode);
            glReconEntity.setDestBrnid(orgncode);
            glReconEntity.setTrantime(dt);
            glReconEntity.setAuth(auth);
            glReconEntity.setEnterBy(enterby);
            glReconEntity.setAuthby("System");
            glReconEntity.setValueDt(dt);

            glReconEntity.setAdviceBrnCode(advBrn);

            msg = saveGlRecon(glReconEntity);
            msg = "true";

        } catch (Exception ex) {
            return msg = "false";
        }
        return msg;
    }

    public String saveAmortHistory(BigInteger ctrlNo, String monAmort, BigInteger yrAmort, String postBy, Date postDt) throws ApplicationException {
        String msg = "";
        try {
            InvestmentAmortPostHistory investAmortHis = new InvestmentAmortPostHistory();

            investAmortHis.setCtrlno(ctrlNo);
            investAmortHis.setMonthAmort(monAmort);
            investAmortHis.setYearAmort(yrAmort);
            investAmortHis.setPostedby(postBy);
            investAmortHis.setPostingdate(postDt);

            msg = saveInvestAmortPostHis(investAmortHis);
            msg = "true";
        } catch (Exception ex) {
            msg = "false";
        }
        return msg;
    }

    public String saveInvestAmortPostHis(InvestmentAmortPostHistory entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveInvestAmortPostHis(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateAmortDtl(String lastupdateby, Date lastupdatedt, BigInteger slno, Long ctrlno, Date years) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = (dao.updateInvestmentAmortDtl(lastupdateby, lastupdatedt, slno, ctrlno, years));
            ut.commit();
            return result;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortizationDetails> getAmortCtrlNo(Date yearAmort, BigInteger ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAmortCtrlNo(yearAmort, ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Long> getCtrlNo() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getCtrlNo());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortPostHistory> getPostStatusCtrl(BigInteger yearAmort, String monthAmort, BigInteger ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getPostStatusCtrl(yearAmort, monthAmort, ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentAmortPostHistory> getPostStatusAll(BigInteger yearAmort, String monthAmort) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getPostStatusAll(yearAmort, monthAmort));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public BigDecimal getTotalAmortAmt(Date years, BigInteger ctrNo, String stat) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (BigDecimal) (dao.getTotalAmortAmt(years, ctrNo, stat));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Object[] getRepData(BigInteger ctrNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            Object[] obj = (Object[]) (dao.getRepData(ctrNo));
            return obj;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateFdrDates(double amount, Integer ctrlno) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = (dao.updateInvestmentFdrDatesByCtrlNo(amount, ctrlno));
            ut.commit();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return result;
    }

    public Object[] getDetailDataOfCtrlNo(Integer ctrNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            Object[] obj = (Object[]) (dao.getDetailDataOfCtrlNo(ctrNo));
            return obj;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Integer> getCtrlNoFromInvestmentFdrDetails() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getCtrlNoFromInvestmentFdrDetails());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentFdrDates getInvestmentFdrDatesByCtrlNo(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentFdrDatesByCtrlNo(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentFdrDetails getInvestmentFdrDetailsByCtrlNo(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getInvestmentFdrDetailsByCtrlNo(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentShare getInvestmentShare(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getControlNoDetails(ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String trans(String bookAcno, double bookAmt, String intAcno, double intAmt, String recAcno, double recAmt, String faceAcno, double faceAmt, String enterBy, String brnCode, String flag, int ctrlNo, String accrAcno, double accrAmt, String authBy) throws ApplicationException {
        String trans = "false";
        String msg = "";
        try {
            float recno = ftsObj.getRecNo();
            float trsno = ftsObj.getTrsNo();

            if (accrAmt > 0) {
                if (accrAcno.length() != 12) {
                    trans = "false";
                    return trans;
                }
                String acNat = ftsObj.getAccountNature(accrAcno);
                msg = insertGLDataForFDRClose(accrAcno, 0d, accrAmt, 0, 2, trsno, recno, "", 3d, 0, 2, "FDR Accrued Value For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }

                msg = ftsObj.updateBalance(acNat, accrAcno, accrAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
            }

            if (recAmt > 0) {
                if (recAcno.length() != 12) {
                    trans = "false";
                    return trans;
                }
                String acNat = ftsObj.getAccountNature(recAcno);
                recno = ftsObj.getRecNo();
                msg = insertGLDataForFDRClose(recAcno, 0d, recAmt, 0, 2, trsno, recno, "", 3d, 0, 2, "FDR Interest For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
                msg = ftsObj.updateBalance(acNat, accrAcno, recAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
            }

            if (intAmt > 0) {
                if (intAcno.length() != 12) {
                    trans = "false";
                    return trans;
                }
                recno = ftsObj.getRecNo();
                String acNat = ftsObj.getAccountNature(intAcno);
                if (flag.equalsIgnoreCase("true")) {
                    msg = insertGLDataForFDRClose(intAcno, 0d, intAmt, 1, 2, trsno, recno, "", 3d, 0, 2, "FDR Interesst For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                    if (msg.equalsIgnoreCase("true")) {
                        InvestmentFdrIntHis investFdrIntHis = new InvestmentFdrIntHis();
                        InvestmentFdrIntHisPK investFdrIntHisPK = new InvestmentFdrIntHisPK(ctrlNo, dt);
                        investFdrIntHis.setInvestmentFdrIntHisPK(investFdrIntHisPK);

                        investFdrIntHis.setGlhead(intAcno);
                        investFdrIntHis.setDramt(intAmt);
                        investFdrIntHis.setCramt(0d);
                        investFdrIntHis.setEnterby(enterBy);
                        investFdrIntHis.setTrantime(dt);

                        msg = saveFDRHis(investFdrIntHis);
                        if (!msg.equalsIgnoreCase("true")) {
                            trans = "false";
                            return trans;
                        }
                        msg = ftsObj.updateBalance(acNat, intAcno, 0, intAmt, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            trans = "false";
                            return trans;
                        }
                    } else {
                        trans = "false";
                        return trans;
                    }
                } else {
                    msg = insertGLDataForFDRClose(intAcno, 0d, intAmt, 0, 2, trsno, recno, "", 3d, 0, 2, "FDR Interesst For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                    if (msg.equalsIgnoreCase("true")) {
                        InvestmentFdrIntHis investFdrIntHis = new InvestmentFdrIntHis();
                        InvestmentFdrIntHisPK investFdrIntHisPK = new InvestmentFdrIntHisPK(ctrlNo, dt);
                        investFdrIntHis.setInvestmentFdrIntHisPK(investFdrIntHisPK);

                        investFdrIntHis.setGlhead(intAcno);
                        investFdrIntHis.setDramt(0d);
                        investFdrIntHis.setCramt(intAmt);
                        investFdrIntHis.setEnterby(enterBy);
                        investFdrIntHis.setTrantime(dt);

                        msg = saveFDRHis(investFdrIntHis);
                        if (!msg.equalsIgnoreCase("true")) {
                            trans = "false";
                            return trans;
                        }

                        msg = ftsObj.updateBalance(acNat, intAcno, intAmt, 0, "Y", "Y");
                        if (!msg.equalsIgnoreCase("true")) {
                            trans = "false";
                            return trans;
                        }
                    } else {
                        trans = "false";
                        return trans;
                    }
                }
            }

            if (bookAmt > 0) {
                if (bookAcno.length() != 12) {
                    trans = "false";
                    return trans;
                }
                String acNat = ftsObj.getAccountNature(bookAcno);
                recno = ftsObj.getRecNo();
                msg = insertGLDataForFDRClose(bookAcno, 0d, bookAmt, 1, 2, trsno, recno, "", 3d, 0, 2, "FDR Close Value For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
                msg = ftsObj.updateBalance(acNat, bookAcno, 0, bookAmt, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
            }

            if (faceAmt > 0) {
                if (faceAcno.length() != 12) {
                    trans = "false";
                    return trans;
                }
                String acNat = ftsObj.getAccountNature(faceAcno);
                recno = ftsObj.getRecNo();
                msg = insertGLDataForFDRClose(faceAcno, 0d, faceAmt, 0, 2, trsno, recno, "", 3d, 0, 2, "FDR Close Value For " + ctrlNo, 0, brnCode, "Y", enterBy, authBy);
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
                msg = ftsObj.updateBalance(acNat, faceAcno, faceAmt, 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    trans = "false";
                    return trans;
                }
            }

            trans = "true";
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return trans;
    }

    public String insertGLDataForFDRClose(String acno, double balance, double amount, Integer ty, Integer trantype, Float trsno, Float recno, String instNo, double payby, Integer iy, int tranDesc, String details, Integer tranid, String orgncode, String auth, String enterby, String AuthBy) {
        String msg = "true";
        try {
            GlRecon glReconEntity = new GlRecon();
            GlReconPK glReconPK = new GlReconPK(acno, ymd.parse(ymd.format(dt)), recno);
            glReconEntity.setGlReconPK(glReconPK);
            glReconEntity.setBalance(balance);
            if (ty == 0) {
                glReconEntity.setDrAmt(0);
                glReconEntity.setCrAmt(amount);
            } else if (ty == 1) {
                glReconEntity.setDrAmt(amount);
                glReconEntity.setCrAmt(0);
            }

            glReconEntity.setTy(ty);
            glReconEntity.setTranType(trantype);
            glReconEntity.setTrsno(trsno.intValue());
            glReconEntity.setInstno(instNo);
            glReconEntity.setPayby(payby);
            glReconEntity.setIy(iy);
            glReconEntity.setTranDesc(tranDesc);
            glReconEntity.setDetails(details);
            glReconEntity.setTranid(new BigDecimal(recno.toString()).toBigInteger());
            glReconEntity.setOrgBrnid(orgncode);
            glReconEntity.setDestBrnid(orgncode);
            glReconEntity.setTrantime(dt);
            glReconEntity.setAuth(auth);
            glReconEntity.setEnterBy(enterby);
            glReconEntity.setAuthby(AuthBy);
            glReconEntity.setValueDt(ymd.parse(ymd.format(dt)));
            glReconEntity.setAdviceBrnCode("");
            msg = saveGlRecon(glReconEntity);

        } catch (Exception ex) {
            return msg = "false";
        }
        return msg;
    }

    public String saveFDRHis(InvestmentFdrIntHis entity) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.saveFDRHis(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String fdrCloseProcess(Integer ctrlNo, String renCloseFlg, String closeFlg, String purchaseDate,
            Integer days, Integer months, Integer years, String matDate, float roi, String intOpt, double faceValue, double bookvalue,
            String intCrGlHead, double intCrGlHeadVal, String intRecCrGlHead, double intRecCrGlHeadVal, String faceValCrGlHead, double faceValCrGlHeadVal,
            String bookValDrGlHead, double bookValDrGlHeadVal, String flag, String enterBy, String brnCode, String sellerName, String vchDt, String intAccGlHead, double intAccGlHeadVal, String authBy) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_fdr_close_renew_auth set auth ='Y', "
                    + " auth_by='" + enterBy + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            if (renCloseFlg.equalsIgnoreCase("R")) {
                int maxCtrlNo = getMaxCtrlNoFromInvestmentFdrDetails();
                msg = trans(bookValDrGlHead, bookValDrGlHeadVal, intCrGlHead, intCrGlHeadVal, intRecCrGlHead, intRecCrGlHeadVal,
                        faceValCrGlHead, faceValCrGlHeadVal, enterBy, brnCode, flag, ctrlNo, intAccGlHead, intAccGlHeadVal, authBy);
                if (msg.equalsIgnoreCase("true")) {
                    msg = fdrRenewalSaveProcess(maxCtrlNo, ymd.parse(purchaseDate.substring(6, 10) + purchaseDate.substring(3, 5) + purchaseDate.substring(0, 2)),
                            bookValDrGlHead, faceValue, brnCode, sellerName, enterBy);
                    if (msg.equalsIgnoreCase("true")) {
                        InvestmentFdrDetails investFdrEntity = getInvestmentFdrDetailsByCtrlNo(ctrlNo);
                        if (investFdrEntity != null) {
                            InvestmentFdrDates invFdrDates = new InvestmentFdrDates();

                            invFdrDates.setCtrlNo(maxCtrlNo);
                            invFdrDates.setPurchaseDt(ymd.parse(purchaseDate.substring(6, 10) + purchaseDate.substring(3, 5) + purchaseDate.substring(0, 2)));
                            invFdrDates.setMatDt(ymd.parse(matDate.substring(6, 10) + matDate.substring(3, 5) + matDate.substring(0, 2)));
                            invFdrDates.setFlag("P");
                            invFdrDates.setAmtIntTrec(0.0);
                            invFdrDates.setTotAmtIntRec(0.0);
                            invFdrDates.setLastIntPaidDt(ymd.parse(vchDt.substring(6, 10) + vchDt.substring(3, 5) + vchDt.substring(0, 2)));
                            invFdrDates.setDays(BigInteger.valueOf(days));
                            invFdrDates.setMonths(BigInteger.valueOf(months));
                            invFdrDates.setYears(BigInteger.valueOf(years));
                            invFdrDates.setIntOpt(intOpt);
                         //   invFdrDates.setIntPdt(dmy.parse(dmy.format(dt)));// as per bug 37932
                            invFdrDates.setIntPdt(ymd.parse(purchaseDate.substring(6, 10) + purchaseDate.substring(3, 5) + purchaseDate.substring(0, 2))); 
                           
                            msg = dao.saveInvestmentFdrDates(invFdrDates);
                            if (msg.equalsIgnoreCase("true")) {
                                InvestmentFdrDetails invFdrDetails = new InvestmentFdrDetails();

                                invFdrDetails.setCtrlNo(maxCtrlNo);
                                invFdrDetails.setSecType("TERM DEPOSIT");
                                invFdrDetails.setSecDesc(investFdrEntity.getSecDesc());
                                invFdrDetails.setRoi(Double.valueOf(formatter.format(roi)));
                                invFdrDetails.setSellerName(sellerName);
                                invFdrDetails.setFaceValue(faceValue);
                                invFdrDetails.setBookValue(bookvalue);
                                invFdrDetails.setEnterBy(enterBy);
                                invFdrDetails.setAuth("Y");
                                invFdrDetails.setStatus("ACTIVE");
                                invFdrDetails.setTranTime(dt);
                                invFdrDetails.setBranch(investFdrEntity.getBranch());
                                invFdrDetails.setFdrNo(investFdrEntity.getFdrNo());
                                invFdrDetails.setRenewStatus("R");
                                invFdrDetails.setOldCtrlNo(ctrlNo);
                                invFdrDetails.setIntOpt(intOpt);
                                invFdrDetails.setAuthBy(authBy);

                                msg = dao.saveInvestmentFdrDetails(invFdrDetails);

                                if (msg.equalsIgnoreCase("true")) {
                                    msg = updateFDRDetails(ctrlNo, "CLOSED");
                                    if (msg.equalsIgnoreCase("true")) {
                                        msg = updateFDRDatesBySno(ctrlNo, dt);
                                        if (msg.equalsIgnoreCase("true")) {
                                            msg = "true" + maxCtrlNo;
                                            ut.commit();
                                        } else {
                                            msg = "Error in Renewal";
                                            ut.rollback();
                                        }
                                    } else {
                                        msg = "Error in Renewal";
                                        ut.rollback();
                                    }
                                } else {
                                    msg = "Error";
                                    ut.rollback();
                                }
                            } else {
                                msg = "Error";
                                ut.rollback();
                            }
                        } else {
                            msg = "Error in Renewal";
                            ut.rollback();
                        }

                    } else {
                        msg = "Error in Renewal";
                        ut.rollback();
                    }
                } else {
                    msg = "Error in Renewal";
                    ut.rollback();
                }
            }

            if (renCloseFlg.equalsIgnoreCase("C")) {
                if (closeFlg.equalsIgnoreCase("A")) {
                    msg = trans(bookValDrGlHead, bookValDrGlHeadVal, intCrGlHead, intCrGlHeadVal, intRecCrGlHead, intRecCrGlHeadVal,
                            faceValCrGlHead, faceValCrGlHeadVal, enterBy, brnCode, flag, ctrlNo, intAccGlHead, intAccGlHeadVal, authBy);
                    if (msg.equalsIgnoreCase("true")) {
                        msg = updateFDRDetails(ctrlNo, "CLOSED");
                        if (msg.equalsIgnoreCase("true")) {
                            msg = updateFDRDatesBySno(ctrlNo, dt);
                            if (msg.equalsIgnoreCase("true")) {
                                msg = "true";
                                ut.commit();
                            } else {
                                msg = "Error in Closing";
                                ut.rollback();
                            }
                        } else {
                            msg = "Error in Closing";
                            ut.rollback();
                        }
                    } else {
                        msg = "Error in Closing";
                        ut.rollback();
                    }
                } else {
                    msg = updateFDRDetails(ctrlNo, "CLOSED");
                    if (msg.equalsIgnoreCase("true")) {
                        msg = updateFDRDatesBySno(ctrlNo, dt);
                        if (msg.equalsIgnoreCase("true")) {
                            msg = "true";
                            ut.commit();
                        } else {
                            msg = "Error in Closing";
                            ut.rollback();
                        }
                    } else {
                        msg = "Error in Closing";
                        ut.rollback();
                    }
                }
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return msg;
    }

    public String updateFDRDetails(Integer ctrlNo, String status) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            result = dao.updateFDRDetails(ctrlNo, status);
            if (!result.equalsIgnoreCase("true")) {
                result = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String updateFDRDatesBySno(Integer ctrlNo, Date dt) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            result = dao.updateFDRDatesBySno(ctrlNo, dt);
            if (!result.equalsIgnoreCase("true")) {
                result = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public List<String> getAllSellerName(String secTp) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getAllSellerName(secTp));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Integer getMaxCtrlNoByInvestmentMasterSec() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (Integer) (dao.getMaxCtrlNoByInvestmentMasterSec());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<GlDescRange> getGlRangeBySec(String glName) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getGlRangeBySec(glName));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentInterestAmount(InvestmentFrdDatesAndDetailsHistory historyObj, InvestmentFdrDates obj, String drAcno, String drAmt, String crAcno, String crAmt, String enterBy, String orgncode, BigDecimal curAmt, BigDecimal prevAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        String acnofdrreconofctlno ="";
        try {
            ut.begin();
           List acnoList = entityManager.createNativeQuery("select distinct ACNO from fdr_recon where ctrl_no = '"+(int) historyObj.getCtrlNo()+"' and dt = (select min(dt) from fdr_recon where ctrl_no = '"+(int) historyObj.getCtrlNo()+"' and  TRANTYPE = 8) ").getResultList();
            if(!acnoList.isEmpty()){
                Vector vector = (Vector)acnoList.get(0);
               acnofdrreconofctlno = vector.get(0).toString();
            }
            
            result = dao.saveInvestmentFdrDatesAndDetailsHistory(historyObj);
            if (result.equalsIgnoreCase("true")) {
                result = dao.updateInvestmentFdrDates(obj);
                if (result.equalsIgnoreCase("true")) {
                    trsno = ftsObj.getTrsNo();
                    if (curAmt.compareTo(prevAmt) == 1) {
                        BigDecimal totAmt = curAmt.subtract(prevAmt);
                        recno = ftsObj.getRecNo();
                        FdrRecon fdrRecon = new FdrRecon();
                        FdrReconPK fdrReconPK = new FdrReconPK();

                        fdrReconPK.setAcno(acnofdrreconofctlno);
                        fdrReconPK.setDt(ymd.parse(ymd.format(dt)));
                        fdrReconPK.setRecno(recno);

                        fdrRecon.setFdrReconPK(fdrReconPK);
                        fdrRecon.setCtrlNo((int) historyObj.getCtrlNo());
                        fdrRecon.setBalance(0.0);
                        fdrRecon.setDrAmt(totAmt.doubleValue());

                        fdrRecon.setCrAmt(0.0);
                        fdrRecon.setTy(1);
                        fdrRecon.setTrantype(8);
                        fdrRecon.setTrsno(trsno.intValue());

                        fdrRecon.setPayBy(3);
                        fdrRecon.setIy(0);
                        fdrRecon.setTranDesc(3);
                        fdrRecon.setDetails("Interest Updation of FDR");

                        fdrRecon.setOrgBrnid(orgncode);
                        fdrRecon.setDestBrnid(drAcno.substring(0, 2));
                        fdrRecon.setTranTime(dt);
                        fdrRecon.setAuth("Y");
                        fdrRecon.setEnterBy(enterBy);
                        fdrRecon.setAuthBy(enterBy);

                        result = dao.saveFdrRecon(fdrRecon);
                    } else {
                        BigDecimal totAmt = prevAmt.subtract(curAmt);
                        recno = ftsObj.getRecNo();
                        FdrRecon fdrRecon = new FdrRecon();
                        FdrReconPK fdrReconPK = new FdrReconPK();

                        fdrReconPK.setAcno(acnofdrreconofctlno);
                        fdrReconPK.setDt(ymd.parse(ymd.format(dt)));
                        fdrReconPK.setRecno(recno);

                        fdrRecon.setFdrReconPK(fdrReconPK);
                        fdrRecon.setCtrlNo((int) historyObj.getCtrlNo());
                        fdrRecon.setBalance(0.0);
                        fdrRecon.setDrAmt(0.0);

                        fdrRecon.setCrAmt(totAmt.doubleValue());
                        fdrRecon.setTy(0);
                        fdrRecon.setTrantype(8);
                        fdrRecon.setTrsno(trsno.intValue());

                        fdrRecon.setPayBy(3);
                        fdrRecon.setIy(0);
                        fdrRecon.setTranDesc(3);
                        fdrRecon.setDetails("Interest Updation of FDR");

                        fdrRecon.setOrgBrnid(orgncode);
                        fdrRecon.setDestBrnid(drAcno.substring(0, 2));
                        fdrRecon.setTranTime(dt);
                        fdrRecon.setAuth("Y");
                        fdrRecon.setEnterBy(enterBy);
                        fdrRecon.setAuthBy(enterBy);

                        result = dao.saveFdrRecon(fdrRecon);
                    }

                    if (result.equalsIgnoreCase("true")) {
                        recno = ftsObj.getRecNo();
                        String glMsg = insertDataIntoGlRecon(crAcno, trsno, recno, Double.parseDouble(crAmt), 0, 2, 3, "Rec Int Rev. OF FDRs", "Y", orgncode, orgncode, enterBy, 0, 0, "System");
                        if (glMsg.equals("true")) {
                            recno = ftsObj.getRecNo();
                            glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, Double.parseDouble(drAmt), 1, 2, 3, "Rec Int Rev. OF FDRs", "Y", orgncode, orgncode, enterBy, 0, 0, "System");
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String postIntRecSec(List<GoiIntReportPojo> reportList, Date tillDt, String crAcno, String crAmt, String drAcno, String drAmt, String userName, String brCode, String remark, String accrAcno, String accrAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        try {
            ut.begin();
            InvestmentCallHead entity = dao.getInvestCallHeadByCode("14");
            if (entity == null) {
                throw new ApplicationException("Data does not exist in Investment Call Head !");
            } else {
                if (entity.getDrGlhead() == null || entity.getDrGlhead().equals("") || entity.getIntGlhead() == null || entity.getIntGlhead().equals("")) {
                    throw new ApplicationException("GL Head does not present in Investment Call Head !");
                }
            }

            trsno = ftsObj.getTrsNo();
            if (reportList.isEmpty()) {
                throw new ApplicationException("There is no data to post !");
            } else {
                for (int i = 0; i < reportList.size(); i++) {
                    //double amount = Double.parseDouble(reportList.get(i).getIntAmt());
                    double amount = Double.parseDouble(crAmt);
                    BigInteger ctrNo = BigInteger.valueOf(reportList.get(i).getCtrlNo());

                    if ((amount > 0) || (Double.parseDouble(accrAmt) > 0)) {
                        recno = ftsObj.getRecNo();
                        GoiRecon goiReconEntity = new GoiRecon();
                        GoiReconPK goiReconPK = new GoiReconPK(ctrNo.longValue(), drAcno, ymd.parse(ymd.format(dt)), recno);

                        goiReconEntity.setGoiReconPK(goiReconPK);
                        goiReconEntity.setBalance(0d);
                        goiReconEntity.setTy(1);
                        goiReconEntity.setDramt(Double.parseDouble(formatter.format(amount)));
                        goiReconEntity.setCramt(0);
                        goiReconEntity.setTrantype(8);
                        goiReconEntity.setTrsno(trsno.intValue());
                        goiReconEntity.setInstno("");
                        goiReconEntity.setPayby(3);
                        goiReconEntity.setIy(0);
                        goiReconEntity.setTrandesc(3);
                        goiReconEntity.setDetails("Entry of Intt. Rece. for Security");
                        goiReconEntity.setTranId(new BigInteger("0"));
                        goiReconEntity.setOrgBrnid(brCode);
                        goiReconEntity.setDestBrnid(brCode);
                        goiReconEntity.setTrantime(dt);
                        goiReconEntity.setAuth("Y");
                        goiReconEntity.setEnterby(userName);
                        goiReconEntity.setAuthby("System");
                        String msg = saveGoiRecon(goiReconEntity);

                        if (msg.equals("true")) {
                            if (Double.parseDouble(accrAmt) > 0) {
                                double dAmt = 0;
                                if (Double.parseDouble(accrAmt) > amount) {
                                    dAmt = Double.parseDouble(accrAmt) - amount;
                                } else {
                                    dAmt = amount - Double.parseDouble(accrAmt);
                                }

                                recno = ftsObj.getRecNo();
                                GoiRecon goiReconEntity1 = new GoiRecon();
                                GoiReconPK goiReconPK1 = new GoiReconPK(ctrNo.longValue(), drAcno, ymd.parse(ymd.format(dt)), recno);

                                goiReconEntity1.setGoiReconPK(goiReconPK1);
                                goiReconEntity1.setBalance(0d);
                                goiReconEntity1.setTy(1);
                                goiReconEntity1.setDramt(Double.parseDouble(formatter.format(dAmt)));
                                goiReconEntity1.setCramt(0);
                                goiReconEntity1.setTrantype(8);
                                goiReconEntity1.setTrsno(trsno.intValue());
                                goiReconEntity1.setInstno("");
                                goiReconEntity1.setPayby(3);
                                goiReconEntity1.setIy(0);
                                goiReconEntity1.setTrandesc(3);
                                goiReconEntity1.setDetails("Entry of Intt. Rece. for Security");
                                goiReconEntity1.setTranId(new BigInteger("0"));
                                goiReconEntity1.setOrgBrnid(brCode);
                                goiReconEntity1.setDestBrnid(brCode);
                                goiReconEntity1.setTrantime(dt);
                                goiReconEntity1.setAuth("Y");
                                goiReconEntity1.setEnterby(userName);
                                goiReconEntity1.setAuthby("System");
                                msg = saveGoiRecon(goiReconEntity1);
                                if (msg.equals("true")) {
                                    InvestmentGoidates dateEntity = dao.getInvestmentGoiDatesByCtrlNo(ctrNo.intValue());
                                    if (dateEntity == null) {
                                        throw new ApplicationException("Data not found in Investment Goi Dates !");
                                    }

                                    Date maxIntPostDt = dateEntity.getLastintpaiddt();
                                    if (tillDt.compareTo(maxIntPostDt) <= 0) {
                                        throw new ApplicationException("Interest already posted upto " + dmy.format(maxIntPostDt));
                                    }

                                    double oldAmt = dateEntity.getAmtinttrec();
                                    double amtIntaccr = 0.0;
                                    double totAmtIntAccr = 0.0;
                                    msg = dao.updateIntInvestGoiDatesByCtrlNo(ctrNo.intValue(), tillDt, Double.parseDouble(formatter.format(amount)) + oldAmt, userName, amtIntaccr, totAmtIntAccr);
                                    if (!msg.equalsIgnoreCase("true")) {
                                        throw new ApplicationException("Data not Updated in Investment Goi Dates !");
                                    }
                                }
                            } else {
                                InvestmentGoidates dateEntity = dao.getInvestmentGoiDatesByCtrlNo(ctrNo.intValue());
                                if (dateEntity == null) {
                                    throw new ApplicationException("Data not found in Investment Goi Dates !");
                                }

                                Date maxIntPostDt = dateEntity.getLastintpaiddt();
                                if (tillDt.compareTo(maxIntPostDt) <= 0) {
                                    throw new ApplicationException("Interest already posted upto " + dmy.format(maxIntPostDt));
                                }

                                double oldAmt = dateEntity.getAmtinttrec();
                                double amtIntaccr = 0.0;
                                double totAmtIntAccr = 0.0;
                                msg = dao.updateIntInvestGoiDatesByCtrlNo(ctrNo.intValue(), tillDt, Double.parseDouble(formatter.format(amount)) + oldAmt, userName, amtIntaccr, totAmtIntAccr);
                                if (!msg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Data not Updated in Investment Goi Dates !");
                                }
                            }
                        }
                        if (msg.equalsIgnoreCase("true")) {
                            if (Double.parseDouble(accrAmt) != 0.0) {
                                recno = ftsObj.getRecNo();
                                msg = insertDataIntoGlRecon(accrAcno, trsno, recno, Double.parseDouble(accrAmt), 0, 2, 3, remark, "Y", brCode, brCode, userName, 0, 0, "System");
                                if (msg.equals("true")) {
                                    msg = ftsObj.updateBalance(ftsObj.getAccountNature(accrAcno), accrAcno, Double.parseDouble(accrAmt), 0, "Y", "Y");
                                    if (msg.equalsIgnoreCase("true")) {
                                        updateInvestMasterAccdIntByCtrlNo(0.0, ctrNo.intValue());
                                    } else {
                                        throw new ApplicationException("Accdint Data not Saved!");
                                    }
                                } else {
                                    throw new ApplicationException("Accdint Data not Saved!");
                                }
                            }
                        }
                    }
                }
            }

            String glMsg = "true";
            if (Double.parseDouble(crAmt) > 0) {
                recno = ftsObj.getRecNo();
                glMsg = insertDataIntoGlRecon(entity.getIntGlhead(), trsno, recno, Double.parseDouble(crAmt), 0, 2, 3, remark, "Y", brCode, brCode, userName, 0, 0, "System");
                if (glMsg.equalsIgnoreCase("true")) {
                    glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(entity.getIntGlhead()), entity.getIntGlhead(), Double.parseDouble(crAmt), 0, "Y", "Y");
                    if (!glMsg.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Data not Saved!");
                    }
                } else {
                    throw new ApplicationException("Data not Saved!");
                }
            }
            if (glMsg.equalsIgnoreCase("true")) {
                recno = ftsObj.getRecNo();
                glMsg = insertDataIntoGlRecon(entity.getDrGlhead(), trsno, recno, Double.parseDouble(drAmt), 1, 2, 3, remark, "Y", brCode, brCode, userName, 0, 0, "System");
                if (glMsg.equalsIgnoreCase("true")) {
                    glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(entity.getDrGlhead()), entity.getDrGlhead(), 0, Double.parseDouble(drAmt), "Y", "Y");
                    if (!glMsg.equalsIgnoreCase("true")) {
                        throw new ApplicationException("Data not Saved!");
                    }
                } else {
                    throw new ApplicationException("Data not Saved!");
                }
            }
            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                ex.printStackTrace();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return result;
    }

    public Date getMaxToDateFromIntPostHistorySec() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getMaxToDateFromIntPostHistorySec();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<Object[]> getInvestmentDetailAndDatesForIntRecPostSec(String status, Date purDt) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (List<Object[]>) dao.getInvestmentDetailAndDatesForIntRecPostSec(status, purDt);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestmentSecInterestAmount(InvestmentFrdDatesAndDetailsHistory historyObj, InvestmentGoidates obj, String drAcno, String drAmt, String crAcno, String crAmt, String enterBy, String orgncode, String Flag) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "", acno = "", damt = "", camt = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        int ty = 0;
        try {
            ut.begin();
            result = dao.saveInvestmentFdrDatesAndDetailsHistory(historyObj);
            if (result.equalsIgnoreCase("true")) {
                result = dao.updateInvestmentSecDates(obj);
                if (result.equalsIgnoreCase("true")) {
                    trsno = ftsObj.getTrsNo();
                    recno = ftsObj.getRecNo();
                    String glMsg = insertDataIntoGlRecon(crAcno, trsno, recno, Double.parseDouble(crAmt), 0, 2, 3, "Update Int Rec. Of Sec", "Y", orgncode, orgncode, enterBy, 0, 0, "System");
                    if (glMsg.equals("true")) {
                        recno = ftsObj.getRecNo();
                        glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, Double.parseDouble(drAmt), 1, 2, 3, "Update Int Rec. Of Sec", "Y", orgncode, orgncode, enterBy, 0, 0, "System");
                        if (glMsg.equals("true")) {
                            recno = ftsObj.getRecNo();
                            if (Flag.equals("D")) {
                                acno = drAcno;
                                ty = 1;
                                damt = drAmt;
                                camt = "0";
                            } else {
                                acno = crAcno;
                                ty = 0;
                                damt = "0";
                                camt = drAmt;
                            }

                            GoiRecon goiReconEntity = new GoiRecon();
                            GoiReconPK goiReconPK = new GoiReconPK(historyObj.getCtrlNo(), drAcno, ymd.parse(ymd.format(dt)), recno);
                            goiReconEntity.setGoiReconPK(goiReconPK);
                            goiReconEntity.setBalance(0d);
                            goiReconEntity.setTy(ty);
                            goiReconEntity.setDramt(Double.parseDouble(damt));
                            goiReconEntity.setCramt(Double.parseDouble(camt));
                            goiReconEntity.setTrantype(8);
                            goiReconEntity.setTrsno(trsno.intValue());
                            goiReconEntity.setInstno("");
                            goiReconEntity.setPayby(3);
                            goiReconEntity.setIy(0);
                            goiReconEntity.setTrandesc(3);
                            goiReconEntity.setDetails("Entry of Intt. Rece. for Security");
                            goiReconEntity.setTranId(new BigInteger("0"));
                            goiReconEntity.setOrgBrnid(orgncode);
                            goiReconEntity.setDestBrnid(orgncode);
                            goiReconEntity.setTrantime(dt);
                            goiReconEntity.setAuth("Y");
                            goiReconEntity.setEnterby(enterBy);
                            goiReconEntity.setAuthby("System");
                            String msg = saveGoiRecon(goiReconEntity);
                        }
                    }
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String getGLByCtrlNo(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getGLByCtrlNo(ctrlNo);
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String fdrRenewalSaveProcess(Integer maxCtrlNo, Date purDate, String crHead, double facevalue, String orgnCode, String bank, String user) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float recno = 0.0f;
        Float trsno = 0.0f;
        try {
            String drAcno = "";
            List<Gltable> entityList = dao.getGltable(bank);
            for (Gltable var : entityList) {
                drAcno = var.getAcNo();
            }
            /**
             * save process in FDR Recon
             */
            recno = ftsObj.getRecNo();
            trsno = ftsObj.getTrsNo();
            FdrRecon fdrRecon = new FdrRecon();
            FdrReconPK fdrReconPK = new FdrReconPK();

            fdrReconPK.setAcno(drAcno);
            fdrReconPK.setDt(purDate);
            fdrReconPK.setRecno(recno);

            fdrRecon.setFdrReconPK(fdrReconPK);
            fdrRecon.setCtrlNo(maxCtrlNo);
            fdrRecon.setBalance(0.0);
            fdrRecon.setDrAmt(facevalue);

            fdrRecon.setCrAmt(0.0);
            fdrRecon.setTy(0);
            fdrRecon.setTrantype(0);
            fdrRecon.setTrsno(trsno.intValue());

            fdrRecon.setPayBy(3);
            fdrRecon.setIy(0);
            fdrRecon.setTranDesc(3);
            fdrRecon.setDetails("One Time Entry of FDR");

            fdrRecon.setOrgBrnid(orgnCode);
            fdrRecon.setDestBrnid(crHead.substring(0, 2));
            fdrRecon.setTranTime(dt);
            fdrRecon.setAuth("Y");
            fdrRecon.setEnterBy(user);
            fdrRecon.setAuthBy(user);

            String fdrMsg = dao.saveFdrRecon(fdrRecon);
            if (fdrMsg.equals("true")) {
                ParameterinfoReport entity = dao.getParameterinfoReportByName("FDPURCHASE");
                if (entity != null) {
                    if (entity.getCode() == 1) {
                        if (drAcno == null || drAcno.equalsIgnoreCase("")) {
                            throw new ApplicationException("There should be entry for FDPURCHASE in ParameterInfo Report table !");
                        } else {
                            recno = ftsObj.getRecNo();
                            String glMsg = insertDataIntoGlRecon(drAcno, trsno, recno, facevalue, 1, 2, 3, "PURCHASE ENTRY OF FDR", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                            if (glMsg.equals("true")) {
                                recno = ftsObj.getRecNo();
                                insertDataIntoGlRecon(crHead, trsno, recno, facevalue, 0, 2, 3, "PURCHASE ENTRY OF FDR", "Y", orgnCode, crHead.substring(0, 2), user, 0, 0, "System");
                            }
                        }
                    }
                }
            }
            result = "true";
        } catch (Exception ex) {
            try {
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public BigDecimal getTotalAmortAmtBal(Integer ctrlNo, String status) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (BigDecimal) (dao.getTotalAmortAmtBal(ctrlNo, status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateAmortDetailsAllBalByCtrlNo(String lastupdateby, Date lastupdatedt, BigInteger ctrlno) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            result = (dao.updateAmortDetailsAllBalByCtrlNo(lastupdateby, lastupdatedt, ctrlno));
            return result;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateInvestMasterAccdIntByCtrlNo(double acdint, int ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.updateInvestMasterAccdIntByCtrlNo(acdint, ctrlNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentGoidates> getControllNoForAmortCalc(String status, String type) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getControllNoForAmortCalc(status, type));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentCallMaster> getUnAuthorizeCallCtrlNo() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getUnAuthorizeCallCtrlNo();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentCallMaster getCallCtrlNoDetails(Integer ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getCallCtrlNoDetails(ctrlNo);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String closeCallMoney(Integer ctrlNo, String intDrHead, double intDrAmt, String intCrHead, double intCrAmt, String totDrHead, double totDrAmt,
            String totCrHead, double totCrAmt, String user, String orgncode, String rem) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f, recno = 0.0f;
        try {
            /**
             * Creation of Investment Call Money Entity
             */
            ut.begin();
            InvestmentCallMaster invCall = getCallCtrlNoDetails(ctrlNo);
            invCall.setStatus("CLOSED");
            invCall.setLastUpdateDt(dt);
            invCall.setLastUpdateBy(user);
            String msg = dao.updateCallMoney(invCall);
            if (msg.equals("true")) {
                trsno = ftsObj.getTrsNo();
                recno = ftsObj.getRecNo();
                String glMsgCr = insertDataIntoGlRecon(intCrHead, trsno, recno, intDrAmt, 0, 2, 3, rem, "Y", orgncode, intCrHead.substring(0, 2), user, 0, 0, "System");
                if (glMsgCr.equals("true")) {
                    recno = ftsObj.getRecNo();
                    String glMsgDr = insertDataIntoGlRecon(intDrHead, trsno, recno, intCrAmt, 1, 2, 3, rem, "Y", orgncode, intDrHead.substring(0, 2), user, 0, 0, "System");
                    if (glMsgDr.equals("true")) {
                        recno = ftsObj.getRecNo();
                        String glMsgTotCr = insertDataIntoGlRecon(totCrHead, trsno, recno, totDrAmt, 0, 2, 3, rem, "Y", orgncode, totCrHead.substring(0, 2), user, 0, 0, "System");
                        if (glMsgTotCr.equals("true")) {
                            recno = ftsObj.getRecNo();
                            String glMsgTotDr = insertDataIntoGlRecon(totDrHead, trsno, recno, totCrAmt, 1, 2, 3, rem, "Y", orgncode, totDrHead.substring(0, 2), user, 0, 0, "System");
                            if (!glMsgTotDr.equals("true")) {
                                ut.rollback();
                            }
                        } else {
                            ut.rollback();
                        }
                    } else {
                        ut.rollback();
                    }
                }
            }

            result = "true" + trsno.toString();
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<InvestmentSecurityAuthDetail> getUnAuthSecCtrlNo(String brnCode) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getUnAuthSecCtrlNo(brnCode);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<InvestmentSecurityAuthDetail> getUnAuthCtrlNoDtl(int seqNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getUnAuthCtrlNoDtl(seqNo);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveInvestmentSecurityAuthDetail(InvestmentSecurityAuthDetail entity) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        try {
            ut.begin();
            result = (dao.saveInvestmentSecurityAuthDetail(entity));
            ut.commit();
        } catch (Exception ex) {
            try {
                ex.printStackTrace();
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String deleteInvestmentSecurityAuth(int seqNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "";
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            ut.begin();
            result = (dao.deleteInvestmentSecurityAuth(seqNo));
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public Integer getMaxSeqNoInvestmentSecAuth() throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (Integer) (dao.getMaxSeqNoInvestmentSecAuth());
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public InvestmentSecurityAuthDetail getSecDetailToAuth(Integer seqNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return dao.getSecDetailToAuth(seqNo);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String postSecAccrInt(List<GoiIntReportPojo> tableList, double totAmt, Date tillDt, String authBy, String orgncode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;

        try {
            ut.begin();

            InvestmentCallHead entity = dao.getInvestCallHeadByCode("14");
            if (entity == null) {
                throw new ApplicationException("Data does not exist in Investment Call Head !");
            } else {
                if (entity.getDrGlhead() == null || entity.getDrGlhead().equals("") || entity.getCrGlhead() == null || entity.getCrGlhead().equals("")) {
                    throw new ApplicationException("GL Head does not present in Investment Call Head !");
                }
            }

            if (tableList.isEmpty()) {
                throw new ApplicationException("There is no data to post !");
            } else {
                Double dblInterest = 0.0;
                String glMsg = "";
                trsno = ftsObj.getTrsNo();
                for (int i = 0; i < tableList.size(); i++) {
                    dblInterest = Double.parseDouble(tableList.get(i).getIntAmt());
                    if (dblInterest > 0) {
                        String amount = formatter.format(dblInterest);
                        if (Double.parseDouble(amount) > 0) {
                            recno = ftsObj.getRecNo();

                            GoiRecon goiReconEntity = new GoiRecon();
                            GoiReconPK goiReconPK = new GoiReconPK(tableList.get(i).getCtrlNo(), entity.getCrGlhead(), ymd.parse(ymd.format(dt)), recno);

                            goiReconEntity.setGoiReconPK(goiReconPK);
                            goiReconEntity.setBalance(0d);
                            goiReconEntity.setTy(0);
                            goiReconEntity.setDramt(0);
                            goiReconEntity.setCramt(Double.parseDouble(formatter.format(dblInterest)));
                            goiReconEntity.setTrantype(8);
                            goiReconEntity.setTrsno(trsno.intValue());
                            goiReconEntity.setInstno("");
                            goiReconEntity.setPayby(3);
                            goiReconEntity.setIy(0);
                            goiReconEntity.setTrandesc(3);
                            goiReconEntity.setDetails("Entry of Receivable Intt. for Security");
                            goiReconEntity.setTranId(new BigInteger("0"));
                            goiReconEntity.setOrgBrnid(orgncode);
                            goiReconEntity.setDestBrnid(orgncode);
                            goiReconEntity.setTrantime(dt);
                            goiReconEntity.setAuth("Y");
                            goiReconEntity.setEnterby(authBy);
                            goiReconEntity.setAuthby("System");
                            String msg = saveGoiRecon(goiReconEntity);

                            if (msg.equals("true")) {
                                InvestmentGoidates dateEntity = dao.getSecAccrByCtrlNo(tableList.get(i).getCtrlNo());
                                if (dateEntity == null) {
                                    throw new ApplicationException("Data not found in Investment Frd Dates !");
                                }

                                //Date maxIntPostDt = getMaxToDateFromIntPostHistory();
                                Date maxIntPostDt = dateEntity.getLastIntPaidDtAccr();
                                if (tillDt.compareTo(maxIntPostDt) <= 0) {
                                    throw new ApplicationException("Interest already posted upto " + dmy.format(maxIntPostDt) + "For Control No." + tableList.get(i).getCtrlNo());
                                }

                                InvestmentGoidates goiDateEntity = new InvestmentGoidates();
                                goiDateEntity.setCtrlno(tableList.get(i).getCtrlNo());
                                goiDateEntity.setPurchasedt(dateEntity.getPurchasedt());
                                goiDateEntity.setPrintedIssuedt(dateEntity.getPrintedIssuedt());
                                goiDateEntity.setMatdt(dateEntity.getMatdt());
                                goiDateEntity.setCloseddt(dateEntity.getCloseddt());
                                goiDateEntity.setIntpdt1(dateEntity.getIntpdt1());
                                goiDateEntity.setIntpdt2(dateEntity.getIntpdt2());
                                goiDateEntity.setLastIntPaidDtAccr(tillDt);
                                goiDateEntity.setFlag('P');
                                goiDateEntity.setCode(dateEntity.getCode());
                                goiDateEntity.setAmtinttrec(dateEntity.getAmtinttrec());
                                goiDateEntity.setRecflag(dateEntity.getRecflag());
                                goiDateEntity.setIssueUnits(dateEntity.getIssueUnits());
                                goiDateEntity.setStatus(dateEntity.getStatus());
                                goiDateEntity.setAmtIntAccr(Double.parseDouble(formatter.format(dblInterest)));
                                goiDateEntity.setTotAmtIntAccr(dateEntity.getTotAmtIntAccr() + Double.parseDouble(formatter.format(dblInterest)));
                                goiDateEntity.setTotIntRec(dateEntity.getTotIntRec());
                                goiDateEntity.setLastintpaiddt(dateEntity.getLastintpaiddt());
                                goiDateEntity.setAccrIntBy(authBy);
                                goiDateEntity.setHyIntBy(dateEntity.getHyIntBy());
                                dao.updateInvestmentSecDates(goiDateEntity);
                            } else {
                                throw new ApplicationException("Problem in Goi Recon Entry");
                            }
                        }
                    }
                }

                recno = ftsObj.getRecNo();
                glMsg = insertDataIntoGlRecon(entity.getIntGlhead(), trsno, recno, totAmt, 0, 2, 3, "Int Accr. at " + dmy.format(tillDt), "Y", orgncode, orgncode, authBy, 0, 0, "System");
                if (glMsg.equals("true")) {
                    glMsg = ftsObj.updateBalance("PO", entity.getIntGlhead(), totAmt, 0, "Y", "Y");
                    if (glMsg.equalsIgnoreCase("true")) {
                        recno = ftsObj.getRecNo();
                        glMsg = insertDataIntoGlRecon(entity.getCrGlhead(), trsno, recno, totAmt, 1, 2, 3, "Int Accr. at " + dmy.format(tillDt), "Y", orgncode, orgncode, authBy, 0, 0, "System");
                        if (glMsg.equalsIgnoreCase("true")) {
                            glMsg = ftsObj.updateBalance("PO", entity.getIntGlhead(), totAmt, 0, "Y", "Y");
                            if (!glMsg.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Updation Problem in " + entity.getCrGlhead());
                            }
                        } else {
                            throw new ApplicationException("Insertion Problem in " + entity.getCrGlhead());
                        }
                    } else {
                        throw new ApplicationException("Updation Problem in " + entity.getIntGlhead());
                    }
                } else {
                    throw new ApplicationException("Insertion Problem in " + entity.getIntGlhead());
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

    public String saveFaceLessSellAmtPart(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt, double amortVal) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateInvestGoiDatesByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateAmortDetailsByCtrlNo(new BigInteger(maxSec), Long.parseLong(ctrlNo), todayDt);

            /**
             * ************* No Need For This Code Open After Discussion
             * ************
             */
//            InvestmentMaster investmentMaster = getInvestMaster(Integer.parseInt(ctrlNo), securityType);
//
//            InvestmentMasterPK investmentMasterPK = new InvestmentMasterPK(Integer.parseInt(maxSec), investmentMaster.getInvestmentMasterPK().getSectype());
//            InvestmentMaster investMasterEntity = new InvestmentMaster(investmentMasterPK, investmentMaster.getSecdesc(), investmentMaster.getInvstcode(), investmentMaster.getRoi(), investmentMaster.getTransdt(), investmentMaster.getSettledt(), dt, investmentMaster.getSellername(), investmentMaster.getBuyeracno(), investmentMaster.getBrokeracno(), (investmentMaster.getFacevalue() - sellingAmt), investmentMaster.getBrokerage(), 0d, investmentMaster.getEnterby(), investmentMaster.getMatdt(), ((investmentMaster.getFacevalue() - sellingAmt) * issuingPrice) / 100, investmentMaster.getBrokername(), investmentMaster.getAuth(), investmentMaster.getAuthby(), investmentMaster.getPricegsec(), investmentMaster.getYtm());
//
//            dao = new InvestmentMgmtDAO(entityManager);
//            dao.saveInvestmentMasterMerge(investMasterEntity);
//
//            InvestmentGoidates investmentGoidates = getInvestmentGoidates(Integer.parseInt(ctrlNo));
//
//            InvestmentGoidates investmentGoidatesEntity = new InvestmentGoidates(Integer.parseInt(maxSec), dt, investmentGoidates.getPrintedIssuedt(), investmentGoidates.getMatdt(), investmentGoidates.getCloseddt(), investmentGoidates.getIntpdt1(), investmentGoidates.getIntpdt2(), investmentGoidates.getLastintpaiddt(), investmentGoidates.getFlag(), investmentGoidates.getCode(), investmentGoidates.getAmtinttrec(), investmentGoidates.getRecflag(), investmentGoidates.getIssueUnits(), investmentGoidates.getStatus(), investmentGoidates.getAmtIntAccr(), investmentGoidates.getTotAmtIntAccr(), investmentGoidates.getTotIntRec(), investmentGoidates.getLastIntPaidDtAccr());
//            dao.saveInvestmentGoidatesMerge(investmentGoidatesEntity);
            /**
             * ************* No Need For This Code Open After Discussion
             * ************
             */
            trsno = ftsObj.getTrsNo();

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }
            String insertMsg = "";
            if (amortVal > 0) {
                InvestmentCallHead entity = dao.getInvestCallHeadByCode("10");

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(maxSec), trsno, recno, amortVal, 1, 2, 3d, "Bal. Days Amzt. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, amortVal, 0, 2, 3d, "Bal. Days Amzt. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            InvestmentCallHead entityBal = getInvestCallHeadByCode("11");

            if (balIntAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entityBal.getIntGlhead(), new BigInteger(maxSec), trsno, recno, balIntAmt, 0, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, balIntAmt, 1, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            if (intAccAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entityBal.getDrGlhead(), new BigInteger(maxSec), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, sellingAmt, 0, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, sellingAmt, 1, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                ex.printStackTrace();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public List<InvestmentGoidates> getControllNoSecWise(String secType, String secDesc) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getControllNoSecWise(secType, secDesc));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getSecurityDesc(String secType) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getSecurityDesc(secType));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveTrBillValue(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();
            String insertMsg ="";
            updateInvestMasterByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateInvestGoiDatesByCtrlNo(todayDt, Integer.parseInt(ctrlNo));

            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");
            InvestmentCallHead entity1 = dao.getInvestCallHeadByCode("13");
            trsno = ftsObj.getTrsNo();

            recno = ftsObj.getRecNo();
            if(sellingAmt>=issuingPrice){
             insertMsg = insertDataOnSave(entity1.getIntGlhead(), new BigInteger(maxSec), trsno, recno, sellingAmt-issuingPrice, 0, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }
            }else{
              insertMsg = insertDataOnSave(entity1.getDrGlhead(), new BigInteger(maxSec), trsno, recno, issuingPrice-sellingAmt, 0, 2, 3d, "Tot. Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }    
            }

//            recno = ftsObj.getRecNo();
//            insertMsg = insertDataOnSave(entity.getIntGlhead(), new BigInteger(maxSec), trsno, recno, balIntAmt, 0, 2, 3d, "Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
//            if (insertMsg.equalsIgnoreCase("false")) {
//                throw new ApplicationException();
//            }

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, issuingPrice, 0, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, sellingAmt, 1, 2, 3d, "Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String changeMark(String SECTYPE, String CONTROLNO, String Marking, String MarkingType, String updateBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String date;
            List list = entityManager.createNativeQuery("select SECTYPE,CONTROLNO,date_format(dt,'%Y%m%d') from investment_master where CONTROLNO = " + CONTROLNO + " and SECTYPE = '" + SECTYPE + "'").getResultList();
            if (!list.isEmpty()) {
                Vector vtr = (Vector) list.get(0);
                date = vtr.get(2).toString();
                int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_master_his (SECTYPE, CONTROLNO, dt,previousMarking,currentMarking, updateDate,updateBy) "
                        + "VALUES ('" + SECTYPE + "', " + CONTROLNO + ", '" + date + "', '" + Marking + "',  '" + MarkingType + "', now(), '" + updateBy + "')").executeUpdate();
                if (insertQuery <= 0) {
                    ut.rollback();
                    return "Data Insertion problem";
                }
                int updateQuery = entityManager.createNativeQuery("UPDATE investment_master SET LASTUPDATEBY = '" + updateBy + "', LASTUPDATEDT = now(), marking = '" + MarkingType + "' WHERE CONTROLNO = '" + CONTROLNO + "' and SECTYPE = '" + SECTYPE + "'").executeUpdate();
                if (updateQuery <= 0) {
                    ut.rollback();
                    return "Data updation problem";
                }
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getMarkingStatus(String CONTROLNO, String SECTYPE) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select marking,cast(bookvalue as decimal(25,2)),cast(facevalue as decimal(25,2)),sellername,date_format(matdt,'%d/%m/%Y'),secdesc from investment_master where CONTROLNO= " + Integer.parseInt(CONTROLNO) + " and SECTYPE ='" + SECTYPE + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getControlNo(String SECTYPE) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select distinct CONTROLNO from investment_master a,investment_goidates b where a.CONTROLNO = b.CTRLNO and a.SECTYPE = 'GOVERNMENT SECURITIES' and b.status='Active'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveMutualFundDetail(String buyDrAcNo, String buyCrAcNo, int days, double amt, String userName, String remark, String mark) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int seqNo = 0;
            List seqNoList = entityManager.createNativeQuery("select ifnull(max(seq_no),0) + 1 from investment_mutual_auth_detail").getResultList();
            if (seqNoList.size() > 0) {
                Vector vec = (Vector) seqNoList.get(0);
                if (vec.get(0) != null) {
                    seqNo = Integer.parseInt(vec.get(0).toString());
                }
            }

            int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_mutual_auth_detail (SEQ_NO,CONTROL_NO,BUY_DR_AC_NO,BUY_CR_AC_NO,"
                    + " REDEEM_DR_AC_NO,REDEEM_CR_AC_NO,DAYS,AMOUNT,PROFIT_AMT,GENERATE_DATE,STATUS,ENTER_BY,AUTH,AUTH_BY,REMARK,ENTRY_STATUS,marking,PARTIAL_AMT,PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT) "
                    + " VALUES (" + seqNo + ",0, '" + buyDrAcNo + "', '" + buyCrAcNo + "','',''," + days + "," + amt + ",0.0,"
                    + " now(),'A','" + userName + "','N','','" + remark + "','E','" + mark + "',0.0,0.0,0.0,'" + amt + "')").executeUpdate();
            if (insertQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            ut.commit();
            return "true " + seqNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getUnAuthSeqList(List statusList, String rAcNo) throws ApplicationException {
        List retLst = new ArrayList();

        try {
            retLst = entityManager.createNativeQuery("select distinct seq_no from investment_mutual_auth_detail where status in ('" + statusList.get(0).toString() + "','" + statusList.get(1).toString() + "','" + statusList.get(2).toString() + "','" + statusList.get(3).toString() + "') and auth = 'N' and entry_status ='E'").getResultList();

            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List getUnAuthSeqDetailList(int seqNo) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select BUY_DR_AC_NO,BUY_CR_AC_NO,DAYS,AMOUNT,GENERATE_DATE,ENTER_BY,REMARK,marking,PARTIAL_AMT,PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT from investment_mutual_auth_detail "
                    + " where seq_no = " + seqNo + "").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String verifyMutualFundDetail(int seqNo, String buyDrAcNo, String buyCrAcNo, int days, double amt, String gDate, String enterBy, String authBy, String remark,
            String orgBrnId, String mark) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int ctrlNo = 0;
            List ctrlNoList = entityManager.createNativeQuery("select ifnull(max(CONTROL_NO),0) + 1 from investment_mutual_fund_detail").getResultList();
            if (ctrlNoList.size() > 0) {
                Vector vec = (Vector) ctrlNoList.get(0);
                if (vec.get(0) != null) {
                    ctrlNo = Integer.parseInt(vec.get(0).toString());
                }
            }

            int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_mutual_fund_detail (CONTROL_NO,BUY_DR_AC_NO,BUY_CR_AC_NO,REDEEM_DR_AC_NO,"
                    + " REDEEM_CR_AC_NO,DAYS,AMOUNT,GENERATE_DATE,STATUS,ENTER_BY,AUTH_BY,REDEEM_DATE,REDEEM_BY,REDEEM_AUTH_BY,REMARK,P_L_STATUS,marking,PARTIAL_AMT,"
                    + "PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT) "
                    + " VALUES (" + ctrlNo + ",'" + buyDrAcNo + "', '" + buyCrAcNo + "', '',''," + days + "," + amt + ",'" + gDate + "',"
                    + " 'A','" + enterBy + "','" + authBy + "',NULL,'','','" + remark + "','','" + mark + "',0.00,0.00,0.00,'" + amt + "')").executeUpdate();
            // insertion of mutual fund data in history table 

            int insertQuery2 = entityManager.createNativeQuery("INSERT INTO investment_mutual_fund_detail_his (CONTROL_NO,BUY_DR_AC_NO,BUY_CR_AC_NO,REDEEM_DR_AC_NO,"
                    + " REDEEM_CR_AC_NO,DAYS,AMOUNT,GENERATE_DATE,STATUS,ENTER_BY,AUTH_BY,REDEEM_DATE,REDEEM_BY,REDEEM_AUTH_BY,REMARK,P_L_STATUS,marking,PARTIAL_AMT,"
                    + "PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT) "
                    + " VALUES (" + ctrlNo + ",'" + buyDrAcNo + "', '" + buyCrAcNo + "', '',''," + days + "," + amt + ",'" + gDate + "',"
                    + " 'A','" + enterBy + "','" + authBy + "',NULL,'','','" + remark + "','','" + mark + "',0.00,0.00,0.00,'" + amt + "')").executeUpdate();


            if (insertQuery <= 0 && insertQuery2 <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            Integer certificate = entityManager.createNativeQuery("update investment_mutual_auth_detail set control_no = " + ctrlNo + ", auth ='Y', "
                    + " auth_by='" + authBy + "', entry_status='V' where seq_no = " + seqNo + "").executeUpdate();
            if (certificate <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            String eDate = ymd_Format.format(ymd_Format.parse(gDate));

            String msg = "";
            String acNat = "";
            Float trsno = ftsObj.getTrsNo();
            acNat = ftsObj.getAccountNature(buyDrAcNo);

            float dRecNo = ftsObj.getRecNo();
            Query insertQuery20 = entityManager.createNativeQuery("insert into recon_trf_d(acno ,custname, dt, Dramt, CrAmt, Ty, TranType,"
                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                    + "values ('" + buyDrAcNo + "','" + ftsObj.getCustName(buyDrAcNo) + "','" + eDate + "'," + amt + ",0,'1',2," + dRecNo + ","
                    + trsno + ",'',3,0,'Y','" + enterBy + "','" + authBy + "','',0,0,'', CURRENT_TIMESTAMP,'" + remark + "',0,'','" + orgBrnId + "','"
                    + buyDrAcNo.substring(0, 2) + "','" + eDate + "','','')");
            int var20 = insertQuery20.executeUpdate();
            if (var20 <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            msg = ftsObj.insertRecons(acNat, buyDrAcNo, 1, amt, eDate, eDate, 2, remark, enterBy, trsno, "", dRecNo,
                    "Y", authBy, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgBrnId, buyDrAcNo.substring(0, 2), 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            msg = ftsObj.updateBalance(acNat, buyDrAcNo, 0, amt, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            acNat = ftsObj.getAccountNature(buyCrAcNo);
            dRecNo = ftsObj.getRecNo();

            Query insertQuery21 = entityManager.createNativeQuery("insert into recon_trf_d(acno ,custname, dt, Dramt, CrAmt, Ty, TranType,"
                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                    + "values ('" + buyCrAcNo + "','" + ftsObj.getCustName(buyCrAcNo) + "','" + eDate + "',0," + amt + ",0,2," + dRecNo + ","
                    + trsno + ",'',3,0,'Y','" + enterBy + "','" + authBy + "','',0,0,'', CURRENT_TIMESTAMP,'" + remark + "',0,'','" + orgBrnId + "','"
                    + buyCrAcNo.substring(0, 2) + "','" + eDate + "','','')");
            int var21 = insertQuery21.executeUpdate();
            if (var21 <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            msg = ftsObj.insertRecons(acNat, buyCrAcNo, 0, amt, eDate, eDate, 2, remark, enterBy, trsno, "", ftsObj.getRecNo(),
                    "Y", authBy, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgBrnId, buyCrAcNo.substring(0, 2), 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            msg = ftsObj.updateBalance(acNat, buyCrAcNo, amt, 0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            ut.commit();
            return "true " + ctrlNo + "  Batch No. " + trsno;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String deleteMutualFundDetail(int seqNo, String enterBy) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer certificate = entityManager.createNativeQuery("update investment_mutual_auth_detail set auth_by='" + enterBy + "', auth='Y', entry_status='D' ,STATUS='D'"
                    + " where seq_no = " + seqNo + "").executeUpdate();
            if (certificate <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getRedeemBankList(String code) throws ApplicationException {
        List retLst = new ArrayList();
        try {
            if (code.equalsIgnoreCase("1")) {
                retLst = entityManager.createNativeQuery("select Distinct i.BUY_DR_AC_NO,g.acname from investment_mutual_fund_detail i, gltable g where i.BUY_DR_AC_NO = g.acno and i.status in ('A','R','P','L')").getResultList();
            } else if (code.equalsIgnoreCase("3")) {
                retLst = entityManager.createNativeQuery("select Distinct i.BUY_DR_AC_NO,g.acname from investment_mutual_auth_detail i, gltable g where i.BUY_DR_AC_NO = g.acno and i.status in ('A','R','P','L') and entry_status ='E' and auth = 'N' ").getResultList();
            }
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List<InvestmentFDRInterestPojo> getCtrlToRedeem(String drAcNo) throws ApplicationException {
        List<InvestmentFDRInterestPojo> ctrlRedeemPojoList = new ArrayList<InvestmentFDRInterestPojo>();
        try {
            List result = new ArrayList();
            result = entityManager.createNativeQuery("select control_no, days, amount, date_format(generate_date,'%d/%m/%Y'), "
                    + "enter_by, buy_dr_ac_no,buy_cr_ac_no,marking,PARTIAL_AMT,PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT \n"
                    + "from investment_mutual_fund_detail where buy_dr_ac_no = '" + drAcNo + "' and status in ('A','P','L') and control_no  in (select control_no \n"
                    + "from investment_mutual_auth_detail where status in('A','P','L') and buy_dr_ac_no = '" + drAcNo + "') ").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InvestmentFDRInterestPojo pDtl = new InvestmentFDRInterestPojo();
                    pDtl.setCtrlNo(Integer.parseInt(record.get(0).toString()));
                    pDtl.setFdrNo(record.get(1).toString());
                    pDtl.setFaceValue(new BigDecimal(record.get(2).toString()));
                    pDtl.setPurchaseDt(record.get(3).toString());
                    pDtl.setEnterBy(record.get(4).toString());
                    pDtl.setDrHead(record.get(5).toString());
                    pDtl.setCrHead(record.get(6).toString());
                    pDtl.setIntOpt(record.get(7).toString());
                    pDtl.setPartialAmt(Double.valueOf(record.get(8).toString()));
                    pDtl.setPartialRedeemAmt(Double.valueOf(record.get(9).toString()));
                    pDtl.setTotalRedeemedPartialAmt(Double.valueOf(record.get(10).toString()));
                    pDtl.setTotalRemainingAmt(Double.valueOf(record.get(11).toString()));
                    ctrlRedeemPojoList.add(pDtl);
                }
            }
            return ctrlRedeemPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String saveMFundRedeemDetail(List<InvestmentFDRInterestPojo> dataList, String reDrAcNo, String plAcNo, double partialAmt,
            double partialredeemAmt, String userName, String remark, double totalRemainingAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        double diffAmt = 0.00;
        double indiRedeemAmt = 0.00;
        try {
            ut.begin();
            int seqNo = 0;
            List seqNoList = entityManager.createNativeQuery("select ifnull(max(seq_no),0) + 1 from investment_mutual_auth_detail").getResultList();
            if (seqNoList.size() > 0) {
                Vector vec = (Vector) seqNoList.get(0);
                if (vec.get(0) != null) {
                    seqNo = Integer.parseInt(vec.get(0).toString());
                }
            }
            if (partialredeemAmt > partialAmt) {
                diffAmt = partialredeemAmt - partialAmt;
            } else {
                diffAmt = partialAmt - partialredeemAmt;
            }
            for (int i = 0; i < dataList.size(); i++) {
                if (dataList.get(i).getPartialAmtInput() > 0.0) {
                    double pAmt = dataList.get(i).getPartialAmtInput() * diffAmt / partialAmt;
                    double rem = dataList.get(i).getTotalRemainingAmt() - dataList.get(i).getPartialAmtInput();
                    char status = 'A';
                    double partialRdmAmt = dataList.get(i).getTotalRedeemedPartialAmt() + dataList.get(i).getPartialAmtInput();
                    if (partialredeemAmt > partialAmt) {
                        status = 'P';
                        indiRedeemAmt = dataList.get(i).getPartialAmtInput() + pAmt;
                    } else if (partialredeemAmt < partialAmt) {
                        status = 'L';
                        indiRedeemAmt = dataList.get(i).getPartialAmtInput() - pAmt;
                    } else {
                        status = 'A';
                        indiRedeemAmt = dataList.get(i).getPartialAmtInput();
                    }

                    if (rem == 0.00) {
                        status = 'R';
                    }

                    int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_mutual_auth_detail (SEQ_NO,CONTROL_NO,BUY_DR_AC_NO,BUY_CR_AC_NO,"
                            + " REDEEM_DR_AC_NO,REDEEM_CR_AC_NO,DAYS,AMOUNT,PROFIT_AMT,GENERATE_DATE,STATUS,ENTER_BY,AUTH,AUTH_BY,REMARK,ENTRY_STATUS,marking,PARTIAL_AMT"
                            + ",PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT ) "
                            + " VALUES (" + seqNo + "," + dataList.get(i).getCtrlNo() + ",'" + dataList.get(i).getDrHead() + "', '" + dataList.get(i).getCrHead() + "',"
                            + "'" + reDrAcNo + "','" + plAcNo + "'," + dataList.get(i).getFdrNo() + "," + dataList.get(i).getFaceValue() + "," + pAmt + ", now(),'" + status + "','" + userName + "','N','','"
                            + remark + "','E','" + dataList.get(i).getIntOpt() + "','" + dataList.get(i).getPartialAmtInput() + "','" + indiRedeemAmt + "','"
                            + partialRdmAmt + "','" + rem + "')").executeUpdate();
                    if (insertQuery <= 0) {
                        ut.rollback();
                        throw new ApplicationException("Problem in Insertion");
                    }
                }
            }

            ut.commit();
            return "true " + seqNo;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List<InvestmentFDRInterestPojo> getSeqNoToVerifyDtl(String drAcNo, int seqNo) throws ApplicationException {
        List<InvestmentFDRInterestPojo> seqRedeemPojoList = new ArrayList<InvestmentFDRInterestPojo>();
        try {
            List result = new ArrayList();
            result = entityManager.createNativeQuery("select control_no, days, amount, date_format(generate_date,'%d/%m/%Y'), "
                    + "enter_by, redeem_dr_ac_no,redeem_cr_ac_no,generate_date,"
                    + " profit_amt,remark,marking,PARTIAL_AMT,PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT "
                    + " from investment_mutual_auth_detail where buy_dr_ac_no = '" + drAcNo + "' and seq_no = " + seqNo + " and status in ('A','R','P','L') "
                    + " and auth='N' and entry_status='E'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InvestmentFDRInterestPojo pDtl = new InvestmentFDRInterestPojo();
                    pDtl.setCtrlNo(Integer.parseInt(record.get(0).toString()));
                    pDtl.setFdrNo(record.get(1).toString());
                    pDtl.setFaceValue(new BigDecimal(record.get(2).toString()));
                    pDtl.setPurchaseDt(record.get(3).toString());
                    pDtl.setEnterBy(record.get(4).toString());
                    pDtl.setDrHead(record.get(5).toString());
                    pDtl.setCrHead(record.get(6).toString());
                    pDtl.setLastIntPaidDt(record.get(7).toString());
                    pDtl.setBookValue(new BigDecimal(record.get(8).toString()));
                    pDtl.setSecDesc(record.get(9).toString());
                    pDtl.setIntOpt(record.get(10).toString());
                    pDtl.setPartialAmt(Double.valueOf(record.get(11).toString()));
                    pDtl.setPartialRedeemAmt(Double.valueOf(record.get(12).toString()));
                    pDtl.setTotalRedeemedPartialAmt(Double.valueOf(record.get(13).toString()));
                    pDtl.setTotalRemainingAmt(Double.valueOf(record.get(14).toString()));
                    pDtl.setPartialAmtInput(Double.valueOf(record.get(11).toString()));
                    seqRedeemPojoList.add(pDtl);
                }
            }
            return seqRedeemPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String verifyMutualFundRedeemDetail(List<InvestmentFDRInterestPojo> dataList, int seqNo, String redeemDrAcNo, String redeemCrAcNo, String buyCrAcNo,
            String gDate, String authBy, String enterBy, String remark, String orgBrnId, Double partialAmt, Double totalPartialAmt, Double partialRedeemAmt,
            Double totalRemainingAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String eDate = ymd_Format.format(ymd_Format.parse(gDate));
            String msg = "";
            String acNat = "";
            Float trsno = ftsObj.getTrsNo();

            //Debit Account Of Bank

            acNat = ftsObj.getAccountNature(redeemDrAcNo);

            float dRecNo = ftsObj.getRecNo();
            Query insertQuery20 = entityManager.createNativeQuery("insert into recon_trf_d(acno ,custname, dt, Dramt, CrAmt, Ty, TranType,"
                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                    + "values ('" + redeemDrAcNo + "','" + ftsObj.getCustName(redeemDrAcNo) + "','" + eDate + "','" + partialRedeemAmt + "',0,'1',2," + dRecNo + ","
                    + trsno + ",'',3,0,'Y','" + enterBy + "','" + authBy + "','',0,0,'', CURRENT_TIMESTAMP,'" + remark + "',0,'','" + orgBrnId + "','"
                    + redeemDrAcNo.substring(0, 2) + "','" + eDate + "','','')");
            int var20 = insertQuery20.executeUpdate();
            if (var20 <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            msg = ftsObj.insertRecons(acNat, redeemDrAcNo, 1, partialRedeemAmt, eDate, eDate, 2, remark, enterBy, trsno, "", dRecNo,
                    "Y", authBy, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgBrnId, redeemDrAcNo.substring(0, 2), 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            msg = ftsObj.updateBalance(acNat, redeemDrAcNo, 1, partialRedeemAmt, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            //Credit Account Of Mutual Fund

            acNat = ftsObj.getAccountNature(buyCrAcNo);
            dRecNo = ftsObj.getRecNo();

            Query insertQuery21 = entityManager.createNativeQuery("insert into recon_trf_d(acno ,custname, dt, Dramt, CrAmt, Ty, TranType,"
                    + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                    + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                    + "values ('" + buyCrAcNo + "','" + ftsObj.getCustName(buyCrAcNo) + "','" + eDate + "',0," + partialAmt + ",0,2," + dRecNo + ","
                    + trsno + ",'',3,0,'Y','" + enterBy + "','" + authBy + "','',0,0,'', CURRENT_TIMESTAMP,'" + remark + "',0,'','" + orgBrnId + "','"
                    + buyCrAcNo.substring(0, 2) + "','" + eDate + "','','')");
            int var21 = insertQuery21.executeUpdate();
            if (var21 <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            msg = ftsObj.insertRecons(acNat, buyCrAcNo, 0, partialAmt, eDate, eDate, 2, remark, enterBy, trsno, "", ftsObj.getRecNo(),
                    "Y", authBy, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgBrnId, buyCrAcNo.substring(0, 2), 0, "", "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            msg = ftsObj.updateBalance(acNat, buyCrAcNo, partialAmt, 0, "", "");
            if (!msg.equalsIgnoreCase("true")) {
                ut.rollback();
                throw new ApplicationException(msg);
            }

            //Credit Account Of Profit
            int ty = 0;
            double pAmt = partialRedeemAmt - partialAmt;
            if (pAmt > 0) {
                ty = 0;
                pAmt = pAmt;
            } else if (pAmt < 0) {
                ty = 1;
                pAmt = Math.abs(pAmt);
            }
            acNat = ftsObj.getAccountNature(redeemCrAcNo);
            dRecNo = ftsObj.getRecNo();
            if (pAmt > 0) {
                Query insertQuery22 = entityManager.createNativeQuery("insert into recon_trf_d(acno ,custname, dt, Dramt, CrAmt, Ty, TranType,"
                        + "recno,trsno,instno,payby,iy,Auth,enterby,authby,tokenpaidby,TranDesc, TokenNo,SubTokenNo,trantime,details,"
                        + "tran_id,term_id,org_brnid,dest_brnid,valueDt,adviceNo,adviceBrnCode)"
                        + "values ('" + redeemCrAcNo + "','" + ftsObj.getCustName(redeemCrAcNo) + "','" + eDate + "',0," + pAmt + "," + ty + ",2," + dRecNo + ","
                        + trsno + ",'',3,0,'Y','" + enterBy + "','" + authBy + "','',0,0,'', CURRENT_TIMESTAMP,'" + remark + "',0,'','" + orgBrnId + "','"
                        + redeemCrAcNo.substring(0, 2) + "','" + eDate + "','','')");
                int var22 = insertQuery22.executeUpdate();
                if (var22 <= 0) {
                    ut.rollback();
                    throw new ApplicationException("Problem in Insertion");
                }

                msg = ftsObj.insertRecons(acNat, redeemCrAcNo, ty, pAmt, eDate, eDate, 2, remark, enterBy, trsno, "", ftsObj.getRecNo(),
                        "Y", authBy, 0, 3, "", null, 0f, "", "", 0, "", 0f, "", "", orgBrnId, redeemCrAcNo.substring(0, 2), 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    throw new ApplicationException(msg);
                }

                msg = ftsObj.updateBalance(acNat, redeemCrAcNo, pAmt, ty, "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    ut.rollback();
                    throw new ApplicationException(msg);
                }
            }
            char s = 'A';
            if (partialRedeemAmt > partialAmt) {
                s = 'P';
            } else {
                s = 'L';
            }
            List ctrlList = entityManager.createNativeQuery("select CONTROL_NO from investment_mutual_auth_detail where SEQ_NO='" + seqNo + "'").getResultList();

            for (int j = 0; j < ctrlList.size(); j++) {
                Vector vect = (Vector) ctrlList.get(j);

                if (dataList.get(j).getTotalRemainingAmt() == 0.00) {
                    s = 'R';
                }
                int cert = entityManager.createNativeQuery("update investment_mutual_auth_detail set status='" + s + "',auth ='Y',auth_by='" + authBy + "',entry_status='V'"
                        + " where CONTROL_NO='" + vect.get(0).toString() + "' and seq_no = '" + seqNo + "' ").executeUpdate();
                if (cert <= 0) {
                    ut.rollback();
                    throw new ApplicationException("Problem in Updating status");
                }
            }

            String p_st = "P";
            if (partialRedeemAmt < partialAmt) {
                p_st = "L";
            }

            for (int i = 0; i < dataList.size(); i++) {
                char status = 'R';
                if (dataList.get(i).getTotalRemainingAmt() != 0.00) {
                    if (dataList.get(i).getPartialRedeemAmt() > dataList.get(i).getPartialAmt()) {
                        status = 'P';
                    } else {
                        status = 'L';
                    }
                }

                Integer certificate = entityManager.createNativeQuery("update investment_mutual_fund_detail set redeem_dr_ac_no ='" + dataList.get(i).getDrHead() + "', "
                        + "redeem_cr_ac_no = '" + dataList.get(i).getCrHead() + "', profit_amt = " + dataList.get(i).getBookValue() + ","
                        + "redeem_date = '" + dataList.get(i).getLastIntPaidDt() + "', redeem_by ='" + enterBy + "', redeem_auth_by='" + authBy + "', "
                        + "redeem_remark ='" + remark + "', P_L_status ='" + p_st + "', status='" + status + "',PARTIAL_AMT='" + dataList.get(i).getPartialAmt() + "',PARTIAL_REDEEM_AMT='" + dataList.get(i).getPartialRedeemAmt() + "'"
                        + ",TOTAL_REDEEMED_PARTIAL_AMT='" + dataList.get(i).getTotalRedeemedPartialAmt() + "',TOTAL_REMAINING_AMT='" + dataList.get(i).getTotalRemainingAmt() + "'where control_no = " + dataList.get(i).getCtrlNo() + "").executeUpdate();
                // insert latest data of mutual data in history table

                int insertQuery2 = entityManager.createNativeQuery("INSERT INTO investment_mutual_fund_detail_his (CONTROL_NO,BUY_DR_AC_NO,BUY_CR_AC_NO,REDEEM_DR_AC_NO,"
                        + " REDEEM_CR_AC_NO,DAYS,AMOUNT,PROFIT_AMT,GENERATE_DATE,STATUS,ENTER_BY,AUTH_BY,REDEEM_DATE,REDEEM_BY,REDEEM_AUTH_BY,REMARK,REDEEM_REMARK,P_L_STATUS,marking,PARTIAL_AMT,"
                        + "PARTIAL_REDEEM_AMT,TOTAL_REDEEMED_PARTIAL_AMT,TOTAL_REMAINING_AMT) "
                        + " VALUES ('" + dataList.get(i).getCtrlNo() + "','" + buyCrAcNo + "','" + dataList.get(i).getDrHead() + "', '" + dataList.get(i).getDrHead() + "', '" + dataList.get(i).getCrHead() + "','" + Integer.parseInt(dataList.get(i).getFdrNo()) + "','" + dataList.get(i).getFaceValue() + "','" + dataList.get(i).getBookValue() + "','" + gDate + "',"
                        + " '" + status + "','" + enterBy + "','" + authBy + "','" + dataList.get(i).getLastIntPaidDt() + "','" + enterBy + "','" + authBy + "','" + remark + "','" + dataList.get(i).getSecDesc() + "','" + p_st + "','" + dataList.get(i).getIntOpt() + "','" + dataList.get(i).getPartialAmt() + "','" + dataList.get(i).getPartialRedeemAmt() + "'"
                        + ",'" + dataList.get(i).getTotalRedeemedPartialAmt() + "','" + dataList.get(i).getTotalRemainingAmt() + "')").executeUpdate();

                if (certificate <= 0 && insertQuery2 <= 0) {
                    ut.rollback();
                    throw new ApplicationException("Problem in Insertion");
                }
            }

            ut.commit();
            return "true" + "Batch No. " + trsno;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthSecCloseList(String secType) throws ApplicationException {
        List retLst = new ArrayList();
        try {
            retLst = entityManager.createNativeQuery("select distinct ctrl_no from investment_security_close_auth "
                    + " where auth = 'N' and security_type ='" + secType + "'").getResultList();
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<SecurityAuthClosePojo> getUnAuthCtrlNoSecClose(int ctrlNo) throws ApplicationException {
        List<SecurityAuthClosePojo> secClosePojoList = new ArrayList<SecurityAuthClosePojo>();
        try {
            List result = new ArrayList();
            result = entityManager.createNativeQuery("select TXNID,ifnull(security_type,''),ifnull(security_name,''),ifnull(ctrl_no,''),ifnull(seller_name,''),"
                    + " ifnull(face_value,''),ifnull(book_value,''),ifnull(maturity_date,''),ifnull(accrued_int,''),ifnull(amort_value,''),ifnull(balance_int,''),"
                    + " ifnull(issue_price,''),ifnull(selling_status,''),ifnull(selling_amt,''),ifnull(selling_dt,''),ifnull(debit_glHead,''),ifnull(branch,''),"
                    + " ifnull(enter_by,'') "
                    + " from investment_security_close_auth where ctrl_no = '" + ctrlNo + "' and auth='N'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    SecurityAuthClosePojo pDtl = new SecurityAuthClosePojo();

                    pDtl.setTxnid(Integer.parseInt(record.get(0).toString()));
                    pDtl.setSecTp(record.get(1).toString());
                    pDtl.setSecDetail(record.get(2).toString());
                    pDtl.setCtrlNo(record.get(3).toString());
                    pDtl.setSellerName(record.get(4).toString());
                    pDtl.setFaceValue(record.get(5).toString());
                    pDtl.setBookValue(record.get(6).toString());
                    pDtl.setMatDt(record.get(7).toString());
                    pDtl.setAccrInt(record.get(8).toString());
                    pDtl.setAmortVal(record.get(9).toString());
                    pDtl.setBalInt(record.get(10).toString());
                    pDtl.setIssuePrice(record.get(11).toString());
                    pDtl.setSelingStatus(record.get(12).toString());
                    pDtl.setSelingAmt(record.get(13).toString());
                    pDtl.setSelingDt(record.get(14).toString());
                    pDtl.setDrGlHead(record.get(15).toString());
                    pDtl.setBranch(record.get(16).toString());
                    pDtl.setEnterBy(record.get(17).toString());
                    secClosePojoList.add(pDtl);
                }
            }
            return secClosePojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteSecCloseAuth(int seqNo, String ctrlNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer delRecord = entityManager.createNativeQuery("delete from investment_security_close_auth where TXNID = " + seqNo + " "
                    + " and ctrl_no ='" + ctrlNo + "'").executeUpdate();
            if (delRecord <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Deletion");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveSecurityCloseAuthDetail(String securityType, String securityName, String ctrlNo, String sellerName, String faceValue, String bookValue,
            String maturityDate, String accruedInt, String amortValue, String balanceInt, String issuePrice, String sellingStatus, String sellingAmt,
            String sellingDt, String debitGlHead, String branch, String enterBy, String orgBrnid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_security_close_auth (security_type,security_name,ctrl_no,seller_name,"
                    + "face_value,book_value,maturity_date,accrued_int,amort_value,balance_int,issue_price,selling_status,selling_amt,selling_dt,"
                    + "debit_glHead,branch,enter_by,auth,auth_by,org_brnid,enter_dt) "
                    + " VALUES ('" + securityType + "','" + securityName + "', '" + ctrlNo + "', '" + sellerName + "','" + faceValue + "','" + bookValue + "',"
                    + " '" + maturityDate + "','" + accruedInt + "','" + amortValue + "','" + balanceInt + "','" + issuePrice + "','" + sellingStatus + "',"
                    + " '" + sellingAmt + "','" + sellingDt + "','" + debitGlHead + "','" + branch + "','" + enterBy + "','N','','" + orgBrnid + "',now())").executeUpdate();
            if (insertQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public List getUnAuthFDRSeqNo() throws ApplicationException {
        List retLst = new ArrayList();
        try {
            retLst = entityManager.createNativeQuery("select TXNID from investment_fdr_creation_auth where auth = 'N'").getResultList();
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<InvestmentFDRInterestPojo> getUnAuthSeqNoFDRList(int seqNo) throws ApplicationException {
        List<InvestmentFDRInterestPojo> fdrUnAuthPojoList = new ArrayList<InvestmentFDRInterestPojo>();
        try {
            List result = new ArrayList();
            result = entityManager.createNativeQuery("select TXNID,security_type,fdr_detail,fdr_no,fdr_bank,branch1,purchase_dt,days,months,years,mat_dt,roi,int_opt,"
                    + " face_value,interest,maturity_value,cr_glHead,dr_glHead,branch2,enter_by "
                    + " from investment_fdr_creation_auth where txnid = '" + seqNo + "' and auth='N'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InvestmentFDRInterestPojo pDtl = new InvestmentFDRInterestPojo();

                    pDtl.setCtrlNo(Integer.parseInt(record.get(0).toString()));
                    pDtl.setSecType(record.get(1).toString());
                    pDtl.setSecDesc(record.get(2).toString());
                    pDtl.setFdrNo(record.get(3).toString());
                    pDtl.setSellerName(record.get(4).toString());
                    pDtl.setBranch1(record.get(5).toString());
                    pDtl.setPurchaseDt(record.get(6).toString());
                    pDtl.setDays(record.get(7).toString());
                    pDtl.setMonths(record.get(8).toString());
                    pDtl.setYears(record.get(9).toString());
                    pDtl.setMatDt(record.get(10).toString());
                    pDtl.setRoi(Double.parseDouble(record.get(11).toString()));
                    pDtl.setIntOpt(record.get(12).toString());
                    pDtl.setFaceValue(new BigDecimal(record.get(13).toString()));
                    pDtl.setAmtIntRec(new BigDecimal(record.get(14).toString()));
                    pDtl.setTotAmtIntRec(new BigDecimal(record.get(15).toString()));
                    pDtl.setCrHead(record.get(16).toString());
                    pDtl.setDrHead(record.get(17).toString());
                    pDtl.setBranch2(record.get(18).toString());
                    pDtl.setEnterBy(record.get(19).toString());
                    fdrUnAuthPojoList.add(pDtl);
                }
            }
            return fdrUnAuthPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public String deleteFdrCreateAuth(int seqNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer delRecord = entityManager.createNativeQuery("delete from investment_fdr_creation_auth where TXNID = " + seqNo + "").executeUpdate();
            if (delRecord <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Deletion");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    public String saveFDRCreateAuthDetail(String secType, String tdDetails, String fdrNo, String bank, String branch, String purDate, String passDays, String passMonth, String passYear,
            String matDate, String roi, String intOpt, String faceValue, String interest, String bookvalue, String crHead, String drAcno, String branchName,
            String userName, String orgnBrCode) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_fdr_creation_auth (security_type,fdr_detail,fdr_no,fdr_bank,branch1,"
                    + " purchase_dt,days,months,years,mat_dt,roi,int_opt,face_value,interest,maturity_value,cr_glHead,dr_glHead,branch2,"
                    + " enter_by,auth,auth_by,org_brnid,enter_dt) "
                    + " VALUES ('" + secType + "','" + tdDetails + "', '" + fdrNo + "','" + bank + "', '" + branch + "','" + purDate + "','" + passDays + "',"
                    + " '" + passMonth + "','" + passYear + "','" + matDate + "','" + roi + "','" + intOpt + "','" + faceValue + "',"
                    + " '" + interest + "','" + bookvalue + "','" + crHead + "','" + drAcno + "','" + branchName + "','" + userName + "','N','','" + orgnBrCode + "',now())").executeUpdate();
            if (insertQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * Ok After Testing *
     */
    public List getUnAuthFDRCloseCtrlNo() throws ApplicationException {
        List retLst = new ArrayList();
        try {
            retLst = entityManager.createNativeQuery("select ctrl_no from investment_fdr_close_renew_auth where auth = 'N'").getResultList();
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<InvestmentFDRCloseRenewAuthPojo> getUnAuthFDRCloseRenew(String ctrlNo) throws ApplicationException {
        List<InvestmentFDRCloseRenewAuthPojo> fdrUnAuthPojoList = new ArrayList<InvestmentFDRCloseRenewAuthPojo>();
        try {
            List result = new ArrayList();
            result = entityManager.createNativeQuery("select ifnull(ctrl_no,''),ifnull(fdr_no,''),ifnull(s_purchase_Date,''),"
                    + " ifnull(s_Maturity_Date,''),ifnull(s_face_value,''),ifnull(s_book_value,''),ifnull(seller_name,''),ifnull(s_roi,''),ifnull(s_intOpt,''),"
                    + " ifnull(s_branch,''),ifnull(close_renew,''),ifnull(manual_auto,''),ifnull(ren_pur_date,''),ifnull(f_purchase_date,''),ifnull(days,''),"
                    + " ifnull(months,''),ifnull(years,''),ifnull(mat_date,''),ifnull(r_roi,''),ifnull(r_intOpt,''),ifnull(r_face_value,''),ifnull(r_interest,''),"
                    + " ifnull(r_maturity_value,''),ifnull(int_accr_glHead,''),ifnull(int_accr_value,''),ifnull(int_rec_cr_glHead,''),ifnull(int_rec_cr_value,''),"
                    + " ifnull(int_dr_glHead,''),ifnull(int_dr_value,''),ifnull(face_value_cr_glHead,''),ifnull(face_value_cr_value,''),ifnull(mat_dr_glHead,''),"
                    + " ifnull(mat_dr_value,''),ifnull(enter_by,'') "
                    + " from investment_fdr_close_renew_auth where ctrl_no = '" + ctrlNo + "' and auth='N'").getResultList();
            if (result.size() > 0) {
                for (int j = 0; j < result.size(); j++) {
                    Vector record = (Vector) result.get(j);
                    InvestmentFDRCloseRenewAuthPojo pDtl = new InvestmentFDRCloseRenewAuthPojo();

                    pDtl.setCtrlNo(record.get(0).toString());
                    pDtl.setFdrNo(record.get(1).toString());
                    pDtl.setsPurchaseDate(record.get(2).toString());
                    pDtl.setsMaturityDate(record.get(3).toString());
                    pDtl.setsFaceValue(record.get(4).toString());
                    pDtl.setsBookValue(record.get(5).toString());
                    pDtl.setSellerName(record.get(6).toString());
                    pDtl.setsRoi(record.get(7).toString());
                    pDtl.setsIntOpt(record.get(8).toString());
                    pDtl.setsBranch(record.get(9).toString());
                    pDtl.setCloseRenew(record.get(10).toString());
                    pDtl.setManualAuto(record.get(11).toString());
                    pDtl.setRenPurDate(record.get(12).toString());
                    pDtl.setfPurchaseDate(record.get(13).toString());
                    pDtl.setDays(record.get(14).toString());
                    pDtl.setMonths(record.get(15).toString());
                    pDtl.setYears(record.get(16).toString());
                    pDtl.setMatDate(record.get(17).toString());
                    pDtl.setrRoi(record.get(18).toString());
                    pDtl.setrIntOpt(record.get(19).toString());
                    pDtl.setrFaceValue(record.get(20).toString());
                    pDtl.setrInterest(record.get(21).toString());
                    pDtl.setrMaturityValue(record.get(22).toString());
                    pDtl.setIntAccrGlHead(record.get(23).toString());
                    pDtl.setIntAccrValue(record.get(24).toString());
                    pDtl.setIntRecCrGlHead(record.get(25).toString());
                    pDtl.setIntRecCrValue(record.get(26).toString());
                    pDtl.setIntDrGlHead(record.get(27).toString());
                    pDtl.setIntDrValue(record.get(28).toString());
                    pDtl.setFaceValueCrGlHead(record.get(29).toString());
                    pDtl.setFaceValueCrValue(record.get(30).toString());
                    pDtl.setMatDrGlHead(record.get(31).toString());
                    pDtl.setMatDrValue(record.get(32).toString());
                    pDtl.setEnterBy(record.get(33).toString());
                    fdrUnAuthPojoList.add(pDtl);
                }
            }
            return fdrUnAuthPojoList;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public String deleteFdrCloseRenewAuth(String ctrlNo) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            Integer delRecord = entityManager.createNativeQuery("delete from investment_fdr_close_renew_auth where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (delRecord <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Deletion");
            }
            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String saveFDRCloseRenewAuthDetail(String ctrlNo, String fdrNo, String sPurchaseDate, String sMaturityDate, String sFaceValue,
            String sBookValue, String sellerName, String sRoi, String sIntOpt, String sBranch, String closeRenew, String manualAuto, String renPurDate,
            String fPurchaseDate, String days, String months, String years, String matDate, String rRoi, String rIntOpt, String rFaceValue, String rInterest,
            String rMaturityValue, String intAccrGlHead, String intAccrValue, String intRecCrGlHead, String intRecCrValue, String intDrGlHead, String intDrValue,
            String faceValueCrGlHead, String faceValueCrValue, String matDrGlHead, String matDrValue, String enterBy, String orgBrnid) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();

            int insertQuery = entityManager.createNativeQuery("INSERT INTO investment_fdr_close_renew_auth (ctrl_no,fdr_no,s_purchase_Date,"
                    + "s_Maturity_Date,s_face_value,s_book_value,seller_name,s_roi,s_intOpt,s_branch,close_renew,manual_auto,ren_pur_date,f_purchase_date,"
                    + "days,months,years,mat_date,r_roi,r_intOpt,r_face_value,r_interest,r_maturity_value,int_accr_glHead,int_accr_value,int_rec_cr_glHead,"
                    + "int_rec_cr_value,int_dr_glHead,int_dr_value,face_value_cr_glHead,face_value_cr_value,mat_dr_glHead,mat_dr_value,enter_by,auth,"
                    + "auth_by,org_brnid,enter_dt) "
                    + " VALUES ('" + ctrlNo + "','" + fdrNo + "','" + sPurchaseDate + "', '" + sMaturityDate + "','" + sFaceValue + "',"
                    + " '" + sBookValue + "','" + sellerName + "','" + sRoi + "','" + sIntOpt + "','" + sBranch + "','" + closeRenew + "','" + manualAuto + "',"
                    + " '" + renPurDate + "','" + fPurchaseDate + "','" + days + "','" + months + "','" + years + "','" + matDate + "','" + rRoi + "','" + rIntOpt + "',"
                    + " '" + rFaceValue + "','" + rInterest + "','" + rMaturityValue + "','" + intAccrGlHead + "','" + intAccrValue + "','" + intRecCrGlHead + "',"
                    + " '" + intRecCrValue + "','" + intDrGlHead + "','" + intDrValue + "','" + faceValueCrGlHead + "','" + faceValueCrValue + "','" + matDrGlHead + "',"
                    + " '" + matDrValue + "','" + enterBy + "','N','','" + orgBrnid + "',now())").executeUpdate();
            if (insertQuery <= 0) {
                ut.rollback();
                throw new ApplicationException("Problem in Insertion");
            }

            ut.commit();
            return "true";
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getCtrlNoFromFdrDetails() throws ApplicationException {
        List retLst = new ArrayList();
        try {
            retLst = entityManager.createNativeQuery("select distinct ctrl_no from investment_fdr_details where status = 'ACTIVE' "
                    + " and ctrl_no not in (select ctrl_no from investment_fdr_close_renew_auth)").getResultList();
            return retLst;
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    @Override
    public List getControlList(String status) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            return (dao.getCtrlNoList(status));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<InvestmentFdrDates> onblurCntrlNumber(int ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            List<InvestmentFdrDates> list = (dao.onblurCntrlNumber(ctrlNo));
            return list;
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex);
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    public List<InvestmentFdrDetails> controlNumberDeatils(int ctrlNo) throws ApplicationException {
        dao = new InvestmentMgmtDAO(entityManager);
        try {
            List<InvestmentFdrDetails> list = (dao.controlNumberDeatils(ctrlNo));
            return list;
        } catch (ApplicationException e) {
            throw new ApplicationException(e);
        }
    }

    public String postIntRecievedRecFdr(List<FdrIntPostPojo> reportList, int controlNo, String frDate, String toDate, String authBy, String branchCode, String remark, String DrAcno, String DrAmt, String CrAcno, String CrAmt, String AccNo, String AccAmt, String intOpt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        dao = new InvestmentMgmtDAO(entityManager);
        SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        String result = "";
        Float trsno = 0.0f;
        Float recno = 0.0f;
        try {
            ut.begin();
            InvestmentCallHead entity = dao.getInvestCallHeadByCode("6");
            if (entity == null) {
                throw new ApplicationException("Data does not exist in Investment Call Head !");
            } else {
                if (entity.getDrGlhead() == null || entity.getDrGlhead().equals("") || entity.getCrGlhead() == null || entity.getCrGlhead().equals("")) {
                    throw new ApplicationException("GL Head does not present in Investment Call Head !");
                }
            }
            // Date maxIntPostDt = getMaxToDateFromIntPostHistory(ymd.format(dmy.parse(frDate)));

            Date maxIntPostDt = getMaxToDateFromIntPostHistory("FDR" + "_" + intOpt);
            if (dmy.parse(toDate).compareTo(maxIntPostDt) <= 0) {
                throw new ApplicationException("Interest already posted upto " + dmy.format(maxIntPostDt));
            }
            if (reportList.size() < 0) {
                throw new ApplicationException("There is no data to post !");
            } else {
                Double dblInterest = 0.0;
                String glMsg = "";
                trsno = ftsObj.getTrsNo();
                //dblInterest = Double.valueOf(DrAmt) - Double.valueOf(AccAmt);
                dblInterest = Double.valueOf(CrAmt);
                //   dblInterest =reportList.get(0).getClearingdiff();
                if (dblInterest >= 0) {
                    String branch = "";
                    InvestmentFdrDetails fdrEntity = dao.getInvestmentFdrDetailsByCtrlNo(reportList.get(0).getControlNo());
                    if (fdrEntity == null) {
                        throw new ApplicationException("Data not found in Fdr Details !");
                    } else {
                        branch = fdrEntity.getBranch();
                    }
                    String amount = formatter.format(dblInterest);
                    String AcnoFdr = dao.getGLByCtrlNo(reportList.get(0).getControlNo());

                    if (Double.parseDouble(amount) >= 0) {
                        recno = ftsObj.getRecNo();

                        FdrRecon fdrRecon = new FdrRecon();
                        FdrReconPK fdrReconPK = new FdrReconPK();

                        fdrReconPK.setAcno(AcnoFdr);
                        fdrReconPK.setDt(ymd.parse(ymd.format(dt)));
                        fdrReconPK.setRecno(recno);

                        fdrRecon.setFdrReconPK(fdrReconPK);
                        fdrRecon.setCtrlNo(reportList.get(0).getControlNo());
                        fdrRecon.setBalance(0.0);
                        fdrRecon.setCrAmt(Double.parseDouble(DrAmt));
                        fdrRecon.setDrAmt(Double.parseDouble(formatter.format(dblInterest)));
                        fdrRecon.setTy(0);
                        fdrRecon.setTrantype(8);
                        fdrRecon.setTrsno(trsno.intValue());
                        fdrRecon.setPayBy(3);
                        fdrRecon.setIy(0);
                        fdrRecon.setTranDesc(3);
                        fdrRecon.setDetails(remark);
                        fdrRecon.setOrgBrnid(branch);
                        fdrRecon.setDestBrnid(branch);
                        fdrRecon.setTranTime(dt);
                        fdrRecon.setAuth("Y");
                        fdrRecon.setEnterBy(authBy);
                        fdrRecon.setAuthBy(authBy);
                        glMsg = dao.saveFdrRecon(fdrRecon);
                        if (glMsg.equals("true")) {
                            InvestmentFdrDates dateEntity = dao.getInvestmentFdrDatesByCtrlNo(reportList.get(0).getControlNo());
                            if (dateEntity == null) {
                                throw new ApplicationException("Data not found in Investment Fdr Dates !");
                            }

                            InvestmentFdrDates fdrDateEntity = new InvestmentFdrDates();
                            fdrDateEntity.setCtrlNo(reportList.get(0).getControlNo());
                            fdrDateEntity.setPurchaseDt(dateEntity.getPurchaseDt());
                            fdrDateEntity.setMatDt(dateEntity.getMatDt());
                            fdrDateEntity.setClosedDt(dateEntity.getClosedDt());
                            fdrDateEntity.setIntPdt(dmy.parse(toDate));
                            fdrDateEntity.setLastIntPaidDt(dmy.parse(toDate));
                            fdrDateEntity.setFlag("R");
                            fdrDateEntity.setIntOpt(dateEntity.getIntOpt());
                            //fdrDateEntity.setAmtIntTrec(Double.parseDouble(formatter.format(dblInterest)));
                            fdrDateEntity.setAmtIntTrec(0.0);
                            fdrDateEntity.setAmtIntAccr(dateEntity.getTotAmtIntRec() + Double.parseDouble(formatter.format(dblInterest)));
                            fdrDateEntity.setTotAmtIntAccr(dateEntity.getTotAmtIntAccr() + Double.parseDouble(formatter.format(dblInterest)));
                            fdrDateEntity.setDays(dateEntity.getDays());
                            fdrDateEntity.setMonths(dateEntity.getMonths());
                            fdrDateEntity.setYears(dateEntity.getYears());
                            fdrDateEntity.setLastIntPaidDtAccr(dt);
                            fdrDateEntity.setTotAmtIntRec(0.0);
                            //fdrDateEntity.setAmtIntTrec(dblInterest);
                            dao.updateInvestmentFdrDates(fdrDateEntity);
                        }
                    }

                    if (Double.valueOf(AccAmt) > 0) {
                        recno = ftsObj.getRecNo();
                        glMsg = insertDataIntoGlRecon(DrAcno, trsno, recno, Double.valueOf(AccAmt), 0, 2, 3, remark, "Y", branchCode, branchCode, authBy, 0, 0, "System");
                        if (glMsg.equalsIgnoreCase("true")) {
                            glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(DrAcno), DrAcno, Double.valueOf(AccAmt), 0, "Y", "Y");
                            if (!glMsg.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Data not Saved!");
                            }
                        } else {
                            throw new ApplicationException("Data not Saved!");
                        }

                        if (glMsg.equalsIgnoreCase("true")) {
                            recno = ftsObj.getRecNo();
                            glMsg = insertDataIntoGlRecon(AccNo, trsno, recno, Double.valueOf(DrAmt), 1, 2, 3, remark, "Y", branchCode, branchCode, authBy, 0, 0, "System");
                            if (glMsg.equalsIgnoreCase("true")) {
                                glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(AccNo), AccNo, Double.valueOf(DrAmt), 1, "Y", "Y");
                                if (!glMsg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Data not Saved!");
                                }
                            } else {
                                throw new ApplicationException("Data not Saved!");
                            }
                        }
                        if (glMsg.equalsIgnoreCase("true")) {
                            recno = ftsObj.getRecNo();
                            glMsg = insertDataIntoGlRecon(CrAcno, trsno, recno, dblInterest, 0, 2, 3, remark, "Y", branchCode, branchCode, authBy, 0, 0, "System");
                            if (glMsg.equalsIgnoreCase("true")) {
                                glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(CrAcno), CrAcno, dblInterest, 0, "Y", "Y");

                                if (!glMsg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Data not Saved!");
                                }
                            } else {
                                throw new ApplicationException("Data not Saved!");
                            }
                        }
                    } else if (Double.valueOf(AccAmt) == 0.0) {
                        recno = ftsObj.getRecNo();
                        glMsg = insertDataIntoGlRecon(AccNo, trsno, recno, Double.valueOf(DrAmt), 1, 2, 3, remark, "Y", branchCode, branchCode, authBy, 0, 0, "System");
                        if (glMsg.equalsIgnoreCase("true")) {
                            glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(AccNo), AccNo, Double.valueOf(DrAmt), 1, "Y", "Y");
                            if (!glMsg.equalsIgnoreCase("true")) {
                                throw new ApplicationException("Data not Saved!");
                            }
                        } else {
                            throw new ApplicationException("Data not Saved!");
                        }

                        if (glMsg.equalsIgnoreCase("true")) {
                            recno = ftsObj.getRecNo();
                            glMsg = insertDataIntoGlRecon(CrAcno, trsno, recno, Double.valueOf(DrAmt), 0, 2, 3, remark, "Y", branchCode, branchCode, authBy, 0, 0, "System");
                            if (glMsg.equalsIgnoreCase("true")) {
                                glMsg = ftsObj.updateBalance(ftsObj.getAccountNature(CrAcno), CrAcno, Double.valueOf(DrAmt), 0, "Y", "Y");
                                if (!glMsg.equalsIgnoreCase("true")) {
                                    throw new ApplicationException("Data not Saved!");
                                }
                            } else {
                                throw new ApplicationException("Data not Saved!");
                            }
                        }
                    }
                    result = "true" + trsno;
                    ut.commit();
                }
            }
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

    public String insertDataOnGoiRecon(String acno, Integer ctrNo, Float trsno, Float recno, double amount, Integer ty, Integer trantype, double payby, String details, String auth, String orgncode, String destcode, String brAlpha, String enterby) {
        String msg = "", advBrn = "";
        try {
            GoiRecon goiReconEntity = new GoiRecon();
            GoiReconPK goiReconPK = new GoiReconPK(ctrNo.longValue(), acno, ymd.parse(ymd.format(dt)), recno);
            goiReconEntity.setGoiReconPK(goiReconPK);
            goiReconEntity.setBalance(0d);
            if (ty == 0) {
                goiReconEntity.setDramt(0);
                goiReconEntity.setCramt(amount);
            } else if (ty == 1) {
                goiReconEntity.setDramt(amount);
                goiReconEntity.setCramt(0);
            }
            goiReconEntity.setTy(ty);
            goiReconEntity.setTrantype(trantype);
            goiReconEntity.setTrsno(trsno.intValue());
            goiReconEntity.setInstno("0");
            goiReconEntity.setPayby(payby);
            goiReconEntity.setIy(0);
            goiReconEntity.setTrandesc(0);
            goiReconEntity.setDetails(details);
            goiReconEntity.setTranId(new BigDecimal(recno.toString()).toBigInteger());
            goiReconEntity.setOrgBrnid(orgncode);
            goiReconEntity.setDestBrnid(destcode);
            goiReconEntity.setTrantime(dt);
            goiReconEntity.setAuth(auth);
            goiReconEntity.setEnterby(enterby);
            goiReconEntity.setAuthby("System");
            msg = saveGoiRecon(goiReconEntity);
            msg = "true";
        } catch (Exception ex) {
            return msg = "false";
        }
        return msg;
    }

    public String saveSellAmtEqualBookAmt(Date todayDt, int ctrlNo, String securityType, double sellingAmt, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, ctrlNo);
            updateInvestGoiDatesByCtrlNo(todayDt, ctrlNo);

            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");

            trsno = ftsObj.getTrsNo();
            recno = ftsObj.getRecNo();

            String insertMsg = insertDataOnSave(entity.getIntGlhead(), new BigInteger(String.valueOf(ctrlNo)), trsno, recno, balIntAmt, 0, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();

            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, balIntAmt, 1, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(String.valueOf(ctrlNo)), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();

            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, sellingAmt, 0, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(String.valueOf(ctrlNo)), trsno, recno, sellingAmt, 1, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String saveSellAmtGreaterBookAmt(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateInvestGoiDatesByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateAmortDetailsByCtrlNo(new BigInteger(maxSec), Long.parseLong(ctrlNo), todayDt);

            InvestmentCallHead entity = dao.getInvestCallHeadByCode("11");
            trsno = ftsObj.getTrsNo();
            String insertMsg = "";
            if (balIntAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getIntGlhead(), new BigInteger(maxSec), trsno, recno, balIntAmt, 0, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, balIntAmt, 1, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            if (intAccAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(maxSec), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, issuingPrice, 0, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, issuingPrice, 1, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            InvestmentCallHead entity1 = dao.getInvestCallHeadByCode("13");

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(entity1.getIntGlhead(), new BigInteger(maxSec), trsno, recno, (sellingAmt - issuingPrice), 0, 2, 3d, "Profit On Premature Redm. Of Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, (sellingAmt - issuingPrice), 1, 2, 3d, "Profit On Premature Redm. Of Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    public String saveSellAmtLessBookAmt(Date todayDt, String ctrlNo, String maxSec, String securityType, double sellingAmt, double issuingPrice, String orgnBrCode, String destBrCode, String userName, double intAccAmt, String creditedGlHead, String alphacode, double balIntAmt, double amortVal) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String result = "", cracno = "";
        Float trsno = 0f, recno = 0f;
        try {
            ut.begin();

            updateInvestMasterByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateInvestGoiDatesByCtrlNo(todayDt, Integer.parseInt(ctrlNo));
            updateAmortDetailsByCtrlNo(new BigInteger(maxSec), Long.parseLong(ctrlNo), todayDt);

            trsno = ftsObj.getTrsNo();

            List<GlDescRange> gltableList = getGlRangeBySec(securityType);
            if (!gltableList.isEmpty()) {
                for (GlDescRange glEntity : gltableList) {
                    cracno = orgnBrCode + CbsAcCodeConstant.GL_ACCNO.getAcctCode() + String.format("%06d", Integer.parseInt(glEntity.getFromno())) + "01";
                }
            }
            String insertMsg = "";
            if (amortVal > 0) {
                InvestmentCallHead entity = dao.getInvestCallHeadByCode("10");

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entity.getDrGlhead(), new BigInteger(maxSec), trsno, recno, amortVal, 1, 2, 3d, "Premature Bal. Days Amzt. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, amortVal, 0, 2, 3d, "Premature Bal. Days Amzt. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            InvestmentCallHead entityBal = getInvestCallHeadByCode("11");

            if (balIntAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entityBal.getIntGlhead(), new BigInteger(maxSec), trsno, recno, balIntAmt, 0, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, balIntAmt, 1, 2, 3d, "Premature Bal. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            if (intAccAmt > 0) {
                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(entityBal.getDrGlhead(), new BigInteger(maxSec), trsno, recno, intAccAmt, 0, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }

                recno = ftsObj.getRecNo();
                insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, intAccAmt, 1, 2, 3d, "Tot. Premature Posted Accr. Intt. On C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
                if (insertMsg.equalsIgnoreCase("false")) {
                    throw new ApplicationException();
                }
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(cracno, new BigInteger(maxSec), trsno, recno, sellingAmt, 0, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, sellingAmt, 1, 2, 3d, "Premature Closed Sec. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            InvestmentCallHead entity1 = dao.getInvestCallHeadByCode("13");

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(creditedGlHead, new BigInteger(maxSec), trsno, recno, (issuingPrice - sellingAmt), 0, 2, 3d, "Loss On Premature Redm. Of. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            recno = ftsObj.getRecNo();
            insertMsg = insertDataOnSave(entity1.getDrGlhead(), new BigInteger(maxSec), trsno, recno, (issuingPrice - sellingAmt), 1, 2, 3d, "Loss On Premature Redm. Of. C/N-" + ctrlNo, "Y", orgnBrCode, destBrCode, userName, alphacode);
            if (insertMsg.equalsIgnoreCase("false")) {
                throw new ApplicationException();
            }

            Integer invSecClAuth = entityManager.createNativeQuery("update investment_security_close_auth set auth ='Y', "
                    + " auth_by='" + userName + "' where ctrl_no = " + ctrlNo + "").executeUpdate();
            if (invSecClAuth <= 0) {
                throw new ApplicationException();
            }

            result = "true" + trsno;
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                ex.printStackTrace();
                throw new ApplicationException(ex);
            } catch (Exception e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }
    
    public int getTotalSumOfDaysByCtrl(int ctrlNo) throws ApplicationException {
        int totAmt = 0;
        try {
            List totAmortDaysList = entityManager.createNativeQuery("select ifnull(sum(days),0) from investment_amortization_details where CTRL_NO = " + ctrlNo + "").getResultList();
            if (!totAmortDaysList.isEmpty()) {
                Vector ele = (Vector) totAmortDaysList.get(0);
                totAmt = Integer.parseInt(ele.get(0).toString());                
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return totAmt;
    }
}