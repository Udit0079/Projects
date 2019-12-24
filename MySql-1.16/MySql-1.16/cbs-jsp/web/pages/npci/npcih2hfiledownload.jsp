<%-- 
    Document   : npcih2hfiledownload
    Created on : 14 Jun, 2018, 2:46:46 PM
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
            <title>NPCI H2H File Download</title>
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
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1"  style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3"  style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{NpciH2hFileDownload.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="NPCI H2H File Download"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{NpciH2hFileDownload.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{NpciH2hFileDownload.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="datePanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblToindDt" styleClass="label" value="Date"/>
                        <a4j:region id="dataRegion">
                            <h:inputText id="textToDt" size="10" styleClass="input issueDt"  value="#{NpciH2hFileDownload.toInddt}">
                                <a4j:support event="onblur" action="#{NpciH2hFileDownload.gridDetailAction}" reRender="stxtMessage,tablePanel,tblRegion,taskList,
                                             tablePanel1,intblRegion,intaskList,responseTablePanel,responseTblRegion,responseTaskList" oncomplete="setMask();"/>
                            </h:inputText>
                        </a4j:region>
                        <h:outputLabel></h:outputLabel>
                        <h:outputText></h:outputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{NpciH2hFileDownload.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{NpciH2hFileDownload.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel1" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="intblRegion">
                            <rich:dataTable value="#{NpciH2hFileDownload.gridIncomDtl}" var="inItem"
                                            rowClasses="gridrow1,gridrow2" id="intaskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Inward File" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{inItem.srno}" /></rich:column>
                                <rich:column><h:outputText value="#{inItem.fileName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{NpciH2hFileDownload.downloadFile('IW')}" reRender="stxtMessage,mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{inItem}" target="#{NpciH2hFileDownload.currentItem1}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="intaskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{NpciH2hFileDownload.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Outward File" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.srno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink"  action="#{NpciH2hFileDownload.downloadFile('OW')}" reRender="stxtMessage,mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{NpciH2hFileDownload.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>

                    <h:panelGrid id="responseTablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region id="responseTblRegion">
                            <rich:dataTable value="#{NpciH2hFileDownload.gridDetailres}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="responseTaskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="Response File" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.srno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink2"  action="#{NpciH2hFileDownload.downloadFile('RES')}" reRender="stxtMessage,mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{NpciH2hFileDownload.currentItem2}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="responseTaskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait1')}.show()" onstop="#{rich:component('wait1')}.hide()" for="dataRegion"/>
            <rich:modalPanel id="wait1" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
