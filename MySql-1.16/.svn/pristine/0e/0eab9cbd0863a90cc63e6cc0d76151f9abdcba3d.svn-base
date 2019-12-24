<%-- 
    Document   : creditFlowToMinCommunity
    Created on : 30 Jun, 2016, 4:52:20 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Credit Flow to Minority Communities</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CreditFlowToMinCommunity.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Credit Flow to Minority Communities"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CreditFlowToMinCommunity.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{CreditFlowToMinCommunity.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="8"  columnClasses="col3,col3,col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row1">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{CreditFlowToMinCommunity.branch}" styleClass="ddlist">
                            <f:selectItems value="#{CreditFlowToMinCommunity.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRpType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRpType" size="1" styleClass="ddlist" value="#{CreditFlowToMinCommunity.reportType}">
                            <f:selectItems value="#{CreditFlowToMinCommunity.reportTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="calDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>                        
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="8"  columnClasses="col3,col3,col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row2">
                        <h:outputLabel id="label44" value="From Date : " styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="calFromDate" styleClass="input calFromDate" value="#{CreditFlowToMinCommunity.fromDate}" size="10"/>
                        <h:outputLabel id="label45" value="To Date : " styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="calToDate" styleClass="input calToDate" value="#{CreditFlowToMinCommunity.calDate}" size="10"/>
                        <h:outputLabel id="lblReportIn" styleClass="label" value="Report In"/>
                        <h:selectOneListbox id="ddReportIn" size="1" styleClass="ddlist" value="#{CreditFlowToMinCommunity.reportIn}">
                            <f:selectItems value="#{CreditFlowToMinCommunity.reportInList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{CreditFlowToMinCommunity.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CreditFlowToMinCommunity.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CreditFlowToMinCommunity.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>