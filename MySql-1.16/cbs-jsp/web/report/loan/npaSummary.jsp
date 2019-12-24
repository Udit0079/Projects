<%-- 
    Document   : npaSummary
    Created on : Feb 5, 2013, 4:45:26 PM
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
            <title>NPA Summary Report</title>
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
            <h:form id="npaSummary">
                <h:panelGrid columns="1" id="pg1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="pg2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="pgr1" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NpaSummary.todayDate}"/>                        
                        </h:panelGroup>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NpaSummary.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" style="color:red;" value="#{NpaSummary.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%" >
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{NpaSummary.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{NpaSummary.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputText value="As on Date" styleClass="label"/>
                        <h:inputText id="txtFrmDate" styleClass="input calInstDate" style="width:70px;" value="#{NpaSummary.toDt}"/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid> 
                    <h:panelGrid columns="1" id="fpg1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                        <h:panelGrid columns="1" id="fpg2" style="text-align: center; width:100%" styleClass="footer">
                            <h:panelGroup id="fpgr1" layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton value="Print Report" action="#{NpaSummary.btnViewReport}" reRender="errorMsg,toDt"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{NpaSummary.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton value="Refresh" action="#{NpaSummary.btnRefreshAction}" reRender="errorMsg, toDt"/>
                                <a4j:commandButton value="Exit" action="#{NpaSummary.btnExitAction}" reRender="errorMsg,toDt"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>                
                </h:panelGrid>
            </h:form> 
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>