<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="strd">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="TodReference1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblReferenceType" styleClass="label" value="Reference Type"/>
            <h:selectOneListbox id="ddReferenceType" styleClass="ddlist" style="width:120px" value="#{strd.referenceType}" disabled="#{strd.disableFlagReferenceType}" size="1">
                <f:selectItems value="#{strd.referenceTypeList}"/>
                  <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDiscretNumberOfDays" styleClass="label" value="Discret Number Of Days"/>
            <h:inputText id="txtDiscretNumberOfDays" styleClass="input" maxlength="3" style="width:120px" value="#{strd.discretNumberOfDays}"  disabled="#{strd.disableFlagTodRef}" onkeyup="this.value = this.value.toUpperCase();">
            <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
            <h:outputLabel id="lblPenalDays" styleClass="label" value="Penal Days"/>
            <h:inputText id="txtPenalDays" styleClass="input" maxlength="3" style="width:120px" value="#{strd.penalDays}" disabled="#{strd.disableFlagTodRef}" onkeyup="this.value = this.value.toUpperCase();">
              <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="TodReference2" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblDiscretAdvnType" styleClass="label" value="Discret Advn Type"/>
            <h:selectOneListbox id="ddDiscretAdvnType" styleClass="ddlist" style="width:120px" size="1" value="#{strd.discretAdvnType}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.discretAdvnTypeList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDiscretAdvnCategory" styleClass="label" value="Discret Advn Category"/>
            <h:selectOneListbox id="ddDiscretAdvnCategory" styleClass="ddlist"  size="1" style="width:120px" value="#{strd.discretAdvnCategory}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.discretAdvnCategoryList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblIntFlag" styleClass="label" value="Int Flag"/>
            <h:selectOneListbox id="ddIntFlag" styleClass="ddlist"  size="1" value="#{strd.intFlag}" style="width:120px" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.deleteStrdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="TodReference3" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblInterestTableCode" styleClass="label" value="Interest Table Code"/>
            <h:selectOneListbox id="ddInterestTableCode" styleClass="ddlist"  size="1" style="width:120px" value="#{strd.interestTableCode}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.interestTableCodeList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblToLevelIntTblCode" styleClass="label" value="To Level Int Tbl Code"/>
            <h:selectOneListbox id="ddToLevelIntTblCode" styleClass="ddlist"  size="1" style="width:120px" value="#{strd.toLevelIntTblCode}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.deleteStrdList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblFreeTxtCode" styleClass="label" value="Free Txt Code"/>
            <h:selectOneListbox id="ddFreeTxtCode" styleClass="ddlist"  size="1" style="width:120px" value="#{strd.freeTxtCode}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.freeTxtCodeList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="12" id="TodReference4" style="width:100%;text-align:left;" styleClass="row2">
            <h:outputLabel id="lblDelFlagTodRef" styleClass="label" value="Delete Flag"/>
            <h:selectOneListbox id="ddDelFlagTodRef" styleClass="ddlist"  size="1" style="width:120px" value="#{strd.delFlagTodRef}" disabled="#{strd.disableFlagTodRef}">
                <f:selectItems value="#{strd.deleteStrdList}"/>
                <a4j:support action="#{strd.setValueInTodRef}" event="onblur" reRender="lblMsg,TodReferenceGrid,TodReference1,TodReference2,TodReference3,TodReference4" />
            </h:selectOneListbox>
            <h:outputLabel id="lblMultipleFlag1" styleClass="label" />
            <h:outputLabel id="lblDeleteFlagFeeCharges2" styleClass="label" />
            <h:outputLabel id="lblDeleteFlagFeeCharges3" styleClass="label"/>
            <h:outputLabel id="lblDeleteFlagFeeCharges4" styleClass="label" />
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="TodReferenceGrid" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable value ="#{strd.todRef}" rowClasses="row1, row2" id = "TodReferenceTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="itemTodRef"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="19"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Reference Type" /> </rich:column>
                            <rich:column><h:outputText value="Discret Number Of Days"/></rich:column>
                            <rich:column><h:outputText value="Penal Days"/></rich:column>
                            <rich:column><h:outputText value="Discret Advn Type" /></rich:column>
                            <rich:column><h:outputText value="Discret Advn Category" /></rich:column>
                            <rich:column><h:outputText value="Int Flag" /></rich:column>
                            <rich:column><h:outputText value="Interest Table Code" /></rich:column>
                            <rich:column><h:outputText value="To Level Int Tbl Code" /></rich:column>
                            <rich:column><h:outputText value="Free Txt Code" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value ="#{itemTodRef.referenceType}"/></rich:column>
                    <rich:column><h:outputText  value ="#{itemTodRef.discretNumberOfDays}"/></rich:column>
                    <rich:column><h:outputText  value ="#{itemTodRef.penalDays}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.discretAdvnType}"/></rich:column>
                    <rich:column ><h:outputText  value ="#{itemTodRef.discretAdvnCategory}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.intFlag}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.interestTableCode}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.toLevelIntTblCode}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.freeTxtCode}"/></rich:column>
                    <rich:column ><h:outputText value ="#{itemTodRef.delFlagTodRef}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{strd.selectTodRef}"  reRender="lblMsg,TodReferenceGrid,TodReference1,TodReference2,TodReference3,TodReference4">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemTodRef}" target="#{strd.currentItemtodRef}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{strd.currentRowTodRef}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="TodReferenceTable" maxPages="20" />
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
