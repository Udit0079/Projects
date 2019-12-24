<%-- 
    Document   : loanMislanious
    Created on : Dec 14, 2011, 7:19:03 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Loan MIS Report </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="loanMisLanious">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanMislanious.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Loan MIS Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanMislanious.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{LoanMislanious.errorMsg}"/>
                    </h:panelGrid>

                    <%--
                    <h:panelGrid id="panel222" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">        
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LoanMislanious.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{LoanMislanious.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel value="As on Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{LoanMislanious.frmDt}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                        </h:inputText>      
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%">
                        <h:outputText value="A/c. Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccType" value="#{LoanMislanious.accType}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.accTypeList}"/>
                            <a4j:support event="onblur" action="#{LoanMislanious.blurAcctype}"  reRender="ddSchemeCode" />
                        </h:selectOneListbox>
                        <h:outputText value="Sector" styleClass="label"/>
                        <h:selectOneListbox id="ddlSecor" value="#{LoanMislanious.sector}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.sectorList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputText value="Sub Sector" styleClass="label"/>
                        <h:selectOneListbox id="ddlSubSector" value="#{LoanMislanious.subSector}" size="1" styleClass="ddlist" style="width:100px" >
                            <f:selectItems value="#{LoanMislanious.subSectorList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Security" styleClass="label"/>
                        <h:selectOneListbox id="ddlSecurity" value="#{LoanMislanious.security}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.securityList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%">
                        <h:outputText value="Application Category" styleClass="label"/>
                        <h:selectOneListbox id="ddlAppCategory" value="#{LoanMislanious.appCatgory}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.appCategoryList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Section" styleClass="label"/>
                        <h:selectOneListbox id="ddlSelection" value="#{LoanMislanious.section}" size="1" styleClass="ddlist" style="width:100px" >
                            <f:selectItems value="#{LoanMislanious.sectionList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel5" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputText value="Category Option" styleClass="label"/>
                        <h:selectOneListbox id="ddlCategoryOption" value="#{LoanMislanious.category}" size="1" styleClass="ddlist" style="width:100px" >
                            <f:selectItems value="#{LoanMislanious.categoryOptionList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Minority Community" styleClass="label"/>
                        <h:selectOneListbox id="ddlMinority" value="#{LoanMislanious.minority}" size="1"  styleClass="ddlist" style="width:100px" >
                            <f:selectItems value="#{LoanMislanious.minorityList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel6" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%" >
                        <h:outputText value="Scheme Code" styleClass="label"/>
                        <h:selectOneListbox id="ddlSchemecode" value="#{LoanMislanious.schemeCode}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.schemeCodeOption}" />
                        </h:selectOneListbox>
                        <h:outputText value="Relation" styleClass="label"/>
                        <h:selectOneListbox id="ddlrelation" value="#{LoanMislanious.relation}" size="1" styleClass="ddlist" style="width:100px">
                            <f:selectItems value="#{LoanMislanious.relationList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    --%>

                    <%--*************************--%>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <rich:panel header="Branch Wise" style="text-align:left;">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="RowBr" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputText value="Branch Wise" styleClass="label"/>
                                <h:selectOneListbox id="branchHeader" size="1" value="#{LoanMislanious.branchCode}" styleClass="ddlist">
                                    <f:selectItems value="#{LoanMislanious.branchCodeList}" />
                                </h:selectOneListbox> 
                                <h:outputText value="A/c Nature" styleClass="label"/>
                                <h:selectOneListbox id="ddlAcNatureHeader" value="#{LoanMislanious.acNature}" size="1" styleClass="ddlist" style="width:100px">
                                    <f:selectItems value="#{LoanMislanious.acNatureList}"/>
                                    <a4j:support event="onblur" action="#{LoanMislanious.blurAcctNature}"  reRender="ddlAccTypeHeader,ddSchemeCode" />
                                </h:selectOneListbox>                                
                                <h:outputText value="A/c. Type" styleClass="label"/>
                                <h:selectOneListbox id="ddlAccTypeHeader" value="#{LoanMislanious.accType}" size="1" styleClass="ddlist" style="width:100px">
                                    <f:selectItems value="#{LoanMislanious.accTypeList}"/>
                                    <a4j:support event="onblur" action="#{LoanMislanious.blurAcctype}"  reRender="ddSchemeCode" />
                                </h:selectOneListbox>                                      
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="RowBaseOn" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputText value="Report Based On" styleClass="label"/>
                                <h:selectOneListbox id="ddReportBased" value="#{LoanMislanious.reportBasedOn}" styleClass="ddlist" size="1" style="width:80px">
                                    <f:selectItems value="#{LoanMislanious.reportBasedOnList}" />
                                    <a4j:support event="onblur" actionListener="#{LoanMislanious.sencionedBased}"  reRender="a1" focus="txtFrom" />
                                </h:selectOneListbox>
                                <h:outputLabel value="From " styleClass="label"/>
                                <h:inputText id="txtFrom" value="#{LoanMislanious.from}" size="14"/>
                                <h:outputLabel value="To " styleClass="label"/>
                                <h:inputText id="txtTo" value="#{LoanMislanious.to}" size="14"/>

                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="RowBase" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel value="Report Base" styleClass="label"/>
                                <h:selectOneListbox id="ddReportBase" value="#{LoanMislanious.reportBase}" styleClass="ddlist" size="1" style="width:120px">
                                    <f:selectItems value="#{LoanMislanious.reportBaseList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblLoanPeriod" value="Account Status" styleClass="label"/>
                                <h:selectOneListbox id="ddlLoanPeriod" size="1" styleClass="ddlist" value="#{LoanMislanious.loanPeriod}" >
                                    <f:selectItems value="#{LoanMislanious.loanPeriodList}" />
                                    <a4j:support event="onblur" ajaxSingle="true" />
                                </h:selectOneListbox> 
                                <h:outputLabel value="As on Date" styleClass="label"/>
                                <h:inputText id="frDtHeader" styleClass="input frDt" size="12" value="#{LoanMislanious.frmDt}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                                </h:inputText>
                            </h:panelGrid>
                        </rich:panel>
                        <rich:panel header="Sector Wise Deatils" style="text-align:left;">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblsector" styleClass="label" value="Sector"><font class="required" style="color:red;" title="Sector">*</font></h:outputLabel>
                                    <h:selectOneListbox value="#{LoanMislanious.sector}"id="ddSector" styleClass="ddlist"  size="1" style="width :120px "  title="Sector (182)">
                                        <f:selectItems  value="#{LoanMislanious.sectorOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurSectorVal()}" event="onblur" reRender="ddSubSector,errorMessage,message" oncomplete="setMask();" focus="ddSubSector"  />
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblSubSector" styleClass="label" value="Sub Sector"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.subSector}"id="ddSubSector" styleClass="ddlist"  size="1"  title="Sub-Sector (183)">
                                        <f:selectItems value="#{LoanMislanious.subSectorOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurModeOfAdvanceVal()}" event="onblur" reRender="ddPurposeofAdvance,ddGuaranteeCover,errorMessage,message" oncomplete="setMask();" focus="ddModeofAdvance"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblModeofAdvance" styleClass="label" value="Mode of Advance"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.modeOfAdvance}" id="ddModeofAdvance" styleClass="ddlist" size="1"  title="Mode of Advance(185)">
                                        <f:selectItems value="#{LoanMislanious.modeOfAdvanceOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurModeOfAdvanceVal()}" event="onblur" reRender="ddPurposeofAdvance,ddGuaranteeCover,errorMessage,message" oncomplete="setMask();" focus="ddSecured"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">                                         
                                    <h:outputLabel id="lblSecured" styleClass="label" value="Type of Advances"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.secured}"id="ddSecured" styleClass="ddlist" size="1" title="Type of Advances(187)">
                                        <f:selectItems value="#{LoanMislanious.securedOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblTypeofAdvance" styleClass="label" value="Term of Advance"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.typeOfAdvance}"id="ddTypeofAdvance" styleClass="ddlist"  size="1" title="Term of Advances(186)">
                                        <f:selectItems value="#{LoanMislanious.typeOfAdvanceOption}"/>
                                    </h:selectOneListbox>  
                                    <h:outputLabel id="lblPurposeofAdvance" styleClass="label" value="Sub-Sector's Category(for Quarterly) "><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.purposeOfAdvance}"id="ddPurposeofAdvance"  styleClass="ddlist"  size="1" title="Sub-Sector's Category (Quarter wise)(184)">
                                        <f:selectItems value="#{LoanMislanious.purposeOfAdvanceOption}"/>
                                    </h:selectOneListbox>                                                                                      
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblGuaranteeCover" styleClass="label" value="Sub-Sector's Category(for Yearly)" ><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.guarnteeCover}"id="ddGuaranteeCover" styleClass="ddlist" size="1" title="Sub-Sector's Category(for Yearly)(188)">
                                        <f:selectItems value="#{LoanMislanious.guarnteeCoverOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblPurOfAdv" styleClass="label" value="Purpose of Advances" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px"value="#{LoanMislanious.purOfAdv}"id="ddPurOfAdv" styleClass="ddlist"  size="1"  title="Purpose of Advances (190)">
                                        <f:selectItems value="#{LoanMislanious.purOfAdvOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblGender" styleClass="label" value="Gender"/>
                                    <h:selectOneListbox id="ddGender" styleClass="ddlist" value="#{LoanMislanious.gender}" size="1" title="Gender List (233)">
                                        <f:selectItems value="#{LoanMislanious.genderOption}"/>
                                        <a4j:support event="onblur"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row45" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblIndustry" styleClass="label" value="INDUSTRY" ><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.industry}"id="ddIndustry" styleClass="ddlist" size="1" title="Sub-Sector's Category(for Yearly)(188)">
                                        <f:selectItems value="#{LoanMislanious.industryOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblNPAClass" styleClass="label" value="Npa Classification" ><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.npaClass}"id="ddNpaClass" styleClass="ddlist" size="1" title="Sub-Sector's Category(for Yearly)(196)">
                                        <f:selectItems value="#{LoanMislanious.npaClassOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Exposure" style="text-align:left;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblExposure" styleClass="label" value="Exposure"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.exposure}" id="ddExposure" styleClass="ddlist"  size="1" title="Exposure (191)">
                                        <f:selectItems value="#{LoanMislanious.exposureOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurExposureVal()}" event="onblur" reRender="ddExposureCategory,ddExposureCatePurpose,errorMessage,message" oncomplete="setMask();" focus="ddExposureCategory"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblExposureCategory" styleClass="label" value="Exposure Categroy"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.exposureCategory}" id="ddExposureCategory" styleClass="ddlist"  size="1" title="Exposure Category (230)">
                                        <f:selectItems value="#{LoanMislanious.exposureCategroyOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurExposureCategoryVal()}" event="onblur" reRender="ddExposureCatePurpose,errorMessage,message" oncomplete="setMask();" focus="ddExposureCatePurpose"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblExposureCatePurpose" styleClass="label" value="Purpose of Exposure Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.exposureCategoryPurpose}" id="ddExposureCatePurpose" dir = "RTL" styleClass="ddlist"  size="1" title="Purpose of Exposure Category(231)">
                                        <f:selectItems value="#{LoanMislanious.exposureCatePurposeOption}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Scheme Details" style="text-align:left;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblSchemeCode" styleClass="label" value="Scheme Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.schemeCode}" id="ddSchemeCode" styleClass="ddlist" size="1" >
                                        <f:selectItems value="#{LoanMislanious.schemeCodeOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblInterestType" styleClass="label" value="Interest Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.intType}"id="ddInterestType" styleClass="ddlist" size="1" title="Interest Type(202)">
                                        <f:selectItems value="#{LoanMislanious.intTypeOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblRestructuring" styleClass="label" value="Restructuring"><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.restructuring}" id="ddRestructuring" styleClass="ddlist"  size="1" title="Restructuring(192)">
                                        <f:selectItems value="#{LoanMislanious.restructuringOption}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Category" style="text-align:left;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row20" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblApplicantCatg" styleClass="label" value="Applicant Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.applicantCategory}" id="ddApplicantCatg" styleClass="ddlist"  size="1" title="Applicant Category (208)">
                                        <f:selectItems value="#{LoanMislanious.applicantCategoryOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.visibilityOfCatgOption()}" event="onblur" reRender="ddCatgoption,ddMinority,errorMessage,message" oncomplete="setMask();" focus="ddCatgoption"/>
                                    </h:selectOneListbox>                                                
                                    <h:outputLabel id="lblCatgoption" styleClass="label" value="Category Option"/>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.categoryOpt}" id="ddCatgoption" styleClass="ddlist"  size="1" title="Category (209)">
                                        <f:selectItems value="#{LoanMislanious.categoryOptOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurCategoryWiseMinorityVal()}" event="onblur" reRender="ddCatgoption,ddMinority,errorMessage,message" oncomplete="setMask();" focus="ddMinority"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblMinority" styleClass="label" value="Minority Community"><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" id="ddMinority" styleClass="ddlist"  size="1" value="#{LoanMislanious.minorCommunity}" title="Minority Community(204)">
                                        <f:selectItems value="#{LoanMislanious.minorCommunityOption}"/>
                                    </h:selectOneListbox>                                    
                                </h:panelGrid>
                            </rich:panel>
                            <rich:panel header="Others" style="text-align:left;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="RowRelation" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblCustCat" styleClass="label" value="Director / Staff"><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  value="#{LoanMislanious.relation}" style="width :120px" id="ddCustCat" styleClass="ddlist" size="1" title="Customer Category(210)">
                                        <f:selectItems value="#{LoanMislanious.relationOption}"/>
                                        <a4j:support actionListener="#{LoanMislanious.onblurRelationVal}" event="onblur" reRender="ddRelation,errorMessage,message,ddRelName" oncomplete="setMask();" focus="ddRelation"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblRelation" styleClass="label" value="Relation"><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" id="ddRelation" styleClass="ddlist"  size="1" value="#{LoanMislanious.relOwner}" title="Relation(004)">
                                        <f:selectItems value="#{LoanMislanious.relOwnerOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblDPIndicator" styleClass="label" value="Drawing Power Indicator(DP)"><font style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" id="ddDPIndicator" styleClass="ddlist"  size="1" value="#{LoanMislanious.drawingPwrInd}" title="Drawing Power Indicator(DP)(232)">
                                        <f:selectItems value="#{LoanMislanious.drawingPwrIndOption}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblPopuGroup" styleClass="label" value="Population Group"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.popuGroup}"id="ddPopuGroup" styleClass="ddlist"size="1" title="Population Group(200)">
                                        <f:selectItems value="#{LoanMislanious.popuGroupOption}"/>
                                    </h:selectOneListbox> 
                                    <h:outputLabel id="lblSanctionLevel" styleClass="label" value="Sanction Level"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.sanctionLevel}"id="ddSanctionLevel" styleClass="ddlist"  size="1" title="Sanction Level(193)">
                                        <f:selectItems value="#{LoanMislanious.sanctionLevelOption}"/>
                                    </h:selectOneListbox>     
                                    <h:outputLabel id="lblSanctioningAuthority" styleClass="label" value="Sanctioning Authority"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox style="width :120px" value="#{LoanMislanious.sanctionAuth}"id="ddSanctioningAuthority" styleClass="ddlist" size="1" title="Sanctioning Authority(194)">
                                        <f:selectItems value="#{LoanMislanious.sanctionAuthOption}"/>
                                    </h:selectOneListbox>                                                                                                                                       
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblCourts" styleClass="label" value="Courts"/>
                                    <h:selectOneListbox   style="width :120px"value="#{LoanMislanious.courts}"id="ddCourts" styleClass="ddlist" size="1" title="Courts(196)">
                                        <f:selectItems value="#{LoanMislanious.courtsOption}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblReprtFormat" styleClass="label" value="Format In"/>
                                    <h:selectOneListbox id="ddRptFormat" size="1" styleClass="ddlist" value="#{LoanMislanious.reportFormat}">
                                        <f:selectItems value="#{LoanMislanious.reportFormatIn}"/>
                                        <a4j:support event="onblur" oncomplete="setMask();"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblOverDue" styleClass="label" value="With OverDue?" ><font style="color:red;"></font></h:outputLabel>
                                    <h:selectOneListbox  style="width :120px" value="#{LoanMislanious.overdue}"id="ddOverdue" styleClass="ddlist" size="1">
                                        <f:selectItems value="#{LoanMislanious.overdueOption}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid> 
                            </rich:panel>
                            <rich:panel header="Security" style="text-align:left;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="checkboxSecurity" styleClass="label" value="Security "><font class="required" style="color:red;">*</font></h:outputLabel>  
                                    <h:selectBooleanCheckbox value = "Security" id = "checkMe" style="width:10px" >
                                        <a4j:support event="onclick" action="#{LoanMislanious.onCheckSecurity()}" reRender="lblTypeOfSecurity,ddTypeOfSecurity,lblSecurityNature,ddSecurityNature,ddSecurity,lblSecurityDesc1,ddSecurityDesc1,lblSecurityDesc2,ddSecurityDesc2">
                                        </a4j:support>
                                    </h:selectBooleanCheckbox>
                                <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Security Description" style="display:#{LoanMislanious.displaySectabs}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist" style="width:134px;display:#{LoanMislanious.displaySectabs};"  size="1" value="#{LoanMislanious.securityType}">
                                   <f:selectItems value="#{LoanMislanious.typeOfSecurityList}"/>
                                   <a4j:support  ajaxSingle="true" action="#{LoanMislanious.changeLabel}" event="onblur" 
                                                      reRender="message,errorMsg,ddSecurityNature,ddSecurityDesc1,ddSecurityDesc2"/>
                                </h:selectOneListbox> 
                                    <h:outputLabel id="lblSecurityNature" styleClass="label" value="Security Nature" style="display:#{LoanMislanious.displaySectabs}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityNature" styleClass="ddlist" style="width:134px;display:#{LoanMislanious.displaySectabs};"  size="1" value="#{LoanMislanious.securityNature}">
                                        <f:selectItems value="#{LoanMislanious.securityNatureList}"/> 
                                        <a4j:support event="onblur" oncomplete="setMask();"/>
                                    </h:selectOneListbox>

                               
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblSecurityDesc1" styleClass="label" value="Security Desc (1)" style="display:#{LoanMislanious.displaySectabs}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityDesc1" styleClass="ddlist"  size="1" style="width:134px;display:#{LoanMislanious.displaySectabs}" value="#{LoanMislanious.securityDesc1}">
                                        <f:selectItems value="#{LoanMislanious.securityDesc1List}"/>
                                        <a4j:support  ajaxSingle="true" action="#{LoanMislanious.onChangeOFSecurityDesc1}" event="onblur"
                                                      reRender="message,errorMsg,ddSecurityDesc1,ddSecurityDesc2" />
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblSecurityDesc2" styleClass="label" value="Type Of Security" style="display:#{LoanMislanious.displaySectabs}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityDesc2" styleClass="ddlist"  size="1" style="width:134px;display:#{LoanMislanious.displaySectabs};" value="#{LoanMislanious.securityDesc2}">
                                        <f:selectItems value="#{LoanMislanious.securityDesc2List}"/>                   
                                    </h:selectOneListbox>                         
                                    <h:outputLabel></h:outputLabel>
                                    <h:outputLabel></h:outputLabel>
                                </h:panelGrid>
                            </rich:panel>                    
                        </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnDownload" value="Print (PDF)" action="#{LoanMislanious.printPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton  value="Print (Text)" action="#{LoanMislanious.viewReport}" reRender="a1"/>
                            <h:commandButton id="btnExcel" value="Download Excel" actionListener="#{LoanMislanious.printExcelReport}"/>
                            <a4j:commandButton value="Refresh" action="#{LoanMislanious.refresh}" reRender="a1" />
                            <a4j:commandButton   value="Exit" action="#{LoanMislanious.exitAction}" reRender="a1"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
