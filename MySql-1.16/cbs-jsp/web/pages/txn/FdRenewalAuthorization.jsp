<%-- 
    Document   : FdRenewalAuthorization
    Created on : Aug 6, 2010, 4:29:12 PM
    Author     : ROHIT KRISHNA GUPTA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>TD Renewal Authorization</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{FdRenewalAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="FD Renewal Authorization"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FdRenewalAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{FdRenewalAuthorization.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{FdRenewalAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a5" width="100%" style="height:30px;" styleClass="row1">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :  "/>
                            <h:outputText id="stxtAcctNo" styleClass="output" value="#{FdRenewalAuthorization.actNo}" />
                            <h:inputHidden id="batchNo" value="#{FdRenewalAuthorization.batchNo}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{FdRenewalAuthorization.oldDetailList}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12"><h:outputText value="Closed Receipt Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Voucher No." /></rich:column>
                                        <rich:column><h:outputText value="Reciept No." /></rich:column>
                                        <rich:column><h:outputText value="ROI" /></rich:column>
                                        <rich:column><h:outputText value="TD Date" /></rich:column>
                                        <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                        <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                        <rich:column><h:outputText value="Int Opt." /></rich:column>
                                        <rich:column><h:outputText value="Period" /></rich:column>
                                        <rich:column><h:outputText value="Status" /></rich:column>
                                        <rich:column><h:outputText value="Balance Interest" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.voucherNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.recieptNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.roi}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.fdDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.matDate}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.prinAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.intOpt}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.period}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.status}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totalAmt}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                <rich:column ><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                            </rich:dataTable>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a7" style="height:auto;" styleClass="row1" width="100%">
                        <rich:dataTable value="#{FdRenewalAuthorization.newDetailList}" var="dataItem1"
                                        rowClasses="gridrow1,gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="12"><h:outputText value="New Receipt Details" /></rich:column>
                                    <rich:column breakBefore="true"><h:outputText value="Voucher No." /></rich:column>
                                    <rich:column><h:outputText value="Reciept No." /></rich:column>
                                    <rich:column><h:outputText value="ROI" /></rich:column>
                                    <rich:column><h:outputText value="TD Date" /></rich:column>
                                    <rich:column><h:outputText value="Maturity Date" /></rich:column>
                                    <rich:column><h:outputText value="Prin. Amount" /></rich:column>
                                    <rich:column><h:outputText value="Int Opt." /></rich:column>
                                    <rich:column><h:outputText value="Period" /></rich:column>
                                    <rich:column><h:outputText value="Status" /></rich:column>
                                    <rich:column><h:outputText value="Maturity Amount" /></rich:column>
                                    <rich:column><h:outputText value="Auth" /></rich:column>
                                    <rich:column><h:outputText value="Enter By" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem1.voucherNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.recieptNo}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.roi}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem1.fdDate}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.matDate}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.prinAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.intOpt}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem1.period}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.status}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.totalAmt}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem1.auth}" /></rich:column>
                            <rich:column ><h:outputText value="#{dataItem1.enterBy}" /></rich:column>
                        </rich:dataTable>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a10" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{FdRenewalAuthorization.pendingAcctList}" var="pendingLt" align="center"
                                            rowClasses="gridrow1,gridrow2" id="taskList2" rows="2" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="40%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="12">
                                            <h:outputText value="Pending List To Be Authorized" />
                                        </rich:column>
                                        <rich:column breakBefore="true" width="50"> <h:outputText value="Account No."/></rich:column>
                                        <rich:column width="50"><h:outputText value="Voucher No." /></rich:column>
                                        <rich:column width="50"><h:outputText value="Batch No." /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{pendingLt.acctNo}"/></rich:column>
                                <rich:column><h:outputText value="#{pendingLt.voucherNo}"/></rich:column>
                                <rich:column><h:outputText value="#{pendingLt.batchNo}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink  id="selectlink" action="#{FdRenewalAuthorization.loadOldAndNewGrid}"
                                                      reRender="a5,a6,a7,message,errorMessage,lpg" oncomplete="#{rich:element('btnAuthorize')}.disabled=false" focus="btnAuthorize">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{pendingLt}" target="#{FdRenewalAuthorization.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{FdRenewalAuthorization.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="center" for="taskList2" maxPages="20" />
                        </a4j:region>
                        <rich:modalPanel id="authorizePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText value="Are You Sure To Authorize ?"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{FdRenewalAuthorization.accountAuthorization}"
                                                                   oncomplete="#{rich:component('authorizePanel')}.hide();" reRender="a5,a6,a7,a10,lpg,message,errorMessage" />
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('authorizePanel')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnAuthorize" value="Authorize" oncomplete="#{rich:component('authorizePanel')}.show()" reRender="a5,a6,a7,a10,lpg,message,errorMessage"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FdRenewalAuthorization.resetForm}" reRender="a5,a6,a7,a10,lpg,message,errorMessage"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{FdRenewalAuthorization.exitFrm}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
