<%-- 
    Document   : IBCOBCAuthorization
    Created on : Oct 4, 2010, 10:53:04 AM
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
            <title>IBCOBC Authorization</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{IBCOBCAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="IBC/OBC/Bill Purchase/LC/Bank Gurantee Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IBCOBCAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{IBCOBCAuthorization.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{IBCOBCAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" style="padding-left:350px;"/>
                        <h:selectOneListbox id="ddBillType" tabindex="1" styleClass="ddlist" value="#{IBCOBCAuthorization.billType}" size="1" style="width: 150px">
                            <f:selectItems value="#{IBCOBCAuthorization.billTypeList}"/>
                            <a4j:support event="onchange"  actionListener="#{IBCOBCAuthorization.gridLoad}" reRender="table,gpFooter,taskList,message,errorMessage" focus="btnRefresh"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{IBCOBCAuthorization.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S. No." /></rich:column>
                                        <rich:column><h:outputText value="Bill Type" /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Party Name" /></rich:column>
                                        <rich:column><h:outputText value="Inst. No." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Amt." /></rich:column>
                                        <rich:column><h:outputText value="Inst. Date" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="Coll Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Coll Bank Address" /></rich:column>
                                        <rich:column><h:outputText value="Bank Name" /></rich:column>
                                        <rich:column><h:outputText value="Bank Address" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Delete" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.partyName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colBankname}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.colBankAdd}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.bankAdd}" /></rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList" focus="ddBillType">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{IBCOBCAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{IBCOBCAuthorization.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" actionListener="#{IBCOBCAuthorization.fillValuesofGridInFields}" oncomplete="#{rich:element('btnAuthorize')}.disabled = false;"
                                                     reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList" focus="btnAuthorize">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{IBCOBCAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{IBCOBCAuthorization.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication" styleClass="label" value="Select The Row To Authorize The Entry."/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" tabindex="2" value="Authorize" disabled="#{IBCOBCAuthorization.flag}" oncomplete="#{rich:component('authPanel')}.show()" reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="3" value="Refresh" action="#{IBCOBCAuthorization.refreshForm}" oncomplete="#{rich:element('btnAuthorize')}.disabled = true;" reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList" focus="ddBillType"/>
                            <a4j:commandButton id="btnExit" tabindex="4" value="Exit" action="#{IBCOBCAuthorization.exitForm}" reRender="message,errorMessage"/>
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
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Record ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{IBCOBCAuthorization.delete}" focus="btnRefresh"
                                                       oncomplete="#{rich:component('deletePanel')}.hide();" reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList" />
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDelNo" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="authPanel" autosized="true" width="250" onshow="#{rich:element('btnYesAuth')}.focus();">
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
                                    <a4j:commandButton value="Yes" id="btnYesAuth" ajaxSingle="true" action="#{IBCOBCAuthorization.accountAuthorization}" focus="btnRefresh"
                                                       oncomplete="#{rich:component('authPanel')}.hide();" reRender="a3,message,errorMessage,lpg,gpFooter,table,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoAuth" onclick="#{rich:component('authPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
