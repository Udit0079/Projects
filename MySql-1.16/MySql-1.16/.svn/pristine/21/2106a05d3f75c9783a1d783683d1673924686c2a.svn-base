<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="dspm">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel1" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblDepositAmountMinimum" styleClass="label" value="Deposit Amount Minimum/Maximum"/>
                <h:panelGroup id="groupPanelDepositAmountMinimum" layout="block">
                    <h:inputText id="txtDepositAmountMinimum" styleClass="input" style="width:50px" value="#{dspm.depositAmountMinimum}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDepositAmountMaximum" styleClass="label" value="/"/>
                    <h:inputText id="txtDepositAmountMaximum" styleClass="input" style="width:50px" value="#{dspm.depositAmountMaximum}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputLabel id="lblDepositAmountSteps" styleClass="label" value="Deposit Amount Steps"/>
                <h:inputText id="txtDepositAmountSteps" styleClass="input" style="width:100px" value="#{dspm.depositAmountSteps}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblPeriodMiniMonths" styleClass="label" value="Period Mini Months/Days"/>
                <h:panelGroup id="groupPanelPeriodMiniMonths" layout="block">
                    <h:inputText id="txtPeriodMiniMonths" styleClass="input" style="width:50px" value="#{dspm.periodMiniMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPeriodMiniDays" styleClass="label" value="/"/>
                    <h:inputText id="txtPeriodMiniDays" styleClass="input" style="width:50px" value="#{dspm.periodMiniDaysMain}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGroup>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblPeriodMaxMonths" styleClass="label" value="Period Max Months/Days"/>
                <h:panelGroup id="groupPanelPeriodMaxMonths" layout="block">
                    <h:inputText id="txtPeriodMaxMonths" styleClass="input" style="width:60px" value="#{dspm.periodMaxMonthsMain}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPeriodMaxDays" styleClass="label" value="/"/>
                    <h:inputText id="txtPeriodMaxDays" styleClass="input" style="width:60px" value="#{dspm.periodMaxDaysMain}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputLabel id="lblPeriodStepsMonths" styleClass="label" value="Period Steps Months/Days"/>
                <h:panelGroup id="groupPeriodStepsMonths" layout="block">
                    <h:inputText id="txtPeriodStepsMonths" styleClass="input" style="width:60px" value="#{dspm.periodStepsMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPeriodStepDays" styleClass="label" value="/"/>
                    <h:inputText id="txtPeriodStepDays" styleClass="input" style="width:60px" value="#{dspm.periodStepDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGroup>
                <h:outputLabel id="lblDepositReportTemplate" styleClass="label" value="Deposit Report Template"/>
                <h:inputText id="txtDepositReportTemplate" styleClass="input" style="width:120px" value="#{dspm.depositReportTemplate}" maxlength="25" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow5" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblDepositType" styleClass="label" value="Deposit Type"/>
                <h:selectOneListbox id="ddDepositType" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.depositType}"  disabled="#{dspm.dspmFlag }">
                    <f:selectItems value="#{dspm.ddDepositType}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblAutoRenewal" styleClass="label" value="Auto Renewal"/>
                <h:selectOneListbox id="ddAutoRenewal" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.autoRenewalMain}"  disabled="#{dspm.dspmFlag }">
                    <f:selectItems value="#{dspm.ddautoRenewal}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblMaxRenewalAllowed" styleClass="label" value="Max Renewal Allowed"/>
                <h:inputText id="txtMaxRenewalAllowed" styleClass="input" style="width:120px" value="#{dspm.maxRenewalAllowed}" maxlength="1" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow7" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblRenewalPeriodMonths" styleClass="label" value="Renewal Period Months"/>
                <h:inputText id="txtRenewalPeriodMonths" styleClass="input" style="width:120px" value="#{dspm.renewalPeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblRenewalPeriodDays" styleClass="label" value="Renewal Period Days"/>
                <h:inputText id="txtRenewalPeriodDays" styleClass="input" style="width:120px" value="#{dspm.renewalPeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblRenewalAllowedPeriod" styleClass="label" value="Renewal Allowed Period"/>
                <h:inputText id="txtRenewalAllowedPeriod" styleClass="input" style="width:120px" value="#{dspm.renewalAllowedPeriod}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow8" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblAutoRenewalGracePeriod" styleClass="label" value="Auto Renewal Grace Period"/>
                <h:inputText id="txtAutoRenewalGracePeriod" styleClass="input" style="width:120px" value="#{dspm.autoRenewalGracePeriod}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblSundryDepositPlaceholder" styleClass="label" value="Sundry Deposit Place holder"/>
                <h:inputText id="txtSundryDepositPlaceholder" styleClass="input" style="width:120px" value="#{dspm.sundryDepositPlaceholder}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblRepaymentReportCode" styleClass="label" value="RepaymentReportCode"/>
                <h:inputText id="txtRepaymentReportCode" styleClass="input" style="width:120px" value="#{dspm.repaymentReportCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow9" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblValueDatedClosure" styleClass="label" value="Value Dated Closure"/>
                <h:selectOneListbox id="ddValueDatedClosure" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.valueDatedClosure}"  disabled="#{dspm.dspmFlag}">
                    <f:selectItems value="#{dspm.ddDspmTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCallDepositNoticePeriodMonths" styleClass="label" value="Call Deposit Notice Period Months"/>
                <h:inputText id="txtCallDepositNoticePeriodMonths" styleClass="input" style="width:120px" value="#{dspm.callDepositNoticePeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblCallDepositNoticePeriodDays" styleClass="label" value="Call Deposit Notice Period Days"/>
                <h:inputText id="txtCallDepositNoticePeriodDays" styleClass="input" style="width:120px" value="#{dspm.callDepositNoticePeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <rich:panel header="Recurring Deposit Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="7" id="genRow11" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDelayInstallmentTblCode" styleClass="label" value="Delay Installment Table Code "/>
                    <h:inputText id="txtDelayInstallmentTblCode" styleClass="input" style="width:120px" value="#{dspm.delayInstallmentTblCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>

                    </h:inputText>
                    <h:outputLabel id="lblDelayWithinMonth" styleClass="label" value="Delay Within Month"/>
                    <h:inputText id="txtDelayWithinMonth" styleClass="input" style="width:120px" value="#{dspm.delayWithinMonth}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDelayAllowedPeriodMonths" styleClass="label" value="Delay Allowed Period Months"/>
                    <h:inputText id="txtDelayAllowedPeriodMonths" styleClass="input" style="width:120px" value="#{dspm.delayAllowedPeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow12" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblDelayAllowedPeriodDays" styleClass="label" value="Delay Allowed PeriodDays"/>
                    <h:inputText id="txtDelayAllowedPeriodDays" styleClass="input" style="width:120px" value="#{dspm.delayAllowedPeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPenalFeePlaceholder" styleClass="label" value="Penal Fee Placeholder"/>
                    <h:inputText id="txtPenalFeePlaceholder" styleClass="input" style="width:120px" value="#{dspm.penalFeePlaceholder}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPenalFeeReportCode" styleClass="label" value="PenalFeeReportCode"/>
                    <h:inputText id="txtPenalFeeReportCode" styleClass="input" style="width:120px" value="#{dspm.penalFeeReportCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow13" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPrintPBDR" styleClass="label" value="Print PB/DR"/>
                    <h:selectOneListbox id="ddPrintPBDR" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.printPBDR}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddPrintPB}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMatAmtToleranceLimit" styleClass="label" value="Mat Amt Tolerance Limit"/>
                    <h:selectOneListbox id="ddMatAmtToleranceLimit" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.matAmtToleranceLimit}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddMatAmt}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblUseInventory" styleClass="label" value="Use Inventory"/>
                    <h:selectOneListbox id="ddUseInventory" styleClass="ddlist"  size="1" style="width:120px" value="#{dspm.useInventory}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddDspmTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow14" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblInventoryClass" styleClass="label" value="Inventory Class"/>
                    <h:inputText id="txtInventoryClass" styleClass="input" style="width:120px" value="#{dspm.inventoryClass}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblInventoryType" styleClass="label" value="InventoryType"/>
                    <h:inputText id="txtInventoryType" styleClass="input" style="width:120px" value="#{dspm.inventoryType}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblInventoryLoanClass" styleClass="label" value="Inventory Loan Class"/>
                    <h:inputText id="txtInventoryLoanClass" styleClass="input" style="width:120px" value="#{dspm.inventoryLoanClass}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow15" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblInventoryLoanCode" styleClass="label" value="Inventory Loan Code"/>
                    <h:inputText id="txtInventoryLoanCode" styleClass="input" style="width:120px" value="#{dspm.inventoryLoanCode}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblCommissionPlaceholder" styleClass="label" value="Commission Placeholder"/>
                    <h:inputText id="txtCommissionPlaceholder" styleClass="input" style="width:120px" value="#{dspm.commissionPlaceholder}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblServiceChargeTblCode" styleClass="label" value="ServiceChargeTblCode"/>
                    <h:inputText id="txtServiceChargeTblCode" styleClass="input" style="width:120px" value="#{dspm.serviceChargeTblCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow16" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCommissionTblCode" styleClass="label" value="Commission Tbl Code"/>
                    <h:selectOneListbox id="ddCommissionTblCode" styleClass="ddlist" size="1" style="width:120px" value="#{dspm.commissionTblCode}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddPurposeOfAdvance}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPrePartClosurePenaltyCode" styleClass="label" value="Pre/Part Closure Penalty Code"/>
                    <h:inputText id="txtPrePartClosurePenaltyCode" styleClass="input" style="width:120px" value="#{dspm.prePartClosurePenaltyCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblCommissionReportCode" styleClass="label" value="Commission Report Code"/>
                    <h:inputText id="txtCommissionReportCode" styleClass="input" style="width:120px" value="#{dspm.commissionReportCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dspm.dspmFlag}">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="genRow17" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblAllowSweepsmain" styleClass="label" value="Allow Sweeps"/>
                    <h:selectOneListbox id="ddAllowSweepsmain" styleClass="ddlist"  size="1" style="width:120px" value="#{dspm.allowSweepsmain}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddDspmTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAllowPartClosure" styleClass="label" value="AllowPartClosure"/>
                    <h:selectOneListbox id="ddAllowPartClosure" styleClass="ddlist"  size="1" style="width:120px" value="#{dspm.allowPartClosure}"  disabled="#{dspm.dspmFlag}">
                        <f:selectItems value="#{dspm.ddDspmTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputText id="stxt3" styleClass="output" value=""/>
                    <h:outputText id="stxt4" styleClass="output" value=""/>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
