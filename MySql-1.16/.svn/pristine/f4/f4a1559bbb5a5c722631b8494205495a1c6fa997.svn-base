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
            <title>NPCI CTS Return File</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{npciCTSReturnBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="NPCI CTS Return File"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{npciCTSReturnBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{npciCTSReturnBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columnClasses="col17,col1,col17,col1,col17,col1,col17,col1" columns="8" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblFileType" value="File Type" styleClass="label"/>
                        <h:selectOneListbox id="ddFileType" value="#{npciCTSReturnBean.fileType}" styleClass="ddlist" size="1"  style="width:150px" >
                            <f:selectItems value="#{npciCTSReturnBean.fileTypeList}" />
                            <a4j:support event="onblur" oncomplete="setMask();" focus="txtGenDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblGenDate" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{npciCTSReturnBean.fileGenerationDt}" size="10">
                            <a4j:support event="onblur" focus="ddClgType"/>
                        </h:inputText>
                        <h:outputLabel id="lblClgType" value="Clearing Type" styleClass="label"/>
                        <h:selectOneListbox id="ddClgType" value="#{npciCTSReturnBean.clearingType}" styleClass="ddlist" size="1"  style="width:150px" >
                            <f:selectItems value="#{npciCTSReturnBean.clearingTypeList}" />
                            <a4j:support event="onblur" action="#{npciCTSReturnBean.retrieveScheduleNo}" reRender="stxtMessage,ddScheduleNo" oncomplete="setMask();" focus="ddScheduleNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblScheduleNo" value="ScheduleNo" styleClass="label"/>
                        <a4j:region id="dataRegion">
                            <h:selectOneListbox id="ddScheduleNo" value="#{npciCTSReturnBean.scheduleNo}" styleClass="ddlist" size="1"  style="width:150px" >
                                <f:selectItems value="#{npciCTSReturnBean.scheduleNoList}" />
                                <a4j:support event="onblur" action="#{npciCTSReturnBean.retrieveReturnData}" reRender="stxtMessage,stxtDataFileName,tablePanel,taskList" oncomplete="setMask();" focus="btnHtml"/>
                            </h:selectOneListbox>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="scheduleDetailGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDataFileName" styleClass="label" value="XML Data File Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:outputText id="stxtDataFileName" styleClass="output" value="#{npciCTSReturnBean.scheduleFileName}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="xmlFileLinkGrid" columns="2" style="width:100%;height:30px;text-align:center;" styleClass="row2" width="100%"  rendered="#{npciCTSReturnBean.xmlLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="issuerSaveLink" value="#{npciCTSReturnBean.xmlFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{npciCTSReturnBean.downloadXML('RET')}"/>
                        </h:panelGroup>
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="issuerDoneLink" value="#{npciCTSReturnBean.xmlDoneFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{npciCTSReturnBean.downloadXML('DONE')}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="zipFileLinkGrid" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row2" width="100%"  rendered="#{npciCTSReturnBean.zipLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="zipSaveLink" value="#{npciCTSReturnBean.zipFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{npciCTSReturnBean.downloadXML('ZIP')}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="txtFileLinkGrid" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row2" width="100%" rendered="#{npciCTSReturnBean.txtLink}">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandLink id="txtSaveLink" value="#{npciCTSReturnBean.txtFileName}" style="color:blue;" styleClass="headerLabel"
                                             action="#{npciCTSReturnBean.downloadXML('TXT')}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Generate File" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMessage,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{npciCTSReturnBean.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{npciCTSReturnBean.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{npciCTSReturnBean.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="6"><h:outputText value="Return Instrument Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Item Seq No" /></rich:column>
                                        <rich:column><h:outputText value="Account No" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No" /></rich:column>
                                        <rich:column><h:outputText value="Cheque Date" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="In Favour Of" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.itemSeqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.instAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.inFavourOf}" /></rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
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
                                    <h:outputText id="confirmid" value="Are you sure to generate the file ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="buttonRegion">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{npciCTSReturnBean.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide(); #{rich:element('txtGenDate')}.focus();setMask();" 
                                                           reRender="stxtMessage,mainPanel"/>
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();" oncomplete="setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="dataRegion"/>
            <a4j:status onstart="#{rich:component('wait1')}.show()" onstop="#{rich:component('wait1')}.hide()" for="buttonRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <rich:modalPanel id="wait1" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
