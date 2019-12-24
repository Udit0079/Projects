<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="spm">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel3" style="width:100%;text-align:center;">
            <h:panelGrid id="SbSchemeParamInfoPanel" columns="2" style="width:100%;text-align:center;">
                <h:panelGrid id="leftSbSchemeParamPanel" style="width:100%;text-align:center;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchemeParamRow1" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblAllowNoOfWithDrawls" styleClass="label" value="Allowed No. Of Withdrawls"/>
                        <h:inputText  id="txtAllowNoOfWithDrawls" styleClass="input" maxlength="2"  style="width:120px" value="#{spm.allowedNoWithdrawls}" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblNoIntWithdrawExceed" styleClass="label" value="No Int. If Withdrawls Exceed"/>
                        <h:selectOneListbox value="#{spm.noWithdrawlsExceed}" style="width:120px" id="ddNoIntWithdrawExceed" styleClass="ddlist" size="1" disabled="#{spm.disableFlag}">
                            <f:selectItems value="#{spm.noWithdrawlsExceedoption}"/>
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchemeParamRow2" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblMinBalChq" styleClass="label" value="Min. Bal. for Chq."/>
                        <h:inputText  id="txtMinBalChq" styleClass="input" value="#{spm.minBalChq}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblDrBalLim" styleClass="label" value="Dr. Bal. Limit"/>
                        <h:inputText  id="txtDrBalLim" styleClass="input" value="#{spm.drBalLim}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchParamMain3" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblLedgerFolio" styleClass="label" value="Ledger Folio Charge/Folio"/>
                        <h:inputText  id="txtLedgerFolio" styleClass="input" value="#{spm.ledgerFolio1}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                        <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblServCharge" styleClass="label" value="Serv Chrg/ Extra Withdrawl"/>
                        <h:inputText  id="txtServCharge" styleClass="input" value="#{spm.servChrgWithdrawl}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchParamMain4" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblInactiveAcc" styleClass="label" value="Inactive A/C Abnrml Tran. Lim."/>
                        <h:inputText  id="txtInactiveAcc" styleClass="input" value="#{spm.inactiveAccAbnrmlTranLimit}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblDormantAcc" styleClass="label" value="Dormant A/C Abnrml Tran. Lim."/>
                        <h:inputText  id="txtDormantAcc" styleClass="input" value="#{spm.dormantAccAbnrmlTranLimit}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchParamMain5" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblDurationMarkAcc" styleClass="label" value="Duration to Mark A/C as InAct."/>
                        <h:inputText  id="txtDurationMarkAcc" styleClass="input" value="#{spm.durationMarkAccAct}"maxlength="2" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblDurationInactDorm" styleClass="label" value="Duration From Inact. to Dorm."/>
                        <h:inputText  id="txtDurationInactDorm" styleClass="input" value="#{spm.durationFromactDorm}" maxlength="2" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchParamMain6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblDormChEvent" styleClass="label" value="Dorm. Ch. Event"/>
                        <h:inputText  id="txtDormChEvent" styleClass="input" value="#{spm.dormEvent}" maxlength="25" style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                        <h:outputLabel id="lblInacChEvent" styleClass="label" value="Inac. Ch. Event"/>
                        <h:inputText  id="txtInacChEvent" styleClass="input" value="#{spm.inacEvent}" maxlength="25"  style="width:120px" disabled="#{spm.disableFlag}">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="SbSchParamMain7" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblAllowSweep" styleClass="label" value="Allow Sweeps"/>
                        <h:selectOneListbox   value="#{spm.allowSweep}" style="width:120px" id="ddAllowSweep" styleClass="ddlist" size="1" disabled="#{spm.disableFlag}">
                            <f:selectItems value="#{spm.allowSweepOption}"/>
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:selectOneListbox>
                        <h:outputLabel id="lblAllowDrForUnclearBal" styleClass="label" value="Allow Dr. Against Unclear Balance" />
                        <h:selectOneListbox   value="#{spm.allowDrUnclearBalance}" style="width:120px" id="ddAllowDrForUnclearBal" styleClass="ddlist" size="1" disabled="#{spm.disableFlag}">
                            <f:selectItems value="#{spm.allowDrUnclearBalanceOption}"/>
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <rich:panel header="Int. Calculation Parameter Details For SBA Scheme Type" style="text-align:left;">
                        <h:panelGrid id="IntCalParamInfoPanel" columns="2" style="width:100%;text-align:center;">
                            <h:panelGrid id="IntCalParam2InfoPanel"style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="IntCalParam1" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblIntCalBasedLocalCal" styleClass="label" value="Int. Calc. Based on Local Calender"/>
                                    <h:selectOneListbox   value="#{spm.intCalcLocalCalender}" style="width:120px" id="ddIntCalBasedLocalCal" styleClass="ddlist" size="1" disabled="#{spm.disableFlag}">
                                        <f:selectItems value="#{spm.intCalcLocalCalenderOption}"/>
                                        <a4j:support event="onblur" ajaxSingle="true" />   
                                    </h:selectOneListbox>
                                    <h:outputText/>
                                    <h:outputLabel id="lblIntOnAvg" styleClass="label" value="Int. on Avg. or Min. Balance"/>
                                    <h:selectOneListbox   value="#{spm.intAvgBalance}" style="width:120px" id="ddIntOnAvg" styleClass="ddlist" size="1" disabled="#{spm.disableFlag}">
                                        <f:selectItems value="#{spm.intAvgBalanceOption}"/>
                                        <a4j:support event="onblur" ajaxSingle="true" />   
                                    </h:selectOneListbox>
                                    <h:outputText/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="IntCalParam2" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblBalBetween" styleClass="label" value="Bal. Between"/>
                                    <h:inputText  id="txtBalBetween" styleClass="input" value="#{spm.balBet}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                                        <a4j:support event="onblur" ajaxSingle="true" />   
                                    </h:inputText>
                                    <h:outputLabel id="lblBalBetweenAnd" styleClass="label" value="And"/>
                                    <h:inputText  id="txtAnd" styleClass="input" value="#{spm.andBalBet}" maxlength="13" style="width:120px" disabled="#{spm.disableFlag}">
                                        <a4j:support event="onblur" ajaxSingle="true" />   
                                    </h:inputText>
                                    <h:outputText/>
                                    <h:outputText/>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>
                    <rich:panel header="Exception Codes" style="text-align:left;">
                        <h:panelGrid id="ExceptionCodesInfoPanel" columns="2" style="width:100%;text-align:center;">
                            <h:panelGrid id="ExceptionCodes2InfoPanel"style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes1" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblDrBalLim2" styleClass="label" value="Dr. Bal. Limit"/>
                                    <h:inputText  id="txtDrBalLim2" styleClass="input" value="#{spm.drBalLimitExc}"onkeyup="this.value = this.value.toUpperCase();"maxlength="3" disabled="#{spm.disableFlag}" >
                                        <a4j:support action="#{spm.getExcepDescDrBalLimit}" event="onblur" reRender="stxtDrBalLim2"/>
                                    </h:inputText>
                                    <h:outputText id="stxtDrBalLim2" styleClass="output"  value="#{spm.drBalLimitExcValue}" style="color:green;" />
                                    <h:outputLabel id="lblCITranInactiveAcc" styleClass="label" value="C.I. Tran. to Inactive A/C"/>
                                    <h:inputText  id="txtCITranInactiveAcc" styleClass="input" value="#{spm.cITranInacAcc}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescCITranInacAcc}" event="onblur" reRender="stxtCITranInactiveAcc"/>
                                    </h:inputText>
                                    <h:outputText id="stxtCITranInactiveAcc" styleClass="output"  value="#{spm.cITranInacAccValue}"style="color:green;"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes2" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblCIDrTranDormantAmtInactiveAcc" styleClass="label" value="C.I. Dr. Tran. to Dormant A/C"/>
                                    <h:inputText  id="txtCIDrTranDormantAmtInactiveAcc" styleClass="input" value="#{spm.cIDrTranDormantAcct}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}" >
                                        <a4j:support action="#{spm.getExcepDescCIDrTranDormantAcc}" event="onblur" reRender="stxtCIDrTranDormantAmtInactiveAcc"/>
                                    </h:inputText>
                                    <h:outputText id="stxtCIDrTranDormantAmtInactiveAcc" styleClass="output"  style="color:green;"value="#{spm.cIDrTranDormantAcctValue}"/>
                                    <h:outputLabel id="lblBIDrTranDormantAmtInactiveAcc" styleClass="label" value="B.I. Dr. Tran. to Dormant A/C"/>
                                    <h:inputText  id="txtBIDrTranDormantAmtInactiveAcc" styleClass="input" value="#{spm.bIDrTranDormantAcct}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3">
                                        <a4j:support action="#{spm.getExcepDescBIDrTranDormantAcc}" event="onblur" reRender="stxtBIDrTranDormantAmtInactiveAcc" disabled="#{spm.disableFlag}"/>
                                    </h:inputText>
                                    <h:outputText id="stxtBIDrTranDormantAmtInactiveAcc"style="color:green;" styleClass="output"  value="#{spm.bIDrTranDormantAcctValue}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes3" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblChqIssuOnInactiveAcc" styleClass="label" value="Chq. Issued On an Inactive Account"/>
                                    <h:inputText  id="txtChqIssuOnInactiveAcc" styleClass="input" value="#{spm.chqIssuedOnInactiveAcct}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescChqIssueInacAcc}" event="onblur" reRender="stxtChqIssuOnInactiveAcc" />
                                    </h:inputText>
                                    <h:outputText id="stxtChqIssuOnInactiveAcc" style="color:green;"styleClass="output"  value="#{spm.chqIssuedOnInactiveAcctValue}"/>
                                    <h:outputLabel id="lblChqIssuOnDormantAcc" styleClass="label" value="Chq. Issued On a Dormant Account"/>
                                    <h:inputText  id="txtChqIssuOnDormantAcc" styleClass="input" value="#{spm.chqIssuedOnDormantAcct}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescChqIssueDorAcc}" event="onblur" reRender="stxtChqIssuOnDormantAcc"/>
                                    </h:inputText>
                                    <h:outputText id="stxtChqIssuOnDormantAcc"style="color:green;"styleClass="output"  value="#{spm.chqIssuedOnDormantAcctValue}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes7" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblChqBookWithoutMinBal" styleClass="label" value="Chq. Book Without Min. Bal."/>
                                    <h:inputText  id="txtChqBookWithoutMinBal" styleClass="input" value="#{spm.chqBkWithoutMinBal}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescChqBookMinBal}" event="onblur" reRender="stxtChqBookWithoutMinBal"/>
                                    </h:inputText>
                                    <h:outputText id="stxtChqBookWithoutMinBal" style="color:green;"styleClass="output"  value="#{spm.chqBkWithoutMinBalValue}"/>
                                    <h:outputLabel id="lblAccClosedWithinYear" styleClass="label" value="Account Closed Within an Year"/>
                                    <h:inputText  id="txtAccClosedWithinYear" styleClass="input" value="#{spm.accClosedWithinYr}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescAccClosedWithinYr}" event="onblur" reRender="stxtAccClosedWithinYear"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAccClosedWithinYear" style="color:green;"styleClass="output"  value="#{spm.accClosedWithinYrValue}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes9" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblNoOfWithExceed" styleClass="label" value="No. Of Withdrawls Exceeded"/>
                                    <h:inputText  id="txtNoOfWithExceed" styleClass="input" value="#{spm.noWithdrawlsExceeded}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescNoWithdrawlsExceeded}" event="onblur" reRender="stxtNoOfWithExceed"/>
                                    </h:inputText>
                                    <h:outputText id="stxtNoOfWithExceed" style="color:green;"styleClass="output"  value="#{spm.noWithdrawlsExceededValue}"/>
                                    <h:outputLabel id="lblTodOnMinorAcc" styleClass="label" value="TOD On a Minor A/C"/>
                                    <h:inputText  id="txtTodOnMinorAcc" styleClass="input" value="#{spm.tODMinorAcc}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescTodMinor}" event="onblur" reRender="stxtTodOnMinorAcc"/>
                                    </h:inputText>
                                    <h:outputText id="stxtTodOnMinorAcc" style="color:green;"styleClass="output"  value="#{spm.tODMinorAccValue}"/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="ExceptionCodes11" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblIntroducerNotCAHolder" styleClass="label" value="Introducer Not a CA Holder"/>
                                    <h:inputText  id="txtIntroducerNotCAHolder" styleClass="input" value="#{spm.introducerNotCAHold}"onkeyup="this.value = this.value.toUpperCase();"maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescIntroNotCAHold}" event="onblur" reRender="stxtIntroducerNotCAHolder"/>
                                    </h:inputText>
                                    <h:outputText id="stxtIntroducerNotCAHolder" style="color:green;"styleClass="output" value="#{spm.introducerNotCAHoldValue}"/>
                                    <h:outputLabel id="lblAccMinBalBlowSchMinBal" styleClass="label" value="A/C Min. Bal. Below Scheme Min. Bal."/>
                                    <h:inputText  id="txtAccMinBalBlowSchMinBal" styleClass="input" value="#{spm.accMinBalBlowSchMinBal}"onkeyup="this.value = this.value.toUpperCase();" maxlength="3" disabled="#{spm.disableFlag}">
                                        <a4j:support action="#{spm.getExcepDescAccMinBalBelowSchMinBal}" event="onblur" reRender="stxtAccMinBalBlowSchMinBal"/>
                                    </h:inputText>
                                    <h:outputText id="stxtAccMinBalBlowSchMinBal" style="color:green;"styleClass="output"  value="#{spm.accMinBalBlowSchMinBalValue}"/>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>
                </h:panelGrid>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
