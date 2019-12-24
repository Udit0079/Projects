<%-- 
    Document   : InventoryTypeMaster
    Created on : Jul 21, 2011, 11:39:53 AM
    Author     : ROHIT KRISHNA
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Inventory Type Master</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="InventoryTypeMaster"/>
            <a4j:form id="InventoryTypeMaster">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{InventoryTypeMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Inventory Type Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{InventoryTypeMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="errorMessage" styleClass="error" value="#{InventoryTypeMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{InventoryTypeMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="divPanel" width="100%">
                        <h:panelGrid columnClasses="col9" columns="6" id="a3" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblFunction" styleClass="label" value="Function :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist"  value="#{InventoryTypeMaster.function}" size="1" >
                                <f:selectItems value="#{InventoryTypeMaster.functionList}" />
                                <a4j:support action="#{InventoryTypeMaster.functionOnBlurMethod}" event="onblur" oncomplete="if(#{InventoryTypeMaster.function=='M'}){#{rich:element('ddInvtClass')}.focus();}
                                             else{#{rich:element('txtlInvtClass')}.focus();}" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a4" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblInvtClassdd" rendered="#{InventoryTypeMaster.modFlag}" styleClass="label" value="Inventory Class :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block">
                                <h:selectOneListbox id="ddInvtClass" rendered="#{InventoryTypeMaster.modFlag}" styleClass="ddlist" value="#{InventoryTypeMaster.invtClassDd}" size="1" style="width: 120px">
                                    <f:selectItems value="#{InventoryTypeMaster.invtClassList}" />
                                    <a4j:support action="#{InventoryTypeMaster.invtClassComboOnblur}" event="onblur" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="btnAsk"/>
                                </h:selectOneListbox>
                                <a4j:commandButton id="btnAsk" rendered="#{InventoryTypeMaster.modFlag}" value="Add Invt Type" action="#{InventoryTypeMaster.addNewRecordBtn}" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter"
                                                   oncomplete="#{rich:element('btnUpdate')}.disabled = true;" focus="txtInvtType"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a5" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lblInvtClass" styleClass="label" value="Inventory Class :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtlInvtClass" disabled="#{InventoryTypeMaster.disFlag1}" value="#{InventoryTypeMaster.invtClass}" maxlength="4" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a6" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2">
                            <h:outputLabel id="lbllInvtClassDesc" styleClass="label" value="Inventory Class Description :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInvtClassDesc" disabled="#{InventoryTypeMaster.disFlag1}" value="#{InventoryTypeMaster.invtClassDesc}" maxlength="50" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input">
                                <a4j:support action="#{InventoryTypeMaster.invtClassDescOnblur}" event="onblur" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="txtInvtType"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a7" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblInvtType" styleClass="label" value="Inventory Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInvtType" value="#{InventoryTypeMaster.invtType}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();"  size="20" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a8" width="100%" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1">
                            <h:outputLabel id="lblAcNature" styleClass="label" value="A/c Nature :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcNature" style="width: 120px" styleClass="ddlist"  value="#{InventoryTypeMaster.acNature}" size="1">
                                <f:selectItems value="#{InventoryTypeMaster.acNatureList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="submitBtnPanel" columns="1" style="width:100%;height:30px;text-align:center;border:1px ridge #BED6F8;" styleClass="row2">
                        <a4j:commandButton id="btnSubmitToGrid" rendered="#{InventoryTypeMaster.addFlag}" value="Submit To Grid" action="#{InventoryTypeMaster.gridLoadOnSubmitBtn}" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="txtInvtType"/>
                        <a4j:commandButton id="btnSubmitToGrid1" rendered="#{InventoryTypeMaster.addNewFlag}" value="Submit to Grid" action="#{InventoryTypeMaster.gridLoadOnSubmitBtn}" oncomplete="#{rich:element('btnUpdate')}.disabled = true;" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="txtInvtType"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table1" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{InventoryTypeMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList1" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Invt Class" /></rich:column>
                                        <rich:column><h:outputText value="Invt Class Description" /></rich:column>
                                        <rich:column><h:outputText value="Invt Type" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Enter By" /></rich:column>
                                        <rich:column visible="false"><h:outputText value="Enter Date" /></rich:column>
                                        <rich:column id="selCol" style="text-align:center;" rendered="#{InventoryTypeMaster.modFlag}"><h:outputText value="Select" /></rich:column>
                                        <rich:column style="text-align:center;"><h:outputText value="Delete" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.invtClass}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtClassDesc}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.invtType}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column visible="false"><h:outputText value="#{dataItem.enterDate}" /></rich:column>
                                <rich:column style="text-align:center;width:40px" rendered="#{InventoryTypeMaster.modFlag}">
                                    <a4j:commandLink id="selectlink" action="#{InventoryTypeMaster.fillValuesofGridInFields}" 
                                                     reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="txtInvtType">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InventoryTypeMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InventoryTypeMaster.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <rich:column>
                                    <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{InventoryTypeMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{InventoryTypeMaster.currentRow}" />
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </a4j:region>
                        <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are you sure to delete this record ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true" action="#{InventoryTypeMaster.delete}"
                                                                   oncomplete="#{rich:component('deletePanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" rendered="#{InventoryTypeMaster.addFlag}"/>
                            <a4j:commandButton id="btnSave1" value="Save" oncomplete="#{rich:component('savePanel1')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" rendered="#{InventoryTypeMaster.addNewFlag}"/>
                            <a4j:commandButton id="btnUpdate" value="Update" oncomplete="#{rich:component('modifyPanel')}.show()" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" rendered="#{InventoryTypeMaster.modFlag}"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{InventoryTypeMaster.resetForm}" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="ddFunction"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{InventoryTypeMaster.exitBtnAction}" reRender="message,errorMessage" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYesSavePanel')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are you sure to save?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesSavePanel" ajaxSingle="true" action="#{InventoryTypeMaster.saveMasterDetail}"
                                                               oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNoSavePanel" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="savePanel1" autosized="true" width="250" onshow="#{rich:element('btnYesSavePanel1')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are You Sure To Save?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesSavePanel1" ajaxSingle="true" action="#{InventoryTypeMaster.saveNewInventoryTypes}"
                                                               oncomplete="#{rich:component('savePanel1')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNoSavePanel1" onclick="#{rich:component('savePanel1')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="modifyPanel" autosized="true" width="250" onshow="#{rich:element('btnYesUpdatePanel')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText value="Are You Sure To Update ?"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYesUpdatePanel" ajaxSingle="true" action="#{InventoryTypeMaster.updateInvtTypeDetail}"
                                                               oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="message,errorMessage,a3,a4,a5,a6,a7,a8,submitBtnPanel,table1,taskList1,gpFooter" focus="btnRefresh"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNoUpdatePanel" onclick="#{rich:component('modifyPanel')}.hide();return false;" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
