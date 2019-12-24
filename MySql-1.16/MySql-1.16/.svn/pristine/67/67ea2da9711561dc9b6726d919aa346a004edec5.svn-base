<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="dfd">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="flow1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblFlowCode" styleClass="label" value="Flow Code"/>
            <h:inputText id="txtFlowCode" styleClass="input" style="width:120px" value="#{dfd.flowCode}" maxlength="2" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dfd.disableFlowCode}">
                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            <h:outputLabel id="lblFlowFreqMonths" styleClass="label" value="Flow Freq Months"/>
            <h:inputText id="txtFlowFreqMonths" styleClass="input" style="width:120px" value="#{dfd.flowFreqMonths}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dfd.dfdFlag }">
                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            <h:outputLabel id="lblFlowFreqDays" styleClass="label" value="Flow Freq Days"/>
            <h:inputText id="txtFlowFreqDays" styleClass="input" style="width:120px" value="#{dfd.flowFreqDays}" maxlength="3" onkeyup="this.value = this.value.toUpperCase();" disabled="#{dfd.dfdFlag }">
                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="flow2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblFlowPeriodBegin" styleClass="label" value="Flow Period Begin"/>
            <h:selectOneListbox id="ddFlowPeriodBegin" styleClass="ddlist"  size="1" style="width:120px" value="#{dfd.flowPeriodBegin}"  disabled="#{dfd.dfdFlag }">
                <f:selectItems value="#{dfd.ddturnoverFreqType}"/>
                <a4j:support event="onblur" ajaxSingle="true"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblFlowPeriodEnd" styleClass="label" value="Flow Period End"/>
            <h:selectOneListbox id="ddFlowPeriodEnd" styleClass="ddlist"  size="1" style="width:120px" value="#{dfd.flowPeriodEnd}"  disabled="#{dfd.dfdFlag }">
                <f:selectItems value="#{dfd.ddturnoverFreqType}"/>
                <a4j:support event="onblur" ajaxSingle="true"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblDelFlowFlag" styleClass="label" value="Del Flag"/>
            <h:selectOneListbox id="ddDelFlowFlag" styleClass="ddlist"  size="1" style="width:120px" value="#{dfd.delFlagFlow}"  disabled="#{dfd.dfdFlag }">
                <f:selectItems value="#{dfd.ddDfdTrnRefNo}"/>
                <a4j:support action="#{dfd.setDepositFlowTable}" event="onblur" reRender="schemePopUpForm,tableflow,lblMsg"/>
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="flowPanel1" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable  value="#{dfd.depFlow}" rowClasses="row1, row2" id = "tableflow" rows="4" columnsWidth="100" rowKeyVar="row" var ="itemFlow"
                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="7"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Flow Code" /> </rich:column>
                            <rich:column><h:outputText value="Flow Freq Months" /></rich:column>
                            <rich:column><h:outputText value="Flow Freq Days" /></rich:column>
                            <rich:column><h:outputText value="Flow Period Begin" /></rich:column>
                            <rich:column><h:outputText value="Flow Period End" /></rich:column>
                            <rich:column><h:outputText value="Del Flag" /></rich:column>
                            <rich:column><h:outputText value="Edit" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value="#{itemFlow.tblFlowCode}"/></rich:column>
                    <rich:column><h:outputText value="#{itemFlow.tblFlowFreqMonths}"/></rich:column>
                    <rich:column><h:outputText value="#{itemFlow.tblFlowFreqDays}"/></rich:column>
                    <rich:column><h:outputText value="#{itemFlow.tblFlowPeriodBegin}"/></rich:column>
                    <rich:column><h:outputText value="#{itemFlow.tblFlowPeriodEnd}"/></rich:column>
                    <rich:column><h:outputText value="#{itemFlow.tblDelFlagFlow}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{dfd.selectDepositFlow}" reRender="schemePopUpForm,lblMsg,tableflow">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemFlow}" target="#{dfd.currentItem4}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{dfd.currentRow4}"/>
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="tableflow" maxPages="20"/>
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>

