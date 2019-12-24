<%-- 
    Document   : transaction
    Created on : Aug 19, 2011, 2:25:26 PM
    Author     : Sudhir Kr Bisht
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Transaction</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="transactionForm">
                <h:panelGrid id="transactionsPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">

                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{DdsTransaction.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Transaction Screen"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DdsTransaction.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="gridPanel" style="text-align:center" styleClass="row2">
                        <h:outputText id="errMsg51" value="#{DdsTransaction.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel" styleClass="row1">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="purposeGrid" style="text-align:left;" width="100%">
                            <h:outputLabel id="acType" styleClass="output" value="A/c Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="acTypeList" styleClass="ddlist" size="1" style="width:120px" value="#{DdsTransaction.accountType}">
                                <f:selectItems value="#{DdsTransaction.accountTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="nature" style="text-align:left;" width="100%">
                            <h:outputLabel id="agentCode" styleClass="output" value="Agent Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="agentCodeList" styleClass="ddlist" size="1" style="width:150px" value="#{DdsTransaction.agentCode}">
                                <f:selectItems value="#{DdsTransaction.agentCodeList}"/>
                                <a4j:support event="onblur" action="#{DdsTransaction.cmbAgCodeLostFocus}" oncomplete="if(#{DdsTransaction.flag=='true'}) {#{rich:element('ddsGrid')}.style.display='';}" 
                                             reRender="ddsGrid,ddsList,receiptNoText,errMsg51,agentNameText,totalAmountText,acountNoList,post" focus="receiptNoText"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridpanelSalry" styleClass="row2">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel2" style="text-align:left;" width="100%">
                            <h:outputLabel id="agentName" styleClass="output" value="Agent Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:outputText id="agentNameText" styleClass="output" value="#{DdsTransaction.agentName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel23" style="text-align:left;" width="100%">
                            <h:outputLabel id="receiptNo" styleClass="output" value="Receipt No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="10" id="receiptNoText" styleClass="input" value="#{DdsTransaction.receiptNo}" style="width:80px"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPane4" styleClass="row1">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel6" style="text-align:left;" width="100%">
                            <h:outputLabel id="accoutNo" styleClass="output" value="Account No."><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="6" id="acountNoList" styleClass="input" value="#{DdsTransaction.accountNo}" style="width:80px">
                                <a4j:support event="onblur" action="#{DdsTransaction.cboAccountLostFocus}" reRender="nameText,errMsg51,post" disabled="#{DdsTransaction.disableAccount}"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel5" style="text-align:left;" width="100%">
                            <h:outputLabel id="amount" styleClass="output" value="Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="7" id="amountText" styleClass="input" value="#{DdsTransaction.amount}" style="width:80px">
                                <a4j:support event="onblur" focus="post"/>
                            </h:inputText>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel7" styleClass="row2">
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPane9" style="text-align:left;" width="100%">
                            <h:outputLabel id="nameLabel" styleClass="output" value="Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:outputLabel id="nameText" styleClass="output" value="#{DdsTransaction.name}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col8,col8" columns="2" id="slabGridPanel8" style="text-align:left;" width="100%">
                            <h:outputLabel id="totalAmount" styleClass="output" value="Total Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:outputLabel id="totalAmountText" styleClass="output" value="#{DdsTransaction.totalAmount}"/>
                        </h:panelGrid>
                    </h:panelGrid>

                    <h:panelGrid id="ddsGrid" width="100%" style="width:100%;height:0px;display:none;" columnClasses="vtop" styleClass="row2">
                        <rich:dataTable id="ddsList" value="#{DdsTransaction.ddstransactionGrid}" var="dataItem" rowClasses="gridrow1, gridrow2" rows="10"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                        width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column ><h:outputText value="Agent Code"/></rich:column>
                                    <rich:column><h:outputText value="Agent Name"/></rich:column>
                                    <rich:column><h:outputText value="Date"/></rich:column>
                                    <rich:column><h:outputText value="Receipt No"/></rich:column>
                                    <rich:column><h:outputText value="Account No"/></rich:column>
                                    <rich:column><h:outputText value="Customer Name"/></rich:column>
                                    <rich:column><h:outputText value="Amount"/></rich:column>
                                    <rich:column><h:outputText value="Update"/></rich:column>
                                    <rich:column><h:outputText value="Delete"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align: center">
                                <h:outputText value="#{dataItem.agentCode}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.agentName}"/>
                            </rich:column>
                            <rich:column style="text-align: center">
                                <h:outputText value="#{dataItem.date}"/>
                            </rich:column>
                            <rich:column style="text-align: center">
                                <h:outputText value="#{dataItem.receiptNo}"/>
                            </rich:column>
                            <rich:column style="text-align: center">
                                <h:outputText value="#{dataItem.accountNo}"/>
                            </rich:column>
                            <rich:column>
                                <h:outputText value="#{dataItem.name}"/>
                            </rich:column>
                            <rich:column style="text-align: center">
                                <h:outputText value="#{dataItem.amount}"/>
                            </rich:column>
                            <rich:column style="text-align:center">
                                <a4j:commandLink focus="amountText" ajaxSingle="true" id="selectlink" action="#{DdsTransaction.setTransactionRowValues}" oncomplete="if(#{DdsTransaction.flag=='false'}) {#{rich:element('ddsGrid')}.style.display=none;}else{#{rich:element('ddsGrid')}.style.display='';}" reRender="receiptNoText,ddsGrid,ddsList,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText,totalAmountText,post">
                                    <h:graphicImage id="imagerender" value="/resources/images/passed.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DdsTransaction.currentDdsGrid}"/>
                                </a4j:commandLink>
                            </rich:column>
                            <rich:column style="text-align:center">
                                <a4j:commandLink  ajaxSingle="true" id="selectlink1" oncomplete="#{rich:component('rowDeleteConfPanel1')}.show()" >
                                    <h:graphicImage id="imagerender1" value="/resources/images/delete.gif" style="border:0"/>
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{DdsTransaction.currentDdsGrid}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="stopDataScroll" align="left" for="ddsList" maxPages="20"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col8,col8" columns="1" id="slabGridPane91" style="text-align:left;" styleClass="row1" width="100%">
                        <h:outputLabel styleClass="output" value="Ctrl+E - For transaction Entry"/>
                        <h:outputLabel styleClass="output" value="Ctrl+A - For Authorization"/>
                    </h:panelGrid>

                    <h:panelGrid  id="stopPaymentFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="stopPaymentBtnPanel">
                            <a4j:commandButton id="post" value="#{DdsTransaction.processBtnValue}" action="#{DdsTransaction.postEntries}" reRender="rowDeleteConfPanel1,receiptNoText,ddsGrid,ddsList,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText,totalAmountText" oncomplete="if(#{DdsTransaction.flag=='true'}) {#{rich:element('ddsGrid')}.style.display='';}" focus="agentCodeList"/>
                            <a4j:commandButton id="Refresh" value="Refresh" action="#{DdsTransaction.refresh}" oncomplete="if(#{DdsTransaction.actypeFocus=='true'}){#{rich:element('acTypeList')}.focus();}" reRender="receiptNoText,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText,post"/>
                            <a4j:commandButton id="RefreshAll" value="Refresh All" action="#{DdsTransaction.refreshAll}" oncomplete="if(#{DdsTransaction.actypeFocus=='true'}){#{rich:element('acTypeList')}.focus();} #{rich:element('ddsGrid')}.style.display=none;" reRender="totalAmountText,ddsList,ddsGrid,receiptNoText,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText,post"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{DdsTransaction.exitBtnAction}" oncomplete="#{rich:element('ddsGrid')}.style.display=none;" reRender="receiptNoText,ddsGrid,ddsList,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText,totalAmountText"/>
                        </h:panelGroup>
                    </h:panelGrid>
                   
                </h:panelGrid>
                <a4j:jsFunction name="showTransactionsForm" action="#{DdsTransaction.showTransactionForm}"/>
                <a4j:jsFunction name="showAuthorizationForm" action="#{DdsTransaction.showAuthForm}"/>
            </a4j:form>
            <rich:hotKey key="Ctrl+E" handler="showTransactionsForm(); return false;"/>
            <rich:hotKey key="Ctrl+A" handler="showAuthorizationForm(); return false;"/>
             <rich:modalPanel id="rowDeleteConfPanel1" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are you sure to delete the transaction?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{DdsTransaction.deleteTransactionRows}" oncomplete="#{rich:component('rowDeleteConfPanel1')}.hide();if(#{DdsTransaction.flag=='false'}) {#{rich:element('ddsGrid')}.style.display=none;}else{#{rich:element('ddsGrid')}.style.display='';}" reRender="totalAmountText,ddsList,ddsGrid,receiptNoText,errMsg51,acTypeList,agentCodeList,agentNameText,acountNoList,amountText,nameText" focus="agentCodeList"/>
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('rowDeleteConfPanel1')}.hide();return false;"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
