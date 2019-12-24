<%-- 
    Document   : PdcEntryForm
    Created on : Jul 29, 2017, 11:03:21 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>PDC Processing</title>
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
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup>
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{PdcProcess.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="PDC Processing"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{PdcProcess.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{PdcProcess.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblOpt" styleClass="label" value="Option"/>
                        <h:selectOneListbox id="ddOption" styleClass="ddlist" size="1" value="#{PdcProcess.opt}">
                            <f:selectItems value="#{PdcProcess.optList}"/>.
                            <a4j:support action="#{PdcProcess.optProcess}" event="onblur" focus="#{PdcProcess.focusId}"
                                         reRender="stxtMessage,ddPendingRef,mainPanel,btnHtml,savePanel,confTxt" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblPendingRef" styleClass="label" value="Pending Reference"/>
                        <h:selectOneListbox id="ddPendingRef" styleClass="ddlist" size="1" value="#{PdcProcess.pendingRef}" disabled="#{PdcProcess.disRef}">
                            <f:selectItems value="#{PdcProcess.pRefList}"/>
                            <a4j:support action="#{PdcProcess.refProcess}" event="onblur" reRender="stxtMessage,mainPanel" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="panel2" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCrAcno" styleClass="label" value="Credit Account No."><font class="required" color="red">*</font></h:outputLabel>
                        <h:panelGroup layout="block" style="width:100%;text-align:left;">
                           <h:inputText id="txtCrAcno" styleClass="input" style="width:100px" maxlength="#{PdcProcess.acNoMaxLen}" value="#{PdcProcess.crAccountNo}" disabled="#{PdcProcess.disAccount}">
                                <a4j:support action="#{PdcProcess.crAccountProcess}" event="onblur" reRender="stxtMessage,stxtName,stxtCrAccNo" oncomplete="setMask();"/>
                            </h:inputText>
                           <h:outputText id="stxtCrAccNo" styleClass="output" value="#{PdcProcess.crAcno}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblName" styleClass="label" value="Customer Name"/>
                        <h:outputText id="stxtName" styleClass="output" value="#{PdcProcess.custName}"/>
                        <h:outputLabel id="lblBankCode" styleClass="label" value="Bank Code"/>
                        <h:panelGroup id="panelGrp3" layout="block">
                            <h:inputText id="txtBankCode1" styleClass="input" style="width:40px;" value="#{PdcProcess.bankCode1}" maxlength="3">
                                <a4j:support actionListener="#{PdcProcess.bankCode1LostFocus}" event="onblur" reRender="stxtMessage"/>
                            </h:inputText>
                            <h:inputText id="txtBankCode2" styleClass="input" style="width:40px;" value="#{PdcProcess.bankCode2}" maxlength="3">
                                <a4j:support actionListener="#{PdcProcess.bankCode2LostFocus}" event="onblur" reRender="stxtMessage"/>
                            </h:inputText>
                            <h:inputText id="txtBankCode3" styleClass="input" style="width:40px;" value="#{PdcProcess.bankCode3}" maxlength="3">
                                <a4j:support actionListener="#{PdcProcess.bankCode3LostFocus}" event="onblur" reRender="stxtMessage,txtBankName,txtBranchName"/>
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="panel3" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDrAcno" styleClass="label" value="Debit Account No."/>
                        <h:inputText id="txtDrAcno" styleClass="input" style="width:100px" maxlength="30" value="#{PdcProcess.drAccNo}" disabled="#{PdcProcess.disDrAccount}"/>
                        <h:outputLabel id="lblBankName" styleClass="label" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBankName" styleClass="input" style="width:100px" value="#{PdcProcess.bankName}" disabled="#{PdcProcess.disBankName}"/>
                        <h:outputLabel id="lblBranchName" styleClass="label" value="Branch Name"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtBranchName" styleClass="input" style="width:100px" value="#{PdcProcess.branchName}" disabled="#{PdcProcess.disBranchName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel4" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblIfsc" styleClass="label" value="IFSC Code."><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtIfsc" styleClass="input" style="width:100px" maxlength="11" value="#{PdcProcess.ifscCode}" disabled="#{PdcProcess.disIfscCode}"/>
                        <h:outputLabel id="lblAmount" styleClass="label" value="Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtAmt" styleClass="input" style="width:100px" value="#{PdcProcess.amt}" disabled="#{PdcProcess.disAmt}"/>
                        <h:outputLabel id="lblFreq" styleClass="label" value="Frequency"/>
                        <h:selectOneListbox id="ddFreq" styleClass="ddlist" size="1" value="#{PdcProcess.freq}" disabled="#{PdcProcess.disFreq}">
                            <f:selectItems value="#{PdcProcess.freqList}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="panel5" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblChqDt" styleClass="label" value="Cheque Start Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtChqDt" styleClass="input issueDt" style="width:70px" value="#{PdcProcess.chqDt}" disabled="#{PdcProcess.disChqDt}"/>
                        <h:outputLabel id="lblChqFrm" styleClass="label" value="Cheque No. From"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtChqFrm" styleClass="input" style="width:100px" maxlength="6" value="#{PdcProcess.chqFrm}" disabled="#{PdcProcess.disChqFrm}"/>
                        <h:outputLabel id="lblChqTo" styleClass="label" value="Cheque No. To"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtChqTo" styleClass="input" style="width:100px" maxlength="6" value="#{PdcProcess.chqTo}" disabled="#{PdcProcess.disChqTo}">
                            <a4j:support action="#{PdcProcess.getGridToSave}" event="onblur" reRender="stxtMessage,tablePanel,taskList" oncomplete="setMask();"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="#{PdcProcess.btnValue}" action="#{PdcProcess.createPopUpAlert}" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel,deletePanel,confTxt" disabled="#{PdcProcess.disSave}"/>
                            <a4j:commandButton id="btnDel" value="Delete" oncomplete="#{rich:component('deletePanel')}.show();" reRender="stxtMessage,savePanel,deletePanel" disabled="#{PdcProcess.disDelete}"/>
                            <a4j:commandButton action="#{PdcProcess.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="stxtMessage,mainPanel,tablePanel"/>
                            <a4j:commandButton action="#{PdcProcess.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{PdcProcess.tableDataList}" var="dataItem"
                                    rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="3"><h:outputText value="PDC Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No" /></rich:column>
                                        <rich:column><h:outputText value="Cheque No." /></rich:column>
                                        <rich:column><h:outputText value="Due Date." /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chqDate}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="100"/>
                        </a4j:region>
                    </h:panelGrid>     
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confTxt" styleClass="output" value="#{PdcProcess.btnMsg}"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnSaveYes" action="#{PdcProcess.btnSaveAction}" onclick="#{rich:component('savePanel')}.hide();" reRender="stxtMessage,mainPanel,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnDeleteYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog"/>
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Delete This Reference"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnDeleteYes" action="#{PdcProcess.btnDeleteAction}" onclick="#{rich:component('deletePanel')}.hide();" reRender="stxtMessage,mainPanel,tablePanel,taskList"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnDeleteNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>            
        </body>
    </html>
</f:view>

