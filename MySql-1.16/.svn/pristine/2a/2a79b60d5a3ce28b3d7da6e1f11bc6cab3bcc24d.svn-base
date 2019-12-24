<%--
    Document   : DdsEnquiry
    Created on : Nov 24, 2017, 5:04:20 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Daily Deposit Enquiry</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DdsEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Daily Deposit Enquiry"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DdsEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%">
                        <h:outputText id="stxtError" value="#{DdsEnquiry.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridpanel1" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblamount" styleClass="headerLabel" value="Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtamount" size="15" styleClass="input" value="#{DdsEnquiry.amount}" maxlength="10"/>
                        <h:outputLabel id="lblperiod" styleClass="headerLabel" value="Period(in month)"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtperiod" size="15" styleClass="input" value="#{DdsEnquiry.period}" maxlength="4">
                            <a4j:support reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal,stxtMatAmount,stxtInt" event="onblur" action="#{DdsEnquiry.getInputData}" focus="txtpercentage"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridpanel2" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row2" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblpercentage" styleClass="headerLabel" value="ROI(%)"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtpercentage" size="15" styleClass="input" value="#{DdsEnquiry.percentage}" maxlength="4"/>
                        <h:outputLabel id="lblInvAmt" styleClass="headerLabel" value="Invested Amount"/>
                        <h:outputText id="stxttamt" styleClass="output" value="#{DdsEnquiry.atotAmt}" style="color:Blue"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridpanel3" columnClasses="col13,col13,col13,col13" columns="4" styleClass="row1" style="width:100%;text-align:left;">
                        <h:outputLabel id="lblmatAmount" styleClass="headerLabel" value="Maturity Amount"/>
                        <h:outputText id="stxtMatAmount" styleClass="output" value="#{DdsEnquiry.matAmount}" style="color:Blue"/>
                        <h:outputLabel id="lblInt" styleClass="headerLabel" value="Total Interest"/>
                        <h:outputText id="stxtInt" styleClass="output" value="#{DdsEnquiry.totInt}" style="color:Blue"/>
                    </h:panelGrid>
                    <h:panelGrid columns="3" columnClasses="col19,col19,col19" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                            <h:outputText id="stxtInstruction" styleClass="output" value="365 Days In a Year" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btncal" value="Calculate" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal,stxtMatAmount,stxtInt" action="#{DdsEnquiry.calbtnData}" focus="refresh"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{DdsEnquiry.reSet}" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal,stxtMatAmount,stxtInt" focus="txtamount"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{DdsEnquiry.exitFrm}" reRender="stxtError,txtamount,txtperiod,txtpercentage,stxttamt,btncal"/>
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>