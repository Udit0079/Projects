<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="crbosd">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel11" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="othRow" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblOMaxSanctionLimit" styleClass="label" value="Max Sanction Limit"/>
                <h:inputText id="txtMaxSanctionLimit" styleClass="input" style="width:80px" value="#{crbosd.maxSanction}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblDebitBalanceLimit" styleClass="label" value="Debit Balance Limit"/>
                <h:inputText id="txtDebitBalanceLimit" styleClass="input" style="width:80px" value="#{crbosd.debitBalance}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblLedgerFolioChargeOrFolio" styleClass="label" value="Ledger Folio Charge Or Folio"/>
                <h:inputText id="txtLedgerFolioChargeOrFolio" styleClass="input" style="width:80px" value="#{crbosd.ledgerFolio}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="othRow3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblInactiveAccountAbrnmlTranLimit" styleClass="label" value="Inactive Account Abrnml Tran Limit"/>
                <h:inputText id="txtInactiveAccountAbrnmlTranLimit" styleClass="input" style="width:80px" value="#{crbosd.inactiveAccount}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblDormantAccountAbrnmlTranLimit" styleClass="label" value="Dormant Account Abrnml Tran Limit"/>
                <h:inputText id="txtDormantAccountAbrnmlTranLimit" styleClass="input" style="width:80px" value="#{crbosd.dormantAccount}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblMaximunPenalInterest" styleClass="label" value="Maximun Penal Interest"/>
                <h:inputText id="txtMaximunPenalInterest" styleClass="input" style="width:80px" value="#{crbosd.maximunPenal}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="othRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblDurationToMarkAccountAsInactive" styleClass="label" value="Duration To Mark Account As Inactive"/>
                <h:inputText id="txtDurationToMarkAccountAsInactive" styleClass="input" style="width:80px" value="#{crbosd.durationToMark}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblDurationFromInactiveToDormant" styleClass="label" value="Duration From Inactive To Dormant"/>
                <h:inputText id="txtDurationFromInactiveToDormant" styleClass="input" style="width:80px" value="#{crbosd.durationFrom}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }"/>
                <h:outputLabel id="lblDormantAccountChargeEvent" styleClass="label" value="Dormant Account Charge Event"/>
                <h:inputText id="txtDormantAccountChargeEvent" styleClass="input" style="width:80px" value="#{crbosd.dormantAccountTranlimit}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="othRow2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblInactiveAccountChargeEvent" styleClass="label" value="Inactive Account Charge Event"/>
                <h:inputText id="txtInactiveAccountChargeEvent" styleClass="input" style="width:80px" value="#{crbosd.inactiveAccountabrml}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }"/>
                <h:outputLabel id="lblAllowSweeps" styleClass="label" value="Allow Sweeps"/>
                <h:selectOneListbox id="ddAllowSweeps" styleClass="ddlist" required="true" style="width:60px" value="#{crbosd.allowSweeps}" size="1" disabled="#{crbosd.crbosdFlag }">
                    <f:selectItems value="#{crbosd.ddCrbosdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />   
            </h:selectOneListbox>
                <h:outputLabel id="lblDebitAgainstUnclearBalance" styleClass="label" value="Debit Against Unclear Balance"/>
                <h:selectOneListbox id="ddDebitAgainstUnclearBalance" styleClass="ddlist" required="true" style="width:60px" value="#{crbosd.debitAgainst}" size="1" disabled="#{crbosd.crbosdFlag }">
                    <f:selectItems value="#{crbosd.ddCrbosdTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />  
                </h:selectOneListbox>
            </h:panelGrid>
            <rich:panel header="Exception Codes" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDebitBalanceLimitExce" styleClass="label" value="Debit Balance Limit Exce"/>
                    <h:inputText id="txtDebitBalanceLimitExce" styleClass="input" style="width:80px" value="#{crbosd.debitBalanceabrnml}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descdebitBalanceabrnml}" event="onblur" reRender="stxtDebitBalanceLimitExce,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDebitBalanceLimitExce" styleClass="label"  style="color:green;" value="#{crbosd.stdebitBalanceabrnml}"/>
                    <h:outputLabel id="lblBPPtranOutsideBills" styleClass="label" value="BP Ptran Outside Bills"/>
                    <h:inputText id="txtBPPtranOutsideBills" styleClass="input" style="width:80px" value="#{crbosd.bPPtranOutside}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descbPPtranOutside}" event="onblur" reRender="stxtBPPtranOutsideBills,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtBPPtranOutsideBills" styleClass="label"  style="color:green;" value="#{crbosd.stbPPtranOutside}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblSancOrDisbExceedExpOrdOrDc" styleClass="label" value="Sanc Or Disb Exceed Exp Ord Or Dc"/>
                    <h:inputText id="txtSancOrDisbExceedExpOrdOrDc" styleClass="input" style="width:80px" value="#{crbosd.sancOrDisbExceed}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descsancOrDisbExceed}" event="onblur" reRender="stxtSancOrDisbExceedExpOrdOrDc,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtSancOrDisbExceedExpOrdOrDc" styleClass="label"  style="color:green;" value="#{crbosd.stsancOrDisbExceed}"/>
                    <h:outputLabel id="lblSancOrDisbWithoutExpOrdOrDc" styleClass="label" value="Sanc Or Disb Without Exp Ord Or Dc"/>
                    <h:inputText id="txtSancOrDisbWithoutExpOrdOrDc" styleClass="input" style="width:80px" value="#{crbosd.sancOrDisbWithout}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descSancOrDisbWithout}" event="onblur" reRender="stxtSancOrDisbWithoutExpOrdOrDc,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtSancOrDisbWithoutExpOrdOrDc" styleClass="label"  style="color:green;" value="#{crbosd.stsancOrDisbWithout}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCiTranToInactiveAcct" styleClass="label" value="CI Tran To Inactive Acct"/>
                    <h:inputText id="txtCiTranToInactiveAcct" styleClass="input" style="width:80px" value="#{crbosd.ciTranToInact}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descCiTranToInact}" event="onblur" reRender="stxtCiTranToInactiveAcct,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCiTranToInactiveAcct" styleClass="label"  style="color:green;" value="#{crbosd.stciTranToInact}"/>
                    <h:outputLabel id="lblCIDRTranToDormantAcct" styleClass="label" value="CI DR Tran To Dormant Acct"/>
                    <h:inputText id="txtCIDRTranToDormantAcct" styleClass="input" style="width:80px" value="#{crbosd.cIDRTranTo}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descCIDRTranTo}" event="onblur" reRender="stxtCIDRTranToDormantAcct,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCIDRTranToDormantAcct" styleClass="label"  style="color:green;" value="#{crbosd.stcIDRTranTo}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblBIDRTranToDormantAcct" styleClass="label" value="BI DR Tran To Dormant Acct"/>
                    <h:inputText id="txtBIDRTranToDormantAcct" styleClass="input" style="width:80px" value="#{crbosd.bIDRTranToDorman}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descBIDRTranToDorman}" event="onblur" reRender="stxtBIDRTranToDormantAcct,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtBIDRTranToDormantAcct" styleClass="label"  style="color:green;" value="#{crbosd.stbIDRTranToDorman}"/>
                    <h:outputLabel id="lblChequeIssuedToInactiveAcct" styleClass="label" value="Cheque Issued To Inactive Acct"/>
                    <h:inputText id="txtChequeIssuedToInactiveAcct" styleClass="input" style="width:80px" value="#{crbosd.chequeIssued}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descChequeIssued}" event="onblur" reRender="stxtChequeIssuedToInactiveAcct,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtChequeIssuedToInactiveAcct" styleClass="label"  style="color:green;" value="#{crbosd.stchequeIssued}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblChequeIssuedToDormantAcct" styleClass="label" value="Cheque Issued To Dormant Acct"/>
                    <h:inputText id="txtChequeIssuedToDormantAcct" styleClass="input" style="width:80px" value="#{crbosd.chequeIssuedToDormant}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descChequeIssuedToDormant}" event="onblur" reRender="stxtChequeIssuedToDormantAcct,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtChequeIssuedToDormantAcct" styleClass="label"  style="color:green;" value="#{crbosd.stchequeIssuedToDormant}"/>
                    <h:outputLabel id="lblSanctionLimitCompletelyUtilised" styleClass="label" value="Sanction Limit Completely Utilised"/>
                    <h:inputText id="txtSanctionLimitCompletelyUtilised" styleClass="input" style="width:80px" value="#{crbosd.sanctionLimitCompletely}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descSanctionLimitCompletely}" event="onblur" reRender="stxtSanctionLimitCompletelyUtilised,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtSanctionLimitCompletelyUtilised" styleClass="label"  style="color:green;" value="#{crbosd.stsanctionLimitCompletely}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="custDelRow5" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblAcctMiniBalBelowSchemeMinBal" styleClass="label" value="Acct Mini Bal Below Scheme Min Bal"/>
                    <h:inputText id="txtAcctMiniBalBelowSchemeMinBal" styleClass="input" style="width:80px" value="#{crbosd.acctMiniBalBelowScheme}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{crbosd.crbosdFlag }">
                        <a4j:support action="#{crbosd.descacctMiniBalBelowScheme}" event="onblur" reRender="stxtAcctMiniBalBelowSchemeMinBal,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAcctMiniBalBelowSchemeMinBal" styleClass="label"  style="color:green;" value="#{crbosd.stacctMiniBalBelowScheme}"/>
                    <h:outputText id="stxtDr20" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr21" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtDr22" styleClass="label"  style="color:green;"/>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
