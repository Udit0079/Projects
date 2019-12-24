<%-- 
    Document   : ccturnoverreport
    Created on : 11 Sep, 2019, 2:37:45 PM
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
            <title>CC Turnover Report</title>
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
            <h:form id="ccturnoverreport">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CCTurnOverReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="CC Turnover/Statement Of ALM Reports"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CCTurnOverReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{CCTurnOverReport.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"  styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{CCTurnOverReport.brncode}" styleClass="ddlist">
                            <f:selectItems value="#{CCTurnOverReport.branchcodeList}" />
                        </h:selectOneListbox>                         
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="reportList" size="1" value="#{CCTurnOverReport.reportType}" styleClass="ddlist">
                            <f:selectItems value="#{CCTurnOverReport.reportTypeList}" />
                        </h:selectOneListbox>                        
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="accountnoList" size="1" value="#{CCTurnOverReport.actype}" styleClass="ddlist">
                            <f:selectItems value="#{CCTurnOverReport.acTypeList}" />
                        </h:selectOneListbox>           
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:panelGroup styleClass="label">  
                            <h:inputText id="fromDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{CCTurnOverReport.fromDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel value="To Date" styleClass="label"/>
                            <h:panelGroup styleClass="label">
                                <h:inputText id="toDate"   styleClass="input calInstDate" style="setMask();width:70px;"  value="#{CCTurnOverReport.toDate}"/>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputLabel id="a"></h:outputLabel>
                            <h:outputLabel id="b"></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">                                                       
                             <a4j:commandButton action="#{CCTurnOverReport.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="errorMsg,a1"/>    
                            <h:commandButton id="btnPDF"  value="PDF Download" action="#{CCTurnOverReport.viewPdfReport}"   styleClass="color: #044F93;text-decoration: none;"  ></h:commandButton>
                                <a4j:commandButton value="Refresh" action="#{CCTurnOverReport.refresh}" oncomplete="setMask()" reRender="txtToDate,a1"/>
                                <a4j:commandButton   value="Exit" action="#{CCTurnOverReport.exitAction}" oncomplete="setMask()" reRender="txtToDate,a1"/>
                            </h:panelGroup>
                        </h:panelGrid>                 
                    </h:panelGrid>
                </h:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>
