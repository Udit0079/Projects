<%-- 
    Document   : chequebookstop
    Created on : Dec 12, 2011, 6:17:47 PM
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
            <title>Cheque book stop payment register</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".fromdate").mask("99/99/9999");
                    jQuery(".toDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel0" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ChequeBookStop.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblAccountMaintenanceRegister" styleClass="headerLabel" value="Cheque Book Stop Payment Register"/>
                        <h:panelGroup id="a4" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText styleClass="output" id="stxtUser" value="#{ChequeBookStop.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorMsgReporting" style="text-align:center" styleClass="row1" width="100%">
                        <h:outputText id="errorGrid" styleClass="error" value="#{ChequeBookStop.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="mainPanel1" width="100%" styleClass="row2" style="text-align:center">
                        <h:outputLabel id="branch" styleClass="label" value="Branch"/>
                        <h:selectOneListbox id="branchList" style="width:100px;" styleClass="ddlist" size="1" value="#{ChequeBookStop.branch}"> 
                            <f:selectItems value="#{ChequeBookStop.branchList}"/>
                            <a4j:support ajaxSingle="true" event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="reportType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="reportTypeList"  style="width:160px;" styleClass="ddlist" size="1" value="#{ChequeBookStop.reportType}"> 
                            <f:selectItems value="#{ChequeBookStop.reportTypeList}"/>
                            <a4j:support event="onchange" action="#{ChequeBookStop.onChaneReportType()}" reRender="actypeList,errorGrid"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="actype" styleClass="label" value="A/c Type"/>
                        <h:selectOneListbox id="actypeList" disabled="#{ChequeBookStop.disableAccType}"  style="width:100px;" styleClass="ddlist" size="1" value="#{ChequeBookStop.accountType}"> 
                            <f:selectItems value="#{ChequeBookStop.accountTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="mainpanel2" width="100%" styleClass="row1" style="text-align:center">
                        <h:outputLabel id="lblfromDate" styleClass="label" value="From Date"/>
                        <h:inputText id="fromdate" styleClass="input fromdate" style="setMask();" size="10" value="#{ChequeBookStop.fromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>
                        <h:outputLabel id="lblTODate" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" styleClass="input toDate"  style="setMask();" size="10" value="#{ChequeBookStop.toDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="5" id="gridpanel6" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="a1" layout="block">
                            <a4j:commandButton id="btnReport" value="Print Report" action="#{ChequeBookStop.printReport()}" 
                                               oncomplete="if(#{ChequeBookStop.flag=='false'}) {#{rich:element('errorMsgReporting')}.style.display='';}" reRender="errorMsgReporting"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ChequeBookStop.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{ChequeBookStop.exitFrm()}"/>
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
