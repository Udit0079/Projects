<%-- 
    Document   : LockerOperationDetail
    Created on : Sep 6, 2017, 2:32:53 PM
    Author     : Admin
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
            <title>Locker Operation Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calfrm").mask("39/19/9999");
                    jQuery(".calto").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <h:form id="operationReport">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LockerOperationDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Operation Detail"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LockerOperationDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{LockerOperationDetail.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" styleClass="row2" width="100%">
                        <h:outputLabel value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{LockerOperationDetail.branchCode}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{LockerOperationDetail.branchCodeList}"/>
                        </h:selectOneListbox> 
                        <h:outputText value="From Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calfrm" styleClass="input calfrm" style="width:70px;setMask()" maxlength="10" value="#{LockerOperationDetail.frmDt}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText value="To Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt1" layout="block">
                            <h:inputText id="calto" styleClass="input calto" style="width:70px;setMask()" maxlength="10" value="#{LockerOperationDetail.toDt}">
                                <f:convertDateTime pattern="dd/MM/yyyy"/>
                            </h:inputText>
                            <h:outputLabel id="lblapptDT1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columns="8" columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" styleClass="row2" width="100%">
                        <h:outputLabel value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="report" size="1" value="#{LockerOperationDetail.reportOption}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{LockerOperationDetail.reportOptionList}" />
                            <a4j:support action="#{LockerOperationDetail.onBlur}" event="onblur" 
                                         reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1,btn " limitToList="true" focus="gridPanel"/>
                        </h:selectOneListbox> 
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="type" size="1" value="#{LockerOperationDetail.reportType}" styleClass="ddlist" style="width:70px">
                            <f:selectItems value="#{LockerOperationDetail.reportTypeList}" />
                            <a4j:support action="#{LockerOperationDetail.onTypeBlur}" event="onblur" 
                                         reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1,btn,txtLockerNo,lblLockerNo" limitToList="true" focus="gridPanel"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblLockerNo" value="Locker Number :" styleClass="label" style="display:#{LockerOperationDetail.firstDisplay};"/>
                        <h:inputText id="txtLockerNo" styleClass="input" value="#{LockerOperationDetail.lockerNo}"  style="display:#{LockerOperationDetail.firstDisplay};"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                            <%--  <h:commandButton id="btn" value="Print Letter" actionListener="#{LockerOperationDetail.PrintLetter}" styleClass="color: #044F93;text-decoration: none;"></h:commandButton> --%>
                            <a4j:commandButton id="btn" value="Print Letter" disabled="#{LockerOperationDetail.disableLetter}" action="#{LockerOperationDetail.PrintLetter}"
                                               reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1"/>
                            <a4j:commandButton value="Print Report" action="#{LockerOperationDetail.viewReport}" oncomplete="#{rich:element('calfrm')}.style=setMask();
                                               #{rich:element('calto')}.style=setMask();"
                                               reRender="errorMsg,groupPanelapptDt,groupPanelapptDt1"/>
                            <h:commandButton id="btnPDF" value="PDF Download" actionListener="#{LockerOperationDetail.viewPdfReport}" styleClass="color: #044F93;text-decoration: none;"></h:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LockerOperationDetail.refresh}" reRender= "errorMsg,groupPanelapptDt,branch,groupPanelapptDt1,txtLockerNo,type,report" />
                            <a4j:commandButton value="Exit" action="#{LockerOperationDetail.exitAction}"/>
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