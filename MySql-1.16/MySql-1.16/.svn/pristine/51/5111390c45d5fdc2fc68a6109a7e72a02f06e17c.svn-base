<%-- 
    Document   : deletedTransaction
    Created on : Nov 21, 2016, 11:51:54 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Deleted Transaction Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
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
            <h:form id="pfmsId">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DeletedTransaction.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Deleted Transaction Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DeletedTransaction.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{DeletedTransaction.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="branchId" size="1" value="#{DeletedTransaction.reportType}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{DeletedTransaction.reportTypeList}" />
                            <a4j:support action="#{DeletedTransaction.onReportType}" event="onchange"reRender="acoutId,acId"></a4j:support>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText id="acoutId" value="#{DeletedTransaction.branch}" styleClass="label"/>
                        <h:inputText id="acId"   styleClass="input" style="width:80px;"  value="#{DeletedTransaction.acNo}"disabled="#{DeletedTransaction.disableType}"/>
                        <h:outputText value="From Date :" styleClass="label"/>
                        <h:inputText id="txtFrmDate"   styleClass="input calFromDate" style="width:70px;"  value="#{DeletedTransaction.frDt}"/>
                        <h:outputText value="To Date :" styleClass="label"/>
                        <h:inputText id="txtToDate"   styleClass="input calToDate" style="width:70px;"  value="#{DeletedTransaction.toDt}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel9" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="printId" value="Print Report" action="#{DeletedTransaction.printAction}" reRender="errorMsg,a1" oncomplete="setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{DeletedTransaction.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DeletedTransaction.btnRefreshAction}" reRender="errorMsg,a1" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{DeletedTransaction.btnExitAction}" reRender="errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>

