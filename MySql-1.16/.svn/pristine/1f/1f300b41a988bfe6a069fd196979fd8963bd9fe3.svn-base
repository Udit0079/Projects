<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="tradeFinanceInfo">
     <a4j:region>
    <h:panelGrid id="trFinInfoPanel" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel8" style="width:100%;text-align:center;">
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow" style="width:100%;text-align:left;" styleClass="row1">
                <h:panelGroup id="tfiPanelGrp1" layout="block" >
                    <h:outputLabel id="lblTrFinName" styleClass="label" value="Name"/>
                    <h:outputLabel id="lblTfiNameStar" styleClass="error" value="#{TradeFinanceInfo.tfi1}"/>
                </h:panelGroup>
                <h:inputText  value="#{TradeFinanceInfo.tfiName}"id="txtTrFinName" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTFIName}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:panelGroup id="tfiPanelGrp2" layout="block" >
                    <h:outputLabel id="lblTrFinAddr1" styleClass="label" value="Address Line1"/>
                    <h:outputLabel id="lblTfiAdd1Star" styleClass="error" value="#{TradeFinanceInfo.tfi2}"/>
                </h:panelGroup>
                <h:inputText  value="#{TradeFinanceInfo.tfiAdd1}"id="txtTrFinAddr1" maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblTrFinAddr2" styleClass="label" value="Address Line2"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiAdd2}"id="txtTrFinAddr2" maxlength="50"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow1" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblTrFinCity" styleClass="label" value="City"/>
                <h:selectOneListbox  value="#{TradeFinanceInfo.cityForTFI}"id="ddTrFinCity" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.cityForTFIOption}"/>
                    <%--<rich:toolTip for="ddTrFinCity" value="Please Select City"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblTrFinPostal" styleClass="label" value="Postal Code"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiPostal}"id="txtTrFinPostal" maxlength="6"styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTfiPostal}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblTrFinCountry" styleClass="label" value="Country"/>
                <h:selectOneListbox  value="#{TradeFinanceInfo.countryForTFI}"id="ddTrFinCountry" styleClass="ddlist" size="1">
                    <f:selectItems value="#{TradeFinanceInfo.countryForTFIOption}"/>
                    <%--<rich:toolTip for="ddTrFinCountry" value="Please Select Country"></rich:toolTip>--%>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow2" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblTrFinTel" styleClass="label" value="Telephone No"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiPhone}"id="txtTrFinTel" maxlength="15"styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTFITelNo}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblTrFinFax" styleClass="label" value="Fax No"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiFax}"id="txtTrFinFax" maxlength="15"styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTFIFaxNo}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblTrFinTelex" styleClass="label" value="Telex No"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiTelex}"id="txtTrFinTelex" maxlength="15"styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTFITelexNo}" event="onblur" reRender="stxtError"/>
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow3" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblTrNative" styleClass="label" value="Native"/>
                <h:inputText value="#{TradeFinanceInfo.tfiNative}" id="txtTrNative" maxlength="1"styleClass="input"onkeydown="this.value=this.value.toUpperCase();">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurTFINativeNo}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblInTrAllowed" styleClass="label" value="Inland Trade Allowed"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.inlandTradeAllowed}" id="ddInTrAllowed" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.inlandTradeAllowedOption}"/>
                    <%--<rich:toolTip for="ddInTrAllowed" value="Please Select Inland Trade"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblTrRevDt" styleClass="label" value="Review Date"/>
                <rich:calendar  value="#{TradeFinanceInfo.reviewDate}"datePattern="dd/MM/yyyy" id="TrRevDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" actionListener="#{TradeFinanceInfo.onblurReviewDate}" reRender="stxtError"focus="txtTrOSLiab"/>
                </rich:calendar>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow4" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblTrOSLiab" styleClass="label" value="Present Outstanding Liability"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiPresentOutStandLiab}"id="txtTrOSLiab" styleClass="input" style="120px"/>
                <h:outputLabel id="lblIndType" styleClass="label" value="Industry Type"/>
                <h:selectOneListbox  value="#{TradeFinanceInfo.industryType}"id="ddIndType" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.industryTypeOption}"/>
                    <%--<rich:toolTip for="ddIndType" value="Please Select Industry Trade"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblTrFinType" styleClass="label" value="Type (Exp/Imp)"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.typeExpImp}" id="ddTrFinType" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.typeExpImpOption}"/>
                    <%--<rich:toolTip for="ddTrFinType" value="Please Select Type"></rich:toolTip>--%>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow5" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblTrExpUnit" styleClass="label" value="100% Export Unit"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.exportUnit}" id="ddTrExpUnit" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.exportUnitOption}"/>
                    <%--<rich:toolTip for="ddTrExpUnit" value="Please Select Export Unit"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblTrStatus" styleClass="label" value="Status"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.statusTFI}" id="ddTrStatus" styleClass="ddlist" size="1" style="width:76px;">
                    <f:selectItems value="#{TradeFinanceInfo.statusTFIOption}"/>
                    <%--<rich:toolTip for="ddTrStatus" value="Please Select Status"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblPartCons" styleClass="label" value="Party Constitution"/>
                <h:selectOneListbox  value="#{TradeFinanceInfo.partyConstitution}"id="ddPartCons" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.partyConstitutionOption}"/>
                    <%--<rich:toolTip for="ddPartCons" value="Please Select Party Constitution"></rich:toolTip>--%>
                </h:selectOneListbox>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow6" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblSpParty" styleClass="label" value="Special Party"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.specialParty}" id="ddSpParty" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.specialPartyOption}"/>
                    <%--<rich:toolTip for="ddSpParty" value="Please Select Status"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblPartyTp" styleClass="label" value="Party Type"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiPartyType}"id="txtPartyTp" maxlength="25"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblTrPrdCycle" styleClass="label" value="Production Cycle"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiProdCycle}"id="txtTrPrdCycle" maxlength="3"styleClass="input"onkeydown="this.value=this.value.toUpperCase();">
                </h:inputText>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow7" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblTrExpCd" styleClass="label" value="Trade Exposure Code"/>
                <h:selectOneListbox value="#{TradeFinanceInfo.tradeExpoCode}" id="ddTrExpCd" styleClass="ddlist"  size="1">
                    <f:selectItems value="#{TradeFinanceInfo.tradeExpoCodeOption}"/>
                    <%--<rich:toolTip for="ddTrExpCd" value="Please Select Trade Exposure"></rich:toolTip>--%>
                </h:selectOneListbox>
                <h:outputLabel id="lblTrCodeCBnk" styleClass="label" value="Code Given By Central Bank"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiCodeGivenByCentralBnk}"id="txtTrCodeCBnk" maxlength="12"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblTrCodeBCen" styleClass="label" value="Code Given By Central Authority"/>
                <h:inputText  value="#{TradeFinanceInfo.tfiCodeGivenByCentralAuth}"id="txtTrCodeBCen" maxlength="12"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow8" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblDcMarPerc" styleClass="label" value="DC Margin%"/>
                <h:inputText  value="#{TradeFinanceInfo.dcMargin100}"id="txtDcMarPerc" styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurDcMarginPer}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblDCSancAuth" styleClass="label" value="DC Sactioning Authority"/>
                <h:inputText  value="#{TradeFinanceInfo.dcSactioningAuth}"id="txtDCSancAuth" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow9" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblDCSancExpDt" styleClass="label" value="DC Sacnction Expiry Date"/>
                <rich:calendar  value="#{TradeFinanceInfo.dcSancExpDate}"datePattern="dd/MM/yyyy" id="DCSancExpDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" actionListener="#{TradeFinanceInfo.onblurDcSancExpDate}" reRender="stxtError"focus="txtDCNxtNo"/>
                </rich:calendar>
                <h:outputLabel id="lblDCNxtNo" styleClass="label" value="DC Next No Code"/>
                <h:inputText  value="#{TradeFinanceInfo.dcNextNoCode}"id="txtDCNxtNo" maxlength="20"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblDCCrLmt" styleClass="label" value="DC Credit Limit"/>
                <h:inputText  value="#{TradeFinanceInfo.dcCreditLim}"id="txtDCCrLmt" styleClass="input" style="120px"/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow10" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblFcMarPerc" styleClass="label" value="FC Margin%"/>
                <h:inputText  value="#{TradeFinanceInfo.fcMargin100}"id="txtFcMarPerc" styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurFcMarginPer}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblFCSancAuth" styleClass="label" value="FC Sanctioning Authority"/>
                <h:inputText  value="#{TradeFinanceInfo.fcSactioningAuth}"id="txtFCSancAuth" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblFCSancExpDt" styleClass="label" value="FC Sacnction Expiry Date"/>
                <rich:calendar  value="#{TradeFinanceInfo.fcSancExpDate}"datePattern="dd/MM/yyyy" id="FCSancExpDt" jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" actionListener="#{TradeFinanceInfo.onblurFcSancExpdate}" reRender="stxtError"focus="txtFCNxtNo"/>
                </rich:calendar>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow11" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblFCNxtNo" styleClass="label" value="FC Next No Code"/>
                <h:inputText  value="#{TradeFinanceInfo.fcNextNoCode}"id="txtFCNxtNo" maxlength="20"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblFCCrLmt" styleClass="label" value="FC Credit Limit"/>
                <h:inputText  value="#{TradeFinanceInfo.fcCreditLim}"id="txtFCCrLmt" styleClass="input" style="120px"/>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow12" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblPcMarPerc" styleClass="label" value="PC Margin%"/>
                <h:inputText  value="#{TradeFinanceInfo.pcMargin100}"id="txtPcMarPerc" styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurPCMarginPer}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblPCSancAuth" styleClass="label" value="PC Sanctioning Authority"/>
                <h:inputText  value="#{TradeFinanceInfo.pcSactioningAuth}"id="txtPCSancAuth" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblPCSancExpDt" styleClass="label" value="PC Sacnction Expiry Date"/>
                <rich:calendar  value="#{TradeFinanceInfo.pcSancExpDate}"datePattern="dd/MM/yyyy" id="PCSancExpDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" actionListener="#{TradeFinanceInfo.onblurPcSancExpdate}" reRender="stxtError"focus="txtPCNxtNo"/>
                </rich:calendar>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow13" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblPCNxtNo" styleClass="label" value="PC Next No Code"/>
                <h:inputText  value="#{TradeFinanceInfo.pcNextNoCode}"id="txtPCNxtNo" maxlength="20"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                <h:outputLabel id="lblPCCrLmt" styleClass="label" value="PC Credit Limit"/>
                <h:inputText  value="#{TradeFinanceInfo.pcCreditLim}"id="txtPCCrLmt" styleClass="input" style="120px"/>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow14" style="width:100%;text-align:left;" styleClass="row1">
                <h:outputLabel id="lblBgMarPerc" styleClass="label" value="BG Margin%"/>
                <h:inputText  value="#{TradeFinanceInfo.bgMargin100}"id="txtBgMarPerc" styleClass="input" style="120px">
                    <a4j:support actionListener="#{TradeFinanceInfo.onblurBGMarginPer}" event="onblur" reRender="stxtError"/>
                </h:inputText>
                <h:outputLabel id="lblBGSancAuth" styleClass="label" value="BG Sanctioning Authority"/>
                <h:inputText  value="#{TradeFinanceInfo.bgSactioningAuth}"id="txtBGSancAuth" maxlength="40"styleClass="input" style="120px"/>
                <h:outputLabel id="lblBGSancExpDt" styleClass="label" value="BG Sacnction Expiry Date"/>
                <rich:calendar  value="#{TradeFinanceInfo.bgSancExpDate}"datePattern="dd/MM/yyyy" id="BGSancExpDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                    <a4j:support event="onchanged" actionListener="#{TradeFinanceInfo.onblurBGSancExpdate}" reRender="stxtError"focus="txtBGNxtNo"/>
                </rich:calendar>
            </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="trFinRow15" style="width:100%;text-align:left;" styleClass="row2">
                <h:outputLabel id="lblBGNxtNo" styleClass="label" value="BG Next No Code"/>
                <h:inputText  value="#{TradeFinanceInfo.bgNextNoCode}"id="txtBGNxtNo" maxlength="20"styleClass="input" style="120px"/>
                <h:outputLabel id="lblBGCrLmt" styleClass="label" value="BG Credit Limit"/>
                <h:inputText  value="#{TradeFinanceInfo.bgCreditLim}"id="txtBGCrLmt" styleClass="input" style="120px"/>
                <h:outputLabel/>
                <h:outputLabel/>
            </h:panelGrid>
        </h:panelGrid>
    </h:panelGrid>
          </a4j:region>
</h:form>
