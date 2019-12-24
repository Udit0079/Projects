<%-- 
    Document   : bal
    Created on : Mar 19, 2013, 2:37:27 PM
    Author     : Sanjay Khandelwal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Balance Sheet</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calAsonDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="BalanceSheet">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BalanceSheet.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="BalanceSheet"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{BalanceSheet.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{BalanceSheet.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">                        
                        <h:outputLabel id="lblRpType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRpType" size="1" styleClass="ddlist" value="#{BalanceSheet.reportType}">
                            <f:selectItems value="#{BalanceSheet.reportTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddRuppeType"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRuppeType" styleClass="label" value="Report In"/>
                        <h:selectOneListbox id="ddRuppeType" size="1" styleClass="ddlist" value="#{BalanceSheet.reportIn}">
                            <f:selectItems value="#{BalanceSheet.reportInList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddRptFormat"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row2">
                        <h:outputLabel id="lblReprtFormat" styleClass="label" value="Format In"/>
                            <h:selectOneListbox id="ddRptFormat" size="1" styleClass="ddlist" value="#{BalanceSheet.reportFormat}">
                                <f:selectItems value="#{BalanceSheet.reportFormatIn}"/>
                                <a4j:support event="onblur" oncomplete="setMask();" focus="calAsonDate"/>
                            </h:selectOneListbox>
                            <h:outputLabel  value="As On Date" styleClass="label"/>                       
                            <h:inputText id="calAsonDate" size="10" styleClass="input calAsonDate" style="setMask();" value="#{BalanceSheet.asonDate}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                <a4j:support event="onblur" oncomplete="setMask();" focus="btnDownload"/>
                            </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row2" style="display:#{BalanceSheet.staffShow}">
                        <h:outputLabel id="lblEmpStaff" styleClass="label" value="Total No of Staff"/>
                        <h:inputText id="ddEmpStaff" size="2" styleClass="ddlist" value="#{BalanceSheet.staff}">
                            <%--a4j:support event="onblur" oncomplete="setMask();" focus="txtDividend"/--%>
                        </h:inputText>
                      <h:outputLabel/>
                      <h:outputLabel/>
                    </h:panelGrid>
                    
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <%--h:commandButton id="btnPrint" action="#{BalanceSheet.viewReport}" type="submit" value="View Report" reRender="errmsg"/--%>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{BalanceSheet.viewReport}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{BalanceSheet.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="PanelGridMain" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" action="#{BalanceSheet.exit}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>