<%--
    Document   : DDSAccountOpeningRegister
    Created on : 25 Nov, 2010, 2:28:55 PM
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
            <title>DDS Account Opening Register</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="Form">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DDSAccountOpeningRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="DDS Account Opening Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DDSAccountOpeningRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{DDSAccountOpeningRegister.message}"/>
                            <h:outputText id="lblMsg1" styleClass="msg" value="#{DDSAccountOpeningRegister.message1}"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCustomerID" styleClass="label" value="Customer ID"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtCustomerID" styleClass="input" maxlength="10" value="#{DDSAccountOpeningRegister.customerId}" style="width:80px;" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support   actionListener="#{DDSAccountOpeningRegister.getAccontDetails}" event="onblur"
                                               reRender="leftPanel,btnSave" focus="ddACType"/>
                            </h:inputText>
                            <h:outputLabel id="lblACType" styleClass="label" value="A/C Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddACType" styleClass="ddlist"  size="1" style="width:150px;" value="#{DDSAccountOpeningRegister.accType}" disabled="#{DDSAccountOpeningRegister.disableFlag}" >
                                <f:selectItems value="#{DDSAccountOpeningRegister.accountTypeList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblagCode" styleClass="label" value="Agent Code"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddagcode" styleClass="ddlist"  size="1" style="width:150px;" value="#{DDSAccountOpeningRegister.agentCode}" disabled="#{DDSAccountOpeningRegister.disableFlag}">
                                <f:selectItems value="#{DDSAccountOpeningRegister.introTypeList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblAccOpeningDt" styleClass="label" value="Account Opening Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calAccOpeningDt" readonly="true"  value="#{DDSAccountOpeningRegister.acctOpeningDt}" inputSize="10">
                                <a4j:support event="onchanged" focus="ddTitle" />
                            </rich:calendar>
                            <h:outputLabel id="lblName" styleClass="label" value="Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id ="title">
                                <%--
                                <h:selectOneListbox id="ddTitle" styleClass="ddlist"  style="width:40px;" disabled="#{DDSAccountOpeningRegister.disableTitle}" size="1" value="#{DDSAccountOpeningRegister.title}" >
                                    <f:selectItems value="#{DDSAccountOpeningRegister.titleList}"/>
                                </h:selectOneListbox>
                                --%>
                                <h:inputText id="txtName" styleClass="input" maxlength="53" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableName}" value="#{DDSAccountOpeningRegister.name}" onkeyup="this.value = this.value.toUpperCase();">
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtFatherHusbandName" maxlength="50" styleClass="input" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableHFName}" value="#{DDSAccountOpeningRegister.husFatherName}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblPermanentAddress" styleClass="label" value="Permanent Address"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtPermanentAddress" styleClass="input" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disablePerAdd}" value="#{DDSAccountOpeningRegister.permanentAdd}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblCorrespondenceAddress" styleClass="label" value="Correspondence Address"/>
                            <h:inputText id="txtCorrespondenceAddress" styleClass="input" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableCorresAdd}" onkeyup="this.value = this.value.toUpperCase();" value="#{DDSAccountOpeningRegister.corresAdd}">
                                <a4j:support   actionListener="#{DDSAccountOpeningRegister.setCorressAdd}" event="onblur"
                                               reRender="txtCorrespondenceAddress"/>
                            </h:inputText>
                            <h:outputLabel id="lblPhoneNo" styleClass="label" value="Phone No"/>
                            <h:inputText id="txtPhoneNo" styleClass="input" style="width:80px;" maxlength="100" disabled="#{DDSAccountOpeningRegister.disablePhoneNo}" value="#{DDSAccountOpeningRegister.phoneNo}" onkeyup="this.value = this.value.toUpperCase();"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblPanGirNo" styleClass="label" value="Pan/Gir No"/>
                            <h:inputText id="txtPanGirNo" styleClass="input" maxlength="20" style="width:80px;" disabled="#{DDSAccountOpeningRegister.disablePenNo}" value="#{DDSAccountOpeningRegister.panGirNo}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date of Birth"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calDateOfBirth" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disabledob}" value="#{DDSAccountOpeningRegister.dateofBirth}" inputSize="10">
                            </rich:calendar>
                            <%--<h:outputLabel id="lblOccupation" styleClass="label" value="Occupation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOccupation" styleClass="ddlist"  size="1" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.orgType}" >
                                <f:selectItems value="#{DDSAccountOpeningRegister.orgTypeList}"/>
                            </h:selectOneListbox>
                            --%>
                            <%-- Added by Manish Kumar --%>
                            <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation"></h:outputLabel>
                            <h:outputLabel id="lblOccupationId" styleClass="label" value="#{DDSAccountOpeningRegister.occupationDesc}"></h:outputLabel>
                            <%-- --%>
                        </h:panelGrid>
                        
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row13" style="width:100%;text-align:left;" styleClass="row2">
                             <h:outputLabel id="lblActCategory" styleClass="label" value="Account Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddActCategory" styleClass="ddlist"  size="1" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.actCategory}" >
                                <f:selectItems value="#{DDSAccountOpeningRegister.actCategoryList}"/>
                            </h:selectOneListbox>
                             <h:outputLabel/>
                             <h:outputLabel/>
                             <h:outputLabel/>
                             <h:outputLabel/>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblOperatingMode" styleClass="label" value="Operation Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddOperatingMode" style="width:140px;" styleClass="ddlist"  size="1" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.operatingMode}" >
                                <f:selectItems value="#{DDSAccountOpeningRegister.operatingModeList}"/>
                                <a4j:support actionListener="#{DDSAccountOpeningRegister.getOperatingModeInformation}"  event="onblur" ajaxSingle="true" 
                                             reRender="Row5,Row6,Row12,Row7"
                                             focus="#{DDSAccountOpeningRegister.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblCustIdJtName1" styleClass="label" value="Customer Id/ JtName1 "/>
                            <h:panelGroup id ="group1">
                                <h:inputText id="txtCustIdJtName1" maxlength="10"  style="width:40px;" styleClass="input"  value="#{DDSAccountOpeningRegister.jt1CustomerId}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{DDSAccountOpeningRegister.jt1Flag}"  tabindex="5">
                                    <a4j:support   actionListener="#{DDSAccountOpeningRegister.setJtName1}" event="onblur"
                                                   reRender="txtJtName1,lblMsg,txtCustomerID" focus="#{DDSAccountOpeningRegister.focusId}"/>
                                </h:inputText>
                                <h:inputText id="txtJtName1" maxlength="40" disabled="#{DDSAccountOpeningRegister.jt1Disable}" styleClass="input" style="width:99px;" value="#{DDSAccountOpeningRegister.jtName1}" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustIdJtName2" styleClass="label" value="Customer Id/ JtName2 "/>
                            <h:panelGroup id ="group2">
                                <h:inputText id="txtCustIdJtName2"  maxlength="10" disabled="#{DDSAccountOpeningRegister.jt2Flag}" tabindex="6" style="width:40px;" styleClass="input"  value="#{DDSAccountOpeningRegister.jt2CustomerId}">
                                    <a4j:support   actionListener="#{DDSAccountOpeningRegister.setJtName2}" event="onblur"
                                                   reRender="txtJtName2,lblMsg,txtCustomerID" focus="#{DDSAccountOpeningRegister.focusId}"/>
                                </h:inputText>
                                <h:inputText id="txtJtName2" maxlength="40" disabled="#{DDSAccountOpeningRegister.jt2Disable}" styleClass="input" style="width:99px;" value="#{DDSAccountOpeningRegister.jtName2}" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblCustIdJtName3" styleClass="label" value="Customer Id / JtName3"/>
                            <h:panelGroup id ="group3">
                                <h:inputText id="txtCustIdJtName3"  maxlength="10" disabled="#{DDSAccountOpeningRegister.jt3Flag}"  tabindex="7" style="width:40px;" styleClass="input"  value="#{DDSAccountOpeningRegister.jt3CustomerId}" onkeyup="this.value = this.value.toUpperCase();">
                                    <a4j:support   actionListener="#{DDSAccountOpeningRegister.setJtName3}" event="onblur"
                                                   reRender="txtJtName3,lblMsg,txtCustomerID" focus="#{DDSAccountOpeningRegister.focusId}"/>
                                </h:inputText>
                                <h:inputText id="txtJtName3"  maxlength="40" disabled="#{DDSAccountOpeningRegister.jt3Disable}"styleClass="input" style="width:99px;" value="#{DDSAccountOpeningRegister.jtName3}" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustIdJtName4"  styleClass="label" value="Customer Id/ JtName4 "/>
                            <h:panelGroup id ="group4">
                                <h:inputText id="txtCustIdJtName4" maxlength="10" disabled="#{DDSAccountOpeningRegister.jt4Flag}" style="width:40px;" styleClass="input"  value="#{DDSAccountOpeningRegister.jt4CustomerId}">
                                    <a4j:support   actionListener="#{DDSAccountOpeningRegister.setJtName4}" event="onblur" focus="#{DDSAccountOpeningRegister.focusId}"
                                                   reRender="txtJtName4,lblMsg,txtCustomerId" />
                                </h:inputText>
                                <h:inputText id="txtJtName4" maxlength="40" disabled="#{DDSAccountOpeningRegister.jt4Disable}" styleClass="input" style="width:99px;" value="#{DDSAccountOpeningRegister.jtName4}" onkeyup="this.value = this.value.toUpperCase();"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblNominee" styleClass="label" value="Nominee"/>
                            <h:inputText id="txtNominee" maxlength="40" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"disabled="#{DDSAccountOpeningRegister.disableFlag}" style="width:140px;" value="#{DDSAccountOpeningRegister.nominee}"/>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row12" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblNomineeAdd" styleClass="label" value="Nominee Address"/>
                            <h:inputText id="txtNomineeAdd" maxlength="255" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.nomineeAdd}" style="width:140px;" styleClass="input" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblNomineeRelationship" styleClass="label" value="Nominee Relationship "/>
                            <h:inputText id="txtNomineeRelationship"  onkeyup="this.value = this.value.toUpperCase();" maxlength="50" disabled="#{DDSAccountOpeningRegister.disableFlag}" style="width:140px;" styleClass="input" value="#{DDSAccountOpeningRegister.nomineeRelationship}" />
                            <h:outputLabel id="lblNomineeDob" styleClass="label" value="Nominee DOB"/>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calNomineeDob" value="#{DDSAccountOpeningRegister.nomineeDob}" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" inputSize="10" >
                                <a4j:support event="onchanged" focus="ddDocument"/>
                            </rich:calendar>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblGuardianName" styleClass="label" value="Guardian Name"/>
                            <h:inputText id="txtGuardianName" styleClass="input" maxlength="40" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.guardianName}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblRelationship" styleClass="label" value="Relationship with Guardian"></h:outputLabel>
                            <h:inputText id="txtRelationship" maxlength="30" styleClass="input" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.relationship}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblDocument" styleClass="label" value="Document"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddDocument" styleClass="ddlist"  size="1" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.document}" >
                                <f:selectItems value="#{DDSAccountOpeningRegister.documentList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                            
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                            
                            <h:outputLabel id="lblDocumentDetails" styleClass="label" value="DocumentDetails"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtDocumentDetails" styleClass="input" disabled="#{DDSAccountOpeningRegister.disableFlag}" onkeyup="this.value = this.value.toUpperCase();" style="width:140px;" value="#{DDSAccountOpeningRegister.docDetail}">
                            </h:inputText>
                            <h:outputLabel id="lblIntroducerAccount" styleClass="label" value="Introducer Account"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:panelGroup id = "group12">
                                <%--h:inputText id="txtIntroducerId" maxlength="6" styleClass="input" style="width:40px;" value="#{DDSAccountOpeningRegister.introId}" disabled="#{DDSAccountOpeningRegister.diasbleintrdId}">
                                </h:inputText--%>
                                <h:inputText id="txtIntroducerAccount" maxlength="#{DDSAccountOpeningRegister.acNoMaxLen}" styleClass="input" style="width:99px;" value="#{DDSAccountOpeningRegister.oldintroducerAcctNo}" disabled="#{DDSAccountOpeningRegister.disableFlag}">
                                    <a4j:support   actionListener="#{DDSAccountOpeningRegister.introducerDetail}" event="onblur"
                                                   reRender="stxtAcno,stxtDetail,stxtStatus,lblMsg"/>
                                </h:inputText>
                                <h:outputText id="stxtAcno" styleClass="output"  value="#{DDSAccountOpeningRegister.intrdAcNo}"  />
                            </h:panelGroup>
                            <h:outputText id="stxtDetail" styleClass="output" value="#{DDSAccountOpeningRegister.intrdDetail}"  />
                            <h:outputText id="stxtStatus" styleClass="output" value="#{DDSAccountOpeningRegister.intrdStatus}"  />
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputLabel id="lblInstallmentAmt" styleClass="label" value="Installment Amount"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtInstallmentAmt" styleClass="input" maxlength="53" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.amount}" onkeyup="this.value = this.value.toUpperCase();">
                            </h:inputText>
                            <h:outputLabel id="lblperiod" styleClass="label" value="Period(In Months)"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtperiod" maxlength="30" styleClass="input" style="width:140px;" disabled="#{DDSAccountOpeningRegister.disableFlag}" value="#{DDSAccountOpeningRegister.day}" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support   actionListener="#{DDSAccountOpeningRegister.ROI}" event="onblur"
                                               oncomplete="if(#{DDSAccountOpeningRegister.amountCheck == 'false'}){#{rich:element('txtInstallmentAmt')}.focus();}
                                               else if(#{DDSAccountOpeningRegister.roiCheck == 'false'}){#{rich:element('txtperiod')}.focus();}
                                               else{#{rich:element('txtROI')}.focus();}"
                                               reRender="txtROI,lblMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblROI" styleClass="label" value="ROI"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtROI" maxlength="50" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"disabled="#{DDSAccountOpeningRegister.disableFlag}" style="width:140px;" value="#{DDSAccountOpeningRegister.roi}"/>
                            <rich:modalPanel id="savePanel" autosized="true" width="250" onshow="#{rich:element('yes')}.focus()">
                                <f:facet name="header">
                                    <h:outputText value="Confirmation DialogBox" />
                                </f:facet>
                                <h:form>
                                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                        <tbody>
                                            <tr style="height:40px">
                                                <td colspan="2">
                                                    <h:outputText value="Are You Sure To Save The Data?"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center" width="50%">
                                                    <a4j:region id="yesBtn">
                                                        <a4j:commandButton id="yes" value="Yes" ajaxSingle="true" action="#{DDSAccountOpeningRegister.saveAction}"
                                                                           onclick="#{rich:component('savePanel')}.hide();" reRender="lblMsg,leftPanel,lblMsg1" />
                                                    </a4j:region>
                                                </td>
                                                <td align="center" width="50%">
                                                    <a4j:commandButton id="No" value="No" onclick="#{rich:component('savePanel')}.hide();return false;" />
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </h:form>
                            </rich:modalPanel>

                        </h:panelGrid>

                    </h:panelGrid>


                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" oncomplete="#{rich:component('savePanel')}.show()" reRender="lblMsg" disabled="#{DDSAccountOpeningRegister.disableFlag}" focus="btnExit"/>
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{DDSAccountOpeningRegister.resetValues}"  reRender="leftPanel,lblMsg1" focus="txtCustomerID" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{DDSAccountOpeningRegister.btnExit}" reRender="leftPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

        </body>
    </html>
</f:view>