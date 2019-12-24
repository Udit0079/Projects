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
            <title>User History</title>
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
            <a4j:form id="userhistory">
                <h:panelGrid columns="1" id="mainPanel" width="100%" style="ridge #BED6F8">
                    <h:panelGrid id="headerPanel" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{userHistory.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="User History"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{userHistory.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row2" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{userHistory.errMsg}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columns="6" columnClasses="col3,col3,col3,col3" width="100%" styleClass="row1">
                        <h:outputText value="Branch" styleClass="label"/>
                        <h:selectOneListbox id="listbranch" value="#{userHistory.branch}" styleClass="ddlist" size="1"  style="width:80px">
                            <f:selectItems value="#{userHistory.branchList}" />
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel styleClass="label" value="From Date"/>
                        <h:inputText id="txtFrDt" styleClass="input issueDt" style="width:70px" value="#{userHistory.frDt}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel styleClass="label" value="To Date"/>
                        <h:inputText id="txtToDt" styleClass="input issueDt" style="width:70px" value="#{userHistory.toDt}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid  columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <a4j:region id="buttonRegion">
                            <h:panelGroup id="gridPanel" layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton value="View Report" action="#{userHistory.htmlReport}" reRender="errorMsg"/>
                                <a4j:commandButton value="Refresh" action="#{userHistory.refresh}" reRender="mainPanel" oncomplete="setMask();"/>
                                <a4j:commandButton value="Exit" action="#{userHistory.exit}" reRender="mainPanel" oncomplete="setMask();"/>
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
