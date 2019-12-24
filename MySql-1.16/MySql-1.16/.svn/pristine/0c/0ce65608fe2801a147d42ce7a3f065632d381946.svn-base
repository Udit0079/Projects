<!--
    Document   : CashWithdrawal
    Created on : Jan 28, 2011, 10:32:26 AM
    Author     : Dhirendra Singh
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Pay Order Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="cashDrForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{PoAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Pay Order Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{PoAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="6" style="width: 100%;" styleClass="row2">
                        <h:panelGroup id="batchPanel" >
                            <h:outputLabel id="batchLbl" value="Remittance Authorization:" styleClass="label"/>
                            <h:selectOneListbox id="ddAcType" styleClass="ddlist" value="#{PoAuthorization.acType}" size="1">
                                <f:selectItems value="#{PoAuthorization.acTypeOption}" />
                                <a4j:support actionListener="#{PoAuthorization.getUnAuthPoDetails}" event="onchange"
                                             reRender="taskList,gridPanel3" ajaxSingle="true"/>
                            </h:selectOneListbox>
                        </h:panelGroup>

                    </h:panelGrid>
                    <h:panelGroup layout="block" id="gridPanelTable" style=" width:100%;height:300px;overflow:auto" styleClass="row1">
                        <rich:dataTable value="#{PoAuthorization.poDetailsItemList}" var="dataItem"
                                        rowClasses="gridrow1, gridrow2" id="taskList" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="40px"><h:outputText value="S. No."/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Inst No."/></rich:column>
                                    <rich:column width="60px"><h:outputText value="Seq. No."/></rich:column>
                                    <rich:column width="60px"><h:outputText value="Inst Type."/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Account No."/></rich:column>

                                    <rich:column width="175px"><h:outputText value="Customer Name"/></rich:column>
                                    <rich:column width="100px"><h:outputText value="Payable At"/></rich:column>
                                    <rich:column width="70px"><h:outputText value="Amount"/></rich:column>
                                    <rich:column width="200px"><h:outputText value="In Favour Of"/></rich:column>
                                    <rich:column width="50px"><h:outputText value="Status"/></rich:column>

                                    <rich:column width="45px"><h:outputText value="Auth"/></rich:column>
                                    <rich:column width="65px"><h:outputText value="Enter By"/></rich:column>
                                    <rich:column width="45px"><h:outputText value="Rec No"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column><h:outputText value="#{dataItem.sNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.seqNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.billType}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.payableAt}" /></rich:column>

                            <rich:column>
                                <h:outputText value="#{dataItem.amount}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText value="#{dataItem.infavourOf}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                            <rich:column style="text-align:center;cursor:pointer;">
                                <h:outputText value="#{dataItem.auth}" />
                                <a4j:support action="#{PoAuthorization.changeValue}" ajaxSingle="true" event="ondblclick" reRender="taskList,gridPanel3">
                                    <%--<f:setPropertyActionListener value="#{dataItem}" target="#{PoAuthorization.currentItem}"/> --%>
                                    <f:setPropertyActionListener value="#{row}" target="#{PoAuthorization.currentRow}"/>
                                </a4j:support>
                            </rich:column>
                            <rich:column><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.recNo}">
                                    <f:convertNumber type="number" pattern="#"/>
                                </h:outputText>
                            </rich:column>

                        </rich:dataTable>
                    </h:panelGroup>
                    <h:panelGrid columns="2" id="gridPanel3" style="width: 100%; " styleClass="row2">
                        <h:outputText id="message" styleClass="msg" value="#{PoAuthorization.message}"/>
                        <h:outputText id="errMsg" styleClass="error" value="#{PoAuthorization.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanelBtn" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="gridPaneGrp" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Authorize" reRender="taskList,gridPanel3" action="#{PoAuthorization.authorizePoDetail}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="taskList,batchPanel,gridPanel3" action="#{PoAuthorization.refresh}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{PoAuthorization.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>