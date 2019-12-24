/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.clg;

import com.cbs.dto.NpciFileDto;
import com.cbs.exception.ApplicationException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface CtsManagementFacadeRemote {

    public List getBranch();

    public List getAcctCode();

    public String fnABBGetCtsDetails(String date, String brn, Integer scheduleNo) throws ApplicationException;

    public List getMicrDetails(String bankCode1, String bankCode2, String bankCode3) throws ApplicationException;

    public String getClngDetails(String accNo) throws ApplicationException;

    public String getSeqNo(String instNo, String instAmt, String instDate, String aplhaCode) throws ApplicationException;

    public String saveTxnDetails(String TXN_ID, String BATCH_NO, String ACNO, String INST_NO, String INST_AMT, String INST_DT,
            String FAVOR_OF, String BANK_NAME, String BANK_ADD, String ENTER_BY, String SEQ_NO, String ORGN_BRANCH,
            String DEST_BRANCH, String DT, String imgcode, String custname, String oper_mode, String prbankcode,
            String rbirefno, String userdetails, Integer scheduleNo) throws ApplicationException;

    public String getOrgnBranch(String alphaCode);

    public String getBranchCtsFolder(String orgnCode) throws ApplicationException;

    public String dataVerification(int branchCode, int scheduleNo) throws ApplicationException;

    public int getDestBrnCode(String alphaCode) throws ApplicationException;

    public String getBranchAplhaCode(int brnCode) throws ApplicationException;

    public String chequeValidate(String acno, String chqNo) throws ApplicationException;

    public String getBranchName(int originBrCode) throws ApplicationException;

    public List getReason() throws ApplicationException;

    public List getGridDetails(Integer branchCode) throws ApplicationException;

    public String chequesAuthorization(String acno, String enterBy, String orgnCode, String destCode,
            String details, String secondAuthBy, String authBy, String instNo, String screenFlag,
            String payee, String bankname, String bankAddress, Integer status, Float seqNo, Integer payBy,
            Integer reasonForCancel, Integer vchNo, Integer batchNo, String txnId, double instAmt, String dt,
            String instDt, Integer tranType, String subStatus, Integer requestType, String circleType,
            Integer ty, String remarks, String userDetails, String exccedFlag, int ctsSponsor, String otherReasonText, String custSate, String branchState) throws ApplicationException;

    public String getTotalChequeDetail(String orgnCode) throws ApplicationException;

    public String completion(String orgnCode, String enterBy) throws ApplicationException;

    public List getImageDetails(String imageCode, String orgCode) throws ApplicationException;

    public String makeImageBackup(String imageType, String imageCode, String encyImgFile, String acno,
            String instNo, Float instAmt, String instDt, String favourOf, String bankName, String enterBy,
            String authBy, int status, Float seqNo, String orgBranch, String destBranch, String clgDt,
            String details, String auth, String substatus, String custname, String opermode, String userdetails,
            String prbankcode, String rbirefno, Integer scheduleNo) throws ApplicationException;

    public String uploadDataInCell(String txtFilePath, String pxfFilePath, String ctsBranchCode,
            String zipFileName, Integer scheduleNo, String userName) throws ApplicationException;

    public List getUnAuthorizedInstrument(String todayDate, String branchCode) throws ApplicationException;

    public String saveManualInward(String TXN_ID, String BATCH_NO, String ACNO, String INST_NO, String INST_AMT, String INST_DT, String FAVOR_OF,
            String BANK_NAME, String BANK_ADD, String ENTER_BY, String SEQ_NO, String ORGN_BRANCH, String DEST_BRANCH, String DT,
            String imgcode, String custname, String oper_mode, String prbankcode, String rbirefno, String userdetails) throws ApplicationException;

    public String getModuleActiveCode(String moduleName) throws ApplicationException;

    public String fnLpading(String padVar, Integer padLength, String padChar) throws ApplicationException;

    public String valBillHead(String acNo, String instNo, String instAmt, String instDate, String aplhaCode) throws ApplicationException;

    public List getGridDetailsCts(Integer branchCode, Integer scheduleNo, int ctsSponsor, String clgType, int status) throws ApplicationException;

    public Integer getScheduleNo(String curDt, Integer branchCode) throws ApplicationException;

    public boolean isZipFileAlreadyUploaded(String zipFileName, Integer branchCode, String curDt) throws ApplicationException;

    public List getScheduleNoList(String alphacode, String date) throws ApplicationException;

    public List getTransactionScheduleNos(String date, int brnCode, String clearingType, int ctsSponsor) throws ApplicationException;

    public String getTotalChequeDetailCts(int brnCode, int scheduleNo) throws ApplicationException;

    public String completionCts(Integer brnCode, Integer scheduleNo, String userName) throws ApplicationException;

    public List getImageDetailsCts(String imageCode, Integer brnCode, Integer scheduleNo) throws ApplicationException;

    public List<String> getIwAmountOnCurrentDt(String orgnCode) throws ApplicationException;

    public String generateCtsReturnFile(String branch, String fileDt, String dirLocation,
            String useName, String orgnBrCode, String todayDt) throws Exception;

    public List<NpciFileDto> showCtsReturnFiles(String branch, String fileGenDt) throws ApplicationException;

    public String odBalStopUpdation(String txnId, String authBy, String returnMsg) throws ApplicationException;

    public String getGlHeadName(String acno) throws ApplicationException;

    public List getScheduleNoList1(String alphacode, String date) throws ApplicationException;

    public List fnABBGetCtsDetails1(String curDt, String branchAlphaCode, Integer scheduleNo) throws ApplicationException;

    public String npciSaveClgInwardDetail(String txnId, String accNo, String instNo, BigDecimal instAmount,
            String instDate, String details, String inFavourOf, String bankName, String bankAddress, String userName,
            String seqNo, String custName, String opMode) throws ApplicationException;
}
