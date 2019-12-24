<%-- 
    Document   : cashHandlingCharge
    Created on : Jun 22, 2017, 12:54:15 PM
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
            <title>Cash handling charge</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="LoanInterestCalculation">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashHandlingCharge.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Cash handling charge"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CashHandlingCharge.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{CashHandlingCharge.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText value="Branch Wise:" styleClass="label"/>
                            <h:selectOneListbox id="branch" size="1" value="#{CashHandlingCharge.branch}" styleClass="ddlist">
                                <f:selectItems value="#{CashHandlingCharge.branchList}" />
                            </h:selectOneListbox> 
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputText value="A/c Nature"styleClass="label" />
                            <h:selectOneListbox id="ddacNature" size="1" value="#{CashHandlingCharge.actNature}" styleClass="ddlist" style="width: 70px" >
                                <f:selectItems value="#{CashHandlingCharge.actNatureList}"/>
                                <a4j:support  action="#{CashHandlingCharge.getAcTypeByAcNature}" event="onchange" reRender="ddacType" oncomplete="setMask();"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblacType" styleClass="label"  value="A/c Type"/>
                            <h:selectOneListbox id="ddacType" styleClass="ddlist" size="1" style="width: 70px" value="#{CashHandlingCharge.acType}" >
                                <f:selectItems value="#{CashHandlingCharge.acTypeList}"/>
                                <a4j:support  action="#{CashHandlingCharge.setDateAllAccount}" event="onchange" reRender="lblMsg,Row3,btnCalculate" oncomplete="setMask();" focus="#{rich:clientId('calToDate')}InputDate"/>
                            </h:selectOneListbox>  
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date :"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate" readonly="true" disabled="#{CashHandlingCharge.fromDisable}" value="#{CashHandlingCharge.frDt}" inputStyle="width:75px">
                                    <a4j:support event="onchanged"/>
                                </rich:calendar>
                                <h:outputLabel id="lblToDate" styleClass="label" value="To Date :"><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy"  id="calToDate" disabled="#{CashHandlingCharge.toDisable}" value="#{CashHandlingCharge.toDt}" inputStyle="width:75px">
                                    <a4j:support event="onchanged"/>
                                </rich:calendar>
                            </h:panelGrid>
                            <%--h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblDebitAmt" styleClass="label" value = "Debit Account :"/>
                                <h:outputText id="stxtDebitAmt" styleClass="output" value="#{CashHandlingCharge.debitAmt}"/>
                                <h:outputLabel id="lblTotalDebit" styleClass="label" value="Total Debit :"/>
                                <h:outputText id="stxtTotalDebit" styleClass="output" value="#{CashHandlingCharge.totalDebit}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="lblCreditAmt" styleClass="label" value = "Credit Account :" />
                                <h:outputText id="stxtCreditAmt" styleClass="output" value="#{CashHandlingCharge.creditAmt}"/>
                                <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit :" />
                                <h:outputText id="stxtTotalCredit" styleClass="output" value="#{CashHandlingCharge.totalCredit}"/>
                            </h:panelGrid--%>    
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                            <rich:dataTable value ="#{CashHandlingCharge.gridData}"
                                            rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"><h:outputText value="Details"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Charge Percentage" /></rich:column>
                                        <rich:column><h:outputText value="Charge Min Limit" /></rich:column>
                                        <rich:column><h:outputText value="Charge Max Limit" /></rich:column>
                                        <rich:column><h:outputText value="Deposit Amount" /></rich:column>
                                        <rich:column><h:outputText value="Charge Amount" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                            <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                            <rich:column style="text-align:center;width:8%;"><h:outputText value="#{item.acNo}"/></rich:column>
                            <rich:column style="text-align:left;width:20%;"><h:outputText value="#{item.custName}"/></rich:column>
                            <rich:column style="text-align:right;width:8%;"><h:outputText value="#{item.chargeAmtPercet}"/></rich:column>
                            <rich:column style="text-align:right;width:8%;"><h:outputText value="#{item.chargeMinLimit}"/></rich:column>
                            <rich:column style="text-align:right;width:8%;"><h:outputText value="#{item.chargeMaxLimit}"/></rich:column>
                            <rich:column style="text-align:right;width:8%;"><h:outputText value="#{item.depositAmt}"/></rich:column>
                            <rich:column style="text-align:right;width:8%;"><h:outputText value="#{item.chargeAmt}"/></rich:column>
                            <%--f:facet name="footer">
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
                                        <h:outputText value="#{CashHandlingCharge.totalDebit}"><f:convertNumber   pattern="$####.00"  /></h:outputText>
                                    </rich:column>
                                    <rich:column></rich:column>
                                </rich:columnGroup>
                            </f:facet--%> 
                        </rich:dataTable>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">  
                        <h:panelGroup id="btnPanel">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" disabled="#{CashHandlingCharge.disableCal}" action="#{CashHandlingCharge.calculate}"
                                                   oncomplete="if(#{CashHandlingCharge.reportFlag==true}){
                                                   #{rich:component('popUpRepPanel')}.show();
                                                   }
                                                   else if(#{CashHandlingCharge.reportFlag==false})
                                                   {
                                                   #{rich:component('popUpRepPanel')}.hide();
                                                   }"
                                                   reRender="lblMsg,leftPanel,InterestCal,btnPost,calFromDate,calToDate,popUpRepPanel,btnCalculate"/>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{CashHandlingCharge.disablePost}" action="#{CashHandlingCharge.Post}"
                                                   reRender="msgRow,InterestCal,Row1,Row2,Row3,Row4,Row5,btnPost"/>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CashHandlingCharge.refresh}" reRender="lblMsg,leftPanel,InterestCal,calFromDate,calToDate,btnPost,btnCalculate" />
                                <a4j:commandButton id="btnExit" value="Exit" action="#{CashHandlingCharge.exitBtnAction}"/>
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
                        <h:outputText value="Cash Handling Charges" style="text-align:center;"/>
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
                                <a4j:include viewId="#{CashHandlingCharge.viewID}" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
