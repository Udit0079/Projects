<!--<?xml version="1.0" encoding="UTF-8"?> -->
<!--
    Document   : AccountEditTD
    Created on : May 07, 2010, 5:36:19 PM
    Author     : Vipin Kumar Mandal
-->
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Account Edit TD</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountEditTD.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Account Edit TD"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{AccountEditTD.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" width="100%" columns="1" styleClass="row2" style="text-align:center;">
                        <h:outputText id="stxtError" styleClass="error" value="#{AccountEditTD.errorMsg}"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel5" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblAccountNo" styleClass="output" value="Account No."/>
                        <h:panelGroup layout="block">
                            <h:inputText id="accNo" styleClass="input" value="#{AccountEditTD.custOldAccNo}"  maxlength="#{AccountEditTD.acNoMaxLen}" style="width:100px">
                                <a4j:support actionListener="#{AccountEditTD.getCustDetails}" event="onblur"  oncomplete="if(#{AccountEditTD.flag1=='true'}){#{rich:element('gridPanel7')}.show();} else if(#{AccountEditTD.flag1=='false'}){#{rich:element('gridPanel7')}.hide();}" 
                                             reRender="ddTDSApplicable,ddRcptNo,txtAccountNumber,tblDocDetails,txtInterestToAcNo,otxtnewAc,txtCustomerName,txtFatherHusbandName,txtDocumentDetails,txtIntroducerACNo,ddOperationMode,txtNominee,
                                             txtRelationship,txtJtName1,txtJtName2,txtJtName3,txtJtName4,txtGuradianName,txtACInstruction,stxtAcOpnDt,stxtError,txtNomiName,txtNomAddress,txtNomiRelation,ddNomiMinor,calNomiDob,txtNomiAge,txtJtName1Code,txtJtName2Code,txtJtName3Code,
                                             txtJtName4Code,ddCustomerNature,ddSupportingDoc,ddOrganizationType,ddAutoRenew,ddAutoPay,
                                               txtAutoPayAc,stxtAutoPayAcName,txtInterestToAcNo,otxtnewAc,ddAcctCategType,lblOccupationId" focus="#{AccountEditTD.acNoFlag}"/>
                            </h:inputText>
                            <h:outputText id="txtAccountNumber" styleClass="output"  value="#{AccountEditTD.accountNumber}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCustomerName"  styleClass="label"value="Name"/>
                        <h:outputText id="txtCustomerName" styleClass="output"  value="#{AccountEditTD.custName}"/>
                        <h:outputLabel id="lblFatherHusbandName" value="Father's Name" styleClass="label"/>
                        <h:outputText id="txtFatherHusbandName" styleClass="output"  value="#{AccountEditTD.fatherName}"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel7" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblTDSApplicable" styleClass="label"value="TDS Applicable"/>
                        <h:selectOneListbox id="ddTDSApplicable" styleClass="ddlist" size="1" style="width:78px;" value="#{AccountEditTD.tdsApplicable}" disabled="#{AccountEditTD.tdsApplicableDisable}">
                            <f:selectItems value="#{AccountEditTD.tdsApplicableList}"/>
                            <a4j:support actionListener="#{AccountEditTD.supportingDocVisibility}" event="onblur" reRender="gridPanel7" 
                                         oncomplete="if(#{AccountEditTD.flag1=='true'}){#{rich:element('lblSupportingDoc')}.show();
                                         #{rich:element('ddSupportingDoc')}.show(); #{rich:element('ddSupportingDoc')}.focus();} 
                                         else if(#{AccountEditTD.flag1=='false'}){#{rich:element('lblSupportingDoc')}.hide();#{rich:element('ddSupportingDoc')}.hide();
                                         #{rich:element('ddOperationMode')}.focus();}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblSupportingDoc" styleClass="output" value="Supporting Doc."/>
                        <h:selectOneListbox id="ddSupportingDoc" styleClass="ddlist" size="1" style="width:78px;" value="#{AccountEditTD.tdsDetails}">
                            <f:selectItems value="#{AccountEditTD.supportDocList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="label1" styleClass="label" value="A/c Opening Date"/>
                        <h:outputText id="stxtAcOpnDt" styleClass="output" value="#{AccountEditTD.accOpenDate}"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel16" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblOperationMode" styleClass="label" value="Operation Mode"/>
                        <h:selectOneListbox id="ddOperationMode" styleClass="ddlist" size="1" style="width:120px;" value="#{AccountEditTD.opMode}">
                            <f:selectItems value="#{AccountEditTD.opModeList}"/>
                            <a4j:support actionListener="#{AccountEditTD.onblurOperMode}" event="onblur" reRender="txtJtName1Code,txtJtName2Code,txtJtName3Code,txtJtName4Code"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCustomerNature" styleClass="label" value="Customer Nature"/>
                        <h:selectOneListbox id="ddCustomerNature" styleClass="ddlist" size="1" style="width:78px;" value="#{AccountEditTD.custNature}">
                            <f:selectItems value="#{AccountEditTD.custNatureList}"/>
                        </h:selectOneListbox>
                        <%--
                        <h:outputLabel id="lblOrganizationType"  styleClass="label" value="Occupation"/>
                        <h:selectOneListbox id="ddOrganizationType" styleClass="ddlist" style="width:120px;" size="1" value="#{AccountEditTD.occupation}">
                            <f:selectItems value="#{AccountEditTD.occupationList}"/>
                        </h:selectOneListbox>
                         --%>
                        <%-- Added by Manish Kumar --%>
                            <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation"></h:outputLabel>
                            <h:outputLabel id="lblOccupationId" styleClass="label" value="#{AccountEditTD.occupationDesc}"></h:outputLabel>
                        <%-- --%>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanelh" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblIntroducerACNo" styleClass="label" value="Introducer A/C No."/>
                        <h:panelGroup>
                            <h:inputText id="txtIntroducerACNo" maxlength="#{AccountEditTD.acNoMaxLen}" styleClass="input"  value="#{AccountEditTD.introOldAccNo}">
                                <a4j:support event="onblur" action="#{AccountEditTD.getNewIntroAc}" reRender="stxtIntroAcno,stxtError" />
                            </h:inputText>
                            <h:outputText id="stxtIntroAcno" value="#{AccountEditTD.introAccNo}" styleClass="output" />
                        </h:panelGroup>
                        <h:outputLabel id="lblGuradianName" styleClass="label" value="Guradian Name"/>
                        <h:inputText id="txtGuradianName" maxlength="40"   styleClass="input" value="#{AccountEditTD.guardianName}"onkeydown="this.value=this.value.toUpperCase();">
                            <a4j:support actionListener="#{AccountEditTD.onblurGuardianName}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                        <h:outputLabel id="lblRcptNo" styleClass="label" value="Voucher No (Receipt No.)"/>
                        <h:selectOneListbox id="ddRcptNo"  size="1" value="#{AccountEditTD.receiptNo}" styleClass="ddlist" style="width:120px;">
                            <f:selectItems value="#{AccountEditTD.receiptNoList}"/>
                            <a4j:support event="onblur" action="#{AccountEditTD.receiptProcess}" reRender="txtInterestToAcNo,otxtnewAc,ddAutoRenew,ddAutoPay,txtAutoPayAc"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel25" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblNomiName" styleClass="label" value="Nominee Name"/>
                        <h:inputText id="txtNomiName" maxlength="40"  styleClass="input" value="#{AccountEditTD.nomiName}"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblNomAddress" styleClass="label" value="Nominee Address"/>
                        <h:inputText id="txtNomAddress" maxlength="40" styleClass="input"  value="#{AccountEditTD.nomiAddress}"onkeydown="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="lblNomiRelation" styleClass="label" value="Nominee Relation"/>
                        <h:inputText id="txtNomiRelation" maxlength="15" styleClass="input"  value="#{AccountEditTD.nomiRelation}"onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>

                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel27" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblNomiMinor" styleClass="label" value="Is Nominee Minor"/>
                        <h:selectOneListbox id="ddNomiMinor" styleClass="ddlist" size="1" style="width:78px;" value="#{AccountEditTD.nomiMinor}">
                            <f:selectItems value="#{AccountEditTD.nomiMinorList}"/>
                            <a4j:support actionListener="#{AccountEditTD.onBlurNomineeMinor}" reRender="lblNomiDob,calNomiDob,lblNomiAge,txtNomiAge"event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblNomiDob" styleClass="label" value="Nominee DOB" />
                        <rich:calendar id="calNomiDob" disabled="#{AccountEditTD.flag3}"datePattern="dd/MM/yyyy"  value="#{AccountEditTD.nomiDob}" inputSize="10">
                            <a4j:support actionListener="#{AccountEditTD.onblurNomDob}" event="onchanged" reRender="stxtError"/>
                        </rich:calendar>
                        <h:outputLabel id="lblNomiAge" styleClass="label" value="Nominee Age" />
                        <h:inputText id="txtNomiAge" disabled="#{AccountEditTD.flag2}" styleClass="input"  value="#{AccountEditTD.nomiAge}" style="width:70px"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col1,col4,col1,col4,col1,col4" id="gridPanel28" style="height:30px;" styleClass="row2" width="100%">
                      <h:outputLabel id="lblAcctCateg"  styleClass="label" value="Account Category"/>
                        <h:selectOneListbox id="ddAcctCategType" styleClass="ddlist" style="width:120px;" size="1" value="#{AccountEditTD.actCategory}">
                            <f:selectItems value="#{AccountEditTD.actCategoryList}"/>
                        </h:selectOneListbox>
                      <h:outputLabel/>
                      <h:outputLabel/>
                       <h:outputLabel/>
                      <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="ee" width="100%">
                        <h:panelGrid  columns="1" id="ee2" width="100%">
                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row2" width="100%" >
                                <h:outputLabel id="lblJtName1" styleClass="label" value="Jt Name1"/>
                                <h:panelGroup id="panelGrpJtName1" layout="block">
                                    <h:inputText id="txtJtName1Code"  disabled="#{AccountEditTD.flag4}" styleClass="input"  value="#{AccountEditTD.jtName1Code}" style="width:70px">
                                        <a4j:support actionListener="#{AccountEditTD.jointName1OnBlur}" event="onblur" reRender="txtJtName1,stxtError"/>
                                    </h:inputText>
                                    <h:outputText id="txtJtName1" styleClass="output" value="#{AccountEditTD.jtName1}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row1" width="100%" >
                                <h:outputLabel id="lblJtName2" styleClass="label" value="Jt Name2"/>
                                <h:panelGroup id="panelGrpJtName2" layout="block">
                                    <h:inputText id="txtJtName2Code" styleClass="input"  disabled="#{AccountEditTD.flag5}"value="#{AccountEditTD.jtName2Code}" style="width:70px">
                                        <a4j:support actionListener="#{AccountEditTD.jointName2OnBlur}" event="onblur" reRender="txtJtName2,stxtError"/>
                                    </h:inputText>
                                    <h:outputText id="txtJtName2" styleClass="output" value="#{AccountEditTD.jtName2}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row2" width="100%" >
                                <h:outputLabel id="lblJtName3" styleClass="label" value="Jt Name3"/>
                                <h:panelGroup id="panelGrpJtName3" layout="block">
                                    <h:inputText id="txtJtName3Code" styleClass="input"  disabled="#{AccountEditTD.flag6}" value="#{AccountEditTD.jtName3Code}" style="width:70px">
                                        <a4j:support actionListener="#{AccountEditTD.jointName3OnBlur}" event="onblur" reRender="txtJtName3,stxtError"/>
                                    </h:inputText>
                                    <h:outputText id="txtJtName3" styleClass="output" value="#{AccountEditTD.jtName3}"/>
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row1" width="100%" >
                                <h:outputLabel id="lblJtName4" styleClass="label" value="Jt Name4"/>
                                <h:panelGroup id="panelGrpJtName4" layout="block">
                                    <h:inputText id="txtJtName4Code" styleClass="input" disabled="#{AccountEditTD.flag7}"  value="#{AccountEditTD.jtName4Code}" style="width:70px">
                                        <a4j:support actionListener="#{AccountEditTD.jointName4OnBlur}" event="onblur" reRender="txtJtName4,stxtError"/>
                                    </h:inputText>
                                    <h:outputText id="txtJtName4" styleClass="output" value="#{AccountEditTD.jtName4}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row2" width="100%" >
                                <h:outputLabel id="lblInterestToAcNo" styleClass="label" value="Interest to A/c No."/>
                                <h:panelGroup layout="block">
                                    <h:inputText id="txtInterestToAcNo" maxlength="#{AccountEditTD.acNoMaxLen}"  styleClass="input" value="#{AccountEditTD.intToOldAccNo}" 
                                                 onkeyup="this.value=this.value.toUpperCase();" disabled="#{AccountEditTD.intToAccNoDisable}" style="width:100px">
                                        <a4j:support actionListener="#{AccountEditTD.intoToAcnoOnBlur}" event="onblur" reRender="gridPanelk,stxtError,otxtnewAc" focus="lblACInstruction"/>
                                    </h:inputText>
                                    <h:outputText id="otxtnewAc" styleClass="output" value="#{AccountEditTD.intToAccNo}" style="color:green"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7" columns="2" style="height:30px;"  styleClass="row1" width="100%" >
                                <h:outputLabel id="lblACInstruction" styleClass="label" value="A/C Instruction"/>
                                <h:inputText id="txtACInstruction" maxlength="40" styleClass="input"  value="#{AccountEditTD.instruction}" onkeydown="this.value=this.value.toUpperCase();"/>
                            </h:panelGrid>
                            <h:panelGrid id = "autoRenewAcGrid" columnClasses="col2,col7" columns="2" style="height:30px;display:#{AccountEditTD.autoRenewVar}"  styleClass="row2" width="100%" >
                                <h:outputLabel id="lblAutoRenew" styleClass="label" value="Auto Renew"/>
                                <h:selectOneListbox id="ddAutoRenew" value="#{AccountEditTD.autoRenew}" styleClass="ddlist"  size="1">
                                    <f:selectItems value="#{AccountEditTD.autoRenewList}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid id = "autoPayAcGrid" columnClasses="col13,col13,col13,col13" columns="4" style="height:30px;display:#{AccountEditTD.autoPaymentVar}"  styleClass="row1" width="100%" >
                                <h:outputLabel id="lblAutoPay" styleClass="label" value="Auto Payment" />
                                <h:selectOneListbox id="ddAutoPay" value="#{AccountEditTD.autoPay}" styleClass="ddlist" size="1">
                                    <f:selectItems value="#{AccountEditTD.autoPayList}" />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblAutoPayAc" styleClass="label" value="Auto Payment A/c"/>
                                <h:panelGroup>
                                    <h:inputText id="txtAutoPayAc" styleClass="input" value="#{AccountEditTD.paidAcno}" maxlength="12" size="12">
                                        <a4j:support event="onblur" action="#{AccountEditTD.getCustomerName}" reRender="stxtAutoPayAcName,stxtError"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAutoPayAcName" styleClass="output" value="#{AccountEditTD.paidAcnoName}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="eee" width="100%">
                            <h:panelGrid columnClasses="col2,col7" columns="2" id="gridPanelf" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblDocumentName" value="Document Name" styleClass="label"/>
                                <h:selectOneListbox id="ddDocumentName" styleClass="ddlist" size="1" style="width:120px;" value="#{AccountEditTD.docName}">
                                    <f:selectItems value="#{AccountEditTD.docNameList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7" columns="2" id="gridPanelg" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblDocumentDetails" value="Document Details" styleClass="label"/>
                                <h:inputText id="txtDocumentDetails" maxlength="50" styleClass="input"  value="#{AccountEditTD.docDetails}"onkeydown="this.value=this.value.toUpperCase();"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPaneli" style="height:130px;" styleClass="row2" width="100%">
                                <rich:dataTable  value="#{AccountEditTD.accEditGridList}" id="tblDocDetails" width="100%" var="dataItem" rowClasses="gridrow1, gridrow2" columnsWidth="100"
                                                 rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="3"></rich:column>
                                            <rich:column breakBefore="true"> <h:outputText value="Code" /> </rich:column>
                                            <rich:column><h:outputText value="Description" /></rich:column>
                                            <rich:column><h:outputText value="Details" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.code}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.docDesc}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.docDetal}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller  align="left" for="tblDocDetails" />
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>    
                    <h:panelGrid columns="1" id="gridPanel2" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center; "/>
                        <h:panelGroup id="a100" layout="block" style="width:100%;text-align:center; ">
                            <a4j:commandButton action="#{AccountEditTD.updateAcctEditTd}" id="btnUpdate" value="Update" reRender="ddDocumentName,
                                               ddCustomerNature,ddSupportingDoc,accNo,stxtError,ddTDSApplicable,ddRcptNo,txtAccountNumber,tblDocDetails,
                                               txtInterestToAcNo,txtCustomerName,txtFatherHusbandName,txtDocumentDetails,
                                               txtIntroducerACNo,ddOperationMode,txtNominee,txtRelationship,txtJtName1,
                                               txtJtName2,txtJtName3,txtJtName4,txtGuradianName,txtACInstruction,stxtAcOpnDt,
                                               ddOrganizationType,tblDocDetails,txtJtName1Code,txtJtName2Code,txtJtName3Code,
                                               txtJtName4Code,txtNomiName,txtNomAddress,txtNomiRelation,ddNomiMinor,calNomiDob,
                                               txtNomiAge,otxtnewAc,stxtIntroAcno,ddAutoRenew,ddAutoPay,
                                               txtAutoPayAc,stxtAutoPayAcName,autoPayAcGrid,autoRenewAcGrid,lblOccupationId"/>
                            <a4j:commandButton id="btnRefresh"value="Refresh" action="#{AccountEditTD.refreshButton}" reRender="ddDocumentName,
                                               ddCustomerNature,ddSupportingDoc,accNo,stxtError,ddTDSApplicable,ddRcptNo,txtAccountNumber,
                                               tblDocDetails,txtInterestToAcNo,txtCustomerName,txtFatherHusbandName,txtDocumentDetails,
                                               txtIntroducerACNo,ddOperationMode,txtNominee,txtRelationship,txtJtName1,txtJtName2,txtJtName3,
                                               txtJtName4,txtGuradianName,txtACInstruction,stxtAcOpnDt,ddOrganizationType,tblDocDetails,
                                               txtJtName1Code,txtJtName2Code,txtJtName3Code,txtJtName4Code,txtNomiName,txtNomAddress,
                                               txtNomiRelation,ddNomiMinor,calNomiDob,txtNomiAge,otxtnewAc,stxtIntroAcno,lblAutoRenew,ddAutoRenew,lblAutoPay,ddAutoPay,lblAutoPayAc,
                                               txtAutoPayAc,stxtAutoPayAcName,autoPayAcGrid,autoRenewAcGrid,ddAcctCategType,lblOccupationId"/>
                            <a4j:commandButton id="btnExit"value="Exit" action="#{AccountEditTD.exitBtnAction}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

