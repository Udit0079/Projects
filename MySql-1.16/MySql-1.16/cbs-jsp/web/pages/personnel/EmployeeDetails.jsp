
<%--
 Document : EmployeeDetails
 Created on : Jul 27, 2011, 2:33:04 PM
 Author : Administrator
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
            <title>Employee Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                    setTimeMask();
                });
                function setMask(){
                    jQuery(".calInstDate").mask("99/99/9999");
                }
                function setTimeMask(){
                    jQuery(".calInstTime").mask("99:99");
                }
            </script>
        </head>
        <body>
            <a4j:form>
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headepanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{EmployeeDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Employee Details"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{EmployeeDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <rich:tabPanel>
                        <rich:tab id="GeneralDetailsTab" label="Employee Details" switchType="client">
                            <rich:panel header="General Details" style="text-align:left;">
                                <h:panelGrid id="leftPanel" style="width:100%;text-align:center;">

                                    <h:panelGrid columnClasses="col2" columns="2" id="errPanel" style="text-align:center;" width="100%" >
                                        <h:outputText id="errMsgGeneralDetails" value="#{EmployeeDetails.message}" styleClass="error"/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row0" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Function "><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddFunction" value="#{EmployeeDetails.function}" styleClass="ddlist" style="width:100px" size="1">
                                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                            <f:selectItem itemValue="save" itemLabel="Add"/>
                                            <f:selectItem itemValue="update" itemLabel="Edit"/>
                                            <a4j:support event="onblur" action="#{EmployeeDetails.onChangeFunction}"
                                                         oncomplete="if(#{EmployeeDetails.function=='update'})
                                                         {
                                                         #{rich:component('popUpGridPanel')}.show();
                                                         }
                                                         if(#{EmployeeDetails.function=='save'})
                                                         {
                                                         #{rich:component('popUpGridPanel')}.hide();
                                                         };
                                                         #{rich:element('calDateofbirth')}.style=setMask();
                                                         #{rich:element('calJoiningDate')}.style=setMask();"
                                                         reRender="mainPanel,popUpGridPanel,saveGeneralDetails,deleteGeneralDetails,errMsgGeneralDetails,mainPanelEmployeeSearch1,txtEmpIdGeneralDetails,txtLastEmpIdGeneralDetails,txtIdEmpOtherDetails,txtEmpNameEmpOtherDetails,calDateofbirth,calJoiningDate"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Emp Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtEmpIdGeneralDetails" value="#{EmployeeDetails.empId}" maxlength="8" disabled="true" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:outputLabel styleClass="label" value="Last Emp Id"/>
                                        <h:inputText id="txtLastEmpIdGeneralDetails" value="#{EmployeeDetails.lastEmpId}" styleClass="input" disabled="true" style="width:100px;"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Employee Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtEmployeeFName" value="#{EmployeeDetails.empFName}" maxlength="30" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:inputText id="txtEmployeeMName" value="#{EmployeeDetails.empMName}" maxlength="30" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:inputText id="txtEmployeeLName" value="#{EmployeeDetails.empLName}" maxlength="40" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:outputLabel styleClass="label" value="Employee Card No."/>
                                        <h:inputText id="txtEmpCardNo" value="#{EmployeeDetails.empCardNo}" maxlength="6" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Date of birth"/>
                                        <h:inputText id="calDateofbirth" value="#{EmployeeDetails.dateOfBirth}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                        <h:outputLabel styleClass="label" value="Joining Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="calJoiningDate" value="#{EmployeeDetails.joiningDate}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                        <h:outputLabel styleClass="label" value="Sex"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddSex" value="#{EmployeeDetails.sex}" styleClass="ddlist" size="1" style="width:100px;" >
                                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                            <f:selectItem itemValue="M" itemLabel="Male"/>
                                            <f:selectItem itemValue="F" itemLabel="Female"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Status"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddStatus" value="#{EmployeeDetails.workStatusCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.workStatusList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Block"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddBlock" value="#{EmployeeDetails.blockCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.blockList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Unit"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddUnit" value="#{EmployeeDetails.unitCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.unitList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Grade"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddGrade" value="#{EmployeeDetails.gradeCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.gradeList}"/>
                                            <%--   <a4j:support event="onblur" action="#{EmployeeDetails.getDesignationListValues}" reRender="ddDesignation" focus="ddEmployeeType"/> --%>
                                            <a4j:support event="onblur"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Designation"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddDesignation" value="#{EmployeeDetails.designationCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.designationList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Employee Type"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddEmployeeType" value="#{EmployeeDetails.employeeTypeCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.employeeTypeList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row7" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddCategory" value="#{EmployeeDetails.categoryCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.categoryList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Zone"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddZone" value="#{EmployeeDetails.zoneCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.zoneList}"/>
                                            <a4j:support event="onblur" action="#{EmployeeDetails.getLocations}" reRender="ddLocation" focus="ddDepartment"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Location"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddLocation" value="#{EmployeeDetails.locationCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.locationList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row8" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Department"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddDepartment" value="#{EmployeeDetails.departmentCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.departmentList}"/>
                                            <a4j:support event="onblur" action="#{EmployeeDetails.getSubDepartments}" reRender="ddSubDepartment" focus="ddBank"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Sub Department"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddSubDepartment" value="#{EmployeeDetails.subDepartmentCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.subDepartmentList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Bank"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddBank" value="#{EmployeeDetails.bankCode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItems value="#{EmployeeDetails.bankList}"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row9" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Payment Mode"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddPaymentMode" value="#{EmployeeDetails.paymentMode}" styleClass="ddlist" size="1" style="width:100px;">
                                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                            <f:selectItem itemValue="CH" itemLabel="BY CHEQUE"/>
                                            <f:selectItem itemValue="CS" itemLabel="BY CASH"/>
                                            <f:selectItem itemValue="BK" itemLabel="BY BANK"/>
                                            <f:selectItem itemValue="DD" itemLabel="BY DD"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Account No."><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtAccountNo" value="#{EmployeeDetails.bankAccountNo}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="12"/>
                                        <h:outputLabel styleClass="label" value="Finance Loan A/C Code"></h:outputLabel>
                                        <h:inputText id="txtFinanceLoanAccountCode" value="#{EmployeeDetails.financeLoanAccountCode}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="8"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3" columns="2" id="Row11" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Membership"></h:outputLabel>
                                        <h:selectManyCheckbox id="membershipGroup" value="#{EmployeeDetails.membershipGroup}">
                                            <f:selectItem itemValue="ESI" itemLabel="ESI"/>
                                            <f:selectItem itemValue="PF" itemLabel="PF"/>
                                            <f:selectItem itemValue="VPF" itemLabel="VPF"/>
                                            <f:selectItem itemValue="TRUST" itemLabel="TRUST"/>
                                        </h:selectManyCheckbox>
                                    </h:panelGrid>
                                </h:panelGrid>

                                <h:panelGrid id="Contact_moreDetailsPanel" columns="2"  columnClasses="col9,col9" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col3,col9,col15,col9" columns="4" id="Row23" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Contact Address"></h:outputLabel>
                                        <h:inputText id="txtConAddressLine" value="#{EmployeeDetails.conAddressLine}" styleClass="input" style="width:300px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="100"/>
                                        <h:outputLabel styleClass="label" value="City"></h:outputLabel>
                                        <h:inputText id="txtConCity" value="#{EmployeeDetails.conCity}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="15"/>

                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row24" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="State"></h:outputLabel>
                                        <h:inputText id="txtConState" value="#{EmployeeDetails.conState}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="15"/>
                                        <h:outputLabel styleClass="label" value="Pin"></h:outputLabel>
                                        <h:inputText id="txtConPin" value="#{EmployeeDetails.conPin}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="6"/>
                                        <h:outputLabel styleClass="label" value="Phone" ></h:outputLabel>
                                        <h:inputText id="txtConPhone" value="#{EmployeeDetails.conPhone}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="24"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid columns="2" columnClasses="col9,col9" id="Permanent_moreDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col3,col9,col15,col9" columns="4" id="Row251" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Permanent Address"></h:outputLabel>
                                        <h:inputText id="txtPerAddressLine" value="#{EmployeeDetails.perAddressLine}" styleClass="input" style="width:300px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="100"/>
                                        <h:outputLabel styleClass="label" value="City"></h:outputLabel>
                                        <h:inputText id="txtPerCity" value="#{EmployeeDetails.perCity}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="15"/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row26" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="State"></h:outputLabel>
                                        <h:inputText id="txtPerState" value="#{EmployeeDetails.perState}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="15"/>
                                        <h:outputLabel styleClass="label" value="Pin"></h:outputLabel>
                                        <h:inputText id="txtPerPin" value="#{EmployeeDetails.perPin}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="6"/>
                                        <h:outputLabel styleClass="label" value="Phone" ></h:outputLabel>
                                        <h:inputText id="txtPerPhone" value="#{EmployeeDetails.perPhone}" styleClass="input" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" maxlength="24"/>
                                    </h:panelGrid>
                                </h:panelGrid>
                                <h:panelGrid id="footerPanelGeneralDetails" style="width:100%;text-align:center;" styleClass="footer">
                                    <h:panelGroup id="btnPanelGeneralDetails">
                                        <a4j:commandButton id="saveGeneralDetails" value="Save" action="#{EmployeeDetails.saveGeneralDetails}"
                                                           reRender="txtEmpCardNo,txtSearchValue1,ddSearchCriteria1,ddFunction,txtEmpIdGeneralDetails, txtLastEmpIdGeneralDetails, txtEmployeeFName,txtEmployeeMName,txtEmployeeLName,calDateofbirth, calJoiningDate, ddSex, ddStatus, ddBlock, ddUnit, ddGrade, ddDesignation, ddEmployeeType, ddCategory, ddZone, ddLocation, ddDepartment, ddSubDepartment, ddBank, txtAccountNo, txtFinanceLoanAccountCode, ddPaymentMode, membershipGroup, txtConAddressLine, txtConCity, txtConState, txtConPin, txtConPhone, txtPerAddressLine, txtPerCity, txtPerState, txtPerPin, txtPerPhone, txtFHName, txtFHOccupation, txtFHDesignation, txtFHOrganisation, txtFHPhone, txtBirthCity, txtBirthState, txtNation, txtHeight, txtWeight, txtHealth, txtChest, ddBloodGroup1, txtChildren, ddMaritalStatus, calWeddingDate, txtReligion, txtEmailId, txtVisaDet, txtAdNumber, txtTokenNumber, calAdolescentDate, txtReferenceNo, txtRelay, calRelayDate, errMsgGeneralDetails, taskList1, table1,txtEmpIdOtherDetails,txtEmpNameEmpOtherDetails"
                                                           oncomplete="
                                                           #{rich:element('calDateofbirth')}.style=setMask();
                                                           #{rich:element('calJoiningDate')}.style=setMask();
                                                           #{rich:element('calWeddingDate')}.style=setMask();
                                                           #{rich:element('calAdolescentDate')}.style=setMask();
                                                           #{rich:element('calRelayDate')}.style=setMask();"
                                                           disabled="#{EmployeeDetails.disableSaveButton}"/>

                                        <a4j:commandButton id="cancelGeneralDetails" value="Cancel" action="#{EmployeeDetails.cancelGeneralDetails}"
                                                           oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                                                           #{rich:element('calJoiningDate')}.style=setMask();
                                                           #{rich:element('calWeddingDate')}.style=setMask();
                                                           #{rich:element('calAdolescentDate')}.style=setMask();
                                                           #{rich:element('calRelayDate')}.style=setMask();"
                                                           type="reset" reRender="mainPanel,txtEmpIdOtherDetails,txtEmpNameEmpOtherDetails"/>
                                        <a4j:commandButton id="exitGeneralDetails" value="Exit" action="#{EmployeeDetails.exitGeneralDetails}"/>
                                    </h:panelGroup>
                                </h:panelGrid>      
                            </rich:panel>
                        </rich:tab>
                        <rich:tab id="employeeOtherDetailTab" label="Employee's Other Detail" switchType="client" disabled="#{EmployeeDetails.disableTabs}">
                            <h:panelGrid id="empOtherDtlPanelGrid" style="width:100%;text-align:center;" >
                                <h:panelGrid columnClasses="col2" columns="2" id="errPanelEmpOther" style="text-align:center;" width="100%" styleClass="row2">
                                    <h:outputText id="errMsgEmpOtherDetails" value="#{EmployeeOtherDetail.message}" styleClass="error"/>
                                </h:panelGrid>  
                                <h:panelGrid id="idPanelEmpOtherDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Emp Id"/>
                                    <h:outputText id="txtIdEmpOtherDetails" value="#{EmployeeDetails.empId}" />
                                    <h:outputLabel styleClass="label" value="Emp Name"/>
                                    <h:outputText id="txtEmpNameEmpOtherDetails" value="#{EmployeeDetails.empName}"/>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                </h:panelGrid>   
                                <rich:panel header="Father's/Husband's Details" style="text-align:left;">
                                    <h:panelGrid style="width:100%;text-align:center;" id="father_husband_details_panel">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel styleClass="label" value="Name"/>
                                            <h:inputText id="txtFHName" value="#{EmployeeOtherDetail.fatherName_husbandName}" styleClass="input" maxlength="50" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:outputLabel styleClass="label" value="Occupation"/>
                                            <h:inputText id="txtFHOccupation" value="#{EmployeeOtherDetail.fatherName_husbandOcc}" styleClass="input" maxlength="25" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:outputLabel styleClass="label" value="Designation"/>
                                            <h:inputText id="txtFHDesignation" value="#{EmployeeOtherDetail.fatherName_husbandDesg}" styleClass="input" maxlength="25" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                        </h:panelGrid>
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel styleClass="label" value="Organisation"/>
                                            <h:inputText id="txtFHOrganisation" value="#{EmployeeOtherDetail.fatherName_husbandOrg}" styleClass="input" maxlength="25" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:outputLabel styleClass="label" value="Phone"/>
                                            <h:inputText id="txtFHPhone" value="#{EmployeeOtherDetail.fatherName_husbandPhone}" styleClass="input" maxlength="25" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:outputLabel/><h:outputLabel/>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="More Details" style="text-align:left;">
                                    <h:panelGrid id="_moreDetailsPanel" style="width:100%;text-align:center;">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row27" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel styleClass="label" value="Birth City"></h:outputLabel>
                                            <h:inputText id="txtBirthCity" value="#{EmployeeOtherDetail.birthCity}" styleClass="input" maxlength="15" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                            <h:outputLabel styleClass="label" value="Birth State"></h:outputLabel>
                                            <h:inputText id="txtBirthState" value="#{EmployeeOtherDetail.birthState}" styleClass="input" maxlength="20" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                            <h:outputLabel styleClass="label" value="Nation"/>
                                            <h:inputText id="txtNation" value="#{EmployeeOtherDetail.nation}" styleClass="input" maxlength="15" style="width:50px;" onkeyup="this.value = this.value.toUpperCase();" />
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row28" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel styleClass="label" value="Height"></h:outputLabel>
                                            <h:panelGroup>
                                                <h:inputText id="txtHeight" value="#{EmployeeOtherDetail.height}" styleClass="input" maxlength="6" style="width:50px;" onkeyup="this.value = this.value.toUpperCase();" />
                                                <h:outputLabel value="cms"/>
                                            </h:panelGroup>
                                            <h:outputLabel styleClass="label" value="Weight"></h:outputLabel>
                                            <h:panelGroup>
                                                <h:inputText id="txtWeight" value="#{EmployeeOtherDetail.weight}" styleClass="input" maxlength="6" style="width:50px;" onkeyup="this.value = this.value.toUpperCase();" />
                                                <h:outputLabel value="Kgs"/>
                                            </h:panelGroup >
                                            <h:outputLabel styleClass="label" value="Health"></h:outputLabel>
                                            <h:inputText id="txtHealth" value="#{EmployeeOtherDetail.health}" styleClass="input" maxlength="25" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row29" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel styleClass="label" value="Chest"/>
                                            <h:inputText id="txtChest" value="#{EmployeeOtherDetail.chest}" styleClass="input" maxlength="3" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                            <h:outputLabel styleClass="label" value="Blood Group"></h:outputLabel>
                                            <h:selectOneListbox id="ddBloodGroup1" value="#{EmployeeOtherDetail.bloodGroup}" styleClass="ddlist" size="1" style="width: 100px;" >
                                                <f:selectItem itemValue="0" itemLabel="--SELECT--"/>
                                                <f:selectItem itemValue="A+" itemLabel="A+"/>
                                                <f:selectItem itemValue="A-" itemLabel="A-"/>
                                                <f:selectItem itemValue="B+" itemLabel="B+"/>
                                                <f:selectItem itemValue="B-" itemLabel="B-"/>
                                                <f:selectItem itemValue="O+" itemLabel="O+"/>
                                                <f:selectItem itemValue="O-" itemLabel="O-"/>
                                                <f:selectItem itemValue="AB+" itemLabel="AB+"/>
                                                <f:selectItem itemValue="AB-" itemLabel="AB-"/>
                                            </h:selectOneListbox>
                                            <h:outputLabel styleClass="label" value="Religion"/>
                                            <h:inputText id="txtReligion" value="#{EmployeeOtherDetail.religion}" styleClass="input" maxlength="10" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row30" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel styleClass="label" value="Marital Status"/>
                                            <h:selectOneListbox id="ddMaritalStatus" value="#{EmployeeOtherDetail.maritalStatus}" styleClass="ddlist" size="1" style="width:100px;">
                                                <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                                <f:selectItem itemValue="U" itemLabel="Unmarried"/>
                                                <f:selectItem itemValue="M" itemLabel="Married"/>
                                                <f:selectItem itemValue="D" itemLabel="Divorcee"/>
                                                <f:selectItem itemValue="W" itemLabel="Widower"/>
                                                <a4j:support event="onblur" action="#{EmployeeOtherDetail.onChangeMaritalStatus}" reRender="calWeddingDate,txtChildren"
                                                             oncomplete="#{rich:element('calWeddingDate')}.style=setMask();"
                                                             focus="calWeddingDate"/>
                                            </h:selectOneListbox>
                                            <h:outputLabel styleClass="label" value="Wedding Date"/>
                                            <h:inputText id="calWeddingDate" value="#{EmployeeOtherDetail.weddingDate}" disabled="#{EmployeeOtherDetail.disableWeddingDate}" styleClass="input calInstDate"
                                                         style="width:80px;setMask()" maxlength="10"></h:inputText>
                                            <h:outputLabel styleClass="label" value="Children"/>
                                            <h:inputText id="txtChildren" value="#{EmployeeOtherDetail.children}" disabled="#{EmployeeOtherDetail.disableWeddingDate}" styleClass="input" maxlength="4" style="width:50px;" onkeyup="this.value = this.value.toUpperCase();" />
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row301" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel styleClass="label" value="Email Id"/>
                                            <h:inputText id="txtEmailId" value="#{EmployeeOtherDetail.emailId}" styleClass="input" maxlength="150" style="width:100px;"/>
                                            <h:outputLabel styleClass="label" value="Visa Detail"/>
                                            <h:inputText id="txtVisaDet" value="#{EmployeeOtherDetail.visaDetail}" styleClass="input" maxlength="75" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:outputLabel/><h:outputLabel/>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="Adolescent Certificate Details" style="text-align:left;">
                                    <h:panelGrid id="Adolescent_moreDetailsPanel" style="width:100%;text-align:center;">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row31" style="width:100%;text-align:left;" styleClass="row2">
                                            <h:outputLabel styleClass="label" value="Number"/>
                                            <h:inputText id="txtAdNumber" value="#{EmployeeOtherDetail.adolescentNo}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="25"/>
                                            <h:outputLabel styleClass="label" value="Token Number"/>
                                            <h:inputText id="txtTokenNumber" value="#{EmployeeOtherDetail.adolescentTokenNo}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="25"/>
                                            <h:outputLabel styleClass="label" value="Date"/>
                                            <h:inputText id="calAdolescentDate" value="#{EmployeeOtherDetail.adolescentDate}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row32" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:outputLabel styleClass="label" value="Reference No"></h:outputLabel>
                                            <h:inputText id="txtReferenceNo" value="#{EmployeeOtherDetail.adolescentReferenceNo}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="25"/>
                                            <h:outputLabel styleClass="label" value="Relay"></h:outputLabel>
                                            <h:inputText id="txtRelay" value="#{EmployeeOtherDetail.adolescentRelay}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="25" />
                                            <h:outputLabel styleClass="label" value="Relay Date"/>
                                            <h:inputText id="calRelayDate" value="#{EmployeeOtherDetail.adolescentRelayDate}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>
                            </h:panelGrid>
                            <h:panelGrid id="footerPanelEmpOtherDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelEmpOtherDetails">
                                    <a4j:commandButton id="saveEmpOtherDetails" value="Save" action="#{EmployeeOtherDetail.saveEmpOtherDetails}"
                                                       reRender="errMsgEmpOtherDetails,txtEmpCardNo,txtSearchValue1,ddSearchCriteria1,ddFunction,txtEmpIdGeneralDetails, txtLastEmpIdGeneralDetails, txtEmployeeFName,txtEmployeeMName,txtEmployeeLName,calDateofbirth, calJoiningDate, ddSex, ddStatus, ddBlock, ddUnit, ddGrade, ddDesignation, ddEmployeeType, ddCategory, ddZone, ddLocation, ddDepartment, ddSubDepartment, ddBank, txtAccountNo, txtFinanceLoanAccountCode, ddPaymentMode, membershipGroup, txtConAddressLine, txtConCity, txtConState, txtConPin, txtConPhone, txtPerAddressLine, txtPerCity, txtPerState, txtPerPin, txtPerPhone, txtFHName, txtFHOccupation, txtFHDesignation, txtFHOrganisation, txtFHPhone, txtBirthCity, txtBirthState, txtNation, txtHeight, txtWeight, txtHealth, txtChest, ddBloodGroup1, txtChildren, ddMaritalStatus, calWeddingDate, txtReligion, txtEmailId, txtVisaDet, txtAdNumber, txtTokenNumber, calAdolescentDate, txtReferenceNo, txtRelay, calRelayDate, errMsgGeneralDetails, taskList1, table1"
                                                       />
                                    <a4j:commandButton id="cancelEmpOtherDetails" value="Cancel" action="#{EmployeeOtherDetail.refreshEmpOtherDtlTab}"
                                                       type="reset" reRender="empOtherDtlPanelGrid"/>
                                    <a4j:commandButton id="exitEmpOtherDetails" value="Exit" action="#{EmployeeDetails.exitGeneralDetails}"/> 
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="PresentEmploymentTab" label="Present Employement" switchType="client" disabled="#{EmployeeDetails.disableTabs}" action="#{JobDetails.editJobDetails}"   oncomplete="#{rich:element('calConfirmationDate')}.style=setMask();">
                            <h:panelGrid id="JobDetailsPanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row2">
                                    <h:outputText id="errMsgJobDetails" value="#{JobDetails.message}" styleClass="error"/>
                                </h:panelGrid>

                                <h:panelGrid  columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="idPanelJobDetails" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Emp Id"/>
                                    <h:outputText id="txtEmpIdJobDetails" value="#{EmployeeDetails.empId}"/>
                                    <h:outputLabel styleClass="label" value="Emp Name"/>
                                    <h:outputText id="txtEmpNameJobDetails" value="#{EmployeeDetails.empName}"/>
                                    <h:outputLabel/>
                                    <h:outputLabel/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1011" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Total Experience"></h:outputLabel>
                                    <h:panelGroup id="groupPanelExp" layout="block">
                                        <h:inputText id="txtTotalExp" value="#{JobDetails.totExp}" disabled="#{JobDetails.disableTotExp}" styleClass="input" maxlength="8" style="width:50px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="headerLabel" value="Yrs."/>
                                    </h:panelGroup>
                                    <h:outputLabel styleClass="label" value="Related Industry Experience"></h:outputLabel>
                                    <h:panelGroup id="groupPanelRelatedInstExp" layout="block">
                                        <h:inputText id="txtRelatedInstExp" value="#{JobDetails.autoExp}" disabled="#{JobDetails.disableAutoExp}" styleClass="input" maxlength="8" style="width:50px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="headerLabel" value="Yrs."/>
                                    </h:panelGroup>
                                    <h:outputLabel styleClass="label" value="Notice Period(Months)"></h:outputLabel>
                                    <h:inputText id="txtNoticePeriod" value="#{JobDetails.noticePeriod}" disabled="#{JobDetails.disableNoticePeriod}" styleClass="input" maxlength="8" style="width:50px; " onkeyup="this.value = this.value.toUpperCase();" />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row11236" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Probation Period(Months)"></h:outputLabel>
                                    <h:inputText id="txtProbationPeriod" value="#{JobDetails.probationPeriod}" disabled="#{JobDetails.disableProbationPeriod}" styleClass="input" maxlength="8" style="width:100px;" onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Confirmation Date"/>
                                    <h:inputText id="calConfirmationDate" styleClass="input calInstDate" disabled="#{JobDetails.disableConfirmationDate}" style="width:80px;setMask()" maxlength="10" value="#{JobDetails.confirmationDate}"></h:inputText>
                                    <h:outputLabel styleClass="label" value="Overtime"></h:outputLabel>
                                    <h:selectOneListbox id="ddOvertime" value="#{JobDetails.overtime}" disabled="#{JobDetails.disableOvertime}" styleClass="ddlist" size="1" style="width: 100px;" >
                                        <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                        <f:selectItem itemValue="Y" itemLabel="Yes"/>
                                        <f:selectItem itemValue="N" itemLabel="No"/>
                                    </h:selectOneListbox>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1200" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel id="ReportingTo" styleClass="label" value="Reporting To"/>
                                    <h:panelGroup id="groupReportingTo" layout="block">
                                        <h:inputText id="txtReportingTo" value="#{JobDetails.reportingToEmpName}" maxlength="8" styleClass="input" disabled="true" style="width:100px;"/>
                                        <a4j:commandButton id="btnReportingTo" value="..." disabled="#{JobDetails.disableButton1}" oncomplete="#{rich:element('mainPanelEmployeeSearch2')}.style.display='';" reRender="table2,taskList2,mainPanelEmployeeSearch2"/>
                                    </h:panelGroup>
                                    <h:outputLabel styleClass="label" value="Skill"></h:outputLabel>
                                    <h:selectOneListbox id="ddSkill" styleClass="ddlist" size="1" style="width: 100px;" disabled="#{JobDetails.disableSkill}" value="#{JobDetails.skillCode}">
                                        <f:selectItems value="#{EmployeeDetails.skillList}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel styleClass="label" value="Specialisation"></h:outputLabel>
                                    <h:selectOneListbox id="ddSpecialisation" styleClass="ddlist" size="1" style="width: 100px;" disabled="#{JobDetails.disableSppecialisation}" value="#{JobDetails.specialisationCode}">
                                        <f:selectItems value="#{EmployeeDetails.specializationList}"/>
                                    </h:selectOneListbox>

                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row13" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Achivement"></h:outputLabel>
                                    <h:inputText id="txtAchivement" value="#{JobDetails.achievement}" disabled="#{JobDetails.disableAchievement}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="50"/>
                                    <h:outputLabel styleClass="label" value="Job Description"></h:outputLabel>
                                    <h:inputText id="txtJobDescription" value="#{JobDetails.jobdesc}" disabled="#{JobDetails.disableJobdesc}" styleClass="input" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" maxlength="50"/>
                                    <h:outputLabel/><h:outputLabel/>
                                </h:panelGrid>
                            </h:panelGrid>

                            <h:panelGrid id="mainPanelEmployeeSearch2" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8;display:none" width="100%">

                                <h:panelGrid id="PanelGridEmptable2" bgcolor="#fff" styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
                                    <h:panelGroup layout="block">
                                        <h:outputLabel value="Search Employee" styleClass="label"/>
                                        &nbsp;
                                        <h:inputText id="txtSearchValue2" value="#{JobDetails.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                        &nbsp;
                                        <h:outputLabel value="By" styleClass="label"/>
                                        &nbsp;
                                        <h:selectOneListbox id="ddSearchCriteria2" value="#{JobDetails.empSearchCriteria}" size="1">
                                            <f:selectItem itemValue="Name"/>
                                            <f:selectItem itemValue="ID"/>
                                        </h:selectOneListbox>

                                        &nbsp;
                                        <a4j:commandButton value="Find" action="#{JobDetails.getEmployeeTableData}"  oncomplete=" #{rich:element('table2')}.style.display='';" reRender="table2,taskList2"/>
                                    </h:panelGroup>

                                </h:panelGrid>

                                <h:panelGrid columnClasses="vtop" columns="1" id="table2" styleClass="row2" width="100%">
                                    <a4j:region>
                                        <rich:dataTable value="#{JobDetails.empSearchTable}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="taskList2" rows="6" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Address" style="text-align:left"/></rich:column>
                                                    <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.empAddress}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                            <rich:column style="text-align:center;width:40px">
                                                <a4j:commandLink id="selectlink" action="#{JobDetails.setEmpRowValues}"
                                                                 oncomplete="#{rich:element('mainPanelEmployeeSearch2')}.style.display=none;"
                                                                 reRender="mainPanelEmployeeSearch2,txtReportingTo,taskList2,table2" focus="selectlink">
                                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{dataItem}" target="#{JobDetails.currentEmpItem}"/>
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="taskList2" maxPages="10" />
                                    </a4j:region>
                                </h:panelGrid>
                            </h:panelGrid>
                            <h:panelGrid id="footerPanelJobDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelJobDetails">
                                    <a4j:commandButton id="saveJobDetails" value="Save" action="#{JobDetails.saveJobDetails}" disabled="#{JobDetails.disableSaveButton}"
                                                       oncomplete="#{rich:element('calConfirmationDate')}.style=setMask();"
                                                       reRender="JobDetailsPanel,footerPanelJobDetails"/>
                                    <a4j:commandButton id="deleteJobDetails" value="Delete" action="#{JobDetails.deleteJobDetails}" disabled="#{JobDetails.disableDeleteButton}" reRender="JobDetailsPanel,mainPanelEmployeeSearch2,footerPanelJobDetails"/>
                                    <a4j:commandButton id="cancelJobDetails" value="Cancel" action="#{JobDetails.cancelJobDetails}" type="reset" reRender="JobDetailsPanel,mainPanelEmployeeSearch2,footerPanelJobDetails"/>
                                    <a4j:commandButton id="exitJobDetails" value="Exit" action="#{JobDetails.exitJobDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="ReferenceDetailsTab" label="Reference" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}" action="#{ReferenceDetails.editReferenceDetails}"
                                  oncomplete=" if(#{ReferenceDetails.showreferenceGrid==true})
                                  {
                                  #{rich:element('referenceTablePanel')}.style.display='';
                                  }
                                  else if(#{ReferenceDetails.showreferenceGrid==false})
                                  {
                                  #{rich:element('referenceTablePanel')}.style.display=none;
                                  }"
                                  reRender="referenceTable,ReferenceDetailsPanel,footerPanelReferenceDetails">
                            <rich:panel id="rp1" header="Reference Person Details" >
                                <h:panelGrid id="ReferenceDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row2">
                                        <h:outputText id="errMsgReferenceDetails" value="#{ReferenceDetails.message}" styleClass="error"/>
                                    </h:panelGrid>
                                    <h:panelGrid id="idPanelreferenceDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Emp Id"/>
                                        <h:outputText id="txtEmpIdReferenceDetails" value="#{EmployeeDetails.empId}"/>
                                        <h:outputLabel styleClass="label" value="Emp Name"/>
                                        <h:outputText id="txtEmpNameReferenceDetails" value="#{EmployeeDetails.empName}"/>
                                        <h:outputLabel/><h:outputLabel/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row150" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel id="Name" styleClass="label" value="Name"></h:outputLabel>
                                        <h:inputText id="txtName" value="#{ReferenceDetails.name}" disabled="#{ReferenceDetails.disableRefName}" styleClass="input" maxlength="50" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Address"></h:outputLabel>
                                        <h:inputText id="txtRAddress" value="#{ReferenceDetails.address}" disabled="#{ReferenceDetails.disableRefAddress}" styleClass="input" maxlength="150" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="City"></h:outputLabel>
                                        <h:inputText id="txtRCity" value="#{ReferenceDetails.city}" disabled="#{ReferenceDetails.disableRefCity}" styleClass="input" maxlength="15" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1600" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="State"></h:outputLabel>
                                        <h:inputText id="txtRState" value="#{ReferenceDetails.state}" disabled="#{ReferenceDetails.disableRefState}" styleClass="input" maxlength="15" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Pin"></h:outputLabel>
                                        <h:inputText id="txtRPin" value="#{ReferenceDetails.pin}" disabled="#{ReferenceDetails.disableRefPin}" styleClass="input" maxlength="6" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Phone"></h:outputLabel>
                                        <h:inputText id="txtRPhone" value="#{ReferenceDetails.phone}" disabled="#{ReferenceDetails.disableRefPhone}" styleClass="input" maxlength="12" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3" columns="2" id="Row1601" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Occupation"></h:outputLabel>
                                        <h:inputText id="txtRefOccupation" value="#{ReferenceDetails.occupation}" disabled="#{ReferenceDetails.disableRefOccupation}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="referenceTablePanel" styleClass="row2" width="100%">
                                        <a4j:region>
                                            <rich:dataTable value="#{ReferenceDetails.referenceGrids}" var="dataItem"
                                                            rowClasses="gridrow1,gridrow2" id="referenceTable" rows="3" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Name" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Address" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="City" style="text-align:left"/></rich:column>
                                                        <rich:column><h:outputText value="State" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Pin Code" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Telephone" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Occupation" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.address}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.city}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.state}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.pin}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.phone}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.occupation}" /></rich:column>
                                                <rich:column style="text-align:center;width:40px">
                                                    <a4j:commandLink id="selectlinkReference" action="#{ReferenceDetails.setReferenceRowValues}"
                                                                     oncomplete="#{rich:element('referenceTablePanel')}.style.display=none;"
                                                                     reRender="referenceTable,ReferenceDetailsPanel,footerPanelReferenceDetails" focus="selectlinkReference">
                                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{dataItem}" target="#{ReferenceDetails.currentRefItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{ReferenceDetails.currentIRefRow}"/>
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="referenceTable" maxPages="5"/>
                                        </a4j:region>
                                    </h:panelGrid>
                                </h:panelGrid>

                            </rich:panel>
                            <h:panelGrid id="footerPanelReferenceDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelReferenceDetails">
                                    <a4j:commandButton id="saveReferenceDetails" value="Save" disabled="#{ReferenceDetails.disableSaveButton}"
                                                       reRender="ReferenceDetailsPanel,footerPanelReferenceDetails,referenceTablePanel" action="#{ReferenceDetails.saveReferenceDetails}"/>
                                    <a4j:commandButton id="deleteReferenceDetails" value="Delete" disabled="#{ReferenceDetails.disableDeleteButton}"
                                                       action="#{ReferenceDetails.deleteReferenceDetails}" reRender="ReferenceDetailsPanel,footerPanelReferenceDetails,referenceTablePanel"/>
                                    <a4j:commandButton id="cancelReferenceDetails" value="Cancel" action="#{ReferenceDetails.cancelReferenceDetails}" reRender="ReferenceDetailsPanel,footerPanelReferenceDetails,referenceTablePanel"/>
                                    <a4j:commandButton id="exitReferenceDetails" value="Exit" action="#{ReferenceDetails.exitReferenceDetails}"/>
                                </h:panelGroup>

                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="QualificationsTab" label="Qualifications" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}" action="#{Qualifications.editQualificationsDetails}"
                                  oncomplete=" if(#{Qualifications.showQualificationGrid==true})
                                  {
                                  #{rich:element('qualificationTablePanel')}.style.display='';
                                  }
                                  else if(#{Qualifications.showQualificationGrid==false})
                                  {
                                  #{rich:element('qualificationTablePanel')}.style.display=none;
                                  }">
                            <h:panelGrid id="QualificationPanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                    <h:outputText id="errMsgQualifications" value="#{Qualifications.message}" styleClass="error"/>
                                </h:panelGrid>

                                <h:panelGrid id="idPanelQualification" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Emp Id"/>
                                    <h:outputText id="txtEmpIdQualifications" value="#{EmployeeDetails.empId}"/>
                                    <h:outputLabel styleClass="label" value="Emp Name"/>
                                    <h:outputText id="txtEmpNameQualifications" value="#{EmployeeDetails.empName}"/>
                                    <h:outputLabel/><h:outputLabel/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row15" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Examination/Course"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddExamination" disabled="#{Qualifications.disableExamCourse}" value="#{Qualifications.exam_course}" styleClass="ddlist" size="1" style="width: 100px;" >
                                        <f:selectItems value="#{EmployeeDetails.qualificationList}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel styleClass="label" value="Institute/Board"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtInstitute" value="#{Qualifications.inst_board}" disabled="#{Qualifications.disableInstBoard}" styleClass="input" maxlength="50" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Year"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtYear" value="#{Qualifications.year}" disabled="#{Qualifications.disableYear}" styleClass="input" maxlength="4" style="width:50px; " onkeyup="this.value = this.value.toUpperCase();" />
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row16" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Subjects"/>
                                    <h:inputText id="txtSubjects" value="#{Qualifications.subjects}" disabled="#{Qualifications.disableSubjects}" styleClass="input" maxlength="75" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Specialization"/>
                                    <h:selectOneListbox id="ddSpecialization" value="#{Qualifications.specialisation}" disabled="#{Qualifications.disableSpecialization}" styleClass="ddlist" size="1" style="width: 100px;" >
                                        <f:selectItems value="#{EmployeeDetails.specializationList}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel styleClass="label" value="Marks (%)"/>
                                    <h:inputText id="txtMarks" value="#{Qualifications.marks}" disabled="#{Qualifications.disableMarks}" styleClass="input" maxlength="8" style="width:30px; " onkeyup="this.value = this.value.toUpperCase();" />
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1500" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Division"/>
                                    <h:selectOneListbox id="ddDivision" value="#{Qualifications.division}" disabled="#{Qualifications.disableDivision}" styleClass="ddlist" size="1" style="width:80px;" >
                                        <f:selectItem itemLabel="--Select--" itemValue="0"/>
                                        <f:selectItem itemLabel="I" itemValue="I"/>
                                        <f:selectItem itemLabel="II" itemValue="II"/>
                                        <f:selectItem itemLabel="III" itemValue="III"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel styleClass="label" value="Duration"/>
                                    <h:inputText id="txtDuration" styleClass="input" value="#{Qualifications.duration}" disabled="#{Qualifications.disableDuration}" maxlength="8"/>
                                    <h:outputLabel/><h:outputLabel/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="qualificationTablePanel" styleClass="row2" width="100%">
                                    <a4j:region>
                                        <rich:dataTable value="#{Qualifications.qualificationsTable}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="qualificationTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Qualification" style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Institute" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Year" style="text-align:left"/></rich:column>
                                                    <rich:column><h:outputText value="Subjects" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Specialization Code" style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Marks" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Division" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.qualificationDescription}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.instituteName}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.year}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.subjectsName}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.specializationDescription}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.marks}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.division}" /></rich:column>
                                            <rich:column style="text-align:center;width:40px">
                                                <a4j:commandLink id="selectlinkQualification" action="#{Qualifications.setQualificationRowValues}"
                                                                 oncomplete="#{rich:element('qualificationTablePanel')}.style.display=none;"
                                                                 reRender="QualificationPanel,qualificationTablePanel,footerPanelQualificationsDetails" focus="selectlinkQualification">
                                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{dataItem}" target="#{Qualifications.qualificationsTableItem}"/>
                                                    <f:setPropertyActionListener value="#{row}" target="#{Qualifications.qualificationsIRow}"/>
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="qualificationTable" maxPages="10" />
                                    </a4j:region>
                                </h:panelGrid>

                            </h:panelGrid>

                            <h:panelGrid id="footerPanelQualificationsDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelQualificationsDetails">
                                    <a4j:commandButton id="saveQualificationsDetails" disabled="#{Qualifications.disableSaveButton}" value="Save" action="#{Qualifications.saveQualificationsDetails}"
                                                       reRender="QualificationPanel,footerPanelQualificationsDetails"/>

                                    <a4j:commandButton id="deleteQualificationsDetails" disabled="#{Qualifications.disableDeleteButton}" value="Delete" action="#{Qualifications.deleteQualificationsDetails}"
                                                       reRender="deleteQualificationsDetails,editQualificationsDetails,saveQualificationsDetails,qualificationTablePanel,errMsgQualifications,ddExamination,txtInstitute,txtYear,txtSubjects,ddSpecialization,txtMarks,ddDivision,txtDuration"/>
                                    <a4j:commandButton id="cancelQualificationsDetails" value="Cancel" action="#{Qualifications.cancelQualificationsDetails}" type="reset" reRender="QualificationPanel,qualificationTablePanel,footerPanelQualificationsDetails"/>
                                    <a4j:commandButton id="exitQualificationsDetails" value="Exit" action="#{Qualifications.exitQualificationsDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="DependentDetailsTab" label="Dependents Details" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}" action="#{DependentDetails.editDependentDetails}"
                                  oncomplete=" if(#{DependentDetails.showDependentGrid==true})
                                  {
                                  #{rich:element('dependentTablePanel')}.style.display='';
                                  }
                                  else if(#{DependentDetails.showDependentGrid==false})
                                  {
                                  #{rich:element('dependentTablePanel')}.style.display=none;
                                  }">
                            <rich:panel header="Dependent Details">
                                <h:panelGrid id="DependentDetailsPanel" style="width:100%;text-align:center;">
                                    <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                        <h:outputText id="errMsgDependentDetails" value="#{DependentDetails.message}" styleClass="error"/>
                                    </h:panelGrid>
                                    <h:panelGrid id="idPanelDependentDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Emp Id"/>
                                        <h:outputText id="txtEmpIdDependentDetails" value="#{EmployeeDetails.empId}"/>
                                        <h:outputLabel styleClass="label" value="Emp Name"/>
                                        <h:outputText id="txtEmpNameDependentDetails" value="#{EmployeeDetails.empName}"/>
                                        <h:outputLabel/><h:outputLabel/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1501" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Name"></h:outputLabel>
                                        <h:inputText id="txtDName" value="#{DependentDetails.name}" disabled="#{DependentDetails.disablename}" styleClass="input" maxlength="100" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Other Name"></h:outputLabel>
                                        <h:inputText id="txtDPName" value="#{DependentDetails.petName}" disabled="#{DependentDetails.disablePetName}" styleClass="input" maxlength="100" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Age"></h:outputLabel>
                                        <h:inputText id="txtAge" value="#{DependentDetails.age}" disabled="#{DependentDetails.disableAge}" styleClass="input" maxlength="4" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6"  id="Row16011" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Relationship"></h:outputLabel>
                                        <h:inputText id="txtRelationship" disabled="#{DependentDetails.disableRelationship}" value="#{DependentDetails.relationship}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel styleClass="label" value="Occupation"></h:outputLabel>
                                        <h:inputText id="txtDOccupation" disabled="#{DependentDetails.disableOccupation}" value="#{DependentDetails.occupation}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                        <h:outputLabel/>
                                        <h:outputLabel/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="dependentTablePanel" styleClass="row2" width="100%">
                                        <a4j:region>
                                            <rich:dataTable value="#{DependentDetails.dependentTable}" var="dataItem"
                                                            rowClasses="gridrow1,gridrow2" id="dependentTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Name" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Other Name" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Age" style="text-align:left"/></rich:column>
                                                        <rich:column><h:outputText value="Relation" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Occupation" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.name}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.petName}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.age}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.relation}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.occupation}" /></rich:column>
                                                <rich:column style="text-align:center;width:40px">
                                                    <a4j:commandLink id="selectlinkDependent" action="#{DependentDetails.setDependentRowValues}"
                                                                     oncomplete="#{rich:element('dependentTablePanel')}.style.display=none;"
                                                                     reRender="dependentTablePanel,DependentDetailsPanel,footerPanelDependentDetails" focus="selectlinkDependent">
                                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{dataItem}" target="#{DependentDetails.dependentGridItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{DependentDetails.currentIDepRow}"/>
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="dependentTable" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>

                                </h:panelGrid>
                            </rich:panel>
                            <h:panelGrid id="footerPanelDependentDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelDependentDetails">
                                    <a4j:commandButton id="saveDependentDetails" value="Save" action="#{DependentDetails.saveDependentDetails}" disabled="#{DependentDetails.disableSaveButton}"
                                                       reRender="footerPanelDependentDetails,DependentDetailsPanel"/>
                                    <a4j:commandButton id="deleteDependentDetails" value="Delete" action="#{DependentDetails.deleteDependentDetails}" disabled="#{DependentDetails.disableDeleteButton}"
                                                       reRender="DependentDetailsPanel,footerPanelDependentDetails"/>
                                    <a4j:commandButton id="cancelDependentDetails" value="Cancel" action="#{DependentDetails.cancelDependentDetails}" type="reset" reRender="DependentDetailsPanel,footerPanelDependentDetails"/>
                                    <a4j:commandButton id="exitDependentDetails" value="Exit" action="#{DependentDetails.exitDependentDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="ExperienceTab" label="Previous Employment" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}" action="#{ExperienceDetails.editExperienceDetails}"
                                  oncomplete="
                                  #{rich:element('calDurationFromExperience')}.style=setMask();
                                  #{rich:element('calDurationToExperience')}.style=setMask();
                                  if(#{ExperienceDetails.showExperienceGrid==true})
                                  {
                                  #{rich:element('experienceTablePanel')}.style.display='';
                                  }
                                  else if(#{ExperienceDetails.showExperienceGrid==false})
                                  {
                                  #{rich:element('experienceTablePanel')}.style.display=none;
                                  }">
                            <h:panelGrid id="ExperiencePanel" style="width:100%;text-align:center;">
                                <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                    <h:outputText id="errMsgExperienceDetails" value="#{ExperienceDetails.message}" styleClass="error"/>
                                </h:panelGrid>
                                <h:panelGrid id="idPanelExperienceDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Emp Id"/>
                                    <h:outputText id="txtEmpIdExperience" value="#{EmployeeDetails.empId}"/>
                                    <h:outputLabel styleClass="label" value="Emp Name"/>
                                    <h:outputText id="txtEmpNameExperience" value="#{EmployeeDetails.empName}"/>
                                    <h:outputLabel/><h:outputLabel/>
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row18" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Company Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="txtCompanyNameExp" disabled="#{ExperienceDetails.disablePrevCOmpName}" value="#{ExperienceDetails.preCompName}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Annual Turn Over"/>
                                    <h:inputText id="txtAnnualTurnOver" disabled="#{ExperienceDetails.disableAnnualTurnOver}" value="#{ExperienceDetails.annualTurnOver}" styleClass="input" maxlength="8" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Company Address"></h:outputLabel>
                                    <h:inputText id="txtCompanyAddressExp" disabled="#{ExperienceDetails.disablePrevCompAddress}" value="#{ExperienceDetails.prevCompAddress}" styleClass="input" maxlength="150" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row19" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Reason Of Leaving"></h:outputLabel>
                                    <h:inputText id="txtReasonOfLeavingExperience" disabled="#{ExperienceDetails.disablereasonOfLeaving}" value="#{ExperienceDetails.reasonOfLeaving}" styleClass="input" maxlength="100" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Duration From"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="calDurationFromExperience" disabled="#{ExperienceDetails.disableDurationFrom}" value="#{ExperienceDetails.durationFrom}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                    <h:outputLabel styleClass="label" value="Duration To"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:inputText id="calDurationToExperience" disabled="#{ExperienceDetails.disableDurationTo}" value="#{ExperienceDetails.durationTo}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10"></h:inputText>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row20" style="width:100%;text-align:left;" styleClass="row1">
                                    <h:outputLabel styleClass="label" value="Designation On Joining"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddDesignationOnJoiningExp" disabled="#{ExperienceDetails.disableDesignationOnJoining}" value="#{ExperienceDetails.designationOnJoining}" styleClass="ddlist" size="1" style="width: 100px;" >
                                        <f:selectItems value="#{EmployeeDetails.designationList2}"/>
                                    </h:selectOneListbox>

                                    <h:outputLabel styleClass="label" value="Designation On Leaving"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                    <h:selectOneListbox id="ddDesignationOnLeavingExp" disabled="#{ExperienceDetails.disableDesignationOnLeaving}" value="#{ExperienceDetails.designationOnLeaving}" styleClass="ddlist" size="1" style="width: 100px;" >
                                        <f:selectItems value="#{EmployeeDetails.designationList2}"/>
                                    </h:selectOneListbox>
                                    <h:outputLabel styleClass="label" value="Strength Under"> </h:outputLabel>
                                    <h:inputText id="txtStrengthUnder" disabled="#{ExperienceDetails.disableStrengthUnder}" value="#{ExperienceDetails.strengthUnder}" styleClass="input" maxlength="4" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                </h:panelGrid>
                                <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row21" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Strength Total"> </h:outputLabel>
                                    <h:inputText id="txtStrengthTotal" disabled="#{ExperienceDetails.disableStrengthTotal}" value="#{ExperienceDetails.strengthTotal}" styleClass="input" maxlength="4" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Salary On Joining"> </h:outputLabel>
                                    <h:inputText id="txtSalaryOnJoiningExperience" disabled="#{ExperienceDetails.disableSalaryOnJoining}" value="#{ExperienceDetails.salaryOnJoining}" styleClass="input" maxlength="8" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();" />
                                    <h:outputLabel styleClass="label" value="Salary On Leaving"></h:outputLabel>
                                    <h:inputText id="txtSalaryOnLeavingExperience" disabled="#{ExperienceDetails.disableSalaryOnLeaving}" value="#{ExperienceDetails.salaryOnLeaving}" styleClass="input" maxlength="8" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                </h:panelGrid>

                                <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="experienceTablePanel" styleClass="row2" width="100%">
                                    <a4j:region>
                                        <rich:dataTable value="#{ExperienceDetails.previousCompanyTable}" var="dataItem"
                                                        rowClasses="gridrow1,gridrow2" id="experienceTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Name" style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Duration From" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Duration To" style="text-align:center"/></rich:column>
                                                    <rich:column><h:outputText value="Desg. on Joining" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Desg. on Leaving" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Total Employee" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Employees Under" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Salary on Joining" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Salary on Leaving" style="text-align:left" /></rich:column>
                                                    <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.preCompName}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.durationFrom}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.durationTo}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.designationOnJoining}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.designationOnLeaving}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.strengthTotal}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.strengthUnder}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.salaryOnJoining}" /></rich:column>
                                            <rich:column><h:outputText value="#{dataItem.salaryOnLeaving}" /></rich:column>
                                            <rich:column style="text-align:center;width:40px">
                                                <a4j:commandLink id="selectlinkExperienceDetails" action="#{ExperienceDetails.setExperienceDetailsRowValues}"
                                                                 oncomplete="#{rich:element('experienceTablePanel')}.style.display=none;"
                                                                 reRender="ExperiencePanel,experienceTablePanel,footerPanelExperienceDetails" focus="selectlinkExperienceDetails">
                                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                    <f:setPropertyActionListener value="#{dataItem}" target="#{ExperienceDetails.previousCompanyTableItem}"/>
                                                    <f:setPropertyActionListener value="#{row}" target="#{ExperienceDetails.currentIExpRow}"/>
                                                </a4j:commandLink>
                                            </rich:column>
                                        </rich:dataTable>
                                        <rich:datascroller align="left" for="experienceTable" maxPages="10" />
                                    </a4j:region>
                                </h:panelGrid>

                            </h:panelGrid>
                            <h:panelGrid id="footerPanelExperienceDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelExperienceDetails">
                                    <a4j:commandButton id="saveExperienceDetails" disabled="#{ExperienceDetails.disableSaveButton}" value="Save"  action="#{ExperienceDetails.saveExperienceDetails}"
                                                       reRender="ExperiencePanel,footerPanelExperienceDetails"/>
                                    <a4j:commandButton id="deleteExperienceDetails" disabled="#{ExperienceDetails.disableDeleteButton}" value="Delete" action="#{ExperienceDetails.deleteExperienceDetails}" reRender="ExperiencePanel,footerPanelExperienceDetails"/>
                                    <a4j:commandButton id="cancelExperienceDetails" value="Cancel" action="#{ExperienceDetails.cancelExperienceDetails}" type="reset" reRender="ExperiencePanel,footerPanelExperienceDetails"/>
                                    <a4j:commandButton id="exitExperienceDetails" value="Exit" action="#{ExperienceDetails.exitExperienceDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="TransportDetailsTab" label="Transport" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}"  action="#{TransportDetails.editTransportDetails}"  oncomplete="#{rich:element('txtPickUpTime')}.style=setTimeMask();
                                  if(#{TransportDetails.showTransportGrid==true})
                                  {
                                  #{rich:element('transportTablePanel')}.style.display='';
                                  }
                                  else if(#{TransportDetails.showTransportGrid==false})
                                  {
                                  #{rich:element('transportTablePanel')}.style.display=none;
                                  }">
                            <rich:panel id="rp3" header="Transport Details" >
                                <h:panelGrid id="TransportDetailsPanel" style="width:100%;text-align:center;">

                                    <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                        <h:outputText id="errMsgTransportDetails" value="#{TransportDetails.message}" styleClass="error"/>
                                    </h:panelGrid>

                                    <h:panelGrid id="idPanelTransportDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Emp Id"/>
                                        <h:outputText id="txtEmpIdTransportDetails" value="#{EmployeeDetails.empId}"/>
                                        <h:outputLabel styleClass="label" value="Emp Name"/>
                                        <h:outputText id="txtEmpNameTransportDetails" value="#{EmployeeDetails.empName}"/>
                                        <h:outputLabel/><h:outputLabel/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1502" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Route"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddRoute" value="#{TransportDetails.routeNo}" disabled="#{TransportDetails.disableRoute}" styleClass="ddlist" size="1" style="width: 100px;" >
                                            <f:selectItems value="#{TransportDetails.routeList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Bus No."><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddBusNo" value="#{TransportDetails.busNo}" disabled="#{TransportDetails.disableBus}" styleClass="ddlist" size="1" style="width: 100px;" >
                                            <f:selectItems value="#{TransportDetails.busList}"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Pick Up Time"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtPickUpTime" value="#{TransportDetails.pickUpTime}" disabled="#{TransportDetails.disableTime}" styleClass="input calInstTime" maxlength="5" style="width:35px;text-align:center;setTimeMask()"/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3" columns="2" id="Row1602" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Pick Up Point"></h:outputLabel>
                                        <h:inputText id="txtPickUpPoint" value="#{TransportDetails.pickUpPoint}" disabled="#{TransportDetails.disablePoint}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="transportTablePanel" styleClass="row2" width="100%">
                                        <a4j:region>
                                            <rich:dataTable value="#{TransportDetails.transportTable}" var="dataItem"
                                                            rowClasses="gridrow1,gridrow2" id="transportTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Route Code" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Bus No" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Pick Up Point" style="text-align:left"/></rich:column>
                                                        <rich:column><h:outputText value="Pick Up Time" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.routeCode}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.busNo}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.pickUpPoint}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.pickUpTime}" /></rich:column>
                                                <rich:column style="text-align:center;width:40px">
                                                    <a4j:commandLink id="selectlinkTransport" action="#{TransportDetails.setTransportRowValues}"
                                                                     oncomplete="#{rich:element('txtPickUpTime')}.style=setTimeMask();
                                                                     #{rich:element('transportTablePanel')}.style.display=none;"
                                                                     reRender="txtPickUpTime,TransportDetailsPanel,footerPanelTransportDetails" focus="selectlinkTransport">
                                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{dataItem}" target="#{TransportDetails.transportGridItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{TransportDetails.currentITransRow}"/>
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="transportTable" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>

                                </h:panelGrid>
                            </rich:panel>
                            <h:panelGrid id="footerPanelTransportDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelTransportDetails">

                                    <a4j:commandButton id="saveTransportDetails" value="Save" action="#{TransportDetails.saveTransportDetails}" disabled="#{TransportDetails.disableSaveButton}" reRender="TransportDetailsPanel,footerPanelTransportDetails"
                                                       oncomplete="#{rich:element('txtPickUpTime')}.style=setTimeMask();"/>

                                    <a4j:commandButton id="deleteTransportDetails" value="Delete" action="#{TransportDetails.deleteTransportDetails}" disabled="#{TransportDetails.disableDeleteButton}" reRender="TransportDetailsPanel,footerPanelTransportDetails"/>
                                    <a4j:commandButton id="cancelTransportDetails" value="Cancel" action="#{TransportDetails.cancelTransportDetails}" type="reset" reRender="footerPanelTransportDetails,transportTablePanel,TransportDetailsPanel"/>
                                    <a4j:commandButton id="exitTransportDetails" value="Exit" action="#{TransportDetails.exitTransportDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="MembershipDetailsTab" label="Membership" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}" action="#{MembershipDetails.editMembershipDetails}"   oncomplete="#{rich:element('calExpiryDate')}.style=setMask();
                                  if(#{MembershipDetails.showMembershipGrid==true})
                                  {
                                  #{rich:element('membershipTablePanel')}.style.display='';
                                  }
                                  else if(#{MembershipDetails.showMembershipGrid==false})
                                  {
                                  #{rich:element('membershipTablePanel')}.style.display=none;
                                  }">
                            <rich:panel id="rp4" header="Membership Details" >
                                <h:panelGrid id="MembershipDetailsPanel" style="width:100%;text-align:center;">

                                    <h:panelGrid columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                        <h:outputText id="errMsgMembershipDetails" value="#{MembershipDetails.message}" styleClass="error"/>
                                    </h:panelGrid>

                                    <h:panelGrid id="idPanelMembershipDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Emp Id"/>
                                        <h:outputText id="txtEmpIdMembershipDetails" value="#{EmployeeDetails.empId}"/>
                                        <h:outputLabel styleClass="label" value="Emp Name"/>
                                        <h:outputText id="txtEmpNameMembershipDetails" value="#{EmployeeDetails.empName}"/>
                                        <h:outputLabel/><h:outputLabel/>
                                    </h:panelGrid>
                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1503" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Professional"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtProfessional11" value="#{MembershipDetails.professional}" disabled="#{MembershipDetails.disableProfessional}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:outputLabel styleClass="label" value="Individual"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:inputText id="txtIndividual1" value="#{MembershipDetails.individual}" disabled="#{MembershipDetails.disableIndividual}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                        <h:outputLabel styleClass="label" value="Passport No"></h:outputLabel>
                                        <h:inputText id="txtPassportNo1" value="#{MembershipDetails.passportNo}" disabled="#{MembershipDetails.disablePassportNo}" styleClass="input" maxlength="10" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>

                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row1603" style="width:100%;text-align:left;" styleClass="row2">
                                        <h:outputLabel styleClass="label" value="Expiry Date"/>
                                        <h:inputText id="calExpiryDate" value="#{MembershipDetails.passportDate}" disabled="#{MembershipDetails.disablePassportDate}" styleClass="input calInstDate" style="width:80px;setMask()" maxlength="10" />
                                        <h:outputLabel styleClass="label" value="Accomodation Type"> <font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddAccomodationType" value="#{MembershipDetails.accomodationType}" disabled="#{MembershipDetails.disableAccomodationType}" styleClass="ddlist" size="1" style="width: 100px;" >
                                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                            <f:selectItem itemValue="Self" itemLabel="Self"/>
                                        </h:selectOneListbox>
                                        <h:outputLabel styleClass="label" value="Insurance No"></h:outputLabel>
                                        <h:inputText id="txtInsuranceNo" value="#{MembershipDetails.insuranceNo}" disabled="#{MembershipDetails.disableInsuranceNo}" styleClass="input" maxlength="25" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="col3" columns="2" id="Row1604" style="width:100%;text-align:left;" styleClass="row1">
                                        <h:outputLabel styleClass="label" value="Travelled Overseas"><font class="required" style="color:red;">*</font></h:outputLabel>
                                        <h:selectOneListbox id="ddTravelledOverseas" value="#{MembershipDetails.travOverseasStatus}" disabled="#{MembershipDetails.disableTravOverseasStatus}" styleClass="ddlist" size="1" style="width: 100px;" >
                                            <f:selectItem itemValue="0" itemLabel="--Select--"/>
                                            <f:selectItem itemValue="Y" itemLabel="Yes"/>
                                            <f:selectItem itemValue="N" itemLabel="No"/>
                                        </h:selectOneListbox>
                                    </h:panelGrid>

                                    <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="membershipTablePanel" styleClass="row2" width="100%">
                                        <a4j:region>
                                            <rich:dataTable value="#{MembershipDetails.membershipTable}" var="dataItem"
                                                            rowClasses="gridrow1,gridrow2" id="membershipTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                <f:facet name="header">
                                                    <rich:columnGroup>
                                                        <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Professional Detail" style="text-align:center"/></rich:column>
                                                        <rich:column><h:outputText value="Individual Detail" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Insurance No" style="text-align:left"/></rich:column>
                                                        <rich:column><h:outputText value="Passport No" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Passport Date" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Accomodation Type" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Travelled Overseas" style="text-align:left" /></rich:column>
                                                        <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                    </rich:columnGroup>
                                                </f:facet>
                                                <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.profMember}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.indiMember}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.insuranceNo}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.passportNo}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.passportDate}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.accomodationType}" /></rich:column>
                                                <rich:column><h:outputText value="#{dataItem.travelledOverseas}" /></rich:column>
                                                <rich:column style="text-align:center;width:40px">
                                                    <a4j:commandLink id="selectlinkTransport" action="#{MembershipDetails.setMembershipDetailsRowValues}"
                                                                     oncomplete="#{rich:element('membershipTablePanel')}.style.display=none;"
                                                                     reRender="footerPanelMembershipDetails,membershipTablePanel,MembershipDetailsPanel" focus="selectlinkTransport">
                                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                        <f:setPropertyActionListener value="#{dataItem}" target="#{MembershipDetails.membershipTableItem}"/>
                                                        <f:setPropertyActionListener value="#{row}" target="#{MembershipDetails.currentIMemRow}"/>
                                                    </a4j:commandLink>
                                                </rich:column>
                                            </rich:dataTable>
                                            <rich:datascroller align="left" for="membershipTable" maxPages="10" />
                                        </a4j:region>
                                    </h:panelGrid>

                                </h:panelGrid>
                            </rich:panel>
                            <h:panelGrid id="footerPanelMembershipDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelMembershipDetails">
                                    <a4j:commandButton id="saveMembershipDetails" value="Save" action="#{MembershipDetails.saveMembershipDetails}" disabled="#{MembershipDetails.disableSaveButton}" reRender="MembershipDetailsPanel,footerPanelMembershipDetails"/>
                                    <a4j:commandButton id="deleteMembershipDetails" value="Delete" action="#{MembershipDetails.deleteMembershipDetails}" disabled="#{MembershipDetails.disableDeleteButton}" reRender="MembershipDetailsPanel,footerPanelMembershipDetails"/>
                                    <a4j:commandButton id="cancelMembershipDetails" value="Cancel" action="#{MembershipDetails.cancelMembershipDetails}" type="reset" reRender="MembershipDetailsPanel,footerPanelMembershipDetails"/>
                                    <a4j:commandButton id="exitMembershipDetails" value="Exit" action="#{MembershipDetails.exitMembershipDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>

                        <rich:tab id="OtherDetailsTab" label="Other Details" switchType="ajax" disabled="#{EmployeeDetails.disableTabs}"  action="#{OtherDetails.editOtherDetails}" oncomplete=" #{rich:element('hobbyTablePanel')}.style.display='';
                                  #{rich:element('languageTablePanel')}.style.display='';">
                            <h:panelGrid id="OtherDetailsPanel" style="width:100%;text-align:left;">

                                <h:panelGrid id="errPanelOtherDetails" columnClasses="col2" columns="2" style="text-align:center;" width="100%" styleClass="row1">
                                    <h:panelGroup layout="block">                                       
                                        <h:outputText id="errMsgLanguage" value="#{OtherDetails.messageLanguage}" styleClass="error"/><br>
                                        <h:outputText id="errMsgHobby" value="#{OtherDetails.messageHobby}" styleClass="error"/><br>
                                        <h:outputText id="errMsgOtherDetails" value="#{OtherDetails.message}" styleClass="error"/>
                                    </h:panelGroup>
                                </h:panelGrid>

                                <h:panelGrid id="idPanelOtherDetails" columnClasses="col3,col3,col3,col3,col3,col3" columns="6" style="width:100%;text-align:left;" styleClass="row2">
                                    <h:outputLabel styleClass="label" value="Emp Id"/>
                                    <h:outputText id="txtEmpIdOtherDetails" value="#{EmployeeDetails.empId}"/>
                                    <h:outputLabel styleClass="label" value="Emp Name"/>
                                    <h:outputText id="txtEmpNameOtherDetails" value="#{EmployeeDetails.empName}"/>
                                    <h:outputLabel/><h:outputLabel/>
                                </h:panelGrid>
                                <rich:panel header="Languages">
                                    <h:panelGrid id="languagePanel" width="100%">
                                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row121" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:inputText id="txtLanguage1" disabled="#{OtherDetails.disableLanguage}" value="#{OtherDetails.language}" styleClass="input" maxlength="15" style="width:100px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                            <h:selectManyCheckbox id="languageGroup1" disabled="#{OtherDetails.disableLanguageOptions}" value="#{OtherDetails.languageOptions}">
                                                <f:selectItem itemValue="R" itemLabel="Read"/>
                                                <f:selectItem itemValue="W" itemLabel="Write"/>
                                                <f:selectItem itemValue="S" itemLabel="Speak"/>
                                            </h:selectManyCheckbox>
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="languageTablePanel" styleClass="row2" width="100%">
                                            <a4j:region>
                                                <rich:dataTable value="#{OtherDetails.languageTable}" var="dataItem"
                                                                rowClasses="gridrow1,gridrow2" id="languageTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                    <f:facet name="header">
                                                        <rich:columnGroup>
                                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                            <rich:column><h:outputText value="Language" style="text-align:center"/></rich:column>
                                                            <rich:column><h:outputText value="Read" style="text-align:left" /></rich:column>
                                                            <rich:column><h:outputText value="Write" style="text-align:left"/></rich:column>
                                                            <rich:column><h:outputText value="Speak" style="text-align:left" /></rich:column>
                                                            <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                        </rich:columnGroup>
                                                    </f:facet>
                                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.language}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.langRead}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.langWrite}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.langSpeak}" /></rich:column>
                                                    <rich:column style="text-align:center;width:40px">
                                                        <a4j:commandLink id="selectlinkLanguage" action="#{OtherDetails.setLanguageRowValues}"
                                                                         reRender="errMsgLanguage,languagePanel,languageTablePanel,deleteOtherDetails" focus="selectlinkLanguage">
                                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                            <f:setPropertyActionListener value="#{dataItem}" target="#{OtherDetails.currentLanguageItem}"/>
                                                            <f:setPropertyActionListener value="#{row}" target="#{OtherDetails.currentILanguageRow}"/>
                                                        </a4j:commandLink>
                                                    </rich:column>
                                                </rich:dataTable>
                                                <rich:datascroller align="left" for="languageTable" maxPages="10" />
                                            </a4j:region>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>
                                <rich:panel header="Hobbies">
                                    <h:panelGrid id="hobbyPanel" width="100%">
                                        <h:panelGrid columnClasses="col3,col3,col3" columns="3" id="Row3101" style="width:100%;text-align:left;" styleClass="row1">
                                            <h:inputText id="txtHobies" value="#{OtherDetails.hobby}" disabled="#{OtherDetails.disableHobbies}" styleClass="input" maxlength="50" style="width:300px; " onkeyup="this.value = this.value.toUpperCase();"/>
                                        </h:panelGrid>

                                        <h:panelGrid columnClasses="vtop" columns="1" style="text-align:left;display:none" id="hobbyTablePanel" styleClass="row2" width="100%">
                                            <a4j:region>
                                                <rich:dataTable value="#{OtherDetails.hobbyTable}" var="dataItem"
                                                                rowClasses="gridrow1,gridrow2" id="hobbyTable" rows="6" columnsWidth="100" rowKeyVar="row"
                                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                                    <f:facet name="header">
                                                        <rich:columnGroup>
                                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                            <rich:column><h:outputText value="Hobby" style="text-align:center"/></rich:column>
                                                            <rich:column><h:outputText value="Select" style="text-align:left" /></rich:column>
                                                        </rich:columnGroup>
                                                    </f:facet>
                                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                                    <rich:column><h:outputText value="#{dataItem.hobby}" /></rich:column>
                                                    <rich:column style="text-align:center;width:40px">
                                                        <a4j:commandLink id="selectlinkHobby" action="#{OtherDetails.setHobbyRowValues}"
                                                                         reRender="errMsgHobby,txtHobies,hobbyTablePanel,deleteOtherDetails" focus="selectlinkHobby">
                                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                            <f:setPropertyActionListener value="#{dataItem}" target="#{OtherDetails.currentHobbyItem}"/>
                                                            <f:setPropertyActionListener value="#{row}" target="#{OtherDetails.currentIHobbyRow}"/>
                                                        </a4j:commandLink>
                                                    </rich:column>
                                                </rich:dataTable>
                                                <rich:datascroller align="left" for="hobbyTable" maxPages="10" />
                                            </a4j:region>
                                        </h:panelGrid>
                                    </h:panelGrid>
                                </rich:panel>

                            </h:panelGrid>
                            <h:panelGrid id="footerPanelOtherDetails" style="width:100%;text-align:center;" styleClass="footer">
                                <h:panelGroup id="btnPanelOtherDetails">

                                    <a4j:commandButton id="saveOtherDetails" value="Save" action="#{OtherDetails.saveOtherDetails}" 
                                                       disabled="#{OtherDetails.disableSaveButton}"
                                                       reRender="OtherDetailsPanel,footerPanelOtherDetails"/>
                                    <a4j:commandButton id="deleteOtherDetails" value="Delete" action="#{OtherDetails.deleteOtherDetails}" disabled="#{OtherDetails.disableDeleteButton}"
                                                       reRender="errPanelOtherDetails,OtherDetailsPanel,footerPanelOtherDetails"/>
                                    <a4j:commandButton id="cancelOtherDetails" value="Cancel" action="#{OtherDetails.cancelOtherDetails}" type="reset" reRender="OtherDetailsPanel,footerPanelOtherDetails"/>
                                    <a4j:commandButton id="exitOtherDetails" value="Exit" action="#{OtherDetails.exitOtherDetails}"/>
                                </h:panelGroup>
                            </h:panelGrid>
                        </rich:tab>
                    </rich:tabPanel>
                </h:panelGrid>
            </a4j:form>
            <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">

                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="mainPanelEmployeeSearch1"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid id="PanelGridEmptable1"  styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee" styleClass="label"/>
                                &nbsp;
                                <h:inputText id="txtSearchValue1" value="#{EmployeeDetails.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                &nbsp;
                                <h:outputLabel value="By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria1" value="#{EmployeeDetails.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="Name"/>
                                    <f:selectItem itemValue="ID"/>
                                </h:selectOneListbox>
                                <a4j:commandButton value="Find" action="#{EmployeeDetails.getEmployeeTableData}" reRender="table1,taskList1,errMsgGeneralDetails"/>
                            </h:panelGroup>

                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{EmployeeDetails.empSearchTable}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100" rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Address" style="text-align:left"/></rich:column>
                                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{EmployeeDetails.setEmpRowValues}"
                                                         oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                                                         #{rich:element('calJoiningDate')}.style=setMask();
                                                         #{rich:element('calWeddingDate')}.style=setMask();
                                                         #{rich:element('calAdolescentDate')}.style=setMask();
                                                         #{rich:element('calRelayDate')}.style=setMask();
                                                         #{rich:component('popUpGridPanel')}.hide()"

                                                         reRender="mainPanel,calDateofbirth,calJoiningDate,calWeddingDate,popUpGridPanel,calAdolescentDate,calRelayDate,membershipGroup,idPanelJobDetails,idPanelreferenceDetails,idPanelDependentDetails,idPanelExperienceDetails,idPanelTransportDetails,idPanelMembershipDetails,idPanelOtherDetails,idPanelQualification,father_husband_details_panel,_moreDetailsPanel,Adolescent_moreDetailsPanel,footerPanelGeneralDetails,Permanent_moreDetailsPanel,leftPanel,Contact_moreDetailsPanel,idPanelEmpOtherDetails" focus="selectlink">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{EmployeeDetails.currentEmpItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{EmployeeDetails.currentIEmpRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close"  onclick="#{rich:component('popUpGridPanel')}.hide()"    oncomplete="#{rich:element('calDateofbirth')}.style=setMask();
                                           #{rich:element('calJoiningDate')}.style=setMask();
                                           #{rich:element('calWeddingDate')}.style=setMask();
                                           #{rich:element('calAdolescentDate')}.style=setMask();
                                           #{rich:element('calRelayDate')}.style=setMask();"
                                           reRender="calDateofbirth,calJoiningDate,calWeddingDate,popUpGridPanel,calAdolescentDate,calRelayDate,popUpGridPanel,mainPanel"/>
                    </h:panelGrid>
                </a4j:form>                            
            </rich:modalPanel>                            
        </body>
    </html>
</f:view>