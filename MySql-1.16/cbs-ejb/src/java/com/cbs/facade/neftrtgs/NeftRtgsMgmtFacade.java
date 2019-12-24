/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.adaptor.ObjectAdaptorCommon;
import com.cbs.constant.CbsAcCodeConstant;
import com.cbs.dto.ReInitiateNftPojo;
import com.cbs.constant.CbsConstant;
import com.cbs.constant.SarvatraImpsEnum;
import com.cbs.dao.master.AbbParameterInfoDAO;
import com.cbs.dao.master.GlTableDAO;
import com.cbs.dao.neftrtgs.ElectronicPaymentSystemDAO;
import com.cbs.dao.neftrtgs.NeftOwDetailsDAO;
import com.cbs.dto.NeftRtgsOutwardPojo;
import com.cbs.dto.neftrtgs.ChannelReplyDto;
import com.cbs.dto.neftrtgs.ImpsOwResponseDto;
import com.cbs.dto.neftrtgs.NeftOwDownloadDTO;
import com.cbs.dto.neftrtgs.SvtImpsMmidCanReqDto;
import com.cbs.dto.neftrtgs.SvtImpsMmidGenReqDto;
import com.cbs.dto.neftrtgs.SvtImpsP2ARequestDto;
import com.cbs.dto.sms.TransferSmsRequestTo;
import com.cbs.entity.master.AbbParameterInfo;
import com.cbs.entity.master.Gltable;
import com.cbs.entity.neftrtgs.AccountTypeMaster;
import com.cbs.entity.neftrtgs.CbsAutoNeftDetails;
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
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.common.InterBranchTxnFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.sms.SmsManagementFacadeRemote;
import com.cbs.ibl.util.IblUtil;
import com.cbs.pojo.neftrtgs.NeftRtgsReversalPojo;
import com.cbs.sms.service.SmsType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import com.cbs.dto.neftrtgs.SvtImpsMmidCanResDto;
import com.cbs.dto.neftrtgs.SvtImpsMmidGenResDto;
import com.cbs.dto.neftrtgs.SvtImpsP2AResponseDto;
import com.cbs.dto.neftrtgs.SvtImpsP2PRequestDto;
import com.cbs.pojo.neftrtgs.NeftDataReconsilationPojo;
import com.cbs.utils.ParseFileUtil;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.xml.bind.Unmarshaller;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Stateless(mappedName = "NeftRtgsMgmtFacade")
@TransactionManagement(value = TransactionManagementType.BEAN)
public class NeftRtgsMgmtFacade implements NeftRtgsMgmtFacadeRemote {

    @PersistenceContext
    private EntityManager entityManager;
    ElectronicPaymentSystemDAO dataManagementDAO = null;
    @Resource
    EJBContext context;
    @Resource
    SessionContext ctx;
    @EJB
    private FtsPostingMgmtFacadeRemote ftsPosting;
    @EJB
    private InterBranchTxnFacadeRemote interBranchFacade;
    @EJB
    private SmsManagementFacadeRemote smsFacade;
    @EJB
    private CommonReportMethodsRemote commonReport;
    @EJB
    private H2HMgmtFacadeRemote h2hFacade;
    float trsno = 0.0f;
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dmyy = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String[] respCodeArr = {"12", "13", "20", "52", "65", "30", "63", "94", "61", "92", "M1", "M2", "M3", "M4", "M5", "M6", "MP", "MU", "MV", "MI", "MK", "ML", "MN"};

