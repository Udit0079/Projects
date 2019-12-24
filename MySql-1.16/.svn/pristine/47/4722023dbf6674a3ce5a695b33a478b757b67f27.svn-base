<%-- 
    Document   : moneyExchangeRecord
    Created on : Nov 11, 2016, 11:25:37 AM
    Author     : Admin
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
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet">
            <meta http-equiv="Pragma" content="no-cache">
            <meta http-equiv="Cache-Control" content="no-cache">
            <title>Money Exchange Record</title>
        </head>
        <body>
            <a4j:form id="formMoneyExchRecord">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{MoneyExchRecord.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Money Exchange Record"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{MoneyExchRecord.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                        <h:outputText id="message" styleClass="error" value="#{MoneyExchRecord.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col3,col1,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblopt" styleClass="label" value="Option"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOpt" styleClass="ddlist" style="width:150px" size="1" value="#{MoneyExchRecord.option}">
                                <f:selectItems value="#{MoneyExchRecord.optList}"/>
                                <a4j:support actionListener="#{MoneyExchRecord.changeFunction()}" event="onblur" reRender="btnSave,taskList,gridPanele,message,mainPanel" focus="txtTendName"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblTendName" styleClass="label" value="Tenderer Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtTendName" styleClass="input" style="width:150px" value="#{MoneyExchRecord.tendName}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{MoneyExchRecord.disabled}"/>
                            <h:outputLabel id="labelIdenProof" styleClass="label" value="Identity Proof"/>
                            <h:selectOneListbox id="ddIdProof" styleClass="ddlist" style="width:150px" size="1" value="#{MoneyExchRecord.idProof}" disabled="#{MoneyExchRecord.disabled}">
                                <f:selectItems value="#{MoneyExchRecord.idProofList}"/>
                                <a4j:support event="onblur" focus="txtIdenNo"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col3,col1,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblIdPrNo" styleClass="label" value="Identification Number"></h:outputLabel>
                            <h:inputText id="txtIdenNo" styleClass="input" value="#{MoneyExchRecord.idNo}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{MoneyExchRecord.disabled}" />
                            <h:outputLabel id="labelPlace" styleClass="label" value="Place"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddPlace" styleClass="ddlist" style="width:150px" size="1" value="#{MoneyExchRecord.place}" disabled="#{MoneyExchRecord.disabled}">
                                <f:selectItems value="#{MoneyExchRecord.placeList}"/>
                                <a4j:support ajaxSingle="true" event="onblur" focus="txtXcAmt"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblxcAmt" styleClass="label" value="Exchange Amount"></h:outputLabel>
                            <h:inputText id="txtXcAmt" styleClass="input" value="#{MoneyExchRecord.exchgAmt}" style="width:73px" disabled="#{MoneyExchRecord.disabled}">
                                <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                <a4j:support ajaxSingle="true" event="onblur" focus="#{MoneyExchRecord.focusId}" action="#{MoneyExchRecord.checkXchangeLimit}" reRender="message"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col1,col3,col1,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="labelReturnPaid" styleClass="label" value="Return/Paid"/>
                            <h:selectOneListbox id="ddReturnPaid" styleClass="ddlist" style="width:100px" size="1" value="#{MoneyExchRecord.returnPaid}" >
                                <f:selectItems value="#{MoneyExchRecord.returnPaidList}"/>
                                <a4j:support ajaxSingle="true" event="onblur" reRender="denInCount,message,denInWord"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblFiDen" styleClass="label" value="Denomination"></h:outputLabel>
                            <h:inputText id="txtFiDenNo" styleClass="input" value="#{MoneyExchRecord.denomination}" style="width:73px" >
                                <a4j:support ajaxSingle="true" event="onblur" focus="txtThDenNo"/>
                            </h:inputText>
                            <h:outputLabel id="lblThDen" styleClass="label" value="Number Of Notes/Coins"></h:outputLabel>
                            <h:inputText id="txtThDenNo" styleClass="input" value="#{MoneyExchRecord.noOfNoteCoins}" style="width:73px">
                                <a4j:support ajaxSingle="true" event="onblur" action="#{MoneyExchRecord.addToGrid}" reRender="txtFiDenNo,txtThDenNo,denInCount,message,denInWord,denominationTab,paidAmt,paidAmtWord" />
                            </h:inputText>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col1,col3,col1,col3,col3,col3" columns="6" id="Row24" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblTot" styleClass="label" value="Total Received"></h:outputLabel>
                        <h:outputText id="denInCount" styleClass="output" value="#{MoneyExchRecord.denCnt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                        <h:outputText id="denInWord" styleClass="msg" value="#{MoneyExchRecord.denWord}"/>
                        <h:outputLabel id="lblPaid" styleClass="label" value="Total Paid"></h:outputLabel>
                        <h:outputText id="paidAmt" styleClass="output" value="#{MoneyExchRecord.paidAmt}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                        <h:outputText id="paidAmtWord" styleClass="msg" value="#{MoneyExchRecord.paidAmtWord}"/>
                    </h:panelGrid>
                    <h:panelGrid id="panel10" bgcolor="#fff"  columns="1" width="100%">
                        <h:panelGrid id="panel9" columnClasses="vtop"  columns="1" style="height:auto;" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable id="denominationTab" rows="20" value="#{MoneyExchRecord.denominationTab}" var="dataItem1" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="25"><h:outputText value="Cash Denomination Detail" /></rich:column>
                                            <rich:column width="30%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                            <rich:column width="25%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                            <rich:column width="30%"><h:outputText value="Amount"/></rich:column>
                                            <rich:column width="30%"><h:outputText value="Received/Paid"/></rich:column>
                                            <rich:column width="15%"><h:outputText value="Action"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem1.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:right"><h:outputText value="#{dataItem1.denoNo}"/></rich:column>
                                    <rich:column style="text-align:right">
                                        <h:outputText value="#{dataItem1.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:left">
                                        <h:outputText value="#{dataItem1.tySh}"></h:outputText>
                                    </rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="updateLink1" action="#{MoneyExchRecord.setDenoRowValues}" reRender="message,denominationTab,Row24,Row3">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem1}" target="#{MoneyExchRecord.currentDenominationItm}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="denominationTab" maxPages="10" />
                            </a4j:region>                            
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="2" id="gridPanele" width="100%" styleClass="row1" style="height:150px;">
                        <a4j:region>
                            <rich:dataTable value="#{MoneyExchRecord.monExchTable}" var="dataItem" id="taskList" rows="4" columnsWidth="100" rowKeyVar="row" rowClasses="gridrow1,gridrow2"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="6"><h:outputText value="Money Exchange Information" /></rich:column>
                                        <rich:column breakBefore="true" style="text-align:center;width:300px"><h:outputText value="Tenderer Name"/></rich:column>
                                        <rich:column><h:outputText value="Identity Proof"/></rich:column>
                                        <rich:column><h:outputText value="Identity No." /></rich:column>
                                        <rich:column><h:outputText value="Total" /></rich:column>
                                        <rich:column><h:outputText value="Place" /></rich:column>
                                        <rich:column><h:outputText value="Action" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.tendName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.idenProof}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.idenNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.totVal}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.place}"/></rich:column>
                                <rich:column style="text-align:center;width:50px">
                                    <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{MoneyExchRecord.setDataToForm}" oncomplete="setMask();
                                                     if(#{MoneyExchRecord.option=='V'}){#{rich:element('btnSave')}.focus();}else{#{rich:element('txtTendName')}.focus();}"
                                                     reRender="message,mainPanel">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{MoneyExchRecord.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="scrollerId" align="left" for="taskList" maxPages="20"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" value="#{MoneyExchRecord.btnCaption}" action="#{MoneyExchRecord.save}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{MoneyExchRecord.refreshForm}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{MoneyExchRecord.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>

