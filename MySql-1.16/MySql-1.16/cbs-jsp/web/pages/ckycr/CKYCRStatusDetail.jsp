<%-- 
    Document   : CKYCRStatusDetail
    Created on : 28 Jan, 2017, 11:22:44 AM
    Author     : root
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CKYCR Status Updation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".dateOfBirth").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form  id="ckycrStatusDetails">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CKYCRStatusDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="CKYCR Status Updation"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CKYCRStatusDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{CKYCRStatusDetail.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="successMsg" style="height:1px;text-align:center" styleClass="msg" width="100%">
                        <h:outputText id="succMsg" value="#{CKYCRStatusDetail.successMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col2,col2,col2" columns="4" id="gridPanel1" width="100%" styleClass="row2">
                        <h:outputLabel value="Mode" id="modeLabel" styleClass="label"/>
                        <h:selectOneListbox id="modeId" value="#{CKYCRStatusDetail.fuction}" styleClass="ddlist" size="1"  >
                            <f:selectItems value="#{CKYCRStatusDetail.fuctionList}" />
                            <a4j:support  action="#{CKYCRStatusDetail.onChangeFunction}" event="onchange"  reRender="errmsg,successMsg,custId,gridPanelButton"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Customer Id" id="custIdLabel" styleClass="label"/>
                        <h:inputText id="custId" styleClass="input" style="width:100px" disabled="#{CKYCRStatusDetail.customerIdDisable}" value="#{CKYCRStatusDetail.customerId}" maxlength="12" >
                            <a4j:support  action="#{CKYCRStatusDetail.onblurCustId}" event="onblur"  reRender="errmsg,successMsg,gridPanelButton"/>
                        </h:inputText> 
                    </h:panelGrid>
                    <h:panelGrid columns="3" id="gridPanelButton" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnUpdate" value="UPDATE" action="#{CKYCRStatusDetail.updateReloadAction}" style="display:#{CKYCRStatusDetail.updateBtnDispaly};"/>
                            <h:commandButton id="btnReload" value="RESEND" action="#{CKYCRStatusDetail.updateReloadAction}" style="display:#{CKYCRStatusDetail.resendBtnDispaly};"/>
                            <a4j:commandButton action="#{CKYCRStatusDetail.btnHtmlAction}" id="btnHtml" value="Print Report" reRender="errmsg,successMsg" oncomplete="setMask();" style="display:#{CKYCRStatusDetail.reportBtnDispaly};"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{CKYCRStatusDetail.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;" style="display:#{CKYCRStatusDetail.reportBtnDispaly};"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CKYCRStatusDetail.refreshForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CKYCRStatusDetail.exitForm}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form >   
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

