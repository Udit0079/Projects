<%-- 
    Document   : shareAccStmt
    Created on : Mar 23, 2012, 5:38:30 PM
    Author     : admin
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
            <title>Share Account Statement Report</title>
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
            <h:form id="shareAcctStmt">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{shareAcctStmt.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Account Statement"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{shareAcctStmt.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{shareAcctStmt.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputText value="Folio No" styleClass="label"/>
                        <h:inputText id="txtfno" value="#{shareAcctStmt.folionoShow}" size="#{shareAcctStmt.acNoMaxLen}" maxlength="#{shareAcctStmt.acNoMaxLen}" styleClass="input" >
                            <a4j:support action="#{shareAcctStmt.folioToMethod}" event="onblur" focus="txtacno" reRender="stxtFolioShow,errorMsg"/>
                        </h:inputText>
                        <h:outputText id="stxtFolioShow" styleClass="output" value="#{shareAcctStmt.foliono}" />
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel/>
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:inputText id="txtacno" value="#{shareAcctStmt.frDt}" styleClass="input frDt" size="12" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="frDt" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputText id="newAcno" value="To Date" styleClass="label" />
                        <h:inputText id="frDt" styleClass="input frDt" size="12" value="#{shareAcctStmt.toDt}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" focus="print" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton  id="print" value="Print Report" action="#{shareAcctStmt.viewReport}" reRender="errorMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{shareAcctStmt.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{shareAcctStmt.refreshAction}" reRender="errorMsg,txtacno,newAcno,frDt,toDt,txtfno,stxtFolioShow"  oncomplete="setMask();"/>
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
