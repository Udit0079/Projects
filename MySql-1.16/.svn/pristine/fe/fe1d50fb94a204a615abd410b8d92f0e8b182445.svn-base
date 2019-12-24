<%-- 
    Document   : DailyUpdationOfCRR
    Created on : Oct 12, 2012, 9:32:01 PM
    Author     : Dinesh Pratap Singh
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Daily Updation Of CRR</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyUpdationOfCRR.todayDate}">
                            </h:outputText>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Daily Updation Of CRR / SLR Register"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyUpdationOfCRR.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting1" width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="errorGrid1" value="#{DailyUpdationOfCRR.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel15" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid id="gridPanel16" columns="2" columnClasses="col8,col8" width="100%" styleClass="row1">
                            <h:outputLabel id="lblFrom" styleClass="label" value="From Date"/>
                            <h:inputText id="txtFromDt" size="10" styleClass="input issueDt" value="#{DailyUpdationOfCRR.fromDt}">
                                <a4j:support event="onblur" focus="txtToDt" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel17" columns="2" columnClasses="col8,col8" width="100%" styleClass="row1">
                            <h:outputLabel id="lblTO" styleClass="label" value="To Date"/>
                            <h:inputText id="txtToDt" size="10" styleClass="input issueDt" value="#{DailyUpdationOfCRR.toDt}">
                                <a4j:support event="onblur" focus="btnUpdate" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="btnUpdate" value="Update" action="#{DailyUpdationOfCRR.updateProcess}" reRender="errorGrid1"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DailyUpdationOfCRR.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing" />
                    </f:facet>
                    <h:outputText value="Wait Please..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>
        </body>
    </html>
</f:view>
