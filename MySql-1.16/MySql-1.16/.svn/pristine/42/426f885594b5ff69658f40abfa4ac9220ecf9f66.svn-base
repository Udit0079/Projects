/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.neftrtgs;

import com.cbs.dto.NpciFileDto;
import com.cbs.dto.SSSRejectDto;
import com.cbs.dto.other.PmSchemeRegDto;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.SSSRegistrationPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author root
 */
@Remote
public interface SSSFileGeneratorFacadeRemote {

    public List getPmbsData() throws ApplicationException;

    public List getCustDetailByAcno(String acno) throws ApplicationException;

    public List getVendors(String schemeCode) throws ApplicationException;

    public List getPmRegDetail(String funcFlag, String schemeCode, String vendorCode, String brCode, String acNo) throws ApplicationException;

    public String pmbsDetailSave(String schemeCode, String vendorCode, String premiumAmt, String insuredAamt, String schemeGl, String glAmt,
            String schemePl, String plAmt, String agentAmt, String effectiveDate, String enterBy, String enterDate, String policyNo, String autoFreq) throws ApplicationException;

    public String pmbsDetailUpdate(String txnId, String schemeCode, String vendorCode, String premiumAmt, String insuredAamt, String schemeGl,
            String glAmt, String schemePl, String plAmt, String agentAmt, String effectiveDate, String enterBy, String enterDate, String policyNo) throws ApplicationException;

    public String pmRegistrationDetailSaveUpdateVeryfy(String bntButton, String acNo, String nomName, String nomDob, String nomAdd, String nomRelationship,
            String nomAadhar, String guardianName, String guardianAdd, String spouseName, String spouseAadhar, String schemeCode, String disability,
            String disabilityDetails, String enrolFlag, String enrolDate, String cancelDate, String enterBy, String enterDate, String auth,
            String authBy, String brnCode, String minorFlag, String vendorCode, String pran, String pensionAmt, String contributionAmt, String incomeTax, String swalban, String apyState) throws ApplicationException;

    public List getMaxEffectDate() throws ApplicationException;

    public String generateSSSFiles(String schemeCode, String vendor, String fileGenDt, String todayDt, String orgnBrCode, String enterBy, String premium,
            String utrNo, String utrDate) throws ApplicationException;

    public List<NpciFileDto> showGeneratedFiles(String fileType, String fileShowDt) throws ApplicationException;

    public String calculatePremium(String schemeCode, String vendor, String fileGenDt) throws ApplicationException;

    public List chkSSSAcReg(String schemeCode, String vendorCode, String acno) throws ApplicationException;

    public double getPremiumAmt(String schemeCode, String vendorCode) throws ApplicationException;

    public List<SSSRegistrationPojo> getSSSReportData(String brCode, String schemeCode, String vendorCode, String frDt, String toDt, String status,String genType,String repType) throws ApplicationException;

    public List<SSSRegistrationPojo> getSSSReportSummaryData(String brCode, String schemeCode, String vendorCode, String frDt, String toDt, String status) throws ApplicationException;

    public List getSSSDetail(String schemeCode, String vendorCode) throws ApplicationException;

    public double getContributationAmount(int age, String pensionAmount) throws ApplicationException;

    public String uploadPmSBYRejection(String schemeCode, String vendor, String ctrlFileId, String upldFileId,
            List<SSSRejectDto> inputList, String uploadingUserName, String todayDt) throws Exception;

    public String generateSSSReturnFiles(String schemeCode, String vendor, String fileGenDt,
            String ctrlFile, String todayDt, String enterBy, String uploadBrCode) throws Exception;

    public List getActualFile(String schemeCode, String vendorCode, String fDt) throws ApplicationException;

    public List<SSSRegistrationPojo> getRejectedFileDetail(String brCode, String schemeCode,
            String vendorCode, String fileName, String fDt) throws ApplicationException;

    public List getSssRenewData(String schemeCode, String vendorCode, String orgnBrCode) throws ApplicationException;

    public String sssRenewProcess(List<PmSchemeRegDto> gridDetail, String schemeCode, String vendorCode,
            String orgnBrCode, String userName, List<PmSchemeRegDto> reportList) throws ApplicationException;
}
