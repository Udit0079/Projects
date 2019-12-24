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
            <title>Adhoc Loan Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanInterestTestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Adhoc Loan Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanInterestTestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{LoanInterestTestCalculation.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAllAccount" styleClass="label" />
                            <h:selectOneRadio id="ddAllAccount" style="width:140px;" styleClass="label" value="#{LoanInterestTestCalculation.allAccount}">
                                <f:selectItems value="#{LoanInterestTestCalculation.allAccountList}"/>
                                <a4j:support  action="#{LoanInterestTestCalculation.disableAcctType}" event="onchange"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost" focus="ddAccountType"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 100px" disabled="#{LoanInterestTestCalculation.disableAcctType}" value="#{LoanInterestTestCalculation.accountType}">
                                <f:selectItems value="#{LoanInterestTestCalculation.accountTypeList}"/>
                                <a4j:support  action="#{LoanInterestTestCalculation.setDateAllAccount}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal" focus="btnCalculate"/>
                            </h:selectOneListbox>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAccountWise" styleClass="label" />
                            <h:selectOneRadio id="ddAccountWise" style="width:140px;" styleClass="label" value="#{LoanInterestTestCalculation.accountWise}">
                                <f:selectItems value="#{LoanInterestTestCalculation.accountWiseList}"/>
                                <a4j:support  action="#{LoanInterestTestCalculation.disableAcctTypeNo}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost" focus="ddAccountNo"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No. :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtAccountNo" styleClass="input" style="width: 90px" maxlength="#{LoanInterestTestCalculation.acNoMaxLen}" value="#{LoanInterestTestCalculation.oldAccNo}" disabled="#{LoanInterestTestCalculation.disableAcctNo}">
                                    <a4j:support  action="#{LoanInterestTestCalculation.setDateAccountWise}" event="onblur"
                                                  reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,stxtAccNo" focus="#{rich:clientId('calToDate')}InputDate"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{LoanInterestTestCalculation.accountNo}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate"  value="#{LoanInterestTestCalculation.fromDate}" >
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate"  value="#{LoanInterestTestCalculation.toDate}" >
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{LoanInterestTestCalculation.debitAmt}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{LoanInterestTestCalculation.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{LoanInterestTestCalculation.creditAmt}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{LoanInterestTestCalculation.totalCredit}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{LoanInterestTestCalculation.intCalc}"
                                            rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Closing Bal" /></rich:column>
                                        <rich:column><h:outputText value="Product" /></rich:column>
                                        <rich:column><h:outputText value="Interest Amt" /></rich:column>
                                        <rich:column><h:outputText value="Dr/Cr Flag" /></rich:column>
                                        <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                                <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                                <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                                <rich:column style="text-align:right;width:5%;">
                                    <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;width:10%;">
                                    <h:outputText value="#{item.closingBal}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;width:10%;">
                                    <h:outputText value="#{item.product}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:right;width:10%;">
                                    <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column style="text-align:left;width:8%;"><h:outputText value="#{item.drCrFlag}"/></rich:column>
                                <rich:column style="text-align:center;width:12%;"><h:outputText value="#{item.intTableCode}"/></rich:column>
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
                                            <h:outputText value="#{LoanInterestTestCalculation.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:left;">
                                            <h:outputText value="#{LoanInterestTestCalculation.drCrFlag}"></h:outputText>
                                        </rich:column>
                                        <rich:column></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" actionListener="#{LoanInterestTestCalculation.calculate}"
                                                   oncomplete="if(#{LoanInterestTestCalculation.reportFlag==true}){
                                                                                                                    #{rich:component('popUpRepPanel')}.show();
                                                                                                                  }
                                                                                                                  else if(#{LoanInterestTestCalculation.reportFlag==false})
                                                                                                                  {
                                                                                                                    #{rich:component('popUpRepPanel')}.hide();
                                                                                                                  }"
                                                   reRender="lblMsg,leftPanel,btnPost,InterestCal,calFromDate,calToDate,popUpRepPanel"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{LoanInterestTestCalculation.disablePost}" action="#{LoanInterestTestCalculation.Post}"
                                                   reRender="msgRow,InterestCal,InterestCalTable,Row1,Row2,Row3,Row4,Row5,btnPost"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanInterestTestCalculation.refresh}" reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate" focus="ddAllAccount"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{LoanInterestTestCalculation.exitBtnAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

            </a4j:form>
                 <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Interest Calculation Report" style="text-align:center;"/>
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
                                <a4j:include viewId="#{LoanInterestTestCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>