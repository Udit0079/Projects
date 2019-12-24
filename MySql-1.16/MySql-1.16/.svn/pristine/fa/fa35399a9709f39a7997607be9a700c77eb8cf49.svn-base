<%--
    Document   : BusMaster
    Created on : Jul 18, 2011, 4:22:47 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Bus Master</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:form id="BusMaster">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{BusMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Bus Master"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BusMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row1">
                        <h:outputText id="errMsg" value="#{BusMaster.message}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:120px" styleClass="ddlist" value="#{BusMaster.operation}">
                                <f:selectItems value="#{BusMaster.operationList}"/>
                                <a4j:support event="onblur" action="#{BusMaster.setOperationOnBlur}" reRender="popUpGridPanel,mainPanel" oncomplete="if(#{BusMaster.operation=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else {  #{rich:component('popUpGridPanel')}.hide(); };#{rich:element('txtBusStartTime')}.style=setTimeMask(); #{rich:element('txtBusEndTime')}.style=setTimeMask();" />
                        </h:selectOneListbox>

                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Bus Number"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtBusNo" disabled="#{BusMaster.disableBusNo}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:80px" value="#{BusMaster.busNo}"/>
                        <h:outputLabel styleClass="label" value="Bus Driver"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtBusDriver" disabled="#{BusMaster.disableBusDriver}" maxlength="25" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:140px" value="#{BusMaster.busDriverName}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label" value="Bus Start Time"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:80px" >
                            <h:inputText id="txtBusStartTime" disabled="#{BusMaster.disableStartTime}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{BusMaster.busStartTime}"/>

                            <h:selectOneListbox size="1" id="am_pm_1" disabled="#{BusMaster.disableStartTime}" styleClass="ddlist" value="#{BusMaster.amPmStart}" style="height:18px">
                                <f:selectItem itemValue="AM"/>
                                <f:selectItem itemValue="PM"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                        <h:outputLabel styleClass="label" value="Bus End Time"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:panelGroup layout="block">
                            <h:inputText id="txtBusEndTime" disabled="#{BusMaster.disableEndTime}" styleClass="input calInstTime" style="width:35px;text-align:center;setTimeMask()" maxlength="5" value="#{BusMaster.busEndTime}"/>
                            <h:selectOneListbox size="1" id="am_pm_2" disabled="#{BusMaster.disableEndTime}" styleClass="ddlist" value="#{BusMaster.amPmEnd}" style="height:18px">
                                <f:selectItem itemValue="AM"/>
                                <f:selectItem itemValue="PM"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel3" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label" value="Remarks"/>
                        <h:inputText id="txtRemarks" disabled="#{BusMaster.disableRemarks}" maxlength="25" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:200px" value="#{BusMaster.remarks}"/>
                        <h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{BusMaster.saveAction}"
                                               disabled="#{BusMaster.disableSaveButton}"
                                               oncomplete="#{rich:element('txtBusStartTime')}.style=setTimeMask();
                                               #{rich:element('txtBusEndTime')}.style=setTimeMask();
                                               #{rich:element('tablePanel1')}.style.display=none;"
                                               reRender="tablePanel1,mainPanel"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{BusMaster.deleteAction}"
                                               disabled="#{BusMaster.disableDeleteButton}"
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{BusMaster.cancelAction}" type="reset" reRender="mainPanel,operationList"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{BusMaster.exitAction}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="200" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Bus Master Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                 <h:panelGrid columnClasses="vtop" columns="1" id="tablePanel1"  styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{BusMaster.busMasterTable}"
                                            var="dataItem"
                                            rowClasses="gridrow1,gridrow2"
                                            id="taskList1"
                                            rows="3"
                                            columnsWidth="100"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Bus No" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Start Time" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="End Time"  style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Bus Driver" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Remarks" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.busNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.busStartTime}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.busEndTime}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.busDriverName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{BusMaster.setBusRowValues}"
                                                     oncomplete="#{rich:element('txtBusStartTime')}.style=setTimeMask();
                                                     #{rich:element('txtBusEndTime')}.style=setTimeMask();
                                                     #{rich:element('tablePanel1')}.style.display=none;"
                                                     reRender="mainPanel,txtBusStartTime,txtBusEndTime,popUpGridPanel" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" ondblclick=" #{rich:component('popUpGridPanel')}.hide();"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{BusMaster.currentBusItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{BusMaster.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <f:facet name="footer">
                                    <h:outputText value="#{BusMaster.totalEmpRecords} rows found" style="text-align:center" />
                                </f:facet>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </rich:modalPanel>
            </a4j:form>           
        </body>
    </html>
</f:view>