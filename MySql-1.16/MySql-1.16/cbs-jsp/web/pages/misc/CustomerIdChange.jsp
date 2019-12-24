<%-- 
    Document   : CustomerIdChange
    Created on : Apr 11, 2017, 12:45:01 PM
    Author     : Admin
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Customer ID Change</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
        </head>
        <body>
            <a4j:form id="IdChange">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IdChange.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Customer ID Change"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IdChange.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{IdChange.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row1">
                        <h:outputLabel id="accNoLbl" value="Account No." styleClass="label"/>
                        <h:inputText id="accNoTxt" value="#{IdChange.accNo}" styleClass="input">
                            <a4j:support event="onblur" action="#{IdChange.getAccDetails}" reRender="mainPanel,lblMsg"/>
                        </h:inputText>
                        <h:outputLabel id="remarkLbl" value="Remarks" styleClass="label"/>
                        <h:inputText id="remarkTxt" value="#{IdChange.remark}" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row2">
                        <h:outputLabel id="custNameLbl" styleClass="label" value="Customer Name"/>
                        <h:outputText id ="stxtCustName" styleClass="label" value = "#{IdChange.custName}"/>
                        <h:outputLabel id="opModeLbl" styleClass="label" value="Operation Mode"/>
                        <h:outputText id ="stxtOpMode" styleClass="label" value = "#{IdChange.opMode}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row1">
                        <h:outputLabel id="jointIdLbl" styleClass="label" value="Joint Holder Id"/>
                        <h:outputText id ="stxtJtId" styleClass="label" value = "#{IdChange.jtId}"/>
                        <h:outputLabel id="jtCustNameLbl" styleClass="label" value="Joint Holder Name"/>
                        <h:outputText id ="stxtJtCustName" styleClass="label" value = "#{IdChange.jtCustName}"/>                            
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="Please Collect All Related Documents Before Changing Customer Id" style="color:blue;font-weight:bold;"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton value="Change" id="btnSave" action="#{IdChange.saveDetails}" reRender="mainPanel,lblMsg"/>
                            <a4j:commandButton value="Refresh" id="refresh" action="#{IdChange.refreshForm}" reRender="mainPanel,lblMsg"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{IdChange.exitBtnAction}" reRender="mainPanel,lblMsg1"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>                
            </a4j:form>
        </body>
    </html>
</f:view>