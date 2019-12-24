<%-- 
    Document   : LockerIssueAuthorization
    Created on : Sep 25, 2010, 6:55:16 PM
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
            <title>Locker Issued Authorization</title>
            <script type="text/javascript">
                var row = 0;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LockerIssueAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Issued Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LockerIssueAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LockerIssueAuthorization.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LockerIssueAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerIssueAuthorization.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Cabinate No." /></rich:column>
                                        <rich:column><h:outputText value="Locker Type" /></rich:column>
                                        <rich:column><h:outputText value="Locker No." /></rich:column>
                                        <rich:column><h:outputText value="Key No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Cust Name" /></rich:column>
                                        <rich:column><h:outputText value="Category" /></rich:column>
                                        <rich:column><h:outputText value="Security" /></rich:column>
                                        <rich:column><h:outputText value="Rent" /></rich:column>
                                        <rich:column><h:outputText value="Mode" /></rich:column>
                                        <rich:column><h:outputText value="Nomination" /></rich:column>
                                        <rich:column><h:outputText value="Remarks" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cabNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerTy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.keyNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custCat}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.security}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.rent}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.nomination}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;">
                                    <h:outputText value="#{dataItem.auth}" />
                                    <a4j:support actionListener="#{LockerIssueAuthorization.changeAuth}" ajaxSingle="true" event="ondblclick" reRender="message,errorMessage,lpg,taskList,table">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LockerIssueAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{LockerIssueAuthorization.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column style="text-align:center;">
                                <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{row}" target="#{LockerIssueAuthorization.currentRow}" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{LockerIssueAuthorization.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication" styleClass="label" value="Double Click On Auth Column To Change The Auth."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" tabindex="1" value="Authorize" oncomplete="#{rich:component('authPanel')}.show()" reRender="message,errorMessage,lpg,taskList,table,gpFooter"  />
                            <a4j:commandButton id="btnRefresh" tabindex="2" value="Refresh" action="#{LockerIssueAuthorization.refreshForm}" reRender="message,errorMessage,lpg,taskList,table,gpFooter" focus="btnAuthorize"/>
                            <a4j:commandButton id="btnExit" tabindex="3" value="Exit" reRender="message,errorMessage" action="#{LockerIssueAuthorization.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="authPanel" autosized="true" width="250" onshow="#{rich:element('save')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="save" ajaxSingle="true" action="#{LockerIssueAuthorization.authorizeBtn}"
                                                       oncomplete="#{rich:component('authPanel')}.hide();" reRender="message,errorMessage,lpg,taskList,table" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" onclick="#{rich:component('authPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" label="Confirmation Dialog" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();" style="">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog"/>
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to delete this transaction ? "/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" ajaxSingle="true" action="#{LockerIssueAuthorization.deleteForm}" reRender="message,errorMessage,lpg,taskList,table"
                                                           oncomplete="#{rich:component('deletePanel')}.hide();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
        </body>
    </html>
</f:view>
