<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="iscd">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblInterestPaidFlag" styleClass="label" value="Interest Paid Flag"/>
            <h:selectOneListbox id="ddInterestPaidFlag" styleClass="ddlist"  style="width:120px" size="1" value="#{iscd.intPaidFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblIntpayAcctPlaceholder" styleClass="label" value="Interest payable Account Placeholder"/>
            <h:inputText id="txtIntpayAcctPlaceholder" styleClass="input" style="width:120px" maxlength="12" value="#{iscd.intPayAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
            <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblIntCollectionFlag" styleClass="label" value="Interest Collection Flag"/>
            <h:selectOneListbox id="ddIntCollectionFlag" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.intCollFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblIntRecbleAcctholder" styleClass="label" value="Interest Rec'ble Account Place holder"/>
            <h:inputText id="txtIntRecbleAcctholder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.intRecAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail3" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblServiceChargesFlag" styleClass="label" value="Service Charges Flag"/>
            <h:selectOneListbox id="ddServiceChargesFlag" styleClass="ddlist" style="width:120px"  size="1" value="#{iscd.serviceChgFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblServiceCollAccountPlaceholder" styleClass="label" value="Service Coll Account Placeholder"/>
            <h:inputText id="txtSerCollAcctholder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.serviceCollAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail4" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblParkAcctTDSHolder" styleClass="label" value="Parking Account TDS Place Holder"/>
            <h:inputText id="txtParkAcctTDSHolder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.parkingAcctTdsHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblIncomeExpense" styleClass="label" value="Income/Expense Account InHome Currency"/>
            <h:inputText id="txtIncomeExpense" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.incomeExpenseAcct}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail5" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblAcctHolderCr" styleClass="label" value="Normal Profit And Loss Account Placeholder(Cr)"/>
            <h:inputText id="txtAcctHolderCr" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.nrmlPrftLossAcctHolderCr}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                          <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblAcctHolderDr" styleClass="label" value="Normal Profit And Loss Account Placeholder(Dr)"/>
            <h:inputText id="txtAcctHolderDr" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.nrmlPrftLossActHolderDr}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail6" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblPenalPLDr" styleClass="label" value="Penal Profit And Loss Account Placeholder (Dr)"/>
            <h:inputText id="txtPenalPLDr" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.penalPLAcctHoldeDr}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblParkingAcctHolder" styleClass="label" value="Parking Account Placeholder"/>
            <h:inputText id="txtParkingAcctHolder" styleClass="input"  maxlength="12" style="width:120px" value="#{iscd.parkingAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail7" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblChequeAllowedFlg" styleClass="label" value="Cheque Allowed Flag"/>
            <h:selectOneListbox id="ddChequeAllowedFlg" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.chqAllowedFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblMICRChequeChrgAcctPlace" styleClass="label" value="MICR Cheque Charge Account Placeholder"/>
            <h:inputText id="txtMICRChequeChrgAcctPlace" styleClass="input"  maxlength="12" style="width:120px" value="#{iscd.micrChqChgAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail8" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblNormalPLAcctHolder" styleClass="label" value="Over Due Normal Profit And Loss A/c Placeholder"/>
            <h:inputText id="txtNormalPLAcctHolder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.overDueNrmlPrftLossAcctHolderCr}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblOverIntPaidAcctHolder" styleClass="label" value="Over Due Int Paid A/C PlaceHolder"/>
            <h:inputText id="txtOverIntPaidAcctHolder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.overDueIntpaidAcHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail9" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblOptAcctFund" styleClass="label" value="Operative A/C Without Enough Funds To Be Debited"/>
            <h:selectOneListbox id="ddOptAcctFund" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.optActEnoughFundsDebited}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblMergeIntPtranFlag" styleClass="label" value="Merge Interest Ptran Flag"/>
            <h:selectOneListbox id="ddMergeIntPtranFlag" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.mergeIntPtranFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="detail10" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblAdvanceIntAcct" styleClass="label" value="Advance Interest Account"/>
            <h:inputText id="txtAdvanceIntAcct" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.advanceIntAcct}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
           <h:outputLabel id="lblBookTransScript" styleClass="label" value="Book TransScript"/>
           
            <h:inputText id="txtBookTransScript" styleClass="input" maxlength="100" style="width:120px" value="#{iscd.bookTransScript}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3" columns="10" id="detail11" style="width:100%;text-align:left;" styleClass="row1">
            <h:panelGroup id = "penalINtFReq">
                <h:outputLabel id="lblIntCalFreqCrType" styleClass="label" value="Interest Calculation Freq Cr Type / WeekNo / WeekDay /Start Date / NP  "/>
                <h:selectOneListbox id="ddIntCalFreqCrType" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqCrType}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.freqTypeList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqCrWeekNo" styleClass="label" value="/ "/>
                <h:selectOneListbox id="ddIntCalFreqCrWeekNo" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqCrWeekNo}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.weekNoList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqCrWeekDay" styleClass="label" value="/ "/>
                <h:selectOneListbox id="ddIntCalFreqCrWeekDay" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqCrWeekDay}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.weekDayList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqCrStartDate" styleClass="label" value="/ "/>
                <h:inputText id="txtIntCalFreqCrStartDate" styleClass="input" style="width:110px"  maxlength="2" value="#{iscd.intCalFreqCrStartDt}"
                             disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:inputText>
                <h:outputLabel id="lblIntCalFreqCrNP" styleClass="label" value="/ "/>
                <h:selectOneListbox id="ddIntCalFreqCrNP" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqCrNp}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.npList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
            </h:panelGroup>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7" columns="11" id="detail13" style="width:100%;text-align:left;" styleClass="row1">
            <h:panelGroup id ="penalDrType">
                <h:outputLabel id="lblIntCalFreqDrType" styleClass="label" value="Interest Calculation Freq DrType / WeekNo / WeekDay /Start Date / NP "/>
                <h:selectOneListbox id="ddIntCalFreqDrType" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqDrType}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.freqTypeList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqDrWeekNo" styleClass="label" value=" / "/>
                <h:selectOneListbox id="ddIntCalFreqDrWeekNo" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqDrWeekNo}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.weekNoList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqDrWeekDate" styleClass="label" value=" / "/>
                <h:selectOneListbox id="ddIntCalFreqDrWeekDate" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqDrWeekDay}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.weekDayList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
                <h:outputLabel id="lblIntCalFreqDRStartDate" styleClass="label" value=" / "/>
                <h:inputText id="txtIntCalFreqDRStartDate" styleClass="input"  maxlength="2" style="width:110px" value="#{iscd.intCalFreqDrStartDt}"
                             disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:inputText>
                <h:outputLabel id="lblIntCalFreqDrNP" styleClass="label" value=" / "/>
                <h:selectOneListbox id="ddIntCalFreqDrNP" styleClass="ddlist"  size="1" style="width:110px" value="#{iscd.intCalFreqDrNp}" disabled="#{iscd.disableFlagServiceChgDetail}">
                    <f:selectItems value="#{iscd.npList}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
            </h:panelGroup>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail15" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblInterestOnQIS" styleClass="label" value="Interest On QIS"/>
            <h:selectOneListbox id="ddInterestOnQIS" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.intOnQIS}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblLookBackPeriod" styleClass="label" value="Look Back Period"/>
            <h:inputText id="txtLookBackPeriod" styleClass="input" style="width:120px"  maxlength="3" value="#{iscd.lookBackPeriod}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail16" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblInterestOnStock" styleClass="label" value="Interest On Stock"/>
            <h:selectOneListbox id="ddInterestOnStock" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.interestOnStock}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblCompoundRestDayFlag" styleClass="label" value="Compound Rest Day Flag"/>
            <h:selectOneListbox id="ddCompoundRestDayFlag" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.compoundRestDayFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail17" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblDebitIntCompoundingFreq" styleClass="label" value="Debit Int Compounding Freq"/>
            <h:selectOneListbox id="ddDebitIntCompoundingFreq" styleClass="ddlist" style="width:120px"  size="1" value="#{iscd.drIntCompoundFreq}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.freqTypeList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblApplyDiscountedIntRate" styleClass="label" value="Apply Discounted Int Rate"/>
            <h:inputText id="txtApplyDiscountedIntRate" styleClass="input" style="width:120px" value="#{iscd.applyDisIntRate}" maxlength="53"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail18" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblLimitLevelIntFlag" styleClass="label" value="Limit Level Int Flag"/>
            <h:selectOneListbox id="ddLimitLevelIntFlag" styleClass="ddlist" style="width:120px" size="1" value="#{iscd.lmtLevelIntFlag}" disabled="#{iscd.disableFlagServiceChgDetail}">
                <f:selectItems value="#{iscd.deleteTODList}"/>
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:selectOneListbox>
            <h:outputLabel id="lblPrincipalLossLinePlaceholder" styleClass="label" value="Principal Loss Line Placeholder" />
            <h:inputText id="txtPrincipalLossLinePlaceholder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.principalLossLineHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                          <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail19" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblRecoveryLossLineholer" styleClass="label" value="Recovery Loss Line Placeholer"/>
            <h:inputText id="txtRecoveryLossLineholer" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.recovryLossLineholer}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblChargeOffAcctholder" styleClass="label" value="Charge Off Account Place holder"/>
            <h:inputText id="txtChargeOffAcctholder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.chargeOffAcctHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                          <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="11" id="detail20" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblDealerContholder" styleClass="label" value="Dealer Contribution Place holder"/>
            <h:inputText id="txtDealerContholder" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.dealerContributionHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                          <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
            <h:outputLabel id="lblIntWaiverDrPlace" styleClass="label" value="Interest Waiver Debit Place holder"/>
            <h:inputText id="txtIntWaiverDrPlace" styleClass="input" style="width:120px"  maxlength="12" value="#{iscd.intWaiverDebitHolder}"
                         disabled="#{iscd.disableFlagServiceChgDetail}" onkeyup="this.value = this.value.toUpperCase();">
                 <a4j:support event="onblur" ajaxSingle="true" />  
            </h:inputText>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
