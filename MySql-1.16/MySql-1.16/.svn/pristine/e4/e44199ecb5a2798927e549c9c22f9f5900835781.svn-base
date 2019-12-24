<%-- 
    Document   : serviceDetailReport
    Created on : 25 Apr, 2018, 11:03:39 AM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Service Registration Detail Report </title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".toDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="tdMaturity">
                <h:panelGrid columns="1" id="mainPanel" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="blockPanel" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ServiceDetailReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Service Registration Detail Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ServiceDetailReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ServiceDetailReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText id="stxtserviceType" value="Service Type" styleClass="label"/>
                        <h:selectOneListbox id="selectServiceType" value="#{ServiceDetailReport.serviceType}" styleClass="ddlist" style="width:150px" size="1">
                            <f:selectItems id="selectServiceList" value="#{ServiceDetailReport.serviceList}"/>
                        </h:selectOneListbox>
                        <h:outputText id="stxtbranchCode" value="Branch Code" styleClass="label"/>
                        <h:selectOneListbox id="selectbranchCode" value="#{ServiceDetailReport.branchCode}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectbranchCodeList" value="#{ServiceDetailReport.branchCodeList}"/>
                        </h:selectOneListbox>
                        <h:outputText id="stxtmode" value="Mode" styleClass="label"/>
                        <h:selectOneListbox id="selectmode" value="#{ServiceDetailReport.mode}" styleClass="ddlist" 
                                            style="width:100px" size="1">
                            <f:selectItems id="selectmodeList" value="#{ServiceDetailReport.modeList}"/>
                            <a4j:support event="onblur" action="#{ServiceDetailReport.modeAction}" reRender="errorMsg,lblFrDt,labelCheque
                                         frDate,dataChqRegion,txtacno,txtcustid,lblToDt,dataRegion,toDate,Orid,panel2" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>  
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:panelGroup>
                            <h:outputLabel id="lblFrDt" styleClass="label" value="From Date" style="display:#{ServiceDetailReport.firstdate}"/>
                            <h:outputLabel id="labelCheque" styleClass="label" value="Ac No./Customerid" style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:inputText id="frDate" size="10" styleClass="input toDate"  value="#{ServiceDetailReport.frdt}" 
                                         style="display:#{ServiceDetailReport.firstdate}"/>
                            <h:inputText id="txtacno" styleClass="input" value="#{ServiceDetailReport.acNo}" maxlength="12" 
                                         size="15"style="display:#{ServiceDetailReport.displayoncheque}"/>

                            <h:outputLabel id="Orid" value="OR" styleClass="label" style="display:#{ServiceDetailReport.displayoncheque}"/>
                            <h:inputText id="txtcustid" styleClass="input" value="#{ServiceDetailReport.custid}" maxlength="10" 
                                         size="12"style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel id="lblToDt" styleClass="label" value="To Date" style="display:#{ServiceDetailReport.secondDate}"/>
                            <h:outputLabel  style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:inputText id="toDate" size="10" styleClass="input toDate" value="#{ServiceDetailReport.todt}" 
                                         style="display:#{ServiceDetailReport.secondDate}"/>
                            <h:outputLabel  style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel  style="display:#{ServiceDetailReport.secondDate}"/>
                            <h:outputLabel  style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel  style="display:#{ServiceDetailReport.secondDate}"/>
                            <h:outputLabel  style="display:#{ServiceDetailReport.displayoncheque}"/>
                        </h:panelGroup>
                    </h:panelGrid>  
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton value="Print Report" action="#{ServiceDetailReport.viewReport}" reRender="errorMsg,txtFrmDate,txtToDate"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{ServiceDetailReport.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ServiceDetailReport.btnRefreshAction}" reRender="mainPanel,errorMsg" oncomplete="setMask();"/>
                            <a4j:commandButton value="Exit" action="#{ServiceDetailReport.exitAction}" oncomplete="setMask();" reRender="toDate,mainPanel"/>
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