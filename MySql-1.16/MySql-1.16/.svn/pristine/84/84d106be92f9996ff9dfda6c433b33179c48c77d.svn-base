<%--
    Document   : itemgroupdepreciation
    Created on : Nov 8, 2011, 3:03:27 PM
    Author     : Ankit Verma
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <html>
        <head>
            <%-- <a4j:keepAlive beanName="ItemGroupDepreciation"/> --%>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Item Group Depreciation</title>
             <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

            <script type="text/javascript">
                var row;
                jQuery(function(jQuery){
                    setMonthMask();
                    setYearMask();
                });
                function setMonthMask(){
                    jQuery(".calInstMonth").mask("19");
                }
                 function setYearMask(){
                    jQuery(".calInstYear").mask("9999");
                }

            </script>
        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{ItemGroupDepreciation.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Item Group Depreciation"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{ItemGroupDepreciation.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{ItemGroupDepreciation.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" id="gridpanel1" columnClasses="col3,col3,col3,col3,col3,col3"  style="height:30px;" styleClass="row1" width="100%" >
                            
                        <h:outputLabel id="lblGroup" styleClass="headerLabel"  value="Group" />
                        <h:selectOneListbox id="listGroup" value="#{ItemGroupDepreciation.selectGroup}" size="1" styleClass="ddlist"  style="width:180px" disabled="#{ItemGroupDepreciation.disableGroup}">
                                <f:selectItems value="#{ItemGroupDepreciation.selectGroupList}"/>
                         </h:selectOneListbox>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                            <h:outputText/>
                     </h:panelGrid>
                     <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpane56"   style="height:30px;" styleClass="row2" width="100%" >
                       <h:outputLabel id="lblperiod" styleClass="headerLabel"  value="Wef-Period"  />
                       <h:panelGroup styleClass="label">
                           <h:inputText id="txtMonth" styleClass="input calInstMonth"  maxlength="2"  value="#{ItemGroupDepreciation.month}" style="setMonthMask();width:20px;" />
                          <font color="purple">MM</font>
                          <h:inputText id="txtYear" styleClass="input calInstYear"  maxlength="4"  value="#{ItemGroupDepreciation.year}" style="setYearMask();width:30px;"/>
                          <font color="purple">YYYY</font>
                       </h:panelGroup>
                        <h:outputLabel id="lblDeprecuiation" styleClass="headerLabel"  value="Depreciation"  />
                        <h:panelGroup styleClass="label" >
                            <h:inputText id="txtDep" styleClass="input"  maxlength="3" style="width:40px;" value="#{ItemGroupDepreciation.depPercent}" />
                              <font color="purple">%</font>
                        </h:panelGroup>
                              <h:outputLabel id="lblDepMethod" styleClass="headerLabel"  value="Depreciation Method"  />
                              <h:selectOneListbox id="listMethod" value="#{ItemGroupDepreciation.selectMethod}" size="1" styleClass="ddlist"  style="width:120px" >
                                  <f:selectItem itemValue="--Select--"/>
                                  <f:selectItem itemValue="Straight-Line"/>
                                  <f:selectItem itemValue="Declining Balance"/>
                            </h:selectOneListbox>
                     </h:panelGrid>

                      <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel75"   style="height:30px;" styleClass="row1" width="100%" >

                         <h:outputLabel id="lblDepRound" styleClass="headerLabel"  value="Depreciation Round"/>
                          <h:selectOneListbox id="listRound" value="#{ItemGroupDepreciation.selectRound}" size="1" styleClass="ddlist"  style="width:120px" >
                                  <f:selectItem itemValue="Yes"/>
                                  <f:selectItem itemValue="No"/>
                                  <a4j:support event="onblur" action="#{ItemGroupDepreciation.disableSomeFields}" reRender="listRoundBase,listRoundRange"  />
                          </h:selectOneListbox>
                         <h:outputLabel id="lblRoundBase" styleClass="headerLabel"  value="Round Base"  />
                         <h:selectOneListbox id="listRoundBase" value="#{ItemGroupDepreciation.selectRoundBase}" size="1" styleClass="ddlist"  style="width:50px" disabled="#{ItemGroupDepreciation.disableRoundBase}">
                                  <%--f:selectItem itemValue="-2"/--%>
                                  <%--f:selectItem itemValue="-1"/--%>
                                  <f:selectItem itemValue="0"/>
                                  <f:selectItem itemValue="1"/>
                                  <f:selectItem itemValue="2"/>

                            </h:selectOneListbox>
                       <h:outputLabel id="lblRoundRange" styleClass="headerLabel"  value="Round Range"  />
                       <h:selectOneListbox id="listRoundRange" value="#{ItemGroupDepreciation.selectRoundRange}" size="1" styleClass="ddlist"  style="width:120px" disabled="#{ItemGroupDepreciation.disableRoundRange}" >
                                  <f:selectItem itemValue="Normal"/>
                                  <f:selectItem itemValue="Higher"/>
                                  <f:selectItem itemValue="Lower"/>
                           </h:selectOneListbox>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{ItemGroupDepreciation.datagrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Group Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Wef-Period" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Dep-Percent" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Dep-Method" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Dep-Round" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Dep-Base" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Round-Range" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Enter By" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column width="100px" ><h:outputText  style="text-align:center" value="#{dataItem1.sno}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.groupCode}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.itemCode}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.itemName}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.wefPeriod}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.depPercent}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.depMethod}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.depRound}" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.roundBase}" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.roundRange}" /></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  value="#{dataItem1.enterBy}"/></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlink" action="#{ItemGroupDepreciation.selectData}" reRender="gridpanel1,gridpane56,gridpanel75,btnSave,btnUpdate,btnRefresh" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                           <f:setPropertyActionListener value="#{dataItem1}" target="#{ItemGroupDepreciation.currentData}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>

                     <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnSave" value="Save"   action="#{ItemGroupDepreciation.saveData}" reRender="stxtError,mainPanelGrid" disabled="#{ItemGroupDepreciation.saveDisable}">
                            </a4j:commandButton>
                               <a4j:commandButton  id="btnUpdate" value="Update"   action="#{ItemGroupDepreciation.updateData}" reRender="stxtError,mainPanelGrid" disabled="#{ItemGroupDepreciation.updateDisable}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{ItemGroupDepreciation.refreshForm}"  reRender="mainPanelGrid" oncomplete="#{rich:element('txtMonth')}.style=setMonthMask();#{rich:element('txtYear')}.style=setYearMask()">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{ItemGroupDepreciation.exitAction}" reRender="mainPanelGrid" oncomplete="#{rich:element('txtMonth')}.style=setMonthMask();#{rich:element('txtYear')}.style=setYearMask()">
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
