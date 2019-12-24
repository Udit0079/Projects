<%-- 
    Document   : SalaryAllocation
    Created on : Jul 11, 2011, 10:42:58 AM
    Author     : Sudhir Kr Bisht
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
            <title>Salary Allocation</title>
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
                    jQuery(".calInstDate").mask("99/99/9999");
                } 
            </script>
        </head>
        <body>
            <a4j:form id="SalaryAllocationForm">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date:"/>
                            <h:outputText id="stDate" styleClass="output" value="#{SalaryAllocation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Salary Allocation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{SalaryAllocation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="height:1px;text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{SalaryAllocation.message}"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel6" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{SalaryAllocation.function}" style="width:90px">
                                <f:selectItems value="#{SalaryAllocation.functionList}"/>
                                <a4j:support event="onblur" action="#{SalaryAllocation.onChangeFunction}" reRender="popUpGridPanel,errMsg" focus="#{SalaryAllocation.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="allocationCategory" styleClass="output" value="Salary Allocation Category"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox id="SalaryAllocationlist" styleClass="ddlist" size="1" style="width:120px" value="#{SalaryAllocation.allocationCategory}">
                                <f:selectItems value="#{SalaryAllocation.allocationCategoryList}"/>
                                <a4j:support event="onblur" action="#{SalaryAllocation.onChangeCategory}"
                                             oncomplete="if(#{SalaryAllocation.popupOpen == 1}){#{rich:component('empSearchModelPanel')}.show();} else{#{rich:component('empSearchModelPanel')}.hide();}"
                                             reRender="salaryAllocationTable,gridEmployeeTable,categorizationList,errMsg,PanelGridEmptable,salaryStructureGrid"/>
                            </h:selectOneListbox>
                     </h:panelGrid>
                    
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" width="100%" styleClass="row2">
                            <h:outputLabel id="categorizationDetails" styleClass="output" value="Categorization Details"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox disabled="#{SalaryAllocation.disableCategorizeDetails}" id="categorizationList" styleClass="ddlist" size="1" style="width:120px" value="#{SalaryAllocation.categorizationDetails}">
                                <f:selectItems value="#{SalaryAllocation.categorizationDetailsList}"/>
                                <a4j:support event="onblur" action="#{SalaryAllocation.getSalaryAllocationGridToEmployees}" oncomplete="if(#{SalaryAllocation.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } 
                                             else {  #{rich:component('popUpGridPanel')}.hide(); }" reRender="gridEmployeeTable,errMsg,basicSalaryText,allocDate"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="basicSalary" styleClass="output" value="Basic Salary"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText maxlength="8" id="basicSalaryText" styleClass="input" value="#{SalaryAllocation.basicSalary}" style="width:115px">
                                <a4j:support event="onblur" action="#{SalaryAllocation.getSalaryStruct}" reRender="salaryPanelGrid,errMsg,gridPanel10,salaryStructureList,salaryStructureList2"/> 
                            </h:inputText>
                      </h:panelGrid>
                    <h:panelGrid columnClasses="col7,col7,col7,col7,col7,col7" columns="6" id="gridPanel10" style="text-align:center;" width="100%" styleClass="row2">
                        <h:panelGrid id="salaryStructureGrid" style="width:100%;height:300px;" styleClass="row1" columnClasses="vtop" >
                            <rich:dataTable  value="#{SalaryAllocation.allSlabListItem}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="salaryStructureList" rows="9" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="120px"><h:outputText value="SlabNumber"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="BaseComponent"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="DependentComponent"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="SlabCriteria"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="SalarySlabId"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="SlabCriteriaAmt"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="minCriteriaAmt"/></rich:column> 
                                        <rich:column width="120px"><h:outputText value="maxCriteriaAmt"/></rich:column>  
                                        <rich:column width="45px"><h:outputText value="Select"/></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.slabNumber}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.baseComponent}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.dependentComponent}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteria}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.salarySlabId}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.slabCriteriaAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.minCriteriaAmt}"/></rich:column>
                                <rich:column><h:outputText value="#{dataItem.maxCriteriaAmt}"/></rich:column>  
                                <rich:column style="text-align:center">
                                    <h:selectBooleanCheckbox value = "#{dataItem.selected}" id = "checkMe" />
                                    <a4j:support event="onclick" action="#{SalaryAllocation.finalSelectedSlabList()}" reRender="salaryStructureList2">
                                        <f:setPropertyActionListener value="#{dataItem}" target="#{SalaryAllocation.salryAllocCurrentItem}"/>
                                    </a4j:support>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="salaryStructureList" maxPages="5"/>
                        </h:panelGrid>
                        <h:panelGrid id="salaryStructureGrid2" style="width:100%;height:300px;" styleClass="row1" columnClasses="vtop" >
                            <rich:dataTable  value="#{SalaryAllocation.selectedSlabList}" var="dataItem"
                                             rowClasses="gridrow1, gridrow2" id="salaryStructureList2" rows="8" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="120px"><h:outputText value="SlabNumber"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="BaseComponent"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="DependentComponent"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="SlabCriteria"/></rich:column>
                                        <rich:column width="120px"><h:outputText value="SalarySlabId"/></rich:column>
                                                                            
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column><h:outputText value="#{dataItem.slabNumber}"/></rich:column>  
                                <rich:column><h:outputText value="#{dataItem.baseComponent}"/></rich:column>  
                                <rich:column><h:outputText value="#{dataItem.dependentComponent}"/></rich:column>  
                                <rich:column><h:outputText value="#{dataItem.slabCriteria}"/></rich:column>  
                                <rich:column><h:outputText value="#{dataItem.salarySlabId}"/></rich:column>  
                              
                            </rich:dataTable>
                            <rich:datascroller align="left" for="salaryStructureList2" maxPages="5"/>
                        </h:panelGrid>	
                    </h:panelGrid>   
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel12" style="text-align:left;" width="100%" styleClass="row1">
                        
                        <h:outputLabel id="allocationDateLabel" styleClass="output" value="Allocation Date"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:inputText id="allocDate" value="#{SalaryAllocation.allocationnDate}" style="width:85px" styleClass="input calInstDate">
                                <a4j:support event="onblur" oncomplete="setMask()"/>
                          </h:inputText>

                        <h:outputLabel />
                        <h:outputLabel />

                    </h:panelGrid>
                    <h:panelGrid id="SalaryAllocationFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:region id="processActionRegion">
                            <h:panelGroup id="footerPanel">
                                <a4j:commandButton id="save" disabled="#{SalaryAllocation.disableSave}" value="Save" action="#{SalaryAllocation.saveSalaryAllocation}" oncomplete="setMask();"
                                                   reRender="save,delete,amount,componentText,errMsg,SalaryAllocationlist,categorizationList,basicSalaryText,gridEmployeeTable,optionList,salaryPanelGrid,mainPanel,allocDate"/>
                                <a4j:commandButton id="delete" disabled="#{SalaryAllocation.disableDelete}" value="Delete" action="#{SalaryAllocation.deleteSalaryAllocation}" oncomplete="setMask();"
                                                   reRender="save,delete,amount,componentText,errMsg,SalaryAllocationlist,categorizationList,basicSalaryText,allocationDate,gridEmployeeTable,optionList"/>
                                <a4j:commandButton id="cancel" value="Refresh" action="#{SalaryAllocation.refresh}" oncomplete="setMask();" 
                                                   reRender="save,delete,amount,componentText,errMsg,SalaryAllocationlist,categorizationList,basicSalaryText,gridEmployeeTable,optionList,salaryPanelGrid,gridPanel10,categorizationList,allocDate"/>
                                <a4j:commandButton id="exit" value="Exit" action="#{SalaryAllocation.btnExitAction}" 
                                                   reRender="save,delete,amount,componentText,errMsg,SalaryAllocationlist,categorizationList,basicSalaryText,gridEmployeeTable,optionList"/>
                            </h:panelGroup>
                        </a4j:region>
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
                        <h:panelGrid id="criteriaGrid" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:center" width="100%">
                            <h:outputText/>
                            <h:outputLabel value="Serarch Criteria" styleClass="output"/>
                            <h:selectOneListbox id="searchcriteria" styleClass="ddlist" size="1" style="width: 80px" value="#{SalaryAllocation.empSearchCriteria}">
                                <f:selectItem itemValue="Id"/>
                                <f:selectItem itemValue="Name"/>
                            </h:selectOneListbox>

                            <h:inputText id="searchtext" value="#{SalaryAllocation.empSearchValue}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support event="onblur" action="#{SalaryAllocation.getEmployeeTableDataEmloyeeWise}"  reRender="taskList,searchcriteria,searchtext,basicSalaryText,allocDate"/>
                            </h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                       
                        <h:panelGrid id="listGrid" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:0px">
                            <a4j:region>
                                <rich:dataTable  value="#{SalaryAllocation.empSearchTable}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="5" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                            <rich:column width="150px" ><h:outputText value="Employee Name" /></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Mobile No" /></rich:column>
                                            <rich:column width="50px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column width="150px" ><h:outputText value="#{dataItem.empName}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                    <rich:column width="50px" >
                                        <a4j:commandLink id="selectlinkD"  ajaxSingle="true" action="#{SalaryAllocation.setEmpRowValues}" oncomplete="#{rich:component('empSearchModelPanel')}.hide();setMask();
                                                         if(#{SalaryAllocation.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); }" 
                                                         reRender="gridEmployeeTable,salaryAllocationTable,PanelGridEmptable,categorizationList,gridPanel10,salaryStructureGrid,basicSalaryText,allocDate" focus="#{leaveAllocation.focusId}">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{SalaryAllocation.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList" maxPages="5"/>
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                    <h:panelGrid id="empSearchFooter" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="closeSearch" value="Close" onclick="#{rich:component('empSearchModelPanel')}.hide();"/>
                    </h:panelGrid>
                </h:form>    
              </rich:modalPanel> 
            <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" height="375" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide()">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Salary Allocation Edit Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:form>
                    <h:panelGrid id="gridEmployeeTable" styleClass="row1" columnClasses="vtop" >
                        <rich:dataTable  value="#{SalaryAllocation.salaryAllocationGrid}" var="dataItem"
                                         rowClasses="gridrow1, gridrow2" id="salaryAllocationTable" rows="8" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                         onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="60px"><h:outputText value="Employee Id"/></rich:column>
                                    <rich:column width="150px"><h:outputText value="Employee Name"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Allocation Date"/></rich:column>
                                    <%--rich:column width="70px"><h:outputText value="Months"/></rich:column--%>
                                    <rich:column width="80px"><h:outputText value="Component"/></rich:column>
                                    <rich:column width="80px"><h:outputText value="Amount"/></rich:column>
                                    <rich:column width="50px"><h:outputText value="Select"/></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column><h:outputText value="#{dataItem.empid}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.employeeName}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.allocationDate}"/></rich:column>
                            <%--rich:column><h:outputText value="#{dataItem.months}"/></rich:column--%>
                            <rich:column><h:outputText value="#{dataItem.component}"/></rich:column>
                            <rich:column><h:outputText value="#{dataItem.amount}"/></rich:column>
                            <rich:column>
                                <a4j:commandLink ajaxSingle="true" id="deletelink" reRender="amount,componentText,errMsg,SalaryAllocationlist,categorizationList,allocationDate,delete" 
                                                 action="#{SalaryAllocation.empSalaryAllocationDataSet}" oncomplete="#{rich:component('popUpGridPanel')}.hide()">
                                    <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem}" target="#{SalaryAllocation.salryAllocCurrentItem}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller ajaxSingle="true" align="left" for="salaryAllocationTable" maxPages="5"/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </h:form>
            </rich:modalPanel>                        
            <a4j:status onstart="#{rich:component('wait')}.show()" onstop="#{rich:component('wait')}.hide()" for="processActionRegion"/>
            <rich:modalPanel id="wait" autosized="true" width="200" moveable="true" resizeable="false" style="background-color:#edebeb;text-align:center;font-weight:bold;">
                <f:facet name="header">
                    <h:graphicImage url="/resources/images/ajax-loader.gif"/>
                </f:facet>
            </rich:modalPanel>
        </body>
    </html>
</f:view>