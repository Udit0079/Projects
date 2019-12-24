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
            <title>DDS Issue Receipt Book</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:keepAlive beanName="DDSIssueReceipt"/>
            <a4j:form id="DDSIssueReceipt">
                <h:panelGrid id="chiefPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date:"/>
                            <h:outputText styleClass="output" value="#{DDSIssueReceipt.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="DDS Issue Receipt Book"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User name: "/>
                            <h:outputText styleClass="output" value="#{DDSIssueReceipt.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="errorPanel"  width="100%" style="text-align:center" styleClass="row1">
                        <h:outputText id="errMsg" value="#{DDSIssueReceipt.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel value="Branch Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranches" styleClass="ddlist" size="1" value="#{DDSIssueReceipt.branchCode}" disabled="#{DDSIssueReceipt.disableBranchName}">
                            <f:selectItems value="#{DDSIssueReceipt.branchCodeList}"/>
                            <%--<a4j:support event="onchange" action="#{DDSIssueReceipt.onChangeBranch}" reRender="errorPanel,ddAgents" focus="ddAgents"/>--%>
                        </h:selectOneListbox>
                        <h:outputLabel value="Agent Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAgents" styleClass="ddlist" size="1" value="#{DDSIssueReceipt.agentCode}" disabled="#{DDSIssueReceipt.disableAgentName}">
                            <f:selectItems value="#{DDSIssueReceipt.agentCodeList}"/>
                            <a4j:support event="onchange" focus="btnSave"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="panel2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" styleClass="row2">
                        <h:outputLabel/>
                        <h:outputLabel value="Receipt From" styleClass="label"/>
                        <h:outputText value="#{DDSIssueReceipt.receiptFrom}" styleClass="label"></h:outputText>
                        <h:outputLabel value="Receipt To" styleClass="label"/>
                        <h:outputText value="#{DDSIssueReceipt.receiptTo}" styleClass="label"></h:outputText>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid style="text-align:center" styleClass="labelHeader">
                        <h:outputLabel value="Available Receipt Series" styleClass="label" style="font-weight:bold;"/>
                    </h:panelGrid>

                    <h:panelGrid id="table1" columnClasses="vtop" columns="1" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable id="taskList1" value="#{DDSIssueReceipt.receiptMasterTable1}" var="dataItem" rows="5" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Receipt From" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Receipt To" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Number of Leaves" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptFrom}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptTo}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.leafs}"></h:outputText></rich:column>
                                <rich:column style="text-align:center;">
                                    <a4j:commandLink id="selectLink" action="#{DDSIssueReceipt.setRowValues}" reRender="panel2,footerPanel" ajaxSingle="true" focus="btnSave">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DDSIssueReceipt.currentRmt1}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                        <h:panelGrid id="errorPanel1"  width="100%" style="text-align:center" styleClass="row1">
                            <h:outputText id="errMsg1" value="#{DDSIssueReceipt.message1}" styleClass="msg"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid style="text-align:center" styleClass="labelHeader">
                        <h:outputLabel value="Allocated Receipt Series" styleClass="label" style="font-weight:bold;"/>
                    </h:panelGrid>

                    <h:panelGrid id="table2" columnClasses="vtop" columns="1" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable id="taskList2" value="#{DDSIssueReceipt.receiptMasterTable2}" var="dataItem" rows="5" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Agent Code" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Agent Name" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Receipt From" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Receipt To" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Number of Leaves" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.agCode}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.agName}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptFrom}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.receiptTo}"><f:convertNumber type="number"/></h:outputText></rich:column>
                                <rich:column style="text-align:center"><h:outputText value="#{dataItem.leafs}"></h:outputText></rich:column>
                                <rich:column style="text-align:center">
                                    <a4j:commandLink id="deleteLink" action="#{DDSIssueReceipt.setDeleteRowValues}" reRender="panel1,panel2,footerPanel" ajaxSingle="true" focus="btnDelete">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DDSIssueReceipt.currentRmt2}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList2" maxPages="10" />
                        </a4j:region>
                        <h:panelGrid id="errorPanel2"  width="100%" style="text-align:center" styleClass="row1">
                            <h:outputText id="errMsg2" value="#{DDSIssueReceipt.message2}" styleClass="msg"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <a4j:commandButton id="btnSave" value="Save" action="#{DDSIssueReceipt.saveAction}" disabled="#{DDSIssueReceipt.saveDis}" reRender="chiefPanel"/>
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{DDSIssueReceipt.deleteAction}" reRender="chiefPanel"/>
                            <a4j:commandButton id="btnCancel" type="reset" value="Cancel" action="#{DDSIssueReceipt.cancelAction}" reRender="chiefPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DDSIssueReceipt.exitAction}" reRender="chiefPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
