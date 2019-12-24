<%-- 
    Document   : attendanceprocessing
    Created on : Jul 19, 2011, 11:30:23 AM
    Author     : Sudhir Kr Bisht
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
            <title>Attendance Processing</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="attendanceProcessingForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{AttendanceProcessing.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Attendance Processing"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{AttendanceProcessing.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{AttendanceProcessing.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanelFunc"  width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="operationList" style="width:120px" styleClass="ddlist" value="#{AttendanceProcessing.operation}">
                                <f:selectItems value="#{AttendanceProcessing.operationList}"/>
                                <a4j:support event="onblur" action="#{AttendanceProcessing.getAttendancedetailsforEmployee}"  
                                reRender="popUpEditGridPanel,errMsg,attenDetailsPanelGrid,attendanceTable,postAttendance " />
                            </h:selectOneListbox>
                            <h:outputLabel id="attendanceCategory" styleClass="output" value="Attendance Processing Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="attendanceCategoryList" styleClass="ddlist" size="1" style="width:120px" value="#{AttendanceProcessing.attendanceCategory}">
                                <f:selectItems value="#{AttendanceProcessing.attendanceCategoryList}"/>
                                <a4j:support event="onchange" action="#{AttendanceProcessing.onChangeCategory}"
                                             oncomplete="if(#{AttendanceProcessing.employeeWise=='true'}){
                                             #{rich:component('popUpGridPanel')}.show();
                                             }
                                             else
                                             {
                                             #{rich:component('popUpGridPanel')}.hide();
                                             }"
                                             reRender="categorizationList,taskList,attendanceCategoryList,errMsg5,popUpGridPanel ,categorizationDetails,minWorkingList,fromDate,toDate"/>
                            </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel8" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="categorizationDetails" styleClass="output" value="Categorization Details"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox disabled="#{AttendanceProcessing.disableCategorizeDetails}" id="categorizationList" styleClass="ddlist" size="1" style="width:120px" value="#{AttendanceProcessing.categorizationDetails}">
                                <f:selectItems value="#{AttendanceProcessing.categorizationDetailsList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="minWorkingUnit" styleClass="output" value="Minimum Working Unit"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox disabled="#{AttendanceProcessing.disableCalendars}" id="minWorkingList" styleClass="ddlist" size="1" style="width:100px" value="#{AttendanceProcessing.minimumWorkingUnit}">
                                <f:selectItems value="#{AttendanceProcessing.minWorkingUnitList}"/>
                            </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel10" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="dateFrom" styleClass="output" value="Date From"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar disabled="#{AttendanceProcessing.disableCalendars}"  enableManualInput="true" id="fromDate" datePattern="dd/MM/yyyy" value="#{AttendanceProcessing.pageFromDate}" inputSize="10"/>
                            <h:outputLabel id="DateTo" styleClass="output" value="Date To"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <rich:calendar disabled="#{AttendanceProcessing.disableCalendars}" enableManualInput="true" id="toDate" datePattern="dd/MM/yyyy" value="#{AttendanceProcessing.pageToDate}" inputSize="10"/>
                    </h:panelGrid>
                        <rich:panel header="Edit" style="text-align:left;">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="Row3" style="width:100%;text-align:left;" styleClass="row2">
                                <h:outputLabel id="workingDays" styleClass="label" value="Working Days"></h:outputLabel>
                                <h:inputText disabled="#{AttendanceProcessing.disableWorkingDays}" id="workingDaysText" styleClass="input" maxlength="4" value="#{AttendanceProcessing.workingDays}"/>
                                <h:outputLabel id="presentDays" styleClass="label" value="Present Days"></h:outputLabel>
                                <h:inputText disabled="#{AttendanceProcessing.disableWorkingDays}" id="presentDaysText" styleClass="input" maxlength="4" value="#{AttendanceProcessing.presentDays}"/>
                                <h:outputLabel id="lblPaidLeave" styleClass="label" value="Total Paid Leave"></h:outputLabel>
                                <h:inputText  id="txtPaidLeave" styleClass="input" maxlength="2" value="#{AttendanceProcessing.paidLeave}" disabled="#{AttendanceProcessing.disable}" />
                                <h:outputLabel id="absentDays" styleClass="label" value="Absent Days"></h:outputLabel>
                                <h:inputText id="absentDaysText" styleClass="input" maxlength="2" value="#{AttendanceProcessing.absentDays}" disabled="#{AttendanceProcessing.disable}"/>
                                <h:outputLabel id="OverTime" styleClass="label" value="Over Time"></h:outputLabel>
                                <h:inputText disabled="#{AttendanceProcessing.disable}" id="OverTimeText" styleClass="input" maxlength="8" value="#{AttendanceProcessing.overTime}"/>
                                <h:outputLabel id="deductDays" styleClass="label" value="Salary Deduction Days (Without Leave)"></h:outputLabel>
                                <h:inputText disabled="#{AttendanceProcessing.disable}" id="deductDaysText" styleClass="input" maxlength="8" value="#{AttendanceProcessing.deductDays}"/>
                            </h:panelGrid>
                        </rich:panel>
         <%--           <h:panelGrid id="editPostButGrid" style="width:100%;text-align:center;" styleClass="row1">
                        <h:panelGroup id="buttonPanel">
                            <a4j:commandButton disabled="#{AttendanceProcessing.disable}" id="postAttendance" value="Post Attendance" action="#{AttendanceProcessing.postAttendance}" reRender="postAttendance,operationList,attendanceTable,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>  --%>
                        
                    <h:panelGrid columnClasses="vtop1" columns="1" id="attenDetailsPanelGrid" styleClass="row2" style="border:1px ridge #BED6F8;" width="100%">
                         <f:facet name="header">
                        <h:panelGrid style="width:100%;text-align:center;">
                            <h:outputText value="Attendance Details" style="text-align:center;"/>
                        </h:panelGrid>
                    </f:facet>
                     
                        <a4j:region>
                            <rich:dataTable value="#{AttendanceProcessing.empAttenDetailsGrid}" var="dataItem"
                                            rowClasses="gridrow1,gridrow2" id="attendanceTable" rows="10" columnsWidth="100"
                                            rowKeyVar="row"
                                            onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                            onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                            width="100%">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column><h:outputText value="Emloyee Id" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Employee Name" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Month" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Year" style="text-align:left"/></rich:column>
                                        <rich:column><h:outputText value="Date From" style="text-align:left" /></rich:column>
                                        <rich:column><h:outputText value="Date To" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Working Days" style="text-align:center"/></rich:column>\
                                        <rich:column><h:outputText value="Present" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Absent" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Over Time" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Over Time Unit" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Deduct Days" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Post Flag" style="text-align:center"/></rich:column>
                                        <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.emloyeeId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.employeeName}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.month}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.year}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dateFrom}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dateTo}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.workingDays}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.presentDays}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.absentDays}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.overTime}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.overTimeUnit}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.deductDays}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.postFlag}"/></rich:column>
                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{AttendanceProcessing.setAttendanceRowValues}"  reRender="popUpEditGridPanel,operationList,delete,save,editAttendance,postAttendance,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText,txtPaidLeave,deductDaysText">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{AttendanceProcessing.currentGridItem}"/>
                                    </a4j:commandLink>
                                </rich:column> 
                            </rich:dataTable>
                            <rich:datascroller align="left" for="attendanceTable" maxPages="20" />
                        </a4j:region>
                    </h:panelGrid>  

                <%--  <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                      <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpEditGridPanel')}.hide()" reRender="popUpEditGridPanel"/>
                    </h:panelGrid>   --%>
                        
                        
                        
                        
                        
                    <h:outputLabel id="label" value="Select the edit operation to veiw the attendance details of all employees" styleClass="output"/>
                    <h:panelGrid id="formFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="footerPanel">
                            <a4j:commandButton disabled="#{AttendanceProcessing.disable}" id="postAttendance" value="Post Attendance" action="#{AttendanceProcessing.postAttendance}" reRender="postAttendance,operationList,attendanceTable,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText">
                            </a4j:commandButton>
                            <a4j:commandButton id="save" value="Save" disabled="#{AttendanceProcessing.disableSave}" action="#{AttendanceProcessing.saveAttendanceProcessing}" reRender="postAttendance,attenDetailsPanelGrid,errMsg,attendanceCategoryList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText,operationList,attendanceTable,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText">
                            </a4j:commandButton>
                            <a4j:commandButton id="delete" value="Delete" disabled="#{AttendanceProcessing.disable}" action="#{AttendanceProcessing.deleteAttendanceDetails}" reRender="operationList,editAttendance,postAttendance,delete,save,attenDetailsPanelGrid,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText">
                            </a4j:commandButton>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{AttendanceProcessing.refresh}" reRender="operationList,editAttendance,postAttendance,delete,save,attenDetailsPanelGrid,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText,txtPaidLeave,deductDaysText">
                            </a4j:commandButton>
                            <a4j:commandButton id="exit" value="Exit" action="#{AttendanceProcessing.btnExitAction}" reRender="editAttendance,postAttendance,delete,save,attenDetailsPanelGrid,errMsg,attendanceCategoryList,categorizationList,fromDate,toDate,minWorkingList,workingDaysText,presentDaysText,absentDaysText,OverTimeText">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid> 
                </h:panelGrid>
                 <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()"/>
                 <rich:modalPanel id="wait" autosized="true" width="200" moveable="false" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                     <f:facet name="header">
                        <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                     </f:facet>
                 </rich:modalPanel>                        
            </a4j:form>   
            <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="PanelGridEmptable" bgcolor="#fff" styleClass="row2" columns="1" style="border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid  id="gridPanel77" style="border:1px ridge #BED6F8;text-align:center;" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria" value="#{AttendanceProcessing.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="ID"/>
                                    <f:selectItem itemValue="Name"/>
                                </h:selectOneListbox>
                                &nbsp;
                                <h:inputText maxlength="20" id="txtSearchValue" value="#{AttendanceProcessing.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                &nbsp;
                                <a4j:commandButton value="Find" action="#{AttendanceProcessing.getEmployeeTableDataEmloyeeWise}"
                                                   oncomplete="#{rich:element('table1')}.style.display='';"
                                                   reRender="emptablepanel,EmpDetail,noOfRows,table1,taskList1"/>
                            </h:panelGroup>
                        </h:panelGrid>
                        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{AttendanceProcessing.empSearchTable}"  var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="5" columnsWidth="100"
                                                rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                width="100%">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Address"  style="text-align:left"/></rich:column>
                                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empAddress}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}"/></rich:column>

                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{AttendanceProcessing.setEmpRowValues}" oncomplete="#{rich:component('popUpGridPanel')}.hide()"  reRender="PanelGridEmptable,errMsg,attendanceCategoryList,categorizationList" focus="selectlink">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{AttendanceProcessing.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                    <f:facet name="footer">
                                        <h:outputText value="#{AttendanceProcessing.totalEmpRecords} active employees found" style="text-align:center" />
                                    </f:facet>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid> 
                </a4j:form>            
                <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                    <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                </h:panelGrid>                                
            </rich:modalPanel>                                        
        </body> 
    </html> 
</f:view>
