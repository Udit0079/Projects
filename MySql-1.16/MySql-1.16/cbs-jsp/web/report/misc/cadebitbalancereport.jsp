<%-- 
    Document   : cadebitbalancereport
    Created on : 15 Dec, 2011, 10:31:46 AM
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
            <title>CA Debit Balance Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calFromDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="CADebitBalanceReport">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{CADebitBalanceReport.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="CA Debit Balance Report"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{CADebitBalanceReport.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{CADebitBalanceReport.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2"> 
                        <h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{CADebitBalanceReport.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{CADebitBalanceReport.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblacType" styleClass="label"  value="A/c Type"/>
                        <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 80px" value="#{CADebitBalanceReport.acType}" >
                            <f:selectItems value="#{CADebitBalanceReport.acTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFromDate" value="From Date" styleClass="label"/>
                        <h:inputText id="calFromDate" size="10" styleClass="input calFromDate" value="#{CADebitBalanceReport.calFromDate}" >
                            <f:convertDateTime pattern="dd/MM/yyyy" />
                        </h:inputText> 

                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" value="Print Report" action="#{CADebitBalanceReport.printAction}" reRender="errmsg" oncomplete="setMask()"/>
                             <h:commandButton id="btnPDF"  value="PDF Download" actionListener="#{CADebitBalanceReport.viewPdfReport}"  styleClass="color: #044F93;text-decoration: none;" /> 
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CADebitBalanceReport.refreshAction}" reRender="errmsg,txtAccountNo" oncomplete="setMask()"/>
                            <a4j:commandButton action="#{CADebitBalanceReport.exitAction}" value="Exit" reRender="errmsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

