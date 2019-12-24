<%-- 
    Document   : bulkdemandgeneration
    Created on : 24 Apr, 2014, 2:31:57 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <title>Bulk Demand Generation</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{bulkDemandGeneration.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Bulk Demand Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{bulkDemandGeneration.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="messagePanel" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{bulkDemandGeneration.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="placeOfWorkingGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{bulkDemandGeneration.branchCode}" styleClass="ddlist">
                            <f:selectItems value="#{bulkDemandGeneration.branchCodeList}" />
                        </h:selectOneListbox> 
                        <h:outputLabel id="lblPlaceOfWorking" styleClass="label" value="Place Of Working"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPlaceOfWorking" styleClass="ddlist" size="1" value="#{bulkDemandGeneration.placeOfWorking}">
                            <f:selectItems value="#{bulkDemandGeneration.placeOfWorkingList}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMonth" styleClass="label"  value="Month"><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup>
                            <h:selectOneListbox id="ddMonth" styleClass="ddlist" size="1" style="width: 70px" value="#{bulkDemandGeneration.month}">
                                <f:selectItems value="#{bulkDemandGeneration.monthList}"/>
                            </h:selectOneListbox>
                            <h:inputText id="txtYear" styleClass="input" value="#{bulkDemandGeneration.year}" maxlength="4" size="5"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="rangeGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{bulkDemandGeneration.displayRange}" styleClass="row2" width="100%">
                        <h:outputText id="lblpersonalNo" value="Personal No. Range:" styleClass="label"/>
                        <h:selectOneListbox id="ddpersonalNo" value="#{bulkDemandGeneration.personalNoRange}" styleClass="ddlist"  style="width:110px" size="1">
                            <f:selectItems id="selectpersonalNoList" value="#{bulkDemandGeneration.personalNoRangeList}" />
                        </h:selectOneListbox>
                       <h:outputText id="lblfile" value="File Type:" styleClass="label"/>
                       <h:selectOneListbox id="ddfile" value="#{bulkDemandGeneration.fileType}" styleClass="ddlist"  style="width:110px" size="1">
                           <f:selectItems id="selectfileList" value="#{bulkDemandGeneration.fileTypeList}" />
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="fileLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row1" rendered="#{bulkDemandGeneration.excelLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="excelSaveLink" value="#{bulkDemandGeneration.demandFileName}" style="color:blue;" styleClass="headerLabel" action="#{bulkDemandGeneration.downloadExcel}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Generate File" oncomplete="#{rich:component('processPanel')}.show();" reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{bulkDemandGeneration.btnRefreshAction}" oncomplete="setMask()" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{bulkDemandGeneration.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionMpanal"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" 
                             style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:region id="btnActionMpanal">
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
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{bulkDemandGeneration.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('ddPlaceOfWorking')}.focus();" 
                                                           reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
        </body>
    </html>
</f:view>
