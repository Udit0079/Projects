/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.NpciInwardDto;
import com.cbs.dto.report.AccwiesECSACHReportPojo;
import com.cbs.dto.report.AchEcsResponseStatusReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.AadharLpgStatusPojo;
import com.cbs.pojo.AdharRegistrationDetailPojo;
import com.cbs.pojo.NpciInputPojo;
import com.cbs.pojo.neftrtgs.NeftRtgsReportPojo;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface NpciMgmtFacadeRemote {

    public String npciRegistration(String custId, String mappingStatus, String enterBy,
            String regType) throws ApplicationException;

    public String generateNpciFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String processingMode, String h2hLocation) throws ApplicationException;

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt,
            Double seqNo) throws ApplicationException;

    public List getBankDetails() throws ApplicationException;

    public String npciInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName, String processingMode) throws ApplicationException;

    public List getNpciBranch(String orgnBrCode) throws ApplicationException;

    public String generateNpciReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, Double seqNo, String processingMode, String h2hLocation) throws ApplicationException;

    public List getSeqNoForSettlementDate(String settlementDt, String iwType) throws ApplicationException;
    
    public List getH2HSeqNoForSettlementDate(String settlementDt, String iwType) throws ApplicationException;

    public List<NeftRtgsReportPojo> getNpciReportData(String frDt, String toDt, String status,
            String brCode, String repType, String dt, double trsNo) throws ApplicationException;

    public List<NeftRtgsReportPojo> getNpciACHReportData(String frDt, String toDt, String status,
            String brCode, String repType, String reportMode, String dt, String trsNo) throws ApplicationException;

    public String npciNonAadhaarInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt,String processingMode,String fileName) throws ApplicationException;

    public String npciCbdtInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt) throws ApplicationException;

    public String generateNonAadhaarInput(String lpgType, String fileGenDt,
            String orgnBrCode, String enterBy, String todayDt) throws ApplicationException;

    public String generateNonAadhaarReturn(String lpgType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo,String processingMode,String H2HLocation) throws ApplicationException;

    public String generateNonAadhaarCBDT(String lpgType, String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo) throws ApplicationException;

    public List getLPGTypeList(String refRecNo) throws ApplicationException;

    public List getSeqNoForNonAadhaar(String lpgType, String inComingDt) throws ApplicationException;

    public List<NpciFileDto> showNonAadhaarGeneratedFiles(String fileType, String lpgType, String fileShowDt,
            Double seqNo) throws ApplicationException;

    public String npciAchInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt) throws ApplicationException;

    public String generateAchReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, Double seqNo, String processingMode, String h2hLocation) throws ApplicationException;

    public String generateECSReturnFiles(String fileGenDt, String orgnBrCode, String enterBy,
            String todayDt, String seqNo) throws ApplicationException;

    public List<AdharRegistrationDetailPojo> getAdharRegistraionDetail(String brCode, String Adhar, String filter,
            String fromdate, String todate) throws ApplicationException;

    public List<AadharLpgStatusPojo> getAadharLpgStatus(String status, String aadharNo) throws ApplicationException;

    public String npciAadharResponseUpload(List<NpciInwardDto> inputList, String uploadingUserName,
            String todayDt, String uploadedFileName) throws ApplicationException;

    public String npciNonAadharResponseUpload(List<NpciInwardDto> inputList, String uploadingUserName,
            String todayDt, String uploadedFileName) throws ApplicationException;

//    public String npciEcsCreditInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
//            String todayDt, String uploadingUserName) throws ApplicationException;
    public String getParameterInfoReportValue(String reportName) throws ApplicationException;

    public String aadharDeactivation(String custId, String aadharNo, String deActivationType,
            String mappingStatus, String regType, String enterBy) throws ApplicationException;

    public List getNpciusername(String userName) throws ApplicationException;

    public String aadharLookUpFileGeneration(String brCode, String type, String filter,
            String frDt, String toDt, String dirName, String npciBankCode, String genBrCode,
            String genUser) throws ApplicationException;

    public List<NpciFileDto> showAadharLookUpFiles(String fileType, String fileGenDt) throws ApplicationException;

    public List<AadharLpgStatusPojo> getAadharAndLpgCbsRegistration(String type, String idNo) throws ApplicationException;

    public String npciOacUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode) throws ApplicationException;

    public String npciEcsDrUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode, String uploadedFileName)
            throws ApplicationException;

    public String npciCECSCreditInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode, String todayDt,
            String uploadingUserName, String processingMode) throws ApplicationException;

    public String npciInputSave(String func, String oldUref, String micr, String acType, String acno,
            String name, String amount, String type, String enterBy, String ownBankAcno) throws ApplicationException;

    public List<NpciInputPojo> getNpciInputDetails(String ectType) throws ApplicationException;

    public String getAchReturnReasonCode(String cbsMessage) throws ApplicationException;

    public String npciAchDr306Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String iwType, String processingMode, String uploadedFileName)
            throws ApplicationException;

    public String newNpciAchInwardUpload(List<NpciInwardDto> inputList, String uploadBrCode,
            String uploadingUserName, String todayDt, String fileName, String processingMode) throws ApplicationException;

    public boolean isBankIfsc(List brList, String destBankIIN);

    public boolean isBankMicr(List brList, String destBankIIN);

    public List getAllAchTrsNo(String iwType, String dt) throws ApplicationException;

    public List getAllAchDRFileSqnNo(String iwType, String dt) throws ApplicationException;

    public String[] makeAchMiscellaneousReturnDescription(String reason) throws ApplicationException;

    public List getSettlementDtForUnverifiedEntriesNPCIInward(String fileType) throws ApplicationException;

    public List getSettlementDtForUnverifiedEntriesNPCIOAC(String fileType) throws ApplicationException;

    public String npciAchDrResponse306Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String fileLocation, String fileName, String processingMode) throws ApplicationException;

    public String npciECSDrResponse156Upload(List<NpciInwardDto> inputList, String uploadBrCode,
            String todayDt, String uploadingUserName, String fileLocation, String fileName, String processingMode) throws ApplicationException;

    public List<AccwiesECSACHReportPojo> getAccwiesACHECSReportData(String fileType, String sponsorType, String txnType,
            String frDt, String toDt, String accountNo) throws ApplicationException;

    public List<AchEcsResponseStatusReportPojo> getAchEcsResponseStatusReportData(String txnType, String sponsorType,
            String proprietary, String fromDate, String toDate, String status) throws ApplicationException;

    public List getAllBankIfsc() throws ApplicationException;

    public List getFileComingDtForUnverifiedEntriesNpciNonAadharInward() throws ApplicationException;

    public List getLpgTypeForH2hAvFileGeneration(String Date, String seqNo) throws ApplicationException;

    public List getUploadDateForUnverifiedEntriesForReturnMMs() throws ApplicationException;

    public String updateAtmCardNoViaFile(String cardno, String acno,String user) throws ApplicationException;

    public List getAcNoInAtmCardMaster(String acno) throws ApplicationException;
}