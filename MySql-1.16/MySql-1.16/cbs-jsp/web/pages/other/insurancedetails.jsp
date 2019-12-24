<%-- 
    Document   : insurancedetails
    Created on : 21 Apr, 2014, 2:26:40 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Insurance And Well-Fare Creation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{insuranceDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" styleClass="headerLabel" value="Insurance And WellFare Creation"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{insuranceDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="messagePanel" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMessage" styleClass="error" value="#{insuranceDetails.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="commonComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblTxnType" styleClass="label" value="Transaction Type"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddTxnType" styleClass="ddlist" size="1" value="#{insuranceDetails.txnType}">
                            <f:selectItems value="#{insuranceDetails.txnTypeList}"/>
                            <a4j:support event="onchange" action="#{insuranceDetails.processTxnAction}" reRender="custNameId,stxtMessage,lblAcno,txtAcno,lblMemNo,txtMemNo,insuredComponentPanel,insDateComponentPanel,wellComponentPanel,lblodLomit,txtodLomit" oncomplete="setMask();"/>
                        </h:selectOneListbox>
                        <h:panelGroup>
                            <h:outputLabel id="lblAcno" styleClass="label" value="Account No." style="display:#{insuranceDetails.acnoEnable}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:outputLabel id="lblMemNo" styleClass="label" value="Member No." style="display:#{insuranceDetails.memEnable}"><font class="required" color="red">*</font></h:outputLabel>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:inputText id="txtAcno" styleClass="input" style="width:100px;display:#{insuranceDetails.acnoEnable}" maxlength="12" value="#{insuranceDetails.acno}">
                                <a4j:support event="onblur" action="#{insuranceDetails.processAccountAction}" reRender="custNameId,txtodLomit,btnHtml,stxtMessage,tablePanel,taskList" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:inputText id="txtMemNo" styleClass="input" style="width:100px;display:#{insuranceDetails.memEnable}" maxlength="12" value="#{insuranceDetails.memNo}">
                                <a4j:support event="onblur" action="#{insuranceDetails.processMemberNo}" reRender="custNameId,stxtMessage,tablePanel,taskList,txtRegDate,txtRetDate,btnHtml" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputText id="custNameId" value="#{insuranceDetails.custName}" styleClass="output" style="color:green"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblodLomit" styleClass="label" value="Amount" style="display:#{insuranceDetails.odLimitEnable}"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtodLomit" styleClass="input" style="width:100px;display:#{insuranceDetails.odLimitEnable}" maxlength="12" value="#{insuranceDetails.odLimit}">
                            <%--a4j:support event="onblur" action="#{insuranceDetails.processAccountAction}" reRender="stxtMessage,tablePanel,taskList" oncomplete="setMask();"/--%>
                        </h:inputText>     
                    </h:panelGrid>
                    <h:panelGrid id="insuredComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{insuranceDetails.insEnable};" styleClass="row1" width="100%">
                        <h:outputLabel id="lblInsuredAmt" styleClass="label" value="Insured Amount"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInsuredAmt" styleClass="input" style="width:100px;" value="#{insuranceDetails.insuredAmount}"/>
                        <h:outputLabel id="lblInsPremium" styleClass="label" value="Insurance Premium"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtInsPremium" styleClass="input" style="width:100px;" value="#{insuranceDetails.insurancePremium}"/>
                        <h:outputLabel id="lblPolicyNo" styleClass="label" value="Policy No"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtPolicyNo" styleClass="input" style="width:100px;" value="#{insuranceDetails.policyNo}" maxlength="30"/>
                    </h:panelGrid>
                    <h:panelGrid id="insDateComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{insuranceDetails.insEnable}" styleClass="row2" width="100%">
                        <h:outputLabel id="lblStartDt" styleClass="label" value="Start Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtStartDt" styleClass="input issueDt" style="width:70px;" value="#{insuranceDetails.startDt}"/>
                        <h:outputLabel id="lblTillDt" styleClass="label" value="Till Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtTillDt" styleClass="input issueDt" style="width:70px;" value="#{insuranceDetails.tillDt}">
                            <a4j:support event="onblur" action="#{insuranceDetails.createInsuranceDetails}" reRender="stxtMessage,tablePanel,taskList,btnHtml" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="wellComponentPanel" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;display:#{insuranceDetails.wellEnable}" styleClass="row1" width="100%">
                        <h:outputLabel id="lblRegDate" styleClass="label" value="Registration Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRegDate" styleClass="input issueDt" style="width:70px;" value="#{insuranceDetails.memRegDt}" disabled="#{insuranceDetails.disFrDt}"/>
                        <h:outputLabel id="lblRetDate" styleClass="label" value="Retired Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtRetDate" styleClass="input issueDt" style="width:70px;" value="#{insuranceDetails.memRetiredDt}" disabled="#{insuranceDetails.disToDt}"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnHtml" value="Schedule" oncomplete="#{rich:component('savePanel')}.show();" reRender="stxtMessage,savePanel" disabled="#{insuranceDetails.btnActive}"/>
                            <a4j:commandButton action="#{insuranceDetails.btnRefreshAction}" id="btnRefresh" value="Refresh" reRender="stxtMessage,mainPanel" oncomplete="setMask();" focus="ddTxnType"/>
                            <a4j:commandButton action="#{insuranceDetails.btnExitAction}" id="btnExit" value="Exit" reRender="mainPanel" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <rich:dataTable value="#{insuranceDetails.tableDataList}" var="dataItem"
                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="11"><h:outputText value="Detail List"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="A/c No" /></rich:column>
                                    <rich:column><h:outputText value="S.No" /></rich:column>
                                    <rich:column><h:outputText value="Due Date" /></rich:column>
                                    <rich:column><h:outputText value="Ins. Amount / WellFare Fund" /></rich:column>
                                    <rich:column><h:outputText value="Premium Amt / Paid Amt"/></rich:column>
                                    <rich:column><h:outputText value="Status"/></rich:column>
                                    <rich:column><h:outputText value="Payment Date"/></rich:column>
                                    <rich:column><h:outputText value="Enter By"/></rich:column>
                                    <rich:column><h:outputText value="Periodicity"/></rich:column>
                                    <rich:column><h:outputText value="Excess Amount"/></rich:column>
                                    <rich:column><h:outputText value="Policy No."/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.acno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.dueDt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.premiumAmount}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.paymentDt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.enteryBy}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.periodicity}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.excessAmount}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.policyNo}" /></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="100"/>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:region id="btnActionGrid">
                <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnSaveYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to save this schedule ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnSaveYes" action="#{insuranceDetails.btnSaveAction}" oncomplete="#{rich:component('savePanel')}.hide();" reRender="stxtMessage,mainPanel"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnSaveNo" onclick="#{rich:component('savePanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnActionGrid"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>