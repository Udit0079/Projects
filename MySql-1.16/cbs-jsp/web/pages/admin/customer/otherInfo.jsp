<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="otherInfo">
     <a4j:region>
    <h:panelGrid id="othInfoPanel" columns="3" style="width:100%;text-align:center;">
        <h:panelGrid id="leftPanel11" style="width:100%;text-align:center;">
            <rich:panel header="Customer Rating Details" style="text-align:left;">
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="othRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblOprRskRate" styleClass="label" value="Operational Risk Rating"/>
                    <h:selectOneListbox id="txtOprRskRate" styleClass="ddlist" value="#{OtherInfo.operRiskRate}" size="1">
                        <f:selectItems value="#{OtherInfo.operRiskRateOption}"/>
                        <a4j:support event="onblur"/>
                    </h:selectOneListbox>
                    <%--<h:inputText  value="#{OtherInfo.operRiskRate}"id="txtOprRskRate" maxlength="5"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>--%>
                    <h:outputLabel id="lblRtAsOn" styleClass="label" value="Rating As On"/>
                    <rich:calendar  value="#{OtherInfo.ratingAsOn1}"datePattern="dd/MM/yyyy" id="RtAsOn"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurRatingsAsOn1}" reRender="stxtError"focus="txtCrRskRate"/>
                    </rich:calendar>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="othRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCrRskRate" styleClass="label" value="Credit Risk Rating Internal"/>
                    <h:inputText  value="#{OtherInfo.creditOperRiskrate}"id="txtCrRskRate" maxlength="5"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblCrRtAsOn" styleClass="label" value="Rating As On"/>
                    <rich:calendar  value="#{OtherInfo.ratingAsOn2}"datePattern="dd/MM/yyyy" id="CrRtAsOn"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurRatingsAsOn2}" reRender="stxtError"focus="txtExtRtSt"/>
                    </rich:calendar>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="othRow2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblExtRtSt" styleClass="label" value="External Rating Short Term"/>
                    <h:inputText  value="#{OtherInfo.exRatingShortterm}"id="txtExtRtSt" maxlength="5"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblExtRtAsOn" styleClass="label" value="Rating As On"/>
                    <rich:calendar  value="#{OtherInfo.ratingAsOn3}"datePattern="dd/MM/yyyy" id="ExtRtSt"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurRatingsAsOn3}" reRender="stxtError"focus="txtExtRtLt"/>
                    </rich:calendar>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="othRow3" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblExtRtLt" styleClass="label" value="External Rating Long Term"/>
                    <h:inputText  value="#{OtherInfo.exRatingLongterm}"id="txtExtRtLt" maxlength="5"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblExtRtLtAsOn" styleClass="label" value="Rating As On"/>
                    <rich:calendar  value="#{OtherInfo.ratingAsOn4}"datePattern="dd/MM/yyyy" id="ExtRtLt"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurRatingsAsOn4}" reRender="stxtError"focus="ddAtmCard"/>
                    </rich:calendar>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Customer Delivery Channel Details" style="text-align:left;">
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="custDelRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblAtmCard" styleClass="label" value="ATM/DEBIT Card Holder"/>
                    <h:selectOneListbox id="ddAtmCard" styleClass="ddlist" value="#{OtherInfo.atmCardType}" size="1">
                        <f:selectItems value="#{OtherInfo.atmCardTypeOption}"/>
                        <%--<rich:toolTip for="ddAtmCard" value="Please Select ATM/DEBIT"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblCrdCard" styleClass="label" value="Credit Card Holder"/>
                    <h:selectOneListbox   value="#{OtherInfo.creditCardHolder}"id="ddCrdCard" styleClass="ddlist"  size="1">
                        <%--<f:selectItems value=""/>--%>

                        <f:selectItems value="#{OtherInfo.creditCardHolderOption}"/>
                        <%--<rich:toolTip for="ddCrdCard" value="Please Select Credit Card"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="custDelRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblTelBnk" styleClass="label" value="Phone Banking Enable"/>
                    <h:selectOneListbox id="ddTelBnk" styleClass="ddlist" value="#{OtherInfo.telBnkType}" size="1">
                        <f:selectItems value="#{OtherInfo.telBnkTypeOption}"/>
                        <%--<rich:toolTip for="ddTelBnk" value="Please Select Tele Banking"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblSMSBnk" styleClass="label" value="SMS Banking Enable"/>
                    <h:selectOneListbox id="ddSMSBnk" styleClass="ddlist" value="#{OtherInfo.smsBnkType}" size="1">
                        <f:selectItems value="#{OtherInfo.smsBnkTypeOption}"/>
                        <%--<rich:toolTip for="ddSMSBnk" value="Please Select SMS Banking"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="custDelRow2" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblINetBnk" styleClass="label" value="I-NET Banking Enable"/>
                    <h:selectOneListbox id="ddINetBnk" style="width:72px;"styleClass="ddlist" value="#{OtherInfo.iNetBnkType}" size="1">
                        <f:selectItems value="#{OtherInfo.iNetBnkTypeOption}"/>
                        <%--<rich:toolTip for="ddINetBnk" value="Please Select I-NET Banking"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblMobBnk" styleClass="label" value="Mobile Banking Enable"/>
                    <h:selectOneListbox value="#{OtherInfo.mobBankingEnable}" id="ddMobBnk" styleClass="ddlist"  size="1">
                        <f:selectItems value="#{OtherInfo.mobBankingEnableOption}"/>
                        <%--<f:selectItems value=""/>--%>
                        <%--<rich:toolTip for="ddMobBnk" value="Please Select Mobile Banking"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="custDelRow3" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblSelfSrv" styleClass="label" value="Self Service Enable"/>
                    <h:selectOneListbox id="ddSelfSrv" styleClass="ddlist" value="#{OtherInfo.selfSrvType}" size="1">
                        <f:selectItems value="#{OtherInfo.selfSrvTypeOption}"/>
                        <%--<rich:toolTip for="ddSelfSrv" value="Please Select Self Service"></rich:toolTip>--%>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblECS" styleClass="label" value="ECS Enable"/>
                    <h:selectOneListbox value="#{OtherInfo.ecsEnable}" id="ddECS" styleClass="ddlist"  size="1">
                        <f:selectItems  value="#{OtherInfo.ecsEnableOption}"/>
                        <%--<f:selectItems value=""/>--%>
                        <%--<rich:toolTip for="ddECS" value="Please Select ECS Service"></rich:toolTip>--%>
                    </h:selectOneListbox>
                </h:panelGrid>
            </rich:panel>
            <rich:panel header="Customer KYC/AML Details" style="text-align:left;">
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycRow" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCustAqDt" styleClass="label" value="Customer Acquisition Date"/>
                    <rich:calendar  value="#{OtherInfo.custAquiDate}"datePattern="dd/MM/yyyy" id="CustAqDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurCustAquiDate}"  reRender="stxtError"focus="txtThrTrLmt"/>
                    </rich:calendar>
                    <h:outputLabel id="lblThrTrLmt" styleClass="label" value="Thresold TransactionListener Limit"/>
                    <h:inputText  value="#{OtherInfo.threshOldTransLim}"id="txtThrTrLmt" styleClass="input" style="120px">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycRow1" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblThrLmtUpDt" styleClass="label" value="Thresold Limit Updation Date"/>
                    <rich:calendar  value="#{OtherInfo.thresholdLimUpdDate}"datePattern="dd/MM/yyyy" id="ThrLmtUpDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurThreshUpdateDate}" reRender="stxtError"focus="CustUpDt" ajaxSingle="true"/>
                    </rich:calendar>
                    <h:outputLabel id="lblCustUpDt" styleClass="label" value="Customer Updation Date"/>
                    <rich:calendar  value="#{OtherInfo.custUpdationDate}"datePattern="dd/MM/yyyy" id="CustUpDt"  jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{OtherInfo.onblurCustUpdateDate}" reRender="stxtError"/>
                    </rich:calendar>
                </h:panelGrid>
            </rich:panel>
        </h:panelGrid>
    </h:panelGrid>
          </a4j:region>
</h:form>
