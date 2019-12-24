<%-- 
    Document   : rdActive
    Created on : Feb 23, 2012, 4:24:30 PM
    Author     : ANKIT VERMA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich" %>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Rd Active Report</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }

            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="mainPanelGrid" columns="1" width="100%" style="border:1px ridge #BED6F8"  bgcolor="#fff" >
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdActive.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="RD Active Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RdActive.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3"  style="text-align:center;width:100%" styleClass="row1">
                        <h:outputText id="errorMsg" styleClass="error" value="#{RdActive.errorMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel11" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" width="100%">
                        <h:outputText />
                        <h:outputLabel value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{RdActive.branch}" styleClass="ddlist">
                            <f:selectItems value="#{RdActive.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText />
                        <h:outputText />
                        <h:outputText />
                    </h:panelGrid>     
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputText />
                        <h:outputText value="A/c. Type :" styleClass="label" />
                        <h:selectOneListbox id="ddlacType" size="1" styleClass="ddlist" value="#{RdActive.acType}"  >
                            <f:selectItems value="#{RdActive.acTypeList}"/>
                        </h:selectOneListbox>
                        <h:outputText value="Date :" styleClass="label" />
                        <h:panelGroup styleClass="label">
                            <h:inputText id="txtDate" styleClass="input calInstDate" style="width:70px;"  value="#{RdActive.calDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <font color="purple">DD/MM/YYYY</font>
                            </h:panelGroup>
                            <h:outputText />
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gridPanel9" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="a100" layout="block">
                                <a4j:commandButton action="#{RdActive.btnHtmlAction}"
                                                   id="btnHtml" value="Print Report" reRender="errorMsg,txtDate" oncomplete="setMask()"/>
                                <h:commandButton id="btnDownload"  value="PDF Download" action="#{RdActive.pdfDownLoad}"  styleClass="color:#044F93;text-decoration:none;"/>
                                <a4j:commandButton action="#{RdActive.refreshForm}"
                                                   id="btnRefresh" value="Refresh" reRender="errorMsg,txtDate" oncomplete="setMask()"/>
                                <a4j:commandButton action="#{RdActive.btnExitAction}" 
                                                   id="btnExit" value="Exit" reRender="mainPanelGrid" oncomplete="setMask()"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>               

        </body>
    </html>
</f:view>
