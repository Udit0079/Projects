<%-- 
    Document   : form1
    Created on : Apr 13, 2013, 1:05:37 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Form1 ( XBRL FORM 1) / Form9 ( XBRL FORM 9) Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{form1Controller.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Form1(XBRL FORM 1) / Form9(XBRL FORM 9) Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{form1Controller.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{form1Controller.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblRpType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRpType" size="1" styleClass="ddlist" value="#{form1Controller.reportType}">
                            <f:selectItems value="#{form1Controller.reportTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtFirstDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFirstDt" styleClass="label" value="First Alternate Friday"/>
                        <h:inputText id="txtFirstDt" size="10" styleClass="input issueDt" value="#{form1Controller.firstAltDt}">
                            <a4j:support event="onblur" action="#{form1Controller.processOnFriday}" reRender="message" oncomplete="setMask();" focus="ddRuppeType"/>
                        </h:inputText>                        
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" width="100%" styleClass="row2">
                        <h:outputLabel id="lblReprtFormat" styleClass="label" value="Format In"/>
                        <h:selectOneListbox id="ddRptFormat" size="1" styleClass="ddlist" value="#{form1Controller.reportFormat}">
                            <f:selectItems value="#{form1Controller.reportFormatIn}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRuppeType" styleClass="label" value="Report In"/>
                        <h:selectOneListbox id="ddRuppeType" size="1" styleClass="ddlist" value="#{form1Controller.reportIn}">
                            <f:selectItems value="#{form1Controller.reportInList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>   
                    </h:panelGrid> 
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{form1Controller.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{form1Controller.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{form1Controller.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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
