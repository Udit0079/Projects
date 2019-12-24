<%--
    Document   : LocalHighChkClrRegister
    Created on : 16 Sep, 2010, 1:15:56 PM
    Author     : root
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Local/High Value Cheque Clearing Register</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form3">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="header" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LocalHighChkClrRegisManaged.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="headerLabel" value="Local/High Value Cheque Clearing Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LocalHighChkClrRegisManaged.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel1" width="100%">
                        <h:panelGrid columns="1" id="gridPanel4" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel15" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblClearMode" styleClass="label" value="Clearing Mode"/>
                                <h:panelGroup>
                                    <h:selectOneListbox id="ddClearMode" styleClass="ddlist" size="1" style="width:100px" value="#{LocalHighChkClrRegisManaged.selectclearModeOption}" >
                                        <f:selectItems value="#{LocalHighChkClrRegisManaged.clearModeOption}"/>
                                        <a4j:support reRender="stxtLclHghValue,idError,ddPendingDate" actionListener="#{LocalHighChkClrRegisManaged.clickOnClearingMode}" event="onchange" focus="ddPendingDate"/>

                                    </h:selectOneListbox>
                                    <h:outputText id="stxtLclHghValue" styleClass="output" value="#{LocalHighChkClrRegisManaged.clearModeResult}" />
                                </h:panelGroup>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblPendingDate" styleClass="label" value="Pending Date"/>
                                <h:panelGroup>
                                    <h:selectOneListbox value="#{LocalHighChkClrRegisManaged.loadPendingDateListOption}" id="ddPendingDate" styleClass="ddlist" size="1" style="width:100px"  >
                                        <f:selectItems  value="#{LocalHighChkClrRegisManaged.loadPendingDateList}"/>
                                        <a4j:support reRender="taskList,idError" actionListener="#{LocalHighChkClrRegisManaged.loadCkqGrid}" event="onblur" />
                                    </h:selectOneListbox>
                                </h:panelGroup>            
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTable" width="100%" styleClass="row1" style="height:235px;">
                                <rich:dataTable value="#{LocalHighChkClrRegisManaged.chqGridDetails}" var="dataItem"
                                                rowClasses="gridrow1, gridrow2" id="taskList"  columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                            <rich:column><h:outputText value="A/C No." /></rich:column>
                                            <rich:column><h:outputText value="Instr.Dt" /></rich:column>
                                            <rich:column><h:outputText value="Instr.No" /></rich:column>
                                            <rich:column><h:outputText value="Amount(Rs.)" /></rich:column>
                                            <rich:column><h:outputText value="Circle Type" /></rich:column>
                                            <rich:column><h:outputText value="Name" /></rich:column>
                                            <rich:column><h:outputText value="Select Record" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column>
                                        <f:facet name="header"/>
                                        <h:outputText id="status"  value="#{dataItem.status}"  />
                                    </rich:column>
                                    <rich:column><h:outputText id="accNo" value="#{dataItem.accNo}"/></rich:column>
                                    <rich:column><h:outputText id="instrDt" value="#{dataItem.instrDt}"/></rich:column>
                                    <rich:column><h:outputText  id="instrNo"value="#{dataItem.instrNo}"/></rich:column>
                                    <rich:column>
                                        <h:outputText  id="amount"value="#{dataItem.amount}">
                                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                        </h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText  id="emFlag"value="#{dataItem.emFlag}" />
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText  id="name"value="#{dataItem.name}" />
                                    </rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink focus="ddReasonForReturn" ajaxSingle="true" id="selectlink" action="#{LocalHighChkClrRegisManaged.setRowValues}"
                                                         reRender="stxtInstNo,stxtInstDate,stxtInstAmt,stxtBankName,stxtBankAdd,stxtAccNo,stxtName2">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{LocalHighChkClrRegisManaged.chqDetails}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{LocalHighChkClrRegisManaged.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="20" />
                                <rich:modalPanel id="showPanel" autosized="true" width="250" onshow="#{rich:element('btnCancel')}.focus()">
                                    <f:facet name="header">
                                        <h:outputText value="Do you want to clear the record ?" style="padding-right:15px;" />
                                        
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage  value="/images/close.png" styleClass="hidelink" id="hidelink2" />
                                            <rich:componentControl for="showPanel" attachTo="hidelink2" operation="hide" event="onclick" />
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                            <tbody>
                                                <tr style="height:50px">
                                                    <td align="center" width="50%" colspan="2">
                                                        <a4j:commandButton reRender="idError,stxtName2,stxtAccNo,stxtBankAdd,stxtBankName,stxtInstAmt,stxtInstNo,stxtInstDate,taskList"  action="#{LocalHighChkClrRegisManaged.clickOnClearButton}"value="Yes" ajaxSingle="true"
                                                                           onclick="#{rich:component('showPanel')}.hide();" />
                                                    </td>
                                                    <td align="center" width="50%" colspan="2">
                                                        <a4j:commandButton id="btnCancel" value="Cancel" onclick="#{rich:component('showPanel')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGrid>

                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="panelGridRight1" width="100%" style="height:310px;">
                            <h:panelGrid id="panelGridRight2" style="width:100%;text-align:center;" styleClass="row1" >
                                <h:outputLabel id="lblChqDetails" styleClass="label" value="Cheque Details"/>
                            </h:panelGrid>
                            <h:panelGrid  columns="6" columnClasses="col13,col1,col3,col3,col3,col3" id="panelGridRight3" style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblInstNo" styleClass="label" value="Inst. No."/>
                                <h:outputText id="stxtInstNo" styleClass="output"  value="#{LocalHighChkClrRegisManaged.instrNo}"></h:outputText>
                                <h:outputLabel id="lblInstDate" styleClass="label" value="Inst. Date."/>
                                <h:outputText id="stxtInstDate" styleClass="output"  value="#{LocalHighChkClrRegisManaged.instrDate}"></h:outputText>
                                <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amt."/>
                                <h:outputText id="stxtInstAmt" styleClass="output"  value="#{LocalHighChkClrRegisManaged.instrAmt}"></h:outputText>
                            </h:panelGrid>
                            <h:panelGrid columns="2" columnClasses="col9,col9" id="panelGridRight4" style="width:100%;" styleClass="row1" >
                                <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"/>
                                <h:outputText id="stxtBankName" styleClass="output"  value="#{LocalHighChkClrRegisManaged.bankName}"></h:outputText>
                            </h:panelGrid>
                            <h:panelGrid  columns="2" columnClasses="col9,col9" id="panelGridRight5" style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblBankAdd" styleClass="label" value="Bank Address"/>
                                <h:outputText id="stxtBankAdd" styleClass="output"  value="#{LocalHighChkClrRegisManaged.bankAddress}"></h:outputText>
                            </h:panelGrid>
                            <h:panelGrid  columns="4" columnClasses="col13,col13,col13,col13" id="panelGridRight6" style="width:100%;" styleClass="row1" >
                                <h:outputLabel id="lblAccNo" styleClass="label" value="Account No."/>
                                <h:outputText id="stxtAccNo" styleClass="output"  value="#{LocalHighChkClrRegisManaged.acountNo}"></h:outputText>
                                <h:outputLabel id="lblName2" styleClass="label" value="Name"/>
                                <h:outputText id="stxtName2" styleClass="output"  value="#{LocalHighChkClrRegisManaged.name}"></h:outputText>
                            </h:panelGrid>
                            <h:panelGrid  columns="2" columnClasses="col8,col5"id="panelGridRight9" style="width:100%;" styleClass="row2" >
                                <h:outputLabel id="lblReasonForReturn" styleClass="label" value="Reason For Return(If Returned)"/>
                                <h:selectOneListbox id="ddReasonForReturn" styleClass="ddlist" size="1" style="width:380px" value="#{LocalHighChkClrRegisManaged.reasonForReturnOption}" >
                                    <f:selectItems value="#{LocalHighChkClrRegisManaged.reasonForReturn}"/>
                                    <a4j:support  event="onblur" reRender="btnClear,btnReturn" actionListener="#{LocalHighChkClrRegisManaged.comboReasonForReturn}" />
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelTableRight" width="100%" styleClass="row1" style="height:95px;">
                                <rich:dataTable value="#{LocalHighChkClrRegisManaged.returnGridList}" var="item"
                                                rowClasses="gridrow1, gridrow2" id="taskListRight" rows="2" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                            <rich:column><h:outputText value="Inst. No." /></rich:column>
                                            <rich:column><h:outputText value="Inst. Amt." /></rich:column>
                                            <rich:column><h:outputText value="Status" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column>
                                        <f:facet name="header"/>
                                        <h:outputText  value="#{item.sNo}"  />
                                    </rich:column>
                                    <rich:column><h:outputText value="#{item.accNoReturn}"   /></rich:column>
                                    <rich:column><h:outputText value="#{item.instNumber}" /></rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.instAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="#{item.statuAfterReturn}" />
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" id="dataScroll" for="taskListRight" maxPages="10"/>
                                <rich:modalPanel id="showPanelForRightGrid" autosized="true" width="250" onshow="#{rich:element('btnCancelClearAll')}.focus()">
                                    <f:facet name="header">
                                        <h:outputText value="Do you want to clear all the records ?" style="padding-right:15px;" />
                                        
                                    </f:facet>
                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                            <rich:componentControl for="showPanelForRightGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                        </h:panelGroup>
                                    </f:facet>
                                    <h:form>
                                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                            <tbody>
                                                <tr style="height:50px">
                                                    <td align="center" width="50%" colspan="2">
                                                        <a4j:commandButton reRender="idError,stxtName2,stxtAccNo,stxtBankAdd,stxtBankName,stxtInstAmt,stxtInstNo,stxtInstDate,taskList"  action="#{LocalHighChkClrRegisManaged.clickOnAllClearButton}"value="Yes" ajaxSingle="true"
                                                                           onclick="#{rich:component('showPanelForRightGrid')}.hide();" />
                                                    </td>
                                                    <td align="center" width="50%">
                                                        <a4j:commandButton id="btnCancelClearAll" value="Cancel" onclick="#{rich:component('showPanelForRightGrid')}.hide();return false;" />
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </h:form>
                                </rich:modalPanel>
                            </h:panelGrid>
                        </h:panelGrid>

                    </h:panelGrid>
                    <h:panelGrid id="error" columnClasses="col8" columns="1" width="100%" style="text-align:center;" styleClass="row2">
                        <h:outputText styleClass="error" id="idError" value="#{LocalHighChkClrRegisManaged.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnClearAll" value="Clear All" onclick="#{rich:component('showPanelForRightGrid')}.show();"/>
                            <a4j:commandButton  disabled="#{LocalHighChkClrRegisManaged.flag}" id="btnClear" value="Clear" onclick="#{rich:component('showPanel')}.show();"/>
                            <a4j:commandButton disabled="#{LocalHighChkClrRegisManaged.flag2}" id="btnReturn" value="Return"  action="#{LocalHighChkClrRegisManaged.returnResult}"
                                               reRender="idError,taskListRight,stxtInstNo,stxtInstDate,stxtInstAmt,stxtBankName,stxtBankAdd,stxtAccNo,btnReturn,btnClear,taskList,ddReasonForReturn,dataScroll,stxtName2"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LocalHighChkClrRegisManaged.refreshForm}"
                                               reRender="taskList,ddClearMode,idError,taskListRight,stxtInstNo,stxtInstDate,stxtInstAmt,stxtBankName,stxtBankAdd,stxtAccNo,btnReturn,btnClear,ddPendingDate,ddReasonForReturn"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LocalHighChkClrRegisManaged.exitBtnAction}"
                                               reRender="ddClearMode,idError,taskListRight,stxtInstNo,stxtInstDate,stxtInstAmt,stxtBankName,stxtBankAdd,stxtAccNo,btnReturn,btnClear,ddPendingDate,ddReasonForReturn"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
