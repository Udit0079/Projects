/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.dao.neftrtgs;

import com.cbs.constant.NamedQueryConstant;
import com.cbs.dao.GenericDAO;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.neftrtgs.AccountMaster;
import com.cbs.entity.neftrtgs.AccountTypeMaster;
import com.cbs.entity.neftrtgs.EPSAckMessage;
import com.cbs.entity.neftrtgs.EPSBankMaster;
import com.cbs.entity.neftrtgs.EPSBranchMaster;
import com.cbs.entity.neftrtgs.EPSBranchPaySysDetail;
import com.cbs.entity.neftrtgs.EPSChargeDetails;
import com.cbs.entity.neftrtgs.EPSCorrBranchDetail;
import com.cbs.entity.neftrtgs.EPSInwardCredit;
import com.cbs.entity.neftrtgs.EPSMessageCategory;
import com.cbs.entity.neftrtgs.EPSN06Message;
import com.cbs.entity.neftrtgs.EPSNodalBranchDetail;
import com.cbs.entity.neftrtgs.NeftOwDetails;
import com.cbs.entity.neftrtgs.ReconvmastTrans;
import com.cbs.entity.neftrtgs.TDAccountMaster;
import com.cbs.exception.ApplicationException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author root
 */
public class ElectronicPaymentSystemDAO extends GenericDAO {

    public ElectronicPaymentSystemDAO(EntityManager entityManager) {
        super(entityManager);
    }

