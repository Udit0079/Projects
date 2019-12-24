<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="trccw">
    <h:panelGrid id="schemePopUpForm" style="width:100%;text-align:center;">
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Transaction1" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblTransactionReportCode" styleClass="label" value="Transaction Report Code"/>
            <h:selectOneListbox id="ddTransactionReportCode" styleClass="ddlist" style="width:120px"  size="1" value="#{trccw.transactionReportCode}" disabled="#{trccw.tranReportCodeDisable}">
                <f:selectItems value="#{trccw.transactionRepCodeList}"/>
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:selectOneListbox>
            <h:outputLabel id="lblDrAmtLimit" styleClass="label" value="Dr Amt Limit"/>
            <h:inputText id="txtDrAmtLimit" styleClass="input" style="width:120px" maxlength="13" value="#{trccw.drAmt}" disabled="#{trccw.disableFlagTranCode}">
                <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
        </h:panelGrid>
        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="12" id="Transaction2" style="width:100%;text-align:left;" styleClass="row1">
            <h:outputLabel id="lblCrAmtLimit" styleClass="label" value="Cr Amt Limit"/>
            <h:inputText id="txtCrAmtLimit" styleClass="input" style="width:120px" maxlength="13" value="#{trccw.crAmt}" disabled="#{trccw.disableFlagTranCode}">
            <a4j:support event="onblur" ajaxSingle="true" />
            </h:inputText>
            <h:outputLabel id="lblDeleFlag" styleClass="label" value="Delete Flag"/>
            <h:selectOneListbox id="ddDeleFlag" styleClass="ddlist" style="width:120px" size="1" value="#{trccw.deleteTranCode}" disabled="#{trccw.disableFlagTranCode}">
                <f:selectItems value="#{trccw.deleteTrccwList}"/>
                <a4j:support action="#{trccw.setValueInTranRepCurrWiseTable}" event="onblur" reRender="gridPanel112,lblMsg,Transaction1,Transaction2 " />
            </h:selectOneListbox>
        </h:panelGrid>
        <h:panelGrid columnClasses="col1,col2" columns="1" id="gridPanel112" width="100%" styleClass="row2">
            <a4j:region>
                <rich:dataTable value="#{trccw.tranRepCodeWise}" rowClasses="row1, row2" id = "TransactionTable" rows="6"
                                columnsWidth="100" rowKeyVar="row" var ="itemTran" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="6"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="Transaction Report Code" /></rich:column>
                            <rich:column><h:outputText value="Dr Amt Limit" /></rich:column>
                            <rich:column><h:outputText value="Cr Amt Limit" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value ="#{itemTran.transactionReportCode}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTran.drAmt}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTran.crAmt}"/></rich:column>
                    <rich:column><h:outputText value ="#{itemTran.deleteTranCode}"/></rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action="#{trccw.selectTranRepCurrWise}"  reRender="ddTransactionReportCode,gridPanel112,lblMsg,Transaction1,Transaction2 ">
                            <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{itemTran}" target="#{trccw.currentItemTran}"/>
                            <f:setPropertyActionListener value="#{row}" target="#{trccw.currentRowTranCurr}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="TransactionTable" maxPages="20" />
            </a4j:region>
        </h:panelGrid>
    </h:panelGrid>
</h:form>
