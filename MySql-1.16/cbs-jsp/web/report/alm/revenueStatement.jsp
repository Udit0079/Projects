<%-- 
    Document   : RevenueStatement
    Created on : Dec 12, 2011, 6:16:07 PM
    Author     : Sudhir
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
            <title>Revenue Statement</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RevenueStatement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Revenue Statement"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{RevenueStatement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting" style="width:100%;text-align:center" styleClass="row2" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{RevenueStatement.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="10" id="bodyPanel" width="100%" styleClass="row1" style="text-align:center;">
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{RevenueStatement.branch}" styleClass="ddlist">
                            <f:selectItems value="#{RevenueStatement.branchList}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                            <h:selectOneListbox id="repTypeddl" size="1" value="#{RevenueStatement.repType}" styleClass="ddlist" style="width:140px">
                                <f:selectItems value="#{RevenueStatement.repTypeList}" />
                            </h:selectOneListbox>
                        <h:outputLabel id="fromDate" styleClass="label" value="From Date : " style="align:center"/>
                        <h:inputText id="fromDateText" styleClass="input calInstDate"  style="width:80px;text-align:left" value="#{RevenueStatement.fromDate}"/>

                        <h:outputLabel id="toDate" styleClass="label" value="To Date : " style="align:center"/>
                        <h:inputText id="toDateText" styleClass="input calInstDate"  style="width:80px;text-align:left" value="#{RevenueStatement.toDate}"/>
                        <h:outputLabel id="lbRepOpt" styleClass="label"  value="Report Option"/>
                        <h:selectOneListbox id="ddRepOpt" styleClass="ddlist" size="1" style="width: 80px" value="#{RevenueStatement.repOpt}" >
                            <f:selectItems value="#{RevenueStatement.repOptionList}"/>
                        </h:selectOneListbox>  
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block" >
                            <a4j:commandButton id="btnReport" value="PDF Download" action="#{RevenueStatement.printReport()}" styleClass="color:#044F93;text-decoration:none;"  reRender="errorGrid,tillDateText"/>
                            <a4j:commandButton id="btntxtReport" value="Print Report" action="#{RevenueStatement.textReport()}" styleClass="color:#044F93;text-decoration:none;"  reRender="errorGrid,tillDateText"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{RevenueStatement.refresh()}" oncomplete="setMask();" reRender="errorGrid,tillDateText"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{RevenueStatement.exitFrm()}"/>
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