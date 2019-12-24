<%-- 
    Document   : atmStatus
    Created on : Sep 3, 2014, 10:46:21 AM
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
            <title>Atm Card detail</title>
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
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AtmStatus.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Atm Card Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AtmStatus.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AtmStatus.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="normalPanel" style="width:100%;text-align:center;display:#{AtmStatus.normalDisplay}">
                        <h:panelGrid id="Rowbr" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                            <h:outputText value="Branch" styleClass="label"/>
                            <h:selectOneListbox id="branchId1" size="1" value="#{AtmStatus.branch}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{AtmStatus.branchList}" />
                            </h:selectOneListbox>   
                            <h:outputText>
                            </h:outputText>
                            <h:outputText>
                            </h:outputText>
                        </h:panelGrid>       
                        <h:panelGrid id="Row1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                            <h:outputText value="File Type" styleClass="label"/>
                            <h:selectOneListbox id="fileId" size="1" value="#{AtmStatus.fileType}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{AtmStatus.fileTypeList}" />
                            </h:selectOneListbox> 
                            <h:outputText value="Status" styleClass="label"/>
                            <h:selectOneListbox id="statusID" size="1" value="#{AtmStatus.status}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{AtmStatus.statusList}" />
                            </h:selectOneListbox>        
                        </h:panelGrid> 
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3"  styleClass="row1">       
                            <h:outputLabel id="fromDate" value="From Date" styleClass="label"/>
                            <h:inputText id="cFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{AtmStatus.fromDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="toDate"  value="To Date"  styleClass="label"/>  
                            <h:inputText id="cToDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{AtmStatus.toDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="row4" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel1" layout="block" style="width:100%;text-align:center;">
                                <%--    <h:commandButton id="btnExcPrint1" value="Download Excel" actionListener="#{AtmStatus.cardexcelPrint}"/>  --%>
                                <a4j:commandButton id="btnPrint1" value="Print Report" action="#{AtmStatus.viewcardReport}" reRender="errorMsg"/>
                                <a4j:commandButton id="btnRefresh1" value="Refresh" action="#{AtmStatus.refreshform}" reRender="normalPanel" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnClose1" value="Exit" action="#{AtmStatus.exitBtnAction}" reRender="errorMsg"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="seqPanel" style="width:100%;display:#{AtmStatus.seqFileDisplay}">
                        <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                            <h:outputText value="Branch Wise" styleClass="label"/>
                            <h:selectOneListbox id="branchId" size="1" value="#{AtmStatus.branchCode}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{AtmStatus.branchCodeList}" />
                            </h:selectOneListbox> 
                            <h:outputText value="Report Type" styleClass="label"/>
                            <h:selectOneListbox id="reportID" size="1" value="#{AtmStatus.reportType}" styleClass="ddlist" style="width:80px">
                                <f:selectItems value="#{AtmStatus.reportTypeList}" />
                            </h:selectOneListbox>        
                        </h:panelGrid> 
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" styleClass="row2">       
                            <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                            <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{AtmStatus.calFromDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>  
                            <h:inputText id="calToDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{AtmStatus.caltoDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <h:commandButton id="btnExcPrint" value="Download Excel" actionListener="#{AtmStatus.excelPrint}"/>
                                <%--a4j:commandButton id="btnPrint" value="Print Report" action="#{AtmStatus.viewReport}" reRender="errorMsg"/--%>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AtmStatus.refresh}" reRender="a1" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnClose" value="Exit" action="#{AtmStatus.exitAction}" reRender="errorMsg"/>
                            </h:panelGroup>
                        </h:panelGrid>
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
