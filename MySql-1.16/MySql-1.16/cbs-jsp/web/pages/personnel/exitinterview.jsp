
<%-- 
    Document   : exitinterview
    Created on : Jul 20, 2011, 3:27:16 PM
    Author     : Administrator
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
            <title>Exit Interview</title>
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
            <a4j:form id="exitinterview">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{Exitinterview.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Exit Interview"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{Exitinterview.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{Exitinterview.errMsg}"/>
                    </h:panelGrid>
                     <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"  columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:80px" styleClass="ddlist" value="#{Exitinterview.operation}">
                                <f:selectItems value="#{Exitinterview.operationList}"/>
                                <a4j:support event="onblur" action="#{Exitinterview.setOperationOnBlur}" oncomplete="if(#{Exitinterview.operation=='0'}){ #{rich:component('acView1')}.hide(); } else { #{rich:component('acView1')}.show();};#{rich:element('txtDateOfJoining')}.style=setMask();#{rich:element('txtDateOfLeaving')}.style=setMask();" reRender="txtDateOfJoining,mainPanel,acView1,btnSave,errMsg"  />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                     </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row2" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblEmployeeName" styleClass="output" value="Employee Name"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtEmployeeName"    styleClass="input" disabled="true"  maxlength="100" value="#{Exitinterview.empName}" />
                        <h:outputLabel id="lblEmployeeId"  styleClass="output" value="Employee Id"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:inputText id="txtEmployeeId" styleClass="input" disabled="true" maxlength="8" value="#{Exitinterview.empId}" />
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblBlockName" styleClass="output"  value="Block Name"></h:outputLabel>
                        <h:inputText id="txtBlockName" styleClass="input"  maxlength="200" value="#{Exitinterview.block}" />
                        <h:outputLabel id="lblDesignation" styleClass="output" value="Designation"></h:outputLabel>
                        <h:inputText id="txtDesignation" styleClass="input" maxlength="200" value="#{Exitinterview.desig}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row4" style="width:100%;text-align:left;" styleClass="row1">
                        <h:outputLabel id="lblDepartment" styleClass="output" value="Department"></h:outputLabel>
                        <h:inputText id="txtDepartment" styleClass="input" maxlength="200" value="#{Exitinterview.dept}" />
                        <h:outputLabel id="lblDateOfJoining" styleClass="output" value="Date Of Joining"></h:outputLabel>
                        <h:panelGroup>
                          <h:inputText id="txtDateOfJoining" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Exitinterview.join}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromJoining" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row5" style="width:100%;text-align:left;" styleClass="row2">
                        <h:outputLabel id="lblDateOfLeaving" styleClass="output" value="Date Of Leaving"></h:outputLabel>
                        <h:panelGroup>
                          <h:inputText id="txtDateOfLeaving" styleClass="input calInstDate"  style="width:70px;setMask()" maxlength="10" value="#{Exitinterview.leave}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFromLeaving" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel id="lbl1" />
                        <h:outputLabel id="lbl2" />
                    </h:panelGrid>
                    <rich:panel header="Assessement" style="text-align:left;">
                        <rich:panel header="Satisfaction" style="text-align:left;">
                            <h:panelGrid  id="Row23" style="width:100%;text-align:left;" styleClass="row1">
                                <h:selectOneRadio id="satisfact" style="width:100%" value="#{Exitinterview.satisfact}">
                                    <f:selectItem itemValue="a" itemLabel="Less25%"/>
                                    <f:selectItem itemValue="b" itemLabel="25%"/>
                                    <f:selectItem itemValue="c" itemLabel="50%"/>
                                    <f:selectItem itemValue="d" itemLabel="75%"/>
                                    <f:selectItem itemValue="e" itemLabel="Greater75%"/>
                                </h:selectOneRadio>
                            </h:panelGrid>
                        </rich:panel>
                        <rich:panel header="Dissatisfaction" style="text-align:left;">
                            <h:panelGrid columnClasses="col3,col3" columns="2" id="Row23l" style="width:100%;text-align:left;" styleClass="row2" >
                                <h:selectOneRadio id="dissatisrat" style="width:100%" value="#{Exitinterview.dissatisfact}">
                                    <f:selectItem itemValue="a" itemLabel="Less25%"/>
                                    <f:selectItem itemValue="b" itemLabel="25%"/>
                                    <f:selectItem itemValue="c" itemLabel="50%"/>
                                    <f:selectItem itemValue="d" itemLabel="75%"/>
                                    <f:selectItem itemValue="e" itemLabel="Greater75%"/>
                                </h:selectOneRadio>
                            </h:panelGrid>
                        </rich:panel>
                    </rich:panel>
                        <rich:panel id="rasonpanel" header="Reason">
                            <h:panelGrid id="panelgrid28" columnClasses="col3,col3,col3,col3,col3,col3" columns="6"style="width:100%;text-align:left;" styleClass="row1">
                                <h:outputLabel value="Reason for Resignation"/>
                                <h:selectOneListbox id="reason" style="width:120px" size="1" styleClass="ddlist" value="#{Exitinterview.reason}" disabled="#{Exitinterview.reasonflag}">
                                    <f:selectItems value="#{Exitinterview.reasonList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel/>
                                <h:outputLabel/>
                             </h:panelGrid>
                        </rich:panel>
                        
                    <h:panelGrid id="footerPanel" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="BtnPanel">
                            <a4j:commandButton id="btnSave" value="Save" disabled="#{Exitinterview.save}"action="#{Exitinterview.saveData}"  reRender="errMsg,txtEmployeeName,txtEmployeeId,txtBlockName,txtDesignation,txtDepartment,txtDateOfLeaving,txtDateOfJoining,satisfact,dissatisrat,reason,btnSave"/>
                            <a4j:commandButton id="btnCancel" value="Refresh"  action="#{Exitinterview.refreshData}"reRender="errMsg,txtEmployeeName,txtEmployeeId,txtBlockName,txtDesignation,txtDepartment,txtDateOfLeaving,txtDateOfJoining,satisfact,dissatisrat,reason,btnSave,operationList"/>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{Exitinterview.btnExit}"reRender="leftPanel"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                    </a4j:form>
                        
                 <rich:modalPanel id="acView1" label="Form" width="700" height="270" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('acView1')}.hide();" >
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
                   <h:panelGrid id="mainPanelEmployeeSearch1"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid id="PanelGridEmptable1"  styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee" styleClass="label"/>
                                &nbsp;
                                
                                <h:outputLabel value="By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria1" value="#{Exitinterview.searchflag}" size="1">
                                    <f:selectItem itemValue="Emp-Id"/>
                                     <f:selectItem itemValue="Emp Name"/>
                                </h:selectOneListbox>
                                 &nbsp;
                                <h:inputText id="txtSearchValue1" value="#{Exitinterview.searchtext}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                &nbsp;
                                <a4j:commandButton value="Find" action="#{Exitinterview.findEmpMethod}"  reRender="taskLista,datascrollerTab"/>
                            </h:panelGroup>

                        </h:panelGrid>
                            <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Exitinterview.datagridemp}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskLista" rows="3" rowKeyVar="row"
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
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empphD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empaddD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD"  action="#{Exitinterview.selectDataEmpMethodview}" oncomplete="#{rich:component('acView1')}.hide();#{rich:element('txtDateOfJoining')}.style=setMask();#{rich:element('txtDateOfLeaving')}.style=setMask();"  reRender="txtDateOfJoining,txtDateOfLeaving,errMsg,txtEmployeeName,txtEmployeeId,txtBlockName,txtDesignation,txtDepartment,txtDateOfLeaving,txtDateOfJoining,satisfact,dissatisrat,reason,btnSave,btnView,btnAdd"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{Exitinterview.currentempdata}" />
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab" align="left" for="taskLista" maxPages="5"/>
                            </a4j:region>
                        </h:panelGrid>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;">
                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                        </h:panelGrid>
                    </a4j:form>
                </rich:modalPanel>
        </body>
    </html>
</f:view>
