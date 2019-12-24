<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="pm">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="ShemeParameterMasterPanel8" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblSchemeDescription" styleClass="label" value="Scheme Description"/>
                <h:inputText id="txtSchemeDescription" styleClass="input" style="width:120px" value="#{pm.schemeDesc}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="80">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblNativeSchemeDescription" styleClass="label" value="Native Scheme Description"/>
                <h:inputText id="txtNativeSchemeDescription" styleClass="input" style="width:120px" value="#{pm.nativeSchemeDesc}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="80">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblRptTranFlag" styleClass="label" value="Rpt Tran Flag"/>
                <h:selectOneListbox id="ddRptTranFlag" styleClass="ddlist" required="true" style="width:60px" value="#{pm.rptTransFlag}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddrptTransFlag}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3" columns="12" id="trFinRow2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblSchemeSupervisorID" styleClass="label" value="Scheme Supervisor ID"/>
                <h:inputText id="txtSchemeSupervisorID" styleClass="input" style="width:120px" value="#{pm.schemeSupId}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblStmtFreqType" styleClass="label" value="Stmt Freq Type/Number/WeekDay/Start Date/Np"/>
                <h:selectOneListbox id="ddStmtFreqType" styleClass="ddlist" required="true" style="width:75px" value="#{pm.stmtFrqType}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddstmtFrqType}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lbl21" styleClass="label" value="/"/>
                <h:selectOneListbox id="ddStmFreqWeekNumber" styleClass="ddlist" required="true" style="width: 75px" value="#{pm.stmtFrqNum}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddstmtFrqNum}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lbl22" styleClass="label" value="/"/>
                <h:selectOneListbox id="ddStmtFreqWeekDay" styleClass="ddlist" required="true" style="width: 60px" value="#{pm.stmtFrqDay}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddstmtFrqDay}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lbl20" styleClass="label" value="/"/>
                <h:inputText id="txtStmtFreqStartDate" styleClass="input" style="width:40px" value="#{pm.stmtFrqStartDt}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lbl23" styleClass="label" value="/"/>
                <h:selectOneListbox id="ddStmtFreqNP" styleClass="ddlist" required="true" style="width: 60px" value="#{pm.stmtFrqNP}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddstmtFrqNP}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow3" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblSchemeCrDrInd" styleClass="label" value="Scheme Cr/Dr Ind"/>
                <h:selectOneListbox id="ddSchemeCrDrInd" styleClass="ddlist" required="true" style="width:110px" value="#{pm.schemeCrDrInd}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddschemeCrDrInd}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblTurnOverDetailFlag" styleClass="label" value="OD with Multiple ROI Flag"/>
                <h:selectOneListbox id="ddTurnOverDetailFlag" styleClass="ddlist" required="true" style="width:110px" value="#{pm.turnoverDetailFlag}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddturnoverDetailFlag}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblSysGenAccNoFlag" styleClass="label" value="Sys Gen AccNo Flag"/>
                <h:selectOneListbox id="ddSysGenAccNoFlag" styleClass="ddlist" required="true" style="width:110px" value="#{pm.sysgenAcctNoFlag}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddsysgenAcctNoFlag}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col17,col17,col17,col17,col17,col17,col17,col17," columns="9" id="trFinRow4" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblTurnOverFreqType" styleClass="label" value="TurnOver Freq Type/ Number/ WeekDay/ Start Date/ Np"/>
                <h:panelGroup id="BtnPanelTurnOverFreqWeekNumber555">
                <h:selectOneListbox id="ddTurnOverFreqType" styleClass="ddlist" required="true" style="width:80px" value="#{pm.turnoverFreqType}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddturnoverFreqType}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lbldddTurnOverFreqWeekNumberpara" styleClass="label" value="/"/>
                </h:panelGroup>
                <h:panelGroup id="BtnPanelTurnOverFreqWeekNumber">
                    <h:selectOneListbox id="ddTurnOverFreqWeekNumber" styleClass="ddlist" required="true" style="width:80px" value="#{pm.turnoverFreqNo}" size="1" disabled="#{pm.pmFlag}">
                        <f:selectItems value="#{pm.ddturnoverFreqNo}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblddTurnOverFreqWeekNumber" styleClass="label" value="/"/>
                </h:panelGroup>
                <h:panelGroup id="BtnPanelTurnOverFreqWeekNumberUsed">
                    <h:selectOneListbox id="ddTurnOverFreqWeekDay" styleClass="ddlist" required="true" style="width:50px" value="#{pm.turnoverFreqDay}" size="1" disabled="#{pm.pmFlag}">
                        <f:selectItems value="#{pm.ddturnoverFreqDay}"/>
                         <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lbldddTurnOverFreqWeekNumber" styleClass="label" value="/"/>
                </h:panelGroup>
                <h:panelGroup id="BtnPaneltStartDate">
                    <h:inputText id="txtStartDate" styleClass="input" style="width:40px" value="#{pm.turnoverFreqDate}" disabled="#{pm.pmFlag}" maxlength="2"/>
                    <h:outputLabel id="lbldddStartDate" styleClass="label" value="/"/>
                </h:panelGroup>
                <h:selectOneListbox id="ddTurnOverFreqNp" styleClass="ddlist" required="true" style="width: 60px" value="#{pm.turnoverFreqNp}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddturnoverFreqNp}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAcctPrefix" styleClass="label" value="Acct Prefix"/>
                <h:inputText id="txtAcctPrefix" styleClass="input" style="width:40px" value="#{pm.acctPrefix}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow5" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblNxtNbrTblCode" styleClass="label" value="Nxt Nbr Tbl Code"/>
                <h:inputText id="txtNxtNbrTblCode" styleClass="input" style="width:110px"  value="#{pm.nxtNoTblCode}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="200">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblFCNRSchemeFlag1" styleClass="label" value="FCNR Scheme Flag"/>
                <h:selectOneListbox id="ddFCNRSchemeFlag1" styleClass="ddlist" required="true" style="width:110px" value="#{pm.fcnrSchemeFlg}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddfcnrSchemeFlg}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAcctClosuerAcrossSolsAlwdFlag1" styleClass="label" value="Acct Closuer Across Sols Alwd Flag"/>
                <h:selectOneListbox id="ddAcctClosuerAcrossSolsAlwdFlag1" styleClass="ddlist" required="true" style="width:110px" value="#{pm.acctCloserAcrossSols}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddacctCloserAcrossSols}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow6" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblEEfcSchemeFlag" styleClass="label" value="EEfc Scheme Flag"/>
                <h:selectOneListbox id="ddEEfcSchemeFlag" styleClass="ddlist" required="true" style="width:110px" value="#{pm.schemeFlag}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddschemeFlag}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblOption" styleClass="label" value="Option"/>
                <h:selectOneListbox id="ddOption" styleClass="ddlist" required="true" style="width:110px" value="#{pm.opt}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddopt}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblFDCRACCTPlaceHolder" styleClass="label" value="FD CR ACCT PlaceHolder"/>
                <h:inputText id="txtFDCRACCTPlaceHolder" styleClass="input" style="width:110px"  value="#{pm.fdCrAccountPlaceHolder}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow7" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCommitcalculationMethod" styleClass="label" value="Commit calculation Method "/>
                <h:selectOneListbox id="ddCommitcalculationMethod" styleClass="ddlist" required="true" style="width:110px" value="#{pm.commitCalMethd}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddcommitCalMethd}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblFDDrAcctPlaceHplder" styleClass="label" value="FD Dr Acct Place Hplder"/>
                <h:inputText id="txtFDDrAcctPlaceHplder" styleClass="input" style="width:110px"  value="#{pm.fdDrAccountPlaceHolder}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblMinCommitUtilisation" styleClass="label" value="Min Commit Utilisation"/>
                <h:inputText id="txtMinCommitUtilisation" styleClass="input" style="width:110px"  value="#{pm.minCommitUtln}" disabled="#{pm.pmFlag }" onkeyup="this.value = this.value.toUpperCase();" maxlength="13">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow8" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblStaffSchemeFlag" styleClass="label" value="Staff Scheme Flag"/>
                <h:selectOneListbox id="ddStaffSchemeFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.staffSchmeflg}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddstaffSchmeflg}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblDormantChargePeriodMonths" styleClass="label" value="Dormant Charge Period Months"/>
                <h:inputText id="txtDormantChargePeriodMonths" styleClass="input" style="width:110px"  value="#{pm.dormantChargeMonth}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblDormantChargePeriodDays" styleClass="label" value="Dormant Charge Period Days "/>
                <h:inputText id="txtDormantChargePeriodDays" styleClass="input" style="width:110px" value="#{pm.dormantChargeDay}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="3">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow9" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblNreSchemeFlag" styleClass="label" value="Nre Scheme Flag"/>
                <h:selectOneListbox id="ddNreSchemeFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.nrSchemeFlg}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddnrSchemeFlg}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblInactiveChargePeriodMonths" styleClass="label" value="Inactive Charge Period Months"/>
                <h:inputText id="txtInactiveChargePeriodMonths" styleClass="input" style="width:110px" value="#{pm.inactiveChrgmnth}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblInactiveChargePeriodDays" styleClass="label" value="Inactive Charge Period Days"/>
                <h:inputText id="txtInactiveChargePeriodDays" styleClass="input" style="width:110px" value="#{pm.inactiveChrgDay}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="3">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow10" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblNewAccountDuration" styleClass="label" value="New Account Duration"/>
                <h:inputText id="txtNewAccountDuration" styleClass="input" style="width:110px" value="#{pm.newAcctDur}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblProductGroup" styleClass="label" value="ProductGroup "/>
                <h:selectOneListbox id="ddProductGroup" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.productGroup}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddproductGroup}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblMinimumPostWorkClass" styleClass="label" value="Minimum Post Work Class"/>
                <h:selectOneListbox id="ddMinimumPostWorkClass" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.minPostWorkClass}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddminPostWorkClass}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow11" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblLinkTranToPurchaseSale" styleClass="label" value="LinkTranToPurchaseSale"/>
                <h:selectOneListbox id="ddLinkTranToPurchaseSale" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.linkTranSale}" size="1" disabled="#{SchemeMaster.pmFlag}">
                    <f:selectItems value="#{pm.ddlinkTranSale}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblMinimumAccountClosurePeriodMonth" styleClass="label" value="Minimum Account Closure Period Month"/>
                <h:inputText id="txtMinimumAccountClosurePeriodMonth" styleClass="input" style="width:110px" value="#{pm.minAcctClosurePeriodMonth}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="2">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblMinimumAccountClosurePeriodDays" styleClass="label" value="Minimum Account Closure Period Days"/>
                <h:inputText id="txtMinimumAccountClosurePeriodDays" styleClass="input" style="width:110px" value="#{pm.minAcctClosurePeriodDay}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="3">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow12" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblAccountMaintenancePeriod" styleClass="label" value="Account Maintenance Period"/>
                <h:selectOneListbox id="ddAccountMaintenancePeriod" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.acctMaintPeriod}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddacctMaintPeriod}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblDfltClgTranCode" styleClass="label" value="Dflt Clg Tran Code"/>
                <h:panelGroup id="BtnPanelDfltClgTranCode">
                    <h:inputText id="txtDfltClgTranCode" styleClass="input" style="width:110px" value="#{pm.dfltClgTransCode}" disabled="#{pm.pmFlag}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();">
                         <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                    <h:outputText id="stxtDfltClgTranCode" styleClass="label" value="#{pm.stdfltClgTransCode}" style="color:green;"/>
                </h:panelGroup>
                <h:outputLabel id="lblDfltInstrumenttype" styleClass="label" value="Dflt Instrumenttype"/>
                <h:selectOneListbox id="ddDfltInstrumenttype" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.dfltInsttype}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.dddfltInsttype}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow13" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblTransactionReferanceNumberFlag" styleClass="label" value="Transaction Referance Number Flag"/>
                <h:selectOneListbox id="ddTransactionReferanceNumberFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.trnRefNo}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddtrnRefNo}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblPegInterestForAccountFlag" styleClass="label" value="Peg Interest For Account Flag "/>
                <h:selectOneListbox id="ddPegInterestForAccountFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.pegIntForAcctFlg}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddpegIntForAcctFlg}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblModificationOfPegAllowedFlag" styleClass="label" value="Modification Of Peg Allowed Flag"/>
                <h:selectOneListbox id="ddModificationOfPegAllowedFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.modificationPegFlg}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddmodificationPegFlg}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow14" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblPegRevCustPrefFromCustMastFlag" styleClass="label" value="Peg Rev Cust Pref From Cust Mast Flag "/>
                <h:selectOneListbox id="ddPegRevCustPrefFromCustMastFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.pegRevCust}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddpegRevCust}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAcctPartitionAllowed" styleClass="label" value="Acct Partition Allowed"/>
                <h:selectOneListbox id="ddAcctPartitionAllowed" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.acctPartionAllowed}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddacctPartionAllowed}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAutoRenewalPeriodFlag" styleClass="label" value="Auto Renewal Period Flag"/>
                <h:selectOneListbox id="ddAutoRenewalPeriodFlag" styleClass="ddlist" required="true" style="width: 110px" value="#{pm.autoRenewal}" size="1" disabled="#{pm.pmFlag}">
                    <f:selectItems value="#{pm.ddAutoRenewal}"/>
                     <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow16" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblPDGLSubheadedCode" styleClass="label" value="PD GL Subheaded Code "/>
                <h:panelGroup id="BtnPanelPDGLSubheadedCode">
                    <h:inputText id="txtPDGLSubheadedCode" styleClass="input" style="width:110px" value="#{pm.pdGlSubHead}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="12">
                         <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                    <h:outputText id="stxtPDGLSubheadedCode" styleClass="label" value="#{pm.stClsPendingProxy}" style="color:green;"/>
                </h:panelGroup>
                <h:outputLabel id="lblStmtMessage" styleClass="label" value="Stmt Message"/>
                <h:inputText id="txtStmtMessage" styleClass="input" style="width:110px" value="#{pm.stmtMsg}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="100">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
                <h:outputLabel id="lblNativeStmtMessage" styleClass="label" value="Native Stmt Message"/>
                <h:inputText id="txtNativeStmtMessage" styleClass="input" style="width:110px" value="#{pm.nativeStmtMsg}" disabled="#{pm.pmFlag}" onkeyup="this.value = this.value.toUpperCase();" maxlength="100">
                     <a4j:support event="onblur" ajaxSingle="true" />   
            </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow17" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCreatedByUserID" styleClass="label" value="Created By User ID"/>
                <h:outputText id="stxtCreatedByUserID" styleClass="label" value="#{pm.cretedBy}" style="color:green;"/>
                <h:outputLabel id="lblCreationDate" styleClass="label" value="Creation Date"/>
                <h:outputText id="stxtCreationDate" styleClass="label" value="#{pm.createDt}" style="color:green;"/>
                <h:outputLabel id="lblLastUpdatedUserID" styleClass="label" value="Last Updated User ID"/>
                <h:outputText id="stxtLastUpdatedUserID" styleClass="label" value="#{pm.lastUpdateBy}" style="color:green;"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow18" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblLastUpdatDate" styleClass="label" value="Last Updat Date"/>
                <h:outputText id="stxtLastUpdatDate" styleClass="label" value="#{pm.lastUpdateDt}" style="color:green;"/>
                <h:outputLabel id="lblTotalModifications" styleClass="label" value="Total Modifications"/>
                <h:outputText id="stxtTotalModifications" styleClass="label" value="#{pm.totalModification}" style="color:green;"/>
                <h:outputText id="stxtwaste50" styleClass="label"  />
                <h:outputText id="stxtwaste51" styleClass="label" />
            </h:panelGrid>
            <a4j:region >
            <rich:panel header="Exception Codes" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow33" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblLiabilityExceedsGroupLimitExcep" styleClass="label" value="Liability Exceeds Group Limit Excep"/>
                    <h:inputText id="txtLiabilityExceedsGroupLimitExcep" styleClass="input" style="width:110px" value="#{pm.liabilityExceed}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" maxlength="3">
                        <a4j:support action="#{pm.descLiabilityExceed}" event="onblur" reRender="stxtLiabilityExceedsGroupLimitExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtLiabilityExceedsGroupLimitExcep" styleClass="label" value="#{pm.stliabilityExceed}" style="color:green;"/>
                    <h:outputText id="stxtwaste53" styleClass="label" />
                    <h:outputText id="stxtwaste54" styleClass="label" />
                    <h:outputText id="stxtwaste55" styleClass="label" />
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow19" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblInsufficientAvailableBalanceExcep" styleClass="label" value="Insufficient Available Balance Excep"/>
                    <h:inputText id="txtInsufficientAvailableBalanceExcep" maxlength="3" style="width:110px" value="#{pm.insufficientBal}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descInsufficientBal}" event="onblur" reRender="stxtInsufficientAvailableBalanceExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtInsufficientAvailableBalanceExcep" styleClass="label" value="#{pm.stinsufficientBal}" style="color:green;"/>
                    <h:outputLabel id="lblAccountNameChangeExcep" styleClass="label" value=" Account Name Change Excep"/>
                    <h:inputText id="txtAccountNameChangeExcep" maxlength="3" style="width:110px" value="#{pm.acctNameChng}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctNameChng}" event="onblur" reRender="stxtAccountNameChangeExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAccountNameChangeExcep" styleClass="label" value="#{pm.stacctNameChng}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow20" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblStaleInstrumentExcep" styleClass="label" value="Stale Instrument Excep"/>
                    <h:inputText id="txtStaleInstrumentExcep" maxlength="3" style="width:110px" value="#{pm.staleInstrment}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descStaleInstrment}" event="onblur" reRender="stxtStaleInstrumentExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtStaleInstrumentExcep" styleClass="label" value="#{pm.ststaleInstrment}" style="color:green;"/>
                    <h:outputLabel id="lblAccountFrozenExcep" styleClass="label" value="Account Frozen Excep"/>
                    <h:inputText id="txtAccountFrozenExcep" maxlength="3" style="width:110px" value="#{pm.acctFrozenExcep}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctFrozenExcep}" event="onblur" reRender="stxtAccountFrozenExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAccountFrozenExcep" styleClass="label" value="#{pm.stacctFrozenExcep}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow21" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCustomerIDMismatchDr" styleClass="label" value="Customer ID Mismatch Dr"/>
                    <h:inputText id="txtCustomerIDMismatchDr" maxlength="3" style="width:110px" value="#{pm.custIdMismatchDr}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descCustIdMismatchDr}" event="onblur" reRender="stxtCustomerIDMismatchDr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCustomerIDMismatchDr" styleClass="label" value="#{pm.stcustIdMismatchDr}" style="color:green;"/>
                    <h:outputLabel id="lblCustomerIDMismatchCr" styleClass="label" value="Customer ID Mismatch Cr"/>
                    <h:inputText id="txtlblCustomerIDMismatchCr" maxlength="3" style="width:110px" value="#{pm.custIdMismatchcr}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descCustIdMismatchcr}" event="onblur" reRender="stxtlblCustomerIDMismatchCr,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtlblCustomerIDMismatchCr" styleClass="label" value="#{pm.stcustIdMismatchcr}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow22" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblBackDatedTransactionExcep" styleClass="label" value="Back Dated Transaction Excep"/>
                    <h:inputText id="txtBackDatedTransactionExcep" maxlength="3" style="width:110px" value="#{pm.backDatedTrn}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descBackDatedTrn}" event="onblur" reRender="stxtBackDatedTransactionExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtBackDatedTransactionExcep" styleClass="label" value="#{pm.stbackDatedTrn}" style="color:green;"/>
                    <h:outputLabel id="lblValueDatedTransactionExcep" styleClass="label" value="Value Dated Transaction Excep"/>
                    <h:inputText id="txtValueDatedTransactionExcep" maxlength="3" style="width:110px" value="#{pm.valueDateTrn}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descValueDateTrn}" event="onblur" reRender="stxtValueDatedTransactionExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtValueDatedTransactionExcep" styleClass="label" value="#{pm.stvalueDateTrn}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow23" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCashTransactionExcep" styleClass="label" value="Cash Transaction Excep"/>
                    <h:inputText id="txtCashTransactionExcep" maxlength="3" style="width:110px" value="#{pm.cashTrn}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descCashTrn}" event="onblur" reRender="stxtCashTransactionExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCashTransactionExcep" styleClass="label" value="#{pm.stcashTrn}" style="color:green;"/>
                    <h:outputLabel id="lblAccountOpenMatrixExcep" styleClass="label" value="Account Open Matrix Excep"/>
                    <h:inputText id="txtAccountOpenMatrixExcep" maxlength="3" style="width:110px" value="#{pm.acctOpenMatrix}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctOpenMatrix}" event="onblur" reRender="stxtAccountOpenMatrixExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAccountOpenMatrixExcep" styleClass="label" value="#{pm.stacctOpenMatrix}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow24" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblClearingTransactionExcep" styleClass="label" value="Clearing Transaction Excep"/>
                    <h:inputText id="txtClearingTransactionExcep" maxlength="3" style="width:110px" value="#{pm.clrTrnExcp}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descClrTrnExcp}" event="onblur" reRender="stxtClearingTransactionExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtClearingTransactionExcep" styleClass="label" value="#{pm.stclrTrnExcp}" style="color:green;"/>
                    <h:outputLabel id="lblTransferTransactionExcep" styleClass="label" value="Transfer Transaction Excep"/>
                    <h:inputText id="txtTransferTransactionExcep" maxlength="3" style="width:110px" value="#{pm.transferTrn}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descTransferTrn}" event="onblur" reRender="stxtTransferTransactionExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtTransferTransactionExcep" styleClass="label" value="#{pm.sttransferTrn}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow25" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblSanctionedLimitExpiredExcep" styleClass="label" value="Sanctioned Limit Expired Excep"/>
                    <h:inputText id="txtSanctionedLimitExpiredExcep" maxlength="3" style="width:110px" value="#{pm.sanctionedLmt}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descSanctionedLmt}" event="onblur" reRender="stxtSanctionedLimitExpiredExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtSanctionedLimitExpiredExcep" styleClass="label" value="#{pm.stsanctionedLmt}" style="color:green;"/>
                    <h:outputLabel id="lblSanctionedExceedsLimitExcep" styleClass="label" value="Sanctioned Exceeds Limit Excep"/>
                    <h:inputText id="txtSanctionedExceedsLimitExcep" maxlength="3" style="width:110px" value="#{pm.sanctionedExceed}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descSanctionedExceed}" event="onblur" reRender="stxtSanctionedExceedsLimitExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtSanctionedExceedsLimitExcep" styleClass="label" value="#{pm.stsanctionedExceed}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow26" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCustDrWithoutCheque" styleClass="label" value="Cust Dr Without Cheque"/>
                    <h:inputText id="txtCustDrWithoutCheque" maxlength="3" style="width:110px" value="#{pm.cusrDrWithoutChq}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descCusrDrWithoutChq}" event="onblur" reRender="stxtCustDrWithoutCheque,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtCustDrWithoutCheque" styleClass="label" value="#{pm.stcusrDrWithoutChq}" style="color:green;"/>
                    <h:outputLabel id="lblRefferedAccountClosure" styleClass="label" value="Reffered AccountClosure"/>
                    <h:inputText id="txtRefferedAccountClosure" maxlength="3" style="width:110px" value="#{pm.refferedAcctClosure}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descRefferedAcctClosure}" event="onblur" reRender="stxtRefferedAccountClosure,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtRefferedAccountClosure" styleClass="label" value="#{pm.strefferedAcctClosure}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow27" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblUserTodGrantException" styleClass="label" value="User Tod Grant Exception"/>
                    <h:inputText id="txtUserTodGrantException" maxlength="3" style="width:110px" value="#{pm.userTodGrntExcp}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descUserTodGrntExcp}" event="onblur" reRender="stxtUserTodGrantException,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtUserTodGrantException" styleClass="label" value="#{pm.stuserTodGrntExcp}" style="color:green;"/>
                    <h:outputLabel id="lblAutoTodGrantException" styleClass="label" value="Auto Tod Grant Exception"/>
                    <h:inputText id="txtAutoTodGrantException" maxlength="3" style="width:110px" value="#{pm.autoTodGrntExcp}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAutoTodGrntExcp}" event="onblur" reRender="stxtAutoTodGrantException,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAutoTodGrantException" styleClass="label" value="#{pm.stAutoTodGrntExcp}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow28" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblAccountInDrBalanceExp" styleClass="label" value="Account In Dr Balance Exp"/>
                    <h:inputText id="txtAccountInDrBalanceExp" maxlength="3" style="width:110px" value="#{pm.acctInDrBal}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctInDrBal}" event="onblur" reRender="stxtAccountInDrBalanceExp,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAccountInDrBalanceExp" styleClass="label" value="#{pm.stacctInDrBal}" style="color:green;"/>
                    <h:outputLabel id="lblAccountInCrBalanceExp" styleClass="label" value="Account In Cr Balance Exp"/>
                    <h:inputText id="txtAccountInCrBalanceExp" maxlength="3" style="width:110px" value="#{pm.acctInCrBal}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctInCrBal}" event="onblur" reRender="stxtAccountInCrBalanceExp,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAccountInCrBalanceExp" styleClass="label" value="#{pm.stacctInCrBal}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow29" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblOverrideDfLtCheque" styleClass="label" value="Override Df Lt Cheque"/>
                    <h:inputText id="txtOverrideDfLtCheque" maxlength="3" style="width:110px" value="#{pm.overrideDfltChq}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descOverrideDfltChq}" event="onblur" reRender="stxtOverrideDfLtCheque,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtOverrideDfLtCheque" styleClass="label" value="#{pm.stoverrideDfltChq}" style="color:green;"/>
                    <h:outputLabel id="lblInvalidReportCodeExcep" styleClass="label" value="Invalid Report Code Excep"/>
                    <h:inputText id="txtInvalidReportCodeExcep" maxlength="3" style="width:110px" value="#{pm.invalidReportCode}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descInvalidReportCode}" event="onblur" reRender="stxtInvalidReportCodeExcep,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtInvalidReportCodeExcep" styleClass="label" value="#{pm.stinvalidReportCode}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow30" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMemoPadExists" styleClass="label" value="Memo Pad Exists"/>
                    <h:inputText id="txtMemoPadExists" maxlength="3" style="width:110px" value="#{pm.mamoPadExist}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descMamoPadExist}" event="onblur" reRender="stxtMemoPadExists,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtMemoPadExists" styleClass="label" value="#{pm.stmamoPadExist}" style="color:green;"/>
                    <h:outputLabel id="lblDfltIntParmChngException" styleClass="label" value="Dflt Int Parm Chng Exception"/>
                    <h:inputText id="txtDfltIntParmChngException" maxlength="3" style="width:110px" value="#{pm.dfltIntParmExcp}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descDfltIntParmExcp}" event="onblur" reRender="stxtDfltIntParmChngException,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDfltIntParmChngException" styleClass="label" value="#{pm.stdfltIntParmExcp}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow31" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblAcctBalBeleowTheReqMin" styleClass="label" value="Acct Bal Beleow The Req Min"/>
                    <h:inputText id="txtAcctBalBeleowTheReqMin" maxlength="3" style="width:110px" value="#{pm.acctBalBelowmin}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descAcctBalBelowmin}" event="onblur" reRender="stxtAcctBalBeleowTheReqMin,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtAcctBalBeleowTheReqMin" styleClass="label" value="#{pm.stacctBalBelowmin}" style="color:green;"/>
                    <h:outputLabel id="lblMinBalPenalChrgNotCalc" styleClass="label" value="Min Bal Penal Chrg Not Calc"/>
                    <h:inputText id="txtMinBalPenalChrgNotCalc" maxlength="3" style="width:110px" value="#{pm.minBalPenalChrg}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descMinBalPenalChrg}" event="onblur" reRender="stxtMinBalPenalChrgNotCalc,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtMinBalPenalChrgNotCalc" styleClass="label" value="#{pm.stminBalPenalChrg}" style="color:green;"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow32" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblIntForPastDueNotUpToDate" styleClass="label" value="Int For Past Due Not Up To Date"/>
                    <h:inputText id="txIntForPastDueNotUpToDate" maxlength="3" style="width:110px" value="#{pm.intForPastDue}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descIntForPastDue}" event="onblur" reRender="stxtIntForPastDueNotUpToDate,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtIntForPastDueNotUpToDate" styleClass="label" value="#{pm.stintForPastDue}" style="color:green;"/>
                    <h:outputLabel id="lblDrTranToPastDueAsset" styleClass="label" value="Dr Tran To Past Due Asset"/>
                    <h:inputText id="txtDrTranToPastDueAsset" maxlength="3" style="width:110px" value="#{pm.drTranToPast}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{pm.pmFlag}" styleClass="input">
                        <a4j:support action="#{pm.descDrTranToPast}" event="onblur" reRender="stxtDrTranToPastDueAsset,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtDrTranToPastDueAsset" styleClass="label" value="#{pm.stdrTranToPast}" style="color:green;"/>
                </h:panelGrid>
            </rich:panel>
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
