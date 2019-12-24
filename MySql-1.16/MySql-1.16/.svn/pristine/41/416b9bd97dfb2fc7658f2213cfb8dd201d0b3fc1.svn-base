<%-- 
    Document   : transferdetails
    Created on : 20 Jul, 2011, 5:28:30 PM
    Author     : Zeeshan Waris
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@taglib prefix="a4j" uri="http://richfaces.org/a4j"%>
<%@taglib prefix="rich" uri="http://richfaces.org/rich"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Transfer Details</title>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMask();
                });
                var row;
                function setMask(){
                     jQuery(".calInstDate").mask("39/19/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="TransferDetails">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid id="HeaderPanel" width="100%" bgcolor="#fff" columns="3" style="text-align:center" styleClass="header">
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblDate" value="Date :" styleClass="headerLabel"/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TransferDetails.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="lblTitle" value="Transfer Details" styleClass="headerLabel"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel id="lblUser" value="User Name :" styleClass="headerLabel"/>
                            <h:outputText id="stxtUser"  styleClass="output" value="#{TransferDetails.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="group1" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                        <h:panelGrid columns="2" id="Panel790" style="width:100%;height:30px;text-align:center;" styleClass="row2">
                            <h:outputText id="lblMsg" styleClass="error" value="#{TransferDetails.message}"/>
                        </h:panelGrid>
                         <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:100px" styleClass="ddlist" value="#{TransferDetails.operation}">
                                <f:selectItems value="#{TransferDetails.operationList}"/>
                                <a4j:support event="onblur" action="#{TransferDetails.setOperationOnBlur}" reRender="acView2,acView1,lblMsg,ddsearch" oncomplete="if(#{TransferDetails.operation=='1'}){#{rich:component('acView2')}.hide(); #{rich:component('acView1')}.show(); } else if(#{TransferDetails.operation=='2'}){#{rich:component('acView1')}.hide(); #{rich:component('acView2')}.show(); };#{rich:element('calARDate')}.style=setMask();#{rich:element('calEffectiveFrom')}.style=setMask();" />
                        </h:selectOneListbox>
                          <h:outputLabel value="A/R No" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:inputText id="txtARNo" styleClass="input" style="width:140px;" value="#{TransferDetails.arNo}" disabled="#{TransferDetails.disableArNo}" maxlength="10" onkeyup="this.value = this.value.toUpperCase();"/>
                         <h:outputLabel value="A / R Date" styleClass="label"/>
                            <h:panelGroup id="groupPanelarDt" layout="block">
                                <h:inputText id="calARDate" styleClass="input calInstDate"   style="width:70px;setMask()" maxlength="10" value="#{TransferDetails.arDate}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblarDt" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                          
                    </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" style="text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel value="Employee Id" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:inputText id="txtEmployeeId" styleClass="input" style="width:100px;" value="#{TransferDetails.employeeID}" disabled="#{TransferDetails.flag}"/>
                            <h:outputLabel value="Employee Name" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:inputText id="txtEmployeeName" styleClass="input" style="width:190px;" value="#{TransferDetails.employeeName}" onkeyup="this.value = this.value.toUpperCase();" disabled="#{TransferDetails.flag}"/>
                            <h:outputLabel value="Designation" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddDepartment" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.department}" disabled="#{TransferDetails.flag}">
                                <f:selectItems value="#{TransferDetails.departmentList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel3" style="text-align:left;" styleClass="row1" width="100%">
                            <h:outputLabel value="Designation" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddDesignation" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.designation}" disabled="#{TransferDetails.flag}">
                                <f:selectItems value="#{TransferDetails.designationList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Zone From" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddZoneFrom" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.zoneFrom}" disabled="#{TransferDetails.flag}">
                                <f:selectItems value="#{TransferDetails.zoneFromlist}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Location From" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddLocationFrom" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.locationFrom}" disabled="#{TransferDetails.flag}">
                                <f:selectItems value="#{TransferDetails.locationFromList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" style="text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel value="Reporting To" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:inputText id="txtReportingto" styleClass="input" style="width:140px;" value="#{TransferDetails.reportingTo}" disabled="#{TransferDetails.flag}"/>
                            <h:outputLabel value="Block From" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddBlockFrom" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.blockFrom}" disabled="#{TransferDetails.flag}">
                                <f:selectItems value="#{TransferDetails.blockList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Block" styleClass="label"/>
                            <h:selectOneListbox id="ddBlock" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.block}" >
                                <f:selectItems value="#{TransferDetails.blockList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" style="text-align:left;" styleClass="row1" width="100%">
                            <h:outputLabel value="Zone" styleClass="label"/>
                            <h:selectOneListbox id="ddZone" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.zone}" >
                                <f:selectItems value="#{TransferDetails.zoneFromlist}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Location" styleClass="label"/>
                            <h:selectOneListbox id="ddLocation" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.location}">
                                <f:selectItems value="#{TransferDetails.locationFromList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Designation" styleClass="label"/>
                            <h:selectOneListbox id="ddDesignation1" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.designationSecond}">
                                <f:selectItems value="#{TransferDetails.designationList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel6" style="text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel value="Department" styleClass="label"/>
                            <h:selectOneListbox id="ddDepartment1" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.departmentSecond}">
                                <f:selectItems value="#{TransferDetails.departmentList}"/>
                            </h:selectOneListbox>
                             <h:outputLabel value="Effective From" styleClass="label"/>
                            <h:panelGroup id="groupEffectiveFromDt" layout="block">
                                <h:inputText id="calEffectiveFrom" styleClass="input calInstDate"   style="width:70px;setMask()" maxlength="10" value="#{TransferDetails.effectiveFrom}">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblEffectiveFrom" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel styleClass="label"/>
                            <h:outputLabel styleClass="label"/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel8" style="text-align:left;" styleClass="row1" width="100%">
                            <h:outputLabel value="Category" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddCategory" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.catagory}">
                                <f:selectItems value="#{TransferDetails.catagoryList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Deputation In" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddDeputationIn" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.deputationIn}">
                                <f:selectItems value="#{TransferDetails.deputationInList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="ESI" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddESI" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.esi}">
                                <f:selectItems value="#{TransferDetails.deputationInList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel9" style="text-align:left;" styleClass="row2" width="100%">
                            <h:outputLabel value="Salary Processing" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddSalaryProcessing" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.salaryProcessing}">
                                <f:selectItems value="#{TransferDetails.deputationInList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="Transfer In" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddTransferIn" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.transferIn}" >
                                <f:selectItems value="#{TransferDetails.deputationInList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel value="PF" styleClass="label"><font class="required" color="red">*</font> </h:outputLabel>
                            <h:selectOneListbox id="ddPF" styleClass="ddlist" size="1" style="width:140px;" value="#{TransferDetails.pf}" >
                                <f:selectItems value="#{TransferDetails.deputationInList}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="FooterPanel" width="100%" bgcolor="#fff" styleClass="footer" style="text-align:center">
                        <h:panelGroup id="fpg1"  style="text-align:center;">
                           
                            <a4j:commandButton id="btnSave"value="Save"  style="width:50px" action="#{TransferDetails.saveUpdateTransferDetails}" disabled="#{TransferDetails.saveDisable}"
                                               oncomplete=" #{rich:element('calARDate')}.style=setMask();
                                               #{rich:element('calEffectiveFrom')}.style=setMask();" reRender="stxtMsg,mainPanel" />
                            <a4j:commandButton id="btnCancel" value="Refresh" style="width:50px"  action="#{TransferDetails.refreshAction}" oncomplete=" #{rich:element('calARDate')}.style=setMask();
                                               #{rich:element('calEffectiveFrom')}.style=setMask();" reRender="mainPanel,btnInitiatedBy"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{TransferDetails.btnExit}" style="width:50px" reRender="mainPanel,btnInitiatedBy" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                <rich:modalPanel id="acView1" height="260" width="800" top="true" onshow="#{rich:element('ddsearch')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Employee Search"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="acView1" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="acViewPanel1" width="100%">
                            <h:panelGrid id="acViewRow2" columns="3" columnClasses="col1,col2,col1" width="100%" styleClass="row1" style="text-align:left;">
                                <h:outputLabel id="lebelSearch" styleClass="label" value="Search"/>
                                <h:panelGroup id="groupPanelSearch" layout="block">
                                    <h:selectOneListbox id="ddsearch" styleClass="ddlist" size="1" style="width:80px;" value="#{TransferDetails.searchBy}">
                                        <f:selectItems value="#{TransferDetails.searchList}"/>
                                        <a4j:support  ajaxSingle="true" event="onblur" reRender="ddsearch"/>
                                    </h:selectOneListbox>
                                    <h:inputText id="txtInteviewCode" style="width: 150px"  value="#{TransferDetails.nameSearch}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support action="#{TransferDetails.employeeSearch}"  ajaxSingle="true" event="onblur" reRender="gridPanelInterview,lblMsg2,ddsearch,scroller20"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputText id="lblMsg2" styleClass="error" value="#{TransferDetails.message1}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelInterview" width="100%" styleClass="row2" style="height:168px;">
                                <rich:contextMenu attached="false" id="menuCustomerDetails1" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                   actionListener="#{TransferDetails.fetchCurrentRow2}">
                                        <a4j:actionparam name="Interview Code" value="{Interview Code}"/>
                                        <a4j:actionparam name="row" value="{currentRow2}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{TransferDetails.searchEmp}" var="item2"
                                                    rowClasses="row1, row2" id="ListCustomerDetails1" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="5">
                                                </rich:column>
                                                <rich:column breakBefore="true" width="15%"><h:outputText value="Employee Id"/></rich:column>
                                                <rich:column width="30%"><h:outputText value="Employee Name"/></rich:column>
                                                <rich:column width="30%"> <h:outputText value="Address"/></rich:column>
                                                <rich:column width="15%"> <h:outputText value="Telephone"/></rich:column>
                                                <rich:column width="10%"> <h:outputText value="Select"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item2.empId}"/></rich:column>
                                        <rich:column><h:outputText value="#{item2.empName}"/></rich:column>
                                        <rich:column><h:outputText value="#{item2.empAdd}"/></rich:column>
                                        <rich:column><h:outputText value="#{item2.empTel}"/></rich:column>
                                        <rich:column>
                                            <a4j:commandLink  id="selectlink" action="#{TransferDetails.selectEmpId}" reRender="mainPanel,btnSave,btnUpdate" oncomplete="#{rich:component('acView1')}.hide();  #{rich:element('calARDate')}.style=setMask();
                                                             #{rich:element('calEffectiveFrom')}.style=setMask(); return false;" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />

                                                <f:setPropertyActionListener value="#{item2}" target="#{TransferDetails.currentItem2}"/>
                                            </a4j:commandLink>
                                            <rich:toolTip for="selectlink" value="Update" />
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller id="scroller20" align="left" for="ListCustomerDetails1" maxPages="5" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <rich:modalPanel id="acView2" height="260" width="800" top="true" onshow="#{rich:element('txtarNo')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Transfer Search"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                            <rich:componentControl for="acView2" attachTo="closelink2" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="acViewPanel3" width="100%">
                            <h:panelGrid id="acViewRow3" columns="3" columnClasses="col1,col2,col1" width="100%" styleClass="row1" style="text-align:left;">
                                <h:outputLabel id="lblarNo" styleClass="label" value="A/R No:"/>
                                <h:inputText id="txtarNo" style="width: 150px"  value="#{TransferDetails.arNum}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support action="#{TransferDetails.transferSearchbyArNo}"  ajaxSingle="true" event="onblur" reRender="gridPanelInterview2,lblMsg2"/>
                                </h:inputText>
                                <h:outputText id="lblMsg3" styleClass="error" value="#{TransferDetails.message1}"/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelInterview2" width="100%" styleClass="row2" style="height:168px;">
                                <rich:contextMenu attached="false" id="menuCustomerDetails2" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record" ajaxSingle="true" oncomplete="#{rich:component('deletePanel')}.show()"
                                                   actionListener="#{TransferDetails.fetchCurrentRow}">
                                        <a4j:actionparam name="A/R Code" value="{A/R Code}"/>
                                        <a4j:actionparam name="row" value="{currentRow}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{TransferDetails.arnoSearch}" var="item3"
                                                    rowClasses="row1, row2" id="ListCustomerDetails2" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="3">
                                                </rich:column>
                                                <rich:column breakBefore="true"width="80%"><h:outputText value="A/R Code"/></rich:column>
                                                <rich:column width="10%"><h:outputText value="Delete"/></rich:column>
                                                <rich:column width="10%"><h:outputText value="Update"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item3.arNumber}"/></rich:column>
                                        <rich:column>
                                            <a4j:commandLink ajaxSingle="true" id="deletelink"   action="#{TransferDetails.selectCurrentRowForupdateDelete}" reRender="btnInitiatedBy,subbodyPanel,mainPanel,btnSave,btnUpdate,btndelete"  oncomplete="#{rich:component('deletePanel')}.show(),#{rich:component('acView2')}.hide();  #{rich:element('calARDate')}.style=setMask();
                                                             #{rich:element('calEffectiveFrom')}.style=setMask();return false;">
                                                <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{row}" target="#{TransferDetails.currentRow}" />
                                                <f:setPropertyActionListener value="#{item3}" target="#{TransferDetails.currentItem}"/>
                                            </a4j:commandLink>
                                            <rich:toolTip for="deletelink" value="Delete" />
                                        </rich:column>
                                        <rich:column>
                                            <a4j:commandLink  id="selectlink1" action="#{TransferDetails.selectCurrentRowForupdateDelete}" style="color:red" reRender="mainPanel,btnSave,btnUpdate,btndelete" oncomplete="#{rich:component('acView2')}.hide();  #{rich:element('calARDate')}.style=setMask();
                                                             #{rich:element('calEffectiveFrom')}.style=setMask();return false;" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />

                                                <f:setPropertyActionListener value="#{item3}" target="#{TransferDetails.currentItem}"/>
                                            </a4j:commandLink>
                                            <rich:toolTip for="selectlink1" value="Update" />
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller id="scroller2" align="left" for="ListCustomerDetails2" maxPages="5" />
                                </a4j:region>
                            </h:panelGrid>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel2" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel2">
                                <a4j:commandButton id="acViewClose2" value="Close" onclick="#{rich:component('acView2')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>

                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are You Sure To Delete ?"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{TransferDetails.deleteTransferAction}"
                                                               onclick="#{rich:component('deletePanel')}.hide();" reRender="lblMsg,leftPanel,gridPanel103,mainPanel" />
                                        </a4j:region>
                                    </td>
                                    <td align="center" width="50%">
                                        <a4j:commandButton id="No" value="No" onclick="#{rich:component('deletePanel')}.hide();return false;" />
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