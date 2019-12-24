<%-- 
    Document   : FidelityAccountOpen
    Created on : Apr 26, 2014, 10:49:26 AM
    Author     : sipl
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
            <title>Fidelity Registration Form</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
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
            <a4j:form id="FidelityOpen">
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
                            <h:outputText id="stxtDate" styleClass="output" value="#{FidelityAccountOpen.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Fidelity Registration Form"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{FidelityAccountOpen.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;background-color:#e8eef7" styleClass="row1">
                            <h:outputText id="lblMsg" styleClass="error" value="#{FidelityAccountOpen.message}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                             <h:outputLabel id="lblFuntion" styleClass="label" value="Function" style="display:"/>
                             <h:selectOneListbox id="ddFunction" styleClass="ddlist" size="1" style="width:80px;" value="#{FidelityAccountOpen.function}">
                                <f:selectItems value="#{FidelityAccountOpen.functionList}"/>
                                <a4j:support action="#{FidelityAccountOpen.chenageOperation}" event="onblur" oncomplete="setMask();" focus="#{FidelityAccountOpen.focusId}"
                                               reRender="lblMsg,lblCustId,lblAccountOpen1,lblAccountOpen,txtCustId,txtAccountOpen,btnSave,ddAccount,ddNewAccType,ddDesig,txtBondAmt,txtPrAmt,ddSchemeCode,mainPanel"/>
                            </h:selectOneListbox>
                            <h:panelGroup layout="block">
                                <h:outputLabel id="lblCustId" styleClass="label" value="Customer Id" style="display:#{FidelityAccountOpen.custIdStyle}"/>
                                <h:outputLabel id="lblAccountOpen" styleClass="label" value="Account No" style="display:#{FidelityAccountOpen.acNoStyle}"/>
                                <h:outputLabel id="lblAccountOpen1" styleClass="label" value="Account No" style="display:#{FidelityAccountOpen.verifyStyle}"/>
                            </h:panelGroup>
                             <h:panelGroup layout="block">
                                <h:inputText id="txtCustId" styleClass="input" maxlength="10" value="#{FidelityAccountOpen.custId}" style="display:#{FidelityAccountOpen.custIdStyle};width:80px;">
                                    <a4j:support action="#{FidelityAccountOpen.getCustomerDetail}" event="onblur" oncomplete="setMask();" focus="ddNewAccType"
                                               reRender="lblMsg,txtName,calIntDateOfBirth,txtFatherHusbandName,ddDesig,txtPermanentAddress,txtCorrespondenceAddress,txtPenGirNo,ddNewAccType"/>
                                </h:inputText>
                                <h:inputText id="txtAccountOpen" styleClass="input" maxlength="12" value="#{FidelityAccountOpen.acNo}" style="display:#{FidelityAccountOpen.acNoStyle};width:100px;" onkeyup="this.value = this.value.toUpperCase();" >
                                    <a4j:support action="#{FidelityAccountOpen.candidateDeatails}" event="onblur" oncomplete="setMask();" reRender="lblMsg,mainPanel" />
                                </h:inputText>
                                <h:selectOneListbox id="ddAccount" styleClass="ddlist"  size="1" style="display:#{FidelityAccountOpen.verifyStyle};width:100px;" value="#{FidelityAccountOpen.acNumber}">
                                    <f:selectItems value="#{FidelityAccountOpen.accountList}"/>
                                    <a4j:support action="#{FidelityAccountOpen.candidateDeatails}" event="onblur" oncomplete="setMask();" focus="btnSave"
                                               reRender="lblMsg,txtName,calIntDateOfBirth,txtFatherHusbandName,ddDesig,txtPermanentAddress,txtCorrespondenceAddress,txtPenGirNo,ddNewAccType,txtBondAmt,txtPrAmt,btnSave"/>
                            </h:selectOneListbox>
                            </h:panelGroup>
                            <h:outputLabel id="label13" styleClass="label" value="New A/C Type" ><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup  layout="block">
                                <h:selectOneListbox id="ddNewAccType" styleClass="ddlist" size="1" style="width: 95px" value="#{FidelityAccountOpen.newAccType}" disabled="#{FidelityAccountOpen.flag}">
                                    <a4j:support actionListener="#{FidelityAccountOpen.acTypeLostFocus}" event="onblur" reRender="lblMsg,ddSchemeCode"/>
                                    <f:selectItems value="#{FidelityAccountOpen.acctTypeOption}"/>
                                </h:selectOneListbox>
                                <h:selectOneListbox id="ddSchemeCode" disabled="#{FidelityAccountOpen.fieldDisFlag}" styleClass="ddlist" value="#{FidelityAccountOpen.acTypeDesc}" size="1" style="width: 80px">
                                <f:selectItems value="#{FidelityAccountOpen.schemeCodeList}" />
                                </h:selectOneListbox>
                            </h:panelGroup>
                        </h:panelGrid>                        
                        <rich:panel header="Personal Details" style="text-align:left;">
                            <h:panelGrid  style="width:100%;text-align:left;" >
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblRegisterDt" styleClass="label" value="Opening Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="calIntDate1" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{FidelityAccountOpen.openDt}" disabled="#{FidelityAccountOpen.opFlag}">
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                    </h:inputText>
                                    <h:outputLabel id="lblName" styleClass="label" value="Customer Name"/>
                                    <h:outputText id="txtName" styleClass="output" value="#{FidelityAccountOpen.firstName}"/>
                                    <h:outputLabel id="lblDateOfBirth" styleClass="label" value="Date Of Birth"/>
                                    <h:outputText id="calIntDateOfBirth" styleClass="output"  style="width:70px;setMask()" value="#{FidelityAccountOpen.dateofBirth}"/>                                    
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row6" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblFatherHusbandName" styleClass="label" value="Father/Husband Name"/>
                                    <h:outputText id="txtFatherHusbandName" styleClass="output" value="#{FidelityAccountOpen.fatherName}"/>
                                    <h:outputLabel id="lblDesig" styleClass="label" value="Designation"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddDesig" styleClass="ddlist"  size="1" style="width:140px;" value="#{FidelityAccountOpen.businessDesig}" disabled="#{FidelityAccountOpen.desgFlag}">
                                        <a4j:support actionListener="#{FidelityAccountOpen.desigLostFocus}" event="onblur" reRender="lblMsg,txtBondAmt,txtPrAmt"/>
                                        <f:selectItems value="#{FidelityAccountOpen.businessDesigList}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblPermanentAddress" styleClass="label" value="Permanent Address" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:outputText id="txtPermanentAddress" styleClass="output"style="width:120px" value="#{FidelityAccountOpen.permanentAdd}"/>                               
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="lblCorrespondenceAddress" styleClass="label" value="Correspondence Address" ><font class="required" color="red">*</font></h:outputLabel>
                                    <h:outputText id="txtCorrespondenceAddress" styleClass="output"style="width:150px" value="#{FidelityAccountOpen.correspodenceAdd}"/>
                                    <h:outputLabel id="lblPenGirNo" styleClass="label" value="Pan/Gir No."/>
                                    <h:outputText id="txtPenGirNo" styleClass="output"style="width:100px" value="#{FidelityAccountOpen.panGir}"/>
                                    <h:outputLabel id="lblBondAmt" styleClass="label" value="Bond Amount"/>
                                    <h:outputText id="txtBondAmt" styleClass="output"style="width:100px" value="#{FidelityAccountOpen.bondAmt}"/>                                    
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblPrAmt" styleClass="label" value="Premium Amount"/>
                                    <h:outputText id="txtPrAmt" styleClass="output"style="width:100px" value="#{FidelityAccountOpen.prAmt}"/>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                </h:panelGrid>    
                            </h:panelGrid>
                        </rich:panel>                        
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="#{FidelityAccountOpen.btnValue}"  action="#{FidelityAccountOpen.saveAction}" oncomplete="setMask();"  reRender="lblMsg,mainPanel" focus="btnExit" disabled="#{FidelityAccountOpen.saveDisable}"/>
                            <a4j:commandButton  id="btnrefresh"  value="Refresh" action="#{FidelityAccountOpen.refreshButtonAction}"
                                                oncomplete="setMask();" reRender="mainPanel,lblMsg,btnUpdate" />
                            <a4j:commandButton id="btnExit" value="Exit" action="#{FidelityAccountOpen.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>