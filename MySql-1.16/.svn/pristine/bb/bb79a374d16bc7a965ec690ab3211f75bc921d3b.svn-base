<%-- 
    Document   : ddsreceiptmaster
    Created on : Nov 25, 2011, 10:56:20 AM
    Author     : Navneet Goyal
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
            <title>DDS Receipt Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:keepAlive beanName="DDSReceiptMaster"/>
            <a4j:form id="DDSReceiptMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date:"/>
                            <h:outputText styleClass="output" value="#{DDSReceiptMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="DDS Receipt Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User name: "/>
                            <h:outputText styleClass="output" value="#{DDSReceiptMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel"  width="100%" style="text-align:center" styleClass="row1">
                        <h:outputText id="errMsg" value="#{DDSReceiptMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel value="Receipt From" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtReceiptFrom" value="#{DDSReceiptMaster.receiptFrom}" maxlength="8" styleClass="input" disabled="#{DDSReceiptMaster.disableReceiptFrom}"/>
                        <h:outputLabel value="Receipt To" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText value="#{DDSReceiptMaster.receiptTo}" maxlength="8" styleClass="input" disabled="#{DDSReceiptMaster.disableReceiptTo}">
                            <a4j:support event="onblur" ajaxSingle="true" focus="btnSave"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="table1" columnClasses="vtop" columns="1" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable id="taskList1" value="#{DDSReceiptMaster.receiptMasterTable}" var="dataItem" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="30%"><h:outputText value="Receipt From" style="text-align:center"/></rich:column>
                                        <rich:column width="25%"><h:outputText value="Receipt To" style="text-align:left" /></rich:column>
                                        <rich:column width="30%"><h:outputText value="Number of Leaves" style="text-align:left"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="Delete" style="text-align:left" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptFrom}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptTo}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.leafs}"></h:outputText></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="deleteLink" action="#{DDSReceiptMaster.setRowValues}" reRender="mainPanel" ajaxSingle="true" focus="btnDelete">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DDSReceiptMaster.currentRmt}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="btnSave" value="Save" action="#{DDSReceiptMaster.saveAction}" reRender="mainPanel" disabled="#{DDSReceiptMaster.disableSaveButton}" focus="txtReceiptFrom"/>
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{DDSReceiptMaster.deleteAction}" reRender="mainPanel" disabled="#{DDSReceiptMaster.disableDeleteButton}" focus="txtReceiptFrom"/>
                            <a4j:commandButton id="btnCancel" value="Cancel" action="#{DDSReceiptMaster.cancelAction}" reRender="mainPanel" focus="txtReceiptFrom"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DDSReceiptMaster.exitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
