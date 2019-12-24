<%-- 
    Document   : sssregistrationdetails
    Created on : Jun 4, 2015, 2:19:32 PM
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>SSS Acno Registration Detail</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".fromDateText").mask("99/99/9999");
                    jQuery(".toDateText").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="cashclosingreport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{SSSRegistrationDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="SSS Acno Registration Detail"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{SSSRegistrationDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{SSSRegistrationDetails.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel4" width="100%" styleClass="row2">
                        <h:outputLabel value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{SSSRegistrationDetails.branchCode}" styleClass="ddlist" style="width:70px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Scheme Name"/>
                        <h:selectOneListbox id="ddSecurityType"styleClass="ddlist"  value="#{SSSRegistrationDetails.schemeCode}" size="1" style="width:160px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.schemeCodeList}" />
                            <a4j:support action="#{SSSRegistrationDetails.getVendor}" event="onblur" reRender="ddCtrlNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Vendor Name"/> 
                        <h:selectOneListbox id="ddCtrlNo" styleClass="ddlist" value="#{SSSRegistrationDetails.vendorCode}" size="1" style="width:150px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.vendorCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddrepstatus"styleClass="ddlist" value="#{SSSRegistrationDetails.repStatus}" size="1"  style="width:70px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.repStatusList}" />
                            <a4j:support event="onblur" action="#{SSSRegistrationDetails.onChange}" reRender="gridPanel5,gridPanel6" 
                                         oncomplete="if(#{SSSRegistrationDetails.repStatus=='D'||SSSRegistrationDetails.repStatus=='S'}){#{rich:element('fromDateText')}.focus();}else if(#{SSSRegistrationDetails.repStatus=='T'}){#{rich:element('uploadDtText')}.focus();};setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel5" width="100%" styleClass="row1"style="height:30px;display:#{SSSRegistrationDetails.repDisplay}">
                        <h:outputLabel  value="From Date" styleClass="label"/>
                        <h:inputText id="fromDateText" styleClass="input fromDateText" value="#{SSSRegistrationDetails.frDt}" size = "1" style="width:70px;setMask();">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel  value="To Date" styleClass="label"/>
                        <h:inputText id="toDateText" styleClass="input toDateText" value="#{SSSRegistrationDetails.toDt}" size = "1" style="width:70px;setMask();">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddstatus"styleClass="ddlist" value="#{SSSRegistrationDetails.status}" size="1"  style="width:150px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.statusList}" />
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Gender Type"/>
                        <h:selectOneListbox id="ddgenType"styleClass="ddlist" value="#{SSSRegistrationDetails.genType}" size="1"  style="width:70px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.genTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col15,col15,col15,col15,col15,col15,col15,col1" columns="8" id="gridPanel6" width="100%" styleClass="row1" style="height:30px;display:#{SSSRegistrationDetails.display}">
                        <h:outputLabel  value="Upload Date" styleClass="label"/>
                        <h:inputText id="uploadDtText" styleClass="input fromDateText" value="#{SSSRegistrationDetails.uploadDt}" size = "1" style="width:70px;setMask();">
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                            <a4j:support event="onblur" action="#{SSSRegistrationDetails.retriveActualFile}" reRender="ddctrFile"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Control File"/>
                        <h:selectOneListbox id="ddctrFile"styleClass="ddlist" value="#{SSSRegistrationDetails.ctrFile}" size="1"  style="width:160px;setMask();">
                            <f:selectItems value="#{SSSRegistrationDetails.ctrFileList}" />
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{SSSRegistrationDetails.printAction}" type="submit" value="Print Report" reRender="errmsg">
                            </a4j:commandButton>
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{SSSRegistrationDetails.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SSSRegistrationDetails.refresh}" reRender="PanelGridMain" oncomplete="setMask()"/>
                            <a4j:commandButton id="btnExit" action="#{SSSRegistrationDetails.exitAction}" value="Exit" reRender="errmsg">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>