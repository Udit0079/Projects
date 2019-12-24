<%--
    Document   : cashreceiving
    Created on : Aug 19, 2011, 2:25:48 PM
    Author     : Administrator
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Cash Receiving</title>
        </head>
        <body>
            <a4j:keepAlive beanName="CashReceiving"/>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="headerPanel" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CashReceiving.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Cash Receiving"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="Username: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CashReceiving.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="errPanel" style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText value="#{CashReceiving.errorMessage}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel1" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="acType" styleClass="output" value="A/c Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="acTypeList" styleClass="ddlist" size="1" style="width:120px" value="#{CashReceiving.accountType}">
                            <f:selectItems value="#{CashReceiving.accountTypeList}"/>
                            <a4j:support event="onblur" action="#{CashReceiving.onBlurAcType}" reRender="errPanel"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Pending Agent Codes For Cash Receiving" styleClass="label"/>
                        <h:selectOneListbox id="ddPendingAgents" styleClass="ddlist" size="1" value="#{CashReceiving.pendingAgent}">
                            <f:selectItems value="#{CashReceiving.pendingAgents}"/>
                            <a4j:support event="onblur" action="#{CashReceiving.onBlurAgentCode}" reRender="errPanel,txtAgentName,panel3,panel8,panel5,btnReceive,txtDenoValue,stxtTotalAmt" oncomplete="if(#{CashReceiving.denoPanel=='true'}) {#{rich:element('panel5')}.style.display='';#{rich:element('txtDenoValue')}.focus();}else{#{rich:element('btnReceive')}.focus();}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid id="panel2" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel value="Total Amount :" styleClass="label"/>
                        <h:outputText id="stxtTotalAmt" value="#{CashReceiving.amount}" styleClass="output"/>
                        <h:outputLabel value="Agent Name :" styleClass="label"/>
                        <h:inputText id="txtAgentName" value="#{CashReceiving.agentName}" readonly="true" styleClass="input"/>
                    </h:panelGrid>

                    <h:panelGrid id="panel3" columnClasses="col2,col2" columns="2" style="width:100%;text-align:left;" styleClass="row2">
                        <rich:panel id="panel4">
                            <h:panelGrid id="panel9" columnClasses="vtop" columns="1" style="height:380px" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable id="entryTable" value="#{CashReceiving.entryTable}" var="dataItem" rowClasses="gridrow1,gridrow2" rows="6" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                <rich:column><h:outputText value="Receipt No." style="text-align:center"/></rich:column>
                                                <rich:column><h:outputText value="Account No." style="text-align:left" /></rich:column>
                                                <rich:column><h:outputText value="Amount" style="text-align:left"/></rich:column>
                                                <rich:column><h:outputText value="Entered By" style="text-align:left" /></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.receiptNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.accountNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.amount}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.enteredBy}" /></rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="entryTable" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                        </rich:panel>

                        <rich:panel style="display:#{CashReceiving.denoPanelValue};">
                            <h:panelGrid id="panel5" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8;" width="100%">
                                <h:panelGrid id="panel6" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel value="Denomination" styleClass="label"/>
                                    <h:panelGroup>
                                        <h:inputText id="txtDenoValue" value="#{CashReceiving.denoValue}" styleClass="input" style="width:73px" disabled="#{CashReceiving.disableDenoNo}"/> &nbsp;
                                        <h:outputLabel value="Rs." styleClass="label"/>
                                    </h:panelGroup>                                        
                                    <h:outputLabel value="No of Notes / Coins" styleClass="label"/>
                                    <h:inputText id="txtDenoNo" value="#{CashReceiving.denoNo}" styleClass="input" style="width:73px" disabled="#{CashReceiving.disableDenoNo}">
                                        <%--a4j:support event="onblur" action="#{CashReceiving.onBlurDenominationNo}" oncomplete="#{rich:element('panel5')}.style.display='';" focus="txtDenoValue" reRender="errPanel,panel6,btnReceive,panel4,panel5"/--%>
                                        <a4j:support event="onblur"/>
                                    </h:inputText>
                                </h:panelGrid>
                                <h:panelGrid id="panel10" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row2">
                                    <%--h:outputLabel id="lblOldNewFlg" value="Old/New :" styleClass="label"/>
                                    <h:selectOneListbox id="ddOldNewFlg" styleClass="ddlist" size="1" value="#{CashReceiving.selectOldNewFlg}" disabled="#{CashReceiving.disableDenoNo}">
                                        <f:selectItems value="#{CashReceiving.oldNewFlgList}"/>
                                        <a4j:support event="onblur"/>
                                    </h:selectOneListbox--%>
                                    <h:outputLabel id="lbltyFlg" value="Recived/Return :" styleClass="label"/>
                                    <h:selectOneListbox id="ddtyFlg" styleClass="ddlist" size="1" value="#{CashReceiving.tyFlg}" disabled="#{CashReceiving.disableDenoNo}">
                                        <f:selectItems value="#{CashReceiving.tyList}"/>
                                        <a4j:support event="onblur" action="#{CashReceiving.onBlurDenominationNo}" oncomplete="#{rich:element('panel5')}.style.display='';" focus="txtDenoValue" reRender="message,errorMessage,lpg,taskList,gpFooter,errPanel,panel6,btnReceive,panel4,panel5,panel10"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                </h:panelGrid>        

                                <h:panelGrid id="panel7" columnClasses="vtop" columns="1" style="height:300px;" styleClass="row2" width="100%">
                                    <a4j:region>
                                        <rich:dataTable id="denominationTable" value="#{CashReceiving.denominationTable}" var="dataItem" rowClasses="gridrow1,gridrow2" columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column width="15%"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                                    <rich:column width="15%"><h:outputText value="Denomination No." style="text-align:left" /></rich:column>
                                                    <rich:column width="25%"><h:outputText value="Amount" style="text-align:left"/></rich:column>
                                                    <%--rich:column width="15%"><h:outputText value="Old/New"/></rich:column--%>
                                                    <rich:column width="15%"><h:outputText value="Receive/Return"/></rich:column>
                                                    <rich:column width="15%"><h:outputText value="Update" style="text-align:left" /></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.denoNo}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText></rich:column>
                                            <%--rich:column style="text-align:right"><h:outputText value="#{dataItem.flag}"/></rich:column--%>
                                            <rich:column style="text-align:right"><h:outputText value="#{dataItem.tySh}"/></rich:column>
                                            <rich:column style="text-align:center;width:40px">
                                                <a4j:commandLink id="updateLink" action="#{CashReceiving.setDenominationRowValues}" reRender="panel6,panel7,panel8,btnReceive,panel10" ajaxSingle="true" focus="txtDenoValue">
                                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{dataItem}" target="#{CashReceiving.currentDenominationItem}"/>
                                                    <f:setPropertyActionListener value="#{row}" target="#{CashReceiving.currentIDenominationRow}"/>
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="denominationTable" maxPages="10" />
                                    </a4j:region>
                                </h:panelGrid>

                                <h:panelGrid id="panel8" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel value="Total Amount" styleClass="label"/>
                                    <h:panelGroup>
                                        <h:inputText id="txtTotalAmount" value="#{CashReceiving.totalAmount}" styleClass="input" style="width:73px" readonly="true"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:inputText> &nbsp;
                                        <h:outputLabel value="Rs." styleClass="label"/>
                                    </h:panelGroup>
                                    <h:outputLabel value="Currency Amount" styleClass="label"/>
                                    <h:panelGroup>
                                        <h:inputText id="txtTotalCurrencyAmount" value="#{CashReceiving.currencyAmount}" styleClass="input" style="width:73px" readonly="true"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:inputText> &nbsp;
                                        <h:outputLabel value="Rs." styleClass="label"/>
                                    </h:panelGroup>
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:panel>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnReceive" value="Receive" action="#{CashReceiving.receiveBtnAction}" disabled="#{CashReceiving.disableReveiveButton}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnCancel" value="Refresh" type="reset" action="#{CashReceiving.cancelBtnAction}" reRender="mainPanel"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CashReceiving.exitBtnAction}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
