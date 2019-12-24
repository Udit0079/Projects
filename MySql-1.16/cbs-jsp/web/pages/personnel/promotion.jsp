<%-- 
    Document   : promotion
    Created on : Jul 30, 2011, 10:57:53 AM
    Author     : admin
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
            <title>Promotion</title>
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
            <a4j:form id="promotion">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{Promotion.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Promotion"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Promotion.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{Promotion.errMsg}"/>
                    </h:panelGrid>
                      <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:120px" styleClass="ddlist" value="#{Promotion.operation}">
                                <f:selectItems value="#{Promotion.operationList}"/>
                                <a4j:support event="onblur" action="#{Promotion.setOperationOnBlur}" reRender="acView1,errMsg5,mainPanel,effectivefrom,lastprodt" oncomplete="if(#{Promotion.operation=='1'}){ #{rich:component('acView1')}.show(); } else { #{rich:component('acView1')}.hide(); };if(#{Promotion.operation=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else { #{rich:component('popUpGridPanel')}.hide(); };#{rich:element('effectivefrom')}.style=setTimeMask();#{rich:element('lastprodt')}.style=setTimeMask();" />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Employee Name"  styleClass="output" />
                        <h:inputText id="empName" value="#{Promotion.empName}" styleClass="input" disabled="true" style="width:120px"/>
                        <h:outputLabel value="Employee Id"  styleClass="output"/>
                        <h:inputText id="empId"  disabled="true" styleClass="input" maxlength="8" value="#{Promotion.empId}" style="width:120px"/>
                        <h:outputLabel value="Designation"   styleClass="output"/>
                        <h:selectOneListbox id="designation" disabled="true" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.desgCode}">
                            <f:selectItems value="#{Promotion.deslist}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Department"  styleClass="output" />
                        <h:selectOneListbox id="department" disabled="true" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.deptCode}">
                            <f:selectItems value="#{Promotion.deptlist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Reporting To"  styleClass="output" />
                        <h:inputText id="reportingto" disabled="true"styleClass="input" value="#{Promotion.reportinto}" maxlength="8" style="width:120px"/>
                        <h:outputLabel value="Old Location"  styleClass="output" />
                        <h:selectOneListbox id="oldlocation" disabled="true" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.locatCode}">
                            <f:selectItems value="#{Promotion.locationlist}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Zone From"  styleClass="output" />
                        <h:selectOneListbox id="zone" disabled="true" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.zoneCode}">
                            <f:selectItems value="#{Promotion.zonelist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Block"  styleClass="output"/>
                        <h:selectOneListbox id="block" styleClass="ddlist" size="1" disabled="true" style="width:120px" value="#{Promotion.blockCode}">
                            <f:selectItems value="#{Promotion.blocklist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Date Of Last Promotion"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="lastprodt" styleClass="input calInstDate" disabled="true" style="width:70px;setMask()" maxlength="10" value="#{Promotion.lastpromotion}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromProduct" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel7" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" width="100%" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Date of Approval"   styleClass="output"/>
                        <h:panelGroup>
                            <h:inputText id="dateofapproval" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Promotion.approvaldt}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:message for="dateofapproval" showDetail="true"/>
                            <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel value="Effective From"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="effectivefrom" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Promotion.effectivedt}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFrom2" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                         <h:outputLabel value="Location"  styleClass="output" />
                        <h:selectOneListbox id="location" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.location}">
                            <f:selectItems value="#{Promotion.locationlist}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel8" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                       
                        <h:outputLabel value="Proposed Department"  styleClass="output" />
                        <h:selectOneListbox id="prodepartment" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.proposeddept}">
                            <f:selectItems value="#{Promotion.deptlist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="New Block"  styleClass="output" />
                        <h:selectOneListbox id="newblock" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.newblock}">
                            <f:selectItems value="#{Promotion.blocklist}"/>
                        </h:selectOneListbox>
                         <h:outputLabel value="Proposed Designation"  styleClass="output" />
                        <h:selectOneListbox id="prodesignationn" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.prodeignation}">
                            <f:selectItems value="#{Promotion.deslist}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel10" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Proposed Supervisor"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="prosupervisor" style="width:100px"  styleClass="input" maxlength="8" value="#{Promotion.prosupervisor}"/>
                            <a4j:commandButton value="..." id="supervisor" onclick="#{rich:component('acView2')}.show();"/>
                        </h:panelGroup>
                        <h:outputLabel value="Proposed Zone"  styleClass="output" />
                        <h:selectOneListbox id="prozone" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.prozone}">
                            <f:selectItems value="#{Promotion.zonelist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Overall Rating"  styleClass="output" />
                        <h:selectOneListbox id="overallrating" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.overallrating}">
                            <f:selectItems value="#{Promotion.ratlist}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel11" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                       
                        <h:outputLabel value="Remarks" styleClass="output" />
                        <h:inputText id="remark" value="#{Promotion.remark}" styleClass="input" maxlength="100" style="width:120px"/>
                        <h:outputLabel value="Budgeted Status"  styleClass="output" />
                        <h:selectOneListbox id="buddgetstatus" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.budgetstatus}">
                            <f:selectItems value="#{Promotion.budgetlist}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Approved By HRD"  styleClass="output" />
                        <h:selectOneListbox id="approvedbrhrd" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.approvhrd}">
                            <f:selectItems value="#{Promotion.approve}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel12" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Approved By Head"  styleClass="output" />
                        <h:selectOneListbox id="approvedbyrhead" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.approvhead}">
                            <f:selectItems value="#{Promotion.approve}"/>
                        </h:selectOneListbox>
                        <h:outputLabel value="Approved By MD"  styleClass="output" />
                        <h:selectOneListbox id="approvedbymd" styleClass="ddlist" size="1" style="width:120px" value="#{Promotion.approvmd}">
                            <f:selectItems value="#{Promotion.approve}"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel1">
                            <a4j:commandButton id="save"  action="#{Promotion.saveData}"value="Save" disabled="#{Promotion.save}" oncomplete=" #{rich:element('dateofapproval')}.style=setMask();#{rich:element('effectivefrom')}.style=setMask();" reRender="operationList,gridPanelTable,add,save,update,delete,taskListNew,errMsg,empName,empId,designation,department,reportingto,oldlocation,zone,block,lastprodt,dateofapproval,effectivefrom,arno,location,prodepartment,newblock,prosupervisor,prozone,prodesignationn,overallrating,remark,buddgetstatus,approvedbrhrd,approvedbyrhead,approvedbymd" />
                            <a4j:commandButton id="delete"  value="Delete" action="#{Promotion.deleteData}" disabled="#{Promotion.delete}" oncomplete=" #{rich:element('dateofapproval')}.style=setMask();#{rich:element('effectivefrom')}.style=setMask();" reRender="operationList,gridPanelTable,add,save,update,delete,taskList1,errMsg,empName,empId,designation,department,reportingto,oldlocation,zone,block,lastprodt,dateofapproval,effectivefrom,arno,location,prodepartment,newblock,prosupervisor,prozone,prodesignationn,overallrating,remark,buddgetstatus,approvedbrhrd,approvedbyrhead,approvedbymd" />
                            <a4j:commandButton id="cancel" value="Refresh" action="#{Promotion.refreshButtonAction}" oncomplete=" #{rich:element('dateofapproval')}.style=setMask();#{rich:element('effectivefrom')}.style=setMask();" reRender="operationList,gridPanelTable,add,save,update,delete,taskList1,errMsg,empName,empId,designation,department,reportingto,oldlocation,zone,block,lastprodt,dateofapproval,effectivefrom,arno,location,prodepartment,newblock,prosupervisor,prozone,prodesignationn,overallrating,remark,buddgetstatus,approvedbrhrd,approvedbyrhead,approvedbymd" />
                            <a4j:commandButton id="exit" value="Exit" action="#{Promotion.btnExit}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
               <rich:modalPanel id="popUpGridPanel" label="Form" width="800" height="250" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Promotion Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                     <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{Promotion.datagrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskListNew" rows="2" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Ar No" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Remarks" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Budgeted Status" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empName}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.arNo}"style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.remarks1}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.budgtStatus}"style="text-align:center"  /></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlink" action="#{Promotion.selectData}" oncomplete="#{rich:component('popUpGridPanel')}.hide();#{rich:element('effectivefrom')}.style=setTimeMask();#{rich:element('lastprodt')}.style=setTimeMask();" reRender="effectivefrom,lastprodt,popUpGridPanel,save,update,delete,empName,empId,designation,department,reportingto,oldlocation,zone,block,lastprodt,dateofapproval,effectivefrom,arno,location,prodepartment,newblock,prosupervisor,prozone,prodesignationn,overallrating,remark,buddgetstatus,approvedbrhrd,approvedbyrhead,approvedbymd">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{Promotion.currentdata}"/> </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskListNew" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                 
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </rich:modalPanel>                    
            </a4j:form>
             <rich:modalPanel id="acView1" height="260" width="800"  onshow="#{rich:element('searchcriteria')}.focus();" >
                    <f:facet name="header">
                        <h:outputText value="Employee Search "/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="acView1" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                      <a4j:form>
                        <h:panelGrid id="SubPanelgid1" columns="4" styleClass="row1" style="text-align:left" width="100%">
                            <h:outputLabel value="Serarch"/>
                             <h:panelGroup id="btnPanel">
                            <h:selectOneListbox id="searchcriteria" styleClass="ddlist" size="1" style="width: 80px" value="#{Promotion.searchflag}">
                                <f:selectItem itemValue="Emp-Id"/>
                                <f:selectItem itemValue="Emp Name"/>
                            </h:selectOneListbox>
                                 <h:inputText id="searchtext" value="#{Promotion.searchtext}" maxlength="100" styleClass="input" style="width: 80px" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support   event="onblur" action="#{Promotion.onfindMethod}"  reRender="taskList,datascrollerTab">
                                </a4j:support>
                            </h:inputText>
                            </h:panelGroup>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Promotion.datagridemp}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="3" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Employee Name" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Address" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Mobile No" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empidD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empnameD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empaddD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empphD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD" ajaxSingle="true" action="#{Promotion.selectDataEmpMethod}" oncomplete="#{rich:component('acView1')}.hide();#{rich:element('dateofapproval')}.style=setMask();#{rich:element('effectivefrom')}.style=setMask();" reRender="dateofapproval,effectivefrom,save,update,delete,empName,empId,designation,department,zone,block,reportingto,oldlocation"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{Promotion.currentempdata}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab"  align="left" for="taskList" maxPages="5"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;"/>
                            </h:panelGroup>
                        </h:panelGrid>
                      </a4j:form>
                </rich:modalPanel>
                <rich:modalPanel id="acView2" height="260" width="800"  onshow="#{rich:element('searchcriteria1')}.focus();">
                    <f:facet name="header">
                        <h:outputText value="Employee Search "/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                            <rich:componentControl for="acView2" attachTo="closelink2" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <a4j:form>
                        <h:panelGrid id="SubPanelgid11" columns="4" styleClass="row1" style="text-align:left" width="100%">
                            <h:outputLabel value="Serarch"/>
                            <h:panelGroup id="btnPanel3">
                            <h:selectOneListbox id="searchcriteria1" styleClass="ddlist" size="1" style="width: 80px" value="#{Promotion.searchflag}">
                                <f:selectItem itemValue="Emp-Id"/>
                                <f:selectItem itemValue="Emp Name"/>
                            </h:selectOneListbox>
                            <h:inputText id="searchtext1" value="#{Promotion.searchtext}" style="width:80px" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support   event="onblur" action="#{Promotion.onfindMethod}"  reRender="taskList1,datascrollerTab1">
                                </a4j:support>
                            </h:inputText>
                             </h:panelGroup>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="SubPanelgid31" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Promotion.datagridemp}" var="dataItem1"
                                                 rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Employee Name" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Address" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Mobile No" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem1.empidD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem1.empnameD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem1.empaddD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem1.empphD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD11" ajaxSingle="true" action="#{Promotion.selectSupervisor}" oncomplete="#{rich:component('acView2')}.hide(); return false;" reRender="prosupervisor"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem1}" target="#{Promotion.currentempdata}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab1" align="left" for="taskList1" maxPages="2"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel1" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel1">
                                <a4j:commandButton id="acViewClose2" value="Close" onclick="#{rich:component('acView2')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                     </a4j:form>   
                </rich:modalPanel>
        </body>
    </html>
</f:view>
