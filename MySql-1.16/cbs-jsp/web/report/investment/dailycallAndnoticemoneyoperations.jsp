<%-- 
    Document   : dailycallAndnoticemoneyoperations
    Created on : Dec 24, 2015, 3:28:02 PM
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
            <title>Daily call and notice operations</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="amortiziation">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DailyCallAndNoticeMoneyOperations.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Daily call and notice operations"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DailyCallAndNoticeMoneyOperations.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{DailyCallAndNoticeMoneyOperations.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="bodyPanel"columnClasses="col13,col13,col13,col13" columns="4" width="100%" styleClass="row1" style="text-align:center">
                        <h:panelGroup layout="block"> 
                            <h:outputLabel id="label4" value="First Alternate Friday :" styleClass="label">
                                <font class="required" style="color:red">*</font>
                                </h:outputLabel>
                                <h:panelGroup styleClass="label">
                                    <h:inputText id="frDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{DailyCallAndNoticeMoneyOperations.firstAltDt}"/>
                                <font color="purple">DD/MM/YYYY</font>
                                </h:panelGroup>
                            </h:panelGroup>
                            <h:outputLabel id="lblRuppeType" styleClass="label" value="Report In"/>
                            <h:selectOneListbox id="ddRuppeType" size="1" styleClass="ddlist" value="#{DailyCallAndNoticeMoneyOperations.reportIn}">
                                <f:selectItems value="#{DailyCallAndNoticeMoneyOperations.reportInList}"/>
                                <a4j:support event="onblur" oncomplete="setMask();"/>
                            </h:selectOneListbox> 
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <h:commandButton id="btnDownload" value="PDF Download" action="#{DailyCallAndNoticeMoneyOperations.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DailyCallAndNoticeMoneyOperations.refresh}" reRender="a1" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnClose" value="Exit" action="#{DailyCallAndNoticeMoneyOperations.exitAction}" reRender="errorMsg"/>
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
