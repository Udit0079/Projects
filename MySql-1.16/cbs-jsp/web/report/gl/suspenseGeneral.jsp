<%-- 
    Document   : suspenseGeneral
    Created on : Dec 15, 2015, 3:31:05 PM
    Author     : Admin
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
            <title>Suspense General</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calDate").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{SuspenseGeneral.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Suspense General" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{SuspenseGeneral.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid columnClasses="col8" columns="1" id="a9" styleClass="row1" style="height:30px;text-align:center;" width="100%">
                        <h:outputLabel id="lblMsg" styleClass="error" style="color:red;" value="#{SuspenseGeneral.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="a5" styleClass="row2" style="height:30px;" width="100%">
                        <h:outputText id="lblAcno" value="Account Number" styleClass="label"/>
                        <h:panelGroup layout="block">
                            <h:selectOneListbox id="ddrAcno" value="#{SuspenseGeneral.acNo}" styleClass="ddlist"  style="width:110px" size="1">
                                <f:selectItems id="selectAcno" value="#{SuspenseGeneral.acNoList}" />
                                <a4j:support event="onblur" action="#{SuspenseGeneral.onBlurAcnoName}" reRender="stxtNewAcNo,lblMsg"></a4j:support>
                            </h:selectOneListbox> 
                            <h:outputLabel id="stxtNewAcNo" styleClass="label" value="#{SuspenseGeneral.acName}"style="color:green"></h:outputLabel>
                        </h:panelGroup>
                        <h:outputLabel id="label4" value="Date : " styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="calDate" styleClass="input calDate" value="#{SuspenseGeneral.calDate}" size="10">
                            <f:convertDateTime pattern="dd/MM/yyyy"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">  
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <%--a4j:commandButton action="#{SuspenseGeneral.btnHtmlAction}" id="btnHtml"
                                               value="Print Report"/--%>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{SuspenseGeneral.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                              <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SuspenseGeneral.btnRefreshAction}" reRender="a1"/>
                            <a4j:commandButton action="#{SuspenseGeneral.btnExitAction}" id="btnExit" value="Exit" reRender="lblMsg,calDate"/>
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
