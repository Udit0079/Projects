<%-- 
    Document   : termDepositHis
    Created on : Jan 7, 2013, 3:10:31 PM
    Author     : sipl
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Term Deposit Interest History</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{termDepositHis.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Term Deposit Interest History"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label1" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{termDepositHis.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col2,col3,col3,col3,col3" columns="6" id="gridPanel1" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblacNature" styleClass="label" value="Nature"/>
                        <h:selectOneListbox id="ddFunction" value="#{termDepositHis.nature}" styleClass="ddlist" size="1" style="width:90px">
                            <f:selectItems value="#{termDepositHis.natureList}"/>
                            <a4j:support actionListener="#{termDepositHis.changeFunction()}" event="onblur" reRender="taskList,gridPanel14"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid  columnClasses="vtop" columns="1" id="gridPanel14" width="100%" styleClass="row2" style="height:150px;">
                        <rich:dataTable rows="15" value="#{termDepositHis.tableDtList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList" columnsWidth="100" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="12"><h:outputText value="Term Deposit Interest History" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"/></rich:column>
                                    <rich:column><h:outputText value="Applicable Date"/></rich:column>
                                    <rich:column><h:outputText value="Interest Rate (%)"/></rich:column>
                                    <rich:column><h:outputText value="From Period"/></rich:column>
                                    <rich:column><h:outputText value="To Period" /></rich:column>
                                    <rich:column><h:outputText value="From Amount (Rs)"/></rich:column>
                                    <rich:column><h:outputText value="To Amount (Rs)"/></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Sr. Citizen)"/></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Staff)" /></rich:column>
                                    <rich:column><h:outputText value="Add.Int.(Other)" /></rich:column> 
                                    <rich:column><h:outputText value="Add.Int.(Minor Girl)" /></rich:column>
                                    <rich:column><h:outputText value="Nature" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.seqNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.applicableDate}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.intRate}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.fromPeriod}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.toPeriod}"/></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.fromAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.toAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntSr}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntSt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntOt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.addIntMg}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.acNature}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="15"/>                        
                    </h:panelGrid>                   
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">                            
                            <a4j:commandButton id="btnExit" value="Exit" action="#{termDepositHis.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>            
        </body>
    </html>
</f:view>