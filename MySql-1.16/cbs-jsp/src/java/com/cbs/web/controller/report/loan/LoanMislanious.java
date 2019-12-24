/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cbs.web.controller.report.loan;

import com.cbs.dto.report.LoanMisCellaniousPojo;
import com.cbs.dto.report.SortedByAcNo;
import com.cbs.dto.report.ho.SortedByAcNoMis;
import com.cbs.dto.report.ho.SortedBySector;
import com.cbs.exception.ApplicationException;
import com.cbs.facade.admin.AccountOpeningFacadeRemote;
import com.cbs.facade.loan.AdvancesInformationTrackingRemote;
import com.cbs.facade.report.CommonReportMethodsRemote;
import com.cbs.facade.report.LoanReportFacadeRemote;
import com.cbs.utils.ServiceLocator;
import com.cbs.web.controller.BaseBean;
import com.cbs.web.controller.ReportBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.collections.comparators.ComparatorChain;

/**
 *
 * @author admin
 */
public class LoanMislanious extends BaseBean {

    private String branchCode;
    private List<SelectItem> branchCodeList;
    private String errorMsg;
    SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
    List<SelectItem> acNatureList;
    List<SelectItem> accTypeList;
    List<SelectItem> sectorList;
    List<SelectItem> subSectorList;
    String sector;
    List<SelectItem> sectorOption;    
    String subSector;
    List<SelectItem> subSectorOption;
    String purposeOfAdvance;
    List<SelectItem> purposeOfAdvanceOption;
    String gender;
    List<SelectItem> genderOption;
    private String reportFormat;
    private List<SelectItem> reportFormatIn;
    String modeOfAdvance;
    List<SelectItem> modeOfAdvanceOption;
    String typeOfAdvance;
    List<SelectItem> typeOfAdvanceOption;
    String secured;
    List<SelectItem> securedOption;
    String guarnteeCover;
    List<SelectItem> guarnteeCoverOption;
    String industryType;
    List<SelectItem> industryTypeOption;
    String purOfAdv;
    List<SelectItem> purOfAdvOption;
    String exposure;
    List<SelectItem> exposureOption;
    String exposureCategory;
    List<SelectItem> exposureCategroyOption;
    String exposureCategoryPurpose;
    List<SelectItem> exposureCatePurposeOption;
    String schemeCode;
    String intTable;
    String intType;
    List<SelectItem> schemeCodeOption;
    List<SelectItem> intTableOption;
    List<SelectItem> intTypeOption;
    String applicantCategory;
    String categoryOpt;
    String minorCommunity;
    List<SelectItem> applicantCategoryOption;
    List<SelectItem> categoryOptOption;
    List<SelectItem> minorCommunityOption;
    List<SelectItem> relationOption;
    String relOwner;
    List<SelectItem> relOwnerOption;
    String popuGroup;
    List<SelectItem> popuGroupOption;
    String sanctionLevel;
    List<SelectItem> sanctionLevelOption;
    String sanctionAuth;
    List<SelectItem> sanctionAuthOption;
    String courts;
    List<SelectItem> courtsOption;
    String restructuring;
    List<SelectItem> restructuringOption;
    private String reportBase;
    private List<SelectItem> reportBaseList;
    private String reportBasedOn;
    private List<SelectItem> reportBasedOnList;
    private double from;
    private double to;
    
    List<SelectItem> securityList;
    List<SelectItem> appCategoryList;
    List<SelectItem> sectionList;
    List<SelectItem> categoryOptionList;
    List<SelectItem> minorityList;
    List<SelectItem> schemeCodeList;
    List<SelectItem> relationList;
    String drawingPwrInd;
    List<SelectItem> drawingPwrIndOption;
    private String loanPeriod;
    List<SelectItem> loanPeriodList;
    Map schemeMap = new HashMap();
    
    private LoanReportFacadeRemote local;
    private CommonReportMethodsRemote common;
    private AdvancesInformationTrackingRemote aitr;
    private List tempList;
    private Vector tempVector;
    private String acNature;
    private String accType;    
    private String security;
    private String appCatgory;
    private String section;
    private String category;
    private String minority;
    private String relation;    
    private Date frmDt;
    private String message;
    public String disableFromDtPanel;
    private Date toDt;
    public String industry;
    List<SelectItem> industryOption;
    public String overdue;
    List<SelectItem> overdueOption;
    public String npaClass;
    List<SelectItem> npaClassOption;
    private boolean checkSecurity= false;
    private String securityNature;
    private String securityType;
    private String securityDesc1;
    private String securityDesc2;
    private List<SelectItem> typeOfSecurityList;
    private List<SelectItem> securityNatureList;
    private List<SelectItem> securityDesc1List;
    private List<SelectItem> securityDesc2List;
    AccountOpeningFacadeRemote openingFacadeRemote;
    private String displaySectabs ="none";

    public String getDisplaySectabs() {
        return displaySectabs;
    }

    public void setDisplaySectabs(String displaySectabs) {
        this.displaySectabs = displaySectabs;
    }
    
    public boolean isCheckSecurity() {
        return checkSecurity;
    }

    public void setCheckSecurity(boolean checkSecurity) {
        this.checkSecurity = checkSecurity;
    }

    public String getSecurityNature() {
        return securityNature;
    }

    public void setSecurityNature(String securityNature) {
        this.securityNature = securityNature;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityDesc1() {
        return securityDesc1;
    }

    public void setSecurityDesc1(String securityDesc1) {
        this.securityDesc1 = securityDesc1;
    }

    public String getSecurityDesc2() {
        return securityDesc2;
    }

    public void setSecurityDesc2(String securityDesc2) {
        this.securityDesc2 = securityDesc2;
    }

    public List<SelectItem> getTypeOfSecurityList() {
        return typeOfSecurityList;
    }

    public void setTypeOfSecurityList(List<SelectItem> typeOfSecurityList) {
        this.typeOfSecurityList = typeOfSecurityList;
    }

    public List<SelectItem> getSecurityNatureList() {
        return securityNatureList;
    }

    public void setSecurityNatureList(List<SelectItem> securityNatureList) {
        this.securityNatureList = securityNatureList;
    }

    public List<SelectItem> getSecurityDesc1List() {
        return securityDesc1List;
    }

    public void setSecurityDesc1List(List<SelectItem> securityDesc1List) {
        this.securityDesc1List = securityDesc1List;
    }

    public List<SelectItem> getSecurityDesc2List() {
        return securityDesc2List;
    }

    public void setSecurityDesc2List(List<SelectItem> securityDesc2List) {
        this.securityDesc2List = securityDesc2List;
    }
    
    public String getOverdue() {
        return overdue;
    }
    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }
    public List<SelectItem> getOverdueOption() {
        return overdueOption;
    }
    public void setOverdueOption(List<SelectItem> overdueOption) {
        this.overdueOption = overdueOption;
    }
    public String getReportBase() {
        return reportBase;
    }

    public void setReportBase(String reportBase) {
        this.reportBase = reportBase;
    }

    public String getReportBasedOn() {
        return reportBasedOn;
    }

