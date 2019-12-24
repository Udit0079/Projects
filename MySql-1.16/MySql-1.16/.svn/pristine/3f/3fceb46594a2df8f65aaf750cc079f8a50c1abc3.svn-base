<%-- 
    Document   : achReport
    Created on : 20 Jan, 2017, 1:08:39 PM
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
            <title>ACH Report</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{achReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="ACH Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{achReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{achReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col2,col7,col2,col7" columns="4"   width="100%" styleClass="row1">
                        <h:outputLabel id="brnList" styleClass="label" value="Branch"/>
                        <h:selectOneListbox id="ddBrn" size="1" styleClass="ddlist" value="#{achReport.brnCode}" style="width:120px;">
                            <f:selectItems value="#{achReport.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="reportBased" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddReportBased" size="1" styleClass="ddlist" value="#{achReport.reportType}" style="width:120px;">
                            <f:selectItems value="#{achReport.reportTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columnClasses="col2,col7,col2,col7" columns="4"   width="100%" styleClass="row1">
                        <h:outputLabel id="lblstatus" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{achReport.status}" style="width:120px;">
                            <f:selectItems value="#{achReport.statusList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblmode" styleClass="label" value="Report Mode"/>
                        <h:selectOneListbox id="ddMode" size="1" styleClass="ddlist" value="#{achReport.reportMode}" style="width:120px;">
                            <f:selectItems value="#{achReport.reportModeList}"/>
                            <a4j:support event="onblur" action="#{achReport.processFunction}" reRender="gridPanel4,gridPanel5" 
                                         oncomplete="if(#{achReport.reportMode == 'DTW'}){#{rich:element('frDate')}.focus();}
                                         else if(#{achReport.reportMode == 'SQW'}){#{rich:element('txtDate')}.focus();};setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columnClasses="col2,col7,col2,col7" columns="4" width="100%" 
                                 styleClass="row2" style="display:#{achReport.dateGridDisplay}">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{achReport.fromDate}" style="width:120px;"/>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{achReport.todate}" style="width:120px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columnClasses="col2,col7,col2,col7" columns="4" width="100%" 
                                 styleClass="row2" style="display:#{achReport.enableSeqNoGrid}">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"/>
                        <h:inputText id="txtDate" size="10" styleClass="input issueDt" value="#{achReport.date}" style="width:120px;">
                            <a4j:support event="onblur" action="#{achReport.getAllSeqNo()}" reRender="ddTrsNo" oncomplete="setMask();" focus="ddTrsNo"/>
                        </h:inputText>
                        <h:outputLabel id="lblTrsno" styleClass="label" value="Trs No"/>
                        <h:selectOneListbox id="ddTrsNo" size="1" styleClass="ddlist" value="#{achReport.seqNo}">
                            <f:selectItems value="#{achReport.seqNoList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Print Report" action="#{achReport.btnHtmlAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{achReport.btnPdfAction}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{achReport.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{achReport.btnExitAction}" reRender="mainPanel" oncomplete="setMask();"/>
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
