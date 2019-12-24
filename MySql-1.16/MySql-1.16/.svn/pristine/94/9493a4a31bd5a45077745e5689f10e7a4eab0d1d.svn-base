<%-- 
    Document   : centralizedMinBalanceCharges
    Created on : 24 Dec, 2018, 2:47:11 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Centralized Min Balance charges</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CentralizedMinimumBalanceCharge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblMinBalanceCharges" styleClass="headerLabel" value="Centralized Min Balance Charges"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CentralizedMinimumBalanceCharge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{CentralizedMinimumBalanceCharge.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel0" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="lblBranchWise" styleClass="label" style="width: 100px" value="Branch Wise"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="branchHeader" size="1"  value="#{CentralizedMinimumBalanceCharge.branchCode}" styleClass="ddlist">
                                <f:selectItems value="#{CentralizedMinimumBalanceCharge.branchCodeList}" />
                            </h:selectOneListbox>
                        <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :" />
                        <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width:80px"  value="#{CentralizedMinimumBalanceCharge.acctType}" >
                            <f:selectItems   value="#{CentralizedMinimumBalanceCharge.accountype}"/>
                            <a4j:support action="#{CentralizedMinimumBalanceCharge.accountToCredit}" event="onchange" ajaxSingle="true"  focus="#{rich:clientId('fromDt')}InputDate"
                                         reRender="gridPanel103,txtdrAmt,txtcrAmt,stxtMsg,txtcrAcct,acctDebit,btnCalculation,btnPost" limitToList="true" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel1" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="lblFromDate" styleClass="label"  value="From Date :" />
                        <rich:calendar datePattern="dd/MM/yyyy" id="fromDt" value="#{CentralizedMinimumBalanceCharge.fromDate}" focus="#{rich:clientId('toDt')}InputDate" inputSize="10"/>
                        <h:outputLabel id="lblToDate" styleClass="label"  value="To Date :"/>
                        <rich:calendar datePattern="dd/MM/yyyy" id="toDt" value="#{CentralizedMinimumBalanceCharge.toDate}"  focus="btnCalculation" inputSize="10"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel20" style="width:100%;text-align:center;color:purple;" styleClass="row1">
                        <h:outputLabel id="lblMinimumBalanceCalculation"  style="font-size:15px;" styleClass="label" value="Minimum Balance Calculation"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel2" style="width:100%;" styleClass="row2">
                        <h:outputLabel id="lblAcctToBeDebited" styleClass="label"  value="A/c To Be Debited :" />
                        <h:outputText id="acctDebit" styleClass="output" value="#{CentralizedMinimumBalanceCharge.accoutToDebited}"/>
                        <h:outputLabel id="lblDebitAmount" styleClass="label"  value="Debit Amount :"/>
                        <h:inputText id="txtdrAmt" style="width:110px" value="#{CentralizedMinimumBalanceCharge.debitAmount}" styleClass="input" disabled="#{CentralizedMinimumBalanceCharge.amountDisable}">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Panel3" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="lblAcctToBeCredited" styleClass="label"  value="A/c To Be Credited :"/>
                        <h:inputText id="txtcrAcct" style="width:110px" value="#{CentralizedMinimumBalanceCharge.glAccountNo}" maxlength="12" styleClass="input" disabled="#{CentralizedMinimumBalanceCharge.glDisable}"/>
                        <h:outputLabel id="lblCreditAmount" styleClass="label"  value="Credit Amount :"/>
                        <h:inputText id="txtcrAmt"  style="width:110px" value="#{CentralizedMinimumBalanceCharge.creditAmount}" styleClass="input" disabled="#{CentralizedMinimumBalanceCharge.amountDisable}">
                            <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col7" columns="1" id="gridPanel103" width="100%" styleClass="row2">
                        <rich:dataTable value ="#{CentralizedMinimumBalanceCharge.minBalance}"
                                        rowClasses="row1, row2" id="taskList"  rows="5" columnsWidth="100" rowKeyVar="row" var ="item"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"><h:outputText value="Detail"/></rich:column>
                                    <rich:column breakBefore="true" width="8%"><h:outputText value="S.No." /> </rich:column>
                                    <rich:column width="25%"><h:outputText value="Account No" /></rich:column>
                                    <rich:column width="10%"><h:outputText value="" /></rich:column>
                                    <rich:column width="8%"><h:outputText value="" /></rich:column>
                                    <rich:column width="8%"><h:outputText value="" /></rich:column>
                                    <rich:column width="20%"><h:outputText value="Charges" /></rich:column>
                                    <rich:column width="25"><h:outputText value="Min Balance Charge" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{item.accountNo}"/></rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.july}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.transaction}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.limit}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;"><h:outputText value="#{item.charges}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column style="text-align:center;"><h:outputText value="#{item.minBalnceCharge}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />
                        <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Post Data?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="yes" ajaxSingle="true" action="#{CentralizedMinimumBalanceCharge.postBtnAction}"
                                                                   oncomplete="#{rich:component('postPanel')}.hide();
                                                                   if(#{CentralizedMinimumBalanceCharge.calcFlag1==true}){
                                                                   #{rich:component('popUpRepPanel')}.show();
                                                                   }
                                                                   else if(#{CentralizedMinimumBalanceCharge.calcFlag1==false})
                                                                   {
                                                                   #{rich:component('popUpRepPanel')}.hide();
                                                                   }"
                                                                   reRender="stxtMsg,btnPost,popUpRepPanel"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton  id="btnCalculation" disabled="#{CentralizedMinimumBalanceCharge.calDisable}" value="Calculation" oncomplete="if(#{CentralizedMinimumBalanceCharge.calcFlag==true}){
                                                #{rich:component('popUpRepPanel')}.show();
                                                }
                                                else if(#{CentralizedMinimumBalanceCharge.calcFlag==false})
                                                {
                                                #{rich:component('popUpRepPanel')}.hide();
                                                }"  action="#{CentralizedMinimumBalanceCharge.calculateBtnAction}" focus="btnPost" reRender="stxtMsg,gridPanel103,txtdrAmt,txtcrAmt,btnPost,acctDebit,txtcrAcct,btnCalculation,txtcrAcct,popUpRepPanel" />
                            <a4j:commandButton id="btnPost" disabled="#{CentralizedMinimumBalanceCharge.postDisable}" value="Post"  oncomplete="#{rich:component('postPanel')}.show()" reRender="stxtMsg,btnCalculation,btnPost" />
                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{CentralizedMinimumBalanceCharge.resetValue}" focus="ddAccountType" reRender="mainPanel" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CentralizedMinimumBalanceCharge.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Minimum Balance Charges" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:outputLink  id="closelink" onclick="#{rich:component('popUpRepPanel')}.hide()">
                        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                    </h:outputLink>
                </f:facet>  
                <table width="100%">
                    <tbody>
                        <tr>
                            <td>
                                <a4j:include viewId="#{CentralizedMinimumBalanceCharge.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>