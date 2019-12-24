<%-- 
    Document   : RdInterestCalculation
    Created on : 13 Nov, 2010, 2:56:04 PM
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
            <title>Rd Interest Calculation</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{RdInterestCalculation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Rd Interest Calculation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{RdInterestCalculation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{RdInterestCalculation.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblAccountNo" styleClass="label" value="Account No" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id = "pnlAcctno">
                                <h:selectOneListbox id="ddAccountNo" styleClass="ddlist" size="1" value="#{RdInterestCalculation.acctType}">
                                    <f:selectItems value="#{RdInterestCalculation.acctTypeList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtAccountNo" maxlength="6" styleClass="input" value="#{RdInterestCalculation.acno}" style="width:80px;">
                                    <a4j:support  action="#{RdInterestCalculation.getAccontDetails}" event="onblur" focus="ddIntCalOption"
                                                  reRender="lblMsg,Row5,leftPanel"/>
                                </h:inputText>
                                <h:inputText id="txtAccountCode" value = "01" styleClass="input" style="120px;width:30px;"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblAccountNo1" styleClass="label"/>
                            <h:outputLabel id="lblAccountNo2" styleClass="label"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                            <h:outputText id="stxtName" styleClass="label" style="120px;" value="#{RdInterestCalculation.name}"/>
                            <h:outputLabel id="lblInstallment" styleClass="label" value="Installment "/>
                            <h:outputText id="stxtInstallment" styleClass="label" style="120px;" value="#{RdInterestCalculation.installment}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblBalance" styleClass="label" value="Balance"/>
                            <h:outputText id="stxtBalance"styleClass="label" style="120px;" value="#{RdInterestCalculation.balance}"/>
                            <h:outputLabel id="lblPeriod" styleClass="label" value="Period (month)"/>
                            <h:outputText id="txtPeriod" styleClass="label" style="120px;" value="#{RdInterestCalculation.period}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblMatDate" styleClass="label" value="MatDate"/>
                            <h:outputText id="stxtMatDate"styleClass="label" style="120px;" value="#{RdInterestCalculation.matDate}"/>
                            <h:outputLabel id="lblIntCalOption" styleClass="label" value="Interest Calculation Option"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntCalOption" styleClass="ddlist" size="1" value="#{RdInterestCalculation.intOption}" disabled="#{RdInterestCalculation.optionDisable}">
                                <f:selectItems value="#{RdInterestCalculation.intOptionList}"/>
                                <a4j:support  action="#{RdInterestCalculation.changeOnOption}" event="onblur" focus="txtContractedROI"
                                              reRender="leftPanel"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblContractedROI" styleClass="label" value="Contracted ROI" ><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtContractedROI" styleClass="input" style="120px;" readonly="true"  value="#{RdInterestCalculation.contractedRoi}"/>
                            <h:outputLabel id="lblFromDate" styleClass="label" value="From Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" readonly="true" id="calFromDate" style="width:122px" value="#{RdInterestCalculation.fromDate}">
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblApplicableRate" styleClass="label" value="Applicable Rate"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtApplicableRate" styleClass="input"  style="120px;" value="#{RdInterestCalculation.applicableRate}">
                            </h:inputText>
                            <h:outputLabel id="lblToDate" styleClass="label" value="To Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calToDate" readonly="true" style="width:122px" value="#{RdInterestCalculation.toDate}" >
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPenaltyApplicate" styleClass="label" value="Penalty Applicate %"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPenaltyApplicate" styleClass="input" style="120px;" value="#{RdInterestCalculation.penaltyApplicate}">
                                <a4j:support  action="#{RdInterestCalculation.setApplicableRate}" event="onblur"
                                              reRender="txtNetContractedRoi"/>
                            </h:inputText>
                            <h:outputLabel id="lblPenaltyAmt" styleClass="label" value="Penalty Amt."/>
                            <h:inputText id="txtPenaltyAmt" styleClass="input" style="120px;" value="#{RdInterestCalculation.penaltyAmt}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row9" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblNetContractedRoi" styleClass="label" value="Net Contracted Roi"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtNetContractedRoi" styleClass="input" readonly="true" style="120px;" value="#{RdInterestCalculation.netContractedRoi}"/>
                            <h:outputLabel id="lblProvision" styleClass="label" value="Provision"/>
                            <h:outputLabel id="stxtProvision" styleClass="label" style="120px;" value="#{RdInterestCalculation.provision}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="Row10" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblInterestAmtBefore" styleClass="label" value="Interest Amt(Rs) Before Penalty "/>
                            <h:outputText id="stxtInterestAmtBefore" styleClass="label" style="120px;" value="#{RdInterestCalculation.interestAmtBeforePenalty}"/>
                            <h:outputLabel id="lblInterestAmtAfter" styleClass="label" value="Interest Amt(Rs) After Penalty  "/>
                            <h:outputText id="stxtInterestAmtAfter" styleClass="label" style="120px;" value="#{RdInterestCalculation.interestAmtAfterPenalty}"/>
                        </h:panelGrid>

                        <h:panelGrid id="FooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="btnPanel">
                                <a4j:commandButton id="btnCalculate" value="Calculate" >
                                    <a4j:support  action="#{RdInterestCalculation.btnCalculation}" event="onclick"
                                                  reRender="lblMsg,stxtInterestAmtBefore,stxtInterestAmtAfter,btnPost"/>
                                </a4j:commandButton>
                                <a4j:commandButton id="btnPost" value="Post" disabled="#{RdInterestCalculation.postDisable}"onclick="#{rich:component('postPanel')}.show()">

                                </a4j:commandButton>
                                <a4j:commandButton id="btnRefresh" value="Refresh">
                                    <a4j:support   action="#{RdInterestCalculation.refreshButton}" event="onclick"
                                                   reRender="Row1,Row2,Row3,Row4,Row5,Row6,Row7,Row8,Row9,Row10"/>
                                </a4j:commandButton>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{RdInterestCalculation.exitBtnAction}">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="msgPanel" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputText id="stxtInstruction1" styleClass="output" value="F4- Installment Detail " style="color:blue;"/>
                    </h:panelGrid>
                </h:panelGrid>
                <a4j:region id="keyRegion">
                    <a4j:jsFunction name="showInstDeatil" oncomplete="#{rich:component('instPanel')}.show();" reRender="RDTable,scrollPoOutstand"
                                    action="#{RdInterestCalculation.getInstallmentDetail}"/>
                </a4j:region>

            </a4j:form>
            <rich:modalPanel id="instPanel" autosized="true" width="450">
                <f:facet name="header">
                    <h:outputText value="Installment Detail" />
                </f:facet>
                <h:form>
                    <h:panelGrid columns="1" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                        <rich:dataTable value ="#{RdInterestCalculation.rdTable}"
                                        rowClasses="row1, row2" id = "RDTable" rows="10" rowKeyVar="row" var ="item"
                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Sr No."/> </rich:column>
                                    <rich:column><h:outputText value="Due Date"/> </rich:column>
                                    <rich:column><h:outputText value="Installment Amt"/></rich:column>
                                    <rich:column><h:outputText value="Status"/></rich:column>
                                    <rich:column><h:outputText value="Payment Date"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{item.srNo}"/></rich:column>
                            <rich:column><h:outputText value="#{item.dueDate}"/></rich:column>
                            <rich:column>
                                <h:outputText value="#{item.installAmt}">
                                    <f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/>
                                </h:outputText>
                            </rich:column>
                            <rich:column><h:outputText value="#{item.status}"/></rich:column>
                            <rich:column><h:outputText value="#{item.paymentDate}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller id="scrollPoOutstand" align="left" for="RDTable" maxPages="20" />
                    </h:panelGrid>
                    <h:panelGrid id="popUpFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="popUpBtnPanel">
                            <a4j:commandButton id="btnClose" value="Close" onclick="#{rich:component('instPanel')}.hide();"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>
            <rich:modalPanel id="postPanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" />
                </f:facet>
                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Post Data ?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{RdInterestCalculation.btnPost}"
                                                       onclick="#{rich:component('postPanel')}.hide();" reRender="lblMsg,btnPost"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton id="No" value="Cancel"  ajaxSingle="true" onclick="#{rich:component('postPanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <rich:hotKey id="F4Key"key="F4" handler="showInstDeatil();"/>
        </body>
    </body>
</html>
</f:view>
