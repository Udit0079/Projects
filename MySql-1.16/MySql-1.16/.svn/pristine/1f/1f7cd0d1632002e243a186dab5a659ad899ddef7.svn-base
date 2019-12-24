<%-- 
    Document   : TransferBatchModification
    Created on : Jun 29, 2011, 3:43:16 PM
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
            <title>Transfer Batch Modification</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="TransferBatchModification"/>
            <a4j:form id="TransferBatchModification">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPane42" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{TransferBatchModification.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2new" styleClass="headerLabel"  value="Transfer Batch Modification"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{TransferBatchModification.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{TransferBatchModification.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{TransferBatchModification.message}"/>
                    </h:panelGrid>
                    <h:panelGrid  id="a3" columns="2" style="width:100%;text-align:left;" styleClass="row1">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel id="lblBatchNo" styleClass="headerLabel" value="Batch No :" style=""><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="txtBatchNo" maxlength="9" styleClass="input" value="#{TransferBatchModification.batchNo}" tabindex="1">
                                <a4j:support event="onblur" action="#{TransferBatchModification.gridLoadAccordingToBatch}" oncomplete="if(#{TransferBatchDeletion.flag1=='false'}){#{rich:element('btnUpdate')}.focus();}else{#{rich:element('btnRefresh')}.focus();}"
                                             reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message" />
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a4" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable  value="#{TransferBatchModification.gridDetail}" var="dataItem"
                                         rowClasses="row1,row2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="15"><h:outputText value="Batch Detail" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="Balance" /></rich:column>
                                    <rich:column><h:outputText value="Credit" /></rich:column>
                                    <rich:column><h:outputText value="Debit" /></rich:column>
                                    <rich:column><h:outputText value="Pay By" /></rich:column>
                                    <rich:column><h:outputText value="Details" /></rich:column>
                                    <rich:column><h:outputText value="Instr No." /></rich:column>
                                    <rich:column><h:outputText value="Tran Desc" /></rich:column>
                                    <rich:column><h:outputText value="Enter By" /></rich:column>
                                    <rich:column><h:outputText value="Rec No" /></rich:column>
                                    <rich:column><h:outputText value="Int Flag" /></rich:column>
                                    <rich:column><h:outputText value="IY" /></rich:column>
                                    <rich:column><h:outputText value="TY" /></rich:column>
                                    <rich:column><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acno}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.balance}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.cramt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.dramt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText value="#{dataItem.payBy}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.details}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.instNo}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.tranDesc}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.recNo}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.intFlag}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.iy}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem.ty}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="selectlink" action="#{TransferBatchModification.fillValuesofGridInFields}" 
                                                 reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message" focus="txtDetail">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{TransferBatchModification.currentItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{TransferBatchModification.currentRow}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col8,col8" id="a5" style="width:100%;text-align:left;" styleClass="row1">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel id="lblDetail" value="Detail :"  styleClass="output" />
                            <h:inputText  id="txtDetail" maxlength="250" size="50" disabled="#{TransferBatchModification.flag1}" onblur="this.value = this.value.toUpperCase();" styleClass="input" value="#{TransferBatchModification.detail}" tabindex="2"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnUpdate" value="Update" tabindex="3" oncomplete="#{rich:component('modifyPanel')}.show()" disabled="#{TransferBatchModification.flag1}" reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" tabindex="4" action="#{TransferBatchModification.resetForm}" reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message" focus="txtBatchNo"/>
                            <a4j:commandButton id="Exit"  value="Exit" tabindex="5" action="#{TransferBatchModification.exitBtnAction}" reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="modifyPanel" autosized="true" width="350" onshow="#{rich:element('btnYesUpdate')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to update the detail of this batch ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesUpdate" ajaxSingle="true"  action="#{TransferBatchModification.updateDetail}"
                                                       oncomplete="#{rich:component('modifyPanel')}.hide();" reRender="lpg,a3,a4,a5,footerPanel,taskList,errorMessage,message" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoUpdate" onclick="#{rich:component('modifyPanel')}.hide();return false;" focus="btnRefresh"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
