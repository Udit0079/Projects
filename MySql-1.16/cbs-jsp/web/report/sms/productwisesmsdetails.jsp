<%-- 
    Document   : productwisesmsdetails
    Created on : 1 Nov, 2017, 3:52:05 PM
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
            <title>Product Wise SMS Details</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{productWiseSmsDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value=" Product Wise SMS Details"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "></h:outputLabel>
                            <h:outputText id="stxtUser" styleClass="output" value="#{productWiseSmsDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row2" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{productWiseSmsDetails.message}"></h:outputLabel>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="brch" styleClass="label" value="Branch" ></h:outputLabel>
                        <h:selectOneListbox id="ddbrchype" size="1" styleClass="ddlist" value="#{productWiseSmsDetails.branch}" style="width:100px;">
                            <f:selectItems value="#{productWiseSmsDetails.branchTypeList}"/>
                        </h:selectOneListbox>  
                        <h:outputLabel id="accnature" styleClass="label" value="A/c nature" ></h:outputLabel>
                        <h:selectOneListbox id="ddacnaturetype" size="1" styleClass="ddlist" value="#{productWiseSmsDetails.acNature}" style="width:100px;">
                            <f:selectItems value="#{productWiseSmsDetails.acNatureList}"/>
                            <a4j:support  action="#{productWiseSmsDetails.getAccountDetails}"  event="onchange"
                                          reRender="ddaccounttype" oncomplete="setMask();"/>
                        </h:selectOneListbox> 
                        <h:outputLabel id="actype" styleClass="label" value="Account Type" ></h:outputLabel>
                        <h:selectOneListbox id="ddaccounttype" size="1" styleClass="ddlist" value="#{productWiseSmsDetails.accountType}" style="width:100px;">
                            <f:selectItems value="#{productWiseSmsDetails.accountTypeList}"/>
                        </h:selectOneListbox> 
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel13" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  width="100%" styleClass="row1">
                        <h:outputLabel id="lblfrDt" styleClass="label" value="FromDate"></h:outputLabel>
                        <h:inputText id="frDate" size="10" styleClass="input issueDt" value="#{productWiseSmsDetails.fromDate}">
                            <a4j:support event="onblur" oncomplete="setMask();"focus="btnHtml"/>
                        </h:inputText>
                        <h:outputLabel id="lblToDt" styleClass="label" value="ToDate"></h:outputLabel>
                        <h:inputText id="ToDate" size="10" styleClass="input issueDt" value="#{productWiseSmsDetails.toDate}">
                            <a4j:support event="onblur" oncomplete="setMask();"focus="btnHtml"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton action="#{productWiseSmsDetails.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="lblMsg"></a4j:commandButton>
                            <a4j:commandButton action="#{productWiseSmsDetails.btnPdfDownload}" id="btnPdf" value="Pdf Download" reRender="lblMsg" ></a4j:commandButton>
                            <a4j:commandButton action="#{productWiseSmsDetails.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="mainPanel"></a4j:commandButton>
                            <a4j:commandButton action="#{productWiseSmsDetails.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"></a4j:commandButton>
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



