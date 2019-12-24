<%-- 
    Document   : utrstatus
    Created on : Dec 19, 2012, 12:33:48 PM
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
            <title>UTR Status Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{utrStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Neft-Rtgs Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{utrStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{utrStatus.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">
                        <h:outputLabel id="reportBased" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddReportBased" size="1" styleClass="ddlist" value="#{utrStatus.reportType}">
                            <f:selectItems value="#{utrStatus.reportTypeList}"/>
                            <a4j:support event="onblur" action="#{utrStatus.processFunction}" reRender="lblNeftStatus,lblNpciStatus,
                                         ddStatus,ddNpciStatus,gridPanel3,gridPanel4,gridPanel5" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:outputLabel id="lblNeftStatus" styleClass="label" value="Status" style="display:#{utrStatus.enableNeft}"/>
                            <h:outputLabel id="lblNpciStatus" styleClass="label" value="Status" style="display:#{utrStatus.enableNpci}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{utrStatus.status}" style="display:#{utrStatus.enableNeft}">
                                <f:selectItems value="#{utrStatus.statusList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddNpciStatus" size="1" styleClass="ddlist" value="#{utrStatus.npciStatus}" style="display:#{utrStatus.enableNpci}">
                                <f:selectItems value="#{utrStatus.npciStatusList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1" style="height:30px;display:#{utrStatus.enableNeftBank}">
                        <h:outputLabel id="lblProcessType" styleClass="label" value="Process Type"/>
                        <h:selectOneListbox id="ddProcessType" size="1" styleClass="ddlist" value="#{utrStatus.processType}">
                            <f:selectItems value="#{utrStatus.processTypeList}"/>
                            <a4j:support event="onblur" action="#{utrStatus.processTypeAction}" reRender="lblMsg,ddNeftBank" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblNeftBank" styleClass="label" value="Neft Bank"/>
                        <h:selectOneListbox id="ddNeftBank" size="1" styleClass="ddlist" value="#{utrStatus.neftBank}">
                            <f:selectItems value="#{utrStatus.neftBankList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid >

                    <h:panelGrid id="gridPanel4" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" 
                                 styleClass="row2" style="display:#{utrStatus.dateGridDisplay}">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{utrStatus.frdt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{utrStatus.todt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel5" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" 
                                 styleClass="row2" style="display:#{utrStatus.enableTrsNoGrid}">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"/>
                        <h:inputText id="txtDate" size="10" styleClass="input issueDt" value="#{utrStatus.dt}">
                            <a4j:support event="onblur" action="#{utrStatus.getAllTrsNo}" reRender="ddTrsNo" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblTrsno" styleClass="label" value="Trs No"/>
                        <h:selectOneListbox id="ddTrsNo" size="1" styleClass="ddlist" value="#{utrStatus.trsNo}">
                            <f:selectItems value="#{utrStatus.trsNoList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{utrStatus.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg" oncomplete="setMask();"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{utrStatus.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{utrStatus.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{utrStatus.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
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
        </body>
    </html>
</f:view>
