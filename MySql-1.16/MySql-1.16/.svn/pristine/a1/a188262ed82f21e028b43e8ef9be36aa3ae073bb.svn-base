<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lpesd">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="SchemeLoanPreEISetupDetailsPanel11" style="width:100%;text-align:center;">
            <rich:panel header="Pre EI Setup Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="EISetup" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNormalHolidayPeriodMonths" styleClass="label" value="Normal Holiday Period Months"/>
                    <h:inputText id="txtNormalHolidayPeriodMonths" styleClass="input" style="width:120px" value="#{lpesd.normalHolidayPeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                         <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblMaxHolidayPeriodAllowedMonths" styleClass="label" value="Max Holiday Period Allowed Months"/>
                    <h:inputText id="txtMaxHolidayPeriodAllowedMonths" styleClass="input" style="width:120px" value="#{lpesd.maxHolidayPeriodAllowedMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblIntDuringHolidayPeriod" styleClass="label" value="Int During Holiday Period"/>
                    <h:selectOneListbox id="ddIntDuringHolidayPeriod" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.intDuringHolidayPeriod}" size="1" disabled="#{lpesd.lpesdFlag }">
                        <f:selectItems value="#{lpesd.ddintDuringHolidayPeriod}"/>
                         <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="" columns="2" id="EISetup1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblIntFreqDuringHolidayPeriodType" styleClass="label" value="Int Freq During Holiday Period Type/No/Day/Start Date/NP"/>
                    <h:panelGroup id="groupPanelIntFreqDuringHolidayPeriodType" layout="block">
                        <h:selectOneListbox id="ddIntFreqDuringHolidayPeriodType" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.intFreqDuringHolidayPeriodType}" size="1" disabled="#{lpesd.lpesdFlag }">
                            <f:selectItems value="#{lpesd.ddturnoverFreqType}"/>
                             <a4j:support event="onblur" ajaxSingle="true" /> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntFreqDuringHolidayPeriodWeekNo" styleClass="label" value="/"/>
                        <h:selectOneListbox id="ddIntFreqDuringHolidayPeriodWeekNo" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.intFreqDuringHolidayPeriodWeekNo}" size="1" disabled="#{lpesd.lpesdFlag }">
                            <f:selectItems value="#{lpesd.ddturnoverFreqNo}"/>
                             <a4j:support event="onblur" ajaxSingle="true" /> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntFreqDuringHolidayPeriodWeekDay" styleClass="label" value="/"/>
                        <h:selectOneListbox id="ddIntFreqDuringHolidayPeriodWeekDay" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.intFreqDuringHolidayPeriodWeekDay}" size="1" disabled="#{lpesd.lpesdFlag }">
                            <f:selectItems value="#{lpesd.ddturnoverFreqDay}"/>
                             <a4j:support event="onblur" ajaxSingle="true" /> 
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIntFreqDuringHolidayPeriodStartDate" styleClass="label" value="/"/>
                        <h:inputText id="txtIntFreqDuringHolidayPeriodStartDate" styleClass="input" style="width:40px" value="#{lpesd.intFreqDuringHolidayPeriodStartDate}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                        <h:outputLabel id="lblIntFreqDuringHolidayPeriodNp" styleClass="label" value="/"/>
                        <h:selectOneListbox id="ddIntFreqDuringHolidayPeriodNp" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.intFreqDuringHolidayPeriodNp}" size="1" disabled="#{lpesd.lpesdFlag }">
                            <f:selectItems value="#{lpesd.ddturnoverFreqNp}"/>
                             <a4j:support event="onblur" ajaxSingle="true" /> 
                        </h:selectOneListbox>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="EISetup2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMultipleDisbursementsAllowed" styleClass="label" value="Multiple Disbursements Allowed"/>
                    <h:selectOneListbox id="ddMultipleDisbursementsAllowed" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.multipleDisbursementsAllowed}" size="1" disabled="#{lpesd.lpesdFlag }">
                        <f:selectItems value="#{lpesd.ddtrnRefNo}"/>
                         <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAutoProcessAfterHolidayPeriod" styleClass="label" value="Auto Process After Holiday Period"/>
                    <h:selectOneListbox id="ddAutoProcessAfterHolidayPeriod" styleClass="ddlist" required="true" style="width:120px" value="#{lpesd.autoProcessAfterHolidayPeriod}" size="1" disabled="#{lpesd.lpesdFlag }">
                        <f:selectItems value="#{lpesd.ddtrnRefNo}"/>
                         <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:selectOneListbox>
                    <h:outputLabel id="lblOddDaysInt" styleClass="label" value="Odd Days Int"/>
                    <h:inputText id="txtOddDaysInt" styleClass="input" style="width:120px" value="#{lpesd.oddDaysInt}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Maturity Processing" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="EISetup3" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblResidualBalanceWaiverLimit" styleClass="label" value="Residual Balance Waiver Limit"/>
                    <h:inputText id="txtResidualBalanceWaiverLimit" styleClass="input" style="width:120px" value="#{lpesd.residualBalanceWaiverLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblResidualBalanceAbsorbLimit" styleClass="label" value="Residual Balance Absorb Limit"/>
                    <h:inputText id="txtResidualBalanceAbsorbLimit" styleClass="input" style="width:120px" value="#{lpesd.residualBalanceAbsorbLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblPlaceholderForResidualBalanceWaiver" styleClass="label" value="Placeholder For Residual Balance Waiver"/>
                    <h:inputText id="txtPlaceholderForResidualBalanceWaiver" styleClass="input" style="width:120px" value="#{lpesd.placeholderForResidualBalanceWaiver}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="EISetup4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPlaceholderForResidualBalanceAbsorb" styleClass="label" value="Placeholder For Residual Balance Absorb"/>
                    <h:inputText id="txtPlaceholderForResidualBalanceAbsorb" styleClass="input" style="width:120px" value="#{lpesd.placeholderForResidualBalanceAbsorb}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblMaxCycleForPromptPaymentDiscount" styleClass="label" value="Max Cycle For Prompt Payment Discount"/>
                    <h:inputText id="txtMaxCycleForPromptPaymentDiscount" styleClass="input" style="width:120px" value="#{lpesd.maxCycleForPromptPaymentDiscount}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                    <h:outputLabel id="lblEventIDForPromptPaymentDiscount" styleClass="label" value="Event ID For Prompt Payment Discount"/>
                    <h:inputText id="txtEventIDForPromptPaymentDiscount" styleClass="input" style="width:120px" value="#{lpesd.eventIDForPromptPaymentDiscount}" maxlength="25" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpesd.lpesdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" /> 
                    </h:inputText>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
