<%-- 
    Document   : OutwardNeftRtgs
    Created on : Jun 10, 2013, 2:33:26 PM
    Author     : sipl
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Outward Neft Rtgs</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js"/>
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".calpayDate").mask("99/99/9999");
                }
                function validate(e) {
                    e = e || window.event;
                    var bad = /[^\sa-z\d]/i, key = String.fromCharCode(e.keyCode || e.which);
                    if (e.which !== 0 && e.charCode !== 0 && bad.test(key)) {
                        e.returnValue = false;
                        if (e.preventDefault) {
                            e.preventDefault();
                        }
                    }
                }
                function validateEm(e) {
                    e = e || window.event;
                    var bad = /[^\sa-z\d\.@]/i, key = String.fromCharCode(e.keyCode || e.which);
                    if (e.which !== 0 && e.charCode !== 0 && bad.test(key)) {
                        e.returnValue = false;
                        if (e.preventDefault) {
                            e.preventDefault();
                        }
                    }
                }
                function validateBeneficiaryName(e) {
                    e = e || window.event;
                    var bad = /[^\sa-z&\d]/i, key = String.fromCharCode(e.keyCode || e.which);
                    if (e.which !== 0 && e.charCode !== 0 && bad.test(key)) {
                        e.returnValue = false;
                        if (e.preventDefault) {
                            e.preventDefault();
                        }
                    }
                }
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="gridPanel1" columns="3" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{OutwardNeftRtgs.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="Outward Neft Rtgs"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{OutwardNeftRtgs.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelMsg" style="width:100%;text-align:center;" styleClass="row2" width="100%">
                        <h:outputText id="stxtMsg" styleClass="error" value="#{OutwardNeftRtgs.message}"/>
                    </h:panelGrid>
                    <h:panelGrid id="PanelGrid1" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" width="100%">
                        <h:outputLabel id="lblFunction" styleClass="label" value="Function"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="txtFunction" styleClass="ddlist" required="true" style="width:80px" value="#{OutwardNeftRtgs.functionFlag}" size="1" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <f:selectItems value="#{OutwardNeftRtgs.functionOption}"/>
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.funtionAction}" reRender="stxtMsg,ddNeftType,
                                             txtDrACID,stxtNewAcNo,calpayDate,stxtCustName,stxtBal,stxtBalCrDr,stxtJTName,
                                             stxtAccStatus,txtTranAmount,txtBnkCodeIso,stxtIfscBranch,txtCrACID,txtBenCustName,txtBenCustCode,
                                             txtEmail,txtMobNo,txtDetail,footerPanel,txnGrid,txtInstNo,txtInstDt,btnRefresh,
                                             txtSenderCommMode,txtSenderME,deleteVerifyPanel,stxtDeleteMsg,ddTrsNo,txnDetailGrid,
                                             txnDetailList,deleteProcess,stxtTotalCredit,txtNeftAmount,txnList,
                                             stxtPopUpTotalDebit,stxtPopUpTotalCredit,delConfirmid,ddTransferMode" 
                                             oncomplete="if(#{OutwardNeftRtgs.nextFocusValue == 'addition'})
                                             {#{rich:element('ddNeftType')}.focus();}else{#{rich:component('deleteVerifyPanel')}.show();#{rich:element('ddTrsNo')}.focus();}setMask();"/>
                            </h:selectOneListbox>
                            <%--<h:outputLabel id="lblNeftType" styleClass="label" value="Neft Type"><font class="required" color="red">*</font></h:outputLabel>--%>
                            <h:outputLabel id="lblNeftType" styleClass="label" value="Credit Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddNeftType" styleClass="ddlist" size="1" style="width:100px" value="#{OutwardNeftRtgs.payTp}" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <f:selectItems value="#{OutwardNeftRtgs.payTpList}"/>
                            </h:selectOneListbox>                        
                            <h:outputLabel id="lblpaymentDate" style="width:80%" styleClass="label" value="Payment Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="calpayDate" styleClass="input calpayDate" style="width:70px" value="#{OutwardNeftRtgs.paymentDate}" maxlength="10" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validatePaymentDt}" reRender="stxtMsg" oncomplete="setMask();"/>
                            </h:inputText>
                        </h:panelGrid>                        
                        <h:panelGrid id="PanelGrid2" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblDrACID" styleClass="label" value="Dr.A/C No"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup id="panelGrpCrDr" layout="block">
                                <h:inputText id="txtDrACID" styleClass="input" style="width:120px;" value="#{OutwardNeftRtgs.drAccNo}" maxlength="#{OutwardNeftRtgs.acNoMaxLen}" onkeypress="validate(event)" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                    <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateDrAccount}" reRender="stxtMsg,stxtCustName,stxtBal,stxtBalCrDr,stxtNewAcNo,stxtJTName,stxtAccStatus,txtDrACID" ajaxSingle="true"/>
                                </h:inputText>
                                <h:outputText id="stxtNewAcNo" styleClass="output" value="#{OutwardNeftRtgs.newAcNo}"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustName" styleClass="label" value="Cust Name"></h:outputLabel>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{OutwardNeftRtgs.custName}"/>
                            <h:outputLabel id="lblBalance" styleClass="label" value="Balance"/>
                            <h:panelGroup id="panelGrpBal" layout="block">
                                <h:outputText id="stxtBal" styleClass="output" value="#{OutwardNeftRtgs.balance}">
                                    <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                </h:outputText>
                                <h:outputText id="stxtBalCrDr" styleClass="output" value="#{OutwardNeftRtgs.labelCrDr}"/>
                            </h:panelGroup>
                        </h:panelGrid>                        
                        <h:panelGrid id="PanelGrid3" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblJTName" styleClass="label" value="JT/GU Name"></h:outputLabel>
                            <h:outputText id="stxtJTName" styleClass="output" value="#{OutwardNeftRtgs.jointName}"/>
                            <h:outputLabel id="lblAccStatus" styleClass="label" value="Account Status"/>
                            <h:outputText id="stxtAccStatus" styleClass="output" value="#{OutwardNeftRtgs.accStatus}"/>
                            <h:outputLabel id="lblChargeType" styleClass="label" value="Charge Type"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddChargeType" styleClass="ddlist" size="1" style="width:120px" value="#{OutwardNeftRtgs.chargeType}" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                    <f:selectItems value="#{OutwardNeftRtgs.chargeTypeList}"/>
                                    <a4j:support event="onblur" oncomplete="setMask();"/>
                                </h:selectOneListbox>
                                <h:outputText id="stxtChargeAmount" styleClass="output" value="#{OutwardNeftRtgs.chargeAmount}"/>
                            </h:panelGroup>
                        </h:panelGrid>    
                        <h:panelGrid id="PanelGrid4" columns="6" columnClasses="col3,col3,col3,col3,col3,col1" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblInstNo" styleClass="label" value="Inst.No."><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInstNo" styleClass="input" style="width:80px" maxlength="10" value="#{OutwardNeftRtgs.instNo}" onkeypress="validate(event)" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateInstNo}" reRender="stxtMsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblInstDt" styleClass="label" value="Inst.Date"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtInstDt" styleClass="input calpayDate" style="width:70px" value="#{OutwardNeftRtgs.instDt}" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateInstDt}" reRender="stxtMsg" oncomplete="setMask();"/>
                            </h:inputText>
                            <h:outputLabel id="lblTranAmount" styleClass="label" value="Debit Amount"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtTranAmount" styleClass="input" style="width:110px;" maxlength="20" value="#{OutwardNeftRtgs.tranAmount}" disabled="#{OutwardNeftRtgs.fieldDisableFlag}">
                                <a4j:support event="onblur" actionListener="#{OutwardNeftRtgs.validateAmount}" reRender="stxtMsg,stxtChargeAmount,txtFunction,ddNeftType,calpayDate,txtDrACID,ddChargeType,txtInstNo,txtInstDt,txtTranAmount"/>
                            </h:inputText>
                        </h:panelGrid>    
                        <h:panelGrid id="PanelGrid5" columns="7" columnClasses="col17,col19,col0,col17,col0,col0,col20" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblBenCustName" styleClass="label" value="Beneficiary Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBenCustName" styleClass="input" size="60" value="#{OutwardNeftRtgs.beneficiaryCustName}" maxlength="150" onkeypress="validate(event)" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateBeneficiaryCustname}" reRender="stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblCrACID" styleClass="label" value="Beneficiary A/c"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtCrACID" styleClass="input" size="30" value="#{OutwardNeftRtgs.crAccNo}" maxlength="30" onkeypress="validate(event)" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateCrAcno}" reRender="stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblBnkCodeIso" styleClass="label" value="Beneficiary IFSC"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtBnkCodeIso" styleClass="input" style="width:90px;" value="#{OutwardNeftRtgs.ifsCode}" maxlength="11" onkeypress="validate(event)" onkeyup="this.value = this.value.toUpperCase();">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateIfscCode}" reRender="stxtMsg,stxtIfscBranch"/>
                            </h:inputText>
                            <h:outputText id="stxtIfscBranch" styleClass="output" value="#{OutwardNeftRtgs.ifscBranchName}"/>
                        </h:panelGrid>    
                        <h:panelGrid id="PanelGrid6" columns="8" columnClasses="col17,col17,col17,col17,col17,col17,col17,col19" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="lblNeftAmount" styleClass="label" value="Credit Amount"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtNeftAmount" styleClass="input" style="width:110px;" maxlength="20" value="#{OutwardNeftRtgs.neftAmount}">
                                <a4j:support event="onblur" actionListener="#{OutwardNeftRtgs.validateNeftAmount}" reRender="stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="lblTransferMode" styleClass="label" value="Transfer Mode"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddTransferMode" styleClass="ddlist" size="1" style="width:130px" value="#{OutwardNeftRtgs.transferMode}">
                                <f:selectItems value="#{OutwardNeftRtgs.transferModeList}"/>
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.transferModeOperation}" reRender="stxtMsg,ddNeftBankName,lblNeftBankName"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lblBenCustCode" styleClass="label" value="Beneficiary Code"></h:outputLabel>
                            <h:inputText id="txtBenCustCode" styleClass="input" style="width:150px;" value="#{OutwardNeftRtgs.beneficiaryCustCode}" maxlength="30" onkeypress="validate(event)" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblEmail" styleClass="label" value="Email Id"/>
                            <h:inputText value="#{OutwardNeftRtgs.email}"id="txtEmail" maxlength="100" styleClass="input" style="width:300px" onkeypress="validateEm(event)">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateEmailId}" reRender="stxtMsg"/>
                            </h:inputText>
                        </h:panelGrid>
                        <h:panelGrid id="PanelGrid7" columns="6" columnClasses="col17,col17,col17,col19,col17,col19" style="height:30px;" styleClass="row1" width="100%">
                            <h:outputLabel id="lblMobNo" styleClass="label" value="Mobile No"/>
                            <h:inputText value="#{OutwardNeftRtgs.mobNo}" id="txtMobNo" maxlength="10" styleClass="input" style="width:90px;" onkeypress="validate(event)">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateMobile}" reRender="stxtMsg"/>
                            </h:inputText>
                            <h:outputLabel id="label17" styleClass="label" value="Sender Comm. Mode"><font class="required" color="red">*</font></h:outputLabel>
                            <h:panelGroup>
                                <h:selectOneListbox id="txtSenderCommMode" styleClass="ddlist" required="true" style="width:100px" size="1" value="#{OutwardNeftRtgs.modeType}">
                                    <f:selectItems value="#{OutwardNeftRtgs.modeTypeList}"/>
                                </h:selectOneListbox>
                                <h:inputText id="txtSenderME" styleClass="input" style="width:250px" value="#{OutwardNeftRtgs.mobileOrEmail}">
                                    <a4j:support event="onblur" action="#{OutwardNeftRtgs.validateMobileOrEmail}" reRender="stxtMsg"/>
                                </h:inputText>
                            </h:panelGroup>
                            <h:outputLabel id="lblNeftBankName" styleClass="label" style="display:#{OutwardNeftRtgs.displaybnkNM}" value="Bank Name"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddNeftBankName" styleClass="ddlist" size="1" style="width:100px;display:#{OutwardNeftRtgs.displaybnkNM}" value="#{OutwardNeftRtgs.neftBankName}">
                                <f:selectItems value="#{OutwardNeftRtgs.neftBankNameList}"/>
                            </h:selectOneListbox>    
                        </h:panelGrid>
                        <h:panelGrid id="PanelGrid8" columns="4" columnClasses="col1,col22,col1,col22" style="height:30px;" styleClass="row2" width="100%">
                            <h:outputLabel id="label16" styleClass="label" value="Details"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtDetail" styleClass="input"style="width:250px"  value="#{OutwardNeftRtgs.details}" onkeypress="validate(event)" onkeyup="this.value = this.value.toUpperCase();" maxlength="50">
                                <a4j:support event="onblur" action="#{OutwardNeftRtgs.addUnAuthTxnEntry('')}" reRender="stxtMsg,
                                             ifscConfirmationPanel,stxtTotalCredit,btnSave,txtBenCustName,txtCrACID,
                                             txtBnkCodeIso,stxtIfscBranch,txtNeftAmount,txtBenCustCode,txtEmail,txtMobNo,txtSenderCommMode,
                                             txtSenderME,txtDetail,txnGrid,txnList,ddTransferMode" oncomplete="if(#{OutwardNeftRtgs.ifscFlag == true}){#{rich:component('ifscConfirmationPanel')}.show()}else{if(#{OutwardNeftRtgs.nextFocusValue == 'saveButton'})
                                             {#{rich:element('btnSave')}.focus();}else{#{rich:element('txtBenCustName')}.focus();}}"/>
                            </h:inputText>
                            <h:outputLabel id="lblTotalCredit" styleClass="label" value="Total Credit: "/>
                            <h:outputText id="stxtTotalCredit" styleClass="output" value="#{OutwardNeftRtgs.totalCredit}"/>
                            <h:outputLabel></h:outputLabel>
                            <h:outputText></h:outputText>
                        </h:panelGrid>
                        <h:panelGrid id="txnGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                            <rich:dataTable  value="#{OutwardNeftRtgs.tempUnAuthList}" var="tempItem"
                                             rowClasses="gridrow1, gridrow2" id="txnList" rows="5" columnsWidth="100"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                             width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="11"><h:outputText value="Transaction Detail"/></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Payment Type" /></rich:column>
                                        <rich:column><h:outputText value="Debit A/c" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary Name" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary A/c" /></rich:column>
                                        <rich:column><h:outputText value="Beneficiary IFSC" /></rich:column>
                                        <rich:column><h:outputText value="Neft Amount" /></rich:column>
                                        <rich:column><h:outputText value="Payment Date" /></rich:column>
                                        <rich:column><h:outputText value="Inst.No" /></rich:column>
                                        <rich:column><h:outputText value="Inst.Date" /></rich:column>
                                        <rich:column><h:outputText value="Enter By" /></rich:column> 
                                        <rich:column><h:outputText value="Delete"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{tempItem.paymentType}"/></rich:column>
                                <rich:column><h:outputText value="#{tempItem.debitAccountNo}"/></rich:column>
                                <rich:column><h:outputText value="#{tempItem.beneficiaryName}"/></rich:column>
                                <rich:column><h:outputText value="#{tempItem.creditAccountNo}"/></rich:column>   
                                <rich:column><h:outputText value="#{tempItem.beneficiaryIfsc}"/></rich:column>
                                <rich:column>
                                    <h:outputText value="#{tempItem.neftAmount}">
                                        <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                    </h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{tempItem.paymentDate}"/></rich:column>
                                <rich:column><h:outputText value="#{tempItem.instNo}"/></rich:column>                            
                                <rich:column><h:outputText value="#{tempItem.instDate}"/></rich:column>
                                <rich:column><h:outputText value="#{tempItem.enterBy}"/></rich:column> 
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" oncomplete="#{rich:component('deletePanel')}.show()" reRender="stxtMsg,deletePanel">
                                        <h:graphicImage value="/resources/images/passed.gif" style="border:0"/>
                                        <f:setPropertyActionListener value="#{tempItem}" target="#{OutwardNeftRtgs.tempCurrentItem}"/>                                    
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller id="unAuthTempDataScroll" align="left" for="txnList" maxPages="20" />
                        </h:panelGrid>
                        <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup  style="width:100%;text-align:center;" styleClass="vtop">
                                <a4j:commandButton id="btnSave" disabled="#{OutwardNeftRtgs.disbBtn}" value="Save" action="#{OutwardNeftRtgs.populateMessage}" oncomplete="#{rich:component('processPanel')}.show()" reRender="stxtMsg,processPanel,confirmid"></a4j:commandButton>
                                <a4j:commandButton id="btnRefresh" value="Refresh" action="#{OutwardNeftRtgs.refreshForm}" reRender="mainPanel"></a4j:commandButton>
                                <a4j:commandButton id="btnExit" value="Exit" action="#{OutwardNeftRtgs.exitForm}" reRender="mainPanel"></a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid id="signatureGridPanel" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText id="stxtSignature" styleClass="output" value="F7-Signature View" style="color:blue;"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <a4j:jsFunction name="showSignDetail" oncomplete="if(#{rich:element('stxtNewAcNo')}.value!='') #{rich:component('sigView')}.show();" reRender="sigView"/>
                    <a4j:jsFunction name="deleteVerifyShowSignDetail" oncomplete="if(#{rich:element('stxtPopUpAccount')}.value!='') #{rich:component('deleteVerifySigView')}.show();" reRender="deleteVerifySigView"/>

                <a4j:jsFunction name="jtDetails" action="#{OutwardNeftRtgs.validateJointAccount}" 
                                oncomplete="if(#{OutwardNeftRtgs.jtDetailPopUp==true})
                                {#{rich:component('jtHolder')}.show();}else{#{rich:component('jtHolder')}.hide();}" 
                                reRender="jtHolder,stxtDeleteMsg" />
                <a4j:jsFunction name="populateData" action="#{OutwardNeftRtgs.jtDetails}" reRender="jointDetailPopUpForm"/>
                <rich:hotKey key="Ctrl+J" handler="jtDetails();"/>
            </a4j:form>
            <rich:modalPanel id="sigView" height="380" width="700" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                        <rich:componentControl for="sigView" attachTo="closelink1" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="sigFormPanel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="sigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="signature" element="img" createContent="#{OutwardNeftRtgs.createSignature}" value="#{OutwardNeftRtgs.newAcNo}"/>
                        </h:panelGroup>
                        <h:panelGrid id="sigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="sigViewBtnPanel">
                                <a4j:commandButton id="sigViewClose" value="Close" onclick="#{rich:component('sigView')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>
            <rich:hotKey key="F7" handler="showSignDetail();"/>
            <a4j:region id="processActionRegion">
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="Are you sure to delete it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{OutwardNeftRtgs.deleteRow}" onclick="#{rich:component('deletePanel')}.hide();" 
                                                           reRender="stxtMsg,stxtTotalCredit,txnGrid,txnList"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('deletePanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="confirmid" value="#{OutwardNeftRtgs.confirmationMsg}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="btnYes" action="#{OutwardNeftRtgs.generateOwTxn}" onclick="#{rich:component('processPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel,txnGrid,txnList"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="ifscConfirmationPanel" autosized="true" width="250" onshow="#{rich:element('ifscBtnNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                    </f:facet>
                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="ifscConfirmId" value="IFSC code was not validated. Are you sure to process it ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="ifscBtnYes" action="#{OutwardNeftRtgs.addUnAuthTxnEntry('CNF')}" onclick="#{rich:component('ifscConfirmationPanel')}.hide();" 
                                                           reRender="stxtMsg,mainPanel,txnGrid,txnList,stxtTotalCredit,btnSave,txtBenCustName,txtCrACID,txtBnkCodeIso,stxtIfscBranch,txtNeftAmount,txtBenCustCode,txtEmail,txtMobNo,txtSenderCommMode,txtSenderME,txtDetail,ddTransferMode" oncomplete="if(#{OutwardNeftRtgs.nextFocusValue == 'saveButton'})
                                                           {#{rich:element('btnSave')}.focus();}else{#{rich:element('txtBenCustName')}.focus();}"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="ifscBtnNo" onclick="#{rich:component('ifscConfirmationPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="deleteVerifyPanel" height="560" width="900" left="auto">
                    <f:facet name="header">
                        <h:outputText value="Verify And Delete"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                            <rich:componentControl for="deleteVerifyPanel" attachTo="closelink" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="deleteVerifyMainGrid" width="100%">
                            <h:panelGrid columnClasses="col8,col8" columns="2" id="gridPanelDeleteMsg" style="width:100%;text-align:center;" styleClass="row1" width="100%">
                                <h:outputText id="stxtDeleteMsg" styleClass="error" value="#{OutwardNeftRtgs.deleteMessage}"/>
                            </h:panelGrid>
                            <h:panelGrid id="trsNoGrid" columns="8" columnClasses="col17,col17,col17,col2,col17,col1,col17,col1" width="100%" styleClass="row2" style="text-align:left;">
                                <h:outputLabel id="lblTrsNo" styleClass="label" value="Batch No"><font class="required" color="red">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddTrsNo" styleClass="ddlist" size="1" style="width:80px" value="#{OutwardNeftRtgs.batchNo}">
                                        <f:selectItems value="#{OutwardNeftRtgs.batchNoList}"/>
                                        <a4j:support event="onblur" action="#{OutwardNeftRtgs.fetchAllUnAuthData}" reRender="custdetails,stxtDeleteMsg,txnDetailGrid,txnDetailList,stxtPopUpTotalDebit,stxtPopUpTotalCredit,stxtPopUpAccount,stxtPopUpCustName,stxtPopUpJTName" oncomplete="setMask();"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel id="lblPopUpAccount" styleClass="label" value="Debit A/c: "/>
                                    <h:outputText id="stxtPopUpAccount" styleClass="output" value="#{OutwardNeftRtgs.popUpAccount}"/>
                                    <h:outputLabel id="lblPopUpTotalDebit" styleClass="label" value="Total Debit: "/>
                                    <h:outputText id="stxtPopUpTotalDebit" styleClass="output" value="#{OutwardNeftRtgs.popUpTotalDebit}"/>
                                    <h:outputLabel id="lblPopUpTotalCredit" styleClass="label" value="Total Credit: "/>
                                    <h:outputText id="stxtPopUpTotalCredit" styleClass="output" value="#{OutwardNeftRtgs.popUpTotalCredit}"/>
                                </h:panelGrid>
                                <h:panelGrid id="custdetails" columnClasses="col17,col10,col17,col10" columns="4" styleClass="row1" width="100%">
                                    <h:outputLabel id="lblPopUpCustName" styleClass="label" value="Cust Name"></h:outputLabel>
                                    <h:outputText id="stxtPopUpCustName" styleClass="output" value="#{OutwardNeftRtgs.popUpCustName}"/>
                                    <h:outputLabel id="lblPopUpJTName" styleClass="label" value="JT/GU Name"></h:outputLabel>
                                    <h:outputText id="stxtPopUpJTName" styleClass="output" value="#{OutwardNeftRtgs.popUpJointName}"/>
                                </h:panelGrid>
                                <h:panelGrid id="txnDetailGrid" width="100%" style="background-color:#edebeb;height:200px;" columnClasses="vtop">
                                    <rich:dataTable  value="#{OutwardNeftRtgs.unAuthList}" var="item"
                                                     rowClasses="gridrow1, gridrow2" id="txnDetailList" rows="5" columnsWidth="100"
                                                     onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                     onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                     width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="11"><h:outputText value="Transaction Detail"/></rich:column>
                                                <rich:column breakBefore="true"><h:outputText value="Payment Type" /></rich:column>
                                                <rich:column><h:outputText value="Debit A/c" /></rich:column>
                                                <rich:column><h:outputText value="Beneficiary Name" /></rich:column>
                                                <rich:column><h:outputText value="Beneficiary A/c" /></rich:column>
                                                <rich:column><h:outputText value="Beneficiary IFSC" /></rich:column>
                                                <rich:column><h:outputText value="Neft Amount" /></rich:column>
                                                <rich:column><h:outputText value="Payment Date" /></rich:column>
                                                <rich:column><h:outputText value="Inst.No" /></rich:column>
                                                <rich:column><h:outputText value="Inst.Date" /></rich:column>
                                                <rich:column><h:outputText value="Charge Type" /></rich:column>
                                                <rich:column><h:outputText value="Enter By" /></rich:column> 
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item.paymentType}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.debitAccountNo}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.beneficiaryName}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.creditAccountNo}"/></rich:column>   
                                        <rich:column><h:outputText value="#{item.beneficiaryIfsc}"/></rich:column>
                                        <rich:column>
                                            <h:outputText value="#{item.neftAmount}">
                                                <f:convertNumber type="currency" pattern="#.##" maxFractionDigits="2"/>
                                            </h:outputText>
                                        </rich:column>
                                        <rich:column><h:outputText value="#{item.paymentDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.instNo}"/></rich:column>                            
                                        <rich:column><h:outputText value="#{item.instDate}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.chargeType}"/></rich:column>
                                        <rich:column><h:outputText value="#{item.enterBy}"/></rich:column> 
                                    </rich:dataTable>
                                    <rich:datascroller id="unAuthDataScroll"align="left" for="txnDetailList" maxPages="20" />
                                </h:panelGrid>
                                <h:panelGrid id="deleteFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                    <h:panelGroup id="deleteBtnPanel">
                                        <a4j:commandButton id="deleteProcess" value="#{OutwardNeftRtgs.delBtnValue}" action="#{OutwardNeftRtgs.delPopulateMessage}" oncomplete="#{rich:component('delAlertPanel')}.show()" reRender="stxtDeleteMsg,delAlertPanel,delConfirmid"/>
                                        <a4j:commandButton id="deleteRefresh" value="Refresh" action="#{OutwardNeftRtgs.refreshDeletePanel}" reRender="stxtDeleteMsg,ddTrsNo,txnDetailGrid,txnDetailList,stxtPopUpTotalDebit,stxtPopUpTotalCredit,stxtPopUpAccount,stxtPopUpCustName,stxtPopUpJTName"/>
                                        <a4j:commandButton id="deleteClose" value="Close" onclick="#{rich:component('deleteVerifyPanel')}.hide(); return false;"/>
                                    </h:panelGroup>
                                </h:panelGrid>
                                <h:panelGrid id="popUpSigShowGrid" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputText id="stxtPopUpSigShowGrid" styleClass="output" value="F8-Signature View, Ctrl+J - Joint Detail" style="color:blue;"/>
                                </h:panelGrid>
                            </h:panelGrid>
                        </a4j:form>
                    </rich:modalPanel>
                    <rich:modalPanel id="delAlertPanel" autosized="true" width="250" onshow="#{rich:element('alertBtnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText id="delConfirmid" value="#{OutwardNeftRtgs.alertDelVerifyMessage}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="Yes" id="alertBtnYes" action="#{OutwardNeftRtgs.processDeleteAndVerifyPanel}" onclick="#{rich:component('delAlertPanel')}.hide();" 
                                                           reRender="stxtDeleteMsg,ddTrsNo,txnDetailGrid,txnDetailList,stxtPopUpTotalDebit,stxtPopUpTotalCredit,stxtPopUpAccount,stxtPopUpCustName,stxtPopUpJTName"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="No" id="alertBtnNo" onclick="#{rich:component('delAlertPanel')}.hide();"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
                <rich:modalPanel id="jtHolder" label="Form" width="900" height="400" style="overflow:auto;" resizeable="false" onbeforeshow="populateData();">
                    <f:facet name="header">
                        <h:outputText value="Joint Detail" style="text-align:center;"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:outputLink value="#" onclick="#{rich:component('jtHolder')}.hide(); return false;">X</h:outputLink>
                    </f:facet>
                    <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                        <tbody>
                            <tr>
                                <td>
                                    <a4j:include viewId="#{OutwardNeftRtgs.viewID}"></a4j:include>
                                    </td>

                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                    <a4j:commandButton value="Close" id="btnClose" onclick="#{rich:component('jtHolder')}.hide();"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </rich:modalPanel>
            </a4j:region>
            <rich:modalPanel id="deleteVerifySigView" height="380" width="700" style="align:right">
                <f:facet name="header">
                    <h:outputText value="Signature Detail"/>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                        <rich:componentControl for="deleteVerifySigView" attachTo="closelink2" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="deleteVerifySigFormPanel" style="width:100%;text-align:center;">
                        <h:panelGroup layout="block" id="deleteVerifySigViewPanel" style="overflow:auto;width:700px;height:300px;text-align:center;background-color:#e8eef7">
                            <a4j:mediaOutput id="deleteVerifySignature" element="img" createContent="#{OutwardNeftRtgs.createSignature}" value="#{OutwardNeftRtgs.popUpAccount}"/>
                        </h:panelGroup>
                        <h:panelGrid id="deleteVerifySigViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="deleteVerifySigViewBtnPanel">
                                <a4j:commandButton id="deleteVerifySigViewClose" value="Close" onclick="#{rich:component('deleteVerifySigView')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                </a4j:form>
            </rich:modalPanel>            
            <rich:hotKey key="F8" handler="deleteVerifyShowSignDetail();"/>            
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>