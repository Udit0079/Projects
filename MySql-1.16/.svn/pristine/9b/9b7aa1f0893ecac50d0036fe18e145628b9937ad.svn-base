<%-- 
    Document   : SuspiciousTranVerify
    Created on : Mar 6, 2017, 3:49:18 PM
    Author     : Admin
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
            <title>Suspicious Transaction Verification</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="susTranFrm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SuspiciosTranVerify.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Suspicious Transaction Verification"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SuspiciosTranVerify.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="mainPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{SuspiciosTranVerify.message}"/>
                        </h:panelGrid>                        
                    </h:panelGrid>
                    <h:panelGrid id="checkBoxPenal" styleClass="row2" style="width:100%;text-align:left;">
                        <h:panelGroup id="checkBoxPenalGrp" layout="block">
                            <h:selectBooleanCheckbox id="checkAllId" value="#{SuspiciosTranVerify.checkBoxAll}" disabled="#{SuspiciosTranVerify.checkBoxAllDisable}">
                                <a4j:support actionListener="#{SuspiciosTranVerify.checkAll}" event="onclick" 
                                         reRender="suspiciousPanelGrid"/> 
                            </h:selectBooleanCheckbox>
                            <h:outputLabel id="lblCheckBox" styleClass="output" value="Check/Un-Check All"></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="suspiciousPanelGrid" width="100%" styleClass="row1">
                        <a4j:region>
                            <rich:dataTable value ="#{SuspiciosTranVerify.tableList}"
                                    rowClasses="row1, row2" id = "taskList" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="8"><h:outputText value="Suspicios Transaction Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Batch No." /></rich:column>
                                        <rich:column><h:outputText value="Rec No." /></rich:column>
                                        <rich:column><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Alert Code" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Selection"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.trsNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.recNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.dt}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.alertCode}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.enterBy}"/></rich:column>
                                <rich:column>
                                    <h:selectBooleanCheckbox id="c1" value ="#{item.checkBox}">
                                        <a4j:support actionListener ="#{SuspiciosTranVerify.selectAllBox}" event="onclick" reRender="suspiciousPanelGrid"/> 
                                    </h:selectBooleanCheckbox>
                                </rich:column>                                
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="taskList" maxPages="20"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnGen" value="Mark" onclick="#{rich:component('verifyPanel')}.show()" reRender="lblMsg,mainPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SuspiciosTranVerify.refresh}" reRender="lblMsg,mainPanel,footerPanel,suspiciousPanelGrid,checkBoxPenal"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SuspiciosTranVerify.exitForm}" reRender="lblMsg,mainPanel,footerPanel"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="verifyPanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirm Dialog ?" style="padding-right:15px;" />
                </f:facet>
                <a4j:region id="verifyRegion">
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px;">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Verify Selected Details?" styleClass="output"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{SuspiciosTranVerify.Post}"
                                                    onclick="#{rich:component('verifyPanel')}.hide();" reRender="lblMsg,suspiciousPanelGrid,mainPanel,footerPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel"  ajaxSingle="true" onclick="#{rich:component('verifyPanel')}.hide();return false;"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </a4j:region>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>