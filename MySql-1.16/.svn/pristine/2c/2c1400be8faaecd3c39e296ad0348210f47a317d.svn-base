<%-- 
    Document   : acTransferAuthorization
    Created on : Jan 18, 2012, 7:11:01 PM
    Author     : Ankit Verma
--%>

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
            <title>Account transfer Authorization</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="cashDrForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AcTransferAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Account transfer Authorization"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AcTransferAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                     <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{AcTransferAuthorization.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px;" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                        <rich:dataTable value="#{AcTransferAuthorization.acTrfList}" var="dataItem"
                                            rowClasses="gridrow1, gridrow2" id="taskList" rows="3" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                            onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;" >
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column ><h:outputText value="S. No."/></rich:column>
                                        <rich:column ><h:outputText value="Account no."/></rich:column>
                                        <rich:column ><h:outputText value="From Branch"/></rich:column>
                                        <rich:column ><h:outputText value="To Branch"/></rich:column>
                                        <rich:column ><h:outputText value="Auth"/></rich:column>
                                        <rich:column ><h:outputText value="Enter by"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>

                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.sno}"/></rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column style="text-align:center;"><h:outputText value="#{dataItem.fromBranch}" /></rich:column>
                                <rich:column style="text-align:center;"><h:outputText value="#{dataItem.toBranch}" /></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;">
                                    <h:outputText value="#{dataItem.auth}" />
                                    <a4j:support action="#{AcTransferAuthorization.changeValue}" ajaxSingle="true" event="ondblclick" reRender="taskList,stxtError">
                                        <f:setPropertyActionListener value="#{row}" target="#{AcTransferAuthorization.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                                <rich:column style="text-align:center;"><h:outputText value="#{dataItem.enterBy}"/></rich:column>
                            </rich:dataTable>
                         <rich:datascroller id="selectedListScroller" align="left" for="taskList" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpanelWarn"  styleClass="row2" width="100%" >
                        <h:outputText  styleClass="error"  style="color:green;font-weight:bold;" value="Please double click on Auth flag for authorizing an entry !!"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanelBtn" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="gridPaneGrp" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Authorize" reRender="taskList,stxtError" action="#{AcTransferAuthorization.authorizeAcTransfer}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" reRender="taskList,batchPanel,stxtError" action="#{AcTransferAuthorization.refreshForm}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AcTransferAuthorization.btnExitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>