    public void setReportBasedOn(String reportBasedOn) {
        this.reportBasedOn = reportBasedOn;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public List<SelectItem> getReportBasedOnList() {
        return reportBasedOnList;
    }

    public void setReportBasedOnList(List<SelectItem> reportBasedOnList) {
        this.reportBasedOnList = reportBasedOnList;
    }
    
    public List<SelectItem> getReportBaseList() {
        return reportBaseList;
    }

    public void setReportBaseList(List<SelectItem> reportBaseList) {
        this.reportBaseList = reportBaseList;
    }
    
    public List<SelectItem> getSectorOption() {
        return sectorOption;
    }

    public void setSectorOption(List<SelectItem> sectorOption) {
        this.sectorOption = sectorOption;
    }

    public List<SelectItem> getSubSectorOption() {
        return subSectorOption;
    }

    public void setSubSectorOption(List<SelectItem> subSectorOption) {
        this.subSectorOption = subSectorOption;
    }

    public String getGuarnteeCover() {
        return guarnteeCover;
    }

    public void setGuarnteeCover(String guarnteeCover) {
        this.guarnteeCover = guarnteeCover;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getPurOfAdv() {
        return purOfAdv;
    }

    public void setPurOfAdv(String purOfAdv) {
        this.purOfAdv = purOfAdv;
    }

    public List<SelectItem> getPurOfAdvOption() {
        return purOfAdvOption;
    }

    public void setPurOfAdvOption(List<SelectItem> purOfAdvOption) {
        this.purOfAdvOption = purOfAdvOption;
    }    

    public String getExposure() {
        return exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public String getExposureCategory() {
        return exposureCategory;
    }

    public void setExposureCategory(String exposureCategory) {
        this.exposureCategory = exposureCategory;
    }

    public String getExposureCategoryPurpose() {
        return exposureCategoryPurpose;
    }

    public void setExposureCategoryPurpose(String exposureCategoryPurpose) {
        this.exposureCategoryPurpose = exposureCategoryPurpose;
    }  
    
    public String getIntTable() {
        return intTable;
    }

    public void setIntTable(String intTable) {
        this.intTable = intTable;
    }

    public String getIntType() {
        return intType;
    }

    public void setIntType(String intType) {
        this.intType = intType;
    }

    public List<SelectItem> getSchemeCodeOption() {
        return schemeCodeOption;
    }

    public void setSchemeCodeOption(List<SelectItem> schemeCodeOption) {
        this.schemeCodeOption = schemeCodeOption;
    }

    public List<SelectItem> getIntTableOption() {
        return intTableOption;
    }

    public void setIntTableOption(List<SelectItem> intTableOption) {
        this.intTableOption = intTableOption;
    }

    public List<SelectItem> getIntTypeOption() {
        return intTypeOption;
    }

    public void setIntTypeOption(List<SelectItem> intTypeOption) {
        this.intTypeOption = intTypeOption;
    }

    public String getApplicantCategory() {
        return applicantCategory;
    }

    public void setApplicantCategory(String applicantCategory) {
        this.applicantCategory = applicantCategory;
    }

    public String getCategoryOpt() {
        return categoryOpt;
    }

    public void setCategoryOpt(String categoryOpt) {
        this.categoryOpt = categoryOpt;
    }

    public String getMinorCommunity() {
        return minorCommunity;
    }

    public void setMinorCommunity(String minorCommunity) {
        this.minorCommunity = minorCommunity;
    }

    public List<SelectItem> getApplicantCategoryOption() {
        return applicantCategoryOption;
    }

    public void setApplicantCategoryOption(List<SelectItem> applicantCategoryOption) {
        this.applicantCategoryOption = applicantCategoryOption;
    }

    public List<SelectItem> getCategoryOptOption() {
        return categoryOptOption;
    }

    public void setCategoryOptOption(List<SelectItem> categoryOptOption) {
        this.categoryOptOption = categoryOptOption;
    }

    public List<SelectItem> getMinorCommunityOption() {
        return minorCommunityOption;
    }

    public void setMinorCommunityOption(List<SelectItem> minorCommunityOption) {
        this.minorCommunityOption = minorCommunityOption;
    }    
    
    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getTypeOfAdvance() {
        return typeOfAdvance;
    }

    public void setTypeOfAdvance(String typeOfAdvance) {
        this.typeOfAdvance = typeOfAdvance;
    }

    public List<SelectItem> getRelationOption() {
        return relationOption;
    }

    public void setRelationOption(List<SelectItem> relationOption) {
        this.relationOption = relationOption;
    }
    
    public String getSecured() {
        return secured;
    }

    public void setSecured(String secured) {
        this.secured = secured;
    }
    
    public AdvancesInformationTrackingRemote getTestEJB() {
        return aitr;
    }

    public void setTestEJB(AdvancesInformationTrackingRemote aitr) {
        this.aitr = aitr;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public String getModeOfAdvance() {
        return modeOfAdvance;
    }

    public void setModeOfAdvance(String modeOfAdvance) {
        this.modeOfAdvance = modeOfAdvance;
    }
    
    public List<SelectItem> getPurposeOfAdvanceOption() {
        return purposeOfAdvanceOption;
    }

    public void setPurposeOfAdvanceOption(List<SelectItem> purposeOfAdvanceOption) {
        this.purposeOfAdvanceOption = purposeOfAdvanceOption;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<SelectItem> getGenderOption() {
        return genderOption;
    }

    public void setGenderOption(List<SelectItem> genderOption) {
        this.genderOption = genderOption;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public List<SelectItem> getReportFormatIn() {
        return reportFormatIn;
    }

    public void setReportFormatIn(List<SelectItem> reportFormatIn) {
        this.reportFormatIn = reportFormatIn;
    }

    public List<SelectItem> getModeOfAdvanceOption() {
        return modeOfAdvanceOption;
    }

    public void setModeOfAdvanceOption(List<SelectItem> modeOfAdvanceOption) {
        this.modeOfAdvanceOption = modeOfAdvanceOption;
    }

    public List<SelectItem> getTypeOfAdvanceOption() {
        return typeOfAdvanceOption;
    }

    public void setTypeOfAdvanceOption(List<SelectItem> typeOfAdvanceOption) {
        this.typeOfAdvanceOption = typeOfAdvanceOption;
    }

    public List<SelectItem> getSecuredOption() {
        return securedOption;
    }

    public void setSecuredOption(List<SelectItem> securedOption) {
        this.securedOption = securedOption;
    }

    public List<SelectItem> getGuarnteeCoverOption() {
        return guarnteeCoverOption;
    }

    public void setGuarnteeCoverOption(List<SelectItem> guarnteeCoverOption) {
        this.guarnteeCoverOption = guarnteeCoverOption;
    }

    public List<SelectItem> getIndustryTypeOption() {
        return industryTypeOption;
    }

    public void setIndustryTypeOption(List<SelectItem> industryTypeOption) {
        this.industryTypeOption = industryTypeOption;
    }

    public List<SelectItem> getExposureOption() {
        return exposureOption;
    }

    public void setExposureOption(List<SelectItem> exposureOption) {
        this.exposureOption = exposureOption;
    }

    public List<SelectItem> getExposureCategroyOption() {
        return exposureCategroyOption;
    }

    public void setExposureCategroyOption(List<SelectItem> exposureCategroyOption) {
        this.exposureCategroyOption = exposureCategroyOption;
    }

    public List<SelectItem> getExposureCatePurposeOption() {
        return exposureCatePurposeOption;
    }

    public void setExposureCatePurposeOption(List<SelectItem> exposureCatePurposeOption) {
        this.exposureCatePurposeOption = exposureCatePurposeOption;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List<SelectItem> getBranchCodeList() {
        return branchCodeList;
    }

    public void setBranchCodeList(List<SelectItem> branchCodeList) {
        this.branchCodeList = branchCodeList;
    }

    public Date getFrmDt() {
        return frmDt;
    }

    public void setFrmDt(Date frmDt) {
        this.frmDt = frmDt;
    }

    public List<SelectItem> getAcNatureList() {
        return acNatureList;
    }

    public void setAcNatureList(List<SelectItem> acNatureList) {
        this.acNatureList = acNatureList;
    }

    public String getAcNature() {
        return acNature;
    }

    public void setAcNature(String acNature) {
        this.acNature = acNature;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public List<SelectItem> getAccTypeList() {
        return accTypeList;
    }

    public void setAccTypeList(List<SelectItem> accTypeList) {
        this.accTypeList = accTypeList;
    }

    public List<SelectItem> getAppCategoryList() {
        return appCategoryList;
    }

    public void setAppCategoryList(List<SelectItem> appCategoryList) {
        this.appCategoryList = appCategoryList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<SelectItem> getCategoryOptionList() {
        return categoryOptionList;
    }

    public void setCategoryOptionList(List<SelectItem> categoryOptionList) {
        this.categoryOptionList = categoryOptionList;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMinority() {
        return minority;
    }

    public void setMinority(String minority) {
        this.minority = minority;
    }

    public List<SelectItem> getMinorityList() {
        return minorityList;
    }

    public void setMinorityList(List<SelectItem> minorityList) {
        this.minorityList = minorityList;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public List<SelectItem> getRelationList() {
        return relationList;
    }

    public void setRelationList(List<SelectItem> relationList) {
        this.relationList = relationList;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public List<SelectItem> getSchemeCodeList() {
        return schemeCodeList;
    }

    public void setSchemeCodeList(List<SelectItem> schemeCodeList) {
        this.schemeCodeList = schemeCodeList;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public List<SelectItem> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SelectItem> sectionList) {
        this.sectionList = sectionList;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<SelectItem> getSectorList() {
        return sectorList;
    }

    public void setSectorList(List<SelectItem> sectorList) {
        this.sectorList = sectorList;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public List<SelectItem> getSecurityList() {
        return securityList;
    }

    public void setSecurityList(List<SelectItem> securityList) {
        this.securityList = securityList;
    }

    public String getSubSector() {
        return subSector;
    }

    public void setSubSector(String subSector) {
        this.subSector = subSector;
    }

    public List<SelectItem> getSubSectorList() {
        return subSectorList;
    }

    public void setSubSectorList(List<SelectItem> subSectorList) {
        this.subSectorList = subSectorList;
    }

    public String getAppCatgory() {
        return appCatgory;
    }

    public void setAppCatgory(String appCatgory) {
        this.appCatgory = appCatgory;
    }

    public String getRelOwner() {
        return relOwner;
    }

    public void setRelOwner(String relOwner) {
        this.relOwner = relOwner;
    }
    
    public List<SelectItem> getRelOwnerOption() {
        return relOwnerOption;
    }

    public void setRelOwnerOption(List<SelectItem> relOwnerOption) {
        this.relOwnerOption = relOwnerOption;
    }

    public String getDrawingPwrInd() {
        return drawingPwrInd;
    }

    public void setDrawingPwrInd(String drawingPwrInd) {
        this.drawingPwrInd = drawingPwrInd;
    }

    public List<SelectItem> getDrawingPwrIndOption() {
        return drawingPwrIndOption;
    }

    public void setDrawingPwrIndOption(List<SelectItem> drawingPwrIndOption) {
        this.drawingPwrIndOption = drawingPwrIndOption;
    }

    public String getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(String loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public List<SelectItem> getLoanPeriodList() {
        return loanPeriodList;
    }

    public void setLoanPeriodList(List<SelectItem> loanPeriodList) {
        this.loanPeriodList = loanPeriodList;
    }

    
    public String getPopuGroup() {
        return popuGroup;
    }

    public void setPopuGroup(String popuGroup) {
        this.popuGroup = popuGroup;
    }

    public List<SelectItem> getPopuGroupOption() {
        return popuGroupOption;
    }

    public void setPopuGroupOption(List<SelectItem> popuGroupOption) {
        this.popuGroupOption = popuGroupOption;
    }

    public String getSanctionLevel() {
        return sanctionLevel;
    }

    public void setSanctionLevel(String sanctionLevel) {
        this.sanctionLevel = sanctionLevel;
    }

    public List<SelectItem> getSanctionLevelOption() {
        return sanctionLevelOption;
    }

    public void setSanctionLevelOption(List<SelectItem> sanctionLevelOption) {
        this.sanctionLevelOption = sanctionLevelOption;
    }

    public String getSanctionAuth() {
        return sanctionAuth;
    }

    public void setSanctionAuth(String sanctionAuth) {
        this.sanctionAuth = sanctionAuth;
    }

    public List<SelectItem> getSanctionAuthOption() {
        return sanctionAuthOption;
    }

    public void setSanctionAuthOption(List<SelectItem> sanctionAuthOption) {
        this.sanctionAuthOption = sanctionAuthOption;
    }

    public String getCourts() {
        return courts;
    }

    public void setCourts(String courts) {
        this.courts = courts;
    }

    public List<SelectItem> getCourtsOption() {
        return courtsOption;
    }

    public void setCourtsOption(List<SelectItem> courtsOption) {
        this.courtsOption = courtsOption;
    }

    public String getRestructuring() {
        return restructuring;
    }

    public void setRestructuring(String restructuring) {
        this.restructuring = restructuring;
    }

    public List<SelectItem> getRestructuringOption() {
        return restructuringOption;
    }

    public void setRestructuringOption(List<SelectItem> restructuringOption) {
        this.restructuringOption = restructuringOption;
    }

    public String getDisableFromDtPanel() {
        return disableFromDtPanel;
    }
    public void setDisableFromDtPanel(String disableFromDtPanel) {
        this.disableFromDtPanel = disableFromDtPanel;
    }
    public Date getToDt() {
        return toDt;
    }
    public void setToDt(Date toDt) {
        this.toDt = toDt;
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public List<SelectItem> getIndustryOption() {
        return industryOption;
    }
    public void setIndustryOption(List<SelectItem> industryOption) {
        this.industryOption = industryOption;
    }
    public String getNpaClass() {
        return npaClass;
    }
    public void setNpaClass(String npaClass) {
        this.npaClass = npaClass;
    }
    public List<SelectItem> getNpaClassOption() {
        return npaClassOption;
    }
    public void setNpaClassOption(List<SelectItem> npaClassOption) {
        this.npaClassOption = npaClassOption;
    }
    
    /** Creates a new instance of LoanMislanious */
    public LoanMislanious() {
        try {
            acNatureList = new ArrayList<SelectItem>();
            accTypeList = new ArrayList<SelectItem>();
            sectorList = new ArrayList<SelectItem>();
            subSectorList = new ArrayList<SelectItem>();
            securityList = new ArrayList<SelectItem>();
            appCategoryList = new ArrayList<SelectItem>();
            sectionList = new ArrayList<SelectItem>();
            categoryOptionList = new ArrayList<SelectItem>();
            minorityList = new ArrayList<SelectItem>();
            schemeCodeOption = new ArrayList<SelectItem>();
            relationList = new ArrayList<SelectItem>();
            local = (LoanReportFacadeRemote) ServiceLocator.getInstance().lookup("LoanReportFacade");
            common = (CommonReportMethodsRemote) ServiceLocator.getInstance().lookup("CommonReportMethods");
            aitr = (AdvancesInformationTrackingRemote) ServiceLocator.getInstance().lookup("AdvancesInformationTrackingFacade");
            openingFacadeRemote = (AccountOpeningFacadeRemote) ServiceLocator.getInstance().lookup("AccountOpeningFacade");
            reportBaseList = new ArrayList<SelectItem>();
            reportBaseList.add(new SelectItem("0", "--Select--"));
            reportBaseList.add(new SelectItem("O", "Outstanding Balance"));
            reportBaseList.add(new SelectItem("S", "Sanction Amount"));
            reportBasedOnList = new ArrayList<SelectItem>();
            reportBasedOnList.add(new SelectItem("S", "--Select--"));
            reportBasedOnList.add(new SelectItem("R", "Roi"));
            reportBasedOnList.add(new SelectItem("P", "Period"));
            reportBasedOnList.add(new SelectItem("A", "Amount"));
            loanPeriodList = new ArrayList<SelectItem>();
            loanPeriodList.add(new SelectItem("ALL", "ALL"));
            loanPeriodList.add(new SelectItem("Active", "Active"));
            loanPeriodList.add(new SelectItem("Closed", "Closed"));
            reportFormatIn = new ArrayList<SelectItem>();
            reportFormatIn.add(new SelectItem("N", "In Detail"));
            reportFormatIn.add(new SelectItem("Y", "In Summary"));

            List brncode = new ArrayList();
            brncode = common.getBranchCodeList(this.getOrgnBrCode());
            branchCodeList = new ArrayList<SelectItem>();
            if (!brncode.isEmpty()) {
                for (int i = 0; i < brncode.size(); i++) {
                    Vector brnVector = (Vector) brncode.get(i);
                    branchCodeList.add(new SelectItem(brnVector.get(0).toString().length() < 2 ? "0" + brnVector.get(0).toString() : brnVector.get(0).toString(), brnVector.get(1).toString()));
                }
            }
            ////////////////////////////////////////////////////////////////////////
            List genderList = new ArrayList();
            genderList = local.getGridDataList("233");
            genderOption = new ArrayList<SelectItem>();
            if(!genderList.isEmpty()) {
                for (int i = 0; i < genderList.size(); i++) {
                    Vector element = (Vector) genderList.get(i);
                    genderOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
            List industryList = new ArrayList();
            industryList = local.getGridDataList("445");
            industryOption = new ArrayList<SelectItem>();
            if(!industryList.isEmpty()) {
                for (int i = 0; i < industryList.size(); i++) {
                    Vector element = (Vector) industryList.get(i);
                    industryOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
            overdueOption = new ArrayList<SelectItem>();
            overdueOption.add(new SelectItem("N","No"));
            overdueOption.add(new SelectItem("Y","Yes"));
            List npaClassList = new ArrayList();
            npaClassOption = new ArrayList<>();
            npaClassList = local.getGridDataList("195");
            if(!npaClassList.isEmpty()) {
                for (int i = 0; i < npaClassList.size(); i++) {
                    Vector element = (Vector) npaClassList.get(i);
                    npaClassOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
                }
            }
            
            typeOfSecurityList = new ArrayList<SelectItem>();
            typeOfSecurityList.add(new SelectItem("0", "ALL"));
            typeOfSecurityList.add(new SelectItem("DATED", "DATED"));
            typeOfSecurityList.add(new SelectItem("NON-DATED", "NON-DATED"));

            securityNatureList = new ArrayList<SelectItem>();
            securityNatureList.add(new SelectItem("0", "ALL"));
            securityNatureList.add(new SelectItem("P", "PRIMARY"));
            securityNatureList.add(new SelectItem("C", "COLLATERAL"));
          
            initData();
            getDropDownValues();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }
    }

    private void initData() {
        acNatureList.clear();
        accTypeList.clear();
        sectorList.clear();
        subSectorList.clear();
        securityList.clear();
        appCategoryList.clear();
        sectionList.clear();        
        categoryOptionList.clear();
        minorityList.clear();
        schemeCodeOption.clear();
        relationList.clear();
        
        try {
            frmDt = dmy.parse(getTodayDate());
            tempList = common.getAcctNatureOnlyDB();
            if (!tempList.isEmpty()) {
                acNatureList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    acNatureList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            tempList = common.getAcctTypeAsAcNatureOnlyDB(accType);
            if (!tempList.isEmpty()) {
                accTypeList.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    accTypeList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(0).toString()));
                }
            }
            tempList = local.getGridDataList("182");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    sectorList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("183");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    subSectorList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("203");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    schemeCodeOption.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("209");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    categoryOptionList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("208");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    appCategoryList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("204");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    minorityList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("210");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    relationList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("187");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    securityList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }
            tempList = local.getGridDataList("211");
            if (!tempList.isEmpty()) {
                for (int i = 0; i < tempList.size(); i++) {
                    tempVector = (Vector) tempList.get(i);
                    sectionList.add(new SelectItem(tempVector.get(0).toString(), tempVector.get(1).toString()));
                }
            }        
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
        }

    }
    
        public void changeLabel() {
         if (setSecurityDescription1().equalsIgnoreCase("false")) {
            return;
        }
         if (securityType.equalsIgnoreCase("DATED")) {           
            securityDesc2List = new ArrayList<SelectItem>();           
        } else if (securityType.equalsIgnoreCase("NON-DATED")) {        
            securityDesc2List = new ArrayList<SelectItem>();       
        }
    }
       public void onChangeOFSecurityDesc1() {
        if (setSecurityDescription2().equalsIgnoreCase("false")) {
            return;
        }
       
    }
  
       public String setSecurityDescription1() {
        setMessage("");
        setErrorMsg("");
        if (securityType == null  || securityType.equalsIgnoreCase("")) {
            setErrorMsg("Please Select the Security Type.");
            return "false";
        }
        try {
            List resultList = new ArrayList();
            resultList = openingFacadeRemote.getSecurityDesc1(securityType);
            if (resultList.size() <= 0) {
                securityDesc1List = new ArrayList<SelectItem>();
                securityDesc1List.add(new SelectItem("0", "ALL"));
            } else {
                securityDesc1List = new ArrayList<SelectItem>();
                securityDesc1List.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc1List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";

        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "true";
    }

    public String setSecurityDescription2() {
        setMessage("");
        setErrorMsg("");
        if (securityType == null || securityType.equalsIgnoreCase("")) {
            setErrorMsg("Please Select the Security Type.");
            return "false";
        }
        if (securityDesc1 == null || securityDesc1.equalsIgnoreCase("")) {
            setErrorMsg("Please Select the securityDesc1.");
            return "false";
        }    
        try {
            List resultList = new ArrayList();

            
            resultList = openingFacadeRemote.getSecurityDesc2(securityType, securityDesc1);
            if (resultList.isEmpty() && (securityType.equalsIgnoreCase("0") && securityDesc1.equalsIgnoreCase("0"))) {
                resultList = openingFacadeRemote.getSecurityDescAll();
            }
            if (resultList.size() <= 0) {
                securityDesc2List = new ArrayList<SelectItem>();
                securityDesc2List.add(new SelectItem("0", "ALL"));
            } else {
                securityDesc2List = new ArrayList<SelectItem>();
                securityDesc2List.add(new SelectItem("0", "ALL"));
                for (int i = 0; i < resultList.size(); i++) {
                    Vector ele = (Vector) resultList.get(i);
                    securityDesc2List.add(new SelectItem(ele.get(0).toString()));
                }
            }
            return "true";
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
        return "true";
    }
       
    
    public void getDropDownValues() {
        try {
            List listForSector = new ArrayList();
            listForSector = aitr.getSector();
            sectorOption = new ArrayList<SelectItem>();
            sectorOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSector.size(); i++) {
                Vector element = (Vector) listForSector.get(i);
                sectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////SUB SECTOR//////////////////////////////////////////////////
            List listForSubSector = new ArrayList();
            listForSubSector = aitr.getListAsPerRequirement("183","0","0","0","0","0",ymd.format(frmDt),0);
            subSectorOption = new ArrayList<SelectItem>();
            subSectorOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSubSector.size(); i++) {
                Vector element = (Vector) listForSubSector.get(i);
                subSectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////
            List listForPurAdvance = new ArrayList();
            listForPurAdvance = aitr.getListAsPerRequirement("184","0","0","0","0","0",ymd.format(frmDt),0);
            purposeOfAdvanceOption = new ArrayList<SelectItem>();
            purposeOfAdvanceOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForPurAdvance.size(); i++) {
                Vector element = (Vector) listForPurAdvance.get(i);
                purposeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////////
            List listForModeAdvance = new ArrayList();
            listForModeAdvance = aitr.modeOfAdvance();
            modeOfAdvanceOption = new ArrayList<SelectItem>();
            modeOfAdvanceOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForModeAdvance.size(); i++) {
                Vector element = (Vector) listForModeAdvance.get(i);
                modeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////
            List listForTypeAdvance = new ArrayList();
            listForTypeAdvance = aitr.typeOfAdvance();
            typeOfAdvanceOption = new ArrayList<SelectItem>();
            typeOfAdvanceOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForTypeAdvance.size(); i++) {
                Vector element = (Vector) listForTypeAdvance.get(i);
                typeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////////
            List listForSecured = new ArrayList();
            listForSecured = aitr.secured();
            securedOption = new ArrayList<SelectItem>();
            securedOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSecured.size(); i++) {
                Vector element = (Vector) listForSecured.get(i);
                securedOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////////
            List listForGuarCover = new ArrayList();
            listForGuarCover = aitr.guarnteeCover();
            guarnteeCoverOption = new ArrayList<SelectItem>();
            guarnteeCoverOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForGuarCover.size(); i++) {
                Vector element = (Vector) listForGuarCover.get(i);
                guarnteeCoverOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////////////
            List listForIndusType = new ArrayList();
            listForIndusType = aitr.indusType();
            industryTypeOption = new ArrayList<SelectItem>();
            industryTypeOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForIndusType.size(); i++) {
                Vector element = (Vector) listForIndusType.get(i);
                industryTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////////
            List listForPurOfAdv = new ArrayList();
            listForPurOfAdv = aitr.sickness();
            purOfAdvOption = new ArrayList<SelectItem>();
            purOfAdvOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForPurOfAdv.size(); i++) {
                Vector element = (Vector) listForPurOfAdv.get(i);
                purOfAdvOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////Exposure////////////////////////////////////////////////////
            List listForExposure = new ArrayList();
            listForExposure = aitr.purposeOfAdvance("191","0","0","0");
            exposureOption = new ArrayList<SelectItem>();
            exposureOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposure.size(); i++) {
                Vector element = (Vector) listForExposure.get(i);
                exposureOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCategory = new ArrayList();
            listForExposureCategory = aitr.purposeOfAdvance("230","0","0","0");
            exposureCategroyOption = new ArrayList<SelectItem>();
            exposureCategroyOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposureCategory.size(); i++) {
                Vector element = (Vector) listForExposureCategory.get(i);
                exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.purposeOfAdvance("231","0","0","0");
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            
            ////////////////////////////////////////////////////////////////////////
            List listForIntType = new ArrayList();
            listForIntType = local.getGridDataList("202");
            intTypeOption = new ArrayList<SelectItem>();
            for (int i = 0; i < listForIntType.size(); i++) {
                Vector element = (Vector) listForIntType.get(i);
                intTypeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////
            
            List listForRestructure = new ArrayList();
            listForRestructure = aitr.restructure();
            restructuringOption = new ArrayList<SelectItem>();
            restructuringOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForRestructure.size(); i++) {
                Vector element = (Vector) listForRestructure.get(i);
                restructuringOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            List listForSancLvl = new ArrayList();
            listForSancLvl = aitr.sanctionLevel();
            sanctionLevelOption = new ArrayList<SelectItem>();
            sanctionLevelOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSancLvl.size(); i++) {
                Vector element = (Vector) listForSancLvl.get(i);
                sanctionLevelOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            List listForSancAuth = new ArrayList();
            listForSancAuth = aitr.sanctionAuth();
            sanctionAuthOption = new ArrayList<SelectItem>();
            sanctionAuthOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSancAuth.size(); i++) {
                Vector element = (Vector) listForSancAuth.get(i);
                sanctionAuthOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////
//            List listForNpaClass = new ArrayList();
//            listForNpaClass = aitr.npaClass();
//            npaClassOption = new ArrayList<SelectItem>();
//            npaClassOption.add(new SelectItem("0", "ALL"));
//            for (int i = 0; i < listForNpaClass.size(); i++) {
//                Vector element = (Vector) listForNpaClass.get(i);
//                npaClassOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//            }
            ///////////////////////////////////////////////////////////////////////
            List listForCourts = new ArrayList();
            listForCourts = aitr.courts();
            courtsOption = new ArrayList<SelectItem>();
            courtsOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForCourts.size(); i++) {
                Vector element = (Vector) listForCourts.get(i);
                courtsOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////
//            List listForModeSettlement = new ArrayList();
//            listForModeSettlement = aitr.modeOfSetlement();
//            modeOfSettleOption = new ArrayList<SelectItem>();
//            modeOfSettleOption.add(new SelectItem("0", "ALL"));
//            for (int i = 0; i < listForModeSettlement.size(); i++) {
//                Vector element = (Vector) listForModeSettlement.get(i);
//                modeOfSettleOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//            }
//            //////////////////////////////////////////////////////////////////
//            List listForDWR = new ArrayList();
//            listForDWR = aitr.getDWR();
//            debtWaiverReasonOption = new ArrayList<SelectItem>();
//            debtWaiverReasonOption.add(new SelectItem("0", "ALL"));
//            for (int i = 0; i < listForDWR.size(); i++) {
//                Vector element = (Vector) listForDWR.get(i);
//                debtWaiverReasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//            }
//            /////////////////////////////////////////////////////////////////////////
//            List listForAssetClassReason = new ArrayList();
//            listForAssetClassReason = aitr.assetClassReason();
//            assetClassReasonOption = new ArrayList<SelectItem>();
//            assetClassReasonOption.add(new SelectItem("0", "ALL"));
//            for (int i = 0; i < listForAssetClassReason.size(); i++) {
//                Vector element = (Vector) listForAssetClassReason.get(i);
//                assetClassReasonOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
//            }
            /////////////////////////////////////////////////////////////////////////////
            List listForPopuGroup = new ArrayList();
            listForPopuGroup = aitr.populationGrp();
            popuGroupOption = new ArrayList<SelectItem>();
            popuGroupOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForPopuGroup.size(); i++) {
                Vector element = (Vector) listForPopuGroup.get(i);
                popuGroupOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////
//
            
//            ////////////////////////////////////////////////////////////////////////////
//
            ////////////////////////////////////////////////////////////////////////////
            List listForRelation = new ArrayList();
            listForRelation = aitr.getReferenceCode("210");
            relationOption = new ArrayList<SelectItem>();
            relationOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForRelation.size(); i++) {
                Vector element = (Vector) listForRelation.get(i);
                relationOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            
////            drawingPwrIndOption = new ArrayList<SelectItem>();
////            drawingPwrIndOption.add(new SelectItem("0", "ALL"));
////            drawingPwrIndOption.add(new SelectItem("D", "Derived from Security"));
////            drawingPwrIndOption.add(new SelectItem("E", "Equal to the Sanction Limit"));
////            drawingPwrIndOption.add(new SelectItem("M", "Maintained for NPA"));
            List listDrawingPwrInd = new ArrayList();
            listDrawingPwrInd  = aitr.getReferenceCode("232");
            drawingPwrIndOption = new ArrayList<SelectItem>();
            drawingPwrIndOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listDrawingPwrInd.size(); i++) {
                Vector element = (Vector) listDrawingPwrInd.get(i);
                drawingPwrIndOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////////
            List listForAppliCateg = new ArrayList();
            listForAppliCateg = aitr.getReferenceCode("208");
            applicantCategoryOption = new ArrayList<SelectItem>();
            applicantCategoryOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForAppliCateg.size(); i++) {
                Vector element = (Vector) listForAppliCateg.get(i);
                applicantCategoryOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ///////////////////////////////////////////////////////////////////////////////
            List listForCategOption = new ArrayList();
//            listForCategOption = aitr.getReferenceCode("209");
            listForCategOption = aitr.getListAsPerRequirement("209","0","0","0","0","0",ymd.format(frmDt),0);
            categoryOptOption = new ArrayList<SelectItem>();
            categoryOptOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForCategOption.size(); i++) {
                Vector element = (Vector) listForCategOption.get(i);
                categoryOptOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            ////////////////////////////////////////////////////////////////////////////
            List listForMinorCommu = new ArrayList();
//            listForMinorCommu = aitr.getReferenceCode("204");
            listForMinorCommu = aitr.getListAsPerRequirement("204","0","0","0","0","0",ymd.format(frmDt),0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            
            /////////////////////////////////////////////////////////////////////////////////
            List resultRelationList = new ArrayList();
            relOwnerOption = new ArrayList<SelectItem>();
            relOwnerOption.add(new SelectItem("0","ALL"));
            resultRelationList = aitr.getListAsPerRequirement("004",getRelation(),"0","0","0","0",ymd.format(frmDt),0);
            if (!resultRelationList.isEmpty()) {
                for (int i = 0; i < resultRelationList.size(); i++) {
                    Vector ele = (Vector) resultRelationList.get(i);
                    relOwnerOption.add(new SelectItem(ele.get(0).toString(),ele.get(1).toString()));
                }
            }            
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void visibilityOfCatgOption() {
        try {
            List listForCategOption = new ArrayList();
            listForCategOption = aitr.getListAsPerRequirement("209",getApplicantCategory(),getCategoryOpt(), getMinorCommunity(),"0","0",ymd.format(frmDt),0);
            categoryOptOption = new ArrayList<SelectItem>();
            categoryOptOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForCategOption.size(); i++) {
                Vector element = (Vector) listForCategOption.get(i);
                categoryOptOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            //////////////////////////////////////////////////////////////////////////////
            List listForMinorCommu = new ArrayList();
            listForMinorCommu = aitr.getListAsPerRequirement("204",getApplicantCategory(),getCategoryOpt(), getMinorCommunity(),"0","0",ymd.format(frmDt),0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
//        if (applicantCategory.equalsIgnoreCase("GN")) {
//            setCategoryOpt("");
//            flagForAppCateg = true;
//        } else if (applicantCategory.equalsIgnoreCase("WK")) {
//            flagForAppCateg = false;
//        } else {
//            flagForAppCateg = false;
//        }
    }

    public void onblurCategoryWiseMinorityVal(){
        try {
            List listForMinorCommu = new ArrayList();
            listForMinorCommu = aitr.getListAsPerRequirement("204",getApplicantCategory(),getCategoryOpt(), getMinorCommunity(),"0","0",ymd.format(frmDt),0);
            minorCommunityOption = new ArrayList<SelectItem>();
            minorCommunityOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForMinorCommu.size(); i++) {
                Vector element = (Vector) listForMinorCommu.get(i);
                minorCommunityOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
    public void onblurExposureVal(){
        try {
            List listForExposureCategory = new ArrayList();
            listForExposureCategory = aitr.getListAsPerRequirement("230",getExposure(),getExposureCategory(), getExposureCategoryPurpose(),"0","0",ymd.format(frmDt),0);
            exposureCategroyOption = new ArrayList<SelectItem>();
            exposureCategroyOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposureCategory.size(); i++) {
                Vector element = (Vector) listForExposureCategory.get(i);
                exposureCategroyOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            /////////////////////////////////////////////////////////////////////////////////////
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.getListAsPerRequirement("231",getExposure(),getExposureCategory(), getExposureCategoryPurpose(),"0","0",ymd.format(frmDt),0);
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
    public void onblurExposureCategoryVal(){
        try {
            List listForExposureCatePurpose = new ArrayList();
            listForExposureCatePurpose = aitr.getListAsPerRequirement("231",getExposure(),getExposureCategory(), getExposureCategoryPurpose(),"0","0",ymd.format(frmDt),0);
            exposureCatePurposeOption = new ArrayList<SelectItem>();
            exposureCatePurposeOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForExposureCatePurpose.size(); i++) {
                Vector element = (Vector) listForExposureCatePurpose.get(i);
                exposureCatePurposeOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
    public void onblurRelationVal(){
        try {
            List resultList = new ArrayList();
            relOwnerOption = new ArrayList<SelectItem>();
            relOwnerOption.add(new SelectItem("0","ALL"));
//            if(this.getRelation().equalsIgnoreCase("DIRREL")) {
//                resultList = aitr.refRecCode("004");
                resultList = aitr.getListAsPerRequirement("004",getRelation(),"0","0","0","0",ymd.format(frmDt),0);
                if (!resultList.isEmpty()) {
                    for (int i = 0; i < resultList.size(); i++) {
                        Vector ele = (Vector) resultList.get(i);
                        relOwnerOption.add(new SelectItem(ele.get(0).toString(),ele.get(1).toString()));                        
                    }                
                }
//            } else {
//                relOwnerOption.add(new SelectItem("SEL","SELF"));
//            }
//            relNameData(this.getRelation());
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
    public void blurAcctNature() {
        if (accTypeList != null) {
            accTypeList.clear();            
        }
        Vector vtr = null;
        try {
            List result = null;
            result = common.getAcctTypeAsAcNatureOnlyDB(acNature);
            accTypeList.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                accTypeList.add(new SelectItem(vtr.get(0).toString(), vtr.get(0).toString()));                
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public void blurAcctype() {
        if (schemeCodeOption != null) {
            schemeCodeOption.clear();
            schemeMap.clear();
        }
        Vector vtr = null;
        try {
            List result = null;
            result = local.getSchemeType(accType);

            for (int i = 0; i < result.size(); i++) {
                vtr = (Vector) result.get(i);
                schemeCodeOption.add(new SelectItem(vtr.get(0).toString(), vtr.get(1).toString()));
                schemeMap.put(vtr.get(0).toString(), vtr.get(1).toString());
            }
        } catch (Exception e) {
            setErrorMsg(e.getMessage());
        }
    }
    
    public void reportTypeAction() {
        this.setErrorMsg("");
        try {
            this.setFrom(0);
            this.setTo(0);
        } catch (Exception ex) {
            this.setErrorMsg(ex.getMessage());
        }
    }
    
    public void sencionedBased() {
        if(getReportBasedOn().equalsIgnoreCase("R")) {
            from = 0;
            to = 99;
        } else if(getReportBasedOn().equalsIgnoreCase("P")) {
            from = 0;
            to = 240;
        } else if(getReportBasedOn().equalsIgnoreCase("A")) {
            from = 0;
            to = 999999999;
        }
    }

    
    public String viewReport() {
        if (reportBasedOn.equalsIgnoreCase("S")) {
            setErrorMsg("Please select Report Based On.");
            return null;
        } else {
            if(to==0){
            setErrorMsg("To should not be zero.");
            return null;
            }
        }
        if (reportBase.equalsIgnoreCase("0")) {
            setErrorMsg("Please select Report Base.");
            return null;
        }
        String report = "Loan MIS Report";
        try {
            //List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisLaniousReport(accType, sector, subSector, schemeCode, security, appCatgory, section, category, relation, branchCode, minority, ymd.format(frmDt));
            List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisReport(branchCode, acNature, accType, ymd.format(frmDt), 
                    reportBasedOn, from, to, reportBase, sector==null?"":sector, 
                    subSector==null?"":subSector, modeOfAdvance==null?"":modeOfAdvance, 
                    secured==null?"":secured, typeOfAdvance==null?"":typeOfAdvance, 
                    purOfAdv==null?"":purOfAdv, guarnteeCover==null?"":guarnteeCover, 
                    purposeOfAdvance==null?"":purposeOfAdvance, exposure==null?"":exposure, 
                    exposureCategory==null?"":exposureCategory, exposureCategoryPurpose==null?"":exposureCategoryPurpose, 
                    (schemeCode==null || schemeCode.equalsIgnoreCase("ALL"))?"":schemeCode, intType==null?"":intType, applicantCategory==null?"":applicantCategory, 
                    categoryOpt==null?"":categoryOpt, minorCommunity==null?"":minorCommunity, relation==null?"":relation, 
                    relOwner==null?"":relOwner, drawingPwrInd==null?"":drawingPwrInd, popuGroup==null?"":popuGroup, 
                    sanctionLevel==null?"":sanctionLevel, sanctionAuth==null?"":sanctionAuth, courts==null?"":courts, 
                    restructuring==null?"":restructuring, loanPeriod==null?"ALL":loanPeriod,
                    gender==null?"":(gender.equalsIgnoreCase("ALL")?"0":gender), reportFormat,"Y",industry,this.overdue,this.npaClass,"N","N" ,this.securityNature,
                    this.securityType,this.securityDesc1,this.securityDesc2);
            if (!resuList.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("asOnDate", dmy.format(frmDt));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pOverdueShow", overdue);
                
                String reportName = "LoanMisReportAcNatureWiseGroup";
                if(branchCode==null || branchCode.equalsIgnoreCase("0A")|| branchCode.equalsIgnoreCase("90")){
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "P";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "P";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }

                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                          
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                    }
                } else {
                    reportName = "LoanMisReportSectorWiseGroup";
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "P";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "P";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }

                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                          
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                    }
                }                
                fillParams.put("brnCodeP", (branchCode==null || branchCode.equalsIgnoreCase("0A"))?"":branchCode);
                fillParams.put("acNatureP", acNature==null?"":acNature);
                fillParams.put("acTypeP", accType==null?"":accType);
                fillParams.put("sectorP", sector==null?"":sector);
                fillParams.put("subSectorP", subSector==null?"":subSector);
                fillParams.put("modeOfAdvanceP", modeOfAdvance==null?"":modeOfAdvance);                
                fillParams.put("securedP", secured==null?"":secured);
                fillParams.put("typeOfAdvanceP", typeOfAdvance==null?"":typeOfAdvance);
                fillParams.put("purposeOfAdvanceP", purposeOfAdvance==null?"":purposeOfAdvance);
                fillParams.put("guarnteeCoverP", guarnteeCover==null?"":guarnteeCover);
                fillParams.put("purOfAdvP", purOfAdv==null?"":purOfAdv);
                fillParams.put("exposureP", exposure==null?"":exposure);
                fillParams.put("exposureCategoryP", exposureCategory==null?"":exposureCategory);
                fillParams.put("exposureCategoryPurposeP", exposureCategoryPurpose==null?"":exposureCategoryPurpose);
                fillParams.put("schemeCodeP", (schemeCode==null || schemeCode.equalsIgnoreCase("ALL")|| schemeCode.equalsIgnoreCase(""))?"":schemeCode);
                fillParams.put("intTypeP", (intType==null || intType.equalsIgnoreCase("ALL"))?"":intType);
                fillParams.put("applicantCategoryP", applicantCategory==null?"":applicantCategory);
                fillParams.put("categoryOptP", categoryOpt==null?"":categoryOpt);
                fillParams.put("minorCommunityP", minorCommunity==null?"":minorCommunity);
                fillParams.put("relationP", relation==null?"":relation);
                fillParams.put("relOwnerP", relOwner==null?"":relOwner);
                fillParams.put("drawingPwrIndP", drawingPwrInd==null?"":drawingPwrInd);
                fillParams.put("popuGroupP", popuGroup==null?"":popuGroup); 
                fillParams.put("sanctionLevelP", sanctionLevel==null?"":sanctionLevel);
                fillParams.put("sanctionAuthP", sanctionAuth==null?"":sanctionAuth);
                fillParams.put("restructuringP", restructuring==null?"":restructuring);

//                ComparatorChain chObj = new ComparatorChain();
//                chObj.addComparator(new SortedByAccountType());
//                chObj.addComparator(new SortedBySector());
//                chObj.addComparator(new SortedBySubSector());
//                chObj.addComparator(new SortedBySecurity());

//                Collections.sort(resuList, chObj);

//                for (int i = 0; i < resuList.size(); i++) {
//                    LoanMisCellaniousPojo val = resuList.get(i);
//                    System.out.println("Account type - ->" + val.getAcType()+ "Sector - ->" + val.getSector() + "Sub Sector - ->" + val.getSubSector()+ "Security - ->" + val.getSecured());
//                }
                
                new ReportBean().jasperReport(reportName, "text/html", new JRBeanCollectionDataSource(resuList), fillParams, report);
               // new ReportBean().jasperReport("LoanMisReport", "text/html", new JRBeanCollectionDataSource(resuList), fillParams, report);
                return "report";
            } else {
                setErrorMsg("No detail exists !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMsg(e.getLocalizedMessage());
        }
        return null;
    }
    
    public String printExcelReport() {
        if (reportBasedOn.equalsIgnoreCase("S")) {
            setErrorMsg("Please select Report Based On.");
            return null;
        } else {
            if(to==0){
            setErrorMsg("To should not be zero.");
            return null;
            }
        }
        if (reportBase.equalsIgnoreCase("0")) {
            setErrorMsg("Please select Report Base.");
            return null;
        }
        String report = "Loan MIS Report";
        try {
            //List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisLaniousReport(accType, sector, subSector, schemeCode, security, appCatgory, section, category, relation, branchCode, minority, ymd.format(frmDt));
            List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisReport(branchCode, acNature, accType, ymd.format(frmDt), 
                    reportBasedOn, from, to, reportBase, sector==null?"":sector, 
                    subSector==null?"":subSector, modeOfAdvance==null?"":modeOfAdvance, 
                    secured==null?"":secured, typeOfAdvance==null?"":typeOfAdvance, 
                    purOfAdv==null?"":purOfAdv, guarnteeCover==null?"":guarnteeCover, 
                    purposeOfAdvance==null?"":purposeOfAdvance, exposure==null?"":exposure, 
                    exposureCategory==null?"":exposureCategory, exposureCategoryPurpose==null?"":exposureCategoryPurpose, 
                    (schemeCode==null || schemeCode.equalsIgnoreCase("ALL"))?"":schemeCode, intType==null?"":intType, applicantCategory==null?"":applicantCategory, 
                    categoryOpt==null?"":categoryOpt, minorCommunity==null?"":minorCommunity, relation==null?"":relation, 
                    relOwner==null?"":relOwner, drawingPwrInd==null?"":drawingPwrInd, popuGroup==null?"":popuGroup, 
                    sanctionLevel==null?"":sanctionLevel, sanctionAuth==null?"":sanctionAuth, courts==null?"":courts, 
                    restructuring==null?"":restructuring, loanPeriod==null?"ALL":loanPeriod,
                    gender==null?"":(gender.equalsIgnoreCase("ALL")?"0":gender), reportFormat,"Y",industry,this.overdue,this.npaClass,"N","N" , this.securityNature,
                    this.securityType,this.securityDesc1,this.securityDesc2);
            if (!resuList.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("asOnDate", dmy.format(frmDt));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pOverdueShow", overdue);
                
                String reportName = "LoanMisReportAcNatureWiseGroup";
                if(branchCode==null || branchCode.equalsIgnoreCase("0A")|| branchCode.equalsIgnoreCase("90")){
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "P";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "P";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }

                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                          
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                    }
                } else {
                    reportName = "LoanMisReportSectorWiseGroup";
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "P";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "P";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }

                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                          
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                    }
                }                
                fillParams.put("brnCodeP", (branchCode==null || branchCode.equalsIgnoreCase("0A"))?"":branchCode);
                fillParams.put("acNatureP", acNature==null?"":acNature);
                fillParams.put("acTypeP", accType==null?"":accType);
                fillParams.put("sectorP", sector==null?"":sector);
                fillParams.put("subSectorP", subSector==null?"":subSector);
                fillParams.put("modeOfAdvanceP", modeOfAdvance==null?"":modeOfAdvance);                
                fillParams.put("securedP", secured==null?"":secured);
                fillParams.put("typeOfAdvanceP", typeOfAdvance==null?"":typeOfAdvance);
                fillParams.put("purposeOfAdvanceP", purposeOfAdvance==null?"":purposeOfAdvance);
                fillParams.put("guarnteeCoverP", guarnteeCover==null?"":guarnteeCover);
                fillParams.put("purOfAdvP", purOfAdv==null?"":purOfAdv);
                fillParams.put("exposureP", exposure==null?"":exposure);
                fillParams.put("exposureCategoryP", exposureCategory==null?"":exposureCategory);
                fillParams.put("exposureCategoryPurposeP", exposureCategoryPurpose==null?"":exposureCategoryPurpose);
                fillParams.put("schemeCodeP", (schemeCode==null || schemeCode.equalsIgnoreCase("ALL")|| schemeCode.equalsIgnoreCase(""))?"":schemeCode);
                fillParams.put("intTypeP", (intType==null || intType.equalsIgnoreCase("ALL"))?"":intType);
                fillParams.put("applicantCategoryP", applicantCategory==null?"":applicantCategory);
                fillParams.put("categoryOptP", categoryOpt==null?"":categoryOpt);
                fillParams.put("minorCommunityP", minorCommunity==null?"":minorCommunity);
                fillParams.put("relationP", relation==null?"":relation);
                fillParams.put("relOwnerP", relOwner==null?"":relOwner);
                fillParams.put("drawingPwrIndP", drawingPwrInd==null?"":drawingPwrInd);
                fillParams.put("popuGroupP", popuGroup==null?"":popuGroup); 
                fillParams.put("sanctionLevelP", sanctionLevel==null?"":sanctionLevel);
                fillParams.put("sanctionAuthP", sanctionAuth==null?"":sanctionAuth);
                fillParams.put("restructuringP", restructuring==null?"":restructuring);

                ComparatorChain chObj = new ComparatorChain();
                chObj.addComparator(new SortedByAcNoMis());                                
                Collections.sort(resuList, chObj);

//                for (int i = 0; i < resuList.size(); i++) {
//                    LoanMisCellaniousPojo val = resuList.get(i);
//                    System.out.println("Account type - ->" + val.getAcType()+ "Sector - ->" + val.getSector() + "Sub Sector - ->" + val.getSubSector()+ "Security - ->" + val.getSecured());
//                }
                
                new ReportBean().dynamicExcelJasper("LoanMisReport_Excel", "excel", new JRBeanCollectionDataSource(resuList), fillParams, report);
               // new ReportBean().jasperReport("LoanMisReport", "text/html", new JRBeanCollectionDataSource(resuList), fillParams, report);
                return "report";
            } else {
                setErrorMsg("No detail exists !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMsg(e.getLocalizedMessage());
        }
        return null;
    }
    
    public String printPdf() {
        if (reportBasedOn.equalsIgnoreCase("S")) {
            setErrorMsg("Please select Report Based On.");
            return null;
        } else {
            if(to==0){
            setErrorMsg("To should not be zero.");
            return null;
            }
        }
        if (reportBase.equalsIgnoreCase("0")) {
            setErrorMsg("Please select Report Base.");
            return null;
        }
        String report = "Loan MIS Report";
        try {
            System.out.println("start:"+new Date());
            //List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisLaniousReport(accType, sector, subSector, schemeCode, security, appCatgory, section, category, relation, branchCode, minority, ymd.format(frmDt));
            List<LoanMisCellaniousPojo> resuList = local.cbsLoanMisReport(branchCode, acNature, accType, ymd.format(frmDt), 
                    reportBasedOn, from, to, reportBase, sector==null?"":sector, 
                    subSector==null?"":subSector, modeOfAdvance==null?"":modeOfAdvance, 
                    secured==null?"":secured, typeOfAdvance==null?"":typeOfAdvance, 
                    purOfAdv==null?"":purOfAdv, guarnteeCover==null?"":guarnteeCover, 
                    purposeOfAdvance==null?"":purposeOfAdvance, exposure==null?"":exposure, 
                    exposureCategory==null?"":exposureCategory, exposureCategoryPurpose==null?"":exposureCategoryPurpose, 
                    (schemeCode==null || schemeCode.equalsIgnoreCase("ALL"))?"":schemeCode, 
                    (intType==null || intType.equalsIgnoreCase("ALL"))?"":intType, applicantCategory==null?"":applicantCategory, 
                    categoryOpt==null?"":categoryOpt, minorCommunity==null?"":minorCommunity, relation==null?"":relation, 
                    relOwner==null?"":relOwner, drawingPwrInd==null?"":drawingPwrInd, popuGroup==null?"":popuGroup, 
                    sanctionLevel==null?"":sanctionLevel, sanctionAuth==null?"":sanctionAuth, courts==null?"":courts, 
                    restructuring==null?"":restructuring, loanPeriod==null?"ALL":loanPeriod,
                    gender==null?"":(gender.equalsIgnoreCase("ALL")?"0":gender), reportFormat,"Y",industry,this.overdue,this.npaClass,"N","N" ,this.securityNature,
                    this.securityType,this.securityDesc1,this.securityDesc2);
//            System.out.println("start:"+new Date());
            if (!resuList.isEmpty()) {
                String s[] = common.getBranchNameandAddressString(getOrgnBrCode()).split("!");
                Map fillParams = new HashMap();
                fillParams.put("asOnDate", dmy.format(frmDt));
                fillParams.put("pPrintedBy", getUserName());
                fillParams.put("pReportName", report);
                fillParams.put("pbankName", s[0]);
                fillParams.put("pbankAddress", s[1]);
                fillParams.put("pGender", gender==null?"":(gender.equalsIgnoreCase("ALL")?"0":gender));
                fillParams.put("reportFormat", reportFormat);
                fillParams.put("pOverdueShow", overdue);
                String reportName = "LoanMisReportAcNatureWiseGroup";
                if(branchCode==null || branchCode.equalsIgnoreCase("0A")){
                   
                    acNature =(acNature ==null || acNature.equalsIgnoreCase("")|| acNature.equalsIgnoreCase("0"))?"":acNature;
                    accType =(accType ==null || accType.equalsIgnoreCase("")|| accType.equalsIgnoreCase("0"))?"":accType ;
                    branchCode   =(branchCode   ==null || branchCode.equalsIgnoreCase("")|| branchCode.equalsIgnoreCase("0"))?"":branchCode   ;
                    sector =(sector ==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))?"":sector ;
                    subSector=(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))?"":subSector;
                    modeOfAdvance  =(modeOfAdvance  ==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))?"":modeOfAdvance ; 
                    secured  =(secured  ==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))?"":secured  ;
                    typeOfAdvance =(typeOfAdvance ==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))?"":typeOfAdvance ;
                    purOfAdv  =(purOfAdv  ==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))?"":purOfAdv  ;
                    guarnteeCover =(guarnteeCover ==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))?"":guarnteeCover ;
                    purposeOfAdvance  =(purposeOfAdvance  ==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))?"":purposeOfAdvance  ;
                    exposure =(exposure ==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))?"":exposure ;
                    exposureCategory  =(exposureCategory  ==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))?"":exposureCategory  ;
                    exposureCategoryPurpose  =(exposureCategoryPurpose  ==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))?"":exposureCategoryPurpose  ;
                    schemeCode =(schemeCode ==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0"))?"":schemeCode ;
                    intType  =(intType.equalsIgnoreCase("ALL") ||intType  ==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0"))?"":intType  ;
                    applicantCategory  =(applicantCategory  ==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))?"":applicantCategory  ;
                    categoryOpt  =(categoryOpt  ==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))?"":categoryOpt  ;
                    minorCommunity  =(minorCommunity  ==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))?"":minorCommunity  ;
                    relation  =(relation  ==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))?"":relation  ;
                    relOwner  =(relOwner  ==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))?"":relOwner  ;
                    drawingPwrInd  =(drawingPwrInd  ==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))?"":drawingPwrInd  ;
                    popuGroup  =(popuGroup  ==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))?"":popuGroup  ;
                    sanctionLevel =(sanctionLevel ==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))?"":sanctionLevel ;
                    sanctionAuth  =(sanctionAuth  ==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))?"":sanctionAuth  ;
                    courts  =(courts  ==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))?"":courts  ;
                    restructuring =(restructuring ==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))?"":restructuring ;


//                    acNature="";
//                    accType="";
//                    branchCode="";  
//                    sector = "";
//                    subSector= "";                                                  
//                    modeOfAdvance=""; 
//                    secured=""; 
//                    typeOfAdvance="";
//                    purOfAdv=""; 
//                    guarnteeCover="";
//                    purposeOfAdvance=""; 
//                    exposure="";
//                    exposureCategory=""; 
//                    exposureCategoryPurpose=""; 
//                    schemeCode="";
//                    intType=""; 
//                    applicantCategory=""; 
//                    categoryOpt=""; 
//                    minorCommunity=""; 
//                    relation=""; 
//                    relOwner=""; 
//                    drawingPwrInd=""; 
//                    popuGroup=""; 
//                    sanctionLevel="";
//                    sanctionAuth=""; 
//                    courts=""; 
//                    restructuring="";
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "P";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "P";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
//                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            reportName = "LoanMisReportBranchSectorWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="P";                                
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
//                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";                            
                            acNature="";
                            accType="";
                            branchCode="P";   
                            ComparatorChain chObj = new ComparatorChain();
                            chObj.addComparator(new SortedByBranch());
                            chObj.addComparator(new SortedBySubSector());
                            Collections.sort(resuList, chObj);
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";  
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";   
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }

                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                                  
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                               
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                         
                        }                        
                    }
                } else {                    
                    if(!(acNature.equalsIgnoreCase("0")||acNature.equalsIgnoreCase("")||acNature==null)&&(accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for AcNature, AcType, Branch & Jrxml will change wise grouping
                         P=Print*/
                        acNature="";
                        accType="P";
                        branchCode = "";
                    } else {
                        if((accType.equalsIgnoreCase("0")||accType.equalsIgnoreCase("")||accType==null)){
                        /*Only use for Branch, AcNature, AcType & Jrxml will change wise grouping*/
                            acNature="P";
                            accType="P";
                            branchCode = "";
                        }
                        if(!(sector==null || sector.equalsIgnoreCase("")|| sector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                         
                        if(!(subSector==null || subSector.equalsIgnoreCase("")|| subSector.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sector = "P";
                            subSector= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(modeOfAdvance==null || modeOfAdvance.equalsIgnoreCase("")|| modeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            modeOfAdvance= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(secured==null || secured.equalsIgnoreCase("")|| secured.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            secured = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(typeOfAdvance==null || typeOfAdvance.equalsIgnoreCase("")|| typeOfAdvance.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            typeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(purOfAdv==null || purOfAdv.equalsIgnoreCase("")|| purOfAdv.equalsIgnoreCase("0"))){ 
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purOfAdv = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(guarnteeCover==null || guarnteeCover.equalsIgnoreCase("")|| guarnteeCover.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            guarnteeCover = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }                        
                        if(!(purposeOfAdvance==null || purposeOfAdvance.equalsIgnoreCase("")|| purposeOfAdvance.equalsIgnoreCase("0"))){                       
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            purposeOfAdvance = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(exposure==null || exposure.equalsIgnoreCase("")|| exposure.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                        if(!(exposureCategory==null || exposureCategory.equalsIgnoreCase("")|| exposureCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                              
                        }
                        if(!(exposureCategoryPurpose==null || exposureCategoryPurpose.equalsIgnoreCase("")|| exposureCategoryPurpose.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportExposureWiseGroupNoAcNatureWiseGroup";
                            exposure = "P";
                            exposureCategory= "P";
                            exposureCategoryPurpose= "P";
                            acNature="";
                            accType="";
                            branchCode="";                          
                        }
                        if(!(schemeCode==null || schemeCode.equalsIgnoreCase("")|| schemeCode.equalsIgnoreCase("0")|| schemeCode.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            schemeCode = "P";                            
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(intType==null || intType.equalsIgnoreCase("")|| intType.equalsIgnoreCase("0")|| intType.equalsIgnoreCase("ALL"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            intType = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(applicantCategory==null || applicantCategory.equalsIgnoreCase("")|| applicantCategory.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            applicantCategory = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(categoryOpt==null || categoryOpt.equalsIgnoreCase("")|| categoryOpt.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            categoryOpt = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(minorCommunity==null || minorCommunity.equalsIgnoreCase("")|| minorCommunity.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            minorCommunity = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relation==null || relation.equalsIgnoreCase("")|| relation.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relation = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(relOwner==null || relOwner.equalsIgnoreCase("")|| relOwner.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            relOwner = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(drawingPwrInd==null || drawingPwrInd.equalsIgnoreCase("")|| drawingPwrInd.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            drawingPwrInd = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(popuGroup==null || popuGroup.equalsIgnoreCase("")|| popuGroup.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            popuGroup = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionLevel==null || sanctionLevel.equalsIgnoreCase("")|| sanctionLevel.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionLevel = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(sanctionAuth==null || sanctionAuth.equalsIgnoreCase("")|| sanctionAuth.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            sanctionAuth = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(courts==null || courts.equalsIgnoreCase("")|| courts.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            courts = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        }
                        if(!(restructuring==null || restructuring.equalsIgnoreCase("")|| restructuring.equalsIgnoreCase("0"))){
                            reportName = "LoanMisReportSectorWiseGroupNoAcNatureWiseGroup";
                            restructuring = "P";
                            acNature="";
                            accType="";
                            branchCode="";                            
                        } 
                    }
                }
                fillParams.put("brnCodeP", (branchCode==null || branchCode.equalsIgnoreCase("0A"))?"":branchCode);
                fillParams.put("acNatureP", acNature==null?"":acNature);
                fillParams.put("acTypeP", accType==null?"":accType);
                fillParams.put("sectorP", sector==null?"":sector);
                fillParams.put("subSectorP", subSector==null?"":subSector);
                fillParams.put("modeOfAdvanceP", modeOfAdvance==null?"":modeOfAdvance);                
                fillParams.put("securedP", secured==null?"":secured);
                fillParams.put("typeOfAdvanceP", typeOfAdvance==null?"":typeOfAdvance);
                fillParams.put("purposeOfAdvanceP", purposeOfAdvance==null?"":purposeOfAdvance);
                fillParams.put("guarnteeCoverP", guarnteeCover==null?"":guarnteeCover);
                fillParams.put("purOfAdvP", purOfAdv==null?"":purOfAdv);
                fillParams.put("exposureP", exposure==null?"":exposure);
                fillParams.put("exposureCategoryP", exposureCategory==null?"":exposureCategory);
                fillParams.put("exposureCategoryPurposeP", exposureCategoryPurpose==null?"":exposureCategoryPurpose);
                fillParams.put("schemeCodeP", (schemeCode==null || schemeCode.equalsIgnoreCase("ALL")|| schemeCode.equalsIgnoreCase(""))?"":schemeCode);
                fillParams.put("intTypeP", (intType==null || intType.equalsIgnoreCase("ALL"))?"":intType);
                fillParams.put("applicantCategoryP", applicantCategory==null?"":applicantCategory);
                fillParams.put("categoryOptP", categoryOpt==null?"":categoryOpt);
                fillParams.put("minorCommunityP", minorCommunity==null?"":minorCommunity);
                fillParams.put("relationP", relation==null?"":relation);
                fillParams.put("relOwnerP", relOwner==null?"":relOwner);
                fillParams.put("drawingPwrIndP", drawingPwrInd==null?"":drawingPwrInd);
                fillParams.put("popuGroupP", popuGroup==null?"":popuGroup); 
                fillParams.put("sanctionLevelP", sanctionLevel==null?"":sanctionLevel);
                fillParams.put("sanctionAuthP", sanctionAuth==null?"":sanctionAuth);
                fillParams.put("restructuringP", restructuring==null?"":restructuring);

//                ComparatorChain chObj = new ComparatorChain();
//                chObj.addComparator(new SortedByAccountType());
//                chObj.addComparator(new SortedBySector());
//                chObj.addComparator(new SortedBySubSector());
//                chObj.addComparator(new SortedBySecurity());

//                Collections.sort(resuList, chObj);

//                for (int i = 0; i < resuList.size(); i++) {
//                    LoanMisCellaniousPojo val = resuList.get(i);
//                    System.out.println("Account type - ->" + val.getAcType()+ "Sector - ->" + val.getSector() + "Sub Sector - ->" + val.getSubSector()+ "purOfAdv - ->" + val.getPurOfAdvDesc()+";===>"+val.getPurposeOfAdvanceDesc());
//                }
//                
                new ReportBean().openPdf("Loan_MIS_Report_" + ymd.format(frmDt), reportName, new JRBeanCollectionDataSource(resuList), fillParams);

//                new ReportBean().downloadPdf("Loan_MIS_Report", reportName, new JRBeanCollectionDataSource(resuList), fillParams);
                ((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).sendRedirect("/cbs-jsp/faces/report/ReportPage.jsp");
                HttpSession httpSession = getHttpSession();
                httpSession.setAttribute("ReportName", report);
                // new ReportBean().jasperReport("LoanMisReport", "text/html", new JRBeanCollectionDataSource(resuList), fillParams, report);
//                return "report";
            } else {
                setErrorMsg("No detail exists !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            setErrorMsg(e.getLocalizedMessage());
        }
        return null;
    }    
    

    public class SortedBySubSector implements Comparator<LoanMisCellaniousPojo> {
        public int compare(LoanMisCellaniousPojo o1, LoanMisCellaniousPojo o2) {
            return o1.getSubSector().compareTo(o2.getSubSector());
        }
    }

    public class SortedByBranch implements Comparator<LoanMisCellaniousPojo> {
        public int compare(LoanMisCellaniousPojo o1, LoanMisCellaniousPojo o2) {
            return o1.getBrnCode().compareTo(o2.getBrnCode());
        }
    }
    
    public void onblurSectorVal(){
        try {
            List resultList = new ArrayList();
            List listForSubSector = new ArrayList();
            listForSubSector = aitr.getListAsPerRequirement("183",getSector(),"0","0","0","0",ymd.format(frmDt),0);
            subSectorOption = new ArrayList<SelectItem>();
            subSectorOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForSubSector.size(); i++) {
                Vector element = (Vector) listForSubSector.get(i);
                subSectorOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    public void onblurModeOfAdvanceVal(){
        try {
            List listForPurposeOfAdvanceList = new ArrayList();
            listForPurposeOfAdvanceList = aitr.getListAsPerRequirement("184",getSector(),getSubSector(), getModeOfAdvance(),"0","0",ymd.format(frmDt),0);
            purposeOfAdvanceOption = new ArrayList<SelectItem>();
            purposeOfAdvanceOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForPurposeOfAdvanceList.size(); i++) {
                Vector element = (Vector) listForPurposeOfAdvanceList.get(i);
                purposeOfAdvanceOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
            
            List listForGuarCoverList = new ArrayList();
            listForGuarCoverList = aitr.getListAsPerRequirement("188",getSector(),getSubSector(), getModeOfAdvance(),"0","0",ymd.format(frmDt),0);
            guarnteeCoverOption = new ArrayList<SelectItem>();
            guarnteeCoverOption.add(new SelectItem("0", "ALL"));
            for (int i = 0; i < listForGuarCoverList.size(); i++) {
                Vector element = (Vector) listForGuarCoverList.get(i);
                guarnteeCoverOption.add(new SelectItem(element.get(0).toString(), element.get(1).toString()));
            }
        } catch (ApplicationException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = e.getMessage();
        }
    }
    
     public void onCheckSecurity(){
         
         if(isCheckSecurity() == false){
         this.displaySectabs ="";
         this.checkSecurity=true; 
         }else{            
          this.displaySectabs="none"; 
          this.checkSecurity=false;
         }        
     }
    
    
    public void refresh() {
        branchCode = "ALL";
        acNature = "ALL";
        accType = "ALL";
        sector = "ALL";
        subSector = "ALL";
        security = "ALL";
        appCatgory = "ALL";
        section = "ALL";
        category = "ALL";
        minority = "ALL";
        schemeCode = "ALL";
        relation = "ALL";
        errorMsg = "";
        overdue="N";
        try {
            frmDt = dmy.parse(getTodayDate());
        } catch (ParseException parseException) {
        }
    }

    public String exitAction() {
        refresh();
        return "case1";
    }
}
