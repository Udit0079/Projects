<%--
    Document   : LeaveRegister
    Created on : May 25, 2011, 5:44:56 PM
    Author     : Sudhir kumar Bisht
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
            <title>Leave Register</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />
            <script type="text/javascript">
                var row;
                jQuery(function(jQuery) {
                    setMask();
                });
                var row;
                function setMask() {
                    jQuery(".calInstDate").mask("39/99/9999");
                }
            </script>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="LeaveRegister">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{LeaveRegister.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Leave Register"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LeaveRegister.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="row2">
                        <h:outputText id="errMsg" value="#{LeaveRegister.message}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid  id="gridPanelMain2" style="text-align:left" width="100%" >
                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel3" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                                <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{LeaveRegister.function}" style="width:90px">
                                    <f:selectItems value="#{LeaveRegister.functionList}"/>
                                    <a4j:support event="onblur" action="#{LeaveRegister.onChangeFunction}" reRender="popUpGridPanel,taskList1,gridPanelTable,errMsg,delete" 
                                                 oncomplete="if(#{LeaveRegister.function=='1'}){ #{rich:component('empSearchModelPanel')}.show();#{rich:component('popUpGridPanel')}.hide(); } 
                                                 else if(#{LeaveRegister.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); #{rich:component('empSearchModelPanel')}.hide()} 
                                                 else {  #{rich:component('popUpGridPanel')}.hide(); #{rich:component('empSearchModelPanel')}.hide()}" focus="#{LeaveRegister.focusId}"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="employeeId" styleClass="output" value="Employee ID"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="idText" disabled="true" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{LeaveRegister.empId}" style="width:90px"/>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="employeeLabel" styleClass="output" value="Empoloyee Name"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="employeeText" disabled="true" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{LeaveRegister.empName}"/>
                                <h:outputLabel id="leaveCodeLabel" styleClass="output" value="Leave Code"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="ddleaveCodeList" disabled="#{LeaveRegister.disableLeaveCode}" styleClass="ddlist" size="1" style="width:130px" value="#{LeaveRegister.leaveCode}">
                                    <f:selectItems value="#{LeaveRegister.leaveCodeList}"/>
                                </h:selectOneListbox>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="fdate" styleClass="output" value="From Date"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText  id="appCalendar" value="#{LeaveRegister.leaveFromDate}" style="width:85px" styleClass="input calInstDate">
                                    <f:convertDateTime pattern="dd/MM/yyyy" />
                                </h:inputText>
                                <h:outputLabel id="toDate" styleClass="output" value="To Date"><font class="required" color="red">*</font></h:outputLabel>
                                <h:panelGroup>
                                    <h:inputText id="appCalendar1" value="#{LeaveRegister.leaveToDate}" style="width:85px" styleClass="input calInstDate">
                                        <f:convertDateTime pattern="dd/MM/yyyy" />
                                        <a4j:support action="#{LeaveRegister.isLeaveAllowed}" event="onblur" focus="#{LeaveRegister.focusId}"
                                                     reRender="errMsg,numOfLeaveDays,totalDaysTaken,daysTaken"/>
                                    </h:inputText>
                                    <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                                </h:panelGroup>

                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel61" width="100%" styleClass="row2">
                            <h:outputLabel value="Total No. of Leaves" styleClass="output"/>
                            <h:outputText id="numOfLeaveDays" value="#{LeaveRegister.numOfLeaveDays}" styleClass="msg"/>
                            <h:outputLabel value="Leaves Taken" styleClass="output"/>
                            <h:outputText id="totalDaysTaken" value="#{LeaveRegister.totalDaysTaken}" styleClass="msg"/>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel12" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel value="Leaves Requested" styleClass="output"/>
                            <h:outputText id="daysTaken" value="#{LeaveRegister.daysTaken}" styleClass="msg"/>
                            <h:outputLabel id="paidLevel" styleClass="output" value="Paid"/>
                            <h:selectOneListbox id="paidList" styleClass="ddlist" size="1" style="width:80px" value="#{LeaveRegister.paid}">
                                <f:selectItem itemValue="Yes"/>
                                <f:selectItem itemValue="No"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel16" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="deptRecommend" styleClass="output" value="Dept. Recommendations"><font class="required" color="red">*</font></h:outputLabel>
                                <h:selectOneListbox id="deptList" styleClass="ddlist" size="1" style="width:80px" value="#{LeaveRegister.deptRecommendations}">
                                    <f:selectItem itemValue="Yes"/>
                                    <f:selectItem itemValue="No"/>
                                </h:selectOneListbox>
                                <h:outputLabel id="sancAuth" styleClass="output" value="Sanction Authority"><font class="required" color="red">*</font></h:outputLabel>
                                <h:inputText id="sancText" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{LeaveRegister.sanctionAuthority}"/>
                            </h:panelGrid>

                        <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel17" style="text-align:left;" width="100%" styleClass="row1">
                            <h:outputLabel id="leaveReasons" styleClass="output" value="Reasons of Leave"/>
                            <h:inputText id="reasonsText" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{LeaveRegister.reasonOfLeave}"/>
                            <h:outputLabel id="remarks" styleClass="output" value="Remarks/Details"/>
                            <h:inputText id="remarksText" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" value="#{LeaveRegister.remarks}"/>
                        </h:panelGrid>

                    </h:panelGrid>   

                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{LeaveRegister.saveLeaveData}" reRender="mainPanel,errMsg" oncomplete="setMask()"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{LeaveRegister.deleteLeaveData}" reRender="mainPanel,errMsg" disabled="#{LeaveRegister.function == '1'}" oncomplete="setMask()"/>
                            <a4j:commandButton id="cancel" value="Refresh" type="reset" reRender="mainPanel,errMsg" action="#{LeaveRegister.cancelAction}" oncomplete="setMask()"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{LeaveRegister.exitAction}" reRender="errMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>

            <rich:modalPanel id="empSearchModelPanel" width="600" moveable="false" height="375" onmaskdblclick="#{rich:component('empSearchModelPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:form>
                    <h:panelGrid id="empSearchGrid" columns="1" width="100%">
                        <h:panelGrid id="criteriaGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3"styleClass="row1" style="text-align:center" width="100%">
                            <h:outputText/>
                            <h:outputLabel value="Serarch Criteria" styleClass="output"/>
                            <h:selectOneListbox id="searchcriteria" styleClass="ddlist" size="1" style="width: 80px" value="#{LeaveRegister.empSearchCriteria}">
                                <f:selectItem itemValue="Id"/>
                                <f:selectItem itemValue="Name"/>
                            </h:selectOneListbox>

                            <h:inputText id="searchtext" value="#{LeaveRegister.empSearchValue}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support event="onblur" action="#{LeaveRegister.getEmployeeTableData}"  reRender="taskList,searchcriteria,searchtext"/>
                            </h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="listGrid" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:0px">
                            <rich:dataTable  value="#{LeaveRegister.empSearchTable}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Mobile No" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlinkD"  ajaxSingle="true" action="#{LeaveRegister.setEmpRowValues}" oncomplete="#{rich:component('empSearchModelPanel')}.hide();setMask()" 
                                                     reRender="employeeText,idText,ddleaveCodeList" focus="#{LeaveRegister.focusId}">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{LeaveRegister.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList" maxPages="20"/>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="empSearchFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="closeSearch" value="Close" onclick="#{rich:component('empSearchModelPanel')}.hide();"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel> 
            <rich:modalPanel id="popUpGridPanel" width="700" moveable="false" height="250" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide()">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Leave Allocation Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <f:facet name="controls">
                    <h:panelGroup>
                        <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink"/>
                        <rich:componentControl for="popUpGridPanel" attachTo="closelink" operation="hide" event="onclick"/>
                    </h:panelGroup>
                </f:facet>
                <h:form>
                    <h:panelGrid columnClasses="vtop" columns="1" id="table2" styleClass="row2" width="100%">
                        <rich:dataTable value="#{LeaveRegister.leaveRegisterTable}" var="dataItem" rowClasses="gridrow1,gridrow2" id="taskList2" 
                                        rows="5" columnsWidth="100" rowKeyVar="row"onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"width="100%">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Emp. ID" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Emp. Name" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="Paid"  style="text-align:left"/></rich:column>
                                    <rich:column><h:outputText value="Leave Code" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="Days Taken" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="From Date" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="To Date" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="Reason Of Leave" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.paid}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.leaveCode}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.daysTaken}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.leaveFromDate}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.leaveToDate}" /></rich:column>
                            <rich:column><h:outputText value="#{dataItem.reasonOfLeave}" /></rich:column>
                            <rich:column style="text-align:center;width:40px">
                                <a4j:commandLink id="editlink" action="#{LeaveRegister.setLeaveRegisterRowValues}"
                                                 oncomplete="#{rich:component('popUpGridPanel')}.hide();setMask()"
                                                 reRender="mainPanel,errMsg">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{LeaveRegister.currentLeaveRegisterItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList2" maxPages="20" />

                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel> 
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>