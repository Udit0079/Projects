<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="lrcd">
    <h:panelGrid columns="3" id="schemePopUpForm" style="width:100%;">
        <h:panelGrid id="leftPanel1aw" columns="1" width="100%" style="height:30px" >
            <h:panelGrid columnClasses="" columns="10" id="PanelRepayment1" style="width:100%;" styleClass="row2">
                <h:outputLabel id="lblAccountOpenFromDate" styleClass="label"  value="A/C Open From Date"/>
                <h:inputText id="acOpenFromDate" style="width:50px;"  value="#{lrcd.acOpenFromDate}" maxlength="2" styleClass="input">
                <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                <h:outputLabel id="lblAccountOpenToDate" styleClass="label"  value="A/C Open To Date "/>
                <h:inputText id="acOpenToDate" style="width:50px;" value="#{lrcd.acOpenToDate}" maxlength="2" styleClass="input">
                    <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                <h:outputLabel id="lblRepaymentStatDate" styleClass="label"  value="Repayment Start Date"/>
                <h:inputText id="repaymentStartDate" style="width:50px;" value="#{lrcd.repaymentStartDate}" maxlength="2" styleClass="input">
                <a4j:support event="onblur" ajaxSingle="true" />
                </h:inputText>
                <h:outputLabel id="lblMonthIndicator" styleClass="label" value="Current/Next Month Indicator"/>
                <h:selectOneListbox id="monthIndicator"  size="1" style="width:50px" styleClass="ddlist" value="#{lrcd.monthIndicator}" >
                    <f:selectItems value="#{lrcd.comboMonthIndicator}" />
                    <a4j:support event="onblur" ajaxSingle="true" />
                </h:selectOneListbox >
                <h:outputLabel id="lblDelFlagaw" styleClass="label" value="Delete Flag"/>
                <h:selectOneListbox id="delFlag"  size="1" style="width:50" styleClass="ddlist" value="#{lrcd.delFlagAw}" >
                    <f:selectItems  value="#{lrcd.comboDelFlag}"/>
                    <a4j:support action="#{lrcd.setValuesInLoanRepayment}" event="onblur" reRender="RightPanel,PanelRepayment1,issueregister,lblMsg" />
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col7" columns="1" id="RightPanel" width="100%" styleClass="row2" style="height:136px;">
                <rich:dataTable value="#{lrcd.loanRepay}" var="dataItem" rowClasses="row1, row2" id="issueregister" rows="6" columnsWidth="20" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'" onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column colspan="7"></rich:column>
                            <rich:column breakBefore="true"><h:outputText value="A/C Open From Date" /></rich:column>
                            <rich:column><h:outputText value="A/C Open To Date" /></rich:column>
                            <rich:column><h:outputText value="Repayment Start Date" /></rich:column>
                            <rich:column><h:outputText value="Month Indicator" /></rich:column>
                            <rich:column><h:outputText value="Delete Flag" /></rich:column>
                            <rich:column><h:outputText value="Select" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column>
                        <h:outputText value="#{dataItem.acOpenFromDate}"  />
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{dataItem.acOpenToDate}"  />
                    </rich:column>
                    <rich:column>
                        <h:outputText value="#{dataItem.repaymentStartDate}"  />
                    </rich:column>
                    <rich:column >
                        <h:outputText value="#{dataItem.monthIndicator}"  />
                    </rich:column>
                    <rich:column >
                        <h:outputText value="#{dataItem.delFlag}"  />
                    </rich:column>
                    <rich:column>
                        <a4j:commandLink ajaxSingle="true" id="selectlink" action ="#{lrcd.selectLoanRepayment}"  reRender="lblMsg,schemePopUpForm,RightPanel">
                            <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                            <f:setPropertyActionListener value="#{row}" target="#{lrcd.currentRowLoanRepayment}"/>
                            <f:setPropertyActionListener value="#{dataItem}" target="#{lrcd.currentItemLoanRepayment}" />
                        </a4j:commandLink>
                    </rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="issueregister" maxPages="20" />
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
</h:form>

