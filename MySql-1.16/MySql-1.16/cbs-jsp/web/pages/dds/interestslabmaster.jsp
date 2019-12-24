<%--
 Document : interestslabmaster
 Created on : Aug 19, 2011, 2:28:29 PM
 Author : Himanshu Bhatia
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Daily Deposit Scheme Slab</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="InterestSlabMaster"/>
            <a4j:form id="interestslab">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{InterestSlabMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Daily Deposit Scheme Slab"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{InterestSlabMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errPanel" styleClass="row1" style="text-align: center;">
                        <h:outputLabel id="errmsg" value="#{InterestSlabMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid1" width="100%" columns="4" columnClasses="col3,col3,col3,col3"styleClass="row2">
                        <h:outputLabel value="Applicable Date" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtApplicableDate" styleClass="input calInstDate" style="width:70px;" maxlength="10" value="#{InterestSlabMaster.applicableDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromProduct" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel value="DDS Rate (in %)" styleClass="label"/>
                        <h:inputText id="txtInterestRate" styleClass="input" value="#{InterestSlabMaster.interestRate}" maxlength="6" style="width:70px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid2" styleClass="row1" width="100%" columns="4" columnClasses="col3,col3,col3,col3">
                        <h:outputLabel value="From Period(In Months)" styleClass="label"/>
                        <h:inputText id="txtFromPeriod" styleClass="input" value="#{InterestSlabMaster.fromPeriod}" maxlength="6" style="width:70px;"/>
                        <h:outputLabel value="To Period(In Months)" styleClass="label"/>
                        <h:inputText id="txtToPeriod" styleClass="input" value="#{InterestSlabMaster.toPeriod}" maxlength="6" style="width:70px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" styleClass="row2" width="100%" columns="4" columnClasses="col3,col3,col3,col3">
                        <h:outputLabel value="From Amount" styleClass="label"/>
                        <h:inputText id="txtFromAmt" styleClass="input" value="#{InterestSlabMaster.fromAmt}" style="width:70px;"/>
                        <h:outputLabel value="To Amount" styleClass="label"/>
                        <h:inputText id="txtToAmt" styleClass="input" value="#{InterestSlabMaster.toAmt}" style="width:70px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid4" styleClass="row1" width="100%" columns="4" columnClasses="col3,col3,col3,col3">
                        <h:outputLabel value="Commission %" styleClass="label"/>
                        <h:inputText id="txtComm" styleClass="input" value="#{InterestSlabMaster.comm}" maxlength="5" style="width:70px;"/>
                        <h:outputLabel value="Surcharge %" styleClass="label"/>
                        <h:inputText id="txtSurChg" styleClass="input" value="#{InterestSlabMaster.surChg}" maxlength="5" style="width:70px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid5" styleClass="row2" width="100%" columns="4" columnClasses="col3,col3,col3,col3">
                        <h:outputLabel value="Agent Tax %" styleClass="label"/>
                        <h:inputText id="txtAgtTax" styleClass="input" value="#{InterestSlabMaster.agtTax}" maxlength="5" style="width:70px;"/>
                        <h:outputLabel value="Agent Security %" styleClass="label"/>
                        <h:inputText id="txtAgSec" styleClass="input" value="#{InterestSlabMaster.agSecDep}" maxlength="5" style="width:70px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid6" styleClass="row1" width="100%" columns="4" columnClasses="col3,col3,col3,col3">
                        <h:outputLabel value="Daily Bal" styleClass="label"/>
                        <h:inputText id="txtDBal" styleClass="input" value="#{InterestSlabMaster.dBal}" style="width:70px;"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row2" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable value="#{InterestSlabMaster.datagrid}" var="dataItem1"
                                            rowClasses="gridrow1, gridrow2" id="taskList1" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                            onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column style="text-align:center"><h:outputText value="Applicable Date"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="DDS Rate(%)"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="From Period (In Months)"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="To Period (In Months)"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="From Amt"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="To Amt"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Commission %"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Surcharge %"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Agent Tax %"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Agent Sec Dep %"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Daily Bal"/></rich:column>
                                        <rich:column style="text-align:center"><h:outputText value="Entered By"/></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.applicableDate}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.interestrate}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.fromPeriod}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.toPeriod}"/></rich:column>                                
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.frmAmt}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.toAmt}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.commPerc}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.surChg}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.agiTax}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.agDecDep}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.dayBal}"/></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem1.enteredby}"/></rich:column>                                
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="saveBtn" value="Save" action="#{InterestSlabMaster.btnSave}" disabled="#{InterestSlabMaster.savebtn}" oncomplete="setMask();" reRender="saveBtn,updateBtn,taskList1,errmsg,txtApplicableDate,txtInterestRate,txtFromPeriod,txtToPeriod,taskList1,txtFromPeriod,txtToPeriod,txtFromAmt,txtToAmt,txtComm,txtSurChg,txtAgtTax,txtAgSec,txtDBal"/>
                            <a4j:commandButton id="refreshBtn" value="Refresh" action="#{InterestSlabMaster.btnRefresh}" oncomplete="setMask();" reRender="errmsg,saveBtn,updateBtn,taskList1,txtApplicableDate,txtInterestRate,txtFromPeriod,txtToPeriod,txtFromPeriod,txtToPeriod,txtFromAmt,txtToAmt,txtComm,txtSurChg,txtAgtTax,txtAgSec,txtDBal"/>
                            <a4j:commandButton id="exitBtn" value="Exit" action="#{InterestSlabMaster.btnExit}" reRender="PanelGridMain"/>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
