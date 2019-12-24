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
            <title>NEFT/RTGS File Generation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{manualNeftFileGeneration.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="NEFT/RTGS File Generation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{manualNeftFileGeneration.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{manualNeftFileGeneration.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="modeGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblMode" styleClass="label" value="Mode"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMode" style="width:180px" styleClass="ddlist"  value="#{manualNeftFileGeneration.mode}" size="1">
                            <f:selectItems value="#{manualNeftFileGeneration.modeList}"/>
                            <a4j:support event="onblur" action="#{manualNeftFileGeneration.onChangeFunction}" reRender="stxtMessage,
                                         btnHtml,tablePanel,taskList,showTablePanel,showTaskList" oncomplete="setMask();" focus="ddFileType"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFileType" style="width:180px" styleClass="ddlist"  value="#{manualNeftFileGeneration.fileType}" size="1">
                            <f:selectItems value="#{manualNeftFileGeneration.fileTypeList}"/>
                            <a4j:support event="onblur" focus="txtGenDate"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblGenDate" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{manualNeftFileGeneration.fileGenerationDt}" size="10">
                            <a4j:support event="onblur" action="#{manualNeftFileGeneration.onChangeDate}" reRender="stxtMessage,btnHtml,
                                         tablePanel,taskList,showTablePanel,showTaskList" oncomplete="setMask();" focus="btnHtml"/>
                        </h:inputText>            
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="#{manualNeftFileGeneration.btnValue}" action="#{manualNeftFileGeneration.createConfirmTxt}" 
                                               oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="stxtMessage,processPanel,confirmid,tablePanel,taskList,showTablePanel,showTaskList"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{manualNeftFileGeneration.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{manualNeftFileGeneration.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{manualNeftFileGeneration.neftDetailFlag}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:extendedDataTable value="#{manualNeftFileGeneration.gridDetail}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList" rowKeyVar="row" height="300px"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <h:outputText value="Neft/Rtgs Detail"/>
                                </f:facet>
                                <rich:column width="10%">
                                    <f:facet name="header"><h:outputText value="Sender A/c" /></f:facet>
                                    <h:outputText value="#{dataItem.senderAcc}" />
                                </rich:column>
                                <rich:column width="15%">
                                    <f:facet name="header"><h:outputText value="Sender Name" /></f:facet>
                                    <h:outputText value="#{dataItem.senderName}" />
                                </rich:column>
                                <rich:column width="10%">
                                    <f:facet name="header"><h:outputText value="Sender IFSC" /></f:facet>
                                    <h:outputText value="#{dataItem.senderIfsc}" />
                                </rich:column>
                                <rich:column width="10%">
                                    <f:facet name="header"><h:outputText value="Beneficiary A/c" /></f:facet>
                                    <h:outputText value="#{dataItem.beneAccount}" />
                                </rich:column>
                                <rich:column width="15%">
                                    <f:facet name="header"><h:outputText value="Beneficiary Name" /></f:facet>
                                    <h:outputText value="#{dataItem.beneName}" />
                                </rich:column>
                                <rich:column width="10%">
                                    <f:facet name="header"><h:outputText value="Beneficiary IFSC" /></f:facet>
                                    <h:outputText value="#{dataItem.ifsccode}" />
                                </rich:column>
                                <rich:column width="10%">
                                    <f:facet name="header"><h:outputText value="Amount" /></f:facet>
                                    <h:outputText value="#{dataItem.amount}" />
                                </rich:column>
                                <rich:column width="15%">
                                    <f:facet name="header"><h:outputText value="Reason" /></f:facet>
                                    <h:outputText value="#{dataItem.reason}" />
                                </rich:column>
                                <rich:column width="5%" style="text-align:center"> 
                                <f:facet name="header">
                                    <h:selectBooleanCheckbox value="#{manualNeftFileGeneration.allSelected}">
                                        <a4j:support event="onchange" action="#{manualNeftFileGeneration.setAllBoxSelected}" reRender="taskList"/>
                                    </h:selectBooleanCheckbox>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{dataItem.selected}"/>
                            </rich:column>
                            </rich:extendedDataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="showTablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{manualNeftFileGeneration.neftFileFlag}" styleClass="row2" width="100%">
                        <a4j:region id="showTblRegion">
                            <rich:dataTable value="#{manualNeftFileGeneration.fileGridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="showTaskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="5"><h:outputText value="Generated File Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="File No" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. Date" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="File Gen. By" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fileNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenBy}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{manualNeftFileGeneration.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{manualNeftFileGeneration.fileCurrentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="showTaskList" maxPages="10"/>
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
                                    <h:outputText id="confirmid" value="#{manualNeftFileGeneration.confirmText}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{manualNeftFileGeneration.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();setMask();" 
                                                       reRender="stxtMessage,mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();" oncomplete="setMask();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
