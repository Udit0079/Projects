/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.ShareEnquiryPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.dto.report.ShareAccountStatementPojo;
import com.cbs.dto.report.ShareCertPojo;
import com.cbs.dto.report.ShareHoldersPojo;
import com.cbs.dto.report.SharePaymentPojo;
import com.cbs.dto.report.ShareStatusPojo;
import com.cbs.dto.report.AreaWiseSharePojo;
import com.cbs.dto.report.MemberLabelPojo;
import com.cbs.dto.report.ShareDetailsReportPojo;
import com.cbs.dto.report.ShareDividendDetailPojo;
import com.cbs.dto.report.ShareDividendPojo;
import com.cbs.dto.report.ShareFinalBalanceReportPojo;
import com.cbs.dto.report.ShareIssuedPojo;
import com.cbs.dto.report.ShareMemberDetailPojo;
import com.cbs.dto.report.ShareTransferPojo;
import com.cbs.pojo.ShareCertificatePojo;
import com.cbs.pojo.MemberShareLetterPojo;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author admin
 */
@Remote
public interface ShareReportFacadeRemote {

    //public List getFolioStatementList(java.lang.String folioNo, java.lang.String date);
    public List getFolioStatementList(String folioNo, String date) throws ApplicationException;

    public List< ShareTransferPojo> getTransfererReport(String frmDt, String toDt) throws ApplicationException;

    public List listEnterBy() throws ApplicationException;

    public List<ShareEnquiryPojo> shareEnquiryReport(String fromFolio, String toFolio, String firstStarShareHolderName, String firstName,
            String LastStarShareHolderName, String LastName, String startHouseNo,
            String houseNo, String startSector, String sector, String startCity, String city, String enterBy, String fatherName) throws ApplicationException;

    public List< ShareFinalBalanceReportPojo> getFinalBalanceReport(String frmfoliono, String tofoliono, String dt,String alphaCode,String actCategory) throws ApplicationException;

    List<ShareAccountStatementPojo> getAccountStatement(String folioNo, String frmDt, String toDt) throws ApplicationException;

    public List< ShareDetailsReportPojo> getShareDetailsReport(java.lang.String query, String dt) throws ApplicationException;

    public List<ShareCertPojo> shareCertificateReport(String fromDt, String toDt) throws ApplicationException;

    public List<SharePaymentPojo> sharePaymentReport(String fromDt, String toDt) throws ApplicationException;

    public List< ShareDividendPojo> getDividenDreport(String frmDt, String toDt, String foliono) throws ApplicationException;

    public List<ShareStatusPojo> getShareStatus(int frShareNo, int toShareNo, String status) throws ApplicationException;

    public List getBrnNameandBrnAddress(String alphacode) throws ApplicationException;

    public List getAlphacodeList() throws ApplicationException;

    public List<ShareHoldersPojo> getShareHolders(String alphaCode, String frAcNo, String toAcNo, String frDt,String toDt, String orderBy, String accountCategory,String status,String repOption) throws ApplicationException;

    public List< ShareIssuedPojo> getTransfererReportWise(String frmDt, String toDt);

    public List getAreaWiseList(String branch) throws ApplicationException;

    public List<AreaWiseSharePojo> getAreaWiseShareData(String brnCode, String area, String date) throws ApplicationException;

    public List<String> getShareCertificateData(String folioNo, String certficateNo, String brCode) throws ApplicationException;

    public List<ShareCertificatePojo> getShareCertificateData1(String repType, String fromFolio, String toFolio, String frDt, String toDt, String folioNo, String certficateNo, String brCode, String fromCertNo, String toCertNo) throws ApplicationException;

    public List<ShareDividendDetailPojo> getShareDividendDetail(String finYear, String repType, String area) throws ApplicationException;

    public List fYearList() throws ApplicationException;

    public List getCertificateNoList(String folioNo) throws ApplicationException;

    public List getAreaTypeList() throws ApplicationException;

    public List<MemberShareLetterPojo> getAdditionNewShareReport(String frDt, String toDt, String member, String orgnbrcode) throws ApplicationException;

    public List<MemberLabelPojo> getMemberLabelReportData(String fromDate, String toDate, String status, String custEntityType, String nomDtl) throws ApplicationException;

    public List<ShareMemberDetailPojo> getMemberDetailsReport(String memberType, String memberNo, String accountCatageory, String asOnDate) throws ApplicationException;
    
    public String refDesc(String refNo, String refCode) throws ApplicationException;
    
    public List<ShareHoldersPojo> getRetireShareMemberData(String orgnbrcode ,int retireAge,int retirePeriod,String frDt) throws ApplicationException;
     
     
}
