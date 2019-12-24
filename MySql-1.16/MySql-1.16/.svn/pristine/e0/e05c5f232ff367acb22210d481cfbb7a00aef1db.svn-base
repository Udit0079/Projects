<%-- 
    Document   : consolidatedFinancialSalaryReport
    Created on : 23 Apr, 2019, 5:59:48 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Consolidated Financial Salary Report</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="financialSalaryProjectionForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{ConsolidatedFinancialSalaryReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Consolidated Financial Salary Report"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ConsolidatedFinancialSalaryReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{ConsolidatedFinancialSalaryReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel5" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="dateFrom" styleClass="output" value="Date From"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar   enableManualInput="true" id="fromDate" datePattern="dd/MM/yyyy" value="#{ConsolidatedFinancialSalaryReport.pageFromDate}" inputSize="10"/>
                            <h:outputLabel id="DateTo" styleClass="output" value="Date To"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar  enableManualInput="true" id="toDate" datePattern="dd/MM/yyyy" value="#{ConsolidatedFinancialSalaryReport.pageToDate}" inputSize="10"/>
                     </h:panelGrid>  
                     <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="footerPanel">
                                <h:commandButton id="save" value="PDF Download" action="#{ConsolidatedFinancialSalaryReport.financialProjectionRep}" />
                                <a4j:commandButton id="cancel" value="Refresh" action="#{ConsolidatedFinancialSalaryReport.refresh}" reRender="save,postSalaryButton,categorizationList,salaryTable,salaryGrid,SalaryProjectionCategoryList,errMsg,PanelGridEmptable,fromDate,toDate,ddReportType"/>
                                <a4j:commandButton id="exit" value="Exit" action="#{ConsolidatedFinancialSalaryReport.btnExitAction}" reRender="monthCategoryList,save,postSalaryButton,monthCategoryList,categorizationList,salaryTable,salaryGrid,categorizationList,salaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                            </h:panelGroup>
                     </h:panelGrid>
                    </h:panelGrid>  
                </a4j:form>
        </body>
       </html>
</f:view>
