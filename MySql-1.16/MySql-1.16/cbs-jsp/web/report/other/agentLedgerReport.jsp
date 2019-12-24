<%-- 
    Document   : agentLedgerReport
    Created on : Feb 23, 2013, 1:09:50 PM
    Author     : Athar Reza
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Agent Ledger Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="LoanDisbursementReport">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/> 
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AgentLedgerReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Agent Ledger Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{AgentLedgerReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{AgentLedgerReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{AgentLedgerReport.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{AgentLedgerReport.branchCodeList}" />
                            <a4j:support action="#{AgentLedgerReport.ddbranchValueChange}" event="onblur" reRender="ddacType111" focus="ddacType111" oncomplete="setMask();"/>
                        </h:selectOneListbox>

                        <h:outputText id="lblacType" value="Agent Code:" styleClass="label"/>
                        <h:panelGroup id="agPanel">
                            <h:selectOneListbox id="ddacType111" value="#{AgentLedgerReport.agentCode}" styleClass="ddlist"  style="width:150px" size="1">
                                <f:selectItems id="selectRepTypeList11" value="#{AgentLedgerReport.agentCodeList}" />
                                <a4j:support action="#{AgentLedgerReport.onChangeAgcode}" event="onblur" oncomplete="setMask();" focus="ddacType112"/>
                            </h:selectOneListbox> 
                            <%--h:outputText id="agName" value="#{AgentLedgerReport.agentName}" styleClass="output" style="color:green"/--%>
                        </h:panelGroup>

                        <h:outputText id="lblacType2" value="A/c Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType112" value="#{AgentLedgerReport.acctType}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList12" value="#{AgentLedgerReport.acctTypeList}" />
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFromDate" value="From Date:" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{AgentLedgerReport.calFromDate}" style="width:70px;setMask();"/>
                        <h:outputLabel id="lbltoDate"  value=" To Date:" styleClass="label" />
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{AgentLedgerReport.caltoDate}" style="width:70px;setMask();"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{AgentLedgerReport.PrintViwe}" reRender="errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AgentLedgerReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AgentLedgerReport.refresh}" reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{AgentLedgerReport.closeAction}" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

