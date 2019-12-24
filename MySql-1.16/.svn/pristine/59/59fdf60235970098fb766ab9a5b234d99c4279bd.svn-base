<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <title>Customer Details</title>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
        </head>
        <body>
            <a4j:form id="custForm1">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="grpPanel1" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CustomerDetail.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Customer Details"/>
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblUser" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CustomerDetail.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="FuncPanel" style="width:100%;text-align:center;">
                        <h:panelGrid id="error" columns="1" style="width:100%;text-align:center;"styleClass="row2">
                            <h:outputText id="stxtError" styleClass="error" value="#{CustomerDetail.errorMsg}" />
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="6" id="funcRow1" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputLabel id="lblFunc" styleClass="label" value="Function"/>
                            <h:panelGroup>
                                <h:selectOneListbox id="ddFunc" styleClass="ddlist" value="#{CustomerDetail.function}" size="1">
                                    <f:selectItems value="#{CustomerDetail.functionOption}"/>
                                    <a4j:support actionListener="#{CustomerDetail.functionRefDesc}" event="onblur" reRender="btnSave,stxtError,funcRow1,funcRow2,persInfoPanel,genInfoPanel,genInfoPanel1,addrInfoPanel,CurrInfoPanel,MISInfoPanel,MinInfoPanel,NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,BuySelLmtInfoPanel,othInfoPanel,taskList,taskList1
                                                 ,ddEducationDetails,checkSpouse,checkParents,checkChildren,lblChild,txtChildren,txtNoMales1,txtNoMales2,txtNoMales3,txtNoMales4,txtNoMales5,txtNoMales6,txtNoFemales1,txtNoFemales2,txtNoFemales3,txtNoFemales4,txtNoFemales5, txtRelativeName1,txtRelativeName2,txtRelativeName3,txtRelativeAddress1,txtRelativeAddress2,txtRelativeAddress3,
                                                 txtAbroad,ddRelativeInBank,ddCreditCardInfo,ddDirectorRelation,btnAdd1,taskList3,btnAdd2,taskList4,ddMedicalInsurance,ddCusIdCombo,perInfo,genInfo,addInfo,curInfo,misInfo,tdfInfo,rpInfo,bsLimitInfo,otherInfo,kycDetails" limitToList="true"  oncomplete="if(#{CustomerDetail.funcFlag=='true'}){#{rich:element('ddTitle')}.focus();}else if(#{CustomerDetail.funcFlag=='false'}){#{rich:element('txtCustId')}.focus();}"/>
                                </h:selectOneListbox>
                                <h:outputText id="stxtFunc" styleClass="output" value="#{CustomerDetail.functionValue}" style="color:green;"/>
                            </h:panelGroup>
                            <h:outputLabel id="lblCustId" styleClass="label" value="Customer Id" />
                            <h:panelGroup>
                                <h:inputText  value="#{CustomerDetail.custId}"disabled="#{CustomerDetail.flag}" id="txtCustId" styleClass="input" style="120px;display:#{CustomerDetail.custIdInputDisFlag};" onkeyup="this.value=this.value.toUpperCase()">
                                    <a4j:support event="onblur" actionListener="#{OperationCustomerMaster.inquiryResult}" reRender="btnSave,mainPanel,addrInfoPanel,persInfoPanel,
                                                 MISInfoPanel,genInfoPanel,MinInfoPanel,NreInfoPanel,lblRelPersonType,trFinInfoPanel,kycDetails,BuySelLmtInfoPanel,
                                                 txtRelCustId,ddRelPersonType,txtRelFirstName,txtRelMiddleName,txtRelLastName,txtRelPan,txtRelUid,txtNregaJobCard,
                                                 txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,
                                                 ddRelState,ddRelCountry,txtJuriPostalCode,ddRelStatus,stxtRelCustName,addAddressInfoPanel,txtVid"/>
                                </h:inputText>
                                <h:selectOneListbox id="ddCusIdCombo" styleClass="ddlist" value="#{CustomerDetail.verifyCustId}" size="1" style="display:#{CustomerDetail.custIdComboDisFlag};">
                                    <f:selectItems value="#{CustomerDetail.verifyCustIdList}"/>
                                    <a4j:support actionListener="#{OperationCustomerMaster.inquiryResult}" event="onchange" reRender="btnSave,mainPanel,addrInfoPanel,persInfoPanel,
                                                 MISInfoPanel,genInfoPanel,MinInfoPanel,NreInfoPanel,lblRelPersonType,trFinInfoPanel,kycDetails,BuySelLmtInfoPanel,
                                                 txtRelCustId,ddRelPersonType,txtRelFirstName,txtRelMiddleName,txtRelLastName,txtRelPan,txtRelUid,txtNregaJobCard,
                                                 txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,
                                                 ddRelState,ddRelCountry,txtJuriPostalCode,ddRelStatus,stxtRelCustName,addAddressInfoPanel,txtVid"/>
                                </h:selectOneListbox>
                                <h:outputText id="stxtckycNo" value="#{CustPersonalDetails.getcKYCNo()}"  styleClass="output" style="color:#426af5;"/>
                                <h:outputText id="stxtMapMsg" value="#{CustomerDetail.mapMessage}" style="display:#{CustomerDetail.mapMessageDisFlag};" styleClass="output"/>
                            </h:panelGroup>
                        </h:panelGrid>
                    </h:panelGrid>
                    <rich:tabPanel switchType="ajax">
                        <rich:tab label="Personal Information"  id="perInfo" switchType="ajax" disabled="#{CustomerDetail.disableTab}"  title="Personal Information"  reRender="perInfo">
                            <a4j:include viewId="/pages/admin/customer/custPersonalDetails.jsp" />
                        </rich:tab>
                        <rich:tab  label="Proof of Identity" switchType="ajax" id="genInfo" title="General Information" disabled="#{CustomerDetail.disableTab}"  reRender="genInfo">
                            <a4j:include viewId="/pages/admin/customer/custGeneralInfo.jsp" />
                        </rich:tab>

                        <rich:tab label="Address Information" switchType="ajax" id="addInfo" title="Address Information" disabled="#{CustomerDetail.disableTab}"  reRender="addInfo">
                            <a4j:include viewId="/pages/admin/customer/custAddressInfo.jsp" />
                        </rich:tab>


                        <rich:tab label="Additional Address Information" switchType="ajax" id="additionalAddInfo" title="Additional Address Information" disabled="#{CustomerDetail.disableTab}"  reRender="additionalAddInfo">
                            <a4j:include viewId="/pages/admin/customer/custAdditionalAddresDetails.jsp" />
                        </rich:tab>

                        <rich:tab label="MIS Information" switchType="ajax" id="misInfo" title="MIS Information" disabled="#{CustomerDetail.disableTab}"  reRender="misInfo">
                            <a4j:include viewId="/pages/admin/customer/misInfo.jsp" />
                        </rich:tab>

                        <rich:tab label="Related Persons Information" switchType="ajax" id="rpInfo" title="Related Persons Information" disabled="#{CustomerDetail.disableTab}" reRender="rpInfo">
                            <a4j:include viewId="/pages/admin/customer/relatedPersonInfo.jsp" />
                        </rich:tab>
                        <rich:tab label="Related Person Info" switchType="ajax" id="relatedInfo" title="Related Person Info" disabled="#{CustomerDetail.disableTab}" reRender="relatedInfo">
                            <a4j:include viewId="/pages/admin/customer/relatedperson.jsp" />
                        </rich:tab>

                        <%--<rich:tab   label="General Information" switchType="ajax" disabled="#{CustomerDetail.disableTab}" id="genInformation" title="Other Bank And Card Information"  reRender="genInformation">
                            <a4j:include viewId="/pages/admin/customer/custGeneralInformation.jsp"/>
                        </rich:tab>
                        <rich:tab label="Currency Information" switchType="ajax" id="curInfo" title="Currency Information" disabled="#{CustomerDetail.disableTab}" reRender="curInfo">
                            <a4j:include viewId="/pages/admin/customer/currencyInfo.jsp" />
                        </rich:tab>
                        <rich:tab label="Trade Finance Information" switchType="ajax" id="tdfInfo" title="Trade Finance Information" disabled="#{CustomerDetail.disableTab}"  reRender="tdfInfo">
                            <a4j:include viewId="/pages/admin/customer/tradeFinanceInfo.jsp" />
                        </rich:tab>
                        <rich:tab label="Buyer/Seller Limit Information" switchType="ajax" id="bsLimitInfo" disabled="#{CustomerDetail.disableTab}" title="Buyer/Seller Limit Information"  reRender="bsLimitInfo">
                            <a4j:include viewId="/pages/admin/customer/bsLimitInfo.jsp" />
                        </rich:tab>
                        <rich:tab label="Other Information" switchType="ajax" id="otherInfo" title="Other Information" disabled="#{CustomerDetail.disableTab}" reRender="otherInfo">
                            <a4j:include viewId="/pages/admin/customer/otherInfo.jsp" />
                        </rich:tab>
                        <rich:tab label="KYC Details" switchType="ajax" id="kycDetails" title="KYC Details" disabled="#{CustomerDetail.disableTab}" reRender="kycDetails">
                            <a4j:include viewId="/pages/admin/customer/kycDetails.jsp" />
                        </rich:tab>
                        <rich:tab label="Employment Details" switchType="ajax" id="empDtlInfo" title="Employment Details" disabled="#{CustomerDetail.disableTab}" reRender="empDtlInfo">
                            <a4j:include viewId="/pages/admin/customer/employmentDetInfo.jsp" />
                        </rich:tab--%>
                    </rich:tabPanel>
                    <a4j:region id="alertRegion">
                        <rich:modalPanel id="processPanel" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Confirmation Dialog" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:40px">
                                            <td colspan="2">
                                                <h:outputText id="confirmid" value="#{CustomerDetail.confirmationMsg}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes" action="#{OperationCustomerMaster.callFromAlertButton}" 
                                                                   onclick="#{rich:component('processPanel')}.hide();" 
                                                                   reRender="stxtError,funcRow1,funcRow2,persInfoPanel,genInfoPanel,
                                                                   genInfoPanel1,addrInfoPanel,CurrInfoPanel,MISInfoPanel,MinInfoPanel,
                                                                   NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,BuySelLmtInfoPanel,
                                                                   othInfoPanel,taskList,taskList1,ddEducationDetails,checkSpouse,
                                                                   checkParents,checkChildren,lblChild,txtChildren,txtNoMales1,
                                                                   txtNoMales2,txtNoMales3,txtNoMales4,txtNoMales5,txtNoMales6,
                                                                   txtNoFemales1,txtNoFemales2,txtNoFemales3,txtNoFemales4,
                                                                   txtNoFemales5,txtNoFemales6,txtRelativeName1,txtRelativeName2,
                                                                   txtRelativeName3,txtRelativeAddress1,txtRelativeAddress2,
                                                                   txtRelativeAddress3,txtAbroad,ddRelativeInBank,ddCreditCardInfo,
                                                                   ddDirectorRelation,ddAssetsType,ddAssetOwnership,ddAssetValue,
                                                                   btnAdd1,taskList3,ddExistingLoans,txtLoanAmount,btnAdd2,taskList4,
                                                                   ddMedicalInsurance,ddRelPersonType,txtRelCustId,txtRelFirstName,
                                                                   txtRelMiddleName,txtRelLastName,txtRelPan,txtRelUid,txtNregaJobCard,
                                                                   txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,
                                                                   ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,
                                                                   txtJuriPostalCode,ddRelStatus,realtedPersonsGrid,txnList,txtOtherIdentity,ddAddProof,
                                                                   processPanel2,illegalId,addAddressInfoPanel"
                                                                   oncomplete="if(#{CustomerDetail.badPersonAlertFlag=='true'})
                                                                   {#{rich:component('processPanel2')}.show();}
                                                                   else{#{rich:component('processPanel2')}.hide();}"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel')}.hide();"/>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>


                        <rich:modalPanel id="processPanel2" autosized="true" width="300" onshow="#{rich:element('btnNo2')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Illegal activities Alert !" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr style="height:60px">
                                            <td colspan="2">
                                                <h:outputText id="illegalId" value="#{CustomerDetail.illegalMsg}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="Yes" id="btnYes2" action="#{OperationCustomerMaster.callFromBadPersonAlertButton}" 
                                                                   onclick="#{rich:component('processPanel2')}.hide();" 
                                                                   reRender="stxtError,funcRow1,funcRow2,persInfoPanel,genInfoPanel,
                                                                   genInfoPanel1,addrInfoPanel,CurrInfoPanel,MISInfoPanel,MinInfoPanel,
                                                                   NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,BuySelLmtInfoPanel,
                                                                   othInfoPanel,taskList,taskList1,ddEducationDetails,checkSpouse,
                                                                   checkParents,checkChildren,lblChild,txtChildren,txtNoMales1,
                                                                   txtNoMales2,txtNoMales3,txtNoMales4,txtNoMales5,txtNoMales6,
                                                                   txtNoFemales1,txtNoFemales2,txtNoFemales3,txtNoFemales4,
                                                                   txtNoFemales5,txtNoFemales6,txtRelativeName1,txtRelativeName2,
                                                                   txtRelativeName3,txtRelativeAddress1,txtRelativeAddress2,
                                                                   txtRelativeAddress3,txtAbroad,ddRelativeInBank,ddCreditCardInfo,
                                                                   ddDirectorRelation,ddAssetsType,ddAssetOwnership,ddAssetValue,
                                                                   btnAdd1,taskList3,ddExistingLoans,txtLoanAmount,btnAdd2,taskList4,
                                                                   ddMedicalInsurance,ddRelPersonType,txtRelCustId,txtRelFirstName,
                                                                   txtRelMiddleName,txtRelLastName,txtRelPan,txtRelUid,txtNregaJobCard,
                                                                   txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,
                                                                   ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,
                                                                   txtJuriPostalCode,ddRelStatus,realtedPersonsGrid,txnList,txtOtherIdentity,
                                                                   ddCusttype,txtIdentityNo,txtIdExpiryDate,addAddressInfoPanel"/>
                                            </td>
                                            <td align="center" width="50%">
                                                <a4j:commandButton value="No" id="btnNo2" onclick="#{rich:component('processPanel2')}.hide();"/>
                                            </td>

                                        </tr>
                                    </tbody>
                                </table>
                            </h:form>
                        </rich:modalPanel>

                        <rich:modalPanel id="processPanel3" autosized="true" width="300" onshow="#{rich:element('btnNo3')}.focus();">
                            <f:facet name="header">
                                <h:outputText value="Customer Current Name Mismatch with Previous Name !" style="padding-right:15px;" />
                            </f:facet>
                            <h:form>
                                <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                    <tbody>
                                        <tr>
                                        <tr style="height:60px">
                                            <td colspan="2">
                                                <h:outputText style="color:#e51616;" id="illegalId1" value="Continue to save ?"/>
                                            </td>
                                        </tr>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="NO" id="btnNo3" onclick="#{rich:component('processPanel3')}.hide();"/>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton value="YES" id="btnYes3" action="#{OperationCustomerMaster.callFromCustFullNameMismatch}" 
                                                           onclick="#{rich:component('processPanel3')}.hide();" reRender="stxtError,funcRow1,persInfoPanel"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </h:form>
                </rich:modalPanel>
            </a4j:region>
            <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                <h:panelGroup id="btnPanel">
                    <a4j:region id="btnRegion">
                        <a4j:commandButton disabled="#{CustomerDetail.flagForSelect}" reRender="stxtError,funcRow1,
                                           funcRow2,persInfoPanel,genInfoPanel,genInfoPanel1,addrInfoPanel,CurrInfoPanel,
                                           MISInfoPanel,MinInfoPanel,NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,
                                           BuySelLmtInfoPanel,othInfoPanel,taskList,taskList1,ddEducationDetails,
                                           checkSpouse,checkParents,checkChildren,lblChild,txtChildren,txtNoMales1,
                                           txtNoMales2,txtNoMales3,txtNoMales4,txtNoMales5,txtNoMales6,txtNoFemales1,
                                           txtNoFemales2,txtNoFemales3,txtNoFemales4,txtNoFemales5,txtNoFemales6,
                                           txtRelativeName1,txtRelativeName2,txtRelativeName3,txtRelativeAddress1,
                                           txtRelativeAddress2,txtRelativeAddress3,txtAbroad,ddRelativeInBank,
                                           ddCreditCardInfo,ddDirectorRelation,ddAssetsType,ddAssetOwnership,
                                           ddAssetValue,btnAdd1,taskList3,ddExistingLoans,txtLoanAmount,btnAdd2,
                                           taskList4,ddMedicalInsurance,ddRelPersonType,txtRelCustId,txtRelFirstName,
                                           txtRelMiddleName,txtRelLastName,txtRelPan,txtRelUid,txtNregaJobCard,
                                           txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,
                                           ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,
                                           txtJuriPostalCode,ddRelStatus,realtedPersonsGrid,txnList,processPanel,confirmid,processPanel2,illegalId,txtOtherIdentity,processPanel3,addAddressInfoPanel" 
                                           id="btnSave" value="Save" action="#{OperationCustomerMaster.callFromMainButton}" 
                                           oncomplete="if(#{CustPersonalDetails.custFullNameMismatchFlag=='false'})
                                           {#{rich:component('processPanel3')}.show();}
                                           else{#{rich:component('processPanel3')}.hide();}
                                           if(#{CustomerDetail.chqAlertFlag=='true'})
                                           {#{rich:component('processPanel')}.show();}
                                           else{#{rich:component('processPanel')}.hide();}
                                           if(#{CustomerDetail.badPersonAlertFlag=='true'})
                                           {#{rich:component('processPanel2')}.show();}
                                           else{#{rich:component('processPanel2')}.hide();}">
                        </a4j:commandButton>
                        <a4j:commandButton  id="btnCancel" value="Cancel" action="#{OperationCustomerMaster.refreshAllTabs}" reRender="stxtError,txtRelCustId,funcRow1,funcRow2,persInfoPanel,genInfoPanel,genInfoPanel1,addrInfoPanel,CurrInfoPanel,MISInfoPanel,MinInfoPanel,NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,BuySelLmtInfoPanel,othInfoPanel,taskList,taskList1,empInfoPanel,ddRelPersonType,txtRelCustId,txtRelFirstName,txtRelMiddleName,txtRelLastName,
                                            txtRelPan,txtRelUid,txtNregaJobCard,txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,txtJuriPostalCode,ddRelStatus,realtedPersonsGrid,txnList,txtOtherIdentity,ddAddProof,addAddressInfoPanel,ddRelPersonType1,txtRelCustId1,stxtRelCustName1,row1,row2,realtedPersonsGrid,txnList1,ddFunction1,txtVid"/>
                        <a4j:commandButton id="btnExit" value="Exit" action="#{CustomerDetail.exitForm}" reRender="stxtError,txtRelCustId,funcRow1,funcRow2,persInfoPanel,genInfoPanel,genInfoPanel1,addrInfoPanel,CurrInfoPanel,MISInfoPanel,MinInfoPanel,NreInfoPanel,trFinInfoPanel,RelPerInfoPanel,BuySelLmtInfoPanel,othInfoPanel,taskList,taskList1,ddEducationDetails,checkSpouse,checkParents,checkChildren,lblChild,txtChildren,txtNoMales1,txtNoMales2,txtNoMales3,txtNoMales4,txtNoMales5,txtNoMales6,txtNoFemales1,txtNoFemales2,txtNoFemales3,txtNoFemales4,txtNoFemales5,txtNoFemales6,
                                           txtRelativeName1,txtRelativeName2,txtRelativeName3,txtRelativeAddress1,txtRelativeAddress2,txtRelativeAddress3,
                                           txtAbroad,ddRelativeInBank,ddCreditCardInfo,ddDirectorRelation,ddAssetsType,ddAssetOwnership,ddAssetValue,btnAdd1,
                                           taskList3,ddExistingLoans,txtLoanAmount,btnAdd2,taskList4,ddMedicalInsurance,ddRelPersonType,txtRelCustId,txtRelFirstName,txtRelMiddleName,txtRelLastName,
                                           txtRelPan,txtRelUid,txtNregaJobCard,txtPassNo,txtPassExpDt,txtVoterIdCard,txtDrLicNo,DlExpiry,txtDin,ddExposed,txtRelAddr1,txtRelAddr2,ddRelCity,ddRelState,ddRelCountry,txtJuriPostalCode,ddRelStatus,realtedPersonsGrid,txnList,txtOtherIdentity,ddAddProof,addAddressInfoPanel"/>
                    </a4j:region>
                </h:panelGroup>
            </h:panelGrid>
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="btnRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
            <a4j:status onstart="#{rich:component('waitOne')}.show()" onstop="#{rich:component('waitOne')}.hide()" for="alertRegion"/>
            <rich:modalPanel id="waitOne" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </h:panelGrid>
    </a4j:form>
</body>
</html>
</f:view>

