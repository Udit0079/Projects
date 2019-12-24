<%--
    Document   : InterestCalculation
    Created on : 7 Dec, 2010, 2:06:48 PM
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
            <title>Loan Penal Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanPenalInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Penal Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanPenalInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{LoanPenalInterestCalculation.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAllAccount" styleClass="label" />
                            <h:selectOneRadio id="ddAllAccount" style="width:140px;" styleClass="label" value="#{LoanPenalInterestCalculation.allAccount}">
                                <f:selectItems value="#{LoanPenalInterestCalculation.allAccountList}"/>
                                <a4j:support  action="#{LoanPenalInterestCalculation.disableAcctType}" event="onchange"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type"/>
                            <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 100px" disabled="#{LoanPenalInterestCalculation.disableAcctType}" value="#{LoanPenalInterestCalculation.accountType}">
                                <f:selectItems value="#{LoanPenalInterestCalculation.accountTypeList}"/>
                                <a4j:support  action="#{LoanPenalInterestCalculation.setDateAllAccount}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal"/>
                            </h:selectOneListbox>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAccountWise" styleClass="label" />
                            <h:selectOneRadio id="ddAccountWise" style="width:140px;" styleClass="label" value="#{LoanPenalInterestCalculation.accountWise}">
                                <f:selectItems value="#{LoanPenalInterestCalculation.accountWiseList}"/>
                                <a4j:support  action="#{LoanPenalInterestCalculation.disableAcctTypeNo}" event="onchange"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No"/>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtAccountNo" styleClass="input" style="width: 90px" value="#{LoanPenalInterestCalculation.oldAccNo}" disabled="#{LoanPenalInterestCalculation.disableAcctNo}">
                                    <a4j:support  action="#{LoanPenalInterestCalculation.setDateAccountWise}" event="onblur"
                                                  reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{LoanPenalInterestCalculation.accountNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate" disabled="#{LoanPenalInterestCalculation.fromDisable}" value="#{LoanPenalInterestCalculation.fromDate}" inputSize="12">
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate" disabled="#{LoanPenalInterestCalculation.toDisable}" value="#{LoanPenalInterestCalculation.toDate}" inputSize="12">
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{LoanPenalInterestCalculation.debitAmt}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{LoanPenalInterestCalculation.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{LoanPenalInterestCalculation.creditAmt}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{LoanPenalInterestCalculation.totalCredit}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{LoanPenalInterestCalculation.intCalc}"
                                            rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Closing Bal" /></rich:column>
                                        <rich:column><h:outputText value="Product" /></rich:column>
                                        <rich:column><h:outputText value="Interest Amt" /></rich:column>
                                        <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                                        <rich:column><h:outputText value="Details" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.sNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.firstDt}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.lastDt}"/></rich:column>
                                <rich:column style="text-align:right;">
                                    <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;">
                                    <h:outputText value="#{item.closingBal}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;">
                                    <h:outputText value="#{item.product}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;">
                                    <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.intTableCode}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.details}"/></rich:column>
                                <f:facet name="footer">
                                    <rich:columnGroup style="background-color: #b0c4de;">

                                        <rich:column>Totals</rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{LoanPenalInterestCalculation.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCalculate" value="Calculate" action="#{LoanPenalInterestCalculation.calculate}"
                                               oncomplete="if(#{LoanPenalInterestCalculation.reportFlag==true}){
                                                   #{rich:component('popUpRepPanel')}.show();
                                                   }
                                                   else if(#{LoanPenalInterestCalculation.reportFlag==false})
                                                   {
                                                   #{rich:component('popUpRepPanel')}.hide();
                                                   }"
                                              reRender="lblMsg,leftPanel,InterestCal,btnPost,calFromDate,calToDate,popUpRepPanel">
                             </a4j:commandButton>
                            <a4j:commandButton id="btnPost" value="Post" disabled="#{LoanPenalInterestCalculation.disablePost}" action="#{LoanPenalInterestCalculation.Post}"
                                              reRender="msgRow,InterestCal,Row1,Row2,Row3,Row4,Row5,btnPost">
                               
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{LoanPenalInterestCalculation.refresh}"
                                              reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate,btnPost">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanPenalInterestCalculation.exitBtnAction}">
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
            </a4j:form>
            <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Loan Interest Calculation" style="text-align:center;"/>
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
                                <a4j:include viewId="#{LoanPenalInterestCalculation.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>