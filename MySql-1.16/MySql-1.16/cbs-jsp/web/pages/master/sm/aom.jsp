<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="aom">
        <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="matRow2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblMatrixDesc" styleClass="label" value="Matrix Description"/>
                <h:inputText id="txtMatrixDesc" styleClass="input" style="width:120px" value="#{aom.matrixDesc}" maxlength="80" onkeyup="this.value = this.value.toUpperCase();" disabled="#{aom.aomFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblValidInvalidFlag" styleClass="label" value="Valid/Invalid Flag"/>
                <h:selectOneListbox id="ddValidInvalidFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.validInvalidFlag}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddValidInvalidFlag}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="matRow3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblCustStatus" styleClass="label" value="Cust Status"/>
                <h:selectOneListbox id="ddCustStatus" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custStatus}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddCustStatus}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCustSector" styleClass="label" value="Cust Sector"/>
                <h:selectOneListbox id="ddCustSector" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custSector}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddCustSector}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="matRow4" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCustSubSector" styleClass="label" value="Cust Sub Sector"/>
                <h:selectOneListbox id="ddCustSubSector" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custSubSector}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddCustSubSector}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCustTypeCode" styleClass="label" value="Cust Type Code  "/>
                <h:selectOneListbox id="ddCustTypeCode" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custTypeCode}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddCustTypeCode}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow5" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblCustConstitution" styleClass="label" value="Cust Constitution"/>
                <h:selectOneListbox id="ddCustConstitution" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custConstitution}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddCustConstitution}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCustEmpId" styleClass="label" value="Cust EmpId"/>
                <h:inputText id="txtCustEmpId" styleClass="input" style="width:120px" value="#{aom.custEmpId}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();" disabled="#{aom.aomFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow6" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCustOtherBank" styleClass="label" value="Cust Other Bank"/>
                <h:inputText id="txtCustOtherBank" styleClass="input"  style="width:120px" value="#{aom.custOtherBank}" maxlength="6" onkeyup="this.value = this.value.toUpperCase();" disabled="#{aom.aomFlag }">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblModeOfOperation" styleClass="label" value="Mode Of Operation"/>
                <h:selectOneListbox id="ddModeOfOperation" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.modeOfOperation}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddModeOfOperation}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow10" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblGuaranteeCover" styleClass="label" value="Guarantee Cover"/>
                <h:selectOneListbox id="ddGuaranteeCover" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.guaranteeCover}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddGuaranteeCover}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblNatureOfAdvance" styleClass="label" value="Nature Of Advance"/>
                <h:selectOneListbox id="ddNatureOfAdvance" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.natureOfAdvance}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddNatureOfAdvance}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow8" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblTypeOfAdvance" styleClass="label" value="Type Of Advance"/>
                <h:selectOneListbox id="ddTypeOfAdvance" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.typeOfAdvance}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddTypeOfAdvance}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblModeOfAdvance" styleClass="label" value="Mode Of Advance"/>
                <h:selectOneListbox id="ddModeOfAdvance" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.modeOfAdvance}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddModeOfAdvance}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow9" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblPurposeOfAdvance" styleClass="label" value="Purpose Of Advance"/>
                <h:selectOneListbox id="ddPurposeOfAdvance" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.purposeOfAdvance}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddPurposeOfAdvance}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblCustMinorFlag" styleClass="label" value="Cust Minor Flag"/>
                <h:selectOneListbox id="ddCustMinorFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.custMinorFlag}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddAomTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow11" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblChequedAllowedFlag" styleClass="label" value="Chequed Allowed Flag"/>
                <h:selectOneListbox id="ddChequedAllowedFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.chequedAllowedFlag}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddAomTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblAccountTurnDetail" styleClass="label" value="Account Turn Detail"/>
                <h:selectOneListbox id="ddAccountTurnDetail" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.accountTurnDetail}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddAomTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="5" id="matRow12" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblAccountOwnership" styleClass="label" value="Account Owner ship"/>
                <h:inputText id="txtAccountOwnership" styleClass="input" style="width:120px"  value="#{aom.accountOwnership}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{aom.aomFlag }" maxlength="1">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDelFlagmat" styleClass="label" value="Del Flag"/>
                <h:selectOneListbox id="ddDelFlagmat" styleClass="ddlist"  size="1" style="width:120px" value="#{aom.delFlagmat}"  disabled="#{aom.aomFlag }">
                    <f:selectItems value="#{aom.ddAomTrnRefNo}"/>
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:selectOneListbox>
            </h:panelGrid>
        </h:panelGrid>
</h:form>
