<%-- 
    Document   : npaRecovery
    Created on : Feb 4, 2013, 6:55:45 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Npa Recovery/Auto Npa to Operative Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{npaRecovery.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Npa Recovery/Auto Npa to Operative Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npaRecovery.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{npaRecovery.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputText value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="idReport" size="1" value="#{npaRecovery.repType}" styleClass="ddlist">
                            <f:selectItems value="#{npaRecovery.repTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{npaRecovery.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{npaRecovery.branchCodeList}" />
                        </h:selectOneListbox>  
                        <h:outputLabel id="status" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddStatus" size="1" styleClass="ddlist" value="#{npaRecovery.acType}">
                            <f:selectItems value="#{npaRecovery.acTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();" focus="frDate"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4"  columnClasses="col13,col13,col13,col13"  width="100%" styleClass="row1">                        
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{npaRecovery.frdt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="toDate"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{npaRecovery.todt}">
                            <a4j:support event="onblur" oncomplete="setMask();" focus="btnHtml"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{npaRecovery.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{npaRecovery.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{npaRecovery.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton action="#{npaRecovery.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
