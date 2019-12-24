<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="csdd">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="documentDetails1" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblDocumentCode" styleClass="label" value="Document Code"/>
            <h:selectOneListbox id="ddDocumentCode" styleClass="ddlist"  size="1" value="#{csdd.documentCode}" disabled="#{csdd.disableFlagDocDetail}">
                <f:selectItems value="#{csdd.docCode}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblDocumentDescription" styleClass="label" value="Document Description"/>
            <h:inputText id="txtDocumentDescription" styleClass="input" style="width:120px" maxlength="80" value="#{csdd.documentDesc}" disabled="#{csdd.disableFlagDocDetail}" onkeyup="this.value = this.value.toUpperCase();"/>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="documentDetails2" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblMandatoryDocument" styleClass="label" value="Mandatory Document"/>
            <h:selectOneListbox id="ddMandatoryDocument" styleClass="ddlist" style="width:120px" size="1" value="#{csdd.mandatoryDoc}" disabled="#{csdd.disableFlagDocDetail}">
                <f:selectItems value="#{csdd.deleteTODList}"/>
            </h:selectOneListbox>
            <h:outputLabel id="lblDlFlagDocDetail" styleClass="label" value="Delete Flag" />
            <h:selectOneListbox id="ddDlFlagDocDetail" styleClass="ddlist" style="width:120px" size="1" value="#{csdd.delFlagDocDetail}" disabled="#{csdd.disableFlagDocDetail}">
                <f:selectItems value="#{csdd.deleteTODList}"/>
                <a4j:support action="#{csdd.setValueInDocDetailTable}" event="onblur" reRender="lblMsg,SchemeDocumentDetailsTable,documentDetails1,documentDetails2" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="SchemeDocumentDetailsTable" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable value="#{csdd.docDetail}" var ="itemDoc" rowClasses="row1, row2" id="DocumentDetailsTable" rows="6" 
                                columnsWidth="100" rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" 
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="7"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Document Code" /></rich:column>
                            <rich:column><h:outputText value="Document Description" /></rich:column>
                            <rich:column><h:outputText value="Mandatory Document" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value ="#{itemDoc.documentCode}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemDoc.documentDesc}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemDoc.mandatoryDoc}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemDoc.delFlagDocDetail}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{csdd.selectDocDetail}"  reRender="lblMsg,SchemeDocumentDetailsTable,documentDetails1,documentDetails2">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemDoc}" target="#{csdd.currentItemDoc}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{csdd.currentRowDocDetail}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller id="scroller" align="left" for="DocumentDetailsTable" maxPages="20"/>
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
