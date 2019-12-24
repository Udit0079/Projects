<%-- 
    Document   : CashierTokenBook
    Created on : Aug 9, 2011, 4:39:54 PM
    Author     : ROHIT KRISHNA
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
            <title>Cashier Token Book</title>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <a4j:keepAlive beanName="CashierTokenBook"/>
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{CashierTokenBook.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Cashier Token Book"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{CashierTokenBook.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{CashierTokenBook.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{CashierTokenBook.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="a5" styleClass="row1" columns="2" style="width:100%;height:30px;text-align:center;">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:outputLabel styleClass="headerLabel" value="Today's Date Is : "/>
                            <h:outputText id="stxtTodayDt" styleClass="headerLabel" style="color:purple;" value="#{CashierTokenBook.todayDate}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="a6" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{CashierTokenBook.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="25"><h:outputText value="Detail" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Sr. No." /></rich:column>
                                        <rich:column><h:outputText value="Token No." /></rich:column>
                                        <rich:column><h:outputText value="Sub Token No." /></rich:column>
                                        <rich:column><h:outputText value="A/c. No." /></rich:column>
                                        <rich:column><h:outputText value="Customer Name" /></rich:column>
                                        <rich:column><h:outputText value="Joint Name" /></rich:column>
                                        <rich:column><h:outputText value="Amount" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column>
                                        <rich:column><h:outputText value="Auth By" /></rich:column>
                                        <rich:column><h:outputText value="From Branch" /></rich:column>
                                        <rich:column><h:outputText value="To Branch" /></rich:column>
                                        <rich:column><h:outputText value="Rec No." /></rich:column>
                                        <rich:column><h:outputText value="Token Paid" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right;" width="5%"><h:outputText value="#{dataItem.srNo}" /></rich:column>
                                <rich:column style="text-align:right;" width="5%"><h:outputText value="#{dataItem.tokenNo}" /></rich:column>
                                <rich:column width="10%" style="text-align:center;"><h:outputText value="#{dataItem.subTokenNo}" /></rich:column>
                                <rich:column style="text-align:center;" width="10%"><h:outputText value="#{dataItem.acno}" /></rich:column>
                                <rich:column width="20%"><h:outputText value="#{dataItem.custName}" /></rich:column>
                                <rich:column width="20%"><h:outputText value="#{dataItem.jointName}" /></rich:column>
                                <rich:column style="text-align:right;" width="7%">
                                    <h:outputText value="#{dataItem.amount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column width="8%"><h:outputText value="#{dataItem.enterBy}" /></rich:column>
                                <rich:column width="8%"><h:outputText value="#{dataItem.authBy}" /></rich:column>
                                <rich:column width="5%" style="text-align:center;"><h:outputText value="#{dataItem.orgBrnId}" /></rich:column>
                                <rich:column width="5%" style="text-align:center;"><h:outputText value="#{dataItem.destBrnId}" /></rich:column>
                                <rich:column style="text-align:right;" width="5%"><h:outputText value="#{dataItem.recNo}" /></rich:column>
                                <rich:column style="text-align:center;cursor:pointer;" width="10%">
                                    <h:outputText value="#{dataItem.tokenPaid}" />
                                    <a4j:support action="#{CashierTokenBook.changeTokenPaid}" ajaxSingle="true" event="ondblclick" reRender="message,errorMessage,lpg,a6,taskList,panel5,panel6,panel7,panel8" focus="btnAuthorize">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{CashierTokenBook.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{CashierTokenBook.currentRow}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="taskListScroller" align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="1" id="a7" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblIndication" styleClass="label" style="color:blue" value="# Double click on token paid column to change from N to Y and then press Paid button."/>
                    </h:panelGrid>
                    <h:panelGrid id="panel5" bgcolor="#fff" rendered="#{CashierTokenBook.denominationRender}" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid id="panel6" columnClasses="col13,col13,col13,col13" rendered="#{CashierTokenBook.denominationRender}" columns="4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblDenomination" value="Denomination :" styleClass="label"/>
                            <h:panelGroup>
                                <h:inputText id="txtDenoValue" tabindex="1" value="#{CashierTokenBook.denoValue}" styleClass="input" style="width:73px" disabled="#{CashierTokenBook.disableDenoNo}"/> &nbsp;
                                <h:outputLabel value="Rs." styleClass="label"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblNoOfNotes" value="No. of Notes / Coins :" styleClass="label"/>
                            <h:inputText id="txtDenoNo" tabindex="2" value="#{CashierTokenBook.denoNo}" styleClass="input" style="width:73px" disabled="#{CashierTokenBook.disableDenoNo}">
                                <%--a4j:support event="onblur" action="#{CashierTokenBook.onBlurDenominationNo}" oncomplete="#{rich:element('panel5')}.style.display='';" focus="txtDenoValue" reRender="message,errorMessage,lpg,taskList,gpFooter,errPanel,panel6,btnReceive,panel4,panel5"/--%>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="panel9" columnClasses="col13,col13,col13,col13" rendered="#{CashierTokenBook.denominationRender}" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                            <%--h:outputLabel id="lblOldNewFlg" value="Old/New :" styleClass="label"/>
                            <h:selectOneListbox id="ddOldNewFlg" tabindex="3" styleClass="ddlist" size="1" value="#{CashierTokenBook.selectOldNewFlg}" disabled="#{CashierTokenBook.disableDenoNo}">
                                <f:selectItems value="#{CashierTokenBook.oldNewFlgList}"/>.                                
                            </h:selectOneListbox--%>
                            <h:outputLabel id="lbltyFlg" value="Return/Paid :" styleClass="label"/>
                            <h:selectOneListbox id="ddtyFlg" tabindex="4" styleClass="ddlist" size="1" value="#{CashierTokenBook.tyFlg}" disabled="#{CashierTokenBook.disableDenoNo}">
                                <f:selectItems value="#{CashierTokenBook.tyList}"/>
                                <a4j:support event="onblur" action="#{CashierTokenBook.onBlurDenominationNo}" oncomplete="#{rich:element('panel5')}.style.display='';" focus="txtDenoValue" reRender="message,errorMessage,lpg,taskList,gpFooter,errPanel,panel6,btnReceive,panel4,panel5,panel9"/>
                            </h:selectOneListbox>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>        
                        <h:panelGrid id="panel7" columnClasses="vtop" rendered="#{CashierTokenBook.denominationRender}" columns="1" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable id="denominationTable" rendered="#{CashierTokenBook.denominationRender}" rows="20" value="#{CashierTokenBook.denominationTable}" var="dataItem" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="25"><h:outputText value="Denomination Detail" /></rich:column>
                                            <rich:column width="15%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                            <rich:column width="15%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                            <rich:column width="25%"><h:outputText value="Amount"/></rich:column>
                                            <%--rich:column width="15%"><h:outputText value="Old/New"/></rich:column--%>
                                            <rich:column width="15%"><h:outputText value="Return/Paid"/></rich:column>
                                            <rich:column width="15%"><h:outputText value="Update"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right"><h:outputText value="#{dataItem.denoNo}"/></rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <%--rich:column style="text-align:right"><h:outputText value="#{dataItem.flag}"/></rich:column--%>
                                    <rich:column style="text-align:right"><h:outputText value="#{dataItem.tySh}"/></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="updateLink" action="#{CashierTokenBook.setDenominationRowValues}" reRender="message,errorMessage,lpg,taskList,gpFooter,panel5,panel6,panel7,panel8,btnReceive,panel9" ajaxSingle="true" focus="txtDenoValue">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{CashierTokenBook.currentDenominationItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{CashierTokenBook.currentIDenominationRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="denominationTable" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="panel8" rendered="#{CashierTokenBook.denominationRender}" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblTotalAmt" value="Total Amount :" styleClass="label"/>
                            <h:panelGroup>
                                <h:inputText id="txtTotalAmount" value="#{CashierTokenBook.totalAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                                <h:outputLabel value="Rs." styleClass="label"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCurrencyAmt" value="Currency Amount :" styleClass="label"/>
                            <h:panelGroup>
                                <h:inputText id="txtTotalCurrencyAmount" value="#{CashierTokenBook.currencyAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                                <h:outputLabel value="Rs." styleClass="label"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnPaid" tabindex="5" value="Paid" oncomplete="#{rich:component('payPanel')}.show()" reRender="message,errorMessage,lpg,taskList,gpFooter,panel5,panel6,panel7,panel8" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="6" value="Refresh" action="#{CashierTokenBook.refreshForm}" reRender="message,errorMessage,lpg,taskList,gpFooter,panel5,panel6,panel7,panel8,taskListScroller,panel9" />
                            <a4j:commandButton id="btnExit" tabindex="7" value="Exit" action="#{CashierTokenBook.exitBtnAction}" reRender="message,errorMessage" />
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
            <rich:modalPanel id="payPanel" autosized="true" width="300" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to pay for this token number ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{CashierTokenBook.paidBtnAction}"
                                                       oncomplete="#{rich:component('payPanel')}.hide();" reRender="message,errorMessage,lpg,taskList,gpFooter,panel5,panel6,panel7,panel8" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('payPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
