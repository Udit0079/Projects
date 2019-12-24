<%-- 
    Document   : theftInterestCal
    Created on : Sep 17, 2015, 4:40:12 PM
    Author     : Admin
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
            <title>Thrift Interest Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="ThreftInterestCalculation">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TheftInterestCal.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Thrift Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TheftInterestCal.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{TheftInterestCal.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAllAccount" styleClass="label" />
                            <h:selectOneRadio id="ddAllAccount" style="width:140px;" styleClass="label" value="#{TheftInterestCal.allAccount}">
                                <f:selectItems value="#{TheftInterestCal.allAccountList}"/>
                                <a4j:support  action="#{TheftInterestCal.disableAcctType()}" event="onchange"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost" focus="ddAccountType"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 50px" disabled="#{TheftInterestCal.disableAcctType}" value="#{TheftInterestCal.accountType}">
                                    <f:selectItems value="#{TheftInterestCal.accountTypeList}"/>
                                    <a4j:support  action="#{TheftInterestCal.setDateAllAccount}" event="onblur"
                                                  reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal" focus="btnCalculate"/>
                                </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="display:#{TheftInterestCal.gridStyle};width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAccountWise" styleClass="label" />
                            <h:selectOneRadio id="ddAccountWise" style="width:140px;" styleClass="label" value="#{TheftInterestCal.accountWise}">
                                <f:selectItems value="#{TheftInterestCal.accountWiseList}"/>
                                <a4j:support  action="#{TheftInterestCal.disableAcctTypeNo}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,btnPost"/>
                            </h:selectOneRadio>
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No. :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup layout="block">
                                    <h:inputText id="txtAccountNo" styleClass="input" maxlength="12" value="#{TheftInterestCal.oldAccNo}" disabled="#{TheftInterestCal.disableAcctNo}" onkeyup="this.value=this.value.toUpperCase();" size="12">
                                        <a4j:support  action="#{TheftInterestCal.setDateAccountWise}" event="onblur"
                                                      reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,stxtAccNo" focus="#{rich:clientId('calToDate')}InputDate"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAccNo" styleClass="output" value="#{TheftInterestCal.accountNo}"/>
                                </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="RowRoi" style="display:#{TheftInterestCal.gridStyle1};width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblRoi" styleClass="label" value="Rate Of Interest"/>
                            <h:inputText id="txtRoi" styleClass="input" value="#{TheftInterestCal.roi}" disabled="#{TheftInterestCal.disableRoi}" maxlength="5" size="12">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>    
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate" readonly="true" disabled="#{TheftInterestCal.fromDisable}" value="#{TheftInterestCal.fromDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate" disabled="#{TheftInterestCal.toDisable}" value="#{TheftInterestCal.toDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{TheftInterestCal.debitAmt}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{TheftInterestCal.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{TheftInterestCal.creditAmt}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{TheftInterestCal.totalCredit}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2" style="display:#{TheftInterestCal.gridStyle};">
                        <rich:dataTable value ="#{TheftInterestCal.intCalc}"
                            rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="14"><h:outputText value="Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                    <rich:column><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="From Date" /></rich:column>
                                    <rich:column><h:outputText value="To Date" /></rich:column>
                                    <rich:column><h:outputText value="Sb ROI" /></rich:column>
                                    <rich:column><h:outputText value="Fd ROI" /></rich:column>
                                    <rich:column><h:outputText value="Closing Bal" /></rich:column>
                                    <rich:column><h:outputText value="Sb Product" /></rich:column>
                                    <rich:column><h:outputText value="Fd Product" /></rich:column>
                                    <rich:column><h:outputText value="Sb Int Amt" /></rich:column>
                                    <rich:column><h:outputText value="Fd Int Amt" /></rich:column>
                                    <rich:column><h:outputText value="Total Int Amt" /></rich:column>
                                    <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.fdRoi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.closingBal}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.product}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.fdProduct}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.sbInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.fdInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;"><h:outputText value="#{item.intTableCode}"/></rich:column>
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
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column style="text-align:right;">
                                        <h:outputText value="#{TheftInterestCal.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                    </rich:column>
                                    <rich:column></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal1"  width="100%" styleClass="row2" style="display:#{TheftInterestCal.gridStyle1};">
                        <rich:dataTable value ="#{TheftInterestCal.intCalc}"
                            rowClasses="row1, row2" id = "InterestCalTable1" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="8"><h:outputText value="Details"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="S.No"/> </rich:column>
                                    <rich:column><h:outputText value="Account No." /></rich:column>
                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column><h:outputText value="From Date" /></rich:column>
                                    <rich:column><h:outputText value="To Date" /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="Product" /></rich:column>
                                    <rich:column><h:outputText value="Int Amt" /></rich:column>                                    
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                            <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.roi}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.product}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right;">
                                <h:outputText value="#{item.totalInt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <f:facet name="footer">
                                <rich:columnGroup style="background-color: #b0c4de;">
                                    <rich:column>Totals</rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>
                                    <rich:column></rich:column>                                    
                                    <rich:column style="text-align:right;">
                                        <h:outputText value="#{TheftInterestCal.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                    </rich:column>                                    
                                </rich:columnGroup>
                            </f:facet>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="InterestCalTable1" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">  
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" action="#{TheftInterestCal.calculate}" oncomplete="if(#{TheftInterestCal.reportFlag==true}){
                                                   #{rich:component('popUpRepPanel')}.show(); } else if(#{TheftInterestCal.reportFlag==false}){
                                                   #{rich:component('popUpRepPanel')}.hide();}" reRender="lblMsg,leftPanel,InterestCal,btnPost,calFromDate,calToDate,popUpRepPanel,InterestCal1,btnCalculate,txtRoi"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{TheftInterestCal.disablePost}" action="#{TheftInterestCal.Post}"
                                                   reRender="msgRow,InterestCal,Row1,Row2,Row3,Row4,Row5,btnPost,InterestCal1,btnCalculate"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{TheftInterestCal.refresh}" reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate,btnPost,InterestCal1,txtRoi" focus="ddAllAccount"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{TheftInterestCal.exitBtnAction}" reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate,btnPost,InterestCal1,txtRoi"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
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
                                <a4j:include viewId="#{TheftInterestCal.viewID}" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
