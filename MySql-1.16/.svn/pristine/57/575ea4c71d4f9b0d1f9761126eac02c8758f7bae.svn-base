<%-- 
    Document   : EFTBankMaster
    Created on : Jun 9, 2011, 6:35:10 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>NeftRtgs Bank Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EFTBankMaster.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Bank Master"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EFTBankMaster.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{EFTBankMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="bankCode" styleClass="label" value="Bank Code" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="bankCodeText" styleClass="input"  value="#{EFTBankMaster.bankCode}" maxlength="6" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support reRender="mainPanel" action="#{EFTBankMaster.onBlurBankCode}" event="onblur" oncomplete="{#{rich:element('bankNameText')}.focus();}"/>
                        </h:inputText>
                        <h:outputLabel id="bankName" styleClass="label" value="Bank Name" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="bankNameText" styleClass="input" value="#{EFTBankMaster.bankName}" maxlength="50" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                        <h:outputLabel id="shortName" styleClass="label" value="Short Name" ></h:outputLabel>
                        <h:inputText   id="shortNameText" styleClass="input"  value="#{EFTBankMaster.shortName}" maxlength="20" onkeyup="this.value=this.value.toUpperCase();">
                        </h:inputText>
                        <h:outputLabel id="localClgFlag" styleClass="label" value="Local Clearing Flag" ></h:outputLabel>
                        <h:inputText  id="localClgFlagText" styleClass="input"  value="#{EFTBankMaster.localClgFlag}" maxlength="1" onkeyup="this.value=this.value.toUpperCase();">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel10" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="primeBankFlag" styleClass="label" value="PrimeBankFlag" ></h:outputLabel>
                        <h:inputText  id="primeBankFlagText" styleClass="input"  value="#{EFTBankMaster.primeBankFlag}" maxlength="1" onkeyup="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="corrBankFlag" styleClass="label" value="CorrBank Flag"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText    id="corrBankFlagText" styleClass="input"  value="#{EFTBankMaster.corrBankFlag}"  maxlength="1" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel13" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row2" width="100%">
                        <h:outputLabel id="bicCode" styleClass="label" value="BIC Code"/>
                        <h:inputText id="bicCodeText" styleClass="input"  value="#{EFTBankMaster.bicCode}" maxlength="11" onkeyup="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="weekOff" styleClass="label" value="Weekly Off"/>
                        <h:inputText id="weekOffText" styleClass="input"  value="#{EFTBankMaster.weekOff}"  maxlength="12" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton disabled="#{EFTBankMaster.add}" id="btnSave" value="Save" action="#{EFTBankMaster.saveButton}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton disabled="#{EFTBankMaster.update}"id="btnUpdate" value="Update" action="#{EFTBankMaster.updateButton}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EFTBankMaster.refresh}" reRender="mainPanel" oncomplete="{#{rich:element('bankCodeText')}.focus();}"></a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EFTBankMaster.exitButton}" reRender="mainPanel"></a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
