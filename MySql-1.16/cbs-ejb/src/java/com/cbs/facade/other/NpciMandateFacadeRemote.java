/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.other;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.other.AutoMandateTo;
import com.cbs.dto.report.AccWiesMMSRepPojo;
import com.cbs.dto.report.ho.MmsReportPojo;
import com.cbs.exception.ApplicationException;
import java.io.File;
import java.util.List;
import javax.ejb.Remote;

@Remote
public interface NpciMandateFacadeRemote {

    public List getAllUploadedFileNo(String uploadDt, String mandateType, String mmsMode) throws ApplicationException;
    
    //This method will replace from "getAllUploadedFileNo". For this we have add mandate mode on MMS return generation form.
    public List getAllUploadedFileNoAtGenerationTime(String uploadDt, String mandateType,String mandateMode) throws ApplicationException;

    public List getMandateDetails(String uploadDt, Integer fileNo, String mandateType, String brnCode, String mandateMode) throws ApplicationException;

    public String verifyMms(AutoMandateTo obj, String mode, String returnCode, String uploadDt, String mandateType,
            Integer fileNo, String userName, String todayDate, String mandateMode, String acNo) throws ApplicationException;

    public List<NpciFileDto> showMmsFiles(String fileType, String mandateType, String fileShowDt, Double seqNo) throws ApplicationException;

    public String generateMmsCreateReturn(String mmsType, String fileUpDt, String fileNo,
            String todayDt, String userName, String orgnBrCode, String processingMode, String H2HLocation,String mandateMode) throws ApplicationException;

    public List<AutoMandateTo> getPreviousMandateDetail(String umrn) throws ApplicationException;

    public List<MmsReportPojo> getMmsReport(String selectedBr, String manDateMode, String mandateType, String reportType,
            String frDt, String toDt) throws ApplicationException;

    public String updateMandateStatus(String umrn, String debtorAccount, String orgnBrCode, String userName) throws ApplicationException;

    public List<AutoMandateTo> getStopUmrnDetails(String selectBranch, String orgnBrCode) throws ApplicationException;

    public List getLegacyMandateFileData(String fileType, String mandateType, String uploadDt) throws ApplicationException;

    public String generateMmsInitiationFile(String mmsType, String tillDt,
            String todayDt, String userName, String orgnBrCode) throws ApplicationException;

    public String mmsAcknowledgementAndResponseUpdation(File directory, String uploadedFileName, String userName) throws Exception;

    public List<AccWiesMMSRepPojo> mmsReportData(String reportType, String accno, String umrn, String fromDate, String toDate) throws Exception;

    public List<AutoMandateTo> getArchievingDetails(String oldAcno, String newAcno, String function) throws ApplicationException;

    public List getCbsAcnoFromMmsDetail(String umrn) throws ApplicationException;

    public String mmsxmlandCancelUpdation(File upFile, String uploadedFileName, String userName) throws Exception;

    public List getMandateReverifyDetails(String date, String mandateType, String brnCode, String mandateMode,String umrn) throws ApplicationException;

    public List getMandateAcceptStatus(String mandtID, String mandateType, String mandateMode) throws ApplicationException;

    public String reVerifymandate(AutoMandateTo obj, String mode, String returnCode, String date, String mandateType, String user, String mandateMode, String acno) throws ApplicationException;

    public String generateEsignMmsReturn(String mmsType, String fileUpDt, String fileNo, String todayDt, String userName, String orgnBrCode, String processingMode, String H2HLocation,String mandateMode) throws ApplicationException;
}
