<%-- 
    Document   : demandRecovery
    Created on : Aug 3, 2015, 11:09:03 AM
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
            <title>Demand Recovery </title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="shareHolders">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DemandRecovery.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Demand Recovery"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DemandRecovery.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{DemandRecovery.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblacType" value="Area Wise:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{DemandRecovery.area}" styleClass="ddlist"  style="width:110px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{DemandRecovery.areaList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{DemandRecovery.month}">
                                    <f:selectItems value="#{DemandRecovery.monthList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtYear" styleClass="input" value="#{DemandRecovery.year}" maxlength="4" size="5"/>
                            </h:panelGroup>     
                        </h:panelGrid>  
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row2">
                            <h:outputText id="lbloptType" value="Option Type:" styleClass="label"/>
                            <h:selectOneListbox id="ddoptType111" value="#{DemandRecovery.optionType}" styleClass="ddlist"  style="width:110px" size="1">
                                <f:selectItems id="selectoptTypeList111" value="#{DemandRecovery.optionTypeList}" />
                                <a4j:support event="onblur" action="#{DemandRecovery.optAction}" reRender="ddrepType111,btnPrint"> </a4j:support>
                            </h:selectOneListbox>  
                            <h:outputText id="lblrepType" value="Report Type:" styleClass="label"/>
                            <h:selectOneListbox id="ddrepType111" value="#{DemandRecovery.repType}" styleClass="ddlist"  style="width:110px" size="1">
                                <f:selectItems id="selectRepTypeList111" value="#{DemandRecovery.repList}" />
                                <a4j:support event="onblur" action="#{DemandRecovery.reportTypeAction}" reRender="btnPrint"> </a4j:support>
                            </h:selectOneListbox>  
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <h:commandButton id="btnPrint" value="Excel Download" actionListener="#{DemandRecovery.excelPrint}" disabled="#{DemandRecovery.printButton}"/>
                                <a4j:commandButton  id="print" value="Print Report" action="#{DemandRecovery.viewReport}" reRender="errorMsg"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{DemandRecovery.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DemandRecovery.refreshPage}" reRender="a1" oncomplete="setMask()"/>
                                <a4j:commandButton  id="exit" value="Exit" action="case1"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
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
