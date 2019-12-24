<%-- 
    Document   : IbcObcEnquiry
    Created on : Sep 28, 2010, 6:45:25 PM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>IBCOBC Enquiry</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IbcObcEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="IBCOBC Enquiry"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IbcObcEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{IbcObcEnquiry.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{IbcObcEnquiry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a3" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblBills" styleClass="label" value="Bills :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddbills" tabindex="1" styleClass="ddlist" value="#{IbcObcEnquiry.bill}" size="1" style="width: 150px">
                            <f:selectItems value="#{IbcObcEnquiry.billList}"/>
                            <a4j:support action="#{IbcObcEnquiry.yearCombo}" event="onblur"
                                         reRender="message,errorMessage,a4" limitToList="true" focus="ddBillType" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a4" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblBillType" styleClass="label" value="Bill Type :" style=""><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBillType" tabindex="2" styleClass="ddlist"  value="#{IbcObcEnquiry.billType}" size="1" style="width: 102px">
                            <f:selectItems value="#{IbcObcEnquiry.billTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBillNo" styleClass="label" value="Bill No. :" style=""><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBillNo" maxlength="15" tabindex="3" size="15" value="#{IbcObcEnquiry.billNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                        <h:outputLabel id="lblYear" styleClass="label" value="Year :" style=""><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddYear" tabindex="4" styleClass="ddlist"  value="#{IbcObcEnquiry.year}" size="1" style="width: 102px">
                            <f:selectItems value="#{IbcObcEnquiry.yearList}" />
                            <a4j:support action="#{IbcObcEnquiry.getDetail}" event="onblur"
                                         reRender="message,errorMessage,a5,a6,a7" limitToList="true" focus="btnRefresh" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a5" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblAcNo" styleClass="label" value="Account No. :" style=""/>
                        <h:outputText id="stxtAcNo" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.acNo}" />
                        <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" style=""/>
                        <h:outputText id="stxtCustName" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.custName}" />
                        <h:outputLabel id="lblHide1" value=""/>
                        <h:outputLabel id="lblHide2" value=""/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amount :" style=""/>
                        <h:outputText id="stxtInstAmt" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.instAmt}" />
                        <h:outputLabel id="lblInstNo" styleClass="label" value="Inst No. :" style=""/>
                        <h:outputText id="stxtInstNo" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.instNo}" />
                        <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Date :" style=""/>
                        <h:outputText id="stxtInstDate" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.instDate}" />
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a7" style="height:30px;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                        <h:outputLabel id="lblCommision" styleClass="label" value="Commision :" style=""/>
                        <h:outputText id="stxtCommision" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.commision}" />
                        <h:outputLabel id="lblPostageAmt" styleClass="label" value="Postage Amount :" style=""/>
                        <h:outputText id="stxtPostageAmt" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.postageAmt}" />
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status :" style=""/>
                        <h:outputText id="stxtStatus" styleClass="output" style="color:purple;" value="#{IbcObcEnquiry.status}" />
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnRefresh" tabindex="5" value="Refresh" action="#{IbcObcEnquiry.resetForm}"  reRender="a3,a4,a5,a6,a7,message,errorMessage,lpg" focus="ddbills"/>
                            <a4j:commandButton id="btnExit" tabindex="6" value="Exit" action="#{IbcObcEnquiry.exitForm}" reRender="message,errorMessage"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
