<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="misInfo">
    <a4j:region>
        <h:panelGrid id="MISInfoPanel" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel5" style="width:100%;text-align:center;">
                <rich:panel header="ADDITIONAL DETAILS (Individual And Legal Entity)" style="text-align:left;">
                    <h:panelGrid id="misRow3" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" 
                                 styleClass="row1">
                        <h:outputLabel id="lblConst" styleClass="label" value="Constitution">
                            <font class="required" style="color:red;">*</font>
                        </h:outputLabel>
                        <h:selectOneListbox id="ddConst" styleClass="ddlist"size="1" value="#{MisInfo.misConstitution}" 
                                            style="#{MisInfo.misConstitutionChgFlag}">
                            <f:selectItems value="#{MisInfo.misConstitutionOption}"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblOccup" styleClass="label" value="Occupation"/>
                        <h:selectOneListbox id="ddOccup" style="width:72px;#{MisInfo.misOccupationChgFlag}" styleClass="ddlist" size="1" disabled="#{MisInfo.misIndDisable}"
                                            value="#{MisInfo.misOccupation}">
                            <f:selectItems value="#{MisInfo.misOccupationOption}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="ADDITIONAL DETAILS (Legal Entity)" style="text-align:left;">

                    <h:panelGrid id="misRow2" columnClasses="col2,col7,col2,col7" columns="4" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblIncopCountry" styleClass="label" value="Country of Incorporation or Formation"/>

                        <h:selectOneListbox id="ddIncopCountry" style="width:72px;#{MisInfo.incopPlaceChgFlag}" styleClass="ddlist" size="1" 
                                            value="#{MisInfo.countryOfIncorp}" disabled="#{MisInfo.misCompDisable}">
                            <f:selectItems value="#{MisInfo.countryOfIncorpList}"/>
                            <a4j:support event="onblur"  actionListener="#{MisInfo.onChangeIncorpCountry()}" reRender="txtIncopState,ddIncopState,misRow11"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblIncopState" styleClass="label" value="State of Incorporation or Formation"/>
                        <h:panelGroup>
                            <h:inputText  value="#{MisInfo.stateOfIncorpNotIN}"id="txtIncopState" maxlength="50" styleClass="input" 
                                          style="width:72px;#{MisInfo.stateOfIncorpChgFlag};display:#{MisInfo.displayOnIncrpCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:selectOneListbox id="ddIncopState" style="width:72px;#{MisInfo.stateOfIncorpChgFlag};display:#{MisInfo.displayOnIncrpCntryIN};" styleClass="ddlist" size="1" 
                                                value="#{MisInfo.stateOfIncorp}" disabled="#{MisInfo.misCompDisable}">
                                <f:selectItems value="#{MisInfo.stateOfIncorpList}"/>
                                <a4j:support event="onblur"  actionListener="#{MisInfo.onChangeIncorpState()}" reRender="misRow11"/>
                            </h:selectOneListbox>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow11" style="width:100%;text-align:left;" 
                                 styleClass="row2">
                        <h:outputLabel id="lblIncopPlace" styleClass="label" value="Place of Incorporation or Formation"/>
                        <h:panelGroup>
                            <h:inputText  value="#{MisInfo.incopPlaceNotIN}"id="txtIncopPlace" maxlength="50" styleClass="input" 
                                          style="width:72px;#{MisInfo.incopPlaceChgFlag};display:#{MisInfo.displayOnIncrpCntryNotIN};">
                                <a4j:support event="onblur"/>
                            </h:inputText>
                            <h:inputText id="ddIncopPlace" style="width:72px;#{MisInfo.incopPlaceChgFlag};display:#{MisInfo.displayOnIncrpCntryIN};" styleClass="input"
                                         value="#{MisInfo.incopPlace}" disabled="#{MisInfo.misCompDisable}">
                                <a4j:support event="onblur" />
                            </h:inputText>
                            <rich:suggestionbox height="100" width="150" usingSuggestObjects="true"
                                                onobjectchange="printObjectsSelected(#{rich:component('incopPlaceSuggestion')});"
                                                suggestionAction="#{MisInfo.getPlaceofIncorpList}" var="incopPlace"
                                                for="ddIncopPlace" fetchValue="#{incopPlace}" id="incopPlaceSuggestion">
                                <h:column>
                                    <h:outputText value="#{incopPlace}" />
                                </h:column>
                            </rich:suggestionbox>
                        </h:panelGroup>
                        <h:outputLabel id="lblContryForTax" styleClass="label" value="Country Code of Residence As Per Tax Law"/>
                        <h:selectOneListbox  value="#{MisInfo.countryOfResiForTax}"id="ddContryForTax" style="width:120px;#{MisInfo.misIsoResidenceChgFlag}" 
                                             styleClass="ddlist"size="1">
                            <f:selectItems value="#{MisInfo.countryOfResiForTaxList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="misRow1" columnClasses="col2,col7,col2,col7" columns="4" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <%--
                        <h:outputLabel id="lblDif" styleClass="label" value="Date of Incorporation or Formation"/>
                        <rich:calendar id="difDt" value="#{MisInfo.incopDt}" datePattern="dd/MM/yyyy" inputSize="10" 
                                       disabled="#{MisInfo.misCompDisable}" inputStyle="#{MisInfo.incopDtChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                        --%>
                        <h:outputLabel id="lbldcb" styleClass="label" value="Date of Commencement of Business"/>
                        <rich:calendar id="dcbDt" value="#{MisInfo.dcbDt}" datePattern="dd/MM/yyyy" inputSize="10" disabled="#{MisInfo.misCompDisable}" inputStyle="#{MisInfo.dcbDtChgFlag}">
                            <a4j:support event="onchange"/>
                        </rich:calendar>
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>
                </rich:panel>
                <h:panelGrid id="misRow4" columnClasses="col2,col7,col2,col7" columns="4" style="width:100%;text-align:left;display:#{MisInfo.displayOfficePanal}" styleClass="row1">
                    <h:outputLabel id="lblType" styleClass="label" value="Office Id"/>
                    <h:selectOneListbox id="txtType" styleClass="ddlist"size="1" value="#{MisInfo.misType}" style="width:270px;#{MisInfo.misTypeChgFlag}">
                        <f:selectItems value="#{MisInfo.misTypeList}"/>
                        <a4j:support action="#{MisInfo.getGroupIDdata}" event="onblur" reRender="txtGrpId"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblGrpId" styleClass="label" value="Group Id"/>
                    <h:selectOneListbox id="txtGrpId" styleClass="ddlist"size="1" value="#{MisInfo.groupId}" style="width:150px;#{MisInfo.misGroupIdChgFlag}">
                        <f:selectItems value="#{MisInfo.groupIdList}"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <rich:panel header="ADDITIONAL DETAILS REQUIRED (If Applicant is resident outside India for Tax purposes)" 
                            style="text-align:left;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow8" style="width:100%;text-align:left;" 
                                 styleClass="row1">
                        <h:outputLabel id="lblResidence" styleClass="label" value="ISO -3166 Country Code of Jurisdiction of Residence"/>
                        <h:selectOneListbox  value="#{MisInfo.misIsoResidence}"id="ddResidence" style="width:72px;#{MisInfo.misIsoResidenceChgFlag}" 
                                             styleClass="ddlist"size="1">
                            <f:selectItems value="#{MisInfo.misIsoResidenceList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblJuriTin" styleClass="label" value="Tax Identification Number or equivalent (If issued by jurisdiction)"/>
                        <h:inputText  value="#{MisInfo.misJuriTin}" id="txtJuriTin" maxlength="16" styleClass="input" style="120px;#{MisInfo.misJuriTinChgFlag}" 
                                      onkeydown="this.value=this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow9" style="width:100%;text-align:left;" 
                                 styleClass="row2">
                        <h:outputLabel id="lblPlaceCity" styleClass="label" value="Place/City of Birth"/>
                        <h:inputText  value="#{MisInfo.misPlaceCity}"id="txtPlaceCity" maxlength="25" styleClass="input" 
                                      style="120px;#{MisInfo.misPlaceCityChgFlag}" onkeydown="this.value=this.value.toUpperCase();" disabled="#{MisInfo.misIndDisable}"/>
                        <h:outputLabel id="lblMisBirth" styleClass="label" value="ISO -3166 Country Code of Birth"/>
                        <h:selectOneListbox value="#{MisInfo.misBirth}" id="ddMisBirth" style="width:72px;#{MisInfo.misBirthChgFlag}" styleClass="ddlist" 
                                            size="1" disabled="#{MisInfo.misIndDisable}">
                            <f:selectItems value="#{MisInfo.misBirthList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Customer Rating Details" style="text-align:left;">
                    <h:panelGrid id="customerRating" columnClasses="col2,col7,col2,col7" columns="4" 
                                 style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblOprRskRate" styleClass="label" value="Operational Risk Rating"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox id="txtOprRskRate" styleClass="ddlist" value="#{MisInfo.opRiskRating}" size="1" style="#{MisInfo.opRiskRatingChgFlag}">
                            <f:selectItems value="#{MisInfo.opRiskRatingOption}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblRtAsOn" styleClass="label" value="Rating As On"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <rich:calendar id="rtAsOn" value="#{MisInfo.riskAsOn}" datePattern="dd/MM/yyyy" jointPoint="top-left" 
                                       direction="top-right" inputSize="10" inputStyle="#{MisInfo.riskAsOnChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Customer KYC/AML Details" style="text-align:left;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycRow" style="width:100%;text-align:left;" 
                                 styleClass="row1">
                        <h:outputLabel id="lblCustAqDt" styleClass="label" value="Customer Acquisition Date"/>
                        <rich:calendar id="CustAqDt" value="#{MisInfo.custAquiDate}" datePattern="dd/MM/yyyy" 
                                       jointPoint="top-left" direction="top-right" inputSize="10" inputStyle="#{MisInfo.custAquiDateChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                        <h:outputLabel id="lblThrTrLmt" styleClass="label" value="Thresold TransactionListener Limit"/>
                        <h:inputText id="txtThrTrLmt" styleClass="input" style="120px;#{MisInfo.threshOldTransLimitChgFlag}" value="#{MisInfo.threshOldTransLimit}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycRow1" style="width:100%;text-align:left;" 
                                 styleClass="row2">
                        <h:outputLabel id="lblThrLmtUpDt" styleClass="label" value="Thresold Limit Updation Date"/>
                        <rich:calendar id="ThrLmtUpDt" value="#{MisInfo.thresholdLimitUpdDate}"datePattern="dd/MM/yyyy"  
                                       jointPoint="top-left" direction="top-right" inputSize="10" inputStyle="#{MisInfo.thresholdLimitUpdDateChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                        <h:outputLabel id="lblCustUpDt" styleClass="label" value="Customer Updation Date"/>
                        <rich:calendar id="custUpDt" value="#{MisInfo.custUpdationDate}" datePattern="dd/MM/yyyy" 
                                       jointPoint="top-left" direction="top-right" inputSize="10" inputStyle="#{MisInfo.custUpdationDateChgFlag}">
                            <a4j:support event="onchanged"/>
                        </rich:calendar>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="kycRow2" style="width:100%;text-align:left;" 
                                 styleClass="row1">
                        <h:outputLabel id="lblTypeOfDocSubmit" styleClass="label" value="Type Of Doc Submit">
                            <font class="required" style="color:red;">*</font> </h:outputLabel>
                        <h:selectOneListbox id="txtTypeOfDocSubmit" styleClass="ddlist" value="#{MisInfo.typeOfDocSubmit}" size="1" style="#{MisInfo.typeOfDocSubmitChgFlag}">
                            <f:selectItems value="#{MisInfo.typeOfDocSubmitList}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel />
                        <h:outputLabel />
                    </h:panelGrid>
                </rich:panel>
                <rich:panel header="Image Capture" style="text-align:left;">
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="CustImageRow" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblCustImageCapture" styleClass="label" value="Capture Image">
                            <font class="required" style="color:red;">*</font> </h:outputLabel>
                        <h:selectManyListbox id="ddCustImageCapture" value="#{MisInfo.custImageCapture}" size="5" style="width:200px;#{MisInfo.custImageCaptureChgFlag}">
                            <f:selectItems value="#{MisInfo.custImageCaptureList}"/>
                            <a4j:support event="onclick" actionListener="#{MisInfo.onChangeCustImageCapture()}" reRender="txtCustImageSelected"/>
                        </h:selectManyListbox>
                        <h:outputText id="txtCustImageSelected" value="#{MisInfo.custImageSelected}" escape="false" style="white-space: pre-wrap;word-wrap: break-word;#{MisInfo.custImageSelectedChgFlag}" />
                    </h:panelGrid>
                </rich:panel>
                <%--<h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblType" styleClass="label" value="Type"/>
                    <h:inputText  value="#{MisInfo.misType}"id="txtType" maxlength="40"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                    <h:outputLabel id="lblGrpId" styleClass="label" value="Group Id"/>
                    <h:inputText  value="#{MisInfo.groupId}"id="txtGrpId" maxlength="10"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow2" style="width:100%;text-align:left;" styleClass="row2">
                    <<h:outputLabel id="lblMisStatus" styleClass="label" value="Status"/>
                    <h:inputText  value="#{MisInfo.status}"id="txtMisStatus" styleClass="input"onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur" actionListener="#{MisInfo.StatusOn}"focus="StatOnDt" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel id="lblStatOnDt" styleClass="label" value="Status As On"/>
                    <rich:calendar value="#{MisInfo.statusDate}" datePattern="dd/MM/yyyy" id="StatOnDt" jointPoint="top-left" direction="top-right" inputSize="10">
                        <a4j:support event="onchanged" actionListener="#{MisInfo.onchangeStatusAsOn}"focus="ddOccup" reRender="stxtError"/>
                    </rich:calendar>
                    <h:outputLabel id="lblOccup" styleClass="label" value="Occupation"/>
                    <h:selectOneListbox  value="#{MisInfo.misOccupation}"id="ddOccup" style="width:72px;"styleClass="ddlist"size="1">
                        <f:selectItems value="#{MisInfo.misOccupationOption}"/>
                        <a4j:support event="onblur"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow5" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCast" styleClass="label" value="Caste"/>
                    <h:selectOneListbox value="#{MisInfo.misCast}" id="ddCast" styleClass="ddlist" size="1">
                        <f:selectItems value="#{MisInfo.misCastOption}"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblComm" styleClass="label" value="Community"/>
                    <h:selectOneListbox value="#{MisInfo.misCommunity}" id="ddComm" styleClass="ddlist"  size="1">
                        <f:selectItems value="#{MisInfo.misCommunityOption}"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow6" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblHealth" styleClass="label" value="Health Code"/>
                    <h:selectOneListbox value="#{MisInfo.healthCode}" id="ddHealth" style="width:72px;"styleClass="ddlist" size="1">
                        <f:selectItems value="#{MisInfo.healthCodeOption}"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblBussSeg" styleClass="label" value="Bussiness Segment"/>
                    <h:inputText  value="#{MisInfo.businessSegment}"id="txtBussSeg" maxlength="15"styleClass="input" style="120px"onkeydown="this.value=this.value.toUpperCase();"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="misRow7" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblSTurnover" styleClass="label" value="Sales Turnover"/>
                    <h:inputText  value="#{MisInfo.salesTurnOver}"id="txtSTurnover" styleClass="input" style="120px">
                        <a4j:support actionListener="#{MisInfo.onblurSalesTurnOver}" event="onblur" reRender="stxtError"/>
                    </h:inputText>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>--%>
            </h:panelGrid>
        </h:panelGrid>
    </a4j:region>
</h:form>
