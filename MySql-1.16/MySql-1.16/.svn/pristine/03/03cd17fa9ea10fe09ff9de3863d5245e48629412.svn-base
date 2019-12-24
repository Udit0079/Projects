<%-- 
    Document   : ddsoutwardfile
    Created on : Aug 5, 2013, 11:18:57 AM
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
            <title>DDS Outward File Generation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DDSOutwardFile.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="DDS Outward File Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DDSOutwardFile.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{DDSOutwardFile.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="agentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblAccountCode" styleClass="label" value="Account Code"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddAccountCode" styleClass="ddlist" size="1" value="#{DDSOutwardFile.accountCode}">
                            <f:selectItems value="#{DDSOutwardFile.accountCodeList}"/>
                            <a4j:support action="#{DDSOutwardFile.processAccountCode}" event="onblur" reRender="stxtMessage"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAgentCode" styleClass="label" value="Agent Code"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAgentCode" styleClass="input" style="width:50px" value="#{DDSOutwardFile.agentCode}" maxlength="2">
                            <a4j:support event="onblur" action="#{DDSOutwardFile.processAgentCode}" reRender="stxtMessage,stxtAgentName"/>
                        </h:inputText>
                        <h:outputLabel id="lblAgentName" styleClass="label" value="Agent Name"/>
                        <h:outputText id="stxtAgentName" styleClass="output" value="#{DDSOutwardFile.agentName}"/>
                    </h:panelGrid>
                      <h:panelGrid id="passDaysGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblPwdExpiry" styleClass="label" value="Password Expiry Days"/>
                        <h:inputText id="txtPwdExpiry" styleClass="input" style="width:50px" value="#{DDSOutwardFile.days}" maxlength="1" disabled="#{DDSOutwardFile.daysFlag}">
                            <a4j:support event="onblur" action="#{DDSOutwardFile.validatePwdDays}" reRender="stxtMessage"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="a6" columns="2" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpload" value="Generate File" action="#{DDSOutwardFile.generateFile}"/>
                            <h:commandButton id="btnRefresh" value="Refresh" action="#{DDSOutwardFile.refreshForm}"/>
                            <h:commandButton id="btnExit" value="Exit" action="#{DDSOutwardFile.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
