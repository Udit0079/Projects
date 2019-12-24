<%-- 
    Document   : TransferBatchDeletion
    Created on : Nov 24, 2010, 11:57:45 AM
    Author     : root
    Modify By  : Dhirendra Singh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Transfer Batch Deletion</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="TransferBatchDeletion">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPane42" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{TransferBatchDeletion.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel id="label2new" styleClass="headerLabel"  value="Transfer Batch Deletion"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{TransferBatchDeletion.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errorMsg"   width="100%" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" >
                        <h:outputText id="errMsg"  value="#{TransferBatchDeletion.errorMessage}" styleClass="error"/>
                        <h:outputText id="Msg"  value="#{TransferBatchDeletion.message}" styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid  id="batchgrid" columns="2" style="width:100%;text-align:left;" styleClass="row1">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel id="outlabel1" styleClass="headerLabel" value="Batch No :" style=""><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText  id="outputtext2" maxlength="9" styleClass="input" value="#{TransferBatchDeletion.batchNo}" tabindex="1">
                                <a4j:support event="onblur" action="#{TransferBatchDeletion.gridLoad}" oncomplete="if(#{TransferBatchDeletion.flag1=='false'}){#{rich:element('delete')}.focus();}else{#{rich:element('btnRefresh')}.focus();}"
                                             reRender="errorMsg,errMsg,Msg,gridPanel103,batchgrid1,taskList,footerPanel" />
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable  value="#{TransferBatchDeletion.gridData}" var="dataItem"
                                         rowClasses="row1,row2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
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
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid columns="2" columnClasses="col8,col8" id="batchgrid1" style="width:100%;text-align:left;" styleClass="row1">
                        <h:panelGroup>
                            <h:outputLabel id="label2" value="Cr. Amount:"  styleClass="output" />
                            <h:outputLabel id="label3new" styleClass="output" value="#{TransferBatchDeletion.crAmt}" style="color:purple;padding-left:150px"/>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel id="label4" value="Dr. Amount:"  styleClass="output" />
                            <h:outputLabel id="label6" styleClass="output" value="#{TransferBatchDeletion.drAmt}" style="color:purple;padding-left:150px"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton   id="delete" value="Delete" tabindex="2" oncomplete="#{rich:component('deletePanel')}.show()" reRender="taskList,errMsg,Msg,errorMsg,batchgrid1,footerPanel,outputtext2" focus="btnRefresh"/>
                            <a4j:commandButton   id="btnRefresh" value="Refresh" tabindex="3" action="#{TransferBatchDeletion.resetForm}" reRender="taskList,errorMsg,errMsg,Msg,batchgrid1,footerPanel,outputtext2" focus="outputtext2"/>
                            <a4j:commandButton id="Exit"  value="Exit" tabindex="4" action="#{TransferBatchDeletion.exitBtnAction}" reRender="taskList,errorMsg,errMsg,Msg,batchgrid1,footerPanel,outputtext2"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
              <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>               
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this Batch ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYesDel" ajaxSingle="true"  action="#{TransferBatchDeletion.delete}"
                                                       onclick="#{rich:component('deletePanel')}.hide();" reRender="taskList,errMsg,Msg,errorMsg,batchgrid1,footerPanel,outputtext2" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNoDel" onclick="#{rich:component('deletePanel')}.hide();return false;" focus="btnRefresh"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>