    public EPSBranchMaster getEpsBranchMaster(String branchCode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsBranchMaster(branchCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsBranchMaster(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsBranchMaster(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******BranchPaySysDetailBean***********
     */
    public EPSBranchPaySysDetail getEpsBranchPaySysDetail(String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsBranchPaySysDetail(paySysId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsBranchPaySysDetail(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsBranchPaySysDetail(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******EPSbankMasterBean***********
     */
    public EPSBankMaster getEpsBankMaster(String bankCode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsBankMaster(bankCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsBankMaster(EPSBankMaster entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsBankMaster(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsBankMaster(EPSBankMaster entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsBankMaster(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******EFTChargeDetailsBean***********
     */
    public EPSChargeDetails getEpsChargeDetails(String chargeEventID) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsChargeDetails(chargeEventID));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsChargeDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsChargeDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******EftCorrBranchDetailBean***********
     */
    public EPSCorrBranchDetail getEpsCorrbranchDetails(String branchCode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsCorrbranchDetails(branchCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsCorrbranchDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updaeEpsCorrbranchDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******EftNodalBranchDetailBean***********
     */
    public EPSNodalBranchDetail getEpsNodalBranchDetails(String outwardPoolAccId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsNodalBranchDetails(outwardPoolAccId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsNodalBranchDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsNodalBranchDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******MsgCategoryBean***********
     */
    public EPSMessageCategory getEpsMessageCategory(String msgSubType) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getEpsMessageCategory(msgSubType));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveEpsMessageCategory(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateEpsMessageCategory(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String deleteEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.deleteEpsMessageCategory(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******EPSNodalScreenBean***********
     */
    public List<EPSInwardCredit> getNodalGridData(String paySysId, String msgStatus) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getNodalGridData(paySysId, msgStatus));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void saveAck(EPSAckMessage entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            dataManagementDAO.saveAck(entity);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void saveReceivedMessage(EPSInwardCredit entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            dataManagementDAO.saveReceivedMessage(entity);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public EPSN06Message findBySequenceNo(String tranId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.findBySequenceNo(tranId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateBySequenceNo(EPSN06Message entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateBySequenceNo(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public AccountTypeMaster selectAcctNature(String accNo) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        String acctCode = ftsPosting.getAccountCode(accNo);
        try {
            return (dataManagementDAO.getAccountNature(accNo, acctCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public EPSInwardCredit getInwardCrByUTR(String utr) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getInwardCrByUTR(utr));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public void updateInwardCreditByUTR(EPSInwardCredit entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            dataManagementDAO.updateInwardCreditByUTR(entity);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<EPSNodalBranchDetail> getNodalRTGSDrAcc(String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getNodalRTGSDrAcc(paySysId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * ******RTGSCriteriaBean***********
     */
    public List<String> getMessageType(String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getMessage(paySysId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public List<String> getChargeEvent(String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getChargeEventId(paySysId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getAlphaCode(String brcode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getAlphaCode(Integer.parseInt(brcode)));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getUTRNumber(String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getUTRNumber(paySysId));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getDrAccInformation(String acno, String paySysId) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        String acctCode = ftsPosting.getAccountCode(acno);
        try {
            return (dataManagementDAO.getDrAccInfo(acno, paySysId, acctCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getReceiverDetails(String bankCode, String BranchCode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getReceiverInstDetails(bankCode, BranchCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String getOrderingDetails(String BranchCode) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getOrderingInsDetails(BranchCode));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String saveButton(EPSN06Message entity) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.saveAllDetails(entity));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public EPSN06Message onBlurUtrNumber(String utrNo) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.findAllDetailUsingUtr(utrNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateAllInformation(EPSN06Message allData) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.updateTranIdEpsn06Msg(allData));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    //It has been added for inserting the transaction by dinesh
    public String insertTransaction(String addedBy, String modifiedBy, String drAccID, String crAccID, double amount, String particulars, String instrumentType, String instrNo, Date transDt, String chargeEventID, String chargeAccountID) throws ApplicationException {
        String msg = "";
        Integer payBy = null;
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            if (instrumentType.equalsIgnoreCase("VCH")) {
                payBy = 3;
            } else {
                payBy = 1;
            }

            ut.begin();
            trsno = ftsPosting.getTrsNo();
            String drBrnCode = ftsPosting.getCurrentBrnCode(drAccID);
            String crBrnCode = ftsPosting.getCurrentBrnCode(crAccID);
            if (drBrnCode.equalsIgnoreCase("A/C No. does not exist") || crBrnCode.equalsIgnoreCase("A/C No. does not exist")) {
                return "A/C No. does not exist";
            }
            msg = interBranchFacade.cbsPostingCx(drAccID, 1, sdf.format(transDt), amount, 0f, 2, particulars, 0f, "", instrNo, sdf.format(transDt), payBy, 0f, ftsPosting.getRecNo(), 777, drBrnCode, drBrnCode, addedBy, modifiedBy, trsno, "", "");
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                msg = "TRUE";
                AccountTypeMaster entity = selectAcctNature(drAccID);
                String drAcNature = entity.getAcctNature();
                String balUpdateMsg = ftsPosting.updateBalance(drAcNature, drAccID, 0.0f, amount, "Y", "Y");
                if (!(balUpdateMsg.equalsIgnoreCase("TRUE"))) {
                    ut.rollback();
                    return msg;
                }
            } else {
                ut.rollback();
                return msg;
            }

            msg = interBranchFacade.cbsPostingSx(crAccID, 0, sdf.format(transDt), amount, 0f, 2, particulars, 0f, "", instrNo, sdf.format(transDt), payBy, 0f, ftsPosting.getRecNo(), 777, crBrnCode, drBrnCode, addedBy, modifiedBy, trsno, "", "");
            if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                msg = "TRUE";
            } else {
                ut.rollback();
                return msg;
            }

            if ((!chargeEventID.equalsIgnoreCase("")) && (chargeEventID != null)) {
                if ((!chargeAccountID.equalsIgnoreCase("")) && (chargeAccountID != null)) {
                    msg = chargeDeduction(drAccID, chargeEventID, chargeAccountID);
                    if (!(msg.equalsIgnoreCase("TRUE"))) {
                        ut.rollback();
                        return msg;
                    }
                }
            }
            ut.commit();
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public String chargeDeduction(String drAccID, String chargeEventID, String chargeAccountID) throws ApplicationException {
        String msg = "";
        UserTransaction ut = context.getUserTransaction();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String drAcNature = "", crAcNature = "";
            EPSChargeDetails tableObj = getEpsChargeDetails(chargeEventID);
            String chargeCrAcc = tableObj.getPLPlaceHolder();
            double chargeAmt = tableObj.getChargeAmt().doubleValue();
            AccountTypeMaster entity = selectAcctNature(chargeAccountID);
            drAcNature = entity.getAcctNature();
            entity = selectAcctNature(chargeCrAcc);
            crAcNature = entity.getAcctNature();
            float recno = ftsPosting.getRecNo();
            String drBrnCode = ftsPosting.getCurrentBrnCode(drAccID);
            if (drBrnCode.equalsIgnoreCase("A/C No. does not exist")) {
                return "A/C No. does not exist";
            }
            msg = ftsPosting.insertRecons(drAcNature, chargeAccountID, 1, chargeAmt, sdf.format(new Date()), sdf.format(new Date()), 2, "EPS charges entry", "SYSTEM", trsno, sdf.format(new Date()), recno, "Y", "System", 777, 3, "", null, 0.0f, "", "", 8888, "", 0.0f, "", "", drBrnCode, drBrnCode, 0, "", "", "");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                ut.rollback();
                return msg;
            }

            msg = ftsPosting.updateBalance(drAcNature, chargeAccountID, 0.0f, chargeAmt, "Y", "Y");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                ut.rollback();
                return msg;
            }

            msg = ftsPosting.insertRecons(crAcNature, chargeCrAcc, 0, chargeAmt, sdf.format(new Date()), sdf.format(new Date()), 2, "EPS charges entry", "SYSTEM", trsno, sdf.format(new Date()), recno, "Y", "System", 777, 3, "", null, 0.0f, "", "", 8888, "", 0.0f, "", "", drBrnCode, drBrnCode, 0, "", "", "");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                ut.rollback();
                return msg;
            }

            msg = ftsPosting.updateBalance(crAcNature, chargeCrAcc, chargeAmt, 0.0f, "Y", "Y");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                ut.rollback();
                return msg;
            }
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public List<String> getCashInHandAccInfo(String paySysId, String orgnBrCode) throws ApplicationException {
        List<String> cashInfoList = new ArrayList<String>();
        String outwardPoolAcc = "", drAcc = "";
        try {
            List<EPSNodalBranchDetail> nodalList = getNodalRTGSDrAcc(paySysId);
            if (nodalList.size() > 0) {
                EPSNodalBranchDetail entity = nodalList.get(0);
                outwardPoolAcc = entity.getOutwardPoolAccId();
            }

            // If you want to change CASH IN HAND then change will affect only here according to your entry in abb_parameter_info.
            List drAccList = entityManager.createNativeQuery("select acno from abb_parameter_info where purpose='CASH IN HAND' and substring(acno,1,2)='" + orgnBrCode + "'").getResultList();
            if (drAccList.size() > 0) {
                for (int i = 0; i < drAccList.size(); i++) {
                    Vector element = (Vector) drAccList.get(i);
                    drAcc = element.get(0).toString();
                }
            }
            cashInfoList.add(drAcc);
            cashInfoList.add("CASH IN HAND");
            cashInfoList.add(outwardPoolAcc);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return cashInfoList;
    }

    @Override
    public String saveNeftOwDetails(List<NeftOwDetails> entityList, String orgnCode, String userName,
            String todayDt) throws Exception {
        UserTransaction ut = context.getUserTransaction();
        String result = "true";
        try {
            ut.begin();
            String debitAccountNo = "", chqNo = "";
            BigDecimal debitAmount = BigDecimal.ZERO, chargeAmount = BigDecimal.ZERO;
            String outwardTxnsInsertQuery = "insert into neft_ow_details(paymenttype,uniquecustomerrefno,beneficiaryname,"
                    + "beneficiarycode,txnamt,paymentdate,creditaccountno,outsidebankifsccode,debitaccountno,"
                    + "beneficiaryemailid,beneficiarymobileno,cmsbankrefno,utrno,instno,instdate,dt,trantime,status,"
                    + "reason,details,orgbrnid,destbrnid,auth,enterby,authby,chargetype,chargeamount,file_name,"
                    + "sender_comm_mode_type,sender_comm_mode,remmit_info,outward_file_name,cpsms_messageid,cpsms_batch_no,"
                    + "cpsmstranid_cr_tran_id,debit_success_trsno,response_update_time,success_to_failure_flag,"
                    + "debit_amount,trs_no,Initiated_Bank_Name) values";
            String outwardTxnsDataQuery = "";
            for (int i = 0; i < entityList.size(); i++) {
                NeftOwDetails entity = entityList.get(i);
                if (i == 0) {
                    debitAccountNo = entity.getDebitAccountNo();
                    debitAmount = entity.getDebitAmount();
                    chargeAmount = entity.getChargeAmount();
                    chqNo = entity.getInstNo();
                }

                if (outwardTxnsDataQuery.equals("")) {
                    outwardTxnsDataQuery = "('" + entity.getPaymentType() + "','" + entity.getUniqueCustomerRefNo() + "',"
                            + "'" + entity.getBeneficiaryName() + "','" + entity.getBeneficiaryCode() + "',"
                            + "" + entity.getTxnAmt() + ",'" + entity.getPaymentDate() + "','" + entity.getCreditAccountNo() + "',"
                            + "'" + entity.getOutsideBankIfscCode() + "','" + entity.getDebitAccountNo() + "',"
                            + "'" + entity.getBeneficiaryEmailId() + "','" + entity.getBeneficiaryMobileNo() + "',"
                            + "'" + entity.getCmsBankRefNo() + "','" + entity.getUtrNo() + "','" + entity.getInstNo() + "',"
                            + "'" + ymd.format(entity.getInstDate()) + "','" + ymd.format(entity.getDt()) + "',now(),"
                            + "'" + entity.getStatus() + "','" + entity.getReason() + "','" + entity.getDetails() + "',"
                            + "'" + entity.getOrgBrnid() + "','" + entity.getDestBrnid() + "','" + entity.getAuth() + "',"
                            + "'" + entity.getEnterBy() + "','" + entity.getAuthby() + "','" + entity.getChargeType() + "',"
                            + "" + entity.getChargeAmount() + ",'" + entity.getFileName() + "',"
                            + "'" + entity.getSenderCommModeType() + "','" + entity.getSenderCommMode() + "',"
                            + "'" + entity.getRemmitInfo() + "','" + entity.getOutwardFileName() + "',"
                            + "'" + entity.getCPSMSMessageId() + "','" + entity.getCPSMSBatchNo() + "',"
                            + "'" + entity.getCPSMSTranIdCrTranId() + "'," + entity.getDebitSuccessTrsno() + ","
                            + "now(),'" + entity.getSuccessToFailureFlag() + "',"
                            + "" + entity.getDebitAmount() + "," + entity.getTrsNo() + ",'" + entity.getInitiatedBankName() + "')";
                } else {
                    outwardTxnsDataQuery = outwardTxnsDataQuery + ",('" + entity.getPaymentType() + "','" + entity.getUniqueCustomerRefNo() + "',"
                            + "'" + entity.getBeneficiaryName() + "','" + entity.getBeneficiaryCode() + "',"
                            + "" + entity.getTxnAmt() + ",'" + entity.getPaymentDate() + "','" + entity.getCreditAccountNo() + "',"
                            + "'" + entity.getOutsideBankIfscCode() + "','" + entity.getDebitAccountNo() + "',"
                            + "'" + entity.getBeneficiaryEmailId() + "','" + entity.getBeneficiaryMobileNo() + "',"
                            + "'" + entity.getCmsBankRefNo() + "','" + entity.getUtrNo() + "','" + entity.getInstNo() + "',"
                            + "'" + ymd.format(entity.getInstDate()) + "','" + ymd.format(entity.getDt()) + "',now(),"
                            + "'" + entity.getStatus() + "','" + entity.getReason() + "','" + entity.getDetails() + "',"
                            + "'" + entity.getOrgBrnid() + "','" + entity.getDestBrnid() + "','" + entity.getAuth() + "',"
                            + "'" + entity.getEnterBy() + "','" + entity.getAuthby() + "','" + entity.getChargeType() + "',"
                            + "" + entity.getChargeAmount() + ",'" + entity.getFileName() + "',"
                            + "'" + entity.getSenderCommModeType() + "','" + entity.getSenderCommMode() + "',"
                            + "'" + entity.getRemmitInfo() + "','" + entity.getOutwardFileName() + "',"
                            + "'" + entity.getCPSMSMessageId() + "','" + entity.getCPSMSBatchNo() + "',"
                            + "'" + entity.getCPSMSTranIdCrTranId() + "'," + entity.getDebitSuccessTrsno() + ","
                            + "now(),'" + entity.getSuccessToFailureFlag() + "',"
                            + "" + entity.getDebitAmount() + "," + entity.getTrsNo() + ",'" + entity.getInitiatedBankName() + "')";
                }
            }

            outwardTxnsInsertQuery = outwardTxnsInsertQuery + outwardTxnsDataQuery;
            int n = entityManager.createNativeQuery(outwardTxnsInsertQuery).executeUpdate();
            if (n <= 0) {
                throw new ApplicationException("Problem in outward transaction insertion");
            }

            String accountNature = ftsPosting.getAccountNature(debitAccountNo);
            double amountToBeDebit = debitAmount.add(chargeAmount).doubleValue();
            result = ftsPosting.updateBalance(accountNature, debitAccountNo, 0, amountToBeDebit, "Y", "Y");
            if (!result.equalsIgnoreCase("true")) {
                throw new ApplicationException("Problem in balance updation for account number " + debitAccountNo);
            }
            if (!(chqNo == null || chqNo.equals("") || chqNo.length() == 0)) {
                result = ftsPosting.updateCheque(debitAccountNo, 1, 1, Float.parseFloat(chqNo), userName);
                if (!result.equalsIgnoreCase("true")) {
                    throw new ApplicationException("Problem in cheque updation for account number " + debitAccountNo);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public List getNeftUnAuthBatchNos(String orgBrnid, String status, String auth,
            String currentDt) throws Exception {
        List batchList = new ArrayList();
        try {
            batchList = entityManager.createNativeQuery("select distinct(trs_no) from neft_ow_details where "
                    + "orgbrnid='" + orgBrnid + "' and status='" + status + "' and auth='" + auth + "' and "
                    + "dt='" + currentDt + "'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return batchList;
    }

    @Override
    public List<NeftOwDetails> getOwUnAuthEntryBranch(Double trsNo, String orgBrnid, String status, String auth, String currentDt) throws Exception {
        List<NeftOwDetails> resultList = new ArrayList<>();
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            resultList = dataManagementDAO.getOwUnAuthEntryBranch(trsNo, orgBrnid, status, auth, ymd.parse(currentDt));
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return resultList;
    }

    @Override
    public String deleteNeftOwDetails(List<NeftRtgsOutwardPojo> neftOwDetailList, String orgnCode,
            String userName, String todayDt) throws Exception {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String msg = "true";
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String debitAccountNo = "", chqNo = "";
            BigDecimal debitAmount = BigDecimal.ZERO, chargeAmount = BigDecimal.ZERO;
            Float trsNo = 0f;
            for (int i = 0; i < neftOwDetailList.size(); i++) {
                NeftRtgsOutwardPojo pojo = neftOwDetailList.get(i);
                if (i == 0) {
                    debitAccountNo = pojo.getDebitAccountNo();
                    debitAmount = pojo.getDebitAmount();
                    chargeAmount = pojo.getChargeAmount();
                    chqNo = pojo.getInstNo();
                    trsNo = pojo.getTrsNo();
                }
                List list = entityManager.createNativeQuery("select ifnull(max(sno),0)+1 from neft_ow_details_his "
                        + "where uniquecustomerrefno='" + pojo.getUniqueCustomerRefNo() + "'").getResultList();
                Vector ele = (Vector) list.get(0);
                long sNo = Long.parseLong(ele.get(0).toString());

                int n = entityManager.createNativeQuery("insert into neft_ow_details_his(paymenttype,uniquecustomerrefno,"
                        + "sno,beneficiaryname,beneficiarycode,txnamt,paymentdate,creditaccountno,outsidebankifsccode,"
                        + "debitaccountno,beneficiaryemailid,beneficiarymobileno,cmsbankrefno,utrno,instno,instdate,dt,"
                        + "trantime,status,reason,details,orgbrnid,destbrnid,enterby,modifiedby,chargetype,chargeamount,"
                        + "file_name,sender_comm_mode_type,sender_comm_mode,remmit_info,outward_file_name,cpsms_messageid,"
                        + "cpsms_batch_no,cpsmstranid_cr_tran_id,debit_success_trsno,response_update_time,success_to_failure_flag,"
                        + "debit_amount,trs_no,Initiated_Bank_Name) values('" + pojo.getPaymentType() + "','" + pojo.getUniqueCustomerRefNo() + "',"
                        + "" + sNo + ",'" + pojo.getBeneficiaryName() + "','" + pojo.getBeneficiaryCode() + "',"
                        + "" + pojo.getNeftAmount() + ",'" + pojo.getPaymentDate() + "','" + pojo.getCreditAccountNo() + "',"
                        + "'" + pojo.getBeneficiaryIfsc() + "','" + pojo.getDebitAccountNo() + "',"
                        + "'" + pojo.getBeneficiaryEmailId() + "','" + pojo.getBeneficiaryMobileNo() + "',"
                        + "'" + pojo.getCmsBankRefNo() + "','" + pojo.getUtrNo() + "','" + pojo.getInstNo() + "',"
                        + "'" + ymd.format(dmy.parse(pojo.getInstDate())) + "','" + pojo.getDt() + "',"
                        + "'" + dateTimeFormat.format(pojo.getTranTime()) + "','" + pojo.getStatus() + "','" + pojo.getReason() + "',"
                        + "'" + pojo.getDetails() + "','" + pojo.getOrgnId() + "','" + pojo.getDestbrnId() + "',"
                        + "'" + pojo.getEnterBy() + "','" + userName + "','" + pojo.getChargeType() + "',"
                        + "" + pojo.getChargeAmount() + ",'" + pojo.getFileName() + "','" + pojo.getSenderCommModeType() + "',"
                        + "'" + pojo.getSenderCommMode() + "','" + pojo.getRemmitInfo() + "','" + pojo.getOutwardFileName() + "',"
                        + "'" + pojo.getCpsmsMessageId() + "','" + pojo.getCpsmsBatchNo() + "',"
                        + "'" + pojo.getCpsmsTranIdCrTranId() + "'," + pojo.getDebitSuccessTrsno() + ","
                        + "now(),'" + pojo.getSuccessToFailureFlag() + "',"
                        + "" + pojo.getDebitAmount() + "," + pojo.getTrsNo() + ",'" + pojo.getNeftBankName() + "')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in outward history maintenance.");
                }
            }

            int n = entityManager.createNativeQuery("delete from neft_ow_details where status='P' and auth='N' and "
                    + "orgbrnid='" + orgnCode + "' and trs_no=" + trsNo + " and dt='" + ymd.format(dmy.parse(todayDt)) + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in outward transaction deletion.");
            }

            //Debit Transaction Reversal
            String accountNature = ftsPosting.getAccountNature(debitAccountNo);
            double amountToBeReversed = debitAmount.add(chargeAmount).doubleValue();
            msg = ftsPosting.updateBalance(accountNature, debitAccountNo, amountToBeReversed, 0, "Y", "Y");
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException("Problem in balance updation for account number " + debitAccountNo);
            }
            if (!(chqNo == null || chqNo.equals("") || chqNo.length() == 0)) {
                msg = makeChequeNoFresh(accountNature, debitAccountNo, chqNo, userName);
                if (!msg.equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg + " " + debitAccountNo);
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return msg;
    }

    public String getNeftOwCustName(String debAcNo) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getNeftOwCustName(debAcNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String verifyNeftOwDetails(List<NeftRtgsOutwardPojo> neftOwDetailList, String orgnCode,
            String userName, String todayDt) throws Exception {
        String msg = "true";
        Date curDt = new Date();
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            String debitAccountNo = "", chqNo = "", instDt = "", initiatedBankName = "";
            BigDecimal debitAmount = BigDecimal.ZERO, chargeAmount = BigDecimal.ZERO;
            Float trsNo = 0f;
            String details = "", individualEntryDetail = "";
            int trandesc = 0;

            NeftRtgsOutwardPojo obj = neftOwDetailList.get(0); //First object of complete batch
            debitAccountNo = obj.getDebitAccountNo();
            debitAmount = obj.getDebitAmount();
            chargeAmount = obj.getChargeAmount();
            chqNo = obj.getInstNo();
            trsNo = obj.getTrsNo();
            initiatedBankName = obj.getNeftBankName();

            if (neftOwDetailList.size() == 1) {
                details = ": " + obj.getBeneficiaryName();
            } else {
                details = ": " + obj.getBeneficiaryName() + " and others";
            }

            if (userName.equalsIgnoreCase(obj.getEnterBy())) {
                throw new Exception("You can not authorize your own entry.");
            }
            List list = entityManager.createNativeQuery("select distinct auth from neft_ow_details where "
                    + "orgbrnid='" + orgnCode + "' and trs_no=" + trsNo + " and "
                    + "dt='" + ymd.format(dmy.parse(todayDt)) + "'").getResultList();
            if (list.isEmpty() || list.size() != 1) {
                throw new Exception("There is no batch to authorize and/or this batch is not authorized properly.");
            }
            Vector ele = (Vector) list.get(0);
            String auth = ele.get(0).toString();
            if (auth.equalsIgnoreCase("Y")) {
                throw new Exception("This batch has been already verified.");
            }

            list = entityManager.createNativeQuery("select coalesce(trandebit,0.0),levelid from "
                    + "securityinfo where userid='" + userName + "' and brncode='" + orgnCode + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("Your passing limit is less than withdrawal amount. You can not authorize this transaction");
            }

            Vector element = (Vector) list.get(0);
            double userLimit = Double.parseDouble(element.get(0).toString());
            int level = Integer.parseInt(element.get(1).toString());
            if (!(level == 1 || level == 2)) {
                throw new Exception("You are not permitted to authorize this transaction");
            } else if (userLimit == 0.0 || debitAmount.doubleValue() > userLimit) {
                throw new Exception("Your passing limit is less than withdrawal amount. You can not authorize this transaction");
            }

            Integer payBy = 3;
            if (!(chqNo == null || chqNo.equals("") || chqNo.length() == 0)) {
                payBy = 1;
                instDt = ymd.format(dmy.parse(obj.getInstDate()));
                chqNo = ftsPosting.lPading(chqNo, 10, "0");
            }

            String glAccount = "";
            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                glAccount = ftsPosting.getCodeFromCbsParameterInfo("IMPS-OW-HEAD");
                trandesc = 76;
            } else {
//                glAccount = findAutoNeftDetailForOutward().getOwHead(); //Ho Outward Head(It will be HO account no)
                glAccount = findOutwardHeadByNeftBankName(initiatedBankName); //Ho Outward Head(It will be HO account no)
                trandesc = 66;
            }

            String partyBrnCode = ftsPosting.getCurrentBrnCode(debitAccountNo);
            String loginAlphaCode = commonReport.getAlphacodeByBrncode(orgnCode);

            String isoHead = ftsPosting.getAcnoByPurpose("INTERSOLE ACCOUNT");
            isoHead = isoHead.trim();
            if (isoHead.equals("") || isoHead.length() != 10) {
                throw new Exception("Please check INTERSOLE ACCOUNT head in Abb Parameter Info.");
            }

            //Charges deduction if applicable.
            if (chargeAmount.compareTo(new BigDecimal("0")) == 1) {
                msg = performChargeTransaction(ObjectAdaptorCommon.adaptToNeftOwDetailsEntity(obj),
                        userName, orgnCode, trsNo, partyBrnCode, isoHead);
                if (!msg.equalsIgnoreCase("true")) {
                    throw new Exception(msg);
                }
            }

            if (loginAlphaCode.equalsIgnoreCase("HO")) {
                if (orgnCode.equalsIgnoreCase(partyBrnCode)) {  //Local
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS " : "Outward Neft-Rtgs ";

                    msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(debitAccountNo), debitAccountNo, 1,
                            debitAmount.doubleValue(), ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail + details,
                            obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, payBy, chqNo, instDt,
                            0f, "", "", 0, "", 0f, "", "", orgnCode, orgnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS, Debit A/c:" : "Outward Neft-Rtgs, Debit A/c:";

                    msg = ftsPosting.insertRecons("PO", glAccount, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail + debitAccountNo, obj.getEnterBy(), trsNo, "",
                            ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "",
                            orgnCode, orgnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    msg = ftsPosting.updateBalance("PO", glAccount, debitAmount.doubleValue(), 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                } else {    //Remote
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS " : "Outward Neft-Rtgs ";
                    msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(debitAccountNo), debitAccountNo, 1,
                            debitAmount.doubleValue(), ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail + details,
                            obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, payBy, chqNo, instDt,
                            0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS, Debit A/c:" : "Outward Neft-Rtgs, Debit A/c:";
                    msg = ftsPosting.insertRecons("PO", glAccount, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail + debitAccountNo, obj.getEnterBy(), trsNo, "",
                            ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "",
                            orgnCode, orgnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    msg = ftsPosting.updateBalance("PO", glAccount, debitAmount.doubleValue(), 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                    //Iso Entry
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", partyBrnCode + isoHead, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Form Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", orgnCode + isoHead, 1, debitAmount.doubleValue(),
                            ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(),
                            trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "",
                            "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                }
            } else {    //Branch Level
                String hoBranchCode = commonReport.getBrncodeByAlphacode("HO");
                if (hoBranchCode.equals("") || hoBranchCode.trim().length() != 2) {
                    throw new Exception("There should be head office entry in branch master.");
                }
                if (orgnCode.equalsIgnoreCase(partyBrnCode)) {  //Branch Local
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS " : "Outward Neft-Rtgs ";
                    msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(debitAccountNo), debitAccountNo, 1,
                            debitAmount.doubleValue(), ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail + details,
                            obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, payBy, chqNo, instDt,
                            0f, "", "", 0, "", 0f, "", "", orgnCode, orgnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS, Debit A/c:" : "Outward Neft-Rtgs, Debit A/c:";
                    msg = ftsPosting.insertRecons("PO", glAccount, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail + debitAccountNo, obj.getEnterBy(), trsNo, "",
                            ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "",
                            orgnCode, hoBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    msg = ftsPosting.updateBalance("PO", glAccount, debitAmount.doubleValue(), 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                    //Iso Entry
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", orgnCode + isoHead, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", orgnCode, hoBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", hoBranchCode + isoHead, 1, debitAmount.doubleValue(),
                            ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(),
                            trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "",
                            "", 0, "", 0f, "", "", orgnCode, hoBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                } else {    //Branch Remote
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS " : "Outward Neft-Rtgs ";
                    msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(debitAccountNo), debitAccountNo, 1,
                            debitAmount.doubleValue(), ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail + details,
                            obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, payBy, chqNo, instDt,
                            0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS, Debit A/c:" : "Outward Neft-Rtgs, Debit A/c:";
                    msg = ftsPosting.insertRecons("PO", glAccount, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail + debitAccountNo, obj.getEnterBy(), trsNo, "",
                            ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "",
                            orgnCode, hoBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    msg = ftsPosting.updateBalance("PO", glAccount, debitAmount.doubleValue(), 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    //Iso Entry
                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", partyBrnCode + isoHead, 0, debitAmount.doubleValue(), ymd.format(curDt),
                            ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", userName, trandesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", orgnCode, partyBrnCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }

                    individualEntryDetail = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "Outward IMPS" : "Outward Neft-Rtgs";
                    msg = ftsPosting.insertRecons("PO", hoBranchCode + isoHead, 1, debitAmount.doubleValue(),
                            ymd.format(curDt), ymd.format(curDt), 2, individualEntryDetail, obj.getEnterBy(),
                            trsNo, "", ftsPosting.getRecNo(), "Y", userName, trandesc, 3, "", "", 0f, "",
                            "", 0, "", 0f, "", "", orgnCode, hoBranchCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        throw new Exception(msg);
                    }
                }
            }
            int n = entityManager.createNativeQuery("update neft_ow_details set auth='Y',authby='" + userName + "',"
                    + "details='Verified' where orgbrnid='" + orgnCode + "' and trs_no=" + trsNo + " and "
                    + "dt='" + ymd.format(dmy.parse(todayDt)) + "'").executeUpdate();
            if (n <= 0) {
                throw new Exception("Problem in outward txn verification.");
            }

            ftsPosting.lastTxnDateUpdation(debitAccountNo);
            //Commented By Dinesh Pratap Singh On Date 18/11/2019
//            String logUrl = "", txnUrl = "", impsBin = "";
//            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
//                try {
//                    List impsDetailList = entityManager.createNativeQuery("select ifnull(a.code,'') as imps_vendor,ifnull(b.code,'') "
//                            + "as imps_log_url,ifnull(c.code,'') as imps_txn_url,ifnull(d.code,'') as imps_bank_bin from "
//                            + "(select ifnull((select code from cbs_parameterinfo  where name='IMPS-VENDOR'),'') as code) a,"
//                            + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-LOG-URL'),'') as code) b,"
//                            + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-TXN-URL'),'') as code) c,"
//                            + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-BANK-BIN'),'') as code) d").getResultList();
//                    if (impsDetailList.isEmpty()) {
//                        throw new Exception("Please define IMPS detail.");
//                    }
//                    Vector elem = (Vector) impsDetailList.get(0);
//                    String impsVendor = elem.get(0).toString().trim();
//                    if (impsVendor.equals("") || impsVendor.trim().length() == 0) {
//                        throw new Exception("Please define IMPS Vendor.");
//                    }
//
//                    logUrl = elem.get(1).toString().trim();
//                    txnUrl = elem.get(2).toString().trim();
//                    impsBin = elem.get(3).toString().trim();
//                    if (logUrl.equals("") || txnUrl.equals("") || impsBin.trim().length() != 4) {
//                        throw new Exception("Please define proper IMPS detail like Log Url,Txn Url and IMPS BIN");
//                    }
//
//                    System.out.println("Logged Request-->" + new Date());
//                    ChannelReplyDto resultObj = sendImpsRequestByGetMethod(obj, impsBin, userName, "logged", logUrl, txnUrl); //For GET
////                    ChannelReplyDto resultObj = sendImpsRequestByPostMethod(obj, impsBin, userName); //For POST
//
//                    if (resultObj == null || resultObj.getRespCode() == null) {
//                        throw new Exception("No response found !");
//                    }
//
//                    String uniqueBankRefNo = (resultObj.getUniqueBankRefNo() == null || resultObj.getUniqueBankRefNo().trim().equals(""))
//                            ? "" : resultObj.getUniqueBankRefNo().trim();
//                    if (resultObj.getRespCode().trim().equalsIgnoreCase("00")) {
//                        n = entityManager.createNativeQuery("update neft_ow_details set status='L',details='Logged',"
//                                + "cpsms_batch_no='" + uniqueBankRefNo + "' where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
//                        if (n <= 0) {
//                            throw new Exception("Problem in outward txn verification.");
//                        }
//                    } else {
//                        ut.rollback();
//                        ut.begin();
//                        n = entityManager.createNativeQuery("update neft_ow_details set auth='N',authby='" + userName + "',"
//                                + "details='" + resultObj.getAddDesc() + "',cpsms_batch_no='" + uniqueBankRefNo + "' where "
//                                + "UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
//                        if (n <= 0) {
//                            throw new Exception("Problem in outward txn verification.");
//                        }
//                        msg = "Transaction not verified in log mode !";
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    throw new Exception(ex.getMessage());
//                }
//            }
//            ut.commit();
            //Commented By Dinesh Pratap Singh On Date 18/11/2019 End Here
            String logUrl = "", txnUrl = "", impsBin = "";
            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                List impsDetailList = entityManager.createNativeQuery("select ifnull(a.code,'') as imps_vendor,ifnull(b.code,'') "
                        + "as imps_log_url,ifnull(c.code,'') as imps_txn_url,ifnull(d.code,'') as imps_bank_bin from "
                        + "(select ifnull((select code from cbs_parameterinfo  where name='IMPS-VENDOR'),'') as code) a,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-LOG-URL'),'') as code) b,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-TXN-URL'),'') as code) c,"
                        + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-BANK-BIN'),'') as code) d").getResultList();
                if (impsDetailList.isEmpty()) {
                    throw new Exception("Please define IMPS detail.");
                }
                Vector elem = (Vector) impsDetailList.get(0);
                String impsVendor = elem.get(0).toString().trim();
                if (impsVendor.equals("") || impsVendor.trim().length() == 0) {
                    throw new Exception("Please define IMPS Vendor.");
                }

                logUrl = elem.get(1).toString().trim();
                txnUrl = elem.get(2).toString().trim();
                impsBin = elem.get(3).toString().trim();
                if (logUrl.equals("") || txnUrl.equals("") || impsBin.trim().length() != 4) {
                    throw new Exception("Please define proper IMPS detail like Log Url,Txn Url and IMPS BIN");
                }
            }

            ut.commit(); //Transactions either it is neft or ipms both will be commit here

            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                try {
                    ut.begin();

                    ChannelReplyDto resultObj = sendImpsRequestByGetMethod(obj, impsBin, userName, "logged", logUrl, txnUrl); //For GET
//                    ChannelReplyDto resultObj = sendImpsRequestByPostMethod(obj, impsBin, userName); //For POST
                    if (resultObj == null || resultObj.getUniqueBankRefNo() == null || resultObj.getRespCode() == null
                            || resultObj.getUniqueBankRefNo().trim().equals("") || resultObj.getRespCode().trim().equalsIgnoreCase("")) {
                        n = entityManager.createNativeQuery("update neft_ow_details set status='W',"
                                + "details='Awaiting Response' where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                    } else {
                        String uniqueBankRefNo = resultObj.getUniqueBankRefNo().trim();
                        if (resultObj.getRespCode().trim().equalsIgnoreCase("00")) {
                            n = entityManager.createNativeQuery("update neft_ow_details set status='L',details='Logged',"
                                    + "cpsms_batch_no='" + uniqueBankRefNo + "' where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                        } else {
                            n = entityManager.createNativeQuery("update neft_ow_details set status='W',"
                                    + "authby='" + userName + "',details='" + resultObj.getAddDesc() + "',"
                                    + "cpsms_batch_no='" + uniqueBankRefNo + "' where "
                                    + "UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                        }
                    }
                    if (n <= 0) {
                        throw new Exception("Error response in sending the IMPS first time requset.");
                    }
                    ut.commit();
                } catch (Exception ex) {
                    ut.rollback();
                    ex.printStackTrace();
                }
            }
            try {
                if (!obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                    System.out.println("A/c No is-->" + debitAccountNo);
                    //Commented By Dhirendra Singh to new SMS for NEFT RTGS
//                    smsFacade.sendTransactionalSms(SmsType.TRANSFER_WITHDRAWAL, debitAccountNo, 2, 1,
//                            debitAmount.doubleValue(), obj.getPaymentDate(), "");
                     smsFacade.sendNeftRtgsTxnSms(SmsType.OW_NEFT_RTGS_DEBIT, debitAccountNo, 2,1, 
                            debitAmount.doubleValue(), obj.getPaymentDate(), obj.getPaymentType(), obj.getBeneficiaryName());
                }
            } catch (Exception ex) {
                System.out.println("Error SMS Sending-->A/c is::" + debitAccountNo + " And Amount is::"
                        + debitAmount.doubleValue());
            }
            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                System.out.println("IMPS Txn Request before calling the async method-->" + new Date());
                ctx.getBusinessObject(NeftRtgsMgmtFacadeRemote.class).getImpsTxnStatus(obj, impsBin, userName, logUrl, txnUrl);
            }
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new Exception(ex.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
        return msg;
    }

    @Asynchronous
    @Override
    public void getImpsTxnStatus(NeftRtgsOutwardPojo obj, String impsBin, String userName, String logUrl, String txnUrl) {
        UserTransaction ut = context.getUserTransaction();
        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
        try {
            Thread.sleep(5000);
            List statusList = entityManager.createNativeQuery("select status from neft_ow_details where "
                    + "uniquecustomerrefno='" + obj.getUniqueCustomerRefNo() + "'").getResultList();
            if (statusList == null || statusList.isEmpty()) {
                throw new Exception("There is no data found to send the IMPS Txn request.");
            }
            Vector ele = (Vector) statusList.get(0);
            String status = ele.get(0).toString();
//            if (!status.equalsIgnoreCase("L")) {
//                throw new Exception("Status is not proper of IMPS Txn Entry.");
//            }

            if (!(status.equalsIgnoreCase("L") || status.equalsIgnoreCase("W"))) {
                throw new Exception("Status is not proper of IMPS Txn Entry.");
            }

            System.out.println("IMPS Txn Request-->" + new Date());
            ChannelReplyDto resultObj = sendImpsRequestByGetMethod(obj, impsBin, userName, "txn", logUrl, txnUrl); //For GET
            if (resultObj == null || resultObj.getRespCode() == null) {
                throw new Exception("No response found !");
            }
            String uniqueBankRefNo = (resultObj.getUniqueBankRefNo() == null || resultObj.getUniqueBankRefNo().trim().equals(""))
                    ? "" : resultObj.getUniqueBankRefNo().trim();

            int n;
            int smsCode = ftsPosting.getCodeForReportName("IMPS-SMS");
            if (resultObj.getRespCode().trim().equalsIgnoreCase("00")
                    || resultObj.getRespCode().trim().equalsIgnoreCase("91")) {
                try {
                    ut.begin();
                    n = entityManager.createNativeQuery("update neft_ow_details set status='S',details='Paid',"
                            + "cpsms_batch_no='" + uniqueBankRefNo + "' where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in outward txn sucess status updation.");
                    }
                    //Sending SMS
                    try {
                        if (smsCode == 1) {
                            List<TransferSmsRequestTo> smsList = new ArrayList<>();
                            String mobileNo = getAccountMobileNo(obj.getDebitAccountNo().trim()).trim();
                            if (mobileNo.length() == 13) {
                                TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                                trfSmsTo.setAcno(obj.getDebitAccountNo());
                                trfSmsTo.setPromoMobile(mobileNo);
                                trfSmsTo.setAmount(obj.getNeftAmount().doubleValue());
                                trfSmsTo.setDate(obj.getPaymentDate());
                                trfSmsTo.setUserMsgId(obj.getDebitAccountNo().substring(0, 10) + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
                                trfSmsTo.setModuleName("IMPS");
                                trfSmsTo.setTxnType("W"); //Withdrawal
                                trfSmsTo.setFirstCheque("42"); //IMPS outward processing code
                                trfSmsTo.setLastCheque(obj.getCreditAccountNo().trim() + "/" + resultObj.getUniqueBankRefNo().trim() + "/0");
                                smsList.add(trfSmsTo);
                            }
                            smsFacade.sendAtmSms(smsList);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending Paid IMPS SMS." + ex.getMessage());
                    }
                    //End Here
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    System.out.println("Erorr in Async Method= " + e.getMessage());
                    e.printStackTrace();
                }
            } else if (resultObj.getRespCode().trim().equalsIgnoreCase("03")
                    || resultObj.getRespCode().trim().equalsIgnoreCase("3")) {
                try {
                    ut.begin();
                    n = entityManager.createNativeQuery("update neft_ow_details set status='L',details='" + resultObj.getAddDesc() + "',"
                            + "cpsms_batch_no='" + uniqueBankRefNo + "' where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in outward txn under process status updation.");
                    }
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    System.out.println("Erorr in Async Method= " + e.getMessage());
                    e.printStackTrace();
                }
                getImpsTxnStatus(obj, impsBin, userName, logUrl, txnUrl);
            } else if (isTxnReversed(resultObj.getRespCode().trim())) {
                try {
                    ut.begin();
                    NeftOwDetails neftOwDetailsObj = neftOwDetailsDAO.getSingleNeftOwDetailsInstrument(obj.getUniqueCustomerRefNo().trim());
                    if (neftOwDetailsObj != null && !neftOwDetailsObj.getStatus().equalsIgnoreCase("U")) {
                        String msg = h2hFacade.reverseOutwardTransaction(neftOwDetailsObj, "90", "System", null);
                        if (msg.substring(0, 4).equalsIgnoreCase("true")) {
                            n = entityManager.createNativeQuery("update neft_ow_details set status='U',details='Un-Paid',reason='"
                                    + resultObj.getRespCode() + ":" + resultObj.getAddDesc() + "',cpsms_batch_no='" + uniqueBankRefNo + "' "
                                    + "where UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                            if (n <= 0) {
                                throw new Exception("Problem in outward txn verification.");
                            }
                        }
                    }
                    //Sending SMS
                    try {
                        if (smsCode == 1) {
                            List<TransferSmsRequestTo> smsList = new ArrayList<>();
                            String mobileNo = getAccountMobileNo(obj.getDebitAccountNo().trim()).trim();
                            if (mobileNo.length() == 13) {
                                TransferSmsRequestTo trfSmsTo = new TransferSmsRequestTo();
                                trfSmsTo.setAcno(obj.getDebitAccountNo());
                                trfSmsTo.setPromoMobile(mobileNo);
                                trfSmsTo.setAmount(obj.getNeftAmount().doubleValue());
                                trfSmsTo.setDate(obj.getPaymentDate());
                                trfSmsTo.setUserMsgId(obj.getDebitAccountNo().substring(0, 10) + new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
                                trfSmsTo.setModuleName("IMPS");
                                trfSmsTo.setTxnType("R"); //Reversal
                                trfSmsTo.setFirstCheque("42"); //IMPS outward processing code
                                trfSmsTo.setLastCheque(obj.getCreditAccountNo().trim() + "/" + resultObj.getUniqueBankRefNo().trim() + "/0");
                                smsList.add(trfSmsTo);
                            }
                            smsFacade.sendAtmSms(smsList);
                        }
                    } catch (Exception ex) {
                        System.out.println("Problem In Sending Paid IMPS SMS." + ex.getMessage());
                    }
                    //End Here
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    System.out.println("Erorr in Async Method= " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
                try {
                    ut.begin();
                    n = entityManager.createNativeQuery("update neft_ow_details set status='F',details='FAIL' ,reason='"
                            + resultObj.getRespCode() + ":" + resultObj.getAddDesc() + "',cpsms_batch_no='" + uniqueBankRefNo + "' where "
                            + "UniqueCustomerRefNo='" + obj.getUniqueCustomerRefNo().trim() + "'").executeUpdate();
                    if (n <= 0) {
                        throw new Exception("Problem in outward txn under process status updation.");
                    }
                    ut.commit();
                } catch (Exception e) {
                    ut.rollback();
                    System.out.println("Erorr in Async Method= " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTxnReversed(String resCode) {
        for (int i = 0; i < respCodeArr.length; i++) {
            if (resCode.equalsIgnoreCase(respCodeArr[i])) {
                return true;
            }
        }
        return false;
    }

    public String getAccountMobileNo(String accountNo) throws Exception {
        String mobileNo = "";
        try {
            List smsData = entityManager.createNativeQuery("select mobile_no from mb_subscriber_tab "
                    + "where acno='" + accountNo + "' and status=1 and auth='Y' and auth_status='V'").getResultList();
            if (!smsData.isEmpty()) {
                Vector smsEle = (Vector) smsData.get(0);
                mobileNo = smsEle.get(0).toString().trim();
            } else {
                smsData = entityManager.createNativeQuery("select ifnull(mobilenumber,'') as mobile from "
                        + "cbs_customer_master_detail cu,customerid id where cu.customerid=id.custid "
                        + "and id.acno='" + accountNo + "'").getResultList();
                if (!smsData.isEmpty()) {
                    Vector smsEle = (Vector) smsData.get(0);
                    mobileNo = "+91" + smsEle.get(0).toString().trim();
                }
            }
            mobileNo = mobileNo.trim().length() != 13 ? "" : mobileNo.trim();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return mobileNo;
    }

    //GET
    public ChannelReplyDto sendImpsRequestByGetMethod(NeftRtgsOutwardPojo obj, String impsBin,
            String userName, String txnMode, String logUrl, String txnUrl) throws Exception {
        ChannelReplyDto resultObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode internalNode = mapper.createObjectNode();

            internalNode.put("ChannelId", "00001"); //Finacus will tell us - Rohan
            internalNode.put("ChannelTerminalId", "00000"); //Finacus will tell us - Rohan
            internalNode.put("UniqueChnlRefNo", obj.getUniqueCustomerRefNo().trim());
            internalNode.put("CustType", "E");
            internalNode.put("CustLBrCode", Integer.parseInt(obj.getDebitAccountNo().substring(0, 2).trim()));
            internalNode.put("CustMobNo", Long.parseLong(obj.getSenderCommMode().trim())); //Sender Mobile No

            String custLongName = ftsPosting.getCustName(obj.getDebitAccountNo().trim());
            custLongName = custLongName.replaceAll("[\\W_]", " ");
            custLongName = custLongName.length() > 50 ? custLongName.substring(0, 50) : custLongName;
            internalNode.put("CustLongName", custLongName);
            internalNode.put("CustAcctNo", obj.getDebitAccountNo().trim());
            internalNode.put("CustMMID", impsBin + "001");
            internalNode.put("ModeOfTrn", "P2A");

            internalNode.put("BenefLongName", obj.getBeneficiaryName().trim()); //Beneficiary Name
            internalNode.put("BenefAadhaarNo", "");
            internalNode.put("BenefMobNo", obj.getBeneficiaryMobileNo().trim()); //Beneficiary Mobile
            internalNode.put("BenefAccNo", obj.getCreditAccountNo().trim());
            internalNode.put("BenefIFSC", obj.getBeneficiaryIfsc().trim());
            internalNode.put("MakerUserId", obj.getEnterBy().trim());

            internalNode.put("CheckerUserId", userName);
            internalNode.put("OTP", "0");
            internalNode.put("MPIN", "0");
            internalNode.put("TrnAmount", new BigDecimal(obj.getNeftAmount().toString())); //Confirm from Finacus for point
            internalNode.put("UniqueBankRefNo", obj.getUniqueCustomerRefNo().trim());
            internalNode.put("TrnRemarks", obj.getRemmitInfo().trim());
            internalNode.put("ChargeAmt", BigDecimal.ZERO);

            internalNode.put("InstNo", "000000000000");
            internalNode.put("InstType", "0");
            internalNode.put("SetNo", 10);
            internalNode.put("ScrollNo", 10);
            internalNode.put("BatchCode", "");
            internalNode.put("LbrCode", Integer.parseInt(obj.getDebitAccountNo().substring(0, 2).trim()));
            internalNode.put("EntryDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

            mapper = new ObjectMapper();
            ObjectNode requestNode = mapper.createObjectNode();
            ((ObjectNode) requestNode).set("ChannelReqObj", internalNode);
            String JSON_STRING = requestNode.toString();
            System.out.println("Json Request Is-->" + JSON_STRING);

            DefaultHttpClient httpClient = new DefaultHttpClient();

            int timeout = 30; // seconds
            HttpParams httpParams = httpClient.getParams();
            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
            httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);

            String url = "";
            if (txnMode.equalsIgnoreCase("logged")) {
//                url = "http://172.17.2.83/NagarBranchBankingService/RestWebService.svc/ProcessRequest/ProcChannelBranchBank?";
                url = logUrl;
            } else if (txnMode.equalsIgnoreCase("txn")) {
//                url = "http://172.17.2.83/NagarBranchBankingService/RestWebService.svc/ProcessRequest/BranchBnnkTxnStatus?";
                url = txnUrl;
            }

            List<NameValuePair> params = new LinkedList<>();

            params.add(new BasicNameValuePair("request", JSON_STRING));
            String paramString = URLEncodedUtils.format(params, "utf-8");
            url = url + paramString;
            HttpGet getRequest = new HttpGet(url);
            getRequest.addHeader("Content-type", "application/json");

            System.out.println("Request Line-->" + getRequest.getRequestLine());
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            StringBuilder resultBuffer = new StringBuilder();
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                resultBuffer.append(output);
            }

            Document resultDoc = IblUtil.convertStringToDocument(resultBuffer.toString());
            System.out.println(resultDoc.getDocumentElement().getNodeName());
            Element ele = resultDoc.getDocumentElement();

            ImpsOwResponseDto owResponse = mapper.readValue(ele.getFirstChild().getNodeValue(), ImpsOwResponseDto.class); //Might be change here
            resultObj = owResponse.getChannelReplyObj();
            System.out.println("Result Object-->" + resultObj);

            httpClient.getConnectionManager().shutdown();
            return resultObj;

            //Only For Testing

//            DefaultHttpClient httpClient = new DefaultHttpClient();
//            int timeout = 30; // seconds
//            HttpParams httpParams = httpClient.getParams();
//            httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
//            String url = "";
//            if (txnMode.equalsIgnoreCase("logged")) {
////                url = "http://localhost:8081/axisneftbridge/rest/owimpstest?";
//                url = "http://localhost:8081/rest/owimpstest?";
//            } else if (txnMode.equalsIgnoreCase("txn")) {
////                url = "http://localhost:8081/axisneftbridge/rest/owimpstesttxn?";
//                url = "http://localhost:8081/rest/owimpstesttxn?";
//            }
//
//            List<NameValuePair> params = new LinkedList<>();
//            params.add(new BasicNameValuePair("request", JSON_STRING));
//            String paramString = URLEncodedUtils.format(params, "utf-8");
//            url = url + paramString;
//            HttpGet getRequest = new HttpGet(url);
//            getRequest.addHeader("Content-type", "application/json");
//
//            System.out.println("Request Line-->" + getRequest.getRequestLine());
//            HttpResponse response = httpClient.execute(getRequest);
//            if (response.getStatusLine().getStatusCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
//            }
//
//            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//            StringBuilder resultBuffer = new StringBuilder();
//            String output;
//            System.out.println("Output from Server .... \n");
//            while ((output = br.readLine()) != null) {
//                System.out.println(output);
//                resultBuffer.append(output);
//            }
//
//            ImpsOwResponseDto owResponse = mapper.readValue(resultBuffer.toString(), ImpsOwResponseDto.class); //Might be change here
//            resultObj = owResponse.getChannelReplyObj();
//            System.out.println("Result Object-->" + resultObj);
//
//            httpClient.getConnectionManager().shutdown();
//            return resultObj;

            //End Here
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    //POST
    public ChannelReplyDto sendImpsRequestByPostMethod(NeftRtgsOutwardPojo obj, String impsBin, String userName) throws Exception {
        ChannelReplyDto resultObj = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode internalNode = mapper.createObjectNode();

            internalNode.put("ChannelId", "00001"); //Finacus will tell us - Rohan
            internalNode.put("ChannelTerminalId", "00000"); //Finacus will tell us - Rohan
            internalNode.put("UniqueChnlRefNo", obj.getUniqueCustomerRefNo().trim());
            internalNode.put("CustType", "E");
            internalNode.put("CustLBrCode", Integer.parseInt(obj.getDebitAccountNo().substring(0, 2).trim()));
            internalNode.put("CustMobNo", Long.parseLong(obj.getSenderCommMode().trim())); //Sender Mobile No

            String custLongName = ftsPosting.getCustName(obj.getDebitAccountNo().trim());
            custLongName = custLongName.replaceAll("[\\W_]", " ");
            custLongName = custLongName.length() > 50 ? custLongName.substring(0, 50) : custLongName;
            internalNode.put("CustLongName", custLongName);
            internalNode.put("CustAcctNo", obj.getDebitAccountNo().trim());
            internalNode.put("CustMMID", impsBin + "001");
            internalNode.put("ModeOfTrn", "P2A");

            internalNode.put("BenefLongName", obj.getBeneficiaryName().trim()); //Beneficiary Name
            internalNode.put("BenefAadhaarNo", "");
            internalNode.put("BenefMobNo", obj.getBeneficiaryMobileNo().trim()); //Beneficiary Mobile
            internalNode.put("BenefAccNo", obj.getCreditAccountNo().trim());
            internalNode.put("BenefIFSC", obj.getBeneficiaryIfsc().trim());
            internalNode.put("MakerUserId", obj.getEnterBy().trim());

            internalNode.put("CheckerUserId", userName);
            internalNode.put("OTP", "0");
            internalNode.put("MPIN", "0");
            internalNode.put("TrnAmount", new BigDecimal(obj.getNeftAmount().toString())); //Confirm from Finacus for point
            internalNode.put("UniqueBankRefNo", obj.getUniqueCustomerRefNo().trim());
            internalNode.put("TrnRemarks", obj.getRemmitInfo().trim());
            internalNode.put("ChargeAmt", BigDecimal.ZERO);

            internalNode.put("InstNo", "000000000000");
            internalNode.put("InstType", "0");
            internalNode.put("SetNo", 0);
            internalNode.put("ScrollNo", 0);
            internalNode.put("BatchCode", "");
            internalNode.put("LbrCode", Integer.parseInt(obj.getDebitAccountNo().substring(0, 2).trim()));
            internalNode.put("EntryDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

            mapper = new ObjectMapper();
            ObjectNode requestNode = mapper.createObjectNode();
            ((ObjectNode) requestNode).set("ChannelReqObj", internalNode);
            String JSON_STRING = requestNode.toString();
            System.out.println("Json Request Is-->" + JSON_STRING);

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://localhost:8081/axisneftbridge/rest/owimpstest");

            StringEntity input = new StringEntity(JSON_STRING);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

//            if (response.getStatusLine().getStatusCode() != 201) {
//                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
//            }

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            StringBuilder resultBuffer = new StringBuilder();
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                resultBuffer.append(output);
            }
            ImpsOwResponseDto owResponse = mapper.readValue(resultBuffer.toString(), ImpsOwResponseDto.class); //Might be change here
            resultObj = owResponse.getChannelReplyObj();
            System.out.println("Result Object-->" + resultObj);

            httpClient.getConnectionManager().shutdown();
            return resultObj;
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public String performChargeTransaction(NeftOwDetails obj, String authBy, String orgBrnCode,
            Float trsNo, String partyBrnCode, String isoHead) {
        String msg = "true";
        Date date = new Date();
        Map<String, Double> map = new HashMap<String, Double>();
        try {
            String chgHead, chgRemarks;
            int tranDesc;
            if (obj.getPaymentType().trim().equalsIgnoreCase("IMPS")) {
                chgHead = ftsPosting.getCodeFromCbsParameterInfo("IMPS-OW-CHG");
                tranDesc = 127;
            } else {
                chgHead = ftsPosting.getAcnoByPurpose("NEFT-RTGS-CHARGE");
                tranDesc = 125;
            }
//            String chgHead = ftsPosting.getAcnoByPurpose("NEFT-RTGS-CHARGE");
            chgHead = orgBrnCode + chgHead.trim();
            if (chgHead.equals("") || chgHead.length() != 12) {
                return msg = "Please check NEFT-RTGS-CHARGE/IMPS-OW-CHG head in Abb Parameter Info/CBS Parameter Info.";
            }

            int sTaxModuleActive = ftsPosting.getCodeForReportName("STAXMODULE_ACTIVE");

            String tempOrgnCode = "", tempDestCode = "", orgnIsoHead = "", destIsoHead = "";
            if (orgBrnCode.equalsIgnoreCase(partyBrnCode)) {
                tempOrgnCode = orgBrnCode;
                tempDestCode = orgBrnCode;
            } else {
                tempOrgnCode = orgBrnCode;
                tempDestCode = partyBrnCode;
            }
            orgnIsoHead = tempOrgnCode + isoHead;
            destIsoHead = tempDestCode + isoHead;

            if (sTaxModuleActive == 0) {   //Without Service Tax
                chgRemarks = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "O/W. IMPS Charge" : "O/W. Neft-Rtgs Charge";
                Float partyRecno = ftsPosting.getRecNo();
                //orgBrnCode,orgBrnCode
                int insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                        + "tran_id,adviceno,advicebrncode) values('" + obj.getDebitAccountNo() + "',"
                        + "'" + ftsPosting.ftsGetCustName(obj.getDebitAccountNo()) + "','" + ymd.format(date) + "',"
                        + "'" + ymd.format(date) + "',0," + obj.getChargeAmount().doubleValue() + ",1,2,"
                        + "" + partyRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "',"
                        + "" + tranDesc + " ,0,'A','" + chgRemarks + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                        + "'','" + tempOrgnCode + "','" + tempDestCode + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    return msg = "Data insertion problem in transfer scroll for account number: " + obj.getDebitAccountNo();
                }
                Float chgRecno = ftsPosting.getRecNo();
                //orgBrnCode,orgBrnCode
                insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                        + "adviceno,advicebrncode) values('" + chgHead + "','" + ftsPosting.ftsGetCustName(chgHead) + "',"
                        + "'" + ymd.format(date) + "','" + ymd.format(date) + "'," + obj.getChargeAmount().doubleValue() + ","
                        + "0,0,2," + chgRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "',"
                        + "" + tranDesc + " ,0,'A','" + chgRemarks + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'',"
                        + "'" + tempOrgnCode + "','" + tempOrgnCode + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    return msg = "Data insertion problem in transfer scroll for account number: " + chgHead;
                }

                msg = ftsPosting.updateBalance(ftsPosting.getAccountNature(chgHead), chgHead,
                        obj.getChargeAmount().doubleValue(), 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }
                //orgBrnCode,orgBrnCode
                msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(chgHead), chgHead, 0, obj.getChargeAmount().doubleValue(),
                        ymd.format(date), ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, "",
                        chgRecno, "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempOrgnCode,
                        0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }
                //orgBrnCode,orgBrnCode
                msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(obj.getDebitAccountNo()), obj.getDebitAccountNo(),
                        1, obj.getChargeAmount().doubleValue(), ymd.format(date), ymd.format(date), 2, chgRemarks,
                        obj.getEnterBy(), trsNo, ymd.format(date), partyRecno, "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0,
                        "", 0f, "", "", tempOrgnCode, tempDestCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }
                //For Intra Branch Transaction
                if (!orgBrnCode.equalsIgnoreCase(partyBrnCode)) {
                    msg = ftsPosting.insertRecons("PO", destIsoHead, 0, obj.getChargeAmount().doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }

                    msg = ftsPosting.insertRecons("PO", orgnIsoHead, 1, obj.getChargeAmount().doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, ymd.format(date),
                            ftsPosting.getRecNo(), "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode,
                            tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }
                }
            } else {
                chgRemarks = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "O/W. IMPS Charge" : "O/W. Neft-Rtgs Charge";
                List accountList = entityManager.createNativeQuery("select ifnull(cm.MailStateCode,'') as stateCode, ifnull(br.State,'') as brState "
                        + " from accountmaster am, customerid ci, cbs_customer_master_detail cm, branchmaster br  "
                        + " where am.acno = '" + obj.getDebitAccountNo() + "' and am.acno = ci.acno and ci.CustId = cast(cm.customerid as unsigned) "
                        + " and br.brncode=cast('" + orgBrnCode + "' as unsigned) and substring(ci.Acno,1,2) = '" + partyBrnCode + "'").getResultList();
                if (accountList.isEmpty()) {
                    throw new ApplicationException("Account does not exist");
                }
                Vector accountVector = (Vector) accountList.get(0);
                String custState = accountVector.get(0).toString();
                String branchState = accountVector.get(1).toString();

                BigDecimal serChg = new BigDecimal("0");
                if (custState.equalsIgnoreCase(branchState)) {
                    map = interBranchFacade.getTaxComponent(obj.getChargeAmount().doubleValue(), ymd.format(date));
                } else {
                    map = interBranchFacade.getIgstTaxComponent(obj.getChargeAmount().doubleValue(), ymd.format(date));
                }
                Set<Entry<String, Double>> set = map.entrySet();
                Iterator<Entry<String, Double>> it = set.iterator();
                while (it.hasNext()) {
                    Entry entry = it.next();
                    serChg = serChg.add(new BigDecimal(entry.getValue().toString()));
                }

                Float partyRecno = ftsPosting.getRecNo();
                //orgBrnCode,orgBrnCode
                int insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                        + "tran_id,adviceno,advicebrncode) values('" + obj.getDebitAccountNo() + "',"
                        + "'" + ftsPosting.ftsGetCustName(obj.getDebitAccountNo()) + "','" + ymd.format(date) + "',"
                        + "'" + ymd.format(date) + "',0," + obj.getChargeAmount().doubleValue() + ",1,2,"
                        + "" + partyRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "',"
                        + "" + tranDesc + " ,0,'A','" + chgRemarks + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                        + "'','" + tempOrgnCode + "','" + tempDestCode + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    return msg = "Data insertion problem in transfer scroll for account number: " + obj.getDebitAccountNo();
                }
                //orgBrnCode,orgBrnCode
                msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(obj.getDebitAccountNo()), obj.getDebitAccountNo(),
                        1, obj.getChargeAmount().doubleValue(), ymd.format(date), ymd.format(date), 2, chgRemarks,
                        obj.getEnterBy(), trsNo, "", partyRecno, "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "",
                        "", tempOrgnCode, tempDestCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }

                Float chgRecno = ftsPosting.getRecNo();
                //orgBrnCode,orgBrnCode
                insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,"
                        + "adviceno,advicebrncode) values('" + chgHead + "','" + ftsPosting.ftsGetCustName(chgHead) + "',"
                        + "'" + ymd.format(date) + "','" + ymd.format(date) + "'," + obj.getChargeAmount().doubleValue() + ",0,0,"
                        + "2," + chgRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "'," + tranDesc + " ,0,"
                        + "'A','" + chgRemarks + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'',"
                        + "'" + tempOrgnCode + "','" + tempOrgnCode + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    return msg = "Data insertion problem in transfer scroll for account number: " + chgHead;
                }
                //orgBrnCode,orgBrnCode
                msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(chgHead), chgHead, 0, obj.getChargeAmount().doubleValue(),
                        ymd.format(date), ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, "",
                        chgRecno, "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempOrgnCode,
                        0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }

                Float partySRecno = ftsPosting.getRecNo();
                //orgBrnCode,orgBrnCode
                chgRemarks = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "O/W IMPS GST" : "O/W Neft-Rtgs GST";

                insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,"
                        + "valuedt,cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,"
                        + "tokenno,subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,"
                        + "tran_id,adviceno,advicebrncode) values('" + obj.getDebitAccountNo() + "',"
                        + "'" + ftsPosting.ftsGetCustName(obj.getDebitAccountNo()) + "','" + ymd.format(date) + "',"
                        + "'" + ymd.format(date) + "',0," + serChg + ",1,2,"
                        + "" + partySRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "',"
                        + "71 ,0,'A','" + chgRemarks + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,"
                        + "'','" + tempOrgnCode + "','" + tempDestCode + "',0,'','')").executeUpdate();

                if (insertResult <= 0) {
                    return msg = "Data insertion problem in transfer scroll for account number: " + obj.getDebitAccountNo();
                }
                //orgBrnCode,orgBrnCode
                msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(obj.getDebitAccountNo()), obj.getDebitAccountNo(),
                        1, serChg.doubleValue(), ymd.format(date), ymd.format(date), 2, chgRemarks,
                        obj.getEnterBy(), trsNo, "", partySRecno, "Y", authBy, 71, 3, "", "", 0f, "", "", 0, "", 0f, "",
                        "", tempOrgnCode, tempDestCode, 0, "", "", "");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }

                //For Intra Branch Transaction
                if (!orgBrnCode.equalsIgnoreCase(partyBrnCode)) {
                    chgRemarks = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "O/W. IMPS Charge" : "O/W. Neft-Rtgs Charge";
                    //Charge Amount Intersole
                    msg = ftsPosting.insertRecons("PO", destIsoHead, 0, obj.getChargeAmount().doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }

                    msg = ftsPosting.insertRecons("PO", orgnIsoHead, 1, obj.getChargeAmount().doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, ymd.format(date),
                            ftsPosting.getRecNo(), "Y", authBy, tranDesc, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode,
                            tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }
                    //Service Charge Intersole
                    chgRemarks = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? "GST for O/W. IMPS Charge" : "GST for O/W. Neft-Rtgs Charge";
                    msg = ftsPosting.insertRecons("PO", destIsoHead, 0, serChg.doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, "", ftsPosting.getRecNo(),
                            "Y", authBy, 71, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }

                    msg = ftsPosting.insertRecons("PO", orgnIsoHead, 1, serChg.doubleValue(), ymd.format(date),
                            ymd.format(date), 2, chgRemarks, obj.getEnterBy(), trsNo, ymd.format(date),
                            ftsPosting.getRecNo(), "Y", authBy, 71, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode,
                            tempDestCode, 0, "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }
                }
                //Other Credit
                Set<Entry<String, Double>> setS = map.entrySet();
                Iterator<Entry<String, Double>> itS = setS.iterator();
                double totalPartyTax = 0;
                while (itS.hasNext()) {
                    Entry entry = itS.next();
                    String[] keyArray = entry.getKey().toString().split(":");
                    String description = keyArray[0];
                    //orgBrnCode
                    String taxHead = tempOrgnCode + keyArray[1];

                    String mainDetails = obj.getPaymentType().trim().equalsIgnoreCase("IMPS") ? description.toUpperCase() + " for O/W. IMPS Charge for. " + obj.getDebitAccountNo() : description.toUpperCase() + " for O/W. Neft-Rtgs Charge for. " + obj.getDebitAccountNo();
//                    String mainDetails = description.toUpperCase() + " for O/W. Neft-Rtgs Charge for. " + obj.getDebitAccountNo();
                    double taxAmount = Double.parseDouble(entry.getValue().toString());

                    Float serChgRecno = ftsPosting.getRecNo();
                    //orgBrnCode,orgBrnCode
                    insertResult = entityManager.createNativeQuery("insert into recon_trf_d(acno,custname,dt,valuedt,"
                            + "cramt,dramt,ty,trantype,recno,instno,instdt,payby,iy,auth,enterby,trandesc,tokenno,"
                            + "subtokenno,details,authby,trsno,trantime,term_id,org_brnid,dest_brnid,tran_id,adviceno,"
                            + "advicebrncode) values('" + taxHead + "','" + ftsPosting.ftsGetCustName(taxHead) + "',"
                            + "'" + ymd.format(date) + "','" + ymd.format(date) + "'," + taxAmount + ",0,0,"
                            + "2," + serChgRecno + ",'',date_format(curdate(),'%Y%m%d'),3,0,'Y','" + obj.getEnterBy() + "',71 ,"
                            + "0,'A','" + mainDetails + "','" + authBy + "'," + trsNo + ",CURRENT_TIMESTAMP,'',"
                            + "'" + tempOrgnCode + "','" + tempOrgnCode + "',0,'','')").executeUpdate();

                    if (insertResult <= 0) {
                        return msg = "Data insertion problem in transfer scroll for account number: " + taxHead;
                    }
                    //orgBrnCode,orgBrnCode
                    msg = ftsPosting.insertRecons(ftsPosting.getAccountNature(taxHead), taxHead, 0, taxAmount,
                            ymd.format(date), ymd.format(date), 2, mainDetails, obj.getEnterBy(), trsNo, "",
                            serChgRecno, "Y", authBy, 71, 3, "", "", 0f, "", "", 0, "", 0f, "", "", tempOrgnCode, tempOrgnCode, 0,
                            "", "", "");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }
                    msg = ftsPosting.updateBalance(ftsPosting.getAccountNature(taxHead), taxHead,
                            taxAmount, 0, "Y", "Y");
                    if (!msg.equalsIgnoreCase("true")) {
                        return msg;
                    }
                    totalPartyTax += taxAmount;
                }
                msg = ftsPosting.updateBalance(ftsPosting.getAccountNature(chgHead), chgHead,
                        obj.getChargeAmount().doubleValue(), 0, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }
                //Party tax balance updation
                msg = ftsPosting.updateBalance(ftsPosting.getAccountNature(obj.getDebitAccountNo()), obj.getDebitAccountNo(),
                        0, totalPartyTax, "Y", "Y");
                if (!msg.equalsIgnoreCase("true")) {
                    return msg;
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return msg;
    }

    @Override
    public String reverseOutwardTransaction(NeftOwDetails obj, String brncode, String user,
            String outwardHead) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "true";
        Date curDt = new Date();
        try {
            ut.begin();
            Double txnAmount = obj.getTxnAmt().doubleValue();
            Float trsnumber = ftsPosting.getTrsNo();
            Float recnumber = ftsPosting.getRecNo();
            String creditAcNo = obj.getDebitAccountNo();

            if (creditAcNo.substring(2, 4).equalsIgnoreCase(CbsAcCodeConstant.SF_AC.getAcctCode())) {  //Dividend Case.
                GlTableDAO glTableDAO = new GlTableDAO(entityManager);
                Gltable glEntity = glTableDAO.findByMsgflag(40);
                if (glEntity == null || glEntity.getAcNo().equals("") || glEntity.getAcNo().length() != 12) {
                    throw new ApplicationException("Please define proper dividend payable head in gltable.");
                }
                String divDetails = "Dividend for acno " + creditAcNo;
                String fyear = obj.getRemmitInfo().substring(obj.getRemmitInfo().lastIndexOf(":") + 1).trim();
                int result = entityManager.createNativeQuery("Insert Into dividend_recon(acno,cramt,dramt,ty,"
                        + "trantype,iy,enterby,authby,details,bcode,dest_brnid, org_brnid,auth,disdate,trandesc,"
                        + "fyear,payby,trantime,recno,trsno) Values ('" + creditAcNo + "'," + txnAmount + ",0,"
                        + "0,2,0,'" + user + "','" + user + "','" + divDetails + "','HO',"
                        + "'" + brncode + "','" + brncode + "','Y','" + ymd.format(curDt) + "',110,'" + fyear + "',"
                        + "3,now()," + recnumber + "," + trsnumber + ")").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Dividend Posting Failed");
                }
                creditAcNo = glEntity.getAcNo();
                msg = interBranchFacade.cbsPostingCx(creditAcNo, 0, ymd.format(curDt), txnAmount, 0,
                        2, "Reversed Outward Neft-Rtgs", 0f, "A", "", "", 3, 0f, recnumber, 66,
                        ftsPosting.getCurrentBrnCode(creditAcNo), brncode, user, user, trsnumber, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            } else {
                msg = interBranchFacade.cbsPostingSx(creditAcNo, 0, ymd.format(curDt), txnAmount, 0,
                        2, "Reversed Outward Neft-Rtgs", 0f, "A", "", "", 3, 0f, recnumber, 66,
                        ftsPosting.getCurrentBrnCode(creditAcNo), brncode, user, user, trsnumber, "", "");
                if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                    throw new ApplicationException(msg);
                }
            }

            recnumber = ftsPosting.getRecNo();
//            String glAccount = neftAutoObj.getOwHead().trim();
            String glAccount = outwardHead;
            msg = interBranchFacade.cbsPostingCx(glAccount, 1, ymd.format(curDt), txnAmount, 0,
                    2, "Reversed Outward Neft-Rtgs", 0f, "A", "", "", 3, 0f, recnumber, 66, brncode,
                    brncode, user, user, trsnumber, "", "");
            if (!msg.substring(0, 4).equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            msg = ftsPosting.updateBalance(ftsPosting.getAccountNature(glAccount), glAccount, 0, txnAmount, "Y", "Y");
            if (!(msg.equalsIgnoreCase("TRUE"))) {
                throw new ApplicationException(msg);
            }
            ut.commit();
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

    public NeftOwDetails getNeftOwDetails(String custRefNo) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        try {
            return (dataManagementDAO.getNeftOwDetails(custRefNo));
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage());
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    public String updateNeftOwDetailsList(String custRefNo, String authBy, String auth) throws ApplicationException {
        dataManagementDAO = new ElectronicPaymentSystemDAO(entityManager);
        String result = "";
        try {
            result = dataManagementDAO.updateNeftOwDetailsList(custRefNo, authBy, auth);
            if (!result.equalsIgnoreCase("true")) {
                result = "false";
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return result;
    }

    public String makeChequeNoFresh(String acNature, String acno, String instNo, String userName) throws ApplicationException {
        String msg = "true";
        try {
            String curDt = ymd.format(new Date());
            if (acNature.equalsIgnoreCase(CbsConstant.SAVING_AC)) {
                int updateResult = entityManager.createNativeQuery(" update chbook_sb set statusflag='F',lastupdateby='" + userName + "',lastupdatedt='" + curDt + "' where acno='" + acno + "' and chqno='" + instNo + "' ").executeUpdate();
                if (updateResult <= 0) {
                    return msg = "Problem in cheque no updation";
                }
            } else if (acNature.equalsIgnoreCase(CbsConstant.CURRENT_AC)) {
                int updateResult = entityManager.createNativeQuery(" update chbook_ca set statusflag='F',lastupdateby='" + userName + "',lastupdatedt='" + curDt + "' where acno='" + acno + "' and chqno='" + instNo + "' ").executeUpdate();
                if (updateResult <= 0) {
                    return msg = "Problem in cheque no updation";
                }
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return msg;
    }

    public List getChargeApplyOnCustomer(String chargeType, double amount, String accountType, String orgBrCode) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            String curDate = ymd.format(new Date());
            String result = ftsPosting.ftsDateValidate(curDate, orgBrCode);
            if (!result.equalsIgnoreCase("TRUE")) {
                throw new ApplicationException("Bank has not started the day");
            }
            dataList = entityManager.createNativeQuery("select fix_perc_flag,amt from cbs_charge_detail where charge_name='" + chargeType + "' and ac_type='" + accountType + "' "
                    + "and " + amount + " between from_range and to_range and eff_date=(select max(eff_date) from cbs_charge_detail where "
                    + "charge_name='" + chargeType + "' and ac_type='" + accountType + "' and " + amount + " between from_range and to_range and eff_date<= '" + curDate + "') ").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public List<NeftRtgsReversalPojo> getNeftRtgsReversalData(String orgBrnid, String date) throws ApplicationException {
        List dataList = new ArrayList();
        try {
            dataList = entityManager.createNativeQuery("select DebitAccountNo,CreditAccountNo,BeneficiaryName,"
                    + "OutsideBankIfscCode,TxnAmt,InstNo,PaymentType,Status,CmsBankRefNo,UTRNO,UniqueCustomerRefNo,"
                    + "initiated_bank_name from neft_ow_details where DestBrnid = '" + orgBrnid + "' and "
                    + "Status not in('S','U','M') and PaymentDate = '" + date + "' and auth='Y'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return dataList;
    }

    @Override
    public String neftRtgsReversal(NeftOwDetails obj, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String msg = "";
        try {
//            CbsAutoNeftDetails autoObj = findAutoNeftDetailForOutward();
//            msg = reverseOutwardTransaction(obj, "90", "System", autoObj);

            msg = reverseOutwardTransaction(obj, "90", "System", findOutwardHeadByNeftBankName(obj.getInitiatedBankName().trim()));
            if (!msg.equalsIgnoreCase("true")) {
                throw new ApplicationException(msg);
            }
            String uniqueCustRefNo = obj.getUniqueCustomerRefNo();
            String detail = "Manual Reversed";

            List list = entityManager.createNativeQuery("select ifnull(max(sno),0)+1 from neft_ow_details_his where "
                    + "uniquecustomerrefno ='" + uniqueCustRefNo + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            int sno = Integer.parseInt(ele.get(0).toString().trim());

            ut.begin();
            //History Insertion.
            int result = entityManager.createNativeQuery("insert into neft_ow_details_his (paymenttype, uniquecustomerrefno, "
                    + "beneficiaryname, beneficiarycode, txnamt, paymentdate, creditaccountno, outsidebankifsccode, debitaccountno, "
                    + "beneficiaryemailid, beneficiarymobileno, cmsbankrefno, utrno, instno, instdate, dt, trantime, status, reason, "
                    + "details, orgbrnid, destbrnid,enterby, modifiedby, chargetype, chargeamount, file_name, sender_comm_mode_type, "
                    + "sender_comm_mode, remmit_info, outward_file_name,initiated_bank_name,sno) "
                    + "select paymenttype, uniquecustomerrefno, beneficiaryname, beneficiarycode, txnamt, paymentdate, "
                    + "creditaccountno, outsidebankifsccode, debitaccountno, beneficiaryemailid, beneficiarymobileno, "
                    + "cmsbankrefno, utrno, instno, instdate, dt, trantime, status, reason, details, orgbrnid,destbrnid,"
                    + "enterby,authby,chargetype, chargeamount, file_name, sender_comm_mode_type, sender_comm_mode, "
                    + "remmit_info, outward_file_name,initiated_bank_name," + sno + " from neft_ow_details "
                    + "where uniquecustomerrefno = '" + uniqueCustRefNo + "'").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem In Neft Reversal Insertion !");
            }
            //Updation.
            int result1 = entityManager.createNativeQuery("update neft_ow_details set authby='" + userName + "',"
                    + "status='U',details = '" + detail + "' where uniquecustomerrefno = '" + uniqueCustRefNo + "'").executeUpdate();
            if (result1 <= 0) {
                throw new ApplicationException("Problem In Neft Reversal Updation !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return msg = "true";
    }

//    @Override
//    public CbsAutoNeftDetails findAutoNeftDetailForOutward() throws ApplicationException {
//        NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);
//        try {
//            CbsAutoNeftDetails autoObj = neftOwDetailsDAO.findAutoNeftDetailForOutward("bt");
//            if (autoObj == null || autoObj.getOwHead() == null || autoObj.getOwHead().equals("")
//                    || autoObj.getOwHead().length() != 12 || autoObj.getNeftBankName() == null
//                    || autoObj.getNeftBankName().equals("")) {
//                throw new ApplicationException("Please define proper auto neft details "
//                        + "for inward and outward.");
//            }
//            return autoObj;
//        } catch (Exception ex) {
//            throw new ApplicationException(ex.getMessage());
//        }
//    }
    public String getNeftOutwardFileBkpLocation(String processType) throws Exception {
        String outwardBkpLocation = "";
        try {
            List list = entityManager.createNativeQuery("select ow_local_file_backup_path from cbs_auto_neft_details "
                    + "where process_type='" + processType + "'").getResultList();
            if (list.isEmpty()) {
                throw new Exception("There is no outward process.");
            }
            Vector ele = (Vector) list.get(0);
            outwardBkpLocation = ele.get(0).toString();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return outwardBkpLocation;
    }

    public List<NeftOwDownloadDTO> getGeneratedFiles(String fileType, String orgnBrCode, String fileGeneratedDate) throws Exception {
        List<NeftOwDownloadDTO> files = new ArrayList<NeftOwDownloadDTO>();
        try {
            String alphaCode = commonReport.getAlphacodeByBrncode(orgnBrCode);
            List list = null;
            if (alphaCode.equalsIgnoreCase("HO")) {
                if (fileType.equalsIgnoreCase("A")) { //For All Files
                    list = entityManager.createNativeQuery("select date_format(n.dt,'%d/%m/%Y'),b.branchname,"
                            + "ifnull(n.Outward_File_Name,'') as fileName from neft_ow_details n,branchmaster "
                            + "b where dt='" + fileGeneratedDate + "' and cast(n.OrgBrnid as unsigned)=b.brncode "
                            + "group by fileName").getResultList();
                } else if (fileType.equalsIgnoreCase("W")) { //For Only Awaiting Response
                    list = entityManager.createNativeQuery("select date_format(n.dt,'%d/%m/%Y'),b.branchname,"
                            + "ifnull(n.Outward_File_Name,'') as fileName,DebitAccountNo,BeneficiaryName,"
                            + "CreditAccountNo,OutsideBankIfscCode,TxnAmt from neft_ow_details n,branchmaster b "
                            + "where dt='" + fileGeneratedDate + "' and cast(n.OrgBrnid as unsigned)=b.brncode "
                            + "and status='I'").getResultList();
                }
            } else {
                if (fileType.equalsIgnoreCase("A")) {
                    list = entityManager.createNativeQuery("select date_format(n.dt,'%d/%m/%Y'),b.branchname,"
                            + "ifnull(n.Outward_File_Name,'') as fileName from neft_ow_details n,branchmaster b "
                            + "where dt='" + fileGeneratedDate + "' and cast(n.OrgBrnid as unsigned)=b.brncode "
                            + "and b.brncode=" + Integer.parseInt(orgnBrCode) + " group by fileName").getResultList();
                } else if (fileType.equalsIgnoreCase("W")) {
                    list = entityManager.createNativeQuery("select date_format(n.dt,'%d/%m/%Y'),b.branchname,"
                            + "ifnull(n.Outward_File_Name,'') as fileName,DebitAccountNo,BeneficiaryName,"
                            + "CreditAccountNo,OutsideBankIfscCode,TxnAmt from neft_ow_details n,branchmaster b "
                            + "where dt='" + fileGeneratedDate + "' and cast(n.OrgBrnid as unsigned)=b.brncode "
                            + "and status='I' and b.brncode=" + Integer.parseInt(orgnBrCode) + "").getResultList();
                }
            }
            if (list.isEmpty()) {
                throw new Exception("There is no generated files.");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                NeftOwDownloadDTO dto = new NeftOwDownloadDTO();
                dto.setFileGenDt(ele.get(0).toString());
                dto.setFileGenBrName(ele.get(1).toString());
                dto.setFileName(ele.get(2).toString());
                if (fileType.equalsIgnoreCase("W")) {
                    dto.setRemitterAccount(ele.get(3).toString());
                    dto.setBeneficiaryName(ele.get(4).toString());
                    dto.setBeneficiaryAccount(ele.get(5).toString());
                    dto.setBeneficiaryIfsc(ele.get(6).toString());
                    dto.setAmount(new BigDecimal(ele.get(7).toString()));
                }
                files.add(dto);
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return files;
    }

    public String getSponsorAcctNo() throws ApplicationException {
        try {
            String parentDrAccount = "";
            AbbParameterInfoDAO abbParameterInfoDAO = new AbbParameterInfoDAO(entityManager);
            List<AbbParameterInfo> abbParameterInfoList = abbParameterInfoDAO.getEntityByPurpose("NEFT-OW-DEBIT-ACCOUNT");

            if (abbParameterInfoList.isEmpty()) {
                throw new ApplicationException("NEFT-OW-DEBIT-ACCOUNT head is not defined in ABB Parameterinfo");
            }
            for (AbbParameterInfo abbPojo : abbParameterInfoList) {
                parentDrAccount = abbPojo.getAcno();
            }
            return parentDrAccount;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public CbsAutoNeftDetails getIblWebServiceObject() throws ApplicationException {
        try {
            NeftOwDetailsDAO neftOwDetailsDAO = new NeftOwDetailsDAO(entityManager);

            CbsAutoNeftDetails obj = neftOwDetailsDAO.getNeftDetailsByNefBankNameAndProcessAndProcessType("ibl", "auto", "bt");
            if (obj == null) {
                throw new Exception("Please define IBL H2H Seb Service credential.");
            }
            return obj;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String branchNameByIfsc(String ifscCode) throws ApplicationException {
        String branchName = "";
        try {
            List list = entityManager.createNativeQuery("select ifnull(branch,'') as branch from "
                    + "outward_bank_detail where ifsc='" + ifscCode + "'").getResultList();
            for (int i = 0; i < list.size(); i++) {
                Vector ele = (Vector) list.get(i);
                branchName = ele.get(0).toString().trim();
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return branchName;
    }

    @Override
    public List<ReInitiateNftPojo> getReInitateNftDetails(String date, String branch) throws ApplicationException {
        List<ReInitiateNftPojo> datalist = new ArrayList<>();
        try {
            if (branch.equals("90")) {
                datalist = entityManager.createNativeQuery("select UniqueCustomerRefNo,BeneficiaryName,OutsideBankIfscCode,TxnAmt,"
                        + "  CreditAccountNo,DebitAccountNo,PaymentDate,date_format(dt,'%d/%m/%Y'),Auth,Authby,Status "
                        + "from neft_ow_details where status='I'and Auth='Y'and dt ='" + date + "'").getResultList();
            } else {
                datalist = entityManager.createNativeQuery("select UniqueCustomerRefNo,BeneficiaryName,OutsideBankIfscCode,TxnAmt,"
                        + "  CreditAccountNo,DebitAccountNo,PaymentDate,date_format(dt,'%d/%m/%Y'),Auth,Authby,Status "
                        + "from neft_ow_details where status='I'and Auth='Y'and OrgBrnid='" + branch + "' and dt ='" + date + "'").getResultList();
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return datalist;
    }

    @Override
    public String getreinitateNeftupdation(String refNo, String userName, String date) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            //History Insertion.
            int result = entityManager.createNativeQuery("INSERT INTO neft_ow_details_his (PaymentType, UniqueCustomerRefNo, BeneficiaryName, "
                    + "BeneficiaryCode, TxnAmt, PaymentDate, CreditAccountNo, OutsideBankIfscCode, "
                    + "DebitAccountNo, BeneficiaryEmailId, BeneficiaryMobileNo, CmsBankRefNo, UTRNO, InstNo, InstDate, dt,"
                    + " Trantime, STATUS, Reason, Details, OrgBrnid, DestBrnid,EnterBy,"
                    + "ChargeType, ChargeAmount, FILE_NAME, SENDER_COMM_MODE_TYPE, SENDER_COMM_MODE,REMmIT_INFO,sno,ModifiedBy)"
                    + "select PaymentType, UniqueCustomerRefNo, BeneficiaryName, BeneficiaryCode, TxnAmt, PaymentDate, CreditAccountNo, OutsideBankIfscCode,"
                    + " DebitAccountNo, BeneficiaryEmailId, BeneficiaryMobileNo, CmsBankRefNo, UTRNO, InstNo, InstDate, dt, Trantime, Status,"
                    + " Reason, Details, OrgBrnid,DestBrnid,EnterBy,ChargeType, ChargeAmount, "
                    + "FILE_NAME, SENDER_COMM_MODE_TYPE, SENDER_COMM_MODE,REMmIT_INFO,2 ,'" + userName + "'"
                    + "from neft_ow_details where UniqueCustomerRefNo = '" + refNo + "'").executeUpdate();
            if (result <= 0) {
                throw new ApplicationException("Problem In Neft Reversal Insertion !");
            }
            //Updation.
            int result1 = entityManager.createNativeQuery("update neft_ow_details set Status = 'P', PaymentDate = '" + date + "' where UniqueCustomerRefNo = '" + refNo + "'").executeUpdate();
            if (result1 <= 0) {
                throw new ApplicationException("Problem In Neft Reversal Updation !");
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    //IMPS Coding
    @Override
    public List getverfiedCustomerDetails(String acno, String orgnBrCode) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select am.acno,a.acctcode from (select acctcode from accounttypemaster "
                    + "where (acctnature in('SB') or (acctnature in('CA') and (accttype='CA' or accttype='CC')))) a,"
                    + "accountmaster am where a.acctcode=am.accttype and am.accstatus=1 and am.curbrcode='" + orgnBrCode + "' and "
                    + "am.acno='" + acno + "'").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public List getCustomerDetails(String acno) throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select cs.customerid,cs.CustFullName,ifnull(cs.pan_girnumber,'') as panNo,"
                    + "ifnull(cs.aadhaar_no,'')as aadhaarNo,ifnull(cs.mobilenumber,'') as mobileNo,ci.acno from "
                    + "cbs_customer_master_detail cs,customerid ci  where  ci.acno='" + acno + "' and "
                    + "ci.custid=cs.customerid").getResultList();
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public String getImpsGenertaionCancleAdddata(String acno, String name, String mobileno, String date, String validdata,
            String institutionId, String request_beneficaryName, String beneficaryAccountno, String beneficaryIFSC,
            String beneficarymobile, String beneficaryMMId, double tranAmount, String remark, String user, String mode,
            String function, String chargeamount) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            List list = entityManager.createNativeQuery("select ifnull(max(stan),0)+1 from cbs_imps_ow_request "
                    + "where cast(dt as date)='" + date + "'").getResultList();
            Vector ele = (Vector) list.get(0);
            int stanCode = Double.valueOf(ele.get(0).toString()).intValue();
            String stancode1 = String.valueOf(stanCode);
            stancode1 = ParseFileUtil.addTrailingZeros(stancode1, 6);

            if (function.equalsIgnoreCase("G") && mode.equalsIgnoreCase("A")) {
                List datalistt = entityManager.createNativeQuery("select remitter_mob_no,remitter_acc_no from cbs_imps_ow_request "
                        + "where remitter_acc_no='" + acno + "' ").getResultList();
                if (!datalistt.isEmpty()) {
                    list = entityManager.createNativeQuery(" select acno,mobile from cbs_imps_mmid_detail where "
                            + "(cancel_by is null or cancel_by='') and acno='" + acno + "' and mobile='" + mobileno + "'").getResultList();
                    if (!list.isEmpty()) {
                        throw new ApplicationException("This account number is already registered with same mobile no.");
                    }
                }
                int result = entityManager.createNativeQuery("insert into cbs_imps_ow_request(remitter_name,remitter_acc_no,"
                        + "remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,bene_acc_no,request_bene_name,bene_ifsc,"
                        + "bene_mobile_no,mmid,tran_amount,remark,validation_data,institution_id,enter_by,dt,auth_by,request_status,response_code,"
                        + "response_desc,response_bene_name,return_detail,txn_ref_no,rrn,charge_amount)"
                        + "values('" + name + "','" + acno + "','" + mobileno + "','" + SarvatraImpsEnum.GENERATE_MMID.getCode() + "','"
                        + SarvatraImpsEnum.IMPS_BRANCH_CHANNEL.getCode() + "','" + date + "','" + stancode1 + "','','','','',''"
                        + ",0,'','" + validdata + "','" + institutionId + "','" + user + "',now(),'','L','','','','','','',0)").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in request of MMID generate request in IMPS.");
                }
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("A")) {
                list = entityManager.createNativeQuery("select mobile ,acno from cbs_imps_mmid_detail where acno='" + acno + "' and "
                        + "(cancel_by<>null and cancel_by<>'')").getResultList();
                if (list.isEmpty()) {

                    int result = entityManager.createNativeQuery("insert into cbs_imps_ow_request(remitter_name,remitter_acc_no,"
                            + "remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,bene_acc_no,request_bene_name,bene_ifsc,"
                            + "bene_mobile_no,mmid,tran_amount,remark,validation_data,institution_id,enter_by,dt,auth_by,request_status,response_code,"
                            + "response_desc,response_bene_name,return_detail,txn_ref_no,RRN,Charge_Amount)"
                            + "values('" + name + "','" + acno + "','" + mobileno + "','" + SarvatraImpsEnum.CANCEL_MMID.getCode() + "',"
                            + "'" + SarvatraImpsEnum.IMPS_BRANCH_CHANNEL.getCode() + "','" + date + "','" + stancode1 + "','','','','','',0,'','','" + institutionId + "','" + user + "',"
                            + "now(),'','L','','','','','','',0)").executeUpdate();
                    if (result <= 0) {
                        throw new ApplicationException("Problem in request of MMID cancleation request in IMPS.");
                    }

                } else {
                    throw new Exception("This account no is already cancelled.");
                }
            } else if (function.equalsIgnoreCase("FP2A") && mode.equalsIgnoreCase("A")) {
                int result = entityManager.createNativeQuery("insert into cbs_imps_ow_request(remitter_name,remitter_acc_no,"
                        + "remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,bene_acc_no,request_bene_name,bene_ifsc,"
                        + "bene_mobile_no,mmid,tran_amount,remark,validation_data,institution_id,enter_by,dt,auth_by,request_status,response_code,"
                        + "response_desc,response_bene_name,return_detail,txn_ref_no,rrn,Charge_Amount)"
                        + "values('" + name + "','" + acno + "','" + mobileno + "','" + SarvatraImpsEnum.P2A_TRF.getCode() + "','"
                        + SarvatraImpsEnum.IMPS_BRANCH_CHANNEL.getCode() + "','" + date + "','" + stancode1 + "',"
                        + "'" + beneficaryAccountno + "','" + request_beneficaryName + "','" + beneficaryIFSC + "','',''," + tranAmount + ",'" + remark + "','',"
                        + "'" + institutionId + "','" + user + "',now(),'','L','','','','','','','" + chargeamount + "')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in request of P2A transfer in IMPS !");
                }
            } else if (function.equalsIgnoreCase("FP2P") && mode.equalsIgnoreCase("A")) {
                int result = entityManager.createNativeQuery("insert into cbs_imps_ow_request(remitter_name,remitter_acc_no,"
                        + "remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,bene_acc_no,request_bene_name,bene_ifsc,"
                        + "bene_mobile_no,mmid,tran_amount,remark,validation_data,institution_id,enter_by,dt,auth_by,request_status,response_code,"
                        + "response_desc,response_bene_name,return_detail,txn_ref_no,rrn,Charge_Amount)"
                        + "values('" + name + "','" + acno + "','" + mobileno + "','" + SarvatraImpsEnum.P2P_TRF.getCode() + "','"
                        + SarvatraImpsEnum.IMPS_BRANCH_CHANNEL.getCode() + "','" + date + "','" + stancode1 + "',"
                        + "'','','','" + beneficarymobile + "','" + beneficaryMMId + "'," + tranAmount + ",'" + remark + "','',"
                        + "'" + institutionId + "','" + user + "',now(),'','L','','','','','','','" + chargeamount + "')").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in request of P2P transfer in IMPS !");
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
        }
        return "true";
    }

    @Override
    public List getImpsGridDetails(String acno, String date, String mode, String function, String orgBrnCode) throws ApplicationException {
        List list = new ArrayList();
        try {
            if (function.equalsIgnoreCase("G") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                list = entityManager.createNativeQuery("select remitter_name,remitter_acc_no,remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,"
                        + "bene_acc_no,bene_ifsc,tran_amount,remark,"
                        + "validation_data,institution_id,enter_by,dt,auth_by,auth_dt,request_status,response_code,"
                        + "response_desc,return_detail,txn_ref_no,rrn from cbs_imps_ow_request where cast(dt as date)='" + date + "' "
                        + " and processing_code='" + SarvatraImpsEnum.GENERATE_MMID.getCode() + "' and request_status='L'"
                        + " and substring(remitter_acc_no,1,2)='" + orgBrnCode + "'").getResultList();
            } else if (function.equalsIgnoreCase("FP2A") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                list = entityManager.createNativeQuery("select remitter_name,remitter_acc_no,remitter_mob_no,processing_code,"
                        + "originating_channel,local_txn_dt_time,stan,bene_acc_no,bene_ifsc,tran_amount,remark,validation_data,"
                        + "institution_id,enter_by,dt,auth_by,auth_dt,request_status,response_code,response_desc,return_detail,"
                        + "txn_ref_no,rrn from cbs_imps_ow_request where cast(dt as date)='" + date + "' and "
                        + "processing_code='" + SarvatraImpsEnum.P2A_TRF.getCode() + "' and request_status='L' and "
                        + "substring(remitter_acc_no,1,2)='" + orgBrnCode + "' and response_code=''").getResultList();
            } else if (function.equalsIgnoreCase("FP2P") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                list = entityManager.createNativeQuery("select remitter_name,remitter_acc_no,remitter_mob_no,processing_code,"
                        + "originating_channel,local_txn_dt_time,stan,bene_acc_no,bene_mobile_no,mmid,bene_ifsc,tran_amount,remark,validation_data,"
                        + "institution_id,enter_by,dt,auth_by,auth_dt,request_status,response_code,response_desc,return_detail,"
                        + "txn_ref_no,rrn from cbs_imps_ow_request where cast(dt as date)='" + date + "' and "
                        + "processing_code='" + SarvatraImpsEnum.P2P_TRF.getCode() + "' and request_status='L' and "
                        + "substring(remitter_acc_no,1,2)='" + orgBrnCode + "' and response_code=''").getResultList();
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("A")) {
                list = entityManager.createNativeQuery("select req.remitter_name,req.remitter_acc_no,req.remitter_mob_no,req.processing_code,req.originating_channel,req.local_txn_dt_time,req.stan,"
                        + " req.bene_acc_no,req.bene_ifsc,req.tran_amount,req.remark,req.validation_data,req.institution_id,req.enter_by,req.dt,req.auth_by,req.auth_dt,"
                        + "req.request_status,req.response_code, req.response_desc,req.return_detail,req.txn_ref_no,req.rrn from cbs_imps_ow_request req , "
                        + "cbs_imps_mmid_detail mmidtl where  mmidtl.acno='" + acno + "' and  cast(req.dt as date)='" + date + "'"
                        + " and mmidtl.mobile= req.remitter_mob_no and mmidtl.acno=req.remitter_acc_no and (mmidtl.cancel_by<>null or mmidtl.cancel_by<>'') ").getResultList();
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("V")) {
                list = entityManager.createNativeQuery("select remitter_name,remitter_acc_no,remitter_mob_no,processing_code,originating_channel,local_txn_dt_time,stan,"
                        + "bene_acc_no,bene_ifsc,tran_amount,remark,validation_data,institution_id,enter_by,dt,auth_by,auth_dt,request_status,response_code,"
                        + "response_desc,return_detail,txn_ref_no,rrn from cbs_imps_ow_request where cast(dt as date)='" + date + "'and "
                        + " request_status='L' and substring(remitter_acc_no,1,2)='" + orgBrnCode + "' and processing_code='" + SarvatraImpsEnum.CANCEL_MMID.getCode() + "'").getResultList();
            }
            return list;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }

    }

    @Override
    public String getVerifyDataOnVerifyDelete(String accno, String branch, String function, String mode, String username,
            String date, String stancode) throws ApplicationException {
        List datalist = new ArrayList();
        try {
            if (mode.equalsIgnoreCase("V")) {
                List list1 = entityManager.createNativeQuery("select enter_by from cbs_imps_ow_request where "
                        + "cast(dt as date)='" + date + "' and stan='" + stancode + "'").getResultList();
                if (!list1.isEmpty()) {
                    Vector vtr = (Vector) list1.get(0);
                    String enterby = vtr.get(0).toString();
                    if (enterby.equalsIgnoreCase(username)) {
                        throw new Exception("You can not verified this request.");
                    }
                }
            }
            if (function.equalsIgnoreCase("G") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                datalist = entityManager.createNativeQuery("select remitter_acc_no,processing_code,request_status,stan "
                        + "from cbs_imps_ow_request where remitter_acc_no = '" + accno + "' and processing_code='" + SarvatraImpsEnum.GENERATE_MMID.getCode() + "' and  "
                        + "request_status='L' and substring(remitter_acc_no,1,2)='" + branch + "' and "
                        + "cast(dt as date)='" + date + "'and stan='" + stancode + "'").getResultList();
                if (datalist.isEmpty()) {
                    throw new Exception("Your account is not verfied");
                }
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("V")) {
                datalist = entityManager.createNativeQuery("select remitter_acc_no,processing_code,request_status "
                        + "from cbs_imps_ow_request where remitter_acc_no = '" + accno + "' and processing_code= '" + SarvatraImpsEnum.CANCEL_MMID.getCode() + "'"
                        + " and request_status='L' and substring(remitter_acc_no,1,2)='" + branch + "'and cast(dt as date)='" + date + "' "
                        + " and stan='" + stancode + "'").getResultList();
                if (datalist.isEmpty()) {
                    throw new Exception("Your account is not verfied");
                }
            } else if (function.equalsIgnoreCase("FP2A") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                datalist = entityManager.createNativeQuery("select remitter_acc_no,processing_code,request_status from "
                        + "cbs_imps_ow_request where remitter_acc_no='" + accno + "' and "
                        + "processing_code= '" + SarvatraImpsEnum.P2A_TRF.getCode() + "'and request_status='L' and "
                        + "substring(remitter_acc_no,1,2)='" + branch + "'and cast(dt as date)='" + date + "' and "
                        + "stan='" + stancode + "'").getResultList();
                if (datalist.isEmpty()) {
                    throw new Exception("Your account is not verfied");
                }
            } else if (function.equalsIgnoreCase("FP2P") && (mode.equalsIgnoreCase("V") || mode.equalsIgnoreCase("D"))) {
                datalist = entityManager.createNativeQuery("select remitter_acc_no,processing_code,request_status from "
                        + "cbs_imps_ow_request where remitter_acc_no='" + accno + "' and "
                        + "processing_code='" + SarvatraImpsEnum.P2P_TRF.getCode() + "' and request_status='L' and "
                        + "substring(remitter_acc_no,1,2)='" + branch + "' and cast(dt as date)='" + date + "' and "
                        + "stan='" + stancode + "'").getResultList();
                if (datalist.isEmpty()) {
                    throw new Exception("Your account is not verfied");
                }
            }
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
        return "true";
    }

    @Override
    public String getGenerateCancleProcess(String accountNo, String date, String branch, String function,
            String mode, String stancode, String userName) throws ApplicationException {
        UserTransaction ut = context.getUserTransaction();
        String resultMessage = "";
        try {
            ut.begin();
            List datalist = entityManager.createNativeQuery("select processing_code,originating_channel,local_txn_dt_time,stan,remitter_mob_no,"
                    + "remitter_acc_no,remitter_name,bene_acc_no,request_bene_name,bene_ifsc,bene_mobile_no,mmid,tran_amount,remark,"
                    + "validation_data,institution_id,enter_by, date_format(dt,'%Y%m%d%H%i%S'),auth_by,auth_dt,request_status,"
                    + "response_code,response_desc,response_bene_name,return_detail,txn_ref_no,rrn,charge_amount from cbs_imps_ow_request"
                    + " where cast(dt as date)='" + date + "'and stan='" + stancode + "'and remitter_acc_no='" + accountNo + "'").getResultList();
            if (datalist.isEmpty()) {
                throw new Exception("No data regarding this account no");
            }

            List tcpDetail = entityManager.createNativeQuery("select ifnull(a.code,'') as TCP_IP,ifnull(b.code,'') as TCP_PORT "
                    + "from (select ifnull((select code from cbs_parameterinfo  where name='IMPS-TCP-IP'),'') as code) a,"
                    + "(select ifnull((select code from cbs_parameterinfo where name='IMPS-TCP-PORT'),'') as code) b").getResultList();
            if (tcpDetail.isEmpty()) {
                throw new Exception("Please define TCP details.");
            }
            Vector tcpVec = (Vector) tcpDetail.get(0);
            String tcpIp = tcpVec.get(0).toString();
            int tcpPort = Integer.parseInt(tcpVec.get(1).toString());


            if (function.equalsIgnoreCase("G") && mode.equalsIgnoreCase("V")) {
                Vector vtr = (Vector) datalist.get(0);
                SvtImpsMmidGenReqDto objct = new SvtImpsMmidGenReqDto();
                objct.setMessageType("1200");
                objct.setProcCode(vtr.get(0).toString());
                objct.setOriginatingChannel(vtr.get(1).toString());
                objct.setLocalTxnDtTime(vtr.get(17).toString());
                objct.setStan(vtr.get(3).toString());
                objct.setRemitterMobNo(vtr.get(4).toString());
                objct.setRemitterAccNo(vtr.get(5).toString());
                objct.setValidationData(vtr.get(14).toString());
                objct.setRemitterName(vtr.get(6).toString());
                objct.setInstitutionID(vtr.get(15).toString());
                JAXBContext jaxbContext = JAXBContext.newInstance(SvtImpsMmidGenReqDto.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(objct, sw);
                String xmlRequestData = sw.toString();
                System.out.println(xmlRequestData);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost("http://192.168.2.247:8081/axisneftbridge/rest/sarvatraMMIDGenerate");

                StringEntity input = new StringEntity(xmlRequestData);
                input.setContentType("application/xml");
                postRequest.setEntity(input);

                HttpResponse response = httpClient.execute(postRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }
                jaxbContext = JAXBContext.newInstance(SvtImpsMmidGenResDto.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                SvtImpsMmidGenResDto svtImpsMmidGenResDto = (SvtImpsMmidGenResDto) jaxbUnmarshaller.unmarshal(new InputStreamReader((response.getEntity().getContent())));
                System.out.println("" + svtImpsMmidGenResDto.getActCode() + "::" + svtImpsMmidGenResDto.getActCodeDesc());
                //Testing End Here

                //Data updation based on Response
                int n = 0;
                String responseDesc = svtImpsMmidGenResDto.getActCodeDesc() == null ? "" : svtImpsMmidGenResDto.getActCodeDesc();
                String rrn = svtImpsMmidGenResDto.getTransRefNo() == null ? "" : svtImpsMmidGenResDto.getTransRefNo();
                if (svtImpsMmidGenResDto.getActCode().equalsIgnoreCase("000")) {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "request_status='S',"
                            + "response_code='" + svtImpsMmidGenResDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "',"
                            + "auth_by='" + userName + "',"
                            + "auth_dt=now(),"
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsMmidGenResDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsMmidGenResDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();

                    int p = entityManager.createNativeQuery("insert into cbs_imps_mmid_detail (acno,mobile,mmid,dt,enterby,cancel_by,cancel_dt)"
                            + "values('" + svtImpsMmidGenResDto.getRemitterAccNo() + "'"
                            + ",'" + svtImpsMmidGenResDto.getRemitterMobNo() + "','" + svtImpsMmidGenResDto.getMmid() + "'"
                            + ",'" + svtImpsMmidGenResDto.getLocalTxnDtTime() + "','" + userName + "','" + userName + "','" + svtImpsMmidGenResDto.getLocalTxnDtTime() + "')").executeUpdate();
                    if (p <= 0) {
                        throw new Exception("Problem In Request Processing.");
                    }

                } else {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "response_code='" + svtImpsMmidGenResDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "', "
                            + "auth_by='" + userName + "', "
                            + "auth_dt=now(), "
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsMmidGenResDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsMmidGenResDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                }
                if (n <= 0) {
                    throw new Exception("Problem In Request Processing.");
                }
            } else if (function.equalsIgnoreCase("C") && mode.equalsIgnoreCase("V")) {
                Vector vtr = (Vector) datalist.get(0);
                SvtImpsMmidCanReqDto objct = new SvtImpsMmidCanReqDto();
                objct.setMessageType("1200");
                objct.setProcCode(vtr.get(0).toString());
                objct.setOriginatingChannel(vtr.get(1).toString());
                objct.setLocalTxnDtTime(vtr.get(17).toString());
                objct.setStan(vtr.get(3).toString());
                objct.setRemitterMobNo(vtr.get(4).toString());
                objct.setRemitterAccNo(vtr.get(5).toString());
                objct.setRemitterName(vtr.get(6).toString());
                objct.setInstitutionID(vtr.get(15).toString());
                JAXBContext jaxbContext = JAXBContext.newInstance(SvtImpsMmidCanReqDto.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(objct, sw);
                String xmlRequestData = sw.toString();
                System.out.println(xmlRequestData);
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost("http://192.168.2.247:8081/axisneftbridge/rest/sarvatraMMIDCancel");

                StringEntity input = new StringEntity(xmlRequestData);
                input.setContentType("application/xml");
                postRequest.setEntity(input);

                HttpResponse response = httpClient.execute(postRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                }
                jaxbContext = JAXBContext.newInstance(SvtImpsMmidCanResDto.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                SvtImpsMmidCanResDto svtImpsMmidCanResDto = (SvtImpsMmidCanResDto) jaxbUnmarshaller.unmarshal(new InputStreamReader((response.getEntity().getContent())));
                System.out.println("" + svtImpsMmidCanResDto.getActCode() + "::" + svtImpsMmidCanResDto.getActCodeDesc());
                //Testing End Here

                //Data updation based on Response
                int n = 0;
                String responseDesc = svtImpsMmidCanResDto.getActCodeDesc() == null ? "" : svtImpsMmidCanResDto.getActCodeDesc();
                String rrn = svtImpsMmidCanResDto.getTransRefNo() == null ? "" : svtImpsMmidCanResDto.getTransRefNo();
                if (svtImpsMmidCanResDto.getActCode().equalsIgnoreCase("000")) {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request impreq,cbs_imps_mmid_detail impdt set "
                            + "impreq.request_status='S',"
                            + "impreq.response_code='" + svtImpsMmidCanResDto.getActCode() + "',"
                            + "impreq.response_desc='" + responseDesc + "',"
                            + "impreq.auth_by='" + userName + "',"
                            + "impreq.auth_dt=now(),"
                            + "impreq.rrn='" + rrn + "' ,"
                            + "impdt.cancel_by ='" + userName + "',"
                            + "impdt.cancel_dt='" + svtImpsMmidCanResDto.getLocalTxnDtTime() + "' "
                            + "where impreq.stan='" + svtImpsMmidCanResDto.getStan() + "' and "
                            + "date_format(impreq.dt,'%Y%m%d')='" + svtImpsMmidCanResDto.getLocalTxnDtTime().substring(0, 8) + "' and impdt.acno='" + svtImpsMmidCanResDto.getRemitterAccNo() + "' "
                            + "and impdt.mobile='" + svtImpsMmidCanResDto.getRemitterMobNo() + "'").executeUpdate();
                } else {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "response_code='" + svtImpsMmidCanResDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "', "
                            + "auth_by='" + userName + "', "
                            + "auth_dt=now(), "
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsMmidCanResDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsMmidCanResDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                }
                if (n <= 0) {
                    throw new Exception("Problem In Request Processing.");
                }
            } else if (function.equalsIgnoreCase("FP2A") && mode.equalsIgnoreCase("V")) {
                Vector vtr = (Vector) datalist.get(0);
                SvtImpsP2ARequestDto objct = new SvtImpsP2ARequestDto();
                objct.setMessageType("1200");
                objct.setProCode(vtr.get(0).toString());
                objct.setOriginalChannel(vtr.get(1).toString());
                objct.setLocalTxnDtTime(vtr.get(17).toString());
                objct.setStanCode(vtr.get(3).toString());
                objct.setRemitterMobNo(vtr.get(4).toString());
                objct.setRemitterAccNo(vtr.get(5).toString());
                objct.setRemitterName(vtr.get(6).toString());
                objct.setBeneAccNo(vtr.get(7).toString());
                objct.setBeneIFSC(vtr.get(9).toString());
                objct.setTranAmount(vtr.get(12).toString());
                objct.setRemark(vtr.get(13).toString());
                objct.setInstitutionID(vtr.get(15).toString());
                JAXBContext jaxbContext = JAXBContext.newInstance(SvtImpsP2ARequestDto.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                //output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(objct, sw);
                String xmlRequestData = sw.toString();
                System.out.println("XML Request: " + xmlRequestData);

                //Establish socket connection to server and send request
//                InetSocketAddress hostAddress = new InetSocketAddress("localhost", 9876);
                InetSocketAddress hostAddress = new InetSocketAddress(tcpIp, tcpPort);
                SocketChannel client = SocketChannel.open(hostAddress);
                ByteBuffer sendBuffer = ByteBuffer.wrap(xmlRequestData.getBytes());
                client.write(sendBuffer);
                sendBuffer.clear();
                //Reading response from socket
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                client.read(readBuffer);
                String xmlResponseData = new String(readBuffer.array()).trim();
                System.out.println("XML Response: " + xmlResponseData);
                readBuffer.clear();
                //Converting response into object
                jaxbContext = JAXBContext.newInstance(SvtImpsP2AResponseDto.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader responseReader = new StringReader(xmlResponseData);
                SvtImpsP2AResponseDto svtImpsP2AResponseDto = (SvtImpsP2AResponseDto) jaxbUnmarshaller.unmarshal(responseReader);
                System.out.println("" + svtImpsP2AResponseDto.getActCode() + "::" + svtImpsP2AResponseDto.getActCodeDesc());
                //Data updation based on Response
                int n = 0;
                String responseDesc = svtImpsP2AResponseDto.getActCodeDesc() == null ? "" : svtImpsP2AResponseDto.getActCodeDesc();
                String rrn = svtImpsP2AResponseDto.getTransRefNo() == null ? "" : svtImpsP2AResponseDto.getTransRefNo();
                if (svtImpsP2AResponseDto.getActCode().equalsIgnoreCase("000")) {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "request_status='S',"
                            + "request_bene_name='" + svtImpsP2AResponseDto.getBeneName() + "',"
                            + "response_code='" + svtImpsP2AResponseDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "',"
                            + "auth_by='" + userName + "',"
                            + "auth_dt=now(),"
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsP2AResponseDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsP2AResponseDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                } else {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "response_code='" + svtImpsP2AResponseDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "', "
                            + "auth_by='" + userName + "', "
                            + "auth_dt=now(), "
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsP2AResponseDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsP2AResponseDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                }
                if (n <= 0) {
                    throw new Exception("Problem In Request Processing.");
                }
                ut.commit();
                resultMessage = responseDesc + "::RRN is->" + rrn;
                return resultMessage;
            } else if (function.equalsIgnoreCase("FP2P") && mode.equalsIgnoreCase("V")) {
                Vector vtr = (Vector) datalist.get(0);
                SvtImpsP2PRequestDto objct = new SvtImpsP2PRequestDto();
                objct.setMessageType("1200");
                objct.setProCode(vtr.get(0).toString());
                objct.setOriginalChannel(vtr.get(1).toString());
                objct.setLocalTxnDtTime(vtr.get(17).toString());
                objct.setStanCode(vtr.get(3).toString());
                objct.setRemitterMobNo(vtr.get(4).toString());
                objct.setRemitterAccNo(vtr.get(5).toString());
                objct.setRemitterName(vtr.get(6).toString());
                objct.setBeneMobileNo(vtr.get(10).toString());
                objct.setBeneMMID(vtr.get(11).toString());
                objct.setTranAmount(vtr.get(12).toString());
                objct.setRemark(vtr.get(13).toString());
                objct.setInstitutionID(vtr.get(15).toString());
                JAXBContext jaxbContext = JAXBContext.newInstance(SvtImpsP2PRequestDto.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                //output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                jaxbMarshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                StringWriter sw = new StringWriter();
                jaxbMarshaller.marshal(objct, sw);
                String xmlRequestData = sw.toString();
                System.out.println(xmlRequestData);
                //Sending request
                InetSocketAddress hostAddress = new InetSocketAddress(tcpIp, tcpPort);
                SocketChannel client = SocketChannel.open(hostAddress);
                ByteBuffer sendBuffer = ByteBuffer.wrap(xmlRequestData.getBytes());
                client.write(sendBuffer);
                sendBuffer.clear();
                //Reading response from socket
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                client.read(readBuffer);
                String xmlResponseData = new String(readBuffer.array()).trim();
                System.out.println("XML Response: " + xmlResponseData);
                readBuffer.clear();
                //Converting response into object
                jaxbContext = JAXBContext.newInstance(SvtImpsP2AResponseDto.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader responseReader = new StringReader(xmlResponseData);
                SvtImpsP2AResponseDto svtImpsP2AResponseDto = (SvtImpsP2AResponseDto) jaxbUnmarshaller.unmarshal(responseReader);
                System.out.println("" + svtImpsP2AResponseDto.getActCode() + "::" + svtImpsP2AResponseDto.getActCodeDesc());

                //Data updation based on Response
                int n = 0;
                String responseDesc = svtImpsP2AResponseDto.getActCodeDesc() == null ? "" : svtImpsP2AResponseDto.getActCodeDesc();
                String rrn = svtImpsP2AResponseDto.getTransRefNo() == null ? "" : svtImpsP2AResponseDto.getTransRefNo();
                if (svtImpsP2AResponseDto.getActCode().equalsIgnoreCase("000")) {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "request_status='S',"
                            + "request_bene_name='" + svtImpsP2AResponseDto.getBeneName() + "',"
                            + "response_code='" + svtImpsP2AResponseDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "',"
                            + "auth_by='" + userName + "',"
                            + "auth_dt=now(),"
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsP2AResponseDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsP2AResponseDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                } else {
                    n = entityManager.createNativeQuery("update cbs_imps_ow_request set "
                            + "response_code='" + svtImpsP2AResponseDto.getActCode() + "',"
                            + "response_desc='" + responseDesc + "', "
                            + "auth_by='" + userName + "', "
                            + "auth_dt=now(), "
                            + "rrn='" + rrn + "' "
                            + "where stan='" + svtImpsP2AResponseDto.getStan() + "' and "
                            + "date_format(dt,'%Y%m%d')='" + svtImpsP2AResponseDto.getLocalTxnDtTime().substring(0, 8) + "'").executeUpdate();
                }
                if (n <= 0) {
                    throw new Exception("Problem In Request Processing.");
                }
                ut.commit();
                resultMessage = responseDesc + "::RRN is->" + rrn;
                return resultMessage;
            } else if ((function.equalsIgnoreCase("G") || function.equalsIgnoreCase("FP2A") || function.equalsIgnoreCase("FP2P")) && mode.equalsIgnoreCase("D")) {
                List delList = entityManager.createNativeQuery("select stan from cbs_imps_ow_request where "
                        + "remitter_acc_no='" + accountNo + "' and  cast(dt as date)='" + date + "'and stan='" + stancode + "' and "
                        + "request_status='L' and response_code=''").getResultList();
                if (delList.isEmpty()) {
                    throw new ApplicationException("There is no data to delete !");
                }

                int result = entityManager.createNativeQuery("delete from cbs_imps_ow_request where remitter_acc_no='" + accountNo + "'"
                        + " and  cast(dt as date)='" + date + "'and stan='" + stancode + "' and request_status='L' and response_code=''").executeUpdate();
                if (result <= 0) {
                    throw new ApplicationException("Problem in cbs_imps_ow_request deletion !");
                }
                ut.commit();
                resultMessage = "Data has been deleted successfully.";
                return resultMessage;
            }
//            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                throw new ApplicationException(e.getMessage());
            }
        }
        return "true";
    }

    @Override
    public List autoNeftBankNameList() throws ApplicationException {
        try {
            return entityManager.createNativeQuery("select neft_bank_name from cbs_auto_neft_details where "
                    + "process_type in('OW','BT') and process='AUTO'").getResultList();
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
    }

    @Override
    public String findOutwardHeadByNeftBankName(String neftBankName) throws ApplicationException {
        String owHead = "";
        try {
            List list = entityManager.createNativeQuery("select ow_head from cbs_auto_neft_details where "
                    + "process='AUTO' and neft_bank_name='" + neftBankName + "' and process_type in('BT','OW')").getResultList();
            if (list.isEmpty()) {
                throw new ApplicationException("Please define outward head for bank " + neftBankName);
            }
            Vector ele = (Vector) list.get(0);
            owHead = ele.get(0).toString().trim();
            if (owHead.equals("") || owHead.trim().length() != 12) {
                throw new ApplicationException("Please define proper outward head for bank " + neftBankName);
            }
        } catch (Exception ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return owHead;
    }

    @Override
    public String insertNeftDataReconsilation(List<NeftDataReconsilationPojo> dataList) {
        UserTransaction ut = context.getUserTransaction();
        try {
            ut.begin();
            for (int i = 0; i < dataList.size(); i++) {
                NeftDataReconsilationPojo pojo = dataList.get(i);
                int n = entityManager.createNativeQuery("insert into neft_data_reconsilation(tran_date,value_date,chq_no,txn_detail,"
                        + "amount,dr_cr,balance,branch_name)values('" + pojo.getTranDate() + "','" + pojo.getValueDate() + "',"
                        + "'" + pojo.getChqNo() + "', '" + pojo.getTxnDetail() + "'," + pojo.getAmount() + ",'" + pojo.getDrCr() + "', " + pojo.getBalance() + ",'" + pojo.getBranch() + "')").executeUpdate();
                if (n <= 0) {
                    throw new Exception("Problem in NeftDataReconsilation Insertion");
                }
            }
            ut.commit();
        } catch (Exception ex) {
            try {
                ut.rollback();
                throw new ApplicationException(ex.getMessage());
            } catch (Exception e) {
                try {
                    throw new ApplicationException(e.getMessage());
                } catch (ApplicationException ex1) {
                    Logger.getLogger(NeftRtgsMgmtFacade.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return "true";
    }

    @Override
    public List<NeftDataReconsilationPojo> getNeftDataReconsilation(String reconDate) throws ApplicationException {
        List<NeftDataReconsilationPojo> resultList = new ArrayList<>();
        try {
            String utr, check_dr_cr = "";
            List list = entityManager.createNativeQuery("select id,date_format(tran_date,'%d/%m/%Y'),date_format(value_date,'%d/%m/%Y'), "
                    + "chq_no,txn_detail,amount,dr_cr,balance,branch_name,(case when substring(txn_detail,1,4)='NEFT' then 'NEFT/RTGS' "
                    + " when substring(txn_detail,1,4)='RTGS' then 'NEFT/RTGS' else 'OTEHR' end) orderNo "
                    + " FROM neft_data_reconsilation  where tran_date ='" + ymd.format(dmy.parse(reconDate)) + "' order by 10 ").getResultList();
            if (list.isEmpty()) {
                throw new Exception("No data regarding this Reconsilation Date");
            }
            for (int i = 0; i < list.size(); i++) {
                Vector vtr = (Vector) list.get(i);
                if (vtr.get(9).toString().equalsIgnoreCase("NEFT/RTGS")) {
                    String txn_detail = vtr.get(4).toString();
                    String[] txn_details = txn_detail.split("/");
                    // check_neft_rtgs = txn_details[0];
                    utr = txn_details[1];
                    check_dr_cr = vtr.get(6).toString();
                    if (check_dr_cr.equalsIgnoreCase("CR")) {
                        List list1 = entityManager.createNativeQuery("select bene_account,bene_name,sender_account,sender_name,status,TRS_NO,"
                                + "date_format(dt,'%d/%m/%Y') from neft_rtgs_status where UTR='" + utr + "'").getResultList();
                        if (!list1.isEmpty()) {
                            NeftDataReconsilationPojo pojo = new NeftDataReconsilationPojo();
                            Vector vtr1 = (Vector) list1.get(0);
                            pojo.setTranDate(vtr.get(1).toString());
                            pojo.setValueDate(vtr.get(2).toString());
                            pojo.setChqNo(vtr.get(3).toString());
                            pojo.setTxnDetail(vtr.get(4).toString());
                            pojo.setAmount(BigDecimal.valueOf(new Double(vtr.get(5).toString())));
                            pojo.setDrCr(vtr.get(6).toString());
                            pojo.setBranch(vtr.get(8).toString());
                            pojo.setNeftRtgsData(vtr.get(9).toString());
                            if (!(vtr1.get(4).toString().equalsIgnoreCase("Unsuccess") || vtr1.get(4).toString().equalsIgnoreCase("U"))) {
                                pojo.setBeneficaryAcNo(vtr1.get(0).toString());
                                pojo.setBeneficaryName(vtr1.get(1).toString());
                                pojo.setRemitterAcNo(vtr1.get(2).toString());
                                pojo.setRemitterName(vtr1.get(3).toString());
                                pojo.setStatus(vtr1.get(4).toString());
                                pojo.setTrsNo(Float.valueOf(vtr1.get(5).toString()));
                                pojo.setTranscationDate(vtr1.get(6).toString());
                            } else {
                                pojo.setBeneficaryAcNo("");
                                pojo.setBeneficaryName("");
                                pojo.setRemitterAcNo("");
                                pojo.setRemitterName("");
                                pojo.setStatus("");
                                pojo.setTrsNo(0f);
                                pojo.setTranscationDate("");
                            }
                            resultList.add(pojo);
                        }
                    } else if (check_dr_cr.equalsIgnoreCase("DR")) {
                        List list1 = entityManager.createNativeQuery("select ow.creditaccountno,ow.beneficiaryname,ow.debitaccountno,ow.Status,ow.trs_no,"
                                + "date_format(ow.dt,'%d/%m/%Y'),ow.utrno,ow.cmsbankrefno from  neft_ow_details ow where ow.utrno='" + utr + "' OR "
                                + "ow.cmsbankrefno='" + utr + "'").getResultList();
                        if (!list1.isEmpty()) {
                            NeftDataReconsilationPojo pojo = new NeftDataReconsilationPojo();
                            Vector vtr1 = (Vector) list1.get(0);
                            pojo.setTranDate(vtr.get(1).toString());
                            pojo.setValueDate(vtr.get(2).toString());
                            pojo.setChqNo(vtr.get(3).toString());
                            pojo.setTxnDetail(vtr.get(4).toString());
                            pojo.setAmount(BigDecimal.valueOf(new Double(vtr.get(5).toString())));
                            pojo.setDrCr(vtr.get(6).toString());
                            pojo.setBranch(vtr.get(8).toString());
                            pojo.setNeftRtgsData(vtr.get(9).toString());
                            if (!(vtr1.get(3).toString().equalsIgnoreCase("Unsuccess") || vtr1.get(3).toString().equalsIgnoreCase("U"))) {
                                pojo.setBeneficaryAcNo(vtr1.get(0).toString());
                                pojo.setBeneficaryName(vtr1.get(1).toString());
                                pojo.setRemitterAcNo(vtr1.get(2).toString());
                                String remitterName = ftsPosting.ftsGetCustName(vtr1.get(2).toString());
                                pojo.setRemitterName(remitterName);
                                pojo.setStatus(vtr1.get(3).toString().equalsIgnoreCase("S") ? "Success" : vtr1.get(3).toString());
                                pojo.setTrsNo(Float.valueOf(vtr1.get(4).toString()));
                                pojo.setTranscationDate(vtr1.get(5).toString());
                            } else {
                                pojo.setBeneficaryAcNo("");
                                pojo.setBeneficaryName("");
                                pojo.setRemitterAcNo("");
                                pojo.setRemitterName("");
                                pojo.setStatus("");
                                pojo.setTrsNo(0f);
                                pojo.setTranscationDate("");
                            }
                            resultList.add(pojo);
                        }
                    }
                } else {
                    NeftDataReconsilationPojo pojo = new NeftDataReconsilationPojo();
                    pojo.setTranDate(vtr.get(1).toString());
                    pojo.setValueDate(vtr.get(2).toString());
                    pojo.setChqNo(vtr.get(3).toString());
                    pojo.setTxnDetail(vtr.get(4).toString());
                    pojo.setAmount(BigDecimal.valueOf(new Double(vtr.get(5).toString())));
                    pojo.setDrCr(vtr.get(6).toString());
                    pojo.setBranch(vtr.get(8).toString());
                    pojo.setNeftRtgsData(vtr.get(9).toString());
                    resultList.add(pojo);
                }
            }
        } catch (Exception e) {
            try {
                throw new Exception(e.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultList;
    }
}
