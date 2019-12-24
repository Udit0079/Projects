<%-- 
    Document   : tdsCertificate
    Created on : Aug 12, 2015, 6:33:48 PM
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
            <title>Tds Certificate</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="folioStatement">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TdsCertificate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Tds Certificate"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TdsCertificate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{TdsCertificate.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblreportTtpe" styleClass="headerLabel"  value="Report Type"/>
                        <h:selectOneListbox id="reportTtpeid" value="#{TdsCertificate.repType}" styleClass="ddlist" size="1"  style="width:100px">
                            <f:selectItems value="#{TdsCertificate.repTypeList}" />
                            <a4j:support  action="#{TdsCertificate.onValueReportType}" event="onblur"
                                          reRender="fId,errorMsg" focus="txtacno" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputText id="fId"value="#{TdsCertificate.field}" styleClass="label"/>
                        <h:panelGroup id="folioId">
                            <h:inputText id="txtacno" value="#{TdsCertificate.acno}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support action="#{TdsCertificate.acnoValidate}" event="onblur" reRender="stxtAcnoShow,errorMsg" focus="txtYear"/>
                            </h:inputText>
                            <h:outputText id="stxtAcnoShow" styleClass="output" value="#{TdsCertificate.newAcno}" style="color:green"/>
                        </h:panelGroup>
                        <h:outputText value="Financial Year :" styleClass="label"/>
                        <h:panelGroup id="certificateId">
                            <h:inputText id="txtYear" value="#{TdsCertificate.finYear}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support action="#{TdsCertificate.yearValidate}" event="onblur" reRender="stxtYearShow,errorMsg" focus="print"/>
                            </h:inputText>
                            <h:outputText id="stxtYearShow" styleClass="output" value="#{TdsCertificate.newFinYear}"style="color:green" />
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print" action="#{TdsCertificate.printAction}" reRender="errorMsg"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{TdsCertificate.refreshPage}" reRender="errorMsg,a1"  oncomplete="setMask();"/>
                            <%--a4j:commandButton  id="exit" value="Exit" action="case1"/--%>
                            <a4j:commandButton action="#{TdsCertificate.btnExitAction}" id="btnExit" value="Exit" reRender="errorMsg,a1" oncomplete="setMask();"/>
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
