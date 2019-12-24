<%--
    Document   : ShiftMapping
    Created on : Jul 12, 2011, 3:47:56 PM
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
            <title>Shift Mapping</title>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <script type="text/javascript">
                var row;
            </script>
        </head>
        <body>
            <a4j:form id="ShiftMapping">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="headerpanel" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{ShiftMapping.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Shift Mapping"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ShiftMapping.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="errPanel"   width="100%" style="height:1px;text-align:center;" styleClass="row1">
                        <h:outputText id="errMsg" value="#{ShiftMapping.message}" styleClass="error"/>
                    </h:panelGrid>                    
                     <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" style="text-align:center;" id="gridPanelFunc"  width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:150px" styleClass="ddlist" value="#{ShiftMapping.operation}">
                                <f:selectItems value="#{ShiftMapping.operationList}"/>
                                <a4j:support event="onblur" action="#{ShiftMapping.viewAction}"  oncomplete="
                                               if(#{ShiftMapping.operation=='2'})
                                               {
                                               #{rich:component('popUpEditGridPanel')}.show();
                                               }
                                               else
                                               {
                                               #{rich:component('popUpEditGridPanel')}.hide();
                                               }
                                               " reRender="footerPanel4,taskList2,popUpEditGridPanel"/>
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel1" style="text-align:center;" width="100%" styleClass="row1">
                        <h:outputLabel id="lblCategorization" styleClass="label" value="Categorization "><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddlCategorization" styleClass="ddlist" size="1" style="width:150px" value="#{ShiftMapping.catValue}">
                            <f:selectItems value="#{ShiftMapping.catList}"/>
                            <a4j:support event="onchange" action="#{ShiftMapping.getCategorizationDetailList}"
                                         oncomplete="
                                         if(#{ShiftMapping.employeeWise=='true'})
                                         {
                                         #{rich:component('popUpGridPanel')}.show();
                                         }
                                         else
                                         {
                                         #{rich:component('popUpGridPanel')}.hide();
                                         }"
                                         reRender="popUpGridPanel,errMsg,ddlCategorizationDetails"/>
                        </h:selectOneListbox>
                        <h:outputLabel id="lblCategorizationDetails" styleClass="label" value="Categorization Details "><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox disabled="#{ShiftMapping.disableCategorizeDetails}" id="ddlCategorizationDetails" styleClass="ddlist" size="1" style="width:180px" value="#{ShiftMapping.catDetValue}">
                            <f:selectItems value="#{ShiftMapping.catDetList}"/>                            
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel2" style="text-align:center;" width="100%" styleClass="row2">
                        <h:outputLabel id="lblShift" styleClass="label" value="Shift"><font class="required" color="red">*</font></h:outputLabel>
                        <h:selectOneListbox id="ddlShift" styleClass="ddlist" size="1" style="width:150px" value="#{ShiftMapping.shiftValue}">
                            <f:selectItems value="#{ShiftMapping.shiftList}"/>
                            <a4j:support event="onblur" action="#{ShiftMapping.onChangeShift}" reRender="footerPanel4"/>
                        </h:selectOneListbox>
                        <h:outputLabel/><h:outputLabel/>
                    </h:panelGrid>
                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save" value="Save" action="#{ShiftMapping.saveAction}" disabled="#{ShiftMapping.disableSaveButton}"
                                               reRender="mainPanel,errMsg,table2,PanelGridShifttable"/>                          
                            <a4j:commandButton id="delete" value="Delete" action="#{ShiftMapping.deleteAction}" disabled="#{ShiftMapping.disableDeleteButton}"
                                               reRender="errMsg,table2,footerPanel4,mainPanel"/>
                            <a4j:commandButton id="cancel" value="Cancel" action="#{ShiftMapping.cancelAction}" type="reset" reRender="mainPanel"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{ShiftMapping.exitAction}"  reRender="errMsg"/>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
              <rich:modalPanel id="popUpEditGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpEditGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Shift Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:panelGrid id="PanelGridShifttable" bgcolor="#fff" styleClass="row2" columns="1" style="border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid columnClasses="vtop" columns="1" id="table2" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{ShiftMapping.empShiftTable}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList2" rows="5" columnsWidth="100"
                                                rowKeyVar="row"
                                                onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'"
                                                width="100%">
                                    
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column><h:outputText value="S.No." style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee ID" style="text-align:center"/></rich:column>
                                            <rich:column><h:outputText value="Employee Name" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Shift Code"  style="text-align:left"/></rich:column>
                                            <rich:column><h:outputText value="Shift Description" style="text-align:left" /></rich:column>
                                            <rich:column><h:outputText value="Select"  style="text-align:center"/></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.shiftCode}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.shiftDescription}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink2" action="#{ShiftMapping.setShiftTableRowValues}"
                                                         oncomplete="#{rich:component('popUpEditGridPanel')}.hide()"
                                                         reRender="popUpEditGridPanel,footerPanel4,taskList2,table2,ddlCategorizationDetails,ddlShift,ddlCategorization" focus="selectlink2">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ShiftMapping.currentShiftItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShiftMapping.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                    <f:facet name="footer">
                                        <h:outputText value="#{ShiftMapping.totalShiftTableRecords} rows found" style="text-align:center" />
                                    </f:facet>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList2" maxPages="20" />
                            </a4j:region>
                        </h:panelGrid>
                    </h:panelGrid>
                 
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpEditGridPanel')}.hide()" reRender="popUpEditGridPanel"/>
                    </h:panelGrid>
                </rich:modalPanel>         
            </a4j:form>
             <rich:modalPanel id="popUpGridPanel" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <h:panelGrid id="PanelGridEmptable" bgcolor="#fff" styleClass="row2" columns="1" style="border:1px ridge #BED6F8;" width="100%">
                     <a4j:form>      
                    <h:panelGrid  id="gridPanel77" style="border:1px ridge #BED6F8;text-align:center;" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee" styleClass="label"/>
                                &nbsp;
                                <h:inputText id="txtSearchValue" value="#{ShiftMapping.empSearchValue}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input" maxlength="30"/>
                                &nbsp;
                                <h:outputLabel value="By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria" value="#{ShiftMapping.empSearchCriteria}" size="1">
                                    <f:selectItem itemValue="Name"/>
                                    <f:selectItem itemValue="ID"/>
                                </h:selectOneListbox>
                                &nbsp;                                                                
                                <a4j:commandButton value="Find" action="#{ShiftMapping.getEmployeeTableData}"
                                                   oncomplete="#{rich:element('table1')}.style.display='';"
                                                   reRender="emptablepanel,EmpDetail,noOfRows,table1,taskList1"/>
                            </h:panelGroup>
                        </h:panelGrid>

                        <h:panelGrid columnClasses="vtop" columns="1" id="table1" styleClass="row2" width="100%">
                            <a4j:region>
                                <rich:dataTable value="#{ShiftMapping.empSearchTable}" var="dataItem"
                                                rowClasses="gridrow1,gridrow2" id="taskList1" rows="10" columnsWidth="100"
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
                                    <rich:column><h:outputText value="#{dataItem.sno}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empId}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empName}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empAddress}" /></rich:column>
                                    <rich:column><h:outputText value="#{dataItem.empPhone}" /></rich:column>
                                    <rich:column style="text-align:center;width:40px">
                                        <a4j:commandLink id="selectlink" action="#{ShiftMapping.setEmpRowValues}"
                                                         oncomplete="#{rich:component('popUpGridPanel')}.hide()"    reRender="taskList1,PanelGridEmptable,ddlCategorizationDetails" focus="selectlink">
                                            <h:graphicImage value="/resources/images/select.gif" style="border:0" />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{ShiftMapping.currentItem}"/>
                                            <f:setPropertyActionListener value="#{row}" target="#{ShiftMapping.currentRow}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                                    <f:facet name="footer">
                                        <h:outputText value="#{ShiftMapping.totalEmpRecords} rows found" style="text-align:center" />
                                    </f:facet>
                                </rich:dataTable>
                                <rich:datascroller align="left" for="taskList1" maxPages="10" />
                            </a4j:region>
                        </h:panelGrid>
                       </a4j:form>            
                    </h:panelGrid>               
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>                                
                </rich:modalPanel>                               
        </body>
    </html>
</f:view>
