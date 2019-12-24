<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="svi">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="ValidInstruments" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblInstrumentCode" styleClass="label" value="Instrument Code"/>
            <h:selectOneListbox id="ddInstrumentCode" styleClass="ddlist" style="width:120px" size="1" value="#{svi.instrumentCode}" disabled="#{svi.sviFlag}">
                <f:selectItems value="#{svi.instrumentCodeList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDrCrIndicator" styleClass="label" value="Dr/Cr Indicator"/>
            <h:selectOneListbox id="ddDrCrIndicator" styleClass="ddlist" style="width:120px" size="1" value="#{svi.crDrIndFlg}" disabled="#{svi.sviFlag}">
                <f:selectItems value="#{svi.drCrIndicatorSchemeValidList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDFlag" styleClass="label" value="Delete Flag"/>
            <h:selectOneListbox id="ddDFlag" styleClass="ddlist" style="width:120px" size="1" value="#{svi.deleteSchemeValid}" disabled="#{svi.sviFlag}">
                <f:selectItems value="#{svi.deleteSchemeValidList}"/>
                <a4j:support action="#{svi.setValueInSchemeValidTable}" event="onblur" reRender="ValidInstrumentsTable,lblMsg,ValidInstruments" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel1030" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable value ="#{svi.schemeValid}" rowClasses="row1, row2" id = "ValidInstrumentsTable" rows="6" 
                                columnsWidth="100" rowKeyVar="row" var ="ItemSchemeValid" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="4"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Instrument Code" /></rich:column>
                            <rich:column><h:outputText value="Dr/Cr Indicator" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select"/></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value="#{ItemSchemeValid.instrumentCode}"/></rich:column>
                    <rich:column><h:outputText value="#{ItemSchemeValid.crDrIndFlg}"/></rich:column>
                    <rich:column><h:outputText value="#{ItemSchemeValid.deleteSchemeValid}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{svi.selectSchemeValid}"  reRender="ValidInstruments,gridPanel1030">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{ItemSchemeValid}" target="#{svi.currentItemSchValid}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{svi.currentRowSchValid}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="ValidInstrumentsTable" maxPages="20" />
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
