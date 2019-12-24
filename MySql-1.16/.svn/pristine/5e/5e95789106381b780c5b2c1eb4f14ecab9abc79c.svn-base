/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.dto.NeftRtgsOutwardPojo;
import com.cbs.dto.neftrtgs.NeftOwDownloadDTO;
import com.cbs.dto.ReInitiateNftPojo;
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
import com.cbs.pojo.neftrtgs.NeftDataReconsilationPojo;
import com.cbs.pojo.neftrtgs.NeftRtgsReversalPojo;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface NeftRtgsMgmtFacadeRemote {

    public String saveEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException;

    public String updateEpsBranchMaster(EPSBranchMaster entity) throws ApplicationException;

    public EPSBranchMaster getEpsBranchMaster(String branchCode) throws ApplicationException;

    public String saveEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException;

    public String updateEpsBranchPaySysDetail(EPSBranchPaySysDetail entity) throws ApplicationException;

    public EPSBranchPaySysDetail getEpsBranchPaySysDetail(String paySysId) throws ApplicationException;

    public String saveEpsBankMaster(EPSBankMaster entity) throws ApplicationException;

    public String updateEpsBankMaster(EPSBankMaster entity) throws ApplicationException;

    public EPSBankMaster getEpsBankMaster(String bankCode) throws ApplicationException;

    public String saveEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException;

    public String updateEpsChargeDetails(EPSChargeDetails entity) throws ApplicationException;

    public EPSChargeDetails getEpsChargeDetails(String chargeEventID) throws ApplicationException;

    public String saveEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException;

    public String updateEpsCorrbranchDetails(EPSCorrBranchDetail entity) throws ApplicationException;

    public EPSCorrBranchDetail getEpsCorrbranchDetails(String branchCode) throws ApplicationException;

    public String saveEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException;

    public String updateEpsNodalBranchDetails(EPSNodalBranchDetail entity) throws ApplicationException;

    public EPSNodalBranchDetail getEpsNodalBranchDetails(String outwardPoolAccId) throws ApplicationException;

    public String saveEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException;

    public String updateEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException;

    public String deleteEpsMessageCategory(EPSMessageCategory entity) throws ApplicationException;

    public EPSMessageCategory getEpsMessageCategory(String msgSubType) throws ApplicationException;

    public List<EPSInwardCredit> getNodalGridData(String paySysId, String msgStatus) throws ApplicationException;

    public void saveAck(EPSAckMessage entity) throws ApplicationException;

    public void saveReceivedMessage(EPSInwardCredit entity) throws ApplicationException;

    public EPSN06Message findBySequenceNo(String tranId) throws ApplicationException;

    public String updateBySequenceNo(EPSN06Message entity) throws ApplicationException;

    public EPSInwardCredit getInwardCrByUTR(String utr) throws ApplicationException;

    public void updateInwardCreditByUTR(EPSInwardCredit entity) throws ApplicationException;

    public List<EPSNodalBranchDetail> getNodalRTGSDrAcc(String paySysId) throws ApplicationException;

    public List<String> getMessageType(String paySysId) throws ApplicationException;

    public String getAlphaCode(String brcode) throws ApplicationException;

    String getUTRNumber(String paySysId) throws ApplicationException;

    public String getDrAccInformation(String acno, String paySysId) throws ApplicationException;

    public List<String> getChargeEvent(String paySysId) throws ApplicationException;

    public String getReceiverDetails(String bankCode, String branchCode) throws ApplicationException;

    public String getOrderingDetails(String branchCode) throws ApplicationException;

    public String saveButton(EPSN06Message entity) throws ApplicationException;

    public EPSN06Message onBlurUtrNumber(String utrNo) throws ApplicationException;

    public String updateAllInformation(EPSN06Message allData) throws ApplicationException;

    public String insertTransaction(String addedBy, String modifiedBy, String drAccID, String crAccID, double amount, String particulars, String instrumentType, String instrNo, Date transDt, String chargeEventID, String chargeAccountID) throws ApplicationException;

    public List<String> getCashInHandAccInfo(String paySysId, String orgnBrCode) throws ApplicationException;

    public AccountTypeMaster selectAcctNature(String accNo) throws ApplicationException;

    public String saveNeftOwDetails(List<NeftOwDetails> entityList, String orgnCode, String userName, String todayDt) throws Exception;

    public List getNeftUnAuthBatchNos(String orgBrnid, String status, String auth, String currentDt) throws Exception;

    public String deleteNeftOwDetails(List<NeftRtgsOutwardPojo> neftOwDetailList, String orgnCode, String userName, String todayDt) throws Exception;

    public String verifyNeftOwDetails(List<NeftRtgsOutwardPojo> neftOwDetailList, String orgnCode, String userName, String todayDt) throws Exception;

//    public String reverseOutwardTransaction(NeftOwDetails obj, String brncode, String user,
//            CbsAutoNeftDetails neftAutoObj) throws ApplicationException;
    public String reverseOutwardTransaction(NeftOwDetails obj, String brncode, String user,
            String outwardHead) throws ApplicationException;

    public List getChargeApplyOnCustomer(String chargeType, double amount, String accountType, String orgBrCode) throws ApplicationException;

    public List<NeftRtgsReversalPojo> getNeftRtgsReversalData(String orgBrnid, String date) throws ApplicationException;

    public String neftRtgsReversal(NeftOwDetails obj, String userName) throws ApplicationException;

//    public CbsAutoNeftDetails findAutoNeftDetailForOutward() throws ApplicationException;
    public List<NeftOwDetails> getOwUnAuthEntryBranch(Double trsNo, String orgBrnid, String status, String auth, String currentDt) throws Exception;

    public String getNeftOutwardFileBkpLocation(String processType) throws Exception;

    public List<NeftOwDownloadDTO> getGeneratedFiles(String fileType, String orgnBrCode, String fileGeneratedDate) throws Exception;

    public String getSponsorAcctNo() throws ApplicationException;

    public CbsAutoNeftDetails getIblWebServiceObject() throws ApplicationException;

    public String branchNameByIfsc(String ifscCode) throws ApplicationException;

    public void getImpsTxnStatus(NeftRtgsOutwardPojo obj, String impsBin, String userName, String logUrl, String txnUrl);

    public List<ReInitiateNftPojo> getReInitateNftDetails(String date, String branch) throws ApplicationException;
    //impsgenreation

    public List getverfiedCustomerDetails(String acno, String orgnBrCode) throws ApplicationException;

    public List getCustomerDetails(String acno) throws ApplicationException;

    public String getImpsGenertaionCancleAdddata(String acno, String name, String mobileno, String date, String validdata,
            String institutionId, String request_beneficaryName, String beneficaryAccountno, String beneficaryIFSC,
            String beneficarymobile, String beneficaryMMId, double tranAmount, String remark, String user, String mode, String function, String chagreamount) throws ApplicationException;

    public List getImpsGridDetails(String acno, String date, String mode, String function, String orgBrnCode) throws ApplicationException;

    public String getVerifyDataOnVerifyDelete(String accno, String branch, String function, String mode, String username, String date, String stancode) throws ApplicationException;

    public String getGenerateCancleProcess(String accountNo, String date, String branch, String function, String mode,
            String Stancode, String userName) throws ApplicationException;

    public String getreinitateNeftupdation(String refNo, String userName, String date) throws ApplicationException;

    public List autoNeftBankNameList() throws ApplicationException;

    public String findOutwardHeadByNeftBankName(String neftBankName) throws ApplicationException;

    public String insertNeftDataReconsilation(List<NeftDataReconsilationPojo> dataList) throws ApplicationException;

    public List<NeftDataReconsilationPojo> getNeftDataReconsilation(String reconDate) throws ApplicationException;

    public String getAccountMobileNo(String accountNo) throws Exception;

    public boolean isTxnReversed(String resCode);
}
