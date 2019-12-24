<%-- 
    Document   : singledemandgeneration
    Created on : 2 May, 2014, 1:11:50 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Single Demand Generation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{singleDemandGeneration.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Single Demand"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{singleDemandGeneration.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{singleDemandGeneration.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="componentPanel" columnClasses="col13,col13,col13,col13" columns="4" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAcno" styleClass="label" value="Account No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAcno" styleClass="input" style="width:100px" maxlength="12" value="#{singleDemandGeneration.acno}">
                            <a4j:support event="onblur" action="#{singleDemandGeneration.validateAccount}" reRender="stxtMessage" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblTillDt" styleClass="label" value="Till Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTillDt" styleClass="input issueDt" style="width:70px" value="#{singleDemandGeneration.tillDt}">
                            <a4j:support event="onblur" action="#{singleDemandGeneration.processAccountDemand}" reRender="stxtMessage,tablePanel,taskList" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{singleDemandGeneration.btnRefreshAction}" reRender="stxtMessage,mainPanel,tablePanel,taskList" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{singleDemandGeneration.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <rich:dataTable value="#{singleDemandGeneration.tableDataList}" var="dataItem"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="4"><h:outputText value="Account Demand Detail" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Demand Type" /></rich:column>
                                    <rich:column><h:outputText value="Overdue Amount" /></rich:column>
                                    <rich:column><h:outputText value="Due Date" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dmdType}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.overDueAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dueDt}" /></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="1"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
