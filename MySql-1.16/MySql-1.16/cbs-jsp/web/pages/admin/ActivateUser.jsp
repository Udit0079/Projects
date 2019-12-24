<%-- 
    Document   : ActivateUser
    Created on : Aug 20, 2010, 1:06:10 PM
    Author     : Rohit Krishna Gupta
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
            <title>User Management</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ActivateUser.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="User Management"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ActivateUser.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ActivateUser.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ActivateUser.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{ActivateUser.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="12" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="User Management" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Serial No." /></rich:column>
                                        <rich:column><h:outputText value="User ID" /></rich:column>
                                        <rich:column><h:outputText value="User Name" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Allow Login" /></rich:column>
                                        <rich:column><h:outputText value="Cashier Access" /></rich:column>
                                        <rich:column><h:outputText value="Head Cashier Access" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="10px"><h:outputText value="#{dataItem.serialNo}" /></rich:column>
                                <rich:column width="200px"><h:outputText value="#{dataItem.userId}" /></rich:column>
                                <rich:column width="200px"><h:outputText value="#{dataItem.userName}" /></rich:column>
                                <rich:column width="150px" style="text-align:center;">
                                    <h:outputText value="#{dataItem.status}" style="cursor:pointer;" />
                                    <a4j:support action="#{ActivateUser.changeStatus}" ajaxSingle="true" event="ondblclick" reRender="taskList,lpg">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ActivateUser.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ActivateUser.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column width="150px" style="text-align:center;">
                                    <h:outputText value="#{dataItem.allowLogin}" style="cursor:pointer;" />
                                    <a4j:support action="#{ActivateUser.changeLogin}" ajaxSingle="true" event="ondblclick" reRender="taskList,lpg">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ActivateUser.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ActivateUser.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column width="150px" style="text-align:center;">
                                    <h:outputText value="#{dataItem.cashierAccess}" style="cursor:pointer;" />
                                    <a4j:support action="#{ActivateUser.changeAccess}" ajaxSingle="true" event="ondblclick" reRender="taskList,lpg">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ActivateUser.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ActivateUser.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column width="150px" style="text-align:center;">
                                    <h:outputText value="#{dataItem.headCashierAccess}" style="cursor:pointer;" />
                                    <a4j:support action="#{ActivateUser.changeHeadCashierAccess}" ajaxSingle="true" event="ondblclick" reRender="taskList,lpg">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ActivateUser.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{ActivateUser.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSave')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog"/>
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Do You Want to Change the Status and Login?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{ActivateUser.saveUserInfo}"
                                                                   oncomplete="#{rich:component('savePanel')}.hide();" reRender="a6,lpg,message,errorMessage,taskList" focus="btnRefresh"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnNoSave" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication1" styleClass="label" value="To Activate / Deactivate User(s) Double Click On Status."/>
                        <h:outputLabel id="lblIndication2" styleClass="label" value="To Change Login Permission / Cashier Access Double Click On Login / Cashier Access."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="a6,lpg,message,errorMessage"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ActivateUser.refreshForm}" reRender="a6,lpg,message,errorMessage"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ActivateUser.ExitForm}"/>
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
        </body>
    </html>
</f:view>
