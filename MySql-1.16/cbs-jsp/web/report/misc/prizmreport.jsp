<%-- 
    Document   : prizmreport
    Created on : 15 Oct, 2015, 10:28:16 AM
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
            <title>Prizm Card Report</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{prizmReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Prizm Card Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{prizmReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{prizmReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" size="1" styleClass="ddlist" value="#{prizmReport.branch}">
                            <f:selectItems value="#{prizmReport.branchList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="reportBased" styleClass="label" value="Report Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddReportBased" size="1" styleClass="ddlist" value="#{prizmReport.reportType}">
                            <f:selectItems value="#{prizmReport.reportTypeList}"/>
                            <a4j:support event="onblur" action="#{prizmReport.reportTypeAction}" reRender="lblTxtAcno,lblFrDt,txtAcno,txtFrDt,lblToDt,txtToDt" 
                                         oncomplete="setMask();if(#{prizmReport.reportType == 'I'})
                                         {#{rich:element('txtAcno')}.focus();}else if(#{prizmReport.reportType == 'D'})
                                         {#{rich:element('lblFrDt')}.focus();}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row2">
                        <h:panelGroup>
                            <h:outputLabel id="lblTxtAcno" styleClass="label" value="A/c Number" style="display:#{prizmReport.showAcno}">
                                <font class="required" color="red">*</font></h:outputLabel>
                            <h:outputLabel id="lblFrDt" styleClass="label" value="From Date" style="display:#{prizmReport.showDt}">
                                <font class="required" color="red">*</font></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:inputText id="txtAcno" size="12" styleClass="input" value="#{prizmReport.acno}" 
                                         style="display:#{prizmReport.showAcno}" maxlength="12">
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:inputText id="txtFrDt" size="10" styleClass="input issueDt" value="#{prizmReport.frdt}" 
                                         style="display:#{prizmReport.showDt}">
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date" style="display:#{prizmReport.showDt}"/>
                        <h:inputText id="txtToDt" size="10" styleClass="input issueDt" value="#{prizmReport.todt}" 
                                     style="display:#{prizmReport.showDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{prizmReport.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{prizmReport.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{prizmReport.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
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
