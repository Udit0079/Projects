<%-- 
    Document   : depostWithdrawl
    Created on : Dec 15, 2011, 12:38:30 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Cash/Clearing/Transfer Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="cashtransfer">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashTran.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cash/Clearing/Transfer Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CashTran.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{CashTran.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{CashTran.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{CashTran.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{CashTran.frmDt}" style="width:70px;setMask();"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{CashTran.toDt}" style="width:70px;setMask();"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddlAccountType" size="1" styleClass="ddlist" value="#{CashTran.accType}" >
                            <f:selectItems value="#{CashTran.acctTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputText value="Report Type" styleClass="label" />
                        <h:panelGroup>
                            <h:selectOneListbox id="ddlreportType" size="1" styleClass="ddlist" value="#{CashTran.reportType}">
                                <f:selectItem itemLabel="Cash" itemValue="0" />
                                <f:selectItem itemLabel="Clearing" itemValue="1" />
                                <f:selectItem itemLabel="Transfer" itemValue="2" />
                            </h:selectOneListbox>
                            <h:selectOneListbox id="ddTranType" size="1" styleClass="ddlist" value="#{CashTran.ty}">
                                <f:selectItem itemLabel="All" itemValue="2" />
                                <f:selectItem itemLabel="Deposit" itemValue="0" />
                                <f:selectItem itemLabel="Withdrawal" itemValue="1" />
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel value="Amount" styleClass="label" />
                        <h:inputText id="txtAmount" value="#{CashTran.amt}" size="12" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{CashTran.viewReport}" reRender="errorMsg" oncomplete="#{rich:element('frDt')}.style=setMask();#{rich:element('toDt')}.style=setMask();"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{CashTran.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="refresh" value="Reset" action="#{CashTran.refreshPage}" reRender="errorMsg,frDt,toDt,txtAmount" oncomplete="#{rich:element('frDt')}.style=setMask();#{rich:element('toDt')}.style=setMask();"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{CashTran.exitPage}" reRender="errorMsg,frDt,toDt,txtAmount"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
