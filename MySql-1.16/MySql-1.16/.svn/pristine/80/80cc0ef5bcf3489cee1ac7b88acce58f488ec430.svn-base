<%-- 
    Document   : salarySheet
    Created on : Jan 31, 2017, 12:54:20 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>SALARY SHEET </title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>            
        </head>
        <body>
            <a4j:form id="salarySheetForm">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SalarySheet.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="SALARY SHEET"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SalarySheet.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{SalarySheet.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="SalarySheetCategory" styleClass="output" value="Salary Sheet Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="SalarySheetCategoryList" styleClass="ddlist" size="1" style="width:150px" value="#{SalarySheet.sheetCateory}">
                            <f:selectItems value="#{SalarySheet.sheetCateoryList}"/>
                            <a4j:support event="onblur" action="#{SalarySheet.onChangeCategory}" oncomplete="if(#{SalarySheet.employeeWise=='true'})
                                    {#{rich:component('empSearchModelPanel')}.show(); } else{#{rich:component('empSearchModelPanel')}.hide();}"
                                    reRender="ddacType111,errorMsg,PanelGridEmptable"/>
                        </h:selectOneListbox>
                        <h:outputText id="lblacType" value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{SalarySheet.branchCode}" styleClass="ddlist"  style="width:110px" size="1" disabled="#{SalarySheet.disableCategorizeDetails}">
                            <f:selectItems value="#{SalarySheet.branchCodeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel4"  styleClass="row1">
                        <%--h:outputText id="lblrepType" value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{SalarySheet.repType}" styleClass="ddlist"  style="width:150px" size="1">
                        <f:selectItems id="selectRepTypeList111" value="#{SalarySheet.repList}" />
                        </h:selectOneListbox--%>
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 100px" value="#{SalarySheet.month}">
                                <f:selectItems value="#{SalarySheet.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{SalarySheet.year}" maxlength="4" size="5"/>
                        </h:panelGroup>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{SalarySheet.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SalarySheet.refreshPage}" reRender="a1" oncomplete="setMask()"/>
                            <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
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
                        <h:panelGrid  id="gridPanel77" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="text-align:center;" width="100%">
                            <h:outputText/>
                            <h:outputLabel value="Serarch Criteria" styleClass="label"/>
                            <h:selectOneListbox id="ddSearchCriteria" value="#{SalarySheet.empSearchCriteria}" size="1">
                                <f:selectItem itemValue="ID"/>
                                <f:selectItem itemValue="Name"/>
                            </h:selectOneListbox>
                            <h:inputText maxlength="20" id="txtSearchValue" value="#{SalarySheet.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support event="onblur" action="#{SalarySheet.getEmployeeDetails}" reRender="emptablepanel,EmpDetail,noOfRows,table1,taskList1"/>
                            </h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                            <rich:dataTable value="#{SalarySheet.empSearchTable}"  var="dataItem" rowKeyVar="row" width="100%"
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
                                    <a4j:commandLink id="selectlink" action="#{SalarySheet.setEmpRowValues}"  oncomplete="#{rich:component('empSearchModelPanel')}.hide();"  
                                                     reRender="save,errorMsg,ddacType111" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SalarySheet.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <f:facet name="footer">
                                    <h:outputText value="#{SalarySheet.totalEmpRecords} active employees found" style="text-align:center" />
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
        </body>
    </html>
</f:view>
