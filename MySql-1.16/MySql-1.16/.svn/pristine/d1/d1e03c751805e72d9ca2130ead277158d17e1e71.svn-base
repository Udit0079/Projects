<%-- 
    Document   : accountopen
    Created on : 24 Aug, 2011, 11:40:28 AM
    Author     : Zeeshan Waris
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Registration Form</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                var row;
                function setMask(){
                    jQuery(".calInstDate").mask("39/19/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:keepAlive beanName="AccountOpen"/>
            <a4j:form id="AccountOpen">
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{AccountOpen.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Registration Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AccountOpen.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{AccountOpen.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="display:"/>
                            <h:selectOneListbox id="ddFunction" styleClass="ddlist"  size="1" style="width:80px;" value="#{AccountOpen.function}" >
                                <f:selectItems value="#{AccountOpen.functionList}"/>
                                <a4j:support action="#{AccountOpen.chenageOperation}" event="onblur" oncomplete="setMask();" focus="#{AccountOpen.focusId}"
                                             reRender="acnogenId,lblMsg,lblCustId,lblShareholder,txtCustId,txtShareholder,btnSave,lblShareholder1,ddAccount,txtName,calIntDateOfBirth,
                                             txtFatherHusbandName,ddArea, ddBusinessDesig,lbOccupy,ddOccuoation,PanId,txtPanId" />
                            </h:selectOneListbox>
                            <h:panelGroup layout="block">
                                <h:outputLabel id="lblCustId" styleClass="label" value="Customer Id" style="display:#{AccountOpen.custIdStyle}"/>
                                <h:outputLabel id="lblShareholder" styleClass="label" value="Folio No" style="display:#{AccountOpen.folioNoStyle}"/>
                                <h:outputLabel id="lblShareholder1" styleClass="label" value="Folio No" style="display:#{AccountOpen.verifyStyle}"/>
                            </h:panelGroup>    
                            <h:panelGroup layout="block">
                                <h:inputText id="txtCustId" styleClass="input" maxlength="12" value="#{AccountOpen.custId}" style="display:#{AccountOpen.custIdStyle};width:80px;">
                                    <a4j:support action="#{AccountOpen.getCustomerDetail}" event="onblur" oncomplete="setMask();" focus="calIntDate1"
                                                 reRender="lblMsg,txtName,calIntDateOfBirth,txtFatherHusbandName,txtCustId,txtPanId" />

                                </h:inputText>
                                <h:inputText id="txtShareholder" styleClass="input" maxlength="12" value="#{AccountOpen.folioNo}" style="display:#{AccountOpen.folioNoStyle};width:100px;" onkeyup="this.value = this.value.toUpperCase();" >
                                    <a4j:support action="#{AccountOpen.candidateDeatails}" event="onblur" oncomplete="setMask();" focus="ddPurpose" reRender="lblMsg,mainPanel" />
                                </h:inputText>
                                <h:selectOneListbox id="ddAccount" styleClass="ddlist"  size="1" style="display:#{AccountOpen.verifyStyle};width:100px;" value="#{AccountOpen.folioNumber}" >
                                    <f:selectItems value="#{AccountOpen.accountList}"/>
                                    <a4j:support  action="#{AccountOpen.candidateDeatails}" event="onblur" oncomplete="setMask();" focus="btnSave"                                              
                                                  reRender="lblMsg,mainPanel"/>
                                </h:selectOneListbox>
                            </h:panelGroup>
                            <h:outputLabel id="lblRegisterDt" styleClass="label" value="Reg.Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:inputText id="calIntDate1" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10"  value="#{AccountOpen.registerDt}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support action="#{AccountOpen.getshareValue}" event="onblur" oncomplete="setMask();" reRender="lblMsg,txtShareValue,calIntDateOfBirth,calIntDate1" focus="ddPurpose"/>
                                </h:inputText>
                            </h:panelGrid>     
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel id="lblShareValue" styleClass="label" value="Share Value"/>
                                <h:inputText id="txtShareValue" styleClass="input" maxlength="6" value="#{AccountOpen.shareValue}" style="width:80px;" readonly="true"/>
                                <h:outputLabel id="lblPurpose" styleClass="label" value="Purpose"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddPurpose" styleClass="ddlist"  size="1" style="width:140px;" value="#{AccountOpen.purpose}" >
                                    <f:selectItems value="#{AccountOpen.purposeList}"/>
                                    <a4j:support  action="#{AccountOpen.purposeValueChange}" event="onclick" oncomplete="setMask();" focus="#{AccountOpen.focusId}"                                              
                                                  reRender="idBnkNameList,calIntDateOfBirth,calIntDate1"/>
                                </h:selectOneListbox>     
                                <h:outputLabel id="idBnkName" styleClass="label" value="Bank Name"/>
                                <h:selectOneListbox id="idBnkNameList" styleClass="ddlist"  size="1" style="width:70px;" value="#{AccountOpen.bnkName}" >
                                    <f:selectItems value="#{AccountOpen.bnkNameList}"/>
                                    <a4j:support  action="#{AccountOpen.operationModeChangeByBnkName}" event="onblur" oncomplete="setMask();"                                                   
                                                  reRender="lbBeneciaryacno,lbIBeneficiaryName,lbIfscCode,txtBeneficiaryName,txtIfscCode,lblMsg,Row22" focus="#{AccountOpen.focusId}" />
                                </h:selectOneListbox>         
                            </h:panelGrid>    
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row22" style="width:100%;text-align:left;" styleClass="row1">    
                                <h:outputLabel id="lbBeneciaryacno" styleClass="label" value="Beneficiary A/c No."/>
                                <h:inputText id="txtBeneciaryacno" styleClass="input" maxlength="20" value="#{AccountOpen.beneficiaryAcNo}" style="width:80px;" > 
                                    <a4j:support action="#{AccountOpen.acnoStatusCheck}" event="onblur" oncomplete="setMask();" reRender="lblMsg,txtBeneciaryacno,mainPanel" focus="#{AccountOpen.focusId}" />
                                </h:inputText>
                                <h:outputLabel id="lbIBeneficiaryName" styleClass="label" value="Beneficiary Name"/>
                                <h:inputText id="txtBeneficiaryName" styleClass="input" style="width:140px;"  value="#{AccountOpen.beneficiaryName}" onkeyup="this.value = this.value.toUpperCase();" maxlength="100" disabled="#{AccountOpen.benefNameDisable}"/>                   
                                <h:outputLabel id="lbIfscCode" styleClass="label" value="IFSC Code"/>
                                <h:inputText id="txtIfscCode" styleClass="input" maxlength="11" value="#{AccountOpen.ifscCode}" style="width:80px;" disabled="#{AccountOpen.benefIfscDisable}"/>
                            </h:panelGrid>
                            <rich:panel header="Personal Details" style="text-align:left;">
                                <h:panelGrid  style="width:100%;text-align:left;" >
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblName" styleClass="label" value="Customer Name"/>
                                        <h:outputText id="txtName" styleClass="output" value="#{AccountOpen.firstName}" />
                                        <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date Of Birth"/>
                                        <h:outputText id="calIntDateOfBirth" styleClass="output"  style="width:70px;setMask()" value="#{AccountOpen.dateofBirth}"/>
                                        <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name"/>
                                        <h:outputText id="txtFatherHusbandName" styleClass="output" value="#{AccountOpen.fatherName}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblCategory" styleClass="label" value="Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddCategory" styleClass="ddlist"  size="1" style="width:140px;"  value="#{AccountOpen.category}" >
                                            <f:selectItems value="#{AccountOpen.categoryList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblBusinessDesig" styleClass="label" value="Business/Desig."/>
                                        <h:selectOneListbox id="ddBusinessDesig" styleClass="ddlist"  size="1" style="width:140px;"  value="#{AccountOpen.businessDesig}">
                                            <f:selectItems value="#{AccountOpen.businessDesigList}"/>
                                        </h:selectOneListbox>                                        
                                        <h:outputLabel id="lblFirmHolderName" styleClass="label" value="Firm Holder Name"/>
                                        <h:inputText id="txtFirmHolderName" maxlength="100"  value="#{AccountOpen.firmHolderName}" style="width:140px;" styleClass="input" onkeyup="this.value = this.value.toUpperCase();"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblArea" styleClass="label" value="Area"></h:outputLabel>
                                        <h:selectOneListbox id="ddArea" styleClass="ddlist"  size="1" style="width:140px;"  value="#{AccountOpen.area}">
                                            <f:selectItems value="#{AccountOpen.areaList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel id="lblJoinDt" styleClass="label" value="Joining Date"></h:outputLabel>
                                        <h:inputText id="calJoinDt1" styleClass="input calInstDate" style="width:70px;setMask()" maxlength="10" value="#{AccountOpen.joinDt}" disabled="#{AccountOpen.joinDtDisFlag}">
                                            <f:convertDateTime pattern="dd/MM/yyyy" />
                                            <a4j:support oncomplete="setMask();" reRender="lblMsg,txtShareValue,calIntDateOfBirth,calIntDate1,calJoinDt1"/>
                                        </h:inputText>
                                        <h:outputLabel id="lblSalary" styleClass="label" value="Salary/ Basic Pay"/>
                                        <h:inputText value="#{AccountOpen.salary2}"id="txtSalary" styleClass="input" style="120px" disabled="#{AccountOpen.salDisFlag}"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row14" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblGPay" styleClass="label" value="Grade Pay/ DA"/>
                                        <h:inputText value="#{AccountOpen.gPay}" id="txtGPay" styleClass="input" style="120px" disabled="#{AccountOpen.gPayDisFlag}"/>
                                        <h:outputLabel id='lbOccupy'styleClass="label" value="Occupation" style="display:#{AccountOpen.occupationPanStyle}"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddOccuoation" styleClass="ddlist"  size="1" style="width:140px;display:#{AccountOpen.occupationPanStyle}"  value="#{AccountOpen.occupation}">
                                            <f:selectItems value="#{AccountOpen.occupationList}"/>
                                        </h:selectOneListbox>
                                    <h:outputLabel id="PanId" styleClass="label" value="Pan No."style="display:#{AccountOpen.occupationPanStyle}" />
                                    <h:inputText id="txtPanId" styleClass="input" style="width:70px;display:#{AccountOpen.occupationPanStyle}" maxlength="10"  value="#{AccountOpen.panNo}" onkeyup="this.value = this.value.toUpperCase();"/>    
                                </h:panelGrid>    
                            </h:panelGrid>
                        </rich:panel>
                        <rich:panel header="Nominee Details/Other Details" style="text-align:left;">
                            <h:panelGrid  style="width:100%;text-align:left;" >
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblFirstWitnessName" styleClass="label" value="First Witness Name"/>
                                    <h:inputText id="txtFirstWitnessName" styleClass="input" style="width:150px;" maxlength="50"  value="#{AccountOpen.witnessFirstName}" onkeyup="this.value = this.value.toUpperCase();"/>
                                    <h:outputLabel id="lblFirstWitnessAddress" styleClass="label" value="First Witness Address" />
                                    <h:inputText id="txtFirstWitnessAddress" styleClass="input" style="width:200px;"  value="#{AccountOpen.witnessFirstAddress}" onkeyup="this.value = this.value.toUpperCase();" maxlength="50"/>
                                    <h:outputLabel id="lblSecondWitnessName" styleClass="label" value="Second Witness Name" />
                                    <h:inputText id="txtSecondWitnessName" styleClass="input" style="width:150px;" maxlength="50"  value="#{AccountOpen.witnessSecondName}" onkeyup="this.value = this.value.toUpperCase();"/>    
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">   
                                    <h:outputLabel id="lblSecondWitnessAddress" styleClass="label" value="Second Witness Address" />
                                    <h:inputText id="txtSecondWitnessAddress" styleClass="input" style="width:200px;"  value="#{AccountOpen.witnessSecondAddress}" onkeyup="this.value = this.value.toUpperCase();" maxlength="50"/>
                                    <h:outputLabel id="lblNominee" styleClass="label" value="Nominee Name" />
                                    <h:inputText id="txtNominee" maxlength="50" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" style="width:150px;" value="#{AccountOpen.nomineeName}"/>
                                    <h:outputLabel id="lblNomineeAdd" styleClass="label" value="Nominee Address" />
                                    <h:inputText id="txtNomineeAdd" maxlength="50" value="#{AccountOpen.nomineeAddress}" style="width:200px;" styleClass="input" onkeyup="this.value = this.value.toUpperCase();"/>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid  style="width:100%;text-align:left;" >
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblNomAge" styleClass="label" value="Nominee Dob/Age" ></h:outputLabel>
                                        <h:panelGroup id="dobage">
                                            <h:inputText id="txtNomAge" styleClass="input calInstDate" style="width:70px;" value="#{AccountOpen.nomineeDob}">
                                                <f:convertDateTime pattern="dd/MM/yyyy" />
                                                <a4j:support action="#{AccountOpen.calAge}"  event="onblur" reRender="lblMsg,outDobage" focus="txtNomineeRelationship"/>
                                            </h:inputText>
                                            <h:outputText id="outDobage" styleClass="output" value="#{AccountOpen.nomineeAge}"/>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblNomineeRelationship" styleClass="label" value="Relation" />
                                        <h:inputText id="txtNomineeRelationship"  onkeyup="this.value = this.value.toUpperCase();" maxlength="20" style="width:150px;" styleClass="input" value="#{AccountOpen.nomineeRelation}" />
                                    <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operation Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddOperatingMode" style="width:140px;" styleClass="ddlist"  size="1" value="#{AccountOpen.operationMode}" >
                                            <f:selectItems value="#{AccountOpen.operatingModeList}"/>
                                            <a4j:support   action="#{AccountOpen.operationModeValueChange}" event="onblur" oncomplete="setMask();
                                                           if(#{AccountOpen.operationMode=='1'}){#{rich:element('txtRemarks')}.focus();}else{#{rich:element('txtJointIdFirst')}.focus();}"
                                                           reRender="Row11,lblJointNmaeFirst,txtJointIdFirst,txtJointNmaeFirst,lblJointNmaeSecond,txtJointNmaeSecond" />
                                        </h:selectOneListbox>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblJointNmaeFirst" styleClass="label" value="Jt 1 Customer Id  /Jt. Name 1"/>
                                        <h:panelGroup layout="block">
                                            <h:inputText id="txtJointIdFirst" maxlength="10"  value="#{AccountOpen.jtFirstId}" style="width:60px;" styleClass="input" disabled="#{AccountOpen.jtName1Disable}">
                                                <a4j:support action="#{AccountOpen.getJointNameOne}" event="onblur"  oncomplete="setMask();" reRender="txtJointNmaeFirst,lblMsg" focus="txtJointIdSecond"/>
                                            </h:inputText>
                                            <h:outputText id="txtJointNmaeFirst"  value="#{AccountOpen.jtName1}" styleClass="output"/>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblJointNmaeSecond" styleClass="label" value="Jt 2 Customer Id / Jt. Name 2"/>
                                        <h:panelGroup layout="block">
                                            <h:inputText id="txtJointIdSecond" maxlength="10" value="#{AccountOpen.jtSecondId}" style="width:60px;" styleClass="input" disabled="#{AccountOpen.jtName1Disable}">
                                                <a4j:support action="#{AccountOpen.getJointNameTwo}" event="onblur"  oncomplete="setMask();" reRender="txtJointNmaeSecond,lblMsg" focus="txtRemarks"/>
                                            </h:inputText>   
                                            <h:outputText id="txtJointNmaeSecond" value="#{AccountOpen.jtName2}" styleClass="output"/>
                                        </h:panelGroup>
                                        <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"/>
                                        <h:inputText id="txtRemarks"  onkeyup="this.value = this.value.toUpperCase();" maxlength="60"  style="width:200px;" styleClass="input" value="#{AccountOpen.remark}" />                       
                                    </h:panelGrid>
                                </h:panelGrid>
                            </rich:panel>
                        <rich:panel id="acnogenId"header="Account Generation" style="text-align:left;display:#{AccountOpen.memberStyle}">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="DocRow" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="introdserAcno" styleClass="label" value="Introducer Account" />
                                <h:panelGroup layout="acniId">
                                    <h:inputText id="txtintrodserAcno" styleClass="input" style="width:80px;" maxlength="12" value="#{AccountOpen.intruduser}">
                                    <a4j:support event="onblur" action="#{AccountOpen.onBlurCustName}" reRender="stxtNewAcNo,lblMsg" focus="ddDocName"></a4j:support>
                                    </h:inputText>
                                    <h:outputLabel id="stxtNewAcNo" styleClass="label" value="#{AccountOpen.intruduserName}"style="color:green"></h:outputLabel>
                                </h:panelGroup>
                                <h:outputLabel value="Document Name" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddDocName" value="#{AccountOpen.docName}" styleClass="ddlist" size="1" style="width:80px">
                                        <f:selectItems value="#{AccountOpen.docNameList}"/>
                                    </h:selectOneListbox> 
                                    <h:outputLabel id="docDetail" styleClass="label" value="Document Detail" />
                                    <h:inputText id="txtdocDetail" styleClass="input" style="width:80px;" maxlength="20"  value="#{AccountOpen.docDetail}" onkeyup="this.value = this.value.toUpperCase();"/>       
                                </h:panelGrid>    
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="BrRow" style="width:100%;text-align:left;" styleClass="row1">    
                                    <h:outputText value="Branch" styleClass="label"/>
                                    <h:selectOneListbox id="listbranch" value="#{AccountOpen.branch}" styleClass="ddlist" size="1"  style="width:80px">
                                        <f:selectItems value="#{AccountOpen.branchList}" />
                                    </h:selectOneListbox>
                                    <h:outputLabel value="A/c Classification" styleClass="label"/>
                                    <h:selectOneListbox id="ddClassification" value="#{AccountOpen.classification}" styleClass="ddlist" size="1" style="width:80px">
                                        <f:selectItems value="#{AccountOpen.classificationList}"/>
                                        <a4j:support action="#{AccountOpen.classificationAction}" event="onblur" oncomplete="setMask();" reRender="ddNature,lblMsg"  focus="ddNature"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel value="Account Nature" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddNature" value="#{AccountOpen.acNature}" styleClass="ddlist" size="1" style="width:80px">
                                        <f:selectItems value="#{AccountOpen.acNatureList}"/>
                                        <a4j:support action="#{AccountOpen.getAcTypeForNature}" event="onblur" oncomplete="setMask();" reRender="ddAcType,lblMsg" focus="ddAcType"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="AcTypeRow" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel value="Account Type" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddAcType" value="#{AccountOpen.acType}" styleClass="ddlist" size="1" style="width:80px">
                                        <f:selectItems value="#{AccountOpen.acTypeList}"/>
                                        <a4j:support action="#{AccountOpen.setScheme}" event="onblur" oncomplete="setMask();" reRender="ddSchemeCode,lblMsg"  focus="ddSchemeCode"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel value="Scheme Code" styleClass="label"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddSchemeCode" value="#{AccountOpen.schemeCode}" styleClass="ddlist" size="1" style="width:80px">
                                        <f:selectItems value="#{AccountOpen.schemeCodeList}"/>
                                        <a4j:support event="onblur" oncomplete="setMask();" reRender="ddPriority,lblMsg" focus="ddPriority"/>
                                    </h:selectOneListbox>        
                                <h:outputLabel value="Priority" styleClass="label"/>
                                <h:selectOneListbox id="ddPriority" value="#{AccountOpen.priority}" styleClass="ddlist" size="1" style="width:80px">
                                    <f:selectItems value="#{AccountOpen.priorityList}"/>
                                    <a4j:support action="#{AccountOpen.accGenInfo}" event="onblur" oncomplete="setMask();" reRender="InterestCal,lblMsg"  focus="btnSave"/>
                                </h:selectOneListbox>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="col1,col2" columns="1" id="InterestCal"  width="100%" styleClass="row2">
                                <rich:dataTable value ="#{AccountOpen.acnoGenInfo}"
                                                rowClasses="row1, row2" id = "InterestCalTable" rows="6" columnsWidth="100" rowKeyVar="row" var ="item"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column colspan="10"><h:outputText value="Details"/></rich:column>
                                            <rich:column breakBefore="true"><h:outputText value="S.No"  /> </rich:column>
                                            <rich:column><h:outputText value="Branch" /></rich:column>
                                            <rich:column><h:outputText value="Account Nature" /></rich:column>
                                            <rich:column><h:outputText value="Account Type" /></rich:column>
                                            <rich:column><h:outputText value="Scheme Code" /></rich:column>
                                            <rich:column><h:outputText value="Primary" /></rich:column>
                                            <rich:column><h:outputText value="Account No." /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column style="text-align:center;width:3%;"><h:outputText value="#{item.sNo}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.branch}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.actNature}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.actType}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.schemeCode}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.priority}"/></rich:column>
                                    <rich:column style="text-align:center;"><h:outputText value="#{item.acNo}"/></rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="InterestCalTable" maxPages="20" />
                            </h:panelGrid>
                        </rich:panel>            
                        <%--rich:panel header="" style="text-align:left;">
                            <h:panelGrid  style="width:100%;text-align:left;" >
                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Row12" style="width:100%;text-align:left;" styleClass="row2">
                                     
                                     <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operation Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddOperatingMode" style="width:140px;" styleClass="ddlist"  size="1" value="#{AccountOpen.operationMode}" >
                                        <f:selectItems value="#{AccountOpen.operatingModeList}"/>
                                        <a4j:support   action="#{AccountOpen.operationModeValueChange}" event="onblur" oncomplete="setMask();
                                                       if(#{AccountOpen.operationMode=='1'}){#{rich:element('txtRemarks')}.focus();}else{#{rich:element('txtJointIdFirst')}.focus();}"
                                                       reRender="txtJointNmaeFirst,txtJointNmaeSecond" />
                                    </h:selectOneListbox>
                                    
                                    <h:outputLabel id="lblJointNmaeFirst" styleClass="label" value="Jt 1 Customer Id  /Jt. Name 1"/>
                                    <h:panelGroup layout="block">
                                        <h:inputText id="txtJointIdFirst" maxlength="10"  value="#{AccountOpen.jtFirstId}" style="width:60px;" styleClass="input" disabled="#{AccountOpen.jtName1Disable}">
                                            <a4j:support action="#{AccountOpen.getJointNameOne}" event="onblur"  oncomplete="setMask();" reRender="txtJointNmaeFirst,lblMsg" focus="#{AccountOpen.focusId}"/>
                                        </h:inputText>
                                        <h:outputText id="txtJointNmaeFirst"  value="#{AccountOpen.jtName1}" styleClass="output"/>
                                    </h:panelGroup>
                                    
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col1,col2,col1,col2" columns="4" id="Row13" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblJointNmaeSecond" styleClass="label" value="Jt 2 Customer Id / Jt. Name 2"/>
                                    <h:panelGroup layout="block">
                                        <h:inputText id="txtJointIdSecond" maxlength="10" value="#{AccountOpen.jtSecondId}" style="width:60px;" styleClass="input" disabled="#{AccountOpen.jtName1Disable}">
                                            <a4j:support action="#{AccountOpen.getJointNameTwo}" event="onblur"  oncomplete="setMask();" reRender="txtJointNmaeSecond,lblMsg" focus="#{AccountOpen.focusId}"/>
                                        </h:inputText>   
                                        <h:outputText id="txtJointNmaeSecond" value="#{AccountOpen.jtName2}" styleClass="output"/>
                                    </h:panelGroup>
                                    <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks"/>
                                    <h:inputText id="txtRemarks"  onkeyup="this.value = this.value.toUpperCase();" maxlength="60"  style="width:200px;" styleClass="input" value="#{AccountOpen.remark}" />
                                </h:panelGrid>
                            </h:panelGrid>
                        </rich:panel--%>                                        
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="#{AccountOpen.btnValue}"  action="#{AccountOpen.saveAction}" oncomplete="setMask();"  reRender="lblMsg,mainPanel" focus="btnExit" disabled="#{AccountOpen.saveDisable}"/>
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{AccountOpen.refreshButtonAction}"
                                                oncomplete="setMask();" reRender="mainPanel,lblMsg,btnUpdate" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{AccountOpen.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>