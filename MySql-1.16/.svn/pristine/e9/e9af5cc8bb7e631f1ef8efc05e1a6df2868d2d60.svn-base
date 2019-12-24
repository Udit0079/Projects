<%-- 
    Document   : ddsmultiglowfile
    Created on : 4 Feb, 2015, 1:21:42 PM
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
            <title>DDS Multi GL Outward File Generation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ddsMultiGlController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="DDS Multi GL File Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ddsMultiGlController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columns="2" columnClasses="col8,col8" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{ddsMultiGlController.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="agentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDdsAcCode" styleClass="label" value="A/c Type"/>
                        <h:selectOneListbox id="ddDdsAcCode" styleClass="ddlist" size="1" value="#{ddsMultiGlController.ddsAcCode}">
                            <f:selectItems value="#{ddsMultiGlController.ddsAcCodeList}"/>
                            <a4j:support event="onblur" action="#{ddsMultiGlController.processAcType}" 
                                         reRender="stxtMessage,tablePanel,taskList,lblAccountCode,ddAccountCode,lblAgentCode,groupPanelId"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccountCode" styleClass="label" value="Account Code" style="display:#{ddsMultiGlController.agentPanelEnable}"/>
                        <h:selectOneListbox id="ddAccountCode" styleClass="ddlist" size="1" value="#{ddsMultiGlController.accountCode}" style="display:#{ddsMultiGlController.agentPanelEnable}">
                            <f:selectItems value="#{ddsMultiGlController.accountCodeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAgentCode" styleClass="label" value="Agent Code" style="display:#{ddsMultiGlController.agentPanelEnable}"/>
                        <h:panelGroup id = "groupPanelId" style="display:#{ddsMultiGlController.agentPanelEnable}">
                            <h:inputText id="txtAgentCode" styleClass="input" style="width:50px" value="#{ddsMultiGlController.agentCode}" maxlength="2">
                                <a4j:support event="onblur" action="#{ddsMultiGlController.processAgentCode}" reRender="stxtMessage,stxtAgentName"/>
                            </h:inputText>
                            <h:outputText id="stxtAgentName" styleClass="output" value="#{ddsMultiGlController.agentName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{ddsMultiGlController.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="A/c Code Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Nature" /></rich:column>
                                        <rich:column><h:outputText value="A/c Code" /></rich:column>
                                        <rich:column><h:outputText value="A/c Description" /></rich:column>
                                        <rich:column><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.nature}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acCode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acCodeDesc}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <h:selectBooleanCheckbox id="booleanCheckBox"  value="#{dataItem.checkBox}"  styleClass="input">
                                        <a4j:support event="onchange" action="#{ddsMultiGlController.addRemoveAcCode}">
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ddsMultiGlController.currentItem}"/>
                                        </a4j:support>
                                    </h:selectBooleanCheckbox>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="pwdPanelGrid" columns="2" columnClasses="col2,col80" style="height:30px;text-align:left" styleClass="row2" width="100%" >                            
                        <h:outputLabel id="lblPwdExpiry" styleClass="label" value="Password Expiry Days"/>
                        <h:inputText id="txtPwdExpiry" styleClass="input" style="width:50px" value="#{ddsMultiGlController.days}" maxlength="1"/>
                    </h:panelGrid>
                    <h:panelGrid id="a6" columns="2" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Generate File" action="#{ddsMultiGlController.generateFile}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{ddsMultiGlController.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{ddsMultiGlController.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
