<%-- 
    Document   : accwiesecsachreport
    Created on : 25 Jul, 2017, 6:11:51 PM
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Account Wise ACH/ECS Status Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{accwiesECSACHReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="A/c Wise Sponsor ACH ECS Txn Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{accwiesECSACHReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{accwiesECSACHReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"/>
                        <h:selectOneListbox id="ddFileType" size="1" styleClass="ddlist" value="#{accwiesECSACHReport.fileType}" style="width:120px;">
                            <f:selectItems value="#{accwiesECSACHReport.fileTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTxnType" styleClass="label" value="Txn Type"/>
                        <h:selectOneListbox id="ddTxnType" size="1" styleClass="ddlist" value="#{accwiesECSACHReport.txnType}" style="width:120px;">
                            <f:selectItems value="#{accwiesECSACHReport.txnTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                        <h:inputText id="txtAccNo" styleClass="input" value="#{accwiesECSACHReport.accountNo}" maxlength="#{accwiesECSACHReport.acNoMaxLen}" style="width:120px;setMask();"/> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblFromDate" styleClass="label" value="From Date"/>
                        <h:inputText id="txtFromDate" styleClass="input issueDt" value="#{accwiesECSACHReport.fromDate}" style="width:120px;setMask();"/> 
                        <h:outputLabel id="lblToDate" styleClass="label" value="To Date"/>
                        <h:inputText id="txtToDate" styleClass="input issueDt" value="#{accwiesECSACHReport.toDate}" style="width:120px;setMask();"/> 
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{accwiesECSACHReport.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="mainPanel" oncomplete="setMask();"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{accwiesECSACHReport.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{accwiesECSACHReport.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{accwiesECSACHReport.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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
