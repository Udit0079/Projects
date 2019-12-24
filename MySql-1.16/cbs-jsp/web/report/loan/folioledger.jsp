<%-- 
    Document   : folioledger
    Created on : Nov 26, 2015, 11:51:17 AM
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
            <title>Folio Ledger</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{FolioLedger.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Folio Ledger"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FolioLedger.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{FolioLedger.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText value="Folio Number" styleClass="label"/>
                        <h:panelGroup id="folionoId">
                            <h:inputText id="txtacno" value="#{FolioLedger.folioNo}" styleClass="input" size="12" maxlength="12" onkeyup="this.value=this.value.toUpperCase();">
                                <a4j:support event="onblur" actionListener="#{FolioLedger.getNewAccNo}" reRender="newAcno,errorMsg" oncomplete="setMask();" focus="frDt"/>
                            </h:inputText>
                            <h:outputText id="newAcno" value="#{FolioLedger.newFolioNo}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{FolioLedger.frmDt}">
                            <a4j:support event="onblur" focus="toDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" size="12" value="#{FolioLedger.toDt}">
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{FolioLedger.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{FolioLedger.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{FolioLedger.refreshPage}" oncomplete="setMask();" reRender="errorMsg,txtacno,newAcno,frDt,toDt"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{FolioLedger.exitPage}"/>
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
