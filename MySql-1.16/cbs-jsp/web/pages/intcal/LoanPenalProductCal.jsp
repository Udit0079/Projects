<%-- 
    Document   : LoanPenalProductCal
    Created on : Mar 26, 2011, 3:00:41 PM
    Author     : admin
--%>

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
            <title>Loan Penal Product Calculation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanPenalProductCal.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Loan Penal Product Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanPenalProductCal.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{LoanPenalProductCal.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No"/>
                            <h:panelGroup layout="block"  style="width:100%;text-align:left;" >
                                <h:inputText id="txtAccountNo" styleClass="input" style="width: 100px" value="#{LoanPenalProductCal.oldAccNo}" maxlength="#{LoanPenalProductCal.acNoMaxLen}">
                                    <a4j:support  action="#{LoanPenalProductCal.setDateAccountWise}" event="onblur"
                                                  reRender="calFromDate,calToDate,lblMsg,InterestCal,stxtAccNo"/>
                                </h:inputText>
                                <h:outputText id="stxtAccNo" styleClass="output" value="#{LoanPenalProductCal.accountNo}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblAccountWise" styleClass="label" />
                            <h:outputLabel id="lblAccountWise1" styleClass="label" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblFromDate" styleClass="label" value = "From Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate"  value="#{LoanPenalProductCal.fromDate}" inputSize="12">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date"/>
                            <rich:calendar id="calToDate" value="#{LoanPenalProductCal.toDt}" datePattern="dd/MM/yyyy" inputSize="12">
                                <a4j:support event="onchanged"/>
                            </rich:calendar>

                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{LoanPenalProductCal.intCalc}"
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
                                        <rich:column><h:outputText value="No. of Days" /></rich:column>
                                        <rich:column><h:outputText value="Product" /></rich:column>
                                        <rich:column><h:outputText value="Interest Amt" /></rich:column>
                                        <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                                        <rich:column><h:outputText value="Details " /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.sNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.acNo}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.custName}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.firstDt}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.lastDt}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.roi}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.closingBal}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.product}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value="#{item.totalInt}"/></rich:column>
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
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{LoanPenalProductCal.totalNoOfDays}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{LoanPenalProductCal.totalProduct}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{LoanPenalProductCal.totalInt}"><f:convertNumber   pattern="####.00"  /></h:outputText>
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
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="btnCalculate" value="Calculate" action="#{LoanPenalProductCal.calculate}"
                                                   reRender="lblMsg,InterestCal,Row3,leftPanel" focus="btnRefresh">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{LoanPenalProductCal.refresh}"
                                                   reRender="lblMsg,txtAccountNo,InterestCal,calFromDate,calToDate,leftPanel,stxtAccNo" focus="btnExit">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{LoanPenalProductCal.exitBtnAction}" reRender="lblMsg,txtAccountNo,InterestCal,calFromDate,calToDate,leftPanel,stxtAccNo">
                                </a4j:commandButton>
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
        </body>
    </html>
</f:view>

