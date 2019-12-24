<%-- 
    Document   : duplicateCustIdInfo
    Created on : Aug 5, 2016, 3:59:14 PM
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
            <title>Duplicate Cust Id Info</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".date").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1"> 
                <h:panelGrid bgcolor="#fff" columns="1" id="a1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel value="Date : " styleClass="headerLabel"/>
                            <h:outputText value="#{DuplicateCustIdInfo.todayDate}" id="stxtDate" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel value="Duplicate Cust Id Info" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel value="User : " styleClass="headerLabel"/>
                            <h:outputText value="#{DuplicateCustIdInfo.userName}" id="stxtUser" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8" columns="1" id="af5" style="width:100%;height:30px;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtErrMsg" styleClass="error" value="#{DuplicateCustIdInfo.message}" style="color:red;padding-left:100px;"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2">
                        <%--h:outputText value="Branch Wise:" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{DuplicateCustIdInfo.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{DuplicateCustIdInfo.branchCodeList}" />
                        </h:selectOneListbox--%> 
                        <h:outputLabel value="Report Type : " styleClass="label"/>
                        <h:selectOneListbox id="repId" size="1" value="#{DuplicateCustIdInfo.repType}" styleClass="ddlist">
                            <f:selectItems value="#{DuplicateCustIdInfo.repTypeList}" />
                             <a4j:support action="#{DuplicateCustIdInfo.onReportAction}" event="onblur" reRender="optionId,stxtErrMsg" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Duplicate By : " styleClass="label"/>
                        <h:selectOneListbox id="optionId" size="1" value="#{DuplicateCustIdInfo.duplicateIdBy}" styleClass="ddlist" style="width: 200px" disabled="#{DuplicateCustIdInfo.disableOrderBy}">
                            <f:selectItems value="#{DuplicateCustIdInfo.duplicateIdByList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel value="Order By : " styleClass="label"/>
                        <h:selectOneListbox id="branchddl" size="1" value="#{DuplicateCustIdInfo.orderBy}" styleClass="ddlist">
                            <f:selectItems value="#{DuplicateCustIdInfo.orderByList}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columns="1" styleClass="footer" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton action="#{DuplicateCustIdInfo.btnHtmlAction}" id="btnHtml" style="width: 100px" value="Print Report" reRender="stxtErrMsg"/>
                            <h:commandButton id="btnDownload"  value="PDF Download" action="#{DuplicateCustIdInfo.btnPdfAction}"  styleClass="color:#044F93;text-decoration:none;"/>
                            <a4j:commandButton action="#{DuplicateCustIdInfo.btnExitAction}" id="btnExit" style="width: 100px" value="Exit" reRender="stxtErrMsg,a1"/>
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
