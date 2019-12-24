<%-- 
    Document   : EMI Calculater
    Created on : 9 Apr, 2011, 10:57:19 AM
    Author     : root
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>EMI Calculater</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="outerPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{EMICalculater.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="EMI Calculater"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EMICalculater.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{EMICalculater.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPeriodicity" styleClass="label" value="Periodicity"/>
                            <h:selectOneListbox id="ddPeriodicity"  styleClass="ddlist" size="1" value="#{EMICalculater.periodicity}" style="width:90px">
                                <f:selectItems value="#{EMICalculater.periodList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblEmiStartingDate" styleClass="label" value = "EMI Starting Date"/>
                            <rich:calendar datePattern="dd/MM/yyyy"  id="calFromDate"  value="#{EMICalculater.emiStartingDate}" inputSize="10">
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPrinciple" styleClass="label" value = "Principle"/>
                            <h:inputText id = "txtPrinciple" styleClass="input" value="#{EMICalculater.principal}" style="width:80px"/>
                            <h:outputLabel id="lblRateOfInterest" styleClass="label" value="RateOfInterest" />
                            <h:inputText id = "txtRateOfInterest" styleClass="input" maxlength="5" value="#{EMICalculater.roi}" style="width:60px"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblNoOfInst" styleClass="label" value = "No Of Installment"/>
                            <h:inputText id = "txtNoOfInst" styleClass="input" value="#{EMICalculater.noOfInstallment}" maxlength="10" style="width:60px"/>
                            <h:outputLabel id="lblInterestOpt" styleClass="label" value="Interest Option"/>
                            <h:selectOneListbox id="ddInterestOpt"  styleClass="ddlist" size="1" value="#{EMICalculater.interestOption}" style="width:90px">
                                <f:selectItems value="#{EMICalculater.interestOptionList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                        <a4j:region>
                            <rich:dataTable value ="#{EMICalculater.amortSch}"
                                            rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="10"></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                        <rich:column><h:outputText value="Due Date." /></rich:column>
                                        <rich:column><h:outputText value="Opening Principal" /></rich:column>
                                        <rich:column><h:outputText value="Principle Amount" /></rich:column>
                                        <rich:column><h:outputText value="Interest Amount" /></rich:column>
                                        <rich:column><h:outputText value="Installment" /></rich:column>
                                        <rich:column><h:outputText value="Closing Principal" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column style="text-align:right;"><h:outputText value ="#{item.sno}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText  value ="#{item.dueDate}"/></rich:column>
                                <rich:column style="text-align:right;"><h:outputText  value ="#{item.openingPrinciple}"></h:outputText></rich:column>
                                <rich:column style="text-align:right;"><h:outputText  value ="#{item.principleAmt}"></h:outputText></rich:column>
                                <rich:column style="text-align:right;"><h:outputText value ="#{item.interestAmt}"></h:outputText></rich:column>
                                <rich:column style="text-align:right;"><h:outputText  value ="#{item.installment}"></h:outputText></rich:column>
                                <rich:column style="text-align:right;"><h:outputText  value ="#{item.closingPrinciple}"></h:outputText></rich:column>
                                <f:facet name="footer">
                                    <rich:columnGroup style="background-color: #b0c4de;">

                                        <rich:column>Totals</rich:column>
                                        <rich:column></rich:column>
                                        <rich:column></rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{EMICalculater.totalPrincipal}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{EMICalculater.totalInterest}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column style="text-align:right;">
                                            <h:outputText value="#{EMICalculater.totalInstallment}"><f:convertNumber   pattern="####.00"  /></h:outputText>
                                        </rich:column>
                                        <rich:column></rich:column>                                        
                                    </rich:columnGroup>
                                </f:facet>
                            </rich:dataTable>
                        </a4j:region>
                        <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="poFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnCalculate" value="Calculate" action="#{EMICalculater.calculateActionRepayment}" reRender="msgRow,InterestCal">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{EMICalculater.refresh}" reRender="msgRow,Row2,Row3,Row4,InterestCal">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{EMICalculater.exitBtnAction}" reRender="msgRow,Row2,Row3,Row4,InterestCal">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <rich:messages></rich:messages> 
            </a4j:form>
        </body>
    </html>
</f:view>
