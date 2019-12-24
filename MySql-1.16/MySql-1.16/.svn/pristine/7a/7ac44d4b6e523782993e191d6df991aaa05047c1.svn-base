<%-- 
    Document   : atmdailytxnreconsilation
    Created on : 22 Mar, 2018, 2:43:54 PM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>ATM Daily Transaction Reconsilation</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
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
            <a4j:form>
                <h:panelGrid id="maingrid" columns="1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid id="headergrid" columns="3" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{atmDailyTxnReconsilationBean.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="ATM Daily Transaction Reconsilation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{atmDailyTxnReconsilationBean.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="msggrid" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{atmDailyTxnReconsilationBean.errorMessage}"/>
                    </h:panelGrid>
                    <h:panelGrid id="dategrid" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblDt" styleClass="label" value="Date"><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtDt" styleClass="input issueDt" value="#{atmDailyTxnReconsilationBean.txnDt}" size="12">
                            <a4j:support event="onblur" action="#{atmDailyTxnReconsilationBean.populateReconData}" reRender="message,tablePanel,taskList,btnPost" oncomplete="setMask();"/>
                        </h:inputText>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="tablePanel" columns="1" columnClasses="vtop" style="border:1px ridge #BED6F8;" styleClass="row2" width="100%">
                        <a4j:region id="tblRegion">
                            <rich:dataTable value="#{atmDailyTxnReconsilationBean.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="15" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="7"><h:outputText value="Transaction Detail" /></rich:column>
                                        <rich:column breakBefore="true" style="width:180px;"><h:outputText value="Request Type" /></rich:column>
                                        <rich:column style="width:100px;"><h:outputText value="No of Txn." /></rich:column>
                                        <rich:column><h:outputText value="Charge Amount" /></rich:column>
                                        <rich:column><h:outputText value="General Ledger"/></rich:column>
                                        <rich:column><h:outputText value="SGST" /></rich:column>
                                        <rich:column><h:outputText value="CGST" /></rich:column>
                                        <rich:column><h:outputText value="IGST" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.requestType}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.noOfTxn}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totalCharge}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.chargeHead}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totalPayableSgst}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totalPayableCgst}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totalInpurCreditIgst}" /></rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="10"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPost" value="Post" oncomplete="#{rich:component('processPanel')}.show();" reRender="message,processPanel" disabled="#{atmDailyTxnReconsilationBean.btnDisable}"/>
                            <a4j:commandButton id="btnReset" value="Refresh" action="#{atmDailyTxnReconsilationBean.refreshForm}" oncomplete="setMask();" reRender="maingrid"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{atmDailyTxnReconsilationBean.exitForm}" oncomplete="setMask();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText id="confirmid" value="Are you sure to post ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{atmDailyTxnReconsilationBean.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();setMask();" reRender="message,maingrid"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
