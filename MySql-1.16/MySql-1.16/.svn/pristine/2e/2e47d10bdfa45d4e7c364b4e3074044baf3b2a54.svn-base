<%-- 
    Document   : apprisal
    Created on : Jul 22, 2011, 6:27:11 PM
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
            <title>Appraisal Letter</title>
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
            <a4j:form id="apprisal">
                
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" value="#{Appraisal.todayDate}" styleClass="output"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Appraisal Letter"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" value="#{Appraisal.userName}" styleClass="output"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg5" value="#{Appraisal.errorMsg}"/>
                    </h:panelGrid>
                     <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3" columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:120px" styleClass="ddlist" value="#{Appraisal.operation}">
                                <f:selectItems value="#{Appraisal.operationList}"/>
                                <a4j:support event="onblur" action="#{Appraisal.setOperationOnBlur}" reRender="acView1,errMsg5,mainPanel" oncomplete="if(#{Appraisal.operation=='1'}){ #{rich:component('acView1')}.show(); } else { #{rich:component('acView1')}.hide(); };if(#{Appraisal.operation=='2'}){ #{rich:component('popUpGridPanel')}.show(); } else { #{rich:component('popUpGridPanel')}.hide(); };#{rich:element('appraisaldate')}.style=setTimeMask();#{rich:element('efectivedate')}.style=setTimeMask();" />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel3" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Employee Name"  styleClass="output" />
                        <h:inputText id="empName" value="#{Appraisal.empname}" maxlength="100" styleClass="input" disabled="true" />
                        <h:outputLabel value="Employee Id"  styleClass="output"/>
                        <h:inputText id="empId"  disabled="true" styleClass="input"  maxlength="8" value="#{Appraisal.empid}"/>
                        <h:outputLabel value="Department"  styleClass="output" />
                        <h:inputText id="department" disabled="true" styleClass="input" maxlength="200" value="#{Appraisal.department}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel4" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Designation"   styleClass="output"/>
                        <h:inputText id="designation" disabled="true" maxlength="200" styleClass="input" value="#{Appraisal.designation}" />
                        <h:outputLabel value="Grade"  styleClass="output" />
                        <h:inputText id="grade" disabled="true" styleClass="input" maxlength="200" value="#{Appraisal.grade}" />
                        <h:outputLabel value="Unit"  styleClass="output" />
                        <h:inputText id="unit" disabled="true"styleClass="input" maxlength="200" value="#{Appraisal.unit}"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel5" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Joining Date"  styleClass="output" />
                        <h:inputText id="joiningdate" disabled="true" styleClass="input" style="width:80px" maxlength="8" value="#{Appraisal.joindate}" />
                        <h:outputLabel value="Block From"  styleClass="output" />
                        <h:inputText id="blockfrom" disabled="true" styleClass="input" maxlength="200" value="#{Appraisal.block}" />
                        <h:outputLabel value="Experince (inYers)"  styleClass="output" />
                        <h:inputText id="expirince" disabled="true" styleClass="input" maxlength="8" value="#{Appraisal.expr}" />
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel8" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row2" style="text-align:left">
                        <h:outputLabel value="Appraisal Date"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="appraisaldate" styleClass="input calInstDate"  style="width:80px;setMask()" maxlength="10" value="#{Appraisal.apprisaldt}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel value="Efective Date"  styleClass="output" />
                        <h:panelGroup>
                            <h:inputText id="efectivedate" styleClass="input calInstDate"  style="width:80px;setMask()" maxlength="10" value="#{Appraisal.effectivedt}"  >
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFrom2" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputLabel value="Overall Rating"  styleClass="output" />
                        <h:selectOneListbox id="overallrating" styleClass="ddlist" size="1" style="width:100px" value="#{Appraisal.overallrating}">
                            <f:selectItems value="#{Appraisal.overallratingdata}" />
                        </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanel9" width="100%" columns="6" columnClasses="col3,col3,col3,col3,col3,col3" styleClass="row1" style="text-align:left">
                        <h:outputLabel value="Training Need"  styleClass="output" />
                        <h:selectOneListbox id="trainingneed" styleClass="ddlist" size="1" style="width:120px" value="#{Appraisal.trainingneed}">
                            <f:selectItems value="#{Appraisal.trainingneeddata}" />
                        </h:selectOneListbox>
                        <h:outputLabel value="Increment Amount"  styleClass="output" />
                        <h:inputText id="incrementamount"  styleClass="input"  value="#{Appraisal.incementamt}" />
                        <h:outputText />
                        <h:outputText/>
                    </h:panelGrid>
                    <rich:panel id="richPanel1" header="RECOMMENDATIONS" >
                        <h:panelGrid id="subPanelgrid1" width="100%" columns="5" columnClasses="col2,col2,col2,col2,col2" styleClass="row1" style="text-align:left">
                            <h:outputLabel value="Section Head" styleClass="output"/>
                            <h:inputText id="selectionhead" maxlength="100"styleClass="input" value="#{Appraisal.slcnhd}"  />
                            <h:outputText />
                            <h:outputLabel value="Head of Dept" styleClass="output"/>
                            <h:inputText id="headofdepart" styleClass="input" maxlength="100" value="#{Appraisal.hdpdt}" />
                        </h:panelGrid>
                        <h:panelGrid id="subPanelgrid2" width="100%" columns="5" columnClasses="col2,col2,col2,col2,col2" styleClass="row2" style="text-align:left">
                            <h:outputLabel value="Head of HRD" styleClass="output"/>
                            <h:inputText id="headofhrd"styleClass="input" maxlength="100" value="#{Appraisal.hdhrd}"  />
                            <h:outputText />
                            <h:outputLabel value="Personnel Head" styleClass="output"/>
                            <h:inputText id="personalhead" styleClass="input"  maxlength="100" value="#{Appraisal.prsnlhead}" />
                        </h:panelGrid>
                    </rich:panel>
                    <rich:panel id="richPanel2" header="Performance" style="width:100%">
                        <h:panelGrid id="gridPanel11" width="100%" columns="5" columnClasses="col2,col2,col2,col2,col2" styleClass="row1" style="text-align:left" >
                            <h:outputLabel value="Performance Factor" styleClass="output"/>
                            <h:selectOneListbox id="performancefact" styleClass="ddlist" size="1" style="width:100px" value="#{Appraisal.performancefct}">
                                <f:selectItems value="#{Appraisal.performancefctdata}"/>
                            </h:selectOneListbox>
                            <h:outputText />
                            <h:outputLabel value="Rating" styleClass="output"/>
                            <h:selectOneListbox id="rating" styleClass="ddlist" size="1" style="width:100px" value="#{Appraisal.rating}">
                                <f:selectItems value="#{Appraisal.overallratingdata}" />
                            </h:selectOneListbox>
                        </h:panelGrid>
                    </rich:panel>
                    <h:panelGrid id="footer" width="100%" styleClass="footer" style="text-align:center">
                        <h:panelGroup>
                            <a4j:commandButton id="save" value="Save" action="#{Appraisal.saveData}" oncomplete=" #{rich:element('appraisaldate')}.style=setMask();#{rich:element('efectivedate')}.style=setMask();" disabled="#{Appraisal.save}"reRender="operationList,save,delete,errMsg5,taskList1,empName,empId,department,designation,grade,unit,joiningdate,blockfrom,expirince,appraisaldate,efectivedate,overallrating,trainingneed,incrementamount,selectionhead,headofdepart,headofhrd,personalhead,performancefact,rating"/>
                            <a4j:commandButton id="delete" value="Delete" action="#{Appraisal.deleteData}"disabled="#{Appraisal.delete}" oncomplete=" #{rich:element('appraisaldate')}.style=setMask();#{rich:element('efectivedate')}.style=setMask();" reRender="operationList,save,delete,taskList1,errMsg5,empName,empId,department,designation,grade,unit,joiningdate,blockfrom,expirince,appraisaldate,efectivedate,overallrating,trainingneed,incrementamount,selectionhead,headofdepart,headofhrd,personalhead,performancefact,rating"/>
                            <a4j:commandButton id="refresh" value="Refresh" action="#{Appraisal.refreshbutton}" oncomplete=" #{rich:element('appraisaldate')}.style=setMask();#{rich:element('efectivedate')}.style=setMask();" reRender="operationList,save,delete,errMsg5,taskList1,empName,empId,department,designation,grade,unit,joiningdate,blockfrom,expirince,appraisaldate,efectivedate,overallrating,trainingneed,incrementamount,selectionhead,headofdepart,headofhrd,personalhead,performancefact,rating" />
                            <a4j:commandButton id="exit" value="Exit" action="#{Appraisal.btnExit}" />
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                 <rich:modalPanel id="popUpGridPanel" label="Form" width="800" height="250" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Appraisal Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                  <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{Appraisal.griddata}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="2" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText  value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText  value="Department" /></rich:column>
                                        <rich:column width="100px" ><h:outputText  value="Designation" /></rich:column>
                                        <rich:column width="100px" ><h:outputText  value="Joining Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Experience(In Yrs)" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Appraisal Date" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Effective From" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Increment Amount" /></rich:column>
                                        <rich:column width="100px" ><h:outputText value="Select" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empName}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.deptCodeDescription}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.desgCodeDescription}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.joinDate}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.totExpr}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.appraisalDt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.promWef}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.incrAmt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlink" action="#{Appraisal.selectUpdate}" onclick="#{rich:component('popUpGridPanel')}.hide()" oncomplete=" #{rich:element('appraisaldate')}.style=setMask();#{rich:element('efectivedate')}.style=setMask();" reRender="operationList,appraisaldate,efectivedate,add,update,save,delete,empName,empId,department,designation,grade,unit,joiningdate,blockfrom,expirince,appraisaldate,efectivedate,overallrating,trainingneed,incrementamount,selectionhead,headofdepart,headofhrd,personalhead,performancefact,rating">
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{Appraisal.currentrow}"/> </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                    <h:panelGrid id="footerPaneInRich" style="width:100%;text-align:center;" styleClass="footer">
                        <a4j:commandButton id="close" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide()" reRender="popUpGridPanel"/>
                    </h:panelGrid>
                </rich:modalPanel>                    
            </a4j:form>
             <rich:modalPanel id="acView1" label="Form" width="700" height="300" resizeable="true" moveable="false" onmaskdblclick="#{rich:component('acView1')}.hide();">

                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Employee Search" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                <a4j:form>
                    <h:panelGrid id="mainPanelEmployeeSearch1"  columns="1" style="text-align:left;border:1px ridge #BED6F8;" width="100%">
                        <h:panelGrid id="PanelGridEmptable1"  styleClass="row2" columns="1" style="border:1px ridge #BED6F8" width="100%">
                            <h:panelGroup layout="block">
                                <h:outputLabel value="Search Employee" styleClass="label"/>
                                &nbsp;
                                
                                <h:outputLabel value="By" styleClass="label"/>
                                &nbsp;
                                <h:selectOneListbox id="ddSearchCriteria1" value="#{Appraisal.searchflag}" size="1">
                                    <f:selectItem itemValue="Emp-Id"/>
                                     <f:selectItem itemValue="Emp Name"/>
                                </h:selectOneListbox>
                                 &nbsp;
                                <h:inputText id="txtSearchValue1" value="#{Appraisal.searchtext}" onkeyup="this.value = this.value.toUpperCase();" styleClass="input"/>
                                &nbsp;
                                <a4j:commandButton value="Find"  action="#{Appraisal.onfindMethod}"  reRender="taskList,datascrollerTab"/>
                            </h:panelGroup>

                        </h:panelGrid>

                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            <a4j:region>
                                <rich:dataTable  value="#{Appraisal.datagridemp}" var="dataItem"
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
                                        <a4j:commandLink id="selectlinkD"  action="#{Appraisal.selectDataEmpMethod}" oncomplete="#{rich:component('acView1')}.hide();#{rich:element('appraisaldate')}.style=setMask();#{rich:element('efectivedate')}.style=setMask();"  reRender="add,update,save,delete,empName,empId,department,designation,grade,unit,joiningdate,blockfrom,expirince,appraisaldate,efectivedate"  >
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{Appraisal.selectdataEmpdetail}"/>
                                        </a4j:commandLink>
                                    </rich:column>

                                </rich:dataTable>
                                <rich:datascroller id="datascrollerTab" align="left" for="taskList" maxPages="5"/>
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
