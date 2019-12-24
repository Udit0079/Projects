<%-- 
    Document   : financialSalaryProjection
    Created on : 29 Dec, 2017, 3:50:26 PM
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
            <title> Financial Salary Projection</title>
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
                            <h:outputText id="stDate" styleClass="output" value="#{FinancialSalaryProjection.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Financial Salary Projection"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FinancialSalaryProjection.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{FinancialSalaryProjection.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="SalaryProjectionCategory" styleClass="output" value="Salary Projection Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="SalaryProjectionCategoryList" styleClass="ddlist" size="1" style="width:150px" value="#{FinancialSalaryProjection.projectionCateory}">
                                <f:selectItems value="#{FinancialSalaryProjection.projectionCateoryList}"/>
                                <a4j:support event="onblur" action="#{FinancialSalaryProjection.onChangeCategory}" oncomplete="if(#{FinancialSalaryProjection.employeeWise=='true'})
                                             {#{rich:component('empSearchModelPanel')}.show(); } else{#{rich:component('empSearchModelPanel')}.hide();}"
                                             reRender="salaryTable,salaryGrid,categorizationList,taskListsalaryProjectionCategoryList,errMsg,PanelGridEmptable"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="categorizationDetails" styleClass="output" value="Categorization Details"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox disabled="#{FinancialSalaryProjection.disableCategorizeDetails}" id="categorizationList" styleClass="ddlist" size="1" style="width:120px" value="#{FinancialSalaryProjection.categorizationDetails}">
                                <f:selectItems value="#{FinancialSalaryProjection.categorizationDetailsList}"/>
                            </h:selectOneListbox>
                     </h:panelGrid>
                      <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel5" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="dateFrom" styleClass="output" value="Date From"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar   enableManualInput="true" id="fromDate" datePattern="dd/MM/yyyy" value="#{FinancialSalaryProjection.pageFromDate}" inputSize="10"/>
                            <h:outputLabel id="DateTo" styleClass="output" value="Date To"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar  enableManualInput="true" id="toDate" datePattern="dd/MM/yyyy" value="#{FinancialSalaryProjection.pageToDate}" inputSize="10"/>
                      </h:panelGrid>  
                      <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="footerPanel">
                                <h:commandButton id="save" value="PDF Download" action="#{FinancialSalaryProjection.financialProjectionRep}" />
                                <a4j:commandButton id="cancel" value="Refresh" action="#{FinancialSalaryProjection.refresh}" reRender="save,postSalaryButton,categorizationList,salaryTable,salaryGrid,SalaryProjectionCategoryList,errMsg,PanelGridEmptable,fromDate,toDate,ddReportType"/>
                                <a4j:commandButton id="exit" value="Exit" action="#{FinancialSalaryProjection.btnExitAction}" reRender="monthCategoryList,save,postSalaryButton,monthCategoryList,categorizationList,salaryTable,salaryGrid,categorizationList,salaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                            </h:panelGroup>
                      </h:panelGrid>
                    </h:panelGrid>
                    
                </a4j:form>
                <rich:modalPanel id="empSearchModelPanel" width="600" moveable="false" height="250" onmaskdblclick="#{rich:component('empSearchModelPanel')}.hide();">
                    <f:facet name="header">
                        <h:panelGrid style="width:100%;text-align:center;">
                            <h:outputText value="Employee Details" style="text-align:center;"/>
                        </h:panelGrid>
                    </f:facet>
                    <h:form>
                        <h:panelGrid id="PanelGridEmptable" bgcolor="#fff" styleClass="row2" columns="1" width="100%">
                            <h:panelGrid  id="gridPanel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="text-align:center;" width="100%">
                                <h:outputText/>
                                <h:outputLabel value="Serarch Criteria" styleClass="label"/>
                                <h:selectOneListbox id="ddSearchCriteria" value="#{FinancialSalaryProjection.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="ID"/>
                                    <f:selectItem itemValue="Name"/>
                                </h:selectOneListbox>
                                <h:inputText maxlength="20" id="txtSearchValue" value="#{FinancialSalaryProjection.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support event="onblur" action="#{FinancialSalaryProjection.getEmployeeDetails}" reRender="emptablepanel,EmpDetail,noOfRows,table1,taskList1"/>
                                </h:inputText>
                                <h:outputText/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                                <rich:dataTable value="#{FinancialSalaryProjection.empSearchTable}"  var="dataItem" rowKeyVar="row" width="100%"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}"/></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{FinancialSalaryProjection.setEmpRowValues}"  oncomplete="#{rich:component('empSearchModelPanel')}.hide();"  
                                                     reRender="save,errMsg,salaryProcessingCategoryList,categorizationList" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{FinancialSalaryProjection.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <f:facet name="footer">
                                    <h:outputText value="#{FinancialSalaryProjection.totalEmpRecords} active employees found" style="text-align:center" />
                                </f:facet>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="empSearchFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="closeSearch" value="Close" onclick="#{rich:component('empSearchModelPanel')}.hide();"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

