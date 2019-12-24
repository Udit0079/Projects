<%-- 
    Document   : goireport
    Created on : Jun 20, 2012, 10:48:39 AM
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
            <title>GOI Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{goiReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="GOI Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{goiReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{goiReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="repTypeGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRepType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRepType" size="1" styleClass="ddlist" value="#{goiReport.repType}">
                            <f:selectItems value="#{goiReport.repTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblfrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="txtfrDt" size="10" styleClass="input issueDt" value="#{goiReport.frDt}">
                            <a4j:support event="onblur" action="#{goiReport.onBlurFrDt}" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel id="lblDt" styleClass="label" value="To Date"/>
                        <h:inputText id="txtDt" size="10" styleClass="input issueDt" value="#{goiReport.asOnDt}">
                            <a4j:support event="onblur" action="#{goiReport.onBlurAsOnDt}" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  id="repTypeGrid2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblsellarName" styleClass="label" value="Sellar Name"/>
                        <h:selectOneListbox id="ddReportType" size="1" styleClass="ddlist" value="#{goiReport.sellarName}">
                            <f:selectItems value="#{goiReport.sellarNameList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblstatus" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddstatus" size="1" styleClass="ddlist" value="#{goiReport.status}">
                            <f:selectItems value="#{goiReport.statusList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{goiReport.processAction}" id="btnProcess" value="Print Report" reRender="mainPanel"/>
                            <a4j:commandButton action="#{goiReport.resetAction}" id="btnReset" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{goiReport.exitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
