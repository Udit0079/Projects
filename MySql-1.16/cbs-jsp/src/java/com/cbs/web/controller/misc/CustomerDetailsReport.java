package com.cbs.web.controller.misc;

import com.cbs.constant.CbsConstant;
import com.cbs.dto.report.OverdueEmiReportPojo;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.common.FtsPostingMgmtFacadeRemote;
import com.cbs.facade.misc.CustomerDetailsReportFacadeRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.facade.report.OverDueReportFacadeRemote;
import com.cbs.servlets.Init;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.pojo.misc.SecurityTable;
import com.cbs.web.pojo.misc.CustomerDetail;
import com.cbs.web.pojo.misc.OtherAcTable;
import com.hrms.web.utils.WebUtil;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;

public class CustomerDetailsReport extends BaseBean {

    Context ctx;
    CustomerDetailsReportFacadeRemote remoteObject;
    private String todayDate;
    private String date1;
    private String userName;
    private String orgnBrCode;
    private HttpServletRequest req;
    private String messages;
    //private List<SelectItem> acctTypeOption;
    private String accno;
    private String cname;
    private String opendate;
    private Date opendate1;
    private String opdate;
    private String relname;
    private String mailadd;
    private String phno;
    private String panno;
    private String acinstn;
    private String dname;
    private String detail;
    private String omode;
    private String otype;
    private String dob;
    private String cbook;
    private String ioption;
//    private String blnce;
    private BigDecimal blnce;
    private String roi;
    private String roivalue;
    private String rdinstall;
    private String rdinstallval;
    private String rdMat;
    private String rdMateval;
    private String rdMAt;
    private BigDecimal rdMAtval;
    private String fd;
    private String acNat;
    private String fdvalue;
    private String tabname;
    private String roivalue1;
    private Date roivalue2;
    private String jtMsg;
    private String jtName1;
    private String dob1;
    private String address1;
    private String panNo1;
    private String occupation1;
    private String fatherName1;
    private String jtName2;
    private String dob2;
    private String address2;
    private String panNo2;
    private String occupation2;
    private String fatherName2;
    private String jtName3;
    private String dob3;
    private String address3;
    private String panNo3;
    private String occupation3;
    private String fatherName3;
    private String jtName4;
    private String dob4;
    private String address4;
    private String panNo4;
    private String occupation4;
    private String fatherName4;
    private String nomineeName;
    private String nomineeAddress;
    private String nomineeRelation;
    private String loanMsg;
    private String sanctionLimit;
    private String sanctionDate;
    private String loanAcctRoi;
    private String amountDisbursed;
    private String overDue;
    private String recover;
    private String interestDue;
    private String category;
    private String classification;
    private String sector;
    private String schemes;
    private String period;
    private String securityType;
    private String securityValue;
    private String installAmount;
    private String paidEMI;
    private String overdueEMI;
    private String loanPeriod;
    private String principalAmt;
    private String status;
    private String nPADate;
    private String nPAIntrest;
    private String intOption;
    private String DPODLimit;
    private String penalROI;
    private String aDHOCLimit;
    private String aDHOCROI;
    private String aDHOCAppDate;
    private String aDHOCExpiry;
    private String subsidy;
    private String subsidyExpDate;
    /**
     * ******************** Variables Of MIS Details
     * *******************************
     */
    private String MisMsg;
    String sectorMis;
    String SubSector;
    String purposeAdvance;
    String modeAdvance;
    String typeAdvance;
    String secured;
    String guaranteeCover;
    String industryType;
    String sickness;
    String exposure;
    String restructuring;
    String sanctionLevel;
    String sanAuth;
    String intTable;
    String interestType;
    String schemeCode;
    String npaCal;
    String court;
    String misMsg;
    String modeOfSet;
    String debtWaiverReason;
    String assetClassReason;
    String populationGroup;
    /**
     * ******************** End Variables Of MIS Details
     * *******************************
     */
    /**
     * ******************** Variables Of Customer Details
     * *******************************
     */
    String custDetailMsg;
    String occup;
    String constitution;
    String casteCode;
    String communityCode;
    String businessSeg;
    String salesTurnover;
    String custStatus;
    String stateCode;
    String village;
    String block;
    String tehsil;
    String externalRatingLongTerm;
    String externalRatingShortTerm;
    String OperRiskRating;
    String CreditRiskRatingInternal;
    String stateCodeMinor;
    String oldAccNo;
    private final String jndiHomeNameFtsPost = "FtsPostingMgmtFacade";
    private FtsPostingMgmtFacadeRemote ftsPostRemote = null;
    private String custAcNoLen;
    private LoanReportFacadeRemote loanReportRemote;
    private CommonReportMethodsRemote common;
    private OverDueReportFacadeRemote overDueReport;

    public String getCustDetailMsg() {
        return custDetailMsg;
    }

    public void setCustDetailMsg(String custDetailMsg) {
        this.custDetailMsg = custDetailMsg;
    }

    public String getLoanMsg() {
        return loanMsg;
    }

    public void setLoanMsg(String loanMsg) {
        this.loanMsg = loanMsg;
    }

    public String getCreditRiskRatingInternal() {
        return CreditRiskRatingInternal;
    }

    public void setCreditRiskRatingInternal(String CreditRiskRatingInternal) {
        this.CreditRiskRatingInternal = CreditRiskRatingInternal;
    }

    public String getOperRiskRating() {
        return OperRiskRating;
    }

