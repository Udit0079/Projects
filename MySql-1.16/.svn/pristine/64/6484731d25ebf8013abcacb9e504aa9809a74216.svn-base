<%-- 
    Document   : employeeOtherDetail
    Created on : Jun 1, 2012, 4:28:16 PM
    Author     : Ankit Verma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@taglib uri="http://richfaces.org/rich" prefix="rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<h:form>
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

    <h:panelGrid id="footerPanelEmpOtherDetails" style="width:100%;text-align:center;" styleClass="footer">
        <h:panelGroup id="btnPanelEmpOtherDetails">
            <a4j:commandButton id="saveEmpOtherDetails" value="Save" action="#{EmployeeOtherDetail.saveEmpOtherDetails}"
                               reRender="errMsgEmpOtherDetails,txtEmpCardNo,txtSearchValue1,ddSearchCriteria1,ddFunction,txtEmpIdGeneralDetails, txtLastEmpIdGeneralDetails, txtEmployeeFName,txtEmployeeMName,txtEmployeeLName,calDateofbirth, calJoiningDate, ddSex, ddStatus, ddBlock, ddUnit, ddGrade, ddDesignation, ddEmployeeType, ddCategory, ddZone, ddLocation, ddDepartment, ddSubDepartment, ddBank, txtAccountNo, txtFinanceLoanAccountCode, ddPaymentMode, membershipGroup, txtConAddressLine, txtConCity, txtConState, txtConPin, txtConPhone, txtPerAddressLine, txtPerCity, txtPerState, txtPerPin, txtPerPhone, txtFHName, txtFHOccupation, txtFHDesignation, txtFHOrganisation, txtFHPhone, txtBirthCity, txtBirthState, txtNation, txtHeight, txtWeight, txtHealth, txtChest, ddBloodGroup1, txtChildren, ddMaritalStatus, calWeddingDate, txtReligion, txtEmailId, txtVisaDet, txtAdNumber, txtTokenNumber, calAdolescentDate, txtReferenceNo, txtRelay, calRelayDate, errMsgGeneralDetails, taskList1, table1"
                               />
      <%--      <a4j:commandButton id="cancelEmpOtherDetails" value="Cancel" action="#{EmployeeDetails.cancelGeneralDetails}"
                               type="reset" reRender="mainPanel"/>--%>
            <a4j:commandButton id="exitEmpOtherDetails" value="Exit" action="#{EmployeeDetails.exitGeneralDetails}"/> 
        </h:panelGroup>
    </h:panelGrid>
</h:form>