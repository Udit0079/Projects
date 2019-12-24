<%-- 
    Document   : loanOutStandingBalance
    Created on : Feb 3, 2014, 3:32:50 PM
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
            <title>Deposit and Loan Balance Wise Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanOutStandingBalance.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Deposit and Loan Balance Wise Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanOutStandingBalance.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{LoanOutStandingBalance.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LoanOutStandingBalance.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{LoanOutStandingBalance.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputText value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccountNature" size="1" value="#{LoanOutStandingBalance.acNature}" styleClass="ddlist">
                            <f:selectItems value="#{LoanOutStandingBalance.acNatureList}" />
                            <%--a4j:support id="idnature" event="onblur" action="#{LoanOutStandingBalance.initData}" reRender="ddlAccountTypeTypeddl"/--%>
                        </h:selectOneListbox> 
                        <h:outputText value="Balance Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlBalanceTypeddl" size="1" value="#{LoanOutStandingBalance.balType}" style="width:70px" styleClass="ddlist">
                            <f:selectItems value="#{LoanOutStandingBalance.balTypeList}" />
                            <a4j:support id="idnature" event="onblur" action="#{LoanOutStandingBalance.initData}" reRender="ddlAccountTypeTypeddl"/>
                        </h:selectOneListbox>
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccountTypeTypeddl" size="1" value="#{LoanOutStandingBalance.acctType}" style="width:70px" styleClass="ddlist">
                            <f:selectItems value="#{LoanOutStandingBalance.acctTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" styleClass="row1" width="100%">
                        <h:outputText value="Till Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtToDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{LoanOutStandingBalance.toDt}"/>
                            <%--font color="purple">DD/MM/YYYY</font--%>
                        </h:panelGroup>                           
                        <h:outputLabel id="lblDisFrAmount" styleClass="label"  value="From Amount:"/>
                        <h:inputText id="txtdisAmt"  style="width:70px" value="#{LoanOutStandingBalance.frAmount}" styleClass="input" maxlength="12">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText> 
                        <h:outputLabel id="lblDisToAmount" styleClass="label"  value="To Amount:"/>
                        <h:inputText id="txtdistoAmt"  style="width:70px" value="#{LoanOutStandingBalance.toAmount}" styleClass="input" maxlength ="12">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText> 
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  value="Print Report" action="#{LoanOutStandingBalance.viewReport()}" reRender="errorMsg,txtFrmDate,txtToDate" oncomplete="#{rich:element('txtToDate')}.style=setMask()"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{LoanOutStandingBalance.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <h:commandButton id="btnExcelPrint" value="Excel Download" actionListener="#{LoanOutStandingBalance.excelPrint}"/>
                            <a4j:commandButton value="Refresh" action="#{LoanOutStandingBalance.refresh}" oncomplete="setMask()" reRender="txtToDate,a1"/>
                            <a4j:commandButton   value="Exit" action="#{LoanOutStandingBalance.exitAction}" oncomplete="setMask()" reRender="txtToDate,a1"/>
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
