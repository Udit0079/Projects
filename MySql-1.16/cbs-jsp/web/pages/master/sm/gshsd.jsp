<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="gshsd">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="GLleftPanel2" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="GLDetailsRow1" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblGLSubHeadCode" styleClass="label" value="GL Sub Head Code"/>
                <h:inputText id="txtGLSubHeadCode" styleClass="input"  style="width:120px" maxlength="12" value="#{gshsd.glSubHeadCode}"  disabled="#{gshsd.disableFlagGl}" onkeyup="this.value = this.value.toUpperCase();">
                    <a4j:support action="#{gshsd.getGlSubHeadFun}" event="onblur" reRender="txtGLSubHead,lblMsg" focus="txtGLSubHead,lblMsg"/>
                </h:inputText>
                <h:outputLabel id="lblGLSubHead" styleClass="label" value="GL Sub Head " />
                <h:inputText id="txtGLSubHead" styleClass="input" style="width:120px" value="#{gshsd.glSubHead}"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{gshsd.disableFlagGl}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="GLFlowRow2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblDfltFlag" styleClass="label" value="Default Flag"/>
                <h:inputText id="txtDfltFlag" styleClass="input" style="width:120px" maxlength="1" value="#{gshsd.defaultFlag}"  onkeyup="this.value = this.value.toUpperCase();" disabled="#{gshsd.disableFlagGl}">
                    <a4j:support event="onblur" ajaxSingle="true"/>
                </h:inputText>
                <h:outputLabel id="lblDelFlagGL" styleClass="label" value="Del Flag"/>
                <h:selectOneListbox id="ddDeleFlagGl" styleClass="ddlist"  size="1" style="width:120px"  value="#{gshsd.deleteFlag}" disabled="#{gshsd.disableFlagGl}">
                    <f:selectItems value="#{gshsd.deleteGlList}"/>
                    <a4j:support action="#{gshsd.setDataInGlTable}" event="onblur" reRender="gl,lblMsg,GLDetailsRow1,GLFlowRow2" />
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col1,col2" columns="1" id="gl" width="100%" styleClass="row2">
                <a4j:region>
                    <rich:dataTable  var="item" value ="#{gshsd.glHead}" rowClasses="row1, row2" id="GLtaskList" rows="6" columnsWidth="100" rowKeyVar="row"
                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="5"></rich:column>
                                <rich:column breakBefore="true"><h:outputText value="GL Sub Head Code" /> </rich:column>
                                <rich:column><h:outputText value="GL Sub Head" /></rich:column>
                                <rich:column><h:outputText value="Default Flag" /></rich:column>
                                <rich:column><h:outputText value="Delete Flag" /></rich:column>
                                <rich:column><h:outputText value="Select" /></rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column><h:outputText value="#{item.glSubHeadCode}"/></rich:column>
                        <rich:column><h:outputText value="#{item.glSubHead}"/></rich:column>
                        <rich:column><h:outputText value="#{item.defaultFlag}"/></rich:column>
                        <rich:column><h:outputText value="#{item.deleteFlag}"/></rich:column>
                        <rich:column>
                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{gshsd.selectGl}"  reRender="GLFlowRow2,GLDetailsRow1,gl">
                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                <f:setPropertyActionListener value="#{item}" target="#{gshsd.currentItem}"/>
                                <f:setPropertyActionListener value="#{row}" target="#{gshsd.currentRow}" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller id="scrollerGL" align="left" for="GLtaskList" maxPages="20" />
                </a4j:region>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
