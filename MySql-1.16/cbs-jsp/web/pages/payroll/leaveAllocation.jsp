<%-- 
    Document   : LeaveAllocation
    Created on : May 25, 2011, 5:26:23 PM
    Author     : Sudhir Kumar Bisht
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
            <title>Leave Allocation</title>
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
        </head>
        <body>
            <a4j:form id="leaveAllocation">
                <h:panelGrid id="mainPanel" bgcolor="#fff" columns="1" style="border:1px ridge #BED6F8" width="100%">
                    <h:panelGrid columns="3" id="gridPanel1" style="width:100%;text-align:center;" styleClass="header">
                        <h:panelGroup id="groupPanel2" layout="block">
                            <h:outputLabel id="lblDate" styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stDate" styleClass="output" value="#{leaveAllocation.todayDate}"/>
                        </h:panelGroup>
                        <h:outputLabel id="OL30" styleClass="headerLabel" value="Leave Allocation"/>
                        <h:panelGroup id="groupPanel3" layout="block">
                            <h:outputLabel id="label3" styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{leaveAllocation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>

                    <h:panelGrid  id="gridPanel2"   width="100%" style="text-align:center" styleClass="row2">
                        <h:outputText id="errMsg5" value="#{leaveAllocation.errmsg}" styleClass="error"/>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4" id="gridPanel9" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel styleClass="label"  value="Operation"><font class="required" style="color:red;">*</font></h:outputLabel>
                            <h:selectOneListbox size="1" id="optionList"  styleClass="ddlist" value="#{leaveAllocation.function}" style="width:90px">
                                <f:selectItems value="#{leaveAllocation.functionList}"/>
                                <a4j:support event="onblur" action="#{leaveAllocation.onChangeFunction}" reRender="popUpGridPanel,taskList1,gridPanelTable,errMsg5" 
                                             oncomplete="if(#{leaveAllocation.function=='2'}){ #{rich:component('popUpGridPanel')}.show(); } 
                                             else {  #{rich:component('popUpGridPanel')}.hide(); }" focus="#{leaveAllocation.focusId}"/>
                            </h:selectOneListbox>
                            <h:outputLabel id="nature" styleClass="output" value="Allocation Category"/>
                            <h:selectOneListbox id="natureList" styleClass="ddlist" size="1" style="width: 160px" value="#{leaveAllocation.allocationcat}" disabled="#{leaveAllocation.boolleavecode}">
                                <f:selectItems value="#{leaveAllocation.category}" />
                                <a4j:support event="onblur" action="#{leaveAllocation.getCategoryDetail}" reRender="categorydetail,empSearchGrid,taskList,errMsg5" 
                                             oncomplete="if(#{leaveAllocation.allocationcat=='EWE'}){ #{rich:component('empSearchModelPanel')}.show();}
                                             else{#{rich:component('empSearchModelPanel')}.hide();}" focus="#{leaveAllocation.focusId}"/>
                            </h:selectOneListbox>
                        </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4"  id="gridPanel6" style="text-align:left;" width="100%" styleClass="row2">
                        <h:outputLabel id="descLabel" styleClass="output" value="Categorization Details"/>
                        <h:selectOneListbox id="categorydetail" styleClass="ddlist" size="1" style="width: 160px" value="#{leaveAllocation.allocationcatdetail}"  disabled="#{leaveAllocation.boolleavecode}">
                            <f:selectItems value="#{leaveAllocation.categorydetails}" />
                        </h:selectOneListbox>
                        <h:outputLabel id="purpose" styleClass="output" value="Leave Code"/>
                        <h:selectOneListbox id="purposelist" styleClass="ddlist" size="1" style="width:100px" value="#{leaveAllocation.leaveCode}" disabled="#{leaveAllocation.boolleavecode}">
                            <f:selectItems value="#{leaveAllocation.leavecode}"/>
                        </h:selectOneListbox>
                    </h:panelGrid>

                    <h:panelGrid columnClasses="col2,col7,col2,col7" columns="4"  id="gridPanel16" style="text-align:left;" width="100%" styleClass="row1">
                        <h:outputLabel id="applicableDate" styleClass="output" value="Allocation Date "/>
                        <h:panelGroup>
                            <h:inputText id="calIntDate1" styleClass="input calInstDate" style="width:80px" maxlength="10" value="#{leaveAllocation.postingDate}">
                                <f:convertDateTime pattern="dd/MM/yyyy" />
                            </h:inputText>
                            <h:outputLabel id="lblDurationFrom1" styleClass="label" value="DD/MM/YYYY" style="color:purple"/>
                        </h:panelGroup>
                        <h:outputText/>
                        <h:outputText/>
                    </h:panelGrid>

                    <h:panelGrid id="footerPanel4" style="width:100%;text-align:center;" styleClass="footer">
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton id="save"  value="Save" action="#{leaveAllocation.saveData}"
                                               reRender="natureList,purposelist,calIntDate1,categorydetail,errMsg5,optionList" oncomplete="setMask()"/>
                            <a4j:commandButton id="delete"   value="Delete" action="#{leaveAllocation.deleteData}" disabled="#{leaveAllocation.updateDelete}"
                                               reRender="natureList,purposelist,calIntDate1,categorydetail,errMsg5,optionList" oncomplete="setMask()"/>
                            <a4j:commandButton id="refresh" ajaxSingle="true" value="Refresh" action="#{leaveAllocation.refreshDatabtn}" 
                                               reRender="natureList,purposelist,calIntDate1,categorydetail,errMsg5,optionList" oncomplete="setMask()"/>
                            <a4j:commandButton id="exit" value="Exit" action="#{leaveAllocation.btn_exit}"/>
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
                            <h:selectOneListbox id="searchcriteria" styleClass="ddlist" size="1" style="width: 80px" value="#{leaveAllocation.searchflag}">
                                <f:selectItem itemValue="Emp Id"/>
                                <f:selectItem itemValue="Emp Name"/>
                            </h:selectOneListbox>

                            <h:inputText id="searchtext" value="#{leaveAllocation.searchcriteria}" styleClass="input" onkeyup="this.value = this.value.toUpperCase();" >
                                <a4j:support event="onblur" action="#{leaveAllocation.categorizationDetails}"  reRender="taskList,searchcriteria,searchtext"/>
                            </h:inputText>
                            <h:outputText/>
                        </h:panelGrid>
                        <h:panelGrid id="listGrid" columns="1" styleClass="row1" columnClasses="vtop" style=" width:100%;height:0px">
                            <a4j:region>
                                <rich:dataTable  value="#{leaveAllocation.gridresult}" var="dataItem"
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
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empidD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empnameD}" /></rich:column>
                                    <rich:column width="100px" ><h:outputText value="#{dataItem.empphD}" /></rich:column>
                                    <rich:column width="100px" >
                                        <a4j:commandLink id="selectlinkD"  ajaxSingle="true" action="#{leaveAllocation.selectvalueforupdate}" oncomplete="#{rich:component('empSearchModelPanel')}.hide();;setMask()" 
                                                         reRender="categorydetail" focus="#{leaveAllocation.focusId}">
                                            <h:graphicImage value="/resources/images/edit.gif" style="border:0"  />
                                            <f:setPropertyActionListener value="#{dataItem}" target="#{leaveAllocation.selectdata}"/>
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
            <rich:modalPanel id="popUpGridPanel" width="600" moveable="false" height="250" onmaskdblclick="#{rich:component('popUpGridPanel')}.hide()">
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
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <rich:dataTable  value="#{leaveAllocation.result}" var="dataItem1"
                                         rowClasses="gridrow1, gridrow2" id="taskList1" rows="5" rowKeyVar="row"
                                         onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                         onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                         onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                            <f:facet name="header">
                                <rich:columnGroup>
                                    <rich:column width="100px" ><h:outputText value="Employee Id" /></rich:column>
                                    <rich:column width="200px" ><h:outputText value="Employee Name" /></rich:column>
                                    <rich:column width="80px" ><h:outputText value="Leave Code" /></rich:column>
                                    <rich:column width="80px" ><h:outputText value="Posting Date" /></rich:column>
                                    <rich:column width="80px" ><h:outputText value="Select" /></rich:column>
                                </rich:columnGroup>
                            </f:facet>
                            <rich:column width="100px" ><h:outputText value="#{dataItem1.empid}" /></rich:column>
                            <rich:column width="200px" ><h:outputText value="#{dataItem1.empname}" /></rich:column>
                            <rich:column width="80px" ><h:outputText value="#{dataItem1.leavecode}" /></rich:column>
                            <rich:column width="80px" ><h:outputText value="#{dataItem1.postingdate}" /></rich:column>
                            <rich:column width="80px" >
                                <a4j:commandLink id="selectlink" ajaxSingle="true" action="#{leaveAllocation.selectdataOperation}" focus="#{leaveAllocation.focusId}"
                                                 reRender="purposelist,natureList,calIntDate1,categorydetail,save,Update,delete" oncomplete="#{rich:component('popUpGridPanel')}.hide();setMask()">
                                    <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                    <f:setPropertyActionListener value="#{dataItem1}" target="#{leaveAllocation.selectdata1}"/>
                                </a4j:commandLink>
                            </rich:column>
                        </rich:dataTable>
                        <rich:datascroller align="left" for="taskList1" maxPages="20"/>
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
