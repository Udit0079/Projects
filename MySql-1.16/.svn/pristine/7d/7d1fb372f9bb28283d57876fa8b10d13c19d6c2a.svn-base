<%-- 
    Document   : voucherPrintingALL
    Created on : 8 Jun, 2018, 11:41:13 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Voucher Printing</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".issueDt").mask("99/99/9999");
                }
            </script>
        </head>
        <body>
            <h:form id="ckycrBranchRequest" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{VoucherPrintingALL.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Voucher Printing"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{VoucherPrintingALL.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="errorPanel" columns="1" columnClasses="col3" styleClass="row1" style="text-align:center;width:100%">
                        <h:outputText id="errorMsg" styleClass="error" value="#{VoucherPrintingALL.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4"  styleClass="row2"> 
                        <h:outputText value="Branch Wise" styleClass="label"/>
                        <h:selectOneListbox id="branch" size="1" value="#{VoucherPrintingALL.branch}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrintingALL.branchList}" />
                        </h:selectOneListbox>
                        <h:outputText value="Voucher Option" styleClass="label"/>
                        <h:selectOneListbox id="txnId" size="1" value="#{VoucherPrintingALL.reportOption}" styleClass="ddlist">
                            <f:selectItems value="#{VoucherPrintingALL.reportOptionList }" />
                            <%--a4j:support event="onchange" action="#{VoucherPrintingALL.onReportAction}" reRender="lId,vId"/--%>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblToDt" styleClass="label" value="Date"/>
                        <h:inputText id="toDate" size="10" styleClass="input issueDt" value="#{VoucherPrintingALL.curDate}" >
                            <a4j:support event="onblur" action="#{VoucherPrintingALL.gridLoad}" reRender="taskList,errorMsg"/>
                        </h:inputText>
                    </h:panelGrid> 
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row1" width="100%">
                        <rich:extendedDataTable enableContextMenu="false" id="taskList" width="100%" 
                                                value="#{VoucherPrintingALL.gridList}" styleClass="gridrow1,gridrow2,noTableBorder"
                                                height="231px" var="dataItem" rowKeyVar="row" 
                                                noDataLabel="No Record Found">
                            <f:facet name="header">
                                <h:outputText value="Voucher Detail"/>
                            </f:facet>
                            <rich:column width="4%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="SNo"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.sNo}"/>
                            </rich:column>
                            <rich:column width="22%" style="text-align:left">
                                <f:facet name="header">
                                    <h:outputText value="Customer Name"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.acName}"/>
                            </rich:column>
                            <rich:column width="10%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Account No."/>
                                </f:facet>
                                <h:outputText value="#{dataItem.acNo}"/>
                            </rich:column>
                            <rich:column width="7%" style="text-align:right">
                                <f:facet name="header">
                                    <h:outputText value="Credit"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.creditAmt}"/>
                            </rich:column>
                            <rich:column width="7%" style="text-align:right">
                                <f:facet name="header">
                                    <h:outputText value="drAmt"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.debitAmt}"/>
                            </rich:column>
                            <rich:column width="6%" style="text-align:center">
                                <f:facet name="header">
                                    <h:outputText value="Date"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.date}"/>
                            </rich:column>
                            <rich:column width="6%" style="text-align:right">
                                <f:facet name="header">
                                    <h:outputText value="Voucher No."/>
                                </f:facet>
                                <h:outputText value="#{dataItem.voucherNo}"/>
                            </rich:column>
                            <rich:column width="34%" style="text-align:left" >
                                <f:facet name="header">
                                    <h:outputText value="Particular"/>
                                </f:facet>
                                <h:outputText value="#{dataItem.particular}"/>
                            </rich:column>
                            <rich:column width="5%" style="text-align:center"> 
                                <f:facet name="header">
                                    <h:selectBooleanCheckbox value="#{VoucherPrintingALL.allSelected}">
                                        <a4j:support event="onchange" action="#{VoucherPrintingALL.setAllBoxSelected}" reRender="taskList"/>
                                    </h:selectBooleanCheckbox>
                                </f:facet>
                                <h:selectBooleanCheckbox value="#{dataItem.selected}"/>
                            </rich:column>
                        </rich:extendedDataTable>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gridPanel7" style="text-align: center; width: 100%" styleClass="footer">
                        <h:panelGroup id="gridPanel8" layout="block" style="width:100%;text-align:center;">
                            <h:commandButton id="btnPrint" value="Printing" actionListener="#{VoucherPrintingALL.printReport}"/>
                            <a4j:commandButton id="reset" value="Refresh" actionListener="#{VoucherPrintingALL.refreshPage}" oncomplete="setMask()" reRender="mainPanel,errorMsg"/>
                            <a4j:commandButton  id="exit" value="Exit" action="#{VoucherPrintingALL.exitPage}"reRender="mainPanel,errorMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </h:form>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
