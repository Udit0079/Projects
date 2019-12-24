<%-- 
    Document   : impsTxnReport
    Created on : 18 Jul, 2018, 5:37:13 PM
    Author     : root
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>IMPS TRANSACTION REPORT</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <h:form id="loanPeriod">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">

                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ImpsTxnReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Imps Transaction Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ImpsTxnReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{ImpsTxnReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="brani" size="1" value="#{ImpsTxnReport.branch}" styleClass="ddlist">
                            <f:selectItems value="#{ImpsTxnReport.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="IMPS Type" styleClass="label"/>
                        <h:selectOneListbox id="imps" size="1" value="#{ImpsTxnReport.impsType}" styleClass="ddlist">
                            <f:selectItems value="#{ImpsTxnReport.impsTypeList}" />
                            <a4j:support action="#{ImpsTxnReport.impsOption}" event="onblur" reRender="panel1"/>
                        </h:selectOneListbox>  
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row1" width="100%" style="display:#{ImpsTxnReport.panelDisplay}">
                        <h:outputText value="Process" styleClass="label"/>
                        <h:selectOneListbox id="proc" size="1" value="#{ImpsTxnReport.process}" styleClass="ddlist">
                            <f:selectItems value="#{ImpsTxnReport.processList}" />
                        </h:selectOneListbox>  
                        <h:outputText value="Mode" styleClass="label"/>
                        <h:selectOneListbox id="mode" size="1" value="#{ImpsTxnReport.mode}" styleClass="ddlist">
                            <f:selectItems value="#{ImpsTxnReport.modeList}" />
                        </h:selectOneListbox>  
                    </h:panelGrid>                   
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtFrmDate"   styleClass="input calInstDate" style="setMask();"  value="#{ImpsTxnReport.frmDt}" size="10"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputText value="To Date" styleClass="label"/>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="txtToDate"   styleClass="input calInstDate" style="setMask();"  value="#{ImpsTxnReport.toDt}" size="10"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton  value="Print Report" action="#{ImpsTxnReport.viewReport()}" reRender="errorMsg,txtFrmDate,txtToDate" oncomplete="#{rich:element('txtFrmDate')}.style=setMask();#{rich:element('txtToDate')}.style=setMask()"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{ImpsTxnReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton value="Refresh" action="#{ImpsTxnReport.refresh}" oncomplete="setMask();" reRender="txtFrmDate,txtToDate,a1"/>
                                <a4j:commandButton   value="Exit" action="#{ImpsTxnReport.exitAction}" oncomplete="setMask();" reRender="txtFrmDate,txtToDate,a1"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
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


