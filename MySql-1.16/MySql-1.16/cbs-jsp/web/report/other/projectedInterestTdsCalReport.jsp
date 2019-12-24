<%-- 
    Document   : projectedInterestTdsCalReport
    Created on : 25 Apr, 2018, 12:10:44 PM
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
            <title>Projected Interest Tds Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".frDt").mask("99/99/9999");
                    jQuery(".toDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="tdReceipt">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ProjectedInterestTdsCalReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Projected Interest Tds Report"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ProjectedInterestTdsCalReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{ProjectedInterestTdsCalReport.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3"  width="100%" styleClass="row2">
                        <h:outputLabel value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="reportOpt" size="1" value="#{ProjectedInterestTdsCalReport.repOption}" styleClass="ddlist" style="width:70px;">
                            <f:selectItems value="#{ProjectedInterestTdsCalReport.repOptionList}" />
                            <a4j:support event="onblur" action="#{ProjectedInterestTdsCalReport.repOptAction}" reRender="txtcid" focus="#{ProjectedInterestTdsCalReport.focusId}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Customer Id" id="cid" styleClass="label"/>
                        <h:inputText id="txtcid" value="#{ProjectedInterestTdsCalReport.custId}" maxlength="10"styleClass="input" style="width:70px;" disabled="#{ProjectedInterestTdsCalReport.disableId}"></h:inputText>
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{ProjectedInterestTdsCalReport.branch}" styleClass="ddlist" style="width:70px;">
                            <f:selectItems value="#{ProjectedInterestTdsCalReport.branchList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3"  width="100%" styleClass="row1">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frDt" styleClass="input frDt" maxlength="10" value="#{ProjectedInterestTdsCalReport.frmDt}" style="width:70px;setMask();"/>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toDt" styleClass="input toDt" maxlength="10" value="#{ProjectedInterestTdsCalReport.toDt}" style="width:70px;setMask();"/>
                        <h:outputLabel value="Cust Type" styleClass="label"/>
                        <h:selectOneListbox id="custId1" size="1" value="#{ProjectedInterestTdsCalReport.custType}" styleClass="ddlist" style="width:110px;">
                            <f:selectItems value="#{ProjectedInterestTdsCalReport.custTypeList}" />
                            <a4j:support event="onblur" action="#{ProjectedInterestTdsCalReport.setSeniorCitizenFrAmt}" reRender="txtintFrAmt" focus="#{ProjectedInterestTdsCalReport.focusId}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel7" columns="6"  columnClasses="col1,col3,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <h:outputLabel value="Interest From Amount" id="intFrAmt" styleClass="label"/>
                        <h:inputText id="txtintFrAmt" value="#{ProjectedInterestTdsCalReport.intFrmAmt}" maxlength="10"styleClass="input" style="width:70px;">
                            <a4j:support event="onblur"  focus="txtIntToAmt"  />
                        </h:inputText>
                        <h:outputLabel value="Interest To Amount" styleClass="label" id="IntToAmt"/>
                        <h:inputText id="txtIntToAmt" styleClass="interest toAmt" maxlength="10" value="#{ProjectedInterestTdsCalReport.intToAmt}"style="width:70px;"> 
                            <a4j:support event="onblur"  focus="btnPrint" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{ProjectedInterestTdsCalReport.repType}" styleClass="ddlist" style="width:70px;">
                            <f:selectItems value="#{ProjectedInterestTdsCalReport.repTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{ProjectedInterestTdsCalReport.viewReport}" reRender="message"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{ProjectedInterestTdsCalReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{ProjectedInterestTdsCalReport.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{ProjectedInterestTdsCalReport.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
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

