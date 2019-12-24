<%-- 
    Document   : LockerAccountMaster
    Created on : Sep 20, 2010, 12:20:37 PM
    Author     : ROHIT KRISHNA GUPTA
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
            <title>Locker Account Master</title>
            <script type="text/javascript">
                var row = 0;
            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="a1" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="headerLabel" value="#{LockerAccountMaster.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel styleClass="headerLabel" value="Locker Account Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="headerLabel" value="#{LockerAccountMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="lpg" columns="1" style="width:100%;height:30px;text-align:center;background-color:#e8eef7">
                        <h:outputText id="errorMessage" styleClass="error" value="#{LockerAccountMaster.errorMessage}"/>
                        <h:outputText id="message" styleClass="msg" value="#{LockerAccountMaster.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="" columns="8" id="a5" width="100%" style="height:30px;" styleClass="row1">
                        <h:outputLabel id="lblCabNo" styleClass="label" value="Cabinate No. :"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddCabNo" tabindex="1" styleClass="ddlist" value="#{LockerAccountMaster.cabNo}" size="1" style="width: 102px">
                            <f:selectItems value="#{LockerAccountMaster.cabNoList}"/>
                            <a4j:support event="onchange"  action="#{LockerAccountMaster.lockerTypeCombo}" focus="ddLockerType" reRender="a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter" />
                        </h:selectOneListbox>
                        <h:outputLabel id="lblLockerType" styleClass="label" value="Locker Type :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddLockerType" tabindex="2" styleClass="ddlist" value="#{LockerAccountMaster.lockerType}" size="1" style="width: 102px">
                            <f:selectItems value="#{LockerAccountMaster.lockerTypeList}"/>
                            <a4j:support event="onblur"  action="#{LockerAccountMaster.lockerTypeComboLostFocus}" oncomplete="#{rich:element('txtLockerNo')}.disabled = false;#{rich:element('btnUpdate')}.disabled = true;#{rich:element('btnSave')}.disabled = true" reRender="txtLockerNo,a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter" focus="txtLockerNo"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblLockerNo" styleClass="label" value="Locker No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtLockerNo" maxlength="6" size="15" tabindex="3" value="#{LockerAccountMaster.lockerNo}"  onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                            <a4j:support event="onblur"  action="#{LockerAccountMaster.lockerNoLostFocus}" oncomplete="#{rich:element('btnSave')}.disabled = true" focus="ddAcctNo" reRender="a5,a6,a9,a12,a11,a10,a13,a14,a15,a16,a17,a18,a19,a20,a21,table,taskList,message,errorMessage,gpFooter" />
                        </h:inputText>
                        <h:outputLabel id="lblKeyNo" styleClass="label" value="Key No. :" ><font class="required" color="red">*</font></h:outputLabel>
                        <h:inputText id="txtKeyNo" maxlength="6" size="7" tabindex="4" value="#{LockerAccountMaster.keyNo}" disabled="true" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                        <h:outputText id="stxtKeyNo" styleClass="output" value="#{LockerAccountMaster.keyNo}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col9" columns="2" id="a7" width="100%">
                        <h:panelGrid columns="6" id="a6" style="height:30px;" styleClass="row2" columnClasses="col9" width="100%">
                            <h:outputLabel id="lblAcctNo" styleClass="label" value="Account No. :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtAcNo" maxlength="#{LockerAccountMaster.acNoMaxLen}" tabindex="6" disabled="#{LockerAccountMaster.disableAcntNo}" value="#{LockerAccountMaster.acctNo}" size="20" styleClass="input">
                                <a4j:support action="#{LockerAccountMaster.getAccountDetail}" event="onblur" oncomplete="#{rich:element('btnUpdate')}.disabled = true;#{rich:element('btnSave')}.disabled = false"
                                             reRender="a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter,taskList,acnoLabel"/>
                            </h:inputText>
                            <h:outputLabel id="acnoLabel" value="#{LockerAccountMaster.newAcno}" styleClass="label"/>
                            <h:outputText id="stxtAcNo" styleClass="output" value="#{LockerAccountMaster.acctNo}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a15" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblModeOfOpr" styleClass="label" value="Mode Of Operation :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddModeOfOpr" tabindex="12" styleClass="ddlist" value="#{LockerAccountMaster.mode}" size="1" style="width: 125px" rendered="#{LockerAccountMaster.lockNoFlag==false}">
                                <f:selectItems value="#{LockerAccountMaster.modeList}" />
                            </h:selectOneListbox>
                            <h:outputText id="stxtModeOfOpr" styleClass="output" value="#{LockerAccountMaster.mode}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a9" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblCustName" styleClass="label" value="Customer Name :" style="padding-left:70px;"/>
                            <h:outputText id="stxtCustName" styleClass="output" value="#{LockerAccountMaster.custName}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a16" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblNomination" styleClass="label" value="Nomination :" style="padding-left:70px;"/>
                            <h:inputText id="txtNomination" maxlength="25" tabindex="13" size="20" value="#{LockerAccountMaster.nomination}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtNomination" styleClass="output" value="#{LockerAccountMaster.nomination}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a10" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAddress" styleClass="label" value="Address :" style="padding-left:70px;"/>
                            <h:outputText id="stxtAddress" styleClass="output" value="#{LockerAccountMaster.address}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a17" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAdOpr1" styleClass="label" value="Ad Operator 1 :" style="padding-left:70px;"/>
                            <h:inputText id="txtAdOpr1" maxlength="25" tabindex="14" size="20" value="#{LockerAccountMaster.adOpr1}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtAdOpr1" styleClass="output" value="#{LockerAccountMaster.adOpr1}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col9" columns="6" id="a155" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblNoOfYear" styleClass="label" value="Adv.Payment(No Of Year) :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddNoOfYear" tabindex="12" styleClass="ddlist" value="#{LockerAccountMaster.noOfYear}" size="1" style="width: 125px" rendered="#{LockerAccountMaster.lockNoFlag==false}">
                                <f:selectItems value="#{LockerAccountMaster.noOfYearList}" />
                             <a4j:support event="onblur"  action="#{LockerAccountMaster.custCatLostFocus}" 
                                             reRender="txtAssociatedRent,message,errorMessage" />
                            </h:selectOneListbox>
                            <h:outputText id="stxtNoOfYear" styleClass="output" value="#{LockerAccountMaster.noOfYear}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a177" width="100%" style="height:30px;" styleClass="row2">
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a11" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblCustCat" styleClass="label" value="Customer Category :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddCustCat" tabindex="8" styleClass="ddlist" value="#{LockerAccountMaster.custCat}" size="1" style="width: 125px" rendered="#{LockerAccountMaster.lockNoFlag==false}">
                                <f:selectItems value="#{LockerAccountMaster.custCatList}"/>
                                <a4j:support event="onblur"  action="#{LockerAccountMaster.custCatLostFocus}" oncomplete="if(#{LockerAccountMaster.custCatFlag=='true'}){#{rich:element('txtSecDeposite')}.focus();}else{#{rich:element('txtAssociatedRent')}.focus();}"
                                             reRender="ddLockerType,txtAssociatedRent,message,errorMessage" />
                            </h:selectOneListbox>
                            <h:outputText id="stxtCustCat" styleClass="output" value="#{LockerAccountMaster.custCat}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a18" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblAdOpr2" styleClass="label" value="Ad Operator 2 :" style="padding-left:70px;"/>
                            <h:inputText id="txtAdOpr2" maxlength="25" tabindex="15" size="20" value="#{LockerAccountMaster.adOpr2}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtAdOpr2" styleClass="output" value="#{LockerAccountMaster.adOpr2}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a12" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAssociatedRent" styleClass="label" value="Associated Rent :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtAssociatedRent" maxlength="10" tabindex="9" size="10" value="#{LockerAccountMaster.rent}" disabled="#{LockerAccountMaster.custCatFlag}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtAssociatedRent" styleClass="output" value="#{LockerAccountMaster.rent}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a19" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblAdOpr3" styleClass="label" value="Ad Operator 3 :" style="padding-left:70px;"/>
                            <h:inputText id="txtAdOpr3" maxlength="25" tabindex="16" size="20" value="#{LockerAccountMaster.adOpr3}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtAdOpr3" styleClass="output" value="#{LockerAccountMaster.adOpr3}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a13" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblSecDeposite" styleClass="label" value="Security Deposite :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <h:inputText id="txtSecDeposite" maxlength="10" tabindex="10" size="10" value="#{LockerAccountMaster.secDeposite}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}">
                                <a4j:support event="onblur"  action="#{LockerAccountMaster.secDepositeLostFocus}" oncomplete="if(#{rich:element('txtSecDeposite')}.value == ''){#{rich:element('txtSecDeposite')}.focus();}else{#{rich:element('calRentDueDt')}.focus();}"
                                             reRender="txtSecDeposite,message,errorMessage,a14" />
                            </h:inputText>
                            <h:outputText id="stxtSecDeposite" styleClass="output" value="#{LockerAccountMaster.secDeposite}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a20" width="100%" style="height:30px;" styleClass="row1">
                            <h:outputLabel id="lblAdOpr4" styleClass="label" value="Ad Operator 4 :" style="padding-left:70px;"/>
                            <h:inputText id="txtAdOpr4" maxlength="25" tabindex="17" size="20" value="#{LockerAccountMaster.adOpr4}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtAdOpr4" styleClass="output" value="#{LockerAccountMaster.adOpr4}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a14" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblRentDueDt" styleClass="label" value="Rent Due Date :" style="padding-left:70px;"><font class="required" color="red">*</font></h:outputLabel>
                            <rich:calendar datePattern="dd/MM/yyyy" id="calRentDueDt" value="#{LockerAccountMaster.rentDueDt}" disabled="#{LockerAccountMaster.rentDtFlag}" tabindex="11" inputSize="10"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col9" columns="6" id="a21" width="100%" style="height:30px;" styleClass="row2">
                            <h:outputLabel id="lblRemarks" styleClass="label" value="Remarks :" style="padding-left:70px;"/>
                            <h:inputText id="txtRemarks" maxlength="30" tabindex="18" size="20" value="#{LockerAccountMaster.remark}" onblur="this.value = this.value.toUpperCase();" styleClass="input" rendered="#{LockerAccountMaster.lockNoFlag==false}"/>
                            <h:outputText id="stxtRemarks" styleClass="output" value="#{LockerAccountMaster.remark}" rendered="#{LockerAccountMaster.lockNoFlag==true}"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table" style="height:auto;" styleClass="row2" width="100%">
                        <a4j:region>
                            <rich:dataTable value="#{LockerAccountMaster.gridDetail}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="taskList" rows="8" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column colspan="15"><h:outputText value="Details" /></rich:column>
                                        <rich:column breakBefore="true"><h:outputText value="Cabinate No." /></rich:column>
                                        <rich:column><h:outputText value="Locker Type" /></rich:column>
                                        <rich:column><h:outputText value="Locker No." /></rich:column>
                                        <rich:column><h:outputText value="Key No." /></rich:column>
                                        <rich:column><h:outputText value="Account No." /></rich:column>
                                        <rich:column><h:outputText value="Category" /></rich:column>
                                        <rich:column><h:outputText value="Rent" /></rich:column>
                                        <rich:column><h:outputText value="Security" /></rich:column>
                                        <rich:column><h:outputText value="Nomination" /></rich:column>
                                        <rich:column><h:outputText value="Mode" /></rich:column>
                                        <rich:column><h:outputText value="AdvPayYear" /></rich:column>
                                        <rich:column><h:outputText value="Auth" /></rich:column>
                                        <rich:column><h:outputText value="Remarks" /></rich:column>
                                        <rich:column width="20"><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.cabNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerTy}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.lockerNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.keyNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.acctNo}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.custCat}" /></rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.rent}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column>
                                    <h:outputText value="#{dataItem.security}"><f:convertNumber type="currency" pattern="#.##" minFractionDigits="2"/></h:outputText>
                                </rich:column>
                                <rich:column><h:outputText value="#{dataItem.nomination}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.mode}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.advPayYr}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.auth}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.remarks}" /></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{LockerAccountMaster.fillValuesofGridInFields}" oncomplete="#{rich:element('btnSave')}.disabled = true;#{rich:element('txtLockerNo')}.disabled = true;#{rich:element('btnUpdate')}.disabled = false;#{rich:element('btnUpdate')}.focus();"
                                                     reRender="a5,a6,a9,a10,a11,a12,a13,a14,message,errorMessage,table,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter,acnoLabel,ddNoOfYear" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LockerAccountMaster.currentItem}"/>
                                        <f:setPropertyActionListener value="#{row}" target="#{LockerAccountMaster.currentRow}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid columns="1" id="gpFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup layout="block" style="width:100%;text-align:center;">
                            <a4j:commandButton id="btnSave" tabindex="19" value="Save" action="#{LockerAccountMaster.saveBtn}" oncomplete="#{rich:element('btnUpdate')}.disabled = true" disabled="#{LockerAccountMaster.gridFlag}"  reRender="a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter,taskList,acnoLabel,ddNoOfYear" focus="ddCabNo"/>
                            <a4j:commandButton id="btnUpdate" tabindex="20" value="Update" action="#{LockerAccountMaster.updateBtn}" oncomplete="#{rich:element('btnSave')}.disabled = true" disabled="#{LockerAccountMaster.lockNoFlag}"  reRender="a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter,taskList,ddNoOfYear" focus="ddCabNo"/>
                            <a4j:commandButton id="btnRefresh" tabindex="21" value="Refresh" action="#{LockerAccountMaster.resetForm}"  reRender="a5,a6,a9,a10,a11,a12,a13,a14,table,message,errorMessage,lpg,a16,a18,a15,a17,a19,a20,a21,gpFooter,taskList,acnoLabel,ddNoOfYear" focus="ddCabNo"/>
                            <a4j:commandButton id="btnExit" tabindex="22" value="Exit" reRender="message,errorMessage" action="#{LockerAccountMaster.exitForm}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>  
