<%-- 
    Document   : EFTChargeDetails
    Created on : Jun 10, 2011, 5:28:24 PM
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{EFTChargeDetails.currentDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Charge Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EFTChargeDetails.loggedInUser}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{EFTChargeDetails.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4"  columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="chargeEventId" styleClass="label" value="ChargeEventID" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="chargeEventIdText" styleClass="input"  value="#{EFTChargeDetails.chargeEventId}" maxlength="30" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support reRender="mainPanel" action="#{EFTChargeDetails.onBlurChargeEventId}" event="onblur" oncomplete="{#{rich:element('paySysIdText')}.focus();}"/>
                        </h:inputText>
                        <h:outputLabel id="paySysId" styleClass="label" value="PaySysID" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText  id="paySysIdText" styleClass="input" value="#{EFTChargeDetails.paySysId}" maxlength="5" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel6" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row2" width="100%">
                        <h:outputLabel id="minLimit" styleClass="label" value="Minimum Limit" ></h:outputLabel>
                        <h:inputText   id="minLimitText" styleClass="input"  value="#{EFTChargeDetails.minLimit}">
                        </h:inputText>
                        <h:outputLabel id="maxLimit" styleClass="label" value="Maximum Limit" ></h:outputLabel>
                        <h:inputText  id="maxLimitText" styleClass="input"  value="#{EFTChargeDetails.maxLimit}">
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel10" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px"  styleClass="row1" width="100%">
                        <h:outputLabel id="chargeAmt" styleClass="label" value="Charge Amount" ></h:outputLabel>
                        <h:inputText  id="chargeAmtText" styleClass="input"  value="#{EFTChargeDetails.chargeAmt}"/>
                        <h:outputLabel id="chargeRoi" styleClass="label" value="Charge ROI"></h:outputLabel>
                        <h:inputText id="chargeRoiText" styleClass="input"  value="#{EFTChargeDetails.chargeRoi}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel13" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row2" width="100%">
                        <h:outputLabel id="sTax" styleClass="label" value="Goods and Service Tax"/>
                        <h:inputText id="sTaxText" styleClass="input"  value="#{EFTChargeDetails.sTax}"/>
                        <h:outputLabel id="sTaxHolder" styleClass="label" value="S.Tax Account"/>
                        <h:inputText id="sTaxHolderText" styleClass="input"  value="#{EFTChargeDetails.sTaxHolder}" maxlength="12" onkeyup="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel16" columns="4" columnClasses="col2,col7,col2,col7" style="height:30px;border:1px ridge #BED6F8;padding-left:120px" styleClass="row1" width="100%">
                        <h:outputLabel id="plHolder" styleClass="label" value="P/L Account" ></h:outputLabel>
                        <h:inputText id="plHolderText" styleClass="input"  value="#{EFTChargeDetails.plHolder}" maxlength="12" onkeyup="this.value=this.value.toUpperCase();"/>
                        <h:outputLabel id="labeldg"/>
                        <h:outputLabel id="labelblank"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                            <a4j:commandButton disabled="#{EFTChargeDetails.add}" id="btnSave" value="Save" action="#{EFTChargeDetails.saveButton}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton disabled="#{EFTChargeDetails.update}"id="btnUpdate" value="Update" action="#{EFTChargeDetails.updateButton}" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EFTChargeDetails.refresh}" reRender="mainPanel" oncomplete="{#{rich:element('chargeEventIdText')}.focus();}"></a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EFTChargeDetails.exitButton}" reRender="mainPanel"></a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
