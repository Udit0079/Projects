<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="tedcw">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TOD1" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblBeginAmount" styleClass="label" value="Begin Amount"/>
            <h:inputText id="txtBeginAmount" styleClass="input" style="width:120px" maxlength="13" value="#{tedcw.bgAmt}" disabled="#{tedcw.bgAmtDisable}">
                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            <h:outputLabel id="lblEndAmount" styleClass="label" value="End Amount"/>
            <h:inputText id="txtEndAmount" styleClass="input" style="width:120px"  maxlength="13" value="#{tedcw.endAmt}" disabled="#{tedcw.endAmtDisable}">
                <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="TOD2" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblTODException" styleClass="label" value="TOD Exception"/>
            <h:panelGroup id = "panelTODException">
                <h:inputText id="txtTODException" styleClass="input" style="width:120px" maxlength="3" disabled="#{tedcw.exceptionDisable}" onkeyup="this.value = this.value.toUpperCase();" value="#{tedcw.todException}">
                    <a4j:support action="#{tedcw.setTodExceptionFun}" event="onblur" reRender="stxtTODException,lblMsg" />
                </h:inputText>
                <h:outputText id="stxtTODException" styleClass="input" style="120px" value="#{tedcw.todExceptionDesc}"/>
            </h:panelGroup>
            <h:outputLabel id="lblDlFlag" styleClass="label" value="Delete Flag" />
            <h:selectOneListbox id="ddDlFlag" styleClass="ddlist" style="width:120px" size="1" value="#{tedcw.deleteTod}" disabled="#{tedcw.deleteTodDisable}">
                <f:selectItems value="#{tedcw.deleteTedcwList}"/>
               <a4j:support action="#{tedcw.setValueInTodTable}" event="onblur" reRender="gridPanel12,lblMsg,TOD1,TOD2,TODCurrencyCode" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel12" width="100%" styleClass="row2">
             <a4j:region>
                <rich:dataTable value="#{tedcw.schemeTod}" rowClasses="row1, row2" id = "TODExceptionTable" rows="6" columnsWidth="100"
                                rowKeyVar="row" var ="itemTod" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="7"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Currency Code" /></rich:column>
                            <rich:column><h:outputText value="Begin Amount" /></rich:column>
                            <rich:column><h:outputText value="End Amount" /></rich:column>
                            <rich:column><h:outputText value="TOD Exception" /></rich:column>
                            <rich:column><h:outputText value="TOD Exception Desc" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value ="#{itemTod.currencyCode}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTod.bgAmt}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTod.endAmt}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTod.todException}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTod.todExceptionDesc}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTod.deleteTod}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{tedcw.selectSchemeTod}"  reRender="gridPanel12,lblMsg,TOD1,TOD2,TODCurrencyCode">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemTod}" target="#{tedcw.currentItemTod}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{tedcw.currentRowTod}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="TODExceptionTable" maxPages="20"/>
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>