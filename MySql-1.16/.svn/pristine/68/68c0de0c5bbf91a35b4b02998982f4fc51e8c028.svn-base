<%-- 
    Document   : deafMarkInfo
    Created on : Jan 18, 2016, 12:20:36 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calFromDate").mask("99/99/9999");
                    jQuery(".calToDate").mask("99/99/9999");
                }
            </script>
            <title>Deaf Mark Info</title>
        </head>
        <body>
            <a4j:form id="LoanSanctionDetailDuringthePeriod">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid id="PanelGridMain" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" columns="3"  style="text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DeafMarkInfo.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Deaf Mark Info"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{DeafMarkInfo.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{DeafMarkInfo.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2"> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{DeafMarkInfo.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{DeafMarkInfo.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputText value="A/c Nature"styleClass="label" />
                        <h:selectOneListbox id="ddacNature" size="1" value="#{DeafMarkInfo.acNature}" styleClass="ddlist" style="width: 70px" >
                            <f:selectItems value="#{DeafMarkInfo.acNatureList}"/>
                            <a4j:support  action="#{DeafMarkInfo.getAcTypeByAcNature}" event="onchange"reRender="ddacType" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label"  value="A/c Type"/>
                        <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 70px" value="#{DeafMarkInfo.acType}" >
                            <f:selectItems value="#{DeafMarkInfo.acTypeList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5"  styleClass="row1"> 
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{DeafMarkInfo.calFromDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calFromDate" style="setMask();" value="#{DeafMarkInfo.caltoDate}">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{DeafMarkInfo.printAction}" reRender="errmsg,PanelGridMain" oncomplete="setMask();"/>
                            <h:commandButton id="btnExcel" value="Excel Download" action="#{DeafMarkInfo.downloadExcel}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{DeafMarkInfo.refreshAction}" reRender="errmsg,txtAccountNo,PanelGridMain" oncomplete="setMask();"/>
                            <a4j:commandButton action="#{DeafMarkInfo.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
