<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<html>
    <body>
        <a4j:form id="ad">
            <h:panelGrid id="denomDtlPopUpForm" columns="1" style="width:100%;text-align:center;">
                <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                    <h:outputText id="errorMessage1" styleClass="error" value="#{CashDenominationDtl.errorMessage1}"/>
                    <h:inputHidden id="tyId" value="#{CashDenominationDtl.tyDenoValue}"/>
                    <h:inputHidden id="acNat" value="#{CashDenominationDtl.acDenoNat}"/>
                </h:panelGrid>
                <h:panelGrid id="panel6" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDenomination" value="Denomination :" styleClass="label"/>
                    <h:panelGroup>
                        <h:inputText id="txtDenoValue" value="#{CashDenominationDtl.denoValue}" styleClass="input" style="width:73px" disabled="#{CashDenominationDtl.disableDenoNo}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel value="Rs." styleClass="label"/>
                    </h:panelGroup>                    
                    <h:outputLabel id="lblNoOfNotes" value="No. of Notes / Coins :" styleClass="label"/>
                    <h:inputText id="txtDenoNo" value="#{CashDenominationDtl.denoNo}" styleClass="input" style="width:73px" disabled="#{CashDenominationDtl.disableDenoNo}">
                        <%--a4j:support event="onblur" action="#{CashDenominationDtl.onBlurDenominationNo}" focus="txtDenoValue" 
                                     reRender="errorMessage1,panel5,panel6,panel8,denominationTable" limitToList="true"/--%>
                    </h:inputText>                    
                </h:panelGrid>
                <h:panelGrid id="panel9" columnClasses="col13,col13,col13,col13" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                    <%--h:outputLabel id="lblOldNewFlg" value="Old/New :" styleClass="label"/>
                    <h:selectOneListbox id="ddOldNewFlg" styleClass="ddlist" size="1" value="#{CashDenominationDtl.selectOldNewFlg}" disabled="#{CashDenominationDtl.disableDenoNo}">
                        <f:selectItems value="#{CashDenominationDtl.oldNewFlgList}"/>
                    </h:selectOneListbox--%>
                    <h:outputLabel id="lbltyFlg" value="#{CashDenominationDtl.tyCaption}" styleClass="label"/>
                    <h:selectOneListbox id="ddtyFlg" styleClass="ddlist" size="1" value="#{CashDenominationDtl.tyFlg}" disabled="#{CashDenominationDtl.disableDenoNo}">
                        <f:selectItems value="#{CashDenominationDtl.tyList}"/>
                        <a4j:support event="onblur" action="#{CashDenominationDtl.onBlurDenominationNo}" focus="txtDenoValue"  reRender="errorMessage1,panel5,panel6,panel8,denominationTable,panel9" limitToList="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
                <h:panelGrid id="panel5" columnClasses="vtop"  columns="1" style="height:auto;" styleClass="row2" width="100%">
                    <a4j:region>
                        <rich:dataTable id="denominationTable"  rows="10" 
                                        value="#{CashDenominationDtl.denominationTable}" var="dataItem1" rowClasses="gridrow1,gridrow2" 
                                        columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column colspan="25"><h:outputText value="Denomination Detail" /></rich:column>
                                    <rich:column width="30%" breakBefore="true"><h:outputText value="Denomination Value" style="text-align:center"/></rich:column>
                                    <rich:column width="25%"><h:outputText value="No. of Notes / Coins" style="text-align:left" /></rich:column>
                                    <rich:column width="30%"><h:outputText value="Amount"/></rich:column>
                                    <%--rich:column width="30%"><h:outputText value="Old/New"/></rich:column--%>
                                    <rich:column width="15%"><h:outputText value="Receive/Return"/></rich:column>
                                    <rich:column width="15%"><h:outputText value="Update"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column style="text-align:right">
                                <h:outputText value="#{dataItem1.denoValue}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <rich:column style="text-align:right"><h:outputText value="#{dataItem1.denoNo}"/></rich:column>
                            <rich:column style="text-align:right">
                                <h:outputText value="#{dataItem1.denoAmount}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                            </rich:column>
                            <%--rich:column style="text-align:right"><h:outputText value="#{dataItem1.flag}"/></rich:column--%>
                            <rich:column style="text-align:right"><h:outputText value="#{dataItem1.tySh}"/></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="updateLink" action="#{CashDenominationDtl.setDenominationRowValues}" reRender="errorMessage1,lpg,panel6,panel8,panel9" ajaxSingle="true" focus="txtDenoValue">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem1}" target="#{CashDenominationDtl.currentDenominationItem}"/>
                                    <f:setPropertyActionListener value="#{row}" target="#{CashDenominationDtl.currentIDenominationRow}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="denominationTable" maxPages="10" />
                    </a4j:region>
                </h:panelGrid>
                <h:panelGrid id="panel8" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblTotalAmt" value="Total Amount :" styleClass="label"/>
                    <h:panelGroup>
                        <h:inputText id="txtTotalAmount" value="#{CashDenominationDtl.totalAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                        <h:outputLabel value="Rs." styleClass="label"/>
                    </h:panelGroup>
                    <h:outputLabel id="lblCurrencyAmt" value="Currency Amount :" styleClass="label"/>
                    <h:panelGroup>
                        <h:inputText id="txtTotalCurrencyAmount" value="#{CashDenominationDtl.currencyAmount}" styleClass="input" style="width:73px" readonly="true"/> &nbsp;
                        <h:outputLabel value="Rs." styleClass="label"/>
                    </h:panelGroup>
                </h:panelGrid>                
                <h:panelGrid id="denoFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                    <h:panelGroup id="denoBtnPanel">
                        <a4j:commandButton id="denoClose" value="Close" action="#{CashDenominationDtl.valdateClose}" oncomplete="if(#{CashDenominationDtl.closeDeno==true}){#{rich:component('cashDenoView')}.hide();return false;}"
                                           reRender="errorMessage1"/>
                    </h:panelGroup>
                </h:panelGrid>
            </h:panelGrid>
        </a4j:form>
    </body>
</html>