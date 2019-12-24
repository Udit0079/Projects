<%-- 
    Document   : cpsmsinterbankresponse
    Created on : 26 Dec, 2016, 3:41:30 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Inter Bank Response</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
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
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{interBankResponseBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Inter Bank Response"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{interBankResponseBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelMsg" columnClasses="col8,col8" columns="2" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{interBankResponseBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFileType" styleClass="label" value="File Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddFileType" style="width: 120px" styleClass="ddlist"  value="#{interBankResponseBean.fileType}" size="1">
                            <f:selectItems value="#{interBankResponseBean.fileTypeList}" />
                            <a4j:support event="onblur" action="#{interBankResponseBean.onBlurFileType}" reRender="stxtMsg,ddPaymentReceivedDt"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPaymentReceivedDt" styleClass="label" value="Payment Received Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPaymentReceivedDt" style="width: 120px" styleClass="ddlist"  value="#{interBankResponseBean.paymentReceivedDt}" size="1">
                            <f:selectItems value="#{interBankResponseBean.paymentReceivedDtList}" />
                            <a4j:support event="onblur" action="#{interBankResponseBean.onBlurPaymentReceivedDt}" reRender="stxtMsg,ddPaymentReceivedMessageId"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="4" columnClasses="col13,col13,col13,col13" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblPaymentReceivedMessageId" styleClass="label" value="MessageId"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPaymentReceivedMessageId" style="width: 120px" styleClass="ddlist"  value="#{interBankResponseBean.messageId}" size="1">
                            <f:selectItems value="#{interBankResponseBean.messageIdList}" />
                            <a4j:support event="onblur" action="#{interBankResponseBean.onBlurMessageId}" reRender="stxtMsg,ddPaymentReceivedBatchNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPaymentReceivedBatchNo" styleClass="label" value="Batch No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddPaymentReceivedBatchNo" style="width: 120px" styleClass="ddlist"  value="#{interBankResponseBean.batchNo}" size="1">
                            <f:selectItems value="#{interBankResponseBean.batchNoList}" />
                            <a4j:support event="onblur" action="#{interBankResponseBean.onBlurBatchNo}" reRender="stxtMsg,txtDisplayDate,txtMessageId,txtBatchNo,tablePanel,taskList"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDisplayDate" styleClass="label" value="Date"/>
                        <h:outputText id="txtDisplayDate" styleClass="output" value="#{interBankResponseBean.displayDate}"/>
                        <h:outputLabel id="lblMessageId" styleClass="label" value="MessageId"/>
                        <h:outputText id="txtMessageId" styleClass="output" value="#{interBankResponseBean.displayMessageId}"/>
                        <h:outputLabel id="lblBatchNo" styleClass="label" value="Batch No"/>
                        <h:outputText id="txtBatchNo" styleClass="output" value="#{interBankResponseBean.displayBatchNo}"/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{interBankResponseBean.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Batch Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Beneficiary A/c" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Debit A/c" /></rich:column>
                                        <rich:column><h:outputText value="CMS No" /></rich:column>
                                        <rich:column><h:outputText value="UTR" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Reason" /></rich:column>
                                        <rich:column><h:outputText value="Detail" /></rich:column>
                                        <rich:column><h:outputText value="Credit Tran.ID" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.creditAccountNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.beneficiaryName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.txnAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.debitAccountNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.cmsBankRefNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.utrNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.reason}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.details}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.creditTranId}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="gpFooter" columnClasses="col19,col20,col21" columns="3" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel1" styleClass="vtop">
                            <h:outputText id="instructions" styleClass="output" value="In Detail Column, Paid- For Success Txn and Un-Paid- For Failed Txn" style="color:blue;"/>
                        </h:panelGroup>
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="Generate Response" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{interBankResponseBean.resetForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{interBankResponseBean.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                        <h:panelGroup id="btnPanel3" styleClass="vtop">
                            <h:outputLabel/>
                        </h:panelGroup>
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
                                        <h:outputText id="confirmid" value="Are you sure to generate the file ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{interBankResponseBean.processAction}" 
                                                           oncomplete="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel"/>
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
