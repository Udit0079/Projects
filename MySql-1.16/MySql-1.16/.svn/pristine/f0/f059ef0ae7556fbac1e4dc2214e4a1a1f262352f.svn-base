/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.facade.report;

import com.cbs.dto.report.DatedSecurityPojo;
import com.cbs.dto.report.LimitRenewalNoticePojo;
import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.LoanMprsPojo;
import com.cbs.dto.report.LoanPeriodPojo;
import com.cbs.dto.report.StatemenrtOfRecoveriesPojo;
import com.cbs.dto.report.SuretyLetterPojo;
import com.cbs.dto.report.ho.DemandRecoveryDetailPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.pojo.FreshRenewalEnhashmentPojo;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author SAMY
 */
@Remote
public interface OtherLoanReportFacadeRemote {

    public List<LimitRenewalNoticePojo> getlimitRenewalNotice(String acNature, String accountType, String fromDate, String toDate, String orgnbrcode) throws ApplicationException;

    public List<DatedSecurityPojo> datedSecurity(String dt1, int days, String security_type, String securityStatus, String brnCode, String acctNature, String acType) throws ApplicationException;

    public List<LoanPeriodPojo> loanPeriodReport(String acctCode, int term, int frmperod, int toperiod, float rifrm, float rito, double senfrmamt, double sentoamt, String frmdt, String todt, String scemeCode, String brCode, String reportType, String basedReport, String loanPeriod, String frmDtInt, String toDtInt) throws ApplicationException;

    public List<LoanMisCellaniousPojo> cbsLoanMisLaniousReport(String accountType, String psector, String psubSector, String pschemeCode, String psecure, String pappCategory, String pweekersSection, String pcategoryOption, String prelation, String brncode, String pminCommunity, String frmDt) throws ApplicationException;

    public List<FreshRenewalEnhashmentPojo> getFreshRenewalEnhshmentData(String branchCode, String frDt, String toDt, String reportOption, String acNature, String acType) throws ApplicationException;

    public List<SuretyLetterPojo> getloanSuretyLetter(String frDt, String toDt, String branchCode) throws ApplicationException;

    public List<StatemenrtOfRecoveriesPojo> getDemandRecoveriesData(String area, String tillDate, String brCode, String repType, String optType, String reportType) throws ApplicationException;

    public List<StatemenrtOfRecoveriesPojo> getDemandRecoveriesSummaryData(String area, String tillDate, String brCode, String repType, String optType, String reportType) throws ApplicationException;

    public List getDepositeMpr(String reportType, String col1, String col2, String col3, String col4, String incDec, String monQuter, String fromDt, String toDt, String brnCode, boolean noOfAcsBox, boolean tarAcsBox, boolean tarAmtBox, boolean osAmtBox) throws ApplicationException;

    public List<LoanMprsPojo> getLoanMpr(String fdt, String tdt, String cmbOption, boolean chkTarac, boolean chkTaramt, boolean chkAc1, boolean chkOsbal1, boolean chkRec1, boolean chkDisbAmt1, String cmbReptype, boolean chkDisb2, boolean chkRec2, boolean chkOsbal2, boolean chkOs1, boolean chkOs2, boolean chkLastMon, boolean chkCurMon, boolean chkAc2, boolean chkIncrDcr, String incrdcr, String brnCode, boolean chkOverdue1, boolean chkOverDue2) throws ApplicationException;
    
    public List<FreshRenewalEnhashmentPojo> getFreshRenewalEnhshmentLetter(String branchCode, String frDt, String toDt, String reportOption, String acNature, String acType) throws ApplicationException;

    public List<DemandRecoveryDetailPojo> getDemandRecoveryDetail(String brncode ,String acNature,String actype,String fromDt ,String toDate ) throws ApplicationException;
    
    public HashMap<String,String> getThriftAreawiseAcc(String area, String branch) throws ApplicationException ;
}
