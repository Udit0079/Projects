<%--
    Document   : dividendbatchdeletion
    Created on : Sep 1, 2011, 11:06:31 AM
    Author     : Administrator
--%>

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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Batch Deletion</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="BatchDeletion"/>
            <a4j:form id="BatchDeletion">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{BatchDeletion.todayDate}" />
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel"  value="Batch Deletion"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User Name : "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{BatchDeletion.userName}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel"   width="100%" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" >
                        <h:outputText id="Msg"  value="#{BatchDeletion.message}" styleClass="msg"/>
                    </h:panelGrid>
                    <h:panelGrid  id="panelGrid1" style="text-align:center;width:100%" styleClass="row1">
                        <rich:dataTable id="batchGridData"
                                        value="#{BatchDeletion.batchList}"
                                        var="dataItem"
                                        rowClasses="row1,row2"
                                        rows="3"
                                        columnsWidth="100"
                                        rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="30%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Batch Number" /></rich:column>
                                    <rich:column><h:outputText value="Transaction Date" /></rich:column>
                                    <rich:column><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>F
                            <rich:column><h:outputText value="#{dataItem.batchNo}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.date}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="selectlinkExperienceDetails" action="#{BatchDeletion.setBatchDeletionDetailsRowValues}"
                                                 reRender="mainPanel">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{BatchDeletion.currentBatchData}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="batchGridData" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid2" columnClasses="vtop" columns="1" width="100%" styleClass="row2">
                        <rich:dataTable id="batchGrid"
                                        value="#{BatchDeletion.batchGrid}"
                                        var="dataItem"
                                        rowClasses="row1,row2"
                                        rows="6"
                                        columnsWidth="100"
                                        rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
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
                            <rich:column><h:outputText value="#{dataItem.balance}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{dataItem.cramt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dramt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{dataItem.payBy}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.details}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.instNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.tranDesc}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.recNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.intFlag}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.iy}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.ty}" /></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="batchGrid" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="panelGrid3" columns="2" columnClasses="col8,col8" style="width:100%;text-align:left;" styleClass="row1">
                        <h:panelGroup>
                            <h:outputLabel value="Credit Amount:" styleClass="output" />
                            <h:outputLabel styleClass="output" value="#{BatchDeletion.crAmt}" style="color:purple;padding-left:150px"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel value="Debit Amount:" styleClass="output" />
                            <h:outputLabel styleClass="output" value="#{BatchDeletion.drAmt}" style="color:purple;padding-left:150px"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputLabel>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btndelete" value="Delete" oncomplete="#{rich:component('deletePanel')}.show()" disabled="#{BatchDeletion.disableBtnDelete}" reRender="mainPanel" focus="btnCancel"/>
                            <%-- <a4j:commandButton id="btnCancel" value="Cancel" action="#{BatchDeletion.cancelAction}" reRender="mainPanel" focus="outputtext2"/> --%>
                            <a4j:commandButton id="btnExit"  value="Exit" action="#{BatchDeletion.exitBtnAction()}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnYesDel')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete this batch ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true"  action="#{BatchDeletion.delete}" oncomplete="#{rich:component('deletePanel')}.hide();" reRender="mainPanel" focus="btnCancel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();return false;" focus="btnCancel"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
