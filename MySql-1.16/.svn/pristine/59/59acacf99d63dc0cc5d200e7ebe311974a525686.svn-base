<%--
  Document  : LoanActivity
  Created on : Jul 20, 2011, 2:54:12 PM
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
            <title>Loan Activity</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="LoanActivity">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headepanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{LoanActivity.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Loan Activity"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{LoanActivity.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2" columns="2" id="errPanel" style="text-align:center;" width="100%" styleClass="row2">
                        <h:outputText id="errMsg" value="#{LoanActivity.message}" styleClass="error"/>
                    </h:panelGrid>
                      <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:120px" styleClass="ddlist" value="#{LoanActivity.operation}">
                                <f:selectItems value="#{LoanActivity.operationList}"/>
                                <a4j:support event="onblur" action="#{LoanActivity.setOperationOnBlur}" reRender="popUpGridPanel,popUpGridEditPanel,mainPanel" oncomplete="if(#{LoanActivity.operation=='1'}){ #{rich:component('popUpGridEditPanel')}.hide(); #{rich:component('popUpGridPanel')}.show(); } else if(#{LoanActivity.operation=='2'}){  #{rich:component('popUpGridPanel')}.hide(); #{rich:component('popUpGridEditPanel')}.show(); };" />
                        </h:selectOneListbox>
                         <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <rich:panel header="Employee Detail">
                        <h:panelGrid id="mainPanel1" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel1" style="text-align:left;" width="100%" styleClass="row1">
                                <h:outputLabel styleClass="label" value="Employee Id"/>
                                <h:inputText id="txtEmployeeId" value="#{LoanActivity.empId}" disabled="true" styleClass="input" maxlength="20"/>
                                <h:outputLabel styleClass="label" value="Employee Name"/>
                                <h:inputText id="txtEmployeeName" value="#{LoanActivity.empName}" disabled="true" styleClass="input" maxlength="100"/>
                                <h:outputLabel styleClass="label" value="Department"/>
                                <h:inputText id="txtDepartment" value="#{LoanActivity.department}" disabled="true" styleClass="input" maxlength="20"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel2" style="text-align:left;" width="100%" styleClass="row2">
                                <h:outputLabel styleClass="label" value="Designation"/>
                                <h:inputText id="txtDesignation" value="#{LoanActivity.designation}" disabled="true"  styleClass="input" maxlength="20"/>
                                <h:outputLabel styleClass="label" value="Grade"/>
                                <h:inputText id="txtGrade" value="#{LoanActivity.grade}" disabled="true" styleClass="input" maxlength="20"/>
                                <h:outputLabel styleClass="label" value="Basic Salary"/>
                                <h:inputText id="txtBasicSalary" value="#{LoanActivity.basicSalary}" disabled="true" styleClass="input" maxlength="20"/>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>

                    <rich:panel header="Loan Detail">

                        <h:panelGrid id="mainPanel12" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel4" style="text-align:left;" width="100%" styleClass="row1">
                                <h:outputLabel styleClass="label" value="Loan Type"/>
                                <h:selectOneListbox id="ddLoanType" size="1" styleClass="ddlist" style="width:130px" value="#{LoanActivity.loanType}">
                                    <f:selectItems value="#{LoanActivity.loansList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel styleClass="label" value="Sanction Date"/>
                                <rich:calendar enableManualInput="true" id="calSanctionDate" value="#{LoanActivity.sanctionDate}" datePattern="dd/MM/yyyy"/>
                                <h:outputLabel styleClass="label" value="Starting Date"/>
                                <rich:calendar enableManualInput="true" id="calStartingDate" datePattern="dd/MM/yyyy" value="#{LoanActivity.startingDate}"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel5" style="text-align:left;" width="100%" styleClass="row2">
                                <h:outputLabel styleClass="label" value="Sanction Amount"/>
                                <h:inputText id="txtSanctionAmount" value="#{LoanActivity.sanctionAmount}" styleClass="input" maxlength="10"/>
                                <h:outputLabel styleClass="label" value="ROI %"/>
                                <h:inputText id="txtROI" value="#{LoanActivity.ROI}" styleClass="input" maxlength="5"/>
                                <h:outputLabel styleClass="label" value="No. of Installment"/>
                                <h:inputText id="txtNoOfInstallment" value="#{LoanActivity.noOfInstallment}" styleClass="input" maxlength="5"/>
                            </h:panelGrid>

                            <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">                                
                                <h:outputLabel styleClass="label" value="Installment Plan"/>
                                <h:selectOneListbox id="ddloanPlan" size="1" styleClass="ddlist" style="width:130px" value="#{LoanActivity.loanPlan}">
                                    <f:selectItems value="#{LoanActivity.loanPlanList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel styleClass="label" value="Periodicity"/>
                                <h:selectOneListbox id="ddPeriodicity" size="1" styleClass="ddlist" style="width:130px" value="#{LoanActivity.periodicity}">
                                    <f:selectItems value="#{LoanActivity.periodicityList}"/>
                                </h:selectOneListbox>
                                <h:outputLabel/><h:outputLabel/>
                            </h:panelGrid>
                            <h:panelGrid id="footerPanel1" style="width:100%;text-align:center;" styleClass="row2">
                                <a4j:commandButton id="btnInstallmentPlan" value="Installment Plan" action="#{LoanActivity.calculateInstallmentPlan}"
                                                   oncomplete="if(#{LoanActivity.flag1==true})
                                                   {
                                                   #{rich:component('acView1')}.show();
                                                   }"
                                                   reRender="acView1,errMsg,btnSave"
                                                   focus="acViewClose1"/>

                                <rich:modalPanel id="acView1" autosized="true" style="text-align:center">
                                    <f:facet name="header">
                                        <h:outputText value="Installment Plan"/>
                                    </f:facet>

                                    <f:facet name="controls">
                                        <h:panelGroup>
                                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                                            <rich:componentControl for="acView1" attachTo="closelink1" operation="hide" event="onclick"/>
                                        </h:panelGroup>
                                    </f:facet>

                                    <h:panelGrid columnClasses="vtop" columns="1" id="gridPanelInstallment" width="100%" styleClass="row2" >

                                        <rich:dataTable value="#{LoanActivity.table}" var="dataItem"
                                                        rowClasses="row1, row2" id="InstallmentList"
                                                        rows="12" columnsWidth="100" rowKeyVar="row"
                                                        onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                        width="100%">

                                            <f:facet name="header">
                                                <rich:columnGroup>
                                                    <rich:column breakBefore="true"><h:outputText value="S. No."/></rich:column>
                                                    <rich:column><h:outputText value="Due Date"/></rich:column>
                                                    <rich:column><h:outputText value="Principal"/></rich:column>
                                                    <rich:column><h:outputText value="Interest Amount"/></rich:column>
                                                    <rich:column><h:outputText value="Total Amount"/></rich:column>
                                                    <rich:column><h:outputText value="Installment No."/></rich:column>
                                                    <rich:column><h:outputText value="Paid"/></rich:column>
                                                </rich:columnGroup>
                                            </f:facet>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.sno}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.dueDate}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.principal}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.interestAmount}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.totalAmount}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.currentInstallment}"/></rich:column>
                                            <rich:column style="text-align:center"><h:outputText value="#{dataItem.repayFlag}"/></rich:column>

                                        </rich:dataTable>
                                        <rich:datascroller id="scroller20" for="InstallmentList" maxPages="20"/>

                                    </h:panelGrid>

                                    <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                                        <h:panelGroup id="acViewBtnPanel">
                                            <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;">
                                            </a4j:commandButton>
                                        </h:panelGroup>
                                    </h:panelGrid>
                                </rich:modalPanel>
                            </h:panelGrid>
                        </h:panelGrid>
                    </rich:panel>

                    <h:panelGrid id="footerPanel2" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="btnSave" value="Save" action="#{LoanActivity.saveAction}" reRender="mainPanel" disabled="#{LoanActivity.disableSaveButton}"/>
                            <a4j:commandButton id="btnDelete" value="Delete" disabled="#{LoanActivity.disableDeleteButton}" 
                                               action="#{LoanActivity.deleteAction}" 
                                               reRender="mainPanel"/>
                            <a4j:commandButton id="btnCancel" value="Cancel" action="#{LoanActivity.cancelAction}" type="reset" reRender="mainPanel,btnSave,btnView,btnDelete"/>
                            <a4j:commandButton id="btnExit" value="Exit" action="#{LoanActivity.exitAction}"/>
                        </h:panelGroup>

                    </h:panelGrid>

                </h:panelGrid>
                 <rich:modalPanel id="popUpGridEditPanel" label="Form" width="800" height="250" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Loan Activity Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                      <h:panelGrid columnClasses="vtop" columns="1" id="table2" styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanActivity.loanTable}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList2" rows="4" columnsWidth="100" rowKeyVar="row"
                                                    onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                    onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%">
                                        <f:facet name="header">
                                            <rich:columnGroup>
                                                <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                                <rich:column><h:outputText value="Loan No." style="text-align:left" /></rich:column>
                                                <rich:column><h:outputText value="Employee Name" style="text-align:center"/></rich:column>
                                                <rich:column><h:outputText value="Loan Type" style="text-align:left" /></rich:column>
                                                <rich:column><h:outputText value="Sanction Date" style="text-align:left"/></rich:column>
                                                <rich:column><h:outputText value="Sanction Amount" style="text-align:left" /></rich:column>
                                                <rich:column><h:outputText value="Select" style="text-align:center"/></rich:column>
                                            </rich:columnGroup>
                                        </f:facet>
                                        <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.empLoanNo}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.loanTypeDescription}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.sanctionDate}" /></rich:column>
                                        <rich:column><h:outputText value="#{dataItem.sanctionAmount}" /></rich:column>
                                        <rich:column style="text-align:center;width:40px">
                                            <a4j:commandLink id="selectlink2" action="#{LoanActivity.setLoanRowValues}"
                                                             reRender="txtEmployeeId,txtEmployeeName,txtDepartment,txtDesignation,txtGrade,
                                                             txtBasicSalary,taskList2,table2,table1,ddLoanType,calSanctionDate,txtSanctionAmount,
                                                             txtROI,txtNoOfInstallment,ddloanPlan,ddPeriodicity,calStartingDate,txtAuthorizedUser,btnSave,btnView,btnDelete" oncomplete="#{rich:component('popUpGridEditPanel')}.hide()" focus="selectlink2">
                                                <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{LoanActivity.currentLoanItem}"/>                                                
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList2" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                 
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridEditPanel')}.hide()" reRender="popUpGridEditPanel"/>
                    </h:panelGrid>
                </rich:modalPanel>                           

            </a4j:form>
             <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                      <h:panelGrid id="PanelGridEmptable" bgcolor="#fff" styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">

                                <h:panelGroup layout="block">
                                    <h:outputLabel value="Search Employee" styleClass="label"/>
                                    &nbsp;
                                    <h:inputText id="txtSearchValue" value="#{LoanActivity.empSearchValue}" maxlength="100" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                    &nbsp;
                                    <h:outputLabel value="By"/>
                                    &nbsp;
                                    <h:selectOneListbox id="ddSearchCriteria" value="#{LoanActivity.empSearchCriteria}" size="1">
                                        <f:selectItem itemValue="Name"/>
                                        <f:selectItem itemValue="ID"/>
                                    </h:selectOneListbox>                                    
                                    &nbsp;
                                    <a4j:commandButton value="Find" action="#{LoanActivity.getEmployeeTableData}" reRender="emptablepanel,EmpDetail,noOfRows,table1,table2,taskList1"/>
                                </h:panelGroup>

                            </h:panelGrid>

                            <h:panelGrid columnClasses="vtop" columns="1" id="table1"  styleClass="row2" width="100%">
                                <a4j:region>
                                    <rich:dataTable value="#{LoanActivity.empSearchTable}" var="dataItem"
                                                    rowClasses="gridrow1,gridrow2" id="taskList1" rows="6" columnsWidth="100" rowKeyVar="row"
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
                                            <a4j:commandLink id="selectlink" action="#{LoanActivity.setEmpRowValues}" reRender="mainPanel" oncomplete="#{rich:component('popUpGridPanel')}.hide()" >
                                                <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                                <f:setPropertyActionListener value="#{dataItem}" target="#{LoanActivity.currentEmpItem}"/>                                                
                                            </a4j:commandLink>
                                        </rich:column>
                                    </rich:dataTable>
                                    <rich:datascroller align="left" for="taskList1" maxPages="10" />
                                </a4j:region>
                            </h:panelGrid>
                    
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close"  onclick="#{rich:component('popUpGridPanel')}.hide()"  reRender="popUpGridPanel,mainPanel"/>
                    </h:panelGrid>
                </a4j:form>                            
            </rich:modalPanel>                              
        </body>
    </html>
</f:view>
