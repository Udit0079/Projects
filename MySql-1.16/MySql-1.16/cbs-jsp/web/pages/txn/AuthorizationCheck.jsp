<%--
    Document   : DailyProcess
    Created on : Jan 5, 2011, 2:22:13 PM
    Author     : sipl
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
            <title>Authorization Check Process</title>
        </head>
        <body>
            <a4j:form id="authChkProcessForm">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AuthorizationCheck.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Authorization Check Process"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AuthorizationCheck.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="bodyPanel" style="width:100%;height:300px;text-align:center;background-color:#e8eef7">
                        <rich:dataGrid  id="dataGrid"value="#{AuthorizationCheck.msgShowList}" style="width:50%;display:#{AuthorizationCheck.flag1};"styleClass="row2"var="msgItem" columns="1" align="center">
                            <f:facet name="header">
                                <h:outputText value="Pending Authorizations" ></h:outputText>
                            </f:facet>
                            <h:panelGrid style="text-align:center;" columns="1" >
                                <h:outputText id="stxtmsg1" style="color:blue;"styleClass="output" value="#{msgItem.msg1}"/>
                            </h:panelGrid>
                        </rich:dataGrid>
                        <rich:datascroller for="dataGrid" id="dataScroll" maxPages="10" style="display:#{AuthorizationCheck.flag1};"></rich:datascroller>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" style="text-align:center;width:100%;" styleClass="row1">
                        <h:outputText id="errorMsg" styleClass="error" value="#{AuthorizationCheck.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="authChkRegion">
                                <a4j:commandButton  id="btnAuthCheck" value="Auth Check" action="#{AuthorizationCheck.clickOnAuthChk}" oncomplete="#{rich:element('dataGrid')}.style.display=#{AuthorizationCheck.flag1};#{rich:element('dataScroll')}.style.display=#{AuthorizationCheck.flag1};"reRender="dataGrid,stxtmsg1,dataScroll,errorMsg">
                                </a4j:commandButton>
                                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="authChkRegion"/>
                                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                                    <f:facet name="header">
                                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                                    </f:facet>
                                </rich:modalPanel>
                            </a4j:region>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AuthorizationCheck.exitForm}" reRender="dataGrid,stxtmsg1,dataScroll,errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
