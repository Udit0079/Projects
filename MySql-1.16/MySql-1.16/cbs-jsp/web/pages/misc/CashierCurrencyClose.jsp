<%-- 
    Document   : CashierCurrencyClose
    Created on : Sep 1, 2011, 12:44:40 PM
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
            <title>Cashier Currency Close</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <a4j:keepAlive beanName="CashierCurrencyClose"/>
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CashierCurrencyClose.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cashier Currency Close"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CashierCurrencyClose.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{CashierCurrencyClose.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{CashierCurrencyClose.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblActiveCashier" styleClass="label" value="Active Cashier :" ><font class="required" color="red">*</font></h:outputLabel>
                        <a4j:region id="userIdRegion">
                            <h:selectOneListbox id="ddActiveCashier" tabindex="1" styleClass="ddlist"  value="#{CashierCurrencyClose.activeCashier}" size="1" style="width: 122px">
                                <f:selectItems value="#{CashierCurrencyClose.activeCashierList}" />
                                <a4j:status onstart="#{rich:component('wait')}.show()" for="userIdRegion" onstop="#{rich:component('wait')}.hide()" />
                                <a4j:support action="#{CashierCurrencyClose.activeCashierOnBlur}" event="onblur" reRender="message,errorMessage,a3,a4,a5,a6,gpFooter,table,taskList,gpFooter" focus="btnCashClose" limitToList="true" />
                            </h:selectOneListbox>
                        </a4j:region>
                        <h:outputLabel id="lblOpeningBal" styleClass="label" value="Opening Balance :" />
                        <h:outputText id="stxtOpeningBal" styleClass="output" value="#{CashierCurrencyClose.openingBal}" style="color:purple;"/>
                        <h:outputLabel id="lblCashRecieved" styleClass="label" value="Cash Recieved :" />
                        <h:outputText id="stxtCashRecieved" styleClass="output" value="#{CashierCurrencyClose.cashRecieved}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblSubTotal" styleClass="label" value="Sub Total :" />
                        <h:outputText id="stxtSubTotal" styleClass="output" value="#{CashierCurrencyClose.subTotal}" style="color:purple;"/>
                        <h:outputLabel id="lblCashPayment" styleClass="label" value="Cash Payment :"/>
                        <h:outputText id="stxtCashPayment" styleClass="output" value="#{CashierCurrencyClose.cashPayment}" style="color:purple;"/>
                        <h:outputLabel id="lblDrAmtThrTran" styleClass="label" value="Debit Amt. Through Tran. :"/>
                        <h:outputText id="stxtDrAmtThrTran" styleClass="output" value="#{CashierCurrencyClose.drAmtThroughTran}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblDrAmtForDemand" styleClass="label" value="Debit Amt. For Demand :" />
                        <h:outputText id="stxtDrAmtForDemand" styleClass="output" value="#{CashierCurrencyClose.drAmtForDemand}" style="color:purple;"/>
                        <h:outputLabel id="lblCrAmtToDemand" styleClass="label" value="Credit Amt. To Demand :" />
                        <h:outputText id="stxtCrAmtToDemand" styleClass="output" value="#{CashierCurrencyClose.crAmtToDemand}" style="color:purple;"/>
                        <h:outputLabel id="lblClosingBal" styleClass="label" value="Closing Balance :" />
                        <h:outputText id="stxtClosingBal" styleClass="output" value="#{CashierCurrencyClose.closingBal}" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a6" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblCashCounterAssigned" styleClass="label" value="Cash Counter Assigned :" />
                        <h:outputText id="stxtCashCounterAssigned" styleClass="output" value="#{CashierCurrencyClose.cashCounterAssigned}" style="color:purple;"/>
                        <h:outputLabel id="lblStatusOfCounter" styleClass="label" value="Status Of Counter :" />
                        <h:outputText id="stxtStatusOfCounter" styleClass="output" value="#{CashierCurrencyClose.statusOfCounter}" style="color:purple;"/>
                        <h:outputLabel id="lblCashierType" styleClass="label" value="Cashier Type :" />
                        <h:outputText id="stxtCashierType" value="#{CashierCurrencyClose.cashierType}" styleClass="output" style="color:purple;"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{CashierCurrencyClose.cashInSafeDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Cashier Cash Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Cashier" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 1000" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 500" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 100" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 50" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 20" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 10" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 5" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 2" /></rich:column>
                                        <rich:column><h:outputText value="Rs. 1" /></rich:column>
                                        <rich:column><h:outputText value="50 Paise" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cashierName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs1000}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs500}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs100}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs50}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs20}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs10}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs5}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs2}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rs1}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.paise50}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCashClose" tabindex="2" value="Cash Close" oncomplete="#{rich:component('closePanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,taskList,taskList,table,gpFooter" focus="btnRefresh" />
                            <a4j:commandButton id="btnRefresh" tabindex="3" value="Refresh" action="#{CashierCurrencyClose.resetForm}" reRender="message,errorMessage,a3,a4,a5,a6,taskList,taskList,table,gpFooter" focus="ddActiveCashier"/>
                            <a4j:commandButton id="btnExit" tabindex="4" value="Exit" action="#{CashierCurrencyClose.exitBtnAction}" reRender="message,errorMessage,a3,a4,a5,a6,taskList,taskList,table,gpFooter" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
            <rich:modalPanel id="closePanel" autosized="true" width="300" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to close the cash ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{CashierCurrencyClose.cashCloseBtnAction}"
                                                       oncomplete="#{rich:component('closePanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,taskList,taskList,table,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('closePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
