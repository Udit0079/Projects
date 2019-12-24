<%-- 
    Document   : topDepositorBorrower
    Created on : Dec 13, 2013, 3:49:42 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Top Depositors and Borrowers</title>
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
            <a4j:form id="depositreport">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TopDepositorsAndBorrower.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Top Depositors and Borrowers"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TopDepositorsAndBorrower.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TopDepositorsAndBorrower.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel23" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">    
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{TopDepositorsAndBorrower.repType}" styleClass="ddlist">
                            <f:selectItems value="#{TopDepositorsAndBorrower.repTypeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{TopDepositorsAndBorrower.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{TopDepositorsAndBorrower.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="idnos" value="Number" styleClass="label"/>
                        <h:inputText id="idnumber" value="#{TopDepositorsAndBorrower.nos}" styleClass="input" maxlength="10" size="10"> 
                            <a4j:support event="onblur"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Option Type:" styleClass="label"/>
                        <h:selectOneListbox id="acctid" size="1" value="#{TopDepositorsAndBorrower.optionType}" styleClass="ddlist">
                            <f:selectItems value="#{TopDepositorsAndBorrower.optionTypeList}" /> 
                            <a4j:support action="#{TopDepositorsAndBorrower.getDisableOption}"event="onblur" oncomplete="setMask();" reRender="frDt,toDt,borrowerType,panel3,txtFrom,txtTo"/>
                        </h:selectOneListbox> 
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{TopDepositorsAndBorrower.frDt}" size="8"disabled="#{TopDepositorsAndBorrower.frDtDisable}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>  
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{TopDepositorsAndBorrower.toDt}" size="8">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="width:100%;text-align:left;display:#{TopDepositorsAndBorrower.disableTypePanel}">
                        <h:outputText value="Borrower Type" styleClass="label"/>
                        <h:selectOneListbox id="borrowerType" size="1" value="#{TopDepositorsAndBorrower.borrowerType}" styleClass="ddlist">
                            <f:selectItems value="#{TopDepositorsAndBorrower.borrowerTypeList}" />
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%" style="width:100%;text-align:left;" >       
                        <h:outputLabel value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFrom" value="#{TopDepositorsAndBorrower.from}" disabled="#{TopDepositorsAndBorrower.fromToDisable}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Amount" styleClass="label"/>
                        <h:inputText id="txtTo" value="#{TopDepositorsAndBorrower.to}" disabled="#{TopDepositorsAndBorrower.fromToDisable}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel></h:outputLabel>
                        <h:outputLabel></h:outputLabel>
                    </h:panelGrid>                
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="ajReason">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnPrint" value="Print Report" action="#{TopDepositorsAndBorrower.viewReport}" reRender="errorMsg"/>
                                <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{TopDepositorsAndBorrower.btnPdfAction}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                                <h:commandButton id="btnExcel" value="Download Excel" actionListener="#{TopDepositorsAndBorrower.printExcelReport}"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TopDepositorsAndBorrower.btnRefreshAction}" reRender="errorMsg,a1,idnos" oncomplete="setMask();"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{TopDepositorsAndBorrower.btnExitAction}" reRender="errorMsg" />
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="ajReason"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
