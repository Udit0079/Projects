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
            <title>NPCI Outward Return Report</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{owNpciReturnBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="NPCI Outward Return Report"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{owNpciReturnBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid1" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{owNpciReturnBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid2" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row1" width="100%">
                        <h:outputLabel id="lblBranch" styleClass="label" value="Branch"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddBranch" styleClass="ddlist" size="1" style="width:140px" value="#{owNpciReturnBean.branch}">
                            <f:selectItems value="#{owNpciReturnBean.branchList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblClgMode" styleClass="label" value="Clearing Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddClgMode" styleClass="ddlist" size="1" style="width:140px" value="#{owNpciReturnBean.clgMode}">
                            <f:selectItems value="#{owNpciReturnBean.clgModeList}"/>
                            <a4j:support event="onblur" actionListener="#{owNpciReturnBean.populateRegisterDt}" reRender="stxtMessage,ddRegisterDt"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" columns="4" columnClasses="col2,col7,col2,col7" styleClass="row2" width="100%">
                        <h:outputLabel id="lblRegisterDt" styleClass="label" value="Register Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddRegisterDt" styleClass="ddlist" size="1" style="width:140px" value="#{owNpciReturnBean.registerDt}">
                            <f:selectItems value="#{owNpciReturnBean.registerDtList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width:140px" value="#{owNpciReturnBean.status}">
                            <f:selectItems value="#{owNpciReturnBean.statusList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="1" style="text-align: center; width:100%" styleClass="footer">
                        <h:panelGroup id="footerGroup" layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnHtml" value="Print Report" action="#{owNpciReturnBean.btnHtmlAction}" reRender="stxtMessage"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{owNpciReturnBean.btRefreshAction}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{owNpciReturnBean.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup> 
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
