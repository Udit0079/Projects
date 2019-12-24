<%-- 
    Document   : custNoWiseTdReceiptDetail
    Created on : 1 Mar, 2018, 3:34:45 PM
    Author     : root
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
            <title>Details of Deposit Receipt</title>
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
        </head>
        <body>
            <a4j:form id="accountStatement">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{CustNoWiseTdReceiptDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Details of Deposit Receipt"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUsername" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="otUsername" styleClass="output" value="#{CustNoWiseTdReceiptDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel" style="text-align:center" styleClass="row1">
                        <h:outputText id="errmsg" value="#{CustNoWiseTdReceiptDetail.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col3,col3,col3,col3,col3" columns="6" style="text-align:left;" styleClass="row2">
                        <h:outputLabel value="Customer Id :" styleClass="label"/>
                        <h:panelGroup id="groupPanlcId" layout="block">
                            <h:inputText id="txtAccountNo" styleClass="input" style="width:70px" value="#{CustNoWiseTdReceiptDetail.custId}" >
                                <a4j:support event="onblur" actionListener="#{CustNoWiseTdReceiptDetail.onBlurAccountNumber}" reRender="errmsg,newAcNo" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="newAcNo" value="#{CustNoWiseTdReceiptDetail.newCustId}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel value="To Date :" styleClass="label"/>
                        <h:inputText id="calToDate" size="10" styleClass="input calToDate" value="#{CustNoWiseTdReceiptDetail.toDate}"/>
                    </h:panelGrid>             
                    <h:panelGrid id="FooterPanel" bgcolor="#fff" style="text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnPrint" action="#{CustNoWiseTdReceiptDetail.printAction}" type="submit" value="Print Report" reRender="errmsg"/>
                            <h:commandButton id="btnDownload" value="PDF Download" action="#{CustNoWiseTdReceiptDetail.downloadPdf}" styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton id="btnExit" action="#{CustNoWiseTdReceiptDetail.exitAction}" value="Exit"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>