    /**
     * ******BranchMasterEftDAO***********
     */
    public EPSBranchMaster getEpsBranchMaster(String branchCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSBranchMaster.findByBranchCode");
            createNamedQuery.setParameter("branchCode", branchCode);
            EPSBranchMaster entity = (EPSBranchMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******BranchPaySysDetailDAO***********
     */
    public EPSBranchPaySysDetail getEpsBranchPaySysDetail(String paySysId) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSBranchPaySysDetail.findByPaySysId");
            createNamedQuery.setParameter("paySysId", paySysId);
            EPSBranchPaySysDetail entity = (EPSBranchPaySysDetail) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******EPSbankMasterDAO***********
     */
    public EPSBankMaster getEpsBankMaster(String bankCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSBankMaster.findByBankCode");
            createNamedQuery.setParameter("bankCode", bankCode);
            EPSBankMaster entity = (EPSBankMaster) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsBankMaster(EPSBankMaster entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsBankMaster(EPSBankMaster entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******EFTChargeDetailsDAO***********
     */
    public EPSChargeDetails getEpsChargeDetails(String chargeEventID) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSChargeDetails.findByChargeEventID");
            createNamedQuery.setParameter("chargeEventID", chargeEventID);
            EPSChargeDetails entity = (EPSChargeDetails) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******EftCorrBranchDetailDAO***********
     */
    public EPSCorrBranchDetail getEpsCorrbranchDetails(String branchCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSCorrBranchDetail.findByBranchCode");
            createNamedQuery.setParameter("branchCode", branchCode);
            EPSCorrBranchDetail entity = (EPSCorrBranchDetail) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updaeEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******EftNodalBranchDetailDAO***********
     */
    public EPSNodalBranchDetail getEpsNodalBranchDetails(String outwardPoolAccId) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSNodalBranchDetail.findByOutwardPoolAccId");
            createNamedQuery.setParameter("outwardPoolAccId", outwardPoolAccId);
            EPSNodalBranchDetail entity = (EPSNodalBranchDetail) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******MsgCategoryBeanDAO***********
     */
    public EPSMessageCategory getEpsMessageCategory(String msgSubType) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSMessageCategory.findByMsgSubType");
            createNamedQuery.setParameter("msgSubType", msgSubType);
            EPSMessageCategory entity = (EPSMessageCategory) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String deleteEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        try {
            delete(entity, entity.getMsgSubType());
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * ******TransactionDAO***********
     */
    public List<EPSInwardCredit> getNodalGridData(String paySysId, String msgStatus) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.EPSINWARD_CREDIT_BY_PAYSYSID_MSGSTATUS);
            createNamedQuery.setParameter("paySysId", paySysId);
            createNamedQuery.setParameter("msgStatus", msgStatus);
            List<EPSInwardCredit> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveAck(EPSAckMessage entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveReceivedMessage(EPSInwardCredit entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public EPSN06Message findBySequenceNo(String tranId) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSN06Message.findByTranId");
            createNamedQuery.setParameter("tranId", tranId);
            return ((EPSN06Message) createNamedQuery.getSingleResult());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateBySequenceNo(EPSN06Message entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public EPSInwardCredit getInwardCrByUTR(String utr) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSInwardCredit.findByUtr");
            createNamedQuery.setParameter("utr", utr);
            EPSInwardCredit entity = (EPSInwardCredit) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public void updateInwardCreditByUTR(EPSInwardCredit entity) throws ApplicationException {
        try {
            entityManager.merge(entity);
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<EPSNodalBranchDetail> getNodalRTGSDrAcc(String paySysId) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSNodalBranchDetail.findByPaySysId");
            createNamedQuery.setParameter("paySysId", paySysId);
            List<EPSNodalBranchDetail> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getMessage(String paySysId) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.MSGSUBTYPE_BY_PAYSYSID_SCREENFLAG);
            createNamedQuery.setParameter("paySysId", paySysId);
            createNamedQuery.setParameter("screenFlag", "Y");
            List<String> msgSubType = createNamedQuery.getResultList();
            return msgSubType;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<String> getChargeEventId(String paySysId) throws ApplicationException {
        try {
            Query createNameQuery = entityManager.createQuery(NamedQueryConstant.CHARGE_EVENT_ID_BY_PAYSYS_ID);
            createNameQuery.setParameter("paySysId", paySysId);
            List<String> chargeEventId = createNameQuery.getResultList();
            return chargeEventId;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getAlphaCode(int brnCode) throws ApplicationException {
        try {
            Query createNameQuery = entityManager.createQuery(NamedQueryConstant.GET_ALPHACODE);
            createNameQuery.setParameter("brnCode", brnCode);
            return (createNameQuery.getSingleResult().toString());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public AccountTypeMaster getAccountNature(String accountNumber, String acctCode) throws ApplicationException {
        try {

            Query createNameQuery = entityManager.createNamedQuery("AccountTypeMaster.findByAcctCode");
            createNameQuery.setParameter("acctCode", acctCode);
            AccountTypeMaster entity = (AccountTypeMaster) createNameQuery.getSingleResult();

            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    /**
     * *There could be problem in this method by login and coding***
     */
    public String getUTRNumber(String paySysId) throws ApplicationException {
        String utrNo = "";
        String trnRefNo = "";
        String year = "";
        try {
            if (paySysId.equalsIgnoreCase("NEFT")) {
                paySysId = "N";
            } else if (paySysId.equalsIgnoreCase("RTGS")) {
                paySysId = "R";
            }
            Query createNamedQuery = entityManager.createNamedQuery("EPSBankMaster.findAll");
            List<EPSBankMaster> bankList = createNamedQuery.getResultList();
            if (bankList.size() <= 0) {
                return "Bank code does not exist";
            }
            EPSBankMaster entity = bankList.get(0);
            String bankCode = entity.getBankCode();
            final int julianDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
            Integer jd = new Integer(julianDay);
            String newjd = String.valueOf(jd);
            int length = newjd.length();
            int addZero = 3 - length;
            for (int i = 1; i <= addZero; i++) {
                newjd = "0" + newjd;
            }
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if (String.valueOf(currentYear).length() == 4) {
                year = String.valueOf(currentYear).substring(2, 4);
            }
            createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_TRSNO);
            Object trsNumberObj = (Object) createNamedQuery.getSingleResult();
            String trsNumber = trsNumberObj.toString();
            if (trsNumber == null || trsNumber.equalsIgnoreCase("")) {
                return "Trs no does not exist";
            }
            Double trsNo = Double.parseDouble(trsNumber);
            trsNo = trsNo + 1;
            ReconvmastTrans trsN = new ReconvmastTrans(trsNo);
            entityManager.persist(trsN);
            trsNumber = String.valueOf(trsNo);
            if (trsNumber.contains(".")) {
                trsNumber = trsNumber.substring(0, trsNumber.indexOf("."));
            }
            int trsLength = trsNumber.length();
            int addZeros = 6 - trsLength;
            for (int i = 1; i <= addZeros; i++) {
                trsNumber = "0" + trsNumber;
            }
            utrNo = bankCode + paySysId + year + newjd + trsNumber;

            /**
             * *****************PaySys Tran Ref No****************************
             */
            String hour = "", minute = "", second = "", miliSecond = "";
            Calendar cal = Calendar.getInstance();

            Integer h = (Integer) cal.get(Calendar.HOUR);
            if (h.toString().length() == 1) {
                hour = "0" + h.toString();
            } else {
                hour = h.toString();
            }

            Integer m = (Integer) cal.get(Calendar.MINUTE);
            if (m.toString().length() == 1) {
                minute = "0" + m.toString();
            } else {
                minute = m.toString();
            }

            Integer s = (Integer) cal.get(Calendar.SECOND);
            if (s.toString().length() == 1) {
                second = "0" + s.toString();
            } else {
                second = s.toString();
            }

            Integer ms = (Integer) cal.get(Calendar.MILLISECOND);
            if (ms.toString().length() == 1) {
                miliSecond = "00" + ms.toString();
            } else if (ms.toString().length() == 2) {
                miliSecond = "0" + ms.toString();
            } else {
                miliSecond = ms.toString();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            String curDate = sdf.format(date);
            String appDate = curDate.substring(2);

            trnRefNo = "0" + appDate + hour + minute + second + miliSecond;

            /**
             * *****************end here**************************************
             */
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return utrNo + ":" + trnRefNo + ":" + "bgsg";
    }

    public String getDrAccInfo(String acno, String paySysId, String acctCode) throws ApplicationException {
        String custName = "";
        String CrAccId = "";
        String glcustName = "";
        int acStatus = 0;
        try {
            AccountTypeMaster entity = getAccountNature(acno, acctCode);
            if (entity.getAcctNature() == null || entity.getAcctNature().equalsIgnoreCase("")) {
                return "Account Nature Does Not Exist";
            } else {
                String acNat = entity.getAcctNature();
                if (acNat.equalsIgnoreCase("FD") || acNat.equalsIgnoreCase("MS")) {
                    Query createNamedQuery = entityManager.createNamedQuery("TDAccountMaster.findByAcno");
                    createNamedQuery.setParameter("aCNo", acno);
                    List<TDAccountMaster> tdList = createNamedQuery.getResultList();
                    if (tdList.size() > 0) {
                        TDAccountMaster tdEntity = tdList.get(0);
                        custName = tdEntity.getCustname();
                        acStatus = tdEntity.getAccStatus();
                    } else {
                        return "Account No Does Not Exist";
                    }
                } else {
                    Query createNamedQuery = entityManager.createNamedQuery("AccountMaster.findByACNo");
                    createNamedQuery.setParameter("aCNo", acno);
                    List<AccountMaster> accList = createNamedQuery.getResultList();
                    if (accList.size() > 0) {
                        AccountMaster acMaster = accList.get(0);
                        custName = acMaster.getCustname();
                        acStatus = acMaster.getAccStatus();

                    } else {
                        return "Account No Does Not Exist";
                    }
                }
            }
            if (acStatus == 9) {
                return "This Account No. Has Been Closed";
            } else {
                List<EPSNodalBranchDetail> epsNodalBranchList = getNodalRTGSDrAcc(paySysId);
                EPSNodalBranchDetail epsEntity = epsNodalBranchList.get(0);
                CrAccId = epsEntity.getOutwardPoolAccId();

                Query createNamedQuery = entityManager.createNamedQuery("Gltable.findByAcNo");
                createNamedQuery.setParameter("acNo", CrAccId);
                Gltable glEntity = (Gltable) createNamedQuery.getSingleResult();
                glcustName = glEntity.getAcName();
            }

        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return custName + ":" + CrAccId + ":" + glcustName + ":" + "bdfn";
    }

    public String getReceiverInstDetails(String bankCode, String branchCode) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.IFSCODE_BY_BANK_BRANCH_CODE);
            createNamedQuery.setParameter("bankCode", bankCode);
            createNamedQuery.setParameter("branchCode", branchCode);
            EPSBranchMaster entity = (EPSBranchMaster) createNamedQuery.getSingleResult();
            return (entity.getIFSCode());
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getOrderingInsDetails(String branchCode) throws ApplicationException {
        String bankCode = "";
        String branchCds = "";
        String ifsCode = "";
        String corrBnkCode = "";
        String corrBranchCode = "";
        String corrIfsCode = "";
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSBranchMaster.findAll");
            List<EPSBranchMaster> branchDetails = createNamedQuery.getResultList();
            if (branchDetails.size() > 0) {
                EPSBranchMaster branDetail = branchDetails.get(0);
                bankCode = branDetail.getBankCode();
                branchCds = branDetail.getBranchCode();
                ifsCode = branDetail.getIFSCode();
            } else {
                return "Branch Details Does Not Exist";
            }

            createNamedQuery = entityManager.createNamedQuery("EPSCorrBranchDetail.findAll");
            List<EPSCorrBranchDetail> corrDetails = createNamedQuery.getResultList();
            if (corrDetails.size() > 0) {
                EPSCorrBranchDetail corrDetail = corrDetails.get(0);
                corrBnkCode = corrDetail.getBankCode();
                corrBranchCode = corrDetail.getBranchCode();
                corrIfsCode = corrDetail.getIFSCode();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return bankCode + ":" + branchCds + ":" + ifsCode + ":" + corrBnkCode + ":" + corrBranchCode + ":" + corrIfsCode + ":" + "hfhj";
    }

    public String saveAllDetails(EPSN06Message entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public EPSN06Message findAllDetailUsingUtr(String utr) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("EPSN06Message.findByUtr");
            createNamedQuery.setParameter("utr", utr);
            EPSN06Message entity = (EPSN06Message) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateTranIdEpsn06Msg(EPSN06Message entity) throws ApplicationException {
        try {
            update(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String saveNeftOwDetails(NeftOwDetails entity) throws ApplicationException {
        try {
            save(entity);
            return "true";
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public List<NeftOwDetails> getNeftUnAuthBatchNos(String orgBrnid, String status, String auth, Date currentDt) throws Exception {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_ALL_DETAIL_CUSTOMER_REF_NO);
            createNamedQuery.setParameter("orgBrnid", orgBrnid);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("auth", auth);
            createNamedQuery.setParameter("currentDt", currentDt);
            List<NeftOwDetails> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public List<NeftOwDetails> getOwUnAuthEntryBranch(Double trsNo, String orgBrnid, String status, String auth, Date currentDt) throws Exception {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_OW_UNAUTH_ENTRY_BRANCH);
            createNamedQuery.setParameter("orgBrnid", orgBrnid);
            createNamedQuery.setParameter("status", status);
            createNamedQuery.setParameter("auth", auth);
            createNamedQuery.setParameter("trsNo", trsNo);
            createNamedQuery.setParameter("currentDt", currentDt);
            List<NeftOwDetails> entityList = createNamedQuery.getResultList();
            return entityList;
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public NeftOwDetails getNeftOwDetails(String custRefNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createNamedQuery("NeftOwDetails.findByUniqueCustomerRefNo");
            createNamedQuery.setParameter("uniqueCustomerRefNo", custRefNo);
            NeftOwDetails entity = (NeftOwDetails) createNamedQuery.getSingleResult();
            return entity;
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public Long getSnoCustHis(String uCRefNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_SNO_NEFT_HIS_CUSTOMER_REF_NO);
            createNamedQuery.setParameter("uCRefNo", uCRefNo);
            if (createNamedQuery.getSingleResult() == null) {
                return Long.parseLong("1");
            } else {
                return Long.parseLong(createNamedQuery.getSingleResult().toString()) + 1;
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String getNeftOwCustName(String debAcNo) throws ApplicationException {
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.GET_CUST_NAME_FOR_DEBIT_AC);
            createNamedQuery.setParameter("acNo", debAcNo);
            return createNamedQuery.getSingleResult().toString();
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
    }

    public String updateNeftOwDetailsList(String custRefNo, String authBy, String auth) throws ApplicationException {
        String msg = "";
        try {
            Query createNamedQuery = entityManager.createQuery(NamedQueryConstant.UPDATE_NEFT_RTGS_DETAILS_AUTH);
            createNamedQuery.setParameter("auth", "Y");
            createNamedQuery.setParameter("authBy", authBy);
            createNamedQuery.setParameter("custRefNo", custRefNo);
            int updateNo = createNamedQuery.executeUpdate();
            if (updateNo > 0) {
                msg = "true";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex);
        }
        return msg;
    }
}
