<%-- 
    Document   : MergeSignature
    Created on : Mar 20, 2015, 2:49:33 PM
    Author     : mayank
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
            <title>Signature Merged</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
        </head>
        <body>
            <a4j:form id="sigMergForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SigMerged.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Signature Merged"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SigMerged.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="stxtError" styleClass="error" value="#{SigMerged.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel3" width="100%">
                        <h:panelGrid columnClasses="col85" columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row1" styleClass="row1">
                                <h:outputLabel id="labelOldAcNo" styleClass="label" value="Old Account No."/>
                                <h:inputText id="txtOldAcNo" styleClass="input" maxlength="12" value="#{SigMerged.oldAcno}">
                                    <a4j:support actionListener="#{SigMerged.getOldAccountValues}" event="onblur" reRender="stxtCustName,stxtAccOpenDate,stxtSignScanBy,panelImage22,gridPanel5,stxtError,signature"/>
                                </h:inputText>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row2" styleClass="row2">
                                <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name"/>
                                <h:outputText id="stxtCustName" styleClass="output" value="#{SigMerged.custName}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row3" styleClass="row1">
                                <h:outputLabel id="lblAccOpenDate" styleClass="label" value="A/C Opening Date"/>
                                <h:outputText id="stxtAccOpenDate" styleClass="output" value="#{SigMerged.accOpenDate}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row4" styleClass="row2">
                                <h:outputLabel id="lblSignScanBy" styleClass="label" value="Signatures Scanned By"/>
                                <h:outputText id="stxtSignScanBy" styleClass="output" value="#{SigMerged.signScannedBy}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row5" styleClass="row1">
                                <h:outputLabel id="labelNewAcNo" styleClass="label" value="New Account No."/>
                                <h:inputText id="txtNewAcNo" styleClass="input" maxlength="12" value="#{SigMerged.newAcno}"/>                                
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row6" styleClass="row2">
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col19,col12" columns="2" id="row7" styleClass="row1">
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel5" width="100%" style="height:240px;">
                            <h:panelGrid id="panelImage22" columns="1" columnClasses="col8"style="width:100%;text-align:center;" styleClass="row1" >
                                <a4j:mediaOutput id="signature" style="width:550px;height:250px;" element="img" createContent="#{SigMerged.createSignature}" value="#{SigMerged.acNo}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" reRender="gridPanel3,stxtError" action="#{SigMerged.cmdSaveClick}"/>
                            <a4j:commandButton id="btnCancel" value="Refresh" reRender="gridPanel3,stxtError,btnSave" action="#{SigMerged.refreshForm}"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SigMerged.exitForm}" reRender="gridPanel3"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>