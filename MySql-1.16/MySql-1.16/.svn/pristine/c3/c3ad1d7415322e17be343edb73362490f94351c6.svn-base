<%-- 
    Document   : salaryprocessing
    Created on : Aug 5, 2011, 11:02:45 AM
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
            <title>Salary Processing</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
            </script>
        </head>
        <body>
            <a4j:form id="salaryProcessingForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{SalaryProcessing.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Salary Processing"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User:"/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SalaryProcessing.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{SalaryProcessing.message}"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="salaryProcessingCategory" styleClass="output" value="Salary Processing Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="salaryProcessingCategoryList" styleClass="ddlist" size="1" style="width:150px" value="#{SalaryProcessing.processingCateory}">
                                <f:selectItems value="#{SalaryProcessing.processingCateoryList}"/>
                                <a4j:support event="onblur" action="#{SalaryProcessing.onChangeCategory}" oncomplete="if(#{SalaryProcessing.employeeWise=='true'})
                                             {#{rich:component('empSearchModelPanel')}.show(); } else{#{rich:component('empSearchModelPanel')}.hide();}"
                                             reRender="salaryTable,salaryGrid,categorizationList,taskListsalaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="categorizationDetails" styleClass="output" value="Categorization Details"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox disabled="#{SalaryProcessing.disableCategorizeDetails}" id="categorizationList" styleClass="ddlist" size="1" style="width:120px" value="#{SalaryProcessing.categorizationDetails}">
                                <f:selectItems value="#{SalaryProcessing.categorizationDetailsList}"/>
                            </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel12" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="lblYear" styleClass="output" value="Year"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="ddYear" styleClass="ddlist" size="1" style="width:150px" value="#{SalaryProcessing.year}">
                                <f:selectItems value="#{SalaryProcessing.yearList}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="monthCategory" styleClass="output" value="Month Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="monthCategoryList" styleClass="ddlist" size="1" style="width:100px" value="#{SalaryProcessing.monthCategory}">
                                <f:selectItems value="#{SalaryProcessing.monthCategoryList}"/>
                            </h:selectOneListbox>
                     </h:panelGrid>
                     <h:panelGrid id="salryFooter" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="footerPanel">
                                
                                <a4j:commandButton id="save" value="Calculate" action="#{SalaryProcessing.salaryCalculation}" oncomplete="if(#{SalaryProcessing.showGrid =='true'}){ #{rich:component('popUpGridPanel')}.show()}else{#{rich:component('popUpGridPanel')}.hide()}"
                                                   reRender="monthCategoryList,save,categorizationList,salaryTable,salaryGrid,categorizationList,salaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                                <a4j:commandButton id="cancel" value="Refresh" action="#{SalaryProcessing.refresh}" reRender="monthCategoryList,save,postSalaryButton,monthCategoryList,categorizationList,salaryTable,salaryGrid,categorizationList,salaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                                <a4j:commandButton id="exit" value="Exit" action="#{SalaryProcessing.btnExitAction}" reRender="monthCategoryList,save,postSalaryButton,monthCategoryList,categorizationList,salaryTable,salaryGrid,categorizationList,salaryProcessingCategoryList,errMsg,PanelGridEmptable"/>
                            </h:panelGroup>
                     </h:panelGrid>
                 </h:panelGrid>
                </a4j:form>
                <rich:modalPanel id="empSearchModelPanel" width="600" moveable="false" height="250" onmaskdblclick="#{rich:component('empSearchModelPanel')}.hide();">
                    <f:facet name="header">
                        <h:panelGrid style="width:100%;text-align:center;">
                            <h:outputText value="Employee Details" style="text-align:center;"/>
                        </h:panelGrid>
                    </f:facet>
                    <h:form>
                        <h:panelGrid id="PanelGridEmptable" bgcolor="#fff" styleClass="row2" columns="1" width="100%">
                            <h:panelGrid  id="gridPanel77" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" style="text-align:center;" width="100%">
                                <h:outputText/>
                                <h:outputLabel value="Serarch Criteria" styleClass="label"/>
                                <h:selectOneListbox id="ddSearchCriteria" value="#{SalaryProcessing.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="ID"/>
                                    <f:selectItem itemValue="Name"/>
                                </h:selectOneListbox>
                                <h:inputText maxlength="20" id="txtSearchValue" value="#{SalaryProcessing.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input">
                                    <a4j:support event="onblur" action="#{SalaryProcessing.getEmployeeDetails}" reRender="emptablepanel,EmpDetail,noOfRows,table1,taskList1"/>
                                </h:inputText>
                                <h:outputText/>
                            </h:panelGrid>
                            <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                                <rich:dataTable value="#{SalaryProcessing.empSearchTable}"  var="dataItem" rowKeyVar="row" width="100%"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="3" columnsWidth="100"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Phone Number" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}"/></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}"/></rich:column>

                                <rich:column style="text-align:center;width:40px">
                                    <a4j:commandLink id="selectlink" action="#{SalaryProcessing.setEmpRowValues}"  oncomplete="#{rich:component('empSearchModelPanel')}.hide();"  
                                                     reRender="save,errMsg,salaryProcessingCategoryList,categorizationList" focus="selectlink">
                                        <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SalaryProcessing.currentItem}"/>
                                    </a4j:commandLink>
                                </rich:column>
                                <f:facet name="footer">
                                    <h:outputText value="#{SalaryProcessing.totalEmpRecords} active employees found" style="text-align:center" />
                                </f:facet>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="10" />
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="empSearchFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="closeSearch" value="Close" onclick="#{rich:component('empSearchModelPanel')}.hide();"/>
                    </h:panelGrid>
                </h:form>
               </rich:modalPanel> 
            <rich:modalPanel id="popUpGridPanel" width="750" moveable="false" height="475" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide()">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Salary Allocation Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:form>
                    <h:panelGrid columnClasses="vtop" columns="1" id="salaryGrid" styleClass="row2" width="100%">
                        <rich:dataTable var="dataItem" value="#{SalaryProcessing.salaryProcessingGrid}" rowClasses="gridrow1,gridrow2" id="salaryTable" rows="10" columnsWidth="100"
                                        rowKeyVar="row" onRowMouseOver="this.style.backgroundColor='#B5F3FB'" width="100%"
                                        onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column><h:outputText value="Employee Name" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Emloyee Id" style="text-align:left" /></rich:column>
                                    <rich:column><h:outputText value="Month" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Component Type" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Component Amount" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Month Gross Salary" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Income Tax" style="text-align:center"/></rich:column>
                                    <rich:column><h:outputText value="Net Salary" style="text-align:center"/></rich:column>
                                </rich:columnGroup>
                             </f:facet>                            
                            <rich:column><h:outputText value="#{dataItem.employeeName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.employeeId}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.month}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.compType}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.compAmt}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.grossSalary}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.incomeTax}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.netSalary}"/></rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="salaryTable" maxPages="10" />
                    </h:panelGrid>

                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup>
                            <h:commandButton id="printPdf" value="PDF Download" action="#{SalaryProcessing.getSalRegisterRep}" />
                            <a4j:commandButton id="postBtn" value="Post" action="#{SalaryProcessing.salaryPosting}" onclick="#{rich:component('popUpGridPanel')}.hide()"  reRender="monthCategoryList,save,categorizationList,categorizationList,salaryProcessingCategoryList,errMsg"/>
                            <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="monthCategoryList,save,categorizationList,categorizationList,salaryProcessingCategoryList,errMsg"/>
                        </h:panelGroup>
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
