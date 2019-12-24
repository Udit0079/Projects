<%--
    Document   : reporting friday master
    Created on : Sep 26, 2010, 9:35:59 AM
    Author     : Sudhir Kr bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Reporting Friday Master</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ReportingFridayMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Reporting Friday Master"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel"  value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ReportingFridayMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="text-align:center" styleClass="row2">
                        <h:outputText id="errorGrid" value="#{ReportingFridayMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel1" columns="2" columnClasses="col8,col8" width="100%">
                        <h:panelGrid id="gridPanel2" columns="2" columnClasses="col8,col8" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblAcDesc" styleClass="label" value="Account Description"/>
                            <h:selectOneListbox id="ddAcDesc" styleClass="ddlist" size="1" style="width:60%" value="#{ReportingFridayMaster.accDescType}" >
                                <f:selectItems value="#{ReportingFridayMaster.accountDescList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid id="gridPanel3" columns="2" columnClasses="col8,col8" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblBalType" styleClass="label" value="Balance Type"/>
                            <h:selectOneListbox id="ddBalTp" styleClass="ddlist" size="1" style="width:50%" value="#{ReportingFridayMaster.balType}" >
                                <f:selectItems value="#{ReportingFridayMaster.balanceTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:commandButton id="btnSave" value="Save" action="#{ReportingFridayMaster.saveData}" reRender="errorGrid,ddAcDesc,ddBalTp"/>
                            <a4j:commandButton id="btnDelete" value="Delete" action="#{ReportingFridayMaster.deleteData}" reRender="errorGrid,ddAcDesc,ddBalTp"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ReportingFridayMaster.refresh}" reRender="errorGrid,ddAcDesc,ddBalTp"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ReportingFridayMaster.exitForm}" reRender="errorGrid,ddAcDesc,ddBalTp"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
