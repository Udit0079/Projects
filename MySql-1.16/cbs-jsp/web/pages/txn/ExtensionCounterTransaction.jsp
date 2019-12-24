<%--
    Document   : ExtensionCounterTransaction
    Created on : Jul 31, 2010, 2:16:03 PM
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

            <title>Extension Counter Transaction</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{ExtensionCounterTransaction.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Extension Counter Transaction"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ExtensionCounterTransaction.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{ExtensionCounterTransaction.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{ExtensionCounterTransaction.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid  columns="1" id="gridPanel1" width="100%">
                            <h:panelGrid columns="2" id="a5" columnClasses="col9" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblExtensionCounter" styleClass="label" value="Extension Counter :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddExtCounter" tabindex="1" styleClass="ddlist" value="#{ExtensionCounterTransaction.extCon}" size="1" style="width: 200px">
                                    <f:selectItems value="#{ExtensionCounterTransaction.extensionCon}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col9" width="100%">
                                <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :"><font class="required" color="red">*</font></h:outputLabel>
                                <%--<h:selectOneListbox id="ddAcctNo" tabindex="2" styleClass="ddlist" value="#{ExtensionCounterTransaction.acctType}" size="1">
                                    <f:selectItems value="#{ExtensionCounterTransaction.accountType}" />
                                </h:selectOneListbox>--%>
                                <h:inputText id="txtAcNo" tabindex="2" maxlength="12" value="#{ExtensionCounterTransaction.oldAccNo}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{ExtensionCounterTransaction.getAccountDetail}" event="onblur"
                                                 reRender="errorMessage,message,a9,a10,a11,a12" limitToList="true" focus="ddType"/>
                                </h:inputText> 
                                <%--<h:inputText id="txtAcctNoAgentCode" tabindex="4" value="#{ExtensionCounterTransaction.agentCode}"  disabled="true" size="3" styleClass="input">
                                </h:inputText>--%>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a9" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblAcctNo1" styleClass="label" value="Account No. :"/>
                                <h:outputText id="stxtAcctNo1" styleClass="output" value="#{ExtensionCounterTransaction.acNo}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a10" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblAcctName" styleClass="label" value="Account Name :"/>
                                <h:outputText id="stxtAcctName" styleClass="output" value="#{ExtensionCounterTransaction.acName}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a11" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblBalance" styleClass="label" value="Balance :"/>
                                <h:outputText id="stxtBalance" styleClass="output" value="#{ExtensionCounterTransaction.balance}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a12" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lbltype" styleClass="label" value="Type :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddType" tabindex="5" styleClass="ddlist" size="1" value="#{ExtensionCounterTransaction.tranType}" style="width: 110px">
                                    <f:selectItems value="#{ExtensionCounterTransaction.tranTypeOption}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col9" columns="2" id="a13" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblBy" styleClass="label" value="By :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddBy" tabindex="6" styleClass="ddlist" size="1" value="#{ExtensionCounterTransaction.by}" style="width: 110px">
                                    <f:selectItems value="#{ExtensionCounterTransaction.byOption}"/>
                                </h:selectOneListbox>
                            </h:panelGrid> 
                            <h:panelGrid columnClasses="col9" columns="2" id="a14" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblAmount" styleClass="label" value="Amount :"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtAmount" maxlength="15" tabindex="7" value="#{ExtensionCounterTransaction.amount}" size="16" styleClass="input">
                                    <a4j:support actionListener="#{ExtensionCounterTransaction.loadGrid}" event="onblur" 
                                                 oncomplete="if(#{ExtensionCounterTransaction.errorMessage=='Please Enter Amount.'}){#{rich:element('txtAmount')}.focus();}"
                                                 reRender="errorMessage,message,a16,a9,a10,a11,a12,a13,a14,a17,a18" limitToList="true"/>
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col6" columns="1" id="a15" style="height:30px;" styleClass="row1" width="100%">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <h:selectOneListbox id="ddReport" tabindex="8" styleClass="ddlist" size="1" style="width: 300px" >
                                        <f:selectItems value="#{ExtensionCounterTransaction.acctNoOption}"/>
                                    </h:selectOneListbox>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col6" columns="1" id="reportBtn" style="height:30px;" styleClass="row2" width="100%">
                                <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                    <a4j:commandButton id="btnReportBasedOnExtCounter" tabindex="9" value="Report Based On Extension Counter" styleClass="btnBold" disabled="true" style="width: 250px"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="2" id="a8" width="100%">
                            <h:panelGrid columns="1" id="gridPanel3" width="100%">
                                <h:panelGrid columnClasses="vtop" columns="1" id="a16" style="height:265px;" styleClass="row1" width="100%">
                                    <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                        <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                       actionListener="#{ExtensionCounterTransaction.fetchCurrentRow}">
                                            <a4j:actionparam name="acNo" value="{acNo}" />
                                            <a4j:actionparam name="row" value="{currentRow}" />
                                        </rich:menuItem>
                                    </rich:contextMenu>
                                    <a4j:region>
                                        <rich:dataTable value="#{ExtensionCounterTransaction.transactionDetail}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="taskList" rows="7" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                        onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;
                                                        #{rich:component('menu')}.show(event,{acNo'#{dataItem.acNo}', currentRow:'#{row}'});return false;">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column colspan="6"><h:outputText value="Transaction Details" /></rich:column>
                                                    <rich:column breakBefore="true"><h:outputText value="Account No" /></rich:column>
                                                    <rich:column>
                                                        <h:outputText value="Credit Amount"/>
                                                    </rich:column>
                                                    <rich:column>
                                                        <h:outputText value="Debit Amount"/>
                                                    </rich:column>
                                                    <rich:column><h:outputText value="Customer Name" /></rich:column>
                                                    <rich:column visible="false"><h:outputText value="TY"/></rich:column>
                                                    <rich:column>
                                                        <h:outputText value="Action" />
                                                    </rich:column>
                                                </rich:columnGroup>
                                            </f:facet>

                                            <rich:column><h:outputText value="#{dataItem.acNo}" /></rich:column>
                                            <rich:column>
                                                <h:outputText value="#{dataItem.crAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                            </rich:column>
                                            <rich:column>
                                                <h:outputText value="#{dataItem.drAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                            </rich:column>
                                            <rich:column ><h:outputText value="#{dataItem.acctName}" /></rich:column>
                                            <rich:column visible="false"><h:outputText value="#{dataItem.ty}"/></rich:column>
                                            <rich:column>
                                                <a4j:commandLink ajaxSingle="true" id="deletelink" oncomplete="#{rich:component('deletePanel')}.show()">
                                                    <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{row}" target="#{ExtensionCounterTransaction.currentRow}" />
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller  align="left" for="taskList" maxPages="20" />
                                    </a4j:region>
                                    <rich:modalPanel id="deletePanel" autosized="true" width="200" onshow="#{rich:element('btnYesSave')}.focus();">
                                        <f:facet name="header">
                                            <h:outputText value="Are You Sure To Delete This Transaction?" style="padding-right:15px;"/>
                                        </f:facet>
                                        <h:form>
                                            <table width="100%">
                                                <tbody>
                                                    <tr>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="Yes" id="btnYesSave" ajaxSingle="true" action="#{ExtensionCounterTransaction.delete}" focus="ddAcctNo"
                                                                               oncomplete="#{rich:component('deletePanel')}.hide();" reRender="taskList,gridPanel3,a8,a16,a17,a18" />
                                                        </td>
                                                        <td align="center" width="50%">
                                                            <a4j:commandButton value="Cancel" id="btnNoSave" onclick="#{rich:component('deletePanel')}.hide();return false;" />
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </h:form>
                                    </rich:modalPanel>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="4" id="a17" style="height:30px;" styleClass="row2" width="100%">
                                    <h:outputLabel id="lblCrAmount" styleClass="label" value="Cr Amount :"/>
                                    <h:outputText id="stxtCrAmount" styleClass="output" value="#{ExtensionCounterTransaction.sumCrAmt}"/>
                                    <h:outputLabel id="lblDrAmount" styleClass="label" value="Dr Amount :"/>
                                    <h:outputText id="stxtDrAmount" styleClass="output" value="#{ExtensionCounterTransaction.sumDrAmt}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col9" columns="4" id="a18" style="height:30px;" styleClass="row1" width="100%">
                                    <h:outputLabel id="lblReciepts" styleClass="label" value="Reciepts :"/>
                                    <h:outputText id="stxtReciepts" styleClass="output" value="#{ExtensionCounterTransaction.reciept}"/>
                                    <h:outputLabel id="lblPayments" styleClass="label" value="Payments :"/>
                                    <h:outputText id="stxtPayments" styleClass="output" value="#{ExtensionCounterTransaction.payment}"/>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:region id="buttonReg">
                                <a4j:commandButton id="btnSave" tabindex="10" action="#{ExtensionCounterTransaction.saveTxnDetail}" value="Save" reRender="lpg,txtAcNo,a6,a8,a9,a10,a11,a12,a13,a15,a16,a17,a18,stxtCrAmount,stxtDrAmount,stxtReciepts,stxtPayments,errorMessage,message" focus="btnCancel"/>
                                <a4j:commandButton id="btnCancel" tabindex="11" action="#{ExtensionCounterTransaction.cancelTransaction}" value="Cancel" focus="ddAcctNo"
                                                   reRender="lpg,txtAcNo,a6,a8,a9,a10,a11,a12,a13,a15,a16,a17,a18,stxtCrAmount,stxtDrAmount,stxtReciepts,stxtPayments,errorMessage,message">
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" tabindex="12" value="Exit" action="#{ExtensionCounterTransaction.exitFrm}"/>
                            </a4j:region>
                        </h:panelGroup>
                    </h:panelGrid>

                </h:panelGrid>
                <a4j:status for="buttonReg" onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" height="60" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:outputText value="Processing..." />
                    </f:facet>
                    <h:outputText value="Please Wait..." />
                </rich:modalPanel>
                <rich:messages></rich:messages>
            </a4j:form>

        </body>
    </html>
</f:view>
