<%--
    Document   : AccountOpeningRegister
    Created on : May 15, 2010, 10:45:54 AM
    Author     : jitendra chaudhary
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!-- RichFaces tag library declaration -->
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Account Opening Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountOpeningRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Account Opening Register SB/RD"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountOpeningRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanel001" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{AccountOpeningRegister.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="1" id="gridPanel1" width="100%">
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel15" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblCustomerId" styleClass="label" value="Customer Id"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCustomerId" styleClass="input"style="width:70px" value="#{AccountOpeningRegister.customerId}" maxlength="10">
                                    <a4j:support actionListener="#{AccountOpeningRegister.getCustomerIdInformation}"  event="onblur" 
                                                 oncomplete="{#{rich:element('ddNewAccType')}.focus();}" reRender="ddNewAccType,txtPermanentAddress,
                                                 txtPhoneNumber,txtNameo,txtName,ddOccupation,txtIntroducerAccount1,txtNomineeAddress,calNomDateOfBirth,
                                                 txtCustomerId,txtCorrespondenceAddress,stxtMsg,calDateOfBirth,txtPhoneNumber,txtRelationship,
                                                 txtGuardianName,txtNominee,txtPenGirNo,txtRelationshipWithNominee,txtFatherHusbandName,
                                                 ddOperatingMode,txtDocumentDetails,ddDocument,lblOccupation"/>
                                </h:inputText>
                                <h:outputLabel id="label13" styleClass="label" value="New A/C Type" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup  layout="block">
                                    <h:selectOneListbox id="ddNewAccType" styleClass="ddlist" size="1" style="width: 150px" value="#{AccountOpeningRegister.newAccType}">
                                        <f:selectItem itemValue="--Select--"/>
                                        <a4j:support actionListener="#{AccountOpeningRegister.acTypeLostFocus}" event="onblur" reRender="ddSchemeCode,
                                                     gridPanel1m,a22,stxtMsg,label52,ddCategory,ddOccupation,acTypeDescription,ddChq" focus="ddActCatg"/>
                                        <f:selectItems value="#{AccountOpeningRegister.acctTypeOption}" />
                                    </h:selectOneListbox>
                                    <h:outputText id="acTypeDescription" styleClass="output" value="#{AccountOpeningRegister.acTypeDesc}"/>
                                </h:panelGroup>
                                <h:outputLabel id="label12" styleClass="label" value="Account Opening Date" ><font class="required" color="red">*</font></h:outputLabel>
                                <rich:calendar datePattern="dd/MM/yyyy" id="calDate" value="#{AccountOpeningRegister.accOpenDate}" disabled="true" inputSize="10"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1y" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="label9" styleClass="label" value="Name" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup id="pnlName">
                                    <h:inputText id="txtNameo" styleClass="input"style="width:40px" value="#{AccountOpeningRegister.ddtitle}" disabled="true">
                                    </h:inputText>
                                    <h:inputText id="txtName" styleClass="input"style="width:110px" value="#{AccountOpeningRegister.name}" disabled="true">
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name" />
                                <h:inputText id="txtFatherHusbandName" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.fathersName}" disabled="true" >
                                </h:inputText>
                                <h:outputLabel id="lblPermanentAddress" styleClass="label" value="Permanent Address" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtPermanentAddress" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.permanentAdd}" disabled="true">
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel16" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblCorrespondenceAddress" styleClass="label" value="Correspondence Address" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtCorrespondenceAddress" styleClass="input"style="width:150px" value="#{AccountOpeningRegister.correspodenceAdd}" disabled="true">
                                </h:inputText>
                                <h:outputLabel id="lblPhoneNumber" styleClass="label" value="Phone Number" />
                                <h:inputText id="txtPhoneNumber" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.mobileNo}" disabled="true">
                                </h:inputText>
                                <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date Of Birth" />
                                <rich:calendar datePattern="dd/MM/yyyy" id="calDateOfBirth" disabled="true" value="#{AccountOpeningRegister.dob}" inputSize="10"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1actCatg" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="labeAct" styleClass="label" value="Account Category" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddActCatg" styleClass="ddlist" size="1" style="width: 120px" value="#{AccountOpeningRegister.actCategory}">
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{AccountOpeningRegister.actCategoryList}" />
                                    <a4j:support actionListener="#{AccountOpeningRegister.checkActCatg}" event="onblur" reRender="stxtMsg" limitToList="true" focus="ddOccupation"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblChq" styleClass="label" value="Cheque Book"/>
                                <h:selectOneListbox id="ddChq" styleClass="ddlist" size="1" style="width: 120px" value="#{AccountOpeningRegister.chqFacility}">
                                    <f:selectItems value="#{AccountOpeningRegister.chqFacilityList}"/>                                    
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1a" style="height:30px;" styleClass="row2" width="100%">
                                <%--<h:outputLabel id="labelp" styleClass="label" value="Occupation" ><font class="required" color="red">*</font></h:outputLabel>
                                   <h:selectOneListbox id="ddOccupation" styleClass="ddlist" size="1" style="width: 120px" value="#{AccountOpeningRegister.occupations}" disabled="#{AccountOpeningRegister.flag}">
                                     <f:selectItem itemValue="--Select--"/>
                                     <f:selectItems value="#{AccountOpeningRegister.occupationOption}" />
                                     <a4j:support actionListener="#{AccountOpeningRegister.checkOccupation}" event="onblur" reRender="stxtMsg" limitToList="true"/>
                                 </h:selectOneListbox>
                                --%>
                                <%-- Added by Manish Kumar --%>
                                <h:outputLabel id="labelp" styleClass="label" value="Occupation" ></h:outputLabel>
                                <h:outputLabel id="lblOccupation" styleClass="label" value="#{AccountOpeningRegister.occupationDesc}" ></h:outputLabel>
                                <%-- --%>    
                                <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operating Mode" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddOperatingMode" styleClass="ddlist" size="1" style="width:120px" value="#{AccountOpeningRegister.operatingMode}" disabled="#{AccountOpeningRegister.flag}">
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{AccountOpeningRegister.operatingModeOption}" />
                                    <a4j:support actionListener="#{AccountOpeningRegister.getOperatingModeInformation}"  event="onblur"
                                                 oncomplete="if((#{AccountOpeningRegister.operatingMode == 1})) {#{rich:element('txtNominee')}.focus();}
                                                 else if ((#{AccountOpeningRegister.operatingMode == 8})) {#{rich:element('txtNominee')}.focus();}
                                                 else if ((#{AccountOpeningRegister.operatingMode == 9})) {#{rich:element('txtNominee')}.focus();}
                                                 else if ((#{AccountOpeningRegister.operatingMode == 10})) {#{rich:element('txtNominee')}.focus();}
                                                 else if ((#{AccountOpeningRegister.operatingMode == 11})) {#{rich:element('txtNominee')}.focus();}
                                                 else if ((#{AccountOpeningRegister.operatingMode == 13})) {#{rich:element('txtNominee')}.focus();}
                                                 else{#{rich:element('txtJtName1Id1')}.focus();}"
                                                 reRender="ddOperatingMode,txtNominee,txtJtName1Id1,txtJtName2Id2,txtGuardianName,txtRelationship,gridPanel1k,txtHufFamily" 
                                                 limitToList="true"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblPenGirNo" styleClass="label" value="Pan/Gir No."/>
                                <h:inputText id="txtPenGirNo" styleClass="input"style="width:100px" value="#{AccountOpeningRegister.penGir}" disabled="true" >
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1c" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblJtName1" styleClass="label" value="CustomerId/JtName1"/>
                                <h:panelGroup id="jtName1">
                                    <h:inputText id="txtJtName1Id1" styleClass="input"style="width:40px" value="#{AccountOpeningRegister.jointName1Id}"  disabled="#{AccountOpeningRegister.jt1Flag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getJointHolder1formation}"  event="onblur"
                                                     reRender="stxtMsg,txtNominee,txtJtName2Id2,txtCustomerId,txtJtName1,txtJtName1Id1" limitToList="true"
                                                     oncomplete="if((#{AccountOpeningRegister.operatingMode == 5})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 4})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 6})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 12})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 14})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 15})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 16})) {#{rich:element('txtJtName2Id2')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 18})) {#{rich:element('txtHufFamily')}.focus();}
                                                     else{#{rich:element('txtNominee')}.focus();}"
                                                     />
                                    </h:inputText>
                                    <h:inputText id="txtJtName1" styleClass="input"style="width:110px" value="#{AccountOpeningRegister.jointName1}" disabled="true" >
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblJtName2" styleClass="label" value="CustomerId/JtName2"/>
                                <h:panelGroup id="jtName2">
                                    <h:inputText id="txtJtName2Id2" styleClass="input"style="width:40px" value="#{AccountOpeningRegister.jointName2Id}" disabled="#{AccountOpeningRegister.jt2Flag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getJointHolder2formation}"  event="onblur"
                                                     oncomplete="if((#{AccountOpeningRegister.operatingMode == 6})) {#{rich:element('txtJtName3Id3')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 4})) {#{rich:element('txtJtName3Id3')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 14})) {#{rich:element('txtJtName3Id3')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 15})) {#{rich:element('txtJtName3Id3')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 16})) {#{rich:element('txtJtName3Id3')}.focus();}
                                                     else{#{rich:element('txtNominee')}.focus();}"
                                                     reRender="stxtMsg,txtJtName3Id3,txtNominee,txtCustomerId,txtJtName2,txtJtName2Id2" limitToList="true"  />
                                    </h:inputText>
                                    <h:inputText id="txtJtName2" styleClass="input"style="width:80px" value="#{AccountOpeningRegister.jointName2}" disabled="true" >
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblJtName3" styleClass="label" value="CustomerId/JtName3"/>
                                <h:panelGroup id="jtName3">
                                    <h:inputText id="txtJtName3Id3" styleClass="input"style="width:40px" value="#{AccountOpeningRegister.jointName3Id}" disabled="#{AccountOpeningRegister.jt3Flag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getJointHolder3formation}"  event="onblur"
                                                     reRender="stxtMsg,txtNominee,txtJtName4Id4,txtCustomerId,txtJtName3,txtJtName3Id3" limitToList="true"
                                                     oncomplete="if((#{AccountOpeningRegister.operatingMode == 14})) {#{rich:element('txtJtName4Id4')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 4})) {#{rich:element('txtJtName4Id4')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 15})) {#{rich:element('txtJtName4Id4')}.focus();}
                                                     else if ((#{AccountOpeningRegister.operatingMode == 16})) {#{rich:element('txtJtName4Id4')}.focus();}
                                                     else{#{rich:element('txtNominee')}.focus();}"
                                                     />
                                    </h:inputText>
                                    <h:inputText id="txtJtName3" styleClass="input" style="width:80px" value="#{AccountOpeningRegister.jointName3}" disabled="true" >
                                    </h:inputText>
                                </h:panelGroup>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col49" columns="4" id="gridPanel1k" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblJtName4" styleClass="label" value="CustomerId/JtName4"/>
                                <h:panelGroup id="jtName4">
                                    <h:inputText id="txtJtName4Id4" styleClass="input"style="width:40px" value="#{AccountOpeningRegister.jointName4Id}" disabled="#{AccountOpeningRegister.jt4Flag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getJointHolder4formation}"  event="onblur" focus="txtNominee"
                                                     reRender="stxtMsg,txtNominee,txtCustomerId,txtJtName4,txtJtName4Id4" limitToList="true" />
                                    </h:inputText>
                                    <h:inputText id="txtJtName4" styleClass="input" style="width:110px" value="#{AccountOpeningRegister.jointName4}" disabled="true" >
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblHufFamily" styleClass="label" value="HUF Family Detail"/>
                                <h:inputText id="txtHufFamily" styleClass="input" style="width:590px" value="#{AccountOpeningRegister.hufFamily}" disabled="#{AccountOpeningRegister.hufFlag}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="hufFamilyPanel" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblNominee" styleClass="label" value="Nominee"/>
                                <h:inputText id="txtNominee" styleClass="input" style="width:120px" value="#{AccountOpeningRegister.nomineeNm}" disabled="#{AccountOpeningRegister.flag}" onkeyup="this.value=this.value.toUpperCase();" maxlength="255">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurNominee}" event="onblur" reRender="stxtMsg,txtNomineeAddress" focus="txtNomineeAddress"/>
                                </h:inputText>
                                <h:outputLabel id="lblNomineeAddress" styleClass="label" value="Nominee Address"/>
                                <h:inputText id="txtNomineeAddress" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.nomineeAdd}" disabled="#{AccountOpeningRegister.flag}" onkeydown="this.value=this.value.toUpperCase();" maxlength="255"/>
                                <h:outputLabel id="lblNomineedob" styleClass="label" value="Nominee DOB"/>
                                <rich:calendar inputSize="10" datePattern="dd/MM/yyyy" id="calNomDateOfBirth" value="#{AccountOpeningRegister.nomineedob}" disabled="#{AccountOpeningRegister.flag}">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurNomineeDate}"  event="onchanged" focus="txtRelationshipWithNominee"
                                                 reRender="stxtMsg"  />
                                </rich:calendar>
                            </h:panelGrid>    
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1e" style="height:30px;" styleClass="row2" width="100%">
                                <h:outputLabel id="lblRelationshipWithNominee" styleClass="label" value="Relationship With Nominee"/>
                                <h:inputText id="txtRelationshipWithNominee" styleClass="input" style="width:120px" value="#{AccountOpeningRegister.nomineeRelation}" disabled="#{AccountOpeningRegister.flag}" onkeyup="this.value=this.value.toUpperCase();" maxlength="50">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurNomineerelation}" event="onblur"  reRender="stxtMsg" focus="txtGuardianName"/>
                                </h:inputText>
                                <h:outputLabel id="lblGuardianName" styleClass="label" value="Guardian Name"/>
                                <h:inputText id="txtGuardianName" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.guardName}" disabled="#{AccountOpeningRegister.gNm}" onkeyup="this.value=this.value.toUpperCase();" maxlength="40">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurGuardianName}" event="onblur" reRender="stxtMsg" focus="txtRelationship"/>
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1g" style="height:30px;" styleClass="row1" width="100%">
                                <h:outputLabel id="lblRelationship" styleClass="label" value="Relationship"/>
                                <h:inputText id="txtRelationship" styleClass="input"style="width:150px" value="#{AccountOpeningRegister.guardRelationShip}" disabled="#{AccountOpeningRegister.gNm}" onkeyup="this.value=this.value.toUpperCase();" maxlength="30">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurrelationship}" event="onblur" reRender="stxtMsg" focus="ddDocument"/>
                                </h:inputText>
                                <h:outputLabel id="lblDocument" styleClass="label" value="Document" />
                                <h:selectOneListbox id="ddDocument" styleClass="ddlist" size="1" style="width:95px" value="#{AccountOpeningRegister.documents}" disabled="#{AccountOpeningRegister.flag}">
                                    <f:selectItems value="#{AccountOpeningRegister.documentOption}" />
                                </h:selectOneListbox>
                                <h:outputLabel id="lblDocumentDetails" styleClass="label" value="Document Details"/>
                                <h:inputText id="txtDocumentDetails" styleClass="input"style="width:120px" value="#{AccountOpeningRegister.documentDetails}" disabled="#{AccountOpeningRegister.flag}" onkeydown="this.value=this.value.toUpperCase();" maxlength="50">
                                </h:inputText>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1o" style="width:100%;" styleClass="row2">                        
                                <h:outputLabel id="lblIntroducerAccount" styleClass="label" value="Introducer Account"/>
                                <h:panelGroup id="iAcc">
                                    <h:inputText id="txtIntroduceId" styleClass="input"style="width:50px" value="#{AccountOpeningRegister.introduId}" disabled="true" >
                                    </h:inputText>
                                    <h:inputText id="txtIntroducerAccount1" styleClass="input" style="width:100px" value="#{AccountOpeningRegister.oldfullIntroAcno}" maxlength="#{AccountOpeningRegister.acNoMaxLen}" disabled="#{AccountOpeningRegister.flag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getIntroducerInfo}"  event="onblur" oncomplete="if(#{AccountOpeningRegister.newAccType == '09'}){#{rich:element('txtInstAmt')}.focus();} else{#{rich:element('ddSchemeCode')}.focus();}"
                                                     reRender="stxtMsg,stxtAcno,gridPanel1o,txtDocumentDetails,txtInstAmt,ddSchemeCode" limitToList="true"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputText id="stxtAcno" styleClass="input" value="#{AccountOpeningRegister.acno}" />
                                <h:outputText id="stxtUserName" styleClass="input" value="#{AccountOpeningRegister.custName}"/>
                                <h:outputText id="stxtmode" styleClass="input" value="#{AccountOpeningRegister.description}"/>
                                <h:outputLabel/>
                                <h:outputText />
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel1m" style="height:30px;display:#{AccountOpeningRegister.rdRowFlag};" styleClass="row1" width="100%">
                                <h:outputLabel id="lblInstAmt" styleClass="label" value="Inst. Amt" style="display:#{AccountOpeningRegister.amtLblDisFlag};"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtInstAmt" styleClass="input"style="width:70px;display:#{AccountOpeningRegister.amtTxtDisFlag};" value="#{AccountOpeningRegister.rdInstallmentAmt}">
                                    <a4j:support actionListener="#{AccountOpeningRegister.onblurInstAmount}"  event="onblur"
                                                 reRender="txtMatAmt,stxtMsg,txtInstAmt,txtPeriodInMonths" limitToList="true" focus="txtPeriodInMonths"/>
                                </h:inputText>
                                <h:outputLabel id="lblPeriodInMonths" styleClass="label" value="Period (In Months)" style="display:#{AccountOpeningRegister.periodLblDisFlag};" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="txtPeriodInMonths" styleClass="input" style="width:70px;display:#{AccountOpeningRegister.periodTxtDisFlag};" value="#{AccountOpeningRegister.rdPeriodInMonth}">
                                </h:inputText>
                                <h:outputLabel/>
                                <h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid columns="6" id="a22" style="height:30px;border:1px ridge #BED6F8;" styleClass="#{AccountOpeningRegister.lastLineColor}" columnClasses="col3,col3,col3,col3,col3,col1" width="100%">
                                <h:outputLabel id="label14" styleClass="label" value="Scheme Code" ></h:outputLabel>
                                <h:selectOneListbox id="ddSchemeCode" disabled="#{AccountOpeningRegister.fieldDisFlag}" styleClass="ddlist" size="1" style="width: 95px" value="#{AccountOpeningRegister.schemeCode}">
                                    <f:selectItem itemValue="--Select--"/>
                                    <f:selectItems value="#{AccountOpeningRegister.schemeCodeList}" />
                                    <a4j:support actionListener="#{AccountOpeningRegister.setInterestCode}" event="onblur" reRender="ddInterestCode,intTxtRoi,txtMatAmt,stxtMsg,btnSave" focus="btnSave"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="lblIntCodeRoi" styleClass="label" value="Int Code / ROI :" ><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup  id="pg7">
                                    <h:selectOneListbox disabled="#{AccountOpeningRegister.intCodeDisFlag}" id="ddInterestCode" styleClass="ddlist" size="1" style="width: 95px" value="#{AccountOpeningRegister.intCode}">
                                        <f:selectItem itemValue="--Select--"/>
                                        <f:selectItems value="#{AccountOpeningRegister.intCodeList}" />
                                        <a4j:support actionListener="#{AccountOpeningRegister.getLoanRoi}" event="onblur" reRender="intTxtRoi,txtMatAmt,stxtMsg,btnSave" focus="btnSave"/>
                                    </h:selectOneListbox>
                                    <h:inputText id="intTxtRoi" size="6" value="#{AccountOpeningRegister.roi}" styleClass="input" disabled="#{AccountOpeningRegister.roiDisFlag}">
                                        <a4j:support actionListener="#{AccountOpeningRegister.getMaturityAmt}" event="onblur" reRender="txtMatAmt,stxtMsg,btnSave" focus="btnSave"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputLabel id="lblMatAmt" styleClass="label" value="Mat. Amt." style="display:#{AccountOpeningRegister.matAmtLblDisFlag};" />
                                <h:inputText id="txtMatAmt" styleClass="input" style="width:70px;display:#{AccountOpeningRegister.matAmtTxtDisFlag};" value="#{AccountOpeningRegister.matAmt}"/>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer" >
                            <a4j:region id="btnRegion">
                                <h:panelGroup id="btnPanel">
                                    <a4j:commandButton id="btnSave" value="Save" disabled="#{AccountOpeningRegister.saveFlag}"action="#{AccountOpeningRegister.saveData}"
                                                       reRender="acTypeDescription,txtCustomerId,ddNewAccType,calDate,txtFatherHusbandName,txtPhoneNumber,txtNameo,txtName,
                                                       txtCorrespondenceAddress,txtPermanentAddress,ddOccupation,calDateOfBirth,ddOperatingMode,txtPenGirNo,txtRelationship,
                                                       txtNominee,calNomDateOfBirth,txtGuardianName,txtJtName1Id1,txtJtName1,txtJtName2Id2,txtJtName2,txtJtName3Id3,txtJtName3,
                                                       txtJtName4Id4,txtJtName4,txtDocumentDetails,txtIntroduceId,txtIntroducerAccount1,ddDocument,stxtUserName,stxtmode,stxtAcno,
                                                       txtInstAmt,txtPeriodInMonths,txtMatAmt,stxtMsg,txtNomineeAddress,txtRelationshipWithNominee,ddInterestCode,intTxtRoi,txtMatAmt,
                                                       txtInstAmt,txtPeriodInMonths,ddSchemeCode,lblMatAmt,lblPeriodInMonths,lblInstAmt,ddActCatg"/>
                                    <a4j:commandButton id="btnReset" value="Reset" action="#{AccountOpeningRegister.resetAllField}" reRender="acTypeDescription,txtCustomerId,
                                                       ddNewAccType,calDate,txtFatherHusbandName,txtPhoneNumber,txtNameo,txtName,txtCorrespondenceAddress,txtPermanentAddress,
                                                       ddOccupation,calDateOfBirth,ddOperatingMode,txtPenGirNo,txtRelationship,txtNominee,calNomDateOfBirth,txtGuardianName,
                                                       txtJtName1Id1,txtJtName1,txtJtName2Id2,txtJtName2,txtJtName3Id3,txtJtName3,txtJtName4Id4,txtJtName4,txtDocumentDetails,
                                                       txtIntroduceId,txtIntroducerAccount1,ddDocument,stxtUserName,stxtmode,stxtAcno,txtInstAmt,txtPeriodInMonths,txtMatAmt,
                                                       stxtMsg,txtNomineeAddress,txtRelationshipWithNominee,ddInterestCode,intTxtRoi,txtMatAmt,txtInstAmt,txtPeriodInMonths,
                                                       ddSchemeCode,lblMatAmt,lblPeriodInMonths,lblInstAmt,label52,ddCategory,gridPanel1m,ddActCatg,txtHufFamily" 
                                                       focus="txtCustomerId"/>
                                    <a4j:commandButton id="btnExit" value="Exit" action="#{AccountOpeningRegister.exitBtnAction}" reRender="txtCustomerId,ddNewAccType,calDate,
                                                       txtFatherHusbandName,txtPhoneNumber,txtNameo,txtName,txtCorrespondenceAddress,txtPermanentAddress,ddOccupation,calDateOfBirth,
                                                       ddOperatingMode,txtPenGirNo,txtRelationship,txtNominee,calNomDateOfBirth,txtGuardianName,txtJtName1Id1,txtJtName1,
                                                       txtJtName2Id2,txtJtName2,txtJtName3Id3,txtJtName3,txtJtName4Id4,txtJtName4,txtDocumentDetails,txtIntroduceId,
                                                       txtIntroducerAccount1,ddDocument,stxtUserName,stxtmode,stxtAcno,txtInstAmt,txtPeriodInMonths,txtMatAmt,stxtMsg,
                                                       txtNomineeAddress,txtRelationshipWithNominee,ddInterestCode,intTxtRoi,txtMatAmt,txtInstAmt,txtPeriodInMonths,ddSchemeCode,
                                                       lblMatAmt,lblPeriodInMonths,lblInstAmt,ddActCatg"/>
                                </h:panelGroup>
                            </a4j:region>  
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRegion"/>
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
        </body>
    </html>
</f:view>
