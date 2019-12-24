<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>A/c Wise SMS Details</title>
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
            <h:form id="acwisesmsdetails">
                <h:panelGrid columns="1" id="mainPanel" width="100%" style="ridge #BED6F8">
                    <h:panelGrid id="headerPanel" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{acWiseSmsDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="A/c Wise Sms Details"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{acWiseSmsDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{acWiseSmsDetails.errMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputLabel value="Report Type" styleClass="label"/>
                        <h:selectOneListbox id="ddReportType" value="#{acWiseSmsDetails.reportType}" styleClass="ddlist" size="1" style="width:140px">
                            <f:selectItems value="#{acWiseSmsDetails.reportTypeList}"/>
                            <a4j:support event="onchange" action="#{acWiseSmsDetails.reportTypeAction}" reRender="errorMsg,panel2,acId,txtAcno" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="acId" value="A/c Number " styleClass="label" style="display:#{acWiseSmsDetails.willShow1};"/>
                        <h:inputText id="txtAcno" value="#{acWiseSmsDetails.acno}" size="#{acWiseSmsDetails.acNoMaxLen}" styleClass="input" maxlength="#{acWiseSmsDetails.acNoMaxLen}" style="display:#{acWiseSmsDetails.willShow1};">
                            <a4j:support event="onblur" action="#{acWiseSmsDetails.accountAction}" reRender="errorMsg" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columns="4" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row2" style="display:#{acWiseSmsDetails.willShow};">
                        <h:outputLabel styleClass="label" value="From Date"/>
                        <h:inputText id="txtFrDt" styleClass="input issueDt" style="width:70px" value="#{acWiseSmsDetails.frDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="To Date"/>
                        <h:inputText id="txtToDt" styleClass="input issueDt" style="width:70px" value="#{acWiseSmsDetails.toDt}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  id="buttonPanel" columns="1" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="buttonRegion">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnPrint" value="Print Report" action="#{acWiseSmsDetails.btnHtmlAction}" reRender="errorMsg"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{acWiseSmsDetails.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton value="Refresh" action="#{acWiseSmsDetails.refresh}" reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton value="Exit" action="#{acWiseSmsDetails.exit}" reRender="mainPanel" oncomplete="setMask();"/>
                            </h:panelGroup>
                        </a4j:region>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="buttonRegion"/>
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" 
                                     style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>
                </h:panelGrid>
            </h:form>
        </body>
    </html>
</f:view>
