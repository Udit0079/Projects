<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="didd">
        <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="deposit1" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblInterestMethod" styleClass="label" value="Interest Method"/>
                <h:inputText id="txtInterestMethod" styleClass="input" style="width:120px" value="#{didd.interestMethod}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.disableInterest}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblMaxDepositPeriodMonths" styleClass="label" value="Max Deposit Period Months"/>
                <h:inputText id="txtMaxDepositPeriodMonths" styleClass="input" style="width:120px" value="#{didd.maxDepositPeriodMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblMaxDepositPeriodDays" styleClass="label" value="Max Deposit Period Days"/>
                <h:inputText id="txtMaxDepositPeriodDays" styleClass="input" style="width:120px" value="#{didd.maxDepositPeriodDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="deposit2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblBaseAmountInd" styleClass="label" value="Base Amount Ind"/>
                <h:selectOneListbox id="ddBaseAmountInd" styleClass="ddlist"  size="1" style="width:120px" value="#{didd.baseAmtInd}"  disabled="#{didd.diddFlag }">
                    <f:selectItems value="#{didd.ddBaseAmtInd}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCompoundingPeriod" styleClass="label" value="Compounding Period"/>
                <h:inputText id="txtCompoundingPeriod" styleClass="input" style="width:120px" value="#{didd.compoundingPeriod}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblCompoundingBase" styleClass="label" value="Compounding Base"/>
                <h:inputText id="txtCompoundingBase" styleClass="input" style="width:120px" value="#{didd.compoundingBase}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="deposit3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblMinCompoundingPeriod" styleClass="label" value="Min Compounding Period"/>
                <h:inputText id="txtMinCompoundingPeriod" styleClass="input" style="width:120px" value="#{didd.minCompoundingPeriod}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblMinCompoundingBase" styleClass="label" value="Min Compounding Base"/>
                <h:inputText id="txtMinCompoundingBase" styleClass="input" style="width:120px" value="#{didd.minCompoundingBase}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblBrokenPeriodMthd" styleClass="label" value="Broken Period Mthd"/>
                <h:selectOneListbox id="ddBrokenPeriodMthd" styleClass="ddlist"  size="1" style="width:120px" value="#{didd.brokenPeriodMethod}"  disabled="#{didd.diddFlag }">
                    <f:selectItems value="#{didd.ddBrokenPeriodMthd}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="deposit4" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblBrokenPeriodBase" styleClass="label" value="Broken Period Base"/>
                <h:inputText id="txtBrokenPeriodBase" styleClass="input" style="width:120px" value="#{didd.brokenPeriodBase}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{didd.diddFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDeleteFlag" styleClass="label" value="Delete Flag"/>
                <h:selectOneListbox id="ddDeleteFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{didd.deleteFlag2}"  disabled="#{didd.diddFlag }">
                    <f:selectItems value="#{didd.ddDiddTrnRefNo}"/>
                    <a4j:support action="#{didd.setDepositInterestTable}" event="onblur" reRender="schemePopUpForm,tableInt,lblMsg,txtInterestMethod"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblbc" styleClass="label" />
                <h:outputLabel id="lblg" styleClass="label" />
            </h:panelGrid>
            <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel1" width="100%" styleClass="row2">
                <a4j:region>
                    <rich:dataTable  value="#{didd.deposit}" rowClasses="row1, row2" id = "tableInt" rows="4" columnsWidth="100" 
                                     rowKeyVar="row" var ="itemDeposit" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="19"></rich:column>
                                <rich:column breakBefore="true"><h:outputText value="Interest Method" /> </rich:column>
                                <rich:column><h:outputText value="Max Deposit Period Months" /></rich:column>
                                <rich:column><h:outputText value="Max Deposit Period Days" /></rich:column>
                                <rich:column><h:outputText value="Base Amount Ind" /></rich:column>
                                <rich:column><h:outputText value="Compounding Period" /></rich:column>
                                <rich:column><h:outputText value="Compounding Base" /></rich:column>
                                <rich:column><h:outputText value="Min Compounding Period" /></rich:column>
                                <rich:column><h:outputText value="Min Compounding Base" /></rich:column>
                                <rich:column><h:outputText value="BrokenPeriodMthd" /></rich:column>
                                <rich:column><h:outputText value="Broken Period Base" /></rich:column>
                                <rich:column><h:outputText value="Delete Flag" /></rich:column>
                                <rich:column><h:outputText value="Edit" /></rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column><h:outputText value="#{itemDeposit.tblInterestMethod}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblMaxDepositPeriodMonths}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblMaxDepositPeriodDays}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblBaseAmtInd}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblCompoundingPeriod}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblCompoundingBase}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblMinCompoundingPeriod}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblMinCompoundingBase}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblBrokenPeriodMethod}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblBrokenPeriodBase}"/></rich:column>
                        <rich:column><h:outputText value="#{itemDeposit.tblDeleteFlag}"/></rich:column>
                        <rich:column>
                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{didd.selectDepositInterest}" reRender="schemePopUpForm,lblMsg,tableInt">
                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                <f:setPropertyActionListener value="#{itemDeposit}" target="#{didd.currentItem3}"/>
                                <f:setPropertyActionListener value="#{row}" target="#{didd.currentRow3}" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller align="left" for="tableInt" maxPages="20" />
                </a4j:region>
            </h:panelGrid>
        </h:panelGrid>
</h:form>
