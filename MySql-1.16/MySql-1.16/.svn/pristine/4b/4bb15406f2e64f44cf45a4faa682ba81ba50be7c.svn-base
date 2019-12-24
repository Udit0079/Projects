<%-- 
    Document   : temporarystaff
    Created on : 23 Jul, 2011, 2:22:44 PM
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
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Temporary Staff</title>
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
            <a4j:form id="TemporaryStaff">
                <h:panelGrid bgcolor="#fff" columns="1" id="mainPanel" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel2" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{TemporaryStaff.userName}"/>
                        </h:panelGroup>
                        <h:outputLabel id="label2" styleClass="headerLabel" value="Temporary Staff"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{TemporaryStaff.todayDate}" />
                        </h:panelGroup>
                    </h:panelGrid>
                    
                        <h:panelGrid  id="Panel790"  width="100%" style="text-align:center" styleClass="error">
                            <h:outputText id="lblMsg" styleClass="error" value="#{TemporaryStaff.message}"/>
                            <h:messages />
                        </h:panelGrid>
                      <h:panelGrid columnClasses="cola,col1,col1,col1,col1,col1,col1,cola"  columns="8" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                         <h:outputText/>
                          <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:80px" styleClass="ddlist" value="#{TemporaryStaff.operation}">
                                <f:selectItems value="#{TemporaryStaff.operationList}"/>
                                <a4j:support event="onblur" action="#{TemporaryStaff.setOperationOnBlur}"  oncomplete="if(#{TemporaryStaff.operation=='2'}){ #{rich:component('editPanel')}.show();} else {#{rich:component('editPanel')}.hide();};#{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();" reRender="lblMsg,lblMsg2,btnSave,btnupdate,txtArno,ddDesignation,ddLocation,ddZone,txtEmployeeName,btnAcView1,ddContractorName,txtDailyWages,ddStatus,calPeriodFrom,calPeriodTo"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                       <h:outputText/>
                      <h:outputText/>
                     </h:panelGrid>
                        <h:panelGrid columnClasses="cola,col1,col1,col1,col1,col1,col1,cola" columns="8" id="Row1" style="width:100%;text-align:left;" styleClass="row2">
                            <h:outputText/>
                            <h:outputLabel id="lblArno" styleClass="label" value="A/R No"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="txtArno" styleClass="input" value="#{TemporaryStaff.arNo}" disabled="#{TemporaryStaff.flag}"  maxlength="6" style="width:120px; color:navy" onkeyup="this.value = this.value.toUpperCase();"/>
                            <h:outputLabel id="lblEmployeeName" styleClass="label" value="Employee Name"/>
                            <h:panelGroup layout="block">
                                <a4j:commandButton id="btnAcView1" value="..." action="#{TemporaryStaff.closePanel()}" oncomplete="#{rich:component('acView1')}.show();#{rich:component('ddDesignation')}.focus();" reRender="acView1,lblDesignation" disabled="#{TemporaryStaff.flag}" />
                                <h:inputText id="txtEmployeeName" styleClass="input" style="width: 108px;" value="#{TemporaryStaff.employeeName}" disabled="#{TemporaryStaff.flag}" />
                                </h:panelGroup>
                            <h:outputLabel id="lblDesignation" styleClass="label" value="Designation"/>
                            <h:selectOneListbox id="ddDesignation" styleClass="ddlist" size="1" style="width: 140px" value="#{TemporaryStaff.designation}" disabled="#{TemporaryStaff.flag}" >
                                <f:selectItems value="#{TemporaryStaff.designationList}"/>
                            </h:selectOneListbox>
                          <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="cola,col1,col1,col1,col1,col1,col1,cola" columns="8" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputText/>
                            <h:outputLabel id="lblZone" styleClass="label" value="Zone"/>
                            <h:selectOneListbox id="ddZone" styleClass="ddlist" size="1" style="width: 120px" value="#{TemporaryStaff.zone}" disabled="#{TemporaryStaff.flag}" >
                                <f:selectItems value="#{TemporaryStaff.zoneFromlist}"/>
                            </h:selectOneListbox>
                              <h:outputLabel id="lblLocation" styleClass="label" value="Location"/>
                            <h:selectOneListbox id="ddLocation" styleClass="ddlist" size="1" style="width: 120px" value="#{TemporaryStaff.location}" disabled="#{TemporaryStaff.flag}" >
                                <f:selectItems value="#{TemporaryStaff.locationFromList}"/>
                            </h:selectOneListbox>
                           <h:outputLabel id="lblContractorName" styleClass="label" value="Contractor Name"/>
                            <h:selectOneListbox id="ddContractorName" styleClass="ddlist" size="1" style="width: 120px" value="#{TemporaryStaff.contractorName}" disabled="#{TemporaryStaff.flag}" >
                                <f:selectItems value="#{TemporaryStaff.contractorNameList}"/>
                            </h:selectOneListbox>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="cola,col1,col1,col1,col1,col1,col1,cola" columns="8" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                             <h:outputText/>
                            <h:outputLabel id="lblDailyWages" styleClass="label" value="Daily Wages"/>
                            <h:inputText id="txtDailyWages" styleClass="input"  value="#{TemporaryStaff.dailyWages}"  maxlength="50" style="width:120px;" onkeyup="this.value = this.value.toUpperCase();" disabled="#{TemporaryStaff.flag}" />
                            <h:outputLabel id="lblStatus" styleClass="label" value="Status"/>
                            <h:selectOneListbox id="ddStatus" styleClass="ddlist" size="1" style="width: 120px" value="#{TemporaryStaff.status}" disabled="#{TemporaryStaff.flag}" >
                                <f:selectItems value="#{TemporaryStaff.statusList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="lbl1" styleClass="label"/>
                            <h:outputLabel id="lbl2" styleClass="label"/>
                              <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="cola,col1,col1,col1,col1,col1,col1,cola" columns="8" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                            <h:outputText/>
                            <h:outputLabel value="Period From" styleClass="label"/>
                            <h:panelGroup id="groupPanelarPeriodFrom" layout="block">
                                <h:inputText id="calPeriodFrom" styleClass="input calInstDate"   style="width:62px;setMask()" maxlength="10" value="#{TemporaryStaff.periodFrom}" disabled="#{TemporaryStaff.flag}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="lblPeriodFrom" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel value="Period To" styleClass="label"/>
                            <h:panelGroup id="groupPeriodTo" layout="block">
                                <h:inputText id="calPeriodTo" styleClass="input calInstDate"   style="width:62px;setMask()" maxlength="10" value="#{TemporaryStaff.periodTo}" disabled="#{TemporaryStaff.flag}" >
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                    <a4j:support  focus="btnSave"/>
                                </h:inputText>
                                <h:outputLabel id="lblPeriodTo" styleClass="label" value="DD/MM/YYYY"  style="color:purple"/>
                            </h:panelGroup>
                            <h:outputLabel id="leble1" styleClass="label"/>
                            <h:outputLabel id="lebel2" styleClass="label"/>
                             <h:outputText/>
                        </h:panelGrid>
                     <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{TemporaryStaff.saveTemporaryStaffAction}" disabled="#{TemporaryStaff.disableSave}"
                                               oncomplete=" #{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();" reRender="lblMsg,mainPanel,gridPanel103"  focus="btnExit"/>
                            <%--<a4j:commandButton id="btnUpdate" value="Update"action="#{Consultant.updateButton}" disabled="#{Consultant.disableUpdate}" reRender="lblMsg,leftPanel,gridPanel103"  focus="btnExit"/>--%>
                            <a4j:commandButton id="btnupdate" value="Update" action="#{TemporaryStaff.updateTemporaryStaffAction}" disabled="#{TemporaryStaff.disableUpdate}" oncomplete="#{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();" reRender="mainPanel,acView,stxtMsg,operationList"/>
                            <a4j:commandButton  id="btnrefresh" type="reset" value="Refresh" action="#{TemporaryStaff.refreshButtonAction}" oncomplete="#{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();"  reRender="mainPanel,operationList,btnupdate,btnSave" focus="operationList"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{TemporaryStaff.btnExit}" reRender="mainPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               </a4j:form>
                            
             <rich:modalPanel id="editPanel" height="223" width="800" resizeable="true" moveable="false" >
                    <f:facet name="header">
                        <h:outputText value="Temporary Staff Details"/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="editPanelcloselink1"/>
                            <rich:componentControl for="editPanel" attachTo="editPanelcloselink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                 <a4j:form>               
                  <h:panelGrid columnClasses="vtop" columns="1" id="gridPanel103" width="100%" styleClass="row2" style="height:168px;">
                      <%--  <rich:contextMenu attached="false" id="menuCustomerDetails" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <rich:menuItem value="Remove Record" oncomplete="#{rich:component('deletePanel')}.show()"
                                           actionListener="#{TemporaryStaff.fetchCurrentRow}">
                                <a4j:actionparam name="Description" value="{Description}"/>
                                <a4j:actionparam name="row" value="{currentRow}" />
                            </rich:menuItem>
                        </rich:contextMenu>--%>
                        <a4j:region>
                            <rich:dataTable value="#{TemporaryStaff.arnoSearch}" var="item"
                                            rowClasses="row1, row2" id="editPanelDetails" rows="5" columnsWidth="100" rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                <f:facet name="header">
                                    <rich:columnGroup>
                                      <rich:column breakBefore="true" width="10%"><h:outputText value="A/R No."/></rich:column>
                                        <rich:column width="40%"><h:outputText value="Employee Name"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="From Date"/></rich:column>
                                        <rich:column width="15%"><h:outputText value="To Date"/></rich:column>
                                        <rich:column width="10%"><h:outputText value="Update"/></rich:column>
                                        <rich:column width="10%"><h:outputText value="Delete"/></rich:column> 
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{item.arNo}"/></rich:column>
                                <rich:column><h:outputText value="#{item.empName}"/></rich:column>
                                <rich:column><h:outputText value="#{item.fromDt}"/></rich:column>
                                <rich:column><h:outputText value="#{item.toDt}"/></rich:column>
                                <rich:column>
                                    <a4j:commandLink  id="updatelink" action="#{TemporaryStaff.SelectCurrentroewforEdit}" reRender="mainPanel,subbodyPanel" oncomplete="#{rich:component('editPanel')}.hide();#{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{TemporaryStaff.currentItem}"/>
                                    </a4j:commandLink>
                                   </rich:column>
                               <rich:column>
                                   <a4j:commandLink id="deletelink" action="#{TemporaryStaff.SelectCurrentroewforEdit}" oncomplete="#{rich:component('deletePanel')}.show();#{rich:component('editPanel')}.hide(); " reRender="ddbiltype,leftPanel,btnupdate,btnSave,txtCode" focus="btnSave">
                                        <h:graphicImage value="/resources/images/delete.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{item}" target="#{TemporaryStaff.currentItem}"/>
                                      </a4j:commandLink> 
                               </rich:column>
                                   
                                
                            </rich:dataTable>
                            <rich:datascroller id="scroller2" align="left" for="editPanelDetails" maxPages="5" />
                        </a4j:region>
                    </h:panelGrid>
                  <h:panelGrid id="editPanelFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="editPanelBtnPanel">
                                <a4j:commandButton id="editPanelClose1" value="Close" onclick="#{rich:component('editPanel')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                  </a4j:form>
       </rich:modalPanel>  
                            
                <rich:modalPanel id="acView1" height="260" width="800" onshow="#{rich:element('ddsearch')}.focus();">
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
                                    <h:selectOneListbox id="ddsearch" styleClass="ddlist" size="1" style="width:80px;" value="#{TemporaryStaff.searchBy}">
                                        <f:selectItems value="#{TemporaryStaff.searchList}"/>
                                        <a4j:support  ajaxSingle="true" event="onblur" reRender="ddsearch"/>
                                    </h:selectOneListbox>
                                    <h:inputText id="txtInteviewCode" style="width: 150px"  value="#{TemporaryStaff.nameSearch}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                        <a4j:support action="#{TemporaryStaff.employeeSearch}"  ajaxSingle="true" event="onblur" reRender="gridPanelInterview,lblMsg2,ddsearch,scroller20"/>
                                    </h:inputText>
                                </h:panelGroup>
                                <h:outputText id="lblMsg2" styleClass="error" value="#{TemporaryStaff.message1}"/>
                            </h:panelGrid>
                            
                            
                            <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelInterview" width="100%" styleClass="row2" style="height:168px;">
                                <rich:contextMenu attached="false" id="menuCustomerDetails1" submitMode="ajax" oncollapse="row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <rich:menuItem value="Remove Record"  oncomplete="#{rich:component('deletePanel')}.show()"
                                                   actionListener="#{TemporaryStaff.fetchCurrentRow2}">
                                        <a4j:actionparam name="Interview Code" value="{Interview Code}"/>
                                        <a4j:actionparam name="row" value="{currentRow2}" />
                                    </rich:menuItem>
                                </rich:contextMenu>
                                <a4j:region>
                                    <rich:dataTable value="#{TemporaryStaff.searchEmp}" var="item2"
                                                    rowClasses="row1, row2" id="ListCustomerDetails1" rows="3" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">

                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column colspan="5">
                                                </rich:column>
                                                <rich:column breakBefore="true" width="15%"><h:outputText value="Employee Id"/></rich:column>
                                                <rich:column width="30%"><h:outputText value="Employee Name"/></rich:column>
                                               <rich:column width="10%"> <h:outputText value="Select"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{item2.empId}"/></rich:column>
                                        <rich:column><h:outputText value="#{item2.empName}"/></rich:column>
                                        <rich:column>
                                            <a4j:commandLink  id="selectlink" action="#{TemporaryStaff.SelectEmployeeName}" reRender="mainPanel,btnSave,btnUpdate,ddContractorName" oncomplete="#{rich:component('acView1')}.hide(); #{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask(); return false;"  focus="ddContractorName" >
                                                <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{item2}" target="#{TemporaryStaff.currentItem2}"/>
                                               </a4j:commandLink>
                                           
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

                <rich:modalPanel id="deletePanel" autosized="true" width="250" onshow="#{rich:element('Yes')}.focus()">
                    <f:facet name="header">
                        <h:outputText value="Confirmation DialogBox" />
                    </f:facet>

                    <h:form>
                        <table width="100%" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                            <tbody>
                                <tr style="height:40px">
                                    <td colspan="2">
                                        <h:outputText value="Are you sure to delete ? "/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center" width="50%">
                                        <a4j:region id="yesBtn">
                                            <a4j:commandButton id="Yes" value="Yes" ajaxSingle="true" action="#{TemporaryStaff.deleteTemporaryStaffAction}"
                                                               onclick="#{rich:component('deletePanel')}.hide();" oncomplete="#{rich:element('calPeriodFrom')}.style=setMask();
                                                #{rich:element('calPeriodTo')}.style=setMask();" reRender="lblMsg,mainPanel,gridPanel103" />
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
           
        </body>
    </html>
</f:view>
