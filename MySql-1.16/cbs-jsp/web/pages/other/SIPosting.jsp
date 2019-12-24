<%--
    Document   : standingInstructionPosting
    Created on : May 14, 2010, 2:14:40 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>standing Instruction Posting</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{SIPosting.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Standing Instruction Posting"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SIPosting.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" style="height:30px;" columns="2" id="gridPanel15"  styleClass="row1" width="100%">
                        <h:outputLabel id="lblInstructionType" styleClass="label" style="padding-left:350px" value="Instruction Type"><font class="required">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddInstructionType" styleClass="ddlist" size="1" style="width: 103px" value="#{SIPosting.instructionType}">
                                <f:selectItem itemValue="--SELECT--"/>
                                <f:selectItem itemValue="TRANSACTION"/>
                                <f:selectItem itemValue="GENERAL"/>
                                <a4j:support ajaxSingle="true" action="#{SIPosting.getPostGridDetails}" event="onchange" reRender="p4,stxtMsg" limitToList="true" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:335px;">
                            <h:panelGroup id="p4">
                                <a4j:region>
                                    <rich:dataTable value="#{SIPosting.siTransTables}" var="dataItem" rendered="#{SIPosting.instructionType == 'TRANSACTION'}" 
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="14"><h:outputText value="Standing Instruction Master" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Instruction No." /></rich:column>
                                                <rich:column><h:outputText value="S. No" /></rich:column>
                                                <rich:column><h:outputText value="Debit A/C" /></rich:column>
                                                <rich:column><h:outputText value="Debit Customer Name" /></rich:column>
                                                <rich:column><h:outputText value="Credit A/C" /></rich:column>
                                                <rich:column><h:outputText value="Credit Customer Name" /></rich:column>
                                                <rich:column><h:outputText value="By Amount" /></rich:column>
                                                <rich:column><h:outputText value="Effective Date" /></rich:column>
                                                <rich:column><h:outputText value="Status" /></rich:column>
                                                <rich:column><h:outputText value="Remarks" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column>
                                                <rich:column><h:outputText value="EnterDate" /></rich:column>
                                                <rich:column><h:outputText value="Charge Flag" /></rich:column>
                                                <rich:column><h:outputText value="Expiry Dt" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.instrNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.sNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.fromAcno}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.fromCustName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.toAcno}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.toCustName}" /></rich:column>
                                        <rich:column>
                                            <h:outputText value="#{dataItem.amount}">
                                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column><h:outputText value="#{dataItem.effDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.entryDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.chargeFlag}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.expiryDt}" /></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList" maxPages="20" rendered="#{SIPosting.instructionType == 'TRANSACTION'}"/>
                                </a4j:region>
                                <a4j:region>
                                    <rich:dataTable value="#{SIPosting.siTransGeneTbs}" var="Item" rendered="#{SIPosting.instructionType == 'GENERAL'}" rowClasses="gridrow1,gridrow2" id="taskList1" rows="4" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="8"><h:outputText value="Standing Instruction Master" /></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Instruction No." /></rich:column>
                                                <rich:column><h:outputText value="S. No" /></rich:column>
                                                <rich:column><h:outputText value="Acno" /></rich:column>
                                                <rich:column><h:outputText value="Effective Date" /></rich:column>
                                                <rich:column><h:outputText value="Status" /></rich:column>
                                                <rich:column><h:outputText value="Remarks" /></rich:column>
                                                <rich:column><h:outputText value="EntryDate" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{Item.instrNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.sNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.acno}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.effDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.status}" />
                                            <a4j:support action="#{SIPosting.changeValue}" ajaxSingle="true" event="ondblclick" reRender="taskList1">
                                                <f:setPropertyActionListener value="#{Item}" target="#{SIPosting.currentItem}"/>
                                                <f:setPropertyActionListener value="#{row}" target="#{SIPosting.currentRow}"/>
                                            </a4j:support>
                                        </rich:column>
                                        <rich:column><h:outputText value="#{Item.remarks}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.enterBy}" /></rich:column>
                                        <rich:column><h:outputText value="#{Item.entryDate}" /></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="20" rendered="#{SIPosting.instructionType == 'GENERAL'}"/>
                                </a4j:region>
                                <h:panelGrid columnClasses="col8,col8" columns="1" id="gridPanel004561" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" styleClass="row2" width="100%">
                                    <h:outputText id="stxtMsg" styleClass="msg" value="#{SIPosting.errorMsg}"/>
                                </h:panelGrid>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnPost" style="width:60px;" value="Post" action="#{SIPosting.postingData}" oncomplete="if(#{SIPosting.instructionType=='TRANSACTION'}){#{rich:component('reportView')}.show();}"   
                                                   reRender="reportView,p4,stxtMsg,taskList,gridPanel103,taskList1,ddInstructionType"/>
                                <a4j:commandButton id="btnRefresh" style="width:60px;" value="Refresh" action="#{SIPosting.resetForm}" reRender="stxtMsg,taskList,gridPanel103,taskList1,ddInstructionType"/>
                                <a4j:commandButton id="btnExit" action="#{SIPosting.exitForm}" style="width:60px;" value="Exit" reRender="stxtMsg,taskList,gridPanel103,taskList1,ddInstructionType"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                    <rich:messages></rich:messages>
                </a4j:form>
                <rich:modalPanel id="reportView" height="560" width="900" left="true">
                    <f:facet name="header">
                        <h:outputText value="Detail"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="reportView" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="acViewPanel" width="100%">
                            <h:panelGrid id="acViewAuthGrid" width="100%" style="background-color:#e8eef7;height:150px" columnClasses="vtop">
                                <rich:dataTable  value="#{SIPosting.reportTables}" var="dataItem4"
                                                 rowClasses="gridrow1, gridrow2" id="authTxnList" rows="8"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                 width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="14"><h:outputText value="Error Report Details" /></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="Instruction No." /></rich:column>
                                            <rich:column><h:outputText value="S. No" /></rich:column>
                                            <rich:column><h:outputText value="Debit A/C" /></rich:column>
                                            <rich:column><h:outputText value="Debit Customer Name" /></rich:column>
                                            <rich:column><h:outputText value="Credit A/C" /></rich:column>
                                            <rich:column><h:outputText value="Credit Customer Name" /></rich:column>
                                            <rich:column><h:outputText value="By Amount" /></rich:column>
                                            <rich:column><h:outputText value="Effective Date" /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="Remarks" /></rich:column>
                                            <rich:column><h:outputText value="Enter By" /></rich:column>
                                            <rich:column><h:outputText value="EnterDate" /></rich:column>
                                            <rich:column><h:outputText value="Charge Flag" /></rich:column>
                                            <rich:column><h:outputText value="Expiry Dt" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem4.instrNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.sNo}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.fromAcno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.fromCustName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.toAcno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.toCustName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.amount}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.effDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.status}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.remarks}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.enterBy}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.entryDate}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.chargeFlag}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem4.expiryDt}" /></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="authTxnList" maxPages="20" />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col8,col8" columns="1" id="gridPanel001" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" styleClass="row1" width="100%">
                                <h:outputText id="stxtMsg" styleClass="msg" value="#{SIPosting.message}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose" value="Close" onclick="#{rich:component('reportView')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
        </body>
    </html>
</f:view>
