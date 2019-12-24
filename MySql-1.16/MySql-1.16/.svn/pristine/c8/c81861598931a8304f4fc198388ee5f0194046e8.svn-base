<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="ffdd">
    <h:panelGrid columns="3" id="schemePopUpForm" style="width:100%;">
        <h:panelGrid id="leftPanelaw" columns="1" width="100%" style="height:30px" >
            <h:panelGrid columnClasses="col1,col2,col1,col2,col1,col2" columns="6" id="Panel0" style="width:100%;" styleClass="row2">
                <h:outputLabel id="lblAutomaticallyCreateDeposits" styleClass="label" value="AutomaticallyCreateDeposits "/>
                <h:selectOneListbox id="automaticallyCreateDeposits"   size="1" style="width:110px" styleClass="ddlist" value="#{ffdd.automaticallyCreateDeposits}" disabled="#{ffdd.ffDDFlag}">
                    <f:selectItems value="#{ffdd.comboAutomaticallyCreateDeposits}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAutoCrPerdMonths" styleClass="label" value="Auto Cr Period Months"/>
                <h:inputText id="autoCrPerdMonths" style="width:100px;" value="#{ffdd.autoCrPerdMonths}" disabled="#{ffdd.ffDDFlag}" maxlength="3" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
                <h:outputLabel id="lblAutoCrPerdDays" styleClass="label" value="Auto Cr Period Days"/>
                <h:inputText id="autoCrPerdDays" style="width:100px;" value="#{ffdd.autoCrPerdDays}" disabled="#{ffdd.ffDDFlag}" maxlength="3" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col1,col2" columns="2" id="Panel1" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblAutoFrequency" styleClass="label"  value="Auto Cr Freq Type/WeekNo/WeekDays/Start Date/NP"/>
                <h:panelGroup id="AutoFrequency" layout="block">
                    <h:selectOneListbox id="autoCrFreqType"   size="1" style="width:90px" styleClass="ddlist" value="#{ffdd.autoCrFreqType}" disabled="#{ffdd.ffDDFlag}">
                        <f:selectItems value="#{ffdd.comboAutoCrFreqType}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAutoCrFreqWeekNo" styleClass="label" value="/"/>
                    <h:selectOneListbox id="autoCrFreqWeekNo"  size="1" style="width:100px" styleClass="ddlist" value="#{ffdd.autoCrFreqWeekNo}" disabled="#{ffdd.ffDDFlag}">
                        <f:selectItems value="#{ffdd.comboAutoCrFreqWeekNo}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAutoCrFreqWeekDay" styleClass="label" value="/" />
                    <h:selectOneListbox id="autoCrFreqWeekDay"  size="1" style="width:50px" styleClass="ddlist" value="#{ffdd.autoCrFreqWeekDay}" disabled="#{ffdd.ffDDFlag}">
                        <f:selectItems value="#{ffdd.comboAutoCrFreqWeekDay}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAutoCrFreqStartDate" styleClass="headerLabel" value="/"/>
                    <h:inputText id="autoCrFreqStartDate" style="width:40px;" value="#{ffdd.autoCrFreqStartDate}" disabled="#{ffdd.ffDDFlag}" maxlength="2" styleClass="input" >
                        <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
                    <h:outputLabel id="lblAutoCrFreqNP" styleClass="label" value="/"/>
                    <h:selectOneListbox id="autoCrFreqNP"  size="1" style="width:80px" styleClass="ddlist" value="#{ffdd.autoCrFreqNP}" disabled="#{ffdd.ffDDFlag}">
                        <f:selectItems value="#{ffdd.comboAutoCrFreqNP}"/>
                        <a4j:support event="onblur" ajaxSingle="true" />   
                    </h:selectOneListbox>
                </h:panelGroup>
                
            </h:panelGrid>
            <h:panelGrid columnClasses="col1,col2,col1,col2,col1,col2" columns="6" id="Panel3" style="width:100%;" styleClass="row2">
                <h:outputLabel id="lblCreateDepositIfOperativeAccountMoreThan" styleClass="label" value="Create Deposit If Operative Account More Than"/>
                <h:inputText id="createDepositIfOperativeAccountMoreThan" style="width:100px;" value="#{ffdd.createDepositIfOperativeAccountMoreThan}" disabled="#{ffdd.ffDDFlag}" maxlength="13" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
                <h:outputLabel id="lblCreateDepositsInStepsOf" styleClass="label"  value="Create Deposits In Steps Of"/>
                <h:inputText id="createDepositsInStepsOf" style="width:100px;" value="#{ffdd.createDepositsInStepsOf}" disabled="#{ffdd.ffDDFlag}" maxlength="13" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
                <h:outputLabel id="lblLinkToOperativeAccount" styleClass="label"  value="Link To Operative Account"/>
                <h:selectOneListbox id="linkToOperativeAccount"  size="1" style="width:110px" styleClass="ddlist" value="#{ffdd.linkToOperativeAccount}" disabled="#{ffdd.ffDDFlag}">
                    <f:selectItems value="#{ffdd.comboLinkToOperativeAccount}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col1,col2,col1,col2,col1,col2" columns="6" id="Panel4" style="width:100%;" styleClass="row1">
                <h:outputLabel id="lblBreakDepositInStepsOf" styleClass="label"  value="Break Deposit In Steps Of"/>
                <h:inputText id="breakDepositInStepsOf" style="width:100px;" value="#{ffdd.breakDepositInStepsOf}" disabled="#{ffdd.ffDDFlag}" maxlength="13" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:inputText>
                <h:outputLabel id="lblForeClosureInterestMethod" styleClass="label" value="Fore Closure Interest Method"/>
                <h:selectOneListbox id="foreClosureInterestMethod"  size="1" style="width:110px" styleClass="ddlist" value="#{ffdd.foreClosureInterestMethod}" disabled="#{ffdd.ffDDFlag}">
                    <f:selectItems value="#{ffdd.comboForeClosureInterestMethod}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
                <h:outputLabel id="lblAddPreferentialtoPenaltyRate" styleClass="label" value="Add Preferential to Penalty Rate"/>
                <h:selectOneListbox id="addPreferentialtoPenaltyRate"  size="1" style="width:110px" styleClass="ddlist" value="#{ffdd.addPreferentialtoPenaltyRate}" disabled="#{ffdd.ffDDFlag}">
                    <f:selectItems value="#{ffdd.comboAddPreferentialtoPenaltyRate}"/>
                    <a4j:support event="onblur" ajaxSingle="true" />   
                </h:selectOneListbox>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
