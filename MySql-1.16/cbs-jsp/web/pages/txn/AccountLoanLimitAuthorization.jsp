<%-- 
    Document   : AccountLoanLimitAuthorization
    Created on : Nov 13, 2010, 3:39:22 PM
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Account Loan Limit Authorization</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountLoanLimitAuthorization.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Account Loan Limit Authorization"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountLoanLimitAuthorization.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel3" columns="3" columnClasses="col7,col7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblToDate" styleClass="label" value="Account No"/>
                        <h:outputText id="outtext1"  styleClass="output" value="#{AccountLoanLimitAuthorization.accountName}"/>
                        <h:outputText id="outtext2"   styleClass="output" value="#{AccountLoanLimitAuthorization.accountNumber}"/>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel4" columns="1" columnClasses="vtop" styleClass="row2" style="height:200px;" width="100%">
                        <rich:dataTable  value="#{AccountLoanLimitAuthorization.gridData2}" var="dataItem"
                                         rowClasses="row1,row2" id="taskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="11">
                                        <h:outputText value="Old Details"/>
                                    </rich:column>
                                    <rich:column breakBefore="true">
                                        <h:outputText value="INTEREST DEPOSIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="PENALTY ROI"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC INT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC APPLICABLE DATE"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC TILL DATE"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="SUBSIDY AMOUNT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="SUBSIDY EXP DT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="MAX LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ENTERED BY / EDITED BY"/>
                                    </rich:column>
                                </rich:columnGroup>
                            </f:facet>

                            <rich:column>
                                <h:outputText value="#{dataItem.interestDepositSet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.penaltyRoiSet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.limitSet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.adhocIntSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.adhocLimitoldSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.adhocApplicableDateSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.adhocTillDtOldSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.subsidyAmtOldSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.subsidyExpDtOldSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.maxLimitOldSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataItem.enterByEditedBySet}"/>
                            </rich:column>
                        </rich:dataTable>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel5" columns="1" columnClasses="vtop" styleClass="row2" style="height:200px;" width="100%">
                        <rich:dataTable  value="#{AccountLoanLimitAuthorization.gridData2}" var="dataitem3"
                                         rowClasses="row1,row2" id="taskList2" rows="6" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="12">
                                        <h:outputText value="New Details"/>
                                    </rich:column>
                                    <rich:column breakBefore="true">
                                        <h:outputText value="INTEREST DEPOSIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="PENALTY ROI"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC INT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC APPLICABLE DATE"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ADHOC TILL DATE"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="SUBSIDY AMOUNT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="SUBSIDY EXP DT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="MAX LIMIT"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="ENTERED BY / EDITED BY"/>
                                    </rich:column>

                                </rich:columnGroup>
                            </f:facet>

                            <rich:column>
                                <h:outputText value="#{dataitem3.intDepositSet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem3.penaltySet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem3.odLimitSet}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem3.adhocInterestSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.adhocLimitSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.adhocApplicableDtSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.adhocTillDtSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.subsidyAmtSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.subsidyExpSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.maxLimitSet}"/>
                            </rich:column>
                            <rich:column >
                                <h:outputText value="#{dataitem3.enterBySet}"/>
                            </rich:column>
                        </rich:dataTable>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel56" columns="1" columnClasses="vtop" styleClass="row2" style="width:100%;text-align:center;">
                        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{AccountLoanLimitAuthorization.fetchCurrentRow}">
                                <a4j:actionparam name="ctrlNo" value="{acNo}"/>
                                <a4j:actionparam name="row" value="{currentRow}"/>
                            </rich:menuItem>
                        </rich:contextMenu>
                        <rich:dataTable  align="center" value="#{AccountLoanLimitAuthorization.gridData}" var="dataitem"
                                         rowClasses="row1,row2" id="taskList3" rows="6" columnsWidth="100" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="50%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="3">
                                        <h:outputText value="Pending List To Be Authorized"/>
                                    </rich:column>
                                    <rich:column breakBefore="true">
                                        <h:outputText value="Acccount No"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="Transaction Id"/>
                                    </rich:column>
                                    <rich:column>
                                        <h:outputText value="Select"/>
                                    </rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column>
                                <h:outputText value="#{dataitem.accountNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataitem.txnId}"/>
                            </rich:column>

                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink  ajaxSingle="true" id="selectlink"  reRender="outtext1,outtext2,taskList,taskList2,errorGrid,taskList3,buttonUpdate1" action="#{AccountLoanLimitAuthorization.setRowValues}">
                                    <h:graphicImage   id="imagerender"  value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataitem}" target="#{AccountLoanLimitAuthorization.authorized}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{AccountLoanLimitAuthorization.currentRow}"/>
                                </a4j:commandLink>
                                <rich:toolTip for="selectlink" value="Select This Row"/>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList3" maxPages="20"/>
                        <rich:modalPanel id="showPanelForGrid" autosized="true" width="200">
                            <f:facet name="header">
                                <h:outputText value="Do you want to delete this record ?" style="padding-right:15px;" />
                            </f:facet>
                            <f:facet name="controls">
                                <h:panelGroup>
                                    <h:graphicImage value="/images/close.png" styleClass="hidelink" id="hidelink1" />
                                    <rich:componentControl for="showPanelForGrid" attachTo="hidelink1" operation="hide" event="onclick" />
                                </h:panelGroup>
                            </f:facet>
                            <h:form>
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" ajaxSingle="true" action="#{AccountLoanLimitAuthorization.setRowValues}"
                                                                   onclick="#{rich:component('showPanelForGrid')}.hide();" reRender="taskList1"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Cancel" onclick="#{rich:component('showPanelForGrid')}.hide();return false;" />
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>
                    </h:panelGrid>

                    <h:panelGrid  id="errorMsgReporting"   width="100%"   style="height:50px;" styleClass="error" >
                        <h:outputText id="errorGrid" value="#{AccountLoanLimitAuthorization.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="buttonUpdate"  value="List" reRender="taskList3,errorGrid,taskList,taskList2" action="#{AccountLoanLimitAuthorization.getListData}">
                                <rich:toolTip for="buttonUpdate" value="Click To Update"></rich:toolTip>
                            </a4j:commandButton>

                            <a4j:commandButton  id="buttonUpdate1"  disabled="#{AccountLoanLimitAuthorization.authorize}" value="Authorize" reRender="errorGrid,taskList3,buttonUpdate1,taskList,taskList2" action="#{AccountLoanLimitAuthorization.btnAuthorize}">
                                <rich:toolTip for="buttonUpdate1" value="Click To Update"></rich:toolTip>
                            </a4j:commandButton>

                            <a4j:commandButton id="btnExit" value="exit" action="#{AccountLoanLimitAuthorization.exitBtnAction}" reRender="errorGrid,taskList3,buttonUpdate1,taskList,taskList2,outtext1,outtext2">
                                <rich:toolTip for="btnExit" value="Click To Exit"></rich:toolTip>
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
