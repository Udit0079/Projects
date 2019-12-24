<%-- 
    Document   : sharepaymentandcertificatereport
    Created on : 26 Mar, 2012, 11:09:49 AM
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
            <title>Share Payment Report</title>
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
            <a4j:form id="SharePaymentAndShareCertificateReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{SharePaymentAndShareCertificateReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Share Payment Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{SharePaymentAndShareCertificateReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{SharePaymentAndShareCertificateReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1"  styleClass="row2"> 
                        <h:outputLabel id="lblreporttype" styleClass="label"  value="Report Type"/>
                        <h:selectOneListbox id="ddreporttype" styleClass="ddlist" size="1" style="width: 134px" value="#{SharePaymentAndShareCertificateReport.reportType}">
                            <f:selectItems value="#{SharePaymentAndShareCertificateReport.typeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel2"  styleClass="row1"> 
                        <h:outputLabel id="lblclosingdate" styleClass="label"  value="Closing Date"/>
                        <h:selectOneListbox id="ddclosingdate" styleClass="ddlist" size="1" style="width: 134px" value="#{SharePaymentAndShareCertificateReport.closingDate}">
                            <f:selectItems value="#{SharePaymentAndShareCertificateReport.modeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calfromDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{SharePaymentAndShareCertificateReport.calFromDate}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                        <h:outputLabel id="lbltoDate"  value="To Date"  styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calInstDate" style="setMask();" value="#{SharePaymentAndShareCertificateReport.caltoDate}" disabled="#{SharePaymentAndShareCertificateReport.toDateDisable}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{SharePaymentAndShareCertificateReport.printAction}" oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                               #{rich:element('calToDate')}.style=setMask();" reRender="errmsg,calfromDate,calToDate"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{SharePaymentAndShareCertificateReport.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SharePaymentAndShareCertificateReport.refreshAction}"  oncomplete="#{rich:element('calfromDate')}.style=setMask();
                                               #{rich:element('calToDate')}.style=setMask();" reRender="PanelGridMain,calfromDate,calToDate"/>
                            <a4j:commandButton action="#{SharePaymentAndShareCertificateReport.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>


