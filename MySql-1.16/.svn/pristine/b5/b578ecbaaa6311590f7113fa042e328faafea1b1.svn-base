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
            <title>OSS-8 Statement On Bank Profile</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{oss8Controller.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="OSS-8 Statement On Bank Profile"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{oss8Controller.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMessage" styleClass="error" style="color:red;" value="#{oss8Controller.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="7"  columnClasses="col3,col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblRpType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRpType" size="1" styleClass="ddlist" value="#{oss8Controller.reportType}">
                            <f:selectItems value="#{oss8Controller.reportTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtFirstDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFirstDt" styleClass="label" value="Report Date"/>
                        <h:inputText id="txtFirstDt" size="10" styleClass="input issueDt" value="#{oss8Controller.reportDt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddRuppeType"/>
                        </h:inputText>
                        <h:outputLabel id="lblRuppeType" styleClass="label" value="Report In"/>
                        <h:selectOneListbox id="ddRuppeType" size="1" styleClass="ddlist" value="#{oss8Controller.reportIn}">
                            <f:selectItems value="#{oss8Controller.reportInList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddRepOpt"/>
                        </h:selectOneListbox>
                        <h:selectOneListbox id="ddRepOpt" size="1" styleClass="ddlist" value="#{oss8Controller.reportOption}">
                            <f:selectItems value="#{oss8Controller.reportOptionList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddEmpOff"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6"  columnClasses="col3,col3,col3,col3,col3,col3"  width="100%" styleClass="row2">
                        <h:outputLabel id="lblEmpOff" styleClass="label" value="No of Bank Officier"/>
                        <h:inputText id="ddEmpOff" size="2" styleClass="ddlist" value="#{oss8Controller.noOfBankOfficier}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="ddEmpStaff"/>
                        </h:inputText>
                        <h:outputLabel id="lblEmpStaff" styleClass="label" value="No of Other Staff"/>
                        <h:inputText id="ddEmpStaff" size="2" styleClass="ddlist" value="#{oss8Controller.noOfOtherBankStaff}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtDividend"/>
                        </h:inputText>
                        <h:outputLabel id="lblDividend" styleClass="label" value="Dividend (%)"/>
                        <h:inputText id="txtDividend" size="4" styleClass="input" value="#{oss8Controller.dividend}">
                            <a4j:support event="onblur" action="#{oss8Controller.dtProcessAction}" reRender="lblMessage" oncomplete="setMask();" focus="btnDownload"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{oss8Controller.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{oss8Controller.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{oss8Controller.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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
