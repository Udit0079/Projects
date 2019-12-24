<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="led">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="SchemeLoanExceptionDetailsPanel11" style="width:100%;text-align:center;">
            <rich:panel header="Exception Codes" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNonConformingLoanPeriod" styleClass="label" value="NonConforming Loan Period"/>
                    <h:inputText id="txtNonConformingLoanPeriod" maxlength="3" style="width:120px" value="#{led.nonConformingLoanPeriod}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descNonConformingLoanPeriod}" event="onblur" reRender="stxtNonConformingLoanPeriod,lblMsg" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputText id="stxtNonConformingLoanPeriod" styleClass="label" value="#{led.stNonConformingLoanPeriod}" style="color:green;"/>
                    <h:outputLabel id="lblNonConformingLoanAmount" styleClass="label" value=" NonConforming Loan Amount"/>
                    <h:inputText id="txtNonConformingLoanAmount" maxlength="3" style="width:120px" value="#{led.nonConformingLoanAmount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descNonConformingLoanAmount}" event="onblur" reRender="stxtNonConformingLoanAmount,lblMsg" />
                    </h:inputText>
                    <h:outputText id="stxtNonConformingLoanAmount" styleClass="label" value="#{led.stNonConformingLoanAmount}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblDisbGreaterThanLoanAmount" styleClass="label" value="Disb Greater Than Loan Amount"/>
                    <h:inputText id="txtDisbGreaterThanLoanAmount" maxlength="3" style="width:120px" value="#{led.disbGreaterThanLoanAmount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descDisbGreaterThanLoanAmount}" event="onblur" reRender="stxtDisbGreaterThanLoanAmount,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDisbGreaterThanLoanAmount" styleClass="label" value="#{led.stDisbGreaterThanLoanAmount}" style="color:green;"/>
                    <h:outputLabel id="lblDisbNotConformingToSchedule" styleClass="label" value="Disb Not Conforming To Schedule"/>
                    <h:inputText id="txtDisbNotConformingToSchedule" maxlength="3" style="width:120px" value="#{led.disbNotConformingToSchedule}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descDisbNotConformingToSchedule}" event="onblur" reRender="stxtDisbNotConformingToSchedule,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDisbNotConformingToSchedule" styleClass="label" value="#{led.stDisbNotConformingToSchedule}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDisbDateSanctExpiryDate" styleClass="label" value="DisbDate Sanct Expiry Date"/>
                    <h:inputText id="txtDisbDateSanctExpiryDate" maxlength="3" style="width:120px" value="#{led.disbDateSanctExpiryDate}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descDisbDateSanctExpiryDate}" event="onblur" reRender="stxtDisbDateSanctExpiryDate,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDisbDateSanctExpiryDate" styleClass="label" value="#{led.stDisbDateSanctExpiryDate}" style="color:green;"/>
                    <h:outputLabel id="lblIntCalculationNotUpToDate" styleClass="label" value="Int Calculation Not Up To Date"/>
                    <h:inputText id="txtIntCalculationNotUpToDate" maxlength="3" style="width:120px" value="#{led.intCalculationNotUpToDate}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descIntCalculationNotUpToDate}" event="onblur" reRender="stxtIntCalculationNotUpToDate,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtIntCalculationNotUpToDate" styleClass="label" value="#{led.stIntCalculationNotUpToDate}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan3" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblTransferAmountIsGreaterThanCrBalance" styleClass="label" value="Transfer Amount Is Greater Than Cr Balance"/>
                    <h:inputText id="txtTransferAmountIsGreaterThanCrBalance" maxlength="3" style="width:120px" value="#{led.transferAmountIsGreaterThanCrBalance}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descTransferAmountIsGreaterThanCrBalance}" event="onblur" reRender="stxtTransferAmountIsGreaterThanCrBalance,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtTransferAmountIsGreaterThanCrBalance" styleClass="label" value="#{led.stTransferAmountIsGreaterThanCrBalance}" style="color:green;"/>
                    <h:outputLabel id="lblCustIdDiffForLoanAndOpAccount" styleClass="label" value="Cust Id Diff For Loan And Op Account"/>
                    <h:inputText id="txtCustIdDiffForLoanAndOpAccount" maxlength="3" style="width:120px" value="#{led.custIdDiffForLoanAndOpAccount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descCustIdDiffForLoanAndOpAccount}" event="onblur" reRender="stxtCustIdDiffForLoanAndOpAccount,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCustIdDiffForLoanAndOpAccount" styleClass="label" value="#{led.stCustIdDiffForLoanAndOpAccount}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblInterestCollectedExceedsLimit" styleClass="label" value="Interest Collected Exceeds Limit"/>
                    <h:inputText id="txtInterestCollectedExceedsLimit" maxlength="3" style="width:120px" value="#{led.interestCollectedExceedsLimit}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag}" styleClass="input">
                        <a4j:support action="#{led.descInterestCollectedExceedsLimit}" event="onblur" reRender="stxtInterestCollectedExceedsLimit,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtInterestCollectedExceedsLimit" styleClass="label" value="#{led.stInterestCollectedExceedsLimit}" style="color:green;"/>
                    <h:outputLabel id="lblWaiverOfChargesOrInterest" styleClass="label" value="Waiver Of Charges Or Interest"/>
                    <h:inputText id="txtWaiverOfChargesOrInterest" maxlength="3" style="width:120px" value="#{led.waiverOfChargesOrInterest}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descWaiverOfChargesOrInterest}" event="onblur" reRender="stxtWaiverOfChargesOrInterest,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtWaiverOfChargesOrInterest" styleClass="label" value="#{led.stWaiverOfChargesOrInterest}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan5" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblOverrideSystemCalcEiAmount" styleClass="label" value="Override System Calc. EI Amount"/>
                    <h:inputText id="txtOverrideSystemCalcEiAmount" maxlength="3" style="width:120px" value="#{led.overrideSystemCalcEiAmount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descOverrideSystemCalcEiAmount}" event="onblur" reRender="stxtOverrideSystemCalcEiAmount,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtOverrideSystemCalcEiAmount" styleClass="label" value="#{led.stOverrideSystemCalcEiAmount}" style="color:green;"/>
                    <h:outputLabel id="lblPendingSchedulePayments" styleClass="label" value="Pending Schedule Payments"/>
                    <h:inputText id="txtPendingSchedulePayments" maxlength="3" style="width:120px" value="#{led.pendingSchedulePayments}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descPendingSchedulePayments}" event="onblur" reRender="stxtPendingSchedulePayments,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtPendingSchedulePayments" styleClass="label" value="#{led.stPendingSchedulePayments}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan6" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblRepaymentPerdNotEquelToLoanPerd" styleClass="label" value="Repayment Perd Not Equel To Loan Perd"/>
                    <h:inputText id="txtRepaymentPerdNotEquelToLoanPerd" maxlength="3" style="width:120px" value="#{led.repaymentPerdNotEquelToLoanPerd}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descRepaymentPerdNotEquelToLoanPerd}" event="onblur" reRender="stxtRepaymentPerdNotEquelToLoanPerd,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtRepaymentPerdNotEquelToLoanPerd" styleClass="label" value="#{led.stRepaymentPerdNotEquelToLoanPerd}" style="color:green;"/>
                    <h:outputLabel id="lblRephasementInterestCalcNotUpToDate" styleClass="label" value="Rephasement Interest Calc Not Up To Date"/>
                    <h:inputText id="txtRephasementInterestCalcNotUpToDate" maxlength="3" style="width:120px" value="#{led.rephasementInterestCalcNotUpToDate}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descRephasementInterestCalcNotUpToDate}" event="onblur" reRender="stxtRephasementInterestCalcNotUpToDate,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtRephasementInterestCalcNotUpToDate" styleClass="label" value="#{led.stRephasementInterestCalcNotUpToDate}" style="color:green;"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Exception Contd." style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan7" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMaxHolidayPeriodExceeded" styleClass="label" value="Max Holiday Period Exceeded"/>
                    <h:inputText id="txtMaxHolidayPeriodExceeded" maxlength="3" style="width:120px" value="#{led.maxHolidayPeriodExceeded}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descMaxHolidayPeriodExceeded}" event="onblur" reRender="stxtMaxHolidayPeriodExceeded,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtMaxHolidayPeriodExceeded" styleClass="label" value="#{led.stMaxHolidayPeriodExceeded}" style="color:green;"/>
                    <h:outputLabel id="lblPrepaymentNotAsPerNotice" styleClass="label" value="Prepayment Not As Per Notice"/>
                    <h:inputText id="txtPrepaymentNotAsPerNotice" maxlength="3" style="width:120px" value="#{led.prepaymentNotAsPerNotice}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descPrepaymentNotAsPerNotice}" event="onblur" reRender="stxtPrepaymentNotAsPerNotice,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtPrepaymentNotAsPerNotice" styleClass="label" value="#{led.stPrepaymentNotAsPerNotice}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="scloan8" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblValueDatedNotice" styleClass="label" value="Value Dated Notice"/>
                    <h:inputText id="txtValueDatedNotice" maxlength="3" style="width:120px" value="#{led.valueDatedNotice}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descValueDatedNotice}" event="onblur" reRender="stxtValueDatedNotice,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtValueDatedNotice" styleClass="label" value="#{led.stValueDatedNotice}" style="color:green;"/>
                    <h:outputLabel id="lblBackValueDatedAccountOpening" styleClass="label" value="Back Value Dated Account Opening"/>
                    <h:inputText id="txtBackValueDatedAccountOpening" maxlength="3" style="width:120px" value="#{led.backValueDatedAccountOpening}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{led.ledFlag }" styleClass="input">
                        <a4j:support action="#{led.descBackValueDatedAccountOpening}" event="onblur" reRender="stxtBackValueDatedAccountOpening,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtBackValueDatedAccountOpening" styleClass="label" value="#{led.stBackValueDatedAccountOpening}" style="color:green;"/>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
