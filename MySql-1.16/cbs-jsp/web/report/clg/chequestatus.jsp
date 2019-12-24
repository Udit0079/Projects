<%-- 
    Document   : chequestatus
    Created on : Dec 15, 2011, 4:16:20 PM
    Author     : root
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Cheque Status Report</title>
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
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{chequeStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Cheque Status Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{chequeStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{chequeStatus.message}"/>
                    </h:panelGrid>        
                    <h:panelGrid  id="gridPanel1" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col15" width="100%" styleClass="row1">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch Wise"> </h:outputLabel>
                        <h:selectOneListbox id="ddBranchName" styleClass="ddlist" size="1" style="width:70%" value="#{chequeStatus.branchCode}">
                            <f:selectItems value="#{chequeStatus.branchCodeList}"/>
                            <a4j:support event="onblur" reRender="lblMsg"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblClgType" styleClass="label" value="Clearing Type" style="display:#{chequeStatus.npciCtsDisplayFlag}"/>
                        <h:selectOneListbox id="ddClgType" size="1" styleClass="ddlist" style="width:50%;display:#{chequeStatus.npciCtsDisplayFlag}" value="#{chequeStatus.clgType}">
                            <f:selectItems value="#{chequeStatus.clgTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="status" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{chequeStatus.status}">
                            <f:selectItems value="#{chequeStatus.statusList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"/>
                        <h:inputText id="calDate" size="10" styleClass="input issueDt" value="#{chequeStatus.reportDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel style="display:#{chequeStatus.normalCtsDisplayFlag}"/>
                        <h:outputLabel style="display:#{chequeStatus.normalCtsDisplayFlag}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{chequeStatus.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{chequeStatus.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{chequeStatus.btnRefresh}" id="btnRefresh" value="Refresh" reRender="lblMsg,mainPanel"/>
                            <a4j:commandButton action="#{chequeStatus.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
