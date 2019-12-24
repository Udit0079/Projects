<%-- 
    Document   : freshRenewalEnhasmentOdLimit
    Created on : 17 Oct, 2017, 6:29:21 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Fresh/Renewed/Enhanced Limit/Expiry</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{FreshRenewalEnhasmentOdLimit.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Fresh/Renewed/Enhanced Limit/Expiry"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FreshRenewalEnhasmentOdLimit.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{FreshRenewalEnhasmentOdLimit.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="pId1" style="height:30px;text-align:left;" styleClass="row1" width="100%">
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{FreshRenewalEnhasmentOdLimit.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{FreshRenewalEnhasmentOdLimit.branchCodeList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Report Option" styleClass="label"/>
                        <h:selectOneListbox id="option" size="1" value="#{FreshRenewalEnhasmentOdLimit.reportOption}" styleClass="ddlist" >
                            <f:selectItems value="#{FreshRenewalEnhasmentOdLimit.reportOptionList}" />
                            <a4j:support actionListener="#{FreshRenewalEnhasmentOdLimit.optionAction}" event="onblur" reRender="pId3,lblMsg" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="pId3" style="height:30px;text-align:left;" styleClass="row2" width="100%">
                        <h:outputText value="Account Nature" styleClass="label"/>
                        <h:selectOneListbox id="actNatureId" size="1" value="#{FreshRenewalEnhasmentOdLimit.acctNature}" style = "width:70px"styleClass="ddlist" disabled="#{FreshRenewalEnhasmentOdLimit.disable}">
                            <f:selectItems value="#{FreshRenewalEnhasmentOdLimit.acctNatureList}" />
                            <a4j:support actionListener="#{FreshRenewalEnhasmentOdLimit.getAcTypeForNature}" event="onblur" reRender="ddAcType,lblMsg" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputText value="Account Type" styleClass="label"/>
                        <h:selectOneListbox id="ddAcType" size="1" value="#{FreshRenewalEnhasmentOdLimit.actType}" style="width :70px"styleClass="ddlist" disabled="#{FreshRenewalEnhasmentOdLimit.disable}">
                            <f:selectItems value="#{FreshRenewalEnhasmentOdLimit.actTypeList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="pId2" style="height:30px;text-align:left;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFrDt" styleClass="label" value="From Date"/>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{FreshRenewalEnhasmentOdLimit.fromDate}">
                            <a4j:support event="onblur" oncomplete="setMask();"focus="toDate"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="To Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{FreshRenewalEnhasmentOdLimit.toDate}">
                            <a4j:support event="onblur" oncomplete="setMask();"focus="btnHtml"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <h:commandButton id="btn"  value="Print Letter" action="#{FreshRenewalEnhasmentOdLimit.printLetter}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{FreshRenewalEnhasmentOdLimit.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="mainPanel"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{FreshRenewalEnhasmentOdLimit.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{FreshRenewalEnhasmentOdLimit.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"oncomplete="setMask();"/>
                            <a4j:commandButton action="#{FreshRenewalEnhasmentOdLimit.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
