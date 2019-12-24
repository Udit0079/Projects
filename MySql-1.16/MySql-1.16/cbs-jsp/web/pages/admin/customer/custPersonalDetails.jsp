<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<h:form id="custPersonalDetails">
    <a4j:region>
        <h:panelGrid id="persInfoPanel" columns="2" style="width:100%;text-align:center;">
            <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">

                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row0" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblCusttype" styleClass="label" value="Customer Type">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:selectOneListbox id="ddCusttype" styleClass="ddlist" value="#{CustPersonalDetails.custEntityType}" size="1" style="width:140px;#{CustPersonalDetails.custEntityTypeChgFlag}" 
                                        >
                        <f:selectItems value="#{CustPersonalDetails.custEntityTypeList}"/>
                        <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onChangeCustEntityType}" reRender="ddAccountHolderTypeFlag,ddAccountHolderType,
                                     ddAccountType,ddTinIssueingCountry,ddTitle,ddNationality,ddNri,ddConst,ddIncopPlace,Row1,lblDob"/>
                    </h:selectOneListbox>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel id="lbltitle" styleClass="label" value="Title">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:selectOneListbox id="ddTitle" styleClass="ddlist" value="#{CustPersonalDetails.titleType}" size="1" style="#{CustPersonalDetails.titleChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.titleTypeOption}"/>
                        <a4j:support event="onblur" reRender="Row11,ddNationality,ddNri"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCustName" styleClass="label" value="Customer First Name">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:inputText id="txtCustName" styleClass="input" style="120px;#{CustPersonalDetails.custNameChgFlag}" value="#{CustPersonalDetails.custFirstName}" 
                                 maxlength="150" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblMiddleName" styleClass="label" value="Middle Name"/>
                    <h:inputText id="txtMiddleName" styleClass="input" style="100px;#{CustPersonalDetails.middleNameChgFlag}" value="#{CustPersonalDetails.middleName}" 
                                 maxlength="70" onkeydown="this.value=this.value.toUpperCase();" disabled="#{MisInfo.misIndDisable}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblLastName" styleClass="label" value="Last Name"></h:outputLabel>
                    <h:inputText id="txtLastName" value="#{CustPersonalDetails.lastName}" maxlength="70" styleClass="input" style="100px;#{CustPersonalDetails.lastNameChgFlag}"
                                 onkeydown="this.value=this.value.toUpperCase();" disabled="#{MisInfo.misIndDisable}">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row15" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblCustFullName" styleClass="label" value="Customer Full Name" />
                    <h:inputText id="txtCustFullName" styleClass="input" style="color:green;width:250px;" value="#{CustPersonalDetails.custFullName}" 
                                 onkeydown="this.value=this.value.toUpperCase();" disabled="true">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                     <h:outputLabel />
                     <h:outputLabel /> 
                     <h:outputLabel id="lblgstin" styleClass="label" value="GST Identification Number" />
                    <h:inputText id="txtgstin" styleClass="input" style="100px;#{CustPersonalDetails.gstinChgFlag}" value="#{CustPersonalDetails.gstIdentificationNumber}" 
                                 onkeydown="this.value=this.value.toUpperCase();">
                         <a4j:support actionListener="#{CustPersonalDetails.onblurValidateGstin}" event="onblur" reRender="stxtError"/>
                    </h:inputText> 
                </h:panelGrid>


                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblFName" styleClass="label" value="Father First Name">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:inputText id="txtFName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.fatherNameChgFlag}" value="#{CustPersonalDetails.fathername}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblMiddleFName" styleClass="label" value="Middle Name"/>
                    <h:inputText id="txtMiddleFName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.fatherMiddleNameChgFlag}" value="#{CustPersonalDetails.fatherMiddleName}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblLastFName" styleClass="label" value="Last Name"></h:outputLabel>
                    <h:inputText id="txtLastFName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.fatherLastNameChgFlag}" value="#{CustPersonalDetails.fatherLastName}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>


                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMName" styleClass="label" value="Mother First Name">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:inputText value="#{CustPersonalDetails.motherName}" id="txtMName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.motherNameChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblMiddleMName" styleClass="label" value="Middle Name">
                    </h:outputLabel>
                    <h:inputText value="#{CustPersonalDetails.motherMiddleName}" id="txtMiddleMName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.motherMiddleNameChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblLastMName" styleClass="label" value="Last Name"></h:outputLabel>
                    <h:inputText value="#{CustPersonalDetails.motherLastName}" id="txtLastMName" maxlength="70" styleClass="input" style="120px;#{CustPersonalDetails.motherLastNameChgFlag}" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblGender" styleClass="label" value="Gender">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:selectOneListbox id="ddGender" styleClass="ddlist" value="#{CustPersonalDetails.genderType}" size="1" style="#{CustPersonalDetails.genderChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.genderTypeOption}"/>
                        <a4j:support event="onblur"/>
                    </h:selectOneListbox>

                    <h:outputLabel id="lblDob" styleClass="label" value="#{CustPersonalDetails.dobOrIncorpLebal}">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>

                    <rich:calendar id="Dob" value="#{CustPersonalDetails.perDateOfBirth}" datePattern="dd/MM/yyyy" 
                                   jointPoint="top-left" direction="top-right" cellWidth="10" inputSize="10" inputStyle="#{CustPersonalDetails.dobChgFlag}">
                        <a4j:support event="onchanged"/>
                    </rich:calendar>
                        
                    <h:outputLabel id="lblMStatus" styleClass="label" value="Marital Status"/>
                    <h:selectOneListbox id="ddMStatus" styleClass="ddlist" value="#{CustPersonalDetails.maritalType}" size="1" style="#{CustPersonalDetails.maritalStatusChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.maritalTypeOption}"/>
                        <a4j:support event="onclick" actionListener="#{CustPersonalDetails.onChangeMaritalStatus()}" reRender="fatherSpouseGrid"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="fatherSpouseGrid" style="width:100%;text-align:left;" styleClass="row2">
                    <h:panelGroup id="checkBoxPenalGroup" layout="block">
                        <h:selectBooleanCheckbox id="checkPerMAilAddress" value="#{CustPersonalDetails.fatherSpouseFlag}" disabled="#{CustPersonalDetails.fatherSpouseFlagDisable}">
                            <a4j:support actionListener="#{CustPersonalDetails.onChangeFatherSpouseFlag()}" event="onclick" reRender="Row5"/>
                        </h:selectBooleanCheckbox>
                        <h:outputLabel id="lblCheckBox" styleClass="output" value="Identification By Spouse Name?"></h:outputLabel>
                    </h:panelGroup>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblSpouseName" styleClass="label" value="Spouse First Name"/>
                    <h:inputText id="txtSpouseName" styleClass="input" style="100px;#{CustPersonalDetails.spouseChgFlag}" value="#{CustPersonalDetails.spouseName}" maxlength="70" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblSpouseMiddleName" styleClass="label" value="Middle Name"/>
                    <h:inputText id="txtSpouseMiddleName" styleClass="input" style="100px;#{CustPersonalDetails.spouseMiddleNameChgFlag}" value="#{CustPersonalDetails.spouseMiddleName}" maxlength="70" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblSpouseLastName" styleClass="label" value="Last Name"/>
                    <h:inputText id="txtSpouseLastName" styleClass="input" style="100px;#{CustPersonalDetails.spouseLastNameChgFlag}" value="#{CustPersonalDetails.spouseLastName}" maxlength="70" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMaidenName" styleClass="label" value="Maiden Name"/>
                    <h:inputText id="txtMaidenName" styleClass="input" style="100px;#{CustPersonalDetails.maidenChgFlag}" value="#{CustPersonalDetails.maidenName}" maxlength="70" onkeydown="this.value=this.value.toUpperCase();">
                        <a4j:support event="onblur"/>
                    </h:inputText>
                    <h:outputLabel id="lblStaff" styleClass="label" value="Staff"/>
                    <h:selectOneListbox id="ddStaff" styleClass="ddlist" value="#{CustPersonalDetails.staffType}" size="1" style="#{CustPersonalDetails.staffChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.staffTypeOption}"/>
                        <a4j:support event="onchange" actionListener="#{CustPersonalDetails.onblurStaff}" reRender="txtSNo,stxtForStaff,stxtError"/>
                    </h:selectOneListbox>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblSNo" styleClass="label" value="Staff No"/>
                    <h:panelGroup id="panelForStaff" layout="block">
                        <h:inputText id="txtSNo" styleClass="input" style="120px;#{CustPersonalDetails.staffNoChgFlag}" maxlength="10" value="#{CustPersonalDetails.staffNo}" disabled="#{CustPersonalDetails.flagForsave}" />
                        <h:outputText id="stxtForStaff" styleClass="error" value="#{CustPersonalDetails.msgForStaff}"/>
                    </h:panelGroup>
                    <h:outputLabel id="lblNationality" styleClass="label" value="Nationality"><font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:selectOneListbox id="ddNationality" styleClass="ddlist" value="#{CustPersonalDetails.nationality}" size="1" style="width:120px;#{CustPersonalDetails.nationalityChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.nationalityList}"/>
                        <a4j:support event="onblur"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblNri" styleClass="label" value="Residential Status"/>
                    <h:selectOneListbox id="ddNri" styleClass="ddlist" value="#{CustPersonalDetails.nriType}" size="1" disabled="#{CustPersonalDetails.companyDisable}" style="#{CustPersonalDetails.residenceStatusChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.nriTypeOption}"/>
                        <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onChangeResidentialStatus}" 
                                     reRender="popUpNreInfoPanel" 
                                     oncomplete="if(#{CustPersonalDetails.nriType == 'N'})
                                     {#{rich:component('popUpNreInfoPanel')}.show();}else 
                                     {#{rich:component('popUpNreInfoPanel')}.hide();}"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblMobNo" styleClass="label" value="Mobile No"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:panelGroup>
                        <h:inputText id="txtIsdCode"maxlength="3" styleClass="input" style="width:20px;#{CustPersonalDetails.isdCodeChgFlag}" value="#{CustPersonalDetails.isdCode}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:inputText id="txtMobNo"maxlength="10" styleClass="input" style="width:100px;#{CustPersonalDetails.mobileNoChgFlag}" value="#{CustPersonalDetails.mobNo}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGroup>
                    <h:outputLabel id="lblComPref" styleClass="label" value="Communication Preference">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:selectOneListbox id="ddComPref" styleClass="ddlist" style="width:100px;#{CustPersonalDetails.commPrefChgFlag}"size="1" value="#{CustPersonalDetails.commuPreference}">
                        <f:selectItems value="#{CustPersonalDetails.commuPreferenceOption}"/>
                        <a4j:support event="onchanged"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblPanNo" styleClass="label" value="PAN/GIR No">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <h:panelGroup id="panGrp" layout="block">
                        <h:selectOneListbox id="ddPanNoOption" styleClass="ddlist" value="#{CustPersonalDetails.panNoOption}" size="1">
                            <f:selectItems value="#{CustPersonalDetails.panNoOptionList}"/>
                            <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onblurAndValidatePanNo}" reRender="txtPanNo,stxtError"/>
                        </h:selectOneListbox>
                        <h:inputText id="txtPanNo" styleClass="input" style="width:80px;#{CustPersonalDetails.panGirNoChgFlag}" value="#{CustPersonalDetails.panGirNo}" maxlength="10" onkeydown="this.value=this.value.toUpperCase();" disabled="#{CustPersonalDetails.panNoVisibility}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                    </h:panelGroup>
                </h:panelGrid>
            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="RowvID" style="width:100%;text-align:left;" styleClass="row2">
               <h:outputLabel id="lblviD" styleClass="label" value="Virtual Id"><font class="required" style="color:red;">*</font></h:outputLabel>        
               <h:inputText id="txtVid" maxlength="20"  size="25" styleClass="input" style="width:120px;" value="#{CustPersonalDetails.virtualId}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
               <h:outputLabel></h:outputLabel>
               <h:outputText></h:outputText>
               <h:outputLabel></h:outputLabel>
               <h:outputText></h:outputText>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                    <h:outputLabel id="lblMinor" styleClass="label" value="Minor"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:selectOneListbox id="ddMinor" styleClass="ddlist" value="#{CustPersonalDetails.minorType}" size="1" style="#{CustPersonalDetails.minorChgFlag}">
                        <f:selectItems value="#{CustPersonalDetails.minorTypeOption}"/>
                        <a4j:support event="onchange" actionListener="#{CustPersonalDetails.onChangeMinor}" 
                                     reRender="lblGrdCustId,minorPanelGroup,txtGrdCustId,outGrdCustName,lblMinorMjDt,minorMjDt,Row10,lblGrdName"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblGrdCustId" styleClass="label" value="Guardian Customer Id" style="display:#{CustPersonalDetails.disableMinorGrd}"><font class="required" style="color:red;">*</font></h:outputLabel>
                    <h:inputText id="txtGrdCustId" styleClass="input" style="100px;display:#{CustPersonalDetails.disableMinorGrd};#{CustPersonalDetails.grdCustIdChgFlag}" value="#{CustPersonalDetails.guardianCustId}" maxlength="10">
                        <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onblurGuardianCustId}" reRender="stxtError,outGrdCustName"/>
                    </h:inputText>
                    <h:outputLabel id="lblGrdName" styleClass="label" value="Guardian Name" style="display:#{CustPersonalDetails.disableMinorGrd}"/>
                    <h:outputText id="outGrdCustName" styleClass="output" value="#{CustPersonalDetails.guardianCustName}" style="display:#{CustPersonalDetails.disableMinorGrd}"/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row10" 
                             style="width:100%;text-align:left;display:#{CustPersonalDetails.disableMinorGrd}" styleClass="row2">
                    <h:outputLabel id="lblMinorMjDt" styleClass="label" value="Minor Majority Date">
                        <font class="required" style="color:red;">*</font>
                    </h:outputLabel>
                    <rich:calendar id="minorMjDt" value="#{CustPersonalDetails.minorMajorityDate}" datePattern="dd/MM/yyyy" 
                                   jointPoint="top-left" direction="top-right" cellWidth="10" inputSize="10" inputStyle="#{CustPersonalDetails.minorMajorDtChgFlag}">
                        <a4j:support event="onchanged"/>
                    </rich:calendar>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                    <h:outputLabel/>
                </h:panelGrid>
                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row2">
                    <h:outputLabel id="lblAccountHolderTypeFlag" styleClass="label" value="Account Holder Type Flag ">
                    </h:outputLabel>
                    <h:selectOneListbox id="ddAccountHolderTypeFlag" styleClass="ddlist" value="#{CustPersonalDetails.acHolderTypeFlag}" size="1" 
                                        style="width:140px;#{CustPersonalDetails.acHolderTypeFlagChgFlag}" disabled="#{MisInfo.misCompDisable}">
                        <f:selectItems value="#{CustPersonalDetails.acHolderTypeFlagList}"/>
                        <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onChangeAccHolderTypeFlag}" reRender="ddAccountHolderType"/>
                    </h:selectOneListbox>

                    <h:outputLabel id="lblAccountHolderType" styleClass="label" value="Account Holder Type"></h:outputLabel>
                    <h:selectOneListbox id="ddAccountHolderType" styleClass="ddlist" value="#{CustPersonalDetails.acHolderType}" size="1" 
                                        style="width:140px;#{CustPersonalDetails.acHolderTypeChgFlag}" disabled="#{MisInfo.misCompDisable}">
                        <f:selectItems value="#{CustPersonalDetails.acHolderTypeList}"/>
                        <a4j:support event="onblur"  reRender="ddNationality,ddNri"/>
                    </h:selectOneListbox>
                    <h:outputLabel id="lblAccountType" styleClass="label" value="Account Type"></h:outputLabel>
                    <h:selectOneListbox id="ddAccountType" styleClass="ddlist" value="#{CustPersonalDetails.acType}" size="1" 
                                        style="width:140px;#{CustPersonalDetails.acTypeChgFlag}" disabled="#{MisInfo.misIndDisable}">
                        <f:selectItems value="#{CustPersonalDetails.acTypeList}"/>
                        <a4j:support event="onblur" actionListener="#{CustPersonalDetails.onChangeAccType}" reRender="ddLegalPoiDoc,txtIdentityNo,txtIdExpiryDate"/>
                    </h:selectOneListbox>
                </h:panelGrid>
                <rich:panel header="Aadhaar Details Only For LPG Subsidy" style="text-align:left;">
                    <h:panelGrid id="adhDetails" columnClasses="col23,col23,col23,col23,col23,col22" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblRegType" styleClass="label" value="Registration Type"/>
                        <h:selectOneListbox id="ddRegType" styleClass="ddlist" value="#{CustPersonalDetails.regType}" size="1" style="#{CustPersonalDetails.regTypeChgFlag}">
                            <f:selectItems value="#{CustPersonalDetails.regTypeOption}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                         <h:outputLabel id="lblmodeType" styleClass="label" value="Entry Mode"/>
                        <h:selectOneListbox id="ddmodeType" styleClass="ddlist" value="#{CustPersonalDetails.aadhaarMode}" size="1" style="#{CustPersonalDetails.modeTypeChgFlag}">
                            <f:selectItems value="#{CustPersonalDetails.modeTypeOption}"/>
                            <a4j:support event="onblur" actionListener="#{CustPersonalDetails.validatemodeType}" reRender="lblbank,ddbank"/>
                        </h:selectOneListbox>
                         <h:outputLabel id="lblbank" styleClass="label" value="Bank" style="display:#{CustPersonalDetails.bankChgFlag}">
                              <font class="required" style="color:red;">*</font>
                         </h:outputLabel>
                        <h:selectOneListbox id="ddbank" styleClass="ddlist" value="#{CustPersonalDetails.aadhaarBankIin}" size="1" style="display:#{CustPersonalDetails.bankChgFlag}">
                            <f:selectItems value="#{CustPersonalDetails.bankOption}"/>
                            <a4j:support event="onblur"actionListener="#{CustPersonalDetails.validateAadhaarBankType}" reRender="lblbank,ddbank"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="lastGrid" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblAdhaarNo" styleClass="label" value="Aadhaar No"/>
                        <h:inputText id="txtAdhaarNo" styleClass="input" value="#{CustPersonalDetails.adhaarNo}" size="14" maxlength="12" style="#{CustPersonalDetails.adhaarNoChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblLpgNo" styleClass="label" value="LPG No"/>
                        <h:inputText id="txtLpgNo" styleClass="input" value="#{CustPersonalDetails.lpgId}" size="19" maxlength="17" style="#{CustPersonalDetails.lpgNoChgFlag}">
                            <a4j:support event="onblur"/>
                        </h:inputText>
                        <h:outputLabel id="lblMapAcno" styleClass="label" value="Primary A/c"/>
                        <h:inputText id="txtMapAcno" styleClass="input" value="#{CustPersonalDetails.adhaarLpgAcno}" size="14" maxlength="12" style="#{CustPersonalDetails.primaryAcChgFlag}">
                            <a4j:support actionListener="#{CustPersonalDetails.onblurAadhaarDetails}" event="onblur" reRender="stxtError"/>
                        </h:inputText>
                    </h:panelGrid>
                    <h:panelGrid id="mandateDetails" columnClasses="col3,col3,col3,col3,col3,col1" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblMandateFlag" styleClass="label" value="Mandate Flag"/>
                        <h:selectOneListbox id="ddMandateFlag" styleClass="ddlist" value="#{CustPersonalDetails.mandateFlag}" size="1" style="#{CustPersonalDetails.manDtFlagChgFlag}">
                            <f:selectItems value="#{CustPersonalDetails.mandateFlagOption}"/>
                            <a4j:support event="onblur"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblMandateDt" styleClass="label" value="Mandate Date"/>
                        <rich:calendar  value="#{CustPersonalDetails.mandateDt}" datePattern="dd/MM/yyyy" id="calMandateDt" 
                                        jointPoint="top-left" direction="top-right" cellWidth="10" inputSize="10" inputStyle="#{CustPersonalDetails.manDatChgFlag}">
                            <a4j:support event="onchanged" actionListener="#{CustPersonalDetails.onChangeMandateDt}" reRender="stxtError"/>
                        </rich:calendar>
                        <h:outputLabel/>
                        <h:outputLabel/>
                    </h:panelGrid>
                </rich:panel>
            </h:panelGrid>
        </h:panelGrid>
        <rich:modalPanel id="popUpNreInfoPanel" label="Form" width="900" height="400" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
            <f:facet name="header">
                <h:panelGrid style="width:100%;text-align:center;">
                    <h:outputText value="NRE Information" style="text-align:center;"/>
                </h:panelGrid>
            </f:facet>
            <f:facet name="controls">
                <h:outputLink  id="closelinkPanel" onclick="#{rich:component('popUpNreInfoPanel')}.hide()">
                    <h:graphicImage value="/resources/images/close.gif" style="border:0" />
                </h:outputLink>
            </f:facet>  
            <table width="100%">
                <tbody>
                    <tr>
                        <td>
                            <a4j:include viewId="#{CustPersonalDetails.viewIdForNre}" />
                        </td>

                    </tr>
                    <tr>
                        <td align="center" width="50%">
                            <a4j:commandButton id="btnCloseNrePopUp" value="Close"  onclick="#{rich:component('popUpNreInfoPanel')}.hide()" reRender="popUpNreInfoPanel" ajaxSingle="true"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </rich:modalPanel>







        <%--<rich:modalPanel id="popUpMinorInfoPanel" label="Form" width="900" height="400" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpRepPanel')}.hide();">
<f:facet name="header">
    <h:panelGrid style="width:100%;text-align:center;">
        <h:outputText value="Minor Information" style="text-align:center;"/>
    </h:panelGrid>
</f:facet>
<f:facet name="controls">
    <h:outputLink  id="closelink" onclick="#{rich:component('popUpMinorInfoPanel')}.hide()">
        <h:graphicImage value="/resources/images/close.gif" style="border:0" />
    </h:outputLink>
</f:facet>  
<table width="100%">
    <tbody>
        <tr>
            <td>
                <a4j:include viewId="#{CustPersonalDetails.viewIdForMinor}"/>
            </td>
        </tr>
        <tr>
            <td align="center" width="50%">
                <a4j:commandButton id="btnCloseMinorPopUp" value="Close"  onclick="#{rich:component('popUpMinorInfoPanel')}.hide()" reRender="popUpNreInfoPanel" ajaxSingle="true"/>
            </td>
        </tr>
    </tbody>
</table>
</rich:modalPanel>--%>
    </a4j:region>
</h:form>