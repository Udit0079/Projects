<%-- 
    Document   : umrnstoppayment
    Created on : 8 Jul, 2016, 12:00:14 PM
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
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>UMRN Stop Payment</title>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{umrnStopPayment.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="UMRN Stop Payment"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{umrnStopPayment.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel2" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="message" styleClass="error" value="#{umrnStopPayment.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunctionType" styleClass="label" value="Function"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddMmsType" styleClass="ddlist" size="1" style="width:80px" value="#{umrnStopPayment.function}">
                            <f:selectItems value="#{umrnStopPayment.functionList}"/>
                            <a4j:support event="onblur" action="#{umrnStopPayment.functionAction}" reRender="message,btnProcess"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblUmrn" styleClass="label" value="UMRN" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtUmrn" maxlength="20" size="20" value="#{umrnStopPayment.umrn}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support event="onblur" action="#{umrnStopPayment.getUmrnDetails}" reRender="message,stxtCrName,stxtCollectionAmt,stxtMaxAmt,stxtFrequency,stxtCategory,stxtDebtorName,stxtDebtorAcno"/>
                        </h:inputText>
                        <h:outputLabel id="lblCrName" styleClass="label" value="Creditor Name"></h:outputLabel>
                        <h:outputText id="stxtCrName" styleClass="output" value="#{umrnStopPayment.creditorName}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row2" width="100%">
                        <h:outputLabel id="lblCollectionAmt" styleClass="label" value="Collection Amount"></h:outputLabel>
                        <h:outputText id="stxtCollectionAmt" styleClass="output" value="#{umrnStopPayment.collectionAmt}"/>
                        <h:outputLabel id="lblMaxAmt" styleClass="label" value="Max Amount"></h:outputLabel>
                        <h:outputText id="stxtMaxAmt" styleClass="output" value="#{umrnStopPayment.maxAmt}"/>
                        <h:outputLabel id="lblFrequency" styleClass="label" value="Frequency"></h:outputLabel>
                        <h:outputText id="stxtFrequency" styleClass="output" value="#{umrnStopPayment.frequency}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblCategory" styleClass="label" value="Category"></h:outputLabel>
                        <h:outputText id="stxtCategory" styleClass="output" value="#{umrnStopPayment.category}"/>
                        <h:outputLabel id="lblDebtorName" styleClass="label" value="Debtor Name"></h:outputLabel>
                        <h:outputText id="stxtDebtorName" styleClass="output" value="#{umrnStopPayment.debtorName}"/>
                        <h:outputLabel id="lblDebtorAcno" styleClass="label" value="Debtor A/c"></h:outputLabel>
                        <h:outputText id="stxtDebtorAcno" styleClass="output" value="#{umrnStopPayment.debtorAccount}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnProcess" value="#{umrnStopPayment.btnValue}" 
                                               oncomplete="#{rich:component('processPanel')}.show()" 
                                               reRender="message,processPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{umrnStopPayment.resetForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{umrnStopPayment.exitBtnAction}" reRender="mainPanel"/>
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
                                    <h:outputText id="confirmid" value="Are you sure to stop this UMRN."/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="Yes" id="btnYes" action="#{umrnStopPayment.processAction}" 
                                                       oncomplete="#{rich:component('processPanel')}.hide();" 
                                                       reRender="message,mainPanel"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>
