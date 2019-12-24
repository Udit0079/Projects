<%-- 
    Document   : PayRollCalendar
    Created on : May 25, 2011, 3:00:09 PM
    Author     : Sudhir Kumar Bisht
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>PayRoll Calendar</title>
        </head>
        <body>
            <a4j:form id="payRollCalendarForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText value="#{PayRollCalendar.todayDate}" id="stxtDate" styleClass="output">
                            </h:outputText>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Payroll Calendar"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText value="#{PayRollCalendar.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorGrid" width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errorGridText" value="#{PayRollCalendar.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList"  styleClass="ddlist" value="#{PayRollCalendar.function}" style="width:100px">
                            <f:selectItems value="#{PayRollCalendar.functionList}"/>
                            <a4j:support event="onchange" action="#{PayRollCalendar.onChangeFunction}" reRender="popUpGridPanel,payrollCalendarGrid" oncomplete="if(#{PayRollCalendar.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else {  #{rich:component('popUpGridPanel')}.hide(); }"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col2,col7,col2,col7" width="100%"  styleClass="row1">
                        <h:outputLabel id="lblFrom" styleClass="label" value="From Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <rich:calendar  value="#{PayRollCalendar.dateFrom}" enableManualInput="true" datePattern="dd/MM/yyyy" id="frmDt" inputSize="10">
                            <f:attribute name="maxlength" value="10"/>
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </rich:calendar>
                        <h:outputLabel id="lblTO" styleClass="label" value="To Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <rich:calendar value="#{PayRollCalendar.dateTo}" enableManualInput="true" datePattern="dd/MM/yyyy" id="calTo" inputSize="10">
                            <f:attribute name="maxlength" value="10"/>
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </rich:calendar>
                    </h:panelGrid>
                    <h:panelGrid id="gridPane4" columns="4" columnClasses="col2,col7,col2,col7" width="100%" styleClass="row2">
                        <h:outputLabel id="closing" styleClass="label" value="Closing Criteria"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox disabled="#{PayRollCalendar.disableList}" id="closingCriteraList" styleClass="ddlist"  size="1" style="width:100px;" value="#{PayRollCalendar.selectCriteria}">
                            <f:selectItems value="#{PayRollCalendar.selectCriteriaList}"/>
                        </h:selectOneListbox>
                        <%--<a4j:commandButton disabled="#{PayRollCalendar.closeButton}"  id="closeYear" value="Close year" action="#{PayRollCalendar.closeYearButton}" reRender="taskList,payrollCalendarGrid,closeYear,save,frmDt,calTo,delete,errorGridText,closingCriteraList,gridPanelFunc"/>--%>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton  action="#{PayRollCalendar.saveDeletePayrollCalendar}" disabled="#{!PayRollCalendar.disableDelete}" id="save" value="Save" reRender="taskList,payrollCalendarGrid,closeYear,save,frmDt,calTo,delete,errorGridText,closingCriteraList,gridPanelFunc"/>
                            <a4j:commandButton id="delete" disabled="#{PayRollCalendar.disableDelete}" value="Delete" action="#{PayRollCalendar.saveDeletePayrollCalendar}"  reRender="taskList,payrollCalendarTable,closeYear,save,frmDt,calTo,delete,errorGridText,closingCriteraList,gridPanelFunc"/>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{PayRollCalendar.refresh}" reRender="taskList,payrollCalendarGrid,closeYear,save,frmDt,calTo,delete,errorGridText,closingCriteraList,gridPanelFunc"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{PayRollCalendar.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" minHeight="100" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                        <f:facet name="header">
                            <h:panelGrid style="width:100%;text-align:center;">
                                <h:outputText value="Payroll Calendar Details" style="text-align:center;"/>
                            </h:panelGrid>
                        </f:facet>
                        <h:panelGrid id="payrollCalendarGrid" style="width:100%;" styleClass="row1" columnClasses="vtop">
                            <rich:dataTable  value="#{PayRollCalendar.gridData}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="taskList" rows="10" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="42px"><h:outputText value="Date From"/></rich:column>
                                        <rich:column width="55px"><h:outputText value="Date To"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Status"/></rich:column>
                                        <rich:column width="80px"><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fromdate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.toDate}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                                <rich:column style="text-align:center">
                                    <a4j:commandLink id="selectlink" action="#{PayRollCalendar.setPayrollCalenderValues}"
                                                     reRender="closeYear,save,frmDt,calTo,delete,errorGridText,closingCriteraList" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" onclick="#{rich:component('popUpGridPanel')}.hide();"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{PayRollCalendar.payrollDbl}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                        </h:panelGrid>
                        <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                            <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                        </h:panelGrid>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
