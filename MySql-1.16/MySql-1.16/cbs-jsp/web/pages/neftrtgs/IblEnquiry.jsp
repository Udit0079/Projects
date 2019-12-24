<%-- 
    Document   : uploadneftrtgs
    Created on : Dec 13, 2012, 11:40:13 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link rel="stylesheet" href="/cbs-jsp/resources/txn_style.css" type="text/css"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>IBL Enquiry</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".dt").mask("39/19/9999");
                }
            </script>
        </head>
        <body>
            <a4j:form id="uploadForm" prependId="false">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{IblEnquiry.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="FileUpload"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{IblEnquiry.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errormsg" style="height:1px;text-align:center" styleClass="error" width="100%">
                        <h:outputText id="errmsg" value="#{IblEnquiry.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel17" width="100%">
                        <h:outputLabel id="lblFunction" value="Function" styleClass="label"/>
                        <h:selectOneListbox id="ddFunction" style="width: 120px" styleClass="ddlist" value="#{IblEnquiry.function}" size="1">
                            <f:selectItems value="#{IblEnquiry.functionList}" />
                            <a4j:support event="onblur" action="#{IblEnquiry.changeFunction}" reRender="errmsg,txtFromDt,txtToDt,stmtGrid,txnGrid" 
                                         focus="#{IblEnquiry.focusId}" oncomplete="setMask()"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblcustId" value="Customer Id" styleClass="label"/>
                        <h:outputText id="txtCustId" value="#{IblEnquiry.customerId}" styleClass="output"/>
                        <h:outputLabel id="lblAcNo" value="Sponsor Bank Account" styleClass="label"/>
                        <h:outputText id="txtAcno" value="#{IblEnquiry.acctNo}" styleClass="output"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel18" width="100%">
                        <h:outputLabel id="lblFromDt" value="From Date" styleClass="label"/>
                        <h:inputText id="txtFromDt" value="#{IblEnquiry.fromDt}" styleClass="input dt" maxlength="10" disabled="#{IblEnquiry.disable}" size="10"/>
                        <h:outputLabel id="lblToDt" value="To Date" styleClass="label"/>
                        <h:inputText id="txtToDt" value="#{IblEnquiry.toDt}" styleClass="input dt" maxlength="10" disabled="#{IblEnquiry.disable}" size="10"/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btn" value="Get Details" action="#{IblEnquiry.getDetails}" oncomplete="setMask()" reRender="txnGrid,errmsg"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{IblEnquiry.refreshForm}" oncomplete="setMask()" reRender="mainPanel,errmsg"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{IblEnquiry.exitForm}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;display:#{IblEnquiry.balDisplay}" columnClasses="vtop">
                        <rich:dataTable  value="#{IblEnquiry.balList}" var="tempItem" 
                                         rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="7"><h:outputText value="Account Detail"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Account Number" /></rich:column>
                                    <rich:column><h:outputText value="Account Name" /></rich:column>
                                    <rich:column><h:outputText value="Available Balance" /></rich:column>
                                    <rich:column><h:outputText value="Cleared Balance" /></rich:column>
                                    <rich:column><h:outputText value="Eff Available Balance" /></rich:column>
                                    <rich:column><h:outputText value="UnCleared Balance" /></rich:column>
                                    <rich:column><h:outputText value="FD Balance" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{tempItem.accountNumber}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.accountName}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.availableBalance}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{tempItem.clearBalance}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{tempItem.effAvailableBalance}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>   
                            <rich:column><h:outputText value="#{tempItem.unclearBalance}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{tempItem.fdBalance}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="balDataScroll" align="left" for="txnList" maxPages="5" />
                    </h:panelGrid>
                    <h:panelGrid id="stmtGrid" width="100%" style="background-color:#edebeb;height:500px;display:#{IblEnquiry.stmtDisplay}" columnClasses="vtop">
                        <rich:dataTable  value="#{IblEnquiry.stmtList}" var="tempItem"
                                         rowClasses="gridrow1, gridrow2" id="stmtList" rows="15" columnsWidth="100"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                         width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="8"><h:outputText value="Account Statement"/></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="BankReference" /></rich:column>
                                    <rich:column><h:outputText value="ValueDate" /></rich:column>
                                    <rich:column><h:outputText value="Txn Date" /></rich:column>
                                    <rich:column><h:outputText value="Txn Type" /></rich:column>
                                    <rich:column><h:outputText value="Payment Narration" /></rich:column>
                                    <rich:column><h:outputText value="Debit" /></rich:column>
                                    <rich:column><h:outputText value="Credit" /></rich:column>
                                    <rich:column><h:outputText value="Available Balance" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{tempItem.bankReference}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.valueDate}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.transactionDate}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.trxnType}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.paymentNarration}"/></rich:column>
                            <rich:column><h:outputText value="#{tempItem.debit}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{tempItem.credit}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                            <rich:column><h:outputText value="#{tempItem.availableBal}"><f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/></h:outputText></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="stmtDataScroll" align="left" for="stmtList" maxPages="20" />
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
