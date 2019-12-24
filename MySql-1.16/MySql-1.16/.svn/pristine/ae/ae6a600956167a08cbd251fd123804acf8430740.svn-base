<%-- 
    Document   : agentNature
    Created on : 7 Mar, 2013, 5:21:55 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Agent Wise Monthly collection Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".frDate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="DDSAgentNature">                
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header" width="100%">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AgentWiseMonthlyCollectionReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Agent Wise Monthly collection Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{AgentWiseMonthlyCollectionReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>    
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{AgentWiseMonthlyCollectionReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="gridPanel4"  styleClass="row2">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{AgentWiseMonthlyCollectionReport.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{AgentWiseMonthlyCollectionReport.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputText id="lblacType" value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddacType111" value="#{AgentWiseMonthlyCollectionReport.acType}" styleClass="ddlist"  style="width:100px" size="1">
                            <f:selectItems id="selectRepTypeList11" value="#{AgentWiseMonthlyCollectionReport.selectActTypeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel/>     
                        <h:outputLabel/>     
                    </h:panelGrid>                   
                    <h:panelGrid id="panel2" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="frDate" styleClass="input frDate" style="setMask();width:70px;"  value="#{AgentWiseMonthlyCollectionReport.frdt}">                            
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>    
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="toDate" styleClass="input toDate" style="setMask();width:70px;"  value="#{AgentWiseMonthlyCollectionReport.todt}">                            
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>    
                        </h:panelGroup>                        
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer" width="100%">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{AgentWiseMonthlyCollectionReport.btnHtmlAction}" reRender="errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{AgentWiseMonthlyCollectionReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{AgentWiseMonthlyCollectionReport.refresh}" reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnClose" value="Exit" action="#{AgentWiseMonthlyCollectionReport.exitAction}" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>