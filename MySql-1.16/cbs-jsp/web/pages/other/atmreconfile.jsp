<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>ATM Recon File Generation</title>
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
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ATMFileController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Recon File Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ATMFileController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{ATMFileController.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel value="Recon File Type" id="classificationLabel" styleClass="label"/>
                        <h:selectOneListbox id="reconFileType" value="#{ATMFileController.reconFileType}" styleClass="ddlist" size="1"  style="width:150px" >
                            <f:selectItems value="#{ATMFileController.reconFileTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblGenDate" styleClass="label" value="File Generation Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{ATMFileController.fileGenerationDt}" size="10"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="issLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%"  rendered="#{ATMFileController.issuerLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="issuerSaveLink" value="#{ATMFileController.issuerFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{ATMFileController.downloadIssuer}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="onLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row2" width="100%" rendered="#{ATMFileController.onUsLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="onUsSaveLink" value="#{ATMFileController.onUsFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{ATMFileController.downloadOnUs}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="acqLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%" rendered="#{ATMFileController.acquireLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="acqsaveLink" value="#{ATMFileController.acquireFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{ATMFileController.downloadAcquire}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Create File" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{ATMFileController.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ATMFileController.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to generate the file ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{ATMFileController.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('txtGenDate')}.focus();setMask();" 
                                                           reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();" oncomplete="setMask();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
