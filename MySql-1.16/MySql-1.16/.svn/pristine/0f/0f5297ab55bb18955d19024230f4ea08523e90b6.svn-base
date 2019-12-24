<%-- 
    Document   : sigScanRep
    Created on : Jan 13, 2015, 4:33:31 PM
    Author     : mayank
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Signature Scanned</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SigScanReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Signature Scanned Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SigScanReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%">
                        <h:outputText id="stxtError" styleClass="error" value="#{SigScanReport.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel1" styleClass="row1" width="100%" >
                        <h:outputText value="A/c Nature"styleClass="label"/>
                        <h:selectOneListbox id="ddacType" size="1" value="#{SigScanReport.acnoNature}" styleClass="ddlist">
                            <f:selectItems value="#{SigScanReport.acnoNatureList}"/>
                            <a4j:support action="#{SigScanReport.getAcTypeByAcNature}" event="onchange" reRender="selectListBox"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAcType" styleClass="headerLabel" value="Account Type"/>
                        <h:selectOneListbox id="selectListBox" value="#{SigScanReport.selectAcType}" styleClass="ddlist" size="1">
                            <f:selectItems id="selectAcTypeList" value="#{SigScanReport.selectAcTypeList}"/>
                        </h:selectOneListbox>                        
                    </h:panelGrid>
                    <h:panelGrid columns="4" columnClasses="col13,col13,col13,col13" id="gridpanel2" styleClass="row2" width="100%" >
                        <h:outputText value="Option" styleClass="label"/>
                        <h:selectOneListbox id="ddOpt" size="1" value="#{SigScanReport.optType}" styleClass="ddlist">
                            <f:selectItems value="#{SigScanReport.optTypeLst}"/>                            
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnView" value="View" action="#{SigScanReport.viewReport}" reRender="stxtError,mainPanelGrid,txtDate"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SigScanReport.refreshForm}" reRender="mainPanelGrid,txtDate"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{SigScanReport.exitAction}" reRender="mainPanelGrid"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>