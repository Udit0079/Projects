<%-- 
    Document   : biometricDeviceInventory
    Created on : Dec 13, 2016, 12:05:43 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Biometric Device Inventory</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".chkDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="biometricId">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{BiometricDeviceInventory.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Biometric Device Inventory"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{BiometricDeviceInventory.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="message" styleClass="msg" value="#{BiometricDeviceInventory.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" ><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{BiometricDeviceInventory.function}">
                                    <f:selectItems value="#{BiometricDeviceInventory.functionList}"/>
                                    <a4j:support action="#{BiometricDeviceInventory.chgFunction}" event="onblur" oncomplete="setMask();" reRender="message,stxtcustAdd,stxtcustDob,table,btnSave,lblPersonId,stxtPersonId,lblSections,ddSections,stxtTitle,txtName,txtMake"/>
                                </h:selectOneListbox>
                            <h:outputLabel id="lblPersonId" styleClass="label" value="Base Branch" style="display:#{BiometricDeviceInventory.invisible}"></h:outputLabel>
                             <h:inputText id="stxtPersonId" styleClass="input" style="width:80px;display:#{BiometricDeviceInventory.invisible}" value="#{BiometricDeviceInventory.baseBranch}"maxlength="12" onkeydown="this.value=this.value.toUpperCase();"/>  
                              <h:outputLabel id="lblSections" styleClass="label" value="Branch" style="display:#{BiometricDeviceInventory.invisible}"></h:outputLabel>
                              <h:selectOneListbox id="ddSections" styleClass="ddlist" size="1" style="width:80px;display:#{BiometricDeviceInventory.invisible}" value="#{BiometricDeviceInventory.branch}">
                                    <f:selectItems value="#{BiometricDeviceInventory.branchList}"/>
                                </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblTitle" styleClass="label" value="Device SrlNo" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="stxtTitle" styleClass="input" style="width:80px;" value="#{BiometricDeviceInventory.deviceSrlno}" maxlength="12" onkeydown="this.value=this.value.toUpperCase();" disabled="#{BiometricDeviceInventory.disableOnFun}">
                                <%--a4j:support action="#{BiometricDeviceInventory.gridLoad}" event="onblur" oncomplete="setMask();" reRender="message,stxtcustAdd,stxtcustDob,table,btnSave,stxtPersonId,ddSections"/--%>
                            </h:inputText> 
                            <h:outputLabel id="lblName" styleClass="label" value="Device Model" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtName" styleClass="input" style="width:80px;" value="#{BiometricDeviceInventory.deviceModel}" onkeydown="this.value=this.value.toUpperCase();"/> 
                            <h:outputLabel id="lblDob" styleClass="label" value="Device Make" ><font class="required" style="color:red;">*</font> </h:outputLabel> 
                            <h:inputText id="txtMake" styleClass="input" style="width:80px;" value="#{BiometricDeviceInventory.deviceMake}" onkeydown="this.value=this.value.toUpperCase();"/> 
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{BiometricDeviceInventory.gridDetail}" var="dataItem"
                                                rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="13"><h:outputText value="Biometric Device Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="DeviceSrlNo"/></rich:column>
                                            <rich:column><h:outputText value="Device Model"/></rich:column>
                                            <rich:column><h:outputText value="Device Make"/></rich:column>
                                            <rich:column><h:outputText value="Enter By"/></rich:column>
                                            <rich:column><h:outputText value="Enter Date"/></rich:column>
                                            <rich:column><h:outputText value="Status"/></rich:column>
                                            <rich:column><h:outputText value="Branch"/></rich:column>
                                            <rich:column><h:outputText value="SELECT"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.devSrlNo}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.devModel}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.devMake}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.devEnterBy}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.devEnterDt}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.status}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.branch}"/></rich:column>
                                
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{BiometricDeviceInventory.select}" oncomplete="setMask();" reRender="mainPanel">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"/>
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{BiometricDeviceInventory.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" value="#{BiometricDeviceInventory.btnValue}" action="#{BiometricDeviceInventory.populateMessage}" disabled="#{BadPersonInfo.btnFlag}" oncomplete="#{rich:component('processPanel')}.show();setMask();" reRender="leftPanel,mainPanel,processPanel,confirmid"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{BiometricDeviceInventory.refreshForm}" oncomplete="setMask();" reRender="mainPanel"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{BiometricDeviceInventory.exitBtnAction}" reRender="mainPanel"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{BiometricDeviceInventory.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{BiometricDeviceInventory.saveBiometricDeviceData}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="message,mainPanel"/>
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
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>