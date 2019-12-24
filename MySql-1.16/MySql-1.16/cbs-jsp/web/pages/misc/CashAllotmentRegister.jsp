<%-- 
    Document   : CashAllotmentRegister
    Created on : Aug 27, 2011, 5:09:44 PM
    Author     : ROHIT KRISHNA
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
            <title>Cash Allotment Register</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <a4j:keepAlive beanName="CashAllotmentRegister"/>
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CashAllotmentRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cash Allotment Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CashAllotmentRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{CashAllotmentRegister.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{CashAllotmentRegister.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="a5" styleClass="row1" columns="2" style="width:100%;height:30px;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel styleClass="headerLabel" value="Today's Date Is : "/>
                            <h:outputText id="stxtTodayDt" styleClass="headerLabel" style="color:purple;" value="#{CashAllotmentRegister.todayDate}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="a6" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                        <h:outputLabel id="lblActiveCashier" value="Active Cashier Name :" styleClass="label"/>
                        <h:selectOneListbox id="ddActiveCashier" tabindex="1" styleClass="ddlist"  value="#{CashAllotmentRegister.activeCashier}" size="1" style="width: 122px">
                            <f:selectItems value="#{CashAllotmentRegister.activeCashierList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblOpeningBalance" value="Opening Balance :" styleClass="label"/>
                        <h:inputText id="txtOpeningBalance" tabindex="2" value="#{CashAllotmentRegister.openingBal}" styleClass="input" style="width:120px">
                            <a4j:support event="onblur" action="#{CashAllotmentRegister.openingBalOnBlur}" focus="txtDenoValue" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="a7" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                        <h:outputLabel id="lblDenomination" value="Denomination :" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtDenoValue" tabindex="3" value="#{CashAllotmentRegister.denoValue}" styleClass="input" style="width:120px" disabled="#{CashAllotmentRegister.disableDenoNo}"/> &nbsp;
                            <h:outputLabel value="Rs." styleClass="label"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblNoOfNotes" value="No. of Notes / Coins :" styleClass="label"/>
                        <h:inputText id="txtDenoNo" tabindex="4" value="#{CashAllotmentRegister.denoNo}" styleClass="input" style="width:120px" disabled="#{CashAllotmentRegister.disableDenoNo}">
                            <a4j:support event="onblur" action="#{CashAllotmentRegister.onBlurDenominationNo}" focus="txtDenoValue" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="a8" columnClasses="vtop" columns="1" style="height:auto;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable id="denominationTable" rows="20" value="#{CashAllotmentRegister.denominationTable}" var="dataItem" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Denomination Detail" /></rich:column>
                                        <rich:column width="30%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                        <rich:column width="25%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                        <rich:column width="30%"><h:outputText value="Amount"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="Update"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right">
                                    <h:outputText value="#{dataItem.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right"><h:outputText value="#{dataItem.denoNo}"/></rich:column>
                                <rich:column style="text-align:right">
                                    <h:outputText value="#{dataItem.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="updateLink" action="#{CashAllotmentRegister.setDenominationRowValues}" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9" ajaxSingle="true" focus="txtDenoValue">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{CashAllotmentRegister.currentDenominationItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{CashAllotmentRegister.currentIDenominationRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="denominationTable" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="a9" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                        <h:outputLabel id="lblTotalAmt" value="Total Amount :" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtTotalAmount" value="#{CashAllotmentRegister.totalAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                            <h:outputLabel value="Rs." styleClass="label"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCurrencyAmt" value="Currency Amount :" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtTotalCurrencyAmount" value="#{CashAllotmentRegister.currencyAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                            <h:outputLabel value="Rs." styleClass="label"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnRecieve" tabindex="5" value="Recieve" disabled="#{CashAllotmentRegister.disableReveiveButton}" oncomplete="#{rich:component('recievePanel')}.show()" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{CashAllotmentRegister.refreshForm}" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9" />
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{CashAllotmentRegister.exitBtnAction}" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="recievePanel" autosized="true" width="300" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure that you have recieved the cash ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{CashAllotmentRegister.cashRecievedBtn}"
                                                       oncomplete="#{rich:component('recievePanel')}.hide();" reRender="message,errorMessage,lpg,denominationTable,gpFooter,a5,a6,a7,a8,a9" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('recievePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
