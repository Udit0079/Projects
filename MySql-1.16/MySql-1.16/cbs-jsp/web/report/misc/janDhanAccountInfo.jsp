<%-- 
    Document   : janDhanAccountInfo
    Created on : Nov 15, 2016, 1:28:38 PM
    Author     : Admin
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
            <title>Jan Dhan Account Info/Ctr</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="JanDhanId">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{JanDhanAccountInfo.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Jan Dhan Account Info/Ctr"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{JanDhanAccountInfo.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{JanDhanAccountInfo.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Report Type :" styleClass="label"/>
                        <h:selectOneListbox id="reportId" size="1" value="#{JanDhanAccountInfo.repType}" styleClass="ddlist" style="width:250px">
                            <f:selectItems value="#{JanDhanAccountInfo.repTypeList}" />
                            <a4j:support action="#{JanDhanAccountInfo.onReportType}" event="onblur" reRender="txtAmount,dTranTypeId,withdId,amtId,panel5,panel6,txtFrmDate,denominationId,statusId" focus="branchId"  oncomplete="setMask()"/>
                        </h:selectOneListbox>
                        <h:outputText value="Branch Wise :" styleClass="label"/>
                        <h:selectOneListbox id="branchId" size="1" value="#{JanDhanAccountInfo.branch}" styleClass="ddlist" style="width:80px">
                            <f:selectItems value="#{JanDhanAccountInfo.branchList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputText value="Deposit Tran Type :" styleClass="label"/>
                        <h:selectOneListbox id="dTranTypeId" size="1" value="#{JanDhanAccountInfo.depositTranType}" styleClass="ddlist"  disabled="#{JanDhanAccountInfo.disableTranType}">
                            <f:selectItems value="#{JanDhanAccountInfo.depositTranTypeList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Withdrawal Tran Type :" styleClass="label"/>
                        <h:selectOneListbox id="withdId" size="1" value="#{JanDhanAccountInfo.withdrawTranType}" styleClass="ddlist" disabled="#{JanDhanAccountInfo.disableTranType}">
                            <f:selectItems value="#{JanDhanAccountInfo.withdrawTranTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="From Date :" styleClass="label"/>
                        <h:inputText id="txtFrmDate"   styleClass="input calFromDate" style="width:70px;"  value="#{JanDhanAccountInfo.frDt}">
                            <a4j:support  event="onblur" focus="txtToDate"  oncomplete="setMask()"/>
                        </h:inputText>                            
                        <h:outputText value="To Date :" styleClass="label"/>
                        <h:inputText id="txtToDate"   styleClass="input calToDate" style="width:70px;"  value="#{JanDhanAccountInfo.toDt}">
                            <a4j:support  event="onblur" focus="amtId"  oncomplete="setMask()"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panel7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%">
                        <h:outputText value="Amount Type :" styleClass="label"/>
                        <h:selectOneListbox id="amtId" size="1" value="#{JanDhanAccountInfo.amtType}" styleClass="ddlist" disabled="#{JanDhanAccountInfo.disableAmtType}">
                            <f:selectItems value="#{JanDhanAccountInfo.amtTypeList}" />
                            <a4j:support action="#{JanDhanAccountInfo.onAmtType}" event="onblur" reRender="panel5,panel6,txtAmount,txtAmount1"  focus="denominationId"  oncomplete="setMask()"/>
                        </h:selectOneListbox> 
                        <h:outputText value="Denomination Type :" styleClass="label"/>
                        <h:selectOneListbox id="denominationId" size="1" value="#{JanDhanAccountInfo.denominationType}" styleClass="ddlist" >
                            <f:selectItems value="#{JanDhanAccountInfo.denominationTypeList}" /> 
                            <a4j:support  event="onblur" focus="txtAmount"  oncomplete="setMask()"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="panel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%"style="display:#{JanDhanAccountInfo.amtVisibleTill}"> 
                        <h:outputText value="Amount (More Than Or Equal) :" styleClass="label" />
                        <h:inputText id="txtAmount" value="#{JanDhanAccountInfo.tillAmount}" size="12" styleClass="input">
                            <a4j:support  event="onblur" focus="btnPrint"  oncomplete="setMask()"/>
                        </h:inputText>
                        <h:outputText value="Status :" styleClass="label"/>
                        <h:selectOneListbox id="statusId" size="1" value="#{JanDhanAccountInfo.statusType}" styleClass="ddlist" disabled="#{JanDhanAccountInfo.disableStatus}" >
                            <f:selectItems value="#{JanDhanAccountInfo.statusTypeList}" /> 
                            <%--a4j:support  event="onblur" focus="txtAmount"  oncomplete="setMask()"/--%>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid id="panel6" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%"style="display:#{JanDhanAccountInfo.amtVisible}"> 
                        <h:outputText value="From Amount :" styleClass="label" />
                        <h:inputText id="txtAmount1" value="#{JanDhanAccountInfo.amount1}" size="12" styleClass="input"/>
                        <h:outputText value="To Amount :" styleClass="label" />
                        <h:inputText id="txtAmount2" value="#{JanDhanAccountInfo.amount2}" size="12" styleClass="input"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel9" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnPrint" value="Download Excel" action="#{JanDhanAccountInfo.printAction}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{JanDhanAccountInfo.btnRefreshAction}" reRender="errorMsg,a1" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{JanDhanAccountInfo.btnExitAction}" reRender="errorMsg"/>
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

