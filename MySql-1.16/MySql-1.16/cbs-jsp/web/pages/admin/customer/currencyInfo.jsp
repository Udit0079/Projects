<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="currencyInfo">
     <a4j:region>
    <h:panelGrid id="CurrInfoPanel" columns="2" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel4" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="currRow1" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblCurrCd" styleClass="label" value="Currency Code"/>
                <h:selectOneListbox  value="#{CurrencyInfo.currencyCodeForCurrInformation}"id="ddCurrCd" style="width:72px;"styleClass="ddlist"size="1">
                    <f:selectItems value="#{CurrencyInfo.currencyCodeForCurrInfoOption}"/>
                </h:selectOneListbox>
                <h:outputLabel id="lblHolTaxlvl" styleClass="label" value="Withholding Tax Level"/>
                <h:inputText  value="#{CurrencyInfo.withoutHoldTaxLevel}"id="txtHolTaxlvl" maxlength="10"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="currRow2" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblHolTaxPer" styleClass="label" value="Withholding Tax%"/>
                <h:inputText  value="#{CurrencyInfo.withoutHoldTax100}"id="txtHolTaxPer" styleClass="input" style="120px">
                    <a4j:support actionListener="#{CurrencyInfo.onblurWithholdingTaxPer}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblHolTaxLmt" styleClass="label" value="Withholding Tax Limit"/>
                <h:inputText  value="#{CurrencyInfo.withoutHoldTaxLim}"id="txtHolTaxLmt" styleClass="input" style="120px">
                    <a4j:support event="onblur" actionListener="#{CurrencyInfo.onblurWithholdTaxLimit}" reRender="stxtError"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="currRow3" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblprefIntDt" styleClass="label" value="Preferential Interest Till Date"/>
                <rich:calendar  value="#{CurrencyInfo.prefIntTillDate}"datePattern="dd/MM/yyyy" id="prefIntDt" jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" focus="txtCustIntCr"/>
                </rich:calendar>
                <h:outputLabel id="lblCustIntCr" styleClass="label" value="TDS Operative Account Debit"/>
                <h:inputText  value="#{CurrencyInfo.tdsOperativeAccIdEdit}"id="txtCustIntCr"maxlength="12" styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="currRow4" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblCustIntCr1" styleClass="label" value="Customer Interest Rate Credit"/>
                <h:inputText  value="#{CurrencyInfo.custIntRateCredit}"id="txtCustIntCr1" styleClass="input" style="120px">
                </h:inputText>
                <h:outputLabel id="lblCustIntDr" styleClass="label" value="Customer Interest Rate Debit"/>
                <h:inputText  value="#{CurrencyInfo.custIntrateDebit}"id="txtCustIntDr" styleClass="input" style="120px">
                    <a4j:support  event="onblur" reRender="taskList,stxtError,ddCurrCd,txtHolTaxlvl,txtHolTaxPer,txtHolTaxLmt,prefIntDt,txtCustIntCr,txtCustIntCr1,txtCustIntDr,currScroller" actionListener="#{CurrencyInfo.loadCurrencyGrid}"/>
                </h:inputText>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:200px;">
        <rich:contextMenu attached="false" id="menu" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
            <rich:menuItem value="Select Record" ajaxSingle="true" actionListener="#{CurrencyInfo.fetchCurrentRow}">
                <a4j:actionparam name="currCode" value="{currencyCode}" />
                <a4j:actionparam name="row" value="{currentRowCurr}" />
            </rich:menuItem>
        </rich:contextMenu>
        <rich:dataTable    value="#{CurrencyInfo.listCurrInfoTab}"var="dataItem" rowClasses="gridrow1, gridrow2" id="taskList" columnsWidth="100"
                           rowKeyVar="row" rows="5" onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                           onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%" >
            <f:facet name="header">
                <rich:columnGroup>
                    <rich:column colspan="9"><h:outputText value="Currency Detail" /></rich:column>
                    <rich:column breakBefore="true"><h:outputText value="Currency Code" /></rich:column>
                    <rich:column><h:outputText value="Tax level"/></rich:column>
                    <rich:column><h:outputText value="Tax %" /> </rich:column>
                    <rich:column><h:outputText value="Tax Limit" /></rich:column>
                    <rich:column><h:outputText value="Preferential Int Date" /></rich:column>
                    <rich:column><h:outputText value="TDS Operative A/C" /></rich:column>
                    <rich:column><h:outputText value="Interest Rate Credit" /></rich:column>
                    <rich:column><h:outputText value="Interest Rate Debit" /></rich:column>
                    <rich:column><h:outputText value="Select Record" /></rich:column>
                </rich:columnGroup>
            </f:facet>
            <rich:column><h:outputText value="#{dataItem.currencyCode}"/></rich:column>
            <rich:column><h:outputText  value="#{dataItem.withHoldingTaxLevel}" /></rich:column>
            <rich:column><h:outputText value="#{dataItem.withHoldTaxPer}" /></rich:column>
            <rich:column><h:outputText  value="#{dataItem.withHoldTaxLim}" /></rich:column>
            <rich:column ><h:outputText  value="#{dataItem.preferInterestTillDate}" /></rich:column>
            <rich:column ><h:outputText  value="#{dataItem.tdsOperativeAccIdEdit}" /></rich:column>
            <rich:column ><h:outputText  value="#{dataItem.custIntRateCredit}" /></rich:column>
            <rich:column ><h:outputText  value="#{dataItem.custIntRateDebit}" /></rich:column>
            <rich:column style="text-align:center;width:40px">
                <a4j:commandLink  ajaxSingle="true" id="selectlink" oncomplete="#{rich:component('showPanel1')}.show();"
                                  >
                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                    <f:setPropertyActionListener value="#{dataItem}" target="#{CurrencyInfo.currInfoTab}"/>
                    <f:setPropertyActionListener value="#{row}" target="#{CurrencyInfo.currentRowCurr}"/>
                </a4j:commandLink>
                <%--<rich:toolTip for="selectlink" value="Select This Row"/>--%>
            </rich:column>
        </rich:dataTable>
        <rich:datascroller id="currScroller"align="left" for="taskList" maxPages="20" />
        <rich:modalPanel id="showPanel1" autosized="true" width="250" onshow="#{rich:element('btnSelectCurr')}.focus();">
            <f:facet name="header">
                <h:outputText value="Do you want to Select or Delete this Row." style="padding-right:15px;" />
            </f:facet>
            <f:facet name="controls">
                <h:panelGroup>
                    <h:graphicImage  value="images/close.png" styleClass="hidelink" id="hidelink1" />
                    <rich:componentControl for="showPanel1" attachTo="hidelink1" operation="hide" event="onclick" />
                </h:panelGroup>
            </f:facet>
            <h:form>
                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <tbody>
                        <tr style="height:40px">
                            <td align="center" width="50%" colspan="2">
                                <a4j:commandButton value="SELECT" id="btnSelectCurr" ajaxSingle="true" action="#{CurrencyInfo.selectCurrency}"reRender="taskList,ddCurrCd,txtHolTaxlvl,txtHolTaxPer,txtHolTaxLmt,prefIntDt,txtCustIntCr,txtCustIntCr1,txtCustIntDr"onclick="#{rich:component('showPanel1')}.hide();" oncomplete="#{rich:element('ddCurrCd')}.focus();"/>
                            </td>
                            <td align="center" width="50%">
                                <a4j:commandButton value="DELETE" ajaxSingle="true" action="#{CurrencyInfo.deleteGridCurr}"reRender="taskList"onclick="#{rich:component('showPanel1')}.hide();" />
                            </td>
                        </tr>
                    </tbody>
                </table>
            </h:form>
        </rich:modalPanel>
    </h:panelGrid>
          </a4j:region>
</h:form>
