<%-- 
    Document   : billreport
    Created on : Nov 23, 2011, 11:53:26 AM
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
            <a4j:keepAlive beanName="BillReport"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Sundry Suspence Bill Report</title>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" >
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BillReport.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Sundry Suspence/Bill Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BillReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorpanel" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{BillReport.msg}"/>
                        <h:message for="typereport" styleClass="error" />
                    </h:panelGrid>
                    <h:panelGrid id="panel1" width="100%" style="text-align:center" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1">
                        <h:outputText/>
                        <h:outputText/>
                            <h:outputLabel value="Select Report" styleClass="label" />
                            <h:selectOneListbox id="typereport" size="1" value="#{BillReport.typereport}" styleClass="ddlist" validatorMessage="Please Select The Report Type" >
                            <f:selectItem itemLabel="--Select--" itemValue="0000" />
                            <f:selectItem itemLabel="Sundry Report" itemValue="0" />
                            <f:selectItem itemLabel="Suspence Report" itemValue="1" />
                            <f:validateLength maximum="1" minimum="1" />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" width="100%" style="text-align:left" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2">
                        <h:outputText/>
                        <h:outputLabel value="From date" styleClass="label"/>
                        <rich:calendar id="fromdt" value="#{BillReport.fromdt}" datePattern="dd/MM/yyyy" >
                        </rich:calendar>
                        <h:outputLabel value="To date" styleClass="label" />
                        <rich:calendar id="todt" value="#{BillReport.todt}" datePattern="dd/MM/yyyy" validatorMessage="Please enter to date" >
                         
                        </rich:calendar>
                         <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnPrint" value="Print Html"   action="#{BillReport.showReport}" reRender="stxtError,mainPanelGrid,fromdt,todt">
                            </a4j:commandButton>
                             <a4j:commandButton  id="btnpdf" value="Print Pdf"   action="#{BillReport.showReportpdf}" reRender="stxtError,mainPanelGrid,fromdt,todt">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{BillReport.refreshBtn}" immediate="true" reRender="mainPanelGrid" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" immediate="true" action="case1"  >
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
