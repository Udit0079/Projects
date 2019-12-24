<%--
    Document   : DlAccountOpeningRegister
    Created on : 26 Nov, 2010, 1:04:38 PM
    Author     : root
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
            <title>DL Account Opening Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DlAccountOpeningRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="DL Account Opening Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DlAccountOpeningRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{DlAccountOpeningRegister.messageDl}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCustomerID" styleClass="label" value="Customer ID"><font class="required" style="color:red;">*</font></h:outputLabel>

                            <h:inputText id="txtCustomerID" styleClass="input" tabindex="1" maxlength="10" value="#{DlAccountOpeningRegister.customerId}" style="width:80px;">
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.getAccountDetails}" event="onblur"
                                               reRender="leftPanel,btnSave,MarkSecurities" focus="ddACType"/>
                            </h:inputText>
                            <h:outputLabel id="lblACType" styleClass="label" value="A/C Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddACType" styleClass="ddlist" tabindex="2"  size="1" style="width:150px;" disabled="#{DlAccountOpeningRegister.disableFlag}" value="#{DlAccountOpeningRegister.accType}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.accountTypeList}"/>
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.setScheme}" event="onblur"
                                               reRender="Row1,Row7,Row9,Row10" focus="ddSchemeCode"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblSchemeCode" styleClass="label" value="Scheme Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddSchemeCode" styleClass="ddlist" tabindex="3" size="1" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableFlag}" value="#{DlAccountOpeningRegister.schemeCode}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.schemeCodeList}"/>
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.SetValueAcctToScheme}" event="onblur"
                                               reRender="leftPanel,Row9,MarkSecurities,txtAcPreferableDR,txtAcPreferableCr" focus="ddOrganizationType"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblName" styleClass="label" value="Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id ="title">
                                <%--
                                <h:selectOneListbox id="ddTitle" styleClass="ddlist" tabindex="4" style="width:40px;" disabled="#{DlAccountOpeningRegister.disableTitle}" size="1" value="#{DlAccountOpeningRegister.title}" >
                                    <f:selectItems value="#{DlAccountOpeningRegister.titleList}"/>
                                </h:selectOneListbox>
                                --%>
                                <h:inputText id="txtName" tabindex="5" styleClass="input" maxlength="100" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableName}" value="#{DlAccountOpeningRegister.nameDl}" onkeyup="this.value = this.value.toUpperCase();">
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name"/>
                            <h:inputText id="txtFatherHusbandName" tabindex="6" maxlength="100" styleClass="input" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableHFName}" value="#{DlAccountOpeningRegister.HFName}">
                            </h:inputText>
                            <h:outputLabel id="lblPhoneNo" styleClass="label" value="Phone No"/>
                            <h:inputText id="txtPhoneNo" styleClass="input" tabindex="7" style="width:80px;" maxlength="100" disabled="#{DlAccountOpeningRegister.disablePhoneNo}" value="#{DlAccountOpeningRegister.phoneNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPermanentAddress" styleClass="label" value="Permanent Address"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPermanentAddress" maxlength="100" tabindex="8" styleClass="input" style="width:140px;" disabled="#{DlAccountOpeningRegister.disablePerAdd}" value="#{DlAccountOpeningRegister.permanentAdd}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblCorrespondenceAddress" styleClass="label" value="Correspondence Address"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCorrespondenceAddress" maxlength="100" tabindex="9" styleClass="input" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableCorresAdd}" onkeyup="this.value = this.value.toUpperCase();" value="#{DlAccountOpeningRegister.corresAdd}">
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.setCorressAdd}" event="onblur"
                                               reRender="txtCorrespondenceAddress"/>
                            </h:inputText>
                        <%--
                            <h:outputLabel id="lblOrganizationType" styleClass="label" value="Organization Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOrganizationType" styleClass="ddlist" tabindex="10" size="1" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableFlag}" value="#{DlAccountOpeningRegister.orgType}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.orgTypeList}"/>
                            </h:selectOneListbox>
                        --%>
                        <%-- Added by Manish Kumar --%>
                            <h:outputLabel id="lblOrganizationType" styleClass="label" value="Organization Type"></h:outputLabel>
                            <h:outputLabel id="organizationTypeId" styleClass="label" value="#{DlAccountOpeningRegister.organizationDesc}"></h:outputLabel>
                        <%-- --%>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row13" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblActCategType" styleClass="label" value="Account Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddActCategType" styleClass="ddlist" tabindex="10" size="1" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableFlag}" value="#{DlAccountOpeningRegister.actCategory}" >
                            <f:selectItems value="#{DlAccountOpeningRegister.actCategoryList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>  
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPanGirNo" styleClass="label" value="Pan/Gir No"/>
                            <h:inputText id="txtPanGirNo" styleClass="input" maxlength="20" tabindex="11" style="width:80px;" disabled="#{DlAccountOpeningRegister.disablePenNo}" value="#{DlAccountOpeningRegister.panGirNo}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date of Birth"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calDateOfBirth" tabindex="12" inputSize="10" disabled="#{DlAccountOpeningRegister.disabledob}" value="#{DlAccountOpeningRegister.dateofBirth}" >
                            </rich:calendar>
                            <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operating Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOperatingMode" style="width:140px;" tabindex="13" styleClass="ddlist"  size="1" disabled="#{DlAccountOpeningRegister.disableFlag}"
                                                value="#{DlAccountOpeningRegister.operatingMode}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.operatingModeList}"/>
                                <a4j:support actionListener="#{DlAccountOpeningRegister.ddOperationModeDisableJtName}" event="onblur"
                                             oncomplete="if(#{rich:element('ddOperatingMode')}.value == '0'){#{rich:element('ddOperatingMode')}.focus();}
                                             else{#{rich:element('txtCustIdJtName1')}.focus();}"                                              
                                             reRender="Row5,lblMsg,txtHufFamily"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblCustIdJtName1" styleClass="label" value="Customer Id/ JtName1 "/>
                            <h:panelGroup id ="group1">
                                <h:inputText id="txtCustIdJtName1" maxlength="10" tabindex="14" disabled="#{DlAccountOpeningRegister.jt1IdDisable}" style="width:40px;" styleClass="input"  value="#{DlAccountOpeningRegister.jt1CustomerId}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support   actionListener="#{DlAccountOpeningRegister.setJtName1}" event="onblur"
                                                   oncomplete="if((#{DlAccountOpeningRegister.jt2IdDisable=='false'})){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else{#{rich:element('ddRateCode')}.focus();}"
                                                   reRender="Row5,lblMsg,txtCustIdJtName1" focus="txtCustIdJtName2"/>
                                </h:inputText>
                                <h:inputText id="txtJtName1" maxlength="50" disabled="#{DlAccountOpeningRegister.jt1Disable}" styleClass="input" style="width:99px;" value="#{DlAccountOpeningRegister.jtName1}" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustIdJtName2" styleClass="label" value="Customer Id/ JtName2 "/>
                            <h:panelGroup id ="group2">
                                <h:inputText id="txtCustIdJtName2"  maxlength="10" tabindex="15" disabled="#{DlAccountOpeningRegister.jt2IdDisable}" style="width:40px;" styleClass="input"  value="#{DlAccountOpeningRegister.jt2CustomerId}">
                                    <a4j:support   actionListener="#{DlAccountOpeningRegister.setJtName2}" event="onblur"
                                                   reRender="Row5,lblMsg,txtCustIdJtName2" focus="ddRateCode"/>
                                </h:inputText>
                                <h:inputText id="txtJtName2" maxlength="50" disabled="#{DlAccountOpeningRegister.jt2Disable}" styleClass="input" style="width:99px;" value="#{DlAccountOpeningRegister.jtName2}"/>
                            </h:panelGroup>

                            <h:outputLabel id="lblRateCode" styleClass="label" value="Rate Code "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRateCode" styleClass="ddlist" tabindex="16" style="width:140px;" disabled="#{DlAccountOpeningRegister.disableFlag}" size="1" value="#{DlAccountOpeningRegister.rateCode}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.rateCodeList}"/>
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.disablePaggingFreq}" event="onblur"
                                               oncomplete="if((#{DlAccountOpeningRegister.hufFlag=='false'})){#{rich:element('txtHufFamily')}.focus();}
                                               else{#{rich:element('txtIntroducerAccount')}.focus();}"
                                               reRender="lblMsg,txtPeggingFrequency"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="4" id="a15" style="width:100%;text-align:left;" styleClass="row2" columnClasses="col3,col49,col3,col3" width="100%">
                            <h:outputLabel id="lblHufFamily" styleClass="label" value="HUF Family Detail"/>
                            <h:inputText id="txtHufFamily" styleClass="input" tabindex="17" style="width:590px" value="#{DlAccountOpeningRegister.hufFamily}" disabled="#{DlAccountOpeningRegister.hufFlag}"/>                                
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblIntroducerAccount" styleClass="label" value="Customer Id / Introducer Account"/>
                            <h:panelGroup id = "group12">
                                <h:inputText id="txtIntroducerId" tabindex="18" maxlength="10" disabled="#{DlAccountOpeningRegister.disableIntroAcct}" styleClass="input" style="width:40px;" value="#{DlAccountOpeningRegister.introId}">
                                </h:inputText>
                                <h:inputText id="txtIntroducerAccount" tabindex="19" disabled="#{DlAccountOpeningRegister.disableFlag}" maxlength="#{DlAccountOpeningRegister.acNoMaxLen}" styleClass="input" style="width:99px;" value="#{DlAccountOpeningRegister.oldintroducerAccountNo}">
                                    <a4j:support   actionListener="#{DlAccountOpeningRegister.getIntroducerAcctDetails}" event="onblur"
                                                   reRender="Row6,lblMsg" focus="#{rich:clientId('calDateOfDoc')}InputDate"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblIntrodName"  styleClass="label"  value="#{DlAccountOpeningRegister.introducerName}" />
                            <h:panelGroup id="intro">
                                <h:outputLabel id="lblAcct"  styleClass="label" value="#{DlAccountOpeningRegister.introducerAccount}"/>
                                <h:outputLabel id="lblAcct1" styleClass="label" value="    "/>
                                <h:outputLabel id="lblStatus" styleClass="label"  value="#{DlAccountOpeningRegister.introducerAcctStatus}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblDateOfDoc" styleClass="label" value="Date of Document"></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="20" id="calDateOfDoc" inputSize="10" disabled="#{DlAccountOpeningRegister.disableFlag}" value="#{DlAccountOpeningRegister.dateOfDoc}" >
                                <a4j:support event="onchanged" focus="txtAmtSanctioned"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAmtSanctioned" styleClass="label" value="Amt. Sanctioned"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtAmtSanctioned" tabindex="21" disabled="#{DlAccountOpeningRegister.disableFlag}" styleClass="input" style="width:140px;" value="#{DlAccountOpeningRegister.amtSanction}">
                                <a4j:support event="onblur" focus="txtLoanPeriod"/>
                            </h:inputText>
                            <h:outputLabel id="lblLoanPeriod"  styleClass="label"  value="Loan Period(MM/DD) "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id="PLoanPeriod">
                                <h:inputText id="txtLoanPeriod" tabindex="22" disabled="#{DlAccountOpeningRegister.disableFlag}" styleClass="input" style="width:70px;" value="#{DlAccountOpeningRegister.loanPeriodMonths}">
                                    <a4j:support event="onblur" focus="txtLoanPeriodDays"/>
                                </h:inputText>
                                <h:outputLabel id="lblLoanPeriod1"  styleClass="label"  value="/"/>
                                <h:inputText id="txtLoanPeriodDays" tabindex="23"  disabled="#{DlAccountOpeningRegister.disableFlag}" styleClass="input" style="width:70px;" value="#{DlAccountOpeningRegister.loanPeriodDays}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblMoratoriumPeriod" styleClass="label" value="Moratorium Period (in MM) "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtMoratoriumPeriod" tabindex="24" disabled="#{DlAccountOpeningRegister.disableFlag}" styleClass="input" style="width:140px;" value="#{DlAccountOpeningRegister.moratoriumPeriod}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="2" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblMarkSecurities" styleClass="label" value="Mark Securities : " style="color:purple;padding-left:200px;font-size:15px;"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneRadio id="MarkSecurities" tabindex="25" styleClass="label" disabled="#{DlAccountOpeningRegister.disableMarkSecurity}" value="#{DlAccountOpeningRegister.markSecurities}" style="width:80%;text-align:left;padding-right:200px;color:purple" >
                                <f:selectItems value="#{DlAccountOpeningRegister.marksSecuritiesList}"/>
                                <a4j:support   ajaxSingle="true"  event="onchange" reRender="SecurityPanel,lienMarkingPanel,show,accountStatus"
                                               oncomplete="if(#{DlAccountOpeningRegister.markSecurities == '3'})#{rich:component('SecurityPanel')}.show();
                                               else if(#{DlAccountOpeningRegister.markSecurities == '2'})#{rich:component('lienMarkingPanel')}.show();
                                               else if(#{DlAccountOpeningRegister.markSecurities == '1'})#{rich:component('accountStatus')}.show();"/>
                            </h:selectOneRadio>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblInterestCode" styleClass="label" value="Interest Code / R.O.I"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id="LoanPeriod">
                                <h:selectOneListbox id="ddInterestCode" tabindex="26" readonly="true" styleClass="ddlist"  style="width:70px;" disabled="#{DlAccountOpeningRegister.disableFlag}" size="1" value="#{DlAccountOpeningRegister.interestCode}" >
                                    <f:selectItems value="#{DlAccountOpeningRegister.interestCodeList}"/>
                                    <a4j:support   actionListener="#{DlAccountOpeningRegister.setRoi}" event="onblur"
                                                   reRender="lblMsg,Row9,AcPreferablep" focus="txtAcPreferableDR"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblInterest" styleClass="label" value=" / "/>
                                <h:inputText id="txtInterestCode" maxlength="6" tabindex="27" disabled="#{DlAccountOpeningRegister.disableRoi}" styleClass="input" style="width:70px;" value="#{DlAccountOpeningRegister.roi}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblAcPreferable" styleClass="label" value="A/ c Preferable Dr/Cr"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id ="AcPreferablep">
                                <h:inputText id="txtAcPreferableDR" maxlength="5" tabindex="28" disabled="#{DlAccountOpeningRegister.txtAcPrefDrDisFlag}" styleClass="input" style="width:70px;" value="#{DlAccountOpeningRegister.acPreferableDr}"/>
                                <h:outputLabel id="lblrdcd" styleClass="label" value=" / "/>
                                <h:inputText id="txtAcPreferableCr" maxlength="5" tabindex="29" disabled="#{DlAccountOpeningRegister.txtAcPrefCrDisFlag}" styleClass="input" style="width:70px;" value="#{DlAccountOpeningRegister.acPreferableCr}">
                                    <a4j:support event="onblur" actionListener="#{DlAccountOpeningRegister.printApplicableRoi}" reRender="stxtApplicableRoi,lblMsg"/>
                                </h:inputText>
                                <h:outputText id="stxtApplicableRoi" styleClass="output" value="#{DlAccountOpeningRegister.applicableRoi}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblPeggingFrequency" styleClass="label" value="Pegging Frequency(in MM)"/>
                            <h:inputText id="txtPeggingFrequency" tabindex="30" disabled="#{DlAccountOpeningRegister.disablePaggFreq}" styleClass="input" style="width:140px;" value="#{DlAccountOpeningRegister.peggingFrequency}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDisbursementType" styleClass="label" value="Disbursement Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDisbursementType" tabindex="31" styleClass="ddlist"  style="width:140px;" disabled="#{DlAccountOpeningRegister.disableFlag}" size="1" value="#{DlAccountOpeningRegister.disbursementType}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.disburestTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCalculationMethod" styleClass="label" value="Calculation Method"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalculationMethod" tabindex="32" styleClass="ddlist"  style="width:140px;" disabled="#{DlAccountOpeningRegister.disableRoi}" size="1" value="#{DlAccountOpeningRegister.calculationMethod}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.calculationMethodList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIntApplicableFreq" styleClass="label" value="Int Applicable Freq "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntApplicableFreq" tabindex="33" styleClass="ddlist"  style="width:140px;" disabled="#{DlAccountOpeningRegister.disableRoi}" size="1" value="#{DlAccountOpeningRegister.intApplicableFreq}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.intAppFreqList}"/>
                                <a4j:support   actionListener="#{DlAccountOpeningRegister.disableCompFreq}" event="onblur"
                                               reRender="lblMsg,Row11"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCalculationLevel" styleClass="label" value="Calculation Level "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalculationLevel" tabindex="34" styleClass="ddlist"  style="width:140px;" disabled="#{DlAccountOpeningRegister.disableCalcLevelFlag}" size="1" value="#{DlAccountOpeningRegister.calculationLevel}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.calCuLevelList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCalculationOn" styleClass="label" value="Calculation On"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalculationOn" styleClass="ddlist" tabindex="35" style="width:140px;"  disabled="#{DlAccountOpeningRegister.disableCalculationOn}" size="1" value="#{DlAccountOpeningRegister.calculationOn}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.calculationOnList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCompoundingFrequency" styleClass="label" value="Compounding Frequency "><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCompoundingFrequency" tabindex="36" styleClass="ddlist"  style="width:140px;" disabled="#{DlAccountOpeningRegister.disableCompFreq}" size="1" value="#{DlAccountOpeningRegister.compoundingFrequency}" >
                                <f:selectItems value="#{DlAccountOpeningRegister.compFrequencyList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2" columns="1" id="Row12"  width="100%" styleClass="row2">
                            <a4j:region>
                                <rich:dataTable value ="#{DlAccountOpeningRegister.dlAcctOpen}"
                                                rowClasses="row1, row2" id = "DlTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>

                                            <rich:column colspan="17"></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                            <rich:column><h:outputText value="Type Of Sec" /></rich:column>
                                            <rich:column><h:outputText value="SecNature" /></rich:column>
                                            <rich:column><h:outputText value="SecDes1" /></rich:column>
                                            <rich:column><h:outputText value="Particular" /></rich:column>
                                            <rich:column><h:outputText value="Lien Value" /></rich:column>
                                            <rich:column><h:outputText value="MatValue" /></rich:column>
                                            <rich:column><h:outputText value="MatDate" /></rich:column>
                                            <rich:column><h:outputText value="Issue Date" /></rich:column>
                                            <rich:column><h:outputText value="Details" /></rich:column>
                                            <rich:column><h:outputText value="Enter By" /></rich:column>
                                            <rich:column><h:outputText value="EnterDate" /></rich:column>
                                            <rich:column><h:outputText value="Acno" /></rich:column>
                                            <rich:column><h:outputText value="SecDesc2" /></rich:column>
                                            <rich:column><h:outputText value="SecDesc3" /></rich:column>
                                            <rich:column><h:outputText value="Margin" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{item.sno}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.typeOfSec}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.secNature}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.secDes1}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.particular}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.lien}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.matValue}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.matDate}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.issueDate}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.details}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.enterBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.enterDate}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.acno}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.secDesc2}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.secDesc3}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.margin}"/></rich:column>
                                    <rich:column><h:outputText value="#{item.status}"/></rich:column>
                                </rich:dataTable>
                            </a4j:region>
                            <rich:datascroller align="left" for="DlTable" maxPages="40"/>
                        </h:panelGrid>
                        <h:panelGrid id="FooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnSave" tabindex="37" value="Save" disabled="#{DlAccountOpeningRegister.disableSave}" onclick="#{rich:component('savePanel')}.show()"/>
                                <a4j:commandButton id="btnRefresh" action="#{DlAccountOpeningRegister.resetValueDl}" tabindex="38" value="Refresh" reRender="lblMsg,leftPanel,Row12,a15"/>
                                <a4j:commandButton id="btnExit" value="Exit" tabindex="39" action="#{DlAccountOpeningRegister.exitForm}" reRender="lblMsg,leftPanel,Row12"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYesDl')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Save Data?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id = "btnYesDl" ajaxSingle="true" action="#{DlAccountOpeningRegister.saveAction}"
                                                           onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,btnSave,leftPanel,msgRow"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel"  id = "btnCancelDl" ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="exitPanel" autosized="true" width="200" onshow="#{rich:element('btnYesDlExit')}.focus();">
                    <f:facet name="header">
                       <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Exit?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id = "btnYesDlExit" ajaxSingle="true" actionListener="#{DlAccountOpeningRegister.clearModelPanel}" oncomplete="
                                                           #{rich:component('SecurityPanel')}.hide();#{rich:component('lienMarkingPanel')}.hide(),#{rich:component('accountStatus')}.hide(),
                                                           #{rich:element('ddInterestCode')}.focus() "
                                                           onclick="#{rich:component('exitPanel')}.hide();" reRender="outerPanel,lblMsg,btnSave,a1,leftPanel,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel" id = "btnNoDlExit"  ajaxSingle="true" onclick="#{rich:component('exitPanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>

                <%--THIS IS TD LIEN MARKING FORM JSP CODE--%>
                <rich:modalPanel id="lienMarkingPanel" top="true" height="450" width="1000" onshow="#{rich:element('txtAcNo')}.focus();">
                    <h:form>
                        <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                            <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                                <h:panelGroup id="a2" layout="block">
                                    <h:outputLabel styleClass="headerLabel" value="Date: "/>
                                    <h:outputText id="stxtDate" styleClass="headerLabel" value="#{DlAccountOpeningRegister.todayDate}"/>
                                </h:panelGroup>
                                <h:outputLabel styleClass="headerLabel" value="Term Deposite Lien Marking"/>
                                <h:panelGroup layout="block">
                                    <h:outputLabel styleClass="headerLabel" value="User: "/>
                                    <h:outputText id="stxtUser" styleClass="headerLabel" value="#{DlAccountOpeningRegister.userName}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                                <h:outputText id="errorMessage" styleClass="error" value="#{DlAccountOpeningRegister.errorMessage}"/>
                                <h:outputText id="message" styleClass="msg" value="#{DlAccountOpeningRegister.message}"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                <h:inputText id="txtAcNo" tabindex="1" size="20" maxlength="12" value="#{DlAccountOpeningRegister.oldacctNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{DlAccountOpeningRegister.customerDetail}" ajaxSingle="true" event="onblur" focus="ddLienMarking"
                                                 reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,newTdAcno" limitToList="true" />
                                </h:inputText>
                                    <h:outputText id="newTdAcno" styleClass="output" value="#{DlAccountOpeningRegister.acctNo}" style="color:green"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblHide1" value="" />
                                <h:outputLabel id="lblHide2" value="" />
                             
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" />
                                <h:outputText id="stxtCustName" styleClass="output" value="#{DlAccountOpeningRegister.custName}" style="color:purple;"/>
                                <h:outputLabel id="lblJtName" styleClass="label" value="Joint Name :" />
                                <h:outputText id="stxtJtName" styleClass="output" value="#{DlAccountOpeningRegister.jtName}" style="color:purple;"/>
                                <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" />
                                <h:outputText id="stxtOprMode" styleClass="output" value="#{DlAccountOpeningRegister.oprMode}" style="color:purple;"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{DlAccountOpeningRegister.gridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                                <rich:column><h:outputText value="R.T. No." /></rich:column>
                                                <rich:column><h:outputText value="Reciept No." /></rich:column>
                                                <rich:column><h:outputText value="Control No." /></rich:column>
                                                <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                                <rich:column><h:outputText value="TD Made Date" /></rich:column>
                                                <rich:column><h:outputText value="TD Date (wef)" /></rich:column>
                                                <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                                <rich:column><h:outputText value="Int Opt" /></rich:column>
                                                <rich:column><h:outputText value="ROI" /></rich:column>
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
                                        <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="#{dataItem.status}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.lien}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" actionListener="#{DlAccountOpeningRegister.fillValuesofGridInFields}"
                                                             reRender="a5,a6,message,errorMessage,gpFooter,table,taskList" >
                                                <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{DlAccountOpeningRegister.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{DlAccountOpeningRegister.currentRow}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20" />
                                </a4j:region>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                <h:outputLabel id="lblRecieptNo" styleClass="label" value="Reciept No. :" />
                                <h:outputText id="stxtRecieptNo" styleClass="output" value="#{DlAccountOpeningRegister.recieptNo}" style="color:purple;"/>
                                <h:outputLabel id="lblControlNo" styleClass="label" value="Control No. :" />
                                <h:outputText id="stxtControlNo" styleClass="output" value="#{DlAccountOpeningRegister.controlNo}" style="color:purple;"/>
                                <h:outputLabel id="lblPrinAmt" styleClass="label" value="Present Amount :" />
                                <h:outputText id="stxtPrinAmt" styleClass="output" value="#{DlAccountOpeningRegister.prinAmount}" style="color:purple;"/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                                <h:outputLabel id="lblLienStatus" styleClass="label" value="Lien Status :" />
                                <h:outputText id="stxtLienStatus" styleClass="output" value="#{DlAccountOpeningRegister.statusLien}" style="color:purple;"/>
                                <h:outputLabel id="lblLienMarking" styleClass="label" value="Lien Marking :" />
                                <h:selectOneListbox id="ddLienMarking" tabindex="3" styleClass="ddlist" value="#{DlAccountOpeningRegister.lienMarkOption}" size="1" style="width: 127px">
                                    <f:selectItems value="#{DlAccountOpeningRegister.lienMarkOptionList}" />
                                    <a4j:support  ajaxSingle="true" event="onblur" focus="txtDetails"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDetails" styleClass="label" value="Details :" />
                                <h:inputText id="txtDetails" tabindex="4" size="20" value="#{DlAccountOpeningRegister.detail}" onblur="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support  ajaxSingle="true" event="onblur"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnSave" tabindex="17" value="Ok" disabled="#{DlAccountOpeningRegister.flag1}" oncomplete="#{rich:component('savePanelInLien')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,DlTable,Row12" />
                                    <a4j:commandButton id="btnRefresh" tabindex="19" value="Refresh" ajaxSingle="true" action="#{DlAccountOpeningRegister.resetForm}" oncomplete="#{rich:element('btnSave')}.disabled = true;" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList" focus="txtAcNo"/>
                                    <a4j:commandButton id="btnExit" tabindex="20" value="Exit" onclick="#{rich:component('exitPanel')}.show()" reRender="message,errorMessage,savePanelInLien" />
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:form>
                    <rich:modalPanel id="savePanelInLien" autosized="true" width="200" onshow="#{rich:element('btnYesLien')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are You Sure To Select This Voucher No. ?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id = "btnYesLien" ajaxSingle="true" action="#{DlAccountOpeningRegister.saveDetail}"
                                                               oncomplete="#{rich:component('savePanelInLien')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,DlTable,Row12" />
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Cancel" id = "btnNoLien" ajaxSingle="true" onclick="#{rich:component('savePanelInLien')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </rich:modalPanel>
                <%--END OF TD LIEN MARKING FORM JSP CODE--%>
                <%--START OF SECURITY FORM JSP CODE--%>
                <rich:modalPanel id="SecurityPanel" height="470" width="1000" onshow="#{rich:element('ddTypeOfSecurity')}.focus();">
                    <a4j:form>
                        <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%" >
                            <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                                <h:panelGroup id="groupPanel2" layout="block">
                                    <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                                    <h:outputText id="stxtDate" styleClass="output" value="#{DlAccountOpeningRegister.todayDate}"/>
                                </h:panelGroup>
                                <h:outputLabel id="label2" styleClass="headerLabel" value="Security Details"/>
                                <h:panelGroup id="groupPanel3" layout="block">
                                    <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                                    <h:outputText id="stxtUser" styleClass="output" value="#{DlAccountOpeningRegister.userName}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                                    <h:outputLabel id="lblMsg" styleClass="error" value="#{DlAccountOpeningRegister.messageSec}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                                    <h:outputText id="stxtName" styleClass="output" value="#{DlAccountOpeningRegister.name}"/>
                                    <h:outputLabel id="lblAccount" styleClass="label" value="Account"/>
                                    <h:outputText id="stxtAccount" styleClass="output" value="#{DlAccountOpeningRegister.acno}"/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblTypeOfSecurity" styleClass="label" value="Type Of Security "><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddTypeOfSecurity" styleClass="ddlist" style="width:134px"  size="1" value="#{DlAccountOpeningRegister.secType}">
                                        <f:selectItems value="#{DlAccountOpeningRegister.typeOfSecurityList}"/>
                                        <a4j:support  actionListener="#{DlAccountOpeningRegister.changeLabel}"  ajaxSingle="true" event="onblur" focus="#{rich:clientId('calEstimationDate')}InputDate"
                                                      reRender="lblMsg,lblEstimationDate,lblRevalutionDate,lblValuationAmt,ddSecurityDesc1,ddSecurityDesc2,ddSecurityDesc3,btnSave"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblEstimationDate" styleClass="label" value="#{DlAccountOpeningRegister.estimationDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy"  id="calEstimationDate" value="#{DlAccountOpeningRegister.estimationDate}" >
                                        <a4j:support ajaxSingle="true" event="onchanged" focus="ddSecurityNature"/>
                                    </rich:calendar>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblSecurityNature" styleClass="label" value="Security Nature" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityNature" styleClass="ddlist"  style="width:134px" size="1" value="#{DlAccountOpeningRegister.secNature}">
                                        <f:selectItems value="#{DlAccountOpeningRegister.securityNatureList}"/>
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="#{rich:clientId('calRevalutionDate')}InputDate"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblRevalutionDate" styleClass="label" value="#{DlAccountOpeningRegister.revalutionDateLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calRevalutionDate"  value="#{DlAccountOpeningRegister.revalutionDate}" >
                                        <a4j:support ajaxSingle="true" event="onchanged" focus="ddSecurityDesc1"/>
                                    </rich:calendar>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblSecurityDesc1" styleClass="label" value="Security Desc (1)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityDesc1" styleClass="ddlist"  size="1" style="width:134px" value="#{DlAccountOpeningRegister.securityDesc1}">
                                        <f:selectItems value="#{DlAccountOpeningRegister.securityDesc1List}"/>
                                        <a4j:support  actionListener="#{DlAccountOpeningRegister.onChangeOFSecurityDesc1}" ajaxSingle="true" event="onblur"
                                                      reRender="ddSecurityDesc2,ddSecurityDesc3,lblMsg" focus="txtParticular"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblParticular" styleClass="label" value="Particular"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtParticular" styleClass="input" maxlength = "255"  style="width:134px" value="#{DlAccountOpeningRegister.particular}">
                                        <a4j:support  actionListener="#{DlAccountOpeningRegister.setRemarks}" ajaxSingle="true" event="onblur"
                                                      reRender="txtRemarks,lblMsg" focus="ddSecurityDesc2"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblSecurityDesc2" styleClass="label" value="Security Desc (2)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSecurityDesc2" styleClass="ddlist"  size="1" style="width:134px" value="#{DlAccountOpeningRegister.securityDesc2}">
                                        <f:selectItems value="#{DlAccountOpeningRegister.securityDesc2List}"/>
                                        <a4j:support  actionListener="#{DlAccountOpeningRegister.onChangeOFSecurityDesc2}" event="onblur"   ajaxSingle="true"
                                                      reRender="ddSecurityDesc3,ddSecurityDesc2,lblMsg" focus="txtValuationAmt"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblValuationAmt" styleClass="label" value="#{DlAccountOpeningRegister.valuationAmtLbl}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtValuationAmt" styleClass="input" style="width:134px" value="#{DlAccountOpeningRegister.valuationAmt}">
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="ddSecurityDesc3"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblSecurityDesc3" styleClass="label"  value="Nature Of Charges"/>
                                    <h:selectOneListbox id="ddSecurityDesc3" styleClass="ddlist"  disabled="#{DlAccountOpeningRegister.disableSecDec3}"  size="1" style="width:134px" value="#{DlAccountOpeningRegister.securityDesc3}">
                                        <f:selectItems value="#{DlAccountOpeningRegister.securityDesc3List}"/>
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="txtLienValue"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblLienValue" styleClass="label" value="Lien Value "><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtLienValue" styleClass="input" style="width:134px" value="#{DlAccountOpeningRegister.lienValue}">
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="ddStatusSec"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblStatusSec" styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddStatusSec" styleClass="ddlist"  style="width:134px" size="1" value="#{DlAccountOpeningRegister.status}" >
                                        <f:selectItems value="#{DlAccountOpeningRegister.statusList}"/>
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="txtMargin"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblMargin" styleClass="label" value="Margin(%)"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtMargin" styleClass="input" style="width:134px" value="#{DlAccountOpeningRegister.margin}">
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="txtOtherAC"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblOtherAC" styleClass="label" value="Other A/C "/>
                                    <h:inputText id="txtOtherAC" styleClass="input" style="width:134px" onblur="this.value = this.value.toUpperCase();" value="#{DlAccountOpeningRegister.otherAc}">
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="txtRemarks"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks "/>
                                    <h:inputText id="txtRemarks" styleClass="input" maxlength = "255" style="width:134px"  onblur="this.value = this.value.toUpperCase();" value="#{DlAccountOpeningRegister.remark}">
                                        <a4j:support  ajaxSingle="true" event="onblur" focus="btnSave1" reRender="btnSave1"/>
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="1" id="SecurityDetails"  width="100%" styleClass="row2">

                                <a4j:region>
                                    <rich:dataTable value ="#{DlAccountOpeningRegister.securityDetail}"
                                                    rowClasses="row1, row2" id = "SecurityDetailsTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>

                                                <rich:column colspan="16"></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                                <rich:column><h:outputText value="Maturity Value" /></rich:column>
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
                                                <rich:column><h:outputText value="Particulars" /></rich:column>

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
                                        <rich:column><h:outputText value="#{item.status}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.enteredBy}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.enteredDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.particular}"/></rich:column>
                                    </rich:dataTable>
                                </a4j:region>
                                <rich:datascroller align="left" for="SecurityDetailsTable" maxPages="40" />
                            </h:panelGrid>
                            <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanel">
                                    <a4j:commandButton id="btnSave1" value="Save">
                                        <a4j:support  action="#{DlAccountOpeningRegister.saveSecurityDetails}" event="onclick"
                                                      ajaxSingle="true" reRender="lblMsg,Row12,outerPanel,btnSave1"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnRefresh" value="Refresh">
                                        <a4j:support  action="#{DlAccountOpeningRegister.resetPage}" event="onclick" focus="btnExit"
                                                      ajaxSingle="true" reRender="lblMsg,leftPanel,SecurityDetails"/>
                                    </a4j:commandButton>
                                    <a4j:commandButton id="btnExit" value="Exit" onclick="#{rich:component('exitPanel')}.show()"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>

                <%--END OF SECURITY FORM JSP CODE--%>

                 <%--START OF ACCOUNT STATUS FORM JSP CODE--%>
                <rich:modalPanel id="accountStatus" top="true" height="550" width="1000" onshow="#{rich:element('txtAccountNumber')}.focus();">
                    <h:form>
                        <h:panelGrid bgcolor="#fff" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header" width="100%">
                                <h:panelGroup id="groupPanel" layout="block" >
                                    <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:" />
                                    <h:outputText id="stxtDate" styleClass="output" value="#{DlAccountOpeningRegister.todayDate}"/>
                                </h:panelGroup>
                                <h:outputLabel id="lblincident" styleClass="headerLabel" value="Account Status"/>
                                <h:panelGroup id="groupPanel2" layout="block">
                                    <h:outputLabel id="lbluser" styleClass="headerLabel" value="User: "/>
                                    <h:outputText id="stxtUser" styleClass="output" value="#{DlAccountOpeningRegister.userName}" />
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columns="1" id="d22" style="width:100%;text-align:center;" styleClass="row1"  >
                                <h:outputText id="stxtmessage" styleClass="output" value="#{DlAccountOpeningRegister.message}"  style="width:100%;color:red"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a9" width="100%">
                                <h:panelGrid columns="1" id="accountType" style="width:100%;text-align:center;" styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblAccountNumber" styleClass="headerLabel"  value="Account Number"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                </h:panelGrid>
                                <h:panelGrid columns="3" columnClasses="col7,col7" id="AccountNumber" style="width:100%;text-align:left;"  styleClass="row2" width="100%"  >
                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                        <h:inputText id="txtAccountNumber" styleClass="input" maxlength="12"style="width: 120px" value="#{DlAccountOpeningRegister.oldcode}" onkeyup="this.value = this.value.toUpperCase();">
                                            <a4j:support id="a4j2"  ajaxSingle="true" event="onblur"  reRender="stxtmessage,txtName,txtpStatus,a19,taskList,btnSave,a91,d91,txtRemarks,ddnStatus,stxtNewAcno" action="#{DlAccountOpeningRegister.custName}" />
                                        </h:inputText>
                                       <h:outputText id="stxtNewAcno" styleClass="output" value="#{DlAccountOpeningRegister.acnostatus}" style="color:green"/>
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col7" columns="2" id="c9" width="100%">
                                <h:panelGrid columns="1"  id="Name" style="width:100%;text-align:center;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblName" styleClass="headerLabel"  value="Name"  style="width:100%;text-align:left;"></h:outputLabel>
                                </h:panelGrid>
                                <h:panelGrid columns="1"  id="Name1" style="width:100%;text-align:left;"  styleClass="row1" width="100%"  >
                                    <h:outputText id="txtName" styleClass="output" value="#{DlAccountOpeningRegister.nameAcctStatus}" />
                                </h:panelGrid>
                            </h:panelGrid>



                            <h:panelGrid columnClasses="col7,col7" columns="2"  id="a91" width="100%">
                                <h:panelGrid columns="2" columnClasses="col7,col7" id="pStatus" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblpStatus" styleClass="headerLabel"  value="Present Status"  style="width:100%;text-align:left;"> </h:outputLabel>
                                    <h:outputText id="txtpStatus" styleClass="output" value="#{DlAccountOpeningRegister.pStatus}"/>
                                </h:panelGrid>

                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="nStatus"  style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblnStatus" styleClass="headerLabel"  value="New Status"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                        <h:selectOneListbox id="ddnStatus"  styleClass="ddlist" size="1" style="width: 100px"   value="#{DlAccountOpeningRegister.nStatus}">
                                            <f:selectItem itemValue="--Select--"/>
                                            <f:selectItem itemValue="Lien Marked"/>
                                            <a4j:support ajaxSingle="true" event="onchange" focus="ddlien" reRender="d91"/>
                                        </h:selectOneListbox>
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col7" columns="2" id="d91"  width="100%" >
                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="lien" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lbllien" styleClass="headerLabel"  value="Lien Account No."  style="width:100%;text-align:left;"></h:outputLabel>
                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                        <h:inputText id="txtlienAccountNumber" styleClass="input" style="width: 130px" value="#{DlAccountOpeningRegister.oldliencode}" disabled="true">
                                            <a4j:support ajaxSingle="true" event="onblur" focus="txtlamt" reRender="stxtNewLienNo" actionListener="#{DlAccountOpeningRegister.getNewLienAcNo}"/>
                                        </h:inputText>
                                        <h:outputText id="stxtNewLienNo" styleClass="output" value="#{DlAccountOpeningRegister.oldliencode}" style="color:green"/>
                                    </h:panelGroup>
                                </h:panelGrid>
                                <h:panelGrid columns="2" columnClasses="col7,col7" id="ll" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lbllamt" styleClass="headerLabel"  value="Lien Amount"  style="width:150%;text-align:left;"> </h:outputLabel>
                                    <h:inputText id="txtlamt" styleClass="input" value="#{DlAccountOpeningRegister.lienAmt}">
                                        <a4j:support ajaxSingle="true"event="onblur"  focus="ddwefDate" />
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a92" width="100%">
                                <h:panelGrid columns="3" columnClasses="col7,col7,col7" id="wefDate" style="height:30px;"  styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblwefDate" styleClass="headerLabel"  value="W.E.F.Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                    <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                        <rich:calendar id="ddwefDate" styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{DlAccountOpeningRegister.wefDate}" inputSize="10">
                                            <a4j:support ajaxSingle="true"  event="onchanged" focus="txtRemarks"/>
                                        </rich:calendar>
                                    </h:panelGroup>
                                </h:panelGrid>
                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="remarks" style="height:30px;"   styleClass="row1" width="100%"  >
                                    <h:outputLabel id="lblRemarks" styleClass="headerLabel"  value="Remarks"  style="width:100%;text-align:left;"></h:outputLabel>
                                    <h:inputText id="txtRemarks" styleClass="input" style="width: 200px" disabled="#{DlAccountOpeningRegister.fflag=='false'}" value="#{DlAccountOpeningRegister.remarks}">
                                        <a4j:support ajaxSingle="true" event="onblur" focus="ddReportDate"/>
                                    </h:inputText>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col7,col7" columns="2" id="a93" width="100%">

                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:outputLabel id="lblReportDate" styleClass="headerLabel"  value="Report Date"  style="width:100%;text-align:left;"><font class="required">*</font></h:outputLabel>
                                    <rich:calendar id="ddReportDate"  styleClass="rich-calendar-input" datePattern="dd/MM/yyyy" value="#{DlAccountOpeningRegister.reportDate}" inputSize="10">
                                        <a4j:support ajaxSingle="true"  event="onchanged" focus="includeFl"/>
                                    </rich:calendar>
                                </h:panelGrid>
                                <h:panelGrid columns="2" columnClasses="col7,col7,col7" id="ReportDate1" style="height:30px;"  styleClass="row2" width="100%"  >
                                    <h:selectBooleanCheckbox id="includeFl" >
                                        <a4j:support ajaxSingle="true" event="onclick" focus="btnReport">
                                            <a4j:actionparam name="rowId" value="#{result.rowId}" />
                                        </a4j:support>
                                        <h:outputLabel id="lblnForAllStatusReport" styleClass="headerLabel"  value="For All Status Report"  style="width:100%;text-align:left;"></h:outputLabel>
                                    </h:selectBooleanCheckbox>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="a19" style="height:auto;" styleClass="row1" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{DlAccountOpeningRegister.incis}" var="dataItem"
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
                                    <a4j:commandButton id="btnSave" ajaxSingle="true" focus="btnRefresh" value="Save" disabled="#{DlAccountOpeningRegister.fflag=='false'}" reRender="btnSave,stxtmessage,txtpStatus,ddwefDate,DlTable,Row12,mainPanel,ddInterestCode"  action="#{DlAccountOpeningRegister.save}"/>
                                    <a4j:commandButton id="btnRefresh" focus="btnExit" value="Refresh" ajaxSingle="true" reRender="stxtmessage,txtAccountNumber,txtName,txtlienAccountNumber,txtlamt,nStatus,txtpStatus,txtRemarks,taskList,btnSave,a19,ddwefDate,ddReportDate,stxtNewLienNo,stxtNewAcno" action="#{DlAccountOpeningRegister.reFresh}"/>
                                    <a4j:commandButton id="btnExit" value="Exit" onclick="#{rich:component('exitPanel')}.show()"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:form>
                </rich:modalPanel>
                <%--END OF ACCOUNT STATUS FORM JSP CODE--%>
            </a4j:form>

        </body>
    </html>
</f:view>

