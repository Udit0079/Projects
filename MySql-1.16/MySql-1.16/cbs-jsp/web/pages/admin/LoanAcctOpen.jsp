<%--
    Document   : LoanAccountOpen
    Created on : Nov 23, 2010, 2:58:54 PM
    author     : ROHIT KRISHNA GUPTA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Account Opening Register(TL/CA)</title>
            <script type="text/javascript">

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{LoanAccountOpen.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Account Opening Register (TL/CA)"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanAccountOpen.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7;border:1px ridge #BED6F8;">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LoanAccountOpen.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LoanAccountOpen.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="8" id="a5" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                        <h:outputLabel id="lblCustId" styleClass="label" value="Customer ID :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCustId" tabindex="1" size="10" maxlength="10" value="#{LoanAccountOpen.custId}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support actionListener="#{LoanAccountOpen.customerDetail}" event="onblur" oncomplete="
                                             if(#{rich:element('txtCustId')}.value!=''){if(#{LoanAccountOpen.errorMessage=='THIS CUSTOMER ID DOES NOT EXISTS !!!'}){#{rich:element('txtCustId')}.focus();}
                                             else{#{rich:element('ddAcType')}.focus();#{rich:element('txtCustId')}.disabled=true;}}"
                                             reRender="message,errorMessage,a5,a6,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,gpFooter" limitToList="true" />
                            </h:inputText>
                            <h:outputLabel id="lblAcType" styleClass="label" value="A/C. Type/Scheme Code :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="pg9">
                                <h:selectOneListbox id="ddAcType" tabindex="2" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}"  value="#{LoanAccountOpen.acctType}" size="1" style="width:100px">
                                    <f:selectItems value="#{LoanAccountOpen.acctTypeList}" />
                                    <a4j:support actionListener="#{LoanAccountOpen.acTypeLostFocus}" event="onblur"
                                                 oncomplete="if(#{LoanAccountOpen.acctType=='01'}){
                                                 #{rich:element('txtMoratoriumPeriod')}.disabled=true;#{rich:element('txtMoratoriumPeriod')}.value = '0';
                                                 #{rich:element('ddRateCode')}.disabled=true;#{rich:element('ddRateCode')}.value = 'Fl';
                                                 #{rich:element('txtAmtSanctioned')}.disabled=false;#{rich:element('txtAmtSanctioned')}.value = '0';
                                                 #{rich:element('txtPagingFreq')}.disabled=true;#{rich:element('txtPagingFreq')}.value = '0';
                                                 #{rich:element('ddDisburseType')}.disabled=true;#{rich:element('ddDisburseType')}.value = 'M';
                                                 #{rich:element('txtLoanPeriod1')}.disabled=true;#{rich:element('txtLoanPeriod1')}.value = '0';
                                                 #{rich:element('txtLoanPeriod2')}.disabled=true;#{rich:element('txtLoanPeriod2')}.value = '0';
                                                 #{rich:element('ddCalLevel')}.disabled=true;#{rich:element('ddCalLevel')}.value = 'L';}
                                                 else{#{rich:element('txtMoratoriumPeriod')}.disabled=false;#{rich:element('txtMoratoriumPeriod')}.value = '0';
                                                 #{rich:element('ddRateCode')}.disabled=false;#{rich:element('ddRateCode')}.value = '--Select--';
                                                 #{rich:element('txtAmtSanctioned')}.disabled=false;#{rich:element('txtAmtSanctioned')}.value = '0';
                                                 #{rich:element('txtPagingFreq')}.disabled=false;#{rich:element('txtPagingFreq')}.value = '0';
                                                 #{rich:element('ddDisburseType')}.disabled=false;#{rich:element('ddDisburseType')}.value = '--Select--';
                                                 #{rich:element('txtLoanPeriod1')}.disabled=false;#{rich:element('txtLoanPeriod1')}.value = '0';
                                                 #{rich:element('txtLoanPeriod2')}.disabled=false;#{rich:element('txtLoanPeriod2')}.value = '0';
                                                 #{rich:element('ddCalLevel')}.disabled=true;#{rich:element('ddCalLevel')}.value = '--Select--';}"
                                                 focus="ddSchemeCode"
                                                 reRender="message,errorMessage,a6,a14,a15,a21,a17,a22,ddSchemeCode,acTypeDescription" limitToList="true" />
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddSchemeCode" tabindex="3" disabled="#{LoanAccountOpen.fieldDisFlag}" styleClass="ddlist"  value="#{LoanAccountOpen.schemeCode}" size="1" style="width: 80px">
                                    <f:selectItems value="#{LoanAccountOpen.schemeCodeList}" />
                                    <a4j:support actionListener="#{LoanAccountOpen.SetValueAcctToScheme}" event="onblur"
                                                 reRender="message,errorMessage,a20,a21,a22,a23" limitToList="true" focus="txtGrName"/>
                                </h:selectOneListbox>
                                <h:outputText id="acTypeDescription" styleClass="output" value="#{LoanAccountOpen.acTypeDesc}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblAcOpenDt" styleClass="label" value="Account Opening Date :" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calAcOpenDt" value="#{LoanAccountOpen.acOpenDt}" readonly="true" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columns="8" id="a6" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblAppSeqNo" styleClass="label" value="Application Sequence No. :" />
                            <h:selectOneListbox id="ddAppSeqNo" tabindex="4" disabled="#{LoanAccountOpen.flag1}" styleClass="ddlist"  value="#{LoanAccountOpen.appSeqNo}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.appSeqNoList}" />
                                <a4j:support actionListener="#{LoanAccountOpen.appSeqNoLostFocus}" event="onblur" focus="ddTitle"
                                             reRender="message,errorMessage,a6,a21" limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblName" styleClass="label" value="Name :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="pg10">
                                <%--
                                <h:selectOneListbox id="ddTitle" tabindex="5" disabled="#{LoanAccountOpen.titleFlag}" styleClass="ddlist"  value="#{LoanAccountOpen.title}" size="1" style="width: 60px">
                                    <f:selectItems value="#{LoanAccountOpen.titleList}" />
                                </h:selectOneListbox>
                                --%>
                                <h:inputText id="txtName" tabindex="6" maxlength="50" size="20" disabled="#{LoanAccountOpen.custNameFlag}" value="#{LoanAccountOpen.custName}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblFatherName" styleClass="label" value="Father/Husband Name :"/>
                            <h:inputText id="txtFatherName" maxlength="50" tabindex="7" size="20" disabled="#{LoanAccountOpen.fatherNameFlag}" value="#{LoanAccountOpen.fatherHusName}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a11" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblPermaAdd" styleClass="label" value="Permanent Address :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtPermaAdd" maxlength="100" tabindex="8" disabled="#{LoanAccountOpen.perAddFlag}" size="20" value="#{LoanAccountOpen.perAdd}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblCorrAdd" styleClass="label" value="Correspondence Address :" style=""><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCorrAdd" maxlength="100" tabindex="9" size="20" disabled="#{LoanAccountOpen.corAddFlag}" value="#{LoanAccountOpen.corAdd}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support actionListener="#{LoanAccountOpen.corrAddressLostFocus}" event="onblur" focus="txtPhNo"
                                             reRender="message,errorMessage,txtCorrAdd" limitToList="true" />
                            </h:inputText>
                            <h:outputLabel id="lblPhNo" styleClass="label" value="Phone No. :" style=""/>
                            <h:inputText id="txtPhNo" maxlength="100" tabindex="10" size="20" disabled="#{LoanAccountOpen.phoneFlag}" value="#{LoanAccountOpen.phNo}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a12" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblPanNo" styleClass="label" value="Pan No./Gir No. :" />
                            <h:inputText id="txtPanNo" maxlength="20" tabindex="11" disabled="#{LoanAccountOpen.panFlag}" size="20" value="#{LoanAccountOpen.panNo}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblDob" styleClass="label" value="Date Of Birth :" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="12" disabled="#{LoanAccountOpen.dobFlag}" id="calDob" value="#{LoanAccountOpen.dob}" inputSize="10"/>
                            <%--
                            <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOccupation" tabindex="13" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.occupation}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.occupationList}" />
                            </h:selectOneListbox>
                            --%>
                            <%-- Added By Manish --%>
                            <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation :" ></h:outputLabel>
                            <h:outputLabel id="lblOccupationId" styleClass="label" value="#{LoanAccountOpen.occupationDesc}" ></h:outputLabel>
                            <%-- --%>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a13" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblGrName" styleClass="label" value="Guardian Name :" />
                            <h:inputText id="txtGrName" maxlength="40" tabindex="14" disabled="#{LoanAccountOpen.fieldDisFlag}" size="20" value="#{LoanAccountOpen.grName}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblGrRelationship" styleClass="label" value="Gr. Relationship :" />
                            <h:inputText id="txtGrRelationship" maxlength="30" tabindex="15" disabled="#{LoanAccountOpen.fieldDisFlag}" size="20" value="#{LoanAccountOpen.grRelationship}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblOprMode" styleClass="label" value="Operation Mode :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOprMode" tabindex="16" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.oprMode}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.oprModeList}" />
                                <a4j:support actionListener="#{LoanAccountOpen.operationModeLostFocus}" event="onblur" oncomplete="if(#{rich:element('ddOprMode')}.value == '1'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '4'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '8'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '7'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '9'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '10'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '11'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOprMode')}.value == '13'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else{#{rich:element('txtCustIdJtName1')}.focus();}"
                                             reRender="message,errorMessage,a13,a14,a15" limitToList="true" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a14" style="height:30px;text-align:left;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblJtName1" styleClass="label" value="#{LoanAccountOpen.jtLabel1}" />
                            <h:panelGroup id="pg1">
                                <h:inputText id="txtCustIdJtName1" maxlength="10" tabindex="17" size="5" disabled="#{LoanAccountOpen.jtName1DisFlag}" value="#{LoanAccountOpen.jtName1CustId}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{LoanAccountOpen.jtName1Detail}" event="onblur" oncomplete="if(#{LoanAccountOpen.errorMessage=='JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='THIS CUSTOMER ID DOES NOT EXISTS !!!'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                 else if(#{LoanAccountOpen.jtName2DisFlag=='false'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                 else if(#{LoanAccountOpen.hufFlag=='false'}){#{rich:element('txtHufFamily')}.focus();}
                                                 else{#{rich:element('txtNominee')}.focus();}"
                                                 reRender="message,errorMessage,a14" limitToList="true" />
                                </h:inputText>
                                <h:inputText id="txtJtName1" maxlength="40" tabindex="18" disabled="#{LoanAccountOpen.flag2}" size="15" value="#{LoanAccountOpen.jtName1}" onblur="this.value = this.value.toUpperCase();" styleClass="input" />
                            </h:panelGroup>
                            <h:outputLabel id="lblJtName2" styleClass="label" value="#{LoanAccountOpen.jtLabel2}" />
                            <h:panelGroup id="pg2">
                                <h:inputText id="txtCustIdJtName2" maxlength="10" tabindex="19" size="5" disabled="#{LoanAccountOpen.jtName2DisFlag}" value="#{LoanAccountOpen.jtName2CustId}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{LoanAccountOpen.jtName2Detail}" event="onblur" oncomplete="if(#{LoanAccountOpen.errorMessage=='JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='THIS CUSTOMER ID DOES NOT EXISTS !!!'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                 else if(#{LoanAccountOpen.jtName3DisFlag=='false'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                 else{#{rich:element('txtNominee')}.focus();}"
                                                 reRender="message,errorMessage,a14" limitToList="true" />
                                </h:inputText>
                                <h:inputText id="txtJtName2" maxlength="40" tabindex="20" disabled="#{LoanAccountOpen.flag2}" size="15" value="#{LoanAccountOpen.jtName2}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblJtName3" styleClass="label" value="#{LoanAccountOpen.jtLabel3}" />
                            <h:panelGroup id="pg3">
                                <h:inputText id="txtCustIdJtName3" maxlength="10" tabindex="21" size="5" disabled="#{LoanAccountOpen.jtName3DisFlag}" value="#{LoanAccountOpen.jtName3CustId}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{LoanAccountOpen.jtName3Detail}" event="onblur" oncomplete="if(#{LoanAccountOpen.errorMessage=='JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='THIS CUSTOMER ID DOES NOT EXISTS !!!'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                 else if(#{LoanAccountOpen.jtName4DisFlag=='false'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                 else{#{rich:element('txtNominee')}.focus();}"
                                                 reRender="message,errorMessage,a14,a15" limitToList="true" />
                                </h:inputText>
                                <h:inputText id="txtJtName3" maxlength="40" tabindex="22" disabled="#{LoanAccountOpen.flag2}" size="12" value="#{LoanAccountOpen.jtName3}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="4" id="a15" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col49" width="100%">
                            <h:outputLabel id="lblJtName4" styleClass="label" value="#{LoanAccountOpen.jtLabel4}" />
                            <h:panelGroup layout="block" id="pg4">
                                <h:inputText id="txtCustIdJtName4" maxlength="10" tabindex="23" size="5" disabled="#{LoanAccountOpen.jtName4DisFlag}" value="#{LoanAccountOpen.jtName4CustId}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support actionListener="#{LoanAccountOpen.jtName4Detail}" event="onblur" oncomplete="if(#{LoanAccountOpen.errorMessage=='JOINT NAME CUSTOMER ID CANNOT BE SAME AS CUSTOMER ID FOR WHICH YOU ARE OPENING ACCOUNT !!!'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='THIS CUSTOMER ID DOES NOT EXISTS !!!'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                 else{#{rich:element('txtNominee')}.focus();}"
                                                 reRender="message,errorMessage,a15,a14" limitToList="true" />
                                </h:inputText>
                                <h:inputText id="txtJtName4" maxlength="40" tabindex="24" disabled="#{LoanAccountOpen.flag2}" size="15" value="#{LoanAccountOpen.jtName4}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblHufFamily" styleClass="label" value="HUF Family Detail"/>
                            <h:inputText id="txtHufFamily" styleClass="input" tabindex="25" style="width:590px" value="#{LoanAccountOpen.hufFamily}" disabled="#{LoanAccountOpen.hufFlag}">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a24" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblNominee" styleClass="label" value="Nominee :" />
                            <h:inputText id="txtNominee" maxlength="255" tabindex="26" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.nominee}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" >
                                <a4j:support actionListener="#{LoanAccountOpen.nomineeDetailValidation}" event="onblur" oncomplete="if(#{LoanAccountOpen.nomDisFlag=='false'}){#{rich:element('txtNomRelationship')}.focus();}else if(#{LoanAccountOpen.nomDisFlag=='true'}){#{rich:element('ddActCatg')}.focus();}"
                                             reRender="message,errorMessage,a15,a16,a24" limitToList="true" />
                            </h:inputText>
                            <h:outputLabel id="lblNomRelationship" styleClass="label" value="Nominee Relationship :" />
                            <h:inputText id="txtNomRelationship" maxlength="50" tabindex="27"  disabled="#{LoanAccountOpen.nomDisFlag}" size="20" value="#{LoanAccountOpen.nomRelationship}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblChq" styleClass="label" value="Cheque Book"/>
                            <h:selectOneListbox id="ddChq" styleClass="ddlist" size="1" style="width: 120px" value="#{LoanAccountOpen.chqFacility}">
                                <f:selectItems value="#{LoanAccountOpen.chqFacilityList}"/>                                    
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a16" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblNomineeAdd" styleClass="label" value="Nominee Address :" />
                            <h:inputText id="txtNomineeAdd" maxlength="255" tabindex="28" size="20" disabled="#{LoanAccountOpen.nomDisFlag}" value="#{LoanAccountOpen.nomineeAdd}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblNomDob" styleClass="label" value="Nominee DOB :" />
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="29" id="calNomDob" disabled="#{LoanAccountOpen.nomDisFlag}" value="#{LoanAccountOpen.nomineeDob}" ondateselect="#{rich:element('txtMoratoriumPeriod')}.focus();" inputSize="10"/>
                            <h:outputLabel id="labeAct" styleClass="label" value="Account Category" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddActCatg" tabindex="30" styleClass="ddlist" size="1" style="width: 120px" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.actCategory}">
                                <f:selectItems value="#{LoanAccountOpen.actCategoryList}" />
                                <a4j:support actionListener="#{LoanAccountOpen.checkActCatg}" event="onblur" reRender="message,errorMessage,a16,a17,a18,a24" limitToList="true" 
                                             oncomplete="if(#{LoanAccountOpen.acctType=='01'}){#{rich:element('txtIntroAcNo')}.focus();}else{#{rich:element('txtMoratoriumPeriod')}.focus();}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a17" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblMoratoriumPeriod" styleClass="label" value="Moratorium Period (In Months) :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtMoratoriumPeriod" tabindex="31" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.moratoriumPeriod}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:outputLabel id="lblRateCode" styleClass="label" value="Rate Code :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddRateCode" tabindex="32" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}"  value="#{LoanAccountOpen.rateCode}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.rateCodeList}" />
                                <a4j:support actionListener="#{LoanAccountOpen.rateCodeComboLostFocus}" oncomplete="if(#{rich:element('ddRateCode')}.value == 'Fi'){#{rich:element('txtPagingFreq')}.focus();}else{#{rich:element('txtIntroAcNo')}.focus();}" event="onblur"
                                             reRender="message,errorMessage,a17,a18" limitToList="true" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblPagingFreq" styleClass="label" value="Pagging Frequency(In Months) :" />
                            <h:inputText id="txtPagingFreq" tabindex="33" size="20" disabled="#{LoanAccountOpen.pagFreqDisFlag}" value="#{LoanAccountOpen.paggingFrequence}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a18" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblIntroducerAcNo" styleClass="label" value="Cust ID / Introducer Account :"/>
                            <h:inputText id="txtIntroCustId" tabindex="34" size="20" disabled="#{LoanAccountOpen.introCustIdFlag}" value="#{LoanAccountOpen.introCustId}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                            <h:inputText id="txtIntroAcNo" tabindex="35" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.oldintroAcNo}" styleClass="input">
                                <a4j:support actionListener="#{LoanAccountOpen.introducerAccountDetail}" event="onblur" focus="ddDocName"
                                             reRender="message,errorMessage,a18" limitToList="true" />
                            </h:inputText>
                            <h:outputText id="stxtIntroNAme" styleClass="output" value="#{LoanAccountOpen.introducerName}"/>
                            <h:outputText id="stxtIntroAcNO" styleClass="output" value="#{LoanAccountOpen.introducerAcNo}"/>
                            <h:outputText id="stxtIntroAcStatus" styleClass="output" value="#{LoanAccountOpen.introducerAcStatus}"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a19" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblDocName" styleClass="label" value="Document Name :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDocName" tabindex="36" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.docName}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.docNameList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblDocDetail" styleClass="label" value="Document Detail :" />
                            <h:inputText id="txtDocDetail" maxlength="50" tabindex="37" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.docDetail}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                <a4j:support actionListener="#{LoanAccountOpen.docDetail}" event="onblur" focus="ddDisburseType"
                                             reRender="message,errorMessage,txtDocDetail" limitToList="true" />
                            </h:inputText>
                            <h:outputLabel id="lblSplInstruction" styleClass="label" value="Special Instruction :" />
                            <h:inputText id="txtSplInstruction" maxlength="150" tabindex="38" disabled="#{LoanAccountOpen.fieldDisFlag}" size="20" value="#{LoanAccountOpen.specialInst}" onblur="this.value = this.value.toUpperCase();" styleClass="input"/>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a20" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblDisburseType" styleClass="label" value="Disbursement Type :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDisburseType" tabindex="39" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.disburseType}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.disburseTypeList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCalMethod" styleClass="label" value="Interest Calculation Method :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalMethod" tabindex="40" styleClass="ddlist" disabled="#{LoanAccountOpen.disableOnSchemeCode}" value="#{LoanAccountOpen.calMethod}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.calMethodList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblIntAppFreq" styleClass="label" value="Interest Application Frequency :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="pg5">
                                <h:selectOneListbox id="ddIntAppFreq1" tabindex="41" styleClass="ddlist" disabled="#{LoanAccountOpen.disableOnSchemeCode}" value="#{LoanAccountOpen.interestAppFreq1}" size="1" style="width: 70px">
                                    <f:selectItems value="#{LoanAccountOpen.interestAppFreqList1}" />
                                    <a4j:support actionListener="#{LoanAccountOpen.intAppFrequency1LostFocus}" event="onblur" oncomplete="if(#{rich:element('ddIntAppFreq1')}.value == 'C'){#{rich:element('ddIntAppFreq2')}.focus();}else{#{rich:element('txtAmtSanctioned')}.focus();}"
                                                 reRender="message,errorMessage,a20" limitToList="true" />
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddIntAppFreq2" tabindex="42" styleClass="ddlist" disabled="#{LoanAccountOpen.intAppFrqDisFlag}" value="#{LoanAccountOpen.interestAppFreq2}" size="1" style="width: 70px">
                                    <f:selectItems value="#{LoanAccountOpen.interestAppFreqList2}" />
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a21" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblAmtSanctioned" styleClass="label" value="#{LoanAccountOpen.amtSancLable}" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtAmtSanctioned" tabindex="43" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.limit}" styleClass="input"/>
                            <h:outputLabel id="lblSancDate" styleClass="label" value="Sanction Date :" ><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" tabindex="44" disabled="#{LoanAccountOpen.sancDtDisFlag}" id="calSancDate" value="#{LoanAccountOpen.sancDate}" inputSize="10" ondateselect="if(#{LoanAccountOpen.acNature=='TL'}){#{rich:element('txtSubsidyAmt')}.focus();}else{#{rich:element('txtLoanPeriod1')}.focus();}"/>
                            <h:outputLabel id="lblSubsidyAmt" styleClass="label" value="Subsidy Amount :" rendered="#{LoanAccountOpen.acNature=='TL'}"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtSubsidyAmt" tabindex="45" size="20" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.subsidyAmt}" styleClass="input" rendered="#{LoanAccountOpen.acNature=='TL'}"/>
                            <h:outputLabel id="lblHide5" value="" />
                            <h:outputLabel id="lblHide6" value="" />
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a22" style="height:30px;border:1px ridge #BED6F8;" styleClass="row1" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblLoanPeriod" styleClass="label" value="Loan Period In MM/DD :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup  id="pg6">
                                <h:inputText id="txtLoanPeriod1" tabindex="46" size="8" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.loanPeriodMm}" styleClass="input"/>
                                <h:inputText id="txtLoanPeriod2" tabindex="47" size="8" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.loanPeriodDd}" styleClass="input"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblIntCodeRoi" styleClass="label" value="Int Code / ROI :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup  id="pg7">
                                <h:selectOneListbox id="ddIntCode" tabindex="48" disabled="#{LoanAccountOpen.fieldDisFlag}" styleClass="ddlist"  value="#{LoanAccountOpen.intCode}" size="1" style="width: 70px">
                                    <f:selectItems value="#{LoanAccountOpen.intCodeList}" />
                                    <a4j:support actionListener="#{LoanAccountOpen.getLoanRoi}" event="onblur" oncomplete="if(#{LoanAccountOpen.errorMessage=='ROI DOES NOT EXISTS,PLEASE FILL ROI.'}){#{rich:element('txtRoi')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='PLEASE SELECT INTEREST CODE.'}){#{rich:element('ddIntCode')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='PLEASE ENTER LIMIT / AMOUNT SANCTIONED.'}){#{rich:element('txtAmtSanctioned')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='PLEASE ENTER VALID LIMIT / AMOUNT SANCTIONED.'}){#{rich:element('txtAmtSanctioned')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='INVALID LIMIT / AMOUNT SANCTIONED.PLEASE FILL THE LIMIT CORRECTLY.'}){#{rich:element('txtAmtSanctioned')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='PLEASE FILL THE LIMIT UPTO TWO DECIMAL PLACES.'}){#{rich:element('txtAmtSanctioned')}.focus();}
                                                 else if(#{LoanAccountOpen.errorMessage=='LIMIT / AMOUNT SANCTIONED CANNOT BE NEGATIVE !!!'}){#{rich:element('txtAmtSanctioned')}.focus();}
                                                 else{#{rich:element('txtAcPreferCrDr1')}.focus();}"
                                                 reRender="message,errorMessage,a21,a22,pg7,stxtApplicableRoi" limitToList="true" />
                                </h:selectOneListbox>
                                <h:inputText id="txtRoi" tabindex="49" size="7" disabled="#{LoanAccountOpen.roiDisFlag}" value="#{LoanAccountOpen.roi}" styleClass="input"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblAcPreferCrDr" styleClass="label" value="A/C Preferable Dr/Cr :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup  id="pg8">
                                <h:inputText id="txtAcPreferCrDr1" tabindex="50" size="8" disabled="#{LoanAccountOpen.txtAcPrefDrDisFlag}" value="#{LoanAccountOpen.acPreferCrDr1}" styleClass="input"/>
                                <h:inputText id="txtAcPreferCrDr2" tabindex="51" size="8" disabled="#{LoanAccountOpen.txtAcPrefCrDisFlag}" value="#{LoanAccountOpen.acPreferCrDr2}" styleClass="input">
                                    <a4j:support event="onblur" actionListener="#{LoanAccountOpen.printApplicableRoi}" focus="ddIntOption" reRender="stxtApplicableRoi,errorMessage,message"/>
                                </h:inputText>
                                <h:outputText id="stxtApplicableRoi" styleClass="output" value="#{LoanAccountOpen.applicableRoi}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columns="6" id="a23" style="height:30px;border:1px ridge #BED6F8;" styleClass="row2" columnClasses="col3,col3,col3,col3,col3,col3" width="100%">
                            <h:outputLabel id="lblIntOption" styleClass="label" value="Interest Option :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddIntOption" tabindex="52" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldDisFlag}" value="#{LoanAccountOpen.intOpt}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.intOptList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCalLevel" styleClass="label" value="Calculation Level :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalLevel" tabindex="53" styleClass="ddlist" disabled="#{LoanAccountOpen.fieldCalLevelDisFlag}" value="#{LoanAccountOpen.calLevel}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.calLevelList}" />
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCalOn" styleClass="label" value="Calculation On :" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCalOn" tabindex="54" styleClass="ddlist" disabled="#{LoanAccountOpen.disableOnSchemeCode}" value="#{LoanAccountOpen.calOn}" size="1" style="width: 121px">
                                <f:selectItems value="#{LoanAccountOpen.calOnList}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columns="3" columnClasses="col10,col2,col10" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <%--<h:panelGroup id="imageMsgPanel" style="text-align:left;" layout="block">
                                <h:outputText id="stxtInstruction" styleClass="output" value="F4 - Customer Id View" style="color:blue;"/>
    </h:panelGroup>--%>
                            <h:panelGroup layout="block" style="width:100%;text-align:center;">
                                <a4j:commandButton id="btnSave" tabindex="55" value="Save" disabled="#{LoanAccountOpen.fieldDisFlag}" oncomplete="#{rich:component('savePanel')}.show()" reRender="message,errorMessage,a5,a6,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,gpFooter"/>
                                <a4j:commandButton id="btnRefresh" tabindex="56" value="Refresh" action="#{LoanAccountOpen.resetForm}" reRender="acTypeDescription,message,errorMessage,a5,a6,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,gpFooter" focus="txtCustId"/>
                                <a4j:commandButton id="btnExit" tabindex="57" value="Exit" action="#{LoanAccountOpen.exitBtnAction}" reRender="message,errorMessage" />
                            </h:panelGroup>
                            <%--<h:panelGroup id="hidePanel1" layout="block">
                                <h:outputText id="hideText"/>
    </h:panelGroup>--%>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                    <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <f:facet name="header">
                            <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                        </f:facet>
                    </rich:modalPanel>

                <%--<a4j:region id="jsFuncRegion">
                    <a4j:jsFunction name="showCustIdDetail" actionListener="#{LoanAccountOpen.shortcutKeyMethod}" oncomplete="#{rich:component('custIdGridView')}.show();" reRender="custIdGridView,jsFuncRegion,table,taskList"/>
                </a4j:region>
                <a4j:status for="jsFuncRegion" onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
</rich:modalPanel>--%>
            </a4j:form>
            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('btnYes')}.focus();">
                <f:facet name="header">
                    <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
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
                                    <a4j:commandButton value="Yes" id="btnYes" ajaxSingle="true" action="#{LoanAccountOpen.saveDetail}"
                                                       oncomplete="#{rich:component('savePanel')}.hide();" reRender="message,errorMessage,a5,a6,a11,a12,a13,a14,a15,a16,a17,a18,a19,a20,a21,a22,a23,a24,gpFooter" focus="btnRefresh"/>
                                </td>
                                <td align="center" width="50%">
                                    <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </h:form>
            </rich:modalPanel>
            <%--<rich:modalPanel id="custIdGridView" width="500" height="500" style="align:right" autosized="true" onshow="#{rich:element('btnCloseCustId')}.focus();">
    <f:facet name="header">
        <h:outputText value="Customer IDs which are generated today."/>
    </f:facet>
    <f:facet name="controls">
        <h:panelGroup>
            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
            <rich:componentControl for="custIdGridView" attachTo="closelink1" operation="hide" event="onclick"/>
        </h:panelGroup>
    </f:facet>
    <a4j:form>
        <h:panelGrid columnClasses="vtop" columns="1" id="table" style="width:100%;text-align:center;" styleClass="row2">
            <a4j:region>
                <rich:dataTable value="#{LoanAccountOpen.gridDetail}" var="dataItem"
                                rowClasses="gridrow1,gridrow2" id="taskList" rows="10" columnsWidth="100" rowKeyVar="row"
                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                    <f:facet name="header">
                        <rich:columnGroup>
                            <rich:column breakBefore="true"><h:outputText value="Customer Id" /></rich:column>
                            <rich:column><h:outputText value="Customer Name" /></rich:column>
                            <rich:column><h:outputText value="Creation Date" /></rich:column>
                            <rich:column><h:outputText value="Created By" /></rich:column>
                        </rich:columnGroup>
                    </f:facet>
                    <rich:column><h:outputText value="#{dataItem.custId}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.custName}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.creationDate}" /></rich:column>
                    <rich:column><h:outputText value="#{dataItem.createdBy}" /></rich:column>
                </rich:dataTable>
                <rich:datascroller align="left" for="taskList" maxPages="20" />
            </a4j:region>
        </h:panelGrid>
        <h:panelGrid id="custIdFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
            <h:panelGroup id="custidViewBtnPanel">
                <a4j:commandButton id="btnCloseCustId" value="Close" onclick="#{rich:component('custIdGridView')}.hide(); return false;" focus="txtCustId"/>
            </h:panelGroup>
        </h:panelGrid>
    </a4j:form>
</rich:modalPanel>
<rich:hotKey key="F4" handler="showCustIdDetail();"/>--%>
        </body>
    </html>
</f:view>
