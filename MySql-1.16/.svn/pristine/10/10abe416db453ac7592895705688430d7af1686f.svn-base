<%-- 
    Document   : InoperativeReport
    Created on : Oct 4, 2013, 3:51:22 PM
    Author     : sipl
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
            <title>Inoperative Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                    jQuery(".txnFromDt").mask("99/99/9999");
                    jQuery(".txnToDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="InoperativeReport">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InoperativeReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Inoperative Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{InoperativeReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{InoperativeReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{InoperativeReport.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{InoperativeReport.branchCodeList}"/>
                        </h:selectOneListbox>
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="repType" size="1" value="#{InoperativeReport.repType}" styleClass="ddlist">
                            <f:selectItems value="#{InoperativeReport.repTypeList}"/>
                            <a4j:support id="idDateType" event="onblur" action="#{InoperativeReport.getDateTypeOnBlurr}" reRender="dateType"/>
                        </h:selectOneListbox>                        
                        <h:outputText value="Account  Nature" styleClass="label"/>
                        <h:selectOneListbox id="acNature" size="1" value="#{InoperativeReport.acNature}" styleClass="ddlist">
                            <f:selectItems value="#{InoperativeReport.acNatureList}"/>
                            <a4j:support id="idnature"action="#{InoperativeReport.getAcTypeForNature}" event="onblur" reRender="acType"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="acType" size="1" value="#{InoperativeReport.acType}" styleClass="ddlist">
                            <f:selectItems value="#{InoperativeReport.acTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputText value="Date Type" styleClass="label"/>
                        <h:selectOneListbox id="dateType" size="1" value="#{InoperativeReport.dateType}" styleClass="ddlist">
                            <f:selectItems value="#{InoperativeReport.dateTypeList}"/>
                            <a4j:support id="idFromDt" event="onblur" action="#{InoperativeReport.getDateTypeOnBlurr}" reRender="frDt,frDate,toDt,toDate,txnFrDt,txnFromDt,txnToDt,txnToDate" focus="frDt,frDate,toDt,toDate,txnFrDt,txnFromDt,txnToDt,txnToDate"/>                            
                        </h:selectOneListbox>
                        <h:outputText id="frDt" value="From Date" styleClass="label" style="display:#{InoperativeReport.showFrDt}"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="frDate" styleClass="input frDate" value="#{InoperativeReport.frdt}" style="display:#{InoperativeReport.showFrDt}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>                                
                            </h:inputText>
                        </h:panelGroup>                        
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">                        
                        <h:outputText id ="toDt" value="To Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="toDate" styleClass="input toDate" style="setMask();width:70px;" value="#{InoperativeReport.todt}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                                <%--<a4j:support id="idTxnFromDt" event="onblur" action="#{InoperativeReport.getTxnDate}" reRender="txnFrDt,txnFromDt,txnToDt,txnToDate"/>--%>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputText id="txnFrDt" value="Txn From Date" styleClass="label" style="display:#{InoperativeReport.visible}"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txnFromDt" styleClass="input toDate"  value="#{InoperativeReport.txnFromDt}"  style="display:#{InoperativeReport.visible}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                        </h:panelGroup>
                        <h:outputText id="txnToDt" value="Txn To Date" styleClass="label" style="display:#{InoperativeReport.visible}"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txnToDate" styleClass="input toDate" value="#{InoperativeReport.txnToDt}"  style="display:#{InoperativeReport.visible}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{InoperativeReport.btnHtmlAction}" reRender="errmsg"/>
                             <h:commandButton id="btnDownload"  value="PDF Download" action="#{InoperativeReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InoperativeReport.refresh}" reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{InoperativeReport.exitAction}" reRender="errmsg"/>
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