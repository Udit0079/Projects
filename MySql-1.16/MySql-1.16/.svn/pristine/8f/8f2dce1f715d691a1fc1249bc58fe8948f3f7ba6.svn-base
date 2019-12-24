<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lpd">
    <h:panelGrid id="schemePopUpForm" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="SchemeLoanPrepaymentDetailsleftPanel11" style="width:100%;text-align:center;">
            <rich:panel header="Prepayment Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="repRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPrepaymentIntReductionMethod" styleClass="label" value="Prepayment Int Reduction Method"/>
                    <h:selectOneListbox id="ddPrepaymentIntReductionMethod" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.prepaymentIntReductionMethod}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.lpdDdtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblApplyPrepaymentCharges" styleClass="label" value="Apply Prepayment Charges"/>
                    <h:selectOneListbox id="ddApplyPrepaymentCharges1" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.applyPrepaymentCharges}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.lpdDdtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMinAmountforPrepayment" styleClass="label" value="Min Amount for Prepayment"/>
                    <h:inputText id="txtMinAmountforPrepayment" styleClass="input" style="width:120px" value="#{lpd.minAmountforPrepayment}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="repRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblNoPrepaymentChargesAfterMonths" styleClass="label" value="No Prepayment Charges After Months/Days"/>
                    <h:panelGroup id="groupPanelNoPrepaymentChargesAfterMonths" layout="block">
                        <h:inputText id="txtNoPrepaymentChargesAfterMonths" styleClass="input" style="width:60px" value="#{lpd.noPrepaymentChargesAfterMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel id="lblNoPrepaymentChargesAfterDays" styleClass="label" value="/"/>
                        <h:inputText id="txtNoPrepaymentChargesAfterDays" styleClass="input" style="width:60px" value="#{lpd.noPrepaymentChargesAfterDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel id="lblLimitForPrepaymentInAYear" styleClass="label" value="Limit For Prepayment In a Year"/>
                    <h:inputText id="txtLimitForPrepaymentInAYear" styleClass="input" style="width:120px" value="#{lpd.limitForPrepaymentInAYear}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:inputText>
                    <h:outputLabel id="lblLimitIndicatorForPrepayment" styleClass="label" value="Limit Indicator For Prepayment"/>
                    <h:selectOneListbox id="ddLimitIndicatorForPrepayment" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.limitIndicatorForPrepayment}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.ddlimitIndicatorForPrepayment}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="repRow2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblYearIndicatorForPrepaymentLimit" styleClass="label" value="Year Indicator For Prepaymen tLimit"/>
                    <h:selectOneListbox id="ddYearIndicatorForPrepaymentLimit" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.yearIndicatorForPrepaymentLimit}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.ddyearIndicatorForPrepaymentLimit}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPrepaymentNotAcceptedBeforeMonths" styleClass="label" value="Prepayment Not Accepted Before Months/Days"/>
                    <h:panelGroup id="groupPanelPrepaymentNotAcceptedBeforeMonths" layout="block">
                        <h:inputText id="txtPrepaymentNotAcceptedBeforeMonths" styleClass="input" style="width:60px" value="#{lpd.prepaymentNotAcceptedBeforeMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel id="lblPrepaymentNotAcceptedBeforeDays" styleClass="label" value="/"/>
                        <h:inputText id="txtPrepaymentNotAcceptedBeforeDays" styleClass="input" style="width:60px" value="#{lpd.prepaymentNotAcceptedBeforeDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel id="lblPayOffIntToBeCollectedTill" styleClass="label" value="Pay Off Int To Be Collected Till"/>
                    <h:selectOneListbox id="ddApplyPrepaymentCharges" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.applyPrepaymentChargs1}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.ddapplyPrepaymentChargs}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Notice Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="repRow3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblNoticeReqdForPrepayment" styleClass="label" value="Notice Reqd For Prepayment"/>
                    <h:selectOneListbox id="ddNoticeReqdForPrepayment" styleClass="ddlist" required="true" style="width:120px" value="#{lpd.noticeReqdForPrepayment}" size="1" disabled="#{lpd.lpdFlag }">
                        <f:selectItems value="#{lpd.lpdDdtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMinNoticePeriodMonths" styleClass="label" value="Min Notice Period Months/Days"/>
                    <h:panelGroup id="groupPanelMinNoticePeriodMonths" layout="block">
                        <h:inputText id="txtMinNoticePeriodMonths" styleClass="input" style="width:60px" value="#{lpd.minNoticePeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel id="lblMinNoticePeriodDay" styleClass="label" value="/"/>
                        <h:inputText id="txtMinNoticePeriodDay" styleClass="input" style="width:60px" value="#{lpd.minNoticePeriodDay}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel id="lblValidityOfTheNoticePeriodMonths" styleClass="label" value="Validity Of The Notice Period Months/Days"/>
                    <h:panelGroup id="groupPanelValidityOfTheNoticePeriodMonths" layout="block">
                        <h:inputText id="txtValidityOfTheNoticePeriodMonths" styleClass="input" style="width:60px" value="#{lpd.validityOfTheNoticePeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                        <h:outputLabel id="lblValidityOfTheNoticePeriodDays" styleClass="label" value="/"/>
                        <h:inputText id="txtValidityOfTheNoticePeriodDays" styleClass="input" style="width:60px" value="#{lpd.validityOfTheNoticePeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                            <a4j:support event="onblur" ajaxSingle="true" />
                        </h:inputText>
                    </h:panelGroup>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Flow Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="repRow4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblEIflowId" styleClass="label" value="EI Flow Id"/>
                    <h:inputText id="txtEIflowId" styleClass="input" style="120px" value="#{lpd.eIflowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support action="#{lpd.descEIflowId}" event="onblur"
                                     reRender="stxtEIflowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtEIflowId" styleClass="label"  style="color:green;" value="#{lpd.stxtEIflowId}"/>
                    <h:outputLabel id="lblPrincipalFlowId" styleClass="label" value="Principal Flow Id"/>
                    <h:inputText id="txtPrincipalFlowId" styleClass="input" style="120px" value="#{lpd.principalFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">                        
                        <a4j:support action="#{lpd.descPrincipalFlowId}" event="onblur"
                                     reRender="stxtPrincipalFlowId,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtPrincipalFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtPrincipalFlowId}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="repRow5" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblDisbursementFlowId" styleClass="label" value="Disbursement Flow Id"/>
                    <h:inputText id="txtDisbursementFlowId" styleClass="input" style="120px" value="#{lpd.disbursementFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support action="#{lpd.descDisbursementFlowId}" event="onblur"
                                     reRender="stxtDisbursementFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtDisbursementFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtDisbursementFlowId}"/>
                    <h:outputLabel id="lblCollectionFlowId" styleClass="label" value="Collection Flow Id"/>
                    <h:inputText id="txtCollectionFlowId" styleClass="input" style="120px" value="#{lpd.collectionFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support action="#{lpd.descCollectionFlowId}" event="onblur"
                                     reRender="stxtCollectionFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtCollectionFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtCollectionFlowId}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="repRow6" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntDemandFlowId" styleClass="label" value="Int Demand Flow Id"/>
                    <h:inputText id="txtIntDemandFlowId" styleClass="input" style="120px" value="#{lpd.intDemandFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">
                        <a4j:support action="#{lpd.descIntDemandFlowId}" event="onblur"
                                     reRender="stxtIntDemandFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtIntDemandFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtIntDemandFlowId}"/>
                    <h:outputLabel id="lblPenalIntDemandFlowId" styleClass="label" value="Penal Int Demand Flow Id"/>
                    <h:inputText id="txtPenalIntDemandFlowId" styleClass="input" style="120px" value="#{lpd.penalIntDemandFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">                       
                        <a4j:support action="#{lpd.descPenalIntDemandFlowId}" event="onblur"
                                     reRender="stxtPenalIntDemandFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtPenalIntDemandFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtPenalIntDemandFlowId}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="repRow7" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblOverdueIntDemandFlowId" styleClass="label" value="Overdue Int Demand Flow Id"/>
                    <h:inputText id="txtOverdueIntDemandFlowId" styleClass="input" style="120px" value="#{lpd.overdueIntDemandFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">             
                        <a4j:support action="#{lpd.descOverdueIntDemandFlowId}" event="onblur"
                                     reRender="stxtOverdueIntDemandFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtOverdueIntDemandFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtOverdueIntDemandFlowId}"/>
                    <h:outputLabel id="lblPastDueCollectionFlowId" styleClass="label" value="Past Due Collection Flow Id"/>
                    <h:inputText id="txtPastDueCollectionFlowId" styleClass="input" style="120px" value="#{lpd.pastDueCollectionFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">                                                                                        
                        <a4j:support action="#{lpd.descPastDueCollectionFlowId}" event="onblur"
                                     reRender="stxtPastDueCollectionFlowId,lblMsg"/>

                    </h:inputText>
                    <h:outputText id="stxtPastDueCollectionFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtPastDueCollectionFlowId}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3" columns="6" id="repRow8" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblChargeDemandFlowId" styleClass="label" value="ChargeDemandFlowId"/>
                    <h:inputText id="txtChargeDemandFlowId" styleClass="input" style="120px" value="#{lpd.chargeDemandFlowId}" maxlength="5" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lpd.lpdFlag }">                        
                        <a4j:support action="#{lpd.descChargeDemandFlowId}" event="onblur"
                                     reRender="stxtChargeDemandFlowId,lblMsg"/>
                    </h:inputText>
                    <h:outputText id="stxtChargeDemandFlowId" styleClass="label"  style="color:green;" value="#{lpd.stxtChargeDemandFlowId}"/>
                    <h:outputText id="stxtChargeDemandFlowId1" styleClass="label"  style="color:green;" />
                    <h:outputText id="stxtChargeDemandFlowId2" styleClass="label"  style="color:green;"/>                  
                    <h:outputLabel />
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
