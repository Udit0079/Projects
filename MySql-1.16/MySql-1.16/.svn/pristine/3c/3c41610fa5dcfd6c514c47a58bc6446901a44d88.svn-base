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
            <title>SB Interest Provision Calculation</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var SbInterestCalculation;
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="SbInterestCalculation"/>
            <a4j:form id="SbInterestCalculation">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{SbInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="SB Interest Provision Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SbInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{SbInterestCalculation.message}"/>
                        </h:panelGrid>
                       
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAccountWise" styleClass="label" value="Interest Option : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccountWise" styleClass="ddlist" size="1" style="width:100px;" value="#{SbInterestCalculation.interestOption}">
                                <f:selectItems value="#{SbInterestCalculation.interestOptionList}"/>
                                <a4j:support  action="#{SbInterestCalculation.setIntOptions}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,Row12,InterestCal" focus="#{SbInterestCalculation.focusEle}"/>
                            </h:selectOneListbox>
                            
                            <h:outputLabel id="lblAccountStatus" styleClass="label" value="Account Status : "><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAcctStatus" styleClass="ddlist" size="1" style="width:100px;" disabled="#{!SbInterestCalculation.allAcc}" value="#{SbInterestCalculation.acctStatus}">
                                <f:selectItems value="#{SbInterestCalculation.acctStatusList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row12" style="width:100%;text-align:left;" styleClass="row1">    
                            <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddAccountType" styleClass="ddlist" size="1" style="width: 50px" disabled="#{!SbInterestCalculation.allAcc}" value="#{SbInterestCalculation.accountType}">
                                <f:selectItems value="#{SbInterestCalculation.accountTypeList}"/>
                                <a4j:support  action="#{SbInterestCalculation.setDateAllAccount}" event="onblur"
                                              reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,ddAllAccount,ddAccountWise" focus="#{rich:clientId('calFromDate')}InputDate"/>
                            </h:selectOneListbox>
                            
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No. :"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtAccountNo" styleClass="input" maxlength="#{SbInterestCalculation.acNoMaxLen}" value="#{SbInterestCalculation.oldAccNo}" disabled="#{!SbInterestCalculation.accWise}" size="#{SbInterestCalculation.acNoMaxLen}">
                                    <a4j:support  action="#{SbInterestCalculation.setDateAccountWise}" event="onblur" 
                                                  reRender="msgRow,Row1,Row2,Row3,Row4,Row5,InterestCal,stxtAccNo" focus="#{rich:clientId('calFromDate')}InputDate"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{SbInterestCalculation.accountNo}" rendered="#{SbInterestCalculation.accWise}"/>  
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate" disabled="#{SbInterestCalculation.fromDisable}" value="#{SbInterestCalculation.fromDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate" value="#{SbInterestCalculation.toDate}" inputStyle="width:75px">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                            <h:outputText id="stxtDebitAmt" styleClass="output" value="#{SbInterestCalculation.debitAcc}"/>
                            <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                            <h:outputText id="stxtTotalDebit" styleClass="output" value="#{SbInterestCalculation.totalDebit}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                            <h:outputText id="stxtCreditAmt" styleClass="output" value="#{SbInterestCalculation.creditAcc}"/>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{SbInterestCalculation.totalCredit}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <a4j:region id="regionForCalcPost">
                            <rich:dataTable value ="#{SbInterestCalculation.intCalc}"
                                            rowClasses="gridrow1,gridrow2" id = "InterestCalTable" rows="10" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="From Date" /></rich:column>
                                        <rich:column><h:outputText value="To Date" /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="Closing Bal" /></rich:column>
                                        <rich:column><h:outputText value="Product" /></rich:column>
                                        <rich:column><h:outputText value="Interest Amt" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                                <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.firstDt}"/></rich:column>
                                <rich:column style="text-align:center;width:10%;"><h:outputText value="#{item.lastDt}"/></rich:column>
                                <rich:column style="text-align:right;width:7%;">
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
                                            <h:outputText value="#{SbInterestCalculation.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                        </rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                        <rich:modalPanel id="calculatePanel" autosized="true" width="300" onshow="#{rich:element('btnCalcYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="#{SbInterestCalculation.panelMsg}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" id="btnCalcYes" action="#{SbInterestCalculation.calculate}"
                                                                   oncomplete="#{rich:component('calculatePanel')}.hide();if(#{SbInterestCalculation.reportFlag==true})
                                                                                                                            {
                                                                                                                                #{rich:component('popUpRepPanel')}.show();
                                                                                                                            }
                                                                                                                            else if(#{SbInterestCalculation.reportFlag==false})
                                                                                                                            {
                                                                                                                                #{rich:component('popUpRepPanel')}.hide();
                                                                                                                            }"
                                                                   reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate,popUpRepPanel" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" onclick="#{rich:component('calculatePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" action="#{SbInterestCalculation.panelmsgShow}" oncomplete="#{rich:component('calculatePanel')}.show()"
                                                   reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{SbInterestCalculation.refresh}" reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate" focus="ddAllAccount"/>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{SbInterestCalculation.exitBtnAction}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                <rich:modalPanel id="wait" autosized="true" width="250" height="60" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;" >
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                    <h:outputText value="It May Take Some Time. Please Wait..." />
                </rich:modalPanel>
            </a4j:form>
                                        
              <rich:modalPanel id="popUpRepPanel" label="Form" width="1000" height="550" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="SB Interest Calculation Report" style="text-align:center;"/>
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
                                <a4j:include viewId="#{SbInterestCalculation.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>

        </body>
    </html>
</f:view>