<%-- 
    Document   : FDAccountOpening
    Created on : 22 Nov, 2010, 3:20:59 PM
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
            <title>FD Account Opening</title>
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{FDAccountOpening.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="FD Account Opening"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FDAccountOpening.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columnClasses="col2,col7" columns="2" id="msgRow" style="width:100%;text-align:center;" styleClass="row2">
                            <h:outputLabel id="lblMsg" styleClass="error" value="#{FDAccountOpening.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblCustomerID" styleClass="label" value="Customer ID"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCustomerID" styleClass="input" maxlength="10" value="#{FDAccountOpening.customerId}" style="width:80px;">
                                <a4j:support   actionListener="#{FDAccountOpening.getAccountDetails}" event="onblur"
                                               reRender="leftPanel,btnSave,txtCustomerID" focus="ddACType"/>
                            </h:inputText>
                            <h:outputLabel id="lblACType" styleClass="label" value="A/C Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddACType" styleClass="ddlist"  size="1" style="width:150px;" value="#{FDAccountOpening.accType}" >
                                <f:selectItems value="#{FDAccountOpening.accountTypeList}"/>
                                
                            </h:selectOneListbox>
                            <h:outputLabel id="lblAccOpeningDt" styleClass="label" value="Account Opening Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calAccOpeningDt" readonly="true"   value="#{FDAccountOpening.acctOpeningDt}" inputSize="10">
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblName" styleClass="label" value="Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id ="title">
                                <%--
                                <h:selectOneListbox id="ddTitle" styleClass="ddlist"  style="width:40px;" disabled="#{FDAccountOpening.disableTitle}" size="1" value="#{FDAccountOpening.title}" >
                                    <f:selectItems value="#{FDAccountOpening.titleList}"/>
                                    
                                </h:selectOneListbox>
                                --%>
                                <h:inputText id="txtName" styleClass="input" maxlength="50" style="width:140px;" disabled="#{FDAccountOpening.disableName}" value="#{FDAccountOpening.name}" onblur="this.value = this.value.toUpperCase();">
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtFatherHusbandName" maxlength="50" styleClass="input" style="width:140px;" onblur="this.value = this.value.toUpperCase();" disabled="#{FDAccountOpening.disableHFName}" value="#{FDAccountOpening.HFName}">
                            </h:inputText>
                            <h:outputLabel id="lblPhoneNo" styleClass="label" value="Phone No"/>
                            <h:inputText id="txtPhoneNo" styleClass="input" style="width:80px;" maxlength="20" disabled="#{FDAccountOpening.disablePhoneNo}" value="#{FDAccountOpening.phoneNo}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPermanentAddress" styleClass="label" value="Permanent Address"/>
                            <h:inputText id="txtPermanentAddress" maxlength="100" styleClass="input" style="width:140px;" disabled="#{FDAccountOpening.disablePerAdd}" value="#{FDAccountOpening.permanentAdd}" onblur="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblCorrespondenceAddress" styleClass="label" value="Correspondence Address"/>
                            <h:inputText id="txtCorrespondenceAddress" maxlength="100" styleClass="input" style="width:140px;" disabled="#{FDAccountOpening.disableCorresAdd}" onkeyup="this.value = this.value.toUpperCase();" value="#{FDAccountOpening.corresAdd}">
                                <a4j:support   actionListener="#{FDAccountOpening.setCorressAdd}" event="onblur"
                                               reRender="txtCorrespondenceAddress"/>
                            </h:inputText>
                            <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date of Birth"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calDateOfBirth" disabled="#{FDAccountOpening.disabledob}" value="#{FDAccountOpening.dateofBirth}" inputSize="10">
                            </rich:calendar>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPanGirNo" styleClass="label" value="Pan/Gir No"/>
                            <h:inputText id="txtPanGirNo" styleClass="input" maxlength="20" style="width:100px;" disabled="#{FDAccountOpening.disablePenNo}" value="#{FDAccountOpening.panGirNo}" onblur="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <%--
                            <h:outputLabel id="lblOrganizationType" styleClass="label" value="Organization Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOrganizationType" styleClass="ddlist"  size="1" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.orgType}" >
                                <f:selectItems value="#{FDAccountOpening.orgTypeList}"/>
                            </h:selectOneListbox>
                            --%>
                            <%-- Added by Manish Kumar --%>
                            <h:outputLabel id="lblOrganizationType" styleClass="label" value="Organization Type"></h:outputLabel>
                            <h:outputLabel id="organizationTypeId" styleClass="label" value="#{FDAccountOpening.organizationDesc}"></h:outputLabel>
                            <%-- --%>
                            <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operating Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOperatingMode" style="width:140px;" styleClass="ddlist"  size="1" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.operatingMode}" >
                                <f:selectItems value="#{FDAccountOpening.operatingModeList}"/>
                                <a4j:support actionListener="#{FDAccountOpening.ddOperationModeDisableJtName}" event="onblur"
                                             oncomplete="if(#{rich:element('ddOperatingMode')}.value == '1'){#{rich:element('txtNominee')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '2'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '3'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '4'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '5'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '6'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '7'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '9'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '11'){#{rich:element('txtGuardianName')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '12'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '13'){#{rich:element('txtGuardianName')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '14'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '15'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else if(#{rich:element('ddOperatingMode')}.value == '16'){#{rich:element('txtCustIdJtName1')}.focus();}
                                             else{#{rich:element('txtNominee')}.focus();}"
                                             reRender="Row6,Row12,lblMsg" />
                               
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblCustIdJtName1" styleClass="label" value="Customer Id/ JtName1 "/>
                            <h:panelGroup id ="group1">
                                <h:inputText id="txtCustIdJtName1" maxlength="10" disabled="#{FDAccountOpening.jt1IDDisable}" style="width:40px;" styleClass="input"  value="#{FDAccountOpening.jt1CustomerId}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support   actionListener="#{FDAccountOpening.setJtName1}" event="onblur" oncomplete="if(#{FDAccountOpening.message=='Please Enter Customer Id For JT Name 1.'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName1 ID  should be Numerical.'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JtName1 ID And Customer Id can not be Same'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                   else if(#{FDAccountOpening.message=='Customer Id Does Not Exists'}){#{rich:element('txtCustIdJtName1')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '4'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '5'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '12'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '6'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '14'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '15'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '16'){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else{#{rich:element('txtNominee')}.focus();}"
                                                   reRender="txtJtName1,lblMsg,Row12,Row7,Row6"/>
                                </h:inputText>
                                <h:inputText id="txtJtName1" maxlength="40" disabled="#{FDAccountOpening.jt1Disable}" styleClass="input" style="width:99px;" value="#{FDAccountOpening.jtName1}" onblur="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustIdJtName2" styleClass="label" value="Customer Id/ JtName2 "/>
                            <h:panelGroup id ="group2">
                                <h:inputText id="txtCustIdJtName2"  maxlength="10" disabled="#{FDAccountOpening.jt2IDDisable}" style="width:40px;" styleClass="input"  value="#{FDAccountOpening.jt2CustomerId}">
                                    <a4j:support   actionListener="#{FDAccountOpening.setJtName2}" event="onblur"  oncomplete="if(#{FDAccountOpening.message=='Please Enter Customer Id For JT Name 2.'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName2 ID  should be Numerical.'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JtName2 ID And Customer Id can not be Same'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{FDAccountOpening.message=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{FDAccountOpening.message=='Customer Id Does Not Exists'}){#{rich:element('txtCustIdJtName2')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '4'){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '16'){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '6'){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '14'){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '15'){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else{#{rich:element('txtNominee')}.focus();}"
                                                   reRender="txtJtName2,lblMsg,Row12,Row7,Row6"/>
                                </h:inputText>
                                <h:inputText id="txtJtName2" maxlength="40" disabled="#{FDAccountOpening.jt2Disable}" styleClass="input" style="width:99px;" value="#{FDAccountOpening.jtName2}" onblur="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustIdJtName3" styleClass="label" value="Customer Id / JtName3"/>
                            <h:panelGroup id ="group3">
                                <h:inputText id="txtCustIdJtName3"  maxlength="10" disabled="#{FDAccountOpening.jt3IDDisable}" style="width:40px;" styleClass="input"  value="#{FDAccountOpening.jt3CustomerId}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support   actionListener="#{FDAccountOpening.setJtName3}" event="onblur" oncomplete="if(#{FDAccountOpening.message=='Please Enter Customer Id For JT Name 3.'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName3 ID  should be Numerical.'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName3 ID And Customer Id can not be Same'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{FDAccountOpening.message=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{FDAccountOpening.message=='Customer Id Does Not Exists'}){#{rich:element('txtCustIdJtName3')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '4'){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '16'){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '14'){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{rich:element('ddOperatingMode')}.value == '15'){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else{#{rich:element('txtNominee')}.focus();}"
                                                   reRender="txtJtName3,lblMsg,Row12,Row7,Row6"/>
                                </h:inputText>
                                <h:inputText id="txtJtName3"  maxlength="40" disabled="#{FDAccountOpening.jt3Disable}"styleClass="input" style="width:99px;" value="#{FDAccountOpening.jtName3}" onblur="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row12" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCustIdJtName4"  styleClass="label" value="Customer Id/ JtName4 "/>
                            <h:panelGroup id ="group4">
                                <h:inputText id="txtCustIdJtName4" maxlength="10" disabled="#{FDAccountOpening.jt4IDDisable}" style="width:40px;" styleClass="input"  value="#{FDAccountOpening.jt4CustomerId}">
                                    <a4j:support   actionListener="#{FDAccountOpening.setJtName4}" event="onblur" oncomplete="if(#{FDAccountOpening.message=='Please Enter Customer Id For JT Name 4.'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName 4 ID Id should be Numerical.'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{FDAccountOpening.message=='JTName 4 ID And Customer Id can not be Same'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{FDAccountOpening.message=='TWO JOINT HOLDERS NAME CANNOT BE SAME !!!'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else if(#{FDAccountOpening.message=='Customer Id Does Not Exists'}){#{rich:element('txtCustIdJtName4')}.focus();}
                                                   else{#{rich:element('txtGuardianName')}.focus();}"
                                                   reRender="lblMsg,Row12,Row7,Row6"/>
                                </h:inputText>
                                <h:inputText id="txtJtName4" maxlength="40" disabled="#{FDAccountOpening.jt4Disable}" styleClass="input" style="width:99px;" value="#{FDAccountOpening.jtName4}" onblur="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblGuardianName" styleClass="label" value="Guardian Name"/>
                            <h:inputText id="txtGuardianName" styleClass="input" maxlength="40" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.guardianName}" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support actionListener="#{FDAccountOpening.onblurGuardianName}" event="onblur" reRender="leftPanel" focus="txtRelationship"/>
                            </h:inputText>
                            <h:outputLabel id="lblRelationship" styleClass="label" value="Relationship with Guardian"></h:outputLabel>
                            <h:inputText id="txtRelationship" maxlength="30" styleClass="input" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.relationship}" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support actionListener="#{FDAccountOpening.onblurRelationshipWithGuardian}" event="onblur" reRender="leftPanel,Row12,Row7" focus="txtNominee"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblNominee" styleClass="label" value="Nominee"/>
                            <h:inputText id="txtNominee" maxlength="50" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"disabled="#{FDAccountOpening.disableFlag}" style="width:140px;" value="#{FDAccountOpening.nominee}" >
                                <a4j:support actionListener="#{FDAccountOpening.onblurNominee}" event="onblur" reRender="txtNomineeAdd,leftPanel,Row12,Row7" focus="txtNomineeAdd"/>
                            </h:inputText>
                            <h:outputLabel id="lblNomineeAdd" styleClass="label" value="Nominee Address"/>
                            <h:inputText id="txtNomineeAdd" maxlength="255" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.nomineeAdd}" style="width:140px;" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support focus="txtNomineeRelationship" event="onblur"/>
                            </h:inputText>
                            <h:outputLabel id="lblNomineeRelationship" styleClass="label" value="Nominee Relationship "/>
                            <h:inputText id="txtNomineeRelationship"  onkeyup="this.value = this.value.toUpperCase();" maxlength="50" disabled="#{FDAccountOpening.disableFlag}" style="width:140px;" styleClass="input" value="#{FDAccountOpening.nomineeRelationship}" >
                                <a4j:support actionListener="#{FDAccountOpening.onblurNomineeRelation}" event="onblur" reRender="leftPanel" focus="ddCustomer"/>
                            </h:inputText>

                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblNomineeDob" styleClass="label" value="Nominee DOB"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calNomineeDob" value="#{FDAccountOpening.nomineeDob}" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}">
                                <a4j:support actionListener="#{FDAccountOpening.onblurNomineeDateofBirth}" event="onchanged" reRender="leftPanel" focus="ddCustomer"/>
                            </rich:calendar>
                            <h:outputLabel id="lblCustomer" styleClass="label" value="Customer"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCustomer" styleClass="ddlist"  size="1" disabled="#{FDAccountOpening.disableFlag}" style="width:140px;" value="#{FDAccountOpening.customerType}">
                                <f:selectItems value="#{FDAccountOpening.customerTypeList}"/>
                               
                            </h:selectOneListbox>

                            <%--h:outputLabel id="lblApplyTDS" styleClass="label" value="Apply TDS"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddApplyTDS" styleClass="ddlist"  size="1" value="#{FDAccountOpening.applyTds}" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}">
                                <f:selectItems value="#{FDAccountOpening.applyTdsList}"/>
                                <a4j:support actionListener="#{FDAccountOpening.setDisableTdsDetails}" event="onblur"
                                             reRender="ddTDSDetails"/>
                               
                            </h:selectOneListbox--%>
                            <h:outputLabel id="lblDocument" styleClass="label" value="Document"><font class="required" style="color:red;">*</font>
                            </h:outputLabel>
                            <h:selectOneListbox id="ddDocument" styleClass="ddlist"  size="1" style="width:140px;" disabled="#{FDAccountOpening.disableFlag}" value="#{FDAccountOpening.document}" >
                                <f:selectItems value="#{FDAccountOpening.documentList}"/>
                                  <a4j:support   actionListener="#{FDAccountOpening.documents}" event="onblur"
                                                   reRender="Row9,lblMsg" focus="txtDocumentDetails"/>
                            </h:selectOneListbox>                            
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblDocumentDetails" styleClass="label" value="Document Details"/>
                            <h:inputText id="txtDocumentDetails" maxlength="50" styleClass="input" disabled="#{FDAccountOpening.disableFlag}" onblur="this.value = this.value.toUpperCase();" style="width:140px;" value="#{FDAccountOpening.docDetail}">
                            </h:inputText>
                            <%--h:outputLabel id="lblTDSDetails" styleClass="label" value="TDS Details"/>
                            <h:selectOneListbox id="ddTDSDetails" styleClass="ddlist"  style="width:140px;" size="1" value="#{FDAccountOpening.tdsDetails}" disabled="#{FDAccountOpening.tdsDetailDisable}">
                                <f:selectItems value="#{FDAccountOpening.tdsDetailsList}"/>
                                
                            </h:selectOneListbox--%>
                            <h:outputLabel id="lblIntroducerAccount" styleClass="label" value="Customer Id / Introducer Account"/>
                            <h:panelGroup id = "group12">
                                <h:inputText id="txtIntroducerId" maxlength="10" disabled="#{FDAccountOpening.disableIntroAcct}" styleClass="input" style="width:40px;" value="#{FDAccountOpening.introId}">
                                </h:inputText>
                                <h:inputText id="txtIntroducerAccount" disabled="#{FDAccountOpening.disableFlag}" maxlength="#{FDAccountOpening.acNoMaxLen}" styleClass="input" style="width:99px;" value="#{FDAccountOpening.oldIntroducerAccountNo}">
                                    <a4j:support   actionListener="#{FDAccountOpening.getIntroducerAcctDetails}" event="onblur"
                                                   reRender="Row9,lblMsg" focus="ddActCatgType"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblIntrodName"  styleClass="label"  value="#{FDAccountOpening.introducerName}"/>
                            <h:panelGroup id="intro">
                                <h:outputLabel id="lblAcct"  styleClass="label" value="#{FDAccountOpening.introducerAccount}"/>
                                <h:outputLabel id="lblAcct1" styleClass="label" value="    "/>
                                <h:outputLabel id="lblStatus" styleClass="label"  value="#{FDAccountOpening.introducerAcctStatus}"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row10" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblActCatg" styleClass="label" value="Account Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddActCatgType" styleClass="ddlist"  size="1" style="width:140px;" value="#{FDAccountOpening.actCategory}" >
                                <f:selectItems value="#{FDAccountOpening.actCategoryList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblRemark" styleClass="label" value="Remark"/>
                            <h:inputText id="txtRemark" styleClass="input" maxlength="100" disabled="#{FDAccountOpening.disableFlag}" style="width:140px;" value="#{FDAccountOpening.remark}" onblur="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel/>
                            <h:outputLabel/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" disabled="#{FDAccountOpening.disableFlag}" onclick="#{rich:component('savePanel')}.show();">
                               
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{FDAccountOpening.resetValues}" 
                                               reRender="msgRow,Row1,Row2,Row3,Row4,Row5,Row6,Row12,Row7,Row8,Row9,Row10" focus="txtCustomerID">
                               
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{FDAccountOpening.exitBtnAction}" reRender="leftPanel" >
                               
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                         <rich:modalPanel id="savePanel" autosized="true" width="200" onshow="#{rich:element('btnYes')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" style="padding-right:15px;" />
                    </f:facet>

                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr>
                                    <td colspan="2" style="height:40px" >
                                        <h:outputText value="Are You Sure To Save Data?" styleClass="output"/>

                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id = "btnYes" ajaxSingle="true" action="#{FDAccountOpening.saveAction}"
                                                           onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,btnSave,leftPanel" oncomplete="#{rich:component('btnRefresh')}.focus();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Cancel" id = "btnNo" ajaxSingle="true" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:form>
        </body>
    </html>
</f:view>
