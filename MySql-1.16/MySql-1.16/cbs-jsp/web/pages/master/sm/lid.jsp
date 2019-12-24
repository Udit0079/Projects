<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lid">
     <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="SchemeLoanInterestDetailsleftPanel11" style="width:100%;text-align:center;">
            <rich:panel header="Interest Details" style="text-align:left;">
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntOnPrincipal" styleClass="label" value="Int On Principal"/>
                    <h:selectOneListbox id="ddIntOnPrincipal" styleClass="ddlist" required="true" style="width:120px" value="#{lid.intOnPrincipal}" size="1" disabled="#{lid.flagDisable}">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPenalIntOnPrincipalDemandOverdue" styleClass="label" value="Penal Int On Principal Demand Overdue"/>
                    <h:selectOneListbox id="ddPenalIntOnPrincipalDemandOverdue" styleClass="ddlist" required="true" style="width:120px" value="#{lid.penalIntOnPrincipalDemandOverdue}" size="1" disabled="#{lid.flagDisable}">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPrincipalDemandOverdueAtEndOfMonths" styleClass="label" value="Principal Demand Over due At End Of Months"/>
                    <h:selectOneListbox id="ddPrincipalDemandOverdueAtEndOfMonths" styleClass="ddlist" required="true" style="width:120px" value="#{lid.principalDemandOverdueAtEndOfMonths}" size="1" disabled="#{lid.flagDisable}">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblPrincipalOverduePeriodMonths" styleClass="label" value="Principal Overdue Period Months/Days"/>
                    <h:panelGroup id="groupPanelPrincipalOverduePeriodMonths" layout="block">
                        <h:inputText id="txtPrincipalOverduePeriodMonths" styleClass="input" style="width:55px" value="#{lid.principalOverduePeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }"/>
                        <h:outputLabel id="lblPrincipalOverduePeriodDays" styleClass="label" value="/"/>
                        <h:inputText id="txtPrincipalOverduePeriodDays" styleClass="input" style="width:55px" value="#{lid.principalOverduePeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }"/>
                    </h:panelGroup>
                    <h:outputLabel id="lblIntOnIntDemand" styleClass="label" value="Int On Int Demand"/>
                    <h:selectOneListbox id="ddIntOnIntDemand" styleClass="ddlist" required="true" style="width:120px" value="#{lid.intOnIntDemand}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPenalIntOnIntDemandOverdue" styleClass="label" value="Penal Int On Int Demand Overdue"/>
                    <h:selectOneListbox id="ddPenalIntOnIntDemandOverdue" styleClass="ddlist" required="true" style="width:120px" value="#{lid.penalIntOnIntDemandOverdue}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblIntDemandOverdueAtEndOfMonth" styleClass="label" value="Int Demand Overdue At End Of Month"/>
                    <h:selectOneListbox id="ddIntDemandOverdueAtEndOfMonth" styleClass="ddlist" required="true" style="width:100px" value="#{lid.intDemandOverdueAtEndOfMonth}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntOverduePeriodMonths" styleClass="label" value="IntOverduePeriodMonths/Days"/>
                    <h:panelGroup id="groupPanelIntOverduePeriodMonths" layout="block">
                        <h:inputText id="txtIntOverduePeriodMonths" styleClass="input" style="width:50px" value="#{lid.intOverduePeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                        <h:outputLabel id="lblIntOverduePeriodDays" styleClass="label" value="/"/>
                        <h:inputText id="txtIntOverduePeriodDays" styleClass="input" style="width:50px" value="#{lid.intOverduePeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                    </h:panelGroup>
                    <h:outputLabel id="lblOverdueIntOnPrincipal" styleClass="label" value="Overdue Int On Principal"/>
                    <h:selectOneListbox id="ddOverdueIntOnPrincipal" styleClass="ddlist" required="true" style="width:120px" value="#{lid.overdueIntOnPrincipal}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt3" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblApplyPreferentialForOverdueInt" styleClass="label" value="Apply Preferential For Overdue Int"/>
                    <h:selectOneListbox id="ddApplyPreferentialForOverdueInt" styleClass="ddlist" required="true" style="width:120px" value="#{lid.applyPreferentialForOverdueInt}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntRateBasedOnLoanAmount" styleClass="label" value="Int Rate Based On Loan Amount"/>
                    <h:selectOneListbox id="ddIntRateBasedOnLoanAmount" styleClass="ddlist" required="true" style="width:120px" value="#{lid.intRateBasedOnLoanAmount}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblApplyLateFeeForDelayedPayment" styleClass="label" value="Apply Late Fee For Delayed Payment"/>
                    <h:selectOneListbox id="ddApplyLateFeeForDelayedPayment" styleClass="ddlist" required="true" style="width:120px" value="#{lid.applyLateFeeForDelayedPayment}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt4" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblGracePeriodForLateFeeMonths" styleClass="label" value="Grace Period For Late Fee Months/Days"/>
                    <h:panelGroup id="groupPanelGracePeriodForLateFeeMonths" layout="block">
                        <h:inputText id="txtGracePeriodForLateFeeMonths" styleClass="input" style="width:58px" value="#{lid.gracePeriodForLateFeeMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                        <h:outputLabel id="lblGracePeriodForLateFeeDays" styleClass="label" value="/"/>
                        <h:inputText id="txtGracePeriodForLateFeeDays" styleClass="input" style="width:58px" value="#{lid.gracePeriodForLateFeeDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                            <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                    </h:panelGroup>
                    <h:outputLabel id="lblToleranceLimitForDpdCycleAmount" styleClass="label" value="Tolerance Limit For Dpd Cycle Amount"/>
                    <h:inputText id="txtToleranceLimitForDpdCycleAmount" styleClass="input" style="width:120px" value="#{lid.toleranceLimitForDpdCycleAmount}" maxlength="13" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                        <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                    <h:outputLabel id="lblToleranceLimitForDpdCycleType" styleClass="label" value="Tolerance Limit For Dpd Cycle Type"/>
                    <h:inputText id="txtToleranceLimitForDpdCycleType" styleClass="input" style="width:120px" value="#{lid.toleranceLimitForDpdCycleType}" maxlength="1" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                        <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt5" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblConsiderToleranceForLateFee" styleClass="label" value="Consider Tolerance For Late Fee"/>
                    <h:selectOneListbox id="ddConsiderToleranceForLateFee" styleClass="ddlist" required="true" style="width:120px" value="#{lid.considerToleranceForLateFee}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblCreateIntDemandFromRepSchedule" styleClass="label" value="Create Int Demand From Rep Schedule"/>
                    <h:selectOneListbox id="ddCreateIntDemandFromRepSchedule" styleClass="ddlist" required="true" style="width:120px" value="#{lid.createIntDemandFromRepSchedule}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblRephasementCarryOverdueDemands" styleClass="label" value="Rephasement Carry Overdue Demands"/>
                    <h:selectOneListbox id="ddRephasementCarryOverdueDemands" styleClass="ddlist" required="true" style="width:120px" value="#{lid.rephasementCarryOverdueDemands}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt6" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblPriorityLoan" styleClass="label" value="Priority Loan"/>
                    <h:selectOneListbox id="ddPriorityLoan" styleClass="ddlist" required="true" style="width:120px" value="#{lid.priorityLoan}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAgriLoan" styleClass="label" value="Agri Loan"/>
                    <h:selectOneListbox id="ddAgriLoan" styleClass="ddlist" required="true" style="width:120px" value="#{lid.agriLoan}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblIntLimit" styleClass="label" value="Int Limit"/>
                    <h:inputText id="txtIntLimit" styleClass="input" style="width:120px" value="#{lid.intLimit}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                       <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                  
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt7" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCoveredByDicge" styleClass="label" value="Covered By DICGC"/>
                    <h:selectOneListbox id="ddCoveredByDicge" styleClass="ddlist" required="true" style="width:120px" value="#{lid.coveredByDicge}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblDICGCFeeFlowId" styleClass="label" value="DICGC Fee Flow Id"/>
                    <h:selectOneListbox id="ddDICGCFeeFlowId" styleClass="ddlist" required="true" style="width:120px" value="#{lid.dICGCFeeFlowId}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblDICGCFeeAccountPlaceholder" styleClass="label" value="DICGC Fee Account Placeholder"/>
                    <h:inputText id="txtDICGCFeeAccountPlaceholder" styleClass="input" style="width:120px" value="#{lid.dICGCFeeAccountPlaceholder}" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                        <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                    
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt8" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblHirerDetails" styleClass="label" value="Hirer Details"/>
                    <h:selectOneListbox id="ddHirerDetails" styleClass="ddlist" required="true" style="width:120px" value="#{lid.hirerDetails}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAodOrAosType" styleClass="label" value="Aod Or Aos Type"/>
                    <h:selectOneListbox id="ddAodOrAosType" styleClass="ddlist" required="true" style="width:120px" value="#{lid.aodOrAosType}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddAodOrAosType}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblSubsidyAvailable" styleClass="label" value="Subsidy Available"/>
                    <h:selectOneListbox id="ddSubsidyAvailable" styleClass="ddlist" required="true" style="width:120px" value="#{lid.subsidyAvailable}" size="1" disabled="#{lid.flagDisable }">
                        <f:selectItems value="#{lid.ddtrnRefNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="LoanInt9" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblRefinanceScheme" styleClass="label" value="Refinance Scheme"/>
                    <h:inputText id="txtRefinanceScheme" styleClass="input" style="width:120px" value="#{lid.refinanceScheme}" maxlength="1" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lid.flagDisable }">
                        <a4j:support event="onblur" ajaxSingle="true" />   
                        </h:inputText>  
                    
                    <h:outputText id="stxtRefinanceScheme1" styleClass="label"  style="color:green;" />
                    <h:outputText id="stxtRefinanceScheme2" styleClass="label"  style="color:green;"/>
                    <h:outputText id="stxtRefinanceScheme3" styleClass="label"  style="color:green;" />
                    <h:outputText id="stxtRefinanceScheme4" styleClass="label"  style="color:green;" />
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
