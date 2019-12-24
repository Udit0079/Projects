<%-- 
    Document   : CashDenoModification
    Created on : 19 Nov, 2016, 2:25:57 PM
    Author     : root
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
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Cash Denomination Modification</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DenoModification.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Cash Denomination Modify"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DenoModification.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errormsg" width="100%" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="msg" value="#{DenoModification.msg}" styleClass="msg"/>
                        <h:outputText id="errmsg" value="#{DenoModification.errorMessage}" styleClass="error"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel1" columnClasses="col1,col1,col1,col1,col1,col13" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblAcno" value="Account No :" styleClass="label"/>
                        <h:inputText id="txtAcno" value="#{DenoModification.acno}" styleClass="input" style="width:75px">
                            <a4j:support event="onblur" action="#{DenoModification.getCashTxn}" reRender="gridPanel1,dataList,errmsg"/>
                        </h:inputText>
                        <h:outputLabel id="lblTotalAmt" value="Total Amount :" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtTotalAmount" value="#{DenoModification.totalAmount}" styleClass="input" style="width:75px" readonly="true"/> &nbsp;
                            <h:outputLabel value="Rs." styleClass="label"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblCurrencyAmt" value="Denomination Currency Amount :" styleClass="label"/>
                        <h:panelGroup>
                            <h:inputText id="txtTotalCurrencyAmount" value="#{DenoModification.currencyAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                            <h:outputLabel value="Rs." styleClass="label"/>
                        </h:panelGroup>                 
                    </h:panelGrid>
                    <h:panelGrid id="denomDtlPopUpForm" columns="1" style="width:100%;text-align:center;border:1px">
                        <h:panelGrid id="panel6" columnClasses="col1,col1,col1,col1,col1,col13" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDenomination" value="Denomination :" styleClass="label"/>
                            <h:panelGroup>
                                <h:inputText id="txtDenoValue" value="#{DenoModification.denoValue}" styleClass="input" style="width:73px" disabled="#{CashDenominationDtl.disableDenoNo}">
                                    <a4j:support event="onblur"/>
                                </h:inputText>
                                <h:outputLabel value="Rs." styleClass="label"/>
                            </h:panelGroup>
                            <%--h:outputLabel id="lblOldNewFlg" value="Currency Type :" styleClass="label"/>
                            <h:selectOneListbox id="ddOldNewFlg" styleClass="ddlist" size="1" value="#{DenoModification.selectOldNewFlg}">
                                <f:selectItems value="#{DenoModification.oldNewFlgList}"/>
                            </h:selectOneListbox--%>
                            <h:outputLabel id="lblNoOfNotes" value="No. of Notes / Coins :" styleClass="label"/>
                            <h:inputText id="txtDenoNo" value="#{DenoModification.denoNo}" styleClass="input" style="width:73px" disabled="#{DenoModification.disableDenoNo}">
                                <a4j:support event="onblur" action="#{DenoModification.onBlurDenominationNo}" focus="txtDenoValue" 
                                             reRender="errormsg,panel1,panel5,denominationTable,panel6,txtDenoValue,txtDenoNo" limitToList="true"/>
                            </h:inputText> 
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                        <h:panelGrid id="panel5" columnClasses="vtop"  columns="1" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable id="denominationTable"  rows="10" 
                                                value="#{DenoModification.denominationTable}" var="dataItem1" rowClasses="gridrow1,gridrow2" 
                                                columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="25"><h:outputText value="Denomination Detail" /></rich:column>
                                            <rich:column width="30%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                            <rich:column width="25%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                            <rich:column width="30%"><h:outputText value="Amount"/></rich:column>
                                            <%--rich:column width="30%"><h:outputText value="Currency Type"/></rich:column--%>
                                            <rich:column width="15%"><h:outputText value="Update"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem1.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right"><h:outputText value="#{dataItem1.denoNo}"/></rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem1.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <%--rich:column style="text-align:right"><h:outputText value="#{dataItem1.flag}"/></rich:column--%>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="updateLink" action="#{DenoModification.setDenominationRowValues}" reRender="errorMessage1,lpg,panel6,panel8" ajaxSingle="true" focus="txtDenoValue">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem1}" target="#{DenoModification.currentDenominationItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="denominationTable" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel1" width="100%" styleClass="row2" style="height:200px;">
                        <rich:dataTable  rows="6" value="#{DenoModification.cashTxnList}" var="dataItem" rowClasses="gridrow1, gridrow2" id="datalist" columnsWidth="100"
                                         rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="32px"><h:outputText value="Sr.No." /> </rich:column>
                                    <rich:column width="100px"><h:outputText value="A/c No." /></rich:column>
                                    <rich:column width="250px"><h:outputText value="Customer Name" /></rich:column>
                                    <rich:column width="100px"><h:outputText value="Txn Type" /></rich:column>
                                    <rich:column width="100px"><h:outputText value="Received Amt" /></rich:column>
                                    <rich:column width="100px"><h:outputText value="Payment Amt" /></rich:column>
                                    <rich:column width="100px"><h:outputText value="Txn Date" /></rich:column>
                                    <rich:column><h:outputText value="Details" /></rich:column>
                                    <rich:column width="55px" ><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.tranId}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.acNo}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.screenFlag}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.tyDesc}" style="text-align:center"/></rich:column>
                            <rich:column >
                                <h:outputText  value="#{dataItem.crAmt}" style="text-align:center">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column >
                                <h:outputText  value="#{dataItem.drAmt}" style="text-align:center">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.dt}" style="text-align:center"/></rich:column>
                            <rich:column ><h:outputText  value="#{dataItem.details}" style="text-align:center"/></rich:column>
                            <rich:column style="text-align:center;">
                                <a4j:commandLink ajaxSingle="true" id="signViewLink" action="#{DenoModification.getDenominationDetails}" reRender="panel1,panel5,errmsg">
                                    <h:graphicImage value="/resources/images/passed.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DenoModification.currentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="datalist" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" columns="1">
                        <h:panelGroup id="btnPanel" styleClass="vtop">
                            <a4j:region id="btnPnl">
                                <a4j:commandButton id="update" action="#{DenoModification.updateDenominationDetails}" value="Update" reRender="mainPanel,msg"/>
                                <a4j:commandButton id="refresh" action="#{DenoModification.refresh}" value="Refresh" reRender="mainPanel"/>
                                <a4j:commandButton id="exit" action="#{DenoModification.exit}" value="Exit" reRender="mainPanel"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>        
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnPnl"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>                
        </body>
    </html>
</f:view>
