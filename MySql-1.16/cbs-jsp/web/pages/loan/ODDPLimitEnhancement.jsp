<%-- 
    Document   : ODDPLimitEnhancement
    Created on : 8 Apr, 2011, 3:34:04 PM
    Author     : Zeeshan Waris
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>OD/DP Limit Enhancement</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="txnForm">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ODDPLimitEnhancement.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblInterestMaster" styleClass="headerLabel" value="OD/DP Limit Enhancement"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ODDPLimitEnhancement.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col1" columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{ODDPLimitEnhancement.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel0" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblAcno" styleClass="label" value="Account No:"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id="groupPanelMain" layout="block">
                                <h:inputText id="txtAcno" maxlength="#{ODDPLimitEnhancement.acNoMaxLen}" style="width:120px" value="#{ODDPLimitEnhancement.oldaccountNo}" styleClass="input">
                                    <a4j:support action="#{ODDPLimitEnhancement.accountnoValue}" event="onblur"
                                                 reRender="bodyPanel,btnUpdate,stxtMsg" focus="txtRoiNew"/>
                                </h:inputText>
                                <h:outputText id="stxtNewAcno"  value="#{ODDPLimitEnhancement.accountNo}"  styleClass="output" style="color:green"/>

                            </h:panelGroup>
                            <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                            <h:outputText id="stxtName" styleClass="output" value="#{ODDPLimitEnhancement.name}"/>
                            <h:outputText id="stxtName1" styleClass="output"/>
                            <h:outputText id="stxtName2" styleClass="output"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col9"  columns="2" id="subbodyPanel2" style="width:100%;">
                            <h:panelGrid id="leftPanel" columns="1" width="100%" >
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lbloldRates" styleClass="label"  value="*** Old Rates ***" style="color:navy;"/>
                                    <h:outputLabel id="lbloldRates1" styleClass="label"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel3" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lbloldSanctionedLimit" styleClass="label"  value="#{ODDPLimitEnhancement.oldSanctionedLomitChange}"/>
                                    <h:inputText id="txtoldSanctionedLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.sanctionLimitOld}" styleClass="input" disabled="true"/>
                                </h:panelGrid>
                                <%--h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel4" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lbloldMaximumLimit" styleClass="label"  value="Maximum Limit(Rs.)"/>
                                    <h:inputText id="txtoldMaximumLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.maximumlimitOld}"  styleClass="input" disabled="true"/>
                                </h:panelGrid--%>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel5" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblOldAdhocLimit" styleClass="label"  value="Adhoc Limit(Rs.) (Over Max Limit)"/>
                                    <h:inputText id="txtOldAdhocLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.adhocLimitOld}"  styleClass="input" disabled="true"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel6" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblOldAdhocRoi" styleClass="label"  value="Adhoc ROI(% p.a.)"/>
                                    <h:inputText id="txtOldAdhocRoi" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.adhocRoiOld}"  styleClass="input" disabled="true"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel7" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblOldAdhocApplicableDt" styleClass="label"  value="Adhoc Applicable Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calOldAdhocApplicableDt" value="#{ODDPLimitEnhancement.adhocApplicableDateOld}"  disabled="true"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel8" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblOldAdhocTillDate" styleClass="label"  value="Adhoc Till Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calOldAdhocTillDate" value="#{ODDPLimitEnhancement.adhoctillDateOld}" disabled="true" inputSize="13"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel9" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblOldSubsidyAmount" styleClass="label"  value="Subsidy Amount(Rs.)"/>
                                    <h:inputText id="txtOldSubsidyAmount" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.subsidyAmtOld}" styleClass="input" disabled="true"/>

                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel10" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblOldSubsidyExpDt" styleClass="label"  value="Subsidy Expiry Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calOldSubsidyExpDt" value="#{ODDPLimitEnhancement.subsidyExpiryDateOld}" disabled="true" inputSize="13"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="oldpanel11" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblOldIntOption" styleClass="label"  value="Interest Option"/>
                                    <h:selectOneListbox id="ddOldIntOption" styleClass="ddlist" size="1" required="true" style="width: 120px" value="#{ODDPLimitEnhancement.interestOptionOld}" disabled="true">
                                        <f:selectItems value="#{ODDPLimitEnhancement.ddInterestOptionOld}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid id="rightPanel" columns="1" width="100%">
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNewRates" styleClass="label"  value="*** New Rates ***" style="color:navy;"/>
                                    <h:outputLabel id="lblNewRates1" styleClass="label"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel2" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNewSanctionedLimit" styleClass="label"  value="#{ODDPLimitEnhancement.newSanctionedLomitChange}"/>
                                    <h:inputText id="txtNewSanctionedLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.sanctionLimitNew}" styleClass="input"/>
                                </h:panelGrid>
                                <%--h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel3" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNewMaximumLimit" styleClass="label"  value="Maximum Limit(Rs.)"/>
                                    <h:inputText id="txtNewMaximumLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.maximumlimitNew}"  styleClass="input" disabled="true"/>
                                </h:panelGrid--%>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel4" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNewAdhocLimit" styleClass="label"  value="Adhoc Limit(Rs.) (Over Max Limit)"/>
                                    <h:inputText id="txtNewAdhocLimit" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.adhocLimitNew}"  styleClass="input" disabled="#{ODDPLimitEnhancement.adhocLimitNewDisable}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel5" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNewAdhocRoi" styleClass="label"  value="Adhoc ROI(% p.a.)"/>
                                    <h:inputText id="txtNewAdhocRoi" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.adhocRoiNew}"  styleClass="input" disabled="#{ODDPLimitEnhancement.adhocRoiNewDisable}"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel6" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNewAdhocApplicableDt" styleClass="label"  value="Adhoc Applicable Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calNewAdhocApplicableDt" value="#{ODDPLimitEnhancement.adhocApplicableDateNew}" disabled="#{ODDPLimitEnhancement.adhocApplicableDateNewDisable}" inputSize="13"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel7" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNewAdhocTillDate" styleClass="label"  value="Adhoc Till Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calNewAdhocTillDate" value="#{ODDPLimitEnhancement.adhoctillDateNew}" disabled="#{ODDPLimitEnhancement.adhoctillDateNewDisable}"  inputSize="13"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel8" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNewSubsidyAmount" styleClass="label"  value="Subsidy Amount(Rs.)"/>
                                    <h:inputText id="txtNewSubsidyAmount" maxlength="60" style="width:120px" value="#{ODDPLimitEnhancement.subsidyAmtNew}"styleClass="input" disabled="#{ODDPLimitEnhancement.subsidyAmtNewDisable}"/>

                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel9" style="width:100%;" styleClass="row1">
                                    <h:outputLabel id="lblNewSubsidyExpDt" styleClass="label"  value="Subsidy Expiry Date"/>
                                    <rich:calendar datePattern="dd/MM/yyyy" id="calNewSubsidyExpDt" value="#{ODDPLimitEnhancement.subsidyExpiryDateNew}" disabled="#{ODDPLimitEnhancement.subsidyExpiryDateNewDisable}" inputSize="13"/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2" columns="2" id="newPanel10" style="width:100%;" styleClass="row2">
                                    <h:outputLabel id="lblNewIntOption" styleClass="label"  value="Interest Option"/>
                                    <h:selectOneListbox id="ddNewIntOption" styleClass="ddlist" size="1" required="true" style="width: 120px" value="#{ODDPLimitEnhancement.interestOptionNew}" disabled="#{ODDPLimitEnhancement.interestOptionNewDisable}">
                                        <f:selectItems value="#{ODDPLimitEnhancement.ddInterestOptionOld}"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                            </h:panelGrid>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnUpdate"  value="Save" oncomplete="#{rich:component('updatePanel')}.show()" reRender="bodyPanel,btnUpdate" disabled="#{ODDPLimitEnhancement.saveDisable}">
                            </a4j:commandButton>
                            <a4j:commandButton id = "btnRefresh" value="Refresh" action="#{ODDPLimitEnhancement.clear}" reRender="bodyPanel,btnUpdate" focus="ddNo">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{ODDPLimitEnhancement.exitForm}" reRender="bodyPanel">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="updatePanel" autosized="true" width="250" onshow="#{rich:element('upYes')}.focus()">
                <f:facet name="header">
                    <h:outputText value="Confirmation DialogBox" />
                </f:facet>

                <h:form>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr style="height:40px">
                                <td colspan="2">
                                    <h:outputText value="Are You Sure To Save?"/>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" width="50%">
                                    <a4j:region id="yesBtn">
                                        <a4j:commandButton id="upYes" value="Yes" ajaxSingle="true" action="#{ODDPLimitEnhancement.saveBtnAction}"
                                                           oncomplete="#{rich:component('updatePanel')}.hide();" reRender="bodyPanel"/>
                                    </a4j:region>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton  id="upNo" value="No" onclick="#{rich:component('updatePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
        </body>
    </html>
</f:view>