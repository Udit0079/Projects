<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lfdcw">
    <h:panelGrid id="schemePopUpForm" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel2" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="DetailsRow2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblStartAmount" styleClass="label" value="Start Amount"/>
                <h:inputText id="txtStartAmount" styleClass="input" style="width:120px" maxlength="13" value="#{lfdcw.startAmount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lfdcw.disableStartAmount}">
                <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                <h:outputLabel id="lblEndAmount2" styleClass="label" value="End Amount"/>
                <h:inputText id="txtEndAmount2" styleClass="input" style="width:120px" maxlength="13"  value="#{lfdcw.endAmount}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lfdcw.disableendAmount}">
                    <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="DetailsRow3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblFreeFolios" styleClass="label" value="Free Folios"/>
                <h:inputText id="txtFreeFolios" styleClass="input" style="width:120px"  value="#{lfdcw.freeFolios}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{lfdcw.lfdcwFlag }">
                    <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                <h:outputLabel id="lblDelFlag" styleClass="label" value="DelFlag"/>
                <h:selectOneListbox id="ddDelFlag" styleClass="ddlist" required="true" style="width:120px" value="#{lfdcw.delFlagledger}" size="1" disabled="#{lfdcw.lfdcwFlag }">
                    <f:selectItems value="#{lfdcw.ddLfdcwTrnRefNo}"/>
                    <a4j:support action="#{lfdcw.setTableLedgerFolio}" event="onblur" reRender="leftPanel2,gridPanel103,txtEndAmount2,txtStartAmount,txtFreeFolios,ddDelFlag,lblMsg" />
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                <a4j:region>
                    <rich:dataTable value="#{lfdcw.ledger}" var="itemLedger" rowClasses="row1, row2" id="taskList" rows="3" columnsWidth="100" rowKeyVar="row"
                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                        <f:facet name="header">
                            <rich:columnGroup>
                                <rich:column colspan="5"></rich:column>
                                <rich:column breakBefore="true"><h:outputText value="Start Amount" /> </rich:column>
                                <rich:column><h:outputText value="End Amount" /></rich:column>
                                <rich:column><h:outputText value="Free Folios" /></rich:column>
                                <rich:column><h:outputText value="DelFlag" /></rich:column>
                                <rich:column><h:outputText value="Edit" /></rich:column>
                            </rich:columnGroup>
                        </f:facet>
                        <rich:column><h:outputText value="#{itemLedger.tblStartAmount}"/></rich:column>
                        <rich:column><h:outputText value="#{itemLedger.tblEndAmount}"/></rich:column>
                        <rich:column><h:outputText value="#{itemLedger.tblFreeFolios}"/></rich:column>
                        <rich:column><h:outputText value="#{itemLedger.tblDelFlagledger}"/></rich:column>
                        <rich:column>
                            <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{lfdcw.selectLedgerFolio}" reRender="leftPanel2,lblMsg,taskList">
                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                <f:setPropertyActionListener value="#{itemLedger}" target="#{lfdcw.currentItem2}"/>
                                <f:setPropertyActionListener value="#{row}" target="#{lfdcw.currentRow2}" />
                            </a4j:commandLink>
                        </rich:column>
                    </rich:dataTable>
                    <rich:datascroller id="scroller" align="left" for="taskList" maxPages="20" />
                </a4j:region>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
