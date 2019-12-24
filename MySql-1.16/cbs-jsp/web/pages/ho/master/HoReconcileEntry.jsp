<%-- 
    Document   : HoReconsileEntry
    Created on : Dec 7, 2010, 10:13:54 PM
    Author     : Sudhir kr Bisht
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Ho Reconcile Entry </title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{HoReconcileEntry.newDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Ho Reconcile Entry"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label1" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{HoReconcileEntry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="error1"  width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="errorMsg1" styleClass="error" value="#{HoReconcileEntry.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%" styleClass="row1">
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel2" style="height:100px;"  width="100%">
                            <a4j:commandButton  disabled="#{HoReconcileEntry.disableOriginating}"  reRender="errorMsg1,btnResponding,btnOriginating,btnReconsilation" styleClass="dr-pnl-b1" id="btnOriginating" value="Originating" action="#{HoReconcileEntry.saveOriginatingData}"/>
                            <a4j:commandButton styleClass="dr-pnl-b1"  disabled="#{HoReconcileEntry.disableResponding}" id="btnResponding" value="Responding"  reRender="errorMsg1,btnResponding,btnOriginating,btnReconsilation"  action="#{HoReconcileEntry.saveRespondingData}"/>
                        </h:panelGrid>
                        <h:panelGrid  columnClasses="col8,col8" columns="2" id="gridpanel3" style="height:100px;"  width="100%">
                            <a4j:commandButton styleClass="dr-pnl-b1"   id="btnReconsilation"  disabled="#{HoReconcileEntry.disableReconcilation}" value="Reconcilation"  reRender="errorMsg1,btnResponding,btnOriginating,btnReconsilation" action="#{HoReconcileEntry.saveReconsilationData}"/>
                            <a4j:commandButton reRender="errorMsg1,btnOriginating,btnResponding,btnReconsilation" styleClass="dr-pnl-b1" id="btnExit" value="Exit" action="#{ReportingFriday.exitForm}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>