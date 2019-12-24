<%-- 
    Document   : ChequeMaintenanceAuth
    Created on : Jan 20, 2014, 3:00:32 PM
    Author     : Dhirendra Singh
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
            <title>Cheque Maintenance Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ChequeMaintenanceAuth.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cheque Maintenance Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ChequeMaintenanceAuth.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ChequeMaintenanceAuth.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ChequeMaintenanceAuth.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="a5" styleClass="row1" columns="2" style="width:100%;height:30px;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel styleClass="headerLabel" value="Today's Date Is : "/>
                            <h:outputText id="stxtTodayDt" styleClass="headerLabel" style="color:green" value="#{ChequeMaintenanceAuth.todayDate}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{ChequeMaintenanceAuth.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Cheques Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="A/C. No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Cheque Book No." /></rich:column>
                                        <rich:column><h:outputText value="From Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="To Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Cheque Date" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Charges" /></rich:column>
                                        <rich:column><h:outputText value="Favouring" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Auth Mode" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqBookNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqNoTo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chargeFlag}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.favoring}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enteredBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.authMode}" /></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;">
                                    <h:outputText value="#{dataItem.authBy}" />
                                    <a4j:support action="#{ChequeMaintenanceAuth.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="message,errorMessage,taskList">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ChequeMaintenanceAuth.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ChequeMaintenanceAuth.currentRow}"/>
                                    </a4j:support>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication" styleClass="label" style="color:blue" value="# Double click on auth column to authorize the entry."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" tabindex="1" value="Authorize" oncomplete="#{rich:component('authPanel')}.show()" reRender="message,errorMessage,lpg,taskList,gpFooter" focus="btnRefresh">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" tabindex="2" value="Refresh" action="#{ChequeMaintenanceAuth.refreshForm}" reRender="message,errorMessage,lpg,taskList,gpFooter" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" tabindex="3" value="Exit" action="#{ChequeMaintenanceAuth.exitBtnAction}" reRender="message,errorMessage" >
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="authPanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Authorize ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{ChequeMaintenanceAuth.authorizeBtn}"
                                                       oncomplete="#{rich:component('authPanel')}.hide();" reRender="message,errorMessage,lpg,taskList" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('authPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
