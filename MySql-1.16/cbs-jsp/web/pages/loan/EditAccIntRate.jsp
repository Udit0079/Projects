<%--
    Document   : EditAccIntRate
    Created on : 17 Mar, 2011, 4:29:07 PM
    Author     : Zeeshan Waris[zeeshan.glorious@gmail.com]
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
            <title>Edit Acc Int Rate</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{EditAccIntRate.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblInterestMaster" styleClass="headerLabel" value="Edit Acc Int Rate"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EditAccIntRate.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col1" columns="1" id="bodyPanel" style="width:100%;">
                        <h:panelGrid columnClasses="col2,col7,col9" columns="1" id="Panel51" style="width:100%;text-align:center;" styleClass="row1">
                            <h:outputText id="stxtMsg" styleClass="error" value="#{EditAccIntRate.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3," columns="6" id="select" style="width:100%" styleClass="row2">
                            <h:outputLabel id="lblSelect" styleClass="label" value="Function"><font class="required" style="color:red;">*</font>
                                </h:outputLabel>
                                <h:selectOneListbox id="ddFunctionType" size="1" styleClass="ddlist" value="#{EditAccIntRate.function}">
                                    <f:selectItems value="#{EditAccIntRate.functionList}"/>
                                    <a4j:support action="#{EditAccIntRate.getDetailsOnblurFunction}" event="onblur" reRender="txtCode,ddAcnoCombo" oncomplete="setMask();"/>
                                </h:selectOneListbox>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                            <h:outputLabel></h:outputLabel>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel0" style="width:100%;" styleClass="row2">
                            <h:outputLabel id="lblAcno" styleClass="label" value="Account No."><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:panelGroup id="pg9">
                                    <%--   <h:selectOneListbox id="ddAcType" tabindex="1" styleClass="ddlist" value="#{EditAccIntRate.acctType}" size="1" style="width: 50px">
                                           <f:selectItems value="#{EditAccIntRate.acctTypeList}" />
                                       </h:selectOneListbox> --%>
                                    <h:panelGroup>
                                        <h:inputText id="txtCode" tabindex="2" style="width:110px" size="#{EditAccIntRate.acNoMaxLen}" value="#{EditAccIntRate.oldacno}" disabled="#{EditAccIntRate.acnoFlag}" styleClass="input">
                                    <a4j:support action="#{EditAccIntRate.getDetails}"   event="onblur" focus="ddIntCode" reRender="bodyPanel,stxtMsg,ddIntCode,Panel30,stxtCust,stxtCode,ddPenalApply"/>
                                        </h:inputText>
                                        <h:selectOneListbox id="ddAcnoCombo" styleClass="ddlist" value="#{EditAccIntRate.verifyAcNo}" size="1" style="display:#{EditAccIntRate.acNoComboDisFlag};">
                                            <f:selectItems value="#{EditAccIntRate.verifyAcNoList}"/>
                                            <a4j:support action="#{EditAccIntRate.getDetailsVerifyAcno}" event="onblur" reRender="stxtCust,stxtCust1,lblIntCodeRoi,ddIntCode,stxtRoi
                                                         ,lblAccPrefCr,txtAccPrefCr,lblMinIntRateCr,txtMinIntRateCr,lblMaxIntRateCr,txtMaxIntRateCr,lblAccPrefDr,txtAccPrefDr
                                                         ,lblMinIntRateDr,txtMinIntRateDr,lblMaxIntRateDr,txtMaxIntRateDr,lblIntPegflag,ddIntPegflag,lblPegFreqMon,txtPegFreqMon
                                                         ,lblPegFreqDays,txtPegFreqDays,lblEffFromDate,calEffFromDate,lblEndDate,calEffToDate,lblEffNoOfDays,txtEffNoOfDays,lblCreatedByUserId,c,
                                                         lblCreationDate,stxtCreationDate,lblRecordModificationCount,txtRecordModificationCount,lblLastChangedUserId,
                                                         stxtLastChangedUserId,lblLastChangedDate,stxtLastChangedDate,lblAppRoi,stxtAppRoi,lblVersionNo,txtVersionNo,ddPenalApply,btnUpdate" oncomplete="setMask();"/>
                                        </h:selectOneListbox>
                                    </h:panelGroup>
                                    <h:outputLabel id="stxtCode" styleClass="label" value="#{EditAccIntRate.acno}"/>
                                </h:panelGroup>
                                <h:outputText id="stxtCust" styleClass="output" value="#{EditAccIntRate.customerName}"/>
                                <h:outputText id="stxtCust1" styleClass="output" />
                                <h:outputLabel id="lblIntCodeRoi" styleClass="label" value="Int Table Code" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup id="pg10">
                                    <h:selectOneListbox id="ddIntCode" size="1" tabindex="3" required="true" styleClass="ddlist"  value="#{EditAccIntRate.inttabcode}" style="width: 110px">
                                        <f:selectItems value="#{EditAccIntRate.intCodeList}"/>
                                        <a4j:support action="#{EditAccIntRate.getRoiForIntTableCode}" event="onblur" focus="txtAccPrefCr"
                                                     reRender="bodyPanel,stxtMsg,ddIntCode,Panel0"/>
                                    </h:selectOneListbox>
                                    <h:outputText id="stxtRoi" styleClass="output" value="#{EditAccIntRate.roi}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel12" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblAccPrefCr" styleClass="label" value="Acc Pref (Cr)"/>
                                <h:inputText id="txtAccPrefCr" disabled="#{EditAccIntRate.disFlag1}" tabindex="4" maxlength="5" style="width:110px" value="#{EditAccIntRate.accPrefCr}" styleClass="input">
                                    <a4j:support action="#{EditAccIntRate.disableAcPrefField}" event="onblur" focus="txtMinIntRateCr"
                                                 reRender="bodyPanel,stxtMsg,ddIntCode,Panel0,Panel13,Panel12"/>
                                </h:inputText>
                                <h:outputLabel id="lblMinIntRateCr" styleClass="label" value="Min Int Rate (Cr)"/>
                                <h:inputText id="txtMinIntRateCr" tabindex="5" maxlength="5" style="width:110px" value="#{EditAccIntRate.minIntRateCr}" styleClass="input"/>
                                <h:outputLabel id="lblMaxIntRateCr" styleClass="label" value="Max Int Rate (Cr)"/>
                                <h:inputText id="txtMaxIntRateCr" tabindex="6" maxlength="5" style="width:110px" value="#{EditAccIntRate.maxIntRateCr}" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel13" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="lblAccPrefDr" styleClass="label" value="Acc Pref (Dr)"/>
                                <h:inputText id="txtAccPrefDr" disabled="#{EditAccIntRate.disFlag}" tabindex="7" maxlength="5" style="width:110px" value="#{EditAccIntRate.accPrefDr}" styleClass="input">
                                    <a4j:support action="#{EditAccIntRate.disableAcPrefCrField}" event="onblur" focus="txtMinIntRateDr"
                                                 reRender="bodyPanel,stxtMsg,ddIntCode,Panel0,Panel13,Panel12"/>
                                </h:inputText>
                                <h:outputLabel id="lblMinIntRateDr" styleClass="label" value="Min Int Rate (Dr)"/>
                                <h:inputText id="txtMinIntRateDr" tabindex="8" maxlength="5" style="width:110px" value="#{EditAccIntRate.minIntRateDr}" styleClass="input">
                                    <a4j:support action="#{EditAccIntRate.setAppRoiValue}" event="onblur" focus="txtMaxIntRateDr"
                                                 reRender="bodyPanel,stxtMsg,ddIntCode,Panel0,Panel13,Panel12,Panel30"/>
                                </h:inputText>
                                <h:outputLabel id="lblMaxIntRateDr" styleClass="label" value="Max Int Rate (Dr)"/>
                                <h:inputText id="txtMaxIntRateDr" tabindex="9" maxlength="5" style="width:110px" value="#{EditAccIntRate.maxIntRateDr}" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel14" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblIntPegflag" styleClass="label" value="Int Peg flag"/>
                                <h:selectOneListbox id="ddIntPegflag" tabindex="10" styleClass="ddlist" size="1" required="true" style="width: 110px" value="#{EditAccIntRate.intPegflag}">
                                    <f:selectItems value="#{EditAccIntRate.recrdStatus}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblPegFreqMon" styleClass="label" value="Peg Freq Month"/>
                                <h:inputText id="txtPegFreqMon" tabindex="11" maxlength="12" style="width:110px" value="#{EditAccIntRate.pegFreqMon}"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                <h:outputLabel id="lblPegFreqDays" styleClass="label" value="Peg Freq Days"/>
                                <h:inputText id="txtPegFreqDays" tabindex="12" maxlength="12" style="width:110px" value="#{EditAccIntRate.pegFreqDays}"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel15" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="lblEffFromDate" styleClass="label" value="Eff From Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" tabindex="13" id="calEffFromDate" value="#{EditAccIntRate.fromDate}" inputSize="12">
                                    <a4j:support action="#{EditAccIntRate.dateDiffDetails}" event="onchanged"
                                                 reRender="toDt,txtEffNoOfDays" focus="btnUpdate"/>
                                </rich:calendar>
                                <h:outputLabel id="lblEndDate" styleClass="label" value="Eff To Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" tabindex="14" id="calEffToDate" value="#{EditAccIntRate.toDate}" disabled="#{EditAccIntRate.flag}" inputSize="12"/>
                                <h:outputLabel id="lblEffNoOfDays" styleClass="label" value="Eff No Of Days"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="txtEffNoOfDays" tabindex="15" maxlength="12" style="width:110px" value="#{EditAccIntRate.effNoOfDays}"  disabled="#{EditAccIntRate.flag}" styleClass="input"/>
                            </h:panelGrid>                            
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel16" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblCreatedByUserId" styleClass="label" value="Created By "/>
                                <h:outputText id="c" styleClass="output" value="#{EditAccIntRate.createdBy}"/>
                                <h:outputLabel id="lblCreationDate" styleClass="label" value="Creation Date"/>
                                <h:outputText id="stxtCreationDate" styleClass="output" value="#{EditAccIntRate.creationDate}"/>
                                <h:outputLabel id="lblRecordModificationCount" styleClass="label" value="Record Modification Count"/>
                                <h:outputText id="txtRecordModificationCount" styleClass="output" value="#{EditAccIntRate.recordModificationCount}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel30" style="width:100%;" styleClass="row2">
                                <h:outputLabel id="lblLastChangedUserId" styleClass="label" value="Last Updated By "/>
                                <h:outputText id="stxtLastChangedUserId" styleClass="output" value="#{EditAccIntRate.changedBy}"/>
                                <h:outputLabel id="lblLastChangedDate" styleClass="label" value="Last Updated Date"/>
                                <h:outputText id="stxtLastChangedDate" styleClass="output" value="#{EditAccIntRate.changedDate}"/>
                                <h:outputLabel id="lblAppRoi" styleClass="label" value="Applicable ROI (%) : "/>
                                <h:outputText id="stxtAppRoi" styleClass="output" value="#{EditAccIntRate.appRoi}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Panel31" style="width:100%;" styleClass="row1">
                                <h:outputLabel id="lblVersionNo" styleClass="label" value="Account Int Version No:"/>
                                <h:outputText id="txtVersionNo" styleClass="output" value="#{EditAccIntRate.versionNo}"/>
                                <h:outputLabel id="lblSelectPenal" styleClass="label" value="Penal Apply"><font class="required" style="color:red;">*</font>
                                </h:outputLabel>
                                <h:selectOneListbox id="ddPenalApply" size="1" styleClass="ddlist" value="#{EditAccIntRate.penalApply}" disabled="true">
                                    <f:selectItems value="#{EditAccIntRate.penalApplyList}"/>
                                </h:selectOneListbox>
                            <h:outputLabel  styleClass="label"/>
                            <h:outputLabel  styleClass="label"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnUpdate" tabindex="16" value="Save" action="#{EditAccIntRate.updateBtnAction}" reRender="bodyPanel,stxtMsg" focus="btnRefresh"/>
                            <a4j:commandButton id="btnRefresh" tabindex="17" value="Refresh" action="#{EditAccIntRate.refreshButton}" reRender="bodyPanel,stxtMsg,stxtCode" focus="ddAcType"/>
                            <a4j:commandButton id="btnExit" tabindex="18" value="Exit" action="#{EditAccIntRate.exitForm}" reRender="bodyPanel,stxtMsg,stxtCode"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>