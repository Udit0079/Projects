<%-- 
    Document   : smsReport
    Created on : Mar 22, 2013, 4:16:15 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>SMS Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{smsReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="SMS Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{smsReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{smsReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="6"  columnClasses="col1,col4,col1,col4,col1,col4"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblReportType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddReportType" size="1" styleClass="ddlist" value="#{smsReport.reportType}">
                            <f:selectItems value="#{smsReport.reportTypeList}"/>
                            <a4j:support event="onblur" actionListener="#{smsReport.reportTypeAction}" reRender="lblMsg,lblBrAcno,txtBrAcno,lblBranch,ddBranchType,lblHoAcno,txtHoAcno" oncomplete="setMask();" focus="#{smsReport.focusId}"/>
                        </h:selectOneListbox>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblBrAcno" styleClass="label" value="Account No." style="display:#{smsReport.brAcnoLabelFlag};"/>
                            <h:outputLabel id="lblBranch" styleClass="label" value="Branch" style="display:#{smsReport.branchLabelFlag};"/>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtBrAcno" styleClass="input" value="#{smsReport.brAccountNo}" style="display:#{smsReport.brAcnoTxtFlag};" maxlength="#{smsReport.acNoMaxLen}" size="#{smsReport.acNoMaxLen}"/>
                            <h:selectOneListbox id="ddBranchType" size="1" styleClass="ddlist" value="#{smsReport.branch}" style="display:#{smsReport.branchComboFlag};">
                                <f:selectItems value="#{smsReport.branchList}"/>
                                <a4j:support event="onblur" actionListener="#{smsReport.branchAction}" reRender="lblMsg,lblHoAcno,txtHoAcno" oncomplete="setMask();" focus="#{smsReport.focusId}"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblHoAcno" styleClass="label" value="Account No." style="display:#{smsReport.hoAcnoLabelFlag};"/>
                        <h:inputText id="txtHoAcno" styleClass="input" value="#{smsReport.hoAccountNo}" style="display:#{smsReport.hoAcnoTxtFlag};" maxlength="#{smsReport.acNoMaxLen}" disabled="#{smsReport.hoAccountDisable}" size="#{smsReport.acNoMaxLen}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6"  columnClasses="col1,col4,col1,col4,col1,col4"  width="100%" styleClass="row2">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{smsReport.frdt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="toDate"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{smsReport.todt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="btnHtml"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{smsReport.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{smsReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{smsReport.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{smsReport.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
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
