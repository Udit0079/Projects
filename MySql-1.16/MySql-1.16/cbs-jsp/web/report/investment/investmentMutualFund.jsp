<%-- 
    Document   : investmentMutualFund
    Created on : 17 Jun, 2017, 4:02:25 PM
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
            <title>Investment Mutual Fund</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{investmentMutualFund.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Investment Mutual Fund"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{investmentMutualFund.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="message" styleClass="error" style="color:red;" value="#{investmentMutualFund.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="repTypeGrid" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRepType" styleClass="label" value="Report Type"/>
                        <h:selectOneListbox id="ddRepType" size="1" styleClass="ddlist" value="#{investmentMutualFund.repType}">
                            <f:selectItems value="#{investmentMutualFund.repTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRepType1" styleClass="label" value="Bank Type"/>
                        <h:selectOneListbox id="ddRepType1" size="1" styleClass="ddlist" value="#{investmentMutualFund.bankType}">
                            <f:selectItems value="#{investmentMutualFund.bankTypeList}"/>
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid  id="repTypeGrid1" columns="4" columnClasses="col13,col13,col13,col13" styleClass="row2" width="100%">
                        <h:outputLabel value="From Date" styleClass="label"/>
                        <h:inputText id="frmTxtDt" size="10" styleClass="input issueDt" value="#{investmentMutualFund.frmDt}">
                            <a4j:support event="onblur" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel value="To Date" styleClass="label"/>
                        <h:inputText id="toTxtDt" size="10" styleClass="input issueDt" value="#{investmentMutualFund.toDt}">
                            <a4j:support event="onblur" action="#{investmentMutualFund.onBlurAsOnDt}" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{investmentMutualFund.processAction}" id="btnProcess" value="Print Report" reRender="mainPanel,message"/>
                            <a4j:commandButton action="#{investmentMutualFund.pdfAction}" id="btnPdf" value="Print Pdf" reRender="mainPanel,message"/>
                            <a4j:commandButton action="#{investmentMutualFund.btnRefreshAction}" id="btnReset" value="Refresh" reRender="mainPanel,message,"/>
                            <a4j:commandButton action="#{investmentMutualFund. btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel,message"/>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>