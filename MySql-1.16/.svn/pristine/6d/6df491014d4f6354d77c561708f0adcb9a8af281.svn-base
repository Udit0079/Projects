/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.Npcih2hfilePojo;
import com.cbs.dto.other.CbsMandateDetailPojo;
import com.cbs.dto.other.MandateRecordTo;
import com.cbs.dto.other.NpciOacDto;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.pojo.OnlineAadharRegistrationPojo;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface OtherNpciMgmtFacadeRemote {

    public List findAllFileSeqNo(String fileComingDt, String iwType) throws ApplicationException;

    public List findH2HAllFileSeqNo(String fileComingDt, String iwType) throws ApplicationException;

    public List<NpciOacDto> getDataForInComingDateAndSeqNo(String fileComingDt, String seqNo,
            String iwType, String brnCode, String selectedBranch) throws ApplicationException;

    public String verifyEntry(NpciOacDto currentItem, String cbsAcno, String cbsName,
            String acValid, String reason, String userName, String fileComingDt, String fileSeqNo, String iwType) throws ApplicationException;

    public String generateOacReturnFiles(String fileGenDt, String fileSeqNo, String orgnBrCode,
            String enterBy, String todayDt, String iwType, String processingMode, String h2hLocation) throws ApplicationException;

    public List<NpciFileDto> showOacFiles(String fileType, String fileShowDt) throws ApplicationException;

    public String ecsMandate(NpciOacDto currentItem, String cbsAcno, String cbsName,
            String status, String reason, String userName, String fileComingDt,
            String fileSeqNo, String iwType, Double amount, String todayDt,
            String loginBrCode, String selectedBranch) throws ApplicationException;

    public String generateEcsDrReturnFiles(String fileGenDt, String fileSeqNo, String orgnBrCode,
            String enterBy, String todayDt, String iwType, String processingMode, String h2hLocation) throws ApplicationException;

    public List<MandateRecordTo> retrieveMandateData(String acno) throws ApplicationException;

    public List<CbsMandateDetailPojo> retrieveMandateDataNew(String npciFileType, String function, String modifyCriteria, String acno, String orgnCode) throws ApplicationException;

    public Long getUmrn(String txnFileType) throws ApplicationException;

    public String mandateProcess(CbsMandateDetailPojo cbsMandateDetail) throws ApplicationException;

    public String getRefRecCode(String refRecNo, String refDesc) throws ApplicationException;

    public List findAllFileSeqNoForCreditInward(String fileComingDt, String iwType) throws ApplicationException;

    public List findH2HAllFileSeqNoForCreditInward(String fileComingDt, String iwType) throws ApplicationException;

    public List<NpciOacDto> getDataForEcsCrInComingDateAndSeqNo(String fileComingDt, String seqNo,
            String iwType, String brnCode, String selectedBranch) throws ApplicationException;

    public String processCecsInwCredit(NpciOacDto obj, String userName, String todayDt, String orgnBrCode,
            String iwType, String settlementDt, String achSeqNo, String processAcNo, String processAcHolderName,
            String selectedBranch, String verificationStatus, String mode) throws ApplicationException;

    public String generateEcsIputFile(String fileType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt) throws ApplicationException;

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fromDate, String toDate) throws ApplicationException;

    public List retrieveTxnData(String txnType, String orgnCode) throws ApplicationException;

    public String processEcsInputTxn(List<NpciInputPojo> processList, String txnType, String userName,
            String todayDt, String orgnCode) throws ApplicationException;

    public String generateECS306ReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo, String processingMode, String h2hLocation) throws ApplicationException;

    public String generateAchDr306ReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo, String processingMode, String h2hLocation) throws ApplicationException;

    public List isFileUploaded(String zipFileName) throws ApplicationException;

    public Integer retrieveFileNo(String mandateType, String curDt, String mandateMode) throws ApplicationException;

    public String uploadMmsData(File dir, String currentDt, String userName, Integer fileNo,
            String uploadedFileName, String mandateMode, String processingMode) throws ApplicationException;

    public String[] getIfscAndMicrCodeByBrnCode(String orgnCode) throws ApplicationException;

//    public List<NpciOacDto> getRegisteredAadharAtCbs(List<String> list) throws ApplicationException;
    public String markAadharInactive(List<NpciOacDto> list, String userName) throws ApplicationException;

    public List<List<NpciOacDto>> getRegisteredAadharAtCbs(List<String> activeList, List<String> inActiveList) throws ApplicationException;

    public List getMandateDetail(String umrn) throws ApplicationException;

    public List<NpciOacDto> getDataForEcsCrOnInComingDate(String fileComingDt, String iwType) throws ApplicationException;

    public List<String[]> getUnAuthNPCIFile() throws ApplicationException;

    public String[] getAcHolderDetail(String AcNo) throws ApplicationException;

    public String generateMandateIputFile(List<CbsMandateDetailPojo> mandtDtlList, String txnType, String fileGenDate,
            String enterDate, String enterBy, String brnCode, String settlementDt, String achNewOrLegacyFlag) throws ApplicationException;

    public List<CbsMandateDetailPojo> getUmrDetailsOnmandateDate(String fileType, String fileTransType, String fileGenDt,
            String forgetFlag, int forgetDay, String seqType, String freqnType, String orgnBrCode,
            String enterBy, String achNewOrLegacy) throws ApplicationException;

    public List<String[]> getACHOrECSInputGeneratedFile(String fileGenType, String fromDate, String toDate, String brnCode) throws ApplicationException;

    public String generateECSNewIputFile(List<CbsMandateDetailPojo> mandtDtlList, String txnType, String fileGenDate,
            String enterDate, String enterBy, String brnCode, String settlementDate) throws ApplicationException;

    public List getOlineAadharRegistrationData(String frDt, String toDt, String brnCode) throws ApplicationException;

    public String onlineAadharUpdation(List<OnlineAadharRegistrationPojo> dataList, String enterDate, String enterBy, String brnCode) throws ApplicationException;

    public String getAadharNoByCustId(String custId) throws ApplicationException;

    public String uploadEsignData(File dir, String currentDt, String userName, Integer fileNo,
            String uploadedFileName, String mandateMode, String processingMode) throws ApplicationException;

    public List<Npcih2hfilePojo> getFileDetail(String date) throws ApplicationException;

//    public List<Npcih2hfilePojo> getFileInwardDetail(String date) throws ApplicationException;
    public List isH2HFileDetailexits(String fileName) throws ApplicationException;

    public String h2hfileinsertionResponseCase(String fileName) throws ApplicationException;
}
