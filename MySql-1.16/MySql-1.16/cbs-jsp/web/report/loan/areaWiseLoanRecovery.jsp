<%-- 
    Document   : areaWiseLoanRecovery
    Created on : Jun 13, 2016, 1:17:21 PM
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
            <title>Area Wise Recovery </title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{AreaWiseLoanRecovery.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Area Wise Recovery"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AreaWiseLoanRecovery.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AreaWiseLoanRecovery.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2">
                        <h:outputText id="lblacType" value="Area Wise:" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{AreaWiseLoanRecovery.area}" styleClass="ddlist"  style="width:85px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{AreaWiseLoanRecovery.areaList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="width:80px;"  value="#{AreaWiseLoanRecovery.frmDt}"> 
                        </h:inputText>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:inputText id="txtToDate"   styleClass="input calInstDate" style="width:80px;"  value="#{AreaWiseLoanRecovery.toDt}">
                            <a4j:support action="#{AreaWiseLoanRecovery.chenageOperation}" event="onblur" reRender="dddmdSno" />  
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText id="lbldmdSno" value="Demand Srl No." styleClass="label"/>
                        <h:selectOneListbox id="dddmdSno" value="#{AreaWiseLoanRecovery.dmdSrno}" styleClass="ddlist"  style="width:85px" size="1">
                            <f:selectItems id="selectdmdSno" value="#{AreaWiseLoanRecovery.dmdSrnoList}" />
                        </h:selectOneListbox>
                        <h:outputText id="lblrepType" value="Report Type:" styleClass="label"/>
                        <h:selectOneListbox id="ddrepType111" value="#{AreaWiseLoanRecovery.repType}" styleClass="ddlist"  style="width:85px" size="1">
                            <f:selectItems id="selectRepTypeList111" value="#{AreaWiseLoanRecovery.repList}" />
                        </h:selectOneListbox>
                        <h:outputText id="lbloverDue" value="Over Due" styleClass="label"/>
                        <h:selectOneListbox id="ddoverDue" value="#{AreaWiseLoanRecovery.isoverDue}" styleClass="ddlist"  style="width:85px" size="1">
                            <f:selectItems id="selectoverDue" value="#{AreaWiseLoanRecovery.isoverDueList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{AreaWiseLoanRecovery.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AreaWiseLoanRecovery.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AreaWiseLoanRecovery.refreshPage}" reRender="a1" oncomplete="setMask()"/>
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
