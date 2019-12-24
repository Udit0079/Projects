<%--Legal Documents
    Document   : LoanAccountsDetails
    Created on : Oct 7, 2010, 3:06:09 PM
    Author     : ROHIT KRISHNA GUPTA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Loan Accounts Details</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanAccountsDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan Accounts Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanAccountsDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;text-align:center;"  styleClass="row1">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LoanAccountsDetails.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LoanAccountsDetails.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="funcRow4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup style="width:100%;text-align:left;">
                            <h:selectOneListbox id="ddFunction"  styleClass="ddlist" value="#{LoanAccountsDetails.functionFlag}" size="1" style="width:125px">
                                <f:selectItems value="#{LoanAccountsDetails.functionValList}" />
                                <a4j:support action="#{LoanAccountsDetails.functionAction1}" event="onblur" focus="ddAcctNo"
                                             reRender="lpg,errorMessage,message,funcRowError,funcRow2,stxtErr1,btnUpdate,btnReschedule,stxtFunction,stxtErr,LoanDetailRegisterPanel,stxtError,ddSector,ddSubSector,ddPurposeofAdvance,ddModeofAdvance,ddTypeofAdvance,ddSecured,ddGuaranteeCover,
                                             ddIndustryType,ddSickness,ddExposure,ddExposureCategory,ddExposureCatePurpose,ddRestructuring,ddSanctionLevel,ddSanctioningAuthority,ddInterestTable,ddInterestType,
                                             ddSchemeCode,ddNPAClassification,ddCourts,ddModeofSettlement,ddDWR,ddACR,ddPopuGroup,stxtCreatedUId,stxtCreationDt,stxtLastUpdateUId,
                                             stxtLastUpdateDt,stxtTotalMod,btnSaveN,stxtPartyName,txtNetWorth1,
                                             txtMarginMoney,calDocDate,calRenewalDate,txtLoanDuration,calDocExpDate,ddCustCat,ddRelation,txtSancAmt,ddDPIndicator,
                                             txtMonthlyIncome1,ddApplicantCatg,ddCatgoption,ddMinority,txtRemarks1,Reschedule,funcRowError,funcRow2,funcRow3,funcRow4,leftPanelSec,ddIndustry,othersRow9,txtCustId,stxtDirName,ddbusineesIndustry,Row33" />
                                <rich:toolTip for="ddFunction" value="Please Select Function For Add,Modify and Enquiry." styleClass="output"></rich:toolTip>
                            </h:selectOneListbox>
                            <h:outputText id="stxtFunction" styleClass="output" value ="#{LoanAccountsDetails.functionDesc}" style="color:green;"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No." ><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                            <h:inputText id="txtAcNo"  value="#{LoanAccountsDetails.oldAcctNo}" style="width :120px" styleClass="input" maxlength="#{LoanAccountsDetails.acNoMaxLen}">
                                <a4j:support action="#{LoanAccountsDetails.getAccountDetail}" event="onblur" 
                                             reRender="LegalDocuments,message,errorMessage,gridPanel1,a22,ddListOfDocs,a3,a5,gridPanel3,a11,gridPanel5,a21,gridPanel6,a20,gridPanel7,a34,gridPanel8,a35,gridPanel9,a44,gridPanel10,a45,gridPanel11,a62,gridPanel12,a63,gridPanel13,a76,gridPanel14,a77,gridPanel9,gridPanel10,gridPanel6,empDetail,table,taskList,table1,taskList1,table2,taskList2,table3,taskList3,table4,taskList4,table5,taskList5,a22,a36,a37,a38,a39,a40,a41,ddSector,ddSubSector,ddPurposeofAdvance,ddModeofAdvance,ddTypeofAdvance,ddSecured,ddGuaranteeCover,
                                             ddIndustryType,ddSickness,ddExposure,ddExposureCategory,ddExposureCatePurpose,ddRestructuring,ddSanctionLevel,ddSanctioningAuthority,ddInterestTable,ddInterestType,
                                             ddSchemeCode,ddNPAClassification,ddCourts,ddModeofSettlement,ddDWR,ddACR,ddPopuGroup,stxtCreatedUId,stxtCreationDt,stxtLastUpdateUId,
                                             stxtLastUpdateDt,stxtTotalMod,stxtError,stxtPartyName,txtNetWorth1,
                                             txtMarginMoney,calDocDate,calRenewalDate,txtLoanDuration,calDocExpDate,ddCustCat,ddRelation,txtSancAmt,ddDPIndicator,stxtAcNo,
                                             txtMonthlyIncome1,ddApplicantCatg,ddCatgoption,ddMinority,txtRemarks1,
                                             btnCalculate,btnUpdate_D,btnReschedule,Reschedule,InstallmentPlanPanel,stxtScheduleNo,loanDetail1,loanDetail2,
                                             disbursementDetailTablePanel,disbursementScheduleTablePanel,SecurityDetails,stxtAccount,tabSecDetail,stxtAcctNo,
                                             txtRetirementAge,ddIndustry,othersRow9,txtCustId,stxtDirName,othersRow8,ddGroupID,ddGroup,ddbusineesIndustry,Row33" 
                                             focus="ddSector"/>
                            </h:inputText>
                            <h:outputLabel id="stxtAcctNo" styleClass="label" value="#{LoanAccountsDetails.acctNo}"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="4" id="a3" style="height:30px;" styleClass="row1" columnClasses="col2,col7,col2,col7" width="100%">
                        <h:outputLabel id="lblPartyName" styleClass="label" value="Party Name" style="padding-right:100px;" />
                        <h:outputText id="stxtPartyName" styleClass="output" style="color:purple" value="#{LoanAccountsDetails.partyName}"/>
                        <h:outputLabel id="lblAcOpenDt" styleClass="label" value="A/c. Opening Date" style="padding-right:50px;"/>
                        <h:outputText id="stxtAcOpenDt" styleClass="output" style="color:purple" value="#{LoanAccountsDetails.acOpenDt}"/>
                    </h:panelGrid>
                    <rich:tabPanel>

                        <%--THIS IS Nishant TAB--%>
                        <rich:tab  label="Borrower's Details" switchType="client"  title="Borrower's Details" >
                            <h:panelGrid id="FuncPanel" style="width:100%;text-align:center;">
                                <h:panelGrid id="borowerInfoPanel" columns="2" style="width:100%;text-align:center;">
                                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                                        <rich:panel header="Sector Wise Deatils" style="text-align:left;">
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblsector" styleClass="label" value="Sector"><font class="required" style="color:red;" title="Sector">*</font></h:outputLabel>
                                                <h:selectOneListbox value="#{LoanAccountsDetails.sector}"id="ddSector" styleClass="ddlist"  size="1" style="width :120px "  title="Sector (182)">
                                                    <f:selectItems  value="#{LoanAccountsDetails.sectorOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurSectorVal()}" event="onblur" reRender="ddSubSector,errorMessage,message" oncomplete="setMask();" focus="ddSubSector"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblSubSector" styleClass="label" value="Sub Sector"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.subSector}"id="ddSubSector" styleClass="ddlist"  size="1"  title="Sub-Sector (183)">
                                                    <f:selectItems value="#{LoanAccountsDetails.subSectorOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurModeOfAdvanceVal()}" event="onblur" reRender="ddPurposeofAdvance,ddGuaranteeCover,errorMessage,message" oncomplete="setMask();" focus="ddModeofAdvance"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblModeofAdvance" styleClass="label" value="Mode of Advance"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.modeOfAdvance}" id="ddModeofAdvance" styleClass="ddlist" size="1"  title="Mode of Advance(185)">
                                                    <f:selectItems value="#{LoanAccountsDetails.modeOfAdvanceOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurModeOfAdvanceVal()}" event="onblur" reRender="ddPurposeofAdvance,ddGuaranteeCover,errorMessage,message" oncomplete="setMask();" focus="ddSecured"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">                                         
                                                <h:outputLabel id="lblSecured" styleClass="label" value="Type of Advances"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.secured}"id="ddSecured" styleClass="ddlist" size="1" title="Type of Advances(187)">
                                                    <f:selectItems value="#{LoanAccountsDetails.securedOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblTypeofAdvance" styleClass="label" value="Term of Advance"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.typeOfAdvance}"id="ddTypeofAdvance" styleClass="ddlist"  size="1" title="Term of Advances(186)">
                                                    <f:selectItems value="#{LoanAccountsDetails.typeOfAdvanceOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblPurposeofAdvance" styleClass="label" value="Sub-Sector's Category(for Quarterly) "><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.purposeOfAdvance}"id="ddPurposeofAdvance" styleClass="ddlist" dir="RTL" size="1" title="Sub-Sector's Category (Quarter wise)(184)">
                                                    <f:selectItems value="#{LoanAccountsDetails.purposeOfAdvanceOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblGuaranteeCover" styleClass="label" value="Sub-Sector's Category(for Yearly)" ><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.guarnteeCover}"id="ddGuaranteeCover" styleClass="ddlist" size="1" title="Sub-Sector's Category(for Yearly)(188)">
                                                    <f:selectItems value="#{LoanAccountsDetails.guarnteeCoverOption}"/>
                                                </h:selectOneListbox>
                                                <%--<h:outputLabel id="lblIndustryType" styleClass="label" value="Industry Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.industryType}"id="ddIndustryType" styleClass="ddlist" size="1">
                                                <f:selectItems value="#{LoanAccountsDetails.industryTypeOption}"/>
                                                </h:selectOneListbox>--%>
                                                <h:outputLabel id="lblSickness" styleClass="label" value="Purpose of Advances" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px"value="#{LoanAccountsDetails.sickness}"id="ddSickness" styleClass="ddlist"  size="1"  title="Purpose of Advances (190)">
                                                    <f:selectItems value="#{LoanAccountsDetails.sicknessOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onBlurPurposeOfAdvVal()}" event="onblur" reRender="ddIndustry,ddExposure,ddExposureCategory,ddExposureCatePurpose,errorMessage,message" oncomplete="setMask();" focus="ddIndustry"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblIndustry" styleClass="label" value="Industry Wise Exposure" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px"value="#{LoanAccountsDetails.industry}"id="ddIndustry" styleClass="ddlist"  size="1"  title="Industry Wise Exposure (445)">
                                                    <f:selectItems value="#{LoanAccountsDetails.industryOption}"/>
                                                    <%--<a4j:support actionListener="#{LoanAccountsDetails.onblurIndustry()}" event="onblur" reRender="ddExposure,ddExposureCategory,ddExposureCatePurpose,errorMessage,message" oncomplete="setMask();" focus="ddExposureCategory"/>--%>
                                                </h:selectOneListbox>
                                                <%--<h:outputLabel ></h:outputLabel>
                                                <h:outputLabel ></h:outputLabel>--%>
                                            </h:panelGrid>
                                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row33" style="width:100%;text-align:left;display:#{LoanAccountsDetails.businessIndustryTypeInvisiable}" styleClass="row2">
                                             <h:outputLabel id="lblbusineesIndustry" styleClass="label" value="Business / Industry Type " ><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px"value="#{LoanAccountsDetails.businessIndustryType}"id="ddbusineesIndustry" styleClass="ddlist"  size="1"  title="Business / Industry Type (555)">
                                                    <f:selectItems value="#{LoanAccountsDetails.businessIndustryTypeList}"/>
                                                </h:selectOneListbox>
                                             <h:outputLabel ></h:outputLabel>
                                              <h:outputLabel ></h:outputLabel>
                                             <h:outputLabel ></h:outputLabel>
                                              <h:outputLabel ></h:outputLabel>
                                            </h:panelGrid>
                                        </rich:panel>    

                                        <rich:panel header="Exposure" style="text-align:left;">
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                                <h:outputLabel id="lblExposure" styleClass="label" value="Exposure"></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.exposure}" id="ddExposure" styleClass="ddlist"  size="1" title="Exposure (191)">
                                                    <f:selectItems value="#{LoanAccountsDetails.exposureOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurExposureVal()}" event="onblur" reRender="ddExposureCategory,ddExposureCatePurpose,errorMessage,message"  focus="ddExposureCategory"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblExposureCategory" styleClass="label" value="Exposure Categroy"></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.exposureCategory}" id="ddExposureCategory" styleClass="ddlist"  size="1" title="Exposure Category (230)">
                                                    <f:selectItems value="#{LoanAccountsDetails.exposureCategroyOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurExposureCategoryVal()}" event="onblur" reRender="ddExposureCatePurpose,errorMessage,message,ddExposure"  focus="ddExposureCatePurpose"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblExposureCatePurpose" styleClass="label" value="Purpose of Exposure Category"></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.exposureCategoryPurpose}" id="ddExposureCatePurpose"  styleClass="ddlist"  size="1" title="Purpose of Exposure Category(231)">
                                                    <f:selectItems value="#{LoanAccountsDetails.exposureCatePurposeOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>
                                        </rich:panel>
                                        <rich:panel header="Scheme Details" style="text-align:left;">
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblSchemeCode" styleClass="label" value="Scheme Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.schemeCode}" id="ddSchemeCode" styleClass="ddlist" size="1" title="Scheme Code(203)">
                                                    <f:selectItems value="#{LoanAccountsDetails.schemeCodeOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblInterestTable" styleClass="label" value="Interest Table"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.intTable}"id="ddInterestTable" styleClass="ddlist" size="1">
                                                    <f:selectItems value="#{LoanAccountsDetails.intTableOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblInterestType" styleClass="label" value="Interest Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.intType}"id="ddInterestType" styleClass="ddlist" size="1" title="Interest Type(202)">
                                                    <f:selectItems value="#{LoanAccountsDetails.intTypeOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>
                                        </rich:panel>
                                        <rich:panel header="Category" style="text-align:left;">
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row20" style="width:100%;text-align:left;" styleClass="row1">
                                                <h:outputLabel id="lblApplicantCatg" styleClass="label" value="Applicant Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.applicantCategory}" id="ddApplicantCatg" styleClass="ddlist"  size="1" title="Applicant Category (208)">
                                                    <f:selectItems value="#{LoanAccountsDetails.applicantCategoryOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.visibilityOfCatgOption()}" event="onblur" reRender="ddCatgoption,ddMinority,errorMessage,message" oncomplete="setMask();" focus="ddCatgoption"/>
                                                </h:selectOneListbox>                                                
                                                <h:outputLabel id="lblCatgoption" styleClass="label" value="Category Option"/>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.categoryOpt}" id="ddCatgoption" styleClass="ddlist"  size="1" title="Category (209)">
                                                    <f:selectItems value="#{LoanAccountsDetails.categoryOptOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurCategoryWiseMinorityVal()}" event="onblur" reRender="ddCatgoption,ddMinority,errorMessage,message" oncomplete="setMask();" focus="ddMinority"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblMinority" styleClass="label" value="Minority Community"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" id="ddMinority" styleClass="ddlist"  size="1" value="#{LoanAccountsDetails.minorCommunity}" title="Minority Community(204)">
                                                    <f:selectItems value="#{LoanAccountsDetails.minorCommunityOption}"/>
                                                </h:selectOneListbox>
                                                <%--<h:outputLabel id="lblRestructuring" styleClass="label" value="Restructuring"><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.restructuring}" id="ddRestructuring" styleClass="ddlist"  size="1">
                                                <f:selectItems value="#{LoanAccountsDetails.restructuringOption}"/>
                                            </h:selectOneListbox>
                                            <h:outputLabel id="lblSanctionLevel" styleClass="label" value="Sanction Level"><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.sanctionLevel}"id="ddSanctionLevel" styleClass="ddlist"  size="1">
                                                <f:selectItems value="#{LoanAccountsDetails.sanctionLevelOption}"/>
                                                </h:selectOneListbox>--%>
                                            </h:panelGrid>
                                        </rich:panel>
                                        <rich:panel header="Others" style="text-align:left;">
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="RowRelation" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblCustCat" styleClass="label" value="Director / Staff"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox  value="#{LoanAccountsDetails.relation}" style="width :120px" id="ddCustCat" styleClass="ddlist" size="1" disabled="#{LoanAccountsDetails.disableRel}" title="Customer Category(210)">
                                                    <f:selectItems value="#{LoanAccountsDetails.relationOption}"/>
                                                    <a4j:support actionListener="#{LoanAccountsDetails.onblurRelationVal}" event="onblur" reRender="othersRow8,ddRelation,errorMessage,message,ddRelName" oncomplete="setMask();" focus="ddRelation"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblRelation" styleClass="label" value="Relation"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" id="ddRelation" styleClass="ddlist"  size="1" value="#{LoanAccountsDetails.relOwner}" disabled="#{LoanAccountsDetails.disableRelOwn}" title="Relation(004)">
                                                    <f:selectItems value="#{LoanAccountsDetails.relOwnerOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblDPIndicator" styleClass="label" value="Drawing Power Indicator(DP)"><font style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" id="ddDPIndicator" styleClass="ddlist"  size="1" value="#{LoanAccountsDetails.drawingPwrInd}" title="Drawing Power Indicator(DP)(232)">
                                                    <f:selectItems value="#{LoanAccountsDetails.drawingPwrIndOption}"/>
                                                </h:selectOneListbox>                                                 
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                                                <h:outputLabel id="lblPopuGroup" styleClass="label" value="Population Group"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.popuGroup}"id="ddPopuGroup" styleClass="ddlist"size="1" title="Population Group(200)">
                                                    <f:selectItems value="#{LoanAccountsDetails.popuGroupOption}"/>
                                                </h:selectOneListbox> 
                                                <h:outputLabel id="lblSanctionLevel" styleClass="label" value="Sanction Level"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.sanctionLevel}"id="ddSanctionLevel" styleClass="ddlist"  size="1" title="Sanction Level(193)">
                                                    <f:selectItems value="#{LoanAccountsDetails.sanctionLevelOption}"/>
                                                </h:selectOneListbox>     
                                                <h:outputLabel id="lblSanctioningAuthority" styleClass="label" value="Sanctioning Authority"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.sanctionAuth}"id="ddSanctioningAuthority" styleClass="ddlist" size="1" title="Sanctioning Authority(194)">
                                                    <f:selectItems value="#{LoanAccountsDetails.sanctionAuthOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblNPAClassification" styleClass="label" value="NPA Classification"/>
                                                <h:selectOneListbox style="width :120px"  value="#{LoanAccountsDetails.npaClass}"id="ddNPAClassification" styleClass="ddlist" size="1" title="NPA Classification(195)">
                                                    <f:selectItems value="#{LoanAccountsDetails.npaClassOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblACR" styleClass="label" value="Asset Class Reason"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.assetClassReason}" id="ddACR" styleClass="ddlist" size="1" title="Asset Class Reason(199)">
                                                    <f:selectItems value="#{LoanAccountsDetails.assetClassReasonOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblCourts" styleClass="label" value="Courts"/>
                                                <h:selectOneListbox   style="width :120px"value="#{LoanAccountsDetails.courts}"id="ddCourts" styleClass="ddlist" size="1" title="Courts(196)">
                                                    <f:selectItems value="#{LoanAccountsDetails.courtsOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                                                <h:outputLabel id="lblModeofSettlement" styleClass="label" value="Mode of Settlement"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px" value="#{LoanAccountsDetails.modeOfSettle}"  id="ddModeofSettlement" styleClass="ddlist" size="1" title="Mode of Settlement(197)">
                                                    <f:selectItems value="#{LoanAccountsDetails.modeOfSettleOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblDWR" styleClass="label" value="Debt Waiver Reason"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="width :120px"  value="#{LoanAccountsDetails.debtWaiverReason}"id="ddDWR" styleClass="ddlist"size="1" title="Debt Waiver Reason(198)">
                                                    <f:selectItems value="#{LoanAccountsDetails.debtWaiverReasonOption}"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblRestructuring" styleClass="label" value="Restructuring"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox  style="width :120px" value="#{LoanAccountsDetails.restructuring}" id="ddRestructuring" styleClass="ddlist"  size="1" title="Restructuring(192)">
                                                    <f:selectItems value="#{LoanAccountsDetails.restructuringOption}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="othersRow1" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblSancAmt" styleClass="label" value="Sanction Amount"/>
                                                <h:inputText id="txtSancAmt" styleClass="input"  disabled="#{LoanAccountsDetails.disableSancAmt}" value="#{LoanAccountsDetails.sancAmount}" style="width :120px">
                                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                                </h:inputText>
                                                <h:outputLabel id="lblNetWorth" styleClass="label" value="Net Worth (in Rupees)"/>
                                                <h:inputText id="txtNetWorth1" styleClass="input" style="width :120px" value="#{LoanAccountsDetails.netWorth1}">
                                                </h:inputText>
                                                <h:outputLabel id="lblMarginMoney" styleClass="label" value="Margin Money (in Rupees)"/>
                                                <h:inputText id="txtMarginMoney" style="width :120px"  styleClass="input" value="#{LoanAccountsDetails.marginMoney}">
                                                </h:inputText>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="othersRow2" style="width:100%;text-align:left;" styleClass="row1">                                                   
                                                <h:outputLabel id="lblDocDate" styleClass="label" value="Document Date"/>
                                                <rich:calendar id="calDocDate" datePattern="dd/MM/yyyy"  value="#{LoanAccountsDetails.docDate}" inputSize="12">
                                                    <a4j:support event="onchanged" focus="#{rich:clientId('calDocExpDate')}InputDate"/>
                                                </rich:calendar>
                                                <h:outputLabel id="lblDocExpDate" styleClass="label" value="Document Expiry date"/>
                                                <rich:calendar id="calDocExpDate" datePattern="dd/MM/yyyy"  value="#{LoanAccountsDetails.docExprDate}" inputSize="12">
                                                    <a4j:support event="onchanged" focus="#{rich:clientId('calRenewalDate')}InputDate"/>
                                                </rich:calendar>
                                                <h:outputLabel id="lblRenewalDate" styleClass="label" value="Renewal date"/>
                                                <rich:calendar id="calRenewalDate" datePattern="dd/MM/yyyy"  value="#{LoanAccountsDetails.renewalDate}" inputSize="12">
                                                    <a4j:support event="onchanged" focus="txtLoanDuration"/>
                                                </rich:calendar>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="othersRow3" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblLoanDuration" styleClass="label" value="Loan Duration (in Months)"/>
                                                <h:inputText id="txtLoanDuration" styleClass="input" style="width :120px" value="#{LoanAccountsDetails.loanDuration}">
                                                </h:inputText>
                                                <h:outputLabel id="lblMonthlyIncome1" styleClass="label" value="Monthly Income"/>
                                                <h:inputText id="txtMonthlyIncome1" style="width :120px" styleClass="input" value="#{LoanAccountsDetails.monthlyIncome1}">
                                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                                </h:inputText>
                                                <h:outputLabel id="lblRemarks1" styleClass="label" value="Remarks"/>
                                                <h:inputText id="txtRemarks1" styleClass="input"  style="width :120px" value="#{LoanAccountsDetails.remarks1}"/>                                                
                                                <%--<h:outputLabel id="lblRelName" styleClass="label" value="Relation Name"></h:outputLabel>
                                                        <h:selectOneListbox style="width :120px" id="ddRelName" styleClass="ddlist"  size="1" value="#{LoanAccountsDetails.relName}">
                                                            <f:selectItems value="#{LoanAccountsDetails.relNameOption}"/>
                                                </h:selectOneListbox>--%>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="othersRow8" style="width:100%;text-align:left;display:#{LoanAccountsDetails.disableDirPanel}" styleClass="row2">
                                                <h:outputLabel id="lblDirCustID" styleClass="label" value="Director/Manager/Secreatory's Customer Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                                                <h:inputText id="txtCustId" styleClass="input" style="width :120px" value="#{LoanAccountsDetails.dirCustId}">
                                                    <a4j:support   actionListener="#{LoanAccountsDetails.dirDetail}" event="onblur"
                                               reRender="leftPanel,btnSave,txtCustomerID" focus="ddACType"/>
                                                </h:inputText>
                                                <%--<h:outputLabel id="lblDirectorName" styleClass="label" value="Director's Name"><font style="color:red;">*</font></h:outputLabel>
                                                <h:inputText id="txtDirCustId" styleClass="input" style="width :120px" value="#{LoanAccountsDetails.dirName}">
                                                    <a4j:support   actionListener="#{LoanAccountsDetails.dirDetail}" event="onblur" reRender="leftPanel,btnSave,txtCustomerID" focus="ddACType"/>
                                                </h:inputText>
                                                <h:outputLabel></h:outputLabel>--%>
                                                <h:outputLabel id="lblDirName" styleClass="label" value="Director Name" style="padding-right:100px;" />
                                                <h:outputText id="stxtDirName" styleClass="output" style="color:purple" value="#{LoanAccountsDetails.dirName}"/>
                                                
                                                <h:outputText/>
                                                <h:outputText/>
                                            </h:panelGrid> 
                                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="othersRow9" style="width:100%;text-align:left;" styleClass="row2">
                                                <h:outputLabel id="lblRetirementAge" styleClass="label" value="Retirement Age(In Years)" style="display:#{LoanAccountsDetails.disableretirPanel}"></h:outputLabel>
                                                <h:inputText id="txtRetirementAge" styleClass="input" style="width :120px;display:#{LoanAccountsDetails.disableretirPanel}" value="#{LoanAccountsDetails.retirementAge}">
                                                </h:inputText>
                                                <h:outputLabel id="lblGroupID" styleClass="label" value="Group " style="display:#{LoanAccountsDetails.lblGroupID}"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox  value="#{LoanAccountsDetails.groupID}" style="display:#{LoanAccountsDetails.ddGroupId}" id="ddGroupID" styleClass="ddlist" size="1" title="Group">
                                                    <f:selectItems value="#{LoanAccountsDetails.listGroupId}"/>
                                                 <a4j:support actionListener="#{LoanAccountsDetails.onblurGroupId()}" event="onblur" reRender="ddGroup,errorMessage,message" oncomplete="setMask();" focus="ddGroup"/>
                                                </h:selectOneListbox>
                                                <h:outputLabel id="lblGroup" styleClass="label" value="Group Details" style="display:#{LoanAccountsDetails.lblGroupType}"><font style="color:red;"></font></h:outputLabel>
                                                <h:selectOneListbox style="display:#{LoanAccountsDetails.ddGroupType}" id="ddGroup" styleClass="ddlist"  size="1" value="#{LoanAccountsDetails.groupType}" title="Group Details(417)">
                                                    <f:selectItems value="#{LoanAccountsDetails.listGroupType}"/>
                                                </h:selectOneListbox>
                                            </h:panelGrid>
                                        </rich:panel>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row16" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lblCreatedUId" styleClass="label" value="Created By User Id"/>
                                            <h:outputText id="stxtCreatedUId"  value="#{LoanAccountsDetails.createdByUId}"/>
                                            <h:outputLabel id="lblCreationDt" styleClass="label" value="Creation Date"/>
                                            <h:outputText  value="#{LoanAccountsDetails.creationDate}"id="stxtCreationDt" />
                                            <h:outputLabel id="lblLastUpdateUId" styleClass="label" value="Last Update By User Id"/>
                                            <h:outputText  value="#{LoanAccountsDetails.lastUpdateUId}"id="stxtLastUpdateUId" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row17" style="width:100%;text-align:left;" styleClass="row2">                                        
                                            <h:outputLabel id="lblLastUpdateDt" styleClass="label" value="Last Updation Date"/>
                                            <h:outputText  value="#{LoanAccountsDetails.lastUpdationDate}"id="stxtLastUpdateDt" />
                                            <h:outputLabel id="lblTotalMod" styleClass="label" value="Total No. of Modifications"/>
                                            <h:outputText id="stxtTotalMod"  value="#{LoanAccountsDetails.totalModifications}"/>
                                            <h:outputText/>
                                            <h:outputText/>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                                    <h:panelGroup id="btnPanel">
                                        <a4j:commandButton  disabled="#{LoanAccountsDetails.flag2}" reRender="message,errorMessage,borowerInfoPanel,stxtAcctNo" action="#{LoanAccountsDetails.advancInfoTrackActivities}" id="btnSaveN" value="Save" >
                                        </a4j:commandButton>
                                        <a4j:commandButton   reRender="message,errorMessage,borowerInfoPanel,stxtAcctNo"action="#{LoanAccountsDetails.refreshOnInvalidAccNo}" id="btnRefreshN" value="Refresh" >
                                        </a4j:commandButton>
                                        <a4j:commandButton  action="#{LoanAccountsDetails.exitForm}" id="btnExitN" value="Exit" >
                                        </a4j:commandButton>
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <%--END OF Nishant TAB--%>

                        <%--THIS IS SHIPRA TAB--%>
                        <rich:tab label="Disbursement"  switchType="client" title="Disbursement" ontabenter="#{rich:element('txtAmountSanctioned')}.focus();">
                            <%--  <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="funcRow2" style="width:100%;text-align:center;" styleClass="row2">
                                  <h:outputText  id="stxtErr" styleClass="error" value ="#{LoanAccountsDetails.showMsg}"/>
                              </h:panelGrid> --%>

                            <h:panelGrid columns="1" id="LoanDetailRegisterPanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="loanDetail1" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblAmountSanctioned" styleClass="label" value="Amount Sanctioned"/>
                                    <h:inputText  id="txtAmountSanctioned"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input" disabled="#{LoanAccountsDetails.disableFlagAmt}" value ="#{LoanAccountsDetails.sanctionAmt}"  style="width :120px;color:blue;" >
                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblSanctionDate" styleClass="label" value="Sanction Date" />
                                    <%--  <h:inputText  id="txtSanctionDate"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input"  disabled="#{LoanAccountsDetails.disableFlagDate}" value ="#{LoanAccountsDetails.sanctionDate}" style="color:blue;" /> --%>
                                    <rich:calendar datePattern="dd/MM/yyyy" disabled="#{LoanAccountsDetails.disableFlagDisbst}" id="txtSanctionDate" inputSize="12" value ="#{LoanAccountsDetails.sanctionDate}">
                                    </rich:calendar>
                                    <h:outputLabel id="lblSanctionDate1" styleClass="label"  />
                                    <h:outputLabel id="lblSanctionDate2" styleClass="label"  />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="loanDetail2" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblProcessingFees" styleClass="label" value="Processing Fees"/>
                                    <h:inputText  id="txtProcessingFees"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value ="#{LoanAccountsDetails.processingFee}" disabled="#{LoanAccountsDetails.disableFlagProcessing}" style="width :120px;color:blue;" maxlength="12"/>
                                    <h:outputLabel id="lblRemarks_D" styleClass="label" value="Remarks" />
                                    <h:inputText  id="txtRemarks_D"  maxlength="50" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" value ="#{LoanAccountsDetails.remarkAIT}" disabled="#{LoanAccountsDetails.disableRemarks}" style="width :120px;color:blue;" />
                                    <h:outputLabel id="lbldisdurstmentType" styleClass="label" value="Disbursement Type" />
                                    <h:selectOneListbox id="dddisdurstmentType"  styleClass="ddlist" style="width :120px;color:blue;" size="1" value="#{LoanAccountsDetails.disbursetmentType}">
                                        <f:selectItems value="#{LoanAccountsDetails.disburestType}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <rich:panel  id="gridPanel22" style="width:100%;text-align:left;" header="Disbursement Schedule">
                                    <h:panelGrid id="disbursementSchedule1Panel" style="width:100%;text-align:center;">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="disbursementDetail1" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lblFlowId" styleClass="label" value="Flow Id" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:inputText  id="txtFlowId" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{LoanAccountsDetails.disableFlagDisbst1}" value ="#{LoanAccountsDetails.flowId}" styleClass="input" style="width :120px;color:blue;" />
                                            <h:outputLabel id="lblDisbursementDate" styleClass="label" value="Disbursement Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <rich:calendar datePattern="dd/MM/yyyy" disabled="#{LoanAccountsDetails.disableFlagDisbst}" id="calDisbursementDate" inputSize="12" value ="#{LoanAccountsDetails.disbursetmentDate}">
                                            </rich:calendar>
                                            <h:outputLabel id="lblDisbursementAmt" styleClass="label" value="Disbursement Amount" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:inputText  id="txtDisbursementAmt"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{LoanAccountsDetails.disableFlagDisbst}" value ="#{LoanAccountsDetails.disbursetmentAmt}" styleClass="input" style="width :120px;color:blue;"  maxlength="10">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:inputText>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="disbursementDetail2" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel id="lblRemarks_DDis" styleClass="label" value="Remarks"/>
                                            <h:inputText  id="txtRemarks_DDis"  maxlength="60" onkeyup="this.value = this.value.toUpperCase();" style="width :120px;color:blue;" disabled="#{LoanAccountsDetails.disableFlagDisbst}" styleClass="input" value ="#{LoanAccountsDetails.remarksDisbursment}" />
                                            <h:outputLabel id="lblScheduleNo" styleClass="label" value="Schedule No" />
                                            <h:outputText id="stxtScheduleNo" styleClass="output" value ="#{LoanAccountsDetails.sno}" style="color:blue;"/>
                                            <h:outputLabel id="lblDeleteFlagDis" styleClass="label" value="Delete Flag" />
                                            <h:selectOneListbox id="ddDeleteFlagDis"  disabled="#{LoanAccountsDetails.disableFlagDisbst}" styleClass="ddlist"  style="color:blue;" value ="#{LoanAccountsDetails.deleteFlagDisburest}" size="1">
                                                <f:selectItems value="#{LoanAccountsDetails.deleteDisburest}"/>
                                                <a4j:support action="#{LoanAccountsDetails.SetValueDisburest}" event="onblur"
                                                             reRender="disbursementScheduleTablePanel,message,errorMessage,disbursementDetail1,disbursementDetail2" />
                                            </h:selectOneListbox>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col1,col2" columns="1" id="disbursementScheduleTablePanel" width="100%" styleClass="row2">
                                        <rich:contextMenu attached="false" id="menudisbursementSchedule" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                            <rich:menuItem value="Select Record" ajaxSingle="true" oncomplete=" #{rich:component('showPanel')}.show()"
                                                           actionListener="#{LoanAccountsDetails.fetchCurrentRowDisburest}">
                                                <a4j:actionparam name="sNo" value="{sNo}" />
                                                <a4j:actionparam name="row" value="{currentRowDisbst}" />
                                            </rich:menuItem>
                                        </rich:contextMenu>
                                        <a4j:region>
                                            <rich:dataTable value="#{LoanAccountsDetails.disbstSch}"
                                                            rowClasses="row1, row2" id = "disbursementScheduleTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemDisbst"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="7"><h:outputText value="Disbursement Schedule" /></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="Flow Id" /></rich:column>
                                                        <rich:column><h:outputText value="Disbursement Amt" /></rich:column>
                                                        <rich:column><h:outputText value="Disbursement Date" /></rich:column>
                                                        <rich:column><h:outputText value="Remarks" /></rich:column>
                                                        <rich:column><h:outputText value="Delete Flag" /></rich:column>
                                                        <rich:column><h:outputText value="Select" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value ="#{itemDisbst.flowId}"/></rich:column>
                                                <rich:column><h:outputText value ="#{itemDisbst.disbursetmentAmt}">
                                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                                    </h:outputText>
                                                </rich:column>
                                                <rich:column><h:outputText value ="#{itemDisbst.disbursetmentDate}"/></rich:column>
                                                <rich:column><h:outputText value ="#{itemDisbst.remarksDisbursment}"/></rich:column>
                                                <rich:column><h:outputText value ="#{itemDisbst.deleteFlagDisburest}"/></rich:column>
                                                <rich:column>
                                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{LoanAccountsDetails.selectDisburest}"  reRender="disbursementScheduleTablePanel,message,errorMessage,disbursementDetail1,disbursementDetail2,txtFlowId">
                                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{itemDisbst}" target="#{LoanAccountsDetails.currentItemDisbst}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRowDisbst}" />

                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="disbursementScheduleTable" maxPages="20" />
                                        </a4j:region>
                                    </h:panelGrid>

                                </rich:panel>
                                <rich:panel  id="richDisbursementDetail" style="width:100%;text-align:left;" header="Disbursement Detail">

                                    <h:panelGrid columnClasses="col1,col2" columns="1" id="disbursementDetailTablePanel" width="100%" styleClass="row2">
                                        <rich:contextMenu attached="false" id="menudisbursementDetail" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                            <rich:menuItem value="Select Record" ajaxSingle="true" oncomplete=" #{rich:component('showPanel')}.show()">

                                            </rich:menuItem>
                                        </rich:contextMenu>
                                        <a4j:region>
                                            <rich:dataTable value="#{LoanAccountsDetails.disbstDtl}"
                                                            rowClasses="row1, row2" id = "disbursementDetailTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemDetail"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="7"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="SNo" /></rich:column>
                                                        <rich:column><h:outputText value="Disbursement Amt" /></rich:column>
                                                        <rich:column><h:outputText value="Disbursement Date" /></rich:column>

                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value ="#{itemDetail.sno}"/></rich:column>
                                                <rich:column><h:outputText value ="#{itemDetail.disbursetmentAmt}">
                                                        <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                                    </h:outputText>
                                                </rich:column>
                                                <rich:column><h:outputText value ="#{itemDetail.disbursetmentDate}"/></rich:column>

                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="disbursementDetailTable" maxPages="20" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </rich:panel>

                                <h:panelGrid id="disbursementDetailFooter" style="width:100%;text-align:center;" styleClass="footer">
                                    <h:panelGroup id="disbursementFooter">
                                        <a4j:commandButton id="btnSave_D1" value="Save" >
                                            <a4j:support action="#{LoanAccountsDetails.saveActionDisbursementSchedule}" event="onclick"
                                                         reRender="message,errorMessage,disbursementDetail1,disbursementDetail2,disbursementScheduleTablePanel" />

                                        </a4j:commandButton>
                                        <a4j:commandButton id="btnReset_D1" value="Reset" >
                                            <a4j:support action="#{LoanAccountsDetails.resetVAlues}" event="onclick"
                                                         reRender="LoanDetailRegisterPanel,message,errorMessage" />
                                        </a4j:commandButton>
                                        <a4j:commandButton id="btnExit_D1" value="Exit" action="#{LoanAccountsDetails.exitForm}">
                                        </a4j:commandButton>
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Repayment Schedule" switchType="client" title="Repayment Schedule" ontabenter="#{rich:element('ddTypeOfInvestmentPlan')}.focus();">
                            <h:panelGrid id="InstallmentPlanPanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col1,col2" columns="1" id="InstallmentPlanGrid" width="100%" styleClass="row1">

                                    <a4j:region>
                                        <rich:dataTable value ="#{LoanAccountsDetails.instPlan}"
                                                        rowClasses="row1, row2" id = "InstallmentPlanTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemInt"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                            <f:facet name="header">
                                                <rich:columnGroup>

                                                    <rich:column colspan="19"></rich:column>

                                                    <rich:column breakBefore="true"><h:outputText value="Sno" /> </rich:column>                                                    
                                                    <rich:column><h:outputText value="Due Date"/></rich:column>
                                                    <rich:column><h:outputText value="Opening Principle"/></rich:column>
                                                    <rich:column><h:outputText value="Principle Amount"/></rich:column>
                                                    <rich:column><h:outputText value="Interest Amount" /></rich:column>
                                                    <rich:column><h:outputText value="Installment" /></rich:column>
                                                    <rich:column><h:outputText value="Closing Principle"/></rich:column>
                                                    <rich:column><h:outputText value="Status" /></rich:column>
                                                    <rich:column><h:outputText value="Pay Date" /></rich:column>
                                                    <rich:column><h:outputText value="Entered By" /></rich:column>
                                                    <rich:column><h:outputText value="Remarks" /></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column><h:outputText value ="#{itemInt.sno}"/></rich:column>
                                            <rich:column><h:outputText  value ="#{itemInt.dueDate}"/></rich:column>
                                            <rich:column><h:outputText  value ="#{itemInt.openingPrinciple}"/></rich:column>
                                            <rich:column><h:outputText  value ="#{itemInt.principleAmt}"/></rich:column>
                                            <rich:column ><h:outputText value ="#{itemInt.interestAmt}"/></rich:column>
                                            <rich:column ><h:outputText  value ="#{itemInt.installment}"/></rich:column>
                                            <rich:column><h:outputText  value ="#{itemInt.closingPrinciple}"/></rich:column>
                                            <rich:column ><h:outputText value ="#{itemInt.status}"/></rich:column>
                                            <rich:column ><h:outputText value ="#{itemInt.payDate}"/></rich:column>
                                            <rich:column ><h:outputText value ="#{itemInt.enteredBy}"/></rich:column>
                                            <rich:column ><h:outputText value ="#{itemInt.remarks}"/></rich:column>


                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="InstallmentPlanTable" maxPages="20" />
                                    </a4j:region>
                                </h:panelGrid>


                                <h:panelGrid id="AmortizationSchedulePanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col1,col2" columns="1" id="AmortizationScheduleGrid" width="100%" styleClass="row2">

                                        <a4j:region>
                                            <rich:dataTable value ="#{LoanAccountsDetails.amortSch}"
                                                            rowClasses="row1, row2" id = "AmortizationScheduleTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemAmor"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                                <f:facet name="header">
                                                    <rich:columnGroup>

                                                        <rich:column colspan="19"></rich:column>

                                                        <rich:column breakBefore="true"><h:outputText value="Sno" /> </rich:column>
                                                        <rich:column><h:outputText value="Due Date"/></rich:column>
                                                        <rich:column><h:outputText value="Opeing Principle"/></rich:column>
                                                        <rich:column><h:outputText value="Principle Amount"/></rich:column>
                                                        <rich:column><h:outputText value="Interest Amount" /></rich:column>
                                                        <rich:column><h:outputText value="Installment" /></rich:column>
                                                        <rich:column><h:outputText value="Closing Principle"/></rich:column>
                                                        <rich:column><h:outputText value="Periodicity" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value ="#{itemAmor.sno}"/></rich:column>
                                                <rich:column><h:outputText  value ="#{itemAmor.dueDate}"/></rich:column>
                                                <rich:column><h:outputText  value ="#{itemAmor.openingPrinciple}"/></rich:column>
                                                <rich:column><h:outputText  value ="#{itemAmor.principleAmt}"/></rich:column>
                                                <rich:column ><h:outputText value ="#{itemAmor.interestAmt}"/></rich:column>
                                                <rich:column ><h:outputText  value ="#{itemAmor.installment}"/></rich:column>
                                                <rich:column><h:outputText  value ="#{itemAmor.closingPrinciple}"/></rich:column>
                                                <rich:column ><h:outputText  value ="#{itemAmor.periodicity}"/></rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="AmortizationScheduleTable" maxPages="20" />
                                        </a4j:region>
                                    </h:panelGrid>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="TypeOfInvestmentPlan1" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblTypeOfInvestmentPlan" styleClass="label" value="Type Of Investment Plan" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  id="ddTypeOfInvestmentPlan" style="width:134px;color:blue;" styleClass="ddlist" size="1" value="#{LoanAccountsDetails.investmentType}">
                                        <f:selectItems value="#{LoanAccountsDetails.investmentTypeList}" />
                                        <a4j:support  event="onblur" focus="ddIntServe" action="#{LoanAccountsDetails.changeEventOfInstallmentType}"
                                                      oncomplete="if(#{rich:element('ddTypeOfInvestmentPlan')}.value == 'EIWIMP'){#{rich:element('ddIntServe')}.focus();}else{#{rich:element('ddPeriodicity')}.focus();}"
                                                      reRender="txtRateOfInstallment,ddIntPrinciple,ddIntServe,lblstatusMsg,message,errorMessage,stxtAmountSanction,stxtOsBalance"/>

                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblIntServe" styleClass="label" value="Interest Serve" />
                                    <h:selectOneListbox  id="ddIntServe" style="width:134px;color:blue;" styleClass="ddlist" size="1" disabled="#{LoanAccountsDetails.intServeDisable}" value="#{LoanAccountsDetails.intServe}" >
                                        <f:selectItems value="#{LoanAccountsDetails.intServeList}"/>
                                         <a4j:support  event="onchange" focus="ddPeriodicity" action="#{LoanAccountsDetails.changeEventOfInterestServe}"
                                                      reRender="txtRateOfInstallment,ddIntPrinciple,ddIntServe,lblstatusMsg,message,errorMessage,stxtAmountSanction,stxtOsBalance,ddPeriodicity"/>
                                    </h:selectOneListbox>

                                </h:panelGrid>
                                <rich:panel  id="richEmi"  style="width:100%;text-align:left;" header="EMI/EPRP Formation Schedule">
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TypeOfInvestmentPlan2" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblAmountSanction" styleClass="label" value="Amount Sanctioned" />
                                        <h:outputText id="stxtAmountSanction" style="width:134px;color:blue;" styleClass="input"  value="#{LoanAccountsDetails.sanctionedAmt}"/>
                                        <h:outputLabel id="lblOsBalance" styleClass="label"  value="Os Balance"/>
                                        <h:outputText id="stxtOsBalance" styleClass="label"  style="color:blue;" value="#{LoanAccountsDetails.osBalance}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TypeOfInvestmentPlan3" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPeriodicity" styleClass="label" value="Periodicity"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox  id="ddPeriodicity" style="width:134px;color:blue;" styleClass="ddlist"  value ="#{LoanAccountsDetails.periodicity}" size="1">
                                            <f:selectItems value="#{LoanAccountsDetails.periodList}"/>
                                        <a4j:support  event="onblur" action="#{LoanAccountsDetails.setStartDateOnPeriodicity}"
                                                      reRender="calStartingDt,message,errorMessage"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblStartingDt" styleClass="label" value="Starting Date"  />
                                        <rich:calendar datePattern="dd/MM/yyyy" value="#{LoanAccountsDetails.startDate}"id="calStartingDt" inputSize="13"  style="color:blue;">
                                        </rich:calendar>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TypeOfInvestmentPlan4" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblRateOfInstallment" styleClass="label" value="Rate Of Installment" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtRateOfInstallment" style="width:134px;color:blue;" disabled="#{LoanAccountsDetails.roiDisable}" value="#{LoanAccountsDetails.rateOfInst}" styleClass="input"  />
                                        <h:outputLabel id="lblIntPrinciple" styleClass="label" value="Int / Principle" />
                                        <h:selectOneListbox id="ddIntPrinciple" style="width:134px;color:blue;" disabled="#{LoanAccountsDetails.intPrincipleDisable}" value="#{LoanAccountsDetails.intPrinciple}" styleClass="ddlist"   size="1">
                                            <f:selectItems value="#{LoanAccountsDetails.intPrincipleList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TypeOfInvestmentPlan5" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPeriods" styleClass="label" value="Periods"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtPeriods" value="#{LoanAccountsDetails.periods}" maxlength="3" style="width:134px;color:blue;" styleClass="input"  />
                                        <h:outputLabel id="lblRateOfInstallment1" styleClass="label"  />
                                        <h:outputLabel id="lblRateOfInstallment2" styleClass="label" />
                                    </h:panelGrid>
                                </rich:panel>

                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="status" style="width:100%;text-align:center;color:green;" styleClass="row2">
                                    <h:outputLabel id="lblstatusMsg" styleClass="label"  value="#{LoanAccountsDetails.statusmsg}" />
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid id="footerPanel_D" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanel_D">
                                    <a4j:commandButton id="btnReschedule" value="Reschedule" onclick="#{rich:component('reschedulePanel')}.show()" disabled="#{LoanAccountsDetails.resheduleDisable}">

                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnUpdate_D" value="Update" disabled="#{LoanAccountsDetails.updateDisable}">
                                        <a4j:support action="#{LoanAccountsDetails.updateActionRepayment}" event="onclick"
                                                     reRender="richEmi,TypeOfInvestmentPlan1,message,errorMessage,InstallmentPlanGrid" />
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnCalculate" value="Calculate" disabled="#{LoanAccountsDetails.calculateDisable}">
                                        <a4j:support action="#{LoanAccountsDetails.calculateActionRepayment}" event="onclick"
                                                     reRender="message,errorMessage,lblstatusMsg,AmortizationSchedulePanel,btnCalculate" />
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnReset" value="Reset" >
                                        <a4j:support action="#{LoanAccountsDetails.resetRepaymentSchedule}" event="onclick"
                                                     reRender="ddIntServe,ddPeriodicity,ddIntPrinciple,txtPeriods,InstallmentPlanPanel,message,errorMessage,TypeOfInvestmentPlan1,TypeOfInvestmentPlan2,TypeOfInvestmentPlan3,TypeOfInvestmentPlan4,TypeOfInvestmentPlan5" />
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit_D" value="Exit" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <%--END OF SHIPRA TAB--%>



                        <rich:tab label="Employment Details" switchType="client" id="empDetail" ontabenter="#{rich:element('txtFirmName')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a5" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a6" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblFirmName" styleClass="label" value="Customer Employer Name :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtFirmName" maxlength="40" tabindex="1"  onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.firmName}" style="width :120px" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a7" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblCoAdd" styleClass="label" value="Customer Employer Address :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtCoAdd" maxlength="60" tabindex="3" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.comAdd}" style="width :120px" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a8" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblFrom" styleClass="label" value="From :"><font class="required" color="red">*</font></h:outputLabel>
                                        <rich:calendar datePattern="dd/MM/yyyy" id="calFrom" value="#{LoanAccountsDetails.fromDt}" tabindex="5" inputSize="12" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a9" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblDesignation" styleClass="label" value="Designation :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtDesignation" maxlength="25" onblur="this.value = this.value.toUpperCase();" tabindex="7" value="#{LoanAccountsDetails.designation}" style="width :120px" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a16" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblEmpId" styleClass="label" value="Employee ID :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtEmpId" maxlength="100" onblur="this.value = this.value.toUpperCase();" tabindex="9" value="#{LoanAccountsDetails.empId}" style="width :120px" styleClass="input"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a10" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel3" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a11" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a12" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblCoPhoneNo" styleClass="label" value="Co. Phone No. :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtCoPhoneNo" maxlength="20" onblur="this.value = this.value.toUpperCase();" tabindex="2" value="#{LoanAccountsDetails.comPhoneNo}" style="width :120px" styleClass="input"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a13" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblMonthlyIncome" styleClass="label" value="Monthly Income :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtMonthlyIncome" maxlength="15" onblur="this.value = this.value.toUpperCase();" tabindex="4" value="#{LoanAccountsDetails.monthlyIncome}" style="width :120px" styleClass="input"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a14" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblTo" styleClass="label" value="To :"><font class="required" color="red">*</font></h:outputLabel>
                                            <rich:calendar datePattern="dd/MM/yyyy" id="calTo" value="#{LoanAccountsDetails.toDt}" tabindex="6" inputSize="12"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a15" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblReasonOfLeaving" styleClass="label" value="Reason Of Leaving :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtReasonOfLeaving" maxlength="60" tabindex="8" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.reasonOfLeaving}" style="width :120px" styleClass="input"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a17" style="height:30px;" styleClass="row2" width="100%">
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanAccountsDetails.gridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Employment Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                                <rich:column><h:outputText value="Firm Name" /></rich:column>
                                                <rich:column><h:outputText value="Firm Address" /></rich:column>
                                                <rich:column><h:outputText value="Firm Phone No." /></rich:column>
                                                <rich:column><h:outputText value="From Date" /></rich:column>
                                                <rich:column><h:outputText value="To Date" /></rich:column>
                                                <rich:column><h:outputText value="Designation" /></rich:column>
                                                <rich:column><h:outputText value="Monthly Income" /></rich:column>
                                                <rich:column><h:outputText value="Reason To Leave" /></rich:column>
                                                <rich:column><h:outputText value="Employee ID" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.firmName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.firmAdd}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.firmPhoneNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.fromDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.toDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.designation}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.monthlyIncome}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.reasonToQuit}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,table,taskList">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{LoanAccountsDetails.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20" />
                                </a4j:region>

                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave" tabindex="10"  value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,table,taskList" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh" tabindex="11" value="Refresh" action="#{LoanAccountsDetails.resetEmpDetails}"  reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,table,taskList" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit" tabindex="12" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>

                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Legal Documents"  id="LegalDocuments" switchType="client" ontabenter="#{rich:element('ddListOfDocs')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a18" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel5" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a21" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo1" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo1" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a22" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblListOfDocs" styleClass="label" value="List Of Documents :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddListOfDocs" tabindex="1" styleClass="ddlist" value="#{LoanAccountsDetails.docCode}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.docList}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a24" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblRecieveDt" styleClass="label" value="Recieve Date :"><font class="required" color="red">*</font></h:outputLabel>
                                        <rich:calendar datePattern="dd/MM/yyyy" id="calRecieveDt" value="#{LoanAccountsDetails.recDt}" tabindex="3" inputSize="12" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a26" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks :"/>
                                        <h:inputText id="txtRemarks" maxlength="20" tabindex="5" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.remarks}" style="width :120px" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a28" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblScanDetailFlag" styleClass="label" value="Scan Doc :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddScanDetailFlag" tabindex="7" styleClass="ddlist" value="#{LoanAccountsDetails.scanFlag}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.scanFlagList}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a19" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel6" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a20" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName1" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName1" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a23" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblDueDt" styleClass="label" value="Due Date :"><font class="required" color="red">*</font></h:outputLabel>
                                            <rich:calendar datePattern="dd/MM/yyyy" id="calDueDt" value="#{LoanAccountsDetails.dueDt}" tabindex="2" inputSize="12"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a25" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblExpDt" styleClass="label" value="Expiry Date :"><font class="required" color="red">*</font></h:outputLabel>
                                            <rich:calendar datePattern="dd/MM/yyyy" id="calExpDt" value="#{LoanAccountsDetails.expDt}" tabindex="4" inputSize="12"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a27" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblDelFlag" styleClass="label" value="Del Doc :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddDelFlag" tabindex="6" styleClass="ddlist" value="#{LoanAccountsDetails.delFlag}" size="1" style="width :120px">
                                                <f:selectItems value="#{LoanAccountsDetails.delFlagList}" />
                                            </h:selectOneListbox>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a29" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblForOtherDetail" styleClass="label" value="For Other Details :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddForOtherDetail" tabindex="8" styleClass="ddlist" value="#{LoanAccountsDetails.forOtherDetails}" size="1" style="width :120px">
                                                <f:selectItems value="#{LoanAccountsDetails.forOtherDetailList}" />
                                                <a4j:support reRender="message,errorMessage,a30,a31" limitToList="true" event="onchange" action="#{LoanAccountsDetails.forOtherDetailsMethod}" focus="txtFreeText1"/>
                                            </h:selectOneListbox>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columns="1"width="100%">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3" columns="5" id="a30" style="height:30px;" styleClass="row2" width="100%">
                                    <h:inputText id="txtFreeText1" tabindex="9" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText1}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText2" tabindex="10" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText2}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText3" tabindex="11" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText3}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText4" tabindex="12" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText4}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText5" tabindex="13" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText5}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columns="1"width="100%">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3" columns="5" id="a31" style="height:30px;" styleClass="row1" width="100%">
                                    <h:inputText id="txtFreeText6" tabindex="14" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText6}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText7" tabindex="15" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText7}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText8" tabindex="16" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText8}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText9" tabindex="17" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText9}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                    <h:inputText id="txtFreeText10" tabindex="18" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.freeText10}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.otherDetailFlag==true}" />
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanAccountsDetails.gridDetail1}" var="dataItem1"
                                                    rowClasses="gridrow1,gridrow2" id="taskList1" rows="2" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Document Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="A/c. No." /></rich:column>
                                                <rich:column><h:outputText value="Document" /></rich:column>
                                                <rich:column><h:outputText value="Due Date" /></rich:column>
                                                <rich:column><h:outputText value="Recieve Date" /></rich:column>
                                                <rich:column><h:outputText value="Expiry Date" /></rich:column>
                                                <rich:column><h:outputText value="Remarks" /></rich:column>
                                                <rich:column><h:outputText value="Delete Doc" /></rich:column>
                                                <rich:column><h:outputText value="Scan Doc" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem1.acNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.docCode}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.dueDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.recieveDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.expDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.remarks}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.delFlag}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem1.scanFlag}" /></rich:column>

                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink1" oncomplete="#{rich:component('deletePanel1')}.show()" reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem1}" target="#{LoanAccountsDetails.currentItem1}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow1}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter1" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave1" tabindex="19"  value="Save" oncomplete="#{rich:component('savePanel1')}.show()" reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh1" tabindex="20" value="Refresh" action="#{LoanAccountsDetails.resetDocumentDetailForm}"  reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit1" tabindex="21" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>

                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Loan Share Details" switchType="client" ontabenter="#{rich:element('ddShareType')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a32" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel7" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a34" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo2" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo2" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a36" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblShareType" styleClass="label" value="Share Type :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddShareType" tabindex="1" styleClass="ddlist" value="#{LoanAccountsDetails.shareType}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.shareTypeList}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a38" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblNomineeName" styleClass="label" value="Nominee Name :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtNomineeName" maxlength="40" tabindex="3" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.nominee}" style="width :120px" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a40" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblNominalMem" styleClass="label" value="Nominal Member :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddNominalMem" tabindex="5" styleClass="ddlist" value="#{LoanAccountsDetails.nominalMem}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.nominalMemList}" />
                                            <a4j:support reRender="message,errorMessage,a40,a41" limitToList="true" event="onchange" action="#{LoanAccountsDetails.setMemberNoLable}" focus="txtNominalMemNo"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a33" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel8" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a35" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName2" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName2" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a37" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblAppShareMoney" styleClass="label" value="Applicant Share Money :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtAppShareMoney" maxlength="15" tabindex="2" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.appShareMoney}" style="width :120px" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a39" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblMemDate" styleClass="label" value="Membership Date :"><font class="required" color="red">*</font></h:outputLabel>
                                            <rich:calendar datePattern="dd/MM/yyyy" id="calMemDate" value="#{LoanAccountsDetails.membershipDt}" tabindex="4" inputSize="12" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a41" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblNominalMemNo" styleClass="label" value="#{LoanAccountsDetails.nomMemLable}"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtNominalMemNo" maxlength="16" tabindex="6" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.nominalMemNo}" style="width :120px" styleClass="input" />
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter2" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave2" tabindex="7"  value="Save" oncomplete="#{rich:component('savePanel2')}.show()" reRender="message,errorMessage,gpFooter,a34,a35,a36,a37,a38,a39,a40,a41" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh2" tabindex="8" value="Refresh" action="#{LoanAccountsDetails.resetLoanShareDetailForm}"  reRender="message,errorMessage,gpFooter,a34,a35,a36,a37,a38,a39,a40,a41" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit2" tabindex="9" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>

                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab label="Guarantor Details" switchType="client" ontabenter="#{rich:element('ddGurantorAcLabel')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a42" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel9" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a44" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo3" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo3" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a46" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblGurantorAcLabel" styleClass="label" value="Guarantor? :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddGurantorAcLabel" tabindex="1" styleClass="ddlist" disabled="#{LoanAccountsDetails.gFlag}" value="#{LoanAccountsDetails.guarAcChk}" size="1" style="width :140px">
                                            <f:selectItems value="#{LoanAccountsDetails.guarAcChkList}" />
                                            <a4j:support reRender="message,errorMessage,a46,a47,a48,a50,a52,a59,a49,txtGurantorNetWorth,actulNetWorthId,txtGurantorAcNo" event="onchange" action="#{LoanAccountsDetails.guarAcChkMethod}"
                                                         oncomplete="if(#{rich:element('ddGurantorAcLabel')}.value == 'YA'){#{rich:element('txtGurantorAcNo')}.focus();}
                                                         else if(#{rich:element('ddGurantorAcLabel')}.value == 'YC'){#{rich:element('txtGurantorAcNo')}.focus();}
                                                         else if(#{rich:element('ddGurantorAcLabel')}.value == 'YF'){#{rich:element('txtGurantorAcNo')}.focus();}
                                                         else if(#{rich:element('ddGurantorAcLabel')}.value == 'No'){#{rich:element('txtGurantorName')}.focus();}
                                                         else{#{rich:element('ddGurantorAcLabel')}.focus();}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a48" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblGurantorName" styleClass="label" value="Guarantor Name :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtGurantorName" maxlength="40" tabindex="3" disabled="#{LoanAccountsDetails.guarFlag1}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarName}" size="25" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a50" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblFatherName" styleClass="label" value="Father's Name :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtFatherName" tabindex="5" maxlength="40" disabled="#{LoanAccountsDetails.guarFlag3}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarFatherName}" size="25" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a52" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAge" styleClass="label" value="Age :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtAge" tabindex="7" maxlength="3" disabled="#{LoanAccountsDetails.guarFlag5}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarAge}" size="25" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a54" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation :"/>
                                        <h:inputText id="txtOccupation" maxlength="30" tabindex="9" disabled="#{LoanAccountsDetails.gFlag}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarOccupation}" size="25" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a56" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblGurantorFirmName" styleClass="label" value="Firm Name :"/>
                                        <h:inputText id="txtGurantorFirmName" maxlength="40" disabled="#{LoanAccountsDetails.gFlag}" tabindex="11" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarFirmName}" size="25" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a58" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblOffAddress" styleClass="label" value="Firm Address :"/>
                                        <h:inputText id="txtOffAddress" maxlength="60" tabindex="13" disabled="#{LoanAccountsDetails.gFlag}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarOffAddress}" size="25" styleClass="input" />
                                    </h:panelGrid>


                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a43" width="100%"> 
                                    <h:panelGrid columns="1" id="gridPanel10" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a45" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName3" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName3" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a47" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblGurantorAcNo" styleClass="label" value="Gurantor A/c No/Cust Id:" rendered="#{LoanAccountsDetails.gFlag1==true}"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:panelGroup id="btnAcno">
                                                <h:inputText id="txtGurantorAcNo" maxlength="#{LoanAccountsDetails.acNoMaxLen}" tabindex="2" disabled="#{LoanAccountsDetails.gFlag}" value="#{LoanAccountsDetails.oldGuarAcNo}" size="25" styleClass="input" rendered="#{LoanAccountsDetails.gFlag1==true}">
                                                    <a4j:support reRender="custView,message,errorMessage,a48,a59,a49,a50,a52,stxtGurantorAcNo,actulNetWorthId,txtGurantorNetWorth,txtGurantorRetAge,custViewClose" limitToList="true" event="onblur" action="#{LoanAccountsDetails.guarontorDetailIfCustomer}"oncomplete="if(#{LoanAccountsDetails.popupGuartFlag=='true'}) #{rich:component('custView')}.show()" focus="txtGurantorRetAge" />
                                                </h:inputText>
                                                <h:outputLabel id="stxtGurantorAcNo" styleClass="label" value="#{LoanAccountsDetails.guarAcNo}"style="color:green"></h:outputLabel>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a59" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblGurantorAddress" styleClass="label" value="Address :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtGurantorAddress" maxlength="60" tabindex="4" onblur="this.value = this.value.toUpperCase();" disabled="#{LoanAccountsDetails.guarFlag2}" value="#{LoanAccountsDetails.guarAddress}" size="25" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a49" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblGurantorPhoneNo" styleClass="label" value="Phone No. :"></h:outputLabel>
                                            <h:inputText id="txtGurantorPhoneNo" maxlength="20" tabindex="6" onblur="this.value = this.value.toUpperCase();" disabled="#{LoanAccountsDetails.guarFlag4}" value="#{LoanAccountsDetails.guarPhoneNo}" size="25" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a51" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblGurantorRetAge" styleClass="label" value="Retirement Age :"/>
                                            <h:inputText id="txtGurantorRetAge" maxlength="3" tabindex="8" disabled="#{LoanAccountsDetails.gFlag}" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarRetirementAge}" size="25" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a53" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblGurantorOfficePhoneNo" styleClass="label" value="Office Phone No. :"/>
                                            <h:inputText id="txtGurantorOfficePhoneNo" maxlength="20" disabled="#{LoanAccountsDetails.gFlag}" tabindex="10" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarOfficePhpneNo}" size="25" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a55" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblGurantorDesignation" styleClass="label" value="Designation :"/>
                                            <h:inputText id="txtGurantorDesignation" maxlength="20" disabled="#{LoanAccountsDetails.gFlag}" tabindex="12" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.guarDesignation}" size="25" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a57" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblGurantorNetWorth" styleClass="label" value="Net Worth :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:panelGroup id="guarId">
                                                <h:inputText id="txtGurantorNetWorth" maxlength="9" tabindex="14" disabled="#{LoanAccountsDetails.gFlag}" value="#{LoanAccountsDetails.guarNetWorth}" size="25" styleClass="input" >
                                                </h:inputText>
                                                <h:outputLabel id="actulNetWorthId" styleClass="label" value="#{LoanAccountsDetails.actulNetWorth}"style="color:green"></h:outputLabel>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table2" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanAccountsDetails.gridDetail2}" var="dataItem2"
                                                    rowClasses="gridrow1,gridrow2" id="taskList2" rows="2" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="16"><h:outputText value="Guarantier Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Guarantier A/c. No." /></rich:column>
                                                <rich:column><h:outputText value="Name" /></rich:column>
                                                <rich:column><h:outputText value="Father Name" /></rich:column>
                                                <rich:column><h:outputText value="Age" /></rich:column>
                                                <rich:column><h:outputText value="Retirement Age" /></rich:column>
                                                <rich:column><h:outputText value="Address" /></rich:column>
                                                <rich:column><h:outputText value="Ph No" /></rich:column>
                                                <rich:column><h:outputText value="Occupation" /></rich:column>
                                                <rich:column><h:outputText value="Firm Name" /></rich:column>
                                                <rich:column><h:outputText value="Firm Address" /></rich:column>
                                                <rich:column><h:outputText value="Firm Ph No" /></rich:column>
                                                <rich:column><h:outputText value="Designation" /></rich:column>
                                                <rich:column><h:outputText value="Net Worth" /></rich:column>
                                                <rich:column><h:outputText value="Cust Flag(Y/N)" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem2.guarAcNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarFatherName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarAge}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarRetAge}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarAddress}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarPhoneNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarOccupation}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarFirmName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarfirmAdd}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarFirmPhoneNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarDesignation}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarNetWorth}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem2.guarCustFlag}" /></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink2" oncomplete="#{rich:component('deletePanel2')}.show()" reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2,table2">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem2}" target="#{LoanAccountsDetails.currentItem2}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow2}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" action="#{LoanAccountsDetails.fillValuesofGridInFields}"
                                                             reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem2}" target="#{LoanAccountsDetails.currentItem2}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow2}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList2" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter3" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave3" tabindex="15"  value="Save" oncomplete="#{rich:component('savePanel3')}.show()" reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh3" tabindex="16" value="Refresh" action="#{LoanAccountsDetails.resetGuarantorDetails}"  reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit3" tabindex="17" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}"></a4j:commandButton>
                                    <%--a4j:commandButton id="btnExit33" tabindex="17" value="Exit" style="display:#{LoanAccountsDetails.memberStyle}"reRender="message,errorMessage,networthView,a42" action="#{LoanAccountsDetails.networthCheck}" oncomplete="if(#{LoanAccountsDetails.popupGuartFlag=='true'}) #{rich:component('networthView')}.show()"></a4j:commandButton--%>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab label="Insurance Details" switchType="client" ontabenter="#{rich:element('txtAssetDescription')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a60" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel11" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a62" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo4" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo4" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a64" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblAssetDescription" styleClass="label" value="Assets Description :"><font class="required" color="red">*</font></h:outputLabel>
                                        <%--
                                        <h:inputText id="txtAssetDescription" maxlength="100" tabindex="1" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.assetDesc}" style="width :120px" styleClass="input" />
                                        --%>
                                        <h:selectOneListbox id="txtAssetDescription" tabindex="3" styleClass="ddlist"  value="#{LoanAccountsDetails.assetDesc}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.assetDescOption}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>                                    
                                    <h:panelGrid columnClasses="col9" columns="2" id="a66" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblInsuranceCmpny" styleClass="label" value="Name Of Company :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddInsuranceCmpny" tabindex="3" styleClass="ddlist"  value="#{LoanAccountsDetails.insuranceCmpnyName}" size="1" style="width :120px">
                                            <f:selectItems value="#{LoanAccountsDetails.insuranceCmpnyNameList}" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a68" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblInsuranceCoverNoteNo" styleClass="label" value="Cover Note No. :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtInsuranceCoverNoteNo" maxlength="25" tabindex="5" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.coverNoteNo}" style="width :120px" styleClass="input" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a70" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblInsuranceIssueDt" styleClass="label" value="Issue Date :"><font class="required" color="red">*</font></h:outputLabel>
                                        <rich:calendar datePattern="dd/MM/yyyy" id="calInsuranceIssueDt" value="#{LoanAccountsDetails.insIssueDt}" tabindex="7" inputSize="12" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a71" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblInsuranceExpDt" styleClass="label" value="Expiry Date :"><font class="required" color="red">*</font></h:outputLabel>
                                        <rich:calendar datePattern="dd/MM/yyyy" id="calInsuranceExpDt" value="#{LoanAccountsDetails.insExpDt}" tabindex="9" inputSize="12"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a61" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel12" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a63" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName4" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName4" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a65" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblAssetValue" styleClass="label" value="Assets Value :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtAssetValue" tabindex="2" maxlength="15" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.assetValue}" style="width :120px" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a67" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblTypeOfInsurance" styleClass="label" value="Type Of Insurance :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddTypeOfInsurance" tabindex="4" styleClass="ddlist"  value="#{LoanAccountsDetails.insuranceType}" size="1" style="width :120px">
                                                <f:selectItems value="#{LoanAccountsDetails.insuranceTypeList}" />
                                            </h:selectOneListbox>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a69" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblInsuranceStatus" styleClass="label" value="Status :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddInsuranceStatus" tabindex="6" styleClass="ddlist"  value="#{LoanAccountsDetails.insurancceStatus}" size="1" style="width :120px">
                                                <f:selectItems value="#{LoanAccountsDetails.insurancceStatusList}" />
                                            </h:selectOneListbox>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a72" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblInsuranceAmt" styleClass="label" value="Insurance/Premium Amount Paid :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtInsuranceAmt" maxlength="15" tabindex="8" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.insuranceAmt}" style="width :120px" styleClass="input" />
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col9" columns="2" id="a73" style="height:30px;" styleClass="row2" width="100%">
                                            <h:outputLabel id="lblInsuranceDetails" styleClass="label" value="Insurance Details :"><font class="required" color="red">*</font></h:outputLabel>
                                            <h:inputText id="txtInsuranceDetails" maxlength="100" tabindex="10" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.insuranceDetails}" style="width :120px" styleClass="input" />
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table3" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanAccountsDetails.gridDetail3}" var="dataItem3"
                                                    rowClasses="gridrow1,gridrow2" id="taskList3" rows="2" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="16"><h:outputText value="Insurance Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                                <rich:column><h:outputText value="Asset Desc." /></rich:column>
                                                <rich:column><h:outputText value="Asset value" /></rich:column>
                                                <rich:column><h:outputText value="Name Of Ins. Company" /></rich:column>
                                                <rich:column><h:outputText value="Type Of Insurance" /></rich:column>
                                                <rich:column><h:outputText value="Cover Note No." /></rich:column>
                                                <rich:column><h:outputText value="Insurance/Premium Amount Paid" /></rich:column>
                                                <rich:column><h:outputText value="Expiry Date" /></rich:column>
                                                <rich:column><h:outputText value="Issue Date" /></rich:column>
                                                <rich:column><h:outputText value="Particulars" /></rich:column>
                                                <rich:column><h:outputText value="Status" /></rich:column>
                                                <rich:column><h:outputText value="Entered By" /></rich:column>
                                                <rich:column><h:outputText value="Entry Date" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem3.sNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.assetDesc}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.assetVal}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.insComName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.insType}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.coverNoteNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.premiumPaid}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.toDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.fromDt}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.particulars}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.status}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.enterBy}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem3.enteryDate}" /></rich:column>

                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" action="#{LoanAccountsDetails.fillValuesofGridInFieldsInInsurance}"
                                                             reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem3}" target="#{LoanAccountsDetails.currentItem3}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow3}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList3" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter4" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave4" tabindex="11"  value="Save" oncomplete="#{rich:component('savePanel4')}.show();" rendered="#{LoanAccountsDetails.insFlag==true}" reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnUpdate" tabindex="12"  value="Update" oncomplete="#{rich:component('updatePanel')}.show();" rendered="#{LoanAccountsDetails.insFlag==false}" reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh4" tabindex="13" value="Refresh" action="#{LoanAccountsDetails.resetInsuranceDetailForm}"  reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit4" tabindex="14" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>

                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab label="Company Details Register" switchType="client" ontabenter="#{rich:element('txtCmpnyName')}.focus();">
                            <h:panelGrid columnClasses="col9" columns="2" id="a74" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel13" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a76" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblAcNo5" styleClass="label" value="A/C. No. :"/>
                                        <h:outputText id="stxtAcNo5" styleClass="output" value="#{LoanAccountsDetails.acNo}"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a75" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel14" width="100%">
                                        <h:panelGrid columnClasses="col9" columns="2" id="a77" style="height:30px;" styleClass="row1" width="100%">
                                            <h:outputLabel id="lblName5" styleClass="label" value="Name :"/>
                                            <h:outputText id="stxtName5" styleClass="output" value="#{LoanAccountsDetails.partyName}"/>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid id="a81" columns="1" style="width:100%;height:30px;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblCmpnyDeails" styleClass="label" value="Company Details :-" style="font-size:15px;"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a78" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="lblCmpnyName" styleClass="label" value="Company Name :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCmpnyName" maxlength="40" tabindex="1"  value="#{LoanAccountsDetails.cmpnyName}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblRegOffice" styleClass="label" value="Registered Office :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtRegOffice" maxlength="60" tabindex="2"  value="#{LoanAccountsDetails.regOffice}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblLocation" styleClass="label" value="Location :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtLocation" maxlength="60" tabindex="3"  value="#{LoanAccountsDetails.regOfficeLocation}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a79" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="lblAuthCapital" styleClass="label" value="Authorised Capital :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAuthCapital" maxlength="15" tabindex="4"  value="#{LoanAccountsDetails.authCapital}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblSubCapital" styleClass="label" value="Subscribed Capital :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtSubCapital" maxlength="15" tabindex="5"  value="#{LoanAccountsDetails.suscribedCapital}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblComnetWorth" styleClass="label" value="Net Worth :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtComnetWorth" maxlength="15" tabindex="6"  value="#{LoanAccountsDetails.netWorth}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a80" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="lblIncDate" styleClass="label" value="Incorporation Date :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calIncDate" value="#{LoanAccountsDetails.incDate}" tabindex="7" inputSize="13"/>
                                <h:outputLabel id="lblDirName1" styleClass="label" value="Director/Partner Name1 :" style=""><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtDirName1" maxlength="30" tabindex="8"  value="#{LoanAccountsDetails.dirName1}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblDirName2" styleClass="label" value="Director/Partner Name2 :" style=""/>
                                <h:inputText id="txtDirName2" maxlength="30" tabindex="9"  value="#{LoanAccountsDetails.dirName2}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a88" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="lblDirName3" styleClass="label" value="Director/Partner Name3 :" style=""/>
                                <h:inputText id="txtDirName3" maxlength="30" tabindex="10"  value="#{LoanAccountsDetails.dirName3}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblDirName4" styleClass="label" value="Director/Partner Name4 :" style=""/>
                                <h:inputText id="txtDirName4" maxlength="30" tabindex="11"  value="#{LoanAccountsDetails.dirName4}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblDirName5" styleClass="label" value="Director/Partner Name5 :" style=""/>
                                <h:inputText id="txtDirName5" maxlength="30" tabindex="12"  value="#{LoanAccountsDetails.dirName5}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a89" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="lblDirName6" styleClass="label" value="Director/Partner Name6 :" style=""/>
                                <h:inputText id="txtDirName6" maxlength="30" tabindex="13"  value="#{LoanAccountsDetails.dirName6}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:outputLabel id="lblDirName7" styleClass="label" value="Director/Partner Name7 :" style=""/>
                                <h:inputText id="txtDirName7" maxlength="30" tabindex="14"  value="#{LoanAccountsDetails.dirName7}" onblur="this.value = this.value.toUpperCase();" styleClass="input" style="width:120px"/>
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave5" tabindex="15"  value="Save" action="#{LoanAccountsDetails.saveCompanyDetails}" rendered="#{LoanAccountsDetails.comFlag==true}" reRender="message,errorMessage,gpFooter4,a78,a79,a80,a88,a89,table4,taskList4" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnUpdateCM" tabindex="16"  value="Update" action="#{LoanAccountsDetails.updateCompanyDetail}" rendered="#{LoanAccountsDetails.comFlag==false}" reRender="message,errorMessage,gpFooter4,a78,a79,a80,a88,a89,table4,taskList4" >
                                    </a4j:commandButton>
                                </h:panelGroup>
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnRefresh5" tabindex="17" value="Refresh" action="#{LoanAccountsDetails.resetCompanyDetails}"  reRender="message,errorMessage,gpFooter4,a78,a79,a80,a88,a89,table4,taskList4" >
                                    </a4j:commandButton>

                                    <a4j:commandButton id="btnExit5" tabindex="18"  value="Exit" reRender="message,errorMessage,gpFooter4,a78,a79,a80,a88,a89,table4,taskList4" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>
                                </h:panelGroup>



                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table4" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanAccountsDetails.gridDetail4}" var="dataItem4"
                                                    rowClasses="gridrow1,gridrow2" id="taskList4" rows="2" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="20"><h:outputText value="Company Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Company Name" /></rich:column>
                                                <rich:column><h:outputText value="Regd. Office" /></rich:column>
                                                <rich:column><h:outputText value="Location" /></rich:column>
                                                <rich:column><h:outputText value="Authorised Capital" /></rich:column>
                                                <rich:column><h:outputText value="Subscribed Capital" /></rich:column>
                                                <rich:column><h:outputText value="Net Worth" /></rich:column>
                                                <rich:column><h:outputText value="Incorp. Date" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 1" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 2" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 3" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 4" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 5" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 6" /></rich:column>
                                                <rich:column><h:outputText value="Dir. Name 7" /></rich:column>
                                                <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem4.cmpnyName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.regOffice}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.location}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.authCapital}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.subCapital}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.networth}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.incorpDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname1}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname2}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname3}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname4}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname5}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname6}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem4.dirname7}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" action="#{LoanAccountsDetails.setValuesOfGridInFieldsInCmpnyDetails}"
                                                             reRender="message,errorMessage,gpFooter4,a78,a79,a80,a88,a89,table4,taskList4" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem4}" target="#{LoanAccountsDetails.currentItem4}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow4}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList4" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid id="a82" columns="1" style="width:100%;height:30px;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblGroupCmpnyDeails" styleClass="label" value="Group Company Details :-" style="font-size:15px;"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a83" width="100%">
                                <h:panelGrid  columns="1" id="gridPanel15" width="100%">
                                    <h:panelGrid columnClasses="col9" columns="2" id="a84" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblGrCmpnyName" styleClass="label" value="Company Name :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtGrCmpnyName" maxlength="50" tabindex="19" size="20" value="#{LoanAccountsDetails.grCompName}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a85" style="height:30px;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblGrCmpnyAdd" styleClass="label" value="Company Address :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtGrCmpnyAdd" maxlength="45" tabindex="20" size="20" value="#{LoanAccountsDetails.grCompAdd}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col9" columns="2" id="a86" style="height:30px;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblGrCmpnyPhNo" styleClass="label" value="Phone No. :"><font class="required" color="red">*</font></h:outputLabel>
                                        <h:inputText id="txtGrCmpnyPhNo" maxlength="20" tabindex="21" size="20" value="#{LoanAccountsDetails.grCompPhNo}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="2" id="a87" width="100%">
                                    <h:panelGrid columns="1" id="gridPanel16" width="100%">
                                        <h:panelGrid columnClasses="vtop" columns="1" id="table5" style="height:auto;" styleClass="row2" width="100%">
                                            <a4j:region>
                                                <rich:dataTable value="#{LoanAccountsDetails.gridDetail5}" var="dataItem5"
                                                                rowClasses="gridrow1,gridrow2" id="taskList5" rows="2" columnsWidth="100" rowKeyVar="row"
                                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                    <f:facet name="header">
                                                        <rich:columnGroup>

                                                            <rich:column breakBefore="true"><h:outputText value="Name" /></rich:column>
                                                            <rich:column><h:outputText value="Address" /></rich:column>
                                                            <rich:column><h:outputText value="Phone No." /></rich:column>
                                                            <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                                        </rich:columnGroup>
                                                    </f:facet>
                                                    <rich:column><h:outputText value="#{dataItem5.name}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem5.address}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem5.phNo}" /></rich:column>
                                                    <rich:column>
                                                        <a4j:commandLink ajaxSingle="true" id="deletelink3" oncomplete="#{rich:component('deletePanel3')}.show()" reRender="message,errorMessage,gpFooter5,a84,a85,a86,table5,taskList5">
                                                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                            <f:setPropertyActionListener value="#{dataItem5}" target="#{LoanAccountsDetails.currentItem5}"/>
                                                            <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRow5}" />
                                                        </a4j:commandLink>
                                                    </rich:column>
                                                </rich:dataTable>
                                                <rich:datascroller align="left" for="taskList5" maxPages="20" />
                                            </a4j:region>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter5" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave6" tabindex="23"  value="Save" action="#{LoanAccountsDetails.saveGroupCompDetail}" reRender="message,errorMessage,gpFooter5,a84,a85,a86,table5,taskList5" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh6" tabindex="24" value="Refresh" action="#{LoanAccountsDetails.resetGroupCompFields}"  reRender="message,errorMessage,gpFooter5,a84,a85,a86,table5,taskList5" >
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit6" tabindex="25" value="Exit" reRender="message,errorMessage" action="#{LoanAccountsDetails.exitForm}">
                                    </a4j:commandButton>

                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <%-- THIS CODE IS FOR SECURITY CODE TAB--%>
                        <rich:tab label="Security Detail"  id = "tabSecDetail" switchType="client" title="Security Detail" ontabenter="#{rich:element('MarkSecurities')}.focus();">
                            <h:panelGrid id="leftPanelSec" style="width:100%;text-align:center;">
                                <%--    <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                                        <h:outputLabel id="lblMsg" styleClass="error" value="#{LoanAccountsDetails.message}"/>
                                    </h:panelGrid> --%>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow1" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblNameSec" styleClass="label" value="Name"/>
                                    <h:outputText id="stxtNameSec" styleClass="output" value="#{LoanAccountsDetails.name}"/>
                                    <h:outputLabel id="lblAccount" styleClass="label" value="Account"/>
                                    <h:outputText id="stxtAccount" styleClass="output" value="#{LoanAccountsDetails.acno1}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="2" id="MarkSecuritiesRow" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblMarkSecurities" styleClass="label" value="Mark Securities : " style="color:purple;padding-left:200px;font-size:15px;"/>
                                    <h:selectOneRadio id="MarkSecurities" styleClass="label"  value="#{LoanAccountsDetails.markSecurities}" style="width:80%;text-align:left;padding-right:200px;color:purple" >
                                        <f:selectItems value="#{LoanAccountsDetails.marksSecuritiesList}"/>
                                        <a4j:support   ajaxSingle="true" focus="ddTypeOfSecurity" event="onclick" action="#{LoanAccountsDetails.MarkSecurity}"reRender="secRow2,secRow3,lienMarkingPanel,accountStatus,btnSaveSD,outerPanel,leftPanelSec"
                                                       oncomplete="if(#{LoanAccountsDetails.markSecurities == '3'})#{rich:component('lienMarkingPanel')}.hide();
                                                       else if(#{LoanAccountsDetails.markSecurities == '2'})#{rich:component('lienMarkingPanel')}.show();
                                                       else if(#{LoanAccountsDetails.markSecurities == '1'})#{rich:component('accountStatus')}.show();"/>
                                    </h:selectOneRadio>
                                </h:panelGrid >


                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow2" style="width:100%;text-align:left;" styleClass="row2" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security "><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist" style="width:134px"  size="1" value="#{LoanAccountsDetails.securityType}">
                                        <f:selectItems value="#{LoanAccountsDetails.typeOfSecurityList}"/>
                                        <a4j:support  ajaxSingle="true" action="#{LoanAccountsDetails.changeLabel}" event="onblur" focus="#{rich:clientId('calEstimationDate')}InputDate"
                                                      reRender="message,errorMessage,lblEstimationDate,calEstimationDate,lblRevalutionDate,lblValuationAmt,ddSecurityDesc1,ddSecurityDesc2,ddSecurityDesc3"/>

                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblEstimationDate" styleClass="label" value="#{LoanAccountsDetails.estimationDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calEstimationDate" value="#{LoanAccountsDetails.estimationDate}">
                                        <a4j:support ajaxSingle="true" event="changed" focus="ddSecurityNature"/>
                                    </rich:calendar>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow3" style="width:100%;text-align:left;" styleClass="row1" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblSecurityNature" styleClass="label" value="Security Nature"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityNature" styleClass="ddlist"  style="width:134px" size="1" value="#{LoanAccountsDetails.securityNature}">
                                        <f:selectItems value="#{LoanAccountsDetails.securityNatureList}"/>
                                        <a4j:support ajaxSingle="true" focus="#{rich:clientId('calRevalutionDate')}InputDate"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblRevalutionDate" styleClass="label" value="#{LoanAccountsDetails.revalutionDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calRevalutionDate" value="#{LoanAccountsDetails.revalutionDate}">
                                        <a4j:support ajaxSingle="true" focus="ddSecurityDesc1"/>
                                    </rich:calendar>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow4" style="width:100%;text-align:left;" styleClass="row2" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblSecurityDesc1" styleClass="label" value="Security Desc (1)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityDesc1" styleClass="ddlist"  size="1" style="width:134px" value="#{LoanAccountsDetails.securityDesc1}">
                                        <f:selectItems value="#{LoanAccountsDetails.securityDesc1List}"/>
                                        <a4j:support  ajaxSingle="true" action="#{LoanAccountsDetails.onChangeOFSecurityDesc1}" event="onblur"
                                                      reRender="message,errorMessage,ddSecurityDesc2,ddSecurityDesc3" focus="txtParticular"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblParticular" styleClass="label" value="Particular"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtParticular" styleClass="input" maxlength = "255" onblur="this.value = this.value.toUpperCase();"
                                                 value="#{LoanAccountsDetails.particular}">
                                        <a4j:support  ajaxSingle="true" action="#{LoanAccountsDetails.setRemarksSec}" event="onchange"  reRender="message,errorMessage,txtRemarksSec"/>
                                    </h:inputText>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col9,col2,col7" columns="3" id="secRow5" style="width:100%;text-align:left;" styleClass="row1" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:panelGrid columnClasses="col2,col7" columns="2" id="secRow115" style="width:100%;text-align:left;" styleClass="row1" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                        <h:outputLabel id="lblSecurityDesc2" styleClass="label" value="Security Desc (2)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                            <h:panelGrid columnClasses="col5,col2,col1" columns="3" id="secRow116" style="width:100%;text-align:left;" styleClass="row1" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                                <h:selectOneListbox id="ddSecurityDesc2" styleClass="ddlist"  size="1" style="width:134px" value="#{LoanAccountsDetails.securityDesc2}">
                                                    <f:selectItems value="#{LoanAccountsDetails.securityDesc2List}"/>
                                                    <a4j:support  action="#{LoanAccountsDetails.onChangeOFSecurityDesc2}" event="onblur" 
                                                                  reRender="message,errorMessage,ddSecurityDesc3,stxtNameSecODScheme,stxtNameSecODRoi,txtMargin" focus="txtValuationAmt"/>
                                                </h:selectOneListbox>
                                                <h:outputText id="stxtNameSecODScheme" styleClass="output" value="#{LoanAccountsDetails.secODScheme}"/>
                                                <h:outputText id="stxtNameSecODRoi" styleClass="output" value="#{LoanAccountsDetails.secODRoi}"/>
                                            </h:panelGrid>
                                    </h:panelGrid>
                                    <h:outputLabel id="lblValuationAmt" styleClass="label" value="#{LoanAccountsDetails.valuationAmtLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtValuationAmt" styleClass="input" value="#{LoanAccountsDetails.valuationAmt}">
                                        <a4j:support ajaxSingle="true"  oncomplete="if(#{DlAccountOpeningRegister.securityflag=='true'}){#{rich:element('ddSecurityDesc3')}.focus();}else{#{rich:element('txtLienValue')}.focus();}"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow6" style="width:100%;text-align:left;" styleClass="row2" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblSecurityDesc3" styleClass="label"  value="Nature Of Charges"/>
                                    <h:selectOneListbox id="ddSecurityDesc3" styleClass="ddlist"  disabled="#{LoanAccountsDetails.disableSecDec3}"  size="1" style="width:134px" value="#{LoanAccountsDetails.securityDesc3}">
                                        <f:selectItems value="#{LoanAccountsDetails.securityDesc3List}"/>
                                        <a4j:support ajaxSingle="true" focus="txtLienValue"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblLienValue" styleClass="label" value="Lien Value "><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtLienValue" styleClass="input" value="#{LoanAccountsDetails.lienValue}">
                                        <a4j:support ajaxSingle="true" focus="ddStatus"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow7" style="width:100%;text-align:left;" styleClass="row1" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddStatus" styleClass="ddlist"  style="width:134px" size="1" value="#{LoanAccountsDetails.status1}" >
                                        <f:selectItems value="#{LoanAccountsDetails.statusList}"/>
                                        <a4j:support ajaxSingle="true" focus="txtMargin"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblMargin" styleClass="label" value="Margin(%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtMargin" styleClass="input" value="#{LoanAccountsDetails.margin}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="secRow8" style="width:100%;text-align:left;" styleClass="row2" rendered="#{LoanAccountsDetails.markSecurities == '3'}">
                                    <h:outputLabel id="lblOtherAC" styleClass="label" value="Other A/C "/>
                                    <h:inputText id="txtOtherAC" styleClass="input" onblur="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.otherAc}"/>
                                    <h:outputLabel id="lblRemarksSec" styleClass="label" value="Remarks "/>
                                    <h:inputText id="txtRemarksSec" styleClass="input" maxlength = "255" onchange="this.value = this.value.toUpperCase();" value="#{LoanAccountsDetails.remark}">
                                        <a4j:support ajaxSingle="true" event="onblur" action="#{LoanAccountsDetails.saveEnableSEC}" reRender="btnSaveSD,poFooterPanel" focus= "btnSaveSD"/>
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col1,col2" columns="1" id="SecurityDetails"  width="100%" styleClass="row2">
                                <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete=" #{rich:component('deletePanel')}.show()"

                                                   actionListener="#{LoanAccountsDetails.fetchCurrentRowSEC}">
                                        <a4j:actionparam name="Maturity Value" value="{Maturity Value}" />
                                        <a4j:actionparam name="row" value="{currentRowSD}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value ="#{LoanAccountsDetails.securityDetail}"
                                                    rowClasses="row1, row2" id = "SecurityDetailsTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>

                                                <rich:column colspan="23"></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                                <rich:column><h:outputText value="Maturity/Lien Value" /></rich:column>
                                                <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                <rich:column><h:outputText value="Issue Date" /></rich:column>
                                                <rich:column><h:outputText value="Value at Lien Time" /></rich:column>
                                                <rich:column><h:outputText value="Type" /></rich:column>
                                                <rich:column><h:outputText value="LastSTM Dt" /></rich:column>
                                                <rich:column><h:outputText value="DP Limit" /></rich:column>
                                                <rich:column><h:outputText value="STM Date" /></rich:column>
                                                <rich:column><h:outputText value="NextSTM Dt" /></rich:column>
                                                <rich:column><h:outputText value="STM Frequency" /></rich:column>
                                                <rich:column><h:outputText value="Remark" /></rich:column>
                                                <rich:column><h:outputText value="Status" /></rich:column>
                                                <rich:column><h:outputText value="Entered By" /></rich:column>
                                                <rich:column><h:outputText value="Entered Date" /></rich:column>
                                                <rich:column><h:outputText value="Auth By" /></rich:column>
                                                <rich:column><h:outputText value="Particulars" /></rich:column>
                                                <rich:column><h:outputText value="Int Table Code" /></rich:column>
                                                <rich:column><h:outputText value="Deposit ROI" /></rich:column>
                                                <rich:column><h:outputText value="Margine ROI" /></rich:column>
                                                <rich:column><h:outputText value="Additional ROI" /></rich:column>
                                                <rich:column><h:outputText value="Applicable ROI" /></rich:column>                                              
                                                <rich:column><h:outputText value="Delete" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.matValue}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.matDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.issueDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.lienValue}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.type}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.ltSTMDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.DPLimit}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.STMDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.nxSTMDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.STMFrequency}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.remark}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.status}"/>
                                            <a4j:support oncomplete="#{rich:component('selectPanel')}.show()" ajaxSingle="true" event="ondblclick">
                                                <f:setPropertyActionListener value="#{item}" target="#{LoanAccountsDetails.currentItemSD}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRowSD}" />
                                            </a4j:support>

                                        </rich:column>
                                        <rich:column><h:outputText value="#{item.enteredBy}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.enteredDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.authBy}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.particular}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.intTable}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.depositRoi}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.margineRoi}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.addRoi}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.applicableRoi}"/></rich:column>
                                        <rich:column>
                                            <%--<a4j:commandLink ajaxSingle="true" id="Updatelink" oncomplete="#{rich:component('UpdatePanel')}.show()">
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{item}" target="#{LoanAccountsDetails.currentItemSD}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRowSD}" />
                                            </a4j:commandLink>--%>

                                            <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanelSec')}.show()">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0"/>
                                                <f:setPropertyActionListener value="#{item}" target="#{LoanAccountsDetails.currentItemSD}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRowSD}" />
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>

                                </a4j:region>
                                <rich:datascroller align="left" for="SecurityDetailsTable" maxPages="40" />
                            </h:panelGrid>
                            <rich:modalPanel id="UpdatePanel" autosized="true" width="200" onshow="#{rich:element('btnYesSecUp')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure To Edit The Security Details Of SNo  ?" style="padding-right:15px;" />
                                </f:facet>

                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesSecUp" ajaxSingle="true" action="#{LoanAccountsDetails.Updateselect}"
                                                                       onclick="#{rich:component('UpdatePanel')}.hide();" reRender="leftPanelSec,ddTypeOfSecurity,ddStatus,btnUpdateSD,btnSaveSD,message,errorMessage" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" id="btnNoSecUp" onclick="#{rich:component('UpdatePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <rich:modalPanel id="selectPanel" autosized="true" width="200" onshow="#{rich:element('btnYesSelectSec')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Do You Want To Edit Status As EXPIRED ?" style="padding-right:15px;" />
                                </f:facet>

                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesSelectSec" ajaxSingle="true" action="#{LoanAccountsDetails.select}"
                                                                       onclick="#{rich:component('selectPanel')}.hide();" reRender="SecurityDetails,message,errorMessage,leftPanelSec" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" id="btnNoSelectSec" onclick="#{rich:component('selectPanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <rich:modalPanel id="deletePanelSec" autosized="true" width="200" onshow="#{rich:element('btnYesDelSec')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure To Delete The Security Details ?" style="padding-right:15px;" />
                                </f:facet>
                                <f:facet name="controls">
                                    <h:panelGroup>
                                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                                        <rich:componentControl for="deletePanelSec" attachTo="hidelink2" operation="hide" event="onclick"/>
                                    </h:panelGroup>
                                </f:facet>
                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesDelSec" ajaxSingle="true" action="#{LoanAccountsDetails.deleteSEC}"
                                                                       onclick="#{rich:component('deletePanelSec')}.hide();" reRender="SecurityDetails,message,errorMessage" />
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" id="btnNoDelSec" onclick="#{rich:component('deletePanelSec')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>
                            <%--THIS IS TD LIEN MARKING FORM JSP CODE--%>
                            <rich:modalPanel id="lienMarkingPanel" top="true" height="600" width="1000" onshow="#{rich:element('txtAcNo1')}.focus();">
                                <h:form>
                                    <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                                        <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                                            <h:panelGroup id="a2" layout="block">
                                                <h:outputLabel styleClass="headerLabel" value="Date: "/>
                                                <h:outputText id="stxtDate" styleClass="headerLabel" value="#{LoanAccountsDetails.todayDate}"/>
                                            </h:panelGroup>
                                            <h:outputLabel styleClass="headerLabel" value="Term Deposite Lien Marking"/>
                                            <h:panelGroup layout="block">
                                                <h:outputLabel styleClass="headerLabel" value="User: "/>
                                                <h:outputText id="stxtUser" styleClass="headerLabel" value="#{LoanAccountsDetails.userName}"/>
                                            </h:panelGroup>
                                        </h:panelGrid>
                                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                                            <h:outputText id="errorMessageLM" styleClass="error" value="#{LoanAccountsDetails.errorMessageLM}"/>
                                            <h:outputText id="messageLM" styleClass="msg" value="#{LoanAccountsDetails.messageLM}"/>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                                            <h:panelGroup layout="Acnoblock">
                                                <h:inputText id="txtAcNo1" tabindex="1" size="20" maxlength="#{LoanAccountsDetails.acNoMaxLen}" value="#{LoanAccountsDetails.oldAcctNoLM}" styleClass="input">
                                                    <a4j:support action="#{LoanAccountsDetails.customerDetail}" ajaxSingle="true" event="onblur" focus="ddTypeOfSecurity"
                                                                 reRender="messageLM,errorMessageLM,a3,a4,a5,a6,gpFooter,table,taskList" limitToList="true" />
                                                </h:inputText>
                                                <h:outputLabel id="stxtlmAcNo" styleClass="label" value="#{LoanAccountsDetails.acctNoLM}" ></h:outputLabel>
                                            </h:panelGroup>                                        
                                            <h:outputLabel id="lblHide3" value="" />
                                            <h:outputLabel id="lblHide4" value="" />
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">

                                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                                            <h:outputText id="stxtCustName" styleClass="output" value="#{LoanAccountsDetails.custName}" style="color:purple;"/>
                                            <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name :" />
                                            <h:outputText id="stxtJtName" styleClass="output" value="#{LoanAccountsDetails.jtName}" style="color:purple;"/>
                                            <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" />
                                            <h:outputText id="stxtOprMode" styleClass="output" value="#{LoanAccountsDetails.oprMode}" style="color:purple;"/>

                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="securityType" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security :" ><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist"  style="width:134px" size="1" value="#{LoanAccountsDetails.typeOfSecurity}" >
                                                <f:selectItems value="#{LoanAccountsDetails.securityNatureList}"/>
                                                <a4j:support ajaxSingle="true" event="onblur"/>
                                            </h:selectOneListbox>
                                            <h:outputLabel id="lblTypeOfSecurity1" styleClass="label" />
                                            <h:outputLabel id="lblTypeOfSecurity2" styleClass="label" />
                                            <h:outputLabel id="lblTypeOfSecurity3" styleClass="label" />
                                            <h:outputLabel id="lblTypeOfSecurity4" styleClass="label" />

                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                            <a4j:region>
                                                <rich:dataTable value="#{LoanAccountsDetails.gridDetailLM}" var="dataItem"
                                                                rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                    <f:facet name="header">
                                                        <rich:columnGroup>
                                                            <rich:column colspan="16"><h:outputText value="Details" /></rich:column>
                                                            <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                                            <rich:column><h:outputText value="R.T. No." /></rich:column>
                                                            <rich:column><h:outputText value="Reciept No." /></rich:column>
                                                            <rich:column><h:outputText value="Control No." /></rich:column>
                                                            <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                                            <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                                            <rich:column><h:outputText value="TD Date (wef)" /></rich:column>
                                                            <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                            <rich:column><h:outputText value="Int Opt" /></rich:column>
                                                            <rich:column><h:outputText value="Int Table Code" /></rich:column>
                                                            <rich:column><h:outputText value="ROI" /></rich:column>
                                                            <rich:column><h:outputText value="Margine ROI" /></rich:column>
                                                            <rich:column><h:outputText value="Additional ROI" /></rich:column>
                                                            <rich:column><h:outputText value="Applicable ROI" /></rich:column>
                                                            <rich:column visible="false"><h:outputText value="Status" /></rich:column>
                                                            <rich:column><h:outputText value="Lien Mark" /></rich:column>
                                                            <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                                        </rich:columnGroup>
                                                    </f:facet>
                                                    <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.reciept}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.printAmt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.fddt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.tdmatDt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.matDt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.intTable}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.margineRoi}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.addRoi}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.applicableRoi}" /></rich:column>
                                                    <rich:column visible="false"><h:outputText value="#{dataItem.status}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.lien}" /></rich:column>     
                                                    <rich:column style="text-align:center;width:40px">
                                                        <a4j:commandLink id="selectlink" ajaxSingle="true" action="#{LoanAccountsDetails.fillValuesofGridInFieldsLM}"
                                                                         reRender="a5,a6,messageLM,errorMessageLM,gpFooter,table,taskList" >
                                                            <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                            <f:setPropertyActionListener value="#{dataItem}" target="#{LoanAccountsDetails.currentItemLM}"/>
                                                            <f:setPropertyActionListener value="#{row}" target="#{LoanAccountsDetails.currentRowLM}"/>
                                                        </a4j:commandLink> 

                                                    </rich:column>

                                                </rich:dataTable>
                                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                                            </a4j:region>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblRecieptNo" styleClass="label" value="Reciept No. :" />
                                            <h:outputText id="stxtRecieptNo" styleClass="output" value="#{LoanAccountsDetails.recieptNo}" style="color:purple;"/>
                                            <h:outputLabel id="lblControlNo" styleClass="label" value="Control No. :" />
                                            <h:outputText id="stxtControlNo" styleClass="output" value="#{LoanAccountsDetails.controlNo}" style="color:purple;"/>
                                            <h:outputLabel id="lblPrinAmt" styleClass="label" value="Present Amount :" />
                                            <h:outputText id="stxtPrinAmt" styleClass="output" value="#{LoanAccountsDetails.prinAmount}" style="color:purple;"/>
                                        </h:panelGrid>
                                        <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                            <h:outputLabel id="lblLienStatus" styleClass="label" value="Lien Status :" />
                                            <h:outputText id="stxtLienStatus" styleClass="output" value="#{LoanAccountsDetails.statusLien}" style="color:purple;"/>
                                            <h:outputLabel id="lblLienMarking" styleClass="label" value="Lien Marking :" ><font class="required" color="red">*</font></h:outputLabel>
                                            <h:selectOneListbox id="ddLienMarking" tabindex="3" styleClass="ddlist" value="#{LoanAccountsDetails.lienMarkOption}" size="1" style="width: 120px">
                                                <f:selectItems value="#{LoanAccountsDetails.lienMarkOptionList}" />
                                                <a4j:support  ajaxSingle="true" event="onblur" focus="txtDetails"/>
                                            </h:selectOneListbox>
                                            <h:outputLabel id="lblDetails" styleClass="label" value="Details :" />
                                            <h:inputText id="txtDetails" tabindex="4" size="20" value="#{LoanAccountsDetails.detail}" onblur="this.value = this.value.toUpperCase();" styleClass="input">
                                                <a4j:support  ajaxSingle="true" event="onblur"/>
                                            </h:inputText>
                                        </h:panelGrid>
                                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                                <a4j:commandButton id="btnSave" tabindex="17" value="Ok" disabled="#{LoanAccountsDetails.flag1LM}" oncomplete="#{rich:component('savePanelInLien')}.show()" reRender="messageLM,errorMessageLM,a3,a4,a5,a6,gpFooter,table,taskList" focus="">
                                                </a4j:commandButton>
                                                <a4j:commandButton id="btnRefresh" tabindex="19" value="Refresh" ajaxSingle="true" action="#{LoanAccountsDetails.resetForm}" oncomplete="#{rich:element('btnSave')}.disabled = true;" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" >
                                                </a4j:commandButton>
                                                <a4j:commandButton id="btnExit" tabindex="20" value="Exit" onclick="#{rich:component('exitPanel')}.show()" reRender="messageLM,errorMessageLM,savePanelInLien" >
                                                </a4j:commandButton>

                                            </h:panelGroup>
                                        </h:panelGrid>
                                    </h:panelGrid>


                                </h:form>
                                <rich:modalPanel id="savePanelInLien" autosized="true" width="200" onshow="#{rich:element('btnYesSaveLien')}.focus();">
                                    <f:facet name="header">
                                        <h:outputText value="Are You Sure To Select This Voucher No. ?" style="padding-right:15px;" />
                                    </f:facet>
                                    <h:form>
                                        <table width="100%">
                                            <tbody>
                                                <tr>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Yes" id="btnYesSaveLien" ajaxSingle="true" action="#{LoanAccountsDetails.saveDetailLM}"
                                                                           oncomplete="#{rich:component('savePanelInLien')}.hide();" reRender="messageLM,errorMessageLM,a3,a4,a5,a6,gpFooter,table,taskList,SecurityDetailsTable,txtMargin" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton value="Cancel" id="btnNoSaveLien" ajaxSingle="true" onclick="#{rich:component('savePanelInLien')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </rich:modalPanel>
                            <%--END OF TD LIEN MARKING FORM JSP CODE--%>
                            <%--START OF ACCOUNT STATUS FORM JSP CODE--%>
                            <rich:modalPanel id="accountStatus" top="true" height="550" width="1000" onshow="#{rich:element('txtAccountNumber')}.focus();">
                                <h:form>
                                    <a4j:form>
                                        <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                                            <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                                                <h:panelGroup id="groupPanel" layout="block" >
                                                    <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                                                    <h:outputText id="stxtDate" styleClass="output" value="#{LoanAccountsDetails.todayDate}"/>
                                                </h:panelGroup>
                                                <h:outputLabel id="lblincident" styleClass="headerLabel" value="Account Status"/>
                                                <h:panelGroup id="groupPanel2" layout="block">
                                                    <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                                                    <h:outputText id="stxtUser" styleClass="output" value="#{LoanAccountsDetails.userName}" />
                                                </h:panelGroup>
                                            </h:panelGrid>
                                            <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row1"  >
                                                <h:outputText id="stxtmessage" styleClass="output" value="#{LoanAccountsDetails.messageAcct}"  style="width:100%;color:red"/>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a9" width="100%">
                                                <h:panelGrid columns="1" id="accountType" style="width:100%;text-align:center;" styleClass="row2" width="100%"  >
                                                    <h:outputLabel id="lblAccountNumber" styleClass="headerLabel"  value="Account Number"  style="width:100%;text-align:left;"><font class="required" color="red">*</font></h:outputLabel>
                                                </h:panelGrid>

                                                <h:panelGrid columns="3" columnClasses="col7,col7" id="AccountNumber" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                        <%--  <h:selectOneListbox id="ddAccountNumber" styleClass="ddlist" size="1" style="width: 100px" value="#{LoanAccountsDetails.acctype}">
                                                              <f:selectItem itemValue="--Select--"/>
                                                              <f:selectItems value="#{LoanAccountsDetails.acctOption}"/>
                                                              <a4j:support ajaxSingle="true"  event="onblur"/>
                                                          </h:selectOneListbox>  --%>
                                                        <h:inputText id="txtAccountNumber" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" maxlength="#{LoanAccountsDetails.acNoMaxLen}"style="width: 90px" value="#{LoanAccountsDetails.oldCode}">
                                                            <a4j:support id="a4j2" ajaxSingle="true" event="onblur" limitToList="true" focus="ddnStatus"reRender="stxtAccountNumber,stxtmessage,txtName,txtpStatus,a19,taskList,btnSave,a91,d91,txtRemarksSec,ddnStatus" action="#{LoanAccountsDetails.custName}"/>
                                                        </h:inputText>
                                                        <h:outputLabel id="stxtAccountNumber" styleClass="headerLabel"  value="#{LoanAccountsDetails.code}"></h:outputLabel>
                                                        <%--   <h:inputText id="txtAccountNumber1" styleClass="input" style="width: 30px" value="#{LoanAccountsDetails.code1}">
                                                              <a4j:support ajaxSingle="true" event="onblur"/>
                                                          </h:inputText> --%>
                                                    </h:panelGroup>
                                                </h:panelGrid>
                                            </h:panelGrid>
                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="c9" width="100%">
                                                <h:panelGrid columns="1"  id="Name" style="width:100%;text-align:center;"  styleClass="row1" width="100%"  >
                                                    <h:outputLabel id="lblName" styleClass="headerLabel"  value="Name"  style="width:100%;text-align:left;"></h:outputLabel>
                                                </h:panelGrid>
                                                <h:panelGrid columns="1"  id="Name1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                                    <h:outputText id="txtName" styleClass="output" value="#{LoanAccountsDetails.nameAcct}" />
                                                </h:panelGrid>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a91" width="100%">
                                                <h:panelGrid columns="2" columnClasses="col7,col7" id="pStatus" style="height:30px;"  styleClass="row2" width="100%"  >
                                                    <h:outputLabel id="lblpStatus" styleClass="headerLabel"  value="Present Status"  style="width:100%;text-align:left;"> </h:outputLabel>
                                                    <h:outputText id="txtpStatus" styleClass="output" value="#{LoanAccountsDetails.pStatus}"/>
                                                </h:panelGrid>

                                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="nStatus"  style="height:30px;"  styleClass="row2" width="100%"  >
                                                    <h:outputLabel id="lblnStatus" styleClass="headerLabel"  value="New Status"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                        <h:selectOneListbox id="ddnStatus" styleClass="ddlist" size="1" style="width: 100px"   value="#{LoanAccountsDetails.nStatus}">
                                                            <f:selectItem itemValue="--Select--"/>
                                                            <f:selectItem itemValue="Lien Marked"/>
                                                            <a4j:support ajaxSingle="true" id="soppt" event="onchange" action="#{LoanAccountsDetails.save1}"  reRender="d91,stxtmessage" />
                                                        </h:selectOneListbox>
                                                    </h:panelGroup>
                                                </h:panelGrid>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="d91" width="100%">
                                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="lien" style="height:30px;"  styleClass="row2" rendered="#{LoanAccountsDetails.flagAcct=='true'}" width="100%"  >
                                                    <h:outputLabel id="lbllien" styleClass="headerLabel"  value="Lien Account No."  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                        <h:selectOneListbox id="ddlien" styleClass="ddlist" size="1" style="width: 100px"  disabled="#{LoanAccountsDetails.flagLienMark}" value="#{LoanAccountsDetails.lienacctype}">
                                                            <f:selectItem itemValue="--Select--"/>
                                                            <f:selectItems value="#{LoanAccountsDetails.lienacctOption}"/>
                                                            <a4j:support ajaxSingle="true"  event="onchange"/>
                                                        </h:selectOneListbox>
                                                        <h:inputText id="txtlienAccountNumber" styleClass="input" disabled="#{LoanAccountsDetails.flagLienMark}" style="width: 130px" value="#{LoanAccountsDetails.liencode}">
                                                            <a4j:support ajaxSingle="true"  event="onchange"/>
                                                        </h:inputText>
                                                    </h:panelGroup>
                                                </h:panelGrid>
                                                <h:panelGrid columns="2" columnClasses="col7,col7" id="ll" style="height:30px;"  rendered="#{LoanAccountsDetails.flagAcct=='true'}" styleClass="row2" width="100%"  >
                                                    <h:outputLabel id="lbllamt" styleClass="headerLabel"  value="Lien Amount"  style="width:150%;text-align:left;"> </h:outputLabel>
                                                    <h:inputText id="txtlamt" styleClass="input" value="#{LoanAccountsDetails.lienAmt}">
                                                        <a4j:support ajaxSingle="true"  event="onchange"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                            </h:panelGrid>



                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a92" width="100%">
                                                <h:panelGrid columns="3" columnClasses="col7,col7,col7" id="wefDate" style="height:30px;"  styleClass="row1" width="100%"  >
                                                    <h:outputLabel id="lblwefDate" styleClass="headerLabel"  value="W.E.F.Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                                        <rich:calendar id="ddwefDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{LoanAccountsDetails.wefDate}">
                                                            <a4j:support ajaxSingle="true" action="#{LoanAccountsDetails.onblurWefDate}" event="onchanged" reRender="stxtmessage" focus="txtRemarks"/>
                                                        </rich:calendar>
                                                    </h:panelGroup>
                                                </h:panelGrid>
                                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="remarks" style="height:30px;"   styleClass="row1" width="100%"  >
                                                    <h:outputLabel id="lblRemarks" styleClass="headerLabel"  value="Remarks"  style="width:100%;text-align:left;"></h:outputLabel>
                                                    <h:inputText id="txtRemarks" styleClass="input" style="width: 200px" disabled="#{LoanAccountsDetails.fflag=='false'}" value="#{LoanAccountsDetails.remarks}">
                                                        <a4j:support ajaxSingle="true"  event="onchange"/>
                                                    </h:inputText>
                                                </h:panelGrid>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a93" width="100%">

                                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate" style="height:30px;"  styleClass="row2" width="100%"  >
                                                    <h:outputLabel id="lblReportDate" styleClass="headerLabel"  value="Report Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                                    <rich:calendar id="ddReportDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{LoanAccountsDetails.reportDate}">
                                                        <a4j:support ajaxSingle="true" action="#{LoanAccountsDetails.onblurReportDate}" event="onchanged" reRender="stxtmessage" focus="includeFl"/>
                                                    </rich:calendar>
                                                </h:panelGrid>
                                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate1" style="height:30px;"  styleClass="row2" width="100%"  >
                                                    <h:selectBooleanCheckbox id="includeFl" >
                                                        <a4j:support ajaxSingle="true" event="onclick">
                                                            <a4j:actionparam name="rowId" value="#{result.rowId}" />
                                                        </a4j:support>
                                                        <h:outputLabel id="lblnForAllStatusReport" styleClass="headerLabel"  value="For All Status Report"  style="width:100%;text-align:left;"></h:outputLabel>
                                                    </h:selectBooleanCheckbox>
                                                </h:panelGrid>
                                            </h:panelGrid>

                                            <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                                                <a4j:region>
                                                    <rich:dataTable value="#{LoanAccountsDetails.incis}" var="dataItem"
                                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                                        <f:facet name="header">
                                                            <rich:columnGroup>
                                                                <rich:column colspan="8"><h:outputText value="Details" /></rich:column>
                                                                <rich:column breakBefore="true"  ><h:outputText  value="AccountNo"  /></rich:column>
                                                                <rich:column><h:outputText value="Remark"/></rich:column>
                                                                <rich:column><h:outputText value="Status" /></rich:column>
                                                                <rich:column><h:outputText value="Date" /></rich:column>
                                                                <rich:column><h:outputText value="Amount" /></rich:column>
                                                                <rich:column><h:outputText value="Auth" /></rich:column>
                                                                <rich:column><h:outputText value="EnterBy" /></rich:column>
                                                                <rich:column ><h:outputText value="EfftDate"/></rich:column>
                                                            </rich:columnGroup>
                                                        </f:facet>
                                                        <rich:column ><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                                        <rich:column ><h:outputText value="#{dataItem.remark}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.spFlag}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                                        <rich:column><h:outputText value="#{dataItem.efftDate}" /></rich:column>
                                                    </rich:dataTable>
                                                    <rich:datascroller  align="left" for="taskList"  maxPages="20" />
                                                </a4j:region>
                                            </h:panelGrid>

                                            <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                                                <h:panelGroup id="a1" layout="block" >
                                                    <a4j:commandButton id="btnReport" value="Report"/>
                                                    <a4j:commandButton id="btnSave" value="Save" disabled="#{LoanAccountsDetails.fflag=='false'}" oncomplete="#{rich:component('savePanelSec')}.show()"/>
                                                    <a4j:commandButton id="btnValidate" value="Validate">
                                                    </a4j:commandButton>
                                                    <a4j:commandButton id="btnRefresh" value="Refresh" ajaxSingle="true" reRender="stxtAccountNumber,stxtmessage,txtAccountNumber,txtName,ddlien,txtlienAccountNumber,txtlamt,nStatus,txtpStatus,txtRemarks,taskList,btnSave,a19,ddwefDate,ddReportDate,txtMargin" action="#{LoanAccountsDetails.reFresh}">

                                                    </a4j:commandButton>
                                                    <a4j:commandButton id="btnExit" value="Exit"  ajaxSingle="true" onclick="#{rich:component('exitPanel')}.show()" reRender="stxtmessage,a9,c9,d91,a91,a92,a93,a19">
                                                    </a4j:commandButton>
                                                </h:panelGroup>
                                            </h:panelGrid>
                                        </h:panelGrid>
                                    </a4j:form>
                                    <rich:modalPanel id="savePanelSec" autosized="true" width="200" onshow="#{rich:element('btnYesSavePan')}.focus();">
                                        <f:facet name="header">
                                            <h:outputText value="Do You Want To Save This Record" style="padding-right:15px;" />
                                        </f:facet>
                                        <h:form>
                                            <table width="100%">
                                                <tbody>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="Yes" id="btnYesSavePan" ajaxSingle="true" action="#{LoanAccountsDetails.save}"
                                                                               oncomplete="#{rich:component('savePanelSec')}.hide();" reRender="stxtmessage,a9,c9,d91,a91,a92,a93,a19,SecurityDetailsTable"/>
                                                        </td>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="Cancel" id="btnNoSavePan" ajaxSingle="true" onclick="#{rich:component('savePanelSec')}.hide();return false;" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>

                                </h:form>
                            </rich:modalPanel>
                            <%--END OF ACCOUNT STATUS FORM JSP CODE--%>
                            <rich:modalPanel id="exitPanel" autosized="true" width="200" onshow="#{rich:element('btnYesExit')}.focus();">
                                <f:facet name="header">
                                    <h:outputText value="Are You Sure To Exit?" style="padding-right:15px;" />
                                </f:facet>

                                <h:form>
                                    <table width="100%">
                                        <tbody>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Yes" id="btnYesExit" ajaxSingle="true" action="#{LoanAccountsDetails.clearModelPanel}" oncomplete="#{rich:component('lienMarkingPanel')}.hide(),#{rich:component('accountStatus')}.hide()"
                                                                       onclick="#{rich:component('exitPanel')}.hide();" reRender="lblMsg,btnSave,a1,leftPanelSec,mainPanel,SecurityDetailsTable"/>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton value="Cancel" id="btnNoExit" ajaxSingle="true" onclick="#{rich:component('exitPanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>

                            <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelpo">
                                    <a4j:commandButton id="btnUpdateSD" value="Update" disabled="#{LoanAccountsDetails.updateDisable1}">
                                        <a4j:support  action="#{LoanAccountsDetails.UpdateTable}" event="onclick"
                                                      reRender="message,errorMessage,SecurityDetails,leftPanelSec"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnSaveSD" value="Save" disabled="#{LoanAccountsDetails.saveDisable}">
                                        <a4j:support  action="#{LoanAccountsDetails.saveSecurityDetails}" event="onclick"
                                                      reRender="message,errorMessage,SecurityDetails,leftPanelSec,lblMsg,btnSave,a1,leftPanelSec,mainPanel,SecurityDetailsTable"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefreshSD" value="Refresh">
                                        <a4j:support  action="#{LoanAccountsDetails.resetPage}" event="onclick"
                                                      reRender="message,errorMessage,leftPanelSec,SecurityDetails"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExitSD" action="#{LoanAccountsDetails.exitForm}"  value="Exit">
                                    </a4j:commandButton>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                        <%-- END OF CODE FOR SECURITY CODE TAB--%>
                    </rich:tabPanel>

                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYesSaveEmp')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Do You Want To Save Employment Details ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSaveEmp" ajaxSingle="true" action="#{LoanAccountsDetails.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide()"
                                                       reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,table,taskList"
                                                       focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSaveEmp" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel1" autosized="true" width="200" onshow="#{rich:element('btnYesSaveDoc')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Add Document ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSaveDoc" ajaxSingle="true" action="#{LoanAccountsDetails.saveDocumentDetails}"
                                                       oncomplete="#{rich:component('savePanel1')}.hide()"
                                                       reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1"
                                                       focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSaveDoc" onclick="#{rich:component('savePanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel2" autosized="true" width="200" onshow="#{rich:element('btnYesSaveLoanShare')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Update Loan Share Details ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSaveLoanShare" ajaxSingle="true" action="#{LoanAccountsDetails.saveLoanShareDetails}"
                                                       oncomplete="#{rich:component('savePanel2')}.hide()"
                                                       reRender="message,errorMessage,gpFooter,a34,a35,a36,a37,a38,a39,a40,a41"
                                                       focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSaveLoanShare" onclick="#{rich:component('savePanel2')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="savePanel3" autosized="true" width="200" onshow="#{rich:element('btnYesSaveGuar')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Save Guarantor Details ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSaveGuar" ajaxSingle="true" action="#{LoanAccountsDetails.saveGuarantorDetails}"
                                                       oncomplete="#{rich:component('savePanel3')}.hide()"
                                                       reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2"
                                                       focus="btnRefresh3"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSaveGuar" onclick="#{rich:component('savePanel3')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

            <rich:modalPanel id="custView" height="100" width="300" style="align:right" autosized="true">
                <f:facet name="header">
                    <h:outputText value="Guarantor Date of birth Alert !" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink1"/>
                        <rich:componentControl for="custView" attachTo="glheadlink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="mainPane2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%" >

                        <h:panelGrid id="custViewRow14" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                            <h:outputLabel value="Your Guarantor is going to Retierment !" styleClass="output" style="color:red;text-align:center;"/>
                        </h:panelGrid>
                        <h:panelGrid id="custViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="custViewBtnPanel">
                                <a4j:commandButton id="custViewClose" value="OK"  oncomplete="#{rich:component('custView')}.hide(); return false;" reRender="a42" focus="txtGurantorRetAge">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>

            <%--rich:modalPanel id="networthView" height="100" width="300" style="align:right" autosized="true">
               <f:facet name="header">
                   <h:outputText value="Guarantor Net Worth Alert !" />
               </f:facet>
               <f:facet name="controls">
                   <h:panelGroup>
                       <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="glheadlink2"/>
                       <rich:componentControl for="networthView" attachTo="glheadlink2" operation="hide" event="onclick"/>
                   </h:panelGroup>
               </f:facet>
               <a4j:form>
                   <h:panelGrid id="mainPane3" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%" >
                       <h:panelGrid id="netViewRow14" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                           <h:outputLabel value="Your Guarantor Net Worth is less than sanction amount so,add another guanrantor !" styleClass="output" style="color:red;"/>
                       </h:panelGrid>
                         <h:panelGrid id="netViewRow16" columns="2" columnClasses="col9,col9" width="100%" styleClass="row1" >
                             <h:outputLabel id="lblAccNo" styleClass="label" value="Pending Guaranter No:-"/>
                             <h:outputLabel value="#{LoanAccountsDetails.guarNo}" styleClass="output" style="color:red;"/>
                        </h:panelGrid>
                       
                       <h:panelGrid id="netViewRow15" columns="2" columnClasses="col9,col9" width="100%" style="text-align:center;"styleClass="row1" >
                            <h:outputLabel value="Do you want to exist !" styleClass="output" style="color:green;text-align:center;"/>
                       </h:panelGrid>
                       <h:panelGrid id="netViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                           <h:panelGroup id="netViewBtnPanel">
                               <a4j:commandButton value="Yes" id="btnYesSaveGuar" ajaxSingle="true" action="#{LoanAccountsDetails.exitForm}"
                                                      oncomplete="#{rich:component('networthView')}.hide()"
                                                      reRender="message,errorMessage,gpFooter,a44,a45,a46,a47,a48,a49,a50,a51,a52,a53,a54,a55,a56,a57,a58,a59,table2,taskList2"
                                                      focus="btnRefresh3"/>
                                 <a4j:commandButton value="No" id="btnNoSaveGuar" onclick="#{rich:component('networthView')}.hide();return false;" />
                           </h:panelGroup>
                       </h:panelGrid>
                   </h:panelGrid>
               </a4j:form>
           </rich:modalPanel--%>                                       

            <rich:modalPanel id="savePanel4" autosized="true" width="200" onshow="#{rich:element('btnYesSaveInsu')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Save Insurance Details ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesSaveInsu" ajaxSingle="true" action="#{LoanAccountsDetails.saveInsuranceDetail}"
                                                       oncomplete="#{rich:component('savePanel4')}.hide()"
                                                       reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3"
                                                       focus="btnRefresh3"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoSaveInsu" onclick="#{rich:component('savePanel4')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="updatePanel" autosized="true" width="200" onshow="#{rich:element('btnYesUpdateInsu')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Update Insurance Details ?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesUpdateInsu" ajaxSingle="true" action="#{LoanAccountsDetails.updateInsuranceDetail}"
                                                       oncomplete="#{rich:component('updatePanel')}.hide()"
                                                       reRender="message,errorMessage,gpFooter4,a62,a63,a64,a65,a66,a67,a68,a69,a70,a71,a72,a73,table3,taskList3"
                                                       focus="btnRefresh3"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoUpdateInsu" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete This Record?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{LoanAccountsDetails.delete}"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,gpFooter,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17,table,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel1" autosized="true" width="200" onshow="#{rich:element('btnYesDel1')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete This Record?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel1" ajaxSingle="true"  action="#{LoanAccountsDetails.deleteRecordOfDocuments}"
                                                       oncomplete="#{rich:component('deletePanel1')}.hide();" reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDel1" onclick="#{rich:component('deletePanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel2" autosized="true" width="200" onshow="#{rich:element('btnYesDel2')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete This Record?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel2" ajaxSingle="true"  action="#{LoanAccountsDetails.deleteRecordOfGuarantor}"
                                                       oncomplete="#{rich:component('deletePanel2')}.hide();" reRender="message,errorMessage,gpFooter,a21,a22,a23,a24,a25,a26,a27,a28,a29,a30,a31,table1,taskList1,table2"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDel2" onclick="#{rich:component('deletePanel2')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel3" autosized="true" width="200" onshow="#{rich:element('btnYesDel3')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are You Sure To Delete This Record?" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel3" ajaxSingle="true"  action="#{LoanAccountsDetails.deleteFromGroupCompGrid}"
                                                       oncomplete="#{rich:component('deletePanel3')}.hide();" reRender="message,errorMessage,gpFooter5,a84,a85,a86,table5,taskList5"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoDel3" onclick="#{rich:component('deletePanel3')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="reschedulePanel" autosized="true" width="200" onshow="#{rich:element('btnYesReshe')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Are you sure you want to reschedule UNPAID EMIs Plan ?" style="padding-right:15px;" />
                </f:facet>

                <h:form>
                    <table width="100%">
                        <tbody>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesReshe" ajaxSingle="true" action="#{LoanAccountsDetails.resheduleActionRepayment}"
                                                       onclick="#{rich:component('reschedulePanel')}.hide();" reRender="richEmi,TypeOfInvestmentPlan1,message,errorMessage,InstallmentPlanGrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoReshe" ajaxSingle="true" onclick="#{rich:component('reschedulePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>