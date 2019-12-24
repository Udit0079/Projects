<%-- 
    Document   : comperativepl
    Created on : 23 Jan, 2015, 7:46:15 PM
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
            <title>Comperative Profit & Loss</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
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
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{comperativePl.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Comperative Profit & Loss" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{comperativePl.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid> 
                    <h:panelGrid id="gridPanel2" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputLabel id="errorMsg" styleClass="error" style="color:red;" value="#{comperativePl.errMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel value="Branch" styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" size="1" value="#{comperativePl.branch}" styleClass="ddlist">
                            <f:selectItems value="#{comperativePl.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="Date1"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="txtDt1" styleClass="input issueDt" style="width:70px" value="#{comperativePl.dateOne}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="Date2"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:inputText id="txtDt2" styleClass="input issueDt" style="width:70px" value="#{comperativePl.dateTwo}">
                            <a4j:support event="onblur" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputLabel value="Option" styleClass="label"><font class="required" style="color:red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddOptionList" styleClass="ddlist" value="#{comperativePl.option}" size="1">
                            <f:selectItems value="#{comperativePl.optionList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <a4j:region id="buttonRegion">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnHtml" value="Print Report" action="#{comperativePl.btnHtmlAction}" 
                                                   reRender="errorMsg" oncomplete="setMask();"/>
                                <h:commandButton id="btnDownload" value="PDF Download" action="#{comperativePl.btnPdfAction}" 
                                                 styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton value="Refresh" action="#{comperativePl.refresh}" reRender="mainPanel" 
                                                   oncomplete="setMask();"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{comperativePl.btnExitAction}"  
                                                   reRender="mainPanel" oncomplete="setMask();"/>
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
            </a4j:form>
        </body>
    </html>
</f:view>
