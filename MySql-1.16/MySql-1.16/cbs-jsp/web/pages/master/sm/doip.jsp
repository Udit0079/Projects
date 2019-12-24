<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="doip">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel100" style="width:100%;text-align:center;">
            <rich:panel header="Overdue Interest Parameters" style="text-align:left;">
                <h:panelGrid id="overDueInfoPanel" columns="2" style="width:100%;text-align:center;">
                    <h:panelGrid id="leftOverDuePanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="overdueRow1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblGlSubHeadCode" styleClass="label" value="Overdue GL Subhead Code"/>
                            <h:inputText  value="#{doip.overdueGLSubheadCode}"id="txtGlSubHeadCode" styleClass="input" disabled="#{doip.disableFlag}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                            <h:outputText id="stxtGlSubHeadCode" styleClass="output" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="overdueRow2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblOverdueIntCode" styleClass="label" value="Overdue Interest Code"/>
                            <h:inputText  value="#{doip.overdueIntCode}"id="txtOverdueIntCode" styleClass="input" disabled="#{doip.disableFlag}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                            <h:outputText id="stxtOverdueIntCode" styleClass="output" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="overdueRow3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblOverdueIntTblCode" styleClass="label" value="Overdue Int. Tbl. Code Type"/>
                            <h:selectOneListbox  value="#{doip.overdueIntTblCode}"style="width:28%;"id="ddOverdueIntTblCode" disabled="#{doip.disableFlag}" styleClass="ddlist" size="1">
                                <f:selectItems value="#{doip.overdueIntTblCodeList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                            <h:outputText id="stxtOverdueIntTblCode" styleClass="output"  />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="overdueRow4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblOverdueIntCalcMethod" styleClass="label" value="Overdue Int. Calc. Method"/>
                            <h:selectOneListbox value="#{doip.overdueIntCalcMethod}" style="width:28%;"id="ddOverdueIntCalcMethod" disabled="#{doip.disableFlag}" styleClass="ddlist" size="1">
                                <f:selectItems value="#{doip.overdueIntCalcMethodList}"/>
                                <a4j:support event="onblur" ajaxSingle="true"/>
                            </h:selectOneListbox>
                            <h:outputText id="stxtOverdueIntCalcMethod" styleClass="output"  value="#{doip.overdueIntCalcMethodValue}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Exception Codes" style="text-align:left;">
                <h:panelGrid id="exceptionCodesInfoPanel" columns="2" style="width:100%;text-align:center;">
                    <h:panelGrid id="leftExceptionCodesPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblRenewalPerdExcd" styleClass="label" value="Renewal Period Exceed"/>
                            <h:inputText  value="#{doip.renewalPerdExcd}"id="txtRenewalPerdExcd" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support action="#{doip.getExcepDescRenPrd}" event="onblur" reRender="stxtRenewalPerdExcd,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtRenewalPerdExcd" styleClass="output" value="#{doip.renewalPerdExcdValue}" style="color:green;"/>
                            <h:outputLabel id="lblMaxPerd" styleClass="label" value="Maximum Period"/>
                            <h:inputText  value="#{doip.maxPerd}"id="txtMaxPerd" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support action="#{doip.getExcepDescMaxPrd}" event="onblur" reRender="stxtMaxPerd,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtMaxPerd" styleClass="output" value="#{doip.maxPerdValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblMaxAmt" styleClass="label" value="Maximum Amount"/>
                            <h:inputText  value="#{doip.maxAmt}"id="txtMaxAmt" styleClass="input"  disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support action="#{doip.getExcepDescMaxAmt}" event="onblur" reRender="stxtMaxAmt,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtMaxAmt" styleClass="output" value="#{doip.maxAmtValue}" style="color:green;"/>
                            <h:outputLabel id="lblMinorPreClosure" styleClass="label" value="Minor Dep Preclosure"/>
                            <h:inputText  value="#{doip.minorDepPreclosure}"id="txtMinorPreClosure" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support action="#{doip.getExcepDescMinorDep}" event="onblur" reRender="stxtMinorPreClosure,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtMinorPreClosure" styleClass="output" value="#{doip.minorDepPreclosureValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblExtension" styleClass="label" value="Extension"/>
                            <h:inputText  value="#{doip.extension}"id="txtExtension" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescExtension}" event="onblur" reRender="stxtExtension,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtExtension" styleClass="output" value="#{doip.extensionValue}" style="color:green;"/>
                            <h:outputLabel id="lblSplCatgClosure" styleClass="label" value="Spl. Catg. Closure"/>
                            <h:inputText  value="#{doip.splCatgClosure}"id="txtSplCatgClosure" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescSplCatgClosure}" event="onblur" reRender="stxtSplCatgClosure,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtSplCatgClosure" styleClass="output" value="#{doip.splCatgClosureValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblMatAmtTolerance" styleClass="label" value="Mat. Amt. Tolerance"/>
                            <h:inputText  value="#{doip.matAmtTolerance}"id="txtMatAmtTolerance" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3"  style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescMatAmtTolerance}" event="onblur" reRender="stxtMatAmtTolerance,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtMatAmtTolerance" styleClass="output" value="#{doip.matAmtToleranceValue}" style="color:green;"/>
                            <h:outputLabel id="lblNilPenalty" styleClass="label" value="Nil Penalty"/>
                            <h:inputText  value="#{doip.nilPenalty}"id="txtNilPenalty" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescNilPenalty}" event="onblur" reRender="stxtNilPenalty,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtNilPenalty" styleClass="output" value="#{doip.nilPenaltyValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow5" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDisContinuedInst" styleClass="label" value="DisContinued Inst."/>
                            <h:inputText  value="#{doip.disContinuedInst}"id="txtDisContinuedInst" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescDiscontInst}" event="onblur" reRender="stxtDisContinuedInst,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtDisContinuedInst" styleClass="output" value="#{doip.disContinuedInstValue}" style="color:green;"/>
                            <h:outputLabel id="lblTransferIn" styleClass="label" value="Transfer In"/>
                            <h:inputText  value="#{doip.transferIn}"id="txtTransferIn" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescTransferIn}" event="onblur" reRender="stxtTransferIn,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtTransferIn" styleClass="output" value="#{doip.transferInValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow6" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAcctVerBalCheck" styleClass="label" value="Acct. Ver. Bal. Check"/>
                            <h:inputText  value="#{doip.acctVerBalCheck}"id="txtAcctVerBalCheck" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescAccVerBal}" event="onblur" reRender="stxtAcctVerBalCheck,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtAcctVerBalCheck" styleClass="output" value="#{doip.acctVerBalCheckValue}" style="color:green;"/>
                            <h:outputLabel id="lblSystemDrTransAllowed" styleClass="label" value="System Dr. Trans. Allowed"/>
                            <h:inputText  value="#{doip.systemDrTransAllowed}"id="txtSystemDrTransAllowed" disabled="#{doip.disableFlag}" maxlength="3"  style="width:80px;" styleClass="input"onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescSysDrTransAllowed}" event="onblur" reRender="stxtSystemDrTransAllowed,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtSystemDrTransAllowed" styleClass="output" value="#{doip.systemDrTransAllowedValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow7" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDupReprntRcpt" styleClass="label" value="Duplicate/Reprint Receipt"/>
                            <h:inputText  value="#{doip.dupReprntRcpt}"id="txtDupReprntRcpt" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescDupReprntRcpt}" event="onblur" reRender="stxtDupReprntRcpt,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtDupReprntRcpt" styleClass="output" value="#{doip.dupReprntRcptValue}" style="color:green;"/>
                            <h:outputLabel id="lblPrematureClosure" styleClass="label" value="Premature Closure"/>
                            <h:inputText  value="#{doip.preMatureClosure}"id="txtPrematureClosure" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" styleClass="input"onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescPrematureClosure}" event="onblur" reRender="stxtPrematureClosure,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtPrematureClosure" styleClass="output" value="#{doip.preMatureClosureValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow8" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblNoticePerd" styleClass="label" value="Notice Perd. Below Min. Notice Perd."/>
                            <h:inputText  value="#{doip.noticePerdMinNoticePerd}"id="txtNoticePerd" disabled="#{doip.disableFlag}" maxlength="3" styleClass="input" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescNoticePeriod}" event="onblur" reRender="stxtNoticePerd,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtNoticePerd" styleClass="output" value="#{doip.noticePerdMinNoticePerdValue}" style="color:green;"/>
                            <h:outputLabel id="lblPreferetialIntChrg" styleClass="label" value="Default Value For Preferential Int. Chgd."/>
                            <h:inputText  value="#{doip.defaultValueForPreIntChgd}"id="txtPreferetialIntChrg" disabled="#{doip.disableFlag}" maxlength="3" style="width:80px;" styleClass="input"onkeyup="this.value = this.value.toUpperCase();"  >
                                <a4j:support action="#{doip.getExcepDescDefaultValue}" event="onblur" reRender="stxtPreferetialIntChrg,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtPreferetialIntChrg" styleClass="output" value="#{doip.defaultValueForPreIntChgdValue}" style="color:green;"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="excRow9" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblBackValueDate" styleClass="label" value="Back Value Dated A/C Opening"/>
                            <h:inputText  value="#{doip.backValueDateAccOpen}"id="txtBackValueDate" styleClass="input" disabled="#{doip.disableFlag}" maxlength="3"  style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescBackValueDate}" event="onblur" reRender="stxtBackValueDate,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtBackValueDate" styleClass="output" value="#{doip.backValueDateAccOpenValue}" style="color:green;"/>
                            <h:outputLabel id="lblFutureValueDate" styleClass="label" value="Future Value Dated A/C Opening"/>
                            <h:inputText  value="#{doip.futureValueDateAccOpen}"id="txtFutureValueDate" disabled="#{doip.disableFlag}" maxlength="3"  styleClass="input" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support action="#{doip.getExcepDescFutureValueDate}" event="onblur" reRender="stxtFutureValueDate,lblMsg"/>
                            </h:inputText>
                            <h:outputText id="stxtFutureValueDate" styleClass="output" value="#{doip.futureValueDateAccOpenValue}" style="color:green;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
