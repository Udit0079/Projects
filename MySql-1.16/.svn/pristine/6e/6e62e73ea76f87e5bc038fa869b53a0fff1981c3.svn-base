<%-- 
    Document   : LoanInspectionCharge
    Created on : 27 Jan, 2011, 2:25:17 PM
    Author     : Zeeshan Waris
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
            <title>Loan Inspection Charges</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanInspectionCharge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblMinBalanceCharges" styleClass="headerLabel" value="Loan Inspection Charges"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanInspectionCharge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{LoanInspectionCharge.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel0" style="width:100%;" styleClass="row1">
                        <h:outputLabel id="lblStatus" styleClass="label" value="Status"/>
                        <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width: 100px"  value="#{LoanInspectionCharge.status}" >
                            <f:selectItems   value="#{LoanInspectionCharge.comboStatus}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type"/>
                        <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 80px"  value="#{LoanInspectionCharge.acctType}" >
                            <f:selectItems   value="#{LoanInspectionCharge.accountype}"/>
                            <a4j:support action="#{LoanInspectionCharge.accountToCredit}" event="onchange" ajaxSingle="true"  focus="#{rich:clientId('fromDt')}InputDate"
                                         reRender="gridPanel103,scroller,txtdrAmt,txtcrAmt,stxtMsg,txtcrAcct,acctDebit,btnCalculation,btnPost" limitToList="true" />

                        </h:selectOneListbox>
                    </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel1" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label"  value="From Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="fromDt" value="#{LoanInspectionCharge.fromDate}" focus="#{rich:clientId('toDt')}InputDate" inputSize="10"/>
                            <h:outputLabel id="lblToDate" styleClass="label"  value="To Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="toDt" value="#{LoanInspectionCharge.toDate}" inputSize="10">
                                <a4j:support action="#{LoanInspectionCharge.fromdt}" event="oninputblur"
                                             limitToList="true"  reRender="toDt" focus="btnCalculation" />
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel2" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblAcctToBeDebited" styleClass="label"  value="A/c To Be Debited"/>
                            <h:outputText id="acctDebit" styleClass="output" value="#{LoanInspectionCharge.accoutToDebited}"/>
                            <h:outputLabel id="lblDebitAmount" styleClass="label"  value="Debit Amount"/>
                            <h:inputText id="txtdrAmt" style="width:110px" value="#{LoanInspectionCharge.debitAmount}" styleClass="input" disabled="#{LoanInspectionCharge.amountDisable}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col13,col13,col13,col13" columns="4" id="Panel3" style="width:100%;" styleClass="row1">
                            <h:outputLabel id="lblAcctToBeCredited" styleClass="label"  value="A/c To Be Credited"/>
                            <h:inputText id="txtcrAcct" style="width:110px" value="#{LoanInspectionCharge.glAccountNo}" maxlength="12" styleClass="input" disabled="#{LoanInspectionCharge.glDisable}"/>
                            <h:outputLabel id="lblCreditAmount" styleClass="label"  value="Credit Amount"/>
                            <h:inputText id="txtcrAmt"  style="width:110px" value="#{LoanInspectionCharge.creditAmount}" styleClass="input" disabled="#{LoanInspectionCharge.amountDisable}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                            </h:inputText>
                        </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col7" columns="1" id="gridPanel103" width="100%" styleClass="row2">
                        <rich:dataTable value ="#{LoanInspectionCharge.minBalance}"
                                        rowClasses="row1, row2" id="taskList"  rows="5" columnsWidth="100" rowKeyVar="row" var ="item"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="6"></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No." /> </rich:column>
                                    <rich:column><h:outputText value="Account No" /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="Trans" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                    <rich:column><h:outputText value="Penalty" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>


                            <rich:column><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.accountNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column><h:outputText value="#{item.transaction}"/></rich:column>
                            <rich:column><h:outputText value="#{item.limit}"/></rich:column>
                            <rich:column ><h:outputText value="#{item.charges}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList" maxPages="20" />

                        <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                            <f:facet name="header">
                                <h:outputText value="Confirmation DialogBox" />
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
                                                <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{LoanInspectionCharge.postBtnAction}"
                                                                   onclick="#{rich:component('postPanel')}.hide();" reRender="stxtMsg,subbodyPanel1,btnPost" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton id="No" value="No" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>

                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel20" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblMinimumBalanceCalculation"  style="font-size:15px;" styleClass="label" value="Instructions : This will deduct flat inspection charges from all accounts of the account type selected for the period choosen between two dates"/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton  id="btnCalculation" disabled="#{LoanInspectionCharge.calDisable}" value="Calculation" action="#{LoanInspectionCharge.calculateBtnAction}" focus="btnPost" reRender="stxtMsg,gridPanel103,txtdrAmt,txtcrAmt,btnPost,acctDebit,txtcrAcct,btnCalculation,txtcrAcct" >
                            </a4j:commandButton>

                            <a4j:commandButton id="btnPost" disabled="#{LoanInspectionCharge.postDisable}" value="Post"  oncomplete="#{rich:component('postPanel')}.show()" reRender="stxtMsg,subbodyPanel1,btnCalculation,btnPost" >
                            </a4j:commandButton>

                            <a4j:commandButton  id="btnRefresh" value="Refresh" action="#{LoanInspectionCharge.resetValue}" focus="ddStatus" reRender="mainPanel" >
                            </a4j:commandButton>

                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanInspectionCharge.btnExit}" reRender="mainPanel">
                            </a4j:commandButton>


                        </h:panelGroup>

                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages>

            </a4j:form>
        </body>
    </html>
</f:view>