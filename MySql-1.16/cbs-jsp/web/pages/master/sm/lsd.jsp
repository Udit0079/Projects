<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lsd">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="SchemeLoanSchemeDetailsleftPanel3" style="width:100%;text-align:center;">
            <rich:panel  style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPrAddr" styleClass="label" />
                    <h:outputLabel id="lblPrCity" styleClass="label" value="Min"/>
                    <h:outputLabel id="lblPrState" styleClass="label" value="Max"/>
                    <h:outputText id="stxtDr1" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr2" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr3" styleClass="label"  style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLoanPeriodMiniMonths" styleClass="label" value="Loan Period Months"/>
                    <h:panelGroup id="BtnPanelLoanPeriodMiniMonths">
                        <h:inputText id="txtLoanPeriodMinMonths" styleClass="input" style="120px" value="#{lsd.periodMinMonths}" disabled="#{lsd.lsdFlag}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    </h:panelGroup>
                    <h:panelGroup id="BtnPanelClsMinLoanPeriodMiniMonths">
                        <h:inputText id="txtMaxLoanPeriodMaxMonths" styleClass="input" style="120px" value="#{lsd.periodMaxMonths}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lsd.lsdFlag}" maxlength="3">
                            <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    </h:panelGroup>
                    <h:outputText id="stxtDr4" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr5" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr6" styleClass="label"  style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblLoanPeriodMiniDays" styleClass="label" value="Loan Period Days"/>
                    <h:panelGroup id="BtnPanelLoanLoanPeriodMiniDays">
                        <h:inputText id="txtMinLoanPeriodMiniDays" styleClass="input" style="120px" value="#{lsd.periodMiniDays}" disabled="#{lsd.lsdFlag}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    </h:panelGroup>
                    <h:panelGroup id="BtnPanelClsLoanPeriodMiniDays">
                        <h:inputText id="txtMaxLoanPeriodMaxDays" styleClass="input" style="120px" value="#{lsd.periodMaxDays}" disabled="#{lsd.lsdFlag}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();">
                            <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    </h:panelGroup>
                    <h:outputText id="stxtDr7" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr8" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr9" styleClass="label"  style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow6" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLoanAmount" styleClass="label" value="Loan Amount"/>
                    <h:inputText id="txtAmountMin" styleClass="input" style="120px" value="#{lsd.amountMin}" disabled="#{lsd.lsdFlag }" maxlength="13" onkeyup="this.value = this.value.toUpperCase();">
                        <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    <h:inputText id="txtAmountMax" styleClass="input" style="120px" value="#{lsd.amountMax}" disabled="#{lsd.lsdFlag }" maxlength="13" onkeyup="this.value = this.value.toUpperCase();">
                        <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    <h:outputText id="stxtDr16" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr17" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr18" styleClass="label"  style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="addrRow7" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLoanRepaymentMethod" styleClass="label" value="Loan Repayment Method"/>
                    <h:selectOneListbox id="ddLoanRepaymentMethod" styleClass="ddlist" required="true" style="width: 120px" value="#{lsd.repaymentMethod}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.loanRepaymentMethod}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                  </h:selectOneListbox>
                    <h:outputLabel id="lblHoldInOpenAccountForAmountDue" styleClass="label" value="Hold In Open Account For Amount Due"/>
                    <h:selectOneListbox id="ddHoldInOpenAccountForAmountDue" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.holdInOpenAccountForAmount}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblUpfrontInstallmentCollection" styleClass="label" value="Upfront Installment Collection"/>
                    <h:selectOneListbox id="ddUpfrontInstallmentCollection" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.upfrontInstallmentCollection}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Interest Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="mailRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntBaseMethod" styleClass="label" value="Int Base Method"/>
                    <h:selectOneListbox id="ddIntBaseMethod" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.intBaseMethod}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddstmtFrqType}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntProductMethod" styleClass="label" value="Int Product Method"/>
                    <h:selectOneListbox id="ddIntProductMethod" styleClass="ddlist" required="true" style="width: 120px" value="#{lsd.intProduct}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.intProductMethod}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblRouteFlag" styleClass="label" value="Int Route lsdFlag"/>
                    <h:selectOneListbox id="ddRouteFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.route}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.routeFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="MailRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblChargeRouteFlag" styleClass="label" value="Charge Route lsdFlag"/>
                    <h:selectOneListbox id="ddChargeRouteFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.chargeRoute}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems  value="#{lsd.routeFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblLoanIntOrChrgAccountPlaceholder" styleClass="label" value="Loan Int Or Chrg Account Placeholder"/>
                    <h:inputText id="txtLoanIntOrChrgAccountPlaceholder" styleClass="input" style="120px" value="#{lsd.intOrChrgAccount}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lsd.lsdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                    <h:outputLabel id="lblMailPhone" styleClass="label" />
                    <h:outputLabel id="lblMailPhone1" styleClass="label" />
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Equated Installments" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblEquatedInstallments" styleClass="label" value="Equated Installments"/>
                    <h:selectOneListbox id="ddEquatedInstallments" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.equatedInstallments}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblEIINAdvance" styleClass="label" value="EI IN Advance"/>
                    <h:selectOneListbox id="ddEIINAdvance" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.eiINAdvance}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.eIINAdvance}" />
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblEIFormulaFlag" styleClass="label" value="EI Formula lsdFlag"/>
                    <h:selectOneListbox id="ddEIFormulaFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.eiFormulaFlag}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.eIFormulaFlag}" />
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblEIRoundingOffAmount" styleClass="label" value="EI Rounding Off Amount"/>
                    <h:selectOneListbox id="ddEIRoundingOffAmount" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.eiRoundingOffAmount}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.eIRoundingOffAmount}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblEIRoundingOffInd" styleClass="label" value="EI Rounding Off Ind"/>
                    <h:selectOneListbox id="ddEIRoundingOffInd" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.roundingOffInd}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.eIRoundingOffInd}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblCompoundingFreq" styleClass="label" value="Compounding Freq"/>
                    <h:selectOneListbox id="ddCompoundingFreq" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.compoundingFreq}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.ddstmtFrqType}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="ovrRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblEIPaymentFreq" styleClass="label" value="EI Payment Freq"/>
                    <h:selectOneListbox id="ddEIPaymentFreq" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.eiPaymentFreq}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.ddstmtFrqType}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblInterestRestFreq" styleClass="label" value="Interest Rest Freq"/>
                    <h:selectOneListbox id="ddInterestRestFreq" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.interestRestFreq}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddstmtFrqType}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblInterestRestBasis" styleClass="label" value="Interest Rest Basis"/>
                    <h:selectOneListbox id="ddInterestRestBasis" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.interestRestBasis}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.ddstmtFrqType}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Equated Installments" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="equRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblUpfrontInterestCollection" styleClass="label" value="Up front Interest Collection"/>
                    <h:selectOneListbox id="ddUpfrontInterestCollection" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.upfrontInterestCollection}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblDiscountedInterest" styleClass="label" value="Discounted Interest"/>
                    <h:selectOneListbox id="ddDiscountedInterest" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.discountedInterest}" size="1" disabled="#{lsd.lsdFlag}">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntAmortizationByRule78" styleClass="label" value="Int Amortization ByRule 78"/>
                    <h:selectOneListbox id="ddIntAmortizationByRule78" styleClass="ddlist" required="true" style="width: 110px" value="#{lsd.intAmortization}" size="1" disabled="#{lsd.lsdFlag }">
                        <f:selectItems value="#{lsd.ddturnoverDetailFlag}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
