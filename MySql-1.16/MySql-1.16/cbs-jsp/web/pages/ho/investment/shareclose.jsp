<%-- 
    Document   : shareclose
    Created on : Jul 25, 2012, 2:29:46 PM
    Author     : root
--%>

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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Share Close</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{shareClose.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Share Close"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{shareClose.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{shareClose.message}"/>
                    </h:panelGrid>

                    <h:panelGrid id="secTypeGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCtrlNo" styleClass="label" value="Control No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCtrlNo" style="width: 120px" styleClass="ddlist"  value="#{shareClose.ctrlNo}" size="1">
                            <f:selectItems value="#{shareClose.ctrlNoList}"/>
                            <a4j:support event="onblur" action="#{shareClose.onBlurCtrlNo}" reRender="message,txtShareDetails,txtShareCert,ddBank,txtPurDt,stxtFaceValue,btnProcess" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblShareDetails" styleClass="label" value="Security Detail"></h:outputLabel>
                        <h:inputText id="txtShareDetails" value="#{shareClose.securityDetail}" size="10" styleClass="input"/>
                        <h:outputLabel id="lblShareCert" styleClass="label" value="Share Certificate"></h:outputLabel>
                        <h:inputText id="txtShareCert" value="#{shareClose.shareCertificate}" size="10" styleClass="input" disabled="#{shareClose.enableCertificate}"/>
                    </h:panelGrid>

                    <h:panelGrid id="bankGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblBank" styleClass="label" value="Bank"></h:outputLabel>
                        <h:selectOneListbox id="ddBank" style="width: 120px" styleClass="ddlist"  value="#{shareClose.bank}" size="1">
                            <f:selectItems value="#{shareClose.bankList}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" style="width: 120px" styleClass="ddlist"  value="#{shareClose.branch}" size="1" disabled="#{shareClose.enableBranch}">
                            <f:selectItems value="#{shareClose.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPurDt" styleClass="label" value="Purchase Date"></h:outputLabel>
                        <h:inputText id="txtPurDt" value="#{shareClose.purDate}" size="10" styleClass="input chkDt">
                            <a4j:support action="#{shareClose.onBlurPurDate}" event="onblur" reRender="message" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>

                    <h:panelGrid id="faceValueGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFaceValue" styleClass="label" value="Face Value"></h:outputLabel>
                        <h:outputText id="stxtFaceValue" value="#{shareClose.faceValue}" styleClass="output"/>
                        <h:outputLabel id="lblCrHead" styleClass="label" value="Debited GL Head"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtDrHead" value="#{shareClose.drHead}" size="10" styleClass="input">
                                <a4j:support action="#{shareClose.onBlurDrHead}" event="onblur" reRender="message,stxtDrHeadName" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="stxtDrHeadName" value="#{shareClose.drHeadName}" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblBranchName" styleClass="label" value="Branch Name"></h:outputLabel>
                        <h:selectOneListbox id="ddBranchName" style="width: 120px" styleClass="ddlist"  value="#{shareClose.branchName}" size="1">
                            <f:selectItems value="#{shareClose.branchList}"/>
                            <a4j:support event="onblur" action="#{shareClose.onBlurBrName}" reRender="btnProcess" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Save" oncomplete="#{rich:component('processPanel')}.show();setMask();" disabled="#{shareClose.enableSaveButton}" reRender="maingrid,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{shareClose.resetForm}" reRender="maingrid,processPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{shareClose.exitBtnAction}" reRender="maingrid,processPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to save this entry ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{shareClose.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
