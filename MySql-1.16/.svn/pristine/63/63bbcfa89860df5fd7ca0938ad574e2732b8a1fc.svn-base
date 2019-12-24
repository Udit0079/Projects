<%-- 
    Document   : taxInvestmentCategory
    Created on : Jun 15, 2012, 1:17:04 PM
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
            <title>Tax Investment Category</title>
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
            <a4j:form id="taxInvestmentCategory">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                 <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{taxinvestmentcategory.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Tax Investment Category"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{taxInvestmentCategory.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                     <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="error">
                        <h:outputText id="errMsg" value="#{taxInvestmentCategory.errmsg}"/>
                         <h:messages errorClass="errorMessage" infoClass="infoMessage" layout="table" globalOnly="true" showDetail="false" showSummary="true"/>
                    </h:panelGrid>
                    <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"    columns="6" id="gridPanelFunc" style="text-align:left;" width="100%" styleClass="row2">
                           <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                        <h:selectOneListbox size="1" id="operationList" style="width:80px" styleClass="ddlist" value="#{taxInvestmentCategory.operation}">
                                <f:selectItems value="#{taxInvestmentCategory.operationList}"/>
                                <a4j:support event="onblur" action="#{taxInvestmentCategory.setOperationOnBlur}" oncomplete="if(#{taxInvestmentCategory.operation=='1'}){ #{rich:component('acView1')}.show();#{rich:component('popUpGridPanel')}.hide(); } 
                                             else if(#{taxInvestmentCategory.operation=='2'}){ #{rich:component('popUpGridPanel')}.show(); #{rich:component('acView1')}.hide()} 
                                             else {  #{rich:component('popUpGridPanel')}.hide(); #{rich:component('acView1')}.hide()}"
                                             reRender="errMsg,save,delete,acView1,popUpGridPanel,categoryname,catamount,catlimit" />
                        </h:selectOneListbox>
                        <h:outputText/>
                        <h:outputText/>
                      </h:panelGrid>
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"   columns="6" id="second" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel value="Employee Name" styleClass="label" />
                        <h:inputText id="empname" value="#{taxInvestmentCategory.empName}" style="width:120px;" maxlength="100" styleClass="input" disabled="true"/>
                        <h:outputLabel value="Category Name" styleClass="label" />
                        <h:selectOneListbox id="categoryname" size="1" style="width:120px" value="#{taxInvestmentCategory.categoryCode}" styleClass="ddlist" disabled="#{taxInvestmentCategory.disableflag}">
                          <f:selectItems value="#{taxInvestmentCategory.categoryNamelist}"/>
                        </h:selectOneListbox>
                        </h:panelGrid>
                        
                        <h:panelGrid columnClasses="col3,col3,col3,col3,col3,col3"   columns="6" id="third" style="text-align:left;" width="100%" styleClass="row2">
                         <h:outputLabel value="Category Amount" styleClass="label" />
                         <h:inputText id="catamount" value="#{taxInvestmentCategory.categoryAmount}" style="width:120px;" maxlength="100" styleClass="input" disabled="#{taxInvestmentCategory.disableflag}" />
                        <h:outputLabel value="Category Max Limit" styleClass="label" />
                        <h:inputText id="catlimit" value="#{taxInvestmentCategory.categoryLimit}" style="width:120px;" maxlength="100" styleClass="input" disabled="#{taxInvestmentCategory.disableflag}" />
                        </h:panelGrid>
                   <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save"  value="Save" action="#{taxInvestmentCategory.save}" disabled="#{taxInvestmentCategory.disablesave}" reRender="errMsg,save,delete,empname,catamount,catlimit,categoryname" >
                            </a4j:commandButton>
                            <a4j:commandButton id="delete"  value="Delete" action="#{taxInvestmentCategory.delete}" disabled="#{taxInvestmentCategory.disabledelete}" reRender="errMsg,save,delete,empname,catamount,catlimit,categoryname">
                            </a4j:commandButton>
                            <a4j:commandButton id="cancel" value="Refresh" action="#{taxInvestmentCategory.refreshButton}" reRender="errMsg,save,delete,operationList,empname,catamount,catlimit,categoryname" >
                            </a4j:commandButton>
                            <a4j:commandButton id="exit" value="Exit" action="#{taxInvestmentCategory.btnExit}">
                            </a4j:commandButton>
                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
                </a4j:form>
                        
                <rich:modalPanel id="acView1" moveable="false" height="222" resizeable="true"  width="800" onmaskdblclick="#{rich:component('acView1')}.hide();">
                    <f:facet name="header">
                        <h:outputText value="Employee Search "/>
                    </f:facet>
                    <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink1"/>
                            <rich:componentControl for="acView1" attachTo="closelink1" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                        <h:panelGrid id="SubPanelgid3" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:168px">
                            
                                <rich:dataTable  value="#{taxInvestmentCategory.datagridemp}" var="dataItem"
                                                 rowClasses="gridrow1, gridrow2" id="taskList" rows="3" rowKeyVar="row"
                                                 onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                                 onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                                 onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                    <f:facet name="header">
                                        <rich:columnGroup>
                                            <rich:column width="100px" ><h:outputText value="Employee Id"/></rich:column>
                                            <rich:column width="100px" ><h:outputText value= "Employee Name"/></rich:column>
                                            <rich:column width="100px" ><h:outputText value="Select" /></rich:column>
                                        </rich:columnGroup>
                                    </f:facet>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empnameD}" /></rich:column> 
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empidD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD"  action="#{taxInvestmentCategory.selectDataEmpMethod}" oncomplete="#{rich:component('acView1')}.hide();" reRender="acView1,empname,save,delete,empname,categoryname">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{taxInvestmentCategory.currentItem}"/>
                                        </a4j:commandLink>
                                    </rich:column>
                              </rich:dataTable>
                                <rich:datascroller id="datascrollerTab" align="left" for="taskList" maxPages="5"/>
                             </h:panelGrid>
                        </h:form>
                        <h:panelGrid id="acViewFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="acViewBtnPanel">
                                <a4j:commandButton id="acViewClose1" value="Close" onclick="#{rich:component('acView1')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid>
                </rich:modalPanel>     
            <rich:modalPanel id="popUpGridPanel" width="800"  moveable="false" height="185" resizeable="true" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide();">
                <f:facet name="header">
                    <h:panelGrid style="width:100%;text-align:center;">
                        <h:outputText value="Tax Investment Category Details" style="text-align:center;"/>
                    </h:panelGrid>
                </f:facet>
                 <f:facet name="controls">
                        <h:panelGroup>
                            <h:graphicImage value="/resources/images/close.png" styleClass="hidelink" id="closelink2"/>
                            <rich:componentControl for="popUpGridPanel" attachTo="closelink2" operation="hide" event="onclick"/>
                        </h:panelGroup>
                    </f:facet>
                    <h:form>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:150px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{taxInvestmentCategory.griddata}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Employee Id" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Employee Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Category Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Category Amount" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Category Max Limit" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>
                                    </rich:columnGroup>
                                </f:facet>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empIds}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.empnameT}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.categoryCode}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.categoryAmt}" style="text-align:center" /></rich:column>
                                <rich:column width="100px" ><h:outputText value="#{dataItem1.categoryMaxLimit}" style="text-align:center" /></rich:column>
                               <rich:column width="100px" >
                                   <a4j:commandLink id="selectlinka"  action="#{taxInvestmentCategory.selectDataCurrent}" oncomplete="#{rich:component('popUpGridPanel')}.hide();"  reRender="save,delete,errMsg,catamount,catlimit,empname,categoryname" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                        <f:setPropertyActionListener value="#{dataItem1}" target="#{taxInvestmentCategory.currentdata}"/> </a4j:commandLink>
                                </rich:column>

                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                          <h:panelGrid id="popUpGridPanelFooterPanel" style="width:100%;text-align:center;" styleClass="footer">
                            <h:panelGroup id="popUpGridBtnPanel">
                                <a4j:commandButton id="popUpGridPanelClose1" value="Close" onclick="#{rich:component('popUpGridPanel')}.hide(); return false;">

                                </a4j:commandButton>
                            </h:panelGroup>
                        </h:panelGrid  >
                        </a4j:region>
                    </h:panelGrid>
                    </h:form>
                </rich:modalPanel>
                    
        </body>
    </html>
</f:view>
