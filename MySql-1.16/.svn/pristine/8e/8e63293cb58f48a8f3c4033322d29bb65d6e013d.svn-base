<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="sfcd">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="feeCharges1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblChargesType" styleClass="label" value="Charges Type"/>
            <h:selectOneListbox id="ddChargesType" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.chargesType}" disabled="#{sfcd.chargesTypedisable}">
                <f:selectItems value="#{sfcd.chargesTypeList}"/>
                <a4j:support action="#{sfcd.setValueCharDesc}" event="onblur" ajaxSingle="true"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblChargesEventId" styleClass="label" value="Charges Event Id"/>
            <h:selectOneListbox id="ddChargesEventId" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.chargesEventId}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.chargesEventIdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblChargesEventId1" styleClass="label" />
            <h:outputLabel id="lblChargesEventId2" styleClass="label" />
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="feeCharges2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblAmortMethod" styleClass="label" value="Amort Method"/>
            <h:inputText id="txtAmortMethod" styleClass="input" maxlength="3" style="width:120px" onkeyup="this.value = this.value.toUpperCase();" value="#{sfcd.amortMethod}" disabled="#{sfcd.disableFlagFeeCharges}">
            <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
            <h:outputLabel id="lblAmortize" styleClass="label" value="Amortize"/>
            <h:selectOneListbox id="ddAmortize" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.amortize}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.deleteSfcdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDrPlaceHolder" styleClass="label" value="Dr. Place Holder"/>
            <h:inputText id="txtDrPlaceHolder" styleClass="input" style="width:120px" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" value="#{sfcd.drPlaceHolder}" disabled="#{sfcd.disableFlagFeeCharges}">
            <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="feeCharges3" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblCrPlaceHolder" styleClass="label" value="Cr. Place Holder"/>
            <h:inputText id="txtCrPlaceHolder" styleClass="input" style="width:120px" maxlength="12" onkeyup="this.value = this.value.toUpperCase();" value="#{sfcd.crPlaceHolder}" disabled="#{sfcd.disableFlagFeeCharges}">
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
            <h:outputLabel id="lblAssertOrDmd" styleClass="label" value="Assert Or Dmd"/>
            <h:selectOneListbox id="ddAssertOrDmd" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.assertOrDmd}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.assertOrDmdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDeductible" styleClass="label" value="Deductible"/>
            <h:selectOneListbox id="ddDeductible" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.deductible}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.deleteSfcdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="feeCharges4" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblConsiderForIrr" styleClass="label" value="Consider For IRR"/>
            <h:selectOneListbox id="ddConsiderForIrr" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.considerForIrr}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.deleteSfcdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblMultipleFlag" styleClass="label" value="Multiple Flag"/>
            <h:selectOneListbox id="ddMultipleFlag" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.multipleFlag}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.deleteSfcdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDeleteFlagFeeCharges" styleClass="label" value="Delete Flag"/>
            <h:selectOneListbox id="ddDeleteFlagFeeCharges" styleClass="ddlist" style="width:120px" size="1" value="#{sfcd.delFlagFeeCharges}" disabled="#{sfcd.disableFlagFeeCharges}">
                <f:selectItems value="#{sfcd.deleteSfcdList}"/>
                <a4j:support action="#{sfcd.setValueInFeeChargesTable}" event="onblur" reRender="lblMsg,feeChargesGrid,feeCharges1,feeCharges2,feeCharges3,feeCharges4" ajaxSingle="true"/>
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="feeChargesGrid" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable value ="#{sfcd.feeCharges}" rowClasses="row1, row2" id = "feeChargesTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemFeeCharges"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="19"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Charges Type" /> </rich:column>
                            <rich:column><h:outputText value="Amortize" /></rich:column>
                            <rich:column><h:outputText value="Charges Description"/></rich:column>
                            <rich:column><h:outputText value="Amort Method"/></rich:column>
                            <rich:column><h:outputText value="Charges Event Id" /></rich:column>
                            <rich:column><h:outputText value="Dr. Place Holder" /></rich:column>
                            <rich:column><h:outputText value="Cr. Place Holder" /></rich:column>
                            <rich:column><h:outputText value="Assert Or Dmd" /></rich:column>
                            <rich:column><h:outputText value="Deductible" /></rich:column>
                            <rich:column><h:outputText value="Consider For IRR" /></rich:column>
                            <rich:column><h:outputText value="Multiple Flag" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value ="#{itemFeeCharges.chargesType}"/></rich:column>
                    <rich:column><h:outputText  value ="#{itemFeeCharges.amortize}"/></rich:column>
                    <rich:column><h:outputText  value ="#{itemFeeCharges.chargesDesc}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.amortMethod}"/></rich:column>
                    <rich:column ><h:outputText  value ="#{itemFeeCharges.chargesEventId}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.drPlaceHolder}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.crPlaceHolder}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.assertOrDmd}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.deductible}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.considerForIrr}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.multipleFlag}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemFeeCharges.delFlagFeeCharges}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{sfcd.selectFeeCharges}"  reRender="lblMsg,feeChargesGrid,feeCharges1,feeCharges2,feeCharges3,feeCharges4">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemFeeCharges}" target="#{sfcd.currentItemFeeCharges}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{sfcd.currentRowFeeCharges}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="feeChargesTable" maxPages="20" />
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
