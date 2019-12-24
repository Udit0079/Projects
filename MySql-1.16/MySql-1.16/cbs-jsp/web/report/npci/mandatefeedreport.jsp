<%-- 
    Document   : mandatefeedreport
    Created on : 20 Nov, 2017, 3:25:58 PM
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
            <title>Mandate Feeding Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".toDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{mandateFeedBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Outward Mandate Feeding Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{mandateFeedBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{mandateFeedBean.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"/>
                        <h:selectOneListbox id="ddBranch" size="1" styleClass="ddlist" value="#{mandateFeedBean.branch}" style="width:120px;">
                            <f:selectItems value="#{mandateFeedBean.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblTxnType" styleClass="label" value="Txn Type"/>
                        <h:selectOneListbox id="ddTxnType" size="1" styleClass="ddlist" value="#{mandateFeedBean.txnType}" style="width:120px;">
                            <f:selectItems value="#{mandateFeedBean.txnTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lbloption" styleClass="label" value="Option"/>
                         <h:selectOneListbox id="ddoption" size="1" styleClass="ddlist" value="#{mandateFeedBean.option}" style="width:120px;">
                            <f:selectItems value="#{mandateFeedBean.optionList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input toDate"  value="#{mandateFeedBean.frdt}"/>
                         <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                          <h:inputText id="toDate" size="10" styleClass="input toDate" value="#{mandateFeedBean.todt}"/>
                          <h:outputLabel></h:outputLabel>
                          <h:outputText></h:outputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" action="#{mandateFeedBean.btnHtmlAction}" value="Print Report" reRender="lblMsg"/>
                            <a4j:commandButton id="btnRefresh" action="#{mandateFeedBean.btnRefreshAction}"  value="Refresh" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit"action="#{mandateFeedBean.btnExitAction}"  value="Exit" reRender="mainPanel"/>
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
