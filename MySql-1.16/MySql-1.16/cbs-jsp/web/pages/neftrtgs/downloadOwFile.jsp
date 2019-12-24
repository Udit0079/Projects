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
            <title>Download NEFT/RTGS Outward File</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{downloadOwFileController.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Download NEFT/RTGS Outward File"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{downloadOwFileController.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{downloadOwFileController.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dateGrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblGenDate" styleClass="label" value="File Generation Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtGenDate" styleClass="input issueDt" value="#{downloadOwFileController.fileGenerationDt}" size="10"/>
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"></h:outputLabel>
                        <h:selectOneListbox id="ddFileType" styleClass="ddlist" required="true" style="width:150px" value="#{downloadOwFileController.fileType}" size="1">
                            <f:selectItems value="#{downloadOwFileController.fileTypeList}"/>
                            <a4j:support event="onblur" action="#{downloadOwFileController.dateAction}" reRender="stxtMessage,tablePanel,taskList,awaitingTablePanel,awaitingTaskList" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{downloadOwFileController.btnRefreshAction}" reRender="mainPanel" oncomplete="setMask();"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{downloadOwFileController.btnExitAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{downloadOwFileController.allGridDisplay}" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{downloadOwFileController.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="4"><h:outputText value="Generated File Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Txn Generated Branch" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fileGenDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenBrName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{downloadOwFileController.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{downloadOwFileController.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="awaitingTablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;display:#{downloadOwFileController.awaitingGridDisplay}" styleClass="row2" width="100%">
                        <a4j:region id="awaitingTblRegion">
                            <rich:dataTable value="#{downloadOwFileController.awaitingGridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="awaitingTaskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="9"><h:outputText value="Generated File Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Date" /></rich:column>
                                        <rich:column><h:outputText value="Txn Generated Branch" /></rich:column>
                                        <rich:column><h:outputText value="File Name" /></rich:column>
                                        <rich:column><h:outputText value="Remitter A/c" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary A/c" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary IFSC" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Download Link" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.fileGenDt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileGenBrName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.fileName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remitterAccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneficiaryName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneficiaryAccount}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneficiaryIfsc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink ajaxSingle="true" id="awaitingSelectlink" action="#{downloadOwFileController.downloadFile}" reRender="mainPanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{downloadOwFileController.awaitingCurrentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="awaitingTaskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
