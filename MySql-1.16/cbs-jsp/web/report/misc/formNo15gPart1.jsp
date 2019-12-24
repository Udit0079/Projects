<%-- 
    Document   : formNo15gPart1
    Created on : Apr 29, 2016, 12:23:51 PM
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
            <title>Form 15G / 15H</title>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="loanAcStateme">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FormNo15gPart1.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Form 15G / 15H"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FormNo15gPart1.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{FormNo15gPart1.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Customer Id" styleClass="label"/>
                        <h:panelGroup id="custId">
                            <h:inputText id="txtCustId" value="#{FormNo15gPart1.custId}" styleClass="input" size="8" maxlength="10" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{FormNo15gPart1.getNewcId}" reRender="newCustId,txtFinan,txtFinan,errorMsg" oncomplete="setMask();" focus="txtFinan"/>
                            </h:inputText>
                            <h:outputText id="newCustId" value="#{FormNo15gPart1.newCustId}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputText value="Financial Year" styleClass="label"/>
                        <h:inputText id="txtFinan" value="#{FormNo15gPart1.finYear}" styleClass="input" size="8" maxlength="4" onkeyup="this.value=this.value.toUpperCase();">
                            <a4j:support event="onblur" actionListener="#{FormNo15gPart1.getNewfYear}" reRender="newFinan,ddrepType111,errorMsg" oncomplete="setMask();" focus="ddrepType111"/>
                        </h:inputText>
                        <%--h:outputText id="newFinan" value="#{FormNo15gPart1.newFinYear}" styleClass="output" style="color:green"/--%>                     
                        <h:outputText id="lblrepType" value="Document Type:" styleClass="label"/>
                        <h:inputText id="ddrepType111" value="#{FormNo15gPart1.repType}" styleClass="input" size="8" disabled="true" onkeyup="this.value=this.value.toUpperCase();"></h:inputText>
                    </h:panelGrid> 
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{FormNo15gPart1.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{FormNo15gPart1.refreshPage}" reRender="a1,errorMsg"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{FormNo15gPart1.exitPage}"/>
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
