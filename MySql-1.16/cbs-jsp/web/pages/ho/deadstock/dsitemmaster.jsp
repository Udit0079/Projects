<%-- 
    Document   : dsitemmaster
    Created on : Nov 5, 2011, 5:03:27 PM
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
          <%--  <a4j:keepAlive beanName="DsItemMaster"/> --%>
            <link href="/cbs-jsp/resources/txn_style.css" type="text/css" rel="stylesheet"/>
            <meta http-equiv="Pragma" content="no-cache"/>
            <meta http-equiv="Cache-Control" content="no-cache"/>
            <title>Deadstock Item Master</title>
             <a4j:loadScript src="resource:///org/richfaces/renderkit/html/scripts/jquery/jquery.js" />
            <a4j:loadScript src="/resources/js/jquery.maskedinput-1.3.min.js" />

        </head>
        <body>
            <a4j:form id="form1">
                <h:panelGrid columns="1" id="mainPanelGrid" width="100%" bgcolor="#fff" style="border:1px ridge #BED6F8">
                    <h:panelGrid columns="3" id="gridPanel2" styleClass="header" style="width:100%;text-align:center;">
                        <h:panelGroup id="a2" layout="block">
                            <h:outputLabel styleClass="headerLabel" value="Date: "/>
                            <h:outputText id="stxtDate" styleClass="output" value="#{DsItemMaster.todayDate}"/>
                        </h:panelGroup>

                        <h:outputLabel styleClass="headerLabel" value="Item Master"/>
                        <h:panelGroup layout="block">
                            <h:outputLabel styleClass="headerLabel" value="User: "/>
                            <h:outputText id="stxtUser" styleClass="output" value="#{DsItemMaster.userName}"/>
                        </h:panelGroup>
                    </h:panelGrid>
                    <h:panelGrid  id="grdpane6" style="height:30px;text-align:center" styleClass="row2" width="100%" >
                        <h:outputText id="stxtError"  styleClass="error" value="#{DsItemMaster.msg}"/>
                    </h:panelGrid>
                    <h:panelGrid columns="6" columnClasses="col3,col3,col3,col3,col3,col3" id="gridpanel1"   style="height:30px;" styleClass="row1" width="100%" >
                        <h:outputLabel id="lblGroup" styleClass="headerLabel"  value="Group"  />
                              <h:selectOneListbox id="listGroup" value="#{DsItemMaster.selectGroup}" size="1" styleClass="ddlist"  style="width:120px" >
                                <f:selectItems value="#{DsItemMaster.selectGroupList}"/>
                            </h:selectOneListbox>
                         <h:outputLabel id="lblItemCode" styleClass="headerLabel"  value="Item Code"  />
                         <h:inputText id="txtItemCode" styleClass="input"  maxlength="10" style="width:60px" value="#{DsItemMaster.itemCode}" disabled="#{DsItemMaster.itemCodeDisable}"/>
                         <h:outputLabel id="lblItemName" styleClass="headerLabel"  value="Item Name"  />
                        <h:inputText id="txtItemName" styleClass="input"  maxlength="30" value="#{DsItemMaster.itemName}" onkeyup="this.value = this.value.toUpperCase();"/>
                    </h:panelGrid>
                    <h:panelGrid id="gridPanelTable" style=" width:100%;height:0px" styleClass="row1" columnClasses="vtop">
                        <a4j:region>
                            <rich:dataTable  value="#{DsItemMaster.datagrid}" var="dataItem1"
                                             rowClasses="gridrow1, gridrow2" id="taskList1" rows="3" rowKeyVar="row"
                                             onRowMouseOver="this.style.backgroundColor='#B5F3FB'"
                                             onRowMouseOut="this.style.backgroundColor='#{a4jSkin.rowBackgroundColor}'" width="100%"
                                             onRowContextMenu="if (row) row.style.backgroundColor='#{a4jSkin.rowBackgroundColor}';row=this;">
                                <f:facet name="header">
                                    <rich:columnGroup>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="S No." /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Group Name" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Code" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Item Name" /></rich:column>
                                         <rich:column width="100px" ><h:outputText style="text-align:center" value="Enter by" /></rich:column>
                                        <rich:column width="100px" ><h:outputText style="text-align:center" value="Select" /></rich:column>

                                    </rich:columnGroup>
                                </f:facet>

                                <rich:column width="100px" ><h:outputText  style="text-align:center" value="#{dataItem1.sno}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.groupName}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.itemCode}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center" value="#{dataItem1.itemName}"/></rich:column>
                                <rich:column width="100px" ><h:outputText style="text-align:center"  value="#{dataItem1.enterBy}"/></rich:column>
                                <rich:column width="100px" >
                                    <a4j:commandLink id="selectlink" action="#{DsItemMaster.selectData}" reRender="gridpanel1,btnSave,btnUpdate,stxtError" >
                                        <h:graphicImage value="/resources/images/edit.gif" style="border:0" />
                                           <f:setPropertyActionListener value="#{dataItem1}" target="#{DsItemMaster.currentData}"/>
                                    </a4j:commandLink>
                                </rich:column>
                            </rich:dataTable>
                            <rich:datascroller align="left" for="taskList1" maxPages="5"/>
                        </a4j:region>
                    </h:panelGrid>
                   
                     <h:panelGrid columns="2" id="gridpanel5" style="width:100%;text-align:center;" styleClass="footer" >
                        <h:panelGroup id="btnPanel">
                            <a4j:commandButton  id="btnSave" value="Save"   action="#{DsItemMaster.saveData}" reRender="stxtError,mainPanelGrid" disabled="#{DsItemMaster.saveDisable}">
                            </a4j:commandButton>
                               <a4j:commandButton  id="btnUpdate" value="Update"   action="#{DsItemMaster.updateData}" reRender="stxtError,mainPanelGrid" disabled="#{DsItemMaster.updateDisable}">
                            </a4j:commandButton>
                            <a4j:commandButton id="btnRefresh" value="Refresh"  action="#{DsItemMaster.refreshForm}"  reRender="mainPanelGrid" >
                            </a4j:commandButton>
                            <a4j:commandButton id="btnExit" value="Exit"  action="#{DsItemMaster.exitAction}" reRender="mainPanelGrid" >
                            </a4j:commandButton>

                        </h:panelGroup>
                    </h:panelGrid>
                </h:panelGrid>
            </a4j:form>
        </body>
    </html>
</f:view>
