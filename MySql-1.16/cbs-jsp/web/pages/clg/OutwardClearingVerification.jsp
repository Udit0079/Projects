<%--
    Document   : OutwardClearingVerification
    Created on : Nov 22, 2010, 11:49:29 AM
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
            <title>Outward Clearing Verification</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{OutwardClearingVerification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Outward Clearing Verification"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{OutwardClearingVerification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{OutwardClearingVerification.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{OutwardClearingVerification.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblIndication" styleClass="headerLabel" value="Select The Clearing Register" style="color:green"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblUserWise" styleClass="label" value="User Wise" />
                            <h:selectBooleanCheckbox id="chkUserWise" tabindex="3" valueChangeListener="#{OutwardClearingVerification.selectUserChkBoxValue}" value="#{OutwardClearingVerification.userChk}">
                                <a4j:support event="onclick" ajaxSingle="true"
                                             reRender="a6" data="#{OutwardClearingVerification.userChk}"/>
                            </h:selectBooleanCheckbox>

                            <h:selectOneListbox id="ddUser" tabindex="4" styleClass="ddlist" value="#{OutwardClearingVerification.user}" disabled="#{OutwardClearingVerification.userDisFlag}" size="1" style="width: 102px">
                                <f:selectItems value="#{OutwardClearingVerification.userList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblClearingMode" styleClass="label" value="Clearing Mode :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddClearingMode" tabindex="1" styleClass="ddlist"  value="#{OutwardClearingVerification.clearingMode}" size="1" style="width: 120px">
                                <f:selectItems value="#{OutwardClearingVerification.clearingModeList}" />
                                <a4j:support actionListener="#{OutwardClearingVerification.registerDate}" event="onblur"
                                             reRender="a6,a7,a8,a9,a10,a11,message,errorMessage,lpg,gpFooter,table,taskList" limitToList="true" focus="ddRegDate" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblTerminalWise" styleClass="label" value="Terminal Wise" />
                            <h:selectBooleanCheckbox id="chkTerminalWise" tabindex="5" valueChangeListener="#{OutwardClearingVerification.selectTerminalChkBoxValue}" value="#{OutwardClearingVerification.terminalChk}">
                                <a4j:support event="onclick" ajaxSingle="true"
                                             reRender="a9" data="#{OutwardClearingVerification.terminalChk}"/>
                            </h:selectBooleanCheckbox>

                            <h:selectOneListbox id="ddTerminal" tabindex="6" styleClass="ddlist" value="#{OutwardClearingVerification.terminal}" disabled="#{OutwardClearingVerification.terminalDisFlag}" size="1" style="width: 102px">
                                <f:selectItems value="#{OutwardClearingVerification.terminalList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblRegDate" styleClass="label" value="Register Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRegDate" tabindex="2" styleClass="ddlist"  value="#{OutwardClearingVerification.regDate}" size="1" style="width: 120px">
                                <f:selectItems value="#{OutwardClearingVerification.regDateList}" />
                                <a4j:support actionListener="#{OutwardClearingVerification.userCombo}" event="onblur"
                                             reRender="a6,a7,a8,a9,a10,a11,message,errorMessage,lpg,gpFooter,table,taskList" limitToList="true" focus="btnLoad" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblStatusWise" styleClass="label" value="Status Wise" />
                            <h:selectBooleanCheckbox id="chkStatusWise" tabindex="7" valueChangeListener="#{OutwardClearingVerification.selectStatusChkBoxValue}" value="#{OutwardClearingVerification.statusChk}">
                                <a4j:support event="onclick" ajaxSingle="true"
                                             reRender="a10" data="#{OutwardClearingVerification.statusChk}"/>
                            </h:selectBooleanCheckbox>

                            <h:selectOneListbox id="ddStatus" tabindex="8" styleClass="ddlist" value="#{OutwardClearingVerification.status}" disabled="#{OutwardClearingVerification.statusDisFlag}" size="1" style="width: 102px">
                                <f:selectItems value="#{OutwardClearingVerification.statusList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;" styleClass="row2">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel id="lblBy" styleClass="label" value="By :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddBy" tabindex="9" styleClass="ddlist"  value="#{OutwardClearingVerification.by}" size="1" style="width: 120px">
                                <f:selectItems value="#{OutwardClearingVerification.byList}" />
                            </h:selectOneListbox>
                            <a4j:commandButton id="btnLoad" tabindex="10" value="Load" disabled="#{OutwardClearingVerification.flag3}" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList" actionListener="#{OutwardClearingVerification.gridLoad}" style=""/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{OutwardClearingVerification.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="20"><h:outputText value="Details (Please double click on Select column to verify or unverify the instruments.)" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Cust Name" /></rich:column>
                                        <rich:column><h:outputText value="Jt Name 1" /></rich:column>
                                        <rich:column><h:outputText value="Jt Name 2" /></rich:column>
                                        <rich:column><h:outputText value="Jt Name 3" /></rich:column>
                                        <rich:column><h:outputText value="Jt Name 4" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Inst No." /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="MICR Code" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Drw A/C. No." /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Drw Name" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column ><h:outputText value="Voucher No" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acctno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName1}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName2}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName3}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.jtName4}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.amount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAdd}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.micrCode}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.drwAcctNo}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.drwName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.vtot}" /></rich:column>
                                <rich:column style="text-align:center;width:40px;cursor:pointer;">
                                    <h:outputText value="#{dataItem.checkFlag}"/>
                                    <a4j:support action="#{OutwardClearingVerification.fillValuesofGridInFields}" oncomplete="if(#{dataItem.status == 'VERIFIED'}){#{rich:element('btnUnverify')}.focus();}else{#{rich:element('btnVerify')}.focus();}"
                                                 event="ondblclick" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{OutwardClearingVerification.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{OutwardClearingVerification.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7" columns="3" id="a12" width="100%" style="width:100%;text-align:center;" styleClass="row1">
                        <h:outputLabel id="lblPendingStatus" styleClass="headerLabel" value="Pending Status" style="color:purple"/>
                        <h:outputLabel id="lblVerifiedStatus" styleClass="headerLabel" value="Verified Status" style="color:purple"/>
                        <h:outputLabel id="lblNetStatus" styleClass="headerLabel" value="Net Status" style="color:purple"/>
                        <h:outputText id="stxtPendingStatus1" styleClass="output" value="#{OutwardClearingVerification.pendingStatus1}"/>
                        <h:outputText id="stxtVerifiedStatus1" styleClass="output" value="#{OutwardClearingVerification.verifiedStatus1}"/>
                        <h:outputText id="stxtNetStatus1" styleClass="output" value="#{OutwardClearingVerification.netStatus1}"/>
                        <h:outputText id="stxtPendingStatus2" styleClass="output" value="#{OutwardClearingVerification.pendingStatus2}"/>
                        <h:outputText id="stxtVerifiedStatus2" styleClass="output" value="#{OutwardClearingVerification.verifiedStatus2}"/>
                        <h:outputText id="stxtNetStatus2" styleClass="output" value="#{OutwardClearingVerification.netStatus2}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnVerify" tabindex="11" value="Verify" disabled="#{OutwardClearingVerification.flag2}" oncomplete="#{rich:component('verifyPanel')}.show()" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList" focus=""/>
                            <a4j:commandButton id="btnUnverify" tabindex="12" value="Unverify" disabled="#{OutwardClearingVerification.flag1}" oncomplete="#{rich:component('unverifyPanel')}.show()" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList"/>
                            <a4j:commandButton id="btnRefresh" tabindex="13" value="Refresh" action="#{OutwardClearingVerification.resetForm}" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList" focus="ddClearingMode"/>
                            <a4j:commandButton id="btnExit" tabindex="14" value="Exit" action="#{OutwardClearingVerification.exitBtnAction}" reRender="message,errorMessage"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="verifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesVerify')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to verify ? "/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesVerify" ajaxSingle="true" action="#{OutwardClearingVerification.verifyInstruments}"
                                                       oncomplete="#{rich:component('verifyPanel')}.hide();" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList" focus="btnLoad"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoVerify" onclick="#{rich:component('verifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="unverifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesUnverify')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to unverify ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesUnverify" ajaxSingle="true" action="#{OutwardClearingVerification.unverifyInstruments}"
                                                       oncomplete="#{rich:component('unverifyPanel')}.hide();" reRender="a6,a7,a8,a9,a10,a12,message,errorMessage,lpg,gpFooter,table,taskList" focus="btnLoad"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Cancel" id="btnNoUnverify" onclick="#{rich:component('unverifyPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
