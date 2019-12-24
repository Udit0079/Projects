<%-- 
    Document   : EarningRecords
    Created on : Nov 9, 2017, 10:53:43 AM
    Author     : user
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
            <title>EARNING RECORDS</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="earningRecordsForm">
                <h:panelGrid columns="3" id="gridPanel1" styleClass="header" style="width:100%;text-align:center;">
                    <h:panelGroup id="a2" layout="block">
                        <h:outputLabel styleClass="headerLabel" value="Date: "/>
                        <h:outputText id="stxtDate" styleClass="output" value="#{EarningRecords.todayDate}"/>
                    </h:panelGroup>
                    <h:outputLabel styleClass="headerLabel" value="EARNING RECORDS"/>
                    <h:panelGroup layout="block">
                        <h:outputLabel styleClass="headerLabel" value="User: "/>
                        <h:outputText id="stxtUser" styleClass="output" value="#{EarningRecords.userName}"/>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{EarningRecords.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row1">
                        <h:panelGroup id="groupPanel4" layout="block">
                            <h:outputLabel value="Serarch Criteria" styleClass="label"/>
                            <h:selectOneListbox id="ddSearchCriteria" value="#{EarningRecords.empSearchCriteria}" size="1">
                                <f:selectItem itemValue="ID"/>
                                <f:selectItem itemValue="Name"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:panelGroup id="groupPanel5" layout="block">
                            <h:outputLabel id="EmpId" styleClass="output" value="Employee Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtEmpId" styleClass="input" style="width:70px;" value="#{EarningRecords.empTxtId}">
                                    <a4j:support event="onblur" action="#{EarningRecords.getEmployeeDetails}" reRender="table1,stxtEmpTxtName,stxtId"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:panelGroup id="groupPanel6" layout="block">
                                <h:outputLabel id="label3" styleClass="headerLabel" value="Employee Detail :"/>
                                <h:outputText id="stxtEmpTxtName" styleClass="output" value="#{EarningRecords.empTxtName}"/>
                            </h:panelGroup>
                            <h:panelGroup id="groupPanel7" layout="block">
                                <h:outputLabel id="label7" styleClass="headerLabel" value="Employee Id :"/>
                                <h:outputText id="stxtId" styleClass="output" value="#{EarningRecords.employeeId}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel3" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel value="From Date" styleClass="label"/>
                            <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{EarningRecords.frDt}" size="10">
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel value="To Date" styleClass="label"/>
                            <h:inputText id="toDt" styleClass="input frDt" maxlength="10" value="#{EarningRecords.toDt}" size="10">
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                        <rich:dataTable value="#{EarningRecords.empSearchTable}" var="dataItem" rowKeyVar="row" width="100%"
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
                                <a4j:commandLink id="selectlink" action="#{EarningRecords.setEmpRowValues}" 
                                                 reRender="errorMsg,stxtEmpTxtName,stxtId" focus="selectlink">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{EarningRecords.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                            <f:facet name="footer">
                                <h:outputText value="#{EarningRecords.totalEmpRecords} active employees found" style="text-align:center" />
                            </f:facet>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="10" />
                    </h:panelGrid>
                    <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel">
                            <h:commandButton id="pdf" value="PDF Download" action="#{EarningRecords.earningRecordRep}"/>
                            <h:commandButton id="excel" value="Execel Download" action="#{EarningRecords.earningRecordExl}"/>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{EarningRecords.refresh}" reRender="errorMsg,txtEmpId,stxtEmpTxtName,stxtId,frDt,toDt,table1"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{EarningRecords.btnExitAction}" reRender="errorMsg,txtEmpId,stxtEmpTxtName,stxtId,frDt,toDt,table1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </a4j:form>
        </body>
    </html>    
</f:view>

