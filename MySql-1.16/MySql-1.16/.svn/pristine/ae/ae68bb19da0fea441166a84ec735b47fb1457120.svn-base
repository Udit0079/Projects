<%--
    Document   : BackUp
    Created on : Mar 27, 2011, 11:20:16 AM
    Author     : Administrator
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
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>BACK UP</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BackUp.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel"value="BackUp"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{BackUp.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="PanelGrid1" styleClass="row2" columns="3" style="height:30px;text-align:center;"  width="100%">
                        <h:outputText id="msg" styleClass="msg" value="#{BackUp.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="PanelGrid2" styleClass="row1" columns="1" style="height:30px;"  width="100%">
                        <h:outputLabel id="lblbackupforcurrentdate" styleClass="label" value="Backup for current date"/>
                    </h:panelGrid>
                    <h:panelGrid id="PanelGrid3" styleClass="row2" columns="2" columnClasses="col1,col7" style="height:30px;"  width="100%">
                        <h:outputLabel id="lblbackuppath" styleClass="label" value="Backup Path"/>
                        <h:selectOneListbox id="ddlist1" styleClass="ddlist" size="1" style="width: 115px">
                            <f:selectItem itemValue="#{BackUp.description}"></f:selectItem>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" columns="3" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup>
                            <a4j:region id="btnId">
                                <a4j:commandButton type="submit" value="Backup" id="button1" styleClass="" action="#{BackUp.callBackUp}" reRender="msg,PanelGrid1"/>
                            </a4j:region>
                        <a4j:commandButton type="reset" id="btnCancel" value="Refresh" action="#{BackUp.reset}" reRender="msg"/>
                        <a4j:commandButton id="exit" action="#{BackUp.ExitForm}" value="Exit" reRender="msg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status for="btnId" onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="150" height="15" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>