    public void setOperRiskRating(String OperRiskRating) {
        this.OperRiskRating = OperRiskRating;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getBusinessSeg() {
        return businessSeg;
    }

    public void setBusinessSeg(String businessSeg) {
        this.businessSeg = businessSeg;
    }

    public String getCasteCode() {
        return casteCode;
    }

    public void setCasteCode(String casteCode) {
        this.casteCode = casteCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(String custStatus) {
        this.custStatus = custStatus;
    }

    public String getExternalRatingLongTerm() {
        return externalRatingLongTerm;
    }

    public void setExternalRatingLongTerm(String externalRatingLongTerm) {
        this.externalRatingLongTerm = externalRatingLongTerm;
    }

    public String getExternalRatingShortTerm() {
        return externalRatingShortTerm;
    }

    public void setExternalRatingShortTerm(String externalRatingShortTerm) {
        this.externalRatingShortTerm = externalRatingShortTerm;
    }

    public String getOccup() {
        return occup;
    }

    public void setOccup(String occup) {
        this.occup = occup;
    }

    public String getSalesTurnover() {
        return salesTurnover;
    }

    public void setSalesTurnover(String salesTurnover) {
        this.salesTurnover = salesTurnover;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCodeMinor() {
        return stateCodeMinor;
    }

    public void setStateCodeMinor(String stateCodeMinor) {
        this.stateCodeMinor = stateCodeMinor;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    /**
     * ******************** End Variables Of Customer Details
     * *******************************
     */
    public String getMisMsg() {
        return misMsg;
    }

    public void setMisMsg(String misMsg) {
        this.misMsg = misMsg;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getSubSector() {
        return SubSector;
    }

    public void setSubSector(String SubSector) {
        this.SubSector = SubSector;
    }

    public String getAssetClassReason() {
        return assetClassReason;
    }

    public void setAssetClassReason(String assetClassReason) {
        this.assetClassReason = assetClassReason;
    }

    public String getDebtWaiverReason() {
        return debtWaiverReason;
    }

    public void setDebtWaiverReason(String debtWaiverReason) {
        this.debtWaiverReason = debtWaiverReason;
    }

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getGuaranteeCover() {
        return guaranteeCover;
    }

    public void setGuaranteeCover(String guaranteeCover) {
        this.guaranteeCover = guaranteeCover;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public String getInterestType() {
        return interestType;
    }

    public void setInterestType(String interestType) {
        this.interestType = interestType;
    }

    public String getModeAdvance() {
        return modeAdvance;
    }

    public void setModeAdvance(String modeAdvance) {
        this.modeAdvance = modeAdvance;
    }

    public String getModeOfSet() {
        return modeOfSet;
    }

    public void setModeOfSet(String modeOfSet) {
        this.modeOfSet = modeOfSet;
    }

    public String getNpaCal() {
        return npaCal;
    }

    public void setNpaCal(String npaCal) {
        this.npaCal = npaCal;
    }

    public String getPopulationGroup() {
        return populationGroup;
    }

    public void setPopulationGroup(String populationGroup) {
        this.populationGroup = populationGroup;
    }

    public String getPurposeAdvance() {
        return purposeAdvance;
    }

    public void setPurposeAdvance(String purposeAdvance) {
        this.purposeAdvance = purposeAdvance;
    }

    public String getRestructuring() {
        return restructuring;
    }

    public void setRestructuring(String restructuring) {
        this.restructuring = restructuring;
    }

    public String getSanAuth() {
        return sanAuth;
    }

    public void setSanAuth(String sanAuth) {
        this.sanAuth = sanAuth;
    }

    public String getSanctionLevel() {
        return sanctionLevel;
    }

    public void setSanctionLevel(String sanctionLevel) {
        this.sanctionLevel = sanctionLevel;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSectorMis() {
        return sectorMis;
    }

    public void setSectorMis(String sectorMis) {
        this.sectorMis = sectorMis;
    }

    public String getJtMsg() {
        return jtMsg;
    }

    public void setJtMsg(String jtMsg) {
        this.jtMsg = jtMsg;
    }

    public String getSecured() {
        return secured;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getTypeAdvance() {
        return typeAdvance;
    }

    public void setTypeAdvance(String typeAdvance) {
        this.typeAdvance = typeAdvance;
    }

    public String getLoanAcctRoi() {
        return loanAcctRoi;
    }

    public void setLoanAcctRoi(String loanAcctRoi) {
        this.loanAcctRoi = loanAcctRoi;
    }

    public String getIntOption() {
        return intOption;
    }

    public void setIntOption(String intOption) {
        this.intOption = intOption;
    }

    public String getSanctionLimit() {
        return sanctionLimit;
    }

    public void setSanctionLimit(String sanctionLimit) {
        this.sanctionLimit = sanctionLimit;
    }

    public String getDPODLimit() {
        return DPODLimit;
    }

    public void setDPODLimit(String DPODLimit) {
        this.DPODLimit = DPODLimit;
    }

    public String getaDHOCAppDate() {
        return aDHOCAppDate;
    }

    public void setaDHOCAppDate(String aDHOCAppDate) {
        this.aDHOCAppDate = aDHOCAppDate;
    }

    public String getaDHOCExpiry() {
        return aDHOCExpiry;
    }

    public void setaDHOCExpiry(String aDHOCExpiry) {
        this.aDHOCExpiry = aDHOCExpiry;
    }

    public String getaDHOCLimit() {
        return aDHOCLimit;
    }

    public void setaDHOCLimit(String aDHOCLimit) {
        this.aDHOCLimit = aDHOCLimit;
    }

    public String getaDHOCROI() {
        return aDHOCROI;
    }

    public void setaDHOCROI(String aDHOCROI) {
        this.aDHOCROI = aDHOCROI;
    }

    public String getAmountDisbursed() {
        return amountDisbursed;
    }

    public void setAmountDisbursed(String amountDisbursed) {
        this.amountDisbursed = amountDisbursed;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getInstallAmount() {
        return installAmount;
    }

    public void setInstallAmount(String installAmount) {
        this.installAmount = installAmount;
    }

    public String getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(String interestDue) {
        this.interestDue = interestDue;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getnPADate() {
        return nPADate;
    }

    public void setnPADate(String nPADate) {
        this.nPADate = nPADate;
    }

    public String getnPAIntrest() {
        return nPAIntrest;
    }

    public void setnPAIntrest(String nPAIntrest) {
        this.nPAIntrest = nPAIntrest;
    }

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public String getOverdueEMI() {
        return overdueEMI;
    }

    public void setOverdueEMI(String overdueEMI) {
        this.overdueEMI = overdueEMI;
    }

    public String getPaidEMI() {
        return paidEMI;
    }

    public void setPaidEMI(String paidEMI) {
        this.paidEMI = paidEMI;
    }

    public String getPenalROI() {
        return penalROI;
    }

    public void setPenalROI(String penalROI) {
        this.penalROI = penalROI;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPrincipalAmt() {
        return principalAmt;
    }

    public void setPrincipalAmt(String principalAmt) {
        this.principalAmt = principalAmt;
    }

    public String getRecover() {
        return recover;
    }

    public void setRecover(String recover) {
        this.recover = recover;
    }

    public String getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(String sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getSchemes() {
        return schemes;
    }

    public void setSchemes(String schemes) {
        this.schemes = schemes;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityValue() {
        return securityValue;
    }

    public void setSecurityValue(String securityValue) {
        this.securityValue = securityValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getSubsidyExpDate() {
        return subsidyExpDate;
    }

    public void setSubsidyExpDate(String subsidyExpDate) {
        this.subsidyExpDate = subsidyExpDate;
    }
    private List<CustomerDetail> incis;
    private List<SecurityTable> security;
    private List<OtherAcTable> otherAc;
    private String flag;

    public String getNomineeAddress() {
        return nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getDob1() {
        return dob1;
    }

    public void setDob1(String dob1) {
        this.dob1 = dob1;
    }

    public String getDob2() {
        return dob2;
    }

    public void setDob2(String dob2) {
        this.dob2 = dob2;
    }

    public String getDob3() {
        return dob3;
    }

    public void setDob3(String dob3) {
        this.dob3 = dob3;
    }

    public String getDob4() {
        return dob4;
    }

    public void setDob4(String dob4) {
        this.dob4 = dob4;
    }

    public String getFatherName1() {
        return fatherName1;
    }

    public void setFatherName1(String fatherName1) {
        this.fatherName1 = fatherName1;
    }

    public String getFatherName2() {
        return fatherName2;
    }

    public void setFatherName2(String fatherName2) {
        this.fatherName2 = fatherName2;
    }

    public String getFatherName3() {
        return fatherName3;
    }

    public void setFatherName3(String fatherName3) {
        this.fatherName3 = fatherName3;
    }

    public String getFatherName4() {
        return fatherName4;
    }

    public void setFatherName4(String fatherName4) {
        this.fatherName4 = fatherName4;
    }

    public String getJtName1() {
        return jtName1;
    }

    public void setJtName1(String jtName1) {
        this.jtName1 = jtName1;
    }

    public String getJtName2() {
        return jtName2;
    }

    public void setJtName2(String jtName2) {
        this.jtName2 = jtName2;
    }

    public String getJtName3() {
        return jtName3;
    }

    public void setJtName3(String jtName3) {
        this.jtName3 = jtName3;
    }

    public String getJtName4() {
        return jtName4;
    }

    public void setJtName4(String jtName4) {
        this.jtName4 = jtName4;
    }

    public String getOccupation1() {
        return occupation1;
    }

    public void setOccupation1(String occupation1) {
        this.occupation1 = occupation1;
    }

    public String getOccupation2() {
        return occupation2;
    }

    public void setOccupation2(String occupation2) {
        this.occupation2 = occupation2;
    }

    public String getOccupation3() {
        return occupation3;
    }

    public void setOccupation3(String occupation3) {
        this.occupation3 = occupation3;
    }

    public String getOccupation4() {
        return occupation4;
    }

    public void setOccupation4(String occupation4) {
        this.occupation4 = occupation4;
    }

    public String getPanNo1() {
        return panNo1;
    }

    public void setPanNo1(String panNo1) {
        this.panNo1 = panNo1;
    }

    public String getPanNo2() {
        return panNo2;
    }

    public void setPanNo2(String panNo2) {
        this.panNo2 = panNo2;
    }

    public String getPanNo3() {
        return panNo3;
    }

    public void setPanNo3(String panNo3) {
        this.panNo3 = panNo3;
    }

    public String getPanNo4() {
        return panNo4;
    }

    public void setPanNo4(String panNo4) {
        this.panNo4 = panNo4;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getOrgnBrCode() {
        return orgnBrCode;
    }

    public void setOrgnBrCode(String orgnBrCode) {
        this.orgnBrCode = orgnBrCode;
    }

    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getOpendate() {
        return opendate;
    }

    public void setOpendate(String opendate) {
        this.opendate = opendate;
    }

    public Date getOpendate1() {
        return opendate1;
    }

    public void setOpendate1(Date opendate1) {
        this.opendate1 = opendate1;
    }

    public String getOpdate() {
        return opdate;
    }

    public void setOpdate(String opdate) {
        this.opdate = opdate;
    }

    public String getRelname() {
        return relname;
    }

    public void setRelname(String relname) {
        this.relname = relname;
    }

    public String getMailadd() {
        return mailadd;
    }

    public void setMailadd(String mailadd) {
        this.mailadd = mailadd;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getAcinstn() {
        return acinstn;
    }

    public void setAcinstn(String acinstn) {
        this.acinstn = acinstn;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getOmode() {
        return omode;
    }

    public void setOmode(String omode) {
        this.omode = omode;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getCbook() {
        return cbook;
    }

    public void setCbook(String cbook) {
        this.cbook = cbook;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public BigDecimal getBlnce() {
        return blnce;
    }

    public void setBlnce(BigDecimal blnce) {
        this.blnce = blnce;
    }

    public String getIoption() {
        return ioption;
    }

    public void setIoption(String ioption) {
        this.ioption = ioption;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    public String getRoivalue() {
        return roivalue;
    }

    public void setRoivalue(String roivalue) {
        this.roivalue = roivalue;
    }

    public String getRdMAt() {
        return rdMAt;
    }

    public void setRdMAt(String rdMAt) {
        this.rdMAt = rdMAt;
    }

    public BigDecimal getRdMAtval() {
        return rdMAtval;
    }

    public void setRdMAtval(BigDecimal rdMAtval) {
        this.rdMAtval = rdMAtval;
    }

    public String getRdMat() {
        return rdMat;
    }

    public void setRdMat(String rdMat) {
        this.rdMat = rdMat;
    }

    public String getRdMateval() {
        return rdMateval;
    }

    public void setRdMateval(String rdMateval) {
        this.rdMateval = rdMateval;
    }

    public String getRdinstall() {
        return rdinstall;
    }

    public void setRdinstall(String rdinstall) {
        this.rdinstall = rdinstall;
    }

    public String getRdinstallval() {
        return rdinstallval;
    }

    public void setRdinstallval(String rdinstallval) {
        this.rdinstallval = rdinstallval;
    }

    public String getFd() {
        return fd;
    }

    public void setFd(String fd) {
        this.fd = fd;
    }

    public String getAcNat() {
        return acNat;
    }

    public void setAcNat(String acNat) {
        this.acNat = acNat;
    }

    public String getFdvalue() {
        return fdvalue;
    }

    public void setFdvalue(String fdvalue) {
        this.fdvalue = fdvalue;
    }

    public String getTabname() {
        return tabname;
    }

    public void setTabname(String tabname) {
        this.tabname = tabname;
    }

    public String getRoivalue1() {
        return roivalue1;
    }

    public void setRoivalue1(String roivalue1) {
        this.roivalue1 = roivalue1;
    }

    public Date getRoivalue2() {
        return roivalue2;
    }

    public void setRoivalue2(Date roivalue2) {
        this.roivalue2 = roivalue2;
    }

    public List<CustomerDetail> getIncis() {
        return incis;
    }

    public void setIncis(List<CustomerDetail> incis) {
        this.incis = incis;
    }

    public List<OtherAcTable> getOtherAc() {
        return otherAc;
    }

    public void setOtherAc(List<OtherAcTable> otherAc) {
        this.otherAc = otherAc;
    }

    public List<SecurityTable> getSecurity() {
        return security;
    }

    public void setSecurity(List<SecurityTable> security) {
        this.security = security;
    }

    public String getOldAccNo() {
        return oldAccNo;
    }

    public void setOldAccNo(String oldAccNo) {
        this.oldAccNo = oldAccNo;
    }

    public String getCustAcNoLen() {
        return custAcNoLen;
    }

    public void setCustAcNoLen(String custAcNoLen) {
        this.custAcNoLen = custAcNoLen;
    }

    public HttpServletRequest getRequest() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request == null) {
            throw new RuntimeException("Sorry. Got a null request from faces context");
        }
        return request;
    }

    public CustomerDetailsReport() {
        req = getRequest();
        try {
            String orgnBrIp = WebUtil.getClientIP(req);
            InetAddress localhost = InetAddress.getByName(orgnBrIp);
            orgnBrCode = Init.IP2BR.getBranch(localhost);
            setUserName(req.getRemoteUser());
            remoteObject = (CustomerDetailsReportFacadeRemote) ServiceLocator.getInstance().lookup("CustomerDetailsReportFacade");
            ftsPostRemote = (FtsPostingMgmtFacadeRemote) ServiceLocator.getInstance().lookup(jndiHomeNameFtsPost);
            loanReportRemote = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            overDueReport = (OverDueReportFacadeRemote) ServiceLocator.getInstance().lookup("OverDueReportFacade");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
            setTodayDate(sdf.format(date));
            setDate1(ymd.format(date));
            security = new ArrayList<SecurityTable>();
            otherAc = new ArrayList<OtherAcTable>();
            reSet();
            this.setCustAcNoLen(getAcNoLength());
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }
    }

    public void tabData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
        reSetForm();
        try {
            NumberFormat formatter = new DecimalFormat("#.##");
            flag = "false";
            incis = new ArrayList<CustomerDetail>();
            setMessages("");
            if ((this.oldAccNo.equals("")) || (this.oldAccNo == null)) {
                this.setMessages("Please Enter Account Number");
                incis.clear();
                return;
            }
            //if (!this.oldAccNo.equalsIgnoreCase("") && this.oldAccNo.length() < 12) {
            if (!this.oldAccNo.equalsIgnoreCase("") && ((this.oldAccNo.length() != 12) && (this.oldAccNo.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
                this.setMessages("Please Fill Proper Account Number.");
                return;
            }
            accno = ftsPostRemote.getNewAccountNumber(oldAccNo);
            String accountCode = ftsPostRemote.getAccountCode(accno);
            String result = remoteObject.accNoPass(accno, accountCode, date1);
            if (result.equals("A/c No Does Not Exist") || result.equals("A/c Has Been Closed")) {
                reSet();
                setMessages(result);
                return;
            }
            if (result.contains("###")) {
                List chequeFacilityList = remoteObject.getChequeFacility(accno);
                if (chequeFacilityList.size() > 0) {
                    for (int t = 0; t < chequeFacilityList.size(); t++) {
                        Vector element = (Vector) chequeFacilityList.get(t);
                        int chequeFacility = Integer.parseInt(element.get(0).toString());
                        if (chequeFacility == 1) {
                            setCbook("YES");
                        } else {
                            setCbook("NO");
                        }
                    }

                } else {
                    setCbook("NO");
                }
                /**
                 * *end here**
                 */
                String[] values = null;
                String spliter = "###";
                values = result.split(spliter);

                this.setAcNat(values[2]);
                this.setTabname(values[3]);
                this.setOpdate(values[4]);

                setOpendate1(ymd.parse(opdate));
                setOpendate(sdf.format(opendate1));
                this.setOmode(values[5]);
                this.setOtype(values[6]);
                this.setCname(values[7]);

                this.setRelname(values[8]);
                this.setMailadd(values[9]);
                this.setAcinstn(values[10]);
                this.setPhno(values[11]);

                this.setPanno(values[12]);
                this.setDob(values[13]);
                this.setDetail(values[14]);
                this.setDname(values[15]);
                this.setIoption(values[16]);
                this.setBlnce(new BigDecimal(values[17]));
                if (acNat.equalsIgnoreCase(CbsConstant.RECURRING_AC)) {
                    this.setRoivalue(values[18]);
                    this.setRdMateval(sdf.format(ymd.parse(values[19])));
                    this.setRdinstallval(values[20]);
                    this.setRdMAtval(new BigDecimal(values[21]));
                    this.setFdvalue(values[22]);
                }
                if (acNat.equalsIgnoreCase(CbsConstant.FIXED_AC) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    this.setFdvalue(values[22]);
                }

                if (((acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN))
                        || (acNat.equalsIgnoreCase(CbsConstant.DEMAND_LOAN))
                        || (acNat.equalsIgnoreCase(CbsConstant.CURRENT_AC)))
                        && !(accno.substring(2, 4).equalsIgnoreCase("01"))) {
                    flag = "true";
                    BigDecimal overDueAmt = new BigDecimal("0");
                    if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        int isExceessEmi = common.isExceessEmi(date1);
                        List<OverdueEmiReportPojo> overdueEmiList = overDueReport.getOverdueEmiReport(1, 0, "", "ALL", 1, 5000, date1, accno.substring(0, 2), "", false, accno,"N");
                        //   List<OverdueEmiReportPojo> overdueEmiList = overDueReport.getOverdueEmiList("A/Cs Whose Plan Has Finished", accno, 1, 200, date1, accno.substring(0, 2), "", 0, isExceessEmi, null, 0);
                        if (!overdueEmiList.isEmpty()) {
                            overDueAmt = overdueEmiList.get(0).getAmountOverdue();
                        }
                    }
                    CustomerDetail tab = new CustomerDetail();
                    if (values[23].equalsIgnoreCase("") || values[23].equalsIgnoreCase(null)) {
                        setSanctionLimit("");
                    } else {
                        setSanctionLimit(formatter.format(Double.parseDouble(values[23])));
                    }
                    if (values[24].equalsIgnoreCase("") || values[24].equalsIgnoreCase(null)) {
                        setSanctionDate("");
                    } else {
                        setSanctionDate(values[24]);
                    }
                    if (values[25].equalsIgnoreCase("") || values[25].equalsIgnoreCase(null)) {
                        setLoanAcctRoi("");
                    } else {
                        setLoanAcctRoi(values[25]);
                    }
                    if (values[26].equalsIgnoreCase("") || values[26].equalsIgnoreCase(null)) {
                        setAmountDisbursed("");
                    } else {
                        setAmountDisbursed(formatter.format(Double.parseDouble(values[26])));
                    }
                    if (acNat.equalsIgnoreCase(CbsConstant.TERM_LOAN)) {
                        setOverDue(overDueAmt.toString());
                    } else {
                        if (values[27].equalsIgnoreCase("") || values[27].equalsIgnoreCase(null)) {
                            setOverDue("");
                        } else {
                            setOverDue(values[27]);
                        }
                    }
                    if (values[28].equalsIgnoreCase("") || values[28].equalsIgnoreCase(null)) {
                        setRecover("");
                    } else {
                        setRecover(new DecimalFormat("#.##").format(Double.parseDouble(values[28].toString())));
                    }
                    if (values[29].equalsIgnoreCase("") || values[29].equalsIgnoreCase(null)) {
                        setInterestDue("");
                    } else {
                        setInterestDue(values[29]);
                    }
                    if (values[30].equalsIgnoreCase("") || values[30].equalsIgnoreCase(null)) {
                        setCategory("");
                    } else {
                        setCategory(values[30]);
                    }
                    if (values[31].equalsIgnoreCase("") || values[31].equalsIgnoreCase(null)) {
                        setClassification("");
                    } else {
                        setClassification(values[31]);
                    }
                    if (values[32].equalsIgnoreCase("") || values[32].equalsIgnoreCase(null)) {
                        setSector("");
                    } else {
                        setSector(values[32]);
                    }
                    if (values[33].equalsIgnoreCase("") || values[33].equalsIgnoreCase(null)) {
                        setSchemes("");
                    } else {
                        setSchemes(values[33]);
                    }
                    if (values[34].equalsIgnoreCase("") || values[34].equalsIgnoreCase(null)) {
                        setPeriod("");
                    } else {
                        setPeriod(values[34]);
                    }
                    if (values[35].equalsIgnoreCase("") || values[35].equalsIgnoreCase(null)) {
                        setSecurityType("");
                    } else {
                        setSecurityType(values[35]);
                    }
                    if (values[36].equalsIgnoreCase("") || values[36].equalsIgnoreCase(null)) {
                        setSecurityValue("");
                    } else {
                        setSecurityValue(formatter.format(Double.parseDouble(values[36])));
                    }
                    if (values[37].equalsIgnoreCase("") || values[37].equalsIgnoreCase(null)) {
                        setInstallAmount("");
                    } else {
                        setInstallAmount(values[37]);
                    }
                    if (values[38].equalsIgnoreCase("") || values[38].equalsIgnoreCase(null)) {
                        setPaidEMI("");
                    } else {
                        setPaidEMI(values[38]);
                    }
                    if (values[39].equalsIgnoreCase("") || values[39].equalsIgnoreCase(null)) {
                        setOverdueEMI("");
                    } else {
                        setOverdueEMI(values[39]);
                    }
                    if (values[40].equalsIgnoreCase("") || values[40].equalsIgnoreCase(null)) {
                        setLoanPeriod("");
                    } else {
                        setLoanPeriod(values[40]);
                    }

                    if (values[44].equalsIgnoreCase("") || values[44].equalsIgnoreCase(null)) {
                        setPrincipalAmt("");
                    } else {
                        setPrincipalAmt(values[44]);
                    }
                    /**
                     * ****
                     */
                    if (values[45].equalsIgnoreCase("") || values[45].equalsIgnoreCase(null)) {
                        setStatus("");
                    } else {
                        setStatus(values[45]);
                    }
                    if (values[46].equalsIgnoreCase("") || values[46].equalsIgnoreCase(null)) {
                        setnPADate("");
                    } else {
                        setnPADate(values[46]);
                    }
                    if (values[47].equalsIgnoreCase("") || values[47].equalsIgnoreCase(null)) {
                        setnPAIntrest("");
                    } else {
                        setnPAIntrest(values[47]);
                    }
                    if (values[48].equalsIgnoreCase("") || values[48].equalsIgnoreCase(null)) {
                        setDPODLimit("");
                    } else {
                        setDPODLimit(values[48]);
                    }
                    if (values[49].equalsIgnoreCase("") || values[49].equalsIgnoreCase(null)) {
                        setPenalROI("");
                    } else {
                        setPenalROI(new DecimalFormat("#.##").format(Double.parseDouble(values[49].toString())));
                    }
                    if (values[50].equalsIgnoreCase("") || values[50].equalsIgnoreCase(null)) {
                        setaDHOCLimit("");
                    } else {
                        setaDHOCLimit(values[50]);
                    }
                    if (values[51].equalsIgnoreCase("") || values[51].equalsIgnoreCase(null)) {
                        setaDHOCROI("");
                    } else {
                        setaDHOCROI(values[51]);
                    }
                    if (values[52].equalsIgnoreCase("") || values[52].equalsIgnoreCase(null)) {
                        setaDHOCAppDate("");
                    } else {
                        setaDHOCAppDate(values[52]);
                    }
                    if (values[53].equalsIgnoreCase("") || values[53].equalsIgnoreCase(null)) {
                        setaDHOCExpiry("");
                    } else {
                        setaDHOCExpiry(values[53]);
                    }
                    if (values[54].equalsIgnoreCase("") || values[54].equalsIgnoreCase(null)) {
                        setSubsidy("");
                    } else {
                        setSubsidy(values[54]);
                    }
                    if (values[55].equalsIgnoreCase("") || values[55].equalsIgnoreCase(null)) {
                        setSubsidyExpDate("");
                    } else {
                        setSubsidyExpDate(values[55]);
                    }

                    incis.add(tab);

                    this.setIntOption(values[56]);
                    if (values[56].equals("null")) {
                        setFdvalue("");
                    } else {
                        setFdvalue(values[57]);
                    }
                    loanMisDetail();
                    customerDetail();
                }
                if ((acNat.equals(CbsConstant.FIXED_AC)) || acNat.equalsIgnoreCase(CbsConstant.MS_AC)) {
                    setRoi("");
                    setRdinstall("");
                    setRdMat("");
                    setRdMAt("");
                    setFd("No.FD's");
                    setFdvalue("");
                } else if ((acNat.equals(CbsConstant.RECURRING_AC))) {
                    setRoi("ROI");
                    setRdinstall("RD Install Amt");
                    setRdMat("RD Mat Date");
                    setRdMAt("RD MAT Value");
                    setFd("RD Period");
                    //setFdvalue("");
                } else if ((acNat.equals(CbsConstant.CURRENT_AC))
                        && (accno.substring(2, 3).toString().equalsIgnoreCase("02"))) {
                    setRoi("NextRene.DT");
                    setRdinstall("");
                    setRdMat("");
                    setRdMAt("");
                    setFd("");
                    setFdvalue("");
                    this.setRoivalue1(values[1]);
                    setRoivalue2(ymd.parse(roivalue1));
                    this.setRoivalue(sdf.format(roivalue2));
                } else {
                    setRdMateval("");
                    setRoi("ROI");
                    setRdinstall("");
                    setRdMat("");
                    setRdMAt("");
                    setFd("");
                    setFdvalue("");
                }
            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }
    }

    public void secDetail() {
        security = new ArrayList<SecurityTable>();
        if ((this.accno.equals("")) || (this.accno == null)) {
            this.setMessages("Please Enter Account Number");
            incis.clear();
            return;
        }
        //if (!this.accno.equalsIgnoreCase("") && this.accno.length() < 12) {
        if (!this.accno.equalsIgnoreCase("") && ((this.accno.length() != 12) && (this.accno.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
            this.setMessages("Please Fill Proper Account Number.");
            return;
        }
        try {
            List resultList;
            resultList = remoteObject.securityDetails(accno);
            if (resultList.size() <= 0) {
                return;
            } else {
                for (int i = 0; i < resultList.size(); i++) {
                    Vector tableVector = (Vector) resultList.get(i);
                    SecurityTable mt = new SecurityTable();
                    Integer sno = i + 1;
                    mt.setSno(sno.toString());
                    mt.setSecurityType(tableVector.get(0).toString());
                    mt.setMatValue(tableVector.get(1).toString());
                    mt.setMatDate(tableVector.get(2).toString());
                    mt.setLienValue(tableVector.get(3).toString());
                    mt.setStatus(tableVector.get(4).toString());
                    mt.setIssueDate(tableVector.get(5).toString());
                    security.add(mt);

                }
            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }

    }

    public void otherAccDetail() {
        otherAc = new ArrayList<OtherAcTable>();
        if ((this.accno.equals("")) || (this.accno == null)) {
            this.setMessages("Please Enter Account Number");
            incis.clear();
            return;
        }
        //if (!this.accno.equalsIgnoreCase("") && this.accno.length() < 12) {
        if (!this.accno.equalsIgnoreCase("") && ((this.accno.length() != 12) && (this.accno.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
            this.setMessages("Please Fill Proper Account Number.");
            return;
        }
        try {
            List resultList;
            resultList = remoteObject.otherAcnoDetails(accno);
            if (resultList.size() <= 0) {
                return;
            } else {
                for (int i = 0, j = 1, t = 0; i < resultList.size(); i = i + 2, j = j + 2, t = t + 1) {
                    OtherAcTable mt = new OtherAcTable();
                    Integer sno = t + 1;
                    mt.setSno(sno.toString());
                    mt.setName(resultList.get(i).toString());
                    mt.setAcno(resultList.get(j).toString());
                    otherAc.add(mt);

                }
            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }

    }

    public void jtDetails() {
        resetjtHHolderDetails();
        if ((this.accno.equals("")) || (this.accno == null)) {
            this.setMessages("Please Enter Account Number");
            incis.clear();
            return;
        }
        //if (!this.accno.equalsIgnoreCase("") && this.accno.length() < 12) {
        if (!this.accno.equalsIgnoreCase("") && ((this.accno.length() != 12) && (this.accno.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
            this.setMessages("Please Fill Proper Account Number.");
            return;
        }
        try {
            List resultList;
            resultList = remoteObject.jtDetails(accno, orgnBrCode);
            if (resultList.size() <= 0) {
                return;
            } else {

                if (resultList.get(0).toString().equalsIgnoreCase("")) {
                    setJtMsg("Sorry Nominee Details Are Not Present");
                } else {
                    setNomineeName(resultList.get(0).toString());
                    setNomineeAddress(resultList.get(1).toString());
                    setNomineeRelation(resultList.get(2).toString());
                }
                if (resultList.size() == 3 || resultList.get(3).toString().equalsIgnoreCase("")) {
                    setJtMsg("Sorry Joint Holder Details Are Not Present");
                    return;
                }
                if (resultList.size() > 3) {
                    setJtName1(resultList.get(3).toString());
                    setDob1(resultList.get(4).toString());
                    setAddress1(resultList.get(5).toString());
                    setFatherName1(resultList.get(6).toString());
                    setPanNo1(resultList.get(7).toString());
                    setOccupation1(resultList.get(8).toString());
                }
                if (resultList.size() > 8) {
                    setJtName2(resultList.get(9).toString());
                    setDob2(resultList.get(10).toString());
                    setAddress2(resultList.get(11).toString());
                    setFatherName2(resultList.get(12).toString());
                    setPanNo2(resultList.get(13).toString());
                    setOccupation2(resultList.get(14).toString());
                }
                if (resultList.size() > 14) {
                    setJtName3(resultList.get(15).toString());
                    setDob3(resultList.get(16).toString());
                    setAddress3(resultList.get(17).toString());
                    setFatherName3(resultList.get(1).toString());
                    setPanNo3(resultList.get(19).toString());
                    setOccupation3(resultList.get(20).toString());
                }
                if (resultList.size() > 20) {
                    setJtName4(resultList.get(21).toString());
                    setDob4(resultList.get(22).toString());
                    setAddress4(resultList.get(23).toString());
                    setFatherName4(resultList.get(24).toString());
                    setPanNo4(resultList.get(25).toString());
                    setOccupation4(resultList.get(26).toString());
                }

            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }

    }

    public void loanMisDetail() {
        setMisMsg("");
        resetMisDetails();
        if ((this.accno.equals("")) || (this.accno == null)) {
            this.setMessages("Please Enter Account Number");
            incis.clear();
            return;
        }
        //if (!this.accno.equalsIgnoreCase("") && this.accno.length() < 12) {
        if (!this.accno.equalsIgnoreCase("") && ((this.accno.length() != 12) && (this.accno.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
            this.setMessages("Please Fill Proper Account Number.");
            return;
        }

        try {
            List resultList;
            resultList = remoteObject.misDetails(accno);
            if (resultList.size() <= 0) {
                resetMisDetails();
                setMisMsg("Mis Details is Not Present");
                return;
            } else {
                setSectorMis(resultList.get(0).toString());
                setSubSector(resultList.get(1).toString());
                setPurposeAdvance(resultList.get(2).toString());
                setModeAdvance(resultList.get(3).toString());

                setTypeAdvance(resultList.get(4).toString());
                setSecured(resultList.get(5).toString());
                setGuaranteeCover(resultList.get(6).toString());
                setIndustryType(resultList.get(7).toString());
                setSickness(resultList.get(8).toString());

                setExposure(resultList.get(9).toString());
                setRestructuring(resultList.get(10).toString());
                setSanctionLevel(resultList.get(11).toString());
                setSanAuth(resultList.get(12).toString());
                setIntTable(resultList.get(13).toString());

                setInterestType(resultList.get(14).toString());
                setSchemeCode(resultList.get(15).toString());
                setNpaCal(resultList.get(16).toString());
                setCourt(resultList.get(17).toString());
                setModeOfSet(resultList.get(18).toString());

                setDebtWaiverReason(resultList.get(19).toString());
                setAssetClassReason(resultList.get(20).toString());
                setPopulationGroup(resultList.get(21).toString());

            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }

    }
    String custMsg;

    public String getCustMsg() {
        return custMsg;
    }

    public void setCustMsg(String custMsg) {
        this.custMsg = custMsg;
    }

    public void customerDetail() {
        setCustMsg("");
        customerDetails();
        if ((this.accno.equals("")) || (this.accno == null)) {
            this.setMessages("Please Enter Account Number");
            incis.clear();
            return;
        }
        //if (!this.accno.equalsIgnoreCase("") && this.accno.length() < 12) {
        if (!this.accno.equalsIgnoreCase("") && ((this.accno.length() != 12) && (this.accno.length() != (Integer.parseInt(this.getCustAcNoLen()))))) {
            this.setMessages("Please Fill Proper Account Number.");
            return;
        }
        try {
            List resultList;
            resultList = remoteObject.getCustDetails(accno);
//            if (resultList.get(0).toString().equalsIgnoreCase("") || resultList.get(1).toString().equalsIgnoreCase("")
//                    || resultList.get(2).toString().equalsIgnoreCase("") || resultList.get(3).toString().equalsIgnoreCase("")
//                    || resultList.get(4).toString().equalsIgnoreCase("") || resultList.get(5).toString().equalsIgnoreCase("")
//                    || resultList.get(6).toString().equalsIgnoreCase("") || resultList.get(7).toString().equalsIgnoreCase("")
//                    || resultList.get(8).toString().equalsIgnoreCase("") || resultList.get(8).toString().equalsIgnoreCase("")
//                    || resultList.get(9).toString().equalsIgnoreCase("") || resultList.get(10).toString().equalsIgnoreCase("")
//                    || resultList.get(11).toString().equalsIgnoreCase("") || resultList.get(12).toString().equalsIgnoreCase("")) {
            if (resultList.isEmpty()) {
                customerDetails();
                setCustMsg("Customer Detail Is not present.");
                return;
            } else {
                setOccup(resultList.get(0).toString());
                setConstitution(resultList.get(1).toString());
                setCasteCode(resultList.get(2).toString());
                setCommunityCode(resultList.get(3).toString());
                setBusinessSeg(resultList.get(4).toString());

                setSalesTurnover(resultList.get(5).toString());
                setCustStatus(resultList.get(6).toString());
                setStateCode(resultList.get(7).toString());
                setVillage(resultList.get(8).toString());
                setExternalRatingLongTerm(resultList.get(9).toString());

                setExternalRatingShortTerm(resultList.get(10).toString());
                setTehsil(resultList.get(11).toString());
                setBlock(resultList.get(12).toString());
                setOperRiskRating(resultList.get(13).toString());
                setCreditRiskRatingInternal(resultList.get(14).toString());
            }
        } catch (ApplicationException e) {
            setMessages(e.getLocalizedMessage());
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }
    }

    public void resetMisDetails() {
        setSectorMis("");
        setSubSector("");
        setPurposeAdvance("");
        setModeAdvance("");
        setTypeAdvance("");

        setSector("");
        setGuaranteeCover("");
        setIndustryType("");
        setSickness("");
        setExposure("");

        setRestructuring("");
        setSanctionLevel("");
        setSanAuth("");
        setIntTable("");
        setInterestType("");
        setSchemeCode("");

        setNpaCal("");
        setCourt("");
        setModeOfSet("");
        setDebtWaiverReason("");
        setAssetClassReason("");
        setPopulationGroup("");
    }

    public void customerDetails() {
        setOccup("");
        setConstitution("");
        setCasteCode("");
        setCommunityCode("");
        setBusinessSeg("");
        setSalesTurnover("");
        setCustStatus("");
        setStateCode("");
        setVillage("");
        setExternalRatingLongTerm("");
        setExternalRatingShortTerm("");
        setTehsil("");
        setBlock("");
        setOperRiskRating("");
        setCreditRiskRatingInternal("");
    }

    public void resetjtHHolderDetails() {
        setJtName1("");
        setDob1("");
        setAddress1("");
        setFatherName1("");
        setPanNo1("");
        setOccupation1("");
        setJtName2("");
        setDob2("");
        setAddress2("");
        setFatherName2("");
        setPanNo2("");
        setOccupation2("");
        setJtName3("");
        setDob3("");
        setAddress3("");
        setFatherName3("");
        setPanNo3("");
        setOccupation3("");
        setJtName4("");
        setDob4("");
        setAddress4("");
        setFatherName4("");
        setPanNo4("");
        setOccupation4("");
        setNomineeName("");
        setNomineeAddress("");
        setNomineeRelation("");
    }

    public void resetLoanDetails() {
        setSanctionLimit("");
        setSanctionDate("");
        setLoanAcctRoi("");
        setAmountDisbursed("");
        setOverDue("");
        setRecover("");
        setInterestDue("");
        setCategory("");
        setClassification("");
        setSector("");
        setSchemes("");
        setPeriod("");
        setSecurityType("");
        setSecurityValue("");
        setInstallAmount("");
        setPaidEMI("");
        setOverdueEMI("");
        setLoanPeriod("");
        setPrincipalAmt("");
        setStatus("");
        setnPADate("");
        setnPAIntrest("");
        setDPODLimit("");
        setPenalROI("");
        setaDHOCLimit("");
        setaDHOCROI("");
        setaDHOCAppDate("");
        setaDHOCExpiry("");
        setSubsidy("");
        setSubsidyExpDate("");
        setFdvalue("");
    }

    public void reSet() {
        incis = new ArrayList<CustomerDetail>();
        flag = "false";
        this.setAccno("");
        this.setMessages("");

        this.setAcNat("");
        this.setTabname("");
        this.setOpendate("");

        this.setOmode("");
        this.setOtype("");
        this.setCname("");

        this.setRelname("");
        this.setMailadd("");
        this.setAcinstn("");

        this.setPhno("");
        this.setPanno("");
        this.setDetail("");

        this.setDname("");
        this.setIoption("");
        this.setBlnce(new BigDecimal("0"));

        this.setRoivalue("");
        this.setRdMateval("");
        this.setRdinstallval("");

        this.setRdMAtval(new BigDecimal("0"));
        this.setFdvalue("");

        setRoi("ROI");
        setRdinstall("RD Install Amt");
        setRdMat("RD Mat. Date");
        setRdMAt("RD MAT. Value");
        setFd("No.FD's");

        this.setCbook("");
        incis.clear();
        otherAc = new ArrayList<OtherAcTable>();

        resetjtHHolderDetails();
        resetLoanDetails();
        customerDetails();
        resetMisDetails();
        this.setDob("");
        this.setOldAccNo("");
    }

    public void reSetForm() {
        incis = new ArrayList<CustomerDetail>();
        flag = "false";
        this.setMessages("");
        this.setAcNat("");
        this.setTabname("");
        this.setOpendate("");
        this.setOmode("");
        this.setOtype("");
        this.setCname("");
        this.setRelname("");
        this.setMailadd("");
        this.setAcinstn("");
        this.setPhno("");
        this.setPanno("");
        this.setDetail("");
        this.setDname("");
        this.setIoption("");
        this.setBlnce(new BigDecimal("0"));
        this.setRoivalue("");
        this.setRdMateval("");
        this.setRdinstallval("");
        this.setRdMAtval(new BigDecimal("0"));
        this.setFdvalue("");
        setRoi("ROI");
        setRdinstall("RD Install Amt");
        setRdMat("RD Mat. Date");
        setRdMAt("RD MAT. Value");
        setFd("No.FD's");
        this.setCbook("");
        incis.clear();
        otherAc = new ArrayList<OtherAcTable>();
        resetjtHHolderDetails();
        resetLoanDetails();
        customerDetails();
        resetMisDetails();
    }

    public String exitFrm() {
        reSet();
        return "case1";
    }

    public String clearForm() {
        try {
            reSet();
        } catch (Exception e) {
            setMessages(e.getLocalizedMessage());
        }
        return "custInfo";
    }
}
