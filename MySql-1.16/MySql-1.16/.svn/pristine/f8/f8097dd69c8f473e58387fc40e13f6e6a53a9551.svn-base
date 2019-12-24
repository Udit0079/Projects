<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="cd">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanelCurrencyDetails" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblAcctReportCode" styleClass="label" value="Acct Report Code"/>
                <h:inputText id="txtAcctReportCode" styleClass="input" style="width:120px" value="#{cd.acctReportCode}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDefaultNationalRate" styleClass="label" value="Default National Rate"/>
                <h:selectOneListbox id="ddDefaultNationalRate" styleClass="ddlist" size="1" style="width:120px" value="#{cd.defaultNationalRate}"  disabled="#{cd.cdFlag }">
                    <f:selectItems value="#{cd.ddDefaultNationalRate}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblRevaluationAcctPlaceholder" styleClass="label" value="Revaluation Acct Placeholder"/>
                <h:inputText id="txtRevaluationAcctPlaceholder" styleClass="input" style="width:120px" value="#{cd.revaluationAcctPlaceholder}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblCrIntRateCode" styleClass="label" value="Cr Int Rate Code"/>
                <h:inputText id="txtCrIntRateCode" styleClass="input" style="width:120px" value="#{cd.crIntRateCode}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDrIntRateCode" styleClass="label" value="Dr Int Rate Code"/>
                <h:inputText id="txtDrIntRateCode" styleClass="input" style="width:120px" value="#{cd.drIntRateCode}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDefaultCustPreferentialToAcct" styleClass="label" value="Default Cust Preferential To Acct"/>
                <h:selectOneListbox id="ddDefaultCustPreferentialToAcct" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.defaultCustPreferentialToAcct}"  disabled="#{cd.cdFlag }">
                    <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd3" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblAcctOpenEvent" styleClass="label" value="Acct Open Event"/>
                <h:inputText id="txtAcctOpenEvent" styleClass="input" style="width:120px" value="#{cd.acctOpenEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblAcctMatchEvent" styleClass="label" value="Acct Match Event"/>
                <h:inputText id="txtAcctMatchEvent" styleClass="input" style="width:120px" value="#{cd.acctMatchEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblAcctClosureEvent" styleClass="label" value="Acct Closure Event"/>
                <h:inputText id="txtAcctClosureEvent" styleClass="input" style="width:120px" value="#{cd.acctClosureEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd4" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblLedgerFolioCalcEvent" styleClass="label" value="Ledger Folio Calc Event"/>
                <h:inputText id="txtLedgerFolioCalcEvent" styleClass="input" style="width:120px" value="#{cd.ledgerFolioCalcEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblAdhocPassSheetPrintEvent" styleClass="label" value="Adhoc Pass Sheet Print Event"/>
                <h:inputText id="txtAdhocPassSheetPrintEvent" styleClass="input" style="width:120px" value="#{cd.adhocPassSheetPrintEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblRegPassSheetPrintEvent" styleClass="label" value="Reg Pass Sheet Print Event"/>
                <h:inputText id="txtRegPassSheetPrintEvent" styleClass="input" style="width:120px" value="#{cd.regPassSheetPrintEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd5" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCommitmentEvent" styleClass="label" value="Limit Value Against Security(in %)"/>
                <h:inputText id="txtCommitmentEvent" styleClass="input" style="width:120px" value="#{cd.commitmentEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblsc1" styleClass="label" value=""/>
                <h:outputLabel id="lblsc2" styleClass="label" value=""/>
                <h:outputLabel id="lblsc3" styleClass="label" value=""/>
                <h:outputLabel id="lblsc4" styleClass="label" value=""/>
            </h:panelGrid>
            <rich:panel header="Scheme Limits" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd6" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCrCashLimit" styleClass="label" value="Cr Cash Limit"/>
                    <h:inputText id="txtCrCashLimit" styleClass="input" style="width:120px" value="#{cd.crCashLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrCashLimit" styleClass="label" value="Dr Cash Limit"/>
                    <h:inputText id="txtDrCashLimit" styleClass="input" style="width:120px" value="#{cd.drCashLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblCrClgLimit" styleClass="label" value="Cr Clg Limit "/>
                    <h:inputText id="txtCrClgLimit" styleClass="input" style="width:120px" value="#{cd.crClgLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd7" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblDrClgLimit" styleClass="label" value="Dr Clg Limit"/>
                    <h:inputText id="txtDrClgLimit" styleClass="input" style="width:120px" value="#{cd.drClgLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblCrXrefLimit" styleClass="label" value="Cr Xref Limit"/>
                    <h:inputText id="txtCrXrefLimit" styleClass="input" style="width:120px" value="#{cd.crXrefLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrXrefLimit" styleClass="label" value="Dr Xref Limit"/>
                    <h:inputText id="txtDrXrefLimit" styleClass="input" style="width:120px" value="#{cd.drXrefLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd9" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDrCashAbnLimit" styleClass="label" value="Dr Cash Abn Limit"/>
                    <h:inputText id="txtDrCashAbnLimit" styleClass="input" style="width:120px" value="#{cd.drCashAbnLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrClgAbnLimit" styleClass="label" value="Dr Clg Abn Limit"/>
                    <h:inputText id="txtDrClgAbnLimit" styleClass="input" style="width:120px" value="#{cd.drClgAbnLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrXrefAbnLimit" styleClass="label" value="Dr Xref Abn Limit"/>
                    <h:inputText id="txtDrXrefAbnLimit" styleClass="input" style="width:120px" value="#{cd.drXrefAbnLimit}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd10" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNewAccountAbnTranAmt" styleClass="label" value="New A/c Abn Tran Amt"/>
                    <h:inputText id="txtNewAccountAbnTranAmt" styleClass="input" style="width:120px" value="#{cd.newAccountAbnTranAmt}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblsc5" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc6" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc7" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc8" styleClass="label" value=""/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd11" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblInterestTableCode1" styleClass="label" value="Interest Table Code"/>
                    <h:selectOneListbox id="ddInterestTableCode1" styleClass="ddlist" size="1" style="width:120px" value="#{cd.interestTableCode1}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.interestTableCodeList}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblCrMin" styleClass="label" value="Cr % Min/Max"/>
                    <h:panelGroup id="groupPanelCrMin" layout="block">
                        <h:inputText id="txtCrMin" styleClass="input" style="width:60px" value="#{cd.crMin}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true"/>
                        </h:inputText>
                        <h:outputLabel id="lblCrMax" styleClass="label" value="/"/>
                        <h:inputText id="txtCrMax" styleClass="input" style="width:60px" value="#{cd.crMax}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true"/>
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel id="lblDrMin" styleClass="label" value="Dr % Min/Max"/>
                    <h:panelGroup id="groupPanelDrMin" layout="block">
                        <h:inputText id="txtDrMin" styleClass="input" style="width:60px" value="#{cd.drMin}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true"/>
                        </h:inputText>
                        <h:outputLabel id="lblDrMax" styleClass="label" value="/"/>
                        <h:inputText id="txtDrMax" styleClass="input" style="width:60px" value="#{cd.drMax}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true"/>
                        </h:inputText>
                    </h:panelGroup>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd12" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCrDaysInaYear" styleClass="label" value="Days In a Year(Cr) "/>
                    <h:inputText id="txtCrDaysInaYear" styleClass="input" style="width:120px" value="#{cd.crDaysInaYear}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrDaysinaYear" styleClass="label" value="Days in a Year(Dr) "/>
                    <h:inputText id="txtDrDaysinaYear" styleClass="input" style="width:120px" value="#{cd.drDaysinaYear}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblCrLeapYearAdjustment" styleClass="label" value="Leap Year Adjustment(Cr)"/>
                    <h:inputText id="txtCrLeapYearAdjustment" styleClass="input" style="width:120px" value="#{cd.crLeapYearAdjustment}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd13" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblDrLeapYearAdjustment" styleClass="label" value="Leap Year Adjustment(Dr) "/>
                    <h:inputText id="txtDrLeapYearAdjustment" styleClass="input" style="width:120px" value="#{cd.drLeapYearAdjustment}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblMinIntPaidAmt" styleClass="label" value="Min Int Paid Amt"/>
                    <h:inputText id="txtMinIntPaidAmt" styleClass="input" style="width:120px" value="#{cd.minIntPaidAmt}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblMinIntCollAmt" styleClass="label" value="Min Int Coll Amt"/>
                    <h:inputText id="txtMinIntCollAmt" styleClass="input" style="width:120px" value="#{cd.minIntCollAmt}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd14" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblBookAdvanceInt" styleClass="label" value="Book Advance Int"/>
                    <h:selectOneListbox id="ddBookAdvanceInt" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.bookAdvanceInt}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMICRChrgEvent" styleClass="label" value="MICR Chrg Event"/>
                    <h:inputText id="txtMICRChrgEvent" styleClass="input" style="width:120px" value="#{cd.mICRChrgEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblResidualIntAdjustmentAmountforBooking" styleClass="label" value="Residual Int Adjustment Amount for Booking"/>
                    <h:inputText id="txtResidualIntAdjustmentAmountforBooking" styleClass="input" style="width:120px" value="#{cd.residualIntAdjustmentAmountforBooking}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd151" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblWithoutTaxAmountRndSt" styleClass="label" value="Without Tax Amount Rnd St"/>
                    <h:selectOneListbox id="ddWithoutTaxAmountRndSt" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.withoutTaxAmountRndSt}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddWithoutTaxAmount}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblWithoutTaxAmountRndAmount" styleClass="label" value="Without Tax Amount Rnd Amount"/>
                    <h:inputText id="txtWithoutTaxAmountRndAmount" styleClass="input" style="width:120px" value="#{cd.withoutTaxAmountRndAmount}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblIntPrdRndStCr" styleClass="label" value="Int Prd Rounding Start (Cr)"/>
                    <h:selectOneListbox id="ddIntPrdRndStCr" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.intPrdRndStCr}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddWithoutTaxAmount}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd15" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntPrdRndAmountCr" styleClass="label" value="Int Prd Rounding Amount (Cr)"/>
                    <h:inputText id="txtIntPrdRndAmountCr" styleClass="input" style="width:120px" value="#{cd.intPrdRndAmountCr}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblIntPrdRoundingStartDr" styleClass="label" value="Int Prd Rounding Start (Dr)"/>
                    <h:selectOneListbox id="ddIntPrdRoundingStartDr" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.intPrdRoundingStartDr}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddWithoutTaxAmount}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntPrdRoundingAmtDr" styleClass="label" value="Int Prd Rounding Amount (Dr)"/>
                    <h:inputText id="txtIntPrdRoundingAmtDr" styleClass="input" style="width:120px" value="#{cd.intPrdRoundingAmtDr}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd16" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblIntAmRoundingStartCr" styleClass="label" value="Int Amt Rounding Start(Cr)"/>
                    <h:selectOneListbox id="ddIntAmRoundingStartCr" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.intAmRoundingStartCr}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddWithoutTaxAmount}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntAmtRoundingAmtCr" styleClass="label" value="Int Amt Rounding Amount (Cr)"/>
                    <h:inputText id="txtIntAmtRoundingAmtCr" styleClass="input" style="width:120px" value="#{cd.intAmtRoundingAmtCr}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblIntAmtRoundingStartDr" styleClass="label" value="Int Amt Rounding Start (Dr)"/>
                    <h:selectOneListbox id="ddIntAmtRoundingStartDr" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.intAmtRoundingStartDr}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddWithoutTaxAmount}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd18" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntAmtRoundingAmtDr" styleClass="label" value="Int Amt Rounding Amt(Dr)"/>
                    <h:inputText id="txtIntAmtRoundingAmtDr" styleClass="input" style="width:120px" value="#{cd.intAmtRoundingAmtDr}" maxlength="11" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblsc9" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc10" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc11" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc12" styleClass="label" value=""/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd19" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblIntPaidRptCode" styleClass="label" value="Int Paid Rpt Code"/>
                    <h:inputText id="txtIntPaidRptCode" styleClass="input" style="width:120px" value="#{cd.intPaidRptCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblIntCollRptCode" styleClass="label" value="Int Coll Rpt Code"/>
                    <h:inputText id="txtIntCollRptCode" styleClass="input" style="width:120px" value="#{cd.intCollRptCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblIntDrRptCode" styleClass="label" value="Int Dr Rpt Code"/>
                    <h:inputText id="txtIntDrRptCode" styleClass="input" style="width:120px" value="#{cd.intDrRptCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd20" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntCrRptCode" styleClass="label" value="Int Cr Rpt Code"/>
                    <h:inputText id="txtIntCrRptCode" styleClass="input" style="width:120px" value="#{cd.intCrRptCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblTaxCollRptCode" styleClass="label" value="Tax Coll Rpt Code"/>
                    <h:inputText id="txtTaxCollRptCode" styleClass="input" style="width:120px" value="#{cd.taxCollRptCode}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblTaxCollAccountPlaceholder" styleClass="label" value="Tax Coll Account Placeholder"/>
                    <h:inputText id="txtTaxCollAccountPlaceholder" styleClass="input" style="width:120px" value="#{cd.taxCollAccountPlaceholder}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd21" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblTaxFlag" styleClass="label" value="Tax Flag"/>
                    <h:selectOneListbox id="ddTaxFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.taxFlag}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddTaxglag}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIncludeFloorLimitForTax" styleClass="label" value="Include Floor Limit For Tax"/>
                    <h:inputText id="txtIncludeFloorLimitForTax" styleClass="input" style="width:120px" value="#{cd.includeFloorLimitForTax}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblProportionateFloorLimit" styleClass="label" value="Proportionate Floor Limit"/>
                    <h:selectOneListbox id="ddProportionateFloorLimit" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.proportionateFloorLimit}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd22" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblWithoutTaxFlrLim" styleClass="label" value="Without Tax Flr Lim"/>
                    <h:inputText id="txtWithoutTaxFlrLim" styleClass="input" style="width:120px" value="#{cd.withoutTaxFlrLim}" maxlength="8" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblWithHoldingTax" styleClass="label" value="WithHolding Tax %"/>
                    <h:inputText id="txtWithHoldingTax" styleClass="input" style="width:120px" value="#{cd.withHoldingTax}" maxlength="8" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblWTAXMaturityAdjReqd" styleClass="label" value="WTAX Maturity Adj Reqd ?"/>
                    <h:selectOneListbox id="ddWTAXMaturityAdjReqd" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.wTAXMaturityAdjReqd}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd23" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblEndofDayBalCheck" styleClass="label" value="End of Day Bal Check"/>
                    <h:selectOneListbox id="ddEndofDayBalCheck" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.endofDayBalCheck}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblEndOfDayMinBalance" styleClass="label" value="End Of Day Min Balance"/>
                    <h:inputText id="txtEndOfDayMinBalance" styleClass="input" style="width:120px" value="#{cd.endOfDayMinBalance}" maxlength="18" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrCrInd" styleClass="label" value="Dr/Cr Ind"/>
                    <h:selectOneListbox id="ddDrCrInd" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.drCrInd}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd24" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblEndOfDayMaxBalance" styleClass="label" value="End Of Day Max Balance"/>
                    <h:inputText id="txtEndOfDayMaxBalance" styleClass="input" style="width:120px" value="#{cd.endOfDayMaxBalance}" maxlength="18" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblDrCrInd1" styleClass="label" value="Dr/Cr Ind"/>
                    <h:selectOneListbox id="ddDrCrInd1" styleClass="ddlist"  size="1" style="width:120px" value="#{cd.drCrInd1}"  disabled="#{cd.cdFlag }">
                        <f:selectItems value="#{cd.ddCDTrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblEODMinBalanceExceptionCode" styleClass="label" value="EOD Min Balance Exception Code"/>
                    <h:inputText id="txtEODMinBalanceExceptionCode" styleClass="input" style="width:120px" value="#{cd.eODMinBalanceExceptionCode}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd25" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblEODMaxBalanceExceptionCode" styleClass="label" value="EOD Max Bal Exp Code"/>
                    <h:inputText id="txtEODMaxBalanceExceptionCode" styleClass="input" style="width:120px" value="#{cd.eODMaxBalanceExceptionCode}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblsc13" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc14" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc15" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc16" styleClass="label" value=""/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Exception Codes" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd26" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCashLimitDr" styleClass="label" value="Cash Limit Dr"/>
                    <h:inputText id="txtCashLimitDr" styleClass="input" style="width:120px" value="#{cd.cashLimitDr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descCashLimitDr}" event="onblur" reRender="stxtCashLimitDr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCashLimitDr" styleClass="label"  style="color:green;" value="#{cd.stCashLimitDr}"/>
                    <h:outputLabel id="lblCashLimitCr" styleClass="label" value="Cash Limit Cr"/>
                    <h:inputText id="txtCashLimitCr" styleClass="input" style="width:120px" value="#{cd.cashLimitCr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descCashLimitCr}" event="onblur" reRender="stxtCashLimitCr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCashLimitCr" styleClass="label"  style="color:green;" value="#{cd.stCashLimitCr}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd27" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblClgLimitDr" styleClass="label" value="Clg Limit Dr"/>
                    <h:inputText id="txtClgLimitDr" styleClass="input" style="width:120px" value="#{cd.clgLimitDr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descClgLimitDr}" event="onblur" reRender="stxtClgLimitDr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtClgLimitDr" styleClass="label"  style="color:green;" value="#{cd.stClgLimitDr}"/>
                    <h:outputLabel id="lblClgLimitCr" styleClass="label" value="Clg Limit Cr"/>
                    <h:inputText id="txtClgLimitCr" styleClass="input" style="width:120px" value="#{cd.clgLimitCr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descClgLimitCr}" event="onblur" reRender="stxtClgLimitCr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtClgLimitCr" styleClass="label"  style="color:green;" value="#{cd.stClgLimitCr}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd28" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblTransferLimitDr" styleClass="label" value="Transfer Limit Dr"/>
                    <h:inputText id="txtTransferLimitDr" styleClass="input" style="width:120px" value="#{cd.transferLimitDr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descTransferLimitDr}" event="onblur" reRender="stxtTransferLimitDr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtTransferLimitDr" styleClass="label"  style="color:green;" value="#{cd.stTransferLimitDr}"/>
                    <h:outputLabel id="lblTransferLimitCr" styleClass="label" value="Transfer Limit Cr"/>
                    <h:inputText id="txtTransferLimitCr" styleClass="input" style="width:120px" value="#{cd.transferLimitCr}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descTransferLimitCr}" event="onblur" reRender="stxtTransferLimitCr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtTransferLimitCr" styleClass="label"  style="color:green;" value="#{cd.stTransferLimitCr}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd29" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLateCashTran" styleClass="label" value="Late Cash Tran"/>
                    <h:inputText id="txtLateCashTran" styleClass="input" style="width:120px" value="#{cd.lateCashTran}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descLateCashTran}" event="onblur" reRender="stxtLateCashTran,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtLateCashTran" styleClass="label"  style="color:green;" value="#{cd.stLateCashTran}"/>
                    <h:outputLabel id="lblTranAmtLimit" styleClass="label" value="Tran Amt Limit"/>
                    <h:inputText id="txtTranAmtLimit" styleClass="input" style="width:120px" value="#{cd.tranAmtLimit}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descTranAmtLimit}" event="onblur" reRender="stxtTranAmtLimit,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtTranAmtLimit" styleClass="label"  style="color:green;" value="#{cd.stTranAmtLimit}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd30" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDrTranAmtNotAllowed" styleClass="label" value="Dr Tran Amt Not Allowed"/>
                    <h:inputText id="txtDrTranAmtNotAllowed" styleClass="input" style="width:120px" value="#{cd.drTranAmtNotAllowed}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descDrTranAmtNotAllowed}" event="onblur" reRender="stxtDrTranAmtNotAllowed,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDrTranAmtNotAllowed" styleClass="label"  style="color:green;" value="#{cd.stDrTranAmtNotAllowed}"/>
                    <h:outputLabel id="lblCrTranAmtNotAllowed" styleClass="label" value="Cr Tran Amt Not Allowed"/>
                    <h:inputText id="txtCrTranAmtNotAllowed" styleClass="input" style="width:120px" value="#{cd.crTranAmtNotAllowed}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support action="#{cd.descCrTranAmtNotAllowed}" event="onblur" reRender="stxtCrTranAmtNotAllowed,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCrTranAmtNotAllowed" styleClass="label"  style="color:green;" value="#{cd.stCrTranAmtNotAllowed}"/>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Min Balance Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd31" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMinBalanceEvent" styleClass="label" value="Min Balance Event"/>
                    <h:inputText id="txtMinBalanceEvent" styleClass="input" style="width:120px" value="#{cd.minBalanceEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblAcctMinBalance" styleClass="label" value="Acct Min Balance"/>
                    <h:inputText id="txtAcctMinBalance" styleClass="input" style="width:120px" value="#{cd.acctMinBalance}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblSweepMinBalance" styleClass="label" value="Sweep Min Balance"/>
                    <h:inputText id="txtSweepMinBalance" styleClass="input" style="width:120px" value="#{cd.sweepMinBalance}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd32" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinChargePeriod" styleClass="label" value="Min Charge/Period"/>
                    <h:inputText id="txtMinChargePeriod" styleClass="input" style="width:120px" value="#{cd.minChargePeriod}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblPenalChargePeriod" styleClass="label" value="Penal Charge/Period"/>
                    <h:inputText id="txtPenalChargePeriod" styleClass="input" style="width:120px" value="#{cd.penalChargePeriod}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblMinBalanceServiceChrg" styleClass="label" value="Min Balance Service Chrg"/>
                    <h:inputText id="txtMinBalanceServiceChrg" styleClass="input" style="width:120px" value="#{cd.minBalanceServiceChrg}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="cd34" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblRegularIntCertificatePrintingEvent" styleClass="label" value="Reg.Int Certificate Print Event"/>
                    <h:inputText id="txtRegularIntCertificatePrintingEvent" styleClass="input" style="width:120px" value="#{cd.regularIntCertificatePrintingEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblAdhocIntCertificatePrintingEvent" styleClass="label" value="Adhoc Int Certificate Print Event"/>
                    <h:inputText id="txtAdhocIntCertificatePrintingEvent" styleClass="input" style="width:120px" value="#{cd.adhocIntCertificatePrintingEvent}" maxlength="20" onkeyup="this.value = this.value.toUpperCase();" disabled="#{cd.cdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true"/>
                    </h:inputText>
                    <h:outputLabel id="lblsc17" styleClass="label" value=""/>
                    <h:outputLabel id="lblsc18" styleClass="label" value=""/>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
