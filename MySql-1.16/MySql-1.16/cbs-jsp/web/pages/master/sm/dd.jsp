<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="dd">
    <h:panelGrid columns="3" id="schemePopUpForm" style="width:100%;">
        <h:panelGrid id="leftPanelDeliquencyDetails" columns="1" width="100%" style="height:30px" >
            <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="PanelDeliquency" style="width:100%;" styleClass="row2">
                <h:outputLabel id="lblDeliqCycle" styleClass="label" value="Deliquency Cycle "><font class="required" color="red">*</font></h:outputLabel>
                <h:selectOneListbox id="deliqCycle" size="1" style="width:100px" styleClass="ddlist" value="#{dd.deliqCycle}" disabled="#{dd.dpFlag}" >
                    <f:selectItems value="#{dd.referenceTypeList}"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblDaysPastDue" styleClass="label"  value="Days Past Due"><font class="required" color="red">*</font></h:outputLabel>
                <h:inputText id="daysPastDue" style="width:100px;" value="#{dd.daysPastDue}" disabled="#{dd.ddFlag}"maxlength="3" styleClass="input"/>
                <h:outputLabel id="lblPlaceHolder" styleClass="label"  value="Place Holder"><font class="required" color="red">*</font></h:outputLabel>
                <h:inputText id="txtPlaceHolder" styleClass="input" value="#{dd.placeholder}" size="11" maxlength="8"/>
            </h:panelGrid>
            <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col1" id="delId" style="width:100%;" styleClass="row1">
                <h:outputLabel id="lblProvPercent" styleClass="label"  value="Prov. Percent(In %)"><font class="required" color="red">*</font></h:outputLabel>
                <h:inputText id="txtProvPercent" styleClass="input" value="#{dd.provisionPercent}" size="13" maxlength="5"/>
                <h:outputLabel id="lblDelFlagDeliq" styleClass="label" value="Delete ddFlag "><font class="required" color="red">*</font></h:outputLabel>
                <h:selectOneListbox id="delFlagDeliq" size="1" style="width:100px" styleClass="ddlist" value="#{dd.delFlagDeliq}" disabled="#{dd.ddFlag}">
                    <f:selectItems value="#{dd.comboDelFlagDeliq}" />
                    <a4j:support action="#{dd.setValueInDeliquencyDetailsTable}" event="onblur" reRender="RightPanelDeliq,PanelDeliquency,DeliquencyTab,lblMsg,txtPlaceHolder,txtProvPercent"/>
                </h:selectOneListbox>
                <h:outputText/>
                <h:outputText/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col7" columns="1" id="RightPanelDeliq" width="100%" styleClass="row2" style="height:136px;">
                <rich:dataTable value="#{dd.deliqDetails}" var="dataItem" rowClasses="row1, row2" id="DeliquencyTab" rows="6" columnsWidth="20" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="7"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Deliquency Cycle" /></rich:column>
                            <rich:column><h:outputText value="Days Past Due" /></rich:column>
                            <rich:column><h:outputText value="Place Holder" /></rich:column>
                            <rich:column><h:outputText value="Provision Percent" /></rich:column>
                            <rich:column><h:outputText value="Delete ddFlag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value="#{dataItem.deliqCycle}"/></rich:column>
                    <rich:column ><h:outputText value="#{dataItem.daysPastDue}"/></rich:column>
                    <rich:column ><h:outputText value="#{dataItem.placeInHolder}"/></rich:column>
                    <rich:column ><h:outputText value="#{dataItem.provisionInPercent}"/></rich:column>
                    <rich:column ><h:outputText value="#{dataItem.delFlagDeliq}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action ="#{dd.selectDeliquencyDetails}"  reRender="lblMsg,schemePopUpForm,RightPanelDeliq">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{row}" target="#{dd.currentRowDeliqDetails}"/>
                            <f:setPropertyActionListener value="#{dataItem}" target="#{dd.currentItemDeliqDetails}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="DeliquencyTab" maxPages="20" />
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
