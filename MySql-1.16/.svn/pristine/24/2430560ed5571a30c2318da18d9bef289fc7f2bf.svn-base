<%-- 
    Document   : CashierOpeningBalanceRegister
    Created on : Aug 26, 2011, 3:03:30 PM
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
            <title>Cashier Opening Balance Register</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <a4j:keepAlive beanName="CashierOpeningBalanceRegister"/>
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CashierOpeningBalanceRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cashier Opening Balance Register"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CashierOpeningBalanceRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <%--<h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
</h:panelGrid>--%>
                    <h:panelGrid columnClasses="col9" columns="2" id="a4" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:40px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <h:outputText id="errorMessage" styleClass="error" value="#{CashierOpeningBalanceRegister.errorMessage}"/>
                                    <h:outputText id="message" styleClass="msg" value="#{CashierOpeningBalanceRegister.message}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblUserId" styleClass="label" value="User ID :" style=""/>
                                <h:inputText id="txtUserId" maxlength="20" tabindex="1" size="20" value="#{CashierOpeningBalanceRegister.userId}" styleClass="input">
                                    <a4j:support action="#{CashierOpeningBalanceRegister.getUserInformation}" event="onblur"
                                                 reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" focus="txtCashCounterNo"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                                <h:outputLabel id="lblCashierName" styleClass="label" value="Cashier Name :" style=""/>
                                <h:inputText id="txtCashierName" maxlength="40" tabindex="2" size="20" value="#{CashierOpeningBalanceRegister.cashierName}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" readonly="true"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                                <h:outputLabel id="lblCashCounterNo" styleClass="label" value="Cash Counter No. :" style=""/>
                                <h:inputText id="txtCashCounterNo" maxlength="5" tabindex="3" size="20" value="#{CashierOpeningBalanceRegister.cashCounterNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="1" id="a9" width="100%">
                            <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:140px;border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{CashierOpeningBalanceRegister.activeUserGridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="15"><h:outputText value="Cashiers Detail" /></rich:column>
                                                <rich:column breakBefore="true" width="10%"><h:outputText value="Sr No." /></rich:column>
                                                <rich:column width="20%"><h:outputText value="Name" /></rich:column>
                                                <rich:column width="20%"><h:outputText value="User ID" /></rich:column>
                                                <rich:column width="20%"><h:outputText value="Counter No." /></rich:column>
                                                <rich:column width="20%"><h:outputText value="Status" /></rich:column>
                                                <rich:column width="10"><h:outputText value="Select" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.userId}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.counterNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink" action="#{CashierOpeningBalanceRegister.fillValuesofGridInFields}" oncomplete="#{rich:element('txtUserId')}.disabled = true;#{rich:element('btnSave')}.disabled = true;"
                                                             reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" focus="txtCashCounterNo">
                                                <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{CashierOpeningBalanceRegister.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{CashierOpeningBalanceRegister.currentRow}"/>
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{CashierOpeningBalanceRegister.cashInSafeDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100" rowKeyVar="row"
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
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="4" value="Save" action="#{CashierOpeningBalanceRegister.assignCounterToCashier}" rendered="#{CashierOpeningBalanceRegister.saveFlag}" reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" focus="btnRefresh" />
                            <a4j:commandButton id="btnUpdate" tabindex="5" value="Update" action="#{CashierOpeningBalanceRegister.updateCounterOfCashier}" rendered="#{CashierOpeningBalanceRegister.updateFlag}" reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{CashierOpeningBalanceRegister.resetActiveUserPanel}" reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" focus="txtUserId"/>
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{CashierOpeningBalanceRegister.exitBtnAction}" reRender="message,errorMessage,a5,a6,a7,a8,a9,taskList,taskList,table,gpFooter" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
