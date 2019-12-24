<%-- 
    Document   : tdCondition
    Created on : May 13, 2010, 11:04:28 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <title>TD Condition </title>
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{tdCondition.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="TD Condition"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{tdCondition.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label1" styleClass="label" style="width:100px" value="Customer Type"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddCustType" styleClass="ddlist" size="1" style="width:100px" value="#{tdCondition.custType}" tabindex="1">
                                    <f:selectItem itemValue="--SELECT--"/>
                                    <f:selectItem itemValue="NEW"/>
                                    <f:selectItem itemValue="RENEWAL"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label12" styleClass="label" value="Monthly(In Days)" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtMonthlyInDays" styleClass="input" style="width:100px" value="#{tdCondition.tdMonthly}" tabindex="3" maxlength="9">

                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanela" style="height:30px;" styleClass="row1" width="100%">

                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid  columns="1" id="gridPanel103" width="100%" >
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel14" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label9" styleClass="label" value="TD-Amount" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtTdAmount" styleClass="input" style="width:100px" value="#{tdCondition.tdAmounts}" tabindex="2" maxlength="9">

                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel17" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="label13" styleClass="label" value="Cumulative(In Days)" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCumulativeInD" styleClass="input" style="width:100px" value="#{tdCondition.tdCumulative}" tabindex="4" maxlength="9">

                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelb" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="labelb" styleClass="label" value="Applicable Date" ><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calApplicableDate" value="#{tdCondition.dates}" tabindex="5" inputSize="10" >
                                    <a4j:support action="#{tdCondition.onblurApplicableDate}"  event="onchanged" focus="btnList"
                                                 reRender="stxtMsg"  />
                                </rich:calendar>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" id="gridPanele" width="100%" styleClass="row2" style="height:200px;">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{tdCondition.fetchCurrentRow}">
                                <a4j:actionparam name="acNo" value="{acNo}" />
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>
                        <a4j:region>
                            <rich:dataTable value="#{tdCondition.condits}" var="dataItem" rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="TD Condition" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Type" /></rich:column>
                                        <rich:column><h:outputText value="Applicable Date" /></rich:column>
                                        <rich:column><h:outputText value="TD Amount" /></rich:column>
                                        <rich:column><h:outputText value="Monthly" /></rich:column>
                                        <rich:column><h:outputText value="Cumulative" /></rich:column>
                                        <rich:column><h:outputText value="EnterBy" /></rich:column>
                                        <rich:column><h:outputText value="Last Update" /></rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sNumber}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.applicableDate}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.tdAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.tdDayMonthly}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.tdDayCumulat}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lastUpDateDt}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{tdCondition.setRowValues}"
                                                     reRender="ddCustType,btnSave,btnUpdate,btnDelete,txtMonthlyInDays,calApplicableDate,txtTdAmount,txtCumulativeInD" >
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{tdCondition.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{tdCondition.currentRow}"/>
                                    </a4j:commandLink>

                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel001" style="height:30px;text-align:center;" styleClass="row1" width="100%"  >
                        <h:outputText id="stxtMsg" styleClass="error" value="#{tdCondition.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnList" value="List" action="#{tdCondition.getList}"  reRender="gridPanele,stxtMsg" tabindex="6"/>
                            <a4j:commandButton id="btnSave" value="Save" action="#{tdCondition.save}" reRender="stxtMsg,ddCustType,gridPanele,calApplicableDate,txtTdAmount,txtCumulativeInD,txtMonthlyInDays" tabindex="7" disabled="#{tdCondition.flag}" />
                            <a4j:commandButton id="btnUpdate" value="Update" action="#{tdCondition.upDate}" reRender="ddCustType,calApplicableDate,gridPanele,txtTdAmount,txtCumulativeInD,txtMonthlyInDays,btnSave,btnUpdate,btnDelete,stxtMsg" disabled="#{tdCondition.flag1}"/>
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{tdCondition.delete}" reRender="ddCustType,calApplicableDate,gridPanele,txtTdAmount,txtCumulativeInD,txtMonthlyInDays,btnSave,btnUpdate,btnDelete,stxtMsg" disabled="#{tdCondition.flag2}"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{tdCondition.clearText}" reRender="txtTdAmount,txtCumulativeInD,ddCustType,txtMonthlyInDays,gridPanele,btnSave,calApplicableDate,btnUpdate,btnDelete,stxtMsg"  tabindex="9" focus="ddCustType"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{tdCondition.exitFrm}" tabindex="8"/>                            
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
