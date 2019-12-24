<%-- 
    Document   : CKYCRCustomerDetails
    Created on : 23 Feb, 2017, 1:19:32 PM
    Author     : root
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html>
<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>CKYCR Customer Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js"/>
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                jQuery(function(jQuery) {
                    setMask();
                });
                function setMask() {
                    jQuery(".dobInput").mask("99/99/9999");
                    
                }
            </script>
        </head>
        <body>
            <a4j:form id="ckycrCustomerDetails" enctype="multipart/form-data">
                <h:panelGrid id="mainPanel" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{CKYCRCustomerDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label" styleClass="headerLabel" value="CKYCR Customer Details"/>
                        <h:panelGroup id="groupPanel1" layout="block">
                            <h:outputLabel id="label4" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{CKYCRCustomerDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="custMsg" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:center;" styleClass="row2">
                        <h:outputText id="Msg" styleClass="label" value="#{CKYCRCustomerDetails.msg}" style="color:#f22813;"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col1" columns="6" id="gridPanel0" width="100%" styleClass="row2">
                        <h:panelGroup>
                            <h:outputLabel id="lblRequestType" styleClass="label" value="Request Type"/>
                            <h:selectOneListbox id="ddRequestType" value="#{CKYCRCustomerDetails.requestType}"  styleClass="ddlist" size="1" 
                                                style="width:100px;" >
                                <f:selectItems value="#{CKYCRCustomerDetails.requestTypeList}"/>
                                <a4j:support  action="#{CKYCRCustomerDetails.onChangeRequestType()}" event="onblur"  reRender="gridPanel0,a6,custMsg"  oncomplete="setMask();"/>
                            </h:selectOneListbox> 
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel id="lblIdType" styleClass="label" value="Id Type" style="display:#{CKYCRCustomerDetails.displayOnSearch}"/>
                            <h:outputLabel value="CKYCR NO." id="ckycrLabel" styleClass="label"  style="display:#{CKYCRCustomerDetails.displayOnDownload}" />
                            <h:selectOneListbox id="ddIdType" value="#{CKYCRCustomerDetails.idType}"  
                                                styleClass="ddlist" size="1" style="width:100px;display:#{CKYCRCustomerDetails.displayOnSearch}" >
                                <f:selectItems value="#{CKYCRCustomerDetails.idTypeList}"/>
                            </h:selectOneListbox> 
                            <h:inputText  value="#{CKYCRCustomerDetails.ckycrNoDwn}" id="txtCKYCRNo" maxlength="14" 
                                          styleClass="input" style="width:140px;display:#{CKYCRCustomerDetails.displayOnDownload}">
                                <a4j:support event="onblur"   />
                            </h:inputText>
                        </h:panelGroup>
                        <h:panelGroup>
                            <h:outputLabel value="Identity No." id="IdNoLabel" styleClass="label" style="display:#{CKYCRCustomerDetails.displayOnSearch}"/>
                            <h:outputLabel value="Date Of Birth" id="dobInputLabel" styleClass="label" style="display:#{CKYCRCustomerDetails.displayOnDownload}" />
                            <h:outputLabel value="CKYCR NO." id="ckycrViewLabel" styleClass="label"  style="display:#{CKYCRCustomerDetails.displayOnView}" />
                            <h:inputText id="txtIdNo" styleClass="input"  value="#{CKYCRCustomerDetails.idNo}" maxlength="25" style="display:#{CKYCRCustomerDetails.displayOnSearch}"/> 
                            <h:inputText id="dobInput" styleClass="input dobInput" value="#{CKYCRCustomerDetails.dobInput}" maxlength="10"
                                         style="width:100px;display:#{CKYCRCustomerDetails.displayOnDownload};setMask();"> 
                                <a4j:support event="onblur"  oncomplete="setMask();"/>
                            </h:inputText>
                            <h:inputText  value="#{CKYCRCustomerDetails.ckycrNo}" id="txtViewCKYCRNo" maxlength="14" 
                                          styleClass="input" style="width:140px;display:#{CKYCRCustomerDetails.displayOnView}">
                                <a4j:support event="onblur"   />
                            </h:inputText>
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid columns="2" id="a6" style="height:30px;" styleClass="footer" columnClasses="col7,col7,col7" width="100%">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <h:panelGroup>
                                <a4j:commandButton id="btngenerateCustId" value="Generate Customer Id" action="#{CKYCRCustomerDetails.onClickGenerateCustId()}" style="display:#{CKYCRCustomerDetails.displayGenerateCustId}" reRender="gridPanel0,customerDetailGrid,custMsg,a6"/>
                                <a4j:commandButton id="btnSearch" value="SEARCH" action="#{CKYCRCustomerDetails.onSubmitSearchCkyc()}" style="display:#{CKYCRCustomerDetails.displayOnSearch}" reRender="gridPanel0,customerDetailGrid,custMsg,a6"/>
                                <a4j:commandButton id="btnDownload" value="DOWNLOAD" action="#{CKYCRCustomerDetails.onSubmitDownloadCkyc()}" style="display:#{CKYCRCustomerDetails.displayOnDownload}" reRender="gridPanel0,customerDetailGrid,custMsg,a6,processPanel2"
                                                   oncomplete="if(#{CKYCRCustomerDetails.iscKYCRExistCheckFlag()=='true'})
                                                   {#{rich:component('processPanel2')}.show();}
                                                   else{#{rich:component('processPanel2')}.hide();}" />
                                <a4j:commandButton id="btnView" value="VIEW" action="#{CKYCRCustomerDetails.onSubmitView()}" style="display:#{CKYCRCustomerDetails.displayOnView}" reRender="gridPanel0,customerDetailGrid,custMsg,a6"/>
                            </h:panelGroup>
                            <a4j:commandButton id="btnRefresh" value="Refresh" action="#{CKYCRCustomerDetails.refreshPage()}" oncomplete="setMask();" reRender="gridPanel0,customerDetailGrid,custMsg,a6"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{CKYCRCustomerDetails.exitForm}"/>
                        </h:panelGroup>
                    </h:panelGrid>


                    <h:panelGrid id="customerDetailGrid" columns="2" width="100%" style="background-color:#edebeb;" columnClasses="vtop">
                        <rich:panel  header="CKYCR Document Images" id="customerSearchImageGroup" style="display:#{CKYCRCustomerDetails.displaySearchDetails}" >
                            <rich:panel  style="text-align:left;height:150px;overflow:auto;">                                                     
                                <h:graphicImage  value="/ckycr/dynamic/?file=#{CKYCRCustomerDetails.requestId}&rqId=#{CKYCRCustomerDetails.requestId}" width="150px" height="200px"  />                                    
                            </rich:panel>
                        </rich:panel>

                        <h:panelGroup layout="block" >

                            <rich:panel header="CKYCR Search Deatils" id="customerSearchDetailGrop"  style="display:#{CKYCRCustomerDetails.displaySearchDetails}">
                                <h:panelGrid id="custDetailSearchRow1" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblRequestDate" styleClass="label" value="Request Date"/>
                                    <h:outputText id="txtRequestDate" value="#{CKYCRCustomerDetails.requestDate}"/>
                                    <h:outputLabel id="lblRequestId" styleClass="label" value="Request Id"/>
                                    <h:outputText id="txtRequestId" value="#{CKYCRCustomerDetails.requestId}"/>
                                </h:panelGrid>
                                <h:panelGrid id="custDetailSearchRow2" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblCKYCNo" styleClass="label" value="CKYC No"/>
                                    <h:outputText id="txtCKYCNo" value="#{CKYCRCustomerDetails.getcKYCNo()}"/>
                                    <h:outputLabel id="lblUpdatedDate" styleClass="label" value="Updated Date"/>
                                    <h:outputText id="txtUpdatedDate" value="#{CKYCRCustomerDetails.updatedDate}"/>
                                </h:panelGrid>
                                <h:panelGrid id="custDetailSearchRow3" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblName" styleClass="label" value="Name"/>
                                    <h:outputText id="txtName" value="#{CKYCRCustomerDetails.name}"/>
                                    <h:outputLabel id="lblFatherName" styleClass="label" value="Father Name"/>
                                    <h:outputText id="txtFatherName" value="#{CKYCRCustomerDetails.fatherName}"/>
                                </h:panelGrid>
                                <h:panelGrid id="custDetailSearchRow4" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblAge" styleClass="label" value="Age"/>
                                    <h:outputText id="txtAge" value="#{CKYCRCustomerDetails.age}"/>
                                    <h:outputLabel id="lblKycDate" styleClass="label" value="Kyc Date"/>
                                    <h:outputText id="txtKycDate" value="#{CKYCRCustomerDetails.kycDate}"/>
                                </h:panelGrid>
                                <h:panelGrid id="custDetailSearchRow5" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel id="lblReason" styleClass="label" value="Reason"/>
                                    <h:outputText id="txtReason" value="#{CKYCRCustomerDetails.reason}"/>
                                </h:panelGrid>
                            </rich:panel>
                        </h:panelGroup>

                        <rich:panel  header="CKYCR Document Images" id="customerImageGrop"  style="display:#{CKYCRCustomerDetails.displayDownloadViewDetails}" >
                            <a4j:repeat value="#{CKYCRCustomerDetails.fileNames}" var="item" >
                                <rich:panel  style="text-align:left;height:150px;overflow:auto;">                                                     
                                    <h:graphicImage  value="/ckycr/dynamic/?file=#{item}&ckycNo=#{CKYCRCustomerDetails.ckycrNo}" width="150px" height="200px"  />                                    
                                </rich:panel>
                            </a4j:repeat>
                        </rich:panel>

                        <h:panelGroup layout="block" >
                            <rich:panel header="CKYCR Deatils" id="customerDetailGrop"  style="display:#{CKYCRCustomerDetails.displayDownloadViewDetails}" >
                                <rich:panel header="Personal/Entity Details" style="text-align:left;" id="PerDetails">
                                    <h:panelGrid id="custDetailRow1" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblConst" styleClass="label" value="Constitution Type"/>
                                        <h:outputText id="txtConst" value="#{CKYCRCustomerDetails.constitutionType}"/>
                                        <h:outputLabel id="lblCustFullName" styleClass="label" value="Customer Name"/>
                                        <h:outputText id="txtCustFullName" value="#{CKYCRCustomerDetails.custFullName}"/>
                                    </h:panelGrid>
                                    <h:panelGroup layout="block" style="width:100%;text-align:center; display:#{CKYCRCustomerDetails.displayOnIndividual};">
                                        <h:panelGrid id="custDetailRow2" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel id="lblFathSpouseName" styleClass="label" value="Father/Spouse Name"/>
                                            <h:outputText id="txtFathSpouseName" value="#{CKYCRCustomerDetails.fatherSpouseFullName}"/>
                                            <h:outputLabel id="lblMothName" styleClass="label" value="Mother Name"/>
                                            <h:outputText id="txtMothName" value="#{CKYCRCustomerDetails.motherFullName}"/>
                                        </h:panelGrid>
                                        <h:panelGrid id="custDetailRow3" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lbldob" styleClass="label" value="Date Of Birth"/>
                                            <h:outputText id="txtdob" value="#{CKYCRCustomerDetails.dateOfBirth}"/>
                                            <h:outputLabel id="lblGender" styleClass="label" value="Gender"/>
                                            <h:outputText id="txtGender" value="#{CKYCRCustomerDetails.gender}"/>
                                        </h:panelGrid>
                                        <h:panelGrid id="custDetailRow4" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel id="lblMaritalSts" styleClass="label" value="Marital Status"/>
                                            <h:outputText id="txtMaritalSts" value="#{CKYCRCustomerDetails.maritalStatus}"/>
                                            <h:outputLabel id="lblOccupation" styleClass="label" value="Occupation"/>
                                            <h:outputText id="txtOccupation" value="#{CKYCRCustomerDetails.occupationType}"/>
                                        </h:panelGrid>
                                        <h:panelGrid id="custDetailRow6" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lblNationality" styleClass="label" value="Nationality"/>
                                            <h:outputText id="txtNationality" value="#{CKYCRCustomerDetails.nationality}"/>
                                            <h:outputLabel id="lblAcType" styleClass="label" value="Account Type"/>
                                            <h:outputText id="txtAcType" value="#{CKYCRCustomerDetails.accType}"/>
                                        </h:panelGrid>
                                    </h:panelGroup>
                                </rich:panel>
                                <h:panelGroup  style=" display:#{CKYCRCustomerDetails.displayOnEntity};">
                                    <rich:panel header="Additional Details" style="text-align:left;" id="AddDetails">
                                        <h:panelGrid id="AddDetails1" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel id="lblAcTypeHoldFlag" styleClass="label" value="Account Holder Type Flag"/>
                                            <h:outputText id="txtAcTypeHoldFlag" value="#{CKYCRCustomerDetails.accHolderTypeFlag}"/>
                                            <h:outputLabel id="lblAcHolderType" styleClass="label" value="Account Holder Type"/>
                                            <h:outputText id="txtAcHolderType" value="#{CKYCRCustomerDetails.accHolderType}"/>      
                                        </h:panelGrid>
                                        <h:panelGrid id="AddDetails2" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lblDateOfIncop" styleClass="label" value="Date Of Incorporation"/>
                                            <h:outputText id="txtDateOfIncop" value="#{CKYCRCustomerDetails.dateOfBirth}"/>
                                            <h:outputLabel id="lblPlaceOfIncorp" styleClass="label" value="Place Of Incorporation"/>
                                            <h:outputText id="txtPlaceOfIncorp" value="#{CKYCRCustomerDetails.placeOfIncorporation}"/>                             
                                        </h:panelGrid>
                                        <h:panelGrid id="AddDetails3" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel id="lblDateOfComcc" styleClass="label" value="Date of Commencement of business"/>
                                            <h:outputText id="txtDateOfComcc" value="#{CKYCRCustomerDetails.dateOfCommBusiness}"/>  
                                            <h:outputLabel id="lblCountryOfIncorp" styleClass="label" value="Country of Incorporation"/>
                                            <h:outputText id="txtCountryOfIncorp" value="#{CKYCRCustomerDetails.countryOfIncorporation}"/>  
                                        </h:panelGrid>
                                        <h:panelGrid id="AddDetails4" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel id="lblCountryOfResiTax" styleClass="label" value="Country of residence as per Tax Laws"/>
                                            <h:outputText id="txtCountryOfResiTax" value="#{CKYCRCustomerDetails.residenceCountryTaxLaw}"/>                    
                                            <h:outputLabel/>
                                            <h:outputLabel/>
                                        </h:panelGrid>
                                    </rich:panel>
                                </h:panelGroup>
                                <rich:panel header="Contact Details" style="text-align:left;" id="contactDetail">
                                    <h:panelGrid id="contactDetailRow1" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblMobNo" styleClass="label" value="Mobile No"/>
                                        <h:outputText id="txtMobNo" value="#{CKYCRCustomerDetails.mobileNo}"/>                             
                                        <h:outputLabel id="lblEmail" styleClass="label" value="Email"/>
                                        <h:outputText id="txtEmail" value="#{CKYCRCustomerDetails.emailID}"/>
                                    </h:panelGrid>
                                    <h:panelGrid id="contactDetailRow2" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblResiTel" styleClass="label" value="Residencial Tel"/>
                                        <h:outputText id="txtResiTel" value="#{CKYCRCustomerDetails.residenceTelNo}"/>                             
                                        <h:outputLabel id="lblOffcTel" styleClass="label" value="Office Tel"/>
                                        <h:outputText id="txtOffcTel" value="#{CKYCRCustomerDetails.officeTelNo}"/>
                                    </h:panelGrid>
                                    <h:panelGrid id="contactDetailRow3" columnClasses="col2,col7,col2,col7" columns="4"  style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblFax" styleClass="label" value="Fax"/>
                                        <h:outputText id="txtFax" value="#{CKYCRCustomerDetails.faxNo}"/>                             
                                        <h:outputLabel/>
                                        <h:outputLabel/>
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="Permanent Address" style="text-align:left;" id="PerAddress">
                                    <h:panelGrid id="PerAddressRow1" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPerAddLine1" styleClass="label" value="Address Line 1"/>
                                        <h:outputText id="txtPerAddLine1" value="#{CKYCRCustomerDetails.perAddressLine1}"/>                             
                                        <h:outputLabel id="lblPerAddLine2" styleClass="label" value="Address Line 2"/>
                                        <h:outputText id="txtPerAddLine2" value="#{CKYCRCustomerDetails.peraddressline2}"/>  
                                        <h:outputLabel id="lblPerAddLine3" styleClass="label" value="Address Line 3"/>
                                        <h:outputText id="txtPerAddLine3" value="#{CKYCRCustomerDetails.peraddressline3}"/>  
                                    </h:panelGrid>
                                    <h:panelGrid id="PerAddressRow2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblPerCityVill" styleClass="label" value="City/Town/Village"/>
                                        <h:outputText id="txtPerCityVill" value="#{CKYCRCustomerDetails.perCityVillage}"/>                             
                                        <h:outputLabel id="lblPerDist" styleClass="label" value="District"/>
                                        <h:outputText id="txtPerDist" value="#{CKYCRCustomerDetails.perDistrict}"/>  
                                        <h:outputLabel id="lblPerState" styleClass="label" value="State"/>
                                        <h:outputText id="txtPerState" value="#{CKYCRCustomerDetails.perState}"/>  
                                    </h:panelGrid>
                                    <h:panelGrid id="PerAddressRow3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblPerPostalCode" styleClass="label" value="Postal Code"/>
                                        <h:outputText id="txtPerPostalCode" value="#{CKYCRCustomerDetails.perPostalCode}"/>                             
                                        <h:outputLabel id="lblPerAddType" styleClass="label" value="Address Type"/>
                                        <h:outputText id="txtPerAddType" value="#{CKYCRCustomerDetails.perAddType}"/>  
                                        <h:outputLabel id="lblPerpoa" styleClass="label" value="Proof Of Address"/>
                                        <h:outputText id="txtPerpoa" value="#{CKYCRCustomerDetails.perPOA}"/>  
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="Mailing Address" style="text-align:left;" id="MailAddress">
                                    <h:panelGrid id="MailAddressRow1" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblMailAddLine1" styleClass="label" value="Address Line 1"/>
                                        <h:outputText id="txtMailAddLine1" value="#{CKYCRCustomerDetails.mailAddressLine1}"/>                             
                                        <h:outputLabel id="lblMailAddLine2" styleClass="label" value="Address Line 2"/>
                                        <h:outputText id="txtMailAddLine2" value="#{CKYCRCustomerDetails.mailAddressLine2}"/>  
                                        <h:outputLabel id="lblMailAddLine3" styleClass="label" value="Address Line 3"/>
                                        <h:outputText id="txtMailAddLine3" value="#{CKYCRCustomerDetails.mailAddressLine2}"/>  
                                    </h:panelGrid>
                                    <h:panelGrid id="MailAddressRow2" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblMailCityVill" styleClass="label" value="City/Town/Village"/>
                                        <h:outputText id="txtMailCityVill" value="#{CKYCRCustomerDetails.mailCityVillage}"/>                             
                                        <h:outputLabel id="lblMailDist" styleClass="label" value="District"/>
                                        <h:outputText id="txtMailDist" value="#{CKYCRCustomerDetails.mailDistrict}"/>  
                                        <h:outputLabel id="lblMailState" styleClass="label" value="State"/>
                                        <h:outputText id="txtMailState" value="#{CKYCRCustomerDetails.mailState}"/>  
                                    </h:panelGrid>
                                    <h:panelGrid id="MailAddressRow3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblMailPostalCode" styleClass="label" value="Postal Code"/>
                                        <h:outputText id="txtMailPostalCode" value="#{CKYCRCustomerDetails.mailPostalCode}"/>                             
                                        <h:outputLabel id="lblMailAddType" styleClass="label" value="Address Type"/>
                                        <h:outputText id="txtMailAddType" value="#{CKYCRCustomerDetails.mailAddType}"/>  
                                        <h:outputLabel id="lblMailpoa" styleClass="label" value="Proof Of Address"/>
                                        <h:outputText id="txtMailpoa" value="#{CKYCRCustomerDetails.mailPOA}"/>  
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="Juridiction Address" style="text-align:left;" id="JuriAddress">
                                    <h:panelGrid id="JuriAddressRow1" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="lblJuriAddLine1" styleClass="label" value="Address"/>
                                        <h:outputText id="txtJuriAddLine1" value="#{CKYCRCustomerDetails.juriAddressLine1}"/>                             
                                        <h:outputLabel id="lblJuriCityVill" styleClass="label" value="City/Town/Village"/>
                                        <h:outputText id="txtJuriCityVill" value="#{CKYCRCustomerDetails.juriCityVillage}"/> 
                                        <h:outputLabel id="lblJuriState" styleClass="label" value="State"/>
                                        <h:outputText id="txtJuriState" value="#{CKYCRCustomerDetails.juriState}"/>  
                                    </h:panelGrid>
                                    <h:panelGrid id="JuriAddressRow3" columnClasses="col3,col3,col3,col3,col3,col1" columns="6"  style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel id="lblJuriPostalCode" styleClass="label" value="Postal Code"/>
                                        <h:outputText id="txtJuriPostalCode" value="#{CKYCRCustomerDetails.juriPostCode}"/>                             
                                        <h:outputLabel id="lblJuriAddType" styleClass="label" value="Address Type"/>
                                        <h:outputText id="txtJuriAddType" value="#{CKYCRCustomerDetails.juriAddType}"/>  
                                        <h:outputLabel id="lblJuripoa" styleClass="label" value="Proof Of Address"/>
                                        <h:outputText id="txtJuripoa" value="#{CKYCRCustomerDetails.juriPOA}"/>  
                                    </h:panelGrid>
                                </rich:panel>
                            </rich:panel>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>

                <a4j:region id="alertRegion">
                    <rich:modalPanel id="processPanel2" autosized="true" width="250" onshow="#{rich:element('btnNo')}.focus();">
                        <f:facet name="header">
                            <h:outputText value="CKYC Data Exist!" style="padding-right:15px;" />
                        </f:facet>
                        <h:form>
                            <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                                <tbody>
                                    <tr style="height:40px">
                                        <td colspan="2">
                                            <h:outputText id="confirmid1" value="CKYC Data Already Downloded."/><br>
                                            <h:outputText id="confirmid" value="Press 'Yes' to Continue Download Process,'No' to Cancle !"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="Yes" id="btnYes" action="#{CKYCRCustomerDetails.callFromDownloadCkycAlert}" 
                                                               onclick="#{rich:component('processPanel2')}.hide();" 
                                                               reRender="gridPanel0,customerDetailGrid,custMsg,a6,processPanel2"/>
                                        </td>
                                        <td align="center" width="50%">
                                            <a4j:commandButton value="No" id="btnNo" onclick="#{rich:component('processPanel2')}.hide();"/>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h:form>
                    </rich:modalPanel>
                </a4j:region>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="alertRegion"/>
                <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" />
                <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                    <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                    </f:facet>
                </rich:modalPanel>
            </a4j:form>  
        </body>
    </html>
</f:view>