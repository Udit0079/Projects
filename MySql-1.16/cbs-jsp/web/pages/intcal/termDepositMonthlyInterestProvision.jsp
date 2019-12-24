<%-- 
    Document   : termDepositMonthlyInterestProvision
    Created on : 22 Nov, 2019, 10:29:03 AM
    Author     : root
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
            <title>Term Deposit Monthly Interest Provision </title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{TermDepositMonthlyInterestProvision.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Term Deposit Monthly Interest Provision"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{TermDepositMonthlyInterestProvision.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <%--h:outputText id="errorMessage" styleClass="error" value="#{TermDepositMonthlyInterestProvision.errorMessage}"/--%>
                        <h:outputText id="message" styleClass="msg" value="#{TermDepositMonthlyInterestProvision.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="a3" style="height:30px;" styleClass="row1" width="100%" columnClasses="col1,col1,col1,col2,col1,col2">
                        <h:outputLabel id="lblAcType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcType" tabindex="1" styleClass="ddlist" style="width: 70px" value="#{TermDepositMonthlyInterestProvision.acType}" size="1" >
                                <f:selectItems value="#{TermDepositMonthlyInterestProvision.acTypeList}" />
                                <a4j:support action="#{TermDepositMonthlyInterestProvision.setdescription}" event="onchange"
                                             reRender="a3,a4,a5,a6,message,errorMessage,lpg,gpFooter,stxtAcToBeCr" limitToList="true" focus="ddIntOption" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIntOption" styleClass="label" value="Interest Option :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntOption" tabindex="2" styleClass="ddlist" style="width: 100px" value="#{TermDepositMonthlyInterestProvision.interestOption}" size="1" >
                                <f:selectItems value="#{TermDepositMonthlyInterestProvision.interestOptionList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblMonthEnd" styleClass="label" value="#{TermDepositMonthlyInterestProvision.lblMonthEnd}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup style="width:100%;text-align:center;">
                                <h:selectOneListbox id="ddMonthEnd" tabindex="3" styleClass="ddlist" style="width: 100px" value="#{TermDepositMonthlyInterestProvision.monthEnd}" size="1" >
                                    <f:selectItems value="#{TermDepositMonthlyInterestProvision.monthEndList}" />  
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddFinYear" tabindex="4" styleClass="ddlist" style="width: 75px" value="#{TermDepositMonthlyInterestProvision.finYear}" size="1" >
                                    <f:selectItems value="#{TermDepositMonthlyInterestProvision.finYearList}" />
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnCalculate" tabindex="5"  value="Print Report" action="#{TermDepositMonthlyInterestProvision.printReport}" reRender="a1,a3,message,gpFooter" />
                              <h:commandButton id="btnDownload"  value="PDF Download" action="#{TermDepositMonthlyInterestProvision.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" tabindex="7" value="Refresh" action="#{TermDepositMonthlyInterestProvision.resetForm}" reRender="a3,message" />
                            <a4j:commandButton id="btnExit" tabindex="8" value="Exit" action="#{TermDepositMonthlyInterestProvision.exitBtnAction}" reRender="message" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>      
        </body>
    </html>
</f:view>
