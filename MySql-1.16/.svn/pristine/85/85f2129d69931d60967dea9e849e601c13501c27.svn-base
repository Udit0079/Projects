<%--
    Document   : KCCAccountOpen
    Created on : Dec 24, 2010, 11:41:36 AM
    Author     : jitendra Chaudhary
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Kcc Account Opening</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{KCCAccountOpen.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="KCC Account Opening"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{KCCAccountOpen.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="gridPanelam" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" style="width:100%;text-align:center;" styleClass="error" value="#{KCCAccountOpen.message}"/>
                    </h:panelGrid>
                    <rich:tabPanel>
                        <rich:tab label="General/Crop Details" id="genInfo"switchType="client"  title="General/Crop Details">
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type"/>
                                <h:selectOneListbox id="ddAccountType" styleClass="ddlist" style="width:54px" size="1" value="#{KCCAccountOpen.acctType}" >
                                    <f:selectItems value="#{KCCAccountOpen.acctTypeOption}" />
                                    
                                </h:selectOneListbox>
                                <h:outputLabel id="label1" styleClass="label" value="Account Opening Date"><font class="required">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calAccountOpenDt" value="#{KCCAccountOpen.acOpenDt}" disabled="true">

                                </rich:calendar>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="1" id="gridPanel15" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblgeneralInfo" styleClass="label" value="GENERAL INFORMATION"/>
                            </h:panelGrid>
                            <h:panelGrid id="GeneralPanel" columns="3" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblNameOfApplicant" styleClass="label" value="Name Of Applicant"/>
                                        <h:panelGroup id="namePanel">
                                            <h:selectOneListbox  id="ddNameOfApplicant" styleClass="ddlist" size="1" style="width:54px"  value="#{KCCAccountOpen.nameTital}">
                                                <f:selectItems value="#{KCCAccountOpen.titleOption}" />
                                                
                                            </h:selectOneListbox>
                                            <h:inputText  id="txtNameOfApplicant" styleClass="input" style="width:100px" onkeyup="this.value=this.value.toUpperCase();" value="#{KCCAccountOpen.txtName}">
                                                
                                            </h:inputText>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblFatherName" styleClass="label" value="Father's Name"/>
                                        <h:inputText id="txtFatherName"  styleClass="input" onkeyup="this.value=this.value.toUpperCase();"  style="width:100px" value="#{KCCAccountOpen.fathersName}" >
                                           
                                        </h:inputText>
                                        <h:outputLabel id="lblAddress" styleClass="label" value="Address"/>
                                        <h:inputText id="txtAddress"  styleClass="input" onkeyup="this.value=this.value.toUpperCase();"  style="width:100px" value="#{KCCAccountOpen.txtAddress}">
                                           
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow1" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblVillage" styleClass="label" value="City/Village"/>
                                        <h:selectOneListbox  id="ddVillage" styleClass="ddlist"size="1" value="#{KCCAccountOpen.ddVillage}">
                                            <f:selectItems value="#{KCCAccountOpen.villageOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblState" styleClass="label" value="State"/>
                                        <h:inputText id="txtState"  styleClass="input" onkeyup="this.value=this.value.toUpperCase();"  style="width:100px" value="#{KCCAccountOpen.txtState}">
                                          
                                        </h:inputText>
                                        <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date Of Birth"/>
                                        <rich:calendar datePattern="dd/MM/yyyy" id="calDateOfBirth" value="#{KCCAccountOpen.calDob}" >
                                            <a4j:support actionListener="#{KCCAccountOpen.onblurDateOfBirth}"  event="onchanged" focus="txtPhoneNo"
                                             reRender="stxtMsg"  />
                                        </rich:calendar>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow3" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPhoneNo" styleClass="label" value="Phone No"/>
                                        <h:inputText id="txtPhoneNo"  styleClass="input"   style="width:100px" value="#{KCCAccountOpen.phoneNo}" maxlength="10">
                                        
                                        </h:inputText>
                                        <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation"/>
                                        <h:selectOneListbox  id="ddOccupation" styleClass="ddlist" size="1" value="#{KCCAccountOpen.occupation}" >
                                            <f:selectItems value="#{KCCAccountOpen.occupationOption}" />
                                          
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblEducationQualification" styleClass="label" value="Education Qualification"/>
                                        <h:selectOneListbox  id="ddEducationQualification" styleClass="ddlist" size="1" value="#{KCCAccountOpen.education}">
                                            <f:selectItems value="#{KCCAccountOpen.educationListOption}" />
                                           
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="loanPanel" style="width:100%;text-align:left;" styleClass="row2" >
                                        <h:outputLabel id="lblOperationMode" styleClass="label" value="Operation Mode"/>
                                        <h:selectOneListbox  id="ddOperationMode" styleClass="ddlist" size="1">
                                            <f:selectItems value="#{KCCAccountOpen.operationModeOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputText id="stxtaggn" styleClass="output" />
                                        <h:outputText id="stxta" styleClass="output" />
                                        <h:outputText id="stxtbasda" styleClass="output" />
                                        <h:outputText id="stxtb" styleClass="output" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col8,col8" columns="1" id="loanSenPanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblLoanSanDetails" styleClass="label" value="LOAN SANCTION DETAILS"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow4" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblSancAmt" styleClass="label" value="Sanc. Amt"/>
                                        <h:inputText id="txtSancAmt"  styleClass="input"  style="width:100px" value="#{KCCAccountOpen.sancAmount}" maxlength="15">
                                          
                                        </h:inputText>
                                        <h:outputLabel id="lblSancDate" styleClass="label" value="Sanc. Date"/>
                                        <rich:calendar  datePattern="dd/MM/yyyy" id="calSancDate" value="#{KCCAccountOpen.sancDt}">
                                            <a4j:support actionListener="#{KCCAccountOpen.onblurSancDate}"  event="onchanged" focus="txtRabiLimit"
                                             reRender="stxtMsg"  />
                                        </rich:calendar>
                                        <h:outputLabel id="lblRabiLimit" styleClass="label" value="Rabi Limit"/>
                                        <h:inputText id="txtRabiLimit"  styleClass="input"  style="width:100px" value="#{KCCAccountOpen.txtRabiLimit}" maxlength="14">
                                         
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow9" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblKharifLimit" styleClass="label" value="Kharif Limit"/>
                                        <h:inputText id="txtKharifLimit"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.txtKharifLimit}" maxlength="14">
                                         
                                        </h:inputText>
                                        <h:outputLabel id="lblRoi" styleClass="label" value="ROI(%)"/>
                                        <h:inputText id="txtRoi"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.roi}" maxlength="14">
                                       
                                        </h:inputText>
                                        <h:outputLabel id="lblFarmer" styleClass="label" value="Farmer"/>
                                        <h:selectOneListbox id="ddFarmer" styleClass="ddlist" size="1" style="width:100px" value="#{KCCAccountOpen.cmbFarmer}" >
                                            <f:selectItems value="#{KCCAccountOpen.farmerOption}" />
                                         
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col8,col8" columns="1" id="presentIncomePanel" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                                        <h:outputLabel id="lblPresentIncome" styleClass="label" value="PRESENT ANNUAL INCOME OF APPLICANT"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow10" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblAgricultureIncome" styleClass="label" value="A) Agriculture Income"/>
                                        <h:inputText id="txtAgricultureIncome"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.txtAgriInc}" maxlength="14">

                                          
                                        </h:inputText>
                                        <h:outputLabel id="lblOtherIncome" styleClass="label" value="B) Other Income"/>
                                        <h:inputText id="txtOtherIncome"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.txtOthInc}"  maxlength="14">
                                          
                                        </h:inputText>
                                        <h:outputText id="stxtcklukl" styleClass="output" />
                                        <h:outputText id="stxtc" styleClass="output" />
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab  label="Land Holding/Security Details" switchType="client"  title="Land Holding/Security Details">
                            <h:panelGrid columnClasses="col8,col8" columns="1" id="particularLandPanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblParticularLand" styleClass="label" value="PARTICULARS OF LAND HOLDINGS (IF LEASE HOLD/SHARE CROPPER, SPECIFY)"/>
                            </h:panelGrid>
                            <h:panelGrid id="SecurityPanel" columns="3" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel1" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow2" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblVillage1" styleClass="label" value="Village"/>
                                        <h:selectOneListbox  id="ddVillage1" styleClass="ddlist" size="1" value="#{KCCAccountOpen.villages}">
                                            <f:selectItems value="#{KCCAccountOpen.villageOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblSurveyNoBlockNo" styleClass="label" value="Survey No./Block No."/>
                                        <h:inputText  id="txtSurveyNoBlockNo" styleClass="input" style="width:100px"onkeydown="this.value=this.value.toUpperCase();" value="#{KCCAccountOpen.blockNumber}" maxlength="14">
                                           
                                        </h:inputText>
                                        <h:outputLabel id="lblAreaInAcres" styleClass="label" value="Area In Acres"/>
                                        <h:inputText id="txtAreaInAcres"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.areaAcress}" maxlength="14">
                                         
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRowa" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblTitleOwned" styleClass="label" value="Title/Owned"/>
                                        <h:inputText id="txtTitleOwned"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.ownedLands}" maxlength="14">
                                       
                                        </h:inputText>
                                        <h:outputLabel id="lblTitleLeased" styleClass="label" value="Title/Leased"/>
                                        <h:inputText id="txtTitleLeased"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.leaseds}" maxlength="14">
                                     
                                        </h:inputText>
                                        <h:outputLabel id="lblTitleShareCropper" styleClass="label" value="Title/Share Cropper"/>
                                        <h:inputText id="txtTitleShareCropper"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.croppers}" maxlength="14">
                                         
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow5" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblOfWhichIrrigated" styleClass="label" value="Of Which Irrigated"/>
                                        <h:inputText id="txtOfWhichIrrigated"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.irrigateds}" maxlength="14">
                                         
                                        </h:inputText>
                                        <h:outputLabel id="lblSourceOfIrrgation" styleClass="label" value="Source Of Irrgation"/>
                                        <h:inputText id="txtSourceOfIrrgation"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.sourceOfIrrigations}" maxlength="14">
                                         
                                        </h:inputText>
                                        <h:outputLabel id="lblEncumbranceIfAny" styleClass="label" value="Encumbrance If Any"/>
                                        <h:inputText id="txtEncumbranceIfAny"  styleClass="input" style="width:100px" value="#{KCCAccountOpen.incumbrances}" maxlength="14">
                                        
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel104" width="100%" styleClass="row2" style="height:150px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{KCCAccountOpen.landHoldingDetail}" var="dataItem" rows="3"  rowClasses="gridrow1, gridrow2" id="taskList1" columnsWidth="100"
                                                            rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="11"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="S.No." /></rich:column>
                                                        <rich:column><h:outputText value="Village"/></rich:column>
                                                        <rich:column><h:outputText value="Area"/></rich:column>
                                                        <rich:column><h:outputText value="Block No."/></rich:column>
                                                        <rich:column><h:outputText value="Owened Land" /> </rich:column>
                                                        <rich:column><h:outputText value="Leased Land" /></rich:column>
                                                        <rich:column><h:outputText value="Share Cropper" /></rich:column>
                                                        <rich:column><h:outputText value="Irrigated" /></rich:column>
                                                        <rich:column><h:outputText value="Irrgation" /></rich:column>
                                                        <rich:column><h:outputText value="Encumbrance" /></rich:column>
                                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem.seqNo}"/></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.village}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.areaAcres}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.blockNo}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.ownedLand}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.leased}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.cropper}"/></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.irrigated}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.sourceOfIrrigation}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.incumbrance}" /></rich:column>
                                                <rich:column>
                                                    <a4j:commandLink ajaxSingle="true" id="deletelink" actionListener="#{KCCAccountOpen.delete}"  reRender="gridPanel104" >
                                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{row}" target="#{KCCAccountOpen.currentRow}" />
                                                    </a4j:commandLink>
                                                  
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="taskList1" maxPages="20" />
                                        </a4j:region>
                                        <a4j:commandButton id="btnS" value="Add" style="width:80px" oncomplete="#{rich:component('showPanel2')}.show()" >
                                           
                                        </a4j:commandButton>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col8,col8" columns="1" id="SecurityPrposePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lbloffered" styleClass="label" value="SECURITY(IES) PROPOSED TO BE OFFERED"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="2" id="trFinRow" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblPrimary" styleClass="label" value="Primary"/>
                                        <h:outputLabel id="lblCollateral" styleClass="label" value="Collateral(where Applicable)"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="4" id="trFinRow1" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblSecurity1" styleClass="label" value="Security1"/>
                                        <h:selectOneListbox id="ddSecurity1" styleClass="ddlist"  size="1" value="#{KCCAccountOpen.cmbPriSec1}" >
                                            <f:selectItems value="#{KCCAccountOpen.securityPraposalOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblSecurity1a" styleClass="label" value="Security1"/>
                                        <h:selectOneListbox id="ddSecurity1a" styleClass="ddlist"  size="1"  value="#{KCCAccountOpen.cmbPriSec2}" >
                                            <f:selectItems value="#{KCCAccountOpen.securityPraposalOption}" />
                                           
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="4" id="trFinRow1S" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblSecurity2" styleClass="label" value="Security2"/>
                                        <h:selectOneListbox id="ddSecurity2" styleClass="ddlist"  size="1" value="#{KCCAccountOpen.cmbColSec1}" >
                                            <f:selectItems value="#{KCCAccountOpen.securityPraposalOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblSecurity2a" styleClass="label" value="Security2"/>
                                        <h:selectOneListbox id="ddSecurity2a" styleClass="ddlist"  size="1"  value="#{KCCAccountOpen.cmbColSec2}" >
                                            <f:selectItems value="#{KCCAccountOpen.securityPraposalOption}" />
                                            
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                        <rich:tab   label="Liabilities Information" switchType="client"  title="Liabilities/Assets Details">
                            <h:panelGrid columnClasses="col8,col8" columns="1" id="LiabilitiesPanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblLiabilities" styleClass="label" value="PARTICULARS OF EXISTING LIABILITIES AS BORROWER, IF ANY"/>
                            </h:panelGrid>
                            <h:panelGrid id="genInfoPanel1" columns="2" style="width:100%;text-align:center;">
                                <h:panelGrid id="leftPanel2" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow1aq" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblNameOfInstitution" styleClass="label" value="Name Of Institution"/>
                                        <h:selectOneListbox id="ddNameOfInstitution" styleClass="ddlist"  size="1" value="#{KCCAccountOpen.institutions}" >
                                            <f:selectItems value="#{KCCAccountOpen.nameOfInsOption}" />
                                           
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblPurposeOfLoan" styleClass="label" value="Purpose Of Loan"/>
                                        <h:inputText  id="txtPurposeOfLoan" maxlength="14"styleClass="input" style="120px" value="#{KCCAccountOpen.purposes}">
                                           
                                        </h:inputText>
                                        <h:outputLabel id="lblPresent" styleClass="label" value="Present O/S"/>
                                        <h:inputText id="txtPresent"  styleClass="input" style="120px" maxlength="14" value="#{KCCAccountOpen.osAmounts}">
                                         
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow2" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblOverDue" styleClass="label" value="Over Due"/>
                                        <h:inputText id="txtOverDue" styleClass="input" maxlength="14" style="120px" value="#{KCCAccountOpen.overDues}">
                                           
                                        </h:inputText>
                                        <h:outputLabel id="lblSecurityOffered" styleClass="label" value="Security Offered"/>
                                        <h:inputText id="txtSecurityOffered" styleClass="input" maxlength="14" style="120px" value="#{KCCAccountOpen.securitys}" >
                                        
                                        </h:inputText>
                                        <h:outputLabel id="lblInstallAmtRepayDurTheYear" styleClass="label" value="Install. Amt Repay.Dur. The Year"/>
                                        <h:inputText id="txtInstallAmtRepayDurTheYear" maxlength="14" styleClass="input" style="120px" value="#{KCCAccountOpen.installAmts}">
                                           
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:150px;">
                                        <a4j:region>
                                            <rich:dataTable value="#{KCCAccountOpen.libilitiesDetails}" var="dataItem1" rows="3" rowClasses="gridrow1, gridrow2" id="taskList" columnsWidth="100"
                                                            rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="8"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="S. No" /></rich:column>
                                                        <rich:column><h:outputText value="institution" /></rich:column>
                                                        <rich:column><h:outputText value="Purpose" /></rich:column>
                                                        <rich:column><h:outputText value="OS Amount" /></rich:column>
                                                        <rich:column><h:outputText value="OverDue" /></rich:column>
                                                        <rich:column><h:outputText value="Security" /></rich:column>
                                                        <rich:column><h:outputText value="Install. Amt" /></rich:column>
                                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem1.sequenceNo}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.institution}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.purpose}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.osAmount}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.overDue}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.security}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem1.installAmt}" /></rich:column>
                                                <rich:column>
                                                    <a4j:commandLink ajaxSingle="true" id="deletelink1" actionListener="#{KCCAccountOpen.libilitiesDelete}"  reRender="gridPanel103" >
                                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{row}" target="#{KCCAccountOpen.currentRow}" />
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                                        </a4j:region>
                                        <a4j:commandButton id="btnAdd" value="Add" style="width:80px" oncomplete="#{rich:component('libilitiesPanel1')}.show()" >
                                           
                                        </a4j:commandButton>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col8,col8" columns="1" id="farmImmovalblePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                        <h:outputLabel id="lblfarmImmovalble" styleClass="label" value="PARTICULARS OF FARM EQIPMENTS / LIVE STOCK / IMMOVALBLE ASSETS OWENED"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow3" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblMovable" styleClass="label" value="Movable"/>
                                        <h:selectOneListbox id="ddMovable" styleClass="ddlist"  size="1" value="#{KCCAccountOpen.movabless}" >
                                            <f:selectItems value="#{KCCAccountOpen.movableOption}" />
                                            
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblNumber" styleClass="label" value="Number"/>
                                        <h:inputText maxlength="14" id="txtNumber" value="#{KCCAccountOpen.number}" styleClass="input"onkeydown="this.value=this.value.toUpperCase();" >
                                           
                                        </h:inputText>
                                        <h:outputLabel id="lblPresentValue" styleClass="label" value="Present Value"/>
                                        <h:inputText maxlength="14" id="txtPresentValue" styleClass="input" value="#{KCCAccountOpen.presentValue}" onkeydown="this.value=this.value.toUpperCase();"   >
                                         
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel13a" width="100%" styleClass="row1" style="height:150px;">
                                    <a4j:region>
                                            <rich:dataTable  value="#{KCCAccountOpen.movableDetail}" var="dataItem2" rows="3" rowClasses="gridrow1, gridrow2" id="taskList2" columnsWidth="100"
                                                             rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="8"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="S. No" /></rich:column>
                                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                                        <rich:column><h:outputText value="Number" /></rich:column>
                                                        <rich:column><h:outputText value="Present Value" /></rich:column>
                                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column ><h:outputText  value="#{dataItem2.seqNo}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem2.particulars}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem2.number}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem2.presentValue}"/></rich:column>
                                                <rich:column>
                                                    <a4j:commandLink ajaxSingle="true" id="deletelink2" actionListener="#{KCCAccountOpen.movableDelete}"  reRender="gridPanel13a" >
                                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{row}" target="#{KCCAccountOpen.currentRow}" />
                                                    </a4j:commandLink>
                                                  
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="taskList2" maxPages="20" />
                                        </a4j:region>
                                        <a4j:commandButton id="btnAdd1" value="Add" style="width:80px" oncomplete="#{rich:component('movablePanel')}.show()" >
                                           
                                        </a4j:commandButton>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow4" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblImmovable" styleClass="label" value="Immovable"/>
                                        <h:selectOneListbox  id="ddImmovable" styleClass="ddlist"  size="1" value="#{KCCAccountOpen.imovabless}" >
                                            <f:selectItems value="#{KCCAccountOpen.imovableOption}" />
                                            
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblNumber1" styleClass="label" value="Number"/>
                                        <h:inputText maxlength="14" id="txtNumber1" styleClass="input"onkeydown="this.value=this.value.toUpperCase();" value="#{KCCAccountOpen.inumbers}" >
                                            
                                        </h:inputText>
                                        <h:outputLabel id="lblPresentValue1" styleClass="label" value="Present Value"/>
                                        <h:inputText maxlength="14" id="txtPresentValue1" styleClass="input"onkeydown="this.value=this.value.toUpperCase();" value="#{KCCAccountOpen.ipresentValues}" >
                                          
                                        </h:inputText>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel13b" width="100%" styleClass="row1" style="height:150px;">
                                    <a4j:region>
                                            <rich:dataTable  value="#{KCCAccountOpen.imovableDetails}" var="dataItem3" rows="3" rowClasses="gridrow1, gridrow2" id="taskList3" columnsWidth="100"
                                                             rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column colspan="8"></rich:column>
                                                        <rich:column breakBefore="true"><h:outputText value="S. No" /></rich:column>
                                                        <rich:column><h:outputText value="Particulars" /></rich:column>
                                                        <rich:column><h:outputText value="Number" /></rich:column>
                                                        <rich:column><h:outputText value="Present Value" /></rich:column>
                                                        <rich:column><h:outputText value="Select" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column ><h:outputText  value="#{dataItem3.imSeqNo}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem3.imParticulars}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem3.imNumber}"/></rich:column>
                                                <rich:column ><h:outputText  value="#{dataItem3.imPresentValue}"/></rich:column>
                                                <rich:column>
                                                    <a4j:commandLink ajaxSingle="true" id="deletelink3" actionListener="#{KCCAccountOpen.imovableDelete}"  reRender="gridPanel13b" >
                                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{row}" target="#{KCCAccountOpen.currentRow}" />
                                                    </a4j:commandLink>
                                                   
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="taskList3" maxPages="20" />
                                        </a4j:region>
                                        <a4j:commandButton id="btnAdd2" value="Add" style="width:80px" oncomplete="#{rich:component('imovablePanel')}.show()" >
                                            
                                        </a4j:commandButton>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRemarks" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"/>
                                        <h:inputText  id="txtRemarks" styleClass="input"onkeydown="this.value=this.value.toUpperCase();" value="#{KCCAccountOpen.remarks}" >
                                            
                                        </h:inputText>
                                        <h:outputText id="stxtRvb" styleClass="output" />
                                        <h:outputText id="stxtR" styleClass="output" />
                                        <h:outputText id="stxtRluyjd" styleClass="output" />
                                        <h:outputText id="stxtS" styleClass="output" />
                                    </h:panelGrid>

                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:tab>
                    </rich:tabPanel>
                    <h:panelGrid id="footerPanel34" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel1">
                            <a4j:commandButton id="btnSave" value="Save" action="#{KCCAccountOpen.saveData}" reRender="txtNameOfApplicant,txtFatherName,txtAddress,txtState,calDateOfBirth,txtPhoneNo,txtSancAmt,calSancDate,txtRabiLimit,txtKharifLimit,txtRoi,txtAgricultureIncome,txtOtherIncome,txtRemarks,gridPanel104,gridPanel103,gridPanel13a,gridPanel13b,stxtMsg" >
                               
                            </a4j:commandButton>
                            <a4j:commandButton id="btnReset" value="Reset" action="#{KCCAccountOpen.refreshAll}" reRender="txtNameOfApplicant,txtFatherName,txtAddress,txtState,calDateOfBirth,txtPhoneNo,txtSancAmt,calSancDate,txtRabiLimit,txtKharifLimit,txtRoi,txtAgricultureIncome,txtOtherIncome,txtRemarks,gridPanel104,gridPanel103,gridPanel13a,gridPanel13b,stxtMsg,  txtAreaInAcres,txtSurveyNoBlockNo,txtTitleOwned,txtTitleLeased,txtTitleShareCropper,txtOfWhichIrrigated,txtSourceOfIrrgation,txtEncumbranceIfAny,txtPurposeOfLoan,txtPresent,txtOverDue,txtSecurityOffered,txtInstallAmtRepayDurTheYear,txtPresentValue,txtNumber,txtPresentValue1,txtNumber1" >
                                
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{KCCAccountOpen.exitForm}" reRender="txtNameOfApplicant,txtFatherName,txtAddress,txtState,calDateOfBirth,txtPhoneNo,txtSancAmt,calSancDate,txtRabiLimit,txtKharifLimit,txtRoi,txtAgricultureIncome,txtOtherIncome,txtRemarks,gridPanel104,gridPanel103,gridPanel13a,gridPanel13b,stxtMsg ,  txtAreaInAcres,txtSurveyNoBlockNo,txtTitleOwned,txtTitleLeased,txtTitleShareCropper,txtOfWhichIrrigated,txtSourceOfIrrgation,txtEncumbranceIfAny,txtPurposeOfLoan,txtPresent,txtOverDue,txtSecurityOffered,txtInstallAmtRepayDurTheYear,txtPresentValue,txtNumber,txtPresentValue1,txtNumber1" >
                                
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>

            </a4j:form>
            <rich:modalPanel id="showPanel2" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink21" />
                        <rich:componentControl for="showPanel2" attachTo="hidelink21" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Add More Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{KCCAccountOpen.loadGrid}"
                                                       oncomplete="#{rich:component('showPanel2')}.hide();" reRender="ddVillage1,stxtMsg,txtAreaInAcres,txtSurveyNoBlockNo,txtTitleOwned,txtTitleLeased,txtTitleShareCropper,txtOfWhichIrrigated,txtSourceOfIrrgation,txtEncumbranceIfAny,gridPanel104" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanel2')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="libilitiesPanel1" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink2" />
                        <rich:componentControl for="libilitiesPanel1" attachTo="hidelink2" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Add More Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{KCCAccountOpen.loadGridExistingLibilities}"
                                                       oncomplete="#{rich:component('libilitiesPanel1')}.hide();" reRender="ddNameOfInstitution,stxtMsg,txtPurposeOfLoan,txtPresent,txtOverDue,txtSecurityOffered,txtInstallAmtRepayDurTheYear,gridPanel103" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('libilitiesPanel1')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="movablePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink4" />
                        <rich:componentControl for="movablePanel" attachTo="hidelink4" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Add More Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{KCCAccountOpen.loadGridMovable}"
                                                       oncomplete="#{rich:component('movablePanel')}.hide();" reRender="gridPanel13a,stxtMsg,ddMovable,txtPresentValue,txtNumber"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('movablePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="imovablePanel" autosized="true" width="200">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/images/modal/close.png" styleClass="hidelink" id="hidelink3" />
                        <rich:componentControl for="imovablePanel" attachTo="hidelink3" operation="hide" event="onclick" />
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Do You Want To Add More Record?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" ajaxSingle="true" action="#{KCCAccountOpen.loadGridImovable}"
                                                       oncomplete="#{rich:component('imovablePanel')}.hide();" reRender="gridPanel13b,stxtMsg,ddImmovable,txtPresentValue1,txtNumber1"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" onclick="#{rich:component('imovablePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>

        </body>
    </html>
</f:view>