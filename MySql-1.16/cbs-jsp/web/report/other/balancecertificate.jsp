<%-- 
    Document   : BalanceCertificate
    Created on : 14 Dec, 2011, 1:14:19 PM
    Author     : Zeeshan Waris
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
            <title>Balance Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="BalanceCertificate">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{BalanceCertificate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Balance Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{BalanceCertificate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{BalanceCertificate.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3" columns="5" id="gridPanel4"  styleClass="row2"> 
                        <h:outputLabel/>
                        <h:outputLabel id="lblAccountNo" value="Account No" styleClass="label"/>
                        <h:panelGroup  layout="block">
                            <h:inputText id="txtAccountNo"  style="width: 90px" value="#{BalanceCertificate.accountNo}" styleClass="input" maxlength="#{BalanceCertificate.acNoMaxLen}"  onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support actionListener="#{BalanceCertificate.getNewAccountNo}"  event="onblur" oncomplete="setMask();" focus="calIntDate" reRender="stxtStAccountNo,groupPanelapptDt,errmsg"/> 
                            </h:inputText>
                            <h:outputText id="stxtStAccountNo" styleClass="output" value="#{BalanceCertificate.newAcno}" />
                        </h:panelGroup>
                        <h:outputLabel  value="Till Date" styleClass="label"/>
                        <h:panelGroup id="groupPanelapptDt" layout="block">
                            <h:inputText id="calIntDate" styleClass="input calInstDate"   style="width:70px;" maxlength="10" value="#{BalanceCertificate.calDate}" >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblapptDT" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{BalanceCertificate.printAction}" oncomplete="setMask();" reRender="groupPanelapptDt,errmsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{BalanceCertificate.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BalanceCertificate.refreshAction}" oncomplete="setMask();" reRender="groupPanelapptDt,errmsg,txtAccountNo,stxtStAccountNo"/>
                            <a4j:commandButton action="#{BalanceCertificate.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
