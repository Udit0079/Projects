<%-- 
    Document   : ClearingTextFilePrint
    Created on : Apr 1, 2011, 10:46:46 AM
    Author     : ROHIT KRISHNA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Clearing Text File Print</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript"> 
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="ClearingTextFilePrint"/>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{ClearingTextFilePrint.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Clearing Text File Print"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{ClearingTextFilePrint.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ClearingTextFilePrint.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ClearingTextFilePrint.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="3" id="a4" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblOption" styleClass="label" value="Report Option :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddOption" tabindex="1" styleClass="ddlist" style="width: 121px" value="#{ClearingTextFilePrint.reportOption}" size="1" >
                            <f:selectItems value="#{ClearingTextFilePrint.reportOptionList}" />
                                <a4j:support event="onblur" oncomplete="setMask()" focus="calDt"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date :" style="padding-left:350px;"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="calDt" styleClass="input calInstDate" value="#{ClearingTextFilePrint.calDate}" style="width:75px;">
                            <a4j:support event="onblur" oncomplete="setMask()" focus="btnSaveToFile"/>                         
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="a6" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:repeat value="#{ClearingTextFilePrint.txtFileNameList}" var="fileName">
                                <a4j:commandLink id="saveLink" value="#{fileName}"  style="color:blue;" styleClass="headerLabel"
                                                 action="#{ClearingTextFilePrint.downloadFile}" rendered="#{ClearingTextFilePrint.saveLink}">
                                    <f:setPropertyActionListener target="#{ClearingTextFilePrint.fileName}" value="#{fileName}"/>
                                </a4j:commandLink>
                                &nbsp;&nbsp;
                            </a4j:repeat>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="btnRegion">
                                <a4j:commandButton id="btnSaveToFile" tabindex="2" value="Save To File" action="#{ClearingTextFilePrint.saveToFile}" reRender="message,errorMessage,a5,a6,lpg,saveLink" oncomplete="setMask()" focus="ddOption"/>
                                <a4j:commandButton id="btnRefresh" tabindex="3" value="Refresh" action="#{ClearingTextFilePrint.resetForm}" reRender="message,errorMessage,a5,a6,lpg,saveLink,ddOption" focus="ddOption" oncomplete="setMask()"/>
                                <a4j:commandButton id="btnExit" tabindex="4" value="Exit" action="#{ClearingTextFilePrint.exitBtnAction}" reRender="message,errorMessage,lpg,a6,saveLink"/>
                           </a4j:region>
                        </h:panelGroup>
                        
                    </h:panelGrid>
                </h:panelGrid>
                    <a4j:status  onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="250" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
